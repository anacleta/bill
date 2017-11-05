package bill.auth;

import bill.auth.persistence.TicketDao;

import java.time.LocalDateTime;
import java.util.Properties;

public class Tickets {
    private final CredentialsRepo credentials;
    private final WSAAClient wsaaClient;
    private final TicketDao dao;

    public Tickets(Properties authConf, Properties persistenceConf) {
        this.credentials = new CredentialsRepo(authConf);
        this.wsaaClient = new WSAAClient(new TicketRequestToXml(), new XmlToTicketResponse(), new Signer());
        this.dao = new TicketDao(persistenceConf);
    }

    public Ticket getTicket(String cuit) {
        Ticket ticket = dao.get(cuit);
        if (ticket == null || ticket.getExpiration().isBefore(LocalDateTime.now())) {
            ticket = createTicket(cuit);

            dao.save(cuit, ticket);
        }
        return ticket;
    }

    private Ticket createTicket(String cuit) {
        Credentials credentials = this.credentials.get(cuit);

        return wsaaClient.createTicket(new TicketRequest(
                credentials.getCertificate().getSubjectDN().toString(),
                "cn=wsaahomo,o=afip,c=ar,serialNumber=CUIT 33693450239",
                "wsfe"), credentials);
    }
}
