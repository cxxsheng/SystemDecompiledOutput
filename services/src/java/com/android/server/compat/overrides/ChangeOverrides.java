package com.android.server.compat.overrides;

/* loaded from: classes.dex */
public class ChangeOverrides {
    private java.lang.Long changeId;
    private com.android.server.compat.overrides.ChangeOverrides.Deferred deferred;
    private com.android.server.compat.overrides.ChangeOverrides.Raw raw;
    private com.android.server.compat.overrides.ChangeOverrides.Validated validated;

    public static class Validated {
        private java.util.List<com.android.server.compat.overrides.OverrideValue> overrideValue;

        public java.util.List<com.android.server.compat.overrides.OverrideValue> getOverrideValue() {
            if (this.overrideValue == null) {
                this.overrideValue = new java.util.ArrayList();
            }
            return this.overrideValue;
        }

        static com.android.server.compat.overrides.ChangeOverrides.Validated read(org.xmlpull.v1.XmlPullParser xmlPullParser) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException, javax.xml.datatype.DatatypeConfigurationException {
            int next;
            com.android.server.compat.overrides.ChangeOverrides.Validated validated = new com.android.server.compat.overrides.ChangeOverrides.Validated();
            xmlPullParser.getDepth();
            while (true) {
                next = xmlPullParser.next();
                if (next == 1 || next == 3) {
                    break;
                }
                if (xmlPullParser.getEventType() == 2) {
                    if (xmlPullParser.getName().equals("override-value")) {
                        validated.getOverrideValue().add(com.android.server.compat.overrides.OverrideValue.read(xmlPullParser));
                    } else {
                        com.android.server.compat.overrides.XmlParser.skip(xmlPullParser);
                    }
                }
            }
            if (next != 3) {
                throw new javax.xml.datatype.DatatypeConfigurationException("ChangeOverrides.Validated is not closed");
            }
            return validated;
        }

        void write(com.android.server.compat.overrides.XmlWriter xmlWriter, java.lang.String str) throws java.io.IOException {
            xmlWriter.print("<" + str);
            xmlWriter.print(">\n");
            xmlWriter.increaseIndent();
            java.util.Iterator<com.android.server.compat.overrides.OverrideValue> it = getOverrideValue().iterator();
            while (it.hasNext()) {
                it.next().write(xmlWriter, "override-value");
            }
            xmlWriter.decreaseIndent();
            xmlWriter.print("</" + str + ">\n");
        }
    }

    public static class Deferred {
        private java.util.List<com.android.server.compat.overrides.OverrideValue> overrideValue;

        public java.util.List<com.android.server.compat.overrides.OverrideValue> getOverrideValue() {
            if (this.overrideValue == null) {
                this.overrideValue = new java.util.ArrayList();
            }
            return this.overrideValue;
        }

        static com.android.server.compat.overrides.ChangeOverrides.Deferred read(org.xmlpull.v1.XmlPullParser xmlPullParser) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException, javax.xml.datatype.DatatypeConfigurationException {
            int next;
            com.android.server.compat.overrides.ChangeOverrides.Deferred deferred = new com.android.server.compat.overrides.ChangeOverrides.Deferred();
            xmlPullParser.getDepth();
            while (true) {
                next = xmlPullParser.next();
                if (next == 1 || next == 3) {
                    break;
                }
                if (xmlPullParser.getEventType() == 2) {
                    if (xmlPullParser.getName().equals("override-value")) {
                        deferred.getOverrideValue().add(com.android.server.compat.overrides.OverrideValue.read(xmlPullParser));
                    } else {
                        com.android.server.compat.overrides.XmlParser.skip(xmlPullParser);
                    }
                }
            }
            if (next != 3) {
                throw new javax.xml.datatype.DatatypeConfigurationException("ChangeOverrides.Deferred is not closed");
            }
            return deferred;
        }

        void write(com.android.server.compat.overrides.XmlWriter xmlWriter, java.lang.String str) throws java.io.IOException {
            xmlWriter.print("<" + str);
            xmlWriter.print(">\n");
            xmlWriter.increaseIndent();
            java.util.Iterator<com.android.server.compat.overrides.OverrideValue> it = getOverrideValue().iterator();
            while (it.hasNext()) {
                it.next().write(xmlWriter, "override-value");
            }
            xmlWriter.decreaseIndent();
            xmlWriter.print("</" + str + ">\n");
        }
    }

    public static class Raw {
        private java.util.List<com.android.server.compat.overrides.RawOverrideValue> rawOverrideValue;

        public java.util.List<com.android.server.compat.overrides.RawOverrideValue> getRawOverrideValue() {
            if (this.rawOverrideValue == null) {
                this.rawOverrideValue = new java.util.ArrayList();
            }
            return this.rawOverrideValue;
        }

        static com.android.server.compat.overrides.ChangeOverrides.Raw read(org.xmlpull.v1.XmlPullParser xmlPullParser) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException, javax.xml.datatype.DatatypeConfigurationException {
            int next;
            com.android.server.compat.overrides.ChangeOverrides.Raw raw = new com.android.server.compat.overrides.ChangeOverrides.Raw();
            xmlPullParser.getDepth();
            while (true) {
                next = xmlPullParser.next();
                if (next == 1 || next == 3) {
                    break;
                }
                if (xmlPullParser.getEventType() == 2) {
                    if (xmlPullParser.getName().equals("raw-override-value")) {
                        raw.getRawOverrideValue().add(com.android.server.compat.overrides.RawOverrideValue.read(xmlPullParser));
                    } else {
                        com.android.server.compat.overrides.XmlParser.skip(xmlPullParser);
                    }
                }
            }
            if (next != 3) {
                throw new javax.xml.datatype.DatatypeConfigurationException("ChangeOverrides.Raw is not closed");
            }
            return raw;
        }

        void write(com.android.server.compat.overrides.XmlWriter xmlWriter, java.lang.String str) throws java.io.IOException {
            xmlWriter.print("<" + str);
            xmlWriter.print(">\n");
            xmlWriter.increaseIndent();
            java.util.Iterator<com.android.server.compat.overrides.RawOverrideValue> it = getRawOverrideValue().iterator();
            while (it.hasNext()) {
                it.next().write(xmlWriter, "raw-override-value");
            }
            xmlWriter.decreaseIndent();
            xmlWriter.print("</" + str + ">\n");
        }
    }

    public com.android.server.compat.overrides.ChangeOverrides.Validated getValidated() {
        return this.validated;
    }

    boolean hasValidated() {
        if (this.validated == null) {
            return false;
        }
        return true;
    }

    public void setValidated(com.android.server.compat.overrides.ChangeOverrides.Validated validated) {
        this.validated = validated;
    }

    public com.android.server.compat.overrides.ChangeOverrides.Deferred getDeferred() {
        return this.deferred;
    }

    boolean hasDeferred() {
        if (this.deferred == null) {
            return false;
        }
        return true;
    }

    public void setDeferred(com.android.server.compat.overrides.ChangeOverrides.Deferred deferred) {
        this.deferred = deferred;
    }

    public com.android.server.compat.overrides.ChangeOverrides.Raw getRaw() {
        return this.raw;
    }

    boolean hasRaw() {
        if (this.raw == null) {
            return false;
        }
        return true;
    }

    public void setRaw(com.android.server.compat.overrides.ChangeOverrides.Raw raw) {
        this.raw = raw;
    }

    public long getChangeId() {
        if (this.changeId == null) {
            return 0L;
        }
        return this.changeId.longValue();
    }

    boolean hasChangeId() {
        if (this.changeId == null) {
            return false;
        }
        return true;
    }

    public void setChangeId(long j) {
        this.changeId = java.lang.Long.valueOf(j);
    }

    static com.android.server.compat.overrides.ChangeOverrides read(org.xmlpull.v1.XmlPullParser xmlPullParser) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException, javax.xml.datatype.DatatypeConfigurationException {
        int next;
        com.android.server.compat.overrides.ChangeOverrides changeOverrides = new com.android.server.compat.overrides.ChangeOverrides();
        java.lang.String attributeValue = xmlPullParser.getAttributeValue(null, "changeId");
        if (attributeValue != null) {
            changeOverrides.setChangeId(java.lang.Long.parseLong(attributeValue));
        }
        xmlPullParser.getDepth();
        while (true) {
            next = xmlPullParser.next();
            if (next == 1 || next == 3) {
                break;
            }
            if (xmlPullParser.getEventType() == 2) {
                java.lang.String name = xmlPullParser.getName();
                if (name.equals("validated")) {
                    changeOverrides.setValidated(com.android.server.compat.overrides.ChangeOverrides.Validated.read(xmlPullParser));
                } else if (name.equals("deferred")) {
                    changeOverrides.setDeferred(com.android.server.compat.overrides.ChangeOverrides.Deferred.read(xmlPullParser));
                } else if (name.equals("raw")) {
                    changeOverrides.setRaw(com.android.server.compat.overrides.ChangeOverrides.Raw.read(xmlPullParser));
                } else {
                    com.android.server.compat.overrides.XmlParser.skip(xmlPullParser);
                }
            }
        }
        if (next != 3) {
            throw new javax.xml.datatype.DatatypeConfigurationException("ChangeOverrides is not closed");
        }
        return changeOverrides;
    }

    void write(com.android.server.compat.overrides.XmlWriter xmlWriter, java.lang.String str) throws java.io.IOException {
        xmlWriter.print("<" + str);
        if (hasChangeId()) {
            xmlWriter.print(" changeId=\"");
            xmlWriter.print(java.lang.Long.toString(getChangeId()));
            xmlWriter.print("\"");
        }
        xmlWriter.print(">\n");
        xmlWriter.increaseIndent();
        if (hasValidated()) {
            getValidated().write(xmlWriter, "validated");
        }
        if (hasDeferred()) {
            getDeferred().write(xmlWriter, "deferred");
        }
        if (hasRaw()) {
            getRaw().write(xmlWriter, "raw");
        }
        xmlWriter.decreaseIndent();
        xmlWriter.print("</" + str + ">\n");
    }
}
