package bill.cl;

import bill.auth.Ticket;
import bill.auth.Tickets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Properties;

public class TicketCmd {
    private static final Logger LOGGER = LoggerFactory.getLogger(TicketCmd.class);
    public static void main(String[] args) {
        Tickets tickets = null;
        try {
            Properties authConf = loadConf("/auth.properties");
            Properties persistenceConf = loadConf("/persistence.properties");

            tickets = new Tickets(authConf, persistenceConf);
            Ticket ticket = tickets.getTicket(args[0]);
            System.out.println(ticket);
        } catch (IOException e) {
            LOGGER.error("Error creando/buscando ticket.", e);
        } finally {
            if (tickets != null) {
                tickets.close();
            }
        }
    }

    private static Properties loadConf(String resource) throws IOException {
        Properties persistenceConf = new Properties();
        persistenceConf.load(TicketCmd.class.getResourceAsStream(resource));
        return persistenceConf;
    }
}
