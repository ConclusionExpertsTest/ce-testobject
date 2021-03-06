package CeUsersTests;

import CeUsersTests.controller.CeUsersEndpoints;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.DockerComposeContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import usersapplication.domain.NewUpdateUsers;
import usersapplication.domain.Users;

import java.io.File;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
public class CeUsersTest {

    protected static ObjectMapper objectMapper = new ObjectMapper();

    private static String getProjectDir() {
        String projectDir = System.getProperty("user.dir");
        projectDir = projectDir.replace("e2e-test", "");
        return projectDir;
    }

    @Container
    public static DockerComposeContainer ceTestobject =
            new DockerComposeContainer(new File(getProjectDir() + "docker-compose.yaml"))
                    .withExposedService("ce-users-application_1", 8082,
                            Wait.forHttp("/api/users/")
                                    .forStatusCode(200));

    @Test
    public void CrudActions() throws JsonProcessingException {
        // Arrange
        NewUpdateUsers postUsers1 = NewUpdateUsers.builder().isActive(true).firstName("Harry")
                .lastName("Wit, de").address("Straat 2").occupation("TAE").workingConditionsId(1).build();
        NewUpdateUsers postUsers2 = NewUpdateUsers.builder().isActive(true).firstName("Jos")
                .lastName("Lelijk").address("Weg 400").occupation("TAE").workingConditionsId(1).build();
        NewUpdateUsers postUsers3 = NewUpdateUsers.builder().isActive(false).firstName("Jos")
                .lastName("Lelijk").address("Weg 400").occupation("TAE").workingConditionsId(1).build();
        Users users1 = Users.builder().id(1L).isActive(true).firstName("Harry")
                .lastName("Wit, de").address("Straat 2").occupation("TAE").workingConditionsId(1).build();
        Users users2 = Users.builder().id(2L).isActive(true).firstName("Jos")
                .lastName("Lelijk").address("Weg 400").occupation("TAE").workingConditionsId(1).build();
        Users users3 = Users.builder().id(3L).isActive(false).firstName("Jos")
                .lastName("Lelijk").address("Weg 400").occupation("TAE").workingConditionsId(1).build();
        NewUpdateUsers putUpdatedUsers1 = NewUpdateUsers.builder().isActive(true).firstName("Johan")
                .lastName("Rode, de").address("Steegje 22").occupation("FAB").workingConditionsId(1).build();
        Users updatedUsers1 = Users.builder().id(1L).isActive(true).firstName("Johan")
                .lastName("Rode, de").address("Steegje 22").occupation("FAB").workingConditionsId(1).build();

        String blabla = objectMapper.writeValueAsString(postUsers1);

        // Act - POST
        Response postResponse1 = CeUsersEndpoints.postNewCeUser(objectMapper.writeValueAsString(postUsers1));
        Response postResponse2 = CeUsersEndpoints.postNewCeUser(objectMapper.writeValueAsString(postUsers2));
        Response postResponse3 = CeUsersEndpoints.postNewCeUser(objectMapper.writeValueAsString(postUsers3));

        // Assert - POST
        assertThat(postResponse1.statusCode()).isEqualTo(HttpStatus.SC_CREATED);
        assertThat(postResponse2.statusCode()).isEqualTo(HttpStatus.SC_CREATED);
        assertThat(postResponse3.statusCode()).isEqualTo(HttpStatus.SC_CREATED);

        // Act - GET
        Response getAllActiveResponse = CeUsersEndpoints.getAllActive();
        Response getAllInactiveResponse = CeUsersEndpoints.getAllInactive();
        Response getResponse1 = CeUsersEndpoints.getById(users1.getId().toString());
        Response getResponse2 = CeUsersEndpoints.getById(users2.getId().toString());
        Response getResponse3 = CeUsersEndpoints.getById(users3.getId().toString());

        // Assert - GET
        assertThat(getAllActiveResponse.statusCode()).isEqualTo(HttpStatus.SC_OK);
        assertThat(getAllActiveResponse.body().print()).isNotEqualTo("[" +
                objectMapper.writeValueAsString(users1) + "," +
                objectMapper.writeValueAsString(users2) + "," +
                objectMapper.writeValueAsString(users3 + "]"));
        assertThat(getAllActiveResponse.body().print()).isEqualTo("[" +
                objectMapper.writeValueAsString(users1) + "," +
                objectMapper.writeValueAsString(users2) + "]");
        assertThat(getAllInactiveResponse.statusCode()).isEqualTo(HttpStatus.SC_OK);
        assertThat(getAllInactiveResponse.body().print()).isEqualTo("[" +
                objectMapper.writeValueAsString(users3) + "]");
        assertThat(getResponse1.statusCode()).isEqualTo(HttpStatus.SC_OK);
        assertThat(getResponse1.body().print()).isEqualTo(objectMapper.writeValueAsString(users1));
        assertThat(getResponse2.statusCode()).isEqualTo(HttpStatus.SC_OK);
        assertThat(getResponse2.body().print()).isEqualTo(objectMapper.writeValueAsString(users2));
        assertThat(getResponse3.statusCode()).isEqualTo(HttpStatus.SC_OK);
        assertThat(getResponse3.body().print()).isEqualTo(objectMapper.writeValueAsString(users3));

        // Act - PUT & GET (by id)
        Response putResponse = CeUsersEndpoints.putCeUser("1", objectMapper.writeValueAsString(putUpdatedUsers1));
        Response getUpdatedResponse1 = CeUsersEndpoints.getById(users1.getId().toString());

        // Assert - PUT & GET (by id)
        assertThat(putResponse.statusCode()).isEqualTo(HttpStatus.SC_OK);
        assertThat(getUpdatedResponse1.statusCode()).isEqualTo(HttpStatus.SC_OK);
        assertThat(getUpdatedResponse1.body().print()).isEqualTo(objectMapper.writeValueAsString(updatedUsers1));

        // Act - DELETE & GET
        Response deleteResponse1 = CeUsersEndpoints.deleteCeUser("1");
        Response deleteResponse2 = CeUsersEndpoints.deleteCeUser("2");
        Response getAllActiveAfterDeleteResponse = CeUsersEndpoints.getAllActive();

        // Assert - DELETE & GET
        assertThat(deleteResponse1.statusCode()).isEqualTo(HttpStatus.SC_OK);
        assertThat(deleteResponse1.body().print()).isEqualTo("{\"succesMessage\": \"CE User with ID '1' was successfully deleted.\"}");
        assertThat(deleteResponse2.statusCode()).isEqualTo(HttpStatus.SC_OK);
        assertThat(deleteResponse2.body().print()).isEqualTo("{\"succesMessage\": \"CE User with ID '2' was successfully deleted.\"}");
        assertThat(getAllActiveAfterDeleteResponse.statusCode()).isEqualTo(HttpStatus.SC_OK);
        assertThat(getAllActiveAfterDeleteResponse.body().print()).isEqualTo("[]");
    }

}
