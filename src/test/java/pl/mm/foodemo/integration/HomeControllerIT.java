package pl.mm.foodemo.integration;


import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import pl.mm.foodemo.configuration.AbstractIT;

import static org.assertj.core.api.Assertions.assertThat;


@AllArgsConstructor(onConstructor = @__(@Autowired))
public class HomeControllerIT extends AbstractIT {

    private final TestRestTemplate testRestTemplate;

    @Test
    void thatHomePageWorksCorrectly() {
        String url = String.format("http://localhost:%s%s", port, basePath);

        String page = this.testRestTemplate.getForObject(url, String.class);
        assertThat(page).contains("<title>Foodemo - Home</title>");
    }
}
