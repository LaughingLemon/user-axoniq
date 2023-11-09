package org.lemon.useraxoniq.events.queries;

import org.axonframework.queryhandling.QueryHandler;
import org.lemon.useraxoniq.model.User;
import org.lemon.useraxoniq.repositories.UserRepository;
import org.springframework.stereotype.Component;

import static org.lemon.useraxoniq.controllers.UserController.FIND_ALL_QUERY;

@Component
public class UserProjection {

    private final UserRepository userRepository;

    public UserProjection(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @QueryHandler(queryName = FIND_ALL_QUERY)
    public Iterable<User> findAll() {
        return userRepository.findAll();
    }

}
