package usersapplication.unit.repository;

import usersapplication.domain.Users;
import usersapplication.repository.UsersRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.jdbc.Sql;

import java.util.Collection;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
class UsersRepositoryTest {

    @Autowired
    private UsersRepository usersRepository;

    @Test
    @Sql("data.sql")
    public void findCeUserById() {
        // Act
        Optional<Users> ceUsers = usersRepository.findById(100L);

        // Assert
        assertThat(ceUsers).isNotNull();
    }

    @Test
    public void createUserAndAssertIdCountingAnsResult() {
        // Arrange
        Users newUsers1 = Users.builder()
                .firstName("test 1")
                .isActive(true)
                .lastName("de tester 1")
                .address("testweg 456")
                .occupation("what he does all day").build();

        Users newUsers2 = Users.builder()
                .firstName("test 2")
                .isActive(true)
                .lastName("de tester 2")
                .address("testweg 456")
                .occupation("what he does all day").build();

        usersRepository.save(newUsers1);
        usersRepository.save(newUsers2);

        // Act
        Optional<Users> ceUsers1 = usersRepository.findById(1L);
        Optional<Users> ceUsers2 = usersRepository.findById(2L);

        // Assert
        assertThat(ceUsers1.get().getFirstName()).isEqualTo(newUsers1.getFirstName());
        assertThat(ceUsers1.get().getLastName()).isEqualTo(newUsers1.getLastName());
        assertThat(ceUsers1.get().getAddress()).isEqualTo(newUsers1.getAddress());
        assertThat(ceUsers1.get().getOccupation()).isEqualTo(newUsers1.getOccupation());

        assertThat(ceUsers2.get().getFirstName()).isEqualTo(newUsers2.getFirstName());
        assertThat(ceUsers2.get().getLastName()).isEqualTo(newUsers2.getLastName());
        assertThat(ceUsers2.get().getAddress()).isEqualTo(newUsers2.getAddress());
        assertThat(ceUsers2.get().getOccupation()).isEqualTo(newUsers2.getOccupation());
    }

    @Test
    @Sql("data.sql")
    public void findUserByIsActive() {
        // Act
        Collection<Users> users = usersRepository.findAllByIsActive();
        Collection<Users> usersInActive = usersRepository.findAllByIsInActive();

        // Assert
        assertThat(users).isNotEmpty();
        assertThat(usersInActive).isNotEmpty();
    }

    @Test
    public void findUserAndAssertEmptyResponseIsGivenInsteadOfException() {
        // Act
        Optional<Users> ceUsers = usersRepository.findById(1L);

        // Assert
        assertThat(ceUsers).isEmpty();
    }

    @Test
    public void tryCreateCeUserWithEmptyBody() {
        // Act
        assertThrows(DataIntegrityViolationException.class, () -> {
            usersRepository.save(Users.builder().build());
        });

        Optional<Users> ceUsers = usersRepository.findById(1L);

        // Assert
        assertThat(ceUsers).isEmpty();
    }

}