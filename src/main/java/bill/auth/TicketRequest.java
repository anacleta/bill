package bill.auth;

class TicketRequest {
    private final String source;
    private final String destination;
    private final String service;

    TicketRequest(String source, String destination, String service) {
        this.source = source;
        this.destination = destination;
        this.service = service;
    }

    String getSource() {
        return source;
    }

    String getDestination() {
        return destination;
    }

    public String getService() {
        return service;
    }
}
