package com.android.server.firewall;

/* loaded from: classes.dex */
class AndFilter extends com.android.server.firewall.FilterList {
    public static final com.android.server.firewall.FilterFactory FACTORY = new com.android.server.firewall.FilterFactory("and") { // from class: com.android.server.firewall.AndFilter.1
        @Override // com.android.server.firewall.FilterFactory
        public com.android.server.firewall.Filter newFilter(org.xmlpull.v1.XmlPullParser xmlPullParser) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
            return new com.android.server.firewall.AndFilter().readFromXml(xmlPullParser);
        }
    };

    AndFilter() {
    }

    @Override // com.android.server.firewall.Filter
    public boolean matches(com.android.server.firewall.IntentFirewall intentFirewall, android.content.ComponentName componentName, android.content.Intent intent, int i, int i2, java.lang.String str, int i3) {
        for (int i4 = 0; i4 < this.children.size(); i4++) {
            if (!this.children.get(i4).matches(intentFirewall, componentName, intent, i, i2, str, i3)) {
                return false;
            }
        }
        return true;
    }
}
