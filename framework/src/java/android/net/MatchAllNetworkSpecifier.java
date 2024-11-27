package android.net;

@android.annotation.SystemApi
/* loaded from: classes2.dex */
public final class MatchAllNetworkSpecifier extends android.net.NetworkSpecifier implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.net.MatchAllNetworkSpecifier> CREATOR = new android.os.Parcelable.Creator<android.net.MatchAllNetworkSpecifier>() { // from class: android.net.MatchAllNetworkSpecifier.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.net.MatchAllNetworkSpecifier createFromParcel(android.os.Parcel parcel) {
            return new android.net.MatchAllNetworkSpecifier();
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.net.MatchAllNetworkSpecifier[] newArray(int i) {
            return new android.net.MatchAllNetworkSpecifier[i];
        }
    };

    @Override // android.net.NetworkSpecifier
    public boolean canBeSatisfiedBy(android.net.NetworkSpecifier networkSpecifier) {
        throw new java.lang.IllegalStateException("MatchAllNetworkSpecifier must not be used in NetworkRequests");
    }

    public boolean equals(java.lang.Object obj) {
        return obj instanceof android.net.MatchAllNetworkSpecifier;
    }

    public int hashCode() {
        return 0;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
    }
}
