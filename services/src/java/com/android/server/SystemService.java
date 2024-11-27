package com.android.server;

@android.annotation.SystemApi(client = android.annotation.SystemApi.Client.SYSTEM_SERVER)
@android.ravenwood.annotation.RavenwoodKeepWholeClass
/* loaded from: classes.dex */
public abstract class SystemService {
    protected static final boolean DEBUG_USER = false;
    public static final int PHASE_ACTIVITY_MANAGER_READY = 550;
    public static final int PHASE_BOOT_COMPLETED = 1000;
    public static final int PHASE_DEVICE_SPECIFIC_SERVICES_READY = 520;
    public static final int PHASE_LOCK_SETTINGS_READY = 480;
    public static final int PHASE_SYSTEM_SERVICES_READY = 500;
    public static final int PHASE_THIRD_PARTY_APPS_CAN_START = 600;
    public static final int PHASE_WAIT_FOR_DEFAULT_DISPLAY = 100;
    public static final int PHASE_WAIT_FOR_SENSOR_SERVICE = 200;
    private final android.content.Context mContext;
    private final java.util.List<java.lang.Class<?>> mDependencies;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface BootPhase {
    }

    public abstract void onStart();

    @android.annotation.SystemApi(client = android.annotation.SystemApi.Client.SYSTEM_SERVER)
    public static final class TargetUser {
        private final boolean mFull;
        private final boolean mPreCreated;
        private final boolean mProfile;
        private final int mUserId;
        private final java.lang.String mUserType;

        public TargetUser(@android.annotation.NonNull android.content.pm.UserInfo userInfo) {
            this.mUserId = userInfo.id;
            this.mFull = userInfo.isFull();
            this.mProfile = userInfo.isProfile();
            this.mUserType = userInfo.userType;
            this.mPreCreated = userInfo.preCreated;
        }

        public boolean isFull() {
            return this.mFull;
        }

        public boolean isProfile() {
            return this.mProfile;
        }

        public boolean isManagedProfile() {
            return android.os.UserManager.isUserTypeManagedProfile(this.mUserType);
        }

        public boolean isPreCreated() {
            return this.mPreCreated;
        }

        @android.annotation.NonNull
        public android.os.UserHandle getUserHandle() {
            return android.os.UserHandle.of(this.mUserId);
        }

        public int getUserIdentifier() {
            return this.mUserId;
        }

        public java.lang.String toString() {
            return java.lang.Integer.toString(this.mUserId);
        }

        public void dump(@android.annotation.NonNull java.io.PrintWriter printWriter) {
            printWriter.print(getUserIdentifier());
            if (isFull() || isProfile()) {
                printWriter.print('(');
                if (isFull()) {
                    printWriter.print("full");
                }
                if (isProfile()) {
                    printWriter.print("profile");
                }
                printWriter.print(')');
            }
        }
    }

    public static final class UserCompletedEventType {
        public static final int EVENT_TYPE_USER_STARTING = 1;
        public static final int EVENT_TYPE_USER_SWITCHING = 4;
        public static final int EVENT_TYPE_USER_UNLOCKED = 2;
        private final int mEventType;

        @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
        public @interface EventTypesFlag {
        }

        UserCompletedEventType(int i) {
            this.mEventType = i;
        }

        @com.android.internal.annotations.VisibleForTesting
        public static com.android.server.SystemService.UserCompletedEventType newUserCompletedEventTypeForTest(int i) {
            return new com.android.server.SystemService.UserCompletedEventType(i);
        }

        public boolean includesOnUserStarting() {
            return (this.mEventType & 1) != 0;
        }

        public boolean includesOnUserUnlocked() {
            return (this.mEventType & 2) != 0;
        }

        public boolean includesOnUserSwitching() {
            return (this.mEventType & 4) != 0;
        }

        public java.lang.String toString() {
            java.lang.StringBuilder sb = new java.lang.StringBuilder("{");
            if (includesOnUserSwitching()) {
                sb.append("|Switching");
            }
            if (includesOnUserUnlocked()) {
                sb.append("|Unlocked");
            }
            if (includesOnUserStarting()) {
                sb.append("|Starting");
            }
            if (sb.length() > 1) {
                sb.append("|");
            }
            sb.append("}");
            return sb.toString();
        }
    }

    public SystemService(@android.annotation.NonNull android.content.Context context) {
        this(context, java.util.Collections.emptyList());
    }

    public SystemService(@android.annotation.NonNull android.content.Context context, @android.annotation.NonNull java.util.List<java.lang.Class<?>> list) {
        this.mContext = context;
        java.util.Objects.requireNonNull(list);
        this.mDependencies = list;
    }

    @android.annotation.NonNull
    public final android.content.Context getContext() {
        return this.mContext;
    }

    public final android.content.Context getUiContext() {
        return android.app.ActivityThread.currentActivityThread().getSystemUiContext();
    }

    @android.annotation.NonNull
    public final java.util.List<java.lang.Class<?>> getDependencies() {
        return this.mDependencies;
    }

    public final boolean isSafeMode() {
        return getManager().isSafeMode();
    }

    public void onBootPhase(int i) {
    }

    public boolean isUserSupported(@android.annotation.NonNull com.android.server.SystemService.TargetUser targetUser) {
        return true;
    }

    protected void dumpSupportedUsers(@android.annotation.NonNull java.io.PrintWriter printWriter, @android.annotation.NonNull java.lang.String str) {
        java.util.List users = android.os.UserManager.get(this.mContext).getUsers();
        java.util.ArrayList arrayList = new java.util.ArrayList(users.size());
        for (int i = 0; i < users.size(); i++) {
            android.content.pm.UserInfo userInfo = (android.content.pm.UserInfo) users.get(i);
            if (isUserSupported(new com.android.server.SystemService.TargetUser(userInfo))) {
                arrayList.add(java.lang.Integer.valueOf(userInfo.id));
            }
        }
        if (arrayList.isEmpty()) {
            printWriter.print(str);
            printWriter.println("No supported users");
            return;
        }
        int size = arrayList.size();
        printWriter.print(str);
        printWriter.print(size);
        printWriter.print(" supported user");
        if (size > 1) {
            printWriter.print("s");
        }
        printWriter.print(": ");
        printWriter.println(arrayList);
    }

    public void onUserStarting(@android.annotation.NonNull com.android.server.SystemService.TargetUser targetUser) {
    }

    public void onUserUnlocking(@android.annotation.NonNull com.android.server.SystemService.TargetUser targetUser) {
    }

    public void onUserUnlocked(@android.annotation.NonNull com.android.server.SystemService.TargetUser targetUser) {
    }

    public void onUserSwitching(@android.annotation.Nullable com.android.server.SystemService.TargetUser targetUser, @android.annotation.NonNull com.android.server.SystemService.TargetUser targetUser2) {
    }

    public void onUserStopping(@android.annotation.NonNull com.android.server.SystemService.TargetUser targetUser) {
    }

    public void onUserStopped(@android.annotation.NonNull com.android.server.SystemService.TargetUser targetUser) {
    }

    public void onUserCompletedEvent(@android.annotation.NonNull com.android.server.SystemService.TargetUser targetUser, com.android.server.SystemService.UserCompletedEventType userCompletedEventType) {
    }

    protected final void publishBinderService(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull android.os.IBinder iBinder) {
        publishBinderService(str, iBinder, false);
    }

    protected final void publishBinderService(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull android.os.IBinder iBinder, boolean z) {
        publishBinderService(str, iBinder, z, 8);
    }

    protected final void publishBinderService(java.lang.String str, android.os.IBinder iBinder, boolean z, int i) {
        android.os.ServiceManager.addService(str, iBinder, z, i);
    }

    protected final android.os.IBinder getBinderService(java.lang.String str) {
        return android.os.ServiceManager.getService(str);
    }

    protected final <T> void publishLocalService(java.lang.Class<T> cls, T t) {
        com.android.server.LocalServices.addService(cls, t);
    }

    protected final <T> T getLocalService(java.lang.Class<T> cls) {
        return (T) com.android.server.LocalServices.getService(cls);
    }

    private com.android.server.SystemServiceManager getManager() {
        return (com.android.server.SystemServiceManager) com.android.server.LocalServices.getService(com.android.server.SystemServiceManager.class);
    }
}
