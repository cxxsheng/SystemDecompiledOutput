package com.android.server.devicepolicy;

/* loaded from: classes.dex */
public class DeviceAdminServiceController {
    static final boolean DEBUG = false;
    static final java.lang.String TAG = "DevicePolicyManager";
    private final com.android.server.devicepolicy.DevicePolicyConstants mConstants;
    final android.content.Context mContext;
    private final com.android.server.devicepolicy.DevicePolicyManagerService.Injector mInjector;
    final java.lang.Object mLock = new java.lang.Object();

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final android.util.SparseArray<java.util.Map<java.lang.String, com.android.server.devicepolicy.DeviceAdminServiceController.DevicePolicyServiceConnection>> mConnections = new android.util.SparseArray<>();
    private final android.os.Handler mHandler = new android.os.Handler(com.android.internal.os.BackgroundThread.get().getLooper());

    private class DevicePolicyServiceConnection extends com.android.server.am.PersistentConnection<android.app.admin.IDeviceAdminService> {
        public DevicePolicyServiceConnection(int i, @android.annotation.NonNull android.content.ComponentName componentName) {
            super(com.android.server.devicepolicy.DeviceAdminServiceController.TAG, com.android.server.devicepolicy.DeviceAdminServiceController.this.mContext, com.android.server.devicepolicy.DeviceAdminServiceController.this.mHandler, i, componentName, com.android.server.devicepolicy.DeviceAdminServiceController.this.mConstants.DAS_DIED_SERVICE_RECONNECT_BACKOFF_SEC, com.android.server.devicepolicy.DeviceAdminServiceController.this.mConstants.DAS_DIED_SERVICE_RECONNECT_BACKOFF_INCREASE, com.android.server.devicepolicy.DeviceAdminServiceController.this.mConstants.DAS_DIED_SERVICE_RECONNECT_MAX_BACKOFF_SEC, com.android.server.devicepolicy.DeviceAdminServiceController.this.mConstants.DAS_DIED_SERVICE_STABLE_CONNECTION_THRESHOLD_SEC);
        }

        @Override // com.android.server.am.PersistentConnection
        protected int getBindFlags() {
            return 67108864;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // com.android.server.am.PersistentConnection
        public android.app.admin.IDeviceAdminService asInterface(android.os.IBinder iBinder) {
            return android.app.admin.IDeviceAdminService.Stub.asInterface(iBinder);
        }
    }

    public DeviceAdminServiceController(com.android.server.devicepolicy.DevicePolicyManagerService devicePolicyManagerService, com.android.server.devicepolicy.DevicePolicyConstants devicePolicyConstants) {
        this.mInjector = devicePolicyManagerService.mInjector;
        this.mContext = this.mInjector.mContext;
        this.mConstants = devicePolicyConstants;
    }

    @android.annotation.Nullable
    private android.content.pm.ServiceInfo findService(@android.annotation.NonNull java.lang.String str, int i) {
        return com.android.server.appbinding.AppBindingUtils.findService(str, i, "android.app.action.DEVICE_ADMIN_SERVICE", "android.permission.BIND_DEVICE_ADMIN", android.app.admin.DeviceAdminService.class, this.mInjector.getIPackageManager(), new java.lang.StringBuilder());
    }

    public void startServiceForAdmin(@android.annotation.NonNull java.lang.String str, int i, @android.annotation.NonNull java.lang.String str2) {
        long binderClearCallingIdentity = this.mInjector.binderClearCallingIdentity();
        try {
            synchronized (this.mLock) {
                android.content.pm.ServiceInfo findService = findService(str, i);
                if (findService == null) {
                    disconnectServiceOnUserLocked(str, i, str2);
                    return;
                }
                if ((this.mConnections.contains(i) ? this.mConnections.get(i).get(str) : null) != null) {
                    disconnectServiceOnUserLocked(str, i, str2);
                }
                com.android.server.devicepolicy.DeviceAdminServiceController.DevicePolicyServiceConnection devicePolicyServiceConnection = new com.android.server.devicepolicy.DeviceAdminServiceController.DevicePolicyServiceConnection(i, findService.getComponentName());
                if (!this.mConnections.contains(i)) {
                    this.mConnections.put(i, new java.util.HashMap());
                }
                this.mConnections.get(i).put(str, devicePolicyServiceConnection);
                devicePolicyServiceConnection.bind();
            }
        } finally {
            this.mInjector.binderRestoreCallingIdentity(binderClearCallingIdentity);
        }
    }

    public void stopServiceForAdmin(@android.annotation.NonNull java.lang.String str, int i, @android.annotation.NonNull java.lang.String str2) {
        long binderClearCallingIdentity = this.mInjector.binderClearCallingIdentity();
        try {
            synchronized (this.mLock) {
                disconnectServiceOnUserLocked(str, i, str2);
            }
        } finally {
            this.mInjector.binderRestoreCallingIdentity(binderClearCallingIdentity);
        }
    }

    public void stopServicesForUser(int i, @android.annotation.NonNull java.lang.String str) {
        long binderClearCallingIdentity = this.mInjector.binderClearCallingIdentity();
        try {
            synchronized (this.mLock) {
                disconnectServiceOnUserLocked(i, str);
            }
        } finally {
            this.mInjector.binderRestoreCallingIdentity(binderClearCallingIdentity);
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void disconnectServiceOnUserLocked(@android.annotation.NonNull java.lang.String str, int i, @android.annotation.NonNull java.lang.String str2) {
        com.android.server.devicepolicy.DeviceAdminServiceController.DevicePolicyServiceConnection devicePolicyServiceConnection = this.mConnections.contains(i) ? this.mConnections.get(i).get(str) : null;
        if (devicePolicyServiceConnection != null) {
            devicePolicyServiceConnection.unbind();
            this.mConnections.get(i).remove(str);
            if (this.mConnections.get(i).isEmpty()) {
                this.mConnections.remove(i);
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void disconnectServiceOnUserLocked(int i, @android.annotation.NonNull java.lang.String str) {
        if (!this.mConnections.contains(i)) {
            return;
        }
        java.util.Iterator<java.lang.String> it = this.mConnections.get(i).keySet().iterator();
        while (it.hasNext()) {
            this.mConnections.get(i).get(it.next()).unbind();
        }
        this.mConnections.remove(i);
    }

    public void dump(android.util.IndentingPrintWriter indentingPrintWriter) {
        synchronized (this.mLock) {
            try {
                if (this.mConnections.size() == 0) {
                    return;
                }
                indentingPrintWriter.println("Admin Services:");
                indentingPrintWriter.increaseIndent();
                for (int i = 0; i < this.mConnections.size(); i++) {
                    int keyAt = this.mConnections.keyAt(i);
                    indentingPrintWriter.print("User: ");
                    indentingPrintWriter.println(keyAt);
                    for (java.lang.String str : this.mConnections.get(keyAt).keySet()) {
                        indentingPrintWriter.increaseIndent();
                        indentingPrintWriter.print("Package: ");
                        indentingPrintWriter.println(str);
                        com.android.server.devicepolicy.DeviceAdminServiceController.DevicePolicyServiceConnection devicePolicyServiceConnection = this.mConnections.valueAt(i).get(str);
                        indentingPrintWriter.increaseIndent();
                        devicePolicyServiceConnection.dump("", indentingPrintWriter);
                        indentingPrintWriter.decreaseIndent();
                        indentingPrintWriter.decreaseIndent();
                    }
                }
                indentingPrintWriter.decreaseIndent();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }
}
