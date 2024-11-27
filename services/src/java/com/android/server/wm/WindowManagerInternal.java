package com.android.server.wm;

/* loaded from: classes3.dex */
public abstract class WindowManagerInternal {

    public interface AccessibilityControllerInternal {

        public interface UiChangesForAccessibilityCallbacks {
            void onRectangleOnScreenRequested(int i, int i2, int i3, int i4, int i5);
        }

        boolean isAccessibilityTracingEnabled();

        void logTrace(java.lang.String str, long j, java.lang.String str2, byte[] bArr, int i, java.lang.StackTraceElement[] stackTraceElementArr, long j2, int i2, long j3, java.util.Set<java.lang.String> set);

        void logTrace(java.lang.String str, long j, java.lang.String str2, byte[] bArr, int i, java.lang.StackTraceElement[] stackTraceElementArr, java.util.Set<java.lang.String> set);

        void setUiChangesForAccessibilityCallbacks(com.android.server.wm.WindowManagerInternal.AccessibilityControllerInternal.UiChangesForAccessibilityCallbacks uiChangesForAccessibilityCallbacks);

        void startTrace(long j);

        void stopTrace();
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface ImeClientFocusResult {
        public static final int DISPLAY_ID_MISMATCH = -2;
        public static final int HAS_IME_FOCUS = 0;
        public static final int INVALID_DISPLAY_ID = -3;
        public static final int NOT_IME_TARGET_WINDOW = -1;
    }

    public interface KeyguardExitAnimationStartListener {
        void onAnimationStart(android.view.RemoteAnimationTarget[] remoteAnimationTargetArr, android.view.RemoteAnimationTarget[] remoteAnimationTargetArr2, android.view.IRemoteAnimationFinishedCallback iRemoteAnimationFinishedCallback);
    }

    public interface MagnificationCallbacks {
        void onDisplaySizeChanged();

        void onImeWindowVisibilityChanged(boolean z);

        void onMagnificationRegionChanged(android.graphics.Region region);

        void onRectangleOnScreenRequested(int i, int i2, int i3, int i4);

        void onUserContextChanged();
    }

    public interface OnHardKeyboardStatusChangeListener {
        void onHardKeyboardStatusChange(boolean z);
    }

    public interface TaskSystemBarsListener {
        void onTransientSystemBarsVisibilityChanged(int i, boolean z, boolean z2);
    }

    public interface WindowsForAccessibilityCallback {
        void onAccessibilityWindowsChanged(boolean z, int i, @android.annotation.NonNull android.os.IBinder iBinder, @android.annotation.NonNull android.graphics.Point point, @android.annotation.NonNull java.util.List<com.android.server.wm.AccessibilityWindowsPopulator.AccessibilityWindow> list);

        void onWindowsForAccessibilityChanged(boolean z, int i, android.os.IBinder iBinder, @android.annotation.NonNull java.util.List<android.view.WindowInfo> list);
    }

    public abstract void addBlockScreenCaptureForApps(@android.annotation.NonNull android.util.ArraySet<com.android.server.wm.SensitiveContentPackages.PackageInfo> arraySet);

    public abstract void addRefreshRateRangeForPackage(@android.annotation.NonNull java.lang.String str, float f, float f2);

    public abstract void addTrustedTaskOverlay(int i, android.view.SurfaceControlViewHost.SurfacePackage surfacePackage);

    public abstract void addWindowToken(@android.annotation.NonNull android.os.IBinder iBinder, int i, int i2, @android.annotation.Nullable android.os.Bundle bundle);

    public abstract void captureDisplay(int i, @android.annotation.Nullable android.window.ScreenCapture.CaptureArgs captureArgs, android.window.ScreenCapture.ScreenCaptureListener screenCaptureListener);

    public abstract void clearBlockedApps();

    public abstract void clearDisplaySettings(@android.annotation.NonNull java.lang.String str, int i);

    public abstract void clearForcedDisplaySize(int i);

    public abstract void clearSnapshotCache();

    public abstract void computeWindowsForAccessibility(int i);

    public abstract android.view.SurfaceControl getA11yOverlayLayer(int i);

    public abstract com.android.server.wm.WindowManagerInternal.AccessibilityControllerInternal getAccessibilityController();

    public abstract int getDisplayIdForWindow(android.os.IBinder iBinder);

    public abstract int getDisplayImePolicy(int i);

    public abstract android.os.IBinder getFocusedWindowToken();

    public abstract android.os.IBinder getFocusedWindowTokenFromWindowStates();

    public abstract android.view.SurfaceControl getHandwritingSurfaceForDisplay(int i);

    public abstract int getInputMethodWindowVisibleHeight(int i);

    @android.annotation.Nullable
    public abstract com.android.internal.policy.KeyInterceptionInfo getKeyInterceptionInfoFromToken(android.os.IBinder iBinder);

    public abstract void getMagnificationRegion(int i, @android.annotation.NonNull android.graphics.Region region);

    @android.annotation.Nullable
    public abstract android.os.IBinder getTargetWindowTokenFromInputToken(android.os.IBinder iBinder);

    public abstract int getTopFocusedDisplayId();

    public abstract android.content.Context getTopFocusedDisplayUiContext();

    public abstract void getWindowFrame(android.os.IBinder iBinder, android.graphics.Rect rect);

    public abstract java.lang.String getWindowName(@android.annotation.NonNull android.os.IBinder iBinder);

    public abstract int getWindowOwnerUserId(android.os.IBinder iBinder);

    public abstract android.util.Pair<android.graphics.Matrix, android.view.MagnificationSpec> getWindowTransformationMatrixAndMagnificationSpec(android.os.IBinder iBinder);

    public abstract int hasInputMethodClientFocus(android.os.IBinder iBinder, int i, int i2, int i3);

    public abstract boolean hasNavigationBar(int i);

    public abstract void hideIme(android.os.IBinder iBinder, int i, @android.annotation.NonNull android.view.inputmethod.ImeTracker.Token token);

    public abstract boolean isHardKeyboardAvailable();

    public abstract boolean isHomeSupportedOnDisplay(int i);

    public abstract boolean isKeyguardLocked();

    public abstract boolean isKeyguardSecure(int i);

    public abstract boolean isKeyguardShowingAndNotOccluded();

    public abstract boolean isPointInsideWindow(@android.annotation.NonNull android.os.IBinder iBinder, int i, float f, float f2);

    public abstract boolean isTouchOrFaketouchDevice();

    public abstract boolean isUidAllowedOnDisplay(int i, int i2);

    public abstract boolean isUidFocused(int i);

    public abstract void lockNow();

    public abstract void moveDisplayToTopIfAllowed(int i);

    public abstract boolean moveFocusToTopEmbeddedWindowIfNeeded();

    public abstract void moveWindowTokenToDisplay(android.os.IBinder iBinder, int i);

    public abstract void onDisplayManagerReceivedDeviceState(int i);

    public abstract com.android.server.wm.WindowManagerInternal.ImeTargetInfo onToggleImeRequested(boolean z, @android.annotation.NonNull android.os.IBinder iBinder, @android.annotation.NonNull android.os.IBinder iBinder2, int i);

    public abstract void registerAppTransitionListener(com.android.server.wm.WindowManagerInternal.AppTransitionListener appTransitionListener);

    public abstract void registerDragDropControllerCallback(com.android.server.wm.WindowManagerInternal.IDragDropCallback iDragDropCallback);

    public abstract void registerKeyguardExitAnimationStartListener(com.android.server.wm.WindowManagerInternal.KeyguardExitAnimationStartListener keyguardExitAnimationStartListener);

    public abstract void registerTaskSystemBarsListener(com.android.server.wm.WindowManagerInternal.TaskSystemBarsListener taskSystemBarsListener);

    public abstract void removeBlockScreenCaptureForApps(@android.annotation.NonNull android.util.ArraySet<com.android.server.wm.SensitiveContentPackages.PackageInfo> arraySet);

    public abstract void removeRefreshRateRangeForPackage(@android.annotation.NonNull java.lang.String str);

    public abstract void removeTrustedTaskOverlay(int i, android.view.SurfaceControlViewHost.SurfacePackage surfacePackage);

    public abstract void removeWindowToken(android.os.IBinder iBinder, boolean z, boolean z2, int i);

    public abstract void reportPasswordChanged(int i);

    public abstract void requestTraversalFromDisplayManager();

    public abstract void requestWindowFocus(android.os.IBinder iBinder);

    public abstract void setAccessibilityIdToSurfaceMetadata(android.os.IBinder iBinder, int i);

    public abstract boolean setContentRecordingSession(android.view.ContentRecordingSession contentRecordingSession);

    public abstract void setDismissImeOnBackKeyPressed(boolean z);

    public abstract void setForcedDisplaySize(int i, int i2, int i3);

    public abstract void setFullscreenMagnificationActivated(int i, boolean z);

    public abstract void setHomeSupportedOnDisplay(@android.annotation.NonNull java.lang.String str, int i, boolean z);

    public abstract void setInputFilter(android.view.IInputFilter iInputFilter);

    public abstract void setInputMethodTargetChangeListener(@android.annotation.NonNull com.android.server.wm.ImeTargetChangeListener imeTargetChangeListener);

    public abstract boolean setMagnificationCallbacks(int i, @android.annotation.Nullable com.android.server.wm.WindowManagerInternal.MagnificationCallbacks magnificationCallbacks);

    public abstract void setMagnificationSpec(int i, android.view.MagnificationSpec magnificationSpec);

    public abstract void setOnHardKeyboardStatusChangeListener(com.android.server.wm.WindowManagerInternal.OnHardKeyboardStatusChangeListener onHardKeyboardStatusChangeListener);

    public abstract void setOrientationRequestPolicy(boolean z, int[] iArr, int[] iArr2);

    public abstract void setVr2dDisplayId(int i);

    public abstract void setWallpaperCropHints(android.os.IBinder iBinder, android.util.SparseArray<android.graphics.Rect> sparseArray);

    public abstract void setWallpaperCropUtils(com.android.server.wallpaper.WallpaperCropper.WallpaperCropUtils wallpaperCropUtils);

    public abstract void setWallpaperShowWhenLocked(android.os.IBinder iBinder, boolean z);

    public abstract void setWindowsForAccessibilityCallback(int i, com.android.server.wm.WindowManagerInternal.WindowsForAccessibilityCallback windowsForAccessibilityCallback);

    public abstract boolean shouldRestoreImeVisibility(android.os.IBinder iBinder);

    public abstract void showGlobalActions();

    public abstract void showImePostLayout(android.os.IBinder iBinder, @android.annotation.NonNull android.view.inputmethod.ImeTracker.Token token);

    public abstract android.window.ScreenCapture.ScreenshotHardwareBuffer takeAssistScreenshot();

    public abstract void unregisterTaskSystemBarsListener(com.android.server.wm.WindowManagerInternal.TaskSystemBarsListener taskSystemBarsListener);

    public abstract void updateInputMethodTargetWindow(@android.annotation.NonNull android.os.IBinder iBinder, @android.annotation.NonNull android.os.IBinder iBinder2);

    public abstract void waitForAllWindowsDrawn(android.os.Message message, long j, int i);

    public static abstract class AppTransitionListener {
        public void onAppTransitionPendingLocked() {
        }

        public void onAppTransitionCancelledLocked(boolean z) {
        }

        public void onAppTransitionTimeoutLocked() {
        }

        public int onAppTransitionStartingLocked(long j, long j2) {
            return 0;
        }

        public void onAppTransitionFinishedLocked(android.os.IBinder iBinder) {
        }
    }

    public interface IDragDropCallback {
        default java.util.concurrent.CompletableFuture<java.lang.Boolean> registerInputChannel(final com.android.server.wm.DragState dragState, android.view.Display display, final com.android.server.input.InputManagerService inputManagerService, final android.view.InputChannel inputChannel) {
            return dragState.register(display).thenApply(new java.util.function.Function() { // from class: com.android.server.wm.WindowManagerInternal$IDragDropCallback$$ExternalSyntheticLambda0
                @Override // java.util.function.Function
                public final java.lang.Object apply(java.lang.Object obj) {
                    java.lang.Boolean lambda$registerInputChannel$0;
                    lambda$registerInputChannel$0 = com.android.server.wm.WindowManagerInternal.IDragDropCallback.lambda$registerInputChannel$0(com.android.server.input.InputManagerService.this, inputChannel, dragState, (java.lang.Void) obj);
                    return lambda$registerInputChannel$0;
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        static /* synthetic */ java.lang.Boolean lambda$registerInputChannel$0(com.android.server.input.InputManagerService inputManagerService, android.view.InputChannel inputChannel, com.android.server.wm.DragState dragState, java.lang.Void r3) {
            return java.lang.Boolean.valueOf(inputManagerService.startDragAndDrop(inputChannel, dragState.getInputChannel()));
        }

        default boolean prePerformDrag(android.view.IWindow iWindow, android.os.IBinder iBinder, int i, float f, float f2, float f3, float f4, android.content.ClipData clipData) {
            return true;
        }

        default void postPerformDrag() {
        }

        default void preReportDropResult(android.view.IWindow iWindow, boolean z) {
        }

        default void postReportDropResult() {
        }

        default void preCancelDragAndDrop(android.os.IBinder iBinder) {
        }

        default void postCancelDragAndDrop() {
        }

        default void dragRecipientEntered(android.view.IWindow iWindow) {
        }

        default void dragRecipientExited(android.view.IWindow iWindow) {
        }
    }

    public final void removeWindowToken(android.os.IBinder iBinder, boolean z, int i) {
        removeWindowToken(iBinder, z, true, i);
    }

    public static class ImeTargetInfo {
        public final java.lang.String focusedWindowName;
        public final java.lang.String imeControlTargetName;
        public final java.lang.String imeLayerTargetName;
        public final java.lang.String imeSurfaceParentName;
        public final java.lang.String requestWindowName;

        public ImeTargetInfo(java.lang.String str, java.lang.String str2, java.lang.String str3, java.lang.String str4, java.lang.String str5) {
            this.focusedWindowName = str;
            this.requestWindowName = str2;
            this.imeControlTargetName = str3;
            this.imeLayerTargetName = str4;
            this.imeSurfaceParentName = str5;
        }
    }
}
