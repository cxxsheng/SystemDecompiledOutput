package android.app;

/* loaded from: classes.dex */
public class Instrumentation {
    private static final long CONNECT_TIMEOUT_MILLIS = 60000;
    static final boolean DEBUG_START_ACTIVITY;
    public static final java.lang.String REPORT_KEY_IDENTIFIER = "id";
    public static final java.lang.String REPORT_KEY_STREAMRESULT = "stream";
    private static final java.lang.String TAG = "Instrumentation";
    private static final boolean VERBOSE = android.util.Log.isLoggable(TAG, 2);
    private java.util.List<android.app.Instrumentation.ActivityMonitor> mActivityMonitors;
    private android.content.Context mAppContext;
    private android.content.ComponentName mComponent;
    private android.content.Context mInstrContext;
    private android.os.PerformanceCollector mPerformanceCollector;
    private java.lang.Thread mRunner;
    private android.app.UiAutomation mUiAutomation;
    private android.app.IUiAutomationConnection mUiAutomationConnection;
    private java.util.List<android.app.Instrumentation.ActivityWaiter> mWaitingActivities;
    private android.app.IInstrumentationWatcher mWatcher;
    private final java.lang.Object mSync = new java.lang.Object();
    private android.app.ActivityThread mThread = null;
    private android.os.MessageQueue mMessageQueue = null;
    private boolean mAutomaticPerformanceSnapshots = false;
    private android.os.Bundle mPerfMetrics = new android.os.Bundle();
    private final java.lang.Object mAnimationCompleteLock = new java.lang.Object();

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface UiAutomationFlags {
    }

    static {
        boolean z = false;
        if (android.os.Build.IS_DEBUGGABLE && android.os.SystemProperties.getBoolean("persist.wm.debug.start_activity", false)) {
            z = true;
        }
        DEBUG_START_ACTIVITY = z;
    }

    private void checkInstrumenting(java.lang.String str) {
        if (this.mInstrContext == null) {
            throw new java.lang.RuntimeException(str + " cannot be called outside of instrumented processes");
        }
    }

    public boolean isInstrumenting() {
        if (this.mInstrContext == null) {
            return false;
        }
        return true;
    }

    public void onCreate(android.os.Bundle bundle) {
    }

    public void start() {
        if (this.mRunner != null) {
            throw new java.lang.RuntimeException("Instrumentation already started");
        }
        this.mRunner = new android.app.Instrumentation.InstrumentationThread("Instr: " + getClass().getName());
        this.mRunner.start();
    }

    public void onStart() {
    }

    public boolean onException(java.lang.Object obj, java.lang.Throwable th) {
        return false;
    }

    public void sendStatus(int i, android.os.Bundle bundle) {
        if (this.mWatcher != null) {
            try {
                this.mWatcher.instrumentationStatus(this.mComponent, i, bundle);
            } catch (android.os.RemoteException e) {
                this.mWatcher = null;
            }
        }
    }

    public void addResults(android.os.Bundle bundle) {
        try {
            android.app.ActivityManager.getService().addInstrumentationResults(this.mThread.getApplicationThread(), bundle);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void finish(int i, android.os.Bundle bundle) {
        if (this.mAutomaticPerformanceSnapshots) {
            endPerformanceSnapshot();
        }
        if (this.mPerfMetrics != null) {
            if (bundle == null) {
                bundle = new android.os.Bundle();
            }
            bundle.putAll(this.mPerfMetrics);
        }
        if (this.mUiAutomation != null && !this.mUiAutomation.isDestroyed()) {
            this.mUiAutomation.disconnect();
            this.mUiAutomation = null;
        }
        this.mThread.finishInstrumentation(i, bundle);
    }

    public void setAutomaticPerformanceSnapshots() {
        this.mAutomaticPerformanceSnapshots = true;
        this.mPerformanceCollector = new android.os.PerformanceCollector();
    }

    public void startPerformanceSnapshot() {
        if (!isProfiling()) {
            this.mPerformanceCollector.beginSnapshot(null);
        }
    }

    public void endPerformanceSnapshot() {
        if (!isProfiling()) {
            this.mPerfMetrics = this.mPerformanceCollector.endSnapshot();
        }
    }

    public void onDestroy() {
    }

    public android.content.Context getContext() {
        return this.mInstrContext;
    }

    public android.content.ComponentName getComponentName() {
        return this.mComponent;
    }

    public android.content.Context getTargetContext() {
        return this.mAppContext;
    }

    public java.lang.String getProcessName() {
        return this.mThread.getProcessName();
    }

    public boolean isProfiling() {
        return this.mThread.isProfiling();
    }

    public void startProfiling() {
        if (this.mThread.isProfiling()) {
            java.io.File file = new java.io.File(this.mThread.getProfileFilePath());
            file.getParentFile().mkdirs();
            android.os.Debug.startMethodTracing(file.toString(), 8388608);
        }
    }

    public void stopProfiling() {
        if (this.mThread.isProfiling()) {
            android.os.Debug.stopMethodTracing();
        }
    }

    public void setInTouchMode(boolean z) {
        try {
            android.view.IWindowManager.Stub.asInterface(android.os.ServiceManager.getService(android.content.Context.WINDOW_SERVICE)).setInTouchModeOnAllDisplays(z);
        } catch (android.os.RemoteException e) {
        }
    }

    public void resetInTouchMode() {
        setInTouchMode(getContext().getResources().getBoolean(com.android.internal.R.bool.config_defaultInTouchMode));
    }

    public void waitForIdle(java.lang.Runnable runnable) {
        this.mMessageQueue.addIdleHandler(new android.app.Instrumentation.Idler(runnable));
        this.mThread.getHandler().post(new android.app.Instrumentation.EmptyRunnable());
    }

    public void waitForIdleSync() {
        validateNotAppThread();
        android.app.Instrumentation.Idler idler = new android.app.Instrumentation.Idler(null);
        this.mMessageQueue.addIdleHandler(idler);
        this.mThread.getHandler().post(new android.app.Instrumentation.EmptyRunnable());
        idler.waitForIdle();
    }

    private void waitForEnterAnimationComplete(android.app.Activity activity) {
        synchronized (this.mAnimationCompleteLock) {
            long j = 5000;
            while (j > 0) {
                try {
                    if (activity.mEnterAnimationComplete) {
                        break;
                    }
                    long currentTimeMillis = java.lang.System.currentTimeMillis();
                    this.mAnimationCompleteLock.wait(j);
                    j -= java.lang.System.currentTimeMillis() - currentTimeMillis;
                } catch (java.lang.InterruptedException e) {
                }
            }
        }
    }

    public void onEnterAnimationComplete() {
        synchronized (this.mAnimationCompleteLock) {
            this.mAnimationCompleteLock.notifyAll();
        }
    }

    public void runOnMainSync(java.lang.Runnable runnable) {
        validateNotAppThread();
        android.app.Instrumentation.SyncRunnable syncRunnable = new android.app.Instrumentation.SyncRunnable(runnable);
        this.mThread.getHandler().post(syncRunnable);
        syncRunnable.waitForComplete();
    }

    boolean isSdkSandboxAllowedToStartActivities() {
        return android.os.Process.isSdkSandbox() && this.mThread != null && this.mThread.mBoundApplication != null && this.mThread.mBoundApplication.isSdkInSandbox && getContext() != null && getContext().checkSelfPermission(android.Manifest.permission.START_ACTIVITIES_FROM_SDK_SANDBOX) == 0;
    }

    private void adjustIntentForCtsInSdkSandboxInstrumentation(android.content.Intent intent) {
        if (this.mComponent != null && intent.getComponent() != null && getContext().getPackageManager().getSdkSandboxPackageName().equals(intent.getComponent().getPackageName())) {
            intent.setComponent(new android.content.ComponentName(this.mComponent.getPackageName(), intent.getComponent().getClassName()));
        }
        intent.setIdentifier(this.mComponent.getPackageName());
    }

    private android.content.pm.ActivityInfo resolveActivityInfoForCtsInSandbox(android.content.Intent intent) {
        adjustIntentForCtsInSdkSandboxInstrumentation(intent);
        android.content.pm.ActivityInfo resolveActivityInfo = intent.resolveActivityInfo(getTargetContext().getPackageManager(), 0);
        if (resolveActivityInfo != null) {
            resolveActivityInfo.processName = this.mThread.getProcessName();
        }
        return resolveActivityInfo;
    }

    public android.app.Activity startActivitySync(android.content.Intent intent) {
        return startActivitySync(intent, null);
    }

    public android.app.Activity startActivitySync(android.content.Intent intent, android.os.Bundle bundle) {
        android.content.pm.ActivityInfo resolveActivityInfo;
        android.app.Activity activity;
        if (DEBUG_START_ACTIVITY) {
            android.util.Log.d(TAG, "startActivity: intent=" + intent + " options=" + bundle, new java.lang.Throwable());
        }
        validateNotAppThread();
        synchronized (this.mSync) {
            android.content.Intent intent2 = new android.content.Intent(intent);
            if (isSdkSandboxAllowedToStartActivities()) {
                resolveActivityInfo = resolveActivityInfoForCtsInSandbox(intent2);
            } else {
                resolveActivityInfo = intent2.resolveActivityInfo(getTargetContext().getPackageManager(), 0);
            }
            if (resolveActivityInfo == null) {
                throw new java.lang.RuntimeException("Unable to resolve activity for: " + intent2);
            }
            java.lang.String processName = this.mThread.getProcessName();
            if (!resolveActivityInfo.processName.equals(processName)) {
                throw new java.lang.RuntimeException("Intent in process " + processName + " resolved to different process " + resolveActivityInfo.processName + ": " + intent2);
            }
            intent2.setComponent(new android.content.ComponentName(resolveActivityInfo.applicationInfo.packageName, resolveActivityInfo.name));
            android.app.Instrumentation.ActivityWaiter activityWaiter = new android.app.Instrumentation.ActivityWaiter(intent2);
            if (this.mWaitingActivities == null) {
                this.mWaitingActivities = new java.util.ArrayList();
            }
            this.mWaitingActivities.add(activityWaiter);
            getTargetContext().startActivity(intent2, bundle);
            do {
                try {
                    this.mSync.wait();
                } catch (java.lang.InterruptedException e) {
                }
            } while (this.mWaitingActivities.contains(activityWaiter));
            activity = activityWaiter.activity;
        }
        waitForEnterAnimationComplete(activity);
        android.view.SurfaceControl.Transaction transaction = new android.view.SurfaceControl.Transaction();
        try {
            transaction.apply(true);
            transaction.close();
            return activity;
        } catch (java.lang.Throwable th) {
            try {
                transaction.close();
            } catch (java.lang.Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    public static class ActivityMonitor {
        private final boolean mBlock;
        private final java.lang.String mClass;
        int mHits;
        private final boolean mIgnoreMatchingSpecificIntents;
        android.app.Activity mLastActivity;
        private final android.app.Instrumentation.ActivityResult mResult;
        private final android.content.IntentFilter mWhich;

        public ActivityMonitor(android.content.IntentFilter intentFilter, android.app.Instrumentation.ActivityResult activityResult, boolean z) {
            this.mHits = 0;
            this.mLastActivity = null;
            this.mWhich = intentFilter;
            this.mClass = null;
            this.mResult = activityResult;
            this.mBlock = z;
            this.mIgnoreMatchingSpecificIntents = false;
        }

        public ActivityMonitor(java.lang.String str, android.app.Instrumentation.ActivityResult activityResult, boolean z) {
            this.mHits = 0;
            this.mLastActivity = null;
            this.mWhich = null;
            this.mClass = str;
            this.mResult = activityResult;
            this.mBlock = z;
            this.mIgnoreMatchingSpecificIntents = false;
        }

        public ActivityMonitor() {
            this.mHits = 0;
            this.mLastActivity = null;
            this.mWhich = null;
            this.mClass = null;
            this.mResult = null;
            this.mBlock = false;
            this.mIgnoreMatchingSpecificIntents = true;
        }

        final boolean ignoreMatchingSpecificIntents() {
            return this.mIgnoreMatchingSpecificIntents;
        }

        public final android.content.IntentFilter getFilter() {
            return this.mWhich;
        }

        public final android.app.Instrumentation.ActivityResult getResult() {
            return this.mResult;
        }

        public final boolean isBlocking() {
            return this.mBlock;
        }

        public final int getHits() {
            return this.mHits;
        }

        public final android.app.Activity getLastActivity() {
            return this.mLastActivity;
        }

        public final android.app.Activity waitForActivity() {
            android.app.Activity activity;
            synchronized (this) {
                while (this.mLastActivity == null) {
                    try {
                        wait();
                    } catch (java.lang.InterruptedException e) {
                    }
                }
                activity = this.mLastActivity;
                this.mLastActivity = null;
            }
            return activity;
        }

        public final android.app.Activity waitForActivityWithTimeout(long j) {
            synchronized (this) {
                if (this.mLastActivity == null) {
                    try {
                        wait(j);
                    } catch (java.lang.InterruptedException e) {
                    }
                }
                if (this.mLastActivity == null) {
                    return null;
                }
                android.app.Activity activity = this.mLastActivity;
                this.mLastActivity = null;
                return activity;
            }
        }

        public android.app.Instrumentation.ActivityResult onStartActivity(android.content.Context context, android.content.Intent intent, android.os.Bundle bundle) {
            return onStartActivity(intent);
        }

        public android.app.Instrumentation.ActivityResult onStartActivity(android.content.Intent intent) {
            return null;
        }

        public void onStartActivityResult(int i, android.os.Bundle bundle) {
        }

        final boolean match(android.content.Context context, android.app.Activity activity, android.content.Intent intent) {
            java.lang.String str;
            if (this.mIgnoreMatchingSpecificIntents) {
                return false;
            }
            synchronized (this) {
                if (this.mWhich != null && this.mWhich.match(context.getContentResolver(), intent, true, android.app.Instrumentation.TAG) < 0) {
                    return false;
                }
                if (this.mClass != null) {
                    if (activity != null) {
                        str = activity.getClass().getName();
                    } else if (intent.getComponent() == null) {
                        str = null;
                    } else {
                        str = intent.getComponent().getClassName();
                    }
                    if (str == null || !this.mClass.equals(str)) {
                        return false;
                    }
                }
                if (activity != null) {
                    this.mLastActivity = activity;
                    notifyAll();
                }
                return true;
            }
        }
    }

    public void addMonitor(android.app.Instrumentation.ActivityMonitor activityMonitor) {
        synchronized (this.mSync) {
            if (this.mActivityMonitors == null) {
                this.mActivityMonitors = new java.util.ArrayList();
            }
            this.mActivityMonitors.add(activityMonitor);
        }
    }

    public android.app.Instrumentation.ActivityMonitor addMonitor(android.content.IntentFilter intentFilter, android.app.Instrumentation.ActivityResult activityResult, boolean z) {
        android.app.Instrumentation.ActivityMonitor activityMonitor = new android.app.Instrumentation.ActivityMonitor(intentFilter, activityResult, z);
        addMonitor(activityMonitor);
        return activityMonitor;
    }

    public android.app.Instrumentation.ActivityMonitor addMonitor(java.lang.String str, android.app.Instrumentation.ActivityResult activityResult, boolean z) {
        android.app.Instrumentation.ActivityMonitor activityMonitor = new android.app.Instrumentation.ActivityMonitor(str, activityResult, z);
        addMonitor(activityMonitor);
        return activityMonitor;
    }

    public boolean checkMonitorHit(android.app.Instrumentation.ActivityMonitor activityMonitor, int i) {
        waitForIdleSync();
        synchronized (this.mSync) {
            if (activityMonitor.getHits() < i) {
                return false;
            }
            this.mActivityMonitors.remove(activityMonitor);
            return true;
        }
    }

    public android.app.Activity waitForMonitor(android.app.Instrumentation.ActivityMonitor activityMonitor) {
        android.app.Activity waitForActivity = activityMonitor.waitForActivity();
        synchronized (this.mSync) {
            this.mActivityMonitors.remove(activityMonitor);
        }
        return waitForActivity;
    }

    public android.app.Activity waitForMonitorWithTimeout(android.app.Instrumentation.ActivityMonitor activityMonitor, long j) {
        android.app.Activity waitForActivityWithTimeout = activityMonitor.waitForActivityWithTimeout(j);
        synchronized (this.mSync) {
            this.mActivityMonitors.remove(activityMonitor);
        }
        return waitForActivityWithTimeout;
    }

    public void removeMonitor(android.app.Instrumentation.ActivityMonitor activityMonitor) {
        synchronized (this.mSync) {
            this.mActivityMonitors.remove(activityMonitor);
        }
    }

    /* renamed from: android.app.Instrumentation$1MenuRunnable, reason: invalid class name */
    class C1MenuRunnable implements java.lang.Runnable {
        private final android.app.Activity activity;
        private final int flags;
        private final int identifier;
        boolean returnValue;

        public C1MenuRunnable(android.app.Activity activity, int i, int i2) {
            this.activity = activity;
            this.identifier = i;
            this.flags = i2;
        }

        @Override // java.lang.Runnable
        public void run() {
            this.returnValue = this.activity.getWindow().performPanelIdentifierAction(0, this.identifier, this.flags);
        }
    }

    public boolean invokeMenuActionSync(android.app.Activity activity, int i, int i2) {
        android.app.Instrumentation.C1MenuRunnable c1MenuRunnable = new android.app.Instrumentation.C1MenuRunnable(activity, i, i2);
        runOnMainSync(c1MenuRunnable);
        return c1MenuRunnable.returnValue;
    }

    public boolean invokeContextMenuAction(android.app.Activity activity, int i, int i2) {
        validateNotAppThread();
        sendKeySync(new android.view.KeyEvent(0, 23));
        waitForIdleSync();
        try {
            java.lang.Thread.sleep(android.view.ViewConfiguration.getLongPressTimeout());
            sendKeySync(new android.view.KeyEvent(1, 23));
            waitForIdleSync();
            android.app.Instrumentation.C1ContextMenuRunnable c1ContextMenuRunnable = new android.app.Instrumentation.C1ContextMenuRunnable(activity, i, i2);
            runOnMainSync(c1ContextMenuRunnable);
            return c1ContextMenuRunnable.returnValue;
        } catch (java.lang.InterruptedException e) {
            android.util.Log.e(TAG, "Could not sleep for long press timeout", e);
            return false;
        }
    }

    /* renamed from: android.app.Instrumentation$1ContextMenuRunnable, reason: invalid class name */
    class C1ContextMenuRunnable implements java.lang.Runnable {
        private final android.app.Activity activity;
        private final int flags;
        private final int identifier;
        boolean returnValue;

        public C1ContextMenuRunnable(android.app.Activity activity, int i, int i2) {
            this.activity = activity;
            this.identifier = i;
            this.flags = i2;
        }

        @Override // java.lang.Runnable
        public void run() {
            this.returnValue = this.activity.getWindow().performContextMenuIdentifierAction(this.identifier, this.flags);
        }
    }

    public void sendStringSync(java.lang.String str) {
        android.view.KeyEvent[] events;
        if (str != null && (events = android.view.KeyCharacterMap.load(-1).getEvents(str.toCharArray())) != null) {
            for (android.view.KeyEvent keyEvent : events) {
                sendKeySync(android.view.KeyEvent.changeTimeRepeat(keyEvent, android.os.SystemClock.uptimeMillis(), 0));
            }
        }
    }

    public void sendKeySync(android.view.KeyEvent keyEvent) {
        validateNotAppThread();
        long downTime = keyEvent.getDownTime();
        long eventTime = keyEvent.getEventTime();
        int source = keyEvent.getSource();
        if (source == 0) {
            source = 257;
        }
        if (eventTime == 0) {
            eventTime = android.os.SystemClock.uptimeMillis();
        }
        if (downTime == 0) {
            downTime = eventTime;
        }
        android.view.KeyEvent keyEvent2 = new android.view.KeyEvent(keyEvent);
        keyEvent2.setTime(downTime, eventTime);
        keyEvent2.setSource(source);
        keyEvent2.setFlags(keyEvent.getFlags() | 8);
        setDisplayIfNeeded(keyEvent2);
        android.hardware.input.InputManagerGlobal.getInstance().injectInputEvent(keyEvent2, 2);
    }

    private void setDisplayIfNeeded(android.view.KeyEvent keyEvent) {
        if (!android.os.UserManager.isVisibleBackgroundUsersEnabled()) {
            return;
        }
        int displayId = keyEvent.getDisplayId();
        if (displayId != -1) {
            if (VERBOSE) {
                android.util.Log.v(TAG, "setDisplayIfNeeded(" + keyEvent + "): not changing display id as it's explicitly set to " + displayId);
                return;
            }
            return;
        }
        int mainDisplayIdAssignedToUser = ((android.os.UserManager) this.mInstrContext.getSystemService(android.os.UserManager.class)).getMainDisplayIdAssignedToUser();
        if (VERBOSE) {
            android.util.Log.v(TAG, "setDisplayIfNeeded(" + keyEvent + "): eventDisplayId=" + displayId + ", user=" + this.mInstrContext.getUser() + ", userDisplayId=" + mainDisplayIdAssignedToUser);
        }
        if (mainDisplayIdAssignedToUser == -1) {
            android.util.Log.e(TAG, "setDisplayIfNeeded(" + keyEvent + "): UserManager returned INVALID_DISPLAY as display assigned to user " + this.mInstrContext.getUser());
        } else {
            keyEvent.setDisplayId(mainDisplayIdAssignedToUser);
        }
    }

    public void sendKeyDownUpSync(int i) {
        sendKeySync(new android.view.KeyEvent(0, i));
        sendKeySync(new android.view.KeyEvent(1, i));
    }

    public void sendCharacterSync(int i) {
        sendKeyDownUpSync(i);
    }

    public void sendPointerSync(android.view.MotionEvent motionEvent) {
        validateNotAppThread();
        if ((motionEvent.getSource() & 2) == 0) {
            motionEvent.setSource(4098);
        }
        syncInputTransactionsAndInjectEventIntoSelf(motionEvent);
    }

    private void syncInputTransactionsAndInjectEventIntoSelf(android.view.MotionEvent motionEvent) {
        boolean z = motionEvent.getAction() == 0 || motionEvent.isFromSource(8194);
        boolean z2 = motionEvent.getAction() == 1;
        if (z) {
            try {
                android.view.WindowManagerGlobal.getWindowManagerService().syncInputTransactions(true);
            } catch (android.os.RemoteException e) {
                e.rethrowFromSystemServer();
                return;
            }
        }
        android.hardware.input.InputManagerGlobal.getInstance().injectInputEvent(motionEvent, 2, android.os.Process.myUid());
        if (z2) {
            android.view.WindowManagerGlobal.getWindowManagerService().syncInputTransactions(true);
        }
    }

    public void sendTrackballEventSync(android.view.MotionEvent motionEvent) {
        validateNotAppThread();
        if (!motionEvent.isFromSource(4)) {
            motionEvent.setSource(65540);
        }
        android.hardware.input.InputManagerGlobal.getInstance().injectInputEvent(motionEvent, 2);
    }

    public android.app.Application newApplication(java.lang.ClassLoader classLoader, java.lang.String str, android.content.Context context) throws java.lang.InstantiationException, java.lang.IllegalAccessException, java.lang.ClassNotFoundException {
        android.app.Application instantiateApplication = getFactory(context.getPackageName()).instantiateApplication(classLoader, str);
        instantiateApplication.attach(context);
        return instantiateApplication;
    }

    public static android.app.Application newApplication(java.lang.Class<?> cls, android.content.Context context) throws java.lang.InstantiationException, java.lang.IllegalAccessException, java.lang.ClassNotFoundException {
        android.app.Application application = (android.app.Application) cls.newInstance();
        application.attach(context);
        return application;
    }

    public void callApplicationOnCreate(android.app.Application application) {
        application.onCreate();
    }

    public android.app.Activity newActivity(java.lang.Class<?> cls, android.content.Context context, android.os.IBinder iBinder, android.app.Application application, android.content.Intent intent, android.content.pm.ActivityInfo activityInfo, java.lang.CharSequence charSequence, android.app.Activity activity, java.lang.String str, java.lang.Object obj) throws java.lang.InstantiationException, java.lang.IllegalAccessException {
        android.app.Application application2;
        android.app.Activity activity2 = (android.app.Activity) cls.newInstance();
        if (application != null) {
            application2 = application;
        } else {
            application2 = new android.app.Application();
        }
        activity2.attach(context, null, this, iBinder, 0, application2, intent, activityInfo, charSequence, activity, str, (android.app.Activity.NonConfigurationInstances) obj, new android.content.res.Configuration(), null, null, null, null, null, null, null);
        return activity2;
    }

    public android.app.Activity newActivity(java.lang.ClassLoader classLoader, java.lang.String str, android.content.Intent intent) throws java.lang.InstantiationException, java.lang.IllegalAccessException, java.lang.ClassNotFoundException {
        return getFactory((intent == null || intent.getComponent() == null) ? null : intent.getComponent().getPackageName()).instantiateActivity(classLoader, str, intent);
    }

    private android.app.AppComponentFactory getFactory(java.lang.String str) {
        if (str == null) {
            android.util.Log.e(TAG, "No pkg specified, disabling AppComponentFactory");
            return android.app.AppComponentFactory.DEFAULT;
        }
        if (this.mThread == null) {
            android.util.Log.e(TAG, "Uninitialized ActivityThread, likely app-created Instrumentation, disabling AppComponentFactory", new java.lang.Throwable());
            return android.app.AppComponentFactory.DEFAULT;
        }
        android.app.LoadedApk peekPackageInfo = this.mThread.peekPackageInfo(str, true);
        if (peekPackageInfo == null) {
            peekPackageInfo = this.mThread.getSystemContext().mPackageInfo;
        }
        return peekPackageInfo.getAppFactory();
    }

    private void notifyStartActivityResult(int i, android.os.Bundle bundle) {
        if (this.mActivityMonitors == null) {
            return;
        }
        synchronized (this.mSync) {
            int size = this.mActivityMonitors.size();
            for (int i2 = 0; i2 < size; i2++) {
                android.app.Instrumentation.ActivityMonitor activityMonitor = this.mActivityMonitors.get(i2);
                if (activityMonitor.ignoreMatchingSpecificIntents()) {
                    if (bundle == null) {
                        bundle = android.app.ActivityOptions.makeBasic().toBundle();
                    }
                    activityMonitor.onStartActivityResult(i, bundle);
                }
            }
        }
    }

    private void prePerformCreate(android.app.Activity activity) {
        if (this.mWaitingActivities != null) {
            synchronized (this.mSync) {
                int size = this.mWaitingActivities.size();
                for (int i = 0; i < size; i++) {
                    android.app.Instrumentation.ActivityWaiter activityWaiter = this.mWaitingActivities.get(i);
                    if (activityWaiter.intent.filterEquals(activity.getIntent())) {
                        activityWaiter.activity = activity;
                        this.mMessageQueue.addIdleHandler(new android.app.Instrumentation.ActivityGoing(activityWaiter));
                    }
                }
            }
        }
    }

    private void postPerformCreate(android.app.Activity activity) {
        if (this.mActivityMonitors != null) {
            synchronized (this.mSync) {
                int size = this.mActivityMonitors.size();
                for (int i = 0; i < size; i++) {
                    this.mActivityMonitors.get(i).match(activity, activity, activity.getIntent());
                }
            }
        }
    }

    public void callActivityOnCreate(android.app.Activity activity, android.os.Bundle bundle) {
        prePerformCreate(activity);
        activity.performCreate(bundle);
        postPerformCreate(activity);
    }

    public void callActivityOnCreate(android.app.Activity activity, android.os.Bundle bundle, android.os.PersistableBundle persistableBundle) {
        prePerformCreate(activity);
        activity.performCreate(bundle, persistableBundle);
        postPerformCreate(activity);
    }

    public void callActivityOnDestroy(android.app.Activity activity) {
        activity.performDestroy();
    }

    public void callActivityOnRestoreInstanceState(android.app.Activity activity, android.os.Bundle bundle) {
        activity.performRestoreInstanceState(bundle);
    }

    public void callActivityOnRestoreInstanceState(android.app.Activity activity, android.os.Bundle bundle, android.os.PersistableBundle persistableBundle) {
        activity.performRestoreInstanceState(bundle, persistableBundle);
    }

    public void callActivityOnPostCreate(android.app.Activity activity, android.os.Bundle bundle) {
        activity.onPostCreate(bundle);
    }

    public void callActivityOnPostCreate(android.app.Activity activity, android.os.Bundle bundle, android.os.PersistableBundle persistableBundle) {
        activity.onPostCreate(bundle, persistableBundle);
    }

    public void callActivityOnNewIntent(android.app.Activity activity, android.content.Intent intent) {
        if (android.security.Flags.contentUriPermissionApis()) {
            activity.performNewIntent(intent, new android.app.ComponentCaller(activity.getActivityToken(), null));
        } else {
            activity.performNewIntent(intent);
        }
    }

    public void callActivityOnNewIntent(android.app.Activity activity, android.content.Intent intent, android.app.ComponentCaller componentCaller) {
        activity.performNewIntent(intent, componentCaller);
    }

    public void callActivityOnNewIntent(android.app.Activity activity, com.android.internal.content.ReferrerIntent referrerIntent, android.app.ComponentCaller componentCaller) {
        internalCallActivityOnNewIntent(activity, referrerIntent, componentCaller);
    }

    private void internalCallActivityOnNewIntent(android.app.Activity activity, com.android.internal.content.ReferrerIntent referrerIntent, android.app.ComponentCaller componentCaller) {
        java.lang.String str = activity.mReferrer;
        if (referrerIntent != null) {
            try {
                activity.mReferrer = referrerIntent.mReferrer;
            } catch (java.lang.Throwable th) {
                activity.mReferrer = str;
                throw th;
            }
        }
        callActivityOnNewIntent(activity, referrerIntent != null ? new android.content.Intent(referrerIntent) : null, componentCaller);
        activity.mReferrer = str;
    }

    public void callActivityOnNewIntent(android.app.Activity activity, com.android.internal.content.ReferrerIntent referrerIntent) {
        if (android.security.Flags.contentUriPermissionApis()) {
            internalCallActivityOnNewIntent(activity, referrerIntent, new android.app.ComponentCaller(activity.getActivityToken(), null));
            return;
        }
        java.lang.String str = activity.mReferrer;
        if (referrerIntent != null) {
            try {
                activity.mReferrer = referrerIntent.mReferrer;
            } catch (java.lang.Throwable th) {
                activity.mReferrer = str;
                throw th;
            }
        }
        callActivityOnNewIntent(activity, referrerIntent != null ? new android.content.Intent(referrerIntent) : null);
        activity.mReferrer = str;
    }

    public void callActivityOnStart(android.app.Activity activity) {
        activity.onStart();
    }

    public void callActivityOnRestart(android.app.Activity activity) {
        activity.onRestart();
    }

    public void callActivityOnResume(android.app.Activity activity) {
        activity.mResumed = true;
        activity.onResume();
        if (this.mActivityMonitors != null) {
            synchronized (this.mSync) {
                int size = this.mActivityMonitors.size();
                for (int i = 0; i < size; i++) {
                    this.mActivityMonitors.get(i).match(activity, activity, activity.getIntent());
                }
            }
        }
    }

    public void callActivityOnStop(android.app.Activity activity) {
        activity.onStop();
    }

    public void callActivityOnSaveInstanceState(android.app.Activity activity, android.os.Bundle bundle) {
        activity.performSaveInstanceState(bundle);
    }

    public void callActivityOnSaveInstanceState(android.app.Activity activity, android.os.Bundle bundle, android.os.PersistableBundle persistableBundle) {
        activity.performSaveInstanceState(bundle, persistableBundle);
    }

    public void callActivityOnPause(android.app.Activity activity) {
        activity.performPause();
    }

    public void callActivityOnUserLeaving(android.app.Activity activity) {
        activity.performUserLeaving();
    }

    public void callActivityOnPictureInPictureRequested(android.app.Activity activity) {
        activity.onPictureInPictureRequested();
    }

    @java.lang.Deprecated
    public void startAllocCounting() {
        java.lang.Runtime.getRuntime().gc();
        java.lang.Runtime.getRuntime().runFinalization();
        java.lang.Runtime.getRuntime().gc();
        android.os.Debug.resetAllCounts();
        android.os.Debug.startAllocCounting();
    }

    @java.lang.Deprecated
    public void stopAllocCounting() {
        java.lang.Runtime.getRuntime().gc();
        java.lang.Runtime.getRuntime().runFinalization();
        java.lang.Runtime.getRuntime().gc();
        android.os.Debug.stopAllocCounting();
    }

    private void addValue(java.lang.String str, int i, android.os.Bundle bundle) {
        if (bundle.containsKey(str)) {
            java.util.ArrayList<java.lang.Integer> integerArrayList = bundle.getIntegerArrayList(str);
            if (integerArrayList != null) {
                integerArrayList.add(java.lang.Integer.valueOf(i));
                return;
            }
            return;
        }
        java.util.ArrayList<java.lang.Integer> arrayList = new java.util.ArrayList<>();
        arrayList.add(java.lang.Integer.valueOf(i));
        bundle.putIntegerArrayList(str, arrayList);
    }

    public android.os.Bundle getAllocCounts() {
        android.os.Bundle bundle = new android.os.Bundle();
        bundle.putLong(android.os.PerformanceCollector.METRIC_KEY_GLOBAL_ALLOC_COUNT, android.os.Debug.getGlobalAllocCount());
        bundle.putLong(android.os.PerformanceCollector.METRIC_KEY_GLOBAL_ALLOC_SIZE, android.os.Debug.getGlobalAllocSize());
        bundle.putLong(android.os.PerformanceCollector.METRIC_KEY_GLOBAL_FREED_COUNT, android.os.Debug.getGlobalFreedCount());
        bundle.putLong(android.os.PerformanceCollector.METRIC_KEY_GLOBAL_FREED_SIZE, android.os.Debug.getGlobalFreedSize());
        bundle.putLong(android.os.PerformanceCollector.METRIC_KEY_GC_INVOCATION_COUNT, android.os.Debug.getGlobalGcInvocationCount());
        return bundle;
    }

    public android.os.Bundle getBinderCounts() {
        android.os.Bundle bundle = new android.os.Bundle();
        bundle.putLong(android.os.PerformanceCollector.METRIC_KEY_SENT_TRANSACTIONS, android.os.Debug.getBinderSentTransactions());
        bundle.putLong(android.os.PerformanceCollector.METRIC_KEY_RECEIVED_TRANSACTIONS, android.os.Debug.getBinderReceivedTransactions());
        return bundle;
    }

    public static final class ActivityResult {
        private final int mResultCode;
        private final android.content.Intent mResultData;

        public ActivityResult(int i, android.content.Intent intent) {
            this.mResultCode = i;
            this.mResultData = intent;
        }

        public int getResultCode() {
            return this.mResultCode;
        }

        public android.content.Intent getResultData() {
            return this.mResultData;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:37:0x00d0, code lost:
    
        r15 = r5;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public android.app.Instrumentation.ActivityResult execStartActivity(android.content.Context context, android.os.IBinder iBinder, android.os.IBinder iBinder2, android.app.Activity activity, android.content.Intent intent, int i, android.os.Bundle bundle) {
        android.os.Bundle bundle2;
        android.os.Bundle bundle3;
        android.app.Instrumentation.ActivityResult activityResult;
        if (!DEBUG_START_ACTIVITY) {
            bundle2 = bundle;
        } else {
            bundle2 = bundle;
            android.util.Log.d(TAG, "startActivity: who=" + context + " source=" + activity + " intent=" + intent + " requestCode=" + i + " options=" + bundle2, new java.lang.Throwable());
        }
        java.util.Objects.requireNonNull(intent);
        android.app.IApplicationThread iApplicationThread = (android.app.IApplicationThread) iBinder;
        android.net.Uri onProvideReferrer = activity != null ? activity.onProvideReferrer() : null;
        if (onProvideReferrer != null) {
            intent.putExtra(android.content.Intent.EXTRA_REFERRER, onProvideReferrer);
        }
        if (isSdkSandboxAllowedToStartActivities()) {
            adjustIntentForCtsInSdkSandboxInstrumentation(intent);
        }
        if (this.mActivityMonitors == null) {
            bundle3 = bundle2;
        } else {
            synchronized (this.mSync) {
                int size = this.mActivityMonitors.size();
                int i2 = 0;
                while (true) {
                    if (i2 >= size) {
                        break;
                    }
                    android.app.Instrumentation.ActivityMonitor activityMonitor = this.mActivityMonitors.get(i2);
                    if (!activityMonitor.ignoreMatchingSpecificIntents()) {
                        activityResult = null;
                    } else {
                        if (bundle2 == null) {
                            bundle2 = android.app.ActivityOptions.makeBasic().toBundle();
                        }
                        activityResult = activityMonitor.onStartActivity(context, intent, bundle2);
                    }
                    if (activityResult != null) {
                        activityMonitor.mHits++;
                        return activityResult;
                    }
                    if (!activityMonitor.match(context, null, intent)) {
                        i2++;
                    } else {
                        activityMonitor.mHits++;
                        if (activityMonitor.isBlocking()) {
                            return i >= 0 ? activityMonitor.getResult() : null;
                        }
                    }
                }
            }
        }
        try {
            intent.migrateExtraStreamToClipData(context);
            intent.prepareToLeaveProcess(context);
            int startActivity = android.app.ActivityTaskManager.getService().startActivity(iApplicationThread, context.getOpPackageName(), context.getAttributionTag(), intent, intent.resolveTypeIfNeeded(context.getContentResolver()), iBinder2, activity != null ? activity.mEmbeddedID : null, i, 0, null, bundle3);
            notifyStartActivityResult(startActivity, bundle3);
            checkStartActivityResult(startActivity, intent);
            return null;
        } catch (android.os.RemoteException e) {
            throw new java.lang.RuntimeException("Failure from system", e);
        }
    }

    public void execStartActivities(android.content.Context context, android.os.IBinder iBinder, android.os.IBinder iBinder2, android.app.Activity activity, android.content.Intent[] intentArr, android.os.Bundle bundle) {
        execStartActivitiesAsUser(context, iBinder, iBinder2, activity, intentArr, bundle, context.getUserId());
    }

    /* JADX WARN: Code restructure failed: missing block: B:42:0x00ef, code lost:
    
        r12 = r4;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public int execStartActivitiesAsUser(android.content.Context context, android.os.IBinder iBinder, android.os.IBinder iBinder2, android.app.Activity activity, android.content.Intent[] intentArr, android.os.Bundle bundle, int i) {
        android.os.Bundle bundle2;
        android.os.Bundle bundle3;
        android.app.Instrumentation.ActivityResult activityResult;
        if (!DEBUG_START_ACTIVITY) {
            bundle2 = bundle;
        } else {
            java.util.StringJoiner stringJoiner = new java.util.StringJoiner(", ");
            for (android.content.Intent intent : intentArr) {
                stringJoiner.add(intent.toString());
            }
            bundle2 = bundle;
            android.util.Log.d(TAG, "startActivities: who=" + context + " source=" + activity + " userId=" + i + " intents=[" + stringJoiner + "] options=" + bundle2, new java.lang.Throwable());
        }
        java.util.Objects.requireNonNull(intentArr);
        for (int length = intentArr.length - 1; length >= 0; length--) {
            java.util.Objects.requireNonNull(intentArr[length]);
        }
        android.app.IApplicationThread iApplicationThread = (android.app.IApplicationThread) iBinder;
        if (isSdkSandboxAllowedToStartActivities()) {
            for (android.content.Intent intent2 : intentArr) {
                adjustIntentForCtsInSdkSandboxInstrumentation(intent2);
            }
        }
        if (this.mActivityMonitors == null) {
            bundle3 = bundle2;
        } else {
            synchronized (this.mSync) {
                int size = this.mActivityMonitors.size();
                int i2 = 0;
                while (true) {
                    if (i2 >= size) {
                        break;
                    }
                    android.app.Instrumentation.ActivityMonitor activityMonitor = this.mActivityMonitors.get(i2);
                    if (!activityMonitor.ignoreMatchingSpecificIntents()) {
                        activityResult = null;
                    } else {
                        if (bundle2 == null) {
                            bundle2 = android.app.ActivityOptions.makeBasic().toBundle();
                        }
                        activityResult = activityMonitor.onStartActivity(context, intentArr[0], bundle2);
                    }
                    if (activityResult != null) {
                        activityMonitor.mHits++;
                        return -96;
                    }
                    if (!activityMonitor.match(context, null, intentArr[0])) {
                        i2++;
                    } else {
                        activityMonitor.mHits++;
                        if (activityMonitor.isBlocking()) {
                            return -96;
                        }
                    }
                }
            }
        }
        try {
            java.lang.String[] strArr = new java.lang.String[intentArr.length];
            for (int i3 = 0; i3 < intentArr.length; i3++) {
                intentArr[i3].migrateExtraStreamToClipData(context);
                intentArr[i3].prepareToLeaveProcess(context);
                strArr[i3] = intentArr[i3].resolveTypeIfNeeded(context.getContentResolver());
            }
            int startActivities = android.app.ActivityTaskManager.getService().startActivities(iApplicationThread, context.getOpPackageName(), context.getAttributionTag(), intentArr, strArr, iBinder2, bundle3, i);
            notifyStartActivityResult(startActivities, bundle3);
            checkStartActivityResult(startActivities, intentArr[0]);
            return startActivities;
        } catch (android.os.RemoteException e) {
            throw new java.lang.RuntimeException("Failure from system", e);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:32:0x00c3, code lost:
    
        r15 = r4;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public android.app.Instrumentation.ActivityResult execStartActivity(android.content.Context context, android.os.IBinder iBinder, android.os.IBinder iBinder2, java.lang.String str, android.content.Intent intent, int i, android.os.Bundle bundle) {
        android.os.Bundle bundle2;
        android.os.Bundle bundle3;
        android.app.Instrumentation.ActivityResult activityResult;
        if (DEBUG_START_ACTIVITY) {
            bundle2 = bundle;
            android.util.Log.d(TAG, "startActivity: who=" + context + " target=" + str + " intent=" + intent + " requestCode=" + i + " options=" + bundle2, new java.lang.Throwable());
        } else {
            bundle2 = bundle;
        }
        java.util.Objects.requireNonNull(intent);
        android.app.IApplicationThread iApplicationThread = (android.app.IApplicationThread) iBinder;
        if (isSdkSandboxAllowedToStartActivities()) {
            adjustIntentForCtsInSdkSandboxInstrumentation(intent);
        }
        if (this.mActivityMonitors == null) {
            bundle3 = bundle2;
        } else {
            synchronized (this.mSync) {
                int size = this.mActivityMonitors.size();
                int i2 = 0;
                while (true) {
                    if (i2 >= size) {
                        break;
                    }
                    android.app.Instrumentation.ActivityMonitor activityMonitor = this.mActivityMonitors.get(i2);
                    if (!activityMonitor.ignoreMatchingSpecificIntents()) {
                        activityResult = null;
                    } else {
                        if (bundle2 == null) {
                            bundle2 = android.app.ActivityOptions.makeBasic().toBundle();
                        }
                        activityResult = activityMonitor.onStartActivity(context, intent, bundle2);
                    }
                    if (activityResult != null) {
                        activityMonitor.mHits++;
                        return activityResult;
                    }
                    if (!activityMonitor.match(context, null, intent)) {
                        i2++;
                    } else {
                        activityMonitor.mHits++;
                        if (activityMonitor.isBlocking()) {
                            return i >= 0 ? activityMonitor.getResult() : null;
                        }
                    }
                }
            }
        }
        try {
            intent.migrateExtraStreamToClipData(context);
            intent.prepareToLeaveProcess(context);
            int startActivity = android.app.ActivityTaskManager.getService().startActivity(iApplicationThread, context.getOpPackageName(), context.getAttributionTag(), intent, intent.resolveTypeIfNeeded(context.getContentResolver()), iBinder2, str, i, 0, null, bundle3);
            notifyStartActivityResult(startActivity, bundle3);
            checkStartActivityResult(startActivity, intent);
            return null;
        } catch (android.os.RemoteException e) {
            throw new java.lang.RuntimeException("Failure from system", e);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:32:0x00d1, code lost:
    
        r13 = r5;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public android.app.Instrumentation.ActivityResult execStartActivity(android.content.Context context, android.os.IBinder iBinder, android.os.IBinder iBinder2, java.lang.String str, android.content.Intent intent, int i, android.os.Bundle bundle, android.os.UserHandle userHandle) {
        android.os.Bundle bundle2;
        android.os.Bundle bundle3;
        android.app.Instrumentation.ActivityResult activityResult;
        if (DEBUG_START_ACTIVITY) {
            bundle2 = bundle;
            android.util.Log.d(TAG, "startActivity: who=" + context + " user=" + userHandle + " intent=" + intent + " requestCode=" + i + " resultWho=" + str + " options=" + bundle2, new java.lang.Throwable());
        } else {
            bundle2 = bundle;
        }
        java.util.Objects.requireNonNull(intent);
        android.app.IApplicationThread iApplicationThread = (android.app.IApplicationThread) iBinder;
        if (isSdkSandboxAllowedToStartActivities()) {
            adjustIntentForCtsInSdkSandboxInstrumentation(intent);
        }
        if (this.mActivityMonitors == null) {
            bundle3 = bundle2;
        } else {
            synchronized (this.mSync) {
                int size = this.mActivityMonitors.size();
                int i2 = 0;
                while (true) {
                    if (i2 >= size) {
                        break;
                    }
                    android.app.Instrumentation.ActivityMonitor activityMonitor = this.mActivityMonitors.get(i2);
                    if (!activityMonitor.ignoreMatchingSpecificIntents()) {
                        activityResult = null;
                    } else {
                        if (bundle2 == null) {
                            bundle2 = android.app.ActivityOptions.makeBasic().toBundle();
                        }
                        activityResult = activityMonitor.onStartActivity(context, intent, bundle2);
                    }
                    if (activityResult != null) {
                        activityMonitor.mHits++;
                        return activityResult;
                    }
                    if (!activityMonitor.match(context, null, intent)) {
                        i2++;
                    } else {
                        activityMonitor.mHits++;
                        if (activityMonitor.isBlocking()) {
                            return i >= 0 ? activityMonitor.getResult() : null;
                        }
                    }
                }
            }
        }
        try {
            intent.migrateExtraStreamToClipData(context);
            intent.prepareToLeaveProcess(context);
            int startActivityAsUser = android.app.ActivityTaskManager.getService().startActivityAsUser(iApplicationThread, context.getOpPackageName(), context.getAttributionTag(), intent, intent.resolveTypeIfNeeded(context.getContentResolver()), iBinder2, str, i, 0, null, bundle3, userHandle.getIdentifier());
            notifyStartActivityResult(startActivityAsUser, bundle3);
            checkStartActivityResult(startActivityAsUser, intent);
            return null;
        } catch (android.os.RemoteException e) {
            throw new java.lang.RuntimeException("Failure from system", e);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:32:0x00dd, code lost:
    
        r10 = r5;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public android.app.Instrumentation.ActivityResult execStartActivityAsCaller(android.content.Context context, android.os.IBinder iBinder, android.os.IBinder iBinder2, android.app.Activity activity, android.content.Intent intent, int i, android.os.Bundle bundle, boolean z, int i2) {
        android.os.Bundle bundle2;
        android.os.Bundle bundle3;
        android.app.Instrumentation.ActivityResult activityResult;
        if (!DEBUG_START_ACTIVITY) {
            bundle2 = bundle;
        } else {
            bundle2 = bundle;
            android.util.Log.d(TAG, "startActivity: who=" + context + " source=" + activity + " userId=" + i2 + " intent=" + intent + " requestCode=" + i + " ignoreTargetSecurity=" + z + " options=" + bundle2, new java.lang.Throwable());
        }
        java.util.Objects.requireNonNull(intent);
        android.app.IApplicationThread iApplicationThread = (android.app.IApplicationThread) iBinder;
        if (isSdkSandboxAllowedToStartActivities()) {
            adjustIntentForCtsInSdkSandboxInstrumentation(intent);
        }
        if (this.mActivityMonitors == null) {
            bundle3 = bundle2;
        } else {
            synchronized (this.mSync) {
                int size = this.mActivityMonitors.size();
                int i3 = 0;
                while (true) {
                    if (i3 >= size) {
                        break;
                    }
                    android.app.Instrumentation.ActivityMonitor activityMonitor = this.mActivityMonitors.get(i3);
                    if (!activityMonitor.ignoreMatchingSpecificIntents()) {
                        activityResult = null;
                    } else {
                        if (bundle2 == null) {
                            bundle2 = android.app.ActivityOptions.makeBasic().toBundle();
                        }
                        activityResult = activityMonitor.onStartActivity(context, intent, bundle2);
                    }
                    if (activityResult != null) {
                        activityMonitor.mHits++;
                        return activityResult;
                    }
                    if (!activityMonitor.match(context, null, intent)) {
                        i3++;
                    } else {
                        activityMonitor.mHits++;
                        if (activityMonitor.isBlocking()) {
                            return i >= 0 ? activityMonitor.getResult() : null;
                        }
                    }
                }
            }
        }
        try {
            intent.migrateExtraStreamToClipData(context);
            intent.prepareToLeaveProcess(context);
            android.app.IActivityTaskManager service = android.app.ActivityTaskManager.getService();
            java.lang.String opPackageName = context.getOpPackageName();
            java.lang.String resolveTypeIfNeeded = intent.resolveTypeIfNeeded(context.getContentResolver());
            android.os.Bundle bundle4 = bundle3;
            int startActivityAsCaller = service.startActivityAsCaller(iApplicationThread, opPackageName, intent, resolveTypeIfNeeded, iBinder2, activity != null ? activity.mEmbeddedID : null, i, 0, null, bundle4, z, i2);
            notifyStartActivityResult(startActivityAsCaller, bundle4);
            checkStartActivityResult(startActivityAsCaller, intent);
            return null;
        } catch (android.os.RemoteException e) {
            throw new java.lang.RuntimeException("Failure from system", e);
        }
    }

    public void execStartActivityFromAppTask(android.content.Context context, android.os.IBinder iBinder, android.app.IAppTask iAppTask, android.content.Intent intent, android.os.Bundle bundle) {
        android.app.Instrumentation.ActivityResult activityResult;
        if (DEBUG_START_ACTIVITY) {
            android.util.Log.d(TAG, "startActivity: who=" + context + " intent=" + intent + " options=" + bundle, new java.lang.Throwable());
        }
        java.util.Objects.requireNonNull(intent);
        android.app.IApplicationThread iApplicationThread = (android.app.IApplicationThread) iBinder;
        if (isSdkSandboxAllowedToStartActivities()) {
            adjustIntentForCtsInSdkSandboxInstrumentation(intent);
        }
        if (this.mActivityMonitors != null) {
            synchronized (this.mSync) {
                int size = this.mActivityMonitors.size();
                int i = 0;
                while (true) {
                    if (i >= size) {
                        break;
                    }
                    android.app.Instrumentation.ActivityMonitor activityMonitor = this.mActivityMonitors.get(i);
                    if (!activityMonitor.ignoreMatchingSpecificIntents()) {
                        activityResult = null;
                    } else {
                        if (bundle == null) {
                            bundle = android.app.ActivityOptions.makeBasic().toBundle();
                        }
                        activityResult = activityMonitor.onStartActivity(context, intent, bundle);
                    }
                    if (activityResult != null) {
                        activityMonitor.mHits++;
                        return;
                    } else if (!activityMonitor.match(context, null, intent)) {
                        i++;
                    } else {
                        activityMonitor.mHits++;
                        if (activityMonitor.isBlocking()) {
                            return;
                        }
                    }
                }
            }
        }
        try {
            intent.migrateExtraStreamToClipData(context);
            intent.prepareToLeaveProcess(context);
            int startActivity = iAppTask.startActivity(iApplicationThread.asBinder(), context.getOpPackageName(), context.getAttributionTag(), intent, intent.resolveTypeIfNeeded(context.getContentResolver()), bundle);
            notifyStartActivityResult(startActivity, bundle);
            checkStartActivityResult(startActivity, intent);
        } catch (android.os.RemoteException e) {
            throw new java.lang.RuntimeException("Failure from system", e);
        }
    }

    final void init(android.app.ActivityThread activityThread, android.content.Context context, android.content.Context context2, android.content.ComponentName componentName, android.app.IInstrumentationWatcher iInstrumentationWatcher, android.app.IUiAutomationConnection iUiAutomationConnection) {
        this.mThread = activityThread;
        this.mThread.getLooper();
        this.mMessageQueue = android.os.Looper.myQueue();
        this.mInstrContext = context;
        this.mAppContext = context2;
        this.mComponent = componentName;
        this.mWatcher = iInstrumentationWatcher;
        this.mUiAutomationConnection = iUiAutomationConnection;
    }

    final void basicInit(android.app.ActivityThread activityThread) {
        this.mThread = activityThread;
    }

    public final void basicInit(android.content.Context context) {
        this.mInstrContext = context;
        this.mAppContext = context;
    }

    public static void checkStartActivityResult(int i, java.lang.Object obj) {
        if (!android.app.ActivityManager.isStartResultFatalError(i)) {
            return;
        }
        switch (i) {
            case -100:
                throw new java.lang.IllegalStateException("Cannot start voice activity on a hidden session");
            case android.app.ActivityManager.START_VOICE_NOT_ACTIVE_SESSION /* -99 */:
                throw new java.lang.IllegalStateException("Session calling startVoiceActivity does not match active session");
            case android.app.ActivityManager.START_NOT_CURRENT_USER_ACTIVITY /* -98 */:
            default:
                throw new android.util.AndroidRuntimeException("Unknown error code " + i + " when starting " + obj);
            case android.app.ActivityManager.START_NOT_VOICE_COMPATIBLE /* -97 */:
                throw new java.lang.SecurityException("Starting under voice control not allowed for: " + obj);
            case android.app.ActivityManager.START_CANCELED /* -96 */:
                throw new android.util.AndroidRuntimeException("Activity could not be started for " + obj);
            case android.app.ActivityManager.START_NOT_ACTIVITY /* -95 */:
                throw new java.lang.IllegalArgumentException("PendingIntent is not an activity");
            case android.app.ActivityManager.START_PERMISSION_DENIED /* -94 */:
                throw new java.lang.SecurityException("Not allowed to start activity " + obj);
            case android.app.ActivityManager.START_FORWARD_AND_REQUEST_CONFLICT /* -93 */:
                throw new android.util.AndroidRuntimeException("FORWARD_RESULT_FLAG used while also requesting a result");
            case android.app.ActivityManager.START_CLASS_NOT_FOUND /* -92 */:
            case android.app.ActivityManager.START_INTENT_NOT_RESOLVED /* -91 */:
                if (obj instanceof android.content.Intent) {
                    android.content.Intent intent = (android.content.Intent) obj;
                    if (intent.getComponent() != null) {
                        throw new android.content.ActivityNotFoundException("Unable to find explicit activity class " + intent.getComponent().toShortString() + "; have you declared this activity in your AndroidManifest.xml, or does your intent not match its declared <intent-filter>?");
                    }
                }
                throw new android.content.ActivityNotFoundException("No Activity found to handle " + obj);
            case android.app.ActivityManager.START_ASSISTANT_HIDDEN_SESSION /* -90 */:
                throw new java.lang.IllegalStateException("Cannot start assistant activity on a hidden session");
            case android.app.ActivityManager.START_ASSISTANT_NOT_ACTIVE_SESSION /* -89 */:
                throw new java.lang.IllegalStateException("Session calling startAssistantActivity does not match active session");
        }
    }

    private final void validateNotAppThread() {
        if (android.os.Looper.myLooper() == android.os.Looper.getMainLooper()) {
            throw new java.lang.RuntimeException("This method can not be called from the main application thread");
        }
    }

    public android.app.UiAutomation getUiAutomation() {
        return getUiAutomation(0);
    }

    public android.app.UiAutomation getUiAutomation(int i) {
        boolean z = this.mUiAutomation == null || this.mUiAutomation.isDestroyed();
        if (this.mUiAutomationConnection != null) {
            if (!z && this.mUiAutomation.getFlags() == i) {
                return this.mUiAutomation;
            }
            if (z) {
                this.mUiAutomation = new android.app.UiAutomation(getTargetContext(), this.mUiAutomationConnection);
            } else {
                this.mUiAutomation.disconnect();
            }
            if (getTargetContext().getApplicationInfo().targetSdkVersion <= 30) {
                this.mUiAutomation.connect(i);
                return this.mUiAutomation;
            }
            long uptimeMillis = android.os.SystemClock.uptimeMillis();
            try {
                this.mUiAutomation.connectWithTimeout(i, 60000L);
                return this.mUiAutomation;
            } catch (java.util.concurrent.TimeoutException e) {
                android.util.Log.e(TAG, "Unable to connect to UiAutomation. Waited for " + (android.os.SystemClock.uptimeMillis() - uptimeMillis) + " ms", e);
                this.mUiAutomation.destroy();
                this.mUiAutomation = null;
            }
        }
        return null;
    }

    public android.os.TestLooperManager acquireLooperManager(android.os.Looper looper) {
        checkInstrumenting("acquireLooperManager");
        return new android.os.TestLooperManager(looper);
    }

    private final class InstrumentationThread extends java.lang.Thread {
        public InstrumentationThread(java.lang.String str) {
            super(str);
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
            try {
                android.os.Process.setThreadPriority(-8);
            } catch (java.lang.RuntimeException e) {
                android.util.Log.w(android.app.Instrumentation.TAG, "Exception setting priority of instrumentation thread " + android.os.Process.myTid(), e);
            }
            if (android.app.Instrumentation.this.mAutomaticPerformanceSnapshots) {
                android.app.Instrumentation.this.startPerformanceSnapshot();
            }
            android.app.Instrumentation.this.onStart();
        }
    }

    private static final class EmptyRunnable implements java.lang.Runnable {
        private EmptyRunnable() {
        }

        @Override // java.lang.Runnable
        public void run() {
        }
    }

    private static final class SyncRunnable implements java.lang.Runnable {
        private boolean mComplete;
        private final java.lang.Runnable mTarget;

        public SyncRunnable(java.lang.Runnable runnable) {
            this.mTarget = runnable;
        }

        @Override // java.lang.Runnable
        public void run() {
            this.mTarget.run();
            synchronized (this) {
                this.mComplete = true;
                notifyAll();
            }
        }

        public void waitForComplete() {
            synchronized (this) {
                while (!this.mComplete) {
                    try {
                        wait();
                    } catch (java.lang.InterruptedException e) {
                    }
                }
            }
        }
    }

    private static final class ActivityWaiter {
        public android.app.Activity activity;
        public final android.content.Intent intent;

        public ActivityWaiter(android.content.Intent intent) {
            this.intent = intent;
        }
    }

    private final class ActivityGoing implements android.os.MessageQueue.IdleHandler {
        private final android.app.Instrumentation.ActivityWaiter mWaiter;

        public ActivityGoing(android.app.Instrumentation.ActivityWaiter activityWaiter) {
            this.mWaiter = activityWaiter;
        }

        @Override // android.os.MessageQueue.IdleHandler
        public final boolean queueIdle() {
            synchronized (android.app.Instrumentation.this.mSync) {
                android.app.Instrumentation.this.mWaitingActivities.remove(this.mWaiter);
                android.app.Instrumentation.this.mSync.notifyAll();
            }
            return false;
        }
    }

    private static final class Idler implements android.os.MessageQueue.IdleHandler {
        private final java.lang.Runnable mCallback;
        private boolean mIdle = false;

        public Idler(java.lang.Runnable runnable) {
            this.mCallback = runnable;
        }

        @Override // android.os.MessageQueue.IdleHandler
        public final boolean queueIdle() {
            if (this.mCallback != null) {
                this.mCallback.run();
            }
            synchronized (this) {
                this.mIdle = true;
                notifyAll();
            }
            return false;
        }

        public void waitForIdle() {
            synchronized (this) {
                while (!this.mIdle) {
                    try {
                        wait();
                    } catch (java.lang.InterruptedException e) {
                    }
                }
            }
        }
    }
}
