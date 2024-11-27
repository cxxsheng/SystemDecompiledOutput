package com.android.server.am;

/* loaded from: classes.dex */
final class AppFGSTracker extends com.android.server.am.BaseAppStateDurationsTracker<com.android.server.am.AppFGSTracker.AppFGSPolicy, com.android.server.am.AppFGSTracker.PackageDurations> implements android.app.ActivityManagerInternal.ForegroundServiceStateListener {
    static final boolean DEBUG_BACKGROUND_FGS_TRACKER = false;
    static final java.lang.String TAG = "ActivityManager";

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final com.android.server.am.UidProcessMap<android.util.SparseBooleanArray> mFGSNotificationIDs;
    private final com.android.server.am.AppFGSTracker.MyHandler mHandler;

    @com.android.internal.annotations.VisibleForTesting
    final com.android.server.am.AppFGSTracker.NotificationListener mNotificationListener;
    final android.app.IProcessObserver.Stub mProcessObserver;
    private final android.util.ArrayMap<com.android.server.am.AppFGSTracker.PackageDurations, java.lang.Long> mTmpPkgDurations;

    public void onForegroundServiceStateChanged(java.lang.String str, int i, int i2, boolean z) {
        this.mHandler.obtainMessage(z ? 0 : 1, i2, i, str).sendToTarget();
    }

    public void onForegroundServiceNotificationUpdated(java.lang.String str, int i, int i2, boolean z) {
        com.android.internal.os.SomeArgs obtain = com.android.internal.os.SomeArgs.obtain();
        obtain.argi1 = i;
        obtain.argi2 = i2;
        obtain.arg1 = str;
        obtain.arg2 = z ? java.lang.Boolean.TRUE : java.lang.Boolean.FALSE;
        this.mHandler.obtainMessage(3, obtain).sendToTarget();
    }

    private static class MyHandler extends android.os.Handler {
        static final int MSG_CHECK_LONG_RUNNING_FGS = 4;
        static final int MSG_FOREGROUND_SERVICES_CHANGED = 2;
        static final int MSG_FOREGROUND_SERVICES_NOTIFICATION_UPDATED = 3;
        static final int MSG_FOREGROUND_SERVICES_STARTED = 0;
        static final int MSG_FOREGROUND_SERVICES_STOPPED = 1;
        static final int MSG_NOTIFICATION_POSTED = 5;
        static final int MSG_NOTIFICATION_REMOVED = 6;
        private final com.android.server.am.AppFGSTracker mTracker;

        MyHandler(com.android.server.am.AppFGSTracker appFGSTracker) {
            super(appFGSTracker.mBgHandler.getLooper());
            this.mTracker = appFGSTracker;
        }

        @Override // android.os.Handler
        public void handleMessage(android.os.Message message) {
            switch (message.what) {
                case 0:
                    this.mTracker.handleForegroundServicesChanged((java.lang.String) message.obj, message.arg1, message.arg2, true);
                    break;
                case 1:
                    this.mTracker.handleForegroundServicesChanged((java.lang.String) message.obj, message.arg1, message.arg2, false);
                    break;
                case 2:
                    this.mTracker.handleForegroundServicesChanged((java.lang.String) message.obj, message.arg1, message.arg2);
                    break;
                case 3:
                    com.android.internal.os.SomeArgs someArgs = (com.android.internal.os.SomeArgs) message.obj;
                    this.mTracker.handleForegroundServiceNotificationUpdated((java.lang.String) someArgs.arg1, someArgs.argi1, someArgs.argi2, ((java.lang.Boolean) someArgs.arg2).booleanValue());
                    someArgs.recycle();
                    break;
                case 4:
                    this.mTracker.checkLongRunningFgs();
                    break;
                case 5:
                    this.mTracker.handleNotificationPosted((java.lang.String) message.obj, message.arg1, message.arg2);
                    break;
                case 6:
                    this.mTracker.handleNotificationRemoved((java.lang.String) message.obj, message.arg1, message.arg2);
                    break;
            }
        }
    }

    AppFGSTracker(android.content.Context context, com.android.server.am.AppRestrictionController appRestrictionController) {
        this(context, appRestrictionController, null, null);
    }

    AppFGSTracker(android.content.Context context, com.android.server.am.AppRestrictionController appRestrictionController, java.lang.reflect.Constructor<? extends com.android.server.am.BaseAppStateTracker.Injector<com.android.server.am.AppFGSTracker.AppFGSPolicy>> constructor, java.lang.Object obj) {
        super(context, appRestrictionController, constructor, obj);
        this.mFGSNotificationIDs = new com.android.server.am.UidProcessMap<>();
        this.mTmpPkgDurations = new android.util.ArrayMap<>();
        this.mNotificationListener = new com.android.server.am.AppFGSTracker.NotificationListener();
        this.mProcessObserver = new android.app.IProcessObserver.Stub() { // from class: com.android.server.am.AppFGSTracker.1
            public void onForegroundActivitiesChanged(int i, int i2, boolean z) {
            }

            public void onForegroundServicesChanged(int i, int i2, int i3) {
                java.lang.String packageName = com.android.server.am.AppFGSTracker.this.mAppRestrictionController.getPackageName(i);
                if (packageName != null) {
                    com.android.server.am.AppFGSTracker.this.mHandler.obtainMessage(2, i2, i3, packageName).sendToTarget();
                }
            }

            public void onProcessStarted(int i, int i2, int i3, java.lang.String str, java.lang.String str2) {
            }

            public void onProcessDied(int i, int i2) {
            }
        };
        this.mHandler = new com.android.server.am.AppFGSTracker.MyHandler(this);
        this.mInjector.setPolicy(new com.android.server.am.AppFGSTracker.AppFGSPolicy(this.mInjector, this));
    }

    @Override // com.android.server.am.BaseAppStateTracker
    @com.android.server.am.AppRestrictionController.TrackerType
    int getType() {
        return 3;
    }

    @Override // com.android.server.am.BaseAppStateTracker
    void onSystemReady() {
        super.onSystemReady();
        this.mInjector.getActivityManagerInternal().addForegroundServiceStateListener(this);
        this.mInjector.getActivityManagerInternal().registerProcessObserver(this.mProcessObserver);
    }

    @Override // com.android.server.am.BaseAppStateDurationsTracker, com.android.server.am.BaseAppStateEventsTracker
    @com.android.internal.annotations.VisibleForTesting
    void reset() {
        this.mHandler.removeMessages(4);
        super.reset();
    }

    @Override // com.android.server.am.BaseAppStateEvents.Factory
    public com.android.server.am.AppFGSTracker.PackageDurations createAppStateEvents(int i, java.lang.String str) {
        return new com.android.server.am.AppFGSTracker.PackageDurations(i, str, (com.android.server.am.BaseAppStateEvents.MaxTrackingDurationConfig) this.mInjector.getPolicy(), this);
    }

    @Override // com.android.server.am.BaseAppStateEvents.Factory
    public com.android.server.am.AppFGSTracker.PackageDurations createAppStateEvents(com.android.server.am.AppFGSTracker.PackageDurations packageDurations) {
        return new com.android.server.am.AppFGSTracker.PackageDurations(packageDurations);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleForegroundServicesChanged(java.lang.String str, int i, int i2, boolean z) {
        boolean z2;
        if (!((com.android.server.am.AppFGSTracker.AppFGSPolicy) this.mInjector.getPolicy()).isEnabled()) {
            return;
        }
        long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
        int shouldExemptUid = ((com.android.server.am.AppFGSTracker.AppFGSPolicy) this.mInjector.getPolicy()).shouldExemptUid(i2);
        synchronized (this.mLock) {
            try {
                com.android.server.am.AppFGSTracker.PackageDurations packageDurations = (com.android.server.am.AppFGSTracker.PackageDurations) this.mPkgEvents.get(i2, str);
                if (packageDurations == null) {
                    packageDurations = createAppStateEvents(i2, str);
                    this.mPkgEvents.put(i2, str, packageDurations);
                }
                boolean isLongRunning = packageDurations.isLongRunning();
                packageDurations.addEvent(z, elapsedRealtime);
                z2 = isLongRunning && !packageDurations.hasForegroundServices();
                if (z2) {
                    packageDurations.setIsLongRunning(false);
                }
                packageDurations.mExemptReason = shouldExemptUid;
                scheduleDurationCheckLocked(elapsedRealtime);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        if (z2) {
            ((com.android.server.am.AppFGSTracker.AppFGSPolicy) this.mInjector.getPolicy()).onLongRunningFgsGone(str, i2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleForegroundServiceNotificationUpdated(java.lang.String str, int i, int i2, boolean z) {
        int indexOfKey;
        synchronized (this.mLock) {
            try {
                android.util.SparseBooleanArray sparseBooleanArray = this.mFGSNotificationIDs.get(i, str);
                if (!z) {
                    if (sparseBooleanArray == null) {
                        sparseBooleanArray = new android.util.SparseBooleanArray();
                        this.mFGSNotificationIDs.put(i, str, sparseBooleanArray);
                    }
                    sparseBooleanArray.put(i2, false);
                } else if (sparseBooleanArray != null && (indexOfKey = sparseBooleanArray.indexOfKey(i2)) >= 0) {
                    boolean valueAt = sparseBooleanArray.valueAt(indexOfKey);
                    sparseBooleanArray.removeAt(indexOfKey);
                    if (sparseBooleanArray.size() == 0) {
                        this.mFGSNotificationIDs.remove(i, str);
                    }
                    for (int size = sparseBooleanArray.size() - 1; size >= 0; size--) {
                        if (sparseBooleanArray.valueAt(size)) {
                            return;
                        }
                    }
                    if (valueAt) {
                        notifyListenersOnStateChange(i, str, false, android.os.SystemClock.elapsedRealtime(), 8);
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean hasForegroundServiceNotificationsLocked(java.lang.String str, int i) {
        android.util.SparseBooleanArray sparseBooleanArray = this.mFGSNotificationIDs.get(i, str);
        if (sparseBooleanArray == null || sparseBooleanArray.size() == 0) {
            return false;
        }
        for (int size = sparseBooleanArray.size() - 1; size >= 0; size--) {
            if (sparseBooleanArray.valueAt(size)) {
                return true;
            }
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleNotificationPosted(java.lang.String str, int i, int i2) {
        int indexOfKey;
        boolean z;
        synchronized (this.mLock) {
            try {
                android.util.SparseBooleanArray sparseBooleanArray = this.mFGSNotificationIDs.get(i, str);
                if (sparseBooleanArray != null && (indexOfKey = sparseBooleanArray.indexOfKey(i2)) >= 0) {
                    if (sparseBooleanArray.valueAt(indexOfKey)) {
                        return;
                    }
                    int size = sparseBooleanArray.size() - 1;
                    while (true) {
                        if (size < 0) {
                            z = false;
                            break;
                        } else if (!sparseBooleanArray.valueAt(size)) {
                            size--;
                        } else {
                            z = true;
                            break;
                        }
                    }
                    sparseBooleanArray.setValueAt(indexOfKey, true);
                    if (!z) {
                        notifyListenersOnStateChange(i, str, true, android.os.SystemClock.elapsedRealtime(), 8);
                    }
                }
            } finally {
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleNotificationRemoved(java.lang.String str, int i, int i2) {
        int indexOfKey;
        synchronized (this.mLock) {
            try {
                android.util.SparseBooleanArray sparseBooleanArray = this.mFGSNotificationIDs.get(i, str);
                if (sparseBooleanArray != null && (indexOfKey = sparseBooleanArray.indexOfKey(i2)) >= 0) {
                    if (sparseBooleanArray.valueAt(indexOfKey)) {
                        sparseBooleanArray.setValueAt(indexOfKey, false);
                        for (int size = sparseBooleanArray.size() - 1; size >= 0; size--) {
                            if (sparseBooleanArray.valueAt(size)) {
                                return;
                            }
                        }
                        notifyListenersOnStateChange(i, str, false, android.os.SystemClock.elapsedRealtime(), 8);
                    }
                }
            } finally {
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void scheduleDurationCheckLocked(long j) {
        android.util.SparseArray map = this.mPkgEvents.getMap();
        long j2 = -1;
        for (int size = map.size() - 1; size >= 0; size--) {
            android.util.ArrayMap arrayMap = (android.util.ArrayMap) map.valueAt(size);
            for (int size2 = arrayMap.size() - 1; size2 >= 0; size2--) {
                com.android.server.am.AppFGSTracker.PackageDurations packageDurations = (com.android.server.am.AppFGSTracker.PackageDurations) arrayMap.valueAt(size2);
                if (packageDurations.hasForegroundServices() && !packageDurations.isLongRunning()) {
                    j2 = java.lang.Math.max(getTotalDurations(packageDurations, j), j2);
                }
            }
        }
        this.mHandler.removeMessages(4);
        if (j2 >= 0) {
            this.mHandler.sendEmptyMessageDelayed(4, this.mInjector.getServiceStartForegroundTimeout() + java.lang.Math.max(0L, ((com.android.server.am.AppFGSTracker.AppFGSPolicy) this.mInjector.getPolicy()).getFgsLongRunningThreshold() - j2));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void checkLongRunningFgs() {
        com.android.server.am.AppFGSTracker.AppFGSPolicy appFGSPolicy = (com.android.server.am.AppFGSTracker.AppFGSPolicy) this.mInjector.getPolicy();
        final android.util.ArrayMap<com.android.server.am.AppFGSTracker.PackageDurations, java.lang.Long> arrayMap = this.mTmpPkgDurations;
        long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
        long fgsLongRunningThreshold = appFGSPolicy.getFgsLongRunningThreshold();
        long max = java.lang.Math.max(0L, elapsedRealtime - appFGSPolicy.getFgsLongRunningWindowSize());
        synchronized (this.mLock) {
            try {
                android.util.SparseArray map = this.mPkgEvents.getMap();
                int i = 1;
                int size = map.size() - 1;
                while (size >= 0) {
                    android.util.ArrayMap arrayMap2 = (android.util.ArrayMap) map.valueAt(size);
                    for (int size2 = arrayMap2.size() - i; size2 >= 0; size2--) {
                        com.android.server.am.AppFGSTracker.PackageDurations packageDurations = (com.android.server.am.AppFGSTracker.PackageDurations) arrayMap2.valueAt(size2);
                        if (packageDurations.hasForegroundServices() && !packageDurations.isLongRunning()) {
                            long totalDurations = getTotalDurations(packageDurations, elapsedRealtime);
                            if (totalDurations >= fgsLongRunningThreshold) {
                                arrayMap.put(packageDurations, java.lang.Long.valueOf(totalDurations));
                                packageDurations.setIsLongRunning(true);
                            }
                        }
                    }
                    size--;
                    i = 1;
                }
                trim(max);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        int size3 = arrayMap.size();
        if (size3 > 0) {
            java.lang.Integer[] numArr = new java.lang.Integer[size3];
            for (int i2 = 0; i2 < size3; i2++) {
                numArr[i2] = java.lang.Integer.valueOf(i2);
            }
            java.util.Arrays.sort(numArr, new java.util.Comparator() { // from class: com.android.server.am.AppFGSTracker$$ExternalSyntheticLambda0
                @Override // java.util.Comparator
                public final int compare(java.lang.Object obj, java.lang.Object obj2) {
                    int lambda$checkLongRunningFgs$0;
                    lambda$checkLongRunningFgs$0 = com.android.server.am.AppFGSTracker.lambda$checkLongRunningFgs$0(arrayMap, (java.lang.Integer) obj, (java.lang.Integer) obj2);
                    return lambda$checkLongRunningFgs$0;
                }
            });
            for (int i3 = size3 - 1; i3 >= 0; i3--) {
                com.android.server.am.AppFGSTracker.PackageDurations keyAt = arrayMap.keyAt(numArr[i3].intValue());
                appFGSPolicy.onLongRunningFgs(keyAt.mPackageName, keyAt.mUid, keyAt.mExemptReason);
            }
            arrayMap.clear();
        }
        synchronized (this.mLock) {
            scheduleDurationCheckLocked(elapsedRealtime);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ int lambda$checkLongRunningFgs$0(android.util.ArrayMap arrayMap, java.lang.Integer num, java.lang.Integer num2) {
        return java.lang.Long.compare(((java.lang.Long) arrayMap.valueAt(num.intValue())).longValue(), ((java.lang.Long) arrayMap.valueAt(num2.intValue())).longValue());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleForegroundServicesChanged(java.lang.String str, int i, int i2) {
        if (!((com.android.server.am.AppFGSTracker.AppFGSPolicy) this.mInjector.getPolicy()).isEnabled()) {
            return;
        }
        int shouldExemptUid = ((com.android.server.am.AppFGSTracker.AppFGSPolicy) this.mInjector.getPolicy()).shouldExemptUid(i);
        long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
        synchronized (this.mLock) {
            try {
                com.android.server.am.AppFGSTracker.PackageDurations packageDurations = (com.android.server.am.AppFGSTracker.PackageDurations) this.mPkgEvents.get(i, str);
                if (packageDurations == null) {
                    packageDurations = new com.android.server.am.AppFGSTracker.PackageDurations(i, str, (com.android.server.am.BaseAppStateEvents.MaxTrackingDurationConfig) this.mInjector.getPolicy(), this);
                    this.mPkgEvents.put(i, str, packageDurations);
                }
                packageDurations.setForegroundServiceType(i2, elapsedRealtime);
                packageDurations.mExemptReason = shouldExemptUid;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onBgFgsMonitorEnabled(boolean z) {
        if (z) {
            synchronized (this.mLock) {
                scheduleDurationCheckLocked(android.os.SystemClock.elapsedRealtime());
            }
            try {
                this.mNotificationListener.registerAsSystemService(this.mContext, new android.content.ComponentName(this.mContext, (java.lang.Class<?>) com.android.server.am.AppFGSTracker.NotificationListener.class), -1);
                return;
            } catch (android.os.RemoteException e) {
                return;
            }
        }
        try {
            this.mNotificationListener.unregisterAsSystemService();
        } catch (android.os.RemoteException e2) {
        }
        this.mHandler.removeMessages(4);
        synchronized (this.mLock) {
            this.mPkgEvents.clear();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onBgFgsLongRunningThresholdChanged() {
        synchronized (this.mLock) {
            try {
                if (((com.android.server.am.AppFGSTracker.AppFGSPolicy) this.mInjector.getPolicy()).isEnabled()) {
                    scheduleDurationCheckLocked(android.os.SystemClock.elapsedRealtime());
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    static int foregroundServiceTypeToIndex(int i) {
        if (i == 0) {
            return 0;
        }
        return java.lang.Integer.numberOfTrailingZeros(i) + 1;
    }

    static int indexToForegroundServiceType(int i) {
        if (i == com.android.server.am.AppFGSTracker.PackageDurations.DEFAULT_INDEX) {
            return 0;
        }
        return 1 << (i - 1);
    }

    long getTotalDurations(com.android.server.am.AppFGSTracker.PackageDurations packageDurations, long j) {
        return getTotalDurations(packageDurations.mPackageName, packageDurations.mUid, j, foregroundServiceTypeToIndex(0));
    }

    @Override // com.android.server.am.BaseAppStateDurationsTracker
    long getTotalDurations(int i, long j) {
        return getTotalDurations(i, j, foregroundServiceTypeToIndex(0));
    }

    boolean hasForegroundServices(java.lang.String str, int i) {
        boolean z;
        synchronized (this.mLock) {
            try {
                com.android.server.am.AppFGSTracker.PackageDurations packageDurations = (com.android.server.am.AppFGSTracker.PackageDurations) this.mPkgEvents.get(i, str);
                z = packageDurations != null && packageDurations.hasForegroundServices();
            } finally {
            }
        }
        return z;
    }

    boolean hasForegroundServices(int i) {
        synchronized (this.mLock) {
            try {
                android.util.ArrayMap arrayMap = (android.util.ArrayMap) this.mPkgEvents.getMap().get(i);
                if (arrayMap != null) {
                    for (int size = arrayMap.size() - 1; size >= 0; size--) {
                        if (((com.android.server.am.AppFGSTracker.PackageDurations) arrayMap.valueAt(size)).hasForegroundServices()) {
                            return true;
                        }
                    }
                }
                return false;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    boolean hasForegroundServiceNotifications(java.lang.String str, int i) {
        boolean hasForegroundServiceNotificationsLocked;
        synchronized (this.mLock) {
            hasForegroundServiceNotificationsLocked = hasForegroundServiceNotificationsLocked(str, i);
        }
        return hasForegroundServiceNotificationsLocked;
    }

    boolean hasForegroundServiceNotifications(int i) {
        synchronized (this.mLock) {
            try {
                android.util.ArrayMap<java.lang.String, android.util.SparseBooleanArray> arrayMap = this.mFGSNotificationIDs.getMap().get(i);
                if (arrayMap != null) {
                    for (int size = arrayMap.size() - 1; size >= 0; size--) {
                        if (hasForegroundServiceNotificationsLocked(arrayMap.keyAt(size), i)) {
                            return true;
                        }
                    }
                }
                return false;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.server.am.BaseAppStateTracker
    byte[] getTrackerInfoForStatsd(int i) {
        long totalDurations = getTotalDurations(i, android.os.SystemClock.elapsedRealtime());
        if (totalDurations == 0) {
            return null;
        }
        android.util.proto.ProtoOutputStream protoOutputStream = new android.util.proto.ProtoOutputStream();
        protoOutputStream.write(1133871366145L, hasForegroundServiceNotifications(i));
        protoOutputStream.write(1112396529666L, totalDurations);
        protoOutputStream.flush();
        return protoOutputStream.getBytes();
    }

    @Override // com.android.server.am.BaseAppStateEventsTracker, com.android.server.am.BaseAppStateTracker
    void dump(java.io.PrintWriter printWriter, java.lang.String str) {
        printWriter.print(str);
        printWriter.println("APP FOREGROUND SERVICE TRACKER:");
        super.dump(printWriter, "  " + str);
    }

    @Override // com.android.server.am.BaseAppStateEventsTracker
    void dumpOthers(java.io.PrintWriter printWriter, java.lang.String str) {
        printWriter.print(str);
        printWriter.println("APPS WITH ACTIVE FOREGROUND SERVICES:");
        java.lang.String str2 = "  " + str;
        synchronized (this.mLock) {
            try {
                android.util.SparseArray<android.util.ArrayMap<java.lang.String, android.util.SparseBooleanArray>> map = this.mFGSNotificationIDs.getMap();
                if (map.size() == 0) {
                    printWriter.print(str2);
                    printWriter.println("(none)");
                }
                int size = map.size();
                for (int i = 0; i < size; i++) {
                    int keyAt = map.keyAt(i);
                    java.lang.String formatUid = android.os.UserHandle.formatUid(keyAt);
                    android.util.ArrayMap<java.lang.String, android.util.SparseBooleanArray> valueAt = map.valueAt(i);
                    int size2 = valueAt.size();
                    for (int i2 = 0; i2 < size2; i2++) {
                        java.lang.String keyAt2 = valueAt.keyAt(i2);
                        printWriter.print(str2);
                        printWriter.print(keyAt2);
                        printWriter.print('/');
                        printWriter.print(formatUid);
                        printWriter.print(" notification=");
                        printWriter.println(hasForegroundServiceNotificationsLocked(keyAt2, keyAt));
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    static class PackageDurations extends com.android.server.am.BaseAppStateDurations<com.android.server.am.BaseAppStateTimeEvents.BaseTimeEvent> {
        static final int DEFAULT_INDEX = com.android.server.am.AppFGSTracker.foregroundServiceTypeToIndex(0);
        private int mForegroundServiceTypes;
        private boolean mIsLongRunning;
        private final com.android.server.am.AppFGSTracker mTracker;

        /* JADX WARN: Multi-variable type inference failed */
        PackageDurations(int i, java.lang.String str, com.android.server.am.BaseAppStateEvents.MaxTrackingDurationConfig maxTrackingDurationConfig, com.android.server.am.AppFGSTracker appFGSTracker) {
            super(i, str, 31, com.android.server.am.AppFGSTracker.TAG, maxTrackingDurationConfig);
            this.mEvents[DEFAULT_INDEX] = new java.util.LinkedList();
            this.mTracker = appFGSTracker;
        }

        PackageDurations(@android.annotation.NonNull com.android.server.am.AppFGSTracker.PackageDurations packageDurations) {
            super(packageDurations);
            this.mIsLongRunning = packageDurations.mIsLongRunning;
            this.mForegroundServiceTypes = packageDurations.mForegroundServiceTypes;
            this.mTracker = packageDurations.mTracker;
        }

        void addEvent(boolean z, long j) {
            addEvent(z, (boolean) new com.android.server.am.BaseAppStateTimeEvents.BaseTimeEvent(j), DEFAULT_INDEX);
            if (!z && !hasForegroundServices()) {
                this.mIsLongRunning = false;
            }
            if (!z && this.mForegroundServiceTypes != 0) {
                for (int i = 1; i < this.mEvents.length; i++) {
                    if (this.mEvents[i] != null && isActive(i)) {
                        this.mEvents[i].add(new com.android.server.am.BaseAppStateTimeEvents.BaseTimeEvent(j));
                        notifyListenersOnStateChangeIfNecessary(false, j, com.android.server.am.AppFGSTracker.indexToForegroundServiceType(i));
                    }
                }
                this.mForegroundServiceTypes = 0;
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        void setForegroundServiceType(int i, long j) {
            if (i == this.mForegroundServiceTypes || !hasForegroundServices()) {
                return;
            }
            int i2 = this.mForegroundServiceTypes ^ i;
            int highestOneBit = java.lang.Integer.highestOneBit(i2);
            while (highestOneBit != 0) {
                int foregroundServiceTypeToIndex = com.android.server.am.AppFGSTracker.foregroundServiceTypeToIndex(highestOneBit);
                if (foregroundServiceTypeToIndex < this.mEvents.length) {
                    if ((i & highestOneBit) != 0) {
                        if (this.mEvents[foregroundServiceTypeToIndex] == null) {
                            this.mEvents[foregroundServiceTypeToIndex] = new java.util.LinkedList();
                        }
                        if (!isActive(foregroundServiceTypeToIndex)) {
                            this.mEvents[foregroundServiceTypeToIndex].add(new com.android.server.am.BaseAppStateTimeEvents.BaseTimeEvent(j));
                            notifyListenersOnStateChangeIfNecessary(true, j, highestOneBit);
                        }
                    } else if (this.mEvents[foregroundServiceTypeToIndex] != null && isActive(foregroundServiceTypeToIndex)) {
                        this.mEvents[foregroundServiceTypeToIndex].add(new com.android.server.am.BaseAppStateTimeEvents.BaseTimeEvent(j));
                        notifyListenersOnStateChangeIfNecessary(false, j, highestOneBit);
                    }
                }
                i2 &= ~highestOneBit;
                highestOneBit = java.lang.Integer.highestOneBit(i2);
            }
            this.mForegroundServiceTypes = i;
        }

        private void notifyListenersOnStateChangeIfNecessary(boolean z, long j, int i) {
            int i2;
            switch (i) {
                case 2:
                    i2 = 2;
                    break;
                case 8:
                    i2 = 4;
                    break;
                default:
                    return;
            }
            this.mTracker.notifyListenersOnStateChange(this.mUid, this.mPackageName, z, j, i2);
        }

        void setIsLongRunning(boolean z) {
            this.mIsLongRunning = z;
        }

        boolean isLongRunning() {
            return this.mIsLongRunning;
        }

        boolean hasForegroundServices() {
            return isActive(DEFAULT_INDEX);
        }

        @Override // com.android.server.am.BaseAppStateEvents
        java.lang.String formatEventTypeLabel(int i) {
            if (i == DEFAULT_INDEX) {
                return "Overall foreground services: ";
            }
            return android.content.pm.ServiceInfo.foregroundServiceTypeToLabel(com.android.server.am.AppFGSTracker.indexToForegroundServiceType(i)) + ": ";
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    class NotificationListener extends android.service.notification.NotificationListenerService {
        NotificationListener() {
        }

        @Override // android.service.notification.NotificationListenerService
        public void onNotificationPosted(android.service.notification.StatusBarNotification statusBarNotification, android.service.notification.NotificationListenerService.RankingMap rankingMap) {
            com.android.server.am.AppFGSTracker.this.mHandler.obtainMessage(5, statusBarNotification.getUid(), statusBarNotification.getId(), statusBarNotification.getPackageName()).sendToTarget();
        }

        @Override // android.service.notification.NotificationListenerService
        public void onNotificationRemoved(android.service.notification.StatusBarNotification statusBarNotification, android.service.notification.NotificationListenerService.RankingMap rankingMap, int i) {
            com.android.server.am.AppFGSTracker.this.mHandler.obtainMessage(6, statusBarNotification.getUid(), statusBarNotification.getId(), statusBarNotification.getPackageName()).sendToTarget();
        }
    }

    static final class AppFGSPolicy extends com.android.server.am.BaseAppStateEventsTracker.BaseAppStateEventsPolicy<com.android.server.am.AppFGSTracker> {
        static final long DEFAULT_BG_FGS_LOCATION_THRESHOLD = 14400000;
        static final long DEFAULT_BG_FGS_LONG_RUNNING_THRESHOLD = 72000000;
        static final long DEFAULT_BG_FGS_LONG_RUNNING_WINDOW = 86400000;
        static final long DEFAULT_BG_FGS_MEDIA_PLAYBACK_THRESHOLD = 14400000;
        static final boolean DEFAULT_BG_FGS_MONITOR_ENABLED = true;
        static final java.lang.String KEY_BG_FGS_LOCATION_THRESHOLD = "bg_fgs_location_threshold";
        static final java.lang.String KEY_BG_FGS_LONG_RUNNING_THRESHOLD = "bg_fgs_long_running_threshold";
        static final java.lang.String KEY_BG_FGS_LONG_RUNNING_WINDOW = "bg_fgs_long_running_window";
        static final java.lang.String KEY_BG_FGS_MEDIA_PLAYBACK_THRESHOLD = "bg_fgs_media_playback_threshold";
        static final java.lang.String KEY_BG_FGS_MONITOR_ENABLED = "bg_fgs_monitor_enabled";
        private volatile long mBgFgsLocationThresholdMs;
        private volatile long mBgFgsLongRunningThresholdMs;
        private volatile long mBgFgsMediaPlaybackThresholdMs;

        AppFGSPolicy(@android.annotation.NonNull com.android.server.am.BaseAppStateTracker.Injector injector, @android.annotation.NonNull com.android.server.am.AppFGSTracker appFGSTracker) {
            super(injector, appFGSTracker, KEY_BG_FGS_MONITOR_ENABLED, true, KEY_BG_FGS_LONG_RUNNING_WINDOW, 86400000L);
            this.mBgFgsLongRunningThresholdMs = DEFAULT_BG_FGS_LONG_RUNNING_THRESHOLD;
            this.mBgFgsMediaPlaybackThresholdMs = 14400000L;
            this.mBgFgsLocationThresholdMs = 14400000L;
        }

        @Override // com.android.server.am.BaseAppStateEventsTracker.BaseAppStateEventsPolicy, com.android.server.am.BaseAppStatePolicy
        public void onSystemReady() {
            super.onSystemReady();
            updateBgFgsLongRunningThreshold();
            updateBgFgsMediaPlaybackThreshold();
            updateBgFgsLocationThreshold();
        }

        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        @Override // com.android.server.am.BaseAppStateEventsTracker.BaseAppStateEventsPolicy, com.android.server.am.BaseAppStatePolicy
        public void onPropertiesChanged(java.lang.String str) {
            char c;
            switch (str.hashCode()) {
                case -2001687768:
                    if (str.equals(KEY_BG_FGS_LOCATION_THRESHOLD)) {
                        c = 2;
                        break;
                    }
                    c = 65535;
                    break;
                case 351955503:
                    if (str.equals(KEY_BG_FGS_LONG_RUNNING_THRESHOLD)) {
                        c = 0;
                        break;
                    }
                    c = 65535;
                    break;
                case 803245321:
                    if (str.equals(KEY_BG_FGS_MEDIA_PLAYBACK_THRESHOLD)) {
                        c = 1;
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
                    updateBgFgsLongRunningThreshold();
                    break;
                case 1:
                    updateBgFgsMediaPlaybackThreshold();
                    break;
                case 2:
                    updateBgFgsLocationThreshold();
                    break;
                default:
                    super.onPropertiesChanged(str);
                    break;
            }
        }

        @Override // com.android.server.am.BaseAppStatePolicy
        public void onTrackerEnabled(boolean z) {
            ((com.android.server.am.AppFGSTracker) this.mTracker).onBgFgsMonitorEnabled(z);
        }

        @Override // com.android.server.am.BaseAppStateEventsTracker.BaseAppStateEventsPolicy
        public void onMaxTrackingDurationChanged(long j) {
            ((com.android.server.am.AppFGSTracker) this.mTracker).onBgFgsLongRunningThresholdChanged();
        }

        private void updateBgFgsLongRunningThreshold() {
            long j = android.provider.DeviceConfig.getLong("activity_manager", KEY_BG_FGS_LONG_RUNNING_THRESHOLD, DEFAULT_BG_FGS_LONG_RUNNING_THRESHOLD);
            if (j != this.mBgFgsLongRunningThresholdMs) {
                this.mBgFgsLongRunningThresholdMs = j;
                ((com.android.server.am.AppFGSTracker) this.mTracker).onBgFgsLongRunningThresholdChanged();
            }
        }

        private void updateBgFgsMediaPlaybackThreshold() {
            this.mBgFgsMediaPlaybackThresholdMs = android.provider.DeviceConfig.getLong("activity_manager", KEY_BG_FGS_MEDIA_PLAYBACK_THRESHOLD, 14400000L);
        }

        private void updateBgFgsLocationThreshold() {
            this.mBgFgsLocationThresholdMs = android.provider.DeviceConfig.getLong("activity_manager", KEY_BG_FGS_LOCATION_THRESHOLD, 14400000L);
        }

        long getFgsLongRunningThreshold() {
            return this.mBgFgsLongRunningThresholdMs;
        }

        long getFgsLongRunningWindowSize() {
            return getMaxTrackingDuration();
        }

        long getFGSMediaPlaybackThreshold() {
            return this.mBgFgsMediaPlaybackThresholdMs;
        }

        long getLocationFGSThreshold() {
            return this.mBgFgsLocationThresholdMs;
        }

        void onLongRunningFgs(java.lang.String str, int i, int i2) {
            if (i2 != -1) {
                return;
            }
            long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
            long fgsLongRunningWindowSize = getFgsLongRunningWindowSize();
            long max = java.lang.Math.max(0L, elapsedRealtime - fgsLongRunningWindowSize);
            if (shouldExemptMediaPlaybackFGS(str, i, elapsedRealtime, fgsLongRunningWindowSize) || shouldExemptLocationFGS(str, i, elapsedRealtime, max)) {
                return;
            }
            ((com.android.server.am.AppFGSTracker) this.mTracker).mAppRestrictionController.postLongRunningFgsIfNecessary(str, i);
        }

        boolean shouldExemptMediaPlaybackFGS(java.lang.String str, int i, long j, long j2) {
            long compositeMediaPlaybackDurations = ((com.android.server.am.AppFGSTracker) this.mTracker).mAppRestrictionController.getCompositeMediaPlaybackDurations(str, i, j, j2);
            if (compositeMediaPlaybackDurations > 0 && compositeMediaPlaybackDurations >= getFGSMediaPlaybackThreshold()) {
                return true;
            }
            return false;
        }

        boolean shouldExemptLocationFGS(java.lang.String str, int i, long j, long j2) {
            long foregroundServiceTotalDurationsSince = ((com.android.server.am.AppFGSTracker) this.mTracker).mAppRestrictionController.getForegroundServiceTotalDurationsSince(str, i, j2, j, 8);
            if (foregroundServiceTotalDurationsSince > 0 && foregroundServiceTotalDurationsSince >= getLocationFGSThreshold()) {
                return true;
            }
            return false;
        }

        @Override // com.android.server.am.BaseAppStateEventsTracker.BaseAppStateEventsPolicy
        java.lang.String getExemptionReasonString(java.lang.String str, int i, int i2) {
            if (i2 != -1) {
                return super.getExemptionReasonString(str, i, i2);
            }
            long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
            return "{mediaPlayback=" + shouldExemptMediaPlaybackFGS(str, i, elapsedRealtime, getFgsLongRunningWindowSize()) + ", location=" + shouldExemptLocationFGS(str, i, elapsedRealtime, java.lang.Math.max(0L, elapsedRealtime - getFgsLongRunningWindowSize())) + "}";
        }

        void onLongRunningFgsGone(java.lang.String str, int i) {
            ((com.android.server.am.AppFGSTracker) this.mTracker).mAppRestrictionController.cancelLongRunningFGSNotificationIfNecessary(str, i);
        }

        @Override // com.android.server.am.BaseAppStateEventsTracker.BaseAppStateEventsPolicy, com.android.server.am.BaseAppStatePolicy
        void dump(java.io.PrintWriter printWriter, java.lang.String str) {
            printWriter.print(str);
            printWriter.println("APP FOREGROUND SERVICE TRACKER POLICY SETTINGS:");
            java.lang.String str2 = "  " + str;
            super.dump(printWriter, str2);
            if (isEnabled()) {
                printWriter.print(str2);
                printWriter.print(KEY_BG_FGS_LONG_RUNNING_THRESHOLD);
                printWriter.print('=');
                printWriter.println(this.mBgFgsLongRunningThresholdMs);
                printWriter.print(str2);
                printWriter.print(KEY_BG_FGS_MEDIA_PLAYBACK_THRESHOLD);
                printWriter.print('=');
                printWriter.println(this.mBgFgsMediaPlaybackThresholdMs);
                printWriter.print(str2);
                printWriter.print(KEY_BG_FGS_LOCATION_THRESHOLD);
                printWriter.print('=');
                printWriter.println(this.mBgFgsLocationThresholdMs);
            }
        }
    }
}
