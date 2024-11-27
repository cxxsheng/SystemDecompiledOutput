package com.android.server.pm;

@com.android.internal.annotations.VisibleForTesting(visibility = com.android.internal.annotations.VisibleForTesting.Visibility.PRIVATE)
/* loaded from: classes2.dex */
public final class ComputerLocked extends com.android.server.pm.ComputerEngine {
    ComputerLocked(com.android.server.pm.PackageManagerService.Snapshot snapshot) {
        super(snapshot, -1);
    }

    @Override // com.android.server.pm.ComputerEngine
    protected android.content.ComponentName resolveComponentName() {
        return this.mService.getResolveComponentName();
    }

    @Override // com.android.server.pm.ComputerEngine
    protected android.content.pm.ActivityInfo instantAppInstallerActivity() {
        return this.mService.mInstantAppInstallerActivity;
    }

    @Override // com.android.server.pm.ComputerEngine
    protected android.content.pm.ApplicationInfo androidApplication() {
        return this.mService.getCoreAndroidApplication();
    }
}
