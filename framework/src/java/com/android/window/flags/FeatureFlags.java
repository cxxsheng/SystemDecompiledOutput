package com.android.window.flags;

/* loaded from: classes5.dex */
public interface FeatureFlags {
    boolean activityEmbeddingInteractiveDividerFlag();

    boolean activityEmbeddingOverlayPresentationFlag();

    boolean activitySnapshotByDefault();

    boolean activityWindowInfoFlag();

    boolean allowDisableActivityRecordInputSink();

    boolean allowHideScmButton();

    boolean allowsScreenSizeDecoupledFromStatusBarAndCutout();

    boolean alwaysUpdateWallpaperPermission();

    boolean appCompatPropertiesApi();

    boolean appCompatRefactoring();

    boolean balDontBringExistingBackgroundTaskStackToFg();

    boolean balImproveRealCallerVisibilityCheck();

    boolean balRequireOptInByPendingIntentCreator();

    boolean balRequireOptInSameUid();

    boolean balRespectAppSwitchStateWhenCheckBoundByForegroundUid();

    boolean balShowToasts();

    boolean balShowToastsBlocked();

    boolean bundleClientTransactionFlag();

    boolean cameraCompatForFreeform();

    boolean closeToSquareConfigIncludesStatusBar();

    boolean configurableFontScaleDefault();

    boolean coverDisplayOptIn();

    boolean deferDisplayUpdates();

    boolean delegateUnhandledDrags();

    boolean deleteCaptureDisplay();

    boolean density390Api();

    boolean doNotCheckIntersectionWhenNonMagnifiableWindowTransitions();

    boolean edgeToEdgeByDefault();

    boolean embeddedActivityBackNavFlag();

    boolean enableBufferTransformHintFromDisplay();

    boolean enableDesktopWindowingMode();

    boolean enableScaledResizing();

    boolean enforceEdgeToEdge();

    boolean explicitRefreshRateHints();

    boolean fullscreenDimFlag();

    boolean insetsDecoupledConfiguration();

    boolean introduceSmootherDimmer();

    boolean letterboxBackgroundWallpaper();

    boolean magnificationAlwaysDrawFullscreenBorder();

    boolean movableCutoutConfiguration();

    boolean multiCrop();

    boolean navBarTransparentByDefault();

    boolean noConsecutiveVisibilityEvents();

    boolean predictiveBackSystemAnims();

    boolean screenRecordingCallbacks();

    boolean sdkDesiredPresentTime();

    boolean secureWindowState();

    boolean supportsMultiInstanceSystemUi();

    boolean surfaceControlInputReceiver();

    boolean surfaceTrustedOverlay();

    boolean syncScreenCapture();

    boolean taskFragmentSystemOrganizerFlag();

    boolean transitReadyTracking();

    boolean trustedPresentationListenerForWindow();

    boolean untrustedEmbeddingAnyAppPermission();

    boolean untrustedEmbeddingStateSharing();

    boolean userMinAspectRatioAppDefault();

    boolean wallpaperOffsetAsync();
}
