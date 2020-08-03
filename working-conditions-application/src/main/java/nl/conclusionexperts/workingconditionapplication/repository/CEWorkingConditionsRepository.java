package nl.conclusionexperts.workingconditionapplication.repository;

import nl.conclusionexperts.workingconditionapplication.domain.WorkingConditions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CEWorkingConditionsRepository extends JpaRepository<WorkingConditions, Long> { }
