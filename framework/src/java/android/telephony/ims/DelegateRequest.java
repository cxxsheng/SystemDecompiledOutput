package android.telephony.ims;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public final class DelegateRequest implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.telephony.ims.DelegateRequest> CREATOR = new android.os.Parcelable.Creator<android.telephony.ims.DelegateRequest>() { // from class: android.telephony.ims.DelegateRequest.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.ims.DelegateRequest createFromParcel(android.os.Parcel parcel) {
            return new android.telephony.ims.DelegateRequest(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.ims.DelegateRequest[] newArray(int i) {
            return new android.telephony.ims.DelegateRequest[i];
        }
    };
    private final java.util.ArrayList<java.lang.String> mFeatureTags;

    public DelegateRequest(java.util.Set<java.lang.String> set) {
        if (set == null) {
            throw new java.lang.IllegalStateException("Invalid arguments, featureTags List can not be null");
        }
        this.mFeatureTags = new java.util.ArrayList<>(set);
    }

    public java.util.Set<java.lang.String> getFeatureTags() {
        return new android.util.ArraySet(this.mFeatureTags);
    }

    private DelegateRequest(android.os.Parcel parcel) {
        this.mFeatureTags = new java.util.ArrayList<>();
        parcel.readList(this.mFeatureTags, null, java.lang.String.class);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeList(this.mFeatureTags);
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return this.mFeatureTags.equals(((android.telephony.ims.DelegateRequest) obj).mFeatureTags);
    }

    public int hashCode() {
        return java.util.Objects.hash(this.mFeatureTags);
    }

    public java.lang.String toString() {
        return "DelegateRequest{mFeatureTags=" + this.mFeatureTags + '}';
    }
}
