package org.apache.http.conn.ssl;

@java.lang.Deprecated
/* loaded from: classes5.dex */
public class SSLSocketFactory implements org.apache.http.conn.scheme.LayeredSocketFactory {
    public static final java.lang.String SSL = "SSL";
    public static final java.lang.String SSLV2 = "SSLv2";
    public static final java.lang.String TLS = "TLS";
    private org.apache.http.conn.ssl.X509HostnameVerifier hostnameVerifier;
    private final org.apache.http.conn.scheme.HostNameResolver nameResolver;
    private final javax.net.ssl.SSLSocketFactory socketfactory;
    private final javax.net.ssl.SSLContext sslcontext;
    public static final org.apache.http.conn.ssl.X509HostnameVerifier ALLOW_ALL_HOSTNAME_VERIFIER = new org.apache.http.conn.ssl.AllowAllHostnameVerifier();
    public static final org.apache.http.conn.ssl.X509HostnameVerifier BROWSER_COMPATIBLE_HOSTNAME_VERIFIER = new org.apache.http.conn.ssl.BrowserCompatHostnameVerifier();
    public static final org.apache.http.conn.ssl.X509HostnameVerifier STRICT_HOSTNAME_VERIFIER = new org.apache.http.conn.ssl.StrictHostnameVerifier();

    private static class NoPreloadHolder {
        private static final org.apache.http.conn.ssl.SSLSocketFactory DEFAULT_FACTORY = new org.apache.http.conn.ssl.SSLSocketFactory();

        private NoPreloadHolder() {
        }
    }

    public static org.apache.http.conn.ssl.SSLSocketFactory getSocketFactory() {
        return org.apache.http.conn.ssl.SSLSocketFactory.NoPreloadHolder.DEFAULT_FACTORY;
    }

    public SSLSocketFactory(java.lang.String str, java.security.KeyStore keyStore, java.lang.String str2, java.security.KeyStore keyStore2, java.security.SecureRandom secureRandom, org.apache.http.conn.scheme.HostNameResolver hostNameResolver) throws java.security.NoSuchAlgorithmException, java.security.KeyManagementException, java.security.KeyStoreException, java.security.UnrecoverableKeyException {
        javax.net.ssl.KeyManager[] keyManagerArr;
        this.hostnameVerifier = BROWSER_COMPATIBLE_HOSTNAME_VERIFIER;
        str = str == null ? TLS : str;
        if (keyStore == null) {
            keyManagerArr = null;
        } else {
            keyManagerArr = createKeyManagers(keyStore, str2);
        }
        javax.net.ssl.TrustManager[] createTrustManagers = keyStore2 != null ? createTrustManagers(keyStore2) : null;
        this.sslcontext = javax.net.ssl.SSLContext.getInstance(str);
        this.sslcontext.init(keyManagerArr, createTrustManagers, secureRandom);
        this.socketfactory = this.sslcontext.getSocketFactory();
        this.nameResolver = hostNameResolver;
    }

    public SSLSocketFactory(java.security.KeyStore keyStore, java.lang.String str, java.security.KeyStore keyStore2) throws java.security.NoSuchAlgorithmException, java.security.KeyManagementException, java.security.KeyStoreException, java.security.UnrecoverableKeyException {
        this(TLS, keyStore, str, keyStore2, null, null);
    }

    public SSLSocketFactory(java.security.KeyStore keyStore, java.lang.String str) throws java.security.NoSuchAlgorithmException, java.security.KeyManagementException, java.security.KeyStoreException, java.security.UnrecoverableKeyException {
        this(TLS, keyStore, str, null, null, null);
    }

    public SSLSocketFactory(java.security.KeyStore keyStore) throws java.security.NoSuchAlgorithmException, java.security.KeyManagementException, java.security.KeyStoreException, java.security.UnrecoverableKeyException {
        this(TLS, null, null, keyStore, null, null);
    }

    public SSLSocketFactory(javax.net.ssl.SSLSocketFactory sSLSocketFactory) {
        this.hostnameVerifier = BROWSER_COMPATIBLE_HOSTNAME_VERIFIER;
        this.sslcontext = null;
        this.socketfactory = sSLSocketFactory;
        this.nameResolver = null;
    }

    private SSLSocketFactory() {
        this.hostnameVerifier = BROWSER_COMPATIBLE_HOSTNAME_VERIFIER;
        this.sslcontext = null;
        this.socketfactory = javax.net.ssl.HttpsURLConnection.getDefaultSSLSocketFactory();
        this.nameResolver = null;
    }

    private static javax.net.ssl.KeyManager[] createKeyManagers(java.security.KeyStore keyStore, java.lang.String str) throws java.security.KeyStoreException, java.security.NoSuchAlgorithmException, java.security.UnrecoverableKeyException {
        if (keyStore == null) {
            throw new java.lang.IllegalArgumentException("Keystore may not be null");
        }
        javax.net.ssl.KeyManagerFactory keyManagerFactory = javax.net.ssl.KeyManagerFactory.getInstance(javax.net.ssl.KeyManagerFactory.getDefaultAlgorithm());
        keyManagerFactory.init(keyStore, str != null ? str.toCharArray() : null);
        return keyManagerFactory.getKeyManagers();
    }

    private static javax.net.ssl.TrustManager[] createTrustManagers(java.security.KeyStore keyStore) throws java.security.KeyStoreException, java.security.NoSuchAlgorithmException {
        if (keyStore == null) {
            throw new java.lang.IllegalArgumentException("Keystore may not be null");
        }
        javax.net.ssl.TrustManagerFactory trustManagerFactory = javax.net.ssl.TrustManagerFactory.getInstance(javax.net.ssl.TrustManagerFactory.getDefaultAlgorithm());
        trustManagerFactory.init(keyStore);
        return trustManagerFactory.getTrustManagers();
    }

    @Override // org.apache.http.conn.scheme.SocketFactory
    public java.net.Socket createSocket() throws java.io.IOException {
        return (javax.net.ssl.SSLSocket) this.socketfactory.createSocket();
    }

    @Override // org.apache.http.conn.scheme.SocketFactory
    public java.net.Socket connectSocket(java.net.Socket socket, java.lang.String str, int i, java.net.InetAddress inetAddress, int i2, org.apache.http.params.HttpParams httpParams) throws java.io.IOException {
        java.net.InetSocketAddress inetSocketAddress;
        if (str == null) {
            throw new java.lang.IllegalArgumentException("Target host may not be null.");
        }
        if (httpParams == null) {
            throw new java.lang.IllegalArgumentException("Parameters may not be null.");
        }
        if (socket == null) {
            socket = createSocket();
        }
        javax.net.ssl.SSLSocket sSLSocket = (javax.net.ssl.SSLSocket) socket;
        if (inetAddress != null || i2 > 0) {
            if (i2 < 0) {
                i2 = 0;
            }
            sSLSocket.bind(new java.net.InetSocketAddress(inetAddress, i2));
        }
        int connectionTimeout = org.apache.http.params.HttpConnectionParams.getConnectionTimeout(httpParams);
        int soTimeout = org.apache.http.params.HttpConnectionParams.getSoTimeout(httpParams);
        if (this.nameResolver != null) {
            inetSocketAddress = new java.net.InetSocketAddress(this.nameResolver.resolve(str), i);
        } else {
            inetSocketAddress = new java.net.InetSocketAddress(str, i);
        }
        sSLSocket.connect(inetSocketAddress, connectionTimeout);
        sSLSocket.setSoTimeout(soTimeout);
        try {
            sSLSocket.startHandshake();
            this.hostnameVerifier.verify(str, sSLSocket);
            return sSLSocket;
        } catch (java.io.IOException e) {
            try {
                sSLSocket.close();
            } catch (java.lang.Exception e2) {
            }
            throw e;
        }
    }

    @Override // org.apache.http.conn.scheme.SocketFactory
    public boolean isSecure(java.net.Socket socket) throws java.lang.IllegalArgumentException {
        if (socket == null) {
            throw new java.lang.IllegalArgumentException("Socket may not be null.");
        }
        if (!(socket instanceof javax.net.ssl.SSLSocket)) {
            throw new java.lang.IllegalArgumentException("Socket not created by this factory.");
        }
        if (socket.isClosed()) {
            throw new java.lang.IllegalArgumentException("Socket is closed.");
        }
        return true;
    }

    @Override // org.apache.http.conn.scheme.LayeredSocketFactory
    public java.net.Socket createSocket(java.net.Socket socket, java.lang.String str, int i, boolean z) throws java.io.IOException, java.net.UnknownHostException {
        javax.net.ssl.SSLSocket sSLSocket = (javax.net.ssl.SSLSocket) this.socketfactory.createSocket(socket, str, i, z);
        sSLSocket.startHandshake();
        this.hostnameVerifier.verify(str, sSLSocket);
        return sSLSocket;
    }

    public void setHostnameVerifier(org.apache.http.conn.ssl.X509HostnameVerifier x509HostnameVerifier) {
        if (x509HostnameVerifier == null) {
            throw new java.lang.IllegalArgumentException("Hostname verifier may not be null");
        }
        this.hostnameVerifier = x509HostnameVerifier;
    }

    public org.apache.http.conn.ssl.X509HostnameVerifier getHostnameVerifier() {
        return this.hostnameVerifier;
    }
}
