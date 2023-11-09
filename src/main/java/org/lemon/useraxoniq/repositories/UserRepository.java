package org.lemon.useraxoniq.repositories;

import org.lemon.useraxoniq.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
}
