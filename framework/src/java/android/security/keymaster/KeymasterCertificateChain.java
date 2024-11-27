package android.security.keymaster;

/* loaded from: classes3.dex */
public class KeymasterCertificateChain implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.security.keymaster.KeymasterCertificateChain> CREATOR = new android.os.Parcelable.Creator<android.security.keymaster.KeymasterCertificateChain>() { // from class: android.security.keymaster.KeymasterCertificateChain.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.security.keymaster.KeymasterCertificateChain createFromParcel(android.os.Parcel parcel) {
            return new android.security.keymaster.KeymasterCertificateChain(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.security.keymaster.KeymasterCertificateChain[] newArray(int i) {
            return new android.security.keymaster.KeymasterCertificateChain[i];
        }
    };
    private java.util.List<byte[]> mCertificates;

    public KeymasterCertificateChain() {
        this.mCertificates = null;
    }

    public KeymasterCertificateChain(java.util.List<byte[]> list) {
        this.mCertificates = list;
    }

    private KeymasterCertificateChain(android.os.Parcel parcel) {
        readFromParcel(parcel);
    }

    public void shallowCopyFrom(android.security.keymaster.KeymasterCertificateChain keymasterCertificateChain) {
        this.mCertificates = keymasterCertificateChain.mCertificates;
    }

    public java.util.List<byte[]> getCertificates() {
        return this.mCertificates;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        if (this.mCertificates == null) {
            parcel.writeInt(0);
            return;
        }
        parcel.writeInt(this.mCertificates.size());
        java.util.Iterator<byte[]> it = this.mCertificates.iterator();
        while (it.hasNext()) {
            parcel.writeByteArray(it.next());
        }
    }

    public void readFromParcel(android.os.Parcel parcel) {
        int readInt = parcel.readInt();
        this.mCertificates = new java.util.ArrayList(readInt);
        for (int i = 0; i < readInt; i++) {
            this.mCertificates.add(parcel.createByteArray());
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }
}
