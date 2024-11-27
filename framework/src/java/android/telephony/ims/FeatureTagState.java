package android.telephony.ims;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public final class FeatureTagState implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.telephony.ims.FeatureTagState> CREATOR = new android.os.Parcelable.Creator<android.telephony.ims.FeatureTagState>() { // from class: android.telephony.ims.FeatureTagState.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.ims.FeatureTagState createFromParcel(android.os.Parcel parcel) {
            return new android.telephony.ims.FeatureTagState(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.ims.FeatureTagState[] newArray(int i) {
            return new android.telephony.ims.FeatureTagState[i];
        }
    };
    private final java.lang.String mFeatureTag;
    private final int mState;

    public FeatureTagState(java.lang.String str, int i) {
        this.mFeatureTag = str;
        this.mState = i;
    }

    private FeatureTagState(android.os.Parcel parcel) {
        this.mFeatureTag = parcel.readString();
        this.mState = parcel.readInt();
    }

    public java.lang.String getFeatureTag() {
        return this.mFeatureTag;
    }

    public int getState() {
        return this.mState;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeString(this.mFeatureTag);
        parcel.writeInt(this.mState);
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        android.telephony.ims.FeatureTagState featureTagState = (android.telephony.ims.FeatureTagState) obj;
        if (this.mState == featureTagState.mState && this.mFeatureTag.equals(featureTagState.mFeatureTag)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return java.util.Objects.hash(this.mFeatureTag, java.lang.Integer.valueOf(this.mState));
    }

    public java.lang.String toString() {
        return "FeatureTagState{mFeatureTag='" + this.mFeatureTag + ", mState=" + this.mState + '}';
    }
}
