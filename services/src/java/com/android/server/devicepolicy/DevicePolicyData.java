package com.android.server.devicepolicy;

/* loaded from: classes.dex */
class DevicePolicyData {
    private static final java.lang.String ATTR_ALIAS = "alias";
    private static final java.lang.String ATTR_DEVICE_PAIRED = "device-paired";
    private static final java.lang.String ATTR_DEVICE_PROVISIONING_CONFIG_APPLIED = "device-provisioning-config-applied";
    private static final java.lang.String ATTR_DISABLED = "disabled";
    private static final java.lang.String ATTR_FACTORY_RESET_FLAGS = "factory-reset-flags";
    private static final java.lang.String ATTR_FACTORY_RESET_REASON = "factory-reset-reason";
    private static final java.lang.String ATTR_ID = "id";
    private static final java.lang.String ATTR_NAME = "name";
    private static final java.lang.String ATTR_NEW_USER_DISCLAIMER = "new-user-disclaimer";
    private static final java.lang.String ATTR_PERMISSION_POLICY = "permission-policy";
    private static final java.lang.String ATTR_PERMISSION_PROVIDER = "permission-provider";
    private static final java.lang.String ATTR_PROVISIONING_STATE = "provisioning-state";
    private static final java.lang.String ATTR_SETUP_COMPLETE = "setup-complete";
    private static final java.lang.String ATTR_VALUE = "value";
    public static final int FACTORY_RESET_FLAG_ON_BOOT = 1;
    public static final int FACTORY_RESET_FLAG_WIPE_EUICC = 4;
    public static final int FACTORY_RESET_FLAG_WIPE_EXTERNAL_STORAGE = 2;
    public static final int FACTORY_RESET_FLAG_WIPE_FACTORY_RESET_PROTECTION = 8;
    static final java.lang.String NEW_USER_DISCLAIMER_ACKNOWLEDGED = "acked";
    static final java.lang.String NEW_USER_DISCLAIMER_NEEDED = "needed";
    static final java.lang.String NEW_USER_DISCLAIMER_NOT_NEEDED = "not_needed";
    private static final java.lang.String TAG = "DevicePolicyManager";
    private static final java.lang.String TAG_ACCEPTED_CA_CERTIFICATES = "accepted-ca-certificate";
    private static final java.lang.String TAG_ADMIN_BROADCAST_PENDING = "admin-broadcast-pending";
    private static final java.lang.String TAG_AFFILIATION_ID = "affiliation-id";
    private static final java.lang.String TAG_APPS_SUSPENDED = "apps-suspended";
    private static final java.lang.String TAG_BYPASS_ROLE_QUALIFICATIONS = "bypass-role-qualifications";
    private static final java.lang.String TAG_CURRENT_INPUT_METHOD_SET = "current-ime-set";
    private static final java.lang.String TAG_DO_NOT_ASK_CREDENTIALS_ON_BOOT = "do-not-ask-credentials-on-boot";
    private static final java.lang.String TAG_INITIALIZATION_BUNDLE = "initialization-bundle";
    private static final java.lang.String TAG_KEEP_PROFILES_RUNNING = "keep-profiles-running";
    private static final java.lang.String TAG_LAST_BUG_REPORT_REQUEST = "last-bug-report-request";
    private static final java.lang.String TAG_LAST_NETWORK_LOG_RETRIEVAL = "last-network-log-retrieval";
    private static final java.lang.String TAG_LAST_SECURITY_LOG_RETRIEVAL = "last-security-log-retrieval";
    private static final java.lang.String TAG_LOCK_TASK_COMPONENTS = "lock-task-component";
    private static final java.lang.String TAG_LOCK_TASK_FEATURES = "lock-task-features";
    private static final java.lang.String TAG_OWNER_INSTALLED_CA_CERT = "owner-installed-ca-cert";
    private static final java.lang.String TAG_PASSWORD_TOKEN_HANDLE = "password-token";
    private static final java.lang.String TAG_PROTECTED_PACKAGES = "protected-packages";
    private static final java.lang.String TAG_SECONDARY_LOCK_SCREEN = "secondary-lock-screen";
    private static final java.lang.String TAG_STATUS_BAR = "statusbar";
    private static final boolean VERBOSE_LOG = false;
    java.lang.String mCurrentRoleHolder;
    int mFactoryResetFlags;
    java.lang.String mFactoryResetReason;
    com.android.server.devicepolicy.ActiveAdmin mPermissionBasedAdmin;
    int mPermissionPolicy;
    android.content.ComponentName mRestrictionsProvider;

    @android.annotation.Nullable
    @java.lang.Deprecated
    java.util.List<java.lang.String> mUserControlDisabledPackages;
    final int mUserId;
    int mUserProvisioningState;
    int mFailedPasswordAttempts = 0;
    boolean mPasswordValidAtLastCheckpoint = true;
    int mPasswordOwner = -1;
    long mLastMaximumTimeToLock = -1;
    boolean mUserSetupComplete = false;
    boolean mBypassDevicePolicyManagementRoleQualifications = false;
    boolean mPaired = false;
    boolean mDeviceProvisioningConfigApplied = false;
    final android.util.ArrayMap<android.content.ComponentName, com.android.server.devicepolicy.ActiveAdmin> mAdminMap = new android.util.ArrayMap<>();
    final java.util.ArrayList<com.android.server.devicepolicy.ActiveAdmin> mAdminList = new java.util.ArrayList<>();
    final java.util.ArrayList<android.content.ComponentName> mRemovingAdmins = new java.util.ArrayList<>();
    final android.util.ArraySet<java.lang.String> mAcceptedCaCertificates = new android.util.ArraySet<>();
    java.util.List<java.lang.String> mLockTaskPackages = new java.util.ArrayList();
    int mLockTaskFeatures = 16;
    boolean mStatusBarDisabled = false;
    final android.util.ArrayMap<java.lang.String, java.util.List<java.lang.String>> mDelegationMap = new android.util.ArrayMap<>();
    boolean mDoNotAskCredentialsOnBoot = false;
    java.util.Set<java.lang.String> mAffiliationIds = new android.util.ArraySet();
    long mLastSecurityLogRetrievalTime = -1;
    long mLastBugReportRequestTime = -1;
    long mLastNetworkLogsRetrievalTime = -1;
    boolean mCurrentInputMethodSet = false;
    boolean mSecondaryLockscreenEnabled = false;
    java.util.Set<java.lang.String> mOwnerInstalledCaCerts = new android.util.ArraySet();
    boolean mAdminBroadcastPending = false;
    android.os.PersistableBundle mInitBundle = null;
    long mPasswordTokenHandle = 0;
    boolean mAppsSuspended = false;
    java.lang.String mNewUserDisclaimer = NEW_USER_DISCLAIMER_NOT_NEEDED;
    boolean mEffectiveKeepProfilesRunning = false;

    com.android.server.devicepolicy.ActiveAdmin createOrGetPermissionBasedAdmin(int i) {
        if (this.mPermissionBasedAdmin == null) {
            this.mPermissionBasedAdmin = new com.android.server.devicepolicy.ActiveAdmin(i, true);
        }
        return this.mPermissionBasedAdmin;
    }

    DevicePolicyData(int i) {
        this.mUserId = i;
    }

    static boolean store(com.android.server.devicepolicy.DevicePolicyData devicePolicyData, com.android.internal.util.JournaledFile journaledFile) {
        java.io.File file;
        java.io.FileOutputStream fileOutputStream;
        java.lang.String str;
        com.android.modules.utils.TypedXmlSerializer resolveSerializer;
        java.lang.String str2;
        java.lang.String str3 = TAG_DO_NOT_ASK_CREDENTIALS_ON_BOOT;
        java.lang.String str4 = TAG_STATUS_BAR;
        try {
            file = journaledFile.chooseForWrite();
            try {
                str = TAG_AFFILIATION_ID;
                java.io.FileOutputStream fileOutputStream2 = new java.io.FileOutputStream(file, false);
                try {
                    resolveSerializer = android.util.Xml.resolveSerializer(fileOutputStream2);
                    fileOutputStream = fileOutputStream2;
                } catch (java.io.IOException | org.xmlpull.v1.XmlPullParserException e) {
                    e = e;
                    fileOutputStream = fileOutputStream2;
                }
            } catch (java.io.IOException | org.xmlpull.v1.XmlPullParserException e2) {
                e = e2;
                fileOutputStream = null;
            }
        } catch (java.io.IOException | org.xmlpull.v1.XmlPullParserException e3) {
            e = e3;
            file = null;
            fileOutputStream = null;
        }
        try {
            resolveSerializer.startDocument((java.lang.String) null, true);
            resolveSerializer.startTag((java.lang.String) null, "policies");
            if (devicePolicyData.mRestrictionsProvider == null) {
                str2 = "policies";
            } else {
                str2 = "policies";
                resolveSerializer.attribute((java.lang.String) null, ATTR_PERMISSION_PROVIDER, devicePolicyData.mRestrictionsProvider.flattenToString());
            }
            if (devicePolicyData.mUserSetupComplete) {
                resolveSerializer.attributeBoolean((java.lang.String) null, ATTR_SETUP_COMPLETE, true);
            }
            if (devicePolicyData.mPaired) {
                resolveSerializer.attributeBoolean((java.lang.String) null, ATTR_DEVICE_PAIRED, true);
            }
            if (devicePolicyData.mDeviceProvisioningConfigApplied) {
                resolveSerializer.attributeBoolean((java.lang.String) null, ATTR_DEVICE_PROVISIONING_CONFIG_APPLIED, true);
            }
            if (devicePolicyData.mUserProvisioningState != 0) {
                resolveSerializer.attributeInt((java.lang.String) null, ATTR_PROVISIONING_STATE, devicePolicyData.mUserProvisioningState);
            }
            if (devicePolicyData.mPermissionPolicy != 0) {
                resolveSerializer.attributeInt((java.lang.String) null, ATTR_PERMISSION_POLICY, devicePolicyData.mPermissionPolicy);
            }
            if (NEW_USER_DISCLAIMER_NEEDED.equals(devicePolicyData.mNewUserDisclaimer)) {
                resolveSerializer.attribute((java.lang.String) null, ATTR_NEW_USER_DISCLAIMER, devicePolicyData.mNewUserDisclaimer);
            }
            if (devicePolicyData.mFactoryResetFlags != 0) {
                resolveSerializer.attributeInt((java.lang.String) null, ATTR_FACTORY_RESET_FLAGS, devicePolicyData.mFactoryResetFlags);
            }
            if (devicePolicyData.mFactoryResetReason != null) {
                resolveSerializer.attribute((java.lang.String) null, ATTR_FACTORY_RESET_REASON, devicePolicyData.mFactoryResetReason);
            }
            for (int i = 0; i < devicePolicyData.mDelegationMap.size(); i++) {
                java.lang.String keyAt = devicePolicyData.mDelegationMap.keyAt(i);
                java.util.Iterator<java.lang.String> it = devicePolicyData.mDelegationMap.valueAt(i).iterator();
                while (it.hasNext()) {
                    java.util.Iterator<java.lang.String> it2 = it;
                    java.lang.String next = it.next();
                    resolveSerializer.startTag((java.lang.String) null, "delegation");
                    resolveSerializer.attribute((java.lang.String) null, "delegatePackage", keyAt);
                    resolveSerializer.attribute((java.lang.String) null, "scope", next);
                    resolveSerializer.endTag((java.lang.String) null, "delegation");
                    str3 = str3;
                    it = it2;
                    str4 = str4;
                }
            }
            java.lang.String str5 = str3;
            java.lang.String str6 = str4;
            int size = devicePolicyData.mAdminList.size();
            for (int i2 = 0; i2 < size; i2++) {
                com.android.server.devicepolicy.ActiveAdmin activeAdmin = devicePolicyData.mAdminList.get(i2);
                if (activeAdmin != null) {
                    resolveSerializer.startTag((java.lang.String) null, "admin");
                    resolveSerializer.attribute((java.lang.String) null, "name", activeAdmin.info.getComponent().flattenToString());
                    activeAdmin.writeToXml(resolveSerializer);
                    resolveSerializer.endTag((java.lang.String) null, "admin");
                }
            }
            if (devicePolicyData.mPermissionBasedAdmin != null) {
                resolveSerializer.startTag((java.lang.String) null, "permission-based-admin");
                devicePolicyData.mPermissionBasedAdmin.writeToXml(resolveSerializer);
                resolveSerializer.endTag((java.lang.String) null, "permission-based-admin");
            }
            if (devicePolicyData.mPasswordOwner >= 0) {
                resolveSerializer.startTag((java.lang.String) null, "password-owner");
                resolveSerializer.attributeInt((java.lang.String) null, ATTR_VALUE, devicePolicyData.mPasswordOwner);
                resolveSerializer.endTag((java.lang.String) null, "password-owner");
            }
            if (devicePolicyData.mFailedPasswordAttempts != 0) {
                resolveSerializer.startTag((java.lang.String) null, "failed-password-attempts");
                resolveSerializer.attributeInt((java.lang.String) null, ATTR_VALUE, devicePolicyData.mFailedPasswordAttempts);
                resolveSerializer.endTag((java.lang.String) null, "failed-password-attempts");
            }
            for (int i3 = 0; i3 < devicePolicyData.mAcceptedCaCertificates.size(); i3++) {
                resolveSerializer.startTag((java.lang.String) null, TAG_ACCEPTED_CA_CERTIFICATES);
                resolveSerializer.attribute((java.lang.String) null, "name", devicePolicyData.mAcceptedCaCertificates.valueAt(i3));
                resolveSerializer.endTag((java.lang.String) null, TAG_ACCEPTED_CA_CERTIFICATES);
            }
            for (int i4 = 0; i4 < devicePolicyData.mLockTaskPackages.size(); i4++) {
                java.lang.String str7 = devicePolicyData.mLockTaskPackages.get(i4);
                resolveSerializer.startTag((java.lang.String) null, TAG_LOCK_TASK_COMPONENTS);
                resolveSerializer.attribute((java.lang.String) null, "name", str7);
                resolveSerializer.endTag((java.lang.String) null, TAG_LOCK_TASK_COMPONENTS);
            }
            if (devicePolicyData.mLockTaskFeatures != 0) {
                resolveSerializer.startTag((java.lang.String) null, TAG_LOCK_TASK_FEATURES);
                resolveSerializer.attributeInt((java.lang.String) null, ATTR_VALUE, devicePolicyData.mLockTaskFeatures);
                resolveSerializer.endTag((java.lang.String) null, TAG_LOCK_TASK_FEATURES);
            }
            if (devicePolicyData.mSecondaryLockscreenEnabled) {
                resolveSerializer.startTag((java.lang.String) null, TAG_SECONDARY_LOCK_SCREEN);
                resolveSerializer.attributeBoolean((java.lang.String) null, ATTR_VALUE, true);
                resolveSerializer.endTag((java.lang.String) null, TAG_SECONDARY_LOCK_SCREEN);
            }
            if (devicePolicyData.mStatusBarDisabled) {
                resolveSerializer.startTag((java.lang.String) null, str6);
                resolveSerializer.attributeBoolean((java.lang.String) null, "disabled", devicePolicyData.mStatusBarDisabled);
                resolveSerializer.endTag((java.lang.String) null, str6);
            }
            if (devicePolicyData.mDoNotAskCredentialsOnBoot) {
                resolveSerializer.startTag((java.lang.String) null, str5);
                resolveSerializer.endTag((java.lang.String) null, str5);
            }
            for (java.lang.String str8 : devicePolicyData.mAffiliationIds) {
                java.lang.String str9 = str;
                resolveSerializer.startTag((java.lang.String) null, str9);
                resolveSerializer.attribute((java.lang.String) null, ATTR_ID, str8);
                resolveSerializer.endTag((java.lang.String) null, str9);
                str = str9;
            }
            if (devicePolicyData.mLastSecurityLogRetrievalTime >= 0) {
                resolveSerializer.startTag((java.lang.String) null, TAG_LAST_SECURITY_LOG_RETRIEVAL);
                resolveSerializer.attributeLong((java.lang.String) null, ATTR_VALUE, devicePolicyData.mLastSecurityLogRetrievalTime);
                resolveSerializer.endTag((java.lang.String) null, TAG_LAST_SECURITY_LOG_RETRIEVAL);
            }
            if (devicePolicyData.mLastBugReportRequestTime >= 0) {
                resolveSerializer.startTag((java.lang.String) null, TAG_LAST_BUG_REPORT_REQUEST);
                resolveSerializer.attributeLong((java.lang.String) null, ATTR_VALUE, devicePolicyData.mLastBugReportRequestTime);
                resolveSerializer.endTag((java.lang.String) null, TAG_LAST_BUG_REPORT_REQUEST);
            }
            if (devicePolicyData.mLastNetworkLogsRetrievalTime >= 0) {
                resolveSerializer.startTag((java.lang.String) null, TAG_LAST_NETWORK_LOG_RETRIEVAL);
                resolveSerializer.attributeLong((java.lang.String) null, ATTR_VALUE, devicePolicyData.mLastNetworkLogsRetrievalTime);
                resolveSerializer.endTag((java.lang.String) null, TAG_LAST_NETWORK_LOG_RETRIEVAL);
            }
            if (devicePolicyData.mAdminBroadcastPending) {
                resolveSerializer.startTag((java.lang.String) null, TAG_ADMIN_BROADCAST_PENDING);
                resolveSerializer.attributeBoolean((java.lang.String) null, ATTR_VALUE, devicePolicyData.mAdminBroadcastPending);
                resolveSerializer.endTag((java.lang.String) null, TAG_ADMIN_BROADCAST_PENDING);
            }
            if (devicePolicyData.mInitBundle != null) {
                resolveSerializer.startTag((java.lang.String) null, TAG_INITIALIZATION_BUNDLE);
                devicePolicyData.mInitBundle.saveToXml(resolveSerializer);
                resolveSerializer.endTag((java.lang.String) null, TAG_INITIALIZATION_BUNDLE);
            }
            if (devicePolicyData.mPasswordTokenHandle != 0) {
                resolveSerializer.startTag((java.lang.String) null, TAG_PASSWORD_TOKEN_HANDLE);
                resolveSerializer.attributeLong((java.lang.String) null, ATTR_VALUE, devicePolicyData.mPasswordTokenHandle);
                resolveSerializer.endTag((java.lang.String) null, TAG_PASSWORD_TOKEN_HANDLE);
            }
            if (devicePolicyData.mCurrentInputMethodSet) {
                resolveSerializer.startTag((java.lang.String) null, TAG_CURRENT_INPUT_METHOD_SET);
                resolveSerializer.endTag((java.lang.String) null, TAG_CURRENT_INPUT_METHOD_SET);
            }
            for (java.lang.String str10 : devicePolicyData.mOwnerInstalledCaCerts) {
                resolveSerializer.startTag((java.lang.String) null, TAG_OWNER_INSTALLED_CA_CERT);
                resolveSerializer.attribute((java.lang.String) null, ATTR_ALIAS, str10);
                resolveSerializer.endTag((java.lang.String) null, TAG_OWNER_INSTALLED_CA_CERT);
            }
            if (devicePolicyData.mAppsSuspended) {
                resolveSerializer.startTag((java.lang.String) null, TAG_APPS_SUSPENDED);
                resolveSerializer.attributeBoolean((java.lang.String) null, ATTR_VALUE, devicePolicyData.mAppsSuspended);
                resolveSerializer.endTag((java.lang.String) null, TAG_APPS_SUSPENDED);
            }
            if (devicePolicyData.mBypassDevicePolicyManagementRoleQualifications) {
                resolveSerializer.startTag((java.lang.String) null, TAG_BYPASS_ROLE_QUALIFICATIONS);
                resolveSerializer.attribute((java.lang.String) null, ATTR_VALUE, devicePolicyData.mCurrentRoleHolder);
                resolveSerializer.endTag((java.lang.String) null, TAG_BYPASS_ROLE_QUALIFICATIONS);
            }
            if (devicePolicyData.mEffectiveKeepProfilesRunning) {
                resolveSerializer.startTag((java.lang.String) null, TAG_KEEP_PROFILES_RUNNING);
                resolveSerializer.attributeBoolean((java.lang.String) null, ATTR_VALUE, devicePolicyData.mEffectiveKeepProfilesRunning);
                resolveSerializer.endTag((java.lang.String) null, TAG_KEEP_PROFILES_RUNNING);
            }
            resolveSerializer.endTag((java.lang.String) null, str2);
            resolveSerializer.endDocument();
            fileOutputStream.flush();
            android.os.FileUtils.sync(fileOutputStream);
            fileOutputStream.close();
            journaledFile.commit();
            return true;
        } catch (java.io.IOException | org.xmlpull.v1.XmlPullParserException e4) {
            e = e4;
            file = file;
            com.android.server.utils.Slogf.w(TAG, e, "failed writing file %s", file);
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (java.io.IOException e5) {
                }
            }
            journaledFile.rollback();
            return false;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:45:0x033d A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    static void load(com.android.server.devicepolicy.DevicePolicyData devicePolicyData, com.android.internal.util.JournaledFile journaledFile, java.util.function.Function<android.content.ComponentName, android.app.admin.DeviceAdminInfo> function, android.content.ComponentName componentName) {
        java.io.FileInputStream fileInputStream;
        com.android.modules.utils.TypedXmlPullParser resolvePullParser;
        int next;
        java.lang.String name;
        java.io.File chooseForRead = journaledFile.chooseForRead();
        java.io.FileInputStream fileInputStream2 = null;
        try {
            fileInputStream = new java.io.FileInputStream(chooseForRead);
        } catch (java.io.FileNotFoundException e) {
        } catch (java.io.IOException | java.lang.IndexOutOfBoundsException | java.lang.NullPointerException | java.lang.NumberFormatException | org.xmlpull.v1.XmlPullParserException e2) {
            e = e2;
        }
        try {
            try {
                resolvePullParser = android.util.Xml.resolvePullParser(fileInputStream);
                do {
                    next = resolvePullParser.next();
                    if (next == 1) {
                        break;
                    }
                } while (next != 2);
                name = resolvePullParser.getName();
            } catch (java.io.IOException | java.lang.IndexOutOfBoundsException | java.lang.NullPointerException | java.lang.NumberFormatException | org.xmlpull.v1.XmlPullParserException e3) {
                e = e3;
                fileInputStream2 = fileInputStream;
                com.android.server.utils.Slogf.w(TAG, e, "failed parsing %s", chooseForRead);
                fileInputStream = fileInputStream2;
                if (fileInputStream != null) {
                }
                devicePolicyData.mAdminList.addAll(devicePolicyData.mAdminMap.values());
                return;
            }
        } catch (java.io.FileNotFoundException e4) {
            fileInputStream2 = fileInputStream;
            fileInputStream = fileInputStream2;
            if (fileInputStream != null) {
            }
            devicePolicyData.mAdminList.addAll(devicePolicyData.mAdminMap.values());
            return;
        }
        if ("policies".equals(name)) {
            java.lang.String attributeValue = resolvePullParser.getAttributeValue((java.lang.String) null, ATTR_PERMISSION_PROVIDER);
            if (attributeValue != null) {
                devicePolicyData.mRestrictionsProvider = android.content.ComponentName.unflattenFromString(attributeValue);
            }
            if (java.lang.Boolean.toString(true).equals(resolvePullParser.getAttributeValue((java.lang.String) null, ATTR_SETUP_COMPLETE))) {
                devicePolicyData.mUserSetupComplete = true;
            }
            if (java.lang.Boolean.toString(true).equals(resolvePullParser.getAttributeValue((java.lang.String) null, ATTR_DEVICE_PAIRED))) {
                devicePolicyData.mPaired = true;
            }
            if (java.lang.Boolean.toString(true).equals(resolvePullParser.getAttributeValue((java.lang.String) null, ATTR_DEVICE_PROVISIONING_CONFIG_APPLIED))) {
                devicePolicyData.mDeviceProvisioningConfigApplied = true;
            }
            int attributeInt = resolvePullParser.getAttributeInt((java.lang.String) null, ATTR_PROVISIONING_STATE, -1);
            if (attributeInt != -1) {
                devicePolicyData.mUserProvisioningState = attributeInt;
            }
            int attributeInt2 = resolvePullParser.getAttributeInt((java.lang.String) null, ATTR_PERMISSION_POLICY, -1);
            if (attributeInt2 != -1) {
                devicePolicyData.mPermissionPolicy = attributeInt2;
            }
            devicePolicyData.mNewUserDisclaimer = resolvePullParser.getAttributeValue((java.lang.String) null, ATTR_NEW_USER_DISCLAIMER);
            devicePolicyData.mFactoryResetFlags = resolvePullParser.getAttributeInt((java.lang.String) null, ATTR_FACTORY_RESET_FLAGS, 0);
            devicePolicyData.mFactoryResetReason = resolvePullParser.getAttributeValue((java.lang.String) null, ATTR_FACTORY_RESET_REASON);
            int depth = resolvePullParser.getDepth();
            devicePolicyData.mLockTaskPackages.clear();
            devicePolicyData.mAdminList.clear();
            devicePolicyData.mAdminMap.clear();
            devicePolicyData.mPermissionBasedAdmin = null;
            devicePolicyData.mAffiliationIds.clear();
            devicePolicyData.mOwnerInstalledCaCerts.clear();
            devicePolicyData.mUserControlDisabledPackages = null;
            while (true) {
                int next2 = resolvePullParser.next();
                if (next2 == 1 || (next2 == 3 && resolvePullParser.getDepth() <= depth)) {
                    break;
                }
                if (next2 != 3 && next2 != 4) {
                    java.lang.String name2 = resolvePullParser.getName();
                    if ("admin".equals(name2)) {
                        java.lang.String attributeValue2 = resolvePullParser.getAttributeValue((java.lang.String) null, "name");
                        try {
                            android.app.admin.DeviceAdminInfo apply = function.apply(android.content.ComponentName.unflattenFromString(attributeValue2));
                            if (apply != null) {
                                boolean z = !apply.getComponent().equals(componentName);
                                com.android.server.devicepolicy.ActiveAdmin activeAdmin = new com.android.server.devicepolicy.ActiveAdmin(apply, false);
                                activeAdmin.readFromXml(resolvePullParser, z);
                                devicePolicyData.mAdminMap.put(activeAdmin.info.getComponent(), activeAdmin);
                            }
                        } catch (java.lang.RuntimeException e5) {
                            com.android.server.utils.Slogf.w(TAG, e5, "Failed loading admin %s", attributeValue2);
                        }
                    } else if ("permission-based-admin".equals(name2)) {
                        com.android.server.devicepolicy.ActiveAdmin activeAdmin2 = new com.android.server.devicepolicy.ActiveAdmin(devicePolicyData.mUserId, true);
                        activeAdmin2.readFromXml(resolvePullParser, false);
                        devicePolicyData.mPermissionBasedAdmin = activeAdmin2;
                    } else if ("delegation".equals(name2)) {
                        java.lang.String attributeValue3 = resolvePullParser.getAttributeValue((java.lang.String) null, "delegatePackage");
                        java.lang.String attributeValue4 = resolvePullParser.getAttributeValue((java.lang.String) null, "scope");
                        java.util.List<java.lang.String> list = devicePolicyData.mDelegationMap.get(attributeValue3);
                        if (list == null) {
                            list = new java.util.ArrayList<>();
                            devicePolicyData.mDelegationMap.put(attributeValue3, list);
                        }
                        if (!list.contains(attributeValue4)) {
                            list.add(attributeValue4);
                        }
                    } else if ("failed-password-attempts".equals(name2)) {
                        devicePolicyData.mFailedPasswordAttempts = resolvePullParser.getAttributeInt((java.lang.String) null, ATTR_VALUE);
                    } else if ("password-owner".equals(name2)) {
                        devicePolicyData.mPasswordOwner = resolvePullParser.getAttributeInt((java.lang.String) null, ATTR_VALUE);
                    } else if (TAG_ACCEPTED_CA_CERTIFICATES.equals(name2)) {
                        devicePolicyData.mAcceptedCaCertificates.add(resolvePullParser.getAttributeValue((java.lang.String) null, "name"));
                    } else if (TAG_LOCK_TASK_COMPONENTS.equals(name2)) {
                        devicePolicyData.mLockTaskPackages.add(resolvePullParser.getAttributeValue((java.lang.String) null, "name"));
                    } else if (TAG_LOCK_TASK_FEATURES.equals(name2)) {
                        devicePolicyData.mLockTaskFeatures = resolvePullParser.getAttributeInt((java.lang.String) null, ATTR_VALUE);
                    } else if (TAG_SECONDARY_LOCK_SCREEN.equals(name2)) {
                        devicePolicyData.mSecondaryLockscreenEnabled = resolvePullParser.getAttributeBoolean((java.lang.String) null, ATTR_VALUE, false);
                    } else if (TAG_STATUS_BAR.equals(name2)) {
                        devicePolicyData.mStatusBarDisabled = resolvePullParser.getAttributeBoolean((java.lang.String) null, "disabled", false);
                    } else if (TAG_DO_NOT_ASK_CREDENTIALS_ON_BOOT.equals(name2)) {
                        devicePolicyData.mDoNotAskCredentialsOnBoot = true;
                    } else if (TAG_AFFILIATION_ID.equals(name2)) {
                        devicePolicyData.mAffiliationIds.add(resolvePullParser.getAttributeValue((java.lang.String) null, ATTR_ID));
                    } else if (TAG_LAST_SECURITY_LOG_RETRIEVAL.equals(name2)) {
                        devicePolicyData.mLastSecurityLogRetrievalTime = resolvePullParser.getAttributeLong((java.lang.String) null, ATTR_VALUE);
                    } else if (TAG_LAST_BUG_REPORT_REQUEST.equals(name2)) {
                        devicePolicyData.mLastBugReportRequestTime = resolvePullParser.getAttributeLong((java.lang.String) null, ATTR_VALUE);
                    } else if (TAG_LAST_NETWORK_LOG_RETRIEVAL.equals(name2)) {
                        devicePolicyData.mLastNetworkLogsRetrievalTime = resolvePullParser.getAttributeLong((java.lang.String) null, ATTR_VALUE);
                    } else if (TAG_ADMIN_BROADCAST_PENDING.equals(name2)) {
                        devicePolicyData.mAdminBroadcastPending = java.lang.Boolean.toString(true).equals(resolvePullParser.getAttributeValue((java.lang.String) null, ATTR_VALUE));
                    } else if (TAG_INITIALIZATION_BUNDLE.equals(name2)) {
                        devicePolicyData.mInitBundle = android.os.PersistableBundle.restoreFromXml(resolvePullParser);
                    } else if (TAG_PASSWORD_TOKEN_HANDLE.equals(name2)) {
                        devicePolicyData.mPasswordTokenHandle = resolvePullParser.getAttributeLong((java.lang.String) null, ATTR_VALUE);
                    } else if (TAG_CURRENT_INPUT_METHOD_SET.equals(name2)) {
                        devicePolicyData.mCurrentInputMethodSet = true;
                    } else if (TAG_OWNER_INSTALLED_CA_CERT.equals(name2)) {
                        devicePolicyData.mOwnerInstalledCaCerts.add(resolvePullParser.getAttributeValue((java.lang.String) null, ATTR_ALIAS));
                    } else if (TAG_APPS_SUSPENDED.equals(name2)) {
                        devicePolicyData.mAppsSuspended = resolvePullParser.getAttributeBoolean((java.lang.String) null, ATTR_VALUE, false);
                    } else if (TAG_BYPASS_ROLE_QUALIFICATIONS.equals(name2)) {
                        devicePolicyData.mBypassDevicePolicyManagementRoleQualifications = true;
                        devicePolicyData.mCurrentRoleHolder = resolvePullParser.getAttributeValue((java.lang.String) null, ATTR_VALUE);
                    } else if (TAG_KEEP_PROFILES_RUNNING.equals(name2)) {
                        devicePolicyData.mEffectiveKeepProfilesRunning = resolvePullParser.getAttributeBoolean((java.lang.String) null, ATTR_VALUE, false);
                    } else if (!TAG_PROTECTED_PACKAGES.equals(name2)) {
                        com.android.server.utils.Slogf.w(TAG, "Unknown tag: %s", name2);
                        com.android.internal.util.XmlUtils.skipCurrentTag(resolvePullParser);
                    } else {
                        if (devicePolicyData.mUserControlDisabledPackages == null) {
                            devicePolicyData.mUserControlDisabledPackages = new java.util.ArrayList();
                        }
                        devicePolicyData.mUserControlDisabledPackages.add(resolvePullParser.getAttributeValue((java.lang.String) null, "name"));
                    }
                }
            }
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (java.io.IOException e6) {
                }
            }
            devicePolicyData.mAdminList.addAll(devicePolicyData.mAdminMap.values());
            return;
        }
        throw new org.xmlpull.v1.XmlPullParserException("Settings do not start with policies tag: found " + name);
    }

    void validatePasswordOwner() {
        if (this.mPasswordOwner >= 0) {
            boolean z = true;
            int size = this.mAdminList.size() - 1;
            while (true) {
                if (size < 0) {
                    z = false;
                    break;
                } else if (this.mAdminList.get(size).getUid() == this.mPasswordOwner) {
                    break;
                } else {
                    size--;
                }
            }
            if (!z) {
                com.android.server.utils.Slogf.w(TAG, "Previous password owner %s no longer active; disabling", java.lang.Integer.valueOf(this.mPasswordOwner));
                this.mPasswordOwner = -1;
            }
        }
    }

    void setDelayedFactoryReset(@android.annotation.NonNull java.lang.String str, boolean z, boolean z2, boolean z3) {
        this.mFactoryResetReason = str;
        this.mFactoryResetFlags = 1;
        if (z) {
            this.mFactoryResetFlags |= 2;
        }
        if (z2) {
            this.mFactoryResetFlags |= 4;
        }
        if (z3) {
            this.mFactoryResetFlags |= 8;
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    boolean isNewUserDisclaimerAcknowledged() {
        char c;
        if (this.mNewUserDisclaimer == null) {
            if (this.mUserId == 0) {
                return true;
            }
            com.android.server.utils.Slogf.w(TAG, "isNewUserDisclaimerAcknowledged(%d): mNewUserDisclaimer is null", java.lang.Integer.valueOf(this.mUserId));
            return false;
        }
        java.lang.String str = this.mNewUserDisclaimer;
        switch (str.hashCode()) {
            case -1238968671:
                if (str.equals(NEW_USER_DISCLAIMER_NOT_NEEDED)) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case -1049376843:
                if (str.equals(NEW_USER_DISCLAIMER_NEEDED)) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case 92636904:
                if (str.equals(NEW_USER_DISCLAIMER_ACKNOWLEDGED)) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        switch (c) {
            case 0:
            case 1:
                return true;
            case 2:
                return false;
            default:
                com.android.server.utils.Slogf.w(TAG, "isNewUserDisclaimerAcknowledged(%d): invalid value %d", java.lang.Integer.valueOf(this.mUserId), this.mNewUserDisclaimer);
                return false;
        }
    }

    void dump(android.util.IndentingPrintWriter indentingPrintWriter) {
        indentingPrintWriter.println();
        indentingPrintWriter.println("Enabled Device Admins (User " + this.mUserId + ", provisioningState: " + this.mUserProvisioningState + "):");
        int size = this.mAdminList.size();
        for (int i = 0; i < size; i++) {
            com.android.server.devicepolicy.ActiveAdmin activeAdmin = this.mAdminList.get(i);
            if (activeAdmin != null) {
                indentingPrintWriter.increaseIndent();
                indentingPrintWriter.print(activeAdmin.info.getComponent().flattenToShortString());
                indentingPrintWriter.println(":");
                indentingPrintWriter.increaseIndent();
                activeAdmin.dump(indentingPrintWriter);
                indentingPrintWriter.decreaseIndent();
                indentingPrintWriter.decreaseIndent();
            }
        }
        if (!this.mRemovingAdmins.isEmpty()) {
            indentingPrintWriter.increaseIndent();
            indentingPrintWriter.println("Removing Device Admins (User " + this.mUserId + "): " + this.mRemovingAdmins);
            indentingPrintWriter.decreaseIndent();
        }
        indentingPrintWriter.println();
        indentingPrintWriter.increaseIndent();
        indentingPrintWriter.print("mPasswordOwner=");
        indentingPrintWriter.println(this.mPasswordOwner);
        indentingPrintWriter.print("mPasswordTokenHandle=");
        indentingPrintWriter.println(java.lang.Long.toHexString(this.mPasswordTokenHandle));
        indentingPrintWriter.print("mAppsSuspended=");
        indentingPrintWriter.println(this.mAppsSuspended);
        indentingPrintWriter.print("mUserSetupComplete=");
        indentingPrintWriter.println(this.mUserSetupComplete);
        indentingPrintWriter.print("mAffiliationIds=");
        indentingPrintWriter.println(this.mAffiliationIds);
        indentingPrintWriter.print("mNewUserDisclaimer=");
        indentingPrintWriter.println(this.mNewUserDisclaimer);
        if (this.mFactoryResetFlags != 0) {
            indentingPrintWriter.print("mFactoryResetFlags=");
            indentingPrintWriter.print(this.mFactoryResetFlags);
            indentingPrintWriter.print(" (");
            indentingPrintWriter.print(factoryResetFlagsToString(this.mFactoryResetFlags));
            indentingPrintWriter.println(')');
        }
        if (this.mFactoryResetReason != null) {
            indentingPrintWriter.print("mFactoryResetReason=");
            indentingPrintWriter.println(this.mFactoryResetReason);
        }
        if (this.mDelegationMap.size() != 0) {
            indentingPrintWriter.println("mDelegationMap=");
            indentingPrintWriter.increaseIndent();
            for (int i2 = 0; i2 < this.mDelegationMap.size(); i2++) {
                java.util.List<java.lang.String> valueAt = this.mDelegationMap.valueAt(i2);
                indentingPrintWriter.println(this.mDelegationMap.keyAt(i2) + "[size=" + valueAt.size() + "]");
                indentingPrintWriter.increaseIndent();
                for (int i3 = 0; i3 < valueAt.size(); i3++) {
                    indentingPrintWriter.println(i3 + ": " + valueAt.get(i3));
                }
                indentingPrintWriter.decreaseIndent();
            }
            indentingPrintWriter.decreaseIndent();
        }
        indentingPrintWriter.decreaseIndent();
    }

    static java.lang.String factoryResetFlagsToString(int i) {
        return android.util.DebugUtils.flagsToString(com.android.server.devicepolicy.DevicePolicyData.class, "FACTORY_RESET_FLAG_", i);
    }
}
