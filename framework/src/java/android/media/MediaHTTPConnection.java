package android.media;

/* loaded from: classes2.dex */
public class MediaHTTPConnection extends android.media.IMediaHTTPConnection.Stub {
    private static final int CONNECT_TIMEOUT_MS = 30000;
    private static final int HTTP_TEMP_REDIRECT = 307;
    private static final int MAX_REDIRECTS = 20;
    private static final java.lang.String TAG = "MediaHTTPConnection";
    private static final boolean VERBOSE = false;
    private long mNativeContext;
    private long mCurrentOffset = -1;
    private java.net.URL mURL = null;
    private java.util.Map<java.lang.String, java.lang.String> mHeaders = null;
    private volatile java.net.HttpURLConnection mConnection = null;
    private long mTotalSize = -1;
    private java.io.InputStream mInputStream = null;
    private boolean mAllowCrossDomainRedirect = true;
    private boolean mAllowCrossProtocolRedirect = true;
    private final java.util.concurrent.atomic.AtomicInteger mNumDisconnectingThreads = new java.util.concurrent.atomic.AtomicInteger(0);

    private final native void native_finalize();

    private final native android.os.IBinder native_getIMemory();

    private static final native void native_init();

    private final native int native_readAt(long j, int i);

    private final native void native_setup();

    public MediaHTTPConnection() {
        if (java.net.CookieHandler.getDefault() == null) {
            android.util.Log.w(TAG, "MediaHTTPConnection: Unexpected. No CookieHandler found.");
        }
        native_setup();
    }

    @Override // android.media.IMediaHTTPConnection
    public synchronized android.os.IBinder connect(java.lang.String str, java.lang.String str2) {
        try {
            disconnect();
            this.mAllowCrossDomainRedirect = true;
            this.mURL = new java.net.URL(str);
            this.mHeaders = convertHeaderStringToMap(str2);
        } catch (java.net.MalformedURLException e) {
            return null;
        }
        return native_getIMemory();
    }

    private static boolean parseBoolean(java.lang.String str) {
        try {
            return java.lang.Long.parseLong(str) != 0;
        } catch (java.lang.NumberFormatException e) {
            return "true".equalsIgnoreCase(str) || android.media.MediaMetrics.Value.YES.equalsIgnoreCase(str);
        }
    }

    private synchronized boolean filterOutInternalHeaders(java.lang.String str, java.lang.String str2) {
        if (!"android-allow-cross-domain-redirect".equalsIgnoreCase(str)) {
            return false;
        }
        this.mAllowCrossDomainRedirect = parseBoolean(str2);
        this.mAllowCrossProtocolRedirect = this.mAllowCrossDomainRedirect;
        return true;
    }

    private synchronized java.util.Map<java.lang.String, java.lang.String> convertHeaderStringToMap(java.lang.String str) {
        java.util.HashMap hashMap;
        hashMap = new java.util.HashMap();
        for (java.lang.String str2 : str.split("\r\n")) {
            int indexOf = str2.indexOf(":");
            if (indexOf >= 0) {
                java.lang.String substring = str2.substring(0, indexOf);
                java.lang.String substring2 = str2.substring(indexOf + 1);
                if (!filterOutInternalHeaders(substring, substring2)) {
                    hashMap.put(substring, substring2);
                }
            }
        }
        return hashMap;
    }

    @Override // android.media.IMediaHTTPConnection
    public void disconnect() {
        this.mNumDisconnectingThreads.incrementAndGet();
        try {
            java.net.HttpURLConnection httpURLConnection = this.mConnection;
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
            synchronized (this) {
                teardownConnection();
                this.mHeaders = null;
                this.mURL = null;
            }
        } finally {
            this.mNumDisconnectingThreads.decrementAndGet();
        }
    }

    private synchronized void teardownConnection() {
        if (this.mConnection != null) {
            if (this.mInputStream != null) {
                try {
                    this.mInputStream.close();
                } catch (java.io.IOException e) {
                }
                this.mInputStream = null;
            }
            this.mConnection.disconnect();
            this.mConnection = null;
            this.mCurrentOffset = -1L;
        }
    }

    private static final boolean isLocalHost(java.net.URL url) {
        java.lang.String host;
        if (url == null || (host = url.getHost()) == null) {
            return false;
        }
        if (host.equalsIgnoreCase("localhost")) {
            return true;
        }
        return android.net.InetAddresses.parseNumericAddress(host).isLoopbackAddress();
    }

    /* JADX WARN: Code restructure failed: missing block: B:98:0x01a6, code lost:
    
        r9.mURL = r6;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private synchronized void seekTo(long j) throws java.io.IOException {
        int lastIndexOf;
        teardownConnection();
        try {
            java.net.URL url = this.mURL;
            boolean isLocalHost = isLocalHost(url);
            int i = 0;
            while (this.mNumDisconnectingThreads.get() <= 0) {
                if (isLocalHost) {
                    this.mConnection = (java.net.HttpURLConnection) url.openConnection(java.net.Proxy.NO_PROXY);
                } else {
                    this.mConnection = (java.net.HttpURLConnection) url.openConnection();
                }
                if (this.mNumDisconnectingThreads.get() > 0) {
                    throw new java.io.IOException("concurrently disconnecting");
                }
                this.mConnection.setConnectTimeout(30000);
                this.mConnection.setInstanceFollowRedirects(this.mAllowCrossDomainRedirect);
                if (this.mHeaders != null) {
                    for (java.util.Map.Entry<java.lang.String, java.lang.String> entry : this.mHeaders.entrySet()) {
                        this.mConnection.setRequestProperty(entry.getKey(), entry.getValue());
                    }
                }
                if (j > 0) {
                    this.mConnection.setRequestProperty("Range", "bytes=" + j + com.android.internal.content.NativeLibraryHelper.CLEAR_ABI_OVERRIDE);
                }
                int responseCode = this.mConnection.getResponseCode();
                if (responseCode == 300 || responseCode == 301 || responseCode == 302 || responseCode == 303 || responseCode == 307) {
                    i++;
                    if (i > 20) {
                        throw new java.net.NoRouteToHostException("Too many redirects: " + i);
                    }
                    java.lang.String requestMethod = this.mConnection.getRequestMethod();
                    if (responseCode == 307 && !requestMethod.equals("GET") && !requestMethod.equals("HEAD")) {
                        throw new java.net.NoRouteToHostException("Invalid redirect");
                    }
                    java.lang.String headerField = this.mConnection.getHeaderField("Location");
                    if (headerField == null) {
                        throw new java.net.NoRouteToHostException("Invalid redirect");
                    }
                    java.net.URL url2 = new java.net.URL(this.mURL, headerField);
                    if (!url2.getProtocol().equals(android.content.IntentFilter.SCHEME_HTTPS) && !url2.getProtocol().equals(android.content.IntentFilter.SCHEME_HTTP)) {
                        throw new java.net.NoRouteToHostException("Unsupported protocol redirect");
                    }
                    boolean equals = this.mURL.getProtocol().equals(url2.getProtocol());
                    if (!this.mAllowCrossProtocolRedirect && !equals) {
                        throw new java.net.NoRouteToHostException("Cross-protocol redirects are disallowed");
                    }
                    boolean equals2 = this.mURL.getHost().equals(url2.getHost());
                    if (!this.mAllowCrossDomainRedirect && !equals2) {
                        throw new java.net.NoRouteToHostException("Cross-domain redirects are disallowed");
                    }
                    url = url2;
                } else {
                    if (this.mAllowCrossDomainRedirect) {
                        this.mURL = this.mConnection.getURL();
                    }
                    if (responseCode == 206) {
                        java.lang.String headerField2 = this.mConnection.getHeaderField("Content-Range");
                        this.mTotalSize = -1L;
                        if (headerField2 != null && (lastIndexOf = headerField2.lastIndexOf(47)) >= 0) {
                            try {
                                this.mTotalSize = java.lang.Long.parseLong(headerField2.substring(lastIndexOf + 1));
                            } catch (java.lang.NumberFormatException e) {
                            }
                        }
                    } else {
                        if (responseCode != 200) {
                            throw new java.io.IOException();
                        }
                        this.mTotalSize = this.mConnection.getContentLength();
                    }
                    if (j > 0 && responseCode != 206) {
                        throw new java.net.ProtocolException();
                    }
                    this.mInputStream = new java.io.BufferedInputStream(this.mConnection.getInputStream());
                    this.mCurrentOffset = j;
                }
            }
            throw new java.io.IOException("concurrently disconnecting");
        } catch (java.io.IOException e2) {
            this.mTotalSize = -1L;
            teardownConnection();
            this.mCurrentOffset = -1L;
            throw e2;
        }
    }

    @Override // android.media.IMediaHTTPConnection
    public synchronized int readAt(long j, int i) {
        return native_readAt(j, i);
    }

    private synchronized int readAt(long j, byte[] bArr, int i) {
        int i2;
        android.os.StrictMode.setThreadPolicy(new android.os.StrictMode.ThreadPolicy.Builder().permitAll().build());
        try {
            try {
                try {
                    if (j != this.mCurrentOffset) {
                        seekTo(j);
                    }
                    i2 = 0;
                    int read = this.mInputStream.read(bArr, 0, i);
                    if (read != -1) {
                        i2 = read;
                    }
                    this.mCurrentOffset += i2;
                } catch (java.net.ProtocolException e) {
                    android.util.Log.w(TAG, "readAt " + j + " / " + i + " => " + e);
                    return android.media.MediaPlayer.MEDIA_ERROR_UNSUPPORTED;
                } catch (java.io.IOException e2) {
                    return -1;
                }
            } catch (java.net.NoRouteToHostException e3) {
                android.util.Log.w(TAG, "readAt " + j + " / " + i + " => " + e3);
                return android.media.MediaPlayer.MEDIA_ERROR_UNSUPPORTED;
            } catch (java.lang.Exception e4) {
                return -1;
            }
        } catch (java.net.UnknownServiceException e5) {
            android.util.Log.w(TAG, "readAt " + j + " / " + i + " => " + e5);
            return android.media.MediaPlayer.MEDIA_ERROR_UNSUPPORTED;
        }
        return i2;
    }

    @Override // android.media.IMediaHTTPConnection
    public synchronized long getSize() {
        if (this.mConnection == null) {
            try {
                seekTo(0L);
            } catch (java.io.IOException e) {
                return -1L;
            }
        }
        return this.mTotalSize;
    }

    @Override // android.media.IMediaHTTPConnection
    public synchronized java.lang.String getMIMEType() {
        if (this.mConnection == null) {
            try {
                seekTo(0L);
            } catch (java.io.IOException e) {
                return "application/octet-stream";
            }
        }
        return this.mConnection.getContentType();
    }

    @Override // android.media.IMediaHTTPConnection
    public synchronized java.lang.String getUri() {
        return this.mURL.toString();
    }

    protected void finalize() {
        native_finalize();
    }

    static {
        java.lang.System.loadLibrary("media_jni");
        native_init();
    }
}
