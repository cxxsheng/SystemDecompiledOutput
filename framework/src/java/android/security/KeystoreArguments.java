package android.security;

/* loaded from: classes3.dex */
public class KeystoreArguments implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.security.KeystoreArguments> CREATOR = new android.os.Parcelable.Creator<android.security.KeystoreArguments>() { // from class: android.security.KeystoreArguments.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.security.KeystoreArguments createFromParcel(android.os.Parcel parcel) {
            return new android.security.KeystoreArguments(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.security.KeystoreArguments[] newArray(int i) {
            return new android.security.KeystoreArguments[i];
        }
    };
    public byte[][] args;

    public KeystoreArguments() {
        this.args = null;
    }

    public KeystoreArguments(byte[][] bArr) {
        this.args = bArr;
    }

    private KeystoreArguments(android.os.Parcel parcel) {
        readFromParcel(parcel);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        if (this.args == null) {
            parcel.writeInt(0);
            return;
        }
        parcel.writeInt(this.args.length);
        for (byte[] bArr : this.args) {
            parcel.writeByteArray(bArr);
        }
    }

    private void readFromParcel(android.os.Parcel parcel) {
        int readInt = parcel.readInt();
        this.args = new byte[readInt][];
        for (int i = 0; i < readInt; i++) {
            this.args[i] = parcel.createByteArray();
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }
}
