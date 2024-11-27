package com.android.internal.telephony.util;

/* loaded from: classes5.dex */
public final class XmlUtils {
    private XmlUtils() {
    }

    public static void beginDocument(org.xmlpull.v1.XmlPullParser xmlPullParser, java.lang.String str) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        int next;
        do {
            next = xmlPullParser.next();
            if (next == 2) {
                break;
            }
        } while (next != 1);
        if (next != 2) {
            throw new org.xmlpull.v1.XmlPullParserException("No start tag found");
        }
        if (!xmlPullParser.getName().equals(str)) {
            throw new org.xmlpull.v1.XmlPullParserException("Unexpected start tag: found " + xmlPullParser.getName() + ", expected " + str);
        }
    }

    public static void nextElement(org.xmlpull.v1.XmlPullParser xmlPullParser) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        int next;
        do {
            next = xmlPullParser.next();
            if (next == 2) {
                return;
            }
        } while (next != 1);
    }

    public static boolean nextElementWithin(org.xmlpull.v1.XmlPullParser xmlPullParser, int i) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
        while (true) {
            int next = xmlPullParser.next();
            if (next != 1) {
                if (next == 3 && xmlPullParser.getDepth() == i) {
                    return false;
                }
                if (next == 2 && xmlPullParser.getDepth() == i + 1) {
                    return true;
                }
            } else {
                return false;
            }
        }
    }
}
