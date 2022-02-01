package homework.service.impl;

import homework.db.service.DBServiceUser;
import homework.domain.model.User;
import homework.service.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final DBServiceUser service;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return service.save(user);
    }

    @Override
    public void delete(User user) {
        //service.delete(user);
    }

    @Override
    public void closeAccount(User user) {
        user.setIsOpen(false);
        service.save(user);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return service.findByLogin(username);
    }

    @Override
    public List<User> findAllExcept(String principal) {
        return service.findAllExcept(principal);
    }
}
