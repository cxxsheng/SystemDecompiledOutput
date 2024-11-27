package android.net;

/* loaded from: classes2.dex */
public final class TelephonyNetworkSpecifier extends android.net.NetworkSpecifier implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.net.TelephonyNetworkSpecifier> CREATOR = new android.os.Parcelable.Creator<android.net.TelephonyNetworkSpecifier>() { // from class: android.net.TelephonyNetworkSpecifier.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.net.TelephonyNetworkSpecifier createFromParcel(android.os.Parcel parcel) {
            return new android.net.TelephonyNetworkSpecifier(parcel.readInt());
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.net.TelephonyNetworkSpecifier[] newArray(int i) {
            return new android.net.TelephonyNetworkSpecifier[i];
        }
    };
    private final int mSubId;

    public int getSubscriptionId() {
        return this.mSubId;
    }

    public TelephonyNetworkSpecifier(int i) {
        this.mSubId = i;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mSubId);
    }

    public int hashCode() {
        return this.mSubId;
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof android.net.TelephonyNetworkSpecifier) && this.mSubId == ((android.net.TelephonyNetworkSpecifier) obj).mSubId;
    }

    public java.lang.String toString() {
        return "TelephonyNetworkSpecifier [mSubId = " + this.mSubId + android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_END;
    }

    @Override // android.net.NetworkSpecifier
    public boolean canBeSatisfiedBy(android.net.NetworkSpecifier networkSpecifier) {
        return equals(networkSpecifier) || (networkSpecifier instanceof android.net.MatchAllNetworkSpecifier);
    }

    public static final class Builder {
        private static final int SENTINEL_SUB_ID = Integer.MIN_VALUE;
        private int mSubId = Integer.MIN_VALUE;

        public android.net.TelephonyNetworkSpecifier.Builder setSubscriptionId(int i) {
            this.mSubId = i;
            return this;
        }

        public android.net.TelephonyNetworkSpecifier build() {
            if (this.mSubId == Integer.MIN_VALUE) {
                throw new java.lang.IllegalArgumentException("Subscription Id is not provided.");
            }
            return new android.net.TelephonyNetworkSpecifier(this.mSubId);
        }
    }
}
