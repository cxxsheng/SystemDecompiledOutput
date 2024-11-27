package android.hardware.tv.tuner;

/* loaded from: classes2.dex */
public final class FrontendScanMessageStandard implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.hardware.tv.tuner.FrontendScanMessageStandard> CREATOR = new android.os.Parcelable.Creator<android.hardware.tv.tuner.FrontendScanMessageStandard>() { // from class: android.hardware.tv.tuner.FrontendScanMessageStandard.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.tv.tuner.FrontendScanMessageStandard createFromParcel(android.os.Parcel parcel) {
            return new android.hardware.tv.tuner.FrontendScanMessageStandard(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.tv.tuner.FrontendScanMessageStandard[] newArray(int i) {
            return new android.hardware.tv.tuner.FrontendScanMessageStandard[i];
        }
    };
    public static final int sStd = 0;
    public static final int sifStd = 2;
    public static final int tStd = 1;
    private int _tag;
    private java.lang.Object _value;

    public @interface Tag {
        public static final int sStd = 0;
        public static final int sifStd = 2;
        public static final int tStd = 1;
    }

    public FrontendScanMessageStandard() {
        this._tag = 0;
        this._value = (byte) 0;
    }

    private FrontendScanMessageStandard(android.os.Parcel parcel) {
        readFromParcel(parcel);
    }

    private FrontendScanMessageStandard(int i, java.lang.Object obj) {
        this._tag = i;
        this._value = obj;
    }

    public int getTag() {
        return this._tag;
    }

    public static android.hardware.tv.tuner.FrontendScanMessageStandard sStd(byte b) {
        return new android.hardware.tv.tuner.FrontendScanMessageStandard(0, java.lang.Byte.valueOf(b));
    }

    public byte getSStd() {
        _assertTag(0);
        return ((java.lang.Byte) this._value).byteValue();
    }

    public void setSStd(byte b) {
        _set(0, java.lang.Byte.valueOf(b));
    }

    public static android.hardware.tv.tuner.FrontendScanMessageStandard tStd(byte b) {
        return new android.hardware.tv.tuner.FrontendScanMessageStandard(1, java.lang.Byte.valueOf(b));
    }

    public byte getTStd() {
        _assertTag(1);
        return ((java.lang.Byte) this._value).byteValue();
    }

    public void setTStd(byte b) {
        _set(1, java.lang.Byte.valueOf(b));
    }

    public static android.hardware.tv.tuner.FrontendScanMessageStandard sifStd(int i) {
        return new android.hardware.tv.tuner.FrontendScanMessageStandard(2, java.lang.Integer.valueOf(i));
    }

    public int getSifStd() {
        _assertTag(2);
        return ((java.lang.Integer) this._value).intValue();
    }

    public void setSifStd(int i) {
        _set(2, java.lang.Integer.valueOf(i));
    }

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this._tag);
        switch (this._tag) {
            case 0:
                parcel.writeByte(getSStd());
                break;
            case 1:
                parcel.writeByte(getTStd());
                break;
            case 2:
                parcel.writeInt(getSifStd());
                break;
        }
    }

    public void readFromParcel(android.os.Parcel parcel) {
        int readInt = parcel.readInt();
        switch (readInt) {
            case 0:
                _set(readInt, java.lang.Byte.valueOf(parcel.readByte()));
                return;
            case 1:
                _set(readInt, java.lang.Byte.valueOf(parcel.readByte()));
                return;
            case 2:
                _set(readInt, java.lang.Integer.valueOf(parcel.readInt()));
                return;
            default:
                throw new java.lang.IllegalArgumentException("union: unknown tag: " + readInt);
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        getTag();
        return 0;
    }

    private void _assertTag(int i) {
        if (getTag() != i) {
            throw new java.lang.IllegalStateException("bad access: " + _tagString(i) + ", " + _tagString(getTag()) + " is available.");
        }
    }

    private java.lang.String _tagString(int i) {
        switch (i) {
            case 0:
                return "sStd";
            case 1:
                return "tStd";
            case 2:
                return "sifStd";
            default:
                throw new java.lang.IllegalStateException("unknown field: " + i);
        }
    }

    private void _set(int i, java.lang.Object obj) {
        this._tag = i;
        this._value = obj;
    }
}
