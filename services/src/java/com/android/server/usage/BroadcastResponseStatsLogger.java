package com.android.server.usage;

/* loaded from: classes2.dex */
public class BroadcastResponseStatsLogger {
    private static final int MAX_LOG_SIZE;
    private final java.lang.Object mLock = new java.lang.Object();

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final com.android.server.usage.BroadcastResponseStatsLogger.LogBuffer mBroadcastEventsBuffer = new com.android.server.usage.BroadcastResponseStatsLogger.LogBuffer(new java.util.function.Supplier() { // from class: com.android.server.usage.BroadcastResponseStatsLogger$$ExternalSyntheticLambda0
        @Override // java.util.function.Supplier
        public final java.lang.Object get() {
            return com.android.server.usage.BroadcastResponseStatsLogger.m8214$r8$lambda$Ar2wcV2w4QjqlPjHFaij6oV5bQ();
        }
    }, new java.util.function.IntFunction() { // from class: com.android.server.usage.BroadcastResponseStatsLogger$$ExternalSyntheticLambda1
        @Override // java.util.function.IntFunction
        public final java.lang.Object apply(int i) {
            java.lang.Object lambda$new$0;
            lambda$new$0 = com.android.server.usage.BroadcastResponseStatsLogger.lambda$new$0(i);
            return lambda$new$0;
        }
    }, MAX_LOG_SIZE);

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final com.android.server.usage.BroadcastResponseStatsLogger.LogBuffer mNotificationEventsBuffer = new com.android.server.usage.BroadcastResponseStatsLogger.LogBuffer(new java.util.function.Supplier() { // from class: com.android.server.usage.BroadcastResponseStatsLogger$$ExternalSyntheticLambda2
        @Override // java.util.function.Supplier
        public final java.lang.Object get() {
            return com.android.server.usage.BroadcastResponseStatsLogger.m8215$r8$lambda$lW8VT65e47HoACtwf8YZwpc8Xs();
        }
    }, new java.util.function.IntFunction() { // from class: com.android.server.usage.BroadcastResponseStatsLogger$$ExternalSyntheticLambda3
        @Override // java.util.function.IntFunction
        public final java.lang.Object apply(int i) {
            java.lang.Object lambda$new$1;
            lambda$new$1 = com.android.server.usage.BroadcastResponseStatsLogger.lambda$new$1(i);
            return lambda$new$1;
        }
    }, MAX_LOG_SIZE);

    private interface Data {
        void reset();
    }

    /* renamed from: $r8$lambda$A-r2wcV2w4QjqlPjHFaij6oV5bQ, reason: not valid java name */
    public static /* synthetic */ com.android.server.usage.BroadcastResponseStatsLogger.BroadcastEvent m8214$r8$lambda$Ar2wcV2w4QjqlPjHFaij6oV5bQ() {
        return new com.android.server.usage.BroadcastResponseStatsLogger.BroadcastEvent();
    }

    /* renamed from: $r8$lambda$lW8VT65-e47HoACtwf8YZwpc8Xs, reason: not valid java name */
    public static /* synthetic */ com.android.server.usage.BroadcastResponseStatsLogger.NotificationEvent m8215$r8$lambda$lW8VT65e47HoACtwf8YZwpc8Xs() {
        return new com.android.server.usage.BroadcastResponseStatsLogger.NotificationEvent();
    }

    static {
        MAX_LOG_SIZE = android.app.ActivityManager.isLowRamDeviceStatic() ? 20 : 50;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ java.lang.Object lambda$new$0(int i) {
        return new com.android.server.usage.BroadcastResponseStatsLogger.BroadcastEvent[i];
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ java.lang.Object lambda$new$1(int i) {
        return new com.android.server.usage.BroadcastResponseStatsLogger.NotificationEvent[i];
    }

    void logBroadcastDispatchEvent(int i, @android.annotation.NonNull java.lang.String str, android.os.UserHandle userHandle, long j, long j2, int i2) {
        synchronized (this.mLock) {
            try {
                if (com.android.server.usage.UsageStatsService.DEBUG_RESPONSE_STATS) {
                    android.util.Slog.d("ResponseStatsTracker", getBroadcastDispatchEventLog(i, str, userHandle.getIdentifier(), j, j2, i2));
                }
                this.mBroadcastEventsBuffer.logBroadcastDispatchEvent(i, str, userHandle, j, j2, i2);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    void logNotificationEvent(int i, @android.annotation.NonNull java.lang.String str, android.os.UserHandle userHandle, long j) {
        synchronized (this.mLock) {
            try {
                if (com.android.server.usage.UsageStatsService.DEBUG_RESPONSE_STATS) {
                    android.util.Slog.d("ResponseStatsTracker", getNotificationEventLog(i, str, userHandle.getIdentifier(), j));
                }
                this.mNotificationEventsBuffer.logNotificationEvent(i, str, userHandle, j);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    void dumpLogs(com.android.internal.util.IndentingPrintWriter indentingPrintWriter) {
        synchronized (this.mLock) {
            indentingPrintWriter.println("Broadcast events (most recent first):");
            indentingPrintWriter.increaseIndent();
            this.mBroadcastEventsBuffer.reverseDump(indentingPrintWriter);
            indentingPrintWriter.decreaseIndent();
            indentingPrintWriter.println();
            indentingPrintWriter.println("Notification events (most recent first):");
            indentingPrintWriter.increaseIndent();
            this.mNotificationEventsBuffer.reverseDump(indentingPrintWriter);
            indentingPrintWriter.decreaseIndent();
        }
    }

    private static final class LogBuffer<T extends com.android.server.usage.BroadcastResponseStatsLogger.Data> extends com.android.internal.util.RingBuffer<T> {
        LogBuffer(java.util.function.Supplier<T> supplier, java.util.function.IntFunction<T[]> intFunction, int i) {
            super(supplier, intFunction, i);
        }

        void logBroadcastDispatchEvent(int i, @android.annotation.NonNull java.lang.String str, android.os.UserHandle userHandle, long j, long j2, int i2) {
            com.android.server.usage.BroadcastResponseStatsLogger.Data data = (com.android.server.usage.BroadcastResponseStatsLogger.Data) getNextSlot();
            if (data == null) {
                return;
            }
            data.reset();
            com.android.server.usage.BroadcastResponseStatsLogger.BroadcastEvent broadcastEvent = (com.android.server.usage.BroadcastResponseStatsLogger.BroadcastEvent) data;
            broadcastEvent.sourceUid = i;
            broadcastEvent.targetUserId = userHandle.getIdentifier();
            broadcastEvent.targetUidProcessState = i2;
            broadcastEvent.targetPackage = str;
            broadcastEvent.idForResponseEvent = j;
            broadcastEvent.timestampMs = j2;
        }

        void logNotificationEvent(int i, @android.annotation.NonNull java.lang.String str, android.os.UserHandle userHandle, long j) {
            com.android.server.usage.BroadcastResponseStatsLogger.Data data = (com.android.server.usage.BroadcastResponseStatsLogger.Data) getNextSlot();
            if (data == null) {
                return;
            }
            data.reset();
            com.android.server.usage.BroadcastResponseStatsLogger.NotificationEvent notificationEvent = (com.android.server.usage.BroadcastResponseStatsLogger.NotificationEvent) data;
            notificationEvent.type = i;
            notificationEvent.packageName = str;
            notificationEvent.userId = userHandle.getIdentifier();
            notificationEvent.timestampMs = j;
        }

        public void reverseDump(com.android.internal.util.IndentingPrintWriter indentingPrintWriter) {
            com.android.server.usage.BroadcastResponseStatsLogger.Data[] dataArr = (com.android.server.usage.BroadcastResponseStatsLogger.Data[]) toArray();
            for (int length = dataArr.length - 1; length >= 0; length--) {
                if (dataArr[length] != null) {
                    indentingPrintWriter.println(getContent(dataArr[length]));
                }
            }
        }

        @android.annotation.NonNull
        public java.lang.String getContent(com.android.server.usage.BroadcastResponseStatsLogger.Data data) {
            return data.toString();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @android.annotation.NonNull
    public static java.lang.String getBroadcastDispatchEventLog(int i, @android.annotation.NonNull java.lang.String str, int i2, long j, long j2, int i3) {
        return android.text.TextUtils.formatSimple("broadcast:%s; srcUid=%d, tgtPkg=%s, tgtUsr=%d, id=%d, state=%s", new java.lang.Object[]{android.util.TimeUtils.formatDuration(j2), java.lang.Integer.valueOf(i), str, java.lang.Integer.valueOf(i2), java.lang.Long.valueOf(j), android.app.ActivityManager.procStateToString(i3)});
    }

    /* JADX INFO: Access modifiers changed from: private */
    @android.annotation.NonNull
    public static java.lang.String getNotificationEventLog(int i, @android.annotation.NonNull java.lang.String str, int i2, long j) {
        return android.text.TextUtils.formatSimple("notification:%s; event=<%s>, pkg=%s, usr=%d", new java.lang.Object[]{android.util.TimeUtils.formatDuration(j), notificationEventToString(i), str, java.lang.Integer.valueOf(i2)});
    }

    @android.annotation.NonNull
    private static java.lang.String notificationEventToString(int i) {
        switch (i) {
            case 0:
                return "posted";
            case 1:
                return "updated";
            case 2:
                return "cancelled";
            default:
                return java.lang.String.valueOf(i);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static final class BroadcastEvent implements com.android.server.usage.BroadcastResponseStatsLogger.Data {
        public long idForResponseEvent;
        public int sourceUid;
        public java.lang.String targetPackage;
        public int targetUidProcessState;
        public int targetUserId;
        public long timestampMs;

        private BroadcastEvent() {
        }

        @Override // com.android.server.usage.BroadcastResponseStatsLogger.Data
        public void reset() {
            this.targetPackage = null;
        }

        public java.lang.String toString() {
            return com.android.server.usage.BroadcastResponseStatsLogger.getBroadcastDispatchEventLog(this.sourceUid, this.targetPackage, this.targetUserId, this.idForResponseEvent, this.timestampMs, this.targetUidProcessState);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static final class NotificationEvent implements com.android.server.usage.BroadcastResponseStatsLogger.Data {
        public java.lang.String packageName;
        public long timestampMs;
        public int type;
        public int userId;

        private NotificationEvent() {
        }

        @Override // com.android.server.usage.BroadcastResponseStatsLogger.Data
        public void reset() {
            this.packageName = null;
        }

        public java.lang.String toString() {
            return com.android.server.usage.BroadcastResponseStatsLogger.getNotificationEventLog(this.type, this.packageName, this.userId, this.timestampMs);
        }
    }
}
