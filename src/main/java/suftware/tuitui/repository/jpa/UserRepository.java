package suftware.tuitui.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import suftware.tuitui.domain.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    boolean existsByAccount(String account);

    Optional<User> findByAccount(String account);
}
