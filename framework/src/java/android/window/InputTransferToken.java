package android.window;

/* loaded from: classes4.dex */
public final class InputTransferToken implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.window.InputTransferToken> CREATOR = new android.os.Parcelable.Creator<android.window.InputTransferToken>() { // from class: android.window.InputTransferToken.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.window.InputTransferToken createFromParcel(android.os.Parcel parcel) {
            return new android.window.InputTransferToken(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.window.InputTransferToken[] newArray(int i) {
            return new android.window.InputTransferToken[i];
        }
    };
    public final android.os.IBinder mToken;

    public InputTransferToken(android.os.IBinder iBinder) {
        this.mToken = iBinder;
    }

    public InputTransferToken() {
        this.mToken = new android.os.Binder();
    }

    private InputTransferToken(android.os.Parcel parcel) {
        this.mToken = parcel.readStrongBinder();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeStrongBinder(this.mToken);
    }

    public int hashCode() {
        return java.util.Objects.hash(this.mToken);
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass() && ((android.window.InputTransferToken) obj).mToken == this.mToken) {
            return true;
        }
        return false;
    }
}
