package usersapplication.unit.repository;

import org.junit.jupiter.api.*;
import org.springframework.dao.DataIntegrityViolationException;
import usersapplication.common.ReplaceCamelCase;
import usersapplication.domain.Users;
import usersapplication.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
@DisplayNameGeneration(ReplaceCamelCase.class)
@DisplayName("Users repository tests")
class UsersRepositoryTest {

    @Autowired
    private UsersRepository usersRepository;

    private Users newActiveUser = Users.builder()
            .firstName("test 1")
            .isActive(true)
            .lastName("de tester 1")
            .address("testweg 456")
            .occupation("what he does all day").build();

    @Nested
    public class givenAnEmptyUsersRepository {

        @BeforeEach
        public void setup() {
            usersRepository.deleteAll();
        }

        @Test
        public void thenSearchingForAllUsersShouldReturnAnEmptyCollection() {
            assertThat(usersRepository.findAll()).isEmpty();
        }

        @Test
        public void thenSearchingForAllActiveUsersShouldReturnAnEmptyCollection() {
            assertThat(usersRepository.findAllByIsActive()).isEmpty();
        }

        @Test
        public void thenSearchingForAllInactiveUsersShouldReturnAnEmptyCollection() {
            assertThat(usersRepository.findAllByIsInActive()).isEmpty();
        }

        @Test
        public void thenAddingAnEmptyUserWillResultInException() {
            assertThrows(DataIntegrityViolationException.class, () -> usersRepository.save(Users.builder().build()));
        }

        @Nested
        public class whenAnActiveUserIsAdded {

            @BeforeEach
            public void setup() {
                usersRepository.saveAndFlush(newActiveUser);
            }

            @Test
            public void thenSearchingForAllUsersShouldReturnACollectionWithOneUser() {
                assertThat(usersRepository.findAll())
                        .hasSize(1)
                        .hasAtLeastOneElementOfType(Users.class);
            }

            @Test
            public void thenSearchingForAllActiveUsersShouldReturnACollectionWithOneUser() {
                assertThat(usersRepository.findAllByIsActive())
                        .hasSize(1)
                        .hasAtLeastOneElementOfType(Users.class);
            }

            @Test
            public void thenSearchingForAllInactiveUsersShouldReturnAnEmptyCollection() {
                assertThat(usersRepository.findAllByIsInActive()).isEmpty();
            }

            @Test
            public void thenSearchingForUserWithIdZeroShouldReturnAUser() {
                assertThat(usersRepository.findById(1L)).isNotEmpty().containsInstanceOf(Users.class);
            }

            @Test
            public void thenSearchingForUserWithIdOneShouldReturnAnEmptyUser() {
                assertThat(usersRepository.findById(2L)).isEmpty();
            }

            @Test
            @Disabled
            public void thenTheUserCanBeDeleted() {
                usersRepository.deleteById(1L);
                assertThat(usersRepository.findById(1L)).isEmpty();
            }

        }

    }

}