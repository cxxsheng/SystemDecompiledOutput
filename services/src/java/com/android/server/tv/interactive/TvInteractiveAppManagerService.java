package com.android.server.tv.interactive;

/* loaded from: classes2.dex */
public class TvInteractiveAppManagerService extends com.android.server.SystemService {
    private static final boolean DEBUG = false;
    private static final java.lang.String METADATA_CLASS_NAME = "android.media.tv.interactive.AppLinkInfo.ClassName";
    private static final java.lang.String METADATA_URI = "android.media.tv.interactive.AppLinkInfo.Uri";
    private static final java.lang.String TAG = "TvInteractiveAppManagerService";
    private final android.content.Context mContext;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private int mCurrentUserId;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean mGetAdServiceListCalled;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean mGetAppLinkInfoListCalled;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean mGetServiceListCalled;
    private final java.lang.Object mLock;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final java.util.Set<java.lang.Integer> mRunningProfiles;
    private final android.os.UserManager mUserManager;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final android.util.SparseArray<com.android.server.tv.interactive.TvInteractiveAppManagerService.UserState> mUserStates;

    private static final class TvAdServiceState {
        private int mAdNumber;
        private java.lang.String mAdServiceId;
        private android.content.ComponentName mComponentName;
        private android.media.tv.ad.TvAdServiceInfo mInfo;
        private int mUid;

        private TvAdServiceState() {
        }
    }

    private static final class TvInteractiveAppState {
        private android.content.ComponentName mComponentName;
        private int mIAppNumber;
        private java.lang.String mIAppServiceId;
        private android.media.tv.interactive.TvInteractiveAppServiceInfo mInfo;
        private int mUid;

        private TvInteractiveAppState() {
        }
    }

    public TvInteractiveAppManagerService(android.content.Context context) {
        super(context);
        this.mLock = new java.lang.Object();
        this.mCurrentUserId = 0;
        this.mRunningProfiles = new java.util.HashSet();
        this.mUserStates = new android.util.SparseArray<>();
        this.mGetServiceListCalled = false;
        this.mGetAdServiceListCalled = false;
        this.mGetAppLinkInfoListCalled = false;
        this.mContext = context;
        this.mUserManager = (android.os.UserManager) getContext().getSystemService("user");
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public void buildAppLinkInfoLocked(int i) {
        com.android.server.tv.interactive.TvInteractiveAppManagerService.UserState orCreateUserStateLocked = getOrCreateUserStateLocked(i);
        java.util.List installedApplicationsAsUser = this.mContext.getPackageManager().getInstalledApplicationsAsUser(android.content.pm.PackageManager.ApplicationInfoFlags.of(128L), i);
        java.util.ArrayList arrayList = new java.util.ArrayList();
        java.util.Iterator it = installedApplicationsAsUser.iterator();
        while (it.hasNext()) {
            android.media.tv.interactive.AppLinkInfo buildAppLinkInfoLocked = buildAppLinkInfoLocked((android.content.pm.ApplicationInfo) it.next());
            if (buildAppLinkInfoLocked != null) {
                arrayList.add(buildAppLinkInfoLocked);
            }
        }
        java.util.Collections.sort(arrayList, java.util.Comparator.comparing(new java.util.function.Function() { // from class: com.android.server.tv.interactive.TvInteractiveAppManagerService$$ExternalSyntheticLambda2
            @Override // java.util.function.Function
            public final java.lang.Object apply(java.lang.Object obj) {
                return ((android.media.tv.interactive.AppLinkInfo) obj).getComponentName();
            }
        }));
        orCreateUserStateLocked.mAppLinkInfoList.clear();
        orCreateUserStateLocked.mAppLinkInfoList.addAll(arrayList);
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private android.media.tv.interactive.AppLinkInfo buildAppLinkInfoLocked(android.content.pm.ApplicationInfo applicationInfo) {
        if (applicationInfo.metaData == null || applicationInfo.packageName == null) {
            return null;
        }
        java.lang.String string = applicationInfo.metaData.getString(METADATA_CLASS_NAME, null);
        java.lang.String string2 = applicationInfo.metaData.getString(METADATA_URI, null);
        if (string == null || string2 == null) {
            return null;
        }
        return new android.media.tv.interactive.AppLinkInfo(applicationInfo.packageName, string, string2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public void buildTvInteractiveAppServiceListLocked(int i, java.lang.String[] strArr) {
        com.android.server.tv.interactive.TvInteractiveAppManagerService.UserState orCreateUserStateLocked = getOrCreateUserStateLocked(i);
        orCreateUserStateLocked.mPackageSet.clear();
        java.util.List queryIntentServicesAsUser = this.mContext.getPackageManager().queryIntentServicesAsUser(new android.content.Intent("android.media.tv.interactive.TvInteractiveAppService"), 132, i);
        java.util.ArrayList<android.media.tv.interactive.TvInteractiveAppServiceInfo> arrayList = new java.util.ArrayList();
        java.util.Iterator it = queryIntentServicesAsUser.iterator();
        while (it.hasNext()) {
            android.content.pm.ServiceInfo serviceInfo = ((android.content.pm.ResolveInfo) it.next()).serviceInfo;
            if ("android.permission.BIND_TV_INTERACTIVE_APP".equals(serviceInfo.permission)) {
                try {
                    arrayList.add(new android.media.tv.interactive.TvInteractiveAppServiceInfo(this.mContext, new android.content.ComponentName(serviceInfo.packageName, serviceInfo.name)));
                    orCreateUserStateLocked.mPackageSet.add(serviceInfo.packageName);
                } catch (java.lang.Exception e) {
                    com.android.server.utils.Slogf.e(TAG, "failed to load TV Interactive App service " + serviceInfo.name, e);
                }
            } else {
                android.util.Slog.w(TAG, "Skipping TV interactiva app service " + serviceInfo.name + ": it does not require the permission android.permission.BIND_TV_INTERACTIVE_APP");
            }
        }
        java.util.Collections.sort(arrayList, java.util.Comparator.comparing(new java.util.function.Function() { // from class: com.android.server.tv.interactive.TvInteractiveAppManagerService$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final java.lang.Object apply(java.lang.Object obj) {
                return ((android.media.tv.interactive.TvInteractiveAppServiceInfo) obj).getId();
            }
        }));
        java.util.HashMap hashMap = new java.util.HashMap();
        android.util.ArrayMap arrayMap = new android.util.ArrayMap(hashMap.size());
        for (android.media.tv.interactive.TvInteractiveAppServiceInfo tvInteractiveAppServiceInfo : arrayList) {
            java.lang.String id = tvInteractiveAppServiceInfo.getId();
            java.lang.Integer num = (java.lang.Integer) arrayMap.get(id);
            java.lang.Integer valueOf = java.lang.Integer.valueOf(num != null ? 1 + num.intValue() : 1);
            arrayMap.put(id, valueOf);
            com.android.server.tv.interactive.TvInteractiveAppManagerService.TvInteractiveAppState tvInteractiveAppState = (com.android.server.tv.interactive.TvInteractiveAppManagerService.TvInteractiveAppState) orCreateUserStateLocked.mIAppMap.get(id);
            if (tvInteractiveAppState == null) {
                tvInteractiveAppState = new com.android.server.tv.interactive.TvInteractiveAppManagerService.TvInteractiveAppState();
            }
            tvInteractiveAppState.mInfo = tvInteractiveAppServiceInfo;
            tvInteractiveAppState.mUid = getInteractiveAppUid(tvInteractiveAppServiceInfo);
            tvInteractiveAppState.mComponentName = tvInteractiveAppServiceInfo.getComponent();
            hashMap.put(id, tvInteractiveAppState);
            tvInteractiveAppState.mIAppNumber = valueOf.intValue();
        }
        for (java.lang.String str : hashMap.keySet()) {
            if (!orCreateUserStateLocked.mIAppMap.containsKey(str)) {
                notifyInteractiveAppServiceAddedLocked(orCreateUserStateLocked, str);
            } else if (strArr != null) {
                android.content.ComponentName component = ((com.android.server.tv.interactive.TvInteractiveAppManagerService.TvInteractiveAppState) hashMap.get(str)).mInfo.getComponent();
                int length = strArr.length;
                int i2 = 0;
                while (true) {
                    if (i2 < length) {
                        if (!component.getPackageName().equals(strArr[i2])) {
                            i2++;
                        } else {
                            updateServiceConnectionLocked(component, i);
                            notifyInteractiveAppServiceUpdatedLocked(orCreateUserStateLocked, str);
                            break;
                        }
                    }
                }
            }
        }
        for (java.lang.String str2 : orCreateUserStateLocked.mIAppMap.keySet()) {
            if (!hashMap.containsKey(str2)) {
                com.android.server.tv.interactive.TvInteractiveAppManagerService.ServiceState serviceState = (com.android.server.tv.interactive.TvInteractiveAppManagerService.ServiceState) orCreateUserStateLocked.mServiceStateMap.get(((com.android.server.tv.interactive.TvInteractiveAppManagerService.TvInteractiveAppState) orCreateUserStateLocked.mIAppMap.get(str2)).mInfo.getComponent());
                if (serviceState != null) {
                    abortPendingCreateSessionRequestsLocked(serviceState, str2, i);
                }
                notifyInteractiveAppServiceRemovedLocked(orCreateUserStateLocked, str2);
            }
        }
        orCreateUserStateLocked.mIAppMap.clear();
        orCreateUserStateLocked.mIAppMap = hashMap;
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public void buildTvAdServiceListLocked(int i, java.lang.String[] strArr) {
        if (!android.media.tv.flags.Flags.enableAdServiceFw()) {
            return;
        }
        com.android.server.tv.interactive.TvInteractiveAppManagerService.UserState orCreateUserStateLocked = getOrCreateUserStateLocked(i);
        orCreateUserStateLocked.mPackageSet.clear();
        java.util.List queryIntentServicesAsUser = this.mContext.getPackageManager().queryIntentServicesAsUser(new android.content.Intent("android.media.tv.ad.TvAdService"), 132, i);
        java.util.ArrayList<android.media.tv.ad.TvAdServiceInfo> arrayList = new java.util.ArrayList();
        java.util.Iterator it = queryIntentServicesAsUser.iterator();
        while (it.hasNext()) {
            android.content.pm.ServiceInfo serviceInfo = ((android.content.pm.ResolveInfo) it.next()).serviceInfo;
            if ("android.permission.BIND_TV_AD_SERVICE".equals(serviceInfo.permission)) {
                try {
                    arrayList.add(new android.media.tv.ad.TvAdServiceInfo(this.mContext, new android.content.ComponentName(serviceInfo.packageName, serviceInfo.name)));
                    orCreateUserStateLocked.mPackageSet.add(serviceInfo.packageName);
                } catch (java.lang.Exception e) {
                    com.android.server.utils.Slogf.e(TAG, "failed to load TV AD service " + serviceInfo.name, e);
                }
            } else {
                android.util.Slog.w(TAG, "Skipping TV AD service " + serviceInfo.name + ": it does not require the permission android.permission.BIND_TV_AD_SERVICE");
            }
        }
        java.util.Collections.sort(arrayList, java.util.Comparator.comparing(new java.util.function.Function() { // from class: com.android.server.tv.interactive.TvInteractiveAppManagerService$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final java.lang.Object apply(java.lang.Object obj) {
                return ((android.media.tv.ad.TvAdServiceInfo) obj).getId();
            }
        }));
        java.util.HashMap hashMap = new java.util.HashMap();
        for (android.media.tv.ad.TvAdServiceInfo tvAdServiceInfo : arrayList) {
            java.lang.String id = tvAdServiceInfo.getId();
            com.android.server.tv.interactive.TvInteractiveAppManagerService.TvAdServiceState tvAdServiceState = (com.android.server.tv.interactive.TvInteractiveAppManagerService.TvAdServiceState) orCreateUserStateLocked.mAdServiceMap.get(id);
            if (tvAdServiceState == null) {
                tvAdServiceState = new com.android.server.tv.interactive.TvInteractiveAppManagerService.TvAdServiceState();
            }
            tvAdServiceState.mInfo = tvAdServiceInfo;
            tvAdServiceState.mUid = getAdServiceUid(tvAdServiceInfo);
            tvAdServiceState.mComponentName = tvAdServiceInfo.getComponent();
            hashMap.put(id, tvAdServiceState);
        }
        for (java.lang.String str : hashMap.keySet()) {
            if (!orCreateUserStateLocked.mAdServiceMap.containsKey(str)) {
                notifyAdServiceAddedLocked(orCreateUserStateLocked, str);
            } else if (strArr != null) {
                android.content.ComponentName component = ((com.android.server.tv.interactive.TvInteractiveAppManagerService.TvAdServiceState) hashMap.get(str)).mInfo.getComponent();
                int length = strArr.length;
                int i2 = 0;
                while (true) {
                    if (i2 < length) {
                        if (!component.getPackageName().equals(strArr[i2])) {
                            i2++;
                        } else {
                            updateAdServiceConnectionLocked(component, i);
                            notifyAdServiceUpdatedLocked(orCreateUserStateLocked, str);
                            break;
                        }
                    }
                }
            }
        }
        for (java.lang.String str2 : orCreateUserStateLocked.mAdServiceMap.keySet()) {
            if (!hashMap.containsKey(str2)) {
                com.android.server.tv.interactive.TvInteractiveAppManagerService.AdServiceState adServiceState = (com.android.server.tv.interactive.TvInteractiveAppManagerService.AdServiceState) orCreateUserStateLocked.mAdServiceStateMap.get(((com.android.server.tv.interactive.TvInteractiveAppManagerService.TvAdServiceState) orCreateUserStateLocked.mAdServiceMap.get(str2)).mInfo.getComponent());
                if (adServiceState != null) {
                    abortPendingCreateAdSessionRequestsLocked(adServiceState, str2, i);
                }
                notifyAdServiceRemovedLocked(orCreateUserStateLocked, str2);
            }
        }
        orCreateUserStateLocked.mIAppMap.clear();
        orCreateUserStateLocked.mAdServiceMap = hashMap;
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void notifyAdServiceAddedLocked(com.android.server.tv.interactive.TvInteractiveAppManagerService.UserState userState, java.lang.String str) {
        int beginBroadcast = userState.mAdCallbacks.beginBroadcast();
        for (int i = 0; i < beginBroadcast; i++) {
            try {
                userState.mAdCallbacks.getBroadcastItem(i).onAdServiceAdded(str);
            } catch (android.os.RemoteException e) {
                android.util.Slog.e(TAG, "failed to report added AD service to callback", e);
            }
        }
        userState.mAdCallbacks.finishBroadcast();
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void notifyAdServiceRemovedLocked(com.android.server.tv.interactive.TvInteractiveAppManagerService.UserState userState, java.lang.String str) {
        int beginBroadcast = userState.mAdCallbacks.beginBroadcast();
        for (int i = 0; i < beginBroadcast; i++) {
            try {
                userState.mAdCallbacks.getBroadcastItem(i).onAdServiceRemoved(str);
            } catch (android.os.RemoteException e) {
                android.util.Slog.e(TAG, "failed to report removed AD service to callback", e);
            }
        }
        userState.mAdCallbacks.finishBroadcast();
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void notifyAdServiceUpdatedLocked(com.android.server.tv.interactive.TvInteractiveAppManagerService.UserState userState, java.lang.String str) {
        int beginBroadcast = userState.mAdCallbacks.beginBroadcast();
        for (int i = 0; i < beginBroadcast; i++) {
            try {
                userState.mAdCallbacks.getBroadcastItem(i).onAdServiceUpdated(str);
            } catch (android.os.RemoteException e) {
                android.util.Slog.e(TAG, "failed to report updated AD service to callback", e);
            }
        }
        userState.mAdCallbacks.finishBroadcast();
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void notifyInteractiveAppServiceAddedLocked(com.android.server.tv.interactive.TvInteractiveAppManagerService.UserState userState, java.lang.String str) {
        int beginBroadcast = userState.mCallbacks.beginBroadcast();
        for (int i = 0; i < beginBroadcast; i++) {
            try {
                userState.mCallbacks.getBroadcastItem(i).onInteractiveAppServiceAdded(str);
            } catch (android.os.RemoteException e) {
                android.util.Slog.e(TAG, "failed to report added Interactive App service to callback", e);
            }
        }
        userState.mCallbacks.finishBroadcast();
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void notifyInteractiveAppServiceRemovedLocked(com.android.server.tv.interactive.TvInteractiveAppManagerService.UserState userState, java.lang.String str) {
        int beginBroadcast = userState.mCallbacks.beginBroadcast();
        for (int i = 0; i < beginBroadcast; i++) {
            try {
                userState.mCallbacks.getBroadcastItem(i).onInteractiveAppServiceRemoved(str);
            } catch (android.os.RemoteException e) {
                android.util.Slog.e(TAG, "failed to report removed Interactive App service to callback", e);
            }
        }
        userState.mCallbacks.finishBroadcast();
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void notifyInteractiveAppServiceUpdatedLocked(com.android.server.tv.interactive.TvInteractiveAppManagerService.UserState userState, java.lang.String str) {
        int beginBroadcast = userState.mCallbacks.beginBroadcast();
        for (int i = 0; i < beginBroadcast; i++) {
            try {
                userState.mCallbacks.getBroadcastItem(i).onInteractiveAppServiceUpdated(str);
            } catch (android.os.RemoteException e) {
                android.util.Slog.e(TAG, "failed to report updated Interactive App service to callback", e);
            }
        }
        userState.mCallbacks.finishBroadcast();
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public void notifyStateChangedLocked(com.android.server.tv.interactive.TvInteractiveAppManagerService.UserState userState, java.lang.String str, int i, int i2, int i3) {
        int beginBroadcast = userState.mCallbacks.beginBroadcast();
        for (int i4 = 0; i4 < beginBroadcast; i4++) {
            try {
                userState.mCallbacks.getBroadcastItem(i4).onStateChanged(str, i, i2, i3);
            } catch (android.os.RemoteException e) {
                android.util.Slog.e(TAG, "failed to report RTE state changed", e);
            }
        }
        userState.mCallbacks.finishBroadcast();
    }

    private int getInteractiveAppUid(android.media.tv.interactive.TvInteractiveAppServiceInfo tvInteractiveAppServiceInfo) {
        try {
            return getContext().getPackageManager().getApplicationInfo(tvInteractiveAppServiceInfo.getServiceInfo().packageName, 0).uid;
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            com.android.server.utils.Slogf.w(TAG, "Unable to get UID for  " + tvInteractiveAppServiceInfo, e);
            return -1;
        }
    }

    private int getAdServiceUid(android.media.tv.ad.TvAdServiceInfo tvAdServiceInfo) {
        try {
            return getContext().getPackageManager().getApplicationInfo(tvAdServiceInfo.getServiceInfo().packageName, 0).uid;
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            com.android.server.utils.Slogf.w(TAG, "Unable to get UID for  " + tvAdServiceInfo, e);
            return -1;
        }
    }

    @Override // com.android.server.SystemService
    public void onStart() {
        publishBinderService("tv_interactive_app", new com.android.server.tv.interactive.TvInteractiveAppManagerService.BinderService());
        publishBinderService("tv_ad", new com.android.server.tv.interactive.TvInteractiveAppManagerService.TvAdBinderService());
    }

    @Override // com.android.server.SystemService
    public void onBootPhase(int i) {
        if (i == 500) {
            registerBroadcastReceivers();
            return;
        }
        if (i == 600) {
            synchronized (this.mLock) {
                buildTvInteractiveAppServiceListLocked(this.mCurrentUserId, null);
                buildAppLinkInfoLocked(this.mCurrentUserId);
                buildTvAdServiceListLocked(this.mCurrentUserId, null);
            }
        }
    }

    private void registerBroadcastReceivers() {
        new com.android.internal.content.PackageMonitor() { // from class: com.android.server.tv.interactive.TvInteractiveAppManagerService.1
            private void buildTvInteractiveAppServiceList(java.lang.String[] strArr) {
                int changingUserId = getChangingUserId();
                synchronized (com.android.server.tv.interactive.TvInteractiveAppManagerService.this.mLock) {
                    try {
                        if (com.android.server.tv.interactive.TvInteractiveAppManagerService.this.mCurrentUserId == changingUserId || com.android.server.tv.interactive.TvInteractiveAppManagerService.this.mRunningProfiles.contains(java.lang.Integer.valueOf(changingUserId))) {
                            com.android.server.tv.interactive.TvInteractiveAppManagerService.this.buildTvInteractiveAppServiceListLocked(changingUserId, strArr);
                            com.android.server.tv.interactive.TvInteractiveAppManagerService.this.buildAppLinkInfoLocked(changingUserId);
                        }
                    } catch (java.lang.Throwable th) {
                        throw th;
                    }
                }
            }

            private void buildTvAdServiceList(java.lang.String[] strArr) {
                int changingUserId = getChangingUserId();
                synchronized (com.android.server.tv.interactive.TvInteractiveAppManagerService.this.mLock) {
                    try {
                        if (com.android.server.tv.interactive.TvInteractiveAppManagerService.this.mCurrentUserId == changingUserId || com.android.server.tv.interactive.TvInteractiveAppManagerService.this.mRunningProfiles.contains(java.lang.Integer.valueOf(changingUserId))) {
                            com.android.server.tv.interactive.TvInteractiveAppManagerService.this.buildTvAdServiceListLocked(changingUserId, strArr);
                        }
                    } catch (java.lang.Throwable th) {
                        throw th;
                    }
                }
            }

            public void onPackageUpdateFinished(java.lang.String str, int i) {
                buildTvInteractiveAppServiceList(new java.lang.String[]{str});
                buildTvAdServiceList(new java.lang.String[]{str});
            }

            public void onPackagesAvailable(java.lang.String[] strArr) {
                if (isReplacing()) {
                    buildTvInteractiveAppServiceList(strArr);
                    buildTvAdServiceList(strArr);
                }
            }

            public void onPackagesUnavailable(java.lang.String[] strArr) {
                if (isReplacing()) {
                    buildTvInteractiveAppServiceList(strArr);
                    buildTvAdServiceList(strArr);
                }
            }

            public void onSomePackagesChanged() {
                if (isReplacing()) {
                    return;
                }
                buildTvInteractiveAppServiceList(null);
                buildTvAdServiceList(null);
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
        this.mContext.registerReceiverAsUser(new android.content.BroadcastReceiver() { // from class: com.android.server.tv.interactive.TvInteractiveAppManagerService.2
            @Override // android.content.BroadcastReceiver
            public void onReceive(android.content.Context context, android.content.Intent intent) {
                java.lang.String action = intent.getAction();
                if ("android.intent.action.USER_SWITCHED".equals(action)) {
                    com.android.server.tv.interactive.TvInteractiveAppManagerService.this.switchUser(intent.getIntExtra("android.intent.extra.user_handle", 0));
                    return;
                }
                if ("android.intent.action.USER_REMOVED".equals(action)) {
                    com.android.server.tv.interactive.TvInteractiveAppManagerService.this.removeUser(intent.getIntExtra("android.intent.extra.user_handle", 0));
                    return;
                }
                if ("android.intent.action.USER_STARTED".equals(action)) {
                    com.android.server.tv.interactive.TvInteractiveAppManagerService.this.startUser(intent.getIntExtra("android.intent.extra.user_handle", 0));
                } else if ("android.intent.action.USER_STOPPED".equals(action)) {
                    com.android.server.tv.interactive.TvInteractiveAppManagerService.this.stopUser(intent.getIntExtra("android.intent.extra.user_handle", 0));
                }
            }
        }, android.os.UserHandle.ALL, intentFilter, null, null);
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
                buildTvInteractiveAppServiceListLocked(i, null);
                buildAppLinkInfoLocked(i);
                buildTvAdServiceListLocked(i, null);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void removeUser(int i) {
        synchronized (this.mLock) {
            try {
                com.android.server.tv.interactive.TvInteractiveAppManagerService.UserState userStateLocked = getUserStateLocked(i);
                if (userStateLocked == null) {
                    return;
                }
                for (com.android.server.tv.interactive.TvInteractiveAppManagerService.SessionState sessionState : userStateLocked.mSessionStateMap.values()) {
                    if (sessionState.mSession != null) {
                        try {
                            sessionState.mSession.release();
                        } catch (android.os.RemoteException e) {
                            android.util.Slog.e(TAG, "error in release", e);
                        }
                    }
                }
                userStateLocked.mSessionStateMap.clear();
                for (com.android.server.tv.interactive.TvInteractiveAppManagerService.ServiceState serviceState : userStateLocked.mServiceStateMap.values()) {
                    if (serviceState.mService != null) {
                        if (serviceState.mCallback != null) {
                            try {
                                serviceState.mService.unregisterCallback(serviceState.mCallback);
                            } catch (android.os.RemoteException e2) {
                                android.util.Slog.e(TAG, "error in unregisterCallback", e2);
                            }
                        }
                        this.mContext.unbindService(serviceState.mConnection);
                    }
                }
                userStateLocked.mServiceStateMap.clear();
                userStateLocked.mIAppMap.clear();
                userStateLocked.mPackageSet.clear();
                userStateLocked.mClientStateMap.clear();
                userStateLocked.mCallbacks.kill();
                this.mRunningProfiles.remove(java.lang.Integer.valueOf(i));
                this.mUserStates.remove(i);
                if (i == this.mCurrentUserId) {
                    switchUser(0);
                }
            } catch (java.lang.Throwable th) {
                throw th;
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
        buildTvInteractiveAppServiceListLocked(i, null);
        buildAppLinkInfoLocked(i);
        buildTvAdServiceListLocked(i, null);
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void releaseSessionOfUserLocked(int i) {
        com.android.server.tv.interactive.TvInteractiveAppManagerService.UserState userStateLocked = getUserStateLocked(i);
        if (userStateLocked == null) {
            return;
        }
        java.util.ArrayList<com.android.server.tv.interactive.TvInteractiveAppManagerService.SessionState> arrayList = new java.util.ArrayList();
        for (com.android.server.tv.interactive.TvInteractiveAppManagerService.SessionState sessionState : userStateLocked.mSessionStateMap.values()) {
            if (sessionState.mSession != null) {
                arrayList.add(sessionState);
            }
        }
        for (com.android.server.tv.interactive.TvInteractiveAppManagerService.SessionState sessionState2 : arrayList) {
            try {
                sessionState2.mSession.release();
            } catch (android.os.RemoteException e) {
                android.util.Slog.e(TAG, "error in release", e);
            }
            clearSessionAndNotifyClientLocked(sessionState2);
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void unbindServiceOfUserLocked(int i) {
        com.android.server.tv.interactive.TvInteractiveAppManagerService.UserState userStateLocked = getUserStateLocked(i);
        if (userStateLocked == null) {
            return;
        }
        java.util.Iterator it = userStateLocked.mServiceStateMap.keySet().iterator();
        while (it.hasNext()) {
            com.android.server.tv.interactive.TvInteractiveAppManagerService.ServiceState serviceState = (com.android.server.tv.interactive.TvInteractiveAppManagerService.ServiceState) userStateLocked.mServiceStateMap.get((android.content.ComponentName) it.next());
            if (serviceState != null && serviceState.mSessionTokens.isEmpty()) {
                if (serviceState.mCallback != null) {
                    try {
                        serviceState.mService.unregisterCallback(serviceState.mCallback);
                    } catch (android.os.RemoteException e) {
                        android.util.Slog.e(TAG, "error in unregisterCallback", e);
                    }
                }
                this.mContext.unbindService(serviceState.mConnection);
                it.remove();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public void clearSessionAndNotifyClientLocked(com.android.server.tv.interactive.TvInteractiveAppManagerService.SessionState sessionState) {
        if (sessionState.mClient != null) {
            try {
                sessionState.mClient.onSessionReleased(sessionState.mSeq);
            } catch (android.os.RemoteException e) {
                android.util.Slog.e(TAG, "error in onSessionReleased", e);
            }
        }
        removeAdSessionStateLocked(sessionState.mSessionToken, sessionState.mUserId);
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public void clearAdSessionAndNotifyClientLocked(com.android.server.tv.interactive.TvInteractiveAppManagerService.AdSessionState adSessionState) {
        if (adSessionState.mClient != null) {
            try {
                adSessionState.mClient.onSessionReleased(adSessionState.mSeq);
            } catch (android.os.RemoteException e) {
                android.util.Slog.e(TAG, "error in onSessionReleased", e);
            }
        }
        removeAdSessionStateLocked(adSessionState.mSessionToken, adSessionState.mUserId);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int resolveCallingUserId(int i, int i2, int i3, java.lang.String str) {
        return android.app.ActivityManager.handleIncomingUser(i, i2, i3, false, false, str, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public com.android.server.tv.interactive.TvInteractiveAppManagerService.UserState getOrCreateUserStateLocked(int i) {
        com.android.server.tv.interactive.TvInteractiveAppManagerService.UserState userStateLocked = getUserStateLocked(i);
        if (userStateLocked == null) {
            com.android.server.tv.interactive.TvInteractiveAppManagerService.UserState userState = new com.android.server.tv.interactive.TvInteractiveAppManagerService.UserState(i);
            this.mUserStates.put(i, userState);
            return userState;
        }
        return userStateLocked;
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public com.android.server.tv.interactive.TvInteractiveAppManagerService.UserState getUserStateLocked(int i) {
        return this.mUserStates.get(i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public com.android.server.tv.interactive.TvInteractiveAppManagerService.ServiceState getServiceStateLocked(android.content.ComponentName componentName, int i) {
        com.android.server.tv.interactive.TvInteractiveAppManagerService.ServiceState serviceState = (com.android.server.tv.interactive.TvInteractiveAppManagerService.ServiceState) getOrCreateUserStateLocked(i).mServiceStateMap.get(componentName);
        if (serviceState == null) {
            throw new java.lang.IllegalStateException("Service state not found for " + componentName + " (userId=" + i + ")");
        }
        return serviceState;
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public com.android.server.tv.interactive.TvInteractiveAppManagerService.AdSessionState getAdSessionStateLocked(android.os.IBinder iBinder, int i, int i2) {
        return getAdSessionStateLocked(iBinder, i, getOrCreateUserStateLocked(i2));
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private com.android.server.tv.interactive.TvInteractiveAppManagerService.AdSessionState getAdSessionStateLocked(android.os.IBinder iBinder, int i, com.android.server.tv.interactive.TvInteractiveAppManagerService.UserState userState) {
        com.android.server.tv.interactive.TvInteractiveAppManagerService.AdSessionState adSessionState = (com.android.server.tv.interactive.TvInteractiveAppManagerService.AdSessionState) userState.mAdSessionStateMap.get(iBinder);
        if (adSessionState == null) {
            throw new com.android.server.tv.interactive.TvInteractiveAppManagerService.SessionNotFoundException("Session state not found for token " + iBinder);
        }
        if (i != 1000 && i != adSessionState.mCallingUid) {
            throw new java.lang.SecurityException("Illegal access to the session with token " + iBinder + " from uid " + i);
        }
        return adSessionState;
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public android.media.tv.ad.ITvAdSession getAdSessionLocked(android.os.IBinder iBinder, int i, int i2) {
        return getAdSessionLocked(getAdSessionStateLocked(iBinder, i, i2));
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public android.media.tv.ad.ITvAdSession getAdSessionLocked(com.android.server.tv.interactive.TvInteractiveAppManagerService.AdSessionState adSessionState) {
        android.media.tv.ad.ITvAdSession iTvAdSession = adSessionState.mSession;
        if (iTvAdSession == null) {
            throw new java.lang.IllegalStateException("Session not yet created for token " + adSessionState.mSessionToken);
        }
        return iTvAdSession;
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public com.android.server.tv.interactive.TvInteractiveAppManagerService.SessionState getSessionStateLocked(android.os.IBinder iBinder, int i, int i2) {
        return getSessionStateLocked(iBinder, i, getOrCreateUserStateLocked(i2));
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private com.android.server.tv.interactive.TvInteractiveAppManagerService.SessionState getSessionStateLocked(android.os.IBinder iBinder, int i, com.android.server.tv.interactive.TvInteractiveAppManagerService.UserState userState) {
        com.android.server.tv.interactive.TvInteractiveAppManagerService.SessionState sessionState = (com.android.server.tv.interactive.TvInteractiveAppManagerService.SessionState) userState.mSessionStateMap.get(iBinder);
        if (sessionState == null) {
            throw new com.android.server.tv.interactive.TvInteractiveAppManagerService.SessionNotFoundException("Session state not found for token " + iBinder);
        }
        if (i != 1000 && i != sessionState.mCallingUid) {
            throw new java.lang.SecurityException("Illegal access to the session with token " + iBinder + " from uid " + i);
        }
        return sessionState;
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public android.media.tv.interactive.ITvInteractiveAppSession getSessionLocked(android.os.IBinder iBinder, int i, int i2) {
        return getSessionLocked(getSessionStateLocked(iBinder, i, i2));
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public android.media.tv.interactive.ITvInteractiveAppSession getSessionLocked(com.android.server.tv.interactive.TvInteractiveAppManagerService.SessionState sessionState) {
        android.media.tv.interactive.ITvInteractiveAppSession iTvInteractiveAppSession = sessionState.mSession;
        if (iTvInteractiveAppSession == null) {
            throw new java.lang.IllegalStateException("Session not yet created for token " + sessionState.mSessionToken);
        }
        return iTvInteractiveAppSession;
    }

    private final class TvAdBinderService extends android.media.tv.ad.ITvAdManager.Stub {
        private TvAdBinderService() {
        }

        public java.util.List<android.media.tv.ad.TvAdServiceInfo> getTvAdServiceList(int i) {
            java.util.ArrayList arrayList;
            int resolveCallingUserId = com.android.server.tv.interactive.TvInteractiveAppManagerService.this.resolveCallingUserId(android.os.Binder.getCallingPid(), android.os.Binder.getCallingUid(), i, "getTvAdServiceList");
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                synchronized (com.android.server.tv.interactive.TvInteractiveAppManagerService.this.mLock) {
                    try {
                        if (!com.android.server.tv.interactive.TvInteractiveAppManagerService.this.mGetAdServiceListCalled) {
                            com.android.server.tv.interactive.TvInteractiveAppManagerService.this.buildTvAdServiceListLocked(i, null);
                            com.android.server.tv.interactive.TvInteractiveAppManagerService.this.mGetAdServiceListCalled = true;
                        }
                        com.android.server.tv.interactive.TvInteractiveAppManagerService.UserState orCreateUserStateLocked = com.android.server.tv.interactive.TvInteractiveAppManagerService.this.getOrCreateUserStateLocked(resolveCallingUserId);
                        arrayList = new java.util.ArrayList();
                        java.util.Iterator it = orCreateUserStateLocked.mAdServiceMap.values().iterator();
                        while (it.hasNext()) {
                            arrayList.add(((com.android.server.tv.interactive.TvInteractiveAppManagerService.TvAdServiceState) it.next()).mInfo);
                        }
                    } finally {
                    }
                }
                return arrayList;
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void sendAppLinkCommand(java.lang.String str, android.os.Bundle bundle, int i) {
            int resolveCallingUserId = com.android.server.tv.interactive.TvInteractiveAppManagerService.this.resolveCallingUserId(android.os.Binder.getCallingPid(), android.os.Binder.getCallingUid(), i, "sendAppLinkCommand");
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                try {
                } catch (android.os.RemoteException e) {
                    com.android.server.utils.Slogf.e(com.android.server.tv.interactive.TvInteractiveAppManagerService.TAG, "error in sendAppLinkCommand", e);
                }
                synchronized (com.android.server.tv.interactive.TvInteractiveAppManagerService.this.mLock) {
                    com.android.server.tv.interactive.TvInteractiveAppManagerService.UserState orCreateUserStateLocked = com.android.server.tv.interactive.TvInteractiveAppManagerService.this.getOrCreateUserStateLocked(resolveCallingUserId);
                    com.android.server.tv.interactive.TvInteractiveAppManagerService.TvAdServiceState tvAdServiceState = (com.android.server.tv.interactive.TvInteractiveAppManagerService.TvAdServiceState) orCreateUserStateLocked.mAdServiceMap.get(str);
                    if (tvAdServiceState == null) {
                        com.android.server.utils.Slogf.e(com.android.server.tv.interactive.TvInteractiveAppManagerService.TAG, "failed to sendAppLinkCommand - unknown service id " + str);
                        return;
                    }
                    android.content.ComponentName component = tvAdServiceState.mInfo.getComponent();
                    com.android.server.tv.interactive.TvInteractiveAppManagerService.AdServiceState adServiceState = (com.android.server.tv.interactive.TvInteractiveAppManagerService.AdServiceState) orCreateUserStateLocked.mAdServiceStateMap.get(component);
                    if (adServiceState == null) {
                        com.android.server.tv.interactive.TvInteractiveAppManagerService.AdServiceState adServiceState2 = new com.android.server.tv.interactive.TvInteractiveAppManagerService.AdServiceState(component, str, resolveCallingUserId);
                        adServiceState2.addPendingAppLinkCommand(bundle);
                        orCreateUserStateLocked.mAdServiceStateMap.put(component, adServiceState2);
                        com.android.server.tv.interactive.TvInteractiveAppManagerService.this.updateAdServiceConnectionLocked(component, resolveCallingUserId);
                    } else if (adServiceState.mService != null) {
                        adServiceState.mService.sendAppLinkCommand(bundle);
                    } else {
                        adServiceState.addPendingAppLinkCommand(bundle);
                        com.android.server.tv.interactive.TvInteractiveAppManagerService.this.updateAdServiceConnectionLocked(component, resolveCallingUserId);
                    }
                }
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void createSession(android.media.tv.ad.ITvAdClient iTvAdClient, java.lang.String str, java.lang.String str2, int i, int i2) {
            long j;
            com.android.server.tv.interactive.TvInteractiveAppManagerService.AdServiceState adServiceState;
            int callingUid = android.os.Binder.getCallingUid();
            int callingPid = android.os.Binder.getCallingPid();
            int resolveCallingUserId = com.android.server.tv.interactive.TvInteractiveAppManagerService.this.resolveCallingUserId(callingPid, callingUid, i2, "createSession");
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                try {
                    synchronized (com.android.server.tv.interactive.TvInteractiveAppManagerService.this.mLock) {
                        try {
                            if (i2 != com.android.server.tv.interactive.TvInteractiveAppManagerService.this.mCurrentUserId && !com.android.server.tv.interactive.TvInteractiveAppManagerService.this.mRunningProfiles.contains(java.lang.Integer.valueOf(i2))) {
                                com.android.server.tv.interactive.TvInteractiveAppManagerService.this.sendAdSessionTokenToClientLocked(iTvAdClient, str, null, null, i);
                                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                                return;
                            }
                            com.android.server.tv.interactive.TvInteractiveAppManagerService.UserState orCreateUserStateLocked = com.android.server.tv.interactive.TvInteractiveAppManagerService.this.getOrCreateUserStateLocked(resolveCallingUserId);
                            com.android.server.tv.interactive.TvInteractiveAppManagerService.TvAdServiceState tvAdServiceState = (com.android.server.tv.interactive.TvInteractiveAppManagerService.TvAdServiceState) orCreateUserStateLocked.mAdMap.get(str);
                            if (tvAdServiceState == null) {
                                com.android.server.utils.Slogf.w(com.android.server.tv.interactive.TvInteractiveAppManagerService.TAG, "Failed to find state for serviceId=" + str);
                                com.android.server.tv.interactive.TvInteractiveAppManagerService.this.sendAdSessionTokenToClientLocked(iTvAdClient, str, null, null, i);
                                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                                return;
                            }
                            com.android.server.tv.interactive.TvInteractiveAppManagerService.AdServiceState adServiceState2 = (com.android.server.tv.interactive.TvInteractiveAppManagerService.AdServiceState) orCreateUserStateLocked.mAdServiceStateMap.get(tvAdServiceState.mComponentName);
                            if (adServiceState2 == null) {
                                int i3 = android.content.pm.PackageManager.getApplicationInfoAsUserCached(tvAdServiceState.mComponentName.getPackageName(), 0L, resolveCallingUserId).uid;
                                com.android.server.tv.interactive.TvInteractiveAppManagerService.AdServiceState adServiceState3 = new com.android.server.tv.interactive.TvInteractiveAppManagerService.AdServiceState(tvAdServiceState.mComponentName, str, resolveCallingUserId);
                                orCreateUserStateLocked.mAdServiceStateMap.put(tvAdServiceState.mComponentName, adServiceState3);
                                adServiceState = adServiceState3;
                            } else {
                                adServiceState = adServiceState2;
                            }
                            if (adServiceState.mReconnecting) {
                                com.android.server.tv.interactive.TvInteractiveAppManagerService.this.sendAdSessionTokenToClientLocked(iTvAdClient, str, null, null, i);
                                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                                return;
                            }
                            android.os.Binder binder = new android.os.Binder();
                            orCreateUserStateLocked.mAdSessionStateMap.put(binder, new com.android.server.tv.interactive.TvInteractiveAppManagerService.AdSessionState(binder, str, str2, tvAdServiceState.mComponentName, iTvAdClient, i, callingUid, callingPid, resolveCallingUserId));
                            adServiceState.mSessionTokens.add(binder);
                            if (adServiceState.mService == null) {
                                com.android.server.tv.interactive.TvInteractiveAppManagerService.this.updateAdServiceConnectionLocked(tvAdServiceState.mComponentName, resolveCallingUserId);
                            } else if (!com.android.server.tv.interactive.TvInteractiveAppManagerService.this.createAdSessionInternalLocked(adServiceState.mService, binder, resolveCallingUserId)) {
                                com.android.server.tv.interactive.TvInteractiveAppManagerService.this.removeAdSessionStateLocked(binder, resolveCallingUserId);
                            }
                            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                        } catch (java.lang.Throwable th) {
                            th = th;
                            j = clearCallingIdentity;
                            try {
                                throw th;
                            } catch (java.lang.Throwable th2) {
                                th = th2;
                                android.os.Binder.restoreCallingIdentity(j);
                                throw th;
                            }
                        }
                    }
                } catch (java.lang.Throwable th3) {
                    th = th3;
                }
            } catch (java.lang.Throwable th4) {
                th = th4;
                j = clearCallingIdentity;
            }
        }

        public void releaseSession(android.os.IBinder iBinder, int i) {
            int callingUid = android.os.Binder.getCallingUid();
            int resolveCallingUserId = com.android.server.tv.interactive.TvInteractiveAppManagerService.this.resolveCallingUserId(android.os.Binder.getCallingPid(), callingUid, i, "releaseSession");
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                synchronized (com.android.server.tv.interactive.TvInteractiveAppManagerService.this.mLock) {
                    com.android.server.tv.interactive.TvInteractiveAppManagerService.this.releaseSessionLocked(iBinder, callingUid, resolveCallingUserId);
                }
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void setSurface(android.os.IBinder iBinder, android.view.Surface surface, int i) {
            int callingUid = android.os.Binder.getCallingUid();
            int resolveCallingUserId = com.android.server.tv.interactive.TvInteractiveAppManagerService.this.resolveCallingUserId(android.os.Binder.getCallingPid(), callingUid, i, "setSurface");
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                synchronized (com.android.server.tv.interactive.TvInteractiveAppManagerService.this.mLock) {
                    try {
                        com.android.server.tv.interactive.TvInteractiveAppManagerService.this.getAdSessionLocked(com.android.server.tv.interactive.TvInteractiveAppManagerService.this.getAdSessionStateLocked(iBinder, callingUid, resolveCallingUserId)).setSurface(surface);
                    } catch (android.os.RemoteException | com.android.server.tv.interactive.TvInteractiveAppManagerService.SessionNotFoundException e) {
                        com.android.server.utils.Slogf.e(com.android.server.tv.interactive.TvInteractiveAppManagerService.TAG, "error in setSurface", e);
                    }
                }
            } finally {
                if (surface != null) {
                    surface.release();
                }
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void dispatchSurfaceChanged(android.os.IBinder iBinder, int i, int i2, int i3, int i4) {
            int callingUid = android.os.Binder.getCallingUid();
            int resolveCallingUserId = com.android.server.tv.interactive.TvInteractiveAppManagerService.this.resolveCallingUserId(android.os.Binder.getCallingPid(), callingUid, i4, "dispatchSurfaceChanged");
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                synchronized (com.android.server.tv.interactive.TvInteractiveAppManagerService.this.mLock) {
                    try {
                        com.android.server.tv.interactive.TvInteractiveAppManagerService.this.getAdSessionLocked(com.android.server.tv.interactive.TvInteractiveAppManagerService.this.getAdSessionStateLocked(iBinder, callingUid, resolveCallingUserId)).dispatchSurfaceChanged(i, i2, i3);
                    } catch (android.os.RemoteException | com.android.server.tv.interactive.TvInteractiveAppManagerService.SessionNotFoundException e) {
                        com.android.server.utils.Slogf.e(com.android.server.tv.interactive.TvInteractiveAppManagerService.TAG, "error in dispatchSurfaceChanged", e);
                    }
                }
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void startAdService(android.os.IBinder iBinder, int i) {
            int callingUid = android.os.Binder.getCallingUid();
            int resolveCallingUserId = com.android.server.tv.interactive.TvInteractiveAppManagerService.this.resolveCallingUserId(android.os.Binder.getCallingPid(), callingUid, i, "startAdService");
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                synchronized (com.android.server.tv.interactive.TvInteractiveAppManagerService.this.mLock) {
                    try {
                        com.android.server.tv.interactive.TvInteractiveAppManagerService.this.getAdSessionLocked(com.android.server.tv.interactive.TvInteractiveAppManagerService.this.getAdSessionStateLocked(iBinder, callingUid, resolveCallingUserId)).startAdService();
                    } catch (android.os.RemoteException | com.android.server.tv.interactive.TvInteractiveAppManagerService.SessionNotFoundException e) {
                        com.android.server.utils.Slogf.e(com.android.server.tv.interactive.TvInteractiveAppManagerService.TAG, "error in start", e);
                    }
                }
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void stopAdService(android.os.IBinder iBinder, int i) {
            int callingUid = android.os.Binder.getCallingUid();
            int resolveCallingUserId = com.android.server.tv.interactive.TvInteractiveAppManagerService.this.resolveCallingUserId(android.os.Binder.getCallingPid(), callingUid, i, "stopAdService");
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                synchronized (com.android.server.tv.interactive.TvInteractiveAppManagerService.this.mLock) {
                    try {
                        com.android.server.tv.interactive.TvInteractiveAppManagerService.this.getAdSessionLocked(com.android.server.tv.interactive.TvInteractiveAppManagerService.this.getAdSessionStateLocked(iBinder, callingUid, resolveCallingUserId)).stopAdService();
                    } catch (android.os.RemoteException | com.android.server.tv.interactive.TvInteractiveAppManagerService.SessionNotFoundException e) {
                        com.android.server.utils.Slogf.e(com.android.server.tv.interactive.TvInteractiveAppManagerService.TAG, "error in stop", e);
                    }
                }
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void resetAdService(android.os.IBinder iBinder, int i) {
            int callingUid = android.os.Binder.getCallingUid();
            int resolveCallingUserId = com.android.server.tv.interactive.TvInteractiveAppManagerService.this.resolveCallingUserId(android.os.Binder.getCallingPid(), callingUid, i, "resetAdService");
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                synchronized (com.android.server.tv.interactive.TvInteractiveAppManagerService.this.mLock) {
                    try {
                        com.android.server.tv.interactive.TvInteractiveAppManagerService.this.getAdSessionLocked(com.android.server.tv.interactive.TvInteractiveAppManagerService.this.getAdSessionStateLocked(iBinder, callingUid, resolveCallingUserId)).resetAdService();
                    } catch (android.os.RemoteException | com.android.server.tv.interactive.TvInteractiveAppManagerService.SessionNotFoundException e) {
                        com.android.server.utils.Slogf.e(com.android.server.tv.interactive.TvInteractiveAppManagerService.TAG, "error in reset", e);
                    }
                }
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void sendCurrentVideoBounds(android.os.IBinder iBinder, android.graphics.Rect rect, int i) {
            int callingUid = android.os.Binder.getCallingUid();
            int resolveCallingUserId = com.android.server.tv.interactive.TvInteractiveAppManagerService.this.resolveCallingUserId(android.os.Binder.getCallingPid(), callingUid, i, "sendCurrentVideoBounds");
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                synchronized (com.android.server.tv.interactive.TvInteractiveAppManagerService.this.mLock) {
                    try {
                        com.android.server.tv.interactive.TvInteractiveAppManagerService.this.getAdSessionLocked(com.android.server.tv.interactive.TvInteractiveAppManagerService.this.getAdSessionStateLocked(iBinder, callingUid, resolveCallingUserId)).sendCurrentVideoBounds(rect);
                    } catch (android.os.RemoteException | com.android.server.tv.interactive.TvInteractiveAppManagerService.SessionNotFoundException e) {
                        com.android.server.utils.Slogf.e(com.android.server.tv.interactive.TvInteractiveAppManagerService.TAG, "error in sendCurrentVideoBounds", e);
                    }
                }
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void sendCurrentChannelUri(android.os.IBinder iBinder, android.net.Uri uri, int i) {
            int callingUid = android.os.Binder.getCallingUid();
            int resolveCallingUserId = com.android.server.tv.interactive.TvInteractiveAppManagerService.this.resolveCallingUserId(android.os.Binder.getCallingPid(), callingUid, i, "sendCurrentChannelUri");
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                synchronized (com.android.server.tv.interactive.TvInteractiveAppManagerService.this.mLock) {
                    try {
                        com.android.server.tv.interactive.TvInteractiveAppManagerService.this.getAdSessionLocked(com.android.server.tv.interactive.TvInteractiveAppManagerService.this.getAdSessionStateLocked(iBinder, callingUid, resolveCallingUserId)).sendCurrentChannelUri(uri);
                    } catch (android.os.RemoteException | com.android.server.tv.interactive.TvInteractiveAppManagerService.SessionNotFoundException e) {
                        com.android.server.utils.Slogf.e(com.android.server.tv.interactive.TvInteractiveAppManagerService.TAG, "error in sendCurrentChannelUri", e);
                    }
                }
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void sendTrackInfoList(android.os.IBinder iBinder, java.util.List<android.media.tv.TvTrackInfo> list, int i) {
            int callingUid = android.os.Binder.getCallingUid();
            int resolveCallingUserId = com.android.server.tv.interactive.TvInteractiveAppManagerService.this.resolveCallingUserId(android.os.Binder.getCallingPid(), callingUid, i, "sendTrackInfoList");
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                synchronized (com.android.server.tv.interactive.TvInteractiveAppManagerService.this.mLock) {
                    try {
                        com.android.server.tv.interactive.TvInteractiveAppManagerService.this.getAdSessionLocked(com.android.server.tv.interactive.TvInteractiveAppManagerService.this.getAdSessionStateLocked(iBinder, callingUid, resolveCallingUserId)).sendTrackInfoList(list);
                    } catch (android.os.RemoteException | com.android.server.tv.interactive.TvInteractiveAppManagerService.SessionNotFoundException e) {
                        com.android.server.utils.Slogf.e(com.android.server.tv.interactive.TvInteractiveAppManagerService.TAG, "error in sendTrackInfoList", e);
                    }
                }
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void sendCurrentTvInputId(android.os.IBinder iBinder, java.lang.String str, int i) {
            int callingUid = android.os.Binder.getCallingUid();
            int resolveCallingUserId = com.android.server.tv.interactive.TvInteractiveAppManagerService.this.resolveCallingUserId(android.os.Binder.getCallingPid(), callingUid, i, "sendCurrentTvInputId");
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                synchronized (com.android.server.tv.interactive.TvInteractiveAppManagerService.this.mLock) {
                    try {
                        com.android.server.tv.interactive.TvInteractiveAppManagerService.this.getAdSessionLocked(com.android.server.tv.interactive.TvInteractiveAppManagerService.this.getAdSessionStateLocked(iBinder, callingUid, resolveCallingUserId)).sendCurrentTvInputId(str);
                    } catch (android.os.RemoteException | com.android.server.tv.interactive.TvInteractiveAppManagerService.SessionNotFoundException e) {
                        com.android.server.utils.Slogf.e(com.android.server.tv.interactive.TvInteractiveAppManagerService.TAG, "error in sendCurrentTvInputId", e);
                    }
                }
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void sendSigningResult(android.os.IBinder iBinder, java.lang.String str, byte[] bArr, int i) {
            int callingUid = android.os.Binder.getCallingUid();
            int resolveCallingUserId = com.android.server.tv.interactive.TvInteractiveAppManagerService.this.resolveCallingUserId(android.os.Binder.getCallingPid(), callingUid, i, "sendSigningResult");
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                synchronized (com.android.server.tv.interactive.TvInteractiveAppManagerService.this.mLock) {
                    try {
                        com.android.server.tv.interactive.TvInteractiveAppManagerService.this.getAdSessionLocked(com.android.server.tv.interactive.TvInteractiveAppManagerService.this.getAdSessionStateLocked(iBinder, callingUid, resolveCallingUserId)).sendSigningResult(str, bArr);
                    } catch (android.os.RemoteException | com.android.server.tv.interactive.TvInteractiveAppManagerService.SessionNotFoundException e) {
                        com.android.server.utils.Slogf.e(com.android.server.tv.interactive.TvInteractiveAppManagerService.TAG, "error in sendSigningResult", e);
                    }
                }
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void notifyError(android.os.IBinder iBinder, java.lang.String str, android.os.Bundle bundle, int i) {
            int callingUid = android.os.Binder.getCallingUid();
            int resolveCallingUserId = com.android.server.tv.interactive.TvInteractiveAppManagerService.this.resolveCallingUserId(android.os.Binder.getCallingPid(), callingUid, i, "notifyError");
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                synchronized (com.android.server.tv.interactive.TvInteractiveAppManagerService.this.mLock) {
                    try {
                        com.android.server.tv.interactive.TvInteractiveAppManagerService.this.getAdSessionLocked(com.android.server.tv.interactive.TvInteractiveAppManagerService.this.getAdSessionStateLocked(iBinder, callingUid, resolveCallingUserId)).notifyError(str, bundle);
                    } catch (android.os.RemoteException | com.android.server.tv.interactive.TvInteractiveAppManagerService.SessionNotFoundException e) {
                        com.android.server.utils.Slogf.e(com.android.server.tv.interactive.TvInteractiveAppManagerService.TAG, "error in notifyError", e);
                    }
                }
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void notifyTvMessage(android.os.IBinder iBinder, int i, android.os.Bundle bundle, int i2) {
            int callingUid = android.os.Binder.getCallingUid();
            int resolveCallingUserId = com.android.server.tv.interactive.TvInteractiveAppManagerService.this.resolveCallingUserId(android.os.Binder.getCallingPid(), callingUid, i2, "notifyTvMessage");
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                synchronized (com.android.server.tv.interactive.TvInteractiveAppManagerService.this.mLock) {
                    try {
                        com.android.server.tv.interactive.TvInteractiveAppManagerService.this.getAdSessionLocked(com.android.server.tv.interactive.TvInteractiveAppManagerService.this.getAdSessionStateLocked(iBinder, callingUid, resolveCallingUserId)).notifyTvMessage(i, bundle);
                    } catch (android.os.RemoteException | com.android.server.tv.interactive.TvInteractiveAppManagerService.SessionNotFoundException e) {
                        com.android.server.utils.Slogf.e(com.android.server.tv.interactive.TvInteractiveAppManagerService.TAG, "error in notifyTvMessage", e);
                    }
                }
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void notifyTvInputSessionData(android.os.IBinder iBinder, java.lang.String str, android.os.Bundle bundle, int i) {
            int callingUid = android.os.Binder.getCallingUid();
            int resolveCallingUserId = com.android.server.tv.interactive.TvInteractiveAppManagerService.this.resolveCallingUserId(android.os.Binder.getCallingPid(), callingUid, i, "notifyTvInputSessionData");
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                synchronized (com.android.server.tv.interactive.TvInteractiveAppManagerService.this.mLock) {
                    try {
                        com.android.server.tv.interactive.TvInteractiveAppManagerService.this.getAdSessionLocked(com.android.server.tv.interactive.TvInteractiveAppManagerService.this.getAdSessionStateLocked(iBinder, callingUid, resolveCallingUserId)).notifyTvInputSessionData(str, bundle);
                    } catch (android.os.RemoteException | com.android.server.tv.interactive.TvInteractiveAppManagerService.SessionNotFoundException e) {
                        com.android.server.utils.Slogf.e(com.android.server.tv.interactive.TvInteractiveAppManagerService.TAG, "error in notifyTvInputSessionData", e);
                    }
                }
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void registerCallback(android.media.tv.ad.ITvAdManagerCallback iTvAdManagerCallback, int i) {
            int resolveCallingUserId = com.android.server.tv.interactive.TvInteractiveAppManagerService.this.resolveCallingUserId(android.os.Binder.getCallingPid(), android.os.Binder.getCallingUid(), i, "registerCallback");
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                synchronized (com.android.server.tv.interactive.TvInteractiveAppManagerService.this.mLock) {
                    try {
                        if (!com.android.server.tv.interactive.TvInteractiveAppManagerService.this.getOrCreateUserStateLocked(resolveCallingUserId).mAdCallbacks.register(iTvAdManagerCallback)) {
                            android.util.Slog.e(com.android.server.tv.interactive.TvInteractiveAppManagerService.TAG, "client process has already died");
                        }
                    } finally {
                    }
                }
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void unregisterCallback(android.media.tv.ad.ITvAdManagerCallback iTvAdManagerCallback, int i) {
            int resolveCallingUserId = com.android.server.tv.interactive.TvInteractiveAppManagerService.this.resolveCallingUserId(android.os.Binder.getCallingPid(), android.os.Binder.getCallingUid(), i, "unregisterCallback");
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                synchronized (com.android.server.tv.interactive.TvInteractiveAppManagerService.this.mLock) {
                    com.android.server.tv.interactive.TvInteractiveAppManagerService.this.getOrCreateUserStateLocked(resolveCallingUserId).mAdCallbacks.unregister(iTvAdManagerCallback);
                }
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void createMediaView(android.os.IBinder iBinder, android.os.IBinder iBinder2, android.graphics.Rect rect, int i) {
            int callingUid = android.os.Binder.getCallingUid();
            int resolveCallingUserId = com.android.server.tv.interactive.TvInteractiveAppManagerService.this.resolveCallingUserId(android.os.Binder.getCallingPid(), callingUid, i, "createMediaView");
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                synchronized (com.android.server.tv.interactive.TvInteractiveAppManagerService.this.mLock) {
                    try {
                        com.android.server.tv.interactive.TvInteractiveAppManagerService.this.getAdSessionLocked(iBinder, callingUid, resolveCallingUserId).createMediaView(iBinder2, rect);
                    } catch (android.os.RemoteException | com.android.server.tv.interactive.TvInteractiveAppManagerService.SessionNotFoundException e) {
                        android.util.Slog.e(com.android.server.tv.interactive.TvInteractiveAppManagerService.TAG, "error in createMediaView", e);
                    }
                }
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void relayoutMediaView(android.os.IBinder iBinder, android.graphics.Rect rect, int i) {
            int callingUid = android.os.Binder.getCallingUid();
            int resolveCallingUserId = com.android.server.tv.interactive.TvInteractiveAppManagerService.this.resolveCallingUserId(android.os.Binder.getCallingPid(), callingUid, i, "relayoutMediaView");
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                synchronized (com.android.server.tv.interactive.TvInteractiveAppManagerService.this.mLock) {
                    try {
                        com.android.server.tv.interactive.TvInteractiveAppManagerService.this.getAdSessionLocked(iBinder, callingUid, resolveCallingUserId).relayoutMediaView(rect);
                    } catch (android.os.RemoteException | com.android.server.tv.interactive.TvInteractiveAppManagerService.SessionNotFoundException e) {
                        android.util.Slog.e(com.android.server.tv.interactive.TvInteractiveAppManagerService.TAG, "error in relayoutMediaView", e);
                    }
                }
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void removeMediaView(android.os.IBinder iBinder, int i) {
            int callingUid = android.os.Binder.getCallingUid();
            int resolveCallingUserId = com.android.server.tv.interactive.TvInteractiveAppManagerService.this.resolveCallingUserId(android.os.Binder.getCallingPid(), callingUid, i, "removeMediaView");
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                synchronized (com.android.server.tv.interactive.TvInteractiveAppManagerService.this.mLock) {
                    try {
                        com.android.server.tv.interactive.TvInteractiveAppManagerService.this.getAdSessionLocked(iBinder, callingUid, resolveCallingUserId).removeMediaView();
                    } catch (android.os.RemoteException | com.android.server.tv.interactive.TvInteractiveAppManagerService.SessionNotFoundException e) {
                        android.util.Slog.e(com.android.server.tv.interactive.TvInteractiveAppManagerService.TAG, "error in removeMediaView", e);
                    }
                }
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }

    private final class BinderService extends android.media.tv.interactive.ITvInteractiveAppManager.Stub {
        private BinderService() {
        }

        public java.util.List<android.media.tv.interactive.TvInteractiveAppServiceInfo> getTvInteractiveAppServiceList(int i) {
            java.util.ArrayList arrayList;
            int resolveCallingUserId = com.android.server.tv.interactive.TvInteractiveAppManagerService.this.resolveCallingUserId(android.os.Binder.getCallingPid(), android.os.Binder.getCallingUid(), i, "getTvInteractiveAppServiceList");
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                synchronized (com.android.server.tv.interactive.TvInteractiveAppManagerService.this.mLock) {
                    try {
                        if (!com.android.server.tv.interactive.TvInteractiveAppManagerService.this.mGetServiceListCalled) {
                            com.android.server.tv.interactive.TvInteractiveAppManagerService.this.buildTvInteractiveAppServiceListLocked(i, null);
                            com.android.server.tv.interactive.TvInteractiveAppManagerService.this.mGetServiceListCalled = true;
                        }
                        com.android.server.tv.interactive.TvInteractiveAppManagerService.UserState orCreateUserStateLocked = com.android.server.tv.interactive.TvInteractiveAppManagerService.this.getOrCreateUserStateLocked(resolveCallingUserId);
                        arrayList = new java.util.ArrayList();
                        java.util.Iterator it = orCreateUserStateLocked.mIAppMap.values().iterator();
                        while (it.hasNext()) {
                            arrayList.add(((com.android.server.tv.interactive.TvInteractiveAppManagerService.TvInteractiveAppState) it.next()).mInfo);
                        }
                    } finally {
                    }
                }
                return arrayList;
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public java.util.List<android.media.tv.interactive.AppLinkInfo> getAppLinkInfoList(int i) {
            java.util.ArrayList arrayList;
            int resolveCallingUserId = com.android.server.tv.interactive.TvInteractiveAppManagerService.this.resolveCallingUserId(android.os.Binder.getCallingPid(), android.os.Binder.getCallingUid(), i, "getAppLinkInfoList");
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                synchronized (com.android.server.tv.interactive.TvInteractiveAppManagerService.this.mLock) {
                    try {
                        if (!com.android.server.tv.interactive.TvInteractiveAppManagerService.this.mGetAppLinkInfoListCalled) {
                            com.android.server.tv.interactive.TvInteractiveAppManagerService.this.buildAppLinkInfoLocked(i);
                            com.android.server.tv.interactive.TvInteractiveAppManagerService.this.mGetAppLinkInfoListCalled = true;
                        }
                        arrayList = new java.util.ArrayList(com.android.server.tv.interactive.TvInteractiveAppManagerService.this.getOrCreateUserStateLocked(resolveCallingUserId).mAppLinkInfoList);
                    } catch (java.lang.Throwable th) {
                        throw th;
                    }
                }
                return arrayList;
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void registerAppLinkInfo(java.lang.String str, android.media.tv.interactive.AppLinkInfo appLinkInfo, int i) {
            int resolveCallingUserId = com.android.server.tv.interactive.TvInteractiveAppManagerService.this.resolveCallingUserId(android.os.Binder.getCallingPid(), android.os.Binder.getCallingUid(), i, "registerAppLinkInfo: " + appLinkInfo);
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                try {
                } catch (android.os.RemoteException e) {
                    com.android.server.utils.Slogf.e(com.android.server.tv.interactive.TvInteractiveAppManagerService.TAG, "error in registerAppLinkInfo", e);
                }
                synchronized (com.android.server.tv.interactive.TvInteractiveAppManagerService.this.mLock) {
                    com.android.server.tv.interactive.TvInteractiveAppManagerService.UserState orCreateUserStateLocked = com.android.server.tv.interactive.TvInteractiveAppManagerService.this.getOrCreateUserStateLocked(resolveCallingUserId);
                    com.android.server.tv.interactive.TvInteractiveAppManagerService.TvInteractiveAppState tvInteractiveAppState = (com.android.server.tv.interactive.TvInteractiveAppManagerService.TvInteractiveAppState) orCreateUserStateLocked.mIAppMap.get(str);
                    if (tvInteractiveAppState == null) {
                        com.android.server.utils.Slogf.e(com.android.server.tv.interactive.TvInteractiveAppManagerService.TAG, "failed to registerAppLinkInfo - unknown TIAS id " + str);
                        return;
                    }
                    android.content.ComponentName component = tvInteractiveAppState.mInfo.getComponent();
                    com.android.server.tv.interactive.TvInteractiveAppManagerService.ServiceState serviceState = (com.android.server.tv.interactive.TvInteractiveAppManagerService.ServiceState) orCreateUserStateLocked.mServiceStateMap.get(component);
                    if (serviceState == null) {
                        com.android.server.tv.interactive.TvInteractiveAppManagerService.ServiceState serviceState2 = new com.android.server.tv.interactive.TvInteractiveAppManagerService.ServiceState(component, str, resolveCallingUserId);
                        serviceState2.addPendingAppLink(appLinkInfo, true);
                        orCreateUserStateLocked.mServiceStateMap.put(component, serviceState2);
                        com.android.server.tv.interactive.TvInteractiveAppManagerService.this.updateServiceConnectionLocked(component, resolveCallingUserId);
                    } else if (serviceState.mService != null) {
                        serviceState.mService.registerAppLinkInfo(appLinkInfo);
                    } else {
                        serviceState.addPendingAppLink(appLinkInfo, true);
                        com.android.server.tv.interactive.TvInteractiveAppManagerService.this.updateServiceConnectionLocked(component, resolveCallingUserId);
                    }
                }
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void unregisterAppLinkInfo(java.lang.String str, android.media.tv.interactive.AppLinkInfo appLinkInfo, int i) {
            int resolveCallingUserId = com.android.server.tv.interactive.TvInteractiveAppManagerService.this.resolveCallingUserId(android.os.Binder.getCallingPid(), android.os.Binder.getCallingUid(), i, "unregisterAppLinkInfo: " + appLinkInfo);
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                try {
                } catch (android.os.RemoteException e) {
                    com.android.server.utils.Slogf.e(com.android.server.tv.interactive.TvInteractiveAppManagerService.TAG, "error in unregisterAppLinkInfo", e);
                }
                synchronized (com.android.server.tv.interactive.TvInteractiveAppManagerService.this.mLock) {
                    com.android.server.tv.interactive.TvInteractiveAppManagerService.UserState orCreateUserStateLocked = com.android.server.tv.interactive.TvInteractiveAppManagerService.this.getOrCreateUserStateLocked(resolveCallingUserId);
                    com.android.server.tv.interactive.TvInteractiveAppManagerService.TvInteractiveAppState tvInteractiveAppState = (com.android.server.tv.interactive.TvInteractiveAppManagerService.TvInteractiveAppState) orCreateUserStateLocked.mIAppMap.get(str);
                    if (tvInteractiveAppState == null) {
                        com.android.server.utils.Slogf.e(com.android.server.tv.interactive.TvInteractiveAppManagerService.TAG, "failed to unregisterAppLinkInfo - unknown TIAS id " + str);
                        return;
                    }
                    android.content.ComponentName component = tvInteractiveAppState.mInfo.getComponent();
                    com.android.server.tv.interactive.TvInteractiveAppManagerService.ServiceState serviceState = (com.android.server.tv.interactive.TvInteractiveAppManagerService.ServiceState) orCreateUserStateLocked.mServiceStateMap.get(component);
                    if (serviceState == null) {
                        com.android.server.tv.interactive.TvInteractiveAppManagerService.ServiceState serviceState2 = new com.android.server.tv.interactive.TvInteractiveAppManagerService.ServiceState(component, str, resolveCallingUserId);
                        serviceState2.addPendingAppLink(appLinkInfo, false);
                        orCreateUserStateLocked.mServiceStateMap.put(component, serviceState2);
                        com.android.server.tv.interactive.TvInteractiveAppManagerService.this.updateServiceConnectionLocked(component, resolveCallingUserId);
                    } else if (serviceState.mService != null) {
                        serviceState.mService.unregisterAppLinkInfo(appLinkInfo);
                    } else {
                        serviceState.addPendingAppLink(appLinkInfo, false);
                        com.android.server.tv.interactive.TvInteractiveAppManagerService.this.updateServiceConnectionLocked(component, resolveCallingUserId);
                    }
                }
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void sendAppLinkCommand(java.lang.String str, android.os.Bundle bundle, int i) {
            int resolveCallingUserId = com.android.server.tv.interactive.TvInteractiveAppManagerService.this.resolveCallingUserId(android.os.Binder.getCallingPid(), android.os.Binder.getCallingUid(), i, "sendAppLinkCommand");
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                try {
                } catch (android.os.RemoteException e) {
                    com.android.server.utils.Slogf.e(com.android.server.tv.interactive.TvInteractiveAppManagerService.TAG, "error in sendAppLinkCommand", e);
                }
                synchronized (com.android.server.tv.interactive.TvInteractiveAppManagerService.this.mLock) {
                    com.android.server.tv.interactive.TvInteractiveAppManagerService.UserState orCreateUserStateLocked = com.android.server.tv.interactive.TvInteractiveAppManagerService.this.getOrCreateUserStateLocked(resolveCallingUserId);
                    com.android.server.tv.interactive.TvInteractiveAppManagerService.TvInteractiveAppState tvInteractiveAppState = (com.android.server.tv.interactive.TvInteractiveAppManagerService.TvInteractiveAppState) orCreateUserStateLocked.mIAppMap.get(str);
                    if (tvInteractiveAppState == null) {
                        com.android.server.utils.Slogf.e(com.android.server.tv.interactive.TvInteractiveAppManagerService.TAG, "failed to sendAppLinkCommand - unknown TIAS id " + str);
                        return;
                    }
                    android.content.ComponentName component = tvInteractiveAppState.mInfo.getComponent();
                    com.android.server.tv.interactive.TvInteractiveAppManagerService.ServiceState serviceState = (com.android.server.tv.interactive.TvInteractiveAppManagerService.ServiceState) orCreateUserStateLocked.mServiceStateMap.get(component);
                    if (serviceState == null) {
                        com.android.server.tv.interactive.TvInteractiveAppManagerService.ServiceState serviceState2 = new com.android.server.tv.interactive.TvInteractiveAppManagerService.ServiceState(component, str, resolveCallingUserId);
                        serviceState2.addPendingAppLinkCommand(bundle);
                        orCreateUserStateLocked.mServiceStateMap.put(component, serviceState2);
                        com.android.server.tv.interactive.TvInteractiveAppManagerService.this.updateServiceConnectionLocked(component, resolveCallingUserId);
                    } else if (serviceState.mService != null) {
                        serviceState.mService.sendAppLinkCommand(bundle);
                    } else {
                        serviceState.addPendingAppLinkCommand(bundle);
                        com.android.server.tv.interactive.TvInteractiveAppManagerService.this.updateServiceConnectionLocked(component, resolveCallingUserId);
                    }
                }
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void createSession(android.media.tv.interactive.ITvInteractiveAppClient iTvInteractiveAppClient, java.lang.String str, int i, int i2, int i3) {
            long j;
            com.android.server.tv.interactive.TvInteractiveAppManagerService.ServiceState serviceState;
            int callingUid = android.os.Binder.getCallingUid();
            int callingPid = android.os.Binder.getCallingPid();
            int resolveCallingUserId = com.android.server.tv.interactive.TvInteractiveAppManagerService.this.resolveCallingUserId(callingPid, callingUid, i3, "createSession");
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                try {
                    synchronized (com.android.server.tv.interactive.TvInteractiveAppManagerService.this.mLock) {
                        try {
                            if (i3 != com.android.server.tv.interactive.TvInteractiveAppManagerService.this.mCurrentUserId && !com.android.server.tv.interactive.TvInteractiveAppManagerService.this.mRunningProfiles.contains(java.lang.Integer.valueOf(i3))) {
                                com.android.server.tv.interactive.TvInteractiveAppManagerService.this.sendSessionTokenToClientLocked(iTvInteractiveAppClient, str, null, null, i2);
                                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                                return;
                            }
                            com.android.server.tv.interactive.TvInteractiveAppManagerService.UserState orCreateUserStateLocked = com.android.server.tv.interactive.TvInteractiveAppManagerService.this.getOrCreateUserStateLocked(resolveCallingUserId);
                            com.android.server.tv.interactive.TvInteractiveAppManagerService.TvInteractiveAppState tvInteractiveAppState = (com.android.server.tv.interactive.TvInteractiveAppManagerService.TvInteractiveAppState) orCreateUserStateLocked.mIAppMap.get(str);
                            if (tvInteractiveAppState == null) {
                                com.android.server.utils.Slogf.w(com.android.server.tv.interactive.TvInteractiveAppManagerService.TAG, "Failed to find state for iAppServiceId=" + str);
                                com.android.server.tv.interactive.TvInteractiveAppManagerService.this.sendSessionTokenToClientLocked(iTvInteractiveAppClient, str, null, null, i2);
                                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                                return;
                            }
                            com.android.server.tv.interactive.TvInteractiveAppManagerService.ServiceState serviceState2 = (com.android.server.tv.interactive.TvInteractiveAppManagerService.ServiceState) orCreateUserStateLocked.mServiceStateMap.get(tvInteractiveAppState.mComponentName);
                            if (serviceState2 == null) {
                                int i4 = android.content.pm.PackageManager.getApplicationInfoAsUserCached(tvInteractiveAppState.mComponentName.getPackageName(), 0L, resolveCallingUserId).uid;
                                com.android.server.tv.interactive.TvInteractiveAppManagerService.ServiceState serviceState3 = new com.android.server.tv.interactive.TvInteractiveAppManagerService.ServiceState(tvInteractiveAppState.mComponentName, str, resolveCallingUserId);
                                orCreateUserStateLocked.mServiceStateMap.put(tvInteractiveAppState.mComponentName, serviceState3);
                                serviceState = serviceState3;
                            } else {
                                serviceState = serviceState2;
                            }
                            if (serviceState.mReconnecting) {
                                com.android.server.tv.interactive.TvInteractiveAppManagerService.this.sendSessionTokenToClientLocked(iTvInteractiveAppClient, str, null, null, i2);
                                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                                return;
                            }
                            android.os.Binder binder = new android.os.Binder();
                            orCreateUserStateLocked.mSessionStateMap.put(binder, new com.android.server.tv.interactive.TvInteractiveAppManagerService.SessionState(binder, str, i, tvInteractiveAppState.mComponentName, iTvInteractiveAppClient, i2, callingUid, callingPid, resolveCallingUserId));
                            serviceState.mSessionTokens.add(binder);
                            if (serviceState.mService == null) {
                                com.android.server.tv.interactive.TvInteractiveAppManagerService.this.updateServiceConnectionLocked(tvInteractiveAppState.mComponentName, resolveCallingUserId);
                            } else if (!com.android.server.tv.interactive.TvInteractiveAppManagerService.this.createSessionInternalLocked(serviceState.mService, binder, resolveCallingUserId)) {
                                com.android.server.tv.interactive.TvInteractiveAppManagerService.this.removeSessionStateLocked(binder, resolveCallingUserId);
                            }
                            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                        } catch (java.lang.Throwable th) {
                            th = th;
                            j = clearCallingIdentity;
                            try {
                                throw th;
                            } catch (java.lang.Throwable th2) {
                                th = th2;
                                android.os.Binder.restoreCallingIdentity(j);
                                throw th;
                            }
                        }
                    }
                } catch (java.lang.Throwable th3) {
                    th = th3;
                }
            } catch (java.lang.Throwable th4) {
                th = th4;
                j = clearCallingIdentity;
            }
        }

        public void releaseSession(android.os.IBinder iBinder, int i) {
            int callingUid = android.os.Binder.getCallingUid();
            int resolveCallingUserId = com.android.server.tv.interactive.TvInteractiveAppManagerService.this.resolveCallingUserId(android.os.Binder.getCallingPid(), callingUid, i, "releaseSession");
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                synchronized (com.android.server.tv.interactive.TvInteractiveAppManagerService.this.mLock) {
                    com.android.server.tv.interactive.TvInteractiveAppManagerService.this.releaseAdSessionLocked(iBinder, callingUid, resolveCallingUserId);
                }
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void notifyTuned(android.os.IBinder iBinder, android.net.Uri uri, int i) {
            int callingUid = android.os.Binder.getCallingUid();
            int resolveCallingUserId = com.android.server.tv.interactive.TvInteractiveAppManagerService.this.resolveCallingUserId(android.os.Binder.getCallingPid(), callingUid, i, "notifyTuned");
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                synchronized (com.android.server.tv.interactive.TvInteractiveAppManagerService.this.mLock) {
                    try {
                        com.android.server.tv.interactive.TvInteractiveAppManagerService.this.getSessionLocked(com.android.server.tv.interactive.TvInteractiveAppManagerService.this.getSessionStateLocked(iBinder, callingUid, resolveCallingUserId)).notifyTuned(uri);
                    } catch (android.os.RemoteException | com.android.server.tv.interactive.TvInteractiveAppManagerService.SessionNotFoundException e) {
                        com.android.server.utils.Slogf.e(com.android.server.tv.interactive.TvInteractiveAppManagerService.TAG, "error in notifyTuned", e);
                    }
                }
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void notifyTrackSelected(android.os.IBinder iBinder, int i, java.lang.String str, int i2) {
            int callingUid = android.os.Binder.getCallingUid();
            int resolveCallingUserId = com.android.server.tv.interactive.TvInteractiveAppManagerService.this.resolveCallingUserId(android.os.Binder.getCallingPid(), callingUid, i2, "notifyTrackSelected");
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                synchronized (com.android.server.tv.interactive.TvInteractiveAppManagerService.this.mLock) {
                    try {
                        com.android.server.tv.interactive.TvInteractiveAppManagerService.this.getSessionLocked(com.android.server.tv.interactive.TvInteractiveAppManagerService.this.getSessionStateLocked(iBinder, callingUid, resolveCallingUserId)).notifyTrackSelected(i, str);
                    } catch (android.os.RemoteException | com.android.server.tv.interactive.TvInteractiveAppManagerService.SessionNotFoundException e) {
                        com.android.server.utils.Slogf.e(com.android.server.tv.interactive.TvInteractiveAppManagerService.TAG, "error in notifyTrackSelected", e);
                    }
                }
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void notifyTracksChanged(android.os.IBinder iBinder, java.util.List<android.media.tv.TvTrackInfo> list, int i) {
            int callingUid = android.os.Binder.getCallingUid();
            int resolveCallingUserId = com.android.server.tv.interactive.TvInteractiveAppManagerService.this.resolveCallingUserId(android.os.Binder.getCallingPid(), callingUid, i, "notifyTracksChanged");
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                synchronized (com.android.server.tv.interactive.TvInteractiveAppManagerService.this.mLock) {
                    try {
                        com.android.server.tv.interactive.TvInteractiveAppManagerService.this.getSessionLocked(com.android.server.tv.interactive.TvInteractiveAppManagerService.this.getSessionStateLocked(iBinder, callingUid, resolveCallingUserId)).notifyTracksChanged(list);
                    } catch (android.os.RemoteException | com.android.server.tv.interactive.TvInteractiveAppManagerService.SessionNotFoundException e) {
                        com.android.server.utils.Slogf.e(com.android.server.tv.interactive.TvInteractiveAppManagerService.TAG, "error in notifyTracksChanged", e);
                    }
                }
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void notifyVideoAvailable(android.os.IBinder iBinder, int i) {
            int callingUid = android.os.Binder.getCallingUid();
            int resolveCallingUserId = com.android.server.tv.interactive.TvInteractiveAppManagerService.this.resolveCallingUserId(android.os.Binder.getCallingPid(), callingUid, i, "notifyVideoAvailable");
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                synchronized (com.android.server.tv.interactive.TvInteractiveAppManagerService.this.mLock) {
                    try {
                        com.android.server.tv.interactive.TvInteractiveAppManagerService.this.getSessionLocked(com.android.server.tv.interactive.TvInteractiveAppManagerService.this.getSessionStateLocked(iBinder, callingUid, resolveCallingUserId)).notifyVideoAvailable();
                    } catch (android.os.RemoteException | com.android.server.tv.interactive.TvInteractiveAppManagerService.SessionNotFoundException e) {
                        com.android.server.utils.Slogf.e(com.android.server.tv.interactive.TvInteractiveAppManagerService.TAG, "error in notifyVideoAvailable", e);
                    }
                }
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void notifyVideoUnavailable(android.os.IBinder iBinder, int i, int i2) {
            int callingUid = android.os.Binder.getCallingUid();
            int resolveCallingUserId = com.android.server.tv.interactive.TvInteractiveAppManagerService.this.resolveCallingUserId(android.os.Binder.getCallingPid(), callingUid, i2, "notifyVideoUnavailable");
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                synchronized (com.android.server.tv.interactive.TvInteractiveAppManagerService.this.mLock) {
                    try {
                        com.android.server.tv.interactive.TvInteractiveAppManagerService.this.getSessionLocked(com.android.server.tv.interactive.TvInteractiveAppManagerService.this.getSessionStateLocked(iBinder, callingUid, resolveCallingUserId)).notifyVideoUnavailable(i);
                    } catch (android.os.RemoteException | com.android.server.tv.interactive.TvInteractiveAppManagerService.SessionNotFoundException e) {
                        com.android.server.utils.Slogf.e(com.android.server.tv.interactive.TvInteractiveAppManagerService.TAG, "error in notifyVideoUnavailable", e);
                    }
                }
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void notifyVideoFreezeUpdated(android.os.IBinder iBinder, boolean z, int i) {
            int callingUid = android.os.Binder.getCallingUid();
            int resolveCallingUserId = com.android.server.tv.interactive.TvInteractiveAppManagerService.this.resolveCallingUserId(android.os.Binder.getCallingPid(), callingUid, i, "notifyVideoFreezeUpdated");
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                synchronized (com.android.server.tv.interactive.TvInteractiveAppManagerService.this.mLock) {
                    try {
                        com.android.server.tv.interactive.TvInteractiveAppManagerService.this.getSessionLocked(com.android.server.tv.interactive.TvInteractiveAppManagerService.this.getSessionStateLocked(iBinder, callingUid, resolveCallingUserId)).notifyVideoFreezeUpdated(z);
                    } catch (android.os.RemoteException | com.android.server.tv.interactive.TvInteractiveAppManagerService.SessionNotFoundException e) {
                        com.android.server.utils.Slogf.e(com.android.server.tv.interactive.TvInteractiveAppManagerService.TAG, "error in notifyVideoFreezeUpdated", e);
                    }
                }
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void notifyContentAllowed(android.os.IBinder iBinder, int i) {
            int callingUid = android.os.Binder.getCallingUid();
            int resolveCallingUserId = com.android.server.tv.interactive.TvInteractiveAppManagerService.this.resolveCallingUserId(android.os.Binder.getCallingPid(), callingUid, i, "notifyContentAllowed");
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                synchronized (com.android.server.tv.interactive.TvInteractiveAppManagerService.this.mLock) {
                    try {
                        com.android.server.tv.interactive.TvInteractiveAppManagerService.this.getSessionLocked(com.android.server.tv.interactive.TvInteractiveAppManagerService.this.getSessionStateLocked(iBinder, callingUid, resolveCallingUserId)).notifyContentAllowed();
                    } catch (android.os.RemoteException | com.android.server.tv.interactive.TvInteractiveAppManagerService.SessionNotFoundException e) {
                        com.android.server.utils.Slogf.e(com.android.server.tv.interactive.TvInteractiveAppManagerService.TAG, "error in notifyContentAllowed", e);
                    }
                }
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void notifyContentBlocked(android.os.IBinder iBinder, java.lang.String str, int i) {
            int callingUid = android.os.Binder.getCallingUid();
            int resolveCallingUserId = com.android.server.tv.interactive.TvInteractiveAppManagerService.this.resolveCallingUserId(android.os.Binder.getCallingPid(), callingUid, i, "notifyContentBlocked");
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                synchronized (com.android.server.tv.interactive.TvInteractiveAppManagerService.this.mLock) {
                    try {
                        com.android.server.tv.interactive.TvInteractiveAppManagerService.this.getSessionLocked(com.android.server.tv.interactive.TvInteractiveAppManagerService.this.getSessionStateLocked(iBinder, callingUid, resolveCallingUserId)).notifyContentBlocked(str);
                    } catch (android.os.RemoteException | com.android.server.tv.interactive.TvInteractiveAppManagerService.SessionNotFoundException e) {
                        com.android.server.utils.Slogf.e(com.android.server.tv.interactive.TvInteractiveAppManagerService.TAG, "error in notifyContentBlocked", e);
                    }
                }
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void notifySignalStrength(android.os.IBinder iBinder, int i, int i2) {
            int callingUid = android.os.Binder.getCallingUid();
            int resolveCallingUserId = com.android.server.tv.interactive.TvInteractiveAppManagerService.this.resolveCallingUserId(android.os.Binder.getCallingPid(), callingUid, i2, "notifySignalStrength");
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                synchronized (com.android.server.tv.interactive.TvInteractiveAppManagerService.this.mLock) {
                    try {
                        com.android.server.tv.interactive.TvInteractiveAppManagerService.this.getSessionLocked(com.android.server.tv.interactive.TvInteractiveAppManagerService.this.getSessionStateLocked(iBinder, callingUid, resolveCallingUserId)).notifySignalStrength(i);
                    } catch (android.os.RemoteException | com.android.server.tv.interactive.TvInteractiveAppManagerService.SessionNotFoundException e) {
                        com.android.server.utils.Slogf.e(com.android.server.tv.interactive.TvInteractiveAppManagerService.TAG, "error in notifySignalStrength", e);
                    }
                }
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void notifyTvMessage(android.os.IBinder iBinder, int i, android.os.Bundle bundle, int i2) {
            int callingUid = android.os.Binder.getCallingUid();
            int resolveCallingUserId = com.android.server.tv.interactive.TvInteractiveAppManagerService.this.resolveCallingUserId(android.os.Binder.getCallingPid(), callingUid, i2, "notifyTvMessage");
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                synchronized (com.android.server.tv.interactive.TvInteractiveAppManagerService.this.mLock) {
                    try {
                        com.android.server.tv.interactive.TvInteractiveAppManagerService.this.getSessionLocked(com.android.server.tv.interactive.TvInteractiveAppManagerService.this.getSessionStateLocked(iBinder, callingUid, resolveCallingUserId)).notifyTvMessage(i, bundle);
                    } catch (android.os.RemoteException | com.android.server.tv.interactive.TvInteractiveAppManagerService.SessionNotFoundException e) {
                        com.android.server.utils.Slogf.e(com.android.server.tv.interactive.TvInteractiveAppManagerService.TAG, "error in notifyTvMessage", e);
                    }
                }
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void notifyRecordingStarted(android.os.IBinder iBinder, java.lang.String str, java.lang.String str2, int i) {
            int callingUid = android.os.Binder.getCallingUid();
            int resolveCallingUserId = com.android.server.tv.interactive.TvInteractiveAppManagerService.this.resolveCallingUserId(android.os.Binder.getCallingPid(), callingUid, i, "notifyRecordingStarted");
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                synchronized (com.android.server.tv.interactive.TvInteractiveAppManagerService.this.mLock) {
                    try {
                        com.android.server.tv.interactive.TvInteractiveAppManagerService.this.getSessionLocked(com.android.server.tv.interactive.TvInteractiveAppManagerService.this.getSessionStateLocked(iBinder, callingUid, resolveCallingUserId)).notifyRecordingStarted(str, str2);
                    } catch (android.os.RemoteException | com.android.server.tv.interactive.TvInteractiveAppManagerService.SessionNotFoundException e) {
                        com.android.server.utils.Slogf.e(com.android.server.tv.interactive.TvInteractiveAppManagerService.TAG, "error in notifyRecordingStarted", e);
                    }
                }
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void notifyRecordingStopped(android.os.IBinder iBinder, java.lang.String str, int i) {
            int callingUid = android.os.Binder.getCallingUid();
            int resolveCallingUserId = com.android.server.tv.interactive.TvInteractiveAppManagerService.this.resolveCallingUserId(android.os.Binder.getCallingPid(), callingUid, i, "notifyRecordingStopped");
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                synchronized (com.android.server.tv.interactive.TvInteractiveAppManagerService.this.mLock) {
                    try {
                        com.android.server.tv.interactive.TvInteractiveAppManagerService.this.getSessionLocked(com.android.server.tv.interactive.TvInteractiveAppManagerService.this.getSessionStateLocked(iBinder, callingUid, resolveCallingUserId)).notifyRecordingStopped(str);
                    } catch (android.os.RemoteException | com.android.server.tv.interactive.TvInteractiveAppManagerService.SessionNotFoundException e) {
                        com.android.server.utils.Slogf.e(com.android.server.tv.interactive.TvInteractiveAppManagerService.TAG, "error in notifyRecordingStopped", e);
                    }
                }
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void startInteractiveApp(android.os.IBinder iBinder, int i) {
            int callingUid = android.os.Binder.getCallingUid();
            int resolveCallingUserId = com.android.server.tv.interactive.TvInteractiveAppManagerService.this.resolveCallingUserId(android.os.Binder.getCallingPid(), callingUid, i, "startInteractiveApp");
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                synchronized (com.android.server.tv.interactive.TvInteractiveAppManagerService.this.mLock) {
                    try {
                        com.android.server.tv.interactive.TvInteractiveAppManagerService.this.getSessionLocked(com.android.server.tv.interactive.TvInteractiveAppManagerService.this.getSessionStateLocked(iBinder, callingUid, resolveCallingUserId)).startInteractiveApp();
                    } catch (android.os.RemoteException | com.android.server.tv.interactive.TvInteractiveAppManagerService.SessionNotFoundException e) {
                        com.android.server.utils.Slogf.e(com.android.server.tv.interactive.TvInteractiveAppManagerService.TAG, "error in start", e);
                    }
                }
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void stopInteractiveApp(android.os.IBinder iBinder, int i) {
            int callingUid = android.os.Binder.getCallingUid();
            int resolveCallingUserId = com.android.server.tv.interactive.TvInteractiveAppManagerService.this.resolveCallingUserId(android.os.Binder.getCallingPid(), callingUid, i, "stopInteractiveApp");
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                synchronized (com.android.server.tv.interactive.TvInteractiveAppManagerService.this.mLock) {
                    try {
                        com.android.server.tv.interactive.TvInteractiveAppManagerService.this.getSessionLocked(com.android.server.tv.interactive.TvInteractiveAppManagerService.this.getSessionStateLocked(iBinder, callingUid, resolveCallingUserId)).stopInteractiveApp();
                    } catch (android.os.RemoteException | com.android.server.tv.interactive.TvInteractiveAppManagerService.SessionNotFoundException e) {
                        com.android.server.utils.Slogf.e(com.android.server.tv.interactive.TvInteractiveAppManagerService.TAG, "error in stop", e);
                    }
                }
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void resetInteractiveApp(android.os.IBinder iBinder, int i) {
            int callingUid = android.os.Binder.getCallingUid();
            int resolveCallingUserId = com.android.server.tv.interactive.TvInteractiveAppManagerService.this.resolveCallingUserId(android.os.Binder.getCallingPid(), callingUid, i, "resetInteractiveApp");
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                synchronized (com.android.server.tv.interactive.TvInteractiveAppManagerService.this.mLock) {
                    try {
                        com.android.server.tv.interactive.TvInteractiveAppManagerService.this.getSessionLocked(com.android.server.tv.interactive.TvInteractiveAppManagerService.this.getSessionStateLocked(iBinder, callingUid, resolveCallingUserId)).resetInteractiveApp();
                    } catch (android.os.RemoteException | com.android.server.tv.interactive.TvInteractiveAppManagerService.SessionNotFoundException e) {
                        com.android.server.utils.Slogf.e(com.android.server.tv.interactive.TvInteractiveAppManagerService.TAG, "error in reset", e);
                    }
                }
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void createBiInteractiveApp(android.os.IBinder iBinder, android.net.Uri uri, android.os.Bundle bundle, int i) {
            int callingUid = android.os.Binder.getCallingUid();
            int resolveCallingUserId = com.android.server.tv.interactive.TvInteractiveAppManagerService.this.resolveCallingUserId(android.os.Binder.getCallingPid(), callingUid, i, "createBiInteractiveApp");
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                synchronized (com.android.server.tv.interactive.TvInteractiveAppManagerService.this.mLock) {
                    try {
                        com.android.server.tv.interactive.TvInteractiveAppManagerService.this.getSessionLocked(com.android.server.tv.interactive.TvInteractiveAppManagerService.this.getSessionStateLocked(iBinder, callingUid, resolveCallingUserId)).createBiInteractiveApp(uri, bundle);
                    } catch (android.os.RemoteException | com.android.server.tv.interactive.TvInteractiveAppManagerService.SessionNotFoundException e) {
                        com.android.server.utils.Slogf.e(com.android.server.tv.interactive.TvInteractiveAppManagerService.TAG, "error in createBiInteractiveApp", e);
                    }
                }
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void destroyBiInteractiveApp(android.os.IBinder iBinder, java.lang.String str, int i) {
            int callingUid = android.os.Binder.getCallingUid();
            int resolveCallingUserId = com.android.server.tv.interactive.TvInteractiveAppManagerService.this.resolveCallingUserId(android.os.Binder.getCallingPid(), callingUid, i, "destroyBiInteractiveApp");
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                synchronized (com.android.server.tv.interactive.TvInteractiveAppManagerService.this.mLock) {
                    try {
                        com.android.server.tv.interactive.TvInteractiveAppManagerService.this.getSessionLocked(com.android.server.tv.interactive.TvInteractiveAppManagerService.this.getSessionStateLocked(iBinder, callingUid, resolveCallingUserId)).destroyBiInteractiveApp(str);
                    } catch (android.os.RemoteException | com.android.server.tv.interactive.TvInteractiveAppManagerService.SessionNotFoundException e) {
                        com.android.server.utils.Slogf.e(com.android.server.tv.interactive.TvInteractiveAppManagerService.TAG, "error in destroyBiInteractiveApp", e);
                    }
                }
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void setTeletextAppEnabled(android.os.IBinder iBinder, boolean z, int i) {
            int callingUid = android.os.Binder.getCallingUid();
            int resolveCallingUserId = com.android.server.tv.interactive.TvInteractiveAppManagerService.this.resolveCallingUserId(android.os.Binder.getCallingPid(), callingUid, i, "setTeletextAppEnabled");
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                synchronized (com.android.server.tv.interactive.TvInteractiveAppManagerService.this.mLock) {
                    try {
                        com.android.server.tv.interactive.TvInteractiveAppManagerService.this.getSessionLocked(com.android.server.tv.interactive.TvInteractiveAppManagerService.this.getSessionStateLocked(iBinder, callingUid, resolveCallingUserId)).setTeletextAppEnabled(z);
                    } catch (android.os.RemoteException | com.android.server.tv.interactive.TvInteractiveAppManagerService.SessionNotFoundException e) {
                        com.android.server.utils.Slogf.e(com.android.server.tv.interactive.TvInteractiveAppManagerService.TAG, "error in setTeletextAppEnabled", e);
                    }
                }
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void sendCurrentVideoBounds(android.os.IBinder iBinder, android.graphics.Rect rect, int i) {
            int callingUid = android.os.Binder.getCallingUid();
            int resolveCallingUserId = com.android.server.tv.interactive.TvInteractiveAppManagerService.this.resolveCallingUserId(android.os.Binder.getCallingPid(), callingUid, i, "sendCurrentVideoBounds");
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                synchronized (com.android.server.tv.interactive.TvInteractiveAppManagerService.this.mLock) {
                    try {
                        com.android.server.tv.interactive.TvInteractiveAppManagerService.this.getSessionLocked(com.android.server.tv.interactive.TvInteractiveAppManagerService.this.getSessionStateLocked(iBinder, callingUid, resolveCallingUserId)).sendCurrentVideoBounds(rect);
                    } catch (android.os.RemoteException | com.android.server.tv.interactive.TvInteractiveAppManagerService.SessionNotFoundException e) {
                        com.android.server.utils.Slogf.e(com.android.server.tv.interactive.TvInteractiveAppManagerService.TAG, "error in sendCurrentVideoBounds", e);
                    }
                }
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void sendCurrentChannelUri(android.os.IBinder iBinder, android.net.Uri uri, int i) {
            int callingUid = android.os.Binder.getCallingUid();
            int resolveCallingUserId = com.android.server.tv.interactive.TvInteractiveAppManagerService.this.resolveCallingUserId(android.os.Binder.getCallingPid(), callingUid, i, "sendCurrentChannelUri");
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                synchronized (com.android.server.tv.interactive.TvInteractiveAppManagerService.this.mLock) {
                    try {
                        com.android.server.tv.interactive.TvInteractiveAppManagerService.this.getSessionLocked(com.android.server.tv.interactive.TvInteractiveAppManagerService.this.getSessionStateLocked(iBinder, callingUid, resolveCallingUserId)).sendCurrentChannelUri(uri);
                    } catch (android.os.RemoteException | com.android.server.tv.interactive.TvInteractiveAppManagerService.SessionNotFoundException e) {
                        com.android.server.utils.Slogf.e(com.android.server.tv.interactive.TvInteractiveAppManagerService.TAG, "error in sendCurrentChannelUri", e);
                    }
                }
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void sendCurrentChannelLcn(android.os.IBinder iBinder, int i, int i2) {
            int callingUid = android.os.Binder.getCallingUid();
            int resolveCallingUserId = com.android.server.tv.interactive.TvInteractiveAppManagerService.this.resolveCallingUserId(android.os.Binder.getCallingPid(), callingUid, i2, "sendCurrentChannelLcn");
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                synchronized (com.android.server.tv.interactive.TvInteractiveAppManagerService.this.mLock) {
                    try {
                        com.android.server.tv.interactive.TvInteractiveAppManagerService.this.getSessionLocked(com.android.server.tv.interactive.TvInteractiveAppManagerService.this.getSessionStateLocked(iBinder, callingUid, resolveCallingUserId)).sendCurrentChannelLcn(i);
                    } catch (android.os.RemoteException | com.android.server.tv.interactive.TvInteractiveAppManagerService.SessionNotFoundException e) {
                        com.android.server.utils.Slogf.e(com.android.server.tv.interactive.TvInteractiveAppManagerService.TAG, "error in sendCurrentChannelLcn", e);
                    }
                }
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void sendStreamVolume(android.os.IBinder iBinder, float f, int i) {
            int callingUid = android.os.Binder.getCallingUid();
            int resolveCallingUserId = com.android.server.tv.interactive.TvInteractiveAppManagerService.this.resolveCallingUserId(android.os.Binder.getCallingPid(), callingUid, i, "sendStreamVolume");
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                synchronized (com.android.server.tv.interactive.TvInteractiveAppManagerService.this.mLock) {
                    try {
                        com.android.server.tv.interactive.TvInteractiveAppManagerService.this.getSessionLocked(com.android.server.tv.interactive.TvInteractiveAppManagerService.this.getSessionStateLocked(iBinder, callingUid, resolveCallingUserId)).sendStreamVolume(f);
                    } catch (android.os.RemoteException | com.android.server.tv.interactive.TvInteractiveAppManagerService.SessionNotFoundException e) {
                        com.android.server.utils.Slogf.e(com.android.server.tv.interactive.TvInteractiveAppManagerService.TAG, "error in sendStreamVolume", e);
                    }
                }
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void sendTrackInfoList(android.os.IBinder iBinder, java.util.List<android.media.tv.TvTrackInfo> list, int i) {
            int callingUid = android.os.Binder.getCallingUid();
            int resolveCallingUserId = com.android.server.tv.interactive.TvInteractiveAppManagerService.this.resolveCallingUserId(android.os.Binder.getCallingPid(), callingUid, i, "sendTrackInfoList");
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                synchronized (com.android.server.tv.interactive.TvInteractiveAppManagerService.this.mLock) {
                    try {
                        com.android.server.tv.interactive.TvInteractiveAppManagerService.this.getSessionLocked(com.android.server.tv.interactive.TvInteractiveAppManagerService.this.getSessionStateLocked(iBinder, callingUid, resolveCallingUserId)).sendTrackInfoList(list);
                    } catch (android.os.RemoteException | com.android.server.tv.interactive.TvInteractiveAppManagerService.SessionNotFoundException e) {
                        com.android.server.utils.Slogf.e(com.android.server.tv.interactive.TvInteractiveAppManagerService.TAG, "error in sendTrackInfoList", e);
                    }
                }
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void sendSelectedTrackInfo(android.os.IBinder iBinder, java.util.List<android.media.tv.TvTrackInfo> list, int i) {
            int callingUid = android.os.Binder.getCallingUid();
            int resolveCallingUserId = com.android.server.tv.interactive.TvInteractiveAppManagerService.this.resolveCallingUserId(android.os.Binder.getCallingPid(), callingUid, i, "sendSelectedTrackInfo");
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                synchronized (com.android.server.tv.interactive.TvInteractiveAppManagerService.this.mLock) {
                    try {
                        com.android.server.tv.interactive.TvInteractiveAppManagerService.this.getSessionLocked(com.android.server.tv.interactive.TvInteractiveAppManagerService.this.getSessionStateLocked(iBinder, callingUid, resolveCallingUserId)).sendSelectedTrackInfo(list);
                    } catch (android.os.RemoteException | com.android.server.tv.interactive.TvInteractiveAppManagerService.SessionNotFoundException e) {
                        com.android.server.utils.Slogf.e(com.android.server.tv.interactive.TvInteractiveAppManagerService.TAG, "error in sendSelectedTrackInfo", e);
                    }
                }
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void sendCurrentTvInputId(android.os.IBinder iBinder, java.lang.String str, int i) {
            int callingUid = android.os.Binder.getCallingUid();
            int resolveCallingUserId = com.android.server.tv.interactive.TvInteractiveAppManagerService.this.resolveCallingUserId(android.os.Binder.getCallingPid(), callingUid, i, "sendCurrentTvInputId");
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                synchronized (com.android.server.tv.interactive.TvInteractiveAppManagerService.this.mLock) {
                    try {
                        com.android.server.tv.interactive.TvInteractiveAppManagerService.this.getSessionLocked(com.android.server.tv.interactive.TvInteractiveAppManagerService.this.getSessionStateLocked(iBinder, callingUid, resolveCallingUserId)).sendCurrentTvInputId(str);
                    } catch (android.os.RemoteException | com.android.server.tv.interactive.TvInteractiveAppManagerService.SessionNotFoundException e) {
                        com.android.server.utils.Slogf.e(com.android.server.tv.interactive.TvInteractiveAppManagerService.TAG, "error in sendCurrentTvInputId", e);
                    }
                }
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void sendTimeShiftMode(android.os.IBinder iBinder, int i, int i2) {
            int callingUid = android.os.Binder.getCallingUid();
            int resolveCallingUserId = com.android.server.tv.interactive.TvInteractiveAppManagerService.this.resolveCallingUserId(android.os.Binder.getCallingPid(), callingUid, i2, "sendTimeShiftMode");
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                synchronized (com.android.server.tv.interactive.TvInteractiveAppManagerService.this.mLock) {
                    try {
                        com.android.server.tv.interactive.TvInteractiveAppManagerService.this.getSessionLocked(com.android.server.tv.interactive.TvInteractiveAppManagerService.this.getSessionStateLocked(iBinder, callingUid, resolveCallingUserId)).sendTimeShiftMode(i);
                    } catch (android.os.RemoteException | com.android.server.tv.interactive.TvInteractiveAppManagerService.SessionNotFoundException e) {
                        com.android.server.utils.Slogf.e(com.android.server.tv.interactive.TvInteractiveAppManagerService.TAG, "error in sendTimeShiftMode", e);
                    }
                }
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void sendAvailableSpeeds(android.os.IBinder iBinder, float[] fArr, int i) {
            int callingUid = android.os.Binder.getCallingUid();
            int resolveCallingUserId = com.android.server.tv.interactive.TvInteractiveAppManagerService.this.resolveCallingUserId(android.os.Binder.getCallingPid(), callingUid, i, "sendAvailableSpeeds");
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                synchronized (com.android.server.tv.interactive.TvInteractiveAppManagerService.this.mLock) {
                    try {
                        com.android.server.tv.interactive.TvInteractiveAppManagerService.this.getSessionLocked(com.android.server.tv.interactive.TvInteractiveAppManagerService.this.getSessionStateLocked(iBinder, callingUid, resolveCallingUserId)).sendAvailableSpeeds(fArr);
                    } catch (android.os.RemoteException | com.android.server.tv.interactive.TvInteractiveAppManagerService.SessionNotFoundException e) {
                        com.android.server.utils.Slogf.e(com.android.server.tv.interactive.TvInteractiveAppManagerService.TAG, "error in sendAvailableSpeeds", e);
                    }
                }
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void sendTvRecordingInfo(android.os.IBinder iBinder, android.media.tv.TvRecordingInfo tvRecordingInfo, int i) {
            int callingUid = android.os.Binder.getCallingUid();
            int resolveCallingUserId = com.android.server.tv.interactive.TvInteractiveAppManagerService.this.resolveCallingUserId(android.os.Binder.getCallingPid(), callingUid, i, "sendTvRecordingInfo");
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                synchronized (com.android.server.tv.interactive.TvInteractiveAppManagerService.this.mLock) {
                    try {
                        com.android.server.tv.interactive.TvInteractiveAppManagerService.this.getSessionLocked(com.android.server.tv.interactive.TvInteractiveAppManagerService.this.getSessionStateLocked(iBinder, callingUid, resolveCallingUserId)).sendTvRecordingInfo(tvRecordingInfo);
                    } catch (android.os.RemoteException | com.android.server.tv.interactive.TvInteractiveAppManagerService.SessionNotFoundException e) {
                        com.android.server.utils.Slogf.e(com.android.server.tv.interactive.TvInteractiveAppManagerService.TAG, "error in sendTvRecordingInfo", e);
                    }
                }
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void sendTvRecordingInfoList(android.os.IBinder iBinder, java.util.List<android.media.tv.TvRecordingInfo> list, int i) {
            int callingUid = android.os.Binder.getCallingUid();
            int resolveCallingUserId = com.android.server.tv.interactive.TvInteractiveAppManagerService.this.resolveCallingUserId(android.os.Binder.getCallingPid(), callingUid, i, "sendTvRecordingInfoList");
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                synchronized (com.android.server.tv.interactive.TvInteractiveAppManagerService.this.mLock) {
                    try {
                        com.android.server.tv.interactive.TvInteractiveAppManagerService.this.getSessionLocked(com.android.server.tv.interactive.TvInteractiveAppManagerService.this.getSessionStateLocked(iBinder, callingUid, resolveCallingUserId)).sendTvRecordingInfoList(list);
                    } catch (android.os.RemoteException | com.android.server.tv.interactive.TvInteractiveAppManagerService.SessionNotFoundException e) {
                        com.android.server.utils.Slogf.e(com.android.server.tv.interactive.TvInteractiveAppManagerService.TAG, "error in sendTvRecordingInfoList", e);
                    }
                }
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void sendSigningResult(android.os.IBinder iBinder, java.lang.String str, byte[] bArr, int i) {
            int callingUid = android.os.Binder.getCallingUid();
            int resolveCallingUserId = com.android.server.tv.interactive.TvInteractiveAppManagerService.this.resolveCallingUserId(android.os.Binder.getCallingPid(), callingUid, i, "sendSigningResult");
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                synchronized (com.android.server.tv.interactive.TvInteractiveAppManagerService.this.mLock) {
                    try {
                        com.android.server.tv.interactive.TvInteractiveAppManagerService.this.getSessionLocked(com.android.server.tv.interactive.TvInteractiveAppManagerService.this.getSessionStateLocked(iBinder, callingUid, resolveCallingUserId)).sendSigningResult(str, bArr);
                    } catch (android.os.RemoteException | com.android.server.tv.interactive.TvInteractiveAppManagerService.SessionNotFoundException e) {
                        com.android.server.utils.Slogf.e(com.android.server.tv.interactive.TvInteractiveAppManagerService.TAG, "error in sendSigningResult", e);
                    }
                }
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void sendCertificate(android.os.IBinder iBinder, java.lang.String str, int i, android.os.Bundle bundle, int i2) {
            int callingUid = android.os.Binder.getCallingUid();
            int resolveCallingUserId = com.android.server.tv.interactive.TvInteractiveAppManagerService.this.resolveCallingUserId(android.os.Binder.getCallingPid(), callingUid, i2, "sendCertificate");
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                synchronized (com.android.server.tv.interactive.TvInteractiveAppManagerService.this.mLock) {
                    try {
                        com.android.server.tv.interactive.TvInteractiveAppManagerService.this.getSessionLocked(com.android.server.tv.interactive.TvInteractiveAppManagerService.this.getSessionStateLocked(iBinder, callingUid, resolveCallingUserId)).sendCertificate(str, i, bundle);
                    } catch (android.os.RemoteException | com.android.server.tv.interactive.TvInteractiveAppManagerService.SessionNotFoundException e) {
                        com.android.server.utils.Slogf.e(com.android.server.tv.interactive.TvInteractiveAppManagerService.TAG, "error in sendCertificate", e);
                    }
                }
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void notifyError(android.os.IBinder iBinder, java.lang.String str, android.os.Bundle bundle, int i) {
            int callingUid = android.os.Binder.getCallingUid();
            int resolveCallingUserId = com.android.server.tv.interactive.TvInteractiveAppManagerService.this.resolveCallingUserId(android.os.Binder.getCallingPid(), callingUid, i, "notifyError");
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                synchronized (com.android.server.tv.interactive.TvInteractiveAppManagerService.this.mLock) {
                    try {
                        com.android.server.tv.interactive.TvInteractiveAppManagerService.this.getSessionLocked(com.android.server.tv.interactive.TvInteractiveAppManagerService.this.getSessionStateLocked(iBinder, callingUid, resolveCallingUserId)).notifyError(str, bundle);
                    } catch (android.os.RemoteException | com.android.server.tv.interactive.TvInteractiveAppManagerService.SessionNotFoundException e) {
                        com.android.server.utils.Slogf.e(com.android.server.tv.interactive.TvInteractiveAppManagerService.TAG, "error in notifyError", e);
                    }
                }
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void notifyTimeShiftPlaybackParams(android.os.IBinder iBinder, android.media.PlaybackParams playbackParams, int i) {
            int callingUid = android.os.Binder.getCallingUid();
            int resolveCallingUserId = com.android.server.tv.interactive.TvInteractiveAppManagerService.this.resolveCallingUserId(android.os.Binder.getCallingPid(), callingUid, i, "notifyTimeShiftPlaybackParams");
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                synchronized (com.android.server.tv.interactive.TvInteractiveAppManagerService.this.mLock) {
                    try {
                        com.android.server.tv.interactive.TvInteractiveAppManagerService.this.getSessionLocked(com.android.server.tv.interactive.TvInteractiveAppManagerService.this.getSessionStateLocked(iBinder, callingUid, resolveCallingUserId)).notifyTimeShiftPlaybackParams(playbackParams);
                    } catch (android.os.RemoteException | com.android.server.tv.interactive.TvInteractiveAppManagerService.SessionNotFoundException e) {
                        com.android.server.utils.Slogf.e(com.android.server.tv.interactive.TvInteractiveAppManagerService.TAG, "error in notifyTimeShiftPlaybackParams", e);
                    }
                }
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void notifyTimeShiftStatusChanged(android.os.IBinder iBinder, java.lang.String str, int i, int i2) {
            int callingUid = android.os.Binder.getCallingUid();
            int resolveCallingUserId = com.android.server.tv.interactive.TvInteractiveAppManagerService.this.resolveCallingUserId(android.os.Binder.getCallingPid(), callingUid, i2, "notifyTimeShiftStatusChanged");
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                synchronized (com.android.server.tv.interactive.TvInteractiveAppManagerService.this.mLock) {
                    try {
                        com.android.server.tv.interactive.TvInteractiveAppManagerService.this.getSessionLocked(com.android.server.tv.interactive.TvInteractiveAppManagerService.this.getSessionStateLocked(iBinder, callingUid, resolveCallingUserId)).notifyTimeShiftStatusChanged(str, i);
                    } catch (android.os.RemoteException | com.android.server.tv.interactive.TvInteractiveAppManagerService.SessionNotFoundException e) {
                        com.android.server.utils.Slogf.e(com.android.server.tv.interactive.TvInteractiveAppManagerService.TAG, "error in notifyTimeShiftStatusChanged", e);
                    }
                }
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void notifyTimeShiftStartPositionChanged(android.os.IBinder iBinder, java.lang.String str, long j, int i) {
            int callingUid = android.os.Binder.getCallingUid();
            int resolveCallingUserId = com.android.server.tv.interactive.TvInteractiveAppManagerService.this.resolveCallingUserId(android.os.Binder.getCallingPid(), callingUid, i, "notifyTimeShiftStartPositionChanged");
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                synchronized (com.android.server.tv.interactive.TvInteractiveAppManagerService.this.mLock) {
                    try {
                        com.android.server.tv.interactive.TvInteractiveAppManagerService.this.getSessionLocked(com.android.server.tv.interactive.TvInteractiveAppManagerService.this.getSessionStateLocked(iBinder, callingUid, resolveCallingUserId)).notifyTimeShiftStartPositionChanged(str, j);
                    } catch (android.os.RemoteException | com.android.server.tv.interactive.TvInteractiveAppManagerService.SessionNotFoundException e) {
                        com.android.server.utils.Slogf.e(com.android.server.tv.interactive.TvInteractiveAppManagerService.TAG, "error in notifyTimeShiftStartPositionChanged", e);
                    }
                }
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void notifyTimeShiftCurrentPositionChanged(android.os.IBinder iBinder, java.lang.String str, long j, int i) {
            int callingUid = android.os.Binder.getCallingUid();
            int resolveCallingUserId = com.android.server.tv.interactive.TvInteractiveAppManagerService.this.resolveCallingUserId(android.os.Binder.getCallingPid(), callingUid, i, "notifyTimeShiftCurrentPositionChanged");
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                synchronized (com.android.server.tv.interactive.TvInteractiveAppManagerService.this.mLock) {
                    try {
                        com.android.server.tv.interactive.TvInteractiveAppManagerService.this.getSessionLocked(com.android.server.tv.interactive.TvInteractiveAppManagerService.this.getSessionStateLocked(iBinder, callingUid, resolveCallingUserId)).notifyTimeShiftCurrentPositionChanged(str, j);
                    } catch (android.os.RemoteException | com.android.server.tv.interactive.TvInteractiveAppManagerService.SessionNotFoundException e) {
                        com.android.server.utils.Slogf.e(com.android.server.tv.interactive.TvInteractiveAppManagerService.TAG, "error in notifyTimeShiftCurrentPositionChanged", e);
                    }
                }
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void notifyRecordingConnectionFailed(android.os.IBinder iBinder, java.lang.String str, java.lang.String str2, int i) {
            int callingUid = android.os.Binder.getCallingUid();
            int resolveCallingUserId = com.android.server.tv.interactive.TvInteractiveAppManagerService.this.resolveCallingUserId(android.os.Binder.getCallingPid(), callingUid, i, "notifyRecordingConnectionFailed");
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                synchronized (com.android.server.tv.interactive.TvInteractiveAppManagerService.this.mLock) {
                    try {
                        com.android.server.tv.interactive.TvInteractiveAppManagerService.this.getSessionLocked(com.android.server.tv.interactive.TvInteractiveAppManagerService.this.getSessionStateLocked(iBinder, callingUid, resolveCallingUserId)).notifyRecordingConnectionFailed(str, str2);
                    } catch (android.os.RemoteException | com.android.server.tv.interactive.TvInteractiveAppManagerService.SessionNotFoundException e) {
                        com.android.server.utils.Slogf.e(com.android.server.tv.interactive.TvInteractiveAppManagerService.TAG, "error in notifyRecordingConnectionFailed", e);
                    }
                }
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void notifyRecordingDisconnected(android.os.IBinder iBinder, java.lang.String str, java.lang.String str2, int i) {
            int callingUid = android.os.Binder.getCallingUid();
            int resolveCallingUserId = com.android.server.tv.interactive.TvInteractiveAppManagerService.this.resolveCallingUserId(android.os.Binder.getCallingPid(), callingUid, i, "notifyRecordingDisconnected");
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                synchronized (com.android.server.tv.interactive.TvInteractiveAppManagerService.this.mLock) {
                    try {
                        com.android.server.tv.interactive.TvInteractiveAppManagerService.this.getSessionLocked(com.android.server.tv.interactive.TvInteractiveAppManagerService.this.getSessionStateLocked(iBinder, callingUid, resolveCallingUserId)).notifyRecordingDisconnected(str, str2);
                    } catch (android.os.RemoteException | com.android.server.tv.interactive.TvInteractiveAppManagerService.SessionNotFoundException e) {
                        com.android.server.utils.Slogf.e(com.android.server.tv.interactive.TvInteractiveAppManagerService.TAG, "error in notifyRecordingDisconnected", e);
                    }
                }
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void notifyRecordingTuned(android.os.IBinder iBinder, java.lang.String str, android.net.Uri uri, int i) {
            int callingUid = android.os.Binder.getCallingUid();
            int resolveCallingUserId = com.android.server.tv.interactive.TvInteractiveAppManagerService.this.resolveCallingUserId(android.os.Binder.getCallingPid(), callingUid, i, "notifyRecordingTuned");
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                synchronized (com.android.server.tv.interactive.TvInteractiveAppManagerService.this.mLock) {
                    try {
                        com.android.server.tv.interactive.TvInteractiveAppManagerService.this.getSessionLocked(com.android.server.tv.interactive.TvInteractiveAppManagerService.this.getSessionStateLocked(iBinder, callingUid, resolveCallingUserId)).notifyRecordingTuned(str, uri);
                    } catch (android.os.RemoteException | com.android.server.tv.interactive.TvInteractiveAppManagerService.SessionNotFoundException e) {
                        com.android.server.utils.Slogf.e(com.android.server.tv.interactive.TvInteractiveAppManagerService.TAG, "error in notifyRecordingTuned", e);
                    }
                }
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void notifyRecordingError(android.os.IBinder iBinder, java.lang.String str, int i, int i2) {
            int callingUid = android.os.Binder.getCallingUid();
            int resolveCallingUserId = com.android.server.tv.interactive.TvInteractiveAppManagerService.this.resolveCallingUserId(android.os.Binder.getCallingPid(), callingUid, i2, "notifyRecordingError");
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                synchronized (com.android.server.tv.interactive.TvInteractiveAppManagerService.this.mLock) {
                    try {
                        com.android.server.tv.interactive.TvInteractiveAppManagerService.this.getSessionLocked(com.android.server.tv.interactive.TvInteractiveAppManagerService.this.getSessionStateLocked(iBinder, callingUid, resolveCallingUserId)).notifyRecordingError(str, i);
                    } catch (android.os.RemoteException | com.android.server.tv.interactive.TvInteractiveAppManagerService.SessionNotFoundException e) {
                        com.android.server.utils.Slogf.e(com.android.server.tv.interactive.TvInteractiveAppManagerService.TAG, "error in notifyRecordingError", e);
                    }
                }
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void notifyRecordingScheduled(android.os.IBinder iBinder, java.lang.String str, java.lang.String str2, int i) {
            int callingUid = android.os.Binder.getCallingUid();
            int resolveCallingUserId = com.android.server.tv.interactive.TvInteractiveAppManagerService.this.resolveCallingUserId(android.os.Binder.getCallingPid(), callingUid, i, "notifyRecordingScheduled");
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                synchronized (com.android.server.tv.interactive.TvInteractiveAppManagerService.this.mLock) {
                    try {
                        com.android.server.tv.interactive.TvInteractiveAppManagerService.this.getSessionLocked(com.android.server.tv.interactive.TvInteractiveAppManagerService.this.getSessionStateLocked(iBinder, callingUid, resolveCallingUserId)).notifyRecordingScheduled(str, str2);
                    } catch (android.os.RemoteException | com.android.server.tv.interactive.TvInteractiveAppManagerService.SessionNotFoundException e) {
                        com.android.server.utils.Slogf.e(com.android.server.tv.interactive.TvInteractiveAppManagerService.TAG, "error in notifyRecordingScheduled", e);
                    }
                }
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void setSurface(android.os.IBinder iBinder, android.view.Surface surface, int i) {
            int callingUid = android.os.Binder.getCallingUid();
            int resolveCallingUserId = com.android.server.tv.interactive.TvInteractiveAppManagerService.this.resolveCallingUserId(android.os.Binder.getCallingPid(), callingUid, i, "setSurface");
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                synchronized (com.android.server.tv.interactive.TvInteractiveAppManagerService.this.mLock) {
                    try {
                        com.android.server.tv.interactive.TvInteractiveAppManagerService.this.getSessionLocked(com.android.server.tv.interactive.TvInteractiveAppManagerService.this.getSessionStateLocked(iBinder, callingUid, resolveCallingUserId)).setSurface(surface);
                    } catch (android.os.RemoteException | com.android.server.tv.interactive.TvInteractiveAppManagerService.SessionNotFoundException e) {
                        com.android.server.utils.Slogf.e(com.android.server.tv.interactive.TvInteractiveAppManagerService.TAG, "error in setSurface", e);
                    }
                }
            } finally {
                if (surface != null) {
                    surface.release();
                }
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void dispatchSurfaceChanged(android.os.IBinder iBinder, int i, int i2, int i3, int i4) {
            int callingUid = android.os.Binder.getCallingUid();
            int resolveCallingUserId = com.android.server.tv.interactive.TvInteractiveAppManagerService.this.resolveCallingUserId(android.os.Binder.getCallingPid(), callingUid, i4, "dispatchSurfaceChanged");
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                synchronized (com.android.server.tv.interactive.TvInteractiveAppManagerService.this.mLock) {
                    try {
                        com.android.server.tv.interactive.TvInteractiveAppManagerService.this.getSessionLocked(com.android.server.tv.interactive.TvInteractiveAppManagerService.this.getSessionStateLocked(iBinder, callingUid, resolveCallingUserId)).dispatchSurfaceChanged(i, i2, i3);
                    } catch (android.os.RemoteException | com.android.server.tv.interactive.TvInteractiveAppManagerService.SessionNotFoundException e) {
                        com.android.server.utils.Slogf.e(com.android.server.tv.interactive.TvInteractiveAppManagerService.TAG, "error in dispatchSurfaceChanged", e);
                    }
                }
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void notifyBroadcastInfoResponse(android.os.IBinder iBinder, android.media.tv.BroadcastInfoResponse broadcastInfoResponse, int i) {
            int callingUid = android.os.Binder.getCallingUid();
            int resolveCallingUserId = com.android.server.tv.interactive.TvInteractiveAppManagerService.this.resolveCallingUserId(android.os.Binder.getCallingPid(), callingUid, i, "notifyBroadcastInfoResponse");
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                synchronized (com.android.server.tv.interactive.TvInteractiveAppManagerService.this.mLock) {
                    try {
                        com.android.server.tv.interactive.TvInteractiveAppManagerService.this.getSessionLocked(com.android.server.tv.interactive.TvInteractiveAppManagerService.this.getSessionStateLocked(iBinder, callingUid, resolveCallingUserId)).notifyBroadcastInfoResponse(broadcastInfoResponse);
                    } catch (android.os.RemoteException | com.android.server.tv.interactive.TvInteractiveAppManagerService.SessionNotFoundException e) {
                        com.android.server.utils.Slogf.e(com.android.server.tv.interactive.TvInteractiveAppManagerService.TAG, "error in notifyBroadcastInfoResponse", e);
                    }
                }
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void notifyAdResponse(android.os.IBinder iBinder, android.media.tv.AdResponse adResponse, int i) {
            int callingUid = android.os.Binder.getCallingUid();
            int resolveCallingUserId = com.android.server.tv.interactive.TvInteractiveAppManagerService.this.resolveCallingUserId(android.os.Binder.getCallingPid(), callingUid, i, "notifyAdResponse");
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                synchronized (com.android.server.tv.interactive.TvInteractiveAppManagerService.this.mLock) {
                    try {
                        com.android.server.tv.interactive.TvInteractiveAppManagerService.this.getSessionLocked(com.android.server.tv.interactive.TvInteractiveAppManagerService.this.getSessionStateLocked(iBinder, callingUid, resolveCallingUserId)).notifyAdResponse(adResponse);
                    } catch (android.os.RemoteException | com.android.server.tv.interactive.TvInteractiveAppManagerService.SessionNotFoundException e) {
                        com.android.server.utils.Slogf.e(com.android.server.tv.interactive.TvInteractiveAppManagerService.TAG, "error in notifyAdResponse", e);
                    }
                }
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void notifyAdBufferConsumed(android.os.IBinder iBinder, android.media.tv.AdBuffer adBuffer, int i) {
            android.os.SharedMemory sharedMemory;
            int callingUid = android.os.Binder.getCallingUid();
            int resolveCallingUserId = com.android.server.tv.interactive.TvInteractiveAppManagerService.this.resolveCallingUserId(android.os.Binder.getCallingPid(), callingUid, i, "notifyAdBufferConsumed");
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                synchronized (com.android.server.tv.interactive.TvInteractiveAppManagerService.this.mLock) {
                    try {
                        try {
                            com.android.server.tv.interactive.TvInteractiveAppManagerService.this.getSessionLocked(com.android.server.tv.interactive.TvInteractiveAppManagerService.this.getSessionStateLocked(iBinder, callingUid, resolveCallingUserId)).notifyAdBufferConsumed(adBuffer);
                        } catch (java.lang.Throwable th) {
                            if (adBuffer != null) {
                                adBuffer.getSharedMemory().close();
                            }
                            throw th;
                        }
                    } catch (android.os.RemoteException | com.android.server.tv.interactive.TvInteractiveAppManagerService.SessionNotFoundException e) {
                        com.android.server.utils.Slogf.e(com.android.server.tv.interactive.TvInteractiveAppManagerService.TAG, "error in notifyAdBufferConsumed", e);
                        if (adBuffer != null) {
                            sharedMemory = adBuffer.getSharedMemory();
                        }
                    }
                    if (adBuffer != null) {
                        sharedMemory = adBuffer.getSharedMemory();
                        sharedMemory.close();
                    }
                }
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void registerCallback(android.media.tv.interactive.ITvInteractiveAppManagerCallback iTvInteractiveAppManagerCallback, int i) {
            int resolveCallingUserId = com.android.server.tv.interactive.TvInteractiveAppManagerService.this.resolveCallingUserId(android.os.Binder.getCallingPid(), android.os.Binder.getCallingUid(), i, "registerCallback");
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                synchronized (com.android.server.tv.interactive.TvInteractiveAppManagerService.this.mLock) {
                    try {
                        if (!com.android.server.tv.interactive.TvInteractiveAppManagerService.this.getOrCreateUserStateLocked(resolveCallingUserId).mCallbacks.register(iTvInteractiveAppManagerCallback)) {
                            android.util.Slog.e(com.android.server.tv.interactive.TvInteractiveAppManagerService.TAG, "client process has already died");
                        }
                    } finally {
                    }
                }
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void unregisterCallback(android.media.tv.interactive.ITvInteractiveAppManagerCallback iTvInteractiveAppManagerCallback, int i) {
            int resolveCallingUserId = com.android.server.tv.interactive.TvInteractiveAppManagerService.this.resolveCallingUserId(android.os.Binder.getCallingPid(), android.os.Binder.getCallingUid(), i, "unregisterCallback");
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                synchronized (com.android.server.tv.interactive.TvInteractiveAppManagerService.this.mLock) {
                    com.android.server.tv.interactive.TvInteractiveAppManagerService.this.getOrCreateUserStateLocked(resolveCallingUserId).mCallbacks.unregister(iTvInteractiveAppManagerCallback);
                }
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void createMediaView(android.os.IBinder iBinder, android.os.IBinder iBinder2, android.graphics.Rect rect, int i) {
            int callingUid = android.os.Binder.getCallingUid();
            int resolveCallingUserId = com.android.server.tv.interactive.TvInteractiveAppManagerService.this.resolveCallingUserId(android.os.Binder.getCallingPid(), callingUid, i, "createMediaView");
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                synchronized (com.android.server.tv.interactive.TvInteractiveAppManagerService.this.mLock) {
                    try {
                        com.android.server.tv.interactive.TvInteractiveAppManagerService.this.getSessionLocked(iBinder, callingUid, resolveCallingUserId).createMediaView(iBinder2, rect);
                    } catch (android.os.RemoteException | com.android.server.tv.interactive.TvInteractiveAppManagerService.SessionNotFoundException e) {
                        android.util.Slog.e(com.android.server.tv.interactive.TvInteractiveAppManagerService.TAG, "error in createMediaView", e);
                    }
                }
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void relayoutMediaView(android.os.IBinder iBinder, android.graphics.Rect rect, int i) {
            int callingUid = android.os.Binder.getCallingUid();
            int resolveCallingUserId = com.android.server.tv.interactive.TvInteractiveAppManagerService.this.resolveCallingUserId(android.os.Binder.getCallingPid(), callingUid, i, "relayoutMediaView");
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                synchronized (com.android.server.tv.interactive.TvInteractiveAppManagerService.this.mLock) {
                    try {
                        com.android.server.tv.interactive.TvInteractiveAppManagerService.this.getSessionLocked(iBinder, callingUid, resolveCallingUserId).relayoutMediaView(rect);
                    } catch (android.os.RemoteException | com.android.server.tv.interactive.TvInteractiveAppManagerService.SessionNotFoundException e) {
                        android.util.Slog.e(com.android.server.tv.interactive.TvInteractiveAppManagerService.TAG, "error in relayoutMediaView", e);
                    }
                }
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void removeMediaView(android.os.IBinder iBinder, int i) {
            int callingUid = android.os.Binder.getCallingUid();
            int resolveCallingUserId = com.android.server.tv.interactive.TvInteractiveAppManagerService.this.resolveCallingUserId(android.os.Binder.getCallingPid(), callingUid, i, "removeMediaView");
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                synchronized (com.android.server.tv.interactive.TvInteractiveAppManagerService.this.mLock) {
                    try {
                        com.android.server.tv.interactive.TvInteractiveAppManagerService.this.getSessionLocked(iBinder, callingUid, resolveCallingUserId).removeMediaView();
                    } catch (android.os.RemoteException | com.android.server.tv.interactive.TvInteractiveAppManagerService.SessionNotFoundException e) {
                        android.util.Slog.e(com.android.server.tv.interactive.TvInteractiveAppManagerService.TAG, "error in removeMediaView", e);
                    }
                }
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public void sendSessionTokenToClientLocked(android.media.tv.interactive.ITvInteractiveAppClient iTvInteractiveAppClient, java.lang.String str, android.os.IBinder iBinder, android.view.InputChannel inputChannel, int i) {
        try {
            iTvInteractiveAppClient.onSessionCreated(str, iBinder, inputChannel, i);
        } catch (android.os.RemoteException e) {
            com.android.server.utils.Slogf.e(TAG, "error in onSessionCreated", e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public void sendAdSessionTokenToClientLocked(android.media.tv.ad.ITvAdClient iTvAdClient, java.lang.String str, android.os.IBinder iBinder, android.view.InputChannel inputChannel, int i) {
        try {
            iTvAdClient.onSessionCreated(str, iBinder, inputChannel, i);
        } catch (android.os.RemoteException e) {
            com.android.server.utils.Slogf.e(TAG, "error in onSessionCreated", e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public boolean createSessionInternalLocked(android.media.tv.interactive.ITvInteractiveAppService iTvInteractiveAppService, android.os.IBinder iBinder, int i) {
        boolean z;
        com.android.server.tv.interactive.TvInteractiveAppManagerService.SessionState sessionState = (com.android.server.tv.interactive.TvInteractiveAppManagerService.SessionState) getOrCreateUserStateLocked(i).mSessionStateMap.get(iBinder);
        android.view.InputChannel[] openInputChannelPair = android.view.InputChannel.openInputChannelPair(iBinder.toString());
        try {
            iTvInteractiveAppService.createSession(openInputChannelPair[1], new com.android.server.tv.interactive.TvInteractiveAppManagerService.SessionCallback(sessionState, openInputChannelPair), sessionState.mIAppServiceId, sessionState.mType);
            z = true;
        } catch (android.os.RemoteException e) {
            com.android.server.utils.Slogf.e(TAG, "error in createSession", e);
            sendSessionTokenToClientLocked(sessionState.mClient, sessionState.mIAppServiceId, null, null, sessionState.mSeq);
            z = false;
        }
        openInputChannelPair[1].dispose();
        return z;
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public boolean createAdSessionInternalLocked(android.media.tv.ad.ITvAdService iTvAdService, android.os.IBinder iBinder, int i) {
        boolean z;
        com.android.server.tv.interactive.TvInteractiveAppManagerService.AdSessionState adSessionState = (com.android.server.tv.interactive.TvInteractiveAppManagerService.AdSessionState) getOrCreateUserStateLocked(i).mAdSessionStateMap.get(iBinder);
        android.view.InputChannel[] openInputChannelPair = android.view.InputChannel.openInputChannelPair(iBinder.toString());
        try {
            iTvAdService.createSession(openInputChannelPair[1], new com.android.server.tv.interactive.TvInteractiveAppManagerService.AdSessionCallback(adSessionState, openInputChannelPair), adSessionState.mAdServiceId, adSessionState.mType);
            z = true;
        } catch (android.os.RemoteException e) {
            com.android.server.utils.Slogf.e(TAG, "error in createSession", e);
            sendAdSessionTokenToClientLocked(adSessionState.mClient, adSessionState.mAdServiceId, null, null, adSessionState.mSeq);
            z = false;
        }
        openInputChannelPair[1].dispose();
        return z;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code restructure failed: missing block: B:10:0x0029, code lost:
    
        r6.mSession = null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:11:0x003d, code lost:
    
        removeAdSessionStateLocked(r5, r7);
     */
    /* JADX WARN: Code restructure failed: missing block: B:12:0x0040, code lost:
    
        return r6;
     */
    /* JADX WARN: Code restructure failed: missing block: B:16:0x003a, code lost:
    
        if (r6 == null) goto L22;
     */
    /* JADX WARN: Code restructure failed: missing block: B:9:0x0027, code lost:
    
        if (r6 != null) goto L13;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:20:0x0043  */
    /* JADX WARN: Type inference failed for: r6v1 */
    @com.android.internal.annotations.GuardedBy({"mLock"})
    @android.annotation.Nullable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public com.android.server.tv.interactive.TvInteractiveAppManagerService.AdSessionState releaseAdSessionLocked(android.os.IBinder iBinder, int i, int i2) {
        com.android.server.tv.interactive.TvInteractiveAppManagerService.AdSessionState adSessionState;
        try {
            try {
                adSessionState = getAdSessionStateLocked(iBinder, i, i2);
            } catch (android.os.RemoteException | com.android.server.tv.interactive.TvInteractiveAppManagerService.SessionNotFoundException e) {
                e = e;
                adSessionState = null;
            } catch (java.lang.Throwable th) {
                th = th;
                i = 0;
                if (i != 0) {
                }
                throw th;
            }
            try {
                getOrCreateUserStateLocked(i2);
                if (adSessionState.mSession != null) {
                    adSessionState.mSession.asBinder().unlinkToDeath(adSessionState, 0);
                    adSessionState.mSession.release();
                }
            } catch (android.os.RemoteException | com.android.server.tv.interactive.TvInteractiveAppManagerService.SessionNotFoundException e2) {
                e = e2;
                com.android.server.utils.Slogf.e(TAG, "error in releaseSession", e);
            }
        } catch (java.lang.Throwable th2) {
            th = th2;
            if (i != 0) {
                i.mSession = null;
            }
            throw th;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code restructure failed: missing block: B:10:0x0029, code lost:
    
        r6.mSession = null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:11:0x003d, code lost:
    
        removeSessionStateLocked(r5, r7);
     */
    /* JADX WARN: Code restructure failed: missing block: B:12:0x0040, code lost:
    
        return r6;
     */
    /* JADX WARN: Code restructure failed: missing block: B:16:0x003a, code lost:
    
        if (r6 == null) goto L22;
     */
    /* JADX WARN: Code restructure failed: missing block: B:9:0x0027, code lost:
    
        if (r6 != null) goto L13;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:20:0x0043  */
    /* JADX WARN: Type inference failed for: r6v1 */
    @com.android.internal.annotations.GuardedBy({"mLock"})
    @android.annotation.Nullable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public com.android.server.tv.interactive.TvInteractiveAppManagerService.SessionState releaseSessionLocked(android.os.IBinder iBinder, int i, int i2) {
        com.android.server.tv.interactive.TvInteractiveAppManagerService.SessionState sessionState;
        try {
            try {
                sessionState = getSessionStateLocked(iBinder, i, i2);
            } catch (android.os.RemoteException | com.android.server.tv.interactive.TvInteractiveAppManagerService.SessionNotFoundException e) {
                e = e;
                sessionState = null;
            } catch (java.lang.Throwable th) {
                th = th;
                i = 0;
                if (i != 0) {
                }
                throw th;
            }
            try {
                getOrCreateUserStateLocked(i2);
                if (sessionState.mSession != null) {
                    sessionState.mSession.asBinder().unlinkToDeath(sessionState, 0);
                    sessionState.mSession.release();
                }
            } catch (android.os.RemoteException | com.android.server.tv.interactive.TvInteractiveAppManagerService.SessionNotFoundException e2) {
                e = e2;
                com.android.server.utils.Slogf.e(TAG, "error in releaseSession", e);
            }
        } catch (java.lang.Throwable th2) {
            th = th2;
            if (i != 0) {
                i.mSession = null;
            }
            throw th;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public void removeSessionStateLocked(android.os.IBinder iBinder, int i) {
        com.android.server.tv.interactive.TvInteractiveAppManagerService.UserState orCreateUserStateLocked = getOrCreateUserStateLocked(i);
        com.android.server.tv.interactive.TvInteractiveAppManagerService.SessionState sessionState = (com.android.server.tv.interactive.TvInteractiveAppManagerService.SessionState) orCreateUserStateLocked.mSessionStateMap.remove(iBinder);
        if (sessionState == null) {
            com.android.server.utils.Slogf.e(TAG, "sessionState null, no more remove session action!");
            return;
        }
        com.android.server.tv.interactive.TvInteractiveAppManagerService.ClientState clientState = (com.android.server.tv.interactive.TvInteractiveAppManagerService.ClientState) orCreateUserStateLocked.mClientStateMap.get(sessionState.mClient.asBinder());
        if (clientState != null) {
            clientState.mSessionTokens.remove(iBinder);
            if (clientState.isEmpty()) {
                orCreateUserStateLocked.mClientStateMap.remove(sessionState.mClient.asBinder());
                sessionState.mClient.asBinder().unlinkToDeath(clientState, 0);
            }
        }
        com.android.server.tv.interactive.TvInteractiveAppManagerService.ServiceState serviceState = (com.android.server.tv.interactive.TvInteractiveAppManagerService.ServiceState) orCreateUserStateLocked.mServiceStateMap.get(sessionState.mComponent);
        if (serviceState != null) {
            serviceState.mSessionTokens.remove(iBinder);
        }
        updateServiceConnectionLocked(sessionState.mComponent, i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public void removeAdSessionStateLocked(android.os.IBinder iBinder, int i) {
        com.android.server.tv.interactive.TvInteractiveAppManagerService.UserState orCreateUserStateLocked = getOrCreateUserStateLocked(i);
        com.android.server.tv.interactive.TvInteractiveAppManagerService.AdSessionState adSessionState = (com.android.server.tv.interactive.TvInteractiveAppManagerService.AdSessionState) orCreateUserStateLocked.mAdSessionStateMap.remove(iBinder);
        if (adSessionState == null) {
            com.android.server.utils.Slogf.e(TAG, "sessionState null, no more remove session action!");
            return;
        }
        com.android.server.tv.interactive.TvInteractiveAppManagerService.ClientState clientState = (com.android.server.tv.interactive.TvInteractiveAppManagerService.ClientState) orCreateUserStateLocked.mClientStateMap.get(adSessionState.mClient.asBinder());
        if (clientState != null) {
            clientState.mSessionTokens.remove(iBinder);
            if (clientState.isEmpty()) {
                orCreateUserStateLocked.mClientStateMap.remove(adSessionState.mClient.asBinder());
                adSessionState.mClient.asBinder().unlinkToDeath(clientState, 0);
            }
        }
        com.android.server.tv.interactive.TvInteractiveAppManagerService.AdServiceState adServiceState = (com.android.server.tv.interactive.TvInteractiveAppManagerService.AdServiceState) orCreateUserStateLocked.mAdServiceStateMap.get(adSessionState.mComponent);
        if (adServiceState != null) {
            adServiceState.mSessionTokens.remove(iBinder);
        }
        updateAdServiceConnectionLocked(adSessionState.mComponent, i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public void abortPendingCreateSessionRequestsLocked(com.android.server.tv.interactive.TvInteractiveAppManagerService.ServiceState serviceState, java.lang.String str, int i) {
        com.android.server.tv.interactive.TvInteractiveAppManagerService.UserState orCreateUserStateLocked = getOrCreateUserStateLocked(i);
        java.util.ArrayList<com.android.server.tv.interactive.TvInteractiveAppManagerService.SessionState> arrayList = new java.util.ArrayList();
        java.util.Iterator it = serviceState.mSessionTokens.iterator();
        while (it.hasNext()) {
            com.android.server.tv.interactive.TvInteractiveAppManagerService.SessionState sessionState = (com.android.server.tv.interactive.TvInteractiveAppManagerService.SessionState) orCreateUserStateLocked.mSessionStateMap.get((android.os.IBinder) it.next());
            if (sessionState.mSession == null && (str == null || sessionState.mIAppServiceId.equals(str))) {
                arrayList.add(sessionState);
            }
        }
        for (com.android.server.tv.interactive.TvInteractiveAppManagerService.SessionState sessionState2 : arrayList) {
            removeSessionStateLocked(sessionState2.mSessionToken, sessionState2.mUserId);
            sendSessionTokenToClientLocked(sessionState2.mClient, sessionState2.mIAppServiceId, null, null, sessionState2.mSeq);
        }
        updateServiceConnectionLocked(serviceState.mComponent, i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public void abortPendingCreateAdSessionRequestsLocked(com.android.server.tv.interactive.TvInteractiveAppManagerService.AdServiceState adServiceState, java.lang.String str, int i) {
        com.android.server.tv.interactive.TvInteractiveAppManagerService.UserState orCreateUserStateLocked = getOrCreateUserStateLocked(i);
        java.util.ArrayList<com.android.server.tv.interactive.TvInteractiveAppManagerService.AdSessionState> arrayList = new java.util.ArrayList();
        java.util.Iterator it = adServiceState.mSessionTokens.iterator();
        while (it.hasNext()) {
            com.android.server.tv.interactive.TvInteractiveAppManagerService.AdSessionState adSessionState = (com.android.server.tv.interactive.TvInteractiveAppManagerService.AdSessionState) orCreateUserStateLocked.mAdSessionStateMap.get((android.os.IBinder) it.next());
            if (adSessionState.mSession == null && (adServiceState == null || adSessionState.mAdServiceId.equals(str))) {
                arrayList.add(adSessionState);
            }
        }
        for (com.android.server.tv.interactive.TvInteractiveAppManagerService.AdSessionState adSessionState2 : arrayList) {
            removeAdSessionStateLocked(adSessionState2.mSessionToken, adSessionState2.mUserId);
            sendAdSessionTokenToClientLocked(adSessionState2.mClient, adSessionState2.mAdServiceId, null, null, adSessionState2.mSeq);
        }
        updateAdServiceConnectionLocked(adServiceState.mComponent, i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public void updateServiceConnectionLocked(android.content.ComponentName componentName, int i) {
        com.android.server.tv.interactive.TvInteractiveAppManagerService.UserState orCreateUserStateLocked = getOrCreateUserStateLocked(i);
        com.android.server.tv.interactive.TvInteractiveAppManagerService.ServiceState serviceState = (com.android.server.tv.interactive.TvInteractiveAppManagerService.ServiceState) orCreateUserStateLocked.mServiceStateMap.get(componentName);
        if (serviceState == null) {
            return;
        }
        if (serviceState.mReconnecting) {
            if (!serviceState.mSessionTokens.isEmpty()) {
                return;
            } else {
                serviceState.mReconnecting = false;
            }
        }
        boolean z = (serviceState.mSessionTokens.isEmpty() && serviceState.mPendingAppLinkInfo.isEmpty() && serviceState.mPendingAppLinkCommand.isEmpty()) ? false : true;
        if (serviceState.mService == null && z) {
            if (serviceState.mBound) {
                return;
            }
            serviceState.mBound = this.mContext.bindServiceAsUser(new android.content.Intent("android.media.tv.interactive.TvInteractiveAppService").setComponent(componentName), serviceState.mConnection, 33554433, new android.os.UserHandle(i));
        } else if (serviceState.mService != null && !z) {
            this.mContext.unbindService(serviceState.mConnection);
            orCreateUserStateLocked.mServiceStateMap.remove(componentName);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public void updateAdServiceConnectionLocked(android.content.ComponentName componentName, int i) {
        com.android.server.tv.interactive.TvInteractiveAppManagerService.UserState orCreateUserStateLocked = getOrCreateUserStateLocked(i);
        com.android.server.tv.interactive.TvInteractiveAppManagerService.AdServiceState adServiceState = (com.android.server.tv.interactive.TvInteractiveAppManagerService.AdServiceState) orCreateUserStateLocked.mAdServiceStateMap.get(componentName);
        if (adServiceState == null) {
            return;
        }
        if (adServiceState.mReconnecting) {
            if (!adServiceState.mSessionTokens.isEmpty()) {
                return;
            } else {
                adServiceState.mReconnecting = false;
            }
        }
        boolean z = (adServiceState.mSessionTokens.isEmpty() && adServiceState.mPendingAppLinkCommand.isEmpty()) ? false : true;
        if (adServiceState.mService == null && z) {
            if (adServiceState.mBound) {
                return;
            }
            adServiceState.mBound = this.mContext.bindServiceAsUser(new android.content.Intent("android.media.tv.ad.TvAdService").setComponent(componentName), adServiceState.mConnection, 33554433, new android.os.UserHandle(i));
        } else if (adServiceState.mService != null && !z) {
            this.mContext.unbindService(adServiceState.mConnection);
            orCreateUserStateLocked.mAdServiceStateMap.remove(componentName);
        }
    }

    private static final class UserState {
        private final android.os.RemoteCallbackList<android.media.tv.ad.ITvAdManagerCallback> mAdCallbacks;
        private java.util.Map<java.lang.String, com.android.server.tv.interactive.TvInteractiveAppManagerService.TvAdServiceState> mAdMap;
        private java.util.Map<java.lang.String, com.android.server.tv.interactive.TvInteractiveAppManagerService.TvAdServiceState> mAdServiceMap;
        private final java.util.Map<android.content.ComponentName, com.android.server.tv.interactive.TvInteractiveAppManagerService.AdServiceState> mAdServiceStateMap;
        private final java.util.Map<android.os.IBinder, com.android.server.tv.interactive.TvInteractiveAppManagerService.AdSessionState> mAdSessionStateMap;
        private final java.util.List<android.media.tv.interactive.AppLinkInfo> mAppLinkInfoList;
        private final android.os.RemoteCallbackList<android.media.tv.interactive.ITvInteractiveAppManagerCallback> mCallbacks;
        private final java.util.Map<android.os.IBinder, com.android.server.tv.interactive.TvInteractiveAppManagerService.ClientState> mClientStateMap;
        private java.util.Map<java.lang.String, com.android.server.tv.interactive.TvInteractiveAppManagerService.TvInteractiveAppState> mIAppMap;
        private final java.util.Set<java.lang.String> mPackageSet;
        private final java.util.Map<android.content.ComponentName, com.android.server.tv.interactive.TvInteractiveAppManagerService.ServiceState> mServiceStateMap;
        private final java.util.Map<android.os.IBinder, com.android.server.tv.interactive.TvInteractiveAppManagerService.SessionState> mSessionStateMap;
        private final int mUserId;

        private UserState(int i) {
            this.mAdMap = new java.util.HashMap();
            this.mAdServiceStateMap = new java.util.HashMap();
            this.mAdSessionStateMap = new java.util.HashMap();
            this.mIAppMap = new java.util.HashMap();
            this.mAdServiceMap = new java.util.HashMap();
            this.mClientStateMap = new java.util.HashMap();
            this.mServiceStateMap = new java.util.HashMap();
            this.mSessionStateMap = new java.util.HashMap();
            this.mPackageSet = new java.util.HashSet();
            this.mAppLinkInfoList = new java.util.ArrayList();
            this.mAdCallbacks = new android.os.RemoteCallbackList<>();
            this.mCallbacks = new android.os.RemoteCallbackList<>();
            this.mUserId = i;
        }
    }

    private final class SessionState implements android.os.IBinder.DeathRecipient {
        private final int mCallingPid;
        private final int mCallingUid;
        private final android.media.tv.interactive.ITvInteractiveAppClient mClient;
        private final android.content.ComponentName mComponent;
        private final java.lang.String mIAppServiceId;
        private final int mSeq;
        private android.media.tv.interactive.ITvInteractiveAppSession mSession;
        private final android.os.IBinder mSessionToken;
        private final int mType;
        private final int mUserId;

        private SessionState(android.os.IBinder iBinder, java.lang.String str, int i, android.content.ComponentName componentName, android.media.tv.interactive.ITvInteractiveAppClient iTvInteractiveAppClient, int i2, int i3, int i4, int i5) {
            this.mSessionToken = iBinder;
            this.mIAppServiceId = str;
            this.mComponent = componentName;
            this.mType = i;
            this.mClient = iTvInteractiveAppClient;
            this.mSeq = i2;
            this.mCallingUid = i3;
            this.mCallingPid = i4;
            this.mUserId = i5;
        }

        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
            synchronized (com.android.server.tv.interactive.TvInteractiveAppManagerService.this.mLock) {
                this.mSession = null;
                com.android.server.tv.interactive.TvInteractiveAppManagerService.this.clearSessionAndNotifyClientLocked(this);
            }
        }
    }

    private final class AdSessionState implements android.os.IBinder.DeathRecipient {
        private final java.lang.String mAdServiceId;
        private final int mCallingPid;
        private final int mCallingUid;
        private final android.media.tv.ad.ITvAdClient mClient;
        private final android.content.ComponentName mComponent;
        private final int mSeq;
        private android.media.tv.ad.ITvAdSession mSession;
        private final android.os.IBinder mSessionToken;
        private final java.lang.String mType;
        private final int mUserId;

        private AdSessionState(android.os.IBinder iBinder, java.lang.String str, java.lang.String str2, android.content.ComponentName componentName, android.media.tv.ad.ITvAdClient iTvAdClient, int i, int i2, int i3, int i4) {
            this.mSessionToken = iBinder;
            this.mAdServiceId = str;
            this.mType = str2;
            this.mComponent = componentName;
            this.mClient = iTvAdClient;
            this.mSeq = i;
            this.mCallingUid = i2;
            this.mCallingPid = i3;
            this.mUserId = i4;
        }

        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
            synchronized (com.android.server.tv.interactive.TvInteractiveAppManagerService.this.mLock) {
                this.mSession = null;
                com.android.server.tv.interactive.TvInteractiveAppManagerService.this.clearAdSessionAndNotifyClientLocked(this);
            }
        }
    }

    private final class ClientState implements android.os.IBinder.DeathRecipient {
        private android.os.IBinder mClientToken;
        private final java.util.List<android.os.IBinder> mSessionTokens = new java.util.ArrayList();
        private final int mUserId;

        ClientState(android.os.IBinder iBinder, int i) {
            this.mClientToken = iBinder;
            this.mUserId = i;
        }

        public boolean isEmpty() {
            return this.mSessionTokens.isEmpty();
        }

        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
            synchronized (com.android.server.tv.interactive.TvInteractiveAppManagerService.this.mLock) {
                try {
                    com.android.server.tv.interactive.TvInteractiveAppManagerService.ClientState clientState = (com.android.server.tv.interactive.TvInteractiveAppManagerService.ClientState) com.android.server.tv.interactive.TvInteractiveAppManagerService.this.getOrCreateUserStateLocked(this.mUserId).mClientStateMap.get(this.mClientToken);
                    if (clientState != null) {
                        while (clientState.mSessionTokens.size() > 0) {
                            android.os.IBinder iBinder = clientState.mSessionTokens.get(0);
                            com.android.server.tv.interactive.TvInteractiveAppManagerService.this.releaseSessionLocked(iBinder, 1000, this.mUserId);
                            if (clientState.mSessionTokens.contains(iBinder)) {
                                com.android.server.utils.Slogf.d(com.android.server.tv.interactive.TvInteractiveAppManagerService.TAG, "remove sessionToken " + iBinder + " for " + this.mClientToken);
                                clientState.mSessionTokens.remove(iBinder);
                            }
                        }
                    }
                    this.mClientToken = null;
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
    }

    private final class ServiceState {
        private boolean mBound;
        private com.android.server.tv.interactive.TvInteractiveAppManagerService.ServiceCallback mCallback;
        private final android.content.ComponentName mComponent;
        private final android.content.ServiceConnection mConnection;
        private final java.lang.String mIAppServiceId;
        private final java.util.List<android.os.Bundle> mPendingAppLinkCommand;
        private final java.util.List<android.util.Pair<android.media.tv.interactive.AppLinkInfo, java.lang.Boolean>> mPendingAppLinkInfo;
        private boolean mReconnecting;
        private android.media.tv.interactive.ITvInteractiveAppService mService;
        private final java.util.List<android.os.IBinder> mSessionTokens;

        private ServiceState(android.content.ComponentName componentName, java.lang.String str, int i) {
            this.mSessionTokens = new java.util.ArrayList();
            this.mPendingAppLinkInfo = new java.util.ArrayList();
            this.mPendingAppLinkCommand = new java.util.ArrayList();
            this.mComponent = componentName;
            this.mConnection = new com.android.server.tv.interactive.TvInteractiveAppManagerService.InteractiveAppServiceConnection(componentName, i);
            this.mIAppServiceId = str;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void addPendingAppLink(android.media.tv.interactive.AppLinkInfo appLinkInfo, boolean z) {
            this.mPendingAppLinkInfo.add(android.util.Pair.create(appLinkInfo, java.lang.Boolean.valueOf(z)));
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void addPendingAppLinkCommand(android.os.Bundle bundle) {
            this.mPendingAppLinkCommand.add(bundle);
        }
    }

    private final class AdServiceState {
        private final java.lang.String mAdServiceId;
        private boolean mBound;
        private com.android.server.tv.interactive.TvInteractiveAppManagerService.AdServiceCallback mCallback;
        private final android.content.ComponentName mComponent;
        private final android.content.ServiceConnection mConnection;
        private final java.util.List<android.os.Bundle> mPendingAppLinkCommand;
        private boolean mReconnecting;
        private android.media.tv.ad.ITvAdService mService;
        private final java.util.List<android.os.IBinder> mSessionTokens;

        private AdServiceState(android.content.ComponentName componentName, java.lang.String str, int i) {
            this.mSessionTokens = new java.util.ArrayList();
            this.mPendingAppLinkCommand = new java.util.ArrayList();
            this.mComponent = componentName;
            this.mConnection = new com.android.server.tv.interactive.TvInteractiveAppManagerService.AdServiceConnection(componentName, i);
            this.mAdServiceId = str;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void addPendingAppLinkCommand(android.os.Bundle bundle) {
            this.mPendingAppLinkCommand.add(bundle);
        }
    }

    private final class InteractiveAppServiceConnection implements android.content.ServiceConnection {
        private final android.content.ComponentName mComponent;
        private final int mUserId;

        private InteractiveAppServiceConnection(android.content.ComponentName componentName, int i) {
            this.mComponent = componentName;
            this.mUserId = i;
        }

        @Override // android.content.ServiceConnection
        public void onServiceConnected(android.content.ComponentName componentName, android.os.IBinder iBinder) {
            long clearCallingIdentity;
            synchronized (com.android.server.tv.interactive.TvInteractiveAppManagerService.this.mLock) {
                try {
                    com.android.server.tv.interactive.TvInteractiveAppManagerService.UserState userStateLocked = com.android.server.tv.interactive.TvInteractiveAppManagerService.this.getUserStateLocked(this.mUserId);
                    if (userStateLocked == null) {
                        com.android.server.tv.interactive.TvInteractiveAppManagerService.this.mContext.unbindService(this);
                        return;
                    }
                    com.android.server.tv.interactive.TvInteractiveAppManagerService.ServiceState serviceState = (com.android.server.tv.interactive.TvInteractiveAppManagerService.ServiceState) userStateLocked.mServiceStateMap.get(this.mComponent);
                    serviceState.mService = android.media.tv.interactive.ITvInteractiveAppService.Stub.asInterface(iBinder);
                    if (serviceState.mCallback == null) {
                        serviceState.mCallback = com.android.server.tv.interactive.TvInteractiveAppManagerService.this.new ServiceCallback(this.mComponent, this.mUserId);
                        try {
                            serviceState.mService.registerCallback(serviceState.mCallback);
                        } catch (android.os.RemoteException e) {
                            android.util.Slog.e(com.android.server.tv.interactive.TvInteractiveAppManagerService.TAG, "error in registerCallback", e);
                        }
                    }
                    if (!serviceState.mPendingAppLinkInfo.isEmpty()) {
                        java.util.Iterator it = serviceState.mPendingAppLinkInfo.iterator();
                        while (it.hasNext()) {
                            android.util.Pair pair = (android.util.Pair) it.next();
                            clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                            try {
                                try {
                                    if (((java.lang.Boolean) pair.second).booleanValue()) {
                                        serviceState.mService.registerAppLinkInfo((android.media.tv.interactive.AppLinkInfo) pair.first);
                                    } else {
                                        serviceState.mService.unregisterAppLinkInfo((android.media.tv.interactive.AppLinkInfo) pair.first);
                                    }
                                    it.remove();
                                } catch (android.os.RemoteException e2) {
                                    com.android.server.utils.Slogf.e(com.android.server.tv.interactive.TvInteractiveAppManagerService.TAG, "error in notifyAppLinkInfo(" + pair + ") when onServiceConnected", e2);
                                }
                            } finally {
                            }
                        }
                    }
                    if (!serviceState.mPendingAppLinkCommand.isEmpty()) {
                        java.util.Iterator it2 = serviceState.mPendingAppLinkCommand.iterator();
                        while (it2.hasNext()) {
                            android.os.Bundle bundle = (android.os.Bundle) it2.next();
                            clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                            try {
                                try {
                                    serviceState.mService.sendAppLinkCommand(bundle);
                                    it2.remove();
                                } catch (android.os.RemoteException e3) {
                                    com.android.server.utils.Slogf.e(com.android.server.tv.interactive.TvInteractiveAppManagerService.TAG, "error in sendAppLinkCommand(" + bundle + ") when onServiceConnected", e3);
                                }
                            } finally {
                                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                            }
                        }
                    }
                    java.util.ArrayList arrayList = new java.util.ArrayList();
                    for (android.os.IBinder iBinder2 : serviceState.mSessionTokens) {
                        if (!com.android.server.tv.interactive.TvInteractiveAppManagerService.this.createSessionInternalLocked(serviceState.mService, iBinder2, this.mUserId)) {
                            arrayList.add(iBinder2);
                        }
                    }
                    java.util.Iterator it3 = arrayList.iterator();
                    while (it3.hasNext()) {
                        com.android.server.tv.interactive.TvInteractiveAppManagerService.this.removeSessionStateLocked((android.os.IBinder) it3.next(), this.mUserId);
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
            synchronized (com.android.server.tv.interactive.TvInteractiveAppManagerService.this.mLock) {
                try {
                    com.android.server.tv.interactive.TvInteractiveAppManagerService.ServiceState serviceState = (com.android.server.tv.interactive.TvInteractiveAppManagerService.ServiceState) com.android.server.tv.interactive.TvInteractiveAppManagerService.this.getOrCreateUserStateLocked(this.mUserId).mServiceStateMap.get(this.mComponent);
                    if (serviceState != null) {
                        serviceState.mReconnecting = true;
                        serviceState.mBound = false;
                        serviceState.mService = null;
                        serviceState.mCallback = null;
                        com.android.server.tv.interactive.TvInteractiveAppManagerService.this.abortPendingCreateSessionRequestsLocked(serviceState, null, this.mUserId);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
    }

    private final class AdServiceConnection implements android.content.ServiceConnection {
        private final android.content.ComponentName mComponent;
        private final int mUserId;

        private AdServiceConnection(android.content.ComponentName componentName, int i) {
            this.mComponent = componentName;
            this.mUserId = i;
        }

        @Override // android.content.ServiceConnection
        public void onServiceConnected(android.content.ComponentName componentName, android.os.IBinder iBinder) {
            synchronized (com.android.server.tv.interactive.TvInteractiveAppManagerService.this.mLock) {
                try {
                    com.android.server.tv.interactive.TvInteractiveAppManagerService.UserState userStateLocked = com.android.server.tv.interactive.TvInteractiveAppManagerService.this.getUserStateLocked(this.mUserId);
                    if (userStateLocked == null) {
                        com.android.server.tv.interactive.TvInteractiveAppManagerService.this.mContext.unbindService(this);
                        return;
                    }
                    com.android.server.tv.interactive.TvInteractiveAppManagerService.AdServiceState adServiceState = (com.android.server.tv.interactive.TvInteractiveAppManagerService.AdServiceState) userStateLocked.mAdServiceStateMap.get(this.mComponent);
                    adServiceState.mService = android.media.tv.ad.ITvAdService.Stub.asInterface(iBinder);
                    if (adServiceState.mCallback == null) {
                        adServiceState.mCallback = com.android.server.tv.interactive.TvInteractiveAppManagerService.this.new AdServiceCallback(this.mComponent, this.mUserId);
                        try {
                            adServiceState.mService.registerCallback(adServiceState.mCallback);
                        } catch (android.os.RemoteException e) {
                            android.util.Slog.e(com.android.server.tv.interactive.TvInteractiveAppManagerService.TAG, "error in registerCallback", e);
                        }
                    }
                    if (!adServiceState.mPendingAppLinkCommand.isEmpty()) {
                        java.util.Iterator it = adServiceState.mPendingAppLinkCommand.iterator();
                        while (it.hasNext()) {
                            android.os.Bundle bundle = (android.os.Bundle) it.next();
                            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                            try {
                                try {
                                    adServiceState.mService.sendAppLinkCommand(bundle);
                                    it.remove();
                                } catch (android.os.RemoteException e2) {
                                    com.android.server.utils.Slogf.e(com.android.server.tv.interactive.TvInteractiveAppManagerService.TAG, "error in sendAppLinkCommand(" + bundle + ") when onServiceConnected", e2);
                                }
                            } finally {
                                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                            }
                        }
                    }
                    java.util.ArrayList arrayList = new java.util.ArrayList();
                    for (android.os.IBinder iBinder2 : adServiceState.mSessionTokens) {
                        if (!com.android.server.tv.interactive.TvInteractiveAppManagerService.this.createAdSessionInternalLocked(adServiceState.mService, iBinder2, this.mUserId)) {
                            arrayList.add(iBinder2);
                        }
                    }
                    java.util.Iterator it2 = arrayList.iterator();
                    while (it2.hasNext()) {
                        com.android.server.tv.interactive.TvInteractiveAppManagerService.this.removeAdSessionStateLocked((android.os.IBinder) it2.next(), this.mUserId);
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
            synchronized (com.android.server.tv.interactive.TvInteractiveAppManagerService.this.mLock) {
                try {
                    com.android.server.tv.interactive.TvInteractiveAppManagerService.AdServiceState adServiceState = (com.android.server.tv.interactive.TvInteractiveAppManagerService.AdServiceState) com.android.server.tv.interactive.TvInteractiveAppManagerService.this.getOrCreateUserStateLocked(this.mUserId).mAdServiceStateMap.get(this.mComponent);
                    if (adServiceState != null) {
                        adServiceState.mReconnecting = true;
                        adServiceState.mBound = false;
                        adServiceState.mService = null;
                        adServiceState.mCallback = null;
                        com.android.server.tv.interactive.TvInteractiveAppManagerService.this.abortPendingCreateAdSessionRequestsLocked(adServiceState, null, this.mUserId);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
    }

    private final class ServiceCallback extends android.media.tv.interactive.ITvInteractiveAppServiceCallback.Stub {
        private final android.content.ComponentName mComponent;
        private final int mUserId;

        ServiceCallback(android.content.ComponentName componentName, int i) {
            this.mComponent = componentName;
            this.mUserId = i;
        }

        public void onStateChanged(int i, int i2, int i3) {
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                synchronized (com.android.server.tv.interactive.TvInteractiveAppManagerService.this.mLock) {
                    java.lang.String str = com.android.server.tv.interactive.TvInteractiveAppManagerService.this.getServiceStateLocked(this.mComponent, this.mUserId).mIAppServiceId;
                    com.android.server.tv.interactive.TvInteractiveAppManagerService.this.notifyStateChangedLocked(com.android.server.tv.interactive.TvInteractiveAppManagerService.this.getUserStateLocked(this.mUserId), str, i, i2, i3);
                }
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }

    private final class AdServiceCallback extends android.media.tv.ad.ITvAdServiceCallback.Stub {
        private final android.content.ComponentName mComponent;
        private final int mUserId;

        AdServiceCallback(android.content.ComponentName componentName, int i) {
            this.mComponent = componentName;
            this.mUserId = i;
        }
    }

    private final class SessionCallback extends android.media.tv.interactive.ITvInteractiveAppSessionCallback.Stub {
        private final android.view.InputChannel[] mInputChannels;
        private final com.android.server.tv.interactive.TvInteractiveAppManagerService.SessionState mSessionState;

        SessionCallback(com.android.server.tv.interactive.TvInteractiveAppManagerService.SessionState sessionState, android.view.InputChannel[] inputChannelArr) {
            this.mSessionState = sessionState;
            this.mInputChannels = inputChannelArr;
        }

        public void onSessionCreated(android.media.tv.interactive.ITvInteractiveAppSession iTvInteractiveAppSession) {
            synchronized (com.android.server.tv.interactive.TvInteractiveAppManagerService.this.mLock) {
                try {
                    this.mSessionState.mSession = iTvInteractiveAppSession;
                    if (iTvInteractiveAppSession != null && addSessionTokenToClientStateLocked(iTvInteractiveAppSession)) {
                        com.android.server.tv.interactive.TvInteractiveAppManagerService.this.sendSessionTokenToClientLocked(this.mSessionState.mClient, this.mSessionState.mIAppServiceId, this.mSessionState.mSessionToken, this.mInputChannels[0], this.mSessionState.mSeq);
                    } else {
                        com.android.server.tv.interactive.TvInteractiveAppManagerService.this.removeSessionStateLocked(this.mSessionState.mSessionToken, this.mSessionState.mUserId);
                        com.android.server.tv.interactive.TvInteractiveAppManagerService.this.sendSessionTokenToClientLocked(this.mSessionState.mClient, this.mSessionState.mIAppServiceId, null, null, this.mSessionState.mSeq);
                    }
                    this.mInputChannels[0].dispose();
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        public void onLayoutSurface(int i, int i2, int i3, int i4) {
            synchronized (com.android.server.tv.interactive.TvInteractiveAppManagerService.this.mLock) {
                if (this.mSessionState.mSession == null || this.mSessionState.mClient == null) {
                    return;
                }
                try {
                    this.mSessionState.mClient.onLayoutSurface(i, i2, i3, i4, this.mSessionState.mSeq);
                } catch (android.os.RemoteException e) {
                    com.android.server.utils.Slogf.e(com.android.server.tv.interactive.TvInteractiveAppManagerService.TAG, "error in onLayoutSurface", e);
                }
            }
        }

        public void onBroadcastInfoRequest(android.media.tv.BroadcastInfoRequest broadcastInfoRequest) {
            synchronized (com.android.server.tv.interactive.TvInteractiveAppManagerService.this.mLock) {
                if (this.mSessionState.mSession == null || this.mSessionState.mClient == null) {
                    return;
                }
                try {
                    this.mSessionState.mClient.onBroadcastInfoRequest(broadcastInfoRequest, this.mSessionState.mSeq);
                } catch (android.os.RemoteException e) {
                    com.android.server.utils.Slogf.e(com.android.server.tv.interactive.TvInteractiveAppManagerService.TAG, "error in onBroadcastInfoRequest", e);
                }
            }
        }

        public void onRemoveBroadcastInfo(int i) {
            synchronized (com.android.server.tv.interactive.TvInteractiveAppManagerService.this.mLock) {
                if (this.mSessionState.mSession == null || this.mSessionState.mClient == null) {
                    return;
                }
                try {
                    this.mSessionState.mClient.onRemoveBroadcastInfo(i, this.mSessionState.mSeq);
                } catch (android.os.RemoteException e) {
                    com.android.server.utils.Slogf.e(com.android.server.tv.interactive.TvInteractiveAppManagerService.TAG, "error in onRemoveBroadcastInfo", e);
                }
            }
        }

        public void onCommandRequest(java.lang.String str, android.os.Bundle bundle) {
            synchronized (com.android.server.tv.interactive.TvInteractiveAppManagerService.this.mLock) {
                if (this.mSessionState.mSession == null || this.mSessionState.mClient == null) {
                    return;
                }
                try {
                    this.mSessionState.mClient.onCommandRequest(str, bundle, this.mSessionState.mSeq);
                } catch (android.os.RemoteException e) {
                    com.android.server.utils.Slogf.e(com.android.server.tv.interactive.TvInteractiveAppManagerService.TAG, "error in onCommandRequest", e);
                }
            }
        }

        public void onTimeShiftCommandRequest(java.lang.String str, android.os.Bundle bundle) {
            synchronized (com.android.server.tv.interactive.TvInteractiveAppManagerService.this.mLock) {
                if (this.mSessionState.mSession == null || this.mSessionState.mClient == null) {
                    return;
                }
                try {
                    this.mSessionState.mClient.onTimeShiftCommandRequest(str, bundle, this.mSessionState.mSeq);
                } catch (android.os.RemoteException e) {
                    com.android.server.utils.Slogf.e(com.android.server.tv.interactive.TvInteractiveAppManagerService.TAG, "error in onTimeShiftCommandRequest", e);
                }
            }
        }

        public void onSetVideoBounds(android.graphics.Rect rect) {
            synchronized (com.android.server.tv.interactive.TvInteractiveAppManagerService.this.mLock) {
                if (this.mSessionState.mSession == null || this.mSessionState.mClient == null) {
                    return;
                }
                try {
                    this.mSessionState.mClient.onSetVideoBounds(rect, this.mSessionState.mSeq);
                } catch (android.os.RemoteException e) {
                    com.android.server.utils.Slogf.e(com.android.server.tv.interactive.TvInteractiveAppManagerService.TAG, "error in onSetVideoBounds", e);
                }
            }
        }

        public void onRequestCurrentVideoBounds() {
            synchronized (com.android.server.tv.interactive.TvInteractiveAppManagerService.this.mLock) {
                if (this.mSessionState.mSession == null || this.mSessionState.mClient == null) {
                    return;
                }
                try {
                    this.mSessionState.mClient.onRequestCurrentVideoBounds(this.mSessionState.mSeq);
                } catch (android.os.RemoteException e) {
                    com.android.server.utils.Slogf.e(com.android.server.tv.interactive.TvInteractiveAppManagerService.TAG, "error in onRequestCurrentVideoBounds", e);
                }
            }
        }

        public void onRequestCurrentChannelUri() {
            synchronized (com.android.server.tv.interactive.TvInteractiveAppManagerService.this.mLock) {
                if (this.mSessionState.mSession == null || this.mSessionState.mClient == null) {
                    return;
                }
                try {
                    this.mSessionState.mClient.onRequestCurrentChannelUri(this.mSessionState.mSeq);
                } catch (android.os.RemoteException e) {
                    com.android.server.utils.Slogf.e(com.android.server.tv.interactive.TvInteractiveAppManagerService.TAG, "error in onRequestCurrentChannelUri", e);
                }
            }
        }

        public void onRequestCurrentChannelLcn() {
            synchronized (com.android.server.tv.interactive.TvInteractiveAppManagerService.this.mLock) {
                if (this.mSessionState.mSession == null || this.mSessionState.mClient == null) {
                    return;
                }
                try {
                    this.mSessionState.mClient.onRequestCurrentChannelLcn(this.mSessionState.mSeq);
                } catch (android.os.RemoteException e) {
                    com.android.server.utils.Slogf.e(com.android.server.tv.interactive.TvInteractiveAppManagerService.TAG, "error in onRequestCurrentChannelLcn", e);
                }
            }
        }

        public void onRequestStreamVolume() {
            synchronized (com.android.server.tv.interactive.TvInteractiveAppManagerService.this.mLock) {
                if (this.mSessionState.mSession == null || this.mSessionState.mClient == null) {
                    return;
                }
                try {
                    this.mSessionState.mClient.onRequestStreamVolume(this.mSessionState.mSeq);
                } catch (android.os.RemoteException e) {
                    com.android.server.utils.Slogf.e(com.android.server.tv.interactive.TvInteractiveAppManagerService.TAG, "error in onRequestStreamVolume", e);
                }
            }
        }

        public void onRequestTrackInfoList() {
            synchronized (com.android.server.tv.interactive.TvInteractiveAppManagerService.this.mLock) {
                if (this.mSessionState.mSession == null || this.mSessionState.mClient == null) {
                    return;
                }
                try {
                    this.mSessionState.mClient.onRequestTrackInfoList(this.mSessionState.mSeq);
                } catch (android.os.RemoteException e) {
                    com.android.server.utils.Slogf.e(com.android.server.tv.interactive.TvInteractiveAppManagerService.TAG, "error in onRequestTrackInfoList", e);
                }
            }
        }

        public void onRequestSelectedTrackInfo() {
            synchronized (com.android.server.tv.interactive.TvInteractiveAppManagerService.this.mLock) {
                if (this.mSessionState.mSession == null || this.mSessionState.mClient == null) {
                    return;
                }
                try {
                    this.mSessionState.mClient.onRequestSelectedTrackInfo(this.mSessionState.mSeq);
                } catch (android.os.RemoteException e) {
                    com.android.server.utils.Slogf.e(com.android.server.tv.interactive.TvInteractiveAppManagerService.TAG, "error in onRequestSelectedTrackInfo", e);
                }
            }
        }

        public void onRequestCurrentTvInputId() {
            synchronized (com.android.server.tv.interactive.TvInteractiveAppManagerService.this.mLock) {
                if (this.mSessionState.mSession == null || this.mSessionState.mClient == null) {
                    return;
                }
                try {
                    this.mSessionState.mClient.onRequestCurrentTvInputId(this.mSessionState.mSeq);
                } catch (android.os.RemoteException e) {
                    com.android.server.utils.Slogf.e(com.android.server.tv.interactive.TvInteractiveAppManagerService.TAG, "error in onRequestCurrentTvInputId", e);
                }
            }
        }

        public void onRequestTimeShiftMode() {
            synchronized (com.android.server.tv.interactive.TvInteractiveAppManagerService.this.mLock) {
                if (this.mSessionState.mSession == null || this.mSessionState.mClient == null) {
                    return;
                }
                try {
                    this.mSessionState.mClient.onRequestTimeShiftMode(this.mSessionState.mSeq);
                } catch (android.os.RemoteException e) {
                    com.android.server.utils.Slogf.e(com.android.server.tv.interactive.TvInteractiveAppManagerService.TAG, "error in onRequestTimeShiftMode", e);
                }
            }
        }

        public void onRequestAvailableSpeeds() {
            synchronized (com.android.server.tv.interactive.TvInteractiveAppManagerService.this.mLock) {
                if (this.mSessionState.mSession == null || this.mSessionState.mClient == null) {
                    return;
                }
                try {
                    this.mSessionState.mClient.onRequestAvailableSpeeds(this.mSessionState.mSeq);
                } catch (android.os.RemoteException e) {
                    com.android.server.utils.Slogf.e(com.android.server.tv.interactive.TvInteractiveAppManagerService.TAG, "error in onRequestAvailableSpeeds", e);
                }
            }
        }

        public void onRequestStartRecording(java.lang.String str, android.net.Uri uri) {
            synchronized (com.android.server.tv.interactive.TvInteractiveAppManagerService.this.mLock) {
                if (this.mSessionState.mSession == null || this.mSessionState.mClient == null) {
                    return;
                }
                try {
                    this.mSessionState.mClient.onRequestStartRecording(str, uri, this.mSessionState.mSeq);
                } catch (android.os.RemoteException e) {
                    com.android.server.utils.Slogf.e(com.android.server.tv.interactive.TvInteractiveAppManagerService.TAG, "error in onRequestStartRecording", e);
                }
            }
        }

        public void onRequestStopRecording(java.lang.String str) {
            synchronized (com.android.server.tv.interactive.TvInteractiveAppManagerService.this.mLock) {
                if (this.mSessionState.mSession == null || this.mSessionState.mClient == null) {
                    return;
                }
                try {
                    this.mSessionState.mClient.onRequestStopRecording(str, this.mSessionState.mSeq);
                } catch (android.os.RemoteException e) {
                    com.android.server.utils.Slogf.e(com.android.server.tv.interactive.TvInteractiveAppManagerService.TAG, "error in onRequestStopRecording", e);
                }
            }
        }

        public void onRequestScheduleRecording(java.lang.String str, java.lang.String str2, android.net.Uri uri, android.net.Uri uri2, android.os.Bundle bundle) {
            synchronized (com.android.server.tv.interactive.TvInteractiveAppManagerService.this.mLock) {
                if (this.mSessionState.mSession == null || this.mSessionState.mClient == null) {
                    return;
                }
                try {
                    this.mSessionState.mClient.onRequestScheduleRecording(str, str2, uri, uri2, bundle, this.mSessionState.mSeq);
                } catch (android.os.RemoteException e) {
                    com.android.server.utils.Slogf.e(com.android.server.tv.interactive.TvInteractiveAppManagerService.TAG, "error in onRequestScheduleRecording", e);
                }
            }
        }

        public void onRequestScheduleRecording2(java.lang.String str, java.lang.String str2, android.net.Uri uri, long j, long j2, int i, android.os.Bundle bundle) {
            synchronized (com.android.server.tv.interactive.TvInteractiveAppManagerService.this.mLock) {
                if (this.mSessionState.mSession == null || this.mSessionState.mClient == null) {
                    return;
                }
                try {
                    this.mSessionState.mClient.onRequestScheduleRecording2(str, str2, uri, j, j2, i, bundle, this.mSessionState.mSeq);
                } catch (android.os.RemoteException e) {
                    com.android.server.utils.Slogf.e(com.android.server.tv.interactive.TvInteractiveAppManagerService.TAG, "error in onRequestScheduleRecording2", e);
                }
            }
        }

        public void onSetTvRecordingInfo(java.lang.String str, android.media.tv.TvRecordingInfo tvRecordingInfo) {
            synchronized (com.android.server.tv.interactive.TvInteractiveAppManagerService.this.mLock) {
                if (this.mSessionState.mSession == null || this.mSessionState.mClient == null) {
                    return;
                }
                try {
                    this.mSessionState.mClient.onSetTvRecordingInfo(str, tvRecordingInfo, this.mSessionState.mSeq);
                } catch (android.os.RemoteException e) {
                    com.android.server.utils.Slogf.e(com.android.server.tv.interactive.TvInteractiveAppManagerService.TAG, "error in onSetTvRecordingInfo", e);
                }
            }
        }

        public void onRequestTvRecordingInfo(java.lang.String str) {
            synchronized (com.android.server.tv.interactive.TvInteractiveAppManagerService.this.mLock) {
                if (this.mSessionState.mSession == null || this.mSessionState.mClient == null) {
                    return;
                }
                try {
                    this.mSessionState.mClient.onRequestTvRecordingInfo(str, this.mSessionState.mSeq);
                } catch (android.os.RemoteException e) {
                    com.android.server.utils.Slogf.e(com.android.server.tv.interactive.TvInteractiveAppManagerService.TAG, "error in onRequestTvRecordingInfo", e);
                }
            }
        }

        public void onRequestTvRecordingInfoList(int i) {
            synchronized (com.android.server.tv.interactive.TvInteractiveAppManagerService.this.mLock) {
                if (this.mSessionState.mSession == null || this.mSessionState.mClient == null) {
                    return;
                }
                try {
                    this.mSessionState.mClient.onRequestTvRecordingInfoList(i, this.mSessionState.mSeq);
                } catch (android.os.RemoteException e) {
                    com.android.server.utils.Slogf.e(com.android.server.tv.interactive.TvInteractiveAppManagerService.TAG, "error in onRequestTvRecordingInfoList", e);
                }
            }
        }

        public void onRequestSigning(java.lang.String str, java.lang.String str2, java.lang.String str3, byte[] bArr) {
            synchronized (com.android.server.tv.interactive.TvInteractiveAppManagerService.this.mLock) {
                if (this.mSessionState.mSession == null || this.mSessionState.mClient == null) {
                    return;
                }
                try {
                    this.mSessionState.mClient.onRequestSigning(str, str2, str3, bArr, this.mSessionState.mSeq);
                } catch (android.os.RemoteException e) {
                    com.android.server.utils.Slogf.e(com.android.server.tv.interactive.TvInteractiveAppManagerService.TAG, "error in onRequestSigning", e);
                }
            }
        }

        public void onRequestSigning2(java.lang.String str, java.lang.String str2, java.lang.String str3, int i, byte[] bArr) {
            synchronized (com.android.server.tv.interactive.TvInteractiveAppManagerService.this.mLock) {
                if (this.mSessionState.mSession == null || this.mSessionState.mClient == null) {
                    return;
                }
                try {
                    this.mSessionState.mClient.onRequestSigning2(str, str2, str3, i, bArr, this.mSessionState.mSeq);
                } catch (android.os.RemoteException e) {
                    com.android.server.utils.Slogf.e(com.android.server.tv.interactive.TvInteractiveAppManagerService.TAG, "error in onRequestSigning", e);
                }
            }
        }

        public void onRequestCertificate(java.lang.String str, int i) {
            synchronized (com.android.server.tv.interactive.TvInteractiveAppManagerService.this.mLock) {
                if (this.mSessionState.mSession == null || this.mSessionState.mClient == null) {
                    return;
                }
                try {
                    this.mSessionState.mClient.onRequestCertificate(str, i, this.mSessionState.mSeq);
                } catch (android.os.RemoteException e) {
                    com.android.server.utils.Slogf.e(com.android.server.tv.interactive.TvInteractiveAppManagerService.TAG, "error in onRequestCertificate", e);
                }
            }
        }

        public void onAdRequest(android.media.tv.AdRequest adRequest) {
            synchronized (com.android.server.tv.interactive.TvInteractiveAppManagerService.this.mLock) {
                if (this.mSessionState.mSession == null || this.mSessionState.mClient == null) {
                    return;
                }
                try {
                    this.mSessionState.mClient.onAdRequest(adRequest, this.mSessionState.mSeq);
                } catch (android.os.RemoteException e) {
                    com.android.server.utils.Slogf.e(com.android.server.tv.interactive.TvInteractiveAppManagerService.TAG, "error in onAdRequest", e);
                }
            }
        }

        public void onSessionStateChanged(int i, int i2) {
            synchronized (com.android.server.tv.interactive.TvInteractiveAppManagerService.this.mLock) {
                if (this.mSessionState.mSession == null || this.mSessionState.mClient == null) {
                    return;
                }
                try {
                    this.mSessionState.mClient.onSessionStateChanged(i, i2, this.mSessionState.mSeq);
                } catch (android.os.RemoteException e) {
                    com.android.server.utils.Slogf.e(com.android.server.tv.interactive.TvInteractiveAppManagerService.TAG, "error in onSessionStateChanged", e);
                }
            }
        }

        public void onBiInteractiveAppCreated(android.net.Uri uri, java.lang.String str) {
            synchronized (com.android.server.tv.interactive.TvInteractiveAppManagerService.this.mLock) {
                if (this.mSessionState.mSession == null || this.mSessionState.mClient == null) {
                    return;
                }
                try {
                    this.mSessionState.mClient.onBiInteractiveAppCreated(uri, str, this.mSessionState.mSeq);
                } catch (android.os.RemoteException e) {
                    com.android.server.utils.Slogf.e(com.android.server.tv.interactive.TvInteractiveAppManagerService.TAG, "error in onBiInteractiveAppCreated", e);
                }
            }
        }

        public void onTeletextAppStateChanged(int i) {
            synchronized (com.android.server.tv.interactive.TvInteractiveAppManagerService.this.mLock) {
                if (this.mSessionState.mSession == null || this.mSessionState.mClient == null) {
                    return;
                }
                try {
                    this.mSessionState.mClient.onTeletextAppStateChanged(i, this.mSessionState.mSeq);
                } catch (android.os.RemoteException e) {
                    com.android.server.utils.Slogf.e(com.android.server.tv.interactive.TvInteractiveAppManagerService.TAG, "error in onTeletextAppStateChanged", e);
                }
            }
        }

        public void onAdBufferReady(android.media.tv.AdBuffer adBuffer) {
            android.os.SharedMemory sharedMemory;
            synchronized (com.android.server.tv.interactive.TvInteractiveAppManagerService.this.mLock) {
                if (this.mSessionState.mSession != null) {
                    try {
                        if (this.mSessionState.mClient != null) {
                            try {
                                this.mSessionState.mClient.onAdBufferReady(adBuffer, this.mSessionState.mSeq);
                            } catch (android.os.RemoteException e) {
                                com.android.server.utils.Slogf.e(com.android.server.tv.interactive.TvInteractiveAppManagerService.TAG, "error in onAdBuffer", e);
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

        @com.android.internal.annotations.GuardedBy({"mLock"})
        private boolean addSessionTokenToClientStateLocked(android.media.tv.interactive.ITvInteractiveAppSession iTvInteractiveAppSession) {
            try {
                iTvInteractiveAppSession.asBinder().linkToDeath(this.mSessionState, 0);
                android.os.IBinder asBinder = this.mSessionState.mClient.asBinder();
                com.android.server.tv.interactive.TvInteractiveAppManagerService.UserState orCreateUserStateLocked = com.android.server.tv.interactive.TvInteractiveAppManagerService.this.getOrCreateUserStateLocked(this.mSessionState.mUserId);
                com.android.server.tv.interactive.TvInteractiveAppManagerService.ClientState clientState = (com.android.server.tv.interactive.TvInteractiveAppManagerService.ClientState) orCreateUserStateLocked.mClientStateMap.get(asBinder);
                if (clientState == null) {
                    clientState = com.android.server.tv.interactive.TvInteractiveAppManagerService.this.new ClientState(asBinder, this.mSessionState.mUserId);
                    try {
                        asBinder.linkToDeath(clientState, 0);
                        orCreateUserStateLocked.mClientStateMap.put(asBinder, clientState);
                    } catch (android.os.RemoteException e) {
                        com.android.server.utils.Slogf.e(com.android.server.tv.interactive.TvInteractiveAppManagerService.TAG, "client process has already died", e);
                        return false;
                    }
                }
                clientState.mSessionTokens.add(this.mSessionState.mSessionToken);
                return true;
            } catch (android.os.RemoteException e2) {
                com.android.server.utils.Slogf.e(com.android.server.tv.interactive.TvInteractiveAppManagerService.TAG, "session process has already died", e2);
                return false;
            }
        }
    }

    private final class AdSessionCallback extends android.media.tv.ad.ITvAdSessionCallback.Stub {
        private final android.view.InputChannel[] mInputChannels;
        private final com.android.server.tv.interactive.TvInteractiveAppManagerService.AdSessionState mSessionState;

        AdSessionCallback(com.android.server.tv.interactive.TvInteractiveAppManagerService.AdSessionState adSessionState, android.view.InputChannel[] inputChannelArr) {
            this.mSessionState = adSessionState;
            this.mInputChannels = inputChannelArr;
        }

        public void onSessionCreated(android.media.tv.ad.ITvAdSession iTvAdSession) {
            synchronized (com.android.server.tv.interactive.TvInteractiveAppManagerService.this.mLock) {
                try {
                    this.mSessionState.mSession = iTvAdSession;
                    if (iTvAdSession != null && addAdSessionTokenToClientStateLocked(iTvAdSession)) {
                        com.android.server.tv.interactive.TvInteractiveAppManagerService.this.sendAdSessionTokenToClientLocked(this.mSessionState.mClient, this.mSessionState.mAdServiceId, this.mSessionState.mSessionToken, this.mInputChannels[0], this.mSessionState.mSeq);
                    } else {
                        com.android.server.tv.interactive.TvInteractiveAppManagerService.this.removeAdSessionStateLocked(this.mSessionState.mSessionToken, this.mSessionState.mUserId);
                        com.android.server.tv.interactive.TvInteractiveAppManagerService.this.sendAdSessionTokenToClientLocked(this.mSessionState.mClient, this.mSessionState.mAdServiceId, null, null, this.mSessionState.mSeq);
                    }
                    this.mInputChannels[0].dispose();
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        public void onLayoutSurface(int i, int i2, int i3, int i4) {
            synchronized (com.android.server.tv.interactive.TvInteractiveAppManagerService.this.mLock) {
                if (this.mSessionState.mSession == null || this.mSessionState.mClient == null) {
                    return;
                }
                try {
                    this.mSessionState.mClient.onLayoutSurface(i, i2, i3, i4, this.mSessionState.mSeq);
                } catch (android.os.RemoteException e) {
                    com.android.server.utils.Slogf.e(com.android.server.tv.interactive.TvInteractiveAppManagerService.TAG, "error in onLayoutSurface", e);
                }
            }
        }

        public void onRequestCurrentVideoBounds() {
            synchronized (com.android.server.tv.interactive.TvInteractiveAppManagerService.this.mLock) {
                if (this.mSessionState.mSession == null || this.mSessionState.mClient == null) {
                    return;
                }
                try {
                    this.mSessionState.mClient.onRequestCurrentVideoBounds(this.mSessionState.mSeq);
                } catch (android.os.RemoteException e) {
                    com.android.server.utils.Slogf.e(com.android.server.tv.interactive.TvInteractiveAppManagerService.TAG, "error in onRequestCurrentVideoBounds", e);
                }
            }
        }

        public void onRequestCurrentChannelUri() {
            synchronized (com.android.server.tv.interactive.TvInteractiveAppManagerService.this.mLock) {
                if (this.mSessionState.mSession == null || this.mSessionState.mClient == null) {
                    return;
                }
                try {
                    this.mSessionState.mClient.onRequestCurrentChannelUri(this.mSessionState.mSeq);
                } catch (android.os.RemoteException e) {
                    com.android.server.utils.Slogf.e(com.android.server.tv.interactive.TvInteractiveAppManagerService.TAG, "error in onRequestCurrentChannelUri", e);
                }
            }
        }

        public void onRequestTrackInfoList() {
            synchronized (com.android.server.tv.interactive.TvInteractiveAppManagerService.this.mLock) {
                if (this.mSessionState.mSession == null || this.mSessionState.mClient == null) {
                    return;
                }
                try {
                    this.mSessionState.mClient.onRequestTrackInfoList(this.mSessionState.mSeq);
                } catch (android.os.RemoteException e) {
                    com.android.server.utils.Slogf.e(com.android.server.tv.interactive.TvInteractiveAppManagerService.TAG, "error in onRequestTrackInfoList", e);
                }
            }
        }

        public void onRequestCurrentTvInputId() {
            synchronized (com.android.server.tv.interactive.TvInteractiveAppManagerService.this.mLock) {
                if (this.mSessionState.mSession == null || this.mSessionState.mClient == null) {
                    return;
                }
                try {
                    this.mSessionState.mClient.onRequestCurrentTvInputId(this.mSessionState.mSeq);
                } catch (android.os.RemoteException e) {
                    com.android.server.utils.Slogf.e(com.android.server.tv.interactive.TvInteractiveAppManagerService.TAG, "error in onRequestCurrentTvInputId", e);
                }
            }
        }

        public void onRequestSigning(java.lang.String str, java.lang.String str2, java.lang.String str3, byte[] bArr) {
            synchronized (com.android.server.tv.interactive.TvInteractiveAppManagerService.this.mLock) {
                if (this.mSessionState.mSession == null || this.mSessionState.mClient == null) {
                    return;
                }
                try {
                    this.mSessionState.mClient.onRequestSigning(str, str2, str3, bArr, this.mSessionState.mSeq);
                } catch (android.os.RemoteException e) {
                    com.android.server.utils.Slogf.e(com.android.server.tv.interactive.TvInteractiveAppManagerService.TAG, "error in onRequestSigning", e);
                }
            }
        }

        public void onTvAdSessionData(java.lang.String str, android.os.Bundle bundle) {
            synchronized (com.android.server.tv.interactive.TvInteractiveAppManagerService.this.mLock) {
                if (this.mSessionState.mSession == null || this.mSessionState.mClient == null) {
                    return;
                }
                try {
                    this.mSessionState.mClient.onTvAdSessionData(str, bundle, this.mSessionState.mSeq);
                } catch (android.os.RemoteException e) {
                    com.android.server.utils.Slogf.e(com.android.server.tv.interactive.TvInteractiveAppManagerService.TAG, "error in onTvAdSessionData", e);
                }
            }
        }

        @com.android.internal.annotations.GuardedBy({"mLock"})
        private boolean addAdSessionTokenToClientStateLocked(android.media.tv.ad.ITvAdSession iTvAdSession) {
            try {
                iTvAdSession.asBinder().linkToDeath(this.mSessionState, 0);
                android.os.IBinder asBinder = this.mSessionState.mClient.asBinder();
                com.android.server.tv.interactive.TvInteractiveAppManagerService.UserState orCreateUserStateLocked = com.android.server.tv.interactive.TvInteractiveAppManagerService.this.getOrCreateUserStateLocked(this.mSessionState.mUserId);
                com.android.server.tv.interactive.TvInteractiveAppManagerService.ClientState clientState = (com.android.server.tv.interactive.TvInteractiveAppManagerService.ClientState) orCreateUserStateLocked.mClientStateMap.get(asBinder);
                if (clientState == null) {
                    clientState = com.android.server.tv.interactive.TvInteractiveAppManagerService.this.new ClientState(asBinder, this.mSessionState.mUserId);
                    try {
                        asBinder.linkToDeath(clientState, 0);
                        orCreateUserStateLocked.mClientStateMap.put(asBinder, clientState);
                    } catch (android.os.RemoteException e) {
                        com.android.server.utils.Slogf.e(com.android.server.tv.interactive.TvInteractiveAppManagerService.TAG, "client process has already died", e);
                        return false;
                    }
                }
                clientState.mSessionTokens.add(this.mSessionState.mSessionToken);
                return true;
            } catch (android.os.RemoteException e2) {
                com.android.server.utils.Slogf.e(com.android.server.tv.interactive.TvInteractiveAppManagerService.TAG, "session process has already died", e2);
                return false;
            }
        }
    }

    private static class SessionNotFoundException extends java.lang.IllegalArgumentException {
        SessionNotFoundException(java.lang.String str) {
            super(str);
        }
    }

    private static class ClientPidNotFoundException extends java.lang.IllegalArgumentException {
        ClientPidNotFoundException(java.lang.String str) {
            super(str);
        }
    }
}
