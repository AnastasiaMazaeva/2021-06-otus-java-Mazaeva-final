package homework.db.service;


import homework.domain.model.User;

import java.util.List;
import java.util.Optional;

public interface DBServiceUser {

    User save(User client);

    List<User> findAll();

    Optional<User> findByLogin(String username);

    List<User> findAllExcept(String principal);
}
