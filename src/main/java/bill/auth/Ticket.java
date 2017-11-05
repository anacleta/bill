package bill.auth;

import java.time.LocalDateTime;

public class Ticket {
    private final long id;
    private final String token;
    private final LocalDateTime expiration;
    private final String signature;

    public Ticket(long id, String token, LocalDateTime expiration, String signature) {
        this.id = id;
        this.token = token;
        this.expiration = expiration;
        this.signature = signature;
    }

    public long getId() {
        return id;
    }

    public String getToken() {
        return token;
    }

    public LocalDateTime getExpiration() {
        return expiration;
    }

    public String getSignature() {
        return signature;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "id=" + id +
                ", token='" + token + '\'' +
                ", expiration=" + expiration +
                ", signature='" + signature + '\'' +
                '}';
    }
}
