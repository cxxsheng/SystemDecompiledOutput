package android.media.tv.tuner;

@android.annotation.SystemApi
/* loaded from: classes2.dex */
public class Tuner implements java.lang.AutoCloseable {
    public static final int DVR_TYPE_PLAYBACK = 1;
    public static final int DVR_TYPE_RECORD = 0;
    private static final int FILTER_CLEANUP_THRESHOLD = 256;
    public static final int INVALID_AV_SYNC_ID = -1;
    public static final int INVALID_FILTER_ID = -1;
    public static final long INVALID_FILTER_ID_LONG = -1;
    public static final int INVALID_FIRST_MACROBLOCK_IN_SLICE = -1;
    public static final int INVALID_FRONTEND_ID = -1;
    public static final int INVALID_FRONTEND_SETTING_FREQUENCY = -1;
    public static final int INVALID_LNB_ID = -1;
    public static final int INVALID_LTS_ID = -1;
    public static final int INVALID_MMTP_RECORD_EVENT_MPT_SEQUENCE_NUM = -1;
    public static final int INVALID_STREAM_ID = 65535;
    public static final long INVALID_TIMESTAMP = -1;
    public static final int INVALID_TS_PID = 65535;
    private static final int MSG_ON_FILTER_EVENT = 2;
    private static final int MSG_ON_FILTER_STATUS = 3;
    private static final int MSG_ON_LNB_EVENT = 4;
    private static final int MSG_RESOURCE_LOST = 1;
    public static final int RESULT_INVALID_ARGUMENT = 4;
    public static final int RESULT_INVALID_STATE = 3;
    public static final int RESULT_NOT_INITIALIZED = 2;
    public static final int RESULT_OUT_OF_MEMORY = 5;
    public static final int RESULT_SUCCESS = 0;
    public static final int RESULT_UNAVAILABLE = 1;
    public static final int RESULT_UNKNOWN_ERROR = 6;
    public static final int SCAN_TYPE_AUTO = 1;
    public static final int SCAN_TYPE_BLIND = 2;
    public static final int SCAN_TYPE_UNDEFINED = 0;
    private static int sTunerVersion;
    private final int mClientId;
    private final android.content.Context mContext;
    private java.lang.Integer mDemuxHandle;
    private android.media.tv.tuner.Tuner.Frontend mFrontend;
    private java.lang.Integer mFrontendCiCamHandle;
    private java.lang.Integer mFrontendCiCamId;
    private java.lang.Integer mFrontendHandle;
    private android.media.tv.tuner.frontend.FrontendInfo mFrontendInfo;
    private android.media.tv.tuner.Tuner.EventHandler mHandler;
    private android.media.tv.tuner.Lnb mLnb;
    private java.lang.Integer mLnbHandle;
    private long mNativeContext;
    private android.media.tv.tuner.Tuner.OnResourceLostListener mOnResourceLostListener;
    private java.util.concurrent.Executor mOnResourceLostListenerExecutor;
    private java.util.concurrent.Executor mOnTuneEventExecutor;
    private android.media.tv.tuner.frontend.OnTuneEventListener mOnTuneEventListener;
    private int mRequestedCiCamId;
    private android.media.tv.tuner.frontend.ScanCallback mScanCallback;
    private java.util.concurrent.Executor mScanCallbackExecutor;
    private final android.media.tv.tunerresourcemanager.TunerResourceManager mTunerResourceManager;
    private int mUserId;
    public static final byte[] VOID_KEYTOKEN = {0};
    private static final java.lang.String TAG = "MediaTvTuner";
    private static final boolean DEBUG = android.util.Log.isLoggable(TAG, 3);
    private android.media.tv.tuner.DemuxInfo mDesiredDemuxInfo = new android.media.tv.tuner.DemuxInfo(0);
    private android.media.tv.tuner.Tuner mFeOwnerTuner = null;
    private int mFrontendType = 0;
    private java.lang.Integer mDesiredFrontendId = null;
    private final java.lang.Object mOnTuneEventLock = new java.lang.Object();
    private final java.lang.Object mScanCallbackLock = new java.lang.Object();
    private final java.lang.Object mOnResourceLostListenerLock = new java.lang.Object();
    private final java.util.concurrent.locks.ReentrantLock mFrontendLock = new java.util.concurrent.locks.ReentrantLock();
    private final java.util.concurrent.locks.ReentrantLock mLnbLock = new java.util.concurrent.locks.ReentrantLock();
    private final java.util.concurrent.locks.ReentrantLock mFrontendCiCamLock = new java.util.concurrent.locks.ReentrantLock();
    private final java.util.concurrent.locks.ReentrantLock mDemuxLock = new java.util.concurrent.locks.ReentrantLock();
    private java.util.Map<java.lang.Integer, java.lang.ref.WeakReference<android.media.tv.tuner.Descrambler>> mDescramblers = new java.util.HashMap();
    private java.util.List<java.lang.ref.WeakReference<android.media.tv.tuner.filter.Filter>> mFilters = new java.util.ArrayList();
    private final android.media.tv.tunerresourcemanager.TunerResourceManager.ResourcesReclaimListener mResourceListener = new android.media.tv.tunerresourcemanager.TunerResourceManager.ResourcesReclaimListener() { // from class: android.media.tv.tuner.Tuner.1
        @Override // android.media.tv.tunerresourcemanager.TunerResourceManager.ResourcesReclaimListener
        public void onReclaimResources() {
            if (android.media.tv.tuner.Tuner.this.mFrontend != null) {
                com.android.internal.util.FrameworkStatsLog.write(276, android.media.tv.tuner.Tuner.this.mUserId, 0);
            }
            android.media.tv.tuner.Tuner.this.releaseAll();
            android.media.tv.tuner.Tuner.this.mHandler.sendMessage(android.media.tv.tuner.Tuner.this.mHandler.obtainMessage(1));
        }
    };

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface DvrType {
    }

    public interface OnResourceLostListener {
        void onResourceLost(android.media.tv.tuner.Tuner tuner);
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface Result {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface ScanType {
    }

    private native int nativeClose();

    private native int nativeCloseDemux(int i);

    private native int nativeCloseFrontend(int i);

    private native int nativeConnectCiCam(int i);

    private native int nativeDisconnectCiCam();

    private native java.lang.Integer nativeGetAvSyncHwId(android.media.tv.tuner.filter.Filter filter);

    private native java.lang.Long nativeGetAvSyncTime(int i);

    private native android.media.tv.tuner.DemuxCapabilities nativeGetDemuxCapabilities();

    private native android.media.tv.tuner.DemuxInfo nativeGetDemuxInfo(int i);

    private native java.lang.String nativeGetFrontendHardwareInfo();

    private native java.util.List<java.lang.Integer> nativeGetFrontendIds();

    private native android.media.tv.tuner.frontend.FrontendInfo nativeGetFrontendInfo(int i);

    private native android.media.tv.tuner.frontend.FrontendStatus nativeGetFrontendStatus(int[] iArr);

    private native android.media.tv.tuner.frontend.FrontendStatusReadiness[] nativeGetFrontendStatusReadiness(int[] iArr);

    private native int nativeGetMaxNumberOfFrontends(int i);

    private native int nativeGetTunerVersion();

    private static native void nativeInit();

    private native boolean nativeIsLnaSupported();

    private native int nativeLinkCiCam(int i);

    private native int nativeOpenDemuxByhandle(int i);

    private native android.media.tv.tuner.Descrambler nativeOpenDescramblerByHandle(int i);

    private native android.media.tv.tuner.dvr.DvrPlayback nativeOpenDvrPlayback(long j);

    private native android.media.tv.tuner.dvr.DvrRecorder nativeOpenDvrRecorder(long j);

    private native android.media.tv.tuner.filter.Filter nativeOpenFilter(int i, int i2, long j);

    private native android.media.tv.tuner.Tuner.Frontend nativeOpenFrontendByHandle(int i);

    private native android.media.tv.tuner.Lnb nativeOpenLnbByHandle(int i);

    private native android.media.tv.tuner.Lnb nativeOpenLnbByName(java.lang.String str);

    private static native android.media.tv.tuner.filter.SharedFilter nativeOpenSharedFilter(java.lang.String str);

    private native android.media.tv.tuner.filter.TimeFilter nativeOpenTimeFilter();

    private native void nativeRegisterFeCbListener(long j);

    private native int nativeRemoveOutputPid(int i);

    private native int nativeScan(int i, android.media.tv.tuner.frontend.FrontendSettings frontendSettings, int i2);

    private native int nativeSetLna(boolean z);

    private native int nativeSetLnb(android.media.tv.tuner.Lnb lnb);

    private native int nativeSetMaxNumberOfFrontends(int i, int i2);

    private native void nativeSetup();

    private native int nativeShareFrontend(int i);

    private native int nativeStopScan();

    private native int nativeStopTune();

    private native int nativeTune(int i, android.media.tv.tuner.frontend.FrontendSettings frontendSettings);

    private native int nativeUnlinkCiCam(int i);

    private native void nativeUnregisterFeCbListener(long j);

    private native int nativeUnshareFrontend();

    private native void nativeUpdateFrontend(long j);

    static {
        try {
            java.lang.System.loadLibrary("media_tv_tuner");
            nativeInit();
            java.lang.Class.forName(android.media.MediaCodec.class.getName());
        } catch (java.lang.ClassNotFoundException e) {
            android.util.Log.e(TAG, "MediaCodec class not found!", e);
        } catch (java.lang.UnsatisfiedLinkError e2) {
            android.util.Log.d(TAG, "tuner JNI library not found!");
        }
        sTunerVersion = 0;
    }

    public Tuner(android.content.Context context, java.lang.String str, int i) {
        this.mContext = context;
        this.mTunerResourceManager = (android.media.tv.tunerresourcemanager.TunerResourceManager) this.mContext.getSystemService(android.media.tv.tunerresourcemanager.TunerResourceManager.class);
        if (this.mTunerResourceManager == null) {
            throw new java.lang.IllegalStateException("Tuner instance is created, but the device doesn't have tuner feature");
        }
        nativeSetup();
        sTunerVersion = nativeGetTunerVersion();
        if (sTunerVersion == 0) {
            android.util.Log.e(TAG, "Unknown Tuner version!");
        } else {
            android.util.Log.d(TAG, "Current Tuner version is " + android.media.tv.tuner.TunerVersionChecker.getMajorVersion(sTunerVersion) + android.media.MediaMetrics.SEPARATOR + android.media.tv.tuner.TunerVersionChecker.getMinorVersion(sTunerVersion) + android.media.MediaMetrics.SEPARATOR);
        }
        if (this.mHandler == null) {
            this.mHandler = createEventHandler();
        }
        int[] iArr = new int[1];
        android.media.tv.tunerresourcemanager.ResourceClientProfile resourceClientProfile = new android.media.tv.tunerresourcemanager.ResourceClientProfile();
        resourceClientProfile.tvInputSessionId = str;
        resourceClientProfile.useCase = i;
        this.mTunerResourceManager.registerClientProfile(resourceClientProfile, new android.app.PendingIntent$$ExternalSyntheticLambda0(), this.mResourceListener, iArr);
        this.mClientId = iArr[0];
        this.mUserId = android.os.Process.myUid();
    }

    private android.media.tv.tuner.frontend.FrontendInfo[] getFrontendInfoListInternal() {
        java.util.List<java.lang.Integer> frontendIds = getFrontendIds();
        if (frontendIds == null) {
            return null;
        }
        android.media.tv.tuner.frontend.FrontendInfo[] frontendInfoArr = new android.media.tv.tuner.frontend.FrontendInfo[frontendIds.size()];
        for (int i = 0; i < frontendIds.size(); i++) {
            int intValue = frontendIds.get(i).intValue();
            android.media.tv.tuner.frontend.FrontendInfo frontendInfoById = getFrontendInfoById(intValue);
            if (frontendInfoById == null) {
                android.util.Log.e(TAG, "Failed to get a FrontendInfo on frontend id:" + intValue + "!");
            } else {
                frontendInfoArr[i] = frontendInfoById;
            }
        }
        return (android.media.tv.tuner.frontend.FrontendInfo[]) java.util.Arrays.stream(frontendInfoArr).filter(new java.util.function.Predicate() { // from class: android.media.tv.tuner.Tuner$$ExternalSyntheticLambda12
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                return java.util.Objects.nonNull((android.media.tv.tuner.frontend.FrontendInfo) obj);
            }
        }).toArray(new java.util.function.IntFunction() { // from class: android.media.tv.tuner.Tuner$$ExternalSyntheticLambda13
            @Override // java.util.function.IntFunction
            public final java.lang.Object apply(int i2) {
                return android.media.tv.tuner.Tuner.lambda$getFrontendInfoListInternal$0(i2);
            }
        });
    }

    static /* synthetic */ android.media.tv.tuner.frontend.FrontendInfo[] lambda$getFrontendInfoListInternal$0(int i) {
        return new android.media.tv.tuner.frontend.FrontendInfo[i];
    }

    public static int getTunerVersion() {
        return sTunerVersion;
    }

    public java.util.List<java.lang.Integer> getFrontendIds() {
        this.mFrontendLock.lock();
        try {
            return nativeGetFrontendIds();
        } finally {
            this.mFrontendLock.unlock();
        }
    }

    public void setResourceLostListener(java.util.concurrent.Executor executor, android.media.tv.tuner.Tuner.OnResourceLostListener onResourceLostListener) {
        synchronized (this.mOnResourceLostListenerLock) {
            java.util.Objects.requireNonNull(executor, "OnResourceLostListener must not be null");
            java.util.Objects.requireNonNull(onResourceLostListener, "executor must not be null");
            this.mOnResourceLostListener = onResourceLostListener;
            this.mOnResourceLostListenerExecutor = executor;
        }
    }

    public void clearResourceLostListener() {
        synchronized (this.mOnResourceLostListenerLock) {
            this.mOnResourceLostListener = null;
            this.mOnResourceLostListenerExecutor = null;
        }
    }

    public void shareFrontendFromTuner(android.media.tv.tuner.Tuner tuner) {
        acquireTRMSLock("shareFrontendFromTuner()");
        this.mFrontendLock.lock();
        try {
            if (this.mFeOwnerTuner != null) {
                this.mFeOwnerTuner.unregisterFrontendCallbackListener(this);
                this.mFeOwnerTuner = null;
                nativeUnshareFrontend();
            }
            this.mTunerResourceManager.shareFrontend(this.mClientId, tuner.mClientId);
            this.mFeOwnerTuner = tuner;
            this.mFeOwnerTuner.registerFrontendCallbackListener(this);
            this.mFrontendHandle = this.mFeOwnerTuner.mFrontendHandle;
            this.mFrontend = this.mFeOwnerTuner.mFrontend;
            nativeShareFrontend(this.mFrontend.mId);
        } finally {
            releaseTRMSLock();
            this.mFrontendLock.unlock();
        }
    }

    public int transferOwner(android.media.tv.tuner.Tuner tuner) {
        acquireTRMSLock("transferOwner()");
        this.mFrontendLock.lock();
        this.mFrontendCiCamLock.lock();
        this.mLnbLock.lock();
        try {
            if (isFrontendOwner() && isNewOwnerQualifiedForTransfer(tuner)) {
                int transferFeOwner = transferFeOwner(tuner);
                if (transferFeOwner != 0) {
                    return transferFeOwner;
                }
                int transferCiCamOwner = transferCiCamOwner(tuner);
                if (transferCiCamOwner != 0) {
                    return transferCiCamOwner;
                }
                int transferLnbOwner = transferLnbOwner(tuner);
                if (transferLnbOwner != 0) {
                    return transferLnbOwner;
                }
                this.mFrontendLock.unlock();
                this.mFrontendCiCamLock.unlock();
                this.mLnbLock.unlock();
                releaseTRMSLock();
                return 0;
            }
            this.mFrontendLock.unlock();
            this.mFrontendCiCamLock.unlock();
            this.mLnbLock.unlock();
            releaseTRMSLock();
            return 3;
        } finally {
            this.mFrontendLock.unlock();
            this.mFrontendCiCamLock.unlock();
            this.mLnbLock.unlock();
            releaseTRMSLock();
        }
    }

    private void replicateFrontendSettings(android.media.tv.tuner.Tuner tuner) {
        this.mFrontendLock.lock();
        try {
            if (tuner == null) {
                if (DEBUG) {
                    android.util.Log.d(TAG, "resetting Frontend params for " + this.mClientId);
                }
                this.mFrontend = null;
                this.mFrontendHandle = null;
                this.mFrontendInfo = null;
                this.mFrontendType = 0;
            } else {
                if (DEBUG) {
                    android.util.Log.d(TAG, "copying Frontend params from " + tuner.mClientId + " to " + this.mClientId);
                }
                this.mFrontend = tuner.mFrontend;
                this.mFrontendHandle = tuner.mFrontendHandle;
                this.mFrontendInfo = tuner.mFrontendInfo;
                this.mFrontendType = tuner.mFrontendType;
            }
        } finally {
            this.mFrontendLock.unlock();
        }
    }

    private void setFrontendOwner(android.media.tv.tuner.Tuner tuner) {
        this.mFrontendLock.lock();
        try {
            this.mFeOwnerTuner = tuner;
        } finally {
            this.mFrontendLock.unlock();
        }
    }

    private void replicateCiCamSettings(android.media.tv.tuner.Tuner tuner) {
        this.mFrontendCiCamLock.lock();
        try {
            if (tuner == null) {
                if (DEBUG) {
                    android.util.Log.d(TAG, "resetting CiCamParams: " + this.mClientId);
                }
                this.mFrontendCiCamHandle = null;
                this.mFrontendCiCamId = null;
            } else {
                if (DEBUG) {
                    android.util.Log.d(TAG, "copying CiCamParams from " + tuner.mClientId + " to " + this.mClientId);
                    android.util.Log.d(TAG, "mFrontendCiCamHandle:" + tuner.mFrontendCiCamHandle + ", mFrontendCiCamId:" + tuner.mFrontendCiCamId);
                }
                this.mFrontendCiCamHandle = tuner.mFrontendCiCamHandle;
                this.mFrontendCiCamId = tuner.mFrontendCiCamId;
            }
        } finally {
            this.mFrontendCiCamLock.unlock();
        }
    }

    private void replicateLnbSettings(android.media.tv.tuner.Tuner tuner) {
        this.mLnbLock.lock();
        try {
            if (tuner == null) {
                if (DEBUG) {
                    android.util.Log.d(TAG, "resetting Lnb params");
                }
                this.mLnb = null;
                this.mLnbHandle = null;
            } else {
                if (DEBUG) {
                    android.util.Log.d(TAG, "copying Lnb params from " + tuner.mClientId + " to " + this.mClientId);
                }
                this.mLnb = tuner.mLnb;
                this.mLnbHandle = tuner.mLnbHandle;
            }
        } finally {
            this.mLnbLock.unlock();
        }
    }

    private boolean isFrontendOwner() {
        if (!(this.mFeOwnerTuner != null)) {
            return true;
        }
        android.util.Log.e(TAG, "transferOwner() - cannot be called on the non-owner");
        return false;
    }

    private boolean isNewOwnerQualifiedForTransfer(android.media.tv.tuner.Tuner tuner) {
        if (!(tuner.mFeOwnerTuner == this && tuner.mFrontendHandle.equals(this.mFrontendHandle))) {
            android.util.Log.e(TAG, "transferOwner() - new owner must be the current sharee");
            return false;
        }
        if (!((tuner.mFrontendCiCamHandle == null && tuner.mLnb == null) ? false : true)) {
            return true;
        }
        android.util.Log.e(TAG, "transferOwner() - new owner cannot be holding CiCam nor Lnb resource");
        return false;
    }

    private int transferFeOwner(android.media.tv.tuner.Tuner tuner) {
        tuner.nativeUpdateFrontend(getNativeContext());
        nativeUpdateFrontend(0L);
        tuner.replicateFrontendSettings(this);
        setFrontendOwner(tuner);
        tuner.setFrontendOwner(null);
        return this.mTunerResourceManager.transferOwner(0, this.mClientId, tuner.mClientId) ? 0 : 6;
    }

    private int transferCiCamOwner(android.media.tv.tuner.Tuner tuner) {
        if (this.mFrontendCiCamHandle == null) {
            return 0;
        }
        tuner.replicateCiCamSettings(this);
        replicateCiCamSettings(null);
        return this.mTunerResourceManager.transferOwner(5, this.mClientId, tuner.mClientId) ? 0 : 6;
    }

    private int transferLnbOwner(android.media.tv.tuner.Tuner tuner) {
        if (this.mLnb == null) {
            return 0;
        }
        this.mLnb.setOwner(tuner);
        tuner.replicateLnbSettings(this);
        replicateLnbSettings(null);
        return this.mTunerResourceManager.transferOwner(3, this.mClientId, tuner.mClientId) ? 0 : 6;
    }

    public void updateResourcePriority(int i, int i2) {
        this.mTunerResourceManager.updateClientPriority(this.mClientId, i, i2);
    }

    public boolean hasUnusedFrontend(int i) {
        return this.mTunerResourceManager.hasUnusedFrontend(i);
    }

    public boolean isLowestPriority(int i) {
        return this.mTunerResourceManager.isLowestPriority(this.mClientId, i);
    }

    private void registerFrontendCallbackListener(android.media.tv.tuner.Tuner tuner) {
        nativeRegisterFeCbListener(tuner.getNativeContext());
    }

    private void unregisterFrontendCallbackListener(android.media.tv.tuner.Tuner tuner) {
        nativeUnregisterFeCbListener(tuner.getNativeContext());
    }

    long getNativeContext() {
        return this.mNativeContext;
    }

    @Override // java.lang.AutoCloseable
    public void close() {
        acquireTRMSLock("close()");
        try {
            releaseAll();
            this.mTunerResourceManager.unregisterClientProfile(this.mClientId);
            android.media.tv.tuner.TunerUtils.throwExceptionForResult(nativeClose(), "failed to close tuner");
        } finally {
            releaseTRMSLock();
        }
    }

    public void closeFrontend() {
        acquireTRMSLock("closeFrontend()");
        try {
            releaseFrontend();
        } finally {
            releaseTRMSLock();
        }
    }

    private void releaseFrontend() {
        if (DEBUG) {
            android.util.Log.d(TAG, "Tuner#releaseFrontend");
        }
        this.mFrontendLock.lock();
        try {
            if (this.mFrontendHandle != null) {
                if (DEBUG) {
                    android.util.Log.d(TAG, "mFrontendHandle not null");
                }
                if (this.mFeOwnerTuner != null) {
                    if (DEBUG) {
                        android.util.Log.d(TAG, "mFeOwnerTuner not null - sharee");
                    }
                    this.mFeOwnerTuner.unregisterFrontendCallbackListener(this);
                    this.mFeOwnerTuner = null;
                    nativeUnshareFrontend();
                } else {
                    if (DEBUG) {
                        android.util.Log.d(TAG, "mFeOwnerTuner null - owner");
                    }
                    int nativeCloseFrontend = nativeCloseFrontend(this.mFrontendHandle.intValue());
                    if (nativeCloseFrontend != 0) {
                        android.media.tv.tuner.TunerUtils.throwExceptionForResult(nativeCloseFrontend, "failed to close frontend");
                    }
                }
                if (DEBUG) {
                    android.util.Log.d(TAG, "call TRM#releaseFrontend :" + this.mFrontendHandle + ", " + this.mClientId);
                }
                this.mTunerResourceManager.releaseFrontend(this.mFrontendHandle.intValue(), this.mClientId);
                com.android.internal.util.FrameworkStatsLog.write(276, this.mUserId, 0);
                replicateFrontendSettings(null);
            }
        } finally {
            this.mFrontendLock.unlock();
        }
    }

    private void releaseCiCam() {
        this.mFrontendCiCamLock.lock();
        try {
            if (this.mFrontendCiCamHandle != null) {
                if (DEBUG) {
                    android.util.Log.d(TAG, "unlinking CiCam : " + this.mFrontendCiCamHandle + " for " + this.mClientId);
                }
                int nativeUnlinkCiCam = nativeUnlinkCiCam(this.mFrontendCiCamId.intValue());
                if (nativeUnlinkCiCam == 0) {
                    this.mTunerResourceManager.releaseCiCam(this.mFrontendCiCamHandle.intValue(), this.mClientId);
                    replicateCiCamSettings(null);
                } else {
                    android.util.Log.e(TAG, "nativeUnlinkCiCam(" + this.mFrontendCiCamHandle + ") for mClientId:" + this.mClientId + "failed with result:" + nativeUnlinkCiCam);
                }
            } else if (DEBUG) {
                android.util.Log.d(TAG, "NOT unlinking CiCam : " + this.mClientId);
            }
        } finally {
            this.mFrontendCiCamLock.unlock();
        }
    }

    private void closeLnb() {
        this.mLnbLock.lock();
        try {
            if (this.mLnb != null) {
                if (DEBUG) {
                    android.util.Log.d(TAG, "calling mLnb.close() : " + this.mClientId);
                }
                this.mLnb.closeInternal();
            } else if (DEBUG) {
                android.util.Log.d(TAG, "NOT calling mLnb.close() : " + this.mClientId);
            }
        } finally {
            this.mLnbLock.unlock();
        }
    }

    private void releaseFilters() {
        synchronized (this.mFilters) {
            if (!this.mFilters.isEmpty()) {
                java.util.Iterator<java.lang.ref.WeakReference<android.media.tv.tuner.filter.Filter>> it = this.mFilters.iterator();
                while (it.hasNext()) {
                    android.media.tv.tuner.filter.Filter filter = it.next().get();
                    if (filter != null) {
                        filter.close();
                    }
                }
                this.mFilters.clear();
            }
        }
    }

    private void releaseDescramblers() {
        synchronized (this.mDescramblers) {
            if (!this.mDescramblers.isEmpty()) {
                for (java.util.Map.Entry<java.lang.Integer, java.lang.ref.WeakReference<android.media.tv.tuner.Descrambler>> entry : this.mDescramblers.entrySet()) {
                    android.media.tv.tuner.Descrambler descrambler = entry.getValue().get();
                    if (descrambler != null) {
                        descrambler.close();
                    }
                    this.mTunerResourceManager.releaseDescrambler(entry.getKey().intValue(), this.mClientId);
                }
                this.mDescramblers.clear();
            }
        }
    }

    private void releaseDemux() {
        this.mDemuxLock.lock();
        try {
            if (this.mDemuxHandle != null) {
                int nativeCloseDemux = nativeCloseDemux(this.mDemuxHandle.intValue());
                if (nativeCloseDemux != 0) {
                    android.media.tv.tuner.TunerUtils.throwExceptionForResult(nativeCloseDemux, "failed to close demux");
                }
                this.mTunerResourceManager.releaseDemux(this.mDemuxHandle.intValue(), this.mClientId);
                this.mDemuxHandle = null;
            }
        } finally {
            this.mDemuxLock.unlock();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void releaseAll() {
        releaseCiCam();
        releaseFrontend();
        closeLnb();
        releaseDescramblers();
        releaseFilters();
        releaseDemux();
    }

    private android.media.tv.tuner.Tuner.EventHandler createEventHandler() {
        android.os.Looper myLooper = android.os.Looper.myLooper();
        if (myLooper != null) {
            return new android.media.tv.tuner.Tuner.EventHandler(myLooper);
        }
        android.os.Looper mainLooper = android.os.Looper.getMainLooper();
        if (mainLooper != null) {
            return new android.media.tv.tuner.Tuner.EventHandler(mainLooper);
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    class EventHandler extends android.os.Handler {
        private EventHandler(android.os.Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(android.os.Message message) {
            switch (message.what) {
                case 1:
                    synchronized (android.media.tv.tuner.Tuner.this.mOnResourceLostListenerLock) {
                        if (android.media.tv.tuner.Tuner.this.mOnResourceLostListener != null && android.media.tv.tuner.Tuner.this.mOnResourceLostListenerExecutor != null) {
                            android.media.tv.tuner.Tuner.this.mOnResourceLostListenerExecutor.execute(new java.lang.Runnable() { // from class: android.media.tv.tuner.Tuner$EventHandler$$ExternalSyntheticLambda0
                                @Override // java.lang.Runnable
                                public final void run() {
                                    android.media.tv.tuner.Tuner.EventHandler.this.lambda$handleMessage$0();
                                }
                            });
                        }
                    }
                    return;
                case 2:
                default:
                    return;
                case 3:
                    android.media.tv.tuner.filter.Filter filter = (android.media.tv.tuner.filter.Filter) message.obj;
                    if (filter.getCallback() != null) {
                        filter.getCallback().onFilterStatusChanged(filter, message.arg1);
                        return;
                    }
                    return;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$handleMessage$0() {
            synchronized (android.media.tv.tuner.Tuner.this.mOnResourceLostListenerLock) {
                if (android.media.tv.tuner.Tuner.this.mOnResourceLostListener != null) {
                    android.media.tv.tuner.Tuner.this.mOnResourceLostListener.onResourceLost(android.media.tv.tuner.Tuner.this);
                }
            }
        }
    }

    private class Frontend {
        private int mId;

        private Frontend(int i) {
            this.mId = i;
        }
    }

    public void setOnTuneEventListener(java.util.concurrent.Executor executor, android.media.tv.tuner.frontend.OnTuneEventListener onTuneEventListener) {
        synchronized (this.mOnTuneEventLock) {
            this.mOnTuneEventListener = onTuneEventListener;
            this.mOnTuneEventExecutor = executor;
        }
    }

    public void clearOnTuneEventListener() {
        synchronized (this.mOnTuneEventLock) {
            this.mOnTuneEventListener = null;
            this.mOnTuneEventExecutor = null;
        }
    }

    public int tune(android.media.tv.tuner.frontend.FrontendSettings frontendSettings) {
        this.mFrontendLock.lock();
        try {
            if (this.mFeOwnerTuner != null) {
                android.util.Log.d(TAG, "Operation cannot be done by sharee of tuner");
                return 3;
            }
            int type = frontendSettings.getType();
            if (this.mFrontendHandle != null && type != this.mFrontendType) {
                android.util.Log.e(TAG, "Frontend was opened with type " + this.mFrontendType + ", new type is " + type);
                return 3;
            }
            android.util.Log.d(TAG, "Tune to " + frontendSettings.getFrequencyLong());
            this.mFrontendType = type;
            if (this.mFrontendType == 10 && !android.media.tv.tuner.TunerVersionChecker.checkHigherOrEqualVersionTo(65537, "Tuner with DTMB Frontend")) {
                return 1;
            }
            if (this.mFrontendType == 11 && !android.media.tv.tuner.TunerVersionChecker.checkHigherOrEqualVersionTo(196608, "Tuner with IPTV Frontend")) {
                return 1;
            }
            if (!checkResource(0, this.mFrontendLock)) {
                return 1;
            }
            this.mFrontendInfo = null;
            android.util.Log.d(TAG, "Write Stats Log for tuning.");
            com.android.internal.util.FrameworkStatsLog.write(276, this.mUserId, 1);
            return nativeTune(frontendSettings.getType(), frontendSettings);
        } finally {
            this.mFrontendLock.unlock();
        }
    }

    public int cancelTuning() {
        this.mFrontendLock.lock();
        try {
            if (this.mFeOwnerTuner != null) {
                android.util.Log.d(TAG, "Operation cannot be done by sharee of tuner");
                this.mFrontendLock.unlock();
                return 3;
            }
            return nativeStopTune();
        } finally {
            this.mFrontendLock.unlock();
        }
    }

    public int scan(android.media.tv.tuner.frontend.FrontendSettings frontendSettings, int i, java.util.concurrent.Executor executor, android.media.tv.tuner.frontend.ScanCallback scanCallback) {
        this.mFrontendLock.lock();
        try {
            if (this.mFeOwnerTuner != null) {
                android.util.Log.d(TAG, "Operation cannot be done by sharee of tuner");
                this.mFrontendLock.unlock();
                return 3;
            }
            synchronized (this.mScanCallbackLock) {
                if ((this.mScanCallback != null && this.mScanCallback != scanCallback) || (this.mScanCallbackExecutor != null && this.mScanCallbackExecutor != executor)) {
                    throw new java.lang.IllegalStateException("Different Scan session already in progress.  stopScan must be called before a new scan session can be started.");
                }
                this.mFrontendType = frontendSettings.getType();
                if (this.mFrontendType == 10 && !android.media.tv.tuner.TunerVersionChecker.checkHigherOrEqualVersionTo(65537, "Scan with DTMB Frontend")) {
                    return 1;
                }
                if (this.mFrontendType == 11 && !android.media.tv.tuner.TunerVersionChecker.checkHigherOrEqualVersionTo(196608, "Tuner with IPTV Frontend")) {
                    return 1;
                }
                if (!checkResource(0, this.mFrontendLock)) {
                    return 1;
                }
                this.mScanCallback = scanCallback;
                this.mScanCallbackExecutor = executor;
                this.mFrontendInfo = null;
                com.android.internal.util.FrameworkStatsLog.write(276, this.mUserId, 5);
                return nativeScan(frontendSettings.getType(), frontendSettings, i);
            }
        } finally {
            this.mFrontendLock.unlock();
        }
    }

    public int cancelScanning() {
        int nativeStopScan;
        this.mFrontendLock.lock();
        try {
            if (this.mFeOwnerTuner != null) {
                android.util.Log.d(TAG, "Operation cannot be done by sharee of tuner");
                this.mFrontendLock.unlock();
                return 3;
            }
            synchronized (this.mScanCallbackLock) {
                com.android.internal.util.FrameworkStatsLog.write(276, this.mUserId, 6);
                nativeStopScan = nativeStopScan();
                this.mScanCallback = null;
                this.mScanCallbackExecutor = null;
            }
            return nativeStopScan;
        } finally {
            this.mFrontendLock.unlock();
        }
    }

    private boolean requestFrontend() {
        int intValue;
        int[] iArr = new int[1];
        try {
            android.media.tv.tunerresourcemanager.TunerFrontendRequest tunerFrontendRequest = new android.media.tv.tunerresourcemanager.TunerFrontendRequest();
            tunerFrontendRequest.clientId = this.mClientId;
            tunerFrontendRequest.frontendType = this.mFrontendType;
            if (this.mDesiredFrontendId == null) {
                intValue = -1;
            } else {
                intValue = this.mDesiredFrontendId.intValue();
            }
            tunerFrontendRequest.desiredId = intValue;
            boolean requestFrontend = this.mTunerResourceManager.requestFrontend(tunerFrontendRequest, iArr);
            if (requestFrontend) {
                this.mFrontendHandle = java.lang.Integer.valueOf(iArr[0]);
                this.mFrontend = nativeOpenFrontendByHandle(this.mFrontendHandle.intValue());
            }
            if (this.mFrontendType == 5 || this.mFrontendType == 7 || this.mFrontendType == 8) {
                this.mLnbLock.lock();
                try {
                    if (this.mLnbHandle != null && this.mLnb != null) {
                        nativeSetLnb(this.mLnb);
                    }
                } finally {
                    this.mLnbLock.unlock();
                }
            }
            return requestFrontend;
        } finally {
            this.mDesiredFrontendId = null;
        }
    }

    private int setLnb(android.media.tv.tuner.Lnb lnb) {
        this.mLnbLock.lock();
        try {
            return nativeSetLnb(lnb);
        } finally {
            this.mLnbLock.unlock();
        }
    }

    public boolean isLnaSupported() {
        if (!android.media.tv.tuner.TunerVersionChecker.checkHigherOrEqualVersionTo(196608, "isLnaSupported")) {
            throw new java.lang.UnsupportedOperationException("Tuner HAL version " + android.media.tv.tuner.TunerVersionChecker.getTunerVersion() + " doesn't support this method.");
        }
        return nativeIsLnaSupported();
    }

    public int setLnaEnabled(boolean z) {
        return nativeSetLna(z);
    }

    public android.media.tv.tuner.frontend.FrontendStatus getFrontendStatus(int[] iArr) {
        this.mFrontendLock.lock();
        try {
            if (this.mFrontend == null) {
                throw new java.lang.IllegalStateException("frontend is not initialized");
            }
            if (this.mFeOwnerTuner != null) {
                throw new java.lang.IllegalStateException("Operation cannot be done by sharee of tuner");
            }
            return nativeGetFrontendStatus(iArr);
        } finally {
            this.mFrontendLock.unlock();
        }
    }

    public int getAvSyncHwId(android.media.tv.tuner.filter.Filter filter) {
        this.mDemuxLock.lock();
        try {
            int i = -1;
            if (!checkResource(1, this.mDemuxLock)) {
                return -1;
            }
            java.lang.Integer nativeGetAvSyncHwId = nativeGetAvSyncHwId(filter);
            if (nativeGetAvSyncHwId != null) {
                i = nativeGetAvSyncHwId.intValue();
            }
            return i;
        } finally {
            this.mDemuxLock.unlock();
        }
    }

    public long getAvSyncTime(int i) {
        this.mDemuxLock.lock();
        try {
            long j = -1;
            if (!checkResource(1, this.mDemuxLock)) {
                return -1L;
            }
            java.lang.Long nativeGetAvSyncTime = nativeGetAvSyncTime(i);
            if (nativeGetAvSyncTime != null) {
                j = nativeGetAvSyncTime.longValue();
            }
            return j;
        } finally {
            this.mDemuxLock.unlock();
        }
    }

    public int connectCiCam(int i) {
        this.mDemuxLock.lock();
        try {
            if (checkResource(1, this.mDemuxLock)) {
                return nativeConnectCiCam(i);
            }
            return 1;
        } finally {
            this.mDemuxLock.unlock();
        }
    }

    public int connectFrontendToCiCam(int i) {
        acquireTRMSLock("connectFrontendToCiCam()");
        this.mFrontendCiCamLock.lock();
        this.mFrontendLock.lock();
        try {
            if (this.mFrontendHandle == null) {
                android.util.Log.d(TAG, "Operation cannot be done without frontend");
                return 3;
            }
            if (this.mFeOwnerTuner != null) {
                android.util.Log.d(TAG, "Operation cannot be done by sharee of tuner");
                return 3;
            }
            if (android.media.tv.tuner.TunerVersionChecker.checkHigherOrEqualVersionTo(65537, "linkFrontendToCiCam")) {
                this.mRequestedCiCamId = i;
                if (checkResource(5, null) && checkResource(0, null)) {
                    return nativeLinkCiCam(i);
                }
            }
            releaseTRMSLock();
            this.mFrontendCiCamLock.unlock();
            this.mFrontendLock.unlock();
            return -1;
        } finally {
            releaseTRMSLock();
            this.mFrontendCiCamLock.unlock();
            this.mFrontendLock.unlock();
        }
    }

    public int disconnectCiCam() {
        this.mDemuxLock.lock();
        try {
            if (this.mDemuxHandle != null) {
                return nativeDisconnectCiCam();
            }
            this.mDemuxLock.unlock();
            return 1;
        } finally {
            this.mDemuxLock.unlock();
        }
    }

    public int disconnectFrontendToCiCam(int i) {
        acquireTRMSLock("disconnectFrontendToCiCam()");
        try {
            if (this.mFrontendHandle == null) {
                android.util.Log.d(TAG, "Operation cannot be done without frontend");
                return 3;
            }
            if (this.mFeOwnerTuner != null) {
                android.util.Log.d(TAG, "Operation cannot be done by sharee of tuner");
                if (this.mFrontendCiCamLock.isLocked()) {
                    this.mFrontendCiCamLock.unlock();
                }
                releaseTRMSLock();
                return 3;
            }
            if (android.media.tv.tuner.TunerVersionChecker.checkHigherOrEqualVersionTo(65537, "unlinkFrontendToCiCam")) {
                this.mFrontendCiCamLock.lock();
                if (this.mFrontendCiCamHandle != null && this.mFrontendCiCamId != null && this.mFrontendCiCamId.intValue() == i) {
                    int nativeUnlinkCiCam = nativeUnlinkCiCam(i);
                    if (nativeUnlinkCiCam == 0) {
                        this.mTunerResourceManager.releaseCiCam(this.mFrontendCiCamHandle.intValue(), this.mClientId);
                        this.mFrontendCiCamId = null;
                        this.mFrontendCiCamHandle = null;
                    }
                    if (this.mFrontendCiCamLock.isLocked()) {
                        this.mFrontendCiCamLock.unlock();
                    }
                    releaseTRMSLock();
                    return nativeUnlinkCiCam;
                }
            }
            if (this.mFrontendCiCamLock.isLocked()) {
                this.mFrontendCiCamLock.unlock();
            }
            releaseTRMSLock();
            return 1;
        } finally {
            if (this.mFrontendCiCamLock.isLocked()) {
                this.mFrontendCiCamLock.unlock();
            }
            releaseTRMSLock();
        }
    }

    public int removeOutputPid(int i) {
        this.mFrontendLock.lock();
        try {
            if (!android.media.tv.tuner.TunerVersionChecker.checkHigherOrEqualVersionTo(131072, "Remove output PID")) {
                this.mFrontendLock.unlock();
                return 1;
            }
            if (this.mFrontend == null) {
                throw new java.lang.IllegalStateException("frontend is not initialized");
            }
            if (this.mFeOwnerTuner == null) {
                return nativeRemoveOutputPid(i);
            }
            android.util.Log.d(TAG, "Operation cannot be done by sharee of tuner");
            this.mFrontendLock.unlock();
            return 3;
        } finally {
            this.mFrontendLock.unlock();
        }
    }

    public java.util.List<android.media.tv.tuner.frontend.FrontendStatusReadiness> getFrontendStatusReadiness(int[] iArr) {
        this.mFrontendLock.lock();
        try {
            if (!android.media.tv.tuner.TunerVersionChecker.checkHigherOrEqualVersionTo(131072, "Get fronted status readiness")) {
                return java.util.Collections.EMPTY_LIST;
            }
            if (this.mFrontend == null) {
                throw new java.lang.IllegalStateException("frontend is not initialized");
            }
            if (this.mFeOwnerTuner != null) {
                throw new java.lang.IllegalStateException("Operation cannot be done by sharee of tuner");
            }
            android.media.tv.tuner.frontend.FrontendStatusReadiness[] nativeGetFrontendStatusReadiness = nativeGetFrontendStatusReadiness(iArr);
            return nativeGetFrontendStatusReadiness == null ? java.util.Collections.EMPTY_LIST : java.util.Arrays.asList(nativeGetFrontendStatusReadiness);
        } finally {
            this.mFrontendLock.unlock();
        }
    }

    public android.media.tv.tuner.frontend.FrontendInfo getFrontendInfo() {
        this.mFrontendLock.lock();
        try {
            if (checkResource(0, this.mFrontendLock)) {
                if (this.mFrontend == null) {
                    throw new java.lang.IllegalStateException("frontend is not initialized");
                }
                if (this.mFrontendInfo == null) {
                    this.mFrontendInfo = getFrontendInfoById(this.mFrontend.mId);
                }
                return this.mFrontendInfo;
            }
            this.mFrontendLock.unlock();
            return null;
        } finally {
            this.mFrontendLock.unlock();
        }
    }

    public java.util.List<android.media.tv.tuner.frontend.FrontendInfo> getAvailableFrontendInfos() {
        android.media.tv.tuner.frontend.FrontendInfo[] frontendInfoListInternal = getFrontendInfoListInternal();
        if (frontendInfoListInternal == null) {
            return null;
        }
        return java.util.Arrays.asList(frontendInfoListInternal);
    }

    public java.lang.String getCurrentFrontendHardwareInfo() {
        this.mFrontendLock.lock();
        try {
            if (android.media.tv.tuner.TunerVersionChecker.checkHigherOrEqualVersionTo(131072, "Get Frontend hardware info")) {
                if (this.mFrontend == null) {
                    throw new java.lang.IllegalStateException("frontend is not initialized");
                }
                if (this.mFeOwnerTuner != null) {
                    throw new java.lang.IllegalStateException("Operation cannot be done by sharee of tuner");
                }
                return nativeGetFrontendHardwareInfo();
            }
            this.mFrontendLock.unlock();
            return null;
        } finally {
            this.mFrontendLock.unlock();
        }
    }

    public int setMaxNumberOfFrontends(int i, int i2) {
        if (!android.media.tv.tuner.TunerVersionChecker.checkHigherOrEqualVersionTo(131072, "Set maximum Frontends")) {
            return 1;
        }
        if (i2 < 0) {
            return 4;
        }
        if (this.mFeOwnerTuner != null) {
            android.util.Log.d(TAG, "Operation cannot be done by sharee of tuner");
            return 3;
        }
        int nativeSetMaxNumberOfFrontends = nativeSetMaxNumberOfFrontends(i, i2);
        if (nativeSetMaxNumberOfFrontends == 0 && !this.mTunerResourceManager.setMaxNumberOfFrontends(i, i2)) {
            return 4;
        }
        return nativeSetMaxNumberOfFrontends;
    }

    public int getMaxNumberOfFrontends(int i) {
        if (!android.media.tv.tuner.TunerVersionChecker.checkHigherOrEqualVersionTo(131072, "Set maximum Frontends")) {
            return -1;
        }
        int nativeGetMaxNumberOfFrontends = nativeGetMaxNumberOfFrontends(i);
        int maxNumberOfFrontends = this.mTunerResourceManager.getMaxNumberOfFrontends(i);
        if (nativeGetMaxNumberOfFrontends != maxNumberOfFrontends) {
            android.util.Log.w(TAG, "max num of usable frontend is out-of-sync b/w " + nativeGetMaxNumberOfFrontends + " != " + maxNumberOfFrontends);
        }
        return nativeGetMaxNumberOfFrontends;
    }

    public android.media.tv.tuner.frontend.FrontendInfo getFrontendInfoById(int i) {
        this.mFrontendLock.lock();
        try {
            return nativeGetFrontendInfo(i);
        } finally {
            this.mFrontendLock.unlock();
        }
    }

    public android.media.tv.tuner.DemuxCapabilities getDemuxCapabilities() {
        this.mDemuxLock.lock();
        try {
            return nativeGetDemuxCapabilities();
        } finally {
            this.mDemuxLock.unlock();
        }
    }

    public android.media.tv.tuner.DemuxInfo getCurrentDemuxInfo() {
        this.mDemuxLock.lock();
        try {
            if (this.mDemuxHandle != null) {
                return nativeGetDemuxInfo(this.mDemuxHandle.intValue());
            }
            this.mDemuxLock.unlock();
            return null;
        } finally {
            this.mDemuxLock.unlock();
        }
    }

    public android.media.tv.tuner.DemuxInfo getDesiredDemuxInfo() {
        return this.mDesiredDemuxInfo;
    }

    private void onFrontendEvent(final int i) {
        android.util.Log.d(TAG, "Got event from tuning. Event type: " + i + " for " + this);
        synchronized (this.mOnTuneEventLock) {
            if (this.mOnTuneEventExecutor != null && this.mOnTuneEventListener != null) {
                this.mOnTuneEventExecutor.execute(new java.lang.Runnable() { // from class: android.media.tv.tuner.Tuner$$ExternalSyntheticLambda21
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.media.tv.tuner.Tuner.this.lambda$onFrontendEvent$1(i);
                    }
                });
            }
        }
        android.util.Log.d(TAG, "Wrote Stats Log for the events from tuning.");
        if (i == 0) {
            com.android.internal.util.FrameworkStatsLog.write(276, this.mUserId, 2);
        } else if (i == 1) {
            com.android.internal.util.FrameworkStatsLog.write(276, this.mUserId, 3);
        } else if (i == 2) {
            com.android.internal.util.FrameworkStatsLog.write(276, this.mUserId, 4);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onFrontendEvent$1(int i) {
        synchronized (this.mOnTuneEventLock) {
            if (this.mOnTuneEventListener != null) {
                this.mOnTuneEventListener.onTuneEvent(i);
            }
        }
    }

    private void onLocked() {
        android.util.Log.d(TAG, "Wrote Stats Log for locked event from scanning.");
        com.android.internal.util.FrameworkStatsLog.write(276, this.mUserId, 2);
        synchronized (this.mScanCallbackLock) {
            if (this.mScanCallbackExecutor != null && this.mScanCallback != null) {
                this.mScanCallbackExecutor.execute(new java.lang.Runnable() { // from class: android.media.tv.tuner.Tuner$$ExternalSyntheticLambda19
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.media.tv.tuner.Tuner.this.lambda$onLocked$2();
                    }
                });
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onLocked$2() {
        synchronized (this.mScanCallbackLock) {
            if (this.mScanCallback != null) {
                this.mScanCallback.onLocked();
            }
        }
    }

    private void onUnlocked() {
        android.util.Log.d(TAG, "Wrote Stats Log for unlocked event from scanning.");
        com.android.internal.util.FrameworkStatsLog.write(276, this.mUserId, 2);
        synchronized (this.mScanCallbackLock) {
            if (this.mScanCallbackExecutor != null && this.mScanCallback != null) {
                this.mScanCallbackExecutor.execute(new java.lang.Runnable() { // from class: android.media.tv.tuner.Tuner$$ExternalSyntheticLambda6
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.media.tv.tuner.Tuner.this.lambda$onUnlocked$3();
                    }
                });
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onUnlocked$3() {
        synchronized (this.mScanCallbackLock) {
            if (this.mScanCallback != null) {
                this.mScanCallback.onUnlocked();
            }
        }
    }

    private void onScanStopped() {
        synchronized (this.mScanCallbackLock) {
            if (this.mScanCallbackExecutor != null && this.mScanCallback != null) {
                this.mScanCallbackExecutor.execute(new java.lang.Runnable() { // from class: android.media.tv.tuner.Tuner$$ExternalSyntheticLambda20
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.media.tv.tuner.Tuner.this.lambda$onScanStopped$4();
                    }
                });
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onScanStopped$4() {
        synchronized (this.mScanCallbackLock) {
            if (this.mScanCallback != null) {
                this.mScanCallback.onScanStopped();
            }
        }
    }

    private void onProgress(final int i) {
        synchronized (this.mScanCallbackLock) {
            if (this.mScanCallbackExecutor != null && this.mScanCallback != null) {
                this.mScanCallbackExecutor.execute(new java.lang.Runnable() { // from class: android.media.tv.tuner.Tuner$$ExternalSyntheticLambda4
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.media.tv.tuner.Tuner.this.lambda$onProgress$5(i);
                    }
                });
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onProgress$5(int i) {
        synchronized (this.mScanCallbackLock) {
            if (this.mScanCallback != null) {
                this.mScanCallback.onProgress(i);
            }
        }
    }

    private void onFrequenciesReport(final long[] jArr) {
        synchronized (this.mScanCallbackLock) {
            if (this.mScanCallbackExecutor != null && this.mScanCallback != null) {
                this.mScanCallbackExecutor.execute(new java.lang.Runnable() { // from class: android.media.tv.tuner.Tuner$$ExternalSyntheticLambda3
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.media.tv.tuner.Tuner.this.lambda$onFrequenciesReport$6(jArr);
                    }
                });
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onFrequenciesReport$6(long[] jArr) {
        synchronized (this.mScanCallbackLock) {
            if (this.mScanCallback != null) {
                this.mScanCallback.onFrequenciesLongReported(jArr);
            }
        }
    }

    private void onSymbolRates(final int[] iArr) {
        synchronized (this.mScanCallbackLock) {
            if (this.mScanCallbackExecutor != null && this.mScanCallback != null) {
                this.mScanCallbackExecutor.execute(new java.lang.Runnable() { // from class: android.media.tv.tuner.Tuner$$ExternalSyntheticLambda16
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.media.tv.tuner.Tuner.this.lambda$onSymbolRates$7(iArr);
                    }
                });
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onSymbolRates$7(int[] iArr) {
        synchronized (this.mScanCallbackLock) {
            if (this.mScanCallback != null) {
                this.mScanCallback.onSymbolRatesReported(iArr);
            }
        }
    }

    private void onHierarchy(final int i) {
        synchronized (this.mScanCallbackLock) {
            if (this.mScanCallbackExecutor != null && this.mScanCallback != null) {
                this.mScanCallbackExecutor.execute(new java.lang.Runnable() { // from class: android.media.tv.tuner.Tuner$$ExternalSyntheticLambda8
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.media.tv.tuner.Tuner.this.lambda$onHierarchy$8(i);
                    }
                });
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onHierarchy$8(int i) {
        synchronized (this.mScanCallbackLock) {
            if (this.mScanCallback != null) {
                this.mScanCallback.onHierarchyReported(i);
            }
        }
    }

    private void onSignalType(final int i) {
        synchronized (this.mScanCallbackLock) {
            if (this.mScanCallbackExecutor != null && this.mScanCallback != null) {
                this.mScanCallbackExecutor.execute(new java.lang.Runnable() { // from class: android.media.tv.tuner.Tuner$$ExternalSyntheticLambda14
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.media.tv.tuner.Tuner.this.lambda$onSignalType$9(i);
                    }
                });
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onSignalType$9(int i) {
        synchronized (this.mScanCallbackLock) {
            if (this.mScanCallback != null) {
                this.mScanCallback.onSignalTypeReported(i);
            }
        }
    }

    private void onPlpIds(final int[] iArr) {
        synchronized (this.mScanCallbackLock) {
            if (this.mScanCallbackExecutor != null && this.mScanCallback != null) {
                this.mScanCallbackExecutor.execute(new java.lang.Runnable() { // from class: android.media.tv.tuner.Tuner$$ExternalSyntheticLambda18
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.media.tv.tuner.Tuner.this.lambda$onPlpIds$10(iArr);
                    }
                });
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onPlpIds$10(int[] iArr) {
        synchronized (this.mScanCallbackLock) {
            if (this.mScanCallback != null) {
                this.mScanCallback.onPlpIdsReported(iArr);
            }
        }
    }

    private void onGroupIds(final int[] iArr) {
        synchronized (this.mScanCallbackLock) {
            if (this.mScanCallbackExecutor != null && this.mScanCallback != null) {
                this.mScanCallbackExecutor.execute(new java.lang.Runnable() { // from class: android.media.tv.tuner.Tuner$$ExternalSyntheticLambda7
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.media.tv.tuner.Tuner.this.lambda$onGroupIds$11(iArr);
                    }
                });
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onGroupIds$11(int[] iArr) {
        synchronized (this.mScanCallbackLock) {
            if (this.mScanCallback != null) {
                this.mScanCallback.onGroupIdsReported(iArr);
            }
        }
    }

    private void onInputStreamIds(final int[] iArr) {
        synchronized (this.mScanCallbackLock) {
            if (this.mScanCallbackExecutor != null && this.mScanCallback != null) {
                this.mScanCallbackExecutor.execute(new java.lang.Runnable() { // from class: android.media.tv.tuner.Tuner$$ExternalSyntheticLambda2
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.media.tv.tuner.Tuner.this.lambda$onInputStreamIds$12(iArr);
                    }
                });
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onInputStreamIds$12(int[] iArr) {
        synchronized (this.mScanCallbackLock) {
            if (this.mScanCallback != null) {
                this.mScanCallback.onInputStreamIdsReported(iArr);
            }
        }
    }

    private void onDvbsStandard(final int i) {
        synchronized (this.mScanCallbackLock) {
            if (this.mScanCallbackExecutor != null && this.mScanCallback != null) {
                this.mScanCallbackExecutor.execute(new java.lang.Runnable() { // from class: android.media.tv.tuner.Tuner$$ExternalSyntheticLambda9
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.media.tv.tuner.Tuner.this.lambda$onDvbsStandard$13(i);
                    }
                });
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onDvbsStandard$13(int i) {
        synchronized (this.mScanCallbackLock) {
            if (this.mScanCallback != null) {
                this.mScanCallback.onDvbsStandardReported(i);
            }
        }
    }

    private void onDvbtStandard(final int i) {
        synchronized (this.mScanCallbackLock) {
            if (this.mScanCallbackExecutor != null && this.mScanCallback != null) {
                this.mScanCallbackExecutor.execute(new java.lang.Runnable() { // from class: android.media.tv.tuner.Tuner$$ExternalSyntheticLambda11
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.media.tv.tuner.Tuner.this.lambda$onDvbtStandard$14(i);
                    }
                });
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onDvbtStandard$14(int i) {
        synchronized (this.mScanCallbackLock) {
            if (this.mScanCallback != null) {
                this.mScanCallback.onDvbtStandardReported(i);
            }
        }
    }

    private void onAnalogSifStandard(final int i) {
        synchronized (this.mScanCallbackLock) {
            if (this.mScanCallbackExecutor != null && this.mScanCallback != null) {
                this.mScanCallbackExecutor.execute(new java.lang.Runnable() { // from class: android.media.tv.tuner.Tuner$$ExternalSyntheticLambda15
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.media.tv.tuner.Tuner.this.lambda$onAnalogSifStandard$15(i);
                    }
                });
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onAnalogSifStandard$15(int i) {
        synchronized (this.mScanCallbackLock) {
            if (this.mScanCallback != null) {
                this.mScanCallback.onAnalogSifStandardReported(i);
            }
        }
    }

    private void onAtsc3PlpInfos(final android.media.tv.tuner.frontend.Atsc3PlpInfo[] atsc3PlpInfoArr) {
        synchronized (this.mScanCallbackLock) {
            if (this.mScanCallbackExecutor != null && this.mScanCallback != null) {
                this.mScanCallbackExecutor.execute(new java.lang.Runnable() { // from class: android.media.tv.tuner.Tuner$$ExternalSyntheticLambda17
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.media.tv.tuner.Tuner.this.lambda$onAtsc3PlpInfos$16(atsc3PlpInfoArr);
                    }
                });
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onAtsc3PlpInfos$16(android.media.tv.tuner.frontend.Atsc3PlpInfo[] atsc3PlpInfoArr) {
        synchronized (this.mScanCallbackLock) {
            if (this.mScanCallback != null) {
                this.mScanCallback.onAtsc3PlpInfosReported(atsc3PlpInfoArr);
            }
        }
    }

    private void onModulationReported(final int i) {
        synchronized (this.mScanCallbackLock) {
            if (this.mScanCallbackExecutor != null && this.mScanCallback != null) {
                this.mScanCallbackExecutor.execute(new java.lang.Runnable() { // from class: android.media.tv.tuner.Tuner$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.media.tv.tuner.Tuner.this.lambda$onModulationReported$17(i);
                    }
                });
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onModulationReported$17(int i) {
        synchronized (this.mScanCallbackLock) {
            if (this.mScanCallback != null) {
                this.mScanCallback.onModulationReported(i);
            }
        }
    }

    private void onPriorityReported(final boolean z) {
        synchronized (this.mScanCallbackLock) {
            if (this.mScanCallbackExecutor != null && this.mScanCallback != null) {
                this.mScanCallbackExecutor.execute(new java.lang.Runnable() { // from class: android.media.tv.tuner.Tuner$$ExternalSyntheticLambda10
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.media.tv.tuner.Tuner.this.lambda$onPriorityReported$18(z);
                    }
                });
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onPriorityReported$18(boolean z) {
        synchronized (this.mScanCallbackLock) {
            if (this.mScanCallback != null) {
                this.mScanCallback.onPriorityReported(z);
            }
        }
    }

    private void onDvbcAnnexReported(final int i) {
        synchronized (this.mScanCallbackLock) {
            if (this.mScanCallbackExecutor != null && this.mScanCallback != null) {
                this.mScanCallbackExecutor.execute(new java.lang.Runnable() { // from class: android.media.tv.tuner.Tuner$$ExternalSyntheticLambda5
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.media.tv.tuner.Tuner.this.lambda$onDvbcAnnexReported$19(i);
                    }
                });
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onDvbcAnnexReported$19(int i) {
        synchronized (this.mScanCallbackLock) {
            if (this.mScanCallback != null) {
                this.mScanCallback.onDvbcAnnexReported(i);
            }
        }
    }

    private void onDvbtCellIdsReported(final int[] iArr) {
        synchronized (this.mScanCallbackLock) {
            if (this.mScanCallbackExecutor != null && this.mScanCallback != null) {
                this.mScanCallbackExecutor.execute(new java.lang.Runnable() { // from class: android.media.tv.tuner.Tuner$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.media.tv.tuner.Tuner.this.lambda$onDvbtCellIdsReported$20(iArr);
                    }
                });
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onDvbtCellIdsReported$20(int[] iArr) {
        synchronized (this.mScanCallbackLock) {
            if (this.mScanCallback != null) {
                this.mScanCallback.onDvbtCellIdsReported(iArr);
            }
        }
    }

    public android.media.tv.tuner.filter.Filter openFilter(int i, int i2, long j, java.util.concurrent.Executor executor, android.media.tv.tuner.filter.FilterCallback filterCallback) {
        this.mDemuxLock.lock();
        try {
            android.media.tv.tuner.TunerVersionChecker.getMajorVersion(sTunerVersion);
            if (sTunerVersion >= 196608 && configureDemuxInternal(new android.media.tv.tuner.DemuxInfo(i), false) != 0) {
                android.util.Log.e(TAG, "openFilter called for unsupported mainType: " + i);
                return null;
            }
            if (!checkResource(1, this.mDemuxLock)) {
                return null;
            }
            android.media.tv.tuner.filter.Filter nativeOpenFilter = nativeOpenFilter(i, android.media.tv.tuner.TunerUtils.getFilterSubtype(i, i2), j);
            if (nativeOpenFilter != null) {
                nativeOpenFilter.setType(i, i2);
                nativeOpenFilter.setCallback(filterCallback, executor);
                if (this.mHandler == null) {
                    this.mHandler = createEventHandler();
                }
                synchronized (this.mFilters) {
                    this.mFilters.add(new java.lang.ref.WeakReference<>(nativeOpenFilter));
                    if (this.mFilters.size() > 256) {
                        java.util.Iterator<java.lang.ref.WeakReference<android.media.tv.tuner.filter.Filter>> it = this.mFilters.iterator();
                        while (it.hasNext()) {
                            if (it.next().get() == null) {
                                it.remove();
                            }
                        }
                    }
                }
            }
            return nativeOpenFilter;
        } finally {
            this.mDemuxLock.unlock();
        }
    }

    public android.media.tv.tuner.Lnb openLnb(java.util.concurrent.Executor executor, android.media.tv.tuner.LnbCallback lnbCallback) {
        this.mLnbLock.lock();
        try {
            java.util.Objects.requireNonNull(executor, "executor must not be null");
            java.util.Objects.requireNonNull(lnbCallback, "LnbCallback must not be null");
            if (this.mLnb != null) {
                this.mLnb.setCallbackAndOwner(this, executor, lnbCallback);
                return this.mLnb;
            }
            if (!checkResource(3, this.mLnbLock) || this.mLnb == null) {
                this.mLnbLock.unlock();
                return null;
            }
            this.mLnb.setCallbackAndOwner(this, executor, lnbCallback);
            if (this.mFrontendHandle != null && this.mFrontend != null) {
                setLnb(this.mLnb);
            }
            return this.mLnb;
        } finally {
            this.mLnbLock.unlock();
        }
    }

    public android.media.tv.tuner.Lnb openLnbByName(java.lang.String str, java.util.concurrent.Executor executor, android.media.tv.tuner.LnbCallback lnbCallback) {
        acquireTRMSLock("openLnbByName");
        this.mLnbLock.lock();
        try {
            java.util.Objects.requireNonNull(str, "LNB name must not be null");
            java.util.Objects.requireNonNull(executor, "executor must not be null");
            java.util.Objects.requireNonNull(lnbCallback, "LnbCallback must not be null");
            android.media.tv.tuner.Lnb nativeOpenLnbByName = nativeOpenLnbByName(str);
            if (nativeOpenLnbByName != null) {
                if (this.mLnb != null) {
                    this.mLnb.closeInternal();
                    this.mLnbHandle = null;
                }
                this.mLnb = nativeOpenLnbByName;
                this.mLnb.setCallbackAndOwner(this, executor, lnbCallback);
                if (this.mFrontendHandle != null && this.mFrontend != null) {
                    setLnb(this.mLnb);
                }
            }
            return this.mLnb;
        } finally {
            releaseTRMSLock();
            this.mLnbLock.unlock();
        }
    }

    private boolean requestLnb() {
        int[] iArr = new int[1];
        android.media.tv.tunerresourcemanager.TunerLnbRequest tunerLnbRequest = new android.media.tv.tunerresourcemanager.TunerLnbRequest();
        tunerLnbRequest.clientId = this.mClientId;
        boolean requestLnb = this.mTunerResourceManager.requestLnb(tunerLnbRequest, iArr);
        if (requestLnb) {
            this.mLnbHandle = java.lang.Integer.valueOf(iArr[0]);
            this.mLnb = nativeOpenLnbByHandle(this.mLnbHandle.intValue());
        }
        return requestLnb;
    }

    public android.media.tv.tuner.filter.TimeFilter openTimeFilter() {
        this.mDemuxLock.lock();
        try {
            if (checkResource(1, this.mDemuxLock)) {
                return nativeOpenTimeFilter();
            }
            this.mDemuxLock.unlock();
            return null;
        } finally {
            this.mDemuxLock.unlock();
        }
    }

    public android.media.tv.tuner.Descrambler openDescrambler() {
        acquireTRMSLock("openDescrambler()");
        this.mDemuxLock.lock();
        try {
            if (checkResource(1, null)) {
                return requestDescrambler();
            }
            return null;
        } finally {
            releaseTRMSLock();
            this.mDemuxLock.unlock();
        }
    }

    public android.media.tv.tuner.dvr.DvrRecorder openDvrRecorder(long j, java.util.concurrent.Executor executor, android.media.tv.tuner.dvr.OnRecordStatusChangedListener onRecordStatusChangedListener) {
        this.mDemuxLock.lock();
        try {
            java.util.Objects.requireNonNull(executor, "executor must not be null");
            java.util.Objects.requireNonNull(onRecordStatusChangedListener, "OnRecordStatusChangedListener must not be null");
            if (checkResource(1, this.mDemuxLock)) {
                android.media.tv.tuner.dvr.DvrRecorder nativeOpenDvrRecorder = nativeOpenDvrRecorder(j);
                nativeOpenDvrRecorder.setListener(executor, onRecordStatusChangedListener);
                return nativeOpenDvrRecorder;
            }
            this.mDemuxLock.unlock();
            return null;
        } finally {
            this.mDemuxLock.unlock();
        }
    }

    public android.media.tv.tuner.dvr.DvrPlayback openDvrPlayback(long j, java.util.concurrent.Executor executor, android.media.tv.tuner.dvr.OnPlaybackStatusChangedListener onPlaybackStatusChangedListener) {
        this.mDemuxLock.lock();
        try {
            java.util.Objects.requireNonNull(executor, "executor must not be null");
            java.util.Objects.requireNonNull(onPlaybackStatusChangedListener, "OnPlaybackStatusChangedListener must not be null");
            if (checkResource(1, this.mDemuxLock)) {
                android.media.tv.tuner.dvr.DvrPlayback nativeOpenDvrPlayback = nativeOpenDvrPlayback(j);
                nativeOpenDvrPlayback.setListener(executor, onPlaybackStatusChangedListener);
                return nativeOpenDvrPlayback;
            }
            this.mDemuxLock.unlock();
            return null;
        } finally {
            this.mDemuxLock.unlock();
        }
    }

    public int applyFrontend(android.media.tv.tuner.frontend.FrontendInfo frontendInfo) {
        java.util.Objects.requireNonNull(frontendInfo, "desiredFrontendInfo must not be null");
        this.mFrontendLock.lock();
        try {
            if (this.mFeOwnerTuner != null) {
                android.util.Log.e(TAG, "Operation connot be done by sharee of tuner");
                return 3;
            }
            if (this.mFrontendHandle != null) {
                android.util.Log.e(TAG, "A frontend has been opened before");
                return 3;
            }
            this.mFrontendType = frontendInfo.getType();
            this.mDesiredFrontendId = java.lang.Integer.valueOf(frontendInfo.getId());
            if (DEBUG) {
                android.util.Log.d(TAG, "Applying frontend with type " + this.mFrontendType + ", id " + this.mDesiredFrontendId);
            }
            if (checkResource(0, this.mFrontendLock)) {
                return 0;
            }
            this.mFrontendLock.unlock();
            return 1;
        } finally {
            this.mFrontendLock.unlock();
        }
    }

    public static android.media.tv.tuner.filter.SharedFilter openSharedFilter(android.content.Context context, java.lang.String str, java.util.concurrent.Executor executor, android.media.tv.tuner.filter.SharedFilterCallback sharedFilterCallback) {
        java.util.Objects.requireNonNull(str, "sharedFilterToken must not be null");
        java.util.Objects.requireNonNull(executor, "executor must not be null");
        java.util.Objects.requireNonNull(sharedFilterCallback, "SharedFilterCallback must not be null");
        if (context.checkCallingOrSelfPermission(android.Manifest.permission.ACCESS_TV_SHARED_FILTER) != 0) {
            throw new java.lang.SecurityException("Caller must have ACCESS_TV_SHAREDFILTER permission.");
        }
        android.media.tv.tuner.filter.SharedFilter nativeOpenSharedFilter = nativeOpenSharedFilter(str);
        if (nativeOpenSharedFilter != null) {
            nativeOpenSharedFilter.setCallback(sharedFilterCallback, executor);
        }
        return nativeOpenSharedFilter;
    }

    public int configureDemux(android.media.tv.tuner.DemuxInfo demuxInfo) {
        int configureDemuxInternal;
        android.media.tv.tuner.TunerVersionChecker.getMajorVersion(sTunerVersion);
        if (sTunerVersion < 196608) {
            android.util.Log.e(TAG, "configureDemux() is not supported for tuner version:" + android.media.tv.tuner.TunerVersionChecker.getMajorVersion(sTunerVersion) + android.media.MediaMetrics.SEPARATOR + android.media.tv.tuner.TunerVersionChecker.getMinorVersion(sTunerVersion) + android.media.MediaMetrics.SEPARATOR);
            return 1;
        }
        synchronized (this.mDemuxLock) {
            configureDemuxInternal = configureDemuxInternal(demuxInfo, true);
        }
        return configureDemuxInternal;
    }

    private int configureDemuxInternal(android.media.tv.tuner.DemuxInfo demuxInfo, boolean z) {
        boolean z2;
        android.media.tv.tuner.DemuxInfo nativeGetDemuxInfo;
        if (demuxInfo == null) {
            if (this.mDemuxHandle != null) {
                releaseFilters();
                releaseDemux();
            }
            return 0;
        }
        int filterTypes = demuxInfo.getFilterTypes();
        if ((this.mDesiredDemuxInfo.getFilterTypes() & filterTypes) == filterTypes) {
            if (z) {
                this.mDesiredDemuxInfo.setFilterTypes(filterTypes);
            }
            return 0;
        }
        android.media.tv.tuner.DemuxCapabilities nativeGetDemuxCapabilities = nativeGetDemuxCapabilities();
        if (nativeGetDemuxCapabilities == null) {
            android.util.Log.e(TAG, "configureDemuxInternal:failed to get DemuxCapabilities");
            return 1;
        }
        int[] filterTypeCapabilityList = nativeGetDemuxCapabilities.getFilterTypeCapabilityList();
        if (filterTypeCapabilityList.length <= 0) {
            android.util.Log.e(TAG, "configureDemuxInternal: getFilterTypeCapabilityList() returned an empty array");
            return 1;
        }
        int length = filterTypeCapabilityList.length;
        int i = 0;
        while (true) {
            if (i >= length) {
                z2 = false;
                break;
            }
            if ((filterTypeCapabilityList[i] & filterTypes) != filterTypes) {
                i++;
            } else {
                z2 = true;
                break;
            }
        }
        if (!z2) {
            android.util.Log.e(TAG, "configureDemuxInternal: requested caps:" + filterTypes + " is not supported by the system");
            return 1;
        }
        if (this.mDemuxHandle != null && filterTypes != 0 && (nativeGetDemuxInfo = nativeGetDemuxInfo(this.mDemuxHandle.intValue())) != null && (nativeGetDemuxInfo.getFilterTypes() & filterTypes) != filterTypes) {
            releaseFilters();
            releaseDemux();
        }
        this.mDesiredDemuxInfo.setFilterTypes(filterTypes);
        return 0;
    }

    private boolean requestDemux() {
        int[] iArr = new int[1];
        android.media.tv.tunerresourcemanager.TunerDemuxRequest tunerDemuxRequest = new android.media.tv.tunerresourcemanager.TunerDemuxRequest();
        tunerDemuxRequest.clientId = this.mClientId;
        tunerDemuxRequest.desiredFilterTypes = this.mDesiredDemuxInfo.getFilterTypes();
        boolean requestDemux = this.mTunerResourceManager.requestDemux(tunerDemuxRequest, iArr);
        if (requestDemux) {
            this.mDemuxHandle = java.lang.Integer.valueOf(iArr[0]);
            nativeOpenDemuxByhandle(this.mDemuxHandle.intValue());
        }
        return requestDemux;
    }

    private android.media.tv.tuner.Descrambler requestDescrambler() {
        int[] iArr = new int[1];
        android.media.tv.tunerresourcemanager.TunerDescramblerRequest tunerDescramblerRequest = new android.media.tv.tunerresourcemanager.TunerDescramblerRequest();
        tunerDescramblerRequest.clientId = this.mClientId;
        if (!this.mTunerResourceManager.requestDescrambler(tunerDescramblerRequest, iArr)) {
            return null;
        }
        int i = iArr[0];
        android.media.tv.tuner.Descrambler nativeOpenDescramblerByHandle = nativeOpenDescramblerByHandle(i);
        if (nativeOpenDescramblerByHandle != null) {
            synchronized (this.mDescramblers) {
                this.mDescramblers.put(java.lang.Integer.valueOf(i), new java.lang.ref.WeakReference<>(nativeOpenDescramblerByHandle));
            }
        } else {
            this.mTunerResourceManager.releaseDescrambler(i, this.mClientId);
        }
        return nativeOpenDescramblerByHandle;
    }

    private boolean requestFrontendCiCam(int i) {
        int[] iArr = new int[1];
        android.media.tv.tunerresourcemanager.TunerCiCamRequest tunerCiCamRequest = new android.media.tv.tunerresourcemanager.TunerCiCamRequest();
        tunerCiCamRequest.clientId = this.mClientId;
        tunerCiCamRequest.ciCamId = i;
        boolean requestCiCam = this.mTunerResourceManager.requestCiCam(tunerCiCamRequest, iArr);
        if (requestCiCam) {
            this.mFrontendCiCamHandle = java.lang.Integer.valueOf(iArr[0]);
            this.mFrontendCiCamId = java.lang.Integer.valueOf(i);
        }
        return requestCiCam;
    }

    private boolean checkResource(int i, java.util.concurrent.locks.ReentrantLock reentrantLock) {
        switch (i) {
            case 0:
                if (this.mFrontendHandle != null || requestResource(i, reentrantLock)) {
                }
                break;
            case 1:
                if (this.mDemuxHandle != null || requestResource(i, reentrantLock)) {
                }
                break;
            case 3:
                if (this.mLnb != null || requestResource(i, reentrantLock)) {
                }
                break;
            case 5:
                if (this.mFrontendCiCamHandle != null || requestResource(i, reentrantLock)) {
                }
                break;
        }
        return false;
    }

    private boolean requestResource(int i, java.util.concurrent.locks.ReentrantLock reentrantLock) {
        boolean z = reentrantLock != null;
        if (z) {
            if (!reentrantLock.isLocked()) {
                throw new java.lang.IllegalStateException("local lock must be locked beforehand");
            }
            reentrantLock.unlock();
        }
        if (z) {
            acquireTRMSLock("requestResource:" + i);
        }
        if (z) {
            try {
                reentrantLock.lock();
            } finally {
                if (z) {
                    releaseTRMSLock();
                }
            }
        }
        switch (i) {
            case 0:
                boolean requestFrontend = requestFrontend();
                if (z) {
                    releaseTRMSLock();
                }
                return requestFrontend;
            case 1:
                boolean requestDemux = requestDemux();
                if (z) {
                    releaseTRMSLock();
                }
                return requestDemux;
            case 2:
            case 4:
            default:
                if (z) {
                    releaseTRMSLock();
                }
                return false;
            case 3:
                boolean requestLnb = requestLnb();
                if (z) {
                    releaseTRMSLock();
                }
                return requestLnb;
            case 5:
                return requestFrontendCiCam(this.mRequestedCiCamId);
        }
    }

    void releaseLnb() {
        this.mLnbLock.lock();
        try {
            if (this.mLnbHandle != null) {
                if (DEBUG) {
                    android.util.Log.d(TAG, "releasing Lnb");
                }
                this.mTunerResourceManager.releaseLnb(this.mLnbHandle.intValue(), this.mClientId);
                this.mLnbHandle = null;
            } else if (DEBUG) {
                android.util.Log.d(TAG, "NOT releasing Lnb because mLnbHandle is null");
            }
            this.mLnb = null;
        } finally {
            this.mLnbLock.unlock();
        }
    }

    public int getClientId() {
        return this.mClientId;
    }

    android.media.tv.tunerresourcemanager.TunerResourceManager getTunerResourceManager() {
        return this.mTunerResourceManager;
    }

    private void acquireTRMSLock(java.lang.String str) {
        if (DEBUG) {
            android.util.Log.d(TAG, "ATTEMPT:acquireLock() in " + str + "for clientId:" + this.mClientId);
        }
        if (!this.mTunerResourceManager.acquireLock(this.mClientId)) {
            android.util.Log.e(TAG, "FAILED:acquireLock() in " + str + " for clientId:" + this.mClientId + " - this can cause deadlock between Tuner API calls and onReclaimResources()");
        }
    }

    private void releaseTRMSLock() {
        this.mTunerResourceManager.releaseLock(this.mClientId);
    }
}
