package android.os.storage;

/* loaded from: classes3.dex */
public class StorageManager {
    public static final java.lang.String ACTION_CLEAR_APP_CACHE = "android.os.storage.action.CLEAR_APP_CACHE";
    public static final java.lang.String ACTION_MANAGE_STORAGE = "android.os.storage.action.MANAGE_STORAGE";

    @android.annotation.SystemApi(client = android.annotation.SystemApi.Client.MODULE_LIBRARIES)
    public static final int APP_IO_BLOCKED_REASON_TRANSCODING = 1;

    @android.annotation.SystemApi(client = android.annotation.SystemApi.Client.MODULE_LIBRARIES)
    public static final int APP_IO_BLOCKED_REASON_UNKNOWN = 0;
    public static final java.lang.String CACHE_RESERVE_PERCENT_HIGH_KEY = "cache_reserve_percent_high";
    public static final java.lang.String CACHE_RESERVE_PERCENT_LOW_KEY = "cache_reserve_percent_low";
    public static final int CRYPT_TYPE_DEFAULT = 1;
    public static final int CRYPT_TYPE_PASSWORD = 0;
    public static final int DEBUG_ADOPTABLE_FORCE_OFF = 2;
    public static final int DEBUG_ADOPTABLE_FORCE_ON = 1;
    public static final int DEBUG_SDCARDFS_FORCE_OFF = 8;
    public static final int DEBUG_SDCARDFS_FORCE_ON = 4;
    public static final int DEBUG_VIRTUAL_DISK = 16;
    public static final int DEFAULT_CACHE_RESERVE_PERCENT_HIGH = 10;
    public static final int DEFAULT_CACHE_RESERVE_PERCENT_LOW = 2;
    public static final int DEFAULT_STORAGE_THRESHOLD_PERCENT_HIGH = 20;
    public static final int DEFAULT_STORAGE_THRESHOLD_PERCENT_LOW = 5;
    public static final int ENCRYPTION_STATE_NONE = 1;
    public static final java.lang.String EXTRA_REQUESTED_BYTES = "android.os.storage.extra.REQUESTED_BYTES";
    public static final java.lang.String EXTRA_UUID = "android.os.storage.extra.UUID";
    private static final java.lang.String FAT_UUID_PREFIX = "fafafafa-fafa-5afa-8afa-fafa";

    @android.annotation.SystemApi
    public static final int FLAG_ALLOCATE_AGGRESSIVE = 1;
    public static final int FLAG_ALLOCATE_CACHE_ONLY = 16;
    public static final int FLAG_ALLOCATE_DEFY_ALL_RESERVED = 2;
    public static final int FLAG_ALLOCATE_DEFY_HALF_RESERVED = 4;
    public static final int FLAG_ALLOCATE_NON_CACHE_ONLY = 8;
    public static final int FLAG_FOR_WRITE = 256;
    public static final int FLAG_INCLUDE_INVISIBLE = 1024;
    public static final int FLAG_INCLUDE_RECENT = 2048;
    public static final int FLAG_INCLUDE_SHARED_PROFILE = 4096;
    public static final int FLAG_REAL_STATE = 512;
    public static final int FLAG_STORAGE_CE = 2;
    public static final int FLAG_STORAGE_DE = 1;
    public static final int FLAG_STORAGE_EXTERNAL = 4;
    public static final int FLAG_STORAGE_SDK = 8;
    public static final int FSTRIM_FLAG_DEEP = 1;

    @android.annotation.SystemApi
    public static final int MOUNT_MODE_EXTERNAL_ANDROID_WRITABLE = 4;

    @android.annotation.SystemApi
    public static final int MOUNT_MODE_EXTERNAL_DEFAULT = 1;

    @android.annotation.SystemApi
    public static final int MOUNT_MODE_EXTERNAL_INSTALLER = 2;

    @android.annotation.SystemApi
    public static final int MOUNT_MODE_EXTERNAL_NONE = 0;

    @android.annotation.SystemApi
    public static final int MOUNT_MODE_EXTERNAL_PASS_THROUGH = 3;
    public static final int PROJECT_ID_EXT_DEFAULT = 1000;
    public static final int PROJECT_ID_EXT_MEDIA_AUDIO = 1001;
    public static final int PROJECT_ID_EXT_MEDIA_IMAGE = 1003;
    public static final int PROJECT_ID_EXT_MEDIA_VIDEO = 1002;
    public static final java.lang.String PROP_ADOPTABLE = "persist.sys.adoptable";
    public static final java.lang.String PROP_FORCED_SCOPED_STORAGE_WHITELIST = "forced_scoped_storage_whitelist";
    public static final java.lang.String PROP_HAS_ADOPTABLE = "vold.has_adoptable";
    public static final java.lang.String PROP_HAS_RESERVED = "vold.has_reserved";
    public static final java.lang.String PROP_PRIMARY_PHYSICAL = "ro.vold.primary_physical";
    public static final java.lang.String PROP_SDCARDFS = "persist.sys.sdcardfs";
    public static final java.lang.String PROP_VIRTUAL_DISK = "persist.sys.virtual_disk";

    @android.annotation.SystemApi
    public static final int QUOTA_TYPE_MEDIA_AUDIO = 2;

    @android.annotation.SystemApi
    public static final int QUOTA_TYPE_MEDIA_IMAGE = 1;

    @android.annotation.SystemApi
    public static final int QUOTA_TYPE_MEDIA_NONE = 0;

    @android.annotation.SystemApi
    public static final int QUOTA_TYPE_MEDIA_VIDEO = 3;
    public static final java.lang.String STORAGE_THRESHOLD_PERCENT_HIGH_KEY = "storage_threshold_percent_high";
    public static final java.lang.String UUID_PRIMARY_PHYSICAL = "primary_physical";
    public static final java.lang.String UUID_SYSTEM = "system";
    private static final java.lang.String XATTR_CACHE_GROUP = "user.cache_group";
    private static final java.lang.String XATTR_CACHE_TOMBSTONE = "user.cache_tombstone";
    private final android.app.AppOpsManager mAppOps;
    private final android.content.Context mContext;
    private final android.os.Looper mLooper;
    private final android.content.ContentResolver mResolver;
    private static final java.lang.String TAG = "StorageManager";
    private static final boolean LOCAL_LOGV = android.util.Log.isLoggable(TAG, 2);
    public static final java.lang.String UUID_PRIVATE_INTERNAL = null;
    public static final java.util.UUID UUID_DEFAULT = java.util.UUID.fromString("41217664-9172-527a-b3d5-edabb50a7d69");
    public static final java.util.UUID UUID_PRIMARY_PHYSICAL_ = java.util.UUID.fromString("0f95a519-dae7-5abf-9519-fbd6209e05fd");
    public static final java.util.UUID UUID_SYSTEM_ = java.util.UUID.fromString("5d258386-e60d-59e3-826d-0089cdd42cc0");
    private static volatile android.os.storage.IStorageManager sStorageManager = null;
    private static final long DEFAULT_THRESHOLD_MAX_BYTES = android.util.DataUnit.MEBIBYTES.toBytes(500);
    private static final long DEFAULT_FULL_THRESHOLD_BYTES = android.util.DataUnit.MEBIBYTES.toBytes(1);
    private final java.util.concurrent.atomic.AtomicInteger mNextNonce = new java.util.concurrent.atomic.AtomicInteger(0);
    private final java.util.ArrayList<android.os.storage.StorageManager.StorageEventListenerDelegate> mDelegates = new java.util.ArrayList<>();
    private final android.os.storage.StorageManager.ObbActionListener mObbActionListener = new android.os.storage.StorageManager.ObbActionListener();
    private final java.lang.Object mFuseAppLoopLock = new java.lang.Object();
    private com.android.internal.os.FuseAppLoop mFuseAppLoop = null;
    private final android.os.storage.IStorageManager mStorageManager = android.os.storage.IStorageManager.Stub.asInterface(android.os.ServiceManager.getServiceOrThrow("mount"));

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface AllocateFlags {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface AppIoBlockedReason {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface MountMode {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface QuotaType {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface StorageFlags {
    }

    private static native boolean setQuotaProjectId(java.lang.String str, long j);

    /* JADX INFO: Access modifiers changed from: private */
    class StorageEventListenerDelegate extends android.os.storage.IStorageEventListener.Stub {
        final android.os.storage.StorageManager.StorageVolumeCallback mCallback;
        final java.util.concurrent.Executor mExecutor;
        final android.os.storage.StorageEventListener mListener;

        public StorageEventListenerDelegate(java.util.concurrent.Executor executor, android.os.storage.StorageEventListener storageEventListener, android.os.storage.StorageManager.StorageVolumeCallback storageVolumeCallback) {
            this.mExecutor = executor;
            this.mListener = storageEventListener;
            this.mCallback = storageVolumeCallback;
        }

        @Override // android.os.storage.IStorageEventListener
        public void onUsbMassStorageConnectionChanged(final boolean z) throws android.os.RemoteException {
            this.mExecutor.execute(new java.lang.Runnable() { // from class: android.os.storage.StorageManager$StorageEventListenerDelegate$$ExternalSyntheticLambda5
                @Override // java.lang.Runnable
                public final void run() {
                    android.os.storage.StorageManager.StorageEventListenerDelegate.this.lambda$onUsbMassStorageConnectionChanged$0(z);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onUsbMassStorageConnectionChanged$0(boolean z) {
            this.mListener.onUsbMassStorageConnectionChanged(z);
        }

        @Override // android.os.storage.IStorageEventListener
        public void onStorageStateChanged(final java.lang.String str, final java.lang.String str2, final java.lang.String str3) {
            this.mExecutor.execute(new java.lang.Runnable() { // from class: android.os.storage.StorageManager$StorageEventListenerDelegate$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    android.os.storage.StorageManager.StorageEventListenerDelegate.this.lambda$onStorageStateChanged$1(str, str2, str3);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onStorageStateChanged$1(java.lang.String str, java.lang.String str2, java.lang.String str3) {
            this.mListener.onStorageStateChanged(str, str2, str3);
            if (str != null) {
                for (android.os.storage.StorageVolume storageVolume : android.os.storage.StorageManager.this.getStorageVolumes()) {
                    if (java.util.Objects.equals(str, storageVolume.getPath())) {
                        this.mCallback.onStateChanged(storageVolume);
                    }
                }
            }
        }

        @Override // android.os.storage.IStorageEventListener
        public void onVolumeStateChanged(final android.os.storage.VolumeInfo volumeInfo, final int i, final int i2) {
            this.mExecutor.execute(new java.lang.Runnable() { // from class: android.os.storage.StorageManager$StorageEventListenerDelegate$$ExternalSyntheticLambda4
                @Override // java.lang.Runnable
                public final void run() {
                    android.os.storage.StorageManager.StorageEventListenerDelegate.this.lambda$onVolumeStateChanged$2(volumeInfo, i, i2);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onVolumeStateChanged$2(android.os.storage.VolumeInfo volumeInfo, int i, int i2) {
            this.mListener.onVolumeStateChanged(volumeInfo, i, i2);
            java.io.File pathForUser = volumeInfo.getPathForUser(android.os.UserHandle.myUserId());
            if (pathForUser != null) {
                for (android.os.storage.StorageVolume storageVolume : android.os.storage.StorageManager.this.getStorageVolumes()) {
                    if (java.util.Objects.equals(pathForUser.getAbsolutePath(), storageVolume.getPath())) {
                        this.mCallback.onStateChanged(storageVolume);
                    }
                }
            }
        }

        @Override // android.os.storage.IStorageEventListener
        public void onVolumeRecordChanged(final android.os.storage.VolumeRecord volumeRecord) {
            this.mExecutor.execute(new java.lang.Runnable() { // from class: android.os.storage.StorageManager$StorageEventListenerDelegate$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    android.os.storage.StorageManager.StorageEventListenerDelegate.this.lambda$onVolumeRecordChanged$3(volumeRecord);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onVolumeRecordChanged$3(android.os.storage.VolumeRecord volumeRecord) {
            this.mListener.onVolumeRecordChanged(volumeRecord);
        }

        @Override // android.os.storage.IStorageEventListener
        public void onVolumeForgotten(final java.lang.String str) {
            this.mExecutor.execute(new java.lang.Runnable() { // from class: android.os.storage.StorageManager$StorageEventListenerDelegate$$ExternalSyntheticLambda6
                @Override // java.lang.Runnable
                public final void run() {
                    android.os.storage.StorageManager.StorageEventListenerDelegate.this.lambda$onVolumeForgotten$4(str);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onVolumeForgotten$4(java.lang.String str) {
            this.mListener.onVolumeForgotten(str);
        }

        @Override // android.os.storage.IStorageEventListener
        public void onDiskScanned(final android.os.storage.DiskInfo diskInfo, final int i) {
            this.mExecutor.execute(new java.lang.Runnable() { // from class: android.os.storage.StorageManager$StorageEventListenerDelegate$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    android.os.storage.StorageManager.StorageEventListenerDelegate.this.lambda$onDiskScanned$5(diskInfo, i);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onDiskScanned$5(android.os.storage.DiskInfo diskInfo, int i) {
            this.mListener.onDiskScanned(diskInfo, i);
        }

        @Override // android.os.storage.IStorageEventListener
        public void onDiskDestroyed(final android.os.storage.DiskInfo diskInfo) throws android.os.RemoteException {
            this.mExecutor.execute(new java.lang.Runnable() { // from class: android.os.storage.StorageManager$StorageEventListenerDelegate$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    android.os.storage.StorageManager.StorageEventListenerDelegate.this.lambda$onDiskDestroyed$6(diskInfo);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onDiskDestroyed$6(android.os.storage.DiskInfo diskInfo) {
            this.mListener.onDiskDestroyed(diskInfo);
        }
    }

    private class ObbActionListener extends android.os.storage.IObbActionListener.Stub {
        private android.util.SparseArray<android.os.storage.StorageManager.ObbListenerDelegate> mListeners;

        private ObbActionListener() {
            this.mListeners = new android.util.SparseArray<>();
        }

        @Override // android.os.storage.IObbActionListener
        public void onObbResult(java.lang.String str, int i, int i2) {
            android.os.storage.StorageManager.ObbListenerDelegate obbListenerDelegate;
            synchronized (this.mListeners) {
                obbListenerDelegate = this.mListeners.get(i);
                if (obbListenerDelegate != null) {
                    this.mListeners.remove(i);
                }
            }
            if (obbListenerDelegate != null) {
                obbListenerDelegate.sendObbStateChanged(str, i2);
            }
        }

        public int addListener(android.os.storage.OnObbStateChangeListener onObbStateChangeListener) {
            android.os.storage.StorageManager.ObbListenerDelegate obbListenerDelegate = android.os.storage.StorageManager.this.new ObbListenerDelegate(onObbStateChangeListener);
            synchronized (this.mListeners) {
                this.mListeners.put(obbListenerDelegate.nonce, obbListenerDelegate);
            }
            return obbListenerDelegate.nonce;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getNextNonce() {
        return this.mNextNonce.getAndIncrement();
    }

    private class ObbListenerDelegate {
        private final android.os.Handler mHandler;
        private final java.lang.ref.WeakReference<android.os.storage.OnObbStateChangeListener> mObbEventListenerRef;
        private final int nonce;

        ObbListenerDelegate(android.os.storage.OnObbStateChangeListener onObbStateChangeListener) {
            this.nonce = android.os.storage.StorageManager.this.getNextNonce();
            this.mObbEventListenerRef = new java.lang.ref.WeakReference<>(onObbStateChangeListener);
            this.mHandler = new android.os.Handler(android.os.storage.StorageManager.this.mLooper) { // from class: android.os.storage.StorageManager.ObbListenerDelegate.1
                @Override // android.os.Handler
                public void handleMessage(android.os.Message message) {
                    android.os.storage.OnObbStateChangeListener listener = android.os.storage.StorageManager.ObbListenerDelegate.this.getListener();
                    if (listener == null) {
                        return;
                    }
                    listener.onObbStateChange((java.lang.String) message.obj, message.arg1);
                }
            };
        }

        android.os.storage.OnObbStateChangeListener getListener() {
            if (this.mObbEventListenerRef == null) {
                return null;
            }
            return this.mObbEventListenerRef.get();
        }

        void sendObbStateChanged(java.lang.String str, int i) {
            this.mHandler.obtainMessage(0, i, 0, str).sendToTarget();
        }
    }

    @java.lang.Deprecated
    public static android.os.storage.StorageManager from(android.content.Context context) {
        return (android.os.storage.StorageManager) context.getSystemService(android.os.storage.StorageManager.class);
    }

    public StorageManager(android.content.Context context, android.os.Looper looper) throws android.os.ServiceManager.ServiceNotFoundException {
        this.mContext = context;
        this.mResolver = context.getContentResolver();
        this.mLooper = looper;
        this.mAppOps = (android.app.AppOpsManager) this.mContext.getSystemService(android.app.AppOpsManager.class);
    }

    public void registerListener(android.os.storage.StorageEventListener storageEventListener) {
        synchronized (this.mDelegates) {
            android.os.storage.StorageManager.StorageEventListenerDelegate storageEventListenerDelegate = new android.os.storage.StorageManager.StorageEventListenerDelegate(this.mContext.getMainExecutor(), storageEventListener, new android.os.storage.StorageManager.StorageVolumeCallback());
            try {
                this.mStorageManager.registerListener(storageEventListenerDelegate);
                this.mDelegates.add(storageEventListenerDelegate);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public void unregisterListener(android.os.storage.StorageEventListener storageEventListener) {
        synchronized (this.mDelegates) {
            java.util.Iterator<android.os.storage.StorageManager.StorageEventListenerDelegate> it = this.mDelegates.iterator();
            while (it.hasNext()) {
                android.os.storage.StorageManager.StorageEventListenerDelegate next = it.next();
                if (next.mListener == storageEventListener) {
                    try {
                        this.mStorageManager.unregisterListener(next);
                        it.remove();
                    } catch (android.os.RemoteException e) {
                        throw e.rethrowFromSystemServer();
                    }
                }
            }
        }
    }

    public static class StorageVolumeCallback {
        public void onStateChanged(android.os.storage.StorageVolume storageVolume) {
        }
    }

    public void registerStorageVolumeCallback(java.util.concurrent.Executor executor, android.os.storage.StorageManager.StorageVolumeCallback storageVolumeCallback) {
        synchronized (this.mDelegates) {
            android.os.storage.StorageManager.StorageEventListenerDelegate storageEventListenerDelegate = new android.os.storage.StorageManager.StorageEventListenerDelegate(executor, new android.os.storage.StorageEventListener(), storageVolumeCallback);
            try {
                this.mStorageManager.registerListener(storageEventListenerDelegate);
                this.mDelegates.add(storageEventListenerDelegate);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public void unregisterStorageVolumeCallback(android.os.storage.StorageManager.StorageVolumeCallback storageVolumeCallback) {
        synchronized (this.mDelegates) {
            java.util.Iterator<android.os.storage.StorageManager.StorageEventListenerDelegate> it = this.mDelegates.iterator();
            while (it.hasNext()) {
                android.os.storage.StorageManager.StorageEventListenerDelegate next = it.next();
                if (next.mCallback == storageVolumeCallback) {
                    try {
                        this.mStorageManager.unregisterListener(next);
                        it.remove();
                    } catch (android.os.RemoteException e) {
                        throw e.rethrowFromSystemServer();
                    }
                }
            }
        }
    }

    @java.lang.Deprecated
    public void enableUsbMassStorage() {
    }

    @java.lang.Deprecated
    public void disableUsbMassStorage() {
    }

    @java.lang.Deprecated
    public boolean isUsbMassStorageConnected() {
        return false;
    }

    @java.lang.Deprecated
    public boolean isUsbMassStorageEnabled() {
        return false;
    }

    public boolean mountObb(java.lang.String str, java.lang.String str2, android.os.storage.OnObbStateChangeListener onObbStateChangeListener) {
        com.android.internal.util.Preconditions.checkNotNull(str, "rawPath cannot be null");
        com.android.internal.util.Preconditions.checkArgument(str2 == null, "mounting encrypted OBBs is no longer supported");
        com.android.internal.util.Preconditions.checkNotNull(onObbStateChangeListener, "listener cannot be null");
        try {
            java.lang.String canonicalPath = new java.io.File(str).getCanonicalPath();
            this.mStorageManager.mountObb(str, canonicalPath, this.mObbActionListener, this.mObbActionListener.addListener(onObbStateChangeListener), getObbInfo(canonicalPath));
            return true;
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        } catch (java.io.IOException e2) {
            throw new java.lang.IllegalArgumentException("Failed to resolve path: " + str, e2);
        }
    }

    public android.app.PendingIntent getManageSpaceActivityIntent(java.lang.String str, int i) {
        try {
            return this.mStorageManager.getManageSpaceActivityIntent(str, i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    private android.content.res.ObbInfo getObbInfo(java.lang.String str) {
        try {
            return android.content.res.ObbScanner.getObbInfo(str);
        } catch (java.io.IOException e) {
            throw new java.lang.IllegalArgumentException("Couldn't get OBB info for " + str, e);
        }
    }

    public boolean unmountObb(java.lang.String str, boolean z, android.os.storage.OnObbStateChangeListener onObbStateChangeListener) {
        com.android.internal.util.Preconditions.checkNotNull(str, "rawPath cannot be null");
        com.android.internal.util.Preconditions.checkNotNull(onObbStateChangeListener, "listener cannot be null");
        try {
            this.mStorageManager.unmountObb(str, z, this.mObbActionListener, this.mObbActionListener.addListener(onObbStateChangeListener));
            return true;
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isObbMounted(java.lang.String str) {
        com.android.internal.util.Preconditions.checkNotNull(str, "rawPath cannot be null");
        try {
            return this.mStorageManager.isObbMounted(str);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public java.lang.String getMountedObbPath(java.lang.String str) {
        com.android.internal.util.Preconditions.checkNotNull(str, "rawPath cannot be null");
        try {
            return this.mStorageManager.getMountedObbPath(str);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public java.util.List<android.os.storage.DiskInfo> getDisks() {
        try {
            return java.util.Arrays.asList(this.mStorageManager.getDisks());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public android.os.storage.DiskInfo findDiskById(java.lang.String str) {
        com.android.internal.util.Preconditions.checkNotNull(str);
        for (android.os.storage.DiskInfo diskInfo : getDisks()) {
            if (java.util.Objects.equals(diskInfo.id, str)) {
                return diskInfo;
            }
        }
        return null;
    }

    public android.os.storage.VolumeInfo findVolumeById(java.lang.String str) {
        com.android.internal.util.Preconditions.checkNotNull(str);
        for (android.os.storage.VolumeInfo volumeInfo : getVolumes()) {
            if (java.util.Objects.equals(volumeInfo.id, str)) {
                return volumeInfo;
            }
        }
        return null;
    }

    public android.os.storage.VolumeInfo findVolumeByUuid(java.lang.String str) {
        com.android.internal.util.Preconditions.checkNotNull(str);
        for (android.os.storage.VolumeInfo volumeInfo : getVolumes()) {
            if (java.util.Objects.equals(volumeInfo.fsUuid, str)) {
                return volumeInfo;
            }
        }
        return null;
    }

    public android.os.storage.VolumeRecord findRecordByUuid(java.lang.String str) {
        com.android.internal.util.Preconditions.checkNotNull(str);
        for (android.os.storage.VolumeRecord volumeRecord : getVolumeRecords()) {
            if (java.util.Objects.equals(volumeRecord.fsUuid, str)) {
                return volumeRecord;
            }
        }
        return null;
    }

    public android.os.storage.VolumeInfo findPrivateForEmulated(android.os.storage.VolumeInfo volumeInfo) {
        if (volumeInfo != null) {
            java.lang.String id = volumeInfo.getId();
            int indexOf = id.indexOf(android.inputmethodservice.navigationbar.NavigationBarInflaterView.GRAVITY_SEPARATOR);
            if (indexOf != -1) {
                id = id.substring(0, indexOf);
            }
            return findVolumeById(id.replace(android.os.storage.VolumeInfo.ID_EMULATED_INTERNAL, android.os.storage.VolumeInfo.ID_PRIVATE_INTERNAL));
        }
        return null;
    }

    public android.os.storage.VolumeInfo findEmulatedForPrivate(android.os.storage.VolumeInfo volumeInfo) {
        if (volumeInfo != null) {
            return findVolumeById(volumeInfo.getId().replace(android.os.storage.VolumeInfo.ID_PRIVATE_INTERNAL, android.os.storage.VolumeInfo.ID_EMULATED_INTERNAL) + android.inputmethodservice.navigationbar.NavigationBarInflaterView.GRAVITY_SEPARATOR + this.mContext.getUserId());
        }
        return null;
    }

    public android.os.storage.VolumeInfo findVolumeByQualifiedUuid(java.lang.String str) {
        if (java.util.Objects.equals(UUID_PRIVATE_INTERNAL, str)) {
            return findVolumeById(android.os.storage.VolumeInfo.ID_PRIVATE_INTERNAL);
        }
        if (java.util.Objects.equals(UUID_PRIMARY_PHYSICAL, str)) {
            return getPrimaryPhysicalVolume();
        }
        return findVolumeByUuid(str);
    }

    public java.util.UUID getUuidForPath(java.io.File file) throws java.io.IOException {
        com.android.internal.util.Preconditions.checkNotNull(file);
        java.lang.String canonicalPath = file.getCanonicalPath();
        if (android.os.FileUtils.contains(android.os.Environment.getDataDirectory().getAbsolutePath(), canonicalPath)) {
            return UUID_DEFAULT;
        }
        try {
            for (android.os.storage.VolumeInfo volumeInfo : this.mStorageManager.getVolumes(0)) {
                if (volumeInfo.path != null && android.os.FileUtils.contains(volumeInfo.path, canonicalPath) && volumeInfo.type != 0 && volumeInfo.type != 5) {
                    try {
                        return convert(volumeInfo.fsUuid);
                    } catch (java.lang.IllegalArgumentException e) {
                    }
                }
            }
            throw new java.io.FileNotFoundException("Failed to find a storage device for " + file);
        } catch (android.os.RemoteException e2) {
            throw e2.rethrowFromSystemServer();
        }
    }

    public java.io.File findPathForUuid(java.lang.String str) throws java.io.FileNotFoundException {
        android.os.storage.VolumeInfo findVolumeByQualifiedUuid = findVolumeByQualifiedUuid(str);
        if (findVolumeByQualifiedUuid != null) {
            return findVolumeByQualifiedUuid.getPath();
        }
        throw new java.io.FileNotFoundException("Failed to find a storage device for " + str);
    }

    public boolean isAllocationSupported(java.io.FileDescriptor fileDescriptor) {
        try {
            getUuidForPath(android.os.ParcelFileDescriptor.getFile(fileDescriptor));
            return true;
        } catch (java.io.IOException e) {
            return false;
        }
    }

    public java.util.List<android.os.storage.VolumeInfo> getVolumes() {
        try {
            return java.util.Arrays.asList(this.mStorageManager.getVolumes(0));
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public java.util.List<android.os.storage.VolumeInfo> getWritablePrivateVolumes() {
        try {
            java.util.ArrayList arrayList = new java.util.ArrayList();
            for (android.os.storage.VolumeInfo volumeInfo : this.mStorageManager.getVolumes(0)) {
                if (volumeInfo.getType() == 1 && volumeInfo.isMountedWritable()) {
                    arrayList.add(volumeInfo);
                }
            }
            return arrayList;
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public java.util.List<android.os.storage.VolumeRecord> getVolumeRecords() {
        try {
            return java.util.Arrays.asList(this.mStorageManager.getVolumeRecords(0));
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public java.lang.String getBestVolumeDescription(android.os.storage.VolumeInfo volumeInfo) {
        android.os.storage.VolumeRecord findRecordByUuid;
        if (volumeInfo == null) {
            return null;
        }
        if (!android.text.TextUtils.isEmpty(volumeInfo.fsUuid) && (findRecordByUuid = findRecordByUuid(volumeInfo.fsUuid)) != null && !android.text.TextUtils.isEmpty(findRecordByUuid.nickname)) {
            return findRecordByUuid.nickname;
        }
        if (!android.text.TextUtils.isEmpty(volumeInfo.getDescription())) {
            return volumeInfo.getDescription();
        }
        if (volumeInfo.disk == null) {
            return null;
        }
        return volumeInfo.disk.getDescription();
    }

    public android.os.storage.VolumeInfo getPrimaryPhysicalVolume() {
        for (android.os.storage.VolumeInfo volumeInfo : getVolumes()) {
            if (volumeInfo.isPrimaryPhysical()) {
                return volumeInfo;
            }
        }
        return null;
    }

    public void mount(java.lang.String str) {
        try {
            this.mStorageManager.mount(str);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void unmount(java.lang.String str) {
        try {
            this.mStorageManager.unmount(str);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void format(java.lang.String str) {
        try {
            this.mStorageManager.format(str);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @java.lang.Deprecated
    public long benchmark(java.lang.String str) {
        final java.util.concurrent.CompletableFuture completableFuture = new java.util.concurrent.CompletableFuture();
        benchmark(str, new android.os.IVoldTaskListener.Stub() { // from class: android.os.storage.StorageManager.1
            @Override // android.os.IVoldTaskListener
            public void onStatus(int i, android.os.PersistableBundle persistableBundle) {
            }

            @Override // android.os.IVoldTaskListener
            public void onFinished(int i, android.os.PersistableBundle persistableBundle) {
                completableFuture.complete(persistableBundle);
            }
        });
        try {
            return ((android.os.PersistableBundle) completableFuture.get(3L, java.util.concurrent.TimeUnit.MINUTES)).getLong("run", Long.MAX_VALUE) * 1000000;
        } catch (java.lang.Exception e) {
            return Long.MAX_VALUE;
        }
    }

    public void benchmark(java.lang.String str, android.os.IVoldTaskListener iVoldTaskListener) {
        try {
            this.mStorageManager.benchmark(str, iVoldTaskListener);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void partitionPublic(java.lang.String str) {
        try {
            this.mStorageManager.partitionPublic(str);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void partitionPrivate(java.lang.String str) {
        try {
            this.mStorageManager.partitionPrivate(str);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void partitionMixed(java.lang.String str, int i) {
        try {
            this.mStorageManager.partitionMixed(str, i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void wipeAdoptableDisks() {
        for (android.os.storage.DiskInfo diskInfo : getDisks()) {
            java.lang.String id = diskInfo.getId();
            if (diskInfo.isAdoptable()) {
                android.util.Slog.d(TAG, "Found adoptable " + id + "; wiping");
                try {
                    this.mStorageManager.partitionPublic(id);
                } catch (java.lang.Exception e) {
                    android.util.Slog.w(TAG, "Failed to wipe " + id + ", but soldiering onward", e);
                }
            } else {
                android.util.Slog.d(TAG, "Ignorning non-adoptable disk " + diskInfo.getId());
            }
        }
    }

    public void setVolumeNickname(java.lang.String str, java.lang.String str2) {
        try {
            this.mStorageManager.setVolumeNickname(str, str2);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setVolumeInited(java.lang.String str, boolean z) {
        try {
            this.mStorageManager.setVolumeUserFlags(str, z ? 1 : 0, 1);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setVolumeSnoozed(java.lang.String str, boolean z) {
        try {
            this.mStorageManager.setVolumeUserFlags(str, z ? 2 : 0, 2);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void forgetVolume(java.lang.String str) {
        try {
            this.mStorageManager.forgetVolume(str);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public java.lang.String getPrimaryStorageUuid() {
        try {
            return this.mStorageManager.getPrimaryStorageUuid();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setPrimaryStorageUuid(java.lang.String str, android.content.pm.IPackageMoveObserver iPackageMoveObserver) {
        try {
            this.mStorageManager.setPrimaryStorageUuid(str, iPackageMoveObserver);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public android.os.storage.StorageVolume getStorageVolume(java.io.File file) {
        return getStorageVolume(getVolumeList(), file);
    }

    /* JADX WARN: Code restructure failed: missing block: B:23:0x0049, code lost:
    
        if (r0.equals("external_primary") != false) goto L24;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public android.os.storage.StorageVolume getStorageVolume(android.net.Uri uri) {
        java.lang.String volumeName = android.provider.MediaStore.getVolumeName(uri);
        char c = 0;
        if (java.util.Objects.equals(volumeName, "external")) {
            android.database.Cursor query = this.mContext.getContentResolver().query(uri, new java.lang.String[]{"volume_name"}, null, null);
            try {
                if (query.moveToFirst()) {
                    volumeName = query.getString(0);
                }
                if (query != null) {
                    query.close();
                }
            } catch (java.lang.Throwable th) {
                if (query != null) {
                    try {
                        query.close();
                    } catch (java.lang.Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        }
        switch (volumeName.hashCode()) {
            case -1921573490:
                break;
            default:
                c = 65535;
                break;
        }
        switch (c) {
            case 0:
                return getPrimaryStorageVolume();
            default:
                for (android.os.storage.StorageVolume storageVolume : getStorageVolumes()) {
                    if (java.util.Objects.equals(storageVolume.getMediaStoreVolumeName(), volumeName)) {
                        return storageVolume;
                    }
                }
                throw new java.lang.IllegalStateException("Unknown volume for " + uri);
        }
    }

    public static android.os.storage.StorageVolume getStorageVolume(java.io.File file, int i) {
        return getStorageVolume(getVolumeList(i, 0), file);
    }

    private static android.os.storage.StorageVolume getStorageVolume(android.os.storage.StorageVolume[] storageVolumeArr, java.io.File file) {
        if (file == null) {
            return null;
        }
        java.lang.String absolutePath = file.getAbsolutePath();
        if (absolutePath.startsWith(android.content.ContentResolver.DEPRECATE_DATA_PREFIX)) {
            return ((android.os.storage.StorageManager) android.app.AppGlobals.getInitialApplication().getSystemService(android.os.storage.StorageManager.class)).getStorageVolume(android.content.ContentResolver.translateDeprecatedDataPath(absolutePath));
        }
        try {
            java.io.File canonicalFile = file.getCanonicalFile();
            for (android.os.storage.StorageVolume storageVolume : storageVolumeArr) {
                if (android.os.FileUtils.contains(storageVolume.getPathFile().getCanonicalFile(), canonicalFile)) {
                    return storageVolume;
                }
            }
            return null;
        } catch (java.io.IOException e) {
            android.util.Slog.d(TAG, "Could not get canonical path for " + file);
            return null;
        }
    }

    @java.lang.Deprecated
    public java.lang.String getVolumeState(java.lang.String str) {
        android.os.storage.StorageVolume storageVolume = getStorageVolume(new java.io.File(str));
        if (storageVolume != null) {
            return storageVolume.getState();
        }
        return "unknown";
    }

    public java.util.List<android.os.storage.StorageVolume> getStorageVolumes() {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        java.util.Collections.addAll(arrayList, getVolumeList(this.mContext.getUserId(), 1536));
        return arrayList;
    }

    public java.util.List<android.os.storage.StorageVolume> getStorageVolumesIncludingSharedProfiles() {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        java.util.Collections.addAll(arrayList, getVolumeList(this.mContext.getUserId(), 5632));
        return arrayList;
    }

    public java.util.List<android.os.storage.StorageVolume> getRecentStorageVolumes() {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        java.util.Collections.addAll(arrayList, getVolumeList(this.mContext.getUserId(), 3584));
        return arrayList;
    }

    public android.os.storage.StorageVolume getPrimaryStorageVolume() {
        return getVolumeList(this.mContext.getUserId(), 1536)[0];
    }

    public static android.util.Pair<java.lang.String, java.lang.Long> getPrimaryStoragePathAndSize() {
        return android.util.Pair.create(null, java.lang.Long.valueOf(android.os.FileUtils.roundStorageSize(android.os.Environment.getDataDirectory().getTotalSpace() + android.os.Environment.getRootDirectory().getTotalSpace())));
    }

    public long getPrimaryStorageSize() {
        return android.os.FileUtils.roundStorageSize(android.os.Environment.getDataDirectory().getTotalSpace() + android.os.Environment.getRootDirectory().getTotalSpace());
    }

    public long getInternalStorageBlockDeviceSize() {
        try {
            return this.mStorageManager.getInternalStorageBlockDeviceSize();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void mkdirs(java.io.File file) {
        dalvik.system.BlockGuard.getVmPolicy().onPathAccess(file.getAbsolutePath());
        try {
            this.mStorageManager.mkdirs(this.mContext.getOpPackageName(), file.getAbsolutePath());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public android.os.storage.StorageVolume[] getVolumeList() {
        return getVolumeList(this.mContext.getUserId(), 0);
    }

    public static android.os.storage.StorageVolume[] getVolumeList(int i, int i2) {
        android.os.storage.IStorageManager asInterface = android.os.storage.IStorageManager.Stub.asInterface(android.os.ServiceManager.getService("mount"));
        try {
            java.lang.String currentOpPackageName = android.app.ActivityThread.currentOpPackageName();
            if (currentOpPackageName == null) {
                java.lang.String[] packagesForUid = android.app.ActivityThread.getPackageManager().getPackagesForUid(android.os.Process.myUid());
                if (packagesForUid != null && packagesForUid.length > 0) {
                    currentOpPackageName = packagesForUid[0];
                }
                android.util.Log.w(TAG, "Missing package names; no storage volumes available");
                return new android.os.storage.StorageVolume[0];
            }
            return asInterface.getVolumeList(i, currentOpPackageName, i2);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @java.lang.Deprecated
    public java.lang.String[] getVolumePaths() {
        android.os.storage.StorageVolume[] volumeList = getVolumeList();
        int length = volumeList.length;
        java.lang.String[] strArr = new java.lang.String[length];
        for (int i = 0; i < length; i++) {
            strArr[i] = volumeList[i].getPath();
        }
        return strArr;
    }

    public android.os.storage.StorageVolume getPrimaryVolume() {
        return getPrimaryVolume(getVolumeList());
    }

    public static android.os.storage.StorageVolume getPrimaryVolume(android.os.storage.StorageVolume[] storageVolumeArr) {
        for (android.os.storage.StorageVolume storageVolume : storageVolumeArr) {
            if (storageVolume.isPrimary()) {
                return storageVolume;
            }
        }
        throw new java.lang.IllegalStateException("Missing primary storage");
    }

    public long getStorageBytesUntilLow(java.io.File file) {
        return file.getUsableSpace() - getStorageFullBytes(file);
    }

    public long getStorageLowBytes(java.io.File file) {
        return java.lang.Math.min((file.getTotalSpace() * android.provider.Settings.Global.getInt(this.mResolver, android.provider.Settings.Global.SYS_STORAGE_THRESHOLD_PERCENTAGE, 5)) / 100, android.provider.Settings.Global.getLong(this.mResolver, android.provider.Settings.Global.SYS_STORAGE_THRESHOLD_MAX_BYTES, DEFAULT_THRESHOLD_MAX_BYTES));
    }

    @android.annotation.SystemApi(client = android.annotation.SystemApi.Client.MODULE_LIBRARIES)
    public long computeStorageCacheBytes(java.io.File file) {
        int i = android.provider.DeviceConfig.getInt("storage_native_boot", STORAGE_THRESHOLD_PERCENT_HIGH_KEY, 20);
        int i2 = android.provider.DeviceConfig.getInt("storage_native_boot", CACHE_RESERVE_PERCENT_HIGH_KEY, 10);
        int i3 = android.provider.DeviceConfig.getInt("storage_native_boot", CACHE_RESERVE_PERCENT_LOW_KEY, 2);
        long totalSpace = file.getTotalSpace();
        long usableSpace = file.getUsableSpace();
        long j = (i * totalSpace) / 100;
        long storageLowBytes = getStorageLowBytes(file);
        if (usableSpace > j) {
            return (totalSpace * i2) / 100;
        }
        if (usableSpace < storageLowBytes) {
            return (totalSpace * i3) / 100;
        }
        double d = ((i2 - i3) * totalSpace) / ((j - storageLowBytes) * 100.0d);
        return java.lang.Math.round((d * usableSpace) + (((totalSpace * i3) / 100.0d) - (storageLowBytes * d)));
    }

    public long getStorageCacheBytes(java.io.File file, int i) {
        if ((i & 1) != 0 || (i & 2) != 0) {
            return 0L;
        }
        if ((i & 4) != 0) {
            return computeStorageCacheBytes(file) / 2;
        }
        return computeStorageCacheBytes(file);
    }

    public long getStorageFullBytes(java.io.File file) {
        return android.provider.Settings.Global.getLong(this.mResolver, android.provider.Settings.Global.SYS_STORAGE_FULL_THRESHOLD_BYTES, DEFAULT_FULL_THRESHOLD_BYTES);
    }

    public void createUserStorageKeys(int i, boolean z) {
        try {
            this.mStorageManager.createUserStorageKeys(i, z);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void destroyUserStorageKeys(int i) {
        try {
            this.mStorageManager.destroyUserStorageKeys(i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void lockCeStorage(int i) {
        try {
            this.mStorageManager.lockCeStorage(i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void prepareUserStorage(java.lang.String str, int i, int i2) {
        try {
            this.mStorageManager.prepareUserStorage(str, i, i2);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void destroyUserStorage(java.lang.String str, int i, int i2) {
        try {
            this.mStorageManager.destroyUserStorage(str, i, i2);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public static boolean isCeStorageUnlocked(int i) {
        if (sStorageManager == null) {
            sStorageManager = android.os.storage.IStorageManager.Stub.asInterface(android.os.ServiceManager.getService("mount"));
        }
        if (sStorageManager == null) {
            android.util.Slog.w(TAG, "Early during boot, assuming CE storage is locked");
            return false;
        }
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            try {
                return sStorageManager.isCeStorageUnlocked(i);
            } catch (android.os.RemoteException e) {
                throw e.rethrowAsRuntimeException();
            }
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public boolean isEncrypted(java.io.File file) {
        if (android.os.FileUtils.contains(android.os.Environment.getDataDirectory(), file)) {
            return isEncrypted();
        }
        if (android.os.FileUtils.contains(android.os.Environment.getExpandDirectory(), file)) {
            return true;
        }
        return false;
    }

    public static boolean isEncrypted() {
        return com.android.internal.os.RoSystemProperties.CRYPTO_ENCRYPTED;
    }

    public static boolean isFileEncrypted() {
        if (!isEncrypted()) {
            return false;
        }
        return com.android.internal.os.RoSystemProperties.CRYPTO_FILE_ENCRYPTED;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public static boolean hasAdoptable() {
        char c;
        java.lang.String str = android.os.SystemProperties.get(PROP_ADOPTABLE);
        switch (str.hashCode()) {
            case 464944051:
                if (str.equals("force_on")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case 1528363547:
                if (str.equals("force_off")) {
                    c = 1;
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
                return true;
            case 1:
                return false;
            default:
                return android.os.SystemProperties.getBoolean(PROP_HAS_ADOPTABLE, false);
        }
    }

    @android.annotation.SystemApi
    public static boolean hasIsolatedStorage() {
        return false;
    }

    @java.lang.Deprecated
    public static java.io.File maybeTranslateEmulatedPathToInternal(java.io.File file) {
        return file;
    }

    public java.io.File translateAppToSystem(java.io.File file, int i, int i2) {
        return file;
    }

    public java.io.File translateSystemToApp(java.io.File file, int i, int i2) {
        return file;
    }

    public static boolean checkPermissionAndAppOp(android.content.Context context, boolean z, int i, int i2, java.lang.String str, java.lang.String str2, java.lang.String str3, int i3) {
        return checkPermissionAndAppOp(context, z, i, i2, str, str2, str3, i3, true);
    }

    public static boolean checkPermissionAndCheckOp(android.content.Context context, boolean z, int i, int i2, java.lang.String str, java.lang.String str2, int i3) {
        return checkPermissionAndAppOp(context, z, i, i2, str, null, str2, i3, false);
    }

    private static boolean checkPermissionAndAppOp(android.content.Context context, boolean z, int i, int i2, java.lang.String str, java.lang.String str2, java.lang.String str3, int i3, boolean z2) {
        int checkOpNoThrow;
        if (context.checkPermission(str3, i, i2) != 0) {
            if (z) {
                throw new java.lang.SecurityException("Permission " + str3 + " denied for package " + str);
            }
            return false;
        }
        android.app.AppOpsManager appOpsManager = (android.app.AppOpsManager) context.getSystemService(android.app.AppOpsManager.class);
        if (z2) {
            checkOpNoThrow = appOpsManager.noteOpNoThrow(i3, i2, str, str2, (java.lang.String) null);
        } else {
            try {
                appOpsManager.checkPackage(i2, str);
                checkOpNoThrow = appOpsManager.checkOpNoThrow(i3, i2, str);
            } catch (java.lang.SecurityException e) {
                if (z) {
                    throw e;
                }
                return false;
            }
        }
        switch (checkOpNoThrow) {
            case 0:
                return true;
            case 1:
            case 2:
            case 3:
                if (z) {
                    throw new java.lang.SecurityException("Op " + android.app.AppOpsManager.opToName(i3) + " " + android.app.AppOpsManager.modeToName(checkOpNoThrow) + " for package " + str);
                }
                return false;
            default:
                throw new java.lang.IllegalStateException(android.app.AppOpsManager.opToName(i3) + " has unknown mode " + android.app.AppOpsManager.modeToName(checkOpNoThrow));
        }
    }

    private boolean checkPermissionAndAppOp(boolean z, int i, int i2, java.lang.String str, java.lang.String str2, java.lang.String str3, int i3) {
        return checkPermissionAndAppOp(this.mContext, z, i, i2, str, str2, str3, i3);
    }

    private boolean noteAppOpAllowingLegacy(boolean z, int i, int i2, java.lang.String str, java.lang.String str2, int i3) {
        int noteOpNoThrow = this.mAppOps.noteOpNoThrow(i3, i2, str, str2, (java.lang.String) null);
        switch (noteOpNoThrow) {
            case 0:
                return true;
            case 1:
            case 2:
            case 3:
                if (this.mAppOps.checkOpNoThrow(87, i2, str) == 0) {
                    return true;
                }
                if (z) {
                    throw new java.lang.SecurityException("Op " + android.app.AppOpsManager.opToName(i3) + " " + android.app.AppOpsManager.modeToName(noteOpNoThrow) + " for package " + str);
                }
                return false;
            default:
                throw new java.lang.IllegalStateException(android.app.AppOpsManager.opToName(i3) + " has unknown mode " + android.app.AppOpsManager.modeToName(noteOpNoThrow));
        }
    }

    @java.lang.Deprecated
    public boolean checkPermissionReadImages(boolean z, int i, int i2, java.lang.String str, java.lang.String str2) {
        if (!checkExternalStoragePermissionAndAppOp(z, i, i2, str, str2, android.Manifest.permission.READ_EXTERNAL_STORAGE, 59)) {
            return false;
        }
        return noteAppOpAllowingLegacy(z, i, i2, str, str2, 85);
    }

    private boolean checkExternalStoragePermissionAndAppOp(boolean z, int i, int i2, java.lang.String str, java.lang.String str2, java.lang.String str3, int i3) {
        int noteOpNoThrow = this.mAppOps.noteOpNoThrow(92, i2, str, str2, (java.lang.String) null);
        if (noteOpNoThrow == 0) {
            return true;
        }
        if (noteOpNoThrow == 3 && this.mContext.checkPermission(android.Manifest.permission.MANAGE_EXTERNAL_STORAGE, i, i2) == 0) {
            return true;
        }
        return checkPermissionAndAppOp(z, i, i2, str, str2, str3, i3);
    }

    public android.os.ParcelFileDescriptor openProxyFileDescriptor(int i, android.os.ProxyFileDescriptorCallback proxyFileDescriptorCallback, android.os.Handler handler, java.util.concurrent.ThreadFactory threadFactory) throws java.io.IOException {
        boolean z;
        android.os.ParcelFileDescriptor openProxyFileDescriptor;
        com.android.internal.util.Preconditions.checkNotNull(proxyFileDescriptorCallback);
        com.android.internal.logging.MetricsLogger.count(this.mContext, "storage_open_proxy_file_descriptor", 1);
        while (true) {
            try {
                synchronized (this.mFuseAppLoopLock) {
                    if (this.mFuseAppLoop != null) {
                        z = false;
                    } else {
                        com.android.internal.os.AppFuseMount mountProxyFileDescriptorBridge = this.mStorageManager.mountProxyFileDescriptorBridge();
                        if (mountProxyFileDescriptorBridge == null) {
                            throw new java.io.IOException("Failed to mount proxy bridge");
                        }
                        this.mFuseAppLoop = new com.android.internal.os.FuseAppLoop(mountProxyFileDescriptorBridge.mountPointId, mountProxyFileDescriptorBridge.fd, threadFactory);
                        z = true;
                    }
                    if (handler == null) {
                        handler = new android.os.Handler(android.os.Looper.getMainLooper());
                    }
                    try {
                        int registerCallback = this.mFuseAppLoop.registerCallback(proxyFileDescriptorCallback, handler);
                        openProxyFileDescriptor = this.mStorageManager.openProxyFileDescriptor(this.mFuseAppLoop.getMountPointId(), registerCallback, i);
                        if (openProxyFileDescriptor == null) {
                            this.mFuseAppLoop.unregisterCallback(registerCallback);
                            throw new com.android.internal.os.FuseUnavailableMountException(this.mFuseAppLoop.getMountPointId());
                        }
                    } catch (com.android.internal.os.FuseUnavailableMountException e) {
                        if (z) {
                            throw new java.io.IOException(e);
                        }
                        this.mFuseAppLoop = null;
                    }
                }
                return openProxyFileDescriptor;
            } catch (android.os.RemoteException e2) {
                throw new java.io.IOException(e2);
            }
        }
    }

    public android.os.ParcelFileDescriptor openProxyFileDescriptor(int i, android.os.ProxyFileDescriptorCallback proxyFileDescriptorCallback) throws java.io.IOException {
        return openProxyFileDescriptor(i, proxyFileDescriptorCallback, null, null);
    }

    public android.os.ParcelFileDescriptor openProxyFileDescriptor(int i, android.os.ProxyFileDescriptorCallback proxyFileDescriptorCallback, android.os.Handler handler) throws java.io.IOException {
        com.android.internal.util.Preconditions.checkNotNull(handler);
        return openProxyFileDescriptor(i, proxyFileDescriptorCallback, handler, null);
    }

    public int getProxyFileDescriptorMountPointId() {
        int mountPointId;
        synchronized (this.mFuseAppLoopLock) {
            mountPointId = this.mFuseAppLoop != null ? this.mFuseAppLoop.getMountPointId() : -1;
        }
        return mountPointId;
    }

    public long getCacheQuotaBytes(java.util.UUID uuid) throws java.io.IOException {
        try {
            return this.mStorageManager.getCacheQuotaBytes(convert(uuid), this.mContext.getApplicationInfo().uid);
        } catch (android.os.ParcelableException e) {
            e.maybeRethrow(java.io.IOException.class);
            throw new java.lang.RuntimeException(e);
        } catch (android.os.RemoteException e2) {
            throw e2.rethrowFromSystemServer();
        }
    }

    public long getCacheSizeBytes(java.util.UUID uuid) throws java.io.IOException {
        try {
            return this.mStorageManager.getCacheSizeBytes(convert(uuid), this.mContext.getApplicationInfo().uid);
        } catch (android.os.ParcelableException e) {
            e.maybeRethrow(java.io.IOException.class);
            throw new java.lang.RuntimeException(e);
        } catch (android.os.RemoteException e2) {
            throw e2.rethrowFromSystemServer();
        }
    }

    public long getAllocatableBytes(java.util.UUID uuid) throws java.io.IOException {
        return getAllocatableBytes(uuid, 0);
    }

    @android.annotation.SystemApi
    public long getAllocatableBytes(java.util.UUID uuid, int i) throws java.io.IOException {
        try {
            return this.mStorageManager.getAllocatableBytes(convert(uuid), i, this.mContext.getOpPackageName());
        } catch (android.os.ParcelableException e) {
            e.maybeRethrow(java.io.IOException.class);
            throw new java.lang.RuntimeException(e);
        } catch (android.os.RemoteException e2) {
            throw e2.rethrowFromSystemServer();
        }
    }

    public void allocateBytes(java.util.UUID uuid, long j) throws java.io.IOException {
        allocateBytes(uuid, j, 0);
    }

    @android.annotation.SystemApi
    public void allocateBytes(java.util.UUID uuid, long j, int i) throws java.io.IOException {
        try {
            this.mStorageManager.allocateBytes(convert(uuid), j, i, this.mContext.getOpPackageName());
        } catch (android.os.ParcelableException e) {
            e.maybeRethrow(java.io.IOException.class);
        } catch (android.os.RemoteException e2) {
            throw e2.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public int getExternalStorageMountMode(int i, java.lang.String str) {
        try {
            return this.mStorageManager.getExternalStorageMountMode(i, str);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void allocateBytes(java.io.FileDescriptor fileDescriptor, long j) throws java.io.IOException {
        allocateBytes(fileDescriptor, j, 0);
    }

    @android.annotation.SystemApi
    public void allocateBytes(java.io.FileDescriptor fileDescriptor, long j, int i) throws java.io.IOException {
        java.io.File file = android.os.ParcelFileDescriptor.getFile(fileDescriptor);
        java.util.UUID uuidForPath = getUuidForPath(file);
        for (int i2 = 0; i2 < 3; i2++) {
            try {
                long j2 = j - (android.system.Os.fstat(fileDescriptor).st_blocks * 512);
                if (j2 > 0) {
                    allocateBytes(uuidForPath, j2, i);
                }
                try {
                    android.system.Os.posix_fallocate(fileDescriptor, 0L, j);
                    return;
                } catch (android.system.ErrnoException e) {
                    if (e.errno != android.system.OsConstants.ENOSYS && e.errno != android.system.OsConstants.ENOTSUP) {
                        throw e;
                    }
                    android.util.Log.w(TAG, "fallocate() not supported; falling back to ftruncate()");
                    android.system.Os.ftruncate(fileDescriptor, j);
                    return;
                }
            } catch (android.system.ErrnoException e2) {
                if (e2.errno == android.system.OsConstants.ENOSPC) {
                    android.util.Log.w(TAG, "Odd, not enough space; let's try again?");
                } else {
                    throw e2.rethrowAsIOException();
                }
            }
        }
        throw new java.io.IOException("Well this is embarassing; we can't allocate " + j + " for " + file);
    }

    private static long getProjectIdForUser(int i, int i2) {
        return (i * 100000) + i2;
    }

    @android.annotation.SystemApi
    public void updateExternalStorageFileQuotaType(java.io.File file, int i) throws java.io.IOException {
        int i2;
        long projectIdForUser;
        java.lang.String canonicalPath = file.getCanonicalPath();
        if (this.mContext.checkSelfPermission(android.Manifest.permission.MANAGE_EXTERNAL_STORAGE) != 0) {
            i2 = 1536;
        } else {
            i2 = 5632;
        }
        android.os.storage.StorageVolume storageVolume = getStorageVolume(getVolumeList(this.mContext.getUserId(), i2), file);
        if (storageVolume == null) {
            android.util.Log.w(TAG, "Failed to update quota type for " + canonicalPath);
            return;
        }
        if (!storageVolume.isEmulated()) {
            return;
        }
        int identifier = storageVolume.getOwner().getIdentifier();
        if (identifier < 0) {
            throw new java.lang.IllegalStateException("Failed to update quota type for " + canonicalPath);
        }
        switch (i) {
            case 0:
                projectIdForUser = getProjectIdForUser(identifier, 1000);
                break;
            case 1:
                projectIdForUser = getProjectIdForUser(identifier, 1003);
                break;
            case 2:
                projectIdForUser = getProjectIdForUser(identifier, 1001);
                break;
            case 3:
                projectIdForUser = getProjectIdForUser(identifier, 1002);
                break;
            default:
                throw new java.lang.IllegalArgumentException("Invalid quota type: " + i);
        }
        if (!setQuotaProjectId(canonicalPath, projectIdForUser)) {
            throw new java.io.IOException("Failed to update quota type for " + canonicalPath);
        }
    }

    public void fixupAppDir(java.io.File file) {
        try {
            this.mStorageManager.fixupAppDir(file.getCanonicalPath());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        } catch (java.io.IOException e2) {
            android.util.Log.e(TAG, "Failed to get canonical path for " + file.getPath(), e2);
        }
    }

    private static void setCacheBehavior(java.io.File file, java.lang.String str, boolean z) throws java.io.IOException {
        if (!file.isDirectory()) {
            throw new java.io.IOException("Cache behavior can only be set on directories");
        }
        if (z) {
            try {
                android.system.Os.setxattr(file.getAbsolutePath(), str, "1".getBytes(java.nio.charset.StandardCharsets.UTF_8), 0);
            } catch (android.system.ErrnoException e) {
                throw e.rethrowAsIOException();
            }
        } else {
            try {
                android.system.Os.removexattr(file.getAbsolutePath(), str);
            } catch (android.system.ErrnoException e2) {
                if (e2.errno != android.system.OsConstants.ENODATA) {
                    throw e2.rethrowAsIOException();
                }
            }
        }
    }

    private static boolean isCacheBehavior(java.io.File file, java.lang.String str) throws java.io.IOException {
        try {
            android.system.Os.getxattr(file.getAbsolutePath(), str);
            return true;
        } catch (android.system.ErrnoException e) {
            if (e.errno != android.system.OsConstants.ENODATA) {
                throw e.rethrowAsIOException();
            }
            return false;
        }
    }

    public void setCacheBehaviorGroup(java.io.File file, boolean z) throws java.io.IOException {
        setCacheBehavior(file, XATTR_CACHE_GROUP, z);
    }

    public boolean isCacheBehaviorGroup(java.io.File file) throws java.io.IOException {
        return isCacheBehavior(file, XATTR_CACHE_GROUP);
    }

    public void setCacheBehaviorTombstone(java.io.File file, boolean z) throws java.io.IOException {
        setCacheBehavior(file, XATTR_CACHE_TOMBSTONE, z);
    }

    public boolean isCacheBehaviorTombstone(java.io.File file) throws java.io.IOException {
        return isCacheBehavior(file, XATTR_CACHE_TOMBSTONE);
    }

    private static boolean isFatVolumeIdentifier(java.lang.String str) {
        return str.length() == 9 && str.charAt(4) == '-';
    }

    public static java.util.UUID convert(java.lang.String str) {
        if (java.util.Objects.equals(str, UUID_PRIVATE_INTERNAL)) {
            return UUID_DEFAULT;
        }
        if (java.util.Objects.equals(str, UUID_PRIMARY_PHYSICAL)) {
            return UUID_PRIMARY_PHYSICAL_;
        }
        if (java.util.Objects.equals(str, "system")) {
            return UUID_SYSTEM_;
        }
        if (isFatVolumeIdentifier(str)) {
            return java.util.UUID.fromString(FAT_UUID_PREFIX + str.replace(com.android.internal.content.NativeLibraryHelper.CLEAR_ABI_OVERRIDE, ""));
        }
        return java.util.UUID.fromString(str);
    }

    public static java.lang.String convert(java.util.UUID uuid) {
        if (UUID_DEFAULT.equals(uuid)) {
            return UUID_PRIVATE_INTERNAL;
        }
        if (UUID_PRIMARY_PHYSICAL_.equals(uuid)) {
            return UUID_PRIMARY_PHYSICAL;
        }
        if (UUID_SYSTEM_.equals(uuid)) {
            return "system";
        }
        java.lang.String obj = uuid.toString();
        if (obj.startsWith(FAT_UUID_PREFIX)) {
            java.lang.String upperCase = obj.substring(FAT_UUID_PREFIX.length()).toUpperCase(java.util.Locale.US);
            return upperCase.substring(0, 4) + com.android.internal.content.NativeLibraryHelper.CLEAR_ABI_OVERRIDE + upperCase.substring(4);
        }
        return uuid.toString();
    }

    public boolean isCheckpointSupported() {
        try {
            return this.mStorageManager.supportsCheckpoint();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi(client = android.annotation.SystemApi.Client.MODULE_LIBRARIES)
    public void notifyAppIoBlocked(java.util.UUID uuid, int i, int i2, int i3) {
        java.util.Objects.requireNonNull(uuid);
        try {
            this.mStorageManager.notifyAppIoBlocked(convert(uuid), i, i2, i3);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi(client = android.annotation.SystemApi.Client.MODULE_LIBRARIES)
    public void notifyAppIoResumed(java.util.UUID uuid, int i, int i2, int i3) {
        java.util.Objects.requireNonNull(uuid);
        try {
            this.mStorageManager.notifyAppIoResumed(convert(uuid), i, i2, i3);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isAppIoBlocked(java.util.UUID uuid, int i, int i2, int i3) {
        java.util.Objects.requireNonNull(uuid);
        try {
            return this.mStorageManager.isAppIoBlocked(convert(uuid), i, i2, i3);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi(client = android.annotation.SystemApi.Client.MODULE_LIBRARIES)
    public void setCloudMediaProvider(java.lang.String str) {
        try {
            this.mStorageManager.setCloudMediaProvider(str);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi(client = android.annotation.SystemApi.Client.MODULE_LIBRARIES)
    public java.lang.String getCloudMediaProvider() {
        try {
            return this.mStorageManager.getCloudMediaProvider();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public int getInternalStorageRemainingLifetime() {
        try {
            return this.mStorageManager.getInternalStorageRemainingLifetime();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }
}
