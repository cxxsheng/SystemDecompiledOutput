package com.android.server.location.injector;

/* loaded from: classes2.dex */
public class SystemLocationPowerSaveModeHelper extends com.android.server.location.injector.LocationPowerSaveModeHelper implements java.util.function.Consumer<android.os.PowerSaveState> {
    private final android.content.Context mContext;
    private volatile int mLocationPowerSaveMode;
    private boolean mReady;

    public SystemLocationPowerSaveModeHelper(android.content.Context context) {
        this.mContext = context;
    }

    public void onSystemReady() {
        if (this.mReady) {
            return;
        }
        ((android.os.PowerManagerInternal) com.android.server.LocalServices.getService(android.os.PowerManagerInternal.class)).registerLowPowerModeObserver(1, this);
        android.os.PowerManager powerManager = (android.os.PowerManager) this.mContext.getSystemService(android.os.PowerManager.class);
        java.util.Objects.requireNonNull(powerManager);
        this.mLocationPowerSaveMode = powerManager.getLocationPowerSaveMode();
        this.mReady = true;
    }

    @Override // java.util.function.Consumer
    public void accept(android.os.PowerSaveState powerSaveState) {
        final int i;
        if (!powerSaveState.batterySaverEnabled) {
            i = 0;
        } else {
            i = powerSaveState.locationMode;
        }
        if (i == this.mLocationPowerSaveMode) {
            return;
        }
        this.mLocationPowerSaveMode = i;
        com.android.server.FgThread.getHandler().post(new java.lang.Runnable() { // from class: com.android.server.location.injector.SystemLocationPowerSaveModeHelper$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.location.injector.SystemLocationPowerSaveModeHelper.this.lambda$accept$0(i);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$accept$0(int i) {
        notifyLocationPowerSaveModeChanged(i);
    }

    @Override // com.android.server.location.injector.LocationPowerSaveModeHelper
    public int getLocationPowerSaveMode() {
        com.android.internal.util.Preconditions.checkState(this.mReady);
        return this.mLocationPowerSaveMode;
    }
}
