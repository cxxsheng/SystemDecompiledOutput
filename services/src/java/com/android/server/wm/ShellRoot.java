package com.android.server.wm;

/* loaded from: classes3.dex */
public class ShellRoot {
    private static final java.lang.String TAG = "ShellRoot";
    private android.view.IWindow mAccessibilityWindow;
    private android.os.IBinder.DeathRecipient mAccessibilityWindowDeath;
    private android.view.IWindow mClient;
    private final android.os.IBinder.DeathRecipient mDeathRecipient;
    private final com.android.server.wm.DisplayContent mDisplayContent;
    private final int mShellRootLayer;
    private android.view.SurfaceControl mSurfaceControl;
    private com.android.server.wm.WindowToken mToken;
    private int mWindowType;

    ShellRoot(@android.annotation.NonNull android.view.IWindow iWindow, @android.annotation.NonNull com.android.server.wm.DisplayContent displayContent, final int i) {
        this.mSurfaceControl = null;
        this.mDisplayContent = displayContent;
        this.mShellRootLayer = i;
        this.mDeathRecipient = new android.os.IBinder.DeathRecipient() { // from class: com.android.server.wm.ShellRoot$$ExternalSyntheticLambda0
            @Override // android.os.IBinder.DeathRecipient
            public final void binderDied() {
                com.android.server.wm.ShellRoot.this.lambda$new$0(i);
            }
        };
        try {
            iWindow.asBinder().linkToDeath(this.mDeathRecipient, 0);
            this.mClient = iWindow;
            switch (i) {
                case 0:
                    this.mWindowType = 2034;
                    break;
                case 1:
                    this.mWindowType = 2038;
                    break;
                default:
                    throw new java.lang.IllegalArgumentException(i + " is not an acceptable shell root layer.");
            }
            this.mToken = new com.android.server.wm.WindowToken.Builder(displayContent.mWmService, iWindow.asBinder(), this.mWindowType).setDisplayContent(displayContent).setPersistOnEmpty(true).setOwnerCanManageAppTokens(true).build();
            this.mSurfaceControl = this.mToken.makeChildSurface(null).setContainerLayer().setName("Shell Root Leash " + displayContent.getDisplayId()).setCallsite(TAG).build();
            this.mToken.getPendingTransaction().show(this.mSurfaceControl);
        } catch (android.os.RemoteException e) {
            android.util.Slog.e(TAG, "Unable to add shell root layer " + i + " on display " + displayContent.getDisplayId(), e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(int i) {
        this.mDisplayContent.removeShellRoot(i);
    }

    int getWindowType() {
        return this.mWindowType;
    }

    void clear() {
        if (this.mClient != null) {
            this.mClient.asBinder().unlinkToDeath(this.mDeathRecipient, 0);
            this.mClient = null;
        }
        if (this.mToken != null) {
            this.mToken.removeImmediately();
            this.mToken = null;
        }
    }

    android.view.SurfaceControl getSurfaceControl() {
        return this.mSurfaceControl;
    }

    android.view.IWindow getClient() {
        return this.mClient;
    }

    void startAnimation(android.view.animation.Animation animation) {
        if (this.mToken.windowType != 2034) {
            return;
        }
        android.view.DisplayInfo fixedRotationTransformDisplayInfo = this.mToken.getFixedRotationTransformDisplayInfo();
        if (fixedRotationTransformDisplayInfo == null) {
            fixedRotationTransformDisplayInfo = this.mDisplayContent.getDisplayInfo();
        }
        animation.initialize(fixedRotationTransformDisplayInfo.logicalWidth, fixedRotationTransformDisplayInfo.logicalHeight, fixedRotationTransformDisplayInfo.appWidth, fixedRotationTransformDisplayInfo.appHeight);
        animation.restrictDuration(com.android.server.job.controllers.JobStatus.DEFAULT_TRIGGER_UPDATE_DELAY);
        animation.scaleCurrentDuration(this.mDisplayContent.mWmService.getWindowAnimationScaleLocked());
        this.mToken.startAnimation(this.mToken.getPendingTransaction(), new com.android.server.wm.LocalAnimationAdapter(new com.android.server.wm.WindowAnimationSpec(animation, new android.graphics.Point(0, 0), false, com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE), this.mDisplayContent.mWmService.mSurfaceAnimationRunner), false, 16);
    }

    @android.annotation.Nullable
    android.os.IBinder getAccessibilityWindowToken() {
        if (this.mAccessibilityWindow != null) {
            return this.mAccessibilityWindow.asBinder();
        }
        return null;
    }

    void setAccessibilityWindow(android.view.IWindow iWindow) {
        if (this.mAccessibilityWindow != null) {
            this.mAccessibilityWindow.asBinder().unlinkToDeath(this.mAccessibilityWindowDeath, 0);
        }
        this.mAccessibilityWindow = iWindow;
        if (this.mAccessibilityWindow != null) {
            try {
                this.mAccessibilityWindowDeath = new android.os.IBinder.DeathRecipient() { // from class: com.android.server.wm.ShellRoot$$ExternalSyntheticLambda1
                    @Override // android.os.IBinder.DeathRecipient
                    public final void binderDied() {
                        com.android.server.wm.ShellRoot.this.lambda$setAccessibilityWindow$1();
                    }
                };
                this.mAccessibilityWindow.asBinder().linkToDeath(this.mAccessibilityWindowDeath, 0);
            } catch (android.os.RemoteException e) {
                this.mAccessibilityWindow = null;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setAccessibilityWindow$1() {
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mDisplayContent.mWmService.mGlobalLock;
        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                this.mAccessibilityWindow = null;
                setAccessibilityWindow(null);
            } catch (java.lang.Throwable th) {
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
    }
}
