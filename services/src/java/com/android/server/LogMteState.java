package com.android.server;

/* loaded from: classes.dex */
public class LogMteState {
    public static void register(android.content.Context context) {
        ((android.app.StatsManager) context.getSystemService(android.app.StatsManager.class)).setPullAtomCallback(com.android.internal.util.FrameworkStatsLog.MTE_STATE, (android.app.StatsManager.PullAtomMetadata) null, com.android.internal.util.ConcurrentUtils.DIRECT_EXECUTOR, new android.app.StatsManager.StatsPullAtomCallback() { // from class: com.android.server.LogMteState.1
            public int onPullAtom(int i, java.util.List<android.util.StatsEvent> list) {
                int i2;
                if (i != 10181) {
                    throw new java.lang.UnsupportedOperationException("Unknown tagId=" + i);
                }
                if (com.android.internal.os.Zygote.nativeSupportsMemoryTagging()) {
                    i2 = 2;
                } else {
                    i2 = 1;
                }
                list.add(com.android.internal.util.FrameworkStatsLog.buildStatsEvent(com.android.internal.util.FrameworkStatsLog.MTE_STATE, i2));
                return 0;
            }
        });
    }
}
