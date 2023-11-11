package org.lemon.useraxoniq.events.queries;

import lombok.extern.slf4j.Slf4j;
import org.axonframework.queryhandling.QueryHandler;
import org.lemon.useraxoniq.model.User;
import org.lemon.useraxoniq.repositories.UserRepository;
import org.springframework.stereotype.Component;

import static org.lemon.useraxoniq.controllers.UserController.FIND_ALL_QUERY;

@Component
@Slf4j
public class UserProjection {

    private final UserRepository userRepository;

    public UserProjection(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @QueryHandler(queryName = FIND_ALL_QUERY)
    public Iterable<User> findAll() {
        log.info(FIND_ALL_QUERY);
        return userRepository.findAll();
    }

}
