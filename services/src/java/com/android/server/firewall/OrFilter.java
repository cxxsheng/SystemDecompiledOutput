package com.android.server.firewall;

/* loaded from: classes.dex */
class OrFilter extends com.android.server.firewall.FilterList {
    public static final com.android.server.firewall.FilterFactory FACTORY = new com.android.server.firewall.FilterFactory("or") { // from class: com.android.server.firewall.OrFilter.1
        @Override // com.android.server.firewall.FilterFactory
        public com.android.server.firewall.Filter newFilter(org.xmlpull.v1.XmlPullParser xmlPullParser) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
            return new com.android.server.firewall.OrFilter().readFromXml(xmlPullParser);
        }
    };

    OrFilter() {
    }

    @Override // com.android.server.firewall.Filter
    public boolean matches(com.android.server.firewall.IntentFirewall intentFirewall, android.content.ComponentName componentName, android.content.Intent intent, int i, int i2, java.lang.String str, int i3) {
        for (int i4 = 0; i4 < this.children.size(); i4++) {
            if (this.children.get(i4).matches(intentFirewall, componentName, intent, i, i2, str, i3)) {
                return true;
            }
        }
        return false;
    }
}
