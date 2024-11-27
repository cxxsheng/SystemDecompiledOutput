package com.android.server.firewall;

/* loaded from: classes.dex */
class SenderFilter {
    private static final java.lang.String ATTR_TYPE = "type";
    public static final com.android.server.firewall.FilterFactory FACTORY = new com.android.server.firewall.FilterFactory("sender") { // from class: com.android.server.firewall.SenderFilter.1
        @Override // com.android.server.firewall.FilterFactory
        public com.android.server.firewall.Filter newFilter(org.xmlpull.v1.XmlPullParser xmlPullParser) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
            java.lang.String attributeValue = xmlPullParser.getAttributeValue(null, "type");
            if (attributeValue == null) {
                throw new org.xmlpull.v1.XmlPullParserException("type attribute must be specified for <sender>", xmlPullParser, null);
            }
            if (attributeValue.equals("system")) {
                return com.android.server.firewall.SenderFilter.SYSTEM;
            }
            if (attributeValue.equals(com.android.server.firewall.SenderFilter.VAL_SIGNATURE)) {
                return com.android.server.firewall.SenderFilter.SIGNATURE;
            }
            if (attributeValue.equals(com.android.server.firewall.SenderFilter.VAL_SYSTEM_OR_SIGNATURE)) {
                return com.android.server.firewall.SenderFilter.SYSTEM_OR_SIGNATURE;
            }
            if (attributeValue.equals("userId")) {
                return com.android.server.firewall.SenderFilter.USER_ID;
            }
            throw new org.xmlpull.v1.XmlPullParserException("Invalid type attribute for <sender>: " + attributeValue, xmlPullParser, null);
        }
    };
    private static final com.android.server.firewall.Filter SIGNATURE = new com.android.server.firewall.Filter() { // from class: com.android.server.firewall.SenderFilter.2
        @Override // com.android.server.firewall.Filter
        public boolean matches(com.android.server.firewall.IntentFirewall intentFirewall, android.content.ComponentName componentName, android.content.Intent intent, int i, int i2, java.lang.String str, int i3) {
            return intentFirewall.signaturesMatch(i, i3);
        }
    };
    private static final com.android.server.firewall.Filter SYSTEM = new com.android.server.firewall.Filter() { // from class: com.android.server.firewall.SenderFilter.3
        @Override // com.android.server.firewall.Filter
        public boolean matches(com.android.server.firewall.IntentFirewall intentFirewall, android.content.ComponentName componentName, android.content.Intent intent, int i, int i2, java.lang.String str, int i3) {
            return com.android.server.firewall.SenderFilter.isPrivilegedApp(intentFirewall.getPackageManager(), i, i2);
        }
    };
    private static final com.android.server.firewall.Filter SYSTEM_OR_SIGNATURE = new com.android.server.firewall.Filter() { // from class: com.android.server.firewall.SenderFilter.4
        @Override // com.android.server.firewall.Filter
        public boolean matches(com.android.server.firewall.IntentFirewall intentFirewall, android.content.ComponentName componentName, android.content.Intent intent, int i, int i2, java.lang.String str, int i3) {
            return com.android.server.firewall.SenderFilter.isPrivilegedApp(intentFirewall.getPackageManager(), i, i2) || intentFirewall.signaturesMatch(i, i3);
        }
    };
    private static final com.android.server.firewall.Filter USER_ID = new com.android.server.firewall.Filter() { // from class: com.android.server.firewall.SenderFilter.5
        @Override // com.android.server.firewall.Filter
        public boolean matches(com.android.server.firewall.IntentFirewall intentFirewall, android.content.ComponentName componentName, android.content.Intent intent, int i, int i2, java.lang.String str, int i3) {
            return intentFirewall.checkComponentPermission(null, i2, i, i3, false);
        }
    };
    private static final java.lang.String VAL_SIGNATURE = "signature";
    private static final java.lang.String VAL_SYSTEM = "system";
    private static final java.lang.String VAL_SYSTEM_OR_SIGNATURE = "system|signature";
    private static final java.lang.String VAL_USER_ID = "userId";

    SenderFilter() {
    }

    static boolean isPrivilegedApp(android.content.pm.PackageManagerInternal packageManagerInternal, int i, int i2) {
        if (i == 1000 || i == 0 || i2 == android.os.Process.myPid() || i2 == 0) {
            return true;
        }
        return packageManagerInternal.isUidPrivileged(i);
    }
}
