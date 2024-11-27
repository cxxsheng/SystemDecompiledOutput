package android.hardware.tv.tuner;

/* loaded from: classes2.dex */
public final class FrontendTransmissionMode implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.hardware.tv.tuner.FrontendTransmissionMode> CREATOR = new android.os.Parcelable.Creator<android.hardware.tv.tuner.FrontendTransmissionMode>() { // from class: android.hardware.tv.tuner.FrontendTransmissionMode.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.tv.tuner.FrontendTransmissionMode createFromParcel(android.os.Parcel parcel) {
            return new android.hardware.tv.tuner.FrontendTransmissionMode(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.tv.tuner.FrontendTransmissionMode[] newArray(int i) {
            return new android.hardware.tv.tuner.FrontendTransmissionMode[i];
        }
    };
    public static final int dtmb = 2;
    public static final int dvbt = 0;
    public static final int isdbt = 1;
    private int _tag;
    private java.lang.Object _value;

    public @interface Tag {
        public static final int dtmb = 2;
        public static final int dvbt = 0;
        public static final int isdbt = 1;
    }

    public FrontendTransmissionMode() {
        this._tag = 0;
        this._value = 0;
    }

    private FrontendTransmissionMode(android.os.Parcel parcel) {
        readFromParcel(parcel);
    }

    private FrontendTransmissionMode(int i, java.lang.Object obj) {
        this._tag = i;
        this._value = obj;
    }

    public int getTag() {
        return this._tag;
    }

    public static android.hardware.tv.tuner.FrontendTransmissionMode dvbt(int i) {
        return new android.hardware.tv.tuner.FrontendTransmissionMode(0, java.lang.Integer.valueOf(i));
    }

    public int getDvbt() {
        _assertTag(0);
        return ((java.lang.Integer) this._value).intValue();
    }

    public void setDvbt(int i) {
        _set(0, java.lang.Integer.valueOf(i));
    }

    public static android.hardware.tv.tuner.FrontendTransmissionMode isdbt(int i) {
        return new android.hardware.tv.tuner.FrontendTransmissionMode(1, java.lang.Integer.valueOf(i));
    }

    public int getIsdbt() {
        _assertTag(1);
        return ((java.lang.Integer) this._value).intValue();
    }

    public void setIsdbt(int i) {
        _set(1, java.lang.Integer.valueOf(i));
    }

    public static android.hardware.tv.tuner.FrontendTransmissionMode dtmb(int i) {
        return new android.hardware.tv.tuner.FrontendTransmissionMode(2, java.lang.Integer.valueOf(i));
    }

    public int getDtmb() {
        _assertTag(2);
        return ((java.lang.Integer) this._value).intValue();
    }

    public void setDtmb(int i) {
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
                parcel.writeInt(getDvbt());
                break;
            case 1:
                parcel.writeInt(getIsdbt());
                break;
            case 2:
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
                return "dvbt";
            case 1:
                return "isdbt";
            case 2:
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
