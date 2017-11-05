package bill.auth;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class XmlToTicketResponseTest {
    private static final String RESPONSE =
            "<loginTicketResponse version=\"1\">\n" +
            "    <header>\n" +
            "        <source>CN=wsaahomo, O=AFIP, C=AR, SERIALNUMBER=CUIT 33693450239</source>\n" +
            "        <destination>SERIALNUMBER=CUIT 11111111111, CN=ucle</destination>\n" +
            "        <uniqueId>2374292067</uniqueId>\n" +
            "        <generationTime>2017-10-29T16:49:45.336-03:00</generationTime>\n" +
            "        <expirationTime>2017-10-30T04:49:45.336-03:00</expirationTime>\n" +
            "    </header>\n" +
            "    <credentials>\n" +
            "        <token>PD94bWwgdmVyc2lvbj0iMS4wIiBlbmNvZGluZz0iVVRGLTgiIHN0YW5kYWxvbmU9InllcyI/Pgo8c3NvIHZlcnNpb249IjIuMCI+CiAgICA8aWQgc3JjPSJDTj13c2FhaG9tbywgTz1BRklQLCBDPUFSLCBTRVJJQUxOVU1CRVI9Q1VJVCAzMzY5MzQ1MDIzOSIgZHN0PSJDTj13c2ZlLCBPPUFGSVAsIEM9QVIiIHVuaXF1ZV9pZD0iMTY4OTMzNzg1MCIgZ2VuX3RpbWU9IjE1MDkzMDY1MjUiIGV4cF90aW1lPSIxNTA5MzQ5Nzg1Ii8+CiAgICA8b3BlcmF0aW9uIHR5cGU9ImxvZ2luIiB2YWx1ZT0iZ3JhbnRlZCI+CiAgICAgICAgPGxvZ2luIGVudGl0eT0iMzM2OTM0NTAyMzkiIHNlcnZpY2U9IndzZmUiIHVpZD0iU0VSSUFMTlVNQkVSPUNVSVQgMjcyODkwNDI0NTMsIENOPXVjbGUiIGF1dGhtZXRob2Q9ImNtcyIgcmVnbWV0aG9kPSIyMiI+CiAgICAgICAgICAgIDxyZWxhdGlvbnM+CiAgICAgICAgICAgICAgICA8cmVsYXRpb24ga2V5PSIyNzI4OTA0MjQ1MyIgcmVsdHlwZT0iNCIvPgogICAgICAgICAgICA8L3JlbGF0aW9ucz4KICAgICAgICA8L2xvZ2luPgogICAgPC9vcGVyYXRpb24+Cjwvc3NvPgo=</token>\n" +
            "        <sign>Obk5LxbnIiKAPSm2ESjJqq0gDEpt9714DVA5/SmtgCy6c4TW1JNnXb9kXJaEZKRxZuN4vDltFJ2CvB8UnIqdxAoWs/9y7TJno3XffwtGR1c0Sh/Wc8JtryqW4ZEesO0dMPUq18hgKZel1i3Ve93eQ0CJwHv/Ra73Zl0Ncz/iVcE=</sign>\n" +
            "    </credentials>\n" +
            "</loginTicketResponse>";
    @Test
    void unmarshall() {
        XmlToTicketResponse xmlToTicketResponse = new XmlToTicketResponse();

        Ticket ticket = xmlToTicketResponse.transform(RESPONSE);

        assertNotNull(ticket);
    }
}
