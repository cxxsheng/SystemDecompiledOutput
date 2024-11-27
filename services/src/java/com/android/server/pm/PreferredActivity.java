package com.android.server.pm;

/* loaded from: classes2.dex */
class PreferredActivity extends com.android.server.pm.WatchedIntentFilter implements com.android.server.pm.PreferredComponent.Callbacks {
    private static final boolean DEBUG_FILTERS = false;
    private static final java.lang.String TAG = "PreferredActivity";
    final com.android.server.pm.PreferredComponent mPref;
    final com.android.server.utils.SnapshotCache<com.android.server.pm.PreferredActivity> mSnapshot;

    private com.android.server.utils.SnapshotCache makeCache() {
        return new com.android.server.utils.SnapshotCache<com.android.server.pm.PreferredActivity>(this, this) { // from class: com.android.server.pm.PreferredActivity.1
            /* JADX WARN: Can't rename method to resolve collision */
            /* JADX WARN: Multi-variable type inference failed */
            @Override // com.android.server.utils.SnapshotCache
            public com.android.server.pm.PreferredActivity createSnapshot() {
                com.android.server.pm.PreferredActivity preferredActivity = new com.android.server.pm.PreferredActivity();
                preferredActivity.seal();
                return preferredActivity;
            }
        };
    }

    PreferredActivity(android.content.IntentFilter intentFilter, int i, android.content.ComponentName[] componentNameArr, android.content.ComponentName componentName, boolean z) {
        super(intentFilter);
        this.mPref = new com.android.server.pm.PreferredComponent(this, i, componentNameArr, componentName, z);
        this.mSnapshot = makeCache();
    }

    PreferredActivity(com.android.server.pm.WatchedIntentFilter watchedIntentFilter, int i, android.content.ComponentName[] componentNameArr, android.content.ComponentName componentName, boolean z) {
        this(watchedIntentFilter.mFilter, i, componentNameArr, componentName, z);
    }

    private PreferredActivity(com.android.server.pm.PreferredActivity preferredActivity) {
        super(preferredActivity);
        this.mPref = preferredActivity.mPref;
        this.mSnapshot = new com.android.server.utils.SnapshotCache.Sealed();
    }

    PreferredActivity(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        this.mPref = new com.android.server.pm.PreferredComponent(this, typedXmlPullParser);
        this.mSnapshot = makeCache();
    }

    public void writeToXml(com.android.modules.utils.TypedXmlSerializer typedXmlSerializer, boolean z) throws java.io.IOException {
        this.mPref.writeToXml(typedXmlSerializer, z);
        typedXmlSerializer.startTag((java.lang.String) null, com.android.server.pm.verify.domain.DomainVerificationPersistence.ATTR_FILTER);
        this.mFilter.writeToXml(typedXmlSerializer);
        typedXmlSerializer.endTag((java.lang.String) null, com.android.server.pm.verify.domain.DomainVerificationPersistence.ATTR_FILTER);
    }

    @Override // com.android.server.pm.PreferredComponent.Callbacks
    public boolean onReadTag(java.lang.String str, com.android.modules.utils.TypedXmlPullParser typedXmlPullParser) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        if (str.equals(com.android.server.pm.verify.domain.DomainVerificationPersistence.ATTR_FILTER)) {
            this.mFilter.readFromXml(typedXmlPullParser);
            return true;
        }
        com.android.server.pm.PackageManagerService.reportSettingsProblem(5, "Unknown element under <preferred-activities>: " + typedXmlPullParser.getName());
        com.android.internal.util.XmlUtils.skipCurrentTag(typedXmlPullParser);
        return true;
    }

    public void dumpPref(java.io.PrintWriter printWriter, java.lang.String str, com.android.server.pm.PreferredActivity preferredActivity) {
        this.mPref.dump(printWriter, str, preferredActivity);
    }

    public java.lang.String toString() {
        return "PreferredActivity{0x" + java.lang.Integer.toHexString(java.lang.System.identityHashCode(this)) + " " + this.mPref.mComponent.flattenToShortString() + "}";
    }

    @Override // com.android.server.pm.WatchedIntentFilter, com.android.server.utils.Snappable
    public com.android.server.pm.PreferredActivity snapshot() {
        return this.mSnapshot.snapshot();
    }
}
