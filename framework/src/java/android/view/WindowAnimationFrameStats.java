package android.view;

@java.lang.Deprecated
/* loaded from: classes4.dex */
public final class WindowAnimationFrameStats extends android.view.FrameStats implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.view.WindowAnimationFrameStats> CREATOR = new android.os.Parcelable.Creator<android.view.WindowAnimationFrameStats>() { // from class: android.view.WindowAnimationFrameStats.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.view.WindowAnimationFrameStats createFromParcel(android.os.Parcel parcel) {
            return new android.view.WindowAnimationFrameStats(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.view.WindowAnimationFrameStats[] newArray(int i) {
            return new android.view.WindowAnimationFrameStats[i];
        }
    };

    public WindowAnimationFrameStats() {
    }

    public void init(long j, long[] jArr) {
        this.mRefreshPeriodNano = j;
        this.mFramesPresentedTimeNano = jArr;
    }

    private WindowAnimationFrameStats(android.os.Parcel parcel) {
        this.mRefreshPeriodNano = parcel.readLong();
        this.mFramesPresentedTimeNano = parcel.createLongArray();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeLong(this.mRefreshPeriodNano);
        parcel.writeLongArray(this.mFramesPresentedTimeNano);
    }

    public java.lang.String toString() {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append("WindowAnimationFrameStats[");
        sb.append("frameCount:" + getFrameCount());
        sb.append(", fromTimeNano:" + getStartTimeNano());
        sb.append(", toTimeNano:" + getEndTimeNano());
        sb.append(']');
        return sb.toString();
    }
}
