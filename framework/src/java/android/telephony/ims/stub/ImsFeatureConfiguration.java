package android.telephony.ims.stub;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public final class ImsFeatureConfiguration implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.telephony.ims.stub.ImsFeatureConfiguration> CREATOR = new android.os.Parcelable.Creator<android.telephony.ims.stub.ImsFeatureConfiguration>() { // from class: android.telephony.ims.stub.ImsFeatureConfiguration.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.ims.stub.ImsFeatureConfiguration createFromParcel(android.os.Parcel parcel) {
            return new android.telephony.ims.stub.ImsFeatureConfiguration(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.ims.stub.ImsFeatureConfiguration[] newArray(int i) {
            return new android.telephony.ims.stub.ImsFeatureConfiguration[i];
        }
    };
    private final java.util.Set<android.telephony.ims.stub.ImsFeatureConfiguration.FeatureSlotPair> mFeatures;

    public static final class FeatureSlotPair {
        public final int featureType;
        public final int slotId;

        public FeatureSlotPair(int i, int i2) {
            this.slotId = i;
            this.featureType = i2;
        }

        public boolean equals(java.lang.Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            android.telephony.ims.stub.ImsFeatureConfiguration.FeatureSlotPair featureSlotPair = (android.telephony.ims.stub.ImsFeatureConfiguration.FeatureSlotPair) obj;
            if (this.slotId == featureSlotPair.slotId && this.featureType == featureSlotPair.featureType) {
                return true;
            }
            return false;
        }

        public int hashCode() {
            return (this.slotId * 31) + this.featureType;
        }

        public java.lang.String toString() {
            return "{s=" + this.slotId + ", f=" + android.telephony.ims.feature.ImsFeature.FEATURE_LOG_MAP.get(java.lang.Integer.valueOf(this.featureType)) + "}";
        }
    }

    public static class Builder {
        android.telephony.ims.stub.ImsFeatureConfiguration mConfig = new android.telephony.ims.stub.ImsFeatureConfiguration();

        public android.telephony.ims.stub.ImsFeatureConfiguration.Builder addFeature(int i, int i2) {
            this.mConfig.addFeature(i, i2);
            return this;
        }

        public android.telephony.ims.stub.ImsFeatureConfiguration build() {
            return this.mConfig;
        }
    }

    public ImsFeatureConfiguration() {
        this.mFeatures = new android.util.ArraySet();
    }

    public ImsFeatureConfiguration(java.util.Set<android.telephony.ims.stub.ImsFeatureConfiguration.FeatureSlotPair> set) {
        this.mFeatures = new android.util.ArraySet();
        if (set != null) {
            this.mFeatures.addAll(set);
        }
    }

    public java.util.Set<android.telephony.ims.stub.ImsFeatureConfiguration.FeatureSlotPair> getServiceFeatures() {
        return new android.util.ArraySet(this.mFeatures);
    }

    void addFeature(int i, int i2) {
        this.mFeatures.add(new android.telephony.ims.stub.ImsFeatureConfiguration.FeatureSlotPair(i, i2));
    }

    protected ImsFeatureConfiguration(android.os.Parcel parcel) {
        int readInt = parcel.readInt();
        this.mFeatures = new android.util.ArraySet(readInt);
        for (int i = 0; i < readInt; i++) {
            this.mFeatures.add(new android.telephony.ims.stub.ImsFeatureConfiguration.FeatureSlotPair(parcel.readInt(), parcel.readInt()));
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        int size = this.mFeatures.size();
        android.telephony.ims.stub.ImsFeatureConfiguration.FeatureSlotPair[] featureSlotPairArr = new android.telephony.ims.stub.ImsFeatureConfiguration.FeatureSlotPair[size];
        this.mFeatures.toArray(featureSlotPairArr);
        parcel.writeInt(size);
        for (int i2 = 0; i2 < size; i2++) {
            android.telephony.ims.stub.ImsFeatureConfiguration.FeatureSlotPair featureSlotPair = featureSlotPairArr[i2];
            parcel.writeInt(featureSlotPair.slotId);
            parcel.writeInt(featureSlotPair.featureType);
        }
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof android.telephony.ims.stub.ImsFeatureConfiguration) {
            return this.mFeatures.equals(((android.telephony.ims.stub.ImsFeatureConfiguration) obj).mFeatures);
        }
        return false;
    }

    public int hashCode() {
        return this.mFeatures.hashCode();
    }
}
