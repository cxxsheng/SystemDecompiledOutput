package android.view;

/* loaded from: classes4.dex */
public final class WindowContentFrameStats extends android.view.FrameStats implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.view.WindowContentFrameStats> CREATOR = new android.os.Parcelable.Creator<android.view.WindowContentFrameStats>() { // from class: android.view.WindowContentFrameStats.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.view.WindowContentFrameStats createFromParcel(android.os.Parcel parcel) {
            return new android.view.WindowContentFrameStats(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.view.WindowContentFrameStats[] newArray(int i) {
            return new android.view.WindowContentFrameStats[i];
        }
    };
    private long[] mFramesPostedTimeNano;
    private long[] mFramesReadyTimeNano;

    public WindowContentFrameStats() {
    }

    public void init(long j, long[] jArr, long[] jArr2, long[] jArr3) {
        this.mRefreshPeriodNano = j;
        this.mFramesPostedTimeNano = jArr;
        this.mFramesPresentedTimeNano = jArr2;
        this.mFramesReadyTimeNano = jArr3;
    }

    private WindowContentFrameStats(android.os.Parcel parcel) {
        this.mRefreshPeriodNano = parcel.readLong();
        this.mFramesPostedTimeNano = parcel.createLongArray();
        this.mFramesPresentedTimeNano = parcel.createLongArray();
        this.mFramesReadyTimeNano = parcel.createLongArray();
    }

    public long getFramePostedTimeNano(int i) {
        if (this.mFramesPostedTimeNano == null) {
            throw new java.lang.IndexOutOfBoundsException();
        }
        return this.mFramesPostedTimeNano[i];
    }

    public long getFrameReadyTimeNano(int i) {
        if (this.mFramesReadyTimeNano == null) {
            throw new java.lang.IndexOutOfBoundsException();
        }
        return this.mFramesReadyTimeNano[i];
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeLong(this.mRefreshPeriodNano);
        parcel.writeLongArray(this.mFramesPostedTimeNano);
        parcel.writeLongArray(this.mFramesPresentedTimeNano);
        parcel.writeLongArray(this.mFramesReadyTimeNano);
    }

    public java.lang.String toString() {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append("WindowContentFrameStats[");
        sb.append("frameCount:" + getFrameCount());
        sb.append(", fromTimeNano:" + getStartTimeNano());
        sb.append(", toTimeNano:" + getEndTimeNano());
        sb.append(']');
        return sb.toString();
    }
}
