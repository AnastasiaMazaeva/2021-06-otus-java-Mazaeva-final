package homework.service.interfaces;

import homework.domain.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    User save(User user);
    void delete(User user);
    void closeAccount(User user);
    Optional<User> findByUsername(String username);

    List<User> findAllExcept(String principal);
}
