package usersapplication.domain.response.workingcondition.salary;

import lombok.*;
import nl.conclusionexperts.workingconditionapplication.enums.SalaryGroups;

@Builder
@Getter
@Setter
@AllArgsConstructor
public class SalaryGroupResponse {

    private Long id;
    private SalaryGroups salaryGroupCode;
    private double minAmount;
    private double maxAmount;

}
