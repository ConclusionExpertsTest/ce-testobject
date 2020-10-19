package usersapplication.component;

import usersapplication.UsersApplication;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import usersapplication.repository.UsersRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = UsersApplication.class)
@ActiveProfiles("test")
public class RunCT {

    @Autowired
    protected TestRestTemplate restTemplate;

    @Autowired
    protected UsersRepository usersRepository;

    protected ObjectMapper objectMapper = new ObjectMapper();

    protected HttpHeaders httpHeaders = new HttpHeaders();
}
