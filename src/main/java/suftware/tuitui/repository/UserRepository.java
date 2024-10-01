package suftware.tuitui.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import suftware.tuitui.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    boolean existsByAccount(String account);
}
