package android.security.keymaster;

/* loaded from: classes3.dex */
abstract class KeymasterArgument implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.security.keymaster.KeymasterArgument> CREATOR = new android.os.Parcelable.Creator<android.security.keymaster.KeymasterArgument>() { // from class: android.security.keymaster.KeymasterArgument.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.security.keymaster.KeymasterArgument createFromParcel(android.os.Parcel parcel) {
            int dataPosition = parcel.dataPosition();
            int readInt = parcel.readInt();
            switch (android.security.keymaster.KeymasterDefs.getTagType(readInt)) {
                case Integer.MIN_VALUE:
                case -1879048192:
                    return new android.security.keymaster.KeymasterBlobArgument(readInt, parcel);
                case -1610612736:
                case 1342177280:
                    return new android.security.keymaster.KeymasterLongArgument(readInt, parcel);
                case 268435456:
                case 536870912:
                case 805306368:
                case 1073741824:
                    return new android.security.keymaster.KeymasterIntArgument(readInt, parcel);
                case 1610612736:
                    return new android.security.keymaster.KeymasterDateArgument(readInt, parcel);
                case 1879048192:
                    return new android.security.keymaster.KeymasterBooleanArgument(readInt, parcel);
                default:
                    throw new android.os.ParcelFormatException("Bad tag: " + readInt + " at " + dataPosition);
            }
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.security.keymaster.KeymasterArgument[] newArray(int i) {
            return new android.security.keymaster.KeymasterArgument[i];
        }
    };
    public final int tag;

    public abstract void writeValue(android.os.Parcel parcel);

    protected KeymasterArgument(int i) {
        this.tag = i;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.tag);
        writeValue(parcel);
    }
}
