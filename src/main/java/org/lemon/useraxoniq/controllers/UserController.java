package org.lemon.useraxoniq.controllers;

import lombok.extern.slf4j.Slf4j;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.lemon.useraxoniq.model.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@Slf4j
public class UserController {
    public static final String FIND_ALL_QUERY = "findAll";

    private final QueryGateway queryGateway;

    public UserController(QueryGateway queryGateway) {
        this.queryGateway = queryGateway;
    }

    @GetMapping("/users")
    public CompletableFuture<List<User>> getUsers() {
        log.info("getUser -> {}", FIND_ALL_QUERY);
        return queryGateway.query(FIND_ALL_QUERY, "", ResponseTypes.multipleInstancesOf(User.class));
    }

}
