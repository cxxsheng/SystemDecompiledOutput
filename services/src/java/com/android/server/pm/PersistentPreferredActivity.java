package com.android.server.pm;

/* loaded from: classes2.dex */
class PersistentPreferredActivity extends com.android.server.pm.WatchedIntentFilter {
    private static final java.lang.String ATTR_FILTER = "filter";
    private static final java.lang.String ATTR_NAME = "name";
    private static final java.lang.String ATTR_SET_BY_DPM = "set-by-dpm";
    private static final boolean DEBUG_FILTERS = false;
    private static final java.lang.String TAG = "PersistentPreferredActivity";
    final android.content.ComponentName mComponent;
    final boolean mIsSetByDpm;
    final com.android.server.utils.SnapshotCache<com.android.server.pm.PersistentPreferredActivity> mSnapshot;

    private com.android.server.utils.SnapshotCache makeCache() {
        return new com.android.server.utils.SnapshotCache<com.android.server.pm.PersistentPreferredActivity>(this, this) { // from class: com.android.server.pm.PersistentPreferredActivity.1
            /* JADX WARN: Can't rename method to resolve collision */
            /* JADX WARN: Multi-variable type inference failed */
            @Override // com.android.server.utils.SnapshotCache
            public com.android.server.pm.PersistentPreferredActivity createSnapshot() {
                com.android.server.pm.PersistentPreferredActivity persistentPreferredActivity = new com.android.server.pm.PersistentPreferredActivity();
                persistentPreferredActivity.seal();
                return persistentPreferredActivity;
            }
        };
    }

    PersistentPreferredActivity(android.content.IntentFilter intentFilter, android.content.ComponentName componentName, boolean z) {
        super(intentFilter);
        this.mComponent = componentName;
        this.mIsSetByDpm = z;
        this.mSnapshot = makeCache();
    }

    PersistentPreferredActivity(com.android.server.pm.WatchedIntentFilter watchedIntentFilter, android.content.ComponentName componentName, boolean z) {
        this(watchedIntentFilter.mFilter, componentName, z);
    }

    private PersistentPreferredActivity(com.android.server.pm.PersistentPreferredActivity persistentPreferredActivity) {
        super(persistentPreferredActivity);
        this.mComponent = persistentPreferredActivity.mComponent;
        this.mIsSetByDpm = persistentPreferredActivity.mIsSetByDpm;
        this.mSnapshot = new com.android.server.utils.SnapshotCache.Sealed();
    }

    PersistentPreferredActivity(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        java.lang.String attributeValue = typedXmlPullParser.getAttributeValue((java.lang.String) null, "name");
        this.mComponent = android.content.ComponentName.unflattenFromString(attributeValue);
        if (this.mComponent == null) {
            com.android.server.pm.PackageManagerService.reportSettingsProblem(5, "Error in package manager settings: Bad activity name " + attributeValue + " at " + typedXmlPullParser.getPositionDescription());
        }
        this.mIsSetByDpm = typedXmlPullParser.getAttributeBoolean((java.lang.String) null, ATTR_SET_BY_DPM, false);
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
                com.android.server.pm.PackageManagerService.reportSettingsProblem(5, "Unknown element: " + name + " at " + typedXmlPullParser.getPositionDescription());
                com.android.internal.util.XmlUtils.skipCurrentTag(typedXmlPullParser);
            }
        }
        if (name.equals("filter")) {
            this.mFilter.readFromXml(typedXmlPullParser);
        } else {
            com.android.server.pm.PackageManagerService.reportSettingsProblem(5, "Missing element filter at " + typedXmlPullParser.getPositionDescription());
            com.android.internal.util.XmlUtils.skipCurrentTag(typedXmlPullParser);
        }
        this.mSnapshot = makeCache();
    }

    public void writeToXml(com.android.modules.utils.TypedXmlSerializer typedXmlSerializer) throws java.io.IOException {
        typedXmlSerializer.attribute((java.lang.String) null, "name", this.mComponent.flattenToShortString());
        typedXmlSerializer.attributeBoolean((java.lang.String) null, ATTR_SET_BY_DPM, this.mIsSetByDpm);
        typedXmlSerializer.startTag((java.lang.String) null, "filter");
        this.mFilter.writeToXml(typedXmlSerializer);
        typedXmlSerializer.endTag((java.lang.String) null, "filter");
    }

    @Override // com.android.server.pm.WatchedIntentFilter
    public android.content.IntentFilter getIntentFilter() {
        return this.mFilter;
    }

    public java.lang.String toString() {
        return "PersistentPreferredActivity{0x" + java.lang.Integer.toHexString(java.lang.System.identityHashCode(this)) + " " + this.mComponent.flattenToShortString() + ", mIsSetByDpm=" + this.mIsSetByDpm + "}";
    }

    @Override // com.android.server.pm.WatchedIntentFilter, com.android.server.utils.Snappable
    public com.android.server.pm.PersistentPreferredActivity snapshot() {
        return this.mSnapshot.snapshot();
    }
}
