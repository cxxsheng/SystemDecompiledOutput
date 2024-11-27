package android.audio.policy.configuration.V7_0;

/* loaded from: classes.dex */
public class Routes {
    private java.util.List<android.audio.policy.configuration.V7_0.Routes.Route> route;

    public static class Route {
        private java.lang.String sink;
        private java.lang.String sources;
        private android.audio.policy.configuration.V7_0.MixType type;

        public android.audio.policy.configuration.V7_0.MixType getType() {
            return this.type;
        }

        boolean hasType() {
            if (this.type == null) {
                return false;
            }
            return true;
        }

        public void setType(android.audio.policy.configuration.V7_0.MixType mixType) {
            this.type = mixType;
        }

        public java.lang.String getSink() {
            return this.sink;
        }

        boolean hasSink() {
            if (this.sink == null) {
                return false;
            }
            return true;
        }

        public void setSink(java.lang.String str) {
            this.sink = str;
        }

        public java.lang.String getSources() {
            return this.sources;
        }

        boolean hasSources() {
            if (this.sources == null) {
                return false;
            }
            return true;
        }

        public void setSources(java.lang.String str) {
            this.sources = str;
        }

        static android.audio.policy.configuration.V7_0.Routes.Route read(org.xmlpull.v1.XmlPullParser xmlPullParser) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException, javax.xml.datatype.DatatypeConfigurationException {
            android.audio.policy.configuration.V7_0.Routes.Route route = new android.audio.policy.configuration.V7_0.Routes.Route();
            java.lang.String attributeValue = xmlPullParser.getAttributeValue(null, "type");
            if (attributeValue != null) {
                route.setType(android.audio.policy.configuration.V7_0.MixType.fromString(attributeValue));
            }
            java.lang.String attributeValue2 = xmlPullParser.getAttributeValue(null, "sink");
            if (attributeValue2 != null) {
                route.setSink(attributeValue2);
            }
            java.lang.String attributeValue3 = xmlPullParser.getAttributeValue(null, "sources");
            if (attributeValue3 != null) {
                route.setSources(attributeValue3);
            }
            android.audio.policy.configuration.V7_0.XmlParser.skip(xmlPullParser);
            return route;
        }
    }

    public java.util.List<android.audio.policy.configuration.V7_0.Routes.Route> getRoute() {
        if (this.route == null) {
            this.route = new java.util.ArrayList();
        }
        return this.route;
    }

    static android.audio.policy.configuration.V7_0.Routes read(org.xmlpull.v1.XmlPullParser xmlPullParser) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException, javax.xml.datatype.DatatypeConfigurationException {
        int next;
        android.audio.policy.configuration.V7_0.Routes routes = new android.audio.policy.configuration.V7_0.Routes();
        xmlPullParser.getDepth();
        while (true) {
            next = xmlPullParser.next();
            if (next == 1 || next == 3) {
                break;
            }
            if (xmlPullParser.getEventType() == 2) {
                if (xmlPullParser.getName().equals("route")) {
                    routes.getRoute().add(android.audio.policy.configuration.V7_0.Routes.Route.read(xmlPullParser));
                } else {
                    android.audio.policy.configuration.V7_0.XmlParser.skip(xmlPullParser);
                }
            }
        }
        if (next != 3) {
            throw new javax.xml.datatype.DatatypeConfigurationException("Routes is not closed");
        }
        return routes;
    }
}
