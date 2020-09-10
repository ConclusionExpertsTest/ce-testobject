package usersapplication.component;

import org.junit.jupiter.api.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import usersapplication.common.ReplaceCamelCase;
import usersapplication.domain.Users;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayNameGeneration(ReplaceCamelCase.class)
@DisplayName("Component / service tests")
public class ComponentTest extends RunCT {

    private final URI uri = UriComponentsBuilder.fromUriString("/api/users/").build().toUri();

    private Users newActiveUser1 = Users.builder()
            .firstName("test 1")
            .isActive(true)
            .lastName("de tester 1")
            .address("testweg 456")
            .occupation("what he does all day").build();

    private Users newActiveUser2 = Users.builder()
            .firstName("test 2")
            .isActive(false)
            .lastName("de tester 2")
            .address("testweg 789")
            .occupation("what he seems to be doing all day").build();

    @Nested
    public class givenAnEmptyUsersRepository {

        @BeforeEach
        public void setup() {
            usersRepository.deleteAll();
        }

        @Test
        public void thenGetAllActiveUsersShouldReturnAnEmptyJson() {
            ResponseEntity<String> response = restTemplate.getForEntity(uri, String.class);
            assertAll(
                    () -> assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK),
                    () -> assertThat(response.getBody()).isEqualTo("[]")
            );
        }

        @Test
        public void thenGetAllInactiveUsersShouldReturnAnEmptyJson() {
            ResponseEntity<String> response = restTemplate.getForEntity(uri + "inactive", String.class);
            assertAll(
                    () -> assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK),
                    () -> assertThat(response.getBody()).isEqualTo("[]")
            );
        }

        @Nested
        public class whenAnActiveUserIsAvailable {

            @BeforeEach
            public void setup() {
                usersRepository.saveAndFlush(newActiveUser1);
                newActiveUser1.setId(1L);
            }

            @Test
            public void thenGetAllUsersShouldReturnAJsonWithTheActiveUser() throws JsonProcessingException {
                ResponseEntity<String> response = restTemplate.getForEntity(uri, String.class);
                assertAll(
                        () -> assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK),
                        () -> assertThat(response.getBody())
                                .isEqualTo("[" + objectMapper.writeValueAsString(newActiveUser1) + "]")
                );
            }

            @Nested
            public class andWhenAnInactiveUserIsAvailable {

                @BeforeEach
                public void setup() {
                    usersRepository.saveAndFlush(newActiveUser2);
                    newActiveUser1.setId(3L);
                    newActiveUser2.setId(6L);
                }

                @Test
                public void thenGetAllUsersShouldReturnAJsonWithTheActiveUsers() {
                    ResponseEntity<String> response = restTemplate.getForEntity(uri, String.class);
                    assertAll(
                            () -> assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK),
                            () -> assertThat(response.getBody())
                                    .isEqualTo("[" + objectMapper.writeValueAsString(newActiveUser1) + "]")
                    );
                }

                @Test
                public void thenGetAllInactiveUsersShouldReturnAJsonWithTheInactiveUsers() {
                    ResponseEntity<String> response = restTemplate.getForEntity(uri + "inactive", String.class);
                    assertAll(
                            () -> assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK),
                            () -> assertThat(response.getBody())
                                    .isEqualTo("[" + objectMapper.writeValueAsString(newActiveUser2) + "]")
                    );
                }

            }

            @Nested
            public class andWhenTheActiveUserIsDeleted {

                @BeforeEach
                public void setup() {
                    usersRepository.deleteById(2L);
                }

                @Test
                public void thenGetAllActiveUsersShouldReturnAnEmptyJson() {
                    ResponseEntity<String> response = restTemplate.getForEntity(uri, String.class);
                    assertAll(
                            () -> assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK),
                            () -> assertThat(response.getBody()).isEqualTo("[]")
                    );
                }

            }

        }

        //TODO: PUT, POST and Actual DELETE!

    }

}
