package com.android.internal.telephony;

/* loaded from: classes5.dex */
public final class SmsApplication {
    public static final java.lang.String ACTION_DEFAULT_SMS_PACKAGE_CHANGED_INTERNAL = "android.provider.action.DEFAULT_SMS_PACKAGE_CHANGED_INTERNAL";
    private static final boolean DEBUG = false;
    private static final boolean DEBUG_MULTIUSER = false;
    static final java.lang.String LOG_TAG = "SmsApplication";
    public static final java.lang.String MMS_SERVICE_PACKAGE_NAME = "com.android.mms.service";
    public static final java.lang.String PERMISSION_MONITOR_DEFAULT_SMS_PACKAGE = "android.permission.MONITOR_DEFAULT_SMS_PACKAGE";
    public static final java.lang.String PHONE_PACKAGE_NAME = "com.android.phone";
    private static final java.lang.String SCHEME_MMS = "mms";
    private static final java.lang.String SCHEME_MMSTO = "mmsto";
    private static final java.lang.String SCHEME_SMS = "sms";
    private static final java.lang.String SCHEME_SMSTO = "smsto";
    public static final java.lang.String TELEPHONY_PROVIDER_PACKAGE_NAME = "com.android.providers.telephony";
    private static final java.lang.String[] DEFAULT_APP_EXCLUSIVE_APPOPS = {android.app.AppOpsManager.OPSTR_READ_SMS, android.app.AppOpsManager.OPSTR_WRITE_SMS, android.app.AppOpsManager.OPSTR_RECEIVE_SMS, android.app.AppOpsManager.OPSTR_RECEIVE_WAP_PUSH, android.app.AppOpsManager.OPSTR_SEND_SMS, android.app.AppOpsManager.OPSTR_READ_CELL_BROADCASTS};
    private static com.android.internal.telephony.SmsApplication.SmsPackageMonitor sSmsPackageMonitor = null;
    private static com.android.internal.telephony.SmsApplication.SmsRoleListener sSmsRoleListener = null;

    public static class SmsApplicationData {
        private java.lang.String mApplicationName;
        private java.lang.String mMmsReceiverClass;
        public java.lang.String mPackageName;
        private java.lang.String mProviderChangedReceiverClass;
        private java.lang.String mRespondViaMessageClass;
        private java.lang.String mSendToClass;
        private java.lang.String mSimFullReceiverClass;
        private java.lang.String mSmsAppChangedReceiverClass;
        private java.lang.String mSmsReceiverClass;
        private int mUid;

        public boolean isComplete() {
            return (this.mSmsReceiverClass == null || this.mMmsReceiverClass == null || this.mRespondViaMessageClass == null || this.mSendToClass == null) ? false : true;
        }

        public SmsApplicationData(java.lang.String str, int i) {
            this.mPackageName = str;
            this.mUid = i;
        }

        public java.lang.String getApplicationName(android.content.Context context) {
            if (this.mApplicationName == null) {
                android.content.pm.PackageManager packageManager = context.getPackageManager();
                try {
                    android.content.pm.ApplicationInfo applicationInfoAsUser = packageManager.getApplicationInfoAsUser(this.mPackageName, 0, android.os.UserHandle.getUserHandleForUid(this.mUid));
                    if (applicationInfoAsUser != null) {
                        java.lang.CharSequence applicationLabel = packageManager.getApplicationLabel(applicationInfoAsUser);
                        this.mApplicationName = applicationLabel != null ? applicationLabel.toString() : null;
                    }
                } catch (android.content.pm.PackageManager.NameNotFoundException e) {
                    return null;
                }
            }
            return this.mApplicationName;
        }

        public java.lang.String toString() {
            return " mPackageName: " + this.mPackageName + " mSmsReceiverClass: " + this.mSmsReceiverClass + " mMmsReceiverClass: " + this.mMmsReceiverClass + " mRespondViaMessageClass: " + this.mRespondViaMessageClass + " mSendToClass: " + this.mSendToClass + " mSmsAppChangedClass: " + this.mSmsAppChangedReceiverClass + " mProviderChangedReceiverClass: " + this.mProviderChangedReceiverClass + " mSimFullReceiverClass: " + this.mSimFullReceiverClass + " mUid: " + this.mUid;
        }
    }

    private static int getIncomingUserId() {
        int myUserId = android.os.UserHandle.myUserId();
        int callingUid = android.os.Binder.getCallingUid();
        if (android.os.UserHandle.getAppId(callingUid) < 10000) {
            return myUserId;
        }
        return android.os.UserHandle.getUserHandleForUid(callingUid).getIdentifier();
    }

    private static android.os.UserHandle getIncomingUserHandle() {
        return android.os.UserHandle.of(getIncomingUserId());
    }

    public static java.util.Collection<com.android.internal.telephony.SmsApplication.SmsApplicationData> getApplicationCollection(android.content.Context context) {
        return getApplicationCollectionAsUser(context, getIncomingUserId());
    }

    public static java.util.Collection<com.android.internal.telephony.SmsApplication.SmsApplicationData> getApplicationCollectionAsUser(android.content.Context context, int i) {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            return getApplicationCollectionInternal(context, i);
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    private static java.util.Collection<com.android.internal.telephony.SmsApplication.SmsApplicationData> getApplicationCollectionInternal(android.content.Context context, int i) {
        com.android.internal.telephony.SmsApplication.SmsApplicationData smsApplicationData;
        com.android.internal.telephony.SmsApplication.SmsApplicationData smsApplicationData2;
        com.android.internal.telephony.SmsApplication.SmsApplicationData smsApplicationData3;
        com.android.internal.telephony.SmsApplication.SmsApplicationData smsApplicationData4;
        com.android.internal.telephony.SmsApplication.SmsApplicationData smsApplicationData5;
        com.android.internal.telephony.SmsApplication.SmsApplicationData smsApplicationData6;
        android.content.pm.PackageManager packageManager = context.getPackageManager();
        android.os.UserHandle of = android.os.UserHandle.of(i);
        java.util.List<android.content.pm.ResolveInfo> queryBroadcastReceiversAsUser = packageManager.queryBroadcastReceiversAsUser(new android.content.Intent(android.provider.Telephony.Sms.Intents.SMS_DELIVER_ACTION), 786432, of);
        java.util.HashMap hashMap = new java.util.HashMap();
        java.util.Iterator<android.content.pm.ResolveInfo> it = queryBroadcastReceiversAsUser.iterator();
        while (it.hasNext()) {
            android.content.pm.ActivityInfo activityInfo = it.next().activityInfo;
            if (activityInfo != null && android.Manifest.permission.BROADCAST_SMS.equals(activityInfo.permission)) {
                java.lang.String str = activityInfo.packageName;
                if (!hashMap.containsKey(str)) {
                    com.android.internal.telephony.SmsApplication.SmsApplicationData smsApplicationData7 = new com.android.internal.telephony.SmsApplication.SmsApplicationData(str, activityInfo.applicationInfo.uid);
                    smsApplicationData7.mSmsReceiverClass = activityInfo.name;
                    hashMap.put(str, smsApplicationData7);
                }
            }
        }
        android.content.Intent intent = new android.content.Intent(android.provider.Telephony.Sms.Intents.WAP_PUSH_DELIVER_ACTION);
        intent.setDataAndType(null, com.google.android.mms.ContentType.MMS_MESSAGE);
        java.util.Iterator<android.content.pm.ResolveInfo> it2 = packageManager.queryBroadcastReceiversAsUser(intent, 786432, of).iterator();
        while (it2.hasNext()) {
            android.content.pm.ActivityInfo activityInfo2 = it2.next().activityInfo;
            if (activityInfo2 != null && android.Manifest.permission.BROADCAST_WAP_PUSH.equals(activityInfo2.permission) && (smsApplicationData6 = (com.android.internal.telephony.SmsApplication.SmsApplicationData) hashMap.get(activityInfo2.packageName)) != null) {
                smsApplicationData6.mMmsReceiverClass = activityInfo2.name;
            }
        }
        java.util.Iterator<android.content.pm.ResolveInfo> it3 = packageManager.queryIntentServicesAsUser(new android.content.Intent(android.telephony.TelephonyManager.ACTION_RESPOND_VIA_MESSAGE, android.net.Uri.fromParts(SCHEME_SMSTO, "", null)), 786432, android.os.UserHandle.of(i)).iterator();
        while (it3.hasNext()) {
            android.content.pm.ServiceInfo serviceInfo = it3.next().serviceInfo;
            if (serviceInfo != null && android.Manifest.permission.SEND_RESPOND_VIA_MESSAGE.equals(serviceInfo.permission) && (smsApplicationData5 = (com.android.internal.telephony.SmsApplication.SmsApplicationData) hashMap.get(serviceInfo.packageName)) != null) {
                smsApplicationData5.mRespondViaMessageClass = serviceInfo.name;
            }
        }
        java.util.Iterator<android.content.pm.ResolveInfo> it4 = packageManager.queryIntentActivitiesAsUser(new android.content.Intent(android.content.Intent.ACTION_SENDTO, android.net.Uri.fromParts(SCHEME_SMSTO, "", null)), 786432, of).iterator();
        while (it4.hasNext()) {
            android.content.pm.ActivityInfo activityInfo3 = it4.next().activityInfo;
            if (activityInfo3 != null && (smsApplicationData4 = (com.android.internal.telephony.SmsApplication.SmsApplicationData) hashMap.get(activityInfo3.packageName)) != null) {
                smsApplicationData4.mSendToClass = activityInfo3.name;
            }
        }
        java.util.Iterator<android.content.pm.ResolveInfo> it5 = packageManager.queryBroadcastReceiversAsUser(new android.content.Intent(android.provider.Telephony.Sms.Intents.ACTION_DEFAULT_SMS_PACKAGE_CHANGED), 786432, of).iterator();
        while (it5.hasNext()) {
            android.content.pm.ActivityInfo activityInfo4 = it5.next().activityInfo;
            if (activityInfo4 != null && (smsApplicationData3 = (com.android.internal.telephony.SmsApplication.SmsApplicationData) hashMap.get(activityInfo4.packageName)) != null) {
                smsApplicationData3.mSmsAppChangedReceiverClass = activityInfo4.name;
            }
        }
        java.util.Iterator<android.content.pm.ResolveInfo> it6 = packageManager.queryBroadcastReceiversAsUser(new android.content.Intent(android.provider.Telephony.Sms.Intents.ACTION_EXTERNAL_PROVIDER_CHANGE), 786432, of).iterator();
        while (it6.hasNext()) {
            android.content.pm.ActivityInfo activityInfo5 = it6.next().activityInfo;
            if (activityInfo5 != null && (smsApplicationData2 = (com.android.internal.telephony.SmsApplication.SmsApplicationData) hashMap.get(activityInfo5.packageName)) != null) {
                smsApplicationData2.mProviderChangedReceiverClass = activityInfo5.name;
            }
        }
        java.util.Iterator<android.content.pm.ResolveInfo> it7 = packageManager.queryBroadcastReceiversAsUser(new android.content.Intent(android.provider.Telephony.Sms.Intents.SIM_FULL_ACTION), 786432, of).iterator();
        while (it7.hasNext()) {
            android.content.pm.ActivityInfo activityInfo6 = it7.next().activityInfo;
            if (activityInfo6 != null && (smsApplicationData = (com.android.internal.telephony.SmsApplication.SmsApplicationData) hashMap.get(activityInfo6.packageName)) != null) {
                smsApplicationData.mSimFullReceiverClass = activityInfo6.name;
            }
        }
        java.util.Iterator<android.content.pm.ResolveInfo> it8 = queryBroadcastReceiversAsUser.iterator();
        while (it8.hasNext()) {
            android.content.pm.ActivityInfo activityInfo7 = it8.next().activityInfo;
            if (activityInfo7 != null) {
                java.lang.String str2 = activityInfo7.packageName;
                com.android.internal.telephony.SmsApplication.SmsApplicationData smsApplicationData8 = (com.android.internal.telephony.SmsApplication.SmsApplicationData) hashMap.get(str2);
                if (smsApplicationData8 != null && !smsApplicationData8.isComplete()) {
                    hashMap.remove(str2);
                }
            }
        }
        return hashMap.values();
    }

    public static com.android.internal.telephony.SmsApplication.SmsApplicationData getApplicationForPackage(java.util.Collection<com.android.internal.telephony.SmsApplication.SmsApplicationData> collection, java.lang.String str) {
        if (str == null) {
            return null;
        }
        for (com.android.internal.telephony.SmsApplication.SmsApplicationData smsApplicationData : collection) {
            if (smsApplicationData.mPackageName.contentEquals(str)) {
                return smsApplicationData;
            }
        }
        return null;
    }

    private static com.android.internal.telephony.SmsApplication.SmsApplicationData getApplication(android.content.Context context, boolean z, int i) {
        com.android.internal.telephony.SmsApplication.SmsApplicationData smsApplicationData;
        android.telephony.TelephonyManager telephonyManager = (android.telephony.TelephonyManager) context.getSystemService("phone");
        android.app.role.RoleManager roleManager = (android.app.role.RoleManager) context.getSystemService(android.content.Context.ROLE_SERVICE);
        if (!telephonyManager.isSmsCapable() && (roleManager == null || !roleManager.isRoleAvailable("android.app.role.SMS"))) {
            return null;
        }
        java.util.Collection<com.android.internal.telephony.SmsApplication.SmsApplicationData> applicationCollectionInternal = getApplicationCollectionInternal(context, i);
        java.lang.String defaultSmsPackage = getDefaultSmsPackage(context, i);
        if (defaultSmsPackage == null) {
            smsApplicationData = null;
        } else {
            smsApplicationData = getApplicationForPackage(applicationCollectionInternal, defaultSmsPackage);
        }
        if (smsApplicationData == null) {
            return smsApplicationData;
        }
        com.android.internal.telephony.SmsApplication.SmsApplicationData smsApplicationData2 = (!(z || smsApplicationData.mUid == android.os.Process.myUid()) || tryFixExclusiveSmsAppops(context, smsApplicationData, z)) ? smsApplicationData : null;
        if (smsApplicationData2 != null && z) {
            defaultSmsAppChanged(context);
        }
        return smsApplicationData2;
    }

    private static java.lang.String getDefaultSmsPackage(android.content.Context context, int i) {
        return ((android.app.role.RoleManager) context.getSystemService(android.app.role.RoleManager.class)).getSmsRoleHolder(i);
    }

    private static void defaultSmsAppChanged(android.content.Context context) {
        android.content.pm.PackageManager packageManager = context.getPackageManager();
        android.app.AppOpsManager appOpsManager = (android.app.AppOpsManager) context.getSystemService(android.app.AppOpsManager.class);
        java.lang.String string = context.getResources().getString(17039427);
        assignExclusiveSmsPermissionsToSystemApp(context, packageManager, appOpsManager, "com.android.phone", true);
        assignExclusiveSmsPermissionsToSystemApp(context, packageManager, appOpsManager, string, false);
        assignExclusiveSmsPermissionsToSystemApp(context, packageManager, appOpsManager, MMS_SERVICE_PACKAGE_NAME, true);
        assignExclusiveSmsPermissionsToSystemApp(context, packageManager, appOpsManager, TELEPHONY_PROVIDER_PACKAGE_NAME, true);
        assignExclusiveSmsPermissionsToSystemApp(context, packageManager, appOpsManager, com.android.internal.telephony.CellBroadcastUtils.getDefaultCellBroadcastReceiverPackageName(context), false);
        for (java.lang.String str : DEFAULT_APP_EXCLUSIVE_APPOPS) {
            appOpsManager.setUidMode(str, 1001, 0);
        }
    }

    private static boolean tryFixExclusiveSmsAppops(android.content.Context context, com.android.internal.telephony.SmsApplication.SmsApplicationData smsApplicationData, boolean z) {
        android.app.AppOpsManager appOpsManager = (android.app.AppOpsManager) context.getSystemService(android.app.AppOpsManager.class);
        for (java.lang.String str : DEFAULT_APP_EXCLUSIVE_APPOPS) {
            if (appOpsManager.unsafeCheckOp(str, smsApplicationData.mUid, smsApplicationData.mPackageName) != 0) {
                android.util.Log.e(LOG_TAG, smsApplicationData.mPackageName + " lost " + str + ": " + (z ? " (fixing)" : " (no permission to fix)"));
                if (!z) {
                    return false;
                }
                appOpsManager.setUidMode(str, smsApplicationData.mUid, 0);
            }
        }
        return true;
    }

    public static void setDefaultApplication(java.lang.String str, android.content.Context context) {
        setDefaultApplicationAsUser(str, context, getIncomingUserId());
    }

    public static void setDefaultApplicationAsUser(java.lang.String str, android.content.Context context, int i) {
        android.telephony.TelephonyManager telephonyManager = (android.telephony.TelephonyManager) context.getSystemService("phone");
        android.app.role.RoleManager roleManager = (android.app.role.RoleManager) context.getSystemService(android.content.Context.ROLE_SERVICE);
        if (!telephonyManager.isSmsCapable() && (roleManager == null || !roleManager.isRoleAvailable("android.app.role.SMS"))) {
            return;
        }
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            setDefaultApplicationInternal(str, context, i);
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    private static void setDefaultApplicationInternal(java.lang.String str, android.content.Context context, int i) {
        android.os.UserHandle of = android.os.UserHandle.of(i);
        java.lang.String defaultSmsPackage = getDefaultSmsPackage(context, i);
        if (str != null && defaultSmsPackage != null && str.equals(defaultSmsPackage)) {
            return;
        }
        android.content.pm.PackageManager packageManager = context.createContextAsUser(of, 0).getPackageManager();
        java.util.Collection<com.android.internal.telephony.SmsApplication.SmsApplicationData> applicationCollectionInternal = getApplicationCollectionInternal(context, i);
        if (defaultSmsPackage != null) {
            getApplicationForPackage(applicationCollectionInternal, defaultSmsPackage);
        }
        com.android.internal.telephony.SmsApplication.SmsApplicationData applicationForPackage = getApplicationForPackage(applicationCollectionInternal, str);
        if (applicationForPackage != null) {
            android.app.AppOpsManager appOpsManager = (android.app.AppOpsManager) context.getSystemService(android.content.Context.APP_OPS_SERVICE);
            if (defaultSmsPackage != null) {
                try {
                    setExclusiveAppops(defaultSmsPackage, appOpsManager, packageManager.getPackageInfo(defaultSmsPackage, 0).applicationInfo.uid, 3);
                } catch (android.content.pm.PackageManager.NameNotFoundException e) {
                    android.util.Log.w(LOG_TAG, "Old SMS package not found: " + defaultSmsPackage);
                }
            }
            final java.util.concurrent.CompletableFuture completableFuture = new java.util.concurrent.CompletableFuture();
            ((android.app.role.RoleManager) context.getSystemService(android.app.role.RoleManager.class)).addRoleHolderAsUser("android.app.role.SMS", applicationForPackage.mPackageName, 0, android.os.UserHandle.of(i), android.os.AsyncTask.THREAD_POOL_EXECUTOR, new java.util.function.Consumer() { // from class: com.android.internal.telephony.SmsApplication$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    com.android.internal.telephony.SmsApplication.lambda$setDefaultApplicationInternal$0(completableFuture, (java.lang.Boolean) obj);
                }
            });
            try {
                completableFuture.get(5L, java.util.concurrent.TimeUnit.SECONDS);
                defaultSmsAppChanged(context);
            } catch (java.lang.InterruptedException | java.util.concurrent.ExecutionException | java.util.concurrent.TimeoutException e2) {
                android.util.Log.e(LOG_TAG, "Exception while adding sms role holder " + applicationForPackage, e2);
            }
        }
    }

    static /* synthetic */ void lambda$setDefaultApplicationInternal$0(java.util.concurrent.CompletableFuture completableFuture, java.lang.Boolean bool) {
        if (bool.booleanValue()) {
            completableFuture.complete(null);
        } else {
            completableFuture.completeExceptionally(new java.lang.RuntimeException());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void broadcastSmsAppChange(android.content.Context context, android.os.UserHandle userHandle, java.lang.String str, java.lang.String str2) {
        java.util.Collection<com.android.internal.telephony.SmsApplication.SmsApplicationData> applicationCollection = getApplicationCollection(context);
        broadcastSmsAppChange(context, userHandle, getApplicationForPackage(applicationCollection, str), getApplicationForPackage(applicationCollection, str2));
    }

    private static void broadcastSmsAppChange(android.content.Context context, android.os.UserHandle userHandle, com.android.internal.telephony.SmsApplication.SmsApplicationData smsApplicationData, com.android.internal.telephony.SmsApplication.SmsApplicationData smsApplicationData2) {
        if (smsApplicationData != null && smsApplicationData.mSmsAppChangedReceiverClass != null) {
            android.content.Intent intent = new android.content.Intent(android.provider.Telephony.Sms.Intents.ACTION_DEFAULT_SMS_PACKAGE_CHANGED);
            intent.setComponent(new android.content.ComponentName(smsApplicationData.mPackageName, smsApplicationData.mSmsAppChangedReceiverClass));
            intent.putExtra(android.provider.Telephony.Sms.Intents.EXTRA_IS_DEFAULT_SMS_APP, false);
            context.sendBroadcastAsUser(intent, userHandle);
        }
        if (smsApplicationData2 != null && smsApplicationData2.mSmsAppChangedReceiverClass != null) {
            android.content.Intent intent2 = new android.content.Intent(android.provider.Telephony.Sms.Intents.ACTION_DEFAULT_SMS_PACKAGE_CHANGED);
            intent2.setComponent(new android.content.ComponentName(smsApplicationData2.mPackageName, smsApplicationData2.mSmsAppChangedReceiverClass));
            intent2.putExtra(android.provider.Telephony.Sms.Intents.EXTRA_IS_DEFAULT_SMS_APP, true);
            context.sendBroadcastAsUser(intent2, userHandle);
        }
        context.sendBroadcastAsUser(new android.content.Intent(ACTION_DEFAULT_SMS_PACKAGE_CHANGED_INTERNAL), userHandle, "android.permission.MONITOR_DEFAULT_SMS_PACKAGE");
    }

    private static void assignExclusiveSmsPermissionsToSystemApp(android.content.Context context, android.content.pm.PackageManager packageManager, android.app.AppOpsManager appOpsManager, java.lang.String str, boolean z) {
        if (str == null) {
            return;
        }
        if (z && packageManager.checkSignatures(context.getPackageName(), str) != 0) {
            android.util.Log.e(LOG_TAG, str + " does not have system signature");
            return;
        }
        try {
            android.content.pm.PackageInfo packageInfo = packageManager.getPackageInfo(str, 0);
            if (appOpsManager.unsafeCheckOp(android.app.AppOpsManager.OPSTR_WRITE_SMS, packageInfo.applicationInfo.uid, str) != 0) {
                android.util.Log.w(LOG_TAG, str + " does not have OP_WRITE_SMS:  (fixing)");
                setExclusiveAppops(str, appOpsManager, packageInfo.applicationInfo.uid, 0);
            }
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            android.util.Log.e(LOG_TAG, "Package not found: " + str);
        }
    }

    private static void setExclusiveAppops(java.lang.String str, android.app.AppOpsManager appOpsManager, int i, int i2) {
        for (java.lang.String str2 : DEFAULT_APP_EXCLUSIVE_APPOPS) {
            appOpsManager.setUidMode(str2, i, i2);
        }
    }

    private static final class SmsPackageMonitor extends com.android.internal.telephony.PackageChangeReceiver {
        final android.content.Context mContext;

        public SmsPackageMonitor(android.content.Context context) {
            this.mContext = context;
        }

        @Override // com.android.internal.telephony.PackageChangeReceiver
        public void onPackageDisappeared() {
            onPackageChanged();
        }

        @Override // com.android.internal.telephony.PackageChangeReceiver
        public void onPackageAppeared() {
            onPackageChanged();
        }

        @Override // com.android.internal.telephony.PackageChangeReceiver
        public void onPackageModified(java.lang.String str) {
            onPackageChanged();
        }

        private void onPackageChanged() {
            int identifier;
            try {
                identifier = getSendingUser().getIdentifier();
            } catch (java.lang.NullPointerException e) {
                identifier = android.os.UserHandle.SYSTEM.getIdentifier();
            }
            android.content.Context context = this.mContext;
            if (identifier != android.os.UserHandle.SYSTEM.getIdentifier()) {
                try {
                    context = this.mContext.createPackageContextAsUser(this.mContext.getPackageName(), 0, android.os.UserHandle.of(identifier));
                } catch (android.content.pm.PackageManager.NameNotFoundException e2) {
                }
            }
            android.content.pm.PackageManager packageManager = context.getPackageManager();
            android.content.ComponentName defaultSendToApplication = com.android.internal.telephony.SmsApplication.getDefaultSendToApplication(context, true);
            if (defaultSendToApplication != null) {
                com.android.internal.telephony.SmsApplication.configurePreferredActivity(packageManager, defaultSendToApplication);
            }
        }
    }

    private static final class SmsRoleListener implements android.app.role.OnRoleHoldersChangedListener {
        private final android.content.Context mContext;
        private final android.app.role.RoleManager mRoleManager;
        private final android.util.SparseArray<java.lang.String> mSmsPackageNames = new android.util.SparseArray<>();

        public SmsRoleListener(android.content.Context context) {
            this.mContext = context;
            this.mRoleManager = (android.app.role.RoleManager) context.getSystemService(android.app.role.RoleManager.class);
            java.util.List<android.os.UserHandle> userHandles = ((android.os.UserManager) context.getSystemService(android.os.UserManager.class)).getUserHandles(true);
            int size = userHandles.size();
            for (int i = 0; i < size; i++) {
                android.os.UserHandle userHandle = userHandles.get(i);
                this.mSmsPackageNames.put(userHandle.getIdentifier(), getSmsPackageName(userHandle));
            }
            this.mRoleManager.addOnRoleHoldersChangedListenerAsUser(context.getMainExecutor(), this, android.os.UserHandle.ALL);
        }

        public void onRoleHoldersChanged(java.lang.String str, android.os.UserHandle userHandle) {
            if (!java.util.Objects.equals(str, "android.app.role.SMS")) {
                return;
            }
            int identifier = userHandle.getIdentifier();
            java.lang.String smsPackageName = getSmsPackageName(userHandle);
            com.android.internal.telephony.SmsApplication.broadcastSmsAppChange(this.mContext, userHandle, this.mSmsPackageNames.get(identifier), smsPackageName);
            this.mSmsPackageNames.put(identifier, smsPackageName);
        }

        private java.lang.String getSmsPackageName(android.os.UserHandle userHandle) {
            java.util.List roleHoldersAsUser = this.mRoleManager.getRoleHoldersAsUser("android.app.role.SMS", userHandle);
            if (roleHoldersAsUser.isEmpty()) {
                return null;
            }
            return (java.lang.String) roleHoldersAsUser.get(0);
        }
    }

    public static void initSmsPackageMonitor(android.content.Context context) {
        sSmsPackageMonitor = new com.android.internal.telephony.SmsApplication.SmsPackageMonitor(context);
        sSmsPackageMonitor.register(context, context.getMainLooper(), android.os.UserHandle.ALL);
        sSmsRoleListener = new com.android.internal.telephony.SmsApplication.SmsRoleListener(context);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void configurePreferredActivity(android.content.pm.PackageManager packageManager, android.content.ComponentName componentName) {
        replacePreferredActivity(packageManager, componentName, "sms");
        replacePreferredActivity(packageManager, componentName, SCHEME_SMSTO);
        replacePreferredActivity(packageManager, componentName, "mms");
        replacePreferredActivity(packageManager, componentName, SCHEME_MMSTO);
    }

    private static void replacePreferredActivity(android.content.pm.PackageManager packageManager, android.content.ComponentName componentName, java.lang.String str) {
        java.util.List<android.content.ComponentName> list = (java.util.List) packageManager.queryIntentActivities(new android.content.Intent(android.content.Intent.ACTION_SENDTO, android.net.Uri.fromParts(str, "", null)), 65600).stream().map(new java.util.function.Function() { // from class: com.android.internal.telephony.SmsApplication$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final java.lang.Object apply(java.lang.Object obj) {
                return com.android.internal.telephony.SmsApplication.lambda$replacePreferredActivity$1((android.content.pm.ResolveInfo) obj);
            }
        }).collect(java.util.stream.Collectors.toList());
        android.content.IntentFilter intentFilter = new android.content.IntentFilter();
        intentFilter.addAction(android.content.Intent.ACTION_SENDTO);
        intentFilter.addCategory(android.content.Intent.CATEGORY_DEFAULT);
        intentFilter.addDataScheme(str);
        packageManager.replacePreferredActivity(intentFilter, 2129920, list, componentName);
    }

    static /* synthetic */ android.content.ComponentName lambda$replacePreferredActivity$1(android.content.pm.ResolveInfo resolveInfo) {
        return new android.content.ComponentName(resolveInfo.activityInfo.packageName, resolveInfo.activityInfo.name);
    }

    public static com.android.internal.telephony.SmsApplication.SmsApplicationData getSmsApplicationData(java.lang.String str, android.content.Context context) {
        return getApplicationForPackage(getApplicationCollection(context), str);
    }

    public static android.content.ComponentName getDefaultSmsApplication(android.content.Context context, boolean z) {
        return getDefaultSmsApplicationAsUser(context, z, getIncomingUserHandle());
    }

    public static android.content.ComponentName getDefaultSmsApplicationAsUser(android.content.Context context, boolean z, android.os.UserHandle userHandle) {
        android.content.ComponentName componentName;
        if (userHandle == null) {
            userHandle = getIncomingUserHandle();
        }
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            com.android.internal.telephony.SmsApplication.SmsApplicationData application = getApplication(context, z, userHandle.getIdentifier());
            if (application == null) {
                componentName = null;
            } else {
                componentName = new android.content.ComponentName(application.mPackageName, application.mSmsReceiverClass);
            }
            return componentName;
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public static android.content.ComponentName getDefaultMmsApplication(android.content.Context context, boolean z) {
        return getDefaultMmsApplicationAsUser(context, z, getIncomingUserHandle());
    }

    public static android.content.ComponentName getDefaultMmsApplicationAsUser(android.content.Context context, boolean z, android.os.UserHandle userHandle) {
        android.content.ComponentName componentName;
        if (userHandle == null) {
            userHandle = getIncomingUserHandle();
        }
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            com.android.internal.telephony.SmsApplication.SmsApplicationData application = getApplication(context, z, userHandle.getIdentifier());
            if (application == null) {
                componentName = null;
            } else {
                componentName = new android.content.ComponentName(application.mPackageName, application.mMmsReceiverClass);
            }
            return componentName;
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public static android.content.ComponentName getDefaultRespondViaMessageApplication(android.content.Context context, boolean z) {
        return getDefaultRespondViaMessageApplicationAsUser(context, z, getIncomingUserHandle());
    }

    public static android.content.ComponentName getDefaultRespondViaMessageApplicationAsUser(android.content.Context context, boolean z, android.os.UserHandle userHandle) {
        android.content.ComponentName componentName;
        if (userHandle == null) {
            userHandle = getIncomingUserHandle();
        }
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            com.android.internal.telephony.SmsApplication.SmsApplicationData application = getApplication(context, z, userHandle.getIdentifier());
            if (application == null) {
                componentName = null;
            } else {
                componentName = new android.content.ComponentName(application.mPackageName, application.mRespondViaMessageClass);
            }
            return componentName;
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public static android.content.ComponentName getDefaultSendToApplication(android.content.Context context, boolean z) {
        android.content.ComponentName componentName;
        int incomingUserId = getIncomingUserId();
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            com.android.internal.telephony.SmsApplication.SmsApplicationData application = getApplication(context, z, incomingUserId);
            if (application == null) {
                componentName = null;
            } else {
                componentName = new android.content.ComponentName(application.mPackageName, application.mSendToClass);
            }
            return componentName;
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public static android.content.ComponentName getDefaultExternalTelephonyProviderChangedApplication(android.content.Context context, boolean z) {
        return getDefaultExternalTelephonyProviderChangedApplicationAsUser(context, z, getIncomingUserHandle());
    }

    public static android.content.ComponentName getDefaultExternalTelephonyProviderChangedApplicationAsUser(android.content.Context context, boolean z, android.os.UserHandle userHandle) {
        android.content.ComponentName componentName;
        if (userHandle == null) {
            userHandle = getIncomingUserHandle();
        }
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            com.android.internal.telephony.SmsApplication.SmsApplicationData application = getApplication(context, z, userHandle.getIdentifier());
            if (application != null && application.mProviderChangedReceiverClass != null) {
                componentName = new android.content.ComponentName(application.mPackageName, application.mProviderChangedReceiverClass);
            } else {
                componentName = null;
            }
            return componentName;
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public static android.content.ComponentName getDefaultSimFullApplication(android.content.Context context, boolean z) {
        return getDefaultSimFullApplicationAsUser(context, z, getIncomingUserHandle());
    }

    public static android.content.ComponentName getDefaultSimFullApplicationAsUser(android.content.Context context, boolean z, android.os.UserHandle userHandle) {
        android.content.ComponentName componentName;
        if (userHandle == null) {
            userHandle = getIncomingUserHandle();
        }
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            com.android.internal.telephony.SmsApplication.SmsApplicationData application = getApplication(context, z, userHandle.getIdentifier());
            if (application != null && application.mSimFullReceiverClass != null) {
                componentName = new android.content.ComponentName(application.mPackageName, application.mSimFullReceiverClass);
            } else {
                componentName = null;
            }
            return componentName;
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public static boolean shouldWriteMessageForPackage(java.lang.String str, android.content.Context context) {
        return !shouldWriteMessageForPackageAsUser(str, context, getIncomingUserHandle());
    }

    public static boolean shouldWriteMessageForPackageAsUser(java.lang.String str, android.content.Context context, android.os.UserHandle userHandle) {
        return !isDefaultSmsApplicationAsUser(context, str, userHandle);
    }

    public static boolean isDefaultSmsApplication(android.content.Context context, java.lang.String str) {
        return isDefaultSmsApplicationAsUser(context, str, getIncomingUserHandle());
    }

    public static boolean isDefaultSmsApplicationAsUser(android.content.Context context, java.lang.String str, android.os.UserHandle userHandle) {
        java.lang.String packageName;
        if (str == null) {
            return false;
        }
        if (userHandle == null) {
            userHandle = getIncomingUserHandle();
        }
        android.content.ComponentName defaultSmsApplicationAsUser = getDefaultSmsApplicationAsUser(context, false, userHandle);
        if (defaultSmsApplicationAsUser == null || (packageName = defaultSmsApplicationAsUser.getPackageName()) == null) {
            return false;
        }
        java.lang.String string = context.getResources().getString(17039427);
        if (!packageName.equals(str) && !string.equals(str)) {
            return false;
        }
        return true;
    }

    public static boolean isDefaultMmsApplication(android.content.Context context, java.lang.String str) {
        return isDefaultMmsApplicationAsUser(context, str, getIncomingUserHandle());
    }

    public static boolean isDefaultMmsApplicationAsUser(android.content.Context context, java.lang.String str, android.os.UserHandle userHandle) {
        java.lang.String packageName;
        if (str == null) {
            return false;
        }
        if (userHandle == null) {
            userHandle = getIncomingUserHandle();
        }
        android.content.ComponentName defaultMmsApplicationAsUser = getDefaultMmsApplicationAsUser(context, false, userHandle);
        if (defaultMmsApplicationAsUser == null || (packageName = defaultMmsApplicationAsUser.getPackageName()) == null) {
            return false;
        }
        java.lang.String string = context.getResources().getString(17039427);
        if (!packageName.equals(str) && !string.equals(str)) {
            return false;
        }
        return true;
    }
}
