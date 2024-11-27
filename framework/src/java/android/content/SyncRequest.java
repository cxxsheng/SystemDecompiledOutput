package android.content;

/* loaded from: classes.dex */
public class SyncRequest implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.content.SyncRequest> CREATOR = new android.os.Parcelable.Creator<android.content.SyncRequest>() { // from class: android.content.SyncRequest.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.content.SyncRequest createFromParcel(android.os.Parcel parcel) {
            return new android.content.SyncRequest(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.content.SyncRequest[] newArray(int i) {
            return new android.content.SyncRequest[i];
        }
    };
    private static final java.lang.String TAG = "SyncRequest";
    private final android.accounts.Account mAccountToSync;
    private final java.lang.String mAuthority;
    private final boolean mDisallowMetered;
    private final android.os.Bundle mExtras;
    private final boolean mIsAuthority;
    private final boolean mIsExpedited;
    private final boolean mIsPeriodic;
    private final boolean mIsScheduledAsExpeditedJob;
    private final long mSyncFlexTimeSecs;
    private final long mSyncRunTimeSecs;

    public boolean isPeriodic() {
        return this.mIsPeriodic;
    }

    public boolean isExpedited() {
        return this.mIsExpedited;
    }

    public boolean isScheduledAsExpeditedJob() {
        return this.mIsScheduledAsExpeditedJob;
    }

    public android.accounts.Account getAccount() {
        return this.mAccountToSync;
    }

    public java.lang.String getProvider() {
        return this.mAuthority;
    }

    public android.os.Bundle getBundle() {
        return this.mExtras;
    }

    public long getSyncFlexTime() {
        return this.mSyncFlexTimeSecs;
    }

    public long getSyncRunTime() {
        return this.mSyncRunTimeSecs;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeBundle(this.mExtras);
        parcel.writeLong(this.mSyncFlexTimeSecs);
        parcel.writeLong(this.mSyncRunTimeSecs);
        parcel.writeInt(this.mIsPeriodic ? 1 : 0);
        parcel.writeInt(this.mDisallowMetered ? 1 : 0);
        parcel.writeInt(this.mIsAuthority ? 1 : 0);
        parcel.writeInt(this.mIsExpedited ? 1 : 0);
        parcel.writeInt(this.mIsScheduledAsExpeditedJob ? 1 : 0);
        parcel.writeParcelable(this.mAccountToSync, i);
        parcel.writeString(this.mAuthority);
    }

    private SyncRequest(android.os.Parcel parcel) {
        this.mExtras = android.os.Bundle.setDefusable(parcel.readBundle(), true);
        this.mSyncFlexTimeSecs = parcel.readLong();
        this.mSyncRunTimeSecs = parcel.readLong();
        this.mIsPeriodic = parcel.readInt() != 0;
        this.mDisallowMetered = parcel.readInt() != 0;
        this.mIsAuthority = parcel.readInt() != 0;
        this.mIsExpedited = parcel.readInt() != 0;
        this.mIsScheduledAsExpeditedJob = parcel.readInt() != 0;
        this.mAccountToSync = (android.accounts.Account) parcel.readParcelable(null, android.accounts.Account.class);
        this.mAuthority = parcel.readString();
    }

    protected SyncRequest(android.content.SyncRequest.Builder builder) {
        this.mSyncFlexTimeSecs = builder.mSyncFlexTimeSecs;
        this.mSyncRunTimeSecs = builder.mSyncRunTimeSecs;
        this.mAccountToSync = builder.mAccount;
        this.mAuthority = builder.mAuthority;
        this.mIsPeriodic = builder.mSyncType == 1;
        this.mIsAuthority = builder.mSyncTarget == 2;
        this.mIsExpedited = builder.mExpedited;
        this.mIsScheduledAsExpeditedJob = builder.mScheduleAsExpeditedJob;
        this.mExtras = new android.os.Bundle(builder.mCustomExtras);
        this.mExtras.putAll(builder.mSyncConfigExtras);
        this.mDisallowMetered = builder.mDisallowMetered;
    }

    public static class Builder {
        private static final int SYNC_TARGET_ADAPTER = 2;
        private static final int SYNC_TARGET_UNKNOWN = 0;
        private static final int SYNC_TYPE_ONCE = 2;
        private static final int SYNC_TYPE_PERIODIC = 1;
        private static final int SYNC_TYPE_UNKNOWN = 0;
        private android.accounts.Account mAccount;
        private java.lang.String mAuthority;
        private android.os.Bundle mCustomExtras;
        private boolean mDisallowMetered;
        private boolean mExpedited;
        private boolean mIgnoreBackoff;
        private boolean mIgnoreSettings;
        private boolean mIsManual;
        private boolean mNoRetry;
        private boolean mRequiresCharging;
        private boolean mScheduleAsExpeditedJob;
        private android.os.Bundle mSyncConfigExtras;
        private long mSyncFlexTimeSecs;
        private long mSyncRunTimeSecs;
        private int mSyncType = 0;
        private int mSyncTarget = 0;

        public android.content.SyncRequest.Builder syncOnce() {
            if (this.mSyncType != 0) {
                throw new java.lang.IllegalArgumentException("Sync type has already been defined.");
            }
            this.mSyncType = 2;
            setupInterval(0L, 0L);
            return this;
        }

        public android.content.SyncRequest.Builder syncPeriodic(long j, long j2) {
            if (this.mSyncType != 0) {
                throw new java.lang.IllegalArgumentException("Sync type has already been defined.");
            }
            this.mSyncType = 1;
            setupInterval(j, j2);
            return this;
        }

        private void setupInterval(long j, long j2) {
            if (j2 > j) {
                throw new java.lang.IllegalArgumentException("Specified run time for the sync must be after the specified flex time.");
            }
            this.mSyncRunTimeSecs = j;
            this.mSyncFlexTimeSecs = j2;
        }

        public android.content.SyncRequest.Builder setDisallowMetered(boolean z) {
            if (this.mIgnoreSettings && z) {
                throw new java.lang.IllegalArgumentException("setDisallowMetered(true) after having specified that settings are ignored.");
            }
            this.mDisallowMetered = z;
            return this;
        }

        public android.content.SyncRequest.Builder setRequiresCharging(boolean z) {
            this.mRequiresCharging = z;
            return this;
        }

        public android.content.SyncRequest.Builder setSyncAdapter(android.accounts.Account account, java.lang.String str) {
            if (this.mSyncTarget != 0) {
                throw new java.lang.IllegalArgumentException("Sync target has already been defined.");
            }
            if (str != null && str.length() == 0) {
                throw new java.lang.IllegalArgumentException("Authority must be non-empty");
            }
            this.mSyncTarget = 2;
            this.mAccount = account;
            this.mAuthority = str;
            return this;
        }

        public android.content.SyncRequest.Builder setExtras(android.os.Bundle bundle) {
            this.mCustomExtras = bundle;
            return this;
        }

        public android.content.SyncRequest.Builder setNoRetry(boolean z) {
            this.mNoRetry = z;
            return this;
        }

        public android.content.SyncRequest.Builder setIgnoreSettings(boolean z) {
            if (this.mDisallowMetered && z) {
                throw new java.lang.IllegalArgumentException("setIgnoreSettings(true) after having specified sync settings with this builder.");
            }
            this.mIgnoreSettings = z;
            return this;
        }

        public android.content.SyncRequest.Builder setIgnoreBackoff(boolean z) {
            this.mIgnoreBackoff = z;
            return this;
        }

        public android.content.SyncRequest.Builder setManual(boolean z) {
            this.mIsManual = z;
            return this;
        }

        public android.content.SyncRequest.Builder setExpedited(boolean z) {
            this.mExpedited = z;
            return this;
        }

        public android.content.SyncRequest.Builder setScheduleAsExpeditedJob(boolean z) {
            this.mScheduleAsExpeditedJob = z;
            return this;
        }

        public android.content.SyncRequest build() {
            this.mSyncConfigExtras = new android.os.Bundle();
            if (this.mIgnoreBackoff) {
                this.mSyncConfigExtras.putBoolean(android.content.ContentResolver.SYNC_EXTRAS_IGNORE_BACKOFF, true);
            }
            if (this.mDisallowMetered) {
                this.mSyncConfigExtras.putBoolean("allow_metered", true);
            }
            if (this.mRequiresCharging) {
                this.mSyncConfigExtras.putBoolean(android.content.ContentResolver.SYNC_EXTRAS_REQUIRE_CHARGING, true);
            }
            if (this.mIgnoreSettings) {
                this.mSyncConfigExtras.putBoolean(android.content.ContentResolver.SYNC_EXTRAS_IGNORE_SETTINGS, true);
            }
            if (this.mNoRetry) {
                this.mSyncConfigExtras.putBoolean(android.content.ContentResolver.SYNC_EXTRAS_DO_NOT_RETRY, true);
            }
            if (this.mExpedited) {
                this.mSyncConfigExtras.putBoolean(android.content.ContentResolver.SYNC_EXTRAS_EXPEDITED, true);
            }
            if (this.mScheduleAsExpeditedJob) {
                this.mSyncConfigExtras.putBoolean(android.content.ContentResolver.SYNC_EXTRAS_SCHEDULE_AS_EXPEDITED_JOB, true);
            }
            if (this.mIsManual) {
                this.mSyncConfigExtras.putBoolean(android.content.ContentResolver.SYNC_EXTRAS_IGNORE_BACKOFF, true);
                this.mSyncConfigExtras.putBoolean(android.content.ContentResolver.SYNC_EXTRAS_IGNORE_SETTINGS, true);
            }
            if (this.mCustomExtras == null) {
                this.mCustomExtras = new android.os.Bundle();
            }
            android.content.ContentResolver.validateSyncExtrasBundle(this.mCustomExtras);
            if (this.mSyncType == 1 && (android.content.ContentResolver.invalidPeriodicExtras(this.mCustomExtras) || android.content.ContentResolver.invalidPeriodicExtras(this.mSyncConfigExtras))) {
                throw new java.lang.IllegalArgumentException("Illegal extras were set");
            }
            if ((this.mCustomExtras.getBoolean(android.content.ContentResolver.SYNC_EXTRAS_SCHEDULE_AS_EXPEDITED_JOB) || this.mScheduleAsExpeditedJob) && (android.content.ContentResolver.hasInvalidScheduleAsEjExtras(this.mCustomExtras) || android.content.ContentResolver.hasInvalidScheduleAsEjExtras(this.mSyncConfigExtras))) {
                throw new java.lang.IllegalArgumentException("Illegal extras were set");
            }
            if (this.mSyncTarget == 0) {
                throw new java.lang.IllegalArgumentException("Must specify an adapter with setSyncAdapter(Account, String");
            }
            return new android.content.SyncRequest(this);
        }
    }
}
