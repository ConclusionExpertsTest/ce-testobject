package usersapplication.domain.response.workingcondition;

import usersapplication.domain.response.workingcondition.companylaptop.CompanyLaptopResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import usersapplication.domain.response.workingcondition.salary.SalaryGroupResponse;

@Builder
@Getter
@Setter
@AllArgsConstructor
public class WorkingConditionsResponse {

    private Long id;
    private SalaryGroupResponse salaryGroupResponse;
    private boolean companyCar;
    private CompanyLaptopResponse companyLaptopResponse;

}
