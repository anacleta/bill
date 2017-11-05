package bill.auth;

import wsaa.LoginCMS;
import wsaa.LoginCMSService;
import wsaa.LoginFault_Exception;
import org.bouncycastle.cms.CMSSignedData;

import javax.xml.namespace.QName;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Base64;

class WSAAClient {

    private final LoginCMSService service;
    private final TicketRequestToXml requestToXml;
    private final XmlToTicketResponse xmlToTicketResponse;
    private final Signer signer;
    private final Base64.Encoder encoder;

    WSAAClient(TicketRequestToXml requestToXml,
               XmlToTicketResponse xmlToTicketResponse,
               Signer signer) {
        this.requestToXml = requestToXml;
        this.xmlToTicketResponse = xmlToTicketResponse;
        this.signer = signer;
        this.encoder = Base64.getEncoder();

        try {
            this.service = new LoginCMSService(new URL("https://wsaahomo.afip.gov.ar/ws/services/LoginCms?wsdl"), new QName("https://wsaahomo.afip.gov.ar/ws/services/LoginCms", "LoginCMSService"));
        } catch (MalformedURLException e) {
            throw new IllegalStateException("Cannot initialize WSAA client", e);
        }
    }

    Ticket createTicket(TicketRequest ticketRequest, Credentials credentials) {

        try {
            CMSSignedData signature = signer.sign(credentials, requestToXml.transform(ticketRequest));
            LoginCMS loginCms = service.getLoginCms();
            String response = loginCms.loginCms(new String(encoder.encode(signature.getEncoded())));
            return xmlToTicketResponse.transform(response);
        } catch (LoginFault_Exception | IOException e) {
            throw new IllegalStateException(e);
        }
    }

}
