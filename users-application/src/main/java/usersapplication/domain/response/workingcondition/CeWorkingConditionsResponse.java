package usersapplication.domain.response.workingcondition;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import nl.conclusionexperts.workingconditionapplication.enums.CompanyLaptopTypes;
import nl.conclusionexperts.workingconditionapplication.enums.SalaryGroups;

@Builder
@Getter
@Setter
@AllArgsConstructor
public class CeWorkingConditionsResponse {

    private Long id;
    private SalaryGroups salaryGroup;
    private boolean companyCar;
    private CompanyLaptopTypes companyLaptopTypes;

}
