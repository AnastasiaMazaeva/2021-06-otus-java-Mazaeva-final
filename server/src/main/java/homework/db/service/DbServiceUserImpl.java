package homework.db.service;

import homework.db.sessionmanager.TransactionManager;
import homework.domain.model.User;
import homework.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DbServiceUserImpl implements DBServiceUser {
    private static final Logger log = LoggerFactory.getLogger(DbServiceUserImpl.class);

    private final TransactionManager transactionManager;
    private final UserRepository userRepository;

    @Override
    public void save(User client) {
        transactionManager.callInTransaction(() -> {
            var savedClient = userRepository.save(client);
            log.info("saved client: {}", savedClient);
        });
    }

    @Override
    public Optional<User> findByLogin(String username) {
        return userRepository.findByLogin(username);
    }

    @Override
    public List<User> findAllExcept(String principal) {
        return userRepository.findAllExcept(principal);
    }

    @Override
    public void changeStatus(Long userId) {
        transactionManager.callInTransaction(() -> userRepository.changeStatus(userId));
    }

}
