package com.android.server.am;

/* loaded from: classes.dex */
public abstract class BroadcastQueue {
    public static final java.lang.String TAG = "BroadcastQueue";
    public static final java.lang.String TAG_DUMP = "broadcast_queue_dump";

    @android.annotation.NonNull
    final android.os.Handler mHandler;

    @android.annotation.NonNull
    final com.android.server.am.BroadcastHistory mHistory;

    @android.annotation.NonNull
    final java.lang.String mQueueName;

    @android.annotation.NonNull
    final com.android.server.am.ActivityManagerService mService;

    @android.annotation.NonNull
    final com.android.server.am.BroadcastSkipPolicy mSkipPolicy;

    @com.android.internal.annotations.GuardedBy({"mService"})
    public abstract void backgroundServicesFinishedLocked(int i);

    @com.android.internal.annotations.GuardedBy({"mService"})
    public abstract boolean cleanupDisabledPackageReceiversLocked(@android.annotation.Nullable java.lang.String str, @android.annotation.Nullable java.util.Set<java.lang.String> set, int i);

    @android.annotation.NonNull
    @com.android.internal.annotations.GuardedBy({"mService"})
    public abstract java.lang.String describeStateLocked();

    @com.android.internal.annotations.GuardedBy({"mService"})
    public abstract void dumpDebug(@android.annotation.NonNull android.util.proto.ProtoOutputStream protoOutputStream, long j);

    @com.android.internal.annotations.GuardedBy({"mService"})
    public abstract boolean dumpLocked(@android.annotation.NonNull java.io.FileDescriptor fileDescriptor, @android.annotation.NonNull java.io.PrintWriter printWriter, @android.annotation.NonNull java.lang.String[] strArr, int i, boolean z, boolean z2, boolean z3, @android.annotation.Nullable java.lang.String str, boolean z4);

    @com.android.internal.annotations.GuardedBy({"mService"})
    public abstract void enqueueBroadcastLocked(@android.annotation.NonNull com.android.server.am.BroadcastRecord broadcastRecord);

    @com.android.internal.annotations.GuardedBy({"mService"})
    public abstract boolean finishReceiverLocked(@android.annotation.NonNull com.android.server.am.ProcessRecord processRecord, int i, @android.annotation.Nullable java.lang.String str, @android.annotation.Nullable android.os.Bundle bundle, boolean z, boolean z2);

    @com.android.internal.annotations.GuardedBy({"mService"})
    public abstract int getPreferredSchedulingGroupLocked(@android.annotation.NonNull com.android.server.am.ProcessRecord processRecord);

    @com.android.internal.annotations.GuardedBy({"mService"})
    /* renamed from: isBeyondBarrierLocked */
    public abstract boolean lambda$waitForBarrier$2(long j);

    public abstract boolean isDelayBehindServices();

    @com.android.internal.annotations.GuardedBy({"mService"})
    /* renamed from: isDispatchedLocked */
    public abstract boolean lambda$waitForDispatched$3(@android.annotation.NonNull android.content.Intent intent);

    @com.android.internal.annotations.GuardedBy({"mService"})
    /* renamed from: isIdleLocked */
    public abstract boolean lambda$waitForIdle$1();

    @com.android.internal.annotations.GuardedBy({"mService"})
    public abstract boolean onApplicationAttachedLocked(@android.annotation.NonNull com.android.server.am.ProcessRecord processRecord) throws com.android.server.am.BroadcastDeliveryFailedException;

    @com.android.internal.annotations.GuardedBy({"mService"})
    public abstract void onApplicationCleanupLocked(@android.annotation.NonNull com.android.server.am.ProcessRecord processRecord);

    @com.android.internal.annotations.GuardedBy({"mService"})
    public abstract void onApplicationProblemLocked(@android.annotation.NonNull com.android.server.am.ProcessRecord processRecord);

    @com.android.internal.annotations.GuardedBy({"mService"})
    public abstract void onApplicationTimeoutLocked(@android.annotation.NonNull com.android.server.am.ProcessRecord processRecord);

    @com.android.internal.annotations.GuardedBy({"mService"})
    public abstract void onProcessFreezableChangedLocked(@android.annotation.NonNull com.android.server.am.ProcessRecord processRecord);

    public abstract void start(@android.annotation.NonNull android.content.ContentResolver contentResolver);

    public abstract void waitForBarrier(@android.annotation.NonNull java.io.PrintWriter printWriter);

    public abstract void waitForDispatched(@android.annotation.NonNull android.content.Intent intent, @android.annotation.NonNull java.io.PrintWriter printWriter);

    public abstract void waitForIdle(@android.annotation.NonNull java.io.PrintWriter printWriter);

    BroadcastQueue(@android.annotation.NonNull com.android.server.am.ActivityManagerService activityManagerService, @android.annotation.NonNull android.os.Handler handler, @android.annotation.NonNull java.lang.String str, @android.annotation.NonNull com.android.server.am.BroadcastSkipPolicy broadcastSkipPolicy, @android.annotation.NonNull com.android.server.am.BroadcastHistory broadcastHistory) {
        java.util.Objects.requireNonNull(activityManagerService);
        this.mService = activityManagerService;
        java.util.Objects.requireNonNull(handler);
        this.mHandler = handler;
        java.util.Objects.requireNonNull(str);
        this.mQueueName = str;
        java.util.Objects.requireNonNull(broadcastSkipPolicy);
        this.mSkipPolicy = broadcastSkipPolicy;
        java.util.Objects.requireNonNull(broadcastHistory);
        this.mHistory = broadcastHistory;
    }

    static void logw(@android.annotation.NonNull java.lang.String str) {
        android.util.Slog.w(TAG, str);
    }

    static void logv(@android.annotation.NonNull java.lang.String str) {
        android.util.Slog.v(TAG, str);
    }

    static void checkState(boolean z, @android.annotation.NonNull java.lang.String str) {
        if (!z) {
            throw new java.lang.IllegalStateException(str);
        }
    }

    static void checkStateWtf(boolean z, @android.annotation.NonNull java.lang.String str) {
        if (!z) {
            android.util.Slog.wtf(TAG, new java.lang.IllegalStateException(str));
        }
    }

    static int traceBegin(@android.annotation.NonNull java.lang.String str) {
        int hashCode = str.hashCode();
        android.os.Trace.asyncTraceForTrackBegin(64L, TAG, str, hashCode);
        return hashCode;
    }

    static void traceEnd(int i) {
        android.os.Trace.asyncTraceForTrackEnd(64L, TAG, i);
    }

    public java.lang.String toString() {
        return this.mQueueName;
    }

    public void forceDelayBroadcastDelivery(@android.annotation.NonNull java.lang.String str, long j) {
    }

    public void dumpToDropBoxLocked(@android.annotation.Nullable final java.lang.String str) {
        ((com.android.server.DropBoxManagerInternal) com.android.server.LocalServices.getService(com.android.server.DropBoxManagerInternal.class)).addEntry(TAG_DUMP, new com.android.server.DropBoxManagerInternal.EntrySource() { // from class: com.android.server.am.BroadcastQueue$$ExternalSyntheticLambda0
            @Override // com.android.server.DropBoxManagerInternal.EntrySource
            public final void writeTo(java.io.FileDescriptor fileDescriptor) {
                com.android.server.am.BroadcastQueue.this.lambda$dumpToDropBoxLocked$0(str, fileDescriptor);
            }
        }, 2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$dumpToDropBoxLocked$0(java.lang.String str, java.io.FileDescriptor fileDescriptor) throws java.io.IOException {
        java.io.FileOutputStream fileOutputStream = new java.io.FileOutputStream(fileDescriptor);
        try {
            java.io.PrintWriter printWriter = new java.io.PrintWriter(fileOutputStream);
            try {
                printWriter.print("Message: ");
                printWriter.println(str);
                dumpLocked(fileDescriptor, printWriter, null, 0, false, false, false, null, false);
                printWriter.flush();
                printWriter.close();
                fileOutputStream.close();
            } finally {
            }
        } catch (java.lang.Throwable th) {
            try {
                fileOutputStream.close();
            } catch (java.lang.Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }
}
