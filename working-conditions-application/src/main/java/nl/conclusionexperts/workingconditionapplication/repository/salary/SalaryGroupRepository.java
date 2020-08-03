package nl.conclusionexperts.workingconditionapplication.repository.salary;

import nl.conclusionexperts.workingconditionapplication.domain.salary.SalaryGroup;
import nl.conclusionexperts.workingconditionapplication.enums.SalaryGroups;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SalaryGroupRepository extends JpaRepository<SalaryGroup, Long> {

    SalaryGroup findUserBySalaryGroupCode(SalaryGroups salaryGroupCode);

}