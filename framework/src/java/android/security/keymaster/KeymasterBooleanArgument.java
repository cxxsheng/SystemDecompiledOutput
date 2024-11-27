package android.security.keymaster;

/* loaded from: classes3.dex */
class KeymasterBooleanArgument extends android.security.keymaster.KeymasterArgument {
    public final boolean value;

    public KeymasterBooleanArgument(int i) {
        super(i);
        this.value = true;
        switch (android.security.keymaster.KeymasterDefs.getTagType(i)) {
            case 1879048192:
                return;
            default:
                throw new java.lang.IllegalArgumentException("Bad bool tag " + i);
        }
    }

    public KeymasterBooleanArgument(int i, android.os.Parcel parcel) {
        super(i);
        this.value = true;
    }

    @Override // android.security.keymaster.KeymasterArgument
    public void writeValue(android.os.Parcel parcel) {
    }
}
