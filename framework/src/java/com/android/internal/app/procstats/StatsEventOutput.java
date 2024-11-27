package com.android.internal.app.procstats;

/* loaded from: classes4.dex */
public class StatsEventOutput {
    java.util.List<android.util.StatsEvent> mOutput;

    public StatsEventOutput(java.util.List<android.util.StatsEvent> list) {
        this.mOutput = list;
    }

    public void write(int i, int i2, java.lang.String str, int i3, int i4, int i5, int i6, int i7, int i8, int i9, int i10, int i11, int i12, int i13) {
        this.mOutput.add(com.android.internal.util.FrameworkStatsLog.buildStatsEvent(i, i2, str, i3, i4, i5, i6, i7, i8, i9, i10, i11, i12, i13));
    }

    public void write(int i, int i2, java.lang.String str, int i3, java.lang.String str2, int i4, int i5, int i6, int i7, int i8, java.lang.String str3) {
        this.mOutput.add(com.android.internal.util.FrameworkStatsLog.buildStatsEvent(i, i2, str, i3, str2, i4, i5, i6, i7, i8, str3));
    }
}
