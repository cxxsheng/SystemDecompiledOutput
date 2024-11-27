package com.android.server.policy.keyguard;

/* loaded from: classes2.dex */
public class KeyguardServiceWrapper implements com.android.internal.policy.IKeyguardService {
    private java.lang.String TAG = "KeyguardServiceWrapper";
    private com.android.server.policy.keyguard.KeyguardStateMonitor mKeyguardStateMonitor;
    private com.android.internal.policy.IKeyguardService mService;

    public KeyguardServiceWrapper(android.content.Context context, com.android.internal.policy.IKeyguardService iKeyguardService, com.android.server.policy.keyguard.KeyguardStateMonitor.StateCallback stateCallback) {
        this.mService = iKeyguardService;
        this.mKeyguardStateMonitor = new com.android.server.policy.keyguard.KeyguardStateMonitor(context, iKeyguardService, stateCallback);
    }

    public void verifyUnlock(com.android.internal.policy.IKeyguardExitCallback iKeyguardExitCallback) {
        try {
            this.mService.verifyUnlock(iKeyguardExitCallback);
        } catch (android.os.RemoteException e) {
            android.util.Slog.w(this.TAG, "Remote Exception", e);
        }
    }

    public void setOccluded(boolean z, boolean z2) {
        try {
            this.mService.setOccluded(z, z2);
        } catch (android.os.RemoteException e) {
            android.util.Slog.w(this.TAG, "Remote Exception", e);
        }
    }

    public void addStateMonitorCallback(com.android.internal.policy.IKeyguardStateCallback iKeyguardStateCallback) {
        try {
            this.mService.addStateMonitorCallback(iKeyguardStateCallback);
        } catch (android.os.RemoteException e) {
            android.util.Slog.w(this.TAG, "Remote Exception", e);
        }
    }

    public void dismiss(com.android.internal.policy.IKeyguardDismissCallback iKeyguardDismissCallback, java.lang.CharSequence charSequence) {
        try {
            this.mService.dismiss(iKeyguardDismissCallback, charSequence);
        } catch (android.os.RemoteException e) {
            android.util.Slog.w(this.TAG, "Remote Exception", e);
        }
    }

    public void onDreamingStarted() {
        try {
            this.mService.onDreamingStarted();
        } catch (android.os.RemoteException e) {
            android.util.Slog.w(this.TAG, "Remote Exception", e);
        }
    }

    public void onDreamingStopped() {
        try {
            this.mService.onDreamingStopped();
        } catch (android.os.RemoteException e) {
            android.util.Slog.w(this.TAG, "Remote Exception", e);
        }
    }

    public void onStartedGoingToSleep(int i) {
        try {
            this.mService.onStartedGoingToSleep(i);
        } catch (android.os.RemoteException e) {
            android.util.Slog.w(this.TAG, "Remote Exception", e);
        }
    }

    public void onFinishedGoingToSleep(int i, boolean z) {
        try {
            this.mService.onFinishedGoingToSleep(i, z);
        } catch (android.os.RemoteException e) {
            android.util.Slog.w(this.TAG, "Remote Exception", e);
        }
    }

    public void onStartedWakingUp(int i, boolean z) {
        try {
            this.mService.onStartedWakingUp(i, z);
        } catch (android.os.RemoteException e) {
            android.util.Slog.w(this.TAG, "Remote Exception", e);
        }
    }

    public void onFinishedWakingUp() {
        try {
            this.mService.onFinishedWakingUp();
        } catch (android.os.RemoteException e) {
            android.util.Slog.w(this.TAG, "Remote Exception", e);
        }
    }

    public void onScreenTurningOn(com.android.internal.policy.IKeyguardDrawnCallback iKeyguardDrawnCallback) {
        try {
            this.mService.onScreenTurningOn(iKeyguardDrawnCallback);
        } catch (android.os.RemoteException e) {
            android.util.Slog.w(this.TAG, "Remote Exception", e);
        }
    }

    public void onScreenTurnedOn() {
        try {
            this.mService.onScreenTurnedOn();
        } catch (android.os.RemoteException e) {
            android.util.Slog.w(this.TAG, "Remote Exception", e);
        }
    }

    public void onScreenTurningOff() {
        try {
            this.mService.onScreenTurningOff();
        } catch (android.os.RemoteException e) {
            android.util.Slog.w(this.TAG, "Remote Exception", e);
        }
    }

    public void onScreenTurnedOff() {
        try {
            this.mService.onScreenTurnedOff();
        } catch (android.os.RemoteException e) {
            android.util.Slog.w(this.TAG, "Remote Exception", e);
        }
    }

    public void setKeyguardEnabled(boolean z) {
        try {
            this.mService.setKeyguardEnabled(z);
        } catch (android.os.RemoteException e) {
            android.util.Slog.w(this.TAG, "Remote Exception", e);
        }
    }

    public void onSystemReady() {
        try {
            this.mService.onSystemReady();
        } catch (android.os.RemoteException e) {
            android.util.Slog.w(this.TAG, "Remote Exception", e);
        }
    }

    public void doKeyguardTimeout(android.os.Bundle bundle) {
        int currentUser = this.mKeyguardStateMonitor.getCurrentUser();
        if (this.mKeyguardStateMonitor.isSecure(currentUser)) {
            this.mKeyguardStateMonitor.onShowingStateChanged(true, currentUser);
        }
        try {
            this.mService.doKeyguardTimeout(bundle);
        } catch (android.os.RemoteException e) {
            android.util.Slog.w(this.TAG, "Remote Exception", e);
        }
    }

    public void showDismissibleKeyguard() {
        try {
            this.mService.showDismissibleKeyguard();
        } catch (android.os.RemoteException e) {
            android.util.Slog.w(this.TAG, "Remote Exception", e);
        }
    }

    public void setSwitchingUser(boolean z) {
        try {
            this.mService.setSwitchingUser(z);
        } catch (android.os.RemoteException e) {
            android.util.Slog.w(this.TAG, "Remote Exception", e);
        }
    }

    public void setCurrentUser(int i) {
        this.mKeyguardStateMonitor.setCurrentUser(i);
        try {
            this.mService.setCurrentUser(i);
        } catch (android.os.RemoteException e) {
            android.util.Slog.w(this.TAG, "Remote Exception", e);
        }
    }

    public void onBootCompleted() {
        try {
            this.mService.onBootCompleted();
        } catch (android.os.RemoteException e) {
            android.util.Slog.w(this.TAG, "Remote Exception", e);
        }
    }

    public void startKeyguardExitAnimation(long j, long j2) {
        try {
            this.mService.startKeyguardExitAnimation(j, j2);
        } catch (android.os.RemoteException e) {
            android.util.Slog.w(this.TAG, "Remote Exception", e);
        }
    }

    public void onShortPowerPressedGoHome() {
        try {
            this.mService.onShortPowerPressedGoHome();
        } catch (android.os.RemoteException e) {
            android.util.Slog.w(this.TAG, "Remote Exception", e);
        }
    }

    public void dismissKeyguardToLaunch(android.content.Intent intent) {
        try {
            this.mService.dismissKeyguardToLaunch(intent);
        } catch (android.os.RemoteException e) {
            android.util.Slog.w(this.TAG, "Remote Exception", e);
        }
    }

    public void onSystemKeyPressed(int i) {
        try {
            this.mService.onSystemKeyPressed(i);
        } catch (android.os.RemoteException e) {
            android.util.Slog.w(this.TAG, "Remote Exception", e);
        }
    }

    public android.os.IBinder asBinder() {
        return this.mService.asBinder();
    }

    public boolean isShowing() {
        return this.mKeyguardStateMonitor.isShowing();
    }

    public boolean isTrusted() {
        return this.mKeyguardStateMonitor.isTrusted();
    }

    public boolean isSecure(int i) {
        return this.mKeyguardStateMonitor.isSecure(i);
    }

    public boolean isInputRestricted() {
        return this.mKeyguardStateMonitor.isInputRestricted();
    }

    public void dump(java.lang.String str, java.io.PrintWriter printWriter) {
        this.mKeyguardStateMonitor.dump(str, printWriter);
    }
}
