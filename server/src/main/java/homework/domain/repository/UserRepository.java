package homework.domain.repository;

import homework.domain.model.User;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    Optional<User> findByLogin(String username);

    @Query("select * from dat_user u where u.login <> :principal")
    List<User> findAllExcept(@Param("principal") String principal);

    @Modifying
    @Query("update dat_user set is_active = not is_active where id = :userId")
    void changeStatus(@Param("userId") Long userId);
}
