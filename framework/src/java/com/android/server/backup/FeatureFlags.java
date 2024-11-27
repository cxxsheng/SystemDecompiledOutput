package com.android.server.backup;

/* loaded from: classes5.dex */
public interface FeatureFlags {
    boolean enableClearPipeAfterRestoreFile();

    boolean enableIncreaseDatatypesForAgentLogging();

    boolean enableMaxSizeWritesToPipes();

    boolean enableMetricsSystemBackupAgents();

    boolean enableSkippingRestoreLaunchedApps();

    boolean enableVToURestoreForSystemComponentsInAllowlist();
}
