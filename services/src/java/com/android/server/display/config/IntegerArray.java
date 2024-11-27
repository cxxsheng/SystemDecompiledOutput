package com.android.server.display.config;

/* loaded from: classes.dex */
public class IntegerArray {
    private java.util.List<java.math.BigInteger> item;

    public java.util.List<java.math.BigInteger> getItem() {
        if (this.item == null) {
            this.item = new java.util.ArrayList();
        }
        return this.item;
    }

    static com.android.server.display.config.IntegerArray read(org.xmlpull.v1.XmlPullParser xmlPullParser) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException, javax.xml.datatype.DatatypeConfigurationException {
        int next;
        com.android.server.display.config.IntegerArray integerArray = new com.android.server.display.config.IntegerArray();
        xmlPullParser.getDepth();
        while (true) {
            next = xmlPullParser.next();
            if (next == 1 || next == 3) {
                break;
            }
            if (xmlPullParser.getEventType() == 2) {
                if (xmlPullParser.getName().equals(com.android.server.pm.Settings.TAG_ITEM)) {
                    integerArray.getItem().add(new java.math.BigInteger(com.android.server.display.config.XmlParser.readText(xmlPullParser)));
                } else {
                    com.android.server.display.config.XmlParser.skip(xmlPullParser);
                }
            }
        }
        if (next != 3) {
            throw new javax.xml.datatype.DatatypeConfigurationException("IntegerArray is not closed");
        }
        return integerArray;
    }
}
