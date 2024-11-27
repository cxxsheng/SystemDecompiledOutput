package android.app;

/* loaded from: classes.dex */
public class ActivityOptions extends android.app.ComponentOptions {
    public static final int ANIM_CLIP_REVEAL = 11;
    public static final int ANIM_CUSTOM = 1;
    public static final int ANIM_CUSTOM_IN_PLACE = 10;
    public static final int ANIM_DEFAULT = 6;
    public static final int ANIM_FROM_STYLE = 14;
    public static final int ANIM_LAUNCH_TASK_BEHIND = 7;
    public static final int ANIM_NONE = 0;
    public static final int ANIM_OPEN_CROSS_PROFILE_APPS = 12;
    public static final int ANIM_REMOTE_ANIMATION = 13;
    public static final int ANIM_SCALE_UP = 2;
    public static final int ANIM_SCENE_TRANSITION = 5;
    public static final int ANIM_THUMBNAIL_ASPECT_SCALE_DOWN = 9;
    public static final int ANIM_THUMBNAIL_ASPECT_SCALE_UP = 8;
    public static final int ANIM_THUMBNAIL_SCALE_DOWN = 4;
    public static final int ANIM_THUMBNAIL_SCALE_UP = 3;
    public static final int ANIM_UNDEFINED = -1;
    public static final java.lang.String EXTRA_USAGE_TIME_REPORT = "android.activity.usage_time";
    public static final java.lang.String EXTRA_USAGE_TIME_REPORT_PACKAGES = "android.usage_time_packages";
    private static final java.lang.String KEY_ANIMATION_FINISHED_LISTENER = "android:activity.animationFinishedListener";
    private static final java.lang.String KEY_ANIM_ABORT_LISTENER = "android:activity.animAbortListener";
    public static final java.lang.String KEY_ANIM_BACKGROUND_COLOR = "android:activity.backgroundColor";
    public static final java.lang.String KEY_ANIM_ENTER_RES_ID = "android:activity.animEnterRes";
    public static final java.lang.String KEY_ANIM_EXIT_RES_ID = "android:activity.animExitRes";
    public static final java.lang.String KEY_ANIM_HEIGHT = "android:activity.animHeight";
    public static final java.lang.String KEY_ANIM_IN_PLACE_RES_ID = "android:activity.animInPlaceRes";
    private static final java.lang.String KEY_ANIM_SPECS = "android:activity.animSpecs";
    public static final java.lang.String KEY_ANIM_START_LISTENER = "android:activity.animStartListener";
    public static final java.lang.String KEY_ANIM_START_X = "android:activity.animStartX";
    public static final java.lang.String KEY_ANIM_START_Y = "android:activity.animStartY";
    public static final java.lang.String KEY_ANIM_THUMBNAIL = "android:activity.animThumbnail";
    public static final java.lang.String KEY_ANIM_TYPE = "android:activity.animType";
    public static final java.lang.String KEY_ANIM_WIDTH = "android:activity.animWidth";
    private static final java.lang.String KEY_APPLY_ACTIVITY_FLAGS_FOR_BUBBLES = "android:activity.applyActivityFlagsForBubbles";
    private static final java.lang.String KEY_APPLY_MULTIPLE_TASK_FLAG_FOR_SHORTCUT = "android:activity.applyMultipleTaskFlagForShortcut";
    private static final java.lang.String KEY_APPLY_NO_USER_ACTION_FLAG_FOR_SHORTCUT = "android:activity.applyNoUserActionFlagForShortcut";
    private static final java.lang.String KEY_AVOID_MOVE_TO_FRONT = "android.activity.avoidMoveToFront";
    private static final java.lang.String KEY_CALLER_DISPLAY_ID = "android.activity.callerDisplayId";
    private static final java.lang.String KEY_DISABLE_STARTING_WINDOW = "android.activity.disableStarting";
    private static final java.lang.String KEY_DISALLOW_ENTER_PICTURE_IN_PICTURE_WHILE_LAUNCHING = "android:activity.disallowEnterPictureInPictureWhileLaunching";
    private static final java.lang.String KEY_DISMISS_KEYGUARD_IF_INSECURE = "android.activity.dismissKeyguardIfInsecure";
    private static final java.lang.String KEY_FREEZE_RECENT_TASKS_REORDERING = "android.activity.freezeRecentTasksReordering";
    private static final java.lang.String KEY_INSTANT_APP_VERIFICATION_BUNDLE = "android:instantapps.installerbundle";
    private static final java.lang.String KEY_LAUNCHED_FROM_BUBBLE = "android.activity.launchTypeBubble";
    private static final java.lang.String KEY_LAUNCH_ACTIVITY_TYPE = "android.activity.activityType";
    public static final java.lang.String KEY_LAUNCH_BOUNDS = "android:activity.launchBounds";
    public static final java.lang.String KEY_LAUNCH_COOKIE = "android.activity.launchCookie";
    private static final java.lang.String KEY_LAUNCH_DISPLAY_ID = "android.activity.launchDisplayId";
    private static final java.lang.String KEY_LAUNCH_INTO_PIP_PARAMS = "android.activity.launchIntoPipParams";
    public static final java.lang.String KEY_LAUNCH_ROOT_TASK_TOKEN = "android.activity.launchRootTaskToken";
    private static final java.lang.String KEY_LAUNCH_TASK_DISPLAY_AREA_FEATURE_ID = "android.activity.launchTaskDisplayAreaFeatureId";
    private static final java.lang.String KEY_LAUNCH_TASK_DISPLAY_AREA_TOKEN = "android.activity.launchTaskDisplayAreaToken";
    public static final java.lang.String KEY_LAUNCH_TASK_FRAGMENT_TOKEN = "android.activity.launchTaskFragmentToken";
    private static final java.lang.String KEY_LAUNCH_TASK_ID = "android.activity.launchTaskId";
    private static final java.lang.String KEY_LAUNCH_WINDOWING_MODE = "android.activity.windowingMode";
    public static final java.lang.String KEY_LEGACY_PERMISSION_PROMPT_ELIGIBLE = "android:activity.legacyPermissionPromptEligible";
    private static final java.lang.String KEY_LOCK_TASK_MODE = "android:activity.lockTaskMode";
    private static final java.lang.String KEY_OVERRIDE_TASK_TRANSITION = "android:activity.overrideTaskTransition";
    public static final java.lang.String KEY_PACKAGE_NAME = "android:activity.packageName";
    private static final java.lang.String KEY_PENDING_INTENT_CREATOR_BACKGROUND_ACTIVITY_START_MODE = "android.activity.pendingIntentCreatorBackgroundActivityStartMode";
    private static final java.lang.String KEY_PENDING_INTENT_LAUNCH_FLAGS = "android.activity.pendingIntentLaunchFlags";
    private static final java.lang.String KEY_REMOTE_ANIMATION_ADAPTER = "android:activity.remoteAnimationAdapter";
    private static final java.lang.String KEY_REMOTE_TRANSITION = "android:activity.remoteTransition";
    private static final java.lang.String KEY_REMOVE_WITH_TASK_ORGANIZER = "android.activity.removeWithTaskOrganizer";
    private static final java.lang.String KEY_ROTATION_ANIMATION_HINT = "android:activity.rotationAnimationHint";
    private static final java.lang.String KEY_SCENE_TRANSITION_INFO = "android:activity.sceneTransitionInfo";
    private static final java.lang.String KEY_SHARE_IDENTITY = "android:activity.shareIdentity";
    private static final java.lang.String KEY_SOURCE_INFO = "android.activity.sourceInfo";
    private static final java.lang.String KEY_SPECS_FUTURE = "android:activity.specsFuture";
    private static final java.lang.String KEY_SPLASH_SCREEN_STYLE = "android.activity.splashScreenStyle";
    public static final java.lang.String KEY_SPLASH_SCREEN_THEME = "android.activity.splashScreenTheme";
    private static final java.lang.String KEY_TASK_ALWAYS_ON_TOP = "android.activity.alwaysOnTop";
    private static final java.lang.String KEY_TASK_OVERLAY = "android.activity.taskOverlay";
    private static final java.lang.String KEY_TASK_OVERLAY_CAN_RESUME = "android.activity.taskOverlayCanResume";
    public static final java.lang.String KEY_TRANSIENT_LAUNCH = "android.activity.transientLaunch";
    private static final java.lang.String KEY_USAGE_TIME_REPORT = "android:activity.usageTimeReport";
    public static final int MODE_BACKGROUND_ACTIVITY_START_ALLOWED = 1;
    public static final int MODE_BACKGROUND_ACTIVITY_START_DENIED = 2;
    public static final int MODE_BACKGROUND_ACTIVITY_START_SYSTEM_DEFINED = 0;
    private static final java.lang.String TAG = "ActivityOptions";
    private android.view.AppTransitionAnimationSpec[] mAnimSpecs;
    private android.os.IRemoteCallback mAnimationAbortListener;
    private android.os.IRemoteCallback mAnimationFinishedListener;
    private android.os.IRemoteCallback mAnimationStartedListener;
    private int mAnimationType;
    private android.os.Bundle mAppVerificationBundle;
    private boolean mApplyActivityFlagsForBubbles;
    private boolean mApplyMultipleTaskFlagForShortcut;
    private boolean mApplyNoUserActionFlagForShortcut;
    private boolean mAvoidMoveToFront;
    private int mCallerDisplayId;
    private int mCustomBackgroundColor;
    private int mCustomEnterResId;
    private int mCustomExitResId;
    private int mCustomInPlaceResId;
    private boolean mDisableStartingWindow;
    private boolean mDisallowEnterPictureInPictureWhileLaunching;
    private boolean mDismissKeyguardIfInsecure;
    private boolean mFreezeRecentTasksReordering;
    private int mHeight;
    private boolean mIsEligibleForLegacyPermissionPrompt;
    private int mLaunchActivityType;
    private android.graphics.Rect mLaunchBounds;
    private android.os.IBinder mLaunchCookie;
    private int mLaunchDisplayId;
    private android.app.PictureInPictureParams mLaunchIntoPipParams;
    private android.window.WindowContainerToken mLaunchRootTask;
    private android.window.WindowContainerToken mLaunchTaskDisplayArea;
    private int mLaunchTaskDisplayAreaFeatureId;
    private android.os.IBinder mLaunchTaskFragmentToken;
    private int mLaunchTaskId;
    private int mLaunchWindowingMode;
    private boolean mLaunchedFromBubble;
    private boolean mLockTaskMode;
    private boolean mOverrideTaskTransition;
    private java.lang.String mPackageName;
    private int mPendingIntentCreatorBackgroundActivityStartMode;
    private int mPendingIntentLaunchFlags;
    private android.view.RemoteAnimationAdapter mRemoteAnimationAdapter;
    private android.window.RemoteTransition mRemoteTransition;
    private boolean mRemoveWithTaskOrganizer;
    private int mRotationAnimationHint;
    private android.app.ActivityOptions.SceneTransitionInfo mSceneTransitionInfo;
    private boolean mShareIdentity;
    private android.app.ActivityOptions.SourceInfo mSourceInfo;
    private android.view.IAppTransitionAnimationSpecsFuture mSpecsFuture;
    private int mSplashScreenStyle;
    private java.lang.String mSplashScreenThemeResName;
    private int mStartX;
    private int mStartY;
    private boolean mTaskAlwaysOnTop;
    private boolean mTaskOverlay;
    private boolean mTaskOverlayCanResume;
    private android.graphics.Bitmap mThumbnail;
    private boolean mTransientLaunch;
    private android.app.PendingIntent mUsageTimeReport;
    private int mWidth;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface BackgroundActivityStartMode {
    }

    public interface OnAnimationFinishedListener {
        void onAnimationFinished(long j);
    }

    public interface OnAnimationStartedListener {
        void onAnimationStarted(long j);
    }

    public static android.app.ActivityOptions makeCustomAnimation(android.content.Context context, int i, int i2) {
        return makeCustomAnimation(context, i, i2, 0, null, null);
    }

    public static android.app.ActivityOptions makeCustomAnimation(android.content.Context context, int i, int i2, int i3) {
        return makeCustomAnimation(context, i, i2, i3, null, null);
    }

    public static android.app.ActivityOptions makeCustomAnimation(android.content.Context context, int i, int i2, int i3, android.os.Handler handler, android.app.ActivityOptions.OnAnimationStartedListener onAnimationStartedListener) {
        android.app.ActivityOptions activityOptions = new android.app.ActivityOptions();
        activityOptions.mPackageName = context.getPackageName();
        activityOptions.mAnimationType = 1;
        activityOptions.mCustomEnterResId = i;
        activityOptions.mCustomExitResId = i2;
        activityOptions.mCustomBackgroundColor = i3;
        activityOptions.setOnAnimationStartedListener(handler, onAnimationStartedListener);
        return activityOptions;
    }

    public static android.app.ActivityOptions makeCustomAnimation(android.content.Context context, int i, int i2, int i3, android.os.Handler handler, android.app.ActivityOptions.OnAnimationStartedListener onAnimationStartedListener, android.app.ActivityOptions.OnAnimationFinishedListener onAnimationFinishedListener) {
        android.app.ActivityOptions makeCustomAnimation = makeCustomAnimation(context, i, i2, i3, handler, onAnimationStartedListener);
        makeCustomAnimation.setOnAnimationFinishedListener(handler, onAnimationFinishedListener);
        return makeCustomAnimation;
    }

    public static android.app.ActivityOptions makeCustomTaskAnimation(android.content.Context context, int i, int i2, android.os.Handler handler, android.app.ActivityOptions.OnAnimationStartedListener onAnimationStartedListener, android.app.ActivityOptions.OnAnimationFinishedListener onAnimationFinishedListener) {
        android.app.ActivityOptions makeCustomAnimation = makeCustomAnimation(context, i, i2, 0, handler, onAnimationStartedListener, onAnimationFinishedListener);
        makeCustomAnimation.mOverrideTaskTransition = true;
        return makeCustomAnimation;
    }

    public static android.app.ActivityOptions makeCustomInPlaceAnimation(android.content.Context context, int i) {
        if (i == 0) {
            throw new java.lang.RuntimeException("You must specify a valid animation.");
        }
        android.app.ActivityOptions activityOptions = new android.app.ActivityOptions();
        activityOptions.mPackageName = context.getPackageName();
        activityOptions.mAnimationType = 10;
        activityOptions.mCustomInPlaceResId = i;
        return activityOptions;
    }

    private void setOnAnimationStartedListener(final android.os.Handler handler, final android.app.ActivityOptions.OnAnimationStartedListener onAnimationStartedListener) {
        if (onAnimationStartedListener != null) {
            this.mAnimationStartedListener = new android.os.IRemoteCallback.Stub() { // from class: android.app.ActivityOptions.1
                @Override // android.os.IRemoteCallback
                public void sendResult(android.os.Bundle bundle) throws android.os.RemoteException {
                    final long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
                    handler.post(new java.lang.Runnable() { // from class: android.app.ActivityOptions.1.1
                        @Override // java.lang.Runnable
                        public void run() {
                            onAnimationStartedListener.onAnimationStarted(elapsedRealtime);
                        }
                    });
                }
            };
        }
    }

    private void setOnAnimationFinishedListener(final android.os.Handler handler, final android.app.ActivityOptions.OnAnimationFinishedListener onAnimationFinishedListener) {
        if (onAnimationFinishedListener != null) {
            this.mAnimationFinishedListener = new android.os.IRemoteCallback.Stub() { // from class: android.app.ActivityOptions.2
                @Override // android.os.IRemoteCallback
                public void sendResult(android.os.Bundle bundle) throws android.os.RemoteException {
                    final long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
                    handler.post(new java.lang.Runnable() { // from class: android.app.ActivityOptions.2.1
                        @Override // java.lang.Runnable
                        public void run() {
                            onAnimationFinishedListener.onAnimationFinished(elapsedRealtime);
                        }
                    });
                }
            };
        }
    }

    public void setOnAnimationFinishedListener(android.os.IRemoteCallback iRemoteCallback) {
        this.mAnimationFinishedListener = iRemoteCallback;
    }

    public void setOnAnimationAbortListener(android.os.IRemoteCallback iRemoteCallback) {
        this.mAnimationAbortListener = iRemoteCallback;
    }

    public static android.app.ActivityOptions makeScaleUpAnimation(android.view.View view, int i, int i2, int i3, int i4) {
        android.app.ActivityOptions activityOptions = new android.app.ActivityOptions();
        activityOptions.mPackageName = view.getContext().getPackageName();
        activityOptions.mAnimationType = 2;
        int[] iArr = new int[2];
        view.getLocationOnScreen(iArr);
        activityOptions.mStartX = iArr[0] + i;
        activityOptions.mStartY = iArr[1] + i2;
        activityOptions.mWidth = i3;
        activityOptions.mHeight = i4;
        return activityOptions;
    }

    public static android.app.ActivityOptions makeClipRevealAnimation(android.view.View view, int i, int i2, int i3, int i4) {
        android.app.ActivityOptions activityOptions = new android.app.ActivityOptions();
        activityOptions.mAnimationType = 11;
        int[] iArr = new int[2];
        view.getLocationOnScreen(iArr);
        activityOptions.mStartX = iArr[0] + i;
        activityOptions.mStartY = iArr[1] + i2;
        activityOptions.mWidth = i3;
        activityOptions.mHeight = i4;
        return activityOptions;
    }

    public static android.app.ActivityOptions makeOpenCrossProfileAppsAnimation() {
        android.app.ActivityOptions activityOptions = new android.app.ActivityOptions();
        activityOptions.mAnimationType = 12;
        return activityOptions;
    }

    public static android.app.ActivityOptions makeThumbnailScaleUpAnimation(android.view.View view, android.graphics.Bitmap bitmap, int i, int i2) {
        return makeThumbnailScaleUpAnimation(view, bitmap, i, i2, null);
    }

    private static android.app.ActivityOptions makeThumbnailScaleUpAnimation(android.view.View view, android.graphics.Bitmap bitmap, int i, int i2, android.app.ActivityOptions.OnAnimationStartedListener onAnimationStartedListener) {
        return makeThumbnailAnimation(view, bitmap, i, i2, onAnimationStartedListener, true);
    }

    private static android.app.ActivityOptions makeThumbnailAnimation(android.view.View view, android.graphics.Bitmap bitmap, int i, int i2, android.app.ActivityOptions.OnAnimationStartedListener onAnimationStartedListener, boolean z) {
        android.app.ActivityOptions activityOptions = new android.app.ActivityOptions();
        activityOptions.mPackageName = view.getContext().getPackageName();
        activityOptions.mAnimationType = z ? 3 : 4;
        activityOptions.mThumbnail = bitmap;
        int[] iArr = new int[2];
        view.getLocationOnScreen(iArr);
        activityOptions.mStartX = iArr[0] + i;
        activityOptions.mStartY = iArr[1] + i2;
        activityOptions.setOnAnimationStartedListener(view.getHandler(), onAnimationStartedListener);
        return activityOptions;
    }

    public static android.app.ActivityOptions makeMultiThumbFutureAspectScaleAnimation(android.content.Context context, android.os.Handler handler, android.view.IAppTransitionAnimationSpecsFuture iAppTransitionAnimationSpecsFuture, android.app.ActivityOptions.OnAnimationStartedListener onAnimationStartedListener, boolean z) {
        int i;
        android.app.ActivityOptions activityOptions = new android.app.ActivityOptions();
        activityOptions.mPackageName = context.getPackageName();
        if (z) {
            i = 8;
        } else {
            i = 9;
        }
        activityOptions.mAnimationType = i;
        activityOptions.mSpecsFuture = iAppTransitionAnimationSpecsFuture;
        activityOptions.setOnAnimationStartedListener(handler, onAnimationStartedListener);
        return activityOptions;
    }

    public static android.app.ActivityOptions makeThumbnailAspectScaleDownAnimation(android.view.View view, android.graphics.Bitmap bitmap, int i, int i2, int i3, int i4, android.os.Handler handler, android.app.ActivityOptions.OnAnimationStartedListener onAnimationStartedListener) {
        return makeAspectScaledThumbnailAnimation(view, bitmap, i, i2, i3, i4, handler, onAnimationStartedListener, false);
    }

    private static android.app.ActivityOptions makeAspectScaledThumbnailAnimation(android.view.View view, android.graphics.Bitmap bitmap, int i, int i2, int i3, int i4, android.os.Handler handler, android.app.ActivityOptions.OnAnimationStartedListener onAnimationStartedListener, boolean z) {
        android.app.ActivityOptions activityOptions = new android.app.ActivityOptions();
        activityOptions.mPackageName = view.getContext().getPackageName();
        activityOptions.mAnimationType = z ? 8 : 9;
        activityOptions.mThumbnail = bitmap;
        int[] iArr = new int[2];
        view.getLocationOnScreen(iArr);
        activityOptions.mStartX = iArr[0] + i;
        activityOptions.mStartY = iArr[1] + i2;
        activityOptions.mWidth = i3;
        activityOptions.mHeight = i4;
        activityOptions.setOnAnimationStartedListener(handler, onAnimationStartedListener);
        return activityOptions;
    }

    public static android.app.ActivityOptions makeThumbnailAspectScaleDownAnimation(android.view.View view, android.view.AppTransitionAnimationSpec[] appTransitionAnimationSpecArr, android.os.Handler handler, android.app.ActivityOptions.OnAnimationStartedListener onAnimationStartedListener, android.app.ActivityOptions.OnAnimationFinishedListener onAnimationFinishedListener) {
        android.app.ActivityOptions activityOptions = new android.app.ActivityOptions();
        activityOptions.mPackageName = view.getContext().getPackageName();
        activityOptions.mAnimationType = 9;
        activityOptions.mAnimSpecs = appTransitionAnimationSpecArr;
        activityOptions.setOnAnimationStartedListener(handler, onAnimationStartedListener);
        activityOptions.setOnAnimationFinishedListener(handler, onAnimationFinishedListener);
        return activityOptions;
    }

    public static android.app.ActivityOptions makeSceneTransitionAnimation(android.app.Activity activity, android.view.View view, java.lang.String str) {
        return makeSceneTransitionAnimation(activity, android.util.Pair.create(view, str));
    }

    @java.lang.SafeVarargs
    public static android.app.ActivityOptions makeSceneTransitionAnimation(android.app.Activity activity, android.util.Pair<android.view.View, java.lang.String>... pairArr) {
        android.app.ActivityOptions activityOptions = new android.app.ActivityOptions();
        android.app.ExitTransitionCoordinator makeSceneTransitionAnimation = makeSceneTransitionAnimation(new android.app.ExitTransitionCoordinator.ActivityExitTransitionCallbacks(activity), activity.mExitTransitionListener, activity.getWindow(), activityOptions, pairArr);
        android.app.ActivityOptions.SceneTransitionInfo sceneTransitionInfo = activityOptions.getSceneTransitionInfo();
        if (sceneTransitionInfo != null) {
            sceneTransitionInfo.setExitCoordinatorKey(activity.mActivityTransitionState.addExitTransitionCoordinator(makeSceneTransitionAnimation));
        }
        return activityOptions;
    }

    @java.lang.SafeVarargs
    public static android.util.Pair<android.app.ActivityOptions, android.app.ExitTransitionCoordinator> startSharedElementAnimation(android.view.Window window, android.app.ExitTransitionCoordinator.ExitTransitionCallbacks exitTransitionCallbacks, android.app.SharedElementCallback sharedElementCallback, android.util.Pair<android.view.View, java.lang.String>... pairArr) {
        android.app.ActivityOptions activityOptions = new android.app.ActivityOptions();
        android.app.ExitTransitionCoordinator makeSceneTransitionAnimation = makeSceneTransitionAnimation(exitTransitionCallbacks, sharedElementCallback, window, activityOptions, pairArr);
        android.app.ActivityOptions.SceneTransitionInfo sceneTransitionInfo = activityOptions.getSceneTransitionInfo();
        if (sceneTransitionInfo != null) {
            sceneTransitionInfo.setExitCoordinatorKey(-1);
        }
        return android.util.Pair.create(activityOptions, makeSceneTransitionAnimation);
    }

    public static void stopSharedElementAnimation(android.view.Window window) {
        android.app.ExitTransitionCoordinator exitTransitionCoordinator;
        android.view.View decorView = window.getDecorView();
        if (decorView != null && (exitTransitionCoordinator = (android.app.ExitTransitionCoordinator) decorView.getTag(com.android.internal.R.id.cross_task_transition)) != null) {
            exitTransitionCoordinator.cancelPendingTransitions();
            decorView.setTagInternal(com.android.internal.R.id.cross_task_transition, null);
            android.transition.TransitionManager.endTransitions((android.view.ViewGroup) decorView);
            exitTransitionCoordinator.resetViews();
            exitTransitionCoordinator.clearState();
            decorView.setVisibility(0);
        }
    }

    static android.app.ExitTransitionCoordinator makeSceneTransitionAnimation(android.app.ExitTransitionCoordinator.ExitTransitionCallbacks exitTransitionCallbacks, android.app.SharedElementCallback sharedElementCallback, android.view.Window window, android.app.ActivityOptions activityOptions, android.util.Pair<android.view.View, java.lang.String>[] pairArr) {
        if (!window.hasFeature(13)) {
            activityOptions.mAnimationType = 6;
            return null;
        }
        activityOptions.mAnimationType = 5;
        java.util.ArrayList<java.lang.String> arrayList = new java.util.ArrayList<>();
        java.util.ArrayList arrayList2 = new java.util.ArrayList();
        if (pairArr != null) {
            for (android.util.Pair<android.view.View, java.lang.String> pair : pairArr) {
                java.lang.String str = pair.second;
                if (str == null) {
                    throw new java.lang.IllegalArgumentException("Shared element name must not be null");
                }
                arrayList.add(str);
                if (pair.first == null) {
                    throw new java.lang.IllegalArgumentException("Shared element must not be null");
                }
                arrayList2.add(pair.first);
            }
        }
        android.app.ExitTransitionCoordinator exitTransitionCoordinator = new android.app.ExitTransitionCoordinator(exitTransitionCallbacks, window, sharedElementCallback, arrayList, arrayList, arrayList2, false);
        android.app.ActivityOptions.SceneTransitionInfo sceneTransitionInfo = new android.app.ActivityOptions.SceneTransitionInfo();
        sceneTransitionInfo.setResultReceiver(exitTransitionCoordinator);
        sceneTransitionInfo.setSharedElementNames(arrayList);
        sceneTransitionInfo.setReturning(false);
        activityOptions.setSceneTransitionInfo(sceneTransitionInfo);
        return exitTransitionCoordinator;
    }

    public static void setExitTransitionTimeout(long j) {
        android.app.ExitTransitionCoordinator.sMaxWaitMillis = j;
    }

    static android.app.ActivityOptions makeSceneTransitionAnimation(android.app.Activity activity, android.app.ExitTransitionCoordinator exitTransitionCoordinator, java.util.ArrayList<java.lang.String> arrayList, int i, android.content.Intent intent) {
        android.app.ActivityOptions activityOptions = new android.app.ActivityOptions();
        activityOptions.mAnimationType = 5;
        android.app.ActivityOptions.SceneTransitionInfo sceneTransitionInfo = new android.app.ActivityOptions.SceneTransitionInfo();
        sceneTransitionInfo.setSharedElementNames(arrayList);
        sceneTransitionInfo.setResultReceiver(exitTransitionCoordinator);
        sceneTransitionInfo.setReturning(true);
        sceneTransitionInfo.setResultCode(i);
        sceneTransitionInfo.setResultData(intent);
        if (activity == null) {
            sceneTransitionInfo.setExitCoordinatorKey(-1);
        } else {
            sceneTransitionInfo.setExitCoordinatorKey(activity.mActivityTransitionState.addExitTransitionCoordinator(exitTransitionCoordinator));
        }
        activityOptions.setSceneTransitionInfo(sceneTransitionInfo);
        return activityOptions;
    }

    public static android.app.ActivityOptions makeTaskLaunchBehind() {
        android.app.ActivityOptions activityOptions = new android.app.ActivityOptions();
        activityOptions.mAnimationType = 7;
        return activityOptions;
    }

    public static android.app.ActivityOptions makeBasic() {
        return new android.app.ActivityOptions();
    }

    public static android.app.ActivityOptions makeRemoteAnimation(android.view.RemoteAnimationAdapter remoteAnimationAdapter) {
        android.app.ActivityOptions activityOptions = new android.app.ActivityOptions();
        activityOptions.mRemoteAnimationAdapter = remoteAnimationAdapter;
        activityOptions.mAnimationType = 13;
        return activityOptions;
    }

    public static android.app.ActivityOptions makeRemoteAnimation(android.view.RemoteAnimationAdapter remoteAnimationAdapter, android.window.RemoteTransition remoteTransition) {
        android.app.ActivityOptions activityOptions = new android.app.ActivityOptions();
        activityOptions.mRemoteAnimationAdapter = remoteAnimationAdapter;
        activityOptions.mAnimationType = 13;
        activityOptions.mRemoteTransition = remoteTransition;
        return activityOptions;
    }

    public static android.app.ActivityOptions makeRemoteTransition(android.window.RemoteTransition remoteTransition) {
        android.app.ActivityOptions activityOptions = new android.app.ActivityOptions();
        activityOptions.mRemoteTransition = remoteTransition;
        return activityOptions;
    }

    public static android.app.ActivityOptions makeLaunchIntoPip(android.app.PictureInPictureParams pictureInPictureParams) {
        android.app.ActivityOptions activityOptions = new android.app.ActivityOptions();
        activityOptions.mLaunchIntoPipParams = new android.app.PictureInPictureParams.Builder(pictureInPictureParams).setIsLaunchIntoPip(true).build();
        return activityOptions;
    }

    public boolean getLaunchTaskBehind() {
        return this.mAnimationType == 7;
    }

    private ActivityOptions() {
        this.mAnimationType = -1;
        this.mLaunchDisplayId = -1;
        this.mCallerDisplayId = -1;
        this.mLaunchTaskDisplayAreaFeatureId = -1;
        this.mLaunchWindowingMode = 0;
        this.mLaunchActivityType = 0;
        this.mLaunchTaskId = -1;
        this.mLockTaskMode = false;
        this.mShareIdentity = false;
        this.mRotationAnimationHint = -1;
        this.mSplashScreenStyle = -1;
        this.mPendingIntentCreatorBackgroundActivityStartMode = 0;
    }

    public ActivityOptions(android.os.Bundle bundle) {
        super(bundle);
        this.mAnimationType = -1;
        this.mLaunchDisplayId = -1;
        this.mCallerDisplayId = -1;
        this.mLaunchTaskDisplayAreaFeatureId = -1;
        this.mLaunchWindowingMode = 0;
        this.mLaunchActivityType = 0;
        this.mLaunchTaskId = -1;
        this.mLockTaskMode = false;
        this.mShareIdentity = false;
        this.mRotationAnimationHint = -1;
        this.mSplashScreenStyle = -1;
        this.mPendingIntentCreatorBackgroundActivityStartMode = 0;
        this.mPackageName = bundle.getString(KEY_PACKAGE_NAME);
        try {
            this.mUsageTimeReport = (android.app.PendingIntent) bundle.getParcelable(KEY_USAGE_TIME_REPORT, android.app.PendingIntent.class);
        } catch (java.lang.RuntimeException e) {
            android.util.Slog.w(TAG, e);
        }
        this.mLaunchBounds = (android.graphics.Rect) bundle.getParcelable(KEY_LAUNCH_BOUNDS, android.graphics.Rect.class);
        this.mAnimationType = bundle.getInt(KEY_ANIM_TYPE, -1);
        switch (this.mAnimationType) {
            case 1:
                this.mCustomEnterResId = bundle.getInt(KEY_ANIM_ENTER_RES_ID, 0);
                this.mCustomExitResId = bundle.getInt(KEY_ANIM_EXIT_RES_ID, 0);
                this.mCustomBackgroundColor = bundle.getInt(KEY_ANIM_BACKGROUND_COLOR, 0);
                this.mAnimationStartedListener = android.os.IRemoteCallback.Stub.asInterface(bundle.getBinder(KEY_ANIM_START_LISTENER));
                break;
            case 2:
            case 11:
                this.mStartX = bundle.getInt(KEY_ANIM_START_X, 0);
                this.mStartY = bundle.getInt(KEY_ANIM_START_Y, 0);
                this.mWidth = bundle.getInt(KEY_ANIM_WIDTH, 0);
                this.mHeight = bundle.getInt(KEY_ANIM_HEIGHT, 0);
                break;
            case 3:
            case 4:
            case 8:
            case 9:
                android.hardware.HardwareBuffer hardwareBuffer = (android.hardware.HardwareBuffer) bundle.getParcelable(KEY_ANIM_THUMBNAIL, android.hardware.HardwareBuffer.class);
                if (hardwareBuffer != null) {
                    this.mThumbnail = android.graphics.Bitmap.wrapHardwareBuffer(hardwareBuffer, null);
                }
                this.mStartX = bundle.getInt(KEY_ANIM_START_X, 0);
                this.mStartY = bundle.getInt(KEY_ANIM_START_Y, 0);
                this.mWidth = bundle.getInt(KEY_ANIM_WIDTH, 0);
                this.mHeight = bundle.getInt(KEY_ANIM_HEIGHT, 0);
                this.mAnimationStartedListener = android.os.IRemoteCallback.Stub.asInterface(bundle.getBinder(KEY_ANIM_START_LISTENER));
                break;
            case 5:
                this.mSceneTransitionInfo = (android.app.ActivityOptions.SceneTransitionInfo) bundle.getParcelable(KEY_SCENE_TRANSITION_INFO, android.app.ActivityOptions.SceneTransitionInfo.class);
                break;
            case 10:
                this.mCustomInPlaceResId = bundle.getInt(KEY_ANIM_IN_PLACE_RES_ID, 0);
                break;
        }
        this.mLockTaskMode = bundle.getBoolean(KEY_LOCK_TASK_MODE, false);
        this.mShareIdentity = bundle.getBoolean(KEY_SHARE_IDENTITY, false);
        this.mLaunchDisplayId = bundle.getInt(KEY_LAUNCH_DISPLAY_ID, -1);
        this.mCallerDisplayId = bundle.getInt(KEY_CALLER_DISPLAY_ID, -1);
        this.mLaunchTaskDisplayArea = (android.window.WindowContainerToken) bundle.getParcelable(KEY_LAUNCH_TASK_DISPLAY_AREA_TOKEN, android.window.WindowContainerToken.class);
        this.mLaunchTaskDisplayAreaFeatureId = bundle.getInt(KEY_LAUNCH_TASK_DISPLAY_AREA_FEATURE_ID, -1);
        this.mLaunchRootTask = (android.window.WindowContainerToken) bundle.getParcelable(KEY_LAUNCH_ROOT_TASK_TOKEN, android.window.WindowContainerToken.class);
        this.mLaunchTaskFragmentToken = bundle.getBinder(KEY_LAUNCH_TASK_FRAGMENT_TOKEN);
        this.mLaunchWindowingMode = bundle.getInt(KEY_LAUNCH_WINDOWING_MODE, 0);
        this.mLaunchActivityType = bundle.getInt(KEY_LAUNCH_ACTIVITY_TYPE, 0);
        this.mLaunchTaskId = bundle.getInt(KEY_LAUNCH_TASK_ID, -1);
        this.mPendingIntentLaunchFlags = bundle.getInt(KEY_PENDING_INTENT_LAUNCH_FLAGS, 0);
        this.mTaskAlwaysOnTop = bundle.getBoolean(KEY_TASK_ALWAYS_ON_TOP, false);
        this.mTaskOverlay = bundle.getBoolean(KEY_TASK_OVERLAY, false);
        this.mTaskOverlayCanResume = bundle.getBoolean(KEY_TASK_OVERLAY_CAN_RESUME, false);
        this.mAvoidMoveToFront = bundle.getBoolean(KEY_AVOID_MOVE_TO_FRONT, false);
        this.mFreezeRecentTasksReordering = bundle.getBoolean(KEY_FREEZE_RECENT_TASKS_REORDERING, false);
        this.mDisallowEnterPictureInPictureWhileLaunching = bundle.getBoolean(KEY_DISALLOW_ENTER_PICTURE_IN_PICTURE_WHILE_LAUNCHING, false);
        this.mApplyActivityFlagsForBubbles = bundle.getBoolean(KEY_APPLY_ACTIVITY_FLAGS_FOR_BUBBLES, false);
        this.mApplyMultipleTaskFlagForShortcut = bundle.getBoolean(KEY_APPLY_MULTIPLE_TASK_FLAG_FOR_SHORTCUT, false);
        this.mApplyNoUserActionFlagForShortcut = bundle.getBoolean(KEY_APPLY_NO_USER_ACTION_FLAG_FOR_SHORTCUT, false);
        if (bundle.containsKey(KEY_ANIM_SPECS)) {
            android.os.Parcelable[] parcelableArray = bundle.getParcelableArray(KEY_ANIM_SPECS);
            this.mAnimSpecs = new android.view.AppTransitionAnimationSpec[parcelableArray.length];
            for (int length = parcelableArray.length - 1; length >= 0; length--) {
                this.mAnimSpecs[length] = (android.view.AppTransitionAnimationSpec) parcelableArray[length];
            }
        }
        if (bundle.containsKey(KEY_ANIMATION_FINISHED_LISTENER)) {
            this.mAnimationFinishedListener = android.os.IRemoteCallback.Stub.asInterface(bundle.getBinder(KEY_ANIMATION_FINISHED_LISTENER));
        }
        this.mSourceInfo = (android.app.ActivityOptions.SourceInfo) bundle.getParcelable(KEY_SOURCE_INFO, android.app.ActivityOptions.SourceInfo.class);
        this.mRotationAnimationHint = bundle.getInt(KEY_ROTATION_ANIMATION_HINT, -1);
        this.mAppVerificationBundle = bundle.getBundle(KEY_INSTANT_APP_VERIFICATION_BUNDLE);
        if (bundle.containsKey(KEY_SPECS_FUTURE)) {
            this.mSpecsFuture = android.view.IAppTransitionAnimationSpecsFuture.Stub.asInterface(bundle.getBinder(KEY_SPECS_FUTURE));
        }
        this.mRemoteAnimationAdapter = (android.view.RemoteAnimationAdapter) bundle.getParcelable(KEY_REMOTE_ANIMATION_ADAPTER, android.view.RemoteAnimationAdapter.class);
        this.mLaunchCookie = bundle.getBinder(KEY_LAUNCH_COOKIE);
        this.mRemoteTransition = (android.window.RemoteTransition) bundle.getParcelable(KEY_REMOTE_TRANSITION, android.window.RemoteTransition.class);
        this.mOverrideTaskTransition = bundle.getBoolean(KEY_OVERRIDE_TASK_TRANSITION);
        this.mSplashScreenThemeResName = bundle.getString(KEY_SPLASH_SCREEN_THEME);
        this.mRemoveWithTaskOrganizer = bundle.getBoolean(KEY_REMOVE_WITH_TASK_ORGANIZER);
        this.mLaunchedFromBubble = bundle.getBoolean(KEY_LAUNCHED_FROM_BUBBLE);
        this.mTransientLaunch = bundle.getBoolean(KEY_TRANSIENT_LAUNCH);
        this.mSplashScreenStyle = bundle.getInt(KEY_SPLASH_SCREEN_STYLE);
        this.mLaunchIntoPipParams = (android.app.PictureInPictureParams) bundle.getParcelable(KEY_LAUNCH_INTO_PIP_PARAMS, android.app.PictureInPictureParams.class);
        this.mIsEligibleForLegacyPermissionPrompt = bundle.getBoolean(KEY_LEGACY_PERMISSION_PROMPT_ELIGIBLE);
        this.mDismissKeyguardIfInsecure = bundle.getBoolean(KEY_DISMISS_KEYGUARD_IF_INSECURE);
        this.mPendingIntentCreatorBackgroundActivityStartMode = bundle.getInt(KEY_PENDING_INTENT_CREATOR_BACKGROUND_ACTIVITY_START_MODE, 0);
        this.mDisableStartingWindow = bundle.getBoolean(KEY_DISABLE_STARTING_WINDOW);
        this.mAnimationAbortListener = android.os.IRemoteCallback.Stub.asInterface(bundle.getBinder(KEY_ANIM_ABORT_LISTENER));
    }

    public android.app.ActivityOptions setLaunchBounds(android.graphics.Rect rect) {
        this.mLaunchBounds = rect != null ? new android.graphics.Rect(rect) : null;
        return this;
    }

    public java.lang.String getPackageName() {
        return this.mPackageName;
    }

    public android.graphics.Rect getLaunchBounds() {
        return this.mLaunchBounds;
    }

    public int getAnimationType() {
        return this.mAnimationType;
    }

    public int getCustomEnterResId() {
        return this.mCustomEnterResId;
    }

    public int getCustomExitResId() {
        return this.mCustomExitResId;
    }

    public int getCustomInPlaceResId() {
        return this.mCustomInPlaceResId;
    }

    public int getCustomBackgroundColor() {
        return this.mCustomBackgroundColor;
    }

    public android.hardware.HardwareBuffer getThumbnail() {
        if (this.mThumbnail != null) {
            return this.mThumbnail.getHardwareBuffer();
        }
        return null;
    }

    public int getStartX() {
        return this.mStartX;
    }

    public int getStartY() {
        return this.mStartY;
    }

    public int getWidth() {
        return this.mWidth;
    }

    public int getHeight() {
        return this.mHeight;
    }

    public android.os.IRemoteCallback getAnimationStartedListener() {
        return this.mAnimationStartedListener;
    }

    public android.os.IRemoteCallback getAnimationFinishedListener() {
        return this.mAnimationFinishedListener;
    }

    public void abort() {
        sendResultIgnoreErrors(this.mAnimationStartedListener, null);
        sendResultIgnoreErrors(this.mAnimationAbortListener, null);
    }

    private void sendResultIgnoreErrors(android.os.IRemoteCallback iRemoteCallback, android.os.Bundle bundle) {
        if (iRemoteCallback != null) {
            try {
                iRemoteCallback.sendResult(bundle);
            } catch (android.os.RemoteException e) {
            }
        }
    }

    public android.app.ActivityOptions setSceneTransitionInfo(android.app.ActivityOptions.SceneTransitionInfo sceneTransitionInfo) {
        this.mSceneTransitionInfo = sceneTransitionInfo;
        return this;
    }

    public android.app.ActivityOptions.SceneTransitionInfo getSceneTransitionInfo() {
        return this.mSceneTransitionInfo;
    }

    public android.app.PendingIntent getUsageTimeReport() {
        return this.mUsageTimeReport;
    }

    public android.view.AppTransitionAnimationSpec[] getAnimSpecs() {
        return this.mAnimSpecs;
    }

    public android.view.IAppTransitionAnimationSpecsFuture getSpecsFuture() {
        return this.mSpecsFuture;
    }

    public android.view.RemoteAnimationAdapter getRemoteAnimationAdapter() {
        return this.mRemoteAnimationAdapter;
    }

    public void setRemoteAnimationAdapter(android.view.RemoteAnimationAdapter remoteAnimationAdapter) {
        this.mRemoteAnimationAdapter = remoteAnimationAdapter;
    }

    public android.window.RemoteTransition getRemoteTransition() {
        return this.mRemoteTransition;
    }

    public android.app.ActivityOptions setRemoteTransition(android.window.RemoteTransition remoteTransition) {
        this.mRemoteTransition = remoteTransition;
        return this;
    }

    public static android.app.ActivityOptions fromBundle(android.os.Bundle bundle) {
        if (bundle != null) {
            return new android.app.ActivityOptions(bundle);
        }
        return null;
    }

    public static void abort(android.app.ActivityOptions activityOptions) {
        if (activityOptions != null) {
            activityOptions.abort();
        }
    }

    public boolean getLockTaskMode() {
        return this.mLockTaskMode;
    }

    public boolean isShareIdentityEnabled() {
        return this.mShareIdentity;
    }

    public java.lang.String getSplashScreenThemeResName() {
        return this.mSplashScreenThemeResName;
    }

    public int getSplashScreenStyle() {
        return this.mSplashScreenStyle;
    }

    public android.app.ActivityOptions setSplashScreenStyle(int i) {
        if (i == 1 || i == 0) {
            this.mSplashScreenStyle = i;
        }
        return this;
    }

    public boolean isEligibleForLegacyPermissionPrompt() {
        return this.mIsEligibleForLegacyPermissionPrompt;
    }

    public void setEligibleForLegacyPermissionPrompt(boolean z) {
        this.mIsEligibleForLegacyPermissionPrompt = z;
    }

    public android.app.ActivityOptions setLockTaskEnabled(boolean z) {
        this.mLockTaskMode = z;
        return this;
    }

    public android.app.ActivityOptions setShareIdentityEnabled(boolean z) {
        this.mShareIdentity = z;
        return this;
    }

    public int getLaunchDisplayId() {
        return this.mLaunchDisplayId;
    }

    public android.app.ActivityOptions setLaunchDisplayId(int i) {
        this.mLaunchDisplayId = i;
        return this;
    }

    public int getCallerDisplayId() {
        return this.mCallerDisplayId;
    }

    public android.app.ActivityOptions setCallerDisplayId(int i) {
        this.mCallerDisplayId = i;
        return this;
    }

    public android.window.WindowContainerToken getLaunchTaskDisplayArea() {
        return this.mLaunchTaskDisplayArea;
    }

    public android.app.ActivityOptions setLaunchTaskDisplayArea(android.window.WindowContainerToken windowContainerToken) {
        this.mLaunchTaskDisplayArea = windowContainerToken;
        return this;
    }

    public int getLaunchTaskDisplayAreaFeatureId() {
        return this.mLaunchTaskDisplayAreaFeatureId;
    }

    public void setLaunchTaskDisplayAreaFeatureId(int i) {
        this.mLaunchTaskDisplayAreaFeatureId = i;
    }

    public android.window.WindowContainerToken getLaunchRootTask() {
        return this.mLaunchRootTask;
    }

    public android.app.ActivityOptions setLaunchRootTask(android.window.WindowContainerToken windowContainerToken) {
        this.mLaunchRootTask = windowContainerToken;
        return this;
    }

    public android.os.IBinder getLaunchTaskFragmentToken() {
        return this.mLaunchTaskFragmentToken;
    }

    public android.app.ActivityOptions setLaunchTaskFragmentToken(android.os.IBinder iBinder) {
        this.mLaunchTaskFragmentToken = iBinder;
        return this;
    }

    public int getLaunchWindowingMode() {
        return this.mLaunchWindowingMode;
    }

    public void setLaunchWindowingMode(int i) {
        this.mLaunchWindowingMode = i;
    }

    public android.app.PictureInPictureParams getLaunchIntoPipParams() {
        return this.mLaunchIntoPipParams;
    }

    public boolean isLaunchIntoPip() {
        return this.mLaunchIntoPipParams != null && this.mLaunchIntoPipParams.isLaunchIntoPip();
    }

    public int getLaunchActivityType() {
        return this.mLaunchActivityType;
    }

    public void setLaunchActivityType(int i) {
        this.mLaunchActivityType = i;
    }

    @android.annotation.SystemApi
    public void setLaunchTaskId(int i) {
        this.mLaunchTaskId = i;
    }

    @android.annotation.SystemApi
    public int getLaunchTaskId() {
        return this.mLaunchTaskId;
    }

    public void setDisableStartingWindow(boolean z) {
        this.mDisableStartingWindow = z;
    }

    public boolean getDisableStartingWindow() {
        return this.mDisableStartingWindow;
    }

    public void setPendingIntentLaunchFlags(int i) {
        this.mPendingIntentLaunchFlags = i;
    }

    public int getPendingIntentLaunchFlags() {
        return this.mPendingIntentLaunchFlags & 402653184;
    }

    public void setTaskAlwaysOnTop(boolean z) {
        this.mTaskAlwaysOnTop = z;
    }

    public boolean getTaskAlwaysOnTop() {
        return this.mTaskAlwaysOnTop;
    }

    public void setTaskOverlay(boolean z, boolean z2) {
        this.mTaskOverlay = z;
        this.mTaskOverlayCanResume = z2;
    }

    public boolean getTaskOverlay() {
        return this.mTaskOverlay;
    }

    public boolean canTaskOverlayResume() {
        return this.mTaskOverlayCanResume;
    }

    public void setAvoidMoveToFront() {
        this.mAvoidMoveToFront = true;
    }

    public boolean getAvoidMoveToFront() {
        return this.mAvoidMoveToFront;
    }

    public void setFreezeRecentTasksReordering() {
        this.mFreezeRecentTasksReordering = true;
    }

    public boolean freezeRecentTasksReordering() {
        return this.mFreezeRecentTasksReordering;
    }

    public void setSplitScreenCreateMode(int i) {
    }

    public void setDisallowEnterPictureInPictureWhileLaunching(boolean z) {
        this.mDisallowEnterPictureInPictureWhileLaunching = z;
    }

    public boolean disallowEnterPictureInPictureWhileLaunching() {
        return this.mDisallowEnterPictureInPictureWhileLaunching;
    }

    public void setApplyActivityFlagsForBubbles(boolean z) {
        this.mApplyActivityFlagsForBubbles = z;
    }

    public boolean isApplyActivityFlagsForBubbles() {
        return this.mApplyActivityFlagsForBubbles;
    }

    public void setApplyMultipleTaskFlagForShortcut(boolean z) {
        this.mApplyMultipleTaskFlagForShortcut = z;
    }

    public boolean isApplyMultipleTaskFlagForShortcut() {
        return this.mApplyMultipleTaskFlagForShortcut;
    }

    public void setApplyNoUserActionFlagForShortcut(boolean z) {
        this.mApplyNoUserActionFlagForShortcut = z;
    }

    public boolean isApplyNoUserActionFlagForShortcut() {
        return this.mApplyNoUserActionFlagForShortcut;
    }

    public static final class LaunchCookie implements android.os.Parcelable {
        public static final android.os.Parcelable.Creator<android.app.ActivityOptions.LaunchCookie> CREATOR = new android.os.Parcelable.Creator<android.app.ActivityOptions.LaunchCookie>() { // from class: android.app.ActivityOptions.LaunchCookie.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.app.ActivityOptions.LaunchCookie createFromParcel(android.os.Parcel parcel) {
                return android.app.ActivityOptions.LaunchCookie.readFromParcel(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.app.ActivityOptions.LaunchCookie[] newArray(int i) {
                return new android.app.ActivityOptions.LaunchCookie[i];
            }
        };
        public final android.os.IBinder binder;

        public LaunchCookie() {
            this.binder = new android.os.Binder();
        }

        public LaunchCookie(java.lang.String str) {
            this.binder = new android.os.Binder(str);
        }

        private LaunchCookie(android.os.IBinder iBinder) {
            this.binder = iBinder;
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(android.os.Parcel parcel, int i) {
            parcel.writeStrongBinder(this.binder);
        }

        public static android.app.ActivityOptions.LaunchCookie readFromParcel(android.os.Parcel parcel) {
            android.os.IBinder readStrongBinder = parcel.readStrongBinder();
            if (readStrongBinder == null) {
                return null;
            }
            return new android.app.ActivityOptions.LaunchCookie(readStrongBinder);
        }

        public static void writeToParcel(android.app.ActivityOptions.LaunchCookie launchCookie, android.os.Parcel parcel) {
            if (launchCookie != null) {
                launchCookie.writeToParcel(parcel, 0);
            } else {
                parcel.writeStrongBinder(null);
            }
        }

        public boolean equals(java.lang.Object obj) {
            return (obj instanceof android.app.ActivityOptions.LaunchCookie) && this.binder == ((android.app.ActivityOptions.LaunchCookie) obj).binder;
        }

        public int hashCode() {
            return this.binder.hashCode();
        }
    }

    public void setLaunchCookie(android.app.ActivityOptions.LaunchCookie launchCookie) {
        setLaunchCookie(launchCookie.binder);
    }

    public void setLaunchCookie(android.os.IBinder iBinder) {
        this.mLaunchCookie = iBinder;
    }

    public android.os.IBinder getLaunchCookie() {
        return this.mLaunchCookie;
    }

    public boolean getOverrideTaskTransition() {
        return this.mOverrideTaskTransition;
    }

    public void setRemoveWithTaskOrganizer(boolean z) {
        this.mRemoveWithTaskOrganizer = z;
    }

    public boolean getRemoveWithTaskOranizer() {
        return this.mRemoveWithTaskOrganizer;
    }

    public void setLaunchedFromBubble(boolean z) {
        this.mLaunchedFromBubble = z;
    }

    public boolean getLaunchedFromBubble() {
        return this.mLaunchedFromBubble;
    }

    public android.app.ActivityOptions setTransientLaunch() {
        this.mTransientLaunch = true;
        return this;
    }

    public boolean getTransientLaunch() {
        return this.mTransientLaunch;
    }

    public void setDismissKeyguardIfInsecure() {
        this.mDismissKeyguardIfInsecure = true;
    }

    public boolean getDismissKeyguardIfInsecure() {
        return this.mDismissKeyguardIfInsecure;
    }

    @java.lang.Deprecated
    public android.app.ActivityOptions setIgnorePendingIntentCreatorForegroundState(boolean z) {
        this.mPendingIntentCreatorBackgroundActivityStartMode = z ? 2 : 1;
        return this;
    }

    public android.app.ActivityOptions setPendingIntentCreatorBackgroundActivityStartMode(int i) {
        this.mPendingIntentCreatorBackgroundActivityStartMode = i;
        return this;
    }

    public int getPendingIntentCreatorBackgroundActivityStartMode() {
        return this.mPendingIntentCreatorBackgroundActivityStartMode;
    }

    public void update(android.app.ActivityOptions activityOptions) {
        if (activityOptions.mPackageName != null) {
            this.mPackageName = activityOptions.mPackageName;
        }
        this.mUsageTimeReport = activityOptions.mUsageTimeReport;
        this.mSceneTransitionInfo = null;
        this.mAnimationType = activityOptions.mAnimationType;
        switch (activityOptions.mAnimationType) {
            case 1:
                this.mCustomEnterResId = activityOptions.mCustomEnterResId;
                this.mCustomExitResId = activityOptions.mCustomExitResId;
                this.mCustomBackgroundColor = activityOptions.mCustomBackgroundColor;
                this.mThumbnail = null;
                sendResultIgnoreErrors(this.mAnimationStartedListener, null);
                this.mAnimationStartedListener = activityOptions.mAnimationStartedListener;
                break;
            case 2:
                this.mStartX = activityOptions.mStartX;
                this.mStartY = activityOptions.mStartY;
                this.mWidth = activityOptions.mWidth;
                this.mHeight = activityOptions.mHeight;
                sendResultIgnoreErrors(this.mAnimationStartedListener, null);
                this.mAnimationStartedListener = null;
                break;
            case 3:
            case 4:
            case 8:
            case 9:
                this.mThumbnail = activityOptions.mThumbnail;
                this.mStartX = activityOptions.mStartX;
                this.mStartY = activityOptions.mStartY;
                this.mWidth = activityOptions.mWidth;
                this.mHeight = activityOptions.mHeight;
                sendResultIgnoreErrors(this.mAnimationStartedListener, null);
                this.mAnimationStartedListener = activityOptions.mAnimationStartedListener;
                break;
            case 5:
                this.mSceneTransitionInfo = activityOptions.mSceneTransitionInfo;
                this.mThumbnail = null;
                this.mAnimationStartedListener = null;
                break;
            case 10:
                this.mCustomInPlaceResId = activityOptions.mCustomInPlaceResId;
                break;
        }
        this.mLockTaskMode = activityOptions.mLockTaskMode;
        this.mShareIdentity = activityOptions.mShareIdentity;
        this.mAnimSpecs = activityOptions.mAnimSpecs;
        this.mAnimationFinishedListener = activityOptions.mAnimationFinishedListener;
        this.mSpecsFuture = activityOptions.mSpecsFuture;
        this.mRemoteAnimationAdapter = activityOptions.mRemoteAnimationAdapter;
        this.mLaunchIntoPipParams = activityOptions.mLaunchIntoPipParams;
        this.mIsEligibleForLegacyPermissionPrompt = activityOptions.mIsEligibleForLegacyPermissionPrompt;
        sendResultIgnoreErrors(this.mAnimationAbortListener, null);
        this.mAnimationAbortListener = activityOptions.mAnimationAbortListener;
    }

    @Override // android.app.ComponentOptions
    public android.os.Bundle toBundle() {
        android.os.Bundle bundle = super.toBundle();
        if (this.mPackageName != null) {
            bundle.putString(KEY_PACKAGE_NAME, this.mPackageName);
        }
        if (this.mLaunchBounds != null) {
            bundle.putParcelable(KEY_LAUNCH_BOUNDS, this.mLaunchBounds);
        }
        if (this.mAnimationType != -1) {
            bundle.putInt(KEY_ANIM_TYPE, this.mAnimationType);
        }
        if (this.mUsageTimeReport != null) {
            bundle.putParcelable(KEY_USAGE_TIME_REPORT, this.mUsageTimeReport);
        }
        switch (this.mAnimationType) {
            case 1:
                bundle.putInt(KEY_ANIM_ENTER_RES_ID, this.mCustomEnterResId);
                bundle.putInt(KEY_ANIM_EXIT_RES_ID, this.mCustomExitResId);
                bundle.putInt(KEY_ANIM_BACKGROUND_COLOR, this.mCustomBackgroundColor);
                bundle.putBinder(KEY_ANIM_START_LISTENER, this.mAnimationStartedListener != null ? this.mAnimationStartedListener.asBinder() : null);
                break;
            case 2:
            case 11:
                bundle.putInt(KEY_ANIM_START_X, this.mStartX);
                bundle.putInt(KEY_ANIM_START_Y, this.mStartY);
                bundle.putInt(KEY_ANIM_WIDTH, this.mWidth);
                bundle.putInt(KEY_ANIM_HEIGHT, this.mHeight);
                break;
            case 3:
            case 4:
            case 8:
            case 9:
                if (this.mThumbnail != null) {
                    android.graphics.Bitmap copy = this.mThumbnail.copy(android.graphics.Bitmap.Config.HARDWARE, false);
                    if (copy != null) {
                        bundle.putParcelable(KEY_ANIM_THUMBNAIL, copy.getHardwareBuffer());
                    } else {
                        android.util.Slog.w(TAG, "Failed to copy thumbnail");
                    }
                }
                bundle.putInt(KEY_ANIM_START_X, this.mStartX);
                bundle.putInt(KEY_ANIM_START_Y, this.mStartY);
                bundle.putInt(KEY_ANIM_WIDTH, this.mWidth);
                bundle.putInt(KEY_ANIM_HEIGHT, this.mHeight);
                bundle.putBinder(KEY_ANIM_START_LISTENER, this.mAnimationStartedListener != null ? this.mAnimationStartedListener.asBinder() : null);
                break;
            case 5:
                if (this.mSceneTransitionInfo != null) {
                    bundle.putParcelable(KEY_SCENE_TRANSITION_INFO, this.mSceneTransitionInfo);
                    break;
                }
                break;
            case 10:
                bundle.putInt(KEY_ANIM_IN_PLACE_RES_ID, this.mCustomInPlaceResId);
                break;
        }
        if (this.mLockTaskMode) {
            bundle.putBoolean(KEY_LOCK_TASK_MODE, this.mLockTaskMode);
        }
        if (this.mShareIdentity) {
            bundle.putBoolean(KEY_SHARE_IDENTITY, this.mShareIdentity);
        }
        if (this.mLaunchDisplayId != -1) {
            bundle.putInt(KEY_LAUNCH_DISPLAY_ID, this.mLaunchDisplayId);
        }
        if (this.mCallerDisplayId != -1) {
            bundle.putInt(KEY_CALLER_DISPLAY_ID, this.mCallerDisplayId);
        }
        if (this.mLaunchTaskDisplayArea != null) {
            bundle.putParcelable(KEY_LAUNCH_TASK_DISPLAY_AREA_TOKEN, this.mLaunchTaskDisplayArea);
        }
        if (this.mLaunchTaskDisplayAreaFeatureId != -1) {
            bundle.putInt(KEY_LAUNCH_TASK_DISPLAY_AREA_FEATURE_ID, this.mLaunchTaskDisplayAreaFeatureId);
        }
        if (this.mLaunchRootTask != null) {
            bundle.putParcelable(KEY_LAUNCH_ROOT_TASK_TOKEN, this.mLaunchRootTask);
        }
        if (this.mLaunchTaskFragmentToken != null) {
            bundle.putBinder(KEY_LAUNCH_TASK_FRAGMENT_TOKEN, this.mLaunchTaskFragmentToken);
        }
        if (this.mLaunchWindowingMode != 0) {
            bundle.putInt(KEY_LAUNCH_WINDOWING_MODE, this.mLaunchWindowingMode);
        }
        if (this.mLaunchActivityType != 0) {
            bundle.putInt(KEY_LAUNCH_ACTIVITY_TYPE, this.mLaunchActivityType);
        }
        if (this.mLaunchTaskId != -1) {
            bundle.putInt(KEY_LAUNCH_TASK_ID, this.mLaunchTaskId);
        }
        if (this.mPendingIntentLaunchFlags != 0) {
            bundle.putInt(KEY_PENDING_INTENT_LAUNCH_FLAGS, this.mPendingIntentLaunchFlags);
        }
        if (this.mTaskAlwaysOnTop) {
            bundle.putBoolean(KEY_TASK_ALWAYS_ON_TOP, this.mTaskAlwaysOnTop);
        }
        if (this.mTaskOverlay) {
            bundle.putBoolean(KEY_TASK_OVERLAY, this.mTaskOverlay);
        }
        if (this.mTaskOverlayCanResume) {
            bundle.putBoolean(KEY_TASK_OVERLAY_CAN_RESUME, this.mTaskOverlayCanResume);
        }
        if (this.mAvoidMoveToFront) {
            bundle.putBoolean(KEY_AVOID_MOVE_TO_FRONT, this.mAvoidMoveToFront);
        }
        if (this.mFreezeRecentTasksReordering) {
            bundle.putBoolean(KEY_FREEZE_RECENT_TASKS_REORDERING, this.mFreezeRecentTasksReordering);
        }
        if (this.mDisallowEnterPictureInPictureWhileLaunching) {
            bundle.putBoolean(KEY_DISALLOW_ENTER_PICTURE_IN_PICTURE_WHILE_LAUNCHING, this.mDisallowEnterPictureInPictureWhileLaunching);
        }
        if (this.mApplyActivityFlagsForBubbles) {
            bundle.putBoolean(KEY_APPLY_ACTIVITY_FLAGS_FOR_BUBBLES, this.mApplyActivityFlagsForBubbles);
        }
        if (this.mApplyMultipleTaskFlagForShortcut) {
            bundle.putBoolean(KEY_APPLY_MULTIPLE_TASK_FLAG_FOR_SHORTCUT, this.mApplyMultipleTaskFlagForShortcut);
        }
        if (this.mApplyNoUserActionFlagForShortcut) {
            bundle.putBoolean(KEY_APPLY_NO_USER_ACTION_FLAG_FOR_SHORTCUT, true);
        }
        if (this.mAnimSpecs != null) {
            bundle.putParcelableArray(KEY_ANIM_SPECS, this.mAnimSpecs);
        }
        if (this.mAnimationFinishedListener != null) {
            bundle.putBinder(KEY_ANIMATION_FINISHED_LISTENER, this.mAnimationFinishedListener.asBinder());
        }
        if (this.mSpecsFuture != null) {
            bundle.putBinder(KEY_SPECS_FUTURE, this.mSpecsFuture.asBinder());
        }
        if (this.mSourceInfo != null) {
            bundle.putParcelable(KEY_SOURCE_INFO, this.mSourceInfo);
        }
        if (this.mRotationAnimationHint != -1) {
            bundle.putInt(KEY_ROTATION_ANIMATION_HINT, this.mRotationAnimationHint);
        }
        if (this.mAppVerificationBundle != null) {
            bundle.putBundle(KEY_INSTANT_APP_VERIFICATION_BUNDLE, this.mAppVerificationBundle);
        }
        if (this.mRemoteAnimationAdapter != null) {
            bundle.putParcelable(KEY_REMOTE_ANIMATION_ADAPTER, this.mRemoteAnimationAdapter);
        }
        if (this.mLaunchCookie != null) {
            bundle.putBinder(KEY_LAUNCH_COOKIE, this.mLaunchCookie);
        }
        if (this.mRemoteTransition != null) {
            bundle.putParcelable(KEY_REMOTE_TRANSITION, this.mRemoteTransition);
        }
        if (this.mOverrideTaskTransition) {
            bundle.putBoolean(KEY_OVERRIDE_TASK_TRANSITION, this.mOverrideTaskTransition);
        }
        if (this.mSplashScreenThemeResName != null && !this.mSplashScreenThemeResName.isEmpty()) {
            bundle.putString(KEY_SPLASH_SCREEN_THEME, this.mSplashScreenThemeResName);
        }
        if (this.mRemoveWithTaskOrganizer) {
            bundle.putBoolean(KEY_REMOVE_WITH_TASK_ORGANIZER, this.mRemoveWithTaskOrganizer);
        }
        if (this.mLaunchedFromBubble) {
            bundle.putBoolean(KEY_LAUNCHED_FROM_BUBBLE, this.mLaunchedFromBubble);
        }
        if (this.mTransientLaunch) {
            bundle.putBoolean(KEY_TRANSIENT_LAUNCH, this.mTransientLaunch);
        }
        if (this.mSplashScreenStyle != 0) {
            bundle.putInt(KEY_SPLASH_SCREEN_STYLE, this.mSplashScreenStyle);
        }
        if (this.mLaunchIntoPipParams != null) {
            bundle.putParcelable(KEY_LAUNCH_INTO_PIP_PARAMS, this.mLaunchIntoPipParams);
        }
        if (this.mIsEligibleForLegacyPermissionPrompt) {
            bundle.putBoolean(KEY_LEGACY_PERMISSION_PROMPT_ELIGIBLE, this.mIsEligibleForLegacyPermissionPrompt);
        }
        if (this.mDismissKeyguardIfInsecure) {
            bundle.putBoolean(KEY_DISMISS_KEYGUARD_IF_INSECURE, this.mDismissKeyguardIfInsecure);
        }
        if (this.mPendingIntentCreatorBackgroundActivityStartMode != 0) {
            bundle.putInt(KEY_PENDING_INTENT_CREATOR_BACKGROUND_ACTIVITY_START_MODE, this.mPendingIntentCreatorBackgroundActivityStartMode);
        }
        if (this.mDisableStartingWindow) {
            bundle.putBoolean(KEY_DISABLE_STARTING_WINDOW, this.mDisableStartingWindow);
        }
        bundle.putBinder(KEY_ANIM_ABORT_LISTENER, this.mAnimationAbortListener != null ? this.mAnimationAbortListener.asBinder() : null);
        return bundle;
    }

    public void requestUsageTimeReport(android.app.PendingIntent pendingIntent) {
        this.mUsageTimeReport = pendingIntent;
    }

    public android.app.ActivityOptions.SourceInfo getSourceInfo() {
        return this.mSourceInfo;
    }

    public void setSourceInfo(int i, long j) {
        this.mSourceInfo = new android.app.ActivityOptions.SourceInfo(i, j);
    }

    public android.app.ActivityOptions forTargetActivity() {
        if (this.mAnimationType == 5) {
            android.app.ActivityOptions activityOptions = new android.app.ActivityOptions();
            activityOptions.update(this);
            return activityOptions;
        }
        return null;
    }

    public int getRotationAnimationHint() {
        return this.mRotationAnimationHint;
    }

    public void setRotationAnimationHint(int i) {
        this.mRotationAnimationHint = i;
    }

    public android.os.Bundle popAppVerificationBundle() {
        android.os.Bundle bundle = this.mAppVerificationBundle;
        this.mAppVerificationBundle = null;
        return bundle;
    }

    public android.app.ActivityOptions setAppVerificationBundle(android.os.Bundle bundle) {
        this.mAppVerificationBundle = bundle;
        return this;
    }

    @Override // android.app.ComponentOptions
    public android.app.ActivityOptions setPendingIntentBackgroundActivityStartMode(int i) {
        super.setPendingIntentBackgroundActivityStartMode(i);
        return this;
    }

    @Override // android.app.ComponentOptions
    public int getPendingIntentBackgroundActivityStartMode() {
        return super.getPendingIntentBackgroundActivityStartMode();
    }

    @Override // android.app.ComponentOptions
    @java.lang.Deprecated
    public void setPendingIntentBackgroundActivityLaunchAllowed(boolean z) {
        super.setPendingIntentBackgroundActivityLaunchAllowed(z);
    }

    @Override // android.app.ComponentOptions
    @java.lang.Deprecated
    public boolean isPendingIntentBackgroundActivityLaunchAllowed() {
        return super.isPendingIntentBackgroundActivityLaunchAllowed();
    }

    public java.lang.String toString() {
        return "ActivityOptions(" + hashCode() + "), mPackageName=" + this.mPackageName + ", mAnimationType=" + this.mAnimationType + ", mStartX=" + this.mStartX + ", mStartY=" + this.mStartY + ", mWidth=" + this.mWidth + ", mHeight=" + this.mHeight + ", mLaunchDisplayId=" + this.mLaunchDisplayId;
    }

    public static class SourceInfo implements android.os.Parcelable {
        public static final android.os.Parcelable.Creator<android.app.ActivityOptions.SourceInfo> CREATOR = new android.os.Parcelable.Creator<android.app.ActivityOptions.SourceInfo>() { // from class: android.app.ActivityOptions.SourceInfo.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.app.ActivityOptions.SourceInfo createFromParcel(android.os.Parcel parcel) {
                return new android.app.ActivityOptions.SourceInfo(parcel.readInt(), parcel.readLong());
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.app.ActivityOptions.SourceInfo[] newArray(int i) {
                return new android.app.ActivityOptions.SourceInfo[i];
            }
        };
        public static final int TYPE_DESKTOP_ANIMATION = 5;
        public static final int TYPE_LAUNCHER = 1;
        public static final int TYPE_LOCKSCREEN = 3;
        public static final int TYPE_NOTIFICATION = 2;
        public static final int TYPE_RECENTS_ANIMATION = 4;
        public final long eventTimeMs;
        public final int type;

        @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
        public @interface SourceType {
        }

        SourceInfo(int i, long j) {
            this.type = i;
            this.eventTimeMs = j;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(android.os.Parcel parcel, int i) {
            parcel.writeInt(this.type);
            parcel.writeLong(this.eventTimeMs);
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }
    }

    public static class SceneTransitionInfo implements android.os.Parcelable {
        public static final android.os.Parcelable.Creator<android.app.ActivityOptions.SceneTransitionInfo> CREATOR = new android.os.Parcelable.Creator<android.app.ActivityOptions.SceneTransitionInfo>() { // from class: android.app.ActivityOptions.SceneTransitionInfo.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.app.ActivityOptions.SceneTransitionInfo createFromParcel(android.os.Parcel parcel) {
                return new android.app.ActivityOptions.SceneTransitionInfo(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.app.ActivityOptions.SceneTransitionInfo[] newArray(int i) {
                return new android.app.ActivityOptions.SceneTransitionInfo[i];
            }
        };
        private int mExitCoordinatorIndex;
        private boolean mIsReturning;
        private int mResultCode;
        private android.content.Intent mResultData;
        private android.os.ResultReceiver mResultReceiver;
        private java.util.ArrayList<java.lang.String> mSharedElementNames;

        public SceneTransitionInfo() {
        }

        SceneTransitionInfo(android.os.Parcel parcel) {
            this.mIsReturning = parcel.readBoolean();
            this.mResultCode = parcel.readInt();
            this.mResultData = (android.content.Intent) parcel.readTypedObject(android.content.Intent.CREATOR);
            this.mSharedElementNames = parcel.createStringArrayList();
            this.mResultReceiver = (android.os.ResultReceiver) parcel.readTypedObject(android.os.ResultReceiver.CREATOR);
            this.mExitCoordinatorIndex = parcel.readInt();
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(android.os.Parcel parcel, int i) {
            parcel.writeBoolean(this.mIsReturning);
            parcel.writeInt(this.mResultCode);
            parcel.writeTypedObject(this.mResultData, i);
            parcel.writeStringList(this.mSharedElementNames);
            parcel.writeTypedObject(this.mResultReceiver, i);
            parcel.writeInt(this.mExitCoordinatorIndex);
        }

        public void setReturning(boolean z) {
            this.mIsReturning = z;
        }

        public boolean isReturning() {
            return this.mIsReturning;
        }

        public void setResultCode(int i) {
            this.mResultCode = i;
        }

        public int getResultCode() {
            return this.mResultCode;
        }

        public void setResultData(android.content.Intent intent) {
            this.mResultData = intent;
        }

        public android.content.Intent getResultData() {
            return this.mResultData;
        }

        public void setSharedElementNames(java.util.ArrayList<java.lang.String> arrayList) {
            this.mSharedElementNames = arrayList;
        }

        public java.util.ArrayList<java.lang.String> getSharedElementNames() {
            return this.mSharedElementNames;
        }

        public void setResultReceiver(android.os.ResultReceiver resultReceiver) {
            this.mResultReceiver = resultReceiver;
        }

        public android.os.ResultReceiver getResultReceiver() {
            return this.mResultReceiver;
        }

        public void setExitCoordinatorKey(int i) {
            this.mExitCoordinatorIndex = i;
        }

        public int getExitCoordinatorKey() {
            return this.mExitCoordinatorIndex;
        }

        boolean isCrossTask() {
            return this.mExitCoordinatorIndex < 0;
        }

        public java.lang.String toString() {
            return "SceneTransitionInfo, mIsReturning=" + this.mIsReturning + ", mResultCode=" + this.mResultCode + ", mResultData=" + this.mResultData + ", mSharedElementNames=" + this.mSharedElementNames + ", mTransitionReceiver=" + this.mResultReceiver + ", mExitCoordinatorIndex=" + this.mExitCoordinatorIndex;
        }
    }
}
