package usersapplication.helpers.workingconditions;

import nl.conclusionexperts.workingconditionapplication.enums.CompanyLaptopTypes;
import nl.conclusionexperts.workingconditionapplication.enums.SalaryGroups;
import usersapplication.domain.response.workingcondition.CeWorkingConditionsResponse;
import usersapplication.domain.response.workingcondition.WorkingConditionsResponse;

public class WorkingConditionsResponseTestData {

    public static CeWorkingConditionsResponse workingConditionsResponse(int id) {

        switch (id) {
            default:
                return CeWorkingConditionsResponse.builder().build();
            case 0:
                return WorkingConditionsResponse.builder()
                        .id(Integer.toUnsignedLong(id))
                        .salaryGroupResponse(SalaryGroups.A)
                        .companyCar(false)
                        .companyLaptopResponse(CompanyLaptopTypes.Type5)
                        .build();
            case 1:
                return CeWorkingConditionsResponse.builder()
                        .id(Integer.toUnsignedLong(id))
                        .salaryGroupResponse(SalaryGroups.B)
                        .companyCar(false)
                        .companyLaptopResponse(CompanyLaptopTypes.Type5)
                        .build();
            case 2:
                return CeWorkingConditionsResponse.builder()
                        .id(Integer.toUnsignedLong(id))
                        .salaryGroupResponse(SalaryGroups.C)
                        .companyCar(true)
                        .companyLaptopResponse(CompanyLaptopTypes.Type3)
                        .build();
            case 3:
                return CeWorkingConditionsResponse.builder()
                        .id(Integer.toUnsignedLong(id))
                        .salaryGroupResponse(SalaryGroups.D)
                        .companyCar(true)
                        .companyLaptopResponse(CompanyLaptopTypes.Type3)
                        .build();
            case 4:
                return CeWorkingConditionsResponse.builder()
                        .id(Integer.toUnsignedLong(id))
                        .salaryGroupResponse(SalaryGroups.E)
                        .companyCar(true)
                        .companyLaptopResponse(CompanyLaptopTypes.Type2)
                        .build();
            case 5:
                return CeWorkingConditionsResponse.builder()
                        .id(Integer.toUnsignedLong(id))
                        .salaryGroupResponse(SalaryGroups.F)
                        .companyCar(true)
                        .companyLaptopResponse(CompanyLaptopTypes.Type1)
                        .build();
            case 6:
                return CeWorkingConditionsResponse.builder()
                        .id(Integer.toUnsignedLong(id))
                        .salaryGroup(SalaryGroups.G)
                        .companyCar(true)
                        .companyLaptopTypes(CompanyLaptopTypes.Type4)
                        .build();
        }
    }

}
