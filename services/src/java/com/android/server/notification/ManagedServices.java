package com.android.server.notification;

/* loaded from: classes2.dex */
public abstract class ManagedServices {
    static final int APPROVAL_BY_COMPONENT = 1;
    static final int APPROVAL_BY_PACKAGE = 0;
    static final java.lang.String ATT_APPROVED_LIST = "approved";
    static final java.lang.String ATT_DEFAULTS = "defaults";
    static final java.lang.String ATT_IS_PRIMARY = "primary";
    static final java.lang.String ATT_USER_CHANGED = "user_changed";
    static final java.lang.String ATT_USER_ID = "user";
    static final java.lang.String ATT_USER_SET = "user_set_services";
    static final java.lang.String ATT_USER_SET_OLD = "user_set";
    static final java.lang.String ATT_VERSION = "version";
    static final java.lang.String DB_VERSION = "4";
    private static final java.lang.String DB_VERSION_1 = "1";
    private static final java.lang.String DB_VERSION_2 = "2";
    private static final java.lang.String DB_VERSION_3 = "3";
    protected static final java.lang.String ENABLED_SERVICES_SEPARATOR = ":";
    private static final int ON_BINDING_DIED_REBIND_DELAY_MS = 10000;
    static final java.lang.String TAG_MANAGED_SERVICES = "service_listing";
    protected final android.content.Context mContext;
    protected final java.lang.Object mMutex;
    protected final android.content.pm.IPackageManager mPm;
    protected final android.os.UserManager mUm;
    private boolean mUseXml;
    private final com.android.server.notification.ManagedServices.UserProfiles mUserProfiles;
    protected final java.lang.String TAG = getClass().getSimpleName();
    protected final boolean DEBUG = android.util.Log.isLoggable(this.TAG, 3);
    private final android.os.Handler mHandler = new android.os.Handler(android.os.Looper.getMainLooper());

    @com.android.internal.annotations.GuardedBy({"mMutex"})
    private final java.util.ArrayList<com.android.server.notification.ManagedServices.ManagedServiceInfo> mServices = new java.util.ArrayList<>();

    @com.android.internal.annotations.GuardedBy({"mMutex"})
    private final java.util.ArrayList<android.util.Pair<android.content.ComponentName, java.lang.Integer>> mServicesBound = new java.util.ArrayList<>();

    @com.android.internal.annotations.GuardedBy({"mMutex"})
    private final android.util.ArraySet<android.util.Pair<android.content.ComponentName, java.lang.Integer>> mServicesRebinding = new android.util.ArraySet<>();
    protected final java.lang.Object mDefaultsLock = new java.lang.Object();

    @com.android.internal.annotations.GuardedBy({"mDefaultsLock"})
    protected final android.util.ArraySet<android.content.ComponentName> mDefaultComponents = new android.util.ArraySet<>();

    @com.android.internal.annotations.GuardedBy({"mDefaultsLock"})
    protected final android.util.ArraySet<java.lang.String> mDefaultPackages = new android.util.ArraySet<>();

    @com.android.internal.annotations.GuardedBy({"mMutex"})
    private final android.util.ArraySet<android.content.ComponentName> mEnabledServicesForCurrentProfiles = new android.util.ArraySet<>();

    @com.android.internal.annotations.GuardedBy({"mMutex"})
    private final android.util.ArraySet<java.lang.String> mEnabledServicesPackageNames = new android.util.ArraySet<>();

    @com.android.internal.annotations.GuardedBy({"mSnoozing"})
    private final android.util.SparseSetArray<android.content.ComponentName> mSnoozing = new android.util.SparseSetArray<>();

    @com.android.internal.annotations.GuardedBy({"mApproved"})
    protected final android.util.ArrayMap<java.lang.Integer, android.util.ArrayMap<java.lang.Boolean, android.util.ArraySet<java.lang.String>>> mApproved = new android.util.ArrayMap<>();

    @com.android.internal.annotations.GuardedBy({"mApproved"})
    protected android.util.ArrayMap<java.lang.Integer, android.util.ArraySet<java.lang.String>> mUserSetServices = new android.util.ArrayMap<>();

    @com.android.internal.annotations.GuardedBy({"mApproved"})
    protected android.util.ArrayMap<java.lang.Integer, java.lang.Boolean> mIsUserChanged = new android.util.ArrayMap<>();
    private final com.android.server.notification.ManagedServices.Config mConfig = getConfig();
    protected int mApprovalLevel = 1;

    public static class Config {
        public java.lang.String bindPermission;
        public java.lang.String caption;
        public int clientLabel;
        public java.lang.String secondarySettingName;
        public java.lang.String secureSettingName;
        public java.lang.String serviceInterface;
        public java.lang.String settingsAction;
        public java.lang.String xmlTag;
    }

    protected abstract boolean allowRebindForParentUser();

    protected abstract android.os.IInterface asInterface(android.os.IBinder iBinder);

    protected abstract boolean checkType(android.os.IInterface iInterface);

    protected abstract void ensureFilters(android.content.pm.ServiceInfo serviceInfo, int i);

    protected abstract com.android.server.notification.ManagedServices.Config getConfig();

    protected abstract java.lang.String getRequiredPermission();

    protected abstract void loadDefaultsFromConfig();

    protected abstract void onServiceAdded(com.android.server.notification.ManagedServices.ManagedServiceInfo managedServiceInfo);

    public ManagedServices(android.content.Context context, java.lang.Object obj, com.android.server.notification.ManagedServices.UserProfiles userProfiles, android.content.pm.IPackageManager iPackageManager) {
        this.mContext = context;
        this.mMutex = obj;
        this.mUserProfiles = userProfiles;
        this.mPm = iPackageManager;
        this.mUm = (android.os.UserManager) this.mContext.getSystemService(ATT_USER_ID);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public java.lang.String getCaption() {
        return this.mConfig.caption;
    }

    protected java.util.List<com.android.server.notification.ManagedServices.ManagedServiceInfo> getServices() {
        java.util.ArrayList arrayList;
        synchronized (this.mMutex) {
            arrayList = new java.util.ArrayList(this.mServices);
        }
        return arrayList;
    }

    protected void addDefaultComponentOrPackage(java.lang.String str) {
        if (!android.text.TextUtils.isEmpty(str)) {
            synchronized (this.mDefaultsLock) {
                try {
                    if (this.mApprovalLevel == 0) {
                        this.mDefaultPackages.add(str);
                        return;
                    }
                    android.content.ComponentName unflattenFromString = android.content.ComponentName.unflattenFromString(str);
                    if (unflattenFromString != null && this.mApprovalLevel == 1) {
                        this.mDefaultPackages.add(unflattenFromString.getPackageName());
                        this.mDefaultComponents.add(unflattenFromString);
                    }
                } finally {
                }
            }
        }
    }

    boolean isDefaultComponentOrPackage(java.lang.String str) {
        synchronized (this.mDefaultsLock) {
            try {
                android.content.ComponentName unflattenFromString = android.content.ComponentName.unflattenFromString(str);
                if (unflattenFromString == null) {
                    return this.mDefaultPackages.contains(str);
                }
                return this.mDefaultComponents.contains(unflattenFromString);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    android.util.ArraySet<android.content.ComponentName> getDefaultComponents() {
        android.util.ArraySet<android.content.ComponentName> arraySet;
        synchronized (this.mDefaultsLock) {
            arraySet = new android.util.ArraySet<>(this.mDefaultComponents);
        }
        return arraySet;
    }

    android.util.ArraySet<java.lang.String> getDefaultPackages() {
        android.util.ArraySet<java.lang.String> arraySet;
        synchronized (this.mDefaultsLock) {
            arraySet = new android.util.ArraySet<>(this.mDefaultPackages);
        }
        return arraySet;
    }

    @android.annotation.NonNull
    android.util.ArrayMap<java.lang.Boolean, java.util.ArrayList<android.content.ComponentName>> resetComponents(java.lang.String str, int i) {
        java.util.ArrayList<android.content.ComponentName> arrayList;
        java.util.ArrayList<android.content.ComponentName> arrayList2;
        boolean z;
        boolean z2;
        android.util.ArraySet arraySet = new android.util.ArraySet(getAllowedComponents(i));
        synchronized (this.mDefaultsLock) {
            try {
                arrayList = new java.util.ArrayList<>(this.mDefaultComponents.size());
                arrayList2 = new java.util.ArrayList<>(this.mDefaultComponents.size());
                for (int i2 = 0; i2 < this.mDefaultComponents.size() && arraySet.size() > 0; i2++) {
                    android.content.ComponentName valueAt = this.mDefaultComponents.valueAt(i2);
                    if (str.equals(valueAt.getPackageName()) && !arraySet.contains(valueAt)) {
                        arrayList.add(valueAt);
                    }
                }
                synchronized (this.mApproved) {
                    try {
                        android.util.ArrayMap<java.lang.Boolean, android.util.ArraySet<java.lang.String>> arrayMap = this.mApproved.get(java.lang.Integer.valueOf(i));
                        if (arrayMap == null) {
                            z = false;
                        } else {
                            int size = arrayMap.size();
                            z = false;
                            for (int i3 = 0; i3 < size; i3++) {
                                android.util.ArraySet<java.lang.String> valueAt2 = arrayMap.valueAt(i3);
                                for (int i4 = 0; i4 < arraySet.size(); i4++) {
                                    android.content.ComponentName componentName = (android.content.ComponentName) arraySet.valueAt(i4);
                                    if (str.equals(componentName.getPackageName()) && !this.mDefaultComponents.contains(componentName) && valueAt2.remove(componentName.flattenToString())) {
                                        arrayList2.add(componentName);
                                        clearUserSetFlagLocked(componentName, i);
                                        z = true;
                                    }
                                }
                                for (int i5 = 0; i5 < arrayList.size(); i5++) {
                                    z |= valueAt2.add(arrayList.get(i5).flattenToString());
                                }
                            }
                        }
                    } finally {
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        if (z) {
            z2 = false;
            rebindServices(false, -1);
        } else {
            z2 = false;
        }
        android.util.ArrayMap<java.lang.Boolean, java.util.ArrayList<android.content.ComponentName>> arrayMap2 = new android.util.ArrayMap<>();
        arrayMap2.put(true, arrayList);
        arrayMap2.put(java.lang.Boolean.valueOf(z2), arrayList2);
        return arrayMap2;
    }

    @com.android.internal.annotations.GuardedBy({"mApproved"})
    private boolean clearUserSetFlagLocked(android.content.ComponentName componentName, int i) {
        java.lang.String approvedValue = getApprovedValue(componentName.flattenToString());
        android.util.ArraySet<java.lang.String> arraySet = this.mUserSetServices.get(java.lang.Integer.valueOf(i));
        return arraySet != null && arraySet.remove(approvedValue);
    }

    protected int getBindFlags() {
        return 83886081;
    }

    protected void onServiceRemovedLocked(com.android.server.notification.ManagedServices.ManagedServiceInfo managedServiceInfo) {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public com.android.server.notification.ManagedServices.ManagedServiceInfo newServiceInfo(android.os.IInterface iInterface, android.content.ComponentName componentName, int i, boolean z, android.content.ServiceConnection serviceConnection, int i2, int i3) {
        return new com.android.server.notification.ManagedServices.ManagedServiceInfo(iInterface, componentName, i, z, serviceConnection, i2, i3);
    }

    public void onBootPhaseAppsCanStart() {
    }

    public void dump(java.io.PrintWriter printWriter, com.android.server.notification.NotificationManagerService.DumpFilter dumpFilter) {
        int i;
        android.util.SparseSetArray sparseSetArray;
        printWriter.println("    Allowed " + getCaption() + "s:");
        synchronized (this.mApproved) {
            try {
                int size = this.mApproved.size();
                for (int i2 = 0; i2 < size; i2++) {
                    int intValue = this.mApproved.keyAt(i2).intValue();
                    android.util.ArrayMap<java.lang.Boolean, android.util.ArraySet<java.lang.String>> valueAt = this.mApproved.valueAt(i2);
                    java.lang.Boolean bool = this.mIsUserChanged.get(java.lang.Integer.valueOf(intValue));
                    if (valueAt != null) {
                        int size2 = valueAt.size();
                        for (int i3 = 0; i3 < size2; i3++) {
                            boolean booleanValue = valueAt.keyAt(i3).booleanValue();
                            android.util.ArraySet<java.lang.String> valueAt2 = valueAt.valueAt(i3);
                            if (valueAt.size() > 0) {
                                java.lang.StringBuilder sb = new java.lang.StringBuilder();
                                sb.append("      ");
                                sb.append(java.lang.String.join(ENABLED_SERVICES_SEPARATOR, valueAt2));
                                sb.append(" (user: ");
                                sb.append(intValue);
                                sb.append(" isPrimary: ");
                                sb.append(booleanValue);
                                sb.append(bool == null ? "" : " isUserChanged: " + bool);
                                sb.append(")");
                                printWriter.println(sb.toString());
                            }
                        }
                    }
                }
                printWriter.println("    Has user set:");
                java.util.Iterator<java.lang.Integer> it = this.mUserSetServices.keySet().iterator();
                while (it.hasNext()) {
                    int intValue2 = it.next().intValue();
                    if (this.mIsUserChanged.get(java.lang.Integer.valueOf(intValue2)) == null) {
                        printWriter.println("      userId=" + intValue2 + " value=" + this.mUserSetServices.get(java.lang.Integer.valueOf(intValue2)));
                    }
                }
            } finally {
            }
        }
        synchronized (this.mMutex) {
            try {
                printWriter.println("    All " + getCaption() + "s (" + this.mEnabledServicesForCurrentProfiles.size() + ") enabled for current profiles:");
                java.util.Iterator<android.content.ComponentName> it2 = this.mEnabledServicesForCurrentProfiles.iterator();
                while (it2.hasNext()) {
                    android.content.ComponentName next = it2.next();
                    if (dumpFilter == null || dumpFilter.matches(next)) {
                        printWriter.println("      " + next);
                    }
                }
                printWriter.println("    Live " + getCaption() + "s (" + this.mServices.size() + "):");
                java.util.Iterator<com.android.server.notification.ManagedServices.ManagedServiceInfo> it3 = this.mServices.iterator();
                while (it3.hasNext()) {
                    com.android.server.notification.ManagedServices.ManagedServiceInfo next2 = it3.next();
                    if (dumpFilter == null || dumpFilter.matches(next2.component)) {
                        java.lang.StringBuilder sb2 = new java.lang.StringBuilder();
                        sb2.append("      ");
                        sb2.append(next2.component);
                        sb2.append(" (user ");
                        sb2.append(next2.userid);
                        sb2.append("): ");
                        sb2.append(next2.service);
                        sb2.append(next2.isSystem ? " SYSTEM" : "");
                        sb2.append(next2.isGuest(this) ? " GUEST" : "");
                        printWriter.println(sb2.toString());
                    }
                }
            } finally {
            }
        }
        synchronized (this.mSnoozing) {
            sparseSetArray = new android.util.SparseSetArray(this.mSnoozing);
        }
        printWriter.println("    Snoozed " + getCaption() + "s (" + sparseSetArray.size() + "):");
        for (i = 0; i < sparseSetArray.size(); i++) {
            printWriter.println("      User: " + sparseSetArray.keyAt(i));
            java.util.Iterator it4 = sparseSetArray.valuesAt(i).iterator();
            while (it4.hasNext()) {
                android.content.ComponentName componentName = (android.content.ComponentName) it4.next();
                android.content.pm.ServiceInfo serviceInfo = getServiceInfo(componentName, sparseSetArray.keyAt(i));
                java.lang.StringBuilder sb3 = new java.lang.StringBuilder();
                sb3.append("        ");
                sb3.append(componentName.flattenToShortString());
                sb3.append(isAutobindAllowed(serviceInfo) ? "" : " (META_DATA_DEFAULT_AUTOBIND=false)");
                printWriter.println(sb3.toString());
            }
        }
    }

    public void dump(android.util.proto.ProtoOutputStream protoOutputStream, com.android.server.notification.NotificationManagerService.DumpFilter dumpFilter) {
        int i;
        int i2;
        protoOutputStream.write(1138166333441L, getCaption());
        synchronized (this.mApproved) {
            try {
                int size = this.mApproved.size();
                int i3 = 0;
                while (true) {
                    long j = 2246267895810L;
                    if (i3 >= size) {
                        break;
                    }
                    int intValue = this.mApproved.keyAt(i3).intValue();
                    android.util.ArrayMap<java.lang.Boolean, android.util.ArraySet<java.lang.String>> valueAt = this.mApproved.valueAt(i3);
                    if (valueAt == null) {
                        i = i3;
                    } else {
                        int size2 = valueAt.size();
                        int i4 = 0;
                        while (i4 < size2) {
                            boolean booleanValue = valueAt.keyAt(i4).booleanValue();
                            android.util.ArraySet<java.lang.String> valueAt2 = valueAt.valueAt(i4);
                            if (valueAt.size() <= 0) {
                                i2 = i3;
                            } else {
                                i2 = i3;
                                long start = protoOutputStream.start(j);
                                java.util.Iterator<java.lang.String> it = valueAt2.iterator();
                                while (it.hasNext()) {
                                    protoOutputStream.write(2237677961217L, it.next());
                                }
                                protoOutputStream.write(1120986464258L, intValue);
                                protoOutputStream.write(1133871366147L, booleanValue);
                                protoOutputStream.end(start);
                            }
                            i4++;
                            i3 = i2;
                            j = 2246267895810L;
                        }
                        i = i3;
                    }
                    i3 = i + 1;
                }
            } finally {
            }
        }
        synchronized (this.mMutex) {
            try {
                java.util.Iterator<android.content.ComponentName> it2 = this.mEnabledServicesForCurrentProfiles.iterator();
                while (it2.hasNext()) {
                    android.content.ComponentName next = it2.next();
                    if (dumpFilter == null || dumpFilter.matches(next)) {
                        next.dumpDebug(protoOutputStream, 2246267895811L);
                    }
                }
                java.util.Iterator<com.android.server.notification.ManagedServices.ManagedServiceInfo> it3 = this.mServices.iterator();
                while (it3.hasNext()) {
                    com.android.server.notification.ManagedServices.ManagedServiceInfo next2 = it3.next();
                    if (dumpFilter == null || dumpFilter.matches(next2.component)) {
                        next2.dumpDebug(protoOutputStream, 2246267895812L, this);
                    }
                }
            } finally {
            }
        }
        synchronized (this.mSnoozing) {
            for (int i5 = 0; i5 < this.mSnoozing.size(); i5++) {
                try {
                    long start2 = protoOutputStream.start(2246267895814L);
                    protoOutputStream.write(1120986464257L, this.mSnoozing.keyAt(i5));
                    java.util.Iterator it4 = this.mSnoozing.valuesAt(i5).iterator();
                    while (it4.hasNext()) {
                        ((android.content.ComponentName) it4.next()).dumpDebug(protoOutputStream, 2246267895810L);
                    }
                    protoOutputStream.end(start2);
                } finally {
                }
            }
        }
    }

    protected void onSettingRestored(java.lang.String str, java.lang.String str2, int i, int i2) {
        if (!this.mUseXml) {
            android.util.Slog.d(this.TAG, "Restored managed service setting: " + str);
            if (this.mConfig.secureSettingName.equals(str) || (this.mConfig.secondarySettingName != null && this.mConfig.secondarySettingName.equals(str))) {
                if (i < 26) {
                    java.lang.String approved = getApproved(i2, this.mConfig.secureSettingName.equals(str));
                    if (!android.text.TextUtils.isEmpty(approved)) {
                        if (!android.text.TextUtils.isEmpty(str2)) {
                            str2 = str2 + ENABLED_SERVICES_SEPARATOR + approved;
                        } else {
                            str2 = approved;
                        }
                    }
                }
                if (shouldReflectToSettings()) {
                    android.provider.Settings.Secure.putStringForUser(this.mContext.getContentResolver(), str, str2, i2);
                }
                java.util.Iterator it = this.mUm.getUsers().iterator();
                while (it.hasNext()) {
                    addApprovedList(str2, ((android.content.pm.UserInfo) it.next()).id, this.mConfig.secureSettingName.equals(str));
                }
                android.util.Slog.d(this.TAG, "Done loading approved values from settings");
                rebindServices(false, i2);
            }
        }
    }

    void writeDefaults(com.android.modules.utils.TypedXmlSerializer typedXmlSerializer) throws java.io.IOException {
        synchronized (this.mDefaultsLock) {
            try {
                java.util.ArrayList arrayList = new java.util.ArrayList(this.mDefaultComponents.size());
                for (int i = 0; i < this.mDefaultComponents.size(); i++) {
                    arrayList.add(this.mDefaultComponents.valueAt(i).flattenToString());
                }
                typedXmlSerializer.attribute((java.lang.String) null, ATT_DEFAULTS, java.lang.String.join(ENABLED_SERVICES_SEPARATOR, arrayList));
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void writeXml(com.android.modules.utils.TypedXmlSerializer typedXmlSerializer, boolean z, int i) throws java.io.IOException {
        java.lang.String join;
        typedXmlSerializer.startTag((java.lang.String) null, getConfig().xmlTag);
        typedXmlSerializer.attributeInt((java.lang.String) null, ATT_VERSION, java.lang.Integer.parseInt(DB_VERSION));
        writeDefaults(typedXmlSerializer);
        if (z) {
            trimApprovedListsAccordingToInstalledServices(i);
        }
        synchronized (this.mApproved) {
            try {
                int size = this.mApproved.size();
                for (int i2 = 0; i2 < size; i2++) {
                    int intValue = this.mApproved.keyAt(i2).intValue();
                    if (!z || intValue == i) {
                        android.util.ArrayMap<java.lang.Boolean, android.util.ArraySet<java.lang.String>> valueAt = this.mApproved.valueAt(i2);
                        java.lang.Boolean bool = this.mIsUserChanged.get(java.lang.Integer.valueOf(intValue));
                        if (valueAt != null) {
                            int size2 = valueAt.size();
                            for (int i3 = 0; i3 < size2; i3++) {
                                boolean booleanValue = valueAt.keyAt(i3).booleanValue();
                                android.util.ArraySet<java.lang.String> valueAt2 = valueAt.valueAt(i3);
                                android.util.ArraySet<java.lang.String> arraySet = this.mUserSetServices.get(java.lang.Integer.valueOf(intValue));
                                if (valueAt2 != null || arraySet != null || bool != null) {
                                    if (valueAt2 == null) {
                                        join = "";
                                    } else {
                                        join = java.lang.String.join(ENABLED_SERVICES_SEPARATOR, valueAt2);
                                    }
                                    typedXmlSerializer.startTag((java.lang.String) null, TAG_MANAGED_SERVICES);
                                    typedXmlSerializer.attribute((java.lang.String) null, ATT_APPROVED_LIST, join);
                                    typedXmlSerializer.attributeInt((java.lang.String) null, ATT_USER_ID, intValue);
                                    typedXmlSerializer.attributeBoolean((java.lang.String) null, ATT_IS_PRIMARY, booleanValue);
                                    if (bool != null) {
                                        typedXmlSerializer.attributeBoolean((java.lang.String) null, ATT_USER_CHANGED, bool.booleanValue());
                                    } else if (arraySet != null) {
                                        typedXmlSerializer.attribute((java.lang.String) null, ATT_USER_SET, java.lang.String.join(ENABLED_SERVICES_SEPARATOR, arraySet));
                                    }
                                    writeExtraAttributes(typedXmlSerializer, intValue);
                                    typedXmlSerializer.endTag((java.lang.String) null, TAG_MANAGED_SERVICES);
                                    if (!z && booleanValue && shouldReflectToSettings()) {
                                        android.provider.Settings.Secure.putStringForUser(this.mContext.getContentResolver(), getConfig().secureSettingName, join, intValue);
                                    }
                                }
                            }
                        }
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        writeExtraXmlTags(typedXmlSerializer);
        typedXmlSerializer.endTag((java.lang.String) null, getConfig().xmlTag);
    }

    protected boolean shouldReflectToSettings() {
        return false;
    }

    protected void writeExtraAttributes(com.android.modules.utils.TypedXmlSerializer typedXmlSerializer, int i) throws java.io.IOException {
    }

    protected void writeExtraXmlTags(com.android.modules.utils.TypedXmlSerializer typedXmlSerializer) throws java.io.IOException {
    }

    protected void readExtraTag(java.lang.String str, com.android.modules.utils.TypedXmlPullParser typedXmlPullParser) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
    }

    protected final void migrateToXml() {
        for (android.content.pm.UserInfo userInfo : this.mUm.getUsers()) {
            android.content.ContentResolver contentResolver = this.mContext.getContentResolver();
            if (!android.text.TextUtils.isEmpty(getConfig().secureSettingName)) {
                addApprovedList(android.provider.Settings.Secure.getStringForUser(contentResolver, getConfig().secureSettingName, userInfo.id), userInfo.id, true);
            }
            if (!android.text.TextUtils.isEmpty(getConfig().secondarySettingName)) {
                addApprovedList(android.provider.Settings.Secure.getStringForUser(contentResolver, getConfig().secondarySettingName, userInfo.id), userInfo.id, false);
            }
        }
    }

    void readDefaults(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser) {
        java.lang.String readStringAttribute = com.android.internal.util.XmlUtils.readStringAttribute(typedXmlPullParser, ATT_DEFAULTS);
        if (!android.text.TextUtils.isEmpty(readStringAttribute)) {
            java.lang.String[] split = readStringAttribute.split(ENABLED_SERVICES_SEPARATOR);
            synchronized (this.mDefaultsLock) {
                for (int i = 0; i < split.length; i++) {
                    try {
                        if (!android.text.TextUtils.isEmpty(split[i])) {
                            android.content.ComponentName unflattenFromString = android.content.ComponentName.unflattenFromString(split[i]);
                            if (unflattenFromString != null) {
                                this.mDefaultPackages.add(unflattenFromString.getPackageName());
                                this.mDefaultComponents.add(unflattenFromString);
                            } else {
                                this.mDefaultPackages.add(split[i]);
                            }
                        }
                    } finally {
                    }
                }
            }
        }
    }

    public void readXml(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser, com.android.internal.util.function.TriPredicate<java.lang.String, java.lang.Integer, java.lang.String> triPredicate, boolean z, int i) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        boolean z2;
        int attributeInt;
        java.lang.String readStringAttribute = com.android.internal.util.XmlUtils.readStringAttribute(typedXmlPullParser, ATT_VERSION);
        readDefaults(typedXmlPullParser);
        boolean z3 = false;
        while (true) {
            int next = typedXmlPullParser.next();
            z2 = true;
            if (next == 1) {
                break;
            }
            java.lang.String name = typedXmlPullParser.getName();
            if (next == 3 && getConfig().xmlTag.equals(name)) {
                break;
            }
            if (next == 2) {
                if (TAG_MANAGED_SERVICES.equals(name)) {
                    android.util.Slog.i(this.TAG, "Read " + this.mConfig.caption + " permissions from xml");
                    java.lang.String readStringAttribute2 = com.android.internal.util.XmlUtils.readStringAttribute(typedXmlPullParser, ATT_APPROVED_LIST);
                    if (!z) {
                        attributeInt = typedXmlPullParser.getAttributeInt((java.lang.String) null, ATT_USER_ID, 0);
                    } else {
                        attributeInt = i;
                    }
                    boolean attributeBoolean = typedXmlPullParser.getAttributeBoolean((java.lang.String) null, ATT_IS_PRIMARY, true);
                    java.lang.String readStringAttribute3 = com.android.internal.util.XmlUtils.readStringAttribute(typedXmlPullParser, ATT_USER_CHANGED);
                    java.lang.String readStringAttribute4 = com.android.internal.util.XmlUtils.readStringAttribute(typedXmlPullParser, ATT_USER_SET_OLD);
                    java.lang.String readStringAttribute5 = com.android.internal.util.XmlUtils.readStringAttribute(typedXmlPullParser, ATT_USER_SET);
                    if (DB_VERSION.equals(readStringAttribute)) {
                        if (readStringAttribute3 == null) {
                            readStringAttribute5 = android.text.TextUtils.emptyIfNull(readStringAttribute5);
                        } else {
                            synchronized (this.mApproved) {
                                this.mIsUserChanged.put(java.lang.Integer.valueOf(attributeInt), java.lang.Boolean.valueOf(readStringAttribute3));
                            }
                            readStringAttribute5 = java.lang.Boolean.valueOf(readStringAttribute3).booleanValue() ? readStringAttribute2 : "";
                        }
                    } else if (readStringAttribute5 != null) {
                        z3 = true;
                    } else if (readStringAttribute4 != null && java.lang.Boolean.valueOf(readStringAttribute4).booleanValue()) {
                        synchronized (this.mApproved) {
                            this.mIsUserChanged.put(java.lang.Integer.valueOf(attributeInt), true);
                        }
                        z3 = false;
                        readStringAttribute5 = readStringAttribute2;
                    } else {
                        readStringAttribute5 = "";
                        z3 = true;
                    }
                    readExtraAttributes(name, typedXmlPullParser, attributeInt);
                    if (triPredicate == null || triPredicate.test(getPackageName(readStringAttribute2), java.lang.Integer.valueOf(attributeInt), getRequiredPermission()) || readStringAttribute2.isEmpty()) {
                        if (this.mUm.getUserInfo(attributeInt) != null) {
                            addApprovedList(readStringAttribute2, attributeInt, attributeBoolean, readStringAttribute5);
                        }
                        this.mUseXml = true;
                    }
                } else {
                    readExtraTag(name, typedXmlPullParser);
                }
            }
        }
        if (!android.text.TextUtils.isEmpty(readStringAttribute) && !DB_VERSION_1.equals(readStringAttribute) && !DB_VERSION_2.equals(readStringAttribute) && !DB_VERSION_3.equals(readStringAttribute)) {
            z2 = false;
        }
        if (z2) {
            upgradeDefaultsXmlVersion();
        }
        if (z3) {
            upgradeUserSet();
        }
        rebindServices(false, -1);
    }

    void upgradeDefaultsXmlVersion() {
        int size;
        int size2;
        synchronized (this.mDefaultsLock) {
            size = this.mDefaultComponents.size() + this.mDefaultPackages.size();
        }
        if (size == 0) {
            if (this.mApprovalLevel == 1) {
                java.util.List<android.content.ComponentName> allowedComponents = getAllowedComponents(0);
                for (int i = 0; i < allowedComponents.size(); i++) {
                    addDefaultComponentOrPackage(allowedComponents.get(i).flattenToString());
                }
            }
            if (this.mApprovalLevel == 0) {
                java.util.List<java.lang.String> allowedPackages = getAllowedPackages(0);
                for (int i2 = 0; i2 < allowedPackages.size(); i2++) {
                    addDefaultComponentOrPackage(allowedPackages.get(i2));
                }
            }
        }
        synchronized (this.mDefaultsLock) {
            size2 = this.mDefaultComponents.size() + this.mDefaultPackages.size();
        }
        if (size2 == 0) {
            loadDefaultsFromConfig();
        }
    }

    protected void upgradeUserSet() {
    }

    protected void readExtraAttributes(java.lang.String str, com.android.modules.utils.TypedXmlPullParser typedXmlPullParser, int i) throws java.io.IOException {
    }

    protected void addApprovedList(java.lang.String str, int i, boolean z) {
        addApprovedList(str, i, z, str);
    }

    protected void addApprovedList(java.lang.String str, int i, boolean z, java.lang.String str2) {
        if (android.text.TextUtils.isEmpty(str)) {
            str = "";
        }
        if (str2 == null) {
            str2 = str;
        }
        synchronized (this.mApproved) {
            try {
                android.util.ArrayMap<java.lang.Boolean, android.util.ArraySet<java.lang.String>> arrayMap = this.mApproved.get(java.lang.Integer.valueOf(i));
                if (arrayMap == null) {
                    arrayMap = new android.util.ArrayMap<>();
                    this.mApproved.put(java.lang.Integer.valueOf(i), arrayMap);
                }
                android.util.ArraySet<java.lang.String> arraySet = arrayMap.get(java.lang.Boolean.valueOf(z));
                if (arraySet == null) {
                    arraySet = new android.util.ArraySet<>();
                    arrayMap.put(java.lang.Boolean.valueOf(z), arraySet);
                }
                for (java.lang.String str3 : str.split(ENABLED_SERVICES_SEPARATOR)) {
                    java.lang.String approvedValue = getApprovedValue(str3);
                    if (approvedValue != null) {
                        arraySet.add(approvedValue);
                    }
                }
                android.util.ArraySet<java.lang.String> arraySet2 = this.mUserSetServices.get(java.lang.Integer.valueOf(i));
                if (arraySet2 == null) {
                    arraySet2 = new android.util.ArraySet<>();
                    this.mUserSetServices.put(java.lang.Integer.valueOf(i), arraySet2);
                }
                for (java.lang.String str4 : str2.split(ENABLED_SERVICES_SEPARATOR)) {
                    java.lang.String approvedValue2 = getApprovedValue(str4);
                    if (approvedValue2 != null) {
                        arraySet2.add(approvedValue2);
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    protected boolean isComponentEnabledForPackage(java.lang.String str) {
        boolean contains;
        synchronized (this.mMutex) {
            contains = this.mEnabledServicesPackageNames.contains(str);
        }
        return contains;
    }

    protected void setPackageOrComponentEnabled(java.lang.String str, int i, boolean z, boolean z2) {
        setPackageOrComponentEnabled(str, i, z, z2, true);
    }

    protected void setPackageOrComponentEnabled(java.lang.String str, int i, boolean z, boolean z2, boolean z3) {
        java.lang.String str2 = this.TAG;
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append(z2 ? " Allowing " : "Disallowing ");
        sb.append(this.mConfig.caption);
        sb.append(" ");
        sb.append(str);
        sb.append(" (userSet: ");
        sb.append(z3);
        sb.append(")");
        android.util.Slog.i(str2, sb.toString());
        synchronized (this.mApproved) {
            try {
                android.util.ArrayMap<java.lang.Boolean, android.util.ArraySet<java.lang.String>> arrayMap = this.mApproved.get(java.lang.Integer.valueOf(i));
                if (arrayMap == null) {
                    arrayMap = new android.util.ArrayMap<>();
                    this.mApproved.put(java.lang.Integer.valueOf(i), arrayMap);
                }
                android.util.ArraySet<java.lang.String> arraySet = arrayMap.get(java.lang.Boolean.valueOf(z));
                if (arraySet == null) {
                    arraySet = new android.util.ArraySet<>();
                    arrayMap.put(java.lang.Boolean.valueOf(z), arraySet);
                }
                java.lang.String approvedValue = getApprovedValue(str);
                if (approvedValue != null) {
                    if (z2) {
                        arraySet.add(approvedValue);
                    } else {
                        arraySet.remove(approvedValue);
                    }
                }
                android.util.ArraySet<java.lang.String> arraySet2 = this.mUserSetServices.get(java.lang.Integer.valueOf(i));
                if (arraySet2 == null) {
                    arraySet2 = new android.util.ArraySet<>();
                    this.mUserSetServices.put(java.lang.Integer.valueOf(i), arraySet2);
                }
                if (z3) {
                    arraySet2.add(str);
                } else {
                    arraySet2.remove(str);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        rebindServices(false, i);
    }

    private java.lang.String getApprovedValue(java.lang.String str) {
        if (this.mApprovalLevel == 1) {
            if (android.content.ComponentName.unflattenFromString(str) != null) {
                return str;
            }
            return null;
        }
        return getPackageName(str);
    }

    protected java.lang.String getApproved(int i, boolean z) {
        java.lang.String join;
        synchronized (this.mApproved) {
            join = java.lang.String.join(ENABLED_SERVICES_SEPARATOR, this.mApproved.getOrDefault(java.lang.Integer.valueOf(i), new android.util.ArrayMap<>()).getOrDefault(java.lang.Boolean.valueOf(z), new android.util.ArraySet<>()));
        }
        return join;
    }

    protected java.util.List<android.content.ComponentName> getAllowedComponents(int i) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        synchronized (this.mApproved) {
            try {
                android.util.ArrayMap<java.lang.Boolean, android.util.ArraySet<java.lang.String>> orDefault = this.mApproved.getOrDefault(java.lang.Integer.valueOf(i), new android.util.ArrayMap<>());
                for (int i2 = 0; i2 < orDefault.size(); i2++) {
                    android.util.ArraySet<java.lang.String> valueAt = orDefault.valueAt(i2);
                    for (int i3 = 0; i3 < valueAt.size(); i3++) {
                        android.content.ComponentName unflattenFromString = android.content.ComponentName.unflattenFromString(valueAt.valueAt(i3));
                        if (unflattenFromString != null) {
                            arrayList.add(unflattenFromString);
                        }
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return arrayList;
    }

    @android.annotation.NonNull
    protected java.util.List<java.lang.String> getAllowedPackages(int i) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        synchronized (this.mApproved) {
            try {
                android.util.ArrayMap<java.lang.Boolean, android.util.ArraySet<java.lang.String>> orDefault = this.mApproved.getOrDefault(java.lang.Integer.valueOf(i), new android.util.ArrayMap<>());
                for (int i2 = 0; i2 < orDefault.size(); i2++) {
                    android.util.ArraySet<java.lang.String> valueAt = orDefault.valueAt(i2);
                    for (int i3 = 0; i3 < valueAt.size(); i3++) {
                        java.lang.String packageName = getPackageName(valueAt.valueAt(i3));
                        if (!android.text.TextUtils.isEmpty(packageName)) {
                            arrayList.add(packageName);
                        }
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return arrayList;
    }

    protected boolean isPackageOrComponentAllowed(java.lang.String str, int i) {
        synchronized (this.mApproved) {
            try {
                android.util.ArrayMap<java.lang.Boolean, android.util.ArraySet<java.lang.String>> orDefault = this.mApproved.getOrDefault(java.lang.Integer.valueOf(i), new android.util.ArrayMap<>());
                for (int i2 = 0; i2 < orDefault.size(); i2++) {
                    if (orDefault.valueAt(i2).contains(str)) {
                        return true;
                    }
                }
                return false;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    protected boolean isPackageOrComponentAllowedWithPermission(android.content.ComponentName componentName, int i) {
        if (!isPackageOrComponentAllowed(componentName.flattenToString(), i) && !isPackageOrComponentAllowed(componentName.getPackageName(), i)) {
            return false;
        }
        return componentHasBindPermission(componentName, i);
    }

    private boolean componentHasBindPermission(android.content.ComponentName componentName, int i) {
        android.content.pm.ServiceInfo serviceInfo = getServiceInfo(componentName, i);
        if (serviceInfo == null) {
            return false;
        }
        return this.mConfig.bindPermission.equals(serviceInfo.permission);
    }

    boolean isPackageOrComponentUserSet(java.lang.String str, int i) {
        boolean z;
        synchronized (this.mApproved) {
            try {
                android.util.ArraySet<java.lang.String> arraySet = this.mUserSetServices.get(java.lang.Integer.valueOf(i));
                z = arraySet != null && arraySet.contains(str);
            } finally {
            }
        }
        return z;
    }

    protected boolean isPackageAllowed(java.lang.String str, int i) {
        if (str == null) {
            return false;
        }
        synchronized (this.mApproved) {
            try {
                android.util.ArrayMap<java.lang.Boolean, android.util.ArraySet<java.lang.String>> orDefault = this.mApproved.getOrDefault(java.lang.Integer.valueOf(i), new android.util.ArrayMap<>());
                for (int i2 = 0; i2 < orDefault.size(); i2++) {
                    java.util.Iterator<java.lang.String> it = orDefault.valueAt(i2).iterator();
                    while (it.hasNext()) {
                        java.lang.String next = it.next();
                        android.content.ComponentName unflattenFromString = android.content.ComponentName.unflattenFromString(next);
                        if (unflattenFromString != null) {
                            if (str.equals(unflattenFromString.getPackageName())) {
                                return true;
                            }
                        } else if (str.equals(next)) {
                            return true;
                        }
                    }
                }
                return false;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void onPackagesChanged(boolean z, java.lang.String[] strArr, int[] iArr) {
        boolean z2;
        if (this.DEBUG) {
            synchronized (this.mMutex) {
                java.lang.String str = this.TAG;
                java.lang.StringBuilder sb = new java.lang.StringBuilder();
                sb.append("onPackagesChanged removingPackage=");
                sb.append(z);
                sb.append(" pkgList=");
                sb.append(strArr == null ? null : java.util.Arrays.asList(strArr));
                sb.append(" mEnabledServicesPackageNames=");
                sb.append(this.mEnabledServicesPackageNames);
                android.util.Slog.d(str, sb.toString());
            }
        }
        if (strArr != null && strArr.length > 0) {
            if (z && iArr != null) {
                int min = java.lang.Math.min(strArr.length, iArr.length);
                z2 = false;
                for (int i = 0; i < min; i++) {
                    z2 = removeUninstalledItemsFromApprovedLists(android.os.UserHandle.getUserId(iArr[i]), strArr[i]);
                }
            } else {
                z2 = false;
            }
            for (java.lang.String str2 : strArr) {
                if (isComponentEnabledForPackage(str2)) {
                    z2 = true;
                }
                if (iArr != null && iArr.length > 0) {
                    for (int i2 : iArr) {
                        if (isPackageAllowed(str2, android.os.UserHandle.getUserId(i2))) {
                            trimApprovedListsForInvalidServices(str2, android.os.UserHandle.getUserId(i2));
                            z2 = true;
                        }
                    }
                }
            }
            if (z2) {
                rebindServices(false, -1);
            }
        }
    }

    public void onUserRemoved(int i) {
        android.util.Slog.i(this.TAG, "Removing approved services for removed user " + i);
        synchronized (this.mApproved) {
            this.mApproved.remove(java.lang.Integer.valueOf(i));
        }
        synchronized (this.mSnoozing) {
            this.mSnoozing.remove(i);
        }
        unbindUserServices(i);
    }

    public void onUserSwitched(int i) {
        if (this.DEBUG) {
            android.util.Slog.d(this.TAG, "onUserSwitched u=" + i);
        }
        unbindOtherUserServices(i);
        rebindServices(true, i);
    }

    public void onUserUnlocked(int i) {
        if (this.DEBUG) {
            android.util.Slog.d(this.TAG, "onUserUnlocked u=" + i);
        }
        rebindServices(false, i);
    }

    private com.android.server.notification.ManagedServices.ManagedServiceInfo getServiceFromTokenLocked(android.os.IInterface iInterface) {
        if (iInterface == null) {
            return null;
        }
        android.os.IBinder asBinder = iInterface.asBinder();
        synchronized (this.mMutex) {
            try {
                int size = this.mServices.size();
                for (int i = 0; i < size; i++) {
                    com.android.server.notification.ManagedServices.ManagedServiceInfo managedServiceInfo = this.mServices.get(i);
                    if (managedServiceInfo.service.asBinder() == asBinder) {
                        return managedServiceInfo;
                    }
                }
                return null;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    protected boolean isServiceTokenValidLocked(android.os.IInterface iInterface) {
        if (iInterface == null || getServiceFromTokenLocked(iInterface) == null) {
            return false;
        }
        return true;
    }

    protected com.android.server.notification.ManagedServices.ManagedServiceInfo checkServiceTokenLocked(android.os.IInterface iInterface) {
        checkNotNull(iInterface);
        com.android.server.notification.ManagedServices.ManagedServiceInfo serviceFromTokenLocked = getServiceFromTokenLocked(iInterface);
        if (serviceFromTokenLocked != null) {
            return serviceFromTokenLocked;
        }
        throw new java.lang.SecurityException("Disallowed call from unknown " + getCaption() + ": " + iInterface + " " + iInterface.getClass());
    }

    public boolean isSameUser(android.os.IInterface iInterface, int i) {
        checkNotNull(iInterface);
        synchronized (this.mMutex) {
            try {
                com.android.server.notification.ManagedServices.ManagedServiceInfo serviceFromTokenLocked = getServiceFromTokenLocked(iInterface);
                if (serviceFromTokenLocked == null) {
                    return false;
                }
                return serviceFromTokenLocked.isSameUser(i);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void unregisterService(android.os.IInterface iInterface, int i) {
        checkNotNull(iInterface);
        unregisterServiceImpl(iInterface, i);
    }

    public void registerSystemService(android.os.IInterface iInterface, android.content.ComponentName componentName, int i, int i2) {
        checkNotNull(iInterface);
        com.android.server.notification.ManagedServices.ManagedServiceInfo registerServiceImpl = registerServiceImpl(iInterface, componentName, i, 10000, i2);
        if (registerServiceImpl != null) {
            onServiceAdded(registerServiceImpl);
        }
    }

    protected void registerGuestService(com.android.server.notification.ManagedServices.ManagedServiceInfo managedServiceInfo) {
        checkNotNull(managedServiceInfo.service);
        if (!checkType(managedServiceInfo.service)) {
            throw new java.lang.IllegalArgumentException();
        }
        if (registerServiceImpl(managedServiceInfo) != null) {
            onServiceAdded(managedServiceInfo);
        }
    }

    protected void setComponentState(android.content.ComponentName componentName, int i, boolean z) {
        synchronized (this.mSnoozing) {
            try {
                if ((!this.mSnoozing.contains(i, componentName)) == z) {
                    return;
                }
                if (z) {
                    this.mSnoozing.remove(i, componentName);
                } else {
                    this.mSnoozing.add(i, componentName);
                }
                java.lang.String str = this.TAG;
                java.lang.StringBuilder sb = new java.lang.StringBuilder();
                sb.append(z ? "Enabling " : "Disabling ");
                sb.append("component ");
                sb.append(componentName.flattenToShortString());
                android.util.Slog.d(str, sb.toString());
                synchronized (this.mMutex) {
                    try {
                        if (z) {
                            if (isPackageOrComponentAllowedWithPermission(componentName, i)) {
                                registerServiceLocked(componentName, i);
                            } else {
                                android.util.Slog.d(this.TAG, componentName + " no longer has permission to be bound");
                            }
                        } else {
                            unregisterServiceLocked(componentName, i);
                        }
                    } finally {
                    }
                }
            } finally {
            }
        }
    }

    @android.annotation.NonNull
    private android.util.ArraySet<android.content.ComponentName> loadComponentNamesFromValues(android.util.ArraySet<java.lang.String> arraySet, int i) {
        if (arraySet == null || arraySet.size() == 0) {
            return new android.util.ArraySet<>();
        }
        android.util.ArraySet<android.content.ComponentName> arraySet2 = new android.util.ArraySet<>(arraySet.size());
        for (int i2 = 0; i2 < arraySet.size(); i2++) {
            java.lang.String valueAt = arraySet.valueAt(i2);
            if (!android.text.TextUtils.isEmpty(valueAt)) {
                android.content.ComponentName unflattenFromString = android.content.ComponentName.unflattenFromString(valueAt);
                if (unflattenFromString != null) {
                    arraySet2.add(unflattenFromString);
                } else {
                    arraySet2.addAll(queryPackageForServices(valueAt, i));
                }
            }
        }
        return arraySet2;
    }

    protected java.util.Set<android.content.ComponentName> queryPackageForServices(java.lang.String str, int i) {
        return queryPackageForServices(str, 0, i);
    }

    protected android.util.ArraySet<android.content.ComponentName> queryPackageForServices(java.lang.String str, int i, int i2) {
        android.util.ArraySet<android.content.ComponentName> arraySet = new android.util.ArraySet<>();
        android.content.pm.PackageManager packageManager = this.mContext.getPackageManager();
        android.content.Intent intent = new android.content.Intent(this.mConfig.serviceInterface);
        if (!android.text.TextUtils.isEmpty(str)) {
            intent.setPackage(str);
        }
        java.util.List queryIntentServicesAsUser = packageManager.queryIntentServicesAsUser(intent, i | 132, i2);
        if (this.DEBUG) {
            android.util.Slog.v(this.TAG, this.mConfig.serviceInterface + " services: " + queryIntentServicesAsUser);
        }
        if (queryIntentServicesAsUser != null) {
            int size = queryIntentServicesAsUser.size();
            for (int i3 = 0; i3 < size; i3++) {
                android.content.pm.ServiceInfo serviceInfo = ((android.content.pm.ResolveInfo) queryIntentServicesAsUser.get(i3)).serviceInfo;
                android.content.ComponentName componentName = new android.content.ComponentName(serviceInfo.packageName, serviceInfo.name);
                if (!this.mConfig.bindPermission.equals(serviceInfo.permission)) {
                    android.util.Slog.w(this.TAG, "Skipping " + getCaption() + " service " + serviceInfo.packageName + com.android.server.slice.SliceClientPermissions.SliceAuthority.DELIMITER + serviceInfo.name + ": it does not require the permission " + this.mConfig.bindPermission);
                } else {
                    arraySet.add(componentName);
                }
            }
        }
        return arraySet;
    }

    private void trimApprovedListsAccordingToInstalledServices(int i) {
        synchronized (this.mApproved) {
            try {
                android.util.ArrayMap<java.lang.Boolean, android.util.ArraySet<java.lang.String>> arrayMap = this.mApproved.get(java.lang.Integer.valueOf(i));
                if (arrayMap == null) {
                    return;
                }
                for (int i2 = 0; i2 < arrayMap.size(); i2++) {
                    android.util.ArraySet<java.lang.String> valueAt = arrayMap.valueAt(i2);
                    for (int size = valueAt.size() - 1; size >= 0; size--) {
                        java.lang.String valueAt2 = valueAt.valueAt(size);
                        if (!isValidEntry(valueAt2, i)) {
                            valueAt.removeAt(size);
                            android.util.Slog.v(this.TAG, "Removing " + valueAt2 + " from approved list; no matching services found");
                        } else if (this.DEBUG) {
                            android.util.Slog.v(this.TAG, "Keeping " + valueAt2 + " on approved list; matching services found");
                        }
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private boolean removeUninstalledItemsFromApprovedLists(int i, java.lang.String str) {
        synchronized (this.mApproved) {
            try {
                android.util.ArrayMap<java.lang.Boolean, android.util.ArraySet<java.lang.String>> arrayMap = this.mApproved.get(java.lang.Integer.valueOf(i));
                if (arrayMap != null) {
                    int size = arrayMap.size();
                    for (int i2 = 0; i2 < size; i2++) {
                        android.util.ArraySet<java.lang.String> valueAt = arrayMap.valueAt(i2);
                        for (int size2 = valueAt.size() - 1; size2 >= 0; size2--) {
                            java.lang.String valueAt2 = valueAt.valueAt(size2);
                            if (android.text.TextUtils.equals(str, getPackageName(valueAt2))) {
                                valueAt.removeAt(size2);
                                if (this.DEBUG) {
                                    android.util.Slog.v(this.TAG, "Removing " + valueAt2 + " from approved list; uninstalled");
                                }
                            }
                        }
                    }
                }
                android.util.ArraySet<java.lang.String> arraySet = this.mUserSetServices.get(java.lang.Integer.valueOf(i));
                if (arraySet != null) {
                    for (int size3 = arraySet.size() - 1; size3 >= 0; size3--) {
                        java.lang.String valueAt3 = arraySet.valueAt(size3);
                        if (android.text.TextUtils.equals(str, getPackageName(valueAt3))) {
                            arraySet.removeAt(size3);
                            if (this.DEBUG) {
                                android.util.Slog.v(this.TAG, "Removing " + valueAt3 + " from user-set list; uninstalled");
                            }
                        }
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return false;
    }

    private void trimApprovedListsForInvalidServices(java.lang.String str, int i) {
        android.content.ComponentName unflattenFromString;
        synchronized (this.mApproved) {
            try {
                android.util.ArrayMap<java.lang.Boolean, android.util.ArraySet<java.lang.String>> arrayMap = this.mApproved.get(java.lang.Integer.valueOf(i));
                if (arrayMap == null) {
                    return;
                }
                for (int i2 = 0; i2 < arrayMap.size(); i2++) {
                    android.util.ArraySet<java.lang.String> valueAt = arrayMap.valueAt(i2);
                    for (int size = valueAt.size() - 1; size >= 0; size--) {
                        java.lang.String valueAt2 = valueAt.valueAt(size);
                        if (android.text.TextUtils.equals(getPackageName(valueAt2), str) && (unflattenFromString = android.content.ComponentName.unflattenFromString(valueAt2)) != null && !componentHasBindPermission(unflattenFromString, i)) {
                            valueAt.removeAt(size);
                            if (this.DEBUG) {
                                android.util.Slog.v(this.TAG, "Removing " + valueAt2 + " from approved list; no bind permission found " + this.mConfig.bindPermission);
                            }
                        }
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    protected java.lang.String getPackageName(java.lang.String str) {
        android.content.ComponentName unflattenFromString = android.content.ComponentName.unflattenFromString(str);
        if (unflattenFromString != null) {
            return unflattenFromString.getPackageName();
        }
        return str;
    }

    protected boolean isValidEntry(java.lang.String str, int i) {
        return hasMatchingServices(str, i);
    }

    private boolean hasMatchingServices(java.lang.String str, int i) {
        return !android.text.TextUtils.isEmpty(str) && queryPackageForServices(getPackageName(str), i).size() > 0;
    }

    @com.android.internal.annotations.VisibleForTesting
    protected android.util.SparseArray<android.util.ArraySet<android.content.ComponentName>> getAllowedComponents(android.util.IntArray intArray) {
        int size = intArray.size();
        android.util.SparseArray<android.util.ArraySet<android.content.ComponentName>> sparseArray = new android.util.SparseArray<>();
        for (int i = 0; i < size; i++) {
            int i2 = intArray.get(i);
            synchronized (this.mApproved) {
                try {
                    android.util.ArrayMap<java.lang.Boolean, android.util.ArraySet<java.lang.String>> arrayMap = this.mApproved.get(java.lang.Integer.valueOf(i2));
                    if (arrayMap != null) {
                        int size2 = arrayMap.size();
                        for (int i3 = 0; i3 < size2; i3++) {
                            android.util.ArraySet<android.content.ComponentName> arraySet = sparseArray.get(i2);
                            if (arraySet == null) {
                                arraySet = new android.util.ArraySet<>();
                                sparseArray.put(i2, arraySet);
                            }
                            arraySet.addAll((android.util.ArraySet<? extends android.content.ComponentName>) loadComponentNamesFromValues(arrayMap.valueAt(i3), i2));
                        }
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
        return sparseArray;
    }

    @com.android.internal.annotations.GuardedBy({"mMutex"})
    protected void populateComponentsToBind(android.util.SparseArray<java.util.Set<android.content.ComponentName>> sparseArray, android.util.IntArray intArray, android.util.SparseArray<android.util.ArraySet<android.content.ComponentName>> sparseArray2) {
        this.mEnabledServicesForCurrentProfiles.clear();
        this.mEnabledServicesPackageNames.clear();
        int size = intArray.size();
        for (int i = 0; i < size; i++) {
            int i2 = intArray.get(i);
            android.util.ArraySet<android.content.ComponentName> arraySet = sparseArray2.get(i2);
            if (arraySet == null) {
                sparseArray.put(i2, new android.util.ArraySet());
            } else {
                java.util.HashSet hashSet = new java.util.HashSet(arraySet);
                synchronized (this.mSnoozing) {
                    try {
                        android.util.ArraySet arraySet2 = this.mSnoozing.get(i2);
                        if (arraySet2 != null) {
                            hashSet.removeAll(arraySet2);
                        }
                    } catch (java.lang.Throwable th) {
                        throw th;
                    }
                }
                sparseArray.put(i2, hashSet);
                this.mEnabledServicesForCurrentProfiles.addAll((android.util.ArraySet<? extends android.content.ComponentName>) arraySet);
                for (int i3 = 0; i3 < arraySet.size(); i3++) {
                    this.mEnabledServicesPackageNames.add(arraySet.valueAt(i3).getPackageName());
                }
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"mMutex"})
    protected java.util.Set<com.android.server.notification.ManagedServices.ManagedServiceInfo> getRemovableConnectedServices() {
        android.util.ArraySet arraySet = new android.util.ArraySet();
        java.util.Iterator<com.android.server.notification.ManagedServices.ManagedServiceInfo> it = this.mServices.iterator();
        while (it.hasNext()) {
            com.android.server.notification.ManagedServices.ManagedServiceInfo next = it.next();
            if (!next.isSystem && !next.isGuest(this)) {
                arraySet.add(next);
            }
        }
        return arraySet;
    }

    protected void populateComponentsToUnbind(boolean z, java.util.Set<com.android.server.notification.ManagedServices.ManagedServiceInfo> set, android.util.SparseArray<java.util.Set<android.content.ComponentName>> sparseArray, android.util.SparseArray<java.util.Set<android.content.ComponentName>> sparseArray2) {
        for (com.android.server.notification.ManagedServices.ManagedServiceInfo managedServiceInfo : set) {
            java.util.Set<android.content.ComponentName> set2 = sparseArray.get(managedServiceInfo.userid);
            if (set2 != null && (z || !set2.contains(managedServiceInfo.component))) {
                java.util.Set<android.content.ComponentName> set3 = sparseArray2.get(managedServiceInfo.userid, new android.util.ArraySet());
                set3.add(managedServiceInfo.component);
                sparseArray2.put(managedServiceInfo.userid, set3);
            }
        }
    }

    protected void rebindServices(boolean z, int i) {
        if (this.DEBUG) {
            android.util.Slog.d(this.TAG, "rebindServices " + z + " " + i);
        }
        android.util.IntArray currentProfileIds = this.mUserProfiles.getCurrentProfileIds();
        boolean z2 = this.mUserProfiles.isProfileUser(i) && allowRebindForParentUser();
        if (i != -1 && !z2) {
            currentProfileIds = new android.util.IntArray(1);
            currentProfileIds.add(i);
        }
        android.util.SparseArray<java.util.Set<android.content.ComponentName>> sparseArray = new android.util.SparseArray<>();
        android.util.SparseArray<java.util.Set<android.content.ComponentName>> sparseArray2 = new android.util.SparseArray<>();
        synchronized (this.mMutex) {
            android.util.SparseArray<android.util.ArraySet<android.content.ComponentName>> allowedComponents = getAllowedComponents(currentProfileIds);
            java.util.Set<com.android.server.notification.ManagedServices.ManagedServiceInfo> removableConnectedServices = getRemovableConnectedServices();
            populateComponentsToBind(sparseArray, currentProfileIds, allowedComponents);
            populateComponentsToUnbind(z, removableConnectedServices, sparseArray, sparseArray2);
        }
        unbindFromServices(sparseArray2);
        bindToServices(sparseArray);
    }

    @com.android.internal.annotations.VisibleForTesting
    void unbindOtherUserServices(int i) {
        com.android.server.utils.TimingsTraceAndSlog timingsTraceAndSlog = new com.android.server.utils.TimingsTraceAndSlog();
        timingsTraceAndSlog.traceBegin("ManagedServices.unbindOtherUserServices_current" + i);
        unbindServicesImpl(i, true);
        timingsTraceAndSlog.traceEnd();
    }

    void unbindUserServices(int i) {
        com.android.server.utils.TimingsTraceAndSlog timingsTraceAndSlog = new com.android.server.utils.TimingsTraceAndSlog();
        timingsTraceAndSlog.traceBegin("ManagedServices.unbindUserServices" + i);
        unbindServicesImpl(i, false);
        timingsTraceAndSlog.traceEnd();
    }

    void unbindServicesImpl(int i, boolean z) {
        android.util.SparseArray<java.util.Set<android.content.ComponentName>> sparseArray = new android.util.SparseArray<>();
        synchronized (this.mMutex) {
            try {
                for (com.android.server.notification.ManagedServices.ManagedServiceInfo managedServiceInfo : getRemovableConnectedServices()) {
                    if ((z && managedServiceInfo.userid != i) || (!z && managedServiceInfo.userid == i)) {
                        java.util.Set<android.content.ComponentName> set = sparseArray.get(managedServiceInfo.userid, new android.util.ArraySet());
                        set.add(managedServiceInfo.component);
                        sparseArray.put(managedServiceInfo.userid, set);
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        unbindFromServices(sparseArray);
    }

    protected void unbindFromServices(android.util.SparseArray<java.util.Set<android.content.ComponentName>> sparseArray) {
        for (int i = 0; i < sparseArray.size(); i++) {
            int keyAt = sparseArray.keyAt(i);
            for (android.content.ComponentName componentName : sparseArray.get(keyAt)) {
                android.util.Slog.v(this.TAG, "disabling " + getCaption() + " for user " + keyAt + ": " + componentName);
                unregisterService(componentName, keyAt);
            }
        }
    }

    private void bindToServices(android.util.SparseArray<java.util.Set<android.content.ComponentName>> sparseArray) {
        for (int i = 0; i < sparseArray.size(); i++) {
            int keyAt = sparseArray.keyAt(i);
            for (android.content.ComponentName componentName : sparseArray.get(keyAt)) {
                android.content.pm.ServiceInfo serviceInfo = getServiceInfo(componentName, keyAt);
                if (serviceInfo == null) {
                    android.util.Slog.w(this.TAG, "Not binding " + getCaption() + " service " + componentName + ": service not found");
                } else if (!this.mConfig.bindPermission.equals(serviceInfo.permission)) {
                    android.util.Slog.w(this.TAG, "Not binding " + getCaption() + " service " + componentName + ": it does not require the permission " + this.mConfig.bindPermission);
                } else if (!isAutobindAllowed(serviceInfo) && !isBoundOrRebinding(componentName, keyAt)) {
                    synchronized (this.mSnoozing) {
                        android.util.Slog.d(this.TAG, "Not binding " + getCaption() + " service " + componentName + ": has META_DATA_DEFAULT_AUTOBIND = false");
                        this.mSnoozing.add(keyAt, componentName);
                    }
                } else {
                    android.util.Slog.v(this.TAG, "enabling " + getCaption() + " for " + keyAt + ": " + componentName);
                    registerService(serviceInfo, keyAt);
                }
            }
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    void registerService(android.content.pm.ServiceInfo serviceInfo, int i) {
        ensureFilters(serviceInfo, i);
        registerService(serviceInfo.getComponentName(), i);
    }

    @com.android.internal.annotations.VisibleForTesting
    void registerService(android.content.ComponentName componentName, int i) {
        synchronized (this.mMutex) {
            registerServiceLocked(componentName, i);
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    void reregisterService(android.content.ComponentName componentName, int i) {
        if (isPackageOrComponentAllowedWithPermission(componentName, i)) {
            registerService(componentName, i);
        }
    }

    public void registerSystemService(android.content.ComponentName componentName, int i) {
        synchronized (this.mMutex) {
            registerServiceLocked(componentName, i, true);
        }
    }

    @com.android.internal.annotations.GuardedBy({"mMutex"})
    private void registerServiceLocked(android.content.ComponentName componentName, int i) {
        registerServiceLocked(componentName, i, false);
    }

    @com.android.internal.annotations.GuardedBy({"mMutex"})
    private void registerServiceLocked(android.content.ComponentName componentName, int i, boolean z) {
        android.content.pm.ApplicationInfo applicationInfo;
        if (this.DEBUG) {
            android.util.Slog.v(this.TAG, "registerService: " + componentName + " u=" + i);
        }
        android.util.Pair<android.content.ComponentName, java.lang.Integer> create = android.util.Pair.create(componentName, java.lang.Integer.valueOf(i));
        if (this.mServicesBound.contains(create)) {
            android.util.Slog.v(this.TAG, "Not registering " + componentName + " is already bound");
            return;
        }
        this.mServicesBound.add(create);
        for (int size = this.mServices.size() - 1; size >= 0; size--) {
            com.android.server.notification.ManagedServices.ManagedServiceInfo managedServiceInfo = this.mServices.get(size);
            if (componentName.equals(managedServiceInfo.component) && managedServiceInfo.userid == i) {
                android.util.Slog.v(this.TAG, "    disconnecting old " + getCaption() + ": " + managedServiceInfo.service);
                removeServiceLocked(size);
                if (managedServiceInfo.connection != null) {
                    unbindService(managedServiceInfo.connection, managedServiceInfo.component, managedServiceInfo.userid);
                }
            }
        }
        android.content.Intent intent = new android.content.Intent(this.mConfig.serviceInterface);
        intent.setComponent(componentName);
        intent.putExtra("android.intent.extra.client_label", this.mConfig.clientLabel);
        android.app.ActivityOptions makeBasic = android.app.ActivityOptions.makeBasic();
        makeBasic.setIgnorePendingIntentCreatorForegroundState(true);
        intent.putExtra("android.intent.extra.client_intent", android.app.PendingIntent.getActivity(this.mContext, 0, new android.content.Intent(this.mConfig.settingsAction), 67108864, makeBasic.toBundle()));
        try {
            applicationInfo = this.mContext.getPackageManager().getApplicationInfo(componentName.getPackageName(), 0);
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            applicationInfo = null;
        }
        int i2 = applicationInfo != null ? applicationInfo.targetSdkVersion : 1;
        int i3 = applicationInfo != null ? applicationInfo.uid : -1;
        try {
            android.util.Slog.v(this.TAG, "binding: " + intent);
            if (!this.mContext.bindServiceAsUser(intent, new com.android.server.notification.ManagedServices.AnonymousClass1(i, create, z, i2, i3), getBindFlags(), new android.os.UserHandle(i))) {
                this.mServicesBound.remove(create);
                android.util.Slog.w(this.TAG, "Unable to bind " + getCaption() + " service: " + intent + " in user " + i);
            }
        } catch (java.lang.SecurityException e2) {
            this.mServicesBound.remove(create);
            android.util.Slog.e(this.TAG, "Unable to bind " + getCaption() + " service: " + intent, e2);
        }
    }

    /* renamed from: com.android.server.notification.ManagedServices$1, reason: invalid class name */
    class AnonymousClass1 implements android.content.ServiceConnection {
        android.os.IInterface mService;
        final /* synthetic */ boolean val$isSystem;
        final /* synthetic */ android.util.Pair val$servicesBindingTag;
        final /* synthetic */ int val$targetSdkVersion;
        final /* synthetic */ int val$uid;
        final /* synthetic */ int val$userid;

        AnonymousClass1(int i, android.util.Pair pair, boolean z, int i2, int i3) {
            this.val$userid = i;
            this.val$servicesBindingTag = pair;
            this.val$isSystem = z;
            this.val$targetSdkVersion = i2;
            this.val$uid = i3;
        }

        @Override // android.content.ServiceConnection
        public void onServiceConnected(android.content.ComponentName componentName, android.os.IBinder iBinder) {
            boolean z;
            com.android.server.notification.ManagedServices.ManagedServiceInfo managedServiceInfo;
            android.util.Slog.v(com.android.server.notification.ManagedServices.this.TAG, this.val$userid + " " + com.android.server.notification.ManagedServices.this.getCaption() + " service connected: " + componentName);
            synchronized (com.android.server.notification.ManagedServices.this.mMutex) {
                com.android.server.notification.ManagedServices.this.mServicesRebinding.remove(this.val$servicesBindingTag);
                z = false;
                managedServiceInfo = null;
                try {
                    this.mService = com.android.server.notification.ManagedServices.this.asInterface(iBinder);
                    managedServiceInfo = com.android.server.notification.ManagedServices.this.newServiceInfo(this.mService, componentName, this.val$userid, this.val$isSystem, this, this.val$targetSdkVersion, this.val$uid);
                    iBinder.linkToDeath(managedServiceInfo, 0);
                    z = com.android.server.notification.ManagedServices.this.mServices.add(managedServiceInfo);
                } catch (android.os.RemoteException e) {
                    android.util.Slog.e(com.android.server.notification.ManagedServices.this.TAG, "Failed to linkToDeath, already dead", e);
                }
            }
            if (z) {
                com.android.server.notification.ManagedServices.this.onServiceAdded(managedServiceInfo);
            }
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(android.content.ComponentName componentName) {
            android.util.Slog.v(com.android.server.notification.ManagedServices.this.TAG, this.val$userid + " " + com.android.server.notification.ManagedServices.this.getCaption() + " connection lost: " + componentName);
        }

        @Override // android.content.ServiceConnection
        public void onBindingDied(final android.content.ComponentName componentName) {
            android.util.Slog.w(com.android.server.notification.ManagedServices.this.TAG, this.val$userid + " " + com.android.server.notification.ManagedServices.this.getCaption() + " binding died: " + componentName);
            synchronized (com.android.server.notification.ManagedServices.this.mMutex) {
                try {
                    com.android.server.notification.ManagedServices.this.unbindService(this, componentName, this.val$userid);
                    if (!com.android.server.notification.ManagedServices.this.mServicesRebinding.contains(this.val$servicesBindingTag)) {
                        com.android.server.notification.ManagedServices.this.mServicesRebinding.add(this.val$servicesBindingTag);
                        android.os.Handler handler = com.android.server.notification.ManagedServices.this.mHandler;
                        final int i = this.val$userid;
                        handler.postDelayed(new java.lang.Runnable() { // from class: com.android.server.notification.ManagedServices$1$$ExternalSyntheticLambda0
                            @Override // java.lang.Runnable
                            public final void run() {
                                com.android.server.notification.ManagedServices.AnonymousClass1.this.lambda$onBindingDied$0(componentName, i);
                            }
                        }, com.android.server.job.controllers.JobStatus.DEFAULT_TRIGGER_UPDATE_DELAY);
                    } else {
                        android.util.Slog.v(com.android.server.notification.ManagedServices.this.TAG, com.android.server.notification.ManagedServices.this.getCaption() + " not rebinding in user " + this.val$userid + " as a previous rebind attempt was made: " + componentName);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onBindingDied$0(android.content.ComponentName componentName, int i) {
            com.android.server.notification.ManagedServices.this.reregisterService(componentName, i);
        }

        @Override // android.content.ServiceConnection
        public void onNullBinding(android.content.ComponentName componentName) {
            android.util.Slog.v(com.android.server.notification.ManagedServices.this.TAG, "onNullBinding() called with: name = [" + componentName + "]");
            com.android.server.notification.ManagedServices.this.mContext.unbindService(this);
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    boolean isBound(android.content.ComponentName componentName, int i) {
        boolean contains;
        android.util.Pair create = android.util.Pair.create(componentName, java.lang.Integer.valueOf(i));
        synchronized (this.mMutex) {
            contains = this.mServicesBound.contains(create);
        }
        return contains;
    }

    protected boolean isBoundOrRebinding(android.content.ComponentName componentName, int i) {
        boolean z;
        synchronized (this.mMutex) {
            try {
                z = isBound(componentName, i) || this.mServicesRebinding.contains(android.util.Pair.create(componentName, java.lang.Integer.valueOf(i)));
            } finally {
            }
        }
        return z;
    }

    private void unregisterService(android.content.ComponentName componentName, int i) {
        synchronized (this.mMutex) {
            unregisterServiceLocked(componentName, i);
        }
    }

    @com.android.internal.annotations.GuardedBy({"mMutex"})
    private void unregisterServiceLocked(android.content.ComponentName componentName, int i) {
        for (int size = this.mServices.size() - 1; size >= 0; size--) {
            com.android.server.notification.ManagedServices.ManagedServiceInfo managedServiceInfo = this.mServices.get(size);
            if (componentName.equals(managedServiceInfo.component) && managedServiceInfo.userid == i) {
                removeServiceLocked(size);
                if (managedServiceInfo.connection != null) {
                    unbindService(managedServiceInfo.connection, managedServiceInfo.component, managedServiceInfo.userid);
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public com.android.server.notification.ManagedServices.ManagedServiceInfo removeServiceImpl(android.os.IInterface iInterface, int i) {
        com.android.server.notification.ManagedServices.ManagedServiceInfo managedServiceInfo;
        if (this.DEBUG) {
            android.util.Slog.d(this.TAG, "removeServiceImpl service=" + iInterface + " u=" + i);
        }
        synchronized (this.mMutex) {
            try {
                managedServiceInfo = null;
                for (int size = this.mServices.size() - 1; size >= 0; size--) {
                    com.android.server.notification.ManagedServices.ManagedServiceInfo managedServiceInfo2 = this.mServices.get(size);
                    if (managedServiceInfo2.service.asBinder() == iInterface.asBinder() && managedServiceInfo2.userid == i) {
                        android.util.Slog.d(this.TAG, "Removing active service " + managedServiceInfo2.component);
                        managedServiceInfo = removeServiceLocked(size);
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return managedServiceInfo;
    }

    @com.android.internal.annotations.GuardedBy({"mMutex"})
    private com.android.server.notification.ManagedServices.ManagedServiceInfo removeServiceLocked(int i) {
        com.android.server.notification.ManagedServices.ManagedServiceInfo remove = this.mServices.remove(i);
        onServiceRemovedLocked(remove);
        return remove;
    }

    private void checkNotNull(android.os.IInterface iInterface) {
        if (iInterface == null) {
            throw new java.lang.IllegalArgumentException(getCaption() + " must not be null");
        }
    }

    private com.android.server.notification.ManagedServices.ManagedServiceInfo registerServiceImpl(android.os.IInterface iInterface, android.content.ComponentName componentName, int i, int i2, int i3) {
        return registerServiceImpl(newServiceInfo(iInterface, componentName, i, true, null, i2, i3));
    }

    private com.android.server.notification.ManagedServices.ManagedServiceInfo registerServiceImpl(com.android.server.notification.ManagedServices.ManagedServiceInfo managedServiceInfo) {
        synchronized (this.mMutex) {
            try {
                try {
                    managedServiceInfo.service.asBinder().linkToDeath(managedServiceInfo, 0);
                    this.mServices.add(managedServiceInfo);
                } catch (android.os.RemoteException e) {
                    return null;
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return managedServiceInfo;
    }

    private void unregisterServiceImpl(android.os.IInterface iInterface, int i) {
        com.android.server.notification.ManagedServices.ManagedServiceInfo removeServiceImpl = removeServiceImpl(iInterface, i);
        if (removeServiceImpl != null && removeServiceImpl.connection != null && !removeServiceImpl.isGuest(this)) {
            unbindService(removeServiceImpl.connection, removeServiceImpl.component, removeServiceImpl.userid);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void unbindService(android.content.ServiceConnection serviceConnection, android.content.ComponentName componentName, int i) {
        try {
            this.mContext.unbindService(serviceConnection);
        } catch (java.lang.IllegalArgumentException e) {
            android.util.Slog.e(this.TAG, getCaption() + " " + componentName + " could not be unbound", e);
        }
        synchronized (this.mMutex) {
            this.mServicesBound.remove(android.util.Pair.create(componentName, java.lang.Integer.valueOf(i)));
        }
    }

    private android.content.pm.ServiceInfo getServiceInfo(android.content.ComponentName componentName, int i) {
        try {
            return this.mPm.getServiceInfo(componentName, 786560L, i);
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
            return null;
        }
    }

    private boolean isAutobindAllowed(android.content.pm.ServiceInfo serviceInfo) {
        if (serviceInfo == null || serviceInfo.metaData == null || !serviceInfo.metaData.containsKey("android.service.notification.default_autobind_listenerservice")) {
            return true;
        }
        return serviceInfo.metaData.getBoolean("android.service.notification.default_autobind_listenerservice", true);
    }

    public class ManagedServiceInfo implements android.os.IBinder.DeathRecipient {
        public android.content.ComponentName component;
        public android.content.ServiceConnection connection;
        public boolean isSystem;
        public boolean isSystemUi;
        public android.util.Pair<android.content.ComponentName, java.lang.Integer> mKey;
        public android.os.IInterface service;
        public int targetSdkVersion;
        public int uid;
        public int userid;

        public ManagedServiceInfo(android.os.IInterface iInterface, android.content.ComponentName componentName, int i, boolean z, android.content.ServiceConnection serviceConnection, int i2, int i3) {
            this.service = iInterface;
            this.component = componentName;
            this.userid = i;
            this.isSystem = z;
            this.connection = serviceConnection;
            this.targetSdkVersion = i2;
            this.uid = i3;
            this.mKey = android.util.Pair.create(componentName, java.lang.Integer.valueOf(i));
        }

        public boolean isGuest(com.android.server.notification.ManagedServices managedServices) {
            return com.android.server.notification.ManagedServices.this != managedServices;
        }

        public com.android.server.notification.ManagedServices getOwner() {
            return com.android.server.notification.ManagedServices.this;
        }

        public android.os.IInterface getService() {
            return this.service;
        }

        public boolean isSystem() {
            return this.isSystem;
        }

        public boolean isSystemUi() {
            return this.isSystemUi;
        }

        public java.lang.String toString() {
            java.lang.StringBuilder sb = new java.lang.StringBuilder("ManagedServiceInfo[");
            sb.append("component=");
            sb.append(this.component);
            sb.append(",userid=");
            sb.append(this.userid);
            sb.append(",isSystem=");
            sb.append(this.isSystem);
            sb.append(",targetSdkVersion=");
            sb.append(this.targetSdkVersion);
            sb.append(",connection=");
            sb.append(this.connection == null ? null : "<connection>");
            sb.append(",service=");
            sb.append(this.service);
            sb.append(']');
            return sb.toString();
        }

        public void dumpDebug(android.util.proto.ProtoOutputStream protoOutputStream, long j, com.android.server.notification.ManagedServices managedServices) {
            long start = protoOutputStream.start(j);
            this.component.dumpDebug(protoOutputStream, 1146756268033L);
            protoOutputStream.write(1120986464258L, this.userid);
            protoOutputStream.write(1138166333443L, this.service.getClass().getName());
            protoOutputStream.write(1133871366148L, this.isSystem);
            protoOutputStream.write(1133871366149L, isGuest(managedServices));
            protoOutputStream.end(start);
        }

        public boolean isSameUser(int i) {
            if (isEnabledForCurrentProfiles()) {
                return i == -1 || i == this.userid;
            }
            return false;
        }

        public boolean enabledAndUserMatches(int i) {
            if (!isEnabledForCurrentProfiles()) {
                return false;
            }
            if (this.userid == -1 || this.isSystem || i == -1 || i == this.userid) {
                return true;
            }
            return supportsProfiles() && com.android.server.notification.ManagedServices.this.mUserProfiles.isCurrentProfile(i) && isPermittedForProfile(i);
        }

        public boolean supportsProfiles() {
            return this.targetSdkVersion >= 21;
        }

        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
            if (com.android.server.notification.ManagedServices.this.DEBUG) {
                android.util.Slog.d(com.android.server.notification.ManagedServices.this.TAG, "binderDied");
            }
            com.android.server.notification.ManagedServices.this.removeServiceImpl(this.service, this.userid);
        }

        public boolean isEnabledForCurrentProfiles() {
            boolean contains;
            if (this.isSystem) {
                return true;
            }
            if (this.connection == null) {
                return false;
            }
            synchronized (com.android.server.notification.ManagedServices.this.mMutex) {
                contains = com.android.server.notification.ManagedServices.this.mEnabledServicesForCurrentProfiles.contains(this.component);
            }
            return contains;
        }

        public boolean isPermittedForProfile(int i) {
            if (!com.android.server.notification.ManagedServices.this.mUserProfiles.isProfileUser(i)) {
                return true;
            }
            android.app.admin.DevicePolicyManager devicePolicyManager = (android.app.admin.DevicePolicyManager) com.android.server.notification.ManagedServices.this.mContext.getSystemService("device_policy");
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                return devicePolicyManager.isNotificationListenerServicePermitted(this.component.getPackageName(), i);
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public boolean equals(java.lang.Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            com.android.server.notification.ManagedServices.ManagedServiceInfo managedServiceInfo = (com.android.server.notification.ManagedServices.ManagedServiceInfo) obj;
            if (this.userid == managedServiceInfo.userid && this.isSystem == managedServiceInfo.isSystem && this.targetSdkVersion == managedServiceInfo.targetSdkVersion && java.util.Objects.equals(this.service, managedServiceInfo.service) && java.util.Objects.equals(this.component, managedServiceInfo.component) && java.util.Objects.equals(this.connection, managedServiceInfo.connection)) {
                return true;
            }
            return false;
        }

        public int hashCode() {
            return java.util.Objects.hash(this.service, this.component, java.lang.Integer.valueOf(this.userid), java.lang.Boolean.valueOf(this.isSystem), this.connection, java.lang.Integer.valueOf(this.targetSdkVersion));
        }
    }

    public boolean isComponentEnabledForCurrentProfiles(android.content.ComponentName componentName) {
        boolean contains;
        synchronized (this.mMutex) {
            contains = this.mEnabledServicesForCurrentProfiles.contains(componentName);
        }
        return contains;
    }

    public static class UserProfiles {
        private final android.util.SparseArray<android.content.pm.UserInfo> mCurrentProfiles = new android.util.SparseArray<>();

        public void updateCache(@android.annotation.NonNull android.content.Context context) {
            android.os.UserManager userManager = (android.os.UserManager) context.getSystemService(com.android.server.notification.ManagedServices.ATT_USER_ID);
            if (userManager != null) {
                java.util.List<android.content.pm.UserInfo> profiles = userManager.getProfiles(android.app.ActivityManager.getCurrentUser());
                synchronized (this.mCurrentProfiles) {
                    try {
                        this.mCurrentProfiles.clear();
                        for (android.content.pm.UserInfo userInfo : profiles) {
                            this.mCurrentProfiles.put(userInfo.id, userInfo);
                        }
                    } finally {
                    }
                }
            }
        }

        public android.util.IntArray getCurrentProfileIds() {
            android.util.IntArray intArray;
            synchronized (this.mCurrentProfiles) {
                try {
                    intArray = new android.util.IntArray(this.mCurrentProfiles.size());
                    int size = this.mCurrentProfiles.size();
                    for (int i = 0; i < size; i++) {
                        intArray.add(this.mCurrentProfiles.keyAt(i));
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
            return intArray;
        }

        public boolean isCurrentProfile(int i) {
            boolean z;
            synchronized (this.mCurrentProfiles) {
                z = this.mCurrentProfiles.get(i) != null;
            }
            return z;
        }

        public boolean isProfileUser(int i) {
            synchronized (this.mCurrentProfiles) {
                try {
                    android.content.pm.UserInfo userInfo = this.mCurrentProfiles.get(i);
                    if (userInfo == null) {
                        return false;
                    }
                    return userInfo.isManagedProfile() || userInfo.isCloneProfile();
                } finally {
                }
            }
        }
    }
}
