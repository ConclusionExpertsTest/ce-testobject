package usersapplication.repository;

import usersapplication.domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {

    @Query("SELECT u FROM Users u WHERE u.isActive = true")
    Collection<Users> findAllByIsActive();

    @Query("SELECT u FROM Users u WHERE u.isActive = false")
    Collection<Users> findAllByIsInActive();

}
