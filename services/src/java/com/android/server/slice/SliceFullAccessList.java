package com.android.server.slice;

/* loaded from: classes2.dex */
public class SliceFullAccessList {
    static final int DB_VERSION = 1;
    private static final java.lang.String TAG = "SliceFullAccessList";
    private static final java.lang.String TAG_LIST = "slice-access-list";
    private static final java.lang.String TAG_PKG = "pkg";
    private static final java.lang.String TAG_USER = "user";
    private final android.content.Context mContext;
    private final java.lang.String ATT_USER_ID = TAG_USER;
    private final java.lang.String ATT_VERSION = "version";
    private final android.util.SparseArray<android.util.ArraySet<java.lang.String>> mFullAccessPkgs = new android.util.SparseArray<>();

    public SliceFullAccessList(android.content.Context context) {
        this.mContext = context;
    }

    public boolean hasFullAccess(java.lang.String str, int i) {
        android.util.ArraySet<java.lang.String> arraySet = this.mFullAccessPkgs.get(i, null);
        return arraySet != null && arraySet.contains(str);
    }

    public void grantFullAccess(java.lang.String str, int i) {
        android.util.ArraySet<java.lang.String> arraySet = this.mFullAccessPkgs.get(i, null);
        if (arraySet == null) {
            arraySet = new android.util.ArraySet<>();
            this.mFullAccessPkgs.put(i, arraySet);
        }
        arraySet.add(str);
    }

    public void removeGrant(java.lang.String str, int i) {
        android.util.ArraySet<java.lang.String> arraySet = this.mFullAccessPkgs.get(i, null);
        if (arraySet == null) {
            arraySet = new android.util.ArraySet<>();
            this.mFullAccessPkgs.put(i, arraySet);
        }
        arraySet.remove(str);
    }

    public void writeXml(org.xmlpull.v1.XmlSerializer xmlSerializer, int i) throws java.io.IOException {
        xmlSerializer.startTag(null, TAG_LIST);
        xmlSerializer.attribute(null, "version", java.lang.String.valueOf(1));
        int size = this.mFullAccessPkgs.size();
        for (int i2 = 0; i2 < size; i2++) {
            int keyAt = this.mFullAccessPkgs.keyAt(i2);
            android.util.ArraySet<java.lang.String> valueAt = this.mFullAccessPkgs.valueAt(i2);
            if (i == -1 || i == keyAt) {
                xmlSerializer.startTag(null, TAG_USER);
                xmlSerializer.attribute(null, TAG_USER, java.lang.Integer.toString(keyAt));
                if (valueAt != null) {
                    int size2 = valueAt.size();
                    for (int i3 = 0; i3 < size2; i3++) {
                        xmlSerializer.startTag(null, TAG_PKG);
                        xmlSerializer.text(valueAt.valueAt(i3));
                        xmlSerializer.endTag(null, TAG_PKG);
                    }
                }
                xmlSerializer.endTag(null, TAG_USER);
            }
        }
        xmlSerializer.endTag(null, TAG_LIST);
    }

    public void readXml(org.xmlpull.v1.XmlPullParser xmlPullParser) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        int readIntAttribute = com.android.internal.util.XmlUtils.readIntAttribute(xmlPullParser, "version", 0);
        java.util.Iterator it = android.os.UserManager.get(this.mContext).getAliveUsers().iterator();
        while (it.hasNext()) {
            upgradeXml(readIntAttribute, ((android.content.pm.UserInfo) it.next()).getUserHandle().getIdentifier());
        }
        this.mFullAccessPkgs.clear();
        while (true) {
            int next = xmlPullParser.next();
            if (next != 1) {
                java.lang.String name = xmlPullParser.getName();
                if (next != 3 || !TAG_LIST.equals(name)) {
                    if (next == 2 && TAG_USER.equals(name)) {
                        int readIntAttribute2 = com.android.internal.util.XmlUtils.readIntAttribute(xmlPullParser, TAG_USER, 0);
                        android.util.ArraySet<java.lang.String> arraySet = new android.util.ArraySet<>();
                        while (true) {
                            int next2 = xmlPullParser.next();
                            if (next2 == 1) {
                                break;
                            }
                            java.lang.String name2 = xmlPullParser.getName();
                            if (next2 == 3 && TAG_USER.equals(name2)) {
                                break;
                            } else if (next2 == 2 && TAG_PKG.equals(name2)) {
                                arraySet.add(xmlPullParser.nextText());
                            }
                        }
                        this.mFullAccessPkgs.put(readIntAttribute2, arraySet);
                    }
                } else {
                    return;
                }
            } else {
                return;
            }
        }
    }

    protected void upgradeXml(int i, int i2) {
    }
}
