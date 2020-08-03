package usersapplication.helpers.workingconditions.salarygroups;

import nl.conclusionexperts.workingconditionapplication.enums.SalaryGroups;
import usersapplication.domain.response.workingcondition.salary.SalaryGroupResponse;

public class SalaryGroupResponseTestData {

    public static SalaryGroupResponse salaryGroupResponse(SalaryGroups salaryGroups) {
        switch (salaryGroups) {
            default:
                return SalaryGroupResponse.builder().build();
            case A:
                return SalaryGroupResponse.builder()
                        .id(0L)
                        .salaryGroupCode(salaryGroups)
                        .minAmount(2000.0)
                        .maxAmount(2500.0)
                        .build();
            case B:
                return SalaryGroupResponse.builder()
                        .id(1L)
                        .salaryGroupCode(salaryGroups)
                        .minAmount(2500.0)
                        .maxAmount(3000.0)
                        .build();
            case C:
                return SalaryGroupResponse.builder()
                        .id(2L)
                        .salaryGroupCode(salaryGroups)
                        .minAmount(3000.0)
                        .maxAmount(3500.0)
                        .build();
            case D:
                return SalaryGroupResponse.builder()
                        .id(3L)
                        .salaryGroupCode(salaryGroups)
                        .minAmount(3500.0)
                        .maxAmount(4000.0)
                        .build();
            case E:
                return SalaryGroupResponse.builder()
                        .id(4L)
                        .salaryGroupCode(salaryGroups)
                        .minAmount(4000.0)
                        .maxAmount(4500.0)
                        .build();
            case F:
                return SalaryGroupResponse.builder()
                        .id(5L)
                        .salaryGroupCode(salaryGroups)
                        .minAmount(4500.0)
                        .maxAmount(5000.0)
                        .build();
            case G:
                return SalaryGroupResponse.builder()
                        .id(6L)
                        .salaryGroupCode(salaryGroups)
                        .minAmount(5000.0)
                        .maxAmount(6000.0)
                        .build();
        }
    }

}
