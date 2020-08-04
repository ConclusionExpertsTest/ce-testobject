package workingconditionapplication.repository;

import workingconditionapplication.domain.WorkingConditions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CEWorkingConditionsRepository extends JpaRepository<WorkingConditions, Long> { }
