package android.view.contentcapture;

/* loaded from: classes4.dex */
public final class ContentCaptureManager {
    public static final int DATA_SHARE_ERROR_CONCURRENT_REQUEST = 2;
    public static final int DATA_SHARE_ERROR_TIMEOUT_INTERRUPTED = 3;
    public static final int DATA_SHARE_ERROR_UNKNOWN = 1;
    public static final boolean DEBUG = false;
    public static final long DEFAULT_CONTENT_PROTECTION_ALLOWLIST_DELAY_MS = 30000;
    public static final long DEFAULT_CONTENT_PROTECTION_ALLOWLIST_TIMEOUT_MS = 250;
    public static final long DEFAULT_CONTENT_PROTECTION_AUTO_DISCONNECT_TIMEOUT_MS = 3000;
    public static final int DEFAULT_CONTENT_PROTECTION_BUFFER_SIZE = 150;
    public static final java.lang.String DEFAULT_CONTENT_PROTECTION_OPTIONAL_GROUPS_CONFIG = "";
    public static final int DEFAULT_CONTENT_PROTECTION_OPTIONAL_GROUPS_THRESHOLD = 0;
    public static final java.lang.String DEFAULT_CONTENT_PROTECTION_REQUIRED_GROUPS_CONFIG = "";
    public static final boolean DEFAULT_DISABLE_FLUSH_FOR_VIEW_TREE_APPEARING = false;
    public static final boolean DEFAULT_ENABLE_CONTENT_CAPTURE_RECEIVER = true;
    public static final boolean DEFAULT_ENABLE_CONTENT_PROTECTION_RECEIVER = false;
    public static final int DEFAULT_IDLE_FLUSHING_FREQUENCY_MS = 5000;
    public static final int DEFAULT_LOG_HISTORY_SIZE = 10;
    public static final int DEFAULT_MAX_BUFFER_SIZE = 500;
    public static final int DEFAULT_TEXT_CHANGE_FLUSHING_FREQUENCY_MS = 1000;
    public static final java.lang.String DEVICE_CONFIG_PROPERTY_CONTENT_PROTECTION_ALLOWLIST_DELAY_MS = "content_protection_allowlist_delay_ms";
    public static final java.lang.String DEVICE_CONFIG_PROPERTY_CONTENT_PROTECTION_ALLOWLIST_TIMEOUT_MS = "content_protection_allowlist_timeout_ms";
    public static final java.lang.String DEVICE_CONFIG_PROPERTY_CONTENT_PROTECTION_AUTO_DISCONNECT_TIMEOUT = "content_protection_auto_disconnect_timeout_ms";
    public static final java.lang.String DEVICE_CONFIG_PROPERTY_CONTENT_PROTECTION_BUFFER_SIZE = "content_protection_buffer_size";
    public static final java.lang.String DEVICE_CONFIG_PROPERTY_CONTENT_PROTECTION_OPTIONAL_GROUPS_CONFIG = "content_protection_optional_groups_config";
    public static final java.lang.String DEVICE_CONFIG_PROPERTY_CONTENT_PROTECTION_OPTIONAL_GROUPS_THRESHOLD = "content_protection_optional_groups_threshold";
    public static final java.lang.String DEVICE_CONFIG_PROPERTY_CONTENT_PROTECTION_REQUIRED_GROUPS_CONFIG = "content_protection_required_groups_config";
    public static final java.lang.String DEVICE_CONFIG_PROPERTY_DISABLE_FLUSH_FOR_VIEW_TREE_APPEARING = "disable_flush_for_view_tree_appearing";
    public static final java.lang.String DEVICE_CONFIG_PROPERTY_ENABLE_CONTENT_PROTECTION_RECEIVER = "enable_content_protection_receiver";
    public static final java.lang.String DEVICE_CONFIG_PROPERTY_IDLE_FLUSH_FREQUENCY = "idle_flush_frequency";
    public static final java.lang.String DEVICE_CONFIG_PROPERTY_IDLE_UNBIND_TIMEOUT = "idle_unbind_timeout";
    public static final java.lang.String DEVICE_CONFIG_PROPERTY_LOGGING_LEVEL = "logging_level";
    public static final java.lang.String DEVICE_CONFIG_PROPERTY_LOG_HISTORY_SIZE = "log_history_size";
    public static final java.lang.String DEVICE_CONFIG_PROPERTY_MAX_BUFFER_SIZE = "max_buffer_size";
    public static final java.lang.String DEVICE_CONFIG_PROPERTY_REPORT_LIST_VIEW_CHILDREN = "report_list_view_children";
    public static final java.lang.String DEVICE_CONFIG_PROPERTY_SERVICE_EXPLICITLY_ENABLED = "service_explicitly_enabled";
    public static final java.lang.String DEVICE_CONFIG_PROPERTY_TEXT_CHANGE_FLUSH_FREQUENCY = "text_change_flush_frequency";
    public static final java.lang.String DUMPABLE_NAME = "ContentCaptureManager";
    public static final int LOGGING_LEVEL_DEBUG = 1;
    public static final int LOGGING_LEVEL_OFF = 0;
    public static final int LOGGING_LEVEL_VERBOSE = 2;

    @android.annotation.SystemApi
    public static final int NO_SESSION_ID = 0;
    public static final int RESULT_CODE_FALSE = 2;
    public static final int RESULT_CODE_OK = 0;
    public static final int RESULT_CODE_SECURITY_EXCEPTION = -1;
    public static final int RESULT_CODE_TRUE = 1;
    private static final int SYNC_CALLS_TIMEOUT_MS = 5000;
    private android.os.Handler mContentCaptureHandler;
    private final com.android.internal.util.RingBuffer<android.view.contentcapture.ContentCaptureEvent> mContentProtectionEventBuffer;
    private final android.view.contentcapture.ContentCaptureManager.StrippedContext mContext;
    private final android.view.contentcapture.ContentCaptureManager.LocalDataShareAdapterResourceManager mDataShareAdapterResourceManager;
    private android.view.contentcapture.ContentCaptureManager.Dumper mDumpable;
    private int mFlags;
    private final java.lang.Object mLock = new java.lang.Object();
    private android.view.contentcapture.ContentCaptureSession mMainSession;
    final android.content.ContentCaptureOptions mOptions;
    private final android.view.contentcapture.IContentCaptureManager mService;
    private android.os.Handler mUiHandler;
    private static final java.lang.String TAG = android.view.contentcapture.ContentCaptureManager.class.getSimpleName();
    public static final java.util.List<java.util.List<java.lang.String>> DEFAULT_CONTENT_PROTECTION_REQUIRED_GROUPS = java.util.Collections.emptyList();
    public static final java.util.List<java.util.List<java.lang.String>> DEFAULT_CONTENT_PROTECTION_OPTIONAL_GROUPS = java.util.Collections.emptyList();

    public interface ContentCaptureClient {
        android.content.ComponentName contentCaptureClientGetComponentName();
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface DataShareError {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface LoggingLevel {
    }

    /* JADX INFO: Access modifiers changed from: private */
    interface MyRunnable {
        void run(com.android.internal.util.SyncResultReceiver syncResultReceiver) throws android.os.RemoteException;
    }

    public static class StrippedContext {
        final java.lang.String mContext;
        final java.lang.String mPackageName;
        final int mUserId;

        public StrippedContext(android.content.Context context) {
            this.mPackageName = context.getPackageName();
            this.mContext = context.toString();
            this.mUserId = context.getUserId();
        }

        public java.lang.String toString() {
            return this.mContext;
        }

        public java.lang.String getPackageName() {
            return this.mPackageName;
        }

        public int getUserId() {
            return this.mUserId;
        }
    }

    public ContentCaptureManager(android.content.Context context, android.view.contentcapture.IContentCaptureManager iContentCaptureManager, android.content.ContentCaptureOptions contentCaptureOptions) {
        java.util.Objects.requireNonNull(context, "context cannot be null");
        this.mContext = new android.view.contentcapture.ContentCaptureManager.StrippedContext(context);
        this.mService = (android.view.contentcapture.IContentCaptureManager) java.util.Objects.requireNonNull(iContentCaptureManager, "service cannot be null");
        this.mOptions = (android.content.ContentCaptureOptions) java.util.Objects.requireNonNull(contentCaptureOptions, "options cannot be null");
        android.view.contentcapture.ContentCaptureHelper.setLoggingLevel(this.mOptions.loggingLevel);
        setFlushViewTreeAppearingEventDisabled(this.mOptions.disableFlushForViewTreeAppearing);
        if (android.view.contentcapture.ContentCaptureHelper.sVerbose) {
            android.util.Log.v(TAG, "Constructor for " + context.getPackageName());
        }
        this.mDataShareAdapterResourceManager = new android.view.contentcapture.ContentCaptureManager.LocalDataShareAdapterResourceManager();
        if (this.mOptions.contentProtectionOptions.enableReceiver && this.mOptions.contentProtectionOptions.bufferSize > 0) {
            this.mContentProtectionEventBuffer = new com.android.internal.util.RingBuffer<>(android.view.contentcapture.ContentCaptureEvent.class, this.mOptions.contentProtectionOptions.bufferSize);
        } else {
            this.mContentProtectionEventBuffer = null;
        }
    }

    public android.view.contentcapture.ContentCaptureSession getMainContentCaptureSession() {
        android.view.contentcapture.ContentCaptureSession contentCaptureSession;
        synchronized (this.mLock) {
            if (this.mMainSession == null) {
                this.mMainSession = prepareMainSession();
                if (android.view.contentcapture.ContentCaptureHelper.sVerbose) {
                    android.util.Log.v(TAG, "getMainContentCaptureSession(): created " + this.mMainSession);
                }
            }
            contentCaptureSession = this.mMainSession;
        }
        return contentCaptureSession;
    }

    private android.view.contentcapture.ContentCaptureSession prepareMainSession() {
        if (android.view.contentcapture.flags.Flags.runOnBackgroundThreadEnabled()) {
            return new android.view.contentcapture.MainContentCaptureSessionV2(this.mContext, this, prepareUiHandler(), prepareContentCaptureHandler(), this.mService);
        }
        return new android.view.contentcapture.MainContentCaptureSession(this.mContext, this, prepareUiHandler(), this.mService);
    }

    private android.os.Handler prepareContentCaptureHandler() {
        if (this.mContentCaptureHandler == null) {
            this.mContentCaptureHandler = com.android.internal.os.BackgroundThread.getHandler();
        }
        return this.mContentCaptureHandler;
    }

    private android.os.Handler prepareUiHandler() {
        if (this.mUiHandler == null) {
            this.mUiHandler = android.os.Handler.createAsync(android.os.Looper.getMainLooper());
        }
        return this.mUiHandler;
    }

    public void onActivityCreated(android.os.IBinder iBinder, android.os.IBinder iBinder2, android.content.ComponentName componentName) {
        if (this.mOptions.lite) {
            return;
        }
        synchronized (this.mLock) {
            getMainContentCaptureSession().start(iBinder, iBinder2, componentName, this.mFlags);
        }
    }

    public void onActivityResumed() {
        if (this.mOptions.lite) {
            return;
        }
        getMainContentCaptureSession().notifySessionResumed();
    }

    public void onActivityPaused() {
        if (this.mOptions.lite) {
            return;
        }
        getMainContentCaptureSession().notifySessionPaused();
    }

    public void onActivityDestroyed() {
        if (this.mOptions.lite) {
            return;
        }
        getMainContentCaptureSession().destroy();
    }

    public void flush(int i) {
        if (this.mOptions.lite) {
            return;
        }
        getMainContentCaptureSession().flush(i);
    }

    public android.content.ComponentName getServiceComponentName() {
        if (!isContentCaptureEnabled() && !this.mOptions.lite) {
            return null;
        }
        com.android.internal.util.SyncResultReceiver syncResultReceiver = new com.android.internal.util.SyncResultReceiver(5000);
        try {
            this.mService.getServiceComponentName(syncResultReceiver);
            return (android.content.ComponentName) syncResultReceiver.getParcelableResult();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        } catch (com.android.internal.util.SyncResultReceiver.TimeoutException e2) {
            throw new java.lang.RuntimeException("Fail to get service componentName.");
        }
    }

    public static android.content.ComponentName getServiceSettingsComponentName() {
        android.os.IBinder checkService = android.os.ServiceManager.checkService(android.content.Context.CONTENT_CAPTURE_MANAGER_SERVICE);
        if (checkService == null) {
            return null;
        }
        android.view.contentcapture.IContentCaptureManager asInterface = android.view.contentcapture.IContentCaptureManager.Stub.asInterface(checkService);
        com.android.internal.util.SyncResultReceiver syncResultReceiver = new com.android.internal.util.SyncResultReceiver(5000);
        try {
            asInterface.getServiceSettingsActivity(syncResultReceiver);
            if (syncResultReceiver.getIntResult() == -1) {
                throw new java.lang.SecurityException(syncResultReceiver.getStringResult());
            }
            return (android.content.ComponentName) syncResultReceiver.getParcelableResult();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        } catch (com.android.internal.util.SyncResultReceiver.TimeoutException e2) {
            android.util.Log.e(TAG, "Fail to get service settings componentName: " + e2);
            return null;
        }
    }

    public boolean isContentCaptureEnabled() {
        android.view.contentcapture.ContentCaptureSession contentCaptureSession;
        if (this.mOptions.lite) {
            return false;
        }
        synchronized (this.mLock) {
            contentCaptureSession = this.mMainSession;
        }
        return contentCaptureSession == null || !contentCaptureSession.isDisabled();
    }

    public java.util.Set<android.view.contentcapture.ContentCaptureCondition> getContentCaptureConditions() {
        if (!isContentCaptureEnabled() && !this.mOptions.lite) {
            return null;
        }
        try {
            return android.view.contentcapture.ContentCaptureHelper.toSet(syncRun(new android.view.contentcapture.ContentCaptureManager.MyRunnable() { // from class: android.view.contentcapture.ContentCaptureManager$$ExternalSyntheticLambda0
                @Override // android.view.contentcapture.ContentCaptureManager.MyRunnable
                public final void run(com.android.internal.util.SyncResultReceiver syncResultReceiver) {
                    android.view.contentcapture.ContentCaptureManager.this.lambda$getContentCaptureConditions$0(syncResultReceiver);
                }
            }).getParcelableListResult());
        } catch (com.android.internal.util.SyncResultReceiver.TimeoutException e) {
            throw new java.lang.RuntimeException("Fail to get content capture conditions.");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getContentCaptureConditions$0(com.android.internal.util.SyncResultReceiver syncResultReceiver) throws android.os.RemoteException {
        this.mService.getContentCaptureConditions(this.mContext.getPackageName(), syncResultReceiver);
    }

    public void setContentCaptureEnabled(boolean z) {
        android.view.contentcapture.ContentCaptureSession contentCaptureSession;
        if (android.view.contentcapture.ContentCaptureHelper.sDebug) {
            android.util.Log.d(TAG, "setContentCaptureEnabled(): setting to " + z + " for " + this.mContext);
        }
        synchronized (this.mLock) {
            if (z) {
                this.mFlags &= -2;
            } else {
                this.mFlags |= 1;
            }
            contentCaptureSession = this.mMainSession;
        }
        if (contentCaptureSession != null) {
            contentCaptureSession.setDisabled(!z);
        }
    }

    public void updateWindowAttributes(android.view.WindowManager.LayoutParams layoutParams) {
        boolean z;
        android.view.contentcapture.ContentCaptureSession contentCaptureSession;
        if (android.view.contentcapture.ContentCaptureHelper.sDebug) {
            android.util.Log.d(TAG, "updateWindowAttributes(): window flags=" + layoutParams.flags);
        }
        boolean z2 = (layoutParams.flags & 8192) != 0;
        synchronized (this.mLock) {
            z = (this.mFlags & 1) != 0;
            if (z2) {
                this.mFlags |= 2;
            } else {
                this.mFlags &= -3;
            }
            contentCaptureSession = this.mMainSession;
        }
        if (contentCaptureSession != null && !z) {
            contentCaptureSession.setDisabled(z2);
        }
    }

    public void setFlushViewTreeAppearingEventDisabled(boolean z) {
        if (android.view.contentcapture.ContentCaptureHelper.sDebug) {
            android.util.Log.d(TAG, "setFlushViewTreeAppearingEventDisabled(): setting to " + z);
        }
        synchronized (this.mLock) {
            if (z) {
                this.mFlags |= 8;
            } else {
                this.mFlags &= -9;
            }
        }
    }

    public boolean getFlushViewTreeAppearingEventDisabled() {
        boolean z;
        synchronized (this.mLock) {
            z = (this.mFlags & 8) != 0;
        }
        return z;
    }

    @android.annotation.SystemApi
    public boolean isContentCaptureFeatureEnabled() {
        try {
            int intResult = syncRun(new android.view.contentcapture.ContentCaptureManager.MyRunnable() { // from class: android.view.contentcapture.ContentCaptureManager$$ExternalSyntheticLambda1
                @Override // android.view.contentcapture.ContentCaptureManager.MyRunnable
                public final void run(com.android.internal.util.SyncResultReceiver syncResultReceiver) {
                    android.view.contentcapture.ContentCaptureManager.this.lambda$isContentCaptureFeatureEnabled$1(syncResultReceiver);
                }
            }).getIntResult();
            switch (intResult) {
                case 1:
                    return true;
                case 2:
                    return false;
                default:
                    android.util.Log.wtf(TAG, "received invalid result: " + intResult);
                    return false;
            }
        } catch (com.android.internal.util.SyncResultReceiver.TimeoutException e) {
            android.util.Log.e(TAG, "Fail to get content capture feature enable status: " + e);
            return false;
        }
        android.util.Log.e(TAG, "Fail to get content capture feature enable status: " + e);
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$isContentCaptureFeatureEnabled$1(com.android.internal.util.SyncResultReceiver syncResultReceiver) throws android.os.RemoteException {
        this.mService.isContentCaptureFeatureEnabled(syncResultReceiver);
    }

    public void removeData(android.view.contentcapture.DataRemovalRequest dataRemovalRequest) {
        java.util.Objects.requireNonNull(dataRemovalRequest);
        try {
            this.mService.removeData(dataRemovalRequest);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void shareData(android.view.contentcapture.DataShareRequest dataShareRequest, java.util.concurrent.Executor executor, android.view.contentcapture.DataShareWriteAdapter dataShareWriteAdapter) {
        java.util.Objects.requireNonNull(dataShareRequest);
        java.util.Objects.requireNonNull(dataShareWriteAdapter);
        java.util.Objects.requireNonNull(executor);
        try {
            this.mService.shareData(dataShareRequest, new android.view.contentcapture.ContentCaptureManager.DataShareAdapterDelegate(executor, dataShareWriteAdapter, this.mDataShareAdapterResourceManager));
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    private com.android.internal.util.SyncResultReceiver syncRun(android.view.contentcapture.ContentCaptureManager.MyRunnable myRunnable) {
        com.android.internal.util.SyncResultReceiver syncResultReceiver = new com.android.internal.util.SyncResultReceiver(5000);
        try {
            myRunnable.run(syncResultReceiver);
            if (syncResultReceiver.getIntResult() == -1) {
                throw new java.lang.SecurityException(syncResultReceiver.getStringResult());
            }
            return syncResultReceiver;
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        } catch (com.android.internal.util.SyncResultReceiver.TimeoutException e2) {
            throw new java.lang.RuntimeException("Fail to get syn run result from SyncResultReceiver.");
        }
    }

    public void addDumpable(android.app.Activity activity) {
        if (this.mDumpable == null) {
            this.mDumpable = new android.view.contentcapture.ContentCaptureManager.Dumper();
        }
        activity.addDumpable(this.mDumpable);
    }

    public com.android.internal.util.RingBuffer<android.view.contentcapture.ContentCaptureEvent> getContentProtectionEventBuffer() {
        return this.mContentProtectionEventBuffer;
    }

    private final class Dumper implements android.util.Dumpable {
        private Dumper() {
        }

        @Override // android.util.Dumpable
        public void dump(java.io.PrintWriter printWriter, java.lang.String[] strArr) {
            printWriter.print("");
            printWriter.println(android.view.contentcapture.ContentCaptureManager.DUMPABLE_NAME);
            java.lang.String str = "  ";
            synchronized (android.view.contentcapture.ContentCaptureManager.this.mLock) {
                printWriter.print(str);
                printWriter.print("isContentCaptureEnabled(): ");
                printWriter.println(android.view.contentcapture.ContentCaptureManager.this.isContentCaptureEnabled());
                printWriter.print(str);
                printWriter.print("Debug: ");
                printWriter.print(android.view.contentcapture.ContentCaptureHelper.sDebug);
                printWriter.print(" Verbose: ");
                printWriter.println(android.view.contentcapture.ContentCaptureHelper.sVerbose);
                printWriter.print(str);
                printWriter.print("Context: ");
                printWriter.println(android.view.contentcapture.ContentCaptureManager.this.mContext);
                printWriter.print(str);
                printWriter.print("User: ");
                printWriter.println(android.view.contentcapture.ContentCaptureManager.this.mContext.getUserId());
                printWriter.print(str);
                printWriter.print("Service: ");
                printWriter.println(android.view.contentcapture.ContentCaptureManager.this.mService);
                printWriter.print(str);
                printWriter.print("Flags: ");
                printWriter.println(android.view.contentcapture.ContentCaptureManager.this.mFlags);
                printWriter.print(str);
                printWriter.print("Options: ");
                android.view.contentcapture.ContentCaptureManager.this.mOptions.dumpShort(printWriter);
                printWriter.println();
                if (android.view.contentcapture.ContentCaptureManager.this.mMainSession != null) {
                    printWriter.print(str);
                    printWriter.println("Main session:");
                    android.view.contentcapture.ContentCaptureManager.this.mMainSession.dump(str + "  ", printWriter);
                } else {
                    printWriter.print(str);
                    printWriter.println("No sessions");
                }
            }
        }

        @Override // android.util.Dumpable
        public java.lang.String getDumpableName() {
            return android.view.contentcapture.ContentCaptureManager.DUMPABLE_NAME;
        }
    }

    public static void resetTemporaryService(int i) {
        android.view.contentcapture.IContentCaptureManager service = getService();
        if (service == null) {
            android.util.Log.e(TAG, "IContentCaptureManager is null");
        }
        try {
            service.resetTemporaryService(i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public static void setTemporaryService(int i, java.lang.String str, int i2) {
        android.view.contentcapture.IContentCaptureManager service = getService();
        if (service == null) {
            android.util.Log.e(TAG, "IContentCaptureManager is null");
        }
        try {
            service.setTemporaryService(i, str, i2);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public static void setDefaultServiceEnabled(int i, boolean z) {
        android.view.contentcapture.IContentCaptureManager service = getService();
        if (service == null) {
            android.util.Log.e(TAG, "IContentCaptureManager is null");
        }
        try {
            service.setDefaultServiceEnabled(i, z);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    private static android.view.contentcapture.IContentCaptureManager getService() {
        return android.view.contentcapture.IContentCaptureManager.Stub.asInterface(android.os.ServiceManager.getService(android.content.Context.CONTENT_CAPTURE_MANAGER_SERVICE));
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class DataShareAdapterDelegate extends android.view.contentcapture.IDataShareWriteAdapter.Stub {
        private final java.lang.ref.WeakReference<android.view.contentcapture.ContentCaptureManager.LocalDataShareAdapterResourceManager> mResourceManagerReference;

        private DataShareAdapterDelegate(java.util.concurrent.Executor executor, android.view.contentcapture.DataShareWriteAdapter dataShareWriteAdapter, android.view.contentcapture.ContentCaptureManager.LocalDataShareAdapterResourceManager localDataShareAdapterResourceManager) {
            java.util.Objects.requireNonNull(executor);
            java.util.Objects.requireNonNull(dataShareWriteAdapter);
            java.util.Objects.requireNonNull(localDataShareAdapterResourceManager);
            localDataShareAdapterResourceManager.initializeForDelegate(this, dataShareWriteAdapter, executor);
            this.mResourceManagerReference = new java.lang.ref.WeakReference<>(localDataShareAdapterResourceManager);
        }

        @Override // android.view.contentcapture.IDataShareWriteAdapter
        public void write(final android.os.ParcelFileDescriptor parcelFileDescriptor) throws android.os.RemoteException {
            executeAdapterMethodLocked(new java.util.function.Consumer() { // from class: android.view.contentcapture.ContentCaptureManager$DataShareAdapterDelegate$$ExternalSyntheticLambda2
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    ((android.view.contentcapture.DataShareWriteAdapter) obj).onWrite(android.os.ParcelFileDescriptor.this);
                }
            }, "onWrite");
        }

        @Override // android.view.contentcapture.IDataShareWriteAdapter
        public void error(final int i) throws android.os.RemoteException {
            executeAdapterMethodLocked(new java.util.function.Consumer() { // from class: android.view.contentcapture.ContentCaptureManager$DataShareAdapterDelegate$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    ((android.view.contentcapture.DataShareWriteAdapter) obj).onError(i);
                }
            }, "onError");
            clearHardReferences();
        }

        @Override // android.view.contentcapture.IDataShareWriteAdapter
        public void rejected() throws android.os.RemoteException {
            executeAdapterMethodLocked(new java.util.function.Consumer() { // from class: android.view.contentcapture.ContentCaptureManager$DataShareAdapterDelegate$$ExternalSyntheticLambda3
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    ((android.view.contentcapture.DataShareWriteAdapter) obj).onRejected();
                }
            }, "onRejected");
            clearHardReferences();
        }

        @Override // android.view.contentcapture.IDataShareWriteAdapter
        public void finish() throws android.os.RemoteException {
            clearHardReferences();
        }

        private void executeAdapterMethodLocked(final java.util.function.Consumer<android.view.contentcapture.DataShareWriteAdapter> consumer, java.lang.String str) {
            android.view.contentcapture.ContentCaptureManager.LocalDataShareAdapterResourceManager localDataShareAdapterResourceManager = this.mResourceManagerReference.get();
            if (localDataShareAdapterResourceManager == null) {
                android.util.Slog.w(android.view.contentcapture.ContentCaptureManager.TAG, "Can't execute " + str + "(), resource manager has been GC'ed");
                return;
            }
            final android.view.contentcapture.DataShareWriteAdapter adapter = localDataShareAdapterResourceManager.getAdapter(this);
            java.util.concurrent.Executor executor = localDataShareAdapterResourceManager.getExecutor(this);
            if (adapter == null || executor == null) {
                android.util.Slog.w(android.view.contentcapture.ContentCaptureManager.TAG, "Can't execute " + str + "(), references are null");
                return;
            }
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                executor.execute(new java.lang.Runnable() { // from class: android.view.contentcapture.ContentCaptureManager$DataShareAdapterDelegate$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        consumer.accept(adapter);
                    }
                });
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        private void clearHardReferences() {
            android.view.contentcapture.ContentCaptureManager.LocalDataShareAdapterResourceManager localDataShareAdapterResourceManager = this.mResourceManagerReference.get();
            if (localDataShareAdapterResourceManager == null) {
                android.util.Slog.w(android.view.contentcapture.ContentCaptureManager.TAG, "Can't clear references, resource manager has been GC'ed");
            } else {
                localDataShareAdapterResourceManager.clearHardReferences(this);
            }
        }
    }

    private static class LocalDataShareAdapterResourceManager {
        private java.util.Map<android.view.contentcapture.ContentCaptureManager.DataShareAdapterDelegate, java.util.concurrent.Executor> mExecutorHardReferences;
        private java.util.Map<android.view.contentcapture.ContentCaptureManager.DataShareAdapterDelegate, android.view.contentcapture.DataShareWriteAdapter> mWriteAdapterHardReferences;

        private LocalDataShareAdapterResourceManager() {
            this.mWriteAdapterHardReferences = new java.util.HashMap();
            this.mExecutorHardReferences = new java.util.HashMap();
        }

        void initializeForDelegate(android.view.contentcapture.ContentCaptureManager.DataShareAdapterDelegate dataShareAdapterDelegate, android.view.contentcapture.DataShareWriteAdapter dataShareWriteAdapter, java.util.concurrent.Executor executor) {
            this.mWriteAdapterHardReferences.put(dataShareAdapterDelegate, dataShareWriteAdapter);
            this.mExecutorHardReferences.put(dataShareAdapterDelegate, executor);
        }

        java.util.concurrent.Executor getExecutor(android.view.contentcapture.ContentCaptureManager.DataShareAdapterDelegate dataShareAdapterDelegate) {
            return this.mExecutorHardReferences.get(dataShareAdapterDelegate);
        }

        android.view.contentcapture.DataShareWriteAdapter getAdapter(android.view.contentcapture.ContentCaptureManager.DataShareAdapterDelegate dataShareAdapterDelegate) {
            return this.mWriteAdapterHardReferences.get(dataShareAdapterDelegate);
        }

        void clearHardReferences(android.view.contentcapture.ContentCaptureManager.DataShareAdapterDelegate dataShareAdapterDelegate) {
            this.mWriteAdapterHardReferences.remove(dataShareAdapterDelegate);
            this.mExecutorHardReferences.remove(dataShareAdapterDelegate);
        }
    }
}
