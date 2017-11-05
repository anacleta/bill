package bill.cl;

import bill.auth.Ticket;
import bill.auth.Tickets;

import java.io.IOException;
import java.util.Properties;

public class TicketCmd {
    public static void main(String[] args) {
        try {
            Properties authConf = loadConf("/auth.properties");
            Properties persistenceConf = loadConf("/persistence.properties");

            Tickets tickets = new Tickets(authConf, persistenceConf);
            Ticket ticket = tickets.getTicket(args[0]);
            System.out.println(ticket);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Properties loadConf(String resource) throws IOException {
        Properties persistenceConf = new Properties();
        persistenceConf.load(TicketCmd.class.getResourceAsStream(resource));
        return persistenceConf;
    }
}
