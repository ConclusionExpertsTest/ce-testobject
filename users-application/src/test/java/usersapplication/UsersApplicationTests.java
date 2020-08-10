package usersapplication;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class UsersApplicationTests {

    @Test
    void contextLoads() {
        UsersApplication.main(new String[0]);
    }

}
