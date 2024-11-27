package com.android.server.tv.tunerresourcemanager;

/* loaded from: classes2.dex */
public class TunerResourceManagerService extends com.android.server.SystemService implements android.os.IBinder.DeathRecipient {
    public static final int INVALID_CLIENT_ID = -1;
    private static final int INVALID_FE_COUNT = -1;
    private static final long INVALID_THREAD_ID = -1;
    private static final int MAX_CLIENT_PRIORITY = 1000;
    private static final long TRMS_LOCK_TIMEOUT = 500;
    private android.app.ActivityManager mActivityManager;
    private java.util.Map<java.lang.Integer, com.android.server.tv.tunerresourcemanager.CasResource> mCasResources;
    private java.util.Map<java.lang.Integer, com.android.server.tv.tunerresourcemanager.CiCamResource> mCiCamResources;
    private java.util.Map<java.lang.Integer, com.android.server.tv.tunerresourcemanager.ClientProfile> mClientProfiles;
    private java.util.Map<java.lang.Integer, com.android.server.tv.tunerresourcemanager.DemuxResource> mDemuxResources;
    private android.util.SparseIntArray mFrontendExistingNums;
    private android.util.SparseIntArray mFrontendExistingNumsBackup;
    private android.util.SparseIntArray mFrontendMaxUsableNums;
    private android.util.SparseIntArray mFrontendMaxUsableNumsBackup;
    private java.util.Map<java.lang.Integer, com.android.server.tv.tunerresourcemanager.FrontendResource> mFrontendResources;
    private java.util.Map<java.lang.Integer, com.android.server.tv.tunerresourcemanager.FrontendResource> mFrontendResourcesBackup;
    private android.util.SparseIntArray mFrontendUsedNums;
    private android.util.SparseIntArray mFrontendUsedNumsBackup;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private java.util.Map<java.lang.Integer, com.android.server.tv.tunerresourcemanager.TunerResourceManagerService.ResourcesReclaimListenerRecord> mListeners;
    private java.util.Map<java.lang.Integer, com.android.server.tv.tunerresourcemanager.LnbResource> mLnbResources;
    private final java.lang.Object mLock;
    private final java.util.concurrent.locks.ReentrantLock mLockForTRMSLock;
    private android.media.IResourceManagerService mMediaResourceManager;
    private int mNextUnusedClientId;
    private com.android.server.tv.tunerresourcemanager.UseCasePriorityHints mPriorityCongfig;
    private int mResourceRequestCount;
    private int mTunerApiLockHolder;
    private long mTunerApiLockHolderThreadId;
    private int mTunerApiLockNestedCount;
    private final java.util.concurrent.locks.Condition mTunerApiLockReleasedCV;
    private android.media.tv.TvInputManager mTvInputManager;
    private static final java.lang.String TAG = "TunerResourceManagerService";
    private static final boolean DEBUG = android.util.Log.isLoggable(TAG, 3);

    public TunerResourceManagerService(@android.annotation.Nullable android.content.Context context) {
        super(context);
        this.mClientProfiles = new java.util.HashMap();
        this.mNextUnusedClientId = 0;
        this.mFrontendResources = new java.util.HashMap();
        this.mFrontendMaxUsableNums = new android.util.SparseIntArray();
        this.mFrontendUsedNums = new android.util.SparseIntArray();
        this.mFrontendExistingNums = new android.util.SparseIntArray();
        this.mFrontendResourcesBackup = new java.util.HashMap();
        this.mFrontendMaxUsableNumsBackup = new android.util.SparseIntArray();
        this.mFrontendUsedNumsBackup = new android.util.SparseIntArray();
        this.mFrontendExistingNumsBackup = new android.util.SparseIntArray();
        this.mDemuxResources = new java.util.HashMap();
        this.mLnbResources = new java.util.HashMap();
        this.mCasResources = new java.util.HashMap();
        this.mCiCamResources = new java.util.HashMap();
        this.mListeners = new java.util.HashMap();
        this.mPriorityCongfig = new com.android.server.tv.tunerresourcemanager.UseCasePriorityHints();
        this.mResourceRequestCount = 0;
        this.mLock = new java.lang.Object();
        this.mLockForTRMSLock = new java.util.concurrent.locks.ReentrantLock();
        this.mTunerApiLockReleasedCV = this.mLockForTRMSLock.newCondition();
        this.mTunerApiLockHolder = -1;
        this.mTunerApiLockHolderThreadId = -1L;
        this.mTunerApiLockNestedCount = 0;
    }

    @Override // com.android.server.SystemService
    public void onStart() {
        onStart(false);
    }

    @com.android.internal.annotations.VisibleForTesting
    protected void onStart(boolean z) {
        if (!z) {
            publishBinderService("tv_tuner_resource_mgr", new com.android.server.tv.tunerresourcemanager.TunerResourceManagerService.BinderService());
        }
        this.mTvInputManager = (android.media.tv.TvInputManager) getContext().getSystemService("tv_input");
        this.mActivityManager = (android.app.ActivityManager) getContext().getSystemService(com.android.server.am.HostingRecord.HOSTING_TYPE_ACTIVITY);
        this.mPriorityCongfig.parse();
        if (!z && !android.os.SystemProperties.getBoolean("ro.tuner.lazyhal", false)) {
            android.os.SystemProperties.set("tuner.server.enable", "true");
        }
        if (this.mMediaResourceManager == null) {
            android.os.IBinder binderService = getBinderService("media.resource_manager");
            if (binderService == null) {
                android.util.Slog.w(TAG, "Resource Manager Service not available.");
                return;
            }
            try {
                binderService.linkToDeath(this, 0);
                this.mMediaResourceManager = android.media.IResourceManagerService.Stub.asInterface(binderService);
            } catch (android.os.RemoteException e) {
                android.util.Slog.w(TAG, "Could not link to death of native resource manager service.");
            }
        }
    }

    private final class BinderService extends android.media.tv.tunerresourcemanager.ITunerResourceManager.Stub {
        private BinderService() {
        }

        public void registerClientProfile(@android.annotation.NonNull android.media.tv.tunerresourcemanager.ResourceClientProfile resourceClientProfile, @android.annotation.NonNull android.media.tv.tunerresourcemanager.IResourcesReclaimListener iResourcesReclaimListener, @android.annotation.NonNull int[] iArr) throws android.os.RemoteException {
            com.android.server.tv.tunerresourcemanager.TunerResourceManagerService.this.enforceTrmAccessPermission("registerClientProfile");
            com.android.server.tv.tunerresourcemanager.TunerResourceManagerService.this.enforceTunerAccessPermission("registerClientProfile");
            if (resourceClientProfile == null) {
                throw new android.os.RemoteException("ResourceClientProfile can't be null");
            }
            if (iArr == null) {
                throw new android.os.RemoteException("clientId can't be null!");
            }
            if (iResourcesReclaimListener == null) {
                throw new android.os.RemoteException("IResourcesReclaimListener can't be null!");
            }
            if (!com.android.server.tv.tunerresourcemanager.TunerResourceManagerService.this.mPriorityCongfig.isDefinedUseCase(resourceClientProfile.useCase)) {
                throw new android.os.RemoteException("Use undefined client use case:" + resourceClientProfile.useCase);
            }
            synchronized (com.android.server.tv.tunerresourcemanager.TunerResourceManagerService.this.mLock) {
                com.android.server.tv.tunerresourcemanager.TunerResourceManagerService.this.registerClientProfileInternal(resourceClientProfile, iResourcesReclaimListener, iArr);
            }
        }

        public void unregisterClientProfile(int i) throws android.os.RemoteException {
            com.android.server.tv.tunerresourcemanager.TunerResourceManagerService.this.enforceTrmAccessPermission("unregisterClientProfile");
            synchronized (com.android.server.tv.tunerresourcemanager.TunerResourceManagerService.this.mLock) {
                try {
                    if (!com.android.server.tv.tunerresourcemanager.TunerResourceManagerService.this.checkClientExists(i)) {
                        android.util.Slog.e(com.android.server.tv.tunerresourcemanager.TunerResourceManagerService.TAG, "Unregistering non exists client:" + i);
                        return;
                    }
                    com.android.server.tv.tunerresourcemanager.TunerResourceManagerService.this.unregisterClientProfileInternal(i);
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        public boolean updateClientPriority(int i, int i2, int i3) {
            boolean updateClientPriorityInternal;
            com.android.server.tv.tunerresourcemanager.TunerResourceManagerService.this.enforceTrmAccessPermission("updateClientPriority");
            synchronized (com.android.server.tv.tunerresourcemanager.TunerResourceManagerService.this.mLock) {
                updateClientPriorityInternal = com.android.server.tv.tunerresourcemanager.TunerResourceManagerService.this.updateClientPriorityInternal(i, i2, i3);
            }
            return updateClientPriorityInternal;
        }

        public boolean hasUnusedFrontend(int i) {
            boolean hasUnusedFrontendInternal;
            com.android.server.tv.tunerresourcemanager.TunerResourceManagerService.this.enforceTrmAccessPermission("hasUnusedFrontend");
            synchronized (com.android.server.tv.tunerresourcemanager.TunerResourceManagerService.this.mLock) {
                hasUnusedFrontendInternal = com.android.server.tv.tunerresourcemanager.TunerResourceManagerService.this.hasUnusedFrontendInternal(i);
            }
            return hasUnusedFrontendInternal;
        }

        public boolean isLowestPriority(int i, int i2) throws android.os.RemoteException {
            boolean isLowestPriorityInternal;
            com.android.server.tv.tunerresourcemanager.TunerResourceManagerService.this.enforceTrmAccessPermission("isLowestPriority");
            synchronized (com.android.server.tv.tunerresourcemanager.TunerResourceManagerService.this.mLock) {
                try {
                    if (!com.android.server.tv.tunerresourcemanager.TunerResourceManagerService.this.checkClientExists(i)) {
                        throw new android.os.RemoteException("isLowestPriority called from unregistered client: " + i);
                    }
                    isLowestPriorityInternal = com.android.server.tv.tunerresourcemanager.TunerResourceManagerService.this.isLowestPriorityInternal(i, i2);
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
            return isLowestPriorityInternal;
        }

        public void setFrontendInfoList(@android.annotation.NonNull android.media.tv.tunerresourcemanager.TunerFrontendInfo[] tunerFrontendInfoArr) throws android.os.RemoteException {
            com.android.server.tv.tunerresourcemanager.TunerResourceManagerService.this.enforceTrmAccessPermission("setFrontendInfoList");
            if (tunerFrontendInfoArr == null) {
                throw new android.os.RemoteException("TunerFrontendInfo can't be null");
            }
            synchronized (com.android.server.tv.tunerresourcemanager.TunerResourceManagerService.this.mLock) {
                com.android.server.tv.tunerresourcemanager.TunerResourceManagerService.this.setFrontendInfoListInternal(tunerFrontendInfoArr);
            }
        }

        public void setDemuxInfoList(@android.annotation.NonNull android.media.tv.tunerresourcemanager.TunerDemuxInfo[] tunerDemuxInfoArr) throws android.os.RemoteException {
            com.android.server.tv.tunerresourcemanager.TunerResourceManagerService.this.enforceTrmAccessPermission("setDemuxInfoList");
            if (tunerDemuxInfoArr == null) {
                throw new android.os.RemoteException("TunerDemuxInfo can't be null");
            }
            synchronized (com.android.server.tv.tunerresourcemanager.TunerResourceManagerService.this.mLock) {
                com.android.server.tv.tunerresourcemanager.TunerResourceManagerService.this.setDemuxInfoListInternal(tunerDemuxInfoArr);
            }
        }

        public void updateCasInfo(int i, int i2) {
            com.android.server.tv.tunerresourcemanager.TunerResourceManagerService.this.enforceTrmAccessPermission("updateCasInfo");
            synchronized (com.android.server.tv.tunerresourcemanager.TunerResourceManagerService.this.mLock) {
                com.android.server.tv.tunerresourcemanager.TunerResourceManagerService.this.updateCasInfoInternal(i, i2);
            }
        }

        public void setLnbInfoList(int[] iArr) throws android.os.RemoteException {
            com.android.server.tv.tunerresourcemanager.TunerResourceManagerService.this.enforceTrmAccessPermission("setLnbInfoList");
            if (iArr == null) {
                throw new android.os.RemoteException("Lnb handle list can't be null");
            }
            synchronized (com.android.server.tv.tunerresourcemanager.TunerResourceManagerService.this.mLock) {
                com.android.server.tv.tunerresourcemanager.TunerResourceManagerService.this.setLnbInfoListInternal(iArr);
            }
        }

        public boolean requestFrontend(@android.annotation.NonNull android.media.tv.tunerresourcemanager.TunerFrontendRequest tunerFrontendRequest, @android.annotation.NonNull int[] iArr) {
            com.android.server.tv.tunerresourcemanager.TunerResourceManagerService.this.enforceTunerAccessPermission("requestFrontend");
            com.android.server.tv.tunerresourcemanager.TunerResourceManagerService.this.enforceTrmAccessPermission("requestFrontend");
            if (iArr == null) {
                android.util.Slog.e(com.android.server.tv.tunerresourcemanager.TunerResourceManagerService.TAG, "frontendHandle can't be null");
                return false;
            }
            synchronized (com.android.server.tv.tunerresourcemanager.TunerResourceManagerService.this.mLock) {
                try {
                    if (!com.android.server.tv.tunerresourcemanager.TunerResourceManagerService.this.checkClientExists(tunerFrontendRequest.clientId)) {
                        android.util.Slog.e(com.android.server.tv.tunerresourcemanager.TunerResourceManagerService.TAG, "Request frontend from unregistered client: " + tunerFrontendRequest.clientId);
                        return false;
                    }
                    if (!com.android.server.tv.tunerresourcemanager.TunerResourceManagerService.this.getClientProfile(tunerFrontendRequest.clientId).getInUseFrontendHandles().isEmpty()) {
                        android.util.Slog.e(com.android.server.tv.tunerresourcemanager.TunerResourceManagerService.TAG, "Release frontend before requesting another one. Client id: " + tunerFrontendRequest.clientId);
                        return false;
                    }
                    return com.android.server.tv.tunerresourcemanager.TunerResourceManagerService.this.requestFrontendInternal(tunerFrontendRequest, iArr);
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        public boolean setMaxNumberOfFrontends(int i, int i2) {
            boolean maxNumberOfFrontendsInternal;
            com.android.server.tv.tunerresourcemanager.TunerResourceManagerService.this.enforceTunerAccessPermission("setMaxNumberOfFrontends");
            com.android.server.tv.tunerresourcemanager.TunerResourceManagerService.this.enforceTrmAccessPermission("setMaxNumberOfFrontends");
            if (i2 < 0) {
                android.util.Slog.w(com.android.server.tv.tunerresourcemanager.TunerResourceManagerService.TAG, "setMaxNumberOfFrontends failed with maxUsableNum:" + i2 + " frontendType:" + i);
                return false;
            }
            synchronized (com.android.server.tv.tunerresourcemanager.TunerResourceManagerService.this.mLock) {
                maxNumberOfFrontendsInternal = com.android.server.tv.tunerresourcemanager.TunerResourceManagerService.this.setMaxNumberOfFrontendsInternal(i, i2);
            }
            return maxNumberOfFrontendsInternal;
        }

        public int getMaxNumberOfFrontends(int i) {
            int maxNumberOfFrontendsInternal;
            com.android.server.tv.tunerresourcemanager.TunerResourceManagerService.this.enforceTunerAccessPermission("getMaxNumberOfFrontends");
            com.android.server.tv.tunerresourcemanager.TunerResourceManagerService.this.enforceTrmAccessPermission("getMaxNumberOfFrontends");
            synchronized (com.android.server.tv.tunerresourcemanager.TunerResourceManagerService.this.mLock) {
                maxNumberOfFrontendsInternal = com.android.server.tv.tunerresourcemanager.TunerResourceManagerService.this.getMaxNumberOfFrontendsInternal(i);
            }
            return maxNumberOfFrontendsInternal;
        }

        public void shareFrontend(int i, int i2) throws android.os.RemoteException {
            com.android.server.tv.tunerresourcemanager.TunerResourceManagerService.this.enforceTunerAccessPermission("shareFrontend");
            com.android.server.tv.tunerresourcemanager.TunerResourceManagerService.this.enforceTrmAccessPermission("shareFrontend");
            synchronized (com.android.server.tv.tunerresourcemanager.TunerResourceManagerService.this.mLock) {
                try {
                    if (!com.android.server.tv.tunerresourcemanager.TunerResourceManagerService.this.checkClientExists(i)) {
                        throw new android.os.RemoteException("Share frontend request from an unregistered client:" + i);
                    }
                    if (!com.android.server.tv.tunerresourcemanager.TunerResourceManagerService.this.checkClientExists(i2)) {
                        throw new android.os.RemoteException("Request to share frontend with an unregistered client:" + i2);
                    }
                    if (com.android.server.tv.tunerresourcemanager.TunerResourceManagerService.this.getClientProfile(i2).getInUseFrontendHandles().isEmpty()) {
                        throw new android.os.RemoteException("Request to share frontend with a client that has no frontend resources. Target client id:" + i2);
                    }
                    com.android.server.tv.tunerresourcemanager.TunerResourceManagerService.this.shareFrontendInternal(i, i2);
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        public boolean transferOwner(int i, int i2, int i3) {
            com.android.server.tv.tunerresourcemanager.TunerResourceManagerService.this.enforceTunerAccessPermission("transferOwner");
            com.android.server.tv.tunerresourcemanager.TunerResourceManagerService.this.enforceTrmAccessPermission("transferOwner");
            synchronized (com.android.server.tv.tunerresourcemanager.TunerResourceManagerService.this.mLock) {
                try {
                    if (!com.android.server.tv.tunerresourcemanager.TunerResourceManagerService.this.checkClientExists(i2)) {
                        android.util.Slog.e(com.android.server.tv.tunerresourcemanager.TunerResourceManagerService.TAG, "currentOwnerId:" + i2 + " does not exit");
                        return false;
                    }
                    if (!com.android.server.tv.tunerresourcemanager.TunerResourceManagerService.this.checkClientExists(i3)) {
                        android.util.Slog.e(com.android.server.tv.tunerresourcemanager.TunerResourceManagerService.TAG, "newOwnerId:" + i3 + " does not exit");
                        return false;
                    }
                    return com.android.server.tv.tunerresourcemanager.TunerResourceManagerService.this.transferOwnerInternal(i, i2, i3);
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        public boolean requestDemux(@android.annotation.NonNull android.media.tv.tunerresourcemanager.TunerDemuxRequest tunerDemuxRequest, @android.annotation.NonNull int[] iArr) throws android.os.RemoteException {
            boolean requestDemuxInternal;
            com.android.server.tv.tunerresourcemanager.TunerResourceManagerService.this.enforceTunerAccessPermission("requestDemux");
            com.android.server.tv.tunerresourcemanager.TunerResourceManagerService.this.enforceTrmAccessPermission("requestDemux");
            if (iArr == null) {
                throw new android.os.RemoteException("demuxHandle can't be null");
            }
            synchronized (com.android.server.tv.tunerresourcemanager.TunerResourceManagerService.this.mLock) {
                try {
                    if (!com.android.server.tv.tunerresourcemanager.TunerResourceManagerService.this.checkClientExists(tunerDemuxRequest.clientId)) {
                        throw new android.os.RemoteException("Request demux from unregistered client:" + tunerDemuxRequest.clientId);
                    }
                    requestDemuxInternal = com.android.server.tv.tunerresourcemanager.TunerResourceManagerService.this.requestDemuxInternal(tunerDemuxRequest, iArr);
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
            return requestDemuxInternal;
        }

        public boolean requestDescrambler(@android.annotation.NonNull android.media.tv.tunerresourcemanager.TunerDescramblerRequest tunerDescramblerRequest, @android.annotation.NonNull int[] iArr) throws android.os.RemoteException {
            boolean requestDescramblerInternal;
            com.android.server.tv.tunerresourcemanager.TunerResourceManagerService.this.enforceDescramblerAccessPermission("requestDescrambler");
            com.android.server.tv.tunerresourcemanager.TunerResourceManagerService.this.enforceTrmAccessPermission("requestDescrambler");
            if (iArr == null) {
                throw new android.os.RemoteException("descramblerHandle can't be null");
            }
            synchronized (com.android.server.tv.tunerresourcemanager.TunerResourceManagerService.this.mLock) {
                try {
                    if (!com.android.server.tv.tunerresourcemanager.TunerResourceManagerService.this.checkClientExists(tunerDescramblerRequest.clientId)) {
                        throw new android.os.RemoteException("Request descrambler from unregistered client:" + tunerDescramblerRequest.clientId);
                    }
                    requestDescramblerInternal = com.android.server.tv.tunerresourcemanager.TunerResourceManagerService.this.requestDescramblerInternal(tunerDescramblerRequest, iArr);
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
            return requestDescramblerInternal;
        }

        public boolean requestCasSession(@android.annotation.NonNull android.media.tv.tunerresourcemanager.CasSessionRequest casSessionRequest, @android.annotation.NonNull int[] iArr) throws android.os.RemoteException {
            boolean requestCasSessionInternal;
            com.android.server.tv.tunerresourcemanager.TunerResourceManagerService.this.enforceTrmAccessPermission("requestCasSession");
            if (iArr == null) {
                throw new android.os.RemoteException("casSessionHandle can't be null");
            }
            synchronized (com.android.server.tv.tunerresourcemanager.TunerResourceManagerService.this.mLock) {
                try {
                    if (!com.android.server.tv.tunerresourcemanager.TunerResourceManagerService.this.checkClientExists(casSessionRequest.clientId)) {
                        throw new android.os.RemoteException("Request cas from unregistered client:" + casSessionRequest.clientId);
                    }
                    requestCasSessionInternal = com.android.server.tv.tunerresourcemanager.TunerResourceManagerService.this.requestCasSessionInternal(casSessionRequest, iArr);
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
            return requestCasSessionInternal;
        }

        public boolean requestCiCam(@android.annotation.NonNull android.media.tv.tunerresourcemanager.TunerCiCamRequest tunerCiCamRequest, @android.annotation.NonNull int[] iArr) throws android.os.RemoteException {
            boolean requestCiCamInternal;
            com.android.server.tv.tunerresourcemanager.TunerResourceManagerService.this.enforceTrmAccessPermission("requestCiCam");
            if (iArr == null) {
                throw new android.os.RemoteException("ciCamHandle can't be null");
            }
            synchronized (com.android.server.tv.tunerresourcemanager.TunerResourceManagerService.this.mLock) {
                try {
                    if (!com.android.server.tv.tunerresourcemanager.TunerResourceManagerService.this.checkClientExists(tunerCiCamRequest.clientId)) {
                        throw new android.os.RemoteException("Request ciCam from unregistered client:" + tunerCiCamRequest.clientId);
                    }
                    requestCiCamInternal = com.android.server.tv.tunerresourcemanager.TunerResourceManagerService.this.requestCiCamInternal(tunerCiCamRequest, iArr);
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
            return requestCiCamInternal;
        }

        public boolean requestLnb(@android.annotation.NonNull android.media.tv.tunerresourcemanager.TunerLnbRequest tunerLnbRequest, @android.annotation.NonNull int[] iArr) throws android.os.RemoteException {
            boolean requestLnbInternal;
            com.android.server.tv.tunerresourcemanager.TunerResourceManagerService.this.enforceTunerAccessPermission("requestLnb");
            com.android.server.tv.tunerresourcemanager.TunerResourceManagerService.this.enforceTrmAccessPermission("requestLnb");
            if (iArr == null) {
                throw new android.os.RemoteException("lnbHandle can't be null");
            }
            synchronized (com.android.server.tv.tunerresourcemanager.TunerResourceManagerService.this.mLock) {
                try {
                    if (!com.android.server.tv.tunerresourcemanager.TunerResourceManagerService.this.checkClientExists(tunerLnbRequest.clientId)) {
                        throw new android.os.RemoteException("Request lnb from unregistered client:" + tunerLnbRequest.clientId);
                    }
                    requestLnbInternal = com.android.server.tv.tunerresourcemanager.TunerResourceManagerService.this.requestLnbInternal(tunerLnbRequest, iArr);
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
            return requestLnbInternal;
        }

        public void releaseFrontend(int i, int i2) throws android.os.RemoteException {
            com.android.server.tv.tunerresourcemanager.TunerResourceManagerService.this.enforceTunerAccessPermission("releaseFrontend");
            com.android.server.tv.tunerresourcemanager.TunerResourceManagerService.this.enforceTrmAccessPermission("releaseFrontend");
            if (!com.android.server.tv.tunerresourcemanager.TunerResourceManagerService.this.validateResourceHandle(0, i)) {
                throw new android.os.RemoteException("frontendHandle can't be invalid");
            }
            synchronized (com.android.server.tv.tunerresourcemanager.TunerResourceManagerService.this.mLock) {
                try {
                    if (!com.android.server.tv.tunerresourcemanager.TunerResourceManagerService.this.checkClientExists(i2)) {
                        throw new android.os.RemoteException("Release frontend from unregistered client:" + i2);
                    }
                    com.android.server.tv.tunerresourcemanager.FrontendResource frontendResource = com.android.server.tv.tunerresourcemanager.TunerResourceManagerService.this.getFrontendResource(i);
                    if (frontendResource == null) {
                        throw new android.os.RemoteException("Releasing frontend does not exist.");
                    }
                    int ownerClientId = frontendResource.getOwnerClientId();
                    com.android.server.tv.tunerresourcemanager.ClientProfile clientProfile = com.android.server.tv.tunerresourcemanager.TunerResourceManagerService.this.getClientProfile(ownerClientId);
                    if (ownerClientId != i2 && clientProfile != null && !clientProfile.getShareFeClientIds().contains(java.lang.Integer.valueOf(i2))) {
                        throw new android.os.RemoteException("Client is not the current owner of the releasing fe.");
                    }
                    com.android.server.tv.tunerresourcemanager.TunerResourceManagerService.this.releaseFrontendInternal(frontendResource, i2);
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        public void releaseDemux(int i, int i2) throws android.os.RemoteException {
            com.android.server.tv.tunerresourcemanager.TunerResourceManagerService.this.enforceTunerAccessPermission("releaseDemux");
            com.android.server.tv.tunerresourcemanager.TunerResourceManagerService.this.enforceTrmAccessPermission("releaseDemux");
            if (com.android.server.tv.tunerresourcemanager.TunerResourceManagerService.DEBUG) {
                android.util.Slog.e(com.android.server.tv.tunerresourcemanager.TunerResourceManagerService.TAG, "releaseDemux(demuxHandle=" + i + ")");
            }
            synchronized (com.android.server.tv.tunerresourcemanager.TunerResourceManagerService.this.mLock) {
                try {
                    if (com.android.server.tv.tunerresourcemanager.TunerResourceManagerService.this.mDemuxResources.size() == 0) {
                        return;
                    }
                    if (!com.android.server.tv.tunerresourcemanager.TunerResourceManagerService.this.checkClientExists(i2)) {
                        throw new android.os.RemoteException("Release demux for unregistered client:" + i2);
                    }
                    com.android.server.tv.tunerresourcemanager.DemuxResource demuxResource = com.android.server.tv.tunerresourcemanager.TunerResourceManagerService.this.getDemuxResource(i);
                    if (demuxResource == null) {
                        throw new android.os.RemoteException("Releasing demux does not exist.");
                    }
                    if (demuxResource.getOwnerClientId() != i2) {
                        throw new android.os.RemoteException("Client is not the current owner of the releasing demux.");
                    }
                    com.android.server.tv.tunerresourcemanager.TunerResourceManagerService.this.releaseDemuxInternal(demuxResource);
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        public void releaseDescrambler(int i, int i2) {
            com.android.server.tv.tunerresourcemanager.TunerResourceManagerService.this.enforceTunerAccessPermission("releaseDescrambler");
            com.android.server.tv.tunerresourcemanager.TunerResourceManagerService.this.enforceTrmAccessPermission("releaseDescrambler");
            if (com.android.server.tv.tunerresourcemanager.TunerResourceManagerService.DEBUG) {
                android.util.Slog.d(com.android.server.tv.tunerresourcemanager.TunerResourceManagerService.TAG, "releaseDescrambler(descramblerHandle=" + i + ")");
            }
        }

        public void releaseCasSession(int i, int i2) throws android.os.RemoteException {
            com.android.server.tv.tunerresourcemanager.TunerResourceManagerService.this.enforceTrmAccessPermission("releaseCasSession");
            if (!com.android.server.tv.tunerresourcemanager.TunerResourceManagerService.this.validateResourceHandle(4, i)) {
                throw new android.os.RemoteException("casSessionHandle can't be invalid");
            }
            synchronized (com.android.server.tv.tunerresourcemanager.TunerResourceManagerService.this.mLock) {
                try {
                    if (!com.android.server.tv.tunerresourcemanager.TunerResourceManagerService.this.checkClientExists(i2)) {
                        throw new android.os.RemoteException("Release cas from unregistered client:" + i2);
                    }
                    com.android.server.tv.tunerresourcemanager.CasResource casResource = com.android.server.tv.tunerresourcemanager.TunerResourceManagerService.this.getCasResource(com.android.server.tv.tunerresourcemanager.TunerResourceManagerService.this.getClientProfile(i2).getInUseCasSystemId());
                    if (casResource == null) {
                        throw new android.os.RemoteException("Releasing cas does not exist.");
                    }
                    if (!casResource.getOwnerClientIds().contains(java.lang.Integer.valueOf(i2))) {
                        throw new android.os.RemoteException("Client is not the current owner of the releasing cas.");
                    }
                    com.android.server.tv.tunerresourcemanager.TunerResourceManagerService.this.releaseCasSessionInternal(casResource, i2);
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        public void releaseCiCam(int i, int i2) throws android.os.RemoteException {
            com.android.server.tv.tunerresourcemanager.TunerResourceManagerService.this.enforceTrmAccessPermission("releaseCiCam");
            if (!com.android.server.tv.tunerresourcemanager.TunerResourceManagerService.this.validateResourceHandle(5, i)) {
                throw new android.os.RemoteException("ciCamHandle can't be invalid");
            }
            synchronized (com.android.server.tv.tunerresourcemanager.TunerResourceManagerService.this.mLock) {
                try {
                    if (!com.android.server.tv.tunerresourcemanager.TunerResourceManagerService.this.checkClientExists(i2)) {
                        throw new android.os.RemoteException("Release ciCam from unregistered client:" + i2);
                    }
                    int inUseCiCamId = com.android.server.tv.tunerresourcemanager.TunerResourceManagerService.this.getClientProfile(i2).getInUseCiCamId();
                    if (inUseCiCamId != com.android.server.tv.tunerresourcemanager.TunerResourceManagerService.this.getResourceIdFromHandle(i)) {
                        throw new android.os.RemoteException("The client " + i2 + " is not the owner of the releasing ciCam.");
                    }
                    com.android.server.tv.tunerresourcemanager.CiCamResource ciCamResource = com.android.server.tv.tunerresourcemanager.TunerResourceManagerService.this.getCiCamResource(inUseCiCamId);
                    if (ciCamResource == null) {
                        throw new android.os.RemoteException("Releasing ciCam does not exist.");
                    }
                    if (!ciCamResource.getOwnerClientIds().contains(java.lang.Integer.valueOf(i2))) {
                        throw new android.os.RemoteException("Client is not the current owner of the releasing ciCam.");
                    }
                    com.android.server.tv.tunerresourcemanager.TunerResourceManagerService.this.releaseCiCamInternal(ciCamResource, i2);
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        public void releaseLnb(int i, int i2) throws android.os.RemoteException {
            com.android.server.tv.tunerresourcemanager.TunerResourceManagerService.this.enforceTunerAccessPermission("releaseLnb");
            com.android.server.tv.tunerresourcemanager.TunerResourceManagerService.this.enforceTrmAccessPermission("releaseLnb");
            if (!com.android.server.tv.tunerresourcemanager.TunerResourceManagerService.this.validateResourceHandle(3, i)) {
                throw new android.os.RemoteException("lnbHandle can't be invalid");
            }
            synchronized (com.android.server.tv.tunerresourcemanager.TunerResourceManagerService.this.mLock) {
                try {
                    if (!com.android.server.tv.tunerresourcemanager.TunerResourceManagerService.this.checkClientExists(i2)) {
                        throw new android.os.RemoteException("Release lnb from unregistered client:" + i2);
                    }
                    com.android.server.tv.tunerresourcemanager.LnbResource lnbResource = com.android.server.tv.tunerresourcemanager.TunerResourceManagerService.this.getLnbResource(i);
                    if (lnbResource == null) {
                        throw new android.os.RemoteException("Releasing lnb does not exist.");
                    }
                    if (lnbResource.getOwnerClientId() != i2) {
                        throw new android.os.RemoteException("Client is not the current owner of the releasing lnb.");
                    }
                    com.android.server.tv.tunerresourcemanager.TunerResourceManagerService.this.releaseLnbInternal(lnbResource);
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        public boolean isHigherPriority(android.media.tv.tunerresourcemanager.ResourceClientProfile resourceClientProfile, android.media.tv.tunerresourcemanager.ResourceClientProfile resourceClientProfile2) throws android.os.RemoteException {
            boolean isHigherPriorityInternal;
            com.android.server.tv.tunerresourcemanager.TunerResourceManagerService.this.enforceTrmAccessPermission("isHigherPriority");
            if (resourceClientProfile == null || resourceClientProfile2 == null) {
                throw new android.os.RemoteException("Client profiles can't be null.");
            }
            synchronized (com.android.server.tv.tunerresourcemanager.TunerResourceManagerService.this.mLock) {
                isHigherPriorityInternal = com.android.server.tv.tunerresourcemanager.TunerResourceManagerService.this.isHigherPriorityInternal(resourceClientProfile, resourceClientProfile2);
            }
            return isHigherPriorityInternal;
        }

        public void storeResourceMap(int i) {
            com.android.server.tv.tunerresourcemanager.TunerResourceManagerService.this.enforceTrmAccessPermission("storeResourceMap");
            synchronized (com.android.server.tv.tunerresourcemanager.TunerResourceManagerService.this.mLock) {
                com.android.server.tv.tunerresourcemanager.TunerResourceManagerService.this.storeResourceMapInternal(i);
            }
        }

        public void clearResourceMap(int i) {
            com.android.server.tv.tunerresourcemanager.TunerResourceManagerService.this.enforceTrmAccessPermission("clearResourceMap");
            synchronized (com.android.server.tv.tunerresourcemanager.TunerResourceManagerService.this.mLock) {
                com.android.server.tv.tunerresourcemanager.TunerResourceManagerService.this.clearResourceMapInternal(i);
            }
        }

        public void restoreResourceMap(int i) {
            com.android.server.tv.tunerresourcemanager.TunerResourceManagerService.this.enforceTrmAccessPermission("restoreResourceMap");
            synchronized (com.android.server.tv.tunerresourcemanager.TunerResourceManagerService.this.mLock) {
                com.android.server.tv.tunerresourcemanager.TunerResourceManagerService.this.restoreResourceMapInternal(i);
            }
        }

        public boolean acquireLock(int i, long j) {
            com.android.server.tv.tunerresourcemanager.TunerResourceManagerService.this.enforceTrmAccessPermission("acquireLock");
            return com.android.server.tv.tunerresourcemanager.TunerResourceManagerService.this.acquireLockInternal(i, j, 500L);
        }

        public boolean releaseLock(int i) {
            com.android.server.tv.tunerresourcemanager.TunerResourceManagerService.this.enforceTrmAccessPermission("releaseLock");
            return com.android.server.tv.tunerresourcemanager.TunerResourceManagerService.this.releaseLockInternal(i, 500L, false, false);
        }

        protected void dump(java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, java.lang.String[] strArr) {
            android.util.IndentingPrintWriter indentingPrintWriter = new android.util.IndentingPrintWriter(printWriter, "  ");
            if (com.android.server.tv.tunerresourcemanager.TunerResourceManagerService.this.getContext().checkCallingOrSelfPermission("android.permission.DUMP") != 0) {
                indentingPrintWriter.println("Permission Denial: can't dump!");
                return;
            }
            synchronized (com.android.server.tv.tunerresourcemanager.TunerResourceManagerService.this.mLock) {
                com.android.server.tv.tunerresourcemanager.TunerResourceManagerService.this.dumpMap(com.android.server.tv.tunerresourcemanager.TunerResourceManagerService.this.mClientProfiles, "ClientProfiles:", "\n", indentingPrintWriter);
                com.android.server.tv.tunerresourcemanager.TunerResourceManagerService.this.dumpMap(com.android.server.tv.tunerresourcemanager.TunerResourceManagerService.this.mFrontendResources, "FrontendResources:", "\n", indentingPrintWriter);
                com.android.server.tv.tunerresourcemanager.TunerResourceManagerService.this.dumpSIA(com.android.server.tv.tunerresourcemanager.TunerResourceManagerService.this.mFrontendExistingNums, "FrontendExistingNums:", ", ", indentingPrintWriter);
                com.android.server.tv.tunerresourcemanager.TunerResourceManagerService.this.dumpSIA(com.android.server.tv.tunerresourcemanager.TunerResourceManagerService.this.mFrontendUsedNums, "FrontendUsedNums:", ", ", indentingPrintWriter);
                com.android.server.tv.tunerresourcemanager.TunerResourceManagerService.this.dumpSIA(com.android.server.tv.tunerresourcemanager.TunerResourceManagerService.this.mFrontendMaxUsableNums, "FrontendMaxUsableNums:", ", ", indentingPrintWriter);
                com.android.server.tv.tunerresourcemanager.TunerResourceManagerService.this.dumpMap(com.android.server.tv.tunerresourcemanager.TunerResourceManagerService.this.mFrontendResourcesBackup, "FrontendResourcesBackUp:", "\n", indentingPrintWriter);
                com.android.server.tv.tunerresourcemanager.TunerResourceManagerService.this.dumpSIA(com.android.server.tv.tunerresourcemanager.TunerResourceManagerService.this.mFrontendExistingNumsBackup, "FrontendExistingNumsBackup:", ", ", indentingPrintWriter);
                com.android.server.tv.tunerresourcemanager.TunerResourceManagerService.this.dumpSIA(com.android.server.tv.tunerresourcemanager.TunerResourceManagerService.this.mFrontendUsedNumsBackup, "FrontendUsedNumsBackup:", ", ", indentingPrintWriter);
                com.android.server.tv.tunerresourcemanager.TunerResourceManagerService.this.dumpSIA(com.android.server.tv.tunerresourcemanager.TunerResourceManagerService.this.mFrontendMaxUsableNumsBackup, "FrontendUsedNumsBackup:", ", ", indentingPrintWriter);
                com.android.server.tv.tunerresourcemanager.TunerResourceManagerService.this.dumpMap(com.android.server.tv.tunerresourcemanager.TunerResourceManagerService.this.mDemuxResources, "DemuxResource:", "\n", indentingPrintWriter);
                com.android.server.tv.tunerresourcemanager.TunerResourceManagerService.this.dumpMap(com.android.server.tv.tunerresourcemanager.TunerResourceManagerService.this.mLnbResources, "LnbResource:", "\n", indentingPrintWriter);
                com.android.server.tv.tunerresourcemanager.TunerResourceManagerService.this.dumpMap(com.android.server.tv.tunerresourcemanager.TunerResourceManagerService.this.mCasResources, "CasResource:", "\n", indentingPrintWriter);
                com.android.server.tv.tunerresourcemanager.TunerResourceManagerService.this.dumpMap(com.android.server.tv.tunerresourcemanager.TunerResourceManagerService.this.mCiCamResources, "CiCamResource:", "\n", indentingPrintWriter);
                com.android.server.tv.tunerresourcemanager.TunerResourceManagerService.this.dumpMap(com.android.server.tv.tunerresourcemanager.TunerResourceManagerService.this.mListeners, "Listners:", "\n", indentingPrintWriter);
            }
        }

        public int getClientPriority(int i, int i2) throws android.os.RemoteException {
            int clientPriority;
            com.android.server.tv.tunerresourcemanager.TunerResourceManagerService.this.enforceTrmAccessPermission("getClientPriority");
            synchronized (com.android.server.tv.tunerresourcemanager.TunerResourceManagerService.this.mLock) {
                clientPriority = com.android.server.tv.tunerresourcemanager.TunerResourceManagerService.this.getClientPriority(i, com.android.server.tv.tunerresourcemanager.TunerResourceManagerService.this.checkIsForeground(i2));
            }
            return clientPriority;
        }

        public int getConfigPriority(int i, boolean z) throws android.os.RemoteException {
            int clientPriority;
            com.android.server.tv.tunerresourcemanager.TunerResourceManagerService.this.enforceTrmAccessPermission("getConfigPriority");
            synchronized (com.android.server.tv.tunerresourcemanager.TunerResourceManagerService.this.mLock) {
                clientPriority = com.android.server.tv.tunerresourcemanager.TunerResourceManagerService.this.getClientPriority(i, z);
            }
            return clientPriority;
        }
    }

    @Override // android.os.IBinder.DeathRecipient
    public void binderDied() {
        if (DEBUG) {
            android.util.Slog.w(TAG, "Native media resource manager service has died");
        }
        synchronized (this.mLock) {
            this.mMediaResourceManager = null;
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    protected void registerClientProfileInternal(android.media.tv.tunerresourcemanager.ResourceClientProfile resourceClientProfile, android.media.tv.tunerresourcemanager.IResourcesReclaimListener iResourcesReclaimListener, int[] iArr) {
        int clientPid;
        if (DEBUG) {
            android.util.Slog.d(TAG, "registerClientProfile(clientProfile=" + resourceClientProfile + ")");
        }
        iArr[0] = -1;
        if (this.mTvInputManager == null) {
            android.util.Slog.e(TAG, "TvInputManager is null. Can't register client profile.");
            return;
        }
        int i = this.mNextUnusedClientId;
        this.mNextUnusedClientId = i + 1;
        iArr[0] = i;
        if (resourceClientProfile.tvInputSessionId == null) {
            clientPid = android.os.Binder.getCallingPid();
        } else {
            clientPid = this.mTvInputManager.getClientPid(resourceClientProfile.tvInputSessionId);
        }
        if (resourceClientProfile.tvInputSessionId != null && this.mMediaResourceManager != null) {
            try {
                this.mMediaResourceManager.overridePid(android.os.Binder.getCallingPid(), clientPid);
            } catch (android.os.RemoteException e) {
                android.util.Slog.e(TAG, "Could not overridePid in resourceManagerSercice, remote exception: " + e);
            }
        }
        com.android.server.tv.tunerresourcemanager.ClientProfile build = new com.android.server.tv.tunerresourcemanager.ClientProfile.Builder(iArr[0]).tvInputSessionId(resourceClientProfile.tvInputSessionId).useCase(resourceClientProfile.useCase).processId(clientPid).build();
        build.setPriority(getClientPriority(resourceClientProfile.useCase, checkIsForeground(clientPid)));
        addClientProfile(iArr[0], build, iResourcesReclaimListener);
    }

    @com.android.internal.annotations.VisibleForTesting
    protected void unregisterClientProfileInternal(int i) {
        if (DEBUG) {
            android.util.Slog.d(TAG, "unregisterClientProfile(clientId=" + i + ")");
        }
        removeClientProfile(i);
        if (this.mMediaResourceManager != null) {
            try {
                this.mMediaResourceManager.overridePid(android.os.Binder.getCallingPid(), -1);
            } catch (android.os.RemoteException e) {
                android.util.Slog.e(TAG, "Could not overridePid in resourceManagerSercice when unregister, remote exception: " + e);
            }
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    protected boolean updateClientPriorityInternal(int i, int i2, int i3) {
        if (DEBUG) {
            android.util.Slog.d(TAG, "updateClientPriority(clientId=" + i + ", priority=" + i2 + ", niceValue=" + i3 + ")");
        }
        com.android.server.tv.tunerresourcemanager.ClientProfile clientProfile = getClientProfile(i);
        if (clientProfile == null) {
            android.util.Slog.e(TAG, "Can not find client profile with id " + i + " when trying to update the client priority.");
            return false;
        }
        clientProfile.overwritePriority(i2);
        clientProfile.setNiceValue(i3);
        return true;
    }

    protected boolean hasUnusedFrontendInternal(int i) {
        for (com.android.server.tv.tunerresourcemanager.FrontendResource frontendResource : getFrontendResources().values()) {
            if (frontendResource.getType() == i && !frontendResource.isInUse()) {
                return true;
            }
        }
        return false;
    }

    protected boolean isLowestPriorityInternal(int i, int i2) throws android.os.RemoteException {
        com.android.server.tv.tunerresourcemanager.ClientProfile clientProfile = getClientProfile(i);
        if (clientProfile == null) {
            return true;
        }
        clientPriorityUpdateOnRequest(clientProfile);
        int priority = clientProfile.getPriority();
        for (com.android.server.tv.tunerresourcemanager.FrontendResource frontendResource : getFrontendResources().values()) {
            if (frontendResource.getType() == i2 && frontendResource.isInUse() && priority > updateAndGetOwnerClientPriority(frontendResource.getOwnerClientId())) {
                return false;
            }
        }
        return true;
    }

    protected void storeResourceMapInternal(int i) {
        switch (i) {
            case 0:
                replaceFeResourceMap(this.mFrontendResources, this.mFrontendResourcesBackup);
                replaceFeCounts(this.mFrontendExistingNums, this.mFrontendExistingNumsBackup);
                replaceFeCounts(this.mFrontendUsedNums, this.mFrontendUsedNumsBackup);
                replaceFeCounts(this.mFrontendMaxUsableNums, this.mFrontendMaxUsableNumsBackup);
                break;
        }
    }

    protected void clearResourceMapInternal(int i) {
        switch (i) {
            case 0:
                replaceFeResourceMap(null, this.mFrontendResources);
                replaceFeCounts(null, this.mFrontendExistingNums);
                replaceFeCounts(null, this.mFrontendUsedNums);
                replaceFeCounts(null, this.mFrontendMaxUsableNums);
                break;
        }
    }

    protected void restoreResourceMapInternal(int i) {
        switch (i) {
            case 0:
                replaceFeResourceMap(this.mFrontendResourcesBackup, this.mFrontendResources);
                replaceFeCounts(this.mFrontendExistingNumsBackup, this.mFrontendExistingNums);
                replaceFeCounts(this.mFrontendUsedNumsBackup, this.mFrontendUsedNums);
                replaceFeCounts(this.mFrontendMaxUsableNumsBackup, this.mFrontendMaxUsableNums);
                break;
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    protected void setFrontendInfoListInternal(android.media.tv.tunerresourcemanager.TunerFrontendInfo[] tunerFrontendInfoArr) {
        if (DEBUG) {
            android.util.Slog.d(TAG, "updateFrontendInfo:");
            for (android.media.tv.tunerresourcemanager.TunerFrontendInfo tunerFrontendInfo : tunerFrontendInfoArr) {
                android.util.Slog.d(TAG, tunerFrontendInfo.toString());
            }
        }
        java.util.HashSet hashSet = new java.util.HashSet(getFrontendResources().keySet());
        for (int i = 0; i < tunerFrontendInfoArr.length; i++) {
            if (getFrontendResource(tunerFrontendInfoArr[i].handle) != null) {
                if (DEBUG) {
                    android.util.Slog.d(TAG, "Frontend handle=" + tunerFrontendInfoArr[i].handle + "exists.");
                }
                hashSet.remove(java.lang.Integer.valueOf(tunerFrontendInfoArr[i].handle));
            } else {
                addFrontendResource(new com.android.server.tv.tunerresourcemanager.FrontendResource.Builder(tunerFrontendInfoArr[i].handle).type(tunerFrontendInfoArr[i].type).exclusiveGroupId(tunerFrontendInfoArr[i].exclusiveGroupId).build());
            }
        }
        java.util.Iterator it = hashSet.iterator();
        while (it.hasNext()) {
            removeFrontendResource(((java.lang.Integer) it.next()).intValue());
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    protected void setDemuxInfoListInternal(android.media.tv.tunerresourcemanager.TunerDemuxInfo[] tunerDemuxInfoArr) {
        if (DEBUG) {
            android.util.Slog.d(TAG, "updateDemuxInfo:");
            for (android.media.tv.tunerresourcemanager.TunerDemuxInfo tunerDemuxInfo : tunerDemuxInfoArr) {
                android.util.Slog.d(TAG, tunerDemuxInfo.toString());
            }
        }
        java.util.HashSet hashSet = new java.util.HashSet(getDemuxResources().keySet());
        for (int i = 0; i < tunerDemuxInfoArr.length; i++) {
            if (getDemuxResource(tunerDemuxInfoArr[i].handle) != null) {
                if (DEBUG) {
                    android.util.Slog.d(TAG, "Demux handle=" + tunerDemuxInfoArr[i].handle + "exists.");
                }
                hashSet.remove(java.lang.Integer.valueOf(tunerDemuxInfoArr[i].handle));
            } else {
                addDemuxResource(new com.android.server.tv.tunerresourcemanager.DemuxResource.Builder(tunerDemuxInfoArr[i].handle).filterTypes(tunerDemuxInfoArr[i].filterTypes).build());
            }
        }
        java.util.Iterator it = hashSet.iterator();
        while (it.hasNext()) {
            removeDemuxResource(((java.lang.Integer) it.next()).intValue());
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    protected void setLnbInfoListInternal(int[] iArr) {
        if (DEBUG) {
            for (int i : iArr) {
                android.util.Slog.d(TAG, "updateLnbInfo(lnbHanle=" + i + ")");
            }
        }
        java.util.HashSet hashSet = new java.util.HashSet(getLnbResources().keySet());
        for (int i2 = 0; i2 < iArr.length; i2++) {
            if (getLnbResource(iArr[i2]) != null) {
                if (DEBUG) {
                    android.util.Slog.d(TAG, "Lnb handle=" + iArr[i2] + "exists.");
                }
                hashSet.remove(java.lang.Integer.valueOf(iArr[i2]));
            } else {
                addLnbResource(new com.android.server.tv.tunerresourcemanager.LnbResource.Builder(iArr[i2]).build());
            }
        }
        java.util.Iterator it = hashSet.iterator();
        while (it.hasNext()) {
            removeLnbResource(((java.lang.Integer) it.next()).intValue());
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    protected void updateCasInfoInternal(int i, int i2) {
        if (DEBUG) {
            android.util.Slog.d(TAG, "updateCasInfo(casSystemId=" + i + ", maxSessionNum=" + i2 + ")");
        }
        if (i2 == 0) {
            removeCasResource(i);
            removeCiCamResource(i);
            return;
        }
        com.android.server.tv.tunerresourcemanager.CasResource casResource = getCasResource(i);
        com.android.server.tv.tunerresourcemanager.CiCamResource ciCamResource = getCiCamResource(i);
        if (casResource != null) {
            if (casResource.getUsedSessionNum() > i2) {
                casResource.getUsedSessionNum();
            }
            casResource.updateMaxSessionNum(i2);
            if (ciCamResource != null) {
                ciCamResource.updateMaxSessionNum(i2);
                return;
            }
            return;
        }
        com.android.server.tv.tunerresourcemanager.CasResource build = new com.android.server.tv.tunerresourcemanager.CasResource.Builder(i).maxSessionNum(i2).build();
        com.android.server.tv.tunerresourcemanager.CiCamResource build2 = new com.android.server.tv.tunerresourcemanager.CiCamResource.Builder(i).maxSessionNum(i2).build();
        addCasResource(build);
        addCiCamResource(build2);
    }

    @com.android.internal.annotations.VisibleForTesting
    protected boolean requestFrontendInternal(android.media.tv.tunerresourcemanager.TunerFrontendRequest tunerFrontendRequest, int[] iArr) {
        int frontendHighestClientPriority;
        if (DEBUG) {
            android.util.Slog.d(TAG, "requestFrontend(request=" + tunerFrontendRequest + ")");
        }
        iArr[0] = -1;
        com.android.server.tv.tunerresourcemanager.ClientProfile clientProfile = getClientProfile(tunerFrontendRequest.clientId);
        if (clientProfile == null) {
            return false;
        }
        clientPriorityUpdateOnRequest(clientProfile);
        boolean z = tunerFrontendRequest.desiredId != -1;
        java.util.Iterator<com.android.server.tv.tunerresourcemanager.FrontendResource> it = getFrontendResources().values().iterator();
        int i = 1001;
        boolean z2 = false;
        int i2 = -1;
        int i3 = -1;
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            com.android.server.tv.tunerresourcemanager.FrontendResource next = it.next();
            int resourceIdFromHandle = getResourceIdFromHandle(next.getHandle());
            if (next.getType() == tunerFrontendRequest.frontendType && (!z || resourceIdFromHandle == tunerFrontendRequest.desiredId)) {
                if (!next.isInUse()) {
                    if (isFrontendMaxNumUseReached(tunerFrontendRequest.frontendType)) {
                        continue;
                    } else {
                        if (next.getExclusiveGroupMemberFeHandles().isEmpty()) {
                            i2 = next.getHandle();
                            break;
                        }
                        if (i2 == -1) {
                            i2 = next.getHandle();
                        }
                    }
                } else if (i2 == -1 && i > (frontendHighestClientPriority = getFrontendHighestClientPriority(next.getOwnerClientId()))) {
                    if (next.getType() == getFrontendResource(getClientProfile(next.getOwnerClientId()).getPrimaryFrontend()).getType() || !isFrontendMaxNumUseReached(next.getType())) {
                        int handle = next.getHandle();
                        z2 = clientProfile.getProcessId() == getClientProfile(next.getOwnerClientId()).getProcessId();
                        i3 = handle;
                        i = frontendHighestClientPriority;
                    }
                }
            }
        }
        if (i2 != -1) {
            iArr[0] = i2;
            updateFrontendClientMappingOnNewGrant(i2, tunerFrontendRequest.clientId);
            return true;
        }
        if (i3 == -1 || ((clientProfile.getPriority() <= i && (clientProfile.getPriority() != i || !z2)) || !reclaimResource(getFrontendResource(i3).getOwnerClientId(), 0))) {
            return false;
        }
        iArr[0] = i3;
        updateFrontendClientMappingOnNewGrant(i3, tunerFrontendRequest.clientId);
        return true;
    }

    @com.android.internal.annotations.VisibleForTesting
    protected void shareFrontendInternal(int i, int i2) {
        if (DEBUG) {
            android.util.Slog.d(TAG, "shareFrontend from " + i + " with " + i2);
        }
        java.lang.Integer shareeFeClientId = getClientProfile(i).getShareeFeClientId();
        if (shareeFeClientId.intValue() != -1) {
            getClientProfile(shareeFeClientId.intValue()).stopSharingFrontend(i);
            getClientProfile(i).releaseFrontend();
        }
        java.util.Iterator<java.lang.Integer> it = getClientProfile(i2).getInUseFrontendHandles().iterator();
        while (it.hasNext()) {
            getClientProfile(i).useFrontend(it.next().intValue());
        }
        getClientProfile(i).setShareeFeClientId(java.lang.Integer.valueOf(i2));
        getClientProfile(i2).shareFrontend(i);
    }

    private boolean transferFeOwner(int i, int i2) {
        com.android.server.tv.tunerresourcemanager.ClientProfile clientProfile = getClientProfile(i);
        com.android.server.tv.tunerresourcemanager.ClientProfile clientProfile2 = getClientProfile(i2);
        clientProfile2.shareFrontend(i);
        clientProfile.stopSharingFrontend(i2);
        clientProfile2.setShareeFeClientId(-1);
        clientProfile.setShareeFeClientId(java.lang.Integer.valueOf(i2));
        java.util.Iterator<java.lang.Integer> it = clientProfile2.getInUseFrontendHandles().iterator();
        while (it.hasNext()) {
            getFrontendResource(it.next().intValue()).setOwner(i2);
        }
        clientProfile2.setPrimaryFrontend(clientProfile.getPrimaryFrontend());
        clientProfile.setPrimaryFrontend(-1);
        java.util.Iterator<java.lang.Integer> it2 = clientProfile.getInUseFrontendHandles().iterator();
        while (it2.hasNext()) {
            int intValue = it2.next().intValue();
            int ownerClientId = getFrontendResource(intValue).getOwnerClientId();
            if (ownerClientId != i2) {
                android.util.Slog.e(TAG, "something is wrong in transferFeOwner:" + intValue + ", " + ownerClientId + ", " + i2);
                return false;
            }
        }
        return true;
    }

    private boolean transferFeCiCamOwner(int i, int i2) {
        com.android.server.tv.tunerresourcemanager.ClientProfile clientProfile = getClientProfile(i);
        com.android.server.tv.tunerresourcemanager.ClientProfile clientProfile2 = getClientProfile(i2);
        int inUseCiCamId = clientProfile.getInUseCiCamId();
        clientProfile2.useCiCam(inUseCiCamId);
        getCiCamResource(inUseCiCamId).setOwner(i2);
        clientProfile.releaseCiCam();
        return true;
    }

    private boolean transferLnbOwner(int i, int i2) {
        com.android.server.tv.tunerresourcemanager.ClientProfile clientProfile = getClientProfile(i);
        com.android.server.tv.tunerresourcemanager.ClientProfile clientProfile2 = getClientProfile(i2);
        java.util.HashSet hashSet = new java.util.HashSet();
        for (java.lang.Integer num : clientProfile.getInUseLnbHandles()) {
            clientProfile2.useLnb(num.intValue());
            getLnbResource(num.intValue()).setOwner(i2);
            hashSet.add(num);
        }
        java.util.Iterator it = hashSet.iterator();
        while (it.hasNext()) {
            clientProfile.releaseLnb(((java.lang.Integer) it.next()).intValue());
        }
        return true;
    }

    @com.android.internal.annotations.VisibleForTesting
    protected boolean transferOwnerInternal(int i, int i2, int i3) {
        switch (i) {
            case 0:
                return transferFeOwner(i2, i3);
            case 3:
                return transferLnbOwner(i2, i3);
            case 5:
                return transferFeCiCamOwner(i2, i3);
            default:
                android.util.Slog.e(TAG, "transferOwnerInternal. unsupported resourceType: " + i);
                return false;
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    protected boolean requestLnbInternal(android.media.tv.tunerresourcemanager.TunerLnbRequest tunerLnbRequest, int[] iArr) {
        int i;
        if (DEBUG) {
            android.util.Slog.d(TAG, "requestLnb(request=" + tunerLnbRequest + ")");
        }
        iArr[0] = -1;
        com.android.server.tv.tunerresourcemanager.ClientProfile clientProfile = getClientProfile(tunerLnbRequest.clientId);
        clientPriorityUpdateOnRequest(clientProfile);
        java.util.Iterator<com.android.server.tv.tunerresourcemanager.LnbResource> it = getLnbResources().values().iterator();
        int i2 = 1001;
        boolean z = false;
        int i3 = -1;
        while (true) {
            if (!it.hasNext()) {
                i = -1;
                break;
            }
            com.android.server.tv.tunerresourcemanager.LnbResource next = it.next();
            if (!next.isInUse()) {
                i = next.getHandle();
                break;
            }
            int updateAndGetOwnerClientPriority = updateAndGetOwnerClientPriority(next.getOwnerClientId());
            if (i2 > updateAndGetOwnerClientPriority) {
                i3 = next.getHandle();
                z = clientProfile.getProcessId() == getClientProfile(next.getOwnerClientId()).getProcessId();
                i2 = updateAndGetOwnerClientPriority;
            }
        }
        if (i > -1) {
            iArr[0] = i;
            updateLnbClientMappingOnNewGrant(i, tunerLnbRequest.clientId);
            return true;
        }
        if (i3 <= -1 || ((clientProfile.getPriority() <= i2 && (clientProfile.getPriority() != i2 || !z)) || !reclaimResource(getLnbResource(i3).getOwnerClientId(), 3))) {
            return false;
        }
        iArr[0] = i3;
        updateLnbClientMappingOnNewGrant(i3, tunerLnbRequest.clientId);
        return true;
    }

    @com.android.internal.annotations.VisibleForTesting
    protected boolean requestCasSessionInternal(android.media.tv.tunerresourcemanager.CasSessionRequest casSessionRequest, int[] iArr) {
        if (DEBUG) {
            android.util.Slog.d(TAG, "requestCasSession(request=" + casSessionRequest + ")");
        }
        com.android.server.tv.tunerresourcemanager.CasResource casResource = getCasResource(casSessionRequest.casSystemId);
        if (casResource == null) {
            casResource = new com.android.server.tv.tunerresourcemanager.CasResource.Builder(casSessionRequest.casSystemId).maxSessionNum(Integer.MAX_VALUE).build();
            addCasResource(casResource);
        }
        iArr[0] = -1;
        com.android.server.tv.tunerresourcemanager.ClientProfile clientProfile = getClientProfile(casSessionRequest.clientId);
        clientPriorityUpdateOnRequest(clientProfile);
        if (!casResource.isFullyUsed()) {
            iArr[0] = generateResourceHandle(4, casResource.getSystemId());
            updateCasClientMappingOnNewGrant(casSessionRequest.casSystemId, casSessionRequest.clientId);
            return true;
        }
        java.util.Iterator<java.lang.Integer> it = casResource.getOwnerClientIds().iterator();
        int i = 1001;
        boolean z = false;
        int i2 = -1;
        while (it.hasNext()) {
            int intValue = it.next().intValue();
            int updateAndGetOwnerClientPriority = updateAndGetOwnerClientPriority(intValue);
            if (i > updateAndGetOwnerClientPriority) {
                z = clientProfile.getProcessId() == getClientProfile(intValue).getProcessId();
                i2 = intValue;
                i = updateAndGetOwnerClientPriority;
            }
        }
        if (i2 <= -1 || ((clientProfile.getPriority() <= i && (clientProfile.getPriority() != i || !z)) || !reclaimResource(i2, 4))) {
            return false;
        }
        iArr[0] = generateResourceHandle(4, casResource.getSystemId());
        updateCasClientMappingOnNewGrant(casSessionRequest.casSystemId, casSessionRequest.clientId);
        return true;
    }

    @com.android.internal.annotations.VisibleForTesting
    protected boolean requestCiCamInternal(android.media.tv.tunerresourcemanager.TunerCiCamRequest tunerCiCamRequest, int[] iArr) {
        if (DEBUG) {
            android.util.Slog.d(TAG, "requestCiCamInternal(TunerCiCamRequest=" + tunerCiCamRequest + ")");
        }
        com.android.server.tv.tunerresourcemanager.CiCamResource ciCamResource = getCiCamResource(tunerCiCamRequest.ciCamId);
        if (ciCamResource == null) {
            ciCamResource = new com.android.server.tv.tunerresourcemanager.CiCamResource.Builder(tunerCiCamRequest.ciCamId).maxSessionNum(Integer.MAX_VALUE).build();
            addCiCamResource(ciCamResource);
        }
        iArr[0] = -1;
        com.android.server.tv.tunerresourcemanager.ClientProfile clientProfile = getClientProfile(tunerCiCamRequest.clientId);
        clientPriorityUpdateOnRequest(clientProfile);
        if (!ciCamResource.isFullyUsed()) {
            iArr[0] = generateResourceHandle(5, ciCamResource.getCiCamId());
            updateCiCamClientMappingOnNewGrant(tunerCiCamRequest.ciCamId, tunerCiCamRequest.clientId);
            return true;
        }
        java.util.Iterator<java.lang.Integer> it = ciCamResource.getOwnerClientIds().iterator();
        int i = 1001;
        boolean z = false;
        int i2 = -1;
        while (it.hasNext()) {
            int intValue = it.next().intValue();
            int updateAndGetOwnerClientPriority = updateAndGetOwnerClientPriority(intValue);
            if (i > updateAndGetOwnerClientPriority) {
                z = clientProfile.getProcessId() == getClientProfile(intValue).getProcessId();
                i2 = intValue;
                i = updateAndGetOwnerClientPriority;
            }
        }
        if (i2 <= -1 || ((clientProfile.getPriority() <= i && (clientProfile.getPriority() != i || !z)) || !reclaimResource(i2, 5))) {
            return false;
        }
        iArr[0] = generateResourceHandle(5, ciCamResource.getCiCamId());
        updateCiCamClientMappingOnNewGrant(tunerCiCamRequest.ciCamId, tunerCiCamRequest.clientId);
        return true;
    }

    @com.android.internal.annotations.VisibleForTesting
    protected boolean isHigherPriorityInternal(android.media.tv.tunerresourcemanager.ResourceClientProfile resourceClientProfile, android.media.tv.tunerresourcemanager.ResourceClientProfile resourceClientProfile2) {
        int clientPid;
        int clientPid2;
        if (DEBUG) {
            android.util.Slog.d(TAG, "isHigherPriority(challengerProfile=" + resourceClientProfile + ", holderProfile=" + resourceClientProfile + ")");
        }
        if (this.mTvInputManager == null) {
            android.util.Slog.e(TAG, "TvInputManager is null. Can't compare the priority.");
            return true;
        }
        if (resourceClientProfile.tvInputSessionId == null) {
            clientPid = android.os.Binder.getCallingPid();
        } else {
            clientPid = this.mTvInputManager.getClientPid(resourceClientProfile.tvInputSessionId);
        }
        if (resourceClientProfile2.tvInputSessionId == null) {
            clientPid2 = android.os.Binder.getCallingPid();
        } else {
            clientPid2 = this.mTvInputManager.getClientPid(resourceClientProfile2.tvInputSessionId);
        }
        return getClientPriority(resourceClientProfile.useCase, checkIsForeground(clientPid)) > getClientPriority(resourceClientProfile2.useCase, checkIsForeground(clientPid2));
    }

    @com.android.internal.annotations.VisibleForTesting
    protected void releaseFrontendInternal(com.android.server.tv.tunerresourcemanager.FrontendResource frontendResource, int i) {
        com.android.server.tv.tunerresourcemanager.ClientProfile clientProfile;
        if (DEBUG) {
            android.util.Slog.d(TAG, "releaseFrontend(id=" + frontendResource.getHandle() + ", clientId=" + i + " )");
        }
        if (i == frontendResource.getOwnerClientId() && (clientProfile = getClientProfile(frontendResource.getOwnerClientId())) != null) {
            java.util.Iterator<java.lang.Integer> it = clientProfile.getShareFeClientIds().iterator();
            while (it.hasNext()) {
                reclaimResource(it.next().intValue(), 0);
            }
        }
        clearFrontendAndClientMapping(getClientProfile(i));
    }

    @com.android.internal.annotations.VisibleForTesting
    protected void releaseDemuxInternal(com.android.server.tv.tunerresourcemanager.DemuxResource demuxResource) {
        if (DEBUG) {
            android.util.Slog.d(TAG, "releaseDemux(DemuxHandle=" + demuxResource.getHandle() + ")");
        }
        updateDemuxClientMappingOnRelease(demuxResource);
    }

    @com.android.internal.annotations.VisibleForTesting
    protected void releaseLnbInternal(com.android.server.tv.tunerresourcemanager.LnbResource lnbResource) {
        if (DEBUG) {
            android.util.Slog.d(TAG, "releaseLnb(lnbHandle=" + lnbResource.getHandle() + ")");
        }
        updateLnbClientMappingOnRelease(lnbResource);
    }

    @com.android.internal.annotations.VisibleForTesting
    protected void releaseCasSessionInternal(com.android.server.tv.tunerresourcemanager.CasResource casResource, int i) {
        if (DEBUG) {
            android.util.Slog.d(TAG, "releaseCasSession(sessionResourceId=" + casResource.getSystemId() + ")");
        }
        updateCasClientMappingOnRelease(casResource, i);
    }

    @com.android.internal.annotations.VisibleForTesting
    protected void releaseCiCamInternal(com.android.server.tv.tunerresourcemanager.CiCamResource ciCamResource, int i) {
        if (DEBUG) {
            android.util.Slog.d(TAG, "releaseCiCamInternal(ciCamId=" + ciCamResource.getCiCamId() + ")");
        }
        updateCiCamClientMappingOnRelease(ciCamResource, i);
    }

    @com.android.internal.annotations.VisibleForTesting
    protected boolean requestDemuxInternal(android.media.tv.tunerresourcemanager.TunerDemuxRequest tunerDemuxRequest, int[] iArr) {
        int updateAndGetOwnerClientPriority;
        boolean z;
        if (DEBUG) {
            android.util.Slog.d(TAG, "requestDemux(request=" + tunerDemuxRequest + ")");
        }
        if (this.mDemuxResources.size() == 0) {
            iArr[0] = generateResourceHandle(1, 0);
            return true;
        }
        iArr[0] = -1;
        com.android.server.tv.tunerresourcemanager.ClientProfile clientProfile = getClientProfile(tunerDemuxRequest.clientId);
        if (clientProfile == null) {
            return false;
        }
        clientPriorityUpdateOnRequest(clientProfile);
        boolean z2 = tunerDemuxRequest.desiredFilterTypes != 0;
        int i = 33;
        int i2 = -1;
        int i3 = -1;
        boolean z3 = false;
        int i4 = 1001;
        int i5 = 33;
        for (com.android.server.tv.tunerresourcemanager.DemuxResource demuxResource : getDemuxResources().values()) {
            if (!z2 || demuxResource.hasSufficientCaps(tunerDemuxRequest.desiredFilterTypes)) {
                if (demuxResource.isInUse()) {
                    if (i2 == -1 && i4 >= (updateAndGetOwnerClientPriority = updateAndGetOwnerClientPriority(demuxResource.getOwnerClientId()))) {
                        int numOfCaps = demuxResource.getNumOfCaps();
                        if (i4 > updateAndGetOwnerClientPriority) {
                            z3 = clientProfile.getProcessId() == getClientProfile(demuxResource.getOwnerClientId()).getProcessId();
                            i5 = numOfCaps;
                            i4 = updateAndGetOwnerClientPriority;
                            z = true;
                        } else if (i5 <= numOfCaps) {
                            z = false;
                        } else {
                            i5 = numOfCaps;
                            z = true;
                        }
                        if (z) {
                            i3 = demuxResource.getHandle();
                        }
                    }
                } else {
                    int numOfCaps2 = demuxResource.getNumOfCaps();
                    if (i > numOfCaps2) {
                        i2 = demuxResource.getHandle();
                        i = numOfCaps2;
                    }
                }
            }
        }
        if (i2 != -1) {
            iArr[0] = i2;
            updateDemuxClientMappingOnNewGrant(i2, tunerDemuxRequest.clientId);
            return true;
        }
        if (i3 == -1 || ((clientProfile.getPriority() <= i4 && !(clientProfile.getPriority() == i4 && z3)) || !reclaimResource(getDemuxResource(i3).getOwnerClientId(), 1))) {
            return false;
        }
        iArr[0] = i3;
        updateDemuxClientMappingOnNewGrant(i3, tunerDemuxRequest.clientId);
        return true;
    }

    @com.android.internal.annotations.VisibleForTesting
    protected void clientPriorityUpdateOnRequest(com.android.server.tv.tunerresourcemanager.ClientProfile clientProfile) {
        if (clientProfile.isPriorityOverwritten()) {
            return;
        }
        clientProfile.setPriority(getClientPriority(clientProfile.getUseCase(), checkIsForeground(clientProfile.getProcessId())));
    }

    @com.android.internal.annotations.VisibleForTesting
    protected boolean requestDescramblerInternal(android.media.tv.tunerresourcemanager.TunerDescramblerRequest tunerDescramblerRequest, int[] iArr) {
        if (DEBUG) {
            android.util.Slog.d(TAG, "requestDescrambler(request=" + tunerDescramblerRequest + ")");
        }
        iArr[0] = generateResourceHandle(2, 0);
        return true;
    }

    private long getElapsedTime(long j) {
        long uptimeMillis = android.os.SystemClock.uptimeMillis();
        if (uptimeMillis >= j) {
            return uptimeMillis - j;
        }
        long j2 = uptimeMillis + (com.android.server.job.controllers.JobStatus.NO_LATEST_RUNTIME - j);
        if (j2 < 0) {
            return com.android.server.job.controllers.JobStatus.NO_LATEST_RUNTIME;
        }
        return j2;
    }

    private boolean lockForTunerApiLock(int i, long j, java.lang.String str) {
        try {
            if (this.mLockForTRMSLock.tryLock(j, java.util.concurrent.TimeUnit.MILLISECONDS)) {
                return true;
            }
            android.util.Slog.e(TAG, "FAILED to lock mLockForTRMSLock in " + str + ", clientId:" + i + ", timeoutMS:" + j + ", mTunerApiLockHolder:" + this.mTunerApiLockHolder);
            return false;
        } catch (java.lang.InterruptedException e) {
            android.util.Slog.e(TAG, "exception thrown in " + str + ":" + e);
            if (this.mLockForTRMSLock.isHeldByCurrentThread()) {
                this.mLockForTRMSLock.unlock();
            }
            return false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean acquireLockInternal(int i, long j, long j2) {
        boolean z;
        boolean z2;
        boolean z3;
        long uptimeMillis = android.os.SystemClock.uptimeMillis();
        try {
            if (!lockForTunerApiLock(i, j2, "acquireLockInternal()")) {
                return false;
            }
            try {
                boolean z4 = this.mTunerApiLockHolder == -1;
                boolean z5 = i == this.mTunerApiLockHolder && j == this.mTunerApiLockHolderThreadId;
                while (!z4 && !z5) {
                    z = z5;
                    long elapsedTime = j2 - getElapsedTime(uptimeMillis);
                    if (elapsedTime <= 0) {
                        android.util.Slog.e(TAG, "FAILED:acquireLockInternal(" + i + ", " + j + ", " + j2 + ") - timed out, but will grant the lock to the callee by stealing it from the current holder:" + this.mTunerApiLockHolder + "(" + this.mTunerApiLockHolderThreadId + "), who likely failed to call releaseLock(), to prevent this from becoming an unrecoverable error");
                        z2 = true;
                        break;
                    }
                    this.mTunerApiLockReleasedCV.await(elapsedTime, java.util.concurrent.TimeUnit.MILLISECONDS);
                    z4 = this.mTunerApiLockHolder == -1;
                    if (!z4) {
                        android.util.Slog.w(TAG, "acquireLockInternal(" + i + ", " + j + ", " + j2 + ") - woken up from cond wait, but " + this.mTunerApiLockHolder + "(" + this.mTunerApiLockHolderThreadId + ") is already holding the lock. Going to wait again if timeout hasn't reached yet");
                    }
                    z5 = z;
                }
                z = z5;
                z2 = false;
                if (z4 || z2) {
                    if (DEBUG) {
                        android.util.Slog.d(TAG, "SUCCESS:acquireLockInternal(" + i + ", " + j + ", " + j2 + ")");
                    }
                    if (this.mTunerApiLockNestedCount != 0) {
                        android.util.Slog.w(TAG, "Something is wrong as nestedCount(" + this.mTunerApiLockNestedCount + ") is not zero. Will overriding it to 1 anyways");
                    }
                    this.mTunerApiLockHolder = i;
                    this.mTunerApiLockHolderThreadId = j;
                    z3 = true;
                    this.mTunerApiLockNestedCount = 1;
                } else if (z) {
                    this.mTunerApiLockNestedCount++;
                    if (DEBUG) {
                        android.util.Slog.d(TAG, "acquireLockInternal(" + i + ", " + j + ", " + j2 + ") - nested count incremented to " + this.mTunerApiLockNestedCount);
                        z3 = true;
                    } else {
                        z3 = true;
                    }
                } else {
                    android.util.Slog.e(TAG, "acquireLockInternal(" + i + ", " + j + ", " + j2 + ") - should not reach here");
                    z3 = true;
                }
                boolean z6 = (z4 || z || z2) ? z3 : false;
                if (this.mLockForTRMSLock.isHeldByCurrentThread()) {
                    this.mLockForTRMSLock.unlock();
                }
                return z6;
            } catch (java.lang.InterruptedException e) {
                android.util.Slog.e(TAG, "exception thrown in acquireLockInternal(" + i + ", " + j + ", " + j2 + "):" + e);
                if (!this.mLockForTRMSLock.isHeldByCurrentThread()) {
                    return false;
                }
                this.mLockForTRMSLock.unlock();
                return false;
            }
        } catch (java.lang.Throwable th) {
            if (this.mLockForTRMSLock.isHeldByCurrentThread()) {
                this.mLockForTRMSLock.unlock();
            }
            throw th;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean releaseLockInternal(int i, long j, boolean z, boolean z2) {
        if (!lockForTunerApiLock(i, j, "releaseLockInternal()")) {
            return false;
        }
        try {
            if (this.mTunerApiLockHolder != i) {
                if (this.mTunerApiLockHolder == -1) {
                    if (!z2) {
                        android.util.Slog.w(TAG, "releaseLockInternal(" + i + ", " + j + ") - called while there is no current holder");
                    }
                    if (this.mLockForTRMSLock.isHeldByCurrentThread()) {
                        this.mLockForTRMSLock.unlock();
                    }
                    return false;
                }
                if (!z2) {
                    android.util.Slog.e(TAG, "releaseLockInternal(" + i + ", " + j + ") - called while someone else:" + this.mTunerApiLockHolder + "is the current holder");
                }
                if (this.mLockForTRMSLock.isHeldByCurrentThread()) {
                    this.mLockForTRMSLock.unlock();
                }
                return false;
            }
            this.mTunerApiLockNestedCount--;
            if (z || this.mTunerApiLockNestedCount <= 0) {
                if (DEBUG) {
                    android.util.Slog.d(TAG, "SUCCESS:releaseLockInternal(" + i + ", " + j + ", " + z + ", " + z2 + ") - signaling!");
                }
                this.mTunerApiLockHolder = -1;
                this.mTunerApiLockHolderThreadId = -1L;
                this.mTunerApiLockNestedCount = 0;
                this.mTunerApiLockReleasedCV.signal();
            } else if (DEBUG) {
                android.util.Slog.d(TAG, "releaseLockInternal(" + i + ", " + j + ", " + z + ", " + z2 + ") - NOT signaling because nested count is not zero (" + this.mTunerApiLockNestedCount + ")");
            }
            if (this.mLockForTRMSLock.isHeldByCurrentThread()) {
                this.mLockForTRMSLock.unlock();
            }
            return true;
        } catch (java.lang.Throwable th) {
            if (this.mLockForTRMSLock.isHeldByCurrentThread()) {
                this.mLockForTRMSLock.unlock();
            }
            throw th;
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    protected class ResourcesReclaimListenerRecord implements android.os.IBinder.DeathRecipient {
        private final int mClientId;
        private final android.media.tv.tunerresourcemanager.IResourcesReclaimListener mListener;

        public ResourcesReclaimListenerRecord(android.media.tv.tunerresourcemanager.IResourcesReclaimListener iResourcesReclaimListener, int i) {
            this.mListener = iResourcesReclaimListener;
            this.mClientId = i;
        }

        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
            try {
                synchronized (com.android.server.tv.tunerresourcemanager.TunerResourceManagerService.this.mLock) {
                    try {
                        if (com.android.server.tv.tunerresourcemanager.TunerResourceManagerService.this.checkClientExists(this.mClientId)) {
                            com.android.server.tv.tunerresourcemanager.TunerResourceManagerService.this.removeClientProfile(this.mClientId);
                        }
                    } finally {
                    }
                }
            } finally {
                com.android.server.tv.tunerresourcemanager.TunerResourceManagerService.this.releaseLockInternal(this.mClientId, 500L, true, true);
            }
        }

        public int getId() {
            return this.mClientId;
        }

        public android.media.tv.tunerresourcemanager.IResourcesReclaimListener getListener() {
            return this.mListener;
        }
    }

    private void addResourcesReclaimListener(int i, android.media.tv.tunerresourcemanager.IResourcesReclaimListener iResourcesReclaimListener) {
        if (iResourcesReclaimListener == null) {
            if (DEBUG) {
                android.util.Slog.w(TAG, "Listener is null when client " + i + " registered!");
                return;
            }
            return;
        }
        com.android.server.tv.tunerresourcemanager.TunerResourceManagerService.ResourcesReclaimListenerRecord resourcesReclaimListenerRecord = new com.android.server.tv.tunerresourcemanager.TunerResourceManagerService.ResourcesReclaimListenerRecord(iResourcesReclaimListener, i);
        try {
            iResourcesReclaimListener.asBinder().linkToDeath(resourcesReclaimListenerRecord, 0);
            this.mListeners.put(java.lang.Integer.valueOf(i), resourcesReclaimListenerRecord);
        } catch (android.os.RemoteException e) {
            android.util.Slog.w(TAG, "Listener already died.");
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    protected boolean reclaimResource(int i, int i2) {
        android.os.Binder.allowBlockingForCurrentThread();
        com.android.server.tv.tunerresourcemanager.ClientProfile clientProfile = getClientProfile(i);
        if (clientProfile == null) {
            return true;
        }
        java.util.Iterator<java.lang.Integer> it = clientProfile.getShareFeClientIds().iterator();
        while (it.hasNext()) {
            int intValue = it.next().intValue();
            try {
                this.mListeners.get(java.lang.Integer.valueOf(intValue)).getListener().onReclaimResources();
                clearAllResourcesAndClientMapping(getClientProfile(intValue));
            } catch (android.os.RemoteException e) {
                android.util.Slog.e(TAG, "Failed to reclaim resources on client " + intValue, e);
                return false;
            }
        }
        if (DEBUG) {
            android.util.Slog.d(TAG, "Reclaiming resources because higher priority client request resource type " + i2 + ", clientId:" + i);
        }
        try {
            this.mListeners.get(java.lang.Integer.valueOf(i)).getListener().onReclaimResources();
            clearAllResourcesAndClientMapping(clientProfile);
            return true;
        } catch (android.os.RemoteException e2) {
            android.util.Slog.e(TAG, "Failed to reclaim resources on client " + i, e2);
            return false;
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    protected int getClientPriority(int i, boolean z) {
        if (DEBUG) {
            android.util.Slog.d(TAG, "getClientPriority useCase=" + i + ", isForeground=" + z + ")");
        }
        if (z) {
            return this.mPriorityCongfig.getForegroundPriority(i);
        }
        return this.mPriorityCongfig.getBackgroundPriority(i);
    }

    @com.android.internal.annotations.VisibleForTesting
    protected boolean checkIsForeground(int i) {
        java.util.List<android.app.ActivityManager.RunningAppProcessInfo> runningAppProcesses;
        if (this.mActivityManager == null || (runningAppProcesses = this.mActivityManager.getRunningAppProcesses()) == null) {
            return false;
        }
        for (android.app.ActivityManager.RunningAppProcessInfo runningAppProcessInfo : runningAppProcesses) {
            if (runningAppProcessInfo.pid == i && runningAppProcessInfo.importance == 100) {
                return true;
            }
        }
        return false;
    }

    private void updateFrontendClientMappingOnNewGrant(int i, int i2) {
        com.android.server.tv.tunerresourcemanager.FrontendResource frontendResource = getFrontendResource(i);
        com.android.server.tv.tunerresourcemanager.ClientProfile clientProfile = getClientProfile(i2);
        frontendResource.setOwner(i2);
        increFrontendNum(this.mFrontendUsedNums, frontendResource.getType());
        clientProfile.useFrontend(i);
        java.util.Iterator<java.lang.Integer> it = frontendResource.getExclusiveGroupMemberFeHandles().iterator();
        while (it.hasNext()) {
            int intValue = it.next().intValue();
            getFrontendResource(intValue).setOwner(i2);
            clientProfile.useFrontend(intValue);
        }
        clientProfile.setPrimaryFrontend(i);
    }

    private void updateDemuxClientMappingOnNewGrant(int i, int i2) {
        com.android.server.tv.tunerresourcemanager.DemuxResource demuxResource = getDemuxResource(i);
        if (demuxResource != null) {
            com.android.server.tv.tunerresourcemanager.ClientProfile clientProfile = getClientProfile(i2);
            demuxResource.setOwner(i2);
            clientProfile.useDemux(i);
        }
    }

    private void updateDemuxClientMappingOnRelease(@android.annotation.NonNull com.android.server.tv.tunerresourcemanager.DemuxResource demuxResource) {
        com.android.server.tv.tunerresourcemanager.ClientProfile clientProfile = getClientProfile(demuxResource.getOwnerClientId());
        demuxResource.removeOwner();
        clientProfile.releaseDemux(demuxResource.getHandle());
    }

    private void updateLnbClientMappingOnNewGrant(int i, int i2) {
        com.android.server.tv.tunerresourcemanager.LnbResource lnbResource = getLnbResource(i);
        com.android.server.tv.tunerresourcemanager.ClientProfile clientProfile = getClientProfile(i2);
        lnbResource.setOwner(i2);
        clientProfile.useLnb(i);
    }

    private void updateLnbClientMappingOnRelease(@android.annotation.NonNull com.android.server.tv.tunerresourcemanager.LnbResource lnbResource) {
        com.android.server.tv.tunerresourcemanager.ClientProfile clientProfile = getClientProfile(lnbResource.getOwnerClientId());
        lnbResource.removeOwner();
        clientProfile.releaseLnb(lnbResource.getHandle());
    }

    private void updateCasClientMappingOnNewGrant(int i, int i2) {
        com.android.server.tv.tunerresourcemanager.CasResource casResource = getCasResource(i);
        com.android.server.tv.tunerresourcemanager.ClientProfile clientProfile = getClientProfile(i2);
        casResource.setOwner(i2);
        clientProfile.useCas(i);
    }

    private void updateCiCamClientMappingOnNewGrant(int i, int i2) {
        com.android.server.tv.tunerresourcemanager.CiCamResource ciCamResource = getCiCamResource(i);
        com.android.server.tv.tunerresourcemanager.ClientProfile clientProfile = getClientProfile(i2);
        ciCamResource.setOwner(i2);
        clientProfile.useCiCam(i);
    }

    private void updateCasClientMappingOnRelease(@android.annotation.NonNull com.android.server.tv.tunerresourcemanager.CasResource casResource, int i) {
        com.android.server.tv.tunerresourcemanager.ClientProfile clientProfile = getClientProfile(i);
        casResource.removeOwner(i);
        clientProfile.releaseCas();
    }

    private void updateCiCamClientMappingOnRelease(@android.annotation.NonNull com.android.server.tv.tunerresourcemanager.CiCamResource ciCamResource, int i) {
        com.android.server.tv.tunerresourcemanager.ClientProfile clientProfile = getClientProfile(i);
        ciCamResource.removeOwner(i);
        clientProfile.releaseCiCam();
    }

    private int updateAndGetOwnerClientPriority(int i) {
        com.android.server.tv.tunerresourcemanager.ClientProfile clientProfile = getClientProfile(i);
        clientPriorityUpdateOnRequest(clientProfile);
        return clientProfile.getPriority();
    }

    private int getFrontendHighestClientPriority(int i) {
        com.android.server.tv.tunerresourcemanager.ClientProfile clientProfile = getClientProfile(i);
        if (clientProfile == null) {
            return 0;
        }
        int updateAndGetOwnerClientPriority = updateAndGetOwnerClientPriority(i);
        java.util.Iterator<java.lang.Integer> it = clientProfile.getShareFeClientIds().iterator();
        while (it.hasNext()) {
            int updateAndGetOwnerClientPriority2 = updateAndGetOwnerClientPriority(it.next().intValue());
            if (updateAndGetOwnerClientPriority2 > updateAndGetOwnerClientPriority) {
                updateAndGetOwnerClientPriority = updateAndGetOwnerClientPriority2;
            }
        }
        return updateAndGetOwnerClientPriority;
    }

    @com.android.internal.annotations.VisibleForTesting
    @android.annotation.Nullable
    protected com.android.server.tv.tunerresourcemanager.FrontendResource getFrontendResource(int i) {
        return this.mFrontendResources.get(java.lang.Integer.valueOf(i));
    }

    @com.android.internal.annotations.VisibleForTesting
    protected java.util.Map<java.lang.Integer, com.android.server.tv.tunerresourcemanager.FrontendResource> getFrontendResources() {
        return this.mFrontendResources;
    }

    @com.android.internal.annotations.VisibleForTesting
    @android.annotation.Nullable
    protected com.android.server.tv.tunerresourcemanager.DemuxResource getDemuxResource(int i) {
        return this.mDemuxResources.get(java.lang.Integer.valueOf(i));
    }

    @com.android.internal.annotations.VisibleForTesting
    protected java.util.Map<java.lang.Integer, com.android.server.tv.tunerresourcemanager.DemuxResource> getDemuxResources() {
        return this.mDemuxResources;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean setMaxNumberOfFrontendsInternal(int i, int i2) {
        int i3 = this.mFrontendUsedNums.get(i, -1);
        if (i3 == -1 || i3 <= i2) {
            this.mFrontendMaxUsableNums.put(i, i2);
            return true;
        }
        android.util.Slog.e(TAG, "max number of frontend for frontendType: " + i + " cannot be set to a value lower than the current usage count. (requested max num = " + i2 + ", current usage = " + i3);
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getMaxNumberOfFrontendsInternal(int i) {
        int i2 = this.mFrontendExistingNums.get(i, -1);
        if (i2 == -1) {
            android.util.Log.e(TAG, "existingNum is -1 for " + i);
            return -1;
        }
        int i3 = this.mFrontendMaxUsableNums.get(i, -1);
        if (i3 == -1) {
            return i2;
        }
        return i3;
    }

    private boolean isFrontendMaxNumUseReached(int i) {
        int i2 = this.mFrontendMaxUsableNums.get(i, -1);
        if (i2 == -1) {
            return false;
        }
        int i3 = this.mFrontendUsedNums.get(i, -1);
        if (i3 == -1) {
            i3 = 0;
        }
        return i3 >= i2;
    }

    private void increFrontendNum(android.util.SparseIntArray sparseIntArray, int i) {
        int i2 = sparseIntArray.get(i, -1);
        if (i2 == -1) {
            sparseIntArray.put(i, 1);
        } else {
            sparseIntArray.put(i, i2 + 1);
        }
    }

    private void decreFrontendNum(android.util.SparseIntArray sparseIntArray, int i) {
        int i2 = sparseIntArray.get(i, -1);
        if (i2 != -1) {
            sparseIntArray.put(i, i2 - 1);
        }
    }

    private void replaceFeResourceMap(java.util.Map<java.lang.Integer, com.android.server.tv.tunerresourcemanager.FrontendResource> map, java.util.Map<java.lang.Integer, com.android.server.tv.tunerresourcemanager.FrontendResource> map2) {
        if (map2 != null) {
            map2.clear();
            if (map != null && map.size() > 0) {
                map2.putAll(map);
            }
        }
    }

    private void replaceFeCounts(android.util.SparseIntArray sparseIntArray, android.util.SparseIntArray sparseIntArray2) {
        if (sparseIntArray2 != null) {
            sparseIntArray2.clear();
            if (sparseIntArray != null) {
                for (int i = 0; i < sparseIntArray.size(); i++) {
                    sparseIntArray2.put(sparseIntArray.keyAt(i), sparseIntArray.valueAt(i));
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dumpMap(java.util.Map<?, ?> map, java.lang.String str, java.lang.String str2, android.util.IndentingPrintWriter indentingPrintWriter) {
        if (map != null) {
            indentingPrintWriter.println(str);
            indentingPrintWriter.increaseIndent();
            for (java.util.Map.Entry<?, ?> entry : map.entrySet()) {
                indentingPrintWriter.print(entry.getKey() + " : " + entry.getValue());
                indentingPrintWriter.print(str2);
            }
            indentingPrintWriter.println();
            indentingPrintWriter.decreaseIndent();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dumpSIA(android.util.SparseIntArray sparseIntArray, java.lang.String str, java.lang.String str2, android.util.IndentingPrintWriter indentingPrintWriter) {
        if (sparseIntArray != null) {
            indentingPrintWriter.println(str);
            indentingPrintWriter.increaseIndent();
            for (int i = 0; i < sparseIntArray.size(); i++) {
                indentingPrintWriter.print(sparseIntArray.keyAt(i) + " : " + sparseIntArray.valueAt(i));
                indentingPrintWriter.print(str2);
            }
            indentingPrintWriter.println();
            indentingPrintWriter.decreaseIndent();
        }
    }

    private void addFrontendResource(com.android.server.tv.tunerresourcemanager.FrontendResource frontendResource) {
        java.util.Iterator<com.android.server.tv.tunerresourcemanager.FrontendResource> it = getFrontendResources().values().iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            com.android.server.tv.tunerresourcemanager.FrontendResource next = it.next();
            if (next.getExclusiveGroupId() == frontendResource.getExclusiveGroupId()) {
                frontendResource.addExclusiveGroupMemberFeHandle(next.getHandle());
                frontendResource.addExclusiveGroupMemberFeHandles(next.getExclusiveGroupMemberFeHandles());
                java.util.Iterator<java.lang.Integer> it2 = next.getExclusiveGroupMemberFeHandles().iterator();
                while (it2.hasNext()) {
                    getFrontendResource(it2.next().intValue()).addExclusiveGroupMemberFeHandle(frontendResource.getHandle());
                }
                next.addExclusiveGroupMemberFeHandle(frontendResource.getHandle());
            }
        }
        this.mFrontendResources.put(java.lang.Integer.valueOf(frontendResource.getHandle()), frontendResource);
        increFrontendNum(this.mFrontendExistingNums, frontendResource.getType());
    }

    private void addDemuxResource(com.android.server.tv.tunerresourcemanager.DemuxResource demuxResource) {
        this.mDemuxResources.put(java.lang.Integer.valueOf(demuxResource.getHandle()), demuxResource);
    }

    private void removeFrontendResource(int i) {
        com.android.server.tv.tunerresourcemanager.FrontendResource frontendResource = getFrontendResource(i);
        if (frontendResource == null) {
            return;
        }
        if (frontendResource.isInUse()) {
            com.android.server.tv.tunerresourcemanager.ClientProfile clientProfile = getClientProfile(frontendResource.getOwnerClientId());
            java.util.Iterator<java.lang.Integer> it = clientProfile.getShareFeClientIds().iterator();
            while (it.hasNext()) {
                clearFrontendAndClientMapping(getClientProfile(it.next().intValue()));
            }
            clearFrontendAndClientMapping(clientProfile);
        }
        java.util.Iterator<java.lang.Integer> it2 = frontendResource.getExclusiveGroupMemberFeHandles().iterator();
        while (it2.hasNext()) {
            getFrontendResource(it2.next().intValue()).removeExclusiveGroupMemberFeId(frontendResource.getHandle());
        }
        decreFrontendNum(this.mFrontendExistingNums, frontendResource.getType());
        this.mFrontendResources.remove(java.lang.Integer.valueOf(i));
    }

    private void removeDemuxResource(int i) {
        com.android.server.tv.tunerresourcemanager.DemuxResource demuxResource = getDemuxResource(i);
        if (demuxResource == null) {
            return;
        }
        if (demuxResource.isInUse()) {
            releaseDemuxInternal(demuxResource);
        }
        this.mDemuxResources.remove(java.lang.Integer.valueOf(i));
    }

    @com.android.internal.annotations.VisibleForTesting
    @android.annotation.Nullable
    protected com.android.server.tv.tunerresourcemanager.LnbResource getLnbResource(int i) {
        return this.mLnbResources.get(java.lang.Integer.valueOf(i));
    }

    @com.android.internal.annotations.VisibleForTesting
    protected java.util.Map<java.lang.Integer, com.android.server.tv.tunerresourcemanager.LnbResource> getLnbResources() {
        return this.mLnbResources;
    }

    private void addLnbResource(com.android.server.tv.tunerresourcemanager.LnbResource lnbResource) {
        this.mLnbResources.put(java.lang.Integer.valueOf(lnbResource.getHandle()), lnbResource);
    }

    private void removeLnbResource(int i) {
        com.android.server.tv.tunerresourcemanager.LnbResource lnbResource = getLnbResource(i);
        if (lnbResource == null) {
            return;
        }
        if (lnbResource.isInUse()) {
            releaseLnbInternal(lnbResource);
        }
        this.mLnbResources.remove(java.lang.Integer.valueOf(i));
    }

    @com.android.internal.annotations.VisibleForTesting
    @android.annotation.Nullable
    protected com.android.server.tv.tunerresourcemanager.CasResource getCasResource(int i) {
        return this.mCasResources.get(java.lang.Integer.valueOf(i));
    }

    @com.android.internal.annotations.VisibleForTesting
    @android.annotation.Nullable
    protected com.android.server.tv.tunerresourcemanager.CiCamResource getCiCamResource(int i) {
        return this.mCiCamResources.get(java.lang.Integer.valueOf(i));
    }

    @com.android.internal.annotations.VisibleForTesting
    protected java.util.Map<java.lang.Integer, com.android.server.tv.tunerresourcemanager.CasResource> getCasResources() {
        return this.mCasResources;
    }

    @com.android.internal.annotations.VisibleForTesting
    protected java.util.Map<java.lang.Integer, com.android.server.tv.tunerresourcemanager.CiCamResource> getCiCamResources() {
        return this.mCiCamResources;
    }

    private void addCasResource(com.android.server.tv.tunerresourcemanager.CasResource casResource) {
        this.mCasResources.put(java.lang.Integer.valueOf(casResource.getSystemId()), casResource);
    }

    private void addCiCamResource(com.android.server.tv.tunerresourcemanager.CiCamResource ciCamResource) {
        this.mCiCamResources.put(java.lang.Integer.valueOf(ciCamResource.getCiCamId()), ciCamResource);
    }

    private void removeCasResource(int i) {
        com.android.server.tv.tunerresourcemanager.CasResource casResource = getCasResource(i);
        if (casResource == null) {
            return;
        }
        java.util.Iterator<java.lang.Integer> it = casResource.getOwnerClientIds().iterator();
        while (it.hasNext()) {
            getClientProfile(it.next().intValue()).releaseCas();
        }
        this.mCasResources.remove(java.lang.Integer.valueOf(i));
    }

    private void removeCiCamResource(int i) {
        com.android.server.tv.tunerresourcemanager.CiCamResource ciCamResource = getCiCamResource(i);
        if (ciCamResource == null) {
            return;
        }
        java.util.Iterator<java.lang.Integer> it = ciCamResource.getOwnerClientIds().iterator();
        while (it.hasNext()) {
            getClientProfile(it.next().intValue()).releaseCiCam();
        }
        this.mCiCamResources.remove(java.lang.Integer.valueOf(i));
    }

    private void releaseLowerPriorityClientCasResources(int i) {
    }

    @com.android.internal.annotations.VisibleForTesting
    @android.annotation.Nullable
    protected com.android.server.tv.tunerresourcemanager.ClientProfile getClientProfile(int i) {
        return this.mClientProfiles.get(java.lang.Integer.valueOf(i));
    }

    private void addClientProfile(int i, com.android.server.tv.tunerresourcemanager.ClientProfile clientProfile, android.media.tv.tunerresourcemanager.IResourcesReclaimListener iResourcesReclaimListener) {
        this.mClientProfiles.put(java.lang.Integer.valueOf(i), clientProfile);
        addResourcesReclaimListener(i, iResourcesReclaimListener);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void removeClientProfile(int i) {
        java.util.Iterator<java.lang.Integer> it = getClientProfile(i).getShareFeClientIds().iterator();
        while (it.hasNext()) {
            clearFrontendAndClientMapping(getClientProfile(it.next().intValue()));
        }
        clearAllResourcesAndClientMapping(getClientProfile(i));
        this.mClientProfiles.remove(java.lang.Integer.valueOf(i));
        synchronized (this.mLock) {
            try {
                com.android.server.tv.tunerresourcemanager.TunerResourceManagerService.ResourcesReclaimListenerRecord remove = this.mListeners.remove(java.lang.Integer.valueOf(i));
                if (remove != null) {
                    remove.getListener().asBinder().unlinkToDeath(remove, 0);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private void clearFrontendAndClientMapping(com.android.server.tv.tunerresourcemanager.ClientProfile clientProfile) {
        com.android.server.tv.tunerresourcemanager.FrontendResource frontendResource;
        if (clientProfile == null) {
            return;
        }
        java.util.Iterator<java.lang.Integer> it = clientProfile.getInUseFrontendHandles().iterator();
        while (it.hasNext()) {
            com.android.server.tv.tunerresourcemanager.FrontendResource frontendResource2 = getFrontendResource(it.next().intValue());
            int ownerClientId = frontendResource2.getOwnerClientId();
            if (ownerClientId == clientProfile.getId()) {
                frontendResource2.removeOwner();
            } else {
                com.android.server.tv.tunerresourcemanager.ClientProfile clientProfile2 = getClientProfile(ownerClientId);
                if (clientProfile2 != null) {
                    clientProfile2.stopSharingFrontend(clientProfile.getId());
                }
            }
        }
        int primaryFrontend = clientProfile.getPrimaryFrontend();
        if (primaryFrontend != -1 && (frontendResource = getFrontendResource(primaryFrontend)) != null) {
            decreFrontendNum(this.mFrontendUsedNums, frontendResource.getType());
        }
        clientProfile.releaseFrontend();
    }

    private void clearAllResourcesAndClientMapping(com.android.server.tv.tunerresourcemanager.ClientProfile clientProfile) {
        if (clientProfile == null) {
            return;
        }
        java.util.Iterator<java.lang.Integer> it = clientProfile.getInUseLnbHandles().iterator();
        while (it.hasNext()) {
            getLnbResource(it.next().intValue()).removeOwner();
        }
        if (clientProfile.getInUseCasSystemId() != -1) {
            getCasResource(clientProfile.getInUseCasSystemId()).removeOwner(clientProfile.getId());
        }
        if (clientProfile.getInUseCiCamId() != -1) {
            getCiCamResource(clientProfile.getInUseCiCamId()).removeOwner(clientProfile.getId());
        }
        java.util.Iterator<java.lang.Integer> it2 = clientProfile.getInUseDemuxHandles().iterator();
        while (it2.hasNext()) {
            getDemuxResource(it2.next().intValue()).removeOwner();
        }
        clearFrontendAndClientMapping(clientProfile);
        clientProfile.reclaimAllResources();
    }

    @com.android.internal.annotations.VisibleForTesting
    protected boolean checkClientExists(int i) {
        return this.mClientProfiles.keySet().contains(java.lang.Integer.valueOf(i));
    }

    private int generateResourceHandle(int i, int i2) {
        int i3 = ((i & 255) << 24) | (i2 << 16);
        int i4 = this.mResourceRequestCount;
        this.mResourceRequestCount = i4 + 1;
        return i3 | (i4 & com.android.server.location.gnss.hal.GnssNative.GNSS_AIDING_TYPE_ALL);
    }

    @com.android.internal.annotations.VisibleForTesting
    protected int getResourceIdFromHandle(int i) {
        if (i == -1) {
            return i;
        }
        return (i & 16711680) >> 16;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean validateResourceHandle(int i, int i2) {
        if (i2 == -1 || ((i2 & android.hardware.audio.common.V2_0.AudioFormat.MAIN_MASK) >> 24) != i) {
            return false;
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void enforceTrmAccessPermission(java.lang.String str) {
        getContext().enforceCallingOrSelfPermission("android.permission.TUNER_RESOURCE_ACCESS", "TunerResourceManagerService: " + str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void enforceTunerAccessPermission(java.lang.String str) {
        getContext().enforceCallingPermission("android.permission.ACCESS_TV_TUNER", "TunerResourceManagerService: " + str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void enforceDescramblerAccessPermission(java.lang.String str) {
        getContext().enforceCallingPermission("android.permission.ACCESS_TV_DESCRAMBLER", "TunerResourceManagerService: " + str);
    }
}
