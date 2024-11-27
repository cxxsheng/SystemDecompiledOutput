package android.app.admin;

/* loaded from: classes.dex */
public final class DeviceAdminInfo implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.app.admin.DeviceAdminInfo> CREATOR;
    public static final int HEADLESS_DEVICE_OWNER_MODE_AFFILIATED = 1;
    public static final int HEADLESS_DEVICE_OWNER_MODE_SINGLE_USER = 2;
    public static final int HEADLESS_DEVICE_OWNER_MODE_UNSUPPORTED = 0;
    static final java.lang.String TAG = "DeviceAdminInfo";
    public static final int USES_ENCRYPTED_STORAGE = 7;
    public static final int USES_POLICY_DISABLE_CAMERA = 8;
    public static final int USES_POLICY_DISABLE_KEYGUARD_FEATURES = 9;
    public static final int USES_POLICY_EXPIRE_PASSWORD = 6;
    public static final int USES_POLICY_FORCE_LOCK = 3;
    public static final int USES_POLICY_LIMIT_PASSWORD = 0;
    public static final int USES_POLICY_RESET_PASSWORD = 2;
    public static final int USES_POLICY_SETS_GLOBAL_PROXY = 5;
    public static final int USES_POLICY_WATCH_LOGIN = 1;
    public static final int USES_POLICY_WIPE_DATA = 4;
    final android.content.pm.ActivityInfo mActivityInfo;
    int mHeadlessDeviceOwnerMode;
    boolean mSupportsTransferOwnership;
    int mUsesPolicies;
    boolean mVisible;
    static java.util.ArrayList<android.app.admin.DeviceAdminInfo.PolicyInfo> sPoliciesDisplayOrder = new java.util.ArrayList<>();
    static java.util.HashMap<java.lang.String, java.lang.Integer> sKnownPolicies = new java.util.HashMap<>();
    static android.util.SparseArray<android.app.admin.DeviceAdminInfo.PolicyInfo> sRevKnownPolicies = new android.util.SparseArray<>();

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    private @interface HeadlessDeviceOwnerMode {
    }

    public static class PolicyInfo {
        public final int description;
        public final int descriptionForSecondaryUsers;
        public final int ident;
        public final int label;
        public final int labelForSecondaryUsers;
        public final java.lang.String tag;

        public PolicyInfo(int i, java.lang.String str, int i2, int i3) {
            this(i, str, i2, i3, i2, i3);
        }

        public PolicyInfo(int i, java.lang.String str, int i2, int i3, int i4, int i5) {
            this.ident = i;
            this.tag = str;
            this.label = i2;
            this.description = i3;
            this.labelForSecondaryUsers = i4;
            this.descriptionForSecondaryUsers = i5;
        }
    }

    static {
        sPoliciesDisplayOrder.add(new android.app.admin.DeviceAdminInfo.PolicyInfo(4, "wipe-data", com.android.internal.R.string.policylab_wipeData, com.android.internal.R.string.policydesc_wipeData, com.android.internal.R.string.policylab_wipeData_secondaryUser, com.android.internal.R.string.policydesc_wipeData_secondaryUser));
        sPoliciesDisplayOrder.add(new android.app.admin.DeviceAdminInfo.PolicyInfo(2, "reset-password", com.android.internal.R.string.policylab_resetPassword, com.android.internal.R.string.policydesc_resetPassword));
        sPoliciesDisplayOrder.add(new android.app.admin.DeviceAdminInfo.PolicyInfo(0, "limit-password", com.android.internal.R.string.policylab_limitPassword, com.android.internal.R.string.policydesc_limitPassword));
        sPoliciesDisplayOrder.add(new android.app.admin.DeviceAdminInfo.PolicyInfo(1, "watch-login", com.android.internal.R.string.policylab_watchLogin, com.android.internal.R.string.policydesc_watchLogin, com.android.internal.R.string.policylab_watchLogin, com.android.internal.R.string.policydesc_watchLogin_secondaryUser));
        sPoliciesDisplayOrder.add(new android.app.admin.DeviceAdminInfo.PolicyInfo(3, "force-lock", com.android.internal.R.string.policylab_forceLock, com.android.internal.R.string.policydesc_forceLock));
        sPoliciesDisplayOrder.add(new android.app.admin.DeviceAdminInfo.PolicyInfo(5, "set-global-proxy", com.android.internal.R.string.policylab_setGlobalProxy, com.android.internal.R.string.policydesc_setGlobalProxy));
        sPoliciesDisplayOrder.add(new android.app.admin.DeviceAdminInfo.PolicyInfo(6, "expire-password", com.android.internal.R.string.policylab_expirePassword, com.android.internal.R.string.policydesc_expirePassword));
        sPoliciesDisplayOrder.add(new android.app.admin.DeviceAdminInfo.PolicyInfo(7, "encrypted-storage", com.android.internal.R.string.policylab_encryptedStorage, com.android.internal.R.string.policydesc_encryptedStorage));
        sPoliciesDisplayOrder.add(new android.app.admin.DeviceAdminInfo.PolicyInfo(8, "disable-camera", com.android.internal.R.string.policylab_disableCamera, com.android.internal.R.string.policydesc_disableCamera));
        sPoliciesDisplayOrder.add(new android.app.admin.DeviceAdminInfo.PolicyInfo(9, "disable-keyguard-features", com.android.internal.R.string.policylab_disableKeyguardFeatures, com.android.internal.R.string.policydesc_disableKeyguardFeatures));
        for (int i = 0; i < sPoliciesDisplayOrder.size(); i++) {
            android.app.admin.DeviceAdminInfo.PolicyInfo policyInfo = sPoliciesDisplayOrder.get(i);
            sRevKnownPolicies.put(policyInfo.ident, policyInfo);
            sKnownPolicies.put(policyInfo.tag, java.lang.Integer.valueOf(policyInfo.ident));
        }
        CREATOR = new android.os.Parcelable.Creator<android.app.admin.DeviceAdminInfo>() { // from class: android.app.admin.DeviceAdminInfo.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.app.admin.DeviceAdminInfo createFromParcel(android.os.Parcel parcel) {
                return new android.app.admin.DeviceAdminInfo(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.app.admin.DeviceAdminInfo[] newArray(int i2) {
                return new android.app.admin.DeviceAdminInfo[i2];
            }
        };
    }

    public DeviceAdminInfo(android.content.Context context, android.content.pm.ResolveInfo resolveInfo) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        this(context, resolveInfo.activityInfo);
    }

    public DeviceAdminInfo(android.content.Context context, android.content.pm.ActivityInfo activityInfo) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        int next;
        this.mHeadlessDeviceOwnerMode = 0;
        this.mActivityInfo = activityInfo;
        android.content.pm.PackageManager packageManager = context.getPackageManager();
        android.content.res.XmlResourceParser xmlResourceParser = null;
        try {
            try {
                android.content.res.XmlResourceParser loadXmlMetaData = this.mActivityInfo.loadXmlMetaData(packageManager, android.app.admin.DeviceAdminReceiver.DEVICE_ADMIN_META_DATA);
                try {
                    if (loadXmlMetaData == null) {
                        throw new org.xmlpull.v1.XmlPullParserException("No android.app.device_admin meta-data");
                    }
                    android.content.res.Resources resourcesForApplication = packageManager.getResourcesForApplication(this.mActivityInfo.applicationInfo);
                    android.util.AttributeSet asAttributeSet = android.util.Xml.asAttributeSet(loadXmlMetaData);
                    do {
                        next = loadXmlMetaData.next();
                        if (next == 1) {
                            break;
                        }
                    } while (next != 2);
                    if (!"device-admin".equals(loadXmlMetaData.getName())) {
                        throw new org.xmlpull.v1.XmlPullParserException("Meta-data does not start with device-admin tag");
                    }
                    android.content.res.TypedArray obtainAttributes = resourcesForApplication.obtainAttributes(asAttributeSet, com.android.internal.R.styleable.DeviceAdmin);
                    this.mVisible = obtainAttributes.getBoolean(0, true);
                    obtainAttributes.recycle();
                    int depth = loadXmlMetaData.getDepth();
                    while (true) {
                        int next2 = loadXmlMetaData.next();
                        if (next2 == 1 || (next2 == 3 && loadXmlMetaData.getDepth() <= depth)) {
                            break;
                        }
                        if (next2 != 3 && next2 != 4) {
                            java.lang.String name = loadXmlMetaData.getName();
                            if (name.equals("uses-policies")) {
                                int depth2 = loadXmlMetaData.getDepth();
                                while (true) {
                                    int next3 = loadXmlMetaData.next();
                                    if (next3 == 1 || (next3 == 3 && loadXmlMetaData.getDepth() <= depth2)) {
                                        break;
                                    }
                                    if (next3 != 3 && next3 != 4) {
                                        java.lang.String name2 = loadXmlMetaData.getName();
                                        java.lang.Integer num = sKnownPolicies.get(name2);
                                        if (num != null) {
                                            this.mUsesPolicies |= 1 << num.intValue();
                                        } else {
                                            android.util.Log.w(TAG, "Unknown tag under uses-policies of " + getComponent() + ": " + name2);
                                        }
                                    }
                                }
                            } else if (name.equals("support-transfer-ownership")) {
                                if (loadXmlMetaData.next() != 3) {
                                    throw new org.xmlpull.v1.XmlPullParserException("support-transfer-ownership tag must be empty.");
                                }
                                this.mSupportsTransferOwnership = true;
                            } else if (name.equals("headless-system-user")) {
                                java.lang.String attributeValue = loadXmlMetaData.getAttributeValue(null, "device-owner-mode");
                                if (attributeValue.equalsIgnoreCase("unsupported")) {
                                    this.mHeadlessDeviceOwnerMode = 0;
                                } else if (attributeValue.equalsIgnoreCase("affiliated")) {
                                    this.mHeadlessDeviceOwnerMode = 1;
                                } else {
                                    if (!attributeValue.equalsIgnoreCase("single_user")) {
                                        throw new org.xmlpull.v1.XmlPullParserException("headless-system-user mode must be valid");
                                    }
                                    this.mHeadlessDeviceOwnerMode = 2;
                                }
                            } else {
                                continue;
                            }
                        }
                    }
                    if (loadXmlMetaData != null) {
                        loadXmlMetaData.close();
                    }
                } catch (android.content.pm.PackageManager.NameNotFoundException e) {
                    throw new org.xmlpull.v1.XmlPullParserException("Unable to create context for: " + this.mActivityInfo.packageName);
                } catch (java.lang.Throwable th) {
                    th = th;
                    xmlResourceParser = loadXmlMetaData;
                    if (xmlResourceParser != null) {
                        xmlResourceParser.close();
                    }
                    throw th;
                }
            } catch (android.content.pm.PackageManager.NameNotFoundException e2) {
            }
        } catch (java.lang.Throwable th2) {
            th = th2;
        }
    }

    DeviceAdminInfo(android.os.Parcel parcel) {
        this.mHeadlessDeviceOwnerMode = 0;
        this.mActivityInfo = android.content.pm.ActivityInfo.CREATOR.createFromParcel(parcel);
        this.mUsesPolicies = parcel.readInt();
        this.mSupportsTransferOwnership = parcel.readBoolean();
        this.mHeadlessDeviceOwnerMode = parcel.readInt();
    }

    public java.lang.String getPackageName() {
        return this.mActivityInfo.packageName;
    }

    public java.lang.String getReceiverName() {
        return this.mActivityInfo.name;
    }

    public android.content.pm.ActivityInfo getActivityInfo() {
        return this.mActivityInfo;
    }

    public android.content.ComponentName getComponent() {
        return new android.content.ComponentName(this.mActivityInfo.packageName, this.mActivityInfo.name);
    }

    public java.lang.CharSequence loadLabel(android.content.pm.PackageManager packageManager) {
        return this.mActivityInfo.loadLabel(packageManager);
    }

    public java.lang.CharSequence loadDescription(android.content.pm.PackageManager packageManager) throws android.content.res.Resources.NotFoundException {
        if (this.mActivityInfo.descriptionRes != 0) {
            return packageManager.getText(this.mActivityInfo.packageName, this.mActivityInfo.descriptionRes, this.mActivityInfo.applicationInfo);
        }
        throw new android.content.res.Resources.NotFoundException();
    }

    public android.graphics.drawable.Drawable loadIcon(android.content.pm.PackageManager packageManager) {
        return this.mActivityInfo.loadIcon(packageManager);
    }

    public boolean isVisible() {
        return this.mVisible;
    }

    public boolean usesPolicy(int i) {
        return ((1 << i) & this.mUsesPolicies) != 0;
    }

    public java.lang.String getTagForPolicy(int i) {
        return sRevKnownPolicies.get(i).tag;
    }

    public boolean supportsTransferOwnership() {
        return this.mSupportsTransferOwnership;
    }

    public int getHeadlessDeviceOwnerMode() {
        return this.mHeadlessDeviceOwnerMode;
    }

    public java.util.ArrayList<android.app.admin.DeviceAdminInfo.PolicyInfo> getUsedPolicies() {
        java.util.ArrayList<android.app.admin.DeviceAdminInfo.PolicyInfo> arrayList = new java.util.ArrayList<>();
        for (int i = 0; i < sPoliciesDisplayOrder.size(); i++) {
            android.app.admin.DeviceAdminInfo.PolicyInfo policyInfo = sPoliciesDisplayOrder.get(i);
            if (usesPolicy(policyInfo.ident)) {
                arrayList.add(policyInfo);
            }
        }
        return arrayList;
    }

    public void writePoliciesToXml(com.android.modules.utils.TypedXmlSerializer typedXmlSerializer) throws java.lang.IllegalArgumentException, java.lang.IllegalStateException, java.io.IOException {
        typedXmlSerializer.attributeInt(null, "flags", this.mUsesPolicies);
    }

    public void readPoliciesFromXml(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        this.mUsesPolicies = typedXmlPullParser.getAttributeInt(null, "flags");
    }

    public void dump(android.util.Printer printer, java.lang.String str) {
        printer.println(str + "Receiver:");
        this.mActivityInfo.dump(printer, str + "  ");
    }

    public java.lang.String toString() {
        return "DeviceAdminInfo{" + this.mActivityInfo.name + "}";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        this.mActivityInfo.writeToParcel(parcel, i);
        parcel.writeInt(this.mUsesPolicies);
        parcel.writeBoolean(this.mSupportsTransferOwnership);
        parcel.writeInt(this.mHeadlessDeviceOwnerMode);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }
}
