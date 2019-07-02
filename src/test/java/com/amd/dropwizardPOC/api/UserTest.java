package com.amd.dropwizardPOC.api;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Test;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.dropwizard.jackson.Jackson;
import io.dropwizard.testing.FixtureHelpers;
//import io.dropwizard.testing.FixtureHelpers.*;

public class UserTest {
	
	private static final ObjectMapper MAPPER = Jackson.newObjectMapper();
	
	@Test
	public void serializesToJSON() throws Exception {
        final User user = new User("a", "b", "c","d");

        final String expected = MAPPER.writeValueAsString(
                MAPPER.readValue(FixtureHelpers.fixture("fixtures/user.json"), User.class));

        assertThat(MAPPER.writeValueAsString(user)).isEqualTo(expected);
    }
	
	@Test
	public void deserializesFromJSON() throws Exception{
		final User user = new User("a", "b", "c","d");
		assertThat(MAPPER.readValue(FixtureHelpers.fixture("fixtures/user.json"), User.class).getFirstName())
		.isEqualTo(user.getFirstName());
	}

}
