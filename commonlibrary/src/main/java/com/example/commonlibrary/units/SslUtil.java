package com.example.commonlibrary.units;


import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;


import com.example.commonlibrary.R;

//import org.bouncycastle.jce.provider.BouncyCastleProvider;
//import org.bouncycastle.openssl.PEMReader;
//import org.bouncycastle.openssl.PasswordFinder;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyPair;
import java.security.KeyStore;
import java.security.Security;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

/**
 * author : ChenShengDan
 * date   : 2021/8/6
 * desc   :
 */
public class SslUtil {
//    public static SSLSocketFactory getSocketFactory(final InputStream caCrtFile, final InputStream crtFile, final InputStream keyFile,
//                                                    final String password) throws Exception {
//        Security.removeProvider("BC");
//        Security.addProvider(new BouncyCastleProvider());
//
//        // load CA certificate
//        PEMReader reader = new PEMReader(new InputStreamReader(caCrtFile));
//        X509Certificate caCert = (X509Certificate)reader.readObject();
//        reader.close();
//
//        // load client certificate
//        reader = new PEMReader(new InputStreamReader(crtFile));
//        X509Certificate cert = (X509Certificate)reader.readObject();
//        reader.close();
//
//        // load client private key
//        reader = new PEMReader(
//                new InputStreamReader(keyFile),
//                new PasswordFinder() {
//                    @Override
//                    public char[] getPassword() {
//                        return password.toCharArray();
//                    }
//                }
//        );
//        KeyPair key = (KeyPair)reader.readObject();
//        reader.close();
//
//        // CA certificate is used to authenticate server
//        KeyStore caKs = KeyStore.getInstance(KeyStore.getDefaultType());
//        caKs.load(null, null);
//        caKs.setCertificateEntry("ca-certificate", caCert);
//        TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
//        tmf.init(caKs);
//
//        // client key and certificates are sent to server so it can authenticate us
//        KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());
//        ks.load(null, null);
//        ks.setCertificateEntry("certificate", cert);
//        ks.setKeyEntry("private-key", key.getPrivate(), password.toCharArray(), new java.security.cert.Certificate[]{cert});
//        KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
//        kmf.init(ks, password.toCharArray());
//
//        // finally, create SSL socket factory
//        SSLContext context = SSLContext.getInstance("TLSv1.2");
//        context.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);
//
//        return context.getSocketFactory();
//    }

//    public static SSLSocketFactory getSocketFactory(InputStream caCrtFile, InputStream crtFile, InputStream keyFile,
//                                                    String password) throws Exception {
//        Security.addProvider(new BouncyCastleProvider());
//
//        // load CA certificate
//        X509Certificate caCert = null;
//
//        BufferedInputStream bis = new BufferedInputStream(caCrtFile);
//        CertificateFactory cf = CertificateFactory.getInstance("X.509","BC");
//
//
//        while (bis.available() > 0) {
//            caCert = (X509Certificate) cf.generateCertificate(bis);
//        }
//
//        // load client certificate
//        bis = new BufferedInputStream(crtFile);
//        X509Certificate cert = null;
//        while (bis.available() > 0) {
//            cert = (X509Certificate) cf.generateCertificate(bis);
//        }
//
//        // load client private cert
//        PEMParser pemParser = new PEMParser(new InputStreamReader(keyFile));
//        Object object = pemParser.readObject();
//        JcaPEMKeyConverter converter = new JcaPEMKeyConverter().setProvider("BC");
//        KeyPair key = converter.getKeyPair((PEMKeyPair) object);
//
//        KeyStore caKs = KeyStore.getInstance(KeyStore.getDefaultType());
//        caKs.load(null, null);
//        caKs.setCertificateEntry("cert-certificate", caCert);
//        TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
//        tmf.init(caKs);
//
//        KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());
//        ks.load(null, null);
//        ks.setCertificateEntry("certificate", cert);
//        ks.setKeyEntry("private-cert", key.getPrivate(), password.toCharArray(),
//                new java.security.cert.Certificate[]{cert});
//        KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
//        kmf.init(ks, password.toCharArray());
//
//        SSLContext context = SSLContext.getInstance("TLSv1.2");
//        context.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);
//
//        return context.getSocketFactory();
//    }
}