package android.net;

/* loaded from: classes2.dex */
public final class StringNetworkSpecifier extends android.net.NetworkSpecifier implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.net.StringNetworkSpecifier> CREATOR = new android.os.Parcelable.Creator<android.net.StringNetworkSpecifier>() { // from class: android.net.StringNetworkSpecifier.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.net.StringNetworkSpecifier createFromParcel(android.os.Parcel parcel) {
            return new android.net.StringNetworkSpecifier(parcel.readString());
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.net.StringNetworkSpecifier[] newArray(int i) {
            return new android.net.StringNetworkSpecifier[i];
        }
    };
    public final java.lang.String specifier;

    public StringNetworkSpecifier(java.lang.String str) {
        com.android.internal.util.Preconditions.checkStringNotEmpty(str);
        this.specifier = str;
    }

    @Override // android.net.NetworkSpecifier
    public boolean canBeSatisfiedBy(android.net.NetworkSpecifier networkSpecifier) {
        return equals(networkSpecifier);
    }

    public boolean equals(java.lang.Object obj) {
        if (obj instanceof android.net.StringNetworkSpecifier) {
            return android.text.TextUtils.equals(this.specifier, ((android.net.StringNetworkSpecifier) obj).specifier);
        }
        return false;
    }

    public int hashCode() {
        return java.util.Objects.hashCode(this.specifier);
    }

    public java.lang.String toString() {
        return this.specifier;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeString(this.specifier);
    }
}
