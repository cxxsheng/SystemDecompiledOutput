package android.service.dreams;

/* loaded from: classes3.dex */
public class DreamService extends android.app.Service implements android.view.Window.Callback {
    public static final boolean DEFAULT_SHOW_COMPLICATIONS = false;
    public static final java.lang.String DREAM_META_DATA = "android.service.dream";
    private static final java.lang.String DREAM_META_DATA_ROOT_TAG = "dream";
    public static final java.lang.String DREAM_SERVICE = "dreams";
    public static final java.lang.String EXTRA_DREAM_OVERLAY_COMPONENT = "android.service.dream.DreamService.dream_overlay_component";
    public static final java.lang.String SERVICE_INTERFACE = "android.service.dreams.DreamService";
    private android.app.Activity mActivity;
    private boolean mCanDoze;
    private java.lang.Runnable mDispatchAfterOnAttachedToWindow;
    private boolean mDozing;
    private android.content.ComponentName mDreamComponent;
    private android.service.dreams.DreamService.DreamServiceWrapper mDreamServiceWrapper;
    private android.os.IBinder mDreamToken;
    private boolean mFinished;
    private boolean mFullscreen;
    private boolean mInteractive;
    private android.service.dreams.IDreamOverlayCallback mOverlayCallback;
    private android.service.dreams.DreamOverlayConnectionHandler mOverlayConnection;
    private boolean mShouldShowComplications;
    private boolean mStarted;
    private boolean mWaking;
    private android.view.Window mWindow;
    private boolean mWindowless;
    private static final java.lang.String TAG = android.service.dreams.DreamService.class.getSimpleName();
    private static final boolean DEBUG = android.util.Log.isLoggable(TAG, 3);
    private final java.lang.String mTag = TAG + android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_START + getClass().getSimpleName() + android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_END;
    private final android.os.Handler mHandler = new android.os.Handler(android.os.Looper.getMainLooper());
    private boolean mScreenBright = true;
    private int mDozeScreenState = 0;
    private int mDozeScreenBrightness = -1;
    private boolean mDebug = false;
    private final android.service.dreams.IDreamManager mDreamManager = android.service.dreams.IDreamManager.Stub.asInterface(android.os.ServiceManager.getService(DREAM_SERVICE));

    public void setDebug(boolean z) {
        this.mDebug = z;
    }

    @Override // android.view.Window.Callback
    public boolean dispatchKeyEvent(android.view.KeyEvent keyEvent) {
        if (!this.mInteractive) {
            if (this.mDebug) {
                android.util.Slog.v(this.mTag, "Waking up on keyEvent");
            }
            wakeUp();
            return true;
        }
        if (keyEvent.getKeyCode() == 4) {
            if (this.mDebug) {
                android.util.Slog.v(this.mTag, "Waking up on back key");
            }
            wakeUp();
            return true;
        }
        return this.mWindow.superDispatchKeyEvent(keyEvent);
    }

    @Override // android.view.Window.Callback
    public boolean dispatchKeyShortcutEvent(android.view.KeyEvent keyEvent) {
        if (!this.mInteractive) {
            if (this.mDebug) {
                android.util.Slog.v(this.mTag, "Waking up on keyShortcutEvent");
            }
            wakeUp();
            return true;
        }
        return this.mWindow.superDispatchKeyShortcutEvent(keyEvent);
    }

    @Override // android.view.Window.Callback
    public boolean dispatchTouchEvent(android.view.MotionEvent motionEvent) {
        if (!this.mInteractive && motionEvent.getActionMasked() == 1) {
            if (this.mDebug) {
                android.util.Slog.v(this.mTag, "Waking up on touchEvent");
            }
            wakeUp();
            return true;
        }
        return this.mWindow.superDispatchTouchEvent(motionEvent);
    }

    @Override // android.view.Window.Callback
    public boolean dispatchTrackballEvent(android.view.MotionEvent motionEvent) {
        if (!this.mInteractive) {
            if (this.mDebug) {
                android.util.Slog.v(this.mTag, "Waking up on trackballEvent");
            }
            wakeUp();
            return true;
        }
        return this.mWindow.superDispatchTrackballEvent(motionEvent);
    }

    @Override // android.view.Window.Callback
    public boolean dispatchGenericMotionEvent(android.view.MotionEvent motionEvent) {
        if (!this.mInteractive) {
            if (this.mDebug) {
                android.util.Slog.v(this.mTag, "Waking up on genericMotionEvent");
            }
            wakeUp();
            return true;
        }
        return this.mWindow.superDispatchGenericMotionEvent(motionEvent);
    }

    @Override // android.view.Window.Callback
    public boolean dispatchPopulateAccessibilityEvent(android.view.accessibility.AccessibilityEvent accessibilityEvent) {
        return false;
    }

    @Override // android.view.Window.Callback
    public android.view.View onCreatePanelView(int i) {
        return null;
    }

    @Override // android.view.Window.Callback
    public boolean onCreatePanelMenu(int i, android.view.Menu menu) {
        return false;
    }

    @Override // android.view.Window.Callback
    public boolean onPreparePanel(int i, android.view.View view, android.view.Menu menu) {
        return false;
    }

    @Override // android.view.Window.Callback
    public boolean onMenuOpened(int i, android.view.Menu menu) {
        return false;
    }

    @Override // android.view.Window.Callback
    public boolean onMenuItemSelected(int i, android.view.MenuItem menuItem) {
        return false;
    }

    @Override // android.view.Window.Callback
    public void onWindowAttributesChanged(android.view.WindowManager.LayoutParams layoutParams) {
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

    @Override // android.view.Window.Callback
    public void onPanelClosed(int i, android.view.Menu menu) {
    }

    @Override // android.view.Window.Callback
    public boolean onSearchRequested(android.view.SearchEvent searchEvent) {
        return onSearchRequested();
    }

    @Override // android.view.Window.Callback
    public boolean onSearchRequested() {
        return false;
    }

    @Override // android.view.Window.Callback
    public android.view.ActionMode onWindowStartingActionMode(android.view.ActionMode.Callback callback) {
        return null;
    }

    @Override // android.view.Window.Callback
    public android.view.ActionMode onWindowStartingActionMode(android.view.ActionMode.Callback callback, int i) {
        return null;
    }

    @Override // android.view.Window.Callback
    public void onActionModeStarted(android.view.ActionMode actionMode) {
    }

    @Override // android.view.Window.Callback
    public void onActionModeFinished(android.view.ActionMode actionMode) {
    }

    public android.view.WindowManager getWindowManager() {
        if (this.mWindow != null) {
            return this.mWindow.getWindowManager();
        }
        return null;
    }

    public android.view.Window getWindow() {
        return this.mWindow;
    }

    public android.app.Activity getActivity() {
        return this.mActivity;
    }

    public void setContentView(int i) {
        getWindow().setContentView(i);
    }

    public void setContentView(android.view.View view) {
        getWindow().setContentView(view);
    }

    public void setContentView(android.view.View view, android.view.ViewGroup.LayoutParams layoutParams) {
        getWindow().setContentView(view, layoutParams);
    }

    public void addContentView(android.view.View view, android.view.ViewGroup.LayoutParams layoutParams) {
        getWindow().addContentView(view, layoutParams);
    }

    public <T extends android.view.View> T findViewById(int i) {
        return (T) getWindow().findViewById(i);
    }

    public final <T extends android.view.View> T requireViewById(int i) {
        T t = (T) findViewById(i);
        if (t == null) {
            throw new java.lang.IllegalArgumentException("ID does not reference a View inside this DreamService");
        }
        return t;
    }

    public void setInteractive(boolean z) {
        this.mInteractive = z;
    }

    public boolean isInteractive() {
        return this.mInteractive;
    }

    public void setFullscreen(boolean z) {
        if (this.mFullscreen != z) {
            this.mFullscreen = z;
            applyWindowFlags(this.mFullscreen ? 1024 : 0, 1024);
        }
    }

    public boolean isFullscreen() {
        return this.mFullscreen;
    }

    public void setScreenBright(boolean z) {
        if (this.mScreenBright != z) {
            this.mScreenBright = z;
            applyWindowFlags(this.mScreenBright ? 128 : 0, 128);
        }
    }

    public boolean isScreenBright() {
        return getWindowFlagValue(128, this.mScreenBright);
    }

    public void setWindowless(boolean z) {
        this.mWindowless = z;
    }

    public boolean isWindowless() {
        return this.mWindowless;
    }

    public boolean canDoze() {
        return this.mCanDoze;
    }

    public void startDozing() {
        if (this.mCanDoze && !this.mDozing) {
            this.mDozing = true;
            updateDoze();
        }
    }

    private void updateDoze() {
        if (this.mDreamToken == null) {
            android.util.Slog.w(this.mTag, "Updating doze without a dream token.");
        } else if (this.mDozing) {
            try {
                this.mDreamManager.startDozing(this.mDreamToken, this.mDozeScreenState, this.mDozeScreenBrightness);
            } catch (android.os.RemoteException e) {
            }
        }
    }

    public void stopDozing() {
        if (this.mDozing) {
            this.mDozing = false;
            try {
                this.mDreamManager.stopDozing(this.mDreamToken);
            } catch (android.os.RemoteException e) {
            }
        }
    }

    public boolean isDozing() {
        return this.mDozing;
    }

    public int getDozeScreenState() {
        return this.mDozeScreenState;
    }

    public void setDozeScreenState(int i) {
        if (this.mDozeScreenState != i) {
            this.mDozeScreenState = i;
            updateDoze();
        }
    }

    public int getDozeScreenBrightness() {
        return this.mDozeScreenBrightness;
    }

    public void setDozeScreenBrightness(int i) {
        if (i != -1) {
            i = clampAbsoluteBrightness(i);
        }
        if (this.mDozeScreenBrightness != i) {
            this.mDozeScreenBrightness = i;
            updateDoze();
        }
    }

    @Override // android.app.Service
    public void onCreate() {
        if (this.mDebug) {
            android.util.Slog.v(this.mTag, "onCreate()");
        }
        this.mDreamComponent = new android.content.ComponentName(this, getClass());
        this.mShouldShowComplications = fetchShouldShowComplications(this, fetchServiceInfo(this, this.mDreamComponent));
        this.mOverlayCallback = new android.service.dreams.DreamService.AnonymousClass1();
        super.onCreate();
    }

    /* renamed from: android.service.dreams.DreamService$1, reason: invalid class name */
    class AnonymousClass1 extends android.service.dreams.IDreamOverlayCallback.Stub {
        AnonymousClass1() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onExitRequested$0() {
            android.service.dreams.DreamService.this.finish();
        }

        @Override // android.service.dreams.IDreamOverlayCallback
        public void onExitRequested() {
            android.service.dreams.DreamService.this.mHandler.post(new java.lang.Runnable() { // from class: android.service.dreams.DreamService$1$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    android.service.dreams.DreamService.AnonymousClass1.this.lambda$onExitRequested$0();
                }
            });
        }
    }

    public void onDreamingStarted() {
        if (this.mDebug) {
            android.util.Slog.v(this.mTag, "onDreamingStarted()");
        }
    }

    public void onDreamingStopped() {
        if (this.mDebug) {
            android.util.Slog.v(this.mTag, "onDreamingStopped()");
        }
    }

    public void onWakeUp() {
        if (this.mOverlayConnection != null) {
            this.mOverlayConnection.addConsumer(new java.util.function.Consumer() { // from class: android.service.dreams.DreamService$$ExternalSyntheticLambda1
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    android.service.dreams.DreamService.this.lambda$onWakeUp$0((android.service.dreams.IDreamOverlayClient) obj);
                }
            });
        } else {
            finish();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onWakeUp$0(android.service.dreams.IDreamOverlayClient iDreamOverlayClient) {
        try {
            try {
                iDreamOverlayClient.wakeUp();
            } catch (android.os.RemoteException e) {
                android.util.Slog.e(TAG, "Error waking the overlay service", e);
            }
        } finally {
            finish();
        }
    }

    @Override // android.app.Service
    public final android.os.IBinder onBind(android.content.Intent intent) {
        if (this.mDebug) {
            android.util.Slog.v(this.mTag, "onBind() intent = " + intent);
        }
        this.mDreamServiceWrapper = new android.service.dreams.DreamService.DreamServiceWrapper();
        android.content.ComponentName componentName = (android.content.ComponentName) intent.getParcelableExtra(EXTRA_DREAM_OVERLAY_COMPONENT, android.content.ComponentName.class);
        if (!this.mWindowless && componentName != null) {
            android.content.res.Resources resources = getResources();
            this.mOverlayConnection = new android.service.dreams.DreamOverlayConnectionHandler(this, android.os.Looper.getMainLooper(), new android.content.Intent().setComponent(componentName), resources.getInteger(com.android.internal.R.integer.config_minDreamOverlayDurationMs), resources.getInteger(com.android.internal.R.integer.config_dreamOverlayMaxReconnectAttempts), resources.getInteger(com.android.internal.R.integer.config_dreamOverlayReconnectTimeoutMs));
            if (!this.mOverlayConnection.bind()) {
                this.mOverlayConnection = null;
            }
        }
        return this.mDreamServiceWrapper;
    }

    @Override // android.app.Service
    public boolean onUnbind(android.content.Intent intent) {
        if (this.mOverlayConnection != null) {
            this.mOverlayConnection.unbind();
            this.mOverlayConnection = null;
        }
        return super.onUnbind(intent);
    }

    public final void finish() {
        if (this.mOverlayConnection != null) {
            this.mOverlayConnection.addConsumer(new java.util.function.Consumer() { // from class: android.service.dreams.DreamService$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    android.service.dreams.DreamService.this.lambda$finish$1((android.service.dreams.IDreamOverlayClient) obj);
                }
            });
            return;
        }
        if (this.mDebug) {
            android.util.Slog.v(this.mTag, "finish(): mFinished=" + this.mFinished);
        }
        android.app.Activity activity = this.mActivity;
        if (activity != null) {
            if (!activity.isFinishing()) {
                activity.finishAndRemoveTask();
            }
        } else {
            if (this.mFinished) {
                return;
            }
            this.mFinished = true;
            if (this.mDreamToken != null) {
                try {
                    this.mDreamManager.finishSelf(this.mDreamToken, true);
                } catch (android.os.RemoteException e) {
                }
            } else {
                if (this.mDebug) {
                    android.util.Slog.v(this.mTag, "finish() called when not attached.");
                }
                stopSelf();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$finish$1(android.service.dreams.IDreamOverlayClient iDreamOverlayClient) {
        try {
            iDreamOverlayClient.endDream();
            this.mOverlayConnection.unbind();
            this.mOverlayConnection = null;
            finish();
        } catch (android.os.RemoteException e) {
            android.util.Log.e(this.mTag, "could not inform overlay of dream end:" + e);
        }
    }

    public final void wakeUp() {
        wakeUp(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void wakeUp(boolean z) {
        if (this.mDebug) {
            android.util.Slog.v(this.mTag, "wakeUp(): fromSystem=" + z + ", mWaking=" + this.mWaking + ", mFinished=" + this.mFinished);
        }
        if (!this.mWaking && !this.mFinished) {
            this.mWaking = true;
            if (this.mActivity != null) {
                this.mActivity.convertToTranslucent(null, null);
            }
            onWakeUp();
            if (!z && !this.mFinished) {
                if (this.mActivity == null) {
                    android.util.Slog.w(this.mTag, "WakeUp was called before the dream was attached.");
                } else {
                    try {
                        this.mDreamManager.finishSelf(this.mDreamToken, false);
                    } catch (android.os.RemoteException e) {
                    }
                }
            }
        }
    }

    @Override // android.app.Service
    public void onDestroy() {
        if (this.mDebug) {
            android.util.Slog.v(this.mTag, "onDestroy()");
        }
        detach();
        this.mOverlayCallback = null;
        super.onDestroy();
    }

    public static android.service.dreams.DreamService.DreamMetadata getDreamMetadata(android.content.Context context, android.content.pm.ServiceInfo serviceInfo) {
        if (serviceInfo == null) {
            return null;
        }
        android.content.res.TypedArray readMetadata = readMetadata(context.getPackageManager(), serviceInfo);
        if (readMetadata != null) {
            try {
                android.service.dreams.DreamService.DreamMetadata dreamMetadata = new android.service.dreams.DreamService.DreamMetadata(convertToComponentName(readMetadata.getString(0), serviceInfo), readMetadata.getDrawable(1), readMetadata.getBoolean(2, false));
                if (readMetadata != null) {
                    readMetadata.close();
                }
                return dreamMetadata;
            } catch (java.lang.Throwable th) {
                if (readMetadata != null) {
                    try {
                        readMetadata.close();
                    } catch (java.lang.Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        }
        if (readMetadata != null) {
            readMetadata.close();
        }
        return null;
    }

    private static android.content.res.TypedArray readMetadata(android.content.pm.PackageManager packageManager, android.content.pm.ServiceInfo serviceInfo) {
        int next;
        if (serviceInfo == null || serviceInfo.metaData == null) {
            return null;
        }
        try {
            android.content.res.XmlResourceParser loadXmlMetaData = serviceInfo.loadXmlMetaData(packageManager, DREAM_META_DATA);
            try {
                if (loadXmlMetaData == null) {
                    if (DEBUG) {
                        android.util.Log.w(TAG, "No android.service.dream metadata");
                    }
                    if (loadXmlMetaData != null) {
                        loadXmlMetaData.close();
                    }
                    return null;
                }
                android.util.AttributeSet asAttributeSet = android.util.Xml.asAttributeSet(loadXmlMetaData);
                do {
                    next = loadXmlMetaData.next();
                    if (next == 1) {
                        break;
                    }
                } while (next != 2);
                if (loadXmlMetaData.getName().equals("dream")) {
                    android.content.res.TypedArray obtainAttributes = packageManager.getResourcesForApplication(serviceInfo.applicationInfo).obtainAttributes(asAttributeSet, com.android.internal.R.styleable.Dream);
                    if (loadXmlMetaData != null) {
                        loadXmlMetaData.close();
                    }
                    return obtainAttributes;
                }
                if (DEBUG) {
                    android.util.Log.w(TAG, "Metadata does not start with dream tag");
                }
                if (loadXmlMetaData != null) {
                    loadXmlMetaData.close();
                }
                return null;
            } catch (java.lang.Throwable th) {
                if (loadXmlMetaData != null) {
                    try {
                        loadXmlMetaData.close();
                    } catch (java.lang.Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        } catch (android.content.pm.PackageManager.NameNotFoundException | java.io.IOException | org.xmlpull.v1.XmlPullParserException e) {
            if (DEBUG) {
                android.util.Log.e(TAG, "Error parsing: " + serviceInfo.packageName, e);
            }
            return null;
        }
    }

    private static android.content.ComponentName convertToComponentName(java.lang.String str, android.content.pm.ServiceInfo serviceInfo) {
        if (str == null) {
            return null;
        }
        if (!str.contains("/")) {
            return new android.content.ComponentName(serviceInfo.packageName, str);
        }
        android.content.ComponentName unflattenFromString = android.content.ComponentName.unflattenFromString(str);
        if (unflattenFromString == null) {
            return null;
        }
        if (!unflattenFromString.getPackageName().equals(serviceInfo.packageName)) {
            android.util.Log.w(TAG, "Inconsistent package name in component: " + unflattenFromString.getPackageName() + ", should be: " + serviceInfo.packageName);
            return null;
        }
        return unflattenFromString;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void detach() {
        if (this.mStarted) {
            if (this.mDebug) {
                android.util.Slog.v(this.mTag, "detach(): Calling onDreamingStopped()");
            }
            this.mStarted = false;
            onDreamingStopped();
        }
        if (this.mActivity != null && !this.mActivity.isFinishing()) {
            this.mActivity.finishAndRemoveTask();
        } else {
            finish();
        }
        this.mDreamToken = null;
        this.mCanDoze = false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void attach(android.os.IBinder iBinder, boolean z, boolean z2, final android.os.IRemoteCallback iRemoteCallback) {
        if (this.mDreamToken != null) {
            android.util.Slog.e(this.mTag, "attach() called when dream with token=" + this.mDreamToken + " already attached");
            return;
        }
        if (this.mFinished || this.mWaking) {
            android.util.Slog.w(this.mTag, "attach() called after dream already finished");
            try {
                this.mDreamManager.finishSelf(iBinder, true);
                return;
            } catch (android.os.RemoteException e) {
                return;
            }
        }
        this.mDreamToken = iBinder;
        this.mCanDoze = z;
        if (this.mWindowless && !this.mCanDoze && !isCallerSystemUi()) {
            throw new java.lang.IllegalStateException("Only doze or SystemUI dreams can be windowless.");
        }
        this.mDispatchAfterOnAttachedToWindow = new java.lang.Runnable() { // from class: android.service.dreams.DreamService$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                android.service.dreams.DreamService.this.lambda$attach$2(iRemoteCallback);
            }
        };
        if (!this.mWindowless) {
            android.content.Intent intent = new android.content.Intent(this, (java.lang.Class<?>) android.service.dreams.DreamActivity.class);
            intent.setPackage(getApplicationContext().getPackageName());
            intent.setFlags(268697600);
            intent.putExtra("binder", new android.service.dreams.DreamService.DreamActivityCallbacks(this.mDreamToken));
            intent.putExtra("title", fetchDreamLabel(this, fetchServiceInfo(this, new android.content.ComponentName(this, getClass())), z2));
            try {
                this.mDreamManager.startDreamActivity(intent);
                return;
            } catch (android.os.RemoteException e2) {
                android.util.Log.w(this.mTag, "Could not connect to activity task manager to start dream activity");
                e2.rethrowFromSystemServer();
                return;
            } catch (java.lang.SecurityException e3) {
                android.util.Log.w(this.mTag, "Received SecurityException trying to start DreamActivity. Aborting dream start.");
                detach();
                return;
            }
        }
        this.mDispatchAfterOnAttachedToWindow.run();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$attach$2(android.os.IRemoteCallback iRemoteCallback) {
        if (this.mWindow != null || this.mWindowless) {
            this.mStarted = true;
            try {
                onDreamingStarted();
                try {
                    iRemoteCallback.sendResult(null);
                } catch (android.os.RemoteException e) {
                    throw e.rethrowFromSystemServer();
                }
            } catch (java.lang.Throwable th) {
                try {
                    iRemoteCallback.sendResult(null);
                    throw th;
                } catch (android.os.RemoteException e2) {
                    throw e2.rethrowFromSystemServer();
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onWindowCreated(android.view.Window window) {
        this.mWindow = window;
        this.mWindow.setCallback(this);
        this.mWindow.requestFeature(1);
        android.view.WindowManager.LayoutParams attributes = this.mWindow.getAttributes();
        attributes.flags |= (this.mFullscreen ? 1024 : 0) | 21561601 | (this.mScreenBright ? 128 : 0);
        attributes.layoutInDisplayCutoutMode = 3;
        this.mWindow.setAttributes(attributes);
        this.mWindow.clearFlags(Integer.MIN_VALUE);
        this.mWindow.getDecorView().getWindowInsetsController().hide(android.view.WindowInsets.Type.systemBars());
        this.mWindow.setDecorFitsSystemWindows(false);
        this.mWindow.getDecorView().addOnAttachStateChangeListener(new android.service.dreams.DreamService.AnonymousClass2());
    }

    /* renamed from: android.service.dreams.DreamService$2, reason: invalid class name */
    class AnonymousClass2 implements android.view.View.OnAttachStateChangeListener {
        private java.util.function.Consumer<android.service.dreams.IDreamOverlayClient> mDreamStartOverlayConsumer;

        AnonymousClass2() {
        }

        @Override // android.view.View.OnAttachStateChangeListener
        public void onViewAttachedToWindow(android.view.View view) {
            android.service.dreams.DreamService.this.mDispatchAfterOnAttachedToWindow.run();
            if (android.service.dreams.DreamService.this.mOverlayConnection != null) {
                this.mDreamStartOverlayConsumer = new java.util.function.Consumer() { // from class: android.service.dreams.DreamService$2$$ExternalSyntheticLambda0
                    @Override // java.util.function.Consumer
                    public final void accept(java.lang.Object obj) {
                        android.service.dreams.DreamService.AnonymousClass2.this.lambda$onViewAttachedToWindow$0((android.service.dreams.IDreamOverlayClient) obj);
                    }
                };
                android.service.dreams.DreamService.this.mOverlayConnection.addConsumer(this.mDreamStartOverlayConsumer);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onViewAttachedToWindow$0(android.service.dreams.IDreamOverlayClient iDreamOverlayClient) {
            if (android.service.dreams.DreamService.this.mWindow == null) {
                android.util.Slog.d(android.service.dreams.DreamService.TAG, "mWindow is null");
                return;
            }
            try {
                iDreamOverlayClient.startDream(android.service.dreams.DreamService.this.mWindow.getAttributes(), android.service.dreams.DreamService.this.mOverlayCallback, android.service.dreams.DreamService.this.mDreamComponent.flattenToString(), android.service.dreams.DreamService.this.mShouldShowComplications);
            } catch (android.os.RemoteException e) {
                android.util.Log.e(android.service.dreams.DreamService.this.mTag, "could not send window attributes:" + e);
            }
        }

        @Override // android.view.View.OnAttachStateChangeListener
        public void onViewDetachedFromWindow(android.view.View view) {
            if (android.service.dreams.DreamService.this.mActivity == null || !android.service.dreams.DreamService.this.mActivity.isChangingConfigurations()) {
                android.service.dreams.DreamService.this.mWindow = null;
                android.service.dreams.DreamService.this.mActivity = null;
                android.service.dreams.DreamService.this.finish();
            }
            if (android.service.dreams.DreamService.this.mOverlayConnection != null && this.mDreamStartOverlayConsumer != null) {
                android.service.dreams.DreamService.this.mOverlayConnection.removeConsumer(this.mDreamStartOverlayConsumer);
            }
        }
    }

    private boolean getWindowFlagValue(int i, boolean z) {
        return this.mWindow == null ? z : (i & this.mWindow.getAttributes().flags) != 0;
    }

    private void applyWindowFlags(int i, int i2) {
        if (this.mWindow != null) {
            android.view.WindowManager.LayoutParams attributes = this.mWindow.getAttributes();
            attributes.flags = applyFlags(attributes.flags, i, i2);
            this.mWindow.setAttributes(attributes);
            this.mWindow.getWindowManager().updateViewLayout(this.mWindow.getDecorView(), attributes);
        }
    }

    private boolean isCallerSystemUi() {
        return checkCallingOrSelfPermission(android.Manifest.permission.STATUS_BAR_SERVICE) == 0;
    }

    private int applyFlags(int i, int i2, int i3) {
        return (i & (~i3)) | (i2 & i3);
    }

    private static boolean fetchShouldShowComplications(android.content.Context context, android.content.pm.ServiceInfo serviceInfo) {
        android.service.dreams.DreamService.DreamMetadata dreamMetadata = getDreamMetadata(context, serviceInfo);
        if (dreamMetadata != null) {
            return dreamMetadata.showComplications;
        }
        return false;
    }

    private static java.lang.CharSequence fetchDreamLabel(android.content.Context context, android.content.pm.ServiceInfo serviceInfo, boolean z) {
        if (serviceInfo == null) {
            return null;
        }
        java.lang.CharSequence loadLabel = serviceInfo.loadLabel(context.getPackageManager());
        if (!z || loadLabel == null) {
            return loadLabel;
        }
        return context.getResources().getString(com.android.internal.R.string.dream_preview_title, loadLabel);
    }

    private static android.content.pm.ServiceInfo fetchServiceInfo(android.content.Context context, android.content.ComponentName componentName) {
        try {
            return context.getPackageManager().getServiceInfo(componentName, android.content.pm.PackageManager.ComponentInfoFlags.of(128L));
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            if (DEBUG) {
                android.util.Log.w(TAG, "cannot find component " + componentName.flattenToShortString());
                return null;
            }
            return null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$dump$3(java.io.FileDescriptor fileDescriptor, java.lang.String[] strArr, java.io.PrintWriter printWriter, java.lang.String str) {
        dumpOnHandler(fileDescriptor, printWriter, strArr);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.app.Service
    public void dump(final java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, final java.lang.String[] strArr) {
        com.android.internal.util.DumpUtils.dumpAsync(this.mHandler, new com.android.internal.util.DumpUtils.Dump() { // from class: android.service.dreams.DreamService$$ExternalSyntheticLambda3
            @Override // com.android.internal.util.DumpUtils.Dump
            public final void dump(java.io.PrintWriter printWriter2, java.lang.String str) {
                android.service.dreams.DreamService.this.lambda$dump$3(fileDescriptor, strArr, printWriter2, str);
            }
        }, printWriter, "", 1000L);
    }

    protected void dumpOnHandler(java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, java.lang.String[] strArr) {
        printWriter.print(this.mTag + ": ");
        if (this.mFinished) {
            printWriter.println("stopped");
        } else {
            printWriter.println("running (dreamToken=" + this.mDreamToken + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
        }
        printWriter.println("  window: " + this.mWindow);
        printWriter.print("  flags:");
        if (isInteractive()) {
            printWriter.print(" interactive");
        }
        if (isFullscreen()) {
            printWriter.print(" fullscreen");
        }
        if (isScreenBright()) {
            printWriter.print(" bright");
        }
        if (isWindowless()) {
            printWriter.print(" windowless");
        }
        if (isDozing()) {
            printWriter.print(" dozing");
        } else if (canDoze()) {
            printWriter.print(" candoze");
        }
        printWriter.println();
        if (canDoze()) {
            printWriter.println("  doze screen state: " + android.view.Display.stateToString(this.mDozeScreenState));
            printWriter.println("  doze screen brightness: " + this.mDozeScreenBrightness);
        }
    }

    private static int clampAbsoluteBrightness(int i) {
        return android.util.MathUtils.constrain(i, 0, 255);
    }

    final class DreamServiceWrapper extends android.service.dreams.IDreamService.Stub {
        DreamServiceWrapper() {
        }

        @Override // android.service.dreams.IDreamService
        public void attach(final android.os.IBinder iBinder, final boolean z, final boolean z2, final android.os.IRemoteCallback iRemoteCallback) {
            android.service.dreams.DreamService.this.mHandler.post(new java.lang.Runnable() { // from class: android.service.dreams.DreamService$DreamServiceWrapper$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    android.service.dreams.DreamService.DreamServiceWrapper.this.lambda$attach$0(iBinder, z, z2, iRemoteCallback);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$attach$0(android.os.IBinder iBinder, boolean z, boolean z2, android.os.IRemoteCallback iRemoteCallback) {
            android.service.dreams.DreamService.this.attach(iBinder, z, z2, iRemoteCallback);
        }

        @Override // android.service.dreams.IDreamService
        public void detach() {
            android.os.Handler handler = android.service.dreams.DreamService.this.mHandler;
            final android.service.dreams.DreamService dreamService = android.service.dreams.DreamService.this;
            handler.post(new java.lang.Runnable() { // from class: android.service.dreams.DreamService$DreamServiceWrapper$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    android.service.dreams.DreamService.this.detach();
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$wakeUp$1() {
            android.service.dreams.DreamService.this.wakeUp(true);
        }

        @Override // android.service.dreams.IDreamService
        public void wakeUp() {
            android.service.dreams.DreamService.this.mHandler.post(new java.lang.Runnable() { // from class: android.service.dreams.DreamService$DreamServiceWrapper$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    android.service.dreams.DreamService.DreamServiceWrapper.this.lambda$wakeUp$1();
                }
            });
        }
    }

    final class DreamActivityCallbacks extends android.os.Binder {
        private final android.os.IBinder mActivityDreamToken;

        DreamActivityCallbacks(android.os.IBinder iBinder) {
            this.mActivityDreamToken = iBinder;
        }

        void onActivityCreated(android.service.dreams.DreamActivity dreamActivity) {
            if (this.mActivityDreamToken != android.service.dreams.DreamService.this.mDreamToken || android.service.dreams.DreamService.this.mFinished) {
                android.util.Slog.d(android.service.dreams.DreamService.TAG, "DreamActivity was created after the dream was finished or a new dream started, finishing DreamActivity");
                if (!dreamActivity.isFinishing()) {
                    dreamActivity.finishAndRemoveTask();
                    return;
                }
                return;
            }
            if (android.service.dreams.DreamService.this.mActivity != null) {
                android.util.Slog.w(android.service.dreams.DreamService.TAG, "A DreamActivity has already been started, finishing latest DreamActivity");
                if (!dreamActivity.isFinishing()) {
                    dreamActivity.finishAndRemoveTask();
                    return;
                }
                return;
            }
            android.service.dreams.DreamService.this.mActivity = dreamActivity;
            android.service.dreams.DreamService.this.onWindowCreated(dreamActivity.getWindow());
        }

        void onActivityDestroyed() {
            android.service.dreams.DreamService.this.mActivity = null;
            android.service.dreams.DreamService.this.mWindow = null;
            android.service.dreams.DreamService.this.detach();
        }
    }

    public static final class DreamMetadata {
        public final android.graphics.drawable.Drawable previewImage;
        public final android.content.ComponentName settingsActivity;
        public final boolean showComplications;

        DreamMetadata(android.content.ComponentName componentName, android.graphics.drawable.Drawable drawable, boolean z) {
            this.settingsActivity = componentName;
            this.previewImage = drawable;
            this.showComplications = z;
        }
    }
}
