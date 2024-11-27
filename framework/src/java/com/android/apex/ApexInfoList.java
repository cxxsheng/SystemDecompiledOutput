package com.android.apex;

/* loaded from: classes4.dex */
public class ApexInfoList {
    private java.util.List<com.android.apex.ApexInfo> apexInfo;

    public java.util.List<com.android.apex.ApexInfo> getApexInfo() {
        if (this.apexInfo == null) {
            this.apexInfo = new java.util.ArrayList();
        }
        return this.apexInfo;
    }

    static com.android.apex.ApexInfoList read(org.xmlpull.v1.XmlPullParser xmlPullParser) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException, javax.xml.datatype.DatatypeConfigurationException {
        int next;
        com.android.apex.ApexInfoList apexInfoList = new com.android.apex.ApexInfoList();
        xmlPullParser.getDepth();
        while (true) {
            next = xmlPullParser.next();
            if (next == 1 || next == 3) {
                break;
            }
            if (xmlPullParser.getEventType() == 2) {
                if (xmlPullParser.getName().equals("apex-info")) {
                    apexInfoList.getApexInfo().add(com.android.apex.ApexInfo.read(xmlPullParser));
                } else {
                    com.android.apex.XmlParser.skip(xmlPullParser);
                }
            }
        }
        if (next != 3) {
            throw new javax.xml.datatype.DatatypeConfigurationException("ApexInfoList is not closed");
        }
        return apexInfoList;
    }

    void write(com.android.apex.XmlWriter xmlWriter, java.lang.String str) throws java.io.IOException {
        xmlWriter.print("<" + str);
        xmlWriter.print(">\n");
        xmlWriter.increaseIndent();
        java.util.Iterator<com.android.apex.ApexInfo> it = getApexInfo().iterator();
        while (it.hasNext()) {
            it.next().write(xmlWriter, "apex-info");
        }
        xmlWriter.decreaseIndent();
        xmlWriter.print("</" + str + ">\n");
    }
}
