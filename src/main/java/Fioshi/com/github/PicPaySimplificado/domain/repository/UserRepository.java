package Fioshi.com.github.PicPaySimplificado.domain.repository;

import Fioshi.com.github.PicPaySimplificado.domain.model.User.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);

    boolean existsByCpf(String cpf);

    boolean existsByEmail(String email);

}
