package android.util;

/* loaded from: classes3.dex */
public abstract class NtpTrustedTime implements android.util.TrustedTime {
    private static final boolean LOGD = false;
    public static final java.lang.String NTP_SETTING_SERVER_NAME_DELIMITER = "|";
    private static final java.lang.String NTP_SETTING_SERVER_NAME_DELIMITER_REGEXP = "\\|";
    private static final java.lang.String TAG = "NtpTrustedTime";
    private static final java.lang.String URI_SCHEME_NTP = "ntp";
    private static android.util.NtpTrustedTime sSingleton;
    private volatile java.net.URI mLastSuccessfulNtpServerUri;
    private android.util.NtpTrustedTime.NtpConfig mNtpConfigForTests;
    private volatile android.util.NtpTrustedTime.TimeResult mTimeResult;
    private final java.lang.Object mRefreshLock = new java.lang.Object();
    private final java.lang.Object mConfigLock = new java.lang.Object();

    public abstract android.net.Network getDefaultNetwork();

    public abstract android.util.NtpTrustedTime.NtpConfig getNtpConfigInternal();

    public abstract boolean isNetworkConnected(android.net.Network network);

    public abstract android.util.NtpTrustedTime.TimeResult queryNtpServer(android.net.Network network, java.net.URI uri, java.time.Duration duration);

    public static final class NtpConfig {
        private final java.util.List<java.net.URI> mServerUris;
        private final java.time.Duration mTimeout;

        public NtpConfig(java.util.List<java.net.URI> list, java.time.Duration duration) throws java.lang.IllegalArgumentException {
            java.util.Objects.requireNonNull(list);
            if (list.isEmpty()) {
                throw new java.lang.IllegalArgumentException("Server URIs is empty");
            }
            java.util.ArrayList arrayList = new java.util.ArrayList();
            java.util.Iterator<java.net.URI> it = list.iterator();
            while (it.hasNext()) {
                try {
                    arrayList.add(android.util.NtpTrustedTime.validateNtpServerUri((java.net.URI) java.util.Objects.requireNonNull(it.next())));
                } catch (java.net.URISyntaxException e) {
                    throw new java.lang.IllegalArgumentException("Bad server URI", e);
                }
            }
            this.mServerUris = java.util.Collections.unmodifiableList(arrayList);
            if (duration.isNegative() || duration.isZero()) {
                throw new java.lang.IllegalArgumentException("timeout < 0");
            }
            this.mTimeout = duration;
        }

        public java.util.List<java.net.URI> getServerUris() {
            return this.mServerUris;
        }

        public java.time.Duration getTimeout() {
            return this.mTimeout;
        }

        public java.lang.String toString() {
            return "NtpConnectionInfo{mServerUris=" + this.mServerUris + ", mTimeout=" + this.mTimeout + '}';
        }
    }

    public static class TimeResult {
        private final long mElapsedRealtimeMillis;
        private final java.net.InetSocketAddress mNtpServerSocketAddress;
        private final int mUncertaintyMillis;
        private final long mUnixEpochTimeMillis;

        public TimeResult(long j, long j2, int i, java.net.InetSocketAddress inetSocketAddress) {
            this.mUnixEpochTimeMillis = j;
            this.mElapsedRealtimeMillis = j2;
            this.mUncertaintyMillis = i;
            this.mNtpServerSocketAddress = (java.net.InetSocketAddress) java.util.Objects.requireNonNull(inetSocketAddress);
        }

        public long getTimeMillis() {
            return this.mUnixEpochTimeMillis;
        }

        public long getElapsedRealtimeMillis() {
            return this.mElapsedRealtimeMillis;
        }

        public int getUncertaintyMillis() {
            return this.mUncertaintyMillis;
        }

        public long currentTimeMillis() {
            return this.mUnixEpochTimeMillis + getAgeMillis();
        }

        public long getAgeMillis() {
            return getAgeMillis(android.os.SystemClock.elapsedRealtime());
        }

        public long getAgeMillis(long j) {
            return j - this.mElapsedRealtimeMillis;
        }

        public boolean equals(java.lang.Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof android.util.NtpTrustedTime.TimeResult)) {
                return false;
            }
            android.util.NtpTrustedTime.TimeResult timeResult = (android.util.NtpTrustedTime.TimeResult) obj;
            return this.mUnixEpochTimeMillis == timeResult.mUnixEpochTimeMillis && this.mElapsedRealtimeMillis == timeResult.mElapsedRealtimeMillis && this.mUncertaintyMillis == timeResult.mUncertaintyMillis && this.mNtpServerSocketAddress.equals(timeResult.mNtpServerSocketAddress);
        }

        public int hashCode() {
            return java.util.Objects.hash(java.lang.Long.valueOf(this.mUnixEpochTimeMillis), java.lang.Long.valueOf(this.mElapsedRealtimeMillis), java.lang.Integer.valueOf(this.mUncertaintyMillis), this.mNtpServerSocketAddress);
        }

        public java.lang.String toString() {
            return "TimeResult{unixEpochTime=" + java.time.Instant.ofEpochMilli(this.mUnixEpochTimeMillis) + ", elapsedRealtime=" + java.time.Duration.ofMillis(this.mElapsedRealtimeMillis) + ", mUncertaintyMillis=" + this.mUncertaintyMillis + ", mNtpServerSocketAddress=" + this.mNtpServerSocketAddress + '}';
        }
    }

    protected NtpTrustedTime() {
    }

    public static synchronized android.util.NtpTrustedTime getInstance(android.content.Context context) {
        android.util.NtpTrustedTime ntpTrustedTime;
        synchronized (android.util.NtpTrustedTime.class) {
            if (sSingleton == null) {
                sSingleton = new android.util.NtpTrustedTime.NtpTrustedTimeImpl(context.getApplicationContext());
            }
            ntpTrustedTime = sSingleton;
        }
        return ntpTrustedTime;
    }

    public void setServerConfigForTests(android.util.NtpTrustedTime.NtpConfig ntpConfig) {
        synchronized (this.mConfigLock) {
            this.mNtpConfigForTests = ntpConfig;
        }
    }

    @Override // android.util.TrustedTime
    public boolean forceRefresh() {
        synchronized (this.mRefreshLock) {
            android.net.Network defaultNetwork = getDefaultNetwork();
            if (defaultNetwork == null) {
                return false;
            }
            return forceRefreshLocked(defaultNetwork);
        }
    }

    public boolean forceRefresh(android.net.Network network) {
        boolean forceRefreshLocked;
        java.util.Objects.requireNonNull(network);
        synchronized (this.mRefreshLock) {
            forceRefreshLocked = forceRefreshLocked(network);
        }
        return forceRefreshLocked;
    }

    private boolean forceRefreshLocked(android.net.Network network) {
        android.util.NtpTrustedTime.NtpConfig ntpConfig;
        java.util.Objects.requireNonNull(network);
        if (!isNetworkConnected(network) || (ntpConfig = getNtpConfig()) == null) {
            return false;
        }
        java.util.List<java.net.URI> serverUris = ntpConfig.getServerUris();
        java.util.ArrayList<java.net.URI> arrayList = new java.util.ArrayList();
        for (java.net.URI uri : serverUris) {
            if (uri.equals(this.mLastSuccessfulNtpServerUri)) {
                arrayList.add(0, uri);
            } else {
                arrayList.add(uri);
            }
        }
        for (java.net.URI uri2 : arrayList) {
            android.util.NtpTrustedTime.TimeResult queryNtpServer = queryNtpServer(network, uri2, ntpConfig.getTimeout());
            if (queryNtpServer != null) {
                this.mLastSuccessfulNtpServerUri = uri2;
                this.mTimeResult = queryNtpServer;
                return true;
            }
        }
        return false;
    }

    private android.util.NtpTrustedTime.NtpConfig getNtpConfig() {
        synchronized (this.mConfigLock) {
            if (this.mNtpConfigForTests != null) {
                return this.mNtpConfigForTests;
            }
            return getNtpConfigInternal();
        }
    }

    @Override // android.util.TrustedTime
    @java.lang.Deprecated
    public boolean hasCache() {
        return this.mTimeResult != null;
    }

    @Override // android.util.TrustedTime
    @java.lang.Deprecated
    public long getCacheAge() {
        android.util.NtpTrustedTime.TimeResult timeResult = this.mTimeResult;
        if (timeResult != null) {
            return android.os.SystemClock.elapsedRealtime() - timeResult.getElapsedRealtimeMillis();
        }
        return Long.MAX_VALUE;
    }

    @Override // android.util.TrustedTime
    @java.lang.Deprecated
    public long currentTimeMillis() {
        android.util.NtpTrustedTime.TimeResult timeResult = this.mTimeResult;
        if (timeResult == null) {
            throw new java.lang.IllegalStateException("Missing authoritative time source");
        }
        return timeResult.currentTimeMillis();
    }

    @java.lang.Deprecated
    public long getCachedNtpTime() {
        android.util.NtpTrustedTime.TimeResult timeResult = this.mTimeResult;
        if (timeResult == null) {
            return 0L;
        }
        return timeResult.getTimeMillis();
    }

    @java.lang.Deprecated
    public long getCachedNtpTimeReference() {
        android.util.NtpTrustedTime.TimeResult timeResult = this.mTimeResult;
        if (timeResult == null) {
            return 0L;
        }
        return timeResult.getElapsedRealtimeMillis();
    }

    public android.util.NtpTrustedTime.TimeResult getCachedTimeResult() {
        return this.mTimeResult;
    }

    public void setCachedTimeResult(android.util.NtpTrustedTime.TimeResult timeResult) {
        synchronized (this.mRefreshLock) {
            this.mTimeResult = timeResult;
        }
    }

    public void clearCachedTimeResult() {
        synchronized (this.mRefreshLock) {
            this.mTimeResult = null;
        }
    }

    public static java.net.URI parseNtpUriStrict(java.lang.String str) throws java.net.URISyntaxException {
        return validateNtpServerUri(new java.net.URI(str));
    }

    public static java.util.List<java.net.URI> parseNtpServerSetting(java.lang.String str) {
        if (android.text.TextUtils.isEmpty(str)) {
            return null;
        }
        java.lang.String[] split = str.split(NTP_SETTING_SERVER_NAME_DELIMITER_REGEXP);
        if (split.length == 0) {
            return null;
        }
        java.util.ArrayList arrayList = new java.util.ArrayList();
        for (java.lang.String str2 : split) {
            if (str2.startsWith("ntp:")) {
                try {
                    arrayList.add(parseNtpUriStrict(str2));
                } catch (java.net.URISyntaxException e) {
                    android.util.Log.w(TAG, "Rejected NTP uri setting=" + str, e);
                    return null;
                }
            } else {
                try {
                    arrayList.add(validateNtpServerUri(new java.net.URI(URI_SCHEME_NTP, str2, null, null)));
                } catch (java.net.URISyntaxException e2) {
                    android.util.Log.w(TAG, "Rejected NTP legacy setting=" + str, e2);
                    return null;
                }
            }
        }
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static java.net.URI validateNtpServerUri(java.net.URI uri) throws java.net.URISyntaxException {
        if (!uri.isAbsolute()) {
            throw new java.net.URISyntaxException(uri.toString(), "Relative URI not supported");
        }
        if (!URI_SCHEME_NTP.equals(uri.getScheme())) {
            throw new java.net.URISyntaxException(uri.toString(), "Unrecognized scheme");
        }
        if (android.text.TextUtils.isEmpty(uri.getHost())) {
            throw new java.net.URISyntaxException(uri.toString(), "Missing host");
        }
        return uri;
    }

    public void dump(java.io.PrintWriter printWriter) {
        synchronized (this.mConfigLock) {
            printWriter.println("getNtpConfig()=" + getNtpConfig());
            printWriter.println("mNtpConfigForTests=" + this.mNtpConfigForTests);
        }
        printWriter.println("mLastSuccessfulNtpServerUri=" + this.mLastSuccessfulNtpServerUri);
        android.util.NtpTrustedTime.TimeResult timeResult = this.mTimeResult;
        printWriter.println("mTimeResult=" + timeResult);
        if (timeResult != null) {
            printWriter.println("mTimeResult.getAgeMillis()=" + java.time.Duration.ofMillis(timeResult.getAgeMillis()));
        }
    }

    private static final class NtpTrustedTimeImpl extends android.util.NtpTrustedTime {
        private android.net.ConnectivityManager mConnectivityManager;
        private final android.content.Context mContext;

        private NtpTrustedTimeImpl(android.content.Context context) {
            this.mContext = (android.content.Context) java.util.Objects.requireNonNull(context);
        }

        @Override // android.util.NtpTrustedTime
        public android.util.NtpTrustedTime.NtpConfig getNtpConfigInternal() {
            android.content.ContentResolver contentResolver = this.mContext.getContentResolver();
            android.content.res.Resources resources = this.mContext.getResources();
            java.util.List<java.net.URI> parseNtpServerSetting = parseNtpServerSetting(android.provider.Settings.Global.getString(contentResolver, android.provider.Settings.Global.NTP_SERVER));
            if (parseNtpServerSetting == null) {
                java.lang.String[] stringArray = resources.getStringArray(com.android.internal.R.array.config_ntpServers);
                try {
                    java.util.ArrayList arrayList = new java.util.ArrayList();
                    for (java.lang.String str : stringArray) {
                        arrayList.add(parseNtpUriStrict(str));
                    }
                    parseNtpServerSetting = arrayList;
                } catch (java.net.URISyntaxException e) {
                    parseNtpServerSetting = null;
                }
            }
            java.time.Duration ofMillis = java.time.Duration.ofMillis(android.provider.Settings.Global.getInt(contentResolver, android.provider.Settings.Global.NTP_TIMEOUT, resources.getInteger(com.android.internal.R.integer.config_ntpTimeout)));
            if (parseNtpServerSetting == null) {
                return null;
            }
            return new android.util.NtpTrustedTime.NtpConfig(parseNtpServerSetting, ofMillis);
        }

        @Override // android.util.NtpTrustedTime
        public android.net.Network getDefaultNetwork() {
            android.net.ConnectivityManager connectivityManager = getConnectivityManager();
            if (connectivityManager == null) {
                return null;
            }
            return connectivityManager.getActiveNetwork();
        }

        @Override // android.util.NtpTrustedTime
        public boolean isNetworkConnected(android.net.Network network) {
            android.net.NetworkInfo networkInfo;
            android.net.ConnectivityManager connectivityManager = getConnectivityManager();
            if (connectivityManager == null || (networkInfo = connectivityManager.getNetworkInfo(network)) == null || !networkInfo.isConnected()) {
                return false;
            }
            return true;
        }

        private synchronized android.net.ConnectivityManager getConnectivityManager() {
            if (this.mConnectivityManager == null) {
                this.mConnectivityManager = (android.net.ConnectivityManager) this.mContext.getSystemService(android.net.ConnectivityManager.class);
            }
            return this.mConnectivityManager;
        }

        @Override // android.util.NtpTrustedTime
        public android.util.NtpTrustedTime.TimeResult queryNtpServer(android.net.Network network, java.net.URI uri, java.time.Duration duration) {
            android.net.SntpClient sntpClient = new android.net.SntpClient();
            if (sntpClient.requestTime(uri.getHost(), uri.getPort() == -1 ? 123 : uri.getPort(), saturatedCast(duration.toMillis()), network)) {
                return new android.util.NtpTrustedTime.TimeResult(sntpClient.getNtpTime(), sntpClient.getNtpTimeReference(), saturatedCast(sntpClient.getRoundTripTime() / 2), sntpClient.getServerSocketAddress());
            }
            return null;
        }

        private static int saturatedCast(long j) {
            if (j > 2147483647L) {
                return Integer.MAX_VALUE;
            }
            if (j < -2147483648L) {
                return Integer.MIN_VALUE;
            }
            return (int) j;
        }
    }
}
