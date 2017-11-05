package bill.auth;

import bill.afip.wsaa.response.LoginTicketResponse;
import bill.afip.wsaa.response.ObjectFactory;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.datatype.XMLGregorianCalendar;
import java.io.StringReader;
import java.time.LocalDateTime;

class XmlToTicketResponse {
    private final Unmarshaller unmarshaller;

    XmlToTicketResponse() {
        try {
            JAXBContext context = JAXBContext.newInstance(ObjectFactory.class);
            unmarshaller = context.createUnmarshaller();
        } catch (JAXBException e) {
            throw new IllegalStateException("Cannot initialize response parser", e);
        }
    }


    Ticket transform(String response) {
        try {
            JAXBElement<LoginTicketResponse> element = (JAXBElement<LoginTicketResponse>) unmarshaller.unmarshal(new StringReader(response));
            LoginTicketResponse ticketResponse = element.getValue();
            return new Ticket(ticketResponse.getHeader().getUniqueId(),
                ticketResponse.getCredentials().getToken(),
                toLocalDateTime(ticketResponse.getHeader().getExpirationTime()),
                ticketResponse.getCredentials().getSign()
            );

        } catch (JAXBException e) {
            throw new IllegalStateException("Cannot read response.", e);
        }
    }

    private static LocalDateTime toLocalDateTime(XMLGregorianCalendar calendar) {
        return LocalDateTime.of(calendar.getYear(), calendar.getMonth(), calendar.getDay(),
                calendar.getHour(), calendar.getMinute(), calendar.getSecond());
    }
}
