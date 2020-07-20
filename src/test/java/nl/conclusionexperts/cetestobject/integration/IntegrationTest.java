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
                CEUsers.builder().id(100L).isActive(true).firstName("Harry").lastName("Wit, de").address("Straat 2").occupation("TAE").build());
        String ceUser101Json = objectMapper.writeValueAsString(
                CEUsers.builder().id(101L).isActive(true).firstName("Jos").lastName("Lelijk").address("Weg 400").occupation("TAE").build());

        // Act
        ResponseEntity<String> response = restTemplate.getForEntity(uri, String.class);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo("[" + ceUser100Json + "," + ceUser101Json + "]");
    }

    @Test
    public void AssertInActiveCeUsersCanBeFound() throws JsonProcessingException {
        // Arrange
        String ceUser102Json = objectMapper.writeValueAsString(
                CEUsers.builder().id(102L).isActive(false).firstName("Pietje").lastName("Petersen").address("Steegje 2").occupation("FAB").build());

        // Act
        ResponseEntity<String> response = restTemplate.getForEntity(uri + "inactive", String.class);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo("[" + ceUser102Json + "]");
    }

    @Test
    public void AssertCeUser100CanBeFound() throws JsonProcessingException {
        // Arrange
        String ceUser100Json = objectMapper.writeValueAsString(
                CEUsers.builder().id(100L).isActive(true).firstName("Harry").lastName("Wit, de").address("Straat 2").occupation("TAE").build());

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
                CEUsers.builder().isActive(true).firstName("Joost").lastName("Mooiman").address("Weg 201").occupation("TAE").build());

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
                CEUsers.builder().id(100L).isActive(true).firstName("Joris").lastName("Wit, de").address("Straat 2").occupation("FABBER").build());

        // Act
        restTemplate.put(uri + "100", CEUsers.builder().isActive(true).firstName("Joris").lastName("Wit, de").address("Straat 2").occupation("FABBER").build());
        ResponseEntity<String> response = restTemplate.getForEntity(uri + "100", String.class);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(updatedUser);
    }

    @Test
    public void AssertCeUserCanBeDeleted() throws JsonProcessingException {
        // Arrange
        String ceUser100Json = objectMapper.writeValueAsString(
                CEUsers.builder().id(100L).isActive(true).firstName("Harry").lastName("Wit, de").address("Straat 2").occupation("TAE").build());
        String ceUser101Json = objectMapper.writeValueAsString(
                CEUsers.builder().id(100L).isActive(true).firstName("Jos").lastName("Lelijk").address("Weg 400").occupation("TAE").build());

        HttpEntity<String> request = new HttpEntity<>("", httpHeaders);

        // Act
        ResponseEntity<String> deleteResponse = restTemplate.exchange(uri + "101", HttpMethod.DELETE, request, String.class);
        ResponseEntity<String> getResponse = restTemplate.getForEntity(uri, String.class);

        // Assert
        assertThat(deleteResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(deleteResponse.getBody()).isEqualTo("CE User with ID '101' was successfully deleted.");
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(getResponse.getBody()).isNotEqualTo("[" + ceUser100Json + "," + ceUser101Json + "]");
        assertThat(getResponse.getBody()).isEqualTo("[" + ceUser100Json + "]");
    }

    @Test
    public void Assert404NotFoundIfCeUserCanNotBeFound() {
        // Arrange
        HttpEntity<String> request = new HttpEntity<>("", httpHeaders);

        // Act
        ResponseEntity<String> getResponse = restTemplate.getForEntity(uri + "103", String.class);
        ResponseEntity<String> deleteResponse = restTemplate.exchange(uri + "103", HttpMethod.DELETE, request, String.class);

        // Assert
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(deleteResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    public void AssertBadRequestWhenPostingWithFaultyJson() {
        // Arrange
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>("{newUser}", httpHeaders);

        // Act
        ResponseEntity<String> response = restTemplate.postForEntity(uri, request, String.class);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }
}
