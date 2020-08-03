package nl.conclusionexperts.workingconditionapplication.repository;

import nl.conclusionexperts.workingconditionapplication.domain.CEWorkingConditions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CEWorkingConditionsRepository extends JpaRepository<CEWorkingConditions, Long> { }
