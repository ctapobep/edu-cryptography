package edu.cryptography.jca;

import org.bouncycastle.util.encoders.Hex;
import org.testng.annotations.Test;

import javax.crypto.*;
import java.security.*;
import java.security.cert.*;

import static org.testng.Assert.assertTrue;
import static org.testng.AssertJUnit.assertEquals;

/**
 * A generator creates objects with brand-new contents, whereas a factory creates objects from existing material
 * (for example, an encoding).
 */
public class EnginesTest {
    @Test
    public void testMessageDigestEngine() throws NoSuchAlgorithmException, DigestException {
        MessageDigest digest = MessageDigest.getInstance("MD5");
        digest.update("hello".getBytes());
        assertEquals(Hex.toHexString(digest.digest()), "5d41402abc4b2a76b9719d911017c592");
    }

    @Test
    public void testCipherEngine() throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, (Key) null);
    }

    @Test
    public void testSignatureEngine() throws NoSuchAlgorithmException, SignatureException, InvalidKeyException {
        KeyPair pair = KeyPairGenerator.getInstance("DSA").generateKeyPair();
        byte[] data = {'t', 'o', 's', 'i', 'g', 'n'};

        Signature toSign = Signature.getInstance("SHA1withDSA");
        PrivateKey priv = pair.getPrivate();
        toSign.initSign(priv);
        toSign.update(data);
        byte[] signature = toSign.sign();//this is our signature!

        Signature toVerify = Signature.getInstance("SHA1withDSA");
        PublicKey pub = pair.getPublic();
        toVerify.initVerify(pub);
        toVerify.update(data);

        assertTrue(toVerify.verify(signature));
    }

    @Test
    public void testSecureRandomEngine() throws NoSuchAlgorithmException {
        SecureRandom random = SecureRandom.getInstance("");
        int i = random.nextInt();

    }

    @Test
    public void testMessageAuthenticationCodeEngine() throws NoSuchAlgorithmException {
        Mac.getInstance("");
    }

    @Test
    public void testKeyFactoryEngine() throws NoSuchAlgorithmException {
        KeyFactory.getInstance("");
    }

    @Test
    public void testSecretKeyFactoryEngine() throws NoSuchAlgorithmException {
        SecretKeyFactory.getInstance("");
    }

    @Test
    public void testOtherEngine() throws Exception {
        KeyPairGenerator.getInstance("");
        KeyGenerator.getInstance("");
        KeyAgreement.getInstance("DH");
        AlgorithmParameters.getInstance("");
        AlgorithmParameterGenerator.getInstance("");
        KeyStore.getInstance("");
        CertificateFactory.getInstance("");
        CertPathBuilder.getInstance("");
        CertPathValidator.getInstance("");
        CertStore.getInstance((String) null, (CertStoreParameters) null);
    }
}
