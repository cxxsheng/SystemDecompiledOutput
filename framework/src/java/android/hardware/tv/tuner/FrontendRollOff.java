package android.hardware.tv.tuner;

/* loaded from: classes2.dex */
public final class FrontendRollOff implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.hardware.tv.tuner.FrontendRollOff> CREATOR = new android.os.Parcelable.Creator<android.hardware.tv.tuner.FrontendRollOff>() { // from class: android.hardware.tv.tuner.FrontendRollOff.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.tv.tuner.FrontendRollOff createFromParcel(android.os.Parcel parcel) {
            return new android.hardware.tv.tuner.FrontendRollOff(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.tv.tuner.FrontendRollOff[] newArray(int i) {
            return new android.hardware.tv.tuner.FrontendRollOff[i];
        }
    };
    public static final int dvbs = 0;
    public static final int isdbs = 1;
    public static final int isdbs3 = 2;
    private int _tag;
    private java.lang.Object _value;

    public @interface Tag {
        public static final int dvbs = 0;
        public static final int isdbs = 1;
        public static final int isdbs3 = 2;
    }

    public FrontendRollOff() {
        this._tag = 0;
        this._value = 0;
    }

    private FrontendRollOff(android.os.Parcel parcel) {
        readFromParcel(parcel);
    }

    private FrontendRollOff(int i, java.lang.Object obj) {
        this._tag = i;
        this._value = obj;
    }

    public int getTag() {
        return this._tag;
    }

    public static android.hardware.tv.tuner.FrontendRollOff dvbs(int i) {
        return new android.hardware.tv.tuner.FrontendRollOff(0, java.lang.Integer.valueOf(i));
    }

    public int getDvbs() {
        _assertTag(0);
        return ((java.lang.Integer) this._value).intValue();
    }

    public void setDvbs(int i) {
        _set(0, java.lang.Integer.valueOf(i));
    }

    public static android.hardware.tv.tuner.FrontendRollOff isdbs(int i) {
        return new android.hardware.tv.tuner.FrontendRollOff(1, java.lang.Integer.valueOf(i));
    }

    public int getIsdbs() {
        _assertTag(1);
        return ((java.lang.Integer) this._value).intValue();
    }

    public void setIsdbs(int i) {
        _set(1, java.lang.Integer.valueOf(i));
    }

    public static android.hardware.tv.tuner.FrontendRollOff isdbs3(int i) {
        return new android.hardware.tv.tuner.FrontendRollOff(2, java.lang.Integer.valueOf(i));
    }

    public int getIsdbs3() {
        _assertTag(2);
        return ((java.lang.Integer) this._value).intValue();
    }

    public void setIsdbs3(int i) {
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
                parcel.writeInt(getDvbs());
                break;
            case 1:
                parcel.writeInt(getIsdbs());
                break;
            case 2:
                parcel.writeInt(getIsdbs3());
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
                return "dvbs";
            case 1:
                return "isdbs";
            case 2:
                return "isdbs3";
            default:
                throw new java.lang.IllegalStateException("unknown field: " + i);
        }
    }

    private void _set(int i, java.lang.Object obj) {
        this._tag = i;
        this._value = obj;
    }
}
