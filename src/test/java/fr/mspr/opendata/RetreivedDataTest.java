package fr.mspr.opendata;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import fr.mspr.opendata.entity.dto.EntryDto;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RetreivedDataTest {

	@Autowired
	private TestRestTemplate restTemplate;
	
	@Test
	public void unsupportedMediaTypeTest() {
		ResponseEntity<EntryDto[]> response = this.restTemplate.getForEntity("/entry", EntryDto[].class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
	}
}