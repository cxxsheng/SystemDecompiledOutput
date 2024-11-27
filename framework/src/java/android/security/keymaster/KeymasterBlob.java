package android.security.keymaster;

/* loaded from: classes3.dex */
public class KeymasterBlob implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.security.keymaster.KeymasterBlob> CREATOR = new android.os.Parcelable.Creator<android.security.keymaster.KeymasterBlob>() { // from class: android.security.keymaster.KeymasterBlob.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.security.keymaster.KeymasterBlob createFromParcel(android.os.Parcel parcel) {
            return new android.security.keymaster.KeymasterBlob(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.security.keymaster.KeymasterBlob[] newArray(int i) {
            return new android.security.keymaster.KeymasterBlob[i];
        }
    };
    public byte[] blob;

    public KeymasterBlob(byte[] bArr) {
        this.blob = bArr;
    }

    protected KeymasterBlob(android.os.Parcel parcel) {
        this.blob = parcel.createByteArray();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeByteArray(this.blob);
    }
}
