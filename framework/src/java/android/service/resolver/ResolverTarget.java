package android.service.resolver;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public final class ResolverTarget implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.service.resolver.ResolverTarget> CREATOR = new android.os.Parcelable.Creator<android.service.resolver.ResolverTarget>() { // from class: android.service.resolver.ResolverTarget.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.service.resolver.ResolverTarget createFromParcel(android.os.Parcel parcel) {
            return new android.service.resolver.ResolverTarget(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.service.resolver.ResolverTarget[] newArray(int i) {
            return new android.service.resolver.ResolverTarget[i];
        }
    };
    private static final java.lang.String TAG = "ResolverTarget";
    private float mChooserScore;
    private float mLaunchScore;
    private float mRecencyScore;
    private float mSelectProbability;
    private float mTimeSpentScore;

    public ResolverTarget() {
    }

    ResolverTarget(android.os.Parcel parcel) {
        this.mRecencyScore = parcel.readFloat();
        this.mTimeSpentScore = parcel.readFloat();
        this.mLaunchScore = parcel.readFloat();
        this.mChooserScore = parcel.readFloat();
        this.mSelectProbability = parcel.readFloat();
    }

    public float getRecencyScore() {
        return this.mRecencyScore;
    }

    public void setRecencyScore(float f) {
        this.mRecencyScore = f;
    }

    public float getTimeSpentScore() {
        return this.mTimeSpentScore;
    }

    public void setTimeSpentScore(float f) {
        this.mTimeSpentScore = f;
    }

    public float getLaunchScore() {
        return this.mLaunchScore;
    }

    public void setLaunchScore(float f) {
        this.mLaunchScore = f;
    }

    public float getChooserScore() {
        return this.mChooserScore;
    }

    public void setChooserScore(float f) {
        this.mChooserScore = f;
    }

    public float getSelectProbability() {
        return this.mSelectProbability;
    }

    public void setSelectProbability(float f) {
        this.mSelectProbability = f;
    }

    public java.lang.String toString() {
        return "ResolverTarget{" + this.mRecencyScore + ", " + this.mTimeSpentScore + ", " + this.mLaunchScore + ", " + this.mChooserScore + ", " + this.mSelectProbability + "}";
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeFloat(this.mRecencyScore);
        parcel.writeFloat(this.mTimeSpentScore);
        parcel.writeFloat(this.mLaunchScore);
        parcel.writeFloat(this.mChooserScore);
        parcel.writeFloat(this.mSelectProbability);
    }
}
