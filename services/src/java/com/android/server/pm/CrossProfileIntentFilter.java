package com.android.server.pm;

/* loaded from: classes2.dex */
class CrossProfileIntentFilter extends com.android.server.pm.WatchedIntentFilter {
    public static final int ACCESS_LEVEL_ALL = 0;
    public static final int ACCESS_LEVEL_SYSTEM = 10;
    public static final int ACCESS_LEVEL_SYSTEM_ADD_ONLY = 20;
    private static final java.lang.String ATTR_ACCESS_CONTROL = "accessControl";
    private static final java.lang.String ATTR_FILTER = "filter";
    private static final java.lang.String ATTR_FLAGS = "flags";
    private static final java.lang.String ATTR_OWNER_PACKAGE = "ownerPackage";
    private static final java.lang.String ATTR_TARGET_USER_ID = "targetUserId";
    public static final int FLAG_ALLOW_CHAINED_RESOLUTION = 16;
    public static final int FLAG_IS_PACKAGE_FOR_FILTER = 8;
    private static final java.lang.String TAG = "CrossProfileIntentFilter";
    final int mAccessControlLevel;
    final int mFlags;
    final java.lang.String mOwnerPackage;
    final com.android.server.utils.SnapshotCache<com.android.server.pm.CrossProfileIntentFilter> mSnapshot;
    final int mTargetUserId;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface AccessControlLevel {
    }

    private com.android.server.utils.SnapshotCache makeCache() {
        return new com.android.server.utils.SnapshotCache<com.android.server.pm.CrossProfileIntentFilter>(this, this) { // from class: com.android.server.pm.CrossProfileIntentFilter.1
            /* JADX WARN: Can't rename method to resolve collision */
            /* JADX WARN: Multi-variable type inference failed */
            @Override // com.android.server.utils.SnapshotCache
            public com.android.server.pm.CrossProfileIntentFilter createSnapshot() {
                com.android.server.pm.CrossProfileIntentFilter crossProfileIntentFilter = new com.android.server.pm.CrossProfileIntentFilter();
                crossProfileIntentFilter.seal();
                return crossProfileIntentFilter;
            }
        };
    }

    CrossProfileIntentFilter(android.content.IntentFilter intentFilter, java.lang.String str, int i, int i2) {
        this(intentFilter, str, i, i2, 0);
    }

    CrossProfileIntentFilter(android.content.IntentFilter intentFilter, java.lang.String str, int i, int i2, int i3) {
        super(intentFilter);
        this.mTargetUserId = i;
        this.mOwnerPackage = str;
        this.mFlags = i2;
        this.mAccessControlLevel = i3;
        this.mSnapshot = makeCache();
    }

    CrossProfileIntentFilter(com.android.server.pm.WatchedIntentFilter watchedIntentFilter, java.lang.String str, int i, int i2) {
        this(watchedIntentFilter.mFilter, str, i, i2);
    }

    CrossProfileIntentFilter(com.android.server.pm.WatchedIntentFilter watchedIntentFilter, java.lang.String str, int i, int i2, int i3) {
        this(watchedIntentFilter.mFilter, str, i, i2, i3);
    }

    private CrossProfileIntentFilter(com.android.server.pm.CrossProfileIntentFilter crossProfileIntentFilter) {
        super(crossProfileIntentFilter);
        this.mTargetUserId = crossProfileIntentFilter.mTargetUserId;
        this.mOwnerPackage = crossProfileIntentFilter.mOwnerPackage;
        this.mFlags = crossProfileIntentFilter.mFlags;
        this.mAccessControlLevel = crossProfileIntentFilter.mAccessControlLevel;
        this.mSnapshot = new com.android.server.utils.SnapshotCache.Sealed();
    }

    public int getTargetUserId() {
        return this.mTargetUserId;
    }

    public int getFlags() {
        return this.mFlags;
    }

    public java.lang.String getOwnerPackage() {
        return this.mOwnerPackage;
    }

    public int getAccessControlLevel() {
        return this.mAccessControlLevel;
    }

    CrossProfileIntentFilter(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        this.mTargetUserId = typedXmlPullParser.getAttributeInt((java.lang.String) null, ATTR_TARGET_USER_ID, com.android.server.am.ProcessList.INVALID_ADJ);
        this.mOwnerPackage = getStringFromXml(typedXmlPullParser, ATTR_OWNER_PACKAGE, "");
        this.mAccessControlLevel = typedXmlPullParser.getAttributeInt((java.lang.String) null, ATTR_ACCESS_CONTROL, 0);
        this.mFlags = typedXmlPullParser.getAttributeInt((java.lang.String) null, ATTR_FLAGS, 0);
        this.mSnapshot = makeCache();
        int depth = typedXmlPullParser.getDepth();
        java.lang.String name = typedXmlPullParser.getName();
        while (true) {
            int next = typedXmlPullParser.next();
            if (next == 1 || (next == 3 && typedXmlPullParser.getDepth() <= depth)) {
                break;
            }
            name = typedXmlPullParser.getName();
            if (next != 3 && next != 4 && next == 2) {
                if (name.equals("filter")) {
                    break;
                }
                com.android.server.pm.PackageManagerService.reportSettingsProblem(5, "Unknown element under crossProfile-intent-filters: " + name + " at " + typedXmlPullParser.getPositionDescription());
                com.android.internal.util.XmlUtils.skipCurrentTag(typedXmlPullParser);
            }
        }
        if (name.equals("filter")) {
            this.mFilter.readFromXml(typedXmlPullParser);
            return;
        }
        com.android.server.pm.PackageManagerService.reportSettingsProblem(5, "Missing element under CrossProfileIntentFilter: filter at " + typedXmlPullParser.getPositionDescription());
        com.android.internal.util.XmlUtils.skipCurrentTag(typedXmlPullParser);
    }

    private java.lang.String getStringFromXml(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser, java.lang.String str, java.lang.String str2) {
        java.lang.String attributeValue = typedXmlPullParser.getAttributeValue((java.lang.String) null, str);
        if (attributeValue == null) {
            com.android.server.pm.PackageManagerService.reportSettingsProblem(5, "Missing element under CrossProfileIntentFilter: " + str + " at " + typedXmlPullParser.getPositionDescription());
            return str2;
        }
        return attributeValue;
    }

    public void writeToXml(com.android.modules.utils.TypedXmlSerializer typedXmlSerializer) throws java.io.IOException {
        typedXmlSerializer.attributeInt((java.lang.String) null, ATTR_TARGET_USER_ID, this.mTargetUserId);
        typedXmlSerializer.attributeInt((java.lang.String) null, ATTR_FLAGS, this.mFlags);
        typedXmlSerializer.attribute((java.lang.String) null, ATTR_OWNER_PACKAGE, this.mOwnerPackage);
        typedXmlSerializer.attributeInt((java.lang.String) null, ATTR_ACCESS_CONTROL, this.mAccessControlLevel);
        typedXmlSerializer.startTag((java.lang.String) null, "filter");
        this.mFilter.writeToXml(typedXmlSerializer);
        typedXmlSerializer.endTag((java.lang.String) null, "filter");
    }

    public java.lang.String toString() {
        return "CrossProfileIntentFilter{0x" + java.lang.Integer.toHexString(java.lang.System.identityHashCode(this)) + " " + java.lang.Integer.toString(this.mTargetUserId) + "}";
    }

    boolean equalsIgnoreFilter(com.android.server.pm.CrossProfileIntentFilter crossProfileIntentFilter) {
        return this.mTargetUserId == crossProfileIntentFilter.mTargetUserId && this.mOwnerPackage.equals(crossProfileIntentFilter.mOwnerPackage) && this.mFlags == crossProfileIntentFilter.mFlags && this.mAccessControlLevel == crossProfileIntentFilter.mAccessControlLevel;
    }

    @Override // com.android.server.pm.WatchedIntentFilter, com.android.server.utils.Snappable
    public com.android.server.pm.CrossProfileIntentFilter snapshot() {
        return this.mSnapshot.snapshot();
    }
}
