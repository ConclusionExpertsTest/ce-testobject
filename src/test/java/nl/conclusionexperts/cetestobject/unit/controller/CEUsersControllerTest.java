package nl.conclusionexperts.cetestobject.unit.controller;

import nl.conclusionexperts.cetestobject.domain.CEUsers;
import nl.conclusionexperts.cetestobject.repository.CEUsersRepository;
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

public class CEUsersControllerTest extends AbstractControllerTest {

    @Autowired
    private CEUsersRepository ceUsersRepository;

    private final URI uri = UriComponentsBuilder.fromUriString("/api/users/").build().toUri();

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
        CEUsers testUser = CEUsers.builder().isActive(true).firstName("Harry").lastName("Wit, de").address("Straat 2").occupation("TAE").build();

        ceUsersRepository.save(testUser);

        // Act & assert
        mockMvc.perform(MockMvcRequestBuilders.get(uri)).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(testUser.getId().intValue())))
                .andExpect(jsonPath("$[0].firstName", is(testUser.getFirstName())))
                .andExpect(jsonPath("$[0].lastName", is(testUser.getLastName())))
                .andExpect(jsonPath("$[0].address", is(testUser.getAddress())))
                .andExpect(jsonPath("$[0].occupation", is(testUser.getOccupation())));
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
        CEUsers testUser1 = CEUsers.builder().isActive(true).firstName("Harry").lastName("Wit, de").address("Straat 2").occupation("TAE").build();

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
    }

    @Test
    @Sql("data.sql")
    public void PutUpdatedCeUsersAndExpectStatusOk() throws Exception {
        // Arrange
        CEUsers testUser = CEUsers.builder().isActive(true).firstName("Harry").lastName("Wit, de").address("Straat 2").occupation("TAE").build();
        CEUsers updatedTestUser = CEUsers.builder().isActive(true).firstName("Harry").lastName("Wit, de").address("Straat 20").occupation("FAB").build();

        ceUsersRepository.save(testUser);

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

}
