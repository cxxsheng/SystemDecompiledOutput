package android.app;

/* loaded from: classes.dex */
public abstract class Service extends android.content.ContextWrapper implements android.content.ComponentCallbacks2, android.view.contentcapture.ContentCaptureManager.ContentCaptureClient {
    public static final int START_CONTINUATION_MASK = 15;
    public static final int START_FLAG_REDELIVERY = 1;
    public static final int START_FLAG_RETRY = 2;
    public static final int START_NOT_STICKY = 2;
    public static final int START_REDELIVER_INTENT = 3;
    public static final int START_STICKY = 1;
    public static final int START_STICKY_COMPATIBILITY = 0;
    public static final int START_TASK_REMOVED_COMPLETE = 1000;
    public static final int STOP_FOREGROUND_DETACH = 2;

    @java.lang.Deprecated
    public static final int STOP_FOREGROUND_LEGACY = 0;
    public static final int STOP_FOREGROUND_REMOVE = 1;
    private static final java.lang.String TAG = "Service";
    private static final java.lang.String TRACE_TRACK_NAME_FOREGROUND_SERVICE = "FGS";
    private static final android.util.ArrayMap<java.lang.String, android.app.StackTrace> sStartForegroundServiceStackTraces = new android.util.ArrayMap<>();
    private android.app.IActivityManager mActivityManager;
    private android.app.Application mApplication;
    private java.lang.String mClassName;
    private java.lang.String mForegroundServiceTraceTitle;
    private final java.lang.Object mForegroundServiceTraceTitleLock;
    private boolean mStartCompatibility;
    private android.app.ActivityThread mThread;
    private android.os.IBinder mToken;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface StartArgFlags {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface StartResult {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface StopForegroundSelector {
    }

    public abstract android.os.IBinder onBind(android.content.Intent intent);

    public Service() {
        super(null);
        this.mThread = null;
        this.mClassName = null;
        this.mToken = null;
        this.mApplication = null;
        this.mActivityManager = null;
        this.mStartCompatibility = false;
        this.mForegroundServiceTraceTitle = null;
        this.mForegroundServiceTraceTitleLock = new java.lang.Object();
    }

    public final android.app.Application getApplication() {
        return this.mApplication;
    }

    public void onCreate() {
    }

    @java.lang.Deprecated
    public void onStart(android.content.Intent intent, int i) {
    }

    public int onStartCommand(android.content.Intent intent, int i, int i2) {
        onStart(intent, i2);
        return !this.mStartCompatibility ? 1 : 0;
    }

    public void onDestroy() {
    }

    @Override // android.content.ComponentCallbacks
    public void onConfigurationChanged(android.content.res.Configuration configuration) {
    }

    @Override // android.content.ComponentCallbacks
    public void onLowMemory() {
    }

    @Override // android.content.ComponentCallbacks2
    public void onTrimMemory(int i) {
    }

    public boolean onUnbind(android.content.Intent intent) {
        return false;
    }

    public void onRebind(android.content.Intent intent) {
    }

    public void onTaskRemoved(android.content.Intent intent) {
    }

    public final void stopSelf() {
        stopSelf(-1);
    }

    public final void stopSelf(int i) {
        if (this.mActivityManager == null) {
            return;
        }
        try {
            this.mActivityManager.stopServiceToken(new android.content.ComponentName(this, this.mClassName), this.mToken, i);
        } catch (android.os.RemoteException e) {
        }
    }

    public final boolean stopSelfResult(int i) {
        if (this.mActivityManager == null) {
            return false;
        }
        try {
            return this.mActivityManager.stopServiceToken(new android.content.ComponentName(this, this.mClassName), this.mToken, i);
        } catch (android.os.RemoteException e) {
            return false;
        }
    }

    @java.lang.Deprecated
    public final void setForeground(boolean z) {
        android.util.Log.w(TAG, "setForeground: ignoring old API call on " + getClass().getName());
    }

    public final void startForeground(int i, android.app.Notification notification) {
        try {
            android.content.ComponentName componentName = new android.content.ComponentName(this, this.mClassName);
            this.mActivityManager.setServiceForeground(componentName, this.mToken, i, notification, 0, -1);
            clearStartForegroundServiceStackTrace();
            logForegroundServiceStart(componentName, -1);
        } catch (android.os.RemoteException e) {
        }
    }

    public final void startForeground(int i, android.app.Notification notification, int i2) {
        try {
            android.content.ComponentName componentName = new android.content.ComponentName(this, this.mClassName);
            this.mActivityManager.setServiceForeground(componentName, this.mToken, i, notification, 0, i2);
            clearStartForegroundServiceStackTrace();
            logForegroundServiceStart(componentName, i2);
        } catch (android.os.RemoteException e) {
        }
    }

    @java.lang.Deprecated
    public final void stopForeground(boolean z) {
        stopForeground(z ? 1 : 0);
    }

    public final void stopForeground(int i) {
        try {
            this.mActivityManager.setServiceForeground(new android.content.ComponentName(this, this.mClassName), this.mToken, 0, null, i, 0);
            logForegroundServiceStopIfNecessary();
        } catch (android.os.RemoteException e) {
        }
    }

    public final int getForegroundServiceType() {
        try {
            return this.mActivityManager.getForegroundServiceType(new android.content.ComponentName(this, this.mClassName), this.mToken);
        } catch (android.os.RemoteException e) {
            return 0;
        }
    }

    protected void dump(java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, java.lang.String[] strArr) {
        printWriter.println("nothing to dump");
    }

    @Override // android.content.ContextWrapper
    protected void attachBaseContext(android.content.Context context) {
        super.attachBaseContext(context);
        if (context != null) {
            context.setContentCaptureOptions(getContentCaptureOptions());
        }
    }

    public final void attach(android.content.Context context, android.app.ActivityThread activityThread, java.lang.String str, android.os.IBinder iBinder, android.app.Application application, java.lang.Object obj) {
        attachBaseContext(context);
        this.mThread = activityThread;
        this.mClassName = str;
        this.mToken = iBinder;
        this.mApplication = application;
        this.mActivityManager = (android.app.IActivityManager) obj;
        this.mStartCompatibility = getApplicationInfo().targetSdkVersion < 5;
        setContentCaptureOptions(application.getContentCaptureOptions());
    }

    public android.content.Context createServiceBaseContext(android.app.ActivityThread activityThread, android.app.LoadedApk loadedApk) {
        return android.app.ContextImpl.createAppContext(activityThread, loadedApk);
    }

    public final void detachAndCleanUp() {
        this.mToken = null;
        logForegroundServiceStopIfNecessary();
    }

    final java.lang.String getClassName() {
        return this.mClassName;
    }

    @Override // android.content.Context
    public final android.view.contentcapture.ContentCaptureManager.ContentCaptureClient getContentCaptureClient() {
        return this;
    }

    @Override // android.view.contentcapture.ContentCaptureManager.ContentCaptureClient
    public final android.content.ComponentName contentCaptureClientGetComponentName() {
        return new android.content.ComponentName(this, this.mClassName);
    }

    private void logForegroundServiceStart(android.content.ComponentName componentName, int i) {
        synchronized (this.mForegroundServiceTraceTitleLock) {
            if (this.mForegroundServiceTraceTitle == null) {
                this.mForegroundServiceTraceTitle = android.text.TextUtils.formatSimple("comp=%s type=%s", componentName.toShortString(), java.lang.Integer.toHexString(i));
                android.os.Trace.asyncTraceForTrackBegin(64L, TRACE_TRACK_NAME_FOREGROUND_SERVICE, this.mForegroundServiceTraceTitle, java.lang.System.identityHashCode(this));
            } else {
                android.os.Trace.instantForTrack(64L, TRACE_TRACK_NAME_FOREGROUND_SERVICE, this.mForegroundServiceTraceTitle);
            }
        }
    }

    private void logForegroundServiceStopIfNecessary() {
        synchronized (this.mForegroundServiceTraceTitleLock) {
            if (this.mForegroundServiceTraceTitle != null) {
                android.os.Trace.asyncTraceForTrackEnd(64L, TRACE_TRACK_NAME_FOREGROUND_SERVICE, java.lang.System.identityHashCode(this));
                this.mForegroundServiceTraceTitle = null;
            }
        }
    }

    public static void setStartForegroundServiceStackTrace(java.lang.String str, android.app.StackTrace stackTrace) {
        synchronized (sStartForegroundServiceStackTraces) {
            sStartForegroundServiceStackTraces.put(str, stackTrace);
        }
    }

    private void clearStartForegroundServiceStackTrace() {
        synchronized (sStartForegroundServiceStackTraces) {
            sStartForegroundServiceStackTraces.remove(getClassName());
        }
    }

    public static android.app.StackTrace getStartForegroundServiceStackTrace(java.lang.String str) {
        android.app.StackTrace stackTrace;
        synchronized (sStartForegroundServiceStackTraces) {
            stackTrace = sStartForegroundServiceStackTraces.get(str);
        }
        return stackTrace;
    }

    public final void callOnTimeout(int i) {
        if (this.mToken == null) {
            android.util.Log.w(TAG, "Service already destroyed, skipping onTimeout()");
            return;
        }
        try {
            if (!this.mActivityManager.shouldServiceTimeOut(new android.content.ComponentName(this, this.mClassName), this.mToken)) {
                android.util.Log.w(TAG, "Service no longer relevant, skipping onTimeout()");
                return;
            }
        } catch (android.os.RemoteException e) {
        }
        onTimeout(i);
    }

    public void onTimeout(int i) {
    }

    public final void callOnTimeLimitExceeded(int i, int i2) {
        if (this.mToken == null) {
            android.util.Log.w(TAG, "Service already destroyed, skipping onTimeLimitExceeded()");
            return;
        }
        try {
            if (!this.mActivityManager.hasServiceTimeLimitExceeded(new android.content.ComponentName(this, this.mClassName), this.mToken)) {
                android.util.Log.w(TAG, "Service no longer relevant, skipping onTimeLimitExceeded()");
                return;
            }
        } catch (android.os.RemoteException e) {
        }
        if (android.app.Flags.introduceNewServiceOntimeoutCallback()) {
            onTimeout(i, i2);
        }
    }

    public void onTimeout(int i, int i2) {
    }
}
