package android.net;

@java.lang.Deprecated
/* loaded from: classes2.dex */
public class SSLCertificateSocketFactory extends javax.net.ssl.SSLSocketFactory {
    private static final javax.net.ssl.TrustManager[] INSECURE_TRUST_MANAGER = {new javax.net.ssl.X509TrustManager() { // from class: android.net.SSLCertificateSocketFactory.1
        @Override // javax.net.ssl.X509TrustManager
        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
            return null;
        }

        @Override // javax.net.ssl.X509TrustManager
        public void checkClientTrusted(java.security.cert.X509Certificate[] x509CertificateArr, java.lang.String str) {
        }

        @Override // javax.net.ssl.X509TrustManager
        public void checkServerTrusted(java.security.cert.X509Certificate[] x509CertificateArr, java.lang.String str) {
        }
    }};
    private static final java.lang.String TAG = "SSLCertificateSocketFactory";
    private byte[] mAlpnProtocols;
    private java.security.PrivateKey mChannelIdPrivateKey;
    private final int mHandshakeTimeoutMillis;
    private javax.net.ssl.SSLSocketFactory mInsecureFactory;
    private javax.net.ssl.KeyManager[] mKeyManagers;
    private byte[] mNpnProtocols;
    private final boolean mSecure;
    private javax.net.ssl.SSLSocketFactory mSecureFactory;
    private final com.android.org.conscrypt.SSLClientSessionCache mSessionCache;
    private javax.net.ssl.TrustManager[] mTrustManagers;

    @java.lang.Deprecated
    public SSLCertificateSocketFactory(int i) {
        this(i, null, true);
    }

    private SSLCertificateSocketFactory(int i, android.net.SSLSessionCache sSLSessionCache, boolean z) {
        this.mInsecureFactory = null;
        this.mSecureFactory = null;
        this.mTrustManagers = null;
        this.mKeyManagers = null;
        this.mNpnProtocols = null;
        this.mAlpnProtocols = null;
        this.mChannelIdPrivateKey = null;
        this.mHandshakeTimeoutMillis = i;
        this.mSessionCache = sSLSessionCache != null ? sSLSessionCache.mSessionCache : null;
        this.mSecure = z;
    }

    public static javax.net.SocketFactory getDefault(int i) {
        return new android.net.SSLCertificateSocketFactory(i, null, true);
    }

    public static javax.net.ssl.SSLSocketFactory getDefault(int i, android.net.SSLSessionCache sSLSessionCache) {
        return new android.net.SSLCertificateSocketFactory(i, sSLSessionCache, true);
    }

    public static javax.net.ssl.SSLSocketFactory getInsecure(int i, android.net.SSLSessionCache sSLSessionCache) {
        return new android.net.SSLCertificateSocketFactory(i, sSLSessionCache, false);
    }

    @java.lang.Deprecated
    public static org.apache.http.conn.ssl.SSLSocketFactory getHttpSocketFactory(int i, android.net.SSLSessionCache sSLSessionCache) {
        return new org.apache.http.conn.ssl.SSLSocketFactory(new android.net.SSLCertificateSocketFactory(i, sSLSessionCache, true));
    }

    public static void verifyHostname(java.net.Socket socket, java.lang.String str) throws java.io.IOException {
        if (!(socket instanceof javax.net.ssl.SSLSocket)) {
            throw new java.lang.IllegalArgumentException("Attempt to verify non-SSL socket");
        }
        if (!isSslCheckRelaxed()) {
            javax.net.ssl.SSLSocket sSLSocket = (javax.net.ssl.SSLSocket) socket;
            sSLSocket.startHandshake();
            javax.net.ssl.SSLSession session = sSLSocket.getSession();
            if (session == null) {
                throw new javax.net.ssl.SSLException("Cannot verify SSL socket without session");
            }
            if (!javax.net.ssl.HttpsURLConnection.getDefaultHostnameVerifier().verify(str, session)) {
                throw new javax.net.ssl.SSLPeerUnverifiedException("Cannot verify hostname: " + str);
            }
        }
    }

    private javax.net.ssl.SSLSocketFactory makeSocketFactory(javax.net.ssl.KeyManager[] keyManagerArr, javax.net.ssl.TrustManager[] trustManagerArr) {
        try {
            javax.net.ssl.SSLContext sSLContext = javax.net.ssl.SSLContext.getInstance(org.apache.http.conn.ssl.SSLSocketFactory.TLS, "AndroidOpenSSL");
            sSLContext.init(keyManagerArr, trustManagerArr, null);
            sSLContext.getClientSessionContext().setPersistentCache(this.mSessionCache);
            return sSLContext.getSocketFactory();
        } catch (java.security.KeyManagementException | java.security.NoSuchAlgorithmException | java.security.NoSuchProviderException e) {
            android.util.Log.wtf(TAG, e);
            return (javax.net.ssl.SSLSocketFactory) javax.net.ssl.SSLSocketFactory.getDefault();
        }
    }

    private static boolean isSslCheckRelaxed() {
        return com.android.internal.os.RoSystemProperties.DEBUGGABLE && android.os.SystemProperties.getBoolean("socket.relaxsslcheck", false);
    }

    private synchronized javax.net.ssl.SSLSocketFactory getDelegate() {
        if (this.mSecure && !isSslCheckRelaxed()) {
            if (this.mSecureFactory == null) {
                this.mSecureFactory = makeSocketFactory(this.mKeyManagers, this.mTrustManagers);
            }
            return this.mSecureFactory;
        }
        if (this.mInsecureFactory == null) {
            if (this.mSecure) {
                android.util.Log.w(TAG, "*** BYPASSING SSL SECURITY CHECKS (socket.relaxsslcheck=yes) ***");
            } else {
                android.util.Log.w(TAG, "Bypassing SSL security checks at caller's request");
            }
            this.mInsecureFactory = makeSocketFactory(this.mKeyManagers, INSECURE_TRUST_MANAGER);
        }
        return this.mInsecureFactory;
    }

    public void setTrustManagers(javax.net.ssl.TrustManager[] trustManagerArr) {
        this.mTrustManagers = trustManagerArr;
        this.mSecureFactory = null;
    }

    public void setNpnProtocols(byte[][] bArr) {
        this.mNpnProtocols = toLengthPrefixedList(bArr);
    }

    public void setAlpnProtocols(byte[][] bArr) {
        this.mAlpnProtocols = toLengthPrefixedList(bArr);
    }

    public static byte[] toLengthPrefixedList(byte[]... bArr) {
        if (bArr.length == 0) {
            throw new java.lang.IllegalArgumentException("items.length == 0");
        }
        int i = 0;
        for (byte[] bArr2 : bArr) {
            if (bArr2.length == 0 || bArr2.length > 255) {
                throw new java.lang.IllegalArgumentException("s.length == 0 || s.length > 255: " + bArr2.length);
            }
            i += bArr2.length + 1;
        }
        byte[] bArr3 = new byte[i];
        int length = bArr.length;
        int i2 = 0;
        int i3 = 0;
        while (i2 < length) {
            byte[] bArr4 = bArr[i2];
            int i4 = i3 + 1;
            bArr3[i3] = (byte) bArr4.length;
            int length2 = bArr4.length;
            int i5 = 0;
            while (i5 < length2) {
                bArr3[i4] = bArr4[i5];
                i5++;
                i4++;
            }
            i2++;
            i3 = i4;
        }
        return bArr3;
    }

    public byte[] getNpnSelectedProtocol(java.net.Socket socket) {
        return castToOpenSSLSocket(socket).getNpnSelectedProtocol();
    }

    public byte[] getAlpnSelectedProtocol(java.net.Socket socket) {
        return castToOpenSSLSocket(socket).getAlpnSelectedProtocol();
    }

    public void setKeyManagers(javax.net.ssl.KeyManager[] keyManagerArr) {
        this.mKeyManagers = keyManagerArr;
        this.mSecureFactory = null;
        this.mInsecureFactory = null;
    }

    public void setChannelIdPrivateKey(java.security.PrivateKey privateKey) {
        this.mChannelIdPrivateKey = privateKey;
    }

    public void setUseSessionTickets(java.net.Socket socket, boolean z) {
        castToOpenSSLSocket(socket).setUseSessionTickets(z);
    }

    public void setHostname(java.net.Socket socket, java.lang.String str) {
        castToOpenSSLSocket(socket).setHostname(str);
    }

    public void setSoWriteTimeout(java.net.Socket socket, int i) throws java.net.SocketException {
        castToOpenSSLSocket(socket).setSoWriteTimeout(i);
    }

    private static com.android.org.conscrypt.OpenSSLSocketImpl castToOpenSSLSocket(java.net.Socket socket) {
        if (!(socket instanceof com.android.org.conscrypt.OpenSSLSocketImpl)) {
            throw new java.lang.IllegalArgumentException("Socket not created by this factory: " + socket);
        }
        return (com.android.org.conscrypt.OpenSSLSocketImpl) socket;
    }

    @Override // javax.net.ssl.SSLSocketFactory
    public java.net.Socket createSocket(java.net.Socket socket, java.lang.String str, int i, boolean z) throws java.io.IOException {
        com.android.org.conscrypt.OpenSSLSocketImpl createSocket = getDelegate().createSocket(socket, str, i, z);
        createSocket.setNpnProtocols(this.mNpnProtocols);
        createSocket.setAlpnProtocols(this.mAlpnProtocols);
        createSocket.setHandshakeTimeout(this.mHandshakeTimeoutMillis);
        createSocket.setChannelIdPrivateKey(this.mChannelIdPrivateKey);
        if (this.mSecure) {
            verifyHostname(createSocket, str);
        }
        return createSocket;
    }

    @Override // javax.net.SocketFactory
    public java.net.Socket createSocket() throws java.io.IOException {
        com.android.org.conscrypt.OpenSSLSocketImpl createSocket = getDelegate().createSocket();
        createSocket.setNpnProtocols(this.mNpnProtocols);
        createSocket.setAlpnProtocols(this.mAlpnProtocols);
        createSocket.setHandshakeTimeout(this.mHandshakeTimeoutMillis);
        createSocket.setChannelIdPrivateKey(this.mChannelIdPrivateKey);
        return createSocket;
    }

    @Override // javax.net.SocketFactory
    public java.net.Socket createSocket(java.net.InetAddress inetAddress, int i, java.net.InetAddress inetAddress2, int i2) throws java.io.IOException {
        com.android.org.conscrypt.OpenSSLSocketImpl createSocket = getDelegate().createSocket(inetAddress, i, inetAddress2, i2);
        createSocket.setNpnProtocols(this.mNpnProtocols);
        createSocket.setAlpnProtocols(this.mAlpnProtocols);
        createSocket.setHandshakeTimeout(this.mHandshakeTimeoutMillis);
        createSocket.setChannelIdPrivateKey(this.mChannelIdPrivateKey);
        return createSocket;
    }

    @Override // javax.net.SocketFactory
    public java.net.Socket createSocket(java.net.InetAddress inetAddress, int i) throws java.io.IOException {
        com.android.org.conscrypt.OpenSSLSocketImpl createSocket = getDelegate().createSocket(inetAddress, i);
        createSocket.setNpnProtocols(this.mNpnProtocols);
        createSocket.setAlpnProtocols(this.mAlpnProtocols);
        createSocket.setHandshakeTimeout(this.mHandshakeTimeoutMillis);
        createSocket.setChannelIdPrivateKey(this.mChannelIdPrivateKey);
        return createSocket;
    }

    @Override // javax.net.SocketFactory
    public java.net.Socket createSocket(java.lang.String str, int i, java.net.InetAddress inetAddress, int i2) throws java.io.IOException {
        com.android.org.conscrypt.OpenSSLSocketImpl createSocket = getDelegate().createSocket(str, i, inetAddress, i2);
        createSocket.setNpnProtocols(this.mNpnProtocols);
        createSocket.setAlpnProtocols(this.mAlpnProtocols);
        createSocket.setHandshakeTimeout(this.mHandshakeTimeoutMillis);
        createSocket.setChannelIdPrivateKey(this.mChannelIdPrivateKey);
        if (this.mSecure) {
            verifyHostname(createSocket, str);
        }
        return createSocket;
    }

    @Override // javax.net.SocketFactory
    public java.net.Socket createSocket(java.lang.String str, int i) throws java.io.IOException {
        com.android.org.conscrypt.OpenSSLSocketImpl createSocket = getDelegate().createSocket(str, i);
        createSocket.setNpnProtocols(this.mNpnProtocols);
        createSocket.setAlpnProtocols(this.mAlpnProtocols);
        createSocket.setHandshakeTimeout(this.mHandshakeTimeoutMillis);
        createSocket.setChannelIdPrivateKey(this.mChannelIdPrivateKey);
        if (this.mSecure) {
            verifyHostname(createSocket, str);
        }
        return createSocket;
    }

    @Override // javax.net.ssl.SSLSocketFactory
    public java.lang.String[] getDefaultCipherSuites() {
        return getDelegate().getDefaultCipherSuites();
    }

    @Override // javax.net.ssl.SSLSocketFactory
    public java.lang.String[] getSupportedCipherSuites() {
        return getDelegate().getSupportedCipherSuites();
    }
}
