package com.android.server.firewall;

/* loaded from: classes.dex */
abstract class FilterList implements com.android.server.firewall.Filter {
    protected final java.util.ArrayList<com.android.server.firewall.Filter> children = new java.util.ArrayList<>();

    FilterList() {
    }

    public com.android.server.firewall.FilterList readFromXml(org.xmlpull.v1.XmlPullParser xmlPullParser) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
        int depth = xmlPullParser.getDepth();
        while (com.android.internal.util.XmlUtils.nextElementWithin(xmlPullParser, depth)) {
            readChild(xmlPullParser);
        }
        return this;
    }

    protected void readChild(org.xmlpull.v1.XmlPullParser xmlPullParser) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
        this.children.add(com.android.server.firewall.IntentFirewall.parseFilter(xmlPullParser));
    }
}
