package com.android.server.net.watchlist;

/* loaded from: classes2.dex */
class WatchlistSettings {
    private static final java.lang.String FILE_NAME = "watchlist_settings.xml";
    private static final int SECRET_KEY_LENGTH = 48;
    private static final java.lang.String TAG = "WatchlistSettings";
    private static final com.android.server.net.watchlist.WatchlistSettings sInstance = new com.android.server.net.watchlist.WatchlistSettings();
    private byte[] mPrivacySecretKey;
    private final android.util.AtomicFile mXmlFile;

    public static com.android.server.net.watchlist.WatchlistSettings getInstance() {
        return sInstance;
    }

    private WatchlistSettings() {
        this(getSystemWatchlistFile());
    }

    static java.io.File getSystemWatchlistFile() {
        return new java.io.File(android.os.Environment.getDataSystemDirectory(), FILE_NAME);
    }

    @com.android.internal.annotations.VisibleForTesting
    protected WatchlistSettings(java.io.File file) {
        this.mPrivacySecretKey = null;
        this.mXmlFile = new android.util.AtomicFile(file, "net-watchlist");
        reloadSettings();
        if (this.mPrivacySecretKey == null) {
            this.mPrivacySecretKey = generatePrivacySecretKey();
            saveSettings();
        }
    }

    private void reloadSettings() {
        if (!this.mXmlFile.exists()) {
            return;
        }
        try {
            java.io.FileInputStream openRead = this.mXmlFile.openRead();
            try {
                com.android.modules.utils.TypedXmlPullParser resolvePullParser = android.util.Xml.resolvePullParser(openRead);
                com.android.internal.util.XmlUtils.beginDocument(resolvePullParser, "network-watchlist-settings");
                int depth = resolvePullParser.getDepth();
                while (com.android.internal.util.XmlUtils.nextElementWithin(resolvePullParser, depth)) {
                    if (resolvePullParser.getName().equals("secret-key")) {
                        this.mPrivacySecretKey = parseSecretKey(resolvePullParser);
                    }
                }
                android.util.Slog.i(TAG, "Reload watchlist settings done");
                if (openRead != null) {
                    openRead.close();
                }
            } catch (java.lang.Throwable th) {
                if (openRead != null) {
                    try {
                        openRead.close();
                    } catch (java.lang.Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        } catch (java.io.IOException | java.lang.IllegalStateException | java.lang.IndexOutOfBoundsException | java.lang.NullPointerException | java.lang.NumberFormatException | org.xmlpull.v1.XmlPullParserException e) {
            android.util.Slog.e(TAG, "Failed parsing xml", e);
        }
    }

    private byte[] parseSecretKey(org.xmlpull.v1.XmlPullParser xmlPullParser) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
        xmlPullParser.require(2, null, "secret-key");
        byte[] hexStringToByteArray = com.android.internal.util.HexDump.hexStringToByteArray(xmlPullParser.nextText());
        xmlPullParser.require(3, null, "secret-key");
        if (hexStringToByteArray == null || hexStringToByteArray.length != 48) {
            android.util.Log.e(TAG, "Unable to parse secret key");
            return null;
        }
        return hexStringToByteArray;
    }

    synchronized byte[] getPrivacySecretKey() {
        byte[] bArr;
        bArr = new byte[48];
        java.lang.System.arraycopy(this.mPrivacySecretKey, 0, bArr, 0, 48);
        return bArr;
    }

    private byte[] generatePrivacySecretKey() {
        byte[] bArr = new byte[48];
        new java.security.SecureRandom().nextBytes(bArr);
        return bArr;
    }

    private void saveSettings() {
        try {
            java.io.FileOutputStream startWrite = this.mXmlFile.startWrite();
            try {
                com.android.modules.utils.TypedXmlSerializer resolveSerializer = android.util.Xml.resolveSerializer(startWrite);
                resolveSerializer.startDocument((java.lang.String) null, true);
                resolveSerializer.startTag((java.lang.String) null, "network-watchlist-settings");
                resolveSerializer.startTag((java.lang.String) null, "secret-key");
                resolveSerializer.text(com.android.internal.util.HexDump.toHexString(this.mPrivacySecretKey));
                resolveSerializer.endTag((java.lang.String) null, "secret-key");
                resolveSerializer.endTag((java.lang.String) null, "network-watchlist-settings");
                resolveSerializer.endDocument();
                this.mXmlFile.finishWrite(startWrite);
            } catch (java.io.IOException e) {
                android.util.Log.w(TAG, "Failed to write display settings, restoring backup.", e);
                this.mXmlFile.failWrite(startWrite);
            }
        } catch (java.io.IOException e2) {
            android.util.Log.w(TAG, "Failed to write display settings: " + e2);
        }
    }
}
