package android.security.keymaster;

/* loaded from: classes3.dex */
class KeymasterDateArgument extends android.security.keymaster.KeymasterArgument {
    public final java.util.Date date;

    public KeymasterDateArgument(int i, java.util.Date date) {
        super(i);
        switch (android.security.keymaster.KeymasterDefs.getTagType(i)) {
            case 1610612736:
                this.date = date;
                return;
            default:
                throw new java.lang.IllegalArgumentException("Bad date tag " + i);
        }
    }

    public KeymasterDateArgument(int i, android.os.Parcel parcel) {
        super(i);
        this.date = new java.util.Date(parcel.readLong());
    }

    @Override // android.security.keymaster.KeymasterArgument
    public void writeValue(android.os.Parcel parcel) {
        parcel.writeLong(this.date.getTime());
    }
}
