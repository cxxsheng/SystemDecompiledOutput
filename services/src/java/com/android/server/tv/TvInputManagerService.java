package com.android.server.tv;

/* loaded from: classes2.dex */
public final class TvInputManagerService extends com.android.server.SystemService {
    private static final int APP_TAG_SELF = 0;
    private static final boolean DEBUG = false;
    private static final java.lang.String DVB_DIRECTORY = "/dev/dvb";
    private static final java.lang.String PERMISSION_ACCESS_WATCHED_PROGRAMS = "com.android.providers.tv.permission.ACCESS_WATCHED_PROGRAMS";
    private static final java.lang.String TAG = "TvInputManagerService";
    private static final long UPDATE_HARDWARE_TIS_BINDING_DELAY_IN_MILLIS = 10000;
    private final android.app.ActivityManager mActivityManager;
    private final android.content.Context mContext;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private int mCurrentUserId;
    private final java.util.List<java.lang.String> mExternalInputLoggingDeviceBrandNames;
    private final java.util.HashSet<java.lang.String> mExternalInputLoggingDeviceOnScreenDisplayNames;
    private boolean mExternalInputLoggingDisplayNameFilterEnabled;
    private final java.lang.Object mLock;
    private final com.android.server.tv.TvInputManagerService.MessageHandler mMessageHandler;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private java.lang.String mOnScreenInputId;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private com.android.server.tv.TvInputManagerService.SessionState mOnScreenSessionState;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final java.util.Set<java.lang.Integer> mRunningProfiles;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final java.util.Map<java.lang.String, com.android.server.tv.TvInputManagerService.SessionState> mSessionIdToSessionStateMap;
    private final com.android.server.tv.TvInputHardwareManager mTvInputHardwareManager;
    private final android.os.UserManager mUserManager;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final android.util.SparseArray<com.android.server.tv.TvInputManagerService.UserState> mUserStates;
    private static final java.util.regex.Pattern sFrontEndDevicePattern = java.util.regex.Pattern.compile("^dvb([0-9]+)\\.frontend([0-9]+)$");
    private static final java.util.regex.Pattern sAdapterDirPattern = java.util.regex.Pattern.compile("^adapter([0-9]+)$");
    private static final java.util.regex.Pattern sFrontEndInAdapterDirPattern = java.util.regex.Pattern.compile("^frontend([0-9]+)$");

    public TvInputManagerService(android.content.Context context) {
        super(context);
        this.mLock = new java.lang.Object();
        this.mCurrentUserId = 0;
        this.mOnScreenInputId = null;
        this.mOnScreenSessionState = null;
        this.mRunningProfiles = new java.util.HashSet();
        this.mUserStates = new android.util.SparseArray<>();
        this.mSessionIdToSessionStateMap = new java.util.HashMap();
        this.mExternalInputLoggingDisplayNameFilterEnabled = false;
        this.mExternalInputLoggingDeviceOnScreenDisplayNames = new java.util.HashSet<>();
        this.mExternalInputLoggingDeviceBrandNames = new java.util.ArrayList();
        this.mContext = context;
        this.mMessageHandler = new com.android.server.tv.TvInputManagerService.MessageHandler(this.mContext.getContentResolver(), com.android.server.IoThread.get().getLooper());
        this.mTvInputHardwareManager = new com.android.server.tv.TvInputHardwareManager(context, new com.android.server.tv.TvInputManagerService.HardwareListener());
        this.mActivityManager = (android.app.ActivityManager) getContext().getSystemService(com.android.server.am.HostingRecord.HOSTING_TYPE_ACTIVITY);
        this.mUserManager = (android.os.UserManager) getContext().getSystemService("user");
        synchronized (this.mLock) {
            getOrCreateUserStateLocked(this.mCurrentUserId);
        }
        initExternalInputLoggingConfigs();
    }

    @Override // com.android.server.SystemService
    public void onStart() {
        publishBinderService("tv_input", new com.android.server.tv.TvInputManagerService.BinderService());
    }

    @Override // com.android.server.SystemService
    public void onBootPhase(int i) {
        if (i == 500) {
            registerBroadcastReceivers();
        } else if (i == 600) {
            synchronized (this.mLock) {
                buildTvInputListLocked(this.mCurrentUserId, null);
                buildTvContentRatingSystemListLocked(this.mCurrentUserId);
            }
        }
        this.mTvInputHardwareManager.onBootPhase(i);
    }

    @Override // com.android.server.SystemService
    public void onUserUnlocking(@android.annotation.NonNull com.android.server.SystemService.TargetUser targetUser) {
        synchronized (this.mLock) {
            try {
                if (this.mCurrentUserId != targetUser.getUserIdentifier()) {
                    return;
                }
                buildTvInputListLocked(this.mCurrentUserId, null);
                buildTvContentRatingSystemListLocked(this.mCurrentUserId);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private void initExternalInputLoggingConfigs() {
        this.mExternalInputLoggingDisplayNameFilterEnabled = this.mContext.getResources().getBoolean(android.R.bool.config_tintNotificationActionButtons);
        if (!this.mExternalInputLoggingDisplayNameFilterEnabled) {
            return;
        }
        java.lang.String[] stringArray = this.mContext.getResources().getStringArray(android.R.array.config_toastCrossUserPackages);
        java.lang.String[] stringArray2 = this.mContext.getResources().getStringArray(android.R.array.config_tether_wifi_regexs);
        this.mExternalInputLoggingDeviceOnScreenDisplayNames.addAll(java.util.Arrays.asList(stringArray));
        this.mExternalInputLoggingDeviceBrandNames.addAll(java.util.Arrays.asList(stringArray2));
    }

    private void registerBroadcastReceivers() {
        new com.android.internal.content.PackageMonitor() { // from class: com.android.server.tv.TvInputManagerService.1
            private void buildTvInputList(java.lang.String[] strArr) {
                int changingUserId = getChangingUserId();
                synchronized (com.android.server.tv.TvInputManagerService.this.mLock) {
                    try {
                        if (com.android.server.tv.TvInputManagerService.this.mCurrentUserId == changingUserId || com.android.server.tv.TvInputManagerService.this.mRunningProfiles.contains(java.lang.Integer.valueOf(changingUserId))) {
                            com.android.server.tv.TvInputManagerService.this.buildTvInputListLocked(changingUserId, strArr);
                            com.android.server.tv.TvInputManagerService.this.buildTvContentRatingSystemListLocked(changingUserId);
                        }
                    } catch (java.lang.Throwable th) {
                        throw th;
                    }
                }
            }

            public void onPackageUpdateFinished(java.lang.String str, int i) {
                buildTvInputList(new java.lang.String[]{str});
            }

            public void onPackagesAvailable(java.lang.String[] strArr) {
                if (isReplacing()) {
                    buildTvInputList(strArr);
                }
            }

            public void onPackagesUnavailable(java.lang.String[] strArr) {
                if (isReplacing()) {
                    buildTvInputList(strArr);
                }
            }

            public void onSomePackagesChanged() {
                if (isReplacing()) {
                    return;
                }
                buildTvInputList(null);
            }

            public boolean onPackageChanged(java.lang.String str, int i, java.lang.String[] strArr) {
                return true;
            }
        }.register(this.mContext, (android.os.Looper) null, android.os.UserHandle.ALL, true);
        android.content.IntentFilter intentFilter = new android.content.IntentFilter();
        intentFilter.addAction("android.intent.action.USER_SWITCHED");
        intentFilter.addAction("android.intent.action.USER_REMOVED");
        intentFilter.addAction("android.intent.action.USER_STARTED");
        intentFilter.addAction("android.intent.action.USER_STOPPED");
        this.mContext.registerReceiverAsUser(new android.content.BroadcastReceiver() { // from class: com.android.server.tv.TvInputManagerService.2
            @Override // android.content.BroadcastReceiver
            public void onReceive(android.content.Context context, android.content.Intent intent) {
                java.lang.String action = intent.getAction();
                if ("android.intent.action.USER_SWITCHED".equals(action)) {
                    com.android.server.tv.TvInputManagerService.this.switchUser(intent.getIntExtra("android.intent.extra.user_handle", 0));
                    return;
                }
                if ("android.intent.action.USER_REMOVED".equals(action)) {
                    com.android.server.tv.TvInputManagerService.this.removeUser(intent.getIntExtra("android.intent.extra.user_handle", 0));
                    return;
                }
                if ("android.intent.action.USER_STARTED".equals(action)) {
                    com.android.server.tv.TvInputManagerService.this.startUser(intent.getIntExtra("android.intent.extra.user_handle", 0));
                } else if ("android.intent.action.USER_STOPPED".equals(action)) {
                    com.android.server.tv.TvInputManagerService.this.stopUser(intent.getIntExtra("android.intent.extra.user_handle", 0));
                }
            }
        }, android.os.UserHandle.ALL, intentFilter, null, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean hasHardwarePermission(android.content.pm.PackageManager packageManager, android.content.ComponentName componentName) {
        return packageManager.checkPermission("android.permission.TV_INPUT_HARDWARE", componentName.getPackageName()) == 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public void buildTvInputListLocked(int i, java.lang.String[] strArr) {
        int intValue;
        com.android.server.tv.TvInputManagerService.UserState orCreateUserStateLocked = getOrCreateUserStateLocked(i);
        orCreateUserStateLocked.packageSet.clear();
        android.content.pm.PackageManager packageManager = this.mContext.getPackageManager();
        java.util.List queryIntentServicesAsUser = packageManager.queryIntentServicesAsUser(new android.content.Intent("android.media.tv.TvInputService"), 132, i);
        java.util.ArrayList<android.media.tv.TvInputInfo> arrayList = new java.util.ArrayList();
        java.util.Iterator it = queryIntentServicesAsUser.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            android.content.pm.ResolveInfo resolveInfo = (android.content.pm.ResolveInfo) it.next();
            android.content.pm.ServiceInfo serviceInfo = resolveInfo.serviceInfo;
            if ("android.permission.BIND_TV_INPUT".equals(serviceInfo.permission)) {
                android.content.ComponentName componentName = new android.content.ComponentName(serviceInfo.packageName, serviceInfo.name);
                if (hasHardwarePermission(packageManager, componentName)) {
                    com.android.server.tv.TvInputManagerService.ServiceState serviceState = (com.android.server.tv.TvInputManagerService.ServiceState) orCreateUserStateLocked.serviceStateMap.get(componentName);
                    if (serviceState == null) {
                        orCreateUserStateLocked.serviceStateMap.put(componentName, new com.android.server.tv.TvInputManagerService.ServiceState(componentName, i));
                        updateServiceConnectionLocked(componentName, i);
                    } else {
                        arrayList.addAll(serviceState.hardwareInputMap.values());
                    }
                } else {
                    try {
                        arrayList.add(new android.media.tv.TvInputInfo.Builder(this.mContext, resolveInfo).build());
                    } catch (java.lang.Exception e) {
                        android.util.Slog.e(TAG, "failed to load TV input " + serviceInfo.name, e);
                    }
                }
                orCreateUserStateLocked.packageSet.add(serviceInfo.packageName);
            } else {
                android.util.Slog.w(TAG, "Skipping TV input " + serviceInfo.name + ": it does not require the permission android.permission.BIND_TV_INPUT");
            }
        }
        java.util.Collections.sort(arrayList, java.util.Comparator.comparing(new java.util.function.Function() { // from class: com.android.server.tv.TvInputManagerService$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final java.lang.Object apply(java.lang.Object obj) {
                return ((android.media.tv.TvInputInfo) obj).getId();
            }
        }));
        java.util.HashMap hashMap = new java.util.HashMap();
        android.util.ArrayMap arrayMap = new android.util.ArrayMap(hashMap.size());
        for (android.media.tv.TvInputInfo tvInputInfo : arrayList) {
            java.lang.String id = tvInputInfo.getId();
            java.lang.Integer num = (java.lang.Integer) arrayMap.get(id);
            if (num == null) {
                java.lang.Integer num2 = 1;
                intValue = num2.intValue();
            } else {
                intValue = num.intValue() + 1;
            }
            java.lang.Integer valueOf = java.lang.Integer.valueOf(intValue);
            arrayMap.put(id, valueOf);
            com.android.server.tv.TvInputManagerService.TvInputState tvInputState = (com.android.server.tv.TvInputManagerService.TvInputState) orCreateUserStateLocked.inputMap.get(id);
            if (tvInputState == null) {
                tvInputState = new com.android.server.tv.TvInputManagerService.TvInputState();
            }
            tvInputState.info = tvInputInfo;
            tvInputState.uid = getInputUid(tvInputInfo);
            hashMap.put(id, tvInputState);
            tvInputState.inputNumber = valueOf.intValue();
        }
        for (java.lang.String str : hashMap.keySet()) {
            if (!orCreateUserStateLocked.inputMap.containsKey(str)) {
                notifyInputAddedLocked(orCreateUserStateLocked, str);
            } else if (strArr != null) {
                android.content.ComponentName component = ((com.android.server.tv.TvInputManagerService.TvInputState) hashMap.get(str)).info.getComponent();
                int length = strArr.length;
                int i2 = 0;
                while (true) {
                    if (i2 < length) {
                        if (!component.getPackageName().equals(strArr[i2])) {
                            i2++;
                        } else {
                            updateServiceConnectionLocked(component, i);
                            notifyInputUpdatedLocked(orCreateUserStateLocked, str);
                            break;
                        }
                    }
                }
            }
        }
        for (java.lang.String str2 : orCreateUserStateLocked.inputMap.keySet()) {
            if (!hashMap.containsKey(str2)) {
                com.android.server.tv.TvInputManagerService.ServiceState serviceState2 = (com.android.server.tv.TvInputManagerService.ServiceState) orCreateUserStateLocked.serviceStateMap.get(((com.android.server.tv.TvInputManagerService.TvInputState) orCreateUserStateLocked.inputMap.get(str2)).info.getComponent());
                if (serviceState2 != null) {
                    abortPendingCreateSessionRequestsLocked(serviceState2, str2, i);
                }
                notifyInputRemovedLocked(orCreateUserStateLocked, str2);
            }
        }
        orCreateUserStateLocked.inputMap.clear();
        orCreateUserStateLocked.inputMap = hashMap;
    }

    private int getInputUid(android.media.tv.TvInputInfo tvInputInfo) {
        try {
            return getContext().getPackageManager().getApplicationInfo(tvInputInfo.getServiceInfo().packageName, 0).uid;
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            android.util.Slog.w(TAG, "Unable to get UID for  " + tvInputInfo, e);
            return -1;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public void buildTvContentRatingSystemListLocked(int i) {
        com.android.server.tv.TvInputManagerService.UserState orCreateUserStateLocked = getOrCreateUserStateLocked(i);
        orCreateUserStateLocked.contentRatingSystemList.clear();
        java.util.Iterator<android.content.pm.ResolveInfo> it = this.mContext.getPackageManager().queryBroadcastReceivers(new android.content.Intent("android.media.tv.action.QUERY_CONTENT_RATING_SYSTEMS"), 128).iterator();
        while (it.hasNext()) {
            android.content.pm.ActivityInfo activityInfo = it.next().activityInfo;
            android.os.Bundle bundle = activityInfo.metaData;
            if (bundle != null) {
                int i2 = bundle.getInt("android.media.tv.metadata.CONTENT_RATING_SYSTEMS");
                if (i2 == 0) {
                    android.util.Slog.w(TAG, "Missing meta-data 'android.media.tv.metadata.CONTENT_RATING_SYSTEMS' on receiver " + activityInfo.packageName + com.android.server.slice.SliceClientPermissions.SliceAuthority.DELIMITER + activityInfo.name);
                } else {
                    orCreateUserStateLocked.contentRatingSystemList.add(android.media.tv.TvContentRatingSystemInfo.createTvContentRatingSystemInfo(i2, activityInfo.applicationInfo));
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startUser(int i) {
        synchronized (this.mLock) {
            try {
                if (i == this.mCurrentUserId || this.mRunningProfiles.contains(java.lang.Integer.valueOf(i))) {
                    return;
                }
                android.content.pm.UserInfo userInfo = this.mUserManager.getUserInfo(i);
                android.content.pm.UserInfo profileParent = this.mUserManager.getProfileParent(i);
                if (userInfo.isProfile() && profileParent != null && profileParent.id == this.mCurrentUserId) {
                    startProfileLocked(i);
                }
            } finally {
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void stopUser(int i) {
        synchronized (this.mLock) {
            try {
                if (i == this.mCurrentUserId) {
                    switchUser(android.app.ActivityManager.getCurrentUser());
                    return;
                }
                releaseSessionOfUserLocked(i);
                unbindServiceOfUserLocked(i);
                this.mRunningProfiles.remove(java.lang.Integer.valueOf(i));
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void startProfileLocked(int i) {
        this.mRunningProfiles.add(java.lang.Integer.valueOf(i));
        buildTvInputListLocked(i, null);
        buildTvContentRatingSystemListLocked(i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void switchUser(int i) {
        synchronized (this.mLock) {
            try {
                if (this.mCurrentUserId == i) {
                    return;
                }
                if (this.mUserManager.getUserInfo(i).isProfile()) {
                    android.util.Slog.w(TAG, "cannot switch to a profile!");
                    return;
                }
                java.util.Iterator<java.lang.Integer> it = this.mRunningProfiles.iterator();
                while (it.hasNext()) {
                    int intValue = it.next().intValue();
                    releaseSessionOfUserLocked(intValue);
                    unbindServiceOfUserLocked(intValue);
                }
                this.mRunningProfiles.clear();
                releaseSessionOfUserLocked(this.mCurrentUserId);
                unbindServiceOfUserLocked(this.mCurrentUserId);
                this.mCurrentUserId = i;
                buildTvInputListLocked(i, null);
                buildTvContentRatingSystemListLocked(i);
                this.mMessageHandler.obtainMessage(3, getContentResolverForUser(i)).sendToTarget();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void releaseSessionOfUserLocked(int i) {
        com.android.server.tv.TvInputManagerService.UserState userStateLocked = getUserStateLocked(i);
        if (userStateLocked == null) {
            return;
        }
        java.util.ArrayList<com.android.server.tv.TvInputManagerService.SessionState> arrayList = new java.util.ArrayList();
        for (com.android.server.tv.TvInputManagerService.SessionState sessionState : userStateLocked.sessionStateMap.values()) {
            if (sessionState.session != null && !sessionState.isRecordingSession) {
                arrayList.add(sessionState);
            }
        }
        boolean z = false;
        for (com.android.server.tv.TvInputManagerService.SessionState sessionState2 : arrayList) {
            try {
                try {
                    sessionState2.session.release();
                    sessionState2.currentChannel = null;
                    if (sessionState2.isCurrent) {
                        sessionState2.isCurrent = false;
                        z = true;
                    }
                } catch (android.os.RemoteException e) {
                    android.util.Slog.e(TAG, "error in release", e);
                    if (!z) {
                    }
                }
                if (!z) {
                    clearSessionAndNotifyClientLocked(sessionState2);
                }
                notifyCurrentChannelInfosUpdatedLocked(userStateLocked);
                clearSessionAndNotifyClientLocked(sessionState2);
            } catch (java.lang.Throwable th) {
                if (z) {
                    notifyCurrentChannelInfosUpdatedLocked(userStateLocked);
                }
                throw th;
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void unbindServiceOfUserLocked(int i) {
        com.android.server.tv.TvInputManagerService.UserState userStateLocked = getUserStateLocked(i);
        if (userStateLocked == null) {
            return;
        }
        java.util.Iterator it = userStateLocked.serviceStateMap.keySet().iterator();
        while (it.hasNext()) {
            com.android.server.tv.TvInputManagerService.ServiceState serviceState = (com.android.server.tv.TvInputManagerService.ServiceState) userStateLocked.serviceStateMap.get((android.content.ComponentName) it.next());
            if (serviceState != null && serviceState.sessionTokens.isEmpty()) {
                if (serviceState.callback != null) {
                    try {
                        serviceState.service.unregisterCallback(serviceState.callback);
                    } catch (android.os.RemoteException e) {
                        android.util.Slog.e(TAG, "error in unregisterCallback", e);
                    }
                }
                unbindService(serviceState);
                it.remove();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public void clearSessionAndNotifyClientLocked(com.android.server.tv.TvInputManagerService.SessionState sessionState) {
        if (sessionState.client != null) {
            try {
                sessionState.client.onSessionReleased(sessionState.seq);
            } catch (android.os.RemoteException e) {
                android.util.Slog.e(TAG, "error in onSessionReleased", e);
            }
        }
        for (com.android.server.tv.TvInputManagerService.SessionState sessionState2 : getOrCreateUserStateLocked(sessionState.userId).sessionStateMap.values()) {
            if (sessionState.sessionToken == sessionState2.hardwareSessionToken) {
                releaseSessionLocked(sessionState2.sessionToken, 1000, sessionState.userId);
                try {
                    sessionState2.client.onSessionReleased(sessionState2.seq);
                } catch (android.os.RemoteException e2) {
                    android.util.Slog.e(TAG, "error in onSessionReleased", e2);
                }
            }
        }
        removeSessionStateLocked(sessionState.sessionToken, sessionState.userId);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void removeUser(int i) {
        synchronized (this.mLock) {
            try {
                com.android.server.tv.TvInputManagerService.UserState userStateLocked = getUserStateLocked(i);
                if (userStateLocked == null) {
                    return;
                }
                boolean z = false;
                for (com.android.server.tv.TvInputManagerService.SessionState sessionState : userStateLocked.sessionStateMap.values()) {
                    if (sessionState.session != null) {
                        try {
                            try {
                                sessionState.session.release();
                                sessionState.currentChannel = null;
                                if (sessionState.isCurrent) {
                                    sessionState.isCurrent = false;
                                    z = true;
                                }
                                if (!z) {
                                }
                            } catch (android.os.RemoteException e) {
                                android.util.Slog.e(TAG, "error in release", e);
                                if (!z) {
                                }
                            }
                            notifyCurrentChannelInfosUpdatedLocked(userStateLocked);
                        } catch (java.lang.Throwable th) {
                            if (z) {
                                notifyCurrentChannelInfosUpdatedLocked(userStateLocked);
                            }
                            throw th;
                        }
                    }
                }
                userStateLocked.sessionStateMap.clear();
                for (com.android.server.tv.TvInputManagerService.ServiceState serviceState : userStateLocked.serviceStateMap.values()) {
                    if (serviceState.service != null) {
                        if (serviceState.callback != null) {
                            try {
                                serviceState.service.unregisterCallback(serviceState.callback);
                            } catch (android.os.RemoteException e2) {
                                android.util.Slog.e(TAG, "error in unregisterCallback", e2);
                            }
                        }
                        unbindService(serviceState);
                    }
                }
                userStateLocked.serviceStateMap.clear();
                userStateLocked.inputMap.clear();
                userStateLocked.packageSet.clear();
                userStateLocked.contentRatingSystemList.clear();
                userStateLocked.clientStateMap.clear();
                userStateLocked.mCallbacks.kill();
                userStateLocked.mainSessionToken = null;
                this.mRunningProfiles.remove(java.lang.Integer.valueOf(i));
                this.mUserStates.remove(i);
                if (i == this.mCurrentUserId) {
                    switchUser(0);
                }
            } catch (java.lang.Throwable th2) {
                throw th2;
            }
        }
    }

    private android.content.ContentResolver getContentResolverForUser(int i) {
        android.content.Context context;
        android.os.UserHandle userHandle = new android.os.UserHandle(i);
        try {
            context = this.mContext.createPackageContextAsUser(com.android.server.pm.PackageManagerService.PLATFORM_PACKAGE_NAME, 0, userHandle);
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            android.util.Slog.e(TAG, "failed to create package context as user " + userHandle);
            context = this.mContext;
        }
        return context.getContentResolver();
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public com.android.server.tv.TvInputManagerService.UserState getOrCreateUserStateLocked(int i) {
        com.android.server.tv.TvInputManagerService.UserState userStateLocked = getUserStateLocked(i);
        if (userStateLocked == null) {
            com.android.server.tv.TvInputManagerService.UserState userState = new com.android.server.tv.TvInputManagerService.UserState(this.mContext, i);
            this.mUserStates.put(i, userState);
            return userState;
        }
        return userStateLocked;
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public com.android.server.tv.TvInputManagerService.ServiceState getServiceStateLocked(android.content.ComponentName componentName, int i) {
        com.android.server.tv.TvInputManagerService.ServiceState serviceState = (com.android.server.tv.TvInputManagerService.ServiceState) getOrCreateUserStateLocked(i).serviceStateMap.get(componentName);
        if (serviceState == null) {
            throw new java.lang.IllegalStateException("Service state not found for " + componentName + " (userId=" + i + ")");
        }
        return serviceState;
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public com.android.server.tv.TvInputManagerService.SessionState getSessionStateLocked(android.os.IBinder iBinder, int i, int i2) {
        return getSessionStateLocked(iBinder, i, getOrCreateUserStateLocked(i2));
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public com.android.server.tv.TvInputManagerService.SessionState getSessionStateLocked(android.os.IBinder iBinder, int i, com.android.server.tv.TvInputManagerService.UserState userState) {
        com.android.server.tv.TvInputManagerService.SessionState sessionState = (com.android.server.tv.TvInputManagerService.SessionState) userState.sessionStateMap.get(iBinder);
        if (sessionState == null) {
            throw new com.android.server.tv.TvInputManagerService.SessionNotFoundException("Session state not found for token " + iBinder);
        }
        if (i != 1000 && i != sessionState.callingUid) {
            throw new java.lang.SecurityException("Illegal access to the session with token " + iBinder + " from uid " + i);
        }
        return sessionState;
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public android.media.tv.ITvInputSession getSessionLocked(android.os.IBinder iBinder, int i, int i2) {
        return getSessionLocked(getSessionStateLocked(iBinder, i, i2));
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public android.media.tv.ITvInputSession getSessionLocked(com.android.server.tv.TvInputManagerService.SessionState sessionState) {
        android.media.tv.ITvInputSession iTvInputSession = sessionState.session;
        if (iTvInputSession == null) {
            throw new java.lang.IllegalStateException("Session not yet created for token " + sessionState.sessionToken);
        }
        return iTvInputSession;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int resolveCallingUserId(int i, int i2, int i3, java.lang.String str) {
        return android.app.ActivityManager.handleIncomingUser(i, i2, i3, false, false, str, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public void updateServiceConnectionLocked(android.content.ComponentName componentName, int i) {
        boolean z;
        com.android.server.tv.TvInputManagerService.UserState orCreateUserStateLocked = getOrCreateUserStateLocked(i);
        com.android.server.tv.TvInputManagerService.ServiceState serviceState = (com.android.server.tv.TvInputManagerService.ServiceState) orCreateUserStateLocked.serviceStateMap.get(componentName);
        if (serviceState == null) {
            return;
        }
        boolean z2 = false;
        if (serviceState.reconnecting) {
            if (!serviceState.sessionTokens.isEmpty()) {
                return;
            } else {
                serviceState.reconnecting = false;
            }
        }
        if (i == this.mCurrentUserId || this.mRunningProfiles.contains(java.lang.Integer.valueOf(i))) {
            if (!serviceState.sessionTokens.isEmpty() || (serviceState.isHardware && serviceState.neverConnected)) {
                z2 = true;
            }
            z = z2;
        } else {
            z = !serviceState.sessionTokens.isEmpty();
        }
        if (z && !serviceState.bound) {
            bindService(serviceState, i);
            return;
        }
        if (!z && serviceState.bound) {
            unbindService(serviceState);
            if (!serviceState.isHardware) {
                orCreateUserStateLocked.serviceStateMap.remove(componentName);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public void abortPendingCreateSessionRequestsLocked(com.android.server.tv.TvInputManagerService.ServiceState serviceState, java.lang.String str, int i) {
        com.android.server.tv.TvInputManagerService.UserState orCreateUserStateLocked = getOrCreateUserStateLocked(i);
        java.util.ArrayList<com.android.server.tv.TvInputManagerService.SessionState> arrayList = new java.util.ArrayList();
        java.util.Iterator it = serviceState.sessionTokens.iterator();
        while (it.hasNext()) {
            com.android.server.tv.TvInputManagerService.SessionState sessionState = (com.android.server.tv.TvInputManagerService.SessionState) orCreateUserStateLocked.sessionStateMap.get((android.os.IBinder) it.next());
            if (sessionState.session == null && (str == null || sessionState.inputId.equals(str))) {
                arrayList.add(sessionState);
            }
        }
        for (com.android.server.tv.TvInputManagerService.SessionState sessionState2 : arrayList) {
            removeSessionStateLocked(sessionState2.sessionToken, sessionState2.userId);
            sendSessionTokenToClientLocked(sessionState2.client, sessionState2.inputId, null, null, sessionState2.seq);
        }
        if (!serviceState.isHardware) {
            updateServiceConnectionLocked(serviceState.component, i);
        } else {
            updateHardwareServiceConnectionDelayed(i);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public boolean createSessionInternalLocked(android.media.tv.ITvInputService iTvInputService, android.os.IBinder iBinder, int i) {
        boolean z;
        com.android.server.tv.TvInputManagerService.SessionState sessionState = (com.android.server.tv.TvInputManagerService.SessionState) getOrCreateUserStateLocked(i).sessionStateMap.get(iBinder);
        android.view.InputChannel[] openInputChannelPair = android.view.InputChannel.openInputChannelPair(iBinder.toString());
        com.android.server.tv.TvInputManagerService.SessionCallback sessionCallback = new com.android.server.tv.TvInputManagerService.SessionCallback(sessionState, openInputChannelPair);
        try {
            if (!sessionState.isRecordingSession) {
                iTvInputService.createSession(openInputChannelPair[1], sessionCallback, sessionState.inputId, sessionState.sessionId, sessionState.tvAppAttributionSource);
            } else {
                iTvInputService.createRecordingSession(sessionCallback, sessionState.inputId, sessionState.sessionId);
            }
            z = true;
        } catch (android.os.RemoteException e) {
            android.util.Slog.e(TAG, "error in createSession", e);
            sendSessionTokenToClientLocked(sessionState.client, sessionState.inputId, null, null, sessionState.seq);
            z = false;
        }
        openInputChannelPair[1].dispose();
        return z;
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public void sendSessionTokenToClientLocked(android.media.tv.ITvInputClient iTvInputClient, java.lang.String str, android.os.IBinder iBinder, android.view.InputChannel inputChannel, int i) {
        try {
            iTvInputClient.onSessionCreated(str, iBinder, inputChannel, i);
        } catch (android.os.RemoteException e) {
            android.util.Slog.e(TAG, "error in onSessionCreated", e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code restructure failed: missing block: B:15:0x0040, code lost:
    
        if (r1 != null) goto L18;
     */
    /* JADX WARN: Code restructure failed: missing block: B:16:0x0042, code lost:
    
        r1.session = null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:18:0x0058, code lost:
    
        if (r5.mOnScreenSessionState != r1) goto L30;
     */
    /* JADX WARN: Code restructure failed: missing block: B:19:0x005a, code lost:
    
        logExternalInputEvent(2, r5.mOnScreenInputId, r1);
        r5.mOnScreenInputId = null;
        r5.mOnScreenSessionState = null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:20:0x0064, code lost:
    
        removeSessionStateLocked(r6, r8);
     */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x0067, code lost:
    
        return r1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:26:0x0053, code lost:
    
        if (r1 == null) goto L27;
     */
    /* JADX WARN: Removed duplicated region for block: B:31:0x006a  */
    @com.android.internal.annotations.GuardedBy({"mLock"})
    @android.annotation.Nullable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public com.android.server.tv.TvInputManagerService.SessionState releaseSessionLocked(android.os.IBinder iBinder, int i, int i2) {
        com.android.server.tv.TvInputManagerService.SessionState sessionState;
        try {
            sessionState = getSessionStateLocked(iBinder, i, i2);
        } catch (android.os.RemoteException | com.android.server.tv.TvInputManagerService.SessionNotFoundException e) {
            e = e;
            sessionState = null;
        } catch (java.lang.Throwable th) {
            th = th;
            sessionState = null;
            if (sessionState != null) {
            }
            throw th;
        }
        try {
            try {
                com.android.server.tv.TvInputManagerService.UserState orCreateUserStateLocked = getOrCreateUserStateLocked(i2);
                if (sessionState.session != null) {
                    if (iBinder == orCreateUserStateLocked.mainSessionToken) {
                        setMainLocked(iBinder, false, i, i2);
                    }
                    sessionState.session.asBinder().unlinkToDeath(sessionState, 0);
                    sessionState.session.release();
                }
                sessionState.currentChannel = null;
                if (sessionState.isCurrent) {
                    sessionState.isCurrent = false;
                    notifyCurrentChannelInfosUpdatedLocked(orCreateUserStateLocked);
                }
            } catch (java.lang.Throwable th2) {
                th = th2;
                if (sessionState != null) {
                    sessionState.session = null;
                }
                throw th;
            }
        } catch (android.os.RemoteException | com.android.server.tv.TvInputManagerService.SessionNotFoundException e2) {
            e = e2;
            android.util.Slog.e(TAG, "error in releaseSession", e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public void removeSessionStateLocked(android.os.IBinder iBinder, int i) {
        com.android.server.tv.TvInputManagerService.UserState orCreateUserStateLocked = getOrCreateUserStateLocked(i);
        if (iBinder == orCreateUserStateLocked.mainSessionToken) {
            orCreateUserStateLocked.mainSessionToken = null;
        }
        com.android.server.tv.TvInputManagerService.SessionState sessionState = (com.android.server.tv.TvInputManagerService.SessionState) orCreateUserStateLocked.sessionStateMap.remove(iBinder);
        if (sessionState == null) {
            android.util.Slog.e(TAG, "sessionState null, no more remove session action!");
            return;
        }
        com.android.server.tv.TvInputManagerService.ClientState clientState = (com.android.server.tv.TvInputManagerService.ClientState) orCreateUserStateLocked.clientStateMap.get(sessionState.client.asBinder());
        if (clientState != null) {
            clientState.sessionTokens.remove(iBinder);
            if (clientState.isEmpty()) {
                orCreateUserStateLocked.clientStateMap.remove(sessionState.client.asBinder());
                sessionState.client.asBinder().unlinkToDeath(clientState, 0);
            }
        }
        this.mSessionIdToSessionStateMap.remove(sessionState.sessionId);
        com.android.server.tv.TvInputManagerService.ServiceState serviceState = (com.android.server.tv.TvInputManagerService.ServiceState) orCreateUserStateLocked.serviceStateMap.get(sessionState.componentName);
        if (serviceState != null) {
            serviceState.sessionTokens.remove(iBinder);
        }
        if (!serviceState.isHardware) {
            updateServiceConnectionLocked(sessionState.componentName, i);
        } else {
            updateHardwareServiceConnectionDelayed(i);
        }
        com.android.internal.os.SomeArgs obtain = com.android.internal.os.SomeArgs.obtain();
        obtain.arg1 = iBinder;
        obtain.arg2 = java.lang.Long.valueOf(java.lang.System.currentTimeMillis());
        this.mMessageHandler.obtainMessage(2, obtain).sendToTarget();
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public void setMainLocked(android.os.IBinder iBinder, boolean z, int i, int i2) {
        try {
            com.android.server.tv.TvInputManagerService.SessionState sessionStateLocked = getSessionStateLocked(iBinder, i, i2);
            if (sessionStateLocked.hardwareSessionToken != null) {
                sessionStateLocked = getSessionStateLocked(sessionStateLocked.hardwareSessionToken, 1000, i2);
            }
            if (!getServiceStateLocked(sessionStateLocked.componentName, i2).isHardware) {
                return;
            }
            getSessionLocked(sessionStateLocked).setMain(z);
            if (sessionStateLocked.isMainSession != z) {
                com.android.server.tv.TvInputManagerService.UserState userStateLocked = getUserStateLocked(i2);
                sessionStateLocked.isMainSession = z;
                notifyCurrentChannelInfosUpdatedLocked(userStateLocked);
            }
        } catch (android.os.RemoteException | com.android.server.tv.TvInputManagerService.SessionNotFoundException e) {
            android.util.Slog.e(TAG, "error in setMain", e);
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void notifyInputAddedLocked(com.android.server.tv.TvInputManagerService.UserState userState, java.lang.String str) {
        int beginBroadcast = userState.mCallbacks.beginBroadcast();
        for (int i = 0; i < beginBroadcast; i++) {
            try {
                userState.mCallbacks.getBroadcastItem(i).onInputAdded(str);
            } catch (android.os.RemoteException e) {
                android.util.Slog.e(TAG, "failed to report added input to callback", e);
            }
        }
        userState.mCallbacks.finishBroadcast();
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void notifyInputRemovedLocked(com.android.server.tv.TvInputManagerService.UserState userState, java.lang.String str) {
        int beginBroadcast = userState.mCallbacks.beginBroadcast();
        for (int i = 0; i < beginBroadcast; i++) {
            try {
                userState.mCallbacks.getBroadcastItem(i).onInputRemoved(str);
            } catch (android.os.RemoteException e) {
                android.util.Slog.e(TAG, "failed to report removed input to callback", e);
            }
        }
        userState.mCallbacks.finishBroadcast();
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void notifyInputUpdatedLocked(com.android.server.tv.TvInputManagerService.UserState userState, java.lang.String str) {
        int beginBroadcast = userState.mCallbacks.beginBroadcast();
        for (int i = 0; i < beginBroadcast; i++) {
            try {
                userState.mCallbacks.getBroadcastItem(i).onInputUpdated(str);
            } catch (android.os.RemoteException e) {
                android.util.Slog.e(TAG, "failed to report updated input to callback", e);
            }
        }
        userState.mCallbacks.finishBroadcast();
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public void notifyInputStateChangedLocked(com.android.server.tv.TvInputManagerService.UserState userState, java.lang.String str, int i, android.media.tv.ITvInputManagerCallback iTvInputManagerCallback) {
        if (iTvInputManagerCallback == null) {
            int beginBroadcast = userState.mCallbacks.beginBroadcast();
            for (int i2 = 0; i2 < beginBroadcast; i2++) {
                try {
                    userState.mCallbacks.getBroadcastItem(i2).onInputStateChanged(str, i);
                } catch (android.os.RemoteException e) {
                    android.util.Slog.e(TAG, "failed to report state change to callback", e);
                }
            }
            userState.mCallbacks.finishBroadcast();
            return;
        }
        try {
            iTvInputManagerCallback.onInputStateChanged(str, i);
        } catch (android.os.RemoteException e2) {
            android.util.Slog.e(TAG, "failed to report state change to callback", e2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public void notifyCurrentChannelInfosUpdatedLocked(com.android.server.tv.TvInputManagerService.UserState userState) {
        int beginBroadcast = userState.mCallbacks.beginBroadcast();
        for (int i = 0; i < beginBroadcast; i++) {
            try {
                android.media.tv.ITvInputManagerCallback broadcastItem = userState.mCallbacks.getBroadcastItem(i);
                android.util.Pair pair = (android.util.Pair) userState.callbackPidUidMap.get(broadcastItem);
                if (this.mContext.checkPermission("android.permission.ACCESS_TUNED_INFO", ((java.lang.Integer) pair.first).intValue(), ((java.lang.Integer) pair.second).intValue()) == 0) {
                    broadcastItem.onCurrentTunedInfosUpdated(getCurrentTunedInfosInternalLocked(userState, ((java.lang.Integer) pair.first).intValue(), ((java.lang.Integer) pair.second).intValue()));
                }
            } catch (android.os.RemoteException e) {
                android.util.Slog.e(TAG, "failed to report updated current channel infos to callback", e);
            }
        }
        userState.mCallbacks.finishBroadcast();
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public void updateTvInputInfoLocked(com.android.server.tv.TvInputManagerService.UserState userState, android.media.tv.TvInputInfo tvInputInfo) {
        java.lang.String id = tvInputInfo.getId();
        com.android.server.tv.TvInputManagerService.TvInputState tvInputState = (com.android.server.tv.TvInputManagerService.TvInputState) userState.inputMap.get(id);
        if (tvInputState == null) {
            android.util.Slog.e(TAG, "failed to set input info - unknown input id " + id);
            return;
        }
        boolean isCurrentCecTvInputInfoUpdate = isCurrentCecTvInputInfoUpdate(userState, tvInputInfo);
        tvInputState.info = tvInputInfo;
        tvInputState.uid = getInputUid(tvInputInfo);
        com.android.server.tv.TvInputManagerService.ServiceState serviceState = (com.android.server.tv.TvInputManagerService.ServiceState) userState.serviceStateMap.get(tvInputInfo.getComponent());
        if (serviceState != null && serviceState.isHardware) {
            serviceState.hardwareInputMap.put(tvInputInfo.getId(), tvInputInfo);
            this.mTvInputHardwareManager.updateInputInfo(tvInputInfo);
        }
        if (isCurrentCecTvInputInfoUpdate) {
            logExternalInputEvent(4, this.mOnScreenInputId, this.mOnScreenSessionState);
        }
        int beginBroadcast = userState.mCallbacks.beginBroadcast();
        for (int i = 0; i < beginBroadcast; i++) {
            try {
                userState.mCallbacks.getBroadcastItem(i).onTvInputInfoUpdated(tvInputInfo);
            } catch (android.os.RemoteException e) {
                android.util.Slog.e(TAG, "failed to report updated input info to callback", e);
            }
        }
        userState.mCallbacks.finishBroadcast();
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean isCurrentCecTvInputInfoUpdate(com.android.server.tv.TvInputManagerService.UserState userState, android.media.tv.TvInputInfo tvInputInfo) {
        com.android.server.tv.TvInputManagerService.TvInputState tvInputState;
        if (tvInputInfo == null || tvInputInfo.getId() == null || !tvInputInfo.getId().equals(this.mOnScreenInputId) || tvInputInfo.getHdmiDeviceInfo() == null || !tvInputInfo.getHdmiDeviceInfo().isCecDevice() || (tvInputState = (com.android.server.tv.TvInputManagerService.TvInputState) userState.inputMap.get(this.mOnScreenInputId)) == null || tvInputState.info == null || tvInputState.info.getHdmiDeviceInfo() == null || !tvInputState.info.getHdmiDeviceInfo().isCecDevice()) {
            return false;
        }
        return (android.text.TextUtils.equals(tvInputInfo.getHdmiDeviceInfo().getDisplayName(), tvInputState.info.getHdmiDeviceInfo().getDisplayName()) && tvInputInfo.getHdmiDeviceInfo().getVendorId() == tvInputState.info.getHdmiDeviceInfo().getVendorId()) ? false : true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public void setStateLocked(java.lang.String str, int i, int i2) {
        android.media.tv.TvInputInfo tvInputInfo;
        com.android.server.tv.TvInputManagerService.UserState orCreateUserStateLocked = getOrCreateUserStateLocked(i2);
        com.android.server.tv.TvInputManagerService.TvInputState tvInputState = (com.android.server.tv.TvInputManagerService.TvInputState) orCreateUserStateLocked.inputMap.get(str);
        if (tvInputState == null) {
            android.util.Slog.e(TAG, "failed to setStateLocked - unknown input id " + str);
            return;
        }
        com.android.server.tv.TvInputManagerService.ServiceState serviceState = (com.android.server.tv.TvInputManagerService.ServiceState) orCreateUserStateLocked.serviceStateMap.get(tvInputState.info.getComponent());
        int i3 = tvInputState.state;
        tvInputState.state = i;
        if ((serviceState == null || !serviceState.reconnecting) && i3 != i) {
            if (str.equals(this.mOnScreenInputId)) {
                logExternalInputEvent(3, this.mOnScreenInputId, this.mOnScreenSessionState);
            } else if (this.mOnScreenInputId != null) {
                com.android.server.tv.TvInputManagerService.TvInputState tvInputState2 = (com.android.server.tv.TvInputManagerService.TvInputState) orCreateUserStateLocked.inputMap.get(this.mOnScreenInputId);
                if (tvInputState2 == null) {
                    tvInputInfo = null;
                } else {
                    tvInputInfo = tvInputState2.info;
                }
                if (tvInputInfo != null && tvInputInfo.getHdmiDeviceInfo() != null && str.equals(tvInputInfo.getParentId())) {
                    logExternalInputEvent(3, str, this.mOnScreenSessionState);
                    if (i == 1) {
                        this.mOnScreenInputId = tvInputInfo.getParentId();
                    }
                }
            }
            notifyInputStateChangedLocked(orCreateUserStateLocked, str, i, null);
        }
    }

    private final class BinderService extends android.media.tv.ITvInputManager.Stub {
        private BinderService() {
        }

        public java.util.List<android.media.tv.TvInputInfo> getTvInputList(int i) {
            java.util.ArrayList arrayList;
            int resolveCallingUserId = com.android.server.tv.TvInputManagerService.this.resolveCallingUserId(android.os.Binder.getCallingPid(), android.os.Binder.getCallingUid(), i, "getTvInputList");
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                synchronized (com.android.server.tv.TvInputManagerService.this.mLock) {
                    try {
                        com.android.server.tv.TvInputManagerService.UserState orCreateUserStateLocked = com.android.server.tv.TvInputManagerService.this.getOrCreateUserStateLocked(resolveCallingUserId);
                        arrayList = new java.util.ArrayList();
                        java.util.Iterator it = orCreateUserStateLocked.inputMap.values().iterator();
                        while (it.hasNext()) {
                            arrayList.add(((com.android.server.tv.TvInputManagerService.TvInputState) it.next()).info);
                        }
                    } finally {
                    }
                }
                return arrayList;
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public android.media.tv.TvInputInfo getTvInputInfo(java.lang.String str, int i) {
            android.media.tv.TvInputInfo tvInputInfo;
            int resolveCallingUserId = com.android.server.tv.TvInputManagerService.this.resolveCallingUserId(android.os.Binder.getCallingPid(), android.os.Binder.getCallingUid(), i, "getTvInputInfo");
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                synchronized (com.android.server.tv.TvInputManagerService.this.mLock) {
                    com.android.server.tv.TvInputManagerService.TvInputState tvInputState = (com.android.server.tv.TvInputManagerService.TvInputState) com.android.server.tv.TvInputManagerService.this.getOrCreateUserStateLocked(resolveCallingUserId).inputMap.get(str);
                    tvInputInfo = tvInputState == null ? null : tvInputState.info;
                }
                return tvInputInfo;
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void updateTvInputInfo(android.media.tv.TvInputInfo tvInputInfo, int i) {
            java.lang.String str = tvInputInfo.getServiceInfo().packageName;
            java.lang.String callingPackageName = getCallingPackageName();
            if (!android.text.TextUtils.equals(str, callingPackageName) && com.android.server.tv.TvInputManagerService.this.mContext.checkCallingPermission("android.permission.WRITE_SECURE_SETTINGS") != 0) {
                throw new java.lang.IllegalArgumentException("calling package " + callingPackageName + " is not allowed to change TvInputInfo for " + str);
            }
            int resolveCallingUserId = com.android.server.tv.TvInputManagerService.this.resolveCallingUserId(android.os.Binder.getCallingPid(), android.os.Binder.getCallingUid(), i, "updateTvInputInfo");
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                synchronized (com.android.server.tv.TvInputManagerService.this.mLock) {
                    com.android.server.tv.TvInputManagerService.this.updateTvInputInfoLocked(com.android.server.tv.TvInputManagerService.this.getOrCreateUserStateLocked(resolveCallingUserId), tvInputInfo);
                }
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        private java.lang.String getCallingPackageName() {
            java.lang.String[] packagesForUid = com.android.server.tv.TvInputManagerService.this.mContext.getPackageManager().getPackagesForUid(android.os.Binder.getCallingUid());
            if (packagesForUid != null && packagesForUid.length > 0) {
                return packagesForUid[0];
            }
            return "unknown";
        }

        public int getTvInputState(java.lang.String str, int i) {
            int i2;
            int resolveCallingUserId = com.android.server.tv.TvInputManagerService.this.resolveCallingUserId(android.os.Binder.getCallingPid(), android.os.Binder.getCallingUid(), i, "getTvInputState");
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                synchronized (com.android.server.tv.TvInputManagerService.this.mLock) {
                    com.android.server.tv.TvInputManagerService.TvInputState tvInputState = (com.android.server.tv.TvInputManagerService.TvInputState) com.android.server.tv.TvInputManagerService.this.getOrCreateUserStateLocked(resolveCallingUserId).inputMap.get(str);
                    i2 = tvInputState == null ? 0 : tvInputState.state;
                }
                return i2;
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public java.util.List<java.lang.String> getAvailableExtensionInterfaceNames(java.lang.String str, int i) {
            android.media.tv.ITvInputService iTvInputService;
            com.android.server.tv.TvInputManagerService.ServiceState serviceState;
            ensureTisExtensionInterfacePermission();
            int callingUid = android.os.Binder.getCallingUid();
            int callingPid = android.os.Binder.getCallingPid();
            int resolveCallingUserId = com.android.server.tv.TvInputManagerService.this.resolveCallingUserId(callingPid, callingUid, i, "getAvailableExtensionInterfaceNames");
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                synchronized (com.android.server.tv.TvInputManagerService.this.mLock) {
                    try {
                        com.android.server.tv.TvInputManagerService.UserState orCreateUserStateLocked = com.android.server.tv.TvInputManagerService.this.getOrCreateUserStateLocked(resolveCallingUserId);
                        com.android.server.tv.TvInputManagerService.TvInputState tvInputState = (com.android.server.tv.TvInputManagerService.TvInputState) orCreateUserStateLocked.inputMap.get(str);
                        iTvInputService = (tvInputState == null || (serviceState = (com.android.server.tv.TvInputManagerService.ServiceState) orCreateUserStateLocked.serviceStateMap.get(tvInputState.info.getComponent())) == null || !serviceState.isHardware || serviceState.service == null) ? null : serviceState.service;
                    } finally {
                    }
                }
                if (iTvInputService != null) {
                    try {
                        java.util.ArrayList arrayList = new java.util.ArrayList();
                        for (java.lang.String str2 : com.android.internal.util.CollectionUtils.emptyIfNull(iTvInputService.getAvailableExtensionInterfaceNames())) {
                            java.lang.String extensionInterfacePermission = iTvInputService.getExtensionInterfacePermission(str2);
                            if (extensionInterfacePermission != null && com.android.server.tv.TvInputManagerService.this.mContext.checkPermission(extensionInterfacePermission, callingPid, callingUid) != 0) {
                            }
                            arrayList.add(str2);
                        }
                        android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                        return arrayList;
                    } catch (android.os.RemoteException e) {
                        android.util.Slog.e(com.android.server.tv.TvInputManagerService.TAG, "error in getAvailableExtensionInterfaceNames or getExtensionInterfacePermission", e);
                    }
                }
                java.util.ArrayList arrayList2 = new java.util.ArrayList();
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                return arrayList2;
            } catch (java.lang.Throwable th) {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                throw th;
            }
        }

        public android.os.IBinder getExtensionInterface(java.lang.String str, java.lang.String str2, int i) {
            android.media.tv.ITvInputService iTvInputService;
            com.android.server.tv.TvInputManagerService.ServiceState serviceState;
            ensureTisExtensionInterfacePermission();
            int callingUid = android.os.Binder.getCallingUid();
            int callingPid = android.os.Binder.getCallingPid();
            int resolveCallingUserId = com.android.server.tv.TvInputManagerService.this.resolveCallingUserId(callingPid, callingUid, i, "getExtensionInterface");
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                synchronized (com.android.server.tv.TvInputManagerService.this.mLock) {
                    try {
                        com.android.server.tv.TvInputManagerService.UserState orCreateUserStateLocked = com.android.server.tv.TvInputManagerService.this.getOrCreateUserStateLocked(resolveCallingUserId);
                        com.android.server.tv.TvInputManagerService.TvInputState tvInputState = (com.android.server.tv.TvInputManagerService.TvInputState) orCreateUserStateLocked.inputMap.get(str);
                        iTvInputService = (tvInputState == null || (serviceState = (com.android.server.tv.TvInputManagerService.ServiceState) orCreateUserStateLocked.serviceStateMap.get(tvInputState.info.getComponent())) == null || !serviceState.isHardware || serviceState.service == null) ? null : serviceState.service;
                    } finally {
                    }
                }
                if (iTvInputService != null) {
                    try {
                        java.lang.String extensionInterfacePermission = iTvInputService.getExtensionInterfacePermission(str2);
                        if (extensionInterfacePermission != null) {
                            if (com.android.server.tv.TvInputManagerService.this.mContext.checkPermission(extensionInterfacePermission, callingPid, callingUid) == 0) {
                            }
                        }
                        android.os.IBinder extensionInterface = iTvInputService.getExtensionInterface(str2);
                        android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                        return extensionInterface;
                    } catch (android.os.RemoteException e) {
                        android.util.Slog.e(com.android.server.tv.TvInputManagerService.TAG, "error in getExtensionInterfacePermission or getExtensionInterface", e);
                    }
                }
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                return null;
            } catch (java.lang.Throwable th) {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                throw th;
            }
        }

        public java.util.List<android.media.tv.TvContentRatingSystemInfo> getTvContentRatingSystemList(int i) {
            java.util.List<android.media.tv.TvContentRatingSystemInfo> list;
            if (com.android.server.tv.TvInputManagerService.this.mContext.checkCallingPermission("android.permission.READ_CONTENT_RATING_SYSTEMS") != 0) {
                throw new java.lang.SecurityException("The caller does not have permission to read content rating systems");
            }
            int resolveCallingUserId = com.android.server.tv.TvInputManagerService.this.resolveCallingUserId(android.os.Binder.getCallingPid(), android.os.Binder.getCallingUid(), i, "getTvContentRatingSystemList");
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                synchronized (com.android.server.tv.TvInputManagerService.this.mLock) {
                    list = com.android.server.tv.TvInputManagerService.this.getOrCreateUserStateLocked(resolveCallingUserId).contentRatingSystemList;
                }
                return list;
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        public void sendTvInputNotifyIntent(android.content.Intent intent, int i) {
            char c;
            if (com.android.server.tv.TvInputManagerService.this.mContext.checkCallingPermission("android.permission.NOTIFY_TV_INPUTS") != 0) {
                throw new java.lang.SecurityException("The caller: " + getCallingPackageName() + " doesn't have permission: android.permission.NOTIFY_TV_INPUTS");
            }
            if (android.text.TextUtils.isEmpty(intent.getPackage())) {
                throw new java.lang.IllegalArgumentException("Must specify package name to notify.");
            }
            java.lang.String action = intent.getAction();
            switch (action.hashCode()) {
                case -160295064:
                    if (action.equals("android.media.tv.action.WATCH_NEXT_PROGRAM_BROWSABLE_DISABLED")) {
                        c = 1;
                        break;
                    }
                    c = 65535;
                    break;
                case 1568780589:
                    if (action.equals("android.media.tv.action.PREVIEW_PROGRAM_BROWSABLE_DISABLED")) {
                        c = 0;
                        break;
                    }
                    c = 65535;
                    break;
                case 2011523553:
                    if (action.equals("android.media.tv.action.PREVIEW_PROGRAM_ADDED_TO_WATCH_NEXT")) {
                        c = 2;
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
                    if (intent.getLongExtra("android.media.tv.extra.PREVIEW_PROGRAM_ID", -1L) < 0) {
                        throw new java.lang.IllegalArgumentException("Invalid preview program ID.");
                    }
                    break;
                case 1:
                    if (intent.getLongExtra("android.media.tv.extra.WATCH_NEXT_PROGRAM_ID", -1L) < 0) {
                        throw new java.lang.IllegalArgumentException("Invalid watch next program ID.");
                    }
                    break;
                case 2:
                    if (intent.getLongExtra("android.media.tv.extra.PREVIEW_PROGRAM_ID", -1L) < 0) {
                        throw new java.lang.IllegalArgumentException("Invalid preview program ID.");
                    }
                    if (intent.getLongExtra("android.media.tv.extra.WATCH_NEXT_PROGRAM_ID", -1L) < 0) {
                        throw new java.lang.IllegalArgumentException("Invalid watch next program ID.");
                    }
                    break;
                default:
                    throw new java.lang.IllegalArgumentException("Invalid TV input notifying action: " + intent.getAction());
            }
            int resolveCallingUserId = com.android.server.tv.TvInputManagerService.this.resolveCallingUserId(android.os.Binder.getCallingPid(), android.os.Binder.getCallingUid(), i, "sendTvInputNotifyIntent");
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                com.android.server.tv.TvInputManagerService.this.getContext().sendBroadcastAsUser(intent, new android.os.UserHandle(resolveCallingUserId));
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void registerCallback(android.media.tv.ITvInputManagerCallback iTvInputManagerCallback, int i) {
            int callingPid = android.os.Binder.getCallingPid();
            int callingUid = android.os.Binder.getCallingUid();
            int resolveCallingUserId = com.android.server.tv.TvInputManagerService.this.resolveCallingUserId(callingPid, callingUid, i, "registerCallback");
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                synchronized (com.android.server.tv.TvInputManagerService.this.mLock) {
                    try {
                        com.android.server.tv.TvInputManagerService.UserState orCreateUserStateLocked = com.android.server.tv.TvInputManagerService.this.getOrCreateUserStateLocked(resolveCallingUserId);
                        if (!orCreateUserStateLocked.mCallbacks.register(iTvInputManagerCallback)) {
                            android.util.Slog.e(com.android.server.tv.TvInputManagerService.TAG, "client process has already died");
                        } else {
                            orCreateUserStateLocked.callbackPidUidMap.put(iTvInputManagerCallback, android.util.Pair.create(java.lang.Integer.valueOf(callingPid), java.lang.Integer.valueOf(callingUid)));
                        }
                    } catch (java.lang.Throwable th) {
                        throw th;
                    }
                }
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void unregisterCallback(android.media.tv.ITvInputManagerCallback iTvInputManagerCallback, int i) {
            int resolveCallingUserId = com.android.server.tv.TvInputManagerService.this.resolveCallingUserId(android.os.Binder.getCallingPid(), android.os.Binder.getCallingUid(), i, "unregisterCallback");
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                synchronized (com.android.server.tv.TvInputManagerService.this.mLock) {
                    com.android.server.tv.TvInputManagerService.UserState orCreateUserStateLocked = com.android.server.tv.TvInputManagerService.this.getOrCreateUserStateLocked(resolveCallingUserId);
                    orCreateUserStateLocked.mCallbacks.unregister(iTvInputManagerCallback);
                    orCreateUserStateLocked.callbackPidUidMap.remove(iTvInputManagerCallback);
                }
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public boolean isParentalControlsEnabled(int i) {
            boolean isParentalControlsEnabled;
            int resolveCallingUserId = com.android.server.tv.TvInputManagerService.this.resolveCallingUserId(android.os.Binder.getCallingPid(), android.os.Binder.getCallingUid(), i, "isParentalControlsEnabled");
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                synchronized (com.android.server.tv.TvInputManagerService.this.mLock) {
                    isParentalControlsEnabled = com.android.server.tv.TvInputManagerService.this.getOrCreateUserStateLocked(resolveCallingUserId).persistentDataStore.isParentalControlsEnabled();
                }
                return isParentalControlsEnabled;
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void setParentalControlsEnabled(boolean z, int i) {
            ensureParentalControlsPermission();
            int resolveCallingUserId = com.android.server.tv.TvInputManagerService.this.resolveCallingUserId(android.os.Binder.getCallingPid(), android.os.Binder.getCallingUid(), i, "setParentalControlsEnabled");
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                synchronized (com.android.server.tv.TvInputManagerService.this.mLock) {
                    com.android.server.tv.TvInputManagerService.this.getOrCreateUserStateLocked(resolveCallingUserId).persistentDataStore.setParentalControlsEnabled(z);
                }
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public boolean isRatingBlocked(java.lang.String str, int i) {
            boolean isRatingBlocked;
            int resolveCallingUserId = com.android.server.tv.TvInputManagerService.this.resolveCallingUserId(android.os.Binder.getCallingPid(), android.os.Binder.getCallingUid(), i, "isRatingBlocked");
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                synchronized (com.android.server.tv.TvInputManagerService.this.mLock) {
                    isRatingBlocked = com.android.server.tv.TvInputManagerService.this.getOrCreateUserStateLocked(resolveCallingUserId).persistentDataStore.isRatingBlocked(android.media.tv.TvContentRating.unflattenFromString(str));
                }
                return isRatingBlocked;
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public java.util.List<java.lang.String> getBlockedRatings(int i) {
            java.util.ArrayList arrayList;
            int resolveCallingUserId = com.android.server.tv.TvInputManagerService.this.resolveCallingUserId(android.os.Binder.getCallingPid(), android.os.Binder.getCallingUid(), i, "getBlockedRatings");
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                synchronized (com.android.server.tv.TvInputManagerService.this.mLock) {
                    try {
                        com.android.server.tv.TvInputManagerService.UserState orCreateUserStateLocked = com.android.server.tv.TvInputManagerService.this.getOrCreateUserStateLocked(resolveCallingUserId);
                        arrayList = new java.util.ArrayList();
                        for (android.media.tv.TvContentRating tvContentRating : orCreateUserStateLocked.persistentDataStore.getBlockedRatings()) {
                            arrayList.add(tvContentRating.flattenToString());
                        }
                    } catch (java.lang.Throwable th) {
                        throw th;
                    }
                }
                return arrayList;
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void addBlockedRating(java.lang.String str, int i) {
            ensureParentalControlsPermission();
            int resolveCallingUserId = com.android.server.tv.TvInputManagerService.this.resolveCallingUserId(android.os.Binder.getCallingPid(), android.os.Binder.getCallingUid(), i, "addBlockedRating");
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                synchronized (com.android.server.tv.TvInputManagerService.this.mLock) {
                    com.android.server.tv.TvInputManagerService.this.getOrCreateUserStateLocked(resolveCallingUserId).persistentDataStore.addBlockedRating(android.media.tv.TvContentRating.unflattenFromString(str));
                }
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void removeBlockedRating(java.lang.String str, int i) {
            ensureParentalControlsPermission();
            int resolveCallingUserId = com.android.server.tv.TvInputManagerService.this.resolveCallingUserId(android.os.Binder.getCallingPid(), android.os.Binder.getCallingUid(), i, "removeBlockedRating");
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                synchronized (com.android.server.tv.TvInputManagerService.this.mLock) {
                    com.android.server.tv.TvInputManagerService.this.getOrCreateUserStateLocked(resolveCallingUserId).persistentDataStore.removeBlockedRating(android.media.tv.TvContentRating.unflattenFromString(str));
                }
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        private void ensureParentalControlsPermission() {
            if (com.android.server.tv.TvInputManagerService.this.mContext.checkCallingPermission("android.permission.MODIFY_PARENTAL_CONTROLS") != 0) {
                throw new java.lang.SecurityException("The caller does not have parental controls permission");
            }
        }

        public void createSession(android.media.tv.ITvInputClient iTvInputClient, java.lang.String str, android.content.AttributionSource attributionSource, boolean z, int i, int i2) {
            com.android.server.tv.TvInputManagerService.ServiceState serviceState;
            int callingUid = android.os.Binder.getCallingUid();
            int callingPid = android.os.Binder.getCallingPid();
            int resolveCallingUserId = com.android.server.tv.TvInputManagerService.this.resolveCallingUserId(callingPid, callingUid, i2, "createSession");
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            java.lang.String obj = java.util.UUID.randomUUID().toString();
            try {
                synchronized (com.android.server.tv.TvInputManagerService.this.mLock) {
                    if (i2 != com.android.server.tv.TvInputManagerService.this.mCurrentUserId && !com.android.server.tv.TvInputManagerService.this.mRunningProfiles.contains(java.lang.Integer.valueOf(i2)) && !z) {
                        com.android.server.tv.TvInputManagerService.this.sendSessionTokenToClientLocked(iTvInputClient, str, null, null, i);
                        return;
                    }
                    com.android.server.tv.TvInputManagerService.UserState orCreateUserStateLocked = com.android.server.tv.TvInputManagerService.this.getOrCreateUserStateLocked(resolveCallingUserId);
                    com.android.server.tv.TvInputManagerService.TvInputState tvInputState = (com.android.server.tv.TvInputManagerService.TvInputState) orCreateUserStateLocked.inputMap.get(str);
                    if (tvInputState == null) {
                        android.util.Slog.w(com.android.server.tv.TvInputManagerService.TAG, "Failed to find input state for inputId=" + str);
                        com.android.server.tv.TvInputManagerService.this.sendSessionTokenToClientLocked(iTvInputClient, str, null, null, i);
                        return;
                    }
                    android.media.tv.TvInputInfo tvInputInfo = tvInputState.info;
                    com.android.server.tv.TvInputManagerService.ServiceState serviceState2 = (com.android.server.tv.TvInputManagerService.ServiceState) orCreateUserStateLocked.serviceStateMap.get(tvInputInfo.getComponent());
                    if (serviceState2 == null) {
                        int i3 = android.content.pm.PackageManager.getApplicationInfoAsUserCached(tvInputInfo.getComponent().getPackageName(), 0L, resolveCallingUserId).uid;
                        com.android.server.tv.TvInputManagerService.ServiceState serviceState3 = new com.android.server.tv.TvInputManagerService.ServiceState(tvInputInfo.getComponent(), resolveCallingUserId);
                        orCreateUserStateLocked.serviceStateMap.put(tvInputInfo.getComponent(), serviceState3);
                        serviceState = serviceState3;
                    } else {
                        serviceState = serviceState2;
                    }
                    if (serviceState.reconnecting) {
                        com.android.server.tv.TvInputManagerService.this.sendSessionTokenToClientLocked(iTvInputClient, str, null, null, i);
                        return;
                    }
                    android.os.Binder binder = new android.os.Binder();
                    com.android.server.tv.TvInputManagerService.SessionState sessionState = new com.android.server.tv.TvInputManagerService.SessionState(binder, tvInputInfo.getId(), tvInputInfo.getComponent(), z, iTvInputClient, i, callingUid, callingPid, resolveCallingUserId, obj, attributionSource);
                    orCreateUserStateLocked.sessionStateMap.put(binder, sessionState);
                    com.android.server.tv.TvInputManagerService.this.mSessionIdToSessionStateMap.put(obj, sessionState);
                    serviceState.sessionTokens.add(binder);
                    if (serviceState.service == null) {
                        com.android.server.tv.TvInputManagerService.this.updateServiceConnectionLocked(tvInputInfo.getComponent(), resolveCallingUserId);
                    } else if (!com.android.server.tv.TvInputManagerService.this.createSessionInternalLocked(serviceState.service, binder, resolveCallingUserId)) {
                        com.android.server.tv.TvInputManagerService.this.removeSessionStateLocked(binder, resolveCallingUserId);
                    }
                    com.android.server.tv.TvInputManagerService.this.logTuneStateChanged(1, sessionState, tvInputState);
                }
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void releaseSession(android.os.IBinder iBinder, int i) {
            com.android.server.tv.TvInputManagerService.SessionState releaseSessionLocked;
            com.android.server.tv.TvInputManagerService.UserState userStateLocked;
            int callingUid = android.os.Binder.getCallingUid();
            int resolveCallingUserId = com.android.server.tv.TvInputManagerService.this.resolveCallingUserId(android.os.Binder.getCallingPid(), callingUid, i, "releaseSession");
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                synchronized (com.android.server.tv.TvInputManagerService.this.mLock) {
                    releaseSessionLocked = com.android.server.tv.TvInputManagerService.this.releaseSessionLocked(iBinder, callingUid, resolveCallingUserId);
                    userStateLocked = com.android.server.tv.TvInputManagerService.this.getUserStateLocked(i);
                }
                if (releaseSessionLocked != null) {
                    com.android.server.tv.TvInputManagerService.this.logTuneStateChanged(4, releaseSessionLocked, com.android.server.tv.TvInputManagerService.getTvInputState(releaseSessionLocked, userStateLocked));
                }
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void setMainSession(android.os.IBinder iBinder, int i) {
            if (com.android.server.tv.TvInputManagerService.this.mContext.checkCallingPermission("android.permission.CHANGE_HDMI_CEC_ACTIVE_SOURCE") != 0) {
                throw new java.lang.SecurityException("The caller does not have CHANGE_HDMI_CEC_ACTIVE_SOURCE permission");
            }
            int callingUid = android.os.Binder.getCallingUid();
            int resolveCallingUserId = com.android.server.tv.TvInputManagerService.this.resolveCallingUserId(android.os.Binder.getCallingPid(), callingUid, i, "setMainSession");
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                synchronized (com.android.server.tv.TvInputManagerService.this.mLock) {
                    com.android.server.tv.TvInputManagerService.UserState orCreateUserStateLocked = com.android.server.tv.TvInputManagerService.this.getOrCreateUserStateLocked(resolveCallingUserId);
                    if (orCreateUserStateLocked.mainSessionToken == iBinder) {
                        return;
                    }
                    android.os.IBinder iBinder2 = orCreateUserStateLocked.mainSessionToken;
                    orCreateUserStateLocked.mainSessionToken = iBinder;
                    if (iBinder != null) {
                        com.android.server.tv.TvInputManagerService.this.setMainLocked(iBinder, true, callingUid, i);
                    }
                    if (iBinder2 != null) {
                        com.android.server.tv.TvInputManagerService.this.setMainLocked(iBinder2, false, 1000, i);
                    }
                }
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Removed duplicated region for block: B:21:0x0074  */
        /* JADX WARN: Removed duplicated region for block: B:23:0x0079  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public void setSurface(android.os.IBinder iBinder, android.view.Surface surface, int i) {
            com.android.server.tv.TvInputManagerService.UserState userState;
            com.android.server.tv.TvInputManagerService.UserState userState2;
            int callingUid = android.os.Binder.getCallingUid();
            int resolveCallingUserId = com.android.server.tv.TvInputManagerService.this.resolveCallingUserId(android.os.Binder.getCallingPid(), callingUid, i, "setSurface");
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            com.android.server.tv.TvInputManagerService.SessionState sessionState = null;
            try {
                try {
                    synchronized (com.android.server.tv.TvInputManagerService.this.mLock) {
                        try {
                            userState2 = com.android.server.tv.TvInputManagerService.this.getUserStateLocked(i);
                        } catch (android.os.RemoteException | com.android.server.tv.TvInputManagerService.SessionNotFoundException e) {
                            e = e;
                            userState2 = null;
                        } catch (java.lang.Throwable th) {
                            th = th;
                            userState = 0;
                            try {
                                throw th;
                            } catch (java.lang.Throwable th2) {
                                th = th2;
                                if (surface != null) {
                                    surface.release();
                                }
                                if (0 != 0) {
                                    com.android.server.tv.TvInputManagerService.this.logTuneStateChanged(surface == null ? 3 : 2, null, com.android.server.tv.TvInputManagerService.getTvInputState(null, userState));
                                }
                                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                                throw th;
                            }
                        }
                        try {
                            sessionState = com.android.server.tv.TvInputManagerService.this.getSessionStateLocked(iBinder, callingUid, resolveCallingUserId);
                            if (sessionState.hardwareSessionToken == null) {
                                com.android.server.tv.TvInputManagerService.this.getSessionLocked(sessionState).setSurface(surface);
                            } else {
                                com.android.server.tv.TvInputManagerService.this.getSessionLocked(sessionState.hardwareSessionToken, 1000, resolveCallingUserId).setSurface(surface);
                            }
                            boolean z = surface == null;
                            if (sessionState.isVisible != z) {
                                sessionState.isVisible = z;
                                com.android.server.tv.TvInputManagerService.this.notifyCurrentChannelInfosUpdatedLocked(userState2);
                            }
                        } catch (android.os.RemoteException | com.android.server.tv.TvInputManagerService.SessionNotFoundException e2) {
                            e = e2;
                            android.util.Slog.e(com.android.server.tv.TvInputManagerService.TAG, "error in setSurface", e);
                            if (surface != null) {
                            }
                            if (sessionState != null) {
                            }
                            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                        }
                        if (surface != null) {
                            surface.release();
                        }
                        if (sessionState != null) {
                            com.android.server.tv.TvInputManagerService.this.logTuneStateChanged(surface == null ? 3 : 2, sessionState, com.android.server.tv.TvInputManagerService.getTvInputState(sessionState, userState2));
                        }
                        android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                    }
                } catch (java.lang.Throwable th3) {
                    th = th3;
                    userState = i;
                }
            } catch (java.lang.Throwable th4) {
                th = th4;
                userState = 0;
            }
        }

        public void dispatchSurfaceChanged(android.os.IBinder iBinder, int i, int i2, int i3, int i4) {
            int callingUid = android.os.Binder.getCallingUid();
            int resolveCallingUserId = com.android.server.tv.TvInputManagerService.this.resolveCallingUserId(android.os.Binder.getCallingPid(), callingUid, i4, "dispatchSurfaceChanged");
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                synchronized (com.android.server.tv.TvInputManagerService.this.mLock) {
                    try {
                        com.android.server.tv.TvInputManagerService.SessionState sessionStateLocked = com.android.server.tv.TvInputManagerService.this.getSessionStateLocked(iBinder, callingUid, resolveCallingUserId);
                        com.android.server.tv.TvInputManagerService.this.getSessionLocked(sessionStateLocked).dispatchSurfaceChanged(i, i2, i3);
                        if (sessionStateLocked.hardwareSessionToken != null) {
                            com.android.server.tv.TvInputManagerService.this.getSessionLocked(sessionStateLocked.hardwareSessionToken, 1000, resolveCallingUserId).dispatchSurfaceChanged(i, i2, i3);
                        }
                    } catch (android.os.RemoteException | com.android.server.tv.TvInputManagerService.SessionNotFoundException e) {
                        android.util.Slog.e(com.android.server.tv.TvInputManagerService.TAG, "error in dispatchSurfaceChanged", e);
                    }
                }
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void setVolume(android.os.IBinder iBinder, float f, int i) {
            int callingUid = android.os.Binder.getCallingUid();
            int resolveCallingUserId = com.android.server.tv.TvInputManagerService.this.resolveCallingUserId(android.os.Binder.getCallingPid(), callingUid, i, "setVolume");
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                synchronized (com.android.server.tv.TvInputManagerService.this.mLock) {
                    try {
                        com.android.server.tv.TvInputManagerService.SessionState sessionStateLocked = com.android.server.tv.TvInputManagerService.this.getSessionStateLocked(iBinder, callingUid, resolveCallingUserId);
                        com.android.server.tv.TvInputManagerService.this.getSessionLocked(sessionStateLocked).setVolume(f);
                        if (sessionStateLocked.hardwareSessionToken != null) {
                            android.media.tv.ITvInputSession sessionLocked = com.android.server.tv.TvInputManagerService.this.getSessionLocked(sessionStateLocked.hardwareSessionToken, 1000, resolveCallingUserId);
                            float f2 = com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE;
                            if (f > com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE) {
                                f2 = 1.0f;
                            }
                            sessionLocked.setVolume(f2);
                        }
                    } catch (android.os.RemoteException | com.android.server.tv.TvInputManagerService.SessionNotFoundException e) {
                        android.util.Slog.e(com.android.server.tv.TvInputManagerService.TAG, "error in setVolume", e);
                    }
                }
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        /* JADX WARN: Removed duplicated region for block: B:13:0x0081 A[Catch: all -> 0x0043, DONT_GENERATE, TRY_ENTER, TRY_LEAVE, TryCatch #0 {all -> 0x0043, blocks: (B:6:0x001c, B:8:0x0038, B:11:0x007b, B:13:0x0081, B:17:0x0086, B:19:0x008c, B:22:0x0091, B:23:0x00d7, B:27:0x0049, B:29:0x005a, B:31:0x006c, B:32:0x0071, B:39:0x00d0), top: B:5:0x001c, outer: #2, inners: #1 }] */
        /* JADX WARN: Removed duplicated region for block: B:17:0x0086 A[Catch: all -> 0x0043, RemoteException | SessionNotFoundException -> 0x0046, RemoteException | SessionNotFoundException -> 0x0046, TRY_ENTER, TRY_LEAVE, TryCatch #1 {RemoteException | SessionNotFoundException -> 0x0046, blocks: (B:6:0x001c, B:8:0x0038, B:11:0x007b, B:17:0x0086, B:17:0x0086, B:22:0x0091, B:22:0x0091, B:27:0x0049, B:29:0x005a, B:31:0x006c, B:32:0x0071), top: B:5:0x001c, outer: #0 }] */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public void tune(android.os.IBinder iBinder, android.net.Uri uri, android.os.Bundle bundle, int i) {
            com.android.server.tv.TvInputManagerService.UserState orCreateUserStateLocked;
            com.android.server.tv.TvInputManagerService.SessionState sessionStateLocked;
            int callingUid = android.os.Binder.getCallingUid();
            int resolveCallingUserId = com.android.server.tv.TvInputManagerService.this.resolveCallingUserId(android.os.Binder.getCallingPid(), callingUid, i, "tune");
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                synchronized (com.android.server.tv.TvInputManagerService.this.mLock) {
                    try {
                        try {
                            com.android.server.tv.TvInputManagerService.this.getSessionLocked(iBinder, callingUid, resolveCallingUserId).tune(uri, bundle);
                            orCreateUserStateLocked = com.android.server.tv.TvInputManagerService.this.getOrCreateUserStateLocked(resolveCallingUserId);
                            sessionStateLocked = com.android.server.tv.TvInputManagerService.this.getSessionStateLocked(iBinder, callingUid, orCreateUserStateLocked);
                        } catch (android.os.RemoteException | com.android.server.tv.TvInputManagerService.SessionNotFoundException e) {
                            android.util.Slog.e(com.android.server.tv.TvInputManagerService.TAG, "error in tune", e);
                        }
                        if (sessionStateLocked.isCurrent) {
                            if (!java.util.Objects.equals(sessionStateLocked.currentChannel, uri)) {
                            }
                            if (android.media.tv.TvContract.isChannelUriForPassthroughInput(uri)) {
                                if (sessionStateLocked.isRecordingSession) {
                                    return;
                                }
                                com.android.server.tv.TvInputManagerService.this.logTuneStateChanged(5, sessionStateLocked, com.android.server.tv.TvInputManagerService.getTvInputState(sessionStateLocked, orCreateUserStateLocked));
                                com.android.internal.os.SomeArgs obtain = com.android.internal.os.SomeArgs.obtain();
                                obtain.arg1 = sessionStateLocked.componentName.getPackageName();
                                obtain.arg2 = java.lang.Long.valueOf(java.lang.System.currentTimeMillis());
                                obtain.arg3 = java.lang.Long.valueOf(android.content.ContentUris.parseId(uri));
                                obtain.arg4 = bundle;
                                obtain.arg5 = iBinder;
                                com.android.server.tv.TvInputManagerService.this.mMessageHandler.obtainMessage(1, obtain).sendToTarget();
                                return;
                            }
                            return;
                        }
                        sessionStateLocked.isCurrent = true;
                        sessionStateLocked.currentChannel = uri;
                        com.android.server.tv.TvInputManagerService.this.notifyCurrentChannelInfosUpdatedLocked(orCreateUserStateLocked);
                        if (!sessionStateLocked.isRecordingSession) {
                            java.lang.String sessionActualInputId = com.android.server.tv.TvInputManagerService.this.getSessionActualInputId(sessionStateLocked);
                            if (!android.text.TextUtils.equals(com.android.server.tv.TvInputManagerService.this.mOnScreenInputId, sessionActualInputId)) {
                                com.android.server.tv.TvInputManagerService.this.logExternalInputEvent(1, sessionActualInputId, sessionStateLocked);
                            }
                            com.android.server.tv.TvInputManagerService.this.mOnScreenInputId = sessionActualInputId;
                            com.android.server.tv.TvInputManagerService.this.mOnScreenSessionState = sessionStateLocked;
                        }
                        if (android.media.tv.TvContract.isChannelUriForPassthroughInput(uri)) {
                        }
                    } catch (java.lang.Throwable th) {
                        throw th;
                    }
                }
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void unblockContent(android.os.IBinder iBinder, java.lang.String str, int i) {
            ensureParentalControlsPermission();
            int callingUid = android.os.Binder.getCallingUid();
            int resolveCallingUserId = com.android.server.tv.TvInputManagerService.this.resolveCallingUserId(android.os.Binder.getCallingPid(), callingUid, i, "unblockContent");
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                synchronized (com.android.server.tv.TvInputManagerService.this.mLock) {
                    try {
                        com.android.server.tv.TvInputManagerService.this.getSessionLocked(iBinder, callingUid, resolveCallingUserId).unblockContent(str);
                    } catch (android.os.RemoteException | com.android.server.tv.TvInputManagerService.SessionNotFoundException e) {
                        android.util.Slog.e(com.android.server.tv.TvInputManagerService.TAG, "error in unblockContent", e);
                    }
                }
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void setCaptionEnabled(android.os.IBinder iBinder, boolean z, int i) {
            int callingUid = android.os.Binder.getCallingUid();
            int resolveCallingUserId = com.android.server.tv.TvInputManagerService.this.resolveCallingUserId(android.os.Binder.getCallingPid(), callingUid, i, "setCaptionEnabled");
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                synchronized (com.android.server.tv.TvInputManagerService.this.mLock) {
                    try {
                        com.android.server.tv.TvInputManagerService.this.getSessionLocked(iBinder, callingUid, resolveCallingUserId).setCaptionEnabled(z);
                    } catch (android.os.RemoteException | com.android.server.tv.TvInputManagerService.SessionNotFoundException e) {
                        android.util.Slog.e(com.android.server.tv.TvInputManagerService.TAG, "error in setCaptionEnabled", e);
                    }
                }
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void selectAudioPresentation(android.os.IBinder iBinder, int i, int i2, int i3) {
            int callingUid = android.os.Binder.getCallingUid();
            int resolveCallingUserId = com.android.server.tv.TvInputManagerService.this.resolveCallingUserId(android.os.Binder.getCallingPid(), callingUid, i3, "selectAudioPresentation");
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                synchronized (com.android.server.tv.TvInputManagerService.this.mLock) {
                    try {
                        com.android.server.tv.TvInputManagerService.this.getSessionLocked(iBinder, callingUid, resolveCallingUserId).selectAudioPresentation(i, i2);
                    } catch (android.os.RemoteException | com.android.server.tv.TvInputManagerService.SessionNotFoundException e) {
                        android.util.Slog.e(com.android.server.tv.TvInputManagerService.TAG, "error in selectAudioPresentation", e);
                    }
                }
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void selectTrack(android.os.IBinder iBinder, int i, java.lang.String str, int i2) {
            int callingUid = android.os.Binder.getCallingUid();
            int resolveCallingUserId = com.android.server.tv.TvInputManagerService.this.resolveCallingUserId(android.os.Binder.getCallingPid(), callingUid, i2, "selectTrack");
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                synchronized (com.android.server.tv.TvInputManagerService.this.mLock) {
                    try {
                        com.android.server.tv.TvInputManagerService.this.getSessionLocked(iBinder, callingUid, resolveCallingUserId).selectTrack(i, str);
                    } catch (android.os.RemoteException | com.android.server.tv.TvInputManagerService.SessionNotFoundException e) {
                        android.util.Slog.e(com.android.server.tv.TvInputManagerService.TAG, "error in selectTrack", e);
                    }
                }
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void setInteractiveAppNotificationEnabled(android.os.IBinder iBinder, boolean z, int i) {
            int callingUid = android.os.Binder.getCallingUid();
            int resolveCallingUserId = com.android.server.tv.TvInputManagerService.this.resolveCallingUserId(android.os.Binder.getCallingPid(), callingUid, i, "setInteractiveAppNotificationEnabled");
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                synchronized (com.android.server.tv.TvInputManagerService.this.mLock) {
                    try {
                        com.android.server.tv.TvInputManagerService.this.getSessionLocked(iBinder, callingUid, resolveCallingUserId).setInteractiveAppNotificationEnabled(z);
                    } catch (android.os.RemoteException | com.android.server.tv.TvInputManagerService.SessionNotFoundException e) {
                        android.util.Slog.e(com.android.server.tv.TvInputManagerService.TAG, "error in setInteractiveAppNotificationEnabled", e);
                    }
                }
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void sendAppPrivateCommand(android.os.IBinder iBinder, java.lang.String str, android.os.Bundle bundle, int i) {
            int callingUid = android.os.Binder.getCallingUid();
            int resolveCallingUserId = com.android.server.tv.TvInputManagerService.this.resolveCallingUserId(android.os.Binder.getCallingPid(), callingUid, i, "sendAppPrivateCommand");
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                synchronized (com.android.server.tv.TvInputManagerService.this.mLock) {
                    try {
                        com.android.server.tv.TvInputManagerService.this.getSessionLocked(iBinder, callingUid, resolveCallingUserId).appPrivateCommand(str, bundle);
                    } catch (android.os.RemoteException | com.android.server.tv.TvInputManagerService.SessionNotFoundException e) {
                        android.util.Slog.e(com.android.server.tv.TvInputManagerService.TAG, "error in appPrivateCommand", e);
                    }
                }
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void createOverlayView(android.os.IBinder iBinder, android.os.IBinder iBinder2, android.graphics.Rect rect, int i) {
            int callingUid = android.os.Binder.getCallingUid();
            int resolveCallingUserId = com.android.server.tv.TvInputManagerService.this.resolveCallingUserId(android.os.Binder.getCallingPid(), callingUid, i, "createOverlayView");
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                synchronized (com.android.server.tv.TvInputManagerService.this.mLock) {
                    try {
                        com.android.server.tv.TvInputManagerService.this.getSessionLocked(iBinder, callingUid, resolveCallingUserId).createOverlayView(iBinder2, rect);
                    } catch (android.os.RemoteException | com.android.server.tv.TvInputManagerService.SessionNotFoundException e) {
                        android.util.Slog.e(com.android.server.tv.TvInputManagerService.TAG, "error in createOverlayView", e);
                    }
                }
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void relayoutOverlayView(android.os.IBinder iBinder, android.graphics.Rect rect, int i) {
            int callingUid = android.os.Binder.getCallingUid();
            int resolveCallingUserId = com.android.server.tv.TvInputManagerService.this.resolveCallingUserId(android.os.Binder.getCallingPid(), callingUid, i, "relayoutOverlayView");
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                synchronized (com.android.server.tv.TvInputManagerService.this.mLock) {
                    try {
                        com.android.server.tv.TvInputManagerService.this.getSessionLocked(iBinder, callingUid, resolveCallingUserId).relayoutOverlayView(rect);
                    } catch (android.os.RemoteException | com.android.server.tv.TvInputManagerService.SessionNotFoundException e) {
                        android.util.Slog.e(com.android.server.tv.TvInputManagerService.TAG, "error in relayoutOverlayView", e);
                    }
                }
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void removeOverlayView(android.os.IBinder iBinder, int i) {
            int callingUid = android.os.Binder.getCallingUid();
            int resolveCallingUserId = com.android.server.tv.TvInputManagerService.this.resolveCallingUserId(android.os.Binder.getCallingPid(), callingUid, i, "removeOverlayView");
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                synchronized (com.android.server.tv.TvInputManagerService.this.mLock) {
                    try {
                        com.android.server.tv.TvInputManagerService.this.getSessionLocked(iBinder, callingUid, resolveCallingUserId).removeOverlayView();
                    } catch (android.os.RemoteException | com.android.server.tv.TvInputManagerService.SessionNotFoundException e) {
                        android.util.Slog.e(com.android.server.tv.TvInputManagerService.TAG, "error in removeOverlayView", e);
                    }
                }
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void stopPlayback(android.os.IBinder iBinder, int i, int i2) {
            int callingUid = android.os.Binder.getCallingUid();
            int resolveCallingUserId = com.android.server.tv.TvInputManagerService.this.resolveCallingUserId(android.os.Binder.getCallingPid(), callingUid, i2, "stopPlayback");
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                synchronized (com.android.server.tv.TvInputManagerService.this.mLock) {
                    try {
                        com.android.server.tv.TvInputManagerService.this.getSessionLocked(iBinder, callingUid, resolveCallingUserId).stopPlayback(i);
                    } catch (android.os.RemoteException | com.android.server.tv.TvInputManagerService.SessionNotFoundException e) {
                        android.util.Slog.e(com.android.server.tv.TvInputManagerService.TAG, "error in stopPlayback(mode)", e);
                    }
                }
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void resumePlayback(android.os.IBinder iBinder, int i) {
            int callingUid = android.os.Binder.getCallingUid();
            int resolveCallingUserId = com.android.server.tv.TvInputManagerService.this.resolveCallingUserId(android.os.Binder.getCallingPid(), callingUid, i, "resumePlayback");
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                synchronized (com.android.server.tv.TvInputManagerService.this.mLock) {
                    try {
                        com.android.server.tv.TvInputManagerService.this.getSessionLocked(iBinder, callingUid, resolveCallingUserId).resumePlayback();
                    } catch (android.os.RemoteException | com.android.server.tv.TvInputManagerService.SessionNotFoundException e) {
                        android.util.Slog.e(com.android.server.tv.TvInputManagerService.TAG, "error in resumePlayback()", e);
                    }
                }
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void timeShiftPlay(android.os.IBinder iBinder, android.net.Uri uri, int i) {
            int callingUid = android.os.Binder.getCallingUid();
            int resolveCallingUserId = com.android.server.tv.TvInputManagerService.this.resolveCallingUserId(android.os.Binder.getCallingPid(), callingUid, i, "timeShiftPlay");
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                synchronized (com.android.server.tv.TvInputManagerService.this.mLock) {
                    try {
                        com.android.server.tv.TvInputManagerService.this.getSessionLocked(iBinder, callingUid, resolveCallingUserId).timeShiftPlay(uri);
                    } catch (android.os.RemoteException | com.android.server.tv.TvInputManagerService.SessionNotFoundException e) {
                        android.util.Slog.e(com.android.server.tv.TvInputManagerService.TAG, "error in timeShiftPlay", e);
                    }
                }
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void timeShiftPause(android.os.IBinder iBinder, int i) {
            int callingUid = android.os.Binder.getCallingUid();
            int resolveCallingUserId = com.android.server.tv.TvInputManagerService.this.resolveCallingUserId(android.os.Binder.getCallingPid(), callingUid, i, "timeShiftPause");
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                synchronized (com.android.server.tv.TvInputManagerService.this.mLock) {
                    try {
                        com.android.server.tv.TvInputManagerService.this.getSessionLocked(iBinder, callingUid, resolveCallingUserId).timeShiftPause();
                    } catch (android.os.RemoteException | com.android.server.tv.TvInputManagerService.SessionNotFoundException e) {
                        android.util.Slog.e(com.android.server.tv.TvInputManagerService.TAG, "error in timeShiftPause", e);
                    }
                }
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void timeShiftResume(android.os.IBinder iBinder, int i) {
            int callingUid = android.os.Binder.getCallingUid();
            int resolveCallingUserId = com.android.server.tv.TvInputManagerService.this.resolveCallingUserId(android.os.Binder.getCallingPid(), callingUid, i, "timeShiftResume");
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                synchronized (com.android.server.tv.TvInputManagerService.this.mLock) {
                    try {
                        com.android.server.tv.TvInputManagerService.this.getSessionLocked(iBinder, callingUid, resolveCallingUserId).timeShiftResume();
                    } catch (android.os.RemoteException | com.android.server.tv.TvInputManagerService.SessionNotFoundException e) {
                        android.util.Slog.e(com.android.server.tv.TvInputManagerService.TAG, "error in timeShiftResume", e);
                    }
                }
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void timeShiftSeekTo(android.os.IBinder iBinder, long j, int i) {
            int callingUid = android.os.Binder.getCallingUid();
            int resolveCallingUserId = com.android.server.tv.TvInputManagerService.this.resolveCallingUserId(android.os.Binder.getCallingPid(), callingUid, i, "timeShiftSeekTo");
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                synchronized (com.android.server.tv.TvInputManagerService.this.mLock) {
                    try {
                        com.android.server.tv.TvInputManagerService.this.getSessionLocked(iBinder, callingUid, resolveCallingUserId).timeShiftSeekTo(j);
                    } catch (android.os.RemoteException | com.android.server.tv.TvInputManagerService.SessionNotFoundException e) {
                        android.util.Slog.e(com.android.server.tv.TvInputManagerService.TAG, "error in timeShiftSeekTo", e);
                    }
                }
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void timeShiftSetPlaybackParams(android.os.IBinder iBinder, android.media.PlaybackParams playbackParams, int i) {
            int callingUid = android.os.Binder.getCallingUid();
            int resolveCallingUserId = com.android.server.tv.TvInputManagerService.this.resolveCallingUserId(android.os.Binder.getCallingPid(), callingUid, i, "timeShiftSetPlaybackParams");
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                synchronized (com.android.server.tv.TvInputManagerService.this.mLock) {
                    try {
                        com.android.server.tv.TvInputManagerService.this.getSessionLocked(iBinder, callingUid, resolveCallingUserId).timeShiftSetPlaybackParams(playbackParams);
                    } catch (android.os.RemoteException | com.android.server.tv.TvInputManagerService.SessionNotFoundException e) {
                        android.util.Slog.e(com.android.server.tv.TvInputManagerService.TAG, "error in timeShiftSetPlaybackParams", e);
                    }
                }
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void timeShiftSetMode(android.os.IBinder iBinder, int i, int i2) {
            int callingUid = android.os.Binder.getCallingUid();
            int resolveCallingUserId = com.android.server.tv.TvInputManagerService.this.resolveCallingUserId(android.os.Binder.getCallingPid(), callingUid, i2, "timeShiftSetMode");
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                synchronized (com.android.server.tv.TvInputManagerService.this.mLock) {
                    try {
                        com.android.server.tv.TvInputManagerService.this.getSessionLocked(iBinder, callingUid, resolveCallingUserId).timeShiftSetMode(i);
                    } catch (android.os.RemoteException | com.android.server.tv.TvInputManagerService.SessionNotFoundException e) {
                        android.util.Slog.e(com.android.server.tv.TvInputManagerService.TAG, "error in timeShiftSetMode", e);
                    }
                }
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void timeShiftEnablePositionTracking(android.os.IBinder iBinder, boolean z, int i) {
            int callingUid = android.os.Binder.getCallingUid();
            int resolveCallingUserId = com.android.server.tv.TvInputManagerService.this.resolveCallingUserId(android.os.Binder.getCallingPid(), callingUid, i, "timeShiftEnablePositionTracking");
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                synchronized (com.android.server.tv.TvInputManagerService.this.mLock) {
                    try {
                        com.android.server.tv.TvInputManagerService.this.getSessionLocked(iBinder, callingUid, resolveCallingUserId).timeShiftEnablePositionTracking(z);
                    } catch (android.os.RemoteException | com.android.server.tv.TvInputManagerService.SessionNotFoundException e) {
                        android.util.Slog.e(com.android.server.tv.TvInputManagerService.TAG, "error in timeShiftEnablePositionTracking", e);
                    }
                }
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void notifyTvMessage(android.os.IBinder iBinder, int i, android.os.Bundle bundle, int i2) {
            int callingUid = android.os.Binder.getCallingUid();
            int resolveCallingUserId = com.android.server.tv.TvInputManagerService.this.resolveCallingUserId(android.os.Binder.getCallingPid(), callingUid, i2, "notifyTvmessage");
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                synchronized (com.android.server.tv.TvInputManagerService.this.mLock) {
                    try {
                        com.android.server.tv.TvInputManagerService.this.getSessionLocked(iBinder, callingUid, resolveCallingUserId).notifyTvMessage(i, bundle);
                    } catch (android.os.RemoteException | com.android.server.tv.TvInputManagerService.SessionNotFoundException e) {
                        android.util.Slog.e(com.android.server.tv.TvInputManagerService.TAG, "error in notifyTvMessage", e);
                    }
                }
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void setVideoFrozen(android.os.IBinder iBinder, boolean z, int i) {
            int callingUid = android.os.Binder.getCallingUid();
            int resolveCallingUserId = com.android.server.tv.TvInputManagerService.this.resolveCallingUserId(android.os.Binder.getCallingPid(), callingUid, i, "setVideoFrozen");
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                synchronized (com.android.server.tv.TvInputManagerService.this.mLock) {
                    try {
                        com.android.server.tv.TvInputManagerService.this.getSessionLocked(iBinder, callingUid, resolveCallingUserId).setVideoFrozen(z);
                    } catch (android.os.RemoteException | com.android.server.tv.TvInputManagerService.SessionNotFoundException e) {
                        android.util.Slog.e(com.android.server.tv.TvInputManagerService.TAG, "error in setVideoFrozen", e);
                    }
                }
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void setTvMessageEnabled(android.os.IBinder iBinder, int i, boolean z, int i2) {
            int callingUid = android.os.Binder.getCallingUid();
            int resolveCallingUserId = com.android.server.tv.TvInputManagerService.this.resolveCallingUserId(android.os.Binder.getCallingPid(), callingUid, i2, "setTvMessageEnabled");
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                synchronized (com.android.server.tv.TvInputManagerService.this.mLock) {
                    try {
                        com.android.server.tv.TvInputManagerService.this.mTvInputHardwareManager.setTvMessageEnabled(com.android.server.tv.TvInputManagerService.this.getSessionStateLocked(iBinder, callingUid, i2).inputId, i, z);
                        com.android.server.tv.TvInputManagerService.this.getSessionLocked(iBinder, callingUid, resolveCallingUserId).setTvMessageEnabled(i, z);
                    } catch (android.os.RemoteException | com.android.server.tv.TvInputManagerService.SessionNotFoundException e) {
                        android.util.Slog.e(com.android.server.tv.TvInputManagerService.TAG, "error in setTvMessageEnabled", e);
                    }
                }
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void startRecording(android.os.IBinder iBinder, @android.annotation.Nullable android.net.Uri uri, @android.annotation.Nullable android.os.Bundle bundle, int i) {
            int callingUid = android.os.Binder.getCallingUid();
            int resolveCallingUserId = com.android.server.tv.TvInputManagerService.this.resolveCallingUserId(android.os.Binder.getCallingPid(), callingUid, i, "startRecording");
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                synchronized (com.android.server.tv.TvInputManagerService.this.mLock) {
                    try {
                        com.android.server.tv.TvInputManagerService.this.getSessionLocked(iBinder, callingUid, resolveCallingUserId).startRecording(uri, bundle);
                    } catch (android.os.RemoteException | com.android.server.tv.TvInputManagerService.SessionNotFoundException e) {
                        android.util.Slog.e(com.android.server.tv.TvInputManagerService.TAG, "error in startRecording", e);
                    }
                }
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void stopRecording(android.os.IBinder iBinder, int i) {
            int callingUid = android.os.Binder.getCallingUid();
            int resolveCallingUserId = com.android.server.tv.TvInputManagerService.this.resolveCallingUserId(android.os.Binder.getCallingPid(), callingUid, i, "stopRecording");
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                synchronized (com.android.server.tv.TvInputManagerService.this.mLock) {
                    try {
                        com.android.server.tv.TvInputManagerService.this.getSessionLocked(iBinder, callingUid, resolveCallingUserId).stopRecording();
                    } catch (android.os.RemoteException | com.android.server.tv.TvInputManagerService.SessionNotFoundException e) {
                        android.util.Slog.e(com.android.server.tv.TvInputManagerService.TAG, "error in stopRecording", e);
                    }
                }
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void pauseRecording(android.os.IBinder iBinder, @android.annotation.NonNull android.os.Bundle bundle, int i) {
            int callingUid = android.os.Binder.getCallingUid();
            int resolveCallingUserId = com.android.server.tv.TvInputManagerService.this.resolveCallingUserId(android.os.Binder.getCallingPid(), callingUid, i, "pauseRecording");
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                synchronized (com.android.server.tv.TvInputManagerService.this.mLock) {
                    try {
                        com.android.server.tv.TvInputManagerService.this.getSessionLocked(iBinder, callingUid, resolveCallingUserId).pauseRecording(bundle);
                    } catch (android.os.RemoteException | com.android.server.tv.TvInputManagerService.SessionNotFoundException e) {
                        android.util.Slog.e(com.android.server.tv.TvInputManagerService.TAG, "error in pauseRecording", e);
                    }
                }
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void resumeRecording(android.os.IBinder iBinder, @android.annotation.NonNull android.os.Bundle bundle, int i) {
            int callingUid = android.os.Binder.getCallingUid();
            int resolveCallingUserId = com.android.server.tv.TvInputManagerService.this.resolveCallingUserId(android.os.Binder.getCallingPid(), callingUid, i, "resumeRecording");
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                synchronized (com.android.server.tv.TvInputManagerService.this.mLock) {
                    try {
                        com.android.server.tv.TvInputManagerService.this.getSessionLocked(iBinder, callingUid, resolveCallingUserId).resumeRecording(bundle);
                    } catch (android.os.RemoteException | com.android.server.tv.TvInputManagerService.SessionNotFoundException e) {
                        android.util.Slog.e(com.android.server.tv.TvInputManagerService.TAG, "error in resumeRecording", e);
                    }
                }
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public java.util.List<android.media.tv.TvInputHardwareInfo> getHardwareList() throws android.os.RemoteException {
            if (com.android.server.tv.TvInputManagerService.this.mContext.checkCallingPermission("android.permission.TV_INPUT_HARDWARE") != 0) {
                return null;
            }
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                return com.android.server.tv.TvInputManagerService.this.mTvInputHardwareManager.getHardwareList();
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public android.media.tv.ITvInputHardware acquireTvInputHardware(int i, android.media.tv.ITvInputHardwareCallback iTvInputHardwareCallback, android.media.tv.TvInputInfo tvInputInfo, int i2, java.lang.String str, int i3) throws android.os.RemoteException {
            if (com.android.server.tv.TvInputManagerService.this.mContext.checkCallingPermission("android.permission.TV_INPUT_HARDWARE") != 0) {
                return null;
            }
            int callingUid = android.os.Binder.getCallingUid();
            int resolveCallingUserId = com.android.server.tv.TvInputManagerService.this.resolveCallingUserId(android.os.Binder.getCallingPid(), callingUid, i2, "acquireTvInputHardware");
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                return com.android.server.tv.TvInputManagerService.this.mTvInputHardwareManager.acquireHardware(i, iTvInputHardwareCallback, tvInputInfo, callingUid, resolveCallingUserId, str, i3);
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void releaseTvInputHardware(int i, android.media.tv.ITvInputHardware iTvInputHardware, int i2) throws android.os.RemoteException {
            if (com.android.server.tv.TvInputManagerService.this.mContext.checkCallingPermission("android.permission.TV_INPUT_HARDWARE") != 0) {
                return;
            }
            int callingUid = android.os.Binder.getCallingUid();
            int resolveCallingUserId = com.android.server.tv.TvInputManagerService.this.resolveCallingUserId(android.os.Binder.getCallingPid(), callingUid, i2, "releaseTvInputHardware");
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                com.android.server.tv.TvInputManagerService.this.mTvInputHardwareManager.releaseHardware(i, iTvInputHardware, callingUid, resolveCallingUserId);
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public java.util.List<android.media.tv.DvbDeviceInfo> getDvbDeviceList() throws android.os.RemoteException {
            if (com.android.server.tv.TvInputManagerService.this.mContext.checkCallingPermission("android.permission.DVB_DEVICE") != 0) {
                throw new java.lang.SecurityException("Requires DVB_DEVICE permission");
            }
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                java.util.ArrayList arrayList = new java.util.ArrayList();
                boolean z = false;
                for (java.lang.String str : new java.io.File("/dev").list()) {
                    java.util.regex.Matcher matcher = com.android.server.tv.TvInputManagerService.sFrontEndDevicePattern.matcher(str);
                    if (matcher.find()) {
                        arrayList.add(new android.media.tv.DvbDeviceInfo(java.lang.Integer.parseInt(matcher.group(1)), java.lang.Integer.parseInt(matcher.group(2))));
                    }
                    if (android.text.TextUtils.equals("dvb", str)) {
                        z = true;
                    }
                }
                if (!z) {
                    java.util.List<android.media.tv.DvbDeviceInfo> unmodifiableList = java.util.Collections.unmodifiableList(arrayList);
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                    return unmodifiableList;
                }
                java.io.File file = new java.io.File(com.android.server.tv.TvInputManagerService.DVB_DIRECTORY);
                java.util.ArrayList arrayList2 = new java.util.ArrayList();
                for (java.lang.String str2 : file.list()) {
                    java.util.regex.Matcher matcher2 = com.android.server.tv.TvInputManagerService.sAdapterDirPattern.matcher(str2);
                    if (matcher2.find()) {
                        int parseInt = java.lang.Integer.parseInt(matcher2.group(1));
                        java.lang.String[] list = new java.io.File("/dev/dvb/" + str2).list();
                        int length = list.length;
                        for (int i = 0; i < length; i++) {
                            java.util.regex.Matcher matcher3 = com.android.server.tv.TvInputManagerService.sFrontEndInAdapterDirPattern.matcher(list[i]);
                            if (matcher3.find()) {
                                arrayList2.add(new android.media.tv.DvbDeviceInfo(parseInt, java.lang.Integer.parseInt(matcher3.group(1))));
                            }
                        }
                    }
                }
                java.util.List<android.media.tv.DvbDeviceInfo> unmodifiableList2 = arrayList2.isEmpty() ? java.util.Collections.unmodifiableList(arrayList) : java.util.Collections.unmodifiableList(arrayList2);
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                return unmodifiableList2;
            } catch (java.lang.Throwable th) {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                throw th;
            }
        }

        public android.os.ParcelFileDescriptor openDvbDevice(android.media.tv.DvbDeviceInfo dvbDeviceInfo, int i) throws android.os.RemoteException {
            java.lang.String format;
            if (com.android.server.tv.TvInputManagerService.this.mContext.checkCallingPermission("android.permission.DVB_DEVICE") != 0) {
                throw new java.lang.SecurityException("Requires DVB_DEVICE permission");
            }
            boolean z = false;
            for (java.lang.String str : new java.io.File("/dev").list()) {
                if (android.text.TextUtils.equals("dvb", str)) {
                    for (java.lang.String str2 : new java.io.File(com.android.server.tv.TvInputManagerService.DVB_DIRECTORY).list()) {
                        if (com.android.server.tv.TvInputManagerService.sAdapterDirPattern.matcher(str2).find()) {
                            java.lang.String[] list = new java.io.File("/dev/dvb/" + str2).list();
                            int length = list.length;
                            int i2 = 0;
                            while (true) {
                                if (i2 >= length) {
                                    break;
                                }
                                if (com.android.server.tv.TvInputManagerService.sFrontEndInAdapterDirPattern.matcher(list[i2]).find()) {
                                    z = true;
                                    break;
                                }
                                i2++;
                            }
                        }
                        if (z) {
                            break;
                        }
                    }
                }
                if (z) {
                    break;
                }
            }
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                switch (i) {
                    case 0:
                        format = java.lang.String.format(z ? "/dev/dvb/adapter%d/demux%d" : "/dev/dvb%d.demux%d", java.lang.Integer.valueOf(dvbDeviceInfo.getAdapterId()), java.lang.Integer.valueOf(dvbDeviceInfo.getDeviceId()));
                        break;
                    case 1:
                        format = java.lang.String.format(z ? "/dev/dvb/adapter%d/dvr%d" : "/dev/dvb%d.dvr%d", java.lang.Integer.valueOf(dvbDeviceInfo.getAdapterId()), java.lang.Integer.valueOf(dvbDeviceInfo.getDeviceId()));
                        break;
                    case 2:
                        format = java.lang.String.format(z ? "/dev/dvb/adapter%d/frontend%d" : "/dev/dvb%d.frontend%d", java.lang.Integer.valueOf(dvbDeviceInfo.getAdapterId()), java.lang.Integer.valueOf(dvbDeviceInfo.getDeviceId()));
                        break;
                    default:
                        throw new java.lang.IllegalArgumentException("Invalid DVB device: " + i);
                }
                try {
                    android.os.ParcelFileDescriptor open = android.os.ParcelFileDescriptor.open(new java.io.File(format), 2 == i ? 805306368 : 268435456);
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                    return open;
                } catch (java.io.FileNotFoundException e) {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                    return null;
                }
            } catch (java.lang.Throwable th) {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                throw th;
            }
        }

        public java.util.List<android.media.tv.TvStreamConfig> getAvailableTvStreamConfigList(java.lang.String str, int i) throws android.os.RemoteException {
            ensureCaptureTvInputPermission();
            int callingUid = android.os.Binder.getCallingUid();
            int resolveCallingUserId = com.android.server.tv.TvInputManagerService.this.resolveCallingUserId(android.os.Binder.getCallingPid(), callingUid, i, "getAvailableTvStreamConfigList");
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                return com.android.server.tv.TvInputManagerService.this.mTvInputHardwareManager.getAvailableTvStreamConfigList(str, callingUid, resolveCallingUserId);
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public boolean captureFrame(java.lang.String str, android.view.Surface surface, android.media.tv.TvStreamConfig tvStreamConfig, int i) throws android.os.RemoteException {
            java.lang.String str2;
            ensureCaptureTvInputPermission();
            int callingUid = android.os.Binder.getCallingUid();
            int resolveCallingUserId = com.android.server.tv.TvInputManagerService.this.resolveCallingUserId(android.os.Binder.getCallingPid(), callingUid, i, "captureFrame");
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                synchronized (com.android.server.tv.TvInputManagerService.this.mLock) {
                    com.android.server.tv.TvInputManagerService.UserState orCreateUserStateLocked = com.android.server.tv.TvInputManagerService.this.getOrCreateUserStateLocked(resolveCallingUserId);
                    if (orCreateUserStateLocked.inputMap.get(str) == null) {
                        android.util.Slog.e(com.android.server.tv.TvInputManagerService.TAG, "input not found for " + str);
                        android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                        return false;
                    }
                    java.util.Iterator it = orCreateUserStateLocked.sessionStateMap.values().iterator();
                    while (true) {
                        if (!it.hasNext()) {
                            str2 = null;
                            break;
                        }
                        com.android.server.tv.TvInputManagerService.SessionState sessionState = (com.android.server.tv.TvInputManagerService.SessionState) it.next();
                        if (sessionState.inputId.equals(str) && sessionState.hardwareSessionToken != null) {
                            str2 = ((com.android.server.tv.TvInputManagerService.SessionState) orCreateUserStateLocked.sessionStateMap.get(sessionState.hardwareSessionToken)).inputId;
                            break;
                        }
                    }
                    return com.android.server.tv.TvInputManagerService.this.mTvInputHardwareManager.captureFrame(str2 != null ? str2 : str, surface, tvStreamConfig, callingUid, resolveCallingUserId);
                }
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public boolean isSingleSessionActive(int i) throws android.os.RemoteException {
            ensureCaptureTvInputPermission();
            int resolveCallingUserId = com.android.server.tv.TvInputManagerService.this.resolveCallingUserId(android.os.Binder.getCallingPid(), android.os.Binder.getCallingUid(), i, "isSingleSessionActive");
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                synchronized (com.android.server.tv.TvInputManagerService.this.mLock) {
                    com.android.server.tv.TvInputManagerService.UserState orCreateUserStateLocked = com.android.server.tv.TvInputManagerService.this.getOrCreateUserStateLocked(resolveCallingUserId);
                    boolean z = true;
                    if (orCreateUserStateLocked.sessionStateMap.size() == 1) {
                        return true;
                    }
                    if (orCreateUserStateLocked.sessionStateMap.size() != 2) {
                        return false;
                    }
                    com.android.server.tv.TvInputManagerService.SessionState[] sessionStateArr = (com.android.server.tv.TvInputManagerService.SessionState[]) orCreateUserStateLocked.sessionStateMap.values().toArray(new com.android.server.tv.TvInputManagerService.SessionState[2]);
                    if (sessionStateArr[0].hardwareSessionToken == null && sessionStateArr[1].hardwareSessionToken == null) {
                        z = false;
                    }
                    return z;
                }
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        private void ensureCaptureTvInputPermission() {
            if (com.android.server.tv.TvInputManagerService.this.mContext.checkCallingPermission("android.permission.CAPTURE_TV_INPUT") != 0) {
                throw new java.lang.SecurityException("Requires CAPTURE_TV_INPUT permission");
            }
        }

        public void requestChannelBrowsable(android.net.Uri uri, int i) throws android.os.RemoteException {
            java.lang.String callingPackageName = getCallingPackageName();
            int resolveCallingUserId = com.android.server.tv.TvInputManagerService.this.resolveCallingUserId(android.os.Binder.getCallingPid(), android.os.Binder.getCallingUid(), i, "requestChannelBrowsable");
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                android.content.Intent intent = new android.content.Intent("android.media.tv.action.CHANNEL_BROWSABLE_REQUESTED");
                java.util.List<android.content.pm.ResolveInfo> queryBroadcastReceivers = com.android.server.tv.TvInputManagerService.this.getContext().getPackageManager().queryBroadcastReceivers(intent, 0);
                if (queryBroadcastReceivers != null) {
                    java.util.Iterator<android.content.pm.ResolveInfo> it = queryBroadcastReceivers.iterator();
                    while (it.hasNext()) {
                        java.lang.String str = it.next().activityInfo.packageName;
                        intent.putExtra("android.media.tv.extra.CHANNEL_ID", android.content.ContentUris.parseId(uri));
                        intent.putExtra("android.media.tv.extra.PACKAGE_NAME", callingPackageName);
                        intent.setPackage(str);
                        com.android.server.tv.TvInputManagerService.this.getContext().sendBroadcastAsUser(intent, new android.os.UserHandle(resolveCallingUserId));
                    }
                }
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void requestBroadcastInfo(android.os.IBinder iBinder, android.media.tv.BroadcastInfoRequest broadcastInfoRequest, int i) {
            int callingUid = android.os.Binder.getCallingUid();
            int resolveCallingUserId = com.android.server.tv.TvInputManagerService.this.resolveCallingUserId(android.os.Binder.getCallingPid(), callingUid, i, "requestBroadcastInfo");
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                synchronized (com.android.server.tv.TvInputManagerService.this.mLock) {
                    try {
                        com.android.server.tv.TvInputManagerService.this.getSessionLocked(com.android.server.tv.TvInputManagerService.this.getSessionStateLocked(iBinder, callingUid, resolveCallingUserId)).requestBroadcastInfo(broadcastInfoRequest);
                    } catch (android.os.RemoteException | com.android.server.tv.TvInputManagerService.SessionNotFoundException e) {
                        android.util.Slog.e(com.android.server.tv.TvInputManagerService.TAG, "error in requestBroadcastInfo", e);
                    }
                }
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void removeBroadcastInfo(android.os.IBinder iBinder, int i, int i2) {
            int callingUid = android.os.Binder.getCallingUid();
            int resolveCallingUserId = com.android.server.tv.TvInputManagerService.this.resolveCallingUserId(android.os.Binder.getCallingPid(), callingUid, i2, "removeBroadcastInfo");
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                synchronized (com.android.server.tv.TvInputManagerService.this.mLock) {
                    try {
                        com.android.server.tv.TvInputManagerService.this.getSessionLocked(com.android.server.tv.TvInputManagerService.this.getSessionStateLocked(iBinder, callingUid, resolveCallingUserId)).removeBroadcastInfo(i);
                    } catch (android.os.RemoteException | com.android.server.tv.TvInputManagerService.SessionNotFoundException e) {
                        android.util.Slog.e(com.android.server.tv.TvInputManagerService.TAG, "error in removeBroadcastInfo", e);
                    }
                }
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void requestAd(android.os.IBinder iBinder, android.media.tv.AdRequest adRequest, int i) {
            int callingUid = android.os.Binder.getCallingUid();
            int resolveCallingUserId = com.android.server.tv.TvInputManagerService.this.resolveCallingUserId(android.os.Binder.getCallingPid(), callingUid, i, "requestAd");
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                synchronized (com.android.server.tv.TvInputManagerService.this.mLock) {
                    try {
                        com.android.server.tv.TvInputManagerService.this.getSessionLocked(com.android.server.tv.TvInputManagerService.this.getSessionStateLocked(iBinder, callingUid, resolveCallingUserId)).requestAd(adRequest);
                    } catch (android.os.RemoteException | com.android.server.tv.TvInputManagerService.SessionNotFoundException e) {
                        android.util.Slog.e(com.android.server.tv.TvInputManagerService.TAG, "error in requestAd", e);
                    }
                }
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void notifyAdBufferReady(android.os.IBinder iBinder, android.media.tv.AdBuffer adBuffer, int i) {
            android.os.SharedMemory sharedMemory;
            int callingUid = android.os.Binder.getCallingUid();
            int resolveCallingUserId = com.android.server.tv.TvInputManagerService.this.resolveCallingUserId(android.os.Binder.getCallingPid(), callingUid, i, "notifyAdBuffer");
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                try {
                    synchronized (com.android.server.tv.TvInputManagerService.this.mLock) {
                        try {
                            com.android.server.tv.TvInputManagerService.this.getSessionLocked(com.android.server.tv.TvInputManagerService.this.getSessionStateLocked(iBinder, callingUid, resolveCallingUserId)).notifyAdBufferReady(adBuffer);
                        } catch (android.os.RemoteException | com.android.server.tv.TvInputManagerService.SessionNotFoundException e) {
                            android.util.Slog.e(com.android.server.tv.TvInputManagerService.TAG, "error in notifyAdBuffer", e);
                            if (adBuffer != null) {
                                sharedMemory = adBuffer.getSharedMemory();
                            }
                        }
                        if (adBuffer != null) {
                            sharedMemory = adBuffer.getSharedMemory();
                            sharedMemory.close();
                        }
                    }
                } catch (java.lang.Throwable th) {
                    if (adBuffer != null) {
                        adBuffer.getSharedMemory().close();
                    }
                    throw th;
                }
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void notifyTvAdSessionData(android.os.IBinder iBinder, java.lang.String str, android.os.Bundle bundle, int i) {
            int callingUid = android.os.Binder.getCallingUid();
            int resolveCallingUserId = com.android.server.tv.TvInputManagerService.this.resolveCallingUserId(android.os.Binder.getCallingPid(), callingUid, i, "notifyTvAdSessionData");
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                synchronized (com.android.server.tv.TvInputManagerService.this.mLock) {
                    try {
                        com.android.server.tv.TvInputManagerService.this.getSessionLocked(com.android.server.tv.TvInputManagerService.this.getSessionStateLocked(iBinder, callingUid, resolveCallingUserId)).notifyTvAdSessionData(str, bundle);
                    } catch (android.os.RemoteException | com.android.server.tv.TvInputManagerService.SessionNotFoundException e) {
                        android.util.Slog.e(com.android.server.tv.TvInputManagerService.TAG, "error in notifyTvAdSessionData", e);
                    }
                }
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public int getClientPid(java.lang.String str) {
            int i;
            ensureTunerResourceAccessPermission();
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                synchronized (com.android.server.tv.TvInputManagerService.this.mLock) {
                    try {
                        i = getClientPidLocked(str);
                    } catch (com.android.server.tv.TvInputManagerService.ClientPidNotFoundException e) {
                        android.util.Slog.e(com.android.server.tv.TvInputManagerService.TAG, "error in getClientPid", e);
                        i = -1;
                    }
                }
                return i;
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public int getClientPriority(int i, java.lang.String str) {
            int i2;
            ensureTunerResourceAccessPermission();
            int callingPid = android.os.Binder.getCallingPid();
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            if (str != null) {
                try {
                    synchronized (com.android.server.tv.TvInputManagerService.this.mLock) {
                        try {
                            i2 = getClientPidLocked(str);
                        } catch (com.android.server.tv.TvInputManagerService.ClientPidNotFoundException e) {
                            android.util.Slog.e(com.android.server.tv.TvInputManagerService.TAG, "error in getClientPriority", e);
                            i2 = -1;
                        }
                    }
                    callingPid = i2;
                } catch (java.lang.Throwable th) {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                    throw th;
                }
            }
            int clientPriority = ((android.media.tv.tunerresourcemanager.TunerResourceManager) com.android.server.tv.TvInputManagerService.this.mContext.getSystemService("tv_tuner_resource_mgr")).getClientPriority(i, callingPid);
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            return clientPriority;
        }

        public java.util.List<android.media.tv.TunedInfo> getCurrentTunedInfos(int i) {
            java.util.List<android.media.tv.TunedInfo> currentTunedInfosInternalLocked;
            if (com.android.server.tv.TvInputManagerService.this.mContext.checkCallingPermission("android.permission.ACCESS_TUNED_INFO") != 0) {
                throw new java.lang.SecurityException("The caller does not have access tuned info permission");
            }
            int callingPid = android.os.Binder.getCallingPid();
            int callingUid = android.os.Binder.getCallingUid();
            int resolveCallingUserId = com.android.server.tv.TvInputManagerService.this.resolveCallingUserId(callingPid, callingUid, i, "getTvCurrentChannelInfos");
            synchronized (com.android.server.tv.TvInputManagerService.this.mLock) {
                currentTunedInfosInternalLocked = com.android.server.tv.TvInputManagerService.this.getCurrentTunedInfosInternalLocked(com.android.server.tv.TvInputManagerService.this.getOrCreateUserStateLocked(resolveCallingUserId), callingPid, callingUid);
            }
            return currentTunedInfosInternalLocked;
        }

        public void addHardwareDevice(int i) {
            com.android.server.tv.TvInputManagerService.this.mTvInputHardwareManager.onDeviceAvailable(new android.media.tv.TvInputHardwareInfo.Builder().deviceId(i).type(9).audioType(0).audioAddress("0").hdmiPortId(0).build(), new android.media.tv.TvStreamConfig[]{new android.media.tv.TvStreamConfig.Builder().streamId(19001).generation(1).maxHeight(600).maxWidth(com.android.server.am.ProcessList.SERVICE_B_ADJ).type(1).build()});
        }

        public void removeHardwareDevice(int i) {
            com.android.server.tv.TvInputManagerService.this.mTvInputHardwareManager.onDeviceUnavailable(i);
        }

        @com.android.internal.annotations.GuardedBy({"mLock"})
        private int getClientPidLocked(java.lang.String str) throws com.android.server.tv.TvInputManagerService.ClientPidNotFoundException {
            if (com.android.server.tv.TvInputManagerService.this.mSessionIdToSessionStateMap.get(str) == null) {
                throw new com.android.server.tv.TvInputManagerService.ClientPidNotFoundException("Client Pid not found with sessionId " + str);
            }
            return ((com.android.server.tv.TvInputManagerService.SessionState) com.android.server.tv.TvInputManagerService.this.mSessionIdToSessionStateMap.get(str)).callingPid;
        }

        private void ensureTunerResourceAccessPermission() {
            if (com.android.server.tv.TvInputManagerService.this.mContext.checkCallingPermission("android.permission.TUNER_RESOURCE_ACCESS") != 0) {
                throw new java.lang.SecurityException("Requires TUNER_RESOURCE_ACCESS permission");
            }
        }

        private void ensureTisExtensionInterfacePermission() {
            if (com.android.server.tv.TvInputManagerService.this.mContext.checkCallingPermission("android.permission.TIS_EXTENSION_INTERFACE") != 0) {
                throw new java.lang.SecurityException("Requires TIS_EXTENSION_INTERFACE permission");
            }
        }

        @dalvik.annotation.optimization.NeverCompile
        protected void dump(java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, java.lang.String[] strArr) {
            com.android.internal.util.IndentingPrintWriter indentingPrintWriter = new com.android.internal.util.IndentingPrintWriter(printWriter, "  ");
            if (com.android.internal.util.DumpUtils.checkDumpPermission(com.android.server.tv.TvInputManagerService.this.mContext, com.android.server.tv.TvInputManagerService.TAG, indentingPrintWriter)) {
                synchronized (com.android.server.tv.TvInputManagerService.this.mLock) {
                    try {
                        indentingPrintWriter.println("User Ids (Current user: " + com.android.server.tv.TvInputManagerService.this.mCurrentUserId + "):");
                        indentingPrintWriter.increaseIndent();
                        for (int i = 0; i < com.android.server.tv.TvInputManagerService.this.mUserStates.size(); i++) {
                            indentingPrintWriter.println(java.lang.Integer.valueOf(com.android.server.tv.TvInputManagerService.this.mUserStates.keyAt(i)));
                        }
                        indentingPrintWriter.decreaseIndent();
                        for (int i2 = 0; i2 < com.android.server.tv.TvInputManagerService.this.mUserStates.size(); i2++) {
                            int keyAt = com.android.server.tv.TvInputManagerService.this.mUserStates.keyAt(i2);
                            com.android.server.tv.TvInputManagerService.UserState orCreateUserStateLocked = com.android.server.tv.TvInputManagerService.this.getOrCreateUserStateLocked(keyAt);
                            indentingPrintWriter.println("UserState (" + keyAt + "):");
                            indentingPrintWriter.increaseIndent();
                            indentingPrintWriter.println("inputMap: inputId -> TvInputState");
                            indentingPrintWriter.increaseIndent();
                            for (java.util.Map.Entry entry : orCreateUserStateLocked.inputMap.entrySet()) {
                                indentingPrintWriter.println(((java.lang.String) entry.getKey()) + ": " + entry.getValue());
                            }
                            indentingPrintWriter.decreaseIndent();
                            indentingPrintWriter.println("packageSet:");
                            indentingPrintWriter.increaseIndent();
                            java.util.Iterator it = orCreateUserStateLocked.packageSet.iterator();
                            while (it.hasNext()) {
                                indentingPrintWriter.println((java.lang.String) it.next());
                            }
                            indentingPrintWriter.decreaseIndent();
                            indentingPrintWriter.println("clientStateMap: ITvInputClient -> ClientState");
                            indentingPrintWriter.increaseIndent();
                            for (java.util.Map.Entry entry2 : orCreateUserStateLocked.clientStateMap.entrySet()) {
                                com.android.server.tv.TvInputManagerService.ClientState clientState = (com.android.server.tv.TvInputManagerService.ClientState) entry2.getValue();
                                indentingPrintWriter.println(entry2.getKey() + ": " + clientState);
                                indentingPrintWriter.increaseIndent();
                                indentingPrintWriter.println("sessionTokens:");
                                indentingPrintWriter.increaseIndent();
                                java.util.Iterator it2 = clientState.sessionTokens.iterator();
                                while (it2.hasNext()) {
                                    indentingPrintWriter.println("" + ((android.os.IBinder) it2.next()));
                                }
                                indentingPrintWriter.decreaseIndent();
                                indentingPrintWriter.println("clientTokens: " + clientState.clientToken);
                                indentingPrintWriter.println("userId: " + clientState.userId);
                                indentingPrintWriter.decreaseIndent();
                            }
                            indentingPrintWriter.decreaseIndent();
                            indentingPrintWriter.println("serviceStateMap: ComponentName -> ServiceState");
                            indentingPrintWriter.increaseIndent();
                            for (java.util.Map.Entry entry3 : orCreateUserStateLocked.serviceStateMap.entrySet()) {
                                com.android.server.tv.TvInputManagerService.ServiceState serviceState = (com.android.server.tv.TvInputManagerService.ServiceState) entry3.getValue();
                                indentingPrintWriter.println(entry3.getKey() + ": " + serviceState);
                                indentingPrintWriter.increaseIndent();
                                indentingPrintWriter.println("sessionTokens:");
                                indentingPrintWriter.increaseIndent();
                                java.util.Iterator it3 = serviceState.sessionTokens.iterator();
                                while (it3.hasNext()) {
                                    indentingPrintWriter.println("" + ((android.os.IBinder) it3.next()));
                                }
                                indentingPrintWriter.decreaseIndent();
                                indentingPrintWriter.println("service: " + serviceState.service);
                                indentingPrintWriter.println("callback: " + serviceState.callback);
                                indentingPrintWriter.println("bound: " + serviceState.bound);
                                indentingPrintWriter.println("reconnecting: " + serviceState.reconnecting);
                                indentingPrintWriter.decreaseIndent();
                            }
                            indentingPrintWriter.decreaseIndent();
                            indentingPrintWriter.println("sessionStateMap: ITvInputSession -> SessionState");
                            indentingPrintWriter.increaseIndent();
                            for (java.util.Map.Entry entry4 : orCreateUserStateLocked.sessionStateMap.entrySet()) {
                                com.android.server.tv.TvInputManagerService.SessionState sessionState = (com.android.server.tv.TvInputManagerService.SessionState) entry4.getValue();
                                indentingPrintWriter.println(entry4.getKey() + ": " + sessionState);
                                indentingPrintWriter.increaseIndent();
                                indentingPrintWriter.println("inputId: " + sessionState.inputId);
                                indentingPrintWriter.println("sessionId: " + sessionState.sessionId);
                                indentingPrintWriter.println("client: " + sessionState.client);
                                indentingPrintWriter.println("seq: " + sessionState.seq);
                                indentingPrintWriter.println("callingUid: " + sessionState.callingUid);
                                indentingPrintWriter.println("callingPid: " + sessionState.callingPid);
                                indentingPrintWriter.println("userId: " + sessionState.userId);
                                indentingPrintWriter.println("sessionToken: " + sessionState.sessionToken);
                                indentingPrintWriter.println("session: " + sessionState.session);
                                indentingPrintWriter.println("hardwareSessionToken: " + sessionState.hardwareSessionToken);
                                indentingPrintWriter.decreaseIndent();
                            }
                            indentingPrintWriter.decreaseIndent();
                            indentingPrintWriter.println("mCallbacks:");
                            indentingPrintWriter.increaseIndent();
                            int beginBroadcast = orCreateUserStateLocked.mCallbacks.beginBroadcast();
                            for (int i3 = 0; i3 < beginBroadcast; i3++) {
                                indentingPrintWriter.println(orCreateUserStateLocked.mCallbacks.getRegisteredCallbackItem(i3));
                            }
                            orCreateUserStateLocked.mCallbacks.finishBroadcast();
                            indentingPrintWriter.decreaseIndent();
                            indentingPrintWriter.println("mainSessionToken: " + orCreateUserStateLocked.mainSessionToken);
                            indentingPrintWriter.decreaseIndent();
                        }
                    } catch (java.lang.Throwable th) {
                        throw th;
                    }
                }
                com.android.server.tv.TvInputManagerService.this.mTvInputHardwareManager.dump(fileDescriptor, printWriter, strArr);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public java.lang.String getSessionActualInputId(com.android.server.tv.TvInputManagerService.SessionState sessionState) {
        com.android.server.tv.TvInputManagerService.TvInputState tvInputState = (com.android.server.tv.TvInputManagerService.TvInputState) getOrCreateUserStateLocked(sessionState.userId).inputMap.get(sessionState.inputId);
        if (tvInputState == null) {
            android.util.Slog.w(TAG, "No TvInputState for sessionState.inputId " + sessionState.inputId);
            return sessionState.inputId;
        }
        android.media.tv.TvInputInfo tvInputInfo = tvInputState.info;
        if (tvInputInfo == null) {
            android.util.Slog.w(TAG, "TvInputInfo is null for input id " + sessionState.inputId);
            return sessionState.inputId;
        }
        java.lang.String str = sessionState.inputId;
        switch (tvInputInfo.getType()) {
            case 1007:
                java.util.Map<java.lang.String, java.util.List<java.lang.String>> hdmiParentInputMap = this.mTvInputHardwareManager.getHdmiParentInputMap();
                if (hdmiParentInputMap.containsKey(sessionState.inputId)) {
                    break;
                }
                break;
        }
        return sessionState.inputId;
    }

    /* JADX INFO: Access modifiers changed from: private */
    @android.annotation.Nullable
    public static com.android.server.tv.TvInputManagerService.TvInputState getTvInputState(com.android.server.tv.TvInputManagerService.SessionState sessionState, @android.annotation.Nullable com.android.server.tv.TvInputManagerService.UserState userState) {
        if (userState != null) {
            return (com.android.server.tv.TvInputManagerService.TvInputState) userState.inputMap.get(sessionState.inputId);
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public java.util.List<android.media.tv.TunedInfo> getCurrentTunedInfosInternalLocked(com.android.server.tv.TvInputManagerService.UserState userState, int i, int i2) {
        java.lang.Integer num;
        int i3;
        int i4;
        java.util.ArrayList arrayList = new java.util.ArrayList();
        boolean hasAccessWatchedProgramsPermission = hasAccessWatchedProgramsPermission(i, i2);
        for (com.android.server.tv.TvInputManagerService.SessionState sessionState : userState.sessionStateMap.values()) {
            if (sessionState.isCurrent) {
                if (sessionState.callingUid == i2) {
                    num = 0;
                    i4 = 1;
                } else {
                    num = (java.lang.Integer) userState.mAppTagMap.get(java.lang.Integer.valueOf(sessionState.callingUid));
                    if (num == null) {
                        int i5 = userState.mNextAppTag;
                        userState.mNextAppTag = i5 + 1;
                        num = java.lang.Integer.valueOf(i5);
                        userState.mAppTagMap.put(java.lang.Integer.valueOf(sessionState.callingUid), num);
                    }
                    if (isSystemApp(sessionState.componentName.getPackageName())) {
                        i3 = 2;
                    } else {
                        i3 = 3;
                    }
                    i4 = i3;
                }
                arrayList.add(new android.media.tv.TunedInfo(sessionState.inputId, hasAccessWatchedProgramsPermission ? sessionState.currentChannel : null, sessionState.isRecordingSession, sessionState.isVisible, sessionState.isMainSession, i4, num.intValue()));
            }
        }
        return arrayList;
    }

    private boolean hasAccessWatchedProgramsPermission(int i, int i2) {
        return this.mContext.checkPermission(PERMISSION_ACCESS_WATCHED_PROGRAMS, i, i2) == 0;
    }

    private boolean isSystemApp(java.lang.String str) {
        try {
            return (this.mContext.getPackageManager().getApplicationInfo(str, 0).flags & 1) != 0;
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void logTuneStateChanged(int i, com.android.server.tv.TvInputManagerService.SessionState sessionState, @android.annotation.Nullable com.android.server.tv.TvInputManagerService.TvInputState tvInputState) {
        int i2;
        int i3;
        int i4;
        int i5;
        if (tvInputState == null) {
            i2 = -1;
            i3 = 0;
            i4 = 0;
            i5 = 0;
        } else {
            i2 = tvInputState.uid;
            int type = tvInputState.info.getType();
            if (type == 0) {
                type = 1;
            }
            int i6 = tvInputState.inputNumber;
            android.hardware.hdmi.HdmiDeviceInfo hdmiDeviceInfo = tvInputState.info.getHdmiDeviceInfo();
            if (hdmiDeviceInfo == null) {
                i5 = 0;
                i3 = type;
                i4 = i6;
            } else {
                i5 = hdmiDeviceInfo.getPortId();
                i3 = type;
                i4 = i6;
            }
        }
        com.android.internal.util.FrameworkStatsLog.write(com.android.internal.util.FrameworkStatsLog.TIF_TUNE_CHANGED, new int[]{sessionState.callingUid, i2}, new java.lang.String[]{"tif_player", "tv_input_service"}, i, sessionState.sessionId, i3, i4, i5);
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public void logExternalInputEvent(int i, java.lang.String str, com.android.server.tv.TvInputManagerService.SessionState sessionState) {
        java.lang.String str2;
        int i2;
        int i3;
        android.hardware.hdmi.HdmiDeviceInfo hdmiDeviceInfo;
        com.android.server.tv.TvInputManagerService.UserState orCreateUserStateLocked = getOrCreateUserStateLocked(sessionState.userId);
        com.android.server.tv.TvInputManagerService.TvInputState tvInputState = (com.android.server.tv.TvInputManagerService.TvInputState) orCreateUserStateLocked.inputMap.get(str);
        if (tvInputState == null) {
            android.util.Slog.w(TAG, "Cannot find input state for input id " + str);
            str = sessionState.inputId;
            tvInputState = (com.android.server.tv.TvInputManagerService.TvInputState) orCreateUserStateLocked.inputMap.get(str);
        }
        if (tvInputState == null) {
            android.util.Slog.w(TAG, "Cannot find input state for sessionState.inputId " + str);
            return;
        }
        android.media.tv.TvInputInfo tvInputInfo = tvInputState.info;
        if (tvInputInfo == null) {
            android.util.Slog.w(TAG, "TvInputInfo is null for input id " + str);
            return;
        }
        int i4 = tvInputState.state;
        int type = tvInputInfo.getType();
        java.lang.String charSequence = tvInputInfo.loadLabel(this.mContext).toString();
        java.lang.String str3 = sessionState.sessionId;
        if (tvInputInfo.getType() == 1007 && (hdmiDeviceInfo = tvInputInfo.getHdmiDeviceInfo()) != null) {
            int portId = hdmiDeviceInfo.getPortId();
            if (!hdmiDeviceInfo.isCecDevice()) {
                str2 = charSequence;
                i2 = portId;
                i3 = 16777215;
            } else {
                java.lang.String displayName = hdmiDeviceInfo.getDisplayName();
                if (this.mExternalInputLoggingDisplayNameFilterEnabled) {
                    displayName = filterExternalInputLoggingDisplayName(displayName);
                }
                str2 = displayName;
                i3 = hdmiDeviceInfo.getVendorId();
                i2 = portId;
            }
        } else {
            str2 = charSequence;
            i2 = -1;
            i3 = 16777215;
        }
        com.android.internal.util.FrameworkStatsLog.write(com.android.internal.util.FrameworkStatsLog.EXTERNAL_TV_INPUT_EVENT, i, i4, type, i3, i2, str3, str2);
    }

    private java.lang.String filterExternalInputLoggingDisplayName(java.lang.String str) {
        if (str == null) {
            return "NULL_DISPLAY_NAME";
        }
        if (this.mExternalInputLoggingDeviceOnScreenDisplayNames.contains(str)) {
            return str;
        }
        for (java.lang.String str2 : this.mExternalInputLoggingDeviceBrandNames) {
            if (str.toUpperCase().contains(str2.toUpperCase())) {
                return str2;
            }
        }
        return "FILTERED_DISPLAY_NAME";
    }

    private static final class UserState {
        private final java.util.Map<android.media.tv.ITvInputManagerCallback, android.util.Pair<java.lang.Integer, java.lang.Integer>> callbackPidUidMap;
        private final java.util.Map<android.os.IBinder, com.android.server.tv.TvInputManagerService.ClientState> clientStateMap;
        private final java.util.List<android.media.tv.TvContentRatingSystemInfo> contentRatingSystemList;
        private java.util.Map<java.lang.String, com.android.server.tv.TvInputManagerService.TvInputState> inputMap;

        @com.android.internal.annotations.GuardedBy({"TvInputManagerService.this.mLock"})
        private final java.util.Map<java.lang.Integer, java.lang.Integer> mAppTagMap;
        private final android.os.RemoteCallbackList<android.media.tv.ITvInputManagerCallback> mCallbacks;

        @com.android.internal.annotations.GuardedBy({"TvInputManagerService.this.mLock"})
        private int mNextAppTag;
        private android.os.IBinder mainSessionToken;
        private final java.util.Set<java.lang.String> packageSet;
        private final com.android.server.tv.PersistentDataStore persistentDataStore;
        private final java.util.Map<android.content.ComponentName, com.android.server.tv.TvInputManagerService.ServiceState> serviceStateMap;
        private final java.util.Map<android.os.IBinder, com.android.server.tv.TvInputManagerService.SessionState> sessionStateMap;

        private UserState(android.content.Context context, int i) {
            this.inputMap = new java.util.HashMap();
            this.packageSet = new java.util.HashSet();
            this.contentRatingSystemList = new java.util.ArrayList();
            this.clientStateMap = new java.util.HashMap();
            this.serviceStateMap = new java.util.HashMap();
            this.sessionStateMap = new java.util.HashMap();
            this.mCallbacks = new android.os.RemoteCallbackList<>();
            this.callbackPidUidMap = new java.util.HashMap();
            this.mainSessionToken = null;
            this.mAppTagMap = new java.util.HashMap();
            this.mNextAppTag = 1;
            this.persistentDataStore = new com.android.server.tv.PersistentDataStore(context, i);
        }
    }

    private final class ClientState implements android.os.IBinder.DeathRecipient {
        private android.os.IBinder clientToken;
        private final java.util.List<android.os.IBinder> sessionTokens = new java.util.ArrayList();
        private final int userId;

        ClientState(android.os.IBinder iBinder, int i) {
            this.clientToken = iBinder;
            this.userId = i;
        }

        public boolean isEmpty() {
            return this.sessionTokens.isEmpty();
        }

        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
            synchronized (com.android.server.tv.TvInputManagerService.this.mLock) {
                try {
                    com.android.server.tv.TvInputManagerService.ClientState clientState = (com.android.server.tv.TvInputManagerService.ClientState) com.android.server.tv.TvInputManagerService.this.getOrCreateUserStateLocked(this.userId).clientStateMap.get(this.clientToken);
                    if (clientState != null) {
                        while (clientState.sessionTokens.size() > 0) {
                            android.os.IBinder iBinder = clientState.sessionTokens.get(0);
                            com.android.server.tv.TvInputManagerService.this.releaseSessionLocked(iBinder, 1000, this.userId);
                            if (clientState.sessionTokens.contains(iBinder)) {
                                android.util.Slog.d(com.android.server.tv.TvInputManagerService.TAG, "remove sessionToken " + iBinder + " for " + this.clientToken);
                                clientState.sessionTokens.remove(iBinder);
                            }
                        }
                    }
                    this.clientToken = null;
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
    }

    private final class ServiceState {
        private boolean bound;
        private com.android.server.tv.TvInputManagerService.ServiceCallback callback;
        private final android.content.ComponentName component;
        private final android.content.ServiceConnection connection;
        private final java.util.List<android.media.tv.TvInputHardwareInfo> hardwareDeviceRemovedBuffer;
        private final java.util.Map<java.lang.String, android.media.tv.TvInputInfo> hardwareInputMap;
        private final java.util.List<android.hardware.hdmi.HdmiDeviceInfo> hdmiDeviceRemovedBuffer;
        private final java.util.List<android.hardware.hdmi.HdmiDeviceInfo> hdmiDeviceUpdatedBuffer;
        private final boolean isHardware;
        private boolean neverConnected;
        private boolean reconnecting;
        private android.media.tv.ITvInputService service;
        private final java.util.List<android.os.IBinder> sessionTokens;

        private ServiceState(android.content.ComponentName componentName, int i) {
            this.sessionTokens = new java.util.ArrayList();
            this.hardwareInputMap = new java.util.HashMap();
            this.hardwareDeviceRemovedBuffer = new java.util.ArrayList();
            this.hdmiDeviceRemovedBuffer = new java.util.ArrayList();
            this.hdmiDeviceUpdatedBuffer = new java.util.ArrayList();
            this.component = componentName;
            this.connection = new com.android.server.tv.TvInputManagerService.InputServiceConnection(componentName, i);
            this.isHardware = com.android.server.tv.TvInputManagerService.hasHardwarePermission(com.android.server.tv.TvInputManagerService.this.mContext.getPackageManager(), componentName);
            this.neverConnected = true;
        }
    }

    private static final class TvInputState {
        private android.media.tv.TvInputInfo info;
        private int inputNumber;
        private int state;
        private int uid;

        private TvInputState() {
            this.state = 0;
        }

        public java.lang.String toString() {
            return "info: " + this.info + "; state: " + this.state;
        }
    }

    private final class SessionState implements android.os.IBinder.DeathRecipient {
        private final int callingPid;
        private final int callingUid;
        private final android.media.tv.ITvInputClient client;
        private final android.content.ComponentName componentName;
        private android.net.Uri currentChannel;
        private android.os.IBinder hardwareSessionToken;
        private final java.lang.String inputId;
        private boolean isCurrent;
        private boolean isMainSession;
        private final boolean isRecordingSession;
        private boolean isVisible;
        private final int seq;
        private android.media.tv.ITvInputSession session;
        private final java.lang.String sessionId;
        private final android.os.IBinder sessionToken;
        private final android.content.AttributionSource tvAppAttributionSource;
        private final int userId;

        private SessionState(android.os.IBinder iBinder, java.lang.String str, android.content.ComponentName componentName, boolean z, android.media.tv.ITvInputClient iTvInputClient, int i, int i2, int i3, int i4, java.lang.String str2, android.content.AttributionSource attributionSource) {
            this.isCurrent = false;
            this.currentChannel = null;
            this.isVisible = false;
            this.isMainSession = false;
            this.sessionToken = iBinder;
            this.inputId = str;
            this.componentName = componentName;
            this.isRecordingSession = z;
            this.client = iTvInputClient;
            this.seq = i;
            this.callingUid = i2;
            this.callingPid = i3;
            this.userId = i4;
            this.sessionId = str2;
            this.tvAppAttributionSource = attributionSource;
        }

        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
            synchronized (com.android.server.tv.TvInputManagerService.this.mLock) {
                this.session = null;
                com.android.server.tv.TvInputManagerService.this.clearSessionAndNotifyClientLocked(this);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public void bindService(com.android.server.tv.TvInputManagerService.ServiceState serviceState, int i) {
        if (serviceState.bound) {
            if (serviceState.isHardware) {
                updateHardwareServiceConnectionDelayed(i);
                return;
            }
            return;
        }
        serviceState.bound = this.mContext.bindServiceAsUser(new android.content.Intent("android.media.tv.TvInputService").setComponent(serviceState.component), serviceState.connection, 33554433, new android.os.UserHandle(i));
        if (!serviceState.bound) {
            android.util.Slog.e(TAG, "failed to bind " + serviceState.component + " for userId " + i);
            this.mContext.unbindService(serviceState.connection);
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void unbindService(com.android.server.tv.TvInputManagerService.ServiceState serviceState) {
        if (!serviceState.bound) {
            return;
        }
        this.mContext.unbindService(serviceState.connection);
        serviceState.bound = false;
        serviceState.service = null;
        serviceState.callback = null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public void updateHardwareTvInputServiceBindingLocked(int i) {
        android.content.pm.PackageManager packageManager = this.mContext.getPackageManager();
        java.util.Iterator it = packageManager.queryIntentServicesAsUser(new android.content.Intent("android.media.tv.TvInputService"), 132, i).iterator();
        while (it.hasNext()) {
            android.content.pm.ServiceInfo serviceInfo = ((android.content.pm.ResolveInfo) it.next()).serviceInfo;
            if ("android.permission.BIND_TV_INPUT".equals(serviceInfo.permission)) {
                android.content.ComponentName componentName = new android.content.ComponentName(serviceInfo.packageName, serviceInfo.name);
                if (hasHardwarePermission(packageManager, componentName)) {
                    updateServiceConnectionLocked(componentName, i);
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateHardwareServiceConnectionDelayed(int i) {
        this.mMessageHandler.removeMessages(4);
        com.android.internal.os.SomeArgs obtain = com.android.internal.os.SomeArgs.obtain();
        obtain.arg1 = java.lang.Integer.valueOf(i);
        this.mMessageHandler.sendMessageDelayed(this.mMessageHandler.obtainMessage(4, obtain), 10000L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public void addHardwareInputLocked(android.media.tv.TvInputInfo tvInputInfo, android.content.ComponentName componentName, int i) {
        getServiceStateLocked(componentName, i).hardwareInputMap.put(tvInputInfo.getId(), tvInputInfo);
        buildTvInputListLocked(i, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public void removeHardwareInputLocked(java.lang.String str, int i) {
        if (!this.mTvInputHardwareManager.getInputMap().containsKey(str)) {
            return;
        }
        if (getServiceStateLocked(this.mTvInputHardwareManager.getInputMap().get(str).getComponent(), i).hardwareInputMap.remove(str) != null) {
            buildTvInputListLocked(i, null);
            this.mTvInputHardwareManager.removeHardwareInput(str);
        }
    }

    private final class InputServiceConnection implements android.content.ServiceConnection {
        private final android.content.ComponentName mComponent;
        private final int mUserId;

        private InputServiceConnection(android.content.ComponentName componentName, int i) {
            this.mComponent = componentName;
            this.mUserId = i;
        }

        @Override // android.content.ServiceConnection
        public void onServiceConnected(android.content.ComponentName componentName, android.os.IBinder iBinder) {
            synchronized (com.android.server.tv.TvInputManagerService.this.mLock) {
                try {
                    com.android.server.tv.TvInputManagerService.UserState userStateLocked = com.android.server.tv.TvInputManagerService.this.getUserStateLocked(this.mUserId);
                    if (userStateLocked == null) {
                        com.android.server.tv.TvInputManagerService.this.mContext.unbindService(this);
                        return;
                    }
                    com.android.server.tv.TvInputManagerService.ServiceState serviceState = (com.android.server.tv.TvInputManagerService.ServiceState) userStateLocked.serviceStateMap.get(this.mComponent);
                    serviceState.service = android.media.tv.ITvInputService.Stub.asInterface(iBinder);
                    serviceState.neverConnected = false;
                    if (serviceState.isHardware && serviceState.callback == null) {
                        serviceState.callback = com.android.server.tv.TvInputManagerService.this.new ServiceCallback(this.mComponent, this.mUserId);
                        try {
                            serviceState.service.registerCallback(serviceState.callback);
                        } catch (android.os.RemoteException e) {
                            android.util.Slog.e(com.android.server.tv.TvInputManagerService.TAG, "error in registerCallback", e);
                        }
                    }
                    for (com.android.server.tv.TvInputManagerService.TvInputState tvInputState : userStateLocked.inputMap.values()) {
                        if (tvInputState.info.getComponent().equals(componentName) && tvInputState.state != 0) {
                            com.android.server.tv.TvInputManagerService.this.notifyInputStateChangedLocked(userStateLocked, tvInputState.info.getId(), tvInputState.state, null);
                        }
                    }
                    if (serviceState.isHardware) {
                        java.util.Iterator it = serviceState.hardwareDeviceRemovedBuffer.iterator();
                        while (it.hasNext()) {
                            try {
                                serviceState.service.notifyHardwareRemoved((android.media.tv.TvInputHardwareInfo) it.next());
                            } catch (android.os.RemoteException e2) {
                                android.util.Slog.e(com.android.server.tv.TvInputManagerService.TAG, "error in hardwareDeviceRemovedBuffer", e2);
                            }
                        }
                        serviceState.hardwareDeviceRemovedBuffer.clear();
                        java.util.Iterator it2 = serviceState.hdmiDeviceRemovedBuffer.iterator();
                        while (it2.hasNext()) {
                            try {
                                serviceState.service.notifyHdmiDeviceRemoved((android.hardware.hdmi.HdmiDeviceInfo) it2.next());
                            } catch (android.os.RemoteException e3) {
                                android.util.Slog.e(com.android.server.tv.TvInputManagerService.TAG, "error in hdmiDeviceRemovedBuffer", e3);
                            }
                        }
                        serviceState.hdmiDeviceRemovedBuffer.clear();
                        java.util.Iterator<android.media.tv.TvInputHardwareInfo> it3 = com.android.server.tv.TvInputManagerService.this.mTvInputHardwareManager.getHardwareList().iterator();
                        while (it3.hasNext()) {
                            try {
                                serviceState.service.notifyHardwareAdded(it3.next());
                            } catch (android.os.RemoteException e4) {
                                android.util.Slog.e(com.android.server.tv.TvInputManagerService.TAG, "error in notifyHardwareAdded", e4);
                            }
                        }
                        java.util.Iterator<android.hardware.hdmi.HdmiDeviceInfo> it4 = com.android.server.tv.TvInputManagerService.this.mTvInputHardwareManager.getHdmiDeviceList().iterator();
                        while (it4.hasNext()) {
                            try {
                                serviceState.service.notifyHdmiDeviceAdded(it4.next());
                            } catch (android.os.RemoteException e5) {
                                android.util.Slog.e(com.android.server.tv.TvInputManagerService.TAG, "error in notifyHdmiDeviceAdded", e5);
                            }
                        }
                        java.util.Iterator it5 = serviceState.hdmiDeviceUpdatedBuffer.iterator();
                        while (it5.hasNext()) {
                            try {
                                serviceState.service.notifyHdmiDeviceUpdated((android.hardware.hdmi.HdmiDeviceInfo) it5.next());
                            } catch (android.os.RemoteException e6) {
                                android.util.Slog.e(com.android.server.tv.TvInputManagerService.TAG, "error in hdmiDeviceUpdatedBuffer", e6);
                            }
                        }
                        serviceState.hdmiDeviceUpdatedBuffer.clear();
                    }
                    java.util.ArrayList arrayList = new java.util.ArrayList();
                    for (android.os.IBinder iBinder2 : serviceState.sessionTokens) {
                        if (!com.android.server.tv.TvInputManagerService.this.createSessionInternalLocked(serviceState.service, iBinder2, this.mUserId)) {
                            arrayList.add(iBinder2);
                        }
                    }
                    java.util.Iterator it6 = arrayList.iterator();
                    while (it6.hasNext()) {
                        com.android.server.tv.TvInputManagerService.this.removeSessionStateLocked((android.os.IBinder) it6.next(), this.mUserId);
                    }
                    if (serviceState.isHardware) {
                        com.android.server.tv.TvInputManagerService.this.updateHardwareServiceConnectionDelayed(this.mUserId);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(android.content.ComponentName componentName) {
            if (!this.mComponent.equals(componentName)) {
                throw new java.lang.IllegalArgumentException("Mismatched ComponentName: " + this.mComponent + " (expected), " + componentName + " (actual).");
            }
            synchronized (com.android.server.tv.TvInputManagerService.this.mLock) {
                try {
                    com.android.server.tv.TvInputManagerService.ServiceState serviceState = (com.android.server.tv.TvInputManagerService.ServiceState) com.android.server.tv.TvInputManagerService.this.getOrCreateUserStateLocked(this.mUserId).serviceStateMap.get(this.mComponent);
                    if (serviceState != null) {
                        serviceState.reconnecting = true;
                        serviceState.bound = false;
                        serviceState.service = null;
                        serviceState.callback = null;
                        com.android.server.tv.TvInputManagerService.this.abortPendingCreateSessionRequestsLocked(serviceState, null, this.mUserId);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
    }

    private final class ServiceCallback extends android.media.tv.ITvInputServiceCallback.Stub {
        private final android.content.ComponentName mComponent;
        private final int mUserId;

        ServiceCallback(android.content.ComponentName componentName, int i) {
            this.mComponent = componentName;
            this.mUserId = i;
        }

        private void ensureHardwarePermission() {
            if (com.android.server.tv.TvInputManagerService.this.mContext.checkCallingPermission("android.permission.TV_INPUT_HARDWARE") != 0) {
                throw new java.lang.SecurityException("The caller does not have hardware permission");
            }
        }

        private void ensureValidInput(android.media.tv.TvInputInfo tvInputInfo) {
            if (tvInputInfo.getId() == null || !this.mComponent.equals(tvInputInfo.getComponent())) {
                throw new java.lang.IllegalArgumentException("Invalid TvInputInfo");
            }
        }

        public void addHardwareInput(int i, android.media.tv.TvInputInfo tvInputInfo) {
            ensureHardwarePermission();
            ensureValidInput(tvInputInfo);
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                synchronized (com.android.server.tv.TvInputManagerService.this.mLock) {
                    if (com.android.server.tv.TvInputManagerService.this.getServiceStateLocked(this.mComponent, this.mUserId).hardwareInputMap.containsKey(tvInputInfo.getId())) {
                        return;
                    }
                    android.util.Slog.d("ServiceCallback", "addHardwareInput: device id " + i + ", " + tvInputInfo.toString());
                    com.android.server.tv.TvInputManagerService.this.mTvInputHardwareManager.addHardwareInput(i, tvInputInfo);
                    com.android.server.tv.TvInputManagerService.this.addHardwareInputLocked(tvInputInfo, this.mComponent, this.mUserId);
                }
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void addHdmiInput(int i, android.media.tv.TvInputInfo tvInputInfo) {
            ensureHardwarePermission();
            ensureValidInput(tvInputInfo);
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                synchronized (com.android.server.tv.TvInputManagerService.this.mLock) {
                    if (com.android.server.tv.TvInputManagerService.this.getServiceStateLocked(this.mComponent, this.mUserId).hardwareInputMap.containsKey(tvInputInfo.getId())) {
                        return;
                    }
                    com.android.server.tv.TvInputManagerService.this.mTvInputHardwareManager.addHdmiInput(i, tvInputInfo);
                    com.android.server.tv.TvInputManagerService.this.addHardwareInputLocked(tvInputInfo, this.mComponent, this.mUserId);
                    if (com.android.server.tv.TvInputManagerService.this.mOnScreenInputId != null && com.android.server.tv.TvInputManagerService.this.mOnScreenSessionState != null) {
                        if (android.text.TextUtils.equals(com.android.server.tv.TvInputManagerService.this.mOnScreenInputId, tvInputInfo.getParentId())) {
                            com.android.server.tv.TvInputManagerService.this.logExternalInputEvent(1, tvInputInfo.getId(), com.android.server.tv.TvInputManagerService.this.mOnScreenSessionState);
                            com.android.server.tv.TvInputManagerService.this.mOnScreenInputId = tvInputInfo.getId();
                        } else if (android.text.TextUtils.equals(com.android.server.tv.TvInputManagerService.this.mOnScreenInputId, tvInputInfo.getId())) {
                            com.android.server.tv.TvInputManagerService.this.logExternalInputEvent(4, com.android.server.tv.TvInputManagerService.this.mOnScreenInputId, com.android.server.tv.TvInputManagerService.this.mOnScreenSessionState);
                        }
                    }
                }
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void removeHardwareInput(java.lang.String str) {
            ensureHardwarePermission();
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                synchronized (com.android.server.tv.TvInputManagerService.this.mLock) {
                    android.util.Slog.d("ServiceCallback", "removeHardwareInput " + str + " by " + this.mComponent);
                    com.android.server.tv.TvInputManagerService.this.removeHardwareInputLocked(str, this.mUserId);
                }
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }

    private final class SessionCallback extends android.media.tv.ITvInputSessionCallback.Stub {
        private final android.view.InputChannel[] mChannels;
        private final com.android.server.tv.TvInputManagerService.SessionState mSessionState;

        SessionCallback(com.android.server.tv.TvInputManagerService.SessionState sessionState, android.view.InputChannel[] inputChannelArr) {
            this.mSessionState = sessionState;
            this.mChannels = inputChannelArr;
        }

        public void onSessionCreated(android.media.tv.ITvInputSession iTvInputSession, android.os.IBinder iBinder) {
            synchronized (com.android.server.tv.TvInputManagerService.this.mLock) {
                try {
                    this.mSessionState.session = iTvInputSession;
                    this.mSessionState.hardwareSessionToken = iBinder;
                    if (iTvInputSession != null && addSessionTokenToClientStateLocked(iTvInputSession)) {
                        com.android.server.tv.TvInputManagerService.this.sendSessionTokenToClientLocked(this.mSessionState.client, this.mSessionState.inputId, this.mSessionState.sessionToken, this.mChannels[0], this.mSessionState.seq);
                    } else {
                        com.android.server.tv.TvInputManagerService.this.removeSessionStateLocked(this.mSessionState.sessionToken, this.mSessionState.userId);
                        com.android.server.tv.TvInputManagerService.this.sendSessionTokenToClientLocked(this.mSessionState.client, this.mSessionState.inputId, null, null, this.mSessionState.seq);
                    }
                    this.mChannels[0].dispose();
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        @com.android.internal.annotations.GuardedBy({"mLock"})
        private boolean addSessionTokenToClientStateLocked(android.media.tv.ITvInputSession iTvInputSession) {
            try {
                iTvInputSession.asBinder().linkToDeath(this.mSessionState, 0);
                android.os.IBinder asBinder = this.mSessionState.client.asBinder();
                com.android.server.tv.TvInputManagerService.UserState orCreateUserStateLocked = com.android.server.tv.TvInputManagerService.this.getOrCreateUserStateLocked(this.mSessionState.userId);
                com.android.server.tv.TvInputManagerService.ClientState clientState = (com.android.server.tv.TvInputManagerService.ClientState) orCreateUserStateLocked.clientStateMap.get(asBinder);
                if (clientState == null) {
                    clientState = com.android.server.tv.TvInputManagerService.this.new ClientState(asBinder, this.mSessionState.userId);
                    try {
                        asBinder.linkToDeath(clientState, 0);
                        orCreateUserStateLocked.clientStateMap.put(asBinder, clientState);
                    } catch (android.os.RemoteException e) {
                        android.util.Slog.e(com.android.server.tv.TvInputManagerService.TAG, "client process has already died", e);
                        return false;
                    }
                }
                clientState.sessionTokens.add(this.mSessionState.sessionToken);
                return true;
            } catch (android.os.RemoteException e2) {
                android.util.Slog.e(com.android.server.tv.TvInputManagerService.TAG, "session process has already died", e2);
                return false;
            }
        }

        public void onChannelRetuned(android.net.Uri uri) {
            synchronized (com.android.server.tv.TvInputManagerService.this.mLock) {
                try {
                    if (this.mSessionState.session == null || this.mSessionState.client == null) {
                        return;
                    }
                    try {
                        this.mSessionState.client.onChannelRetuned(uri, this.mSessionState.seq);
                    } catch (android.os.RemoteException e) {
                        android.util.Slog.e(com.android.server.tv.TvInputManagerService.TAG, "error in onChannelRetuned", e);
                    }
                    if (this.mSessionState.isCurrent) {
                        if (!java.util.Objects.equals(this.mSessionState.currentChannel, uri)) {
                        }
                    }
                    com.android.server.tv.TvInputManagerService.UserState orCreateUserStateLocked = com.android.server.tv.TvInputManagerService.this.getOrCreateUserStateLocked(this.mSessionState.userId);
                    this.mSessionState.isCurrent = true;
                    this.mSessionState.currentChannel = uri;
                    com.android.server.tv.TvInputManagerService.this.notifyCurrentChannelInfosUpdatedLocked(orCreateUserStateLocked);
                    if (!this.mSessionState.isRecordingSession) {
                        java.lang.String sessionActualInputId = com.android.server.tv.TvInputManagerService.this.getSessionActualInputId(this.mSessionState);
                        if (!android.text.TextUtils.equals(com.android.server.tv.TvInputManagerService.this.mOnScreenInputId, sessionActualInputId)) {
                            com.android.server.tv.TvInputManagerService.this.logExternalInputEvent(1, sessionActualInputId, this.mSessionState);
                        }
                        com.android.server.tv.TvInputManagerService.this.mOnScreenInputId = sessionActualInputId;
                        com.android.server.tv.TvInputManagerService.this.mOnScreenSessionState = this.mSessionState;
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        public void onAudioPresentationsChanged(java.util.List<android.media.AudioPresentation> list) {
            synchronized (com.android.server.tv.TvInputManagerService.this.mLock) {
                if (this.mSessionState.session == null || this.mSessionState.client == null) {
                    return;
                }
                try {
                    this.mSessionState.client.onAudioPresentationsChanged(list, this.mSessionState.seq);
                } catch (android.os.RemoteException e) {
                    android.util.Slog.e(com.android.server.tv.TvInputManagerService.TAG, "error in onAudioPresentationsChanged", e);
                }
            }
        }

        public void onAudioPresentationSelected(int i, int i2) {
            synchronized (com.android.server.tv.TvInputManagerService.this.mLock) {
                if (this.mSessionState.session == null || this.mSessionState.client == null) {
                    return;
                }
                try {
                    this.mSessionState.client.onAudioPresentationSelected(i, i2, this.mSessionState.seq);
                } catch (android.os.RemoteException e) {
                    android.util.Slog.e(com.android.server.tv.TvInputManagerService.TAG, "error in onAudioPresentationSelected", e);
                }
            }
        }

        public void onTracksChanged(java.util.List<android.media.tv.TvTrackInfo> list) {
            synchronized (com.android.server.tv.TvInputManagerService.this.mLock) {
                if (this.mSessionState.session == null || this.mSessionState.client == null) {
                    return;
                }
                try {
                    this.mSessionState.client.onTracksChanged(list, this.mSessionState.seq);
                } catch (android.os.RemoteException e) {
                    android.util.Slog.e(com.android.server.tv.TvInputManagerService.TAG, "error in onTracksChanged", e);
                }
            }
        }

        public void onTrackSelected(int i, java.lang.String str) {
            synchronized (com.android.server.tv.TvInputManagerService.this.mLock) {
                if (this.mSessionState.session == null || this.mSessionState.client == null) {
                    return;
                }
                try {
                    this.mSessionState.client.onTrackSelected(i, str, this.mSessionState.seq);
                } catch (android.os.RemoteException e) {
                    android.util.Slog.e(com.android.server.tv.TvInputManagerService.TAG, "error in onTrackSelected", e);
                }
            }
        }

        public void onVideoAvailable() {
            synchronized (com.android.server.tv.TvInputManagerService.this.mLock) {
                if (this.mSessionState.session == null || this.mSessionState.client == null) {
                    return;
                }
                com.android.server.tv.TvInputManagerService.TvInputState tvInputState = com.android.server.tv.TvInputManagerService.getTvInputState(this.mSessionState, com.android.server.tv.TvInputManagerService.this.getUserStateLocked(com.android.server.tv.TvInputManagerService.this.mCurrentUserId));
                try {
                    this.mSessionState.client.onVideoAvailable(this.mSessionState.seq);
                    com.android.server.tv.TvInputManagerService.this.logTuneStateChanged(6, this.mSessionState, tvInputState);
                } catch (android.os.RemoteException e) {
                    android.util.Slog.e(com.android.server.tv.TvInputManagerService.TAG, "error in onVideoAvailable", e);
                }
            }
        }

        public void onVideoUnavailable(int i) {
            synchronized (com.android.server.tv.TvInputManagerService.this.mLock) {
                if (this.mSessionState.session == null || this.mSessionState.client == null) {
                    return;
                }
                com.android.server.tv.TvInputManagerService.TvInputState tvInputState = com.android.server.tv.TvInputManagerService.getTvInputState(this.mSessionState, com.android.server.tv.TvInputManagerService.this.getUserStateLocked(com.android.server.tv.TvInputManagerService.this.mCurrentUserId));
                try {
                    this.mSessionState.client.onVideoUnavailable(i, this.mSessionState.seq);
                    com.android.server.tv.TvInputManagerService.this.logTuneStateChanged(com.android.server.tv.TvInputManagerService.getVideoUnavailableReasonForStatsd(i), this.mSessionState, tvInputState);
                } catch (android.os.RemoteException e) {
                    android.util.Slog.e(com.android.server.tv.TvInputManagerService.TAG, "error in onVideoUnavailable", e);
                }
            }
        }

        public void onVideoFreezeUpdated(boolean z) {
            synchronized (com.android.server.tv.TvInputManagerService.this.mLock) {
                if (this.mSessionState.session == null || this.mSessionState.client == null) {
                    return;
                }
                try {
                    this.mSessionState.client.onVideoFreezeUpdated(z, this.mSessionState.seq);
                } catch (android.os.RemoteException e) {
                    android.util.Slog.e(com.android.server.tv.TvInputManagerService.TAG, "error in onVideoFreezeUpdated", e);
                }
            }
        }

        public void onContentAllowed() {
            synchronized (com.android.server.tv.TvInputManagerService.this.mLock) {
                if (this.mSessionState.session == null || this.mSessionState.client == null) {
                    return;
                }
                try {
                    this.mSessionState.client.onContentAllowed(this.mSessionState.seq);
                } catch (android.os.RemoteException e) {
                    android.util.Slog.e(com.android.server.tv.TvInputManagerService.TAG, "error in onContentAllowed", e);
                }
            }
        }

        public void onContentBlocked(java.lang.String str) {
            synchronized (com.android.server.tv.TvInputManagerService.this.mLock) {
                if (this.mSessionState.session == null || this.mSessionState.client == null) {
                    return;
                }
                try {
                    this.mSessionState.client.onContentBlocked(str, this.mSessionState.seq);
                } catch (android.os.RemoteException e) {
                    android.util.Slog.e(com.android.server.tv.TvInputManagerService.TAG, "error in onContentBlocked", e);
                }
            }
        }

        public void onLayoutSurface(int i, int i2, int i3, int i4) {
            synchronized (com.android.server.tv.TvInputManagerService.this.mLock) {
                if (this.mSessionState.session == null || this.mSessionState.client == null) {
                    return;
                }
                try {
                    this.mSessionState.client.onLayoutSurface(i, i2, i3, i4, this.mSessionState.seq);
                } catch (android.os.RemoteException e) {
                    android.util.Slog.e(com.android.server.tv.TvInputManagerService.TAG, "error in onLayoutSurface", e);
                }
            }
        }

        public void onSessionEvent(java.lang.String str, android.os.Bundle bundle) {
            synchronized (com.android.server.tv.TvInputManagerService.this.mLock) {
                if (this.mSessionState.session == null || this.mSessionState.client == null) {
                    return;
                }
                try {
                    this.mSessionState.client.onSessionEvent(str, bundle, this.mSessionState.seq);
                } catch (android.os.RemoteException e) {
                    android.util.Slog.e(com.android.server.tv.TvInputManagerService.TAG, "error in onSessionEvent", e);
                }
            }
        }

        public void onTimeShiftStatusChanged(int i) {
            synchronized (com.android.server.tv.TvInputManagerService.this.mLock) {
                if (this.mSessionState.session == null || this.mSessionState.client == null) {
                    return;
                }
                try {
                    this.mSessionState.client.onTimeShiftStatusChanged(i, this.mSessionState.seq);
                } catch (android.os.RemoteException e) {
                    android.util.Slog.e(com.android.server.tv.TvInputManagerService.TAG, "error in onTimeShiftStatusChanged", e);
                }
            }
        }

        public void onTimeShiftStartPositionChanged(long j) {
            synchronized (com.android.server.tv.TvInputManagerService.this.mLock) {
                if (this.mSessionState.session == null || this.mSessionState.client == null) {
                    return;
                }
                try {
                    this.mSessionState.client.onTimeShiftStartPositionChanged(j, this.mSessionState.seq);
                } catch (android.os.RemoteException e) {
                    android.util.Slog.e(com.android.server.tv.TvInputManagerService.TAG, "error in onTimeShiftStartPositionChanged", e);
                }
            }
        }

        public void onTimeShiftCurrentPositionChanged(long j) {
            synchronized (com.android.server.tv.TvInputManagerService.this.mLock) {
                if (this.mSessionState.session == null || this.mSessionState.client == null) {
                    return;
                }
                try {
                    this.mSessionState.client.onTimeShiftCurrentPositionChanged(j, this.mSessionState.seq);
                } catch (android.os.RemoteException e) {
                    android.util.Slog.e(com.android.server.tv.TvInputManagerService.TAG, "error in onTimeShiftCurrentPositionChanged", e);
                }
            }
        }

        public void onAitInfoUpdated(android.media.tv.AitInfo aitInfo) {
            synchronized (com.android.server.tv.TvInputManagerService.this.mLock) {
                if (this.mSessionState.session == null || this.mSessionState.client == null) {
                    return;
                }
                try {
                    this.mSessionState.client.onAitInfoUpdated(aitInfo, this.mSessionState.seq);
                } catch (android.os.RemoteException e) {
                    android.util.Slog.e(com.android.server.tv.TvInputManagerService.TAG, "error in onAitInfoUpdated", e);
                }
            }
        }

        public void onSignalStrength(int i) {
            synchronized (com.android.server.tv.TvInputManagerService.this.mLock) {
                if (this.mSessionState.session == null || this.mSessionState.client == null) {
                    return;
                }
                try {
                    this.mSessionState.client.onSignalStrength(i, this.mSessionState.seq);
                } catch (android.os.RemoteException e) {
                    android.util.Slog.e(com.android.server.tv.TvInputManagerService.TAG, "error in onSignalStrength", e);
                }
            }
        }

        public void onCueingMessageAvailability(boolean z) {
            synchronized (com.android.server.tv.TvInputManagerService.this.mLock) {
                if (this.mSessionState.session == null || this.mSessionState.client == null) {
                    return;
                }
                try {
                    this.mSessionState.client.onCueingMessageAvailability(z, this.mSessionState.seq);
                } catch (android.os.RemoteException e) {
                    android.util.Slog.e(com.android.server.tv.TvInputManagerService.TAG, "error in onCueingMessageAvailability", e);
                }
            }
        }

        public void onTimeShiftMode(int i) {
            synchronized (com.android.server.tv.TvInputManagerService.this.mLock) {
                if (this.mSessionState.session == null || this.mSessionState.client == null) {
                    return;
                }
                try {
                    this.mSessionState.client.onTimeShiftMode(i, this.mSessionState.seq);
                } catch (android.os.RemoteException e) {
                    android.util.Slog.e(com.android.server.tv.TvInputManagerService.TAG, "error in onTimeShiftMode", e);
                }
            }
        }

        public void onAvailableSpeeds(float[] fArr) {
            synchronized (com.android.server.tv.TvInputManagerService.this.mLock) {
                if (this.mSessionState.session == null || this.mSessionState.client == null) {
                    return;
                }
                try {
                    this.mSessionState.client.onAvailableSpeeds(fArr, this.mSessionState.seq);
                } catch (android.os.RemoteException e) {
                    android.util.Slog.e(com.android.server.tv.TvInputManagerService.TAG, "error in onAvailableSpeeds", e);
                }
            }
        }

        public void onTuned(android.net.Uri uri) {
            synchronized (com.android.server.tv.TvInputManagerService.this.mLock) {
                if (this.mSessionState.session == null || this.mSessionState.client == null) {
                    return;
                }
                try {
                    this.mSessionState.client.onTuned(uri, this.mSessionState.seq);
                } catch (android.os.RemoteException e) {
                    android.util.Slog.e(com.android.server.tv.TvInputManagerService.TAG, "error in onTuned", e);
                }
            }
        }

        public void onTvMessage(int i, android.os.Bundle bundle) {
            synchronized (com.android.server.tv.TvInputManagerService.this.mLock) {
                if (this.mSessionState.session == null || this.mSessionState.client == null) {
                    return;
                }
                try {
                    this.mSessionState.client.onTvMessage(i, bundle, this.mSessionState.seq);
                } catch (android.os.RemoteException e) {
                    android.util.Slog.e(com.android.server.tv.TvInputManagerService.TAG, "error in onTvMessage", e);
                }
            }
        }

        public void onRecordingStopped(android.net.Uri uri) {
            synchronized (com.android.server.tv.TvInputManagerService.this.mLock) {
                if (this.mSessionState.session == null || this.mSessionState.client == null) {
                    return;
                }
                try {
                    this.mSessionState.client.onRecordingStopped(uri, this.mSessionState.seq);
                } catch (android.os.RemoteException e) {
                    android.util.Slog.e(com.android.server.tv.TvInputManagerService.TAG, "error in onRecordingStopped", e);
                }
            }
        }

        public void onError(int i) {
            synchronized (com.android.server.tv.TvInputManagerService.this.mLock) {
                if (this.mSessionState.session == null || this.mSessionState.client == null) {
                    return;
                }
                try {
                    this.mSessionState.client.onError(i, this.mSessionState.seq);
                } catch (android.os.RemoteException e) {
                    android.util.Slog.e(com.android.server.tv.TvInputManagerService.TAG, "error in onError", e);
                }
            }
        }

        public void onBroadcastInfoResponse(android.media.tv.BroadcastInfoResponse broadcastInfoResponse) {
            synchronized (com.android.server.tv.TvInputManagerService.this.mLock) {
                if (this.mSessionState.session == null || this.mSessionState.client == null) {
                    return;
                }
                try {
                    this.mSessionState.client.onBroadcastInfoResponse(broadcastInfoResponse, this.mSessionState.seq);
                } catch (android.os.RemoteException e) {
                    android.util.Slog.e(com.android.server.tv.TvInputManagerService.TAG, "error in onBroadcastInfoResponse", e);
                }
            }
        }

        public void onAdResponse(android.media.tv.AdResponse adResponse) {
            synchronized (com.android.server.tv.TvInputManagerService.this.mLock) {
                if (this.mSessionState.session == null || this.mSessionState.client == null) {
                    return;
                }
                try {
                    this.mSessionState.client.onAdResponse(adResponse, this.mSessionState.seq);
                } catch (android.os.RemoteException e) {
                    android.util.Slog.e(com.android.server.tv.TvInputManagerService.TAG, "error in onAdResponse", e);
                }
            }
        }

        public void onAdBufferConsumed(android.media.tv.AdBuffer adBuffer) {
            android.os.SharedMemory sharedMemory;
            synchronized (com.android.server.tv.TvInputManagerService.this.mLock) {
                if (this.mSessionState.session != null) {
                    try {
                        if (this.mSessionState.client != null) {
                            try {
                                this.mSessionState.client.onAdBufferConsumed(adBuffer, this.mSessionState.seq);
                            } catch (android.os.RemoteException e) {
                                android.util.Slog.e(com.android.server.tv.TvInputManagerService.TAG, "error in onAdBufferConsumed", e);
                                if (adBuffer != null) {
                                    sharedMemory = adBuffer.getSharedMemory();
                                }
                            }
                            if (adBuffer != null) {
                                sharedMemory = adBuffer.getSharedMemory();
                                sharedMemory.close();
                            }
                        }
                    } catch (java.lang.Throwable th) {
                        if (adBuffer != null) {
                            adBuffer.getSharedMemory().close();
                        }
                        throw th;
                    }
                }
            }
        }

        public void onTvInputSessionData(java.lang.String str, android.os.Bundle bundle) {
            synchronized (com.android.server.tv.TvInputManagerService.this.mLock) {
                if (this.mSessionState.session == null || this.mSessionState.client == null) {
                    return;
                }
                try {
                    this.mSessionState.client.onTvInputSessionData(str, bundle, this.mSessionState.seq);
                } catch (android.os.RemoteException e) {
                    android.util.Slog.e(com.android.server.tv.TvInputManagerService.TAG, "error in onTvInputSessionData", e);
                }
            }
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    static int getVideoUnavailableReasonForStatsd(int i) {
        int i2 = i + 100;
        if (i2 < 100 || i2 > 118) {
            return 100;
        }
        return i2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public com.android.server.tv.TvInputManagerService.UserState getUserStateLocked(int i) {
        return this.mUserStates.get(i);
    }

    private final class MessageHandler extends android.os.Handler {
        static final int MSG_LOG_WATCH_END = 2;
        static final int MSG_LOG_WATCH_START = 1;
        static final int MSG_SWITCH_CONTENT_RESOLVER = 3;
        static final int MSG_UPDATE_HARDWARE_TIS_BINDING = 4;
        private android.content.ContentResolver mContentResolver;

        MessageHandler(android.content.ContentResolver contentResolver, android.os.Looper looper) {
            super(looper);
            this.mContentResolver = contentResolver;
        }

        @Override // android.os.Handler
        public void handleMessage(android.os.Message message) {
            switch (message.what) {
                case 1:
                    com.android.internal.os.SomeArgs someArgs = (com.android.internal.os.SomeArgs) message.obj;
                    java.lang.String str = (java.lang.String) someArgs.arg1;
                    long longValue = ((java.lang.Long) someArgs.arg2).longValue();
                    long longValue2 = ((java.lang.Long) someArgs.arg3).longValue();
                    android.os.Bundle bundle = (android.os.Bundle) someArgs.arg4;
                    android.os.IBinder iBinder = (android.os.IBinder) someArgs.arg5;
                    android.content.ContentValues contentValues = new android.content.ContentValues();
                    contentValues.put("package_name", str);
                    contentValues.put("watch_start_time_utc_millis", java.lang.Long.valueOf(longValue));
                    contentValues.put("channel_id", java.lang.Long.valueOf(longValue2));
                    if (bundle != null) {
                        contentValues.put("tune_params", encodeTuneParams(bundle));
                    }
                    contentValues.put("session_token", iBinder.toString());
                    try {
                        this.mContentResolver.insert(android.media.tv.TvContract.WatchedPrograms.CONTENT_URI, contentValues);
                    } catch (java.lang.IllegalArgumentException e) {
                        android.util.Slog.w(com.android.server.tv.TvInputManagerService.TAG, "error in insert db for MSG_LOG_WATCH_START", e);
                    }
                    someArgs.recycle();
                    return;
                case 2:
                    com.android.internal.os.SomeArgs someArgs2 = (com.android.internal.os.SomeArgs) message.obj;
                    android.os.IBinder iBinder2 = (android.os.IBinder) someArgs2.arg1;
                    long longValue3 = ((java.lang.Long) someArgs2.arg2).longValue();
                    android.content.ContentValues contentValues2 = new android.content.ContentValues();
                    contentValues2.put("watch_end_time_utc_millis", java.lang.Long.valueOf(longValue3));
                    contentValues2.put("session_token", iBinder2.toString());
                    try {
                        this.mContentResolver.insert(android.media.tv.TvContract.WatchedPrograms.CONTENT_URI, contentValues2);
                    } catch (java.lang.IllegalArgumentException e2) {
                        android.util.Slog.w(com.android.server.tv.TvInputManagerService.TAG, "error in insert db for MSG_LOG_WATCH_END", e2);
                    }
                    someArgs2.recycle();
                    return;
                case 3:
                    this.mContentResolver = (android.content.ContentResolver) message.obj;
                    return;
                case 4:
                    com.android.internal.os.SomeArgs someArgs3 = (com.android.internal.os.SomeArgs) message.obj;
                    int intValue = ((java.lang.Integer) someArgs3.arg1).intValue();
                    synchronized (com.android.server.tv.TvInputManagerService.this.mLock) {
                        com.android.server.tv.TvInputManagerService.this.updateHardwareTvInputServiceBindingLocked(intValue);
                    }
                    someArgs3.recycle();
                    return;
                default:
                    android.util.Slog.w(com.android.server.tv.TvInputManagerService.TAG, "unhandled message code: " + message.what);
                    return;
            }
        }

        private java.lang.String encodeTuneParams(android.os.Bundle bundle) {
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            java.util.Iterator<java.lang.String> it = bundle.keySet().iterator();
            while (it.hasNext()) {
                java.lang.String next = it.next();
                java.lang.Object obj = bundle.get(next);
                if (obj != null) {
                    sb.append(replaceEscapeCharacters(next));
                    sb.append("=");
                    sb.append(replaceEscapeCharacters(obj.toString()));
                    if (it.hasNext()) {
                        sb.append(", ");
                    }
                }
            }
            return sb.toString();
        }

        private java.lang.String replaceEscapeCharacters(java.lang.String str) {
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            for (char c : str.toCharArray()) {
                if ("%=,".indexOf(c) >= 0) {
                    sb.append('%');
                }
                sb.append(c);
            }
            return sb.toString();
        }
    }

    private final class HardwareListener implements com.android.server.tv.TvInputHardwareManager.Listener {
        private HardwareListener() {
        }

        @Override // com.android.server.tv.TvInputHardwareManager.Listener
        public void onStateChanged(java.lang.String str, int i) {
            synchronized (com.android.server.tv.TvInputManagerService.this.mLock) {
                com.android.server.tv.TvInputManagerService.this.setStateLocked(str, i, com.android.server.tv.TvInputManagerService.this.mCurrentUserId);
            }
        }

        @Override // com.android.server.tv.TvInputHardwareManager.Listener
        public void onHardwareDeviceAdded(android.media.tv.TvInputHardwareInfo tvInputHardwareInfo) {
            synchronized (com.android.server.tv.TvInputManagerService.this.mLock) {
                for (com.android.server.tv.TvInputManagerService.ServiceState serviceState : com.android.server.tv.TvInputManagerService.this.getOrCreateUserStateLocked(com.android.server.tv.TvInputManagerService.this.mCurrentUserId).serviceStateMap.values()) {
                    if (serviceState.isHardware) {
                        try {
                            com.android.server.tv.TvInputManagerService.this.bindService(serviceState, com.android.server.tv.TvInputManagerService.this.mCurrentUserId);
                            if (serviceState.service != null) {
                                serviceState.service.notifyHardwareAdded(tvInputHardwareInfo);
                            }
                        } catch (android.os.RemoteException e) {
                            android.util.Slog.e(com.android.server.tv.TvInputManagerService.TAG, "error in notifyHardwareAdded", e);
                        }
                    }
                }
                com.android.server.tv.TvInputManagerService.this.updateHardwareServiceConnectionDelayed(com.android.server.tv.TvInputManagerService.this.mCurrentUserId);
            }
        }

        @Override // com.android.server.tv.TvInputHardwareManager.Listener
        public void onHardwareDeviceRemoved(android.media.tv.TvInputHardwareInfo tvInputHardwareInfo) {
            synchronized (com.android.server.tv.TvInputManagerService.this.mLock) {
                try {
                    com.android.server.tv.TvInputManagerService.this.removeHardwareInputLocked(com.android.server.tv.TvInputManagerService.this.mTvInputHardwareManager.getHardwareInputIdMap().get(tvInputHardwareInfo.getDeviceId()), com.android.server.tv.TvInputManagerService.this.mCurrentUserId);
                    for (com.android.server.tv.TvInputManagerService.ServiceState serviceState : com.android.server.tv.TvInputManagerService.this.getOrCreateUserStateLocked(com.android.server.tv.TvInputManagerService.this.mCurrentUserId).serviceStateMap.values()) {
                        if (serviceState.isHardware) {
                            try {
                                com.android.server.tv.TvInputManagerService.this.bindService(serviceState, com.android.server.tv.TvInputManagerService.this.mCurrentUserId);
                                if (serviceState.service != null) {
                                    serviceState.service.notifyHardwareRemoved(tvInputHardwareInfo);
                                } else {
                                    serviceState.hardwareDeviceRemovedBuffer.add(tvInputHardwareInfo);
                                }
                            } catch (android.os.RemoteException e) {
                                android.util.Slog.e(com.android.server.tv.TvInputManagerService.TAG, "error in notifyHardwareRemoved", e);
                            }
                        }
                    }
                    com.android.server.tv.TvInputManagerService.this.updateHardwareServiceConnectionDelayed(com.android.server.tv.TvInputManagerService.this.mCurrentUserId);
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        @Override // com.android.server.tv.TvInputHardwareManager.Listener
        public void onHdmiDeviceAdded(android.hardware.hdmi.HdmiDeviceInfo hdmiDeviceInfo) {
            synchronized (com.android.server.tv.TvInputManagerService.this.mLock) {
                for (com.android.server.tv.TvInputManagerService.ServiceState serviceState : com.android.server.tv.TvInputManagerService.this.getOrCreateUserStateLocked(com.android.server.tv.TvInputManagerService.this.mCurrentUserId).serviceStateMap.values()) {
                    if (serviceState.isHardware) {
                        try {
                            com.android.server.tv.TvInputManagerService.this.bindService(serviceState, com.android.server.tv.TvInputManagerService.this.mCurrentUserId);
                            if (serviceState.service != null) {
                                serviceState.service.notifyHdmiDeviceAdded(hdmiDeviceInfo);
                            }
                        } catch (android.os.RemoteException e) {
                            android.util.Slog.e(com.android.server.tv.TvInputManagerService.TAG, "error in notifyHdmiDeviceAdded", e);
                        }
                    }
                }
                com.android.server.tv.TvInputManagerService.this.updateHardwareServiceConnectionDelayed(com.android.server.tv.TvInputManagerService.this.mCurrentUserId);
            }
        }

        @Override // com.android.server.tv.TvInputHardwareManager.Listener
        public void onHdmiDeviceRemoved(android.hardware.hdmi.HdmiDeviceInfo hdmiDeviceInfo) {
            synchronized (com.android.server.tv.TvInputManagerService.this.mLock) {
                try {
                    com.android.server.tv.TvInputManagerService.this.removeHardwareInputLocked(com.android.server.tv.TvInputManagerService.this.mTvInputHardwareManager.getHdmiInputIdMap().get(hdmiDeviceInfo.getId()), com.android.server.tv.TvInputManagerService.this.mCurrentUserId);
                    for (com.android.server.tv.TvInputManagerService.ServiceState serviceState : com.android.server.tv.TvInputManagerService.this.getOrCreateUserStateLocked(com.android.server.tv.TvInputManagerService.this.mCurrentUserId).serviceStateMap.values()) {
                        if (serviceState.isHardware) {
                            try {
                                com.android.server.tv.TvInputManagerService.this.bindService(serviceState, com.android.server.tv.TvInputManagerService.this.mCurrentUserId);
                                if (serviceState.service != null) {
                                    serviceState.service.notifyHdmiDeviceRemoved(hdmiDeviceInfo);
                                } else {
                                    serviceState.hdmiDeviceRemovedBuffer.add(hdmiDeviceInfo);
                                }
                            } catch (android.os.RemoteException e) {
                                android.util.Slog.e(com.android.server.tv.TvInputManagerService.TAG, "error in notifyHdmiDeviceRemoved", e);
                            }
                        }
                    }
                    com.android.server.tv.TvInputManagerService.this.updateHardwareServiceConnectionDelayed(com.android.server.tv.TvInputManagerService.this.mCurrentUserId);
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        @Override // com.android.server.tv.TvInputHardwareManager.Listener
        public void onHdmiDeviceUpdated(java.lang.String str, android.hardware.hdmi.HdmiDeviceInfo hdmiDeviceInfo) {
            java.lang.Integer num;
            synchronized (com.android.server.tv.TvInputManagerService.this.mLock) {
                try {
                    switch (hdmiDeviceInfo.getDevicePowerStatus()) {
                        case 0:
                            num = 0;
                            break;
                        case 1:
                        case 2:
                        case 3:
                            num = 1;
                            break;
                        default:
                            num = null;
                            break;
                    }
                    if (num != null) {
                        com.android.server.tv.TvInputManagerService.this.setStateLocked(str, num.intValue(), com.android.server.tv.TvInputManagerService.this.mCurrentUserId);
                    }
                    for (com.android.server.tv.TvInputManagerService.ServiceState serviceState : com.android.server.tv.TvInputManagerService.this.getOrCreateUserStateLocked(com.android.server.tv.TvInputManagerService.this.mCurrentUserId).serviceStateMap.values()) {
                        if (serviceState.isHardware) {
                            try {
                                com.android.server.tv.TvInputManagerService.this.bindService(serviceState, com.android.server.tv.TvInputManagerService.this.mCurrentUserId);
                                if (serviceState.service != null) {
                                    serviceState.service.notifyHdmiDeviceUpdated(hdmiDeviceInfo);
                                } else {
                                    serviceState.hdmiDeviceUpdatedBuffer.add(hdmiDeviceInfo);
                                }
                            } catch (android.os.RemoteException e) {
                                android.util.Slog.e(com.android.server.tv.TvInputManagerService.TAG, "error in notifyHdmiDeviceUpdated", e);
                            }
                        }
                    }
                    com.android.server.tv.TvInputManagerService.this.updateHardwareServiceConnectionDelayed(com.android.server.tv.TvInputManagerService.this.mCurrentUserId);
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        @Override // com.android.server.tv.TvInputHardwareManager.Listener
        public void onTvMessage(java.lang.String str, int i, android.os.Bundle bundle) {
            synchronized (com.android.server.tv.TvInputManagerService.this.mLock) {
                try {
                    com.android.server.tv.TvInputManagerService.UserState orCreateUserStateLocked = com.android.server.tv.TvInputManagerService.this.getOrCreateUserStateLocked(com.android.server.tv.TvInputManagerService.this.mCurrentUserId);
                    com.android.server.tv.TvInputManagerService.TvInputState tvInputState = (com.android.server.tv.TvInputManagerService.TvInputState) orCreateUserStateLocked.inputMap.get(str);
                    if (tvInputState == null) {
                        android.util.Slog.e(com.android.server.tv.TvInputManagerService.TAG, "failed to send TV message - unknown input id " + str);
                        return;
                    }
                    java.util.Iterator it = ((com.android.server.tv.TvInputManagerService.ServiceState) orCreateUserStateLocked.serviceStateMap.get(tvInputState.info.getComponent())).sessionTokens.iterator();
                    while (it.hasNext()) {
                        try {
                            com.android.server.tv.TvInputManagerService.SessionState sessionStateLocked = com.android.server.tv.TvInputManagerService.this.getSessionStateLocked((android.os.IBinder) it.next(), android.os.Binder.getCallingUid(), com.android.server.tv.TvInputManagerService.this.mCurrentUserId);
                            if (!sessionStateLocked.isRecordingSession && sessionStateLocked.hardwareSessionToken != null) {
                                sessionStateLocked.session.notifyTvMessage(i, bundle);
                            }
                        } catch (android.os.RemoteException e) {
                            android.util.Slog.e(com.android.server.tv.TvInputManagerService.TAG, "error in onTvMessage", e);
                        }
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
    }

    private static class SessionNotFoundException extends java.lang.IllegalArgumentException {
        public SessionNotFoundException(java.lang.String str) {
            super(str);
        }
    }

    private static class ClientPidNotFoundException extends java.lang.IllegalArgumentException {
        public ClientPidNotFoundException(java.lang.String str) {
            super(str);
        }
    }
}
