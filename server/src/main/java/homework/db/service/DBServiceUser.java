package homework.db.service;


import homework.domain.model.User;

import java.util.List;
import java.util.Optional;

public interface DBServiceUser {

    void save(User client);

    Optional<User> findByLogin(String username);

    List<User> findAllExcept(String principal);

    void changeStatus(Long userId);
}
