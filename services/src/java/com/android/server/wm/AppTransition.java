package com.android.server.wm;

/* loaded from: classes3.dex */
public class AppTransition implements com.android.internal.util.DumpUtils.Dump {
    private static final int APP_STATE_IDLE = 0;
    private static final int APP_STATE_READY = 1;
    private static final int APP_STATE_RUNNING = 2;
    private static final int APP_STATE_TIMEOUT = 3;
    private static final long APP_TRANSITION_TIMEOUT_MS = 5000;
    static final int DEFAULT_APP_TRANSITION_DURATION = 336;
    static final int MAX_APP_TRANSITION_DURATION = 3000;
    private static final int NEXT_TRANSIT_TYPE_CLIP_REVEAL = 8;
    private static final int NEXT_TRANSIT_TYPE_CUSTOM = 1;
    private static final int NEXT_TRANSIT_TYPE_CUSTOM_IN_PLACE = 7;
    private static final int NEXT_TRANSIT_TYPE_NONE = 0;
    private static final int NEXT_TRANSIT_TYPE_OPEN_CROSS_PROFILE_APPS = 9;
    private static final int NEXT_TRANSIT_TYPE_REMOTE = 10;
    private static final int NEXT_TRANSIT_TYPE_SCALE_UP = 2;
    private static final int NEXT_TRANSIT_TYPE_THUMBNAIL_ASPECT_SCALE_DOWN = 6;
    private static final int NEXT_TRANSIT_TYPE_THUMBNAIL_ASPECT_SCALE_UP = 5;
    private static final int NEXT_TRANSIT_TYPE_THUMBNAIL_SCALE_DOWN = 4;
    private static final int NEXT_TRANSIT_TYPE_THUMBNAIL_SCALE_UP = 3;
    private static final java.lang.String TAG = "WindowManager";
    private static final java.util.ArrayList<android.util.Pair<java.lang.Integer, java.lang.String>> sFlagToString = new java.util.ArrayList<>();
    private android.os.IRemoteCallback mAnimationFinishedCallback;
    private final android.content.Context mContext;
    private android.view.AppTransitionAnimationSpec mDefaultNextAppTransitionAnimationSpec;
    private final int mDefaultWindowAnimationStyleResId;
    private final com.android.server.wm.DisplayContent mDisplayContent;
    final android.os.Handler mHandler;
    private com.android.server.wm.WindowManagerInternal.KeyguardExitAnimationStartListener mKeyguardExitAnimationStartListener;
    private java.lang.String mLastChangingApp;
    private java.lang.String mLastClosingApp;
    private java.lang.String mLastOpeningApp;
    private android.view.IAppTransitionAnimationSpecsFuture mNextAppTransitionAnimationsSpecsFuture;
    private boolean mNextAppTransitionAnimationsSpecsPending;
    private int mNextAppTransitionBackgroundColor;
    private android.os.IRemoteCallback mNextAppTransitionCallback;
    private int mNextAppTransitionEnter;
    private int mNextAppTransitionExit;
    private android.os.IRemoteCallback mNextAppTransitionFutureCallback;
    private int mNextAppTransitionInPlace;
    private boolean mNextAppTransitionIsSync;
    private boolean mNextAppTransitionOverrideRequested;
    private java.lang.String mNextAppTransitionPackage;
    private boolean mNextAppTransitionScaleUp;
    private boolean mOverrideTaskTransition;
    private com.android.server.wm.RemoteAnimationController mRemoteAnimationController;
    private final com.android.server.wm.WindowManagerService mService;

    @com.android.internal.annotations.VisibleForTesting
    final com.android.internal.policy.TransitionAnimation mTransitionAnimation;
    private int mNextAppTransitionFlags = 0;
    private final java.util.ArrayList<java.lang.Integer> mNextAppTransitionRequests = new java.util.ArrayList<>();
    private int mLastUsedAppTransition = -1;
    private int mNextAppTransitionType = 0;
    private final android.util.SparseArray<android.view.AppTransitionAnimationSpec> mNextAppTransitionAnimationsSpecs = new android.util.SparseArray<>();
    private final android.graphics.Rect mTmpRect = new android.graphics.Rect();
    private int mAppTransitionState = 0;
    private final java.util.ArrayList<com.android.server.wm.WindowManagerInternal.AppTransitionListener> mListeners = new java.util.ArrayList<>();
    private final java.util.concurrent.ExecutorService mDefaultExecutor = java.util.concurrent.Executors.newSingleThreadExecutor();
    final java.lang.Runnable mHandleAppTransitionTimeoutRunnable = new java.lang.Runnable() { // from class: com.android.server.wm.AppTransition$$ExternalSyntheticLambda2
        @Override // java.lang.Runnable
        public final void run() {
            com.android.server.wm.AppTransition.this.lambda$new$0();
        }
    };
    private final boolean mGridLayoutRecentsEnabled = android.os.SystemProperties.getBoolean("ro.recents.grid", false);

    AppTransition(android.content.Context context, com.android.server.wm.WindowManagerService windowManagerService, com.android.server.wm.DisplayContent displayContent) {
        this.mContext = context;
        this.mService = windowManagerService;
        this.mHandler = new android.os.Handler(windowManagerService.mH.getLooper());
        this.mDisplayContent = displayContent;
        this.mTransitionAnimation = new com.android.internal.policy.TransitionAnimation(context, com.android.internal.protolog.ProtoLogImpl_1545807451.isEnabled(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_ANIM), TAG);
        android.content.res.TypedArray obtainStyledAttributes = this.mContext.getTheme().obtainStyledAttributes(com.android.internal.R.styleable.Window);
        this.mDefaultWindowAnimationStyleResId = obtainStyledAttributes.getResourceId(8, 0);
        obtainStyledAttributes.recycle();
    }

    boolean isTransitionSet() {
        return !this.mNextAppTransitionRequests.isEmpty();
    }

    boolean isUnoccluding() {
        return this.mNextAppTransitionRequests.contains(9);
    }

    boolean transferFrom(com.android.server.wm.AppTransition appTransition) {
        this.mNextAppTransitionRequests.addAll(appTransition.mNextAppTransitionRequests);
        return prepare();
    }

    void setLastAppTransition(int i, com.android.server.wm.ActivityRecord activityRecord, com.android.server.wm.ActivityRecord activityRecord2, com.android.server.wm.ActivityRecord activityRecord3) {
        this.mLastUsedAppTransition = i;
        this.mLastOpeningApp = "" + activityRecord;
        this.mLastClosingApp = "" + activityRecord2;
        this.mLastChangingApp = "" + activityRecord3;
    }

    boolean isReady() {
        return this.mAppTransitionState == 1 || this.mAppTransitionState == 3;
    }

    void setReady() {
        setAppTransitionState(1);
        fetchAppTransitionSpecsFromFuture();
    }

    boolean isRunning() {
        return this.mAppTransitionState == 2;
    }

    void setIdle() {
        setAppTransitionState(0);
    }

    boolean isIdle() {
        return this.mAppTransitionState == 0;
    }

    boolean isTimeout() {
        return this.mAppTransitionState == 3;
    }

    void setTimeout() {
        setAppTransitionState(3);
    }

    @android.annotation.Nullable
    android.view.animation.Animation getNextAppRequestedAnimation(boolean z) {
        android.view.animation.Animation loadAppTransitionAnimation = this.mTransitionAnimation.loadAppTransitionAnimation(this.mNextAppTransitionPackage, z ? this.mNextAppTransitionEnter : this.mNextAppTransitionExit);
        if (this.mNextAppTransitionBackgroundColor != 0 && loadAppTransitionAnimation != null) {
            loadAppTransitionAnimation.setBackdropColor(this.mNextAppTransitionBackgroundColor);
        }
        return loadAppTransitionAnimation;
    }

    int getNextAppTransitionBackgroundColor() {
        return this.mNextAppTransitionBackgroundColor;
    }

    @com.android.internal.annotations.VisibleForTesting
    boolean isNextAppTransitionOverrideRequested() {
        return this.mNextAppTransitionOverrideRequested;
    }

    android.hardware.HardwareBuffer getAppTransitionThumbnailHeader(com.android.server.wm.WindowContainer windowContainer) {
        android.view.AppTransitionAnimationSpec appTransitionAnimationSpec = this.mNextAppTransitionAnimationsSpecs.get(windowContainer.hashCode());
        if (appTransitionAnimationSpec == null) {
            appTransitionAnimationSpec = this.mDefaultNextAppTransitionAnimationSpec;
        }
        if (appTransitionAnimationSpec != null) {
            return appTransitionAnimationSpec.buffer;
        }
        return null;
    }

    boolean isNextThumbnailTransitionAspectScaled() {
        return this.mNextAppTransitionType == 5 || this.mNextAppTransitionType == 6;
    }

    boolean isNextThumbnailTransitionScaleUp() {
        return this.mNextAppTransitionScaleUp;
    }

    boolean isNextAppTransitionThumbnailUp() {
        return this.mNextAppTransitionType == 3 || this.mNextAppTransitionType == 5;
    }

    boolean isNextAppTransitionThumbnailDown() {
        return this.mNextAppTransitionType == 4 || this.mNextAppTransitionType == 6;
    }

    boolean isNextAppTransitionOpenCrossProfileApps() {
        return this.mNextAppTransitionType == 9;
    }

    boolean isFetchingAppTransitionsSpecs() {
        return this.mNextAppTransitionAnimationsSpecsPending;
    }

    private boolean prepare() {
        if (isRunning()) {
            return false;
        }
        setAppTransitionState(0);
        notifyAppTransitionPendingLocked();
        return true;
    }

    int goodToGo(int i, com.android.server.wm.ActivityRecord activityRecord) {
        long uptimeMillis;
        this.mNextAppTransitionFlags = 0;
        this.mNextAppTransitionRequests.clear();
        setAppTransitionState(2);
        com.android.server.wm.WindowContainer animatingContainer = activityRecord != null ? activityRecord.getAnimatingContainer() : null;
        com.android.server.wm.AnimationAdapter animation = animatingContainer != null ? animatingContainer.getAnimation() : null;
        if (animation != null) {
            uptimeMillis = animation.getStatusBarTransitionsStartTime();
        } else {
            uptimeMillis = android.os.SystemClock.uptimeMillis();
        }
        int notifyAppTransitionStartingLocked = notifyAppTransitionStartingLocked(uptimeMillis, 120L);
        if (this.mRemoteAnimationController != null) {
            this.mRemoteAnimationController.goodToGo(i);
        } else if ((isTaskOpenTransitOld(i) || i == 12) && animation != null && this.mDisplayContent.getDisplayPolicy().shouldAttachNavBarToAppDuringTransition() && this.mService.getRecentsAnimationController() == null) {
            new com.android.server.wm.NavBarFadeAnimationController(this.mDisplayContent).fadeOutAndInSequentially(animation.getDurationHint(), null, activityRecord.getSurfaceControl());
        }
        return notifyAppTransitionStartingLocked;
    }

    void clear() {
        clear(true);
    }

    private void clear(boolean z) {
        this.mNextAppTransitionType = 0;
        this.mNextAppTransitionOverrideRequested = false;
        this.mNextAppTransitionAnimationsSpecs.clear();
        this.mRemoteAnimationController = null;
        this.mNextAppTransitionAnimationsSpecsFuture = null;
        this.mDefaultNextAppTransitionAnimationSpec = null;
        this.mAnimationFinishedCallback = null;
        this.mOverrideTaskTransition = false;
        this.mNextAppTransitionIsSync = false;
        if (z) {
            this.mNextAppTransitionPackage = null;
            this.mNextAppTransitionEnter = 0;
            this.mNextAppTransitionExit = 0;
            this.mNextAppTransitionBackgroundColor = 0;
        }
    }

    void freeze() {
        boolean contains = this.mNextAppTransitionRequests.contains(7);
        if (this.mRemoteAnimationController != null) {
            this.mRemoteAnimationController.cancelAnimation("freeze");
        }
        this.mNextAppTransitionRequests.clear();
        clear();
        setReady();
        notifyAppTransitionCancelledLocked(contains);
    }

    private void setAppTransitionState(int i) {
        this.mAppTransitionState = i;
        updateBooster();
    }

    void updateBooster() {
        com.android.server.wm.WindowManagerService.sThreadPriorityBooster.setAppTransitionRunning(needsBoosting());
    }

    private boolean needsBoosting() {
        return !this.mNextAppTransitionRequests.isEmpty() || this.mAppTransitionState == 1 || this.mAppTransitionState == 2 || (this.mService.getRecentsAnimationController() != null);
    }

    void registerListenerLocked(com.android.server.wm.WindowManagerInternal.AppTransitionListener appTransitionListener) {
        this.mListeners.add(appTransitionListener);
    }

    void unregisterListener(com.android.server.wm.WindowManagerInternal.AppTransitionListener appTransitionListener) {
        this.mListeners.remove(appTransitionListener);
    }

    void registerKeygaurdExitAnimationStartListener(com.android.server.wm.WindowManagerInternal.KeyguardExitAnimationStartListener keyguardExitAnimationStartListener) {
        this.mKeyguardExitAnimationStartListener = keyguardExitAnimationStartListener;
    }

    public void notifyAppTransitionFinishedLocked(android.os.IBinder iBinder) {
        for (int i = 0; i < this.mListeners.size(); i++) {
            this.mListeners.get(i).onAppTransitionFinishedLocked(iBinder);
        }
    }

    private void notifyAppTransitionPendingLocked() {
        for (int i = 0; i < this.mListeners.size(); i++) {
            this.mListeners.get(i).onAppTransitionPendingLocked();
        }
    }

    private void notifyAppTransitionCancelledLocked(boolean z) {
        for (int i = 0; i < this.mListeners.size(); i++) {
            this.mListeners.get(i).onAppTransitionCancelledLocked(z);
        }
    }

    private void notifyAppTransitionTimeoutLocked() {
        for (int i = 0; i < this.mListeners.size(); i++) {
            this.mListeners.get(i).onAppTransitionTimeoutLocked();
        }
    }

    private int notifyAppTransitionStartingLocked(long j, long j2) {
        int i = 0;
        for (int i2 = 0; i2 < this.mListeners.size(); i2++) {
            i |= this.mListeners.get(i2).onAppTransitionStartingLocked(j, j2);
        }
        return i;
    }

    @com.android.internal.annotations.VisibleForTesting
    int getDefaultWindowAnimationStyleResId() {
        return this.mDefaultWindowAnimationStyleResId;
    }

    @com.android.internal.annotations.VisibleForTesting
    int getAnimationStyleResId(@android.annotation.NonNull android.view.WindowManager.LayoutParams layoutParams) {
        return this.mTransitionAnimation.getAnimationStyleResId(layoutParams);
    }

    @com.android.internal.annotations.VisibleForTesting
    @android.annotation.Nullable
    android.view.animation.Animation loadAnimationSafely(android.content.Context context, int i) {
        return com.android.internal.policy.TransitionAnimation.loadAnimationSafely(context, i, TAG);
    }

    private static int mapOpenCloseTransitTypes(int i, boolean z) {
        int i2 = 5;
        switch (i) {
            case 6:
            case 24:
                if (z) {
                    i2 = 4;
                }
                return i2;
            case 7:
            case 25:
                return z ? 6 : 7;
            case 8:
                if (z) {
                    return 8;
                }
                return 9;
            case 9:
                if (z) {
                    return 10;
                }
                return 11;
            case 10:
                if (z) {
                    return 12;
                }
                return 13;
            case 11:
                if (z) {
                    return 14;
                }
                return 15;
            case 12:
                if (z) {
                    return 18;
                }
                return 19;
            case 13:
                if (z) {
                    return 16;
                }
                return 17;
            case 14:
                if (z) {
                    return 20;
                }
                return 21;
            case 15:
                if (z) {
                    return 22;
                }
                return 23;
            case 16:
                if (z) {
                    return 25;
                }
                return 24;
            case 17:
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
            case 26:
            case 27:
            case 30:
            default:
                return 0;
            case 28:
                if (z) {
                    i2 = 4;
                }
                return i2;
            case 29:
                return z ? 6 : 7;
            case 31:
                if (z) {
                    return 28;
                }
                return 29;
            case 32:
                return z ? 0 : 27;
        }
    }

    @android.annotation.Nullable
    android.view.animation.Animation loadAnimationAttr(android.view.WindowManager.LayoutParams layoutParams, int i, int i2) {
        return this.mTransitionAnimation.loadAnimationAttr(layoutParams, i, i2);
    }

    private void getDefaultNextAppTransitionStartRect(android.graphics.Rect rect) {
        if (this.mDefaultNextAppTransitionAnimationSpec == null || this.mDefaultNextAppTransitionAnimationSpec.rect == null) {
            android.util.Slog.e(TAG, "Starting rect for app requested, but none available", new java.lang.Throwable());
            rect.setEmpty();
        } else {
            rect.set(this.mDefaultNextAppTransitionAnimationSpec.rect);
        }
    }

    private void putDefaultNextAppTransitionCoordinates(int i, int i2, int i3, int i4, android.hardware.HardwareBuffer hardwareBuffer) {
        this.mDefaultNextAppTransitionAnimationSpec = new android.view.AppTransitionAnimationSpec(-1, hardwareBuffer, new android.graphics.Rect(i, i2, i3 + i, i4 + i2));
    }

    android.hardware.HardwareBuffer createCrossProfileAppsThumbnail(android.graphics.drawable.Drawable drawable, android.graphics.Rect rect) {
        return this.mTransitionAnimation.createCrossProfileAppsThumbnail(drawable, rect);
    }

    android.view.animation.Animation createCrossProfileAppsThumbnailAnimationLocked(android.graphics.Rect rect) {
        return this.mTransitionAnimation.createCrossProfileAppsThumbnailAnimationLocked(rect);
    }

    android.view.animation.Animation createThumbnailAspectScaleAnimationLocked(android.graphics.Rect rect, @android.annotation.Nullable android.graphics.Rect rect2, android.hardware.HardwareBuffer hardwareBuffer, com.android.server.wm.WindowContainer windowContainer, int i) {
        android.graphics.Rect rect3;
        android.view.AppTransitionAnimationSpec appTransitionAnimationSpec = this.mNextAppTransitionAnimationsSpecs.get(windowContainer.hashCode());
        com.android.internal.policy.TransitionAnimation transitionAnimation = this.mTransitionAnimation;
        android.graphics.Rect rect4 = appTransitionAnimationSpec != null ? appTransitionAnimationSpec.rect : null;
        if (this.mDefaultNextAppTransitionAnimationSpec == null) {
            rect3 = null;
        } else {
            rect3 = this.mDefaultNextAppTransitionAnimationSpec.rect;
        }
        return transitionAnimation.createThumbnailAspectScaleAnimationLocked(rect, rect2, hardwareBuffer, i, rect4, rect3, this.mNextAppTransitionScaleUp);
    }

    private android.view.animation.AnimationSet createAspectScaledThumbnailFreeformAnimationLocked(android.graphics.Rect rect, android.graphics.Rect rect2, @android.annotation.Nullable android.graphics.Rect rect3, boolean z) {
        android.view.animation.ScaleAnimation scaleAnimation;
        android.view.animation.TranslateAnimation translateAnimation;
        float width = rect.width();
        float height = rect.height();
        float width2 = rect2.width();
        float height2 = rect2.height();
        float f = z ? width / width2 : width2 / width;
        float f2 = z ? height / height2 : height2 / height;
        android.view.animation.AnimationSet animationSet = new android.view.animation.AnimationSet(true);
        int i = rect3 == null ? 0 : rect3.left + rect3.right;
        int i2 = rect3 != null ? rect3.top + rect3.bottom : 0;
        if (z) {
            width = width2;
        }
        float f3 = (width + i) / 2.0f;
        if (z) {
            height = height2;
        }
        float f4 = (height + i2) / 2.0f;
        if (z) {
            scaleAnimation = new android.view.animation.ScaleAnimation(f, 1.0f, f2, 1.0f, f3, f4);
        } else {
            scaleAnimation = new android.view.animation.ScaleAnimation(1.0f, f, 1.0f, f2, f3, f4);
        }
        int width3 = rect.left + (rect.width() / 2);
        int height3 = rect.top + (rect.height() / 2);
        int width4 = rect2.left + (rect2.width() / 2);
        int height4 = rect2.top + (rect2.height() / 2);
        int i3 = z ? width3 - width4 : width4 - width3;
        int i4 = z ? height3 - height4 : height4 - height3;
        if (z) {
            translateAnimation = new android.view.animation.TranslateAnimation(i3, com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE, i4, com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE);
        } else {
            translateAnimation = new android.view.animation.TranslateAnimation(com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE, i3, com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE, i4);
        }
        animationSet.addAnimation(scaleAnimation);
        animationSet.addAnimation(translateAnimation);
        setAppTransitionFinishedCallbackIfNeeded(animationSet);
        return animationSet;
    }

    boolean canSkipFirstFrame() {
        return (this.mNextAppTransitionType == 1 || this.mNextAppTransitionOverrideRequested || this.mNextAppTransitionType == 7 || this.mNextAppTransitionType == 8 || this.mNextAppTransitionRequests.contains(7)) ? false : true;
    }

    com.android.server.wm.RemoteAnimationController getRemoteAnimationController() {
        return this.mRemoteAnimationController;
    }

    @android.annotation.Nullable
    android.view.animation.Animation loadAnimation(android.view.WindowManager.LayoutParams layoutParams, int i, boolean z, int i2, int i3, android.graphics.Rect rect, android.graphics.Rect rect2, android.graphics.Rect rect3, @android.annotation.Nullable android.graphics.Rect rect4, @android.annotation.Nullable android.graphics.Rect rect5, boolean z2, boolean z3, com.android.server.wm.WindowContainer windowContainer) {
        android.view.animation.Animation animation;
        boolean canCustomizeAppTransition = windowContainer.canCustomizeAppTransition();
        if (this.mNextAppTransitionOverrideRequested) {
            if (canCustomizeAppTransition || this.mOverrideTaskTransition) {
                this.mNextAppTransitionType = 1;
            } else {
                com.android.internal.protolog.ProtoLogImpl_1545807451.e(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_APP_TRANSITIONS_ANIM, -4049608245387511746L, 0, null, null);
            }
        }
        if (isKeyguardGoingAwayTransitOld(i) && z) {
            animation = this.mTransitionAnimation.loadKeyguardExitAnimation(this.mNextAppTransitionFlags, i == 21);
        } else {
            if (i == 22 || i == 33) {
                animation = null;
            } else if (i == 23 && !z) {
                animation = this.mTransitionAnimation.loadKeyguardUnoccludeAnimation();
            } else if (i == 26) {
                animation = null;
            } else if (z2 && (i == 6 || i == 8 || i == 10)) {
                animation = this.mTransitionAnimation.loadVoiceActivityOpenAnimation(z);
                com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_APP_TRANSITIONS_ANIM, -2133100418670643322L, 48, null, java.lang.String.valueOf(animation), java.lang.String.valueOf(appTransitionOldToString(i)), java.lang.Boolean.valueOf(z), java.lang.String.valueOf(android.os.Debug.getCallers(3)));
            } else if (z2 && (i == 7 || i == 9 || i == 11)) {
                animation = this.mTransitionAnimation.loadVoiceActivityExitAnimation(z);
                com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_APP_TRANSITIONS_ANIM, -2133100418670643322L, 48, null, java.lang.String.valueOf(animation), java.lang.String.valueOf(appTransitionOldToString(i)), java.lang.Boolean.valueOf(z), java.lang.String.valueOf(android.os.Debug.getCallers(3)));
            } else if (i != 18) {
                if (this.mNextAppTransitionType == 1) {
                    animation = getNextAppRequestedAnimation(z);
                    com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_APP_TRANSITIONS_ANIM, -8382864384468306610L, 48, null, java.lang.String.valueOf(animation), java.lang.String.valueOf(appTransitionOldToString(i)), java.lang.Boolean.valueOf(z), java.lang.String.valueOf(android.os.Debug.getCallers(3)));
                } else if (this.mNextAppTransitionType == 7) {
                    animation = this.mTransitionAnimation.loadAppTransitionAnimation(this.mNextAppTransitionPackage, this.mNextAppTransitionInPlace);
                    com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_APP_TRANSITIONS_ANIM, 222576013987954454L, 0, null, java.lang.String.valueOf(animation), java.lang.String.valueOf(appTransitionOldToString(i)), java.lang.String.valueOf(android.os.Debug.getCallers(3)));
                } else if (this.mNextAppTransitionType == 8) {
                    animation = this.mTransitionAnimation.createClipRevealAnimationLockedCompat(i, z, rect, rect2, this.mDefaultNextAppTransitionAnimationSpec != null ? this.mDefaultNextAppTransitionAnimationSpec.rect : null);
                    com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_APP_TRANSITIONS_ANIM, 4808089291562562413L, 0, null, java.lang.String.valueOf(animation), java.lang.String.valueOf(appTransitionOldToString(i)), java.lang.String.valueOf(android.os.Debug.getCallers(3)));
                } else if (this.mNextAppTransitionType == 2) {
                    animation = this.mTransitionAnimation.createScaleUpAnimationLockedCompat(i, z, rect, this.mDefaultNextAppTransitionAnimationSpec != null ? this.mDefaultNextAppTransitionAnimationSpec.rect : null);
                    com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_APP_TRANSITIONS_ANIM, -1463563572526433695L, 0, null, java.lang.String.valueOf(animation), java.lang.String.valueOf(appTransitionOldToString(i)), java.lang.String.valueOf(z), java.lang.String.valueOf(android.os.Debug.getCallers(3)));
                } else if (this.mNextAppTransitionType == 3 || this.mNextAppTransitionType == 4) {
                    this.mNextAppTransitionScaleUp = this.mNextAppTransitionType == 3;
                    animation = this.mTransitionAnimation.createThumbnailEnterExitAnimationLockedCompat(z, this.mNextAppTransitionScaleUp, rect, i, getAppTransitionThumbnailHeader(windowContainer), this.mDefaultNextAppTransitionAnimationSpec != null ? this.mDefaultNextAppTransitionAnimationSpec.rect : null);
                    com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_APP_TRANSITIONS_ANIM, -8749850292010208926L, 192, null, java.lang.String.valueOf(animation), java.lang.String.valueOf(this.mNextAppTransitionScaleUp ? "ANIM_THUMBNAIL_SCALE_UP" : "ANIM_THUMBNAIL_SCALE_DOWN"), java.lang.String.valueOf(appTransitionOldToString(i)), java.lang.Boolean.valueOf(z), java.lang.String.valueOf(android.os.Debug.getCallers(3)));
                } else if (this.mNextAppTransitionType == 5 || this.mNextAppTransitionType == 6) {
                    this.mNextAppTransitionScaleUp = this.mNextAppTransitionType == 5;
                    android.view.AppTransitionAnimationSpec appTransitionAnimationSpec = this.mNextAppTransitionAnimationsSpecs.get(windowContainer.hashCode());
                    animation = this.mTransitionAnimation.createAspectScaledThumbnailEnterExitAnimationLocked(z, this.mNextAppTransitionScaleUp, i3, i, rect, rect3, rect4, rect5, z3, appTransitionAnimationSpec != null ? appTransitionAnimationSpec.rect : null, this.mDefaultNextAppTransitionAnimationSpec != null ? this.mDefaultNextAppTransitionAnimationSpec.rect : null);
                    com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_APP_TRANSITIONS_ANIM, -8749850292010208926L, 192, null, java.lang.String.valueOf(animation), java.lang.String.valueOf(this.mNextAppTransitionScaleUp ? "ANIM_THUMBNAIL_ASPECT_SCALE_UP" : "ANIM_THUMBNAIL_ASPECT_SCALE_DOWN"), java.lang.String.valueOf(appTransitionOldToString(i)), java.lang.Boolean.valueOf(z), java.lang.String.valueOf(android.os.Debug.getCallers(3)));
                } else if (this.mNextAppTransitionType == 9 && z) {
                    animation = this.mTransitionAnimation.loadCrossProfileAppEnterAnimation();
                    com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_APP_TRANSITIONS_ANIM, 5939232373291430513L, 0, null, java.lang.String.valueOf(animation), java.lang.String.valueOf(appTransitionOldToString(i)), java.lang.String.valueOf(android.os.Debug.getCallers(3)));
                } else if (isChangeTransitOld(i)) {
                    animation = new android.view.animation.AlphaAnimation(1.0f, 1.0f);
                    animation.setDuration(336L);
                    com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_APP_TRANSITIONS_ANIM, 9082776604722675018L, 48, null, java.lang.String.valueOf(animation), java.lang.String.valueOf(appTransitionOldToString(i)), java.lang.Boolean.valueOf(z), java.lang.String.valueOf(android.os.Debug.getCallers(3)));
                } else {
                    int mapOpenCloseTransitTypes = mapOpenCloseTransitTypes(i, z);
                    if (mapOpenCloseTransitTypes != 0) {
                        com.android.server.wm.ActivityRecord.CustomAppTransition customAppTransition = getCustomAppTransition(mapOpenCloseTransitTypes, windowContainer);
                        if (customAppTransition != null) {
                            animation = loadCustomActivityAnimation(customAppTransition, z, windowContainer);
                        } else if (canCustomizeAppTransition) {
                            animation = loadAnimationAttr(layoutParams, mapOpenCloseTransitTypes, i);
                        } else {
                            animation = this.mTransitionAnimation.loadDefaultAnimationAttr(mapOpenCloseTransitTypes, i);
                        }
                    } else {
                        animation = null;
                    }
                    com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_APP_TRANSITIONS_ANIM, -1218632020771063497L, 964, null, java.lang.String.valueOf(animation), java.lang.Long.valueOf(mapOpenCloseTransitTypes), java.lang.String.valueOf(appTransitionOldToString(i)), java.lang.Boolean.valueOf(z), java.lang.Boolean.valueOf(canCustomizeAppTransition), java.lang.String.valueOf(android.os.Debug.getCallers(3)));
                }
            } else {
                animation = this.mTransitionAnimation.createRelaunchAnimation(rect, rect3, this.mDefaultNextAppTransitionAnimationSpec != null ? this.mDefaultNextAppTransitionAnimationSpec.rect : null);
                com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_APP_TRANSITIONS_ANIM, 6121116119545820299L, 0, null, java.lang.String.valueOf(animation), java.lang.String.valueOf(appTransitionOldToString(i)), java.lang.String.valueOf(android.os.Debug.getCallers(3)));
            }
        }
        setAppTransitionFinishedCallbackIfNeeded(animation);
        return animation;
    }

    com.android.server.wm.ActivityRecord.CustomAppTransition getCustomAppTransition(int i, com.android.server.wm.WindowContainer windowContainer) {
        com.android.server.wm.ActivityRecord asActivityRecord = windowContainer.asActivityRecord();
        if (asActivityRecord == null) {
            return null;
        }
        if ((i == 5 || i == 6) && (asActivityRecord = asActivityRecord.getTask().getActivityAbove(asActivityRecord)) == null) {
            return null;
        }
        switch (i) {
        }
        return null;
    }

    private android.view.animation.Animation loadCustomActivityAnimation(@android.annotation.NonNull com.android.server.wm.ActivityRecord.CustomAppTransition customAppTransition, boolean z, com.android.server.wm.WindowContainer windowContainer) {
        android.view.animation.Animation loadAppTransitionAnimation = this.mTransitionAnimation.loadAppTransitionAnimation(windowContainer.asActivityRecord().packageName, z ? customAppTransition.mEnterAnim : customAppTransition.mExitAnim);
        if (loadAppTransitionAnimation != null && customAppTransition.mBackgroundColor != 0) {
            loadAppTransitionAnimation.setBackdropColor(customAppTransition.mBackgroundColor);
            loadAppTransitionAnimation.setShowBackdrop(true);
        }
        return loadAppTransitionAnimation;
    }

    int getAppRootTaskClipMode() {
        if (this.mNextAppTransitionRequests.contains(5) || this.mNextAppTransitionRequests.contains(7) || this.mNextAppTransitionType == 8) {
            return 1;
        }
        return 0;
    }

    public int getTransitFlags() {
        return this.mNextAppTransitionFlags;
    }

    void postAnimationCallback() {
        if (this.mNextAppTransitionCallback != null) {
            this.mHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new java.util.function.Consumer() { // from class: com.android.server.wm.AppTransition$$ExternalSyntheticLambda3
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    com.android.server.wm.AppTransition.doAnimationCallback((android.os.IRemoteCallback) obj);
                }
            }, this.mNextAppTransitionCallback));
            this.mNextAppTransitionCallback = null;
        }
    }

    void overridePendingAppTransition(java.lang.String str, int i, int i2, int i3, android.os.IRemoteCallback iRemoteCallback, android.os.IRemoteCallback iRemoteCallback2, boolean z) {
        if (canOverridePendingAppTransition()) {
            clear();
            this.mNextAppTransitionOverrideRequested = true;
            this.mNextAppTransitionPackage = str;
            this.mNextAppTransitionEnter = i;
            this.mNextAppTransitionExit = i2;
            this.mNextAppTransitionBackgroundColor = i3;
            postAnimationCallback();
            this.mNextAppTransitionCallback = iRemoteCallback;
            this.mAnimationFinishedCallback = iRemoteCallback2;
            this.mOverrideTaskTransition = z;
        }
    }

    void overridePendingAppTransitionScaleUp(int i, int i2, int i3, int i4) {
        if (canOverridePendingAppTransition()) {
            clear();
            this.mNextAppTransitionType = 2;
            putDefaultNextAppTransitionCoordinates(i, i2, i3, i4, null);
            postAnimationCallback();
        }
    }

    void overridePendingAppTransitionClipReveal(int i, int i2, int i3, int i4) {
        if (canOverridePendingAppTransition()) {
            clear();
            this.mNextAppTransitionType = 8;
            putDefaultNextAppTransitionCoordinates(i, i2, i3, i4, null);
            postAnimationCallback();
        }
    }

    void overridePendingAppTransitionThumb(android.hardware.HardwareBuffer hardwareBuffer, int i, int i2, android.os.IRemoteCallback iRemoteCallback, boolean z) {
        if (canOverridePendingAppTransition()) {
            clear();
            this.mNextAppTransitionType = z ? 3 : 4;
            this.mNextAppTransitionScaleUp = z;
            putDefaultNextAppTransitionCoordinates(i, i2, 0, 0, hardwareBuffer);
            postAnimationCallback();
            this.mNextAppTransitionCallback = iRemoteCallback;
        }
    }

    void overridePendingAppTransitionAspectScaledThumb(android.hardware.HardwareBuffer hardwareBuffer, int i, int i2, int i3, int i4, android.os.IRemoteCallback iRemoteCallback, boolean z) {
        if (canOverridePendingAppTransition()) {
            clear();
            this.mNextAppTransitionType = z ? 5 : 6;
            this.mNextAppTransitionScaleUp = z;
            putDefaultNextAppTransitionCoordinates(i, i2, i3, i4, hardwareBuffer);
            postAnimationCallback();
            this.mNextAppTransitionCallback = iRemoteCallback;
        }
    }

    void overridePendingAppTransitionMultiThumb(android.view.AppTransitionAnimationSpec[] appTransitionAnimationSpecArr, android.os.IRemoteCallback iRemoteCallback, android.os.IRemoteCallback iRemoteCallback2, boolean z) {
        if (canOverridePendingAppTransition()) {
            clear();
            this.mNextAppTransitionType = z ? 5 : 6;
            this.mNextAppTransitionScaleUp = z;
            if (appTransitionAnimationSpecArr != null) {
                for (int i = 0; i < appTransitionAnimationSpecArr.length; i++) {
                    android.view.AppTransitionAnimationSpec appTransitionAnimationSpec = appTransitionAnimationSpecArr[i];
                    if (appTransitionAnimationSpec != null) {
                        java.util.function.Predicate<com.android.server.wm.Task> obtainPredicate = com.android.internal.util.function.pooled.PooledLambda.obtainPredicate(new com.android.server.wm.AppTransition$$ExternalSyntheticLambda1(), com.android.internal.util.function.pooled.PooledLambda.__(com.android.server.wm.Task.class), java.lang.Integer.valueOf(appTransitionAnimationSpec.taskId));
                        com.android.server.wm.Task task = this.mDisplayContent.getTask(obtainPredicate);
                        obtainPredicate.recycle();
                        if (task != null) {
                            this.mNextAppTransitionAnimationsSpecs.put(task.hashCode(), appTransitionAnimationSpec);
                            if (i == 0) {
                                android.graphics.Rect rect = appTransitionAnimationSpec.rect;
                                putDefaultNextAppTransitionCoordinates(rect.left, rect.top, rect.width(), rect.height(), appTransitionAnimationSpec.buffer);
                            }
                        }
                    }
                }
            }
            postAnimationCallback();
            this.mNextAppTransitionCallback = iRemoteCallback;
            this.mAnimationFinishedCallback = iRemoteCallback2;
        }
    }

    void overridePendingAppTransitionMultiThumbFuture(android.view.IAppTransitionAnimationSpecsFuture iAppTransitionAnimationSpecsFuture, android.os.IRemoteCallback iRemoteCallback, boolean z) {
        if (canOverridePendingAppTransition()) {
            clear();
            this.mNextAppTransitionType = z ? 5 : 6;
            this.mNextAppTransitionAnimationsSpecsFuture = iAppTransitionAnimationSpecsFuture;
            this.mNextAppTransitionScaleUp = z;
            this.mNextAppTransitionFutureCallback = iRemoteCallback;
            if (isReady()) {
                fetchAppTransitionSpecsFromFuture();
            }
        }
    }

    void overridePendingAppTransitionRemote(android.view.RemoteAnimationAdapter remoteAnimationAdapter) {
        overridePendingAppTransitionRemote(remoteAnimationAdapter, false, false);
    }

    void overridePendingAppTransitionRemote(android.view.RemoteAnimationAdapter remoteAnimationAdapter, boolean z, boolean z2) {
        com.android.internal.protolog.ProtoLogImpl_1545807451.i(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_APP_TRANSITIONS, 6217525691846442213L, 3, null, java.lang.Boolean.valueOf(isTransitionSet()), java.lang.String.valueOf(remoteAnimationAdapter));
        if (isTransitionSet() && !this.mNextAppTransitionIsSync) {
            clear(!z2);
            this.mNextAppTransitionType = 10;
            this.mRemoteAnimationController = new com.android.server.wm.RemoteAnimationController(this.mService, this.mDisplayContent, remoteAnimationAdapter, this.mHandler, z2);
            this.mNextAppTransitionIsSync = z;
        }
    }

    void overrideInPlaceAppTransition(java.lang.String str, int i) {
        if (canOverridePendingAppTransition()) {
            clear();
            this.mNextAppTransitionType = 7;
            this.mNextAppTransitionPackage = str;
            this.mNextAppTransitionInPlace = i;
        }
    }

    void overridePendingAppTransitionStartCrossProfileApps() {
        if (canOverridePendingAppTransition()) {
            clear();
            this.mNextAppTransitionType = 9;
            postAnimationCallback();
        }
    }

    private boolean canOverridePendingAppTransition() {
        return isTransitionSet() && this.mNextAppTransitionType != 10;
    }

    private void fetchAppTransitionSpecsFromFuture() {
        if (this.mNextAppTransitionAnimationsSpecsFuture != null) {
            this.mNextAppTransitionAnimationsSpecsPending = true;
            final android.view.IAppTransitionAnimationSpecsFuture iAppTransitionAnimationSpecsFuture = this.mNextAppTransitionAnimationsSpecsFuture;
            this.mNextAppTransitionAnimationsSpecsFuture = null;
            this.mDefaultExecutor.execute(new java.lang.Runnable() { // from class: com.android.server.wm.AppTransition$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.wm.AppTransition.this.lambda$fetchAppTransitionSpecsFromFuture$1(iAppTransitionAnimationSpecsFuture);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$fetchAppTransitionSpecsFromFuture$1(android.view.IAppTransitionAnimationSpecsFuture iAppTransitionAnimationSpecsFuture) {
        android.view.AppTransitionAnimationSpec[] appTransitionAnimationSpecArr;
        try {
            android.os.Binder.allowBlocking(iAppTransitionAnimationSpecsFuture.asBinder());
            appTransitionAnimationSpecArr = iAppTransitionAnimationSpecsFuture.get();
        } catch (android.os.RemoteException e) {
            android.util.Slog.w(TAG, "Failed to fetch app transition specs: " + e);
            appTransitionAnimationSpecArr = null;
        }
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mService.mGlobalLock;
        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                this.mNextAppTransitionAnimationsSpecsPending = false;
                overridePendingAppTransitionMultiThumb(appTransitionAnimationSpecArr, this.mNextAppTransitionFutureCallback, null, this.mNextAppTransitionScaleUp);
                this.mNextAppTransitionFutureCallback = null;
                this.mService.requestTraversal();
            } catch (java.lang.Throwable th) {
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
    }

    public java.lang.String toString() {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append("mNextAppTransitionRequests=[");
        java.util.Iterator<java.lang.Integer> it = this.mNextAppTransitionRequests.iterator();
        boolean z = false;
        while (it.hasNext()) {
            java.lang.Integer next = it.next();
            if (z) {
                sb.append(", ");
            }
            sb.append(appTransitionToString(next.intValue()));
            z = true;
        }
        sb.append("]");
        sb.append(", mNextAppTransitionFlags=" + appTransitionFlagsToString(this.mNextAppTransitionFlags));
        return sb.toString();
    }

    public static java.lang.String appTransitionOldToString(int i) {
        switch (i) {
            case -1:
                return "TRANSIT_OLD_UNSET";
            case 0:
                return "TRANSIT_OLD_NONE";
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 17:
            case 19:
            case 27:
            default:
                return "<UNKNOWN: " + i + ">";
            case 6:
                return "TRANSIT_OLD_ACTIVITY_OPEN";
            case 7:
                return "TRANSIT_OLD_ACTIVITY_CLOSE";
            case 8:
                return "TRANSIT_OLD_TASK_OPEN";
            case 9:
                return "TRANSIT_OLD_TASK_CLOSE";
            case 10:
                return "TRANSIT_OLD_TASK_TO_FRONT";
            case 11:
                return "TRANSIT_OLD_TASK_TO_BACK";
            case 12:
                return "TRANSIT_OLD_WALLPAPER_CLOSE";
            case 13:
                return "TRANSIT_OLD_WALLPAPER_OPEN";
            case 14:
                return "TRANSIT_OLD_WALLPAPER_INTRA_OPEN";
            case 15:
                return "TRANSIT_OLD_WALLPAPER_INTRA_CLOSE";
            case 16:
                return "TRANSIT_OLD_TASK_OPEN_BEHIND";
            case 18:
                return "TRANSIT_OLD_ACTIVITY_RELAUNCH";
            case 20:
                return "TRANSIT_OLD_KEYGUARD_GOING_AWAY";
            case 21:
                return "TRANSIT_OLD_KEYGUARD_GOING_AWAY_ON_WALLPAPER";
            case 22:
                return "TRANSIT_OLD_KEYGUARD_OCCLUDE";
            case 23:
                return "TRANSIT_OLD_KEYGUARD_UNOCCLUDE";
            case 24:
                return "TRANSIT_OLD_TRANSLUCENT_ACTIVITY_OPEN";
            case 25:
                return "TRANSIT_OLD_TRANSLUCENT_ACTIVITY_CLOSE";
            case 26:
                return "TRANSIT_OLD_CRASHING_ACTIVITY_CLOSE";
            case 28:
                return "TRANSIT_OLD_TASK_FRAGMENT_OPEN";
            case 29:
                return "TRANSIT_OLD_TASK_FRAGMENT_CLOSE";
            case 30:
                return "TRANSIT_OLD_TASK_FRAGMENT_CHANGE";
            case 31:
                return "TRANSIT_OLD_DREAM_ACTIVITY_OPEN";
            case 32:
                return "TRANSIT_OLD_DREAM_ACTIVITY_CLOSE";
            case 33:
                return "TRANSIT_OLD_KEYGUARD_OCCLUDE_BY_DREAM";
        }
    }

    public static java.lang.String appTransitionToString(int i) {
        switch (i) {
            case 0:
                return "TRANSIT_NONE";
            case 1:
                return "TRANSIT_OPEN";
            case 2:
                return "TRANSIT_CLOSE";
            case 3:
                return "TRANSIT_TO_FRONT";
            case 4:
                return "TRANSIT_TO_BACK";
            case 5:
                return "TRANSIT_RELAUNCH";
            case 6:
                return "TRANSIT_CHANGE";
            case 7:
                return "TRANSIT_KEYGUARD_GOING_AWAY";
            case 8:
                return "TRANSIT_KEYGUARD_OCCLUDE";
            case 9:
                return "TRANSIT_KEYGUARD_UNOCCLUDE";
            default:
                return "<UNKNOWN: " + i + ">";
        }
    }

    private java.lang.String appStateToString() {
        switch (this.mAppTransitionState) {
            case 0:
                return "APP_STATE_IDLE";
            case 1:
                return "APP_STATE_READY";
            case 2:
                return "APP_STATE_RUNNING";
            case 3:
                return "APP_STATE_TIMEOUT";
            default:
                return "unknown state=" + this.mAppTransitionState;
        }
    }

    private java.lang.String transitTypeToString() {
        switch (this.mNextAppTransitionType) {
            case 0:
                return "NEXT_TRANSIT_TYPE_NONE";
            case 1:
                return "NEXT_TRANSIT_TYPE_CUSTOM";
            case 2:
                return "NEXT_TRANSIT_TYPE_SCALE_UP";
            case 3:
                return "NEXT_TRANSIT_TYPE_THUMBNAIL_SCALE_UP";
            case 4:
                return "NEXT_TRANSIT_TYPE_THUMBNAIL_SCALE_DOWN";
            case 5:
                return "NEXT_TRANSIT_TYPE_THUMBNAIL_ASPECT_SCALE_UP";
            case 6:
                return "NEXT_TRANSIT_TYPE_THUMBNAIL_ASPECT_SCALE_DOWN";
            case 7:
                return "NEXT_TRANSIT_TYPE_CUSTOM_IN_PLACE";
            case 8:
            default:
                return "unknown type=" + this.mNextAppTransitionType;
            case 9:
                return "NEXT_TRANSIT_TYPE_OPEN_CROSS_PROFILE_APPS";
        }
    }

    static {
        sFlagToString.add(new android.util.Pair<>(1, "TRANSIT_FLAG_KEYGUARD_GOING_AWAY_TO_SHADE"));
        sFlagToString.add(new android.util.Pair<>(2, "TRANSIT_FLAG_KEYGUARD_GOING_AWAY_NO_ANIMATION"));
        sFlagToString.add(new android.util.Pair<>(4, "TRANSIT_FLAG_KEYGUARD_GOING_AWAY_WITH_WALLPAPER"));
        sFlagToString.add(new android.util.Pair<>(8, "TRANSIT_FLAG_KEYGUARD_GOING_AWAY_SUBTLE_ANIMATION"));
        sFlagToString.add(new android.util.Pair<>(512, "TRANSIT_FLAG_KEYGUARD_GOING_AWAY_TO_LAUNCHER_WITH_IN_WINDOW_ANIMATIONS"));
        sFlagToString.add(new android.util.Pair<>(16, "TRANSIT_FLAG_APP_CRASHED"));
        sFlagToString.add(new android.util.Pair<>(32, "TRANSIT_FLAG_OPEN_BEHIND"));
    }

    public static java.lang.String appTransitionFlagsToString(int i) {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        java.util.Iterator<android.util.Pair<java.lang.Integer, java.lang.String>> it = sFlagToString.iterator();
        java.lang.String str = "";
        while (it.hasNext()) {
            android.util.Pair<java.lang.Integer, java.lang.String> next = it.next();
            if ((((java.lang.Integer) next.first).intValue() & i) != 0) {
                sb.append(str);
                sb.append((java.lang.String) next.second);
                str = " | ";
            }
        }
        return sb.toString();
    }

    void dumpDebug(android.util.proto.ProtoOutputStream protoOutputStream, long j) {
        long start = protoOutputStream.start(j);
        protoOutputStream.write(1159641169921L, this.mAppTransitionState);
        protoOutputStream.write(1159641169922L, this.mLastUsedAppTransition);
        protoOutputStream.end(start);
    }

    public void dump(java.io.PrintWriter printWriter, java.lang.String str) {
        printWriter.print(str);
        printWriter.println(this);
        printWriter.print(str);
        printWriter.print("mAppTransitionState=");
        printWriter.println(appStateToString());
        if (this.mNextAppTransitionType != 0) {
            printWriter.print(str);
            printWriter.print("mNextAppTransitionType=");
            printWriter.println(transitTypeToString());
        }
        if (this.mNextAppTransitionOverrideRequested || this.mNextAppTransitionType == 1) {
            printWriter.print(str);
            printWriter.print("mNextAppTransitionPackage=");
            printWriter.println(this.mNextAppTransitionPackage);
            printWriter.print(str);
            printWriter.print("mNextAppTransitionEnter=0x");
            printWriter.print(java.lang.Integer.toHexString(this.mNextAppTransitionEnter));
            printWriter.print(" mNextAppTransitionExit=0x");
            printWriter.println(java.lang.Integer.toHexString(this.mNextAppTransitionExit));
            printWriter.print(" mNextAppTransitionBackgroundColor=0x");
            printWriter.println(java.lang.Integer.toHexString(this.mNextAppTransitionBackgroundColor));
        }
        switch (this.mNextAppTransitionType) {
            case 2:
                getDefaultNextAppTransitionStartRect(this.mTmpRect);
                printWriter.print(str);
                printWriter.print("mNextAppTransitionStartX=");
                printWriter.print(this.mTmpRect.left);
                printWriter.print(" mNextAppTransitionStartY=");
                printWriter.println(this.mTmpRect.top);
                printWriter.print(str);
                printWriter.print("mNextAppTransitionStartWidth=");
                printWriter.print(this.mTmpRect.width());
                printWriter.print(" mNextAppTransitionStartHeight=");
                printWriter.println(this.mTmpRect.height());
                break;
            case 3:
            case 4:
            case 5:
            case 6:
                printWriter.print(str);
                printWriter.print("mDefaultNextAppTransitionAnimationSpec=");
                printWriter.println(this.mDefaultNextAppTransitionAnimationSpec);
                printWriter.print(str);
                printWriter.print("mNextAppTransitionAnimationsSpecs=");
                printWriter.println(this.mNextAppTransitionAnimationsSpecs);
                printWriter.print(str);
                printWriter.print("mNextAppTransitionScaleUp=");
                printWriter.println(this.mNextAppTransitionScaleUp);
                break;
            case 7:
                printWriter.print(str);
                printWriter.print("mNextAppTransitionPackage=");
                printWriter.println(this.mNextAppTransitionPackage);
                printWriter.print(str);
                printWriter.print("mNextAppTransitionInPlace=0x");
                printWriter.print(java.lang.Integer.toHexString(this.mNextAppTransitionInPlace));
                break;
        }
        if (this.mNextAppTransitionCallback != null) {
            printWriter.print(str);
            printWriter.print("mNextAppTransitionCallback=");
            printWriter.println(this.mNextAppTransitionCallback);
        }
        if (this.mLastUsedAppTransition != 0) {
            printWriter.print(str);
            printWriter.print("mLastUsedAppTransition=");
            printWriter.println(appTransitionOldToString(this.mLastUsedAppTransition));
            printWriter.print(str);
            printWriter.print("mLastOpeningApp=");
            printWriter.println(this.mLastOpeningApp);
            printWriter.print(str);
            printWriter.print("mLastClosingApp=");
            printWriter.println(this.mLastClosingApp);
            printWriter.print(str);
            printWriter.print("mLastChangingApp=");
            printWriter.println(this.mLastChangingApp);
        }
    }

    boolean prepareAppTransition(int i, int i2) {
        if (this.mDisplayContent.mTransitionController.isShellTransitionsEnabled()) {
            return false;
        }
        this.mNextAppTransitionRequests.add(java.lang.Integer.valueOf(i));
        this.mNextAppTransitionFlags |= i2;
        updateBooster();
        removeAppTransitionTimeoutCallbacks();
        this.mHandler.postDelayed(this.mHandleAppTransitionTimeoutRunnable, APP_TRANSITION_TIMEOUT_MS);
        return prepare();
    }

    public static boolean isKeyguardGoingAwayTransitOld(int i) {
        return i == 20 || i == 21;
    }

    static boolean isKeyguardOccludeTransitOld(int i) {
        return i == 22 || i == 33 || i == 23;
    }

    static boolean isKeyguardTransitOld(int i) {
        return isKeyguardGoingAwayTransitOld(i) || isKeyguardOccludeTransitOld(i);
    }

    static boolean isTaskTransitOld(int i) {
        return isTaskOpenTransitOld(i) || isTaskCloseTransitOld(i);
    }

    static boolean isTaskCloseTransitOld(int i) {
        return i == 9 || i == 11;
    }

    private static boolean isTaskOpenTransitOld(int i) {
        return i == 8 || i == 16 || i == 10;
    }

    static boolean isActivityTransitOld(int i) {
        return i == 6 || i == 7 || i == 18;
    }

    static boolean isTaskFragmentTransitOld(int i) {
        return i == 28 || i == 29 || i == 30;
    }

    static boolean isChangeTransitOld(int i) {
        return i == 27 || i == 30;
    }

    static boolean isClosingTransitOld(int i) {
        return i == 7 || i == 9 || i == 12 || i == 15 || i == 25 || i == 26;
    }

    static boolean isNormalTransit(int i) {
        return i == 1 || i == 2 || i == 3 || i == 4;
    }

    static boolean isKeyguardTransit(int i) {
        return i == 7 || i == 8 || i == 9;
    }

    int getKeyguardTransition() {
        if (this.mNextAppTransitionRequests.indexOf(7) != -1) {
            return 7;
        }
        int indexOf = this.mNextAppTransitionRequests.indexOf(9);
        int indexOf2 = this.mNextAppTransitionRequests.indexOf(8);
        if (indexOf == -1 && indexOf2 == -1) {
            return 0;
        }
        if (indexOf == -1 || indexOf >= indexOf2) {
            return indexOf != -1 ? 9 : 8;
        }
        return 0;
    }

    int getFirstAppTransition() {
        for (int i = 0; i < this.mNextAppTransitionRequests.size(); i++) {
            int intValue = this.mNextAppTransitionRequests.get(i).intValue();
            if (intValue != 0 && !isKeyguardTransit(intValue)) {
                return intValue;
            }
        }
        return 0;
    }

    boolean containsTransitRequest(int i) {
        return this.mNextAppTransitionRequests.contains(java.lang.Integer.valueOf(i));
    }

    private boolean shouldScaleDownThumbnailTransition(int i, int i2) {
        return this.mGridLayoutRecentsEnabled || i2 == 1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: handleAppTransitionTimeout, reason: merged with bridge method [inline-methods] */
    public void lambda$new$0() {
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mService.mGlobalLock;
        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                com.android.server.wm.DisplayContent displayContent = this.mDisplayContent;
                if (displayContent == null) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    return;
                }
                notifyAppTransitionTimeoutLocked();
                if (isTransitionSet() || !displayContent.mOpeningApps.isEmpty() || !displayContent.mClosingApps.isEmpty() || !displayContent.mChangingContainers.isEmpty()) {
                    com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_APP_TRANSITIONS, 5233255302148535928L, 349, null, java.lang.Long.valueOf(displayContent.getDisplayId()), java.lang.Boolean.valueOf(displayContent.mAppTransition.isTransitionSet()), java.lang.Long.valueOf(displayContent.mOpeningApps.size()), java.lang.Long.valueOf(displayContent.mClosingApps.size()), java.lang.Long.valueOf(displayContent.mChangingContainers.size()));
                    setTimeout();
                    this.mService.mWindowPlacerLocked.performSurfacePlacement();
                }
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
            } catch (java.lang.Throwable th) {
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void doAnimationCallback(@android.annotation.NonNull android.os.IRemoteCallback iRemoteCallback) {
        try {
            iRemoteCallback.sendResult((android.os.Bundle) null);
        } catch (android.os.RemoteException e) {
        }
    }

    private void setAppTransitionFinishedCallbackIfNeeded(android.view.animation.Animation animation) {
        android.os.IRemoteCallback iRemoteCallback = this.mAnimationFinishedCallback;
        if (iRemoteCallback != null && animation != null) {
            animation.setAnimationListener(new com.android.server.wm.AppTransition.AnonymousClass1(iRemoteCallback));
        }
    }

    /* renamed from: com.android.server.wm.AppTransition$1, reason: invalid class name */
    class AnonymousClass1 implements android.view.animation.Animation.AnimationListener {
        final /* synthetic */ android.os.IRemoteCallback val$callback;

        AnonymousClass1(android.os.IRemoteCallback iRemoteCallback) {
            this.val$callback = iRemoteCallback;
        }

        @Override // android.view.animation.Animation.AnimationListener
        public void onAnimationStart(android.view.animation.Animation animation) {
        }

        @Override // android.view.animation.Animation.AnimationListener
        public void onAnimationEnd(android.view.animation.Animation animation) {
            com.android.server.wm.AppTransition.this.mHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new java.util.function.Consumer() { // from class: com.android.server.wm.AppTransition$1$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    com.android.server.wm.AppTransition.doAnimationCallback((android.os.IRemoteCallback) obj);
                }
            }, this.val$callback));
        }

        @Override // android.view.animation.Animation.AnimationListener
        public void onAnimationRepeat(android.view.animation.Animation animation) {
        }
    }

    void removeAppTransitionTimeoutCallbacks() {
        this.mHandler.removeCallbacks(this.mHandleAppTransitionTimeoutRunnable);
    }
}
