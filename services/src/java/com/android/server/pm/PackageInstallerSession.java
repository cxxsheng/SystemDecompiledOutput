package com.android.server.pm;

/* loaded from: classes2.dex */
public class PackageInstallerSession extends android.content.pm.IPackageInstallerSession.Stub {
    private static final java.lang.String APEX_FILE_EXTENSION = ".apex";
    static final int APP_METADATA_FILE_ACCESS_MODE = 416;
    private static final java.lang.String ATTR_ABI_OVERRIDE = "abiOverride";
    private static final java.lang.String ATTR_APPLICATION_ENABLED_SETTING_PERSISTENT = "applicationEnabledSettingPersistent";

    @java.lang.Deprecated
    private static final java.lang.String ATTR_APP_ICON = "appIcon";
    private static final java.lang.String ATTR_APP_LABEL = "appLabel";
    private static final java.lang.String ATTR_APP_PACKAGE_NAME = "appPackageName";
    private static final java.lang.String ATTR_CHECKSUM_KIND = "checksumKind";
    private static final java.lang.String ATTR_CHECKSUM_VALUE = "checksumValue";
    private static final java.lang.String ATTR_COMMITTED = "committed";
    private static final java.lang.String ATTR_COMMITTED_MILLIS = "committedMillis";
    private static final java.lang.String ATTR_CREATED_MILLIS = "createdMillis";
    private static final java.lang.String ATTR_DATALOADER_ARGUMENTS = "dataLoaderArguments";
    private static final java.lang.String ATTR_DATALOADER_CLASS_NAME = "dataLoaderClassName";
    private static final java.lang.String ATTR_DATALOADER_PACKAGE_NAME = "dataLoaderPackageName";
    private static final java.lang.String ATTR_DATALOADER_TYPE = "dataLoaderType";
    private static final java.lang.String ATTR_DESTROYED = "destroyed";
    private static final java.lang.String ATTR_DOMAIN = "domain";
    private static final java.lang.String ATTR_INITIATING_PACKAGE_NAME = "installInitiatingPackageName";
    private static final java.lang.String ATTR_INSTALLER_ATTRIBUTION_TAG = "installerAttributionTag";
    private static final java.lang.String ATTR_INSTALLER_PACKAGE_NAME = "installerPackageName";
    private static final java.lang.String ATTR_INSTALLER_PACKAGE_UID = "installerPackageUid";
    private static final java.lang.String ATTR_INSTALLER_UID = "installerUid";
    private static final java.lang.String ATTR_INSTALL_FLAGS = "installFlags";
    private static final java.lang.String ATTR_INSTALL_LOCATION = "installLocation";
    private static final java.lang.String ATTR_INSTALL_REASON = "installRason";
    private static final java.lang.String ATTR_IS_APPLIED = "isApplied";
    private static final java.lang.String ATTR_IS_DATALOADER = "isDataLoader";
    private static final java.lang.String ATTR_IS_FAILED = "isFailed";
    private static final java.lang.String ATTR_IS_READY = "isReady";
    private static final java.lang.String ATTR_LENGTH_BYTES = "lengthBytes";
    private static final java.lang.String ATTR_LOCATION = "location";
    private static final java.lang.String ATTR_METADATA = "metadata";
    private static final java.lang.String ATTR_MODE = "mode";
    private static final java.lang.String ATTR_MULTI_PACKAGE = "multiPackage";
    private static final java.lang.String ATTR_NAME = "name";
    private static final java.lang.String ATTR_ORIGINATING_PACKAGE_NAME = "installOriginatingPackageName";
    private static final java.lang.String ATTR_ORIGINATING_UID = "originatingUid";
    private static final java.lang.String ATTR_ORIGINATING_URI = "originatingUri";
    private static final java.lang.String ATTR_PACKAGE_SOURCE = "packageSource";
    private static final java.lang.String ATTR_PARENT_SESSION_ID = "parentSessionId";
    private static final java.lang.String ATTR_PREPARED = "prepared";
    private static final java.lang.String ATTR_REFERRER_URI = "referrerUri";
    private static final java.lang.String ATTR_SEALED = "sealed";
    private static final java.lang.String ATTR_SESSION_ERROR_CODE = "errorCode";
    private static final java.lang.String ATTR_SESSION_ERROR_MESSAGE = "errorMessage";
    private static final java.lang.String ATTR_SESSION_ID = "sessionId";
    private static final java.lang.String ATTR_SESSION_STAGE_CID = "sessionStageCid";
    private static final java.lang.String ATTR_SESSION_STAGE_DIR = "sessionStageDir";
    private static final java.lang.String ATTR_SIGNATURE = "signature";
    private static final java.lang.String ATTR_SIZE_BYTES = "sizeBytes";
    private static final java.lang.String ATTR_STAGED_SESSION = "stagedSession";
    private static final java.lang.String ATTR_UPDATED_MILLIS = "updatedMillis";
    private static final java.lang.String ATTR_UPDATE_OWNER_PACKAGE_NAME = "updateOwnererPackageName";
    private static final java.lang.String ATTR_USER_ID = "userId";
    private static final java.lang.String ATTR_VOLUME_UUID = "volumeUuid";
    private static final long DEFAULT_APP_METADATA_BYTE_SIZE_LIMIT = 32000;
    private static final long DEFAULT_PRE_VERIFIED_DOMAINS_COUNT_LIMIT = 1000;
    private static final long DEFAULT_PRE_VERIFIED_DOMAIN_LENGTH_LIMIT = 256;
    private static final int INCREMENTAL_STORAGE_BLOCKED_TIMEOUT_MS = 2000;
    private static final int INCREMENTAL_STORAGE_UNHEALTHY_MONITORING_MS = 60000;
    private static final int INCREMENTAL_STORAGE_UNHEALTHY_TIMEOUT_MS = 7000;
    private static final int INVALID_TARGET_SDK_VERSION = Integer.MAX_VALUE;
    private static final boolean LOGD = true;
    private static final int MSG_INSTALL = 3;
    private static final int MSG_ON_PACKAGE_INSTALLED = 4;
    private static final int MSG_ON_SESSION_SEALED = 1;
    private static final int MSG_PRE_APPROVAL_REQUEST = 6;
    private static final int MSG_SESSION_VALIDATION_FAILURE = 5;
    private static final int MSG_STREAM_VALIDATE_AND_COMMIT = 2;
    private static final long PRE_APPROVAL_WITH_UPDATE_OWNERSHIP_FIX = 293644536;
    private static final java.lang.String PROPERTY_APP_METADATA_BYTE_SIZE_LIMIT = "app_metadata_byte_size_limit";
    private static final java.lang.String PROPERTY_NAME_INHERIT_NATIVE = "pi.inherit_native_on_dont_kill";
    private static final java.lang.String PROPERTY_PRE_VERIFIED_DOMAINS_COUNT_LIMIT = "pre_verified_domains_count_limit";
    private static final java.lang.String PROPERTY_PRE_VERIFIED_DOMAIN_LENGTH_LIMIT = "pre_verified_domain_length_limit";
    private static final java.lang.String REMOVE_MARKER_EXTENSION = ".removed";
    private static final long SILENT_INSTALL_ALLOWED = 265131695;
    private static final java.lang.String SYSTEM_DATA_LOADER_PACKAGE = "android";
    private static final java.lang.String TAG = "PackageInstallerSession";
    private static final java.lang.String TAG_AUTO_REVOKE_PERMISSIONS_MODE = "auto-revoke-permissions-mode";
    static final java.lang.String TAG_CHILD_SESSION = "childSession";
    private static final java.lang.String TAG_DENY_PERMISSION = "deny-permission";
    private static final java.lang.String TAG_GRANTED_RUNTIME_PERMISSION = "granted-runtime-permission";
    private static final java.lang.String TAG_GRANT_PERMISSION = "grant-permission";
    static final java.lang.String TAG_PRE_VERIFIED_DOMAINS = "preVerifiedDomains";
    static final java.lang.String TAG_SESSION = "session";
    static final java.lang.String TAG_SESSION_CHECKSUM = "sessionChecksum";
    static final java.lang.String TAG_SESSION_CHECKSUM_SIGNATURE = "sessionChecksumSignature";
    static final java.lang.String TAG_SESSION_FILE = "sessionFile";
    private static final java.lang.String TAG_WHITELISTED_RESTRICTED_PERMISSION = "whitelisted-restricted-permission";
    private static final long THROW_EXCEPTION_COMMIT_WITH_IMMUTABLE_PENDING_INTENT = 240618202;
    private static final int USER_ACTION_NOT_NEEDED = 0;
    private static final int USER_ACTION_PENDING_APK_PARSING = 2;
    private static final int USER_ACTION_REQUIRED = 1;
    private static final int USER_ACTION_REQUIRED_UPDATE_OWNER_REMINDER = 3;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private long committedMillis;
    final long createdMillis;
    private final com.android.server.pm.PackageInstallerService.InternalCallback mCallback;
    private final android.content.Context mContext;
    private volatile boolean mDestroyed;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private java.lang.String mFinalMessage;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private int mFinalStatus;
    private final android.os.Handler mHandler;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean mHasDeviceAdminReceiver;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private android.os.incremental.IncrementalFileStorages mIncrementalFileStorages;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private java.io.File mInheritedFilesBase;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private com.android.server.pm.InstallSource mInstallSource;
    private final com.android.server.pm.Installer mInstaller;
    private volatile int mInstallerUid;
    private final java.lang.String mOriginalInstallerPackageName;
    private final int mOriginalInstallerUid;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private android.content.pm.parsing.PackageLite mPackageLite;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private java.lang.String mPackageName;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private int mParentSessionId;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    @android.annotation.Nullable
    private java.lang.Runnable mPendingAbandonCallback;
    private final com.android.server.pm.PackageManagerService mPm;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private android.content.pm.verify.domain.DomainSet mPreVerifiedDomains;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private android.content.pm.PackageInstaller.PreapprovalDetails mPreapprovalDetails;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private android.content.IntentSender mPreapprovalRemoteStatusReceiver;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean mPrepared;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private android.content.IntentSender mRemoteStatusReceiver;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private java.io.File mResolvedBaseFile;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean mSessionApplied;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private int mSessionErrorCode;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private java.lang.String mSessionErrorMessage;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean mSessionFailed;
    private final com.android.server.pm.PackageSessionProvider mSessionProvider;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean mSessionReady;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean mShouldBeSealed;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private android.content.pm.SigningDetails mSigningDetails;
    private final com.android.server.pm.SilentUpdatePolicy mSilentUpdatePolicy;

    @android.annotation.Nullable
    final com.android.server.pm.PackageInstallerSession.StagedSession mStagedSession;
    private final com.android.server.pm.StagingManager mStagingManager;

    @android.annotation.Nullable
    private java.lang.Boolean mUserActionRequired;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private int mUserActionRequirement;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean mVerityFoundForApks;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private long mVersionCode;
    final android.content.pm.PackageInstaller.SessionParams params;
    final int sessionId;
    final java.lang.String stageCid;
    final java.io.File stageDir;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private long updatedMillis;
    final int userId;
    private static final int[] EMPTY_CHILD_SESSION_ARRAY = libcore.util.EmptyArray.INT;
    private static final android.content.pm.InstallationFile[] EMPTY_INSTALLATION_FILE_ARRAY = new android.content.pm.InstallationFile[0];
    private static final java.io.FileFilter sAddedApkFilter = new java.io.FileFilter() { // from class: com.android.server.pm.PackageInstallerSession.1
        @Override // java.io.FileFilter
        public boolean accept(java.io.File file) {
            return (file.isDirectory() || file.getName().endsWith(com.android.server.pm.PackageInstallerSession.REMOVE_MARKER_EXTENSION) || file.getName().endsWith(".idsig") || com.android.server.pm.PackageInstallerSession.isAppMetadata(file) || android.content.pm.dex.DexMetadataHelper.isDexMetadataFile(file) || com.android.internal.security.VerityUtils.isFsveritySignatureFile(file) || com.android.server.pm.ApkChecksums.isDigestOrDigestSignatureFile(file)) ? false : true;
        }
    };
    private static final java.io.FileFilter sAddedFilter = new java.io.FileFilter() { // from class: com.android.server.pm.PackageInstallerSession.2
        @Override // java.io.FileFilter
        public boolean accept(java.io.File file) {
            return (file.isDirectory() || file.getName().endsWith(com.android.server.pm.PackageInstallerSession.REMOVE_MARKER_EXTENSION)) ? false : true;
        }
    };
    private static final java.io.FileFilter sRemovedFilter = new java.io.FileFilter() { // from class: com.android.server.pm.PackageInstallerSession.3
        @Override // java.io.FileFilter
        public boolean accept(java.io.File file) {
            return !file.isDirectory() && file.getName().endsWith(com.android.server.pm.PackageInstallerSession.REMOVE_MARKER_EXTENSION);
        }
    };
    private final java.util.concurrent.atomic.AtomicInteger mActiveCount = new java.util.concurrent.atomic.AtomicInteger();
    private final java.lang.Object mLock = new java.lang.Object();
    private final java.util.concurrent.atomic.AtomicBoolean mTransactionLock = new java.util.concurrent.atomic.AtomicBoolean(false);
    private final java.lang.Object mProgressLock = new java.lang.Object();

    @com.android.internal.annotations.GuardedBy({"mProgressLock"})
    private float mClientProgress = com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE;

    @com.android.internal.annotations.GuardedBy({"mProgressLock"})
    private float mInternalProgress = com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE;

    @com.android.internal.annotations.GuardedBy({"mProgressLock"})
    private float mProgress = com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE;

    @com.android.internal.annotations.GuardedBy({"mProgressLock"})
    private float mReportedProgress = -1.0f;

    @com.android.internal.annotations.GuardedBy({"mProgressLock"})
    private float mIncrementalProgress = com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean mSealed = false;
    private final java.util.concurrent.atomic.AtomicBoolean mPreapprovalRequested = new java.util.concurrent.atomic.AtomicBoolean(false);
    private final java.util.concurrent.atomic.AtomicBoolean mCommitted = new java.util.concurrent.atomic.AtomicBoolean(false);

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean mStageDirInUse = false;
    private boolean mVerificationInProgress = false;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean mPermissionsManuallyAccepted = false;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final java.util.ArrayList<android.os.RevocableFileDescriptor> mFds = new java.util.ArrayList<>();

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final java.util.ArrayList<android.os.FileBridge> mBridges = new java.util.ArrayList<>();

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final android.util.SparseArray<com.android.server.pm.PackageInstallerSession> mChildSessions = new android.util.SparseArray<>();

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final android.util.ArraySet<com.android.server.pm.PackageInstallerSession.FileEntry> mFiles = new android.util.ArraySet<>();

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final android.util.ArrayMap<java.lang.String, com.android.server.pm.PackageInstallerSession.PerFileChecksum> mChecksums = new android.util.ArrayMap<>();

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final java.util.List<java.io.File> mResolvedStagedFiles = new java.util.ArrayList();

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final java.util.List<java.io.File> mResolvedInheritedFiles = new java.util.ArrayList();

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final java.util.List<java.lang.String> mResolvedInstructionSets = new java.util.ArrayList();

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final java.util.List<java.lang.String> mResolvedNativeLibPaths = new java.util.ArrayList();

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final java.util.Set<android.content.IntentSender> mUnarchivalListeners = new android.util.ArraySet();
    private volatile boolean mDataLoaderFinished = false;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private int mValidatedTargetSdk = Integer.MAX_VALUE;
    private int mUnarchivalStatus = -1;
    private final android.os.Handler.Callback mHandlerCallback = new android.os.Handler.Callback() { // from class: com.android.server.pm.PackageInstallerSession.4
        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        /* JADX WARN: Code restructure failed: missing block: B:13:0x0068, code lost:
        
            return true;
         */
        @Override // android.os.Handler.Callback
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public boolean handleMessage(android.os.Message message) {
            switch (message.what) {
                case 1:
                    com.android.server.pm.PackageInstallerSession.this.handleSessionSealed();
                    break;
                case 2:
                    com.android.server.pm.PackageInstallerSession.this.handleStreamValidateAndCommit();
                    break;
                case 3:
                    com.android.server.pm.PackageInstallerSession.this.handleInstall();
                    break;
                case 4:
                    com.android.internal.os.SomeArgs someArgs = (com.android.internal.os.SomeArgs) message.obj;
                    java.lang.String str = (java.lang.String) someArgs.arg1;
                    java.lang.String str2 = (java.lang.String) someArgs.arg2;
                    android.os.Bundle bundle = (android.os.Bundle) someArgs.arg3;
                    android.content.IntentSender intentSender = (android.content.IntentSender) someArgs.arg4;
                    int i = someArgs.argi1;
                    boolean z = someArgs.argi2 == 1;
                    someArgs.recycle();
                    com.android.server.pm.PackageInstallerSession.sendOnPackageInstalled(com.android.server.pm.PackageInstallerSession.this.mContext, intentSender, com.android.server.pm.PackageInstallerSession.this.sessionId, com.android.server.pm.PackageInstallerSession.this.isInstallerDeviceOwnerOrAffiliatedProfileOwner(), com.android.server.pm.PackageInstallerSession.this.userId, str, i, z, str2, bundle);
                    break;
                case 5:
                    com.android.server.pm.PackageInstallerSession.this.onSessionValidationFailure(message.arg1, (java.lang.String) message.obj);
                    break;
                case 6:
                    com.android.server.pm.PackageInstallerSession.this.handlePreapprovalRequest();
                    break;
            }
        }
    };

    @interface UserActionRequirement {
    }

    static class FileEntry {
        private final android.content.pm.InstallationFile mFile;
        private final int mIndex;

        FileEntry(int i, android.content.pm.InstallationFile installationFile) {
            this.mIndex = i;
            this.mFile = installationFile;
        }

        int getIndex() {
            return this.mIndex;
        }

        android.content.pm.InstallationFile getFile() {
            return this.mFile;
        }

        public boolean equals(java.lang.Object obj) {
            if (!(obj instanceof com.android.server.pm.PackageInstallerSession.FileEntry)) {
                return false;
            }
            com.android.server.pm.PackageInstallerSession.FileEntry fileEntry = (com.android.server.pm.PackageInstallerSession.FileEntry) obj;
            return this.mFile.getLocation() == fileEntry.mFile.getLocation() && android.text.TextUtils.equals(this.mFile.getName(), fileEntry.mFile.getName());
        }

        public int hashCode() {
            return java.util.Objects.hash(java.lang.Integer.valueOf(this.mFile.getLocation()), this.mFile.getName());
        }
    }

    static class PerFileChecksum {
        private final android.content.pm.Checksum[] mChecksums;
        private final byte[] mSignature;

        PerFileChecksum(android.content.pm.Checksum[] checksumArr, byte[] bArr) {
            this.mChecksums = checksumArr;
            this.mSignature = bArr;
        }

        android.content.pm.Checksum[] getChecksums() {
            return this.mChecksums;
        }

        byte[] getSignature() {
            return this.mSignature;
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    public class StagedSession implements com.android.server.pm.StagingManager.StagedSession {
        public StagedSession() {
        }

        @Override // com.android.server.pm.StagingManager.StagedSession
        public java.util.List<com.android.server.pm.StagingManager.StagedSession> getChildSessions() {
            java.util.ArrayList arrayList;
            if (!com.android.server.pm.PackageInstallerSession.this.params.isMultiPackage) {
                return java.util.Collections.EMPTY_LIST;
            }
            synchronized (com.android.server.pm.PackageInstallerSession.this.mLock) {
                try {
                    int size = com.android.server.pm.PackageInstallerSession.this.mChildSessions.size();
                    arrayList = new java.util.ArrayList(size);
                    for (int i = 0; i < size; i++) {
                        arrayList.add(((com.android.server.pm.PackageInstallerSession) com.android.server.pm.PackageInstallerSession.this.mChildSessions.valueAt(i)).mStagedSession);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
            return arrayList;
        }

        @Override // com.android.server.pm.StagingManager.StagedSession
        public android.content.pm.PackageInstaller.SessionParams sessionParams() {
            return com.android.server.pm.PackageInstallerSession.this.params;
        }

        @Override // com.android.server.pm.StagingManager.StagedSession
        public boolean isMultiPackage() {
            return com.android.server.pm.PackageInstallerSession.this.params.isMultiPackage;
        }

        @Override // com.android.server.pm.StagingManager.StagedSession
        public boolean isApexSession() {
            return (com.android.server.pm.PackageInstallerSession.this.params.installFlags & 131072) != 0;
        }

        @Override // com.android.server.pm.StagingManager.StagedSession
        public int sessionId() {
            return com.android.server.pm.PackageInstallerSession.this.sessionId;
        }

        @Override // com.android.server.pm.StagingManager.StagedSession
        public boolean containsApexSession() {
            return sessionContains(new java.util.function.Predicate() { // from class: com.android.server.pm.PackageInstallerSession$StagedSession$$ExternalSyntheticLambda0
                @Override // java.util.function.Predicate
                public final boolean test(java.lang.Object obj) {
                    boolean isApexSession;
                    isApexSession = ((com.android.server.pm.StagingManager.StagedSession) obj).isApexSession();
                    return isApexSession;
                }
            });
        }

        @Override // com.android.server.pm.StagingManager.StagedSession
        public java.lang.String getPackageName() {
            return com.android.server.pm.PackageInstallerSession.this.getPackageName();
        }

        @Override // com.android.server.pm.StagingManager.StagedSession
        public void setSessionReady() {
            com.android.server.pm.PackageInstallerSession.this.setSessionReady();
        }

        @Override // com.android.server.pm.StagingManager.StagedSession
        public void setSessionFailed(int i, java.lang.String str) {
            com.android.server.pm.PackageInstallerSession.this.setSessionFailed(i, str);
        }

        @Override // com.android.server.pm.StagingManager.StagedSession
        public void setSessionApplied() {
            com.android.server.pm.PackageInstallerSession.this.setSessionApplied();
        }

        @Override // com.android.server.pm.StagingManager.StagedSession
        public boolean containsApkSession() {
            return com.android.server.pm.PackageInstallerSession.this.containsApkSession();
        }

        @Override // com.android.server.pm.StagingManager.StagedSession
        public java.util.concurrent.CompletableFuture<java.lang.Void> installSession() {
            com.android.server.pm.PackageInstallerSession.this.assertCallerIsOwnerOrRootOrSystem();
            com.android.server.pm.PackageInstallerSession.this.assertNotChild("StagedSession#installSession");
            com.android.internal.util.Preconditions.checkArgument(isCommitted() && isSessionReady());
            return com.android.server.pm.PackageInstallerSession.this.install();
        }

        @Override // com.android.server.pm.StagingManager.StagedSession
        public boolean hasParentSessionId() {
            return com.android.server.pm.PackageInstallerSession.this.hasParentSessionId();
        }

        @Override // com.android.server.pm.StagingManager.StagedSession
        public int getParentSessionId() {
            return com.android.server.pm.PackageInstallerSession.this.getParentSessionId();
        }

        @Override // com.android.server.pm.StagingManager.StagedSession
        public boolean isCommitted() {
            return com.android.server.pm.PackageInstallerSession.this.isCommitted();
        }

        @Override // com.android.server.pm.StagingManager.StagedSession
        public boolean isInTerminalState() {
            return com.android.server.pm.PackageInstallerSession.this.isInTerminalState();
        }

        @Override // com.android.server.pm.StagingManager.StagedSession
        public boolean isDestroyed() {
            return com.android.server.pm.PackageInstallerSession.this.isDestroyed();
        }

        @Override // com.android.server.pm.StagingManager.StagedSession
        public long getCommittedMillis() {
            return com.android.server.pm.PackageInstallerSession.this.getCommittedMillis();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ boolean lambda$sessionContains$1(java.util.function.Predicate predicate, com.android.server.pm.PackageInstallerSession packageInstallerSession) {
            return predicate.test(packageInstallerSession.mStagedSession);
        }

        @Override // com.android.server.pm.StagingManager.StagedSession
        public boolean sessionContains(final java.util.function.Predicate<com.android.server.pm.StagingManager.StagedSession> predicate) {
            return com.android.server.pm.PackageInstallerSession.this.sessionContains(new java.util.function.Predicate() { // from class: com.android.server.pm.PackageInstallerSession$StagedSession$$ExternalSyntheticLambda1
                @Override // java.util.function.Predicate
                public final boolean test(java.lang.Object obj) {
                    boolean lambda$sessionContains$1;
                    lambda$sessionContains$1 = com.android.server.pm.PackageInstallerSession.StagedSession.lambda$sessionContains$1(predicate, (com.android.server.pm.PackageInstallerSession) obj);
                    return lambda$sessionContains$1;
                }
            });
        }

        @Override // com.android.server.pm.StagingManager.StagedSession
        public boolean isSessionReady() {
            return com.android.server.pm.PackageInstallerSession.this.isSessionReady();
        }

        @Override // com.android.server.pm.StagingManager.StagedSession
        public boolean isSessionApplied() {
            return com.android.server.pm.PackageInstallerSession.this.isSessionApplied();
        }

        @Override // com.android.server.pm.StagingManager.StagedSession
        public boolean isSessionFailed() {
            return com.android.server.pm.PackageInstallerSession.this.isSessionFailed();
        }

        @Override // com.android.server.pm.StagingManager.StagedSession
        public void abandon() {
            com.android.server.pm.PackageInstallerSession.this.abandon();
        }

        @Override // com.android.server.pm.StagingManager.StagedSession
        public void verifySession() {
            com.android.server.pm.PackageInstallerSession.this.assertCallerIsOwnerOrRootOrSystem();
            if (isCommittedAndNotInTerminalState()) {
                com.android.server.pm.PackageInstallerSession.this.verify();
            }
        }

        private boolean isCommittedAndNotInTerminalState() {
            java.lang.String str;
            java.lang.String formatSimple;
            if (!isCommitted()) {
                str = android.text.TextUtils.formatSimple("The session %d should be committed", new java.lang.Object[]{java.lang.Integer.valueOf(com.android.server.pm.PackageInstallerSession.this.sessionId)});
            } else if (isSessionApplied()) {
                str = android.text.TextUtils.formatSimple("The session %d has applied", new java.lang.Object[]{java.lang.Integer.valueOf(com.android.server.pm.PackageInstallerSession.this.sessionId)});
            } else if (!isSessionFailed()) {
                str = null;
            } else {
                synchronized (com.android.server.pm.PackageInstallerSession.this.mLock) {
                    formatSimple = android.text.TextUtils.formatSimple("The session %d has failed with error: %s", new java.lang.Object[]{java.lang.Integer.valueOf(com.android.server.pm.PackageInstallerSession.this.sessionId), com.android.server.pm.PackageInstallerSession.this.mSessionErrorMessage});
                }
                str = formatSimple;
            }
            if (str != null) {
                android.util.Slog.e(com.android.server.pm.PackageInstallerSession.TAG, "verifySession error: " + str);
                setSessionFailed(android.hardware.biometrics.fingerprint.V2_1.RequestStatus.SYS_ETIMEDOUT, str);
                com.android.server.pm.PackageInstallerSession.this.onSessionVerificationFailure(android.hardware.biometrics.fingerprint.V2_1.RequestStatus.SYS_ETIMEDOUT, str);
                return false;
            }
            return true;
        }
    }

    static boolean isDataLoaderInstallation(android.content.pm.PackageInstaller.SessionParams sessionParams) {
        return sessionParams.dataLoaderParams != null;
    }

    static boolean isSystemDataLoaderInstallation(android.content.pm.PackageInstaller.SessionParams sessionParams) {
        if (!isDataLoaderInstallation(sessionParams)) {
            return false;
        }
        return "android".equals(sessionParams.dataLoaderParams.getComponentName().getPackageName());
    }

    static boolean isArchivedInstallation(int i) {
        return (i & 134217728) != 0;
    }

    private boolean isDataLoaderInstallation() {
        return isDataLoaderInstallation(this.params);
    }

    private boolean isStreamingInstallation() {
        return isDataLoaderInstallation() && this.params.dataLoaderParams.getType() == 1;
    }

    private boolean isIncrementalInstallation() {
        return isDataLoaderInstallation() && this.params.dataLoaderParams.getType() == 2;
    }

    private boolean isSystemDataLoaderInstallation() {
        return isSystemDataLoaderInstallation(this.params);
    }

    private boolean isArchivedInstallation() {
        return isArchivedInstallation(this.params.installFlags);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isInstallerDeviceOwnerOrAffiliatedProfileOwner() {
        android.app.admin.DevicePolicyManagerInternal devicePolicyManagerInternal;
        assertNotLocked("isInstallerDeviceOwnerOrAffiliatedProfileOwner");
        return this.userId == android.os.UserHandle.getUserId(getInstallerUid()) && (devicePolicyManagerInternal = (android.app.admin.DevicePolicyManagerInternal) com.android.server.LocalServices.getService(android.app.admin.DevicePolicyManagerInternal.class)) != null && devicePolicyManagerInternal.canSilentlyInstallPackage(getInstallSource().mInstallerPackageName, this.mInstallerUid);
    }

    private boolean isEmergencyInstallerEnabled(java.lang.String str, com.android.server.pm.Computer computer) {
        java.lang.String emergencyInstaller;
        com.android.server.pm.pkg.PackageStateInternal packageStateInternal = computer.getPackageStateInternal(str);
        return (packageStateInternal == null || packageStateInternal.getPkg() == null || (emergencyInstaller = packageStateInternal.getPkg().getEmergencyInstaller()) == null || !com.android.internal.util.ArrayUtils.contains(computer.getPackagesForUid(this.mInstallerUid), emergencyInstaller) || computer.checkUidPermission("android.permission.EMERGENCY_INSTALL_PACKAGES", this.mInstallerUid) != 0) ? false : true;
    }

    @com.android.server.pm.PackageInstallerSession.UserActionRequirement
    private int computeUserActionRequirement() {
        java.lang.String str;
        int i;
        android.content.pm.InstallSourceInfo installSourceInfo;
        java.lang.String str2;
        synchronized (this.mLock) {
            try {
                if (this.mPermissionsManuallyAccepted) {
                    return 0;
                }
                if (this.mPackageName != null) {
                    str = this.mPackageName;
                } else if (this.mPreapprovalRequested.get() && this.mPreapprovalDetails != null) {
                    str = this.mPreapprovalDetails.getPackageName();
                } else {
                    str = null;
                }
                boolean z = this.mHasDeviceAdminReceiver;
                if ((this.params.installFlags & 1024) != 0 || this.params.requireUserAction == 1) {
                    i = 1;
                } else {
                    i = 0;
                }
                com.android.server.pm.Computer snapshotComputer = this.mPm.snapshotComputer();
                boolean z2 = snapshotComputer.checkUidPermission("android.permission.INSTALL_PACKAGES", this.mInstallerUid) == 0;
                boolean z3 = snapshotComputer.checkUidPermission("android.permission.INSTALL_SELF_UPDATES", this.mInstallerUid) == 0;
                boolean z4 = snapshotComputer.checkUidPermission("android.permission.INSTALL_PACKAGE_UPDATES", this.mInstallerUid) == 0;
                boolean z5 = snapshotComputer.checkUidPermission("android.permission.UPDATE_PACKAGES_WITHOUT_USER_ACTION", this.mInstallerUid) == 0;
                boolean z6 = snapshotComputer.checkUidPermission("android.permission.INSTALL_DPC_PACKAGES", this.mInstallerUid) == 0;
                int packageUid = snapshotComputer.getPackageUid(str, 0L, this.userId);
                boolean z7 = packageUid != -1 || isApexSession();
                if (z7) {
                    installSourceInfo = snapshotComputer.getInstallSourceInfo(str, this.userId);
                } else {
                    installSourceInfo = null;
                }
                if (installSourceInfo != null) {
                    str2 = installSourceInfo.getInstallingPackageName();
                } else {
                    str2 = null;
                }
                java.lang.String updateOwnerPackageName = installSourceInfo != null ? installSourceInfo.getUpdateOwnerPackageName() : null;
                boolean z8 = z7 && java.util.Objects.equals(str2, getInstallerPackageName());
                boolean equals = android.text.TextUtils.equals(updateOwnerPackageName, getInstallerPackageName());
                boolean z9 = packageUid == this.mInstallerUid;
                boolean isEmergencyInstallerEnabled = isEmergencyInstallerEnabled(str, snapshotComputer);
                boolean z10 = z2 || (z4 && z7) || ((z3 && z9) || (z6 && z));
                boolean z11 = this.mInstallerUid == 0;
                boolean z12 = this.mInstallerUid == 1000;
                boolean z13 = this.mInstallerUid == 2000;
                boolean z14 = (this.params.installFlags & 67108864) != 0;
                boolean z15 = com.android.server.pm.PackageManagerService.isUpdateOwnershipEnforcementAvailable() && updateOwnerPackageName != null;
                if (z11 || z12 || isInstallerDeviceOwnerOrAffiliatedProfileOwner() || isEmergencyInstallerEnabled) {
                    return i;
                }
                if (z15 && !isApexSession() && !equals && !z13 && !z14) {
                    return 3;
                }
                if (!z10) {
                    if (snapshotComputer.isInstallDisabledForPackage(getInstallerPackageName(), this.mInstallerUid, this.userId) || this.params.requireUserAction != 2 || !z5) {
                        return 1;
                    }
                    if (!z15 ? !z8 : !equals) {
                        if (!z9) {
                            return 1;
                        }
                    }
                    return 2;
                }
                return i;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private void updateUserActionRequirement(int i) {
        synchronized (this.mLock) {
            this.mUserActionRequirement = i;
        }
    }

    public PackageInstallerSession(com.android.server.pm.PackageInstallerService.InternalCallback internalCallback, android.content.Context context, com.android.server.pm.PackageManagerService packageManagerService, com.android.server.pm.PackageSessionProvider packageSessionProvider, com.android.server.pm.SilentUpdatePolicy silentUpdatePolicy, android.os.Looper looper, com.android.server.pm.StagingManager stagingManager, int i, int i2, int i3, @android.annotation.NonNull com.android.server.pm.InstallSource installSource, android.content.pm.PackageInstaller.SessionParams sessionParams, long j, long j2, java.io.File file, java.lang.String str, android.content.pm.InstallationFile[] installationFileArr, android.util.ArrayMap<java.lang.String, com.android.server.pm.PackageInstallerSession.PerFileChecksum> arrayMap, boolean z, boolean z2, boolean z3, boolean z4, @android.annotation.Nullable int[] iArr, int i4, boolean z5, boolean z6, boolean z7, int i5, java.lang.String str2, android.content.pm.verify.domain.DomainSet domainSet) {
        this.mPrepared = false;
        this.mShouldBeSealed = false;
        this.mSessionErrorCode = 0;
        this.mDestroyed = false;
        this.mCallback = internalCallback;
        this.mContext = context;
        this.mPm = packageManagerService;
        this.mInstaller = this.mPm != null ? this.mPm.mInstaller : null;
        this.mSessionProvider = packageSessionProvider;
        this.mSilentUpdatePolicy = silentUpdatePolicy;
        this.mHandler = new android.os.Handler(looper, this.mHandlerCallback);
        this.mStagingManager = stagingManager;
        this.sessionId = i;
        this.userId = i2;
        this.mOriginalInstallerUid = i3;
        this.mInstallerUid = i3;
        java.util.Objects.requireNonNull(installSource);
        this.mInstallSource = installSource;
        this.mOriginalInstallerPackageName = this.mInstallSource.mInstallerPackageName;
        this.params = sessionParams;
        this.createdMillis = j;
        this.updatedMillis = j;
        this.committedMillis = j2;
        this.stageDir = file;
        this.stageCid = str;
        this.mShouldBeSealed = z4;
        if (iArr != null) {
            for (int i6 : iArr) {
                this.mChildSessions.put(i6, null);
            }
        }
        this.mParentSessionId = i4;
        if (installationFileArr != null) {
            this.mFiles.ensureCapacity(installationFileArr.length);
            int length = installationFileArr.length;
            for (int i7 = 0; i7 < length; i7++) {
                if (!this.mFiles.add(new com.android.server.pm.PackageInstallerSession.FileEntry(i7, installationFileArr[i7]))) {
                    throw new java.lang.IllegalArgumentException("Trying to add a duplicate installation file");
                }
            }
        }
        if (arrayMap != null) {
            this.mChecksums.putAll((android.util.ArrayMap<? extends java.lang.String, ? extends com.android.server.pm.PackageInstallerSession.PerFileChecksum>) arrayMap);
        }
        if (!sessionParams.isMultiPackage) {
            if ((file == null) == (str == null)) {
                throw new java.lang.IllegalArgumentException("Exactly one of stageDir or stageCid stage must be set");
            }
        }
        this.mPrepared = z;
        this.mCommitted.set(z2);
        this.mDestroyed = z3;
        this.mSessionReady = z5;
        this.mSessionApplied = z7;
        this.mSessionFailed = z6;
        this.mSessionErrorCode = i5;
        this.mSessionErrorMessage = str2 != null ? str2 : "";
        this.mStagedSession = sessionParams.isStaged ? new com.android.server.pm.PackageInstallerSession.StagedSession() : null;
        this.mPreVerifiedDomains = domainSet;
        if (isDataLoaderInstallation()) {
            if (isApexSession()) {
                throw new java.lang.IllegalArgumentException("DataLoader installation of APEX modules is not allowed.");
            }
            if (isSystemDataLoaderInstallation() && this.mContext.checkCallingOrSelfPermission("com.android.permission.USE_SYSTEM_DATA_LOADERS") != 0) {
                throw new java.lang.SecurityException("You need the com.android.permission.USE_SYSTEM_DATA_LOADERS permission to use system data loaders");
            }
        }
        if (isIncrementalInstallation() && !android.os.incremental.IncrementalManager.isAllowed()) {
            throw new java.lang.IllegalArgumentException("Incremental installation not allowed.");
        }
        if (isArchivedInstallation()) {
            if (sessionParams.mode != 1) {
                throw new java.lang.IllegalArgumentException("Archived installation can only be full install.");
            }
            if (!isStreamingInstallation() || !isSystemDataLoaderInstallation()) {
                throw new java.lang.IllegalArgumentException("Archived installation can only use Streaming System DataLoader.");
            }
        }
    }

    com.android.server.pm.PackageInstallerHistoricalSession createHistoricalSession() {
        float f;
        float f2;
        com.android.server.pm.PackageInstallerHistoricalSession packageInstallerHistoricalSession;
        synchronized (this.mProgressLock) {
            f = this.mProgress;
            f2 = this.mClientProgress;
        }
        synchronized (this.mLock) {
            packageInstallerHistoricalSession = new com.android.server.pm.PackageInstallerHistoricalSession(this.sessionId, this.userId, this.mOriginalInstallerUid, this.mOriginalInstallerPackageName, this.mInstallSource, this.mInstallerUid, this.createdMillis, this.updatedMillis, this.committedMillis, this.stageDir, this.stageCid, f2, f, isCommitted(), isPreapprovalRequested(), this.mSealed, this.mPermissionsManuallyAccepted, this.mStageDirInUse, this.mDestroyed, this.mFds.size(), this.mBridges.size(), this.mFinalStatus, this.mFinalMessage, this.params, this.mParentSessionId, getChildSessionIdsLocked(), this.mSessionApplied, this.mSessionFailed, this.mSessionReady, this.mSessionErrorCode, this.mSessionErrorMessage, this.mPreapprovalDetails, this.mPreVerifiedDomains, this.mPackageName);
        }
        return packageInstallerHistoricalSession;
    }

    private boolean shouldScrubData(int i) {
        return i >= 10000 && getInstallerUid() != i;
    }

    public android.content.pm.PackageInstaller.SessionInfo generateInfoForCaller(boolean z, int i) {
        return generateInfoInternal(z, shouldScrubData(i));
    }

    public android.content.pm.PackageInstaller.SessionInfo generateInfoScrubbed(boolean z) {
        return generateInfoInternal(z, true);
    }

    private android.content.pm.PackageInstaller.SessionInfo generateInfoInternal(boolean z, boolean z2) {
        float f;
        java.lang.String str;
        android.content.pm.PackageInstaller.SessionInfo sessionInfo = new android.content.pm.PackageInstaller.SessionInfo();
        synchronized (this.mProgressLock) {
            f = this.mProgress;
        }
        synchronized (this.mLock) {
            try {
                sessionInfo.sessionId = this.sessionId;
                sessionInfo.userId = this.userId;
                sessionInfo.installerPackageName = this.mInstallSource.mInstallerPackageName;
                sessionInfo.installerAttributionTag = this.mInstallSource.mInstallerAttributionTag;
                sessionInfo.resolvedBaseCodePath = null;
                if (this.mContext.checkCallingOrSelfPermission("android.permission.READ_INSTALLED_SESSION_PATHS") == 0) {
                    java.io.File file = this.mResolvedBaseFile;
                    if (file == null) {
                        java.util.List<java.io.File> addedApksLocked = getAddedApksLocked();
                        if (addedApksLocked.size() > 0) {
                            file = addedApksLocked.get(0);
                        }
                    }
                    if (file != null) {
                        sessionInfo.resolvedBaseCodePath = file.getAbsolutePath();
                    }
                }
                sessionInfo.progress = f;
                sessionInfo.sealed = this.mSealed;
                sessionInfo.isCommitted = isCommitted();
                sessionInfo.isPreapprovalRequested = isPreapprovalRequested();
                sessionInfo.active = this.mActiveCount.get() > 0;
                sessionInfo.mode = this.params.mode;
                sessionInfo.installReason = this.params.installReason;
                sessionInfo.installScenario = this.params.installScenario;
                sessionInfo.sizeBytes = this.params.sizeBytes;
                if (this.mPreapprovalDetails != null) {
                    str = this.mPreapprovalDetails.getPackageName();
                } else {
                    str = this.mPackageName != null ? this.mPackageName : this.params.appPackageName;
                }
                sessionInfo.appPackageName = str;
                if (z) {
                    sessionInfo.appIcon = (this.mPreapprovalDetails == null || this.mPreapprovalDetails.getIcon() == null) ? this.params.appIcon : this.mPreapprovalDetails.getIcon();
                }
                sessionInfo.appLabel = this.mPreapprovalDetails != null ? this.mPreapprovalDetails.getLabel() : this.params.appLabel;
                sessionInfo.installLocation = this.params.installLocation;
                if (!z2) {
                    sessionInfo.originatingUri = this.params.originatingUri;
                }
                sessionInfo.originatingUid = this.params.originatingUid;
                if (!z2) {
                    sessionInfo.referrerUri = this.params.referrerUri;
                }
                sessionInfo.grantedRuntimePermissions = this.params.getLegacyGrantedRuntimePermissions();
                sessionInfo.whitelistedRestrictedPermissions = this.params.whitelistedRestrictedPermissions;
                sessionInfo.autoRevokePermissionsMode = this.params.autoRevokePermissionsMode;
                sessionInfo.installFlags = this.params.installFlags;
                sessionInfo.rollbackLifetimeMillis = this.params.rollbackLifetimeMillis;
                sessionInfo.rollbackImpactLevel = this.params.rollbackImpactLevel;
                sessionInfo.isMultiPackage = this.params.isMultiPackage;
                sessionInfo.isStaged = this.params.isStaged;
                sessionInfo.rollbackDataPolicy = this.params.rollbackDataPolicy;
                sessionInfo.parentSessionId = this.mParentSessionId;
                sessionInfo.childSessionIds = getChildSessionIdsLocked();
                sessionInfo.isSessionApplied = this.mSessionApplied;
                sessionInfo.isSessionReady = this.mSessionReady;
                sessionInfo.isSessionFailed = this.mSessionFailed;
                sessionInfo.setSessionErrorCode(this.mSessionErrorCode, this.mSessionErrorMessage);
                sessionInfo.createdMillis = this.createdMillis;
                sessionInfo.updatedMillis = this.updatedMillis;
                sessionInfo.requireUserAction = this.params.requireUserAction;
                sessionInfo.installerUid = this.mInstallerUid;
                sessionInfo.packageSource = this.params.packageSource;
                sessionInfo.applicationEnabledSettingPersistent = this.params.applicationEnabledSettingPersistent;
                sessionInfo.pendingUserActionReason = userActionRequirementToReason(this.mUserActionRequirement);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return sessionInfo;
    }

    public boolean isPrepared() {
        boolean z;
        synchronized (this.mLock) {
            z = this.mPrepared;
        }
        return z;
    }

    public boolean isSealed() {
        boolean z;
        synchronized (this.mLock) {
            z = this.mSealed;
        }
        return z;
    }

    boolean isPreapprovalRequested() {
        return this.mPreapprovalRequested.get();
    }

    boolean isCommitted() {
        return this.mCommitted.get();
    }

    boolean isDestroyed() {
        boolean z;
        synchronized (this.mLock) {
            z = this.mDestroyed;
        }
        return z;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isInTerminalState() {
        boolean z;
        synchronized (this.mLock) {
            try {
                z = this.mSessionApplied || this.mSessionFailed;
            } finally {
            }
        }
        return z;
    }

    public boolean isStagedAndInTerminalState() {
        return this.params.isStaged && isInTerminalState();
    }

    private void assertNotLocked(java.lang.String str) {
        if (java.lang.Thread.holdsLock(this.mLock)) {
            throw new java.lang.IllegalStateException(str + " is holding mLock");
        }
    }

    private void assertSealed(java.lang.String str) {
        if (!isSealed()) {
            throw new java.lang.IllegalStateException(str + " before sealing");
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void assertPreparedAndNotPreapprovalRequestedLocked(java.lang.String str) {
        assertPreparedAndNotSealedLocked(str);
        if (isPreapprovalRequested()) {
            throw new java.lang.IllegalStateException(str + " not allowed after requesting");
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void assertPreparedAndNotSealedLocked(java.lang.String str) {
        assertPreparedAndNotCommittedOrDestroyedLocked(str);
        if (this.mSealed) {
            throw new java.lang.SecurityException(str + " not allowed after sealing");
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void assertPreparedAndNotCommittedOrDestroyedLocked(java.lang.String str) {
        assertPreparedAndNotDestroyedLocked(str);
        if (isCommitted()) {
            throw new java.lang.SecurityException(str + " not allowed after commit");
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void assertPreparedAndNotDestroyedLocked(java.lang.String str) {
        if (!this.mPrepared) {
            throw new java.lang.IllegalStateException(str + " before prepared");
        }
        if (this.mDestroyed) {
            throw new java.lang.SecurityException(str + " not allowed after destruction");
        }
    }

    @com.android.internal.annotations.GuardedBy({"mProgressLock"})
    private void setClientProgressLocked(float f) {
        boolean z = this.mClientProgress == com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE;
        this.mClientProgress = f;
        computeProgressLocked(z);
    }

    public void setClientProgress(float f) {
        assertCallerIsOwnerOrRoot();
        synchronized (this.mProgressLock) {
            setClientProgressLocked(f);
        }
    }

    public void addClientProgress(float f) {
        assertCallerIsOwnerOrRoot();
        synchronized (this.mProgressLock) {
            setClientProgressLocked(this.mClientProgress + f);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mProgressLock"})
    public void computeProgressLocked(boolean z) {
        if (!isIncrementalInstallation() || !isCommitted()) {
            this.mProgress = android.util.MathUtils.constrain(this.mClientProgress * 0.8f, com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE, 0.8f) + android.util.MathUtils.constrain(this.mInternalProgress * 0.2f, com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE, 0.2f);
        } else if (this.mIncrementalProgress - this.mProgress >= 0.01d) {
            this.mProgress = this.mIncrementalProgress;
        }
        if (z || this.mProgress - this.mReportedProgress >= 0.01d) {
            this.mReportedProgress = this.mProgress;
            this.mCallback.onSessionProgressChanged(this, this.mProgress);
        }
    }

    public java.lang.String[] getNames() {
        java.lang.String[] stageDirContentsLocked;
        java.lang.String[] removeString;
        assertCallerIsOwnerRootOrVerifier();
        synchronized (this.mLock) {
            try {
                assertPreparedAndNotDestroyedLocked("getNames");
                if (!isCommitted()) {
                    stageDirContentsLocked = getNamesLocked();
                } else {
                    stageDirContentsLocked = getStageDirContentsLocked();
                }
                removeString = com.android.internal.util.ArrayUtils.removeString(stageDirContentsLocked, com.android.server.pm.PackageManagerService.APP_METADATA_FILE_NAME);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return removeString;
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private java.lang.String[] getStageDirContentsLocked() {
        if (this.stageDir == null) {
            return libcore.util.EmptyArray.STRING;
        }
        java.lang.String[] list = this.stageDir.list();
        if (list == null) {
            return libcore.util.EmptyArray.STRING;
        }
        return list;
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private java.lang.String[] getNamesLocked() {
        if (!isDataLoaderInstallation()) {
            return getStageDirContentsLocked();
        }
        android.content.pm.InstallationFile[] installationFilesLocked = getInstallationFilesLocked();
        java.lang.String[] strArr = new java.lang.String[installationFilesLocked.length];
        int length = installationFilesLocked.length;
        for (int i = 0; i < length; i++) {
            strArr[i] = installationFilesLocked[i].getName();
        }
        return strArr;
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private android.content.pm.InstallationFile[] getInstallationFilesLocked() {
        android.content.pm.InstallationFile[] installationFileArr = new android.content.pm.InstallationFile[this.mFiles.size()];
        java.util.Iterator<com.android.server.pm.PackageInstallerSession.FileEntry> it = this.mFiles.iterator();
        while (it.hasNext()) {
            com.android.server.pm.PackageInstallerSession.FileEntry next = it.next();
            installationFileArr[next.getIndex()] = next.getFile();
        }
        return installationFileArr;
    }

    private static java.util.ArrayList<java.io.File> filterFiles(java.io.File file, java.lang.String[] strArr, java.io.FileFilter fileFilter) {
        java.util.ArrayList<java.io.File> arrayList = new java.util.ArrayList<>(strArr.length);
        for (java.lang.String str : strArr) {
            java.io.File file2 = new java.io.File(file, str);
            if (fileFilter.accept(file2)) {
                arrayList.add(file2);
            }
        }
        return arrayList;
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private java.util.List<java.io.File> getAddedApksLocked() {
        return filterFiles(this.stageDir, getNamesLocked(), sAddedApkFilter);
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void enableFsVerityToAddedApksWithIdsig() throws com.android.server.pm.PackageManagerException {
        try {
            for (java.io.File file : getAddedApksLocked()) {
                if (new java.io.File(file.getPath() + ".idsig").exists()) {
                    com.android.internal.security.VerityUtils.setUpFsverity(file.getPath());
                }
            }
        } catch (java.io.IOException e) {
            throw new com.android.server.pm.PrepareFailure(-118, "Failed to enable fs-verity to verify with idsig: " + e);
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private java.util.List<android.content.pm.parsing.ApkLite> getAddedApkLitesLocked() throws com.android.server.pm.PackageManagerException {
        int i = 0;
        if (!isArchivedInstallation()) {
            java.util.List<java.io.File> addedApksLocked = getAddedApksLocked();
            java.util.ArrayList arrayList = new java.util.ArrayList(addedApksLocked.size());
            android.content.pm.parsing.result.ParseTypeImpl forDefaultParsing = android.content.pm.parsing.result.ParseTypeImpl.forDefaultParsing();
            int size = addedApksLocked.size();
            while (i < size) {
                android.content.pm.parsing.result.ParseResult parseApkLite = android.content.pm.parsing.ApkLiteParseUtils.parseApkLite(forDefaultParsing.reset(), addedApksLocked.get(i), 32);
                if (parseApkLite.isError()) {
                    throw new com.android.server.pm.PackageManagerException(parseApkLite.getErrorCode(), parseApkLite.getErrorMessage(), parseApkLite.getException());
                }
                arrayList.add((android.content.pm.parsing.ApkLite) parseApkLite.getResult());
                i++;
            }
            return arrayList;
        }
        android.content.pm.InstallationFile[] installationFilesLocked = getInstallationFilesLocked();
        java.util.ArrayList arrayList2 = new java.util.ArrayList(installationFilesLocked.length);
        int length = installationFilesLocked.length;
        while (i < length) {
            java.io.File file = new java.io.File(this.stageDir, installationFilesLocked[i].getName());
            if (sAddedApkFilter.accept(file)) {
                try {
                    com.android.server.pm.PackageManagerShellCommandDataLoader.Metadata fromByteArray = com.android.server.pm.PackageManagerShellCommandDataLoader.Metadata.fromByteArray(installationFilesLocked[i].getMetadata());
                    if (fromByteArray.getMode() != 4) {
                        throw new com.android.server.pm.PackageManagerException(-22, "File metadata is not for ARCHIVED package: " + file);
                    }
                    android.content.pm.ArchivedPackageParcel archivedPackage = fromByteArray.getArchivedPackage();
                    if (archivedPackage == null) {
                        throw new com.android.server.pm.PackageManagerException(-22, "Metadata does not contain ArchivedPackage: " + file);
                    }
                    if (archivedPackage.packageName == null || archivedPackage.signingDetails == null) {
                        throw new com.android.server.pm.PackageManagerException(-22, "ArchivedPackage does not contain required info: " + file);
                    }
                    arrayList2.add(new android.content.pm.parsing.ApkLite(file.getAbsolutePath(), archivedPackage));
                } catch (java.io.IOException e) {
                    throw new com.android.server.pm.PackageManagerException(android.hardware.biometrics.fingerprint.V2_1.RequestStatus.SYS_ETIMEDOUT, "Failed to ", e);
                }
            }
            i++;
        }
        return arrayList2;
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private java.util.List<java.io.File> getRemovedFilesLocked() {
        return filterFiles(this.stageDir, getNamesLocked(), sRemovedFilter);
    }

    public void setChecksums(java.lang.String str, @android.annotation.NonNull android.content.pm.Checksum[] checksumArr, @android.annotation.Nullable byte[] bArr) {
        if (checksumArr.length == 0) {
            return;
        }
        java.lang.String str2 = getInstallSource().mInitiatingPackageName;
        if (com.android.server.pm.PackageManagerServiceUtils.isInstalledByAdb(str2)) {
            str2 = getInstallSource().mInstallerPackageName;
        }
        if (android.text.TextUtils.isEmpty(str2)) {
            throw new java.lang.IllegalStateException("Installer package is empty.");
        }
        ((android.app.AppOpsManager) this.mContext.getSystemService(android.app.AppOpsManager.class)).checkPackage(android.os.Binder.getCallingUid(), str2);
        if (((android.content.pm.PackageManagerInternal) com.android.server.LocalServices.getService(android.content.pm.PackageManagerInternal.class)).getPackage(str2) == null) {
            throw new java.lang.IllegalStateException("Can't obtain calling installer's package.");
        }
        if (bArr != null && bArr.length != 0) {
            try {
                com.android.server.pm.ApkChecksums.verifySignature(checksumArr, bArr);
            } catch (java.io.IOException | java.security.NoSuchAlgorithmException | java.security.SignatureException e) {
                throw new java.lang.IllegalArgumentException("Can't verify signature: " + e.getMessage(), e);
            }
        }
        for (android.content.pm.Checksum checksum : checksumArr) {
            if (checksum.getValue() == null || checksum.getValue().length > 64) {
                throw new java.lang.IllegalArgumentException("Invalid checksum.");
            }
        }
        assertCallerIsOwnerOrRoot();
        synchronized (this.mLock) {
            try {
                assertPreparedAndNotCommittedOrDestroyedLocked("addChecksums");
                if (this.mChecksums.containsKey(str)) {
                    throw new java.lang.IllegalStateException("Duplicate checksums.");
                }
                this.mChecksums.put(str, new com.android.server.pm.PackageInstallerSession.PerFileChecksum(checksumArr, bArr));
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void requestChecksums(@android.annotation.NonNull java.lang.String str, int i, int i2, @android.annotation.Nullable java.util.List list, @android.annotation.NonNull android.content.pm.IOnChecksumsReadyListener iOnChecksumsReadyListener) {
        java.lang.String str2;
        assertCallerIsOwnerRootOrVerifier();
        java.io.File file = new java.io.File(this.stageDir, str);
        if (com.android.server.pm.PackageManagerServiceUtils.isInstalledByAdb(getInstallSource().mInitiatingPackageName)) {
            str2 = getInstallSource().mInstallerPackageName;
        } else {
            str2 = getInstallSource().mInitiatingPackageName;
        }
        try {
            this.mPm.requestFileChecksums(file, str2, i, i2, list, iOnChecksumsReadyListener);
        } catch (java.io.FileNotFoundException e) {
            throw new android.os.ParcelableException(e);
        }
    }

    public void removeSplit(java.lang.String str) {
        if (isDataLoaderInstallation()) {
            throw new java.lang.IllegalStateException("Cannot remove splits in a data loader installation session.");
        }
        if (android.text.TextUtils.isEmpty(this.params.appPackageName)) {
            throw new java.lang.IllegalStateException("Must specify package name to remove a split");
        }
        assertCallerIsOwnerOrRoot();
        synchronized (this.mLock) {
            try {
                assertPreparedAndNotCommittedOrDestroyedLocked("removeSplit");
                try {
                    createRemoveSplitMarkerLocked(str);
                } catch (java.io.IOException e) {
                    throw android.util.ExceptionUtils.wrap(e);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private static java.lang.String getRemoveMarkerName(java.lang.String str) {
        java.lang.String str2 = str + REMOVE_MARKER_EXTENSION;
        if (!android.os.FileUtils.isValidExtFilename(str2)) {
            throw new java.lang.IllegalArgumentException("Invalid marker: " + str2);
        }
        return str2;
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void createRemoveSplitMarkerLocked(java.lang.String str) throws java.io.IOException {
        try {
            java.io.File file = new java.io.File(this.stageDir, getRemoveMarkerName(str));
            file.createNewFile();
            android.system.Os.chmod(file.getAbsolutePath(), 0);
        } catch (android.system.ErrnoException e) {
            throw e.rethrowAsIOException();
        }
    }

    private void assertShellOrSystemCalling(java.lang.String str) {
        switch (android.os.Binder.getCallingUid()) {
            case 0:
            case 1000:
            case 2000:
                return;
            default:
                throw new java.lang.SecurityException(str + " only supported from shell or system");
        }
    }

    private void assertCanWrite(boolean z) {
        if (isDataLoaderInstallation()) {
            throw new java.lang.IllegalStateException("Cannot write regular files in a data loader installation session.");
        }
        assertCallerIsOwnerOrRoot();
        synchronized (this.mLock) {
            assertPreparedAndNotSealedLocked("assertCanWrite");
        }
        if (z) {
            assertShellOrSystemCalling("Reverse mode");
        }
    }

    private java.io.File getTmpAppMetadataFile() {
        return new java.io.File(android.os.Environment.getDataAppDirectory(this.params.volumeUuid), this.sessionId + "-" + com.android.server.pm.PackageManagerService.APP_METADATA_FILE_NAME);
    }

    private java.io.File getStagedAppMetadataFile() {
        return new java.io.File(this.stageDir, com.android.server.pm.PackageManagerService.APP_METADATA_FILE_NAME);
    }

    private static boolean isAppMetadata(java.lang.String str) {
        return str.endsWith(com.android.server.pm.PackageManagerService.APP_METADATA_FILE_NAME);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean isAppMetadata(java.io.File file) {
        return isAppMetadata(file.getName());
    }

    public android.os.ParcelFileDescriptor getAppMetadataFd() {
        assertCallerIsOwnerOrRoot();
        synchronized (this.mLock) {
            assertPreparedAndNotCommittedOrDestroyedLocked("getAppMetadataFd");
            if (!getStagedAppMetadataFile().exists()) {
                return null;
            }
            try {
                return openReadInternalLocked(com.android.server.pm.PackageManagerService.APP_METADATA_FILE_NAME);
            } catch (java.io.IOException e) {
                throw android.util.ExceptionUtils.wrap(e);
            }
        }
    }

    public void removeAppMetadata() {
        java.io.File stagedAppMetadataFile = getStagedAppMetadataFile();
        if (stagedAppMetadataFile.exists()) {
            stagedAppMetadataFile.delete();
        }
    }

    static long getAppMetadataSizeLimit() {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            return android.provider.DeviceConfig.getLong("package_manager_service", PROPERTY_APP_METADATA_BYTE_SIZE_LIMIT, DEFAULT_APP_METADATA_BYTE_SIZE_LIMIT);
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public android.os.ParcelFileDescriptor openWriteAppMetadata() {
        assertCallerIsOwnerOrRoot();
        synchronized (this.mLock) {
            assertPreparedAndNotSealedLocked("openWriteAppMetadata");
        }
        try {
            return doWriteInternal(com.android.server.pm.PackageManagerService.APP_METADATA_FILE_NAME, 0L, -1L, null);
        } catch (java.io.IOException e) {
            throw android.util.ExceptionUtils.wrap(e);
        }
    }

    public android.os.ParcelFileDescriptor openWrite(java.lang.String str, long j, long j2) {
        assertCanWrite(false);
        try {
            return doWriteInternal(str, j, j2, null);
        } catch (java.io.IOException e) {
            throw android.util.ExceptionUtils.wrap(e);
        }
    }

    public void write(java.lang.String str, long j, long j2, android.os.ParcelFileDescriptor parcelFileDescriptor) {
        assertCanWrite(parcelFileDescriptor != null);
        try {
            doWriteInternal(str, j, j2, parcelFileDescriptor);
        } catch (java.io.IOException e) {
            throw android.util.ExceptionUtils.wrap(e);
        }
    }

    public void stageViaHardLink(java.lang.String str) {
        if (android.os.Binder.getCallingUid() != 1000) {
            throw new java.lang.SecurityException("link() can only be run by the system");
        }
        java.io.File file = new java.io.File(this.stageDir, new java.io.File(str).getName());
        java.lang.String absolutePath = file.getAbsolutePath();
        try {
            try {
                android.system.Os.link(str, absolutePath);
                android.system.Os.chmod(absolutePath, com.android.internal.util.FrameworkStatsLog.VBMETA_DIGEST_REPORTED);
            } catch (android.system.ErrnoException e) {
                e.rethrowAsIOException();
            }
            if (!android.os.SELinux.restorecon(file)) {
                throw new java.io.IOException("Can't relabel file: " + file);
            }
        } catch (java.io.IOException e2) {
            try {
                android.system.Os.unlink(absolutePath);
            } catch (java.lang.Exception e3) {
                android.util.Slog.d(TAG, "Failed to unlink session file: " + absolutePath);
            }
            throw android.util.ExceptionUtils.wrap(e2);
        }
    }

    private android.os.ParcelFileDescriptor openTargetInternal(java.lang.String str, int i, int i2) throws java.io.IOException, android.system.ErrnoException {
        return new android.os.ParcelFileDescriptor(android.system.Os.open(str, i, i2));
    }

    private android.os.ParcelFileDescriptor createRevocableFdInternal(android.os.RevocableFileDescriptor revocableFileDescriptor, android.os.ParcelFileDescriptor parcelFileDescriptor) throws java.io.IOException {
        int detachFd = parcelFileDescriptor.detachFd();
        java.io.FileDescriptor fileDescriptor = new java.io.FileDescriptor();
        fileDescriptor.setInt$(detachFd);
        revocableFileDescriptor.init(this.mContext, fileDescriptor);
        return revocableFileDescriptor.getRevocableFileDescriptor();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public android.os.ParcelFileDescriptor doWriteInternal(java.lang.String str, long j, long j2, android.os.ParcelFileDescriptor parcelFileDescriptor) throws java.io.IOException {
        android.os.FileBridge fileBridge;
        android.os.RevocableFileDescriptor revocableFileDescriptor;
        synchronized (this.mLock) {
            try {
                if (android.content.pm.PackageInstaller.ENABLE_REVOCABLE_FD) {
                    android.os.RevocableFileDescriptor revocableFileDescriptor2 = new android.os.RevocableFileDescriptor();
                    this.mFds.add(revocableFileDescriptor2);
                    revocableFileDescriptor = revocableFileDescriptor2;
                    fileBridge = null;
                } else {
                    android.os.FileBridge fileBridge2 = new android.os.FileBridge();
                    this.mBridges.add(fileBridge2);
                    fileBridge = fileBridge2;
                    revocableFileDescriptor = null;
                }
            } finally {
            }
        }
        try {
            if (!android.os.FileUtils.isValidExtFilename(str)) {
                throw new java.lang.IllegalArgumentException("Invalid name: " + str);
            }
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                java.io.File file = new java.io.File(this.stageDir, str);
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                int i = str.equals(com.android.server.pm.PackageManagerService.APP_METADATA_FILE_NAME) ? 416 : com.android.internal.util.FrameworkStatsLog.VBMETA_DIGEST_REPORTED;
                android.os.ParcelFileDescriptor openTargetInternal = openTargetInternal(file.getAbsolutePath(), android.system.OsConstants.O_CREAT | android.system.OsConstants.O_WRONLY, i);
                android.system.Os.chmod(file.getAbsolutePath(), i);
                if (this.stageDir != null && j2 > 0) {
                    ((android.os.storage.StorageManager) this.mContext.getSystemService(android.os.storage.StorageManager.class)).allocateBytes(openTargetInternal.getFileDescriptor(), j2, com.android.internal.content.InstallLocationUtils.translateAllocateFlags(this.params.installFlags));
                }
                if (j > 0) {
                    android.system.Os.lseek(openTargetInternal.getFileDescriptor(), j, android.system.OsConstants.SEEK_SET);
                }
                if (parcelFileDescriptor == null) {
                    if (android.content.pm.PackageInstaller.ENABLE_REVOCABLE_FD) {
                        return createRevocableFdInternal(revocableFileDescriptor, openTargetInternal);
                    }
                    fileBridge.setTargetFile(openTargetInternal);
                    fileBridge.start();
                    return fileBridge.getClientSocket();
                }
                try {
                    final android.system.Int64Ref int64Ref = new android.system.Int64Ref(0L);
                    android.os.FileUtils.copy(parcelFileDescriptor.getFileDescriptor(), openTargetInternal.getFileDescriptor(), j2, null, new com.android.server.SystemServerInitThreadPool$$ExternalSyntheticLambda0(), new android.os.FileUtils.ProgressListener() { // from class: com.android.server.pm.PackageInstallerSession$$ExternalSyntheticLambda11
                        @Override // android.os.FileUtils.ProgressListener
                        public final void onProgress(long j3) {
                            com.android.server.pm.PackageInstallerSession.this.lambda$doWriteInternal$0(int64Ref, j3);
                        }
                    });
                    libcore.io.IoUtils.closeQuietly(openTargetInternal);
                    libcore.io.IoUtils.closeQuietly(parcelFileDescriptor);
                    synchronized (this.mLock) {
                        try {
                            if (android.content.pm.PackageInstaller.ENABLE_REVOCABLE_FD) {
                                this.mFds.remove(revocableFileDescriptor);
                            } else {
                                fileBridge.forceClose();
                                this.mBridges.remove(fileBridge);
                            }
                        } catch (java.lang.Throwable th) {
                            throw th;
                        }
                    }
                    return null;
                } catch (java.lang.Throwable th2) {
                    libcore.io.IoUtils.closeQuietly(openTargetInternal);
                    libcore.io.IoUtils.closeQuietly(parcelFileDescriptor);
                    synchronized (this.mLock) {
                        try {
                            if (android.content.pm.PackageInstaller.ENABLE_REVOCABLE_FD) {
                                this.mFds.remove(revocableFileDescriptor);
                            } else {
                                fileBridge.forceClose();
                                this.mBridges.remove(fileBridge);
                            }
                            throw th2;
                        } finally {
                        }
                    }
                }
            } catch (java.lang.Throwable th3) {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                throw th3;
            }
        } catch (android.system.ErrnoException e) {
            throw e.rethrowAsIOException();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$doWriteInternal$0(android.system.Int64Ref int64Ref, long j) {
        if (this.params.sizeBytes > 0) {
            long j2 = j - int64Ref.value;
            int64Ref.value = j;
            synchronized (this.mProgressLock) {
                setClientProgressLocked(this.mClientProgress + (j2 / this.params.sizeBytes));
            }
        }
    }

    public android.os.ParcelFileDescriptor openRead(java.lang.String str) {
        android.os.ParcelFileDescriptor openReadInternalLocked;
        if (isDataLoaderInstallation()) {
            throw new java.lang.IllegalStateException("Cannot read regular files in a data loader installation session.");
        }
        assertCallerIsOwnerOrRoot();
        synchronized (this.mLock) {
            try {
                assertPreparedAndNotCommittedOrDestroyedLocked("openRead");
                try {
                    openReadInternalLocked = openReadInternalLocked(str);
                } catch (java.io.IOException e) {
                    throw android.util.ExceptionUtils.wrap(e);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return openReadInternalLocked;
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private android.os.ParcelFileDescriptor openReadInternalLocked(java.lang.String str) throws java.io.IOException {
        try {
            if (!android.os.FileUtils.isValidExtFilename(str)) {
                throw new java.lang.IllegalArgumentException("Invalid name: " + str);
            }
            return new android.os.ParcelFileDescriptor(android.system.Os.open(new java.io.File(this.stageDir, str).getAbsolutePath(), android.system.OsConstants.O_RDONLY, 0));
        } catch (android.system.ErrnoException e) {
            throw e.rethrowAsIOException();
        }
    }

    private void assertCallerIsOwnerRootOrVerifier() {
        int callingUid = android.os.Binder.getCallingUid();
        if (callingUid == 0 || callingUid == this.mInstallerUid) {
            return;
        }
        if (isSealed() && this.mContext.checkCallingOrSelfPermission("android.permission.PACKAGE_VERIFICATION_AGENT") == 0) {
            return;
        }
        throw new java.lang.SecurityException("Session does not belong to uid " + callingUid);
    }

    private void assertCallerIsOwnerOrRoot() {
        int callingUid = android.os.Binder.getCallingUid();
        if (callingUid != 0 && callingUid != this.mInstallerUid) {
            throw new java.lang.SecurityException("Session does not belong to uid " + callingUid);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void assertCallerIsOwnerOrRootOrSystem() {
        int callingUid = android.os.Binder.getCallingUid();
        if (callingUid != 0 && callingUid != this.mInstallerUid && callingUid != 1000) {
            throw new java.lang.SecurityException("Session does not belong to uid " + callingUid);
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void assertNoWriteFileTransfersOpenLocked() {
        java.util.Iterator<android.os.RevocableFileDescriptor> it = this.mFds.iterator();
        while (it.hasNext()) {
            if (!it.next().isRevoked()) {
                throw new java.lang.SecurityException("Files still open");
            }
        }
        java.util.Iterator<android.os.FileBridge> it2 = this.mBridges.iterator();
        while (it2.hasNext()) {
            if (!it2.next().isClosed()) {
                throw new java.lang.SecurityException("Files still open");
            }
        }
    }

    public void commit(@android.annotation.NonNull android.content.IntentSender intentSender, boolean z) {
        assertNotChild("commit");
        if (android.app.compat.CompatChanges.isChangeEnabled(THROW_EXCEPTION_COMMIT_WITH_IMMUTABLE_PENDING_INTENT, android.os.Binder.getCallingUid()) && intentSender.isImmutable()) {
            throw new java.lang.IllegalArgumentException("The commit() status receiver should come from a mutable PendingIntent");
        }
        if (!markAsSealed(intentSender, z)) {
            return;
        }
        if (isMultiPackage()) {
            synchronized (this.mLock) {
                try {
                    boolean z2 = false;
                    for (int size = this.mChildSessions.size() - 1; size >= 0; size--) {
                        if (!this.mChildSessions.valueAt(size).markAsSealed(null, z)) {
                            z2 = true;
                        }
                    }
                    if (z2) {
                        return;
                    }
                } finally {
                }
            }
        }
        java.io.File stagedAppMetadataFile = getStagedAppMetadataFile();
        if (stagedAppMetadataFile.exists()) {
            long appMetadataSizeLimit = getAppMetadataSizeLimit();
            if (stagedAppMetadataFile.length() > appMetadataSizeLimit) {
                stagedAppMetadataFile.delete();
                throw new java.lang.IllegalArgumentException("App metadata size exceeds the maximum allowed limit of " + appMetadataSizeLimit);
            }
            if (isIncrementalInstallation()) {
                stagedAppMetadataFile.renameTo(getTmpAppMetadataFile());
            }
        }
        dispatchSessionSealed();
    }

    public void seal() {
        assertNotChild("seal");
        assertCallerIsOwnerOrRoot();
        try {
            sealInternal();
            java.util.Iterator<com.android.server.pm.PackageInstallerSession> it = getChildSessions().iterator();
            while (it.hasNext()) {
                it.next().sealInternal();
            }
        } catch (com.android.server.pm.PackageManagerException e) {
            throw new java.lang.IllegalStateException("Package is not valid", e);
        }
    }

    private void sealInternal() throws com.android.server.pm.PackageManagerException {
        synchronized (this.mLock) {
            sealLocked();
        }
    }

    public java.util.List<java.lang.String> fetchPackageNames() {
        assertNotChild("fetchPackageNames");
        assertCallerIsOwnerOrRoot();
        java.util.List<com.android.server.pm.PackageInstallerSession> selfOrChildSessions = getSelfOrChildSessions();
        java.util.ArrayList arrayList = new java.util.ArrayList(selfOrChildSessions.size());
        java.util.Iterator<com.android.server.pm.PackageInstallerSession> it = selfOrChildSessions.iterator();
        while (it.hasNext()) {
            arrayList.add(it.next().fetchPackageName());
        }
        return arrayList;
    }

    private java.lang.String fetchPackageName() {
        java.lang.String packageName;
        assertSealed("fetchPackageName");
        synchronized (this.mLock) {
            try {
                android.content.pm.parsing.result.ParseTypeImpl forDefaultParsing = android.content.pm.parsing.result.ParseTypeImpl.forDefaultParsing();
                java.util.Iterator<java.io.File> it = getAddedApksLocked().iterator();
                while (it.hasNext()) {
                    android.content.pm.parsing.result.ParseResult parseApkLite = android.content.pm.parsing.ApkLiteParseUtils.parseApkLite(forDefaultParsing.reset(), it.next(), 0);
                    if (parseApkLite.isError()) {
                        throw new java.lang.IllegalStateException("Can't parse package for session=" + this.sessionId, parseApkLite.getException());
                    }
                    packageName = ((android.content.pm.parsing.ApkLite) parseApkLite.getResult()).getPackageName();
                    if (packageName != null) {
                    }
                }
                throw new java.lang.IllegalStateException("Can't fetch package name for session=" + this.sessionId);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return packageName;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dispatchSessionSealed() {
        this.mHandler.obtainMessage(1).sendToTarget();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleSessionSealed() {
        assertSealed("dispatchSessionSealed");
        this.mCallback.onSessionSealedBlocking(this);
        dispatchStreamValidateAndCommit();
    }

    private void dispatchStreamValidateAndCommit() {
        this.mHandler.obtainMessage(2).sendToTarget();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleStreamValidateAndCommit() {
        try {
            java.util.Iterator<com.android.server.pm.PackageInstallerSession> it = getChildSessions().iterator();
            boolean z = true;
            while (it.hasNext()) {
                z &= it.next().streamValidateAndCommit();
            }
            if (z && streamValidateAndCommit()) {
                this.mHandler.obtainMessage(3).sendToTarget();
            }
        } catch (com.android.server.pm.PackageManagerException e) {
            java.lang.String completeMessage = android.util.ExceptionUtils.getCompleteMessage(e);
            destroy(completeMessage);
            dispatchSessionFinished(e.error, completeMessage, null);
            maybeFinishChildSessions(e.error, completeMessage);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handlePreapprovalRequest() {
        if (sendPendingUserActionIntentIfNeeded(true)) {
            return;
        }
        dispatchSessionPreapproved();
    }

    private final class FileSystemConnector extends android.content.pm.IPackageInstallerSessionFileSystemConnector.Stub {
        final java.util.Set<java.lang.String> mAddedFiles = new android.util.ArraySet();

        FileSystemConnector(java.util.List<android.content.pm.InstallationFileParcel> list) {
            java.util.Iterator<android.content.pm.InstallationFileParcel> it = list.iterator();
            while (it.hasNext()) {
                this.mAddedFiles.add(it.next().name);
            }
        }

        public void writeData(java.lang.String str, long j, long j2, android.os.ParcelFileDescriptor parcelFileDescriptor) {
            if (parcelFileDescriptor == null) {
                throw new java.lang.IllegalArgumentException("incomingFd can't be null");
            }
            if (!this.mAddedFiles.contains(str)) {
                throw new java.lang.SecurityException("File name is not in the list of added files.");
            }
            try {
                com.android.server.pm.PackageInstallerSession.this.doWriteInternal(str, j, j2, parcelFileDescriptor);
            } catch (java.io.IOException e) {
                throw android.util.ExceptionUtils.wrap(e);
            }
        }
    }

    private static boolean isSecureFrpInstallAllowed(android.content.Context context, int i) {
        android.content.pm.PackageManagerInternal packageManagerInternal = (android.content.pm.PackageManagerInternal) com.android.server.LocalServices.getService(android.content.pm.PackageManagerInternal.class);
        java.lang.String[] knownPackageNames = packageManagerInternal.getKnownPackageNames(2, 0);
        com.android.server.pm.pkg.AndroidPackage androidPackage = packageManagerInternal.getPackage(i);
        return (androidPackage == null || !com.android.internal.util.ArrayUtils.contains(knownPackageNames, androidPackage.getPackageName())) && context.checkCallingOrSelfPermission("android.permission.INSTALL_PACKAGES") == 0;
    }

    private boolean isInstallationAllowed(com.android.server.pm.pkg.PackageStateInternal packageStateInternal) {
        if (packageStateInternal == null || packageStateInternal.getPkg() == null || packageStateInternal.getPkg().isUpdatableSystem()) {
            return true;
        }
        if (this.mOriginalInstallerUid == 0) {
            android.util.Slog.w(TAG, "Overriding updatableSystem because the installer is root: " + packageStateInternal.getPackageName());
            return true;
        }
        return false;
    }

    private static boolean isArchivedInstallationAllowed(com.android.server.pm.pkg.PackageStateInternal packageStateInternal) {
        if (packageStateInternal == null) {
            return true;
        }
        return false;
    }

    private static boolean isIncrementalInstallationAllowed(com.android.server.pm.pkg.PackageStateInternal packageStateInternal) {
        if (packageStateInternal == null || packageStateInternal.getPkg() == null) {
            return true;
        }
        return (packageStateInternal.isSystem() || packageStateInternal.isUpdatedSystemApp()) ? false : true;
    }

    /* JADX WARN: Code restructure failed: missing block: B:20:0x0060, code lost:
    
        r5.mContext.enforceCallingOrSelfPermission("android.permission.INSTALL_PACKAGES", null);
     */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x006c, code lost:
    
        if (r5.mInstallerUid == r5.mOriginalInstallerUid) goto L27;
     */
    /* JADX WARN: Code restructure failed: missing block: B:36:0x0076, code lost:
    
        throw new java.lang.IllegalArgumentException("Session has not been transferred");
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private boolean markAsSealed(@android.annotation.Nullable android.content.IntentSender intentSender, boolean z) {
        com.android.internal.util.Preconditions.checkState(intentSender != null || hasParentSessionId(), "statusReceiver can't be null for the root session");
        assertCallerIsOwnerOrRoot();
        synchronized (this.mLock) {
            try {
                assertPreparedAndNotDestroyedLocked("commit of session " + this.sessionId);
                assertNoWriteFileTransfersOpenLocked();
                if ((android.provider.Settings.Global.getInt(this.mContext.getContentResolver(), "secure_frp_mode", 0) == 1) && !isSecureFrpInstallAllowed(this.mContext, android.os.Binder.getCallingUid())) {
                    throw new java.lang.SecurityException("Can't install packages while in secure FRP");
                }
                if (this.mInstallerUid != this.mOriginalInstallerUid) {
                    throw new java.lang.IllegalArgumentException("Session has been transferred");
                }
                setRemoteStatusReceiver(intentSender);
                if (this.mSealed) {
                    return true;
                }
                try {
                    sealLocked();
                    return true;
                } catch (com.android.server.pm.PackageManagerException e) {
                    return false;
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private boolean streamValidateAndCommit() throws com.android.server.pm.PackageManagerException {
        try {
            synchronized (this.mLock) {
                try {
                    if (isCommitted()) {
                        return true;
                    }
                    if (!this.params.isMultiPackage) {
                        if (!prepareDataLoaderLocked()) {
                            return false;
                        }
                        if (isApexSession()) {
                            validateApexInstallLocked();
                        } else {
                            validateApkInstallLocked();
                        }
                    }
                    if (this.mDestroyed) {
                        throw new com.android.server.pm.PackageManagerException(android.hardware.biometrics.fingerprint.V2_1.RequestStatus.SYS_ETIMEDOUT, "Session destroyed");
                    }
                    if (!isIncrementalInstallation()) {
                        synchronized (this.mProgressLock) {
                            this.mClientProgress = 1.0f;
                            computeProgressLocked(true);
                        }
                    }
                    this.mActiveCount.incrementAndGet();
                    if (!this.mCommitted.compareAndSet(false, true)) {
                        throw new com.android.server.pm.PackageManagerException(android.hardware.biometrics.fingerprint.V2_1.RequestStatus.SYS_ETIMEDOUT, android.text.TextUtils.formatSimple("The mCommitted of session %d should be false originally", new java.lang.Object[]{java.lang.Integer.valueOf(this.sessionId)}));
                    }
                    this.committedMillis = java.lang.System.currentTimeMillis();
                    return true;
                } finally {
                }
            }
        } catch (com.android.server.pm.PackageManagerException e) {
            throw e;
        } catch (java.lang.Throwable th) {
            throw new com.android.server.pm.PackageManagerException(th);
        }
    }

    @android.annotation.NonNull
    @com.android.internal.annotations.GuardedBy({"mLock"})
    private java.util.List<com.android.server.pm.PackageInstallerSession> getChildSessionsLocked() {
        java.util.List<com.android.server.pm.PackageInstallerSession> list = java.util.Collections.EMPTY_LIST;
        if (isMultiPackage()) {
            int size = this.mChildSessions.size();
            java.util.ArrayList arrayList = new java.util.ArrayList(size);
            for (int i = 0; i < size; i++) {
                arrayList.add(this.mChildSessions.valueAt(i));
            }
            return arrayList;
        }
        return list;
    }

    @android.annotation.NonNull
    java.util.List<com.android.server.pm.PackageInstallerSession> getChildSessions() {
        java.util.List<com.android.server.pm.PackageInstallerSession> childSessionsLocked;
        synchronized (this.mLock) {
            childSessionsLocked = getChildSessionsLocked();
        }
        return childSessionsLocked;
    }

    @android.annotation.NonNull
    private java.util.List<com.android.server.pm.PackageInstallerSession> getSelfOrChildSessions() {
        return isMultiPackage() ? getChildSessions() : java.util.Collections.singletonList(this);
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void sealLocked() throws com.android.server.pm.PackageManagerException {
        try {
            assertNoWriteFileTransfersOpenLocked();
            assertPreparedAndNotDestroyedLocked("sealing of session " + this.sessionId);
            this.mSealed = true;
        } catch (java.lang.Throwable th) {
            throw onSessionValidationFailure(new com.android.server.pm.PackageManagerException(th));
        }
    }

    private com.android.server.pm.PackageManagerException onSessionValidationFailure(com.android.server.pm.PackageManagerException packageManagerException) {
        onSessionValidationFailure(packageManagerException.error, android.util.ExceptionUtils.getCompleteMessage(packageManagerException));
        return packageManagerException;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onSessionValidationFailure(int i, java.lang.String str) {
        destroyInternal("Failed to validate session, error: " + i + ", " + str);
        dispatchSessionFinished(i, str, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onSessionVerificationFailure(int i, java.lang.String str) {
        android.util.Slog.e(TAG, "Failed to verify session " + this.sessionId);
        dispatchSessionFinished(i, str, null);
        maybeFinishChildSessions(i, str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onSystemDataLoaderUnrecoverable() {
        final java.lang.String packageName = getPackageName();
        if (android.text.TextUtils.isEmpty(packageName)) {
            return;
        }
        this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.pm.PackageInstallerSession$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.pm.PackageInstallerSession.this.lambda$onSystemDataLoaderUnrecoverable$1(packageName);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onSystemDataLoaderUnrecoverable$1(java.lang.String str) {
        if (this.mPm.deletePackageX(str, -1L, 0, 2, true) != 1) {
            android.util.Slog.e(TAG, "Failed to uninstall package with failed dataloader: " + str);
        }
    }

    void onAfterSessionRead(android.util.SparseArray<com.android.server.pm.PackageInstallerSession> sparseArray) {
        com.android.server.pm.PackageInstallerSession packageInstallerSession;
        synchronized (this.mLock) {
            try {
                for (int size = this.mChildSessions.size() - 1; size >= 0; size--) {
                    int keyAt = this.mChildSessions.keyAt(size);
                    com.android.server.pm.PackageInstallerSession packageInstallerSession2 = sparseArray.get(keyAt);
                    if (packageInstallerSession2 != null) {
                        this.mChildSessions.setValueAt(size, packageInstallerSession2);
                    } else {
                        android.util.Slog.e(TAG, "Child session not existed: " + keyAt);
                        this.mChildSessions.removeAt(size);
                    }
                }
            } catch (com.android.server.pm.PackageManagerException e) {
                android.util.Slog.e(TAG, "Package not valid", e);
            } finally {
            }
            if (!this.mShouldBeSealed || isStagedAndInTerminalState()) {
                return;
            }
            sealLocked();
            if (!isMultiPackage() && isStaged() && isCommitted()) {
                if (hasParentSessionId()) {
                    packageInstallerSession = sparseArray.get(getParentSessionId());
                } else {
                    packageInstallerSession = this;
                }
                if (packageInstallerSession != null && !packageInstallerSession.isStagedAndInTerminalState()) {
                    if (isApexSession()) {
                        validateApexInstallLocked();
                    } else {
                        validateApkInstallLocked();
                    }
                }
            }
        }
    }

    public void markUpdated() {
        synchronized (this.mLock) {
            this.updatedMillis = java.lang.System.currentTimeMillis();
        }
    }

    public void transfer(java.lang.String str) {
        com.android.internal.util.Preconditions.checkArgument(!android.text.TextUtils.isEmpty(str));
        com.android.server.pm.Computer snapshotComputer = this.mPm.snapshotComputer();
        android.content.pm.ApplicationInfo applicationInfo = snapshotComputer.getApplicationInfo(str, 0L, this.userId);
        if (applicationInfo == null) {
            throw new android.os.ParcelableException(new android.content.pm.PackageManager.NameNotFoundException(str));
        }
        if (snapshotComputer.checkUidPermission("android.permission.INSTALL_PACKAGES", applicationInfo.uid) != 0) {
            throw new java.lang.SecurityException("Destination package " + str + " does not have the android.permission.INSTALL_PACKAGES permission");
        }
        if (!this.params.areHiddenOptionsSet()) {
            throw new java.lang.SecurityException("Can only transfer sessions that use public options");
        }
        synchronized (this.mLock) {
            try {
                assertCallerIsOwnerOrRoot();
                assertPreparedAndNotSealedLocked("transfer");
                try {
                    sealLocked();
                    this.mInstallerUid = applicationInfo.uid;
                    this.mInstallSource = com.android.server.pm.InstallSource.create(str, null, str, this.mInstallerUid, str, null, this.params.packageSource);
                } catch (com.android.server.pm.PackageManagerException e) {
                    throw new java.lang.IllegalStateException("Package is not valid", e);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private static boolean checkUserActionRequirement(com.android.server.pm.PackageInstallerSession packageInstallerSession, android.content.IntentSender intentSender) {
        if (packageInstallerSession.isMultiPackage()) {
            return false;
        }
        int computeUserActionRequirement = packageInstallerSession.computeUserActionRequirement();
        packageInstallerSession.updateUserActionRequirement(computeUserActionRequirement);
        if (computeUserActionRequirement == 1 || computeUserActionRequirement == 3) {
            packageInstallerSession.sendPendingUserActionIntent(intentSender);
            return true;
        }
        if (!packageInstallerSession.isApexSession() && computeUserActionRequirement == 2) {
            if (!isTargetSdkConditionSatisfied(packageInstallerSession)) {
                packageInstallerSession.sendPendingUserActionIntent(intentSender);
                return true;
            }
            if (packageInstallerSession.params.requireUserAction == 2) {
                if (!packageInstallerSession.mSilentUpdatePolicy.isSilentUpdateAllowed(packageInstallerSession.getInstallerPackageName(), packageInstallerSession.getPackageName())) {
                    packageInstallerSession.sendPendingUserActionIntent(intentSender);
                    return true;
                }
                packageInstallerSession.mSilentUpdatePolicy.track(packageInstallerSession.getInstallerPackageName(), packageInstallerSession.getPackageName());
            }
        }
        return false;
    }

    private static boolean isTargetSdkConditionSatisfied(com.android.server.pm.PackageInstallerSession packageInstallerSession) {
        int i;
        java.lang.String str;
        synchronized (packageInstallerSession.mLock) {
            i = packageInstallerSession.mValidatedTargetSdk;
            str = packageInstallerSession.mPackageName;
        }
        android.content.pm.ApplicationInfo applicationInfo = new android.content.pm.ApplicationInfo();
        applicationInfo.packageName = str;
        applicationInfo.targetSdkVersion = i;
        com.android.internal.compat.IPlatformCompat asInterface = com.android.internal.compat.IPlatformCompat.Stub.asInterface(android.os.ServiceManager.getService("platform_compat"));
        if (i == Integer.MAX_VALUE) {
            return false;
        }
        try {
            return asInterface.isChangeEnabled(SILENT_INSTALL_ALLOWED, applicationInfo);
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Failed to get a response from PLATFORM_COMPAT_SERVICE", e);
            return false;
        }
    }

    private static int userActionRequirementToReason(@com.android.server.pm.PackageInstallerSession.UserActionRequirement int i) {
        switch (i) {
            case 3:
                return 2;
            default:
                return 0;
        }
    }

    private boolean sendPendingUserActionIntentIfNeeded(boolean z) {
        if (isCommitted()) {
            assertNotChild("PackageInstallerSession#sendPendingUserActionIntentIfNeeded");
        }
        final android.content.IntentSender preapprovalRemoteStatusReceiver = z ? getPreapprovalRemoteStatusReceiver() : getRemoteStatusReceiver();
        return sessionContains(new java.util.function.Predicate() { // from class: com.android.server.pm.PackageInstallerSession$$ExternalSyntheticLambda12
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                boolean lambda$sendPendingUserActionIntentIfNeeded$2;
                lambda$sendPendingUserActionIntentIfNeeded$2 = com.android.server.pm.PackageInstallerSession.lambda$sendPendingUserActionIntentIfNeeded$2(preapprovalRemoteStatusReceiver, (com.android.server.pm.PackageInstallerSession) obj);
                return lambda$sendPendingUserActionIntentIfNeeded$2;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$sendPendingUserActionIntentIfNeeded$2(android.content.IntentSender intentSender, com.android.server.pm.PackageInstallerSession packageInstallerSession) {
        return checkUserActionRequirement(packageInstallerSession, intentSender);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleInstall() {
        if (isInstallerDeviceOwnerOrAffiliatedProfileOwner()) {
            android.app.admin.DevicePolicyEventLogger.createEvent(112).setAdmin(getInstallSource().mInstallerPackageName).write();
        }
        boolean sendPendingUserActionIntentIfNeeded = sendPendingUserActionIntentIfNeeded(false);
        if (this.mUserActionRequired == null) {
            this.mUserActionRequired = java.lang.Boolean.valueOf(sendPendingUserActionIntentIfNeeded);
        }
        if (sendPendingUserActionIntentIfNeeded) {
            deactivate();
            return;
        }
        if (this.mUserActionRequired.booleanValue()) {
            activate();
        }
        if (this.mVerificationInProgress) {
            android.util.Slog.w(TAG, "Verification is already in progress for session " + this.sessionId);
            return;
        }
        this.mVerificationInProgress = true;
        if (this.params.isStaged) {
            this.mStagedSession.verifySession();
        } else {
            verify();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void verify() {
        try {
            java.util.List<com.android.server.pm.PackageInstallerSession> childSessions = getChildSessions();
            if (isMultiPackage()) {
                for (com.android.server.pm.PackageInstallerSession packageInstallerSession : childSessions) {
                    packageInstallerSession.prepareInheritedFiles();
                    packageInstallerSession.parseApkAndExtractNativeLibraries();
                }
            } else {
                prepareInheritedFiles();
                parseApkAndExtractNativeLibraries();
            }
            verifyNonStaged();
        } catch (com.android.server.pm.PackageManagerException e) {
            java.lang.String installStatusToString = android.content.pm.PackageManager.installStatusToString(e.error, android.util.ExceptionUtils.getCompleteMessage(e));
            setSessionFailed(e.error, installStatusToString);
            onSessionVerificationFailure(e.error, installStatusToString);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public android.content.IntentSender getRemoteStatusReceiver() {
        android.content.IntentSender intentSender;
        synchronized (this.mLock) {
            intentSender = this.mRemoteStatusReceiver;
        }
        return intentSender;
    }

    private void setRemoteStatusReceiver(android.content.IntentSender intentSender) {
        synchronized (this.mLock) {
            this.mRemoteStatusReceiver = intentSender;
        }
    }

    private android.content.IntentSender getPreapprovalRemoteStatusReceiver() {
        android.content.IntentSender intentSender;
        synchronized (this.mLock) {
            intentSender = this.mPreapprovalRemoteStatusReceiver;
        }
        return intentSender;
    }

    private void setPreapprovalRemoteStatusReceiver(android.content.IntentSender intentSender) {
        synchronized (this.mLock) {
            this.mPreapprovalRemoteStatusReceiver = intentSender;
        }
    }

    private void prepareInheritedFiles() throws com.android.server.pm.PackageManagerException {
        if (isApexSession() || this.params.mode != 2) {
            return;
        }
        synchronized (this.mLock) {
            try {
                if (this.mStageDirInUse) {
                    throw new com.android.server.pm.PackageManagerException(android.hardware.biometrics.fingerprint.V2_1.RequestStatus.SYS_ETIMEDOUT, "Session files in use");
                }
                if (this.mDestroyed) {
                    throw new com.android.server.pm.PackageManagerException(android.hardware.biometrics.fingerprint.V2_1.RequestStatus.SYS_ETIMEDOUT, "Session destroyed");
                }
                if (!this.mSealed) {
                    throw new com.android.server.pm.PackageManagerException(android.hardware.biometrics.fingerprint.V2_1.RequestStatus.SYS_ETIMEDOUT, "Session not sealed");
                }
                try {
                    java.util.List<java.io.File> list = this.mResolvedInheritedFiles;
                    java.io.File file = this.stageDir;
                    java.lang.String name = file.getName();
                    android.util.Slog.d(TAG, "Inherited files: " + this.mResolvedInheritedFiles);
                    if (!this.mResolvedInheritedFiles.isEmpty() && this.mInheritedFilesBase == null) {
                        throw new java.lang.IllegalStateException("mInheritedFilesBase == null");
                    }
                    if (isLinkPossible(list, file)) {
                        if (!this.mResolvedInstructionSets.isEmpty()) {
                            createOatDirs(name, this.mResolvedInstructionSets, new java.io.File(file, "oat"));
                        }
                        if (!this.mResolvedNativeLibPaths.isEmpty()) {
                            for (java.lang.String str : this.mResolvedNativeLibPaths) {
                                int lastIndexOf = str.lastIndexOf(47);
                                if (lastIndexOf < 0 || lastIndexOf >= str.length() - 1) {
                                    android.util.Slog.e(TAG, "Skipping native library creation for linking due to invalid path: " + str);
                                } else {
                                    java.io.File file2 = new java.io.File(file, str.substring(1, lastIndexOf));
                                    if (!file2.exists()) {
                                        com.android.internal.content.NativeLibraryHelper.createNativeLibrarySubdir(file2);
                                    }
                                    com.android.internal.content.NativeLibraryHelper.createNativeLibrarySubdir(new java.io.File(file2, str.substring(lastIndexOf + 1)));
                                }
                            }
                        }
                        linkFiles(name, list, file, this.mInheritedFilesBase);
                    } else {
                        copyFiles(list, file);
                    }
                } catch (java.io.IOException e) {
                    throw new com.android.server.pm.PackageManagerException(-4, "Failed to inherit existing install", e);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void markStageDirInUseLocked() throws com.android.server.pm.PackageManagerException {
        if (this.mDestroyed) {
            throw new com.android.server.pm.PackageManagerException(android.hardware.biometrics.fingerprint.V2_1.RequestStatus.SYS_ETIMEDOUT, "Session destroyed");
        }
        this.mStageDirInUse = true;
    }

    private void parseApkAndExtractNativeLibraries() throws com.android.server.pm.PackageManagerException {
        android.content.pm.parsing.PackageLite orParsePackageLiteLocked;
        synchronized (this.mLock) {
            try {
                if (this.mStageDirInUse) {
                    throw new com.android.server.pm.PackageManagerException(android.hardware.biometrics.fingerprint.V2_1.RequestStatus.SYS_ETIMEDOUT, "Session files in use");
                }
                if (this.mDestroyed) {
                    throw new com.android.server.pm.PackageManagerException(android.hardware.biometrics.fingerprint.V2_1.RequestStatus.SYS_ETIMEDOUT, "Session destroyed");
                }
                if (!this.mSealed) {
                    throw new com.android.server.pm.PackageManagerException(android.hardware.biometrics.fingerprint.V2_1.RequestStatus.SYS_ETIMEDOUT, "Session not sealed");
                }
                if (this.mPackageName == null) {
                    throw new com.android.server.pm.PackageManagerException(android.hardware.biometrics.fingerprint.V2_1.RequestStatus.SYS_ETIMEDOUT, "Session no package name");
                }
                if (this.mSigningDetails == null) {
                    throw new com.android.server.pm.PackageManagerException(android.hardware.biometrics.fingerprint.V2_1.RequestStatus.SYS_ETIMEDOUT, "Session no signing data");
                }
                if (this.mResolvedBaseFile == null) {
                    throw new com.android.server.pm.PackageManagerException(android.hardware.biometrics.fingerprint.V2_1.RequestStatus.SYS_ETIMEDOUT, "Session no resolved base file");
                }
                if (!isApexSession()) {
                    orParsePackageLiteLocked = getOrParsePackageLiteLocked(this.stageDir, 0);
                } else {
                    orParsePackageLiteLocked = getOrParsePackageLiteLocked(this.mResolvedBaseFile, 0);
                }
                if (orParsePackageLiteLocked != null) {
                    this.mPackageLite = orParsePackageLiteLocked;
                    if (!isApexSession()) {
                        synchronized (this.mProgressLock) {
                            this.mInternalProgress = 0.5f;
                            computeProgressLocked(true);
                        }
                        extractNativeLibraries(this.mPackageLite, this.stageDir, this.params.abiOverride, mayInheritNativeLibs());
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private void verifyNonStaged() throws com.android.server.pm.PackageManagerException {
        synchronized (this.mLock) {
            markStageDirInUseLocked();
        }
        this.mSessionProvider.getSessionVerifier().verify(this, new com.android.server.pm.PackageSessionVerifier.Callback() { // from class: com.android.server.pm.PackageInstallerSession$$ExternalSyntheticLambda6
            @Override // com.android.server.pm.PackageSessionVerifier.Callback
            public final void onResult(int i, java.lang.String str) {
                com.android.server.pm.PackageInstallerSession.this.lambda$verifyNonStaged$4(i, str);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$verifyNonStaged$4(final int i, final java.lang.String str) {
        this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.pm.PackageInstallerSession$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.pm.PackageInstallerSession.this.lambda$verifyNonStaged$3(i, str);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$verifyNonStaged$3(int i, java.lang.String str) {
        if (dispatchPendingAbandonCallback()) {
            return;
        }
        if (i == 1) {
            onVerificationComplete();
        } else {
            onSessionVerificationFailure(i, str);
        }
    }

    private static class InstallResult {
        public final android.os.Bundle extras;
        public final com.android.server.pm.PackageInstallerSession session;

        InstallResult(com.android.server.pm.PackageInstallerSession packageInstallerSession, android.os.Bundle bundle) {
            this.session = packageInstallerSession;
            this.extras = bundle;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public java.util.concurrent.CompletableFuture<java.lang.Void> install() {
        final java.util.List<java.util.concurrent.CompletableFuture<com.android.server.pm.PackageInstallerSession.InstallResult>> installNonStaged = installNonStaged();
        return java.util.concurrent.CompletableFuture.allOf((java.util.concurrent.CompletableFuture[]) installNonStaged.toArray(new java.util.concurrent.CompletableFuture[installNonStaged.size()])).whenComplete(new java.util.function.BiConsumer() { // from class: com.android.server.pm.PackageInstallerSession$$ExternalSyntheticLambda9
            @Override // java.util.function.BiConsumer
            public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                com.android.server.pm.PackageInstallerSession.this.lambda$install$5(installNonStaged, (java.lang.Void) obj, (java.lang.Throwable) obj2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$install$5(java.util.List list, java.lang.Void r6, java.lang.Throwable th) {
        if (th == null) {
            setSessionApplied();
            java.util.ArrayList<java.lang.String> arrayList = new java.util.ArrayList<>();
            if (isMultiPackage()) {
                java.util.Iterator it = list.iterator();
                while (it.hasNext()) {
                    com.android.server.pm.PackageInstallerSession.InstallResult installResult = (com.android.server.pm.PackageInstallerSession.InstallResult) ((java.util.concurrent.CompletableFuture) it.next()).join();
                    if (installResult.session != this && installResult.extras != null) {
                        java.util.ArrayList<java.lang.String> stringArrayList = installResult.extras.getStringArrayList("android.content.pm.extra.WARNINGS");
                        if (!com.android.internal.util.ArrayUtils.isEmpty(stringArrayList)) {
                            arrayList.addAll(stringArrayList);
                        }
                    }
                }
            }
            java.util.Iterator it2 = list.iterator();
            while (it2.hasNext()) {
                com.android.server.pm.PackageInstallerSession.InstallResult installResult2 = (com.android.server.pm.PackageInstallerSession.InstallResult) ((java.util.concurrent.CompletableFuture) it2.next()).join();
                android.os.Bundle bundle = installResult2.extras;
                if (isMultiPackage() && installResult2.session == this && !arrayList.isEmpty()) {
                    if (bundle == null) {
                        bundle = new android.os.Bundle();
                    }
                    bundle.putStringArrayList("android.content.pm.extra.WARNINGS", arrayList);
                }
                installResult2.session.dispatchSessionFinished(1, "Session installed", bundle);
            }
            return;
        }
        com.android.server.pm.PackageManagerException packageManagerException = (com.android.server.pm.PackageManagerException) th.getCause();
        setSessionFailed(packageManagerException.error, android.content.pm.PackageManager.installStatusToString(packageManagerException.error, packageManagerException.getMessage()));
        dispatchSessionFinished(packageManagerException.error, packageManagerException.getMessage(), null);
        maybeFinishChildSessions(packageManagerException.error, packageManagerException.getMessage());
    }

    private java.util.List<java.util.concurrent.CompletableFuture<com.android.server.pm.PackageInstallerSession.InstallResult>> installNonStaged() {
        try {
            java.util.ArrayList arrayList = new java.util.ArrayList();
            java.util.concurrent.CompletableFuture<com.android.server.pm.PackageInstallerSession.InstallResult> completableFuture = new java.util.concurrent.CompletableFuture<>();
            arrayList.add(completableFuture);
            com.android.server.pm.InstallingSession createInstallingSession = createInstallingSession(completableFuture);
            if (isMultiPackage()) {
                java.util.List<com.android.server.pm.PackageInstallerSession> childSessions = getChildSessions();
                java.util.ArrayList arrayList2 = new java.util.ArrayList(childSessions.size());
                for (int i = 0; i < childSessions.size(); i++) {
                    com.android.server.pm.PackageInstallerSession packageInstallerSession = childSessions.get(i);
                    java.util.concurrent.CompletableFuture<com.android.server.pm.PackageInstallerSession.InstallResult> completableFuture2 = new java.util.concurrent.CompletableFuture<>();
                    arrayList.add(completableFuture2);
                    com.android.server.pm.InstallingSession createInstallingSession2 = packageInstallerSession.createInstallingSession(completableFuture2);
                    if (createInstallingSession2 != null) {
                        arrayList2.add(createInstallingSession2);
                    }
                }
                if (!arrayList2.isEmpty()) {
                    java.util.Objects.requireNonNull(createInstallingSession);
                    com.android.server.pm.InstallingSession installingSession = createInstallingSession;
                    createInstallingSession.installStage(arrayList2);
                }
            } else if (createInstallingSession != null) {
                createInstallingSession.installStage();
                return arrayList;
            }
            return arrayList;
        } catch (com.android.server.pm.PackageManagerException e) {
            java.util.ArrayList arrayList3 = new java.util.ArrayList();
            arrayList3.add(java.util.concurrent.CompletableFuture.failedFuture(e));
            return arrayList3;
        }
    }

    private void sendPendingUserActionIntent(android.content.IntentSender intentSender) {
        android.content.Intent intent = new android.content.Intent(isPreapprovalRequested() && !isCommitted() ? "android.content.pm.action.CONFIRM_PRE_APPROVAL" : "android.content.pm.action.CONFIRM_INSTALL");
        intent.setPackage(this.mPm.getPackageInstallerPackageName());
        intent.putExtra("android.content.pm.extra.SESSION_ID", this.sessionId);
        sendOnUserActionRequired(this.mContext, intentSender, this.sessionId, intent);
    }

    private void onVerificationComplete() {
        if (isStaged()) {
            this.mStagingManager.commitSession(this.mStagedSession);
            sendUpdateToRemoteStatusReceiver(1, "Session staged", null, false);
        } else {
            install();
        }
    }

    @android.annotation.Nullable
    private com.android.server.pm.InstallingSession createInstallingSession(final java.util.concurrent.CompletableFuture<com.android.server.pm.PackageInstallerSession.InstallResult> completableFuture) throws com.android.server.pm.PackageManagerException {
        android.os.UserHandle userHandle;
        com.android.server.pm.InstallingSession installingSession;
        synchronized (this.mLock) {
            if (!this.mSealed) {
                throw new com.android.server.pm.PackageManagerException(android.hardware.biometrics.fingerprint.V2_1.RequestStatus.SYS_ETIMEDOUT, "Session not sealed");
            }
            markStageDirInUseLocked();
        }
        if (isMultiPackage()) {
            completableFuture.complete(new com.android.server.pm.PackageInstallerSession.InstallResult(this, null));
        } else if (isApexSession() && this.params.isStaged) {
            completableFuture.complete(new com.android.server.pm.PackageInstallerSession.InstallResult(this, null));
            return null;
        }
        android.content.pm.IPackageInstallObserver2.Stub stub = new android.content.pm.IPackageInstallObserver2.Stub() { // from class: com.android.server.pm.PackageInstallerSession.5
            public void onUserActionRequired(android.content.Intent intent) {
                throw new java.lang.IllegalStateException();
            }

            public void onPackageInstalled(java.lang.String str, int i, java.lang.String str2, android.os.Bundle bundle) {
                if (i == 1) {
                    completableFuture.complete(new com.android.server.pm.PackageInstallerSession.InstallResult(com.android.server.pm.PackageInstallerSession.this, bundle));
                } else {
                    completableFuture.completeExceptionally(new com.android.server.pm.PackageManagerException(i, str2));
                }
            }
        };
        if ((this.params.installFlags & 64) != 0) {
            userHandle = android.os.UserHandle.ALL;
        } else {
            userHandle = new android.os.UserHandle(this.userId);
        }
        if (this.params.isStaged) {
            this.params.installFlags |= 2097152;
        }
        if (!isMultiPackage() && !isApexSession()) {
            synchronized (this.mLock) {
                try {
                    if (this.mPackageLite == null) {
                        android.util.Slog.wtf(TAG, "Session: " + this.sessionId + ". Don't have a valid PackageLite.");
                    }
                    this.mPackageLite = getOrParsePackageLiteLocked(this.stageDir, 0);
                } finally {
                }
            }
        }
        synchronized (this.mLock) {
            installingSession = new com.android.server.pm.InstallingSession(this.sessionId, this.stageDir, stub, this.params, this.mInstallSource, userHandle, this.mSigningDetails, this.mInstallerUid, this.mPackageLite, this.mPreVerifiedDomains, this.mPm);
        }
        return installingSession;
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private android.content.pm.parsing.PackageLite getOrParsePackageLiteLocked(java.io.File file, int i) throws com.android.server.pm.PackageManagerException {
        if (this.mPackageLite != null) {
            return this.mPackageLite;
        }
        android.content.pm.parsing.result.ParseResult parsePackageLite = android.content.pm.parsing.ApkLiteParseUtils.parsePackageLite(android.content.pm.parsing.result.ParseTypeImpl.forDefaultParsing(), file, i);
        if (parsePackageLite.isError()) {
            throw new com.android.server.pm.PackageManagerException(android.hardware.biometrics.fingerprint.V2_1.RequestStatus.SYS_ETIMEDOUT, parsePackageLite.getErrorMessage(), parsePackageLite.getException());
        }
        return (android.content.pm.parsing.PackageLite) parsePackageLite.getResult();
    }

    private static void maybeRenameFile(java.io.File file, java.io.File file2) throws com.android.server.pm.PackageManagerException {
        if (!file.equals(file2) && !file.renameTo(file2)) {
            throw new com.android.server.pm.PackageManagerException(android.hardware.biometrics.fingerprint.V2_1.RequestStatus.SYS_ETIMEDOUT, "Could not rename file " + file + " to " + file2);
        }
    }

    private void logDataLoaderInstallationSession(int i) {
        int packageUid;
        java.lang.String packageName = getPackageName();
        java.lang.String str = (this.params.installFlags & 32) == 0 ? packageName : "";
        long currentTimeMillis = java.lang.System.currentTimeMillis();
        if (i != 1) {
            packageUid = -1;
        } else {
            packageUid = this.mPm.snapshotComputer().getPackageUid(packageName, 0L, this.userId);
        }
        com.android.internal.util.FrameworkStatsLog.write(263, isIncrementalInstallation(), str, currentTimeMillis - this.createdMillis, i, getApksSize(packageName), packageUid);
    }

    private long getApksSize(java.lang.String str) {
        java.io.File path;
        com.android.server.pm.pkg.PackageStateInternal packageStateInternal = ((android.content.pm.PackageManagerInternal) com.android.server.LocalServices.getService(android.content.pm.PackageManagerInternal.class)).getPackageStateInternal(str);
        long j = 0;
        if (packageStateInternal == null || (path = packageStateInternal.getPath()) == null) {
            return 0L;
        }
        if (path.isFile() && path.getName().toLowerCase().endsWith(".apk")) {
            return path.length();
        }
        if (!path.isDirectory()) {
            return 0L;
        }
        java.io.File[] listFiles = path.listFiles();
        for (int i = 0; i < listFiles.length; i++) {
            if (listFiles[i].getName().toLowerCase().endsWith(".apk")) {
                j += listFiles[i].length();
            }
        }
        return j;
    }

    private boolean mayInheritNativeLibs() {
        return android.os.SystemProperties.getBoolean(PROPERTY_NAME_INHERIT_NATIVE, true) && this.params.mode == 2 && (this.params.installFlags & 1) != 0;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public boolean isApexSession() {
        return (this.params.installFlags & 131072) != 0;
    }

    boolean sessionContains(java.util.function.Predicate<com.android.server.pm.PackageInstallerSession> predicate) {
        java.util.List<com.android.server.pm.PackageInstallerSession> childSessionsLocked;
        if (!isMultiPackage()) {
            return predicate.test(this);
        }
        synchronized (this.mLock) {
            childSessionsLocked = getChildSessionsLocked();
        }
        java.util.Iterator<com.android.server.pm.PackageInstallerSession> it = childSessionsLocked.iterator();
        while (it.hasNext()) {
            if (predicate.test(it.next())) {
                return true;
            }
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$containsApkSession$6(com.android.server.pm.PackageInstallerSession packageInstallerSession) {
        return !packageInstallerSession.isApexSession();
    }

    boolean containsApkSession() {
        return sessionContains(new java.util.function.Predicate() { // from class: com.android.server.pm.PackageInstallerSession$$ExternalSyntheticLambda5
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                boolean lambda$containsApkSession$6;
                lambda$containsApkSession$6 = com.android.server.pm.PackageInstallerSession.lambda$containsApkSession$6((com.android.server.pm.PackageInstallerSession) obj);
                return lambda$containsApkSession$6;
            }
        });
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void validateApexInstallLocked() throws com.android.server.pm.PackageManagerException {
        java.util.List<java.io.File> addedApksLocked = getAddedApksLocked();
        if (addedApksLocked.isEmpty()) {
            throw new com.android.server.pm.PackageManagerException(-2, android.text.TextUtils.formatSimple("Session: %d. No packages staged in %s", new java.lang.Object[]{java.lang.Integer.valueOf(this.sessionId), this.stageDir.getAbsolutePath()}));
        }
        if (com.android.internal.util.ArrayUtils.size(addedApksLocked) > 1) {
            throw new com.android.server.pm.PackageManagerException(-2, "Too many files for apex install");
        }
        java.io.File file = addedApksLocked.get(0);
        java.lang.String name = file.getName();
        if (!name.endsWith(APEX_FILE_EXTENSION)) {
            name = name + APEX_FILE_EXTENSION;
        }
        if (!android.os.FileUtils.isValidExtFilename(name)) {
            throw new com.android.server.pm.PackageManagerException(-2, "Invalid filename: " + name);
        }
        java.io.File file2 = new java.io.File(this.stageDir, name);
        resolveAndStageFileLocked(file, file2, null);
        this.mResolvedBaseFile = file2;
        this.mPackageName = null;
        android.content.pm.parsing.result.ParseResult parseApkLite = android.content.pm.parsing.ApkLiteParseUtils.parseApkLite(android.content.pm.parsing.result.ParseTypeImpl.forDefaultParsing().reset(), this.mResolvedBaseFile, 32);
        if (parseApkLite.isError()) {
            throw new com.android.server.pm.PackageManagerException(parseApkLite.getErrorCode(), parseApkLite.getErrorMessage(), parseApkLite.getException());
        }
        android.content.pm.parsing.ApkLite apkLite = (android.content.pm.parsing.ApkLite) parseApkLite.getResult();
        if (this.mPackageName == null) {
            this.mPackageName = apkLite.getPackageName();
            this.mVersionCode = apkLite.getLongVersionCode();
        }
        this.mSigningDetails = apkLite.getSigningDetails();
        this.mHasDeviceAdminReceiver = apkLite.isHasDeviceAdminReceiver();
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private android.content.pm.parsing.PackageLite validateApkInstallLocked() throws com.android.server.pm.PackageManagerException {
        android.content.pm.PackageInfo packageInfo;
        boolean z;
        boolean z2;
        android.content.pm.parsing.PackageLite packageLite;
        java.io.File file;
        java.io.File[] fileArr;
        java.io.File[] fileArr2;
        java.io.File file2;
        java.io.File[] listFiles;
        java.io.File[] fileArr3;
        java.lang.String installerPackageName;
        this.mPackageLite = null;
        this.mPackageName = null;
        this.mVersionCode = -1L;
        this.mSigningDetails = android.content.pm.SigningDetails.UNKNOWN;
        this.mResolvedBaseFile = null;
        this.mResolvedStagedFiles.clear();
        this.mResolvedInheritedFiles.clear();
        android.content.pm.PackageInfo packageInfo2 = this.mPm.snapshotComputer().getPackageInfo(this.params.appPackageName, 67108928L, this.userId);
        int i = -2;
        if (this.params.mode != 2 || (packageInfo2 != null && packageInfo2.applicationInfo != null)) {
            this.mVerityFoundForApks = com.android.server.pm.PackageManagerServiceUtils.isApkVerityEnabled() && this.params.mode == 2 && com.android.internal.security.VerityUtils.hasFsverity(packageInfo2.applicationInfo.getBaseCodePath()) && new java.io.File(com.android.internal.security.VerityUtils.getFsveritySignatureFilePath(packageInfo2.applicationInfo.getBaseCodePath())).exists();
            java.util.List<java.io.File> removedFilesLocked = getRemovedFilesLocked();
            java.util.ArrayList<java.lang.String> arrayList = new java.util.ArrayList();
            if (!removedFilesLocked.isEmpty()) {
                java.util.Iterator<java.io.File> it = removedFilesLocked.iterator();
                while (it.hasNext()) {
                    java.lang.String name = it.next().getName();
                    arrayList.add(name.substring(0, name.length() - REMOVE_MARKER_EXTENSION.length()));
                }
            }
            if (android.security.Flags.extendVbChainToUpdatedApk() && !isIncrementalInstallation()) {
                enableFsVerityToAddedApksWithIdsig();
            }
            java.util.List<android.content.pm.parsing.ApkLite> addedApkLitesLocked = getAddedApkLitesLocked();
            if (addedApkLitesLocked.isEmpty() && (arrayList.size() == 0 || getStagedAppMetadataFile().exists())) {
                throw new com.android.server.pm.PackageManagerException(-2, android.text.TextUtils.formatSimple("Session: %d. No packages staged in %s", new java.lang.Object[]{java.lang.Integer.valueOf(this.sessionId), this.stageDir.getAbsolutePath()}));
            }
            android.util.ArraySet arraySet = new android.util.ArraySet();
            android.util.ArraySet arraySet2 = new android.util.ArraySet();
            android.util.ArraySet arraySet3 = new android.util.ArraySet();
            android.util.ArrayMap arrayMap = new android.util.ArrayMap();
            android.content.pm.parsing.result.ParseTypeImpl forDefaultParsing = android.content.pm.parsing.result.ParseTypeImpl.forDefaultParsing();
            android.content.pm.parsing.ApkLite apkLite = null;
            for (android.content.pm.parsing.ApkLite apkLite2 : addedApkLitesLocked) {
                if (!arraySet.add(apkLite2.getSplitName())) {
                    throw new com.android.server.pm.PackageManagerException(-2, "Split " + apkLite2.getSplitName() + " was defined multiple times");
                }
                if (!apkLite2.isUpdatableSystem()) {
                    if (this.mOriginalInstallerUid == 0) {
                        android.util.Slog.w(TAG, "Overriding updatableSystem because the installer is root for: " + apkLite2.getPackageName());
                    } else {
                        throw new com.android.server.pm.PackageManagerException(i, "Non updatable system package can't be installed or updated");
                    }
                }
                if (this.mPackageName == null) {
                    this.mPackageName = apkLite2.getPackageName();
                    this.mVersionCode = apkLite2.getLongVersionCode();
                }
                if (this.mSigningDetails == android.content.pm.SigningDetails.UNKNOWN) {
                    this.mSigningDetails = apkLite2.getSigningDetails();
                }
                this.mHasDeviceAdminReceiver = apkLite2.isHasDeviceAdminReceiver();
                assertApkConsistentLocked(java.lang.String.valueOf(apkLite2), apkLite2);
                java.lang.String splitNameToFileName = android.content.pm.parsing.ApkLiteParseUtils.splitNameToFileName(apkLite2);
                if (!android.os.FileUtils.isValidExtFilename(splitNameToFileName)) {
                    throw new com.android.server.pm.PackageManagerException(-2, "Invalid filename: " + splitNameToFileName);
                }
                if (apkLite2.getInstallLocation() != -1 && (installerPackageName = getInstallerPackageName()) != null && this.params.installLocation != apkLite2.getInstallLocation()) {
                    android.util.Slog.wtf(TAG, installerPackageName + " drops manifest attribute android:installLocation in " + splitNameToFileName + " for " + this.mPackageName);
                }
                java.io.File file3 = new java.io.File(this.stageDir, splitNameToFileName);
                if (!isArchivedInstallation()) {
                    resolveAndStageFileLocked(new java.io.File(apkLite2.getPath()), file3, apkLite2.getSplitName());
                }
                if (apkLite2.getSplitName() == null) {
                    this.mResolvedBaseFile = file3;
                    apkLite = apkLite2;
                } else {
                    arrayMap.put(apkLite2.getSplitName(), apkLite2);
                }
                com.android.internal.util.CollectionUtils.addAll(arraySet3, apkLite2.getRequiredSplitTypes());
                com.android.internal.util.CollectionUtils.addAll(arraySet2, apkLite2.getSplitTypes());
                i = -2;
            }
            if (arrayList.size() > 0) {
                if (packageInfo2 == null) {
                    throw new com.android.server.pm.PackageManagerException(-2, "Missing existing base package for " + this.mPackageName);
                }
                for (java.lang.String str : arrayList) {
                    if (!com.android.internal.util.ArrayUtils.contains(packageInfo2.splitNames, str)) {
                        throw new com.android.server.pm.PackageManagerException(-2, "Split not found: " + str);
                    }
                }
                if (this.mPackageName == null) {
                    this.mPackageName = packageInfo2.packageName;
                    this.mVersionCode = packageInfo2.getLongVersionCode();
                }
                if (this.mSigningDetails == android.content.pm.SigningDetails.UNKNOWN) {
                    this.mSigningDetails = unsafeGetCertsWithoutVerification(packageInfo2.applicationInfo.sourceDir);
                }
            }
            com.android.server.pm.pkg.PackageStateInternal packageStateInternal = ((android.content.pm.PackageManagerInternal) com.android.server.LocalServices.getService(android.content.pm.PackageManagerInternal.class)).getPackageStateInternal(this.mPackageName);
            if (!isInstallationAllowed(packageStateInternal)) {
                throw new com.android.server.pm.PackageManagerException(-116, "Installation of this package is not allowed.");
            }
            if (isArchivedInstallation()) {
                if (!isArchivedInstallationAllowed(packageStateInternal)) {
                    throw new com.android.server.pm.PackageManagerException(-116, "Archived installation of this package is not allowed.");
                }
                if (!this.mPm.mInstallerService.mPackageArchiver.verifySupportsUnarchival(getInstallSource().mInstallerPackageName, this.userId)) {
                    throw new com.android.server.pm.PackageManagerException(-116, "Installer has to support unarchival in order to install archived packages.");
                }
            }
            if (isIncrementalInstallation()) {
                if (!isIncrementalInstallationAllowed(packageStateInternal)) {
                    throw new com.android.server.pm.PackageManagerException(-116, "Incremental installation of this package is not allowed.");
                }
                java.io.File tmpAppMetadataFile = getTmpAppMetadataFile();
                if (tmpAppMetadataFile.exists()) {
                    try {
                        try {
                            getIncrementalFileStorages().makeFile(com.android.server.pm.PackageManagerService.APP_METADATA_FILE_NAME, java.nio.file.Files.readAllBytes(tmpAppMetadataFile.toPath()), 416);
                        } catch (java.io.IOException e) {
                            android.util.Slog.e(TAG, "Failed to write app metadata to incremental storage", e);
                        }
                    } finally {
                        tmpAppMetadataFile.delete();
                    }
                }
            }
            if (this.mInstallerUid != this.mOriginalInstallerUid && (android.text.TextUtils.isEmpty(this.mPackageName) || !this.mPackageName.equals(this.mOriginalInstallerPackageName))) {
                throw new com.android.server.pm.PackageManagerException(-23, "Can only transfer sessions that update the original installer");
            }
            if (!this.mChecksums.isEmpty()) {
                throw new com.android.server.pm.PackageManagerException(-116, "Invalid checksum name(s): " + java.lang.String.join(",", this.mChecksums.keySet()));
            }
            if (this.params.mode == 1) {
                if (!arraySet.contains(null)) {
                    throw new com.android.server.pm.PackageManagerException(-2, "Full install must include a base package");
                }
                if ((this.params.installFlags & 4096) != 0) {
                    android.util.EventLog.writeEvent(1397638484, "219044664");
                    this.params.setDontKillApp(false);
                }
                if (!apkLite.isSplitRequired() || (arraySet.size() > 1 && arraySet2.containsAll(arraySet3))) {
                    android.content.pm.parsing.result.ParseResult composePackageLiteFromApks = android.content.pm.parsing.ApkLiteParseUtils.composePackageLiteFromApks(forDefaultParsing.reset(), this.stageDir, apkLite, arrayMap, true);
                    if (!composePackageLiteFromApks.isError()) {
                        this.mPackageLite = (android.content.pm.parsing.PackageLite) composePackageLiteFromApks.getResult();
                        packageLite = this.mPackageLite;
                        packageInfo = packageInfo2;
                        z2 = true;
                    } else {
                        throw new com.android.server.pm.PackageManagerException(composePackageLiteFromApks.getErrorCode(), composePackageLiteFromApks.getErrorMessage(), composePackageLiteFromApks.getException());
                    }
                } else {
                    throw new com.android.server.pm.PackageManagerException(-28, "Missing split for " + this.mPackageName);
                }
            } else {
                android.content.pm.ApplicationInfo applicationInfo = packageInfo2.applicationInfo;
                android.content.pm.parsing.result.ParseResult parsePackageLite = android.content.pm.parsing.ApkLiteParseUtils.parsePackageLite(forDefaultParsing.reset(), new java.io.File(applicationInfo.getCodePath()), 0);
                if (parsePackageLite.isError()) {
                    throw new com.android.server.pm.PackageManagerException(android.hardware.biometrics.fingerprint.V2_1.RequestStatus.SYS_ETIMEDOUT, parsePackageLite.getErrorMessage(), parsePackageLite.getException());
                }
                android.content.pm.parsing.PackageLite packageLite2 = (android.content.pm.parsing.PackageLite) parsePackageLite.getResult();
                packageInfo = packageInfo2;
                assertPackageConsistentLocked("Existing", packageLite2.getPackageName(), packageLite2.getLongVersionCode());
                if (!this.mSigningDetails.signaturesMatchExactly(unsafeGetCertsWithoutVerification(packageLite2.getBaseApkPath()))) {
                    throw new com.android.server.pm.PackageManagerException(-2, "Existing signatures are inconsistent");
                }
                if (this.mResolvedBaseFile == null) {
                    this.mResolvedBaseFile = new java.io.File(applicationInfo.getBaseCodePath());
                    inheritFileLocked(this.mResolvedBaseFile);
                    com.android.internal.util.CollectionUtils.addAll(arraySet3, packageLite2.getBaseRequiredSplitTypes());
                } else if ((this.params.installFlags & 4096) != 0) {
                    android.util.EventLog.writeEvent(1397638484, "219044664");
                    this.params.setDontKillApp(false);
                }
                if (com.android.internal.util.ArrayUtils.isEmpty(packageLite2.getSplitNames())) {
                    z = false;
                } else {
                    z = false;
                    for (int i2 = 0; i2 < packageLite2.getSplitNames().length; i2++) {
                        java.lang.String str2 = packageLite2.getSplitNames()[i2];
                        java.io.File file4 = new java.io.File(packageLite2.getSplitApkPaths()[i2]);
                        boolean contains = arrayList.contains(str2);
                        if (!arraySet.contains(str2) && !contains) {
                            inheritFileLocked(file4);
                            com.android.internal.util.CollectionUtils.addAll(arraySet3, packageLite2.getRequiredSplitTypes()[i2]);
                            com.android.internal.util.CollectionUtils.addAll(arraySet2, packageLite2.getSplitTypes()[i2]);
                        } else {
                            z = true;
                        }
                    }
                }
                if (z && (this.params.installFlags & 4096) != 0) {
                    this.params.setDontKillApp(false);
                }
                java.io.File parentFile = new java.io.File(applicationInfo.getBaseCodePath()).getParentFile();
                this.mInheritedFilesBase = parentFile;
                java.io.File file5 = new java.io.File(parentFile, "oat");
                if (file5.exists() && (listFiles = file5.listFiles()) != null && listFiles.length > 0) {
                    java.lang.String[] allDexCodeInstructionSets = com.android.server.pm.InstructionSets.getAllDexCodeInstructionSets();
                    int length = listFiles.length;
                    int i3 = 0;
                    while (i3 < length) {
                        java.io.File file6 = listFiles[i3];
                        if (!com.android.internal.util.ArrayUtils.contains(allDexCodeInstructionSets, file6.getName())) {
                            fileArr3 = listFiles;
                        } else {
                            java.io.File[] listFiles2 = file6.listFiles();
                            if (listFiles2 != null) {
                                fileArr3 = listFiles;
                                if (listFiles2.length != 0) {
                                    this.mResolvedInstructionSets.add(file6.getName());
                                    this.mResolvedInheritedFiles.addAll(java.util.Arrays.asList(listFiles2));
                                }
                            } else {
                                fileArr3 = listFiles;
                            }
                        }
                        i3++;
                        listFiles = fileArr3;
                    }
                }
                if (mayInheritNativeLibs() && arrayList.isEmpty()) {
                    java.io.File[] fileArr4 = {new java.io.File(parentFile, "lib"), new java.io.File(parentFile, "lib64")};
                    int i4 = 0;
                    while (i4 < 2) {
                        java.io.File file7 = fileArr4[i4];
                        if (!file7.exists()) {
                            file = parentFile;
                            fileArr = fileArr4;
                        } else if (!file7.isDirectory()) {
                            file = parentFile;
                            fileArr = fileArr4;
                        } else {
                            java.util.ArrayList<java.lang.String> arrayList2 = new java.util.ArrayList();
                            java.util.ArrayList arrayList3 = new java.util.ArrayList();
                            java.io.File[] listFiles3 = file7.listFiles();
                            int length2 = listFiles3.length;
                            fileArr = fileArr4;
                            int i5 = 0;
                            while (i5 < length2) {
                                int i6 = length2;
                                java.io.File file8 = listFiles3[i5];
                                if (!file8.isDirectory()) {
                                    fileArr2 = listFiles3;
                                    file2 = parentFile;
                                } else {
                                    fileArr2 = listFiles3;
                                    try {
                                        java.lang.String relativePath = getRelativePath(file8, parentFile);
                                        java.io.File[] listFiles4 = file8.listFiles();
                                        if (listFiles4 != null) {
                                            file2 = parentFile;
                                            if (listFiles4.length != 0) {
                                                arrayList2.add(relativePath);
                                                arrayList3.addAll(java.util.Arrays.asList(listFiles4));
                                            }
                                        } else {
                                            file2 = parentFile;
                                        }
                                    } catch (java.io.IOException e2) {
                                        file = parentFile;
                                        android.util.Slog.e(TAG, "Skipping linking of native library directory!", e2);
                                        arrayList2.clear();
                                        arrayList3.clear();
                                    }
                                }
                                i5++;
                                length2 = i6;
                                listFiles3 = fileArr2;
                                parentFile = file2;
                            }
                            file = parentFile;
                            for (java.lang.String str3 : arrayList2) {
                                if (!this.mResolvedNativeLibPaths.contains(str3)) {
                                    this.mResolvedNativeLibPaths.add(str3);
                                }
                            }
                            this.mResolvedInheritedFiles.addAll(arrayList3);
                        }
                        i4++;
                        fileArr4 = fileArr;
                        parentFile = file;
                    }
                }
                if (!packageLite2.isSplitRequired()) {
                    z2 = true;
                } else {
                    boolean z3 = com.android.internal.util.ArrayUtils.size(packageLite2.getSplitNames()) == arrayList.size();
                    z2 = true;
                    boolean z4 = arraySet.size() == 1 && arraySet.contains(null);
                    if ((z3 && (arraySet.isEmpty() || z4)) || !arraySet2.containsAll(arraySet3)) {
                        throw new com.android.server.pm.PackageManagerException(-28, "Missing split for " + this.mPackageName);
                    }
                }
                packageLite = packageLite2;
            }
            assertPreapprovalDetailsConsistentIfNeededLocked(packageLite, packageInfo);
            if (packageLite.isUseEmbeddedDex()) {
                for (java.io.File file9 : this.mResolvedStagedFiles) {
                    if (file9.getName().endsWith(".apk") && !com.android.server.pm.dex.DexManager.auditUncompressedDexInApk(file9.getPath())) {
                        throw new com.android.server.pm.PackageManagerException(-2, "Some dex are not uncompressed and aligned correctly for " + this.mPackageName);
                    }
                }
            }
            if ((this.mInstallerUid == 2000 ? z2 : false) && isIncrementalInstallation() && this.mIncrementalFileStorages != null && !packageLite.isDebuggable() && !packageLite.isProfileableByShell()) {
                this.mIncrementalFileStorages.disallowReadLogs();
            }
            this.mValidatedTargetSdk = packageLite.getTargetSdk();
            return packageLite;
        }
        throw new com.android.server.pm.PackageManagerException(-2, "Missing existing base package");
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void stageFileLocked(java.io.File file, java.io.File file2) throws com.android.server.pm.PackageManagerException {
        this.mResolvedStagedFiles.add(file2);
        maybeRenameFile(file, file2);
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void maybeStageFsveritySignatureLocked(java.io.File file, java.io.File file2, boolean z) throws com.android.server.pm.PackageManagerException {
        if (android.security.Flags.deprecateFsvSig()) {
            return;
        }
        java.io.File file3 = new java.io.File(com.android.internal.security.VerityUtils.getFsveritySignatureFilePath(file.getPath()));
        if (file3.exists()) {
            stageFileLocked(file3, new java.io.File(com.android.internal.security.VerityUtils.getFsveritySignatureFilePath(file2.getPath())));
        } else if (z) {
            throw new com.android.server.pm.PackageManagerException(-118, "Missing corresponding fs-verity signature to " + file);
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void maybeStageV4SignatureLocked(java.io.File file, java.io.File file2) throws com.android.server.pm.PackageManagerException {
        java.io.File file3 = new java.io.File(file.getPath() + ".idsig");
        if (file3.exists()) {
            stageFileLocked(file3, new java.io.File(file2.getPath() + ".idsig"));
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void maybeStageDexMetadataLocked(java.io.File file, java.io.File file2) throws com.android.server.pm.PackageManagerException {
        java.io.File findDexMetadataForFile = android.content.pm.dex.DexMetadataHelper.findDexMetadataForFile(file);
        if (findDexMetadataForFile == null) {
            return;
        }
        if (!android.os.FileUtils.isValidExtFilename(findDexMetadataForFile.getName())) {
            throw new com.android.server.pm.PackageManagerException(-2, "Invalid filename: " + findDexMetadataForFile);
        }
        java.io.File file3 = new java.io.File(this.stageDir, android.content.pm.dex.DexMetadataHelper.buildDexMetadataPathForApk(file2.getName()));
        stageFileLocked(findDexMetadataForFile, file3);
        maybeStageFsveritySignatureLocked(findDexMetadataForFile, file3, android.content.pm.dex.DexMetadataHelper.isFsVerityRequired());
    }

    private android.os.incremental.IncrementalFileStorages getIncrementalFileStorages() {
        android.os.incremental.IncrementalFileStorages incrementalFileStorages;
        synchronized (this.mLock) {
            incrementalFileStorages = this.mIncrementalFileStorages;
        }
        return incrementalFileStorages;
    }

    private void storeBytesToInstallationFile(java.lang.String str, java.lang.String str2, byte[] bArr) throws java.io.IOException {
        android.os.incremental.IncrementalFileStorages incrementalFileStorages = getIncrementalFileStorages();
        if (!isIncrementalInstallation() || incrementalFileStorages == null) {
            android.os.FileUtils.bytesToFile(str2, bArr);
        } else {
            incrementalFileStorages.makeFile(str, bArr, 511);
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void maybeStageDigestsLocked(java.io.File file, java.io.File file2, java.lang.String str) throws com.android.server.pm.PackageManagerException {
        com.android.server.pm.PackageInstallerSession.PerFileChecksum perFileChecksum = this.mChecksums.get(file.getName());
        if (perFileChecksum == null) {
            return;
        }
        this.mChecksums.remove(file.getName());
        android.content.pm.Checksum[] checksums = perFileChecksum.getChecksums();
        if (checksums.length == 0) {
            return;
        }
        java.lang.String buildDigestsPathForApk = com.android.server.pm.ApkChecksums.buildDigestsPathForApk(file2.getName());
        java.io.File file3 = new java.io.File(this.stageDir, buildDigestsPathForApk);
        try {
            try {
                java.io.ByteArrayOutputStream byteArrayOutputStream = new java.io.ByteArrayOutputStream();
                try {
                    com.android.server.pm.ApkChecksums.writeChecksums(byteArrayOutputStream, checksums);
                    byte[] signature = perFileChecksum.getSignature();
                    if (signature != null && signature.length > 0) {
                        com.android.server.pm.ApkChecksums.verifySignature(checksums, signature);
                    }
                    storeBytesToInstallationFile(buildDigestsPathForApk, file3.getAbsolutePath(), byteArrayOutputStream.toByteArray());
                    stageFileLocked(file3, file3);
                    if (signature == null || signature.length == 0) {
                        byteArrayOutputStream.close();
                        return;
                    }
                    java.lang.String buildSignaturePathForDigests = com.android.server.pm.ApkChecksums.buildSignaturePathForDigests(buildDigestsPathForApk);
                    java.io.File file4 = new java.io.File(this.stageDir, buildSignaturePathForDigests);
                    storeBytesToInstallationFile(buildSignaturePathForDigests, file4.getAbsolutePath(), signature);
                    stageFileLocked(file4, file4);
                    byteArrayOutputStream.close();
                } catch (java.lang.Throwable th) {
                    try {
                        byteArrayOutputStream.close();
                    } catch (java.lang.Throwable th2) {
                        th.addSuppressed(th2);
                    }
                    throw th;
                }
            } catch (java.security.NoSuchAlgorithmException | java.security.SignatureException e) {
                throw new com.android.server.pm.PackageManagerException(com.android.server.location.gnss.hal.GnssNative.GeofenceCallbacks.GEOFENCE_STATUS_ERROR_INVALID_TRANSITION, "Failed to verify digests' signature for " + this.mPackageName, e);
            }
        } catch (java.io.IOException e2) {
            throw new com.android.server.pm.PackageManagerException(-4, "Failed to store digests for " + this.mPackageName, e2);
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean isFsVerityRequiredForApk(java.io.File file, java.io.File file2) throws com.android.server.pm.PackageManagerException {
        if (this.mVerityFoundForApks) {
            return true;
        }
        if (!new java.io.File(com.android.internal.security.VerityUtils.getFsveritySignatureFilePath(file.getPath())).exists()) {
            return false;
        }
        this.mVerityFoundForApks = true;
        for (java.io.File file3 : this.mResolvedStagedFiles) {
            if (file3.getName().endsWith(".apk") && !file2.getName().equals(file3.getName())) {
                throw new com.android.server.pm.PackageManagerException(-118, "Previously staged apk is missing fs-verity signature");
            }
        }
        return true;
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void resolveAndStageFileLocked(java.io.File file, java.io.File file2, java.lang.String str) throws com.android.server.pm.PackageManagerException {
        stageFileLocked(file, file2);
        maybeStageFsveritySignatureLocked(file, file2, isFsVerityRequiredForApk(file, file2));
        if (android.security.Flags.extendVbChainToUpdatedApk() && com.android.internal.security.VerityUtils.isFsVeritySupported()) {
            maybeStageV4SignatureLocked(file, file2);
        }
        maybeStageDexMetadataLocked(file, file2);
        maybeStageDigestsLocked(file, file2, str);
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void maybeInheritFsveritySignatureLocked(java.io.File file) {
        java.io.File file2 = new java.io.File(com.android.internal.security.VerityUtils.getFsveritySignatureFilePath(file.getPath()));
        if (file2.exists()) {
            this.mResolvedInheritedFiles.add(file2);
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void maybeInheritV4SignatureLocked(java.io.File file) {
        java.io.File file2 = new java.io.File(file.getPath() + ".idsig");
        if (file2.exists()) {
            this.mResolvedInheritedFiles.add(file2);
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void inheritFileLocked(java.io.File file) {
        this.mResolvedInheritedFiles.add(file);
        maybeInheritFsveritySignatureLocked(file);
        if (android.security.Flags.extendVbChainToUpdatedApk()) {
            maybeInheritV4SignatureLocked(file);
        }
        java.io.File findDexMetadataForFile = android.content.pm.dex.DexMetadataHelper.findDexMetadataForFile(file);
        if (findDexMetadataForFile != null) {
            this.mResolvedInheritedFiles.add(findDexMetadataForFile);
            maybeInheritFsveritySignatureLocked(findDexMetadataForFile);
        }
        java.io.File findDigestsForFile = com.android.server.pm.ApkChecksums.findDigestsForFile(file);
        if (findDigestsForFile != null) {
            this.mResolvedInheritedFiles.add(findDigestsForFile);
            java.io.File findSignatureForDigests = com.android.server.pm.ApkChecksums.findSignatureForDigests(findDigestsForFile);
            if (findSignatureForDigests != null) {
                this.mResolvedInheritedFiles.add(findSignatureForDigests);
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void assertApkConsistentLocked(java.lang.String str, android.content.pm.parsing.ApkLite apkLite) throws com.android.server.pm.PackageManagerException {
        assertPackageConsistentLocked(str, apkLite.getPackageName(), apkLite.getLongVersionCode());
        if (!this.mSigningDetails.signaturesMatchExactly(apkLite.getSigningDetails())) {
            throw new com.android.server.pm.PackageManagerException(-2, str + " signatures are inconsistent");
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void assertPackageConsistentLocked(java.lang.String str, java.lang.String str2, long j) throws com.android.server.pm.PackageManagerException {
        if (!this.mPackageName.equals(str2)) {
            throw new com.android.server.pm.PackageManagerException(-2, str + " package " + str2 + " inconsistent with " + this.mPackageName);
        }
        if (this.params.appPackageName != null && !this.params.appPackageName.equals(str2)) {
            throw new com.android.server.pm.PackageManagerException(-2, str + " specified package " + this.params.appPackageName + " inconsistent with " + str2);
        }
        if (this.mVersionCode != j) {
            throw new com.android.server.pm.PackageManagerException(-2, str + " version code " + j + " inconsistent with " + this.mVersionCode);
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void assertPreapprovalDetailsConsistentIfNeededLocked(@android.annotation.NonNull android.content.pm.parsing.PackageLite packageLite, @android.annotation.Nullable android.content.pm.PackageInfo packageInfo) throws com.android.server.pm.PackageManagerException {
        if (this.mPreapprovalDetails == null || !isPreapprovalRequested()) {
            return;
        }
        if (!android.text.TextUtils.equals(this.mPackageName, this.mPreapprovalDetails.getPackageName())) {
            throw new com.android.server.pm.PackageManagerException(android.hardware.biometrics.fingerprint.V2_1.RequestStatus.SYS_ETIMEDOUT, this.mPreapprovalDetails + " inconsistent with " + this.mPackageName);
        }
        android.content.pm.PackageManager packageManager = this.mContext.getPackageManager();
        if (packageInfo == null) {
            packageInfo = this.mPm.snapshotComputer().getPackageInfo(this.mPackageName, 0L, this.userId);
        }
        java.lang.CharSequence label = this.mPreapprovalDetails.getLabel();
        if (packageInfo != null && android.text.TextUtils.equals(label, packageManager.getApplicationLabel(packageInfo.applicationInfo))) {
            return;
        }
        android.content.pm.PackageInfo packageArchiveInfo = packageManager.getPackageArchiveInfo(packageLite.getPath(), android.content.pm.PackageManager.PackageInfoFlags.of(0L));
        if (packageArchiveInfo == null) {
            throw new com.android.server.pm.PackageManagerException(-2, "Failure to obtain package info from APK files.");
        }
        java.util.List allApkPaths = packageLite.getAllApkPaths();
        android.icu.util.ULocale locale = this.mPreapprovalDetails.getLocale();
        android.content.pm.ApplicationInfo applicationInfo = packageArchiveInfo.applicationInfo;
        boolean z = false;
        for (int size = allApkPaths.size() - 1; size >= 0 && !z; size--) {
            z |= android.text.TextUtils.equals(getAppLabel((java.lang.String) allApkPaths.get(size), locale, applicationInfo), label);
        }
        if (!z) {
            throw new com.android.server.pm.PackageManagerException(android.hardware.biometrics.fingerprint.V2_1.RequestStatus.SYS_ETIMEDOUT, this.mPreapprovalDetails + " inconsistent with app label");
        }
    }

    private java.lang.CharSequence getAppLabel(java.lang.String str, android.icu.util.ULocale uLocale, android.content.pm.ApplicationInfo applicationInfo) throws com.android.server.pm.PackageManagerException {
        android.content.res.Resources resources = this.mContext.getResources();
        android.content.res.AssetManager assetManager = new android.content.res.AssetManager();
        android.content.res.Configuration configuration = new android.content.res.Configuration(resources.getConfiguration());
        try {
            assetManager.setApkAssets(new android.content.res.ApkAssets[]{android.content.res.ApkAssets.loadFromPath(str)}, false);
            configuration.setLocale(uLocale.toLocale());
            return android.text.TextUtils.trimToSize(tryLoadingAppLabel(new android.content.res.Resources(assetManager, resources.getDisplayMetrics(), configuration), applicationInfo), 1000);
        } catch (java.io.IOException e) {
            throw new com.android.server.pm.PackageManagerException(-2, "Failure to get resources from package archive " + str);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:12:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:5:0x0018  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private java.lang.CharSequence tryLoadingAppLabel(@android.annotation.NonNull android.content.res.Resources resources, @android.annotation.NonNull android.content.pm.ApplicationInfo applicationInfo) {
        java.lang.String trim;
        if (applicationInfo.labelRes != 0) {
            try {
                trim = resources.getText(applicationInfo.labelRes).toString().trim();
            } catch (android.content.res.Resources.NotFoundException e) {
            }
            if (trim != null) {
                return applicationInfo.nonLocalizedLabel != null ? applicationInfo.nonLocalizedLabel : applicationInfo.packageName;
            }
            return trim;
        }
        trim = null;
        if (trim != null) {
        }
    }

    private android.content.pm.SigningDetails unsafeGetCertsWithoutVerification(java.lang.String str) throws com.android.server.pm.PackageManagerException {
        android.content.pm.parsing.result.ParseResult unsafeGetCertsWithoutVerification = android.util.apk.ApkSignatureVerifier.unsafeGetCertsWithoutVerification(android.content.pm.parsing.result.ParseTypeImpl.forDefaultParsing(), str, 1);
        if (unsafeGetCertsWithoutVerification.isError()) {
            throw new com.android.server.pm.PackageManagerException(-2, "Couldn't obtain signatures from APK : " + str);
        }
        return (android.content.pm.SigningDetails) unsafeGetCertsWithoutVerification.getResult();
    }

    private static boolean isLinkPossible(java.util.List<java.io.File> list, java.io.File file) {
        try {
            android.system.StructStat stat = android.system.Os.stat(file.getAbsolutePath());
            java.util.Iterator<java.io.File> it = list.iterator();
            while (it.hasNext()) {
                if (android.system.Os.stat(it.next().getAbsolutePath()).st_dev != stat.st_dev) {
                    return false;
                }
            }
            return true;
        } catch (android.system.ErrnoException e) {
            android.util.Slog.w(TAG, "Failed to detect if linking possible: " + e);
            return false;
        }
    }

    public int getInstallerUid() {
        int i;
        synchronized (this.mLock) {
            i = this.mInstallerUid;
        }
        return i;
    }

    @com.android.internal.annotations.VisibleForTesting(visibility = com.android.internal.annotations.VisibleForTesting.Visibility.PACKAGE)
    public java.lang.String getPackageName() {
        java.lang.String str;
        synchronized (this.mLock) {
            str = this.mPackageName;
        }
        return str;
    }

    public long getUpdatedMillis() {
        long j;
        synchronized (this.mLock) {
            j = this.updatedMillis;
        }
        return j;
    }

    long getCommittedMillis() {
        long j;
        synchronized (this.mLock) {
            j = this.committedMillis;
        }
        return j;
    }

    java.lang.String getInstallerPackageName() {
        return getInstallSource().mInstallerPackageName;
    }

    java.lang.String getInstallerAttributionTag() {
        return getInstallSource().mInstallerAttributionTag;
    }

    com.android.server.pm.InstallSource getInstallSource() {
        com.android.server.pm.InstallSource installSource;
        synchronized (this.mLock) {
            installSource = this.mInstallSource;
        }
        return installSource;
    }

    android.content.pm.SigningDetails getSigningDetails() {
        android.content.pm.SigningDetails signingDetails;
        synchronized (this.mLock) {
            signingDetails = this.mSigningDetails;
        }
        return signingDetails;
    }

    android.content.pm.parsing.PackageLite getPackageLite() {
        android.content.pm.parsing.PackageLite packageLite;
        synchronized (this.mLock) {
            packageLite = this.mPackageLite;
        }
        return packageLite;
    }

    public boolean getUserActionRequired() {
        if (this.mUserActionRequired != null) {
            return this.mUserActionRequired.booleanValue();
        }
        android.util.Slog.wtf(TAG, "mUserActionRequired should not be null.");
        return false;
    }

    private static java.lang.String getRelativePath(java.io.File file, java.io.File file2) throws java.io.IOException {
        java.lang.String absolutePath = file.getAbsolutePath();
        java.lang.String absolutePath2 = file2.getAbsolutePath();
        if (absolutePath.contains("/.")) {
            throw new java.io.IOException("Invalid path (was relative) : " + absolutePath);
        }
        if (absolutePath.startsWith(absolutePath2)) {
            return absolutePath.substring(absolutePath2.length());
        }
        throw new java.io.IOException("File: " + absolutePath + " outside base: " + absolutePath2);
    }

    private void createOatDirs(java.lang.String str, java.util.List<java.lang.String> list, java.io.File file) throws com.android.server.pm.PackageManagerException {
        java.util.Iterator<java.lang.String> it = list.iterator();
        while (it.hasNext()) {
            try {
                this.mInstaller.createOatDir(str, file.getAbsolutePath(), it.next());
            } catch (com.android.server.pm.Installer.InstallerException e) {
                throw com.android.server.pm.PackageManagerException.from(e);
            }
        }
    }

    private void linkFile(java.lang.String str, java.lang.String str2, java.lang.String str3, java.lang.String str4) throws java.io.IOException {
        try {
            android.os.incremental.IncrementalFileStorages incrementalFileStorages = getIncrementalFileStorages();
            if (incrementalFileStorages != null && incrementalFileStorages.makeLink(str2, str3, str4)) {
                return;
            }
            this.mInstaller.linkFile(str, str2, str3, str4);
        } catch (com.android.server.pm.Installer.InstallerException | java.io.IOException e) {
            throw new java.io.IOException("failed linkOrCreateDir(" + str2 + ", " + str3 + ", " + str4 + ")", e);
        }
    }

    private void linkFiles(java.lang.String str, java.util.List<java.io.File> list, java.io.File file, java.io.File file2) throws java.io.IOException {
        java.util.Iterator<java.io.File> it = list.iterator();
        while (it.hasNext()) {
            linkFile(str, getRelativePath(it.next(), file2), file2.getAbsolutePath(), file.getAbsolutePath());
        }
        android.util.Slog.d(TAG, "Linked " + list.size() + " files into " + file);
    }

    private static void copyFiles(java.util.List<java.io.File> list, java.io.File file) throws java.io.IOException {
        for (java.io.File file2 : file.listFiles()) {
            if (file2.getName().endsWith(".tmp")) {
                file2.delete();
            }
        }
        for (java.io.File file3 : list) {
            java.io.File createTempFile = java.io.File.createTempFile("inherit", ".tmp", file);
            android.util.Slog.d(TAG, "Copying " + file3 + " to " + createTempFile);
            if (!android.os.FileUtils.copyFile(file3, createTempFile)) {
                throw new java.io.IOException("Failed to copy " + file3 + " to " + createTempFile);
            }
            try {
                android.system.Os.chmod(createTempFile.getAbsolutePath(), com.android.internal.util.FrameworkStatsLog.VBMETA_DIGEST_REPORTED);
                java.io.File file4 = new java.io.File(file, file3.getName());
                android.util.Slog.d(TAG, "Renaming " + createTempFile + " to " + file4);
                if (!createTempFile.renameTo(file4)) {
                    throw new java.io.IOException("Failed to rename " + createTempFile + " to " + file4);
                }
            } catch (android.system.ErrnoException e) {
                throw new java.io.IOException("Failed to chmod " + createTempFile);
            }
        }
        android.util.Slog.d(TAG, "Copied " + list.size() + " files into " + file);
    }

    private void extractNativeLibraries(android.content.pm.parsing.PackageLite packageLite, java.io.File file, java.lang.String str, boolean z) throws com.android.server.pm.PackageManagerException {
        java.util.Objects.requireNonNull(packageLite);
        java.io.File file2 = new java.io.File(file, "lib");
        if (!z) {
            com.android.internal.content.NativeLibraryHelper.removeNativeBinariesFromDirLI(file2, true);
        }
        if (isArchivedInstallation()) {
            return;
        }
        com.android.internal.content.NativeLibraryHelper.Handle handle = null;
        try {
            try {
                handle = com.android.internal.content.NativeLibraryHelper.Handle.create(packageLite);
                int copyNativeBinariesWithOverride = com.android.internal.content.NativeLibraryHelper.copyNativeBinariesWithOverride(handle, file2, str, isIncrementalInstallation());
                if (copyNativeBinariesWithOverride != 1) {
                    throw new com.android.server.pm.PackageManagerException(copyNativeBinariesWithOverride, "Failed to extract native libraries, res=" + copyNativeBinariesWithOverride);
                }
            } catch (java.io.IOException e) {
                throw new com.android.server.pm.PackageManagerException(android.hardware.biometrics.fingerprint.V2_1.RequestStatus.SYS_ETIMEDOUT, "Failed to extract native libraries", e);
            }
        } finally {
            libcore.io.IoUtils.closeQuietly(handle);
        }
    }

    void setPermissionsResult(boolean z) {
        if (!isSealed() && !isPreapprovalRequested()) {
            throw new java.lang.SecurityException("Must be sealed to accept permissions");
        }
        com.android.server.pm.PackageInstallerSession session = (hasParentSessionId() && isCommitted()) ? this.mSessionProvider.getSession(getParentSessionId()) : this;
        if (z) {
            synchronized (this.mLock) {
                this.mPermissionsManuallyAccepted = true;
            }
            session.mHandler.obtainMessage(isCommitted() ? 3 : 6).sendToTarget();
        } else {
            session.destroy("User rejected permissions");
            session.dispatchSessionFinished(-115, "User rejected permissions", null);
            session.maybeFinishChildSessions(-115, "User rejected permissions");
        }
    }

    public void open() throws java.io.IOException {
        boolean z;
        activate();
        synchronized (this.mLock) {
            try {
                z = this.mPrepared;
                if (!this.mPrepared) {
                    if (this.stageDir != null) {
                        com.android.server.pm.PackageInstallerService.prepareStageDir(this.stageDir);
                    } else if (!this.params.isMultiPackage) {
                        throw new java.lang.IllegalArgumentException("stageDir must be set");
                    }
                    this.mPrepared = true;
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        if (!z) {
            this.mCallback.onSessionPrepared(this);
        }
    }

    private void activate() {
        if (this.mActiveCount.getAndIncrement() == 0) {
            this.mCallback.onSessionActiveChanged(this, true);
        }
    }

    public void close() {
        closeInternal(true);
    }

    private void closeInternal(boolean z) {
        synchronized (this.mLock) {
            if (z) {
                try {
                    assertCallerIsOwnerOrRoot();
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
        deactivate();
    }

    private void deactivate() {
        int decrementAndGet;
        synchronized (this.mLock) {
            decrementAndGet = this.mActiveCount.decrementAndGet();
        }
        if (decrementAndGet == 0) {
            this.mCallback.onSessionActiveChanged(this, false);
        }
    }

    private void maybeFinishChildSessions(int i, java.lang.String str) {
        java.util.Iterator<com.android.server.pm.PackageInstallerSession> it = getChildSessions().iterator();
        while (it.hasNext()) {
            it.next().dispatchSessionFinished(i, str, null);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void assertNotChild(java.lang.String str) {
        if (hasParentSessionId()) {
            throw new java.lang.IllegalStateException(str + " can't be called on a child session, id=" + this.sessionId + " parentId=" + getParentSessionId());
        }
    }

    private boolean dispatchPendingAbandonCallback() {
        synchronized (this.mLock) {
            try {
                if (!this.mStageDirInUse) {
                    return false;
                }
                this.mStageDirInUse = false;
                java.lang.Runnable runnable = this.mPendingAbandonCallback;
                this.mPendingAbandonCallback = null;
                if (runnable == null) {
                    return false;
                }
                runnable.run();
                return true;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void abandon() {
        synchronized (this.mLock) {
            try {
                assertNotChild("abandon");
                assertCallerIsOwnerOrRootOrSystem();
                if (isInTerminalState()) {
                    return;
                }
                this.mDestroyed = true;
                java.lang.Runnable runnable = new java.lang.Runnable() { // from class: com.android.server.pm.PackageInstallerSession$$ExternalSyntheticLambda4
                    @Override // java.lang.Runnable
                    public final void run() {
                        com.android.server.pm.PackageInstallerSession.this.lambda$abandon$7();
                    }
                };
                if (this.mStageDirInUse) {
                    this.mPendingAbandonCallback = runnable;
                    this.mCallback.onSessionChanged(this);
                } else {
                    long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                    try {
                        runnable.run();
                    } finally {
                        android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$abandon$7() {
        assertNotLocked("abandonStaged");
        if (isStaged() && isCommitted()) {
            this.mStagingManager.abortCommittedSession(this.mStagedSession);
        }
        destroy("Session was abandoned");
        dispatchSessionFinished(-115, "Session was abandoned", null);
        maybeFinishChildSessions(-115, "Session was abandoned because the parent session is abandoned");
    }

    public boolean isMultiPackage() {
        return this.params.isMultiPackage;
    }

    public boolean isStaged() {
        return this.params.isStaged;
    }

    public int getInstallFlags() {
        return this.params.installFlags;
    }

    @android.annotation.EnforcePermission("com.android.permission.USE_INSTALLER_V2")
    public android.content.pm.DataLoaderParamsParcel getDataLoaderParams() {
        getDataLoaderParams_enforcePermission();
        if (this.params.dataLoaderParams != null) {
            return this.params.dataLoaderParams.getData();
        }
        return null;
    }

    @android.annotation.EnforcePermission("com.android.permission.USE_INSTALLER_V2")
    public void addFile(int i, java.lang.String str, long j, byte[] bArr, byte[] bArr2) {
        addFile_enforcePermission();
        if (!isDataLoaderInstallation()) {
            throw new java.lang.IllegalStateException("Cannot add files to non-data loader installation session.");
        }
        if (isStreamingInstallation() && i != 0) {
            throw new java.lang.IllegalArgumentException("Non-incremental installation only supports /data/app placement: " + str);
        }
        if (bArr == null) {
            throw new java.lang.IllegalArgumentException("DataLoader installation requires valid metadata: " + str);
        }
        if (!android.os.FileUtils.isValidExtFilename(str)) {
            throw new java.lang.IllegalArgumentException("Invalid name: " + str);
        }
        synchronized (this.mLock) {
            try {
                assertCallerIsOwnerOrRoot();
                assertPreparedAndNotSealedLocked("addFile");
                if (!this.mFiles.add(new com.android.server.pm.PackageInstallerSession.FileEntry(this.mFiles.size(), new android.content.pm.InstallationFile(i, str, j, bArr, bArr2)))) {
                    throw new java.lang.IllegalArgumentException("File already added: " + str);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @android.annotation.EnforcePermission("com.android.permission.USE_INSTALLER_V2")
    public void removeFile(int i, java.lang.String str) {
        removeFile_enforcePermission();
        if (!isDataLoaderInstallation()) {
            throw new java.lang.IllegalStateException("Cannot add files to non-data loader installation session.");
        }
        if (android.text.TextUtils.isEmpty(this.params.appPackageName)) {
            throw new java.lang.IllegalStateException("Must specify package name to remove a split");
        }
        synchronized (this.mLock) {
            try {
                assertCallerIsOwnerOrRoot();
                assertPreparedAndNotSealedLocked("removeFile");
                if (!this.mFiles.add(new com.android.server.pm.PackageInstallerSession.FileEntry(this.mFiles.size(), new android.content.pm.InstallationFile(i, getRemoveMarkerName(str), -1L, (byte[]) null, (byte[]) null)))) {
                    throw new java.lang.IllegalArgumentException("File already removed: " + str);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean prepareDataLoaderLocked() throws com.android.server.pm.PackageManagerException {
        if (!isDataLoaderInstallation() || this.mDataLoaderFinished) {
            return true;
        }
        final java.util.ArrayList arrayList = new java.util.ArrayList();
        final java.util.ArrayList arrayList2 = new java.util.ArrayList();
        for (android.content.pm.InstallationFile installationFile : getInstallationFilesLocked()) {
            if (sAddedFilter.accept(new java.io.File(this.stageDir, installationFile.getName()))) {
                arrayList.add(installationFile.getData());
            } else if (sRemovedFilter.accept(new java.io.File(this.stageDir, installationFile.getName()))) {
                arrayList2.add(installationFile.getName().substring(0, installationFile.getName().length() - REMOVE_MARKER_EXTENSION.length()));
            }
        }
        final android.content.pm.DataLoaderParams dataLoaderParams = this.params.dataLoaderParams;
        final boolean isIncrementalInstallation = true ^ isIncrementalInstallation();
        final boolean isSystemDataLoaderInstallation = isSystemDataLoaderInstallation();
        android.content.pm.IDataLoaderStatusListener.Stub stub = new android.content.pm.IDataLoaderStatusListener.Stub() { // from class: com.android.server.pm.PackageInstallerSession.6
            public void onStatusChanged(int i, int i2) {
                switch (i2) {
                    case 0:
                    case 1:
                    case 5:
                        return;
                    default:
                        if (com.android.server.pm.PackageInstallerSession.this.mDestroyed || com.android.server.pm.PackageInstallerSession.this.mDataLoaderFinished) {
                            switch (i2) {
                                case 9:
                                    if (isSystemDataLoaderInstallation) {
                                        com.android.server.pm.PackageInstallerSession.this.onSystemDataLoaderUnrecoverable();
                                        return;
                                    }
                                    return;
                                default:
                                    return;
                            }
                        }
                        try {
                            switch (i2) {
                                case 2:
                                    if (isIncrementalInstallation) {
                                        android.content.pm.FileSystemControlParcel fileSystemControlParcel = new android.content.pm.FileSystemControlParcel();
                                        fileSystemControlParcel.callback = com.android.server.pm.PackageInstallerSession.this.new FileSystemConnector(arrayList);
                                        com.android.server.pm.PackageInstallerSession.this.getDataLoader(i).create(i, dataLoaderParams.getData(), fileSystemControlParcel, this);
                                        return;
                                    }
                                    return;
                                case 3:
                                    if (isIncrementalInstallation) {
                                        com.android.server.pm.PackageInstallerSession.this.getDataLoader(i).start(i);
                                        return;
                                    }
                                    return;
                                case 4:
                                    com.android.server.pm.PackageInstallerSession.this.getDataLoader(i).prepareImage(i, (android.content.pm.InstallationFileParcel[]) arrayList.toArray(new android.content.pm.InstallationFileParcel[arrayList.size()]), (java.lang.String[]) arrayList2.toArray(new java.lang.String[arrayList2.size()]));
                                    return;
                                case 5:
                                default:
                                    return;
                                case 6:
                                    com.android.server.pm.PackageInstallerSession.this.mDataLoaderFinished = true;
                                    if (com.android.server.pm.PackageInstallerSession.this.hasParentSessionId()) {
                                        com.android.server.pm.PackageInstallerSession.this.mSessionProvider.getSession(com.android.server.pm.PackageInstallerSession.this.getParentSessionId()).dispatchSessionSealed();
                                    } else {
                                        com.android.server.pm.PackageInstallerSession.this.dispatchSessionSealed();
                                    }
                                    if (isIncrementalInstallation) {
                                        com.android.server.pm.PackageInstallerSession.this.getDataLoader(i).destroy(i);
                                        return;
                                    }
                                    return;
                                case 7:
                                    com.android.server.pm.PackageInstallerSession.this.mDataLoaderFinished = true;
                                    com.android.server.pm.PackageInstallerSession.this.dispatchSessionValidationFailure(-20, "Failed to prepare image.");
                                    if (isIncrementalInstallation) {
                                        com.android.server.pm.PackageInstallerSession.this.getDataLoader(i).destroy(i);
                                        return;
                                    }
                                    return;
                                case 8:
                                    com.android.server.pm.PackageInstallerSession.sendPendingStreaming(com.android.server.pm.PackageInstallerSession.this.mContext, com.android.server.pm.PackageInstallerSession.this.getRemoteStatusReceiver(), com.android.server.pm.PackageInstallerSession.this.sessionId, "DataLoader unavailable");
                                    return;
                                case 9:
                                    throw new com.android.server.pm.PackageManagerException(-20, "DataLoader reported unrecoverable failure.");
                            }
                        } catch (android.os.RemoteException e) {
                            com.android.server.pm.PackageInstallerSession.sendPendingStreaming(com.android.server.pm.PackageInstallerSession.this.mContext, com.android.server.pm.PackageInstallerSession.this.getRemoteStatusReceiver(), com.android.server.pm.PackageInstallerSession.this.sessionId, e.getMessage());
                            return;
                        } catch (com.android.server.pm.PackageManagerException e2) {
                            com.android.server.pm.PackageInstallerSession.this.mDataLoaderFinished = true;
                            com.android.server.pm.PackageInstallerSession.this.dispatchSessionValidationFailure(e2.error, android.util.ExceptionUtils.getCompleteMessage(e2));
                            return;
                        }
                }
            }
        };
        if (!isIncrementalInstallation) {
            android.os.incremental.PerUidReadTimeouts[] perUidReadTimeouts = this.mPm.getPerUidReadTimeouts(this.mPm.snapshotComputer());
            android.os.incremental.StorageHealthCheckParams storageHealthCheckParams = new android.os.incremental.StorageHealthCheckParams();
            storageHealthCheckParams.blockedTimeoutMs = 2000;
            storageHealthCheckParams.unhealthyTimeoutMs = INCREMENTAL_STORAGE_UNHEALTHY_TIMEOUT_MS;
            storageHealthCheckParams.unhealthyMonitoringMs = 60000;
            android.os.incremental.IStorageHealthListener.Stub stub2 = new android.os.incremental.IStorageHealthListener.Stub() { // from class: com.android.server.pm.PackageInstallerSession.7
                public void onHealthStatus(int i, int i2) {
                    if (com.android.server.pm.PackageInstallerSession.this.mDestroyed || com.android.server.pm.PackageInstallerSession.this.mDataLoaderFinished) {
                        return;
                    }
                    switch (i2) {
                        case 0:
                        default:
                            return;
                        case 1:
                        case 2:
                            if (isSystemDataLoaderInstallation) {
                                return;
                            }
                            break;
                        case 3:
                            break;
                    }
                    com.android.server.pm.PackageInstallerSession.this.mDataLoaderFinished = true;
                    com.android.server.pm.PackageInstallerSession.this.dispatchSessionValidationFailure(-20, "Image is missing pages required for installation.");
                }
            };
            try {
                android.content.pm.PackageInfo packageInfo = this.mPm.snapshotComputer().getPackageInfo(this.params.appPackageName, 0L, this.userId);
                java.io.File parentFile = (packageInfo == null || packageInfo.applicationInfo == null) ? null : new java.io.File(packageInfo.applicationInfo.getCodePath()).getParentFile();
                if (this.mIncrementalFileStorages == null) {
                    this.mIncrementalFileStorages = android.os.incremental.IncrementalFileStorages.initialize(this.mContext, this.stageDir, parentFile, dataLoaderParams, stub, storageHealthCheckParams, stub2, arrayList, perUidReadTimeouts, new android.content.pm.IPackageLoadingProgressCallback.Stub() { // from class: com.android.server.pm.PackageInstallerSession.8
                        public void onPackageLoadingProgressChanged(float f) {
                            synchronized (com.android.server.pm.PackageInstallerSession.this.mProgressLock) {
                                com.android.server.pm.PackageInstallerSession.this.mIncrementalProgress = f;
                                com.android.server.pm.PackageInstallerSession.this.computeProgressLocked(true);
                            }
                        }
                    });
                } else {
                    this.mIncrementalFileStorages.startLoading(dataLoaderParams, stub, storageHealthCheckParams, stub2, perUidReadTimeouts);
                }
                return false;
            } catch (java.io.IOException e) {
                throw new com.android.server.pm.PackageManagerException(-20, e.getMessage(), e.getCause());
            }
        }
        if (getDataLoaderManager().bindToDataLoader(this.sessionId, dataLoaderParams.getData(), 0L, stub)) {
            return false;
        }
        throw new com.android.server.pm.PackageManagerException(-20, "Failed to initialize data loader");
    }

    private android.content.pm.DataLoaderManager getDataLoaderManager() throws com.android.server.pm.PackageManagerException {
        android.content.pm.DataLoaderManager dataLoaderManager = (android.content.pm.DataLoaderManager) this.mContext.getSystemService(android.content.pm.DataLoaderManager.class);
        if (dataLoaderManager == null) {
            throw new com.android.server.pm.PackageManagerException(-20, "Failed to find data loader manager service");
        }
        return dataLoaderManager;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public android.content.pm.IDataLoader getDataLoader(int i) throws com.android.server.pm.PackageManagerException {
        android.content.pm.IDataLoader dataLoader = getDataLoaderManager().getDataLoader(i);
        if (dataLoader == null) {
            throw new com.android.server.pm.PackageManagerException(-20, "Failure to obtain data loader");
        }
        return dataLoader;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dispatchSessionValidationFailure(int i, java.lang.String str) {
        this.mHandler.obtainMessage(5, i, -1, str).sendToTarget();
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private int[] getChildSessionIdsLocked() {
        int size = this.mChildSessions.size();
        if (size == 0) {
            return EMPTY_CHILD_SESSION_ARRAY;
        }
        int[] iArr = new int[size];
        for (int i = 0; i < size; i++) {
            iArr[i] = this.mChildSessions.keyAt(i);
        }
        return iArr;
    }

    public int[] getChildSessionIds() {
        int[] childSessionIdsLocked;
        synchronized (this.mLock) {
            childSessionIdsLocked = getChildSessionIdsLocked();
        }
        return childSessionIdsLocked;
    }

    private boolean canBeAddedAsChild(int i) {
        boolean z;
        synchronized (this.mLock) {
            try {
                z = ((hasParentSessionId() && this.mParentSessionId != i) || isCommitted() || this.mDestroyed) ? false : true;
            } finally {
            }
        }
        return z;
    }

    private void acquireTransactionLock() {
        if (!this.mTransactionLock.compareAndSet(false, true)) {
            throw new java.lang.UnsupportedOperationException("Concurrent access not supported");
        }
    }

    private void releaseTransactionLock() {
        this.mTransactionLock.compareAndSet(true, false);
    }

    public void addChildSessionId(int i) {
        if (!this.params.isMultiPackage) {
            throw new java.lang.IllegalStateException("Single-session " + this.sessionId + " can't have child.");
        }
        com.android.server.pm.PackageInstallerSession session = this.mSessionProvider.getSession(i);
        if (session == null) {
            throw new java.lang.IllegalStateException("Unable to add child session " + i + " as it does not exist.");
        }
        if (session.params.isMultiPackage) {
            throw new java.lang.IllegalStateException("Multi-session " + i + " can't be a child.");
        }
        if (this.params.isStaged != session.params.isStaged) {
            throw new java.lang.IllegalStateException("Multipackage Inconsistency: session " + session.sessionId + " and session " + this.sessionId + " have inconsistent staged settings");
        }
        if (this.params.getEnableRollback() != session.params.getEnableRollback()) {
            throw new java.lang.IllegalStateException("Multipackage Inconsistency: session " + session.sessionId + " and session " + this.sessionId + " have inconsistent rollback settings");
        }
        boolean z = true;
        boolean z2 = containsApkSession() || !session.isApexSession();
        if (!sessionContains(new java.util.function.Predicate() { // from class: com.android.server.pm.PackageInstallerSession$$ExternalSyntheticLambda10
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                boolean isApexSession;
                isApexSession = ((com.android.server.pm.PackageInstallerSession) obj).isApexSession();
                return isApexSession;
            }
        }) && !session.isApexSession()) {
            z = false;
        }
        if (!this.params.isStaged && z2 && z) {
            throw new java.lang.IllegalStateException("Mix of APK and APEX is not supported for non-staged multi-package session");
        }
        try {
            acquireTransactionLock();
            session.acquireTransactionLock();
            if (!session.canBeAddedAsChild(this.sessionId)) {
                throw new java.lang.IllegalStateException("Unable to add child session " + i + " as it is in an invalid state.");
            }
            synchronized (this.mLock) {
                assertCallerIsOwnerOrRoot();
                assertPreparedAndNotSealedLocked("addChildSessionId");
                if (this.mChildSessions.indexOfKey(i) >= 0) {
                    releaseTransactionLock();
                    session.releaseTransactionLock();
                } else {
                    session.setParentSessionId(this.sessionId);
                    this.mChildSessions.put(i, session);
                    releaseTransactionLock();
                    session.releaseTransactionLock();
                }
            }
        } catch (java.lang.Throwable th) {
            releaseTransactionLock();
            session.releaseTransactionLock();
            throw th;
        }
    }

    public void removeChildSessionId(int i) {
        synchronized (this.mLock) {
            try {
                assertCallerIsOwnerOrRoot();
                assertPreparedAndNotSealedLocked("removeChildSessionId");
                int indexOfKey = this.mChildSessions.indexOfKey(i);
                if (indexOfKey < 0) {
                    return;
                }
                com.android.server.pm.PackageInstallerSession valueAt = this.mChildSessions.valueAt(indexOfKey);
                try {
                    acquireTransactionLock();
                    valueAt.acquireTransactionLock();
                    valueAt.setParentSessionId(-1);
                    this.mChildSessions.removeAt(indexOfKey);
                } finally {
                    releaseTransactionLock();
                    valueAt.releaseTransactionLock();
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    void setParentSessionId(int i) {
        synchronized (this.mLock) {
            if (i != -1) {
                try {
                    if (this.mParentSessionId != -1) {
                        throw new java.lang.IllegalStateException("The parent of " + this.sessionId + " is alreadyset to " + this.mParentSessionId);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
            this.mParentSessionId = i;
        }
    }

    boolean hasParentSessionId() {
        boolean z;
        synchronized (this.mLock) {
            z = this.mParentSessionId != -1;
        }
        return z;
    }

    public int getParentSessionId() {
        int i;
        synchronized (this.mLock) {
            i = this.mParentSessionId;
        }
        return i;
    }

    private void dispatchSessionFinished(int i, java.lang.String str, android.os.Bundle bundle) {
        sendUpdateToRemoteStatusReceiver(i, str, bundle, isPreapprovalRequested() && !isCommitted());
        synchronized (this.mLock) {
            this.mFinalStatus = i;
            this.mFinalMessage = str;
        }
        boolean z = i == 1;
        boolean z2 = bundle == null || !bundle.getBoolean("android.intent.extra.REPLACING");
        if (z && z2 && this.mPm.mInstallerService.okToSendBroadcasts()) {
            this.mPm.sendSessionCommitBroadcast(generateInfoScrubbed(true), this.userId);
        }
        this.mCallback.onSessionFinished(this, z);
        if (isDataLoaderInstallation()) {
            logDataLoaderInstallationSession(i);
        }
    }

    private void sendUpdateToRemoteStatusReceiver(int i, java.lang.String str, android.os.Bundle bundle, boolean z) {
        android.content.IntentSender preapprovalRemoteStatusReceiver = z ? getPreapprovalRemoteStatusReceiver() : getRemoteStatusReceiver();
        if (preapprovalRemoteStatusReceiver != null) {
            com.android.internal.os.SomeArgs obtain = com.android.internal.os.SomeArgs.obtain();
            obtain.arg1 = getPackageName();
            obtain.arg2 = str;
            obtain.arg3 = bundle;
            obtain.arg4 = preapprovalRemoteStatusReceiver;
            obtain.argi1 = i;
            obtain.argi2 = (!isPreapprovalRequested() || isCommitted()) ? 0 : 1;
            this.mHandler.obtainMessage(4, obtain).sendToTarget();
        }
    }

    private void dispatchSessionPreapproved() {
        android.content.IntentSender preapprovalRemoteStatusReceiver = getPreapprovalRemoteStatusReceiver();
        android.content.Intent intent = new android.content.Intent();
        intent.putExtra("android.content.pm.extra.SESSION_ID", this.sessionId);
        intent.putExtra("android.content.pm.extra.STATUS", 0);
        intent.putExtra("android.content.pm.extra.PRE_APPROVAL", true);
        try {
            android.app.BroadcastOptions makeBasic = android.app.BroadcastOptions.makeBasic();
            makeBasic.setPendingIntentBackgroundActivityLaunchAllowed(false);
            preapprovalRemoteStatusReceiver.sendIntent(this.mContext, 0, intent, null, null, null, makeBasic.toBundle());
        } catch (android.content.IntentSender.SendIntentException e) {
        }
    }

    public void requestUserPreapproval(@android.annotation.NonNull android.content.pm.PackageInstaller.PreapprovalDetails preapprovalDetails, @android.annotation.NonNull android.content.IntentSender intentSender) {
        validatePreapprovalRequest(preapprovalDetails, intentSender);
        if (!com.android.server.pm.PackageManagerService.isPreapprovalRequestAvailable()) {
            sendUpdateToRemoteStatusReceiver(-129, "Request user pre-approval is currently not available.", null, true);
        } else {
            dispatchPreapprovalRequest();
        }
    }

    private void validatePreapprovalRequest(@android.annotation.NonNull android.content.pm.PackageInstaller.PreapprovalDetails preapprovalDetails, @android.annotation.NonNull android.content.IntentSender intentSender) {
        assertCallerIsOwnerOrRoot();
        if (isMultiPackage()) {
            throw new java.lang.IllegalStateException("Session " + this.sessionId + " is a parent of multi-package session and requestUserPreapproval on the parent session isn't supported.");
        }
        synchronized (this.mLock) {
            assertPreparedAndNotSealedLocked("request of session " + this.sessionId);
            this.mPreapprovalDetails = preapprovalDetails;
            setPreapprovalRemoteStatusReceiver(intentSender);
        }
    }

    private void dispatchPreapprovalRequest() {
        synchronized (this.mLock) {
            assertPreparedAndNotPreapprovalRequestedLocked("dispatchPreapprovalRequest");
        }
        markAsPreapprovalRequested();
        this.mHandler.obtainMessage(6).sendToTarget();
    }

    private void markAsPreapprovalRequested() {
        this.mPreapprovalRequested.set(true);
    }

    public boolean isApplicationEnabledSettingPersistent() {
        return this.params.applicationEnabledSettingPersistent;
    }

    public boolean isRequestUpdateOwnership() {
        return (this.params.installFlags & 33554432) != 0;
    }

    public void setPreVerifiedDomains(@android.annotation.NonNull android.content.pm.verify.domain.DomainSet domainSet) {
        if (!(this.mInstallerUid == 0 || this.mInstallerUid == 2000)) {
            com.android.server.pm.Computer snapshotComputer = this.mPm.snapshotComputer();
            if (snapshotComputer.checkUidPermission("android.permission.ACCESS_INSTANT_APPS", this.mInstallerUid) != 0) {
                throw new java.lang.SecurityException("You need android.permission.ACCESS_INSTANT_APPS permission to set pre-verified domains.");
            }
            android.content.ComponentName instantAppInstallerComponent = snapshotComputer.getInstantAppInstallerComponent();
            if (instantAppInstallerComponent == null) {
                throw new java.lang.IllegalStateException("Instant app installer is not available. Only the instant app installer can call this API.");
            }
            if (!instantAppInstallerComponent.getPackageName().equals(getInstallerPackageName())) {
                throw new java.lang.SecurityException("Only the instant app installer can call this API.");
            }
        }
        long preVerifiedDomainsCountLimit = getPreVerifiedDomainsCountLimit();
        if (domainSet.getDomains().size() > preVerifiedDomainsCountLimit) {
            throw new java.lang.IllegalArgumentException("The number of pre-verified domains have exceeded the maximum of " + preVerifiedDomainsCountLimit);
        }
        long preVerifiedDomainLengthLimit = getPreVerifiedDomainLengthLimit();
        for (java.lang.String str : domainSet.getDomains()) {
            if (str.length() > preVerifiedDomainLengthLimit) {
                throw new java.lang.IllegalArgumentException("Pre-verified domain: [" + str + " ] exceeds maximum length allowed: " + preVerifiedDomainLengthLimit);
            }
        }
        synchronized (this.mLock) {
            this.mPreVerifiedDomains = domainSet;
        }
    }

    private static long getPreVerifiedDomainsCountLimit() {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            return android.provider.DeviceConfig.getLong("package_manager_service", PROPERTY_PRE_VERIFIED_DOMAINS_COUNT_LIMIT, 1000L);
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    private static long getPreVerifiedDomainLengthLimit() {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            return android.provider.DeviceConfig.getLong("package_manager_service", PROPERTY_PRE_VERIFIED_DOMAIN_LENGTH_LIMIT, DEFAULT_PRE_VERIFIED_DOMAIN_LENGTH_LIMIT);
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    @android.annotation.Nullable
    public android.content.pm.verify.domain.DomainSet getPreVerifiedDomains() {
        android.content.pm.verify.domain.DomainSet domainSet;
        assertCallerIsOwnerOrRoot();
        synchronized (this.mLock) {
            assertPreparedAndNotCommittedOrDestroyedLocked("getPreVerifiedDomains");
            domainSet = this.mPreVerifiedDomains;
        }
        return domainSet;
    }

    void setSessionReady() {
        synchronized (this.mLock) {
            if (this.mDestroyed || this.mSessionFailed) {
                return;
            }
            this.mSessionReady = true;
            this.mSessionApplied = false;
            this.mSessionFailed = false;
            this.mSessionErrorCode = 0;
            this.mSessionErrorMessage = "";
            this.mCallback.onSessionChanged(this);
        }
    }

    void setSessionFailed(int i, java.lang.String str) {
        synchronized (this.mLock) {
            if (this.mDestroyed || this.mSessionFailed) {
                return;
            }
            this.mSessionReady = false;
            this.mSessionApplied = false;
            this.mSessionFailed = true;
            this.mSessionErrorCode = i;
            this.mSessionErrorMessage = str;
            android.util.Slog.d(TAG, "Marking session " + this.sessionId + " as failed: " + str);
            destroy("Session marked as failed: " + str);
            this.mCallback.onSessionChanged(this);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setSessionApplied() {
        synchronized (this.mLock) {
            if (this.mDestroyed || this.mSessionFailed) {
                return;
            }
            this.mSessionReady = false;
            this.mSessionApplied = true;
            this.mSessionFailed = false;
            this.mSessionErrorCode = 1;
            this.mSessionErrorMessage = "";
            android.util.Slog.d(TAG, "Marking session " + this.sessionId + " as applied");
            destroy(null);
            this.mCallback.onSessionChanged(this);
        }
    }

    boolean isSessionReady() {
        boolean z;
        synchronized (this.mLock) {
            z = this.mSessionReady;
        }
        return z;
    }

    boolean isSessionApplied() {
        boolean z;
        synchronized (this.mLock) {
            z = this.mSessionApplied;
        }
        return z;
    }

    boolean isSessionFailed() {
        boolean z;
        synchronized (this.mLock) {
            z = this.mSessionFailed;
        }
        return z;
    }

    int getSessionErrorCode() {
        int i;
        synchronized (this.mLock) {
            i = this.mSessionErrorCode;
        }
        return i;
    }

    java.lang.String getSessionErrorMessage() {
        java.lang.String str;
        synchronized (this.mLock) {
            str = this.mSessionErrorMessage;
        }
        return str;
    }

    void registerUnarchivalListener(android.content.IntentSender intentSender) {
        synchronized (this.mLock) {
            this.mUnarchivalListeners.add(intentSender);
        }
    }

    java.util.Set<android.content.IntentSender> getUnarchivalListeners() {
        android.util.ArraySet arraySet;
        synchronized (this.mLock) {
            arraySet = new android.util.ArraySet(this.mUnarchivalListeners);
        }
        return arraySet;
    }

    void reportUnarchivalStatus(final int i, int i2, final long j, final android.app.PendingIntent pendingIntent) {
        if (getUnarchivalStatus() != -1) {
            throw new java.lang.IllegalStateException(android.text.TextUtils.formatSimple("Unarchival status for ID %s has already been set or a session has been created for it already by the caller.", new java.lang.Object[]{java.lang.Integer.valueOf(i2)}));
        }
        this.mUnarchivalStatus = i;
        this.mPm.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.pm.PackageInstallerSession$$ExternalSyntheticLambda7
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.pm.PackageInstallerSession.this.lambda$reportUnarchivalStatus$9(i, j, pendingIntent);
            }
        });
        if (i != 0) {
            android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.pm.PackageInstallerSession$$ExternalSyntheticLambda8
                public final void runOrThrow() {
                    com.android.server.pm.PackageInstallerSession.this.abandon();
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$reportUnarchivalStatus$9(int i, long j, android.app.PendingIntent pendingIntent) {
        this.mPm.mInstallerService.mPackageArchiver.notifyUnarchivalListener(i, getInstallerPackageName(), this.params.appPackageName, j, pendingIntent, getUnarchivalListeners(), this.userId);
    }

    int getUnarchivalStatus() {
        return this.mUnarchivalStatus;
    }

    private void destroy(java.lang.String str) {
        destroyInternal(str);
        java.util.Iterator<com.android.server.pm.PackageInstallerSession> it = getChildSessions().iterator();
        while (it.hasNext()) {
            it.next().destroyInternal(str);
        }
    }

    private void destroyInternal(java.lang.String str) {
        android.os.incremental.IncrementalFileStorages incrementalFileStorages;
        if (str != null) {
            android.util.Slog.i(TAG, "Session [" + this.sessionId + "] was destroyed because of [" + str + "]");
        }
        synchronized (this.mLock) {
            try {
                this.mSealed = true;
                if (!this.params.isStaged) {
                    this.mDestroyed = true;
                }
                java.util.Iterator<android.os.RevocableFileDescriptor> it = this.mFds.iterator();
                while (it.hasNext()) {
                    it.next().revoke();
                }
                java.util.Iterator<android.os.FileBridge> it2 = this.mBridges.iterator();
                while (it2.hasNext()) {
                    it2.next().forceClose();
                }
                incrementalFileStorages = this.mIncrementalFileStorages;
                this.mIncrementalFileStorages = null;
            } finally {
            }
        }
        if (incrementalFileStorages != null) {
            try {
                incrementalFileStorages.cleanUpAndMarkComplete();
            } catch (com.android.server.pm.Installer.InstallerException e) {
                return;
            }
        }
        if (this.stageDir != null) {
            this.mInstaller.rmPackageDir(this.stageDir.getName(), this.stageDir.getAbsolutePath());
        }
    }

    void dump(com.android.internal.util.IndentingPrintWriter indentingPrintWriter) {
        synchronized (this.mLock) {
            dumpLocked(indentingPrintWriter);
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void dumpLocked(com.android.internal.util.IndentingPrintWriter indentingPrintWriter) {
        float f;
        float f2;
        indentingPrintWriter.println("Session " + this.sessionId + ":");
        indentingPrintWriter.increaseIndent();
        indentingPrintWriter.printPair("userId", java.lang.Integer.valueOf(this.userId));
        indentingPrintWriter.printPair("mOriginalInstallerUid", java.lang.Integer.valueOf(this.mOriginalInstallerUid));
        indentingPrintWriter.printPair("mOriginalInstallerPackageName", this.mOriginalInstallerPackageName);
        indentingPrintWriter.printPair(ATTR_INSTALLER_PACKAGE_NAME, this.mInstallSource.mInstallerPackageName);
        indentingPrintWriter.printPair(ATTR_INITIATING_PACKAGE_NAME, this.mInstallSource.mInitiatingPackageName);
        indentingPrintWriter.printPair(ATTR_ORIGINATING_PACKAGE_NAME, this.mInstallSource.mOriginatingPackageName);
        indentingPrintWriter.printPair("mInstallerUid", java.lang.Integer.valueOf(this.mInstallerUid));
        indentingPrintWriter.printPair(ATTR_CREATED_MILLIS, java.lang.Long.valueOf(this.createdMillis));
        indentingPrintWriter.printPair(ATTR_UPDATED_MILLIS, java.lang.Long.valueOf(this.updatedMillis));
        indentingPrintWriter.printPair(ATTR_COMMITTED_MILLIS, java.lang.Long.valueOf(this.committedMillis));
        indentingPrintWriter.printPair("stageDir", this.stageDir);
        indentingPrintWriter.printPair("stageCid", this.stageCid);
        indentingPrintWriter.println();
        this.params.dump(indentingPrintWriter);
        synchronized (this.mProgressLock) {
            f = this.mClientProgress;
            f2 = this.mProgress;
        }
        indentingPrintWriter.printPair("mClientProgress", java.lang.Float.valueOf(f));
        indentingPrintWriter.printPair("mProgress", java.lang.Float.valueOf(f2));
        indentingPrintWriter.printPair("mCommitted", this.mCommitted);
        indentingPrintWriter.printPair("mPreapprovalRequested", this.mPreapprovalRequested);
        indentingPrintWriter.printPair("mSealed", java.lang.Boolean.valueOf(this.mSealed));
        indentingPrintWriter.printPair("mPermissionsManuallyAccepted", java.lang.Boolean.valueOf(this.mPermissionsManuallyAccepted));
        indentingPrintWriter.printPair("mStageDirInUse", java.lang.Boolean.valueOf(this.mStageDirInUse));
        indentingPrintWriter.printPair("mDestroyed", java.lang.Boolean.valueOf(this.mDestroyed));
        indentingPrintWriter.printPair("mFds", java.lang.Integer.valueOf(this.mFds.size()));
        indentingPrintWriter.printPair("mBridges", java.lang.Integer.valueOf(this.mBridges.size()));
        indentingPrintWriter.printPair("mFinalStatus", java.lang.Integer.valueOf(this.mFinalStatus));
        indentingPrintWriter.printPair("mFinalMessage", this.mFinalMessage);
        indentingPrintWriter.printPair("params.isMultiPackage", java.lang.Boolean.valueOf(this.params.isMultiPackage));
        indentingPrintWriter.printPair("params.isStaged", java.lang.Boolean.valueOf(this.params.isStaged));
        indentingPrintWriter.printPair("mParentSessionId", java.lang.Integer.valueOf(this.mParentSessionId));
        indentingPrintWriter.printPair("mChildSessionIds", getChildSessionIdsLocked());
        indentingPrintWriter.printPair("mSessionApplied", java.lang.Boolean.valueOf(this.mSessionApplied));
        indentingPrintWriter.printPair("mSessionFailed", java.lang.Boolean.valueOf(this.mSessionFailed));
        indentingPrintWriter.printPair("mSessionReady", java.lang.Boolean.valueOf(this.mSessionReady));
        indentingPrintWriter.printPair("mSessionErrorCode", java.lang.Integer.valueOf(this.mSessionErrorCode));
        indentingPrintWriter.printPair("mSessionErrorMessage", this.mSessionErrorMessage);
        indentingPrintWriter.printPair("mPreapprovalDetails", this.mPreapprovalDetails);
        if (this.mPreVerifiedDomains != null) {
            indentingPrintWriter.printPair("mPreVerifiedDomains", this.mPreVerifiedDomains);
        }
        indentingPrintWriter.println();
        indentingPrintWriter.decreaseIndent();
    }

    private static void sendOnUserActionRequired(android.content.Context context, android.content.IntentSender intentSender, int i, android.content.Intent intent) {
        android.content.Intent intent2 = new android.content.Intent();
        intent2.putExtra("android.content.pm.extra.SESSION_ID", i);
        intent2.putExtra("android.content.pm.extra.STATUS", -1);
        intent2.putExtra("android.content.pm.extra.PRE_APPROVAL", "android.content.pm.action.CONFIRM_PRE_APPROVAL".equals(intent.getAction()));
        intent2.putExtra("android.intent.extra.INTENT", intent);
        try {
            android.app.BroadcastOptions makeBasic = android.app.BroadcastOptions.makeBasic();
            makeBasic.setPendingIntentBackgroundActivityLaunchAllowed(false);
            intentSender.sendIntent(context, 0, intent2, null, null, null, makeBasic.toBundle());
        } catch (android.content.IntentSender.SendIntentException e) {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void sendOnPackageInstalled(android.content.Context context, android.content.IntentSender intentSender, int i, boolean z, int i2, java.lang.String str, int i3, boolean z2, java.lang.String str2, android.os.Bundle bundle) {
        if (1 == i3 && z) {
            android.app.Notification buildSuccessNotification = com.android.server.pm.PackageInstallerService.buildSuccessNotification(context, getDeviceOwnerInstalledPackageMsg(context, bundle != null && bundle.getBoolean("android.intent.extra.REPLACING")), str, i2);
            if (buildSuccessNotification != null) {
                ((android.app.NotificationManager) context.getSystemService("notification")).notify(str, 21, buildSuccessNotification);
            }
        }
        android.content.Intent intent = new android.content.Intent();
        intent.putExtra("android.content.pm.extra.PACKAGE_NAME", str);
        intent.putExtra("android.content.pm.extra.SESSION_ID", i);
        intent.putExtra("android.content.pm.extra.STATUS", android.content.pm.PackageManager.installStatusToPublicStatus(i3));
        intent.putExtra("android.content.pm.extra.STATUS_MESSAGE", android.content.pm.PackageManager.installStatusToString(i3, str2));
        intent.putExtra("android.content.pm.extra.LEGACY_STATUS", i3);
        intent.putExtra("android.content.pm.extra.PRE_APPROVAL", z2);
        if (bundle != null) {
            java.lang.String string = bundle.getString("android.content.pm.extra.FAILURE_EXISTING_PACKAGE");
            if (!android.text.TextUtils.isEmpty(string)) {
                intent.putExtra("android.content.pm.extra.OTHER_PACKAGE_NAME", string);
            }
            java.util.ArrayList<java.lang.String> stringArrayList = bundle.getStringArrayList("android.content.pm.extra.WARNINGS");
            if (!com.android.internal.util.ArrayUtils.isEmpty(stringArrayList)) {
                intent.putStringArrayListExtra("android.content.pm.extra.WARNINGS", stringArrayList);
            }
        }
        try {
            android.app.BroadcastOptions makeBasic = android.app.BroadcastOptions.makeBasic();
            makeBasic.setPendingIntentBackgroundActivityLaunchAllowed(false);
            intentSender.sendIntent(context, 0, intent, null, null, null, makeBasic.toBundle());
        } catch (android.content.IntentSender.SendIntentException e) {
        }
    }

    private static java.lang.String getDeviceOwnerInstalledPackageMsg(final android.content.Context context, boolean z) {
        android.app.admin.DevicePolicyManager devicePolicyManager = (android.app.admin.DevicePolicyManager) context.getSystemService(android.app.admin.DevicePolicyManager.class);
        if (z) {
            return devicePolicyManager.getResources().getString("Core.PACKAGE_UPDATED_BY_DO", new java.util.function.Supplier() { // from class: com.android.server.pm.PackageInstallerSession$$ExternalSyntheticLambda1
                @Override // java.util.function.Supplier
                public final java.lang.Object get() {
                    java.lang.String lambda$getDeviceOwnerInstalledPackageMsg$10;
                    lambda$getDeviceOwnerInstalledPackageMsg$10 = com.android.server.pm.PackageInstallerSession.lambda$getDeviceOwnerInstalledPackageMsg$10(context);
                    return lambda$getDeviceOwnerInstalledPackageMsg$10;
                }
            });
        }
        return devicePolicyManager.getResources().getString("Core.PACKAGE_INSTALLED_BY_DO", new java.util.function.Supplier() { // from class: com.android.server.pm.PackageInstallerSession$$ExternalSyntheticLambda2
            @Override // java.util.function.Supplier
            public final java.lang.Object get() {
                java.lang.String lambda$getDeviceOwnerInstalledPackageMsg$11;
                lambda$getDeviceOwnerInstalledPackageMsg$11 = com.android.server.pm.PackageInstallerSession.lambda$getDeviceOwnerInstalledPackageMsg$11(context);
                return lambda$getDeviceOwnerInstalledPackageMsg$11;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ java.lang.String lambda$getDeviceOwnerInstalledPackageMsg$10(android.content.Context context) {
        return context.getString(android.R.string.notification_listener_binding_label);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ java.lang.String lambda$getDeviceOwnerInstalledPackageMsg$11(android.content.Context context) {
        return context.getString(android.R.string.notification_inbox_ellipsis);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void sendPendingStreaming(android.content.Context context, android.content.IntentSender intentSender, int i, @android.annotation.Nullable java.lang.String str) {
        if (intentSender == null) {
            android.util.Slog.e(TAG, "Missing receiver for pending streaming status.");
            return;
        }
        android.content.Intent intent = new android.content.Intent();
        intent.putExtra("android.content.pm.extra.SESSION_ID", i);
        intent.putExtra("android.content.pm.extra.STATUS", -2);
        if (!android.text.TextUtils.isEmpty(str)) {
            intent.putExtra("android.content.pm.extra.STATUS_MESSAGE", "Staging Image Not Ready [" + str + "]");
        } else {
            intent.putExtra("android.content.pm.extra.STATUS_MESSAGE", "Staging Image Not Ready");
        }
        try {
            android.app.BroadcastOptions makeBasic = android.app.BroadcastOptions.makeBasic();
            makeBasic.setPendingIntentBackgroundActivityLaunchAllowed(false);
            intentSender.sendIntent(context, 0, intent, null, null, null, makeBasic.toBundle());
        } catch (android.content.IntentSender.SendIntentException e) {
        }
    }

    private static void writePermissionsLocked(@android.annotation.NonNull com.android.modules.utils.TypedXmlSerializer typedXmlSerializer, @android.annotation.NonNull android.content.pm.PackageInstaller.SessionParams sessionParams) throws java.io.IOException {
        android.util.ArrayMap permissionStates = sessionParams.getPermissionStates();
        for (int i = 0; i < permissionStates.size(); i++) {
            java.lang.String str = (java.lang.String) permissionStates.keyAt(i);
            java.lang.String str2 = ((java.lang.Integer) permissionStates.valueAt(i)).intValue() == 1 ? TAG_GRANT_PERMISSION : TAG_DENY_PERMISSION;
            typedXmlSerializer.startTag((java.lang.String) null, str2);
            com.android.internal.util.XmlUtils.writeStringAttribute(typedXmlSerializer, "name", str);
            typedXmlSerializer.endTag((java.lang.String) null, str2);
        }
    }

    private static void writeWhitelistedRestrictedPermissionsLocked(@android.annotation.NonNull com.android.modules.utils.TypedXmlSerializer typedXmlSerializer, @android.annotation.Nullable java.util.List<java.lang.String> list) throws java.io.IOException {
        if (list != null) {
            int size = list.size();
            for (int i = 0; i < size; i++) {
                typedXmlSerializer.startTag((java.lang.String) null, TAG_WHITELISTED_RESTRICTED_PERMISSION);
                com.android.internal.util.XmlUtils.writeStringAttribute(typedXmlSerializer, "name", list.get(i));
                typedXmlSerializer.endTag((java.lang.String) null, TAG_WHITELISTED_RESTRICTED_PERMISSION);
            }
        }
    }

    private static void writeAutoRevokePermissionsMode(@android.annotation.NonNull com.android.modules.utils.TypedXmlSerializer typedXmlSerializer, int i) throws java.io.IOException {
        typedXmlSerializer.startTag((java.lang.String) null, TAG_AUTO_REVOKE_PERMISSIONS_MODE);
        typedXmlSerializer.attributeInt((java.lang.String) null, "mode", i);
        typedXmlSerializer.endTag((java.lang.String) null, TAG_AUTO_REVOKE_PERMISSIONS_MODE);
    }

    private static java.io.File buildAppIconFile(int i, @android.annotation.NonNull java.io.File file) {
        return new java.io.File(file, "app_icon." + i + ".png");
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:30:0x0273 A[Catch: all -> 0x000f, LOOP:0: B:29:0x0271->B:30:0x0273, LOOP_END, TryCatch #4 {all -> 0x000f, blocks: (B:4:0x0003, B:6:0x0007, B:8:0x000d, B:11:0x0012, B:13:0x0081, B:14:0x008d, B:16:0x0091, B:17:0x0099, B:20:0x0187, B:22:0x018e, B:23:0x01ca, B:25:0x01e9, B:27:0x01ef, B:28:0x026b, B:30:0x0273, B:32:0x0288, B:34:0x0290, B:36:0x02d3, B:38:0x02dc, B:40:0x02f4, B:42:0x031d, B:44:0x0320, B:46:0x0328, B:48:0x033e, B:53:0x0342, B:52:0x035a, B:57:0x035d, B:59:0x0361, B:60:0x036b, B:62:0x0371, B:64:0x0389, B:67:0x01f4, B:69:0x01fa, B:72:0x0206, B:77:0x022d, B:78:0x025e, B:87:0x0267, B:88:0x026a), top: B:3:0x0003 }] */
    /* JADX WARN: Removed duplicated region for block: B:34:0x0290 A[Catch: all -> 0x000f, LOOP:1: B:33:0x028e->B:34:0x0290, LOOP_END, TryCatch #4 {all -> 0x000f, blocks: (B:4:0x0003, B:6:0x0007, B:8:0x000d, B:11:0x0012, B:13:0x0081, B:14:0x008d, B:16:0x0091, B:17:0x0099, B:20:0x0187, B:22:0x018e, B:23:0x01ca, B:25:0x01e9, B:27:0x01ef, B:28:0x026b, B:30:0x0273, B:32:0x0288, B:34:0x0290, B:36:0x02d3, B:38:0x02dc, B:40:0x02f4, B:42:0x031d, B:44:0x0320, B:46:0x0328, B:48:0x033e, B:53:0x0342, B:52:0x035a, B:57:0x035d, B:59:0x0361, B:60:0x036b, B:62:0x0371, B:64:0x0389, B:67:0x01f4, B:69:0x01fa, B:72:0x0206, B:77:0x022d, B:78:0x025e, B:87:0x0267, B:88:0x026a), top: B:3:0x0003 }] */
    /* JADX WARN: Removed duplicated region for block: B:38:0x02dc A[Catch: all -> 0x000f, TryCatch #4 {all -> 0x000f, blocks: (B:4:0x0003, B:6:0x0007, B:8:0x000d, B:11:0x0012, B:13:0x0081, B:14:0x008d, B:16:0x0091, B:17:0x0099, B:20:0x0187, B:22:0x018e, B:23:0x01ca, B:25:0x01e9, B:27:0x01ef, B:28:0x026b, B:30:0x0273, B:32:0x0288, B:34:0x0290, B:36:0x02d3, B:38:0x02dc, B:40:0x02f4, B:42:0x031d, B:44:0x0320, B:46:0x0328, B:48:0x033e, B:53:0x0342, B:52:0x035a, B:57:0x035d, B:59:0x0361, B:60:0x036b, B:62:0x0371, B:64:0x0389, B:67:0x01f4, B:69:0x01fa, B:72:0x0206, B:77:0x022d, B:78:0x025e, B:87:0x0267, B:88:0x026a), top: B:3:0x0003 }] */
    /* JADX WARN: Removed duplicated region for block: B:46:0x0328 A[Catch: all -> 0x000f, TryCatch #4 {all -> 0x000f, blocks: (B:4:0x0003, B:6:0x0007, B:8:0x000d, B:11:0x0012, B:13:0x0081, B:14:0x008d, B:16:0x0091, B:17:0x0099, B:20:0x0187, B:22:0x018e, B:23:0x01ca, B:25:0x01e9, B:27:0x01ef, B:28:0x026b, B:30:0x0273, B:32:0x0288, B:34:0x0290, B:36:0x02d3, B:38:0x02dc, B:40:0x02f4, B:42:0x031d, B:44:0x0320, B:46:0x0328, B:48:0x033e, B:53:0x0342, B:52:0x035a, B:57:0x035d, B:59:0x0361, B:60:0x036b, B:62:0x0371, B:64:0x0389, B:67:0x01f4, B:69:0x01fa, B:72:0x0206, B:77:0x022d, B:78:0x025e, B:87:0x0267, B:88:0x026a), top: B:3:0x0003 }] */
    /* JADX WARN: Removed duplicated region for block: B:59:0x0361 A[Catch: all -> 0x000f, TryCatch #4 {all -> 0x000f, blocks: (B:4:0x0003, B:6:0x0007, B:8:0x000d, B:11:0x0012, B:13:0x0081, B:14:0x008d, B:16:0x0091, B:17:0x0099, B:20:0x0187, B:22:0x018e, B:23:0x01ca, B:25:0x01e9, B:27:0x01ef, B:28:0x026b, B:30:0x0273, B:32:0x0288, B:34:0x0290, B:36:0x02d3, B:38:0x02dc, B:40:0x02f4, B:42:0x031d, B:44:0x0320, B:46:0x0328, B:48:0x033e, B:53:0x0342, B:52:0x035a, B:57:0x035d, B:59:0x0361, B:60:0x036b, B:62:0x0371, B:64:0x0389, B:67:0x01f4, B:69:0x01fa, B:72:0x0206, B:77:0x022d, B:78:0x025e, B:87:0x0267, B:88:0x026a), top: B:3:0x0003 }] */
    /* JADX WARN: Type inference failed for: r1v57, types: [int] */
    /* JADX WARN: Type inference failed for: r1v58 */
    /* JADX WARN: Type inference failed for: r1v62, types: [java.lang.AutoCloseable] */
    /* JADX WARN: Type inference failed for: r1v89 */
    /* JADX WARN: Type inference failed for: r1v90 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    void write(@android.annotation.NonNull com.android.modules.utils.TypedXmlSerializer typedXmlSerializer, @android.annotation.NonNull java.io.File file) throws java.io.IOException {
        ?? r1;
        java.io.FileOutputStream fileOutputStream;
        int size;
        int i;
        int size2;
        synchronized (this.mLock) {
            try {
                if (!this.mDestroyed || this.params.isStaged) {
                    java.lang.AutoCloseable autoCloseable = null;
                    typedXmlSerializer.startTag((java.lang.String) null, TAG_SESSION);
                    typedXmlSerializer.attributeInt((java.lang.String) null, ATTR_SESSION_ID, this.sessionId);
                    typedXmlSerializer.attributeInt((java.lang.String) null, "userId", this.userId);
                    com.android.internal.util.XmlUtils.writeStringAttribute(typedXmlSerializer, ATTR_INSTALLER_PACKAGE_NAME, this.mInstallSource.mInstallerPackageName);
                    typedXmlSerializer.attributeInt((java.lang.String) null, ATTR_INSTALLER_PACKAGE_UID, this.mInstallSource.mInstallerPackageUid);
                    com.android.internal.util.XmlUtils.writeStringAttribute(typedXmlSerializer, ATTR_UPDATE_OWNER_PACKAGE_NAME, this.mInstallSource.mUpdateOwnerPackageName);
                    com.android.internal.util.XmlUtils.writeStringAttribute(typedXmlSerializer, ATTR_INSTALLER_ATTRIBUTION_TAG, this.mInstallSource.mInstallerAttributionTag);
                    typedXmlSerializer.attributeInt((java.lang.String) null, ATTR_INSTALLER_UID, this.mInstallerUid);
                    com.android.internal.util.XmlUtils.writeStringAttribute(typedXmlSerializer, ATTR_INITIATING_PACKAGE_NAME, this.mInstallSource.mInitiatingPackageName);
                    com.android.internal.util.XmlUtils.writeStringAttribute(typedXmlSerializer, ATTR_ORIGINATING_PACKAGE_NAME, this.mInstallSource.mOriginatingPackageName);
                    typedXmlSerializer.attributeLong((java.lang.String) null, ATTR_CREATED_MILLIS, this.createdMillis);
                    typedXmlSerializer.attributeLong((java.lang.String) null, ATTR_UPDATED_MILLIS, this.updatedMillis);
                    typedXmlSerializer.attributeLong((java.lang.String) null, ATTR_COMMITTED_MILLIS, this.committedMillis);
                    if (this.stageDir != null) {
                        com.android.internal.util.XmlUtils.writeStringAttribute(typedXmlSerializer, ATTR_SESSION_STAGE_DIR, this.stageDir.getAbsolutePath());
                    }
                    if (this.stageCid != null) {
                        com.android.internal.util.XmlUtils.writeStringAttribute(typedXmlSerializer, ATTR_SESSION_STAGE_CID, this.stageCid);
                    }
                    com.android.internal.util.XmlUtils.writeBooleanAttribute(typedXmlSerializer, ATTR_PREPARED, this.mPrepared);
                    com.android.internal.util.XmlUtils.writeBooleanAttribute(typedXmlSerializer, ATTR_COMMITTED, isCommitted());
                    com.android.internal.util.XmlUtils.writeBooleanAttribute(typedXmlSerializer, ATTR_DESTROYED, this.mDestroyed);
                    com.android.internal.util.XmlUtils.writeBooleanAttribute(typedXmlSerializer, ATTR_SEALED, this.mSealed);
                    com.android.internal.util.XmlUtils.writeBooleanAttribute(typedXmlSerializer, ATTR_MULTI_PACKAGE, this.params.isMultiPackage);
                    com.android.internal.util.XmlUtils.writeBooleanAttribute(typedXmlSerializer, ATTR_STAGED_SESSION, this.params.isStaged);
                    com.android.internal.util.XmlUtils.writeBooleanAttribute(typedXmlSerializer, ATTR_IS_READY, this.mSessionReady);
                    com.android.internal.util.XmlUtils.writeBooleanAttribute(typedXmlSerializer, ATTR_IS_FAILED, this.mSessionFailed);
                    com.android.internal.util.XmlUtils.writeBooleanAttribute(typedXmlSerializer, ATTR_IS_APPLIED, this.mSessionApplied);
                    typedXmlSerializer.attributeInt((java.lang.String) null, ATTR_PACKAGE_SOURCE, this.params.packageSource);
                    typedXmlSerializer.attributeInt((java.lang.String) null, ATTR_SESSION_ERROR_CODE, this.mSessionErrorCode);
                    com.android.internal.util.XmlUtils.writeStringAttribute(typedXmlSerializer, ATTR_SESSION_ERROR_MESSAGE, this.mSessionErrorMessage);
                    typedXmlSerializer.attributeInt((java.lang.String) null, ATTR_PARENT_SESSION_ID, this.mParentSessionId);
                    typedXmlSerializer.attributeInt((java.lang.String) null, "mode", this.params.mode);
                    typedXmlSerializer.attributeInt((java.lang.String) null, ATTR_INSTALL_FLAGS, this.params.installFlags);
                    typedXmlSerializer.attributeInt((java.lang.String) null, ATTR_INSTALL_LOCATION, this.params.installLocation);
                    typedXmlSerializer.attributeLong((java.lang.String) null, ATTR_SIZE_BYTES, this.params.sizeBytes);
                    com.android.internal.util.XmlUtils.writeStringAttribute(typedXmlSerializer, ATTR_APP_PACKAGE_NAME, this.params.appPackageName);
                    com.android.internal.util.XmlUtils.writeStringAttribute(typedXmlSerializer, ATTR_APP_LABEL, this.params.appLabel);
                    com.android.internal.util.XmlUtils.writeUriAttribute(typedXmlSerializer, ATTR_ORIGINATING_URI, this.params.originatingUri);
                    typedXmlSerializer.attributeInt((java.lang.String) null, ATTR_ORIGINATING_UID, this.params.originatingUid);
                    com.android.internal.util.XmlUtils.writeUriAttribute(typedXmlSerializer, ATTR_REFERRER_URI, this.params.referrerUri);
                    com.android.internal.util.XmlUtils.writeStringAttribute(typedXmlSerializer, ATTR_ABI_OVERRIDE, this.params.abiOverride);
                    com.android.internal.util.XmlUtils.writeStringAttribute(typedXmlSerializer, ATTR_VOLUME_UUID, this.params.volumeUuid);
                    typedXmlSerializer.attributeInt((java.lang.String) null, ATTR_INSTALL_REASON, this.params.installReason);
                    com.android.internal.util.XmlUtils.writeBooleanAttribute(typedXmlSerializer, ATTR_APPLICATION_ENABLED_SETTING_PERSISTENT, this.params.applicationEnabledSettingPersistent);
                    boolean z = this.params.dataLoaderParams != null;
                    com.android.internal.util.XmlUtils.writeBooleanAttribute(typedXmlSerializer, ATTR_IS_DATALOADER, z);
                    if (z) {
                        typedXmlSerializer.attributeInt((java.lang.String) null, ATTR_DATALOADER_TYPE, this.params.dataLoaderParams.getType());
                        com.android.internal.util.XmlUtils.writeStringAttribute(typedXmlSerializer, ATTR_DATALOADER_PACKAGE_NAME, this.params.dataLoaderParams.getComponentName().getPackageName());
                        com.android.internal.util.XmlUtils.writeStringAttribute(typedXmlSerializer, ATTR_DATALOADER_CLASS_NAME, this.params.dataLoaderParams.getComponentName().getClassName());
                        com.android.internal.util.XmlUtils.writeStringAttribute(typedXmlSerializer, ATTR_DATALOADER_ARGUMENTS, this.params.dataLoaderParams.getArguments());
                    }
                    writePermissionsLocked(typedXmlSerializer, this.params);
                    writeWhitelistedRestrictedPermissionsLocked(typedXmlSerializer, this.params.whitelistedRestrictedPermissions);
                    writeAutoRevokePermissionsMode(typedXmlSerializer, this.params.autoRevokePermissionsMode);
                    java.io.File buildAppIconFile = buildAppIconFile(this.sessionId, file);
                    if (this.params.appIcon == null && buildAppIconFile.exists()) {
                        buildAppIconFile.delete();
                    } else if (this.params.appIcon != null && buildAppIconFile.lastModified() != this.params.appIconLastModified) {
                        try {
                            android.util.Slog.w(TAG, "Writing changed icon " + buildAppIconFile);
                            try {
                                fileOutputStream = new java.io.FileOutputStream(buildAppIconFile);
                            } catch (java.io.IOException e) {
                                e = e;
                                fileOutputStream = null;
                            } catch (java.lang.Throwable th) {
                                th = th;
                                libcore.io.IoUtils.closeQuietly(autoCloseable);
                                throw th;
                            }
                            try {
                                this.params.appIcon.compress(android.graphics.Bitmap.CompressFormat.PNG, 90, fileOutputStream);
                                r1 = fileOutputStream;
                            } catch (java.io.IOException e2) {
                                e = e2;
                                android.util.Slog.w(TAG, "Failed to write icon " + buildAppIconFile + ": " + e.getMessage());
                                r1 = fileOutputStream;
                                libcore.io.IoUtils.closeQuietly((java.lang.AutoCloseable) r1);
                                this.params.appIconLastModified = buildAppIconFile.lastModified();
                                while (r4 < r1) {
                                }
                                while (r4 < r1) {
                                }
                                size = this.mChecksums.size();
                                while (i < size) {
                                }
                                size2 = this.mChecksums.size();
                                while (r3 < size2) {
                                }
                                if (this.mPreVerifiedDomains != null) {
                                }
                                typedXmlSerializer.endTag((java.lang.String) null, TAG_SESSION);
                            }
                            libcore.io.IoUtils.closeQuietly((java.lang.AutoCloseable) r1);
                            this.params.appIconLastModified = buildAppIconFile.lastModified();
                        } catch (java.lang.Throwable th2) {
                            th = th2;
                            autoCloseable = r1;
                        }
                    }
                    for (int i2 : getChildSessionIdsLocked()) {
                        typedXmlSerializer.startTag((java.lang.String) null, TAG_CHILD_SESSION);
                        typedXmlSerializer.attributeInt((java.lang.String) null, ATTR_SESSION_ID, i2);
                        typedXmlSerializer.endTag((java.lang.String) null, TAG_CHILD_SESSION);
                    }
                    for (android.content.pm.InstallationFile installationFile : getInstallationFilesLocked()) {
                        typedXmlSerializer.startTag((java.lang.String) null, TAG_SESSION_FILE);
                        typedXmlSerializer.attributeInt((java.lang.String) null, ATTR_LOCATION, installationFile.getLocation());
                        com.android.internal.util.XmlUtils.writeStringAttribute(typedXmlSerializer, "name", installationFile.getName());
                        typedXmlSerializer.attributeLong((java.lang.String) null, ATTR_LENGTH_BYTES, installationFile.getLengthBytes());
                        com.android.internal.util.XmlUtils.writeByteArrayAttribute(typedXmlSerializer, ATTR_METADATA, installationFile.getMetadata());
                        com.android.internal.util.XmlUtils.writeByteArrayAttribute(typedXmlSerializer, ATTR_SIGNATURE, installationFile.getSignature());
                        typedXmlSerializer.endTag((java.lang.String) null, TAG_SESSION_FILE);
                    }
                    size = this.mChecksums.size();
                    for (i = 0; i < size; i++) {
                        java.lang.String keyAt = this.mChecksums.keyAt(i);
                        for (android.content.pm.Checksum checksum : this.mChecksums.valueAt(i).getChecksums()) {
                            typedXmlSerializer.startTag((java.lang.String) null, TAG_SESSION_CHECKSUM);
                            com.android.internal.util.XmlUtils.writeStringAttribute(typedXmlSerializer, "name", keyAt);
                            typedXmlSerializer.attributeInt((java.lang.String) null, ATTR_CHECKSUM_KIND, checksum.getType());
                            com.android.internal.util.XmlUtils.writeByteArrayAttribute(typedXmlSerializer, ATTR_CHECKSUM_VALUE, checksum.getValue());
                            typedXmlSerializer.endTag((java.lang.String) null, TAG_SESSION_CHECKSUM);
                        }
                    }
                    size2 = this.mChecksums.size();
                    for (int i3 = 0; i3 < size2; i3++) {
                        java.lang.String keyAt2 = this.mChecksums.keyAt(i3);
                        byte[] signature = this.mChecksums.valueAt(i3).getSignature();
                        if (signature != null && signature.length != 0) {
                            typedXmlSerializer.startTag((java.lang.String) null, TAG_SESSION_CHECKSUM_SIGNATURE);
                            com.android.internal.util.XmlUtils.writeStringAttribute(typedXmlSerializer, "name", keyAt2);
                            com.android.internal.util.XmlUtils.writeByteArrayAttribute(typedXmlSerializer, ATTR_SIGNATURE, signature);
                            typedXmlSerializer.endTag((java.lang.String) null, TAG_SESSION_CHECKSUM_SIGNATURE);
                        }
                    }
                    if (this.mPreVerifiedDomains != null) {
                        for (java.lang.String str : this.mPreVerifiedDomains.getDomains()) {
                            typedXmlSerializer.startTag((java.lang.String) null, TAG_PRE_VERIFIED_DOMAINS);
                            com.android.internal.util.XmlUtils.writeStringAttribute(typedXmlSerializer, "domain", str);
                            typedXmlSerializer.endTag((java.lang.String) null, TAG_PRE_VERIFIED_DOMAINS);
                        }
                    }
                    typedXmlSerializer.endTag((java.lang.String) null, TAG_SESSION);
                }
            } catch (java.lang.Throwable th3) {
                throw th3;
            }
        }
    }

    private static boolean isStagedSessionStateValid(boolean z, boolean z2, boolean z3) {
        return ((z || z2 || z3) && (!z || z2 || z3) && ((z || !z2 || z3) && (z || z2 || !z3))) ? false : true;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Multi-variable type inference failed */
    public static com.android.server.pm.PackageInstallerSession readFromXml(@android.annotation.NonNull com.android.modules.utils.TypedXmlPullParser typedXmlPullParser, @android.annotation.NonNull com.android.server.pm.PackageInstallerService.InternalCallback internalCallback, @android.annotation.NonNull android.content.Context context, @android.annotation.NonNull com.android.server.pm.PackageManagerService packageManagerService, android.os.Looper looper, @android.annotation.NonNull com.android.server.pm.StagingManager stagingManager, @android.annotation.NonNull java.io.File file, @android.annotation.NonNull com.android.server.pm.PackageSessionProvider packageSessionProvider, @android.annotation.NonNull com.android.server.pm.SilentUpdatePolicy silentUpdatePolicy) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
        int i;
        java.lang.String str;
        android.util.ArraySet arraySet;
        android.util.ArrayMap arrayMap;
        android.content.pm.PackageInstaller.SessionParams sessionParams;
        android.util.ArrayMap arrayMap2;
        android.content.pm.InstallationFile[] installationFileArr;
        android.content.pm.PackageInstaller.SessionParams sessionParams2;
        int[] iArr;
        android.content.pm.InstallationFile[] installationFileArr2;
        android.util.ArrayMap arrayMap3;
        char c;
        android.util.ArraySet arraySet2;
        android.util.ArrayMap arrayMap4;
        android.content.pm.PackageInstaller.SessionParams sessionParams3;
        android.util.ArrayMap arrayMap5;
        int attributeInt = typedXmlPullParser.getAttributeInt((java.lang.String) null, ATTR_SESSION_ID);
        int attributeInt2 = typedXmlPullParser.getAttributeInt((java.lang.String) null, "userId");
        java.lang.String readStringAttribute = com.android.internal.util.XmlUtils.readStringAttribute(typedXmlPullParser, ATTR_INSTALLER_PACKAGE_NAME);
        int attributeInt3 = typedXmlPullParser.getAttributeInt((java.lang.String) null, ATTR_INSTALLER_PACKAGE_UID, -1);
        java.lang.String readStringAttribute2 = com.android.internal.util.XmlUtils.readStringAttribute(typedXmlPullParser, ATTR_UPDATE_OWNER_PACKAGE_NAME);
        java.lang.String readStringAttribute3 = com.android.internal.util.XmlUtils.readStringAttribute(typedXmlPullParser, ATTR_INSTALLER_ATTRIBUTION_TAG);
        int attributeInt4 = typedXmlPullParser.getAttributeInt((java.lang.String) null, ATTR_INSTALLER_UID, packageManagerService.snapshotComputer().getPackageUid(readStringAttribute, 8192L, attributeInt2));
        java.lang.String readStringAttribute4 = com.android.internal.util.XmlUtils.readStringAttribute(typedXmlPullParser, ATTR_INITIATING_PACKAGE_NAME);
        java.lang.String readStringAttribute5 = com.android.internal.util.XmlUtils.readStringAttribute(typedXmlPullParser, ATTR_ORIGINATING_PACKAGE_NAME);
        long attributeLong = typedXmlPullParser.getAttributeLong((java.lang.String) null, ATTR_CREATED_MILLIS);
        typedXmlPullParser.getAttributeLong((java.lang.String) null, ATTR_UPDATED_MILLIS);
        long attributeLong2 = typedXmlPullParser.getAttributeLong((java.lang.String) null, ATTR_COMMITTED_MILLIS, 0L);
        java.lang.String readStringAttribute6 = com.android.internal.util.XmlUtils.readStringAttribute(typedXmlPullParser, ATTR_SESSION_STAGE_DIR);
        java.io.File file2 = readStringAttribute6 != null ? new java.io.File(readStringAttribute6) : null;
        java.lang.String readStringAttribute7 = com.android.internal.util.XmlUtils.readStringAttribute(typedXmlPullParser, ATTR_SESSION_STAGE_CID);
        boolean attributeBoolean = typedXmlPullParser.getAttributeBoolean((java.lang.String) null, ATTR_PREPARED, true);
        boolean attributeBoolean2 = typedXmlPullParser.getAttributeBoolean((java.lang.String) null, ATTR_COMMITTED, false);
        boolean attributeBoolean3 = typedXmlPullParser.getAttributeBoolean((java.lang.String) null, ATTR_DESTROYED, false);
        boolean attributeBoolean4 = typedXmlPullParser.getAttributeBoolean((java.lang.String) null, ATTR_SEALED, false);
        int attributeInt5 = typedXmlPullParser.getAttributeInt((java.lang.String) null, ATTR_PARENT_SESSION_ID, -1);
        android.content.pm.PackageInstaller.SessionParams sessionParams4 = new android.content.pm.PackageInstaller.SessionParams(-1);
        sessionParams4.isMultiPackage = typedXmlPullParser.getAttributeBoolean((java.lang.String) null, ATTR_MULTI_PACKAGE, false);
        sessionParams4.isStaged = typedXmlPullParser.getAttributeBoolean((java.lang.String) null, ATTR_STAGED_SESSION, false);
        sessionParams4.mode = typedXmlPullParser.getAttributeInt((java.lang.String) null, "mode");
        sessionParams4.installFlags = typedXmlPullParser.getAttributeInt((java.lang.String) null, ATTR_INSTALL_FLAGS);
        sessionParams4.installLocation = typedXmlPullParser.getAttributeInt((java.lang.String) null, ATTR_INSTALL_LOCATION);
        sessionParams4.sizeBytes = typedXmlPullParser.getAttributeLong((java.lang.String) null, ATTR_SIZE_BYTES);
        sessionParams4.appPackageName = com.android.internal.util.XmlUtils.readStringAttribute(typedXmlPullParser, ATTR_APP_PACKAGE_NAME);
        sessionParams4.appIcon = com.android.internal.util.XmlUtils.readBitmapAttribute(typedXmlPullParser, ATTR_APP_ICON);
        sessionParams4.appLabel = com.android.internal.util.XmlUtils.readStringAttribute(typedXmlPullParser, ATTR_APP_LABEL);
        sessionParams4.originatingUri = com.android.internal.util.XmlUtils.readUriAttribute(typedXmlPullParser, ATTR_ORIGINATING_URI);
        sessionParams4.originatingUid = typedXmlPullParser.getAttributeInt((java.lang.String) null, ATTR_ORIGINATING_UID, -1);
        sessionParams4.referrerUri = com.android.internal.util.XmlUtils.readUriAttribute(typedXmlPullParser, ATTR_REFERRER_URI);
        sessionParams4.abiOverride = com.android.internal.util.XmlUtils.readStringAttribute(typedXmlPullParser, ATTR_ABI_OVERRIDE);
        sessionParams4.volumeUuid = com.android.internal.util.XmlUtils.readStringAttribute(typedXmlPullParser, ATTR_VOLUME_UUID);
        sessionParams4.installReason = typedXmlPullParser.getAttributeInt((java.lang.String) null, ATTR_INSTALL_REASON);
        sessionParams4.packageSource = typedXmlPullParser.getAttributeInt((java.lang.String) null, ATTR_PACKAGE_SOURCE);
        sessionParams4.applicationEnabledSettingPersistent = typedXmlPullParser.getAttributeBoolean((java.lang.String) null, ATTR_APPLICATION_ENABLED_SETTING_PERSISTENT, false);
        if (!typedXmlPullParser.getAttributeBoolean((java.lang.String) null, ATTR_IS_DATALOADER, false)) {
            i = attributeInt4;
        } else {
            i = attributeInt4;
            sessionParams4.dataLoaderParams = new android.content.pm.DataLoaderParams(typedXmlPullParser.getAttributeInt((java.lang.String) null, ATTR_DATALOADER_TYPE), new android.content.ComponentName(com.android.internal.util.XmlUtils.readStringAttribute(typedXmlPullParser, ATTR_DATALOADER_PACKAGE_NAME), com.android.internal.util.XmlUtils.readStringAttribute(typedXmlPullParser, ATTR_DATALOADER_CLASS_NAME)), com.android.internal.util.XmlUtils.readStringAttribute(typedXmlPullParser, ATTR_DATALOADER_ARGUMENTS));
        }
        java.io.File buildAppIconFile = buildAppIconFile(attributeInt, file);
        if (buildAppIconFile.exists()) {
            sessionParams4.appIcon = android.graphics.BitmapFactory.decodeFile(buildAppIconFile.getAbsolutePath());
            sessionParams4.appIconLastModified = buildAppIconFile.lastModified();
        }
        boolean attributeBoolean5 = typedXmlPullParser.getAttributeBoolean((java.lang.String) null, ATTR_IS_READY, false);
        boolean attributeBoolean6 = typedXmlPullParser.getAttributeBoolean((java.lang.String) null, ATTR_IS_FAILED, false);
        boolean attributeBoolean7 = typedXmlPullParser.getAttributeBoolean((java.lang.String) null, ATTR_IS_APPLIED, false);
        int attributeInt6 = typedXmlPullParser.getAttributeInt((java.lang.String) null, ATTR_SESSION_ERROR_CODE, 0);
        java.lang.String readStringAttribute8 = com.android.internal.util.XmlUtils.readStringAttribute(typedXmlPullParser, ATTR_SESSION_ERROR_MESSAGE);
        if (!isStagedSessionStateValid(attributeBoolean5, attributeBoolean7, attributeBoolean6)) {
            throw new java.lang.IllegalArgumentException("Can't restore staged session with invalid state.");
        }
        java.util.ArrayList arrayList = new java.util.ArrayList();
        android.util.ArraySet arraySet3 = new android.util.ArraySet();
        android.util.ArraySet arraySet4 = new android.util.ArraySet();
        java.util.ArrayList arrayList2 = new java.util.ArrayList();
        android.util.IntArray intArray = new android.util.IntArray();
        java.util.ArrayList arrayList3 = new java.util.ArrayList();
        android.util.ArrayMap arrayMap6 = new android.util.ArrayMap();
        android.util.ArrayMap arrayMap7 = new android.util.ArrayMap();
        android.util.ArraySet arraySet5 = new android.util.ArraySet();
        int depth = typedXmlPullParser.getDepth();
        int i2 = 3;
        while (true) {
            int next = typedXmlPullParser.next();
            str = readStringAttribute4;
            if (next == 1) {
                arraySet = arraySet5;
                arrayMap = arrayMap7;
                sessionParams = sessionParams4;
                arrayMap2 = arrayMap6;
                installationFileArr = null;
            } else if (next == 3 && typedXmlPullParser.getDepth() <= depth) {
                arraySet = arraySet5;
                arrayMap = arrayMap7;
                sessionParams = sessionParams4;
                arrayMap2 = arrayMap6;
                installationFileArr = null;
            } else if (next == 3 || next == 4) {
                arrayMap6 = arrayMap6;
                readStringAttribute4 = str;
                depth = depth;
                arraySet5 = arraySet5;
                arrayMap7 = arrayMap7;
                sessionParams4 = sessionParams4;
            } else {
                java.lang.String name = typedXmlPullParser.getName();
                switch (name.hashCode()) {
                    case -1558680102:
                        if (name.equals(TAG_CHILD_SESSION)) {
                            c = 5;
                            break;
                        }
                        c = 65535;
                        break;
                    case -1361644609:
                        if (name.equals(TAG_SESSION_CHECKSUM_SIGNATURE)) {
                            c = '\b';
                            break;
                        }
                        c = 65535;
                        break;
                    case -606495946:
                        if (name.equals(TAG_GRANTED_RUNTIME_PERMISSION)) {
                            c = 0;
                            break;
                        }
                        c = 65535;
                        break;
                    case -43804892:
                        if (name.equals(TAG_PRE_VERIFIED_DOMAINS)) {
                            c = '\t';
                            break;
                        }
                        c = 65535;
                        break;
                    case -22892238:
                        if (name.equals(TAG_SESSION_FILE)) {
                            c = 6;
                            break;
                        }
                        c = 65535;
                        break;
                    case 158177050:
                        if (name.equals(TAG_WHITELISTED_RESTRICTED_PERMISSION)) {
                            c = 3;
                            break;
                        }
                        c = 65535;
                        break;
                    case 641551609:
                        if (name.equals(TAG_SESSION_CHECKSUM)) {
                            c = 7;
                            break;
                        }
                        c = 65535;
                        break;
                    case 1529564053:
                        if (name.equals(TAG_AUTO_REVOKE_PERMISSIONS_MODE)) {
                            c = 4;
                            break;
                        }
                        c = 65535;
                        break;
                    case 1620305696:
                        if (name.equals(TAG_GRANT_PERMISSION)) {
                            c = 1;
                            break;
                        }
                        c = 65535;
                        break;
                    case 1658008624:
                        if (name.equals(TAG_DENY_PERMISSION)) {
                            c = 2;
                            break;
                        }
                        c = 65535;
                        break;
                    default:
                        c = 65535;
                        break;
                }
                int i3 = depth;
                switch (c) {
                    case 0:
                        arraySet2 = arraySet5;
                        arrayMap4 = arrayMap7;
                        sessionParams3 = sessionParams4;
                        arrayMap5 = arrayMap6;
                        arrayList.add(com.android.internal.util.XmlUtils.readStringAttribute(typedXmlPullParser, "name"));
                        break;
                    case 1:
                        arraySet2 = arraySet5;
                        arrayMap4 = arrayMap7;
                        sessionParams3 = sessionParams4;
                        arrayMap5 = arrayMap6;
                        arraySet3.add(com.android.internal.util.XmlUtils.readStringAttribute(typedXmlPullParser, "name"));
                        break;
                    case 2:
                        arraySet2 = arraySet5;
                        arrayMap4 = arrayMap7;
                        sessionParams3 = sessionParams4;
                        arrayMap5 = arrayMap6;
                        arraySet4.add(com.android.internal.util.XmlUtils.readStringAttribute(typedXmlPullParser, "name"));
                        break;
                    case 3:
                        arraySet2 = arraySet5;
                        arrayMap4 = arrayMap7;
                        sessionParams3 = sessionParams4;
                        arrayMap5 = arrayMap6;
                        arrayList2.add(com.android.internal.util.XmlUtils.readStringAttribute(typedXmlPullParser, "name"));
                        break;
                    case 4:
                        arraySet2 = arraySet5;
                        arrayMap4 = arrayMap7;
                        sessionParams3 = sessionParams4;
                        arrayMap5 = arrayMap6;
                        i2 = typedXmlPullParser.getAttributeInt((java.lang.String) null, "mode");
                        break;
                    case 5:
                        arraySet2 = arraySet5;
                        arrayMap4 = arrayMap7;
                        sessionParams3 = sessionParams4;
                        arrayMap5 = arrayMap6;
                        intArray.add(typedXmlPullParser.getAttributeInt((java.lang.String) null, ATTR_SESSION_ID, -1));
                        break;
                    case 6:
                        arraySet2 = arraySet5;
                        arrayMap4 = arrayMap7;
                        arrayMap5 = arrayMap6;
                        sessionParams3 = sessionParams4;
                        arrayList3.add(new android.content.pm.InstallationFile(typedXmlPullParser.getAttributeInt((java.lang.String) null, ATTR_LOCATION, 0), com.android.internal.util.XmlUtils.readStringAttribute(typedXmlPullParser, "name"), typedXmlPullParser.getAttributeLong((java.lang.String) null, ATTR_LENGTH_BYTES, -1L), com.android.internal.util.XmlUtils.readByteArrayAttribute(typedXmlPullParser, ATTR_METADATA), com.android.internal.util.XmlUtils.readByteArrayAttribute(typedXmlPullParser, ATTR_SIGNATURE)));
                        break;
                    case 7:
                        java.lang.String readStringAttribute9 = com.android.internal.util.XmlUtils.readStringAttribute(typedXmlPullParser, "name");
                        arraySet2 = arraySet5;
                        arrayMap4 = arrayMap7;
                        android.content.pm.Checksum checksum = new android.content.pm.Checksum(typedXmlPullParser.getAttributeInt((java.lang.String) null, ATTR_CHECKSUM_KIND, 0), com.android.internal.util.XmlUtils.readByteArrayAttribute(typedXmlPullParser, ATTR_CHECKSUM_VALUE));
                        java.util.List list = (java.util.List) arrayMap6.get(readStringAttribute9);
                        if (list == null) {
                            list = new java.util.ArrayList();
                            arrayMap6.put(readStringAttribute9, list);
                        }
                        list.add(checksum);
                        sessionParams3 = sessionParams4;
                        arrayMap5 = arrayMap6;
                        break;
                    case '\b':
                        arrayMap7.put(com.android.internal.util.XmlUtils.readStringAttribute(typedXmlPullParser, "name"), com.android.internal.util.XmlUtils.readByteArrayAttribute(typedXmlPullParser, ATTR_SIGNATURE));
                        arraySet2 = arraySet5;
                        arrayMap4 = arrayMap7;
                        sessionParams3 = sessionParams4;
                        arrayMap5 = arrayMap6;
                        break;
                    case '\t':
                        arraySet5.add(com.android.internal.util.XmlUtils.readStringAttribute(typedXmlPullParser, "domain"));
                        arraySet2 = arraySet5;
                        arrayMap4 = arrayMap7;
                        sessionParams3 = sessionParams4;
                        arrayMap5 = arrayMap6;
                        break;
                    default:
                        arraySet2 = arraySet5;
                        arrayMap4 = arrayMap7;
                        sessionParams3 = sessionParams4;
                        arrayMap5 = arrayMap6;
                        break;
                }
                arrayMap6 = arrayMap5;
                readStringAttribute4 = str;
                depth = i3;
                arraySet5 = arraySet2;
                arrayMap7 = arrayMap4;
                sessionParams4 = sessionParams3;
            }
        }
        if (arrayList.size() > 0) {
            sessionParams2 = sessionParams;
            sessionParams2.setPermissionStates(arrayList, java.util.Collections.emptyList());
        } else {
            sessionParams2 = sessionParams;
            sessionParams2.setPermissionStates(arraySet3, arraySet4);
        }
        if (arrayList2.size() > 0) {
            sessionParams2.whitelistedRestrictedPermissions = arrayList2;
        }
        sessionParams2.autoRevokePermissionsMode = i2;
        if (intArray.size() > 0) {
            int[] iArr2 = new int[intArray.size()];
            int size = intArray.size();
            for (int i4 = 0; i4 < size; i4++) {
                iArr2[i4] = intArray.get(i4);
            }
            iArr = iArr2;
        } else {
            iArr = EMPTY_CHILD_SESSION_ARRAY;
        }
        if (arrayList3.isEmpty()) {
            installationFileArr2 = installationFileArr;
        } else {
            installationFileArr2 = (android.content.pm.InstallationFile[]) arrayList3.toArray(EMPTY_INSTALLATION_FILE_ARRAY);
        }
        if (arrayMap2.isEmpty()) {
            arrayMap3 = installationFileArr;
        } else {
            android.util.ArrayMap arrayMap8 = new android.util.ArrayMap(arrayMap2.size());
            int size2 = arrayMap2.size();
            for (int i5 = 0; i5 < size2; i5++) {
                android.util.ArrayMap arrayMap9 = arrayMap2;
                java.lang.String str2 = (java.lang.String) arrayMap9.keyAt(i5);
                java.util.List list2 = (java.util.List) arrayMap9.valueAt(i5);
                arrayMap8.put(str2, new com.android.server.pm.PackageInstallerSession.PerFileChecksum((android.content.pm.Checksum[]) list2.toArray(new android.content.pm.Checksum[list2.size()]), (byte[]) arrayMap.get(str2)));
            }
            arrayMap3 = arrayMap8;
        }
        return new com.android.server.pm.PackageInstallerSession(internalCallback, context, packageManagerService, packageSessionProvider, silentUpdatePolicy, looper, stagingManager, attributeInt, attributeInt2, i, com.android.server.pm.InstallSource.create(str, readStringAttribute5, readStringAttribute, attributeInt3, readStringAttribute2, readStringAttribute3, sessionParams2.packageSource), sessionParams2, attributeLong, attributeLong2, file2, readStringAttribute7, installationFileArr2, arrayMap3, attributeBoolean, attributeBoolean2, attributeBoolean3, attributeBoolean4, iArr, attributeInt5, attributeBoolean5, attributeBoolean6, attributeBoolean7, attributeInt6, readStringAttribute8, arraySet.isEmpty() ? installationFileArr : new android.content.pm.verify.domain.DomainSet(arraySet));
    }
}
