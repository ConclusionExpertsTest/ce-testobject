package nl.conclusionexperts.cetestobject.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import nl.conclusionexperts.cetestobject.CeTestobjectApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = CeTestobjectApplication.class)
@ActiveProfiles("test")
@Sql("data.sql")
public class RunIT {

    @Autowired
    protected TestRestTemplate restTemplate;

    protected ObjectMapper objectMapper = new ObjectMapper();

    protected HttpHeaders httpHeaders = new HttpHeaders();
}
