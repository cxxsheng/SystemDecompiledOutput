package com.android.server.policy;

/* loaded from: classes2.dex */
public final class AppOpsPolicy implements android.app.AppOpsManagerInternal.CheckOpsDelegate {
    private static final java.lang.String ACTIVITY_RECOGNITION_TAGS = "android:activity_recognition_allow_listed_tags";
    private static final java.lang.String ACTIVITY_RECOGNITION_TAGS_SEPARATOR = ";";
    private static final java.lang.String LOG_TAG = com.android.server.policy.AppOpsPolicy.class.getName();
    private static final boolean SYSPROP_HOTWORD_DETECTION_SERVICE_REQUIRED = android.os.SystemProperties.getBoolean("ro.hotword.detection_service_required", false);

    @android.annotation.NonNull
    private final android.content.Context mContext;
    private final boolean mIsHotwordDetectionServiceRequired;

    @android.annotation.NonNull
    private final android.app.role.RoleManager mRoleManager;

    @android.annotation.NonNull
    private final java.lang.Object mLock = new java.lang.Object();

    @android.annotation.NonNull
    private final android.os.IBinder mToken = new android.os.Binder();

    @android.annotation.NonNull
    @com.android.internal.annotations.GuardedBy({"mLock - writes only - see above"})
    private final java.util.concurrent.ConcurrentHashMap<java.lang.Integer, android.os.PackageTagsList> mLocationTags = new java.util.concurrent.ConcurrentHashMap<>();

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final android.util.SparseArray<android.os.PackageTagsList> mPerUidLocationTags = new android.util.SparseArray<>();

    @android.annotation.NonNull
    @com.android.internal.annotations.GuardedBy({"mLock - writes only - see above"})
    private final java.util.concurrent.ConcurrentHashMap<java.lang.Integer, android.os.PackageTagsList> mActivityRecognitionTags = new java.util.concurrent.ConcurrentHashMap<>();

    @android.annotation.NonNull
    private final android.service.voice.VoiceInteractionManagerInternal mVoiceInteractionManagerInternal = (android.service.voice.VoiceInteractionManagerInternal) com.android.server.LocalServices.getService(android.service.voice.VoiceInteractionManagerInternal.class);

    public AppOpsPolicy(@android.annotation.NonNull android.content.Context context) {
        this.mContext = context;
        this.mRoleManager = (android.app.role.RoleManager) this.mContext.getSystemService(android.app.role.RoleManager.class);
        this.mIsHotwordDetectionServiceRequired = isHotwordDetectionServiceRequired(this.mContext.getPackageManager());
        ((android.location.LocationManagerInternal) com.android.server.LocalServices.getService(android.location.LocationManagerInternal.class)).setLocationPackageTagsListener(new android.location.LocationManagerInternal.LocationPackageTagsListener() { // from class: com.android.server.policy.AppOpsPolicy$$ExternalSyntheticLambda0
            public final void onLocationPackageTagsChanged(int i, android.os.PackageTagsList packageTagsList) {
                com.android.server.policy.AppOpsPolicy.this.lambda$new$0(i, packageTagsList);
            }
        });
        android.content.IntentFilter intentFilter = new android.content.IntentFilter();
        intentFilter.addAction("android.intent.action.PACKAGE_ADDED");
        intentFilter.addAction("android.intent.action.PACKAGE_CHANGED");
        intentFilter.addDataScheme(com.android.server.pm.Settings.ATTR_PACKAGE);
        context.registerReceiverAsUser(new android.content.BroadcastReceiver() { // from class: com.android.server.policy.AppOpsPolicy.1
            @Override // android.content.BroadcastReceiver
            public void onReceive(android.content.Context context2, android.content.Intent intent) {
                android.net.Uri data = intent.getData();
                if (data == null) {
                    return;
                }
                java.lang.String schemeSpecificPart = data.getSchemeSpecificPart();
                if (!android.text.TextUtils.isEmpty(schemeSpecificPart) && com.android.server.policy.AppOpsPolicy.this.mRoleManager.getRoleHolders("android.app.role.SYSTEM_ACTIVITY_RECOGNIZER").contains(schemeSpecificPart)) {
                    com.android.server.policy.AppOpsPolicy.this.updateActivityRecognizerTags(schemeSpecificPart);
                }
            }
        }, android.os.UserHandle.SYSTEM, intentFilter, null, null);
        this.mRoleManager.addOnRoleHoldersChangedListenerAsUser(context.getMainExecutor(), new android.app.role.OnRoleHoldersChangedListener() { // from class: com.android.server.policy.AppOpsPolicy$$ExternalSyntheticLambda1
            public final void onRoleHoldersChanged(java.lang.String str, android.os.UserHandle userHandle) {
                com.android.server.policy.AppOpsPolicy.this.lambda$new$1(str, userHandle);
            }
        }, android.os.UserHandle.SYSTEM);
        initializeActivityRecognizersTags();
        android.content.pm.PackageManager packageManager = this.mContext.getPackageManager();
        if (!packageManager.hasSystemFeature("android.hardware.telephony") && !packageManager.hasSystemFeature("android.hardware.microphone") && !packageManager.hasSystemFeature("android.software.telecom")) {
            android.app.AppOpsManager appOpsManager = (android.app.AppOpsManager) this.mContext.getSystemService(android.app.AppOpsManager.class);
            appOpsManager.setUserRestrictionForUser(100, true, this.mToken, null, -1);
            appOpsManager.setUserRestrictionForUser(101, true, this.mToken, null, -1);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(int i, android.os.PackageTagsList packageTagsList) {
        synchronized (this.mLock) {
            try {
                if (packageTagsList.isEmpty()) {
                    this.mPerUidLocationTags.remove(i);
                } else {
                    this.mPerUidLocationTags.set(i, packageTagsList);
                }
                int appId = android.os.UserHandle.getAppId(i);
                android.os.PackageTagsList.Builder builder = new android.os.PackageTagsList.Builder(1);
                int size = this.mPerUidLocationTags.size();
                for (int i2 = 0; i2 < size; i2++) {
                    if (android.os.UserHandle.getAppId(this.mPerUidLocationTags.keyAt(i2)) == appId) {
                        builder.add(this.mPerUidLocationTags.valueAt(i2));
                    }
                }
                updateAllowListedTagsForPackageLocked(appId, builder.build(), this.mLocationTags);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$1(java.lang.String str, android.os.UserHandle userHandle) {
        if ("android.app.role.SYSTEM_ACTIVITY_RECOGNIZER".equals(str)) {
            initializeActivityRecognizersTags();
        }
    }

    public static int getVoiceActivationOp() {
        if (android.permission.flags.Flags.voiceActivationPermissionApis()) {
            return 136;
        }
        return 102;
    }

    public static boolean isHotwordDetectionServiceRequired(android.content.pm.PackageManager packageManager) {
        if (packageManager.hasSystemFeature("android.hardware.type.automotive") || packageManager.hasSystemFeature("android.software.leanback")) {
            return false;
        }
        return SYSPROP_HOTWORD_DETECTION_SERVICE_REQUIRED;
    }

    public int checkOperation(int i, int i2, java.lang.String str, @android.annotation.Nullable java.lang.String str2, int i3, boolean z, com.android.internal.util.function.HexFunction<java.lang.Integer, java.lang.Integer, java.lang.String, java.lang.String, java.lang.Integer, java.lang.Boolean, java.lang.Integer> hexFunction) {
        return ((java.lang.Integer) hexFunction.apply(java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(resolveUid(i, i2)), str, str2, java.lang.Integer.valueOf(i3), java.lang.Boolean.valueOf(z))).intValue();
    }

    public int checkAudioOperation(int i, int i2, int i3, java.lang.String str, com.android.internal.util.function.QuadFunction<java.lang.Integer, java.lang.Integer, java.lang.Integer, java.lang.String, java.lang.Integer> quadFunction) {
        return ((java.lang.Integer) quadFunction.apply(java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2), java.lang.Integer.valueOf(i3), str)).intValue();
    }

    public android.app.SyncNotedAppOp noteOperation(int i, int i2, @android.annotation.Nullable java.lang.String str, @android.annotation.Nullable java.lang.String str2, int i3, boolean z, @android.annotation.Nullable java.lang.String str3, boolean z2, @android.annotation.NonNull com.android.internal.util.function.OctFunction<java.lang.Integer, java.lang.Integer, java.lang.String, java.lang.String, java.lang.Integer, java.lang.Boolean, java.lang.String, java.lang.Boolean, android.app.SyncNotedAppOp> octFunction) {
        return (android.app.SyncNotedAppOp) octFunction.apply(java.lang.Integer.valueOf(resolveDatasourceOp(i, i2, str, str2)), java.lang.Integer.valueOf(resolveUid(i, i2)), str, str2, java.lang.Integer.valueOf(i3), java.lang.Boolean.valueOf(z), str3, java.lang.Boolean.valueOf(z2));
    }

    public android.app.SyncNotedAppOp noteProxyOperation(int i, @android.annotation.NonNull android.content.AttributionSource attributionSource, boolean z, @android.annotation.Nullable java.lang.String str, boolean z2, boolean z3, @android.annotation.NonNull com.android.internal.util.function.HexFunction<java.lang.Integer, android.content.AttributionSource, java.lang.Boolean, java.lang.String, java.lang.Boolean, java.lang.Boolean, android.app.SyncNotedAppOp> hexFunction) {
        return (android.app.SyncNotedAppOp) hexFunction.apply(java.lang.Integer.valueOf(resolveDatasourceOp(i, attributionSource.getUid(), attributionSource.getPackageName(), attributionSource.getAttributionTag())), attributionSource, java.lang.Boolean.valueOf(z), str, java.lang.Boolean.valueOf(z2), java.lang.Boolean.valueOf(z3));
    }

    public android.app.SyncNotedAppOp startOperation(android.os.IBinder iBinder, int i, int i2, @android.annotation.Nullable java.lang.String str, @android.annotation.Nullable java.lang.String str2, int i3, boolean z, boolean z2, java.lang.String str3, boolean z3, int i4, int i5, @android.annotation.NonNull com.android.internal.util.function.DodecFunction<android.os.IBinder, java.lang.Integer, java.lang.Integer, java.lang.String, java.lang.String, java.lang.Integer, java.lang.Boolean, java.lang.Boolean, java.lang.String, java.lang.Boolean, java.lang.Integer, java.lang.Integer, android.app.SyncNotedAppOp> dodecFunction) {
        return (android.app.SyncNotedAppOp) dodecFunction.apply(iBinder, java.lang.Integer.valueOf(resolveDatasourceOp(i, i2, str, str2)), java.lang.Integer.valueOf(resolveUid(i, i2)), str, str2, java.lang.Integer.valueOf(i3), java.lang.Boolean.valueOf(z), java.lang.Boolean.valueOf(z2), str3, java.lang.Boolean.valueOf(z3), java.lang.Integer.valueOf(i4), java.lang.Integer.valueOf(i5));
    }

    public android.app.SyncNotedAppOp startProxyOperation(@android.annotation.NonNull android.os.IBinder iBinder, int i, @android.annotation.NonNull android.content.AttributionSource attributionSource, boolean z, boolean z2, java.lang.String str, boolean z3, boolean z4, int i2, int i3, int i4, @android.annotation.NonNull com.android.internal.util.function.UndecFunction<android.os.IBinder, java.lang.Integer, android.content.AttributionSource, java.lang.Boolean, java.lang.Boolean, java.lang.String, java.lang.Boolean, java.lang.Boolean, java.lang.Integer, java.lang.Integer, java.lang.Integer, android.app.SyncNotedAppOp> undecFunction) {
        return (android.app.SyncNotedAppOp) undecFunction.apply(iBinder, java.lang.Integer.valueOf(resolveDatasourceOp(i, attributionSource.getUid(), attributionSource.getPackageName(), attributionSource.getAttributionTag())), attributionSource, java.lang.Boolean.valueOf(z), java.lang.Boolean.valueOf(z2), str, java.lang.Boolean.valueOf(z3), java.lang.Boolean.valueOf(z4), java.lang.Integer.valueOf(i2), java.lang.Integer.valueOf(i3), java.lang.Integer.valueOf(i4));
    }

    public void finishOperation(android.os.IBinder iBinder, int i, int i2, java.lang.String str, java.lang.String str2, int i3, @android.annotation.NonNull com.android.internal.util.function.HexConsumer<android.os.IBinder, java.lang.Integer, java.lang.Integer, java.lang.String, java.lang.String, java.lang.Integer> hexConsumer) {
        hexConsumer.accept(iBinder, java.lang.Integer.valueOf(resolveDatasourceOp(i, i2, str, str2)), java.lang.Integer.valueOf(resolveUid(i, i2)), str, str2, java.lang.Integer.valueOf(i3));
    }

    public void finishProxyOperation(@android.annotation.NonNull android.os.IBinder iBinder, int i, @android.annotation.NonNull android.content.AttributionSource attributionSource, boolean z, @android.annotation.NonNull com.android.internal.util.function.QuadFunction<android.os.IBinder, java.lang.Integer, android.content.AttributionSource, java.lang.Boolean, java.lang.Void> quadFunction) {
        quadFunction.apply(iBinder, java.lang.Integer.valueOf(resolveDatasourceOp(i, attributionSource.getUid(), attributionSource.getPackageName(), attributionSource.getAttributionTag())), attributionSource, java.lang.Boolean.valueOf(z));
    }

    public void dumpTags(java.io.PrintWriter printWriter) {
        if (!this.mLocationTags.isEmpty()) {
            printWriter.println("  AppOps policy location tags:");
            writeTags(this.mLocationTags, printWriter);
            printWriter.println();
        }
        if (!this.mActivityRecognitionTags.isEmpty()) {
            printWriter.println("  AppOps policy activity recognition tags:");
            writeTags(this.mActivityRecognitionTags, printWriter);
            printWriter.println();
        }
    }

    private void writeTags(java.util.Map<java.lang.Integer, android.os.PackageTagsList> map, java.io.PrintWriter printWriter) {
        int i = 0;
        for (java.util.Map.Entry<java.lang.Integer, android.os.PackageTagsList> entry : map.entrySet()) {
            printWriter.print("    #");
            printWriter.print(i);
            printWriter.print(": ");
            printWriter.print(entry.getKey().toString());
            printWriter.print("=");
            entry.getValue().dump(printWriter);
            i++;
        }
    }

    private int resolveDatasourceOp(int i, int i2, @android.annotation.NonNull java.lang.String str, @android.annotation.Nullable java.lang.String str2) {
        int resolveSandboxedServiceOp = resolveSandboxedServiceOp(resolveRecordAudioOp(i, i2), i2);
        if (str2 == null) {
            return resolveSandboxedServiceOp;
        }
        int resolveLocationOp = resolveLocationOp(resolveSandboxedServiceOp);
        if (resolveLocationOp != resolveSandboxedServiceOp) {
            if (isDatasourceAttributionTag(i2, str, str2, this.mLocationTags)) {
                return resolveLocationOp;
            }
        } else {
            int resolveArOp = resolveArOp(resolveSandboxedServiceOp);
            if (resolveArOp != resolveSandboxedServiceOp && isDatasourceAttributionTag(i2, str, str2, this.mActivityRecognitionTags)) {
                return resolveArOp;
            }
        }
        return resolveSandboxedServiceOp;
    }

    private void initializeActivityRecognizersTags() {
        java.util.List roleHolders = this.mRoleManager.getRoleHolders("android.app.role.SYSTEM_ACTIVITY_RECOGNIZER");
        int size = roleHolders.size();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                updateActivityRecognizerTags((java.lang.String) roleHolders.get(i));
            }
            return;
        }
        clearActivityRecognitionTags();
    }

    private void clearActivityRecognitionTags() {
        synchronized (this.mLock) {
            this.mActivityRecognitionTags.clear();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateActivityRecognizerTags(@android.annotation.NonNull java.lang.String str) {
        android.content.Intent intent = new android.content.Intent("android.intent.action.ACTIVITY_RECOGNIZER");
        intent.setPackage(str);
        android.content.pm.ResolveInfo resolveServiceAsUser = this.mContext.getPackageManager().resolveServiceAsUser(intent, 819332, 0);
        if (resolveServiceAsUser == null || resolveServiceAsUser.serviceInfo == null) {
            android.util.Log.w(LOG_TAG, "Service recognizer doesn't handle android.intent.action.ACTIVITY_RECOGNIZER, ignoring!");
            return;
        }
        android.os.Bundle bundle = resolveServiceAsUser.serviceInfo.metaData;
        if (bundle == null) {
            return;
        }
        java.lang.String string = bundle.getString(ACTIVITY_RECOGNITION_TAGS);
        if (!android.text.TextUtils.isEmpty(string)) {
            android.os.PackageTagsList build = new android.os.PackageTagsList.Builder(1).add(resolveServiceAsUser.serviceInfo.packageName, java.util.Arrays.asList(string.split(ACTIVITY_RECOGNITION_TAGS_SEPARATOR))).build();
            synchronized (this.mLock) {
                updateAllowListedTagsForPackageLocked(android.os.UserHandle.getAppId(resolveServiceAsUser.serviceInfo.applicationInfo.uid), build, this.mActivityRecognitionTags);
            }
        }
    }

    private static void updateAllowListedTagsForPackageLocked(int i, android.os.PackageTagsList packageTagsList, java.util.concurrent.ConcurrentHashMap<java.lang.Integer, android.os.PackageTagsList> concurrentHashMap) {
        concurrentHashMap.put(java.lang.Integer.valueOf(i), packageTagsList);
    }

    private static boolean isDatasourceAttributionTag(int i, @android.annotation.NonNull java.lang.String str, @android.annotation.NonNull java.lang.String str2, @android.annotation.NonNull java.util.Map<java.lang.Integer, android.os.PackageTagsList> map) {
        android.os.PackageTagsList packageTagsList = map.get(java.lang.Integer.valueOf(android.os.UserHandle.getAppId(i)));
        return packageTagsList != null && packageTagsList.contains(str, str2);
    }

    private static int resolveLocationOp(int i) {
        switch (i) {
            case 0:
                return 109;
            case 1:
                return 108;
            default:
                return i;
        }
    }

    private static int resolveArOp(int i) {
        if (i == 79) {
            return 113;
        }
        return i;
    }

    private int resolveRecordAudioOp(int i, int i2) {
        if (i == 102) {
            if (!this.mIsHotwordDetectionServiceRequired) {
                return i;
            }
            android.service.voice.VoiceInteractionManagerInternal.HotwordDetectionServiceIdentity hotwordDetectionServiceIdentity = this.mVoiceInteractionManagerInternal.getHotwordDetectionServiceIdentity();
            if (hotwordDetectionServiceIdentity != null && i2 == hotwordDetectionServiceIdentity.getIsolatedUid()) {
                return i;
            }
            return 27;
        }
        return i;
    }

    private int resolveSandboxedServiceOp(int i, int i2) {
        if (!android.os.Process.isIsolated(i2) || (i != 27 && i != 26)) {
            return i;
        }
        android.service.voice.VoiceInteractionManagerInternal.HotwordDetectionServiceIdentity hotwordDetectionServiceIdentity = this.mVoiceInteractionManagerInternal.getHotwordDetectionServiceIdentity();
        if (hotwordDetectionServiceIdentity != null && i2 == hotwordDetectionServiceIdentity.getIsolatedUid()) {
            switch (i) {
            }
        }
        return i;
    }

    private int resolveUid(int i, int i2) {
        android.service.voice.VoiceInteractionManagerInternal.HotwordDetectionServiceIdentity hotwordDetectionServiceIdentity;
        if (android.os.Process.isIsolated(i2)) {
            if ((i == 27 || i == 102 || i == 26) && (hotwordDetectionServiceIdentity = this.mVoiceInteractionManagerInternal.getHotwordDetectionServiceIdentity()) != null && i2 == hotwordDetectionServiceIdentity.getIsolatedUid()) {
                return hotwordDetectionServiceIdentity.getOwnerUid();
            }
            return i2;
        }
        return i2;
    }
}
