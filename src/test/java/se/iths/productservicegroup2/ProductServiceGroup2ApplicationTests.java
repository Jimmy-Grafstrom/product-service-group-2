package se.iths.productservicegroup2;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = {
        "spring.security.oauth2.resourceserver.jwt.issuer-uri=http://localhost:8080/realms/dummy"
})
class ProductServiceGroup2ApplicationTests {

    @Test
    void contextLoads() {
    }

}
