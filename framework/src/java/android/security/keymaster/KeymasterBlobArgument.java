package android.security.keymaster;

/* loaded from: classes3.dex */
class KeymasterBlobArgument extends android.security.keymaster.KeymasterArgument {
    public final byte[] blob;

    public KeymasterBlobArgument(int i, byte[] bArr) {
        super(i);
        switch (android.security.keymaster.KeymasterDefs.getTagType(i)) {
            case Integer.MIN_VALUE:
            case -1879048192:
                this.blob = bArr;
                return;
            default:
                throw new java.lang.IllegalArgumentException("Bad blob tag " + i);
        }
    }

    public KeymasterBlobArgument(int i, android.os.Parcel parcel) {
        super(i);
        this.blob = parcel.createByteArray();
    }

    @Override // android.security.keymaster.KeymasterArgument
    public void writeValue(android.os.Parcel parcel) {
        parcel.writeByteArray(this.blob);
    }
}
