package com.android.server.firewall;

/* loaded from: classes.dex */
class PortFilter implements com.android.server.firewall.Filter {
    private static final java.lang.String ATTR_EQUALS = "equals";
    private static final java.lang.String ATTR_MAX = "max";
    private static final java.lang.String ATTR_MIN = "min";
    public static final com.android.server.firewall.FilterFactory FACTORY = new com.android.server.firewall.FilterFactory("port") { // from class: com.android.server.firewall.PortFilter.1
        @Override // com.android.server.firewall.FilterFactory
        public com.android.server.firewall.Filter newFilter(org.xmlpull.v1.XmlPullParser xmlPullParser) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
            int parseInt;
            int i;
            java.lang.String attributeValue = xmlPullParser.getAttributeValue(null, com.android.server.firewall.PortFilter.ATTR_EQUALS);
            if (attributeValue == null) {
                parseInt = -1;
                i = -1;
            } else {
                try {
                    parseInt = java.lang.Integer.parseInt(attributeValue);
                    i = parseInt;
                } catch (java.lang.NumberFormatException e) {
                    throw new org.xmlpull.v1.XmlPullParserException("Invalid port value: " + attributeValue, xmlPullParser, null);
                }
            }
            java.lang.String attributeValue2 = xmlPullParser.getAttributeValue(null, com.android.server.firewall.PortFilter.ATTR_MIN);
            java.lang.String attributeValue3 = xmlPullParser.getAttributeValue(null, com.android.server.firewall.PortFilter.ATTR_MAX);
            if (attributeValue2 != null || attributeValue3 != null) {
                if (attributeValue != null) {
                    throw new org.xmlpull.v1.XmlPullParserException("Port filter cannot use both equals and range filtering", xmlPullParser, null);
                }
                if (attributeValue2 != null) {
                    try {
                        parseInt = java.lang.Integer.parseInt(attributeValue2);
                    } catch (java.lang.NumberFormatException e2) {
                        throw new org.xmlpull.v1.XmlPullParserException("Invalid minimum port value: " + attributeValue2, xmlPullParser, null);
                    }
                }
                if (attributeValue3 != null) {
                    try {
                        i = java.lang.Integer.parseInt(attributeValue3);
                    } catch (java.lang.NumberFormatException e3) {
                        throw new org.xmlpull.v1.XmlPullParserException("Invalid maximum port value: " + attributeValue3, xmlPullParser, null);
                    }
                }
            }
            return new com.android.server.firewall.PortFilter(parseInt, i);
        }
    };
    private static final int NO_BOUND = -1;
    private final int mLowerBound;
    private final int mUpperBound;

    private PortFilter(int i, int i2) {
        this.mLowerBound = i;
        this.mUpperBound = i2;
    }

    @Override // com.android.server.firewall.Filter
    public boolean matches(com.android.server.firewall.IntentFirewall intentFirewall, android.content.ComponentName componentName, android.content.Intent intent, int i, int i2, java.lang.String str, int i3) {
        int i4;
        android.net.Uri data = intent.getData();
        if (data == null) {
            i4 = -1;
        } else {
            i4 = data.getPort();
        }
        return i4 != -1 && (this.mLowerBound == -1 || this.mLowerBound <= i4) && (this.mUpperBound == -1 || this.mUpperBound >= i4);
    }
}
