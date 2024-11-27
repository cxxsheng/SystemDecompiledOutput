package android.content.pm;

/* loaded from: classes.dex */
public final class FeatureGroupInfo implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.content.pm.FeatureGroupInfo> CREATOR = new android.os.Parcelable.Creator<android.content.pm.FeatureGroupInfo>() { // from class: android.content.pm.FeatureGroupInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.content.pm.FeatureGroupInfo createFromParcel(android.os.Parcel parcel) {
            android.content.pm.FeatureGroupInfo featureGroupInfo = new android.content.pm.FeatureGroupInfo();
            featureGroupInfo.features = (android.content.pm.FeatureInfo[]) parcel.createTypedArray(android.content.pm.FeatureInfo.CREATOR);
            return featureGroupInfo;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.content.pm.FeatureGroupInfo[] newArray(int i) {
            return new android.content.pm.FeatureGroupInfo[i];
        }
    };
    public android.content.pm.FeatureInfo[] features;

    public FeatureGroupInfo() {
    }

    public FeatureGroupInfo(android.content.pm.FeatureGroupInfo featureGroupInfo) {
        this.features = featureGroupInfo.features;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeTypedArray(this.features, i);
    }
}
