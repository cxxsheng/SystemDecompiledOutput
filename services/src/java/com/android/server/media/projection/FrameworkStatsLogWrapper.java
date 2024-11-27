package com.android.server.media.projection;

/* loaded from: classes2.dex */
public class FrameworkStatsLogWrapper {
    public void writeStateChanged(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
        com.android.internal.util.FrameworkStatsLog.write(i, i2, i3, i4, i5, i6, i7, i8);
    }

    public void writeTargetChanged(int i, int i2, int i3, int i4, int i5, int i6) {
        com.android.internal.util.FrameworkStatsLog.write(i, i2, i3, i4, i5, i6);
    }
}
