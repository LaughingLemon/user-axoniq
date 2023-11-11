package org.lemon.useraxoniq;

import lombok.extern.slf4j.Slf4j;
import org.axonframework.test.server.AxonServerContainer;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.lemon.useraxoniq.model.User;
import org.lemon.useraxoniq.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Testcontainers
@SpringBootTest
@Slf4j
class UserAxonIQApplicationTests {
    @Autowired
    protected WebApplicationContext context;
    @Autowired
    protected UserRepository userRepository;

    private MockMvc mvc;

    @Container
    private static AxonServerContainer axonServerContainer = getAxonServerContainer();

    @NotNull
    private static AxonServerContainer getAxonServerContainer() {
        DockerImageName myImage = DockerImageName.parse("axoniq/axonserver:latest")
                                                 .asCompatibleSubstituteFor("axoniq/axonserver:latest-dev");
        return new AxonServerContainer(myImage);
    }

    @BeforeEach
    public void startUp() {
        mvc = MockMvcBuilders.webAppContextSetup(context)
                             .build();
    }

    @DynamicPropertySource
    static void registerPgProperties(DynamicPropertyRegistry registry) {
        String server = "localhost:" + axonServerContainer.getGrpcPort();
        registry.add("axon.axonserver.servers", () -> server);
    }

    @Test
    void testRetrieveUsers() throws Exception {
        User testUser = new User();
        testUser.setFirstName("Andrew");
        testUser.setLastName("Jones");
        testUser.setId(1L);
        userRepository.save(testUser);

        MvcResult result = mvc.perform(get("/users"))
                              .andExpect(status().isOk())
                              .andReturn();
        mvc.perform(asyncDispatch(result))
           .andExpect(status().isOk())
           .andExpect(content().json("""
                                             [
                                                 {
                                                 \t"id": 1,
                                                 \t"firstName": "Andrew",
                                                 \t"lastName": "Jones",
                                                 \t"address": null,
                                                 \t"phones": []
                                                 }
                                             ]
                                             """, true));
    }

}
