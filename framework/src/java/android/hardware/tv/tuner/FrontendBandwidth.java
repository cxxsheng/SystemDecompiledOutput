package android.hardware.tv.tuner;

/* loaded from: classes2.dex */
public final class FrontendBandwidth implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.hardware.tv.tuner.FrontendBandwidth> CREATOR = new android.os.Parcelable.Creator<android.hardware.tv.tuner.FrontendBandwidth>() { // from class: android.hardware.tv.tuner.FrontendBandwidth.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.tv.tuner.FrontendBandwidth createFromParcel(android.os.Parcel parcel) {
            return new android.hardware.tv.tuner.FrontendBandwidth(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.tv.tuner.FrontendBandwidth[] newArray(int i) {
            return new android.hardware.tv.tuner.FrontendBandwidth[i];
        }
    };
    public static final int atsc3 = 0;
    public static final int dtmb = 4;
    public static final int dvbc = 1;
    public static final int dvbt = 2;
    public static final int isdbt = 3;
    private int _tag;
    private java.lang.Object _value;

    public @interface Tag {
        public static final int atsc3 = 0;
        public static final int dtmb = 4;
        public static final int dvbc = 1;
        public static final int dvbt = 2;
        public static final int isdbt = 3;
    }

    public FrontendBandwidth() {
        this._tag = 0;
        this._value = 0;
    }

    private FrontendBandwidth(android.os.Parcel parcel) {
        readFromParcel(parcel);
    }

    private FrontendBandwidth(int i, java.lang.Object obj) {
        this._tag = i;
        this._value = obj;
    }

    public int getTag() {
        return this._tag;
    }

    public static android.hardware.tv.tuner.FrontendBandwidth atsc3(int i) {
        return new android.hardware.tv.tuner.FrontendBandwidth(0, java.lang.Integer.valueOf(i));
    }

    public int getAtsc3() {
        _assertTag(0);
        return ((java.lang.Integer) this._value).intValue();
    }

    public void setAtsc3(int i) {
        _set(0, java.lang.Integer.valueOf(i));
    }

    public static android.hardware.tv.tuner.FrontendBandwidth dvbc(int i) {
        return new android.hardware.tv.tuner.FrontendBandwidth(1, java.lang.Integer.valueOf(i));
    }

    public int getDvbc() {
        _assertTag(1);
        return ((java.lang.Integer) this._value).intValue();
    }

    public void setDvbc(int i) {
        _set(1, java.lang.Integer.valueOf(i));
    }

    public static android.hardware.tv.tuner.FrontendBandwidth dvbt(int i) {
        return new android.hardware.tv.tuner.FrontendBandwidth(2, java.lang.Integer.valueOf(i));
    }

    public int getDvbt() {
        _assertTag(2);
        return ((java.lang.Integer) this._value).intValue();
    }

    public void setDvbt(int i) {
        _set(2, java.lang.Integer.valueOf(i));
    }

    public static android.hardware.tv.tuner.FrontendBandwidth isdbt(int i) {
        return new android.hardware.tv.tuner.FrontendBandwidth(3, java.lang.Integer.valueOf(i));
    }

    public int getIsdbt() {
        _assertTag(3);
        return ((java.lang.Integer) this._value).intValue();
    }

    public void setIsdbt(int i) {
        _set(3, java.lang.Integer.valueOf(i));
    }

    public static android.hardware.tv.tuner.FrontendBandwidth dtmb(int i) {
        return new android.hardware.tv.tuner.FrontendBandwidth(4, java.lang.Integer.valueOf(i));
    }

    public int getDtmb() {
        _assertTag(4);
        return ((java.lang.Integer) this._value).intValue();
    }

    public void setDtmb(int i) {
        _set(4, java.lang.Integer.valueOf(i));
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
                parcel.writeInt(getAtsc3());
                break;
            case 1:
                parcel.writeInt(getDvbc());
                break;
            case 2:
                parcel.writeInt(getDvbt());
                break;
            case 3:
                parcel.writeInt(getIsdbt());
                break;
            case 4:
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
                return "atsc3";
            case 1:
                return "dvbc";
            case 2:
                return "dvbt";
            case 3:
                return "isdbt";
            case 4:
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
