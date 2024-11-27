package com.android.server.am;

/* loaded from: classes.dex */
final class AppPermissionTracker extends com.android.server.am.BaseAppStateTracker<com.android.server.am.AppPermissionTracker.AppPermissionPolicy> implements android.content.pm.PackageManager.OnPermissionsChangedListener {
    static final boolean DEBUG_PERMISSION_TRACKER = false;
    static final java.lang.String TAG = "ActivityManager";

    @com.android.internal.annotations.GuardedBy({"mAppOpsCallbacks"})
    private final android.util.SparseArray<com.android.server.am.AppPermissionTracker.MyAppOpsCallback> mAppOpsCallbacks;
    private final com.android.server.am.AppPermissionTracker.MyHandler mHandler;
    private volatile boolean mLockedBootCompleted;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private android.util.SparseArray<android.util.ArraySet<com.android.server.am.AppPermissionTracker.UidGrantedPermissionState>> mUidGrantedPermissionsInMonitor;

    AppPermissionTracker(android.content.Context context, com.android.server.am.AppRestrictionController appRestrictionController) {
        this(context, appRestrictionController, null, null);
    }

    AppPermissionTracker(android.content.Context context, com.android.server.am.AppRestrictionController appRestrictionController, java.lang.reflect.Constructor<? extends com.android.server.am.BaseAppStateTracker.Injector<com.android.server.am.AppPermissionTracker.AppPermissionPolicy>> constructor, java.lang.Object obj) {
        super(context, appRestrictionController, constructor, obj);
        this.mAppOpsCallbacks = new android.util.SparseArray<>();
        this.mUidGrantedPermissionsInMonitor = new android.util.SparseArray<>();
        this.mLockedBootCompleted = false;
        this.mHandler = new com.android.server.am.AppPermissionTracker.MyHandler(this);
        this.mInjector.setPolicy(new com.android.server.am.AppPermissionTracker.AppPermissionPolicy(this.mInjector, this));
    }

    @Override // com.android.server.am.BaseAppStateTracker
    @com.android.server.am.AppRestrictionController.TrackerType
    int getType() {
        return 5;
    }

    public void onPermissionsChanged(int i) {
        this.mHandler.obtainMessage(2, i, 0).sendToTarget();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleAppOpsInit() {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        for (android.util.Pair pair : ((com.android.server.am.AppPermissionTracker.AppPermissionPolicy) this.mInjector.getPolicy()).getBgPermissionsInMonitor()) {
            if (((java.lang.Integer) pair.second).intValue() != -1) {
                arrayList.add((java.lang.Integer) pair.second);
            }
        }
        startWatchingMode((java.lang.Integer[]) arrayList.toArray(new java.lang.Integer[arrayList.size()]));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handlePermissionsInit() {
        com.android.server.am.AppPermissionTracker.UidGrantedPermissionState uidGrantedPermissionState;
        int i;
        int i2;
        android.content.pm.ApplicationInfo applicationInfo;
        int i3;
        int[] userIds = this.mInjector.getUserManagerInternal().getUserIds();
        android.content.pm.PackageManagerInternal packageManagerInternal = this.mInjector.getPackageManagerInternal();
        this.mInjector.getPermissionManagerServiceInternal();
        android.util.Pair[] bgPermissionsInMonitor = ((com.android.server.am.AppPermissionTracker.AppPermissionPolicy) this.mInjector.getPolicy()).getBgPermissionsInMonitor();
        android.util.SparseArray<android.util.ArraySet<com.android.server.am.AppPermissionTracker.UidGrantedPermissionState>> sparseArray = this.mUidGrantedPermissionsInMonitor;
        for (int i4 : userIds) {
            java.util.List<android.content.pm.ApplicationInfo> installedApplications = packageManagerInternal.getInstalledApplications(0L, i4, 1000);
            if (installedApplications != null) {
                long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
                int size = installedApplications.size();
                int i5 = 0;
                while (i5 < size) {
                    android.content.pm.ApplicationInfo applicationInfo2 = installedApplications.get(i5);
                    int length = bgPermissionsInMonitor.length;
                    int i6 = 0;
                    while (i6 < length) {
                        android.util.Pair pair = bgPermissionsInMonitor[i6];
                        int i7 = i6;
                        com.android.server.am.AppPermissionTracker.UidGrantedPermissionState uidGrantedPermissionState2 = new com.android.server.am.AppPermissionTracker.UidGrantedPermissionState(applicationInfo2.uid, (java.lang.String) pair.first, ((java.lang.Integer) pair.second).intValue());
                        if (!uidGrantedPermissionState2.isGranted()) {
                            i = length;
                            i2 = i5;
                            applicationInfo = applicationInfo2;
                            i3 = size;
                        } else {
                            synchronized (this.mLock) {
                                try {
                                    android.util.ArraySet<com.android.server.am.AppPermissionTracker.UidGrantedPermissionState> arraySet = sparseArray.get(applicationInfo2.uid);
                                    if (arraySet != null) {
                                        uidGrantedPermissionState = uidGrantedPermissionState2;
                                        i = length;
                                        i2 = i5;
                                        applicationInfo = applicationInfo2;
                                        i3 = size;
                                    } else {
                                        android.util.ArraySet<com.android.server.am.AppPermissionTracker.UidGrantedPermissionState> arraySet2 = new android.util.ArraySet<>();
                                        sparseArray.put(applicationInfo2.uid, arraySet2);
                                        uidGrantedPermissionState = uidGrantedPermissionState2;
                                        i = length;
                                        i2 = i5;
                                        applicationInfo = applicationInfo2;
                                        i3 = size;
                                        notifyListenersOnStateChange(applicationInfo2.uid, "", true, elapsedRealtime, 16);
                                        arraySet = arraySet2;
                                    }
                                    arraySet.add(uidGrantedPermissionState);
                                } catch (java.lang.Throwable th) {
                                    throw th;
                                }
                            }
                        }
                        i6 = i7 + 1;
                        length = i;
                        i5 = i2;
                        applicationInfo2 = applicationInfo;
                        size = i3;
                    }
                    i5++;
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleAppOpsDestroy() {
        stopWatchingMode();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handlePermissionsDestroy() {
        synchronized (this.mLock) {
            try {
                android.util.SparseArray<android.util.ArraySet<com.android.server.am.AppPermissionTracker.UidGrantedPermissionState>> sparseArray = this.mUidGrantedPermissionsInMonitor;
                long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
                int size = sparseArray.size();
                for (int i = 0; i < size; i++) {
                    int keyAt = sparseArray.keyAt(i);
                    if (sparseArray.valueAt(i).size() > 0) {
                        notifyListenersOnStateChange(keyAt, "", false, elapsedRealtime, 16);
                    }
                }
                sparseArray.clear();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleOpChanged(int i, int i2, java.lang.String str) {
        android.util.Pair[] bgPermissionsInMonitor = ((com.android.server.am.AppPermissionTracker.AppPermissionPolicy) this.mInjector.getPolicy()).getBgPermissionsInMonitor();
        if (bgPermissionsInMonitor != null && bgPermissionsInMonitor.length > 0) {
            for (android.util.Pair pair : bgPermissionsInMonitor) {
                if (((java.lang.Integer) pair.second).intValue() == i) {
                    com.android.server.am.AppPermissionTracker.UidGrantedPermissionState uidGrantedPermissionState = new com.android.server.am.AppPermissionTracker.UidGrantedPermissionState(i2, (java.lang.String) pair.first, i);
                    synchronized (this.mLock) {
                        handlePermissionsChangedLocked(i2, new com.android.server.am.AppPermissionTracker.UidGrantedPermissionState[]{uidGrantedPermissionState});
                    }
                    return;
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handlePermissionsChanged(int i) {
        android.util.Pair[] bgPermissionsInMonitor = ((com.android.server.am.AppPermissionTracker.AppPermissionPolicy) this.mInjector.getPolicy()).getBgPermissionsInMonitor();
        if (bgPermissionsInMonitor != null && bgPermissionsInMonitor.length > 0) {
            this.mInjector.getPermissionManagerServiceInternal();
            com.android.server.am.AppPermissionTracker.UidGrantedPermissionState[] uidGrantedPermissionStateArr = new com.android.server.am.AppPermissionTracker.UidGrantedPermissionState[bgPermissionsInMonitor.length];
            for (int i2 = 0; i2 < bgPermissionsInMonitor.length; i2++) {
                android.util.Pair pair = bgPermissionsInMonitor[i2];
                uidGrantedPermissionStateArr[i2] = new com.android.server.am.AppPermissionTracker.UidGrantedPermissionState(i, (java.lang.String) pair.first, ((java.lang.Integer) pair.second).intValue());
            }
            synchronized (this.mLock) {
                handlePermissionsChangedLocked(i, uidGrantedPermissionStateArr);
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void handlePermissionsChangedLocked(int i, com.android.server.am.AppPermissionTracker.UidGrantedPermissionState[] uidGrantedPermissionStateArr) {
        int indexOfKey = this.mUidGrantedPermissionsInMonitor.indexOfKey(i);
        android.util.ArraySet<com.android.server.am.AppPermissionTracker.UidGrantedPermissionState> valueAt = indexOfKey >= 0 ? this.mUidGrantedPermissionsInMonitor.valueAt(indexOfKey) : null;
        long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
        for (int i2 = 0; i2 < uidGrantedPermissionStateArr.length; i2++) {
            boolean isGranted = uidGrantedPermissionStateArr[i2].isGranted();
            boolean z = true;
            if (isGranted) {
                if (valueAt != null) {
                    z = false;
                } else {
                    valueAt = new android.util.ArraySet<>();
                    this.mUidGrantedPermissionsInMonitor.put(i, valueAt);
                }
                valueAt.add(uidGrantedPermissionStateArr[i2]);
            } else if (valueAt != null && !valueAt.isEmpty() && valueAt.remove(uidGrantedPermissionStateArr[i2]) && valueAt.isEmpty()) {
                this.mUidGrantedPermissionsInMonitor.removeAt(indexOfKey);
            } else {
                z = false;
            }
            if (z) {
                notifyListenersOnStateChange(i, "", isGranted, elapsedRealtime, 16);
            }
        }
    }

    private class UidGrantedPermissionState {
        final int mAppOp;
        private boolean mAppOpAllowed;

        @android.annotation.Nullable
        final java.lang.String mPermission;
        private boolean mPermissionGranted;
        final int mUid;

        UidGrantedPermissionState(int i, @android.annotation.Nullable java.lang.String str, int i2) {
            this.mUid = i;
            this.mPermission = str;
            this.mAppOp = i2;
            updatePermissionState();
            updateAppOps();
        }

        void updatePermissionState() {
            if (android.text.TextUtils.isEmpty(this.mPermission)) {
                this.mPermissionGranted = true;
            } else {
                this.mPermissionGranted = com.android.server.am.AppPermissionTracker.this.mContext.checkPermission(this.mPermission, -1, this.mUid) == 0;
            }
        }

        void updateAppOps() {
            if (this.mAppOp == -1) {
                this.mAppOpAllowed = true;
                return;
            }
            java.lang.String[] packagesForUid = com.android.server.am.AppPermissionTracker.this.mInjector.getPackageManager().getPackagesForUid(this.mUid);
            if (packagesForUid != null) {
                com.android.internal.app.IAppOpsService iAppOpsService = com.android.server.am.AppPermissionTracker.this.mInjector.getIAppOpsService();
                for (java.lang.String str : packagesForUid) {
                    if (iAppOpsService.checkOperation(this.mAppOp, this.mUid, str) != 0) {
                        continue;
                    } else {
                        this.mAppOpAllowed = true;
                        return;
                    }
                }
            }
            this.mAppOpAllowed = false;
        }

        boolean isGranted() {
            return this.mPermissionGranted && this.mAppOpAllowed;
        }

        public boolean equals(java.lang.Object obj) {
            if (obj == null || !(obj instanceof com.android.server.am.AppPermissionTracker.UidGrantedPermissionState)) {
                return false;
            }
            com.android.server.am.AppPermissionTracker.UidGrantedPermissionState uidGrantedPermissionState = (com.android.server.am.AppPermissionTracker.UidGrantedPermissionState) obj;
            return this.mUid == uidGrantedPermissionState.mUid && this.mAppOp == uidGrantedPermissionState.mAppOp && java.util.Objects.equals(this.mPermission, uidGrantedPermissionState.mPermission);
        }

        public int hashCode() {
            return (((java.lang.Integer.hashCode(this.mUid) * 31) + java.lang.Integer.hashCode(this.mAppOp)) * 31) + (this.mPermission == null ? 0 : this.mPermission.hashCode());
        }

        public java.lang.String toString() {
            java.lang.String str = "UidGrantedPermissionState{" + java.lang.System.identityHashCode(this) + " " + android.os.UserHandle.formatUid(this.mUid) + ": ";
            boolean isEmpty = android.text.TextUtils.isEmpty(this.mPermission);
            if (!isEmpty) {
                str = str + this.mPermission + "=" + this.mPermissionGranted;
            }
            if (this.mAppOp != -1) {
                if (!isEmpty) {
                    str = str + ",";
                }
                str = str + android.app.AppOpsManager.opToPublicName(this.mAppOp) + "=" + this.mAppOpAllowed;
            }
            return str + "}";
        }
    }

    private void startWatchingMode(@android.annotation.NonNull java.lang.Integer[] numArr) {
        synchronized (this.mAppOpsCallbacks) {
            stopWatchingMode();
            com.android.internal.app.IAppOpsService iAppOpsService = this.mInjector.getIAppOpsService();
            try {
                for (java.lang.Integer num : numArr) {
                    int intValue = num.intValue();
                    com.android.server.am.AppPermissionTracker.MyAppOpsCallback myAppOpsCallback = new com.android.server.am.AppPermissionTracker.MyAppOpsCallback();
                    this.mAppOpsCallbacks.put(intValue, myAppOpsCallback);
                    iAppOpsService.startWatchingModeWithFlags(intValue, (java.lang.String) null, 1, myAppOpsCallback);
                }
            } catch (android.os.RemoteException e) {
            }
        }
    }

    private void stopWatchingMode() {
        synchronized (this.mAppOpsCallbacks) {
            com.android.internal.app.IAppOpsService iAppOpsService = this.mInjector.getIAppOpsService();
            for (int size = this.mAppOpsCallbacks.size() - 1; size >= 0; size--) {
                try {
                    iAppOpsService.stopWatchingMode(this.mAppOpsCallbacks.valueAt(size));
                } catch (android.os.RemoteException e) {
                }
            }
            this.mAppOpsCallbacks.clear();
        }
    }

    private class MyAppOpsCallback extends com.android.internal.app.IAppOpsCallback.Stub {
        private MyAppOpsCallback() {
        }

        public void opChanged(int i, int i2, java.lang.String str, java.lang.String str2) {
            com.android.server.am.AppPermissionTracker.this.mHandler.obtainMessage(3, i, i2, str).sendToTarget();
        }
    }

    private static class MyHandler extends android.os.Handler {
        static final int MSG_APPOPS_CHANGED = 3;
        static final int MSG_PERMISSIONS_CHANGED = 2;
        static final int MSG_PERMISSIONS_DESTROY = 1;
        static final int MSG_PERMISSIONS_INIT = 0;

        @android.annotation.NonNull
        private com.android.server.am.AppPermissionTracker mTracker;

        MyHandler(@android.annotation.NonNull com.android.server.am.AppPermissionTracker appPermissionTracker) {
            super(appPermissionTracker.mBgHandler.getLooper());
            this.mTracker = appPermissionTracker;
        }

        @Override // android.os.Handler
        public void handleMessage(android.os.Message message) {
            switch (message.what) {
                case 0:
                    this.mTracker.handleAppOpsInit();
                    this.mTracker.handlePermissionsInit();
                    break;
                case 1:
                    this.mTracker.handlePermissionsDestroy();
                    this.mTracker.handleAppOpsDestroy();
                    break;
                case 2:
                    this.mTracker.handlePermissionsChanged(message.arg1);
                    break;
                case 3:
                    this.mTracker.handleOpChanged(message.arg1, message.arg2, (java.lang.String) message.obj);
                    break;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onPermissionTrackerEnabled(boolean z) {
        if (!this.mLockedBootCompleted) {
            return;
        }
        android.permission.PermissionManager permissionManager = this.mInjector.getPermissionManager();
        if (z) {
            permissionManager.addOnPermissionsChangeListener(this);
            this.mHandler.obtainMessage(0).sendToTarget();
        } else {
            permissionManager.removeOnPermissionsChangeListener(this);
            this.mHandler.obtainMessage(1).sendToTarget();
        }
    }

    @Override // com.android.server.am.BaseAppStateTracker
    void onLockedBootCompleted() {
        this.mLockedBootCompleted = true;
        onPermissionTrackerEnabled(((com.android.server.am.AppPermissionTracker.AppPermissionPolicy) this.mInjector.getPolicy()).isEnabled());
    }

    @Override // com.android.server.am.BaseAppStateTracker
    void dump(java.io.PrintWriter printWriter, java.lang.String str) {
        android.util.Pair[] pairArr;
        java.lang.String str2;
        android.util.Pair[] pairArr2;
        java.lang.String str3;
        com.android.server.am.AppPermissionTracker appPermissionTracker = this;
        printWriter.print(str);
        printWriter.println("APP PERMISSIONS TRACKER:");
        android.util.Pair[] bgPermissionsInMonitor = ((com.android.server.am.AppPermissionTracker.AppPermissionPolicy) appPermissionTracker.mInjector.getPolicy()).getBgPermissionsInMonitor();
        java.lang.String str4 = "  " + str;
        java.lang.String str5 = "  " + str4;
        int length = bgPermissionsInMonitor.length;
        int i = 0;
        while (i < length) {
            android.util.Pair pair = bgPermissionsInMonitor[i];
            printWriter.print(str4);
            boolean isEmpty = android.text.TextUtils.isEmpty((java.lang.CharSequence) pair.first);
            if (!isEmpty) {
                printWriter.print((java.lang.String) pair.first);
            }
            if (((java.lang.Integer) pair.second).intValue() != -1) {
                if (!isEmpty) {
                    printWriter.print('+');
                }
                printWriter.print(android.app.AppOpsManager.opToPublicName(((java.lang.Integer) pair.second).intValue()));
            }
            printWriter.println(':');
            synchronized (appPermissionTracker.mLock) {
                try {
                    android.util.SparseArray<android.util.ArraySet<com.android.server.am.AppPermissionTracker.UidGrantedPermissionState>> sparseArray = appPermissionTracker.mUidGrantedPermissionsInMonitor;
                    printWriter.print(str5);
                    printWriter.print('[');
                    int size = sparseArray.size();
                    int i2 = 0;
                    boolean z = false;
                    while (i2 < size) {
                        android.util.ArraySet<com.android.server.am.AppPermissionTracker.UidGrantedPermissionState> valueAt = sparseArray.valueAt(i2);
                        int size2 = valueAt.size() - 1;
                        while (true) {
                            if (size2 < 0) {
                                pairArr2 = bgPermissionsInMonitor;
                                str3 = str4;
                                break;
                            }
                            com.android.server.am.AppPermissionTracker.UidGrantedPermissionState valueAt2 = valueAt.valueAt(size2);
                            pairArr2 = bgPermissionsInMonitor;
                            str3 = str4;
                            if (valueAt2.mAppOp != ((java.lang.Integer) pair.second).intValue() || !android.text.TextUtils.equals(valueAt2.mPermission, (java.lang.CharSequence) pair.first)) {
                                size2--;
                                bgPermissionsInMonitor = pairArr2;
                                str4 = str3;
                            } else {
                                if (z) {
                                    printWriter.print(',');
                                }
                                printWriter.print(android.os.UserHandle.formatUid(valueAt2.mUid));
                                z = true;
                            }
                        }
                        i2++;
                        bgPermissionsInMonitor = pairArr2;
                        str4 = str3;
                    }
                    pairArr = bgPermissionsInMonitor;
                    str2 = str4;
                    printWriter.println(']');
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
            i++;
            appPermissionTracker = this;
            bgPermissionsInMonitor = pairArr;
            str4 = str2;
        }
        super.dump(printWriter, str);
    }

    static final class AppPermissionPolicy extends com.android.server.am.BaseAppStatePolicy<com.android.server.am.AppPermissionTracker> {
        static final java.lang.String[] DEFAULT_BG_PERMISSIONS_IN_MONITOR = {"android.permission.ACCESS_FINE_LOCATION", "android:fine_location", "android.permission.CAMERA", "android:camera", "android.permission.RECORD_AUDIO", "android:record_audio"};
        static final boolean DEFAULT_BG_PERMISSION_MONITOR_ENABLED = true;
        static final java.lang.String KEY_BG_PERMISSIONS_IN_MONITOR = "bg_permission_in_monitor";
        static final java.lang.String KEY_BG_PERMISSION_MONITOR_ENABLED = "bg_permission_monitor_enabled";

        @android.annotation.NonNull
        volatile android.util.Pair[] mBgPermissionsInMonitor;

        AppPermissionPolicy(@android.annotation.NonNull com.android.server.am.BaseAppStateTracker.Injector injector, @android.annotation.NonNull com.android.server.am.AppPermissionTracker appPermissionTracker) {
            super(injector, appPermissionTracker, KEY_BG_PERMISSION_MONITOR_ENABLED, true);
            this.mBgPermissionsInMonitor = parsePermissionConfig(DEFAULT_BG_PERMISSIONS_IN_MONITOR);
        }

        @Override // com.android.server.am.BaseAppStatePolicy
        public void onSystemReady() {
            super.onSystemReady();
            updateBgPermissionsInMonitor();
        }

        @Override // com.android.server.am.BaseAppStatePolicy
        public void onPropertiesChanged(java.lang.String str) {
            char c;
            switch (str.hashCode()) {
                case -1888141258:
                    if (str.equals(KEY_BG_PERMISSIONS_IN_MONITOR)) {
                        c = 0;
                        break;
                    }
                default:
                    c = 65535;
                    break;
            }
            switch (c) {
                case 0:
                    updateBgPermissionsInMonitor();
                    break;
                default:
                    super.onPropertiesChanged(str);
                    break;
            }
        }

        android.util.Pair[] getBgPermissionsInMonitor() {
            return this.mBgPermissionsInMonitor;
        }

        @android.annotation.NonNull
        private android.util.Pair[] parsePermissionConfig(@android.annotation.NonNull java.lang.String[] strArr) {
            android.util.Pair[] pairArr = new android.util.Pair[strArr.length / 2];
            int i = 0;
            int i2 = 0;
            while (i < strArr.length) {
                try {
                    int i3 = i + 1;
                    pairArr[i2] = android.util.Pair.create(android.text.TextUtils.isEmpty(strArr[i]) ? null : strArr[i], java.lang.Integer.valueOf(android.text.TextUtils.isEmpty(strArr[i3]) ? -1 : android.app.AppOpsManager.strOpToOp(strArr[i3])));
                } catch (java.lang.Exception e) {
                }
                i += 2;
                i2++;
            }
            return pairArr;
        }

        private void updateBgPermissionsInMonitor() {
            java.lang.String string = android.provider.DeviceConfig.getString("activity_manager", KEY_BG_PERMISSIONS_IN_MONITOR, (java.lang.String) null);
            android.util.Pair[] parsePermissionConfig = parsePermissionConfig(string != null ? string.split(",") : DEFAULT_BG_PERMISSIONS_IN_MONITOR);
            if (!java.util.Arrays.equals(this.mBgPermissionsInMonitor, parsePermissionConfig)) {
                this.mBgPermissionsInMonitor = parsePermissionConfig;
                if (isEnabled()) {
                    onTrackerEnabled(false);
                    onTrackerEnabled(true);
                }
            }
        }

        @Override // com.android.server.am.BaseAppStatePolicy
        public void onTrackerEnabled(boolean z) {
            ((com.android.server.am.AppPermissionTracker) this.mTracker).onPermissionTrackerEnabled(z);
        }

        @Override // com.android.server.am.BaseAppStatePolicy
        void dump(java.io.PrintWriter printWriter, java.lang.String str) {
            printWriter.print(str);
            printWriter.println("APP PERMISSION TRACKER POLICY SETTINGS:");
            java.lang.String str2 = "  " + str;
            super.dump(printWriter, str2);
            printWriter.print(str2);
            printWriter.print(KEY_BG_PERMISSIONS_IN_MONITOR);
            printWriter.print('=');
            printWriter.print('[');
            for (int i = 0; i < this.mBgPermissionsInMonitor.length; i++) {
                if (i > 0) {
                    printWriter.print(',');
                }
                android.util.Pair pair = this.mBgPermissionsInMonitor[i];
                if (pair.first != null) {
                    printWriter.print((java.lang.String) pair.first);
                }
                printWriter.print(',');
                if (((java.lang.Integer) pair.second).intValue() != -1) {
                    printWriter.print(android.app.AppOpsManager.opToPublicName(((java.lang.Integer) pair.second).intValue()));
                }
            }
            printWriter.println(']');
        }
    }
}
