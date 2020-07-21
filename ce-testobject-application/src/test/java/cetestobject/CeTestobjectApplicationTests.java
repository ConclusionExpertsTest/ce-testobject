package cetestobject;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class CeTestobjectApplicationTests {

    @Test
    void contextLoads() {
        CeTestobjectApplication.main(new String[0]);
    }

}
