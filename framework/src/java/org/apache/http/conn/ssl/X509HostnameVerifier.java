package org.apache.http.conn.ssl;

@java.lang.Deprecated
/* loaded from: classes5.dex */
public interface X509HostnameVerifier extends javax.net.ssl.HostnameVerifier {
    void verify(java.lang.String str, java.security.cert.X509Certificate x509Certificate) throws javax.net.ssl.SSLException;

    void verify(java.lang.String str, javax.net.ssl.SSLSocket sSLSocket) throws java.io.IOException;

    void verify(java.lang.String str, java.lang.String[] strArr, java.lang.String[] strArr2) throws javax.net.ssl.SSLException;

    @Override // javax.net.ssl.HostnameVerifier
    boolean verify(java.lang.String str, javax.net.ssl.SSLSession sSLSession);
}
