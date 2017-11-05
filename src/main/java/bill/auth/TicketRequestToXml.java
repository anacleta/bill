package bill.auth;

import bill.afip.wsaa.request.HeaderType;
import bill.afip.wsaa.request.LoginTicketRequest;
import bill.afip.wsaa.request.ObjectFactory;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.PropertyException;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;
import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;

class TicketRequestToXml {
    private static final String ENCODING = "utf-8";
    private final DatatypeFactory datatypeFactory;
    private final Marshaller marshaller;

    public TicketRequestToXml()  {
        try {
            datatypeFactory = DatatypeFactory.newInstance();
            JAXBContext context = JAXBContext.newInstance(ObjectFactory.class);
            marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_ENCODING, ENCODING);
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        } catch (DatatypeConfigurationException | JAXBException e) {
            throw new IllegalStateException("Cannot initialize transformer.", e);
        }
    }

    public String transform(TicketRequest ticketRequest) {
        HeaderType header = new HeaderType();
        header.setSource(ticketRequest.getSource());
        header.setDestination(ticketRequest.getDestination());
        header.setUniqueId(1);
        LocalDateTime ahora = LocalDateTime.now();
        header.setGenerationTime(convert(ahora));
        header.setExpirationTime(convert(ahora.plusMinutes(1)));
        LoginTicketRequest request = new LoginTicketRequest();
        request.setService(ticketRequest.getService());
        request.setHeader(header);

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            marshaller.marshal(new JAXBElement<>(new QName("","loginTicketRequest"), LoginTicketRequest.class, request), os);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return os.toString();
    }

    private XMLGregorianCalendar convert(LocalDateTime fechaHora) {
        return datatypeFactory.newXMLGregorianCalendar(fechaHora.getYear(), fechaHora.getMonthValue(), fechaHora.getDayOfMonth(),
                fechaHora.getHour(), fechaHora.getMinute(), fechaHora.getSecond(), 0, -180);
    }
}
