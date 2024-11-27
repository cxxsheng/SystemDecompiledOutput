package com.android.server.am;

/* loaded from: classes.dex */
final class ProcessServiceRecord {
    boolean mAllowlistManager;
    final com.android.server.am.ProcessRecord mApp;
    private int mConnectionGroup;
    private int mConnectionImportance;
    private com.android.server.am.ServiceRecord mConnectionService;
    private boolean mExecServicesFg;
    private int mFgServiceTypes;
    private boolean mHasAboveClient;
    private boolean mHasClientActivities;
    private boolean mHasForegroundServices;
    private boolean mHasTopStartedAlmostPerceptibleServices;
    private boolean mHasTypeNoneFgs;
    private long mLastTopStartedAlmostPerceptibleBindRequestUptimeMs;
    private int mRepFgServiceTypes;
    private boolean mRepHasForegroundServices;
    private boolean mScheduleServiceTimeoutPending;

    @android.annotation.Nullable
    private android.util.ArraySet<com.android.server.am.ConnectionRecord> mSdkSandboxConnections;
    private final com.android.server.am.ActivityManagerService mService;
    private boolean mTreatLikeActivity;
    final android.util.ArraySet<com.android.server.am.ServiceRecord> mServices = new android.util.ArraySet<>();
    private final android.util.ArraySet<com.android.server.am.ServiceRecord> mExecutingServices = new android.util.ArraySet<>();
    private final android.util.ArraySet<com.android.server.am.ConnectionRecord> mConnections = new android.util.ArraySet<>();
    private android.util.ArraySet<java.lang.Integer> mBoundClientUids = new android.util.ArraySet<>();

    ProcessServiceRecord(com.android.server.am.ProcessRecord processRecord) {
        this.mApp = processRecord;
        this.mService = processRecord.mService;
    }

    void setHasClientActivities(boolean z) {
        this.mHasClientActivities = z;
        this.mApp.getWindowProcessController().setHasClientActivities(z);
    }

    boolean hasClientActivities() {
        return this.mHasClientActivities;
    }

    void setHasForegroundServices(boolean z, int i, boolean z2) {
        this.mHasForegroundServices = z;
        this.mFgServiceTypes = i;
        this.mHasTypeNoneFgs = z2;
        this.mApp.getWindowProcessController().setHasForegroundServices(z);
        if (z) {
            this.mApp.mProfile.addHostingComponentType(256);
        } else {
            this.mApp.mProfile.clearHostingComponentType(256);
        }
    }

    boolean hasForegroundServices() {
        return this.mHasForegroundServices;
    }

    void setHasReportedForegroundServices(boolean z) {
        this.mRepHasForegroundServices = z;
    }

    boolean hasReportedForegroundServices() {
        return this.mRepHasForegroundServices;
    }

    int getForegroundServiceTypes() {
        if (this.mHasForegroundServices) {
            return this.mFgServiceTypes;
        }
        return 0;
    }

    boolean areForegroundServiceTypesSame(int i, boolean z) {
        return (getForegroundServiceTypes() & i) == i && this.mHasTypeNoneFgs == z;
    }

    boolean containsAnyForegroundServiceTypes(int i) {
        return (i & getForegroundServiceTypes()) != 0;
    }

    boolean hasNonShortForegroundServices() {
        if (this.mHasForegroundServices) {
            return this.mHasTypeNoneFgs || this.mFgServiceTypes != 2048;
        }
        return false;
    }

    boolean areAllShortForegroundServicesProcstateTimedOut(long j) {
        if (!this.mHasForegroundServices || hasNonShortForegroundServices()) {
            return false;
        }
        for (int size = this.mServices.size() - 1; size >= 0; size--) {
            com.android.server.am.ServiceRecord valueAt = this.mServices.valueAt(size);
            if (valueAt.isShortFgs() && valueAt.hasShortFgsInfo() && valueAt.getShortFgsInfo().getProcStateDemoteTime() >= j) {
                return false;
            }
        }
        return true;
    }

    int getReportedForegroundServiceTypes() {
        return this.mRepFgServiceTypes;
    }

    void setReportedForegroundServiceTypes(int i) {
        this.mRepFgServiceTypes = i;
    }

    int getNumForegroundServices() {
        int size = this.mServices.size();
        int i = 0;
        for (int i2 = 0; i2 < size; i2++) {
            if (this.mServices.valueAt(i2).isForeground) {
                i++;
            }
        }
        return i;
    }

    void updateHasTopStartedAlmostPerceptibleServices() {
        this.mHasTopStartedAlmostPerceptibleServices = false;
        this.mLastTopStartedAlmostPerceptibleBindRequestUptimeMs = 0L;
        for (int size = this.mServices.size() - 1; size >= 0; size--) {
            com.android.server.am.ServiceRecord valueAt = this.mServices.valueAt(size);
            this.mLastTopStartedAlmostPerceptibleBindRequestUptimeMs = java.lang.Math.max(this.mLastTopStartedAlmostPerceptibleBindRequestUptimeMs, valueAt.lastTopAlmostPerceptibleBindRequestUptimeMs);
            if (!this.mHasTopStartedAlmostPerceptibleServices && isAlmostPerceptible(valueAt)) {
                this.mHasTopStartedAlmostPerceptibleServices = true;
            }
        }
    }

    private boolean isAlmostPerceptible(com.android.server.am.ServiceRecord serviceRecord) {
        if (serviceRecord.lastTopAlmostPerceptibleBindRequestUptimeMs <= 0) {
            return false;
        }
        android.util.ArrayMap<android.os.IBinder, java.util.ArrayList<com.android.server.am.ConnectionRecord>> connections = serviceRecord.getConnections();
        for (int size = connections.size() - 1; size >= 0; size--) {
            java.util.ArrayList<com.android.server.am.ConnectionRecord> valueAt = connections.valueAt(size);
            for (int size2 = valueAt.size() - 1; size2 >= 0; size2--) {
                if (valueAt.get(size2).hasFlag(65536)) {
                    return true;
                }
            }
        }
        return false;
    }

    boolean hasTopStartedAlmostPerceptibleServices() {
        return this.mHasTopStartedAlmostPerceptibleServices || (this.mLastTopStartedAlmostPerceptibleBindRequestUptimeMs > 0 && android.os.SystemClock.uptimeMillis() - this.mLastTopStartedAlmostPerceptibleBindRequestUptimeMs < this.mService.mConstants.mServiceBindAlmostPerceptibleTimeoutMs);
    }

    com.android.server.am.ServiceRecord getConnectionService() {
        return this.mConnectionService;
    }

    void setConnectionService(com.android.server.am.ServiceRecord serviceRecord) {
        this.mConnectionService = serviceRecord;
    }

    int getConnectionGroup() {
        return this.mConnectionGroup;
    }

    void setConnectionGroup(int i) {
        this.mConnectionGroup = i;
    }

    int getConnectionImportance() {
        return this.mConnectionImportance;
    }

    void setConnectionImportance(int i) {
        this.mConnectionImportance = i;
    }

    void updateHasAboveClientLocked() {
        this.mHasAboveClient = false;
        for (int size = this.mConnections.size() - 1; size >= 0; size--) {
            com.android.server.am.ConnectionRecord valueAt = this.mConnections.valueAt(size);
            if (!(valueAt.binding.service.app != null && valueAt.binding.service.app.mServices == this) && valueAt.hasFlag(8)) {
                this.mHasAboveClient = true;
                return;
            }
        }
    }

    void setHasAboveClient(boolean z) {
        this.mHasAboveClient = z;
    }

    boolean hasAboveClient() {
        return this.mHasAboveClient;
    }

    int modifyRawOomAdj(int i) {
        if (this.mHasAboveClient && i >= 0) {
            if (i < 100) {
                return 100;
            }
            if (i < 200) {
                return 200;
            }
            if (i < 250) {
                return 250;
            }
            if (i < 900) {
                return com.android.server.am.ProcessList.CACHED_APP_MIN_ADJ;
            }
            if (i < 999) {
                return i + 1;
            }
            return i;
        }
        return i;
    }

    boolean isTreatedLikeActivity() {
        return this.mTreatLikeActivity;
    }

    void setTreatLikeActivity(boolean z) {
        this.mTreatLikeActivity = z;
    }

    boolean shouldExecServicesFg() {
        return this.mExecServicesFg;
    }

    void setExecServicesFg(boolean z) {
        this.mExecServicesFg = z;
    }

    boolean startService(com.android.server.am.ServiceRecord serviceRecord) {
        if (serviceRecord == null) {
            return false;
        }
        boolean add = this.mServices.add(serviceRecord);
        if (add && serviceRecord.serviceInfo != null) {
            this.mApp.getWindowProcessController().onServiceStarted(serviceRecord.serviceInfo);
            updateHostingComonentTypeForBindingsLocked();
        }
        if (serviceRecord.lastTopAlmostPerceptibleBindRequestUptimeMs > 0) {
            this.mLastTopStartedAlmostPerceptibleBindRequestUptimeMs = java.lang.Math.max(this.mLastTopStartedAlmostPerceptibleBindRequestUptimeMs, serviceRecord.lastTopAlmostPerceptibleBindRequestUptimeMs);
            if (!this.mHasTopStartedAlmostPerceptibleServices) {
                this.mHasTopStartedAlmostPerceptibleServices = isAlmostPerceptible(serviceRecord);
            }
        }
        return add;
    }

    boolean stopService(com.android.server.am.ServiceRecord serviceRecord) {
        boolean remove = this.mServices.remove(serviceRecord);
        if (serviceRecord.lastTopAlmostPerceptibleBindRequestUptimeMs > 0) {
            updateHasTopStartedAlmostPerceptibleServices();
        }
        if (remove) {
            updateHostingComonentTypeForBindingsLocked();
        }
        return remove;
    }

    void stopAllServices() {
        this.mServices.clear();
        updateHasTopStartedAlmostPerceptibleServices();
    }

    int numberOfRunningServices() {
        return this.mServices.size();
    }

    com.android.server.am.ServiceRecord getRunningServiceAt(int i) {
        return this.mServices.valueAt(i);
    }

    void startExecutingService(com.android.server.am.ServiceRecord serviceRecord) {
        this.mExecutingServices.add(serviceRecord);
    }

    void stopExecutingService(com.android.server.am.ServiceRecord serviceRecord) {
        this.mExecutingServices.remove(serviceRecord);
    }

    void stopAllExecutingServices() {
        this.mExecutingServices.clear();
    }

    com.android.server.am.ServiceRecord getExecutingServiceAt(int i) {
        return this.mExecutingServices.valueAt(i);
    }

    int numberOfExecutingServices() {
        return this.mExecutingServices.size();
    }

    void addConnection(com.android.server.am.ConnectionRecord connectionRecord) {
        this.mConnections.add(connectionRecord);
        addSdkSandboxConnectionIfNecessary(connectionRecord);
    }

    void removeConnection(com.android.server.am.ConnectionRecord connectionRecord) {
        this.mConnections.remove(connectionRecord);
        removeSdkSandboxConnectionIfNecessary(connectionRecord);
    }

    void removeAllConnections() {
        int size = this.mConnections.size();
        for (int i = 0; i < size; i++) {
            removeSdkSandboxConnectionIfNecessary(this.mConnections.valueAt(i));
        }
        this.mConnections.clear();
    }

    com.android.server.am.ConnectionRecord getConnectionAt(int i) {
        return this.mConnections.valueAt(i);
    }

    int numberOfConnections() {
        return this.mConnections.size();
    }

    private void addSdkSandboxConnectionIfNecessary(com.android.server.am.ConnectionRecord connectionRecord) {
        com.android.server.am.ProcessRecord processRecord = connectionRecord.binding.attributedClient;
        if (processRecord != null && connectionRecord.binding.service.isSdkSandbox) {
            if (processRecord.mServices.mSdkSandboxConnections == null) {
                processRecord.mServices.mSdkSandboxConnections = new android.util.ArraySet<>();
            }
            processRecord.mServices.mSdkSandboxConnections.add(connectionRecord);
        }
    }

    private void removeSdkSandboxConnectionIfNecessary(com.android.server.am.ConnectionRecord connectionRecord) {
        com.android.server.am.ProcessRecord processRecord = connectionRecord.binding.attributedClient;
        if (processRecord != null && connectionRecord.binding.service.isSdkSandbox && processRecord.mServices.mSdkSandboxConnections == null) {
            processRecord.mServices.mSdkSandboxConnections.remove(connectionRecord);
        }
    }

    void removeAllSdkSandboxConnections() {
        if (this.mSdkSandboxConnections != null) {
            this.mSdkSandboxConnections.clear();
        }
    }

    com.android.server.am.ConnectionRecord getSdkSandboxConnectionAt(int i) {
        if (this.mSdkSandboxConnections != null) {
            return this.mSdkSandboxConnections.valueAt(i);
        }
        return null;
    }

    int numberOfSdkSandboxConnections() {
        if (this.mSdkSandboxConnections != null) {
            return this.mSdkSandboxConnections.size();
        }
        return 0;
    }

    void addBoundClientUid(int i, java.lang.String str, long j) {
        this.mBoundClientUids.add(java.lang.Integer.valueOf(i));
        this.mApp.getWindowProcessController().addBoundClientUid(i, str, j);
    }

    void updateBoundClientUids() {
        clearBoundClientUids();
        if (this.mServices.isEmpty()) {
            return;
        }
        android.util.ArraySet<java.lang.Integer> arraySet = new android.util.ArraySet<>();
        int size = this.mServices.size();
        com.android.server.wm.WindowProcessController windowProcessController = this.mApp.getWindowProcessController();
        for (int i = 0; i < size; i++) {
            android.util.ArrayMap<android.os.IBinder, java.util.ArrayList<com.android.server.am.ConnectionRecord>> connections = this.mServices.valueAt(i).getConnections();
            int size2 = connections.size();
            for (int i2 = 0; i2 < size2; i2++) {
                java.util.ArrayList<com.android.server.am.ConnectionRecord> valueAt = connections.valueAt(i2);
                for (int i3 = 0; i3 < valueAt.size(); i3++) {
                    com.android.server.am.ConnectionRecord connectionRecord = valueAt.get(i3);
                    arraySet.add(java.lang.Integer.valueOf(connectionRecord.clientUid));
                    windowProcessController.addBoundClientUid(connectionRecord.clientUid, connectionRecord.clientPackageName, connectionRecord.getFlags());
                }
            }
        }
        this.mBoundClientUids = arraySet;
    }

    void addBoundClientUidsOfNewService(com.android.server.am.ServiceRecord serviceRecord) {
        if (serviceRecord == null) {
            return;
        }
        android.util.ArrayMap<android.os.IBinder, java.util.ArrayList<com.android.server.am.ConnectionRecord>> connections = serviceRecord.getConnections();
        for (int size = connections.size() - 1; size >= 0; size--) {
            java.util.ArrayList<com.android.server.am.ConnectionRecord> valueAt = connections.valueAt(size);
            for (int i = 0; i < valueAt.size(); i++) {
                com.android.server.am.ConnectionRecord connectionRecord = valueAt.get(i);
                this.mBoundClientUids.add(java.lang.Integer.valueOf(connectionRecord.clientUid));
                this.mApp.getWindowProcessController().addBoundClientUid(connectionRecord.clientUid, connectionRecord.clientPackageName, connectionRecord.getFlags());
            }
        }
    }

    void clearBoundClientUids() {
        this.mBoundClientUids.clear();
        this.mApp.getWindowProcessController().clearBoundClientUids();
    }

    @com.android.internal.annotations.GuardedBy({"mService"})
    void updateHostingComonentTypeForBindingsLocked() {
        boolean z = true;
        int numberOfRunningServices = numberOfRunningServices() - 1;
        while (true) {
            if (numberOfRunningServices >= 0) {
                com.android.server.am.ServiceRecord runningServiceAt = getRunningServiceAt(numberOfRunningServices);
                if (runningServiceAt != null && !runningServiceAt.getConnections().isEmpty()) {
                    break;
                } else {
                    numberOfRunningServices--;
                }
            } else {
                z = false;
                break;
            }
        }
        if (z) {
            this.mApp.mProfile.addHostingComponentType(512);
        } else {
            this.mApp.mProfile.clearHostingComponentType(512);
        }
    }

    @com.android.internal.annotations.GuardedBy({"mService"})
    boolean incServiceCrashCountLocked(long j) {
        boolean z = false;
        boolean z2 = this.mApp.mState.getCurProcState() == 5;
        for (int numberOfRunningServices = numberOfRunningServices() - 1; numberOfRunningServices >= 0; numberOfRunningServices--) {
            com.android.server.am.ServiceRecord runningServiceAt = getRunningServiceAt(numberOfRunningServices);
            if (j > runningServiceAt.restartTime + com.android.server.am.ActivityManagerConstants.MIN_CRASH_INTERVAL) {
                runningServiceAt.crashCount = 1;
            } else {
                runningServiceAt.crashCount++;
            }
            if (runningServiceAt.crashCount < this.mService.mConstants.BOUND_SERVICE_MAX_CRASH_RETRY && (runningServiceAt.isForeground || z2)) {
                z = true;
            }
        }
        return z;
    }

    @com.android.internal.annotations.GuardedBy({"mService"})
    void onCleanupApplicationRecordLocked() {
        this.mTreatLikeActivity = false;
        this.mHasAboveClient = false;
        setHasClientActivities(false);
    }

    @com.android.internal.annotations.GuardedBy({"mService"})
    void noteScheduleServiceTimeoutPending(boolean z) {
        this.mScheduleServiceTimeoutPending = z;
    }

    @com.android.internal.annotations.GuardedBy({"mService"})
    boolean isScheduleServiceTimeoutPending() {
        return this.mScheduleServiceTimeoutPending;
    }

    @com.android.internal.annotations.GuardedBy({"mService"})
    void onProcessUnfrozen() {
        scheduleServiceTimeoutIfNeededLocked();
    }

    @com.android.internal.annotations.GuardedBy({"mService"})
    void onProcessFrozenCancelled() {
        scheduleServiceTimeoutIfNeededLocked();
    }

    @com.android.internal.annotations.GuardedBy({"mService"})
    private void scheduleServiceTimeoutIfNeededLocked() {
        com.android.server.am.Flags.serviceBindingOomAdjPolicy();
    }

    void dump(java.io.PrintWriter printWriter, java.lang.String str, long j) {
        if (this.mHasForegroundServices || this.mApp.mState.getForcingToImportant() != null) {
            printWriter.print(str);
            printWriter.print("mHasForegroundServices=");
            printWriter.print(this.mHasForegroundServices);
            printWriter.print(" forcingToImportant=");
            printWriter.println(this.mApp.mState.getForcingToImportant());
        }
        if (this.mHasTopStartedAlmostPerceptibleServices || this.mLastTopStartedAlmostPerceptibleBindRequestUptimeMs > 0) {
            printWriter.print(str);
            printWriter.print("mHasTopStartedAlmostPerceptibleServices=");
            printWriter.print(this.mHasTopStartedAlmostPerceptibleServices);
            printWriter.print(" mLastTopStartedAlmostPerceptibleBindRequestUptimeMs=");
            printWriter.println(this.mLastTopStartedAlmostPerceptibleBindRequestUptimeMs);
        }
        if (this.mHasClientActivities || this.mHasAboveClient || this.mTreatLikeActivity) {
            printWriter.print(str);
            printWriter.print("hasClientActivities=");
            printWriter.print(this.mHasClientActivities);
            printWriter.print(" hasAboveClient=");
            printWriter.print(this.mHasAboveClient);
            printWriter.print(" treatLikeActivity=");
            printWriter.println(this.mTreatLikeActivity);
        }
        if (this.mConnectionService != null || this.mConnectionGroup != 0) {
            printWriter.print(str);
            printWriter.print("connectionGroup=");
            printWriter.print(this.mConnectionGroup);
            printWriter.print(" Importance=");
            printWriter.print(this.mConnectionImportance);
            printWriter.print(" Service=");
            printWriter.println(this.mConnectionService);
        }
        if (this.mAllowlistManager) {
            printWriter.print(str);
            printWriter.print("allowlistManager=");
            printWriter.println(this.mAllowlistManager);
        }
        if (this.mServices.size() > 0) {
            printWriter.print(str);
            printWriter.println("Services:");
            int size = this.mServices.size();
            for (int i = 0; i < size; i++) {
                printWriter.print(str);
                printWriter.print("  - ");
                printWriter.println(this.mServices.valueAt(i));
            }
        }
        if (this.mExecutingServices.size() > 0) {
            printWriter.print(str);
            printWriter.print("Executing Services (fg=");
            printWriter.print(this.mExecServicesFg);
            printWriter.println(")");
            int size2 = this.mExecutingServices.size();
            for (int i2 = 0; i2 < size2; i2++) {
                printWriter.print(str);
                printWriter.print("  - ");
                printWriter.println(this.mExecutingServices.valueAt(i2));
            }
        }
        if (this.mConnections.size() > 0) {
            printWriter.print(str);
            printWriter.println("mConnections:");
            int size3 = this.mConnections.size();
            for (int i3 = 0; i3 < size3; i3++) {
                printWriter.print(str);
                printWriter.print("  - ");
                printWriter.println(this.mConnections.valueAt(i3));
            }
        }
        com.android.server.am.Flags.serviceBindingOomAdjPolicy();
    }
}
