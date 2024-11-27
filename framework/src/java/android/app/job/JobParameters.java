package android.app.job;

/* loaded from: classes.dex */
public class JobParameters implements android.os.Parcelable {
    public static final int INTERNAL_STOP_REASON_ANR = 12;
    public static final int INTERNAL_STOP_REASON_CANCELED = 0;
    public static final int INTERNAL_STOP_REASON_CONSTRAINTS_NOT_SATISFIED = 1;
    public static final int INTERNAL_STOP_REASON_DATA_CLEARED = 8;
    public static final int INTERNAL_STOP_REASON_DEVICE_IDLE = 4;
    public static final int INTERNAL_STOP_REASON_DEVICE_THERMAL = 5;
    public static final int INTERNAL_STOP_REASON_PREEMPT = 2;
    public static final int INTERNAL_STOP_REASON_RESTRICTED_BUCKET = 6;
    public static final int INTERNAL_STOP_REASON_RTC_UPDATED = 9;
    public static final int INTERNAL_STOP_REASON_SUCCESSFUL_FINISH = 10;
    public static final int INTERNAL_STOP_REASON_TIMEOUT = 3;
    public static final int INTERNAL_STOP_REASON_UNINSTALL = 7;
    public static final int INTERNAL_STOP_REASON_UNKNOWN = -1;
    public static final int INTERNAL_STOP_REASON_USER_UI_STOP = 11;
    public static final int STOP_REASON_APP_STANDBY = 12;
    public static final int STOP_REASON_BACKGROUND_RESTRICTION = 11;
    public static final int STOP_REASON_CANCELLED_BY_APP = 1;
    public static final int STOP_REASON_CONSTRAINT_BATTERY_NOT_LOW = 5;
    public static final int STOP_REASON_CONSTRAINT_CHARGING = 6;
    public static final int STOP_REASON_CONSTRAINT_CONNECTIVITY = 7;
    public static final int STOP_REASON_CONSTRAINT_DEVICE_IDLE = 8;
    public static final int STOP_REASON_CONSTRAINT_STORAGE_NOT_LOW = 9;
    public static final int STOP_REASON_DEVICE_STATE = 4;
    public static final int STOP_REASON_ESTIMATED_APP_LAUNCH_TIME_CHANGED = 15;
    public static final int STOP_REASON_PREEMPT = 2;
    public static final int STOP_REASON_QUOTA = 10;
    public static final int STOP_REASON_SYSTEM_PROCESSING = 14;
    public static final int STOP_REASON_TIMEOUT = 3;
    public static final int STOP_REASON_UNDEFINED = 0;
    public static final int STOP_REASON_USER = 13;
    private final android.os.IBinder callback;
    private final android.content.ClipData clipData;
    private final int clipGrantFlags;
    private java.lang.String debugStopReason;
    private final android.os.PersistableBundle extras;
    private final int jobId;
    private int mInternalStopReason;
    private final boolean mIsExpedited;
    private final boolean mIsUserInitiated;
    private final java.lang.String mJobNamespace;
    private android.net.Network mNetwork;
    private int mStopReason;
    private final java.lang.String[] mTriggeredContentAuthorities;
    private final android.net.Uri[] mTriggeredContentUris;
    private final boolean overrideDeadlineExpired;
    private final android.os.Bundle transientExtras;
    public static final int[] JOB_STOP_REASON_CODES = {-1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
    public static final android.os.Parcelable.Creator<android.app.job.JobParameters> CREATOR = new android.os.Parcelable.Creator<android.app.job.JobParameters>() { // from class: android.app.job.JobParameters.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.job.JobParameters createFromParcel(android.os.Parcel parcel) {
            return new android.app.job.JobParameters(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.job.JobParameters[] newArray(int i) {
            return new android.app.job.JobParameters[i];
        }
    };

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface StopReason {
    }

    public static java.lang.String getInternalReasonCodeDescription(int i) {
        switch (i) {
            case 0:
                return android.companion.CompanionDeviceManager.REASON_CANCELED;
            case 1:
                return "constraints";
            case 2:
                return "preempt";
            case 3:
                return "timeout";
            case 4:
                return "device_idle";
            case 5:
                return android.os.PowerManager.SHUTDOWN_THERMAL_STATE;
            case 6:
                return "restricted_bucket";
            case 7:
                return "uninstall";
            case 8:
                return "data_cleared";
            case 9:
                return "rtc_updated";
            case 10:
                return "successful_finish";
            case 11:
                return "user_ui_stop";
            case 12:
                return "anr";
            default:
                return "unknown:" + i;
        }
    }

    public static int[] getJobStopReasonCodes() {
        return JOB_STOP_REASON_CODES;
    }

    public JobParameters(android.os.IBinder iBinder, java.lang.String str, int i, android.os.PersistableBundle persistableBundle, android.os.Bundle bundle, android.content.ClipData clipData, int i2, boolean z, boolean z2, boolean z3, android.net.Uri[] uriArr, java.lang.String[] strArr, android.net.Network network) {
        this.mStopReason = 0;
        this.mInternalStopReason = -1;
        this.jobId = i;
        this.extras = persistableBundle;
        this.transientExtras = bundle;
        this.clipData = clipData;
        this.clipGrantFlags = i2;
        this.callback = iBinder;
        this.overrideDeadlineExpired = z;
        this.mIsExpedited = z2;
        this.mIsUserInitiated = z3;
        this.mTriggeredContentUris = uriArr;
        this.mTriggeredContentAuthorities = strArr;
        this.mNetwork = network;
        this.mJobNamespace = str;
    }

    public int getJobId() {
        return this.jobId;
    }

    public java.lang.String getJobNamespace() {
        return this.mJobNamespace;
    }

    public int getStopReason() {
        return this.mStopReason;
    }

    public int getInternalStopReasonCode() {
        return this.mInternalStopReason;
    }

    public java.lang.String getDebugStopReason() {
        return this.debugStopReason;
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

    public boolean isExpeditedJob() {
        return this.mIsExpedited;
    }

    public boolean isUserInitiatedJob() {
        return this.mIsUserInitiated;
    }

    public boolean isOverrideDeadlineExpired() {
        return this.overrideDeadlineExpired;
    }

    public android.net.Uri[] getTriggeredContentUris() {
        return this.mTriggeredContentUris;
    }

    public java.lang.String[] getTriggeredContentAuthorities() {
        return this.mTriggeredContentAuthorities;
    }

    public android.net.Network getNetwork() {
        return this.mNetwork;
    }

    public android.app.job.JobWorkItem dequeueWork() {
        try {
            return getCallback().dequeueWork(getJobId());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void completeWork(android.app.job.JobWorkItem jobWorkItem) {
        try {
            if (!getCallback().completeWork(getJobId(), jobWorkItem.getWorkId())) {
                throw new java.lang.IllegalArgumentException("Given work is not active: " + jobWorkItem);
            }
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public android.app.job.IJobCallback getCallback() {
        return android.app.job.IJobCallback.Stub.asInterface(this.callback);
    }

    private JobParameters(android.os.Parcel parcel) {
        this.mStopReason = 0;
        this.mInternalStopReason = -1;
        this.jobId = parcel.readInt();
        this.mJobNamespace = parcel.readString();
        this.extras = parcel.readPersistableBundle();
        this.transientExtras = parcel.readBundle();
        if (parcel.readInt() != 0) {
            this.clipData = android.content.ClipData.CREATOR.createFromParcel(parcel);
            this.clipGrantFlags = parcel.readInt();
        } else {
            this.clipData = null;
            this.clipGrantFlags = 0;
        }
        this.callback = parcel.readStrongBinder();
        this.overrideDeadlineExpired = parcel.readInt() == 1;
        this.mIsExpedited = parcel.readBoolean();
        this.mIsUserInitiated = parcel.readBoolean();
        this.mTriggeredContentUris = (android.net.Uri[]) parcel.createTypedArray(android.net.Uri.CREATOR);
        this.mTriggeredContentAuthorities = parcel.createStringArray();
        if (parcel.readInt() != 0) {
            this.mNetwork = (android.net.Network) android.net.Network.CREATOR.createFromParcel(parcel);
        } else {
            this.mNetwork = null;
        }
        this.mStopReason = parcel.readInt();
        this.mInternalStopReason = parcel.readInt();
        this.debugStopReason = parcel.readString();
    }

    public void setNetwork(android.net.Network network) {
        this.mNetwork = network;
    }

    public void setStopReason(int i, int i2, java.lang.String str) {
        this.mStopReason = i;
        this.mInternalStopReason = i2;
        this.debugStopReason = str;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.jobId);
        parcel.writeString(this.mJobNamespace);
        parcel.writePersistableBundle(this.extras);
        parcel.writeBundle(this.transientExtras);
        if (this.clipData != null) {
            parcel.writeInt(1);
            this.clipData.writeToParcel(parcel, i);
            parcel.writeInt(this.clipGrantFlags);
        } else {
            parcel.writeInt(0);
        }
        parcel.writeStrongBinder(this.callback);
        parcel.writeInt(this.overrideDeadlineExpired ? 1 : 0);
        parcel.writeBoolean(this.mIsExpedited);
        parcel.writeBoolean(this.mIsUserInitiated);
        parcel.writeTypedArray(this.mTriggeredContentUris, i);
        parcel.writeStringArray(this.mTriggeredContentAuthorities);
        if (this.mNetwork != null) {
            parcel.writeInt(1);
            this.mNetwork.writeToParcel(parcel, i);
        } else {
            parcel.writeInt(0);
        }
        parcel.writeInt(this.mStopReason);
        parcel.writeInt(this.mInternalStopReason);
        parcel.writeString(this.debugStopReason);
    }
}
