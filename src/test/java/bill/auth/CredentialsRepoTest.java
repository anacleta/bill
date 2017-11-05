package bill.auth;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CredentialsRepoTest {

    private static final String EXISTING_CUIT = "0123456789012";
    private static final String NON_EXISTING_CUIT = "11111111111";
    private CredentialsRepo repo;

    @BeforeEach
    void setUp() {
        Properties properties = new Properties();
        properties.setProperty("auth." + EXISTING_CUIT + ".pk.path", "wsaa/private.pk8");
        properties.setProperty("auth." + EXISTING_CUIT + ".cert.path", "wsaa/ucle-afip-2.crt");

        repo = new CredentialsRepo(properties);
    }

    @Test
    void whenExistingCuitRepoShouldReturnCredentials() {
        Credentials credentials = repo.get(EXISTING_CUIT);

        assertNotNull(credentials);
        assertNotNull(credentials.getKey());
        assertNotNull(credentials.getCertificate());
    }

    @Test
    void whenNonExistingCuitRepoShouldThrowException() {
        assertThrows(IllegalArgumentException.class, () -> repo.get(NON_EXISTING_CUIT));
    }
}