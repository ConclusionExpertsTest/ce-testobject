package workingconditionapplication.unit.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.util.NestedServletException;
import org.springframework.web.util.UriComponentsBuilder;
import workingconditionapplication.domain.NewUpdateWorkingConditions;
import workingconditionapplication.domain.WorkingConditions;
import workingconditionapplication.enums.CompanyLaptopTypes;
import workingconditionapplication.enums.SalaryGroups;
import workingconditionapplication.repository.WorkingConditionsRepository;

import java.net.URI;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class WorkingConditionsControllerTest extends AbstractControllerTest {

    @Autowired
    private WorkingConditionsRepository workingConditionsRepository;

    private final URI uri = UriComponentsBuilder.fromUriString("/api/workingconditions/").build().toUri();

    @Test
    public void GetWorkingConditionsWithoutWorkingConditionsPresentAndAssertEmptyJsonAndStatusOk() throws Exception {
        // Arrange
        workingConditionsRepository.deleteAll();

        // Act & assert
        mockMvc.perform(MockMvcRequestBuilders.get(uri)).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("[]")));
    }

    @Test
    public void PostWorkingConditionAndAssertStatusCreated() throws Exception {
        // Arrange
        NewUpdateWorkingConditions newUpdateWorkingConditions = NewUpdateWorkingConditions.builder()
                .salaryGroup(SalaryGroups.C)
                .companyCar(true)
                .companyLaptop(CompanyLaptopTypes.Type3).build();

        // Act & assert
        mockMvc.perform(MockMvcRequestBuilders
                .post(uri)
                .content(objectMapper.writeValueAsString(newUpdateWorkingConditions))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    public void PostNewWorkingConditionWithFaultyRequestBodyAndExpectException() throws Exception {
        // Arrange
        NewUpdateWorkingConditions newUpdateWorkingConditions1 = NewUpdateWorkingConditions.builder()
                .companyCar(true)
                .companyLaptop(CompanyLaptopTypes.Type3).build();
        NewUpdateWorkingConditions newUpdateWorkingConditions2 = NewUpdateWorkingConditions.builder()
                .salaryGroup(SalaryGroups.C)
                .companyCar(true).build();
        NewUpdateWorkingConditions newUpdateWorkingConditions3 = NewUpdateWorkingConditions.builder().build();

        // Act & assert
        assertThrows(NestedServletException.class, () -> {
            mockMvc.perform(MockMvcRequestBuilders
                    .post(uri)
                    .content(objectMapper.writeValueAsString(newUpdateWorkingConditions1))
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON));
        });

        assertThrows(NestedServletException.class, () -> {
            mockMvc.perform(MockMvcRequestBuilders
                    .post(uri)
                    .content(objectMapper.writeValueAsString(newUpdateWorkingConditions2))
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON));
        });

        assertThrows(NestedServletException.class, () -> {
            mockMvc.perform(MockMvcRequestBuilders
                    .post(uri)
                    .content(objectMapper.writeValueAsString(newUpdateWorkingConditions3))
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON));
        });
    }

    @Test
    public void PutUpdatedWorkingConditionsAndExpectStatusOk() throws Exception {
        // Arrange
        WorkingConditions workingConditions = WorkingConditions.builder()
                .salaryGroup(SalaryGroups.A)
                .companyCar(false)
                .companyLaptop(CompanyLaptopTypes.Type5).build();

        workingConditionsRepository.save(workingConditions);
        Long id = workingConditionsRepository.findAll().get(0).getId();

        NewUpdateWorkingConditions updateWorkingConditions = NewUpdateWorkingConditions.builder()
                .salaryGroup(SalaryGroups.B)
                .companyCar(true)
                .companyLaptop(CompanyLaptopTypes.Type3).build();

        // Act & assert
        mockMvc.perform(MockMvcRequestBuilders
                .put(uri + String.valueOf(id))
                .content(objectMapper.writeValueAsString(updateWorkingConditions))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mockMvc.perform(MockMvcRequestBuilders.get(uri + String.valueOf(id))).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(objectMapper.writeValueAsString(WorkingConditions.builder()
                        .id(id)
                        .salaryGroup(updateWorkingConditions.getSalaryGroup())
                        .companyCar(updateWorkingConditions.isCompanyCar())
                        .companyLaptop(updateWorkingConditions.getCompanyLaptop()).build()))));
    }

    @Test
    public void PutUpdatedWorkingConditionsForNonExistingUserAndExpectStatusNotFound() throws Exception {
        // Arrange
        NewUpdateWorkingConditions updateWorkingConditions = NewUpdateWorkingConditions.builder()
                .salaryGroup(SalaryGroups.B)
                .companyCar(true)
                .companyLaptop(CompanyLaptopTypes.Type3).build();

        // Act & assert
        mockMvc.perform(MockMvcRequestBuilders
                .put(uri + "1001")
                .content(objectMapper.writeValueAsString(updateWorkingConditions))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void DeleteWorkingConditionsAndExpectStatusGone() throws Exception {
        // Arrange
        WorkingConditions workingConditions = WorkingConditions.builder()
                .salaryGroup(SalaryGroups.A)
                .companyCar(false)
                .companyLaptop(CompanyLaptopTypes.Type5).build();

        workingConditionsRepository.save(workingConditions);
        Long id = workingConditionsRepository.findAll().get(0).getId();

        // Act & assert
        mockMvc.perform(MockMvcRequestBuilders
                .delete(uri + String.valueOf(id)))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Working condition with ID '" + id + "' was successfully deleted.")));
    }

    @Test
    public void DeleteNonExistingWorkingConditionAndExpectStatusNotFound() throws Exception {
        // Act & assert
        mockMvc.perform(MockMvcRequestBuilders
                .delete(uri + "10001"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void PatchAndExpectNotImplemented() throws Exception {
        // Act & assert
        mockMvc.perform(MockMvcRequestBuilders
                .patch(uri + "10001"))
                .andExpect(status().isNotImplemented());
    }

}
