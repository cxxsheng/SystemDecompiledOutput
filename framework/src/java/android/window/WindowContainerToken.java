package android.window;

/* loaded from: classes4.dex */
public final class WindowContainerToken implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.window.WindowContainerToken> CREATOR = new android.os.Parcelable.Creator<android.window.WindowContainerToken>() { // from class: android.window.WindowContainerToken.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.window.WindowContainerToken createFromParcel(android.os.Parcel parcel) {
            return new android.window.WindowContainerToken(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.window.WindowContainerToken[] newArray(int i) {
            return new android.window.WindowContainerToken[i];
        }
    };
    private final android.window.IWindowContainerToken mRealToken;

    public WindowContainerToken(android.window.IWindowContainerToken iWindowContainerToken) {
        this.mRealToken = iWindowContainerToken;
    }

    private WindowContainerToken(android.os.Parcel parcel) {
        this.mRealToken = android.window.IWindowContainerToken.Stub.asInterface(parcel.readStrongBinder());
    }

    public android.os.IBinder asBinder() {
        return this.mRealToken.asBinder();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeStrongBinder(this.mRealToken.asBinder());
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public int hashCode() {
        return this.mRealToken.asBinder().hashCode();
    }

    public java.lang.String toString() {
        return "WCT{" + this.mRealToken + "}";
    }

    public boolean equals(java.lang.Object obj) {
        return (obj instanceof android.window.WindowContainerToken) && this.mRealToken.asBinder() == ((android.window.WindowContainerToken) obj).asBinder();
    }
}
