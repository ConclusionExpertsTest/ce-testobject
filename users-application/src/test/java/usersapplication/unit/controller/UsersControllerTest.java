package usersapplication.unit.controller;

import org.junit.jupiter.api.DisplayName;
import usersapplication.domain.NewUpdateUsers;
import usersapplication.domain.Users;
import usersapplication.repository.UsersRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.util.NestedServletException;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("UsersController tests")
public class UsersControllerTest extends AbstractControllerTest {

    @Autowired
    private UsersRepository usersRepository;

    private final URI uri = UriComponentsBuilder.fromUriString("/api/users/").build().toUri();

    @Test
    @DisplayName("Get users with no users present and assert empty JSON and HtppStatus 'OK'")
    public void GetUsersWithNoUsersPresentAndAssertEmptyJsonAndStatusOk() throws Exception {
        // Arrange
        usersRepository.deleteAll();

        // Act & assert
        mockMvc.perform(MockMvcRequestBuilders.get(uri)).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("[]")));
    }

    @Test
    @DisplayName("Get all active users and expect HttpStatus 'OK'")
    public void GetActiveUsersAndExpectStatusOk() throws Exception {
        // Arrange
        Users testUser = Users.builder().isActive(true).firstName("Harry").lastName("Wit, de")
                .address("Straat 2").occupation("TAE").workingConditionsId(1).build();

        usersRepository.save(testUser);

        // Act & assert
        String resultJson = mockMvc.perform(MockMvcRequestBuilders.get(uri)).andDo(print())
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

        assertThat(resultJson).isEqualTo("[" + objectMapper.writeValueAsString(testUser) + "]");

    }

    @Test
    public void GetInactiveUsersAndExpectStatusOk() throws Exception {
        // Arrange
        Users testUser1 = Users.builder().isActive(true).firstName("Harry").lastName("Wit, de").address("Straat 2").occupation("TAE").build();
        Users testUser2 = Users.builder().isActive(false).firstName("Jos").lastName("Lelijk").address("Weg 400").occupation("TAE").build();

        usersRepository.save(testUser1);
        usersRepository.save(testUser2);

        // Act & assert
        mockMvc.perform(MockMvcRequestBuilders.get(uri + "inactive")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(testUser2.getId().intValue())));
    }

    @Test
    public void PostUsersAndExpectStatusCreated() throws Exception {
        // Arrange
        NewUpdateUsers testUser1 = NewUpdateUsers.builder().isActive(true).firstName("Harry").lastName("Wit, de").address("Straat 2").occupation("TAE").build();

        // Act & assert
        mockMvc.perform(MockMvcRequestBuilders
                .post(uri)
                .content(objectMapper.writeValueAsString(testUser1))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    public void PostNewUsersWithFaultyRequestBodyAndExpectException() {
        // Arrange
        Users testUser1 = Users.builder().build();
        Users testUser2 = Users.builder().firstName("Hoi").lastName("te Laat").build();
        Users testUser3 = Users.builder().address("Straatweg 67").build();
        Users testUser4 = Users.builder().occupation("TAB").build();
        Users testUser5 = Users.builder().isActive(true).firstName("Harry").lastName("Wit, de").address("Straat 2").build();
        Users testUser6 = Users.builder().isActive(true).firstName("Harry").lastName("Wit, de").address("Straat 2").workingConditionsId(100).build();

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
    public void PutUpdatedUsersAndExpectStatusOk() throws Exception {
        // Arrange
        Users testUser = Users.builder().isActive(true).firstName("Harry").lastName("Wit, de").address("Straat 2").occupation("TAE").workingConditionsId(1).build();
        Users updatedTestUser = Users.builder().isActive(true).firstName("Harry").lastName("Wit, de").address("Straat 20").occupation("FAB").workingConditionsId(1).build();

        usersRepository.save(testUser);

        // Act & assert
        mockMvc.perform(MockMvcRequestBuilders
                .put(uri + "101")
                .content(objectMapper.writeValueAsString(updatedTestUser))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mockMvc.perform(MockMvcRequestBuilders.get(uri + "101")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(101)))
                .andExpect(jsonPath("$.firstName", is(updatedTestUser.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(updatedTestUser.getLastName())))
                .andExpect(jsonPath("$.address", is(updatedTestUser.getAddress())))
                .andExpect(jsonPath("$.occupation", is(updatedTestUser.getOccupation())));
    }

    @Test
    public void PutUpdatedUsersForNonExistingUserAndExpectStatusNotFound() throws Exception {
        // Arrange
        NewUpdateUsers updatedTestUser = NewUpdateUsers.builder().isActive(true).firstName("Harry").lastName("Wit, de").address("Straat 20").occupation("FAB").workingConditionsId(1).build();

        // Act & assert
        mockMvc.perform(MockMvcRequestBuilders
                .put(uri + "1")
                .content(objectMapper.writeValueAsString(updatedTestUser))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void DeleteUsersAndExpectStatusGone() throws Exception {
        // Arrange
        Users testUser = Users.builder().isActive(true).firstName("Harry").lastName("Wit, de").address("Straat 2").occupation("TAE").build();

        usersRepository.save(testUser);
        Long id = usersRepository.findAll().get(0).getId();

        // Act & assert
        mockMvc.perform(MockMvcRequestBuilders
                .delete(uri + String.valueOf(id)))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("CE User with ID '" + id + "' was successfully deleted.")));
    }

    @Test
    public void DeleteNonExistingUsersAndExpectStatusNotFound() throws Exception {
        // Act & assert
        mockMvc.perform(MockMvcRequestBuilders
                .delete(uri + "1001"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void PatchAndExpectNotImplemented() throws Exception {
        // Act & assert
        mockMvc.perform(MockMvcRequestBuilders
                .patch(uri + "1"))
                .andExpect(status().isNotImplemented());
    }

}
