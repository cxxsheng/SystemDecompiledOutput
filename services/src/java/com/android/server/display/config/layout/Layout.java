package com.android.server.display.config.layout;

/* loaded from: classes.dex */
public class Layout {
    private java.util.List<com.android.server.display.config.layout.Display> display;
    private java.math.BigInteger state;

    public java.math.BigInteger getState() {
        return this.state;
    }

    boolean hasState() {
        if (this.state == null) {
            return false;
        }
        return true;
    }

    public void setState(java.math.BigInteger bigInteger) {
        this.state = bigInteger;
    }

    public java.util.List<com.android.server.display.config.layout.Display> getDisplay() {
        if (this.display == null) {
            this.display = new java.util.ArrayList();
        }
        return this.display;
    }

    static com.android.server.display.config.layout.Layout read(org.xmlpull.v1.XmlPullParser xmlPullParser) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException, javax.xml.datatype.DatatypeConfigurationException {
        int next;
        com.android.server.display.config.layout.Layout layout = new com.android.server.display.config.layout.Layout();
        xmlPullParser.getDepth();
        while (true) {
            next = xmlPullParser.next();
            if (next == 1 || next == 3) {
                break;
            }
            if (xmlPullParser.getEventType() == 2) {
                java.lang.String name = xmlPullParser.getName();
                if (name.equals("state")) {
                    layout.setState(new java.math.BigInteger(com.android.server.display.config.layout.XmlParser.readText(xmlPullParser)));
                } else if (name.equals("display")) {
                    layout.getDisplay().add(com.android.server.display.config.layout.Display.read(xmlPullParser));
                } else {
                    com.android.server.display.config.layout.XmlParser.skip(xmlPullParser);
                }
            }
        }
        if (next != 3) {
            throw new javax.xml.datatype.DatatypeConfigurationException("Layout is not closed");
        }
        return layout;
    }
}
