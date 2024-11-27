package com.android.server.backup;

/* loaded from: classes5.dex */
public final class FeatureFlagsImpl implements com.android.server.backup.FeatureFlags {
    @Override // com.android.server.backup.FeatureFlags
    public boolean enableClearPipeAfterRestoreFile() {
        return true;
    }

    @Override // com.android.server.backup.FeatureFlags
    public boolean enableIncreaseDatatypesForAgentLogging() {
        return false;
    }

    @Override // com.android.server.backup.FeatureFlags
    public boolean enableMaxSizeWritesToPipes() {
        return true;
    }

    @Override // com.android.server.backup.FeatureFlags
    public boolean enableMetricsSystemBackupAgents() {
        return false;
    }

    @Override // com.android.server.backup.FeatureFlags
    public boolean enableSkippingRestoreLaunchedApps() {
        return false;
    }

    @Override // com.android.server.backup.FeatureFlags
    public boolean enableVToURestoreForSystemComponentsInAllowlist() {
        return false;
    }
}
