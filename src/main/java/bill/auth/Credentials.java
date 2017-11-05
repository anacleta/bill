package bill.auth;

import java.security.PrivateKey;
import java.security.cert.X509Certificate;

class Credentials {
    private final PrivateKey key;
    private final X509Certificate certificate;

    public Credentials(PrivateKey key, X509Certificate certificate) {
        this.key = key;
        this.certificate = certificate;
    }

    public PrivateKey getKey() {
        return key;
    }

    public X509Certificate getCertificate() {
        return certificate;
    }
}
