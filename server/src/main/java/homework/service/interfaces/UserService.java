package homework.service.interfaces;

import homework.domain.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    void save(User user);

    void toggleActive(Long userId);

    Optional<User> findByUsername(String username);

    List<User> findAllExcept(String principal);
}
