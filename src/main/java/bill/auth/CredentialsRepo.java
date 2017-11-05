package bill.auth;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Properties;

class CredentialsRepo {
    private final Properties config;
    private final CertificateFactory certificateFactory;
    private final KeyFactory keyFactory;

    CredentialsRepo(Properties config) {
        this.config = config;
        try {
            certificateFactory = CertificateFactory.getInstance("X.509");
            keyFactory = KeyFactory.getInstance("RSA");
        } catch (CertificateException | NoSuchAlgorithmException e) {
            throw new IllegalStateException("Cannot load credentials repository.", e);
        }
    }

    Credentials get(String cuit) {
        String certificatePath = config.getProperty("auth." + cuit + ".cert.path");
        String privateKeyPath = config.getProperty("auth." + cuit + ".pk.path");

        if (certificatePath == null || privateKeyPath == null) {
            throw new IllegalArgumentException("CUIT no registrado.");
        }

        try {
            return new Credentials(loadPrivateKey(privateKeyPath),
                    loadCertificate(certificatePath));
        } catch (IOException | InvalidKeySpecException | CertificateException e) {
            throw new IllegalStateException("Cannot load credentials for CUIT " + cuit, e);
        }
    }

    private X509Certificate loadCertificate(String certificatePath) throws FileNotFoundException, CertificateException {
        FileInputStream is = new FileInputStream(certificatePath);
        return (X509Certificate) certificateFactory.generateCertificate(is);
    }

    private PrivateKey loadPrivateKey(String keyPath) throws IOException, InvalidKeySpecException {
        File privKeyFile = new File(keyPath);
        DataInputStream dis = new DataInputStream(new FileInputStream(privKeyFile));
        byte[] privateBytes = new byte[(int) privKeyFile.length()];
        dis.readFully(privateBytes);
        dis.close();

        return keyFactory.generatePrivate(new PKCS8EncodedKeySpec(privateBytes));
    }
}
