package android.app.job;

/* loaded from: classes.dex */
public class JobInfo implements android.os.Parcelable {
    public static final int BACKOFF_POLICY_EXPONENTIAL = 1;
    public static final int BACKOFF_POLICY_LINEAR = 0;
    public static final int BIAS_ADJ_ALWAYS_RUNNING = -80;
    public static final int BIAS_ADJ_OFTEN_RUNNING = -40;
    public static final int BIAS_BOUND_FOREGROUND_SERVICE = 30;
    public static final int BIAS_DEFAULT = 0;
    public static final int BIAS_FOREGROUND_SERVICE = 35;
    public static final int BIAS_SYNC_EXPEDITED = 10;
    public static final int BIAS_SYNC_INITIALIZATION = 20;
    public static final int BIAS_TOP_APP = 40;
    public static final int CONSTRAINT_FLAG_BATTERY_NOT_LOW = 2;
    public static final int CONSTRAINT_FLAG_CHARGING = 1;
    public static final int CONSTRAINT_FLAG_DEVICE_IDLE = 4;
    public static final int CONSTRAINT_FLAG_STORAGE_NOT_LOW = 8;
    public static final int DEFAULT_BACKOFF_POLICY = 1;
    public static final long DEFAULT_INITIAL_BACKOFF_MILLIS = 30000;
    public static final long DISALLOW_DEADLINES_FOR_PREFETCH_JOBS = 194532703;
    public static final long ENFORCE_MINIMUM_TIME_WINDOWS = 311402873;
    public static final int FLAG_EXEMPT_FROM_APP_STANDBY = 8;
    public static final int FLAG_EXPEDITED = 16;
    public static final int FLAG_IMPORTANT_WHILE_FOREGROUND = 2;
    public static final int FLAG_PREFETCH = 4;
    public static final int FLAG_USER_INITIATED = 32;
    public static final int FLAG_WILL_BE_FOREGROUND = 1;
    public static final long MAX_BACKOFF_DELAY_MILLIS = 18000000;
    public static final int MAX_DEBUG_TAG_LENGTH = 127;
    public static final int MAX_NUM_DEBUG_TAGS = 32;
    public static final int MAX_TRACE_TAG_LENGTH = 127;
    private static final long MIN_ALLOWED_TIME_WINDOW_MILLIS = 900000;
    public static final long MIN_BACKOFF_MILLIS = 10000;
    private static final long MIN_FLEX_MILLIS = 300000;
    private static final long MIN_PERIOD_MILLIS = 900000;
    public static final int NETWORK_BYTES_UNKNOWN = -1;
    public static final int NETWORK_TYPE_ANY = 1;
    public static final int NETWORK_TYPE_CELLULAR = 4;

    @java.lang.Deprecated
    public static final int NETWORK_TYPE_METERED = 4;
    public static final int NETWORK_TYPE_NONE = 0;
    public static final int NETWORK_TYPE_NOT_ROAMING = 3;
    public static final int NETWORK_TYPE_UNMETERED = 2;
    public static final int PRIORITY_DEFAULT = 300;
    public static final int PRIORITY_FOREGROUND_APP = 30;
    public static final int PRIORITY_FOREGROUND_SERVICE = 35;
    public static final int PRIORITY_HIGH = 400;
    public static final int PRIORITY_LOW = 200;
    public static final int PRIORITY_MAX = 500;
    public static final int PRIORITY_MIN = 100;
    public static final long REJECT_NEGATIVE_DELAYS_AND_DEADLINES = 323349338;
    public static final long REJECT_NEGATIVE_NETWORK_ESTIMATES = 253665015;
    public static final long THROW_ON_INVALID_PRIORITY_VALUE = 140852299;
    private final int backoffPolicy;
    private final android.content.ClipData clipData;
    private final int clipGrantFlags;
    private final int constraintFlags;
    private final android.os.PersistableBundle extras;
    private final int flags;
    private final long flexMillis;
    private final boolean hasEarlyConstraint;
    private final boolean hasLateConstraint;
    private final long initialBackoffMillis;
    private final long intervalMillis;
    private final boolean isPeriodic;
    private final boolean isPersisted;
    private final int jobId;
    private final int mBias;
    private final android.util.ArraySet<java.lang.String> mDebugTags;
    private final int mPriority;
    private final java.lang.String mTraceTag;
    private final long maxExecutionDelayMillis;
    private final long minLatencyMillis;
    private final long minimumNetworkChunkBytes;
    private final long networkDownloadBytes;
    private final android.net.NetworkRequest networkRequest;
    private final long networkUploadBytes;
    private final android.content.ComponentName service;
    private final android.os.Bundle transientExtras;
    private final long triggerContentMaxDelay;
    private final long triggerContentUpdateDelay;
    private final android.app.job.JobInfo.TriggerContentUri[] triggerContentUris;
    private static java.lang.String TAG = "JobInfo";
    public static final android.os.Parcelable.Creator<android.app.job.JobInfo> CREATOR = new android.os.Parcelable.Creator<android.app.job.JobInfo>() { // from class: android.app.job.JobInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.job.JobInfo createFromParcel(android.os.Parcel parcel) {
            return new android.app.job.JobInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.job.JobInfo[] newArray(int i) {
            return new android.app.job.JobInfo[i];
        }
    };

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface BackoffPolicy {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface NetworkType {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface Priority {
    }

    public static final long getMinPeriodMillis() {
        return android.app.AlarmManager.INTERVAL_FIFTEEN_MINUTES;
    }

    public static final long getMinFlexMillis() {
        return 300000L;
    }

    public static final long getMinBackoffMillis() {
        return MIN_BACKOFF_MILLIS;
    }

    public int getId() {
        return this.jobId;
    }

    public android.os.PersistableBundle getExtras() {
        return this.extras;
    }

    public android.os.Bundle getTransientExtras() {
        return this.transientExtras;
    }

    public android.content.ClipData getClipData() {
        return this.clipData;
    }

    public int getClipGrantFlags() {
        return this.clipGrantFlags;
    }

    public android.content.ComponentName getService() {
        return this.service;
    }

    public int getBias() {
        return this.mBias;
    }

    public int getPriority() {
        return this.mPriority;
    }

    public int getFlags() {
        return this.flags;
    }

    public boolean isExemptedFromAppStandby() {
        return ((this.flags & 8) == 0 || isPeriodic()) ? false : true;
    }

    public boolean isRequireCharging() {
        return (this.constraintFlags & 1) != 0;
    }

    public boolean isRequireBatteryNotLow() {
        return (this.constraintFlags & 2) != 0;
    }

    public boolean isRequireDeviceIdle() {
        return (this.constraintFlags & 4) != 0;
    }

    public boolean isRequireStorageNotLow() {
        return (this.constraintFlags & 8) != 0;
    }

    public int getConstraintFlags() {
        return this.constraintFlags;
    }

    public android.app.job.JobInfo.TriggerContentUri[] getTriggerContentUris() {
        return this.triggerContentUris;
    }

    public long getTriggerContentUpdateDelay() {
        return this.triggerContentUpdateDelay;
    }

    public long getTriggerContentMaxDelay() {
        return this.triggerContentMaxDelay;
    }

    @java.lang.Deprecated
    public int getNetworkType() {
        if (this.networkRequest == null) {
            return 0;
        }
        if (this.networkRequest.hasCapability(11)) {
            return 2;
        }
        if (this.networkRequest.hasCapability(18)) {
            return 3;
        }
        if (this.networkRequest.hasTransport(0)) {
            return 4;
        }
        return 1;
    }

    public android.net.NetworkRequest getRequiredNetwork() {
        return this.networkRequest;
    }

    public long getEstimatedNetworkDownloadBytes() {
        return this.networkDownloadBytes;
    }

    public long getEstimatedNetworkUploadBytes() {
        return this.networkUploadBytes;
    }

    public long getMinimumNetworkChunkBytes() {
        return this.minimumNetworkChunkBytes;
    }

    public long getMinLatencyMillis() {
        return java.lang.Math.max(0L, this.minLatencyMillis);
    }

    public long getMaxExecutionDelayMillis() {
        return java.lang.Math.max(0L, this.maxExecutionDelayMillis);
    }

    public boolean isPeriodic() {
        return this.isPeriodic;
    }

    public boolean isPersisted() {
        return this.isPersisted;
    }

    public long getIntervalMillis() {
        return this.intervalMillis;
    }

    public long getFlexMillis() {
        return this.flexMillis;
    }

    public long getInitialBackoffMillis() {
        return this.initialBackoffMillis;
    }

    public int getBackoffPolicy() {
        return this.backoffPolicy;
    }

    public java.util.Set<java.lang.String> getDebugTags() {
        return java.util.Collections.unmodifiableSet(this.mDebugTags);
    }

    public android.util.ArraySet<java.lang.String> getDebugTagsArraySet() {
        return this.mDebugTags;
    }

    public java.lang.String getTraceTag() {
        return this.mTraceTag;
    }

    public boolean isExpedited() {
        return (this.flags & 16) != 0;
    }

    public boolean isUserInitiated() {
        return (this.flags & 32) != 0;
    }

    public boolean isImportantWhileForeground() {
        return (this.flags & 2) != 0;
    }

    public boolean isPrefetch() {
        return (this.flags & 4) != 0;
    }

    public boolean hasEarlyConstraint() {
        return this.hasEarlyConstraint;
    }

    public boolean hasLateConstraint() {
        return this.hasLateConstraint;
    }

    public boolean equals(java.lang.Object obj) {
        if (!(obj instanceof android.app.job.JobInfo)) {
            return false;
        }
        android.app.job.JobInfo jobInfo = (android.app.job.JobInfo) obj;
        return this.jobId == jobInfo.jobId && android.os.BaseBundle.kindofEquals(this.extras, jobInfo.extras) && android.os.BaseBundle.kindofEquals(this.transientExtras, jobInfo.transientExtras) && this.clipData == jobInfo.clipData && this.clipGrantFlags == jobInfo.clipGrantFlags && java.util.Objects.equals(this.service, jobInfo.service) && this.constraintFlags == jobInfo.constraintFlags && java.util.Arrays.equals(this.triggerContentUris, jobInfo.triggerContentUris) && this.triggerContentUpdateDelay == jobInfo.triggerContentUpdateDelay && this.triggerContentMaxDelay == jobInfo.triggerContentMaxDelay && this.hasEarlyConstraint == jobInfo.hasEarlyConstraint && this.hasLateConstraint == jobInfo.hasLateConstraint && java.util.Objects.equals(this.networkRequest, jobInfo.networkRequest) && this.networkDownloadBytes == jobInfo.networkDownloadBytes && this.networkUploadBytes == jobInfo.networkUploadBytes && this.minimumNetworkChunkBytes == jobInfo.minimumNetworkChunkBytes && this.minLatencyMillis == jobInfo.minLatencyMillis && this.maxExecutionDelayMillis == jobInfo.maxExecutionDelayMillis && this.isPeriodic == jobInfo.isPeriodic && this.isPersisted == jobInfo.isPersisted && this.intervalMillis == jobInfo.intervalMillis && this.flexMillis == jobInfo.flexMillis && this.initialBackoffMillis == jobInfo.initialBackoffMillis && this.backoffPolicy == jobInfo.backoffPolicy && this.mBias == jobInfo.mBias && this.mPriority == jobInfo.mPriority && this.flags == jobInfo.flags && this.mDebugTags.equals(jobInfo.mDebugTags) && java.util.Objects.equals(this.mTraceTag, jobInfo.mTraceTag);
    }

    public int hashCode() {
        int i = this.jobId;
        if (this.extras != null) {
            i = (i * 31) + this.extras.hashCode();
        }
        if (this.transientExtras != null) {
            i = (i * 31) + this.transientExtras.hashCode();
        }
        if (this.clipData != null) {
            i = (i * 31) + this.clipData.hashCode();
        }
        int i2 = (i * 31) + this.clipGrantFlags;
        if (this.service != null) {
            i2 = (i2 * 31) + this.service.hashCode();
        }
        int i3 = (i2 * 31) + this.constraintFlags;
        if (this.triggerContentUris != null) {
            i3 = (i3 * 31) + java.util.Arrays.hashCode(this.triggerContentUris);
        }
        int hashCode = (((((((i3 * 31) + java.lang.Long.hashCode(this.triggerContentUpdateDelay)) * 31) + java.lang.Long.hashCode(this.triggerContentMaxDelay)) * 31) + java.lang.Boolean.hashCode(this.hasEarlyConstraint)) * 31) + java.lang.Boolean.hashCode(this.hasLateConstraint);
        if (this.networkRequest != null) {
            hashCode = (hashCode * 31) + this.networkRequest.hashCode();
        }
        int hashCode2 = (((((((((((((((((((((((((((hashCode * 31) + java.lang.Long.hashCode(this.networkDownloadBytes)) * 31) + java.lang.Long.hashCode(this.networkUploadBytes)) * 31) + java.lang.Long.hashCode(this.minimumNetworkChunkBytes)) * 31) + java.lang.Long.hashCode(this.minLatencyMillis)) * 31) + java.lang.Long.hashCode(this.maxExecutionDelayMillis)) * 31) + java.lang.Boolean.hashCode(this.isPeriodic)) * 31) + java.lang.Boolean.hashCode(this.isPersisted)) * 31) + java.lang.Long.hashCode(this.intervalMillis)) * 31) + java.lang.Long.hashCode(this.flexMillis)) * 31) + java.lang.Long.hashCode(this.initialBackoffMillis)) * 31) + this.backoffPolicy) * 31) + this.mBias) * 31) + this.mPriority) * 31) + this.flags;
        if (this.mDebugTags.size() > 0) {
            hashCode2 = (hashCode2 * 31) + this.mDebugTags.hashCode();
        }
        if (this.mTraceTag != null) {
            return (hashCode2 * 31) + this.mTraceTag.hashCode();
        }
        return hashCode2;
    }

    private JobInfo(android.os.Parcel parcel) {
        this.jobId = parcel.readInt();
        android.os.PersistableBundle readPersistableBundle = parcel.readPersistableBundle();
        this.extras = readPersistableBundle == null ? android.os.PersistableBundle.EMPTY : readPersistableBundle;
        this.transientExtras = parcel.readBundle();
        if (parcel.readInt() != 0) {
            this.clipData = android.content.ClipData.CREATOR.createFromParcel(parcel);
            this.clipGrantFlags = parcel.readInt();
        } else {
            this.clipData = null;
            this.clipGrantFlags = 0;
        }
        this.service = (android.content.ComponentName) parcel.readParcelable(null);
        this.constraintFlags = parcel.readInt();
        this.triggerContentUris = (android.app.job.JobInfo.TriggerContentUri[]) parcel.createTypedArray(android.app.job.JobInfo.TriggerContentUri.CREATOR);
        this.triggerContentUpdateDelay = parcel.readLong();
        this.triggerContentMaxDelay = parcel.readLong();
        if (parcel.readInt() != 0) {
            this.networkRequest = (android.net.NetworkRequest) android.net.NetworkRequest.CREATOR.createFromParcel(parcel);
        } else {
            this.networkRequest = null;
        }
        this.networkDownloadBytes = parcel.readLong();
        this.networkUploadBytes = parcel.readLong();
        this.minimumNetworkChunkBytes = parcel.readLong();
        this.minLatencyMillis = parcel.readLong();
        this.maxExecutionDelayMillis = parcel.readLong();
        this.isPeriodic = parcel.readInt() == 1;
        this.isPersisted = parcel.readInt() == 1;
        this.intervalMillis = parcel.readLong();
        this.flexMillis = parcel.readLong();
        this.initialBackoffMillis = parcel.readLong();
        this.backoffPolicy = parcel.readInt();
        this.hasEarlyConstraint = parcel.readInt() == 1;
        this.hasLateConstraint = parcel.readInt() == 1;
        this.mBias = parcel.readInt();
        this.mPriority = parcel.readInt();
        this.flags = parcel.readInt();
        int readInt = parcel.readInt();
        this.mDebugTags = new android.util.ArraySet<>();
        for (int i = 0; i < readInt; i++) {
            java.lang.String readString = parcel.readString();
            if (readString == null) {
                throw new java.lang.IllegalStateException("malformed parcel");
            }
            this.mDebugTags.add(readString.intern());
        }
        java.lang.String readString2 = parcel.readString();
        this.mTraceTag = readString2 != null ? readString2.intern() : null;
    }

    private JobInfo(android.app.job.JobInfo.Builder builder) {
        android.app.job.JobInfo.TriggerContentUri[] triggerContentUriArr;
        this.jobId = builder.mJobId;
        this.extras = builder.mExtras.deepCopy();
        this.transientExtras = builder.mTransientExtras.deepCopy();
        this.clipData = builder.mClipData;
        this.clipGrantFlags = builder.mClipGrantFlags;
        this.service = builder.mJobService;
        this.constraintFlags = builder.mConstraintFlags;
        if (builder.mTriggerContentUris != null) {
            triggerContentUriArr = (android.app.job.JobInfo.TriggerContentUri[]) builder.mTriggerContentUris.toArray(new android.app.job.JobInfo.TriggerContentUri[builder.mTriggerContentUris.size()]);
        } else {
            triggerContentUriArr = null;
        }
        this.triggerContentUris = triggerContentUriArr;
        this.triggerContentUpdateDelay = builder.mTriggerContentUpdateDelay;
        this.triggerContentMaxDelay = builder.mTriggerContentMaxDelay;
        this.networkRequest = builder.mNetworkRequest;
        this.networkDownloadBytes = builder.mNetworkDownloadBytes;
        this.networkUploadBytes = builder.mNetworkUploadBytes;
        this.minimumNetworkChunkBytes = builder.mMinimumNetworkChunkBytes;
        this.minLatencyMillis = builder.mMinLatencyMillis;
        this.maxExecutionDelayMillis = builder.mMaxExecutionDelayMillis;
        this.isPeriodic = builder.mIsPeriodic;
        this.isPersisted = builder.mIsPersisted;
        this.intervalMillis = builder.mIntervalMillis;
        this.flexMillis = builder.mFlexMillis;
        this.initialBackoffMillis = builder.mInitialBackoffMillis;
        this.backoffPolicy = builder.mBackoffPolicy;
        this.hasEarlyConstraint = builder.mHasEarlyConstraint;
        this.hasLateConstraint = builder.mHasLateConstraint;
        this.mBias = builder.mBias;
        this.mPriority = builder.mPriority;
        this.flags = builder.mFlags;
        this.mDebugTags = builder.mDebugTags;
        this.mTraceTag = builder.mTraceTag;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.jobId);
        parcel.writePersistableBundle(this.extras);
        parcel.writeBundle(this.transientExtras);
        if (this.clipData != null) {
            parcel.writeInt(1);
            this.clipData.writeToParcel(parcel, i);
            parcel.writeInt(this.clipGrantFlags);
        } else {
            parcel.writeInt(0);
        }
        parcel.writeParcelable(this.service, i);
        parcel.writeInt(this.constraintFlags);
        parcel.writeTypedArray(this.triggerContentUris, i);
        parcel.writeLong(this.triggerContentUpdateDelay);
        parcel.writeLong(this.triggerContentMaxDelay);
        if (this.networkRequest != null) {
            parcel.writeInt(1);
            this.networkRequest.writeToParcel(parcel, i);
        } else {
            parcel.writeInt(0);
        }
        parcel.writeLong(this.networkDownloadBytes);
        parcel.writeLong(this.networkUploadBytes);
        parcel.writeLong(this.minimumNetworkChunkBytes);
        parcel.writeLong(this.minLatencyMillis);
        parcel.writeLong(this.maxExecutionDelayMillis);
        parcel.writeInt(this.isPeriodic ? 1 : 0);
        parcel.writeInt(this.isPersisted ? 1 : 0);
        parcel.writeLong(this.intervalMillis);
        parcel.writeLong(this.flexMillis);
        parcel.writeLong(this.initialBackoffMillis);
        parcel.writeInt(this.backoffPolicy);
        parcel.writeInt(this.hasEarlyConstraint ? 1 : 0);
        parcel.writeInt(this.hasLateConstraint ? 1 : 0);
        parcel.writeInt(this.mBias);
        parcel.writeInt(this.mPriority);
        parcel.writeInt(this.flags);
        int size = this.mDebugTags.size();
        parcel.writeInt(size);
        for (int i2 = 0; i2 < size; i2++) {
            parcel.writeString(this.mDebugTags.valueAt(i2));
        }
        parcel.writeString(this.mTraceTag);
    }

    public java.lang.String toString() {
        return "(job:" + this.jobId + "/" + this.service.flattenToShortString() + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END;
    }

    public static final class TriggerContentUri implements android.os.Parcelable {
        public static final android.os.Parcelable.Creator<android.app.job.JobInfo.TriggerContentUri> CREATOR = new android.os.Parcelable.Creator<android.app.job.JobInfo.TriggerContentUri>() { // from class: android.app.job.JobInfo.TriggerContentUri.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.app.job.JobInfo.TriggerContentUri createFromParcel(android.os.Parcel parcel) {
                return new android.app.job.JobInfo.TriggerContentUri(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.app.job.JobInfo.TriggerContentUri[] newArray(int i) {
                return new android.app.job.JobInfo.TriggerContentUri[i];
            }
        };
        public static final int FLAG_NOTIFY_FOR_DESCENDANTS = 1;
        private final int mFlags;
        private final android.net.Uri mUri;

        @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
        public @interface Flags {
        }

        public TriggerContentUri(android.net.Uri uri, int i) {
            this.mUri = (android.net.Uri) java.util.Objects.requireNonNull(uri);
            this.mFlags = i;
        }

        public android.net.Uri getUri() {
            return this.mUri;
        }

        public int getFlags() {
            return this.mFlags;
        }

        public boolean equals(java.lang.Object obj) {
            if (!(obj instanceof android.app.job.JobInfo.TriggerContentUri)) {
                return false;
            }
            android.app.job.JobInfo.TriggerContentUri triggerContentUri = (android.app.job.JobInfo.TriggerContentUri) obj;
            return java.util.Objects.equals(triggerContentUri.mUri, this.mUri) && triggerContentUri.mFlags == this.mFlags;
        }

        public int hashCode() {
            return (this.mUri == null ? 0 : this.mUri.hashCode()) ^ this.mFlags;
        }

        private TriggerContentUri(android.os.Parcel parcel) {
            this.mUri = android.net.Uri.CREATOR.createFromParcel(parcel);
            this.mFlags = parcel.readInt();
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(android.os.Parcel parcel, int i) {
            this.mUri.writeToParcel(parcel, i);
            parcel.writeInt(this.mFlags);
        }
    }

    public static final class Builder {
        private int mBackoffPolicy;
        private boolean mBackoffPolicySet;
        private int mBias;
        private android.content.ClipData mClipData;
        private int mClipGrantFlags;
        private int mConstraintFlags;
        private final android.util.ArraySet<java.lang.String> mDebugTags;
        private android.os.PersistableBundle mExtras;
        private int mFlags;
        private long mFlexMillis;
        private boolean mHasEarlyConstraint;
        private boolean mHasLateConstraint;
        private long mInitialBackoffMillis;
        private long mIntervalMillis;
        private boolean mIsPeriodic;
        private boolean mIsPersisted;
        private final int mJobId;
        private final android.content.ComponentName mJobService;
        private long mMaxExecutionDelayMillis;
        private long mMinLatencyMillis;
        private long mMinimumNetworkChunkBytes;
        private long mNetworkDownloadBytes;
        private android.net.NetworkRequest mNetworkRequest;
        private long mNetworkUploadBytes;
        private int mPriority;
        private java.lang.String mTraceTag;
        private android.os.Bundle mTransientExtras;
        private long mTriggerContentMaxDelay;
        private long mTriggerContentUpdateDelay;
        private java.util.ArrayList<android.app.job.JobInfo.TriggerContentUri> mTriggerContentUris;

        public Builder(int i, android.content.ComponentName componentName) {
            this.mExtras = android.os.PersistableBundle.EMPTY;
            this.mTransientExtras = android.os.Bundle.EMPTY;
            this.mBias = 0;
            this.mPriority = 300;
            this.mNetworkDownloadBytes = -1L;
            this.mNetworkUploadBytes = -1L;
            this.mMinimumNetworkChunkBytes = -1L;
            this.mTriggerContentUpdateDelay = -1L;
            this.mTriggerContentMaxDelay = -1L;
            this.mInitialBackoffMillis = 30000L;
            this.mBackoffPolicy = 1;
            this.mBackoffPolicySet = false;
            this.mDebugTags = new android.util.ArraySet<>();
            this.mJobService = componentName;
            this.mJobId = i;
        }

        public Builder(android.app.job.JobInfo jobInfo) {
            this.mExtras = android.os.PersistableBundle.EMPTY;
            this.mTransientExtras = android.os.Bundle.EMPTY;
            this.mBias = 0;
            this.mPriority = 300;
            this.mNetworkDownloadBytes = -1L;
            this.mNetworkUploadBytes = -1L;
            this.mMinimumNetworkChunkBytes = -1L;
            this.mTriggerContentUpdateDelay = -1L;
            this.mTriggerContentMaxDelay = -1L;
            this.mInitialBackoffMillis = 30000L;
            this.mBackoffPolicy = 1;
            this.mBackoffPolicySet = false;
            this.mDebugTags = new android.util.ArraySet<>();
            this.mJobId = jobInfo.getId();
            this.mJobService = jobInfo.getService();
            this.mExtras = jobInfo.getExtras();
            this.mTransientExtras = jobInfo.getTransientExtras();
            this.mClipData = jobInfo.getClipData();
            this.mClipGrantFlags = jobInfo.getClipGrantFlags();
            this.mBias = jobInfo.getBias();
            this.mFlags = jobInfo.getFlags();
            this.mConstraintFlags = jobInfo.getConstraintFlags();
            this.mNetworkRequest = jobInfo.getRequiredNetwork();
            this.mNetworkDownloadBytes = jobInfo.getEstimatedNetworkDownloadBytes();
            this.mNetworkUploadBytes = jobInfo.getEstimatedNetworkUploadBytes();
            this.mMinimumNetworkChunkBytes = jobInfo.getMinimumNetworkChunkBytes();
            this.mTriggerContentUris = jobInfo.getTriggerContentUris() != null ? new java.util.ArrayList<>(java.util.Arrays.asList(jobInfo.getTriggerContentUris())) : null;
            this.mTriggerContentUpdateDelay = jobInfo.getTriggerContentUpdateDelay();
            this.mTriggerContentMaxDelay = jobInfo.getTriggerContentMaxDelay();
            this.mIsPersisted = jobInfo.isPersisted();
            this.mMinLatencyMillis = jobInfo.getMinLatencyMillis();
            this.mMaxExecutionDelayMillis = jobInfo.getMaxExecutionDelayMillis();
            this.mIsPeriodic = jobInfo.isPeriodic();
            this.mHasEarlyConstraint = jobInfo.hasEarlyConstraint();
            this.mHasLateConstraint = jobInfo.hasLateConstraint();
            this.mIntervalMillis = jobInfo.getIntervalMillis();
            this.mFlexMillis = jobInfo.getFlexMillis();
            this.mInitialBackoffMillis = jobInfo.getInitialBackoffMillis();
            this.mBackoffPolicy = jobInfo.getBackoffPolicy();
            this.mPriority = jobInfo.getPriority();
        }

        public android.app.job.JobInfo.Builder addDebugTag(java.lang.String str) {
            this.mDebugTags.add(android.app.job.JobInfo.validateDebugTag(str));
            return this;
        }

        public void addDebugTags(java.util.Set<java.lang.String> set) {
            this.mDebugTags.addAll(set);
        }

        public android.app.job.JobInfo.Builder removeDebugTag(java.lang.String str) {
            this.mDebugTags.remove(str);
            return this;
        }

        public android.app.job.JobInfo.Builder setBias(int i) {
            this.mBias = i;
            return this;
        }

        public android.app.job.JobInfo.Builder setPriority(int i) {
            if (i > 500 || i < 100) {
                if (android.compat.Compatibility.isChangeEnabled(android.app.job.JobInfo.THROW_ON_INVALID_PRIORITY_VALUE)) {
                    throw new java.lang.IllegalArgumentException("Invalid priority value");
                }
                return this;
            }
            this.mPriority = i;
            return this;
        }

        public android.app.job.JobInfo.Builder setFlags(int i) {
            this.mFlags = i;
            return this;
        }

        public android.app.job.JobInfo.Builder setExtras(android.os.PersistableBundle persistableBundle) {
            this.mExtras = persistableBundle;
            return this;
        }

        public android.app.job.JobInfo.Builder setTransientExtras(android.os.Bundle bundle) {
            this.mTransientExtras = bundle;
            return this;
        }

        public android.app.job.JobInfo.Builder setClipData(android.content.ClipData clipData, int i) {
            this.mClipData = clipData;
            this.mClipGrantFlags = i;
            return this;
        }

        public android.app.job.JobInfo.Builder setRequiredNetworkType(int i) {
            if (i == 0) {
                return setRequiredNetwork(null);
            }
            android.net.NetworkRequest.Builder builder = new android.net.NetworkRequest.Builder();
            builder.addCapability(12);
            builder.addCapability(16);
            builder.removeCapability(15);
            builder.removeCapability(13);
            if (i != 1) {
                if (i == 2) {
                    builder.addCapability(11);
                } else if (i == 3) {
                    builder.addCapability(18);
                } else if (i == 4) {
                    builder.addTransportType(0);
                }
            }
            return setRequiredNetwork(builder.build());
        }

        public android.app.job.JobInfo.Builder setRequiredNetwork(android.net.NetworkRequest networkRequest) {
            this.mNetworkRequest = networkRequest;
            return this;
        }

        public android.app.job.JobInfo.Builder setEstimatedNetworkBytes(long j, long j2) {
            this.mNetworkDownloadBytes = j;
            this.mNetworkUploadBytes = j2;
            return this;
        }

        public android.app.job.JobInfo.Builder setMinimumNetworkChunkBytes(long j) {
            if (j != -1 && j <= 0) {
                throw new java.lang.IllegalArgumentException("Minimum chunk size must be positive");
            }
            this.mMinimumNetworkChunkBytes = j;
            return this;
        }

        public android.app.job.JobInfo.Builder setRequiresCharging(boolean z) {
            this.mConstraintFlags = (z ? 1 : 0) | (this.mConstraintFlags & (-2));
            return this;
        }

        public android.app.job.JobInfo.Builder setRequiresBatteryNotLow(boolean z) {
            this.mConstraintFlags = (z ? 2 : 0) | (this.mConstraintFlags & (-3));
            return this;
        }

        public android.app.job.JobInfo.Builder setRequiresDeviceIdle(boolean z) {
            this.mConstraintFlags = (z ? 4 : 0) | (this.mConstraintFlags & (-5));
            return this;
        }

        public android.app.job.JobInfo.Builder setRequiresStorageNotLow(boolean z) {
            this.mConstraintFlags = (z ? 8 : 0) | (this.mConstraintFlags & (-9));
            return this;
        }

        public android.app.job.JobInfo.Builder addTriggerContentUri(android.app.job.JobInfo.TriggerContentUri triggerContentUri) {
            if (this.mTriggerContentUris == null) {
                this.mTriggerContentUris = new java.util.ArrayList<>();
            }
            this.mTriggerContentUris.add(triggerContentUri);
            return this;
        }

        public android.app.job.JobInfo.Builder setTriggerContentUpdateDelay(long j) {
            this.mTriggerContentUpdateDelay = j;
            return this;
        }

        public android.app.job.JobInfo.Builder setTriggerContentMaxDelay(long j) {
            this.mTriggerContentMaxDelay = j;
            return this;
        }

        public android.app.job.JobInfo.Builder setPeriodic(long j) {
            return setPeriodic(j, j);
        }

        public android.app.job.JobInfo.Builder setPeriodic(long j, long j2) {
            long minPeriodMillis = android.app.job.JobInfo.getMinPeriodMillis();
            if (j < minPeriodMillis) {
                android.util.Log.w(android.app.job.JobInfo.TAG, "Requested interval " + android.util.TimeUtils.formatDuration(j) + " for job " + this.mJobId + " is too small; raising to " + android.util.TimeUtils.formatDuration(minPeriodMillis));
                j = minPeriodMillis;
            }
            long max = java.lang.Math.max((5 * j) / 100, android.app.job.JobInfo.getMinFlexMillis());
            if (j2 < max) {
                android.util.Log.w(android.app.job.JobInfo.TAG, "Requested flex " + android.util.TimeUtils.formatDuration(j2) + " for job " + this.mJobId + " is too small; raising to " + android.util.TimeUtils.formatDuration(max));
                j2 = max;
            }
            this.mIsPeriodic = true;
            this.mIntervalMillis = j;
            this.mFlexMillis = j2;
            this.mHasLateConstraint = true;
            this.mHasEarlyConstraint = true;
            return this;
        }

        public android.app.job.JobInfo.Builder setMinimumLatency(long j) {
            this.mMinLatencyMillis = j;
            this.mHasEarlyConstraint = true;
            return this;
        }

        public android.app.job.JobInfo.Builder setOverrideDeadline(long j) {
            this.mMaxExecutionDelayMillis = j;
            this.mHasLateConstraint = true;
            return this;
        }

        public android.app.job.JobInfo.Builder setBackoffCriteria(long j, int i) {
            long minBackoffMillis = android.app.job.JobInfo.getMinBackoffMillis();
            if (j < minBackoffMillis) {
                android.util.Log.w(android.app.job.JobInfo.TAG, "Requested backoff " + android.util.TimeUtils.formatDuration(j) + " for job " + this.mJobId + " is too small; raising to " + android.util.TimeUtils.formatDuration(minBackoffMillis));
                j = minBackoffMillis;
            }
            this.mBackoffPolicySet = true;
            this.mInitialBackoffMillis = j;
            this.mBackoffPolicy = i;
            return this;
        }

        public android.app.job.JobInfo.Builder setExpedited(boolean z) {
            if (z) {
                this.mFlags |= 16;
                if (this.mPriority == 300) {
                    this.mPriority = 500;
                }
            } else {
                if (this.mPriority == 500 && (this.mFlags & 16) != 0) {
                    this.mPriority = 300;
                }
                this.mFlags &= -17;
            }
            return this;
        }

        public android.app.job.JobInfo.Builder setUserInitiated(boolean z) {
            if (z) {
                this.mFlags |= 32;
                if (this.mPriority == 300) {
                    this.mPriority = 500;
                }
            } else {
                if (this.mPriority == 500 && (this.mFlags & 32) != 0) {
                    this.mPriority = 300;
                }
                this.mFlags &= -33;
            }
            return this;
        }

        @java.lang.Deprecated
        public android.app.job.JobInfo.Builder setImportantWhileForeground(boolean z) {
            if (z) {
                this.mFlags |= 2;
                if (this.mPriority == 300) {
                    this.mPriority = 400;
                }
            } else {
                if (this.mPriority == 400 && (this.mFlags & 2) != 0) {
                    this.mPriority = 300;
                }
                this.mFlags &= -3;
            }
            return this;
        }

        public android.app.job.JobInfo.Builder setPrefetch(boolean z) {
            if (z) {
                this.mFlags |= 4;
            } else {
                this.mFlags &= -5;
            }
            return this;
        }

        public android.app.job.JobInfo.Builder setPersisted(boolean z) {
            this.mIsPersisted = z;
            return this;
        }

        public android.app.job.JobInfo.Builder setTraceTag(java.lang.String str) {
            this.mTraceTag = android.app.job.JobInfo.validateTraceTag(str);
            return this;
        }

        public android.app.job.JobInfo build() {
            return build(android.compat.Compatibility.isChangeEnabled(android.app.job.JobInfo.DISALLOW_DEADLINES_FOR_PREFETCH_JOBS), android.compat.Compatibility.isChangeEnabled(android.app.job.JobInfo.REJECT_NEGATIVE_NETWORK_ESTIMATES), android.compat.Compatibility.isChangeEnabled(android.app.job.JobInfo.ENFORCE_MINIMUM_TIME_WINDOWS), android.compat.Compatibility.isChangeEnabled(android.app.job.JobInfo.REJECT_NEGATIVE_DELAYS_AND_DEADLINES));
        }

        public android.app.job.JobInfo build(boolean z, boolean z2, boolean z3, boolean z4) {
            if (this.mBackoffPolicySet && (this.mConstraintFlags & 4) != 0) {
                throw new java.lang.IllegalArgumentException("An idle mode job will not respect any back-off policy, so calling setBackoffCriteria with setRequiresDeviceIdle is an error.");
            }
            android.app.job.JobInfo jobInfo = new android.app.job.JobInfo(this);
            jobInfo.enforceValidity(z, z2, z3, z4);
            return jobInfo;
        }

        public java.lang.String summarize() {
            java.lang.String str;
            if (this.mJobService != null) {
                str = this.mJobService.flattenToShortString();
            } else {
                str = "null";
            }
            return "JobInfo.Builder{job:" + this.mJobId + "/" + str + "}";
        }
    }

    public final void enforceValidity(boolean z, boolean z2, boolean z3, boolean z4) {
        long j;
        if ((this.networkDownloadBytes > 0 || this.networkUploadBytes > 0 || this.minimumNetworkChunkBytes > 0) && this.networkRequest == null) {
            throw new java.lang.IllegalArgumentException("Can't provide estimated network usage without requiring a network");
        }
        if (this.networkRequest != null && z2) {
            if (this.networkUploadBytes != -1 && this.networkUploadBytes < 0) {
                throw new java.lang.IllegalArgumentException("Invalid network upload bytes: " + this.networkUploadBytes);
            }
            if (this.networkDownloadBytes != -1 && this.networkDownloadBytes < 0) {
                throw new java.lang.IllegalArgumentException("Invalid network download bytes: " + this.networkDownloadBytes);
            }
        }
        if (this.networkUploadBytes == -1) {
            j = this.networkDownloadBytes;
        } else {
            j = this.networkUploadBytes + (this.networkDownloadBytes == -1 ? 0L : this.networkDownloadBytes);
        }
        if (this.minimumNetworkChunkBytes != -1 && j != -1 && this.minimumNetworkChunkBytes > j) {
            throw new java.lang.IllegalArgumentException("Minimum chunk size can't be greater than estimated network usage");
        }
        if (this.minimumNetworkChunkBytes != -1 && this.minimumNetworkChunkBytes <= 0) {
            throw new java.lang.IllegalArgumentException("Minimum chunk size must be positive");
        }
        if (z4) {
            if (this.minLatencyMillis < 0) {
                throw new java.lang.IllegalArgumentException("Minimum latency is negative: " + this.minLatencyMillis);
            }
            if (this.maxExecutionDelayMillis < 0) {
                throw new java.lang.IllegalArgumentException("Override deadline is negative: " + this.maxExecutionDelayMillis);
            }
        }
        boolean z5 = true;
        boolean z6 = this.maxExecutionDelayMillis != 0;
        if (this.isPeriodic) {
            if (z6) {
                throw new java.lang.IllegalArgumentException("Can't call setOverrideDeadline() on a periodic job.");
            }
            if (this.minLatencyMillis != 0) {
                throw new java.lang.IllegalArgumentException("Can't call setMinimumLatency() on a periodic job");
            }
            if (this.triggerContentUris != null) {
                throw new java.lang.IllegalArgumentException("Can't call addTriggerContentUri() on a periodic job");
            }
        }
        if (z && z6 && (this.flags & 4) != 0) {
            throw new java.lang.IllegalArgumentException("Can't call setOverrideDeadline() on a prefetch job.");
        }
        if (this.isPersisted) {
            if (this.networkRequest != null && this.networkRequest.getNetworkSpecifier() != null) {
                throw new java.lang.IllegalArgumentException("Network specifiers aren't supported for persistent jobs");
            }
            if (this.triggerContentUris != null) {
                throw new java.lang.IllegalArgumentException("Can't call addTriggerContentUri() on a persisted job");
            }
            if (!this.transientExtras.isEmpty()) {
                throw new java.lang.IllegalArgumentException("Can't call setTransientExtras() on a persisted job");
            }
            if (this.clipData != null) {
                throw new java.lang.IllegalArgumentException("Can't call setClipData() on a persisted job");
            }
        }
        if ((this.flags & 2) != 0) {
            if (this.hasEarlyConstraint) {
                throw new java.lang.IllegalArgumentException("An important while foreground job cannot have a time delay");
            }
            if (this.mPriority != 400 && this.mPriority != 300) {
                throw new java.lang.IllegalArgumentException("An important while foreground job must be high or default priority. Don't mark unimportant tasks as important while foreground.");
            }
        }
        boolean z7 = (this.flags & 16) != 0;
        boolean z8 = (this.flags & 32) != 0;
        switch (this.mPriority) {
            case 100:
            case 200:
            case 300:
                break;
            case 400:
                if ((this.flags & 4) != 0) {
                    throw new java.lang.IllegalArgumentException("Prefetch jobs cannot be high priority");
                }
                if (this.isPeriodic) {
                    throw new java.lang.IllegalArgumentException("Periodic jobs cannot be high priority");
                }
                break;
            case 500:
                if (!z7 && !z8) {
                    throw new java.lang.IllegalArgumentException("Only expedited or user-initiated jobs can have max priority");
                }
                break;
            default:
                throw new java.lang.IllegalArgumentException("Invalid priority level provided: " + this.mPriority);
        }
        if (this.networkRequest == null && this.constraintFlags == 0 && (this.triggerContentUris == null || this.triggerContentUris.length <= 0)) {
            z5 = false;
        }
        if (this.hasLateConstraint && !this.isPeriodic) {
            if (!z5) {
                android.util.Log.w(TAG, "Job '" + this.service.flattenToShortString() + "#" + this.jobId + "' has a deadline with no functional constraints. The deadline won't improve job execution latency. Consider removing the deadline.");
            } else {
                long j2 = this.hasEarlyConstraint ? this.minLatencyMillis : 0L;
                if (this.maxExecutionDelayMillis - j2 < android.app.AlarmManager.INTERVAL_FIFTEEN_MINUTES) {
                    if (!z3 || !android.app.job.Flags.enforceMinimumTimeWindows()) {
                        android.util.Log.w(TAG, "Job '" + this.service.flattenToShortString() + "#" + this.jobId + "' has a deadline with functional constraints and an extremely short time window of " + (this.maxExecutionDelayMillis - j2) + " ms (delay=" + j2 + ", deadline=" + this.maxExecutionDelayMillis + "). The functional constraints are not likely to be satisfied when the job runs.");
                    } else {
                        throw new java.lang.IllegalArgumentException("Time window too short. Constraints unlikely to be satisfied. Increase deadline to a reasonable duration. Job '" + this.service.flattenToShortString() + "#" + this.jobId + "' has delay=" + j2 + ", deadline=" + this.maxExecutionDelayMillis);
                    }
                }
            }
        }
        if (z7) {
            if (this.hasEarlyConstraint) {
                throw new java.lang.IllegalArgumentException("An expedited job cannot have a time delay");
            }
            if (this.hasLateConstraint) {
                throw new java.lang.IllegalArgumentException("An expedited job cannot have a deadline");
            }
            if (this.isPeriodic) {
                throw new java.lang.IllegalArgumentException("An expedited job cannot be periodic");
            }
            if (!z8) {
                if (this.mPriority != 500 && this.mPriority != 400) {
                    throw new java.lang.IllegalArgumentException("An expedited job must be high or max priority. Don't use expedited jobs for unimportant tasks.");
                }
                if ((this.constraintFlags & (-9)) != 0 || (this.flags & (-25)) != 0) {
                    throw new java.lang.IllegalArgumentException("An expedited job can only have network and storage-not-low constraints");
                }
                if (this.triggerContentUris != null && this.triggerContentUris.length > 0) {
                    throw new java.lang.IllegalArgumentException("Can't call addTriggerContentUri() on an expedited job");
                }
            } else {
                throw new java.lang.IllegalArgumentException("An expedited job cannot be user-initiated");
            }
        }
        if (z8) {
            if (this.hasEarlyConstraint) {
                throw new java.lang.IllegalArgumentException("A user-initiated job cannot have a time delay");
            }
            if (this.hasLateConstraint) {
                throw new java.lang.IllegalArgumentException("A user-initiated job cannot have a deadline");
            }
            if (this.isPeriodic) {
                throw new java.lang.IllegalArgumentException("A user-initiated job cannot be periodic");
            }
            if ((this.flags & 4) == 0) {
                if (this.mPriority != 500) {
                    throw new java.lang.IllegalArgumentException("A user-initiated job must be max priority.");
                }
                if ((this.constraintFlags & 4) != 0) {
                    throw new java.lang.IllegalArgumentException("A user-initiated job cannot have a device-idle constraint");
                }
                if (this.triggerContentUris != null && this.triggerContentUris.length > 0) {
                    throw new java.lang.IllegalArgumentException("Can't call addTriggerContentUri() on a user-initiated job");
                }
                if (this.networkRequest == null) {
                    throw new java.lang.IllegalArgumentException("A user-initiated data transfer job must specify a valid network type");
                }
            } else {
                throw new java.lang.IllegalArgumentException("A user-initiated job cannot also be a prefetch job");
            }
        }
        if (this.mDebugTags.size() > 32) {
            throw new java.lang.IllegalArgumentException("Can't have more than 32 tags");
        }
        android.util.ArraySet<? extends java.lang.String> arraySet = new android.util.ArraySet<>();
        for (int i = 0; i < this.mDebugTags.size(); i++) {
            arraySet.add(validateDebugTag(this.mDebugTags.valueAt(i)));
        }
        this.mDebugTags.clear();
        this.mDebugTags.addAll(arraySet);
        validateTraceTag(this.mTraceTag);
    }

    public static java.lang.String validateDebugTag(java.lang.String str) {
        if (str == null) {
            throw new java.lang.NullPointerException("debug tag cannot be null");
        }
        java.lang.String trim = str.trim();
        if (trim.isEmpty()) {
            throw new java.lang.IllegalArgumentException("debug tag cannot be empty");
        }
        if (trim.length() > 127) {
            throw new java.lang.IllegalArgumentException("debug tag cannot be more than 127 characters");
        }
        return trim.intern();
    }

    public static java.lang.String validateTraceTag(java.lang.String str) {
        if (str == null) {
            return null;
        }
        java.lang.String trim = str.trim();
        if (trim.isEmpty()) {
            throw new java.lang.IllegalArgumentException("trace tag cannot be empty");
        }
        if (trim.length() > 127) {
            throw new java.lang.IllegalArgumentException("traceTag tag cannot be more than 127 characters");
        }
        if (trim.contains(android.util.NtpTrustedTime.NTP_SETTING_SERVER_NAME_DELIMITER) || trim.contains("\n") || trim.contains("\u0000")) {
            throw new java.lang.IllegalArgumentException("Trace tag cannot contain |, \\n, or \\0");
        }
        return trim.intern();
    }

    public static java.lang.String getBiasString(int i) {
        switch (i) {
            case 0:
                return "0 [DEFAULT]";
            case 10:
                return "10 [SYNC_EXPEDITED]";
            case 20:
                return "20 [SYNC_INITIALIZATION]";
            case 30:
                return "30 [BFGS_APP]";
            case 35:
                return "35 [FGS_APP]";
            case 40:
                return "40 [TOP_APP]";
            default:
                return i + " [UNKNOWN]";
        }
    }

    public static java.lang.String getPriorityString(int i) {
        switch (i) {
            case 100:
                return i + " [MIN]";
            case 200:
                return i + " [LOW]";
            case 300:
                return i + " [DEFAULT]";
            case 400:
                return i + " [HIGH]";
            case 500:
                return i + " [MAX]";
            default:
                return i + " [UNKNOWN]";
        }
    }
}
