package com.android.server.location.gnss;

/* loaded from: classes2.dex */
class GnssPsdsDownloader {
    static final int LONG_TERM_PSDS_SERVER_INDEX = 1;
    private static final long MAXIMUM_CONTENT_LENGTH_BYTES = 1000000;
    private static final int MAX_PSDS_TYPE_INDEX = 3;
    private static final int NORMAL_PSDS_SERVER_INDEX = 2;
    static final long PSDS_INTERVAL = 86400000;
    private static final int REALTIME_PSDS_SERVER_INDEX = 3;
    private final java.lang.String[] mLongTermPsdsServers;
    private int mNextServerIndex;
    private final java.lang.String[] mPsdsServers;
    private static final java.lang.String TAG = "GnssPsdsDownloader";
    private static final boolean DEBUG = android.util.Log.isLoggable(TAG, 3);
    private static final int CONNECTION_TIMEOUT_MS = (int) java.util.concurrent.TimeUnit.SECONDS.toMillis(30);
    private static final int READ_TIMEOUT_MS = (int) java.util.concurrent.TimeUnit.SECONDS.toMillis(60);

    GnssPsdsDownloader(java.util.Properties properties) {
        java.lang.String property = properties.getProperty("LONGTERM_PSDS_SERVER_1");
        java.lang.String property2 = properties.getProperty("LONGTERM_PSDS_SERVER_2");
        java.lang.String property3 = properties.getProperty("LONGTERM_PSDS_SERVER_3");
        int i = 1;
        int i2 = property != null ? 1 : 0;
        i2 = property2 != null ? i2 + 1 : i2;
        i2 = property3 != null ? i2 + 1 : i2;
        if (i2 == 0) {
            android.util.Log.e(TAG, "No Long-Term PSDS servers were specified in the GnssConfiguration");
            this.mLongTermPsdsServers = null;
        } else {
            this.mLongTermPsdsServers = new java.lang.String[i2];
            if (property != null) {
                this.mLongTermPsdsServers[0] = property;
            } else {
                i = 0;
            }
            if (property2 != null) {
                this.mLongTermPsdsServers[i] = property2;
                i++;
            }
            if (property3 != null) {
                this.mLongTermPsdsServers[i] = property3;
                i++;
            }
            this.mNextServerIndex = new java.util.Random().nextInt(i);
        }
        java.lang.String property4 = properties.getProperty("NORMAL_PSDS_SERVER");
        java.lang.String property5 = properties.getProperty("REALTIME_PSDS_SERVER");
        this.mPsdsServers = new java.lang.String[4];
        this.mPsdsServers[2] = property4;
        this.mPsdsServers[3] = property5;
    }

    @android.annotation.Nullable
    byte[] downloadPsdsData(int i) {
        int i2 = this.mNextServerIndex;
        byte[] bArr = null;
        if (i == 1 && this.mLongTermPsdsServers == null) {
            return null;
        }
        if (i > 1 && i <= 3 && this.mPsdsServers[i] == null) {
            return null;
        }
        if (i == 1) {
            while (bArr == null) {
                bArr = doDownloadWithTrafficAccounted(this.mLongTermPsdsServers[this.mNextServerIndex]);
                this.mNextServerIndex++;
                if (this.mNextServerIndex == this.mLongTermPsdsServers.length) {
                    this.mNextServerIndex = 0;
                }
                if (this.mNextServerIndex == i2) {
                    return bArr;
                }
            }
            return bArr;
        }
        if (i <= 1 || i > 3) {
            return null;
        }
        return doDownloadWithTrafficAccounted(this.mPsdsServers[i]);
    }

    @android.annotation.Nullable
    private byte[] doDownloadWithTrafficAccounted(java.lang.String str) {
        int andSetThreadStatsTag = android.net.TrafficStats.getAndSetThreadStatsTag(-188);
        try {
            return doDownload(str);
        } finally {
            android.net.TrafficStats.setThreadStatsTag(andSetThreadStatsTag);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:54:0x00d8  */
    /* JADX WARN: Type inference failed for: r11v0, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r11v2 */
    /* JADX WARN: Type inference failed for: r11v4, types: [java.net.HttpURLConnection] */
    @android.annotation.Nullable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private byte[] doDownload(java.lang.String str) {
        java.lang.Throwable th;
        java.net.HttpURLConnection httpURLConnection;
        if (DEBUG) {
            android.util.Log.d(TAG, "Downloading PSDS data from " + ((java.lang.String) str));
        }
        try {
            try {
                httpURLConnection = (java.net.HttpURLConnection) new java.net.URL(str).openConnection();
            } catch (java.io.IOException e) {
                e = e;
                httpURLConnection = null;
            } catch (java.lang.Throwable th2) {
                th = th2;
                str = 0;
                if (str != 0) {
                }
                throw th;
            }
            try {
                httpURLConnection.setRequestProperty("Accept", "*/*, application/vnd.wap.mms-message, application/vnd.wap.sic");
                httpURLConnection.setRequestProperty("x-wap-profile", "http://www.openmobilealliance.org/tech/profiles/UAPROF/ccppschema-20021212#");
                httpURLConnection.setConnectTimeout(CONNECTION_TIMEOUT_MS);
                httpURLConnection.setReadTimeout(READ_TIMEOUT_MS);
                httpURLConnection.connect();
                int responseCode = httpURLConnection.getResponseCode();
                if (responseCode != 200) {
                    if (DEBUG) {
                        android.util.Log.d(TAG, "HTTP error downloading gnss PSDS: " + responseCode);
                    }
                    httpURLConnection.disconnect();
                    return null;
                }
                java.io.InputStream inputStream = httpURLConnection.getInputStream();
                try {
                    java.io.ByteArrayOutputStream byteArrayOutputStream = new java.io.ByteArrayOutputStream();
                    byte[] bArr = new byte[1024];
                    do {
                        int read = inputStream.read(bArr);
                        if (read == -1) {
                            byte[] byteArray = byteArrayOutputStream.toByteArray();
                            inputStream.close();
                            httpURLConnection.disconnect();
                            return byteArray;
                        }
                        byteArrayOutputStream.write(bArr, 0, read);
                    } while (byteArrayOutputStream.size() <= MAXIMUM_CONTENT_LENGTH_BYTES);
                    if (DEBUG) {
                        android.util.Log.d(TAG, "PSDS file too large");
                    }
                    inputStream.close();
                    httpURLConnection.disconnect();
                    return null;
                } catch (java.lang.Throwable th3) {
                    if (inputStream != null) {
                        try {
                            inputStream.close();
                        } catch (java.lang.Throwable th4) {
                            th3.addSuppressed(th4);
                        }
                    }
                    throw th3;
                }
            } catch (java.io.IOException e2) {
                e = e2;
                if (DEBUG) {
                    android.util.Log.d(TAG, "Error downloading gnss PSDS: ", e);
                }
                if (httpURLConnection != null) {
                    httpURLConnection.disconnect();
                }
                return null;
            }
        } catch (java.lang.Throwable th5) {
            th = th5;
            if (str != 0) {
                str.disconnect();
            }
            throw th;
        }
    }
}
