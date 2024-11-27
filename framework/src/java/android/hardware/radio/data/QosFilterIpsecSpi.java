package android.hardware.radio.data;

/* loaded from: classes2.dex */
public final class QosFilterIpsecSpi implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.hardware.radio.data.QosFilterIpsecSpi> CREATOR = new android.os.Parcelable.Creator<android.hardware.radio.data.QosFilterIpsecSpi>() { // from class: android.hardware.radio.data.QosFilterIpsecSpi.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.radio.data.QosFilterIpsecSpi createFromParcel(android.os.Parcel parcel) {
            return new android.hardware.radio.data.QosFilterIpsecSpi(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.radio.data.QosFilterIpsecSpi[] newArray(int i) {
            return new android.hardware.radio.data.QosFilterIpsecSpi[i];
        }
    };
    public static final int noinit = 0;
    public static final int value = 1;
    private int _tag;
    private java.lang.Object _value;

    public @interface Tag {
        public static final int noinit = 0;
        public static final int value = 1;
    }

    public QosFilterIpsecSpi() {
        this._tag = 0;
        this._value = false;
    }

    private QosFilterIpsecSpi(android.os.Parcel parcel) {
        readFromParcel(parcel);
    }

    private QosFilterIpsecSpi(int i, java.lang.Object obj) {
        this._tag = i;
        this._value = obj;
    }

    public int getTag() {
        return this._tag;
    }

    public static android.hardware.radio.data.QosFilterIpsecSpi noinit(boolean z) {
        return new android.hardware.radio.data.QosFilterIpsecSpi(0, java.lang.Boolean.valueOf(z));
    }

    public boolean getNoinit() {
        _assertTag(0);
        return ((java.lang.Boolean) this._value).booleanValue();
    }

    public void setNoinit(boolean z) {
        _set(0, java.lang.Boolean.valueOf(z));
    }

    public static android.hardware.radio.data.QosFilterIpsecSpi value(int i) {
        return new android.hardware.radio.data.QosFilterIpsecSpi(1, java.lang.Integer.valueOf(i));
    }

    public int getValue() {
        _assertTag(1);
        return ((java.lang.Integer) this._value).intValue();
    }

    public void setValue(int i) {
        _set(1, java.lang.Integer.valueOf(i));
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
                parcel.writeBoolean(getNoinit());
                break;
            case 1:
                parcel.writeInt(getValue());
                break;
        }
    }

    public void readFromParcel(android.os.Parcel parcel) {
        int readInt = parcel.readInt();
        switch (readInt) {
            case 0:
                _set(readInt, java.lang.Boolean.valueOf(parcel.readBoolean()));
                return;
            case 1:
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

    public java.lang.String toString() {
        switch (this._tag) {
            case 0:
                return "QosFilterIpsecSpi.noinit(" + getNoinit() + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END;
            case 1:
                return "QosFilterIpsecSpi.value(" + getValue() + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END;
            default:
                throw new java.lang.IllegalStateException("unknown field: " + this._tag);
        }
    }

    private void _assertTag(int i) {
        if (getTag() != i) {
            throw new java.lang.IllegalStateException("bad access: " + _tagString(i) + ", " + _tagString(getTag()) + " is available.");
        }
    }

    private java.lang.String _tagString(int i) {
        switch (i) {
            case 0:
                return "noinit";
            case 1:
                return "value";
            default:
                throw new java.lang.IllegalStateException("unknown field: " + i);
        }
    }

    private void _set(int i, java.lang.Object obj) {
        this._tag = i;
        this._value = obj;
    }
}
