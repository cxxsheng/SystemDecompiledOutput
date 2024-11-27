package android.security.keymaster;

/* loaded from: classes3.dex */
class KeymasterLongArgument extends android.security.keymaster.KeymasterArgument {
    public final long value;

    public KeymasterLongArgument(int i, long j) {
        super(i);
        switch (android.security.keymaster.KeymasterDefs.getTagType(i)) {
            case -1610612736:
            case 1342177280:
                this.value = j;
                return;
            default:
                throw new java.lang.IllegalArgumentException("Bad long tag " + i);
        }
    }

    public KeymasterLongArgument(int i, android.os.Parcel parcel) {
        super(i);
        this.value = parcel.readLong();
    }

    @Override // android.security.keymaster.KeymasterArgument
    public void writeValue(android.os.Parcel parcel) {
        parcel.writeLong(this.value);
    }
}
