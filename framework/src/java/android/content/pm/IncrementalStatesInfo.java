package android.content.pm;

/* loaded from: classes.dex */
public class IncrementalStatesInfo implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.content.pm.IncrementalStatesInfo> CREATOR = new android.os.Parcelable.Creator<android.content.pm.IncrementalStatesInfo>() { // from class: android.content.pm.IncrementalStatesInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.content.pm.IncrementalStatesInfo createFromParcel(android.os.Parcel parcel) {
            return new android.content.pm.IncrementalStatesInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.content.pm.IncrementalStatesInfo[] newArray(int i) {
            return new android.content.pm.IncrementalStatesInfo[i];
        }
    };
    private final boolean mIsLoading;
    private long mLoadingCompletedTime;
    private final float mProgress;

    public IncrementalStatesInfo(boolean z, float f, long j) {
        this.mIsLoading = z;
        this.mProgress = f;
        this.mLoadingCompletedTime = j;
    }

    private IncrementalStatesInfo(android.os.Parcel parcel) {
        this.mIsLoading = parcel.readBoolean();
        this.mProgress = parcel.readFloat();
        this.mLoadingCompletedTime = parcel.readLong();
    }

    public boolean isLoading() {
        return this.mIsLoading;
    }

    public float getProgress() {
        return this.mProgress;
    }

    public long getLoadingCompletedTime() {
        return this.mLoadingCompletedTime;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeBoolean(this.mIsLoading);
        parcel.writeFloat(this.mProgress);
        parcel.writeLong(this.mLoadingCompletedTime);
    }
}
