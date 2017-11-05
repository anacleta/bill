package bill.auth.persistence;

import bill.auth.Ticket;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class TicketDaoTest {

    private static final String CUIT = "cuit";
    private static final String INEXISTENT_CUIT = "inexistentCuit";
    private TicketDao dao;

    @BeforeAll
    static void setUpClass() {
        //System.setProperty("db.url", "jdbc:hsqldb:mem:afip");
    }

    @BeforeEach
    void setUp() throws IOException {
        Properties properties = new Properties();
        properties.load(getClass().getResourceAsStream("/persistence.properties"));
        dao = new TicketDao(properties);
    }

    @Test
    void jpa() {
        LocalDateTime now = LocalDateTime.now();
        dao.save(CUIT, new Ticket(1, "token", now, "signature"));
        Ticket ticket = dao.get(CUIT);

        assertEquals(now, ticket.getExpiration());
    }

    @Test
    void inexistent() {
        Ticket ticket = dao.get(INEXISTENT_CUIT);

        assertNull(ticket);
    }

}