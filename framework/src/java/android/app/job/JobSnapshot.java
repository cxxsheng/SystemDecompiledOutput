package android.app.job;

/* loaded from: classes.dex */
public class JobSnapshot implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.app.job.JobSnapshot> CREATOR = new android.os.Parcelable.Creator<android.app.job.JobSnapshot>() { // from class: android.app.job.JobSnapshot.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.job.JobSnapshot createFromParcel(android.os.Parcel parcel) {
            return new android.app.job.JobSnapshot(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.job.JobSnapshot[] newArray(int i) {
            return new android.app.job.JobSnapshot[i];
        }
    };
    private final boolean mIsRunnable;
    private final android.app.job.JobInfo mJob;
    private final int mSatisfiedConstraints;

    public JobSnapshot(android.app.job.JobInfo jobInfo, int i, boolean z) {
        this.mJob = jobInfo;
        this.mSatisfiedConstraints = i;
        this.mIsRunnable = z;
    }

    public JobSnapshot(android.os.Parcel parcel) {
        this.mJob = android.app.job.JobInfo.CREATOR.createFromParcel(parcel);
        this.mSatisfiedConstraints = parcel.readInt();
        this.mIsRunnable = parcel.readBoolean();
    }

    private boolean satisfied(int i) {
        return (i & this.mSatisfiedConstraints) != 0;
    }

    public android.app.job.JobInfo getJobInfo() {
        return this.mJob;
    }

    public boolean isRunnable() {
        return this.mIsRunnable;
    }

    public boolean isChargingSatisfied() {
        return !this.mJob.isRequireCharging() || satisfied(1);
    }

    public boolean isBatteryNotLowSatisfied() {
        return !this.mJob.isRequireBatteryNotLow() || satisfied(2);
    }

    public boolean isRequireDeviceIdleSatisfied() {
        return !this.mJob.isRequireDeviceIdle() || satisfied(4);
    }

    public boolean isRequireStorageNotLowSatisfied() {
        return !this.mJob.isRequireStorageNotLow() || satisfied(8);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        this.mJob.writeToParcel(parcel, i);
        parcel.writeInt(this.mSatisfiedConstraints);
        parcel.writeBoolean(this.mIsRunnable);
    }
}
