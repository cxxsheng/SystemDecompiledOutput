package com.android.server.job.controllers.idle;

/* loaded from: classes2.dex */
public interface IdlenessTracker {
    void dump(android.util.proto.ProtoOutputStream protoOutputStream, long j);

    void dump(java.io.PrintWriter printWriter);

    void dumpConstants(android.util.IndentingPrintWriter indentingPrintWriter);

    boolean isIdle();

    void onBatteryStateChanged(boolean z, boolean z2);

    void processConstant(@android.annotation.NonNull android.provider.DeviceConfig.Properties properties, @android.annotation.NonNull java.lang.String str);

    void startTracking(android.content.Context context, com.android.server.job.JobSchedulerService jobSchedulerService, com.android.server.job.controllers.idle.IdlenessListener idlenessListener);
}
