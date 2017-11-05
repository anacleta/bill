package bill.auth;

import org.bouncycastle.cert.jcajce.JcaCertStore;
import org.bouncycastle.cms.CMSException;
import org.bouncycastle.cms.CMSProcessableByteArray;
import org.bouncycastle.cms.CMSSignedData;
import org.bouncycastle.cms.CMSSignedDataGenerator;
import org.bouncycastle.cms.jcajce.JcaSignerInfoGeneratorBuilder;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import org.bouncycastle.operator.jcajce.JcaDigestCalculatorProviderBuilder;

import java.security.cert.CertificateEncodingException;

import static java.util.Collections.singletonList;

class Signer {
    private static final String SIGNATURE_ALGORITHM = "SHA1withRSA";

    CMSSignedData sign(Credentials credentials, String content) {
        try {
            ContentSigner sha1Signer = new JcaContentSignerBuilder(SIGNATURE_ALGORITHM)
                    .build(credentials.getKey());
            CMSSignedDataGenerator generator = new CMSSignedDataGenerator();
            generator.addSignerInfoGenerator(
                    new JcaSignerInfoGeneratorBuilder(
                            new JcaDigestCalculatorProviderBuilder()
                                    .build())
                            .setDirectSignature(true)
                            .build(sha1Signer, credentials.getCertificate()));
            JcaCertStore certs = new JcaCertStore(singletonList(credentials.getCertificate()));
            generator.addCertificates(certs);
            return generator.generate(new CMSProcessableByteArray(content.getBytes()), true);
        } catch (OperatorCreationException | CertificateEncodingException | CMSException e) {
            throw new IllegalStateException("Cannot sign content.", e);
        }
    }
}
