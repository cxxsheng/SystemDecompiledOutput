package com.android.server.firewall;

/* loaded from: classes.dex */
public class SenderPackageFilter implements com.android.server.firewall.Filter {
    private static final java.lang.String ATTR_NAME = "name";
    public static final com.android.server.firewall.FilterFactory FACTORY = new com.android.server.firewall.FilterFactory("sender-package") { // from class: com.android.server.firewall.SenderPackageFilter.1
        @Override // com.android.server.firewall.FilterFactory
        public com.android.server.firewall.Filter newFilter(org.xmlpull.v1.XmlPullParser xmlPullParser) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
            java.lang.String attributeValue = xmlPullParser.getAttributeValue(null, "name");
            if (attributeValue == null) {
                throw new org.xmlpull.v1.XmlPullParserException("A package name must be specified.", xmlPullParser, null);
            }
            return new com.android.server.firewall.SenderPackageFilter(attributeValue);
        }
    };
    public final java.lang.String mPackageName;

    public SenderPackageFilter(java.lang.String str) {
        this.mPackageName = str;
    }

    @Override // com.android.server.firewall.Filter
    public boolean matches(com.android.server.firewall.IntentFirewall intentFirewall, android.content.ComponentName componentName, android.content.Intent intent, int i, int i2, java.lang.String str, int i3) {
        int i4;
        try {
            i4 = android.app.AppGlobals.getPackageManager().getPackageUid(this.mPackageName, 4194304L, 0);
        } catch (android.os.RemoteException e) {
            i4 = -1;
        }
        if (i4 == -1) {
            return false;
        }
        return android.os.UserHandle.isSameApp(i4, i);
    }
}
