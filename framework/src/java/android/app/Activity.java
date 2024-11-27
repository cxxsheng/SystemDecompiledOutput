package android.app;

/* loaded from: classes.dex */
public class Activity extends android.view.ContextThemeWrapper implements android.view.LayoutInflater.Factory2, android.view.Window.Callback, android.view.KeyEvent.Callback, android.view.View.OnCreateContextMenuListener, android.content.ComponentCallbacks2, android.view.Window.OnWindowDismissedCallback, android.view.contentcapture.ContentCaptureManager.ContentCaptureClient {
    private static final int CONTENT_CAPTURE_PAUSE = 3;
    private static final int CONTENT_CAPTURE_RESUME = 2;
    private static final int CONTENT_CAPTURE_START = 1;
    private static final int CONTENT_CAPTURE_STOP = 4;
    private static final boolean DEBUG_LIFECYCLE = false;
    public static final int DEFAULT_KEYS_DIALER = 1;
    public static final int DEFAULT_KEYS_DISABLE = 0;
    public static final int DEFAULT_KEYS_SEARCH_GLOBAL = 4;
    public static final int DEFAULT_KEYS_SEARCH_LOCAL = 3;
    public static final int DEFAULT_KEYS_SHORTCUT = 2;
    public static final int DONT_FINISH_TASK_WITH_ACTIVITY = 0;
    public static final java.lang.String DUMP_ARG_AUTOFILL = "--autofill";
    public static final java.lang.String DUMP_ARG_CONTENT_CAPTURE = "--contentcapture";
    public static final java.lang.String DUMP_ARG_DUMP_DUMPABLE = "--dump-dumpable";
    public static final java.lang.String DUMP_ARG_LIST_DUMPABLES = "--list-dumpables";
    public static final java.lang.String DUMP_ARG_TRANSLATION = "--translation";
    private static final long DUMP_IGNORES_SPECIAL_ARGS = 149254050;
    public static final int FINISH_TASK_WITH_ACTIVITY = 2;
    public static final int FINISH_TASK_WITH_ROOT_ACTIVITY = 1;
    protected static final int[] FOCUSED_STATE_SET = {16842908};
    static final java.lang.String FRAGMENTS_TAG = "android:fragments";
    public static final int FULLSCREEN_MODE_REQUEST_ENTER = 1;
    public static final int FULLSCREEN_MODE_REQUEST_EXIT = 0;
    private static final java.lang.String HAS_CURENT_PERMISSIONS_REQUEST_KEY = "android:hasCurrentPermissionsRequest";
    private static final java.lang.String KEYBOARD_SHORTCUTS_RECEIVER_PKG_NAME = "com.android.systemui";
    private static final int LOG_AM_ON_ACTIVITY_RESULT_CALLED = 30062;
    private static final int LOG_AM_ON_CREATE_CALLED = 30057;
    private static final int LOG_AM_ON_DESTROY_CALLED = 30060;
    private static final int LOG_AM_ON_PAUSE_CALLED = 30021;
    private static final int LOG_AM_ON_RESTART_CALLED = 30058;
    private static final int LOG_AM_ON_RESUME_CALLED = 30022;
    private static final int LOG_AM_ON_START_CALLED = 30059;
    private static final int LOG_AM_ON_STOP_CALLED = 30049;
    private static final int LOG_AM_ON_TOP_RESUMED_GAINED_CALLED = 30064;
    private static final int LOG_AM_ON_TOP_RESUMED_LOST_CALLED = 30065;
    public static final int OVERRIDE_TRANSITION_CLOSE = 1;
    public static final int OVERRIDE_TRANSITION_OPEN = 0;
    private static final java.lang.String REQUEST_PERMISSIONS_WHO_PREFIX = "@android:requestPermissions:";
    public static final int RESULT_CANCELED = 0;
    public static final int RESULT_FIRST_USER = 1;
    public static final int RESULT_OK = -1;
    private static final java.lang.String SAVED_DIALOGS_TAG = "android:savedDialogs";
    private static final java.lang.String SAVED_DIALOG_ARGS_KEY_PREFIX = "android:dialog_args_";
    private static final java.lang.String SAVED_DIALOG_IDS_KEY = "android:savedDialogIds";
    private static final java.lang.String SAVED_DIALOG_KEY_PREFIX = "android:dialog_";
    private static final java.lang.String TAG = "Activity";
    private static final java.lang.String WINDOW_HIERARCHY_TAG = "android:viewHierarchyState";
    android.content.pm.ActivityInfo mActivityInfo;
    private android.app.Application mApplication;
    private android.os.IBinder mAssistToken;
    private android.view.autofill.AutofillClientController mAutofillClientController;
    private android.content.ComponentCallbacksController mCallbacksController;
    boolean mCalled;
    private android.app.ComponentCaller mCaller;
    private boolean mChangeCanvasToTranslucent;
    private android.content.ComponentName mComponent;
    int mConfigChangeFlags;
    private android.view.contentcapture.ContentCaptureManager mContentCaptureManager;
    private android.app.ComponentCaller mCurrentCaller;
    private android.window.OnBackInvokedCallback mDefaultBackCallback;
    private boolean mDestroyed;
    private com.android.internal.util.dump.DumpableContainerImpl mDumpableContainer;
    java.lang.String mEmbeddedID;
    private boolean mEnableDefaultActionBarUp;
    boolean mEnterAnimationComplete;
    boolean mFinished;
    private boolean mHasCurrentPermissionsRequest;
    private int mIdent;
    private android.app.ComponentCaller mInitialCaller;
    private android.app.Instrumentation mInstrumentation;
    android.content.Intent mIntent;
    private boolean mIsInMultiWindowMode;
    boolean mIsInPictureInPictureMode;
    android.app.Activity.NonConfigurationInstances mLastNonConfigurationInstances;
    private int mLastTaskDescriptionHashCode;
    boolean mLaunchedFromBubble;
    android.app.ActivityThread mMainThread;
    private android.util.SparseArray<android.app.Activity.ManagedDialog> mManagedDialogs;
    private android.view.MenuInflater mMenuInflater;
    android.app.Activity mParent;
    java.lang.String mReferrer;
    private boolean mRestoredFromBundle;
    boolean mResumed;
    android.app.ActivityOptions.SceneTransitionInfo mSceneTransitionInfo;
    private android.app.ScreenCaptureCallbackHandler mScreenCaptureCallbackHandler;
    private android.view.SearchEvent mSearchEvent;
    private android.app.SearchManager mSearchManager;
    private android.os.IBinder mShareableActivityToken;
    private boolean mShouldDockBigOverlays;
    private android.window.SplashScreen mSplashScreen;
    boolean mStartedActivity;
    boolean mStopped;
    private java.lang.CharSequence mTitle;
    private android.os.IBinder mToken;
    private android.app.Activity.TranslucentConversionListener mTranslucentCallback;
    private java.lang.Thread mUiThread;
    private android.view.translation.UiTranslationController mUiTranslationController;
    private com.android.internal.app.IVoiceInteractionManagerService mVoiceInteractionManagerService;
    android.app.VoiceInteractor mVoiceInteractor;
    private android.view.Window mWindow;
    private android.view.WindowManager mWindowManager;
    private boolean mDoReportFullyDrawn = true;
    private boolean mCanEnterPictureInPicture = false;
    boolean mChangingConfigurations = false;
    android.content.res.Configuration mCurrentConfig = android.content.res.Configuration.EMPTY;
    private final java.util.ArrayList<android.app.Application.ActivityLifecycleCallbacks> mActivityLifecycleCallbacks = new java.util.ArrayList<>();
    android.view.View mDecor = null;
    boolean mWindowAdded = false;
    boolean mVisibleFromServer = false;
    boolean mVisibleFromClient = true;
    android.app.ActionBar mActionBar = null;
    private int mTitleColor = 0;
    final android.os.Handler mHandler = new android.os.Handler();
    final android.app.FragmentController mFragments = android.app.FragmentController.createController(new android.app.Activity.HostCallbacks());
    private final java.util.ArrayList<android.app.Activity.ManagedCursor> mManagedCursors = new java.util.ArrayList<>();
    int mResultCode = 0;
    android.content.Intent mResultData = null;
    private boolean mTitleReady = false;
    private int mActionModeTypeStarting = 0;
    private int mDefaultKeyMode = 0;
    private android.text.SpannableStringBuilder mDefaultKeySsb = null;
    private final android.app.ActivityManager.TaskDescription mTaskDescription = new android.app.ActivityManager.TaskDescription();
    private int mLastRequestedOrientation = -2;
    private final java.lang.Object mInstanceTracker = android.os.StrictMode.trackActivity(this);
    final android.app.ActivityTransitionState mActivityTransitionState = new android.app.ActivityTransitionState();
    android.app.SharedElementCallback mEnterTransitionListener = android.app.SharedElementCallback.NULL_CALLBACK;
    android.app.SharedElementCallback mExitTransitionListener = android.app.SharedElementCallback.NULL_CALLBACK;
    private final android.view.Window.WindowControllerCallback mWindowControllerCallback = new android.view.Window.WindowControllerCallback() { // from class: android.app.Activity.1
        @Override // android.view.Window.WindowControllerCallback
        public void toggleFreeformWindowingMode() {
            android.app.ActivityClient.getInstance().toggleFreeformWindowingMode(android.app.Activity.this.mToken);
        }

        @Override // android.view.Window.WindowControllerCallback
        public void enterPictureInPictureModeIfPossible() {
            if (android.app.Activity.this.mActivityInfo.supportsPictureInPicture()) {
                android.app.Activity.this.enterPictureInPictureMode();
            }
        }

        @Override // android.view.Window.WindowControllerCallback
        public boolean isTaskRoot() {
            return android.app.ActivityClient.getInstance().getTaskForActivity(android.app.Activity.this.mToken, true) >= 0;
        }

        @Override // android.view.Window.WindowControllerCallback
        public void updateStatusBarColor(int i) {
            android.app.Activity.this.mTaskDescription.setStatusBarColor(i);
            android.app.Activity.this.setTaskDescription(android.app.Activity.this.mTaskDescription);
        }

        @Override // android.view.Window.WindowControllerCallback
        public void updateStatusBarAppearance(int i) {
            android.app.Activity.this.mTaskDescription.setStatusBarAppearance(i);
            android.app.Activity.this.setTaskDescription(android.app.Activity.this.mTaskDescription);
        }

        @Override // android.view.Window.WindowControllerCallback
        public void updateNavigationBarColor(int i) {
            android.app.Activity.this.mTaskDescription.setNavigationBarColor(i);
            android.app.Activity.this.setTaskDescription(android.app.Activity.this.mTaskDescription);
        }
    };

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    @interface ContentCaptureNotificationType {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    @interface DefaultKeyMode {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface FullscreenModeRequest {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface OverrideTransition {
    }

    public interface ScreenCaptureCallback {
        void onScreenCaptured();
    }

    @android.annotation.SystemApi
    public interface TranslucentConversionListener {
        void onTranslucentConversionComplete(boolean z);
    }

    private static native java.lang.String getDlWarning();

    private static class ManagedDialog {
        android.os.Bundle mArgs;
        android.app.Dialog mDialog;

        private ManagedDialog() {
        }
    }

    static final class NonConfigurationInstances {
        java.lang.Object activity;
        java.util.HashMap<java.lang.String, java.lang.Object> children;
        android.app.FragmentManagerNonConfig fragments;
        android.util.ArrayMap<java.lang.String, android.app.LoaderManager> loaders;
        android.app.VoiceInteractor voiceInteractor;

        NonConfigurationInstances() {
        }
    }

    private static final class ManagedCursor {
        private final android.database.Cursor mCursor;
        private boolean mReleased = false;
        private boolean mUpdated = false;

        ManagedCursor(android.database.Cursor cursor) {
            this.mCursor = cursor;
        }
    }

    public android.content.Intent getIntent() {
        return this.mIntent;
    }

    public void setIntent(android.content.Intent intent) {
        internalSetIntent(intent, null);
    }

    public android.app.ComponentCaller getCaller() {
        return this.mCaller;
    }

    public void setIntent(android.content.Intent intent, android.app.ComponentCaller componentCaller) {
        internalSetIntent(intent, componentCaller);
    }

    private void internalSetIntent(android.content.Intent intent, android.app.ComponentCaller componentCaller) {
        this.mIntent = intent;
        this.mCaller = componentCaller;
    }

    public void setLocusContext(android.content.LocusId locusId, android.os.Bundle bundle) {
        try {
            android.app.ActivityManager.getService().setActivityLocusContext(this.mComponent, locusId, this.mToken);
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
        }
        if (locusId != null) {
            setLocusContextToContentCapture(locusId, bundle);
        }
    }

    public final android.app.Application getApplication() {
        return this.mApplication;
    }

    public final boolean isChild() {
        return this.mParent != null;
    }

    public final android.app.Activity getParent() {
        return this.mParent;
    }

    public android.view.WindowManager getWindowManager() {
        return this.mWindowManager;
    }

    public android.view.Window getWindow() {
        return this.mWindow;
    }

    @java.lang.Deprecated
    public android.app.LoaderManager getLoaderManager() {
        return this.mFragments.getLoaderManager();
    }

    public android.view.View getCurrentFocus() {
        if (this.mWindow != null) {
            return this.mWindow.getCurrentFocus();
        }
        return null;
    }

    private android.view.contentcapture.ContentCaptureManager getContentCaptureManager() {
        if (!android.os.UserHandle.isApp(android.os.Process.myUid())) {
            return null;
        }
        if (this.mContentCaptureManager == null) {
            this.mContentCaptureManager = (android.view.contentcapture.ContentCaptureManager) getSystemService(android.view.contentcapture.ContentCaptureManager.class);
        }
        return this.mContentCaptureManager;
    }

    private java.lang.String getContentCaptureTypeAsString(int i) {
        switch (i) {
            case 1:
                return "START";
            case 2:
                return "RESUME";
            case 3:
                return "PAUSE";
            case 4:
                return "STOP";
            default:
                return "UNKNOW-" + i;
        }
    }

    private void notifyContentCaptureManagerIfNeeded(int i) {
        if (android.os.Trace.isTagEnabled(64L)) {
            android.os.Trace.traceBegin(64L, "notifyContentCapture(" + getContentCaptureTypeAsString(i) + ") for " + this.mComponent.toShortString());
        }
        try {
            android.view.contentcapture.ContentCaptureManager contentCaptureManager = getContentCaptureManager();
            if (contentCaptureManager == null) {
                return;
            }
            switch (i) {
                case 1:
                    android.view.Window window = getWindow();
                    if (window != null) {
                        contentCaptureManager.updateWindowAttributes(window.getAttributes());
                    }
                    contentCaptureManager.onActivityCreated(this.mToken, this.mShareableActivityToken, getComponentName());
                    break;
                case 2:
                    contentCaptureManager.onActivityResumed();
                    break;
                case 3:
                    contentCaptureManager.onActivityPaused();
                    break;
                case 4:
                    contentCaptureManager.onActivityDestroyed();
                    break;
                default:
                    android.util.Log.wtf(TAG, "Invalid @ContentCaptureNotificationType: " + i);
                    break;
            }
        } finally {
            android.os.Trace.traceEnd(64L);
        }
    }

    private void setLocusContextToContentCapture(android.content.LocusId locusId, android.os.Bundle bundle) {
        android.view.contentcapture.ContentCaptureManager contentCaptureManager = getContentCaptureManager();
        if (contentCaptureManager == null) {
            return;
        }
        android.view.contentcapture.ContentCaptureContext.Builder builder = new android.view.contentcapture.ContentCaptureContext.Builder(locusId);
        if (bundle != null) {
            builder.setExtras(bundle);
        }
        contentCaptureManager.getMainContentCaptureSession().setContentCaptureContext(builder.build());
    }

    @Override // android.view.ContextThemeWrapper, android.content.ContextWrapper
    protected void attachBaseContext(android.content.Context context) {
        super.attachBaseContext(context);
        if (context != null) {
            context.setAutofillClient(getAutofillClient());
            context.setContentCaptureOptions(getContentCaptureOptions());
        }
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public final android.view.autofill.AutofillManager.AutofillClient getAutofillClient() {
        return getAutofillClientController();
    }

    private android.view.autofill.AutofillClientController getAutofillClientController() {
        if (this.mAutofillClientController == null) {
            this.mAutofillClientController = new android.view.autofill.AutofillClientController(this);
        }
        return this.mAutofillClientController;
    }

    @Override // android.content.Context
    public final android.view.contentcapture.ContentCaptureManager.ContentCaptureClient getContentCaptureClient() {
        return this;
    }

    public void registerActivityLifecycleCallbacks(android.app.Application.ActivityLifecycleCallbacks activityLifecycleCallbacks) {
        synchronized (this.mActivityLifecycleCallbacks) {
            this.mActivityLifecycleCallbacks.add(activityLifecycleCallbacks);
        }
    }

    public void unregisterActivityLifecycleCallbacks(android.app.Application.ActivityLifecycleCallbacks activityLifecycleCallbacks) {
        synchronized (this.mActivityLifecycleCallbacks) {
            this.mActivityLifecycleCallbacks.remove(activityLifecycleCallbacks);
        }
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public void registerComponentCallbacks(android.content.ComponentCallbacks componentCallbacks) {
        if (android.app.compat.CompatChanges.isChangeEnabled(android.content.Context.OVERRIDABLE_COMPONENT_CALLBACKS) && this.mCallbacksController == null) {
            this.mCallbacksController = new android.content.ComponentCallbacksController();
        }
        if (this.mCallbacksController != null) {
            this.mCallbacksController.registerCallbacks(componentCallbacks);
        } else {
            super.registerComponentCallbacks(componentCallbacks);
        }
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public void unregisterComponentCallbacks(android.content.ComponentCallbacks componentCallbacks) {
        if (this.mCallbacksController != null) {
            this.mCallbacksController.unregisterCallbacks(componentCallbacks);
        } else {
            super.unregisterComponentCallbacks(componentCallbacks);
        }
    }

    private void dispatchActivityPreCreated(android.os.Bundle bundle) {
        getApplication().dispatchActivityPreCreated(this, bundle);
        java.lang.Object[] collectActivityLifecycleCallbacks = collectActivityLifecycleCallbacks();
        if (collectActivityLifecycleCallbacks != null) {
            for (java.lang.Object obj : collectActivityLifecycleCallbacks) {
                ((android.app.Application.ActivityLifecycleCallbacks) obj).onActivityPreCreated(this, bundle);
            }
        }
    }

    private void dispatchActivityCreated(android.os.Bundle bundle) {
        getApplication().dispatchActivityCreated(this, bundle);
        java.lang.Object[] collectActivityLifecycleCallbacks = collectActivityLifecycleCallbacks();
        if (collectActivityLifecycleCallbacks != null) {
            for (java.lang.Object obj : collectActivityLifecycleCallbacks) {
                ((android.app.Application.ActivityLifecycleCallbacks) obj).onActivityCreated(this, bundle);
            }
        }
    }

    private void dispatchActivityPostCreated(android.os.Bundle bundle) {
        java.lang.Object[] collectActivityLifecycleCallbacks = collectActivityLifecycleCallbacks();
        if (collectActivityLifecycleCallbacks != null) {
            for (java.lang.Object obj : collectActivityLifecycleCallbacks) {
                ((android.app.Application.ActivityLifecycleCallbacks) obj).onActivityPostCreated(this, bundle);
            }
        }
        getApplication().dispatchActivityPostCreated(this, bundle);
    }

    private void dispatchActivityPreStarted() {
        getApplication().dispatchActivityPreStarted(this);
        java.lang.Object[] collectActivityLifecycleCallbacks = collectActivityLifecycleCallbacks();
        if (collectActivityLifecycleCallbacks != null) {
            for (java.lang.Object obj : collectActivityLifecycleCallbacks) {
                ((android.app.Application.ActivityLifecycleCallbacks) obj).onActivityPreStarted(this);
            }
        }
    }

    private void dispatchActivityStarted() {
        getApplication().dispatchActivityStarted(this);
        java.lang.Object[] collectActivityLifecycleCallbacks = collectActivityLifecycleCallbacks();
        if (collectActivityLifecycleCallbacks != null) {
            for (java.lang.Object obj : collectActivityLifecycleCallbacks) {
                ((android.app.Application.ActivityLifecycleCallbacks) obj).onActivityStarted(this);
            }
        }
    }

    private void dispatchActivityPostStarted() {
        java.lang.Object[] collectActivityLifecycleCallbacks = collectActivityLifecycleCallbacks();
        if (collectActivityLifecycleCallbacks != null) {
            for (java.lang.Object obj : collectActivityLifecycleCallbacks) {
                ((android.app.Application.ActivityLifecycleCallbacks) obj).onActivityPostStarted(this);
            }
        }
        getApplication().dispatchActivityPostStarted(this);
    }

    private void dispatchActivityPreResumed() {
        getApplication().dispatchActivityPreResumed(this);
        java.lang.Object[] collectActivityLifecycleCallbacks = collectActivityLifecycleCallbacks();
        if (collectActivityLifecycleCallbacks != null) {
            for (java.lang.Object obj : collectActivityLifecycleCallbacks) {
                ((android.app.Application.ActivityLifecycleCallbacks) obj).onActivityPreResumed(this);
            }
        }
    }

    private void dispatchActivityResumed() {
        getApplication().dispatchActivityResumed(this);
        java.lang.Object[] collectActivityLifecycleCallbacks = collectActivityLifecycleCallbacks();
        if (collectActivityLifecycleCallbacks != null) {
            for (java.lang.Object obj : collectActivityLifecycleCallbacks) {
                ((android.app.Application.ActivityLifecycleCallbacks) obj).onActivityResumed(this);
            }
        }
    }

    private void dispatchActivityPostResumed() {
        java.lang.Object[] collectActivityLifecycleCallbacks = collectActivityLifecycleCallbacks();
        if (collectActivityLifecycleCallbacks != null) {
            for (java.lang.Object obj : collectActivityLifecycleCallbacks) {
                ((android.app.Application.ActivityLifecycleCallbacks) obj).onActivityPostResumed(this);
            }
        }
        getApplication().dispatchActivityPostResumed(this);
    }

    private void dispatchActivityPrePaused() {
        getApplication().dispatchActivityPrePaused(this);
        java.lang.Object[] collectActivityLifecycleCallbacks = collectActivityLifecycleCallbacks();
        if (collectActivityLifecycleCallbacks != null) {
            for (int length = collectActivityLifecycleCallbacks.length - 1; length >= 0; length--) {
                ((android.app.Application.ActivityLifecycleCallbacks) collectActivityLifecycleCallbacks[length]).onActivityPrePaused(this);
            }
        }
    }

    private void dispatchActivityPaused() {
        java.lang.Object[] collectActivityLifecycleCallbacks = collectActivityLifecycleCallbacks();
        if (collectActivityLifecycleCallbacks != null) {
            for (int length = collectActivityLifecycleCallbacks.length - 1; length >= 0; length--) {
                ((android.app.Application.ActivityLifecycleCallbacks) collectActivityLifecycleCallbacks[length]).onActivityPaused(this);
            }
        }
        getApplication().dispatchActivityPaused(this);
    }

    private void dispatchActivityPostPaused() {
        java.lang.Object[] collectActivityLifecycleCallbacks = collectActivityLifecycleCallbacks();
        if (collectActivityLifecycleCallbacks != null) {
            for (int length = collectActivityLifecycleCallbacks.length - 1; length >= 0; length--) {
                ((android.app.Application.ActivityLifecycleCallbacks) collectActivityLifecycleCallbacks[length]).onActivityPostPaused(this);
            }
        }
        getApplication().dispatchActivityPostPaused(this);
    }

    private void dispatchActivityPreStopped() {
        getApplication().dispatchActivityPreStopped(this);
        java.lang.Object[] collectActivityLifecycleCallbacks = collectActivityLifecycleCallbacks();
        if (collectActivityLifecycleCallbacks != null) {
            for (int length = collectActivityLifecycleCallbacks.length - 1; length >= 0; length--) {
                ((android.app.Application.ActivityLifecycleCallbacks) collectActivityLifecycleCallbacks[length]).onActivityPreStopped(this);
            }
        }
    }

    private void dispatchActivityStopped() {
        java.lang.Object[] collectActivityLifecycleCallbacks = collectActivityLifecycleCallbacks();
        if (collectActivityLifecycleCallbacks != null) {
            for (int length = collectActivityLifecycleCallbacks.length - 1; length >= 0; length--) {
                ((android.app.Application.ActivityLifecycleCallbacks) collectActivityLifecycleCallbacks[length]).onActivityStopped(this);
            }
        }
        getApplication().dispatchActivityStopped(this);
    }

    private void dispatchActivityPostStopped() {
        java.lang.Object[] collectActivityLifecycleCallbacks = collectActivityLifecycleCallbacks();
        if (collectActivityLifecycleCallbacks != null) {
            for (int length = collectActivityLifecycleCallbacks.length - 1; length >= 0; length--) {
                ((android.app.Application.ActivityLifecycleCallbacks) collectActivityLifecycleCallbacks[length]).onActivityPostStopped(this);
            }
        }
        getApplication().dispatchActivityPostStopped(this);
    }

    private void dispatchActivityPreSaveInstanceState(android.os.Bundle bundle) {
        getApplication().dispatchActivityPreSaveInstanceState(this, bundle);
        java.lang.Object[] collectActivityLifecycleCallbacks = collectActivityLifecycleCallbacks();
        if (collectActivityLifecycleCallbacks != null) {
            for (int length = collectActivityLifecycleCallbacks.length - 1; length >= 0; length--) {
                ((android.app.Application.ActivityLifecycleCallbacks) collectActivityLifecycleCallbacks[length]).onActivityPreSaveInstanceState(this, bundle);
            }
        }
    }

    private void dispatchActivitySaveInstanceState(android.os.Bundle bundle) {
        java.lang.Object[] collectActivityLifecycleCallbacks = collectActivityLifecycleCallbacks();
        if (collectActivityLifecycleCallbacks != null) {
            for (int length = collectActivityLifecycleCallbacks.length - 1; length >= 0; length--) {
                ((android.app.Application.ActivityLifecycleCallbacks) collectActivityLifecycleCallbacks[length]).onActivitySaveInstanceState(this, bundle);
            }
        }
        getApplication().dispatchActivitySaveInstanceState(this, bundle);
    }

    private void dispatchActivityPostSaveInstanceState(android.os.Bundle bundle) {
        java.lang.Object[] collectActivityLifecycleCallbacks = collectActivityLifecycleCallbacks();
        if (collectActivityLifecycleCallbacks != null) {
            for (int length = collectActivityLifecycleCallbacks.length - 1; length >= 0; length--) {
                ((android.app.Application.ActivityLifecycleCallbacks) collectActivityLifecycleCallbacks[length]).onActivityPostSaveInstanceState(this, bundle);
            }
        }
        getApplication().dispatchActivityPostSaveInstanceState(this, bundle);
    }

    private void dispatchActivityPreDestroyed() {
        getApplication().dispatchActivityPreDestroyed(this);
        java.lang.Object[] collectActivityLifecycleCallbacks = collectActivityLifecycleCallbacks();
        if (collectActivityLifecycleCallbacks != null) {
            for (int length = collectActivityLifecycleCallbacks.length - 1; length >= 0; length--) {
                ((android.app.Application.ActivityLifecycleCallbacks) collectActivityLifecycleCallbacks[length]).onActivityPreDestroyed(this);
            }
        }
    }

    private void dispatchActivityDestroyed() {
        java.lang.Object[] collectActivityLifecycleCallbacks = collectActivityLifecycleCallbacks();
        if (collectActivityLifecycleCallbacks != null) {
            for (int length = collectActivityLifecycleCallbacks.length - 1; length >= 0; length--) {
                ((android.app.Application.ActivityLifecycleCallbacks) collectActivityLifecycleCallbacks[length]).onActivityDestroyed(this);
            }
        }
        getApplication().dispatchActivityDestroyed(this);
    }

    private void dispatchActivityPostDestroyed() {
        java.lang.Object[] collectActivityLifecycleCallbacks = collectActivityLifecycleCallbacks();
        if (collectActivityLifecycleCallbacks != null) {
            for (int length = collectActivityLifecycleCallbacks.length - 1; length >= 0; length--) {
                ((android.app.Application.ActivityLifecycleCallbacks) collectActivityLifecycleCallbacks[length]).onActivityPostDestroyed(this);
            }
        }
        getApplication().dispatchActivityPostDestroyed(this);
    }

    private void dispatchActivityConfigurationChanged() {
        if (getApplication() != null) {
            getApplication().dispatchActivityConfigurationChanged(this);
        }
        java.lang.Object[] collectActivityLifecycleCallbacks = collectActivityLifecycleCallbacks();
        if (collectActivityLifecycleCallbacks != null) {
            for (java.lang.Object obj : collectActivityLifecycleCallbacks) {
                ((android.app.Application.ActivityLifecycleCallbacks) obj).onActivityConfigurationChanged(this);
            }
        }
    }

    private java.lang.Object[] collectActivityLifecycleCallbacks() {
        java.lang.Object[] objArr;
        synchronized (this.mActivityLifecycleCallbacks) {
            if (this.mActivityLifecycleCallbacks.size() <= 0) {
                objArr = null;
            } else {
                objArr = this.mActivityLifecycleCallbacks.toArray();
            }
        }
        return objArr;
    }

    private void notifyVoiceInteractionManagerServiceActivityEvent(int i) {
        if (this.mVoiceInteractionManagerService == null) {
            this.mVoiceInteractionManagerService = com.android.internal.app.IVoiceInteractionManagerService.Stub.asInterface(android.os.ServiceManager.getService(android.content.Context.VOICE_INTERACTION_MANAGER_SERVICE));
            if (this.mVoiceInteractionManagerService == null) {
                android.util.Log.w(TAG, "notifyVoiceInteractionManagerServiceActivityEvent: Can not get VoiceInteractionManagerService");
                return;
            }
        }
        try {
            this.mVoiceInteractionManagerService.notifyActivityEventChanged(this.mToken, i);
        } catch (android.os.RemoteException e) {
        }
    }

    protected void onCreate(android.os.Bundle bundle) {
        if (this.mLastNonConfigurationInstances != null) {
            this.mFragments.restoreLoaderNonConfig(this.mLastNonConfigurationInstances.loaders);
        }
        if (this.mActivityInfo.parentActivityName != null) {
            if (this.mActionBar == null) {
                this.mEnableDefaultActionBarUp = true;
            } else {
                this.mActionBar.setDefaultDisplayHomeAsUpEnabled(true);
            }
        }
        if (bundle != null) {
            getAutofillClientController().onActivityCreated(bundle);
            this.mFragments.restoreAllState(bundle.getParcelable(FRAGMENTS_TAG), this.mLastNonConfigurationInstances != null ? this.mLastNonConfigurationInstances.fragments : null);
        }
        this.mFragments.dispatchCreate();
        dispatchActivityCreated(bundle);
        if (this.mVoiceInteractor != null) {
            this.mVoiceInteractor.attachActivity(this);
        }
        this.mRestoredFromBundle = bundle != null;
        this.mCalled = true;
        if (android.window.WindowOnBackInvokedDispatcher.isOnBackInvokedCallbackEnabled(this)) {
            this.mDefaultBackCallback = new android.window.OnBackInvokedCallback() { // from class: android.app.Activity$$ExternalSyntheticLambda0
                @Override // android.window.OnBackInvokedCallback
                public final void onBackInvoked() {
                    android.app.Activity.this.onBackInvoked();
                }
            };
            getOnBackInvokedDispatcher().registerSystemOnBackInvokedCallback(this.mDefaultBackCallback);
        }
    }

    public final android.window.SplashScreen getSplashScreen() {
        return getOrCreateSplashScreen();
    }

    private android.window.SplashScreen getOrCreateSplashScreen() {
        android.window.SplashScreen splashScreen;
        synchronized (this) {
            if (this.mSplashScreen == null) {
                this.mSplashScreen = new android.window.SplashScreen.SplashScreenImpl(this);
            }
            splashScreen = this.mSplashScreen;
        }
        return splashScreen;
    }

    public void onCreate(android.os.Bundle bundle, android.os.PersistableBundle persistableBundle) {
        onCreate(bundle);
    }

    final void performRestoreInstanceState(android.os.Bundle bundle) {
        onRestoreInstanceState(bundle);
        restoreManagedDialogs(bundle);
    }

    final void performRestoreInstanceState(android.os.Bundle bundle, android.os.PersistableBundle persistableBundle) {
        onRestoreInstanceState(bundle, persistableBundle);
        if (bundle != null) {
            restoreManagedDialogs(bundle);
        }
    }

    protected void onRestoreInstanceState(android.os.Bundle bundle) {
        android.os.Bundle bundle2;
        if (this.mWindow != null && (bundle2 = bundle.getBundle(WINDOW_HIERARCHY_TAG)) != null) {
            this.mWindow.restoreHierarchyState(bundle2);
        }
    }

    public void onRestoreInstanceState(android.os.Bundle bundle, android.os.PersistableBundle persistableBundle) {
        if (bundle != null) {
            onRestoreInstanceState(bundle);
        }
    }

    private void restoreManagedDialogs(android.os.Bundle bundle) {
        android.os.Bundle bundle2 = bundle.getBundle(SAVED_DIALOGS_TAG);
        if (bundle2 == null) {
            return;
        }
        int[] intArray = bundle2.getIntArray(SAVED_DIALOG_IDS_KEY);
        this.mManagedDialogs = new android.util.SparseArray<>(intArray.length);
        for (int i : intArray) {
            android.os.Bundle bundle3 = bundle2.getBundle(savedDialogKeyFor(i));
            if (bundle3 != null) {
                android.app.Activity.ManagedDialog managedDialog = new android.app.Activity.ManagedDialog();
                managedDialog.mArgs = bundle2.getBundle(savedDialogArgsKeyFor(i));
                managedDialog.mDialog = createDialog(java.lang.Integer.valueOf(i), bundle3, managedDialog.mArgs);
                if (managedDialog.mDialog != null) {
                    this.mManagedDialogs.put(i, managedDialog);
                    onPrepareDialog(i, managedDialog.mDialog, managedDialog.mArgs);
                    managedDialog.mDialog.onRestoreInstanceState(bundle3);
                }
            }
        }
    }

    private android.app.Dialog createDialog(java.lang.Integer num, android.os.Bundle bundle, android.os.Bundle bundle2) {
        android.app.Dialog onCreateDialog = onCreateDialog(num.intValue(), bundle2);
        if (onCreateDialog == null) {
            return null;
        }
        onCreateDialog.dispatchOnCreate(bundle);
        return onCreateDialog;
    }

    private static java.lang.String savedDialogKeyFor(int i) {
        return SAVED_DIALOG_KEY_PREFIX + i;
    }

    private static java.lang.String savedDialogArgsKeyFor(int i) {
        return SAVED_DIALOG_ARGS_KEY_PREFIX + i;
    }

    protected void onPostCreate(android.os.Bundle bundle) {
        if (!isChild()) {
            this.mTitleReady = true;
            onTitleChanged(getTitle(), getTitleColor());
        }
        this.mCalled = true;
        notifyContentCaptureManagerIfNeeded(1);
        notifyVoiceInteractionManagerServiceActivityEvent(1);
    }

    public void onPostCreate(android.os.Bundle bundle, android.os.PersistableBundle persistableBundle) {
        onPostCreate(bundle);
    }

    protected void onStart() {
        this.mCalled = true;
        this.mFragments.doLoaderStart();
        dispatchActivityStarted();
        getAutofillClientController().onActivityStarted();
    }

    protected void onRestart() {
        this.mCalled = true;
    }

    @java.lang.Deprecated
    public void onStateNotSaved() {
    }

    protected void onResume() {
        dispatchActivityResumed();
        this.mActivityTransitionState.onResume(this);
        getAutofillClientController().onActivityResumed();
        notifyContentCaptureManagerIfNeeded(2);
        this.mCalled = true;
    }

    protected void onPostResume() {
        android.view.Window window = getWindow();
        if (window != null) {
            window.makeActive();
        }
        if (this.mActionBar != null) {
            this.mActionBar.setShowHideAnimationEnabled(true);
        }
        notifyVoiceInteractionManagerServiceActivityEvent(2);
        this.mCalled = true;
    }

    public void onTopResumedActivityChanged(boolean z) {
    }

    final void performTopResumedActivityChanged(boolean z, java.lang.String str) {
        onTopResumedActivityChanged(z);
        if (z) {
            android.app.EventLogTags.writeWmOnTopResumedGainedCalled(this.mIdent, getComponentName().getClassName(), str);
        } else {
            android.app.EventLogTags.writeWmOnTopResumedLostCalled(this.mIdent, getComponentName().getClassName(), str);
        }
    }

    void setVoiceInteractor(com.android.internal.app.IVoiceInteractor iVoiceInteractor) {
        if (this.mVoiceInteractor != null && this.mVoiceInteractor.getActiveRequests() != null) {
            for (android.app.VoiceInteractor.Request request : this.mVoiceInteractor.getActiveRequests()) {
                request.cancel();
                request.clear();
            }
        }
        if (iVoiceInteractor == null) {
            this.mVoiceInteractor = null;
        } else {
            this.mVoiceInteractor = new android.app.VoiceInteractor(iVoiceInteractor, this, this, android.os.Looper.myLooper());
        }
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public int getNextAutofillId() {
        return getAutofillClientController().getNextAutofillId();
    }

    public boolean isVoiceInteraction() {
        return this.mVoiceInteractor != null;
    }

    public boolean isVoiceInteractionRoot() {
        return this.mVoiceInteractor != null && android.app.ActivityClient.getInstance().isRootVoiceInteraction(this.mToken);
    }

    public android.app.VoiceInteractor getVoiceInteractor() {
        return this.mVoiceInteractor;
    }

    public boolean isLocalVoiceInteractionSupported() {
        try {
            return android.app.ActivityTaskManager.getService().supportsLocalVoiceInteraction();
        } catch (android.os.RemoteException e) {
            return false;
        }
    }

    public void startLocalVoiceInteraction(android.os.Bundle bundle) {
        android.app.ActivityClient.getInstance().startLocalVoiceInteraction(this.mToken, bundle);
    }

    public void onLocalVoiceInteractionStarted() {
    }

    public void onLocalVoiceInteractionStopped() {
    }

    public void stopLocalVoiceInteraction() {
        android.app.ActivityClient.getInstance().stopLocalVoiceInteraction(this.mToken);
    }

    protected void onNewIntent(android.content.Intent intent) {
    }

    public void onNewIntent(android.content.Intent intent, android.app.ComponentCaller componentCaller) {
        onNewIntent(intent);
    }

    final void performSaveInstanceState(android.os.Bundle bundle) {
        dispatchActivityPreSaveInstanceState(bundle);
        onSaveInstanceState(bundle);
        saveManagedDialogs(bundle);
        this.mActivityTransitionState.saveState(bundle);
        storeHasCurrentPermissionRequest(bundle);
        dispatchActivityPostSaveInstanceState(bundle);
    }

    final void performSaveInstanceState(android.os.Bundle bundle, android.os.PersistableBundle persistableBundle) {
        dispatchActivityPreSaveInstanceState(bundle);
        onSaveInstanceState(bundle, persistableBundle);
        saveManagedDialogs(bundle);
        storeHasCurrentPermissionRequest(bundle);
        dispatchActivityPostSaveInstanceState(bundle);
    }

    protected void onSaveInstanceState(android.os.Bundle bundle) {
        bundle.putBundle(WINDOW_HIERARCHY_TAG, this.mWindow.saveHierarchyState());
        android.os.Parcelable saveAllState = this.mFragments.saveAllState();
        if (saveAllState != null) {
            bundle.putParcelable(FRAGMENTS_TAG, saveAllState);
        }
        getAutofillClientController().onSaveInstanceState(bundle);
        dispatchActivitySaveInstanceState(bundle);
    }

    public void onSaveInstanceState(android.os.Bundle bundle, android.os.PersistableBundle persistableBundle) {
        onSaveInstanceState(bundle);
    }

    private void saveManagedDialogs(android.os.Bundle bundle) {
        int size;
        if (this.mManagedDialogs == null || (size = this.mManagedDialogs.size()) == 0) {
            return;
        }
        android.os.Bundle bundle2 = new android.os.Bundle();
        int[] iArr = new int[this.mManagedDialogs.size()];
        for (int i = 0; i < size; i++) {
            int keyAt = this.mManagedDialogs.keyAt(i);
            iArr[i] = keyAt;
            android.app.Activity.ManagedDialog valueAt = this.mManagedDialogs.valueAt(i);
            bundle2.putBundle(savedDialogKeyFor(keyAt), valueAt.mDialog.onSaveInstanceState());
            if (valueAt.mArgs != null) {
                bundle2.putBundle(savedDialogArgsKeyFor(keyAt), valueAt.mArgs);
            }
        }
        bundle2.putIntArray(SAVED_DIALOG_IDS_KEY, iArr);
        bundle.putBundle(SAVED_DIALOGS_TAG, bundle2);
    }

    protected void onPause() {
        dispatchActivityPaused();
        getAutofillClientController().onActivityPaused();
        notifyContentCaptureManagerIfNeeded(3);
        notifyVoiceInteractionManagerServiceActivityEvent(3);
        this.mCalled = true;
    }

    protected void onUserLeaveHint() {
    }

    @java.lang.Deprecated
    public boolean onCreateThumbnail(android.graphics.Bitmap bitmap, android.graphics.Canvas canvas) {
        return false;
    }

    public java.lang.CharSequence onCreateDescription() {
        return null;
    }

    public void onProvideAssistData(android.os.Bundle bundle) {
    }

    public void onProvideAssistContent(android.app.assist.AssistContent assistContent) {
    }

    public void onGetDirectActions(android.os.CancellationSignal cancellationSignal, java.util.function.Consumer<java.util.List<android.app.DirectAction>> consumer) {
        consumer.accept(java.util.Collections.emptyList());
    }

    public void onPerformDirectAction(java.lang.String str, android.os.Bundle bundle, android.os.CancellationSignal cancellationSignal, java.util.function.Consumer<android.os.Bundle> consumer) {
    }

    public final void requestShowKeyboardShortcuts() {
        android.content.ComponentName unflattenFromString = android.content.ComponentName.unflattenFromString(getResources().getString(com.android.internal.R.string.config_systemUIServiceComponent));
        android.content.Intent intent = new android.content.Intent(android.content.Intent.ACTION_SHOW_KEYBOARD_SHORTCUTS);
        intent.setPackage(unflattenFromString.getPackageName());
        sendBroadcastAsUser(intent, android.os.Process.myUserHandle());
    }

    public final void dismissKeyboardShortcutsHelper() {
        android.content.ComponentName unflattenFromString = android.content.ComponentName.unflattenFromString(getResources().getString(com.android.internal.R.string.config_systemUIServiceComponent));
        android.content.Intent intent = new android.content.Intent(android.content.Intent.ACTION_DISMISS_KEYBOARD_SHORTCUTS);
        intent.setPackage(unflattenFromString.getPackageName());
        sendBroadcastAsUser(intent, android.os.Process.myUserHandle());
    }

    @Override // android.view.Window.Callback
    public void onProvideKeyboardShortcuts(java.util.List<android.view.KeyboardShortcutGroup> list, android.view.Menu menu, int i) {
        if (menu == null) {
            return;
        }
        int size = menu.size();
        android.view.KeyboardShortcutGroup keyboardShortcutGroup = null;
        for (int i2 = 0; i2 < size; i2++) {
            android.view.MenuItem item = menu.getItem(i2);
            java.lang.CharSequence title = item.getTitle();
            char alphabeticShortcut = item.getAlphabeticShortcut();
            int alphabeticModifiers = item.getAlphabeticModifiers();
            if (title != null && alphabeticShortcut != 0) {
                if (keyboardShortcutGroup == null) {
                    int i3 = this.mApplication.getApplicationInfo().labelRes;
                    keyboardShortcutGroup = new android.view.KeyboardShortcutGroup(i3 != 0 ? getString(i3) : null);
                }
                keyboardShortcutGroup.addItem(new android.view.KeyboardShortcutInfo(title, alphabeticShortcut, alphabeticModifiers));
            }
        }
        if (keyboardShortcutGroup != null) {
            list.add(keyboardShortcutGroup);
        }
    }

    public boolean showAssist(android.os.Bundle bundle) {
        return android.app.ActivityClient.getInstance().showAssistFromActivity(this.mToken, bundle);
    }

    protected void onStop() {
        if (this.mActionBar != null) {
            this.mActionBar.setShowHideAnimationEnabled(false);
        }
        this.mActivityTransitionState.onStop(this);
        dispatchActivityStopped();
        this.mTranslucentCallback = null;
        this.mCalled = true;
        getAutofillClientController().onActivityStopped(this.mIntent, this.mChangingConfigurations);
        this.mEnterAnimationComplete = false;
        notifyVoiceInteractionManagerServiceActivityEvent(4);
    }

    protected void onDestroy() {
        this.mCalled = true;
        getAutofillClientController().onActivityDestroyed();
        if (this.mManagedDialogs != null) {
            int size = this.mManagedDialogs.size();
            for (int i = 0; i < size; i++) {
                android.app.Activity.ManagedDialog valueAt = this.mManagedDialogs.valueAt(i);
                if (valueAt.mDialog.isShowing()) {
                    valueAt.mDialog.dismiss();
                }
            }
            this.mManagedDialogs = null;
        }
        synchronized (this.mManagedCursors) {
            int size2 = this.mManagedCursors.size();
            for (int i2 = 0; i2 < size2; i2++) {
                android.app.Activity.ManagedCursor managedCursor = this.mManagedCursors.get(i2);
                if (managedCursor != null) {
                    managedCursor.mCursor.close();
                }
            }
            this.mManagedCursors.clear();
        }
        if (this.mSearchManager != null) {
            this.mSearchManager.stopSearch();
        }
        if (this.mActionBar != null) {
            this.mActionBar.onDestroy();
        }
        dispatchActivityDestroyed();
        notifyContentCaptureManagerIfNeeded(4);
        if (this.mUiTranslationController != null) {
            this.mUiTranslationController.onActivityDestroyed();
        }
        if (this.mDefaultBackCallback != null) {
            getOnBackInvokedDispatcher().unregisterOnBackInvokedCallback(this.mDefaultBackCallback);
            this.mDefaultBackCallback = null;
        }
        if (this.mCallbacksController != null) {
            this.mCallbacksController.clearCallbacks();
        }
    }

    public void reportFullyDrawn() {
        if (this.mDoReportFullyDrawn) {
            if (android.os.Trace.isTagEnabled(64L)) {
                android.os.Trace.traceBegin(64L, "reportFullyDrawn() for " + this.mComponent.toShortString());
            }
            this.mDoReportFullyDrawn = false;
            try {
                android.app.ActivityClient.getInstance().reportActivityFullyDrawn(this.mToken, this.mRestoredFromBundle);
                dalvik.system.VMRuntime.getRuntime().notifyStartupCompleted();
            } finally {
                android.os.Trace.traceEnd(64L);
            }
        }
    }

    public void onMultiWindowModeChanged(boolean z, android.content.res.Configuration configuration) {
        onMultiWindowModeChanged(z);
    }

    @java.lang.Deprecated
    public void onMultiWindowModeChanged(boolean z) {
    }

    public boolean isInMultiWindowMode() {
        return this.mIsInMultiWindowMode;
    }

    public void onPictureInPictureModeChanged(boolean z, android.content.res.Configuration configuration) {
        onPictureInPictureModeChanged(z);
    }

    public void onPictureInPictureUiStateChanged(android.app.PictureInPictureUiState pictureInPictureUiState) {
    }

    @java.lang.Deprecated
    public void onPictureInPictureModeChanged(boolean z) {
    }

    public boolean isInPictureInPictureMode() {
        return this.mIsInPictureInPictureMode;
    }

    @java.lang.Deprecated
    public void enterPictureInPictureMode() {
        enterPictureInPictureMode(new android.app.PictureInPictureParams.Builder().build());
    }

    public boolean enterPictureInPictureMode(android.app.PictureInPictureParams pictureInPictureParams) {
        if (!deviceSupportsPictureInPictureMode()) {
            return false;
        }
        if (pictureInPictureParams == null) {
            throw new java.lang.IllegalArgumentException("Expected non-null picture-in-picture params");
        }
        if (!this.mCanEnterPictureInPicture) {
            throw new java.lang.IllegalStateException("Activity must be resumed to enter picture-in-picture");
        }
        this.mIsInPictureInPictureMode = android.app.ActivityClient.getInstance().enterPictureInPictureMode(this.mToken, pictureInPictureParams);
        return this.mIsInPictureInPictureMode;
    }

    public void setPictureInPictureParams(android.app.PictureInPictureParams pictureInPictureParams) {
        if (!deviceSupportsPictureInPictureMode()) {
            return;
        }
        if (pictureInPictureParams == null) {
            throw new java.lang.IllegalArgumentException("Expected non-null picture-in-picture params");
        }
        android.app.ActivityClient.getInstance().setPictureInPictureParams(this.mToken, pictureInPictureParams);
    }

    public int getMaxNumPictureInPictureActions() {
        return android.app.ActivityTaskManager.getMaxNumPictureInPictureActions(this);
    }

    private boolean deviceSupportsPictureInPictureMode() {
        return getPackageManager().hasSystemFeature(android.content.pm.PackageManager.FEATURE_PICTURE_IN_PICTURE);
    }

    public boolean onPictureInPictureRequested() {
        return false;
    }

    public void requestFullscreenMode(int i, android.os.OutcomeReceiver<java.lang.Void, java.lang.Throwable> outcomeReceiver) {
        android.app.FullscreenRequestHandler.requestFullscreenMode(i, outcomeReceiver, this.mCurrentConfig, getActivityToken());
    }

    public void setShouldDockBigOverlays(boolean z) {
        android.app.ActivityClient.getInstance().setShouldDockBigOverlays(this.mToken, z);
        this.mShouldDockBigOverlays = z;
    }

    public boolean shouldDockBigOverlays() {
        return this.mShouldDockBigOverlays;
    }

    void dispatchMovedToDisplay(int i, android.content.res.Configuration configuration) {
        updateDisplay(i);
        onMovedToDisplay(i, configuration);
    }

    public void onMovedToDisplay(int i, android.content.res.Configuration configuration) {
    }

    @Override // android.content.ComponentCallbacks
    public void onConfigurationChanged(android.content.res.Configuration configuration) {
        this.mCalled = true;
        this.mFragments.dispatchConfigurationChanged(configuration);
        if (this.mWindow != null) {
            this.mWindow.onConfigurationChanged(configuration);
        }
        if (this.mActionBar != null) {
            this.mActionBar.onConfigurationChanged(configuration);
        }
        dispatchActivityConfigurationChanged();
        if (this.mCallbacksController != null) {
            this.mCallbacksController.dispatchConfigurationChanged(configuration);
        }
    }

    public int getChangingConfigurations() {
        return this.mConfigChangeFlags;
    }

    public java.lang.Object getLastNonConfigurationInstance() {
        if (this.mLastNonConfigurationInstances != null) {
            return this.mLastNonConfigurationInstances.activity;
        }
        return null;
    }

    public java.lang.Object onRetainNonConfigurationInstance() {
        return null;
    }

    java.util.HashMap<java.lang.String, java.lang.Object> getLastNonConfigurationChildInstances() {
        if (this.mLastNonConfigurationInstances != null) {
            return this.mLastNonConfigurationInstances.children;
        }
        return null;
    }

    java.util.HashMap<java.lang.String, java.lang.Object> onRetainNonConfigurationChildInstances() {
        return null;
    }

    android.app.Activity.NonConfigurationInstances retainNonConfigurationInstances() {
        java.lang.Object onRetainNonConfigurationInstance = onRetainNonConfigurationInstance();
        java.util.HashMap<java.lang.String, java.lang.Object> onRetainNonConfigurationChildInstances = onRetainNonConfigurationChildInstances();
        android.app.FragmentManagerNonConfig retainNestedNonConfig = this.mFragments.retainNestedNonConfig();
        this.mFragments.doLoaderStart();
        this.mFragments.doLoaderStop(true);
        android.util.ArrayMap<java.lang.String, android.app.LoaderManager> retainLoaderNonConfig = this.mFragments.retainLoaderNonConfig();
        if (onRetainNonConfigurationInstance == null && onRetainNonConfigurationChildInstances == null && retainNestedNonConfig == null && retainLoaderNonConfig == null && this.mVoiceInteractor == null) {
            return null;
        }
        android.app.Activity.NonConfigurationInstances nonConfigurationInstances = new android.app.Activity.NonConfigurationInstances();
        nonConfigurationInstances.activity = onRetainNonConfigurationInstance;
        nonConfigurationInstances.children = onRetainNonConfigurationChildInstances;
        nonConfigurationInstances.fragments = retainNestedNonConfig;
        nonConfigurationInstances.loaders = retainLoaderNonConfig;
        if (this.mVoiceInteractor != null) {
            this.mVoiceInteractor.retainInstance();
            nonConfigurationInstances.voiceInteractor = this.mVoiceInteractor;
        }
        return nonConfigurationInstances;
    }

    @Override // android.content.ComponentCallbacks
    public void onLowMemory() {
        this.mCalled = true;
        this.mFragments.dispatchLowMemory();
        if (this.mCallbacksController != null) {
            this.mCallbacksController.dispatchLowMemory();
        }
    }

    @Override // android.content.ComponentCallbacks2
    public void onTrimMemory(int i) {
        this.mCalled = true;
        this.mFragments.dispatchTrimMemory(i);
        if (this.mCallbacksController != null) {
            this.mCallbacksController.dispatchTrimMemory(i);
        }
    }

    @java.lang.Deprecated
    public android.app.FragmentManager getFragmentManager() {
        return this.mFragments.getFragmentManager();
    }

    @java.lang.Deprecated
    public void onAttachFragment(android.app.Fragment fragment) {
    }

    @java.lang.Deprecated
    public final android.database.Cursor managedQuery(android.net.Uri uri, java.lang.String[] strArr, java.lang.String str, java.lang.String str2) {
        android.database.Cursor query = getContentResolver().query(uri, strArr, str, null, str2);
        if (query != null) {
            startManagingCursor(query);
        }
        return query;
    }

    @java.lang.Deprecated
    public final android.database.Cursor managedQuery(android.net.Uri uri, java.lang.String[] strArr, java.lang.String str, java.lang.String[] strArr2, java.lang.String str2) {
        android.database.Cursor query = getContentResolver().query(uri, strArr, str, strArr2, str2);
        if (query != null) {
            startManagingCursor(query);
        }
        return query;
    }

    @java.lang.Deprecated
    public void startManagingCursor(android.database.Cursor cursor) {
        synchronized (this.mManagedCursors) {
            this.mManagedCursors.add(new android.app.Activity.ManagedCursor(cursor));
        }
    }

    @java.lang.Deprecated
    public void stopManagingCursor(android.database.Cursor cursor) {
        synchronized (this.mManagedCursors) {
            int size = this.mManagedCursors.size();
            int i = 0;
            while (true) {
                if (i >= size) {
                    break;
                }
                if (this.mManagedCursors.get(i).mCursor != cursor) {
                    i++;
                } else {
                    this.mManagedCursors.remove(i);
                    break;
                }
            }
        }
    }

    @java.lang.Deprecated
    public void setPersistent(boolean z) {
    }

    public <T extends android.view.View> T findViewById(int i) {
        return (T) getWindow().findViewById(i);
    }

    public final <T extends android.view.View> T requireViewById(int i) {
        T t = (T) findViewById(i);
        if (t == null) {
            throw new java.lang.IllegalArgumentException("ID does not reference a View inside this Activity");
        }
        return t;
    }

    public android.app.ActionBar getActionBar() {
        initWindowDecorActionBar();
        return this.mActionBar;
    }

    public void setActionBar(android.widget.Toolbar toolbar) {
        android.app.ActionBar actionBar = getActionBar();
        if (actionBar instanceof com.android.internal.app.WindowDecorActionBar) {
            throw new java.lang.IllegalStateException("This Activity already has an action bar supplied by the window decor. Do not request Window.FEATURE_ACTION_BAR and set android:windowActionBar to false in your theme to use a Toolbar instead.");
        }
        this.mMenuInflater = null;
        if (actionBar != null) {
            actionBar.onDestroy();
        }
        if (toolbar != null) {
            com.android.internal.app.ToolbarActionBar toolbarActionBar = new com.android.internal.app.ToolbarActionBar(toolbar, getTitle(), this);
            this.mActionBar = toolbarActionBar;
            this.mWindow.setCallback(toolbarActionBar.getWrappedWindowCallback());
        } else {
            this.mActionBar = null;
            this.mWindow.setCallback(this);
        }
        invalidateOptionsMenu();
    }

    private void initWindowDecorActionBar() {
        android.view.Window window = getWindow();
        window.getDecorView();
        if (isChild() || !window.hasFeature(8) || this.mActionBar != null) {
            return;
        }
        this.mActionBar = new com.android.internal.app.WindowDecorActionBar(this);
        this.mActionBar.setDefaultDisplayHomeAsUpEnabled(this.mEnableDefaultActionBarUp);
        this.mWindow.setDefaultIcon(this.mActivityInfo.getIconResource());
        this.mWindow.setDefaultLogo(this.mActivityInfo.getLogoResource());
    }

    public void setContentView(int i) {
        getWindow().setContentView(i);
        initWindowDecorActionBar();
    }

    public void setContentView(android.view.View view) {
        getWindow().setContentView(view);
        initWindowDecorActionBar();
    }

    public void setContentView(android.view.View view, android.view.ViewGroup.LayoutParams layoutParams) {
        getWindow().setContentView(view, layoutParams);
        initWindowDecorActionBar();
    }

    public void addContentView(android.view.View view, android.view.ViewGroup.LayoutParams layoutParams) {
        getWindow().addContentView(view, layoutParams);
        initWindowDecorActionBar();
    }

    public android.transition.TransitionManager getContentTransitionManager() {
        return getWindow().getTransitionManager();
    }

    public void setContentTransitionManager(android.transition.TransitionManager transitionManager) {
        getWindow().setTransitionManager(transitionManager);
    }

    public android.transition.Scene getContentScene() {
        return getWindow().getContentScene();
    }

    public void setFinishOnTouchOutside(boolean z) {
        this.mWindow.setCloseOnTouchOutside(z);
    }

    public final void setDefaultKeyMode(int i) {
        this.mDefaultKeyMode = i;
        switch (i) {
            case 0:
            case 2:
                this.mDefaultKeySsb = null;
                return;
            case 1:
            case 3:
            case 4:
                this.mDefaultKeySsb = new android.text.SpannableStringBuilder();
                android.text.Selection.setSelection(this.mDefaultKeySsb, 0);
                return;
            default:
                throw new java.lang.IllegalArgumentException();
        }
    }

    @Override // android.view.KeyEvent.Callback
    public boolean onKeyDown(int i, android.view.KeyEvent keyEvent) {
        boolean z;
        boolean z2 = true;
        if (i == 4) {
            if (getApplicationInfo().targetSdkVersion >= 5) {
                keyEvent.startTracking();
            } else {
                onBackPressed();
            }
            return true;
        }
        if (i == 111 && this.mWindow.shouldCloseOnTouchOutside()) {
            keyEvent.startTracking();
            finish();
            return true;
        }
        if (this.mDefaultKeyMode == 0) {
            return false;
        }
        if (this.mDefaultKeyMode == 2) {
            android.view.Window window = getWindow();
            return window.hasFeature(0) && window.performPanelShortcut(0, i, keyEvent, 2);
        }
        if (i == 61) {
            return false;
        }
        if (keyEvent.getRepeatCount() != 0 || keyEvent.isSystem()) {
            z = false;
        } else {
            z = android.text.method.TextKeyListener.getInstance().onKeyDown(null, this.mDefaultKeySsb, i, keyEvent);
            if (z && this.mDefaultKeySsb.length() > 0) {
                java.lang.String spannableStringBuilder = this.mDefaultKeySsb.toString();
                switch (this.mDefaultKeyMode) {
                    case 1:
                        android.content.Intent intent = new android.content.Intent(android.content.Intent.ACTION_DIAL, android.net.Uri.parse(android.webkit.WebView.SCHEME_TEL + spannableStringBuilder));
                        intent.addFlags(268435456);
                        startActivity(intent);
                        break;
                    case 3:
                        startSearch(spannableStringBuilder, false, null, false);
                        break;
                    case 4:
                        startSearch(spannableStringBuilder, false, null, true);
                        break;
                }
            } else {
                z2 = false;
            }
        }
        if (z2) {
            this.mDefaultKeySsb.clear();
            this.mDefaultKeySsb.clearSpans();
            android.text.Selection.setSelection(this.mDefaultKeySsb, 0);
        }
        return z;
    }

    @Override // android.view.KeyEvent.Callback
    public boolean onKeyLongPress(int i, android.view.KeyEvent keyEvent) {
        return false;
    }

    @Override // android.view.KeyEvent.Callback
    public boolean onKeyUp(int i, android.view.KeyEvent keyEvent) {
        if (getApplicationInfo().targetSdkVersion < 5 || i != 4 || !keyEvent.isTracking() || keyEvent.isCanceled() || this.mDefaultBackCallback != null) {
            return i == 111 && keyEvent.isTracking();
        }
        onBackPressed();
        return true;
    }

    @Override // android.view.KeyEvent.Callback
    public boolean onKeyMultiple(int i, int i2, android.view.KeyEvent keyEvent) {
        return false;
    }

    private static final class RequestFinishCallback extends android.app.IRequestFinishCallback.Stub {
        private final java.lang.ref.WeakReference<android.app.Activity> mActivityRef;

        RequestFinishCallback(java.lang.ref.WeakReference<android.app.Activity> weakReference) {
            this.mActivityRef = weakReference;
        }

        @Override // android.app.IRequestFinishCallback
        public void requestFinish() {
            final android.app.Activity activity = this.mActivityRef.get();
            if (activity != null) {
                android.os.Handler handler = activity.mHandler;
                java.util.Objects.requireNonNull(activity);
                handler.post(new java.lang.Runnable() { // from class: android.app.Activity$RequestFinishCallback$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.app.Activity.this.finishAfterTransition();
                    }
                });
            }
        }
    }

    @java.lang.Deprecated
    public void onBackPressed() {
        if (this.mActionBar != null && this.mActionBar.collapseActionView()) {
            return;
        }
        android.app.FragmentManager fragmentManager = this.mFragments.getFragmentManager();
        if (!fragmentManager.isStateSaved() && fragmentManager.popBackStackImmediate()) {
            return;
        }
        onBackInvoked();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onBackInvoked() {
        android.app.ActivityClient.getInstance().onBackPressed(this.mToken, new android.app.Activity.RequestFinishCallback(new java.lang.ref.WeakReference(this)));
        if (isTaskRoot()) {
            getAutofillClientController().onActivityBackPressed(this.mIntent);
        }
    }

    public boolean onKeyShortcut(int i, android.view.KeyEvent keyEvent) {
        android.app.ActionBar actionBar = getActionBar();
        return actionBar != null && actionBar.onKeyShortcut(i, keyEvent);
    }

    public boolean onTouchEvent(android.view.MotionEvent motionEvent) {
        if (this.mWindow.shouldCloseOnTouch(this, motionEvent)) {
            finish();
            return true;
        }
        return false;
    }

    public boolean onTrackballEvent(android.view.MotionEvent motionEvent) {
        return false;
    }

    public boolean onGenericMotionEvent(android.view.MotionEvent motionEvent) {
        return false;
    }

    public void onUserInteraction() {
    }

    @Override // android.view.Window.Callback
    public void onWindowAttributesChanged(android.view.WindowManager.LayoutParams layoutParams) {
        android.view.View view;
        if (this.mParent == null && (view = this.mDecor) != null && view.getParent() != null) {
            getWindowManager().updateViewLayout(view, layoutParams);
            if (this.mContentCaptureManager != null) {
                this.mContentCaptureManager.updateWindowAttributes(layoutParams);
            }
        }
    }

    @Override // android.view.Window.Callback
    public void onContentChanged() {
    }

    @Override // android.view.Window.Callback
    public void onWindowFocusChanged(boolean z) {
    }

    @Override // android.view.Window.Callback
    public void onAttachedToWindow() {
    }

    @Override // android.view.Window.Callback
    public void onDetachedFromWindow() {
    }

    public boolean hasWindowFocus() {
        android.view.View decorView;
        android.view.Window window = getWindow();
        if (window != null && (decorView = window.getDecorView()) != null) {
            return decorView.hasWindowFocus();
        }
        return false;
    }

    @Override // android.view.Window.OnWindowDismissedCallback
    public void onWindowDismissed(boolean z, boolean z2) {
        finish(z ? 2 : 0);
        if (z2) {
            overridePendingTransition(0, 0);
        }
    }

    @Override // android.view.Window.Callback
    public boolean dispatchKeyEvent(android.view.KeyEvent keyEvent) {
        onUserInteraction();
        if (keyEvent.getKeyCode() == 82 && this.mActionBar != null && this.mActionBar.onMenuKeyEvent(keyEvent)) {
            return true;
        }
        android.view.Window window = getWindow();
        if (window.superDispatchKeyEvent(keyEvent)) {
            return true;
        }
        android.view.View view = this.mDecor;
        if (view == null) {
            view = window.getDecorView();
        }
        return keyEvent.dispatch(this, view != null ? view.getKeyDispatcherState() : null, this);
    }

    @Override // android.view.Window.Callback
    public boolean dispatchKeyShortcutEvent(android.view.KeyEvent keyEvent) {
        onUserInteraction();
        if (getWindow().superDispatchKeyShortcutEvent(keyEvent)) {
            return true;
        }
        return onKeyShortcut(keyEvent.getKeyCode(), keyEvent);
    }

    @Override // android.view.Window.Callback
    public boolean dispatchTouchEvent(android.view.MotionEvent motionEvent) {
        if (motionEvent.getAction() == 0) {
            onUserInteraction();
        }
        if (getWindow().superDispatchTouchEvent(motionEvent)) {
            return true;
        }
        return onTouchEvent(motionEvent);
    }

    @Override // android.view.Window.Callback
    public boolean dispatchTrackballEvent(android.view.MotionEvent motionEvent) {
        onUserInteraction();
        if (getWindow().superDispatchTrackballEvent(motionEvent)) {
            return true;
        }
        return onTrackballEvent(motionEvent);
    }

    @Override // android.view.Window.Callback
    public boolean dispatchGenericMotionEvent(android.view.MotionEvent motionEvent) {
        onUserInteraction();
        if (getWindow().superDispatchGenericMotionEvent(motionEvent)) {
            return true;
        }
        return onGenericMotionEvent(motionEvent);
    }

    @Override // android.view.Window.Callback
    public boolean dispatchPopulateAccessibilityEvent(android.view.accessibility.AccessibilityEvent accessibilityEvent) {
        accessibilityEvent.setClassName(getClass().getName());
        accessibilityEvent.setPackageName(getPackageName());
        android.view.WindowManager.LayoutParams attributes = getWindow().getAttributes();
        accessibilityEvent.setFullScreen(attributes.width == -1 && attributes.height == -1);
        java.lang.CharSequence title = getTitle();
        if (!android.text.TextUtils.isEmpty(title)) {
            accessibilityEvent.getText().add(title);
        }
        return true;
    }

    @Override // android.view.Window.Callback
    public android.view.View onCreatePanelView(int i) {
        return null;
    }

    @Override // android.view.Window.Callback
    public boolean onCreatePanelMenu(int i, android.view.Menu menu) {
        if (i == 0) {
            return onCreateOptionsMenu(menu) | this.mFragments.dispatchCreateOptionsMenu(menu, getMenuInflater());
        }
        return false;
    }

    @Override // android.view.Window.Callback
    public boolean onPreparePanel(int i, android.view.View view, android.view.Menu menu) {
        if (i == 0) {
            return onPrepareOptionsMenu(menu) | this.mFragments.dispatchPrepareOptionsMenu(menu);
        }
        return true;
    }

    @Override // android.view.Window.Callback
    public boolean onMenuOpened(int i, android.view.Menu menu) {
        if (i == 8) {
            initWindowDecorActionBar();
            if (this.mActionBar != null) {
                this.mActionBar.dispatchMenuVisibilityChanged(true);
            } else {
                android.util.Log.e(TAG, "Tried to open action bar menu with no action bar");
            }
        }
        return true;
    }

    @Override // android.view.Window.Callback
    public boolean onMenuItemSelected(int i, android.view.MenuItem menuItem) {
        java.lang.CharSequence titleCondensed = menuItem.getTitleCondensed();
        switch (i) {
            case 0:
                if (titleCondensed != null) {
                    android.util.EventLog.writeEvent(50000, 0, titleCondensed.toString());
                }
                if (onOptionsItemSelected(menuItem) || this.mFragments.dispatchOptionsItemSelected(menuItem)) {
                    return true;
                }
                if (menuItem.getItemId() != 16908332 || this.mActionBar == null || (this.mActionBar.getDisplayOptions() & 4) == 0) {
                    return false;
                }
                if (this.mParent == null) {
                    return onNavigateUp();
                }
                return this.mParent.onNavigateUpFromChild(this);
            case 6:
                if (titleCondensed != null) {
                    android.util.EventLog.writeEvent(50000, 1, titleCondensed.toString());
                }
                if (onContextItemSelected(menuItem)) {
                    return true;
                }
                return this.mFragments.dispatchContextItemSelected(menuItem);
            default:
                return false;
        }
    }

    @Override // android.view.Window.Callback
    public void onPanelClosed(int i, android.view.Menu menu) {
        switch (i) {
            case 0:
                this.mFragments.dispatchOptionsMenuClosed(menu);
                onOptionsMenuClosed(menu);
                break;
            case 6:
                onContextMenuClosed(menu);
                break;
            case 8:
                initWindowDecorActionBar();
                this.mActionBar.dispatchMenuVisibilityChanged(false);
                break;
        }
    }

    public void invalidateOptionsMenu() {
        if (this.mWindow.hasFeature(0)) {
            if (this.mActionBar == null || !this.mActionBar.invalidateOptionsMenu()) {
                this.mWindow.invalidatePanelMenu(0);
            }
        }
    }

    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        if (this.mParent != null) {
            return this.mParent.onCreateOptionsMenu(menu);
        }
        return true;
    }

    public boolean onPrepareOptionsMenu(android.view.Menu menu) {
        if (this.mParent != null) {
            return this.mParent.onPrepareOptionsMenu(menu);
        }
        return true;
    }

    public boolean onOptionsItemSelected(android.view.MenuItem menuItem) {
        if (this.mParent != null) {
            return this.mParent.onOptionsItemSelected(menuItem);
        }
        return false;
    }

    public boolean onNavigateUp() {
        android.content.Intent parentActivityIntent = getParentActivityIntent();
        if (parentActivityIntent != null) {
            if (this.mActivityInfo.taskAffinity == null) {
                finish();
                return true;
            }
            if (shouldUpRecreateTask(parentActivityIntent)) {
                android.app.TaskStackBuilder create = android.app.TaskStackBuilder.create(this);
                onCreateNavigateUpTaskStack(create);
                onPrepareNavigateUpTaskStack(create);
                create.startActivities();
                if (this.mResultCode != 0 || this.mResultData != null) {
                    android.util.Log.i(TAG, "onNavigateUp only finishing topmost activity to return a result");
                    finish();
                    return true;
                }
                finishAffinity();
                return true;
            }
            navigateUpTo(parentActivityIntent);
            return true;
        }
        return false;
    }

    @java.lang.Deprecated
    public boolean onNavigateUpFromChild(android.app.Activity activity) {
        return onNavigateUp();
    }

    public void onCreateNavigateUpTaskStack(android.app.TaskStackBuilder taskStackBuilder) {
        taskStackBuilder.addParentStack(this);
    }

    public void onPrepareNavigateUpTaskStack(android.app.TaskStackBuilder taskStackBuilder) {
    }

    public void onOptionsMenuClosed(android.view.Menu menu) {
        if (this.mParent != null) {
            this.mParent.onOptionsMenuClosed(menu);
        }
    }

    public void openOptionsMenu() {
        if (this.mWindow.hasFeature(0)) {
            if (this.mActionBar == null || !this.mActionBar.openOptionsMenu()) {
                this.mWindow.openPanel(0, null);
            }
        }
    }

    public void closeOptionsMenu() {
        if (this.mWindow.hasFeature(0)) {
            if (this.mActionBar == null || !this.mActionBar.closeOptionsMenu()) {
                this.mWindow.closePanel(0);
            }
        }
    }

    @Override // android.view.View.OnCreateContextMenuListener
    public void onCreateContextMenu(android.view.ContextMenu contextMenu, android.view.View view, android.view.ContextMenu.ContextMenuInfo contextMenuInfo) {
    }

    public void registerForContextMenu(android.view.View view) {
        view.setOnCreateContextMenuListener(this);
    }

    public void unregisterForContextMenu(android.view.View view) {
        view.setOnCreateContextMenuListener(null);
    }

    public void openContextMenu(android.view.View view) {
        view.showContextMenu();
    }

    public void closeContextMenu() {
        if (this.mWindow.hasFeature(6)) {
            this.mWindow.closePanel(6);
        }
    }

    public boolean onContextItemSelected(android.view.MenuItem menuItem) {
        if (this.mParent != null) {
            return this.mParent.onContextItemSelected(menuItem);
        }
        return false;
    }

    public void onContextMenuClosed(android.view.Menu menu) {
        if (this.mParent != null) {
            this.mParent.onContextMenuClosed(menu);
        }
    }

    @java.lang.Deprecated
    protected android.app.Dialog onCreateDialog(int i) {
        return null;
    }

    @java.lang.Deprecated
    protected android.app.Dialog onCreateDialog(int i, android.os.Bundle bundle) {
        return onCreateDialog(i);
    }

    @java.lang.Deprecated
    protected void onPrepareDialog(int i, android.app.Dialog dialog) {
        dialog.setOwnerActivity(this);
    }

    @java.lang.Deprecated
    protected void onPrepareDialog(int i, android.app.Dialog dialog, android.os.Bundle bundle) {
        onPrepareDialog(i, dialog);
    }

    @java.lang.Deprecated
    public final void showDialog(int i) {
        showDialog(i, null);
    }

    @java.lang.Deprecated
    public final boolean showDialog(int i, android.os.Bundle bundle) {
        if (this.mManagedDialogs == null) {
            this.mManagedDialogs = new android.util.SparseArray<>();
        }
        android.app.Activity.ManagedDialog managedDialog = this.mManagedDialogs.get(i);
        if (managedDialog == null) {
            managedDialog = new android.app.Activity.ManagedDialog();
            managedDialog.mDialog = createDialog(java.lang.Integer.valueOf(i), null, bundle);
            if (managedDialog.mDialog == null) {
                return false;
            }
            this.mManagedDialogs.put(i, managedDialog);
        }
        managedDialog.mArgs = bundle;
        onPrepareDialog(i, managedDialog.mDialog, bundle);
        managedDialog.mDialog.show();
        return true;
    }

    @java.lang.Deprecated
    public final void dismissDialog(int i) {
        if (this.mManagedDialogs == null) {
            throw missingDialog(i);
        }
        android.app.Activity.ManagedDialog managedDialog = this.mManagedDialogs.get(i);
        if (managedDialog == null) {
            throw missingDialog(i);
        }
        managedDialog.mDialog.dismiss();
    }

    private java.lang.IllegalArgumentException missingDialog(int i) {
        return new java.lang.IllegalArgumentException("no dialog with id " + i + " was ever shown via Activity#showDialog");
    }

    @java.lang.Deprecated
    public final void removeDialog(int i) {
        android.app.Activity.ManagedDialog managedDialog;
        if (this.mManagedDialogs != null && (managedDialog = this.mManagedDialogs.get(i)) != null) {
            managedDialog.mDialog.dismiss();
            this.mManagedDialogs.remove(i);
        }
    }

    @Override // android.view.Window.Callback
    public boolean onSearchRequested(android.view.SearchEvent searchEvent) {
        this.mSearchEvent = searchEvent;
        boolean onSearchRequested = onSearchRequested();
        this.mSearchEvent = null;
        return onSearchRequested;
    }

    @Override // android.view.Window.Callback
    public boolean onSearchRequested() {
        int i = getResources().getConfiguration().uiMode & 15;
        if (i == 4 || i == 6) {
            return false;
        }
        startSearch(null, false, null, false);
        return true;
    }

    public final android.view.SearchEvent getSearchEvent() {
        return this.mSearchEvent;
    }

    public void startSearch(java.lang.String str, boolean z, android.os.Bundle bundle, boolean z2) {
        ensureSearchManager();
        this.mSearchManager.startSearch(str, z, getComponentName(), bundle, z2);
    }

    public void triggerSearch(java.lang.String str, android.os.Bundle bundle) {
        ensureSearchManager();
        this.mSearchManager.triggerSearch(str, getComponentName(), bundle);
    }

    public void takeKeyEvents(boolean z) {
        getWindow().takeKeyEvents(z);
    }

    public final boolean requestWindowFeature(int i) {
        return getWindow().requestFeature(i);
    }

    public final void setFeatureDrawableResource(int i, int i2) {
        getWindow().setFeatureDrawableResource(i, i2);
    }

    public final void setFeatureDrawableUri(int i, android.net.Uri uri) {
        getWindow().setFeatureDrawableUri(i, uri);
    }

    public final void setFeatureDrawable(int i, android.graphics.drawable.Drawable drawable) {
        getWindow().setFeatureDrawable(i, drawable);
    }

    public final void setFeatureDrawableAlpha(int i, int i2) {
        getWindow().setFeatureDrawableAlpha(i, i2);
    }

    public android.view.LayoutInflater getLayoutInflater() {
        return getWindow().getLayoutInflater();
    }

    public android.view.MenuInflater getMenuInflater() {
        if (this.mMenuInflater == null) {
            initWindowDecorActionBar();
            if (this.mActionBar != null) {
                this.mMenuInflater = new android.view.MenuInflater(this.mActionBar.getThemedContext(), this);
            } else {
                this.mMenuInflater = new android.view.MenuInflater(this);
            }
        }
        return this.mMenuInflater;
    }

    @Override // android.view.ContextThemeWrapper, android.content.ContextWrapper, android.content.Context
    public void setTheme(int i) {
        super.setTheme(i);
        this.mWindow.setTheme(i);
    }

    @Override // android.view.ContextThemeWrapper
    protected void onApplyThemeResource(android.content.res.Resources.Theme theme, int i, boolean z) {
        int color;
        if (this.mParent == null) {
            super.onApplyThemeResource(theme, i, z);
        } else {
            try {
                theme.setTo(this.mParent.getTheme());
            } catch (java.lang.Exception e) {
            }
            theme.applyStyle(i, false);
        }
        android.content.res.TypedArray obtainStyledAttributes = theme.obtainStyledAttributes(com.android.internal.R.styleable.ActivityTaskDescription);
        if (this.mTaskDescription.getPrimaryColor() == 0 && (color = obtainStyledAttributes.getColor(1, 0)) != 0 && android.graphics.Color.alpha(color) == 255) {
            this.mTaskDescription.setPrimaryColor(color);
        }
        int color2 = obtainStyledAttributes.getColor(0, 0);
        if (color2 != 0 && android.graphics.Color.alpha(color2) == 255) {
            this.mTaskDescription.setBackgroundColor(color2);
        }
        int color3 = obtainStyledAttributes.getColor(4, 0);
        if (color3 != 0 && android.graphics.Color.alpha(color3) == 255) {
            this.mTaskDescription.setBackgroundColorFloating(color3);
        }
        int color4 = obtainStyledAttributes.getColor(2, 0);
        if (color4 != 0) {
            this.mTaskDescription.setStatusBarColor(color4);
        }
        int color5 = obtainStyledAttributes.getColor(3, 0);
        if (color5 != 0) {
            this.mTaskDescription.setNavigationBarColor(color5);
        }
        if (!(getApplicationInfo().targetSdkVersion < 29)) {
            this.mTaskDescription.setEnsureStatusBarContrastWhenTransparent(obtainStyledAttributes.getBoolean(5, false));
            this.mTaskDescription.setEnsureNavigationBarContrastWhenTransparent(obtainStyledAttributes.getBoolean(6, true));
        }
        obtainStyledAttributes.recycle();
        setTaskDescription(this.mTaskDescription);
    }

    public final void requestPermissions(java.lang.String[] strArr, int i) {
        requestPermissions(strArr, i, getDeviceId());
    }

    public final void requestPermissions(java.lang.String[] strArr, int i, int i2) {
        if (i < 0) {
            throw new java.lang.IllegalArgumentException("requestCode should be >= 0");
        }
        if (this.mHasCurrentPermissionsRequest) {
            android.util.Log.w(TAG, "Can request only one set of permissions at a time");
            onRequestPermissionsResult(i, new java.lang.String[0], new int[0], i2);
            return;
        }
        if (!getAttributionSource().getRenouncedPermissions().isEmpty()) {
            int length = strArr.length;
            for (int i3 = 0; i3 < length; i3++) {
                if (getAttributionSource().getRenouncedPermissions().contains(strArr[i3])) {
                    throw new java.lang.IllegalArgumentException("Cannot request renounced permission: " + strArr[i3]);
                }
            }
        }
        startActivityForResult(REQUEST_PERMISSIONS_WHO_PREFIX, (getDeviceId() == i2 ? getPackageManager() : createDeviceContext(i2).getPackageManager()).buildRequestPermissionsIntent(strArr), i, null);
        this.mHasCurrentPermissionsRequest = true;
    }

    public void onRequestPermissionsResult(int i, java.lang.String[] strArr, int[] iArr) {
    }

    public void onRequestPermissionsResult(int i, java.lang.String[] strArr, int[] iArr, int i2) {
        onRequestPermissionsResult(i, strArr, iArr);
    }

    public boolean shouldShowRequestPermissionRationale(java.lang.String str) {
        return getPackageManager().shouldShowRequestPermissionRationale(str);
    }

    public boolean shouldShowRequestPermissionRationale(java.lang.String str, int i) {
        return (getDeviceId() == i ? getPackageManager() : createDeviceContext(i).getPackageManager()).shouldShowRequestPermissionRationale(str);
    }

    public void startActivityForResult(android.content.Intent intent, int i) {
        startActivityForResult(intent, i, null);
    }

    public void startActivityForResult(android.content.Intent intent, int i, android.os.Bundle bundle) {
        if (this.mParent == null) {
            android.os.Bundle transferSpringboardActivityOptions = transferSpringboardActivityOptions(bundle);
            android.app.Instrumentation.ActivityResult execStartActivity = this.mInstrumentation.execStartActivity(this, this.mMainThread.getApplicationThread(), this.mToken, this, intent, i, transferSpringboardActivityOptions);
            if (execStartActivity != null) {
                this.mMainThread.sendActivityResult(this.mToken, this.mEmbeddedID, i, execStartActivity.getResultCode(), execStartActivity.getResultData());
            }
            if (i >= 0) {
                this.mStartedActivity = true;
            }
            cancelInputsAndStartExitTransition(transferSpringboardActivityOptions);
            return;
        }
        if (bundle != null) {
            this.mParent.startActivityFromChild(this, intent, i, bundle);
        } else {
            this.mParent.startActivityFromChild(this, intent, i);
        }
    }

    private void cancelInputsAndStartExitTransition(android.os.Bundle bundle) {
        android.view.View peekDecorView = this.mWindow != null ? this.mWindow.peekDecorView() : null;
        if (peekDecorView != null) {
            peekDecorView.cancelPendingInputEvents();
        }
        if (bundle != null) {
            this.mActivityTransitionState.startExitOutTransition(this, bundle);
        }
    }

    public boolean isActivityTransitionRunning() {
        return this.mActivityTransitionState.isTransitionRunning();
    }

    private android.os.Bundle transferSpringboardActivityOptions(android.os.Bundle bundle) {
        android.app.ActivityOptions.SceneTransitionInfo sceneTransitionInfo;
        if (bundle == null && this.mWindow != null && !this.mWindow.isActive() && (sceneTransitionInfo = getSceneTransitionInfo()) != null) {
            return android.app.ActivityOptions.makeBasic().setSceneTransitionInfo(sceneTransitionInfo).toBundle();
        }
        return bundle;
    }

    @android.annotation.SystemApi
    public void startActivityForResultAsUser(android.content.Intent intent, int i, android.os.UserHandle userHandle) {
        startActivityForResultAsUser(intent, i, null, userHandle);
    }

    @android.annotation.SystemApi
    public void startActivityForResultAsUser(android.content.Intent intent, int i, android.os.Bundle bundle, android.os.UserHandle userHandle) {
        startActivityForResultAsUser(intent, this.mEmbeddedID, i, bundle, userHandle);
    }

    @android.annotation.SystemApi
    public void startActivityForResultAsUser(android.content.Intent intent, java.lang.String str, int i, android.os.Bundle bundle, android.os.UserHandle userHandle) {
        if (this.mParent != null) {
            throw new java.lang.RuntimeException("Can't be called from a child");
        }
        android.os.Bundle transferSpringboardActivityOptions = transferSpringboardActivityOptions(bundle);
        android.app.Instrumentation.ActivityResult execStartActivity = this.mInstrumentation.execStartActivity(this, this.mMainThread.getApplicationThread(), this.mToken, str, intent, i, transferSpringboardActivityOptions, userHandle);
        if (execStartActivity != null) {
            this.mMainThread.sendActivityResult(this.mToken, this.mEmbeddedID, i, execStartActivity.getResultCode(), execStartActivity.getResultData());
        }
        if (i >= 0) {
            this.mStartedActivity = true;
        }
        cancelInputsAndStartExitTransition(transferSpringboardActivityOptions);
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public void startActivityAsUser(android.content.Intent intent, android.os.UserHandle userHandle) {
        startActivityAsUser(intent, null, userHandle);
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public void startActivityAsUser(android.content.Intent intent, android.os.Bundle bundle, android.os.UserHandle userHandle) {
        if (this.mParent != null) {
            throw new java.lang.RuntimeException("Can't be called from a child");
        }
        android.os.Bundle transferSpringboardActivityOptions = transferSpringboardActivityOptions(bundle);
        android.app.Instrumentation.ActivityResult execStartActivity = this.mInstrumentation.execStartActivity(this, this.mMainThread.getApplicationThread(), this.mToken, this.mEmbeddedID, intent, -1, transferSpringboardActivityOptions, userHandle);
        if (execStartActivity != null) {
            this.mMainThread.sendActivityResult(this.mToken, this.mEmbeddedID, -1, execStartActivity.getResultCode(), execStartActivity.getResultData());
        }
        cancelInputsAndStartExitTransition(transferSpringboardActivityOptions);
    }

    public void startActivityAsCaller(android.content.Intent intent, android.os.Bundle bundle, boolean z, int i) {
        startActivityAsCaller(intent, bundle, z, i, -1);
    }

    public void startActivityAsCaller(android.content.Intent intent, android.os.Bundle bundle, boolean z, int i, int i2) {
        if (this.mParent != null) {
            throw new java.lang.RuntimeException("Can't be called from a child");
        }
        android.os.Bundle transferSpringboardActivityOptions = transferSpringboardActivityOptions(bundle);
        android.app.Instrumentation.ActivityResult execStartActivityAsCaller = this.mInstrumentation.execStartActivityAsCaller(this, this.mMainThread.getApplicationThread(), this.mToken, this, intent, i2, transferSpringboardActivityOptions, z, i);
        if (execStartActivityAsCaller != null) {
            this.mMainThread.sendActivityResult(this.mToken, this.mEmbeddedID, i2, execStartActivityAsCaller.getResultCode(), execStartActivityAsCaller.getResultData());
        }
        cancelInputsAndStartExitTransition(transferSpringboardActivityOptions);
    }

    public void startIntentSenderForResult(android.content.IntentSender intentSender, int i, android.content.Intent intent, int i2, int i3, int i4) throws android.content.IntentSender.SendIntentException {
        startIntentSenderForResult(intentSender, i, intent, i2, i3, i4, (android.os.Bundle) null);
    }

    public void startIntentSenderForResult(android.content.IntentSender intentSender, java.lang.String str, int i, android.content.Intent intent, int i2, int i3, android.os.Bundle bundle) throws android.content.IntentSender.SendIntentException {
        startIntentSenderForResultInner(intentSender, str, i, intent, i2, i3, bundle);
    }

    public void startIntentSenderForResult(android.content.IntentSender intentSender, int i, android.content.Intent intent, int i2, int i3, int i4, android.os.Bundle bundle) throws android.content.IntentSender.SendIntentException {
        if (this.mParent == null) {
            startIntentSenderForResultInner(intentSender, this.mEmbeddedID, i, intent, i2, i3, bundle);
        } else if (bundle != null) {
            this.mParent.startIntentSenderFromChild(this, intentSender, i, intent, i2, i3, i4, bundle);
        } else {
            this.mParent.startIntentSenderFromChild(this, intentSender, i, intent, i2, i3, i4);
        }
    }

    public void startIntentSenderForResultInner(android.content.IntentSender intentSender, java.lang.String str, int i, android.content.Intent intent, int i2, int i3, android.os.Bundle bundle) throws android.content.IntentSender.SendIntentException {
        android.os.Bundle transferSpringboardActivityOptions;
        java.lang.String str2;
        int startActivityIntentSender;
        try {
            transferSpringboardActivityOptions = transferSpringboardActivityOptions(bundle);
            if (intent == null) {
                str2 = null;
            } else {
                intent.migrateExtraStreamToClipData(this);
                intent.prepareToLeaveProcess(this);
                str2 = intent.resolveTypeIfNeeded(getContentResolver());
            }
            startActivityIntentSender = android.app.ActivityTaskManager.getService().startActivityIntentSender(this.mMainThread.getApplicationThread(), intentSender != null ? intentSender.getTarget() : null, intentSender != null ? intentSender.getWhitelistToken() : null, intent, str2, this.mToken, str, i, i2, i3, transferSpringboardActivityOptions);
        } catch (android.os.RemoteException e) {
        }
        if (startActivityIntentSender == -96) {
            throw new android.content.IntentSender.SendIntentException();
        }
        android.app.Instrumentation.checkStartActivityResult(startActivityIntentSender, null);
        if (transferSpringboardActivityOptions != null) {
            cancelInputsAndStartExitTransition(transferSpringboardActivityOptions);
        }
        if (i >= 0) {
            this.mStartedActivity = true;
        }
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public void startActivity(android.content.Intent intent) {
        startActivity(intent, null);
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public void startActivity(android.content.Intent intent, android.os.Bundle bundle) {
        getAutofillClientController().onStartActivity(intent, this.mIntent);
        if (bundle != null) {
            startActivityForResult(intent, -1, bundle);
        } else {
            startActivityForResult(intent, -1);
        }
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public void startActivities(android.content.Intent[] intentArr) {
        startActivities(intentArr, null);
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public void startActivities(android.content.Intent[] intentArr, android.os.Bundle bundle) {
        this.mInstrumentation.execStartActivities(this, this.mMainThread.getApplicationThread(), this.mToken, this, intentArr, bundle);
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public void startIntentSender(android.content.IntentSender intentSender, android.content.Intent intent, int i, int i2, int i3) throws android.content.IntentSender.SendIntentException {
        startIntentSender(intentSender, intent, i, i2, i3, null);
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public void startIntentSender(android.content.IntentSender intentSender, android.content.Intent intent, int i, int i2, int i3, android.os.Bundle bundle) throws android.content.IntentSender.SendIntentException {
        if (bundle != null) {
            startIntentSenderForResult(intentSender, -1, intent, i, i2, i3, bundle);
        } else {
            startIntentSenderForResult(intentSender, -1, intent, i, i2, i3);
        }
    }

    public boolean startActivityIfNeeded(android.content.Intent intent, int i) {
        return startActivityIfNeeded(intent, i, null);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public boolean startActivityIfNeeded(android.content.Intent intent, int i, android.os.Bundle bundle) {
        int i2;
        int i3;
        if (android.app.Instrumentation.DEBUG_START_ACTIVITY) {
            android.util.Log.d("Instrumentation", "startActivity: intent=" + intent + " requestCode=" + i + " options=" + bundle, new java.lang.Throwable());
        }
        if (this.mParent == null) {
            try {
                android.net.Uri onProvideReferrer = onProvideReferrer();
                if (onProvideReferrer != null) {
                    intent.putExtra(android.content.Intent.EXTRA_REFERRER, onProvideReferrer);
                }
                intent.migrateExtraStreamToClipData(this);
                intent.prepareToLeaveProcess(this);
                i2 = android.app.ActivityTaskManager.getService().startActivity(this.mMainThread.getApplicationThread(), getOpPackageName(), getAttributionTag(), intent, intent.resolveTypeIfNeeded(getContentResolver()), this.mToken, this.mEmbeddedID, i, 1, null, bundle);
            } catch (android.os.RemoteException e) {
                i2 = 1;
            }
            android.app.Instrumentation.checkStartActivityResult(i2, intent);
            if (i < 0) {
                i3 = 1;
            } else {
                i3 = 1;
                this.mStartedActivity = true;
            }
            if (i2 != i3) {
                return i3;
            }
            return false;
        }
        throw new java.lang.UnsupportedOperationException("startActivityIfNeeded can only be called from a top-level activity");
    }

    public boolean startNextMatchingActivity(android.content.Intent intent) {
        return startNextMatchingActivity(intent, null);
    }

    public boolean startNextMatchingActivity(android.content.Intent intent, android.os.Bundle bundle) {
        if (this.mParent == null) {
            try {
                intent.migrateExtraStreamToClipData(this);
                intent.prepareToLeaveProcess(this);
                return android.app.ActivityTaskManager.getService().startNextMatchingActivity(this.mToken, intent, bundle);
            } catch (android.os.RemoteException e) {
                return false;
            }
        }
        throw new java.lang.UnsupportedOperationException("startNextMatchingActivity can only be called from a top-level activity");
    }

    @java.lang.Deprecated
    public void startActivityFromChild(android.app.Activity activity, android.content.Intent intent, int i) {
        startActivityFromChild(activity, intent, i, null);
    }

    @java.lang.Deprecated
    public void startActivityFromChild(android.app.Activity activity, android.content.Intent intent, int i, android.os.Bundle bundle) {
        android.os.Bundle transferSpringboardActivityOptions = transferSpringboardActivityOptions(bundle);
        android.app.Instrumentation.ActivityResult execStartActivity = this.mInstrumentation.execStartActivity(this, this.mMainThread.getApplicationThread(), this.mToken, activity, intent, i, transferSpringboardActivityOptions);
        if (execStartActivity != null) {
            this.mMainThread.sendActivityResult(this.mToken, activity.mEmbeddedID, i, execStartActivity.getResultCode(), execStartActivity.getResultData());
        }
        cancelInputsAndStartExitTransition(transferSpringboardActivityOptions);
    }

    @java.lang.Deprecated
    public void startActivityFromFragment(android.app.Fragment fragment, android.content.Intent intent, int i) {
        startActivityFromFragment(fragment, intent, i, null);
    }

    @java.lang.Deprecated
    public void startActivityFromFragment(android.app.Fragment fragment, android.content.Intent intent, int i, android.os.Bundle bundle) {
        startActivityForResult(fragment.mWho, intent, i, bundle);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startActivityAsUserFromFragment(android.app.Fragment fragment, android.content.Intent intent, int i, android.os.Bundle bundle, android.os.UserHandle userHandle) {
        startActivityForResultAsUser(intent, fragment.mWho, i, bundle, userHandle);
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public void startActivityForResult(java.lang.String str, android.content.Intent intent, int i, android.os.Bundle bundle) {
        android.net.Uri onProvideReferrer = onProvideReferrer();
        if (onProvideReferrer != null) {
            intent.putExtra(android.content.Intent.EXTRA_REFERRER, onProvideReferrer);
        }
        android.os.Bundle transferSpringboardActivityOptions = transferSpringboardActivityOptions(bundle);
        android.app.Instrumentation.ActivityResult execStartActivity = this.mInstrumentation.execStartActivity(this, this.mMainThread.getApplicationThread(), this.mToken, str, intent, i, transferSpringboardActivityOptions);
        if (execStartActivity != null) {
            this.mMainThread.sendActivityResult(this.mToken, str, i, execStartActivity.getResultCode(), execStartActivity.getResultData());
        }
        cancelInputsAndStartExitTransition(transferSpringboardActivityOptions);
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public boolean canStartActivityForResult() {
        return true;
    }

    @java.lang.Deprecated
    public void startIntentSenderFromChild(android.app.Activity activity, android.content.IntentSender intentSender, int i, android.content.Intent intent, int i2, int i3, int i4) throws android.content.IntentSender.SendIntentException {
        startIntentSenderFromChild(activity, intentSender, i, intent, i2, i3, i4, null);
    }

    @java.lang.Deprecated
    public void startIntentSenderFromChild(android.app.Activity activity, android.content.IntentSender intentSender, int i, android.content.Intent intent, int i2, int i3, int i4, android.os.Bundle bundle) throws android.content.IntentSender.SendIntentException {
        startIntentSenderForResultInner(intentSender, activity.mEmbeddedID, i, intent, i2, i3, bundle);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startIntentSenderFromFragment(android.app.Fragment fragment, android.content.IntentSender intentSender, int i, android.content.Intent intent, int i2, int i3, android.os.Bundle bundle) throws android.content.IntentSender.SendIntentException {
        startIntentSenderForResultInner(intentSender, fragment.mWho, i, intent, i2, i3, bundle);
    }

    public void overrideActivityTransition(int i, int i2, int i3) {
        overrideActivityTransition(i, i2, i3, 0);
    }

    public void overrideActivityTransition(int i, int i2, int i3, int i4) {
        if (i != 0 && i != 1) {
            throw new java.lang.IllegalArgumentException("Override type must be either open or close");
        }
        android.app.ActivityClient.getInstance().overrideActivityTransition(this.mToken, i == 0, i2, i3, i4);
    }

    public void clearOverrideActivityTransition(int i) {
        if (i != 0 && i != 1) {
            throw new java.lang.IllegalArgumentException("Override type must be either open or close");
        }
        android.app.ActivityClient.getInstance().clearOverrideActivityTransition(this.mToken, i == 0);
    }

    @java.lang.Deprecated
    public void overridePendingTransition(int i, int i2) {
        overridePendingTransition(i, i2, 0);
    }

    @java.lang.Deprecated
    public void overridePendingTransition(int i, int i2, int i3) {
        android.app.ActivityClient.getInstance().overridePendingTransition(this.mToken, getPackageName(), i, i2, i3);
    }

    public final void setResult(int i) {
        synchronized (this) {
            this.mResultCode = i;
            this.mResultData = null;
        }
    }

    public final void setForceSendResultForMediaProjection() {
        android.app.ActivityClient.getInstance().setForceSendResultForMediaProjection(this.mToken);
    }

    public final void setResult(int i, android.content.Intent intent) {
        synchronized (this) {
            this.mResultCode = i;
            this.mResultData = intent;
        }
    }

    public android.net.Uri getReferrer() {
        android.content.Intent intent = getIntent();
        if (intent != null) {
            try {
                android.net.Uri uri = (android.net.Uri) intent.getParcelableExtra(android.content.Intent.EXTRA_REFERRER, android.net.Uri.class);
                if (uri != null) {
                    return uri;
                }
                java.lang.String stringExtra = intent.getStringExtra(android.content.Intent.EXTRA_REFERRER_NAME);
                if (stringExtra != null) {
                    return android.net.Uri.parse(stringExtra);
                }
            } catch (android.os.BadParcelableException e) {
                android.util.Log.w(TAG, "Cannot read referrer from intent; intent extras contain unknown custom Parcelable objects");
            }
        }
        if (this.mReferrer != null) {
            return new android.net.Uri.Builder().scheme("android-app").authority(this.mReferrer).build();
        }
        return null;
    }

    public android.net.Uri onProvideReferrer() {
        return null;
    }

    public java.lang.String getCallingPackage() {
        return android.app.ActivityClient.getInstance().getCallingPackage(this.mToken);
    }

    public android.content.ComponentName getCallingActivity() {
        return android.app.ActivityClient.getInstance().getCallingActivity(this.mToken);
    }

    public int getLaunchedFromUid() {
        return android.app.ActivityClient.getInstance().getLaunchedFromUid(getActivityToken());
    }

    public java.lang.String getLaunchedFromPackage() {
        return android.app.ActivityClient.getInstance().getLaunchedFromPackage(getActivityToken());
    }

    public android.app.ComponentCaller getInitialCaller() {
        return this.mInitialCaller;
    }

    public android.app.ComponentCaller getCurrentCaller() {
        if (this.mCurrentCaller == null) {
            throw new java.lang.IllegalStateException("The caller is null because #getCurrentCaller should be called within #onNewIntent method");
        }
        return this.mCurrentCaller;
    }

    public void setVisible(boolean z) {
        if (this.mVisibleFromClient != z) {
            this.mVisibleFromClient = z;
            if (this.mVisibleFromServer) {
                if (!z) {
                    this.mDecor.setVisibility(4);
                } else {
                    makeVisible();
                }
            }
        }
    }

    void makeVisible() {
        if (!this.mWindowAdded) {
            getWindowManager().addView(this.mDecor, getWindow().getAttributes());
            this.mWindowAdded = true;
        }
        this.mDecor.setVisibility(0);
    }

    public boolean isFinishing() {
        return this.mFinished;
    }

    public boolean isDestroyed() {
        return this.mDestroyed;
    }

    public boolean isChangingConfigurations() {
        return this.mChangingConfigurations;
    }

    public void recreate() {
        if (this.mParent != null) {
            throw new java.lang.IllegalStateException("Can only be called on top-level activity");
        }
        if (android.os.Looper.myLooper() != this.mMainThread.getLooper()) {
            throw new java.lang.IllegalStateException("Must be called from main thread");
        }
        this.mMainThread.scheduleRelaunchActivity(this.mToken);
    }

    private void finish(int i) {
        int i2;
        android.content.Intent intent;
        if (this.mParent == null) {
            synchronized (this) {
                i2 = this.mResultCode;
                intent = this.mResultData;
            }
            if (intent != null) {
                intent.prepareToLeaveProcess(this);
            }
            if (android.app.ActivityClient.getInstance().finishActivity(this.mToken, i2, intent, i)) {
                this.mFinished = true;
            }
        } else {
            this.mParent.finishFromChild(this);
        }
        getAutofillClientController().onActivityFinish(this.mIntent);
    }

    public void finish() {
        finish(0);
    }

    public void finishAffinity() {
        if (this.mParent != null) {
            throw new java.lang.IllegalStateException("Can not be called from an embedded activity");
        }
        if (this.mResultCode != 0 || this.mResultData != null) {
            throw new java.lang.IllegalStateException("Can not be called to deliver a result");
        }
        if (android.app.ActivityClient.getInstance().finishActivityAffinity(this.mToken)) {
            this.mFinished = true;
        }
    }

    @java.lang.Deprecated
    public void finishFromChild(android.app.Activity activity) {
        finish();
    }

    public void finishAfterTransition() {
        if (!this.mActivityTransitionState.startExitBackTransition(this)) {
            finish();
        }
    }

    public void finishActivity(int i) {
        if (this.mParent == null) {
            android.app.ActivityClient.getInstance().finishSubActivity(this.mToken, this.mEmbeddedID, i);
        } else {
            this.mParent.finishActivityFromChild(this, i);
        }
    }

    @java.lang.Deprecated
    public void finishActivityFromChild(android.app.Activity activity, int i) {
        android.app.ActivityClient.getInstance().finishSubActivity(this.mToken, activity.mEmbeddedID, i);
    }

    public void finishAndRemoveTask() {
        finish(1);
    }

    public boolean releaseInstance() {
        return android.app.ActivityClient.getInstance().releaseActivityInstance(this.mToken);
    }

    protected void onActivityResult(int i, int i2, android.content.Intent intent) {
    }

    public void onActivityResult(int i, int i2, android.content.Intent intent, android.app.ComponentCaller componentCaller) {
        onActivityResult(i, i2, intent);
    }

    public void onActivityReenter(int i, android.content.Intent intent) {
    }

    public android.app.PendingIntent createPendingResult(int i, android.content.Intent intent, int i2) {
        java.lang.String packageName = getPackageName();
        try {
            intent.prepareToLeaveProcess(this);
            android.content.IIntentSender intentSenderWithFeature = android.app.ActivityManager.getService().getIntentSenderWithFeature(3, packageName, getAttributionTag(), this.mParent == null ? this.mToken : this.mParent.mToken, this.mEmbeddedID, i, new android.content.Intent[]{intent}, null, i2, null, getUserId());
            if (intentSenderWithFeature != null) {
                return new android.app.PendingIntent(intentSenderWithFeature);
            }
            return null;
        } catch (android.os.RemoteException e) {
            return null;
        }
    }

    public void setRequestedOrientation(int i) {
        if (i == this.mLastRequestedOrientation) {
            return;
        }
        if (this.mParent == null) {
            android.app.ActivityClient.getInstance().setRequestedOrientation(this.mToken, i);
        } else {
            this.mParent.setRequestedOrientation(i);
        }
        this.mLastRequestedOrientation = i;
    }

    public int getRequestedOrientation() {
        if (this.mLastRequestedOrientation != -2) {
            return this.mLastRequestedOrientation;
        }
        if (this.mParent == null) {
            return android.app.ActivityClient.getInstance().getRequestedOrientation(this.mToken);
        }
        return this.mParent.getRequestedOrientation();
    }

    public int getTaskId() {
        return android.app.ActivityClient.getInstance().getTaskForActivity(this.mToken, false);
    }

    public boolean isTaskRoot() {
        return this.mWindowControllerCallback.isTaskRoot();
    }

    public boolean moveTaskToBack(boolean z) {
        return android.app.ActivityClient.getInstance().moveActivityTaskToBack(this.mToken, z);
    }

    public java.lang.String getLocalClassName() {
        java.lang.String packageName = getPackageName();
        java.lang.String className = this.mComponent.getClassName();
        int length = packageName.length();
        if (!className.startsWith(packageName) || className.length() <= length || className.charAt(length) != '.') {
            return className;
        }
        return className.substring(length + 1);
    }

    public android.content.ComponentName getComponentName() {
        return this.mComponent;
    }

    @Override // android.view.contentcapture.ContentCaptureManager.ContentCaptureClient
    public final android.content.ComponentName contentCaptureClientGetComponentName() {
        return getComponentName();
    }

    public android.content.SharedPreferences getPreferences(int i) {
        return getSharedPreferences(getLocalClassName(), i);
    }

    public boolean isLaunchedFromBubble() {
        return this.mLaunchedFromBubble;
    }

    private void ensureSearchManager() {
        if (this.mSearchManager != null) {
            return;
        }
        try {
            this.mSearchManager = new android.app.SearchManager(this, null);
        } catch (android.os.ServiceManager.ServiceNotFoundException e) {
            throw new java.lang.IllegalStateException(e);
        }
    }

    @Override // android.view.ContextThemeWrapper, android.content.ContextWrapper, android.content.Context
    public java.lang.Object getSystemService(java.lang.String str) {
        if (getBaseContext() == null) {
            throw new java.lang.IllegalStateException("System services not available to Activities before onCreate()");
        }
        if (android.content.Context.WINDOW_SERVICE.equals(str)) {
            return this.mWindowManager;
        }
        if ("search".equals(str)) {
            ensureSearchManager();
            return this.mSearchManager;
        }
        return super.getSystemService(str);
    }

    public void setTitle(java.lang.CharSequence charSequence) {
        this.mTitle = charSequence;
        onTitleChanged(charSequence, this.mTitleColor);
        if (this.mParent != null) {
            this.mParent.onChildTitleChanged(this, charSequence);
        }
    }

    public void setTitle(int i) {
        setTitle(getText(i));
    }

    @java.lang.Deprecated
    public void setTitleColor(int i) {
        this.mTitleColor = i;
        onTitleChanged(this.mTitle, i);
    }

    public final java.lang.CharSequence getTitle() {
        return this.mTitle;
    }

    public final int getTitleColor() {
        return this.mTitleColor;
    }

    protected void onTitleChanged(java.lang.CharSequence charSequence, int i) {
        if (this.mTitleReady) {
            android.view.Window window = getWindow();
            if (window != null) {
                window.setTitle(charSequence);
                if (i != 0) {
                    window.setTitleColor(i);
                }
            }
            if (this.mActionBar != null) {
                this.mActionBar.setWindowTitle(charSequence);
            }
        }
    }

    protected void onChildTitleChanged(android.app.Activity activity, java.lang.CharSequence charSequence) {
    }

    public void setTaskDescription(android.app.ActivityManager.TaskDescription taskDescription) {
        if (this.mTaskDescription != taskDescription) {
            this.mTaskDescription.copyFromPreserveHiddenFields(taskDescription);
            if (taskDescription.getIconFilename() == null && taskDescription.getIcon() != null) {
                int launcherLargeIconSizeInner = android.app.ActivityManager.getLauncherLargeIconSizeInner(this);
                this.mTaskDescription.setIcon(android.graphics.drawable.Icon.createWithBitmap(android.graphics.Bitmap.createScaledBitmap(taskDescription.getIcon(), launcherLargeIconSizeInner, launcherLargeIconSizeInner, true)));
            }
        }
        if (this.mLastTaskDescriptionHashCode == this.mTaskDescription.hashCode()) {
            return;
        }
        this.mLastTaskDescriptionHashCode = this.mTaskDescription.hashCode();
        android.app.ActivityClient.getInstance().setTaskDescription(this.mToken, this.mTaskDescription);
    }

    @java.lang.Deprecated
    public final void setProgressBarVisibility(boolean z) {
        getWindow().setFeatureInt(2, z ? -1 : -2);
    }

    @java.lang.Deprecated
    public final void setProgressBarIndeterminateVisibility(boolean z) {
        getWindow().setFeatureInt(5, z ? -1 : -2);
    }

    @java.lang.Deprecated
    public final void setProgressBarIndeterminate(boolean z) {
        getWindow().setFeatureInt(2, z ? -3 : -4);
    }

    @java.lang.Deprecated
    public final void setProgress(int i) {
        getWindow().setFeatureInt(2, i + 0);
    }

    @java.lang.Deprecated
    public final void setSecondaryProgress(int i) {
        getWindow().setFeatureInt(2, i + 20000);
    }

    public final void setVolumeControlStream(int i) {
        getWindow().setVolumeControlStream(i);
    }

    public final int getVolumeControlStream() {
        return getWindow().getVolumeControlStream();
    }

    public final void setMediaController(android.media.session.MediaController mediaController) {
        getWindow().setMediaController(mediaController);
    }

    public final android.media.session.MediaController getMediaController() {
        return getWindow().getMediaController();
    }

    public final void runOnUiThread(java.lang.Runnable runnable) {
        if (java.lang.Thread.currentThread() != this.mUiThread) {
            this.mHandler.post(runnable);
        } else {
            runnable.run();
        }
    }

    @Override // android.view.LayoutInflater.Factory
    public android.view.View onCreateView(java.lang.String str, android.content.Context context, android.util.AttributeSet attributeSet) {
        return null;
    }

    @Override // android.view.LayoutInflater.Factory2
    public android.view.View onCreateView(android.view.View view, java.lang.String str, android.content.Context context, android.util.AttributeSet attributeSet) {
        if (!"fragment".equals(str)) {
            return onCreateView(str, context, attributeSet);
        }
        return this.mFragments.onCreateView(view, str, context, attributeSet);
    }

    public void dump(java.lang.String str, java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, java.lang.String[] strArr) {
        dumpInner(str, fileDescriptor, printWriter, strArr);
    }

    @android.annotation.SystemApi(client = android.annotation.SystemApi.Client.MODULE_LIBRARIES)
    public final boolean addDumpable(android.util.Dumpable dumpable) {
        if (this.mDumpableContainer == null) {
            this.mDumpableContainer = new com.android.internal.util.dump.DumpableContainerImpl();
        }
        return this.mDumpableContainer.addDumpable(dumpable);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public void dumpInternal(java.lang.String str, java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, java.lang.String[] strArr) {
        java.lang.String str2;
        char c;
        if (this.mAutofillClientController != null) {
            addDumpable(this.mAutofillClientController);
        }
        if (this.mUiTranslationController != null) {
            addDumpable(this.mUiTranslationController);
        }
        if (this.mContentCaptureManager != null) {
            this.mContentCaptureManager.addDumpable(this);
        }
        boolean z = true;
        if (strArr != null && strArr.length > 0) {
            boolean z2 = false;
            str2 = strArr[0];
            switch (str2.hashCode()) {
                case -645125871:
                    if (str2.equals(DUMP_ARG_TRANSLATION)) {
                        c = 2;
                        break;
                    }
                    c = 65535;
                    break;
                case 100470631:
                    if (str2.equals(DUMP_ARG_DUMP_DUMPABLE)) {
                        c = 4;
                        break;
                    }
                    c = 65535;
                    break;
                case 472614934:
                    if (str2.equals(DUMP_ARG_LIST_DUMPABLES)) {
                        c = 3;
                        break;
                    }
                    c = 65535;
                    break;
                case 1159329357:
                    if (str2.equals(DUMP_ARG_CONTENT_CAPTURE)) {
                        c = 1;
                        break;
                    }
                    c = 65535;
                    break;
                case 1455016274:
                    if (str2.equals(DUMP_ARG_AUTOFILL)) {
                        c = 0;
                        break;
                    }
                    c = 65535;
                    break;
                default:
                    c = 65535;
                    break;
            }
            switch (c) {
                case 0:
                    dumpLegacyDumpable(str, printWriter, str2, android.view.autofill.AutofillClientController.DUMPABLE_NAME);
                    break;
                case 1:
                    dumpLegacyDumpable(str, printWriter, str2, android.view.contentcapture.ContentCaptureManager.DUMPABLE_NAME);
                    break;
                case 2:
                    dumpLegacyDumpable(str, printWriter, str2, android.view.translation.UiTranslationController.DUMPABLE_NAME);
                    break;
                case 3:
                    if (this.mDumpableContainer == null) {
                        printWriter.print(str);
                        printWriter.println("No dumpables");
                        break;
                    } else {
                        this.mDumpableContainer.listDumpables(str, printWriter);
                        break;
                    }
                case 4:
                    if (strArr.length == 1) {
                        printWriter.print(DUMP_ARG_DUMP_DUMPABLE);
                        printWriter.println(" requires the dumpable name");
                    } else if (this.mDumpableContainer == null) {
                        printWriter.println("no dumpables");
                    } else {
                        int length = strArr.length - 2;
                        java.lang.String[] strArr2 = new java.lang.String[length];
                        java.lang.System.arraycopy(strArr, 2, strArr2, 0, length);
                        this.mDumpableContainer.dumpOneDumpable(str, printWriter, strArr[1], strArr2);
                    }
                    z2 = true;
                default:
                    if (z2) {
                        z = true ^ android.app.compat.CompatChanges.isChangeEnabled(DUMP_IGNORES_SPECIAL_ARGS);
                        break;
                    }
                    break;
            }
            return;
        }
        str2 = null;
        if (z) {
            dump(str, fileDescriptor, printWriter, strArr);
        } else {
            android.util.Log.i(TAG, "Not calling dump() on " + this + " because of special argument " + str2);
        }
    }

    void dumpInner(java.lang.String str, java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, java.lang.String[] strArr) {
        java.lang.String str2 = str + "  ";
        printWriter.print(str);
        printWriter.print("Local Activity ");
        printWriter.print(java.lang.Integer.toHexString(java.lang.System.identityHashCode(this)));
        printWriter.println(" State:");
        printWriter.print(str2);
        printWriter.print("mResumed=");
        printWriter.print(this.mResumed);
        printWriter.print(" mStopped=");
        printWriter.print(this.mStopped);
        printWriter.print(" mFinished=");
        printWriter.println(this.mFinished);
        printWriter.print(str2);
        printWriter.print("mIsInMultiWindowMode=");
        printWriter.print(this.mIsInMultiWindowMode);
        printWriter.print(" mIsInPictureInPictureMode=");
        printWriter.println(this.mIsInPictureInPictureMode);
        printWriter.print(str2);
        printWriter.print("mChangingConfigurations=");
        printWriter.println(this.mChangingConfigurations);
        printWriter.print(str2);
        printWriter.print("mCurrentConfig=");
        printWriter.println(this.mCurrentConfig);
        this.mFragments.dumpLoaders(str2, fileDescriptor, printWriter, strArr);
        this.mFragments.getFragmentManager().dump(str2, fileDescriptor, printWriter, strArr);
        if (this.mVoiceInteractor != null) {
            this.mVoiceInteractor.dump(str2, fileDescriptor, printWriter, strArr);
        }
        if (getWindow() != null && getWindow().peekDecorView() != null && getWindow().peekDecorView().getViewRootImpl() != null) {
            getWindow().peekDecorView().getViewRootImpl().dump(str, printWriter);
        }
        this.mHandler.getLooper().dump(new android.util.PrintWriterPrinter(printWriter), str);
        android.app.ResourcesManager.getInstance().dump(str, printWriter);
        if (this.mDumpableContainer != null) {
            this.mDumpableContainer.dumpAllDumpables(str, printWriter, strArr);
        }
    }

    private void dumpLegacyDumpable(java.lang.String str, java.io.PrintWriter printWriter, java.lang.String str2, java.lang.String str3) {
        printWriter.printf("%s%s option deprecated. Use %s %s instead\n", str, str2, DUMP_ARG_DUMP_DUMPABLE, str3);
    }

    public boolean isImmersive() {
        return android.app.ActivityClient.getInstance().isImmersive(this.mToken);
    }

    final boolean isTopOfTask() {
        if (this.mToken == null || this.mWindow == null) {
            return false;
        }
        return android.app.ActivityClient.getInstance().isTopOfTask(getActivityToken());
    }

    public boolean setTranslucent(boolean z) {
        if (z) {
            return convertToTranslucent(null, null);
        }
        return convertFromTranslucentInternal();
    }

    @android.annotation.SystemApi
    public void convertFromTranslucent() {
        convertFromTranslucentInternal();
    }

    private boolean convertFromTranslucentInternal() {
        this.mTranslucentCallback = null;
        if (android.app.ActivityClient.getInstance().convertFromTranslucent(this.mToken)) {
            android.view.WindowManagerGlobal.getInstance().changeCanvasOpacity(this.mToken, true);
            return true;
        }
        return false;
    }

    @android.annotation.SystemApi
    public boolean convertToTranslucent(android.app.Activity.TranslucentConversionListener translucentConversionListener, android.app.ActivityOptions activityOptions) {
        this.mTranslucentCallback = translucentConversionListener;
        this.mChangeCanvasToTranslucent = android.app.ActivityClient.getInstance().convertToTranslucent(this.mToken, activityOptions == null ? null : activityOptions.toBundle());
        android.view.WindowManagerGlobal.getInstance().changeCanvasOpacity(this.mToken, false);
        if (!this.mChangeCanvasToTranslucent && this.mTranslucentCallback != null) {
            this.mTranslucentCallback.onTranslucentConversionComplete(true);
        }
        return this.mChangeCanvasToTranslucent;
    }

    void onTranslucentConversionComplete(boolean z) {
        if (this.mTranslucentCallback != null) {
            this.mTranslucentCallback.onTranslucentConversionComplete(z);
            this.mTranslucentCallback = null;
        }
        if (this.mChangeCanvasToTranslucent) {
            android.view.WindowManagerGlobal.getInstance().changeCanvasOpacity(this.mToken, false);
        }
    }

    public void onNewSceneTransitionInfo(android.app.ActivityOptions.SceneTransitionInfo sceneTransitionInfo) {
        this.mActivityTransitionState.setEnterSceneTransitionInfo(this, sceneTransitionInfo);
        if (!this.mStopped) {
            this.mActivityTransitionState.enterReady(this);
        }
    }

    android.app.ActivityOptions.SceneTransitionInfo getSceneTransitionInfo() {
        android.app.ActivityOptions.SceneTransitionInfo sceneTransitionInfo = this.mSceneTransitionInfo;
        this.mSceneTransitionInfo = null;
        return sceneTransitionInfo;
    }

    @java.lang.Deprecated
    public boolean requestVisibleBehind(boolean z) {
        return false;
    }

    @java.lang.Deprecated
    public void onVisibleBehindCanceled() {
        this.mCalled = true;
    }

    @android.annotation.SystemApi
    @java.lang.Deprecated
    public boolean isBackgroundVisibleBehind() {
        return false;
    }

    @android.annotation.SystemApi
    @java.lang.Deprecated
    public void onBackgroundVisibleBehindChanged(boolean z) {
    }

    public void onEnterAnimationComplete() {
    }

    public void dispatchEnterAnimationComplete() {
        this.mEnterAnimationComplete = true;
        this.mInstrumentation.onEnterAnimationComplete();
        onEnterAnimationComplete();
        if (getWindow() != null && getWindow().getDecorView() != null) {
            getWindow().getDecorView().getViewTreeObserver().dispatchOnEnterAnimationComplete();
        }
    }

    public void setImmersive(boolean z) {
        android.app.ActivityClient.getInstance().setImmersive(this.mToken, z);
    }

    public void setVrModeEnabled(boolean z, android.content.ComponentName componentName) throws android.content.pm.PackageManager.NameNotFoundException {
        if (android.app.ActivityClient.getInstance().setVrMode(this.mToken, z, componentName) != 0) {
            throw new android.content.pm.PackageManager.NameNotFoundException(componentName.flattenToString());
        }
    }

    public android.view.ActionMode startActionMode(android.view.ActionMode.Callback callback) {
        return this.mWindow.getDecorView().startActionMode(callback);
    }

    public android.view.ActionMode startActionMode(android.view.ActionMode.Callback callback, int i) {
        return this.mWindow.getDecorView().startActionMode(callback, i);
    }

    @Override // android.view.Window.Callback
    public android.view.ActionMode onWindowStartingActionMode(android.view.ActionMode.Callback callback) {
        if (this.mActionModeTypeStarting == 0) {
            initWindowDecorActionBar();
            if (this.mActionBar != null) {
                return this.mActionBar.startActionMode(callback);
            }
            return null;
        }
        return null;
    }

    @Override // android.view.Window.Callback
    public android.view.ActionMode onWindowStartingActionMode(android.view.ActionMode.Callback callback, int i) {
        try {
            this.mActionModeTypeStarting = i;
            return onWindowStartingActionMode(callback);
        } finally {
            this.mActionModeTypeStarting = 0;
        }
    }

    @Override // android.view.Window.Callback
    public void onActionModeStarted(android.view.ActionMode actionMode) {
    }

    @Override // android.view.Window.Callback
    public void onActionModeFinished(android.view.ActionMode actionMode) {
    }

    public boolean shouldUpRecreateTask(android.content.Intent intent) {
        try {
            android.content.pm.PackageManager packageManager = getPackageManager();
            android.content.ComponentName component = intent.getComponent();
            if (component == null) {
                component = intent.resolveActivity(packageManager);
            }
            android.content.pm.ActivityInfo activityInfo = packageManager.getActivityInfo(component, 0);
            if (activityInfo.taskAffinity == null) {
                return false;
            }
            return android.app.ActivityClient.getInstance().shouldUpRecreateTask(this.mToken, activityInfo.taskAffinity);
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    public boolean navigateUpTo(android.content.Intent intent) {
        android.content.Intent intent2;
        int i;
        android.content.Intent intent3;
        if (this.mParent == null) {
            if (intent.getComponent() != null) {
                intent2 = intent;
            } else {
                android.content.ComponentName resolveActivity = intent.resolveActivity(getPackageManager());
                if (resolveActivity == null) {
                    return false;
                }
                android.content.Intent intent4 = new android.content.Intent(intent);
                intent4.setComponent(resolveActivity);
                intent2 = intent4;
            }
            synchronized (this) {
                i = this.mResultCode;
                intent3 = this.mResultData;
            }
            if (intent3 != null) {
                intent3.prepareToLeaveProcess(this);
            }
            intent2.prepareToLeaveProcess(this);
            return android.app.ActivityClient.getInstance().navigateUpTo(this.mToken, intent2, intent2.resolveTypeIfNeeded(getContentResolver()), i, intent3);
        }
        return this.mParent.navigateUpToFromChild(this, intent);
    }

    @java.lang.Deprecated
    public boolean navigateUpToFromChild(android.app.Activity activity, android.content.Intent intent) {
        return navigateUpTo(intent);
    }

    public android.content.Intent getParentActivityIntent() {
        java.lang.String str = this.mActivityInfo.parentActivityName;
        if (android.text.TextUtils.isEmpty(str)) {
            return null;
        }
        android.content.ComponentName componentName = new android.content.ComponentName(this, str);
        try {
            if (getPackageManager().getActivityInfo(componentName, 0).parentActivityName == null) {
                return android.content.Intent.makeMainActivity(componentName);
            }
            return new android.content.Intent().setComponent(componentName);
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            android.util.Log.e(TAG, "getParentActivityIntent: bad parentActivityName '" + str + "' in manifest");
            return null;
        }
    }

    public void setEnterSharedElementCallback(android.app.SharedElementCallback sharedElementCallback) {
        if (sharedElementCallback == null) {
            sharedElementCallback = android.app.SharedElementCallback.NULL_CALLBACK;
        }
        this.mEnterTransitionListener = sharedElementCallback;
    }

    public void setExitSharedElementCallback(android.app.SharedElementCallback sharedElementCallback) {
        if (sharedElementCallback == null) {
            sharedElementCallback = android.app.SharedElementCallback.NULL_CALLBACK;
        }
        this.mExitTransitionListener = sharedElementCallback;
    }

    public void postponeEnterTransition() {
        this.mActivityTransitionState.postponeEnterTransition();
    }

    public void startPostponedEnterTransition() {
        this.mActivityTransitionState.startPostponedEnterTransition();
    }

    public android.view.DragAndDropPermissions requestDragAndDropPermissions(android.view.DragEvent dragEvent) {
        android.view.DragAndDropPermissions obtain = android.view.DragAndDropPermissions.obtain(dragEvent);
        if (obtain != null && obtain.take(getActivityToken())) {
            return obtain;
        }
        return null;
    }

    final void setParent(android.app.Activity activity) {
        this.mParent = activity;
    }

    final void attach(android.content.Context context, android.app.ActivityThread activityThread, android.app.Instrumentation instrumentation, android.os.IBinder iBinder, int i, android.app.Application application, android.content.Intent intent, android.content.pm.ActivityInfo activityInfo, java.lang.CharSequence charSequence, android.app.Activity activity, java.lang.String str, android.app.Activity.NonConfigurationInstances nonConfigurationInstances, android.content.res.Configuration configuration, java.lang.String str2, com.android.internal.app.IVoiceInteractor iVoiceInteractor, android.view.Window window, android.view.ViewRootImpl.ActivityConfigCallback activityConfigCallback, android.os.IBinder iBinder2, android.os.IBinder iBinder3) {
        attach(context, activityThread, instrumentation, iBinder, i, application, intent, activityInfo, charSequence, activity, str, nonConfigurationInstances, configuration, str2, iVoiceInteractor, window, activityConfigCallback, iBinder2, iBinder3, null);
    }

    final void attach(android.content.Context context, android.app.ActivityThread activityThread, android.app.Instrumentation instrumentation, android.os.IBinder iBinder, int i, android.app.Application application, android.content.Intent intent, android.content.pm.ActivityInfo activityInfo, java.lang.CharSequence charSequence, android.app.Activity activity, java.lang.String str, android.app.Activity.NonConfigurationInstances nonConfigurationInstances, android.content.res.Configuration configuration, java.lang.String str2, com.android.internal.app.IVoiceInteractor iVoiceInteractor, android.view.Window window, android.view.ViewRootImpl.ActivityConfigCallback activityConfigCallback, android.os.IBinder iBinder2, android.os.IBinder iBinder3, android.os.IBinder iBinder4) {
        if (com.android.sdksandbox.flags.Flags.sandboxActivitySdkBasedContext()) {
            this.mIntent = intent;
        }
        attachBaseContext(context);
        this.mFragments.attachHost(null);
        this.mActivityInfo = activityInfo;
        this.mWindow = new com.android.internal.policy.PhoneWindow(this, window, activityConfigCallback);
        this.mWindow.setWindowControllerCallback(this.mWindowControllerCallback);
        this.mWindow.setCallback(this);
        this.mWindow.setOnWindowDismissedCallback(this);
        this.mWindow.getLayoutInflater().setPrivateFactory(this);
        if (activityInfo.softInputMode != 0) {
            this.mWindow.setSoftInputMode(activityInfo.softInputMode);
        }
        if (activityInfo.uiOptions != 0) {
            this.mWindow.setUiOptions(activityInfo.uiOptions);
        }
        this.mUiThread = java.lang.Thread.currentThread();
        this.mMainThread = activityThread;
        this.mInstrumentation = instrumentation;
        this.mToken = iBinder;
        this.mAssistToken = iBinder2;
        this.mShareableActivityToken = iBinder3;
        this.mIdent = i;
        this.mApplication = application;
        this.mIntent = intent;
        this.mReferrer = str2;
        this.mComponent = intent.getComponent();
        this.mTitle = charSequence;
        this.mParent = activity;
        this.mEmbeddedID = str;
        this.mLastNonConfigurationInstances = nonConfigurationInstances;
        if (iVoiceInteractor != null) {
            if (nonConfigurationInstances != null) {
                this.mVoiceInteractor = nonConfigurationInstances.voiceInteractor;
            } else {
                this.mVoiceInteractor = new android.app.VoiceInteractor(iVoiceInteractor, this, this, android.os.Looper.myLooper());
            }
        }
        this.mWindow.setWindowManager((android.view.WindowManager) context.getSystemService(android.content.Context.WINDOW_SERVICE), this.mToken, this.mComponent.flattenToString(), (activityInfo.flags & 512) != 0);
        if (this.mParent != null) {
            this.mWindow.setContainer(this.mParent.getWindow());
        }
        this.mWindowManager = this.mWindow.getWindowManager();
        this.mCurrentConfig = configuration;
        this.mWindow.setColorMode(activityInfo.colorMode);
        this.mWindow.setPreferMinimalPostProcessing((activityInfo.flags & 33554432) != 0);
        getAutofillClientController().onActivityAttached(application);
        setContentCaptureOptions(application.getContentCaptureOptions());
        if (android.security.Flags.contentUriPermissionApis()) {
            this.mInitialCaller = new android.app.ComponentCaller(getActivityToken(), iBinder4);
            this.mCaller = this.mInitialCaller;
        }
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public final android.os.IBinder getActivityToken() {
        return this.mParent != null ? this.mParent.getActivityToken() : this.mToken;
    }

    public final android.os.IBinder getAssistToken() {
        return this.mParent != null ? this.mParent.getAssistToken() : this.mAssistToken;
    }

    public final android.os.IBinder getShareableActivityToken() {
        return this.mParent != null ? this.mParent.getShareableActivityToken() : this.mShareableActivityToken;
    }

    public final android.app.ActivityThread getActivityThread() {
        return this.mMainThread;
    }

    public final android.content.pm.ActivityInfo getActivityInfo() {
        return this.mActivityInfo;
    }

    final void performCreate(android.os.Bundle bundle) {
        performCreate(bundle, null);
    }

    final void performCreate(android.os.Bundle bundle, android.os.PersistableBundle persistableBundle) {
        if (android.os.Trace.isTagEnabled(32L)) {
            android.os.Trace.traceBegin(32L, "performCreate:" + this.mComponent.getClassName());
        }
        dispatchActivityPreCreated(bundle);
        this.mCanEnterPictureInPicture = true;
        int windowingMode = getResources().getConfiguration().windowConfiguration.getWindowingMode();
        this.mIsInMultiWindowMode = android.app.WindowConfiguration.inMultiWindowMode(windowingMode);
        this.mIsInPictureInPictureMode = windowingMode == 2;
        this.mShouldDockBigOverlays = getResources().getBoolean(com.android.internal.R.bool.config_dockBigOverlayWindows);
        restoreHasCurrentPermissionRequest(bundle);
        long uptimeMillis = android.os.SystemClock.uptimeMillis();
        if (persistableBundle != null) {
            onCreate(bundle, persistableBundle);
        } else {
            onCreate(bundle);
        }
        android.app.EventLogTags.writeWmOnCreateCalled(this.mIdent, getComponentName().getClassName(), "performCreate", android.os.SystemClock.uptimeMillis() - uptimeMillis);
        this.mActivityTransitionState.readState(bundle);
        this.mVisibleFromClient = !this.mWindow.getWindowStyle().getBoolean(10, false);
        this.mFragments.dispatchActivityCreated();
        this.mActivityTransitionState.setEnterSceneTransitionInfo(this, getSceneTransitionInfo());
        dispatchActivityPostCreated(bundle);
        android.os.Trace.traceEnd(32L);
    }

    final void performNewIntent(android.content.Intent intent) {
        android.os.Trace.traceBegin(32L, "performNewIntent");
        this.mCanEnterPictureInPicture = true;
        onNewIntent(intent);
        android.os.Trace.traceEnd(32L);
    }

    final void performNewIntent(android.content.Intent intent, android.app.ComponentCaller componentCaller) {
        android.os.Trace.traceBegin(32L, "performNewIntent");
        this.mCanEnterPictureInPicture = true;
        this.mCurrentCaller = componentCaller;
        onNewIntent(intent, componentCaller);
        this.mCurrentCaller = null;
        android.os.Trace.traceEnd(32L);
    }

    final void performStart(java.lang.String str) {
        java.lang.String dlWarning;
        if (android.os.Trace.isTagEnabled(32L)) {
            android.os.Trace.traceBegin(32L, "performStart:" + this.mComponent.getClassName());
        }
        dispatchActivityPreStarted();
        this.mActivityTransitionState.setEnterSceneTransitionInfo(this, getSceneTransitionInfo());
        this.mFragments.noteStateNotSaved();
        this.mCalled = false;
        this.mFragments.execPendingActions();
        long uptimeMillis = android.os.SystemClock.uptimeMillis();
        this.mInstrumentation.callActivityOnStart(this);
        android.app.EventLogTags.writeWmOnStartCalled(this.mIdent, getComponentName().getClassName(), str, android.os.SystemClock.uptimeMillis() - uptimeMillis);
        if (!this.mCalled) {
            throw new android.util.SuperNotCalledException("Activity " + this.mComponent.toShortString() + " did not call through to super.onStart()");
        }
        this.mFragments.dispatchStart();
        this.mFragments.reportLoaderStart();
        boolean z = (this.mApplication.getApplicationInfo().flags & 2) != 0;
        if (z && (dlWarning = getDlWarning()) != null) {
            java.lang.String charSequence = getApplicationInfo().loadLabel(getPackageManager()).toString();
            java.lang.String str2 = "Detected problems with app native libraries\n(please consult log for detail):\n" + dlWarning;
            if (z) {
                new android.app.AlertDialog.Builder(this).setTitle(charSequence).setMessage(str2).setPositiveButton(17039370, (android.content.DialogInterface.OnClickListener) null).setCancelable(false).show();
            } else {
                android.widget.Toast.makeText(this, charSequence + "\n" + str2, 1).show();
            }
        }
        android.os.GraphicsEnvironment.getInstance().showAngleInUseDialogBox(this);
        this.mActivityTransitionState.enterReady(this);
        dispatchActivityPostStarted();
        android.os.Trace.traceEnd(32L);
    }

    final void performRestart(boolean z) {
        android.os.Trace.traceBegin(32L, "performRestart");
        this.mCanEnterPictureInPicture = true;
        this.mFragments.noteStateNotSaved();
        if (this.mToken != null && this.mParent == null) {
            android.view.WindowManagerGlobal.getInstance().setStoppedState(this.mToken, false);
        }
        if (this.mStopped) {
            this.mStopped = false;
            synchronized (this.mManagedCursors) {
                int size = this.mManagedCursors.size();
                for (int i = 0; i < size; i++) {
                    android.app.Activity.ManagedCursor managedCursor = this.mManagedCursors.get(i);
                    if (managedCursor.mReleased || managedCursor.mUpdated) {
                        if (!managedCursor.mCursor.requery() && getApplicationInfo().targetSdkVersion >= 14) {
                            throw new java.lang.IllegalStateException("trying to requery an already closed cursor  " + managedCursor.mCursor);
                        }
                        managedCursor.mReleased = false;
                        managedCursor.mUpdated = false;
                    }
                }
            }
            this.mCalled = false;
            long uptimeMillis = android.os.SystemClock.uptimeMillis();
            this.mInstrumentation.callActivityOnRestart(this);
            android.app.EventLogTags.writeWmOnRestartCalled(this.mIdent, getComponentName().getClassName(), "performRestart", android.os.SystemClock.uptimeMillis() - uptimeMillis);
            if (!this.mCalled) {
                throw new android.util.SuperNotCalledException("Activity " + this.mComponent.toShortString() + " did not call through to super.onRestart()");
            }
            if (z) {
                performStart("performRestart");
            }
        }
        android.os.Trace.traceEnd(32L);
    }

    final void performResume(boolean z, java.lang.String str) {
        if (android.os.Trace.isTagEnabled(32L)) {
            android.os.Trace.traceBegin(32L, "performResume:" + this.mComponent.getClassName());
        }
        dispatchActivityPreResumed();
        this.mFragments.execPendingActions();
        this.mLastNonConfigurationInstances = null;
        getAutofillClientController().onActivityPerformResume(z);
        this.mCalled = false;
        long uptimeMillis = android.os.SystemClock.uptimeMillis();
        this.mInstrumentation.callActivityOnResume(this);
        android.app.EventLogTags.writeWmOnResumeCalled(this.mIdent, getComponentName().getClassName(), str, android.os.SystemClock.uptimeMillis() - uptimeMillis);
        if (!this.mCalled) {
            throw new android.util.SuperNotCalledException("Activity " + this.mComponent.toShortString() + " did not call through to super.onResume()");
        }
        if (!this.mVisibleFromClient && !this.mFinished) {
            android.util.Log.w(TAG, "An activity without a UI must call finish() before onResume() completes");
            if (getApplicationInfo().targetSdkVersion > 22) {
                throw new java.lang.IllegalStateException("Activity " + this.mComponent.toShortString() + " did not call finish() prior to onResume() completing");
            }
        }
        this.mCalled = false;
        this.mFragments.dispatchResume();
        this.mFragments.execPendingActions();
        onPostResume();
        if (!this.mCalled) {
            throw new android.util.SuperNotCalledException("Activity " + this.mComponent.toShortString() + " did not call through to super.onPostResume()");
        }
        dispatchActivityPostResumed();
        android.os.Trace.traceEnd(32L);
    }

    final void performPause() {
        if (android.os.Trace.isTagEnabled(32L)) {
            android.os.Trace.traceBegin(32L, "performPause:" + this.mComponent.getClassName());
        }
        dispatchActivityPrePaused();
        this.mDoReportFullyDrawn = false;
        this.mFragments.dispatchPause();
        this.mCalled = false;
        long uptimeMillis = android.os.SystemClock.uptimeMillis();
        onPause();
        android.app.EventLogTags.writeWmOnPausedCalled(this.mIdent, getComponentName().getClassName(), "performPause", android.os.SystemClock.uptimeMillis() - uptimeMillis);
        this.mResumed = false;
        if (!this.mCalled && getApplicationInfo().targetSdkVersion >= 9) {
            throw new android.util.SuperNotCalledException("Activity " + this.mComponent.toShortString() + " did not call through to super.onPause()");
        }
        dispatchActivityPostPaused();
        android.os.Trace.traceEnd(32L);
    }

    final void performUserLeaving() {
        onUserInteraction();
        onUserLeaveHint();
    }

    final void performStop(boolean z, java.lang.String str) {
        if (android.os.Trace.isTagEnabled(32L)) {
            android.os.Trace.traceBegin(32L, "performStop:" + this.mComponent.getClassName());
        }
        this.mDoReportFullyDrawn = false;
        this.mFragments.doLoaderStop(this.mChangingConfigurations);
        this.mCanEnterPictureInPicture = false;
        if (!this.mStopped) {
            dispatchActivityPreStopped();
            if (this.mWindow != null) {
                this.mWindow.closeAllPanels();
            }
            if (!z && this.mToken != null && this.mParent == null) {
                android.view.WindowManagerGlobal.getInstance().setStoppedState(this.mToken, true);
            }
            this.mFragments.dispatchStop();
            this.mCalled = false;
            long uptimeMillis = android.os.SystemClock.uptimeMillis();
            this.mInstrumentation.callActivityOnStop(this);
            android.app.EventLogTags.writeWmOnStopCalled(this.mIdent, getComponentName().getClassName(), str, android.os.SystemClock.uptimeMillis() - uptimeMillis);
            if (!this.mCalled) {
                throw new android.util.SuperNotCalledException("Activity " + this.mComponent.toShortString() + " did not call through to super.onStop()");
            }
            synchronized (this.mManagedCursors) {
                int size = this.mManagedCursors.size();
                for (int i = 0; i < size; i++) {
                    android.app.Activity.ManagedCursor managedCursor = this.mManagedCursors.get(i);
                    if (!managedCursor.mReleased) {
                        managedCursor.mCursor.deactivate();
                        managedCursor.mReleased = true;
                    }
                }
            }
            this.mStopped = true;
            dispatchActivityPostStopped();
        }
        this.mResumed = false;
        android.os.Trace.traceEnd(32L);
    }

    final void performDestroy() {
        if (android.os.Trace.isTagEnabled(32L)) {
            android.os.Trace.traceBegin(32L, "performDestroy:" + this.mComponent.getClassName());
        }
        dispatchActivityPreDestroyed();
        this.mDestroyed = true;
        this.mWindow.destroy();
        this.mFragments.dispatchDestroy();
        long uptimeMillis = android.os.SystemClock.uptimeMillis();
        onDestroy();
        android.app.EventLogTags.writeWmOnDestroyCalled(this.mIdent, getComponentName().getClassName(), "performDestroy", android.os.SystemClock.uptimeMillis() - uptimeMillis);
        this.mFragments.doLoaderDestroy();
        if (this.mVoiceInteractor != null) {
            this.mVoiceInteractor.detachActivity();
        }
        dispatchActivityPostDestroyed();
        android.os.Trace.traceEnd(32L);
    }

    final void dispatchMultiWindowModeChanged(boolean z, android.content.res.Configuration configuration) {
        this.mFragments.dispatchMultiWindowModeChanged(z, configuration);
        if (this.mWindow != null) {
            this.mWindow.onMultiWindowModeChanged();
        }
        this.mIsInMultiWindowMode = z;
        onMultiWindowModeChanged(z, configuration);
    }

    final void dispatchPictureInPictureModeChanged(boolean z, android.content.res.Configuration configuration) {
        this.mFragments.dispatchPictureInPictureModeChanged(z, configuration);
        if (this.mWindow != null) {
            this.mWindow.onPictureInPictureModeChanged(z);
        }
        this.mIsInPictureInPictureMode = z;
        onPictureInPictureModeChanged(z, configuration);
    }

    @android.annotation.SystemApi(client = android.annotation.SystemApi.Client.MODULE_LIBRARIES)
    public final boolean isResumed() {
        return this.mResumed;
    }

    private void storeHasCurrentPermissionRequest(android.os.Bundle bundle) {
        if (bundle != null && this.mHasCurrentPermissionsRequest) {
            bundle.putBoolean(HAS_CURENT_PERMISSIONS_REQUEST_KEY, true);
        }
    }

    private void restoreHasCurrentPermissionRequest(android.os.Bundle bundle) {
        if (bundle != null) {
            this.mHasCurrentPermissionsRequest = bundle.getBoolean(HAS_CURENT_PERMISSIONS_REQUEST_KEY, false);
        }
    }

    void dispatchActivityResult(java.lang.String str, int i, int i2, android.content.Intent intent, android.app.ComponentCaller componentCaller, java.lang.String str2) {
        internalDispatchActivityResult(str, i, i2, intent, componentCaller, str2);
    }

    void dispatchActivityResult(java.lang.String str, int i, int i2, android.content.Intent intent, java.lang.String str2) {
        if (android.security.Flags.contentUriPermissionApis()) {
            internalDispatchActivityResult(str, i, i2, intent, new android.app.ComponentCaller(getActivityToken(), null), str2);
        } else {
            internalDispatchActivityResult(str, i, i2, intent, null, str2);
        }
    }

    private void internalDispatchActivityResult(java.lang.String str, int i, int i2, android.content.Intent intent, android.app.ComponentCaller componentCaller, java.lang.String str2) {
        this.mFragments.noteStateNotSaved();
        if (str == null) {
            if (android.security.Flags.contentUriPermissionApis()) {
                this.mCurrentCaller = componentCaller;
                onActivityResult(i, i2, intent, componentCaller);
                this.mCurrentCaller = null;
            } else {
                onActivityResult(i, i2, intent);
            }
        } else if (str.startsWith(REQUEST_PERMISSIONS_WHO_PREFIX)) {
            java.lang.String substring = str.substring(REQUEST_PERMISSIONS_WHO_PREFIX.length());
            if (android.text.TextUtils.isEmpty(substring)) {
                dispatchRequestPermissionsResult(i, intent);
            } else {
                android.app.Fragment findFragmentByWho = this.mFragments.findFragmentByWho(substring);
                if (findFragmentByWho != null) {
                    dispatchRequestPermissionsResultToFragment(i, intent, findFragmentByWho);
                }
            }
        } else if (str.startsWith("@android:view:")) {
            java.util.Iterator<android.view.ViewRootImpl> it = android.view.WindowManagerGlobal.getInstance().getRootViews(getActivityToken()).iterator();
            while (it.hasNext()) {
                android.view.ViewRootImpl next = it.next();
                if (next.getView() != null && next.getView().dispatchActivityResult(str, i, i2, intent)) {
                    return;
                }
            }
        } else if (str.startsWith(android.view.autofill.AutofillClientController.AUTO_FILL_AUTH_WHO_PREFIX)) {
            getAutofillClientController().onDispatchActivityResult(i, i2, intent);
        } else {
            android.app.Fragment findFragmentByWho2 = this.mFragments.findFragmentByWho(str);
            if (findFragmentByWho2 != null) {
                findFragmentByWho2.onActivityResult(i, i2, intent);
            }
        }
        android.app.EventLogTags.writeWmOnActivityResultCalled(this.mIdent, getComponentName().getClassName(), str2);
    }

    public void startLockTask() {
        android.app.ActivityClient.getInstance().startLockTaskModeByToken(this.mToken);
    }

    public void stopLockTask() {
        android.app.ActivityClient.getInstance().stopLockTaskModeByToken(this.mToken);
    }

    public void showLockTaskEscapeMessage() {
        android.app.ActivityClient.getInstance().showLockTaskEscapeMessage(this.mToken);
    }

    public boolean isOverlayWithDecorCaptionEnabled() {
        return this.mWindow.isOverlayWithDecorCaptionEnabled();
    }

    public void setOverlayWithDecorCaptionEnabled(boolean z) {
        this.mWindow.setOverlayWithDecorCaptionEnabled(z);
    }

    private void dispatchRequestPermissionsResult(int i, android.content.Intent intent) {
        this.mHasCurrentPermissionsRequest = false;
        onRequestPermissionsResult(i, intent != null ? intent.getStringArrayExtra(android.content.pm.PackageManager.EXTRA_REQUEST_PERMISSIONS_NAMES) : new java.lang.String[0], intent != null ? intent.getIntArrayExtra(android.content.pm.PackageManager.EXTRA_REQUEST_PERMISSIONS_RESULTS) : new int[0], intent != null ? intent.getIntExtra(android.content.pm.PackageManager.EXTRA_REQUEST_PERMISSIONS_DEVICE_ID, 0) : 0);
    }

    private void dispatchRequestPermissionsResultToFragment(int i, android.content.Intent intent, android.app.Fragment fragment) {
        fragment.onRequestPermissionsResult(i, intent != null ? intent.getStringArrayExtra(android.content.pm.PackageManager.EXTRA_REQUEST_PERMISSIONS_NAMES) : new java.lang.String[0], intent != null ? intent.getIntArrayExtra(android.content.pm.PackageManager.EXTRA_REQUEST_PERMISSIONS_RESULTS) : new int[0]);
    }

    public final boolean isVisibleForAutofill() {
        return !this.mStopped;
    }

    public void setDisablePreviewScreenshots(boolean z) {
        setRecentsScreenshotEnabled(!z);
    }

    public void setRecentsScreenshotEnabled(boolean z) {
        android.app.ActivityClient.getInstance().setRecentsScreenshotEnabled(this.mToken, z);
    }

    public void setShowWhenLocked(boolean z) {
        android.app.ActivityClient.getInstance().setShowWhenLocked(this.mToken, z);
    }

    public void setInheritShowWhenLocked(boolean z) {
        android.app.ActivityClient.getInstance().setInheritShowWhenLocked(this.mToken, z);
    }

    public void setTurnScreenOn(boolean z) {
        android.app.ActivityClient.getInstance().setTurnScreenOn(this.mToken, z);
    }

    public void setAllowCrossUidActivitySwitchFromBelow(boolean z) {
        android.app.ActivityClient.getInstance().setAllowCrossUidActivitySwitchFromBelow(this.mToken, z);
    }

    public void registerRemoteAnimations(android.view.RemoteAnimationDefinition remoteAnimationDefinition) {
        android.app.ActivityClient.getInstance().registerRemoteAnimations(this.mToken, remoteAnimationDefinition);
    }

    public void unregisterRemoteAnimations() {
        android.app.ActivityClient.getInstance().unregisterRemoteAnimations(this.mToken);
    }

    public void updateUiTranslationState(int i, android.view.translation.TranslationSpec translationSpec, android.view.translation.TranslationSpec translationSpec2, java.util.List<android.view.autofill.AutofillId> list, android.view.translation.UiTranslationSpec uiTranslationSpec) {
        if (this.mUiTranslationController == null) {
            this.mUiTranslationController = new android.view.translation.UiTranslationController(this, getApplicationContext());
        }
        this.mUiTranslationController.updateUiTranslationState(i, translationSpec, translationSpec2, list, uiTranslationSpec);
    }

    public void enableTaskLocaleOverride() {
        android.app.ActivityClient.getInstance().enableTaskLocaleOverride(this.mToken);
    }

    public void setActivityRecordInputSinkEnabled(boolean z) {
        android.app.ActivityClient.getInstance().setActivityRecordInputSinkEnabled(this.mToken, z);
    }

    class HostCallbacks extends android.app.FragmentHostCallback<android.app.Activity> {
        public HostCallbacks() {
            super(android.app.Activity.this);
        }

        @Override // android.app.FragmentHostCallback
        public void onDump(java.lang.String str, java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, java.lang.String[] strArr) {
            android.app.Activity.this.dump(str, fileDescriptor, printWriter, strArr);
        }

        @Override // android.app.FragmentHostCallback
        public boolean onShouldSaveFragmentState(android.app.Fragment fragment) {
            return !android.app.Activity.this.isFinishing();
        }

        @Override // android.app.FragmentHostCallback
        public android.view.LayoutInflater onGetLayoutInflater() {
            android.view.LayoutInflater layoutInflater = android.app.Activity.this.getLayoutInflater();
            if (onUseFragmentManagerInflaterFactory()) {
                return layoutInflater.cloneInContext(android.app.Activity.this);
            }
            return layoutInflater;
        }

        @Override // android.app.FragmentHostCallback
        public boolean onUseFragmentManagerInflaterFactory() {
            return android.app.Activity.this.getApplicationInfo().targetSdkVersion >= 21;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.app.FragmentHostCallback
        public android.app.Activity onGetHost() {
            return android.app.Activity.this;
        }

        @Override // android.app.FragmentHostCallback
        public void onInvalidateOptionsMenu() {
            android.app.Activity.this.invalidateOptionsMenu();
        }

        @Override // android.app.FragmentHostCallback
        public void onStartActivityFromFragment(android.app.Fragment fragment, android.content.Intent intent, int i, android.os.Bundle bundle) {
            android.app.Activity.this.startActivityFromFragment(fragment, intent, i, bundle);
        }

        @Override // android.app.FragmentHostCallback
        public void onStartActivityAsUserFromFragment(android.app.Fragment fragment, android.content.Intent intent, int i, android.os.Bundle bundle, android.os.UserHandle userHandle) {
            android.app.Activity.this.startActivityAsUserFromFragment(fragment, intent, i, bundle, userHandle);
        }

        @Override // android.app.FragmentHostCallback
        public void onStartIntentSenderFromFragment(android.app.Fragment fragment, android.content.IntentSender intentSender, int i, android.content.Intent intent, int i2, int i3, int i4, android.os.Bundle bundle) throws android.content.IntentSender.SendIntentException {
            if (android.app.Activity.this.mParent == null) {
                android.app.Activity.this.startIntentSenderForResultInner(intentSender, fragment.mWho, i, intent, i2, i3, bundle);
            } else if (bundle != null) {
                android.app.Activity.this.mParent.startIntentSenderFromFragment(fragment, intentSender, i, intent, i2, i3, bundle);
            }
        }

        @Override // android.app.FragmentHostCallback
        public void onRequestPermissionsFromFragment(android.app.Fragment fragment, java.lang.String[] strArr, int i) {
            android.app.Activity.this.startActivityForResult(android.app.Activity.REQUEST_PERMISSIONS_WHO_PREFIX + fragment.mWho, android.app.Activity.this.getPackageManager().buildRequestPermissionsIntent(strArr), i, null);
        }

        @Override // android.app.FragmentHostCallback
        public boolean onHasWindowAnimations() {
            return android.app.Activity.this.getWindow() != null;
        }

        @Override // android.app.FragmentHostCallback
        public int onGetWindowAnimations() {
            android.view.Window window = android.app.Activity.this.getWindow();
            if (window == null) {
                return 0;
            }
            return window.getAttributes().windowAnimations;
        }

        @Override // android.app.FragmentHostCallback
        public void onAttachFragment(android.app.Fragment fragment) {
            android.app.Activity.this.onAttachFragment(fragment);
        }

        @Override // android.app.FragmentHostCallback, android.app.FragmentContainer
        public <T extends android.view.View> T onFindViewById(int i) {
            return (T) android.app.Activity.this.findViewById(i);
        }

        @Override // android.app.FragmentHostCallback, android.app.FragmentContainer
        public boolean onHasView() {
            android.view.Window window = android.app.Activity.this.getWindow();
            return (window == null || window.peekDecorView() == null) ? false : true;
        }
    }

    public android.window.OnBackInvokedDispatcher getOnBackInvokedDispatcher() {
        if (this.mWindow == null) {
            throw new java.lang.IllegalStateException("OnBackInvokedDispatcher are not available on non-visual activities");
        }
        return this.mWindow.getOnBackInvokedDispatcher();
    }

    public void registerScreenCaptureCallback(java.util.concurrent.Executor executor, android.app.Activity.ScreenCaptureCallback screenCaptureCallback) {
        if (this.mScreenCaptureCallbackHandler == null) {
            this.mScreenCaptureCallbackHandler = new android.app.ScreenCaptureCallbackHandler(this.mToken);
        }
        this.mScreenCaptureCallbackHandler.registerScreenCaptureCallback(executor, screenCaptureCallback);
    }

    public void unregisterScreenCaptureCallback(android.app.Activity.ScreenCaptureCallback screenCaptureCallback) {
        if (this.mScreenCaptureCallbackHandler != null) {
            this.mScreenCaptureCallbackHandler.unregisterScreenCaptureCallback(screenCaptureCallback);
        }
    }
}
