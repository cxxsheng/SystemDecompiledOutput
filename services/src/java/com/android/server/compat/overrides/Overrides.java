package com.android.server.compat.overrides;

/* loaded from: classes.dex */
public class Overrides {
    private java.util.List<com.android.server.compat.overrides.ChangeOverrides> changeOverrides;

    public java.util.List<com.android.server.compat.overrides.ChangeOverrides> getChangeOverrides() {
        if (this.changeOverrides == null) {
            this.changeOverrides = new java.util.ArrayList();
        }
        return this.changeOverrides;
    }

    static com.android.server.compat.overrides.Overrides read(org.xmlpull.v1.XmlPullParser xmlPullParser) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException, javax.xml.datatype.DatatypeConfigurationException {
        int next;
        com.android.server.compat.overrides.Overrides overrides = new com.android.server.compat.overrides.Overrides();
        xmlPullParser.getDepth();
        while (true) {
            next = xmlPullParser.next();
            if (next == 1 || next == 3) {
                break;
            }
            if (xmlPullParser.getEventType() == 2) {
                if (xmlPullParser.getName().equals("change-overrides")) {
                    overrides.getChangeOverrides().add(com.android.server.compat.overrides.ChangeOverrides.read(xmlPullParser));
                } else {
                    com.android.server.compat.overrides.XmlParser.skip(xmlPullParser);
                }
            }
        }
        if (next != 3) {
            throw new javax.xml.datatype.DatatypeConfigurationException("Overrides is not closed");
        }
        return overrides;
    }

    void write(com.android.server.compat.overrides.XmlWriter xmlWriter, java.lang.String str) throws java.io.IOException {
        xmlWriter.print("<" + str);
        xmlWriter.print(">\n");
        xmlWriter.increaseIndent();
        java.util.Iterator<com.android.server.compat.overrides.ChangeOverrides> it = getChangeOverrides().iterator();
        while (it.hasNext()) {
            it.next().write(xmlWriter, "change-overrides");
        }
        xmlWriter.decreaseIndent();
        xmlWriter.print("</" + str + ">\n");
    }
}
