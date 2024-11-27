package android.hardware.radio.data;

/* loaded from: classes2.dex */
public final class Qos implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.hardware.radio.data.Qos> CREATOR = new android.os.Parcelable.Creator<android.hardware.radio.data.Qos>() { // from class: android.hardware.radio.data.Qos.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.radio.data.Qos createFromParcel(android.os.Parcel parcel) {
            return new android.hardware.radio.data.Qos(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.radio.data.Qos[] newArray(int i) {
            return new android.hardware.radio.data.Qos[i];
        }
    };
    public static final int eps = 1;
    public static final int noinit = 0;
    public static final int nr = 2;
    private int _tag;
    private java.lang.Object _value;

    public @interface Tag {
        public static final int eps = 1;
        public static final int noinit = 0;
        public static final int nr = 2;
    }

    public Qos() {
        this._tag = 0;
        this._value = false;
    }

    private Qos(android.os.Parcel parcel) {
        readFromParcel(parcel);
    }

    private Qos(int i, java.lang.Object obj) {
        this._tag = i;
        this._value = obj;
    }

    public int getTag() {
        return this._tag;
    }

    public static android.hardware.radio.data.Qos noinit(boolean z) {
        return new android.hardware.radio.data.Qos(0, java.lang.Boolean.valueOf(z));
    }

    public boolean getNoinit() {
        _assertTag(0);
        return ((java.lang.Boolean) this._value).booleanValue();
    }

    public void setNoinit(boolean z) {
        _set(0, java.lang.Boolean.valueOf(z));
    }

    public static android.hardware.radio.data.Qos eps(android.hardware.radio.data.EpsQos epsQos) {
        return new android.hardware.radio.data.Qos(1, epsQos);
    }

    public android.hardware.radio.data.EpsQos getEps() {
        _assertTag(1);
        return (android.hardware.radio.data.EpsQos) this._value;
    }

    public void setEps(android.hardware.radio.data.EpsQos epsQos) {
        _set(1, epsQos);
    }

    public static android.hardware.radio.data.Qos nr(android.hardware.radio.data.NrQos nrQos) {
        return new android.hardware.radio.data.Qos(2, nrQos);
    }

    public android.hardware.radio.data.NrQos getNr() {
        _assertTag(2);
        return (android.hardware.radio.data.NrQos) this._value;
    }

    public void setNr(android.hardware.radio.data.NrQos nrQos) {
        _set(2, nrQos);
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
                parcel.writeTypedObject(getEps(), i);
                break;
            case 2:
                parcel.writeTypedObject(getNr(), i);
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
                _set(readInt, (android.hardware.radio.data.EpsQos) parcel.readTypedObject(android.hardware.radio.data.EpsQos.CREATOR));
                return;
            case 2:
                _set(readInt, (android.hardware.radio.data.NrQos) parcel.readTypedObject(android.hardware.radio.data.NrQos.CREATOR));
                return;
            default:
                throw new java.lang.IllegalArgumentException("union: unknown tag: " + readInt);
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        switch (getTag()) {
            case 1:
                return 0 | describeContents(getEps());
            case 2:
                return 0 | describeContents(getNr());
            default:
                return 0;
        }
    }

    private int describeContents(java.lang.Object obj) {
        if (obj == null || !(obj instanceof android.os.Parcelable)) {
            return 0;
        }
        return ((android.os.Parcelable) obj).describeContents();
    }

    public java.lang.String toString() {
        switch (this._tag) {
            case 0:
                return "Qos.noinit(" + getNoinit() + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END;
            case 1:
                return "Qos.eps(" + java.util.Objects.toString(getEps()) + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END;
            case 2:
                return "Qos.nr(" + java.util.Objects.toString(getNr()) + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END;
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
                return "eps";
            case 2:
                return "nr";
            default:
                throw new java.lang.IllegalStateException("unknown field: " + i);
        }
    }

    private void _set(int i, java.lang.Object obj) {
        this._tag = i;
        this._value = obj;
    }
}
