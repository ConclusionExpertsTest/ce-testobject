package workingconditionapplication.unit.controller.companyLaptop;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.util.UriComponentsBuilder;
import workingconditionapplication.repository.companylaptop.CompanyLaptopRepository;
import workingconditionapplication.unit.controller.AbstractControllerTest;

import java.net.URI;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CompanyLaptopControllerTest extends AbstractControllerTest {

    @Autowired
    private CompanyLaptopRepository companyLaptopRepository;

    private final URI uri = UriComponentsBuilder.fromUriString("/api/workingconditions/companylaptop").build().toUri();

    public void GetWorkingConditionsWithoutCompanyLaptopsPresentAndAssertEmptyJsonAndStatusOk() throws Exception {
        // Arrange
        companyLaptopRepository.deleteAll();

        // Act & assert
        mockMvc.perform(MockMvcRequestBuilders.get(uri)).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("[]")));
    }

}
