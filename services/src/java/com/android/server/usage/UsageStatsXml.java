package com.android.server.usage;

/* loaded from: classes2.dex */
public class UsageStatsXml {
    static final java.lang.String CHECKED_IN_SUFFIX = "-c";
    private static final java.lang.String TAG = "UsageStatsXml";
    private static final java.lang.String USAGESTATS_TAG = "usagestats";
    private static final java.lang.String VERSION_ATTR = "version";

    public static void read(java.io.InputStream inputStream, com.android.server.usage.IntervalStats intervalStats) throws java.io.IOException {
        org.xmlpull.v1.XmlPullParser newPullParser = android.util.Xml.newPullParser();
        try {
            newPullParser.setInput(inputStream, "utf-8");
            com.android.internal.util.XmlUtils.beginDocument(newPullParser, USAGESTATS_TAG);
            java.lang.String attributeValue = newPullParser.getAttributeValue(null, VERSION_ATTR);
            try {
                switch (java.lang.Integer.parseInt(attributeValue)) {
                    case 1:
                        com.android.server.usage.UsageStatsXmlV1.read(newPullParser, intervalStats);
                        return;
                    default:
                        android.util.Slog.e(TAG, "Unrecognized version " + attributeValue);
                        throw new java.io.IOException("Unrecognized version " + attributeValue);
                }
            } catch (java.lang.NumberFormatException e) {
                android.util.Slog.e(TAG, "Bad version");
                throw new java.io.IOException(e);
            }
        } catch (org.xmlpull.v1.XmlPullParserException e2) {
            android.util.Slog.e(TAG, "Failed to parse Xml", e2);
            throw new java.io.IOException(e2);
        }
    }
}
