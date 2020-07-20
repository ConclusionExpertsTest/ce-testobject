package cetestobject.repository;

import cetestobject.domain.CEUsers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface CEUsersRepository extends JpaRepository<CEUsers, Long> {

    @Query("SELECT u FROM CEUsers u")
    Collection<CEUsers> findAllCeUsers();

    @Query("SELECT u FROM CEUsers u WHERE u.isActive = true")
    Collection<CEUsers> findAllByIsActive();

    @Query("SELECT u FROM CEUsers u WHERE u.isActive = false")
    Collection<CEUsers> findAllByIsInActive();

}
