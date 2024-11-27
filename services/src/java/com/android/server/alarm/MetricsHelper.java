package com.android.server.alarm;

/* loaded from: classes.dex */
class MetricsHelper {
    private final android.content.Context mContext;
    private final java.lang.Object mLock;

    MetricsHelper(android.content.Context context, java.lang.Object obj) {
        this.mContext = context;
        this.mLock = obj;
    }

    void registerPuller(final java.util.function.Supplier<com.android.server.alarm.AlarmStore> supplier) {
        ((android.app.StatsManager) this.mContext.getSystemService(android.app.StatsManager.class)).setPullAtomCallback(com.android.internal.util.FrameworkStatsLog.PENDING_ALARM_INFO, (android.app.StatsManager.PullAtomMetadata) null, com.android.internal.util.jobs.ConcurrentUtils.DIRECT_EXECUTOR, new android.app.StatsManager.StatsPullAtomCallback() { // from class: com.android.server.alarm.MetricsHelper$$ExternalSyntheticLambda0
            public final int onPullAtom(int i, java.util.List list) {
                int lambda$registerPuller$12;
                lambda$registerPuller$12 = com.android.server.alarm.MetricsHelper.this.lambda$registerPuller$12(supplier, i, list);
                return lambda$registerPuller$12;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ int lambda$registerPuller$12(java.util.function.Supplier supplier, int i, java.util.List list) {
        if (i != 10106) {
            throw new java.lang.UnsupportedOperationException("Unknown tag" + i);
        }
        final long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
        synchronized (this.mLock) {
            com.android.server.alarm.AlarmStore alarmStore = (com.android.server.alarm.AlarmStore) supplier.get();
            list.add(com.android.internal.util.FrameworkStatsLog.buildStatsEvent(i, alarmStore.size(), alarmStore.getCount(new java.util.function.Predicate() { // from class: com.android.server.alarm.MetricsHelper$$ExternalSyntheticLambda1
                @Override // java.util.function.Predicate
                public final boolean test(java.lang.Object obj) {
                    boolean lambda$registerPuller$0;
                    lambda$registerPuller$0 = com.android.server.alarm.MetricsHelper.lambda$registerPuller$0((com.android.server.alarm.Alarm) obj);
                    return lambda$registerPuller$0;
                }
            }), alarmStore.getCount(new java.util.function.Predicate() { // from class: com.android.server.alarm.MetricsHelper$$ExternalSyntheticLambda4
                @Override // java.util.function.Predicate
                public final boolean test(java.lang.Object obj) {
                    boolean z;
                    z = ((com.android.server.alarm.Alarm) obj).wakeup;
                    return z;
                }
            }), alarmStore.getCount(new java.util.function.Predicate() { // from class: com.android.server.alarm.MetricsHelper$$ExternalSyntheticLambda5
                @Override // java.util.function.Predicate
                public final boolean test(java.lang.Object obj) {
                    boolean lambda$registerPuller$2;
                    lambda$registerPuller$2 = com.android.server.alarm.MetricsHelper.lambda$registerPuller$2((com.android.server.alarm.Alarm) obj);
                    return lambda$registerPuller$2;
                }
            }), alarmStore.getCount(new java.util.function.Predicate() { // from class: com.android.server.alarm.MetricsHelper$$ExternalSyntheticLambda6
                @Override // java.util.function.Predicate
                public final boolean test(java.lang.Object obj) {
                    boolean lambda$registerPuller$3;
                    lambda$registerPuller$3 = com.android.server.alarm.MetricsHelper.lambda$registerPuller$3((com.android.server.alarm.Alarm) obj);
                    return lambda$registerPuller$3;
                }
            }), alarmStore.getCount(new java.util.function.Predicate() { // from class: com.android.server.alarm.MetricsHelper$$ExternalSyntheticLambda7
                @Override // java.util.function.Predicate
                public final boolean test(java.lang.Object obj) {
                    boolean lambda$registerPuller$4;
                    lambda$registerPuller$4 = com.android.server.alarm.MetricsHelper.lambda$registerPuller$4((com.android.server.alarm.Alarm) obj);
                    return lambda$registerPuller$4;
                }
            }), alarmStore.getCount(new java.util.function.Predicate() { // from class: com.android.server.alarm.MetricsHelper$$ExternalSyntheticLambda8
                @Override // java.util.function.Predicate
                public final boolean test(java.lang.Object obj) {
                    boolean lambda$registerPuller$5;
                    lambda$registerPuller$5 = com.android.server.alarm.MetricsHelper.lambda$registerPuller$5((com.android.server.alarm.Alarm) obj);
                    return lambda$registerPuller$5;
                }
            }), alarmStore.getCount(new java.util.function.Predicate() { // from class: com.android.server.alarm.MetricsHelper$$ExternalSyntheticLambda9
                @Override // java.util.function.Predicate
                public final boolean test(java.lang.Object obj) {
                    boolean lambda$registerPuller$6;
                    lambda$registerPuller$6 = com.android.server.alarm.MetricsHelper.lambda$registerPuller$6((com.android.server.alarm.Alarm) obj);
                    return lambda$registerPuller$6;
                }
            }), alarmStore.getCount(new java.util.function.Predicate() { // from class: com.android.server.alarm.MetricsHelper$$ExternalSyntheticLambda10
                @Override // java.util.function.Predicate
                public final boolean test(java.lang.Object obj) {
                    boolean lambda$registerPuller$7;
                    lambda$registerPuller$7 = com.android.server.alarm.MetricsHelper.lambda$registerPuller$7((com.android.server.alarm.Alarm) obj);
                    return lambda$registerPuller$7;
                }
            }), alarmStore.getCount(new java.util.function.Predicate() { // from class: com.android.server.alarm.MetricsHelper$$ExternalSyntheticLambda11
                @Override // java.util.function.Predicate
                public final boolean test(java.lang.Object obj) {
                    boolean lambda$registerPuller$8;
                    lambda$registerPuller$8 = com.android.server.alarm.MetricsHelper.lambda$registerPuller$8(elapsedRealtime, (com.android.server.alarm.Alarm) obj);
                    return lambda$registerPuller$8;
                }
            }), alarmStore.getCount(new java.util.function.Predicate() { // from class: com.android.server.alarm.MetricsHelper$$ExternalSyntheticLambda12
                @Override // java.util.function.Predicate
                public final boolean test(java.lang.Object obj) {
                    boolean lambda$registerPuller$9;
                    lambda$registerPuller$9 = com.android.server.alarm.MetricsHelper.lambda$registerPuller$9((com.android.server.alarm.Alarm) obj);
                    return lambda$registerPuller$9;
                }
            }), alarmStore.getCount(new java.util.function.Predicate() { // from class: com.android.server.alarm.MetricsHelper$$ExternalSyntheticLambda2
                @Override // java.util.function.Predicate
                public final boolean test(java.lang.Object obj) {
                    boolean lambda$registerPuller$10;
                    lambda$registerPuller$10 = com.android.server.alarm.MetricsHelper.lambda$registerPuller$10((com.android.server.alarm.Alarm) obj);
                    return lambda$registerPuller$10;
                }
            }), alarmStore.getCount(new java.util.function.Predicate() { // from class: com.android.server.alarm.MetricsHelper$$ExternalSyntheticLambda3
                @Override // java.util.function.Predicate
                public final boolean test(java.lang.Object obj) {
                    boolean lambda$registerPuller$11;
                    lambda$registerPuller$11 = com.android.server.alarm.MetricsHelper.lambda$registerPuller$11((com.android.server.alarm.Alarm) obj);
                    return lambda$registerPuller$11;
                }
            })));
        }
        return 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$registerPuller$0(com.android.server.alarm.Alarm alarm) {
        return alarm.windowLength == 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$registerPuller$2(com.android.server.alarm.Alarm alarm) {
        return (alarm.flags & 4) != 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$registerPuller$3(com.android.server.alarm.Alarm alarm) {
        return (alarm.flags & 64) != 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$registerPuller$4(com.android.server.alarm.Alarm alarm) {
        return alarm.operation != null && alarm.operation.isForegroundService();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$registerPuller$5(com.android.server.alarm.Alarm alarm) {
        return alarm.operation != null && alarm.operation.isActivity();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$registerPuller$6(com.android.server.alarm.Alarm alarm) {
        return alarm.operation != null && alarm.operation.isService();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$registerPuller$7(com.android.server.alarm.Alarm alarm) {
        return alarm.listener != null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$registerPuller$8(long j, com.android.server.alarm.Alarm alarm) {
        return alarm.getRequestedElapsed() > j + 31536000000L;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$registerPuller$9(com.android.server.alarm.Alarm alarm) {
        return alarm.repeatInterval != 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$registerPuller$10(com.android.server.alarm.Alarm alarm) {
        return alarm.alarmClock != null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$registerPuller$11(com.android.server.alarm.Alarm alarm) {
        return com.android.server.alarm.AlarmManagerService.isRtc(alarm.type);
    }

    private static int reasonToStatsReason(int i) {
        switch (i) {
            case 0:
                return 1;
            case 1:
                return 2;
            case 2:
                return 3;
            case 3:
                return 4;
            case 4:
                return 5;
            case 5:
                return 6;
            default:
                return 0;
        }
    }

    static void pushAlarmScheduled(com.android.server.alarm.Alarm alarm, int i) {
        com.android.internal.util.FrameworkStatsLog.write(com.android.internal.util.FrameworkStatsLog.ALARM_SCHEDULED, alarm.uid, alarm.windowLength == 0, alarm.wakeup, (alarm.flags & 4) != 0, alarm.alarmClock != null, alarm.repeatInterval != 0, reasonToStatsReason(alarm.exactAllowReason), com.android.server.alarm.AlarmManagerService.isRtc(alarm.type), android.app.ActivityManager.processStateAmToProto(i));
    }

    static void pushAlarmBatchDelivered(int i, int i2, int[] iArr, int[] iArr2, int[] iArr3) {
        com.android.internal.util.FrameworkStatsLog.write(com.android.internal.util.FrameworkStatsLog.ALARM_BATCH_DELIVERED, i, i2, iArr, iArr2, iArr3);
    }
}
