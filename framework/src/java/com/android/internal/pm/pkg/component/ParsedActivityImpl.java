package com.android.internal.pm.pkg.component;

/* loaded from: classes5.dex */
public class ParsedActivityImpl extends com.android.internal.pm.pkg.component.ParsedMainComponentImpl implements com.android.internal.pm.pkg.component.ParsedActivity, android.os.Parcelable {
    public static final android.os.Parcelable.Creator<com.android.internal.pm.pkg.component.ParsedActivityImpl> CREATOR = new android.os.Parcelable.Creator<com.android.internal.pm.pkg.component.ParsedActivityImpl>() { // from class: com.android.internal.pm.pkg.component.ParsedActivityImpl.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public com.android.internal.pm.pkg.component.ParsedActivityImpl createFromParcel(android.os.Parcel parcel) {
            return new com.android.internal.pm.pkg.component.ParsedActivityImpl(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public com.android.internal.pm.pkg.component.ParsedActivityImpl[] newArray(int i) {
            return new com.android.internal.pm.pkg.component.ParsedActivityImpl[i];
        }
    };
    private int colorMode;
    private int configChanges;
    private int documentLaunchMode;
    private int launchMode;
    private int lockTaskLaunchMode;
    private java.util.Set<java.lang.String> mKnownActivityEmbeddingCerts;
    private int mRequireContentUriPermissionFromCaller;
    private java.lang.String mRequiredDisplayCategory;
    private float maxAspectRatio;
    private int maxRecents;
    private float minAspectRatio;
    private java.lang.String parentActivityName;
    private java.lang.String permission;
    private int persistableMode;
    private int privateFlags;
    private java.lang.String requestedVrComponent;
    private int resizeMode;
    private int rotationAnimation;
    private int screenOrientation;
    private int softInputMode;
    private boolean supportsSizeChanges;
    private java.lang.String targetActivity;
    private java.lang.String taskAffinity;
    private int theme;
    private int uiOptions;
    private android.content.pm.ActivityInfo.WindowLayout windowLayout;

    public ParsedActivityImpl(com.android.internal.pm.pkg.component.ParsedActivityImpl parsedActivityImpl) {
        super(parsedActivityImpl);
        this.screenOrientation = -1;
        this.resizeMode = 2;
        this.maxAspectRatio = -1.0f;
        this.minAspectRatio = -1.0f;
        this.rotationAnimation = -1;
        this.theme = parsedActivityImpl.theme;
        this.uiOptions = parsedActivityImpl.uiOptions;
        this.targetActivity = parsedActivityImpl.targetActivity;
        this.parentActivityName = parsedActivityImpl.parentActivityName;
        this.taskAffinity = parsedActivityImpl.taskAffinity;
        this.privateFlags = parsedActivityImpl.privateFlags;
        this.permission = parsedActivityImpl.permission;
        this.launchMode = parsedActivityImpl.launchMode;
        this.documentLaunchMode = parsedActivityImpl.documentLaunchMode;
        this.maxRecents = parsedActivityImpl.maxRecents;
        this.configChanges = parsedActivityImpl.configChanges;
        this.softInputMode = parsedActivityImpl.softInputMode;
        this.persistableMode = parsedActivityImpl.persistableMode;
        this.lockTaskLaunchMode = parsedActivityImpl.lockTaskLaunchMode;
        this.screenOrientation = parsedActivityImpl.screenOrientation;
        this.resizeMode = parsedActivityImpl.resizeMode;
        this.maxAspectRatio = parsedActivityImpl.maxAspectRatio;
        this.minAspectRatio = parsedActivityImpl.minAspectRatio;
        this.supportsSizeChanges = parsedActivityImpl.supportsSizeChanges;
        this.requestedVrComponent = parsedActivityImpl.requestedVrComponent;
        this.rotationAnimation = parsedActivityImpl.rotationAnimation;
        this.colorMode = parsedActivityImpl.colorMode;
        this.windowLayout = parsedActivityImpl.windowLayout;
        this.mKnownActivityEmbeddingCerts = parsedActivityImpl.mKnownActivityEmbeddingCerts;
        this.mRequiredDisplayCategory = parsedActivityImpl.mRequiredDisplayCategory;
        this.mRequireContentUriPermissionFromCaller = parsedActivityImpl.mRequireContentUriPermissionFromCaller;
    }

    public static com.android.internal.pm.pkg.component.ParsedActivityImpl makeAppDetailsActivity(java.lang.String str, java.lang.String str2, int i, java.lang.String str3, boolean z) {
        com.android.internal.pm.pkg.component.ParsedActivityImpl parsedActivityImpl = new com.android.internal.pm.pkg.component.ParsedActivityImpl();
        parsedActivityImpl.setPackageName(str);
        parsedActivityImpl.theme = 16973909;
        parsedActivityImpl.setExported(true);
        parsedActivityImpl.setName(android.content.pm.PackageManager.APP_DETAILS_ACTIVITY_CLASS_NAME);
        parsedActivityImpl.setProcessName(str2);
        parsedActivityImpl.uiOptions = i;
        parsedActivityImpl.taskAffinity = str3;
        parsedActivityImpl.launchMode = 0;
        parsedActivityImpl.documentLaunchMode = 0;
        parsedActivityImpl.maxRecents = android.app.ActivityTaskManager.getDefaultAppRecentsLimitStatic();
        parsedActivityImpl.configChanges = com.android.internal.pm.pkg.component.ParsedActivityUtils.getActivityConfigChanges(0, 0);
        parsedActivityImpl.softInputMode = 0;
        parsedActivityImpl.persistableMode = 1;
        parsedActivityImpl.screenOrientation = -1;
        parsedActivityImpl.resizeMode = 4;
        parsedActivityImpl.lockTaskLaunchMode = 0;
        parsedActivityImpl.setDirectBootAware(false);
        parsedActivityImpl.rotationAnimation = -1;
        parsedActivityImpl.colorMode = 0;
        if (z) {
            parsedActivityImpl.setFlags(parsedActivityImpl.getFlags() | 512);
        }
        return parsedActivityImpl;
    }

    static com.android.internal.pm.pkg.component.ParsedActivityImpl makeAlias(java.lang.String str, com.android.internal.pm.pkg.component.ParsedActivity parsedActivity) {
        com.android.internal.pm.pkg.component.ParsedActivityImpl parsedActivityImpl = new com.android.internal.pm.pkg.component.ParsedActivityImpl();
        parsedActivityImpl.setPackageName(parsedActivity.getPackageName());
        parsedActivityImpl.setTargetActivity(str);
        parsedActivityImpl.configChanges = parsedActivity.getConfigChanges();
        parsedActivityImpl.setFlags(parsedActivity.getFlags());
        parsedActivityImpl.privateFlags = parsedActivity.getPrivateFlags();
        parsedActivityImpl.setIcon(parsedActivity.getIcon());
        parsedActivityImpl.setLogo(parsedActivity.getLogo());
        parsedActivityImpl.setBanner(parsedActivity.getBanner());
        parsedActivityImpl.setLabelRes(parsedActivity.getLabelRes());
        parsedActivityImpl.setNonLocalizedLabel(parsedActivity.getNonLocalizedLabel());
        parsedActivityImpl.launchMode = parsedActivity.getLaunchMode();
        parsedActivityImpl.lockTaskLaunchMode = parsedActivity.getLockTaskLaunchMode();
        parsedActivityImpl.documentLaunchMode = parsedActivity.getDocumentLaunchMode();
        parsedActivityImpl.setDescriptionRes(parsedActivity.getDescriptionRes());
        parsedActivityImpl.screenOrientation = parsedActivity.getScreenOrientation();
        parsedActivityImpl.taskAffinity = parsedActivity.getTaskAffinity();
        parsedActivityImpl.theme = parsedActivity.getTheme();
        parsedActivityImpl.softInputMode = parsedActivity.getSoftInputMode();
        parsedActivityImpl.uiOptions = parsedActivity.getUiOptions();
        parsedActivityImpl.parentActivityName = parsedActivity.getParentActivityName();
        parsedActivityImpl.maxRecents = parsedActivity.getMaxRecents();
        parsedActivityImpl.windowLayout = parsedActivity.getWindowLayout();
        parsedActivityImpl.resizeMode = parsedActivity.getResizeMode();
        parsedActivityImpl.maxAspectRatio = parsedActivity.getMaxAspectRatio();
        parsedActivityImpl.minAspectRatio = parsedActivity.getMinAspectRatio();
        parsedActivityImpl.supportsSizeChanges = parsedActivity.isSupportsSizeChanges();
        parsedActivityImpl.requestedVrComponent = parsedActivity.getRequestedVrComponent();
        parsedActivityImpl.setDirectBootAware(parsedActivity.isDirectBootAware());
        parsedActivityImpl.setProcessName(parsedActivity.getProcessName());
        parsedActivityImpl.setRequiredDisplayCategory(parsedActivity.getRequiredDisplayCategory());
        parsedActivityImpl.setRequireContentUriPermissionFromCaller(parsedActivity.getRequireContentUriPermissionFromCaller());
        return parsedActivityImpl;
    }

    public com.android.internal.pm.pkg.component.ParsedActivityImpl setMaxAspectRatio(int i, float f) {
        if (i == 2 || i == 1) {
            return this;
        }
        if (f < 1.0f && f != 0.0f) {
            return this;
        }
        this.maxAspectRatio = f;
        return this;
    }

    public com.android.internal.pm.pkg.component.ParsedActivityImpl setMinAspectRatio(int i, float f) {
        if (i == 2 || i == 1) {
            return this;
        }
        if (f < 1.0f && f != 0.0f) {
            return this;
        }
        this.minAspectRatio = f;
        return this;
    }

    public com.android.internal.pm.pkg.component.ParsedActivityImpl setTargetActivity(java.lang.String str) {
        this.targetActivity = android.text.TextUtils.safeIntern(str);
        return this;
    }

    public com.android.internal.pm.pkg.component.ParsedActivityImpl setPermission(java.lang.String str) {
        this.permission = android.text.TextUtils.isEmpty(str) ? null : str.intern();
        return this;
    }

    @Override // com.android.internal.pm.pkg.component.ParsedActivity
    public java.util.Set<java.lang.String> getKnownActivityEmbeddingCerts() {
        return this.mKnownActivityEmbeddingCerts == null ? java.util.Collections.emptySet() : this.mKnownActivityEmbeddingCerts;
    }

    public void setKnownActivityEmbeddingCerts(java.util.Set<java.lang.String> set) {
        this.mKnownActivityEmbeddingCerts = new android.util.ArraySet();
        java.util.Iterator<java.lang.String> it = set.iterator();
        while (it.hasNext()) {
            this.mKnownActivityEmbeddingCerts.add(it.next().toUpperCase(java.util.Locale.US));
        }
    }

    public java.lang.String toString() {
        java.lang.StringBuilder sb = new java.lang.StringBuilder(128);
        sb.append("Activity{");
        sb.append(java.lang.Integer.toHexString(java.lang.System.identityHashCode(this)));
        sb.append(' ');
        android.content.ComponentName.appendShortString(sb, getPackageName(), getName());
        sb.append('}');
        return sb.toString();
    }

    @Override // com.android.internal.pm.pkg.component.ParsedMainComponentImpl, com.android.internal.pm.pkg.component.ParsedComponentImpl, android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // com.android.internal.pm.pkg.component.ParsedMainComponentImpl, com.android.internal.pm.pkg.component.ParsedComponentImpl, android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeInt(this.theme);
        parcel.writeInt(this.uiOptions);
        parcel.writeString(this.targetActivity);
        parcel.writeString(this.parentActivityName);
        parcel.writeString(this.taskAffinity);
        parcel.writeInt(this.privateFlags);
        com.android.internal.pm.parsing.pkg.PackageImpl.sForInternedString.parcel(this.permission, parcel, i);
        parcel.writeInt(this.launchMode);
        parcel.writeInt(this.documentLaunchMode);
        parcel.writeInt(this.maxRecents);
        parcel.writeInt(this.configChanges);
        parcel.writeInt(this.softInputMode);
        parcel.writeInt(this.persistableMode);
        parcel.writeInt(this.lockTaskLaunchMode);
        parcel.writeInt(this.screenOrientation);
        parcel.writeInt(this.resizeMode);
        parcel.writeValue(java.lang.Float.valueOf(this.maxAspectRatio));
        parcel.writeValue(java.lang.Float.valueOf(this.minAspectRatio));
        parcel.writeBoolean(this.supportsSizeChanges);
        parcel.writeString(this.requestedVrComponent);
        parcel.writeInt(this.rotationAnimation);
        parcel.writeInt(this.colorMode);
        parcel.writeBundle(getMetaData());
        if (this.windowLayout != null) {
            parcel.writeInt(1);
            this.windowLayout.writeToParcel(parcel);
        } else {
            parcel.writeBoolean(false);
        }
        com.android.internal.pm.parsing.pkg.PackageImpl.sForStringSet.parcel(this.mKnownActivityEmbeddingCerts, parcel, i);
        parcel.writeString8(this.mRequiredDisplayCategory);
        parcel.writeInt(this.mRequireContentUriPermissionFromCaller);
    }

    public ParsedActivityImpl() {
        this.screenOrientation = -1;
        this.resizeMode = 2;
        this.maxAspectRatio = -1.0f;
        this.minAspectRatio = -1.0f;
        this.rotationAnimation = -1;
    }

    protected ParsedActivityImpl(android.os.Parcel parcel) {
        super(parcel);
        this.screenOrientation = -1;
        this.resizeMode = 2;
        this.maxAspectRatio = -1.0f;
        this.minAspectRatio = -1.0f;
        this.rotationAnimation = -1;
        this.theme = parcel.readInt();
        this.uiOptions = parcel.readInt();
        this.targetActivity = parcel.readString();
        this.parentActivityName = parcel.readString();
        this.taskAffinity = parcel.readString();
        this.privateFlags = parcel.readInt();
        this.permission = com.android.internal.pm.parsing.pkg.PackageImpl.sForInternedString.unparcel(parcel);
        this.launchMode = parcel.readInt();
        this.documentLaunchMode = parcel.readInt();
        this.maxRecents = parcel.readInt();
        this.configChanges = parcel.readInt();
        this.softInputMode = parcel.readInt();
        this.persistableMode = parcel.readInt();
        this.lockTaskLaunchMode = parcel.readInt();
        this.screenOrientation = parcel.readInt();
        this.resizeMode = parcel.readInt();
        this.maxAspectRatio = ((java.lang.Float) parcel.readValue(java.lang.Float.class.getClassLoader())).floatValue();
        this.minAspectRatio = ((java.lang.Float) parcel.readValue(java.lang.Float.class.getClassLoader())).floatValue();
        this.supportsSizeChanges = parcel.readBoolean();
        this.requestedVrComponent = parcel.readString();
        this.rotationAnimation = parcel.readInt();
        this.colorMode = parcel.readInt();
        setMetaData(parcel.readBundle());
        if (parcel.readBoolean()) {
            this.windowLayout = new android.content.pm.ActivityInfo.WindowLayout(parcel);
        }
        this.mKnownActivityEmbeddingCerts = com.android.internal.pm.parsing.pkg.PackageImpl.sForStringSet.unparcel(parcel);
        this.mRequiredDisplayCategory = parcel.readString8();
        this.mRequireContentUriPermissionFromCaller = parcel.readInt();
    }

    public ParsedActivityImpl(int i, int i2, java.lang.String str, java.lang.String str2, java.lang.String str3, int i3, java.lang.String str4, java.util.Set<java.lang.String> set, int i4, int i5, int i6, int i7, int i8, int i9, int i10, int i11, int i12, float f, float f2, boolean z, java.lang.String str5, int i13, int i14, android.content.pm.ActivityInfo.WindowLayout windowLayout, java.lang.String str6, int i15) {
        this.screenOrientation = -1;
        this.resizeMode = 2;
        this.maxAspectRatio = -1.0f;
        this.minAspectRatio = -1.0f;
        this.rotationAnimation = -1;
        this.theme = i;
        this.uiOptions = i2;
        this.targetActivity = str;
        this.parentActivityName = str2;
        this.taskAffinity = str3;
        this.privateFlags = i3;
        this.permission = str4;
        this.mKnownActivityEmbeddingCerts = set;
        this.launchMode = i4;
        this.documentLaunchMode = i5;
        this.maxRecents = i6;
        this.configChanges = i7;
        this.softInputMode = i8;
        this.persistableMode = i9;
        this.lockTaskLaunchMode = i10;
        this.screenOrientation = i11;
        this.resizeMode = i12;
        this.maxAspectRatio = f;
        this.minAspectRatio = f2;
        this.supportsSizeChanges = z;
        this.requestedVrComponent = str5;
        this.rotationAnimation = i13;
        this.colorMode = i14;
        this.windowLayout = windowLayout;
        this.mRequiredDisplayCategory = str6;
        this.mRequireContentUriPermissionFromCaller = i15;
    }

    @Override // com.android.internal.pm.pkg.component.ParsedActivity
    public int getTheme() {
        return this.theme;
    }

    @Override // com.android.internal.pm.pkg.component.ParsedActivity
    public int getUiOptions() {
        return this.uiOptions;
    }

    @Override // com.android.internal.pm.pkg.component.ParsedActivity
    public java.lang.String getTargetActivity() {
        return this.targetActivity;
    }

    @Override // com.android.internal.pm.pkg.component.ParsedActivity
    public java.lang.String getParentActivityName() {
        return this.parentActivityName;
    }

    @Override // com.android.internal.pm.pkg.component.ParsedActivity
    public java.lang.String getTaskAffinity() {
        return this.taskAffinity;
    }

    @Override // com.android.internal.pm.pkg.component.ParsedActivity
    public int getPrivateFlags() {
        return this.privateFlags;
    }

    @Override // com.android.internal.pm.pkg.component.ParsedActivity
    public java.lang.String getPermission() {
        return this.permission;
    }

    @Override // com.android.internal.pm.pkg.component.ParsedActivity
    public int getLaunchMode() {
        return this.launchMode;
    }

    @Override // com.android.internal.pm.pkg.component.ParsedActivity
    public int getDocumentLaunchMode() {
        return this.documentLaunchMode;
    }

    @Override // com.android.internal.pm.pkg.component.ParsedActivity
    public int getMaxRecents() {
        return this.maxRecents;
    }

    @Override // com.android.internal.pm.pkg.component.ParsedActivity
    public int getConfigChanges() {
        return this.configChanges;
    }

    @Override // com.android.internal.pm.pkg.component.ParsedActivity
    public int getSoftInputMode() {
        return this.softInputMode;
    }

    @Override // com.android.internal.pm.pkg.component.ParsedActivity
    public int getPersistableMode() {
        return this.persistableMode;
    }

    @Override // com.android.internal.pm.pkg.component.ParsedActivity
    public int getLockTaskLaunchMode() {
        return this.lockTaskLaunchMode;
    }

    @Override // com.android.internal.pm.pkg.component.ParsedActivity
    public int getScreenOrientation() {
        return this.screenOrientation;
    }

    @Override // com.android.internal.pm.pkg.component.ParsedActivity
    public int getResizeMode() {
        return this.resizeMode;
    }

    @Override // com.android.internal.pm.pkg.component.ParsedActivity
    public float getMaxAspectRatio() {
        return this.maxAspectRatio;
    }

    @Override // com.android.internal.pm.pkg.component.ParsedActivity
    public float getMinAspectRatio() {
        return this.minAspectRatio;
    }

    @Override // com.android.internal.pm.pkg.component.ParsedActivity
    public boolean isSupportsSizeChanges() {
        return this.supportsSizeChanges;
    }

    @Override // com.android.internal.pm.pkg.component.ParsedActivity
    public java.lang.String getRequestedVrComponent() {
        return this.requestedVrComponent;
    }

    @Override // com.android.internal.pm.pkg.component.ParsedActivity
    public int getRotationAnimation() {
        return this.rotationAnimation;
    }

    @Override // com.android.internal.pm.pkg.component.ParsedActivity
    public int getColorMode() {
        return this.colorMode;
    }

    @Override // com.android.internal.pm.pkg.component.ParsedActivity
    public android.content.pm.ActivityInfo.WindowLayout getWindowLayout() {
        return this.windowLayout;
    }

    @Override // com.android.internal.pm.pkg.component.ParsedActivity
    public java.lang.String getRequiredDisplayCategory() {
        return this.mRequiredDisplayCategory;
    }

    @Override // com.android.internal.pm.pkg.component.ParsedActivity
    public int getRequireContentUriPermissionFromCaller() {
        return this.mRequireContentUriPermissionFromCaller;
    }

    public com.android.internal.pm.pkg.component.ParsedActivityImpl setTheme(int i) {
        this.theme = i;
        return this;
    }

    public com.android.internal.pm.pkg.component.ParsedActivityImpl setUiOptions(int i) {
        this.uiOptions = i;
        return this;
    }

    public com.android.internal.pm.pkg.component.ParsedActivityImpl setParentActivityName(java.lang.String str) {
        this.parentActivityName = str;
        return this;
    }

    public com.android.internal.pm.pkg.component.ParsedActivityImpl setTaskAffinity(java.lang.String str) {
        this.taskAffinity = str;
        return this;
    }

    public com.android.internal.pm.pkg.component.ParsedActivityImpl setPrivateFlags(int i) {
        this.privateFlags = i;
        return this;
    }

    public com.android.internal.pm.pkg.component.ParsedActivityImpl setLaunchMode(int i) {
        this.launchMode = i;
        return this;
    }

    public com.android.internal.pm.pkg.component.ParsedActivityImpl setDocumentLaunchMode(int i) {
        this.documentLaunchMode = i;
        return this;
    }

    public com.android.internal.pm.pkg.component.ParsedActivityImpl setMaxRecents(int i) {
        this.maxRecents = i;
        return this;
    }

    public com.android.internal.pm.pkg.component.ParsedActivityImpl setConfigChanges(int i) {
        this.configChanges = i;
        return this;
    }

    public com.android.internal.pm.pkg.component.ParsedActivityImpl setSoftInputMode(int i) {
        this.softInputMode = i;
        return this;
    }

    public com.android.internal.pm.pkg.component.ParsedActivityImpl setPersistableMode(int i) {
        this.persistableMode = i;
        return this;
    }

    public com.android.internal.pm.pkg.component.ParsedActivityImpl setLockTaskLaunchMode(int i) {
        this.lockTaskLaunchMode = i;
        return this;
    }

    public com.android.internal.pm.pkg.component.ParsedActivityImpl setScreenOrientation(int i) {
        this.screenOrientation = i;
        return this;
    }

    public com.android.internal.pm.pkg.component.ParsedActivityImpl setResizeMode(int i) {
        this.resizeMode = i;
        return this;
    }

    public com.android.internal.pm.pkg.component.ParsedActivityImpl setMaxAspectRatio(float f) {
        this.maxAspectRatio = f;
        return this;
    }

    public com.android.internal.pm.pkg.component.ParsedActivityImpl setMinAspectRatio(float f) {
        this.minAspectRatio = f;
        return this;
    }

    public com.android.internal.pm.pkg.component.ParsedActivityImpl setSupportsSizeChanges(boolean z) {
        this.supportsSizeChanges = z;
        return this;
    }

    public com.android.internal.pm.pkg.component.ParsedActivityImpl setRequestedVrComponent(java.lang.String str) {
        this.requestedVrComponent = str;
        return this;
    }

    public com.android.internal.pm.pkg.component.ParsedActivityImpl setRotationAnimation(int i) {
        this.rotationAnimation = i;
        return this;
    }

    public com.android.internal.pm.pkg.component.ParsedActivityImpl setColorMode(int i) {
        this.colorMode = i;
        return this;
    }

    public com.android.internal.pm.pkg.component.ParsedActivityImpl setWindowLayout(android.content.pm.ActivityInfo.WindowLayout windowLayout) {
        this.windowLayout = windowLayout;
        return this;
    }

    public com.android.internal.pm.pkg.component.ParsedActivityImpl setRequiredDisplayCategory(java.lang.String str) {
        this.mRequiredDisplayCategory = str;
        return this;
    }

    public com.android.internal.pm.pkg.component.ParsedActivityImpl setRequireContentUriPermissionFromCaller(int i) {
        this.mRequireContentUriPermissionFromCaller = i;
        return this;
    }

    @java.lang.Deprecated
    private void __metadata() {
    }
}
