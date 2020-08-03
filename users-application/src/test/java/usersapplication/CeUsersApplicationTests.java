package usersapplication;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class CeUsersApplicationTests {

    @Test
    void contextLoads() {
        CeUsersApplication.main(new String[0]);
    }

}
