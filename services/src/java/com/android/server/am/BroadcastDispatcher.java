package com.android.server.am;

/* loaded from: classes.dex */
public class BroadcastDispatcher {
    private static final java.lang.String TAG = "BroadcastDispatcher";
    private com.android.server.AlarmManagerInternal mAlarm;
    private final com.android.server.am.BroadcastConstants mConstants;
    private com.android.server.am.BroadcastRecord mCurrentBroadcast;
    private final android.os.Handler mHandler;
    private final java.lang.Object mLock;
    private final com.android.server.am.BroadcastQueueImpl mQueue;
    final android.util.SparseIntArray mAlarmUids = new android.util.SparseIntArray();
    final com.android.server.AlarmManagerInternal.InFlightListener mAlarmListener = new com.android.server.AlarmManagerInternal.InFlightListener() { // from class: com.android.server.am.BroadcastDispatcher.1
        @Override // com.android.server.AlarmManagerInternal.InFlightListener
        public void broadcastAlarmPending(int i) {
            synchronized (com.android.server.am.BroadcastDispatcher.this.mLock) {
                try {
                    int i2 = 0;
                    com.android.server.am.BroadcastDispatcher.this.mAlarmUids.put(i, com.android.server.am.BroadcastDispatcher.this.mAlarmUids.get(i, 0) + 1);
                    int size = com.android.server.am.BroadcastDispatcher.this.mDeferredBroadcasts.size();
                    while (true) {
                        if (i2 >= size) {
                            break;
                        }
                        if (i != ((com.android.server.am.BroadcastDispatcher.Deferrals) com.android.server.am.BroadcastDispatcher.this.mDeferredBroadcasts.get(i2)).uid) {
                            i2++;
                        } else {
                            com.android.server.am.BroadcastDispatcher.this.mAlarmDeferrals.add((com.android.server.am.BroadcastDispatcher.Deferrals) com.android.server.am.BroadcastDispatcher.this.mDeferredBroadcasts.remove(i2));
                            break;
                        }
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        @Override // com.android.server.AlarmManagerInternal.InFlightListener
        public void broadcastAlarmComplete(int i) {
            synchronized (com.android.server.am.BroadcastDispatcher.this.mLock) {
                try {
                    int i2 = 0;
                    int i3 = com.android.server.am.BroadcastDispatcher.this.mAlarmUids.get(i, 0) - 1;
                    if (i3 >= 0) {
                        com.android.server.am.BroadcastDispatcher.this.mAlarmUids.put(i, i3);
                    } else {
                        android.util.Slog.wtf(com.android.server.am.BroadcastDispatcher.TAG, "Undercount of broadcast alarms in flight for " + i);
                        com.android.server.am.BroadcastDispatcher.this.mAlarmUids.put(i, 0);
                    }
                    if (i3 <= 0) {
                        int size = com.android.server.am.BroadcastDispatcher.this.mAlarmDeferrals.size();
                        while (true) {
                            if (i2 >= size) {
                                break;
                            }
                            if (i != ((com.android.server.am.BroadcastDispatcher.Deferrals) com.android.server.am.BroadcastDispatcher.this.mAlarmDeferrals.get(i2)).uid) {
                                i2++;
                            } else {
                                com.android.server.am.BroadcastDispatcher.insertLocked(com.android.server.am.BroadcastDispatcher.this.mDeferredBroadcasts, (com.android.server.am.BroadcastDispatcher.Deferrals) com.android.server.am.BroadcastDispatcher.this.mAlarmDeferrals.remove(i2));
                                break;
                            }
                        }
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
    };
    final java.lang.Runnable mScheduleRunnable = new java.lang.Runnable() { // from class: com.android.server.am.BroadcastDispatcher.2
        @Override // java.lang.Runnable
        public void run() {
            synchronized (com.android.server.am.BroadcastDispatcher.this.mLock) {
                com.android.server.am.BroadcastDispatcher.this.mQueue.scheduleBroadcastsLocked();
                com.android.server.am.BroadcastDispatcher.this.mRecheckScheduled = false;
            }
        }
    };
    private boolean mRecheckScheduled = false;
    private final java.util.ArrayList<com.android.server.am.BroadcastRecord> mOrderedBroadcasts = new java.util.ArrayList<>();
    private final java.util.ArrayList<com.android.server.am.BroadcastDispatcher.Deferrals> mDeferredBroadcasts = new java.util.ArrayList<>();
    private final java.util.ArrayList<com.android.server.am.BroadcastDispatcher.Deferrals> mAlarmDeferrals = new java.util.ArrayList<>();
    private final java.util.ArrayList<com.android.server.am.BroadcastRecord> mAlarmQueue = new java.util.ArrayList<>();
    private android.util.SparseArray<com.android.server.am.BroadcastDispatcher.DeferredBootCompletedBroadcastPerUser> mUser2Deferred = new android.util.SparseArray<>();

    static class Deferrals {
        int alarmCount;
        final java.util.ArrayList<com.android.server.am.BroadcastRecord> broadcasts = new java.util.ArrayList<>();
        long deferUntil;
        long deferredAt;
        long deferredBy;
        final int uid;

        Deferrals(int i, long j, long j2, int i2) {
            this.uid = i;
            this.deferredAt = j;
            this.deferredBy = j2;
            this.deferUntil = j + j2;
            this.alarmCount = i2;
        }

        void add(com.android.server.am.BroadcastRecord broadcastRecord) {
            this.broadcasts.add(broadcastRecord);
        }

        int size() {
            return this.broadcasts.size();
        }

        boolean isEmpty() {
            return this.broadcasts.isEmpty();
        }

        @dalvik.annotation.optimization.NeverCompile
        void dumpDebug(android.util.proto.ProtoOutputStream protoOutputStream, long j) {
            java.util.Iterator<com.android.server.am.BroadcastRecord> it = this.broadcasts.iterator();
            while (it.hasNext()) {
                it.next().dumpDebug(protoOutputStream, j);
            }
        }

        @dalvik.annotation.optimization.NeverCompile
        void dumpLocked(com.android.server.am.BroadcastDispatcher.Dumper dumper) {
            java.util.Iterator<com.android.server.am.BroadcastRecord> it = this.broadcasts.iterator();
            while (it.hasNext()) {
                dumper.dump(it.next());
            }
        }

        public java.lang.String toString() {
            java.lang.StringBuilder sb = new java.lang.StringBuilder(128);
            sb.append("Deferrals{uid=");
            sb.append(this.uid);
            sb.append(", deferUntil=");
            sb.append(this.deferUntil);
            sb.append(", #broadcasts=");
            sb.append(this.broadcasts.size());
            sb.append("}");
            return sb.toString();
        }
    }

    class Dumper {
        final java.lang.String mDumpPackage;
        java.lang.String mHeading;
        java.lang.String mLabel;
        int mOrdinal;
        final java.io.PrintWriter mPw;
        final java.lang.String mQueueName;
        final java.text.SimpleDateFormat mSdf;
        boolean mPrinted = false;
        boolean mNeedSep = true;

        Dumper(java.io.PrintWriter printWriter, java.lang.String str, java.lang.String str2, java.text.SimpleDateFormat simpleDateFormat) {
            this.mPw = printWriter;
            this.mQueueName = str;
            this.mDumpPackage = str2;
            this.mSdf = simpleDateFormat;
        }

        void setHeading(java.lang.String str) {
            this.mHeading = str;
            this.mPrinted = false;
        }

        void setLabel(java.lang.String str) {
            this.mLabel = "  " + str + " " + this.mQueueName + " #";
            this.mOrdinal = 0;
        }

        boolean didPrint() {
            return this.mPrinted;
        }

        @dalvik.annotation.optimization.NeverCompile
        void dump(com.android.server.am.BroadcastRecord broadcastRecord) {
            if (this.mDumpPackage == null || this.mDumpPackage.equals(broadcastRecord.callerPackage)) {
                if (!this.mPrinted) {
                    if (this.mNeedSep) {
                        this.mPw.println();
                    }
                    this.mPrinted = true;
                    this.mNeedSep = true;
                    this.mPw.println("  " + this.mHeading + " [" + this.mQueueName + "]:");
                }
                this.mPw.println(this.mLabel + this.mOrdinal + ":");
                this.mOrdinal = this.mOrdinal + 1;
                broadcastRecord.dump(this.mPw, "    ", this.mSdf);
            }
        }
    }

    static class DeferredBootCompletedBroadcastPerUser {

        @com.android.internal.annotations.VisibleForTesting
        boolean mBootCompletedBroadcastReceived;

        @com.android.internal.annotations.VisibleForTesting
        boolean mLockedBootCompletedBroadcastReceived;
        private int mUserId;

        @com.android.internal.annotations.VisibleForTesting
        android.util.SparseBooleanArray mUidReadyForLockedBootCompletedBroadcast = new android.util.SparseBooleanArray();

        @com.android.internal.annotations.VisibleForTesting
        android.util.SparseBooleanArray mUidReadyForBootCompletedBroadcast = new android.util.SparseBooleanArray();

        @com.android.internal.annotations.VisibleForTesting
        android.util.SparseArray<com.android.server.am.BroadcastRecord> mDeferredLockedBootCompletedBroadcasts = new android.util.SparseArray<>();

        @com.android.internal.annotations.VisibleForTesting
        android.util.SparseArray<com.android.server.am.BroadcastRecord> mDeferredBootCompletedBroadcasts = new android.util.SparseArray<>();

        DeferredBootCompletedBroadcastPerUser(int i) {
            this.mUserId = i;
        }

        public void updateUidReady(int i) {
            if (!this.mLockedBootCompletedBroadcastReceived || this.mDeferredLockedBootCompletedBroadcasts.size() != 0) {
                this.mUidReadyForLockedBootCompletedBroadcast.put(i, true);
            }
            if (!this.mBootCompletedBroadcastReceived || this.mDeferredBootCompletedBroadcasts.size() != 0) {
                this.mUidReadyForBootCompletedBroadcast.put(i, true);
            }
        }

        public void enqueueBootCompletedBroadcasts(java.lang.String str, android.util.SparseArray<com.android.server.am.BroadcastRecord> sparseArray) {
            if ("android.intent.action.LOCKED_BOOT_COMPLETED".equals(str)) {
                enqueueBootCompletedBroadcasts(sparseArray, this.mDeferredLockedBootCompletedBroadcasts, this.mUidReadyForLockedBootCompletedBroadcast);
                this.mLockedBootCompletedBroadcastReceived = true;
            } else if ("android.intent.action.BOOT_COMPLETED".equals(str)) {
                enqueueBootCompletedBroadcasts(sparseArray, this.mDeferredBootCompletedBroadcasts, this.mUidReadyForBootCompletedBroadcast);
                this.mBootCompletedBroadcastReceived = true;
            }
        }

        private void enqueueBootCompletedBroadcasts(android.util.SparseArray<com.android.server.am.BroadcastRecord> sparseArray, android.util.SparseArray<com.android.server.am.BroadcastRecord> sparseArray2, android.util.SparseBooleanArray sparseBooleanArray) {
            for (int size = sparseBooleanArray.size() - 1; size >= 0; size--) {
                if (sparseArray.indexOfKey(sparseBooleanArray.keyAt(size)) < 0) {
                    sparseBooleanArray.removeAt(size);
                }
            }
            int size2 = sparseArray.size();
            for (int i = 0; i < size2; i++) {
                int keyAt = sparseArray.keyAt(i);
                sparseArray2.put(keyAt, sparseArray.valueAt(i));
                if (sparseBooleanArray.indexOfKey(keyAt) < 0) {
                    sparseBooleanArray.put(keyAt, false);
                }
            }
        }

        @android.annotation.Nullable
        public com.android.server.am.BroadcastRecord dequeueDeferredBootCompletedBroadcast(boolean z) {
            com.android.server.am.BroadcastRecord dequeueDeferredBootCompletedBroadcast = dequeueDeferredBootCompletedBroadcast(this.mDeferredLockedBootCompletedBroadcasts, this.mUidReadyForLockedBootCompletedBroadcast, z);
            if (dequeueDeferredBootCompletedBroadcast == null) {
                return dequeueDeferredBootCompletedBroadcast(this.mDeferredBootCompletedBroadcasts, this.mUidReadyForBootCompletedBroadcast, z);
            }
            return dequeueDeferredBootCompletedBroadcast;
        }

        @android.annotation.Nullable
        private com.android.server.am.BroadcastRecord dequeueDeferredBootCompletedBroadcast(android.util.SparseArray<com.android.server.am.BroadcastRecord> sparseArray, android.util.SparseBooleanArray sparseBooleanArray, boolean z) {
            int size = sparseArray.size();
            for (int i = 0; i < size; i++) {
                int keyAt = sparseArray.keyAt(i);
                if (z || sparseBooleanArray.get(keyAt)) {
                    com.android.server.am.BroadcastRecord valueAt = sparseArray.valueAt(i);
                    sparseArray.removeAt(i);
                    if (sparseArray.size() == 0) {
                        sparseBooleanArray.clear();
                    }
                    return valueAt;
                }
            }
            return null;
        }

        @android.annotation.Nullable
        private android.util.SparseArray<com.android.server.am.BroadcastRecord> getDeferredList(java.lang.String str) {
            if (str.equals("android.intent.action.LOCKED_BOOT_COMPLETED")) {
                return this.mDeferredLockedBootCompletedBroadcasts;
            }
            if (!str.equals("android.intent.action.BOOT_COMPLETED")) {
                return null;
            }
            return this.mDeferredBootCompletedBroadcasts;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public int getBootCompletedBroadcastsUidsSize(java.lang.String str) {
            android.util.SparseArray<com.android.server.am.BroadcastRecord> deferredList = getDeferredList(str);
            if (deferredList != null) {
                return deferredList.size();
            }
            return 0;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public int getBootCompletedBroadcastsReceiversSize(java.lang.String str) {
            android.util.SparseArray<com.android.server.am.BroadcastRecord> deferredList = getDeferredList(str);
            if (deferredList == null) {
                return 0;
            }
            int size = deferredList.size();
            int i = 0;
            for (int i2 = 0; i2 < size; i2++) {
                i += deferredList.valueAt(i2).receivers.size();
            }
            return i;
        }

        @dalvik.annotation.optimization.NeverCompile
        public void dump(com.android.server.am.BroadcastDispatcher.Dumper dumper, java.lang.String str) {
            android.util.SparseArray<com.android.server.am.BroadcastRecord> deferredList = getDeferredList(str);
            if (deferredList == null) {
                return;
            }
            int size = deferredList.size();
            for (int i = 0; i < size; i++) {
                dumper.dump(deferredList.valueAt(i));
            }
        }

        @dalvik.annotation.optimization.NeverCompile
        public void dumpDebug(android.util.proto.ProtoOutputStream protoOutputStream, long j) {
            int size = this.mDeferredLockedBootCompletedBroadcasts.size();
            for (int i = 0; i < size; i++) {
                this.mDeferredLockedBootCompletedBroadcasts.valueAt(i).dumpDebug(protoOutputStream, j);
            }
            int size2 = this.mDeferredBootCompletedBroadcasts.size();
            for (int i2 = 0; i2 < size2; i2++) {
                this.mDeferredBootCompletedBroadcasts.valueAt(i2).dumpDebug(protoOutputStream, j);
            }
        }

        @dalvik.annotation.optimization.NeverCompile
        private void dumpBootCompletedBroadcastRecord(android.util.SparseArray<com.android.server.am.BroadcastRecord> sparseArray) {
            java.lang.String str;
            int size = sparseArray.size();
            for (int i = 0; i < size; i++) {
                java.lang.Object obj = sparseArray.valueAt(i).receivers.get(0);
                if (obj instanceof com.android.server.am.BroadcastFilter) {
                    str = ((com.android.server.am.BroadcastFilter) obj).receiverList.app.processName;
                } else {
                    str = ((android.content.pm.ResolveInfo) obj).activityInfo.applicationInfo.packageName;
                }
                android.util.Slog.i(com.android.server.am.BroadcastDispatcher.TAG, "uid:" + sparseArray.keyAt(i) + " packageName:" + str + " receivers:" + sparseArray.valueAt(i).receivers.size());
            }
        }
    }

    private com.android.server.am.BroadcastDispatcher.DeferredBootCompletedBroadcastPerUser getDeferredPerUser(int i) {
        if (this.mUser2Deferred.contains(i)) {
            return this.mUser2Deferred.get(i);
        }
        com.android.server.am.BroadcastDispatcher.DeferredBootCompletedBroadcastPerUser deferredBootCompletedBroadcastPerUser = new com.android.server.am.BroadcastDispatcher.DeferredBootCompletedBroadcastPerUser(i);
        this.mUser2Deferred.put(i, deferredBootCompletedBroadcastPerUser);
        return deferredBootCompletedBroadcastPerUser;
    }

    public void updateUidReadyForBootCompletedBroadcastLocked(int i) {
        getDeferredPerUser(android.os.UserHandle.getUserId(i)).updateUidReady(i);
    }

    @android.annotation.Nullable
    private com.android.server.am.BroadcastRecord dequeueDeferredBootCompletedBroadcast() {
        boolean z = this.mQueue.mService.mConstants.mDeferBootCompletedBroadcast == 0;
        int size = this.mUser2Deferred.size();
        com.android.server.am.BroadcastRecord broadcastRecord = null;
        for (int i = 0; i < size; i++) {
            broadcastRecord = this.mUser2Deferred.valueAt(i).dequeueDeferredBootCompletedBroadcast(z);
            if (broadcastRecord != null) {
                break;
            }
        }
        return broadcastRecord;
    }

    public BroadcastDispatcher(com.android.server.am.BroadcastQueueImpl broadcastQueueImpl, com.android.server.am.BroadcastConstants broadcastConstants, android.os.Handler handler, java.lang.Object obj) {
        this.mQueue = broadcastQueueImpl;
        this.mConstants = broadcastConstants;
        this.mHandler = handler;
        this.mLock = obj;
    }

    public void start() {
        this.mAlarm = (com.android.server.AlarmManagerInternal) com.android.server.LocalServices.getService(com.android.server.AlarmManagerInternal.class);
        this.mAlarm.registerInFlightListener(this.mAlarmListener);
    }

    public boolean isEmpty() {
        boolean z;
        synchronized (this.mLock) {
            try {
                z = isIdle() && getBootCompletedBroadcastsUidsSize("android.intent.action.LOCKED_BOOT_COMPLETED") == 0 && getBootCompletedBroadcastsUidsSize("android.intent.action.BOOT_COMPLETED") == 0;
            } finally {
            }
        }
        return z;
    }

    public boolean isIdle() {
        boolean z;
        synchronized (this.mLock) {
            try {
                z = this.mCurrentBroadcast == null && this.mOrderedBroadcasts.isEmpty() && this.mAlarmQueue.isEmpty() && isDeferralsListEmpty(this.mDeferredBroadcasts) && isDeferralsListEmpty(this.mAlarmDeferrals);
            } finally {
            }
        }
        return z;
    }

    private static boolean isDeferralsBeyondBarrier(@android.annotation.NonNull java.util.ArrayList<com.android.server.am.BroadcastDispatcher.Deferrals> arrayList, long j) {
        for (int i = 0; i < arrayList.size(); i++) {
            if (!isBeyondBarrier(arrayList.get(i).broadcasts, j)) {
                return false;
            }
        }
        return true;
    }

    private static boolean isBeyondBarrier(@android.annotation.NonNull java.util.ArrayList<com.android.server.am.BroadcastRecord> arrayList, long j) {
        for (int i = 0; i < arrayList.size(); i++) {
            if (arrayList.get(i).enqueueTime <= j) {
                return false;
            }
        }
        return true;
    }

    public boolean isBeyondBarrier(long j) {
        synchronized (this.mLock) {
            try {
                boolean z = false;
                if (this.mCurrentBroadcast != null && this.mCurrentBroadcast.enqueueTime <= j) {
                    return false;
                }
                if (isBeyondBarrier(this.mOrderedBroadcasts, j) && isBeyondBarrier(this.mAlarmQueue, j) && isDeferralsBeyondBarrier(this.mDeferredBroadcasts, j) && isDeferralsBeyondBarrier(this.mAlarmDeferrals, j)) {
                    z = true;
                }
                return z;
            } finally {
            }
        }
    }

    private static boolean isDispatchedInDeferrals(@android.annotation.NonNull java.util.ArrayList<com.android.server.am.BroadcastDispatcher.Deferrals> arrayList, @android.annotation.NonNull android.content.Intent intent) {
        for (int i = 0; i < arrayList.size(); i++) {
            if (!isDispatched(arrayList.get(i).broadcasts, intent)) {
                return false;
            }
        }
        return true;
    }

    private static boolean isDispatched(@android.annotation.NonNull java.util.ArrayList<com.android.server.am.BroadcastRecord> arrayList, @android.annotation.NonNull android.content.Intent intent) {
        for (int i = 0; i < arrayList.size(); i++) {
            if (intent.filterEquals(arrayList.get(i).intent)) {
                return false;
            }
        }
        return true;
    }

    public boolean isDispatched(@android.annotation.NonNull android.content.Intent intent) {
        synchronized (this.mLock) {
            try {
                boolean z = false;
                if (this.mCurrentBroadcast != null && intent.filterEquals(this.mCurrentBroadcast.intent)) {
                    return false;
                }
                if (isDispatched(this.mOrderedBroadcasts, intent) && isDispatched(this.mAlarmQueue, intent) && isDispatchedInDeferrals(this.mDeferredBroadcasts, intent) && isDispatchedInDeferrals(this.mAlarmDeferrals, intent)) {
                    z = true;
                }
                return z;
            } finally {
            }
        }
    }

    private static int pendingInDeferralsList(java.util.ArrayList<com.android.server.am.BroadcastDispatcher.Deferrals> arrayList) {
        int size = arrayList.size();
        int i = 0;
        for (int i2 = 0; i2 < size; i2++) {
            i += arrayList.get(i2).size();
        }
        return i;
    }

    private static boolean isDeferralsListEmpty(java.util.ArrayList<com.android.server.am.BroadcastDispatcher.Deferrals> arrayList) {
        return pendingInDeferralsList(arrayList) == 0;
    }

    public java.lang.String describeStateLocked() {
        java.lang.StringBuilder sb = new java.lang.StringBuilder(128);
        if (this.mCurrentBroadcast != null) {
            sb.append("1 in flight, ");
        }
        sb.append(this.mOrderedBroadcasts.size());
        sb.append(" ordered");
        int size = this.mAlarmQueue.size();
        if (size > 0) {
            sb.append(", ");
            sb.append(size);
            sb.append(" alarms");
        }
        int pendingInDeferralsList = pendingInDeferralsList(this.mAlarmDeferrals);
        if (pendingInDeferralsList > 0) {
            sb.append(", ");
            sb.append(pendingInDeferralsList);
            sb.append(" deferrals in alarm recipients");
        }
        int pendingInDeferralsList2 = pendingInDeferralsList(this.mDeferredBroadcasts);
        if (pendingInDeferralsList2 > 0) {
            sb.append(", ");
            sb.append(pendingInDeferralsList2);
            sb.append(" deferred");
        }
        int bootCompletedBroadcastsUidsSize = getBootCompletedBroadcastsUidsSize("android.intent.action.LOCKED_BOOT_COMPLETED");
        if (bootCompletedBroadcastsUidsSize > 0) {
            sb.append(", ");
            sb.append(bootCompletedBroadcastsUidsSize);
            sb.append(" deferred LOCKED_BOOT_COMPLETED/");
            sb.append(getBootCompletedBroadcastsReceiversSize("android.intent.action.LOCKED_BOOT_COMPLETED"));
            sb.append(" receivers");
        }
        int bootCompletedBroadcastsUidsSize2 = getBootCompletedBroadcastsUidsSize("android.intent.action.BOOT_COMPLETED");
        if (bootCompletedBroadcastsUidsSize2 > 0) {
            sb.append(", ");
            sb.append(bootCompletedBroadcastsUidsSize2);
            sb.append(" deferred BOOT_COMPLETED/");
            sb.append(getBootCompletedBroadcastsReceiversSize("android.intent.action.BOOT_COMPLETED"));
            sb.append(" receivers");
        }
        return sb.toString();
    }

    void enqueueOrderedBroadcastLocked(com.android.server.am.BroadcastRecord broadcastRecord) {
        java.util.ArrayList<com.android.server.am.BroadcastRecord> arrayList;
        if (broadcastRecord.alarm && this.mQueue.mService.mConstants.mPrioritizeAlarmBroadcasts) {
            arrayList = this.mAlarmQueue;
        } else {
            arrayList = this.mOrderedBroadcasts;
        }
        if (broadcastRecord.receivers == null || broadcastRecord.receivers.isEmpty()) {
            arrayList.add(broadcastRecord);
            return;
        }
        if ("android.intent.action.LOCKED_BOOT_COMPLETED".equals(broadcastRecord.intent.getAction())) {
            getDeferredPerUser(broadcastRecord.userId).enqueueBootCompletedBroadcasts("android.intent.action.LOCKED_BOOT_COMPLETED", broadcastRecord.splitDeferredBootCompletedBroadcastLocked(this.mQueue.mService.mInternal, this.mQueue.mService.mConstants.mDeferBootCompletedBroadcast));
            if (!broadcastRecord.receivers.isEmpty()) {
                this.mOrderedBroadcasts.add(broadcastRecord);
                return;
            }
            return;
        }
        if ("android.intent.action.BOOT_COMPLETED".equals(broadcastRecord.intent.getAction())) {
            getDeferredPerUser(broadcastRecord.userId).enqueueBootCompletedBroadcasts("android.intent.action.BOOT_COMPLETED", broadcastRecord.splitDeferredBootCompletedBroadcastLocked(this.mQueue.mService.mInternal, this.mQueue.mService.mConstants.mDeferBootCompletedBroadcast));
            if (!broadcastRecord.receivers.isEmpty()) {
                this.mOrderedBroadcasts.add(broadcastRecord);
                return;
            }
            return;
        }
        arrayList.add(broadcastRecord);
    }

    private int getBootCompletedBroadcastsUidsSize(java.lang.String str) {
        int size = this.mUser2Deferred.size();
        int i = 0;
        for (int i2 = 0; i2 < size; i2++) {
            i += this.mUser2Deferred.valueAt(i2).getBootCompletedBroadcastsUidsSize(str);
        }
        return i;
    }

    private int getBootCompletedBroadcastsReceiversSize(java.lang.String str) {
        int size = this.mUser2Deferred.size();
        int i = 0;
        for (int i2 = 0; i2 < size; i2++) {
            i += this.mUser2Deferred.valueAt(i2).getBootCompletedBroadcastsReceiversSize(str);
        }
        return i;
    }

    com.android.server.am.BroadcastRecord replaceBroadcastLocked(com.android.server.am.BroadcastRecord broadcastRecord, java.lang.String str) {
        com.android.server.am.BroadcastRecord replaceBroadcastLocked = replaceBroadcastLocked(this.mOrderedBroadcasts, broadcastRecord, str);
        if (replaceBroadcastLocked == null) {
            replaceBroadcastLocked = replaceBroadcastLocked(this.mAlarmQueue, broadcastRecord, str);
        }
        if (replaceBroadcastLocked == null) {
            replaceBroadcastLocked = replaceDeferredBroadcastLocked(this.mAlarmDeferrals, broadcastRecord, str);
        }
        if (replaceBroadcastLocked == null) {
            return replaceDeferredBroadcastLocked(this.mDeferredBroadcasts, broadcastRecord, str);
        }
        return replaceBroadcastLocked;
    }

    private com.android.server.am.BroadcastRecord replaceDeferredBroadcastLocked(java.util.ArrayList<com.android.server.am.BroadcastDispatcher.Deferrals> arrayList, com.android.server.am.BroadcastRecord broadcastRecord, java.lang.String str) {
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            com.android.server.am.BroadcastRecord replaceBroadcastLocked = replaceBroadcastLocked(arrayList.get(i).broadcasts, broadcastRecord, str);
            if (replaceBroadcastLocked != null) {
                return replaceBroadcastLocked;
            }
        }
        return null;
    }

    private com.android.server.am.BroadcastRecord replaceBroadcastLocked(java.util.ArrayList<com.android.server.am.BroadcastRecord> arrayList, com.android.server.am.BroadcastRecord broadcastRecord, java.lang.String str) {
        android.content.Intent intent = broadcastRecord.intent;
        for (int size = arrayList.size() - 1; size >= 0; size--) {
            com.android.server.am.BroadcastRecord broadcastRecord2 = arrayList.get(size);
            if (broadcastRecord2.userId == broadcastRecord.userId && intent.filterEquals(broadcastRecord2.intent)) {
                broadcastRecord.deferred = broadcastRecord2.deferred;
                arrayList.set(size, broadcastRecord);
                return broadcastRecord2;
            }
        }
        return null;
    }

    boolean cleanupDisabledPackageReceiversLocked(java.lang.String str, java.util.Set<java.lang.String> set, int i, boolean z) {
        boolean cleanupBroadcastListDisabledReceiversLocked = cleanupBroadcastListDisabledReceiversLocked(this.mOrderedBroadcasts, str, set, i, z);
        if (z || !cleanupBroadcastListDisabledReceiversLocked) {
            cleanupBroadcastListDisabledReceiversLocked = cleanupBroadcastListDisabledReceiversLocked(this.mAlarmQueue, str, set, i, z);
        }
        if (z || !cleanupBroadcastListDisabledReceiversLocked) {
            java.util.ArrayList<com.android.server.am.BroadcastRecord> arrayList = new java.util.ArrayList<>();
            int size = this.mUser2Deferred.size();
            for (int i2 = 0; i2 < size; i2++) {
                android.util.SparseArray<com.android.server.am.BroadcastRecord> sparseArray = this.mUser2Deferred.valueAt(i2).mDeferredLockedBootCompletedBroadcasts;
                int size2 = sparseArray.size();
                for (int i3 = 0; i3 < size2; i3++) {
                    arrayList.add(sparseArray.valueAt(i3));
                }
            }
            cleanupBroadcastListDisabledReceiversLocked = cleanupBroadcastListDisabledReceiversLocked(arrayList, str, set, i, z);
        }
        if (z || !cleanupBroadcastListDisabledReceiversLocked) {
            java.util.ArrayList<com.android.server.am.BroadcastRecord> arrayList2 = new java.util.ArrayList<>();
            int size3 = this.mUser2Deferred.size();
            for (int i4 = 0; i4 < size3; i4++) {
                android.util.SparseArray<com.android.server.am.BroadcastRecord> sparseArray2 = this.mUser2Deferred.valueAt(i4).mDeferredBootCompletedBroadcasts;
                int size4 = sparseArray2.size();
                for (int i5 = 0; i5 < size4; i5++) {
                    arrayList2.add(sparseArray2.valueAt(i5));
                }
            }
            cleanupBroadcastListDisabledReceiversLocked = cleanupBroadcastListDisabledReceiversLocked(arrayList2, str, set, i, z);
        }
        if (z || !cleanupBroadcastListDisabledReceiversLocked) {
            cleanupBroadcastListDisabledReceiversLocked |= cleanupDeferralsListDisabledReceiversLocked(this.mAlarmDeferrals, str, set, i, z);
        }
        if (z || !cleanupBroadcastListDisabledReceiversLocked) {
            cleanupBroadcastListDisabledReceiversLocked |= cleanupDeferralsListDisabledReceiversLocked(this.mDeferredBroadcasts, str, set, i, z);
        }
        if ((z || !cleanupBroadcastListDisabledReceiversLocked) && this.mCurrentBroadcast != null) {
            return cleanupBroadcastListDisabledReceiversLocked | this.mCurrentBroadcast.cleanupDisabledPackageReceiversLocked(str, set, i, z);
        }
        return cleanupBroadcastListDisabledReceiversLocked;
    }

    private boolean cleanupDeferralsListDisabledReceiversLocked(java.util.ArrayList<com.android.server.am.BroadcastDispatcher.Deferrals> arrayList, java.lang.String str, java.util.Set<java.lang.String> set, int i, boolean z) {
        java.util.Iterator<com.android.server.am.BroadcastDispatcher.Deferrals> it = arrayList.iterator();
        boolean z2 = false;
        while (it.hasNext()) {
            z2 = cleanupBroadcastListDisabledReceiversLocked(it.next().broadcasts, str, set, i, z);
            if (!z && z2) {
                return true;
            }
        }
        return z2;
    }

    private boolean cleanupBroadcastListDisabledReceiversLocked(java.util.ArrayList<com.android.server.am.BroadcastRecord> arrayList, java.lang.String str, java.util.Set<java.lang.String> set, int i, boolean z) {
        java.util.Iterator<com.android.server.am.BroadcastRecord> it = arrayList.iterator();
        boolean z2 = false;
        while (it.hasNext()) {
            z2 |= it.next().cleanupDisabledPackageReceiversLocked(str, set, i, z);
            if (!z && z2) {
                return true;
            }
        }
        return z2;
    }

    @dalvik.annotation.optimization.NeverCompile
    public void dumpDebug(android.util.proto.ProtoOutputStream protoOutputStream, long j) {
        if (this.mCurrentBroadcast != null) {
            this.mCurrentBroadcast.dumpDebug(protoOutputStream, j);
        }
        java.util.Iterator<com.android.server.am.BroadcastDispatcher.Deferrals> it = this.mAlarmDeferrals.iterator();
        while (it.hasNext()) {
            it.next().dumpDebug(protoOutputStream, j);
        }
        java.util.Iterator<com.android.server.am.BroadcastRecord> it2 = this.mOrderedBroadcasts.iterator();
        while (it2.hasNext()) {
            it2.next().dumpDebug(protoOutputStream, j);
        }
        java.util.Iterator<com.android.server.am.BroadcastRecord> it3 = this.mAlarmQueue.iterator();
        while (it3.hasNext()) {
            it3.next().dumpDebug(protoOutputStream, j);
        }
        java.util.Iterator<com.android.server.am.BroadcastDispatcher.Deferrals> it4 = this.mDeferredBroadcasts.iterator();
        while (it4.hasNext()) {
            it4.next().dumpDebug(protoOutputStream, j);
        }
        int size = this.mUser2Deferred.size();
        for (int i = 0; i < size; i++) {
            this.mUser2Deferred.valueAt(i).dumpDebug(protoOutputStream, j);
        }
    }

    public com.android.server.am.BroadcastRecord getActiveBroadcastLocked() {
        return this.mCurrentBroadcast;
    }

    public com.android.server.am.BroadcastRecord getNextBroadcastLocked(long j) {
        com.android.server.am.BroadcastRecord broadcastRecord;
        if (this.mCurrentBroadcast != null) {
            return this.mCurrentBroadcast;
        }
        if (this.mAlarmQueue.isEmpty()) {
            broadcastRecord = null;
        } else {
            broadcastRecord = this.mAlarmQueue.remove(0);
        }
        if (broadcastRecord == null) {
            broadcastRecord = dequeueDeferredBootCompletedBroadcast();
        }
        if (broadcastRecord == null && !this.mAlarmDeferrals.isEmpty()) {
            broadcastRecord = popLocked(this.mAlarmDeferrals);
        }
        boolean z = !this.mOrderedBroadcasts.isEmpty();
        if (broadcastRecord == null && !this.mDeferredBroadcasts.isEmpty()) {
            int i = 0;
            while (true) {
                if (i >= this.mDeferredBroadcasts.size()) {
                    break;
                }
                com.android.server.am.BroadcastDispatcher.Deferrals deferrals = this.mDeferredBroadcasts.get(i);
                if (j < deferrals.deferUntil && z) {
                    break;
                }
                if (deferrals.broadcasts.size() <= 0) {
                    i++;
                } else {
                    broadcastRecord = deferrals.broadcasts.remove(0);
                    this.mDeferredBroadcasts.remove(i);
                    deferrals.deferredBy = calculateDeferral(deferrals.deferredBy);
                    deferrals.deferUntil += deferrals.deferredBy;
                    insertLocked(this.mDeferredBroadcasts, deferrals);
                    break;
                }
            }
        }
        if (broadcastRecord == null && z) {
            broadcastRecord = this.mOrderedBroadcasts.remove(0);
        }
        this.mCurrentBroadcast = broadcastRecord;
        return broadcastRecord;
    }

    public void retireBroadcastLocked(com.android.server.am.BroadcastRecord broadcastRecord) {
        if (broadcastRecord != this.mCurrentBroadcast) {
            android.util.Slog.wtf(TAG, "Retiring broadcast " + broadcastRecord + " doesn't match current outgoing " + this.mCurrentBroadcast);
        }
        this.mCurrentBroadcast = null;
    }

    public boolean isDeferringLocked(int i) {
        com.android.server.am.BroadcastDispatcher.Deferrals findUidLocked = findUidLocked(i);
        if (findUidLocked == null || !findUidLocked.broadcasts.isEmpty() || android.os.SystemClock.uptimeMillis() < findUidLocked.deferUntil) {
            return findUidLocked != null;
        }
        removeDeferral(findUidLocked);
        return false;
    }

    public void startDeferring(int i) {
        synchronized (this.mLock) {
            try {
                com.android.server.am.BroadcastDispatcher.Deferrals findUidLocked = findUidLocked(i);
                if (findUidLocked == null) {
                    com.android.server.am.BroadcastDispatcher.Deferrals deferrals = new com.android.server.am.BroadcastDispatcher.Deferrals(i, android.os.SystemClock.uptimeMillis(), this.mConstants.DEFERRAL, this.mAlarmUids.get(i, 0));
                    if (deferrals.alarmCount == 0) {
                        insertLocked(this.mDeferredBroadcasts, deferrals);
                        scheduleDeferralCheckLocked(true);
                    } else {
                        this.mAlarmDeferrals.add(deferrals);
                    }
                } else {
                    findUidLocked.deferredBy = this.mConstants.DEFERRAL;
                }
            } finally {
            }
        }
    }

    public void addDeferredBroadcast(int i, com.android.server.am.BroadcastRecord broadcastRecord) {
        synchronized (this.mLock) {
            try {
                com.android.server.am.BroadcastDispatcher.Deferrals findUidLocked = findUidLocked(i);
                if (findUidLocked == null) {
                    android.util.Slog.wtf(TAG, "Adding deferred broadcast but not tracking " + i);
                } else if (broadcastRecord == null) {
                    android.util.Slog.wtf(TAG, "Deferring null broadcast to " + i);
                } else {
                    broadcastRecord.deferred = true;
                    findUidLocked.add(broadcastRecord);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void scheduleDeferralCheckLocked(boolean z) {
        if ((z || !this.mRecheckScheduled) && !this.mDeferredBroadcasts.isEmpty()) {
            com.android.server.am.BroadcastDispatcher.Deferrals deferrals = this.mDeferredBroadcasts.get(0);
            if (!deferrals.broadcasts.isEmpty()) {
                this.mHandler.removeCallbacks(this.mScheduleRunnable);
                this.mHandler.postAtTime(this.mScheduleRunnable, deferrals.deferUntil);
                this.mRecheckScheduled = true;
            }
        }
    }

    public void cancelDeferralsLocked() {
        zeroDeferralTimes(this.mAlarmDeferrals);
        zeroDeferralTimes(this.mDeferredBroadcasts);
    }

    private static void zeroDeferralTimes(java.util.ArrayList<com.android.server.am.BroadcastDispatcher.Deferrals> arrayList) {
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            com.android.server.am.BroadcastDispatcher.Deferrals deferrals = arrayList.get(i);
            deferrals.deferredBy = 0L;
            deferrals.deferUntil = 0L;
        }
    }

    private com.android.server.am.BroadcastDispatcher.Deferrals findUidLocked(int i) {
        com.android.server.am.BroadcastDispatcher.Deferrals findUidLocked = findUidLocked(i, this.mDeferredBroadcasts);
        if (findUidLocked == null) {
            return findUidLocked(i, this.mAlarmDeferrals);
        }
        return findUidLocked;
    }

    private boolean removeDeferral(com.android.server.am.BroadcastDispatcher.Deferrals deferrals) {
        boolean remove = this.mDeferredBroadcasts.remove(deferrals);
        if (!remove) {
            return this.mAlarmDeferrals.remove(deferrals);
        }
        return remove;
    }

    private static com.android.server.am.BroadcastDispatcher.Deferrals findUidLocked(int i, java.util.ArrayList<com.android.server.am.BroadcastDispatcher.Deferrals> arrayList) {
        int size = arrayList.size();
        for (int i2 = 0; i2 < size; i2++) {
            com.android.server.am.BroadcastDispatcher.Deferrals deferrals = arrayList.get(i2);
            if (i == deferrals.uid) {
                return deferrals;
            }
        }
        return null;
    }

    private static com.android.server.am.BroadcastRecord popLocked(java.util.ArrayList<com.android.server.am.BroadcastDispatcher.Deferrals> arrayList) {
        com.android.server.am.BroadcastDispatcher.Deferrals deferrals = arrayList.get(0);
        if (deferrals.broadcasts.isEmpty()) {
            return null;
        }
        return deferrals.broadcasts.remove(0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void insertLocked(java.util.ArrayList<com.android.server.am.BroadcastDispatcher.Deferrals> arrayList, com.android.server.am.BroadcastDispatcher.Deferrals deferrals) {
        int size = arrayList.size();
        int i = 0;
        while (i < size && deferrals.deferUntil >= arrayList.get(i).deferUntil) {
            i++;
        }
        arrayList.add(i, deferrals);
    }

    private long calculateDeferral(long j) {
        return java.lang.Math.max(this.mConstants.DEFERRAL_FLOOR, (long) (j * this.mConstants.DEFERRAL_DECAY_FACTOR));
    }

    @dalvik.annotation.optimization.NeverCompile
    boolean dumpLocked(java.io.PrintWriter printWriter, java.lang.String str, java.lang.String str2, java.text.SimpleDateFormat simpleDateFormat) {
        com.android.server.am.BroadcastDispatcher.Dumper dumper = new com.android.server.am.BroadcastDispatcher.Dumper(printWriter, str2, str, simpleDateFormat);
        dumper.setHeading("Currently in flight");
        dumper.setLabel("In-Flight Ordered Broadcast");
        if (this.mCurrentBroadcast != null) {
            dumper.dump(this.mCurrentBroadcast);
        } else {
            printWriter.println("  (null)");
        }
        boolean didPrint = dumper.didPrint() | false;
        dumper.setHeading("Active alarm broadcasts");
        dumper.setLabel("Active Alarm Broadcast");
        java.util.Iterator<com.android.server.am.BroadcastRecord> it = this.mAlarmQueue.iterator();
        while (it.hasNext()) {
            dumper.dump(it.next());
        }
        boolean didPrint2 = didPrint | dumper.didPrint();
        dumper.setHeading("Active ordered broadcasts");
        dumper.setLabel("Active Ordered Broadcast");
        java.util.Iterator<com.android.server.am.BroadcastDispatcher.Deferrals> it2 = this.mAlarmDeferrals.iterator();
        while (it2.hasNext()) {
            it2.next().dumpLocked(dumper);
        }
        java.util.Iterator<com.android.server.am.BroadcastRecord> it3 = this.mOrderedBroadcasts.iterator();
        while (it3.hasNext()) {
            dumper.dump(it3.next());
        }
        boolean didPrint3 = didPrint2 | dumper.didPrint();
        dumper.setHeading("Deferred ordered broadcasts");
        dumper.setLabel("Deferred Ordered Broadcast");
        java.util.Iterator<com.android.server.am.BroadcastDispatcher.Deferrals> it4 = this.mDeferredBroadcasts.iterator();
        while (it4.hasNext()) {
            it4.next().dumpLocked(dumper);
        }
        boolean didPrint4 = didPrint3 | dumper.didPrint();
        dumper.setHeading("Deferred LOCKED_BOOT_COMPLETED broadcasts");
        dumper.setLabel("Deferred LOCKED_BOOT_COMPLETED Broadcast");
        int size = this.mUser2Deferred.size();
        for (int i = 0; i < size; i++) {
            this.mUser2Deferred.valueAt(i).dump(dumper, "android.intent.action.LOCKED_BOOT_COMPLETED");
        }
        boolean didPrint5 = didPrint4 | dumper.didPrint();
        dumper.setHeading("Deferred BOOT_COMPLETED broadcasts");
        dumper.setLabel("Deferred BOOT_COMPLETED Broadcast");
        int size2 = this.mUser2Deferred.size();
        for (int i2 = 0; i2 < size2; i2++) {
            this.mUser2Deferred.valueAt(i2).dump(dumper, "android.intent.action.BOOT_COMPLETED");
        }
        return didPrint5 | dumper.didPrint();
    }
}
