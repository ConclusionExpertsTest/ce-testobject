package usersapplication.unit.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import nl.conclusionexperts.workingconditionapplication.enums.CompanyLaptopTypes;
import nl.conclusionexperts.workingconditionapplication.enums.SalaryGroups;
import org.apache.http.HttpHeaders;
import org.junit.jupiter.api.BeforeAll;
import usersapplication.domain.CEUsers;
import usersapplication.helpers.CeUsersResponseTestData;
import usersapplication.helpers.workingconditions.WorkingConditionsResponseTestData;
import usersapplication.helpers.workingconditions.companylaptops.CompanyLaptopsResponseTestData;
import usersapplication.helpers.workingconditions.salarygroups.SalaryGroupResponseTestData;
import usersapplication.repository.CEUsersRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.util.NestedServletException;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.assertj.core.api.Assertions.assertThat;

public class CEUsersControllerTest extends AbstractControllerTest {

    @Autowired
    private CEUsersRepository ceUsersRepository;

    private final URI uri = UriComponentsBuilder.fromUriString("/api/users/").build().toUri();

    static WireMockServer wireMockServer;

    @BeforeAll
    public static void setup() throws JsonProcessingException {
        wireMockServer = new WireMockServer(8083);
        wireMockServer.start();
        setupStub();
    }

    @Test
    public void GetCeUsersWithNoCeUsersPresent() throws Exception {
        // Act & assert
        mockMvc.perform(MockMvcRequestBuilders.get(uri)).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("[]")));
    }

    @Test
    public void GetActiveCeUsersAndExpectStatusOk() throws Exception {
        // Arrange
        CEUsers testUser = CEUsers.builder().isActive(true).firstName("Harry").lastName("Wit, de")
                .address("Straat 2").occupation("TAE").workingConditionsId(1).build();

        ceUsersRepository.save(testUser);

        // Act & assert
        String resultJson = mockMvc.perform(MockMvcRequestBuilders.get(uri)).andDo(print())
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

        assertThat(resultJson).isEqualTo("[" + objectMapper.writeValueAsString(
                CeUsersResponseTestData.ceUsersResponse(testUser)) + "]");

    }

    @Test
    public void GetInactiveCeUsersAndExpectStatusOk() throws Exception {
        // Arrange
        CEUsers testUser1 = CEUsers.builder().isActive(true).firstName("Harry").lastName("Wit, de").address("Straat 2").occupation("TAE").build();
        CEUsers testUser2 = CEUsers.builder().isActive(false).firstName("Jos").lastName("Lelijk").address("Weg 400").occupation("TAE").build();

        ceUsersRepository.save(testUser1);
        ceUsersRepository.save(testUser2);

        // Act & assert
        mockMvc.perform(MockMvcRequestBuilders.get(uri + "inactive")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(testUser2.getId().intValue())));
    }

    @Test
    public void PostCeUsersAndExpectStatusCreated() throws Exception {
        // Arrange
        CEUsers testUser1 = CEUsers.builder().isActive(true).firstName("Harry").lastName("Wit, de").address("Straat 2").occupation("TAE").workingConditionsId(1).build();

        // Act & assert
        mockMvc.perform(MockMvcRequestBuilders
                .post(uri)
                .content(objectMapper.writeValueAsString(testUser1))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    public void PostNewCeUsersWithFaultyRequestBodyAndExpectException() {
        // Arrange
        CEUsers testUser1 = CEUsers.builder().build();
        CEUsers testUser2 = CEUsers.builder().firstName("Hoi").lastName("te Laat").build();
        CEUsers testUser3 = CEUsers.builder().address("Straatweg 67").build();
        CEUsers testUser4 = CEUsers.builder().occupation("TAB").build();
        CEUsers testUser5 = CEUsers.builder().isActive(true).firstName("Harry").lastName("Wit, de").address("Straat 2").build();
        CEUsers testUser6 = CEUsers.builder().isActive(true).firstName("Harry").lastName("Wit, de").address("Straat 2").workingConditionsId(100).build();

        // Act & assert
        assertThrows(NestedServletException.class, () -> {
            mockMvc.perform(MockMvcRequestBuilders
                    .post(uri)
                    .content(objectMapper.writeValueAsString(testUser1))
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON));
        });

        assertThrows(NestedServletException.class, () -> {
            mockMvc.perform(MockMvcRequestBuilders
                    .post(uri)
                    .content(objectMapper.writeValueAsString(testUser2))
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON));
        });

        assertThrows(NestedServletException.class, () -> {
            mockMvc.perform(MockMvcRequestBuilders
                    .post(uri)
                    .content(objectMapper.writeValueAsString(testUser3))
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON));
        });

        assertThrows(NestedServletException.class, () -> {
            mockMvc.perform(MockMvcRequestBuilders
                    .post(uri)
                    .content(objectMapper.writeValueAsString(testUser4))
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON));
        });

        assertThrows(NestedServletException.class, () -> {
            mockMvc.perform(MockMvcRequestBuilders
                    .post(uri)
                    .content(objectMapper.writeValueAsString(testUser5))
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON));
        });

        assertThrows(NestedServletException.class, () -> {
            mockMvc.perform(MockMvcRequestBuilders
                    .post(uri)
                    .content(objectMapper.writeValueAsString(testUser6))
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON));
        });
    }

    @Test
    @Sql("data.sql")
    public void PutUpdatedCeUsersAndExpectStatusOk() throws Exception {
        // Arrange
        CEUsers testUser = CEUsers.builder().isActive(true).firstName("Harry").lastName("Wit, de").address("Straat 2").occupation("TAE").workingConditionsId(1).build();
        CEUsers updatedTestUser = CEUsers.builder().isActive(true).firstName("Harry").lastName("Wit, de").address("Straat 20").occupation("FAB").workingConditionsId(1).build();

        ceUsersRepository.save(testUser);

        // Act & assert
        mockMvc.perform(MockMvcRequestBuilders
                .put(uri + "101")
                .content(objectMapper.writeValueAsString(updatedTestUser))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        updatedTestUser.setId(101L);

        String resultJson = mockMvc.perform(MockMvcRequestBuilders.get(uri + "101")).andDo(print())
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

        assertThat(resultJson).isEqualTo(objectMapper.writeValueAsString(
                CeUsersResponseTestData.ceUsersResponse(updatedTestUser)));
    }

    @Test
    public void PutUpdatedCeUsersForNonExistingCeUserAndExpectStatusNotFound() throws Exception {
        // Arrange
        CEUsers updatedTestUser = CEUsers.builder().isActive(true).firstName("Harry").lastName("Wit, de").address("Straat 20").occupation("FAB").build();

        // Act & assert
        mockMvc.perform(MockMvcRequestBuilders
                .put(uri + "1")
                .content(objectMapper.writeValueAsString(updatedTestUser))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void DeleteCeUsersAndExpectStatusGone() throws Exception {
        // Arrange
        CEUsers testUser = CEUsers.builder().isActive(true).firstName("Harry").lastName("Wit, de").address("Straat 2").occupation("TAE").build();

        ceUsersRepository.save(testUser);

        // Act & assert
        mockMvc.perform(MockMvcRequestBuilders
                .delete(uri + "1"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("CE User with ID '1' was successfully deleted.")));
    }

    @Test
    public void DeleteNonExistingCeUsersAndExpectStatusNotFound() throws Exception {
        // Act & assert
        mockMvc.perform(MockMvcRequestBuilders
                .delete(uri + "1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void PatchAndExpectNotImplemented() throws Exception {
        // Act & assert
        mockMvc.perform(MockMvcRequestBuilders
                .patch(uri + "1"))
                .andExpect(status().isNotImplemented());
    }

    public static void setupStub() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();

        wireMockServer.stubFor(get(urlEqualTo("/api/workingconditions/1"))
                .willReturn(aResponse().withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON.toString())
                        .withStatus(200).withBody(objectMapper.writeValueAsString(
                                WorkingConditionsResponseTestData.ceWorkingConditions(1)))));

        wireMockServer.stubFor(get(urlEqualTo("/api/workingconditions/companylaptop/Type5"))
                .willReturn(aResponse().withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON.toString())
                        .withStatus(200).withBody(objectMapper.writeValueAsString(
                                CompanyLaptopsResponseTestData.companyLaptopResponse(CompanyLaptopTypes.Type5)))));

        wireMockServer.stubFor(get(urlEqualTo("/api/workingconditions/salarygroups/B"))
                .willReturn(aResponse().withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON.toString())
                        .withStatus(200).withBody(objectMapper.writeValueAsString(
                                SalaryGroupResponseTestData.salaryGroupResponse(SalaryGroups.B)))));
    }

}
