package com.android.server.pm;

/* loaded from: classes2.dex */
class PackageMonitorCallbackHelper {
    private static final boolean DEBUG = false;
    private static final java.lang.String TAG = "PackageMonitorCallbackHelper";
    android.app.IActivityManager mActivityManager;

    @android.annotation.NonNull
    private final java.lang.Object mLock = new java.lang.Object();

    @android.annotation.NonNull
    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final android.os.RemoteCallbackList<android.os.IRemoteCallback> mCallbacks = new android.os.RemoteCallbackList<>();

    PackageMonitorCallbackHelper() {
    }

    public void registerPackageMonitorCallback(android.os.IRemoteCallback iRemoteCallback, int i, int i2) {
        synchronized (this.mLock) {
            this.mCallbacks.register(iRemoteCallback, new com.android.server.pm.PackageMonitorCallbackHelper.RegisterUser(i, i2));
        }
    }

    public void unregisterPackageMonitorCallback(android.os.IRemoteCallback iRemoteCallback) {
        synchronized (this.mLock) {
            this.mCallbacks.unregister(iRemoteCallback);
        }
    }

    public void onUserRemoved(int i) {
        int i2;
        java.util.ArrayList arrayList;
        synchronized (this.mLock) {
            try {
                int registeredCallbackCount = this.mCallbacks.getRegisteredCallbackCount();
                arrayList = null;
                for (int i3 = 0; i3 < registeredCallbackCount; i3++) {
                    if (((com.android.server.pm.PackageMonitorCallbackHelper.RegisterUser) this.mCallbacks.getRegisteredCallbackCookie(i3)).getUserId() == i) {
                        android.os.IRemoteCallback registeredCallbackItem = this.mCallbacks.getRegisteredCallbackItem(i3);
                        if (arrayList == null) {
                            arrayList = new java.util.ArrayList();
                        }
                        arrayList.add(registeredCallbackItem);
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        if (arrayList != null && arrayList.size() > 0) {
            int size = arrayList.size();
            for (i2 = 0; i2 < size; i2++) {
                unregisterPackageMonitorCallback((android.os.IRemoteCallback) arrayList.get(i2));
            }
        }
    }

    public void notifyPackageAddedForNewUsers(java.lang.String str, int i, @android.annotation.NonNull int[] iArr, @android.annotation.NonNull int[] iArr2, boolean z, int i2, android.util.SparseArray<int[]> sparseArray, @android.annotation.NonNull android.os.Handler handler) {
        android.os.Bundle bundle = new android.os.Bundle(2);
        bundle.putInt("android.intent.extra.UID", android.os.UserHandle.getUid(com.android.internal.util.ArrayUtils.isEmpty(iArr) ? iArr2[0] : iArr[0], i));
        if (z) {
            bundle.putBoolean("android.intent.extra.ARCHIVAL", true);
        }
        bundle.putInt("android.content.pm.extra.DATA_LOADER_TYPE", i2);
        notifyPackageMonitor("android.intent.action.PACKAGE_ADDED", str, bundle, iArr, iArr2, sparseArray, handler, null);
    }

    public void notifyResourcesChanged(boolean z, boolean z2, @android.annotation.NonNull java.lang.String[] strArr, @android.annotation.NonNull int[] iArr, @android.annotation.NonNull android.os.Handler handler) {
        android.os.Bundle bundle = new android.os.Bundle();
        bundle.putStringArray("android.intent.extra.changed_package_list", strArr);
        bundle.putIntArray("android.intent.extra.changed_uid_list", iArr);
        if (z2) {
            bundle.putBoolean("android.intent.extra.REPLACING", z2);
        }
        notifyPackageMonitor(z ? "android.intent.action.EXTERNAL_APPLICATIONS_AVAILABLE" : "android.intent.action.EXTERNAL_APPLICATIONS_UNAVAILABLE", null, bundle, null, null, null, handler, null);
    }

    public void notifyPackageChanged(java.lang.String str, boolean z, java.util.ArrayList<java.lang.String> arrayList, int i, java.lang.String str2, int[] iArr, int[] iArr2, android.util.SparseArray<int[]> sparseArray, android.os.Handler handler) {
        android.os.Bundle bundle = new android.os.Bundle(4);
        bundle.putString("android.intent.extra.changed_component_name", arrayList.get(0));
        java.lang.String[] strArr = new java.lang.String[arrayList.size()];
        arrayList.toArray(strArr);
        bundle.putStringArray("android.intent.extra.changed_component_name_list", strArr);
        bundle.putBoolean("android.intent.extra.DONT_KILL_APP", z);
        bundle.putInt("android.intent.extra.UID", i);
        if (str2 != null) {
            bundle.putString("android.intent.extra.REASON", str2);
        }
        notifyPackageMonitor("android.intent.action.PACKAGE_CHANGED", str, bundle, iArr, iArr2, sparseArray, handler, null);
    }

    public void notifyPackageMonitor(java.lang.String str, java.lang.String str2, android.os.Bundle bundle, int[] iArr, int[] iArr2, android.util.SparseArray<int[]> sparseArray, android.os.Handler handler, java.util.function.BiFunction<java.lang.Integer, android.os.Bundle, android.os.Bundle> biFunction) {
        int[] runningUserIds;
        if (!isAllowedCallbackAction(str)) {
            return;
        }
        if (iArr == null) {
            try {
                if (this.mActivityManager == null) {
                    this.mActivityManager = android.app.ActivityManager.getService();
                }
                if (this.mActivityManager == null) {
                    return;
                } else {
                    runningUserIds = this.mActivityManager.getRunningUserIds();
                }
            } catch (android.os.RemoteException e) {
                return;
            }
        } else {
            runningUserIds = iArr;
        }
        if (com.android.internal.util.ArrayUtils.isEmpty(iArr2)) {
            doNotifyCallbacksByAction(str, str2, bundle, runningUserIds, sparseArray, handler, biFunction);
        } else {
            doNotifyCallbacksByAction(str, str2, bundle, iArr2, sparseArray, handler, biFunction);
        }
    }

    void notifyPackageMonitorWithIntent(android.content.Intent intent, int i, int[] iArr, android.os.Handler handler) {
        if (!isAllowedCallbackAction(intent.getAction())) {
            return;
        }
        doNotifyCallbacksByIntent(intent, i, iArr, handler);
    }

    private static boolean isAllowedCallbackAction(java.lang.String str) {
        return android.text.TextUtils.equals(str, "android.intent.action.PACKAGE_ADDED") || android.text.TextUtils.equals(str, "android.intent.action.PACKAGE_REMOVED") || android.text.TextUtils.equals(str, "android.intent.action.PACKAGE_CHANGED") || android.text.TextUtils.equals(str, "android.intent.action.UID_REMOVED") || android.text.TextUtils.equals(str, "android.intent.action.PACKAGES_SUSPENDED") || android.text.TextUtils.equals(str, "android.intent.action.PACKAGES_UNSUSPENDED") || android.text.TextUtils.equals(str, "android.intent.action.EXTERNAL_APPLICATIONS_AVAILABLE") || android.text.TextUtils.equals(str, "android.intent.action.EXTERNAL_APPLICATIONS_UNAVAILABLE") || android.text.TextUtils.equals(str, "android.intent.action.PACKAGE_DATA_CLEARED") || android.text.TextUtils.equals(str, "android.intent.action.PACKAGE_RESTARTED") || android.text.TextUtils.equals(str, "android.intent.action.PACKAGE_UNSTOPPED");
    }

    private void doNotifyCallbacksByIntent(android.content.Intent intent, int i, int[] iArr, android.os.Handler handler) {
        android.os.RemoteCallbackList<android.os.IRemoteCallback> remoteCallbackList;
        synchronized (this.mLock) {
            remoteCallbackList = this.mCallbacks;
        }
        doNotifyCallbacks(remoteCallbackList, intent, i, iArr, handler, null);
    }

    private void doNotifyCallbacksByAction(java.lang.String str, java.lang.String str2, android.os.Bundle bundle, int[] iArr, android.util.SparseArray<int[]> sparseArray, android.os.Handler handler, java.util.function.BiFunction<java.lang.Integer, android.os.Bundle, android.os.Bundle> biFunction) {
        android.os.RemoteCallbackList<android.os.IRemoteCallback> remoteCallbackList;
        synchronized (this.mLock) {
            remoteCallbackList = this.mCallbacks;
        }
        for (int i : iArr) {
            int[] iArr2 = null;
            android.content.Intent intent = new android.content.Intent(str, str2 != null ? android.net.Uri.fromParts(com.android.server.pm.Settings.ATTR_PACKAGE, str2, null) : null);
            if (bundle != null) {
                intent.putExtras(bundle);
            }
            int intExtra = intent.getIntExtra("android.intent.extra.UID", -1);
            if (intExtra >= 0 && android.os.UserHandle.getUserId(intExtra) != i) {
                intent.putExtra("android.intent.extra.UID", android.os.UserHandle.getUid(i, android.os.UserHandle.getAppId(intExtra)));
            }
            intent.putExtra("android.intent.extra.user_handle", i);
            if (sparseArray != null) {
                iArr2 = sparseArray.get(i);
            }
            doNotifyCallbacks(remoteCallbackList, intent, i, iArr2, handler, biFunction);
        }
    }

    private void doNotifyCallbacks(final android.os.RemoteCallbackList<android.os.IRemoteCallback> remoteCallbackList, final android.content.Intent intent, final int i, final int[] iArr, android.os.Handler handler, final java.util.function.BiFunction<java.lang.Integer, android.os.Bundle, android.os.Bundle> biFunction) {
        handler.post(new java.lang.Runnable() { // from class: com.android.server.pm.PackageMonitorCallbackHelper$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.pm.PackageMonitorCallbackHelper.this.lambda$doNotifyCallbacks$1(remoteCallbackList, i, iArr, intent, biFunction);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$doNotifyCallbacks$1(android.os.RemoteCallbackList remoteCallbackList, final int i, final int[] iArr, final android.content.Intent intent, final java.util.function.BiFunction biFunction) {
        remoteCallbackList.broadcast(new java.util.function.BiConsumer() { // from class: com.android.server.pm.PackageMonitorCallbackHelper$$ExternalSyntheticLambda0
            @Override // java.util.function.BiConsumer
            public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                com.android.server.pm.PackageMonitorCallbackHelper.this.lambda$doNotifyCallbacks$0(i, iArr, intent, biFunction, (android.os.IRemoteCallback) obj, obj2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$doNotifyCallbacks$0(int i, int[] iArr, android.content.Intent intent, java.util.function.BiFunction biFunction, android.os.IRemoteCallback iRemoteCallback, java.lang.Object obj) {
        android.os.Bundle extras;
        com.android.server.pm.PackageMonitorCallbackHelper.RegisterUser registerUser = (com.android.server.pm.PackageMonitorCallbackHelper.RegisterUser) obj;
        if (registerUser.getUserId() != -1 && registerUser.getUserId() != i) {
            return;
        }
        int uid = registerUser.getUid();
        if (iArr != null && uid != 1000 && !com.android.internal.util.ArrayUtils.contains(iArr, uid)) {
            return;
        }
        if (biFunction != null && (extras = intent.getExtras()) != null) {
            android.os.Bundle bundle = (android.os.Bundle) biFunction.apply(java.lang.Integer.valueOf(uid), extras);
            if (bundle == null) {
                return;
            }
            android.content.Intent intent2 = new android.content.Intent(intent);
            intent2.replaceExtras(bundle);
            intent = intent2;
        }
        invokeCallback(iRemoteCallback, intent);
    }

    private void invokeCallback(android.os.IRemoteCallback iRemoteCallback, android.content.Intent intent) {
        try {
            android.os.Bundle bundle = new android.os.Bundle();
            bundle.putParcelable("android.content.pm.extra.EXTRA_PACKAGE_MONITOR_CALLBACK_RESULT", intent);
            iRemoteCallback.sendResult(bundle);
        } catch (android.os.RemoteException e) {
        }
    }

    private final class RegisterUser {
        int mUid;
        int mUserId;

        RegisterUser(int i, int i2) {
            this.mUid = i2;
            this.mUserId = i;
        }

        public int getUid() {
            return this.mUid;
        }

        public int getUserId() {
            return this.mUserId;
        }
    }
}
