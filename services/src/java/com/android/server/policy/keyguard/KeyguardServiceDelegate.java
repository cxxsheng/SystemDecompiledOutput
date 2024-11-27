package com.android.server.policy.keyguard;

/* loaded from: classes2.dex */
public class KeyguardServiceDelegate {
    private static final boolean DEBUG = false;
    private static final int INTERACTIVE_STATE_AWAKE = 2;
    private static final int INTERACTIVE_STATE_GOING_TO_SLEEP = 3;
    private static final int INTERACTIVE_STATE_SLEEP = 0;
    private static final int INTERACTIVE_STATE_WAKING = 1;
    private static final int SCREEN_STATE_OFF = 0;
    private static final int SCREEN_STATE_ON = 2;
    private static final int SCREEN_STATE_TURNING_OFF = 3;
    private static final int SCREEN_STATE_TURNING_ON = 1;
    private static final java.lang.String TAG = "KeyguardServiceDelegate";
    private final com.android.server.policy.keyguard.KeyguardStateMonitor.StateCallback mCallback;
    private final android.content.Context mContext;
    private com.android.server.policy.keyguard.KeyguardServiceDelegate.DrawnListener mDrawnListenerWhenConnect;
    protected com.android.server.policy.keyguard.KeyguardServiceWrapper mKeyguardService;
    private final com.android.server.policy.keyguard.KeyguardServiceDelegate.KeyguardState mKeyguardState = new com.android.server.policy.keyguard.KeyguardServiceDelegate.KeyguardState();
    private final android.service.dreams.DreamManagerInternal.DreamManagerStateListener mDreamManagerStateListener = new android.service.dreams.DreamManagerInternal.DreamManagerStateListener() { // from class: com.android.server.policy.keyguard.KeyguardServiceDelegate.1
        public void onDreamingStarted() {
            com.android.server.policy.keyguard.KeyguardServiceDelegate.this.onDreamingStarted();
        }

        public void onDreamingStopped() {
            com.android.server.policy.keyguard.KeyguardServiceDelegate.this.onDreamingStopped();
        }
    };
    private final android.content.ServiceConnection mKeyguardConnection = new com.android.server.policy.keyguard.KeyguardServiceDelegate.AnonymousClass2();
    private final android.os.Handler mHandler = com.android.server.UiThread.getHandler();

    public interface DrawnListener {
        void onDrawn();
    }

    private static final class KeyguardState {
        public boolean bootCompleted;
        public int currentUser;
        boolean deviceHasKeyguard;
        boolean dreaming;
        public boolean enabled;
        boolean inputRestricted;
        public int interactiveState;
        volatile boolean occluded;
        public int offReason;
        public int screenState;
        boolean secure;
        boolean showing;
        boolean systemIsReady;

        KeyguardState() {
            reset();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void reset() {
            this.showing = true;
            this.occluded = false;
            this.secure = true;
            this.deviceHasKeyguard = true;
            this.enabled = true;
            this.currentUser = com.android.server.am.ProcessList.INVALID_ADJ;
        }
    }

    private final class KeyguardShowDelegate extends com.android.internal.policy.IKeyguardDrawnCallback.Stub {
        private com.android.server.policy.keyguard.KeyguardServiceDelegate.DrawnListener mDrawnListener;

        KeyguardShowDelegate(com.android.server.policy.keyguard.KeyguardServiceDelegate.DrawnListener drawnListener) {
            this.mDrawnListener = drawnListener;
        }

        public void onDrawn() throws android.os.RemoteException {
            if (this.mDrawnListener != null) {
                this.mDrawnListener.onDrawn();
            }
        }
    }

    private final class KeyguardExitDelegate extends com.android.internal.policy.IKeyguardExitCallback.Stub {
        private com.android.server.policy.WindowManagerPolicy.OnKeyguardExitResult mOnKeyguardExitResult;

        KeyguardExitDelegate(com.android.server.policy.WindowManagerPolicy.OnKeyguardExitResult onKeyguardExitResult) {
            this.mOnKeyguardExitResult = onKeyguardExitResult;
        }

        public void onKeyguardExitResult(boolean z) throws android.os.RemoteException {
            if (this.mOnKeyguardExitResult != null) {
                this.mOnKeyguardExitResult.onKeyguardExitResult(z);
            }
        }
    }

    public KeyguardServiceDelegate(android.content.Context context, com.android.server.policy.keyguard.KeyguardStateMonitor.StateCallback stateCallback) {
        this.mContext = context;
        this.mCallback = stateCallback;
    }

    public void bindService(android.content.Context context) {
        android.content.Intent intent = new android.content.Intent();
        android.content.ComponentName unflattenFromString = android.content.ComponentName.unflattenFromString(context.getApplicationContext().getResources().getString(android.R.string.config_hdmiCecSetMenuLanguageActivity));
        intent.addFlags(256);
        intent.setComponent(unflattenFromString);
        if (!context.bindServiceAsUser(intent, this.mKeyguardConnection, 1, this.mHandler, android.os.UserHandle.SYSTEM)) {
            android.util.Log.v(TAG, "*** Keyguard: can't bind to " + unflattenFromString);
            this.mKeyguardState.showing = false;
            this.mKeyguardState.secure = false;
            synchronized (this.mKeyguardState) {
                this.mKeyguardState.deviceHasKeyguard = false;
            }
        }
        ((android.service.dreams.DreamManagerInternal) com.android.server.LocalServices.getService(android.service.dreams.DreamManagerInternal.class)).registerDreamManagerStateListener(this.mDreamManagerStateListener);
    }

    /* renamed from: com.android.server.policy.keyguard.KeyguardServiceDelegate$2, reason: invalid class name */
    class AnonymousClass2 implements android.content.ServiceConnection {
        AnonymousClass2() {
        }

        @Override // android.content.ServiceConnection
        public void onServiceConnected(android.content.ComponentName componentName, android.os.IBinder iBinder) {
            com.android.server.policy.keyguard.KeyguardServiceDelegate.this.mKeyguardService = new com.android.server.policy.keyguard.KeyguardServiceWrapper(com.android.server.policy.keyguard.KeyguardServiceDelegate.this.mContext, com.android.internal.policy.IKeyguardService.Stub.asInterface(iBinder), com.android.server.policy.keyguard.KeyguardServiceDelegate.this.mCallback);
            if (com.android.server.policy.keyguard.KeyguardServiceDelegate.this.mKeyguardState.systemIsReady) {
                com.android.server.policy.keyguard.KeyguardServiceDelegate.this.mKeyguardService.onSystemReady();
                if (com.android.server.policy.keyguard.KeyguardServiceDelegate.this.mKeyguardState.currentUser != -10000) {
                    com.android.server.policy.keyguard.KeyguardServiceDelegate.this.mKeyguardService.setCurrentUser(com.android.server.policy.keyguard.KeyguardServiceDelegate.this.mKeyguardState.currentUser);
                }
                if (com.android.server.policy.keyguard.KeyguardServiceDelegate.this.mKeyguardState.interactiveState == 2 || com.android.server.policy.keyguard.KeyguardServiceDelegate.this.mKeyguardState.interactiveState == 1) {
                    com.android.server.policy.keyguard.KeyguardServiceDelegate.this.mKeyguardService.onStartedWakingUp(0, false);
                }
                if (com.android.server.policy.keyguard.KeyguardServiceDelegate.this.mKeyguardState.interactiveState == 2) {
                    com.android.server.policy.keyguard.KeyguardServiceDelegate.this.mKeyguardService.onFinishedWakingUp();
                }
                if (com.android.server.policy.keyguard.KeyguardServiceDelegate.this.mKeyguardState.screenState == 2 || com.android.server.policy.keyguard.KeyguardServiceDelegate.this.mKeyguardState.screenState == 1) {
                    com.android.server.policy.keyguard.KeyguardServiceDelegate.this.mKeyguardService.onScreenTurningOn(com.android.server.policy.keyguard.KeyguardServiceDelegate.this.new KeyguardShowDelegate(com.android.server.policy.keyguard.KeyguardServiceDelegate.this.mDrawnListenerWhenConnect));
                }
                if (com.android.server.policy.keyguard.KeyguardServiceDelegate.this.mKeyguardState.screenState == 2) {
                    com.android.server.policy.keyguard.KeyguardServiceDelegate.this.mKeyguardService.onScreenTurnedOn();
                }
                com.android.server.policy.keyguard.KeyguardServiceDelegate.this.mDrawnListenerWhenConnect = null;
            }
            if (com.android.server.policy.keyguard.KeyguardServiceDelegate.this.mKeyguardState.bootCompleted) {
                com.android.server.policy.keyguard.KeyguardServiceDelegate.this.mKeyguardService.onBootCompleted();
            }
            if (com.android.server.policy.keyguard.KeyguardServiceDelegate.this.mKeyguardState.occluded) {
                com.android.server.policy.keyguard.KeyguardServiceDelegate.this.mKeyguardService.setOccluded(com.android.server.policy.keyguard.KeyguardServiceDelegate.this.mKeyguardState.occluded, false);
            }
            if (!com.android.server.policy.keyguard.KeyguardServiceDelegate.this.mKeyguardState.enabled) {
                com.android.server.policy.keyguard.KeyguardServiceDelegate.this.mKeyguardService.setKeyguardEnabled(com.android.server.policy.keyguard.KeyguardServiceDelegate.this.mKeyguardState.enabled);
            }
            if (com.android.server.policy.keyguard.KeyguardServiceDelegate.this.mKeyguardState.dreaming) {
                com.android.server.policy.keyguard.KeyguardServiceDelegate.this.mKeyguardService.onDreamingStarted();
            }
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(android.content.ComponentName componentName) {
            com.android.server.policy.keyguard.KeyguardServiceDelegate.this.mKeyguardService = null;
            com.android.server.policy.keyguard.KeyguardServiceDelegate.this.mKeyguardState.reset();
            com.android.server.policy.keyguard.KeyguardServiceDelegate.this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.policy.keyguard.KeyguardServiceDelegate$2$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.policy.keyguard.KeyguardServiceDelegate.AnonymousClass2.lambda$onServiceDisconnected$0();
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ void lambda$onServiceDisconnected$0() {
            try {
                android.app.ActivityTaskManager.getService().setLockScreenShown(true, false);
            } catch (android.os.RemoteException e) {
            }
        }
    }

    public boolean isShowing() {
        if (this.mKeyguardService != null) {
            this.mKeyguardState.showing = this.mKeyguardService.isShowing();
        }
        return this.mKeyguardState.showing;
    }

    public boolean isTrusted() {
        if (this.mKeyguardService != null) {
            return this.mKeyguardService.isTrusted();
        }
        return false;
    }

    public boolean hasKeyguard() {
        return this.mKeyguardState.deviceHasKeyguard;
    }

    public boolean isInputRestricted() {
        if (this.mKeyguardService != null) {
            this.mKeyguardState.inputRestricted = this.mKeyguardService.isInputRestricted();
        }
        return this.mKeyguardState.inputRestricted;
    }

    public void verifyUnlock(com.android.server.policy.WindowManagerPolicy.OnKeyguardExitResult onKeyguardExitResult) {
        if (this.mKeyguardService != null) {
            this.mKeyguardService.verifyUnlock(new com.android.server.policy.keyguard.KeyguardServiceDelegate.KeyguardExitDelegate(onKeyguardExitResult));
        }
    }

    public void setOccluded(boolean z, boolean z2) {
        if (this.mKeyguardService != null && z2) {
            com.android.server.wm.EventLogTags.writeWmSetKeyguardOccluded(z ? 1 : 0, 0, 0, "setOccluded");
            this.mKeyguardService.setOccluded(z, false);
        }
        this.mKeyguardState.occluded = z;
    }

    public boolean isOccluded() {
        return this.mKeyguardState.occluded;
    }

    public void dismiss(com.android.internal.policy.IKeyguardDismissCallback iKeyguardDismissCallback, java.lang.CharSequence charSequence) {
        if (this.mKeyguardService != null) {
            this.mKeyguardService.dismiss(iKeyguardDismissCallback, charSequence);
        }
    }

    public boolean isSecure(int i) {
        if (this.mKeyguardService != null) {
            this.mKeyguardState.secure = this.mKeyguardService.isSecure(i);
        }
        return this.mKeyguardState.secure;
    }

    public void onDreamingStarted() {
        if (this.mKeyguardService != null) {
            this.mKeyguardService.onDreamingStarted();
        }
        this.mKeyguardState.dreaming = true;
    }

    public void onDreamingStopped() {
        if (this.mKeyguardService != null) {
            this.mKeyguardService.onDreamingStopped();
        }
        this.mKeyguardState.dreaming = false;
    }

    public void onStartedWakingUp(int i, boolean z) {
        if (this.mKeyguardService != null) {
            this.mKeyguardService.onStartedWakingUp(i, z);
        }
        this.mKeyguardState.interactiveState = 1;
    }

    public void onFinishedWakingUp() {
        if (this.mKeyguardService != null) {
            this.mKeyguardService.onFinishedWakingUp();
        }
        this.mKeyguardState.interactiveState = 2;
    }

    public void onScreenTurningOff() {
        if (this.mKeyguardService != null) {
            this.mKeyguardService.onScreenTurningOff();
        }
        this.mKeyguardState.screenState = 3;
    }

    public void onScreenTurnedOff() {
        if (this.mKeyguardService != null) {
            this.mKeyguardService.onScreenTurnedOff();
        }
        this.mKeyguardState.screenState = 0;
    }

    public void onScreenTurningOn(com.android.server.policy.keyguard.KeyguardServiceDelegate.DrawnListener drawnListener) {
        if (this.mKeyguardService != null) {
            this.mKeyguardService.onScreenTurningOn(new com.android.server.policy.keyguard.KeyguardServiceDelegate.KeyguardShowDelegate(drawnListener));
        } else {
            android.util.Slog.w(TAG, "onScreenTurningOn(): no keyguard service!");
            this.mDrawnListenerWhenConnect = drawnListener;
        }
        this.mKeyguardState.screenState = 1;
    }

    public void onScreenTurnedOn() {
        if (this.mKeyguardService != null) {
            this.mKeyguardService.onScreenTurnedOn();
        }
        this.mKeyguardState.screenState = 2;
    }

    public void onStartedGoingToSleep(int i) {
        if (this.mKeyguardService != null) {
            this.mKeyguardService.onStartedGoingToSleep(i);
        }
        this.mKeyguardState.offReason = android.view.WindowManagerPolicyConstants.translateSleepReasonToOffReason(i);
        this.mKeyguardState.interactiveState = 3;
    }

    public void onFinishedGoingToSleep(int i, boolean z) {
        if (this.mKeyguardService != null) {
            this.mKeyguardService.onFinishedGoingToSleep(i, z);
        }
        this.mKeyguardState.interactiveState = 0;
    }

    public void setKeyguardEnabled(boolean z) {
        if (this.mKeyguardService != null) {
            this.mKeyguardService.setKeyguardEnabled(z);
        }
        this.mKeyguardState.enabled = z;
    }

    public void onSystemReady() {
        if (this.mKeyguardService != null) {
            this.mKeyguardService.onSystemReady();
        } else {
            this.mKeyguardState.systemIsReady = true;
        }
    }

    public void doKeyguardTimeout(android.os.Bundle bundle) {
        if (this.mKeyguardService != null) {
            this.mKeyguardService.doKeyguardTimeout(bundle);
        }
    }

    public void showDismissibleKeyguard() {
        if (this.mKeyguardService != null) {
            this.mKeyguardService.showDismissibleKeyguard();
        }
    }

    public void setCurrentUser(int i) {
        if (this.mKeyguardService != null) {
            this.mKeyguardService.setCurrentUser(i);
        }
        this.mKeyguardState.currentUser = i;
    }

    public void setSwitchingUser(boolean z) {
        if (this.mKeyguardService != null) {
            this.mKeyguardService.setSwitchingUser(z);
        }
    }

    public void startKeyguardExitAnimation(long j) {
        if (this.mKeyguardService != null) {
            this.mKeyguardService.startKeyguardExitAnimation(j, 0L);
        }
    }

    public void onBootCompleted() {
        if (this.mKeyguardService != null) {
            this.mKeyguardService.onBootCompleted();
        }
        this.mKeyguardState.bootCompleted = true;
    }

    public void onShortPowerPressedGoHome() {
        if (this.mKeyguardService != null) {
            this.mKeyguardService.onShortPowerPressedGoHome();
        }
    }

    public void dismissKeyguardToLaunch(android.content.Intent intent) {
        if (this.mKeyguardService != null) {
            this.mKeyguardService.dismissKeyguardToLaunch(intent);
        }
    }

    public void onSystemKeyPressed(int i) {
        if (this.mKeyguardService != null) {
            this.mKeyguardService.onSystemKeyPressed(i);
        }
    }

    public void dumpDebug(android.util.proto.ProtoOutputStream protoOutputStream, long j) {
        long start = protoOutputStream.start(j);
        protoOutputStream.write(1133871366145L, this.mKeyguardState.showing);
        protoOutputStream.write(1133871366146L, this.mKeyguardState.occluded);
        protoOutputStream.write(1133871366147L, this.mKeyguardState.secure);
        protoOutputStream.write(1159641169924L, this.mKeyguardState.screenState);
        protoOutputStream.write(1159641169925L, this.mKeyguardState.interactiveState);
        protoOutputStream.end(start);
    }

    public void dump(java.lang.String str, java.io.PrintWriter printWriter) {
        printWriter.println(str + TAG);
        java.lang.String str2 = str + "  ";
        printWriter.println(str2 + "showing=" + this.mKeyguardState.showing);
        printWriter.println(str2 + "inputRestricted=" + this.mKeyguardState.inputRestricted);
        printWriter.println(str2 + "occluded=" + this.mKeyguardState.occluded);
        printWriter.println(str2 + "secure=" + this.mKeyguardState.secure);
        printWriter.println(str2 + "dreaming=" + this.mKeyguardState.dreaming);
        printWriter.println(str2 + "systemIsReady=" + this.mKeyguardState.systemIsReady);
        printWriter.println(str2 + "deviceHasKeyguard=" + this.mKeyguardState.deviceHasKeyguard);
        printWriter.println(str2 + "enabled=" + this.mKeyguardState.enabled);
        printWriter.println(str2 + "offReason=" + android.view.WindowManagerPolicyConstants.offReasonToString(this.mKeyguardState.offReason));
        printWriter.println(str2 + "currentUser=" + this.mKeyguardState.currentUser);
        printWriter.println(str2 + "bootCompleted=" + this.mKeyguardState.bootCompleted);
        printWriter.println(str2 + "screenState=" + screenStateToString(this.mKeyguardState.screenState));
        printWriter.println(str2 + "interactiveState=" + interactiveStateToString(this.mKeyguardState.interactiveState));
        if (this.mKeyguardService != null) {
            this.mKeyguardService.dump(str2, printWriter);
        }
    }

    private static java.lang.String screenStateToString(int i) {
        switch (i) {
            case 0:
                return "SCREEN_STATE_OFF";
            case 1:
                return "SCREEN_STATE_TURNING_ON";
            case 2:
                return "SCREEN_STATE_ON";
            case 3:
                return "SCREEN_STATE_TURNING_OFF";
            default:
                return java.lang.Integer.toString(i);
        }
    }

    private static java.lang.String interactiveStateToString(int i) {
        switch (i) {
            case 0:
                return "INTERACTIVE_STATE_SLEEP";
            case 1:
                return "INTERACTIVE_STATE_WAKING";
            case 2:
                return "INTERACTIVE_STATE_AWAKE";
            case 3:
                return "INTERACTIVE_STATE_GOING_TO_SLEEP";
            default:
                return java.lang.Integer.toString(i);
        }
    }
}
