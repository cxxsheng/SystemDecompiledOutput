package android.content.pm;

/* loaded from: classes.dex */
public class PackageInstaller {

    @android.annotation.SystemApi
    public static final java.lang.String ACTION_CONFIRM_INSTALL = "android.content.pm.action.CONFIRM_INSTALL";

    @android.annotation.SystemApi
    public static final java.lang.String ACTION_CONFIRM_PRE_APPROVAL = "android.content.pm.action.CONFIRM_PRE_APPROVAL";
    public static final java.lang.String ACTION_SESSION_COMMITTED = "android.content.pm.action.SESSION_COMMITTED";
    public static final java.lang.String ACTION_SESSION_DETAILS = "android.content.pm.action.SESSION_DETAILS";
    public static final java.lang.String ACTION_SESSION_UPDATED = "android.content.pm.action.SESSION_UPDATED";
    private static final java.lang.String ACTION_WAIT_INSTALL_CONSTRAINTS = "android.content.pm.action.WAIT_INSTALL_CONSTRAINTS";

    @android.annotation.SystemApi
    public static final int DATA_LOADER_TYPE_INCREMENTAL = 2;

    @android.annotation.SystemApi
    public static final int DATA_LOADER_TYPE_NONE = 0;

    @android.annotation.SystemApi
    public static final int DATA_LOADER_TYPE_STREAMING = 1;
    private static final int DEFAULT_CHECKSUMS = 127;
    public static final boolean ENABLE_REVOCABLE_FD = android.os.SystemProperties.getBoolean("fw.revocable_fd", false);

    @android.annotation.SystemApi
    public static final java.lang.String EXTRA_CALLBACK = "android.content.pm.extra.CALLBACK";

    @android.annotation.SystemApi
    public static final java.lang.String EXTRA_DATA_LOADER_TYPE = "android.content.pm.extra.DATA_LOADER_TYPE";

    @android.annotation.SystemApi
    public static final java.lang.String EXTRA_DELETE_FLAGS = "android.content.pm.extra.DELETE_FLAGS";
    public static final java.lang.String EXTRA_INSTALL_CONSTRAINTS = "android.content.pm.extra.INSTALL_CONSTRAINTS";
    public static final java.lang.String EXTRA_INSTALL_CONSTRAINTS_RESULT = "android.content.pm.extra.INSTALL_CONSTRAINTS_RESULT";
    public static final java.lang.String EXTRA_LEGACY_BUNDLE = "android.content.pm.extra.LEGACY_BUNDLE";

    @android.annotation.SystemApi
    public static final java.lang.String EXTRA_LEGACY_STATUS = "android.content.pm.extra.LEGACY_STATUS";
    public static final java.lang.String EXTRA_OTHER_PACKAGE_NAME = "android.content.pm.extra.OTHER_PACKAGE_NAME";
    public static final java.lang.String EXTRA_PACKAGE_NAME = "android.content.pm.extra.PACKAGE_NAME";

    @java.lang.Deprecated
    public static final java.lang.String EXTRA_PACKAGE_NAMES = "android.content.pm.extra.PACKAGE_NAMES";
    public static final java.lang.String EXTRA_PRE_APPROVAL = "android.content.pm.extra.PRE_APPROVAL";

    @android.annotation.SystemApi
    @java.lang.Deprecated
    public static final java.lang.String EXTRA_RESOLVED_BASE_PATH = "android.content.pm.extra.RESOLVED_BASE_PATH";
    public static final java.lang.String EXTRA_SESSION = "android.content.pm.extra.SESSION";
    public static final java.lang.String EXTRA_SESSION_ID = "android.content.pm.extra.SESSION_ID";
    public static final java.lang.String EXTRA_STATUS = "android.content.pm.extra.STATUS";
    public static final java.lang.String EXTRA_STATUS_MESSAGE = "android.content.pm.extra.STATUS_MESSAGE";
    public static final java.lang.String EXTRA_STORAGE_PATH = "android.content.pm.extra.STORAGE_PATH";
    public static final java.lang.String EXTRA_UNARCHIVE_ALL_USERS = "android.content.pm.extra.UNARCHIVE_ALL_USERS";
    public static final java.lang.String EXTRA_UNARCHIVE_ID = "android.content.pm.extra.UNARCHIVE_ID";
    public static final java.lang.String EXTRA_UNARCHIVE_PACKAGE_NAME = "android.content.pm.extra.UNARCHIVE_PACKAGE_NAME";
    public static final java.lang.String EXTRA_UNARCHIVE_STATUS = "android.content.pm.extra.UNARCHIVE_STATUS";
    public static final java.lang.String EXTRA_WARNINGS = "android.content.pm.extra.WARNINGS";

    @android.annotation.SystemApi
    public static final int LOCATION_DATA_APP = 0;

    @android.annotation.SystemApi
    public static final int LOCATION_MEDIA_DATA = 2;

    @android.annotation.SystemApi
    public static final int LOCATION_MEDIA_OBB = 1;
    public static final int PACKAGE_SOURCE_DOWNLOADED_FILE = 4;
    public static final int PACKAGE_SOURCE_LOCAL_FILE = 3;
    public static final int PACKAGE_SOURCE_OTHER = 1;
    public static final int PACKAGE_SOURCE_STORE = 2;
    public static final int PACKAGE_SOURCE_UNSPECIFIED = 0;

    @android.annotation.SystemApi
    public static final int REASON_CONFIRM_PACKAGE_CHANGE = 0;

    @android.annotation.SystemApi
    public static final int REASON_OWNERSHIP_CHANGED = 1;

    @android.annotation.SystemApi
    public static final int REASON_REMIND_OWNERSHIP = 2;
    public static final int STATUS_FAILURE = 1;
    public static final int STATUS_FAILURE_ABORTED = 3;
    public static final int STATUS_FAILURE_BLOCKED = 2;
    public static final int STATUS_FAILURE_CONFLICT = 5;
    public static final int STATUS_FAILURE_INCOMPATIBLE = 7;
    public static final int STATUS_FAILURE_INVALID = 4;
    public static final int STATUS_FAILURE_STORAGE = 6;
    public static final int STATUS_FAILURE_TIMEOUT = 8;
    public static final int STATUS_PENDING_STREAMING = -2;
    public static final int STATUS_PENDING_USER_ACTION = -1;
    public static final int STATUS_SUCCESS = 0;
    private static final java.lang.String TAG = "PackageInstaller";
    public static final int UNARCHIVAL_ERROR_INSTALLER_DISABLED = 4;
    public static final int UNARCHIVAL_ERROR_INSTALLER_UNINSTALLED = 5;
    public static final int UNARCHIVAL_ERROR_INSUFFICIENT_STORAGE = 2;
    public static final int UNARCHIVAL_ERROR_NO_CONNECTIVITY = 3;
    public static final int UNARCHIVAL_ERROR_USER_ACTION_NEEDED = 1;
    public static final int UNARCHIVAL_GENERIC_ERROR = 100;
    public static final int UNARCHIVAL_OK = 0;
    public static final int UNARCHIVAL_STATUS_UNSET = -1;
    private final java.lang.String mAttributionTag;
    private final java.util.ArrayList<android.content.pm.PackageInstaller.SessionCallbackDelegate> mDelegates = new java.util.ArrayList<>();
    private final android.content.pm.IPackageInstaller mInstaller;
    private final java.lang.String mInstallerPackageName;
    private final int mUserId;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface FileLocation {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    @interface PackageSourceType {
    }

    public static abstract class SessionCallback {
        public abstract void onActiveChanged(int i, boolean z);

        public abstract void onBadgingChanged(int i);

        public abstract void onCreated(int i);

        public abstract void onFinished(int i, boolean z);

        public abstract void onProgressChanged(int i, float f);
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface UnarchivalStatus {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface UserActionReason {
    }

    public PackageInstaller(android.content.pm.IPackageInstaller iPackageInstaller, java.lang.String str, java.lang.String str2, int i) {
        java.util.Objects.requireNonNull(iPackageInstaller, "installer cannot be null");
        this.mInstaller = iPackageInstaller;
        this.mInstallerPackageName = str;
        this.mAttributionTag = str2;
        this.mUserId = i;
    }

    public int createSession(android.content.pm.PackageInstaller.SessionParams sessionParams) throws java.io.IOException {
        try {
            return this.mInstaller.createSession(sessionParams, this.mInstallerPackageName, this.mAttributionTag, this.mUserId);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        } catch (java.lang.RuntimeException e2) {
            android.util.ExceptionUtils.maybeUnwrapIOException(e2);
            throw e2;
        }
    }

    public android.content.pm.PackageInstaller.Session openSession(int i) throws java.io.IOException {
        try {
            try {
                return new android.content.pm.PackageInstaller.Session(this.mInstaller.openSession(i));
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        } catch (java.lang.RuntimeException e2) {
            android.util.ExceptionUtils.maybeUnwrapIOException(e2);
            throw e2;
        }
    }

    public void updateSessionAppIcon(int i, android.graphics.Bitmap bitmap) {
        try {
            this.mInstaller.updateSessionAppIcon(i, bitmap);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void updateSessionAppLabel(int i, java.lang.CharSequence charSequence) {
        java.lang.String charSequence2;
        if (charSequence == null) {
            charSequence2 = null;
        } else {
            try {
                charSequence2 = charSequence.toString();
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        this.mInstaller.updateSessionAppLabel(i, charSequence2);
    }

    public void abandonSession(int i) {
        try {
            this.mInstaller.abandonSession(i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public android.content.pm.PackageInstaller.SessionInfo getSessionInfo(int i) {
        try {
            return this.mInstaller.getSessionInfo(i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public java.util.List<android.content.pm.PackageInstaller.SessionInfo> getAllSessions() {
        try {
            return this.mInstaller.getAllSessions(this.mUserId).getList();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public java.util.List<android.content.pm.PackageInstaller.SessionInfo> getMySessions() {
        try {
            return this.mInstaller.getMySessions(this.mInstallerPackageName, this.mUserId).getList();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public java.util.List<android.content.pm.PackageInstaller.SessionInfo> getStagedSessions() {
        try {
            return this.mInstaller.getStagedSessions().getList();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @java.lang.Deprecated
    public android.content.pm.PackageInstaller.SessionInfo getActiveStagedSession() {
        java.util.List<android.content.pm.PackageInstaller.SessionInfo> activeStagedSessions = getActiveStagedSessions();
        if (activeStagedSessions.isEmpty()) {
            return null;
        }
        return activeStagedSessions.get(0);
    }

    public java.util.List<android.content.pm.PackageInstaller.SessionInfo> getActiveStagedSessions() {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        java.util.List<android.content.pm.PackageInstaller.SessionInfo> stagedSessions = getStagedSessions();
        for (int i = 0; i < stagedSessions.size(); i++) {
            android.content.pm.PackageInstaller.SessionInfo sessionInfo = stagedSessions.get(i);
            if (sessionInfo.isStagedSessionActive()) {
                arrayList.add(sessionInfo);
            }
        }
        return arrayList;
    }

    public void uninstall(java.lang.String str, android.content.IntentSender intentSender) {
        uninstall(str, 0, intentSender);
    }

    public void uninstall(java.lang.String str, int i, android.content.IntentSender intentSender) {
        uninstall(new android.content.pm.VersionedPackage(str, -1), i, intentSender);
    }

    public void uninstall(android.content.pm.VersionedPackage versionedPackage, android.content.IntentSender intentSender) {
        uninstall(versionedPackage, 0, intentSender);
    }

    public void uninstall(android.content.pm.VersionedPackage versionedPackage, int i, android.content.IntentSender intentSender) {
        java.util.Objects.requireNonNull(versionedPackage, "versionedPackage cannot be null");
        try {
            this.mInstaller.uninstall(versionedPackage, this.mInstallerPackageName, i, intentSender, this.mUserId);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void installExistingPackage(java.lang.String str, int i, android.content.IntentSender intentSender) {
        java.util.Objects.requireNonNull(str, "packageName cannot be null");
        try {
            this.mInstaller.installExistingPackage(str, 4194304, i, intentSender, this.mUserId, null);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void uninstallExistingPackage(java.lang.String str, android.content.IntentSender intentSender) {
        java.util.Objects.requireNonNull(str, "packageName cannot be null");
        try {
            this.mInstaller.uninstallExistingPackage(new android.content.pm.VersionedPackage(str, -1), this.mInstallerPackageName, intentSender, this.mUserId);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void installPackageArchived(android.content.pm.ArchivedPackageInfo archivedPackageInfo, android.content.pm.PackageInstaller.SessionParams sessionParams, android.content.IntentSender intentSender) {
        java.util.Objects.requireNonNull(archivedPackageInfo, "archivedPackageInfo cannot be null");
        java.util.Objects.requireNonNull(sessionParams, "sessionParams cannot be null");
        java.util.Objects.requireNonNull(intentSender, "statusReceiver cannot be null");
        try {
            this.mInstaller.installPackageArchived(archivedPackageInfo.getParcel(), sessionParams, intentSender, this.mInstallerPackageName, new android.os.UserHandle(this.mUserId));
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public void setPermissionsResult(int i, boolean z) {
        try {
            this.mInstaller.setPermissionsResult(i, z);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void checkInstallConstraints(java.util.List<java.lang.String> list, android.content.pm.PackageInstaller.InstallConstraints installConstraints, final java.util.concurrent.Executor executor, final java.util.function.Consumer<android.content.pm.PackageInstaller.InstallConstraintsResult> consumer) {
        java.util.Objects.requireNonNull(executor);
        java.util.Objects.requireNonNull(consumer);
        try {
            this.mInstaller.checkInstallConstraints(this.mInstallerPackageName, list, installConstraints, new android.os.RemoteCallback(new android.os.RemoteCallback.OnResultListener() { // from class: android.content.pm.PackageInstaller$$ExternalSyntheticLambda1
                @Override // android.os.RemoteCallback.OnResultListener
                public final void onResult(android.os.Bundle bundle) {
                    executor.execute(new java.lang.Runnable() { // from class: android.content.pm.PackageInstaller$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            r1.accept((android.content.pm.PackageInstaller.InstallConstraintsResult) bundle.getParcelable("result", android.content.pm.PackageInstaller.InstallConstraintsResult.class));
                        }
                    });
                }
            }));
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void waitForInstallConstraints(java.util.List<java.lang.String> list, android.content.pm.PackageInstaller.InstallConstraints installConstraints, android.content.IntentSender intentSender, long j) {
        try {
            this.mInstaller.waitForInstallConstraints(this.mInstallerPackageName, list, installConstraints, intentSender, j);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void commitSessionAfterInstallConstraintsAreMet(int i, android.content.IntentSender intentSender, android.content.pm.PackageInstaller.InstallConstraints installConstraints, long j) {
        try {
            android.content.pm.IPackageInstallerSession openSession = this.mInstaller.openSession(i);
            openSession.seal();
            waitForInstallConstraints(openSession.fetchPackageNames(), installConstraints, new android.content.pm.PackageInstaller.LocalIntentSender(android.app.ActivityThread.currentApplication(), i, openSession, intentSender).getIntentSender(), j);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    private static final class LocalIntentSender extends android.content.BroadcastReceiver {
        private final android.content.Context mContext;
        private final android.content.pm.IPackageInstallerSession mSession;
        private final int mSessionId;
        private final android.content.IntentSender mStatusReceiver;

        LocalIntentSender(android.content.Context context, int i, android.content.pm.IPackageInstallerSession iPackageInstallerSession, android.content.IntentSender intentSender) {
            this.mContext = context;
            this.mSessionId = i;
            this.mSession = iPackageInstallerSession;
            this.mStatusReceiver = intentSender;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public android.content.IntentSender getIntentSender() {
            android.content.Intent intent = new android.content.Intent(android.content.pm.PackageInstaller.ACTION_WAIT_INSTALL_CONSTRAINTS).setPackage(this.mContext.getPackageName());
            this.mContext.registerReceiver(this, new android.content.IntentFilter(android.content.pm.PackageInstaller.ACTION_WAIT_INSTALL_CONSTRAINTS), 2);
            return android.app.PendingIntent.getBroadcast(this.mContext, 0, intent, 33554432).getIntentSender();
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(android.content.Context context, android.content.Intent intent) {
            try {
                if (((android.content.pm.PackageInstaller.InstallConstraintsResult) intent.getParcelableExtra(android.content.pm.PackageInstaller.EXTRA_INSTALL_CONSTRAINTS_RESULT, android.content.pm.PackageInstaller.InstallConstraintsResult.class)).areAllConstraintsSatisfied()) {
                    this.mSession.commit(this.mStatusReceiver, false);
                } else {
                    android.content.Intent intent2 = new android.content.Intent();
                    intent2.putExtra(android.content.pm.PackageInstaller.EXTRA_SESSION_ID, this.mSessionId);
                    intent2.putExtra(android.content.pm.PackageInstaller.EXTRA_STATUS, 8);
                    intent2.putExtra(android.content.pm.PackageInstaller.EXTRA_STATUS_MESSAGE, "Install constraints not satisfied within timeout");
                    this.mStatusReceiver.sendIntent(android.app.ActivityThread.currentApplication(), 0, intent2, null, null);
                }
            } catch (java.lang.Exception e) {
            } catch (java.lang.Throwable th) {
                unregisterReceiver();
                throw th;
            }
            unregisterReceiver();
        }

        private void unregisterReceiver() {
            this.mContext.unregisterReceiver(this);
        }
    }

    static class SessionCallbackDelegate extends android.content.pm.IPackageInstallerCallback.Stub {
        private static final int MSG_SESSION_ACTIVE_CHANGED = 3;
        private static final int MSG_SESSION_BADGING_CHANGED = 2;
        private static final int MSG_SESSION_CREATED = 1;
        private static final int MSG_SESSION_FINISHED = 5;
        private static final int MSG_SESSION_PROGRESS_CHANGED = 4;
        final android.content.pm.PackageInstaller.SessionCallback mCallback;
        final java.util.concurrent.Executor mExecutor;

        SessionCallbackDelegate(android.content.pm.PackageInstaller.SessionCallback sessionCallback, java.util.concurrent.Executor executor) {
            this.mCallback = sessionCallback;
            this.mExecutor = executor;
        }

        @Override // android.content.pm.IPackageInstallerCallback
        public void onSessionCreated(int i) {
            this.mExecutor.execute(com.android.internal.util.function.pooled.PooledLambda.obtainRunnable(new java.util.function.BiConsumer() { // from class: android.content.pm.PackageInstaller$SessionCallbackDelegate$$ExternalSyntheticLambda4
                @Override // java.util.function.BiConsumer
                public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                    ((android.content.pm.PackageInstaller.SessionCallback) obj).onCreated(((java.lang.Integer) obj2).intValue());
                }
            }, this.mCallback, java.lang.Integer.valueOf(i)).recycleOnUse());
        }

        @Override // android.content.pm.IPackageInstallerCallback
        public void onSessionBadgingChanged(int i) {
            this.mExecutor.execute(com.android.internal.util.function.pooled.PooledLambda.obtainRunnable(new java.util.function.BiConsumer() { // from class: android.content.pm.PackageInstaller$SessionCallbackDelegate$$ExternalSyntheticLambda2
                @Override // java.util.function.BiConsumer
                public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                    ((android.content.pm.PackageInstaller.SessionCallback) obj).onBadgingChanged(((java.lang.Integer) obj2).intValue());
                }
            }, this.mCallback, java.lang.Integer.valueOf(i)).recycleOnUse());
        }

        @Override // android.content.pm.IPackageInstallerCallback
        public void onSessionActiveChanged(int i, boolean z) {
            this.mExecutor.execute(com.android.internal.util.function.pooled.PooledLambda.obtainRunnable(new com.android.internal.util.function.TriConsumer() { // from class: android.content.pm.PackageInstaller$SessionCallbackDelegate$$ExternalSyntheticLambda0
                @Override // com.android.internal.util.function.TriConsumer
                public final void accept(java.lang.Object obj, java.lang.Object obj2, java.lang.Object obj3) {
                    ((android.content.pm.PackageInstaller.SessionCallback) obj).onActiveChanged(((java.lang.Integer) obj2).intValue(), ((java.lang.Boolean) obj3).booleanValue());
                }
            }, this.mCallback, java.lang.Integer.valueOf(i), java.lang.Boolean.valueOf(z)).recycleOnUse());
        }

        @Override // android.content.pm.IPackageInstallerCallback
        public void onSessionProgressChanged(int i, float f) {
            this.mExecutor.execute(com.android.internal.util.function.pooled.PooledLambda.obtainRunnable(new com.android.internal.util.function.TriConsumer() { // from class: android.content.pm.PackageInstaller$SessionCallbackDelegate$$ExternalSyntheticLambda1
                @Override // com.android.internal.util.function.TriConsumer
                public final void accept(java.lang.Object obj, java.lang.Object obj2, java.lang.Object obj3) {
                    ((android.content.pm.PackageInstaller.SessionCallback) obj).onProgressChanged(((java.lang.Integer) obj2).intValue(), ((java.lang.Float) obj3).floatValue());
                }
            }, this.mCallback, java.lang.Integer.valueOf(i), java.lang.Float.valueOf(f)).recycleOnUse());
        }

        @Override // android.content.pm.IPackageInstallerCallback
        public void onSessionFinished(int i, boolean z) {
            this.mExecutor.execute(com.android.internal.util.function.pooled.PooledLambda.obtainRunnable(new com.android.internal.util.function.TriConsumer() { // from class: android.content.pm.PackageInstaller$SessionCallbackDelegate$$ExternalSyntheticLambda3
                @Override // com.android.internal.util.function.TriConsumer
                public final void accept(java.lang.Object obj, java.lang.Object obj2, java.lang.Object obj3) {
                    ((android.content.pm.PackageInstaller.SessionCallback) obj).onFinished(((java.lang.Integer) obj2).intValue(), ((java.lang.Boolean) obj3).booleanValue());
                }
            }, this.mCallback, java.lang.Integer.valueOf(i), java.lang.Boolean.valueOf(z)).recycleOnUse());
        }
    }

    @java.lang.Deprecated
    public void addSessionCallback(android.content.pm.PackageInstaller.SessionCallback sessionCallback) {
        registerSessionCallback(sessionCallback);
    }

    public void registerSessionCallback(android.content.pm.PackageInstaller.SessionCallback sessionCallback) {
        registerSessionCallback(sessionCallback, new android.os.Handler());
    }

    @java.lang.Deprecated
    public void addSessionCallback(android.content.pm.PackageInstaller.SessionCallback sessionCallback, android.os.Handler handler) {
        registerSessionCallback(sessionCallback, handler);
    }

    public void registerSessionCallback(android.content.pm.PackageInstaller.SessionCallback sessionCallback, android.os.Handler handler) {
        synchronized (this.mDelegates) {
            android.content.pm.PackageInstaller.SessionCallbackDelegate sessionCallbackDelegate = new android.content.pm.PackageInstaller.SessionCallbackDelegate(sessionCallback, new android.os.HandlerExecutor(handler));
            try {
                this.mInstaller.registerCallback(sessionCallbackDelegate, this.mUserId);
                this.mDelegates.add(sessionCallbackDelegate);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    @java.lang.Deprecated
    public void removeSessionCallback(android.content.pm.PackageInstaller.SessionCallback sessionCallback) {
        unregisterSessionCallback(sessionCallback);
    }

    public void unregisterSessionCallback(android.content.pm.PackageInstaller.SessionCallback sessionCallback) {
        synchronized (this.mDelegates) {
            java.util.Iterator<android.content.pm.PackageInstaller.SessionCallbackDelegate> it = this.mDelegates.iterator();
            while (it.hasNext()) {
                android.content.pm.PackageInstaller.SessionCallbackDelegate next = it.next();
                if (next.mCallback == sessionCallback) {
                    try {
                        this.mInstaller.unregisterCallback(next);
                        it.remove();
                    } catch (android.os.RemoteException e) {
                        throw e.rethrowFromSystemServer();
                    }
                }
            }
        }
    }

    public static class Session implements java.io.Closeable {
        protected final android.content.pm.IPackageInstallerSession mSession;

        public Session(android.content.pm.IPackageInstallerSession iPackageInstallerSession) {
            this.mSession = iPackageInstallerSession;
        }

        @java.lang.Deprecated
        public void setProgress(float f) {
            setStagingProgress(f);
        }

        public void setStagingProgress(float f) {
            try {
                this.mSession.setClientProgress(f);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        public void addProgress(float f) {
            try {
                this.mSession.addClientProgress(f);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        public java.io.OutputStream openWrite(java.lang.String str, long j, long j2) throws java.io.IOException {
            try {
                if (android.content.pm.PackageInstaller.ENABLE_REVOCABLE_FD) {
                    return new android.os.ParcelFileDescriptor.AutoCloseOutputStream(this.mSession.openWrite(str, j, j2));
                }
                return new android.os.FileBridge.FileBridgeOutputStream(this.mSession.openWrite(str, j, j2));
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            } catch (java.lang.RuntimeException e2) {
                android.util.ExceptionUtils.maybeUnwrapIOException(e2);
                throw e2;
            }
        }

        public void write(java.lang.String str, long j, long j2, android.os.ParcelFileDescriptor parcelFileDescriptor) throws java.io.IOException {
            try {
                this.mSession.write(str, j, j2, parcelFileDescriptor);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            } catch (java.lang.RuntimeException e2) {
                android.util.ExceptionUtils.maybeUnwrapIOException(e2);
                throw e2;
            }
        }

        public void stageViaHardLink(java.lang.String str) throws java.io.IOException {
            try {
                this.mSession.stageViaHardLink(str);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            } catch (java.lang.RuntimeException e2) {
                android.util.ExceptionUtils.maybeUnwrapIOException(e2);
                throw e2;
            }
        }

        public void fsync(java.io.OutputStream outputStream) throws java.io.IOException {
            if (android.content.pm.PackageInstaller.ENABLE_REVOCABLE_FD) {
                if (outputStream instanceof android.os.ParcelFileDescriptor.AutoCloseOutputStream) {
                    try {
                        android.system.Os.fsync(((android.os.ParcelFileDescriptor.AutoCloseOutputStream) outputStream).getFD());
                        return;
                    } catch (android.system.ErrnoException e) {
                        throw e.rethrowAsIOException();
                    }
                }
                throw new java.lang.IllegalArgumentException("Unrecognized stream");
            }
            if (outputStream instanceof android.os.FileBridge.FileBridgeOutputStream) {
                ((android.os.FileBridge.FileBridgeOutputStream) outputStream).fsync();
                return;
            }
            throw new java.lang.IllegalArgumentException("Unrecognized stream");
        }

        public java.lang.String[] getNames() throws java.io.IOException {
            try {
                return this.mSession.getNames();
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            } catch (java.lang.RuntimeException e2) {
                android.util.ExceptionUtils.maybeUnwrapIOException(e2);
                throw e2;
            }
        }

        public java.io.InputStream openRead(java.lang.String str) throws java.io.IOException {
            try {
                return new android.os.ParcelFileDescriptor.AutoCloseInputStream(this.mSession.openRead(str));
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            } catch (java.lang.RuntimeException e2) {
                android.util.ExceptionUtils.maybeUnwrapIOException(e2);
                throw e2;
            }
        }

        public void removeSplit(java.lang.String str) throws java.io.IOException {
            try {
                this.mSession.removeSplit(str);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            } catch (java.lang.RuntimeException e2) {
                android.util.ExceptionUtils.maybeUnwrapIOException(e2);
                throw e2;
            }
        }

        @android.annotation.SystemApi
        public android.content.pm.DataLoaderParams getDataLoaderParams() {
            try {
                android.content.pm.DataLoaderParamsParcel dataLoaderParams = this.mSession.getDataLoaderParams();
                if (dataLoaderParams == null) {
                    return null;
                }
                return new android.content.pm.DataLoaderParams(dataLoaderParams);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        @android.annotation.SystemApi
        public void addFile(int i, java.lang.String str, long j, byte[] bArr, byte[] bArr2) {
            try {
                this.mSession.addFile(i, str, j, bArr, bArr2);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        @android.annotation.SystemApi
        public void removeFile(int i, java.lang.String str) {
            try {
                this.mSession.removeFile(i, str);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        @java.lang.Deprecated
        public void setChecksums(java.lang.String str, java.util.List<android.content.pm.Checksum> list, byte[] bArr) throws java.io.IOException {
            java.util.Objects.requireNonNull(str);
            java.util.Objects.requireNonNull(list);
            try {
                this.mSession.setChecksums(str, (android.content.pm.Checksum[]) list.toArray(new android.content.pm.Checksum[list.size()]), bArr);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            } catch (java.lang.RuntimeException e2) {
                android.util.ExceptionUtils.maybeUnwrapIOException(e2);
                throw e2;
            }
        }

        private static java.util.List<byte[]> encodeCertificates(java.util.List<java.security.cert.Certificate> list) throws java.security.cert.CertificateEncodingException {
            if (list == null) {
                return null;
            }
            java.util.ArrayList arrayList = new java.util.ArrayList(list.size());
            for (java.security.cert.Certificate certificate : list) {
                if (!(certificate instanceof java.security.cert.X509Certificate)) {
                    throw new java.security.cert.CertificateEncodingException("Only X509 certificates supported.");
                }
                arrayList.add(certificate.getEncoded());
            }
            return arrayList;
        }

        public void requestChecksums(java.lang.String str, int i, java.util.List<java.security.cert.Certificate> list, java.util.concurrent.Executor executor, android.content.pm.PackageManager.OnChecksumsReadyListener onChecksumsReadyListener) throws java.security.cert.CertificateEncodingException, java.io.FileNotFoundException {
            java.util.Objects.requireNonNull(str);
            java.util.Objects.requireNonNull(list);
            java.util.Objects.requireNonNull(executor);
            java.util.Objects.requireNonNull(onChecksumsReadyListener);
            if (list == android.content.pm.PackageManager.TRUST_ALL) {
                list = null;
            } else if (list == android.content.pm.PackageManager.TRUST_NONE) {
                list = java.util.Collections.emptyList();
            } else if (list.isEmpty()) {
                throw new java.lang.IllegalArgumentException("trustedInstallers has to be one of TRUST_ALL/TRUST_NONE or a non-empty list of certificates.");
            }
            try {
                this.mSession.requestChecksums(str, 127, i, encodeCertificates(list), new android.content.pm.PackageInstaller.Session.AnonymousClass1(executor, onChecksumsReadyListener));
            } catch (android.os.ParcelableException e) {
                e.maybeRethrow(java.io.FileNotFoundException.class);
                throw new java.lang.RuntimeException(e);
            } catch (android.os.RemoteException e2) {
                throw e2.rethrowFromSystemServer();
            }
        }

        /* renamed from: android.content.pm.PackageInstaller$Session$1, reason: invalid class name */
        class AnonymousClass1 extends android.content.pm.IOnChecksumsReadyListener.Stub {
            final /* synthetic */ java.util.concurrent.Executor val$executor;
            final /* synthetic */ android.content.pm.PackageManager.OnChecksumsReadyListener val$onChecksumsReadyListener;

            AnonymousClass1(java.util.concurrent.Executor executor, android.content.pm.PackageManager.OnChecksumsReadyListener onChecksumsReadyListener) {
                this.val$executor = executor;
                this.val$onChecksumsReadyListener = onChecksumsReadyListener;
            }

            @Override // android.content.pm.IOnChecksumsReadyListener
            public void onChecksumsReady(final java.util.List<android.content.pm.ApkChecksum> list) throws android.os.RemoteException {
                java.util.concurrent.Executor executor = this.val$executor;
                final android.content.pm.PackageManager.OnChecksumsReadyListener onChecksumsReadyListener = this.val$onChecksumsReadyListener;
                executor.execute(new java.lang.Runnable() { // from class: android.content.pm.PackageInstaller$Session$1$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.content.pm.PackageManager.OnChecksumsReadyListener.this.onChecksumsReady(list);
                    }
                });
            }
        }

        public void commit(android.content.IntentSender intentSender) {
            try {
                this.mSession.commit(intentSender, false);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        @android.annotation.SystemApi
        public void commitTransferred(android.content.IntentSender intentSender) {
            try {
                this.mSession.commit(intentSender, true);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        public void transfer(java.lang.String str) throws android.content.pm.PackageManager.NameNotFoundException {
            com.android.internal.util.Preconditions.checkArgument(!android.text.TextUtils.isEmpty(str));
            try {
                this.mSession.transfer(str);
            } catch (android.os.ParcelableException e) {
                e.maybeRethrow(android.content.pm.PackageManager.NameNotFoundException.class);
                throw new java.lang.RuntimeException(e);
            } catch (android.os.RemoteException e2) {
                throw e2.rethrowFromSystemServer();
            }
        }

        @Override // java.io.Closeable, java.lang.AutoCloseable
        public void close() {
            try {
                this.mSession.close();
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        public void abandon() {
            try {
                this.mSession.abandon();
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        public boolean isMultiPackage() {
            try {
                return this.mSession.isMultiPackage();
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        public boolean isStaged() {
            try {
                return this.mSession.isStaged();
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        public int getInstallFlags() {
            try {
                return this.mSession.getInstallFlags();
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        public int getParentSessionId() {
            try {
                return this.mSession.getParentSessionId();
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        public int[] getChildSessionIds() {
            try {
                return this.mSession.getChildSessionIds();
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        public void addChildSessionId(int i) {
            try {
                this.mSession.addChildSessionId(i);
            } catch (android.os.RemoteException e) {
                e.rethrowFromSystemServer();
            }
        }

        public void removeChildSessionId(int i) {
            try {
                this.mSession.removeChildSessionId(i);
            } catch (android.os.RemoteException e) {
                e.rethrowFromSystemServer();
            }
        }

        public android.os.PersistableBundle getAppMetadata() {
            android.os.PersistableBundle persistableBundle = null;
            try {
                android.os.ParcelFileDescriptor appMetadataFd = this.mSession.getAppMetadataFd();
                if (appMetadataFd != null) {
                    android.os.ParcelFileDescriptor.AutoCloseInputStream autoCloseInputStream = new android.os.ParcelFileDescriptor.AutoCloseInputStream(appMetadataFd);
                    try {
                        persistableBundle = android.os.PersistableBundle.readFromStream(autoCloseInputStream);
                        autoCloseInputStream.close();
                    } finally {
                    }
                }
            } catch (android.os.RemoteException e) {
                e.rethrowFromSystemServer();
            } catch (java.io.IOException e2) {
                throw new java.lang.RuntimeException(e2);
            }
            return persistableBundle != null ? persistableBundle : new android.os.PersistableBundle();
        }

        private java.io.OutputStream openWriteAppMetadata() throws java.io.IOException {
            try {
                if (android.content.pm.PackageInstaller.ENABLE_REVOCABLE_FD) {
                    return new android.os.ParcelFileDescriptor.AutoCloseOutputStream(this.mSession.openWriteAppMetadata());
                }
                return new android.os.FileBridge.FileBridgeOutputStream(this.mSession.openWriteAppMetadata());
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            } catch (java.lang.RuntimeException e2) {
                android.util.ExceptionUtils.maybeUnwrapIOException(e2);
                throw e2;
            }
        }

        public void setAppMetadata(android.os.PersistableBundle persistableBundle) throws java.io.IOException {
            if (persistableBundle == null || persistableBundle.isEmpty()) {
                try {
                    this.mSession.removeAppMetadata();
                    return;
                } catch (android.os.RemoteException e) {
                    throw e.rethrowFromSystemServer();
                }
            }
            java.util.Objects.requireNonNull(persistableBundle);
            java.io.OutputStream openWriteAppMetadata = openWriteAppMetadata();
            try {
                persistableBundle.writeToStream(openWriteAppMetadata);
                if (openWriteAppMetadata != null) {
                    openWriteAppMetadata.close();
                }
            } catch (java.lang.Throwable th) {
                if (openWriteAppMetadata != null) {
                    try {
                        openWriteAppMetadata.close();
                    } catch (java.lang.Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        }

        public void requestUserPreapproval(android.content.pm.PackageInstaller.PreapprovalDetails preapprovalDetails, android.content.IntentSender intentSender) {
            com.android.internal.util.Preconditions.checkArgument(preapprovalDetails != null, "preapprovalDetails cannot be null.");
            com.android.internal.util.Preconditions.checkArgument(intentSender != null, "statusReceiver cannot be null.");
            try {
                this.mSession.requestUserPreapproval(preapprovalDetails, intentSender);
            } catch (android.os.RemoteException e) {
                e.rethrowFromSystemServer();
            }
        }

        public boolean isApplicationEnabledSettingPersistent() {
            try {
                return this.mSession.isApplicationEnabledSettingPersistent();
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        public boolean isRequestUpdateOwnership() {
            try {
                return this.mSession.isRequestUpdateOwnership();
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        @android.annotation.SystemApi
        public void setPreVerifiedDomains(java.util.Set<java.lang.String> set) {
            com.android.internal.util.Preconditions.checkArgument((set == null || set.isEmpty()) ? false : true, "Provided pre-verified domains cannot be null or empty.");
            try {
                this.mSession.setPreVerifiedDomains(new android.content.pm.verify.domain.DomainSet(set));
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        @android.annotation.SystemApi
        public java.util.Set<java.lang.String> getPreVerifiedDomains() {
            try {
                android.content.pm.verify.domain.DomainSet preVerifiedDomains = this.mSession.getPreVerifiedDomains();
                return preVerifiedDomains != null ? preVerifiedDomains.getDomains() : java.util.Collections.emptySet();
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    @android.annotation.SystemApi
    public android.content.pm.PackageInstaller.InstallInfo readInstallInfo(java.io.File file, int i) throws android.content.pm.PackageInstaller.PackageParsingException {
        android.content.pm.parsing.result.ParseResult<android.content.pm.parsing.PackageLite> parsePackageLite = android.content.pm.parsing.ApkLiteParseUtils.parsePackageLite(android.content.pm.parsing.result.ParseTypeImpl.forDefaultParsing().reset(), file, i);
        if (parsePackageLite.isError()) {
            throw new android.content.pm.PackageInstaller.PackageParsingException(parsePackageLite.getErrorCode(), parsePackageLite.getErrorMessage());
        }
        return new android.content.pm.PackageInstaller.InstallInfo(parsePackageLite);
    }

    @android.annotation.SystemApi
    public android.content.pm.PackageInstaller.InstallInfo readInstallInfo(android.os.ParcelFileDescriptor parcelFileDescriptor, java.lang.String str, int i) throws android.content.pm.PackageInstaller.PackageParsingException {
        android.content.pm.parsing.result.ParseResult<android.content.pm.parsing.PackageLite> parseMonolithicPackageLite = android.content.pm.parsing.ApkLiteParseUtils.parseMonolithicPackageLite(android.content.pm.parsing.result.ParseTypeImpl.forDefaultParsing(), parcelFileDescriptor.getFileDescriptor(), str, i);
        if (parseMonolithicPackageLite.isError()) {
            throw new android.content.pm.PackageInstaller.PackageParsingException(parseMonolithicPackageLite.getErrorCode(), parseMonolithicPackageLite.getErrorMessage());
        }
        return new android.content.pm.PackageInstaller.InstallInfo(parseMonolithicPackageLite);
    }

    public void requestArchive(java.lang.String str, android.content.IntentSender intentSender) throws android.content.pm.PackageManager.NameNotFoundException {
        try {
            this.mInstaller.requestArchive(str, this.mInstallerPackageName, 0, intentSender, new android.os.UserHandle(this.mUserId));
        } catch (android.os.ParcelableException e) {
            e.maybeRethrow(android.content.pm.PackageManager.NameNotFoundException.class);
        } catch (android.os.RemoteException e2) {
            throw e2.rethrowFromSystemServer();
        }
    }

    public void requestUnarchive(java.lang.String str, android.content.IntentSender intentSender) throws java.io.IOException, android.content.pm.PackageManager.NameNotFoundException {
        try {
            this.mInstaller.requestUnarchive(str, this.mInstallerPackageName, intentSender, new android.os.UserHandle(this.mUserId));
        } catch (android.os.ParcelableException e) {
            e.maybeRethrow(java.io.IOException.class);
            e.maybeRethrow(android.content.pm.PackageManager.NameNotFoundException.class);
        } catch (android.os.RemoteException e2) {
            throw e2.rethrowFromSystemServer();
        }
    }

    public void reportUnarchivalStatus(int i, int i2, long j, android.app.PendingIntent pendingIntent) throws android.content.pm.PackageManager.NameNotFoundException {
        try {
            this.mInstaller.reportUnarchivalStatus(i, i2, j, pendingIntent, new android.os.UserHandle(this.mUserId));
        } catch (android.os.ParcelableException e) {
            e.maybeRethrow(android.content.pm.PackageManager.NameNotFoundException.class);
        } catch (android.os.RemoteException e2) {
            throw e2.rethrowFromSystemServer();
        }
    }

    public void reportUnarchivalState(android.content.pm.PackageInstaller.UnarchivalState unarchivalState) throws android.content.pm.PackageManager.NameNotFoundException {
        java.util.Objects.requireNonNull(unarchivalState);
        try {
            this.mInstaller.reportUnarchivalStatus(unarchivalState.getUnarchiveId(), unarchivalState.getStatus(), unarchivalState.getRequiredStorageBytes(), unarchivalState.getUserActionIntent(), new android.os.UserHandle(this.mUserId));
        } catch (android.os.ParcelableException e) {
            e.maybeRethrow(android.content.pm.PackageManager.NameNotFoundException.class);
        } catch (android.os.RemoteException e2) {
            throw e2.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public static class InstallInfo {
        private android.content.pm.parsing.PackageLite mPkg;

        @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
        public @interface InstallLocation {
        }

        InstallInfo(android.content.pm.parsing.result.ParseResult<android.content.pm.parsing.PackageLite> parseResult) {
            this.mPkg = parseResult.getResult();
        }

        public java.lang.String getPackageName() {
            return this.mPkg.getPackageName();
        }

        public int getInstallLocation() {
            return this.mPkg.getInstallLocation();
        }

        public long calculateInstalledSize(android.content.pm.PackageInstaller.SessionParams sessionParams) throws java.io.IOException {
            return com.android.internal.content.InstallLocationUtils.calculateInstalledSize(this.mPkg, sessionParams.abiOverride);
        }

        public long calculateInstalledSize(android.content.pm.PackageInstaller.SessionParams sessionParams, android.os.ParcelFileDescriptor parcelFileDescriptor) throws java.io.IOException {
            return com.android.internal.content.InstallLocationUtils.calculateInstalledSize(this.mPkg, sessionParams.abiOverride, parcelFileDescriptor.getFileDescriptor());
        }
    }

    @android.annotation.SystemApi
    public static class PackageParsingException extends java.lang.Exception {
        private final int mErrorCode;

        public PackageParsingException(int i, java.lang.String str) {
            super(str);
            this.mErrorCode = i;
        }

        public int getErrorCode() {
            return this.mErrorCode;
        }
    }

    public static class SessionParams implements android.os.Parcelable {
        public static final int MAX_PACKAGE_NAME_LENGTH = 255;
        public static final int MODE_FULL_INSTALL = 1;
        public static final int MODE_INHERIT_EXISTING = 2;
        public static final int MODE_INVALID = -1;
        public static final int PERMISSION_STATE_DEFAULT = 0;
        public static final int PERMISSION_STATE_DENIED = 2;
        public static final int PERMISSION_STATE_GRANTED = 1;
        public static final int UID_UNKNOWN = -1;
        public static final int USER_ACTION_NOT_REQUIRED = 2;
        public static final int USER_ACTION_REQUIRED = 1;
        public static final int USER_ACTION_UNSPECIFIED = 0;
        public java.lang.String abiOverride;
        public android.graphics.Bitmap appIcon;
        public long appIconLastModified;
        public java.lang.String appLabel;
        public java.lang.String appPackageName;
        public boolean applicationEnabledSettingPersistent;
        public int autoRevokePermissionsMode;
        public android.content.pm.DataLoaderParams dataLoaderParams;
        public int developmentInstallFlags;
        public boolean forceQueryableOverride;
        public int installFlags;
        public int installLocation;
        public int installReason;
        public int installScenario;
        public java.lang.String installerPackageName;
        public boolean isMultiPackage;
        public boolean isStaged;
        private final android.util.ArrayMap<java.lang.String, java.lang.Integer> mPermissionStates;
        public int mode;
        public int originatingUid;
        public android.net.Uri originatingUri;
        public int packageSource;
        public android.net.Uri referrerUri;
        public int requireUserAction;
        public long requiredInstalledVersionCode;
        public int rollbackDataPolicy;
        public int rollbackImpactLevel;
        public long rollbackLifetimeMillis;
        public long sizeBytes;
        public int unarchiveId;
        public java.lang.String volumeUuid;
        public java.util.List<java.lang.String> whitelistedRestrictedPermissions;
        public static final java.util.Set<java.lang.String> RESTRICTED_PERMISSIONS_ALL = new android.util.ArraySet();
        public static final android.os.Parcelable.Creator<android.content.pm.PackageInstaller.SessionParams> CREATOR = new android.os.Parcelable.Creator<android.content.pm.PackageInstaller.SessionParams>() { // from class: android.content.pm.PackageInstaller.SessionParams.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.content.pm.PackageInstaller.SessionParams createFromParcel(android.os.Parcel parcel) {
                return new android.content.pm.PackageInstaller.SessionParams(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.content.pm.PackageInstaller.SessionParams[] newArray(int i) {
                return new android.content.pm.PackageInstaller.SessionParams[i];
            }
        };

        @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
        public @interface PermissionState {
        }

        @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
        public @interface UserActionRequirement {
        }

        public SessionParams(int i) {
            this.mode = -1;
            this.installFlags = 4194304;
            this.installLocation = 1;
            this.installReason = 0;
            this.installScenario = 0;
            this.sizeBytes = -1L;
            this.appIconLastModified = -1L;
            this.originatingUid = -1;
            this.autoRevokePermissionsMode = 3;
            this.packageSource = 0;
            this.requiredInstalledVersionCode = -1L;
            this.rollbackDataPolicy = 0;
            this.rollbackLifetimeMillis = 0L;
            this.rollbackImpactLevel = 0;
            this.requireUserAction = 0;
            this.applicationEnabledSettingPersistent = false;
            this.developmentInstallFlags = 0;
            this.unarchiveId = -1;
            this.mode = i;
            this.mPermissionStates = new android.util.ArrayMap<>();
        }

        public SessionParams(android.os.Parcel parcel) {
            this.mode = -1;
            this.installFlags = 4194304;
            this.installLocation = 1;
            this.installReason = 0;
            this.installScenario = 0;
            this.sizeBytes = -1L;
            this.appIconLastModified = -1L;
            this.originatingUid = -1;
            this.autoRevokePermissionsMode = 3;
            this.packageSource = 0;
            this.requiredInstalledVersionCode = -1L;
            this.rollbackDataPolicy = 0;
            this.rollbackLifetimeMillis = 0L;
            this.rollbackImpactLevel = 0;
            this.requireUserAction = 0;
            this.applicationEnabledSettingPersistent = false;
            this.developmentInstallFlags = 0;
            this.unarchiveId = -1;
            this.mode = parcel.readInt();
            this.installFlags = parcel.readInt();
            this.installLocation = parcel.readInt();
            this.installReason = parcel.readInt();
            this.installScenario = parcel.readInt();
            this.sizeBytes = parcel.readLong();
            this.appPackageName = parcel.readString();
            this.appIcon = (android.graphics.Bitmap) parcel.readParcelable(null, android.graphics.Bitmap.class);
            this.appLabel = parcel.readString();
            this.originatingUri = (android.net.Uri) parcel.readParcelable(null, android.net.Uri.class);
            this.originatingUid = parcel.readInt();
            this.referrerUri = (android.net.Uri) parcel.readParcelable(null, android.net.Uri.class);
            this.abiOverride = parcel.readString();
            this.volumeUuid = parcel.readString();
            this.mPermissionStates = new android.util.ArrayMap<>();
            parcel.readMap(this.mPermissionStates, null, java.lang.String.class, java.lang.Integer.class);
            this.whitelistedRestrictedPermissions = parcel.createStringArrayList();
            this.autoRevokePermissionsMode = parcel.readInt();
            this.installerPackageName = parcel.readString();
            this.isMultiPackage = parcel.readBoolean();
            this.isStaged = parcel.readBoolean();
            this.forceQueryableOverride = parcel.readBoolean();
            this.requiredInstalledVersionCode = parcel.readLong();
            android.content.pm.DataLoaderParamsParcel dataLoaderParamsParcel = (android.content.pm.DataLoaderParamsParcel) parcel.readParcelable(android.content.pm.DataLoaderParamsParcel.class.getClassLoader(), android.content.pm.DataLoaderParamsParcel.class);
            if (dataLoaderParamsParcel != null) {
                this.dataLoaderParams = new android.content.pm.DataLoaderParams(dataLoaderParamsParcel);
            }
            this.rollbackDataPolicy = parcel.readInt();
            this.rollbackLifetimeMillis = parcel.readLong();
            this.rollbackImpactLevel = parcel.readInt();
            this.requireUserAction = parcel.readInt();
            this.packageSource = parcel.readInt();
            this.applicationEnabledSettingPersistent = parcel.readBoolean();
            this.developmentInstallFlags = parcel.readInt();
            this.unarchiveId = parcel.readInt();
        }

        public android.content.pm.PackageInstaller.SessionParams copy() {
            android.content.pm.PackageInstaller.SessionParams sessionParams = new android.content.pm.PackageInstaller.SessionParams(this.mode);
            sessionParams.installFlags = this.installFlags;
            sessionParams.installLocation = this.installLocation;
            sessionParams.installReason = this.installReason;
            sessionParams.installScenario = this.installScenario;
            sessionParams.sizeBytes = this.sizeBytes;
            sessionParams.appPackageName = this.appPackageName;
            sessionParams.appIcon = this.appIcon;
            sessionParams.appLabel = this.appLabel;
            sessionParams.originatingUri = this.originatingUri;
            sessionParams.originatingUid = this.originatingUid;
            sessionParams.referrerUri = this.referrerUri;
            sessionParams.abiOverride = this.abiOverride;
            sessionParams.volumeUuid = this.volumeUuid;
            sessionParams.mPermissionStates.putAll((android.util.ArrayMap<? extends java.lang.String, ? extends java.lang.Integer>) this.mPermissionStates);
            sessionParams.whitelistedRestrictedPermissions = this.whitelistedRestrictedPermissions;
            sessionParams.autoRevokePermissionsMode = this.autoRevokePermissionsMode;
            sessionParams.installerPackageName = this.installerPackageName;
            sessionParams.isMultiPackage = this.isMultiPackage;
            sessionParams.isStaged = this.isStaged;
            sessionParams.forceQueryableOverride = this.forceQueryableOverride;
            sessionParams.requiredInstalledVersionCode = this.requiredInstalledVersionCode;
            sessionParams.dataLoaderParams = this.dataLoaderParams;
            sessionParams.rollbackDataPolicy = this.rollbackDataPolicy;
            sessionParams.rollbackLifetimeMillis = this.rollbackLifetimeMillis;
            sessionParams.rollbackImpactLevel = this.rollbackImpactLevel;
            sessionParams.requireUserAction = this.requireUserAction;
            sessionParams.packageSource = this.packageSource;
            sessionParams.applicationEnabledSettingPersistent = this.applicationEnabledSettingPersistent;
            sessionParams.developmentInstallFlags = this.developmentInstallFlags;
            sessionParams.unarchiveId = this.unarchiveId;
            return sessionParams;
        }

        public boolean areHiddenOptionsSet() {
            return ((this.installFlags & 1169536) == this.installFlags && this.abiOverride == null && this.volumeUuid == null) ? false : true;
        }

        public void setInstallLocation(int i) {
            this.installLocation = i;
        }

        public void setSize(long j) {
            this.sizeBytes = j;
        }

        public void setAppPackageName(java.lang.String str) {
            this.appPackageName = str;
        }

        public void setAppIcon(android.graphics.Bitmap bitmap) {
            this.appIcon = bitmap;
        }

        public void setAppLabel(java.lang.CharSequence charSequence) {
            this.appLabel = charSequence != null ? charSequence.toString() : null;
        }

        public void setOriginatingUri(android.net.Uri uri) {
            this.originatingUri = uri;
        }

        public void setOriginatingUid(int i) {
            this.originatingUid = i;
        }

        public void setReferrerUri(android.net.Uri uri) {
            this.referrerUri = uri;
        }

        @android.annotation.SystemApi
        @java.lang.Deprecated
        public void setGrantedRuntimePermissions(java.lang.String[] strArr) {
            if (strArr == null) {
                this.installFlags |= 256;
                this.mPermissionStates.clear();
                return;
            }
            this.installFlags &= -257;
            for (java.lang.String str : strArr) {
                setPermissionState(str, 1);
            }
        }

        public android.content.pm.PackageInstaller.SessionParams setPermissionState(java.lang.String str, int i) {
            if (android.text.TextUtils.isEmpty(str)) {
                throw new java.lang.IllegalArgumentException("Provided permissionName cannot be " + (str == null ? "null" : "empty"));
            }
            switch (i) {
                case 0:
                    this.mPermissionStates.remove(str);
                    return this;
                case 1:
                case 2:
                    this.mPermissionStates.put(str, java.lang.Integer.valueOf(i));
                    return this;
                default:
                    throw new java.lang.IllegalArgumentException("Unexpected permission state int: " + i);
            }
        }

        public void setPermissionStates(java.util.Collection<java.lang.String> collection, java.util.Collection<java.lang.String> collection2) {
            java.util.Iterator<java.lang.String> it = collection.iterator();
            while (it.hasNext()) {
                this.mPermissionStates.put(it.next(), 1);
            }
            java.util.Iterator<java.lang.String> it2 = collection2.iterator();
            while (it2.hasNext()) {
                this.mPermissionStates.put(it2.next(), 2);
            }
        }

        public void setPackageSource(int i) {
            this.packageSource = i;
        }

        public void setWhitelistedRestrictedPermissions(java.util.Set<java.lang.String> set) {
            if (set == RESTRICTED_PERMISSIONS_ALL) {
                this.installFlags |= 4194304;
                this.whitelistedRestrictedPermissions = null;
            } else {
                this.installFlags &= -4194305;
                this.whitelistedRestrictedPermissions = set != null ? new java.util.ArrayList(set) : null;
            }
        }

        @java.lang.Deprecated
        public void setAutoRevokePermissionsMode(boolean z) {
            this.autoRevokePermissionsMode = !z ? 1 : 0;
        }

        @android.annotation.SystemApi
        public void setEnableRollback(boolean z) {
            setEnableRollback(z, 0);
        }

        @android.annotation.SystemApi
        public void setEnableRollback(boolean z, int i) {
            if (z) {
                this.installFlags |= 262144;
            } else {
                this.installFlags &= -262145;
                this.rollbackLifetimeMillis = 0L;
            }
            this.rollbackDataPolicy = i;
        }

        @android.annotation.SystemApi
        public void setRollbackLifetimeMillis(long j) {
            if (j < 0) {
                throw new java.lang.IllegalArgumentException("rollbackLifetimeMillis can't be negative.");
            }
            if ((this.installFlags & 262144) == 0) {
                throw new java.lang.IllegalArgumentException("Can't set rollbackLifetimeMillis when rollback is not enabled");
            }
            this.rollbackLifetimeMillis = j;
        }

        @android.annotation.SystemApi
        public void setRollbackImpactLevel(int i) {
            if ((this.installFlags & 262144) == 0) {
                throw new java.lang.IllegalArgumentException("Can't set rollbackImpactLevel when rollback is not enabled");
            }
            this.rollbackImpactLevel = i;
        }

        @android.annotation.SystemApi
        @java.lang.Deprecated
        public void setAllowDowngrade(boolean z) {
            setRequestDowngrade(z);
        }

        @android.annotation.SystemApi
        public void setRequestDowngrade(boolean z) {
            if (z) {
                this.installFlags |= 128;
            } else {
                this.installFlags &= android.content.pm.PackageManager.INSTALL_FAILED_PRE_APPROVAL_NOT_AVAILABLE;
            }
        }

        public void setRequiredInstalledVersionCode(long j) {
            this.requiredInstalledVersionCode = j;
        }

        public void setInstallFlagsForcePermissionPrompt() {
            this.installFlags |= 1024;
        }

        public void setDontKillApp(boolean z) {
            if (z) {
                this.installFlags |= 4096;
            } else {
                this.installFlags &= -4097;
            }
        }

        @android.annotation.SystemApi
        public void setInstallAsInstantApp(boolean z) {
            if (z) {
                this.installFlags |= 2048;
                this.installFlags &= -16385;
            } else {
                this.installFlags &= -2049;
                this.installFlags |= 16384;
            }
        }

        @android.annotation.SystemApi
        public void setInstallAsVirtualPreload() {
            this.installFlags |= 65536;
        }

        public void setInstallReason(int i) {
            this.installReason = i;
        }

        @android.annotation.SystemApi
        public void setAllocateAggressive(boolean z) {
            if (z) {
                this.installFlags |= 32768;
            } else {
                this.installFlags &= -32769;
            }
        }

        public void setInstallFlagAllowTest() {
            this.installFlags |= 4;
        }

        public void setInstallerPackageName(java.lang.String str) {
            this.installerPackageName = str;
        }

        public void setMultiPackage() {
            this.isMultiPackage = true;
        }

        @android.annotation.SystemApi
        public void setStaged() {
            this.isStaged = true;
        }

        @android.annotation.SystemApi
        public void setInstallAsApex() {
            this.installFlags |= 131072;
        }

        public boolean getEnableRollback() {
            return (this.installFlags & 262144) != 0;
        }

        @android.annotation.SystemApi
        public void setDataLoaderParams(android.content.pm.DataLoaderParams dataLoaderParams) {
            this.dataLoaderParams = dataLoaderParams;
        }

        public void setForceQueryable() {
            this.forceQueryableOverride = true;
        }

        public void setRequireUserAction(int i) {
            if (i != 0 && i != 1 && i != 2) {
                throw new java.lang.IllegalArgumentException("requireUserAction set as invalid value of " + i + ", but must be one of [USER_ACTION_UNSPECIFIED, USER_ACTION_REQUIRED, USER_ACTION_NOT_REQUIRED]");
            }
            this.requireUserAction = i;
        }

        public void setInstallScenario(int i) {
            this.installScenario = i;
        }

        public void setApplicationEnabledSettingPersistent() {
            this.applicationEnabledSettingPersistent = true;
        }

        public void setRequestUpdateOwnership(boolean z) {
            if (z) {
                this.installFlags |= 33554432;
            } else {
                this.installFlags &= -33554433;
            }
        }

        public void setUnarchiveId(int i) {
            this.unarchiveId = i;
        }

        public android.util.ArrayMap<java.lang.String, java.lang.Integer> getPermissionStates() {
            return this.mPermissionStates;
        }

        public java.lang.String[] getLegacyGrantedRuntimePermissions() {
            if ((this.installFlags & 256) != 0) {
                return null;
            }
            java.util.ArrayList arrayList = new java.util.ArrayList();
            for (int i = 0; i < this.mPermissionStates.size(); i++) {
                java.lang.String keyAt = this.mPermissionStates.keyAt(i);
                if (this.mPermissionStates.valueAt(i).intValue() == 1) {
                    arrayList.add(keyAt);
                }
            }
            return (java.lang.String[]) arrayList.toArray((java.lang.String[]) com.android.internal.util.ArrayUtils.emptyArray(java.lang.String.class));
        }

        public void dump(com.android.internal.util.IndentingPrintWriter indentingPrintWriter) {
            indentingPrintWriter.printPair("mode", java.lang.Integer.valueOf(this.mode));
            indentingPrintWriter.printHexPair("installFlags", this.installFlags);
            indentingPrintWriter.printPair("installLocation", java.lang.Integer.valueOf(this.installLocation));
            indentingPrintWriter.printPair("installReason", java.lang.Integer.valueOf(this.installReason));
            indentingPrintWriter.printPair("installScenario", java.lang.Integer.valueOf(this.installScenario));
            indentingPrintWriter.printPair("sizeBytes", java.lang.Long.valueOf(this.sizeBytes));
            indentingPrintWriter.printPair("appPackageName", this.appPackageName);
            indentingPrintWriter.printPair("appIcon", java.lang.Boolean.valueOf(this.appIcon != null));
            indentingPrintWriter.printPair("appLabel", this.appLabel);
            indentingPrintWriter.printPair("originatingUri", this.originatingUri);
            indentingPrintWriter.printPair("originatingUid", java.lang.Integer.valueOf(this.originatingUid));
            indentingPrintWriter.printPair("referrerUri", this.referrerUri);
            indentingPrintWriter.printPair("abiOverride", this.abiOverride);
            indentingPrintWriter.printPair("volumeUuid", this.volumeUuid);
            indentingPrintWriter.printPair("mPermissionStates", this.mPermissionStates);
            indentingPrintWriter.printPair("packageSource", java.lang.Integer.valueOf(this.packageSource));
            indentingPrintWriter.printPair("whitelistedRestrictedPermissions", this.whitelistedRestrictedPermissions);
            indentingPrintWriter.printPair("autoRevokePermissions", java.lang.Integer.valueOf(this.autoRevokePermissionsMode));
            indentingPrintWriter.printPair("installerPackageName", this.installerPackageName);
            indentingPrintWriter.printPair("isMultiPackage", java.lang.Boolean.valueOf(this.isMultiPackage));
            indentingPrintWriter.printPair("isStaged", java.lang.Boolean.valueOf(this.isStaged));
            indentingPrintWriter.printPair("forceQueryable", java.lang.Boolean.valueOf(this.forceQueryableOverride));
            indentingPrintWriter.printPair("requireUserAction", android.content.pm.PackageInstaller.SessionInfo.userActionToString(this.requireUserAction));
            indentingPrintWriter.printPair("requiredInstalledVersionCode", java.lang.Long.valueOf(this.requiredInstalledVersionCode));
            indentingPrintWriter.printPair("dataLoaderParams", this.dataLoaderParams);
            indentingPrintWriter.printPair("rollbackDataPolicy", java.lang.Integer.valueOf(this.rollbackDataPolicy));
            indentingPrintWriter.printPair("rollbackLifetimeMillis", java.lang.Long.valueOf(this.rollbackLifetimeMillis));
            indentingPrintWriter.printPair("rollbackImpactLevel", java.lang.Integer.valueOf(this.rollbackImpactLevel));
            indentingPrintWriter.printPair("applicationEnabledSettingPersistent", java.lang.Boolean.valueOf(this.applicationEnabledSettingPersistent));
            indentingPrintWriter.printHexPair("developmentInstallFlags", this.developmentInstallFlags);
            indentingPrintWriter.printPair("unarchiveId", java.lang.Integer.valueOf(this.unarchiveId));
            indentingPrintWriter.println();
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(android.os.Parcel parcel, int i) {
            parcel.writeInt(this.mode);
            parcel.writeInt(this.installFlags);
            parcel.writeInt(this.installLocation);
            parcel.writeInt(this.installReason);
            parcel.writeInt(this.installScenario);
            parcel.writeLong(this.sizeBytes);
            parcel.writeString(this.appPackageName);
            parcel.writeParcelable(this.appIcon, i);
            parcel.writeString(this.appLabel);
            parcel.writeParcelable(this.originatingUri, i);
            parcel.writeInt(this.originatingUid);
            parcel.writeParcelable(this.referrerUri, i);
            parcel.writeString(this.abiOverride);
            parcel.writeString(this.volumeUuid);
            parcel.writeMap(this.mPermissionStates);
            parcel.writeStringList(this.whitelistedRestrictedPermissions);
            parcel.writeInt(this.autoRevokePermissionsMode);
            parcel.writeString(this.installerPackageName);
            parcel.writeBoolean(this.isMultiPackage);
            parcel.writeBoolean(this.isStaged);
            parcel.writeBoolean(this.forceQueryableOverride);
            parcel.writeLong(this.requiredInstalledVersionCode);
            if (this.dataLoaderParams != null) {
                parcel.writeParcelable(this.dataLoaderParams.getData(), i);
            } else {
                parcel.writeParcelable(null, i);
            }
            parcel.writeInt(this.rollbackDataPolicy);
            parcel.writeLong(this.rollbackLifetimeMillis);
            parcel.writeInt(this.rollbackImpactLevel);
            parcel.writeInt(this.requireUserAction);
            parcel.writeInt(this.packageSource);
            parcel.writeBoolean(this.applicationEnabledSettingPersistent);
            parcel.writeInt(this.developmentInstallFlags);
            parcel.writeInt(this.unarchiveId);
        }
    }

    public static class SessionInfo implements android.os.Parcelable {
        public static final int INVALID_ID = -1;
        public static final int SESSION_ACTIVATION_FAILED = 2;
        public static final int SESSION_CONFLICT = 4;
        public static final int SESSION_NO_ERROR = 0;
        public static final int SESSION_UNKNOWN_ERROR = 3;
        public static final int SESSION_VERIFICATION_FAILED = 1;

        @java.lang.Deprecated
        public static final int STAGED_SESSION_ACTIVATION_FAILED = 2;

        @java.lang.Deprecated
        public static final int STAGED_SESSION_CONFLICT = 4;

        @java.lang.Deprecated
        public static final int STAGED_SESSION_NO_ERROR = 0;

        @java.lang.Deprecated
        public static final int STAGED_SESSION_UNKNOWN = 3;

        @java.lang.Deprecated
        public static final int STAGED_SESSION_VERIFICATION_FAILED = 1;
        public boolean active;
        public android.graphics.Bitmap appIcon;
        public java.lang.CharSequence appLabel;
        public java.lang.String appPackageName;
        public boolean applicationEnabledSettingPersistent;
        public int autoRevokePermissionsMode;
        public int[] childSessionIds;
        public long createdMillis;
        public boolean forceQueryable;
        public java.lang.String[] grantedRuntimePermissions;
        public int installFlags;
        public int installLocation;
        public int installReason;
        public int installScenario;
        public java.lang.String installerAttributionTag;
        public java.lang.String installerPackageName;
        public int installerUid;
        public boolean isCommitted;
        public boolean isMultiPackage;
        public boolean isPreapprovalRequested;
        public boolean isSessionApplied;
        public boolean isSessionFailed;
        public boolean isSessionReady;
        public boolean isStaged;
        private int mSessionErrorCode;
        private java.lang.String mSessionErrorMessage;
        public int mode;
        public int originatingUid;
        public android.net.Uri originatingUri;
        public int packageSource;
        public int parentSessionId;
        public int pendingUserActionReason;
        public float progress;
        public android.net.Uri referrerUri;
        public int requireUserAction;
        public java.lang.String resolvedBaseCodePath;
        public int rollbackDataPolicy;
        public int rollbackImpactLevel;
        public long rollbackLifetimeMillis;
        public boolean sealed;
        public int sessionId;
        public long sizeBytes;
        public long updatedMillis;
        public int userId;
        public java.util.List<java.lang.String> whitelistedRestrictedPermissions;
        private static final int[] NO_SESSIONS = new int[0];
        public static final android.os.Parcelable.Creator<android.content.pm.PackageInstaller.SessionInfo> CREATOR = new android.os.Parcelable.Creator<android.content.pm.PackageInstaller.SessionInfo>() { // from class: android.content.pm.PackageInstaller.SessionInfo.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.content.pm.PackageInstaller.SessionInfo createFromParcel(android.os.Parcel parcel) {
                return new android.content.pm.PackageInstaller.SessionInfo(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.content.pm.PackageInstaller.SessionInfo[] newArray(int i) {
                return new android.content.pm.PackageInstaller.SessionInfo[i];
            }
        };

        /* JADX INFO: Access modifiers changed from: private */
        public static java.lang.String userActionToString(int i) {
            switch (i) {
                case 1:
                    return "REQUIRED";
                case 2:
                    return "NOT_REQUIRED";
                default:
                    return "UNSPECIFIED";
            }
        }

        public SessionInfo() {
            this.autoRevokePermissionsMode = 3;
            this.parentSessionId = -1;
            this.childSessionIds = NO_SESSIONS;
            this.packageSource = 0;
        }

        public SessionInfo(android.os.Parcel parcel) {
            this.autoRevokePermissionsMode = 3;
            this.parentSessionId = -1;
            this.childSessionIds = NO_SESSIONS;
            this.packageSource = 0;
            this.sessionId = parcel.readInt();
            this.userId = parcel.readInt();
            this.installerPackageName = parcel.readString();
            this.installerAttributionTag = parcel.readString();
            this.resolvedBaseCodePath = parcel.readString();
            this.progress = parcel.readFloat();
            this.sealed = parcel.readInt() != 0;
            this.active = parcel.readInt() != 0;
            this.mode = parcel.readInt();
            this.installReason = parcel.readInt();
            this.installScenario = parcel.readInt();
            this.sizeBytes = parcel.readLong();
            this.appPackageName = parcel.readString();
            this.appIcon = (android.graphics.Bitmap) parcel.readParcelable(null, android.graphics.Bitmap.class);
            this.appLabel = parcel.readString();
            this.installLocation = parcel.readInt();
            this.originatingUri = (android.net.Uri) parcel.readParcelable(null, android.net.Uri.class);
            this.originatingUid = parcel.readInt();
            this.referrerUri = (android.net.Uri) parcel.readParcelable(null, android.net.Uri.class);
            this.grantedRuntimePermissions = parcel.readStringArray();
            this.whitelistedRestrictedPermissions = parcel.createStringArrayList();
            this.autoRevokePermissionsMode = parcel.readInt();
            this.installFlags = parcel.readInt();
            this.isMultiPackage = parcel.readBoolean();
            this.isStaged = parcel.readBoolean();
            this.forceQueryable = parcel.readBoolean();
            this.parentSessionId = parcel.readInt();
            this.childSessionIds = parcel.createIntArray();
            if (this.childSessionIds == null) {
                this.childSessionIds = NO_SESSIONS;
            }
            this.isSessionApplied = parcel.readBoolean();
            this.isSessionReady = parcel.readBoolean();
            this.isSessionFailed = parcel.readBoolean();
            this.mSessionErrorCode = parcel.readInt();
            this.mSessionErrorMessage = parcel.readString();
            this.isCommitted = parcel.readBoolean();
            this.isPreapprovalRequested = parcel.readBoolean();
            this.rollbackDataPolicy = parcel.readInt();
            this.rollbackLifetimeMillis = parcel.readLong();
            this.rollbackImpactLevel = parcel.readInt();
            this.createdMillis = parcel.readLong();
            this.requireUserAction = parcel.readInt();
            this.installerUid = parcel.readInt();
            this.packageSource = parcel.readInt();
            this.applicationEnabledSettingPersistent = parcel.readBoolean();
            this.pendingUserActionReason = parcel.readInt();
        }

        public int getSessionId() {
            return this.sessionId;
        }

        public android.os.UserHandle getUser() {
            return new android.os.UserHandle(this.userId);
        }

        public java.lang.String getInstallerPackageName() {
            return this.installerPackageName;
        }

        public java.lang.String getInstallerAttributionTag() {
            return this.installerAttributionTag;
        }

        public float getProgress() {
            return this.progress;
        }

        public boolean isActive() {
            return this.active;
        }

        public boolean isSealed() {
            return this.sealed;
        }

        public int getInstallReason() {
            return this.installReason;
        }

        @java.lang.Deprecated
        public boolean isOpen() {
            return isActive();
        }

        public java.lang.String getAppPackageName() {
            return this.appPackageName;
        }

        public android.graphics.Bitmap getAppIcon() {
            if (this.appIcon == null) {
                try {
                    android.content.pm.PackageInstaller.SessionInfo sessionInfo = android.app.AppGlobals.getPackageManager().getPackageInstaller().getSessionInfo(this.sessionId);
                    this.appIcon = sessionInfo != null ? sessionInfo.appIcon : null;
                } catch (android.os.RemoteException e) {
                    throw e.rethrowFromSystemServer();
                }
            }
            return this.appIcon;
        }

        public java.lang.CharSequence getAppLabel() {
            return this.appLabel;
        }

        public android.content.Intent createDetailsIntent() {
            android.content.Intent intent = new android.content.Intent(android.content.pm.PackageInstaller.ACTION_SESSION_DETAILS);
            intent.putExtra(android.content.pm.PackageInstaller.EXTRA_SESSION_ID, this.sessionId);
            intent.setPackage(this.installerPackageName);
            intent.setFlags(268435456);
            return intent;
        }

        public int getMode() {
            return this.mode;
        }

        public int getInstallLocation() {
            return this.installLocation;
        }

        public long getSize() {
            return this.sizeBytes;
        }

        public android.net.Uri getOriginatingUri() {
            return this.originatingUri;
        }

        public int getOriginatingUid() {
            return this.originatingUid;
        }

        public android.net.Uri getReferrerUri() {
            return this.referrerUri;
        }

        @android.annotation.SystemApi
        public java.lang.String getResolvedBaseApkPath() {
            return this.resolvedBaseCodePath;
        }

        @android.annotation.SystemApi
        public java.lang.String[] getGrantedRuntimePermissions() {
            return this.grantedRuntimePermissions;
        }

        @android.annotation.SystemApi
        public java.util.Set<java.lang.String> getWhitelistedRestrictedPermissions() {
            if ((this.installFlags & 4194304) != 0) {
                return android.content.pm.PackageInstaller.SessionParams.RESTRICTED_PERMISSIONS_ALL;
            }
            if (this.whitelistedRestrictedPermissions != null) {
                return new android.util.ArraySet(this.whitelistedRestrictedPermissions);
            }
            return java.util.Collections.emptySet();
        }

        @android.annotation.SystemApi
        public int getAutoRevokePermissionsMode() {
            return this.autoRevokePermissionsMode;
        }

        @android.annotation.SystemApi
        @java.lang.Deprecated
        public boolean getAllowDowngrade() {
            return getRequestDowngrade();
        }

        @android.annotation.SystemApi
        public boolean getRequestDowngrade() {
            return (this.installFlags & 128) != 0;
        }

        public boolean getDontKillApp() {
            return (this.installFlags & 4096) != 0;
        }

        @android.annotation.SystemApi
        public boolean getInstallAsInstantApp(boolean z) {
            return (this.installFlags & 2048) != 0;
        }

        @android.annotation.SystemApi
        public boolean getInstallAsFullApp(boolean z) {
            return (this.installFlags & 16384) != 0;
        }

        @android.annotation.SystemApi
        public boolean getInstallAsVirtualPreload() {
            return (this.installFlags & 65536) != 0;
        }

        @android.annotation.SystemApi
        public boolean getEnableRollback() {
            return (this.installFlags & 262144) != 0;
        }

        @android.annotation.SystemApi
        public boolean getAllocateAggressive() {
            return (this.installFlags & 32768) != 0;
        }

        @java.lang.Deprecated
        public android.content.Intent getDetailsIntent() {
            return createDetailsIntent();
        }

        public int getPackageSource() {
            return this.packageSource;
        }

        public boolean isMultiPackage() {
            return this.isMultiPackage;
        }

        public boolean isStaged() {
            return this.isStaged;
        }

        @android.annotation.SystemApi
        public int getRollbackDataPolicy() {
            return this.rollbackDataPolicy;
        }

        public boolean isForceQueryable() {
            return this.forceQueryable;
        }

        public boolean isStagedSessionActive() {
            return (!this.isStaged || !this.isCommitted || this.isSessionApplied || this.isSessionFailed || hasParentSessionId()) ? false : true;
        }

        public int getParentSessionId() {
            return this.parentSessionId;
        }

        public boolean hasParentSessionId() {
            return this.parentSessionId != -1;
        }

        public int[] getChildSessionIds() {
            return this.childSessionIds;
        }

        private void checkSessionIsStaged() {
            if (!this.isStaged) {
                throw new java.lang.IllegalStateException("Session is not marked as staged.");
            }
        }

        public boolean isStagedSessionApplied() {
            checkSessionIsStaged();
            return this.isSessionApplied;
        }

        public boolean isStagedSessionReady() {
            checkSessionIsStaged();
            return this.isSessionReady;
        }

        public boolean isStagedSessionFailed() {
            checkSessionIsStaged();
            return this.isSessionFailed;
        }

        public int getStagedSessionErrorCode() {
            checkSessionIsStaged();
            return this.mSessionErrorCode;
        }

        public java.lang.String getStagedSessionErrorMessage() {
            checkSessionIsStaged();
            return this.mSessionErrorMessage;
        }

        public void setSessionErrorCode(int i, java.lang.String str) {
            this.mSessionErrorCode = i;
            this.mSessionErrorMessage = str;
        }

        public boolean isCommitted() {
            return this.isCommitted;
        }

        public long getCreatedMillis() {
            return this.createdMillis;
        }

        public long getUpdatedMillis() {
            return this.updatedMillis;
        }

        public int getRequireUserAction() {
            return this.requireUserAction;
        }

        public int getInstallerUid() {
            return this.installerUid;
        }

        public boolean isApplicationEnabledSettingPersistent() {
            return this.applicationEnabledSettingPersistent;
        }

        public boolean isPreApprovalRequested() {
            return this.isPreapprovalRequested;
        }

        public boolean isRequestUpdateOwnership() {
            return (this.installFlags & 33554432) != 0;
        }

        @android.annotation.SystemApi
        public int getPendingUserActionReason() {
            return this.pendingUserActionReason;
        }

        public boolean isUnarchival() {
            return (this.installFlags & 1073741824) != 0;
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(android.os.Parcel parcel, int i) {
            parcel.writeInt(this.sessionId);
            parcel.writeInt(this.userId);
            parcel.writeString(this.installerPackageName);
            parcel.writeString(this.installerAttributionTag);
            parcel.writeString(this.resolvedBaseCodePath);
            parcel.writeFloat(this.progress);
            parcel.writeInt(this.sealed ? 1 : 0);
            parcel.writeInt(this.active ? 1 : 0);
            parcel.writeInt(this.mode);
            parcel.writeInt(this.installReason);
            parcel.writeInt(this.installScenario);
            parcel.writeLong(this.sizeBytes);
            parcel.writeString(this.appPackageName);
            parcel.writeParcelable(this.appIcon, i);
            parcel.writeString(this.appLabel != null ? this.appLabel.toString() : null);
            parcel.writeInt(this.installLocation);
            parcel.writeParcelable(this.originatingUri, i);
            parcel.writeInt(this.originatingUid);
            parcel.writeParcelable(this.referrerUri, i);
            parcel.writeStringArray(this.grantedRuntimePermissions);
            parcel.writeStringList(this.whitelistedRestrictedPermissions);
            parcel.writeInt(this.autoRevokePermissionsMode);
            parcel.writeInt(this.installFlags);
            parcel.writeBoolean(this.isMultiPackage);
            parcel.writeBoolean(this.isStaged);
            parcel.writeBoolean(this.forceQueryable);
            parcel.writeInt(this.parentSessionId);
            parcel.writeIntArray(this.childSessionIds);
            parcel.writeBoolean(this.isSessionApplied);
            parcel.writeBoolean(this.isSessionReady);
            parcel.writeBoolean(this.isSessionFailed);
            parcel.writeInt(this.mSessionErrorCode);
            parcel.writeString(this.mSessionErrorMessage);
            parcel.writeBoolean(this.isCommitted);
            parcel.writeBoolean(this.isPreapprovalRequested);
            parcel.writeInt(this.rollbackDataPolicy);
            parcel.writeLong(this.rollbackLifetimeMillis);
            parcel.writeInt(this.rollbackImpactLevel);
            parcel.writeLong(this.createdMillis);
            parcel.writeInt(this.requireUserAction);
            parcel.writeInt(this.installerUid);
            parcel.writeInt(this.packageSource);
            parcel.writeBoolean(this.applicationEnabledSettingPersistent);
            parcel.writeInt(this.pendingUserActionReason);
        }
    }

    public static final class PreapprovalDetails implements android.os.Parcelable {
        public static final android.os.Parcelable.Creator<android.content.pm.PackageInstaller.PreapprovalDetails> CREATOR = new android.os.Parcelable.Creator<android.content.pm.PackageInstaller.PreapprovalDetails>() { // from class: android.content.pm.PackageInstaller.PreapprovalDetails.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.content.pm.PackageInstaller.PreapprovalDetails[] newArray(int i) {
                return new android.content.pm.PackageInstaller.PreapprovalDetails[i];
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.content.pm.PackageInstaller.PreapprovalDetails createFromParcel(android.os.Parcel parcel) {
                return new android.content.pm.PackageInstaller.PreapprovalDetails(parcel);
            }
        };
        private final android.graphics.Bitmap mIcon;
        private final java.lang.CharSequence mLabel;
        private final android.icu.util.ULocale mLocale;
        private final java.lang.String mPackageName;

        public PreapprovalDetails(android.graphics.Bitmap bitmap, java.lang.CharSequence charSequence, android.icu.util.ULocale uLocale, java.lang.String str) {
            this.mIcon = bitmap;
            this.mLabel = charSequence;
            com.android.internal.util.Preconditions.checkArgument(!android.text.TextUtils.isEmpty(this.mLabel), "App label cannot be empty.");
            this.mLocale = uLocale;
            com.android.internal.util.Preconditions.checkArgument(!java.util.Objects.isNull(this.mLocale), "Locale cannot be null.");
            this.mPackageName = str;
            com.android.internal.util.Preconditions.checkArgument(!android.text.TextUtils.isEmpty(this.mPackageName), "Package name cannot be empty.");
        }

        @Override // android.os.Parcelable
        public void writeToParcel(android.os.Parcel parcel, int i) {
            parcel.writeByte(this.mIcon != null ? (byte) 1 : (byte) 0);
            if (this.mIcon != null) {
                this.mIcon.writeToParcel(parcel, i);
            }
            parcel.writeCharSequence(this.mLabel);
            parcel.writeString8(this.mLocale.toString());
            parcel.writeString8(this.mPackageName);
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        PreapprovalDetails(android.os.Parcel parcel) {
            android.graphics.Bitmap createFromParcel = (parcel.readByte() & 1) == 0 ? null : android.graphics.Bitmap.CREATOR.createFromParcel(parcel);
            java.lang.CharSequence readCharSequence = parcel.readCharSequence();
            android.icu.util.ULocale uLocale = new android.icu.util.ULocale(parcel.readString8());
            java.lang.String readString8 = parcel.readString8();
            this.mIcon = createFromParcel;
            this.mLabel = readCharSequence;
            com.android.internal.util.Preconditions.checkArgument(!android.text.TextUtils.isEmpty(this.mLabel), "App label cannot be empty.");
            this.mLocale = uLocale;
            com.android.internal.util.Preconditions.checkArgument(!java.util.Objects.isNull(this.mLocale), "Locale cannot be null.");
            this.mPackageName = readString8;
            com.android.internal.util.Preconditions.checkArgument(!android.text.TextUtils.isEmpty(this.mPackageName), "Package name cannot be empty.");
        }

        public static final class Builder {
            private long mBuilderFieldsSet = 0;
            private android.graphics.Bitmap mIcon;
            private java.lang.CharSequence mLabel;
            private android.icu.util.ULocale mLocale;
            private java.lang.String mPackageName;

            public android.content.pm.PackageInstaller.PreapprovalDetails.Builder setIcon(android.graphics.Bitmap bitmap) {
                checkNotUsed();
                this.mBuilderFieldsSet |= 1;
                this.mIcon = bitmap;
                return this;
            }

            public android.content.pm.PackageInstaller.PreapprovalDetails.Builder setLabel(java.lang.CharSequence charSequence) {
                checkNotUsed();
                this.mBuilderFieldsSet |= 2;
                this.mLabel = charSequence;
                return this;
            }

            public android.content.pm.PackageInstaller.PreapprovalDetails.Builder setLocale(android.icu.util.ULocale uLocale) {
                checkNotUsed();
                this.mBuilderFieldsSet |= 4;
                this.mLocale = uLocale;
                return this;
            }

            public android.content.pm.PackageInstaller.PreapprovalDetails.Builder setPackageName(java.lang.String str) {
                checkNotUsed();
                this.mBuilderFieldsSet |= 8;
                this.mPackageName = str;
                return this;
            }

            public android.content.pm.PackageInstaller.PreapprovalDetails build() {
                checkNotUsed();
                this.mBuilderFieldsSet |= 16;
                return new android.content.pm.PackageInstaller.PreapprovalDetails(this.mIcon, this.mLabel, this.mLocale, this.mPackageName);
            }

            private void checkNotUsed() {
                if ((this.mBuilderFieldsSet & 16) != 0) {
                    throw new java.lang.IllegalStateException("This Builder should not be reused. Use a new Builder instance instead");
                }
            }
        }

        public android.graphics.Bitmap getIcon() {
            return this.mIcon;
        }

        public java.lang.CharSequence getLabel() {
            return this.mLabel;
        }

        public android.icu.util.ULocale getLocale() {
            return this.mLocale;
        }

        public java.lang.String getPackageName() {
            return this.mPackageName;
        }

        public java.lang.String toString() {
            return "PreapprovalDetails { icon = " + this.mIcon + ", label = " + ((java.lang.Object) this.mLabel) + ", locale = " + this.mLocale + ", packageName = " + this.mPackageName + " }";
        }

        @java.lang.Deprecated
        private void __metadata() {
        }
    }

    public static final class InstallConstraintsResult implements android.os.Parcelable {
        public static final android.os.Parcelable.Creator<android.content.pm.PackageInstaller.InstallConstraintsResult> CREATOR = new android.os.Parcelable.Creator<android.content.pm.PackageInstaller.InstallConstraintsResult>() { // from class: android.content.pm.PackageInstaller.InstallConstraintsResult.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.content.pm.PackageInstaller.InstallConstraintsResult[] newArray(int i) {
                return new android.content.pm.PackageInstaller.InstallConstraintsResult[i];
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.content.pm.PackageInstaller.InstallConstraintsResult createFromParcel(android.os.Parcel parcel) {
                return new android.content.pm.PackageInstaller.InstallConstraintsResult(parcel);
            }
        };
        private boolean mAllConstraintsSatisfied;

        public boolean areAllConstraintsSatisfied() {
            return this.mAllConstraintsSatisfied;
        }

        public InstallConstraintsResult(boolean z) {
            this.mAllConstraintsSatisfied = z;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(android.os.Parcel parcel, int i) {
            parcel.writeByte(this.mAllConstraintsSatisfied ? (byte) 1 : (byte) 0);
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        InstallConstraintsResult(android.os.Parcel parcel) {
            this.mAllConstraintsSatisfied = (parcel.readByte() & 1) != 0;
        }

        @java.lang.Deprecated
        private void __metadata() {
        }
    }

    public static final class InstallConstraints implements android.os.Parcelable {
        private final boolean mAppNotForegroundRequired;
        private final boolean mAppNotInteractingRequired;
        private final boolean mAppNotTopVisibleRequired;
        private final boolean mDeviceIdleRequired;
        private final boolean mNotInCallRequired;
        public static final android.content.pm.PackageInstaller.InstallConstraints GENTLE_UPDATE = new android.content.pm.PackageInstaller.InstallConstraints.Builder().setAppNotInteractingRequired().build();
        public static final android.os.Parcelable.Creator<android.content.pm.PackageInstaller.InstallConstraints> CREATOR = new android.os.Parcelable.Creator<android.content.pm.PackageInstaller.InstallConstraints>() { // from class: android.content.pm.PackageInstaller.InstallConstraints.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.content.pm.PackageInstaller.InstallConstraints[] newArray(int i) {
                return new android.content.pm.PackageInstaller.InstallConstraints[i];
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.content.pm.PackageInstaller.InstallConstraints createFromParcel(android.os.Parcel parcel) {
                return new android.content.pm.PackageInstaller.InstallConstraints(parcel);
            }
        };

        public static final class Builder {
            private boolean mAppNotForegroundRequired;
            private boolean mAppNotInteractingRequired;
            private boolean mAppNotTopVisibleRequired;
            private boolean mDeviceIdleRequired;
            private boolean mNotInCallRequired;

            public android.content.pm.PackageInstaller.InstallConstraints.Builder setDeviceIdleRequired() {
                this.mDeviceIdleRequired = true;
                return this;
            }

            public android.content.pm.PackageInstaller.InstallConstraints.Builder setAppNotForegroundRequired() {
                this.mAppNotForegroundRequired = true;
                return this;
            }

            public android.content.pm.PackageInstaller.InstallConstraints.Builder setAppNotInteractingRequired() {
                this.mAppNotInteractingRequired = true;
                return this;
            }

            public android.content.pm.PackageInstaller.InstallConstraints.Builder setAppNotTopVisibleRequired() {
                this.mAppNotTopVisibleRequired = true;
                return this;
            }

            public android.content.pm.PackageInstaller.InstallConstraints.Builder setNotInCallRequired() {
                this.mNotInCallRequired = true;
                return this;
            }

            public android.content.pm.PackageInstaller.InstallConstraints build() {
                return new android.content.pm.PackageInstaller.InstallConstraints(this.mDeviceIdleRequired, this.mAppNotForegroundRequired, this.mAppNotInteractingRequired, this.mAppNotTopVisibleRequired, this.mNotInCallRequired);
            }
        }

        public InstallConstraints(boolean z, boolean z2, boolean z3, boolean z4, boolean z5) {
            this.mDeviceIdleRequired = z;
            this.mAppNotForegroundRequired = z2;
            this.mAppNotInteractingRequired = z3;
            this.mAppNotTopVisibleRequired = z4;
            this.mNotInCallRequired = z5;
        }

        public boolean isDeviceIdleRequired() {
            return this.mDeviceIdleRequired;
        }

        public boolean isAppNotForegroundRequired() {
            return this.mAppNotForegroundRequired;
        }

        public boolean isAppNotInteractingRequired() {
            return this.mAppNotInteractingRequired;
        }

        public boolean isAppNotTopVisibleRequired() {
            return this.mAppNotTopVisibleRequired;
        }

        public boolean isNotInCallRequired() {
            return this.mNotInCallRequired;
        }

        public boolean equals(java.lang.Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            android.content.pm.PackageInstaller.InstallConstraints installConstraints = (android.content.pm.PackageInstaller.InstallConstraints) obj;
            if (this.mDeviceIdleRequired == installConstraints.mDeviceIdleRequired && this.mAppNotForegroundRequired == installConstraints.mAppNotForegroundRequired && this.mAppNotInteractingRequired == installConstraints.mAppNotInteractingRequired && this.mAppNotTopVisibleRequired == installConstraints.mAppNotTopVisibleRequired && this.mNotInCallRequired == installConstraints.mNotInCallRequired) {
                return true;
            }
            return false;
        }

        public int hashCode() {
            return ((((((((java.lang.Boolean.hashCode(this.mDeviceIdleRequired) + 31) * 31) + java.lang.Boolean.hashCode(this.mAppNotForegroundRequired)) * 31) + java.lang.Boolean.hashCode(this.mAppNotInteractingRequired)) * 31) + java.lang.Boolean.hashCode(this.mAppNotTopVisibleRequired)) * 31) + java.lang.Boolean.hashCode(this.mNotInCallRequired);
        }

        @Override // android.os.Parcelable
        public void writeToParcel(android.os.Parcel parcel, int i) {
            byte b = this.mDeviceIdleRequired ? (byte) 1 : (byte) 0;
            if (this.mAppNotForegroundRequired) {
                b = (byte) (b | 2);
            }
            if (this.mAppNotInteractingRequired) {
                b = (byte) (b | 4);
            }
            if (this.mAppNotTopVisibleRequired) {
                b = (byte) (b | 8);
            }
            if (this.mNotInCallRequired) {
                b = (byte) (b | 16);
            }
            parcel.writeByte(b);
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        InstallConstraints(android.os.Parcel parcel) {
            byte readByte = parcel.readByte();
            boolean z = (readByte & 1) != 0;
            boolean z2 = (readByte & 2) != 0;
            boolean z3 = (readByte & 4) != 0;
            boolean z4 = (readByte & 8) != 0;
            boolean z5 = (readByte & 16) != 0;
            this.mDeviceIdleRequired = z;
            this.mAppNotForegroundRequired = z2;
            this.mAppNotInteractingRequired = z3;
            this.mAppNotTopVisibleRequired = z4;
            this.mNotInCallRequired = z5;
        }

        @java.lang.Deprecated
        private void __metadata() {
        }
    }

    public static final class UnarchivalState {
        private final long mRequiredStorageBytes;
        private final int mStatus;
        private final int mUnarchiveId;
        private final android.app.PendingIntent mUserActionIntent;

        public static android.content.pm.PackageInstaller.UnarchivalState createOkState(int i) {
            return new android.content.pm.PackageInstaller.UnarchivalState(i, 0, -1L, null);
        }

        public static android.content.pm.PackageInstaller.UnarchivalState createUserActionRequiredState(int i, android.app.PendingIntent pendingIntent) {
            java.util.Objects.requireNonNull(pendingIntent);
            return new android.content.pm.PackageInstaller.UnarchivalState(i, 1, -1L, pendingIntent);
        }

        public static android.content.pm.PackageInstaller.UnarchivalState createInsufficientStorageState(int i, long j, android.app.PendingIntent pendingIntent) {
            return new android.content.pm.PackageInstaller.UnarchivalState(i, 2, j, pendingIntent);
        }

        public static android.content.pm.PackageInstaller.UnarchivalState createNoConnectivityState(int i) {
            return new android.content.pm.PackageInstaller.UnarchivalState(i, 3, -1L, null);
        }

        public static android.content.pm.PackageInstaller.UnarchivalState createGenericErrorState(int i) {
            return new android.content.pm.PackageInstaller.UnarchivalState(i, 100, -1L, null);
        }

        private UnarchivalState(int i, int i2, long j, android.app.PendingIntent pendingIntent) {
            this.mUnarchiveId = i;
            this.mStatus = i2;
            com.android.internal.util.AnnotationValidations.validate((java.lang.Class<? extends java.lang.annotation.Annotation>) android.content.pm.PackageInstaller.UnarchivalStatus.class, (java.lang.annotation.Annotation) null, this.mStatus);
            this.mRequiredStorageBytes = j;
            this.mUserActionIntent = pendingIntent;
        }

        int getUnarchiveId() {
            return this.mUnarchiveId;
        }

        int getStatus() {
            return this.mStatus;
        }

        long getRequiredStorageBytes() {
            return this.mRequiredStorageBytes;
        }

        android.app.PendingIntent getUserActionIntent() {
            return this.mUserActionIntent;
        }
    }
}
