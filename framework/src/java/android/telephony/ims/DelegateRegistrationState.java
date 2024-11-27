package android.telephony.ims;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public final class DelegateRegistrationState implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.telephony.ims.DelegateRegistrationState> CREATOR = new android.os.Parcelable.Creator<android.telephony.ims.DelegateRegistrationState>() { // from class: android.telephony.ims.DelegateRegistrationState.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.ims.DelegateRegistrationState createFromParcel(android.os.Parcel parcel) {
            return new android.telephony.ims.DelegateRegistrationState(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.ims.DelegateRegistrationState[] newArray(int i) {
            return new android.telephony.ims.DelegateRegistrationState[i];
        }
    };
    public static final int DEREGISTERED_REASON_NOT_PROVISIONED = 1;
    public static final int DEREGISTERED_REASON_NOT_REGISTERED = 2;
    public static final int DEREGISTERED_REASON_UNKNOWN = 0;
    public static final int DEREGISTERING_REASON_DESTROY_PENDING = 6;
    public static final int DEREGISTERING_REASON_FEATURE_TAGS_CHANGING = 5;
    public static final int DEREGISTERING_REASON_LOSING_PDN = 7;
    public static final int DEREGISTERING_REASON_PDN_CHANGE = 3;
    public static final int DEREGISTERING_REASON_PROVISIONING_CHANGE = 4;
    public static final int DEREGISTERING_REASON_UNSPECIFIED = 8;
    private final android.util.ArraySet<android.telephony.ims.FeatureTagState> mDeregisteredTags;
    private final android.util.ArraySet<android.telephony.ims.FeatureTagState> mDeregisteringTags;
    private android.util.ArraySet<java.lang.String> mRegisteredTags;
    private android.util.ArraySet<java.lang.String> mRegisteringTags;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface DeregisteredReason {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface DeregisteringReason {
    }

    public static final class Builder {
        private final android.telephony.ims.DelegateRegistrationState mState = new android.telephony.ims.DelegateRegistrationState();

        public android.telephony.ims.DelegateRegistrationState.Builder addRegisteringFeatureTags(java.util.Set<java.lang.String> set) {
            this.mState.mRegisteringTags.addAll(set);
            return this;
        }

        public android.telephony.ims.DelegateRegistrationState.Builder addRegisteredFeatureTag(java.lang.String str) {
            this.mState.mRegisteredTags.add(str);
            return this;
        }

        public android.telephony.ims.DelegateRegistrationState.Builder addRegisteredFeatureTags(java.util.Set<java.lang.String> set) {
            this.mState.mRegisteredTags.addAll(set);
            return this;
        }

        public android.telephony.ims.DelegateRegistrationState.Builder addDeregisteringFeatureTag(java.lang.String str, int i) {
            this.mState.mDeregisteringTags.add(new android.telephony.ims.FeatureTagState(str, i));
            return this;
        }

        public android.telephony.ims.DelegateRegistrationState.Builder addDeregisteredFeatureTag(java.lang.String str, int i) {
            this.mState.mDeregisteredTags.add(new android.telephony.ims.FeatureTagState(str, i));
            return this;
        }

        public android.telephony.ims.DelegateRegistrationState build() {
            return this.mState;
        }
    }

    private DelegateRegistrationState() {
        this.mRegisteringTags = new android.util.ArraySet<>();
        this.mRegisteredTags = new android.util.ArraySet<>();
        this.mDeregisteringTags = new android.util.ArraySet<>();
        this.mDeregisteredTags = new android.util.ArraySet<>();
    }

    private DelegateRegistrationState(android.os.Parcel parcel) {
        this.mRegisteringTags = new android.util.ArraySet<>();
        this.mRegisteredTags = new android.util.ArraySet<>();
        this.mDeregisteringTags = new android.util.ArraySet<>();
        this.mDeregisteredTags = new android.util.ArraySet<>();
        this.mRegisteredTags = parcel.readArraySet(null);
        readStateFromParcel(parcel, this.mDeregisteringTags);
        readStateFromParcel(parcel, this.mDeregisteredTags);
        this.mRegisteringTags = parcel.readArraySet(null);
    }

    public java.util.Set<java.lang.String> getRegisteringFeatureTags() {
        return new android.util.ArraySet((android.util.ArraySet) this.mRegisteringTags);
    }

    public java.util.Set<java.lang.String> getRegisteredFeatureTags() {
        return new android.util.ArraySet((android.util.ArraySet) this.mRegisteredTags);
    }

    public java.util.Set<android.telephony.ims.FeatureTagState> getDeregisteringFeatureTags() {
        return new android.util.ArraySet((android.util.ArraySet) this.mDeregisteringTags);
    }

    public java.util.Set<android.telephony.ims.FeatureTagState> getDeregisteredFeatureTags() {
        return new android.util.ArraySet((android.util.ArraySet) this.mDeregisteredTags);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeArraySet(this.mRegisteredTags);
        writeStateToParcel(parcel, this.mDeregisteringTags);
        writeStateToParcel(parcel, this.mDeregisteredTags);
        parcel.writeArraySet(this.mRegisteringTags);
    }

    private void writeStateToParcel(android.os.Parcel parcel, java.util.Set<android.telephony.ims.FeatureTagState> set) {
        parcel.writeInt(set.size());
        for (android.telephony.ims.FeatureTagState featureTagState : set) {
            parcel.writeString(featureTagState.getFeatureTag());
            parcel.writeInt(featureTagState.getState());
        }
    }

    private void readStateFromParcel(android.os.Parcel parcel, java.util.Set<android.telephony.ims.FeatureTagState> set) {
        int readInt = parcel.readInt();
        for (int i = 0; i < readInt; i++) {
            set.add(new android.telephony.ims.FeatureTagState(parcel.readString(), parcel.readInt()));
        }
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        android.telephony.ims.DelegateRegistrationState delegateRegistrationState = (android.telephony.ims.DelegateRegistrationState) obj;
        if (this.mRegisteringTags.equals(delegateRegistrationState.mRegisteringTags) && this.mRegisteredTags.equals(delegateRegistrationState.mRegisteredTags) && this.mDeregisteringTags.equals(delegateRegistrationState.mDeregisteringTags) && this.mDeregisteredTags.equals(delegateRegistrationState.mDeregisteredTags)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return java.util.Objects.hash(this.mRegisteringTags, this.mRegisteredTags, this.mDeregisteringTags, this.mDeregisteredTags);
    }

    public java.lang.String toString() {
        return "DelegateRegistrationState{ registered={" + this.mRegisteredTags + "}, registering={" + this.mRegisteringTags + "}, deregistering={" + this.mDeregisteringTags + "}, deregistered={" + this.mDeregisteredTags + "}}";
    }
}
