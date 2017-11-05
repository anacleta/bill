package bill.integration;

import bill.auth.Ticket;
import bill.auth.Tickets;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.xml.bind.JAXBException;
import javax.xml.datatype.DatatypeConfigurationException;
import java.io.IOException;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TicketsTest {

    private static final String CUIT = "11111111111";
    private Tickets tickets;

    @BeforeEach
    void setUp() throws JAXBException, DatatypeConfigurationException, IOException {
        Properties authConf = new Properties();
        authConf.setProperty("auth." + CUIT + ".pk.path", "wsaa/private.pk8");
        authConf.setProperty("auth." + CUIT + ".cert.path", "wsaa/ucle-afip-2.crt");

        Properties dbConf = new Properties();
        dbConf.load(getClass().getResourceAsStream("/persistence.properties"));

        tickets = new Tickets(authConf, dbConf);
    }

    @Test
    void test() {
        Ticket ticket = tickets.getTicket(CUIT);
        Ticket ticket2 = tickets.getTicket(CUIT);

        assertEquals(ticket.getId(), ticket2.getId());
    }

}