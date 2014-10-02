package edu.crypto.jca;

import org.apache.commons.codec.binary.Base64;
import org.testng.annotations.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class KeyFormatTest {
    @Test
    public void privateKey() throws Exception {
        KeyPair pair = (KeyPair) new ObjectInputStream(
                new FileInputStream("/Users/sbashkyrtsev/projects/job/gd/cicd/ssh_host_key")).readObject();
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PKCS8EncodedKeySpec pkc8PrivateKeySpec = keyFactory.getKeySpec(pair.getPrivate(), PKCS8EncodedKeySpec.class);
        byte[] pkcs8private = keyFactory.generatePrivate(pkc8PrivateKeySpec).getEncoded();

        FileOutputStream fileOutputStream = new FileOutputStream("target/ssh_host_rsa_key");
        fileOutputStream.write("-----BEGIN PRIVATE KEY-----\n".getBytes());
        byte[] base64 = Base64.encodeBase64(pkcs8private);
        for (int i = 0; i < base64.length; i++) {
            fileOutputStream.write(base64[i]);
            if ((i + 1) % 64 == 0) {
                fileOutputStream.write('\n');
            }
        }
        fileOutputStream.write("\n-----END PRIVATE KEY-----".getBytes());
        fileOutputStream.flush();
        fileOutputStream.close();
    }

    @Test
    public void privateKeyX509() throws Exception {
        KeyPair pair = (KeyPair) new ObjectInputStream(
                new FileInputStream("/Users/sbashkyrtsev/projects/job/gd/cicd/ssh_host_key")).readObject();
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        X509EncodedKeySpec x509PrivateKeySpec = keyFactory.getKeySpec(pair.getPrivate(), X509EncodedKeySpec.class);
        byte[] x509private = keyFactory.generatePrivate(x509PrivateKeySpec).getEncoded();

        FileOutputStream fileOutputStream = new FileOutputStream("target/x509_key");
        fileOutputStream.write("-----BEGIN ENCRYPTED PRIVATE KEY-----\n".getBytes());
        byte[] base64 = Base64.encodeBase64(x509private);
        for (int i = 0; i < base64.length; i++) {
            fileOutputStream.write(base64[i]);
            if ((i + 1) % 64 == 0) {
                fileOutputStream.write('\n');
            }
        }
        fileOutputStream.write("\n-----END ENCRYPTED PRIVATE KEY-----".getBytes());
        fileOutputStream.flush();
        fileOutputStream.close();
    }

    @Test
    public void publicKey() throws Exception {
        KeyPair pair = (KeyPair) new ObjectInputStream(
                new FileInputStream("/Users/sbashkyrtsev/projects/job/gd/cicd/ssh_host_key")).readObject();
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        RSAPublicKeySpec pkc8PublicKeySpec = keyFactory.getKeySpec(pair.getPublic(), RSAPublicKeySpec.class);
        byte[] pkcs8public = keyFactory.generatePublic(pkc8PublicKeySpec).getEncoded();

        FileOutputStream fileOutputStream = new FileOutputStream("target/ssh_host_rsa_key.pub");
        fileOutputStream.write("-----BEGIN PUBLIC KEY-----\n".getBytes());
        byte[] base64 = Base64.encodeBase64(pkcs8public);
        for (int i = 0; i < base64.length; i++) {
            fileOutputStream.write(base64[i]);
            if ((i + 1) % 64 == 0) {
                fileOutputStream.write('\n');
            }
        }
        fileOutputStream.write("\n-----END PUBLIC KEY-----".getBytes());
        fileOutputStream.flush();
        fileOutputStream.close();
    }
}
