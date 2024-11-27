package com.android.server.statusbar;

/* loaded from: classes2.dex */
public class SessionMonitor {
    private static final java.lang.String TAG = "SessionMonitor";
    private final android.content.Context mContext;
    private final java.util.Map<java.lang.Integer, java.util.Set<com.android.internal.statusbar.ISessionListener>> mSessionToListeners = new java.util.HashMap();

    public SessionMonitor(android.content.Context context) {
        this.mContext = context;
        java.util.Iterator it = android.app.StatusBarManager.ALL_SESSIONS.iterator();
        while (it.hasNext()) {
            this.mSessionToListeners.put(java.lang.Integer.valueOf(((java.lang.Integer) it.next()).intValue()), new java.util.HashSet());
        }
    }

    public void registerSessionListener(int i, com.android.internal.statusbar.ISessionListener iSessionListener) {
        requireListenerPermissions(i);
        synchronized (this.mSessionToListeners) {
            try {
                java.util.Iterator it = android.app.StatusBarManager.ALL_SESSIONS.iterator();
                while (it.hasNext()) {
                    int intValue = ((java.lang.Integer) it.next()).intValue();
                    if ((i & intValue) != 0) {
                        this.mSessionToListeners.get(java.lang.Integer.valueOf(intValue)).add(iSessionListener);
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void unregisterSessionListener(int i, com.android.internal.statusbar.ISessionListener iSessionListener) {
        synchronized (this.mSessionToListeners) {
            try {
                java.util.Iterator it = android.app.StatusBarManager.ALL_SESSIONS.iterator();
                while (it.hasNext()) {
                    int intValue = ((java.lang.Integer) it.next()).intValue();
                    if ((i & intValue) != 0) {
                        this.mSessionToListeners.get(java.lang.Integer.valueOf(intValue)).remove(iSessionListener);
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void onSessionStarted(int i, @android.annotation.NonNull com.android.internal.logging.InstanceId instanceId) {
        requireSetterPermissions(i);
        if (!isValidSessionType(i)) {
            android.util.Log.e(TAG, "invalid onSessionStarted sessionType=" + i);
            return;
        }
        synchronized (this.mSessionToListeners) {
            for (com.android.internal.statusbar.ISessionListener iSessionListener : this.mSessionToListeners.get(java.lang.Integer.valueOf(i))) {
                try {
                    iSessionListener.onSessionStarted(i, instanceId);
                } catch (android.os.RemoteException e) {
                    android.util.Log.e(TAG, "unable to send session start to listener=" + iSessionListener, e);
                }
            }
        }
    }

    public void onSessionEnded(int i, @android.annotation.NonNull com.android.internal.logging.InstanceId instanceId) {
        requireSetterPermissions(i);
        if (!isValidSessionType(i)) {
            android.util.Log.e(TAG, "invalid onSessionEnded sessionType=" + i);
            return;
        }
        synchronized (this.mSessionToListeners) {
            for (com.android.internal.statusbar.ISessionListener iSessionListener : this.mSessionToListeners.get(java.lang.Integer.valueOf(i))) {
                try {
                    iSessionListener.onSessionEnded(i, instanceId);
                } catch (android.os.RemoteException e) {
                    android.util.Log.e(TAG, "unable to send session end to listener=" + iSessionListener, e);
                }
            }
        }
    }

    private boolean isValidSessionType(int i) {
        return android.app.StatusBarManager.ALL_SESSIONS.contains(java.lang.Integer.valueOf(i));
    }

    private void requireListenerPermissions(int i) {
        if ((i & 1) != 0) {
            this.mContext.enforceCallingOrSelfPermission("android.permission.MANAGE_BIOMETRIC", "StatusBarManagerService.SessionMonitor");
        }
        if ((i & 2) != 0) {
            this.mContext.enforceCallingOrSelfPermission("android.permission.MANAGE_BIOMETRIC", "StatusBarManagerService.SessionMonitor");
        }
    }

    private void requireSetterPermissions(int i) {
        if ((i & 1) != 0) {
            this.mContext.enforceCallingOrSelfPermission("android.permission.CONTROL_KEYGUARD", "StatusBarManagerService.SessionMonitor");
        }
        if ((i & 2) != 0) {
            this.mContext.enforceCallingOrSelfPermission("android.permission.STATUS_BAR_SERVICE", "StatusBarManagerService.SessionMonitor");
        }
    }
}
