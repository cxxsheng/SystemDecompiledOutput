package com.android.server.am;

/* loaded from: classes.dex */
public abstract class BaseAppStateTracker<T extends com.android.server.am.BaseAppStatePolicy> {
    static final long ONE_DAY = 86400000;
    static final long ONE_HOUR = 3600000;
    static final long ONE_MINUTE = 60000;
    static final int STATE_TYPE_FGS_LOCATION = 4;
    static final int STATE_TYPE_FGS_MEDIA_PLAYBACK = 2;
    static final int STATE_TYPE_FGS_WITH_NOTIFICATION = 8;
    static final int STATE_TYPE_INDEX_FGS_LOCATION = 2;
    static final int STATE_TYPE_INDEX_FGS_MEDIA_PLAYBACK = 1;
    static final int STATE_TYPE_INDEX_FGS_WITH_NOTIFICATION = 3;
    static final int STATE_TYPE_INDEX_MEDIA_SESSION = 0;
    static final int STATE_TYPE_INDEX_PERMISSION = 4;
    static final int STATE_TYPE_MEDIA_SESSION = 1;
    static final int STATE_TYPE_NUM = 5;
    static final int STATE_TYPE_PERMISSION = 16;
    protected static final java.lang.String TAG = "ActivityManager";
    protected final com.android.server.am.AppRestrictionController mAppRestrictionController;
    protected final android.os.Handler mBgHandler;
    protected final android.content.Context mContext;
    protected final com.android.server.am.BaseAppStateTracker.Injector<T> mInjector;
    protected final java.lang.Object mLock;
    protected final java.util.ArrayList<com.android.server.am.BaseAppStateTracker.StateListener> mStateListeners = new java.util.ArrayList<>();

    interface StateListener {
        void onStateChange(int i, java.lang.String str, boolean z, long j, int i2);
    }

    BaseAppStateTracker(android.content.Context context, com.android.server.am.AppRestrictionController appRestrictionController, @android.annotation.Nullable java.lang.reflect.Constructor<? extends com.android.server.am.BaseAppStateTracker.Injector<T>> constructor, java.lang.Object obj) {
        com.android.server.am.BaseAppStateTracker.Injector<T> injector;
        this.mContext = context;
        this.mAppRestrictionController = appRestrictionController;
        this.mBgHandler = appRestrictionController.getBackgroundHandler();
        this.mLock = appRestrictionController.getLock();
        if (constructor == null) {
            this.mInjector = new com.android.server.am.BaseAppStateTracker.Injector<>();
            return;
        }
        try {
            injector = constructor.newInstance(obj);
        } catch (java.lang.Exception e) {
            android.util.Slog.w(TAG, "Unable to instantiate " + constructor, e);
            injector = null;
        }
        this.mInjector = injector == null ? new com.android.server.am.BaseAppStateTracker.Injector<>() : injector;
    }

    static int stateTypeToIndex(int i) {
        return java.lang.Integer.numberOfTrailingZeros(i);
    }

    static int stateIndexToType(int i) {
        return 1 << i;
    }

    static java.lang.String stateTypesToString(int i) {
        java.lang.StringBuilder sb = new java.lang.StringBuilder("[");
        int highestOneBit = java.lang.Integer.highestOneBit(i);
        boolean z = false;
        while (highestOneBit != 0) {
            if (z) {
                sb.append('|');
            }
            switch (highestOneBit) {
                case 1:
                    sb.append("MEDIA_SESSION");
                    break;
                case 2:
                    sb.append("FGS_MEDIA_PLAYBACK");
                    break;
                case 4:
                    sb.append("FGS_LOCATION");
                    break;
                case 8:
                    sb.append("FGS_NOTIFICATION");
                    break;
                case 16:
                    sb.append("PERMISSION");
                    break;
                default:
                    return "[UNKNOWN(" + java.lang.Integer.toHexString(i) + ")]";
            }
            i &= ~highestOneBit;
            highestOneBit = java.lang.Integer.highestOneBit(i);
            z = true;
        }
        sb.append("]");
        return sb.toString();
    }

    void registerStateListener(@android.annotation.NonNull com.android.server.am.BaseAppStateTracker.StateListener stateListener) {
        synchronized (this.mLock) {
            this.mStateListeners.add(stateListener);
        }
    }

    void notifyListenersOnStateChange(int i, java.lang.String str, boolean z, long j, int i2) {
        synchronized (this.mLock) {
            try {
                int size = this.mStateListeners.size();
                for (int i3 = 0; i3 < size; i3++) {
                    this.mStateListeners.get(i3).onStateChange(i, str, z, j, i2);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @com.android.server.am.AppRestrictionController.TrackerType
    int getType() {
        return 0;
    }

    byte[] getTrackerInfoForStatsd(int i) {
        return null;
    }

    T getPolicy() {
        return this.mInjector.getPolicy();
    }

    void onSystemReady() {
        this.mInjector.onSystemReady();
    }

    void onUidAdded(int i) {
    }

    void onUidRemoved(int i) {
    }

    void onUserAdded(int i) {
    }

    void onUserStarted(int i) {
    }

    void onUserStopped(int i) {
    }

    void onUserRemoved(int i) {
    }

    void onLockedBootCompleted() {
    }

    void onPropertiesChanged(@android.annotation.NonNull java.lang.String str) {
        getPolicy().onPropertiesChanged(str);
    }

    void onUserInteractionStarted(java.lang.String str, int i) {
    }

    void onBackgroundRestrictionChanged(int i, java.lang.String str, boolean z) {
    }

    void onUidProcStateChanged(int i, int i2) {
    }

    void onUidGone(int i) {
    }

    void dump(java.io.PrintWriter printWriter, java.lang.String str) {
        this.mInjector.getPolicy().dump(printWriter, "  " + str);
    }

    void dumpAsProto(android.util.proto.ProtoOutputStream protoOutputStream, int i) {
    }

    static class Injector<T extends com.android.server.am.BaseAppStatePolicy> {
        android.app.ActivityManagerInternal mActivityManagerInternal;
        android.app.AppOpsManager mAppOpsManager;
        T mAppStatePolicy;
        android.os.BatteryManagerInternal mBatteryManagerInternal;
        android.os.BatteryStatsInternal mBatteryStatsInternal;
        com.android.server.DeviceIdleInternal mDeviceIdleInternal;
        com.android.internal.app.IAppOpsService mIAppOpsService;
        android.media.session.MediaSessionManager mMediaSessionManager;
        com.android.server.notification.NotificationManagerInternal mNotificationManagerInternal;
        android.content.pm.PackageManager mPackageManager;
        android.content.pm.PackageManagerInternal mPackageManagerInternal;
        android.permission.PermissionManager mPermissionManager;
        com.android.server.pm.permission.PermissionManagerServiceInternal mPermissionManagerServiceInternal;
        android.app.role.RoleManager mRoleManager;
        com.android.server.pm.UserManagerInternal mUserManagerInternal;

        Injector() {
        }

        void setPolicy(T t) {
            this.mAppStatePolicy = t;
        }

        void onSystemReady() {
            this.mActivityManagerInternal = (android.app.ActivityManagerInternal) com.android.server.LocalServices.getService(android.app.ActivityManagerInternal.class);
            this.mBatteryManagerInternal = (android.os.BatteryManagerInternal) com.android.server.LocalServices.getService(android.os.BatteryManagerInternal.class);
            this.mBatteryStatsInternal = (android.os.BatteryStatsInternal) com.android.server.LocalServices.getService(android.os.BatteryStatsInternal.class);
            this.mDeviceIdleInternal = (com.android.server.DeviceIdleInternal) com.android.server.LocalServices.getService(com.android.server.DeviceIdleInternal.class);
            this.mUserManagerInternal = (com.android.server.pm.UserManagerInternal) com.android.server.LocalServices.getService(com.android.server.pm.UserManagerInternal.class);
            this.mPackageManagerInternal = (android.content.pm.PackageManagerInternal) com.android.server.LocalServices.getService(android.content.pm.PackageManagerInternal.class);
            this.mPermissionManagerServiceInternal = (com.android.server.pm.permission.PermissionManagerServiceInternal) com.android.server.LocalServices.getService(com.android.server.pm.permission.PermissionManagerServiceInternal.class);
            android.content.Context context = this.mAppStatePolicy.mTracker.mContext;
            this.mPackageManager = context.getPackageManager();
            this.mAppOpsManager = (android.app.AppOpsManager) context.getSystemService(android.app.AppOpsManager.class);
            this.mMediaSessionManager = (android.media.session.MediaSessionManager) context.getSystemService(android.media.session.MediaSessionManager.class);
            this.mPermissionManager = (android.permission.PermissionManager) context.getSystemService(android.permission.PermissionManager.class);
            this.mRoleManager = (android.app.role.RoleManager) context.getSystemService(android.app.role.RoleManager.class);
            this.mNotificationManagerInternal = (com.android.server.notification.NotificationManagerInternal) com.android.server.LocalServices.getService(com.android.server.notification.NotificationManagerInternal.class);
            this.mIAppOpsService = com.android.internal.app.IAppOpsService.Stub.asInterface(android.os.ServiceManager.getService("appops"));
            getPolicy().onSystemReady();
        }

        android.app.ActivityManagerInternal getActivityManagerInternal() {
            return this.mActivityManagerInternal;
        }

        android.os.BatteryManagerInternal getBatteryManagerInternal() {
            return this.mBatteryManagerInternal;
        }

        android.os.BatteryStatsInternal getBatteryStatsInternal() {
            return this.mBatteryStatsInternal;
        }

        T getPolicy() {
            return this.mAppStatePolicy;
        }

        com.android.server.DeviceIdleInternal getDeviceIdleInternal() {
            return this.mDeviceIdleInternal;
        }

        com.android.server.pm.UserManagerInternal getUserManagerInternal() {
            return this.mUserManagerInternal;
        }

        long currentTimeMillis() {
            return java.lang.System.currentTimeMillis();
        }

        android.content.pm.PackageManager getPackageManager() {
            return this.mPackageManager;
        }

        android.content.pm.PackageManagerInternal getPackageManagerInternal() {
            return this.mPackageManagerInternal;
        }

        android.permission.PermissionManager getPermissionManager() {
            return this.mPermissionManager;
        }

        com.android.server.pm.permission.PermissionManagerServiceInternal getPermissionManagerServiceInternal() {
            return this.mPermissionManagerServiceInternal;
        }

        android.app.AppOpsManager getAppOpsManager() {
            return this.mAppOpsManager;
        }

        android.media.session.MediaSessionManager getMediaSessionManager() {
            return this.mMediaSessionManager;
        }

        long getServiceStartForegroundTimeout() {
            return this.mActivityManagerInternal.getServiceStartForegroundTimeout();
        }

        android.app.role.RoleManager getRoleManager() {
            return this.mRoleManager;
        }

        com.android.server.notification.NotificationManagerInternal getNotificationManagerInternal() {
            return this.mNotificationManagerInternal;
        }

        com.android.internal.app.IAppOpsService getIAppOpsService() {
            return this.mIAppOpsService;
        }
    }
}
