package android.app;

/* loaded from: classes.dex */
public final class ActivityThread extends android.app.ClientTransactionHandler implements android.app.ActivityThreadInternal {
    private static final int ACTIVITY_THREAD_CHECKIN_VERSION = 4;
    private static final long CONTENT_PROVIDER_RETAIN_TIME = 1000;
    private static final boolean DEBUG_APP_INFO = false;
    private static final boolean DEBUG_BACKUP = false;
    public static final boolean DEBUG_BROADCAST = false;
    public static final boolean DEBUG_CONFIGURATION = false;
    public static final boolean DEBUG_MEMORY_TRIM = false;
    static final boolean DEBUG_MESSAGES = false;
    public static final boolean DEBUG_ORDER = false;
    private static final boolean DEBUG_PROVIDER = false;
    private static final boolean DEBUG_RESULTS = false;
    private static final boolean DEBUG_SERVICE = false;
    private static final java.lang.String DEFAULT_FULL_BACKUP_AGENT = "android.app.backup.FullBackupAgent";
    private static final java.lang.String HEAP_COLUMN = "%13s %8s %8s %8s %8s %8s %8s %8s %8s";
    private static final java.lang.String HEAP_FULL_COLUMN = "%13s %8s %8s %8s %8s %8s %8s %8s %8s %8s %8s %8s";
    public static final long INVALID_PROC_STATE_SEQ = -1;
    private static final long MIN_TIME_BETWEEN_GCS = 5000;
    private static final java.lang.String ONE_ALT_COUNT_COLUMN = "%21s %8s %21s %8d";
    private static final java.lang.String ONE_COUNT_COLUMN = "%21s %8d";
    public static final java.lang.String PROC_START_SEQ_IDENT = "seq=";
    private static final int REQUEST_DIRECT_ACTIONS_RETRY_MAX_COUNT = 7;
    private static final long REQUEST_DIRECT_ACTIONS_RETRY_TIME_MS = 200;
    public static final int SERVICE_DONE_EXECUTING_ANON = 0;
    public static final int SERVICE_DONE_EXECUTING_REBIND = 3;
    public static final int SERVICE_DONE_EXECUTING_START = 1;
    public static final int SERVICE_DONE_EXECUTING_STOP = 2;
    public static final int SERVICE_DONE_EXECUTING_UNBIND = 4;
    private static final int SQLITE_MEM_RELEASED_EVENT_LOG_TAG = 75003;
    public static final java.lang.String TAG = "ActivityThread";
    private static final java.lang.String THREE_COUNT_COLUMNS = "%21s %8d %21s %8d %21s %8d";
    private static final java.lang.String TWO_COUNT_COLUMNS = "%21s %8d %21s %8d";
    private static final java.lang.String TWO_COUNT_COLUMN_HEADER = "%21s %8s %21s %8s";
    private static final int VM_PROCESS_STATE_JANK_IMPERCEPTIBLE = 1;
    private static final int VM_PROCESS_STATE_JANK_PERCEPTIBLE = 0;
    static final boolean localLOGV = false;
    private static volatile android.app.ActivityThread sCurrentActivityThread;
    private static final java.lang.ThreadLocal<android.content.Intent> sCurrentBroadcastIntent = new java.lang.ThreadLocal<>();
    static volatile android.os.Handler sMainThreadHandler;
    static volatile android.content.pm.IPackageManager sPackageManager;
    private static volatile android.permission.IPermissionManager sPermissionManager;
    android.app.ActivityThread.AppBindData mBoundApplication;
    private android.content.res.CompatibilityInfo mCompatibilityInfo;
    android.content.res.Configuration mConfiguration;
    private android.app.ConfigurationController mConfigurationController;
    int mCurDefaultDisplayDpi;
    boolean mDensityCompatMode;
    private android.util.SparseArray<android.app.ContextImpl> mDisplaySystemUiContexts;
    android.app.Application mInitialApplication;
    android.app.Instrumentation mInstrumentation;
    boolean mInstrumentingWithoutRestart;
    private int mLastSessionId;
    android.app.ActivityThread.Profiler mProfiler;
    private java.util.Map<android.app.ActivityThread.SafeCancellationTransport, android.os.CancellationSignal> mRemoteCancellations;
    private android.window.SplashScreen.SplashScreenManagerGlobal mSplashScreenGlobal;
    private long mStartSeq;
    private android.app.ContextImpl mSystemContext;
    private final android.os.DdmSyncStageUpdater mDdmSyncStageUpdater = new android.os.DdmSyncStageUpdater();
    private final java.lang.Object mNetworkPolicyLock = new java.lang.Object();
    private long mNetworkBlockSeq = -1;
    final android.app.ActivityThread.ApplicationThread mAppThread = new android.app.ActivityThread.ApplicationThread();
    final android.os.Looper mLooper = android.os.Looper.myLooper();
    final android.app.ActivityThread.H mH = new android.app.ActivityThread.H();
    final java.util.concurrent.Executor mExecutor = new android.os.HandlerExecutor(this.mH);
    final android.util.ArrayMap<android.os.IBinder, android.app.ActivityThread.ActivityClientRecord> mActivities = new android.util.ArrayMap<>();
    private final android.util.ArrayMap<android.os.IBinder, android.content.res.Configuration> mPendingOverrideConfigs = new android.util.ArrayMap<>();
    private final android.util.ArrayMap<java.lang.String, android.content.pm.ApplicationInfo> mPendingAppInfoUpdates = new android.util.ArrayMap<>();
    final java.util.Map<android.os.IBinder, android.app.servertransaction.DestroyActivityItem> mActivitiesToBeDestroyed = java.util.Collections.synchronizedMap(new android.util.ArrayMap());
    final java.util.ArrayList<android.app.ActivityThread.ActivityClientRecord> mNewActivities = new java.util.ArrayList<>();
    int mNumVisibleActivities = 0;
    private final java.util.concurrent.atomic.AtomicInteger mNumLaunchingActivities = new java.util.concurrent.atomic.AtomicInteger();
    private int mLastProcessState = -1;
    final java.util.ArrayList<java.lang.ref.WeakReference<android.app.assist.AssistStructure>> mLastAssistStructures = new java.util.ArrayList<>();
    private final android.app.ConfigurationChangedListenerController mConfigurationChangedListenerController = new android.app.ConfigurationChangedListenerController();
    int mLastReportedDeviceId = 0;
    final android.util.ArrayMap<android.os.IBinder, android.app.ActivityThread.CreateServiceData> mServicesData = new android.util.ArrayMap<>();
    final android.util.ArrayMap<android.os.IBinder, android.app.Service> mServices = new android.util.ArrayMap<>();
    private boolean mUpdateHttpProxyOnBind = false;
    final java.util.ArrayList<android.app.Application> mAllApplications = new java.util.ArrayList<>();
    private final android.util.SparseArray<android.util.ArrayMap<java.lang.String, android.app.backup.BackupAgent>> mBackupAgentsByUser = new android.util.SparseArray<>();
    java.lang.String mInstrumentationPackageName = null;
    java.lang.String mInstrumentationAppDir = null;
    java.lang.String[] mInstrumentationSplitAppDirs = null;
    java.lang.String mInstrumentationLibDir = null;
    java.lang.String mInstrumentedAppDir = null;
    java.lang.String[] mInstrumentedSplitAppDirs = null;
    java.lang.String mInstrumentedLibDir = null;
    boolean mSystemThread = false;
    boolean mSomeActivitiesChanged = false;
    final android.util.ArrayMap<java.lang.String, java.lang.ref.WeakReference<android.app.LoadedApk>> mPackages = new android.util.ArrayMap<>();
    final android.util.ArrayMap<java.lang.String, java.lang.ref.WeakReference<android.app.LoadedApk>> mResourcePackages = new android.util.ArrayMap<>();
    final java.util.ArrayList<android.app.ActivityThread.ActivityClientRecord> mRelaunchingActivities = new java.util.ArrayList<>();
    android.content.res.Configuration mPendingConfiguration = null;
    private final android.app.servertransaction.TransactionExecutor mTransactionExecutor = new android.app.servertransaction.TransactionExecutor(this);
    final android.util.ArrayMap<android.app.ActivityThread.ProviderKey, android.app.ActivityThread.ProviderClientRecord> mProviderMap = new android.util.ArrayMap<>();
    final android.util.ArrayMap<android.os.IBinder, android.app.ActivityThread.ProviderRefCount> mProviderRefCountMap = new android.util.ArrayMap<>();
    final android.util.ArrayMap<android.os.IBinder, android.app.ActivityThread.ProviderClientRecord> mLocalProviders = new android.util.ArrayMap<>();
    final android.util.ArrayMap<android.content.ComponentName, android.app.ActivityThread.ProviderClientRecord> mLocalProvidersByName = new android.util.ArrayMap<>();
    final android.util.ArrayMap<android.app.ActivityThread.ProviderKey, android.app.ActivityThread.ProviderKey> mGetProviderKeys = new android.util.ArrayMap<>();
    final android.util.ArrayMap<android.app.Activity, java.util.ArrayList<android.app.OnActivityPausedListener>> mOnPauseListeners = new android.util.ArrayMap<>();
    final android.app.ActivityThread.GcIdler mGcIdler = new android.app.ActivityThread.GcIdler();
    final android.app.ActivityThread.PurgeIdler mPurgeIdler = new android.app.ActivityThread.PurgeIdler();
    boolean mPurgeIdlerScheduled = false;
    boolean mGcIdlerScheduled = false;
    android.os.Bundle mCoreSettings = null;
    private final java.lang.Object mCoreSettingsLock = new java.lang.Object();
    private android.view.contentcapture.IContentCaptureOptionsCallback.Stub mContentCaptureOptionsCallback = null;
    private final android.app.ResourcesManager mResourcesManager = android.app.ResourcesManager.getInstance();

    private native void nInitZygoteChildHeapProfiling();

    private native void nPurgePendingResources();

    /* JADX INFO: Access modifiers changed from: private */
    static final class ProviderKey {
        final java.lang.String authority;
        android.app.ContentProviderHolder mHolder;
        final java.lang.Object mLock = new java.lang.Object();
        final int userId;

        public ProviderKey(java.lang.String str, int i) {
            this.authority = str;
            this.userId = i;
        }

        public boolean equals(java.lang.Object obj) {
            if (!(obj instanceof android.app.ActivityThread.ProviderKey)) {
                return false;
            }
            android.app.ActivityThread.ProviderKey providerKey = (android.app.ActivityThread.ProviderKey) obj;
            return java.util.Objects.equals(this.authority, providerKey.authority) && this.userId == providerKey.userId;
        }

        public int hashCode() {
            return (this.authority != null ? this.authority.hashCode() : 0) ^ this.userId;
        }
    }

    public static final class ActivityClientRecord {
        android.app.Activity activity;
        android.view.ViewRootImpl.ActivityConfigCallback activityConfigCallback;
        android.content.pm.ActivityInfo activityInfo;
        public android.os.IBinder assistToken;
        android.content.res.CompatibilityInfo compatInfo;
        android.content.res.Configuration createdConfig;
        java.lang.String embeddedID;
        boolean hideForNow;
        int ident;
        public android.os.IBinder initialCallerInfoAccessToken;
        android.content.Intent intent;
        public final boolean isForward;
        boolean isTopResumedActivity;
        android.app.Activity.NonConfigurationInstances lastNonConfigurationInstances;
        boolean lastReportedTopResumedState;
        private android.window.ActivityWindowInfo mActivityWindowInfo;
        boolean mIsUserLeaving;
        int mLastReportedWindowingMode;
        boolean mLaunchedFromBubble;
        private int mLifecycleState;
        android.view.Window mPendingRemoveWindow;
        android.view.WindowManager mPendingRemoveWindowManager;
        boolean mPreserveWindow;
        android.app.ActivityOptions.SceneTransitionInfo mSceneTransitionInfo;
        private android.window.SizeConfigurationBuckets mSizeConfigurations;
        public android.os.IBinder mTaskFragmentToken;
        android.content.res.Configuration overrideConfig;
        public android.app.LoadedApk packageInfo;
        android.app.Activity parent;
        boolean paused;
        int pendingConfigChanges;
        java.util.List<com.android.internal.content.ReferrerIntent> pendingIntents;
        java.util.List<android.app.ResultInfo> pendingResults;
        android.os.PersistableBundle persistentState;
        android.app.ProfilerInfo profilerInfo;
        java.lang.String referrer;
        public android.os.IBinder shareableActivityToken;
        boolean startsNotResumed;
        android.os.Bundle state;
        boolean stopped;
        private final android.content.res.Configuration tmpConfig;
        public android.os.IBinder token;
        com.android.internal.app.IVoiceInteractor voiceInteractor;
        android.view.Window window;

        public ActivityClientRecord() {
            this.tmpConfig = new android.content.res.Configuration();
            this.mLastReportedWindowingMode = 0;
            this.mLifecycleState = 0;
            this.isForward = false;
            init();
        }

        public ActivityClientRecord(android.os.IBinder iBinder, android.content.Intent intent, int i, android.content.pm.ActivityInfo activityInfo, android.content.res.Configuration configuration, java.lang.String str, com.android.internal.app.IVoiceInteractor iVoiceInteractor, android.os.Bundle bundle, android.os.PersistableBundle persistableBundle, java.util.List<android.app.ResultInfo> list, java.util.List<com.android.internal.content.ReferrerIntent> list2, android.app.ActivityOptions.SceneTransitionInfo sceneTransitionInfo, boolean z, android.app.ProfilerInfo profilerInfo, android.app.ClientTransactionHandler clientTransactionHandler, android.os.IBinder iBinder2, android.os.IBinder iBinder3, boolean z2, android.os.IBinder iBinder4, android.os.IBinder iBinder5, android.window.ActivityWindowInfo activityWindowInfo) {
            this.tmpConfig = new android.content.res.Configuration();
            this.mLastReportedWindowingMode = 0;
            this.mLifecycleState = 0;
            this.token = iBinder;
            this.assistToken = iBinder2;
            this.shareableActivityToken = iBinder3;
            this.ident = i;
            this.intent = intent;
            this.referrer = str;
            this.voiceInteractor = iVoiceInteractor;
            this.activityInfo = activityInfo;
            this.state = bundle;
            this.persistentState = persistableBundle;
            this.pendingResults = list;
            this.pendingIntents = list2;
            this.isForward = z;
            this.profilerInfo = profilerInfo;
            this.overrideConfig = configuration;
            this.packageInfo = clientTransactionHandler.getPackageInfoNoCheck(this.activityInfo.applicationInfo);
            this.initialCallerInfoAccessToken = iBinder5;
            this.mSceneTransitionInfo = sceneTransitionInfo;
            this.mLaunchedFromBubble = z2;
            this.mTaskFragmentToken = iBinder4;
            this.mActivityWindowInfo = activityWindowInfo;
            init();
        }

        private void init() {
            this.parent = null;
            this.embeddedID = null;
            this.paused = false;
            this.stopped = false;
            this.hideForNow = false;
            this.activityConfigCallback = new android.view.ViewRootImpl.ActivityConfigCallback() { // from class: android.app.ActivityThread.ActivityClientRecord.1
                @Override // android.view.ViewRootImpl.ActivityConfigCallback
                public void onConfigurationChanged(android.content.res.Configuration configuration, int i) {
                    if (android.app.ActivityThread.ActivityClientRecord.this.activity == null) {
                        throw new java.lang.IllegalStateException("Received config update for non-existing activity");
                    }
                    android.app.ActivityThread.ActivityClientRecord.this.activity.mMainThread.handleActivityConfigurationChanged(android.app.ActivityThread.ActivityClientRecord.this, configuration, i, android.app.ActivityThread.ActivityClientRecord.this.mActivityWindowInfo, false);
                }

                @Override // android.view.ViewRootImpl.ActivityConfigCallback
                public void requestCompatCameraControl(boolean z, boolean z2, android.app.ICompatCameraControlCallback iCompatCameraControlCallback) {
                    if (android.app.ActivityThread.ActivityClientRecord.this.activity == null) {
                        throw new java.lang.IllegalStateException("Received camera compat control update for non-existing activity");
                    }
                    android.app.ActivityClient.getInstance().requestCompatCameraControl(android.app.ActivityThread.ActivityClientRecord.this.activity.getResources(), android.app.ActivityThread.ActivityClientRecord.this.token, z, z2, iCompatCameraControlCallback);
                }
            };
        }

        public int getLifecycleState() {
            return this.mLifecycleState;
        }

        public void setState(int i) {
            this.mLifecycleState = i;
            switch (this.mLifecycleState) {
                case 1:
                    this.paused = true;
                    this.stopped = true;
                    break;
                case 2:
                    this.paused = true;
                    this.stopped = false;
                    break;
                case 3:
                    this.paused = false;
                    this.stopped = false;
                    break;
                case 4:
                    this.paused = true;
                    this.stopped = false;
                    break;
                case 5:
                    this.paused = true;
                    this.stopped = true;
                    break;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public boolean isPreHoneycomb() {
            return this.activity != null && this.activity.getApplicationInfo().targetSdkVersion < 11;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public boolean isPreP() {
            return this.activity != null && this.activity.getApplicationInfo().targetSdkVersion < 28;
        }

        public boolean isPersistable() {
            return this.activityInfo.persistableMode == 2;
        }

        public boolean isVisibleFromServer() {
            return this.activity != null && this.activity.mVisibleFromServer;
        }

        public android.window.ActivityWindowInfo getActivityWindowInfo() {
            return this.mActivityWindowInfo;
        }

        public java.lang.String toString() {
            android.content.ComponentName component = this.intent != null ? this.intent.getComponent() : null;
            return "ActivityRecord{" + java.lang.Integer.toHexString(java.lang.System.identityHashCode(this)) + " token=" + this.token + " " + (component == null ? "no component name" : component.toShortString()) + "}";
        }

        public java.lang.String getStateString() {
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            sb.append("ActivityClientRecord{");
            sb.append("paused=").append(this.paused);
            sb.append(", stopped=").append(this.stopped);
            sb.append(", hideForNow=").append(this.hideForNow);
            sb.append(", startsNotResumed=").append(this.startsNotResumed);
            sb.append(", isForward=").append(this.isForward);
            sb.append(", pendingConfigChanges=").append(this.pendingConfigChanges);
            sb.append(", preserveWindow=").append(this.mPreserveWindow);
            if (this.activity != null) {
                sb.append(", Activity{");
                sb.append("resumed=").append(this.activity.mResumed);
                sb.append(", stopped=").append(this.activity.mStopped);
                sb.append(", finished=").append(this.activity.isFinishing());
                sb.append(", destroyed=").append(this.activity.isDestroyed());
                sb.append(", startedActivity=").append(this.activity.mStartedActivity);
                sb.append(", changingConfigurations=").append(this.activity.mChangingConfigurations);
                sb.append("}");
            }
            sb.append("}");
            return sb.toString();
        }
    }

    static final class ProviderClientRecord {
        final android.app.ContentProviderHolder mHolder;
        final android.content.ContentProvider mLocalProvider;
        final java.lang.String[] mNames;
        final android.content.IContentProvider mProvider;

        ProviderClientRecord(java.lang.String[] strArr, android.content.IContentProvider iContentProvider, android.content.ContentProvider contentProvider, android.app.ContentProviderHolder contentProviderHolder) {
            this.mNames = strArr;
            this.mProvider = iContentProvider;
            this.mLocalProvider = contentProvider;
            this.mHolder = contentProviderHolder;
        }
    }

    static final class ReceiverData extends android.content.BroadcastReceiver.PendingResult {
        android.content.res.CompatibilityInfo compatInfo;
        android.content.pm.ActivityInfo info;
        final android.content.Intent intent;

        public ReceiverData(android.content.Intent intent, int i, java.lang.String str, android.os.Bundle bundle, boolean z, boolean z2, boolean z3, android.os.IBinder iBinder, int i2, int i3, java.lang.String str2) {
            super(i, str, bundle, 0, z, z2, z3, iBinder, i2, intent.getFlags(), i3, str2);
            this.intent = intent;
        }

        public java.lang.String toString() {
            return "ReceiverData{intent=" + this.intent + " packageName=" + this.info.packageName + " resultCode=" + getResultCode() + " resultData=" + getResultData() + " resultExtras=" + getResultExtras(false) + " sentFromUid=" + getSentFromUid() + " sentFromPackage=" + getSentFromPackage() + "}";
        }
    }

    static final class CreateBackupAgentData {
        android.content.pm.ApplicationInfo appInfo;
        int backupDestination;
        int backupMode;
        int userId;

        CreateBackupAgentData() {
        }

        public java.lang.String toString() {
            return "CreateBackupAgentData{appInfo=" + this.appInfo + " backupAgent=" + this.appInfo.backupAgentName + " mode=" + this.backupMode + " userId=" + this.userId + "}";
        }
    }

    static final class CreateServiceData {
        android.content.res.CompatibilityInfo compatInfo;
        android.content.pm.ServiceInfo info;
        android.content.Intent intent;
        android.os.IBinder token;

        CreateServiceData() {
        }

        public java.lang.String toString() {
            return "CreateServiceData{token=" + this.token + " className=" + this.info.name + " packageName=" + this.info.packageName + " intent=" + this.intent + "}";
        }
    }

    static final class BindServiceData {
        long bindSeq;
        android.content.Intent intent;
        boolean rebind;
        android.os.IBinder token;

        BindServiceData() {
        }

        public java.lang.String toString() {
            return "BindServiceData{token=" + this.token + " intent=" + this.intent + " bindSeq=" + this.bindSeq + "}";
        }
    }

    static final class ServiceArgsData {
        android.content.Intent args;
        int flags;
        int startId;
        boolean taskRemoved;
        android.os.IBinder token;

        ServiceArgsData() {
        }

        public java.lang.String toString() {
            return "ServiceArgsData{token=" + this.token + " startId=" + this.startId + " args=" + this.args + "}";
        }
    }

    static final class AppBindData {
        android.content.pm.ApplicationInfo appInfo;
        android.content.AutofillOptions autofillOptions;
        java.lang.String buildSerial;
        android.content.res.CompatibilityInfo compatInfo;
        android.content.res.Configuration config;
        android.content.ContentCaptureOptions contentCaptureOptions;
        int debugMode;
        long[] disabledCompatChanges;
        boolean enableBinderTracking;
        android.app.LoadedApk info;
        android.app.ProfilerInfo initProfilerInfo;
        android.os.Bundle instrumentationArgs;
        android.content.ComponentName instrumentationName;
        android.app.IUiAutomationConnection instrumentationUiAutomationConnection;
        android.app.IInstrumentationWatcher instrumentationWatcher;
        boolean isSdkInSandbox;
        android.os.SharedMemory mSerializedSystemFontMap;
        boolean persistent;
        java.lang.String processName;
        java.util.List<android.content.pm.ProviderInfo> providers;
        boolean restrictedBackupMode;
        java.lang.String sdkSandboxClientAppPackage;
        java.lang.String sdkSandboxClientAppVolumeUuid;
        long startRequestedElapsedTime;
        long startRequestedUptime;
        boolean trackAllocation;

        AppBindData() {
        }

        public java.lang.String toString() {
            return "AppBindData{appInfo=" + this.appInfo + "}";
        }
    }

    static final class Profiler {
        boolean autoStopProfiler;
        boolean handlingProfiling;
        int mClockType;
        android.os.ParcelFileDescriptor profileFd;
        java.lang.String profileFile;
        boolean profiling;
        int samplingInterval;
        boolean streamingOutput;

        Profiler() {
        }

        public void setProfiler(android.app.ProfilerInfo profilerInfo) {
            android.os.ParcelFileDescriptor parcelFileDescriptor = profilerInfo.profileFd;
            if (this.profiling) {
                if (parcelFileDescriptor != null) {
                    try {
                        parcelFileDescriptor.close();
                        return;
                    } catch (java.io.IOException e) {
                        return;
                    }
                }
                return;
            }
            if (this.profileFd != null) {
                try {
                    this.profileFd.close();
                } catch (java.io.IOException e2) {
                }
            }
            this.profileFile = profilerInfo.profileFile;
            this.profileFd = parcelFileDescriptor;
            this.samplingInterval = profilerInfo.samplingInterval;
            this.autoStopProfiler = profilerInfo.autoStopProfiler;
            this.streamingOutput = profilerInfo.streamingOutput;
            this.mClockType = profilerInfo.clockType;
        }

        public void startProfiling() {
            if (this.profileFd == null || this.profiling) {
                return;
            }
            try {
                dalvik.system.VMDebug.startMethodTracing(this.profileFile, this.profileFd.getFileDescriptor(), android.os.SystemProperties.getInt("debug.traceview-buffer-size-mb", 8) * 1024 * 1024, this.mClockType, this.samplingInterval != 0, this.samplingInterval, this.streamingOutput);
                this.profiling = true;
            } catch (java.lang.RuntimeException e) {
                android.util.Slog.w(android.app.ActivityThread.TAG, "Profiling failed on path " + this.profileFile, e);
                try {
                    this.profileFd.close();
                    this.profileFd = null;
                } catch (java.io.IOException e2) {
                    android.util.Slog.w(android.app.ActivityThread.TAG, "Failure closing profile fd", e2);
                }
            }
        }

        public void stopProfiling() {
            if (this.profiling) {
                this.profiling = false;
                android.os.Debug.stopMethodTracing();
                if (this.profileFd != null) {
                    try {
                        this.profileFd.close();
                    } catch (java.io.IOException e) {
                    }
                }
                this.profileFd = null;
                this.profileFile = null;
            }
        }
    }

    static final class DumpComponentInfo {
        java.lang.String[] args;
        android.os.ParcelFileDescriptor fd;
        java.lang.String prefix;
        android.os.IBinder token;

        DumpComponentInfo() {
        }
    }

    static final class ContextCleanupInfo {
        android.app.ContextImpl context;
        java.lang.String what;
        java.lang.String who;

        ContextCleanupInfo() {
        }
    }

    static final class DumpHeapData {
        android.os.ParcelFileDescriptor fd;
        android.os.RemoteCallback finishCallback;
        public boolean mallocInfo;
        public boolean managed;
        java.lang.String path;
        public boolean runGc;

        DumpHeapData() {
        }
    }

    static final class DumpResourcesData {
        public android.os.ParcelFileDescriptor fd;
        public android.os.RemoteCallback finishCallback;

        DumpResourcesData() {
        }
    }

    static final class UpdateCompatibilityData {
        android.content.res.CompatibilityInfo info;
        java.lang.String pkg;

        UpdateCompatibilityData() {
        }
    }

    static final class RequestAssistContextExtras {
        android.os.IBinder activityToken;
        int flags;
        android.os.IBinder requestToken;
        int requestType;
        int sessionId;

        RequestAssistContextExtras() {
        }
    }

    static final class ReceiverList {
        int index;
        java.util.List<android.app.ReceiverInfo> receivers;

        ReceiverList() {
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX INFO: Access modifiers changed from: private */
    public class ApplicationThread extends android.app.IApplicationThread.Stub {
        private static final java.lang.String DB_CONNECTION_INFO_FORMAT = "  %8s %8s %14s %5d %5d %5d  %s";
        private static final java.lang.String DB_CONNECTION_INFO_HEADER = "  %8s %8s %14s %5s %5s %5s  %s";
        private static final java.lang.String DB_POOL_INFO_FORMAT = "  %13d %13d %13d  %s";
        private static final java.lang.String DB_POOL_INFO_HEADER = "  %13s %13s %13s  %s";

        private ApplicationThread() {
        }

        @Override // android.app.IApplicationThread
        public final void scheduleReceiver(android.content.Intent intent, android.content.pm.ActivityInfo activityInfo, android.content.res.CompatibilityInfo compatibilityInfo, int i, java.lang.String str, android.os.Bundle bundle, boolean z, boolean z2, int i2, int i3, int i4, java.lang.String str2) {
            android.app.ActivityThread.this.updateProcessState(i3, false);
            android.app.ActivityThread.ReceiverData receiverData = new android.app.ActivityThread.ReceiverData(intent, i, str, bundle, z, false, z2, android.app.ActivityThread.this.mAppThread.asBinder(), i2, i4, str2);
            receiverData.info = activityInfo;
            android.app.ActivityThread.this.sendMessage(113, receiverData);
        }

        @Override // android.app.IApplicationThread
        public final void scheduleReceiverList(java.util.List<android.app.ReceiverInfo> list) throws android.os.RemoteException {
            for (int i = 0; i < list.size(); i++) {
                android.app.ReceiverInfo receiverInfo = list.get(i);
                if (receiverInfo.registered) {
                    scheduleRegisteredReceiver(receiverInfo.receiver, receiverInfo.intent, receiverInfo.resultCode, receiverInfo.data, receiverInfo.extras, receiverInfo.ordered, receiverInfo.sticky, receiverInfo.assumeDelivered, receiverInfo.sendingUser, receiverInfo.processState, receiverInfo.sendingUid, receiverInfo.sendingPackage);
                } else {
                    scheduleReceiver(receiverInfo.intent, receiverInfo.activityInfo, receiverInfo.compatInfo, receiverInfo.resultCode, receiverInfo.data, receiverInfo.extras, receiverInfo.sync, receiverInfo.assumeDelivered, receiverInfo.sendingUser, receiverInfo.processState, receiverInfo.sendingUid, receiverInfo.sendingPackage);
                }
            }
        }

        @Override // android.app.IApplicationThread
        public final void scheduleCreateBackupAgent(android.content.pm.ApplicationInfo applicationInfo, int i, int i2, int i3) {
            android.app.ActivityThread.CreateBackupAgentData createBackupAgentData = new android.app.ActivityThread.CreateBackupAgentData();
            createBackupAgentData.appInfo = applicationInfo;
            createBackupAgentData.backupMode = i;
            createBackupAgentData.userId = i2;
            createBackupAgentData.backupDestination = i3;
            android.app.ActivityThread.this.sendMessage(128, createBackupAgentData);
        }

        @Override // android.app.IApplicationThread
        public final void scheduleDestroyBackupAgent(android.content.pm.ApplicationInfo applicationInfo, int i) {
            android.app.ActivityThread.CreateBackupAgentData createBackupAgentData = new android.app.ActivityThread.CreateBackupAgentData();
            createBackupAgentData.appInfo = applicationInfo;
            createBackupAgentData.userId = i;
            android.app.ActivityThread.this.sendMessage(129, createBackupAgentData);
        }

        @Override // android.app.IApplicationThread
        public final void scheduleCreateService(android.os.IBinder iBinder, android.content.pm.ServiceInfo serviceInfo, android.content.res.CompatibilityInfo compatibilityInfo, int i) {
            android.app.ActivityThread.this.updateProcessState(i, false);
            android.app.ActivityThread.CreateServiceData createServiceData = new android.app.ActivityThread.CreateServiceData();
            createServiceData.token = iBinder;
            createServiceData.info = serviceInfo;
            if (android.os.Trace.isTagEnabled(64L)) {
                android.os.Trace.instant(64L, "scheduleCreateService. token=" + iBinder);
            }
            android.app.ActivityThread.this.sendMessage(114, createServiceData);
        }

        @Override // android.app.IApplicationThread
        public final void scheduleBindService(android.os.IBinder iBinder, android.content.Intent intent, boolean z, int i, long j) {
            android.app.ActivityThread.this.updateProcessState(i, false);
            android.app.ActivityThread.BindServiceData bindServiceData = new android.app.ActivityThread.BindServiceData();
            bindServiceData.token = iBinder;
            bindServiceData.intent = intent;
            bindServiceData.rebind = z;
            bindServiceData.bindSeq = j;
            if (android.os.Trace.isTagEnabled(64L)) {
                android.os.Trace.instant(64L, "scheduleBindService. token=" + iBinder + " bindSeq=" + j);
            }
            android.app.ActivityThread.this.sendMessage(121, bindServiceData);
        }

        @Override // android.app.IApplicationThread
        public final void scheduleUnbindService(android.os.IBinder iBinder, android.content.Intent intent) {
            android.app.ActivityThread.BindServiceData bindServiceData = new android.app.ActivityThread.BindServiceData();
            bindServiceData.token = iBinder;
            bindServiceData.intent = intent;
            bindServiceData.bindSeq = -1L;
            if (android.os.Trace.isTagEnabled(64L)) {
                android.os.Trace.instant(64L, "scheduleUnbindService. token=" + iBinder);
            }
            android.app.ActivityThread.this.sendMessage(122, bindServiceData);
        }

        @Override // android.app.IApplicationThread
        public final void scheduleServiceArgs(android.os.IBinder iBinder, android.content.pm.ParceledListSlice parceledListSlice) {
            java.util.List list = parceledListSlice.getList();
            for (int i = 0; i < list.size(); i++) {
                android.app.ServiceStartArgs serviceStartArgs = (android.app.ServiceStartArgs) list.get(i);
                android.app.ActivityThread.ServiceArgsData serviceArgsData = new android.app.ActivityThread.ServiceArgsData();
                serviceArgsData.token = iBinder;
                serviceArgsData.taskRemoved = serviceStartArgs.taskRemoved;
                serviceArgsData.startId = serviceStartArgs.startId;
                serviceArgsData.flags = serviceStartArgs.flags;
                serviceArgsData.args = serviceStartArgs.args;
                if (android.os.Trace.isTagEnabled(64L)) {
                    android.os.Trace.instant(64L, "scheduleServiceArgs. token=" + iBinder + " startId=" + serviceArgsData.startId);
                }
                android.app.ActivityThread.this.sendMessage(115, serviceArgsData);
            }
        }

        @Override // android.app.IApplicationThread
        public final void scheduleStopService(android.os.IBinder iBinder) {
            if (android.os.Trace.isTagEnabled(64L)) {
                android.os.Trace.instant(64L, "scheduleStopService. token=" + iBinder);
            }
            android.app.ActivityThread.this.sendMessage(116, iBinder);
        }

        @Override // android.app.IApplicationThread
        public final void scheduleTimeoutService(android.os.IBinder iBinder, int i) {
            if (android.os.Trace.isTagEnabled(64L)) {
                android.os.Trace.instant(64L, "scheduleTimeoutService. token=" + iBinder);
            }
            android.app.ActivityThread.this.sendMessage(167, iBinder, i);
        }

        @Override // android.app.IApplicationThread
        public final void schedulePing(android.os.RemoteCallback remoteCallback) {
            android.app.ActivityThread.this.sendMessage(168, remoteCallback);
        }

        @Override // android.app.IApplicationThread
        public final void scheduleTimeoutServiceForType(android.os.IBinder iBinder, int i, int i2) {
            if (android.os.Trace.isTagEnabled(64L)) {
                android.os.Trace.instant(64L, "scheduleTimeoutServiceForType. token=" + iBinder);
            }
            android.app.ActivityThread.this.sendMessage(172, iBinder, i, i2);
        }

        @Override // android.app.IApplicationThread
        public final void bindApplication(java.lang.String str, android.content.pm.ApplicationInfo applicationInfo, java.lang.String str2, java.lang.String str3, boolean z, android.content.pm.ProviderInfoList providerInfoList, android.content.ComponentName componentName, android.app.ProfilerInfo profilerInfo, android.os.Bundle bundle, android.app.IInstrumentationWatcher iInstrumentationWatcher, android.app.IUiAutomationConnection iUiAutomationConnection, int i, boolean z2, boolean z3, boolean z4, boolean z5, android.content.res.Configuration configuration, android.content.res.CompatibilityInfo compatibilityInfo, java.util.Map map, android.os.Bundle bundle2, java.lang.String str4, android.content.AutofillOptions autofillOptions, android.content.ContentCaptureOptions contentCaptureOptions, long[] jArr, android.os.SharedMemory sharedMemory, long j, long j2) {
            if (map != null) {
                android.os.ServiceManager.initServiceCache(map);
            }
            setCoreSettings(bundle2);
            android.app.ActivityThread.AppBindData appBindData = new android.app.ActivityThread.AppBindData();
            appBindData.processName = str;
            appBindData.appInfo = applicationInfo;
            appBindData.sdkSandboxClientAppVolumeUuid = str2;
            appBindData.sdkSandboxClientAppPackage = str3;
            appBindData.isSdkInSandbox = z;
            appBindData.providers = providerInfoList.getList();
            appBindData.instrumentationName = componentName;
            appBindData.instrumentationArgs = bundle;
            appBindData.instrumentationWatcher = iInstrumentationWatcher;
            appBindData.instrumentationUiAutomationConnection = iUiAutomationConnection;
            appBindData.debugMode = i;
            appBindData.enableBinderTracking = z2;
            appBindData.trackAllocation = z3;
            appBindData.restrictedBackupMode = z4;
            appBindData.persistent = z5;
            appBindData.config = configuration;
            appBindData.compatInfo = compatibilityInfo;
            appBindData.initProfilerInfo = profilerInfo;
            appBindData.buildSerial = str4;
            appBindData.autofillOptions = autofillOptions;
            appBindData.contentCaptureOptions = contentCaptureOptions;
            appBindData.disabledCompatChanges = jArr;
            appBindData.mSerializedSystemFontMap = sharedMemory;
            appBindData.startRequestedElapsedTime = j;
            appBindData.startRequestedUptime = j2;
            updateCompatOverrideScale(compatibilityInfo);
            android.content.res.CompatibilityInfo.applyOverrideScaleIfNeeded(configuration);
            android.app.ActivityThread.this.sendMessage(110, appBindData);
        }

        private void updateCompatOverrideScale(android.content.res.CompatibilityInfo compatibilityInfo) {
            if (compatibilityInfo.hasOverrideScaling()) {
                android.content.res.CompatibilityInfo.setOverrideInvertedScale(compatibilityInfo.applicationInvertedScale, compatibilityInfo.applicationDensityInvertedScale);
            } else {
                android.content.res.CompatibilityInfo.setOverrideInvertedScale(1.0f, 1.0f);
            }
        }

        @Override // android.app.IApplicationThread
        public final void runIsolatedEntryPoint(java.lang.String str, java.lang.String[] strArr) {
            com.android.internal.os.SomeArgs obtain = com.android.internal.os.SomeArgs.obtain();
            obtain.arg1 = str;
            obtain.arg2 = strArr;
            android.app.ActivityThread.this.sendMessage(158, obtain);
        }

        @Override // android.app.IApplicationThread
        public final void scheduleExit() {
            android.app.ActivityThread.this.sendMessage(111, null);
        }

        @Override // android.app.IApplicationThread
        public final void scheduleSuicide() {
            android.app.ActivityThread.this.sendMessage(130, null);
        }

        @Override // android.app.IApplicationThread
        public void scheduleApplicationInfoChanged(android.content.pm.ApplicationInfo applicationInfo) {
            synchronized (android.app.ActivityThread.this.mResourcesManager) {
                android.content.pm.ApplicationInfo applicationInfo2 = (android.content.pm.ApplicationInfo) android.app.ActivityThread.this.mPendingAppInfoUpdates.put(applicationInfo.packageName, applicationInfo);
                if (applicationInfo2 != null && applicationInfo2.createTimestamp > applicationInfo.createTimestamp) {
                    android.util.Slog.w(android.app.ActivityThread.TAG, "Skipping application info changed for obsolete AI with TS " + applicationInfo.createTimestamp + " < already pending TS " + applicationInfo2.createTimestamp);
                    android.app.ActivityThread.this.mPendingAppInfoUpdates.put(applicationInfo.packageName, applicationInfo2);
                } else {
                    android.app.ActivityThread.this.mResourcesManager.appendPendingAppInfoUpdate(new java.lang.String[]{applicationInfo.sourceDir}, applicationInfo);
                    android.app.ActivityThread.this.mH.removeMessages(156, applicationInfo.packageName);
                    android.app.ActivityThread.this.sendMessage(156, applicationInfo.packageName);
                }
            }
        }

        @Override // android.app.IApplicationThread
        public void updateTimeZone() {
            java.util.TimeZone.setDefault(null);
        }

        @Override // android.app.IApplicationThread
        public void clearDnsCache() {
            java.net.InetAddress.clearDnsCache();
            libcore.net.event.NetworkEventDispatcher.getInstance().dispatchNetworkConfigurationChange();
        }

        @Override // android.app.IApplicationThread
        public void updateHttpProxy() {
            synchronized (android.app.ActivityThread.this) {
                android.app.Application application = android.app.ActivityThread.this.getApplication();
                if (application == null) {
                    android.app.ActivityThread.this.mUpdateHttpProxyOnBind = true;
                } else {
                    android.app.ActivityThread.updateHttpProxy(application);
                }
            }
        }

        @Override // android.app.IApplicationThread
        public void processInBackground() {
            android.app.ActivityThread.this.mH.removeMessages(120);
            android.app.ActivityThread.this.mH.sendMessage(android.app.ActivityThread.this.mH.obtainMessage(120));
        }

        @Override // android.app.IApplicationThread
        public void dumpService(android.os.ParcelFileDescriptor parcelFileDescriptor, android.os.IBinder iBinder, java.lang.String[] strArr) {
            android.app.ActivityThread.DumpComponentInfo dumpComponentInfo = new android.app.ActivityThread.DumpComponentInfo();
            try {
                try {
                    dumpComponentInfo.fd = parcelFileDescriptor.dup();
                    dumpComponentInfo.token = iBinder;
                    dumpComponentInfo.args = strArr;
                    android.app.ActivityThread.this.sendMessage(123, dumpComponentInfo, 0, 0, true);
                } catch (java.io.IOException e) {
                    android.util.Slog.w(android.app.ActivityThread.TAG, "dumpService failed", e);
                }
            } finally {
                libcore.io.IoUtils.closeQuietly(parcelFileDescriptor);
            }
        }

        @Override // android.app.IApplicationThread
        public void scheduleRegisteredReceiver(android.content.IIntentReceiver iIntentReceiver, android.content.Intent intent, int i, java.lang.String str, android.os.Bundle bundle, boolean z, boolean z2, boolean z3, int i2, int i3, int i4, java.lang.String str2) throws android.os.RemoteException {
            android.app.ActivityThread.this.updateProcessState(i3, false);
            if (iIntentReceiver instanceof android.app.LoadedApk.ReceiverDispatcher.InnerReceiver) {
                ((android.app.LoadedApk.ReceiverDispatcher.InnerReceiver) iIntentReceiver).performReceive(intent, i, str, bundle, z, z2, z3, i2, i4, str2);
                return;
            }
            if (!z3) {
                android.util.Log.wtf(android.app.ActivityThread.TAG, "scheduleRegisteredReceiver() called for " + iIntentReceiver + " and " + intent + " without mechanism to finish delivery");
            }
            if (i4 != -1 || str2 != null) {
                android.util.Log.wtf(android.app.ActivityThread.TAG, "scheduleRegisteredReceiver() called for " + iIntentReceiver + " and " + intent + " from " + str2 + " (UID: " + i4 + ") without mechanism to propagate the sender's identity");
            }
            iIntentReceiver.performReceive(intent, i, str, bundle, z, z2, i2);
        }

        @Override // android.app.IApplicationThread
        public void scheduleLowMemory() {
            android.app.ActivityThread.this.sendMessage(124, null);
        }

        @Override // android.app.IApplicationThread
        public void profilerControl(boolean z, android.app.ProfilerInfo profilerInfo, int i) {
            android.app.ActivityThread.this.sendMessage(127, profilerInfo, z ? 1 : 0, i);
        }

        @Override // android.app.IApplicationThread
        public void dumpHeap(boolean z, boolean z2, boolean z3, java.lang.String str, android.os.ParcelFileDescriptor parcelFileDescriptor, android.os.RemoteCallback remoteCallback) {
            android.app.ActivityThread.DumpHeapData dumpHeapData = new android.app.ActivityThread.DumpHeapData();
            dumpHeapData.managed = z;
            dumpHeapData.mallocInfo = z2;
            dumpHeapData.runGc = z3;
            dumpHeapData.path = str;
            try {
                try {
                    dumpHeapData.fd = parcelFileDescriptor.dup();
                    libcore.io.IoUtils.closeQuietly(parcelFileDescriptor);
                    dumpHeapData.finishCallback = remoteCallback;
                    android.app.ActivityThread.this.sendMessage(135, dumpHeapData, 0, 0, true);
                } catch (java.io.IOException e) {
                    android.util.Slog.e(android.app.ActivityThread.TAG, "Failed to duplicate heap dump file descriptor", e);
                    libcore.io.IoUtils.closeQuietly(parcelFileDescriptor);
                }
            } catch (java.lang.Throwable th) {
                libcore.io.IoUtils.closeQuietly(parcelFileDescriptor);
                throw th;
            }
        }

        @Override // android.app.IApplicationThread
        public void attachAgent(java.lang.String str) {
            android.app.ActivityThread.this.sendMessage(155, str);
        }

        @Override // android.app.IApplicationThread
        public void attachStartupAgents(java.lang.String str) {
            android.app.ActivityThread.this.sendMessage(162, str);
        }

        @Override // android.app.IApplicationThread
        public void setSchedulingGroup(int i) {
            try {
                android.os.Process.setProcessGroup(android.os.Process.myPid(), i);
            } catch (java.lang.Exception e) {
                android.util.Slog.w(android.app.ActivityThread.TAG, "Failed setting process group to " + i, e);
            }
        }

        @Override // android.app.IApplicationThread
        public void dispatchPackageBroadcast(int i, java.lang.String[] strArr) {
            android.app.ActivityThread.this.sendMessage(133, strArr, i);
        }

        @Override // android.app.IApplicationThread
        public void scheduleCrash(java.lang.String str, int i, android.os.Bundle bundle) {
            com.android.internal.os.SomeArgs obtain = com.android.internal.os.SomeArgs.obtain();
            obtain.arg1 = str;
            obtain.arg2 = bundle;
            android.app.ActivityThread.this.sendMessage(134, obtain, i);
        }

        @Override // android.app.IApplicationThread
        public void dumpResources(android.os.ParcelFileDescriptor parcelFileDescriptor, android.os.RemoteCallback remoteCallback) {
            android.app.ActivityThread.DumpResourcesData dumpResourcesData = new android.app.ActivityThread.DumpResourcesData();
            try {
                try {
                    dumpResourcesData.fd = parcelFileDescriptor.dup();
                    dumpResourcesData.finishCallback = remoteCallback;
                    android.app.ActivityThread.this.sendMessage(166, dumpResourcesData, 0, 0, false);
                } catch (java.io.IOException e) {
                    android.util.Slog.w(android.app.ActivityThread.TAG, "dumpResources failed", e);
                }
            } finally {
                libcore.io.IoUtils.closeQuietly(parcelFileDescriptor);
            }
        }

        @Override // android.app.IApplicationThread
        public void dumpActivity(android.os.ParcelFileDescriptor parcelFileDescriptor, android.os.IBinder iBinder, java.lang.String str, java.lang.String[] strArr) {
            android.app.ActivityThread.DumpComponentInfo dumpComponentInfo = new android.app.ActivityThread.DumpComponentInfo();
            try {
                try {
                    dumpComponentInfo.fd = parcelFileDescriptor.dup();
                    dumpComponentInfo.token = iBinder;
                    dumpComponentInfo.prefix = str;
                    dumpComponentInfo.args = strArr;
                    android.app.ActivityThread.this.sendMessage(136, dumpComponentInfo, 0, 0, true);
                } catch (java.io.IOException e) {
                    android.util.Slog.w(android.app.ActivityThread.TAG, "dumpActivity failed", e);
                }
            } finally {
                libcore.io.IoUtils.closeQuietly(parcelFileDescriptor);
            }
        }

        @Override // android.app.IApplicationThread
        public void dumpProvider(android.os.ParcelFileDescriptor parcelFileDescriptor, android.os.IBinder iBinder, java.lang.String[] strArr) {
            android.app.ActivityThread.DumpComponentInfo dumpComponentInfo = new android.app.ActivityThread.DumpComponentInfo();
            try {
                try {
                    dumpComponentInfo.fd = parcelFileDescriptor.dup();
                    dumpComponentInfo.token = iBinder;
                    dumpComponentInfo.args = strArr;
                    android.app.ActivityThread.this.sendMessage(141, dumpComponentInfo, 0, 0, true);
                } catch (java.io.IOException e) {
                    android.util.Slog.w(android.app.ActivityThread.TAG, "dumpProvider failed", e);
                }
            } finally {
                libcore.io.IoUtils.closeQuietly(parcelFileDescriptor);
            }
        }

        @Override // android.app.IApplicationThread
        @dalvik.annotation.optimization.NeverCompile
        public void dumpMemInfo(android.os.ParcelFileDescriptor parcelFileDescriptor, android.os.Debug.MemoryInfo memoryInfo, boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6, java.lang.String[] strArr) {
            com.android.internal.util.FastPrintWriter fastPrintWriter = new com.android.internal.util.FastPrintWriter(new java.io.FileOutputStream(parcelFileDescriptor.getFileDescriptor()));
            try {
                dumpMemInfo(fastPrintWriter, memoryInfo, z, z2, z3, z4, z5, z6);
            } finally {
                fastPrintWriter.flush();
                libcore.io.IoUtils.closeQuietly(parcelFileDescriptor);
            }
        }

        @dalvik.annotation.optimization.NeverCompile
        private void dumpMemInfo(java.io.PrintWriter printWriter, android.os.Debug.MemoryInfo memoryInfo, boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6) {
            long nativeHeapSize = android.os.Debug.getNativeHeapSize() / 1024;
            long nativeHeapAllocatedSize = android.os.Debug.getNativeHeapAllocatedSize() / 1024;
            long nativeHeapFreeSize = android.os.Debug.getNativeHeapFreeSize() / 1024;
            java.lang.Runtime runtime = java.lang.Runtime.getRuntime();
            runtime.gc();
            long j = runtime.totalMemory() / 1024;
            long freeMemory = runtime.freeMemory() / 1024;
            long j2 = j - freeMemory;
            long[] countInstancesOfClasses = dalvik.system.VMDebug.countInstancesOfClasses(new java.lang.Class[]{android.app.ContextImpl.class, android.app.Activity.class, android.webkit.WebView.class, android.view.View.class, android.view.ViewRootImpl.class}, true);
            long j3 = countInstancesOfClasses[0];
            long j4 = countInstancesOfClasses[1];
            long j5 = countInstancesOfClasses[2];
            long j6 = countInstancesOfClasses[3];
            long j7 = countInstancesOfClasses[4];
            int globalAssetCount = android.content.res.AssetManager.getGlobalAssetCount();
            int globalAssetManagerCount = android.content.res.AssetManager.getGlobalAssetManagerCount();
            int binderLocalObjectCount = android.os.Debug.getBinderLocalObjectCount();
            int binderProxyObjectCount = android.os.Debug.getBinderProxyObjectCount();
            int binderDeathObjectCount = android.os.Debug.getBinderDeathObjectCount();
            long globalAllocSize = android.os.Parcel.getGlobalAllocSize();
            long globalAllocCount = android.os.Parcel.getGlobalAllocCount();
            android.database.sqlite.SQLiteDebug.PagerStats databaseInfo = android.database.sqlite.SQLiteDebug.getDatabaseInfo();
            android.app.ActivityThread.dumpMemInfoTable(printWriter, memoryInfo, z, z2, z3, z4, android.os.Process.myPid(), android.app.ActivityThread.this.mBoundApplication != null ? android.app.ActivityThread.this.mBoundApplication.processName : "unknown", nativeHeapSize, nativeHeapAllocatedSize, nativeHeapFreeSize, j, j2, freeMemory);
            if (z) {
                printWriter.print(j6);
                printWriter.print(',');
                printWriter.print(j7);
                printWriter.print(',');
                printWriter.print(j3);
                printWriter.print(',');
                printWriter.print(j4);
                printWriter.print(',');
                printWriter.print(globalAssetCount);
                printWriter.print(',');
                printWriter.print(globalAssetManagerCount);
                printWriter.print(',');
                printWriter.print(binderLocalObjectCount);
                printWriter.print(',');
                printWriter.print(binderProxyObjectCount);
                printWriter.print(',');
                printWriter.print(binderDeathObjectCount);
                printWriter.print(',');
                printWriter.print(databaseInfo.memoryUsed / 1024);
                printWriter.print(',');
                printWriter.print(databaseInfo.memoryUsed / 1024);
                printWriter.print(',');
                printWriter.print(databaseInfo.pageCacheOverflow / 1024);
                printWriter.print(',');
                printWriter.print(databaseInfo.largestMemAlloc / 1024);
                for (int i = 0; i < databaseInfo.dbStats.size(); i++) {
                    android.database.sqlite.SQLiteDebug.DbStats dbStats = databaseInfo.dbStats.get(i);
                    printWriter.print(',');
                    printWriter.print(dbStats.dbName);
                    printWriter.print(',');
                    printWriter.print(dbStats.pageSize);
                    printWriter.print(',');
                    printWriter.print(dbStats.dbSize);
                    printWriter.print(',');
                    printWriter.print(dbStats.lookaside);
                    printWriter.print(',');
                    printWriter.print(dbStats.cacheHits);
                    printWriter.print(',');
                    printWriter.print(dbStats.cacheMisses);
                    printWriter.print(',');
                    printWriter.print(dbStats.cacheSize);
                }
                printWriter.println();
                return;
            }
            printWriter.println(" ");
            printWriter.println(" Objects");
            android.app.ActivityThread.printRow(printWriter, android.app.ActivityThread.TWO_COUNT_COLUMNS, "Views:", java.lang.Long.valueOf(j6), "ViewRootImpl:", java.lang.Long.valueOf(j7));
            android.app.ActivityThread.printRow(printWriter, android.app.ActivityThread.TWO_COUNT_COLUMNS, "AppContexts:", java.lang.Long.valueOf(j3), "Activities:", java.lang.Long.valueOf(j4));
            android.app.ActivityThread.printRow(printWriter, android.app.ActivityThread.TWO_COUNT_COLUMNS, "Assets:", java.lang.Integer.valueOf(globalAssetCount), "AssetManagers:", java.lang.Integer.valueOf(globalAssetManagerCount));
            android.app.ActivityThread.printRow(printWriter, android.app.ActivityThread.TWO_COUNT_COLUMNS, "Local Binders:", java.lang.Integer.valueOf(binderLocalObjectCount), "Proxy Binders:", java.lang.Integer.valueOf(binderProxyObjectCount));
            android.app.ActivityThread.printRow(printWriter, android.app.ActivityThread.TWO_COUNT_COLUMNS, "Parcel memory:", java.lang.Long.valueOf(globalAllocSize / 1024), "Parcel count:", java.lang.Long.valueOf(globalAllocCount));
            android.app.ActivityThread.printRow(printWriter, android.app.ActivityThread.TWO_COUNT_COLUMNS, "Death Recipients:", java.lang.Integer.valueOf(binderDeathObjectCount), "WebViews:", java.lang.Long.valueOf(j5));
            printWriter.println(" ");
            printWriter.println(" SQL");
            android.app.ActivityThread.printRow(printWriter, android.app.ActivityThread.ONE_COUNT_COLUMN, "MEMORY_USED:", java.lang.Integer.valueOf(databaseInfo.memoryUsed / 1024));
            android.app.ActivityThread.printRow(printWriter, android.app.ActivityThread.TWO_COUNT_COLUMNS, "PAGECACHE_OVERFLOW:", java.lang.Integer.valueOf(databaseInfo.pageCacheOverflow / 1024), "MALLOC_SIZE:", java.lang.Integer.valueOf(databaseInfo.largestMemAlloc / 1024));
            printWriter.println(" ");
            int size = databaseInfo.dbStats.size();
            if (size > 0) {
                printWriter.println(" DATABASES");
                android.app.ActivityThread.printRow(printWriter, DB_CONNECTION_INFO_HEADER, "pgsz", "dbsz", "Lookaside(b)", "cache hits", "cache misses", "cache size", "Dbname");
                printWriter.println("PER CONNECTION STATS");
                for (int i2 = 0; i2 < size; i2++) {
                    android.database.sqlite.SQLiteDebug.DbStats dbStats2 = databaseInfo.dbStats.get(i2);
                    if (!dbStats2.arePoolStats) {
                        android.app.ActivityThread.printRow(printWriter, DB_CONNECTION_INFO_FORMAT, dbStats2.pageSize > 0 ? java.lang.String.valueOf(dbStats2.pageSize) : " ", dbStats2.dbSize > 0 ? java.lang.String.valueOf(dbStats2.dbSize) : " ", dbStats2.lookaside > 0 ? java.lang.String.valueOf(dbStats2.lookaside) : " ", java.lang.Integer.valueOf(dbStats2.cacheHits), java.lang.Integer.valueOf(dbStats2.cacheMisses), java.lang.Integer.valueOf(dbStats2.cacheSize), dbStats2.dbName);
                    }
                }
                printWriter.println("POOL STATS");
                android.app.ActivityThread.printRow(printWriter, DB_POOL_INFO_HEADER, "cache hits", "cache misses", "cache size", "Dbname");
                for (int i3 = 0; i3 < size; i3++) {
                    android.database.sqlite.SQLiteDebug.DbStats dbStats3 = databaseInfo.dbStats.get(i3);
                    if (dbStats3.arePoolStats) {
                        android.app.ActivityThread.printRow(printWriter, DB_POOL_INFO_FORMAT, java.lang.Integer.valueOf(dbStats3.cacheHits), java.lang.Integer.valueOf(dbStats3.cacheMisses), java.lang.Integer.valueOf(dbStats3.cacheSize), dbStats3.dbName);
                    }
                }
            }
            java.lang.String assetAllocations = android.content.res.AssetManager.getAssetAllocations();
            if (assetAllocations != null) {
                printWriter.println(" ");
                printWriter.println(" Asset Allocations");
                printWriter.print(assetAllocations);
            }
            if (z5) {
                boolean z7 = !(android.app.ActivityThread.this.mBoundApplication == null || (android.app.ActivityThread.this.mBoundApplication.appInfo.flags & 2) == 0) || android.os.Build.IS_DEBUGGABLE;
                printWriter.println(" ");
                printWriter.println(" Unreachable memory");
                printWriter.print(android.os.Debug.getUnreachableMemory(100, z7));
            }
            if (z6) {
                android.os.Debug.logAllocatorStats();
            }
        }

        @Override // android.app.IApplicationThread
        @dalvik.annotation.optimization.NeverCompile
        public void dumpMemInfoProto(android.os.ParcelFileDescriptor parcelFileDescriptor, android.os.Debug.MemoryInfo memoryInfo, boolean z, boolean z2, boolean z3, boolean z4, java.lang.String[] strArr) {
            android.util.proto.ProtoOutputStream protoOutputStream = new android.util.proto.ProtoOutputStream(parcelFileDescriptor.getFileDescriptor());
            try {
                dumpMemInfo(protoOutputStream, memoryInfo, z, z2, z3, z4);
            } finally {
                protoOutputStream.flush();
                libcore.io.IoUtils.closeQuietly(parcelFileDescriptor);
            }
        }

        @dalvik.annotation.optimization.NeverCompile
        private void dumpMemInfo(android.util.proto.ProtoOutputStream protoOutputStream, android.os.Debug.MemoryInfo memoryInfo, boolean z, boolean z2, boolean z3, boolean z4) {
            long nativeHeapSize = android.os.Debug.getNativeHeapSize() / 1024;
            long nativeHeapAllocatedSize = android.os.Debug.getNativeHeapAllocatedSize() / 1024;
            long nativeHeapFreeSize = android.os.Debug.getNativeHeapFreeSize() / 1024;
            java.lang.Runtime runtime = java.lang.Runtime.getRuntime();
            runtime.gc();
            long j = runtime.totalMemory() / 1024;
            long freeMemory = runtime.freeMemory() / 1024;
            long j2 = j - freeMemory;
            long[] countInstancesOfClasses = dalvik.system.VMDebug.countInstancesOfClasses(new java.lang.Class[]{android.app.ContextImpl.class, android.app.Activity.class, android.webkit.WebView.class, android.view.View.class, android.view.ViewRootImpl.class}, true);
            long j3 = countInstancesOfClasses[0];
            long j4 = countInstancesOfClasses[1];
            long j5 = countInstancesOfClasses[2];
            long j6 = countInstancesOfClasses[3];
            long j7 = countInstancesOfClasses[4];
            int globalAssetCount = android.content.res.AssetManager.getGlobalAssetCount();
            int globalAssetManagerCount = android.content.res.AssetManager.getGlobalAssetManagerCount();
            int binderLocalObjectCount = android.os.Debug.getBinderLocalObjectCount();
            int binderProxyObjectCount = android.os.Debug.getBinderProxyObjectCount();
            int binderDeathObjectCount = android.os.Debug.getBinderDeathObjectCount();
            long globalAllocSize = android.os.Parcel.getGlobalAllocSize();
            long globalAllocCount = android.os.Parcel.getGlobalAllocCount();
            android.database.sqlite.SQLiteDebug.PagerStats databaseInfo = android.database.sqlite.SQLiteDebug.getDatabaseInfo();
            long start = protoOutputStream.start(1146756268033L);
            protoOutputStream.write(1120986464257L, android.os.Process.myPid());
            protoOutputStream.write(1138166333442L, android.app.ActivityThread.this.mBoundApplication != null ? android.app.ActivityThread.this.mBoundApplication.processName : "unknown");
            android.app.ActivityThread.dumpMemInfoTable(protoOutputStream, memoryInfo, z2, z3, nativeHeapSize, nativeHeapAllocatedSize, nativeHeapFreeSize, j, j2, freeMemory);
            protoOutputStream.end(start);
            long start2 = protoOutputStream.start(1146756268034L);
            protoOutputStream.write(1120986464257L, j6);
            long j8 = 1120986464258L;
            protoOutputStream.write(1120986464258L, j7);
            protoOutputStream.write(1120986464259L, j3);
            protoOutputStream.write(1120986464260L, j4);
            protoOutputStream.write(1120986464261L, globalAssetCount);
            protoOutputStream.write(1120986464262L, globalAssetManagerCount);
            protoOutputStream.write(1120986464263L, binderLocalObjectCount);
            protoOutputStream.write(1120986464264L, binderProxyObjectCount);
            protoOutputStream.write(1112396529673L, globalAllocSize / 1024);
            protoOutputStream.write(1120986464266L, globalAllocCount);
            protoOutputStream.write(1120986464267L, binderDeathObjectCount);
            protoOutputStream.write(1120986464269L, j5);
            protoOutputStream.end(start2);
            long start3 = protoOutputStream.start(1146756268035L);
            protoOutputStream.write(1120986464257L, databaseInfo.memoryUsed / 1024);
            protoOutputStream.write(1120986464258L, databaseInfo.pageCacheOverflow / 1024);
            protoOutputStream.write(1120986464259L, databaseInfo.largestMemAlloc / 1024);
            int size = databaseInfo.dbStats.size();
            int i = 0;
            while (i < size) {
                android.database.sqlite.SQLiteDebug.DbStats dbStats = databaseInfo.dbStats.get(i);
                long start4 = protoOutputStream.start(2246267895812L);
                protoOutputStream.write(1138166333441L, dbStats.dbName);
                protoOutputStream.write(j8, dbStats.pageSize);
                protoOutputStream.write(1120986464259L, dbStats.dbSize);
                protoOutputStream.write(1120986464260L, dbStats.lookaside);
                protoOutputStream.write(1120986464262L, dbStats.cacheHits);
                protoOutputStream.write(1120986464263L, dbStats.cacheMisses);
                protoOutputStream.write(1120986464264L, dbStats.cacheSize);
                protoOutputStream.end(start4);
                i++;
                j8 = 1120986464258L;
            }
            protoOutputStream.end(start3);
            java.lang.String assetAllocations = android.content.res.AssetManager.getAssetAllocations();
            if (assetAllocations != null) {
                protoOutputStream.write(1138166333444L, assetAllocations);
            }
            if (z4) {
                protoOutputStream.write(1138166333445L, android.os.Debug.getUnreachableMemory(100, ((android.app.ActivityThread.this.mBoundApplication == null ? 0 : android.app.ActivityThread.this.mBoundApplication.appInfo.flags) & 2) != 0 || android.os.Build.IS_DEBUGGABLE));
            }
        }

        @Override // android.app.IApplicationThread
        public void dumpGfxInfo(android.os.ParcelFileDescriptor parcelFileDescriptor, java.lang.String[] strArr) {
            android.app.ActivityThread.DumpComponentInfo dumpComponentInfo = new android.app.ActivityThread.DumpComponentInfo();
            try {
                try {
                    dumpComponentInfo.fd = parcelFileDescriptor.dup();
                    dumpComponentInfo.token = null;
                    dumpComponentInfo.args = strArr;
                    android.app.ActivityThread.this.sendMessage(165, dumpComponentInfo, 0, 0, true);
                } catch (java.io.IOException e) {
                    android.util.Slog.w(android.app.ActivityThread.TAG, "dumpGfxInfo failed", e);
                }
            } finally {
                libcore.io.IoUtils.closeQuietly(parcelFileDescriptor);
            }
        }

        @Override // android.app.IApplicationThread
        public void dumpCacheInfo(android.os.ParcelFileDescriptor parcelFileDescriptor, java.lang.String[] strArr) {
            android.app.PropertyInvalidatedCache.dumpCacheInfo(parcelFileDescriptor, strArr);
            libcore.io.IoUtils.closeQuietly(parcelFileDescriptor);
        }

        private java.io.File getDatabasesDir(android.content.Context context) {
            return context.getDatabasePath(android.app.backup.FullBackup.APK_TREE_TOKEN).getParentFile();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void dumpDatabaseInfo(android.os.ParcelFileDescriptor parcelFileDescriptor, java.lang.String[] strArr, boolean z) {
            com.android.internal.util.FastPrintWriter fastPrintWriter = new com.android.internal.util.FastPrintWriter(new java.io.FileOutputStream(parcelFileDescriptor.getFileDescriptor()));
            android.database.sqlite.SQLiteDebug.dump(new android.util.PrintWriterPrinter(fastPrintWriter), strArr, z);
            fastPrintWriter.flush();
        }

        @Override // android.app.IApplicationThread
        public void dumpDbInfo(android.os.ParcelFileDescriptor parcelFileDescriptor, final java.lang.String[] strArr) {
            try {
                if (!android.app.ActivityThread.this.mSystemThread) {
                    dumpDatabaseInfo(parcelFileDescriptor, strArr, false);
                    return;
                }
                final android.os.ParcelFileDescriptor dup = parcelFileDescriptor.dup();
                libcore.io.IoUtils.closeQuietly(parcelFileDescriptor);
                android.os.AsyncTask.THREAD_POOL_EXECUTOR.execute(new java.lang.Runnable() { // from class: android.app.ActivityThread.ApplicationThread.1
                    @Override // java.lang.Runnable
                    public void run() {
                        try {
                            android.app.ActivityThread.ApplicationThread.this.dumpDatabaseInfo(dup, strArr, true);
                        } finally {
                            libcore.io.IoUtils.closeQuietly(dup);
                        }
                    }
                });
            } catch (java.io.IOException e) {
                android.util.Log.w(android.app.ActivityThread.TAG, "Could not dup FD " + parcelFileDescriptor.getFileDescriptor().getInt$());
            } finally {
                libcore.io.IoUtils.closeQuietly(parcelFileDescriptor);
            }
        }

        @Override // android.app.IApplicationThread
        public void unstableProviderDied(android.os.IBinder iBinder) {
            android.app.ActivityThread.this.sendMessage(142, iBinder);
        }

        @Override // android.app.IApplicationThread
        public void requestAssistContextExtras(android.os.IBinder iBinder, android.os.IBinder iBinder2, int i, int i2, int i3) {
            android.app.ActivityThread.RequestAssistContextExtras requestAssistContextExtras = new android.app.ActivityThread.RequestAssistContextExtras();
            requestAssistContextExtras.activityToken = iBinder;
            requestAssistContextExtras.requestToken = iBinder2;
            requestAssistContextExtras.requestType = i;
            requestAssistContextExtras.sessionId = i2;
            requestAssistContextExtras.flags = i3;
            android.app.ActivityThread.this.sendMessage(143, requestAssistContextExtras);
        }

        @Override // android.app.IApplicationThread
        public void setCoreSettings(android.os.Bundle bundle) {
            android.app.ActivityThread.this.sendMessage(138, bundle);
        }

        @Override // android.app.IApplicationThread
        public void updatePackageCompatibilityInfo(java.lang.String str, android.content.res.CompatibilityInfo compatibilityInfo) {
            android.app.ActivityThread.UpdateCompatibilityData updateCompatibilityData = new android.app.ActivityThread.UpdateCompatibilityData();
            updateCompatibilityData.pkg = str;
            updateCompatibilityData.info = compatibilityInfo;
            updateCompatOverrideScale(compatibilityInfo);
            android.app.ActivityThread.this.sendMessage(139, updateCompatibilityData);
        }

        @Override // android.app.IApplicationThread
        public void scheduleTrimMemory(int i) {
            com.android.internal.util.function.pooled.PooledRunnable recycleOnUse = com.android.internal.util.function.pooled.PooledLambda.obtainRunnable(new java.util.function.BiConsumer() { // from class: android.app.ActivityThread$ApplicationThread$$ExternalSyntheticLambda2
                @Override // java.util.function.BiConsumer
                public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                    ((android.app.ActivityThread) obj).handleTrimMemory(((java.lang.Integer) obj2).intValue());
                }
            }, android.app.ActivityThread.this, java.lang.Integer.valueOf(i)).recycleOnUse();
            android.view.Choreographer mainThreadInstance = android.view.Choreographer.getMainThreadInstance();
            if (mainThreadInstance != null) {
                mainThreadInstance.postCallback(4, recycleOnUse, null);
            } else {
                android.app.ActivityThread.this.mH.post(recycleOnUse);
            }
        }

        @Override // android.app.IApplicationThread
        public void scheduleTranslucentConversionComplete(android.os.IBinder iBinder, boolean z) {
            android.app.ActivityThread.this.sendMessage(144, iBinder, z ? 1 : 0);
        }

        @Override // android.app.IApplicationThread
        public void scheduleOnNewSceneTransitionInfo(android.os.IBinder iBinder, android.app.ActivityOptions.SceneTransitionInfo sceneTransitionInfo) {
            android.app.ActivityThread.this.sendMessage(146, new android.util.Pair(iBinder, sceneTransitionInfo));
        }

        @Override // android.app.IApplicationThread
        public void setProcessState(int i) {
            android.app.ActivityThread.this.updateProcessState(i, true);
        }

        @Override // android.app.IApplicationThread
        public void setNetworkBlockSeq(long j) {
            synchronized (android.app.ActivityThread.this.mNetworkPolicyLock) {
                android.app.ActivityThread.this.mNetworkBlockSeq = j;
            }
        }

        @Override // android.app.IApplicationThread
        public void scheduleInstallProvider(android.content.pm.ProviderInfo providerInfo) {
            android.app.ActivityThread.this.sendMessage(145, providerInfo);
        }

        @Override // android.app.IApplicationThread
        public final void updateTimePrefs(int i) {
            java.lang.Boolean bool;
            if (i == 0) {
                bool = java.lang.Boolean.FALSE;
            } else if (i == 1) {
                bool = java.lang.Boolean.TRUE;
            } else {
                bool = null;
            }
            java.text.DateFormat.set24HourTimePref(bool);
        }

        @Override // android.app.IApplicationThread
        public void scheduleEnterAnimationComplete(android.os.IBinder iBinder) {
            android.app.ActivityThread.this.sendMessage(149, iBinder);
        }

        @Override // android.app.IApplicationThread
        public void notifyCleartextNetwork(byte[] bArr) {
            if (android.os.StrictMode.vmCleartextNetworkEnabled()) {
                android.os.StrictMode.onCleartextNetworkDetected(bArr);
            }
        }

        @Override // android.app.IApplicationThread
        public void startBinderTracking() {
            android.app.ActivityThread.this.sendMessage(150, null);
        }

        @Override // android.app.IApplicationThread
        public void stopBinderTrackingAndDump(android.os.ParcelFileDescriptor parcelFileDescriptor) {
            try {
                android.app.ActivityThread.this.sendMessage(151, parcelFileDescriptor.dup());
            } catch (java.io.IOException e) {
            } catch (java.lang.Throwable th) {
                libcore.io.IoUtils.closeQuietly(parcelFileDescriptor);
                throw th;
            }
            libcore.io.IoUtils.closeQuietly(parcelFileDescriptor);
        }

        @Override // android.app.IApplicationThread
        public void scheduleLocalVoiceInteractionStarted(android.os.IBinder iBinder, com.android.internal.app.IVoiceInteractor iVoiceInteractor) throws android.os.RemoteException {
            com.android.internal.os.SomeArgs obtain = com.android.internal.os.SomeArgs.obtain();
            obtain.arg1 = iBinder;
            obtain.arg2 = iVoiceInteractor;
            android.app.ActivityThread.this.sendMessage(154, obtain);
        }

        @Override // android.app.IApplicationThread
        public void handleTrustStorageUpdate() {
            android.security.NetworkSecurityPolicy.getInstance().handleTrustStorageUpdate();
        }

        @Override // android.app.IApplicationThread
        public void scheduleTransaction(android.app.servertransaction.ClientTransaction clientTransaction) throws android.os.RemoteException {
            android.app.ActivityThread.this.scheduleTransaction(clientTransaction);
        }

        @Override // android.app.IApplicationThread
        public void scheduleTaskFragmentTransaction(android.window.ITaskFragmentOrganizer iTaskFragmentOrganizer, android.window.TaskFragmentTransaction taskFragmentTransaction) throws android.os.RemoteException {
            iTaskFragmentOrganizer.onTransactionReady(taskFragmentTransaction);
        }

        @Override // android.app.IApplicationThread
        public void requestDirectActions(android.os.IBinder iBinder, com.android.internal.app.IVoiceInteractor iVoiceInteractor, android.os.RemoteCallback remoteCallback, android.os.RemoteCallback remoteCallback2) {
            android.os.CancellationSignal cancellationSignal = new android.os.CancellationSignal();
            if (remoteCallback != null) {
                android.app.ActivityThread.SafeCancellationTransport createSafeCancellationTransport = android.app.ActivityThread.this.createSafeCancellationTransport(cancellationSignal);
                android.os.Bundle bundle = new android.os.Bundle();
                bundle.putBinder(android.app.VoiceInteractor.KEY_CANCELLATION_SIGNAL, createSafeCancellationTransport.asBinder());
                remoteCallback.sendResult(bundle);
            }
            android.app.ActivityThread.this.mH.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new com.android.internal.util.function.HexConsumer() { // from class: android.app.ActivityThread$ApplicationThread$$ExternalSyntheticLambda1
                @Override // com.android.internal.util.function.HexConsumer
                public final void accept(java.lang.Object obj, java.lang.Object obj2, java.lang.Object obj3, java.lang.Object obj4, java.lang.Object obj5, java.lang.Object obj6) {
                    ((android.app.ActivityThread) obj).handleRequestDirectActions((android.os.IBinder) obj2, (com.android.internal.app.IVoiceInteractor) obj3, (android.os.CancellationSignal) obj4, (android.os.RemoteCallback) obj5, ((java.lang.Integer) obj6).intValue());
                }
            }, android.app.ActivityThread.this, iBinder, iVoiceInteractor, cancellationSignal, remoteCallback2, 7));
        }

        @Override // android.app.IApplicationThread
        public void performDirectAction(android.os.IBinder iBinder, java.lang.String str, android.os.Bundle bundle, android.os.RemoteCallback remoteCallback, android.os.RemoteCallback remoteCallback2) {
            android.os.CancellationSignal cancellationSignal = new android.os.CancellationSignal();
            if (remoteCallback != null) {
                android.app.ActivityThread.SafeCancellationTransport createSafeCancellationTransport = android.app.ActivityThread.this.createSafeCancellationTransport(cancellationSignal);
                android.os.Bundle bundle2 = new android.os.Bundle();
                bundle2.putBinder(android.app.VoiceInteractor.KEY_CANCELLATION_SIGNAL, createSafeCancellationTransport.asBinder());
                remoteCallback.sendResult(bundle2);
            }
            android.app.ActivityThread.this.mH.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new com.android.internal.util.function.HexConsumer() { // from class: android.app.ActivityThread$ApplicationThread$$ExternalSyntheticLambda0
                @Override // com.android.internal.util.function.HexConsumer
                public final void accept(java.lang.Object obj, java.lang.Object obj2, java.lang.Object obj3, java.lang.Object obj4, java.lang.Object obj5, java.lang.Object obj6) {
                    ((android.app.ActivityThread) obj).handlePerformDirectAction((android.os.IBinder) obj2, (java.lang.String) obj3, (android.os.Bundle) obj4, (android.os.CancellationSignal) obj5, (android.os.RemoteCallback) obj6);
                }
            }, android.app.ActivityThread.this, iBinder, str, bundle, cancellationSignal, remoteCallback2));
        }

        @Override // android.app.IApplicationThread
        public void notifyContentProviderPublishStatus(android.app.ContentProviderHolder contentProviderHolder, java.lang.String str, int i, boolean z) {
            for (java.lang.String str2 : str.split(android.inputmethodservice.navigationbar.NavigationBarInflaterView.GRAVITY_SEPARATOR)) {
                android.app.ActivityThread.ProviderKey getProviderKey = android.app.ActivityThread.this.getGetProviderKey(str2, i);
                synchronized (getProviderKey.mLock) {
                    getProviderKey.mHolder = contentProviderHolder;
                    getProviderKey.mLock.notifyAll();
                }
            }
        }

        @Override // android.app.IApplicationThread
        public void instrumentWithoutRestart(android.content.ComponentName componentName, android.os.Bundle bundle, android.app.IInstrumentationWatcher iInstrumentationWatcher, android.app.IUiAutomationConnection iUiAutomationConnection, android.content.pm.ApplicationInfo applicationInfo) {
            android.app.ActivityThread.AppBindData appBindData = new android.app.ActivityThread.AppBindData();
            appBindData.instrumentationName = componentName;
            appBindData.instrumentationArgs = bundle;
            appBindData.instrumentationWatcher = iInstrumentationWatcher;
            appBindData.instrumentationUiAutomationConnection = iUiAutomationConnection;
            appBindData.appInfo = applicationInfo;
            android.app.ActivityThread.this.sendMessage(170, appBindData);
        }

        @Override // android.app.IApplicationThread
        public void updateUiTranslationState(android.os.IBinder iBinder, int i, android.view.translation.TranslationSpec translationSpec, android.view.translation.TranslationSpec translationSpec2, java.util.List<android.view.autofill.AutofillId> list, android.view.translation.UiTranslationSpec uiTranslationSpec) {
            com.android.internal.os.SomeArgs obtain = com.android.internal.os.SomeArgs.obtain();
            obtain.arg1 = iBinder;
            obtain.arg2 = java.lang.Integer.valueOf(i);
            obtain.arg3 = translationSpec;
            obtain.arg4 = translationSpec2;
            obtain.arg5 = list;
            obtain.arg6 = uiTranslationSpec;
            android.app.ActivityThread.this.sendMessage(163, obtain);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public android.app.ActivityThread.SafeCancellationTransport createSafeCancellationTransport(android.os.CancellationSignal cancellationSignal) {
        android.app.ActivityThread.SafeCancellationTransport safeCancellationTransport;
        synchronized (this) {
            if (this.mRemoteCancellations == null) {
                this.mRemoteCancellations = new android.util.ArrayMap();
            }
            safeCancellationTransport = new android.app.ActivityThread.SafeCancellationTransport(this, cancellationSignal);
            this.mRemoteCancellations.put(safeCancellationTransport, cancellationSignal);
        }
        return safeCancellationTransport;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public android.os.CancellationSignal removeSafeCancellationTransport(android.app.ActivityThread.SafeCancellationTransport safeCancellationTransport) {
        android.os.CancellationSignal remove;
        synchronized (this) {
            remove = this.mRemoteCancellations.remove(safeCancellationTransport);
            if (this.mRemoteCancellations.isEmpty()) {
                this.mRemoteCancellations = null;
            }
        }
        return remove;
    }

    private static final class SafeCancellationTransport extends android.os.ICancellationSignal.Stub {
        private final java.lang.ref.WeakReference<android.app.ActivityThread> mWeakActivityThread;

        SafeCancellationTransport(android.app.ActivityThread activityThread, android.os.CancellationSignal cancellationSignal) {
            this.mWeakActivityThread = new java.lang.ref.WeakReference<>(activityThread);
        }

        @Override // android.os.ICancellationSignal
        public void cancel() {
            android.os.CancellationSignal removeSafeCancellationTransport;
            android.app.ActivityThread activityThread = this.mWeakActivityThread.get();
            if (activityThread != null && (removeSafeCancellationTransport = activityThread.removeSafeCancellationTransport(this)) != null) {
                removeSafeCancellationTransport.cancel();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void throwRemoteServiceException(java.lang.String str, int i, android.os.Bundle bundle) {
        switch (i) {
            case 1:
                throw generateForegroundServiceDidNotStartInTimeException(str, bundle);
            case 2:
                throw new android.app.RemoteServiceException.CannotPostForegroundServiceNotificationException(str);
            case 3:
                throw new android.app.RemoteServiceException.BadForegroundServiceNotificationException(str);
            case 4:
                throw new android.app.RemoteServiceException.MissingRequestPasswordComplexityPermissionException(str);
            case 5:
                throw new android.app.RemoteServiceException.CrashedByAdbException(str);
            case 6:
                throw new android.app.RemoteServiceException.BadUserInitiatedJobNotificationException(str);
            default:
                throw new android.app.RemoteServiceException(str + " (with unwknown typeId:" + i + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
        }
    }

    private android.app.RemoteServiceException.ForegroundServiceDidNotStartInTimeException generateForegroundServiceDidNotStartInTimeException(java.lang.String str, android.os.Bundle bundle) {
        java.lang.String serviceClassNameFromExtras = android.app.RemoteServiceException.ForegroundServiceDidNotStartInTimeException.getServiceClassNameFromExtras(bundle);
        throw new android.app.RemoteServiceException.ForegroundServiceDidNotStartInTimeException(str, serviceClassNameFromExtras == null ? null : android.app.Service.getStartForegroundServiceStackTrace(serviceClassNameFromExtras));
    }

    class H extends android.os.Handler {
        public static final int APPLICATION_INFO_CHANGED = 156;
        public static final int ATTACH_AGENT = 155;
        public static final int ATTACH_STARTUP_AGENTS = 162;
        public static final int BIND_APPLICATION = 110;
        public static final int BIND_SERVICE = 121;
        public static final int CLEAN_UP_CONTEXT = 119;
        public static final int CONFIGURATION_CHANGED = 118;
        public static final int CREATE_BACKUP_AGENT = 128;
        public static final int CREATE_SERVICE = 114;
        public static final int DESTROY_BACKUP_AGENT = 129;
        public static final int DISPATCH_PACKAGE_BROADCAST = 133;
        public static final int DUMP_ACTIVITY = 136;
        public static final int DUMP_GFXINFO = 165;
        public static final int DUMP_HEAP = 135;
        public static final int DUMP_PROVIDER = 141;
        public static final int DUMP_RESOURCES = 166;
        public static final int DUMP_SERVICE = 123;
        public static final int ENTER_ANIMATION_COMPLETE = 149;
        public static final int EXECUTE_TRANSACTION = 159;
        public static final int EXIT_APPLICATION = 111;
        public static final int FINISH_INSTRUMENTATION_WITHOUT_RESTART = 171;
        public static final int GC_WHEN_IDLE = 120;
        public static final int INSTALL_PROVIDER = 145;
        public static final int INSTRUMENT_WITHOUT_RESTART = 170;
        public static final int LOCAL_VOICE_INTERACTION_STARTED = 154;
        public static final int LOW_MEMORY = 124;
        public static final int ON_NEW_SCENE_TRANSITION_INFO = 146;
        public static final int PING = 168;
        public static final int PROFILER_CONTROL = 127;
        public static final int PURGE_RESOURCES = 161;
        public static final int RECEIVER = 113;
        public static final int RELAUNCH_ACTIVITY = 160;
        public static final int REMOVE_PROVIDER = 131;
        public static final int REQUEST_ASSIST_CONTEXT_EXTRAS = 143;
        public static final int RUN_ISOLATED_ENTRY_POINT = 158;
        public static final int SCHEDULE_CRASH = 134;
        public static final int SERVICE_ARGS = 115;
        public static final int SET_CONTENT_CAPTURE_OPTIONS_CALLBACK = 164;
        public static final int SET_CORE_SETTINGS = 138;
        public static final int SLEEPING = 137;
        public static final int START_BINDER_TRACKING = 150;
        public static final int STOP_BINDER_TRACKING_AND_DUMP = 151;
        public static final int STOP_SERVICE = 116;
        public static final int SUICIDE = 130;
        public static final int TIMEOUT_SERVICE = 167;
        public static final int TIMEOUT_SERVICE_FOR_TYPE = 172;
        public static final int TRANSLUCENT_CONVERSION_COMPLETE = 144;
        public static final int UNBIND_SERVICE = 122;
        public static final int UNSTABLE_PROVIDER_DIED = 142;
        public static final int UPDATE_PACKAGE_COMPATIBILITY_INFO = 139;
        public static final int UPDATE_UI_TRANSLATION_STATE = 163;

        H() {
        }

        java.lang.String codeToString(int i) {
            return java.lang.Integer.toString(i);
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // android.os.Handler
        public void handleMessage(android.os.Message message) {
            switch (message.what) {
                case 110:
                    android.os.Trace.traceBegin(64L, "bindApplication");
                    android.app.ActivityThread.this.handleBindApplication((android.app.ActivityThread.AppBindData) message.obj);
                    break;
                case 111:
                    if (android.app.ActivityThread.this.mInitialApplication != null) {
                        android.app.ActivityThread.this.mInitialApplication.onTerminate();
                    }
                    android.os.Looper.myLooper().quit();
                    break;
                case 113:
                    if (android.os.Trace.isTagEnabled(64L)) {
                        android.app.ActivityThread.ReceiverData receiverData = (android.app.ActivityThread.ReceiverData) message.obj;
                        if (receiverData.intent != null) {
                            android.os.Trace.traceBegin(64L, "broadcastReceiveComp: " + receiverData.intent.getAction());
                        } else {
                            android.os.Trace.traceBegin(64L, "broadcastReceiveComp");
                        }
                    }
                    android.app.ActivityThread.this.handleReceiver((android.app.ActivityThread.ReceiverData) message.obj);
                    android.os.Trace.traceEnd(64L);
                    break;
                case 114:
                    if (android.os.Trace.isTagEnabled(64L)) {
                        android.os.Trace.traceBegin(64L, "serviceCreate: " + java.lang.String.valueOf(message.obj));
                    }
                    android.app.ActivityThread.this.handleCreateService((android.app.ActivityThread.CreateServiceData) message.obj);
                    android.os.Trace.traceEnd(64L);
                    break;
                case 115:
                    if (android.os.Trace.isTagEnabled(64L)) {
                        android.os.Trace.traceBegin(64L, "serviceStart: " + java.lang.String.valueOf(message.obj));
                    }
                    android.app.ActivityThread.this.handleServiceArgs((android.app.ActivityThread.ServiceArgsData) message.obj);
                    android.os.Trace.traceEnd(64L);
                    break;
                case 116:
                    if (android.os.Trace.isTagEnabled(64L)) {
                        android.os.Trace.traceBegin(64L, "serviceStop: " + java.lang.String.valueOf(message.obj));
                    }
                    android.app.ActivityThread.this.handleStopService((android.os.IBinder) message.obj);
                    android.app.ActivityThread.this.schedulePurgeIdler();
                    android.os.Trace.traceEnd(64L);
                    break;
                case 118:
                    android.app.ActivityThread.this.mConfigurationController.handleConfigurationChanged((android.content.res.Configuration) message.obj);
                    break;
                case 119:
                    android.app.ActivityThread.ContextCleanupInfo contextCleanupInfo = (android.app.ActivityThread.ContextCleanupInfo) message.obj;
                    contextCleanupInfo.context.performFinalCleanup(contextCleanupInfo.who, contextCleanupInfo.what);
                    break;
                case 120:
                    android.os.Trace.traceBegin(64L, "gcWhenIdle");
                    try {
                        android.app.ActivityThread.this.scheduleGcIdler();
                        android.os.Trace.traceEnd(64L);
                        break;
                    } finally {
                    }
                case 121:
                    if (android.os.Trace.isTagEnabled(64L)) {
                        android.os.Trace.traceBegin(64L, "serviceBind: " + java.lang.String.valueOf(message.obj));
                    }
                    android.app.ActivityThread.this.handleBindService((android.app.ActivityThread.BindServiceData) message.obj);
                    android.os.Trace.traceEnd(64L);
                    break;
                case 122:
                    if (android.os.Trace.isTagEnabled(64L)) {
                        android.os.Trace.traceBegin(64L, "serviceUnbind: " + java.lang.String.valueOf(message.obj));
                    }
                    android.app.ActivityThread.this.handleUnbindService((android.app.ActivityThread.BindServiceData) message.obj);
                    android.app.ActivityThread.this.schedulePurgeIdler();
                    android.os.Trace.traceEnd(64L);
                    break;
                case 123:
                    android.app.ActivityThread.this.handleDumpService((android.app.ActivityThread.DumpComponentInfo) message.obj);
                    break;
                case 124:
                    android.os.Trace.traceBegin(64L, "lowMemory");
                    android.app.ActivityThread.this.handleLowMemory();
                    android.os.Trace.traceEnd(64L);
                    break;
                case 127:
                    android.app.ActivityThread.this.handleProfilerControl(message.arg1 != 0, (android.app.ProfilerInfo) message.obj, message.arg2);
                    break;
                case 128:
                    android.os.Trace.traceBegin(64L, "backupCreateAgent");
                    android.app.ActivityThread.this.handleCreateBackupAgent((android.app.ActivityThread.CreateBackupAgentData) message.obj);
                    android.os.Trace.traceEnd(64L);
                    break;
                case 129:
                    android.os.Trace.traceBegin(64L, "backupDestroyAgent");
                    android.app.ActivityThread.this.handleDestroyBackupAgent((android.app.ActivityThread.CreateBackupAgentData) message.obj);
                    android.os.Trace.traceEnd(64L);
                    break;
                case 130:
                    android.os.Process.killProcess(android.os.Process.myPid());
                    break;
                case 131:
                    android.os.Trace.traceBegin(64L, "providerRemove");
                    android.app.ActivityThread.this.completeRemoveProvider((android.app.ActivityThread.ProviderRefCount) message.obj);
                    android.os.Trace.traceEnd(64L);
                    break;
                case 133:
                    android.os.Trace.traceBegin(64L, "broadcastPackage");
                    android.app.ActivityThread.this.handleDispatchPackageBroadcast(message.arg1, (java.lang.String[]) message.obj);
                    android.os.Trace.traceEnd(64L);
                    break;
                case 134:
                    com.android.internal.os.SomeArgs someArgs = (com.android.internal.os.SomeArgs) message.obj;
                    java.lang.String str = (java.lang.String) someArgs.arg1;
                    android.os.Bundle bundle = (android.os.Bundle) someArgs.arg2;
                    someArgs.recycle();
                    android.app.ActivityThread.this.throwRemoteServiceException(str, message.arg1, bundle);
                    break;
                case 135:
                    android.app.ActivityThread.handleDumpHeap((android.app.ActivityThread.DumpHeapData) message.obj);
                    break;
                case 136:
                    android.app.ActivityThread.this.handleDumpActivity((android.app.ActivityThread.DumpComponentInfo) message.obj);
                    break;
                case 138:
                    android.os.Trace.traceBegin(64L, "setCoreSettings");
                    android.app.ActivityThread.this.handleSetCoreSettings((android.os.Bundle) message.obj);
                    android.os.Trace.traceEnd(64L);
                    break;
                case 139:
                    android.app.ActivityThread.this.handleUpdatePackageCompatibilityInfo((android.app.ActivityThread.UpdateCompatibilityData) message.obj);
                    break;
                case 141:
                    android.app.ActivityThread.this.handleDumpProvider((android.app.ActivityThread.DumpComponentInfo) message.obj);
                    break;
                case 142:
                    android.app.ActivityThread.this.handleUnstableProviderDied((android.os.IBinder) message.obj, false);
                    break;
                case 143:
                    android.app.ActivityThread.this.handleRequestAssistContextExtras((android.app.ActivityThread.RequestAssistContextExtras) message.obj);
                    break;
                case 144:
                    android.app.ActivityThread.this.handleTranslucentConversionComplete((android.os.IBinder) message.obj, message.arg1 == 1);
                    break;
                case 145:
                    if (android.os.Trace.isTagEnabled(64L)) {
                        android.os.Trace.traceBegin(64L, "providerInstall: " + java.lang.String.valueOf(message.obj));
                    }
                    try {
                        android.app.ActivityThread.this.handleInstallProvider((android.content.pm.ProviderInfo) message.obj);
                        android.os.Trace.traceEnd(64L);
                        break;
                    } finally {
                    }
                case 146:
                    android.util.Pair pair = (android.util.Pair) message.obj;
                    android.app.ActivityThread.this.onNewSceneTransitionInfo((android.os.IBinder) pair.first, (android.app.ActivityOptions.SceneTransitionInfo) pair.second);
                    break;
                case 149:
                    android.app.ActivityThread.this.handleEnterAnimationComplete((android.os.IBinder) message.obj);
                    break;
                case 150:
                    android.app.ActivityThread.this.handleStartBinderTracking();
                    break;
                case 151:
                    android.app.ActivityThread.this.handleStopBinderTrackingAndDump((android.os.ParcelFileDescriptor) message.obj);
                    break;
                case 154:
                    android.app.ActivityThread.this.handleLocalVoiceInteractionStarted((android.os.IBinder) ((com.android.internal.os.SomeArgs) message.obj).arg1, (com.android.internal.app.IVoiceInteractor) ((com.android.internal.os.SomeArgs) message.obj).arg2);
                    break;
                case 155:
                    android.app.Application application = android.app.ActivityThread.this.getApplication();
                    android.app.ActivityThread.handleAttachAgent((java.lang.String) message.obj, application != null ? application.mLoadedApk : null);
                    break;
                case 156:
                    android.app.ActivityThread.this.applyPendingApplicationInfoChanges((java.lang.String) message.obj);
                    break;
                case 158:
                    android.app.ActivityThread.this.handleRunIsolatedEntryPoint((java.lang.String) ((com.android.internal.os.SomeArgs) message.obj).arg1, (java.lang.String[]) ((com.android.internal.os.SomeArgs) message.obj).arg2);
                    break;
                case 159:
                    android.app.servertransaction.ClientTransaction clientTransaction = (android.app.servertransaction.ClientTransaction) message.obj;
                    android.app.ActivityThread.this.mTransactionExecutor.execute(clientTransaction);
                    if (android.app.ActivityThread.isSystem()) {
                        clientTransaction.recycle();
                        break;
                    }
                    break;
                case 160:
                    android.app.ActivityThread.this.handleRelaunchActivityLocally((android.os.IBinder) message.obj);
                    break;
                case 161:
                    android.app.ActivityThread.this.schedulePurgeIdler();
                    break;
                case 162:
                    android.app.ActivityThread.handleAttachStartupAgents((java.lang.String) message.obj);
                    break;
                case 163:
                    com.android.internal.os.SomeArgs someArgs2 = (com.android.internal.os.SomeArgs) message.obj;
                    android.app.ActivityThread.this.updateUiTranslationState((android.os.IBinder) someArgs2.arg1, ((java.lang.Integer) someArgs2.arg2).intValue(), (android.view.translation.TranslationSpec) someArgs2.arg3, (android.view.translation.TranslationSpec) someArgs2.arg4, (java.util.List) someArgs2.arg5, (android.view.translation.UiTranslationSpec) someArgs2.arg6);
                    break;
                case 164:
                    android.app.ActivityThread.this.handleSetContentCaptureOptionsCallback((java.lang.String) message.obj);
                    break;
                case 165:
                    android.app.ActivityThread.this.handleDumpGfxInfo((android.app.ActivityThread.DumpComponentInfo) message.obj);
                    break;
                case 166:
                    android.app.ActivityThread.this.handleDumpResources((android.app.ActivityThread.DumpResourcesData) message.obj);
                    break;
                case 167:
                    if (android.os.Trace.isTagEnabled(64L)) {
                        android.os.Trace.traceBegin(64L, "serviceTimeout: " + java.lang.String.valueOf(message.obj));
                    }
                    android.app.ActivityThread.this.handleTimeoutService((android.os.IBinder) message.obj, message.arg1);
                    android.os.Trace.traceEnd(64L);
                    break;
                case 168:
                    ((android.os.RemoteCallback) message.obj).sendResult(null);
                    break;
                case 170:
                    android.app.ActivityThread.this.handleInstrumentWithoutRestart((android.app.ActivityThread.AppBindData) message.obj);
                    break;
                case 171:
                    android.app.ActivityThread.this.handleFinishInstrumentationWithoutRestart();
                    break;
                case 172:
                    if (android.os.Trace.isTagEnabled(64L)) {
                        android.os.Trace.traceBegin(64L, "serviceTimeoutForType: " + message.obj);
                    }
                    android.app.ActivityThread.this.handleTimeoutServiceForType((android.os.IBinder) message.obj, message.arg1, message.arg2);
                    break;
            }
            java.lang.Object obj = message.obj;
            if (obj instanceof com.android.internal.os.SomeArgs) {
                ((com.android.internal.os.SomeArgs) obj).recycle();
            }
        }
    }

    private class Idler implements android.os.MessageQueue.IdleHandler {
        private Idler() {
        }

        @Override // android.os.MessageQueue.IdleHandler
        public final boolean queueIdle() {
            boolean z;
            if (android.app.ActivityThread.this.mBoundApplication != null && android.app.ActivityThread.this.mProfiler.profileFd != null && android.app.ActivityThread.this.mProfiler.autoStopProfiler) {
                z = true;
            } else {
                z = false;
            }
            android.app.ActivityClient activityClient = android.app.ActivityClient.getInstance();
            while (android.app.ActivityThread.this.mNewActivities.size() > 0) {
                android.app.ActivityThread.ActivityClientRecord remove = android.app.ActivityThread.this.mNewActivities.remove(0);
                if (remove.activity != null && !remove.activity.mFinished) {
                    activityClient.activityIdle(remove.token, remove.createdConfig, z);
                    remove.createdConfig = null;
                }
            }
            if (z) {
                android.app.ActivityThread.this.mProfiler.stopProfiling();
            }
            return false;
        }
    }

    final class GcIdler implements android.os.MessageQueue.IdleHandler {
        GcIdler() {
        }

        @Override // android.os.MessageQueue.IdleHandler
        public final boolean queueIdle() {
            android.app.ActivityThread.this.doGcIfNeeded();
            android.app.ActivityThread.this.purgePendingResources();
            return false;
        }
    }

    final class PurgeIdler implements android.os.MessageQueue.IdleHandler {
        PurgeIdler() {
        }

        @Override // android.os.MessageQueue.IdleHandler
        public boolean queueIdle() {
            android.app.ActivityThread.this.purgePendingResources();
            return false;
        }
    }

    public static android.app.ActivityThread currentActivityThread() {
        return sCurrentActivityThread;
    }

    public static boolean isSystem() {
        if (sCurrentActivityThread != null) {
            return sCurrentActivityThread.mSystemThread;
        }
        return false;
    }

    public static java.lang.String currentOpPackageName() {
        android.app.ActivityThread currentActivityThread = currentActivityThread();
        if (currentActivityThread == null || currentActivityThread.getApplication() == null) {
            return null;
        }
        return currentActivityThread.getApplication().getOpPackageName();
    }

    public static android.content.AttributionSource currentAttributionSource() {
        android.app.ActivityThread currentActivityThread = currentActivityThread();
        if (currentActivityThread == null || currentActivityThread.getApplication() == null) {
            return null;
        }
        return currentActivityThread.getApplication().getAttributionSource();
    }

    public static java.lang.String currentPackageName() {
        android.app.ActivityThread currentActivityThread = currentActivityThread();
        if (currentActivityThread == null || currentActivityThread.mBoundApplication == null) {
            return null;
        }
        return currentActivityThread.mBoundApplication.appInfo.packageName;
    }

    public static java.lang.String currentProcessName() {
        android.app.ActivityThread currentActivityThread = currentActivityThread();
        if (currentActivityThread == null || currentActivityThread.mBoundApplication == null) {
            return null;
        }
        return currentActivityThread.mBoundApplication.processName;
    }

    public static android.app.Application currentApplication() {
        android.app.ActivityThread currentActivityThread = currentActivityThread();
        if (currentActivityThread != null) {
            return currentActivityThread.mInitialApplication;
        }
        return null;
    }

    public static android.content.pm.IPackageManager getPackageManager() {
        if (sPackageManager != null) {
            return sPackageManager;
        }
        sPackageManager = android.content.pm.IPackageManager.Stub.asInterface(android.os.ServiceManager.getService("package"));
        return sPackageManager;
    }

    public static android.permission.IPermissionManager getPermissionManager() {
        if (sPermissionManager != null) {
            return sPermissionManager;
        }
        sPermissionManager = android.permission.IPermissionManager.Stub.asInterface(android.os.ServiceManager.getService("permissionmgr"));
        return sPermissionManager;
    }

    android.content.res.Resources getTopLevelResources(java.lang.String str, java.lang.String[] strArr, java.lang.String[] strArr2, java.lang.String[] strArr3, java.lang.String[] strArr4, android.app.LoadedApk loadedApk, android.content.res.Configuration configuration) {
        return this.mResourcesManager.getResources(null, str, strArr, strArr2, strArr3, strArr4, null, configuration, loadedApk.getCompatibilityInfo(), loadedApk.getClassLoader(), null);
    }

    public android.os.Handler getHandler() {
        return this.mH;
    }

    public final android.app.LoadedApk getPackageInfo(java.lang.String str, android.content.res.CompatibilityInfo compatibilityInfo, int i) {
        return getPackageInfo(str, compatibilityInfo, i, android.os.UserHandle.myUserId());
    }

    public final android.app.LoadedApk getPackageInfo(java.lang.String str, android.content.res.CompatibilityInfo compatibilityInfo, int i, int i2) {
        java.lang.ref.WeakReference<android.app.LoadedApk> weakReference;
        boolean z = android.os.UserHandle.myUserId() != i2;
        if (i2 < 0) {
            i2 = android.os.UserHandle.myUserId();
        }
        android.content.pm.ApplicationInfo applicationInfoAsUserCached = android.content.pm.PackageManager.getApplicationInfoAsUserCached(str, 268436480L, i2);
        synchronized (this.mResourcesManager) {
            if (z) {
                weakReference = null;
            } else if ((i & 1) != 0) {
                weakReference = this.mPackages.get(str);
            } else {
                weakReference = this.mResourcePackages.get(str);
            }
            android.app.LoadedApk loadedApk = weakReference != null ? weakReference.get() : null;
            if (applicationInfoAsUserCached != null && loadedApk != null) {
                if (!isLoadedApkResourceDirsUpToDate(loadedApk, applicationInfoAsUserCached)) {
                    java.util.ArrayList arrayList = new java.util.ArrayList();
                    android.app.LoadedApk.makePaths(this, applicationInfoAsUserCached, arrayList);
                    loadedApk.updateApplicationInfo(applicationInfoAsUserCached, arrayList);
                }
                if (loadedApk.isSecurityViolation() && (i & 2) == 0) {
                    throw new java.lang.SecurityException("Requesting code from " + str + " to be run in process " + this.mBoundApplication.processName + "/" + this.mBoundApplication.appInfo.uid);
                }
                return loadedApk;
            }
            if (applicationInfoAsUserCached == null) {
                return null;
            }
            return getPackageInfo(applicationInfoAsUserCached, compatibilityInfo, i);
        }
    }

    public final android.app.LoadedApk getPackageInfo(android.content.pm.ApplicationInfo applicationInfo, android.content.res.CompatibilityInfo compatibilityInfo, int i) {
        boolean z = (i & 1) != 0;
        boolean z2 = z && applicationInfo.uid != 0 && applicationInfo.uid != 1000 && (this.mBoundApplication == null || !android.os.UserHandle.isSameApp(applicationInfo.uid, this.mBoundApplication.appInfo.uid));
        boolean z3 = z && (1073741824 & i) != 0;
        if ((i & 3) == 1 && z2) {
            java.lang.String str = "Requesting code from " + applicationInfo.packageName + " (with uid " + applicationInfo.uid + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END;
            if (this.mBoundApplication != null) {
                str = str + " to be run in process " + this.mBoundApplication.processName + " (with uid " + this.mBoundApplication.appInfo.uid + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END;
            }
            throw new java.lang.SecurityException(str);
        }
        return getPackageInfo(applicationInfo, compatibilityInfo, null, z2, z, z3);
    }

    public final android.app.LoadedApk getPackageInfoNoCheck(android.content.pm.ApplicationInfo applicationInfo, android.content.res.CompatibilityInfo compatibilityInfo) {
        return getPackageInfo(applicationInfo, compatibilityInfo, null, false, true, false);
    }

    @Override // android.app.ClientTransactionHandler
    public android.app.LoadedApk getPackageInfoNoCheck(android.content.pm.ApplicationInfo applicationInfo) {
        return getPackageInfo(applicationInfo, this.mCompatibilityInfo, null, false, true, false);
    }

    public final android.app.LoadedApk peekPackageInfo(java.lang.String str, boolean z) {
        java.lang.ref.WeakReference<android.app.LoadedApk> weakReference;
        android.app.LoadedApk loadedApk;
        synchronized (this.mResourcesManager) {
            if (z) {
                weakReference = this.mPackages.get(str);
            } else {
                weakReference = this.mResourcePackages.get(str);
            }
            loadedApk = weakReference != null ? weakReference.get() : null;
        }
        return loadedApk;
    }

    private android.app.LoadedApk getPackageInfo(android.content.pm.ApplicationInfo applicationInfo, android.content.res.CompatibilityInfo compatibilityInfo, java.lang.ClassLoader classLoader, boolean z, boolean z2, boolean z3) {
        return getPackageInfo(applicationInfo, compatibilityInfo, classLoader, z, z2, z3, android.os.Process.isSdkSandbox());
    }

    private android.app.LoadedApk getPackageInfo(android.content.pm.ApplicationInfo applicationInfo, android.content.res.CompatibilityInfo compatibilityInfo, java.lang.ClassLoader classLoader, boolean z, boolean z2, boolean z3, boolean z4) {
        java.lang.ref.WeakReference<android.app.LoadedApk> weakReference;
        boolean z5 = android.os.UserHandle.myUserId() != android.os.UserHandle.getUserId(applicationInfo.uid);
        synchronized (this.mResourcesManager) {
            try {
                if (z5 || z4) {
                    weakReference = null;
                } else if (z2) {
                    weakReference = this.mPackages.get(applicationInfo.packageName);
                } else {
                    weakReference = this.mResourcePackages.get(applicationInfo.packageName);
                }
                android.app.LoadedApk loadedApk = weakReference != null ? weakReference.get() : null;
                if (loadedApk != null) {
                    if (!isLoadedApkResourceDirsUpToDate(loadedApk, applicationInfo)) {
                        if (loadedApk.getApplicationInfo().createTimestamp > applicationInfo.createTimestamp) {
                            android.util.Slog.w(TAG, "getPackageInfo() called with an older ApplicationInfo than the cached version for package " + applicationInfo.packageName);
                        } else {
                            android.util.Slog.v(TAG, "getPackageInfo() caused update to cached ApplicationInfo for package " + applicationInfo.packageName);
                            java.util.ArrayList arrayList = new java.util.ArrayList();
                            android.app.LoadedApk.makePaths(this, applicationInfo, arrayList);
                            loadedApk.updateApplicationInfo(applicationInfo, arrayList);
                        }
                    }
                    return loadedApk;
                }
                android.app.LoadedApk loadedApk2 = new android.app.LoadedApk(this, applicationInfo, compatibilityInfo, classLoader, z, z2 && (applicationInfo.flags & 4) != 0, z3);
                if (this.mSystemThread && "android".equals(applicationInfo.packageName)) {
                    loadedApk2.installSystemApplicationInfo(applicationInfo, getSystemContext().mPackageInfo.getClassLoader());
                }
                if (!z5 && !z4) {
                    if (z2) {
                        this.mPackages.put(applicationInfo.packageName, new java.lang.ref.WeakReference<>(loadedApk2));
                    } else {
                        this.mResourcePackages.put(applicationInfo.packageName, new java.lang.ref.WeakReference<>(loadedApk2));
                    }
                }
                return loadedApk2;
            } finally {
            }
        }
    }

    private static boolean isLoadedApkResourceDirsUpToDate(android.app.LoadedApk loadedApk, android.content.pm.ApplicationInfo applicationInfo) {
        android.content.res.Resources resources = loadedApk.mResources;
        return (resources == null || resources.getAssets().isUpToDate()) && java.util.Arrays.equals(com.android.internal.util.ArrayUtils.defeatNullable(applicationInfo.resourceDirs), com.android.internal.util.ArrayUtils.defeatNullable(loadedApk.getOverlayDirs())) && java.util.Arrays.equals(com.android.internal.util.ArrayUtils.defeatNullable(applicationInfo.overlayPaths), com.android.internal.util.ArrayUtils.defeatNullable(loadedApk.getOverlayPaths()));
    }

    ActivityThread() {
    }

    public android.app.ActivityThread.ApplicationThread getApplicationThread() {
        return this.mAppThread;
    }

    public android.app.Instrumentation getInstrumentation() {
        return this.mInstrumentation;
    }

    public boolean isProfiling() {
        return (this.mProfiler == null || this.mProfiler.profileFile == null || this.mProfiler.profileFd != null) ? false : true;
    }

    public java.lang.String getProfileFilePath() {
        return this.mProfiler.profileFile;
    }

    public android.os.Looper getLooper() {
        return this.mLooper;
    }

    public java.util.concurrent.Executor getExecutor() {
        return this.mExecutor;
    }

    @Override // android.app.ActivityThreadInternal
    public android.app.Application getApplication() {
        return this.mInitialApplication;
    }

    public java.lang.String getProcessName() {
        return this.mBoundApplication.processName;
    }

    @Override // android.app.ActivityThreadInternal
    public android.app.ContextImpl getSystemContext() {
        android.app.ContextImpl contextImpl;
        synchronized (this) {
            if (this.mSystemContext == null) {
                this.mSystemContext = android.app.ContextImpl.createSystemContext(this);
            }
            contextImpl = this.mSystemContext;
        }
        return contextImpl;
    }

    public android.app.ContextImpl getSystemUiContext() {
        return getSystemUiContext(0);
    }

    public android.app.ContextImpl getSystemUiContext(int i) {
        android.app.ContextImpl contextImpl;
        synchronized (this) {
            if (this.mDisplaySystemUiContexts == null) {
                this.mDisplaySystemUiContexts = new android.util.SparseArray<>();
            }
            contextImpl = this.mDisplaySystemUiContexts.get(i);
            if (contextImpl == null) {
                contextImpl = android.app.ContextImpl.createSystemUiContext(getSystemContext(), i);
                this.mDisplaySystemUiContexts.put(i, contextImpl);
            }
        }
        return contextImpl;
    }

    @Override // android.app.ActivityThreadInternal
    public android.app.ContextImpl getSystemUiContextNoCreate() {
        synchronized (this) {
            if (this.mDisplaySystemUiContexts == null) {
                return null;
            }
            return this.mDisplaySystemUiContexts.get(0);
        }
    }

    void onSystemUiContextCleanup(android.app.ContextImpl contextImpl) {
        synchronized (this) {
            if (this.mDisplaySystemUiContexts == null) {
                return;
            }
            int indexOfValue = this.mDisplaySystemUiContexts.indexOfValue(contextImpl);
            if (indexOfValue >= 0) {
                this.mDisplaySystemUiContexts.removeAt(indexOfValue);
            }
        }
    }

    public void installSystemApplicationInfo(android.content.pm.ApplicationInfo applicationInfo, java.lang.ClassLoader classLoader) {
        synchronized (this) {
            getSystemContext().installSystemApplicationInfo(applicationInfo, classLoader);
            getSystemUiContext().installSystemApplicationInfo(applicationInfo, classLoader);
            this.mProfiler = new android.app.ActivityThread.Profiler();
        }
    }

    void scheduleGcIdler() {
        if (!this.mGcIdlerScheduled) {
            this.mGcIdlerScheduled = true;
            android.os.Looper.myQueue().addIdleHandler(this.mGcIdler);
        }
        this.mH.removeMessages(120);
    }

    void unscheduleGcIdler() {
        if (this.mGcIdlerScheduled) {
            this.mGcIdlerScheduled = false;
            android.os.Looper.myQueue().removeIdleHandler(this.mGcIdler);
        }
        this.mH.removeMessages(120);
    }

    void schedulePurgeIdler() {
        if (!this.mPurgeIdlerScheduled) {
            this.mPurgeIdlerScheduled = true;
            android.os.Looper.myQueue().addIdleHandler(this.mPurgeIdler);
        }
        this.mH.removeMessages(161);
    }

    void unschedulePurgeIdler() {
        if (this.mPurgeIdlerScheduled) {
            this.mPurgeIdlerScheduled = false;
            android.os.Looper.myQueue().removeIdleHandler(this.mPurgeIdler);
        }
        this.mH.removeMessages(161);
    }

    void doGcIfNeeded() {
        doGcIfNeeded("bg");
    }

    void doGcIfNeeded(java.lang.String str) {
        this.mGcIdlerScheduled = false;
        if (com.android.internal.os.BinderInternal.getLastGcTime() + 5000 < android.os.SystemClock.uptimeMillis()) {
            com.android.internal.os.BinderInternal.forceGc(str);
        }
    }

    static void printRow(java.io.PrintWriter printWriter, java.lang.String str, java.lang.Object... objArr) {
        printWriter.println(java.lang.String.format(java.util.Locale.US, str, objArr));
    }

    @dalvik.annotation.optimization.NeverCompile
    public static void dumpMemInfoTable(java.io.PrintWriter printWriter, android.os.Debug.MemoryInfo memoryInfo, boolean z, boolean z2, boolean z3, boolean z4, int i, java.lang.String str, long j, long j2, long j3, long j4, long j5, long j6) {
        java.lang.String str2;
        int i2;
        java.lang.String str3;
        if (z) {
            printWriter.print(4);
            printWriter.print(',');
            printWriter.print(i);
            printWriter.print(',');
            printWriter.print(str);
            printWriter.print(',');
            printWriter.print(j);
            printWriter.print(',');
            printWriter.print(j4);
            printWriter.print(',');
            printWriter.print("N/A,");
            printWriter.print(j + j4);
            printWriter.print(',');
            printWriter.print(j2);
            printWriter.print(',');
            printWriter.print(j5);
            printWriter.print(',');
            printWriter.print("N/A,");
            printWriter.print(j2 + j5);
            printWriter.print(',');
            printWriter.print(j3);
            printWriter.print(',');
            printWriter.print(j6);
            printWriter.print(',');
            printWriter.print("N/A,");
            printWriter.print(j3 + j6);
            printWriter.print(',');
            printWriter.print(memoryInfo.nativePss);
            printWriter.print(',');
            printWriter.print(memoryInfo.dalvikPss);
            printWriter.print(',');
            printWriter.print(memoryInfo.otherPss);
            printWriter.print(',');
            printWriter.print(memoryInfo.getTotalPss());
            printWriter.print(',');
            printWriter.print(memoryInfo.nativeSwappablePss);
            printWriter.print(',');
            printWriter.print(memoryInfo.dalvikSwappablePss);
            printWriter.print(',');
            printWriter.print(memoryInfo.otherSwappablePss);
            printWriter.print(',');
            printWriter.print(memoryInfo.getTotalSwappablePss());
            printWriter.print(',');
            printWriter.print(memoryInfo.nativeSharedDirty);
            printWriter.print(',');
            printWriter.print(memoryInfo.dalvikSharedDirty);
            printWriter.print(',');
            printWriter.print(memoryInfo.otherSharedDirty);
            printWriter.print(',');
            printWriter.print(memoryInfo.getTotalSharedDirty());
            printWriter.print(',');
            printWriter.print(memoryInfo.nativeSharedClean);
            printWriter.print(',');
            printWriter.print(memoryInfo.dalvikSharedClean);
            printWriter.print(',');
            printWriter.print(memoryInfo.otherSharedClean);
            printWriter.print(',');
            printWriter.print(memoryInfo.getTotalSharedClean());
            printWriter.print(',');
            printWriter.print(memoryInfo.nativePrivateDirty);
            printWriter.print(',');
            printWriter.print(memoryInfo.dalvikPrivateDirty);
            printWriter.print(',');
            printWriter.print(memoryInfo.otherPrivateDirty);
            printWriter.print(',');
            printWriter.print(memoryInfo.getTotalPrivateDirty());
            printWriter.print(',');
            printWriter.print(memoryInfo.nativePrivateClean);
            printWriter.print(',');
            printWriter.print(memoryInfo.dalvikPrivateClean);
            printWriter.print(',');
            printWriter.print(memoryInfo.otherPrivateClean);
            printWriter.print(',');
            printWriter.print(memoryInfo.getTotalPrivateClean());
            printWriter.print(',');
            printWriter.print(memoryInfo.nativeSwappedOut);
            printWriter.print(',');
            printWriter.print(memoryInfo.dalvikSwappedOut);
            printWriter.print(',');
            printWriter.print(memoryInfo.otherSwappedOut);
            printWriter.print(',');
            printWriter.print(memoryInfo.getTotalSwappedOut());
            printWriter.print(',');
            if (memoryInfo.hasSwappedOutPss) {
                printWriter.print(memoryInfo.nativeSwappedOutPss);
                printWriter.print(',');
                printWriter.print(memoryInfo.dalvikSwappedOutPss);
                printWriter.print(',');
                printWriter.print(memoryInfo.otherSwappedOutPss);
                printWriter.print(',');
                printWriter.print(memoryInfo.getTotalSwappedOutPss());
                printWriter.print(',');
            } else {
                printWriter.print("N/A,");
                printWriter.print("N/A,");
                printWriter.print("N/A,");
                printWriter.print("N/A,");
            }
            for (int i3 = 0; i3 < 17; i3++) {
                printWriter.print(android.os.Debug.MemoryInfo.getOtherLabel(i3));
                printWriter.print(',');
                printWriter.print(memoryInfo.getOtherPss(i3));
                printWriter.print(',');
                printWriter.print(memoryInfo.getOtherSwappablePss(i3));
                printWriter.print(',');
                printWriter.print(memoryInfo.getOtherSharedDirty(i3));
                printWriter.print(',');
                printWriter.print(memoryInfo.getOtherSharedClean(i3));
                printWriter.print(',');
                printWriter.print(memoryInfo.getOtherPrivateDirty(i3));
                printWriter.print(',');
                printWriter.print(memoryInfo.getOtherPrivateClean(i3));
                printWriter.print(',');
                printWriter.print(memoryInfo.getOtherSwappedOut(i3));
                printWriter.print(',');
                if (memoryInfo.hasSwappedOutPss) {
                    printWriter.print(memoryInfo.getOtherSwappedOutPss(i3));
                    printWriter.print(',');
                } else {
                    printWriter.print("N/A,");
                }
            }
            return;
        }
        if (z4) {
            str2 = " ";
        } else {
            if (z2) {
                printRow(printWriter, HEAP_FULL_COLUMN, "", "Pss", "Pss", "Shared", "Private", "Shared", "Private", memoryInfo.hasSwappedOutPss ? "SwapPss" : "Swap", "Rss", "Heap", "Heap", "Heap");
                printRow(printWriter, HEAP_FULL_COLUMN, "", "Total", "Clean", "Dirty", "Dirty", "Clean", "Clean", "Dirty", "Total", "Size", "Alloc", "Free");
                printRow(printWriter, HEAP_FULL_COLUMN, "", "------", "------", "------", "------", "------", "------", "------", "------", "------", "------", "------");
                printRow(printWriter, HEAP_FULL_COLUMN, "Native Heap", java.lang.Integer.valueOf(memoryInfo.nativePss), java.lang.Integer.valueOf(memoryInfo.nativeSwappablePss), java.lang.Integer.valueOf(memoryInfo.nativeSharedDirty), java.lang.Integer.valueOf(memoryInfo.nativePrivateDirty), java.lang.Integer.valueOf(memoryInfo.nativeSharedClean), java.lang.Integer.valueOf(memoryInfo.nativePrivateClean), java.lang.Integer.valueOf(memoryInfo.hasSwappedOutPss ? memoryInfo.nativeSwappedOutPss : memoryInfo.nativeSwappedOut), java.lang.Integer.valueOf(memoryInfo.nativeRss), java.lang.Long.valueOf(j), java.lang.Long.valueOf(j2), java.lang.Long.valueOf(j3));
                printRow(printWriter, HEAP_FULL_COLUMN, "Dalvik Heap", java.lang.Integer.valueOf(memoryInfo.dalvikPss), java.lang.Integer.valueOf(memoryInfo.dalvikSwappablePss), java.lang.Integer.valueOf(memoryInfo.dalvikSharedDirty), java.lang.Integer.valueOf(memoryInfo.dalvikPrivateDirty), java.lang.Integer.valueOf(memoryInfo.dalvikSharedClean), java.lang.Integer.valueOf(memoryInfo.dalvikPrivateClean), java.lang.Integer.valueOf(memoryInfo.hasSwappedOutPss ? memoryInfo.dalvikSwappedOutPss : memoryInfo.dalvikSwappedOut), java.lang.Integer.valueOf(memoryInfo.dalvikRss), java.lang.Long.valueOf(j4), java.lang.Long.valueOf(j5), java.lang.Long.valueOf(j6));
            } else {
                printRow(printWriter, HEAP_COLUMN, "", "Pss", "Private", "Private", memoryInfo.hasSwappedOutPss ? "SwapPss" : "Swap", "Rss", "Heap", "Heap", "Heap");
                printRow(printWriter, HEAP_COLUMN, "", "Total", "Dirty", "Clean", "Dirty", "Total", "Size", "Alloc", "Free");
                printRow(printWriter, HEAP_COLUMN, "", "------", "------", "------", "------", "------", "------", "------", "------", "------");
                printRow(printWriter, HEAP_COLUMN, "Native Heap", java.lang.Integer.valueOf(memoryInfo.nativePss), java.lang.Integer.valueOf(memoryInfo.nativePrivateDirty), java.lang.Integer.valueOf(memoryInfo.nativePrivateClean), java.lang.Integer.valueOf(memoryInfo.hasSwappedOutPss ? memoryInfo.nativeSwappedOutPss : memoryInfo.nativeSwappedOut), java.lang.Integer.valueOf(memoryInfo.nativeRss), java.lang.Long.valueOf(j), java.lang.Long.valueOf(j2), java.lang.Long.valueOf(j3));
                printRow(printWriter, HEAP_COLUMN, "Dalvik Heap", java.lang.Integer.valueOf(memoryInfo.dalvikPss), java.lang.Integer.valueOf(memoryInfo.dalvikPrivateDirty), java.lang.Integer.valueOf(memoryInfo.dalvikPrivateClean), java.lang.Integer.valueOf(memoryInfo.hasSwappedOutPss ? memoryInfo.dalvikSwappedOutPss : memoryInfo.dalvikSwappedOut), java.lang.Integer.valueOf(memoryInfo.dalvikRss), java.lang.Long.valueOf(j4), java.lang.Long.valueOf(j5), java.lang.Long.valueOf(j6));
            }
            int i4 = memoryInfo.otherPss;
            int i5 = memoryInfo.otherSwappablePss;
            int i6 = memoryInfo.otherSharedDirty;
            int i7 = memoryInfo.otherPrivateDirty;
            int i8 = memoryInfo.otherSharedClean;
            int i9 = memoryInfo.otherPrivateClean;
            int i10 = memoryInfo.otherSwappedOut;
            int i11 = memoryInfo.otherSwappedOutPss;
            int i12 = i5;
            int i13 = memoryInfo.otherRss;
            int i14 = i4;
            while (i2 < 17) {
                int otherPss = memoryInfo.getOtherPss(i2);
                int otherSwappablePss = memoryInfo.getOtherSwappablePss(i2);
                int otherSharedDirty = memoryInfo.getOtherSharedDirty(i2);
                int otherPrivateDirty = memoryInfo.getOtherPrivateDirty(i2);
                int otherSharedClean = memoryInfo.getOtherSharedClean(i2);
                int otherPrivateClean = memoryInfo.getOtherPrivateClean(i2);
                int otherSwappedOut = memoryInfo.getOtherSwappedOut(i2);
                int otherSwappedOutPss = memoryInfo.getOtherSwappedOutPss(i2);
                int otherRss = memoryInfo.getOtherRss(i2);
                if (otherPss == 0 && otherSharedDirty == 0 && otherPrivateDirty == 0 && otherSharedClean == 0 && otherPrivateClean == 0 && otherRss == 0) {
                    i2 = (memoryInfo.hasSwappedOutPss ? otherSwappedOutPss : otherSwappedOut) == 0 ? i2 + 1 : 0;
                }
                if (z2) {
                    printRow(printWriter, HEAP_FULL_COLUMN, android.os.Debug.MemoryInfo.getOtherLabel(i2), java.lang.Integer.valueOf(otherPss), java.lang.Integer.valueOf(otherSwappablePss), java.lang.Integer.valueOf(otherSharedDirty), java.lang.Integer.valueOf(otherPrivateDirty), java.lang.Integer.valueOf(otherSharedClean), java.lang.Integer.valueOf(otherPrivateClean), java.lang.Integer.valueOf(memoryInfo.hasSwappedOutPss ? otherSwappedOutPss : otherSwappedOut), java.lang.Integer.valueOf(otherRss), "", "", "");
                } else {
                    printRow(printWriter, HEAP_COLUMN, android.os.Debug.MemoryInfo.getOtherLabel(i2), java.lang.Integer.valueOf(otherPss), java.lang.Integer.valueOf(otherPrivateDirty), java.lang.Integer.valueOf(otherPrivateClean), java.lang.Integer.valueOf(memoryInfo.hasSwappedOutPss ? otherSwappedOutPss : otherSwappedOut), java.lang.Integer.valueOf(otherRss), "", "", "");
                }
                i14 -= otherPss;
                i12 -= otherSwappablePss;
                i6 -= otherSharedDirty;
                i7 -= otherPrivateDirty;
                i8 -= otherSharedClean;
                i9 -= otherPrivateClean;
                i10 -= otherSwappedOut;
                i11 -= otherSwappedOutPss;
                i13 -= otherRss;
            }
            if (z2) {
                java.lang.Integer valueOf = java.lang.Integer.valueOf(i14);
                java.lang.Integer valueOf2 = java.lang.Integer.valueOf(i12);
                java.lang.Integer valueOf3 = java.lang.Integer.valueOf(i6);
                java.lang.Integer valueOf4 = java.lang.Integer.valueOf(i7);
                java.lang.Integer valueOf5 = java.lang.Integer.valueOf(i8);
                java.lang.Integer valueOf6 = java.lang.Integer.valueOf(i9);
                if (memoryInfo.hasSwappedOutPss) {
                    i10 = i11;
                }
                printRow(printWriter, HEAP_FULL_COLUMN, "Unknown", valueOf, valueOf2, valueOf3, valueOf4, valueOf5, valueOf6, java.lang.Integer.valueOf(i10), java.lang.Integer.valueOf(i13), "", "", "");
                printRow(printWriter, HEAP_FULL_COLUMN, "TOTAL", java.lang.Integer.valueOf(memoryInfo.getTotalPss()), java.lang.Integer.valueOf(memoryInfo.getTotalSwappablePss()), java.lang.Integer.valueOf(memoryInfo.getTotalSharedDirty()), java.lang.Integer.valueOf(memoryInfo.getTotalPrivateDirty()), java.lang.Integer.valueOf(memoryInfo.getTotalSharedClean()), java.lang.Integer.valueOf(memoryInfo.getTotalPrivateClean()), java.lang.Integer.valueOf(memoryInfo.hasSwappedOutPss ? memoryInfo.getTotalSwappedOutPss() : memoryInfo.getTotalSwappedOut()), java.lang.Integer.valueOf(memoryInfo.getTotalRss()), java.lang.Long.valueOf(j + j4), java.lang.Long.valueOf(j2 + j5), java.lang.Long.valueOf(j3 + j6));
                str3 = HEAP_COLUMN;
            } else {
                java.lang.Integer valueOf7 = java.lang.Integer.valueOf(i14);
                java.lang.Integer valueOf8 = java.lang.Integer.valueOf(i7);
                java.lang.Integer valueOf9 = java.lang.Integer.valueOf(i9);
                if (memoryInfo.hasSwappedOutPss) {
                    i10 = i11;
                }
                java.lang.Object[] objArr = {"Unknown", valueOf7, valueOf8, valueOf9, java.lang.Integer.valueOf(i10), java.lang.Integer.valueOf(i13), "", "", ""};
                str3 = HEAP_COLUMN;
                printRow(printWriter, str3, objArr);
                printRow(printWriter, str3, "TOTAL", java.lang.Integer.valueOf(memoryInfo.getTotalPss()), java.lang.Integer.valueOf(memoryInfo.getTotalPrivateDirty()), java.lang.Integer.valueOf(memoryInfo.getTotalPrivateClean()), java.lang.Integer.valueOf(memoryInfo.hasSwappedOutPss ? memoryInfo.getTotalSwappedOutPss() : memoryInfo.getTotalSwappedOut()), java.lang.Integer.valueOf(memoryInfo.getTotalRss()), java.lang.Long.valueOf(j + j4), java.lang.Long.valueOf(j2 + j5), java.lang.Long.valueOf(j3 + j6));
            }
            if (z3) {
                str2 = " ";
                printWriter.println(str2);
                printWriter.println(" Dalvik Details");
                for (int i15 = 17; i15 < 32; i15++) {
                    int otherPss2 = memoryInfo.getOtherPss(i15);
                    int otherSwappablePss2 = memoryInfo.getOtherSwappablePss(i15);
                    int otherSharedDirty2 = memoryInfo.getOtherSharedDirty(i15);
                    int otherPrivateDirty2 = memoryInfo.getOtherPrivateDirty(i15);
                    int otherSharedClean2 = memoryInfo.getOtherSharedClean(i15);
                    int otherPrivateClean2 = memoryInfo.getOtherPrivateClean(i15);
                    int otherSwappedOut2 = memoryInfo.getOtherSwappedOut(i15);
                    int otherSwappedOutPss2 = memoryInfo.getOtherSwappedOutPss(i15);
                    int otherRss2 = memoryInfo.getOtherRss(i15);
                    if (otherPss2 == 0 && otherSharedDirty2 == 0 && otherPrivateDirty2 == 0 && otherSharedClean2 == 0 && otherPrivateClean2 == 0) {
                        if ((memoryInfo.hasSwappedOutPss ? otherSwappedOutPss2 : otherSwappedOut2) == 0) {
                        }
                    }
                    if (z2) {
                        printRow(printWriter, HEAP_FULL_COLUMN, android.os.Debug.MemoryInfo.getOtherLabel(i15), java.lang.Integer.valueOf(otherPss2), java.lang.Integer.valueOf(otherSwappablePss2), java.lang.Integer.valueOf(otherSharedDirty2), java.lang.Integer.valueOf(otherPrivateDirty2), java.lang.Integer.valueOf(otherSharedClean2), java.lang.Integer.valueOf(otherPrivateClean2), java.lang.Integer.valueOf(memoryInfo.hasSwappedOutPss ? otherSwappedOutPss2 : otherSwappedOut2), java.lang.Integer.valueOf(otherRss2), "", "", "");
                    } else {
                        printRow(printWriter, str3, android.os.Debug.MemoryInfo.getOtherLabel(i15), java.lang.Integer.valueOf(otherPss2), java.lang.Integer.valueOf(otherPrivateDirty2), java.lang.Integer.valueOf(otherPrivateClean2), java.lang.Integer.valueOf(memoryInfo.hasSwappedOutPss ? otherSwappedOutPss2 : otherSwappedOut2), java.lang.Integer.valueOf(otherRss2), "", "", "");
                    }
                }
            } else {
                str2 = " ";
            }
        }
        printWriter.println(str2);
        printWriter.println(" App Summary");
        printRow(printWriter, TWO_COUNT_COLUMN_HEADER, "", "Pss(KB)", "", "Rss(KB)");
        printRow(printWriter, TWO_COUNT_COLUMN_HEADER, "", "------", "", "------");
        printRow(printWriter, TWO_COUNT_COLUMNS, "Java Heap:", java.lang.Integer.valueOf(memoryInfo.getSummaryJavaHeap()), "", java.lang.Integer.valueOf(memoryInfo.getSummaryJavaHeapRss()));
        printRow(printWriter, TWO_COUNT_COLUMNS, "Native Heap:", java.lang.Integer.valueOf(memoryInfo.getSummaryNativeHeap()), "", java.lang.Integer.valueOf(memoryInfo.getSummaryNativeHeapRss()));
        printRow(printWriter, TWO_COUNT_COLUMNS, "Code:", java.lang.Integer.valueOf(memoryInfo.getSummaryCode()), "", java.lang.Integer.valueOf(memoryInfo.getSummaryCodeRss()));
        printRow(printWriter, TWO_COUNT_COLUMNS, "Stack:", java.lang.Integer.valueOf(memoryInfo.getSummaryStack()), "", java.lang.Integer.valueOf(memoryInfo.getSummaryStackRss()));
        printRow(printWriter, TWO_COUNT_COLUMNS, "Graphics:", java.lang.Integer.valueOf(memoryInfo.getSummaryGraphics()), "", java.lang.Integer.valueOf(memoryInfo.getSummaryGraphicsRss()));
        printRow(printWriter, ONE_COUNT_COLUMN, "Private Other:", java.lang.Integer.valueOf(memoryInfo.getSummaryPrivateOther()));
        printRow(printWriter, ONE_COUNT_COLUMN, "System:", java.lang.Integer.valueOf(memoryInfo.getSummarySystem()));
        printRow(printWriter, ONE_ALT_COUNT_COLUMN, "Unknown:", "", "", java.lang.Integer.valueOf(memoryInfo.getSummaryUnknownRss()));
        printWriter.println(str2);
        if (memoryInfo.hasSwappedOutPss) {
            printRow(printWriter, THREE_COUNT_COLUMNS, "TOTAL PSS:", java.lang.Integer.valueOf(memoryInfo.getSummaryTotalPss()), "TOTAL RSS:", java.lang.Integer.valueOf(memoryInfo.getTotalRss()), "TOTAL SWAP PSS:", java.lang.Integer.valueOf(memoryInfo.getSummaryTotalSwapPss()));
        } else {
            printRow(printWriter, THREE_COUNT_COLUMNS, "TOTAL PSS:", java.lang.Integer.valueOf(memoryInfo.getSummaryTotalPss()), "TOTAL RSS:", java.lang.Integer.valueOf(memoryInfo.getTotalRss()), "TOTAL SWAP (KB):", java.lang.Integer.valueOf(memoryInfo.getSummaryTotalSwap()));
        }
    }

    private static void dumpMemoryInfo(android.util.proto.ProtoOutputStream protoOutputStream, long j, java.lang.String str, int i, int i2, int i3, int i4, int i5, int i6, boolean z, int i7, int i8, int i9) {
        long start = protoOutputStream.start(j);
        protoOutputStream.write(1138166333441L, str);
        protoOutputStream.write(1120986464258L, i);
        protoOutputStream.write(1120986464259L, i2);
        protoOutputStream.write(1120986464260L, i3);
        protoOutputStream.write(1120986464261L, i4);
        protoOutputStream.write(1120986464262L, i5);
        protoOutputStream.write(1120986464263L, i6);
        if (z) {
            protoOutputStream.write(1120986464265L, i8);
        } else {
            protoOutputStream.write(1120986464264L, i7);
        }
        protoOutputStream.write(1120986464266L, i9);
        protoOutputStream.end(start);
    }

    @dalvik.annotation.optimization.NeverCompile
    public static void dumpMemInfoTable(android.util.proto.ProtoOutputStream protoOutputStream, android.os.Debug.MemoryInfo memoryInfo, boolean z, boolean z2, long j, long j2, long j3, long j4, long j5, long j6) {
        long j7;
        long j8;
        int i;
        if (!z2) {
            long start = protoOutputStream.start(1146756268035L);
            dumpMemoryInfo(protoOutputStream, 1146756268033L, "Native Heap", memoryInfo.nativePss, memoryInfo.nativeSwappablePss, memoryInfo.nativeSharedDirty, memoryInfo.nativePrivateDirty, memoryInfo.nativeSharedClean, memoryInfo.nativePrivateClean, memoryInfo.hasSwappedOutPss, memoryInfo.nativeSwappedOut, memoryInfo.nativeSwappedOutPss, memoryInfo.nativeRss);
            protoOutputStream.write(1120986464258L, j);
            protoOutputStream.write(1120986464259L, j2);
            protoOutputStream.write(1120986464260L, j3);
            protoOutputStream.end(start);
            long start2 = protoOutputStream.start(1146756268036L);
            dumpMemoryInfo(protoOutputStream, 1146756268033L, "Dalvik Heap", memoryInfo.dalvikPss, memoryInfo.dalvikSwappablePss, memoryInfo.dalvikSharedDirty, memoryInfo.dalvikPrivateDirty, memoryInfo.dalvikSharedClean, memoryInfo.dalvikPrivateClean, memoryInfo.hasSwappedOutPss, memoryInfo.dalvikSwappedOut, memoryInfo.dalvikSwappedOutPss, memoryInfo.dalvikRss);
            protoOutputStream.write(1120986464258L, j4);
            protoOutputStream.write(1120986464259L, j5);
            protoOutputStream.write(1120986464260L, j6);
            protoOutputStream.end(start2);
            int i2 = memoryInfo.otherPss;
            int i3 = memoryInfo.otherSwappablePss;
            int i4 = memoryInfo.otherSharedDirty;
            int i5 = memoryInfo.otherPrivateDirty;
            int i6 = memoryInfo.otherSharedClean;
            int i7 = memoryInfo.otherPrivateClean;
            int i8 = memoryInfo.otherSwappedOut;
            int i9 = memoryInfo.otherSwappedOutPss;
            int i10 = i2;
            int i11 = memoryInfo.otherRss;
            int i12 = i4;
            int i13 = i5;
            int i14 = i6;
            int i15 = i7;
            int i16 = i8;
            int i17 = i9;
            int i18 = 0;
            int i19 = i3;
            while (i18 < 17) {
                int otherPss = memoryInfo.getOtherPss(i18);
                int otherSwappablePss = memoryInfo.getOtherSwappablePss(i18);
                int otherSharedDirty = memoryInfo.getOtherSharedDirty(i18);
                int otherPrivateDirty = memoryInfo.getOtherPrivateDirty(i18);
                int otherSharedClean = memoryInfo.getOtherSharedClean(i18);
                int otherPrivateClean = memoryInfo.getOtherPrivateClean(i18);
                int otherSwappedOut = memoryInfo.getOtherSwappedOut(i18);
                int otherSwappedOutPss = memoryInfo.getOtherSwappedOutPss(i18);
                int otherRss = memoryInfo.getOtherRss(i18);
                if (otherPss == 0 && otherSharedDirty == 0 && otherPrivateDirty == 0 && otherSharedClean == 0 && otherPrivateClean == 0 && otherRss == 0) {
                    if ((memoryInfo.hasSwappedOutPss ? otherSwappedOutPss : otherSwappedOut) == 0) {
                        i = i18;
                        i18 = i + 1;
                    }
                }
                i = i18;
                dumpMemoryInfo(protoOutputStream, 2246267895813L, android.os.Debug.MemoryInfo.getOtherLabel(i18), otherPss, otherSwappablePss, otherSharedDirty, otherPrivateDirty, otherSharedClean, otherPrivateClean, memoryInfo.hasSwappedOutPss, otherSwappedOut, otherSwappedOutPss, otherRss);
                i10 -= otherPss;
                i19 -= otherSwappablePss;
                i12 -= otherSharedDirty;
                i13 -= otherPrivateDirty;
                i14 -= otherSharedClean;
                i15 -= otherPrivateClean;
                i16 -= otherSwappedOut;
                i17 -= otherSwappedOutPss;
                i11 -= otherRss;
                i18 = i + 1;
            }
            dumpMemoryInfo(protoOutputStream, 1146756268038L, "Unknown", i10, i19, i12, i13, i14, i15, memoryInfo.hasSwappedOutPss, i16, i17, i11);
            long start3 = protoOutputStream.start(1146756268039L);
            dumpMemoryInfo(protoOutputStream, 1146756268033L, "TOTAL", memoryInfo.getTotalPss(), memoryInfo.getTotalSwappablePss(), memoryInfo.getTotalSharedDirty(), memoryInfo.getTotalPrivateDirty(), memoryInfo.getTotalSharedClean(), memoryInfo.getTotalPrivateClean(), memoryInfo.hasSwappedOutPss, memoryInfo.getTotalSwappedOut(), memoryInfo.getTotalSwappedOutPss(), memoryInfo.getTotalRss());
            j7 = 1120986464258L;
            protoOutputStream.write(1120986464258L, j + j4);
            j8 = 1120986464259L;
            protoOutputStream.write(1120986464259L, j2 + j5);
            protoOutputStream.write(1120986464260L, j3 + j6);
            protoOutputStream.end(start3);
            if (z) {
                for (int i20 = 17; i20 < 32; i20++) {
                    int otherPss2 = memoryInfo.getOtherPss(i20);
                    int otherSwappablePss2 = memoryInfo.getOtherSwappablePss(i20);
                    int otherSharedDirty2 = memoryInfo.getOtherSharedDirty(i20);
                    int otherPrivateDirty2 = memoryInfo.getOtherPrivateDirty(i20);
                    int otherSharedClean2 = memoryInfo.getOtherSharedClean(i20);
                    int otherPrivateClean2 = memoryInfo.getOtherPrivateClean(i20);
                    int otherSwappedOut2 = memoryInfo.getOtherSwappedOut(i20);
                    int otherSwappedOutPss2 = memoryInfo.getOtherSwappedOutPss(i20);
                    int otherRss2 = memoryInfo.getOtherRss(i20);
                    if (otherPss2 == 0 && otherSharedDirty2 == 0 && otherPrivateDirty2 == 0 && otherSharedClean2 == 0 && otherPrivateClean2 == 0) {
                        if ((memoryInfo.hasSwappedOutPss ? otherSwappedOutPss2 : otherSwappedOut2) == 0) {
                        }
                    }
                    dumpMemoryInfo(protoOutputStream, 2246267895816L, android.os.Debug.MemoryInfo.getOtherLabel(i20), otherPss2, otherSwappablePss2, otherSharedDirty2, otherPrivateDirty2, otherSharedClean2, otherPrivateClean2, memoryInfo.hasSwappedOutPss, otherSwappedOut2, otherSwappedOutPss2, otherRss2);
                }
            }
        } else {
            j7 = 1120986464258L;
            j8 = 1120986464259L;
        }
        long start4 = protoOutputStream.start(1146756268041L);
        protoOutputStream.write(1120986464257L, memoryInfo.getSummaryJavaHeap());
        protoOutputStream.write(j7, memoryInfo.getSummaryNativeHeap());
        protoOutputStream.write(j8, memoryInfo.getSummaryCode());
        protoOutputStream.write(1120986464260L, memoryInfo.getSummaryStack());
        protoOutputStream.write(1120986464261L, memoryInfo.getSummaryGraphics());
        protoOutputStream.write(1120986464262L, memoryInfo.getSummaryPrivateOther());
        protoOutputStream.write(1120986464263L, memoryInfo.getSummarySystem());
        if (memoryInfo.hasSwappedOutPss) {
            protoOutputStream.write(1120986464264L, memoryInfo.getSummaryTotalSwapPss());
        } else {
            protoOutputStream.write(1120986464264L, memoryInfo.getSummaryTotalSwap());
        }
        protoOutputStream.write(1120986464266L, memoryInfo.getSummaryJavaHeapRss());
        protoOutputStream.write(1120986464267L, memoryInfo.getSummaryNativeHeapRss());
        protoOutputStream.write(1120986464268L, memoryInfo.getSummaryCodeRss());
        protoOutputStream.write(1120986464269L, memoryInfo.getSummaryStackRss());
        protoOutputStream.write(1120986464270L, memoryInfo.getSummaryGraphicsRss());
        protoOutputStream.write(1120986464271L, memoryInfo.getSummaryUnknownRss());
        protoOutputStream.end(start4);
    }

    public void registerOnActivityPausedListener(android.app.Activity activity, android.app.OnActivityPausedListener onActivityPausedListener) {
        synchronized (this.mOnPauseListeners) {
            this.mOnPauseListeners.computeIfAbsent(activity, new java.util.function.Function() { // from class: android.app.ActivityThread$$ExternalSyntheticLambda4
                @Override // java.util.function.Function
                public final java.lang.Object apply(java.lang.Object obj) {
                    return android.app.ActivityThread.lambda$registerOnActivityPausedListener$0((android.app.Activity) obj);
                }
            }).add(onActivityPausedListener);
        }
    }

    static /* synthetic */ java.util.ArrayList lambda$registerOnActivityPausedListener$0(android.app.Activity activity) {
        return new java.util.ArrayList();
    }

    public void unregisterOnActivityPausedListener(android.app.Activity activity, android.app.OnActivityPausedListener onActivityPausedListener) {
        synchronized (this.mOnPauseListeners) {
            java.util.ArrayList<android.app.OnActivityPausedListener> arrayList = this.mOnPauseListeners.get(activity);
            if (arrayList != null) {
                arrayList.remove(onActivityPausedListener);
            }
        }
    }

    public final android.content.pm.ActivityInfo resolveActivityInfo(android.content.Intent intent) {
        android.content.pm.ActivityInfo resolveActivityInfo = intent.resolveActivityInfo(this.mInitialApplication.getPackageManager(), 1024);
        if (resolveActivityInfo == null) {
            android.app.Instrumentation.checkStartActivityResult(-92, intent);
        }
        return resolveActivityInfo;
    }

    public final android.app.Activity startActivityNow(android.app.Activity activity, java.lang.String str, android.content.Intent intent, android.content.pm.ActivityInfo activityInfo, android.os.IBinder iBinder, android.os.Bundle bundle, android.app.Activity.NonConfigurationInstances nonConfigurationInstances, android.os.IBinder iBinder2, android.os.IBinder iBinder3) {
        android.app.ActivityThread.ActivityClientRecord activityClientRecord = new android.app.ActivityThread.ActivityClientRecord();
        activityClientRecord.token = iBinder;
        activityClientRecord.assistToken = iBinder2;
        activityClientRecord.shareableActivityToken = iBinder3;
        activityClientRecord.ident = 0;
        activityClientRecord.intent = intent;
        activityClientRecord.state = bundle;
        activityClientRecord.parent = activity;
        activityClientRecord.embeddedID = str;
        activityClientRecord.activityInfo = activityInfo;
        activityClientRecord.lastNonConfigurationInstances = nonConfigurationInstances;
        return performLaunchActivity(activityClientRecord, null);
    }

    @Override // android.app.ClientTransactionHandler
    public final android.app.Activity getActivity(android.os.IBinder iBinder) {
        android.app.ActivityThread.ActivityClientRecord activityClientRecord = this.mActivities.get(iBinder);
        if (activityClientRecord != null) {
            return activityClientRecord.activity;
        }
        return null;
    }

    @Override // android.app.ClientTransactionHandler
    public android.app.ActivityThread.ActivityClientRecord getActivityClient(android.os.IBinder iBinder) {
        return this.mActivities.get(iBinder);
    }

    @Override // android.app.ClientTransactionHandler
    public android.content.Context getWindowContext(android.os.IBinder iBinder) {
        return android.window.WindowTokenClientController.getInstance().getWindowContext(iBinder);
    }

    public android.content.res.Configuration getConfiguration() {
        return this.mConfigurationController.getConfiguration();
    }

    public void addConfigurationChangedListener(java.util.concurrent.Executor executor, java.util.function.Consumer<android.os.IBinder> consumer) {
        this.mConfigurationChangedListenerController.addListener(executor, consumer);
    }

    public void removeConfigurationChangedListener(java.util.function.Consumer<android.os.IBinder> consumer) {
        this.mConfigurationChangedListenerController.removeListener(consumer);
    }

    @Override // android.app.ClientTransactionHandler
    public void updatePendingConfiguration(android.content.res.Configuration configuration) {
        android.content.res.Configuration updatePendingConfiguration = this.mConfigurationController.updatePendingConfiguration(configuration);
        if (updatePendingConfiguration != null) {
            this.mPendingConfiguration = updatePendingConfiguration;
        }
    }

    @Override // android.app.ClientTransactionHandler
    public void updateProcessState(int i, boolean z) {
        synchronized (this.mAppThread) {
            if (this.mLastProcessState == i) {
                return;
            }
            if (android.app.ActivityManager.isProcStateCached(this.mLastProcessState) != android.app.ActivityManager.isProcStateCached(i)) {
                updateVmProcessState(i);
            }
            this.mLastProcessState = i;
        }
    }

    private void updateVmProcessState(int i) {
        int i2;
        if (android.app.ActivityManager.isProcStateCached(i)) {
            i2 = 1;
        } else {
            i2 = 0;
        }
        dalvik.system.VMRuntime.getRuntime().updateProcessState(i2);
    }

    @Override // android.app.ClientTransactionHandler
    public void countLaunchingActivities(int i) {
        this.mNumLaunchingActivities.getAndAdd(i);
    }

    public void sendActivityResult(android.os.IBinder iBinder, java.lang.String str, int i, int i2, android.content.Intent intent) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        arrayList.add(new android.app.ResultInfo(str, i, i2, intent, iBinder));
        android.app.servertransaction.ClientTransaction obtain = android.app.servertransaction.ClientTransaction.obtain(this.mAppThread);
        obtain.addTransactionItem(android.app.servertransaction.ActivityResultItem.obtain(iBinder, arrayList));
        try {
            this.mAppThread.scheduleTransaction(obtain);
        } catch (android.os.RemoteException e) {
        }
    }

    @Override // android.app.ClientTransactionHandler
    android.app.servertransaction.TransactionExecutor getTransactionExecutor() {
        return this.mTransactionExecutor;
    }

    @Override // android.app.ClientTransactionHandler
    void sendMessage(int i, java.lang.Object obj) {
        sendMessage(i, obj, 0, 0, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendMessage(int i, java.lang.Object obj, int i2) {
        sendMessage(i, obj, i2, 0, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendMessage(int i, java.lang.Object obj, int i2, int i3) {
        sendMessage(i, obj, i2, i3, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendMessage(int i, java.lang.Object obj, int i2, int i3, boolean z) {
        android.os.Message obtain = android.os.Message.obtain();
        obtain.what = i;
        obtain.obj = obj;
        obtain.arg1 = i2;
        obtain.arg2 = i3;
        if (z) {
            obtain.setAsynchronous(true);
        }
        this.mH.sendMessage(obtain);
    }

    final void scheduleContextCleanup(android.app.ContextImpl contextImpl, java.lang.String str, java.lang.String str2) {
        android.app.ActivityThread.ContextCleanupInfo contextCleanupInfo = new android.app.ActivityThread.ContextCleanupInfo();
        contextCleanupInfo.context = contextImpl;
        contextCleanupInfo.who = str;
        contextCleanupInfo.what = str2;
        sendMessage(119, contextCleanupInfo);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:41:0x010c A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r0v34, types: [android.app.Instrumentation] */
    /* JADX WARN: Type inference failed for: r0v40, types: [android.app.Instrumentation] */
    /* JADX WARN: Type inference failed for: r1v22, types: [android.app.Activity] */
    /* JADX WARN: Type inference failed for: r1v8, types: [android.app.Instrumentation] */
    /* JADX WARN: Type inference failed for: r2v0 */
    /* JADX WARN: Type inference failed for: r2v1, types: [android.app.ContextImpl, android.content.Context] */
    /* JADX WARN: Type inference failed for: r2v20 */
    /* JADX WARN: Type inference failed for: r2v21 */
    /* JADX WARN: Type inference failed for: r2v22 */
    /* JADX WARN: Type inference failed for: r2v23 */
    /* JADX WARN: Type inference failed for: r2v24, types: [android.app.Activity] */
    /* JADX WARN: Type inference failed for: r2v25, types: [android.app.Activity] */
    /* JADX WARN: Type inference failed for: r2v26 */
    /* JADX WARN: Type inference failed for: r2v27 */
    /* JADX WARN: Type inference failed for: r2v30 */
    /* JADX WARN: Type inference failed for: r2v8 */
    /* JADX WARN: Type inference failed for: r2v9, types: [java.lang.Object] */
    /* JADX WARN: Type inference failed for: r3v1, types: [boolean] */
    /* JADX WARN: Type inference failed for: r3v31, types: [android.os.Bundle] */
    /* JADX WARN: Type inference failed for: r3v33, types: [android.os.Bundle] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private android.app.Activity performLaunchActivity(android.app.ActivityThread.ActivityClientRecord activityClientRecord, android.content.Intent intent) {
        android.content.ComponentName componentName;
        ?? createBaseContextForActivity;
        boolean z;
        android.app.Activity activity;
        android.app.Activity activity2;
        android.app.ActivityThread activityThread;
        android.content.ComponentName componentName2;
        android.app.Activity activity3;
        android.app.Activity activity4;
        android.app.ActivityThread.ActivityClientRecord activityClientRecord2;
        android.view.Window window;
        java.lang.ClassLoader classLoader;
        android.content.pm.ActivityInfo activityInfo = activityClientRecord.activityInfo;
        if (getInstrumentation() != null && getInstrumentation().getContext() != null && getInstrumentation().getContext().getApplicationInfo() != null && getInstrumentation().isSdkSandboxAllowedToStartActivities()) {
            activityClientRecord.packageInfo = getPackageInfo(getInstrumentation().getContext().getApplicationInfo(), this.mCompatibilityInfo, 1);
        } else if (activityClientRecord.packageInfo == null) {
            activityClientRecord.packageInfo = getPackageInfo(activityInfo.applicationInfo, this.mCompatibilityInfo, 1);
        }
        android.content.ComponentName component = activityClientRecord.intent.getComponent();
        if (component == null) {
            component = activityClientRecord.intent.resolveActivity(this.mInitialApplication.getPackageManager());
            activityClientRecord.intent.setComponent(component);
        }
        if (activityClientRecord.activityInfo.targetActivity == null) {
            componentName = component;
        } else {
            componentName = new android.content.ComponentName(activityClientRecord.activityInfo.packageName, activityClientRecord.activityInfo.targetActivity);
        }
        if (com.android.sdksandbox.flags.Flags.sandboxActivitySdkBasedContext() && android.app.sdksandbox.sandboxactivity.SdkSandboxActivityAuthority.isSdkSandboxActivityIntent(this.mSystemContext, activityClientRecord.intent)) {
            android.app.ContextImpl createBaseContextForSandboxActivity = createBaseContextForSandboxActivity(activityClientRecord);
            if (createBaseContextForSandboxActivity == null) {
                createBaseContextForActivity = createBaseContextForActivity(activityClientRecord);
                z = false;
            } else {
                createBaseContextForActivity = createBaseContextForSandboxActivity;
                z = true;
            }
        } else {
            createBaseContextForActivity = createBaseContextForActivity(activityClientRecord);
            z = false;
        }
        try {
            if (z) {
                classLoader = createBaseContextForActivity.getApplicationContext().getClassLoader();
            } else {
                classLoader = createBaseContextForActivity.getClassLoader();
            }
            activity = this.mInstrumentation.newActivity(classLoader, componentName.getClassName(), activityClientRecord.intent);
            try {
                android.os.StrictMode.incrementExpectedActivityCount(activity.getClass());
                activityClientRecord.intent.setExtrasClassLoader(classLoader);
                activityClientRecord.intent.prepareToEnterProcess(isProtectedComponent(activityClientRecord.activityInfo), createBaseContextForActivity.getAttributionSource());
                ?? r3 = activityClientRecord.state;
                android.app.ActivityThread activityThread2 = r3;
                if (r3 != 0) {
                    ?? r32 = activityClientRecord.state;
                    r32.setClassLoader(classLoader);
                    activityThread2 = r32;
                }
                activity2 = activity;
                activityThread = activityThread2;
            } catch (java.lang.Exception e) {
                e = e;
                ?? onException = this.mInstrumentation.onException(activity, e);
                if (onException != 0) {
                    activity2 = activity;
                    activityThread = onException;
                    try {
                        android.app.Application makeApplicationInner = activityClientRecord.packageInfo.makeApplicationInner(false, this.mInstrumentation);
                        try {
                            synchronized (this.mResourcesManager) {
                            }
                        } catch (java.lang.Exception e2) {
                            e = e2;
                        }
                    } catch (android.util.SuperNotCalledException e3) {
                        throw e3;
                    }
                } else {
                    throw new java.lang.RuntimeException("Unable to instantiate activity " + componentName + ": " + e.toString(), e);
                }
            }
        } catch (java.lang.Exception e4) {
            e = e4;
            activity = null;
        }
        try {
            android.app.Application makeApplicationInner2 = activityClientRecord.packageInfo.makeApplicationInner(false, this.mInstrumentation);
            synchronized (this.mResourcesManager) {
                try {
                    this.mActivities.put(activityClientRecord.token, activityClientRecord);
                } finally {
                    th = th;
                    android.app.Activity activity5 = activity2;
                    android.content.ComponentName componentName3 = componentName;
                    while (true) {
                        try {
                        } catch (java.lang.Throwable th) {
                            th = th;
                        }
                    }
                }
            }
            if (activity2 == null) {
                activity4 = activity2;
                activityClientRecord2 = activityClientRecord;
            } else {
                java.lang.CharSequence loadLabel = activityClientRecord.activityInfo.loadLabel(createBaseContextForActivity.getPackageManager());
                android.content.res.Configuration configuration = new android.content.res.Configuration(this.mConfigurationController.getCompatConfiguration());
                if (activityClientRecord.overrideConfig != null) {
                    configuration.updateFrom(activityClientRecord.overrideConfig);
                }
                if (activityClientRecord.mPendingRemoveWindow != null && activityClientRecord.mPreserveWindow) {
                    android.view.Window window2 = activityClientRecord.mPendingRemoveWindow;
                    activityClientRecord.mPendingRemoveWindow = null;
                    activityClientRecord.mPendingRemoveWindowManager = null;
                    window = window2;
                } else {
                    window = null;
                }
                createBaseContextForActivity.getResources().addLoaders((android.content.res.loader.ResourcesLoader[]) makeApplicationInner2.getResources().getLoaders().toArray(new android.content.res.loader.ResourcesLoader[0]));
                createBaseContextForActivity.setOuterContext(activity2);
                try {
                    try {
                        activity2.attach(createBaseContextForActivity, this, getInstrumentation(), activityClientRecord.token, activityClientRecord.ident, makeApplicationInner2, activityClientRecord.intent, activityClientRecord.activityInfo, loadLabel, activityClientRecord.parent, activityClientRecord.embeddedID, activityClientRecord.lastNonConfigurationInstances, configuration, activityClientRecord.referrer, activityClientRecord.voiceInteractor, window, activityClientRecord.activityConfigCallback, activityClientRecord.assistToken, activityClientRecord.shareableActivityToken, activityClientRecord.initialCallerInfoAccessToken);
                        if (intent == null) {
                            createBaseContextForActivity = r28;
                        } else {
                            createBaseContextForActivity = r28;
                            try {
                                createBaseContextForActivity.mIntent = intent;
                                createBaseContextForActivity = createBaseContextForActivity;
                            } catch (java.lang.Exception e5) {
                                e = e5;
                                activityThread = this;
                                boolean onException2 = activityThread.mInstrumentation.onException(createBaseContextForActivity, e);
                                activity3 = createBaseContextForActivity;
                                if (!onException2) {
                                    throw new java.lang.RuntimeException("Unable to start activity " + componentName2 + ": " + e.toString(), e);
                                }
                                return activity3;
                            }
                        }
                        activityClientRecord2 = activityClientRecord;
                        activityClientRecord2.lastNonConfigurationInstances = null;
                        checkAndBlockForNetworkAccess();
                        createBaseContextForActivity.mStartedActivity = false;
                        int themeResource = activityClientRecord2.activityInfo.getThemeResource();
                        if (themeResource != 0) {
                            createBaseContextForActivity.setTheme(themeResource);
                        }
                        if (activityClientRecord2.mSceneTransitionInfo != null) {
                            createBaseContextForActivity.mSceneTransitionInfo = activityClientRecord2.mSceneTransitionInfo;
                            activityClientRecord2.mSceneTransitionInfo = null;
                        }
                        createBaseContextForActivity.mLaunchedFromBubble = activityClientRecord2.mLaunchedFromBubble;
                        createBaseContextForActivity.mCalled = false;
                        activityClientRecord2.activity = createBaseContextForActivity;
                        if (activityClientRecord.isPersistable()) {
                            this.mInstrumentation.callActivityOnCreate(createBaseContextForActivity, activityClientRecord2.state, activityClientRecord2.persistentState);
                        } else {
                            this.mInstrumentation.callActivityOnCreate(createBaseContextForActivity, activityClientRecord2.state);
                        }
                        if (!createBaseContextForActivity.mCalled) {
                            throw new android.util.SuperNotCalledException("Activity " + activityClientRecord2.intent.getComponent().toShortString() + " did not call through to super.onCreate()");
                        }
                        activityClientRecord2.mLastReportedWindowingMode = configuration.windowConfiguration.getWindowingMode();
                        activity4 = createBaseContextForActivity;
                    } catch (java.lang.Exception e6) {
                        e = e6;
                        activityThread = this;
                        createBaseContextForActivity = r28;
                    }
                } catch (java.lang.Exception e7) {
                    e = e7;
                    activityThread = this;
                    createBaseContextForActivity = activity2;
                    componentName2 = componentName;
                }
            }
            activityClientRecord2.setState(1);
            activity3 = activity4;
        } catch (java.lang.Exception e8) {
            e = e8;
            createBaseContextForActivity = activity2;
            componentName2 = componentName;
            activityThread = this;
        }
        return activity3;
    }

    @Override // android.app.ClientTransactionHandler
    public void handleStartActivity(android.app.ActivityThread.ActivityClientRecord activityClientRecord, android.app.servertransaction.PendingTransactionActions pendingTransactionActions, android.app.ActivityOptions.SceneTransitionInfo sceneTransitionInfo) {
        android.app.Activity activity = activityClientRecord.activity;
        if (!activityClientRecord.stopped) {
            throw new java.lang.IllegalStateException("Can't start activity that is not stopped.");
        }
        if (activityClientRecord.activity.mFinished) {
            return;
        }
        unscheduleGcIdler();
        if (sceneTransitionInfo != null) {
            activity.mSceneTransitionInfo = sceneTransitionInfo;
        }
        activity.performStart("handleStartActivity");
        activityClientRecord.setState(2);
        if (pendingTransactionActions == null) {
            return;
        }
        if (pendingTransactionActions.shouldRestoreInstanceState()) {
            if (activityClientRecord.isPersistable()) {
                if (activityClientRecord.state != null || activityClientRecord.persistentState != null) {
                    this.mInstrumentation.callActivityOnRestoreInstanceState(activity, activityClientRecord.state, activityClientRecord.persistentState);
                }
            } else if (activityClientRecord.state != null) {
                this.mInstrumentation.callActivityOnRestoreInstanceState(activity, activityClientRecord.state);
            }
        }
        if (pendingTransactionActions.shouldCallOnPostCreate()) {
            activity.mCalled = false;
            android.os.Trace.traceBegin(32L, "onPostCreate");
            if (activityClientRecord.isPersistable()) {
                this.mInstrumentation.callActivityOnPostCreate(activity, activityClientRecord.state, activityClientRecord.persistentState);
            } else {
                this.mInstrumentation.callActivityOnPostCreate(activity, activityClientRecord.state);
            }
            android.os.Trace.traceEnd(32L);
            if (!activity.mCalled) {
                throw new android.util.SuperNotCalledException("Activity " + activityClientRecord.intent.getComponent().toShortString() + " did not call through to super.onPostCreate()");
            }
        }
        updateVisibility(activityClientRecord, true);
        this.mSomeActivitiesChanged = true;
    }

    private void checkAndBlockForNetworkAccess() {
        synchronized (this.mNetworkPolicyLock) {
            if (this.mNetworkBlockSeq != -1) {
                try {
                    android.app.ActivityManager.getService().waitForNetworkStateUpdate(this.mNetworkBlockSeq);
                    this.mNetworkBlockSeq = -1L;
                } catch (android.os.RemoteException e) {
                }
            }
        }
    }

    private android.app.ContextImpl createBaseContextForActivity(android.app.ActivityThread.ActivityClientRecord activityClientRecord) {
        android.app.ContextImpl createActivityContext = android.app.ContextImpl.createActivityContext(this, activityClientRecord.packageInfo, activityClientRecord.activityInfo, activityClientRecord.token, android.app.ActivityClient.getInstance().getDisplayId(activityClientRecord.token), activityClientRecord.overrideConfig);
        android.hardware.display.DisplayManagerGlobal displayManagerGlobal = android.hardware.display.DisplayManagerGlobal.getInstance();
        java.lang.String str = android.os.SystemProperties.get("debug.second-display.pkg");
        if (str != null && !str.isEmpty() && activityClientRecord.packageInfo.mPackageName.contains(str)) {
            for (int i : displayManagerGlobal.getDisplayIds()) {
                if (i != 0) {
                    return (android.app.ContextImpl) createActivityContext.createDisplayContext(displayManagerGlobal.getCompatibleDisplay(i, createActivityContext.getResources()));
                }
            }
            return createActivityContext;
        }
        return createActivityContext;
    }

    private android.app.ContextImpl createBaseContextForSandboxActivity(android.app.ActivityThread.ActivityClientRecord activityClientRecord) {
        try {
            android.app.sdksandbox.sandboxactivity.ActivityContextInfo activityContextInfo = android.app.sdksandbox.sandboxactivity.SdkSandboxActivityAuthority.getInstance().getActivityContextInfo(activityClientRecord.intent);
            android.app.ContextImpl createActivityContext = android.app.ContextImpl.createActivityContext(this, getPackageInfo(activityContextInfo.getSdkApplicationInfo(), activityClientRecord.packageInfo.getCompatibilityInfo(), activityContextInfo.getContextFlags()), activityClientRecord.activityInfo, activityClientRecord.token, android.app.ActivityClient.getInstance().getDisplayId(activityClientRecord.token), activityClientRecord.overrideConfig);
            createActivityContext.mPackageInfo.makeApplicationInner(false, this.mInstrumentation);
            return createActivityContext;
        } catch (java.lang.IllegalArgumentException e) {
            android.util.Log.e(TAG, "Passed intent does not match an expected sandbox activity", e);
            return null;
        } catch (java.lang.IllegalStateException e2) {
            android.util.Log.e(TAG, "SDK customized context flag is disabled", e2);
            return null;
        } catch (java.lang.Exception e3) {
            android.util.Log.e(TAG, "Failed to create context for sandbox activity", e3);
            return null;
        }
    }

    @Override // android.app.ClientTransactionHandler
    public android.app.Activity handleLaunchActivity(android.app.ActivityThread.ActivityClientRecord activityClientRecord, android.app.servertransaction.PendingTransactionActions pendingTransactionActions, int i, android.content.Intent intent) {
        unscheduleGcIdler();
        this.mSomeActivitiesChanged = true;
        if (activityClientRecord.profilerInfo != null) {
            this.mProfiler.setProfiler(activityClientRecord.profilerInfo);
            this.mProfiler.startProfiling();
        }
        applyPendingApplicationInfoChanges(activityClientRecord.activityInfo.packageName);
        this.mConfigurationController.handleConfigurationChanged(null, null);
        updateDeviceIdForNonUIContexts(i);
        if (android.view.ThreadedRenderer.sRendererEnabled && (activityClientRecord.activityInfo.flags & 512) != 0) {
            android.graphics.HardwareRenderer.preload();
        }
        android.view.WindowManagerGlobal.initialize();
        android.os.GraphicsEnvironment.hintActivityLaunch();
        android.app.Activity performLaunchActivity = performLaunchActivity(activityClientRecord, intent);
        if (performLaunchActivity != null) {
            activityClientRecord.createdConfig = new android.content.res.Configuration(this.mConfigurationController.getConfiguration());
            reportSizeConfigurations(activityClientRecord);
            if (!activityClientRecord.activity.mFinished && pendingTransactionActions != null) {
                pendingTransactionActions.setOldState(activityClientRecord.state);
                pendingTransactionActions.setRestoreInstanceState(true);
                pendingTransactionActions.setCallOnPostCreate(true);
            }
        } else {
            android.app.ActivityClient.getInstance().finishActivity(activityClientRecord.token, 0, null, 0);
        }
        return performLaunchActivity;
    }

    private void reportSizeConfigurations(android.app.ActivityThread.ActivityClientRecord activityClientRecord) {
        android.content.res.Configuration[] sizeConfigurations;
        if (this.mActivitiesToBeDestroyed.containsKey(activityClientRecord.token) || (sizeConfigurations = activityClientRecord.activity.getResources().getSizeConfigurations()) == null) {
            return;
        }
        activityClientRecord.mSizeConfigurations = new android.window.SizeConfigurationBuckets(sizeConfigurations);
        android.app.ActivityClient.getInstance().reportSizeConfigurations(activityClientRecord.token, activityClientRecord.mSizeConfigurations);
    }

    private void deliverNewIntents(android.app.ActivityThread.ActivityClientRecord activityClientRecord, java.util.List<com.android.internal.content.ReferrerIntent> list) {
        int size = list.size();
        for (int i = 0; i < size; i++) {
            com.android.internal.content.ReferrerIntent referrerIntent = list.get(i);
            referrerIntent.setExtrasClassLoader(activityClientRecord.activity.getClassLoader());
            referrerIntent.prepareToEnterProcess(isProtectedComponent(activityClientRecord.activityInfo), activityClientRecord.activity.getAttributionSource());
            activityClientRecord.activity.mFragments.noteStateNotSaved();
            if (android.security.Flags.contentUriPermissionApis()) {
                this.mInstrumentation.callActivityOnNewIntent(activityClientRecord.activity, referrerIntent, new android.app.ComponentCaller(activityClientRecord.token, referrerIntent.mCallerToken));
            } else {
                this.mInstrumentation.callActivityOnNewIntent(activityClientRecord.activity, referrerIntent);
            }
        }
    }

    @Override // android.app.ClientTransactionHandler
    public void handleNewIntent(android.app.ActivityThread.ActivityClientRecord activityClientRecord, java.util.List<com.android.internal.content.ReferrerIntent> list) {
        checkAndBlockForNetworkAccess();
        deliverNewIntents(activityClientRecord, list);
    }

    public void handleRequestAssistContextExtras(android.app.ActivityThread.RequestAssistContextExtras requestAssistContextExtras) {
        android.net.Uri uri;
        android.app.assist.AssistStructure assistStructure;
        boolean z = requestAssistContextExtras.requestType == 2;
        boolean z2 = requestAssistContextExtras.requestType == 3;
        if (this.mLastSessionId != requestAssistContextExtras.sessionId) {
            this.mLastSessionId = requestAssistContextExtras.sessionId;
            for (int size = this.mLastAssistStructures.size() - 1; size >= 0; size--) {
                android.app.assist.AssistStructure assistStructure2 = this.mLastAssistStructures.get(size).get();
                if (assistStructure2 != null) {
                    assistStructure2.clearSendChannel();
                }
                this.mLastAssistStructures.remove(size);
            }
        }
        android.os.Bundle bundle = new android.os.Bundle();
        android.app.assist.AssistStructure assistStructure3 = null;
        android.app.assist.AssistContent assistContent = z ? null : new android.app.assist.AssistContent();
        long uptimeMillis = android.os.SystemClock.uptimeMillis();
        android.app.ActivityThread.ActivityClientRecord activityClientRecord = this.mActivities.get(requestAssistContextExtras.activityToken);
        if (activityClientRecord == null) {
            uri = null;
        } else {
            if (z) {
                uri = null;
            } else {
                activityClientRecord.activity.getApplication().dispatchOnProvideAssistData(activityClientRecord.activity, bundle);
                activityClientRecord.activity.onProvideAssistData(bundle);
                uri = activityClientRecord.activity.onProvideReferrer();
            }
            if (requestAssistContextExtras.requestType == 1 || z || z2) {
                if (!z2) {
                    assistStructure3 = new android.app.assist.AssistStructure(activityClientRecord.activity, z, requestAssistContextExtras.flags);
                }
                android.content.Intent intent = activityClientRecord.activity.getIntent();
                boolean z3 = activityClientRecord.window == null || (activityClientRecord.window.getAttributes().flags & 8192) == 0;
                if (intent != null && z3) {
                    if (!z) {
                        android.content.Intent intent2 = new android.content.Intent(intent);
                        intent2.setFlags(intent2.getFlags() & (-67));
                        assistContent.setDefaultIntent(intent2);
                    }
                } else if (!z) {
                    assistContent.setDefaultIntent(new android.content.Intent());
                }
                if (!z) {
                    activityClientRecord.activity.onProvideAssistContent(assistContent);
                }
            }
        }
        if (z2) {
            assistStructure = assistStructure3;
        } else {
            if (assistStructure3 == null) {
                assistStructure3 = new android.app.assist.AssistStructure();
            }
            assistStructure3.setAcquisitionStartTime(uptimeMillis);
            assistStructure3.setAcquisitionEndTime(android.os.SystemClock.uptimeMillis());
            this.mLastAssistStructures.add(new java.lang.ref.WeakReference<>(assistStructure3));
            assistStructure = assistStructure3;
        }
        try {
            android.app.ActivityTaskManager.getService().reportAssistContextExtras(requestAssistContextExtras.requestToken, bundle, assistStructure, assistContent, uri);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleRequestDirectActions(android.os.IBinder iBinder, com.android.internal.app.IVoiceInteractor iVoiceInteractor, android.os.CancellationSignal cancellationSignal, final android.os.RemoteCallback remoteCallback, int i) {
        final android.app.ActivityThread.ActivityClientRecord activityClientRecord = this.mActivities.get(iBinder);
        if (activityClientRecord == null) {
            android.util.Log.w(TAG, "requestDirectActions(): no activity for " + iBinder);
            remoteCallback.sendResult(null);
            return;
        }
        int lifecycleState = activityClientRecord.getLifecycleState();
        if (lifecycleState < 2) {
            if (i > 0) {
                this.mH.sendMessageDelayed(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new com.android.internal.util.function.HexConsumer() { // from class: android.app.ActivityThread$$ExternalSyntheticLambda5
                    @Override // com.android.internal.util.function.HexConsumer
                    public final void accept(java.lang.Object obj, java.lang.Object obj2, java.lang.Object obj3, java.lang.Object obj4, java.lang.Object obj5, java.lang.Object obj6) {
                        ((android.app.ActivityThread) obj).handleRequestDirectActions((android.os.IBinder) obj2, (com.android.internal.app.IVoiceInteractor) obj3, (android.os.CancellationSignal) obj4, (android.os.RemoteCallback) obj5, ((java.lang.Integer) obj6).intValue());
                    }
                }, this, iBinder, iVoiceInteractor, cancellationSignal, remoteCallback, java.lang.Integer.valueOf(i - 1)), REQUEST_DIRECT_ACTIONS_RETRY_TIME_MS);
                return;
            } else {
                android.util.Log.w(TAG, "requestDirectActions(" + activityClientRecord + "): wrong lifecycle: " + lifecycleState);
                remoteCallback.sendResult(null);
                return;
            }
        }
        if (lifecycleState >= 5) {
            android.util.Log.w(TAG, "requestDirectActions(" + activityClientRecord + "): wrong lifecycle: " + lifecycleState);
            remoteCallback.sendResult(null);
            return;
        }
        if (activityClientRecord.activity.mVoiceInteractor == null || activityClientRecord.activity.mVoiceInteractor.mInteractor.asBinder() != iVoiceInteractor.asBinder()) {
            if (activityClientRecord.activity.mVoiceInteractor != null) {
                activityClientRecord.activity.mVoiceInteractor.destroy();
            }
            activityClientRecord.activity.mVoiceInteractor = new android.app.VoiceInteractor(iVoiceInteractor, activityClientRecord.activity, activityClientRecord.activity, android.os.Looper.myLooper());
        }
        activityClientRecord.activity.onGetDirectActions(cancellationSignal, new java.util.function.Consumer() { // from class: android.app.ActivityThread$$ExternalSyntheticLambda6
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                android.app.ActivityThread.lambda$handleRequestDirectActions$1(android.app.ActivityThread.ActivityClientRecord.this, remoteCallback, (java.util.List) obj);
            }
        });
    }

    static /* synthetic */ void lambda$handleRequestDirectActions$1(android.app.ActivityThread.ActivityClientRecord activityClientRecord, android.os.RemoteCallback remoteCallback, java.util.List list) {
        java.util.Objects.requireNonNull(list);
        com.android.internal.util.Preconditions.checkCollectionElementsNotNull(list, android.app.slice.Slice.HINT_ACTIONS);
        if (!list.isEmpty()) {
            int size = list.size();
            for (int i = 0; i < size; i++) {
                ((android.app.DirectAction) list.get(i)).setSource(activityClientRecord.activity.getTaskId(), activityClientRecord.activity.getAssistToken());
            }
            android.os.Bundle bundle = new android.os.Bundle();
            bundle.putParcelable(android.app.DirectAction.KEY_ACTIONS_LIST, new android.content.pm.ParceledListSlice(list));
            remoteCallback.sendResult(bundle);
            return;
        }
        remoteCallback.sendResult(null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handlePerformDirectAction(android.os.IBinder iBinder, java.lang.String str, android.os.Bundle bundle, android.os.CancellationSignal cancellationSignal, final android.os.RemoteCallback remoteCallback) {
        android.app.ActivityThread.ActivityClientRecord activityClientRecord = this.mActivities.get(iBinder);
        if (activityClientRecord != null) {
            int lifecycleState = activityClientRecord.getLifecycleState();
            if (lifecycleState < 2 || lifecycleState >= 5) {
                remoteCallback.sendResult(null);
                return;
            }
            if (bundle == null) {
                bundle = android.os.Bundle.EMPTY;
            }
            android.app.Activity activity = activityClientRecord.activity;
            java.util.Objects.requireNonNull(remoteCallback);
            activity.onPerformDirectAction(str, bundle, cancellationSignal, new java.util.function.Consumer() { // from class: android.app.ActivityThread$$ExternalSyntheticLambda7
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    android.os.RemoteCallback.this.sendResult((android.os.Bundle) obj);
                }
            });
            return;
        }
        remoteCallback.sendResult(null);
    }

    public void handleTranslucentConversionComplete(android.os.IBinder iBinder, boolean z) {
        android.app.ActivityThread.ActivityClientRecord activityClientRecord = this.mActivities.get(iBinder);
        if (activityClientRecord != null) {
            activityClientRecord.activity.onTranslucentConversionComplete(z);
        }
    }

    public void onNewSceneTransitionInfo(android.os.IBinder iBinder, android.app.ActivityOptions.SceneTransitionInfo sceneTransitionInfo) {
        android.app.ActivityThread.ActivityClientRecord activityClientRecord = this.mActivities.get(iBinder);
        if (activityClientRecord != null) {
            activityClientRecord.activity.onNewSceneTransitionInfo(sceneTransitionInfo);
        }
    }

    public void handleInstallProvider(android.content.pm.ProviderInfo providerInfo) {
        android.os.StrictMode.ThreadPolicy allowThreadDiskWrites = android.os.StrictMode.allowThreadDiskWrites();
        try {
            installContentProviders(this.mInitialApplication, java.util.Arrays.asList(providerInfo));
        } finally {
            android.os.StrictMode.setThreadPolicy(allowThreadDiskWrites);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleEnterAnimationComplete(android.os.IBinder iBinder) {
        android.app.ActivityThread.ActivityClientRecord activityClientRecord = this.mActivities.get(iBinder);
        if (activityClientRecord != null) {
            activityClientRecord.activity.dispatchEnterAnimationComplete();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleStartBinderTracking() {
        android.os.Binder.enableStackTracking();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleStopBinderTrackingAndDump(android.os.ParcelFileDescriptor parcelFileDescriptor) {
        try {
            android.os.Binder.disableStackTracking();
            android.os.Binder.getTransactionTracker().writeTracesToFile(parcelFileDescriptor);
        } finally {
            libcore.io.IoUtils.closeQuietly(parcelFileDescriptor);
            android.os.Binder.getTransactionTracker().clearTraces();
        }
    }

    @Override // android.app.ClientTransactionHandler
    public void handlePictureInPictureRequested(android.app.ActivityThread.ActivityClientRecord activityClientRecord) {
        if (!activityClientRecord.activity.onPictureInPictureRequested()) {
            schedulePauseWithUserLeaveHintAndReturnToCurrentState(activityClientRecord);
        }
    }

    @Override // android.app.ClientTransactionHandler
    public void handlePictureInPictureStateChanged(android.app.ActivityThread.ActivityClientRecord activityClientRecord, android.app.PictureInPictureUiState pictureInPictureUiState) {
        activityClientRecord.activity.onPictureInPictureUiStateChanged(pictureInPictureUiState);
    }

    public void registerSplashScreenManager(android.window.SplashScreen.SplashScreenManagerGlobal splashScreenManagerGlobal) {
        synchronized (this) {
            this.mSplashScreenGlobal = splashScreenManagerGlobal;
        }
    }

    @Override // android.app.ClientTransactionHandler
    public boolean isHandleSplashScreenExit(android.os.IBinder iBinder) {
        boolean z;
        synchronized (this) {
            z = this.mSplashScreenGlobal != null && this.mSplashScreenGlobal.containsExitListener(iBinder);
        }
        return z;
    }

    @Override // android.app.ClientTransactionHandler
    public void handleAttachSplashScreenView(android.app.ActivityThread.ActivityClientRecord activityClientRecord, android.window.SplashScreenView.SplashScreenViewParcelable splashScreenViewParcelable, android.view.SurfaceControl surfaceControl) {
        com.android.internal.policy.DecorView decorView = (com.android.internal.policy.DecorView) activityClientRecord.window.peekDecorView();
        if (splashScreenViewParcelable != null && decorView != null) {
            createSplashScreen(activityClientRecord, decorView, splashScreenViewParcelable, surfaceControl);
        } else {
            android.util.Slog.e(TAG, "handleAttachSplashScreenView failed, unable to attach");
        }
    }

    private void createSplashScreen(android.app.ActivityThread.ActivityClientRecord activityClientRecord, com.android.internal.policy.DecorView decorView, android.window.SplashScreenView.SplashScreenViewParcelable splashScreenViewParcelable, android.view.SurfaceControl surfaceControl) {
        android.window.SplashScreenView build = new android.window.SplashScreenView.Builder(activityClientRecord.activity).createFromParcel(splashScreenViewParcelable).build();
        build.attachHostWindow(activityClientRecord.window);
        decorView.addView(build);
        build.requestLayout();
        build.getViewTreeObserver().addOnPreDrawListener(new android.app.ActivityThread.AnonymousClass1(build, activityClientRecord, decorView, surfaceControl));
    }

    /* renamed from: android.app.ActivityThread$1, reason: invalid class name */
    class AnonymousClass1 implements android.view.ViewTreeObserver.OnPreDrawListener {
        private boolean mHandled = false;
        final /* synthetic */ com.android.internal.policy.DecorView val$decorView;
        final /* synthetic */ android.app.ActivityThread.ActivityClientRecord val$r;
        final /* synthetic */ android.view.SurfaceControl val$startingWindowLeash;
        final /* synthetic */ android.window.SplashScreenView val$view;

        AnonymousClass1(android.window.SplashScreenView splashScreenView, android.app.ActivityThread.ActivityClientRecord activityClientRecord, com.android.internal.policy.DecorView decorView, android.view.SurfaceControl surfaceControl) {
            this.val$view = splashScreenView;
            this.val$r = activityClientRecord;
            this.val$decorView = decorView;
            this.val$startingWindowLeash = surfaceControl;
        }

        @Override // android.view.ViewTreeObserver.OnPreDrawListener
        public boolean onPreDraw() {
            if (this.mHandled) {
                return true;
            }
            this.mHandled = true;
            android.app.ActivityThread.this.syncTransferSplashscreenViewTransaction(this.val$view, this.val$r.token, this.val$decorView, this.val$startingWindowLeash);
            android.window.SplashScreenView splashScreenView = this.val$view;
            final android.window.SplashScreenView splashScreenView2 = this.val$view;
            splashScreenView.post(new java.lang.Runnable() { // from class: android.app.ActivityThread$1$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    android.app.ActivityThread.AnonymousClass1.this.lambda$onPreDraw$0(splashScreenView2);
                }
            });
            return true;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onPreDraw$0(android.window.SplashScreenView splashScreenView) {
            splashScreenView.getViewTreeObserver().removeOnPreDrawListener(this);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: reportSplashscreenViewShown, reason: merged with bridge method [inline-methods] */
    public void lambda$syncTransferSplashscreenViewTransaction$2(android.os.IBinder iBinder, android.window.SplashScreenView splashScreenView) {
        android.app.ActivityClient.getInstance().reportSplashScreenAttached(iBinder);
        synchronized (this) {
            if (this.mSplashScreenGlobal != null) {
                this.mSplashScreenGlobal.handOverSplashScreenView(iBinder, splashScreenView);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void syncTransferSplashscreenViewTransaction(final android.window.SplashScreenView splashScreenView, final android.os.IBinder iBinder, android.view.View view, android.view.SurfaceControl surfaceControl) {
        android.view.SurfaceControl.Transaction transaction = new android.view.SurfaceControl.Transaction();
        transaction.hide(surfaceControl);
        view.getViewRootImpl().applyTransactionOnDraw(transaction);
        splashScreenView.syncTransferSurfaceOnDraw();
        view.postOnAnimation(new java.lang.Runnable() { // from class: android.app.ActivityThread$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                android.app.ActivityThread.this.lambda$syncTransferSplashscreenViewTransaction$2(iBinder, splashScreenView);
            }
        });
    }

    private void schedulePauseWithUserLeaveHintAndReturnToCurrentState(android.app.ActivityThread.ActivityClientRecord activityClientRecord) {
        int lifecycleState = activityClientRecord.getLifecycleState();
        if (lifecycleState != 3 && lifecycleState != 4) {
        }
        switch (lifecycleState) {
            case 3:
                schedulePauseWithUserLeavingHint(activityClientRecord);
                scheduleResume(activityClientRecord);
                break;
            case 4:
                scheduleResume(activityClientRecord);
                schedulePauseWithUserLeavingHint(activityClientRecord);
                break;
        }
    }

    private void schedulePauseWithUserLeavingHint(android.app.ActivityThread.ActivityClientRecord activityClientRecord) {
        android.app.servertransaction.ClientTransaction obtain = android.app.servertransaction.ClientTransaction.obtain(this.mAppThread);
        obtain.addTransactionItem(android.app.servertransaction.PauseActivityItem.obtain(activityClientRecord.token, activityClientRecord.activity.isFinishing(), true, activityClientRecord.activity.mConfigChangeFlags, false, false));
        executeTransaction(obtain);
    }

    private void scheduleResume(android.app.ActivityThread.ActivityClientRecord activityClientRecord) {
        android.app.servertransaction.ClientTransaction obtain = android.app.servertransaction.ClientTransaction.obtain(this.mAppThread);
        obtain.addTransactionItem(android.app.servertransaction.ResumeActivityItem.obtain(activityClientRecord.token, false, false));
        executeTransaction(obtain);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleLocalVoiceInteractionStarted(android.os.IBinder iBinder, com.android.internal.app.IVoiceInteractor iVoiceInteractor) {
        android.app.ActivityThread.ActivityClientRecord activityClientRecord = this.mActivities.get(iBinder);
        if (activityClientRecord != null) {
            activityClientRecord.voiceInteractor = iVoiceInteractor;
            activityClientRecord.activity.setVoiceInteractor(iVoiceInteractor);
            if (iVoiceInteractor == null) {
                activityClientRecord.activity.onLocalVoiceInteractionStopped();
            } else {
                activityClientRecord.activity.onLocalVoiceInteractionStarted();
            }
        }
    }

    private static boolean attemptAttachAgent(java.lang.String str, java.lang.ClassLoader classLoader) {
        try {
            dalvik.system.VMDebug.attachAgent(str, classLoader);
            return true;
        } catch (java.io.IOException e) {
            android.util.Slog.e(TAG, "Attaching agent with " + classLoader + " failed: " + str);
            return false;
        }
    }

    static void handleAttachAgent(java.lang.String str, android.app.LoadedApk loadedApk) {
        java.lang.ClassLoader classLoader = loadedApk != null ? loadedApk.getClassLoader() : null;
        if (!attemptAttachAgent(str, classLoader) && classLoader != null) {
            attemptAttachAgent(str, null);
        }
    }

    static void handleAttachStartupAgents(java.lang.String str) {
        try {
            java.nio.file.Path path = android.app.ContextImpl.getCodeCacheDirBeforeBind(new java.io.File(str)).toPath();
            if (!java.nio.file.Files.exists(path, new java.nio.file.LinkOption[0])) {
                return;
            }
            java.nio.file.Path resolve = path.resolve("startup_agents");
            if (java.nio.file.Files.exists(resolve, new java.nio.file.LinkOption[0])) {
                java.nio.file.DirectoryStream<java.nio.file.Path> newDirectoryStream = java.nio.file.Files.newDirectoryStream(resolve);
                try {
                    java.util.Iterator<java.nio.file.Path> it = newDirectoryStream.iterator();
                    while (it.hasNext()) {
                        handleAttachAgent(it.next().toAbsolutePath().toString() + "=" + str, null);
                    }
                    if (newDirectoryStream != null) {
                        newDirectoryStream.close();
                    }
                } finally {
                }
            }
        } catch (java.lang.Exception e) {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateUiTranslationState(android.os.IBinder iBinder, int i, android.view.translation.TranslationSpec translationSpec, android.view.translation.TranslationSpec translationSpec2, java.util.List<android.view.autofill.AutofillId> list, android.view.translation.UiTranslationSpec uiTranslationSpec) {
        android.app.ActivityThread.ActivityClientRecord activityClientRecord = this.mActivities.get(iBinder);
        if (activityClientRecord == null) {
            android.util.Log.w(TAG, "updateUiTranslationState(): no activity for " + iBinder);
        } else {
            activityClientRecord.activity.updateUiTranslationState(i, translationSpec, translationSpec2, list, uiTranslationSpec);
        }
    }

    public static android.content.Intent getIntentBeingBroadcast() {
        return sCurrentBroadcastIntent.get();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleReceiver(android.app.ActivityThread.ReceiverData receiverData) {
        unscheduleGcIdler();
        java.lang.String className = receiverData.intent.getComponent().getClassName();
        android.app.LoadedApk packageInfoNoCheck = getPackageInfoNoCheck(receiverData.info.applicationInfo);
        android.app.IActivityManager service = android.app.ActivityManager.getService();
        try {
            android.app.ContextImpl contextImpl = (android.app.ContextImpl) packageInfoNoCheck.makeApplicationInner(false, this.mInstrumentation).getBaseContext();
            if (receiverData.info.splitName != null) {
                contextImpl = (android.app.ContextImpl) contextImpl.createContextForSplit(receiverData.info.splitName);
            }
            if (receiverData.info.attributionTags != null && receiverData.info.attributionTags.length > 0) {
                contextImpl = (android.app.ContextImpl) contextImpl.createAttributionContext(receiverData.info.attributionTags[0]);
            }
            java.lang.ClassLoader classLoader = contextImpl.getClassLoader();
            receiverData.intent.setExtrasClassLoader(classLoader);
            receiverData.intent.prepareToEnterProcess(isProtectedComponent(receiverData.info) || isProtectedBroadcast(receiverData.intent), contextImpl.getAttributionSource());
            receiverData.setExtrasClassLoader(classLoader);
            android.content.BroadcastReceiver instantiateReceiver = packageInfoNoCheck.getAppFactory().instantiateReceiver(classLoader, receiverData.info.name, receiverData.intent);
            try {
                try {
                    sCurrentBroadcastIntent.set(receiverData.intent);
                    instantiateReceiver.setPendingResult(receiverData);
                    instantiateReceiver.onReceive(contextImpl.getReceiverRestrictedContext(), receiverData.intent);
                } catch (java.lang.Exception e) {
                    receiverData.sendFinished(service);
                    if (!this.mInstrumentation.onException(instantiateReceiver, e)) {
                        throw new java.lang.RuntimeException("Unable to start receiver " + className + ": " + e.toString(), e);
                    }
                }
                sCurrentBroadcastIntent.set(null);
                if (instantiateReceiver.getPendingResult() != null) {
                    receiverData.finish();
                }
            } catch (java.lang.Throwable th) {
                sCurrentBroadcastIntent.set(null);
                throw th;
            }
        } catch (java.lang.Exception e2) {
            receiverData.sendFinished(service);
            throw new java.lang.RuntimeException("Unable to instantiate receiver " + className + ": " + e2.toString(), e2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleCreateBackupAgent(android.app.ActivityThread.CreateBackupAgentData createBackupAgentData) {
        android.os.IBinder iBinder;
        try {
            if (getPackageManager().getPackageInfo(createBackupAgentData.appInfo.packageName, 0L, android.os.UserHandle.myUserId()).applicationInfo.uid != android.os.Process.myUid()) {
                android.util.Slog.w(TAG, "Asked to instantiate non-matching package " + createBackupAgentData.appInfo.packageName);
                return;
            }
            unscheduleGcIdler();
            android.app.LoadedApk packageInfoNoCheck = getPackageInfoNoCheck(createBackupAgentData.appInfo);
            java.lang.String str = packageInfoNoCheck.mPackageName;
            if (str == null) {
                android.util.Slog.d(TAG, "Asked to create backup agent for nonexistent package");
                return;
            }
            java.lang.String backupAgentName = getBackupAgentName(createBackupAgentData);
            try {
                java.util.Map backupAgentsForUser = getBackupAgentsForUser(createBackupAgentData.userId);
                android.app.backup.BackupAgent backupAgent = (android.app.backup.BackupAgent) backupAgentsForUser.get(str);
                if (backupAgent != null) {
                    iBinder = backupAgent.onBind();
                } else {
                    try {
                        android.app.backup.BackupAgent backupAgent2 = (android.app.backup.BackupAgent) packageInfoNoCheck.getClassLoader().loadClass(backupAgentName).newInstance();
                        android.app.ContextImpl createAppContext = android.app.ContextImpl.createAppContext(this, packageInfoNoCheck);
                        createAppContext.setOuterContext(backupAgent2);
                        backupAgent2.attach(createAppContext);
                        backupAgent2.onCreate(android.os.UserHandle.of(createBackupAgentData.userId), createBackupAgentData.backupDestination, getOperationTypeFromBackupMode(createBackupAgentData.backupMode));
                        iBinder = backupAgent2.onBind();
                        try {
                            backupAgentsForUser.put(str, backupAgent2);
                        } catch (java.lang.Exception e) {
                            e = e;
                            android.util.Slog.e(TAG, "Agent threw during creation: " + e);
                            if (createBackupAgentData.backupMode != 2 && createBackupAgentData.backupMode != 3) {
                                throw e;
                            }
                            android.app.ActivityManager.getService().backupAgentCreated(str, iBinder, createBackupAgentData.userId);
                        }
                    } catch (java.lang.Exception e2) {
                        e = e2;
                        iBinder = null;
                    }
                }
                try {
                    android.app.ActivityManager.getService().backupAgentCreated(str, iBinder, createBackupAgentData.userId);
                } catch (android.os.RemoteException e3) {
                    throw e3.rethrowFromSystemServer();
                }
            } catch (java.lang.Exception e4) {
                throw new java.lang.RuntimeException("Unable to create BackupAgent " + backupAgentName + ": " + e4.toString(), e4);
            }
        } catch (android.os.RemoteException e5) {
            throw e5.rethrowFromSystemServer();
        }
    }

    private static int getOperationTypeFromBackupMode(int i) {
        switch (i) {
            case 0:
            case 1:
                return 0;
            case 2:
            case 3:
                return 1;
            default:
                android.util.Slog.w(TAG, "Invalid backup mode when initialising BackupAgent: " + i);
                return -1;
        }
    }

    private java.lang.String getBackupAgentName(android.app.ActivityThread.CreateBackupAgentData createBackupAgentData) {
        java.lang.String str = createBackupAgentData.appInfo.backupAgentName;
        if (str != null) {
            return str;
        }
        if (createBackupAgentData.backupMode == 1 || createBackupAgentData.backupMode == 3) {
            return DEFAULT_FULL_BACKUP_AGENT;
        }
        return str;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleDestroyBackupAgent(android.app.ActivityThread.CreateBackupAgentData createBackupAgentData) {
        java.lang.String str = getPackageInfoNoCheck(createBackupAgentData.appInfo).mPackageName;
        android.util.ArrayMap<java.lang.String, android.app.backup.BackupAgent> backupAgentsForUser = getBackupAgentsForUser(createBackupAgentData.userId);
        android.app.backup.BackupAgent backupAgent = backupAgentsForUser.get(str);
        if (backupAgent == null) {
            android.util.Slog.w(TAG, "Attempt to destroy unknown backup agent " + createBackupAgentData);
            return;
        }
        try {
            backupAgent.onDestroy();
        } catch (java.lang.Exception e) {
            android.util.Slog.w(TAG, "Exception thrown in onDestroy by backup agent of " + createBackupAgentData.appInfo);
            e.printStackTrace();
        }
        backupAgentsForUser.remove(str);
    }

    private android.util.ArrayMap<java.lang.String, android.app.backup.BackupAgent> getBackupAgentsForUser(int i) {
        android.util.ArrayMap<java.lang.String, android.app.backup.BackupAgent> arrayMap = this.mBackupAgentsByUser.get(i);
        if (arrayMap == null) {
            android.util.ArrayMap<java.lang.String, android.app.backup.BackupAgent> arrayMap2 = new android.util.ArrayMap<>();
            this.mBackupAgentsByUser.put(i, arrayMap2);
            return arrayMap2;
        }
        return arrayMap;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleCreateService(android.app.ActivityThread.CreateServiceData createServiceData) {
        java.lang.ClassLoader classLoader;
        unscheduleGcIdler();
        android.app.LoadedApk packageInfoNoCheck = getPackageInfoNoCheck(createServiceData.info.applicationInfo);
        android.app.Service service = null;
        try {
            android.app.Application makeApplicationInner = packageInfoNoCheck.makeApplicationInner(false, this.mInstrumentation);
            if (createServiceData.info.splitName != null) {
                classLoader = packageInfoNoCheck.getSplitClassLoader(createServiceData.info.splitName);
            } else {
                classLoader = packageInfoNoCheck.getClassLoader();
            }
            service = packageInfoNoCheck.getAppFactory().instantiateService(classLoader, createServiceData.info.name, createServiceData.intent);
            android.app.ContextImpl impl = android.app.ContextImpl.getImpl(service.createServiceBaseContext(this, packageInfoNoCheck));
            if (createServiceData.info.splitName != null) {
                impl = (android.app.ContextImpl) impl.createContextForSplit(createServiceData.info.splitName);
            }
            if (createServiceData.info.attributionTags != null && createServiceData.info.attributionTags.length > 0) {
                impl = (android.app.ContextImpl) impl.createAttributionContext(createServiceData.info.attributionTags[0]);
            }
            impl.getResources().addLoaders((android.content.res.loader.ResourcesLoader[]) makeApplicationInner.getResources().getLoaders().toArray(new android.content.res.loader.ResourcesLoader[0]));
            impl.setOuterContext(service);
            service.attach(impl, this, createServiceData.info.name, createServiceData.token, makeApplicationInner, android.app.ActivityManager.getService());
            if (!service.isUiContext()) {
                if (this.mLastReportedDeviceId == 0) {
                    service.updateDeviceId(this.mLastReportedDeviceId);
                } else {
                    android.companion.virtual.VirtualDeviceManager virtualDeviceManager = (android.companion.virtual.VirtualDeviceManager) impl.getSystemService(android.companion.virtual.VirtualDeviceManager.class);
                    if (virtualDeviceManager != null && virtualDeviceManager.isValidVirtualDeviceId(this.mLastReportedDeviceId)) {
                        service.updateDeviceId(this.mLastReportedDeviceId);
                    }
                }
            }
            service.onCreate();
            this.mServicesData.put(createServiceData.token, createServiceData);
            this.mServices.put(createServiceData.token, service);
            try {
                android.app.ActivityManager.getService().serviceDoneExecuting(createServiceData.token, 0, 0, 0, null);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        } catch (java.lang.Exception e2) {
            if (!this.mInstrumentation.onException(service, e2)) {
                throw new java.lang.RuntimeException("Unable to create service " + createServiceData.info.name + ": " + e2.toString(), e2);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleBindService(android.app.ActivityThread.BindServiceData bindServiceData) {
        android.app.ActivityThread.CreateServiceData createServiceData = this.mServicesData.get(bindServiceData.token);
        android.app.Service service = this.mServices.get(bindServiceData.token);
        if (service != null) {
            try {
                bindServiceData.intent.setExtrasClassLoader(service.getClassLoader());
                bindServiceData.intent.prepareToEnterProcess(isProtectedComponent(createServiceData.info), service.getAttributionSource());
                try {
                    if (!bindServiceData.rebind) {
                        android.app.ActivityManager.getService().publishService(bindServiceData.token, bindServiceData.intent, service.onBind(bindServiceData.intent));
                    } else {
                        service.onRebind(bindServiceData.intent);
                        android.app.ActivityManager.getService().serviceDoneExecuting(bindServiceData.token, 3, 0, 0, bindServiceData.intent);
                    }
                } catch (android.os.RemoteException e) {
                    throw e.rethrowFromSystemServer();
                }
            } catch (java.lang.Exception e2) {
                if (!this.mInstrumentation.onException(service, e2)) {
                    throw new java.lang.RuntimeException("Unable to bind to service " + service + " with " + bindServiceData.intent + ": " + e2.toString(), e2);
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleUnbindService(android.app.ActivityThread.BindServiceData bindServiceData) {
        android.app.ActivityThread.CreateServiceData createServiceData = this.mServicesData.get(bindServiceData.token);
        android.app.Service service = this.mServices.get(bindServiceData.token);
        if (service != null) {
            try {
                bindServiceData.intent.setExtrasClassLoader(service.getClassLoader());
                bindServiceData.intent.prepareToEnterProcess(isProtectedComponent(createServiceData.info), service.getAttributionSource());
                boolean onUnbind = service.onUnbind(bindServiceData.intent);
                try {
                    if (onUnbind) {
                        android.app.ActivityManager.getService().unbindFinished(bindServiceData.token, bindServiceData.intent, onUnbind);
                    } else {
                        android.app.ActivityManager.getService().serviceDoneExecuting(bindServiceData.token, 4, 0, 0, bindServiceData.intent);
                    }
                } catch (android.os.RemoteException e) {
                    throw e.rethrowFromSystemServer();
                }
            } catch (java.lang.Exception e2) {
                if (!this.mInstrumentation.onException(service, e2)) {
                    throw new java.lang.RuntimeException("Unable to unbind to service " + service + " with " + bindServiceData.intent + ": " + e2.toString(), e2);
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleDumpGfxInfo(android.app.ActivityThread.DumpComponentInfo dumpComponentInfo) {
        android.os.StrictMode.ThreadPolicy allowThreadDiskWrites = android.os.StrictMode.allowThreadDiskWrites();
        try {
            try {
                android.view.ThreadedRenderer.handleDumpGfxInfo(dumpComponentInfo.fd.getFileDescriptor(), dumpComponentInfo.args);
            } catch (java.lang.Exception e) {
                android.util.Log.w(TAG, "Caught exception from dumpGfxInfo()", e);
            }
        } finally {
            libcore.io.IoUtils.closeQuietly(dumpComponentInfo.fd);
            android.os.StrictMode.setThreadPolicy(allowThreadDiskWrites);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleDumpService(android.app.ActivityThread.DumpComponentInfo dumpComponentInfo) {
        android.os.StrictMode.ThreadPolicy allowThreadDiskWrites = android.os.StrictMode.allowThreadDiskWrites();
        try {
            android.app.Service service = this.mServices.get(dumpComponentInfo.token);
            if (service != null) {
                com.android.internal.util.FastPrintWriter fastPrintWriter = new com.android.internal.util.FastPrintWriter(new java.io.FileOutputStream(dumpComponentInfo.fd.getFileDescriptor()));
                service.dump(dumpComponentInfo.fd.getFileDescriptor(), fastPrintWriter, dumpComponentInfo.args);
                fastPrintWriter.flush();
            }
        } finally {
            libcore.io.IoUtils.closeQuietly(dumpComponentInfo.fd);
            android.os.StrictMode.setThreadPolicy(allowThreadDiskWrites);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleDumpResources(android.app.ActivityThread.DumpResourcesData dumpResourcesData) {
        android.os.StrictMode.ThreadPolicy allowThreadDiskWrites = android.os.StrictMode.allowThreadDiskWrites();
        try {
            com.android.internal.util.FastPrintWriter fastPrintWriter = new com.android.internal.util.FastPrintWriter(new java.io.FileOutputStream(dumpResourcesData.fd.getFileDescriptor()));
            android.content.res.Resources.dumpHistory(fastPrintWriter, "");
            fastPrintWriter.flush();
            if (dumpResourcesData.finishCallback != null) {
                dumpResourcesData.finishCallback.sendResult(null);
            }
        } finally {
            libcore.io.IoUtils.closeQuietly(dumpResourcesData.fd);
            android.os.StrictMode.setThreadPolicy(allowThreadDiskWrites);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleDumpActivity(android.app.ActivityThread.DumpComponentInfo dumpComponentInfo) {
        android.os.StrictMode.ThreadPolicy allowThreadDiskWrites = android.os.StrictMode.allowThreadDiskWrites();
        try {
            android.app.ActivityThread.ActivityClientRecord activityClientRecord = this.mActivities.get(dumpComponentInfo.token);
            if (activityClientRecord != null && activityClientRecord.activity != null) {
                com.android.internal.util.FastPrintWriter fastPrintWriter = new com.android.internal.util.FastPrintWriter(new java.io.FileOutputStream(dumpComponentInfo.fd.getFileDescriptor()));
                activityClientRecord.activity.dumpInternal(dumpComponentInfo.prefix, dumpComponentInfo.fd.getFileDescriptor(), fastPrintWriter, dumpComponentInfo.args);
                fastPrintWriter.flush();
            }
        } finally {
            libcore.io.IoUtils.closeQuietly(dumpComponentInfo.fd);
            android.os.StrictMode.setThreadPolicy(allowThreadDiskWrites);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleDumpProvider(android.app.ActivityThread.DumpComponentInfo dumpComponentInfo) {
        android.os.StrictMode.ThreadPolicy allowThreadDiskWrites = android.os.StrictMode.allowThreadDiskWrites();
        try {
            android.app.ActivityThread.ProviderClientRecord providerClientRecord = this.mLocalProviders.get(dumpComponentInfo.token);
            if (providerClientRecord != null && providerClientRecord.mLocalProvider != null) {
                com.android.internal.util.FastPrintWriter fastPrintWriter = new com.android.internal.util.FastPrintWriter(new java.io.FileOutputStream(dumpComponentInfo.fd.getFileDescriptor()));
                providerClientRecord.mLocalProvider.dump(dumpComponentInfo.fd.getFileDescriptor(), fastPrintWriter, dumpComponentInfo.args);
                fastPrintWriter.flush();
            }
        } finally {
            libcore.io.IoUtils.closeQuietly(dumpComponentInfo.fd);
            android.os.StrictMode.setThreadPolicy(allowThreadDiskWrites);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleServiceArgs(android.app.ActivityThread.ServiceArgsData serviceArgsData) {
        int i;
        android.app.ActivityThread.CreateServiceData createServiceData = this.mServicesData.get(serviceArgsData.token);
        android.app.Service service = this.mServices.get(serviceArgsData.token);
        if (service != null) {
            try {
                if (serviceArgsData.args != null) {
                    serviceArgsData.args.setExtrasClassLoader(service.getClassLoader());
                    serviceArgsData.args.prepareToEnterProcess(isProtectedComponent(createServiceData.info), service.getAttributionSource());
                }
                if (!serviceArgsData.taskRemoved) {
                    i = service.onStartCommand(serviceArgsData.args, serviceArgsData.flags, serviceArgsData.startId);
                } else {
                    service.onTaskRemoved(serviceArgsData.args);
                    i = 1000;
                }
                android.app.QueuedWork.waitToFinish();
                try {
                    android.app.ActivityManager.getService().serviceDoneExecuting(serviceArgsData.token, 1, serviceArgsData.startId, i, null);
                } catch (android.os.RemoteException e) {
                    throw e.rethrowFromSystemServer();
                }
            } catch (java.lang.Exception e2) {
                if (!this.mInstrumentation.onException(service, e2)) {
                    throw new java.lang.RuntimeException("Unable to start service " + service + " with " + serviceArgsData.args + ": " + e2.toString(), e2);
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleStopService(android.os.IBinder iBinder) {
        this.mServicesData.remove(iBinder);
        android.app.Service remove = this.mServices.remove(iBinder);
        if (remove == null) {
            android.util.Slog.i(TAG, "handleStopService: token=" + iBinder + " not found.");
            return;
        }
        try {
            remove.onDestroy();
            remove.detachAndCleanUp();
            android.content.Context baseContext = remove.getBaseContext();
            if (baseContext instanceof android.app.ContextImpl) {
                ((android.app.ContextImpl) baseContext).scheduleFinalCleanup(remove.getClassName(), "Service");
            }
            android.app.QueuedWork.waitToFinish();
            try {
                android.app.ActivityManager.getService().serviceDoneExecuting(iBinder, 2, 0, 0, null);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        } catch (java.lang.Exception e2) {
            if (this.mInstrumentation.onException(remove, e2)) {
                android.util.Slog.i(TAG, "handleStopService: exception for " + iBinder, e2);
                return;
            }
            throw new java.lang.RuntimeException("Unable to stop service " + remove + ": " + e2.toString(), e2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleTimeoutService(android.os.IBinder iBinder, int i) {
        android.app.Service service = this.mServices.get(iBinder);
        if (service == null) {
            android.util.Slog.wtf(TAG, "handleTimeoutService: token=" + iBinder + " not found.");
            return;
        }
        try {
            service.callOnTimeout(i);
        } catch (java.lang.Exception e) {
            if (this.mInstrumentation.onException(service, e)) {
                android.util.Slog.i(TAG, "handleTimeoutService: exception for " + iBinder, e);
                return;
            }
            throw new java.lang.RuntimeException("Unable to call onTimeout on service " + service + ": " + e.toString(), e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleTimeoutServiceForType(android.os.IBinder iBinder, int i, int i2) {
        android.app.Service service = this.mServices.get(iBinder);
        if (service == null) {
            android.util.Slog.wtf(TAG, "handleTimeoutServiceForType: token=" + iBinder + " not found.");
            return;
        }
        try {
            service.callOnTimeLimitExceeded(i, i2);
        } catch (java.lang.Exception e) {
            if (this.mInstrumentation.onException(service, e)) {
                android.util.Slog.i(TAG, "handleTimeoutServiceForType: exception for " + iBinder, e);
                return;
            }
            throw new java.lang.RuntimeException("Unable to call onTimeLimitExceeded on service " + service + ": " + e, e);
        }
    }

    public boolean performResumeActivity(android.app.ActivityThread.ActivityClientRecord activityClientRecord, boolean z, java.lang.String str) {
        if (activityClientRecord.activity.mFinished) {
            return false;
        }
        if (activityClientRecord.getLifecycleState() == 3) {
            if (!z) {
                java.lang.IllegalStateException illegalStateException = new java.lang.IllegalStateException("Trying to resume activity which is already resumed");
                android.util.Slog.e(TAG, illegalStateException.getMessage(), illegalStateException);
                android.util.Slog.e(TAG, activityClientRecord.getStateString());
            }
            return false;
        }
        if (z) {
            activityClientRecord.hideForNow = false;
            activityClientRecord.activity.mStartedActivity = false;
        }
        try {
            activityClientRecord.activity.onStateNotSaved();
            activityClientRecord.activity.mFragments.noteStateNotSaved();
            checkAndBlockForNetworkAccess();
            if (activityClientRecord.pendingIntents != null) {
                deliverNewIntents(activityClientRecord, activityClientRecord.pendingIntents);
                activityClientRecord.pendingIntents = null;
            }
            if (activityClientRecord.pendingResults != null) {
                deliverResults(activityClientRecord, activityClientRecord.pendingResults, str);
                activityClientRecord.pendingResults = null;
            }
            activityClientRecord.activity.performResume(activityClientRecord.startsNotResumed, str);
            activityClientRecord.state = null;
            activityClientRecord.persistentState = null;
            activityClientRecord.setState(3);
            reportTopResumedActivityChanged(activityClientRecord, activityClientRecord.isTopResumedActivity, "topWhenResuming");
            return true;
        } catch (java.lang.Exception e) {
            if (!this.mInstrumentation.onException(activityClientRecord.activity, e)) {
                throw new java.lang.RuntimeException("Unable to resume activity " + activityClientRecord.intent.getComponent().toShortString() + ": " + e.toString(), e);
            }
            return true;
        }
    }

    static final void cleanUpPendingRemoveWindows(android.app.ActivityThread.ActivityClientRecord activityClientRecord, boolean z) {
        if (activityClientRecord.mPreserveWindow && !z) {
            return;
        }
        if (activityClientRecord.mPendingRemoveWindow != null) {
            activityClientRecord.mPendingRemoveWindowManager.removeViewImmediate(activityClientRecord.mPendingRemoveWindow.getDecorView());
            android.os.IBinder windowToken = activityClientRecord.mPendingRemoveWindow.getDecorView().getWindowToken();
            if (windowToken != null) {
                android.view.WindowManagerGlobal.getInstance().closeAll(windowToken, activityClientRecord.activity.getClass().getName(), "Activity");
            }
        }
        activityClientRecord.mPendingRemoveWindow = null;
        activityClientRecord.mPendingRemoveWindowManager = null;
    }

    @Override // android.app.ClientTransactionHandler
    public void handleResumeActivity(android.app.ActivityThread.ActivityClientRecord activityClientRecord, boolean z, boolean z2, boolean z3, java.lang.String str) {
        unscheduleGcIdler();
        this.mSomeActivitiesChanged = true;
        if (!performResumeActivity(activityClientRecord, z, str) || this.mActivitiesToBeDestroyed.containsKey(activityClientRecord.token)) {
            return;
        }
        android.app.Activity activity = activityClientRecord.activity;
        int i = z2 ? 256 : 0;
        boolean z4 = !activity.mStartedActivity;
        if (!z4) {
            z4 = android.app.ActivityClient.getInstance().willActivityBeVisible(activity.getActivityToken());
        }
        if (activityClientRecord.window == null && !activity.mFinished && z4) {
            activityClientRecord.window = activityClientRecord.activity.getWindow();
            android.view.View decorView = activityClientRecord.window.getDecorView();
            decorView.setVisibility(4);
            android.view.WindowManager windowManager = activity.getWindowManager();
            android.view.WindowManager.LayoutParams attributes = activityClientRecord.window.getAttributes();
            activity.mDecor = decorView;
            attributes.type = 1;
            attributes.softInputMode |= i;
            if (activityClientRecord.mPreserveWindow) {
                activity.mWindowAdded = true;
                activityClientRecord.mPreserveWindow = false;
                android.view.ViewRootImpl viewRootImpl = decorView.getViewRootImpl();
                if (viewRootImpl != null) {
                    viewRootImpl.notifyChildRebuilt();
                }
            }
            if (activity.mVisibleFromClient) {
                if (!activity.mWindowAdded) {
                    activity.mWindowAdded = true;
                    windowManager.addView(decorView, attributes);
                } else {
                    activity.onWindowAttributesChanged(attributes);
                }
            }
        } else if (!z4) {
            activityClientRecord.hideForNow = true;
        }
        cleanUpPendingRemoveWindows(activityClientRecord, false);
        if (!activityClientRecord.activity.mFinished && z4 && activityClientRecord.activity.mDecor != null && !activityClientRecord.hideForNow) {
            android.view.ViewRootImpl viewRootImpl2 = activityClientRecord.window.getDecorView().getViewRootImpl();
            android.view.WindowManager.LayoutParams attributes2 = viewRootImpl2 != null ? viewRootImpl2.mWindowAttributes : activityClientRecord.window.getAttributes();
            if ((256 & attributes2.softInputMode) != i) {
                attributes2.softInputMode = i | (attributes2.softInputMode & (-257));
                if (activityClientRecord.activity.mVisibleFromClient) {
                    activity.getWindowManager().updateViewLayout(activityClientRecord.window.getDecorView(), attributes2);
                }
            }
            activityClientRecord.activity.mVisibleFromServer = true;
            this.mNumVisibleActivities++;
            if (activityClientRecord.activity.mVisibleFromClient) {
                activityClientRecord.activity.makeVisible();
            }
            if (z3) {
                if (viewRootImpl2 != null) {
                    viewRootImpl2.dispatchCompatFakeFocus();
                } else {
                    activityClientRecord.window.getDecorView().fakeFocusAfterAttachingToWindow();
                }
            }
        }
        this.mNewActivities.add(activityClientRecord);
        android.os.Looper.myQueue().addIdleHandler(new android.app.ActivityThread.Idler());
    }

    @Override // android.app.ClientTransactionHandler
    public void handleTopResumedActivityChanged(android.app.ActivityThread.ActivityClientRecord activityClientRecord, boolean z, java.lang.String str) {
        if (activityClientRecord.isTopResumedActivity == z) {
            if (!android.os.Build.IS_DEBUGGABLE) {
                android.util.Slog.w(TAG, "Activity top position already set to onTop=" + z);
                return;
            }
            android.util.Slog.e(TAG, "Activity top position already set to onTop=" + z);
        }
        activityClientRecord.isTopResumedActivity = z;
        if (activityClientRecord.getLifecycleState() == 3) {
            reportTopResumedActivityChanged(activityClientRecord, z, "topStateChangedWhenResumed");
        }
    }

    private void reportTopResumedActivityChanged(android.app.ActivityThread.ActivityClientRecord activityClientRecord, boolean z, java.lang.String str) {
        if (activityClientRecord.lastReportedTopResumedState != z) {
            activityClientRecord.lastReportedTopResumedState = z;
            activityClientRecord.activity.performTopResumedActivityChanged(z, str);
        }
    }

    @Override // android.app.ClientTransactionHandler
    public void handlePauseActivity(android.app.ActivityThread.ActivityClientRecord activityClientRecord, boolean z, boolean z2, int i, boolean z3, android.app.servertransaction.PendingTransactionActions pendingTransactionActions, java.lang.String str) {
        if (z2) {
            performUserLeavingActivity(activityClientRecord);
        }
        android.app.Activity activity = activityClientRecord.activity;
        activity.mConfigChangeFlags = i | activity.mConfigChangeFlags;
        if (z3) {
            activityClientRecord.activity.mIsInPictureInPictureMode = true;
        }
        performPauseActivity(activityClientRecord, z, str, pendingTransactionActions);
        if (activityClientRecord.isPreHoneycomb()) {
            android.app.QueuedWork.waitToFinish();
        }
        this.mSomeActivitiesChanged = true;
    }

    final void performUserLeavingActivity(android.app.ActivityThread.ActivityClientRecord activityClientRecord) {
        this.mInstrumentation.callActivityOnPictureInPictureRequested(activityClientRecord.activity);
        this.mInstrumentation.callActivityOnUserLeaving(activityClientRecord.activity);
    }

    final android.os.Bundle performPauseActivity(android.os.IBinder iBinder, boolean z, java.lang.String str, android.app.servertransaction.PendingTransactionActions pendingTransactionActions) {
        android.app.ActivityThread.ActivityClientRecord activityClientRecord = this.mActivities.get(iBinder);
        if (activityClientRecord != null) {
            return performPauseActivity(activityClientRecord, z, str, pendingTransactionActions);
        }
        return null;
    }

    private android.os.Bundle performPauseActivity(android.app.ActivityThread.ActivityClientRecord activityClientRecord, boolean z, java.lang.String str, android.app.servertransaction.PendingTransactionActions pendingTransactionActions) {
        java.util.ArrayList<android.app.OnActivityPausedListener> remove;
        if (activityClientRecord.paused) {
            if (activityClientRecord.activity.mFinished) {
                return null;
            }
            java.lang.RuntimeException runtimeException = new java.lang.RuntimeException("Performing pause of activity that is not resumed: " + activityClientRecord.intent.getComponent().toShortString());
            android.util.Slog.e(TAG, runtimeException.getMessage(), runtimeException);
        }
        if (z) {
            activityClientRecord.activity.mFinished = true;
        }
        boolean z2 = !activityClientRecord.activity.mFinished && activityClientRecord.isPreHoneycomb();
        if (z2) {
            callActivityOnSaveInstanceState(activityClientRecord);
        }
        performPauseActivityIfNeeded(activityClientRecord, str);
        synchronized (this.mOnPauseListeners) {
            remove = this.mOnPauseListeners.remove(activityClientRecord.activity);
        }
        int size = remove != null ? remove.size() : 0;
        for (int i = 0; i < size; i++) {
            remove.get(i).onPaused(activityClientRecord.activity);
        }
        android.os.Bundle oldState = pendingTransactionActions != null ? pendingTransactionActions.getOldState() : null;
        if (oldState != null && activityClientRecord.isPreHoneycomb()) {
            activityClientRecord.state = oldState;
        }
        if (z2) {
            return activityClientRecord.state;
        }
        return null;
    }

    private void performPauseActivityIfNeeded(android.app.ActivityThread.ActivityClientRecord activityClientRecord, java.lang.String str) {
        if (activityClientRecord.paused) {
            return;
        }
        reportTopResumedActivityChanged(activityClientRecord, false, "pausing");
        try {
            activityClientRecord.activity.mCalled = false;
            this.mInstrumentation.callActivityOnPause(activityClientRecord.activity);
        } catch (android.util.SuperNotCalledException e) {
            throw e;
        } catch (java.lang.Exception e2) {
            if (!this.mInstrumentation.onException(activityClientRecord.activity, e2)) {
                throw new java.lang.RuntimeException("Unable to pause activity " + safeToComponentShortString(activityClientRecord.intent) + ": " + e2.toString(), e2);
            }
        }
        if (!activityClientRecord.activity.mCalled) {
            throw new android.util.SuperNotCalledException("Activity " + safeToComponentShortString(activityClientRecord.intent) + " did not call through to super.onPause()");
        }
        activityClientRecord.setState(4);
    }

    final void performStopActivity(android.os.IBinder iBinder, boolean z, java.lang.String str) {
        performStopActivityInner(this.mActivities.get(iBinder), null, z, false, str);
    }

    private static final class ProviderRefCount {
        public final android.app.ActivityThread.ProviderClientRecord client;
        public final android.app.ContentProviderHolder holder;
        public boolean removePending;
        public int stableCount;
        public int unstableCount;

        ProviderRefCount(android.app.ContentProviderHolder contentProviderHolder, android.app.ActivityThread.ProviderClientRecord providerClientRecord, int i, int i2) {
            this.holder = contentProviderHolder;
            this.client = providerClientRecord;
            this.stableCount = i;
            this.unstableCount = i2;
        }
    }

    private void performStopActivityInner(android.app.ActivityThread.ActivityClientRecord activityClientRecord, android.app.servertransaction.PendingTransactionActions.StopInfo stopInfo, boolean z, boolean z2, java.lang.String str) {
        if (activityClientRecord.stopped) {
            if (activityClientRecord.activity.mFinished) {
                return;
            }
            if (!z2) {
                java.lang.RuntimeException runtimeException = new java.lang.RuntimeException("Performing stop of activity that is already stopped: " + activityClientRecord.intent.getComponent().toShortString());
                android.util.Slog.e(TAG, runtimeException.getMessage(), runtimeException);
                android.util.Slog.e(TAG, activityClientRecord.getStateString());
            }
        }
        performPauseActivityIfNeeded(activityClientRecord, str);
        if (stopInfo != null) {
            try {
                stopInfo.setDescription(activityClientRecord.activity.onCreateDescription());
            } catch (java.lang.Exception e) {
                if (!this.mInstrumentation.onException(activityClientRecord.activity, e)) {
                    throw new java.lang.RuntimeException("Unable to save state of activity " + activityClientRecord.intent.getComponent().toShortString() + ": " + e.toString(), e);
                }
            }
        }
        callActivityOnStop(activityClientRecord, z, str);
    }

    private void callActivityOnStop(android.app.ActivityThread.ActivityClientRecord activityClientRecord, boolean z, java.lang.String str) {
        boolean z2 = z && !activityClientRecord.activity.mFinished && activityClientRecord.state == null && !activityClientRecord.isPreHoneycomb();
        boolean isPreP = activityClientRecord.isPreP();
        if (z2 && isPreP) {
            callActivityOnSaveInstanceState(activityClientRecord);
        }
        try {
            activityClientRecord.activity.performStop(activityClientRecord.mPreserveWindow, str);
        } catch (android.util.SuperNotCalledException e) {
            throw e;
        } catch (java.lang.Exception e2) {
            if (!this.mInstrumentation.onException(activityClientRecord.activity, e2)) {
                throw new java.lang.RuntimeException("Unable to stop activity " + activityClientRecord.intent.getComponent().toShortString() + ": " + e2.toString(), e2);
            }
        }
        activityClientRecord.setState(5);
        if (z2 && !isPreP) {
            callActivityOnSaveInstanceState(activityClientRecord);
        }
    }

    private void updateVisibility(android.app.ActivityThread.ActivityClientRecord activityClientRecord, boolean z) {
        android.view.View view = activityClientRecord.activity.mDecor;
        if (view != null) {
            if (z) {
                if (!activityClientRecord.activity.mVisibleFromServer) {
                    activityClientRecord.activity.mVisibleFromServer = true;
                    this.mNumVisibleActivities++;
                    if (activityClientRecord.activity.mVisibleFromClient) {
                        activityClientRecord.activity.makeVisible();
                        return;
                    }
                    return;
                }
                return;
            }
            if (activityClientRecord.activity.mVisibleFromServer) {
                activityClientRecord.activity.mVisibleFromServer = false;
                this.mNumVisibleActivities--;
                view.setVisibility(4);
            }
        }
    }

    @Override // android.app.ClientTransactionHandler
    public void handleStopActivity(android.app.ActivityThread.ActivityClientRecord activityClientRecord, int i, android.app.servertransaction.PendingTransactionActions pendingTransactionActions, boolean z, java.lang.String str) {
        android.app.Activity activity = activityClientRecord.activity;
        activity.mConfigChangeFlags = i | activity.mConfigChangeFlags;
        android.app.servertransaction.PendingTransactionActions.StopInfo stopInfo = new android.app.servertransaction.PendingTransactionActions.StopInfo();
        performStopActivityInner(activityClientRecord, stopInfo, true, z, str);
        updateVisibility(activityClientRecord, false);
        if (!activityClientRecord.isPreHoneycomb()) {
            android.app.QueuedWork.waitToFinish();
        }
        stopInfo.setActivity(activityClientRecord);
        stopInfo.setState(activityClientRecord.state);
        stopInfo.setPersistentState(activityClientRecord.persistentState);
        pendingTransactionActions.setStopInfo(stopInfo);
        this.mSomeActivitiesChanged = true;
    }

    @Override // android.app.ClientTransactionHandler
    public void reportStop(android.app.servertransaction.PendingTransactionActions pendingTransactionActions) {
        this.mH.post(pendingTransactionActions.getStopInfo());
    }

    @Override // android.app.ClientTransactionHandler
    public void performRestartActivity(android.app.ActivityThread.ActivityClientRecord activityClientRecord, boolean z) {
        if (activityClientRecord.stopped) {
            activityClientRecord.activity.performRestart(z);
            if (z) {
                activityClientRecord.setState(2);
            }
        }
    }

    @Override // android.app.ClientTransactionHandler
    public void reportRefresh(android.app.ActivityThread.ActivityClientRecord activityClientRecord) {
        android.app.ActivityClient.getInstance().activityRefreshed(activityClientRecord.token);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleSetCoreSettings(android.os.Bundle bundle) {
        synchronized (this.mCoreSettingsLock) {
            this.mCoreSettings = bundle;
        }
        onCoreSettingsChange();
    }

    private void onCoreSettingsChange() {
        if (updateDebugViewAttributeState()) {
            relaunchAllActivities(true, "onCoreSettingsChange");
        }
    }

    private boolean updateDebugViewAttributeState() {
        boolean z = android.view.View.sDebugViewAttributes;
        android.view.View.sDebugViewAttributesApplicationPackage = this.mCoreSettings.getString(android.provider.Settings.Global.DEBUG_VIEW_ATTRIBUTES_APPLICATION_PACKAGE, "");
        android.view.View.sDebugViewAttributes = this.mCoreSettings.getInt(android.provider.Settings.Global.DEBUG_VIEW_ATTRIBUTES, 0) != 0 || android.view.View.sDebugViewAttributesApplicationPackage.equals((this.mBoundApplication == null || this.mBoundApplication.appInfo == null) ? "<unknown-app>" : this.mBoundApplication.appInfo.packageName);
        return z != android.view.View.sDebugViewAttributes;
    }

    private void relaunchAllActivities(boolean z, java.lang.String str) {
        android.util.Log.i(TAG, "Relaunch all activities: " + str);
        for (int size = this.mActivities.size() - 1; size >= 0; size--) {
            scheduleRelaunchActivityIfPossible(this.mActivities.valueAt(size), z);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleUpdatePackageCompatibilityInfo(android.app.ActivityThread.UpdateCompatibilityData updateCompatibilityData) {
        this.mCompatibilityInfo = updateCompatibilityData.info;
        android.app.LoadedApk peekPackageInfo = peekPackageInfo(updateCompatibilityData.pkg, false);
        if (peekPackageInfo != null) {
            peekPackageInfo.setCompatibilityInfo(updateCompatibilityData.info);
        }
        android.app.LoadedApk peekPackageInfo2 = peekPackageInfo(updateCompatibilityData.pkg, true);
        if (peekPackageInfo2 != null) {
            peekPackageInfo2.setCompatibilityInfo(updateCompatibilityData.info);
        }
        this.mConfigurationController.handleConfigurationChanged(updateCompatibilityData.info);
    }

    private void deliverResults(android.app.ActivityThread.ActivityClientRecord activityClientRecord, java.util.List<android.app.ResultInfo> list, java.lang.String str) {
        int size = list.size();
        for (int i = 0; i < size; i++) {
            android.app.ResultInfo resultInfo = list.get(i);
            try {
                if (resultInfo.mData != null) {
                    resultInfo.mData.setExtrasClassLoader(activityClientRecord.activity.getClassLoader());
                    resultInfo.mData.prepareToEnterProcess(isProtectedComponent(activityClientRecord.activityInfo), activityClientRecord.activity.getAttributionSource());
                }
                if (android.security.Flags.contentUriPermissionApis()) {
                    activityClientRecord.activity.dispatchActivityResult(resultInfo.mResultWho, resultInfo.mRequestCode, resultInfo.mResultCode, resultInfo.mData, new android.app.ComponentCaller(activityClientRecord.token, resultInfo.mCallerToken), str);
                } else {
                    activityClientRecord.activity.dispatchActivityResult(resultInfo.mResultWho, resultInfo.mRequestCode, resultInfo.mResultCode, resultInfo.mData, str);
                }
            } catch (java.lang.Exception e) {
                if (!this.mInstrumentation.onException(activityClientRecord.activity, e)) {
                    throw new java.lang.RuntimeException("Failure delivering result " + resultInfo + " to activity " + activityClientRecord.intent.getComponent().toShortString() + ": " + e.toString(), e);
                }
            }
        }
    }

    @Override // android.app.ClientTransactionHandler
    public void handleSendResult(android.app.ActivityThread.ActivityClientRecord activityClientRecord, java.util.List<android.app.ResultInfo> list, java.lang.String str) {
        boolean z = !activityClientRecord.paused;
        if (!activityClientRecord.activity.mFinished && activityClientRecord.activity.mDecor != null && activityClientRecord.hideForNow && z) {
            updateVisibility(activityClientRecord, true);
        }
        if (z) {
            try {
                activityClientRecord.activity.mCalled = false;
                this.mInstrumentation.callActivityOnPause(activityClientRecord.activity);
                if (!activityClientRecord.activity.mCalled) {
                    throw new android.util.SuperNotCalledException("Activity " + activityClientRecord.intent.getComponent().toShortString() + " did not call through to super.onPause()");
                }
            } catch (android.util.SuperNotCalledException e) {
                throw e;
            } catch (java.lang.Exception e2) {
                if (!this.mInstrumentation.onException(activityClientRecord.activity, e2)) {
                    throw new java.lang.RuntimeException("Unable to pause activity " + activityClientRecord.intent.getComponent().toShortString() + ": " + e2.toString(), e2);
                }
            }
        }
        checkAndBlockForNetworkAccess();
        deliverResults(activityClientRecord, list, str);
        if (z) {
            activityClientRecord.activity.performResume(false, str);
        }
    }

    void performDestroyActivity(android.app.ActivityThread.ActivityClientRecord activityClientRecord, boolean z, int i, boolean z2, java.lang.String str) {
        java.lang.Class<?> cls = activityClientRecord.activity.getClass();
        android.app.Activity activity = activityClientRecord.activity;
        activity.mConfigChangeFlags = i | activity.mConfigChangeFlags;
        if (z) {
            activityClientRecord.activity.mFinished = true;
        }
        performPauseActivityIfNeeded(activityClientRecord, "destroy");
        if (!activityClientRecord.stopped) {
            callActivityOnStop(activityClientRecord, false, "destroy");
        }
        if (z2) {
            try {
                activityClientRecord.lastNonConfigurationInstances = activityClientRecord.activity.retainNonConfigurationInstances();
            } catch (java.lang.Exception e) {
                if (!this.mInstrumentation.onException(activityClientRecord.activity, e)) {
                    throw new java.lang.RuntimeException("Unable to retain activity " + activityClientRecord.intent.getComponent().toShortString() + ": " + e.toString(), e);
                }
            }
        }
        try {
            activityClientRecord.activity.mCalled = false;
            this.mInstrumentation.callActivityOnDestroy(activityClientRecord.activity);
        } catch (android.util.SuperNotCalledException e2) {
            throw e2;
        } catch (java.lang.Exception e3) {
            if (!this.mInstrumentation.onException(activityClientRecord.activity, e3)) {
                throw new java.lang.RuntimeException("Unable to destroy activity " + safeToComponentShortString(activityClientRecord.intent) + ": " + e3.toString(), e3);
            }
        }
        if (!activityClientRecord.activity.mCalled) {
            throw new android.util.SuperNotCalledException("Activity " + safeToComponentShortString(activityClientRecord.intent) + " did not call through to super.onDestroy()");
        }
        if (activityClientRecord.window != null) {
            activityClientRecord.window.closeAllPanels();
        }
        activityClientRecord.setState(6);
        schedulePurgeIdler();
        synchronized (this) {
            if (this.mSplashScreenGlobal != null) {
                this.mSplashScreenGlobal.tokenDestroyed(activityClientRecord.token);
            }
        }
        synchronized (this.mResourcesManager) {
            this.mActivities.remove(activityClientRecord.token);
        }
        android.os.StrictMode.decrementExpectedActivityCount(cls);
    }

    private static java.lang.String safeToComponentShortString(android.content.Intent intent) {
        android.content.ComponentName component = intent.getComponent();
        return component == null ? "[Unknown]" : component.toShortString();
    }

    @Override // android.app.ClientTransactionHandler
    public java.util.Map<android.os.IBinder, android.app.servertransaction.DestroyActivityItem> getActivitiesToBeDestroyed() {
        return this.mActivitiesToBeDestroyed;
    }

    @Override // android.app.ClientTransactionHandler
    public void handleDestroyActivity(android.app.ActivityThread.ActivityClientRecord activityClientRecord, boolean z, int i, boolean z2, java.lang.String str) {
        performDestroyActivity(activityClientRecord, z, i, z2, str);
        cleanUpPendingRemoveWindows(activityClientRecord, z);
        android.view.WindowManager windowManager = activityClientRecord.activity.getWindowManager();
        android.view.View view = activityClientRecord.activity.mDecor;
        if (view != null) {
            if (activityClientRecord.activity.mVisibleFromServer) {
                this.mNumVisibleActivities--;
            }
            android.os.IBinder windowToken = view.getWindowToken();
            if (activityClientRecord.activity.mWindowAdded) {
                if (activityClientRecord.mPreserveWindow) {
                    activityClientRecord.mPendingRemoveWindow = activityClientRecord.window;
                    activityClientRecord.mPendingRemoveWindowManager = windowManager;
                    activityClientRecord.window.clearContentView();
                } else {
                    android.view.ViewRootImpl viewRootImpl = view.getViewRootImpl();
                    if (viewRootImpl != null) {
                        viewRootImpl.setActivityConfigCallback(null);
                    }
                    windowManager.removeViewImmediate(view);
                }
            }
            if (windowToken != null && activityClientRecord.mPendingRemoveWindow == null) {
                android.view.WindowManagerGlobal.getInstance().closeAll(windowToken, activityClientRecord.activity.getClass().getName(), "Activity");
            } else if (activityClientRecord.mPendingRemoveWindow != null) {
                android.view.WindowManagerGlobal.getInstance().closeAllExceptView(activityClientRecord.token, view, activityClientRecord.activity.getClass().getName(), "Activity");
            }
            activityClientRecord.activity.mDecor = null;
        }
        if (activityClientRecord.mPendingRemoveWindow == null) {
            android.view.WindowManagerGlobal.getInstance().closeAll(activityClientRecord.token, activityClientRecord.activity.getClass().getName(), "Activity");
        }
        android.content.Context baseContext = activityClientRecord.activity.getBaseContext();
        if (baseContext instanceof android.app.ContextImpl) {
            ((android.app.ContextImpl) baseContext).scheduleFinalCleanup(activityClientRecord.activity.getClass().getName(), "Activity");
        }
        if (z) {
            android.app.ActivityClient.getInstance().activityDestroyed(activityClientRecord.token);
            this.mNewActivities.remove(activityClientRecord);
        }
        this.mSomeActivitiesChanged = true;
    }

    @Override // android.app.ClientTransactionHandler
    public android.app.ActivityThread.ActivityClientRecord prepareRelaunchActivity(android.os.IBinder iBinder, java.util.List<android.app.ResultInfo> list, java.util.List<com.android.internal.content.ReferrerIntent> list2, int i, android.util.MergedConfiguration mergedConfiguration, boolean z, android.window.ActivityWindowInfo activityWindowInfo) {
        boolean z2;
        android.app.ActivityThread.ActivityClientRecord activityClientRecord;
        synchronized (this.mResourcesManager) {
            z2 = false;
            int i2 = 0;
            while (true) {
                if (i2 >= this.mRelaunchingActivities.size()) {
                    activityClientRecord = null;
                    break;
                }
                activityClientRecord = this.mRelaunchingActivities.get(i2);
                if (activityClientRecord.token != iBinder) {
                    i2++;
                } else {
                    if (list != null) {
                        if (activityClientRecord.pendingResults != null) {
                            activityClientRecord.pendingResults.addAll(list);
                        } else {
                            activityClientRecord.pendingResults = list;
                        }
                    }
                    if (list2 != null) {
                        if (activityClientRecord.pendingIntents != null) {
                            activityClientRecord.pendingIntents.addAll(list2);
                        } else {
                            activityClientRecord.pendingIntents = list2;
                        }
                    }
                }
            }
            if (activityClientRecord == null) {
                activityClientRecord = new android.app.ActivityThread.ActivityClientRecord();
                activityClientRecord.token = iBinder;
                activityClientRecord.pendingResults = list;
                activityClientRecord.pendingIntents = list2;
                activityClientRecord.mPreserveWindow = z;
                this.mRelaunchingActivities.add(activityClientRecord);
                z2 = true;
            }
            activityClientRecord.createdConfig = mergedConfiguration.getGlobalConfiguration();
            activityClientRecord.overrideConfig = mergedConfiguration.getOverrideConfiguration();
            activityClientRecord.pendingConfigChanges |= i;
            activityClientRecord.mActivityWindowInfo = activityWindowInfo;
        }
        if (z2) {
            return activityClientRecord;
        }
        return null;
    }

    @Override // android.app.ClientTransactionHandler
    public void handleRelaunchActivity(android.app.ActivityThread.ActivityClientRecord activityClientRecord, android.app.servertransaction.PendingTransactionActions pendingTransactionActions) {
        android.content.res.Configuration configuration;
        unscheduleGcIdler();
        this.mSomeActivitiesChanged = true;
        synchronized (this.mResourcesManager) {
            int size = this.mRelaunchingActivities.size();
            android.os.IBinder iBinder = activityClientRecord.token;
            int i = 0;
            android.app.ActivityThread.ActivityClientRecord activityClientRecord2 = null;
            int i2 = 0;
            while (i2 < size) {
                android.app.ActivityThread.ActivityClientRecord activityClientRecord3 = this.mRelaunchingActivities.get(i2);
                if (activityClientRecord3.token == iBinder) {
                    i |= activityClientRecord3.pendingConfigChanges;
                    this.mRelaunchingActivities.remove(i2);
                    i2--;
                    size--;
                    activityClientRecord2 = activityClientRecord3;
                }
                i2++;
            }
            if (activityClientRecord2 == null) {
                return;
            }
            android.content.res.Configuration pendingConfiguration = this.mConfigurationController.getPendingConfiguration(true);
            this.mPendingConfiguration = null;
            if (activityClientRecord2.createdConfig != null && (((configuration = this.mConfigurationController.getConfiguration()) == null || (activityClientRecord2.createdConfig.isOtherSeqNewer(configuration) && configuration.diff(activityClientRecord2.createdConfig) != 0)) && (pendingConfiguration == null || activityClientRecord2.createdConfig.isOtherSeqNewer(pendingConfiguration)))) {
                pendingConfiguration = activityClientRecord2.createdConfig;
            }
            if (pendingConfiguration != null) {
                this.mConfigurationController.updateDefaultDensity(pendingConfiguration.densityDpi);
                this.mConfigurationController.handleConfigurationChanged(pendingConfiguration, null);
                this.mCurDefaultDisplayDpi = this.mConfigurationController.getCurDefaultDisplayDpi();
                this.mConfiguration = this.mConfigurationController.getConfiguration();
            }
            android.app.ActivityThread.ActivityClientRecord activityClientRecord4 = this.mActivities.get(activityClientRecord2.token);
            if (activityClientRecord4 == null) {
                return;
            }
            activityClientRecord4.activity.mConfigChangeFlags |= i;
            activityClientRecord4.mPreserveWindow = activityClientRecord2.mPreserveWindow;
            activityClientRecord4.activity.mChangingConfigurations = true;
            handleRelaunchActivityInner(activityClientRecord4, i, activityClientRecord2.pendingResults, activityClientRecord2.pendingIntents, pendingTransactionActions, activityClientRecord2.startsNotResumed, activityClientRecord2.overrideConfig, activityClientRecord2.mActivityWindowInfo, "handleRelaunchActivity");
        }
    }

    void scheduleRelaunchActivity(android.os.IBinder iBinder) {
        android.app.ActivityThread.ActivityClientRecord activityClientRecord = this.mActivities.get(iBinder);
        if (activityClientRecord != null) {
            android.util.Log.i(TAG, "Schedule relaunch activity: " + activityClientRecord.activityInfo.name);
            scheduleRelaunchActivityIfPossible(activityClientRecord, !activityClientRecord.stopped);
        }
    }

    private void scheduleRelaunchActivityIfPossible(android.app.ActivityThread.ActivityClientRecord activityClientRecord, boolean z) {
        if ((activityClientRecord.activity != null && activityClientRecord.activity.mFinished) || (activityClientRecord.token instanceof android.os.Binder)) {
            return;
        }
        if (z && activityClientRecord.window != null) {
            activityClientRecord.mPreserveWindow = true;
        }
        this.mH.removeMessages(160, activityClientRecord.token);
        sendMessage(160, activityClientRecord.token);
    }

    public void handleRelaunchActivityLocally(android.os.IBinder iBinder) {
        android.app.ActivityThread.ActivityClientRecord activityClientRecord = this.mActivities.get(iBinder);
        if (activityClientRecord == null) {
            android.util.Log.w(TAG, "Activity to relaunch no longer exists");
            return;
        }
        int lifecycleState = activityClientRecord.getLifecycleState();
        if (lifecycleState < 2 || lifecycleState > 5) {
            android.util.Log.w(TAG, "Activity state must be in [ON_START..ON_STOP] in order to be relaunched,current state is " + lifecycleState);
            return;
        }
        android.app.ActivityClient.getInstance().activityLocalRelaunch(activityClientRecord.token);
        android.app.servertransaction.ActivityRelaunchItem obtain = android.app.servertransaction.ActivityRelaunchItem.obtain(activityClientRecord.token, null, null, 0, new android.util.MergedConfiguration(activityClientRecord.createdConfig != null ? activityClientRecord.createdConfig : this.mConfigurationController.getConfiguration(), activityClientRecord.overrideConfig), activityClientRecord.mPreserveWindow, activityClientRecord.getActivityWindowInfo());
        android.app.servertransaction.ActivityLifecycleItem lifecycleRequestForCurrentState = android.app.servertransaction.TransactionExecutorHelper.getLifecycleRequestForCurrentState(activityClientRecord);
        android.app.servertransaction.ClientTransaction obtain2 = android.app.servertransaction.ClientTransaction.obtain(this.mAppThread);
        obtain2.addTransactionItem(obtain);
        obtain2.addTransactionItem(lifecycleRequestForCurrentState);
        executeTransaction(obtain2);
    }

    private void handleRelaunchActivityInner(android.app.ActivityThread.ActivityClientRecord activityClientRecord, int i, java.util.List<android.app.ResultInfo> list, java.util.List<com.android.internal.content.ReferrerIntent> list2, android.app.servertransaction.PendingTransactionActions pendingTransactionActions, boolean z, android.content.res.Configuration configuration, android.window.ActivityWindowInfo activityWindowInfo, java.lang.String str) {
        android.content.Intent intent = activityClientRecord.activity.mIntent;
        if (!activityClientRecord.paused) {
            performPauseActivity(activityClientRecord, false, str, (android.app.servertransaction.PendingTransactionActions) null);
        }
        if (!activityClientRecord.stopped) {
            callActivityOnStop(activityClientRecord, true, str);
        }
        handleDestroyActivity(activityClientRecord, false, i, true, str);
        activityClientRecord.activity = null;
        activityClientRecord.window = null;
        activityClientRecord.hideForNow = false;
        if (list != null) {
            if (activityClientRecord.pendingResults == null) {
                activityClientRecord.pendingResults = list;
            } else {
                activityClientRecord.pendingResults.addAll(list);
            }
        }
        if (list2 != null) {
            if (activityClientRecord.pendingIntents == null) {
                activityClientRecord.pendingIntents = list2;
            } else {
                activityClientRecord.pendingIntents.addAll(list2);
            }
        }
        activityClientRecord.startsNotResumed = z;
        activityClientRecord.overrideConfig = configuration;
        activityClientRecord.mActivityWindowInfo = activityWindowInfo;
        handleLaunchActivity(activityClientRecord, pendingTransactionActions, this.mLastReportedDeviceId, intent);
    }

    @Override // android.app.ClientTransactionHandler
    public void reportRelaunch(android.app.ActivityThread.ActivityClientRecord activityClientRecord) {
        android.app.ActivityClient.getInstance().activityRelaunched(activityClientRecord.token);
    }

    private void callActivityOnSaveInstanceState(android.app.ActivityThread.ActivityClientRecord activityClientRecord) {
        activityClientRecord.state = new android.os.Bundle();
        activityClientRecord.state.setAllowFds(false);
        if (activityClientRecord.isPersistable()) {
            activityClientRecord.persistentState = new android.os.PersistableBundle();
            this.mInstrumentation.callActivityOnSaveInstanceState(activityClientRecord.activity, activityClientRecord.state, activityClientRecord.persistentState);
        } else {
            this.mInstrumentation.callActivityOnSaveInstanceState(activityClientRecord.activity, activityClientRecord.state);
        }
    }

    @Override // android.app.ActivityThreadInternal
    public java.util.ArrayList<android.content.ComponentCallbacks2> collectComponentCallbacks(boolean z) {
        int i;
        java.util.ArrayList<android.content.ComponentCallbacks2> arrayList = new java.util.ArrayList<>();
        synchronized (this.mResourcesManager) {
            int size = this.mAllApplications.size();
            for (int i2 = 0; i2 < size; i2++) {
                arrayList.add(this.mAllApplications.get(i2));
            }
            if (z) {
                for (int size2 = this.mActivities.size() - 1; size2 >= 0; size2--) {
                    android.app.Activity activity = this.mActivities.valueAt(size2).activity;
                    if (activity != null && !activity.mFinished) {
                        arrayList.add(activity);
                    }
                }
            }
            int size3 = this.mServices.size();
            for (int i3 = 0; i3 < size3; i3++) {
                android.app.Service valueAt = this.mServices.valueAt(i3);
                if (z || !(valueAt instanceof android.window.WindowProviderService)) {
                    arrayList.add(valueAt);
                }
            }
        }
        synchronized (this.mProviderMap) {
            int size4 = this.mLocalProviders.size();
            for (i = 0; i < size4; i++) {
                arrayList.add(this.mLocalProviders.valueAt(i).mLocalProvider);
            }
        }
        return arrayList;
    }

    private android.content.res.Configuration performConfigurationChangedForActivity(android.app.ActivityThread.ActivityClientRecord activityClientRecord, android.content.res.Configuration configuration, int i, boolean z) {
        activityClientRecord.tmpConfig.setTo(configuration);
        if (activityClientRecord.overrideConfig != null) {
            activityClientRecord.tmpConfig.updateFrom(activityClientRecord.overrideConfig);
        }
        android.content.res.Configuration performActivityConfigurationChanged = performActivityConfigurationChanged(activityClientRecord, activityClientRecord.tmpConfig, activityClientRecord.overrideConfig, i, z);
        android.window.ConfigurationHelper.freeTextLayoutCachesIfNeeded(activityClientRecord.activity.mCurrentConfig.diff(activityClientRecord.tmpConfig));
        return performActivityConfigurationChanged;
    }

    private android.content.res.Configuration performActivityConfigurationChanged(android.app.ActivityThread.ActivityClientRecord activityClientRecord, android.content.res.Configuration configuration, android.content.res.Configuration configuration2, int i, boolean z) {
        android.app.Activity activity = activityClientRecord.activity;
        android.os.IBinder activityToken = activity.getActivityToken();
        handleWindowingModeChangeIfNeeded(activityClientRecord, configuration);
        boolean isDifferentDisplay = android.window.ConfigurationHelper.isDifferentDisplay(activity.getDisplayId(), i);
        android.content.res.Configuration configuration3 = activity.getResources().getConfiguration();
        boolean z2 = true;
        boolean z3 = configuration3.diffPublicOnly(configuration) != 0;
        if (!z3 && !android.window.ConfigurationHelper.shouldUpdateResources(activityToken, configuration3, configuration, configuration2, isDifferentDisplay, java.lang.Boolean.valueOf(z3))) {
            z2 = false;
        }
        boolean z4 = activity.getResources().getBoolean(com.android.internal.R.bool.config_skipActivityRelaunchWhenDocking);
        int realConfigChanged = activity.mActivityInfo.getRealConfigChanged();
        if (z4 && onlyDeskInUiModeChanged(activity.mCurrentConfig, configuration)) {
            realConfigChanged |= 512;
        }
        boolean shouldReportChange = shouldReportChange(activity.mCurrentConfig, configuration, activityClientRecord.mSizeConfigurations, realConfigChanged, z);
        if (!z2 && !shouldReportChange) {
            return null;
        }
        android.content.res.Configuration overrideConfiguration = activity.getOverrideConfiguration();
        this.mResourcesManager.updateResourcesForActivity(activityToken, android.app.ConfigurationController.createNewConfigAndUpdateIfNotNull(configuration2, overrideConfiguration), i);
        android.content.res.Configuration createNewConfigAndUpdateIfNotNull = android.app.ConfigurationController.createNewConfigAndUpdateIfNotNull(configuration, overrideConfiguration);
        if (isDifferentDisplay) {
            activity.dispatchMovedToDisplay(i, createNewConfigAndUpdateIfNotNull);
        }
        activity.mConfigChangeFlags = 0;
        if (shouldReportChange) {
            activity.mCalled = false;
            activity.mCurrentConfig = new android.content.res.Configuration(configuration);
            activity.onConfigurationChanged(createNewConfigAndUpdateIfNotNull);
            if (!activity.mCalled) {
                throw new android.util.SuperNotCalledException("Activity " + activity.getLocalClassName() + " did not call through to super.onConfigurationChanged()");
            }
        }
        this.mConfigurationChangedListenerController.dispatchOnConfigurationChanged(activity.getActivityToken());
        return createNewConfigAndUpdateIfNotNull;
    }

    private boolean onlyDeskInUiModeChanged(android.content.res.Configuration configuration, android.content.res.Configuration configuration2) {
        return (isInDeskUiMode(configuration) != isInDeskUiMode(configuration2)) && !((configuration.uiMode & (-16)) != (configuration2.uiMode & (-16)));
    }

    private static boolean isInDeskUiMode(android.content.res.Configuration configuration) {
        return (configuration.uiMode & 15) == 2;
    }

    public static boolean shouldReportChange(android.content.res.Configuration configuration, android.content.res.Configuration configuration2, android.window.SizeConfigurationBuckets sizeConfigurationBuckets, int i, boolean z) {
        int diffPublicOnly = configuration.diffPublicOnly(configuration2);
        if (diffPublicOnly == 0) {
            return false;
        }
        if (z) {
            return true;
        }
        int filterDiff = android.window.SizeConfigurationBuckets.filterDiff(diffPublicOnly, configuration, configuration2, sizeConfigurationBuckets);
        if (filterDiff != 0) {
            diffPublicOnly = filterDiff;
        }
        if (((~i) & diffPublicOnly) != 0) {
            return false;
        }
        return true;
    }

    public final void applyConfigurationToResources(android.content.res.Configuration configuration) {
        synchronized (this.mResourcesManager) {
            this.mResourcesManager.applyConfigurationToResources(configuration, null);
        }
    }

    private void updateDeviceIdForNonUIContexts(int i) {
        if (i == -1 || i == this.mLastReportedDeviceId) {
            return;
        }
        this.mLastReportedDeviceId = i;
        java.util.ArrayList arrayList = new java.util.ArrayList();
        synchronized (this.mResourcesManager) {
            int size = this.mAllApplications.size();
            for (int i2 = 0; i2 < size; i2++) {
                arrayList.add(this.mAllApplications.get(i2));
            }
            int size2 = this.mServices.size();
            for (int i3 = 0; i3 < size2; i3++) {
                android.app.Service valueAt = this.mServices.valueAt(i3);
                if (!valueAt.isUiContext()) {
                    arrayList.add(valueAt);
                }
            }
        }
        java.util.Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            try {
                ((android.content.Context) it.next()).updateDeviceId(i);
            } catch (java.lang.IllegalArgumentException e) {
            }
        }
    }

    @Override // android.app.ClientTransactionHandler
    public void handleConfigurationChanged(android.content.res.Configuration configuration, int i) {
        this.mConfigurationController.handleConfigurationChanged(configuration);
        updateDeviceIdForNonUIContexts(i);
        this.mCurDefaultDisplayDpi = this.mConfigurationController.getCurDefaultDisplayDpi();
        this.mConfiguration = this.mConfigurationController.getConfiguration();
        this.mPendingConfiguration = this.mConfigurationController.getPendingConfiguration(false);
    }

    @Override // android.app.ClientTransactionHandler
    public void handleWindowContextInfoChanged(android.os.IBinder iBinder, android.window.WindowContextInfo windowContextInfo) {
        android.window.WindowTokenClientController.getInstance().onWindowContextInfoChanged(iBinder, windowContextInfo);
    }

    @Override // android.app.ClientTransactionHandler
    public void handleWindowContextWindowRemoval(android.os.IBinder iBinder) {
        android.window.WindowTokenClientController.getInstance().onWindowContextWindowRemoved(iBinder);
    }

    private void handleWindowingModeChangeIfNeeded(android.app.ActivityThread.ActivityClientRecord activityClientRecord, android.content.res.Configuration configuration) {
        android.app.Activity activity = activityClientRecord.activity;
        int windowingMode = configuration.windowConfiguration.getWindowingMode();
        int i = activityClientRecord.mLastReportedWindowingMode;
        if (i == windowingMode) {
            return;
        }
        if (windowingMode == 2) {
            activity.dispatchPictureInPictureModeChanged(true, configuration);
        } else if (i == 2) {
            activity.dispatchPictureInPictureModeChanged(false, configuration);
        }
        boolean inMultiWindowMode = android.app.WindowConfiguration.inMultiWindowMode(i);
        boolean inMultiWindowMode2 = android.app.WindowConfiguration.inMultiWindowMode(windowingMode);
        if (inMultiWindowMode != inMultiWindowMode2) {
            activity.dispatchMultiWindowModeChanged(inMultiWindowMode2, configuration);
        }
        activityClientRecord.mLastReportedWindowingMode = windowingMode;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void applyPendingApplicationInfoChanges(java.lang.String str) {
        android.content.pm.ApplicationInfo remove;
        synchronized (this.mResourcesManager) {
            remove = this.mPendingAppInfoUpdates.remove(str);
        }
        if (remove == null) {
            return;
        }
        handleApplicationInfoChanged(remove);
    }

    public void handleSystemApplicationInfoChanged(android.content.pm.ApplicationInfo applicationInfo) {
        com.android.internal.util.Preconditions.checkState(this.mSystemThread, "Must only be called in the system process");
        handleApplicationInfoChanged(applicationInfo);
    }

    /* JADX WARN: Code restructure failed: missing block: B:23:0x005c, code lost:
    
        r5 = r1;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void handleApplicationInfoChanged(android.content.pm.ApplicationInfo applicationInfo) {
        android.app.LoadedApk loadedApk;
        android.app.LoadedApk loadedApk2;
        synchronized (this.mResourcesManager) {
            java.lang.ref.WeakReference<android.app.LoadedApk> weakReference = this.mPackages.get(applicationInfo.packageName);
            loadedApk = weakReference != null ? weakReference.get() : null;
            java.lang.ref.WeakReference<android.app.LoadedApk> weakReference2 = this.mResourcePackages.get(applicationInfo.packageName);
            loadedApk2 = weakReference2 != null ? weakReference2.get() : null;
            for (android.app.ActivityThread.ActivityClientRecord activityClientRecord : this.mActivities.values()) {
                if (activityClientRecord.activityInfo.applicationInfo.packageName.equals(applicationInfo.packageName)) {
                    activityClientRecord.activityInfo.applicationInfo = applicationInfo;
                    if (loadedApk == null && loadedApk2 == null) {
                        loadedApk = activityClientRecord.packageInfo;
                    }
                    android.app.LoadedApk loadedApk3 = loadedApk2;
                    activityClientRecord.packageInfo = loadedApk3;
                }
            }
        }
        if (loadedApk != null) {
            java.util.ArrayList arrayList = new java.util.ArrayList();
            android.app.LoadedApk.makePaths(this, loadedApk.getApplicationInfo(), arrayList);
            loadedApk.updateApplicationInfo(applicationInfo, arrayList);
        }
        if (loadedApk2 != null) {
            java.util.ArrayList arrayList2 = new java.util.ArrayList();
            android.app.LoadedApk.makePaths(this, loadedApk2.getApplicationInfo(), arrayList2);
            loadedApk2.updateApplicationInfo(applicationInfo, arrayList2);
        }
        android.content.res.ResourcesImpl impl = getApplication().getResources().getImpl();
        synchronized (this.mResourcesManager) {
            this.mResourcesManager.applyAllPendingAppInfoUpdates();
        }
        android.content.res.ResourcesImpl impl2 = getApplication().getResources().getImpl();
        if (impl != impl2 && !java.util.Arrays.equals(impl.getAssets().getApkAssets(), impl2.getAssets().getApkAssets())) {
            java.util.List asList = java.util.Arrays.asList(impl.getAssets().getApkPaths());
            java.util.List asList2 = java.util.Arrays.asList(impl2.getAssets().getApkPaths());
            java.util.ArrayList arrayList3 = new java.util.ArrayList(asList);
            arrayList3.removeAll(asList2);
            java.util.ArrayList arrayList4 = new java.util.ArrayList(asList2);
            arrayList4.removeAll(asList);
            android.util.Slog.i(TAG, "ApplicationInfo updating for " + applicationInfo.packageName + ", new timestamp: " + applicationInfo.createTimestamp + "\nassets removed: " + arrayList3 + "\nassets added: " + arrayList4);
        }
    }

    @Override // android.app.ClientTransactionHandler
    public void updatePendingActivityConfiguration(android.os.IBinder iBinder, android.content.res.Configuration configuration) {
        synchronized (this.mPendingOverrideConfigs) {
            android.content.res.Configuration configuration2 = this.mPendingOverrideConfigs.get(iBinder);
            if (configuration2 == null || configuration2.isOtherSeqNewer(configuration)) {
                this.mPendingOverrideConfigs.put(iBinder, configuration);
            }
        }
    }

    @Override // android.app.ClientTransactionHandler
    public void handleActivityConfigurationChanged(android.app.ActivityThread.ActivityClientRecord activityClientRecord, android.content.res.Configuration configuration, int i, android.window.ActivityWindowInfo activityWindowInfo) {
        handleActivityConfigurationChanged(activityClientRecord, configuration, i, activityWindowInfo, true);
    }

    void handleActivityConfigurationChanged(android.app.ActivityThread.ActivityClientRecord activityClientRecord, android.content.res.Configuration configuration, int i, android.window.ActivityWindowInfo activityWindowInfo, boolean z) {
        synchronized (this.mPendingOverrideConfigs) {
            if (configuration.isOtherSeqNewer(this.mPendingOverrideConfigs.get(activityClientRecord.token))) {
                return;
            }
            this.mPendingOverrideConfigs.remove(activityClientRecord.token);
            if (i == -1) {
                i = activityClientRecord.activity.getDisplayId();
            }
            boolean isDifferentDisplay = android.window.ConfigurationHelper.isDifferentDisplay(activityClientRecord.activity.getDisplayId(), i);
            if (activityClientRecord.overrideConfig != null && !activityClientRecord.overrideConfig.isOtherSeqNewer(configuration) && !isDifferentDisplay) {
                return;
            }
            activityClientRecord.overrideConfig = configuration;
            activityClientRecord.mActivityWindowInfo = activityWindowInfo;
            android.view.ViewRootImpl viewRootImpl = activityClientRecord.activity.mDecor != null ? activityClientRecord.activity.mDecor.getViewRootImpl() : null;
            android.content.res.Configuration performConfigurationChangedForActivity = performConfigurationChangedForActivity(activityClientRecord, this.mConfigurationController.getCompatConfiguration(), isDifferentDisplay ? i : activityClientRecord.activity.getDisplayId(), z);
            if (viewRootImpl != null) {
                if (isDifferentDisplay) {
                    viewRootImpl.onMovedToDisplay(i, performConfigurationChangedForActivity);
                }
                viewRootImpl.updateConfiguration(i);
            }
            this.mSomeActivitiesChanged = true;
        }
    }

    final void handleProfilerControl(boolean z, android.app.ProfilerInfo profilerInfo, int i) {
        try {
            if (z) {
                try {
                    this.mProfiler.setProfiler(profilerInfo);
                    this.mProfiler.startProfiling();
                } catch (java.lang.RuntimeException e) {
                    android.util.Slog.w(TAG, "Profiling failed on path " + profilerInfo.profileFile + " -- can the process access this path?");
                }
                return;
            }
            this.mProfiler.stopProfiling();
        } finally {
            profilerInfo.closeFd();
        }
    }

    public void stopProfiling() {
        if (this.mProfiler != null) {
            this.mProfiler.stopProfiling();
        }
    }

    static void handleDumpHeap(android.app.ActivityThread.DumpHeapData dumpHeapData) {
        android.os.ParcelFileDescriptor parcelFileDescriptor;
        if (dumpHeapData.runGc) {
            java.lang.System.gc();
            java.lang.System.runFinalization();
            java.lang.System.gc();
        }
        try {
            parcelFileDescriptor = dumpHeapData.fd;
        } catch (java.io.IOException e) {
            if (dumpHeapData.managed) {
                android.util.Slog.w(TAG, "Managed heap dump failed on path " + dumpHeapData.path + " -- can the process access this path?", e);
            } else {
                android.util.Slog.w(TAG, "Failed to dump heap", e);
            }
        } catch (java.lang.RuntimeException e2) {
            android.util.Slog.wtf(TAG, "Heap dumper threw a runtime exception", e2);
        }
        try {
            if (dumpHeapData.managed) {
                android.os.Debug.dumpHprofData(dumpHeapData.path, parcelFileDescriptor.getFileDescriptor());
            } else if (dumpHeapData.mallocInfo) {
                android.os.Debug.dumpNativeMallocInfo(parcelFileDescriptor.getFileDescriptor());
            } else {
                android.os.Debug.dumpNativeHeap(parcelFileDescriptor.getFileDescriptor());
            }
            if (parcelFileDescriptor != null) {
                parcelFileDescriptor.close();
            }
            try {
                android.app.ActivityManager.getService().dumpHeapFinished(dumpHeapData.path);
                if (dumpHeapData.finishCallback != null) {
                    dumpHeapData.finishCallback.sendResult(null);
                }
            } catch (android.os.RemoteException e3) {
                throw e3.rethrowFromSystemServer();
            }
        } finally {
        }
    }

    final void handleDispatchPackageBroadcast(int i, java.lang.String[] strArr) {
        boolean z;
        boolean z2 = false;
        switch (i) {
            case 0:
            case 2:
                boolean z3 = i == 0;
                if (strArr != null) {
                    synchronized (this.mResourcesManager) {
                        for (int length = strArr.length - 1; length >= 0; length--) {
                            if (!z2) {
                                java.lang.ref.WeakReference<android.app.LoadedApk> weakReference = this.mPackages.get(strArr[length]);
                                if (weakReference != null && weakReference.get() != null) {
                                    z2 = true;
                                } else {
                                    java.lang.ref.WeakReference<android.app.LoadedApk> weakReference2 = this.mResourcePackages.get(strArr[length]);
                                    if (weakReference2 != null && weakReference2.get() != null) {
                                        z2 = true;
                                    }
                                }
                            }
                            if (z3) {
                                this.mPackages.remove(strArr[length]);
                                this.mResourcePackages.remove(strArr[length]);
                            }
                        }
                    }
                    break;
                }
                break;
            case 3:
                if (strArr != null) {
                    java.util.ArrayList arrayList = new java.util.ArrayList();
                    synchronized (this.mResourcesManager) {
                        z = false;
                        for (int length2 = strArr.length - 1; length2 >= 0; length2--) {
                            java.lang.String str = strArr[length2];
                            java.lang.ref.WeakReference<android.app.LoadedApk> weakReference3 = this.mPackages.get(str);
                            android.app.LoadedApk loadedApk = weakReference3 != null ? weakReference3.get() : null;
                            if (loadedApk != null) {
                                z = true;
                            } else {
                                java.lang.ref.WeakReference<android.app.LoadedApk> weakReference4 = this.mResourcePackages.get(str);
                                android.app.LoadedApk loadedApk2 = weakReference4 != null ? weakReference4.get() : null;
                                if (loadedApk2 == null) {
                                    loadedApk = loadedApk2;
                                } else {
                                    z = true;
                                    loadedApk = loadedApk2;
                                }
                            }
                            if (loadedApk != null) {
                                arrayList.add(str);
                                try {
                                    android.content.pm.ApplicationInfo applicationInfo = sPackageManager.getApplicationInfo(str, 1024L, android.os.UserHandle.myUserId());
                                    if (applicationInfo != null) {
                                        if (this.mActivities.size() > 0) {
                                            for (android.app.ActivityThread.ActivityClientRecord activityClientRecord : this.mActivities.values()) {
                                                if (activityClientRecord.activityInfo.applicationInfo.packageName.equals(str)) {
                                                    activityClientRecord.activityInfo.applicationInfo = applicationInfo;
                                                    activityClientRecord.packageInfo = loadedApk;
                                                }
                                            }
                                        }
                                        java.lang.String[] strArr2 = {loadedApk.getResDir()};
                                        java.util.ArrayList arrayList2 = new java.util.ArrayList();
                                        android.app.LoadedApk.makePaths(this, loadedApk.getApplicationInfo(), arrayList2);
                                        loadedApk.updateApplicationInfo(applicationInfo, arrayList2);
                                        this.mResourcesManager.appendPendingAppInfoUpdate(strArr2, applicationInfo);
                                        this.mResourcesManager.applyAllPendingAppInfoUpdates();
                                    }
                                } catch (android.os.RemoteException e) {
                                }
                            } else {
                                android.util.Slog.e(TAG, "Package [" + strArr[length2] + "] reported as REPLACED, but missing application info. Assuming REMOVED.");
                                this.mPackages.remove(strArr[length2]);
                                this.mResourcePackages.remove(strArr[length2]);
                            }
                        }
                    }
                    try {
                        getPackageManager().notifyPackagesReplacedReceived((java.lang.String[]) arrayList.toArray(new java.lang.String[0]));
                    } catch (android.os.RemoteException e2) {
                    }
                    z2 = z;
                    break;
                }
                break;
        }
        android.app.ApplicationPackageManager.handlePackageBroadcast(i, strArr, z2);
    }

    final void handleLowMemory() {
        java.util.ArrayList<android.content.ComponentCallbacks2> collectComponentCallbacks = collectComponentCallbacks(true);
        int size = collectComponentCallbacks.size();
        for (int i = 0; i < size; i++) {
            collectComponentCallbacks.get(i).onLowMemory();
        }
        if (android.os.Process.myUid() != 1000) {
            android.util.EventLog.writeEvent(SQLITE_MEM_RELEASED_EVENT_LOG_TAG, android.database.sqlite.SQLiteDatabase.releaseMemory());
        }
        android.graphics.Canvas.freeCaches();
        android.graphics.Canvas.freeTextLayoutCaches();
        com.android.internal.os.BinderInternal.forceGc("mem");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleTrimMemory(int i) {
        if (android.os.Trace.isTagEnabled(64L)) {
            android.os.Trace.traceBegin(64L, "trimMemory: " + i);
        }
        if (i >= 80) {
            try {
                android.app.PropertyInvalidatedCache.onTrimMemory();
            } catch (java.lang.Throwable th) {
                android.os.Trace.traceEnd(64L);
                throw th;
            }
        }
        java.util.ArrayList<android.content.ComponentCallbacks2> collectComponentCallbacks = collectComponentCallbacks(true);
        int size = collectComponentCallbacks.size();
        for (int i2 = 0; i2 < size; i2++) {
            collectComponentCallbacks.get(i2).onTrimMemory(i);
        }
        android.os.Trace.traceEnd(64L);
        android.view.WindowManagerGlobal.getInstance().trimMemory(i);
        if (android.os.SystemProperties.getInt("debug.am.run_gc_trim_level", Integer.MAX_VALUE) <= i) {
            unscheduleGcIdler();
            doGcIfNeeded("tm");
        }
        if (android.os.SystemProperties.getInt("debug.am.run_mallopt_trim_level", Integer.MAX_VALUE) <= i) {
            unschedulePurgeIdler();
            purgePendingResources();
        }
    }

    private void setupGraphicsSupport(android.content.Context context) {
        android.os.Trace.traceBegin(64L, "setupGraphicsSupport");
        if (!"android".equals(context.getPackageName())) {
            java.io.File cacheDir = context.getCacheDir();
            if (cacheDir == null) {
                android.util.Log.v(TAG, "Unable to initialize \"java.io.tmpdir\" property due to missing cache directory");
            } else {
                java.lang.String absolutePath = cacheDir.getAbsolutePath();
                java.lang.System.setProperty("java.io.tmpdir", absolutePath);
                try {
                    android.system.Os.setenv("TMPDIR", absolutePath, true);
                } catch (android.system.ErrnoException e) {
                    android.util.Log.w(TAG, "Unable to initialize $TMPDIR", e);
                }
            }
            android.content.Context createDeviceProtectedStorageContext = context.createDeviceProtectedStorageContext();
            java.io.File codeCacheDir = createDeviceProtectedStorageContext.getCodeCacheDir();
            java.io.File cacheDir2 = createDeviceProtectedStorageContext.getCacheDir();
            if (codeCacheDir == null || cacheDir2 == null) {
                android.util.Log.w(TAG, "Unable to use shader/script cache: missing code-cache directory");
            } else {
                try {
                    if (getPackageManager().getPackagesForUid(android.os.Process.myUid()) != null) {
                        android.graphics.HardwareRenderer.setupDiskCache(cacheDir2);
                        android.renderscript.RenderScriptCacheDir.setupDiskCache(codeCacheDir);
                    }
                } catch (android.os.RemoteException e2) {
                    android.os.Trace.traceEnd(64L);
                    throw e2.rethrowFromSystemServer();
                }
            }
        }
        android.os.GraphicsEnvironment.getInstance().setup(context, this.mCoreSettings);
        android.os.Trace.traceEnd(64L);
    }

    private java.lang.String getInstrumentationLibrary(android.content.pm.ApplicationInfo applicationInfo, android.content.pm.InstrumentationInfo instrumentationInfo) {
        if (applicationInfo.primaryCpuAbi != null && applicationInfo.secondaryCpuAbi != null && applicationInfo.secondaryCpuAbi.equals(instrumentationInfo.secondaryCpuAbi)) {
            java.lang.String instructionSet = dalvik.system.VMRuntime.getInstructionSet(applicationInfo.secondaryCpuAbi);
            java.lang.String str = android.os.SystemProperties.get("ro.dalvik.vm.isa." + instructionSet);
            if (!str.isEmpty()) {
                instructionSet = str;
            }
            if (dalvik.system.VMRuntime.getRuntime().vmInstructionSet().equals(instructionSet)) {
                return instrumentationInfo.secondaryNativeLibraryDir;
            }
        }
        return instrumentationInfo.nativeLibraryDir;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Can't wrap try/catch for region: R(20:0|1|(1:3)|4|(1:6)|7|(2:9|(64:11|12|(1:14)|15|(1:17)|18|(1:20)(1:190)|21|22|23|24|11f|29|(1:31)(1:182)|32|(1:34)|(1:36)|37|(1:39)|40|(2:42|(1:44)(1:180))(1:181)|45|(1:47)(1:179)|(1:178)(1:51)|52|(1:177)|(1:174)|(1:173)(1:62)|63|(1:65)|66|(1:68)(1:172)|69|70|71|(1:73)|75|(4:77|78|79|80)(1:168)|81|(1:83)|(1:85)(1:163)|86|(1:88)(1:162)|89|(2:91|(1:93)(2:94|(1:96)))|97|98|99|2d1|(1:105)|106|(1:110)|111|112|113|114|115|(1:140)|119|(3:130|131|(1:135))|121|122|123|124))|191|12|(0)|15|(0)|18|(0)(0)|21|22|23|24|11f|(1:(0))) */
    /* JADX WARN: Code restructure failed: missing block: B:189:0x0113, code lost:
    
        android.util.Slog.e(android.app.ActivityThread.TAG, "Failed to parse serialized system font map");
        android.graphics.Typeface.loadPreinstalledSystemFontMap();
     */
    /* JADX WARN: Finally extract failed */
    /* JADX WARN: Removed duplicated region for block: B:14:0x00d1  */
    /* JADX WARN: Removed duplicated region for block: B:17:0x00de  */
    /* JADX WARN: Removed duplicated region for block: B:190:0x00ee  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x00ec  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x0120 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void handleBindApplication(android.app.ActivityThread.AppBindData appBindData) {
        java.lang.String str;
        java.lang.Boolean bool;
        android.content.pm.InstrumentationInfo instrumentationInfo;
        boolean z;
        int i;
        this.mDdmSyncStageUpdater.next(android.os.DdmSyncState.Stage.Bind);
        dalvik.system.VMRuntime.registerSensitiveThread();
        java.lang.String str2 = android.os.SystemProperties.get("debug.allocTracker.stackDepth");
        if (str2.length() != 0) {
            dalvik.system.VMDebug.setAllocTrackerStackDepth(java.lang.Integer.parseInt(str2));
        }
        if (appBindData.trackAllocation) {
            org.apache.harmony.dalvik.ddmc.DdmVmInternal.setRecentAllocationsTrackingEnabled(true);
        }
        android.os.Process.setStartTimes(android.os.SystemClock.elapsedRealtime(), android.os.SystemClock.uptimeMillis(), appBindData.startRequestedElapsedTime, appBindData.startRequestedUptime);
        android.app.AppCompatCallbacks.install(appBindData.disabledCompatChanges);
        dalvik.system.AppSpecializationHooks.handleCompatChangesBeforeBindingApplication();
        initZipPathValidatorCallback();
        this.mBoundApplication = appBindData;
        this.mConfigurationController.setConfiguration(appBindData.config);
        this.mConfigurationController.setCompatConfiguration(appBindData.config);
        this.mConfiguration = this.mConfigurationController.getConfiguration();
        this.mCompatibilityInfo = appBindData.compatInfo;
        this.mProfiler = new android.app.ActivityThread.Profiler();
        if (appBindData.initProfilerInfo != null) {
            this.mProfiler.profileFile = appBindData.initProfilerInfo.profileFile;
            this.mProfiler.profileFd = appBindData.initProfilerInfo.profileFd;
            this.mProfiler.samplingInterval = appBindData.initProfilerInfo.samplingInterval;
            this.mProfiler.autoStopProfiler = appBindData.initProfilerInfo.autoStopProfiler;
            this.mProfiler.streamingOutput = appBindData.initProfilerInfo.streamingOutput;
            this.mProfiler.mClockType = appBindData.initProfilerInfo.clockType;
            if (appBindData.initProfilerInfo.attachAgentDuringBind) {
                str = appBindData.initProfilerInfo.agent;
                android.os.Process.setArgV0(appBindData.processName);
                android.ddm.DdmHandleAppName.setAppName(appBindData.processName, appBindData.appInfo.packageName, android.os.UserHandle.myUserId());
                dalvik.system.VMRuntime.setProcessPackageName(appBindData.appInfo.packageName);
                this.mDdmSyncStageUpdater.next(android.os.DdmSyncState.Stage.Named);
                dalvik.system.VMRuntime.setProcessDataDirectory(appBindData.appInfo.dataDir);
                if (this.mProfiler.profileFd != null) {
                    this.mProfiler.startProfiling();
                }
                if (appBindData.appInfo.targetSdkVersion <= 12) {
                    android.os.AsyncTask.setDefaultExecutor(android.os.AsyncTask.THREAD_POOL_EXECUTOR);
                }
                android.util.UtilConfig.setThrowExceptionForUpperArrayOutOfBounds(appBindData.appInfo.targetSdkVersion < 29);
                android.os.Message.updateCheckRecycle(appBindData.appInfo.targetSdkVersion);
                android.graphics.Compatibility.setTargetSdkVersion(appBindData.appInfo.targetSdkVersion);
                java.util.TimeZone.setDefault(null);
                android.os.LocaleList.setDefault(appBindData.config.getLocales());
                android.graphics.Typeface.setSystemFontMap(appBindData.mSerializedSystemFontMap);
                synchronized (this.mResourcesManager) {
                    this.mResourcesManager.applyConfigurationToResources(appBindData.config, appBindData.compatInfo);
                    this.mCurDefaultDisplayDpi = appBindData.config.densityDpi;
                    this.mConfigurationController.applyCompatConfiguration();
                }
                boolean z2 = appBindData.sdkSandboxClientAppPackage != null;
                appBindData.info = getPackageInfo(appBindData.appInfo, this.mCompatibilityInfo, null, false, true, false, z2);
                if (z2) {
                    appBindData.info.setSdkSandboxStorage(appBindData.sdkSandboxClientAppVolumeUuid, appBindData.sdkSandboxClientAppPackage);
                }
                if (str != null) {
                    handleAttachAgent(str, appBindData.info);
                }
                if ((appBindData.appInfo.flags & 8192) == 0) {
                    this.mDensityCompatMode = true;
                    android.graphics.Bitmap.setDefaultDensity(160);
                }
                this.mConfigurationController.updateDefaultDensity(appBindData.config.densityDpi);
                java.lang.String string = this.mCoreSettings.getString(android.provider.Settings.System.TIME_12_24);
                if (string == null) {
                    bool = null;
                } else {
                    bool = "24".equals(string) ? java.lang.Boolean.TRUE : java.lang.Boolean.FALSE;
                }
                java.text.DateFormat.set24HourTimePref(bool);
                updateDebugViewAttributeState();
                android.os.StrictMode.initThreadDefaults(appBindData.appInfo);
                android.os.StrictMode.initVmDefaults(appBindData.appInfo);
                boolean z3 = (appBindData.appInfo.flags & 2) != 0;
                boolean z4 = z3 || appBindData.appInfo.isProfileable();
                android.os.Trace.setAppTracingAllowed(z4);
                if ((z4 || android.os.Build.IS_DEBUGGABLE) && appBindData.enableBinderTracking) {
                    android.os.Binder.enableStackTracking();
                }
                if (z4 || android.os.Build.IS_DEBUGGABLE) {
                    nInitZygoteChildHeapProfiling();
                }
                android.graphics.HardwareRenderer.setDebuggingEnabled(z3 || android.os.Build.IS_DEBUGGABLE);
                android.graphics.HardwareRenderer.setPackageName(appBindData.appInfo.packageName);
                android.graphics.HardwareRenderer.setContextForInit(getSystemContext());
                if (appBindData.persistent) {
                    android.graphics.HardwareRenderer.setIsSystemOrPersistent();
                }
                if (appBindData.instrumentationName != null) {
                    instrumentationInfo = prepareInstrumentation(appBindData);
                } else {
                    instrumentationInfo = null;
                }
                final android.app.IActivityManager service = android.app.ActivityManager.getService();
                android.app.ContextImpl createAppContext = android.app.ContextImpl.createAppContext(this, appBindData.info);
                this.mConfigurationController.updateLocaleListFromAppContext(createAppContext);
                android.os.Trace.traceBegin(64L, "Setup proxies");
                try {
                    if (android.os.ServiceManager.getService(android.content.Context.CONNECTIVITY_SERVICE) != null) {
                        android.net.Proxy.setHttpProxyConfiguration(((android.net.ConnectivityManager) createAppContext.getSystemService(android.net.ConnectivityManager.class)).getDefaultProxy());
                    }
                    android.os.Trace.traceEnd(64L);
                    if (!android.os.Process.isIsolated()) {
                        int allowThreadDiskWritesMask = android.os.StrictMode.allowThreadDiskWritesMask();
                        try {
                            setupGraphicsSupport(createAppContext);
                        } finally {
                            android.os.StrictMode.setThreadPolicyMask(allowThreadDiskWritesMask);
                        }
                    } else {
                        android.graphics.HardwareRenderer.setIsolatedProcess(true);
                    }
                    android.os.Trace.traceBegin(64L, "NetworkSecurityConfigProvider.install");
                    android.security.net.config.NetworkSecurityConfigProvider.install(createAppContext);
                    android.os.Trace.traceEnd(64L);
                    if (!android.os.Process.isIsolated()) {
                        android.net.TrafficStats.init(createAppContext);
                    }
                    if (instrumentationInfo != null) {
                        initInstrumentation(instrumentationInfo, appBindData, createAppContext);
                    } else {
                        this.mInstrumentation = new android.app.Instrumentation();
                        this.mInstrumentation.basicInit(this);
                    }
                    if ((appBindData.appInfo.flags & 1048576) != 0) {
                        dalvik.system.VMRuntime.getRuntime().clearGrowthLimit();
                    } else {
                        dalvik.system.VMRuntime.getRuntime().clampGrowthLimit();
                    }
                    android.os.StrictMode.ThreadPolicy allowThreadDiskWrites = android.os.StrictMode.allowThreadDiskWrites();
                    android.os.StrictMode.ThreadPolicy threadPolicy = android.os.StrictMode.getThreadPolicy();
                    if (appBindData.debugMode != 0) {
                        this.mDdmSyncStageUpdater.next(android.os.DdmSyncState.Stage.Debugger);
                        if (appBindData.debugMode == 2) {
                            waitForDebugger(appBindData);
                        } else if (appBindData.debugMode == 3) {
                            suspendAllAndSendVmStart(appBindData);
                        }
                    }
                    this.mDdmSyncStageUpdater.next(android.os.DdmSyncState.Stage.Running);
                    try {
                        android.app.Application makeApplicationInner = appBindData.info.makeApplicationInner(appBindData.restrictedBackupMode, null);
                        makeApplicationInner.setAutofillOptions(appBindData.autofillOptions);
                        makeApplicationInner.setContentCaptureOptions(appBindData.contentCaptureOptions);
                        sendMessage(164, appBindData.appInfo.packageName);
                        this.mInitialApplication = makeApplicationInner;
                        synchronized (this) {
                            z = this.mUpdateHttpProxyOnBind;
                        }
                        if (z) {
                            updateHttpProxy(makeApplicationInner);
                        }
                        if (!appBindData.restrictedBackupMode && !com.android.internal.util.ArrayUtils.isEmpty(appBindData.providers)) {
                            installContentProviders(makeApplicationInner, appBindData.providers);
                        }
                        try {
                            this.mInstrumentation.onCreate(appBindData.instrumentationArgs);
                            try {
                                this.mInstrumentation.callApplicationOnCreate(makeApplicationInner);
                            } catch (java.lang.Exception e) {
                                if (!this.mInstrumentation.onException(makeApplicationInner, e)) {
                                    throw new java.lang.RuntimeException("Unable to create application " + makeApplicationInner.getClass().getName() + ": " + e.toString(), e);
                                }
                            }
                            if (appBindData.appInfo.targetSdkVersion < 27 || android.os.StrictMode.getThreadPolicy().equals(threadPolicy)) {
                                android.os.StrictMode.setThreadPolicy(allowThreadDiskWrites);
                            }
                            android.provider.FontsContract.setApplicationContextForResources(createAppContext);
                            if (!android.os.Process.isIsolated()) {
                                try {
                                    android.content.pm.ApplicationInfo applicationInfo = getPackageManager().getApplicationInfo(appBindData.appInfo.packageName, 128L, android.os.UserHandle.myUserId());
                                    if (applicationInfo.metaData != null && (i = applicationInfo.metaData.getInt(android.content.pm.ApplicationInfo.METADATA_PRELOADED_FONTS, 0)) != 0) {
                                        appBindData.info.getResources().preloadFonts(i);
                                    }
                                } catch (android.os.RemoteException e2) {
                                    throw e2.rethrowFromSystemServer();
                                }
                            }
                            try {
                                service.finishAttachApplication(this.mStartSeq);
                                android.os.Binder.setTransactionCallback(new android.os.IBinderCallback() { // from class: android.app.ActivityThread.2
                                    @Override // android.os.IBinderCallback
                                    public void onTransactionError(int i2, int i3, int i4, int i5) {
                                        try {
                                            service.frozenBinderTransactionDetected(i2, i3, i4, i5);
                                        } catch (android.os.RemoteException e3) {
                                            throw e3.rethrowFromSystemServer();
                                        }
                                    }
                                });
                                return;
                            } catch (android.os.RemoteException e3) {
                                throw e3.rethrowFromSystemServer();
                            }
                        } catch (java.lang.Exception e4) {
                            throw new java.lang.RuntimeException("Exception thrown in onCreate() of " + appBindData.instrumentationName + ": " + e4.toString(), e4);
                        }
                    } catch (java.lang.Throwable th) {
                        if (appBindData.appInfo.targetSdkVersion < 27 || android.os.StrictMode.getThreadPolicy().equals(threadPolicy)) {
                            android.os.StrictMode.setThreadPolicy(allowThreadDiskWrites);
                        }
                        throw th;
                    }
                } catch (java.lang.Throwable th2) {
                    android.os.Trace.traceEnd(64L);
                    throw th2;
                }
            }
        }
        str = null;
        android.os.Process.setArgV0(appBindData.processName);
        android.ddm.DdmHandleAppName.setAppName(appBindData.processName, appBindData.appInfo.packageName, android.os.UserHandle.myUserId());
        dalvik.system.VMRuntime.setProcessPackageName(appBindData.appInfo.packageName);
        this.mDdmSyncStageUpdater.next(android.os.DdmSyncState.Stage.Named);
        dalvik.system.VMRuntime.setProcessDataDirectory(appBindData.appInfo.dataDir);
        if (this.mProfiler.profileFd != null) {
        }
        if (appBindData.appInfo.targetSdkVersion <= 12) {
        }
        android.util.UtilConfig.setThrowExceptionForUpperArrayOutOfBounds(appBindData.appInfo.targetSdkVersion < 29);
        android.os.Message.updateCheckRecycle(appBindData.appInfo.targetSdkVersion);
        android.graphics.Compatibility.setTargetSdkVersion(appBindData.appInfo.targetSdkVersion);
        java.util.TimeZone.setDefault(null);
        android.os.LocaleList.setDefault(appBindData.config.getLocales());
        android.graphics.Typeface.setSystemFontMap(appBindData.mSerializedSystemFontMap);
        synchronized (this.mResourcesManager) {
        }
    }

    private void waitForDebugger(android.app.ActivityThread.AppBindData appBindData) {
        android.app.IActivityManager service = android.app.ActivityManager.getService();
        android.util.Slog.w(TAG, "Application " + appBindData.info.getPackageName() + " is waiting for the debugger ...");
        try {
            service.showWaitingForDebugger(this.mAppThread, true);
            android.os.Debug.waitForDebugger();
            try {
                service.showWaitingForDebugger(this.mAppThread, false);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        } catch (android.os.RemoteException e2) {
            throw e2.rethrowFromSystemServer();
        }
    }

    private void suspendAllAndSendVmStart(android.app.ActivityThread.AppBindData appBindData) {
        android.app.IActivityManager service = android.app.ActivityManager.getService();
        android.util.Slog.w(TAG, "Application " + appBindData.info.getPackageName() + " is suspending. Debugger needs to resume to continue.");
        try {
            service.showWaitingForDebugger(this.mAppThread, true);
            android.os.Debug.suspendAllAndSendVmStart();
            try {
                service.showWaitingForDebugger(this.mAppThread, false);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        } catch (android.os.RemoteException e2) {
            throw e2.rethrowFromSystemServer();
        }
    }

    private void initZipPathValidatorCallback() {
        if (android.app.compat.CompatChanges.isChangeEnabled(com.android.internal.os.SafeZipPathValidatorCallback.VALIDATE_ZIP_PATH_FOR_PATH_TRAVERSAL)) {
            dalvik.system.ZipPathValidator.setCallback(new com.android.internal.os.SafeZipPathValidatorCallback());
        } else {
            dalvik.system.ZipPathValidator.clearCallback();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleSetContentCaptureOptionsCallback(java.lang.String str) {
        android.os.IBinder service;
        if (this.mContentCaptureOptionsCallback != null || (service = android.os.ServiceManager.getService(android.content.Context.CONTENT_CAPTURE_MANAGER_SERVICE)) == null) {
            return;
        }
        android.view.contentcapture.IContentCaptureManager asInterface = android.view.contentcapture.IContentCaptureManager.Stub.asInterface(service);
        this.mContentCaptureOptionsCallback = new android.view.contentcapture.IContentCaptureOptionsCallback.Stub() { // from class: android.app.ActivityThread.3
            @Override // android.view.contentcapture.IContentCaptureOptionsCallback
            public void setContentCaptureOptions(android.content.ContentCaptureOptions contentCaptureOptions) throws android.os.RemoteException {
                if (android.app.ActivityThread.this.mInitialApplication != null) {
                    android.app.ActivityThread.this.mInitialApplication.setContentCaptureOptions(contentCaptureOptions);
                }
            }
        };
        try {
            asInterface.registerContentCaptureOptionsCallback(str, this.mContentCaptureOptionsCallback);
        } catch (android.os.RemoteException e) {
            android.util.Slog.w(TAG, "registerContentCaptureOptionsCallback() failed: " + str, e);
            this.mContentCaptureOptionsCallback = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleInstrumentWithoutRestart(android.app.ActivityThread.AppBindData appBindData) {
        try {
            appBindData.compatInfo = android.content.res.CompatibilityInfo.DEFAULT_COMPATIBILITY_INFO;
            appBindData.info = getPackageInfoNoCheck(appBindData.appInfo);
            this.mInstrumentingWithoutRestart = true;
            initInstrumentation(prepareInstrumentation(appBindData), appBindData, android.app.ContextImpl.createAppContext(this, appBindData.info));
            try {
                this.mInstrumentation.onCreate(appBindData.instrumentationArgs);
            } catch (java.lang.Exception e) {
                throw new java.lang.RuntimeException("Exception thrown in onCreate() of " + appBindData.instrumentationName + ": " + e.toString(), e);
            }
        } catch (java.lang.Exception e2) {
            android.util.Slog.e(TAG, "Error in handleInstrumentWithoutRestart", e2);
        }
    }

    private android.content.pm.InstrumentationInfo prepareInstrumentation(android.app.ActivityThread.AppBindData appBindData) {
        try {
            android.content.pm.InstrumentationInfo instrumentationInfoAsUser = getPackageManager().getInstrumentationInfoAsUser(appBindData.instrumentationName, 0, android.os.UserHandle.myUserId());
            if (instrumentationInfoAsUser == null) {
                throw new java.lang.RuntimeException("Unable to find instrumentation info for: " + appBindData.instrumentationName);
            }
            if (!java.util.Objects.equals(appBindData.appInfo.primaryCpuAbi, instrumentationInfoAsUser.primaryCpuAbi) || !java.util.Objects.equals(appBindData.appInfo.secondaryCpuAbi, instrumentationInfoAsUser.secondaryCpuAbi)) {
                android.util.Slog.w(TAG, "Package uses different ABI(s) than its instrumentation: package[" + appBindData.appInfo.packageName + "]: " + appBindData.appInfo.primaryCpuAbi + ", " + appBindData.appInfo.secondaryCpuAbi + " instrumentation[" + instrumentationInfoAsUser.packageName + "]: " + instrumentationInfoAsUser.primaryCpuAbi + ", " + instrumentationInfoAsUser.secondaryCpuAbi);
            }
            this.mInstrumentationPackageName = instrumentationInfoAsUser.packageName;
            this.mInstrumentationAppDir = instrumentationInfoAsUser.sourceDir;
            this.mInstrumentationSplitAppDirs = instrumentationInfoAsUser.splitSourceDirs;
            this.mInstrumentationLibDir = getInstrumentationLibrary(appBindData.appInfo, instrumentationInfoAsUser);
            this.mInstrumentedAppDir = appBindData.info.getAppDir();
            this.mInstrumentedSplitAppDirs = appBindData.info.getSplitAppDirs();
            this.mInstrumentedLibDir = appBindData.info.getLibDir();
            return instrumentationInfoAsUser;
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    private void initInstrumentation(android.content.pm.InstrumentationInfo instrumentationInfo, android.app.ActivityThread.AppBindData appBindData, android.app.ContextImpl contextImpl) {
        android.content.pm.ApplicationInfo applicationInfo;
        android.content.pm.ApplicationInfo applicationInfo2;
        android.app.ContextImpl createAppContext;
        try {
            applicationInfo = getPackageManager().getApplicationInfo(instrumentationInfo.packageName, 0L, android.os.UserHandle.myUserId());
        } catch (android.os.RemoteException e) {
            applicationInfo = null;
        }
        if (applicationInfo != null) {
            applicationInfo2 = applicationInfo;
        } else {
            applicationInfo2 = new android.content.pm.ApplicationInfo();
        }
        instrumentationInfo.copyTo(applicationInfo2);
        applicationInfo2.initForUser(android.os.UserHandle.myUserId());
        android.app.LoadedApk packageInfo = getPackageInfo(applicationInfo2, appBindData.compatInfo, contextImpl.getClassLoader(), false, true, false);
        if (appBindData.isSdkInSandbox) {
            createAppContext = contextImpl;
        } else {
            createAppContext = android.app.ContextImpl.createAppContext(this, packageInfo, contextImpl.getOpPackageName());
        }
        try {
            this.mInstrumentation = (android.app.Instrumentation) createAppContext.getClassLoader().loadClass(appBindData.instrumentationName.getClassName()).newInstance();
            this.mInstrumentation.init(this, createAppContext, contextImpl, new android.content.ComponentName(instrumentationInfo.packageName, instrumentationInfo.name), appBindData.instrumentationWatcher, appBindData.instrumentationUiAutomationConnection);
            if (this.mProfiler.profileFile != null && !instrumentationInfo.handleProfiling && this.mProfiler.profileFd == null) {
                this.mProfiler.handlingProfiling = true;
                java.io.File file = new java.io.File(this.mProfiler.profileFile);
                file.getParentFile().mkdirs();
                android.os.Debug.startMethodTracing(file.toString(), 8388608);
            }
        } catch (java.lang.Exception e2) {
            throw new java.lang.RuntimeException("Unable to instantiate instrumentation " + appBindData.instrumentationName + ": " + e2.toString(), e2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleFinishInstrumentationWithoutRestart() {
        this.mInstrumentation.onDestroy();
        this.mInstrumentationPackageName = null;
        this.mInstrumentationAppDir = null;
        this.mInstrumentationSplitAppDirs = null;
        this.mInstrumentationLibDir = null;
        this.mInstrumentedAppDir = null;
        this.mInstrumentedSplitAppDirs = null;
        this.mInstrumentedLibDir = null;
        this.mInstrumentingWithoutRestart = false;
    }

    final void finishInstrumentation(int i, android.os.Bundle bundle) {
        android.app.IActivityManager service = android.app.ActivityManager.getService();
        if (this.mProfiler.profileFile != null && this.mProfiler.handlingProfiling && this.mProfiler.profileFd == null) {
            android.os.Debug.stopMethodTracing();
        }
        try {
            service.finishInstrumentation(this.mAppThread, i, bundle);
            if (this.mInstrumentingWithoutRestart) {
                sendMessage(171, null);
            }
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    private void installContentProviders(android.content.Context context, java.util.List<android.content.pm.ProviderInfo> list) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        java.util.Iterator<android.content.pm.ProviderInfo> it = list.iterator();
        while (it.hasNext()) {
            android.app.ContentProviderHolder installProvider = installProvider(context, null, it.next(), false, true, true);
            if (installProvider != null) {
                installProvider.noReleaseNeeded = true;
                arrayList.add(installProvider);
            }
        }
        try {
            android.app.ActivityManager.getService().publishContentProviders(getApplicationThread(), arrayList);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public final android.content.IContentProvider acquireProvider(android.content.Context context, java.lang.String str, int i, boolean z) {
        android.app.ContentProviderHolder contentProviderHolder;
        android.app.ContentProviderHolder contentProvider;
        android.app.ContentProviderHolder contentProviderHolder2;
        android.content.IContentProvider acquireExistingProvider = acquireExistingProvider(context, str, i, z);
        if (acquireExistingProvider != null) {
            return acquireExistingProvider;
        }
        android.app.ActivityThread.ProviderKey getProviderKey = getGetProviderKey(str, i);
        try {
            try {
                synchronized (getProviderKey) {
                    contentProvider = android.app.ActivityManager.getService().getContentProvider(getApplicationThread(), context.getOpPackageName(), str, i, z);
                    if (contentProvider != null && contentProvider.provider == null && !contentProvider.mLocal) {
                        synchronized (getProviderKey.mLock) {
                            if (getProviderKey.mHolder == null) {
                                getProviderKey.mLock.wait(android.content.ContentResolver.CONTENT_PROVIDER_READY_TIMEOUT_MILLIS);
                            }
                            contentProviderHolder2 = getProviderKey.mHolder;
                        }
                        contentProvider = (contentProviderHolder2 == null || contentProviderHolder2.provider != null) ? contentProviderHolder2 : null;
                    }
                }
                synchronized (getProviderKey.mLock) {
                    getProviderKey.mHolder = null;
                }
                contentProviderHolder = contentProvider;
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            } catch (java.lang.InterruptedException e2) {
                synchronized (getProviderKey.mLock) {
                    getProviderKey.mHolder = null;
                    contentProviderHolder = null;
                }
            }
            if (contentProviderHolder != null) {
                return installProvider(context, contentProviderHolder, contentProviderHolder.info, true, contentProviderHolder.noReleaseNeeded, z).provider;
            }
            if (android.os.UserManager.get(context).isUserUnlocked(i)) {
                android.util.Slog.e(TAG, "Failed to find provider info for " + str);
            } else {
                android.util.Slog.w(TAG, "Failed to find provider info for " + str + " (user not unlocked)");
            }
            return null;
        } catch (java.lang.Throwable th) {
            synchronized (getProviderKey.mLock) {
                getProviderKey.mHolder = null;
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public android.app.ActivityThread.ProviderKey getGetProviderKey(java.lang.String str, int i) {
        android.app.ActivityThread.ProviderKey computeIfAbsent;
        android.app.ActivityThread.ProviderKey providerKey = new android.app.ActivityThread.ProviderKey(str, i);
        synchronized (this.mGetProviderKeys) {
            computeIfAbsent = this.mGetProviderKeys.computeIfAbsent(providerKey, new java.util.function.Function() { // from class: android.app.ActivityThread$$ExternalSyntheticLambda1
                @Override // java.util.function.Function
                public final java.lang.Object apply(java.lang.Object obj) {
                    return android.app.ActivityThread.lambda$getGetProviderKey$3((android.app.ActivityThread.ProviderKey) obj);
                }
            });
        }
        return computeIfAbsent;
    }

    static /* synthetic */ android.app.ActivityThread.ProviderKey lambda$getGetProviderKey$3(android.app.ActivityThread.ProviderKey providerKey) {
        return providerKey;
    }

    private final void incProviderRefLocked(android.app.ActivityThread.ProviderRefCount providerRefCount, boolean z) {
        int i = 0;
        if (z) {
            providerRefCount.stableCount++;
            if (providerRefCount.stableCount == 1) {
                if (providerRefCount.removePending) {
                    providerRefCount.removePending = false;
                    this.mH.removeMessages(131, providerRefCount);
                    i = -1;
                }
                try {
                    android.app.ActivityManager.getService().refContentProvider(providerRefCount.holder.connection, 1, i);
                    return;
                } catch (android.os.RemoteException e) {
                    return;
                }
            }
            return;
        }
        providerRefCount.unstableCount++;
        if (providerRefCount.unstableCount == 1) {
            if (providerRefCount.removePending) {
                providerRefCount.removePending = false;
                this.mH.removeMessages(131, providerRefCount);
            } else {
                try {
                    android.app.ActivityManager.getService().refContentProvider(providerRefCount.holder.connection, 0, 1);
                } catch (android.os.RemoteException e2) {
                }
            }
        }
    }

    public final android.content.IContentProvider acquireExistingProvider(android.content.Context context, java.lang.String str, int i, boolean z) {
        synchronized (this.mProviderMap) {
            android.app.ActivityThread.ProviderClientRecord providerClientRecord = this.mProviderMap.get(new android.app.ActivityThread.ProviderKey(str, i));
            if (providerClientRecord == null) {
                return null;
            }
            android.content.IContentProvider iContentProvider = providerClientRecord.mProvider;
            android.os.IBinder asBinder = iContentProvider.asBinder();
            if (!asBinder.isBinderAlive()) {
                android.util.Log.i(TAG, "Acquiring provider " + str + " for user " + i + ": existing object's process dead");
                handleUnstableProviderDiedLocked(asBinder, true);
                return null;
            }
            android.app.ActivityThread.ProviderRefCount providerRefCount = this.mProviderRefCountMap.get(asBinder);
            if (providerRefCount != null) {
                incProviderRefLocked(providerRefCount, z);
            }
            return iContentProvider;
        }
    }

    public final boolean releaseProvider(android.content.IContentProvider iContentProvider, boolean z) {
        int i;
        if (iContentProvider == null) {
            return false;
        }
        android.os.IBinder asBinder = iContentProvider.asBinder();
        synchronized (this.mProviderMap) {
            android.app.ActivityThread.ProviderRefCount providerRefCount = this.mProviderRefCountMap.get(asBinder);
            if (providerRefCount == null) {
                return false;
            }
            if (z) {
                if (providerRefCount.stableCount == 0) {
                    return false;
                }
                providerRefCount.stableCount--;
                if (providerRefCount.stableCount == 0) {
                    i = providerRefCount.unstableCount == 0 ? 1 : 0;
                    try {
                        android.app.ActivityManager.getService().refContentProvider(providerRefCount.holder.connection, -1, i != 0 ? 1 : 0);
                    } catch (android.os.RemoteException e) {
                    }
                    r0 = i;
                }
            } else {
                if (providerRefCount.unstableCount == 0) {
                    return false;
                }
                providerRefCount.unstableCount--;
                if (providerRefCount.unstableCount == 0) {
                    i = providerRefCount.stableCount == 0 ? 1 : 0;
                    if (i == 0) {
                        try {
                            android.app.ActivityManager.getService().refContentProvider(providerRefCount.holder.connection, 0, -1);
                        } catch (android.os.RemoteException e2) {
                        }
                    }
                    r0 = i;
                }
            }
            if (r0 != 0) {
                if (!providerRefCount.removePending) {
                    providerRefCount.removePending = true;
                    this.mH.sendMessageDelayed(this.mH.obtainMessage(131, providerRefCount), 1000L);
                } else {
                    android.util.Slog.w(TAG, "Duplicate remove pending of provider " + providerRefCount.holder.info.name);
                }
            }
            return true;
        }
    }

    final void completeRemoveProvider(android.app.ActivityThread.ProviderRefCount providerRefCount) {
        synchronized (this.mProviderMap) {
            if (providerRefCount.removePending) {
                providerRefCount.removePending = false;
                android.os.IBinder asBinder = providerRefCount.holder.provider.asBinder();
                if (this.mProviderRefCountMap.get(asBinder) == providerRefCount) {
                    this.mProviderRefCountMap.remove(asBinder);
                }
                for (int size = this.mProviderMap.size() - 1; size >= 0; size--) {
                    if (this.mProviderMap.valueAt(size).mProvider.asBinder() == asBinder) {
                        this.mProviderMap.removeAt(size);
                    }
                }
                try {
                    android.app.ActivityManager.getService().removeContentProvider(providerRefCount.holder.connection, false);
                } catch (android.os.RemoteException e) {
                }
            }
        }
    }

    final void handleUnstableProviderDied(android.os.IBinder iBinder, boolean z) {
        synchronized (this.mProviderMap) {
            handleUnstableProviderDiedLocked(iBinder, z);
        }
    }

    final void handleUnstableProviderDiedLocked(android.os.IBinder iBinder, boolean z) {
        android.app.ActivityThread.ProviderRefCount providerRefCount = this.mProviderRefCountMap.get(iBinder);
        if (providerRefCount != null) {
            this.mProviderRefCountMap.remove(iBinder);
            for (int size = this.mProviderMap.size() - 1; size >= 0; size--) {
                android.app.ActivityThread.ProviderClientRecord valueAt = this.mProviderMap.valueAt(size);
                if (valueAt != null && valueAt.mProvider.asBinder() == iBinder) {
                    android.util.Slog.i(TAG, "Removing dead content provider:" + valueAt.mProvider.toString());
                    this.mProviderMap.removeAt(size);
                }
            }
            if (z) {
                try {
                    android.app.ActivityManager.getService().unstableProviderDied(providerRefCount.holder.connection);
                } catch (android.os.RemoteException e) {
                }
            }
        }
    }

    final void appNotRespondingViaProvider(android.os.IBinder iBinder) {
        synchronized (this.mProviderMap) {
            android.app.ActivityThread.ProviderRefCount providerRefCount = this.mProviderRefCountMap.get(iBinder);
            if (providerRefCount != null) {
                try {
                    android.app.ActivityManager.getService().appNotRespondingViaProvider(providerRefCount.holder.connection);
                } catch (android.os.RemoteException e) {
                    throw e.rethrowFromSystemServer();
                }
            }
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private android.app.ActivityThread.ProviderClientRecord installProviderAuthoritiesLocked(android.content.IContentProvider iContentProvider, android.content.ContentProvider contentProvider, android.app.ContentProviderHolder contentProviderHolder) {
        char c;
        java.lang.String[] split = contentProviderHolder.info.authority.split(android.inputmethodservice.navigationbar.NavigationBarInflaterView.GRAVITY_SEPARATOR);
        int userId = android.os.UserHandle.getUserId(contentProviderHolder.info.applicationInfo.uid);
        if (iContentProvider != null) {
            for (java.lang.String str : split) {
                switch (str.hashCode()) {
                    case -845193793:
                        if (str.equals(android.provider.ContactsContract.AUTHORITY)) {
                            c = 0;
                            break;
                        }
                        c = 65535;
                        break;
                    case -456066902:
                        if (str.equals(android.provider.CalendarContract.AUTHORITY)) {
                            c = 4;
                            break;
                        }
                        c = 65535;
                        break;
                    case -172298781:
                        if (str.equals(android.provider.CallLog.AUTHORITY)) {
                            c = 1;
                            break;
                        }
                        c = 65535;
                        break;
                    case 63943420:
                        if (str.equals(android.provider.CallLog.SHADOW_AUTHORITY)) {
                            c = 2;
                            break;
                        }
                        c = 65535;
                        break;
                    case 783201304:
                        if (str.equals(android.app.PropertyInvalidatedCache.MODULE_TELEPHONY)) {
                            c = 6;
                            break;
                        }
                        c = 65535;
                        break;
                    case 1312704747:
                        if (str.equals("downloads")) {
                            c = 5;
                            break;
                        }
                        c = 65535;
                        break;
                    case 1995645513:
                        if (str.equals(android.provider.BlockedNumberContract.AUTHORITY)) {
                            c = 3;
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
                    case 1:
                    case 2:
                    case 3:
                    case 4:
                    case 5:
                    case 6:
                        android.os.Binder.allowBlocking(iContentProvider.asBinder());
                        break;
                }
            }
        }
        android.app.ActivityThread.ProviderClientRecord providerClientRecord = new android.app.ActivityThread.ProviderClientRecord(split, iContentProvider, contentProvider, contentProviderHolder);
        for (java.lang.String str2 : split) {
            android.app.ActivityThread.ProviderKey providerKey = new android.app.ActivityThread.ProviderKey(str2, userId);
            if (this.mProviderMap.get(providerKey) != null) {
                android.util.Slog.w(TAG, "Content provider " + providerClientRecord.mHolder.info.name + " already published as " + str2);
            } else {
                this.mProviderMap.put(providerKey, providerClientRecord);
            }
        }
        return providerClientRecord;
    }

    private android.app.ContentProviderHolder installProvider(android.content.Context context, android.app.ContentProviderHolder contentProviderHolder, android.content.pm.ProviderInfo providerInfo, boolean z, boolean z2, boolean z3) {
        android.content.IContentProvider iContentProvider;
        android.app.ContentProviderHolder contentProviderHolder2;
        android.content.ContentProvider contentProvider = null;
        if (contentProviderHolder == null || contentProviderHolder.provider == null) {
            if (z) {
                android.util.Slog.d(TAG, "Loading provider " + providerInfo.authority + ": " + providerInfo.name);
            }
            android.content.pm.ApplicationInfo applicationInfo = providerInfo.applicationInfo;
            if (!context.getPackageName().equals(applicationInfo.packageName)) {
                if (this.mInitialApplication != null && this.mInitialApplication.getPackageName().equals(applicationInfo.packageName)) {
                    context = this.mInitialApplication;
                } else {
                    try {
                        context = context.createPackageContext(applicationInfo.packageName, 1);
                    } catch (android.content.pm.PackageManager.NameNotFoundException e) {
                        context = null;
                    }
                }
            }
            if (context == null) {
                android.util.Slog.w(TAG, "Unable to get context for package " + applicationInfo.packageName + " while loading content provider " + providerInfo.name);
                return null;
            }
            if (providerInfo.splitName != null) {
                try {
                    context = context.createContextForSplit(providerInfo.splitName);
                } catch (android.content.pm.PackageManager.NameNotFoundException e2) {
                    throw new java.lang.RuntimeException(e2);
                }
            }
            if (providerInfo.attributionTags != null && providerInfo.attributionTags.length > 0) {
                context = context.createAttributionContext(providerInfo.attributionTags[0]);
            }
            try {
                java.lang.ClassLoader classLoader = context.getClassLoader();
                android.app.LoadedApk peekPackageInfo = peekPackageInfo(applicationInfo.packageName, true);
                if (peekPackageInfo == null) {
                    peekPackageInfo = getSystemContext().mPackageInfo;
                }
                android.content.ContentProvider instantiateProvider = peekPackageInfo.getAppFactory().instantiateProvider(classLoader, providerInfo.name);
                android.content.IContentProvider iContentProvider2 = instantiateProvider.getIContentProvider();
                if (iContentProvider2 == null) {
                    android.util.Slog.e(TAG, "Failed to instantiate class " + providerInfo.name + " from sourceDir " + providerInfo.applicationInfo.sourceDir);
                    return null;
                }
                instantiateProvider.attachInfo(context, providerInfo);
                contentProvider = instantiateProvider;
                iContentProvider = iContentProvider2;
            } catch (java.lang.Exception e3) {
                if (this.mInstrumentation.onException(null, e3)) {
                    return null;
                }
                throw new java.lang.RuntimeException("Unable to get provider " + providerInfo.name + ": " + e3.toString(), e3);
            }
        } else {
            iContentProvider = contentProviderHolder.provider;
        }
        synchronized (this.mProviderMap) {
            android.os.IBinder asBinder = iContentProvider.asBinder();
            if (contentProvider != null) {
                android.content.ComponentName componentName = new android.content.ComponentName(providerInfo.packageName, providerInfo.name);
                android.app.ActivityThread.ProviderClientRecord providerClientRecord = this.mLocalProvidersByName.get(componentName);
                if (providerClientRecord != null) {
                    android.content.IContentProvider iContentProvider3 = providerClientRecord.mProvider;
                } else {
                    android.app.ContentProviderHolder contentProviderHolder3 = new android.app.ContentProviderHolder(providerInfo);
                    contentProviderHolder3.provider = iContentProvider;
                    contentProviderHolder3.noReleaseNeeded = true;
                    providerClientRecord = installProviderAuthoritiesLocked(iContentProvider, contentProvider, contentProviderHolder3);
                    this.mLocalProviders.put(asBinder, providerClientRecord);
                    this.mLocalProvidersByName.put(componentName, providerClientRecord);
                }
                contentProviderHolder2 = providerClientRecord.mHolder;
            } else {
                android.app.ActivityThread.ProviderRefCount providerRefCount = this.mProviderRefCountMap.get(asBinder);
                if (providerRefCount != null) {
                    if (!z2) {
                        incProviderRefLocked(providerRefCount, z3);
                        try {
                            android.app.ActivityManager.getService().removeContentProvider(contentProviderHolder.connection, z3);
                        } catch (android.os.RemoteException e4) {
                        }
                    }
                } else {
                    android.app.ActivityThread.ProviderClientRecord installProviderAuthoritiesLocked = installProviderAuthoritiesLocked(iContentProvider, contentProvider, contentProviderHolder);
                    if (z2) {
                        providerRefCount = new android.app.ActivityThread.ProviderRefCount(contentProviderHolder, installProviderAuthoritiesLocked, 1000, 1000);
                    } else if (z3) {
                        providerRefCount = new android.app.ActivityThread.ProviderRefCount(contentProviderHolder, installProviderAuthoritiesLocked, 1, 0);
                    } else {
                        providerRefCount = new android.app.ActivityThread.ProviderRefCount(contentProviderHolder, installProviderAuthoritiesLocked, 0, 1);
                    }
                    this.mProviderRefCountMap.put(asBinder, providerRefCount);
                }
                contentProviderHolder2 = providerRefCount.holder;
            }
        }
        return contentProviderHolder2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleRunIsolatedEntryPoint(java.lang.String str, java.lang.String[] strArr) {
        try {
            java.lang.Class.forName(str).getMethod("main", java.lang.String[].class).invoke(null, strArr);
            java.lang.System.exit(0);
        } catch (java.lang.ReflectiveOperationException e) {
            throw new android.util.AndroidRuntimeException("runIsolatedEntryPoint failed", e);
        }
    }

    private void attach(boolean z, long j) {
        sCurrentActivityThread = this;
        this.mConfigurationController = new android.app.ConfigurationController(this);
        this.mSystemThread = z;
        this.mStartSeq = j;
        this.mDdmSyncStageUpdater.next(android.os.DdmSyncState.Stage.Attach);
        if (!z) {
            android.ddm.DdmHandleAppName.setAppName("<pre-initialized>", android.os.UserHandle.myUserId());
            com.android.internal.os.RuntimeInit.setApplicationObject(this.mAppThread.asBinder());
            try {
                android.app.ActivityManager.getService().attachApplication(this.mAppThread, j);
                com.android.internal.os.BinderInternal.addGcWatcher(new java.lang.Runnable() { // from class: android.app.ActivityThread.4
                    @Override // java.lang.Runnable
                    public void run() {
                        if (!android.app.ActivityThread.this.mSomeActivitiesChanged) {
                            return;
                        }
                        java.lang.Runtime runtime = java.lang.Runtime.getRuntime();
                        if (runtime.totalMemory() - runtime.freeMemory() > (runtime.maxMemory() * 3) / 4) {
                            android.app.ActivityThread.this.mSomeActivitiesChanged = false;
                            try {
                                android.app.ActivityTaskManager.getService().releaseSomeActivities(android.app.ActivityThread.this.mAppThread);
                            } catch (android.os.RemoteException e) {
                                throw e.rethrowFromSystemServer();
                            }
                        }
                    }
                });
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        } else {
            android.ddm.DdmHandleAppName.setAppName("system_process", android.os.UserHandle.myUserId());
            try {
                this.mInstrumentation = new android.app.Instrumentation();
                this.mInstrumentation.basicInit(this);
                this.mInitialApplication = android.app.ContextImpl.createAppContext(this, getSystemContext().mPackageInfo).mPackageInfo.makeApplicationInner(true, null);
                this.mInitialApplication.onCreate();
            } catch (java.lang.Exception e2) {
                throw new java.lang.RuntimeException("Unable to instantiate Application():" + e2.toString(), e2);
            }
        }
        android.view.ViewRootImpl.addConfigCallback(new android.view.ViewRootImpl.ConfigChangedCallback() { // from class: android.app.ActivityThread$$ExternalSyntheticLambda3
            @Override // android.view.ViewRootImpl.ConfigChangedCallback
            public final void onConfigurationChanged(android.content.res.Configuration configuration) {
                android.app.ActivityThread.this.lambda$attach$4(configuration);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$attach$4(android.content.res.Configuration configuration) {
        synchronized (this.mResourcesManager) {
            if (this.mResourcesManager.applyConfigurationToResources(configuration, null)) {
                this.mConfigurationController.updateLocaleListFromAppContext(this.mInitialApplication.getApplicationContext());
                android.content.res.Configuration updatePendingConfiguration = this.mConfigurationController.updatePendingConfiguration(configuration);
                if (updatePendingConfiguration != null) {
                    sendMessage(118, configuration);
                    this.mPendingConfiguration = updatePendingConfiguration;
                }
            }
        }
    }

    public static android.app.ActivityThread systemMain() {
        android.view.ThreadedRenderer.initForSystemProcess();
        android.app.ActivityThread activityThread = new android.app.ActivityThread();
        activityThread.attach(true, 0L);
        return activityThread;
    }

    public static void updateHttpProxy(android.content.Context context) {
        android.net.Proxy.setHttpProxyConfiguration(((android.net.ConnectivityManager) context.getSystemService(android.net.ConnectivityManager.class)).getDefaultProxy());
    }

    public final void installSystemProviders(java.util.List<android.content.pm.ProviderInfo> list) {
        if (list != null) {
            installContentProviders(this.mInitialApplication, list);
        }
    }

    android.os.Bundle getCoreSettings() {
        android.os.Bundle bundle;
        synchronized (this.mCoreSettingsLock) {
            bundle = this.mCoreSettings;
        }
        return bundle;
    }

    public int getIntCoreSetting(java.lang.String str, int i) {
        synchronized (this.mCoreSettingsLock) {
            if (this.mCoreSettings == null) {
                return i;
            }
            return this.mCoreSettings.getInt(str, i);
        }
    }

    public java.lang.String getStringCoreSetting(java.lang.String str, java.lang.String str2) {
        synchronized (this.mCoreSettingsLock) {
            if (this.mCoreSettings == null) {
                return str2;
            }
            return this.mCoreSettings.getString(str, str2);
        }
    }

    float getFloatCoreSetting(java.lang.String str, float f) {
        synchronized (this.mCoreSettingsLock) {
            if (this.mCoreSettings == null) {
                return f;
            }
            return this.mCoreSettings.getFloat(str, f);
        }
    }

    private static class AndroidOs extends libcore.io.ForwardingOs {
        public static void install() {
            libcore.io.Os os;
            do {
                os = libcore.io.Os.getDefault();
            } while (!libcore.io.Os.compareAndSetDefault(os, new android.app.ActivityThread.AndroidOs(os)));
        }

        private AndroidOs(libcore.io.Os os) {
            super(os);
        }

        private java.io.FileDescriptor openDeprecatedDataPath(java.lang.String str, int i) throws android.system.ErrnoException {
            android.net.Uri translateDeprecatedDataPath = android.content.ContentResolver.translateDeprecatedDataPath(str);
            android.util.Log.v(android.app.ActivityThread.TAG, "Redirecting " + str + " to " + translateDeprecatedDataPath);
            android.content.ContentResolver contentResolver = android.app.ActivityThread.currentActivityThread().getApplication().getContentResolver();
            try {
                java.io.FileDescriptor fileDescriptor = new java.io.FileDescriptor();
                fileDescriptor.setInt$(contentResolver.openFileDescriptor(translateDeprecatedDataPath, android.os.FileUtils.translateModePosixToString(i)).detachFd());
                return fileDescriptor;
            } catch (java.io.FileNotFoundException e) {
                throw new android.system.ErrnoException(e.getMessage(), android.system.OsConstants.ENOENT);
            } catch (java.lang.SecurityException e2) {
                throw new android.system.ErrnoException(e2.getMessage(), android.system.OsConstants.EACCES);
            }
        }

        private void deleteDeprecatedDataPath(java.lang.String str) throws android.system.ErrnoException {
            android.net.Uri translateDeprecatedDataPath = android.content.ContentResolver.translateDeprecatedDataPath(str);
            android.util.Log.v(android.app.ActivityThread.TAG, "Redirecting " + str + " to " + translateDeprecatedDataPath);
            try {
                if (android.app.ActivityThread.currentActivityThread().getApplication().getContentResolver().delete(translateDeprecatedDataPath, null, null) == 0) {
                    throw new java.io.FileNotFoundException();
                }
            } catch (java.io.FileNotFoundException e) {
                throw new android.system.ErrnoException(e.getMessage(), android.system.OsConstants.ENOENT);
            } catch (java.lang.SecurityException e2) {
                throw new android.system.ErrnoException(e2.getMessage(), android.system.OsConstants.EACCES);
            }
        }

        public boolean access(java.lang.String str, int i) throws android.system.ErrnoException {
            if (str != null && str.startsWith(android.content.ContentResolver.DEPRECATE_DATA_PREFIX)) {
                libcore.io.IoUtils.closeQuietly(openDeprecatedDataPath(str, android.os.FileUtils.translateModeAccessToPosix(i)));
                return true;
            }
            return super.access(str, i);
        }

        public java.io.FileDescriptor open(java.lang.String str, int i, int i2) throws android.system.ErrnoException {
            if (str != null && str.startsWith(android.content.ContentResolver.DEPRECATE_DATA_PREFIX)) {
                return openDeprecatedDataPath(str, i2);
            }
            return super.open(str, i, i2);
        }

        public android.system.StructStat stat(java.lang.String str) throws android.system.ErrnoException {
            if (str != null && str.startsWith(android.content.ContentResolver.DEPRECATE_DATA_PREFIX)) {
                java.io.FileDescriptor openDeprecatedDataPath = openDeprecatedDataPath(str, android.system.OsConstants.O_RDONLY);
                try {
                    return android.system.Os.fstat(openDeprecatedDataPath);
                } finally {
                    libcore.io.IoUtils.closeQuietly(openDeprecatedDataPath);
                }
            }
            return super.stat(str);
        }

        public void unlink(java.lang.String str) throws android.system.ErrnoException {
            if (str != null && str.startsWith(android.content.ContentResolver.DEPRECATE_DATA_PREFIX)) {
                deleteDeprecatedDataPath(str);
            } else {
                super.unlink(str);
            }
        }

        public void remove(java.lang.String str) throws android.system.ErrnoException {
            if (str != null && str.startsWith(android.content.ContentResolver.DEPRECATE_DATA_PREFIX)) {
                deleteDeprecatedDataPath(str);
            } else {
                super.remove(str);
            }
        }

        public void rename(java.lang.String str, java.lang.String str2) throws android.system.ErrnoException {
            try {
                super.rename(str, str2);
            } catch (android.system.ErrnoException e) {
                if (e.errno == android.system.OsConstants.EXDEV && str.startsWith("/storage/emulated") && str2.startsWith("/storage/emulated")) {
                    android.util.Log.v(android.app.ActivityThread.TAG, "Recovering failed rename " + str + " to " + str2);
                    try {
                        java.nio.file.Files.move(new java.io.File(str).toPath(), new java.io.File(str2).toPath(), java.nio.file.StandardCopyOption.REPLACE_EXISTING);
                        return;
                    } catch (java.io.IOException e2) {
                        android.util.Log.e(android.app.ActivityThread.TAG, "Rename recovery failed ", e2);
                        throw e;
                    }
                }
                throw e;
            }
        }
    }

    public static void main(java.lang.String[] strArr) {
        android.os.Trace.traceBegin(64L, "ActivityThreadMain");
        android.app.ActivityThread.AndroidOs.install();
        dalvik.system.CloseGuard.setEnabled(false);
        android.os.Environment.initForCurrentUser();
        com.android.org.conscrypt.TrustedCertificateStore.setDefaultUserDirectory(android.os.Environment.getUserConfigDirectory(android.os.UserHandle.myUserId()));
        initializeMainlineModules();
        android.os.Process.setArgV0("<pre-initialized>");
        android.os.Looper.prepareMainLooper();
        long j = 0;
        if (strArr != null) {
            for (int length = strArr.length - 1; length >= 0; length--) {
                if (strArr[length] != null && strArr[length].startsWith(PROC_START_SEQ_IDENT)) {
                    j = java.lang.Long.parseLong(strArr[length].substring(PROC_START_SEQ_IDENT.length()));
                }
            }
        }
        android.app.ActivityThread activityThread = new android.app.ActivityThread();
        activityThread.attach(false, j);
        if (sMainThreadHandler == null) {
            sMainThreadHandler = activityThread.getHandler();
        }
        android.os.Trace.traceEnd(64L);
        android.os.Looper.loop();
        throw new java.lang.RuntimeException("Main thread loop unexpectedly exited");
    }

    public static void initializeMainlineModules() {
        android.telephony.TelephonyFrameworkInitializer.setTelephonyServiceManager(new android.os.TelephonyServiceManager());
        android.os.StatsFrameworkInitializer.setStatsServiceManager(new android.os.StatsServiceManager());
        android.media.MediaFrameworkPlatformInitializer.setMediaServiceManager(new android.media.MediaServiceManager());
        android.media.MediaFrameworkInitializer.setMediaServiceManager(new android.media.MediaServiceManager());
        android.bluetooth.BluetoothFrameworkInitializer.setBluetoothServiceManager(new android.os.BluetoothServiceManager());
        android.bluetooth.BluetoothFrameworkInitializer.setBinderCallsStatsInitializer(new java.util.function.Consumer() { // from class: android.app.ActivityThread$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.internal.os.BinderCallsStats.startForBluetooth((android.content.Context) obj);
            }
        });
        android.nfc.NfcFrameworkInitializer.setNfcServiceManager(new android.nfc.NfcServiceManager());
        android.provider.DeviceConfigInitializer.setDeviceConfigServiceManager(new android.provider.DeviceConfigServiceManager());
        android.se.omapi.SeFrameworkInitializer.setSeServiceManager(new android.se.omapi.SeServiceManager());
        if (android.server.Flags.telemetryApisService()) {
            android.os.ProfilingFrameworkInitializer.setProfilingServiceManager(new android.os.ProfilingServiceManager());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void purgePendingResources() {
        android.os.Trace.traceBegin(64L, "purgePendingResources");
        nPurgePendingResources();
        android.os.Trace.traceEnd(64L);
    }

    public static boolean isProtectedComponent(android.content.pm.ActivityInfo activityInfo) {
        return isProtectedComponent(activityInfo, activityInfo.permission);
    }

    public static boolean isProtectedComponent(android.content.pm.ServiceInfo serviceInfo) {
        return isProtectedComponent(serviceInfo, serviceInfo.permission);
    }

    private static boolean isProtectedComponent(android.content.pm.ComponentInfo componentInfo, java.lang.String str) {
        if (!android.os.StrictMode.vmUnsafeIntentLaunchEnabled()) {
            return false;
        }
        if (!componentInfo.exported) {
            return true;
        }
        if (str != null) {
            try {
                android.content.pm.PermissionInfo permissionInfo = getPermissionManager().getPermissionInfo(str, currentOpPackageName(), 0);
                if (permissionInfo != null) {
                    return permissionInfo.getProtection() == 2;
                }
                return false;
            } catch (android.os.RemoteException e) {
            }
        }
        return false;
    }

    public static boolean isProtectedBroadcast(android.content.Intent intent) {
        if (!android.os.StrictMode.vmUnsafeIntentLaunchEnabled()) {
            return false;
        }
        try {
            return getPackageManager().isProtectedBroadcast(intent.getAction());
        } catch (android.os.RemoteException e) {
            return false;
        }
    }

    @Override // android.app.ActivityThreadInternal
    public boolean isInDensityCompatMode() {
        return this.mDensityCompatMode;
    }
}
