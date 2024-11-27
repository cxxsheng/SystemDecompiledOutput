package android.hardware.tv.tuner;

/* loaded from: classes2.dex */
public final class FrontendModulationStatus implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.hardware.tv.tuner.FrontendModulationStatus> CREATOR = new android.os.Parcelable.Creator<android.hardware.tv.tuner.FrontendModulationStatus>() { // from class: android.hardware.tv.tuner.FrontendModulationStatus.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.tv.tuner.FrontendModulationStatus createFromParcel(android.os.Parcel parcel) {
            return new android.hardware.tv.tuner.FrontendModulationStatus(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.tv.tuner.FrontendModulationStatus[] newArray(int i) {
            return new android.hardware.tv.tuner.FrontendModulationStatus[i];
        }
    };
    public static final int dvbc = 0;
    public static final int dvbs = 1;
    public static final int isdbs = 2;
    public static final int isdbs3 = 3;
    public static final int isdbt = 4;
    private int _tag;
    private java.lang.Object _value;

    public @interface Tag {
        public static final int dvbc = 0;
        public static final int dvbs = 1;
        public static final int isdbs = 2;
        public static final int isdbs3 = 3;
        public static final int isdbt = 4;
    }

    public FrontendModulationStatus() {
        this._tag = 0;
        this._value = 0;
    }

    private FrontendModulationStatus(android.os.Parcel parcel) {
        readFromParcel(parcel);
    }

    private FrontendModulationStatus(int i, java.lang.Object obj) {
        this._tag = i;
        this._value = obj;
    }

    public int getTag() {
        return this._tag;
    }

    public static android.hardware.tv.tuner.FrontendModulationStatus dvbc(int i) {
        return new android.hardware.tv.tuner.FrontendModulationStatus(0, java.lang.Integer.valueOf(i));
    }

    public int getDvbc() {
        _assertTag(0);
        return ((java.lang.Integer) this._value).intValue();
    }

    public void setDvbc(int i) {
        _set(0, java.lang.Integer.valueOf(i));
    }

    public static android.hardware.tv.tuner.FrontendModulationStatus dvbs(int i) {
        return new android.hardware.tv.tuner.FrontendModulationStatus(1, java.lang.Integer.valueOf(i));
    }

    public int getDvbs() {
        _assertTag(1);
        return ((java.lang.Integer) this._value).intValue();
    }

    public void setDvbs(int i) {
        _set(1, java.lang.Integer.valueOf(i));
    }

    public static android.hardware.tv.tuner.FrontendModulationStatus isdbs(int i) {
        return new android.hardware.tv.tuner.FrontendModulationStatus(2, java.lang.Integer.valueOf(i));
    }

    public int getIsdbs() {
        _assertTag(2);
        return ((java.lang.Integer) this._value).intValue();
    }

    public void setIsdbs(int i) {
        _set(2, java.lang.Integer.valueOf(i));
    }

    public static android.hardware.tv.tuner.FrontendModulationStatus isdbs3(int i) {
        return new android.hardware.tv.tuner.FrontendModulationStatus(3, java.lang.Integer.valueOf(i));
    }

    public int getIsdbs3() {
        _assertTag(3);
        return ((java.lang.Integer) this._value).intValue();
    }

    public void setIsdbs3(int i) {
        _set(3, java.lang.Integer.valueOf(i));
    }

    public static android.hardware.tv.tuner.FrontendModulationStatus isdbt(int i) {
        return new android.hardware.tv.tuner.FrontendModulationStatus(4, java.lang.Integer.valueOf(i));
    }

    public int getIsdbt() {
        _assertTag(4);
        return ((java.lang.Integer) this._value).intValue();
    }

    public void setIsdbt(int i) {
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
                parcel.writeInt(getDvbc());
                break;
            case 1:
                parcel.writeInt(getDvbs());
                break;
            case 2:
                parcel.writeInt(getIsdbs());
                break;
            case 3:
                parcel.writeInt(getIsdbs3());
                break;
            case 4:
                parcel.writeInt(getIsdbt());
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
                return "dvbc";
            case 1:
                return "dvbs";
            case 2:
                return "isdbs";
            case 3:
                return "isdbs3";
            case 4:
                return "isdbt";
            default:
                throw new java.lang.IllegalStateException("unknown field: " + i);
        }
    }

    private void _set(int i, java.lang.Object obj) {
        this._tag = i;
        this._value = obj;
    }
}
