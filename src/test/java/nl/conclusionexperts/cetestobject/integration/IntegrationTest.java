package nl.conclusionexperts.cetestobject.integration;

import com.fasterxml.jackson.core.JsonProcessingException;
import nl.conclusionexperts.cetestobject.domain.CEUsers;
import org.junit.jupiter.api.Test;
import org.springframework.http.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

import static org.assertj.core.api.Assertions.assertThat;

public class IntegrationTest extends RunIT {

    private final URI uri = UriComponentsBuilder.fromUriString("/api/users/").build().toUri();

    @Test
    public void AssertCeUsersCanBeFound() throws JsonProcessingException {
        // Arrange
        String ceUser100Json = objectMapper.writeValueAsString(
                CEUsers.builder().id(100L).firstName("Harry").lastName("Wit, de").address("Straat 2").occupation("TAE").build());
        String ceUser101Json = objectMapper.writeValueAsString(
                CEUsers.builder().id(101L).firstName("Jos").lastName("Lelijk").address("Weg 400").occupation("TAE").build());

        // Act
        ResponseEntity<String> response = restTemplate.getForEntity(uri, String.class);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo("[" + ceUser100Json + "," + ceUser101Json + "]");
    }

    @Test
    public void AssertCeUser100CanBeFound() throws JsonProcessingException {
        // Arrange
        String ceUser100Json = objectMapper.writeValueAsString(
                CEUsers.builder().id(100L).firstName("Harry").lastName("Wit, de").address("Straat 2").occupation("TAE").build());

        // Act
        ResponseEntity<String> response = restTemplate.getForEntity(uri + "100", String.class);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(ceUser100Json);
    }

    @Test
    public void AssertCeUserCanBeCreated() throws JsonProcessingException {
        // Arrange
        String newUser = objectMapper.writeValueAsString(
                CEUsers.builder().firstName("Joost").lastName("Mooiman").address("Weg 201").occupation("TAE").build());

        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(newUser, httpHeaders);

        // Act
        ResponseEntity<String> response = restTemplate.postForEntity(uri, request, String.class);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    public void AssertCeUserCanBeUpdated() throws JsonProcessingException {
        // Arrange
        String updatedUser = objectMapper.writeValueAsString(
                CEUsers.builder().id(100L).firstName("Joris").lastName("Wit, de").address("Straat 2").occupation("FABBER").build());

        // Act
        restTemplate.put(uri + "100", CEUsers.builder().firstName("Joris").lastName("Wit, de").address("Straat 2").occupation("FABBER").build());
        ResponseEntity<String> response = restTemplate.getForEntity(uri + "100", String.class);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(updatedUser);
    }

    @Test
    public void AssertCeUserCanBeDeleted() throws JsonProcessingException {
        // Arrange
        String ceUser100Json = objectMapper.writeValueAsString(
                CEUsers.builder().id(100L).firstName("Harry").lastName("Wit, de").address("Straat 2").occupation("TAE").build());
        String ceUser101Json = objectMapper.writeValueAsString(
                CEUsers.builder().id(100L).firstName("Jos").lastName("Lelijk").address("Weg 400").occupation("TAE").build());

        // Act
        restTemplate.delete(uri + "101");
        ResponseEntity<String> response = restTemplate.getForEntity(uri, String.class);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotEqualTo("[" + ceUser100Json + "," + ceUser101Json + "]");
        assertThat(response.getBody()).isEqualTo("[" + ceUser100Json + "]");
    }

}
