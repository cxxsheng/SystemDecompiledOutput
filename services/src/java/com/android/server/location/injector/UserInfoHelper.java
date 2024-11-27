package com.android.server.location.injector;

/* loaded from: classes2.dex */
public abstract class UserInfoHelper {
    private final java.util.concurrent.CopyOnWriteArrayList<com.android.server.location.injector.UserInfoHelper.UserListener> mListeners = new java.util.concurrent.CopyOnWriteArrayList<>();

    public interface UserListener {
        public static final int CURRENT_USER_CHANGED = 1;
        public static final int USER_STARTED = 2;
        public static final int USER_STOPPED = 3;
        public static final int USER_VISIBILITY_CHANGED = 4;

        @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
        public @interface UserChange {
        }

        void onUserChanged(int i, int i2);
    }

    public abstract void dump(java.io.FileDescriptor fileDescriptor, android.util.IndentingPrintWriter indentingPrintWriter, java.lang.String[] strArr);

    public abstract int getCurrentUserId();

    protected abstract int[] getProfileIds(int i);

    public abstract int[] getRunningUserIds();

    public abstract boolean isCurrentUserId(int i);

    public abstract boolean isVisibleUserId(int i);

    public final void addListener(com.android.server.location.injector.UserInfoHelper.UserListener userListener) {
        this.mListeners.add(userListener);
    }

    public final void removeListener(com.android.server.location.injector.UserInfoHelper.UserListener userListener) {
        this.mListeners.remove(userListener);
    }

    protected final void dispatchOnUserStarted(int i) {
        if (com.android.server.location.LocationManagerService.D) {
            android.util.Log.d(com.android.server.location.LocationManagerService.TAG, "u" + i + " started");
        }
        java.util.Iterator<com.android.server.location.injector.UserInfoHelper.UserListener> it = this.mListeners.iterator();
        while (it.hasNext()) {
            it.next().onUserChanged(i, 2);
        }
    }

    protected final void dispatchOnUserStopped(int i) {
        if (com.android.server.location.LocationManagerService.D) {
            android.util.Log.d(com.android.server.location.LocationManagerService.TAG, "u" + i + " stopped");
        }
        java.util.Iterator<com.android.server.location.injector.UserInfoHelper.UserListener> it = this.mListeners.iterator();
        while (it.hasNext()) {
            it.next().onUserChanged(i, 3);
        }
    }

    protected final void dispatchOnCurrentUserChanged(int i, int i2) {
        int[] profileIds = getProfileIds(i);
        int[] profileIds2 = getProfileIds(i2);
        if (com.android.server.location.LocationManagerService.D) {
            android.util.Log.d(com.android.server.location.LocationManagerService.TAG, "current user changed from u" + java.util.Arrays.toString(profileIds) + " to u" + java.util.Arrays.toString(profileIds2));
        }
        com.android.server.location.eventlog.LocationEventLog.EVENT_LOG.logUserSwitched(i, i2);
        java.util.Iterator<com.android.server.location.injector.UserInfoHelper.UserListener> it = this.mListeners.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            com.android.server.location.injector.UserInfoHelper.UserListener next = it.next();
            for (int i3 : profileIds) {
                next.onUserChanged(i3, 1);
            }
        }
        java.util.Iterator<com.android.server.location.injector.UserInfoHelper.UserListener> it2 = this.mListeners.iterator();
        while (it2.hasNext()) {
            com.android.server.location.injector.UserInfoHelper.UserListener next2 = it2.next();
            for (int i4 : profileIds2) {
                next2.onUserChanged(i4, 1);
            }
        }
    }

    protected final void dispatchOnVisibleUserChanged(int i, boolean z) {
        if (com.android.server.location.LocationManagerService.D) {
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            sb.append("visibility of u");
            sb.append(i);
            sb.append(" changed to ");
            sb.append(z ? com.android.server.wm.ActivityTaskManagerService.DUMP_VISIBLE_ACTIVITIES : "invisible");
            android.util.Log.d(com.android.server.location.LocationManagerService.TAG, sb.toString());
        }
        com.android.server.location.eventlog.LocationEventLog.EVENT_LOG.logUserVisibilityChanged(i, z);
        java.util.Iterator<com.android.server.location.injector.UserInfoHelper.UserListener> it = this.mListeners.iterator();
        while (it.hasNext()) {
            it.next().onUserChanged(i, 4);
        }
    }
}
