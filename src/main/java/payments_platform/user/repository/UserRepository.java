package payments_platform.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import payments_platform.user.model.User;

public interface UserRepository extends JpaRepository<User, Long>{
    User findByEmail(String email);
}
