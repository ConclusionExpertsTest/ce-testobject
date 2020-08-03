package usersapplication.helpers.workingconditions;

import nl.conclusionexperts.workingconditionapplication.domain.CEWorkingConditions;
import nl.conclusionexperts.workingconditionapplication.enums.CompanyLaptopTypes;
import nl.conclusionexperts.workingconditionapplication.enums.SalaryGroups;
import usersapplication.domain.response.workingcondition.CeWorkingConditionsResponse;
import usersapplication.domain.response.workingcondition.WorkingConditionsResponse;
import usersapplication.helpers.workingconditions.companylaptops.CompanyLaptopsResponseTestData;
import usersapplication.helpers.workingconditions.salarygroups.SalaryGroupResponseTestData;

public class WorkingConditionsResponseTestData {

    public static CEWorkingConditions ceWorkingConditions(int id) {
        switch (id) {
            default:
                return CEWorkingConditions.builder().build();
            case 0:
                return CEWorkingConditions.builder()
                        .id(Integer.toUnsignedLong(id))
                        .salaryGroup(SalaryGroups.A)
                        .companyCar(false)
                        .companyLaptop(CompanyLaptopTypes.Type5)
                        .build();
            case 1:
                return CEWorkingConditions.builder()
                        .id(Integer.toUnsignedLong(id))
                        .salaryGroup(SalaryGroups.B)
                        .companyCar(false)
                        .companyLaptop(CompanyLaptopTypes.Type5)
                        .build();
            case 2:
                return CEWorkingConditions.builder()
                        .id(Integer.toUnsignedLong(id))
                        .salaryGroup(SalaryGroups.C)
                        .companyCar(true)
                        .companyLaptop(CompanyLaptopTypes.Type3)
                        .build();
            case 3:
                return CEWorkingConditions.builder()
                        .id(Integer.toUnsignedLong(id))
                        .salaryGroup(SalaryGroups.D)
                        .companyCar(true)
                        .companyLaptop(CompanyLaptopTypes.Type3)
                        .build();
            case 4:
                return CEWorkingConditions.builder()
                        .id(Integer.toUnsignedLong(id))
                        .salaryGroup(SalaryGroups.E)
                        .companyCar(true)
                        .companyLaptop(CompanyLaptopTypes.Type2)
                        .build();
            case 5:
                return CEWorkingConditions.builder()
                        .id(Integer.toUnsignedLong(id))
                        .salaryGroup(SalaryGroups.F)
                        .companyCar(true)
                        .companyLaptop(CompanyLaptopTypes.Type1)
                        .build();
            case 6:
                return CEWorkingConditions.builder()
                        .id(Integer.toUnsignedLong(id))
                        .salaryGroup(SalaryGroups.G)
                        .companyCar(true)
                        .companyLaptop(CompanyLaptopTypes.Type4)
                        .build();
        }
    }

    public static WorkingConditionsResponse workingConditionsResponse(int id) {

        switch (id) {
            default:
                return WorkingConditionsResponse.builder().build();
            case 0:
                return WorkingConditionsResponse.builder()
                        .id(Integer.toUnsignedLong(id))
                        .salaryGroupResponse(SalaryGroupResponseTestData.salaryGroupResponse(SalaryGroups.A))
                        .companyCar(false)
                        .companyLaptopResponse(CompanyLaptopsResponseTestData.companyLaptopResponse(CompanyLaptopTypes.Type5))
                        .build();
            case 1:
                return WorkingConditionsResponse.builder()
                        .id(Integer.toUnsignedLong(id))
                        .salaryGroupResponse(SalaryGroupResponseTestData.salaryGroupResponse(SalaryGroups.B))
                        .companyCar(false)
                        .companyLaptopResponse(CompanyLaptopsResponseTestData.companyLaptopResponse(CompanyLaptopTypes.Type5))
                        .build();
            case 2:
                return WorkingConditionsResponse.builder()
                        .id(Integer.toUnsignedLong(id))
                        .salaryGroupResponse(SalaryGroupResponseTestData.salaryGroupResponse(SalaryGroups.C))
                        .companyCar(true)
                        .companyLaptopResponse(CompanyLaptopsResponseTestData.companyLaptopResponse(CompanyLaptopTypes.Type3))
                        .build();
            case 3:
                return WorkingConditionsResponse.builder()
                        .id(Integer.toUnsignedLong(id))
                        .salaryGroupResponse(SalaryGroupResponseTestData.salaryGroupResponse(SalaryGroups.D))
                        .companyCar(true)
                        .companyLaptopResponse(CompanyLaptopsResponseTestData.companyLaptopResponse(CompanyLaptopTypes.Type3))
                        .build();
            case 4:
                return WorkingConditionsResponse.builder()
                        .id(Integer.toUnsignedLong(id))
                        .salaryGroupResponse(SalaryGroupResponseTestData.salaryGroupResponse(SalaryGroups.E))
                        .companyCar(true)
                        .companyLaptopResponse(CompanyLaptopsResponseTestData.companyLaptopResponse(CompanyLaptopTypes.Type2))
                        .build();
            case 5:
                return WorkingConditionsResponse.builder()
                        .id(Integer.toUnsignedLong(id))
                        .salaryGroupResponse(SalaryGroupResponseTestData.salaryGroupResponse(SalaryGroups.F))
                        .companyCar(true)
                        .companyLaptopResponse(CompanyLaptopsResponseTestData.companyLaptopResponse(CompanyLaptopTypes.Type1))
                        .build();
            case 6:
                return WorkingConditionsResponse.builder()
                        .id(Integer.toUnsignedLong(id))
                        .salaryGroupResponse(SalaryGroupResponseTestData.salaryGroupResponse(SalaryGroups.G))
                        .companyCar(true)
                        .companyLaptopResponse(CompanyLaptopsResponseTestData.companyLaptopResponse(CompanyLaptopTypes.Type4))
                        .build();
        }
    }

}
