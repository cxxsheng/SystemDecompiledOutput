package android.telephony;

/* loaded from: classes3.dex */
public class MbmsDownloadSession implements java.lang.AutoCloseable {
    public static final java.lang.String DEFAULT_TOP_LEVEL_TEMP_DIRECTORY = "androidMbmsTempFileRoot";
    private static final java.lang.String DESTINATION_SANITY_CHECK_FILE_NAME = "destinationSanityCheckFile";
    public static final java.lang.String EXTRA_MBMS_COMPLETED_FILE_URI = "android.telephony.extra.MBMS_COMPLETED_FILE_URI";
    public static final java.lang.String EXTRA_MBMS_DOWNLOAD_REQUEST = "android.telephony.extra.MBMS_DOWNLOAD_REQUEST";
    public static final java.lang.String EXTRA_MBMS_DOWNLOAD_RESULT = "android.telephony.extra.MBMS_DOWNLOAD_RESULT";
    public static final java.lang.String EXTRA_MBMS_FILE_INFO = "android.telephony.extra.MBMS_FILE_INFO";
    private static final int MAX_SERVICE_ANNOUNCEMENT_SIZE = 10240;

    @android.annotation.SystemApi
    public static final java.lang.String MBMS_DOWNLOAD_SERVICE_ACTION = "android.telephony.action.EmbmsDownload";
    public static final java.lang.String MBMS_DOWNLOAD_SERVICE_OVERRIDE_METADATA = "mbms-download-service-override";
    public static final int RESULT_CANCELLED = 2;
    public static final int RESULT_DOWNLOAD_FAILURE = 6;
    public static final int RESULT_EXPIRED = 3;
    public static final int RESULT_FILE_ROOT_UNREACHABLE = 8;
    public static final int RESULT_IO_ERROR = 4;
    public static final int RESULT_OUT_OF_STORAGE = 7;
    public static final int RESULT_SERVICE_ID_NOT_DEFINED = 5;
    public static final int RESULT_SUCCESSFUL = 1;
    public static final int STATUS_ACTIVELY_DOWNLOADING = 1;
    public static final int STATUS_PENDING_DOWNLOAD = 2;
    public static final int STATUS_PENDING_DOWNLOAD_WINDOW = 4;
    public static final int STATUS_PENDING_REPAIR = 3;
    public static final int STATUS_UNKNOWN = 0;
    private final android.content.Context mContext;
    private final android.telephony.mbms.InternalDownloadSessionCallback mInternalCallback;
    private android.content.ServiceConnection mServiceConnection;
    private int mSubscriptionId;
    private static final java.lang.String LOG_TAG = android.telephony.MbmsDownloadSession.class.getSimpleName();
    private static java.util.concurrent.atomic.AtomicBoolean sIsInitialized = new java.util.concurrent.atomic.AtomicBoolean(false);
    private android.os.IBinder.DeathRecipient mDeathRecipient = new android.os.IBinder.DeathRecipient() { // from class: android.telephony.MbmsDownloadSession.1
        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
            android.telephony.MbmsDownloadSession.this.sendErrorToApp(3, "Received death notification");
        }
    };
    private java.util.concurrent.atomic.AtomicReference<android.telephony.mbms.vendor.IMbmsDownloadService> mService = new java.util.concurrent.atomic.AtomicReference<>(null);
    private final java.util.Map<android.telephony.mbms.DownloadStatusListener, android.telephony.mbms.InternalDownloadStatusListener> mInternalDownloadStatusListeners = new java.util.HashMap();
    private final java.util.Map<android.telephony.mbms.DownloadProgressListener, android.telephony.mbms.InternalDownloadProgressListener> mInternalDownloadProgressListeners = new java.util.HashMap();

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface DownloadResultCode {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface DownloadStatus {
    }

    private MbmsDownloadSession(android.content.Context context, java.util.concurrent.Executor executor, int i, android.telephony.mbms.MbmsDownloadSessionCallback mbmsDownloadSessionCallback) {
        this.mSubscriptionId = -1;
        this.mContext = context;
        this.mSubscriptionId = i;
        this.mInternalCallback = new android.telephony.mbms.InternalDownloadSessionCallback(mbmsDownloadSessionCallback, executor);
    }

    public static android.telephony.MbmsDownloadSession create(android.content.Context context, java.util.concurrent.Executor executor, android.telephony.mbms.MbmsDownloadSessionCallback mbmsDownloadSessionCallback) {
        return create(context, executor, android.telephony.SubscriptionManager.getDefaultSubscriptionId(), mbmsDownloadSessionCallback);
    }

    public static android.telephony.MbmsDownloadSession create(android.content.Context context, java.util.concurrent.Executor executor, int i, final android.telephony.mbms.MbmsDownloadSessionCallback mbmsDownloadSessionCallback) {
        if (!sIsInitialized.compareAndSet(false, true)) {
            throw new java.lang.IllegalStateException("Cannot have two active instances");
        }
        android.telephony.MbmsDownloadSession mbmsDownloadSession = new android.telephony.MbmsDownloadSession(context, executor, i, mbmsDownloadSessionCallback);
        final int bindAndInitialize = mbmsDownloadSession.bindAndInitialize();
        if (bindAndInitialize != 0) {
            sIsInitialized.set(false);
            executor.execute(new java.lang.Runnable() { // from class: android.telephony.MbmsDownloadSession.2
                @Override // java.lang.Runnable
                public void run() {
                    android.telephony.mbms.MbmsDownloadSessionCallback.this.onError(bindAndInitialize, null);
                }
            });
            return null;
        }
        return mbmsDownloadSession;
    }

    public static int getMaximumServiceAnnouncementSize() {
        return 10240;
    }

    private int bindAndInitialize() {
        this.mServiceConnection = new android.content.ServiceConnection() { // from class: android.telephony.MbmsDownloadSession.3
            @Override // android.content.ServiceConnection
            public void onServiceConnected(android.content.ComponentName componentName, android.os.IBinder iBinder) {
                android.telephony.mbms.vendor.IMbmsDownloadService asInterface = android.telephony.mbms.vendor.IMbmsDownloadService.Stub.asInterface(iBinder);
                try {
                    int initialize = asInterface.initialize(android.telephony.MbmsDownloadSession.this.mSubscriptionId, android.telephony.MbmsDownloadSession.this.mInternalCallback);
                    if (initialize == -1) {
                        android.telephony.MbmsDownloadSession.this.close();
                        throw new java.lang.IllegalStateException("Middleware must not return an unknown error code");
                    }
                    if (initialize == 0) {
                        try {
                            asInterface.asBinder().linkToDeath(android.telephony.MbmsDownloadSession.this.mDeathRecipient, 0);
                            android.telephony.MbmsDownloadSession.this.mService.set(asInterface);
                            return;
                        } catch (android.os.RemoteException e) {
                            android.telephony.MbmsDownloadSession.this.sendErrorToApp(3, "Middleware lost during initialization");
                            android.telephony.MbmsDownloadSession.sIsInitialized.set(false);
                            return;
                        }
                    }
                    android.telephony.MbmsDownloadSession.this.sendErrorToApp(initialize, "Error returned during initialization");
                    android.telephony.MbmsDownloadSession.sIsInitialized.set(false);
                } catch (android.os.RemoteException e2) {
                    android.util.Log.e(android.telephony.MbmsDownloadSession.LOG_TAG, "Service died before initialization");
                    android.telephony.MbmsDownloadSession.sIsInitialized.set(false);
                } catch (java.lang.RuntimeException e3) {
                    android.util.Log.e(android.telephony.MbmsDownloadSession.LOG_TAG, "Runtime exception during initialization");
                    android.telephony.MbmsDownloadSession.this.sendErrorToApp(103, e3.toString());
                    android.telephony.MbmsDownloadSession.sIsInitialized.set(false);
                }
            }

            @Override // android.content.ServiceConnection
            public void onServiceDisconnected(android.content.ComponentName componentName) {
                android.util.Log.w(android.telephony.MbmsDownloadSession.LOG_TAG, "bindAndInitialize: Remote service disconnected");
                android.telephony.MbmsDownloadSession.sIsInitialized.set(false);
                android.telephony.MbmsDownloadSession.this.mService.set(null);
            }

            @Override // android.content.ServiceConnection
            public void onNullBinding(android.content.ComponentName componentName) {
                android.util.Log.w(android.telephony.MbmsDownloadSession.LOG_TAG, "bindAndInitialize: Remote service returned null");
                android.telephony.MbmsDownloadSession.this.sendErrorToApp(3, "Middleware service binding returned null");
                android.telephony.MbmsDownloadSession.sIsInitialized.set(false);
                android.telephony.MbmsDownloadSession.this.mService.set(null);
                android.telephony.MbmsDownloadSession.this.mContext.unbindService(this);
            }
        };
        return android.telephony.mbms.MbmsUtils.startBinding(this.mContext, MBMS_DOWNLOAD_SERVICE_ACTION, this.mServiceConnection);
    }

    public void requestUpdateFileServices(java.util.List<java.lang.String> list) {
        android.telephony.mbms.vendor.IMbmsDownloadService iMbmsDownloadService = this.mService.get();
        if (iMbmsDownloadService == null) {
            throw new java.lang.IllegalStateException("Middleware not yet bound");
        }
        try {
            int requestUpdateFileServices = iMbmsDownloadService.requestUpdateFileServices(this.mSubscriptionId, list);
            if (requestUpdateFileServices == -1) {
                close();
                throw new java.lang.IllegalStateException("Middleware must not return an unknown error code");
            }
            if (requestUpdateFileServices != 0) {
                sendErrorToApp(requestUpdateFileServices, null);
            }
        } catch (android.os.RemoteException e) {
            android.util.Log.w(LOG_TAG, "Remote process died");
            this.mService.set(null);
            sIsInitialized.set(false);
            sendErrorToApp(3, null);
        }
    }

    public void addServiceAnnouncement(byte[] bArr) {
        android.telephony.mbms.vendor.IMbmsDownloadService iMbmsDownloadService = this.mService.get();
        if (iMbmsDownloadService == null) {
            throw new java.lang.IllegalStateException("Middleware not yet bound");
        }
        if (bArr.length > 10240) {
            throw new java.lang.IllegalArgumentException("File too large");
        }
        try {
            int addServiceAnnouncement = iMbmsDownloadService.addServiceAnnouncement(this.mSubscriptionId, bArr);
            if (addServiceAnnouncement == -1) {
                close();
                throw new java.lang.IllegalStateException("Middleware must not return an unknown error code");
            }
            if (addServiceAnnouncement != 0) {
                sendErrorToApp(addServiceAnnouncement, null);
            }
        } catch (android.os.RemoteException e) {
            android.util.Log.w(LOG_TAG, "Remote process died");
            this.mService.set(null);
            sIsInitialized.set(false);
            sendErrorToApp(3, null);
        }
    }

    public void setTempFileRootDirectory(java.io.File file) {
        android.telephony.mbms.vendor.IMbmsDownloadService iMbmsDownloadService = this.mService.get();
        if (iMbmsDownloadService == null) {
            throw new java.lang.IllegalStateException("Middleware not yet bound");
        }
        try {
            validateTempFileRootSanity(file);
            try {
                java.lang.String canonicalPath = file.getCanonicalPath();
                try {
                    int tempFileRootDirectory = iMbmsDownloadService.setTempFileRootDirectory(this.mSubscriptionId, canonicalPath);
                    if (tempFileRootDirectory == -1) {
                        close();
                        throw new java.lang.IllegalStateException("Middleware must not return an unknown error code");
                    }
                    if (tempFileRootDirectory != 0) {
                        sendErrorToApp(tempFileRootDirectory, null);
                    } else {
                        this.mContext.getSharedPreferences(android.telephony.mbms.MbmsTempFileProvider.TEMP_FILE_ROOT_PREF_FILE_NAME, 0).edit().putString(android.telephony.mbms.MbmsTempFileProvider.TEMP_FILE_ROOT_PREF_NAME, canonicalPath).apply();
                    }
                } catch (android.os.RemoteException e) {
                    this.mService.set(null);
                    sIsInitialized.set(false);
                    sendErrorToApp(3, null);
                }
            } catch (java.io.IOException e2) {
                throw new java.lang.IllegalArgumentException("Unable to canonicalize the provided path: " + e2);
            }
        } catch (java.io.IOException e3) {
            throw new java.lang.IllegalStateException("Got IOException checking directory sanity");
        }
    }

    private void validateTempFileRootSanity(java.io.File file) throws java.io.IOException {
        if (!file.exists()) {
            throw new java.lang.IllegalArgumentException("Provided directory does not exist");
        }
        if (!file.isDirectory()) {
            throw new java.lang.IllegalArgumentException("Provided File is not a directory");
        }
        java.lang.String canonicalPath = file.getCanonicalPath();
        if (this.mContext.getDataDir().getCanonicalPath().equals(canonicalPath)) {
            throw new java.lang.IllegalArgumentException("Temp file root cannot be your data dir");
        }
        if (this.mContext.getCacheDir().getCanonicalPath().equals(canonicalPath)) {
            throw new java.lang.IllegalArgumentException("Temp file root cannot be your cache dir");
        }
        if (this.mContext.getFilesDir().getCanonicalPath().equals(canonicalPath)) {
            throw new java.lang.IllegalArgumentException("Temp file root cannot be your files dir");
        }
    }

    public java.io.File getTempFileRootDirectory() {
        java.lang.String string = this.mContext.getSharedPreferences(android.telephony.mbms.MbmsTempFileProvider.TEMP_FILE_ROOT_PREF_FILE_NAME, 0).getString(android.telephony.mbms.MbmsTempFileProvider.TEMP_FILE_ROOT_PREF_NAME, null);
        if (string != null) {
            return new java.io.File(string);
        }
        return null;
    }

    public void download(android.telephony.mbms.DownloadRequest downloadRequest) {
        android.telephony.mbms.vendor.IMbmsDownloadService iMbmsDownloadService = this.mService.get();
        if (iMbmsDownloadService == null) {
            throw new java.lang.IllegalStateException("Middleware not yet bound");
        }
        if (this.mContext.getSharedPreferences(android.telephony.mbms.MbmsTempFileProvider.TEMP_FILE_ROOT_PREF_FILE_NAME, 0).getString(android.telephony.mbms.MbmsTempFileProvider.TEMP_FILE_ROOT_PREF_NAME, null) == null) {
            java.io.File file = new java.io.File(this.mContext.getFilesDir(), DEFAULT_TOP_LEVEL_TEMP_DIRECTORY);
            file.mkdirs();
            setTempFileRootDirectory(file);
        }
        checkDownloadRequestDestination(downloadRequest);
        try {
            int download = iMbmsDownloadService.download(downloadRequest);
            if (download == 0) {
                writeDownloadRequestToken(downloadRequest);
            } else {
                if (download == -1) {
                    close();
                    throw new java.lang.IllegalStateException("Middleware must not return an unknown error code");
                }
                sendErrorToApp(download, null);
            }
        } catch (android.os.RemoteException e) {
            this.mService.set(null);
            sIsInitialized.set(false);
            sendErrorToApp(3, null);
        }
    }

    public java.util.List<android.telephony.mbms.DownloadRequest> listPendingDownloads() {
        android.telephony.mbms.vendor.IMbmsDownloadService iMbmsDownloadService = this.mService.get();
        if (iMbmsDownloadService == null) {
            throw new java.lang.IllegalStateException("Middleware not yet bound");
        }
        try {
            return iMbmsDownloadService.listPendingDownloads(this.mSubscriptionId);
        } catch (android.os.RemoteException e) {
            this.mService.set(null);
            sIsInitialized.set(false);
            sendErrorToApp(3, null);
            return java.util.Collections.emptyList();
        }
    }

    public void addStatusListener(android.telephony.mbms.DownloadRequest downloadRequest, java.util.concurrent.Executor executor, android.telephony.mbms.DownloadStatusListener downloadStatusListener) {
        android.telephony.mbms.vendor.IMbmsDownloadService iMbmsDownloadService = this.mService.get();
        if (iMbmsDownloadService == null) {
            throw new java.lang.IllegalStateException("Middleware not yet bound");
        }
        android.telephony.mbms.InternalDownloadStatusListener internalDownloadStatusListener = new android.telephony.mbms.InternalDownloadStatusListener(downloadStatusListener, executor);
        try {
            int addStatusListener = iMbmsDownloadService.addStatusListener(downloadRequest, internalDownloadStatusListener);
            if (addStatusListener == -1) {
                close();
                throw new java.lang.IllegalStateException("Middleware must not return an unknown error code");
            }
            if (addStatusListener != 0) {
                if (addStatusListener == 402) {
                    throw new java.lang.IllegalArgumentException("Unknown download request.");
                }
                sendErrorToApp(addStatusListener, null);
                return;
            }
            this.mInternalDownloadStatusListeners.put(downloadStatusListener, internalDownloadStatusListener);
        } catch (android.os.RemoteException e) {
            this.mService.set(null);
            sIsInitialized.set(false);
            sendErrorToApp(3, null);
        }
    }

    public void removeStatusListener(android.telephony.mbms.DownloadRequest downloadRequest, android.telephony.mbms.DownloadStatusListener downloadStatusListener) {
        try {
            android.telephony.mbms.vendor.IMbmsDownloadService iMbmsDownloadService = this.mService.get();
            if (iMbmsDownloadService == null) {
                throw new java.lang.IllegalStateException("Middleware not yet bound");
            }
            android.telephony.mbms.InternalDownloadStatusListener internalDownloadStatusListener = this.mInternalDownloadStatusListeners.get(downloadStatusListener);
            if (internalDownloadStatusListener == null) {
                throw new java.lang.IllegalArgumentException("Provided listener was never registered");
            }
            try {
                int removeStatusListener = iMbmsDownloadService.removeStatusListener(downloadRequest, internalDownloadStatusListener);
                if (removeStatusListener == -1) {
                    close();
                    throw new java.lang.IllegalStateException("Middleware must not return an unknown error code");
                }
                if (removeStatusListener == 0) {
                    android.telephony.mbms.InternalDownloadStatusListener remove = this.mInternalDownloadStatusListeners.remove(downloadStatusListener);
                    if (remove != null) {
                        remove.stop();
                        return;
                    }
                    return;
                }
                if (removeStatusListener == 402) {
                    throw new java.lang.IllegalArgumentException("Unknown download request.");
                }
                sendErrorToApp(removeStatusListener, null);
                android.telephony.mbms.InternalDownloadStatusListener remove2 = this.mInternalDownloadStatusListeners.remove(downloadStatusListener);
                if (remove2 != null) {
                    remove2.stop();
                }
            } catch (android.os.RemoteException e) {
                this.mService.set(null);
                sIsInitialized.set(false);
                sendErrorToApp(3, null);
                android.telephony.mbms.InternalDownloadStatusListener remove3 = this.mInternalDownloadStatusListeners.remove(downloadStatusListener);
                if (remove3 != null) {
                    remove3.stop();
                }
            }
        } catch (java.lang.Throwable th) {
            android.telephony.mbms.InternalDownloadStatusListener remove4 = this.mInternalDownloadStatusListeners.remove(downloadStatusListener);
            if (remove4 != null) {
                remove4.stop();
            }
            throw th;
        }
    }

    public void addProgressListener(android.telephony.mbms.DownloadRequest downloadRequest, java.util.concurrent.Executor executor, android.telephony.mbms.DownloadProgressListener downloadProgressListener) {
        android.telephony.mbms.vendor.IMbmsDownloadService iMbmsDownloadService = this.mService.get();
        if (iMbmsDownloadService == null) {
            throw new java.lang.IllegalStateException("Middleware not yet bound");
        }
        android.telephony.mbms.InternalDownloadProgressListener internalDownloadProgressListener = new android.telephony.mbms.InternalDownloadProgressListener(downloadProgressListener, executor);
        try {
            int addProgressListener = iMbmsDownloadService.addProgressListener(downloadRequest, internalDownloadProgressListener);
            if (addProgressListener == -1) {
                close();
                throw new java.lang.IllegalStateException("Middleware must not return an unknown error code");
            }
            if (addProgressListener != 0) {
                if (addProgressListener == 402) {
                    throw new java.lang.IllegalArgumentException("Unknown download request.");
                }
                sendErrorToApp(addProgressListener, null);
                return;
            }
            this.mInternalDownloadProgressListeners.put(downloadProgressListener, internalDownloadProgressListener);
        } catch (android.os.RemoteException e) {
            this.mService.set(null);
            sIsInitialized.set(false);
            sendErrorToApp(3, null);
        }
    }

    public void removeProgressListener(android.telephony.mbms.DownloadRequest downloadRequest, android.telephony.mbms.DownloadProgressListener downloadProgressListener) {
        try {
            android.telephony.mbms.vendor.IMbmsDownloadService iMbmsDownloadService = this.mService.get();
            if (iMbmsDownloadService == null) {
                throw new java.lang.IllegalStateException("Middleware not yet bound");
            }
            android.telephony.mbms.InternalDownloadProgressListener internalDownloadProgressListener = this.mInternalDownloadProgressListeners.get(downloadProgressListener);
            if (internalDownloadProgressListener == null) {
                throw new java.lang.IllegalArgumentException("Provided listener was never registered");
            }
            try {
                int removeProgressListener = iMbmsDownloadService.removeProgressListener(downloadRequest, internalDownloadProgressListener);
                if (removeProgressListener == -1) {
                    close();
                    throw new java.lang.IllegalStateException("Middleware must not return an unknown error code");
                }
                if (removeProgressListener == 0) {
                    android.telephony.mbms.InternalDownloadProgressListener remove = this.mInternalDownloadProgressListeners.remove(downloadProgressListener);
                    if (remove != null) {
                        remove.stop();
                        return;
                    }
                    return;
                }
                if (removeProgressListener == 402) {
                    throw new java.lang.IllegalArgumentException("Unknown download request.");
                }
                sendErrorToApp(removeProgressListener, null);
                android.telephony.mbms.InternalDownloadProgressListener remove2 = this.mInternalDownloadProgressListeners.remove(downloadProgressListener);
                if (remove2 != null) {
                    remove2.stop();
                }
            } catch (android.os.RemoteException e) {
                this.mService.set(null);
                sIsInitialized.set(false);
                sendErrorToApp(3, null);
                android.telephony.mbms.InternalDownloadProgressListener remove3 = this.mInternalDownloadProgressListeners.remove(downloadProgressListener);
                if (remove3 != null) {
                    remove3.stop();
                }
            }
        } catch (java.lang.Throwable th) {
            android.telephony.mbms.InternalDownloadProgressListener remove4 = this.mInternalDownloadProgressListeners.remove(downloadProgressListener);
            if (remove4 != null) {
                remove4.stop();
            }
            throw th;
        }
    }

    public void cancelDownload(android.telephony.mbms.DownloadRequest downloadRequest) {
        android.telephony.mbms.vendor.IMbmsDownloadService iMbmsDownloadService = this.mService.get();
        if (iMbmsDownloadService == null) {
            throw new java.lang.IllegalStateException("Middleware not yet bound");
        }
        try {
            int cancelDownload = iMbmsDownloadService.cancelDownload(downloadRequest);
            if (cancelDownload == -1) {
                close();
                throw new java.lang.IllegalStateException("Middleware must not return an unknown error code");
            }
            if (cancelDownload != 0) {
                sendErrorToApp(cancelDownload, null);
            } else {
                deleteDownloadRequestToken(downloadRequest);
            }
        } catch (android.os.RemoteException e) {
            this.mService.set(null);
            sIsInitialized.set(false);
            sendErrorToApp(3, null);
        }
    }

    public void requestDownloadState(android.telephony.mbms.DownloadRequest downloadRequest, android.telephony.mbms.FileInfo fileInfo) {
        android.telephony.mbms.vendor.IMbmsDownloadService iMbmsDownloadService = this.mService.get();
        if (iMbmsDownloadService == null) {
            throw new java.lang.IllegalStateException("Middleware not yet bound");
        }
        try {
            int requestDownloadState = iMbmsDownloadService.requestDownloadState(downloadRequest, fileInfo);
            if (requestDownloadState == -1) {
                close();
                throw new java.lang.IllegalStateException("Middleware must not return an unknown error code");
            }
            if (requestDownloadState != 0) {
                if (requestDownloadState == 402) {
                    throw new java.lang.IllegalArgumentException("Unknown download request.");
                }
                if (requestDownloadState == 403) {
                    throw new java.lang.IllegalArgumentException("Unknown file.");
                }
                sendErrorToApp(requestDownloadState, null);
            }
        } catch (android.os.RemoteException e) {
            this.mService.set(null);
            sIsInitialized.set(false);
            sendErrorToApp(3, null);
        }
    }

    public void resetDownloadKnowledge(android.telephony.mbms.DownloadRequest downloadRequest) {
        android.telephony.mbms.vendor.IMbmsDownloadService iMbmsDownloadService = this.mService.get();
        if (iMbmsDownloadService == null) {
            throw new java.lang.IllegalStateException("Middleware not yet bound");
        }
        try {
            int resetDownloadKnowledge = iMbmsDownloadService.resetDownloadKnowledge(downloadRequest);
            if (resetDownloadKnowledge == -1) {
                close();
                throw new java.lang.IllegalStateException("Middleware must not return an unknown error code");
            }
            if (resetDownloadKnowledge != 0) {
                if (resetDownloadKnowledge == 402) {
                    throw new java.lang.IllegalArgumentException("Unknown download request.");
                }
                sendErrorToApp(resetDownloadKnowledge, null);
            }
        } catch (android.os.RemoteException e) {
            this.mService.set(null);
            sIsInitialized.set(false);
            sendErrorToApp(3, null);
        }
    }

    @Override // java.lang.AutoCloseable
    public void close() {
        android.telephony.mbms.vendor.IMbmsDownloadService iMbmsDownloadService;
        try {
            try {
                iMbmsDownloadService = this.mService.get();
            } catch (android.os.RemoteException e) {
                android.util.Log.i(LOG_TAG, "Remote exception while disposing of service");
            }
            if (iMbmsDownloadService != null && this.mServiceConnection != null) {
                iMbmsDownloadService.dispose(this.mSubscriptionId);
                this.mContext.unbindService(this.mServiceConnection);
                return;
            }
            android.util.Log.i(LOG_TAG, "Service already dead");
        } finally {
            this.mService.set(null);
            sIsInitialized.set(false);
            this.mServiceConnection = null;
            this.mInternalCallback.stop();
        }
    }

    private void writeDownloadRequestToken(android.telephony.mbms.DownloadRequest downloadRequest) {
        java.io.File downloadRequestTokenPath = getDownloadRequestTokenPath(downloadRequest);
        if (!downloadRequestTokenPath.getParentFile().exists()) {
            downloadRequestTokenPath.getParentFile().mkdirs();
        }
        if (downloadRequestTokenPath.exists()) {
            android.util.Log.w(LOG_TAG, "Download token " + downloadRequestTokenPath.getName() + " already exists");
            return;
        }
        try {
            if (!downloadRequestTokenPath.createNewFile()) {
                throw new java.lang.RuntimeException("Failed to create download token for request " + downloadRequest + ". Token location is " + downloadRequestTokenPath.getPath());
            }
        } catch (java.io.IOException e) {
            throw new java.lang.RuntimeException("Failed to create download token for request " + downloadRequest + " due to IOException " + e + ". Attempted to write to " + downloadRequestTokenPath.getPath());
        }
    }

    private void deleteDownloadRequestToken(android.telephony.mbms.DownloadRequest downloadRequest) {
        java.io.File downloadRequestTokenPath = getDownloadRequestTokenPath(downloadRequest);
        if (!downloadRequestTokenPath.isFile()) {
            android.util.Log.w(LOG_TAG, "Attempting to delete non-existent download token at " + downloadRequestTokenPath);
        } else if (!downloadRequestTokenPath.delete()) {
            android.util.Log.w(LOG_TAG, "Couldn't delete download token at " + downloadRequestTokenPath);
        }
    }

    private void checkDownloadRequestDestination(android.telephony.mbms.DownloadRequest downloadRequest) {
        java.io.File file = new java.io.File(downloadRequest.getDestinationUri().getPath());
        if (!file.isDirectory()) {
            throw new java.lang.IllegalArgumentException("The destination path must be a directory");
        }
        java.io.File file2 = new java.io.File(android.telephony.mbms.MbmsTempFileProvider.getEmbmsTempFileDir(this.mContext), DESTINATION_SANITY_CHECK_FILE_NAME);
        java.io.File file3 = new java.io.File(file, DESTINATION_SANITY_CHECK_FILE_NAME);
        try {
            try {
                if (!file2.exists()) {
                    file2.createNewFile();
                }
                if (!file2.renameTo(file3)) {
                    throw new java.lang.IllegalArgumentException("Destination provided in the download request is invalid -- files in the temp file directory cannot be directly moved there.");
                }
            } catch (java.io.IOException e) {
                throw new java.lang.IllegalStateException("Got IOException while testing out the destination: " + e);
            }
        } finally {
            file2.delete();
            file3.delete();
        }
    }

    private java.io.File getDownloadRequestTokenPath(android.telephony.mbms.DownloadRequest downloadRequest) {
        return new java.io.File(android.telephony.mbms.MbmsUtils.getEmbmsTempFileDirForService(this.mContext, downloadRequest.getFileServiceId()), downloadRequest.getHash() + android.telephony.mbms.MbmsDownloadReceiver.DOWNLOAD_TOKEN_SUFFIX);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendErrorToApp(int i, java.lang.String str) {
        this.mInternalCallback.onError(i, str);
    }
}
