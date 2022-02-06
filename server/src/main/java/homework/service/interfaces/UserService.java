package homework.service.interfaces;

import homework.domain.model.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserService {

    void save(User user);

    void toggleActive(Long userId);

    Optional<User> findByUsername(String username);

    List<User> findAllExcept(String principal);

    UUID getToken(String principal);

    User findByToken(UUID token);
}
