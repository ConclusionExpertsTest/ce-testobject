package usersapplication.mappers;

import nl.conclusionexperts.workingconditionapplication.domain.CEWorkingConditions;
import nl.conclusionexperts.workingconditionapplication.domain.companylaptop.CompanyLaptop;
import nl.conclusionexperts.workingconditionapplication.domain.salary.SalaryGroup;
import nl.conclusionexperts.workingconditionapplication.enums.CompanyLaptopTypes;
import nl.conclusionexperts.workingconditionapplication.enums.SalaryGroups;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import usersapplication.domain.CEUsers;
import usersapplication.domain.response.CeUsersResponse;
import usersapplication.domain.response.workingcondition.WorkingConditionsResponse;
import usersapplication.domain.response.workingcondition.companylaptop.CompanyLaptopResponse;
import usersapplication.domain.response.workingcondition.salary.SalaryGroupResponse;

public class CeUsersResponseMapper {

    private static final RestTemplate restTemplate = new RestTemplate();

    private static String workingConditionsUrl = "http://localhost:8083/api/workingconditions";

    private static String companyLaptopsUrl = "http://localhost:8083/api/workingconditions/companylaptop";

    private static String salaryGroupsUrl = "http://localhost:8083/api/workingconditions/salarygroups";

    public static CeUsersResponse getCeUsersResponse(CEUsers ceUser) {
        return CeUsersResponse.builder()
                .workingConditionsResponse(getWorkingCondition(ceUser))
                .id(ceUser.getId())
                .isActive(ceUser.isActive())
                .firstName(ceUser.getFirstName())
                .lastName(ceUser.getLastName())
                .address(ceUser.getAddress())
                .occupation(ceUser.getOccupation()).build();
    }

    protected static WorkingConditionsResponse getWorkingCondition(CEUsers ceUser) {
        // TODO Awaitility !!
        ResponseEntity<CEWorkingConditions> responseEntity = restTemplate.getForEntity(
                workingConditionsUrl + "/" + ceUser.getWorkingConditionsId(), CEWorkingConditions.class);

        CEWorkingConditions ceWorkingConditions = responseEntity.getBody();

        return WorkingConditionsResponse.builder()
                .id((long) ceUser.getWorkingConditionsId())
                .companyLaptopResponse(getCompanyLaptop(ceWorkingConditions.getCompanyLaptop()))
                .companyCar(ceWorkingConditions.isCompanyCar())
                .salaryGroupResponse(getSalaryGroup(ceWorkingConditions.getSalaryGroup()))
                .build();
    }

    protected static CompanyLaptopResponse getCompanyLaptop(CompanyLaptopTypes companyLaptopTypes) {
        // TODO Awaitility !!
        ResponseEntity<CompanyLaptop> responseEntity =
                restTemplate.getForEntity(companyLaptopsUrl + "/" + companyLaptopTypes.toString(), CompanyLaptop.class);

        CompanyLaptop companyLaptop = responseEntity.getBody();

        return CompanyLaptopResponse.builder()
                .id(companyLaptop.getId())
                .available(companyLaptop.isAvailable())
                .brandAndType(companyLaptop.getBrandAndType())
                .companyLaptopTypes(companyLaptop.getCompanyLaptopTypes())
                .diskspace(companyLaptop.getDiskspace())
                .memory(companyLaptop.getMemory())
                .firstOperatingSystem(companyLaptop.getFirstOperatingSystem())
                .secondOperatingSystem(companyLaptop.getSecondOperatingSystem()).build();
    }

    protected static SalaryGroupResponse getSalaryGroup(SalaryGroups salaryGroups) {
        // TODO Awaitility !!
        ResponseEntity<SalaryGroup> responseEntity =
                restTemplate.getForEntity(salaryGroupsUrl + "/" + salaryGroups.toString(), SalaryGroup.class);

        SalaryGroup salaryGroup = responseEntity.getBody();

        return SalaryGroupResponse.builder()
                .id(salaryGroup.getId())
                .salaryGroupCode(salaryGroup.getSalaryGroupCode())
                .minAmount(salaryGroup.getMinAmount())
                .maxAmount(salaryGroup.getMaxAmount()).build();
    }

}
