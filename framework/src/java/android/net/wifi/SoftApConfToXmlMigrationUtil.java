package android.net.wifi;

/* loaded from: classes2.dex */
public final class SoftApConfToXmlMigrationUtil {
    private static final int CONFIG_STORE_DATA_VERSION = 3;
    private static final java.lang.String LEGACY_AP_CONFIG_FILE = "softap.conf";
    private static final java.lang.String LEGACY_WIFI_STORE_DIRECTORY_NAME = "wifi";
    private static final java.lang.String TAG = "SoftApConfToXmlMigrationUtil";
    private static final int WIFICONFIG_AP_BAND_2GHZ = 0;
    private static final int WIFICONFIG_AP_BAND_5GHZ = 1;
    private static final int WIFICONFIG_AP_BAND_ANY = -1;
    private static final java.lang.String XML_TAG_ALLOWED_CLIENT_LIST = "AllowedClientList";
    private static final java.lang.String XML_TAG_AP_BAND = "ApBand";
    private static final java.lang.String XML_TAG_AUTO_SHUTDOWN_ENABLED = "AutoShutdownEnabled";
    private static final java.lang.String XML_TAG_BLOCKED_CLIENT_LIST = "BlockedClientList";
    private static final java.lang.String XML_TAG_BSSID = "Bssid";
    private static final java.lang.String XML_TAG_CHANNEL = "Channel";
    private static final java.lang.String XML_TAG_CLIENT_CONTROL_BY_USER = "ClientControlByUser";
    public static final java.lang.String XML_TAG_CLIENT_MACADDRESS = "ClientMacAddress";
    private static final java.lang.String XML_TAG_DOCUMENT_HEADER = "WifiConfigStoreData";
    private static final java.lang.String XML_TAG_HIDDEN_SSID = "HiddenSSID";
    private static final java.lang.String XML_TAG_MAX_NUMBER_OF_CLIENTS = "MaxNumberOfClients";
    private static final java.lang.String XML_TAG_PASSPHRASE = "Passphrase";
    private static final java.lang.String XML_TAG_SECTION_HEADER_SOFTAP = "SoftAp";
    private static final java.lang.String XML_TAG_SECURITY_TYPE = "SecurityType";
    private static final java.lang.String XML_TAG_SHUTDOWN_TIMEOUT_MILLIS = "ShutdownTimeoutMillis";
    private static final java.lang.String XML_TAG_SSID = "SSID";
    private static final java.lang.String XML_TAG_VERSION = "Version";

    private static java.io.File getLegacyWifiSharedDirectory() {
        return new java.io.File(android.os.Environment.getDataMiscDirectory(), "wifi");
    }

    public static int convertWifiConfigBandToSoftApConfigBand(int i) {
        switch (i) {
        }
        return 1;
    }

    /* JADX WARN: Not initialized variable reg: 4, insn: 0x009e: MOVE (r2 I:??[OBJECT, ARRAY]) = (r4 I:??[OBJECT, ARRAY]), block:B:59:0x009e */
    private static android.net.wifi.SoftApConfiguration loadFromLegacyFile(java.io.InputStream inputStream) {
        java.io.DataInputStream dataInputStream;
        java.io.DataInputStream dataInputStream2;
        android.net.wifi.SoftApConfiguration.Builder builder;
        int readInt;
        android.net.wifi.SoftApConfiguration softApConfiguration = null;
        softApConfiguration = null;
        softApConfiguration = null;
        softApConfiguration = null;
        softApConfiguration = null;
        java.io.DataInputStream dataInputStream3 = null;
        try {
            try {
                try {
                    builder = new android.net.wifi.SoftApConfiguration.Builder();
                    dataInputStream2 = new java.io.DataInputStream(new java.io.BufferedInputStream(inputStream));
                } catch (java.io.IOException e) {
                    e = e;
                    dataInputStream2 = null;
                } catch (java.lang.IllegalArgumentException e2) {
                    e = e2;
                    dataInputStream2 = null;
                } catch (java.lang.Throwable th) {
                    th = th;
                    if (dataInputStream3 != null) {
                        try {
                            dataInputStream3.close();
                        } catch (java.io.IOException e3) {
                            android.util.Log.e(TAG, "Error closing hotspot configuration during read", e3);
                        }
                    }
                    throw th;
                }
                try {
                    readInt = dataInputStream2.readInt();
                } catch (java.io.IOException e4) {
                    e = e4;
                    android.util.Log.e(TAG, "Error reading hotspot configuration ", e);
                    if (dataInputStream2 != null) {
                        dataInputStream2.close();
                    }
                    return softApConfiguration;
                } catch (java.lang.IllegalArgumentException e5) {
                    e = e5;
                    android.util.Log.e(TAG, "Invalid hotspot configuration ", e);
                    if (dataInputStream2 != null) {
                        dataInputStream2.close();
                    }
                    return softApConfiguration;
                }
            } catch (java.io.IOException e6) {
                android.util.Log.e(TAG, "Error closing hotspot configuration during read", e6);
            }
            if (readInt >= 1 && readInt <= 3) {
                builder.setSsid(dataInputStream2.readUTF());
                if (readInt >= 2) {
                    int readInt2 = dataInputStream2.readInt();
                    int readInt3 = dataInputStream2.readInt();
                    if (readInt3 == 0) {
                        builder.setBand(convertWifiConfigBandToSoftApConfigBand(readInt2));
                    } else {
                        builder.setChannel(readInt3, convertWifiConfigBandToSoftApConfigBand(readInt2));
                    }
                }
                if (readInt >= 3) {
                    builder.setHiddenSsid(dataInputStream2.readBoolean());
                }
                if (dataInputStream2.readInt() == 4) {
                    builder.setPassphrase(dataInputStream2.readUTF(), 1);
                }
                softApConfiguration = builder.build();
                dataInputStream2.close();
                return softApConfiguration;
            }
            android.util.Log.e(TAG, "Bad version on hotspot configuration file");
            try {
                dataInputStream2.close();
            } catch (java.io.IOException e7) {
                android.util.Log.e(TAG, "Error closing hotspot configuration during read", e7);
            }
            return null;
        } catch (java.lang.Throwable th2) {
            th = th2;
            dataInputStream3 = dataInputStream;
        }
    }

    private static byte[] convertConfToXml(android.net.wifi.SoftApConfiguration softApConfiguration) {
        try {
            com.android.internal.util.FastXmlSerializer fastXmlSerializer = new com.android.internal.util.FastXmlSerializer();
            java.io.ByteArrayOutputStream byteArrayOutputStream = new java.io.ByteArrayOutputStream();
            fastXmlSerializer.setOutput(byteArrayOutputStream, java.nio.charset.StandardCharsets.UTF_8.name());
            fastXmlSerializer.startDocument(null, true);
            fastXmlSerializer.startTag(null, XML_TAG_DOCUMENT_HEADER);
            com.android.internal.util.XmlUtils.writeValueXml((java.lang.Object) 3, XML_TAG_VERSION, (org.xmlpull.v1.XmlSerializer) fastXmlSerializer);
            fastXmlSerializer.startTag(null, XML_TAG_SECTION_HEADER_SOFTAP);
            com.android.internal.util.XmlUtils.writeValueXml(softApConfiguration.getSsid(), XML_TAG_SSID, fastXmlSerializer);
            if (softApConfiguration.getBssid() != null) {
                com.android.internal.util.XmlUtils.writeValueXml(softApConfiguration.getBssid().toString(), XML_TAG_BSSID, fastXmlSerializer);
            }
            com.android.internal.util.XmlUtils.writeValueXml(java.lang.Integer.valueOf(softApConfiguration.getBand()), XML_TAG_AP_BAND, fastXmlSerializer);
            com.android.internal.util.XmlUtils.writeValueXml(java.lang.Integer.valueOf(softApConfiguration.getChannel()), XML_TAG_CHANNEL, fastXmlSerializer);
            com.android.internal.util.XmlUtils.writeValueXml(java.lang.Boolean.valueOf(softApConfiguration.isHiddenSsid()), XML_TAG_HIDDEN_SSID, fastXmlSerializer);
            com.android.internal.util.XmlUtils.writeValueXml(java.lang.Integer.valueOf(softApConfiguration.getSecurityType()), XML_TAG_SECURITY_TYPE, fastXmlSerializer);
            if (softApConfiguration.getSecurityType() != 0) {
                com.android.internal.util.XmlUtils.writeValueXml(softApConfiguration.getPassphrase(), XML_TAG_PASSPHRASE, fastXmlSerializer);
            }
            com.android.internal.util.XmlUtils.writeValueXml(java.lang.Integer.valueOf(softApConfiguration.getMaxNumberOfClients()), XML_TAG_MAX_NUMBER_OF_CLIENTS, fastXmlSerializer);
            com.android.internal.util.XmlUtils.writeValueXml(java.lang.Boolean.valueOf(softApConfiguration.isClientControlByUserEnabled()), XML_TAG_CLIENT_CONTROL_BY_USER, fastXmlSerializer);
            com.android.internal.util.XmlUtils.writeValueXml(java.lang.Boolean.valueOf(softApConfiguration.isAutoShutdownEnabled()), XML_TAG_AUTO_SHUTDOWN_ENABLED, fastXmlSerializer);
            com.android.internal.util.XmlUtils.writeValueXml(java.lang.Long.valueOf(softApConfiguration.getShutdownTimeoutMillis()), XML_TAG_SHUTDOWN_TIMEOUT_MILLIS, fastXmlSerializer);
            fastXmlSerializer.startTag(null, XML_TAG_BLOCKED_CLIENT_LIST);
            java.util.Iterator it = softApConfiguration.getBlockedClientList().iterator();
            while (it.hasNext()) {
                com.android.internal.util.XmlUtils.writeValueXml(((android.net.MacAddress) it.next()).toString(), XML_TAG_CLIENT_MACADDRESS, fastXmlSerializer);
            }
            fastXmlSerializer.endTag(null, XML_TAG_BLOCKED_CLIENT_LIST);
            fastXmlSerializer.startTag(null, XML_TAG_ALLOWED_CLIENT_LIST);
            java.util.Iterator it2 = softApConfiguration.getAllowedClientList().iterator();
            while (it2.hasNext()) {
                com.android.internal.util.XmlUtils.writeValueXml(((android.net.MacAddress) it2.next()).toString(), XML_TAG_CLIENT_MACADDRESS, fastXmlSerializer);
            }
            fastXmlSerializer.endTag(null, XML_TAG_ALLOWED_CLIENT_LIST);
            fastXmlSerializer.endTag(null, XML_TAG_SECTION_HEADER_SOFTAP);
            fastXmlSerializer.endTag(null, XML_TAG_DOCUMENT_HEADER);
            fastXmlSerializer.endDocument();
            return byteArrayOutputStream.toByteArray();
        } catch (java.io.IOException | org.xmlpull.v1.XmlPullParserException e) {
            android.util.Log.e(TAG, "Failed to convert softap conf to XML", e);
            return null;
        }
    }

    private SoftApConfToXmlMigrationUtil() {
    }

    public static java.io.InputStream convert(java.io.InputStream inputStream) {
        byte[] convertConfToXml;
        android.net.wifi.SoftApConfiguration loadFromLegacyFile = loadFromLegacyFile(inputStream);
        if (loadFromLegacyFile == null || (convertConfToXml = convertConfToXml(loadFromLegacyFile)) == null) {
            return null;
        }
        return new java.io.ByteArrayInputStream(convertConfToXml);
    }

    public static java.io.InputStream convert() {
        try {
            return convert(new java.io.FileInputStream(new java.io.File(getLegacyWifiSharedDirectory(), LEGACY_AP_CONFIG_FILE)));
        } catch (java.io.FileNotFoundException e) {
            return null;
        }
    }

    public static void remove() {
        new java.io.File(getLegacyWifiSharedDirectory(), LEGACY_AP_CONFIG_FILE).delete();
    }
}
