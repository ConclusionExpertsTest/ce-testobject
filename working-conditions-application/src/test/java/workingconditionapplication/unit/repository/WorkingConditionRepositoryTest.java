package workingconditionapplication.unit.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import workingconditionapplication.domain.WorkingConditions;
import workingconditionapplication.enums.CompanyLaptopTypes;
import workingconditionapplication.enums.SalaryGroups;
import workingconditionapplication.repository.WorkingConditionsRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class WorkingConditionRepositoryTest {

    @Autowired
    private WorkingConditionsRepository workingConditionsRepository;

    @Test
    public void findWorkingConditionById() {
        // Arrange
        WorkingConditions newWorkingConditions = WorkingConditions.builder()
                .salaryGroup(SalaryGroups.A)
                .companyCar(false)
                .companyLaptop(CompanyLaptopTypes.Type5).build();

        workingConditionsRepository.save(newWorkingConditions);
        Long id = workingConditionsRepository.findAll().get(0).getId();

        // Act
        Optional<WorkingConditions> workingConditions = workingConditionsRepository.findById(id);

        // Assert
        assertThat(workingConditions).isEqualTo(workingConditions);
    }



}
