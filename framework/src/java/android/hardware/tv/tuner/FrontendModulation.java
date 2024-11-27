package android.hardware.tv.tuner;

/* loaded from: classes2.dex */
public final class FrontendModulation implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.hardware.tv.tuner.FrontendModulation> CREATOR = new android.os.Parcelable.Creator<android.hardware.tv.tuner.FrontendModulation>() { // from class: android.hardware.tv.tuner.FrontendModulation.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.tv.tuner.FrontendModulation createFromParcel(android.os.Parcel parcel) {
            return new android.hardware.tv.tuner.FrontendModulation(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.tv.tuner.FrontendModulation[] newArray(int i) {
            return new android.hardware.tv.tuner.FrontendModulation[i];
        }
    };
    public static final int atsc = 6;
    public static final int atsc3 = 7;
    public static final int dtmb = 8;
    public static final int dvbc = 0;
    public static final int dvbs = 1;
    public static final int dvbt = 2;
    public static final int isdbs = 3;
    public static final int isdbs3 = 4;
    public static final int isdbt = 5;
    private int _tag;
    private java.lang.Object _value;

    public @interface Tag {
        public static final int atsc = 6;
        public static final int atsc3 = 7;
        public static final int dtmb = 8;
        public static final int dvbc = 0;
        public static final int dvbs = 1;
        public static final int dvbt = 2;
        public static final int isdbs = 3;
        public static final int isdbs3 = 4;
        public static final int isdbt = 5;
    }

    public FrontendModulation() {
        this._tag = 0;
        this._value = 0;
    }

    private FrontendModulation(android.os.Parcel parcel) {
        readFromParcel(parcel);
    }

    private FrontendModulation(int i, java.lang.Object obj) {
        this._tag = i;
        this._value = obj;
    }

    public int getTag() {
        return this._tag;
    }

    public static android.hardware.tv.tuner.FrontendModulation dvbc(int i) {
        return new android.hardware.tv.tuner.FrontendModulation(0, java.lang.Integer.valueOf(i));
    }

    public int getDvbc() {
        _assertTag(0);
        return ((java.lang.Integer) this._value).intValue();
    }

    public void setDvbc(int i) {
        _set(0, java.lang.Integer.valueOf(i));
    }

    public static android.hardware.tv.tuner.FrontendModulation dvbs(int i) {
        return new android.hardware.tv.tuner.FrontendModulation(1, java.lang.Integer.valueOf(i));
    }

    public int getDvbs() {
        _assertTag(1);
        return ((java.lang.Integer) this._value).intValue();
    }

    public void setDvbs(int i) {
        _set(1, java.lang.Integer.valueOf(i));
    }

    public static android.hardware.tv.tuner.FrontendModulation dvbt(int i) {
        return new android.hardware.tv.tuner.FrontendModulation(2, java.lang.Integer.valueOf(i));
    }

    public int getDvbt() {
        _assertTag(2);
        return ((java.lang.Integer) this._value).intValue();
    }

    public void setDvbt(int i) {
        _set(2, java.lang.Integer.valueOf(i));
    }

    public static android.hardware.tv.tuner.FrontendModulation isdbs(int i) {
        return new android.hardware.tv.tuner.FrontendModulation(3, java.lang.Integer.valueOf(i));
    }

    public int getIsdbs() {
        _assertTag(3);
        return ((java.lang.Integer) this._value).intValue();
    }

    public void setIsdbs(int i) {
        _set(3, java.lang.Integer.valueOf(i));
    }

    public static android.hardware.tv.tuner.FrontendModulation isdbs3(int i) {
        return new android.hardware.tv.tuner.FrontendModulation(4, java.lang.Integer.valueOf(i));
    }

    public int getIsdbs3() {
        _assertTag(4);
        return ((java.lang.Integer) this._value).intValue();
    }

    public void setIsdbs3(int i) {
        _set(4, java.lang.Integer.valueOf(i));
    }

    public static android.hardware.tv.tuner.FrontendModulation isdbt(int i) {
        return new android.hardware.tv.tuner.FrontendModulation(5, java.lang.Integer.valueOf(i));
    }

    public int getIsdbt() {
        _assertTag(5);
        return ((java.lang.Integer) this._value).intValue();
    }

    public void setIsdbt(int i) {
        _set(5, java.lang.Integer.valueOf(i));
    }

    public static android.hardware.tv.tuner.FrontendModulation atsc(int i) {
        return new android.hardware.tv.tuner.FrontendModulation(6, java.lang.Integer.valueOf(i));
    }

    public int getAtsc() {
        _assertTag(6);
        return ((java.lang.Integer) this._value).intValue();
    }

    public void setAtsc(int i) {
        _set(6, java.lang.Integer.valueOf(i));
    }

    public static android.hardware.tv.tuner.FrontendModulation atsc3(int i) {
        return new android.hardware.tv.tuner.FrontendModulation(7, java.lang.Integer.valueOf(i));
    }

    public int getAtsc3() {
        _assertTag(7);
        return ((java.lang.Integer) this._value).intValue();
    }

    public void setAtsc3(int i) {
        _set(7, java.lang.Integer.valueOf(i));
    }

    public static android.hardware.tv.tuner.FrontendModulation dtmb(int i) {
        return new android.hardware.tv.tuner.FrontendModulation(8, java.lang.Integer.valueOf(i));
    }

    public int getDtmb() {
        _assertTag(8);
        return ((java.lang.Integer) this._value).intValue();
    }

    public void setDtmb(int i) {
        _set(8, java.lang.Integer.valueOf(i));
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
                parcel.writeInt(getDvbc());
                break;
            case 1:
                parcel.writeInt(getDvbs());
                break;
            case 2:
                parcel.writeInt(getDvbt());
                break;
            case 3:
                parcel.writeInt(getIsdbs());
                break;
            case 4:
                parcel.writeInt(getIsdbs3());
                break;
            case 5:
                parcel.writeInt(getIsdbt());
                break;
            case 6:
                parcel.writeInt(getAtsc());
                break;
            case 7:
                parcel.writeInt(getAtsc3());
                break;
            case 8:
                parcel.writeInt(getDtmb());
                break;
        }
    }

    public void readFromParcel(android.os.Parcel parcel) {
        int readInt = parcel.readInt();
        switch (readInt) {
            case 0:
                _set(readInt, java.lang.Integer.valueOf(parcel.readInt()));
                return;
            case 1:
                _set(readInt, java.lang.Integer.valueOf(parcel.readInt()));
                return;
            case 2:
                _set(readInt, java.lang.Integer.valueOf(parcel.readInt()));
                return;
            case 3:
                _set(readInt, java.lang.Integer.valueOf(parcel.readInt()));
                return;
            case 4:
                _set(readInt, java.lang.Integer.valueOf(parcel.readInt()));
                return;
            case 5:
                _set(readInt, java.lang.Integer.valueOf(parcel.readInt()));
                return;
            case 6:
                _set(readInt, java.lang.Integer.valueOf(parcel.readInt()));
                return;
            case 7:
                _set(readInt, java.lang.Integer.valueOf(parcel.readInt()));
                return;
            case 8:
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
                return "dvbc";
            case 1:
                return "dvbs";
            case 2:
                return "dvbt";
            case 3:
                return "isdbs";
            case 4:
                return "isdbs3";
            case 5:
                return "isdbt";
            case 6:
                return "atsc";
            case 7:
                return "atsc3";
            case 8:
                return "dtmb";
            default:
                throw new java.lang.IllegalStateException("unknown field: " + i);
        }
    }

    private void _set(int i, java.lang.Object obj) {
        this._tag = i;
        this._value = obj;
    }
}
