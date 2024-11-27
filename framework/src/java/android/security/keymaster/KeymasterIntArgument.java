package android.security.keymaster;

/* loaded from: classes3.dex */
class KeymasterIntArgument extends android.security.keymaster.KeymasterArgument {
    public final int value;

    public KeymasterIntArgument(int i, int i2) {
        super(i);
        switch (android.security.keymaster.KeymasterDefs.getTagType(i)) {
            case 268435456:
            case 536870912:
            case 805306368:
            case 1073741824:
                this.value = i2;
                return;
            default:
                throw new java.lang.IllegalArgumentException("Bad int tag " + i);
        }
    }

    public KeymasterIntArgument(int i, android.os.Parcel parcel) {
        super(i);
        this.value = parcel.readInt();
    }

    @Override // android.security.keymaster.KeymasterArgument
    public void writeValue(android.os.Parcel parcel) {
        parcel.writeInt(this.value);
    }
}
