package com.android.server.rollback;

/* loaded from: classes2.dex */
class Rollback {
    static final int ROLLBACK_STATE_AVAILABLE = 1;
    static final int ROLLBACK_STATE_COMMITTED = 3;
    static final int ROLLBACK_STATE_DELETED = 4;
    static final int ROLLBACK_STATE_ENABLING = 0;
    private static final java.lang.String TAG = "RollbackManager";
    public final android.content.rollback.RollbackInfo info;
    private final java.io.File mBackupDir;

    @android.annotation.NonNull
    private final android.util.SparseIntArray mExtensionVersions;

    @android.annotation.Nullable
    private final android.os.Handler mHandler;

    @android.annotation.Nullable
    private final java.lang.String mInstallerPackageName;
    private final int mOriginalSessionId;
    private final int[] mPackageSessionIds;
    private boolean mRestoreUserDataInProgress;
    private long mRollbackLifetimeMillis;
    private int mState;

    @android.annotation.NonNull
    private java.lang.String mStateDescription;

    @android.annotation.NonNull
    private java.time.Instant mTimestamp;
    private final int mUserId;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    @interface RollbackState {
    }

    Rollback(int i, java.io.File file, int i2, boolean z, int i3, java.lang.String str, int[] iArr, android.util.SparseIntArray sparseIntArray) {
        this.mStateDescription = "";
        this.mRestoreUserDataInProgress = false;
        this.mRollbackLifetimeMillis = 0L;
        this.info = new android.content.rollback.RollbackInfo(i, new java.util.ArrayList(), z, new java.util.ArrayList(), -1, 0);
        this.mUserId = i3;
        this.mInstallerPackageName = str;
        this.mBackupDir = file;
        this.mOriginalSessionId = i2;
        this.mState = 0;
        this.mTimestamp = java.time.Instant.now();
        this.mPackageSessionIds = iArr != null ? iArr : new int[0];
        java.util.Objects.requireNonNull(sparseIntArray);
        this.mExtensionVersions = sparseIntArray;
        this.mHandler = android.os.Looper.myLooper() != null ? new android.os.Handler(android.os.Looper.myLooper()) : null;
    }

    Rollback(android.content.rollback.RollbackInfo rollbackInfo, java.io.File file, java.time.Instant instant, int i, int i2, java.lang.String str, boolean z, int i3, java.lang.String str2, android.util.SparseIntArray sparseIntArray) {
        this.mStateDescription = "";
        this.mRestoreUserDataInProgress = false;
        this.mRollbackLifetimeMillis = 0L;
        this.info = rollbackInfo;
        this.mUserId = i3;
        this.mInstallerPackageName = str2;
        this.mBackupDir = file;
        this.mTimestamp = instant;
        this.mOriginalSessionId = i;
        this.mState = i2;
        this.mStateDescription = str;
        this.mRestoreUserDataInProgress = z;
        java.util.Objects.requireNonNull(sparseIntArray);
        this.mExtensionVersions = sparseIntArray;
        this.mPackageSessionIds = new int[0];
        this.mHandler = android.os.Looper.myLooper() != null ? new android.os.Handler(android.os.Looper.myLooper()) : null;
    }

    private void assertInWorkerThread() {
        com.android.internal.util.Preconditions.checkState(this.mHandler == null || this.mHandler.getLooper().isCurrentThread());
    }

    boolean isStaged() {
        return this.info.isStaged();
    }

    java.io.File getBackupDir() {
        return this.mBackupDir;
    }

    java.time.Instant getTimestamp() {
        assertInWorkerThread();
        return this.mTimestamp;
    }

    void setTimestamp(java.time.Instant instant) {
        assertInWorkerThread();
        this.mTimestamp = instant;
        com.android.server.rollback.RollbackStore.saveRollback(this);
    }

    void setRollbackLifetimeMillis(long j) {
        assertInWorkerThread();
        this.mRollbackLifetimeMillis = j;
    }

    long getRollbackLifetimeMillis() {
        assertInWorkerThread();
        return this.mRollbackLifetimeMillis;
    }

    int getOriginalSessionId() {
        return this.mOriginalSessionId;
    }

    int getUserId() {
        return this.mUserId;
    }

    @android.annotation.Nullable
    java.lang.String getInstallerPackageName() {
        return this.mInstallerPackageName;
    }

    android.util.SparseIntArray getExtensionVersions() {
        return this.mExtensionVersions;
    }

    boolean isEnabling() {
        assertInWorkerThread();
        return this.mState == 0;
    }

    boolean isAvailable() {
        assertInWorkerThread();
        return this.mState == 1;
    }

    boolean isCommitted() {
        assertInWorkerThread();
        return this.mState == 3;
    }

    boolean isDeleted() {
        assertInWorkerThread();
        return this.mState == 4;
    }

    void saveRollback() {
        assertInWorkerThread();
        com.android.server.rollback.RollbackStore.saveRollback(this);
    }

    boolean enableForPackage(java.lang.String str, long j, long j2, boolean z, java.lang.String str2, java.lang.String[] strArr, int i, int i2) {
        assertInWorkerThread();
        try {
            com.android.server.rollback.RollbackStore.backupPackageCodePath(this, str, str2);
            if (!com.android.internal.util.ArrayUtils.isEmpty(strArr)) {
                for (java.lang.String str3 : strArr) {
                    com.android.server.rollback.RollbackStore.backupPackageCodePath(this, str, str3);
                }
            }
            this.info.getPackages().add(new android.content.rollback.PackageRollbackInfo(new android.content.pm.VersionedPackage(str, j), new android.content.pm.VersionedPackage(str, j2), new java.util.ArrayList(), new java.util.ArrayList(), z, false, new java.util.ArrayList(), i));
            if (this.info.getRollbackImpactLevel() < i2) {
                this.info.setRollbackImpactLevel(i2);
                return true;
            }
            return true;
        } catch (java.io.IOException e) {
            android.util.Slog.e(TAG, "Unable to copy package for rollback for " + str, e);
            return false;
        }
    }

    boolean enableForPackageInApex(java.lang.String str, long j, int i) {
        assertInWorkerThread();
        this.info.getPackages().add(new android.content.rollback.PackageRollbackInfo(new android.content.pm.VersionedPackage(str, 0), new android.content.pm.VersionedPackage(str, j), new java.util.ArrayList(), new java.util.ArrayList(), false, true, new java.util.ArrayList(), i));
        return true;
    }

    private static void addAll(java.util.List<java.lang.Integer> list, int[] iArr) {
        for (int i : iArr) {
            list.add(java.lang.Integer.valueOf(i));
        }
    }

    void snapshotUserData(java.lang.String str, int[] iArr, com.android.server.rollback.AppDataRollbackHelper appDataRollbackHelper) {
        assertInWorkerThread();
        if (!isEnabling()) {
            return;
        }
        for (android.content.rollback.PackageRollbackInfo packageRollbackInfo : this.info.getPackages()) {
            if (packageRollbackInfo.getPackageName().equals(str)) {
                if (packageRollbackInfo.getRollbackDataPolicy() == 0) {
                    appDataRollbackHelper.snapshotAppData(this.info.getRollbackId(), packageRollbackInfo, iArr);
                    addAll(packageRollbackInfo.getSnapshottedUsers(), iArr);
                    com.android.server.rollback.RollbackStore.saveRollback(this);
                    return;
                }
                return;
            }
        }
    }

    void commitPendingBackupAndRestoreForUser(int i, com.android.server.rollback.AppDataRollbackHelper appDataRollbackHelper) {
        assertInWorkerThread();
        if (appDataRollbackHelper.commitPendingBackupAndRestoreForUser(i, this)) {
            com.android.server.rollback.RollbackStore.saveRollback(this);
        }
    }

    void makeAvailable() {
        assertInWorkerThread();
        if (isDeleted()) {
            android.util.Slog.w(TAG, "Cannot make deleted rollback available.");
            return;
        }
        setState(1, "");
        this.mTimestamp = java.time.Instant.now();
        com.android.server.rollback.RollbackStore.saveRollback(this);
    }

    void commit(final android.content.Context context, final java.util.List<android.content.pm.VersionedPackage> list, java.lang.String str, final android.content.IntentSender intentSender) {
        boolean z;
        java.io.File[] fileArr;
        android.content.pm.PackageInstaller.Session session;
        assertInWorkerThread();
        if (!isAvailable()) {
            com.android.server.rollback.RollbackManagerServiceImpl.sendFailure(context, intentSender, 2, "Rollback unavailable");
            return;
        }
        boolean z2 = true;
        if (containsApex() && wasCreatedAtLowerExtensionVersion()) {
            if (extensionVersionReductionWouldViolateConstraint(this.mExtensionVersions, (android.content.pm.PackageManagerInternal) com.android.server.LocalServices.getService(android.content.pm.PackageManagerInternal.class))) {
                com.android.server.rollback.RollbackManagerServiceImpl.sendFailure(context, intentSender, 1, "Rollback may violate a minExtensionVersion constraint");
                return;
            }
        }
        try {
            android.content.pm.PackageManager packageManager = context.createPackageContextAsUser(str, 0, android.os.UserHandle.of(this.mUserId)).getPackageManager();
            try {
                android.content.pm.PackageInstaller packageInstaller = packageManager.getPackageInstaller();
                android.content.pm.PackageInstaller.SessionParams sessionParams = new android.content.pm.PackageInstaller.SessionParams(1);
                sessionParams.setRequestDowngrade(true);
                sessionParams.setMultiPackage();
                if (isStaged()) {
                    sessionParams.setStaged();
                }
                int i = 5;
                sessionParams.setInstallReason(5);
                int createSession = packageInstaller.createSession(sessionParams);
                android.content.pm.PackageInstaller.Session openSession = packageInstaller.openSession(createSession);
                java.util.ArrayList arrayList = new java.util.ArrayList(this.info.getPackages().size());
                for (android.content.rollback.PackageRollbackInfo packageRollbackInfo : this.info.getPackages()) {
                    arrayList.add(packageRollbackInfo.getPackageName());
                    if (!packageRollbackInfo.isApkInApex()) {
                        android.content.pm.PackageInstaller.SessionParams sessionParams2 = new android.content.pm.PackageInstaller.SessionParams(z2 ? 1 : 0);
                        java.lang.String str2 = this.mInstallerPackageName;
                        if (android.text.TextUtils.isEmpty(this.mInstallerPackageName)) {
                            str2 = packageManager.getInstallerPackageName(packageRollbackInfo.getPackageName());
                        }
                        if (str2 != null) {
                            sessionParams2.setInstallerPackageName(str2);
                        }
                        sessionParams2.setRequestDowngrade(z2);
                        sessionParams2.setRequiredInstalledVersionCode(packageRollbackInfo.getVersionRolledBackFrom().getLongVersionCode());
                        sessionParams2.setInstallReason(i);
                        if (isStaged()) {
                            sessionParams2.setStaged();
                        }
                        if (packageRollbackInfo.isApex()) {
                            sessionParams2.setInstallAsApex();
                        }
                        int createSession2 = packageInstaller.createSession(sessionParams2);
                        android.content.pm.PackageInstaller.Session openSession2 = packageInstaller.openSession(createSession2);
                        java.io.File[] packageCodePaths = com.android.server.rollback.RollbackStore.getPackageCodePaths(this, packageRollbackInfo.getPackageName());
                        if (packageCodePaths == null) {
                            com.android.server.rollback.RollbackManagerServiceImpl.sendFailure(context, intentSender, z2 ? 1 : 0, "Backup copy of package: " + packageRollbackInfo.getPackageName() + " is inaccessible");
                            return;
                        }
                        int length = packageCodePaths.length;
                        int i2 = 0;
                        while (i2 < length) {
                            java.io.File file = packageCodePaths[i2];
                            android.os.ParcelFileDescriptor open = android.os.ParcelFileDescriptor.open(file, 268435456);
                            try {
                                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                                try {
                                    try {
                                        openSession2.stageViaHardLink(file.getAbsolutePath());
                                        z = false;
                                    } catch (java.lang.Exception e) {
                                        z = true;
                                    }
                                    if (!z) {
                                        fileArr = packageCodePaths;
                                        session = openSession2;
                                    } else {
                                        java.lang.String name = file.getName();
                                        long length2 = file.length();
                                        fileArr = packageCodePaths;
                                        session = openSession2;
                                        openSession2.write(name, 0L, length2, open);
                                    }
                                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                                    if (open != null) {
                                        open.close();
                                    }
                                    i2++;
                                    packageCodePaths = fileArr;
                                    openSession2 = session;
                                } catch (java.lang.Throwable th) {
                                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                                    throw th;
                                }
                            } finally {
                            }
                        }
                        openSession.addChildSessionId(createSession2);
                        z2 = true;
                        i = 5;
                    }
                }
                com.android.server.RescueParty.resetDeviceConfigForPackages(arrayList);
                com.android.server.rollback.LocalIntentReceiver localIntentReceiver = new com.android.server.rollback.LocalIntentReceiver(new java.util.function.Consumer() { // from class: com.android.server.rollback.Rollback$$ExternalSyntheticLambda1
                    @Override // java.util.function.Consumer
                    public final void accept(java.lang.Object obj) {
                        com.android.server.rollback.Rollback.this.lambda$commit$1(context, intentSender, list, (android.content.Intent) obj);
                    }
                });
                setState(3, "");
                this.info.setCommittedSessionId(createSession);
                this.mRestoreUserDataInProgress = true;
                openSession.commit(localIntentReceiver.getIntentSender());
            } catch (java.io.IOException e2) {
                android.util.Slog.e(TAG, "Rollback failed", e2);
                com.android.server.rollback.RollbackManagerServiceImpl.sendFailure(context, intentSender, 1, "IOException: " + e2.toString());
            }
        } catch (android.content.pm.PackageManager.NameNotFoundException e3) {
            com.android.server.rollback.RollbackManagerServiceImpl.sendFailure(context, intentSender, 1, "Invalid callerPackageName");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$commit$1(final android.content.Context context, final android.content.IntentSender intentSender, final java.util.List list, final android.content.Intent intent) {
        this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.rollback.Rollback$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.rollback.Rollback.this.lambda$commit$0(intent, context, intentSender, list);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$commit$0(android.content.Intent intent, android.content.Context context, android.content.IntentSender intentSender, java.util.List list) {
        assertInWorkerThread();
        if (intent.getIntExtra("android.content.pm.extra.STATUS", 1) != 0) {
            setState(1, "Commit failed");
            this.mRestoreUserDataInProgress = false;
            this.info.setCommittedSessionId(-1);
            com.android.server.rollback.RollbackManagerServiceImpl.sendFailure(context, intentSender, 3, "Rollback downgrade install failed: " + intent.getStringExtra("android.content.pm.extra.STATUS_MESSAGE"));
            return;
        }
        if (!isStaged()) {
            this.mRestoreUserDataInProgress = false;
        }
        this.info.getCausePackages().addAll(list);
        com.android.server.rollback.RollbackStore.deletePackageCodePaths(this);
        com.android.server.rollback.RollbackStore.saveRollback(this);
        try {
            android.content.Intent intent2 = new android.content.Intent();
            intent2.putExtra("android.content.rollback.extra.STATUS", 0);
            intentSender.sendIntent(context, 0, intent2, null, null);
        } catch (android.content.IntentSender.SendIntentException e) {
        }
        android.content.Intent intent3 = new android.content.Intent("android.intent.action.ROLLBACK_COMMITTED");
        java.util.Iterator it = ((android.os.UserManager) context.getSystemService(android.os.UserManager.class)).getUserHandles(true).iterator();
        while (it.hasNext()) {
            context.sendBroadcastAsUser(intent3, (android.os.UserHandle) it.next(), "android.permission.MANAGE_ROLLBACKS");
        }
    }

    boolean restoreUserDataForPackageIfInProgress(java.lang.String str, int[] iArr, int i, java.lang.String str2, com.android.server.rollback.AppDataRollbackHelper appDataRollbackHelper) {
        assertInWorkerThread();
        if (!isRestoreUserDataInProgress()) {
            return false;
        }
        for (android.content.rollback.PackageRollbackInfo packageRollbackInfo : this.info.getPackages()) {
            if (packageRollbackInfo.getPackageName().equals(str)) {
                boolean z = false;
                for (int i2 : iArr) {
                    z |= appDataRollbackHelper.restoreAppData(this.info.getRollbackId(), packageRollbackInfo, i2, i, str2);
                }
                if (z) {
                    com.android.server.rollback.RollbackStore.saveRollback(this);
                }
                return true;
            }
        }
        return false;
    }

    void delete(com.android.server.rollback.AppDataRollbackHelper appDataRollbackHelper, @android.annotation.NonNull java.lang.String str) {
        assertInWorkerThread();
        android.util.ArraySet arraySet = new android.util.ArraySet();
        boolean z = false;
        for (android.content.rollback.PackageRollbackInfo packageRollbackInfo : this.info.getPackages()) {
            java.util.List snapshottedUsers = packageRollbackInfo.getSnapshottedUsers();
            if (packageRollbackInfo.isApex()) {
                arraySet.addAll(snapshottedUsers);
                z = true;
            } else {
                for (int i = 0; i < snapshottedUsers.size(); i++) {
                    appDataRollbackHelper.destroyAppDataSnapshot(this.info.getRollbackId(), packageRollbackInfo, ((java.lang.Integer) snapshottedUsers.get(i)).intValue());
                }
            }
        }
        if (z) {
            appDataRollbackHelper.destroyApexDeSnapshots(this.info.getRollbackId());
            java.util.Iterator it = arraySet.iterator();
            while (it.hasNext()) {
                appDataRollbackHelper.destroyApexCeSnapshots(((java.lang.Integer) it.next()).intValue(), this.info.getRollbackId());
            }
        }
        com.android.server.rollback.RollbackStore.deleteRollback(this);
        setState(4, str);
    }

    boolean isRestoreUserDataInProgress() {
        assertInWorkerThread();
        return this.mRestoreUserDataInProgress;
    }

    void setRestoreUserDataInProgress(boolean z) {
        assertInWorkerThread();
        this.mRestoreUserDataInProgress = z;
        com.android.server.rollback.RollbackStore.saveRollback(this);
    }

    boolean includesPackage(java.lang.String str) {
        assertInWorkerThread();
        java.util.Iterator it = this.info.getPackages().iterator();
        while (it.hasNext()) {
            if (((android.content.rollback.PackageRollbackInfo) it.next()).getPackageName().equals(str)) {
                return true;
            }
        }
        return false;
    }

    boolean includesPackageWithDifferentVersion(java.lang.String str, long j) {
        assertInWorkerThread();
        for (android.content.rollback.PackageRollbackInfo packageRollbackInfo : this.info.getPackages()) {
            if (packageRollbackInfo.getPackageName().equals(str) && packageRollbackInfo.getVersionRolledBackFrom().getLongVersionCode() != j) {
                return true;
            }
        }
        return false;
    }

    java.util.List<java.lang.String> getPackageNames() {
        assertInWorkerThread();
        java.util.ArrayList arrayList = new java.util.ArrayList();
        java.util.Iterator it = this.info.getPackages().iterator();
        while (it.hasNext()) {
            arrayList.add(((android.content.rollback.PackageRollbackInfo) it.next()).getPackageName());
        }
        return arrayList;
    }

    java.util.List<java.lang.String> getApexPackageNames() {
        assertInWorkerThread();
        java.util.ArrayList arrayList = new java.util.ArrayList();
        for (android.content.rollback.PackageRollbackInfo packageRollbackInfo : this.info.getPackages()) {
            if (packageRollbackInfo.isApex()) {
                arrayList.add(packageRollbackInfo.getPackageName());
            }
        }
        return arrayList;
    }

    boolean containsSessionId(int i) {
        for (int i2 : this.mPackageSessionIds) {
            if (i2 == i) {
                return true;
            }
        }
        return false;
    }

    boolean allPackagesEnabled() {
        assertInWorkerThread();
        java.util.Iterator it = this.info.getPackages().iterator();
        int i = 0;
        while (it.hasNext()) {
            if (!((android.content.rollback.PackageRollbackInfo) it.next()).isApkInApex()) {
                i++;
            }
        }
        return i == this.mPackageSessionIds.length;
    }

    static java.lang.String rollbackStateToString(int i) {
        switch (i) {
            case 0:
                return "enabling";
            case 1:
                return "available";
            case 2:
            default:
                throw new java.lang.AssertionError("Invalid rollback state: " + i);
            case 3:
                return "committed";
            case 4:
                return "deleted";
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    static int rollbackStateFromString(java.lang.String str) throws java.text.ParseException {
        char c;
        switch (str.hashCode()) {
            case -1491142788:
                if (str.equals("committed")) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case -733902135:
                if (str.equals("available")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case 1550463001:
                if (str.equals("deleted")) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case 1642196352:
                if (str.equals("enabling")) {
                    c = 0;
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
                return 0;
            case 1:
                return 1;
            case 2:
                return 3;
            case 3:
                return 4;
            default:
                throw new java.text.ParseException("Invalid rollback state: " + str, 0);
        }
    }

    java.lang.String getStateAsString() {
        assertInWorkerThread();
        return rollbackStateToString(this.mState);
    }

    @com.android.internal.annotations.VisibleForTesting
    static boolean extensionVersionReductionWouldViolateConstraint(android.util.SparseIntArray sparseIntArray, android.content.pm.PackageManagerInternal packageManagerInternal) {
        if (sparseIntArray.size() == 0) {
            return false;
        }
        java.util.List<java.lang.String> packageNames = packageManagerInternal.getPackageList().getPackageNames();
        for (int i = 0; i < packageNames.size(); i++) {
            android.util.SparseIntArray minExtensionVersions = packageManagerInternal.getPackage(packageNames.get(i)).getMinExtensionVersions();
            if (minExtensionVersions != null) {
                for (int i2 = 0; i2 < sparseIntArray.size(); i2++) {
                    if (sparseIntArray.valueAt(i2) < minExtensionVersions.get(sparseIntArray.keyAt(i2), -1)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean wasCreatedAtLowerExtensionVersion() {
        for (int i = 0; i < this.mExtensionVersions.size(); i++) {
            if (android.os.ext.SdkExtensions.getExtensionVersion(this.mExtensionVersions.keyAt(i)) > this.mExtensionVersions.valueAt(i)) {
                return true;
            }
        }
        return false;
    }

    private boolean containsApex() {
        java.util.Iterator it = this.info.getPackages().iterator();
        while (it.hasNext()) {
            if (((android.content.rollback.PackageRollbackInfo) it.next()).isApex()) {
                return true;
            }
        }
        return false;
    }

    void dump(com.android.internal.util.IndentingPrintWriter indentingPrintWriter) {
        assertInWorkerThread();
        indentingPrintWriter.println(this.info.getRollbackId() + ":");
        indentingPrintWriter.increaseIndent();
        indentingPrintWriter.println("-state: " + getStateAsString());
        indentingPrintWriter.println("-stateDescription: " + this.mStateDescription);
        indentingPrintWriter.println("-timestamp: " + getTimestamp());
        indentingPrintWriter.println("-rollbackLifetimeMillis: " + getRollbackLifetimeMillis());
        indentingPrintWriter.println("-isStaged: " + isStaged());
        indentingPrintWriter.println("-originalSessionId: " + getOriginalSessionId());
        indentingPrintWriter.println("-packages:");
        indentingPrintWriter.increaseIndent();
        for (android.content.rollback.PackageRollbackInfo packageRollbackInfo : this.info.getPackages()) {
            indentingPrintWriter.println(packageRollbackInfo.getPackageName() + " " + packageRollbackInfo.getVersionRolledBackFrom().getLongVersionCode() + " -> " + packageRollbackInfo.getVersionRolledBackTo().getLongVersionCode() + " [" + packageRollbackInfo.getRollbackDataPolicy() + "]");
        }
        indentingPrintWriter.decreaseIndent();
        if (isCommitted()) {
            indentingPrintWriter.println("-causePackages:");
            indentingPrintWriter.increaseIndent();
            for (android.content.pm.VersionedPackage versionedPackage : this.info.getCausePackages()) {
                indentingPrintWriter.println(versionedPackage.getPackageName() + " " + versionedPackage.getLongVersionCode());
            }
            indentingPrintWriter.decreaseIndent();
            indentingPrintWriter.println("-committedSessionId: " + this.info.getCommittedSessionId());
        }
        if (this.mExtensionVersions.size() > 0) {
            indentingPrintWriter.println("-extensionVersions:");
            indentingPrintWriter.increaseIndent();
            indentingPrintWriter.println(this.mExtensionVersions.toString());
            indentingPrintWriter.decreaseIndent();
        }
        indentingPrintWriter.decreaseIndent();
    }

    java.lang.String getStateDescription() {
        assertInWorkerThread();
        return this.mStateDescription;
    }

    @com.android.internal.annotations.VisibleForTesting
    void setState(int i, java.lang.String str) {
        assertInWorkerThread();
        this.mState = i;
        this.mStateDescription = str;
    }
}
