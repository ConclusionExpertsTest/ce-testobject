package workingconditionapplication.repository.salary;

import workingconditionapplication.domain.salary.SalaryGroup;
import workingconditionapplication.enums.SalaryGroups;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SalaryGroupRepository extends JpaRepository<SalaryGroup, Long> {

    SalaryGroup findUserBySalaryGroupCode(SalaryGroups salaryGroupCode);

}