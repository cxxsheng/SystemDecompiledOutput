package com.android.server.content;

/* loaded from: classes.dex */
public class SyncOperation {
    public static final int NO_JOB_ID = -1;
    public static final int REASON_ACCOUNTS_UPDATED = -2;
    public static final int REASON_BACKGROUND_DATA_SETTINGS_CHANGED = -1;
    public static final int REASON_IS_SYNCABLE = -5;
    public static final int REASON_MASTER_SYNC_AUTO = -7;
    private static java.lang.String[] REASON_NAMES = {"DataSettingsChanged", "AccountsUpdated", "ServiceChanged", "Periodic", "IsSyncable", "AutoSync", "MasterSyncAuto", "UserStart"};
    public static final int REASON_PERIODIC = -4;
    public static final int REASON_SERVICE_CHANGED = -3;
    public static final int REASON_SYNC_AUTO = -6;
    public static final int REASON_USER_START = -8;
    public static final java.lang.String TAG = "SyncManager";
    public final boolean allowParallelSyncs;
    public long expectedRuntime;
    public final long flexMillis;
    public final boolean isPeriodic;
    public int jobId;
    public final java.lang.String key;
    private volatile android.os.Bundle mImmutableExtras;
    public final java.lang.String owningPackage;
    public final int owningUid;
    public final long periodMillis;
    public final int reason;
    int retries;
    boolean scheduleEjAsRegularJob;
    public final int sourcePeriodicId;
    public int syncExemptionFlag;
    public final int syncSource;
    public final com.android.server.content.SyncStorageEngine.EndPoint target;
    public java.lang.String wakeLockName;

    public SyncOperation(android.accounts.Account account, int i, int i2, java.lang.String str, int i3, int i4, java.lang.String str2, android.os.Bundle bundle, boolean z, int i5) {
        this(new com.android.server.content.SyncStorageEngine.EndPoint(account, str2, i), i2, str, i3, i4, bundle, z, i5);
    }

    private SyncOperation(com.android.server.content.SyncStorageEngine.EndPoint endPoint, int i, java.lang.String str, int i2, int i3, android.os.Bundle bundle, boolean z, int i4) {
        this(endPoint, i, str, i2, i3, bundle, z, false, -1, 0L, 0L, i4);
    }

    public SyncOperation(com.android.server.content.SyncOperation syncOperation, long j, long j2) {
        this(syncOperation.target, syncOperation.owningUid, syncOperation.owningPackage, syncOperation.reason, syncOperation.syncSource, syncOperation.mImmutableExtras, syncOperation.allowParallelSyncs, syncOperation.isPeriodic, syncOperation.sourcePeriodicId, j, j2, 0);
    }

    public SyncOperation(com.android.server.content.SyncStorageEngine.EndPoint endPoint, int i, java.lang.String str, int i2, int i3, android.os.Bundle bundle, boolean z, boolean z2, int i4, long j, long j2, int i5) {
        this.target = endPoint;
        this.owningUid = i;
        this.owningPackage = str;
        this.reason = i2;
        this.syncSource = i3;
        this.mImmutableExtras = new android.os.Bundle(bundle);
        this.allowParallelSyncs = z;
        this.isPeriodic = z2;
        this.sourcePeriodicId = i4;
        this.periodMillis = j;
        this.flexMillis = j2;
        this.jobId = -1;
        this.key = toKey();
        this.syncExemptionFlag = i5;
    }

    public com.android.server.content.SyncOperation createOneTimeSyncOperation() {
        if (!this.isPeriodic) {
            return null;
        }
        return new com.android.server.content.SyncOperation(this.target, this.owningUid, this.owningPackage, this.reason, this.syncSource, this.mImmutableExtras, this.allowParallelSyncs, false, this.jobId, this.periodMillis, this.flexMillis, 0);
    }

    public SyncOperation(com.android.server.content.SyncOperation syncOperation) {
        this.target = syncOperation.target;
        this.owningUid = syncOperation.owningUid;
        this.owningPackage = syncOperation.owningPackage;
        this.reason = syncOperation.reason;
        this.syncSource = syncOperation.syncSource;
        this.allowParallelSyncs = syncOperation.allowParallelSyncs;
        this.mImmutableExtras = syncOperation.mImmutableExtras;
        this.wakeLockName = syncOperation.wakeLockName();
        this.isPeriodic = syncOperation.isPeriodic;
        this.sourcePeriodicId = syncOperation.sourcePeriodicId;
        this.periodMillis = syncOperation.periodMillis;
        this.flexMillis = syncOperation.flexMillis;
        this.key = syncOperation.key;
        this.syncExemptionFlag = syncOperation.syncExemptionFlag;
    }

    android.os.PersistableBundle toJobInfoExtras() {
        android.os.PersistableBundle persistableBundle = new android.os.PersistableBundle();
        android.os.PersistableBundle persistableBundle2 = new android.os.PersistableBundle();
        android.os.Bundle bundle = this.mImmutableExtras;
        for (java.lang.String str : bundle.keySet()) {
            java.lang.Object obj = bundle.get(str);
            if (obj instanceof android.accounts.Account) {
                android.accounts.Account account = (android.accounts.Account) obj;
                android.os.PersistableBundle persistableBundle3 = new android.os.PersistableBundle();
                persistableBundle3.putString("accountName", account.name);
                persistableBundle3.putString("accountType", account.type);
                persistableBundle.putPersistableBundle("ACCOUNT:" + str, persistableBundle3);
            } else if (obj instanceof java.lang.Long) {
                persistableBundle2.putLong(str, ((java.lang.Long) obj).longValue());
            } else if (obj instanceof java.lang.Integer) {
                persistableBundle2.putInt(str, ((java.lang.Integer) obj).intValue());
            } else if (obj instanceof java.lang.Boolean) {
                persistableBundle2.putBoolean(str, ((java.lang.Boolean) obj).booleanValue());
            } else if (obj instanceof java.lang.Float) {
                persistableBundle2.putDouble(str, ((java.lang.Float) obj).floatValue());
            } else if (obj instanceof java.lang.Double) {
                persistableBundle2.putDouble(str, ((java.lang.Double) obj).doubleValue());
            } else if (obj instanceof java.lang.String) {
                persistableBundle2.putString(str, (java.lang.String) obj);
            } else if (obj == null) {
                persistableBundle2.putString(str, null);
            } else {
                android.util.Slog.e(TAG, "Unknown extra type.");
            }
        }
        persistableBundle.putPersistableBundle("syncExtras", persistableBundle2);
        persistableBundle.putBoolean("SyncManagerJob", true);
        persistableBundle.putString("provider", this.target.provider);
        persistableBundle.putString("accountName", this.target.account.name);
        persistableBundle.putString("accountType", this.target.account.type);
        persistableBundle.putInt("userId", this.target.userId);
        persistableBundle.putInt("owningUid", this.owningUid);
        persistableBundle.putString("owningPackage", this.owningPackage);
        persistableBundle.putInt(com.android.server.policy.PhoneWindowManager.SYSTEM_DIALOG_REASON_KEY, this.reason);
        persistableBundle.putInt("source", this.syncSource);
        persistableBundle.putBoolean("allowParallelSyncs", this.allowParallelSyncs);
        persistableBundle.putInt("jobId", this.jobId);
        persistableBundle.putBoolean("isPeriodic", this.isPeriodic);
        persistableBundle.putInt("sourcePeriodicId", this.sourcePeriodicId);
        persistableBundle.putLong("periodMillis", this.periodMillis);
        persistableBundle.putLong("flexMillis", this.flexMillis);
        persistableBundle.putLong("expectedRuntime", this.expectedRuntime);
        persistableBundle.putInt("retries", this.retries);
        persistableBundle.putInt("syncExemptionFlag", this.syncExemptionFlag);
        persistableBundle.putBoolean("ejDowngradedToRegular", this.scheduleEjAsRegularJob);
        return persistableBundle;
    }

    static com.android.server.content.SyncOperation maybeCreateFromJobExtras(android.os.PersistableBundle persistableBundle) {
        java.util.Iterator<java.lang.String> it;
        if (persistableBundle == null || !persistableBundle.getBoolean("SyncManagerJob", false)) {
            return null;
        }
        java.lang.String string = persistableBundle.getString("accountName");
        java.lang.String string2 = persistableBundle.getString("accountType");
        java.lang.String string3 = persistableBundle.getString("provider");
        int i = persistableBundle.getInt("userId", Integer.MAX_VALUE);
        int i2 = persistableBundle.getInt("owningUid");
        java.lang.String string4 = persistableBundle.getString("owningPackage");
        int i3 = persistableBundle.getInt(com.android.server.policy.PhoneWindowManager.SYSTEM_DIALOG_REASON_KEY, Integer.MAX_VALUE);
        int i4 = persistableBundle.getInt("source", Integer.MAX_VALUE);
        boolean z = persistableBundle.getBoolean("allowParallelSyncs", false);
        boolean z2 = persistableBundle.getBoolean("isPeriodic", false);
        int i5 = persistableBundle.getInt("sourcePeriodicId", -1);
        long j = persistableBundle.getLong("periodMillis");
        long j2 = persistableBundle.getLong("flexMillis");
        int i6 = persistableBundle.getInt("syncExemptionFlag", 0);
        android.os.Bundle bundle = new android.os.Bundle();
        android.os.PersistableBundle persistableBundle2 = persistableBundle.getPersistableBundle("syncExtras");
        if (persistableBundle2 != null) {
            bundle.putAll(persistableBundle2);
        }
        java.util.Iterator<java.lang.String> it2 = persistableBundle.keySet().iterator();
        while (it2.hasNext()) {
            java.lang.String next = it2.next();
            if (next == null || !next.startsWith("ACCOUNT:")) {
                it = it2;
            } else {
                java.lang.String substring = next.substring(8);
                android.os.PersistableBundle persistableBundle3 = persistableBundle.getPersistableBundle(next);
                it = it2;
                bundle.putParcelable(substring, new android.accounts.Account(persistableBundle3.getString("accountName"), persistableBundle3.getString("accountType")));
            }
            it2 = it;
        }
        com.android.server.content.SyncOperation syncOperation = new com.android.server.content.SyncOperation(new com.android.server.content.SyncStorageEngine.EndPoint(new android.accounts.Account(string, string2), string3, i), i2, string4, i3, i4, bundle, z, z2, i5, j, j2, i6);
        syncOperation.jobId = persistableBundle.getInt("jobId");
        syncOperation.expectedRuntime = persistableBundle.getLong("expectedRuntime");
        syncOperation.retries = persistableBundle.getInt("retries");
        syncOperation.scheduleEjAsRegularJob = persistableBundle.getBoolean("ejDowngradedToRegular");
        return syncOperation;
    }

    boolean isConflict(com.android.server.content.SyncOperation syncOperation) {
        com.android.server.content.SyncStorageEngine.EndPoint endPoint = syncOperation.target;
        return this.target.account.type.equals(endPoint.account.type) && this.target.provider.equals(endPoint.provider) && this.target.userId == endPoint.userId && (!this.allowParallelSyncs || this.target.account.name.equals(endPoint.account.name));
    }

    boolean isReasonPeriodic() {
        return this.reason == -4;
    }

    boolean matchesPeriodicOperation(com.android.server.content.SyncOperation syncOperation) {
        return this.target.matchesSpec(syncOperation.target) && com.android.server.content.SyncManager.syncExtrasEquals(this.mImmutableExtras, syncOperation.mImmutableExtras, true) && this.periodMillis == syncOperation.periodMillis && this.flexMillis == syncOperation.flexMillis;
    }

    boolean isDerivedFromFailedPeriodicSync() {
        return this.sourcePeriodicId != -1;
    }

    int getJobBias() {
        if (isInitialization()) {
            return 20;
        }
        if (isExpedited()) {
            return 10;
        }
        return 0;
    }

    private java.lang.String toKey() {
        android.os.Bundle bundle = this.mImmutableExtras;
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append("provider: ");
        sb.append(this.target.provider);
        sb.append(" account {name=" + this.target.account.name + ", user=" + this.target.userId + ", type=" + this.target.account.type + "}");
        sb.append(" isPeriodic: ");
        sb.append(this.isPeriodic);
        sb.append(" period: ");
        sb.append(this.periodMillis);
        sb.append(" flex: ");
        sb.append(this.flexMillis);
        sb.append(" extras: ");
        extrasToStringBuilder(bundle, sb);
        return sb.toString();
    }

    public java.lang.String toString() {
        return dump(null, true, null, false);
    }

    public java.lang.String toSafeString() {
        return dump(null, true, null, true);
    }

    java.lang.String dump(android.content.pm.PackageManager packageManager, boolean z, com.android.server.content.SyncAdapterStateFetcher syncAdapterStateFetcher, boolean z2) {
        android.os.Bundle bundle = this.mImmutableExtras;
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append("JobId=");
        sb.append(this.jobId);
        sb.append(" ");
        sb.append(z2 ? "***" : this.target.account.name);
        sb.append(com.android.server.slice.SliceClientPermissions.SliceAuthority.DELIMITER);
        sb.append(this.target.account.type);
        sb.append(" u");
        sb.append(this.target.userId);
        sb.append(" [");
        sb.append(this.target.provider);
        sb.append("] ");
        sb.append(com.android.server.content.SyncStorageEngine.SOURCES[this.syncSource]);
        if (this.expectedRuntime != 0) {
            sb.append(" ExpectedIn=");
            com.android.server.content.SyncManager.formatDurationHMS(sb, this.expectedRuntime - android.os.SystemClock.elapsedRealtime());
        }
        if (bundle.getBoolean("expedited", false)) {
            sb.append(" EXPEDITED");
        }
        if (bundle.getBoolean("schedule_as_expedited_job", false)) {
            sb.append(" EXPEDITED-JOB");
            if (this.scheduleEjAsRegularJob) {
                sb.append("(scheduled-as-regular)");
            }
        }
        switch (this.syncExemptionFlag) {
            case 0:
                break;
            case 1:
                sb.append(" STANDBY-EXEMPTED");
                break;
            case 2:
                sb.append(" STANDBY-EXEMPTED(TOP)");
                break;
            default:
                sb.append(" ExemptionFlag=" + this.syncExemptionFlag);
                break;
        }
        sb.append(" Reason=");
        sb.append(reasonToString(packageManager, this.reason));
        if (this.isPeriodic) {
            sb.append(" (period=");
            com.android.server.content.SyncManager.formatDurationHMS(sb, this.periodMillis);
            sb.append(" flex=");
            com.android.server.content.SyncManager.formatDurationHMS(sb, this.flexMillis);
            sb.append(")");
        }
        if (this.retries > 0) {
            sb.append(" Retries=");
            sb.append(this.retries);
        }
        if (!z) {
            sb.append(" Owner={");
            android.os.UserHandle.formatUid(sb, this.owningUid);
            sb.append(" ");
            sb.append(this.owningPackage);
            if (syncAdapterStateFetcher != null) {
                sb.append(" [");
                sb.append(syncAdapterStateFetcher.getStandbyBucket(android.os.UserHandle.getUserId(this.owningUid), this.owningPackage));
                sb.append("]");
                if (syncAdapterStateFetcher.isAppActive(this.owningUid)) {
                    sb.append(" [ACTIVE]");
                }
            }
            sb.append("}");
            if (!bundle.keySet().isEmpty()) {
                sb.append(" ");
                extrasToStringBuilder(bundle, sb);
            }
        }
        return sb.toString();
    }

    static java.lang.String reasonToString(android.content.pm.PackageManager packageManager, int i) {
        if (i < 0) {
            int i2 = (-i) - 1;
            if (i2 >= REASON_NAMES.length) {
                return java.lang.String.valueOf(i);
            }
            return REASON_NAMES[i2];
        }
        if (packageManager != null) {
            java.lang.String[] packagesForUid = packageManager.getPackagesForUid(i);
            if (packagesForUid != null && packagesForUid.length == 1) {
                return packagesForUid[0];
            }
            java.lang.String nameForUid = packageManager.getNameForUid(i);
            if (nameForUid != null) {
                return nameForUid;
            }
            return java.lang.String.valueOf(i);
        }
        return java.lang.String.valueOf(i);
    }

    boolean isInitialization() {
        return this.mImmutableExtras.getBoolean("initialize", false);
    }

    boolean isExpedited() {
        return this.mImmutableExtras.getBoolean("expedited", false);
    }

    boolean isUpload() {
        return this.mImmutableExtras.getBoolean("upload", false);
    }

    void enableTwoWaySync() {
        removeExtra("upload");
    }

    boolean hasIgnoreBackoff() {
        return this.mImmutableExtras.getBoolean("ignore_backoff", false);
    }

    void enableBackoff() {
        removeExtra("ignore_backoff");
    }

    boolean hasDoNotRetry() {
        return this.mImmutableExtras.getBoolean("do_not_retry", false);
    }

    boolean isNotAllowedOnMetered() {
        return this.mImmutableExtras.getBoolean("allow_metered", false);
    }

    boolean isManual() {
        return this.mImmutableExtras.getBoolean("force", false);
    }

    boolean isIgnoreSettings() {
        return this.mImmutableExtras.getBoolean("ignore_settings", false);
    }

    boolean hasRequireCharging() {
        return this.mImmutableExtras.getBoolean("require_charging", false);
    }

    boolean isScheduledAsExpeditedJob() {
        return this.mImmutableExtras.getBoolean("schedule_as_expedited_job", false);
    }

    boolean isAppStandbyExempted() {
        return this.syncExemptionFlag != 0;
    }

    boolean areExtrasEqual(android.os.Bundle bundle, boolean z) {
        return com.android.server.content.SyncManager.syncExtrasEquals(this.mImmutableExtras, bundle, z);
    }

    static void extrasToStringBuilder(android.os.Bundle bundle, java.lang.StringBuilder sb) {
        if (bundle == null) {
            sb.append("null");
            return;
        }
        sb.append("[");
        for (java.lang.String str : bundle.keySet()) {
            sb.append(str);
            sb.append("=");
            sb.append(bundle.get(str));
            sb.append(" ");
        }
        sb.append("]");
    }

    private static java.lang.String extrasToString(android.os.Bundle bundle) {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        extrasToStringBuilder(bundle, sb);
        return sb.toString();
    }

    java.lang.String wakeLockName() {
        if (this.wakeLockName != null) {
            return this.wakeLockName;
        }
        java.lang.String str = this.target.provider + com.android.server.slice.SliceClientPermissions.SliceAuthority.DELIMITER + this.target.account.type;
        this.wakeLockName = str;
        return str;
    }

    public java.lang.Object[] toEventLog(int i) {
        return new java.lang.Object[]{this.target.provider, java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(this.syncSource), java.lang.Integer.valueOf(this.target.account.name.hashCode())};
    }

    private void removeExtra(java.lang.String str) {
        android.os.Bundle bundle = this.mImmutableExtras;
        if (!bundle.containsKey(str)) {
            return;
        }
        android.os.Bundle bundle2 = new android.os.Bundle(bundle);
        bundle2.remove(str);
        this.mImmutableExtras = bundle2;
    }

    public android.os.Bundle getClonedExtras() {
        return new android.os.Bundle(this.mImmutableExtras);
    }

    public java.lang.String getExtrasAsString() {
        return extrasToString(this.mImmutableExtras);
    }
}
