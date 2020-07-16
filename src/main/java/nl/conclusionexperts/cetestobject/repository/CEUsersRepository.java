package nl.conclusionexperts.cetestobject.repository;

import nl.conclusionexperts.cetestobject.domain.CEUsers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CEUsersRepository extends JpaRepository<CEUsers, Long> {
}
