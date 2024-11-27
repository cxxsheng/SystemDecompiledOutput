package com.android.server.net.watchlist;

/* loaded from: classes2.dex */
class WatchlistConfig {
    private static final java.lang.String NETWORK_WATCHLIST_DB_FOR_TEST_PATH = "/data/misc/network_watchlist/network_watchlist_for_test.xml";
    private static final java.lang.String NETWORK_WATCHLIST_DB_PATH = "/data/misc/network_watchlist/network_watchlist.xml";
    private static final java.lang.String TAG = "WatchlistConfig";
    private static final com.android.server.net.watchlist.WatchlistConfig sInstance = new com.android.server.net.watchlist.WatchlistConfig();
    private volatile com.android.server.net.watchlist.WatchlistConfig.CrcShaDigests mDomainDigests;
    private volatile com.android.server.net.watchlist.WatchlistConfig.CrcShaDigests mIpDigests;
    private boolean mIsSecureConfig;
    private java.io.File mXmlFile;

    private static class XmlTags {
        private static final java.lang.String CRC32_DOMAIN = "crc32-domain";
        private static final java.lang.String CRC32_IP = "crc32-ip";
        private static final java.lang.String HASH = "hash";
        private static final java.lang.String SHA256_DOMAIN = "sha256-domain";
        private static final java.lang.String SHA256_IP = "sha256-ip";
        private static final java.lang.String WATCHLIST_CONFIG = "watchlist-config";

        private XmlTags() {
        }
    }

    private static class CrcShaDigests {
        public final com.android.server.net.watchlist.HarmfulCrcs crc32s;
        public final com.android.server.net.watchlist.HarmfulDigests sha256Digests;

        CrcShaDigests(com.android.server.net.watchlist.HarmfulCrcs harmfulCrcs, com.android.server.net.watchlist.HarmfulDigests harmfulDigests) {
            this.crc32s = harmfulCrcs;
            this.sha256Digests = harmfulDigests;
        }
    }

    public static com.android.server.net.watchlist.WatchlistConfig getInstance() {
        return sInstance;
    }

    private WatchlistConfig() {
        this(new java.io.File(NETWORK_WATCHLIST_DB_PATH));
    }

    @com.android.internal.annotations.VisibleForTesting
    protected WatchlistConfig(java.io.File file) {
        this.mIsSecureConfig = true;
        this.mXmlFile = file;
        reloadConfig();
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public void reloadConfig() {
        if (!this.mXmlFile.exists()) {
            return;
        }
        try {
            java.io.FileInputStream fileInputStream = new java.io.FileInputStream(this.mXmlFile);
            try {
                java.util.List<byte[]> arrayList = new java.util.ArrayList<>();
                java.util.List<byte[]> arrayList2 = new java.util.ArrayList<>();
                java.util.List<byte[]> arrayList3 = new java.util.ArrayList<>();
                java.util.List<byte[]> arrayList4 = new java.util.ArrayList<>();
                org.xmlpull.v1.XmlPullParser newPullParser = android.util.Xml.newPullParser();
                newPullParser.setInput(fileInputStream, java.nio.charset.StandardCharsets.UTF_8.name());
                newPullParser.nextTag();
                newPullParser.require(2, null, "watchlist-config");
                while (true) {
                    char c = 3;
                    if (newPullParser.nextTag() == 2) {
                        java.lang.String name = newPullParser.getName();
                        switch (name.hashCode()) {
                            case -1862636386:
                                if (name.equals("crc32-domain")) {
                                    c = 0;
                                    break;
                                }
                                c = 65535;
                                break;
                            case -14835926:
                                if (name.equals("sha256-domain")) {
                                    c = 2;
                                    break;
                                }
                                c = 65535;
                                break;
                            case 835385997:
                                if (name.equals("sha256-ip")) {
                                    break;
                                }
                                c = 65535;
                                break;
                            case 1718657537:
                                if (name.equals("crc32-ip")) {
                                    c = 1;
                                    break;
                                }
                                c = 65535;
                                break;
                            default:
                                c = 65535;
                                break;
                        }
                        switch (c) {
                            case 0:
                                parseHashes(newPullParser, name, arrayList);
                                break;
                            case 1:
                                parseHashes(newPullParser, name, arrayList3);
                                break;
                            case 2:
                                parseHashes(newPullParser, name, arrayList2);
                                break;
                            case 3:
                                parseHashes(newPullParser, name, arrayList4);
                                break;
                            default:
                                android.util.Log.w(TAG, "Unknown element: " + newPullParser.getName());
                                com.android.internal.util.XmlUtils.skipCurrentTag(newPullParser);
                                break;
                        }
                    } else {
                        newPullParser.require(3, null, "watchlist-config");
                        this.mDomainDigests = new com.android.server.net.watchlist.WatchlistConfig.CrcShaDigests(new com.android.server.net.watchlist.HarmfulCrcs(arrayList), new com.android.server.net.watchlist.HarmfulDigests(arrayList2));
                        this.mIpDigests = new com.android.server.net.watchlist.WatchlistConfig.CrcShaDigests(new com.android.server.net.watchlist.HarmfulCrcs(arrayList3), new com.android.server.net.watchlist.HarmfulDigests(arrayList4));
                        android.util.Log.i(TAG, "Reload watchlist done");
                        fileInputStream.close();
                        return;
                    }
                }
            } catch (java.lang.Throwable th) {
                try {
                    fileInputStream.close();
                } catch (java.lang.Throwable th2) {
                    th.addSuppressed(th2);
                }
                throw th;
            }
        } catch (java.io.IOException | java.lang.IllegalStateException | java.lang.IndexOutOfBoundsException | java.lang.NullPointerException | java.lang.NumberFormatException | org.xmlpull.v1.XmlPullParserException e) {
            android.util.Slog.e(TAG, "Failed parsing xml", e);
        }
    }

    private void parseHashes(org.xmlpull.v1.XmlPullParser xmlPullParser, java.lang.String str, java.util.List<byte[]> list) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
        xmlPullParser.require(2, null, str);
        while (xmlPullParser.nextTag() == 2) {
            xmlPullParser.require(2, null, "hash");
            byte[] hexStringToByteArray = com.android.internal.util.HexDump.hexStringToByteArray(xmlPullParser.nextText());
            xmlPullParser.require(3, null, "hash");
            list.add(hexStringToByteArray);
        }
        xmlPullParser.require(3, null, str);
    }

    public boolean containsDomain(java.lang.String str) {
        com.android.server.net.watchlist.WatchlistConfig.CrcShaDigests crcShaDigests = this.mDomainDigests;
        if (crcShaDigests == null) {
            return false;
        }
        if (!crcShaDigests.crc32s.contains(getCrc32(str))) {
            return false;
        }
        return crcShaDigests.sha256Digests.contains(getSha256(str));
    }

    public boolean containsIp(java.lang.String str) {
        com.android.server.net.watchlist.WatchlistConfig.CrcShaDigests crcShaDigests = this.mIpDigests;
        if (crcShaDigests == null) {
            return false;
        }
        if (!crcShaDigests.crc32s.contains(getCrc32(str))) {
            return false;
        }
        return crcShaDigests.sha256Digests.contains(getSha256(str));
    }

    private int getCrc32(java.lang.String str) {
        java.util.zip.CRC32 crc32 = new java.util.zip.CRC32();
        crc32.update(str.getBytes());
        return (int) crc32.getValue();
    }

    private byte[] getSha256(java.lang.String str) {
        try {
            java.security.MessageDigest messageDigest = java.security.MessageDigest.getInstance("SHA256");
            messageDigest.update(str.getBytes());
            return messageDigest.digest();
        } catch (java.security.NoSuchAlgorithmException e) {
            return null;
        }
    }

    public boolean isConfigSecure() {
        return this.mIsSecureConfig;
    }

    @android.annotation.Nullable
    public byte[] getWatchlistConfigHash() {
        if (!this.mXmlFile.exists()) {
            return null;
        }
        try {
            return com.android.server.net.watchlist.DigestUtils.getSha256Hash(this.mXmlFile);
        } catch (java.io.IOException | java.security.NoSuchAlgorithmException e) {
            android.util.Log.e(TAG, "Unable to get watchlist config hash", e);
            return null;
        }
    }

    public void setTestMode(java.io.InputStream inputStream) throws java.io.IOException {
        android.util.Log.i(TAG, "Setting watchlist testing config");
        android.os.FileUtils.copyToFileOrThrow(inputStream, new java.io.File(NETWORK_WATCHLIST_DB_FOR_TEST_PATH));
        this.mIsSecureConfig = false;
        this.mXmlFile = new java.io.File(NETWORK_WATCHLIST_DB_FOR_TEST_PATH);
        reloadConfig();
    }

    public void removeTestModeConfig() {
        try {
            java.io.File file = new java.io.File(NETWORK_WATCHLIST_DB_FOR_TEST_PATH);
            if (file.exists()) {
                file.delete();
            }
        } catch (java.lang.Exception e) {
            android.util.Log.e(TAG, "Unable to delete test config");
        }
    }

    public void dump(java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, java.lang.String[] strArr) {
        byte[] watchlistConfigHash = getWatchlistConfigHash();
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append("Watchlist config hash: ");
        sb.append(watchlistConfigHash != null ? com.android.internal.util.HexDump.toHexString(watchlistConfigHash) : null);
        printWriter.println(sb.toString());
        printWriter.println("Domain CRC32 digest list:");
        if (this.mDomainDigests != null) {
            this.mDomainDigests.crc32s.dump(fileDescriptor, printWriter, strArr);
        }
        printWriter.println("Domain SHA256 digest list:");
        if (this.mDomainDigests != null) {
            this.mDomainDigests.sha256Digests.dump(fileDescriptor, printWriter, strArr);
        }
        printWriter.println("Ip CRC32 digest list:");
        if (this.mIpDigests != null) {
            this.mIpDigests.crc32s.dump(fileDescriptor, printWriter, strArr);
        }
        printWriter.println("Ip SHA256 digest list:");
        if (this.mIpDigests != null) {
            this.mIpDigests.sha256Digests.dump(fileDescriptor, printWriter, strArr);
        }
    }
}
