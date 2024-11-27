package com.android.server.firewall;

/* loaded from: classes.dex */
class NotFilter implements com.android.server.firewall.Filter {
    public static final com.android.server.firewall.FilterFactory FACTORY = new com.android.server.firewall.FilterFactory("not") { // from class: com.android.server.firewall.NotFilter.1
        @Override // com.android.server.firewall.FilterFactory
        public com.android.server.firewall.Filter newFilter(org.xmlpull.v1.XmlPullParser xmlPullParser) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
            int depth = xmlPullParser.getDepth();
            com.android.server.firewall.Filter filter = null;
            while (com.android.internal.util.XmlUtils.nextElementWithin(xmlPullParser, depth)) {
                com.android.server.firewall.Filter parseFilter = com.android.server.firewall.IntentFirewall.parseFilter(xmlPullParser);
                if (filter != null) {
                    throw new org.xmlpull.v1.XmlPullParserException("<not> tag can only contain a single child filter.", xmlPullParser, null);
                }
                filter = parseFilter;
            }
            return new com.android.server.firewall.NotFilter(filter);
        }
    };
    private final com.android.server.firewall.Filter mChild;

    private NotFilter(com.android.server.firewall.Filter filter) {
        this.mChild = filter;
    }

    @Override // com.android.server.firewall.Filter
    public boolean matches(com.android.server.firewall.IntentFirewall intentFirewall, android.content.ComponentName componentName, android.content.Intent intent, int i, int i2, java.lang.String str, int i3) {
        return !this.mChild.matches(intentFirewall, componentName, intent, i, i2, str, i3);
    }
}
