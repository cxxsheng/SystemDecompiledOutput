package android.os;

/* loaded from: classes3.dex */
public final class WorkDuration implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.os.WorkDuration> CREATOR = new android.os.Parcelable.Creator<android.os.WorkDuration>() { // from class: android.os.WorkDuration.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.os.WorkDuration createFromParcel(android.os.Parcel parcel) {
            return new android.os.WorkDuration(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.os.WorkDuration[] newArray(int i) {
            return new android.os.WorkDuration[i];
        }
    };
    long mActualCpuDurationNanos;
    long mActualGpuDurationNanos;
    long mActualTotalDurationNanos;
    long mTimestampNanos;
    long mWorkPeriodStartTimestampNanos;

    public WorkDuration() {
        this.mWorkPeriodStartTimestampNanos = 0L;
        this.mActualTotalDurationNanos = 0L;
        this.mActualCpuDurationNanos = 0L;
        this.mActualGpuDurationNanos = 0L;
        this.mTimestampNanos = 0L;
    }

    public WorkDuration(long j, long j2, long j3, long j4, long j5) {
        this.mWorkPeriodStartTimestampNanos = 0L;
        this.mActualTotalDurationNanos = 0L;
        this.mActualCpuDurationNanos = 0L;
        this.mActualGpuDurationNanos = 0L;
        this.mTimestampNanos = 0L;
        this.mWorkPeriodStartTimestampNanos = j;
        this.mActualTotalDurationNanos = j2;
        this.mActualCpuDurationNanos = j3;
        this.mActualGpuDurationNanos = j4;
        this.mTimestampNanos = j5;
    }

    WorkDuration(android.os.Parcel parcel) {
        this.mWorkPeriodStartTimestampNanos = 0L;
        this.mActualTotalDurationNanos = 0L;
        this.mActualCpuDurationNanos = 0L;
        this.mActualGpuDurationNanos = 0L;
        this.mTimestampNanos = 0L;
        this.mWorkPeriodStartTimestampNanos = parcel.readLong();
        this.mActualTotalDurationNanos = parcel.readLong();
        this.mActualCpuDurationNanos = parcel.readLong();
        this.mActualGpuDurationNanos = parcel.readLong();
        this.mTimestampNanos = parcel.readLong();
    }

    public void setWorkPeriodStartTimestampNanos(long j) {
        if (j <= 0) {
            throw new java.lang.IllegalArgumentException("the work period start timestamp should be greater than zero.");
        }
        this.mWorkPeriodStartTimestampNanos = j;
    }

    public void setActualTotalDurationNanos(long j) {
        if (j <= 0) {
            throw new java.lang.IllegalArgumentException("the actual total duration should be greater than zero.");
        }
        this.mActualTotalDurationNanos = j;
    }

    public void setActualCpuDurationNanos(long j) {
        if (j < 0) {
            throw new java.lang.IllegalArgumentException("the actual CPU duration should be greater than or equal to zero.");
        }
        this.mActualCpuDurationNanos = j;
    }

    public void setActualGpuDurationNanos(long j) {
        if (j < 0) {
            throw new java.lang.IllegalArgumentException("the actual GPU duration should be greater than or equal to zero.");
        }
        this.mActualGpuDurationNanos = j;
    }

    public long getWorkPeriodStartTimestampNanos() {
        return this.mWorkPeriodStartTimestampNanos;
    }

    public long getActualTotalDurationNanos() {
        return this.mActualTotalDurationNanos;
    }

    public long getActualCpuDurationNanos() {
        return this.mActualCpuDurationNanos;
    }

    public long getActualGpuDurationNanos() {
        return this.mActualGpuDurationNanos;
    }

    public long getTimestampNanos() {
        return this.mTimestampNanos;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeLong(this.mWorkPeriodStartTimestampNanos);
        parcel.writeLong(this.mActualTotalDurationNanos);
        parcel.writeLong(this.mActualCpuDurationNanos);
        parcel.writeLong(this.mActualGpuDurationNanos);
        parcel.writeLong(this.mTimestampNanos);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public boolean equals(java.lang.Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof android.os.WorkDuration)) {
            return false;
        }
        android.os.WorkDuration workDuration = (android.os.WorkDuration) obj;
        return workDuration.mTimestampNanos == this.mTimestampNanos && workDuration.mWorkPeriodStartTimestampNanos == this.mWorkPeriodStartTimestampNanos && workDuration.mActualTotalDurationNanos == this.mActualTotalDurationNanos && workDuration.mActualCpuDurationNanos == this.mActualCpuDurationNanos && workDuration.mActualGpuDurationNanos == this.mActualGpuDurationNanos;
    }

    public int hashCode() {
        return java.util.Objects.hash(java.lang.Long.valueOf(this.mWorkPeriodStartTimestampNanos), java.lang.Long.valueOf(this.mActualTotalDurationNanos), java.lang.Long.valueOf(this.mActualCpuDurationNanos), java.lang.Long.valueOf(this.mActualGpuDurationNanos), java.lang.Long.valueOf(this.mTimestampNanos));
    }
}
