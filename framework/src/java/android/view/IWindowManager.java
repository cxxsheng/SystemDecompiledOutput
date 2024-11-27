package android.view;

/* loaded from: classes4.dex */
public interface IWindowManager extends android.os.IInterface {
    public static final int FIXED_TO_USER_ROTATION_DEFAULT = 0;
    public static final int FIXED_TO_USER_ROTATION_DISABLED = 1;
    public static final int FIXED_TO_USER_ROTATION_ENABLED = 2;
    public static final int FIXED_TO_USER_ROTATION_IF_NO_AUTO_ROTATION = 3;

    void addKeyguardLockedStateListener(com.android.internal.policy.IKeyguardLockedStateListener iKeyguardLockedStateListener) throws android.os.RemoteException;

    android.view.SurfaceControl addShellRoot(int i, android.view.IWindow iWindow, int i2) throws android.os.RemoteException;

    boolean addToSurfaceSyncGroup(android.os.IBinder iBinder, boolean z, android.window.ISurfaceSyncGroupCompletedListener iSurfaceSyncGroupCompletedListener, android.window.AddToSurfaceSyncGroupResult addToSurfaceSyncGroupResult) throws android.os.RemoteException;

    void addWindowToken(android.os.IBinder iBinder, int i, int i2, android.os.Bundle bundle) throws android.os.RemoteException;

    android.window.WindowContextInfo attachWindowContextToDisplayArea(android.app.IApplicationThread iApplicationThread, android.os.IBinder iBinder, int i, int i2, android.os.Bundle bundle) throws android.os.RemoteException;

    android.window.WindowContextInfo attachWindowContextToDisplayContent(android.app.IApplicationThread iApplicationThread, android.os.IBinder iBinder, int i) throws android.os.RemoteException;

    android.window.WindowContextInfo attachWindowContextToWindowToken(android.app.IApplicationThread iApplicationThread, android.os.IBinder iBinder, android.os.IBinder iBinder2) throws android.os.RemoteException;

    void captureDisplay(int i, android.window.ScreenCapture.CaptureArgs captureArgs, android.window.ScreenCapture.ScreenCaptureListener screenCaptureListener) throws android.os.RemoteException;

    void clearForcedDisplayDensityForUser(int i, int i2) throws android.os.RemoteException;

    void clearForcedDisplaySize(int i) throws android.os.RemoteException;

    void clearTaskTransitionSpec() throws android.os.RemoteException;

    boolean clearWindowContentFrameStats(android.os.IBinder iBinder) throws android.os.RemoteException;

    void closeSystemDialogs(java.lang.String str) throws android.os.RemoteException;

    void createInputConsumer(android.os.IBinder iBinder, java.lang.String str, int i, android.view.InputChannel inputChannel) throws android.os.RemoteException;

    boolean destroyInputConsumer(android.os.IBinder iBinder, int i) throws android.os.RemoteException;

    void detachWindowContext(android.os.IBinder iBinder) throws android.os.RemoteException;

    @java.lang.Deprecated
    void disableKeyguard(android.os.IBinder iBinder, java.lang.String str, int i) throws android.os.RemoteException;

    void dismissKeyguard(com.android.internal.policy.IKeyguardDismissCallback iKeyguardDismissCallback, java.lang.CharSequence charSequence) throws android.os.RemoteException;

    @java.lang.Deprecated
    void endProlongedAnimations() throws android.os.RemoteException;

    void exitKeyguardSecurely(android.view.IOnKeyguardExitResult iOnKeyguardExitResult) throws android.os.RemoteException;

    void freezeDisplayRotation(int i, int i2, java.lang.String str) throws android.os.RemoteException;

    void freezeRotation(int i, java.lang.String str) throws android.os.RemoteException;

    float getAnimationScale(int i) throws android.os.RemoteException;

    float[] getAnimationScales() throws android.os.RemoteException;

    int getBaseDisplayDensity(int i) throws android.os.RemoteException;

    void getBaseDisplaySize(int i, android.graphics.Point point) throws android.os.RemoteException;

    float getCurrentAnimatorScale() throws android.os.RemoteException;

    android.graphics.Region getCurrentImeTouchRegion() throws android.os.RemoteException;

    int getDefaultDisplayRotation() throws android.os.RemoteException;

    int getDisplayIdByUniqueId(java.lang.String str) throws android.os.RemoteException;

    int getDisplayImePolicy(int i) throws android.os.RemoteException;

    int getDockedStackSide() throws android.os.RemoteException;

    int getImeDisplayId() throws android.os.RemoteException;

    int getInitialDisplayDensity(int i) throws android.os.RemoteException;

    void getInitialDisplaySize(int i, android.graphics.Point point) throws android.os.RemoteException;

    int getLetterboxBackgroundColorInArgb() throws android.os.RemoteException;

    java.util.List<android.view.DisplayInfo> getPossibleDisplayInfo(int i) throws android.os.RemoteException;

    int getPreferredOptionsPanelGravity(int i) throws android.os.RemoteException;

    int getRemoveContentMode(int i) throws android.os.RemoteException;

    void getStableInsets(int i, android.graphics.Rect rect) throws android.os.RemoteException;

    java.lang.String[] getSupportedDisplayHashAlgorithms() throws android.os.RemoteException;

    android.view.WindowContentFrameStats getWindowContentFrameStats(android.os.IBinder iBinder) throws android.os.RemoteException;

    boolean getWindowInsets(int i, android.os.IBinder iBinder, android.view.InsetsState insetsState) throws android.os.RemoteException;

    int getWindowingMode(int i) throws android.os.RemoteException;

    boolean hasNavigationBar(int i) throws android.os.RemoteException;

    void hideTransientBars(int i) throws android.os.RemoteException;

    void holdLock(android.os.IBinder iBinder, int i) throws android.os.RemoteException;

    boolean isDisplayRotationFrozen(int i) throws android.os.RemoteException;

    boolean isGlobalKey(int i) throws android.os.RemoteException;

    boolean isInTouchMode(int i) throws android.os.RemoteException;

    boolean isKeyguardLocked() throws android.os.RemoteException;

    boolean isKeyguardSecure(int i) throws android.os.RemoteException;

    boolean isLayerTracing() throws android.os.RemoteException;

    boolean isLetterboxBackgroundMultiColored() throws android.os.RemoteException;

    boolean isRotationFrozen() throws android.os.RemoteException;

    boolean isSafeModeEnabled() throws android.os.RemoteException;

    boolean isTaskSnapshotSupported() throws android.os.RemoteException;

    boolean isTransitionTraceEnabled() throws android.os.RemoteException;

    boolean isViewServerRunning() throws android.os.RemoteException;

    boolean isWindowToken(android.os.IBinder iBinder) throws android.os.RemoteException;

    boolean isWindowTraceEnabled() throws android.os.RemoteException;

    void lockNow(android.os.Bundle bundle) throws android.os.RemoteException;

    void markSurfaceSyncGroupReady(android.os.IBinder iBinder) throws android.os.RemoteException;

    boolean mirrorDisplay(int i, android.view.SurfaceControl surfaceControl) throws android.os.RemoteException;

    android.view.SurfaceControl mirrorWallpaperSurface(int i) throws android.os.RemoteException;

    java.util.List<android.content.ComponentName> notifyScreenshotListeners(int i) throws android.os.RemoteException;

    void onOverlayChanged() throws android.os.RemoteException;

    android.view.IWindowSession openSession(android.view.IWindowSessionCallback iWindowSessionCallback) throws android.os.RemoteException;

    void overridePendingAppTransitionMultiThumbFuture(android.view.IAppTransitionAnimationSpecsFuture iAppTransitionAnimationSpecsFuture, android.os.IRemoteCallback iRemoteCallback, boolean z, int i) throws android.os.RemoteException;

    void overridePendingAppTransitionRemote(android.view.RemoteAnimationAdapter remoteAnimationAdapter, int i) throws android.os.RemoteException;

    @java.lang.Deprecated
    void reenableKeyguard(android.os.IBinder iBinder, int i) throws android.os.RemoteException;

    void refreshScreenCaptureDisabled() throws android.os.RemoteException;

    boolean registerCrossWindowBlurEnabledListener(android.view.ICrossWindowBlurEnabledListener iCrossWindowBlurEnabledListener) throws android.os.RemoteException;

    void registerDecorViewGestureListener(android.view.IDecorViewGestureListener iDecorViewGestureListener, int i) throws android.os.RemoteException;

    void registerDisplayFoldListener(android.view.IDisplayFoldListener iDisplayFoldListener) throws android.os.RemoteException;

    int[] registerDisplayWindowListener(android.view.IDisplayWindowListener iDisplayWindowListener) throws android.os.RemoteException;

    void registerPinnedTaskListener(int i, android.view.IPinnedTaskListener iPinnedTaskListener) throws android.os.RemoteException;

    int registerProposedRotationListener(android.os.IBinder iBinder, android.view.IRotationWatcher iRotationWatcher) throws android.os.RemoteException;

    boolean registerScreenRecordingCallback(android.window.IScreenRecordingCallback iScreenRecordingCallback) throws android.os.RemoteException;

    void registerShortcutKey(long j, com.android.internal.policy.IShortcutService iShortcutService) throws android.os.RemoteException;

    void registerSystemGestureExclusionListener(android.view.ISystemGestureExclusionListener iSystemGestureExclusionListener, int i) throws android.os.RemoteException;

    void registerTaskFpsCallback(int i, android.window.ITaskFpsCallback iTaskFpsCallback) throws android.os.RemoteException;

    void registerTrustedPresentationListener(android.os.IBinder iBinder, android.window.ITrustedPresentationListener iTrustedPresentationListener, android.window.TrustedPresentationThresholds trustedPresentationThresholds, int i) throws android.os.RemoteException;

    boolean registerWallpaperVisibilityListener(android.view.IWallpaperVisibilityListener iWallpaperVisibilityListener, int i) throws android.os.RemoteException;

    void removeKeyguardLockedStateListener(com.android.internal.policy.IKeyguardLockedStateListener iKeyguardLockedStateListener) throws android.os.RemoteException;

    void removeRotationWatcher(android.view.IRotationWatcher iRotationWatcher) throws android.os.RemoteException;

    void removeWindowToken(android.os.IBinder iBinder, int i) throws android.os.RemoteException;

    boolean replaceContentOnDisplay(int i, android.view.SurfaceControl surfaceControl) throws android.os.RemoteException;

    void requestAppKeyboardShortcuts(com.android.internal.os.IResultReceiver iResultReceiver, int i) throws android.os.RemoteException;

    boolean requestAssistScreenshot(android.app.IAssistDataReceiver iAssistDataReceiver) throws android.os.RemoteException;

    void requestImeKeyboardShortcuts(com.android.internal.os.IResultReceiver iResultReceiver, int i) throws android.os.RemoteException;

    void requestScrollCapture(int i, android.os.IBinder iBinder, int i2, android.view.IScrollCaptureResponseListener iScrollCaptureResponseListener) throws android.os.RemoteException;

    void saveWindowTraceToFile() throws android.os.RemoteException;

    android.graphics.Bitmap screenshotWallpaper() throws android.os.RemoteException;

    void setActiveTransactionTracing(boolean z) throws android.os.RemoteException;

    void setAnimationScale(int i, float f) throws android.os.RemoteException;

    void setAnimationScales(float[] fArr) throws android.os.RemoteException;

    void setDisplayChangeWindowController(android.view.IDisplayChangeWindowController iDisplayChangeWindowController) throws android.os.RemoteException;

    void setDisplayHashThrottlingEnabled(boolean z) throws android.os.RemoteException;

    void setDisplayImePolicy(int i, int i2) throws android.os.RemoteException;

    void setDisplayWindowInsetsController(int i, android.view.IDisplayWindowInsetsController iDisplayWindowInsetsController) throws android.os.RemoteException;

    void setEventDispatching(boolean z) throws android.os.RemoteException;

    void setFixedToUserRotation(int i, int i2) throws android.os.RemoteException;

    void setForcedDisplayDensityForUser(int i, int i2, int i3) throws android.os.RemoteException;

    void setForcedDisplayScalingMode(int i, int i2) throws android.os.RemoteException;

    void setForcedDisplaySize(int i, int i2, int i3) throws android.os.RemoteException;

    void setGlobalDragListener(android.window.IGlobalDragListener iGlobalDragListener) throws android.os.RemoteException;

    void setIgnoreOrientationRequest(int i, boolean z) throws android.os.RemoteException;

    void setInTouchMode(boolean z, int i) throws android.os.RemoteException;

    void setInTouchModeOnAllDisplays(boolean z) throws android.os.RemoteException;

    void setLayerTracing(boolean z) throws android.os.RemoteException;

    void setLayerTracingFlags(int i) throws android.os.RemoteException;

    void setNavBarVirtualKeyHapticFeedbackEnabled(boolean z) throws android.os.RemoteException;

    void setRecentsAppBehindSystemBars(boolean z) throws android.os.RemoteException;

    void setRecentsVisibility(boolean z) throws android.os.RemoteException;

    void setRemoveContentMode(int i, int i2) throws android.os.RemoteException;

    void setShellRootAccessibilityWindow(int i, int i2, android.view.IWindow iWindow) throws android.os.RemoteException;

    void setShouldShowSystemDecors(int i, boolean z) throws android.os.RemoteException;

    void setShouldShowWithInsecureKeyguard(int i, boolean z) throws android.os.RemoteException;

    void setStrictModeVisualIndicatorPreference(java.lang.String str) throws android.os.RemoteException;

    void setSwitchingUser(boolean z) throws android.os.RemoteException;

    void setTaskSnapshotEnabled(boolean z) throws android.os.RemoteException;

    void setTaskTransitionSpec(android.view.TaskTransitionSpec taskTransitionSpec) throws android.os.RemoteException;

    void setWindowingMode(int i, int i2) throws android.os.RemoteException;

    boolean shouldShowSystemDecors(int i) throws android.os.RemoteException;

    boolean shouldShowWithInsecureKeyguard(int i) throws android.os.RemoteException;

    void showGlobalActions() throws android.os.RemoteException;

    void showStrictModeViolation(boolean z) throws android.os.RemoteException;

    android.graphics.Bitmap snapshotTaskForRecents(int i) throws android.os.RemoteException;

    void startFreezingScreen(int i, int i2) throws android.os.RemoteException;

    void startTransitionTrace() throws android.os.RemoteException;

    boolean startViewServer(int i) throws android.os.RemoteException;

    void startWindowTrace() throws android.os.RemoteException;

    void stopFreezingScreen() throws android.os.RemoteException;

    void stopTransitionTrace() throws android.os.RemoteException;

    boolean stopViewServer() throws android.os.RemoteException;

    void stopWindowTrace() throws android.os.RemoteException;

    void syncInputTransactions(boolean z) throws android.os.RemoteException;

    void thawDisplayRotation(int i, java.lang.String str) throws android.os.RemoteException;

    void thawRotation(java.lang.String str) throws android.os.RemoteException;

    boolean transferTouchGesture(android.window.InputTransferToken inputTransferToken, android.window.InputTransferToken inputTransferToken2) throws android.os.RemoteException;

    void unregisterCrossWindowBlurEnabledListener(android.view.ICrossWindowBlurEnabledListener iCrossWindowBlurEnabledListener) throws android.os.RemoteException;

    void unregisterDecorViewGestureListener(android.view.IDecorViewGestureListener iDecorViewGestureListener, int i) throws android.os.RemoteException;

    void unregisterDisplayFoldListener(android.view.IDisplayFoldListener iDisplayFoldListener) throws android.os.RemoteException;

    void unregisterDisplayWindowListener(android.view.IDisplayWindowListener iDisplayWindowListener) throws android.os.RemoteException;

    void unregisterScreenRecordingCallback(android.window.IScreenRecordingCallback iScreenRecordingCallback) throws android.os.RemoteException;

    void unregisterSystemGestureExclusionListener(android.view.ISystemGestureExclusionListener iSystemGestureExclusionListener, int i) throws android.os.RemoteException;

    void unregisterTaskFpsCallback(android.window.ITaskFpsCallback iTaskFpsCallback) throws android.os.RemoteException;

    void unregisterTrustedPresentationListener(android.window.ITrustedPresentationListener iTrustedPresentationListener, int i) throws android.os.RemoteException;

    void unregisterWallpaperVisibilityListener(android.view.IWallpaperVisibilityListener iWallpaperVisibilityListener, int i) throws android.os.RemoteException;

    void updateDisplayWindowRequestedVisibleTypes(int i, int i2) throws android.os.RemoteException;

    void updateStaticPrivacyIndicatorBounds(int i, android.graphics.Rect[] rectArr) throws android.os.RemoteException;

    android.view.displayhash.VerifiedDisplayHash verifyDisplayHash(android.view.displayhash.DisplayHash displayHash) throws android.os.RemoteException;

    int watchRotation(android.view.IRotationWatcher iRotationWatcher, int i) throws android.os.RemoteException;

    public static class Default implements android.view.IWindowManager {
        @Override // android.view.IWindowManager
        public boolean startViewServer(int i) throws android.os.RemoteException {
            return false;
        }

        @Override // android.view.IWindowManager
        public boolean stopViewServer() throws android.os.RemoteException {
            return false;
        }

        @Override // android.view.IWindowManager
        public boolean isViewServerRunning() throws android.os.RemoteException {
            return false;
        }

        @Override // android.view.IWindowManager
        public android.view.IWindowSession openSession(android.view.IWindowSessionCallback iWindowSessionCallback) throws android.os.RemoteException {
            return null;
        }

        @Override // android.view.IWindowManager
        public void getInitialDisplaySize(int i, android.graphics.Point point) throws android.os.RemoteException {
        }

        @Override // android.view.IWindowManager
        public void getBaseDisplaySize(int i, android.graphics.Point point) throws android.os.RemoteException {
        }

        @Override // android.view.IWindowManager
        public void setForcedDisplaySize(int i, int i2, int i3) throws android.os.RemoteException {
        }

        @Override // android.view.IWindowManager
        public void clearForcedDisplaySize(int i) throws android.os.RemoteException {
        }

        @Override // android.view.IWindowManager
        public int getInitialDisplayDensity(int i) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.view.IWindowManager
        public int getBaseDisplayDensity(int i) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.view.IWindowManager
        public int getDisplayIdByUniqueId(java.lang.String str) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.view.IWindowManager
        public void setForcedDisplayDensityForUser(int i, int i2, int i3) throws android.os.RemoteException {
        }

        @Override // android.view.IWindowManager
        public void clearForcedDisplayDensityForUser(int i, int i2) throws android.os.RemoteException {
        }

        @Override // android.view.IWindowManager
        public void setForcedDisplayScalingMode(int i, int i2) throws android.os.RemoteException {
        }

        @Override // android.view.IWindowManager
        public void setEventDispatching(boolean z) throws android.os.RemoteException {
        }

        @Override // android.view.IWindowManager
        public boolean isWindowToken(android.os.IBinder iBinder) throws android.os.RemoteException {
            return false;
        }

        @Override // android.view.IWindowManager
        public void addWindowToken(android.os.IBinder iBinder, int i, int i2, android.os.Bundle bundle) throws android.os.RemoteException {
        }

        @Override // android.view.IWindowManager
        public void removeWindowToken(android.os.IBinder iBinder, int i) throws android.os.RemoteException {
        }

        @Override // android.view.IWindowManager
        public void setDisplayChangeWindowController(android.view.IDisplayChangeWindowController iDisplayChangeWindowController) throws android.os.RemoteException {
        }

        @Override // android.view.IWindowManager
        public android.view.SurfaceControl addShellRoot(int i, android.view.IWindow iWindow, int i2) throws android.os.RemoteException {
            return null;
        }

        @Override // android.view.IWindowManager
        public void setShellRootAccessibilityWindow(int i, int i2, android.view.IWindow iWindow) throws android.os.RemoteException {
        }

        @Override // android.view.IWindowManager
        public void overridePendingAppTransitionMultiThumbFuture(android.view.IAppTransitionAnimationSpecsFuture iAppTransitionAnimationSpecsFuture, android.os.IRemoteCallback iRemoteCallback, boolean z, int i) throws android.os.RemoteException {
        }

        @Override // android.view.IWindowManager
        public void overridePendingAppTransitionRemote(android.view.RemoteAnimationAdapter remoteAnimationAdapter, int i) throws android.os.RemoteException {
        }

        @Override // android.view.IWindowManager
        public void endProlongedAnimations() throws android.os.RemoteException {
        }

        @Override // android.view.IWindowManager
        public void startFreezingScreen(int i, int i2) throws android.os.RemoteException {
        }

        @Override // android.view.IWindowManager
        public void stopFreezingScreen() throws android.os.RemoteException {
        }

        @Override // android.view.IWindowManager
        public void disableKeyguard(android.os.IBinder iBinder, java.lang.String str, int i) throws android.os.RemoteException {
        }

        @Override // android.view.IWindowManager
        public void reenableKeyguard(android.os.IBinder iBinder, int i) throws android.os.RemoteException {
        }

        @Override // android.view.IWindowManager
        public void exitKeyguardSecurely(android.view.IOnKeyguardExitResult iOnKeyguardExitResult) throws android.os.RemoteException {
        }

        @Override // android.view.IWindowManager
        public boolean isKeyguardLocked() throws android.os.RemoteException {
            return false;
        }

        @Override // android.view.IWindowManager
        public boolean isKeyguardSecure(int i) throws android.os.RemoteException {
            return false;
        }

        @Override // android.view.IWindowManager
        public void dismissKeyguard(com.android.internal.policy.IKeyguardDismissCallback iKeyguardDismissCallback, java.lang.CharSequence charSequence) throws android.os.RemoteException {
        }

        @Override // android.view.IWindowManager
        public void addKeyguardLockedStateListener(com.android.internal.policy.IKeyguardLockedStateListener iKeyguardLockedStateListener) throws android.os.RemoteException {
        }

        @Override // android.view.IWindowManager
        public void removeKeyguardLockedStateListener(com.android.internal.policy.IKeyguardLockedStateListener iKeyguardLockedStateListener) throws android.os.RemoteException {
        }

        @Override // android.view.IWindowManager
        public void setSwitchingUser(boolean z) throws android.os.RemoteException {
        }

        @Override // android.view.IWindowManager
        public void closeSystemDialogs(java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.view.IWindowManager
        public float getAnimationScale(int i) throws android.os.RemoteException {
            return 0.0f;
        }

        @Override // android.view.IWindowManager
        public float[] getAnimationScales() throws android.os.RemoteException {
            return null;
        }

        @Override // android.view.IWindowManager
        public void setAnimationScale(int i, float f) throws android.os.RemoteException {
        }

        @Override // android.view.IWindowManager
        public void setAnimationScales(float[] fArr) throws android.os.RemoteException {
        }

        @Override // android.view.IWindowManager
        public float getCurrentAnimatorScale() throws android.os.RemoteException {
            return 0.0f;
        }

        @Override // android.view.IWindowManager
        public void setInTouchMode(boolean z, int i) throws android.os.RemoteException {
        }

        @Override // android.view.IWindowManager
        public void setInTouchModeOnAllDisplays(boolean z) throws android.os.RemoteException {
        }

        @Override // android.view.IWindowManager
        public boolean isInTouchMode(int i) throws android.os.RemoteException {
            return false;
        }

        @Override // android.view.IWindowManager
        public void showStrictModeViolation(boolean z) throws android.os.RemoteException {
        }

        @Override // android.view.IWindowManager
        public void setStrictModeVisualIndicatorPreference(java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.view.IWindowManager
        public void refreshScreenCaptureDisabled() throws android.os.RemoteException {
        }

        @Override // android.view.IWindowManager
        public int getDefaultDisplayRotation() throws android.os.RemoteException {
            return 0;
        }

        @Override // android.view.IWindowManager
        public int watchRotation(android.view.IRotationWatcher iRotationWatcher, int i) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.view.IWindowManager
        public void removeRotationWatcher(android.view.IRotationWatcher iRotationWatcher) throws android.os.RemoteException {
        }

        @Override // android.view.IWindowManager
        public int registerProposedRotationListener(android.os.IBinder iBinder, android.view.IRotationWatcher iRotationWatcher) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.view.IWindowManager
        public int getPreferredOptionsPanelGravity(int i) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.view.IWindowManager
        public void freezeRotation(int i, java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.view.IWindowManager
        public void thawRotation(java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.view.IWindowManager
        public boolean isRotationFrozen() throws android.os.RemoteException {
            return false;
        }

        @Override // android.view.IWindowManager
        public void freezeDisplayRotation(int i, int i2, java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.view.IWindowManager
        public void thawDisplayRotation(int i, java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.view.IWindowManager
        public boolean isDisplayRotationFrozen(int i) throws android.os.RemoteException {
            return false;
        }

        @Override // android.view.IWindowManager
        public void setFixedToUserRotation(int i, int i2) throws android.os.RemoteException {
        }

        @Override // android.view.IWindowManager
        public void setIgnoreOrientationRequest(int i, boolean z) throws android.os.RemoteException {
        }

        @Override // android.view.IWindowManager
        public android.graphics.Bitmap screenshotWallpaper() throws android.os.RemoteException {
            return null;
        }

        @Override // android.view.IWindowManager
        public android.view.SurfaceControl mirrorWallpaperSurface(int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.view.IWindowManager
        public boolean registerWallpaperVisibilityListener(android.view.IWallpaperVisibilityListener iWallpaperVisibilityListener, int i) throws android.os.RemoteException {
            return false;
        }

        @Override // android.view.IWindowManager
        public void unregisterWallpaperVisibilityListener(android.view.IWallpaperVisibilityListener iWallpaperVisibilityListener, int i) throws android.os.RemoteException {
        }

        @Override // android.view.IWindowManager
        public void registerSystemGestureExclusionListener(android.view.ISystemGestureExclusionListener iSystemGestureExclusionListener, int i) throws android.os.RemoteException {
        }

        @Override // android.view.IWindowManager
        public void unregisterSystemGestureExclusionListener(android.view.ISystemGestureExclusionListener iSystemGestureExclusionListener, int i) throws android.os.RemoteException {
        }

        @Override // android.view.IWindowManager
        public boolean requestAssistScreenshot(android.app.IAssistDataReceiver iAssistDataReceiver) throws android.os.RemoteException {
            return false;
        }

        @Override // android.view.IWindowManager
        public void hideTransientBars(int i) throws android.os.RemoteException {
        }

        @Override // android.view.IWindowManager
        public void setRecentsVisibility(boolean z) throws android.os.RemoteException {
        }

        @Override // android.view.IWindowManager
        public void updateStaticPrivacyIndicatorBounds(int i, android.graphics.Rect[] rectArr) throws android.os.RemoteException {
        }

        @Override // android.view.IWindowManager
        public void setNavBarVirtualKeyHapticFeedbackEnabled(boolean z) throws android.os.RemoteException {
        }

        @Override // android.view.IWindowManager
        public boolean hasNavigationBar(int i) throws android.os.RemoteException {
            return false;
        }

        @Override // android.view.IWindowManager
        public void lockNow(android.os.Bundle bundle) throws android.os.RemoteException {
        }

        @Override // android.view.IWindowManager
        public boolean isSafeModeEnabled() throws android.os.RemoteException {
            return false;
        }

        @Override // android.view.IWindowManager
        public boolean clearWindowContentFrameStats(android.os.IBinder iBinder) throws android.os.RemoteException {
            return false;
        }

        @Override // android.view.IWindowManager
        public android.view.WindowContentFrameStats getWindowContentFrameStats(android.os.IBinder iBinder) throws android.os.RemoteException {
            return null;
        }

        @Override // android.view.IWindowManager
        public int getDockedStackSide() throws android.os.RemoteException {
            return 0;
        }

        @Override // android.view.IWindowManager
        public void registerPinnedTaskListener(int i, android.view.IPinnedTaskListener iPinnedTaskListener) throws android.os.RemoteException {
        }

        @Override // android.view.IWindowManager
        public void requestAppKeyboardShortcuts(com.android.internal.os.IResultReceiver iResultReceiver, int i) throws android.os.RemoteException {
        }

        @Override // android.view.IWindowManager
        public void requestImeKeyboardShortcuts(com.android.internal.os.IResultReceiver iResultReceiver, int i) throws android.os.RemoteException {
        }

        @Override // android.view.IWindowManager
        public void getStableInsets(int i, android.graphics.Rect rect) throws android.os.RemoteException {
        }

        @Override // android.view.IWindowManager
        public void registerShortcutKey(long j, com.android.internal.policy.IShortcutService iShortcutService) throws android.os.RemoteException {
        }

        @Override // android.view.IWindowManager
        public void createInputConsumer(android.os.IBinder iBinder, java.lang.String str, int i, android.view.InputChannel inputChannel) throws android.os.RemoteException {
        }

        @Override // android.view.IWindowManager
        public boolean destroyInputConsumer(android.os.IBinder iBinder, int i) throws android.os.RemoteException {
            return false;
        }

        @Override // android.view.IWindowManager
        public android.graphics.Region getCurrentImeTouchRegion() throws android.os.RemoteException {
            return null;
        }

        @Override // android.view.IWindowManager
        public void registerDisplayFoldListener(android.view.IDisplayFoldListener iDisplayFoldListener) throws android.os.RemoteException {
        }

        @Override // android.view.IWindowManager
        public void unregisterDisplayFoldListener(android.view.IDisplayFoldListener iDisplayFoldListener) throws android.os.RemoteException {
        }

        @Override // android.view.IWindowManager
        public int[] registerDisplayWindowListener(android.view.IDisplayWindowListener iDisplayWindowListener) throws android.os.RemoteException {
            return null;
        }

        @Override // android.view.IWindowManager
        public void unregisterDisplayWindowListener(android.view.IDisplayWindowListener iDisplayWindowListener) throws android.os.RemoteException {
        }

        @Override // android.view.IWindowManager
        public void startWindowTrace() throws android.os.RemoteException {
        }

        @Override // android.view.IWindowManager
        public void stopWindowTrace() throws android.os.RemoteException {
        }

        @Override // android.view.IWindowManager
        public void saveWindowTraceToFile() throws android.os.RemoteException {
        }

        @Override // android.view.IWindowManager
        public boolean isWindowTraceEnabled() throws android.os.RemoteException {
            return false;
        }

        @Override // android.view.IWindowManager
        public void startTransitionTrace() throws android.os.RemoteException {
        }

        @Override // android.view.IWindowManager
        public void stopTransitionTrace() throws android.os.RemoteException {
        }

        @Override // android.view.IWindowManager
        public boolean isTransitionTraceEnabled() throws android.os.RemoteException {
            return false;
        }

        @Override // android.view.IWindowManager
        public int getWindowingMode(int i) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.view.IWindowManager
        public void setWindowingMode(int i, int i2) throws android.os.RemoteException {
        }

        @Override // android.view.IWindowManager
        public int getRemoveContentMode(int i) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.view.IWindowManager
        public void setRemoveContentMode(int i, int i2) throws android.os.RemoteException {
        }

        @Override // android.view.IWindowManager
        public boolean shouldShowWithInsecureKeyguard(int i) throws android.os.RemoteException {
            return false;
        }

        @Override // android.view.IWindowManager
        public void setShouldShowWithInsecureKeyguard(int i, boolean z) throws android.os.RemoteException {
        }

        @Override // android.view.IWindowManager
        public boolean shouldShowSystemDecors(int i) throws android.os.RemoteException {
            return false;
        }

        @Override // android.view.IWindowManager
        public void setShouldShowSystemDecors(int i, boolean z) throws android.os.RemoteException {
        }

        @Override // android.view.IWindowManager
        public int getDisplayImePolicy(int i) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.view.IWindowManager
        public void setDisplayImePolicy(int i, int i2) throws android.os.RemoteException {
        }

        @Override // android.view.IWindowManager
        public void syncInputTransactions(boolean z) throws android.os.RemoteException {
        }

        @Override // android.view.IWindowManager
        public boolean isLayerTracing() throws android.os.RemoteException {
            return false;
        }

        @Override // android.view.IWindowManager
        public void setLayerTracing(boolean z) throws android.os.RemoteException {
        }

        @Override // android.view.IWindowManager
        public boolean mirrorDisplay(int i, android.view.SurfaceControl surfaceControl) throws android.os.RemoteException {
            return false;
        }

        @Override // android.view.IWindowManager
        public void setDisplayWindowInsetsController(int i, android.view.IDisplayWindowInsetsController iDisplayWindowInsetsController) throws android.os.RemoteException {
        }

        @Override // android.view.IWindowManager
        public void updateDisplayWindowRequestedVisibleTypes(int i, int i2) throws android.os.RemoteException {
        }

        @Override // android.view.IWindowManager
        public boolean getWindowInsets(int i, android.os.IBinder iBinder, android.view.InsetsState insetsState) throws android.os.RemoteException {
            return false;
        }

        @Override // android.view.IWindowManager
        public java.util.List<android.view.DisplayInfo> getPossibleDisplayInfo(int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.view.IWindowManager
        public void showGlobalActions() throws android.os.RemoteException {
        }

        @Override // android.view.IWindowManager
        public void setLayerTracingFlags(int i) throws android.os.RemoteException {
        }

        @Override // android.view.IWindowManager
        public void setActiveTransactionTracing(boolean z) throws android.os.RemoteException {
        }

        @Override // android.view.IWindowManager
        public void requestScrollCapture(int i, android.os.IBinder iBinder, int i2, android.view.IScrollCaptureResponseListener iScrollCaptureResponseListener) throws android.os.RemoteException {
        }

        @Override // android.view.IWindowManager
        public void holdLock(android.os.IBinder iBinder, int i) throws android.os.RemoteException {
        }

        @Override // android.view.IWindowManager
        public java.lang.String[] getSupportedDisplayHashAlgorithms() throws android.os.RemoteException {
            return null;
        }

        @Override // android.view.IWindowManager
        public android.view.displayhash.VerifiedDisplayHash verifyDisplayHash(android.view.displayhash.DisplayHash displayHash) throws android.os.RemoteException {
            return null;
        }

        @Override // android.view.IWindowManager
        public void setDisplayHashThrottlingEnabled(boolean z) throws android.os.RemoteException {
        }

        @Override // android.view.IWindowManager
        public android.window.WindowContextInfo attachWindowContextToDisplayArea(android.app.IApplicationThread iApplicationThread, android.os.IBinder iBinder, int i, int i2, android.os.Bundle bundle) throws android.os.RemoteException {
            return null;
        }

        @Override // android.view.IWindowManager
        public android.window.WindowContextInfo attachWindowContextToWindowToken(android.app.IApplicationThread iApplicationThread, android.os.IBinder iBinder, android.os.IBinder iBinder2) throws android.os.RemoteException {
            return null;
        }

        @Override // android.view.IWindowManager
        public android.window.WindowContextInfo attachWindowContextToDisplayContent(android.app.IApplicationThread iApplicationThread, android.os.IBinder iBinder, int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.view.IWindowManager
        public void detachWindowContext(android.os.IBinder iBinder) throws android.os.RemoteException {
        }

        @Override // android.view.IWindowManager
        public boolean registerCrossWindowBlurEnabledListener(android.view.ICrossWindowBlurEnabledListener iCrossWindowBlurEnabledListener) throws android.os.RemoteException {
            return false;
        }

        @Override // android.view.IWindowManager
        public void unregisterCrossWindowBlurEnabledListener(android.view.ICrossWindowBlurEnabledListener iCrossWindowBlurEnabledListener) throws android.os.RemoteException {
        }

        @Override // android.view.IWindowManager
        public boolean isTaskSnapshotSupported() throws android.os.RemoteException {
            return false;
        }

        @Override // android.view.IWindowManager
        public int getImeDisplayId() throws android.os.RemoteException {
            return 0;
        }

        @Override // android.view.IWindowManager
        public void setTaskSnapshotEnabled(boolean z) throws android.os.RemoteException {
        }

        @Override // android.view.IWindowManager
        public void setTaskTransitionSpec(android.view.TaskTransitionSpec taskTransitionSpec) throws android.os.RemoteException {
        }

        @Override // android.view.IWindowManager
        public void clearTaskTransitionSpec() throws android.os.RemoteException {
        }

        @Override // android.view.IWindowManager
        public void registerTaskFpsCallback(int i, android.window.ITaskFpsCallback iTaskFpsCallback) throws android.os.RemoteException {
        }

        @Override // android.view.IWindowManager
        public void unregisterTaskFpsCallback(android.window.ITaskFpsCallback iTaskFpsCallback) throws android.os.RemoteException {
        }

        @Override // android.view.IWindowManager
        public android.graphics.Bitmap snapshotTaskForRecents(int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.view.IWindowManager
        public void setRecentsAppBehindSystemBars(boolean z) throws android.os.RemoteException {
        }

        @Override // android.view.IWindowManager
        public int getLetterboxBackgroundColorInArgb() throws android.os.RemoteException {
            return 0;
        }

        @Override // android.view.IWindowManager
        public boolean isLetterboxBackgroundMultiColored() throws android.os.RemoteException {
            return false;
        }

        @Override // android.view.IWindowManager
        public void captureDisplay(int i, android.window.ScreenCapture.CaptureArgs captureArgs, android.window.ScreenCapture.ScreenCaptureListener screenCaptureListener) throws android.os.RemoteException {
        }

        @Override // android.view.IWindowManager
        public boolean isGlobalKey(int i) throws android.os.RemoteException {
            return false;
        }

        @Override // android.view.IWindowManager
        public boolean addToSurfaceSyncGroup(android.os.IBinder iBinder, boolean z, android.window.ISurfaceSyncGroupCompletedListener iSurfaceSyncGroupCompletedListener, android.window.AddToSurfaceSyncGroupResult addToSurfaceSyncGroupResult) throws android.os.RemoteException {
            return false;
        }

        @Override // android.view.IWindowManager
        public void markSurfaceSyncGroupReady(android.os.IBinder iBinder) throws android.os.RemoteException {
        }

        @Override // android.view.IWindowManager
        public java.util.List<android.content.ComponentName> notifyScreenshotListeners(int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.view.IWindowManager
        public boolean replaceContentOnDisplay(int i, android.view.SurfaceControl surfaceControl) throws android.os.RemoteException {
            return false;
        }

        @Override // android.view.IWindowManager
        public void registerDecorViewGestureListener(android.view.IDecorViewGestureListener iDecorViewGestureListener, int i) throws android.os.RemoteException {
        }

        @Override // android.view.IWindowManager
        public void unregisterDecorViewGestureListener(android.view.IDecorViewGestureListener iDecorViewGestureListener, int i) throws android.os.RemoteException {
        }

        @Override // android.view.IWindowManager
        public void registerTrustedPresentationListener(android.os.IBinder iBinder, android.window.ITrustedPresentationListener iTrustedPresentationListener, android.window.TrustedPresentationThresholds trustedPresentationThresholds, int i) throws android.os.RemoteException {
        }

        @Override // android.view.IWindowManager
        public void unregisterTrustedPresentationListener(android.window.ITrustedPresentationListener iTrustedPresentationListener, int i) throws android.os.RemoteException {
        }

        @Override // android.view.IWindowManager
        public boolean registerScreenRecordingCallback(android.window.IScreenRecordingCallback iScreenRecordingCallback) throws android.os.RemoteException {
            return false;
        }

        @Override // android.view.IWindowManager
        public void unregisterScreenRecordingCallback(android.window.IScreenRecordingCallback iScreenRecordingCallback) throws android.os.RemoteException {
        }

        @Override // android.view.IWindowManager
        public void setGlobalDragListener(android.window.IGlobalDragListener iGlobalDragListener) throws android.os.RemoteException {
        }

        @Override // android.view.IWindowManager
        public boolean transferTouchGesture(android.window.InputTransferToken inputTransferToken, android.window.InputTransferToken inputTransferToken2) throws android.os.RemoteException {
            return false;
        }

        @Override // android.view.IWindowManager
        public void onOverlayChanged() throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.view.IWindowManager {
        public static final java.lang.String DESCRIPTOR = "android.view.IWindowManager";
        static final int TRANSACTION_addKeyguardLockedStateListener = 33;
        static final int TRANSACTION_addShellRoot = 20;
        static final int TRANSACTION_addToSurfaceSyncGroup = 142;
        static final int TRANSACTION_addWindowToken = 17;
        static final int TRANSACTION_attachWindowContextToDisplayArea = 123;
        static final int TRANSACTION_attachWindowContextToDisplayContent = 125;
        static final int TRANSACTION_attachWindowContextToWindowToken = 124;
        static final int TRANSACTION_captureDisplay = 140;
        static final int TRANSACTION_clearForcedDisplayDensityForUser = 13;
        static final int TRANSACTION_clearForcedDisplaySize = 8;
        static final int TRANSACTION_clearTaskTransitionSpec = 133;
        static final int TRANSACTION_clearWindowContentFrameStats = 75;
        static final int TRANSACTION_closeSystemDialogs = 36;
        static final int TRANSACTION_createInputConsumer = 83;
        static final int TRANSACTION_destroyInputConsumer = 84;
        static final int TRANSACTION_detachWindowContext = 126;
        static final int TRANSACTION_disableKeyguard = 27;
        static final int TRANSACTION_dismissKeyguard = 32;
        static final int TRANSACTION_endProlongedAnimations = 24;
        static final int TRANSACTION_exitKeyguardSecurely = 29;
        static final int TRANSACTION_freezeDisplayRotation = 56;
        static final int TRANSACTION_freezeRotation = 53;
        static final int TRANSACTION_getAnimationScale = 37;
        static final int TRANSACTION_getAnimationScales = 38;
        static final int TRANSACTION_getBaseDisplayDensity = 10;
        static final int TRANSACTION_getBaseDisplaySize = 6;
        static final int TRANSACTION_getCurrentAnimatorScale = 41;
        static final int TRANSACTION_getCurrentImeTouchRegion = 85;
        static final int TRANSACTION_getDefaultDisplayRotation = 48;
        static final int TRANSACTION_getDisplayIdByUniqueId = 11;
        static final int TRANSACTION_getDisplayImePolicy = 105;
        static final int TRANSACTION_getDockedStackSide = 77;
        static final int TRANSACTION_getImeDisplayId = 130;
        static final int TRANSACTION_getInitialDisplayDensity = 9;
        static final int TRANSACTION_getInitialDisplaySize = 5;
        static final int TRANSACTION_getLetterboxBackgroundColorInArgb = 138;
        static final int TRANSACTION_getPossibleDisplayInfo = 114;
        static final int TRANSACTION_getPreferredOptionsPanelGravity = 52;
        static final int TRANSACTION_getRemoveContentMode = 99;
        static final int TRANSACTION_getStableInsets = 81;
        static final int TRANSACTION_getSupportedDisplayHashAlgorithms = 120;
        static final int TRANSACTION_getWindowContentFrameStats = 76;
        static final int TRANSACTION_getWindowInsets = 113;
        static final int TRANSACTION_getWindowingMode = 97;
        static final int TRANSACTION_hasNavigationBar = 72;
        static final int TRANSACTION_hideTransientBars = 68;
        static final int TRANSACTION_holdLock = 119;
        static final int TRANSACTION_isDisplayRotationFrozen = 58;
        static final int TRANSACTION_isGlobalKey = 141;
        static final int TRANSACTION_isInTouchMode = 44;
        static final int TRANSACTION_isKeyguardLocked = 30;
        static final int TRANSACTION_isKeyguardSecure = 31;
        static final int TRANSACTION_isLayerTracing = 108;
        static final int TRANSACTION_isLetterboxBackgroundMultiColored = 139;
        static final int TRANSACTION_isRotationFrozen = 55;
        static final int TRANSACTION_isSafeModeEnabled = 74;
        static final int TRANSACTION_isTaskSnapshotSupported = 129;
        static final int TRANSACTION_isTransitionTraceEnabled = 96;
        static final int TRANSACTION_isViewServerRunning = 3;
        static final int TRANSACTION_isWindowToken = 16;
        static final int TRANSACTION_isWindowTraceEnabled = 93;
        static final int TRANSACTION_lockNow = 73;
        static final int TRANSACTION_markSurfaceSyncGroupReady = 143;
        static final int TRANSACTION_mirrorDisplay = 110;
        static final int TRANSACTION_mirrorWallpaperSurface = 62;
        static final int TRANSACTION_notifyScreenshotListeners = 144;
        static final int TRANSACTION_onOverlayChanged = 154;
        static final int TRANSACTION_openSession = 4;
        static final int TRANSACTION_overridePendingAppTransitionMultiThumbFuture = 22;
        static final int TRANSACTION_overridePendingAppTransitionRemote = 23;
        static final int TRANSACTION_reenableKeyguard = 28;
        static final int TRANSACTION_refreshScreenCaptureDisabled = 47;
        static final int TRANSACTION_registerCrossWindowBlurEnabledListener = 127;
        static final int TRANSACTION_registerDecorViewGestureListener = 146;
        static final int TRANSACTION_registerDisplayFoldListener = 86;
        static final int TRANSACTION_registerDisplayWindowListener = 88;
        static final int TRANSACTION_registerPinnedTaskListener = 78;
        static final int TRANSACTION_registerProposedRotationListener = 51;
        static final int TRANSACTION_registerScreenRecordingCallback = 150;
        static final int TRANSACTION_registerShortcutKey = 82;
        static final int TRANSACTION_registerSystemGestureExclusionListener = 65;
        static final int TRANSACTION_registerTaskFpsCallback = 134;
        static final int TRANSACTION_registerTrustedPresentationListener = 148;
        static final int TRANSACTION_registerWallpaperVisibilityListener = 63;
        static final int TRANSACTION_removeKeyguardLockedStateListener = 34;
        static final int TRANSACTION_removeRotationWatcher = 50;
        static final int TRANSACTION_removeWindowToken = 18;
        static final int TRANSACTION_replaceContentOnDisplay = 145;
        static final int TRANSACTION_requestAppKeyboardShortcuts = 79;
        static final int TRANSACTION_requestAssistScreenshot = 67;
        static final int TRANSACTION_requestImeKeyboardShortcuts = 80;
        static final int TRANSACTION_requestScrollCapture = 118;
        static final int TRANSACTION_saveWindowTraceToFile = 92;
        static final int TRANSACTION_screenshotWallpaper = 61;
        static final int TRANSACTION_setActiveTransactionTracing = 117;
        static final int TRANSACTION_setAnimationScale = 39;
        static final int TRANSACTION_setAnimationScales = 40;
        static final int TRANSACTION_setDisplayChangeWindowController = 19;
        static final int TRANSACTION_setDisplayHashThrottlingEnabled = 122;
        static final int TRANSACTION_setDisplayImePolicy = 106;
        static final int TRANSACTION_setDisplayWindowInsetsController = 111;
        static final int TRANSACTION_setEventDispatching = 15;
        static final int TRANSACTION_setFixedToUserRotation = 59;
        static final int TRANSACTION_setForcedDisplayDensityForUser = 12;
        static final int TRANSACTION_setForcedDisplayScalingMode = 14;
        static final int TRANSACTION_setForcedDisplaySize = 7;
        static final int TRANSACTION_setGlobalDragListener = 152;
        static final int TRANSACTION_setIgnoreOrientationRequest = 60;
        static final int TRANSACTION_setInTouchMode = 42;
        static final int TRANSACTION_setInTouchModeOnAllDisplays = 43;
        static final int TRANSACTION_setLayerTracing = 109;
        static final int TRANSACTION_setLayerTracingFlags = 116;
        static final int TRANSACTION_setNavBarVirtualKeyHapticFeedbackEnabled = 71;
        static final int TRANSACTION_setRecentsAppBehindSystemBars = 137;
        static final int TRANSACTION_setRecentsVisibility = 69;
        static final int TRANSACTION_setRemoveContentMode = 100;
        static final int TRANSACTION_setShellRootAccessibilityWindow = 21;
        static final int TRANSACTION_setShouldShowSystemDecors = 104;
        static final int TRANSACTION_setShouldShowWithInsecureKeyguard = 102;
        static final int TRANSACTION_setStrictModeVisualIndicatorPreference = 46;
        static final int TRANSACTION_setSwitchingUser = 35;
        static final int TRANSACTION_setTaskSnapshotEnabled = 131;
        static final int TRANSACTION_setTaskTransitionSpec = 132;
        static final int TRANSACTION_setWindowingMode = 98;
        static final int TRANSACTION_shouldShowSystemDecors = 103;
        static final int TRANSACTION_shouldShowWithInsecureKeyguard = 101;
        static final int TRANSACTION_showGlobalActions = 115;
        static final int TRANSACTION_showStrictModeViolation = 45;
        static final int TRANSACTION_snapshotTaskForRecents = 136;
        static final int TRANSACTION_startFreezingScreen = 25;
        static final int TRANSACTION_startTransitionTrace = 94;
        static final int TRANSACTION_startViewServer = 1;
        static final int TRANSACTION_startWindowTrace = 90;
        static final int TRANSACTION_stopFreezingScreen = 26;
        static final int TRANSACTION_stopTransitionTrace = 95;
        static final int TRANSACTION_stopViewServer = 2;
        static final int TRANSACTION_stopWindowTrace = 91;
        static final int TRANSACTION_syncInputTransactions = 107;
        static final int TRANSACTION_thawDisplayRotation = 57;
        static final int TRANSACTION_thawRotation = 54;
        static final int TRANSACTION_transferTouchGesture = 153;
        static final int TRANSACTION_unregisterCrossWindowBlurEnabledListener = 128;
        static final int TRANSACTION_unregisterDecorViewGestureListener = 147;
        static final int TRANSACTION_unregisterDisplayFoldListener = 87;
        static final int TRANSACTION_unregisterDisplayWindowListener = 89;
        static final int TRANSACTION_unregisterScreenRecordingCallback = 151;
        static final int TRANSACTION_unregisterSystemGestureExclusionListener = 66;
        static final int TRANSACTION_unregisterTaskFpsCallback = 135;
        static final int TRANSACTION_unregisterTrustedPresentationListener = 149;
        static final int TRANSACTION_unregisterWallpaperVisibilityListener = 64;
        static final int TRANSACTION_updateDisplayWindowRequestedVisibleTypes = 112;
        static final int TRANSACTION_updateStaticPrivacyIndicatorBounds = 70;
        static final int TRANSACTION_verifyDisplayHash = 121;
        static final int TRANSACTION_watchRotation = 49;
        private final android.os.PermissionEnforcer mEnforcer;

        public Stub(android.os.PermissionEnforcer permissionEnforcer) {
            attachInterface(this, DESCRIPTOR);
            if (permissionEnforcer == null) {
                throw new java.lang.IllegalArgumentException("enforcer cannot be null");
            }
            this.mEnforcer = permissionEnforcer;
        }

        @java.lang.Deprecated
        public Stub() {
            this(android.os.PermissionEnforcer.fromContext(android.app.ActivityThread.currentActivityThread().getSystemContext()));
        }

        public static android.view.IWindowManager asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.view.IWindowManager)) {
                return (android.view.IWindowManager) queryLocalInterface;
            }
            return new android.view.IWindowManager.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "startViewServer";
                case 2:
                    return "stopViewServer";
                case 3:
                    return "isViewServerRunning";
                case 4:
                    return "openSession";
                case 5:
                    return "getInitialDisplaySize";
                case 6:
                    return "getBaseDisplaySize";
                case 7:
                    return "setForcedDisplaySize";
                case 8:
                    return "clearForcedDisplaySize";
                case 9:
                    return "getInitialDisplayDensity";
                case 10:
                    return "getBaseDisplayDensity";
                case 11:
                    return "getDisplayIdByUniqueId";
                case 12:
                    return "setForcedDisplayDensityForUser";
                case 13:
                    return "clearForcedDisplayDensityForUser";
                case 14:
                    return "setForcedDisplayScalingMode";
                case 15:
                    return "setEventDispatching";
                case 16:
                    return "isWindowToken";
                case 17:
                    return "addWindowToken";
                case 18:
                    return "removeWindowToken";
                case 19:
                    return "setDisplayChangeWindowController";
                case 20:
                    return "addShellRoot";
                case 21:
                    return "setShellRootAccessibilityWindow";
                case 22:
                    return "overridePendingAppTransitionMultiThumbFuture";
                case 23:
                    return "overridePendingAppTransitionRemote";
                case 24:
                    return "endProlongedAnimations";
                case 25:
                    return "startFreezingScreen";
                case 26:
                    return "stopFreezingScreen";
                case 27:
                    return "disableKeyguard";
                case 28:
                    return "reenableKeyguard";
                case 29:
                    return "exitKeyguardSecurely";
                case 30:
                    return "isKeyguardLocked";
                case 31:
                    return "isKeyguardSecure";
                case 32:
                    return "dismissKeyguard";
                case 33:
                    return "addKeyguardLockedStateListener";
                case 34:
                    return "removeKeyguardLockedStateListener";
                case 35:
                    return "setSwitchingUser";
                case 36:
                    return "closeSystemDialogs";
                case 37:
                    return "getAnimationScale";
                case 38:
                    return "getAnimationScales";
                case 39:
                    return "setAnimationScale";
                case 40:
                    return "setAnimationScales";
                case 41:
                    return "getCurrentAnimatorScale";
                case 42:
                    return "setInTouchMode";
                case 43:
                    return "setInTouchModeOnAllDisplays";
                case 44:
                    return "isInTouchMode";
                case 45:
                    return "showStrictModeViolation";
                case 46:
                    return "setStrictModeVisualIndicatorPreference";
                case 47:
                    return "refreshScreenCaptureDisabled";
                case 48:
                    return "getDefaultDisplayRotation";
                case 49:
                    return "watchRotation";
                case 50:
                    return "removeRotationWatcher";
                case 51:
                    return "registerProposedRotationListener";
                case 52:
                    return "getPreferredOptionsPanelGravity";
                case 53:
                    return "freezeRotation";
                case 54:
                    return "thawRotation";
                case 55:
                    return "isRotationFrozen";
                case 56:
                    return "freezeDisplayRotation";
                case 57:
                    return "thawDisplayRotation";
                case 58:
                    return "isDisplayRotationFrozen";
                case 59:
                    return "setFixedToUserRotation";
                case 60:
                    return "setIgnoreOrientationRequest";
                case 61:
                    return "screenshotWallpaper";
                case 62:
                    return "mirrorWallpaperSurface";
                case 63:
                    return "registerWallpaperVisibilityListener";
                case 64:
                    return "unregisterWallpaperVisibilityListener";
                case 65:
                    return "registerSystemGestureExclusionListener";
                case 66:
                    return "unregisterSystemGestureExclusionListener";
                case 67:
                    return "requestAssistScreenshot";
                case 68:
                    return "hideTransientBars";
                case 69:
                    return "setRecentsVisibility";
                case 70:
                    return "updateStaticPrivacyIndicatorBounds";
                case 71:
                    return "setNavBarVirtualKeyHapticFeedbackEnabled";
                case 72:
                    return "hasNavigationBar";
                case 73:
                    return "lockNow";
                case 74:
                    return "isSafeModeEnabled";
                case 75:
                    return "clearWindowContentFrameStats";
                case 76:
                    return "getWindowContentFrameStats";
                case 77:
                    return "getDockedStackSide";
                case 78:
                    return "registerPinnedTaskListener";
                case 79:
                    return "requestAppKeyboardShortcuts";
                case 80:
                    return "requestImeKeyboardShortcuts";
                case 81:
                    return "getStableInsets";
                case 82:
                    return "registerShortcutKey";
                case 83:
                    return "createInputConsumer";
                case 84:
                    return "destroyInputConsumer";
                case 85:
                    return "getCurrentImeTouchRegion";
                case 86:
                    return "registerDisplayFoldListener";
                case 87:
                    return "unregisterDisplayFoldListener";
                case 88:
                    return "registerDisplayWindowListener";
                case 89:
                    return "unregisterDisplayWindowListener";
                case 90:
                    return "startWindowTrace";
                case 91:
                    return "stopWindowTrace";
                case 92:
                    return "saveWindowTraceToFile";
                case 93:
                    return "isWindowTraceEnabled";
                case 94:
                    return "startTransitionTrace";
                case 95:
                    return "stopTransitionTrace";
                case 96:
                    return "isTransitionTraceEnabled";
                case 97:
                    return "getWindowingMode";
                case 98:
                    return "setWindowingMode";
                case 99:
                    return "getRemoveContentMode";
                case 100:
                    return "setRemoveContentMode";
                case 101:
                    return "shouldShowWithInsecureKeyguard";
                case 102:
                    return "setShouldShowWithInsecureKeyguard";
                case 103:
                    return "shouldShowSystemDecors";
                case 104:
                    return "setShouldShowSystemDecors";
                case 105:
                    return "getDisplayImePolicy";
                case 106:
                    return "setDisplayImePolicy";
                case 107:
                    return "syncInputTransactions";
                case 108:
                    return "isLayerTracing";
                case 109:
                    return "setLayerTracing";
                case 110:
                    return "mirrorDisplay";
                case 111:
                    return "setDisplayWindowInsetsController";
                case 112:
                    return "updateDisplayWindowRequestedVisibleTypes";
                case 113:
                    return "getWindowInsets";
                case 114:
                    return "getPossibleDisplayInfo";
                case 115:
                    return "showGlobalActions";
                case 116:
                    return "setLayerTracingFlags";
                case 117:
                    return "setActiveTransactionTracing";
                case 118:
                    return "requestScrollCapture";
                case 119:
                    return "holdLock";
                case 120:
                    return "getSupportedDisplayHashAlgorithms";
                case 121:
                    return "verifyDisplayHash";
                case 122:
                    return "setDisplayHashThrottlingEnabled";
                case 123:
                    return "attachWindowContextToDisplayArea";
                case 124:
                    return "attachWindowContextToWindowToken";
                case 125:
                    return "attachWindowContextToDisplayContent";
                case 126:
                    return "detachWindowContext";
                case 127:
                    return "registerCrossWindowBlurEnabledListener";
                case 128:
                    return "unregisterCrossWindowBlurEnabledListener";
                case 129:
                    return "isTaskSnapshotSupported";
                case 130:
                    return "getImeDisplayId";
                case 131:
                    return "setTaskSnapshotEnabled";
                case 132:
                    return "setTaskTransitionSpec";
                case 133:
                    return "clearTaskTransitionSpec";
                case 134:
                    return "registerTaskFpsCallback";
                case 135:
                    return "unregisterTaskFpsCallback";
                case 136:
                    return "snapshotTaskForRecents";
                case 137:
                    return "setRecentsAppBehindSystemBars";
                case 138:
                    return "getLetterboxBackgroundColorInArgb";
                case 139:
                    return "isLetterboxBackgroundMultiColored";
                case 140:
                    return "captureDisplay";
                case 141:
                    return "isGlobalKey";
                case 142:
                    return "addToSurfaceSyncGroup";
                case 143:
                    return "markSurfaceSyncGroupReady";
                case 144:
                    return "notifyScreenshotListeners";
                case 145:
                    return "replaceContentOnDisplay";
                case 146:
                    return "registerDecorViewGestureListener";
                case 147:
                    return "unregisterDecorViewGestureListener";
                case 148:
                    return "registerTrustedPresentationListener";
                case 149:
                    return "unregisterTrustedPresentationListener";
                case 150:
                    return "registerScreenRecordingCallback";
                case 151:
                    return "unregisterScreenRecordingCallback";
                case 152:
                    return "setGlobalDragListener";
                case 153:
                    return "transferTouchGesture";
                case 154:
                    return "onOverlayChanged";
                default:
                    return null;
            }
        }

        @Override // android.os.Binder
        public java.lang.String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, android.os.Parcel parcel, android.os.Parcel parcel2, int i2) throws android.os.RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean startViewServer = startViewServer(readInt);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(startViewServer);
                    return true;
                case 2:
                    boolean stopViewServer = stopViewServer();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(stopViewServer);
                    return true;
                case 3:
                    boolean isViewServerRunning = isViewServerRunning();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isViewServerRunning);
                    return true;
                case 4:
                    android.view.IWindowSessionCallback asInterface = android.view.IWindowSessionCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    android.view.IWindowSession openSession = openSession(asInterface);
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(openSession);
                    return true;
                case 5:
                    int readInt2 = parcel.readInt();
                    android.graphics.Point point = new android.graphics.Point();
                    parcel.enforceNoDataAvail();
                    getInitialDisplaySize(readInt2, point);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(point, 1);
                    return true;
                case 6:
                    int readInt3 = parcel.readInt();
                    android.graphics.Point point2 = new android.graphics.Point();
                    parcel.enforceNoDataAvail();
                    getBaseDisplaySize(readInt3, point2);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(point2, 1);
                    return true;
                case 7:
                    int readInt4 = parcel.readInt();
                    int readInt5 = parcel.readInt();
                    int readInt6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setForcedDisplaySize(readInt4, readInt5, readInt6);
                    parcel2.writeNoException();
                    return true;
                case 8:
                    int readInt7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    clearForcedDisplaySize(readInt7);
                    parcel2.writeNoException();
                    return true;
                case 9:
                    int readInt8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int initialDisplayDensity = getInitialDisplayDensity(readInt8);
                    parcel2.writeNoException();
                    parcel2.writeInt(initialDisplayDensity);
                    return true;
                case 10:
                    int readInt9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int baseDisplayDensity = getBaseDisplayDensity(readInt9);
                    parcel2.writeNoException();
                    parcel2.writeInt(baseDisplayDensity);
                    return true;
                case 11:
                    java.lang.String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int displayIdByUniqueId = getDisplayIdByUniqueId(readString);
                    parcel2.writeNoException();
                    parcel2.writeInt(displayIdByUniqueId);
                    return true;
                case 12:
                    int readInt10 = parcel.readInt();
                    int readInt11 = parcel.readInt();
                    int readInt12 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setForcedDisplayDensityForUser(readInt10, readInt11, readInt12);
                    parcel2.writeNoException();
                    return true;
                case 13:
                    int readInt13 = parcel.readInt();
                    int readInt14 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    clearForcedDisplayDensityForUser(readInt13, readInt14);
                    parcel2.writeNoException();
                    return true;
                case 14:
                    int readInt15 = parcel.readInt();
                    int readInt16 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setForcedDisplayScalingMode(readInt15, readInt16);
                    parcel2.writeNoException();
                    return true;
                case 15:
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setEventDispatching(readBoolean);
                    parcel2.writeNoException();
                    return true;
                case 16:
                    android.os.IBinder readStrongBinder = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    boolean isWindowToken = isWindowToken(readStrongBinder);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isWindowToken);
                    return true;
                case 17:
                    android.os.IBinder readStrongBinder2 = parcel.readStrongBinder();
                    int readInt17 = parcel.readInt();
                    int readInt18 = parcel.readInt();
                    android.os.Bundle bundle = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    addWindowToken(readStrongBinder2, readInt17, readInt18, bundle);
                    parcel2.writeNoException();
                    return true;
                case 18:
                    android.os.IBinder readStrongBinder3 = parcel.readStrongBinder();
                    int readInt19 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    removeWindowToken(readStrongBinder3, readInt19);
                    parcel2.writeNoException();
                    return true;
                case 19:
                    android.view.IDisplayChangeWindowController asInterface2 = android.view.IDisplayChangeWindowController.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    setDisplayChangeWindowController(asInterface2);
                    parcel2.writeNoException();
                    return true;
                case 20:
                    int readInt20 = parcel.readInt();
                    android.view.IWindow asInterface3 = android.view.IWindow.Stub.asInterface(parcel.readStrongBinder());
                    int readInt21 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.view.SurfaceControl addShellRoot = addShellRoot(readInt20, asInterface3, readInt21);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(addShellRoot, 1);
                    return true;
                case 21:
                    int readInt22 = parcel.readInt();
                    int readInt23 = parcel.readInt();
                    android.view.IWindow asInterface4 = android.view.IWindow.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    setShellRootAccessibilityWindow(readInt22, readInt23, asInterface4);
                    parcel2.writeNoException();
                    return true;
                case 22:
                    android.view.IAppTransitionAnimationSpecsFuture asInterface5 = android.view.IAppTransitionAnimationSpecsFuture.Stub.asInterface(parcel.readStrongBinder());
                    android.os.IRemoteCallback asInterface6 = android.os.IRemoteCallback.Stub.asInterface(parcel.readStrongBinder());
                    boolean readBoolean2 = parcel.readBoolean();
                    int readInt24 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    overridePendingAppTransitionMultiThumbFuture(asInterface5, asInterface6, readBoolean2, readInt24);
                    parcel2.writeNoException();
                    return true;
                case 23:
                    android.view.RemoteAnimationAdapter remoteAnimationAdapter = (android.view.RemoteAnimationAdapter) parcel.readTypedObject(android.view.RemoteAnimationAdapter.CREATOR);
                    int readInt25 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    overridePendingAppTransitionRemote(remoteAnimationAdapter, readInt25);
                    parcel2.writeNoException();
                    return true;
                case 24:
                    endProlongedAnimations();
                    parcel2.writeNoException();
                    return true;
                case 25:
                    int readInt26 = parcel.readInt();
                    int readInt27 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    startFreezingScreen(readInt26, readInt27);
                    parcel2.writeNoException();
                    return true;
                case 26:
                    stopFreezingScreen();
                    parcel2.writeNoException();
                    return true;
                case 27:
                    android.os.IBinder readStrongBinder4 = parcel.readStrongBinder();
                    java.lang.String readString2 = parcel.readString();
                    int readInt28 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    disableKeyguard(readStrongBinder4, readString2, readInt28);
                    parcel2.writeNoException();
                    return true;
                case 28:
                    android.os.IBinder readStrongBinder5 = parcel.readStrongBinder();
                    int readInt29 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    reenableKeyguard(readStrongBinder5, readInt29);
                    parcel2.writeNoException();
                    return true;
                case 29:
                    android.view.IOnKeyguardExitResult asInterface7 = android.view.IOnKeyguardExitResult.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    exitKeyguardSecurely(asInterface7);
                    parcel2.writeNoException();
                    return true;
                case 30:
                    boolean isKeyguardLocked = isKeyguardLocked();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isKeyguardLocked);
                    return true;
                case 31:
                    int readInt30 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isKeyguardSecure = isKeyguardSecure(readInt30);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isKeyguardSecure);
                    return true;
                case 32:
                    com.android.internal.policy.IKeyguardDismissCallback asInterface8 = com.android.internal.policy.IKeyguardDismissCallback.Stub.asInterface(parcel.readStrongBinder());
                    java.lang.CharSequence charSequence = (java.lang.CharSequence) parcel.readTypedObject(android.text.TextUtils.CHAR_SEQUENCE_CREATOR);
                    parcel.enforceNoDataAvail();
                    dismissKeyguard(asInterface8, charSequence);
                    parcel2.writeNoException();
                    return true;
                case 33:
                    com.android.internal.policy.IKeyguardLockedStateListener asInterface9 = com.android.internal.policy.IKeyguardLockedStateListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    addKeyguardLockedStateListener(asInterface9);
                    parcel2.writeNoException();
                    return true;
                case 34:
                    com.android.internal.policy.IKeyguardLockedStateListener asInterface10 = com.android.internal.policy.IKeyguardLockedStateListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    removeKeyguardLockedStateListener(asInterface10);
                    parcel2.writeNoException();
                    return true;
                case 35:
                    boolean readBoolean3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setSwitchingUser(readBoolean3);
                    parcel2.writeNoException();
                    return true;
                case 36:
                    java.lang.String readString3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    closeSystemDialogs(readString3);
                    parcel2.writeNoException();
                    return true;
                case 37:
                    int readInt31 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    float animationScale = getAnimationScale(readInt31);
                    parcel2.writeNoException();
                    parcel2.writeFloat(animationScale);
                    return true;
                case 38:
                    float[] animationScales = getAnimationScales();
                    parcel2.writeNoException();
                    parcel2.writeFloatArray(animationScales);
                    return true;
                case 39:
                    int readInt32 = parcel.readInt();
                    float readFloat = parcel.readFloat();
                    parcel.enforceNoDataAvail();
                    setAnimationScale(readInt32, readFloat);
                    parcel2.writeNoException();
                    return true;
                case 40:
                    float[] createFloatArray = parcel.createFloatArray();
                    parcel.enforceNoDataAvail();
                    setAnimationScales(createFloatArray);
                    parcel2.writeNoException();
                    return true;
                case 41:
                    float currentAnimatorScale = getCurrentAnimatorScale();
                    parcel2.writeNoException();
                    parcel2.writeFloat(currentAnimatorScale);
                    return true;
                case 42:
                    boolean readBoolean4 = parcel.readBoolean();
                    int readInt33 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setInTouchMode(readBoolean4, readInt33);
                    parcel2.writeNoException();
                    return true;
                case 43:
                    boolean readBoolean5 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setInTouchModeOnAllDisplays(readBoolean5);
                    parcel2.writeNoException();
                    return true;
                case 44:
                    int readInt34 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isInTouchMode = isInTouchMode(readInt34);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isInTouchMode);
                    return true;
                case 45:
                    boolean readBoolean6 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    showStrictModeViolation(readBoolean6);
                    parcel2.writeNoException();
                    return true;
                case 46:
                    java.lang.String readString4 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setStrictModeVisualIndicatorPreference(readString4);
                    parcel2.writeNoException();
                    return true;
                case 47:
                    refreshScreenCaptureDisabled();
                    parcel2.writeNoException();
                    return true;
                case 48:
                    int defaultDisplayRotation = getDefaultDisplayRotation();
                    parcel2.writeNoException();
                    parcel2.writeInt(defaultDisplayRotation);
                    return true;
                case 49:
                    android.view.IRotationWatcher asInterface11 = android.view.IRotationWatcher.Stub.asInterface(parcel.readStrongBinder());
                    int readInt35 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int watchRotation = watchRotation(asInterface11, readInt35);
                    parcel2.writeNoException();
                    parcel2.writeInt(watchRotation);
                    return true;
                case 50:
                    android.view.IRotationWatcher asInterface12 = android.view.IRotationWatcher.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    removeRotationWatcher(asInterface12);
                    parcel2.writeNoException();
                    return true;
                case 51:
                    android.os.IBinder readStrongBinder6 = parcel.readStrongBinder();
                    android.view.IRotationWatcher asInterface13 = android.view.IRotationWatcher.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int registerProposedRotationListener = registerProposedRotationListener(readStrongBinder6, asInterface13);
                    parcel2.writeNoException();
                    parcel2.writeInt(registerProposedRotationListener);
                    return true;
                case 52:
                    int readInt36 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int preferredOptionsPanelGravity = getPreferredOptionsPanelGravity(readInt36);
                    parcel2.writeNoException();
                    parcel2.writeInt(preferredOptionsPanelGravity);
                    return true;
                case 53:
                    int readInt37 = parcel.readInt();
                    java.lang.String readString5 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    freezeRotation(readInt37, readString5);
                    parcel2.writeNoException();
                    return true;
                case 54:
                    java.lang.String readString6 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    thawRotation(readString6);
                    parcel2.writeNoException();
                    return true;
                case 55:
                    boolean isRotationFrozen = isRotationFrozen();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isRotationFrozen);
                    return true;
                case 56:
                    int readInt38 = parcel.readInt();
                    int readInt39 = parcel.readInt();
                    java.lang.String readString7 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    freezeDisplayRotation(readInt38, readInt39, readString7);
                    parcel2.writeNoException();
                    return true;
                case 57:
                    int readInt40 = parcel.readInt();
                    java.lang.String readString8 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    thawDisplayRotation(readInt40, readString8);
                    parcel2.writeNoException();
                    return true;
                case 58:
                    int readInt41 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isDisplayRotationFrozen = isDisplayRotationFrozen(readInt41);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isDisplayRotationFrozen);
                    return true;
                case 59:
                    int readInt42 = parcel.readInt();
                    int readInt43 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setFixedToUserRotation(readInt42, readInt43);
                    parcel2.writeNoException();
                    return true;
                case 60:
                    int readInt44 = parcel.readInt();
                    boolean readBoolean7 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setIgnoreOrientationRequest(readInt44, readBoolean7);
                    parcel2.writeNoException();
                    return true;
                case 61:
                    android.graphics.Bitmap screenshotWallpaper = screenshotWallpaper();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(screenshotWallpaper, 1);
                    return true;
                case 62:
                    int readInt45 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.view.SurfaceControl mirrorWallpaperSurface = mirrorWallpaperSurface(readInt45);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(mirrorWallpaperSurface, 1);
                    return true;
                case 63:
                    android.view.IWallpaperVisibilityListener asInterface14 = android.view.IWallpaperVisibilityListener.Stub.asInterface(parcel.readStrongBinder());
                    int readInt46 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean registerWallpaperVisibilityListener = registerWallpaperVisibilityListener(asInterface14, readInt46);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(registerWallpaperVisibilityListener);
                    return true;
                case 64:
                    android.view.IWallpaperVisibilityListener asInterface15 = android.view.IWallpaperVisibilityListener.Stub.asInterface(parcel.readStrongBinder());
                    int readInt47 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    unregisterWallpaperVisibilityListener(asInterface15, readInt47);
                    parcel2.writeNoException();
                    return true;
                case 65:
                    android.view.ISystemGestureExclusionListener asInterface16 = android.view.ISystemGestureExclusionListener.Stub.asInterface(parcel.readStrongBinder());
                    int readInt48 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    registerSystemGestureExclusionListener(asInterface16, readInt48);
                    parcel2.writeNoException();
                    return true;
                case 66:
                    android.view.ISystemGestureExclusionListener asInterface17 = android.view.ISystemGestureExclusionListener.Stub.asInterface(parcel.readStrongBinder());
                    int readInt49 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    unregisterSystemGestureExclusionListener(asInterface17, readInt49);
                    parcel2.writeNoException();
                    return true;
                case 67:
                    android.app.IAssistDataReceiver asInterface18 = android.app.IAssistDataReceiver.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    boolean requestAssistScreenshot = requestAssistScreenshot(asInterface18);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(requestAssistScreenshot);
                    return true;
                case 68:
                    int readInt50 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    hideTransientBars(readInt50);
                    return true;
                case 69:
                    boolean readBoolean8 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setRecentsVisibility(readBoolean8);
                    return true;
                case 70:
                    int readInt51 = parcel.readInt();
                    android.graphics.Rect[] rectArr = (android.graphics.Rect[]) parcel.createTypedArray(android.graphics.Rect.CREATOR);
                    parcel.enforceNoDataAvail();
                    updateStaticPrivacyIndicatorBounds(readInt51, rectArr);
                    return true;
                case 71:
                    boolean readBoolean9 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setNavBarVirtualKeyHapticFeedbackEnabled(readBoolean9);
                    parcel2.writeNoException();
                    return true;
                case 72:
                    int readInt52 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean hasNavigationBar = hasNavigationBar(readInt52);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(hasNavigationBar);
                    return true;
                case 73:
                    android.os.Bundle bundle2 = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    lockNow(bundle2);
                    parcel2.writeNoException();
                    return true;
                case 74:
                    boolean isSafeModeEnabled = isSafeModeEnabled();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isSafeModeEnabled);
                    return true;
                case 75:
                    android.os.IBinder readStrongBinder7 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    boolean clearWindowContentFrameStats = clearWindowContentFrameStats(readStrongBinder7);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(clearWindowContentFrameStats);
                    return true;
                case 76:
                    android.os.IBinder readStrongBinder8 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    android.view.WindowContentFrameStats windowContentFrameStats = getWindowContentFrameStats(readStrongBinder8);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(windowContentFrameStats, 1);
                    return true;
                case 77:
                    int dockedStackSide = getDockedStackSide();
                    parcel2.writeNoException();
                    parcel2.writeInt(dockedStackSide);
                    return true;
                case 78:
                    int readInt53 = parcel.readInt();
                    android.view.IPinnedTaskListener asInterface19 = android.view.IPinnedTaskListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerPinnedTaskListener(readInt53, asInterface19);
                    parcel2.writeNoException();
                    return true;
                case 79:
                    com.android.internal.os.IResultReceiver asInterface20 = com.android.internal.os.IResultReceiver.Stub.asInterface(parcel.readStrongBinder());
                    int readInt54 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    requestAppKeyboardShortcuts(asInterface20, readInt54);
                    parcel2.writeNoException();
                    return true;
                case 80:
                    com.android.internal.os.IResultReceiver asInterface21 = com.android.internal.os.IResultReceiver.Stub.asInterface(parcel.readStrongBinder());
                    int readInt55 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    requestImeKeyboardShortcuts(asInterface21, readInt55);
                    parcel2.writeNoException();
                    return true;
                case 81:
                    int readInt56 = parcel.readInt();
                    android.graphics.Rect rect = new android.graphics.Rect();
                    parcel.enforceNoDataAvail();
                    getStableInsets(readInt56, rect);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(rect, 1);
                    return true;
                case 82:
                    long readLong = parcel.readLong();
                    com.android.internal.policy.IShortcutService asInterface22 = com.android.internal.policy.IShortcutService.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerShortcutKey(readLong, asInterface22);
                    parcel2.writeNoException();
                    return true;
                case 83:
                    android.os.IBinder readStrongBinder9 = parcel.readStrongBinder();
                    java.lang.String readString9 = parcel.readString();
                    int readInt57 = parcel.readInt();
                    android.view.InputChannel inputChannel = new android.view.InputChannel();
                    parcel.enforceNoDataAvail();
                    createInputConsumer(readStrongBinder9, readString9, readInt57, inputChannel);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(inputChannel, 1);
                    return true;
                case 84:
                    android.os.IBinder readStrongBinder10 = parcel.readStrongBinder();
                    int readInt58 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean destroyInputConsumer = destroyInputConsumer(readStrongBinder10, readInt58);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(destroyInputConsumer);
                    return true;
                case 85:
                    android.graphics.Region currentImeTouchRegion = getCurrentImeTouchRegion();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(currentImeTouchRegion, 1);
                    return true;
                case 86:
                    android.view.IDisplayFoldListener asInterface23 = android.view.IDisplayFoldListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerDisplayFoldListener(asInterface23);
                    parcel2.writeNoException();
                    return true;
                case 87:
                    android.view.IDisplayFoldListener asInterface24 = android.view.IDisplayFoldListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterDisplayFoldListener(asInterface24);
                    parcel2.writeNoException();
                    return true;
                case 88:
                    android.view.IDisplayWindowListener asInterface25 = android.view.IDisplayWindowListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int[] registerDisplayWindowListener = registerDisplayWindowListener(asInterface25);
                    parcel2.writeNoException();
                    parcel2.writeIntArray(registerDisplayWindowListener);
                    return true;
                case 89:
                    android.view.IDisplayWindowListener asInterface26 = android.view.IDisplayWindowListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterDisplayWindowListener(asInterface26);
                    parcel2.writeNoException();
                    return true;
                case 90:
                    startWindowTrace();
                    parcel2.writeNoException();
                    return true;
                case 91:
                    stopWindowTrace();
                    parcel2.writeNoException();
                    return true;
                case 92:
                    saveWindowTraceToFile();
                    parcel2.writeNoException();
                    return true;
                case 93:
                    boolean isWindowTraceEnabled = isWindowTraceEnabled();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isWindowTraceEnabled);
                    return true;
                case 94:
                    startTransitionTrace();
                    parcel2.writeNoException();
                    return true;
                case 95:
                    stopTransitionTrace();
                    parcel2.writeNoException();
                    return true;
                case 96:
                    boolean isTransitionTraceEnabled = isTransitionTraceEnabled();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isTransitionTraceEnabled);
                    return true;
                case 97:
                    int readInt59 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int windowingMode = getWindowingMode(readInt59);
                    parcel2.writeNoException();
                    parcel2.writeInt(windowingMode);
                    return true;
                case 98:
                    int readInt60 = parcel.readInt();
                    int readInt61 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setWindowingMode(readInt60, readInt61);
                    parcel2.writeNoException();
                    return true;
                case 99:
                    int readInt62 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int removeContentMode = getRemoveContentMode(readInt62);
                    parcel2.writeNoException();
                    parcel2.writeInt(removeContentMode);
                    return true;
                case 100:
                    int readInt63 = parcel.readInt();
                    int readInt64 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setRemoveContentMode(readInt63, readInt64);
                    parcel2.writeNoException();
                    return true;
                case 101:
                    int readInt65 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean shouldShowWithInsecureKeyguard = shouldShowWithInsecureKeyguard(readInt65);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(shouldShowWithInsecureKeyguard);
                    return true;
                case 102:
                    int readInt66 = parcel.readInt();
                    boolean readBoolean10 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setShouldShowWithInsecureKeyguard(readInt66, readBoolean10);
                    parcel2.writeNoException();
                    return true;
                case 103:
                    int readInt67 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean shouldShowSystemDecors = shouldShowSystemDecors(readInt67);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(shouldShowSystemDecors);
                    return true;
                case 104:
                    int readInt68 = parcel.readInt();
                    boolean readBoolean11 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setShouldShowSystemDecors(readInt68, readBoolean11);
                    parcel2.writeNoException();
                    return true;
                case 105:
                    int readInt69 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int displayImePolicy = getDisplayImePolicy(readInt69);
                    parcel2.writeNoException();
                    parcel2.writeInt(displayImePolicy);
                    return true;
                case 106:
                    int readInt70 = parcel.readInt();
                    int readInt71 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setDisplayImePolicy(readInt70, readInt71);
                    parcel2.writeNoException();
                    return true;
                case 107:
                    boolean readBoolean12 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    syncInputTransactions(readBoolean12);
                    parcel2.writeNoException();
                    return true;
                case 108:
                    boolean isLayerTracing = isLayerTracing();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isLayerTracing);
                    return true;
                case 109:
                    boolean readBoolean13 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setLayerTracing(readBoolean13);
                    parcel2.writeNoException();
                    return true;
                case 110:
                    int readInt72 = parcel.readInt();
                    android.view.SurfaceControl surfaceControl = new android.view.SurfaceControl();
                    parcel.enforceNoDataAvail();
                    boolean mirrorDisplay = mirrorDisplay(readInt72, surfaceControl);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(mirrorDisplay);
                    parcel2.writeTypedObject(surfaceControl, 1);
                    return true;
                case 111:
                    int readInt73 = parcel.readInt();
                    android.view.IDisplayWindowInsetsController asInterface27 = android.view.IDisplayWindowInsetsController.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    setDisplayWindowInsetsController(readInt73, asInterface27);
                    parcel2.writeNoException();
                    return true;
                case 112:
                    int readInt74 = parcel.readInt();
                    int readInt75 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    updateDisplayWindowRequestedVisibleTypes(readInt74, readInt75);
                    parcel2.writeNoException();
                    return true;
                case 113:
                    int readInt76 = parcel.readInt();
                    android.os.IBinder readStrongBinder11 = parcel.readStrongBinder();
                    android.view.InsetsState insetsState = new android.view.InsetsState();
                    parcel.enforceNoDataAvail();
                    boolean windowInsets = getWindowInsets(readInt76, readStrongBinder11, insetsState);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(windowInsets);
                    parcel2.writeTypedObject(insetsState, 1);
                    return true;
                case 114:
                    int readInt77 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    java.util.List<android.view.DisplayInfo> possibleDisplayInfo = getPossibleDisplayInfo(readInt77);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(possibleDisplayInfo, 1);
                    return true;
                case 115:
                    showGlobalActions();
                    parcel2.writeNoException();
                    return true;
                case 116:
                    int readInt78 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setLayerTracingFlags(readInt78);
                    parcel2.writeNoException();
                    return true;
                case 117:
                    boolean readBoolean14 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setActiveTransactionTracing(readBoolean14);
                    parcel2.writeNoException();
                    return true;
                case 118:
                    int readInt79 = parcel.readInt();
                    android.os.IBinder readStrongBinder12 = parcel.readStrongBinder();
                    int readInt80 = parcel.readInt();
                    android.view.IScrollCaptureResponseListener asInterface28 = android.view.IScrollCaptureResponseListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    requestScrollCapture(readInt79, readStrongBinder12, readInt80, asInterface28);
                    parcel2.writeNoException();
                    return true;
                case 119:
                    android.os.IBinder readStrongBinder13 = parcel.readStrongBinder();
                    int readInt81 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    holdLock(readStrongBinder13, readInt81);
                    parcel2.writeNoException();
                    return true;
                case 120:
                    java.lang.String[] supportedDisplayHashAlgorithms = getSupportedDisplayHashAlgorithms();
                    parcel2.writeNoException();
                    parcel2.writeStringArray(supportedDisplayHashAlgorithms);
                    return true;
                case 121:
                    android.view.displayhash.DisplayHash displayHash = (android.view.displayhash.DisplayHash) parcel.readTypedObject(android.view.displayhash.DisplayHash.CREATOR);
                    parcel.enforceNoDataAvail();
                    android.view.displayhash.VerifiedDisplayHash verifyDisplayHash = verifyDisplayHash(displayHash);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(verifyDisplayHash, 1);
                    return true;
                case 122:
                    boolean readBoolean15 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setDisplayHashThrottlingEnabled(readBoolean15);
                    parcel2.writeNoException();
                    return true;
                case 123:
                    android.app.IApplicationThread asInterface29 = android.app.IApplicationThread.Stub.asInterface(parcel.readStrongBinder());
                    android.os.IBinder readStrongBinder14 = parcel.readStrongBinder();
                    int readInt82 = parcel.readInt();
                    int readInt83 = parcel.readInt();
                    android.os.Bundle bundle3 = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    android.window.WindowContextInfo attachWindowContextToDisplayArea = attachWindowContextToDisplayArea(asInterface29, readStrongBinder14, readInt82, readInt83, bundle3);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(attachWindowContextToDisplayArea, 1);
                    return true;
                case 124:
                    android.app.IApplicationThread asInterface30 = android.app.IApplicationThread.Stub.asInterface(parcel.readStrongBinder());
                    android.os.IBinder readStrongBinder15 = parcel.readStrongBinder();
                    android.os.IBinder readStrongBinder16 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    android.window.WindowContextInfo attachWindowContextToWindowToken = attachWindowContextToWindowToken(asInterface30, readStrongBinder15, readStrongBinder16);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(attachWindowContextToWindowToken, 1);
                    return true;
                case 125:
                    android.app.IApplicationThread asInterface31 = android.app.IApplicationThread.Stub.asInterface(parcel.readStrongBinder());
                    android.os.IBinder readStrongBinder17 = parcel.readStrongBinder();
                    int readInt84 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.window.WindowContextInfo attachWindowContextToDisplayContent = attachWindowContextToDisplayContent(asInterface31, readStrongBinder17, readInt84);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(attachWindowContextToDisplayContent, 1);
                    return true;
                case 126:
                    android.os.IBinder readStrongBinder18 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    detachWindowContext(readStrongBinder18);
                    parcel2.writeNoException();
                    return true;
                case 127:
                    android.view.ICrossWindowBlurEnabledListener asInterface32 = android.view.ICrossWindowBlurEnabledListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    boolean registerCrossWindowBlurEnabledListener = registerCrossWindowBlurEnabledListener(asInterface32);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(registerCrossWindowBlurEnabledListener);
                    return true;
                case 128:
                    android.view.ICrossWindowBlurEnabledListener asInterface33 = android.view.ICrossWindowBlurEnabledListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterCrossWindowBlurEnabledListener(asInterface33);
                    parcel2.writeNoException();
                    return true;
                case 129:
                    boolean isTaskSnapshotSupported = isTaskSnapshotSupported();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isTaskSnapshotSupported);
                    return true;
                case 130:
                    int imeDisplayId = getImeDisplayId();
                    parcel2.writeNoException();
                    parcel2.writeInt(imeDisplayId);
                    return true;
                case 131:
                    boolean readBoolean16 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setTaskSnapshotEnabled(readBoolean16);
                    parcel2.writeNoException();
                    return true;
                case 132:
                    android.view.TaskTransitionSpec taskTransitionSpec = (android.view.TaskTransitionSpec) parcel.readTypedObject(android.view.TaskTransitionSpec.CREATOR);
                    parcel.enforceNoDataAvail();
                    setTaskTransitionSpec(taskTransitionSpec);
                    parcel2.writeNoException();
                    return true;
                case 133:
                    clearTaskTransitionSpec();
                    parcel2.writeNoException();
                    return true;
                case 134:
                    int readInt85 = parcel.readInt();
                    android.window.ITaskFpsCallback asInterface34 = android.window.ITaskFpsCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerTaskFpsCallback(readInt85, asInterface34);
                    parcel2.writeNoException();
                    return true;
                case 135:
                    android.window.ITaskFpsCallback asInterface35 = android.window.ITaskFpsCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterTaskFpsCallback(asInterface35);
                    parcel2.writeNoException();
                    return true;
                case 136:
                    int readInt86 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.graphics.Bitmap snapshotTaskForRecents = snapshotTaskForRecents(readInt86);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(snapshotTaskForRecents, 1);
                    return true;
                case 137:
                    boolean readBoolean17 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setRecentsAppBehindSystemBars(readBoolean17);
                    parcel2.writeNoException();
                    return true;
                case 138:
                    int letterboxBackgroundColorInArgb = getLetterboxBackgroundColorInArgb();
                    parcel2.writeNoException();
                    parcel2.writeInt(letterboxBackgroundColorInArgb);
                    return true;
                case 139:
                    boolean isLetterboxBackgroundMultiColored = isLetterboxBackgroundMultiColored();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isLetterboxBackgroundMultiColored);
                    return true;
                case 140:
                    int readInt87 = parcel.readInt();
                    android.window.ScreenCapture.CaptureArgs captureArgs = (android.window.ScreenCapture.CaptureArgs) parcel.readTypedObject(android.window.ScreenCapture.CaptureArgs.CREATOR);
                    android.window.ScreenCapture.ScreenCaptureListener screenCaptureListener = (android.window.ScreenCapture.ScreenCaptureListener) parcel.readTypedObject(android.window.ScreenCapture.ScreenCaptureListener.CREATOR);
                    parcel.enforceNoDataAvail();
                    captureDisplay(readInt87, captureArgs, screenCaptureListener);
                    return true;
                case 141:
                    int readInt88 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isGlobalKey = isGlobalKey(readInt88);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isGlobalKey);
                    return true;
                case 142:
                    android.os.IBinder readStrongBinder19 = parcel.readStrongBinder();
                    boolean readBoolean18 = parcel.readBoolean();
                    android.window.ISurfaceSyncGroupCompletedListener asInterface36 = android.window.ISurfaceSyncGroupCompletedListener.Stub.asInterface(parcel.readStrongBinder());
                    android.window.AddToSurfaceSyncGroupResult addToSurfaceSyncGroupResult = new android.window.AddToSurfaceSyncGroupResult();
                    parcel.enforceNoDataAvail();
                    boolean addToSurfaceSyncGroup = addToSurfaceSyncGroup(readStrongBinder19, readBoolean18, asInterface36, addToSurfaceSyncGroupResult);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(addToSurfaceSyncGroup);
                    parcel2.writeTypedObject(addToSurfaceSyncGroupResult, 1);
                    return true;
                case 143:
                    android.os.IBinder readStrongBinder20 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    markSurfaceSyncGroupReady(readStrongBinder20);
                    return true;
                case 144:
                    int readInt89 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    java.util.List<android.content.ComponentName> notifyScreenshotListeners = notifyScreenshotListeners(readInt89);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(notifyScreenshotListeners, 1);
                    return true;
                case 145:
                    int readInt90 = parcel.readInt();
                    android.view.SurfaceControl surfaceControl2 = (android.view.SurfaceControl) parcel.readTypedObject(android.view.SurfaceControl.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean replaceContentOnDisplay = replaceContentOnDisplay(readInt90, surfaceControl2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(replaceContentOnDisplay);
                    return true;
                case 146:
                    android.view.IDecorViewGestureListener asInterface37 = android.view.IDecorViewGestureListener.Stub.asInterface(parcel.readStrongBinder());
                    int readInt91 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    registerDecorViewGestureListener(asInterface37, readInt91);
                    parcel2.writeNoException();
                    return true;
                case 147:
                    android.view.IDecorViewGestureListener asInterface38 = android.view.IDecorViewGestureListener.Stub.asInterface(parcel.readStrongBinder());
                    int readInt92 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    unregisterDecorViewGestureListener(asInterface38, readInt92);
                    parcel2.writeNoException();
                    return true;
                case 148:
                    android.os.IBinder readStrongBinder21 = parcel.readStrongBinder();
                    android.window.ITrustedPresentationListener asInterface39 = android.window.ITrustedPresentationListener.Stub.asInterface(parcel.readStrongBinder());
                    android.window.TrustedPresentationThresholds trustedPresentationThresholds = (android.window.TrustedPresentationThresholds) parcel.readTypedObject(android.window.TrustedPresentationThresholds.CREATOR);
                    int readInt93 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    registerTrustedPresentationListener(readStrongBinder21, asInterface39, trustedPresentationThresholds, readInt93);
                    parcel2.writeNoException();
                    return true;
                case 149:
                    android.window.ITrustedPresentationListener asInterface40 = android.window.ITrustedPresentationListener.Stub.asInterface(parcel.readStrongBinder());
                    int readInt94 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    unregisterTrustedPresentationListener(asInterface40, readInt94);
                    parcel2.writeNoException();
                    return true;
                case 150:
                    android.window.IScreenRecordingCallback asInterface41 = android.window.IScreenRecordingCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    boolean registerScreenRecordingCallback = registerScreenRecordingCallback(asInterface41);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(registerScreenRecordingCallback);
                    return true;
                case 151:
                    android.window.IScreenRecordingCallback asInterface42 = android.window.IScreenRecordingCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterScreenRecordingCallback(asInterface42);
                    parcel2.writeNoException();
                    return true;
                case 152:
                    android.window.IGlobalDragListener asInterface43 = android.window.IGlobalDragListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    setGlobalDragListener(asInterface43);
                    parcel2.writeNoException();
                    return true;
                case 153:
                    android.window.InputTransferToken inputTransferToken = (android.window.InputTransferToken) parcel.readTypedObject(android.window.InputTransferToken.CREATOR);
                    android.window.InputTransferToken inputTransferToken2 = (android.window.InputTransferToken) parcel.readTypedObject(android.window.InputTransferToken.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean transferTouchGesture = transferTouchGesture(inputTransferToken, inputTransferToken2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(transferTouchGesture);
                    return true;
                case 154:
                    onOverlayChanged();
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.view.IWindowManager {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.view.IWindowManager.Stub.DESCRIPTOR;
            }

            @Override // android.view.IWindowManager
            public boolean startViewServer(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.view.IWindowManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public boolean stopViewServer() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.view.IWindowManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public boolean isViewServerRunning() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.view.IWindowManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public android.view.IWindowSession openSession(android.view.IWindowSessionCallback iWindowSessionCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.view.IWindowManager.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iWindowSessionCallback);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return android.view.IWindowSession.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void getInitialDisplaySize(int i, android.graphics.Point point) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.view.IWindowManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    if (obtain2.readInt() != 0) {
                        point.readFromParcel(obtain2);
                    }
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void getBaseDisplaySize(int i, android.graphics.Point point) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.view.IWindowManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    if (obtain2.readInt() != 0) {
                        point.readFromParcel(obtain2);
                    }
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void setForcedDisplaySize(int i, int i2, int i3) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.view.IWindowManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void clearForcedDisplaySize(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.view.IWindowManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public int getInitialDisplayDensity(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.view.IWindowManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public int getBaseDisplayDensity(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.view.IWindowManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public int getDisplayIdByUniqueId(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.view.IWindowManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void setForcedDisplayDensityForUser(int i, int i2, int i3) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.view.IWindowManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void clearForcedDisplayDensityForUser(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.view.IWindowManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void setForcedDisplayScalingMode(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.view.IWindowManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void setEventDispatching(boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.view.IWindowManager.Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public boolean isWindowToken(android.os.IBinder iBinder) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.view.IWindowManager.Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void addWindowToken(android.os.IBinder iBinder, int i, int i2, android.os.Bundle bundle) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.view.IWindowManager.Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void removeWindowToken(android.os.IBinder iBinder, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.view.IWindowManager.Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i);
                    this.mRemote.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void setDisplayChangeWindowController(android.view.IDisplayChangeWindowController iDisplayChangeWindowController) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.view.IWindowManager.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iDisplayChangeWindowController);
                    this.mRemote.transact(19, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public android.view.SurfaceControl addShellRoot(int i, android.view.IWindow iWindow, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.view.IWindowManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iWindow);
                    obtain.writeInt(i2);
                    this.mRemote.transact(20, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.view.SurfaceControl) obtain2.readTypedObject(android.view.SurfaceControl.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void setShellRootAccessibilityWindow(int i, int i2, android.view.IWindow iWindow) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.view.IWindowManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeStrongInterface(iWindow);
                    this.mRemote.transact(21, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void overridePendingAppTransitionMultiThumbFuture(android.view.IAppTransitionAnimationSpecsFuture iAppTransitionAnimationSpecsFuture, android.os.IRemoteCallback iRemoteCallback, boolean z, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.view.IWindowManager.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iAppTransitionAnimationSpecsFuture);
                    obtain.writeStrongInterface(iRemoteCallback);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i);
                    this.mRemote.transact(22, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void overridePendingAppTransitionRemote(android.view.RemoteAnimationAdapter remoteAnimationAdapter, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.view.IWindowManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(remoteAnimationAdapter, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(23, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void endProlongedAnimations() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.view.IWindowManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(24, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void startFreezingScreen(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.view.IWindowManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(25, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void stopFreezingScreen() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.view.IWindowManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(26, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void disableKeyguard(android.os.IBinder iBinder, java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.view.IWindowManager.Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(27, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void reenableKeyguard(android.os.IBinder iBinder, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.view.IWindowManager.Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i);
                    this.mRemote.transact(28, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void exitKeyguardSecurely(android.view.IOnKeyguardExitResult iOnKeyguardExitResult) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.view.IWindowManager.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iOnKeyguardExitResult);
                    this.mRemote.transact(29, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public boolean isKeyguardLocked() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.view.IWindowManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(30, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public boolean isKeyguardSecure(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.view.IWindowManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(31, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void dismissKeyguard(com.android.internal.policy.IKeyguardDismissCallback iKeyguardDismissCallback, java.lang.CharSequence charSequence) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.view.IWindowManager.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iKeyguardDismissCallback);
                    if (charSequence != null) {
                        obtain.writeInt(1);
                        android.text.TextUtils.writeToParcel(charSequence, obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.mRemote.transact(32, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void addKeyguardLockedStateListener(com.android.internal.policy.IKeyguardLockedStateListener iKeyguardLockedStateListener) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.view.IWindowManager.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iKeyguardLockedStateListener);
                    this.mRemote.transact(33, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void removeKeyguardLockedStateListener(com.android.internal.policy.IKeyguardLockedStateListener iKeyguardLockedStateListener) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.view.IWindowManager.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iKeyguardLockedStateListener);
                    this.mRemote.transact(34, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void setSwitchingUser(boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.view.IWindowManager.Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(35, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void closeSystemDialogs(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.view.IWindowManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(36, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public float getAnimationScale(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.view.IWindowManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(37, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readFloat();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public float[] getAnimationScales() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.view.IWindowManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(38, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createFloatArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void setAnimationScale(int i, float f) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.view.IWindowManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeFloat(f);
                    this.mRemote.transact(39, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void setAnimationScales(float[] fArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.view.IWindowManager.Stub.DESCRIPTOR);
                    obtain.writeFloatArray(fArr);
                    this.mRemote.transact(40, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public float getCurrentAnimatorScale() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.view.IWindowManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(41, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readFloat();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void setInTouchMode(boolean z, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.view.IWindowManager.Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i);
                    this.mRemote.transact(42, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void setInTouchModeOnAllDisplays(boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.view.IWindowManager.Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(43, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public boolean isInTouchMode(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.view.IWindowManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(44, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void showStrictModeViolation(boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.view.IWindowManager.Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(45, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void setStrictModeVisualIndicatorPreference(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.view.IWindowManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(46, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void refreshScreenCaptureDisabled() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.view.IWindowManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(47, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public int getDefaultDisplayRotation() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.view.IWindowManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(48, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public int watchRotation(android.view.IRotationWatcher iRotationWatcher, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.view.IWindowManager.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iRotationWatcher);
                    obtain.writeInt(i);
                    this.mRemote.transact(49, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void removeRotationWatcher(android.view.IRotationWatcher iRotationWatcher) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.view.IWindowManager.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iRotationWatcher);
                    this.mRemote.transact(50, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public int registerProposedRotationListener(android.os.IBinder iBinder, android.view.IRotationWatcher iRotationWatcher) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.view.IWindowManager.Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeStrongInterface(iRotationWatcher);
                    this.mRemote.transact(51, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public int getPreferredOptionsPanelGravity(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.view.IWindowManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(52, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void freezeRotation(int i, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.view.IWindowManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(53, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void thawRotation(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.view.IWindowManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(54, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public boolean isRotationFrozen() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.view.IWindowManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(55, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void freezeDisplayRotation(int i, int i2, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.view.IWindowManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeString(str);
                    this.mRemote.transact(56, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void thawDisplayRotation(int i, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.view.IWindowManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(57, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public boolean isDisplayRotationFrozen(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.view.IWindowManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(58, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void setFixedToUserRotation(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.view.IWindowManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(59, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void setIgnoreOrientationRequest(int i, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.view.IWindowManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(60, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public android.graphics.Bitmap screenshotWallpaper() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.view.IWindowManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(61, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.graphics.Bitmap) obtain2.readTypedObject(android.graphics.Bitmap.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public android.view.SurfaceControl mirrorWallpaperSurface(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.view.IWindowManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(62, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.view.SurfaceControl) obtain2.readTypedObject(android.view.SurfaceControl.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public boolean registerWallpaperVisibilityListener(android.view.IWallpaperVisibilityListener iWallpaperVisibilityListener, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.view.IWindowManager.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iWallpaperVisibilityListener);
                    obtain.writeInt(i);
                    this.mRemote.transact(63, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void unregisterWallpaperVisibilityListener(android.view.IWallpaperVisibilityListener iWallpaperVisibilityListener, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.view.IWindowManager.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iWallpaperVisibilityListener);
                    obtain.writeInt(i);
                    this.mRemote.transact(64, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void registerSystemGestureExclusionListener(android.view.ISystemGestureExclusionListener iSystemGestureExclusionListener, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.view.IWindowManager.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iSystemGestureExclusionListener);
                    obtain.writeInt(i);
                    this.mRemote.transact(65, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void unregisterSystemGestureExclusionListener(android.view.ISystemGestureExclusionListener iSystemGestureExclusionListener, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.view.IWindowManager.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iSystemGestureExclusionListener);
                    obtain.writeInt(i);
                    this.mRemote.transact(66, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public boolean requestAssistScreenshot(android.app.IAssistDataReceiver iAssistDataReceiver) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.view.IWindowManager.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iAssistDataReceiver);
                    this.mRemote.transact(67, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void hideTransientBars(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.view.IWindowManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(68, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void setRecentsVisibility(boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.view.IWindowManager.Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(69, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void updateStaticPrivacyIndicatorBounds(int i, android.graphics.Rect[] rectArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.view.IWindowManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedArray(rectArr, 0);
                    this.mRemote.transact(70, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void setNavBarVirtualKeyHapticFeedbackEnabled(boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.view.IWindowManager.Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(71, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public boolean hasNavigationBar(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.view.IWindowManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(72, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void lockNow(android.os.Bundle bundle) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.view.IWindowManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(73, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public boolean isSafeModeEnabled() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.view.IWindowManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(74, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public boolean clearWindowContentFrameStats(android.os.IBinder iBinder) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.view.IWindowManager.Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(75, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public android.view.WindowContentFrameStats getWindowContentFrameStats(android.os.IBinder iBinder) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.view.IWindowManager.Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(76, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.view.WindowContentFrameStats) obtain2.readTypedObject(android.view.WindowContentFrameStats.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public int getDockedStackSide() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.view.IWindowManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(77, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void registerPinnedTaskListener(int i, android.view.IPinnedTaskListener iPinnedTaskListener) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.view.IWindowManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iPinnedTaskListener);
                    this.mRemote.transact(78, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void requestAppKeyboardShortcuts(com.android.internal.os.IResultReceiver iResultReceiver, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.view.IWindowManager.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iResultReceiver);
                    obtain.writeInt(i);
                    this.mRemote.transact(79, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void requestImeKeyboardShortcuts(com.android.internal.os.IResultReceiver iResultReceiver, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.view.IWindowManager.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iResultReceiver);
                    obtain.writeInt(i);
                    this.mRemote.transact(80, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void getStableInsets(int i, android.graphics.Rect rect) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.view.IWindowManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(81, obtain, obtain2, 0);
                    obtain2.readException();
                    if (obtain2.readInt() != 0) {
                        rect.readFromParcel(obtain2);
                    }
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void registerShortcutKey(long j, com.android.internal.policy.IShortcutService iShortcutService) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.view.IWindowManager.Stub.DESCRIPTOR);
                    obtain.writeLong(j);
                    obtain.writeStrongInterface(iShortcutService);
                    this.mRemote.transact(82, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void createInputConsumer(android.os.IBinder iBinder, java.lang.String str, int i, android.view.InputChannel inputChannel) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.view.IWindowManager.Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(83, obtain, obtain2, 0);
                    obtain2.readException();
                    if (obtain2.readInt() != 0) {
                        inputChannel.readFromParcel(obtain2);
                    }
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public boolean destroyInputConsumer(android.os.IBinder iBinder, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.view.IWindowManager.Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i);
                    this.mRemote.transact(84, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public android.graphics.Region getCurrentImeTouchRegion() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.view.IWindowManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(85, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.graphics.Region) obtain2.readTypedObject(android.graphics.Region.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void registerDisplayFoldListener(android.view.IDisplayFoldListener iDisplayFoldListener) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.view.IWindowManager.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iDisplayFoldListener);
                    this.mRemote.transact(86, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void unregisterDisplayFoldListener(android.view.IDisplayFoldListener iDisplayFoldListener) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.view.IWindowManager.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iDisplayFoldListener);
                    this.mRemote.transact(87, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public int[] registerDisplayWindowListener(android.view.IDisplayWindowListener iDisplayWindowListener) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.view.IWindowManager.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iDisplayWindowListener);
                    this.mRemote.transact(88, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createIntArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void unregisterDisplayWindowListener(android.view.IDisplayWindowListener iDisplayWindowListener) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.view.IWindowManager.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iDisplayWindowListener);
                    this.mRemote.transact(89, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void startWindowTrace() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.view.IWindowManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(90, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void stopWindowTrace() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.view.IWindowManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(91, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void saveWindowTraceToFile() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.view.IWindowManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(92, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public boolean isWindowTraceEnabled() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.view.IWindowManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(93, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void startTransitionTrace() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.view.IWindowManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(94, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void stopTransitionTrace() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.view.IWindowManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(95, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public boolean isTransitionTraceEnabled() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.view.IWindowManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(96, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public int getWindowingMode(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.view.IWindowManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(97, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void setWindowingMode(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.view.IWindowManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(98, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public int getRemoveContentMode(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.view.IWindowManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(99, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void setRemoveContentMode(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.view.IWindowManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(100, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public boolean shouldShowWithInsecureKeyguard(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.view.IWindowManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(101, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void setShouldShowWithInsecureKeyguard(int i, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.view.IWindowManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(102, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public boolean shouldShowSystemDecors(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.view.IWindowManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(103, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void setShouldShowSystemDecors(int i, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.view.IWindowManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(104, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public int getDisplayImePolicy(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.view.IWindowManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(105, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void setDisplayImePolicy(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.view.IWindowManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(106, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void syncInputTransactions(boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.view.IWindowManager.Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(107, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public boolean isLayerTracing() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.view.IWindowManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(108, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void setLayerTracing(boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.view.IWindowManager.Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(109, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public boolean mirrorDisplay(int i, android.view.SurfaceControl surfaceControl) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.view.IWindowManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(110, obtain, obtain2, 0);
                    obtain2.readException();
                    boolean readBoolean = obtain2.readBoolean();
                    if (obtain2.readInt() != 0) {
                        surfaceControl.readFromParcel(obtain2);
                    }
                    return readBoolean;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void setDisplayWindowInsetsController(int i, android.view.IDisplayWindowInsetsController iDisplayWindowInsetsController) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.view.IWindowManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iDisplayWindowInsetsController);
                    this.mRemote.transact(111, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void updateDisplayWindowRequestedVisibleTypes(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.view.IWindowManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(112, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public boolean getWindowInsets(int i, android.os.IBinder iBinder, android.view.InsetsState insetsState) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.view.IWindowManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(113, obtain, obtain2, 0);
                    obtain2.readException();
                    boolean readBoolean = obtain2.readBoolean();
                    if (obtain2.readInt() != 0) {
                        insetsState.readFromParcel(obtain2);
                    }
                    return readBoolean;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public java.util.List<android.view.DisplayInfo> getPossibleDisplayInfo(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.view.IWindowManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(114, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(android.view.DisplayInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void showGlobalActions() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.view.IWindowManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(115, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void setLayerTracingFlags(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.view.IWindowManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(116, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void setActiveTransactionTracing(boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.view.IWindowManager.Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(117, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void requestScrollCapture(int i, android.os.IBinder iBinder, int i2, android.view.IScrollCaptureResponseListener iScrollCaptureResponseListener) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.view.IWindowManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i2);
                    obtain.writeStrongInterface(iScrollCaptureResponseListener);
                    this.mRemote.transact(118, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void holdLock(android.os.IBinder iBinder, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.view.IWindowManager.Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i);
                    this.mRemote.transact(119, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public java.lang.String[] getSupportedDisplayHashAlgorithms() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.view.IWindowManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(120, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public android.view.displayhash.VerifiedDisplayHash verifyDisplayHash(android.view.displayhash.DisplayHash displayHash) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.view.IWindowManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(displayHash, 0);
                    this.mRemote.transact(121, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.view.displayhash.VerifiedDisplayHash) obtain2.readTypedObject(android.view.displayhash.VerifiedDisplayHash.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void setDisplayHashThrottlingEnabled(boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.view.IWindowManager.Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(122, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public android.window.WindowContextInfo attachWindowContextToDisplayArea(android.app.IApplicationThread iApplicationThread, android.os.IBinder iBinder, int i, int i2, android.os.Bundle bundle) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.view.IWindowManager.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iApplicationThread);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(123, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.window.WindowContextInfo) obtain2.readTypedObject(android.window.WindowContextInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public android.window.WindowContextInfo attachWindowContextToWindowToken(android.app.IApplicationThread iApplicationThread, android.os.IBinder iBinder, android.os.IBinder iBinder2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.view.IWindowManager.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iApplicationThread);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeStrongBinder(iBinder2);
                    this.mRemote.transact(124, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.window.WindowContextInfo) obtain2.readTypedObject(android.window.WindowContextInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public android.window.WindowContextInfo attachWindowContextToDisplayContent(android.app.IApplicationThread iApplicationThread, android.os.IBinder iBinder, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.view.IWindowManager.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iApplicationThread);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i);
                    this.mRemote.transact(125, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.window.WindowContextInfo) obtain2.readTypedObject(android.window.WindowContextInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void detachWindowContext(android.os.IBinder iBinder) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.view.IWindowManager.Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(126, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public boolean registerCrossWindowBlurEnabledListener(android.view.ICrossWindowBlurEnabledListener iCrossWindowBlurEnabledListener) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.view.IWindowManager.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iCrossWindowBlurEnabledListener);
                    this.mRemote.transact(127, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void unregisterCrossWindowBlurEnabledListener(android.view.ICrossWindowBlurEnabledListener iCrossWindowBlurEnabledListener) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.view.IWindowManager.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iCrossWindowBlurEnabledListener);
                    this.mRemote.transact(128, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public boolean isTaskSnapshotSupported() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.view.IWindowManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(129, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public int getImeDisplayId() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.view.IWindowManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(130, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void setTaskSnapshotEnabled(boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.view.IWindowManager.Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(131, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void setTaskTransitionSpec(android.view.TaskTransitionSpec taskTransitionSpec) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.view.IWindowManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(taskTransitionSpec, 0);
                    this.mRemote.transact(132, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void clearTaskTransitionSpec() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.view.IWindowManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(133, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void registerTaskFpsCallback(int i, android.window.ITaskFpsCallback iTaskFpsCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.view.IWindowManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iTaskFpsCallback);
                    this.mRemote.transact(134, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void unregisterTaskFpsCallback(android.window.ITaskFpsCallback iTaskFpsCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.view.IWindowManager.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iTaskFpsCallback);
                    this.mRemote.transact(135, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public android.graphics.Bitmap snapshotTaskForRecents(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.view.IWindowManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(136, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.graphics.Bitmap) obtain2.readTypedObject(android.graphics.Bitmap.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void setRecentsAppBehindSystemBars(boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.view.IWindowManager.Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(137, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public int getLetterboxBackgroundColorInArgb() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.view.IWindowManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(138, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public boolean isLetterboxBackgroundMultiColored() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.view.IWindowManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(139, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void captureDisplay(int i, android.window.ScreenCapture.CaptureArgs captureArgs, android.window.ScreenCapture.ScreenCaptureListener screenCaptureListener) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.view.IWindowManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(captureArgs, 0);
                    obtain.writeTypedObject(screenCaptureListener, 0);
                    this.mRemote.transact(140, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public boolean isGlobalKey(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.view.IWindowManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(141, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public boolean addToSurfaceSyncGroup(android.os.IBinder iBinder, boolean z, android.window.ISurfaceSyncGroupCompletedListener iSurfaceSyncGroupCompletedListener, android.window.AddToSurfaceSyncGroupResult addToSurfaceSyncGroupResult) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.view.IWindowManager.Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeBoolean(z);
                    obtain.writeStrongInterface(iSurfaceSyncGroupCompletedListener);
                    this.mRemote.transact(142, obtain, obtain2, 0);
                    obtain2.readException();
                    boolean readBoolean = obtain2.readBoolean();
                    if (obtain2.readInt() != 0) {
                        addToSurfaceSyncGroupResult.readFromParcel(obtain2);
                    }
                    return readBoolean;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void markSurfaceSyncGroupReady(android.os.IBinder iBinder) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.view.IWindowManager.Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(143, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public java.util.List<android.content.ComponentName> notifyScreenshotListeners(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.view.IWindowManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(144, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(android.content.ComponentName.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public boolean replaceContentOnDisplay(int i, android.view.SurfaceControl surfaceControl) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.view.IWindowManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(surfaceControl, 0);
                    this.mRemote.transact(145, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void registerDecorViewGestureListener(android.view.IDecorViewGestureListener iDecorViewGestureListener, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.view.IWindowManager.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iDecorViewGestureListener);
                    obtain.writeInt(i);
                    this.mRemote.transact(146, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void unregisterDecorViewGestureListener(android.view.IDecorViewGestureListener iDecorViewGestureListener, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.view.IWindowManager.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iDecorViewGestureListener);
                    obtain.writeInt(i);
                    this.mRemote.transact(147, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void registerTrustedPresentationListener(android.os.IBinder iBinder, android.window.ITrustedPresentationListener iTrustedPresentationListener, android.window.TrustedPresentationThresholds trustedPresentationThresholds, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.view.IWindowManager.Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeStrongInterface(iTrustedPresentationListener);
                    obtain.writeTypedObject(trustedPresentationThresholds, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(148, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void unregisterTrustedPresentationListener(android.window.ITrustedPresentationListener iTrustedPresentationListener, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.view.IWindowManager.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iTrustedPresentationListener);
                    obtain.writeInt(i);
                    this.mRemote.transact(149, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public boolean registerScreenRecordingCallback(android.window.IScreenRecordingCallback iScreenRecordingCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.view.IWindowManager.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iScreenRecordingCallback);
                    this.mRemote.transact(150, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void unregisterScreenRecordingCallback(android.window.IScreenRecordingCallback iScreenRecordingCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.view.IWindowManager.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iScreenRecordingCallback);
                    this.mRemote.transact(151, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void setGlobalDragListener(android.window.IGlobalDragListener iGlobalDragListener) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.view.IWindowManager.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iGlobalDragListener);
                    this.mRemote.transact(152, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public boolean transferTouchGesture(android.window.InputTransferToken inputTransferToken, android.window.InputTransferToken inputTransferToken2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.view.IWindowManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(inputTransferToken, 0);
                    obtain.writeTypedObject(inputTransferToken2, 0);
                    this.mRemote.transact(153, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowManager
            public void onOverlayChanged() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.view.IWindowManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(154, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        protected void setForcedDisplaySize_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.WRITE_SECURE_SETTINGS, getCallingPid(), getCallingUid());
        }

        protected void clearForcedDisplaySize_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.WRITE_SECURE_SETTINGS, getCallingPid(), getCallingUid());
        }

        protected void setForcedDisplayDensityForUser_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.WRITE_SECURE_SETTINGS, getCallingPid(), getCallingUid());
        }

        protected void clearForcedDisplayDensityForUser_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.WRITE_SECURE_SETTINGS, getCallingPid(), getCallingUid());
        }

        protected void setForcedDisplayScalingMode_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.WRITE_SECURE_SETTINGS, getCallingPid(), getCallingUid());
        }

        protected void addShellRoot_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.MANAGE_APP_TOKENS, getCallingPid(), getCallingUid());
        }

        protected void setShellRootAccessibilityWindow_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.MANAGE_APP_TOKENS, getCallingPid(), getCallingUid());
        }

        protected void exitKeyguardSecurely_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.DISABLE_KEYGUARD, getCallingPid(), getCallingUid());
        }

        protected void setNavBarVirtualKeyHapticFeedbackEnabled_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.STATUS_BAR, getCallingPid(), getCallingUid());
        }

        protected void getCurrentImeTouchRegion_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.RESTRICTED_VR_ACCESS, getCallingPid(), getCallingUid());
        }

        protected void setDisplayWindowInsetsController_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.MANAGE_APP_TOKENS, getCallingPid(), getCallingUid());
        }

        protected void updateDisplayWindowRequestedVisibleTypes_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.MANAGE_APP_TOKENS, getCallingPid(), getCallingUid());
        }

        protected void registerScreenRecordingCallback_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.DETECT_SCREEN_RECORDING, getCallingPid(), getCallingUid());
        }

        protected void unregisterScreenRecordingCallback_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.DETECT_SCREEN_RECORDING, getCallingPid(), getCallingUid());
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 153;
        }
    }
}
