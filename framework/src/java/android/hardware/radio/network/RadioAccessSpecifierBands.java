package android.hardware.radio.network;

/* loaded from: classes2.dex */
public final class RadioAccessSpecifierBands implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.hardware.radio.network.RadioAccessSpecifierBands> CREATOR = new android.os.Parcelable.Creator<android.hardware.radio.network.RadioAccessSpecifierBands>() { // from class: android.hardware.radio.network.RadioAccessSpecifierBands.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.radio.network.RadioAccessSpecifierBands createFromParcel(android.os.Parcel parcel) {
            return new android.hardware.radio.network.RadioAccessSpecifierBands(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.radio.network.RadioAccessSpecifierBands[] newArray(int i) {
            return new android.hardware.radio.network.RadioAccessSpecifierBands[i];
        }
    };
    public static final int eutranBands = 3;
    public static final int geranBands = 1;
    public static final int ngranBands = 4;
    public static final int noinit = 0;
    public static final int utranBands = 2;
    private int _tag;
    private java.lang.Object _value;

    public @interface Tag {
        public static final int eutranBands = 3;
        public static final int geranBands = 1;
        public static final int ngranBands = 4;
        public static final int noinit = 0;
        public static final int utranBands = 2;
    }

    public RadioAccessSpecifierBands() {
        this._tag = 0;
        this._value = false;
    }

    private RadioAccessSpecifierBands(android.os.Parcel parcel) {
        readFromParcel(parcel);
    }

    private RadioAccessSpecifierBands(int i, java.lang.Object obj) {
        this._tag = i;
        this._value = obj;
    }

    public int getTag() {
        return this._tag;
    }

    public static android.hardware.radio.network.RadioAccessSpecifierBands noinit(boolean z) {
        return new android.hardware.radio.network.RadioAccessSpecifierBands(0, java.lang.Boolean.valueOf(z));
    }

    public boolean getNoinit() {
        _assertTag(0);
        return ((java.lang.Boolean) this._value).booleanValue();
    }

    public void setNoinit(boolean z) {
        _set(0, java.lang.Boolean.valueOf(z));
    }

    public static android.hardware.radio.network.RadioAccessSpecifierBands geranBands(int[] iArr) {
        return new android.hardware.radio.network.RadioAccessSpecifierBands(1, iArr);
    }

    public int[] getGeranBands() {
        _assertTag(1);
        return (int[]) this._value;
    }

    public void setGeranBands(int[] iArr) {
        _set(1, iArr);
    }

    public static android.hardware.radio.network.RadioAccessSpecifierBands utranBands(int[] iArr) {
        return new android.hardware.radio.network.RadioAccessSpecifierBands(2, iArr);
    }

    public int[] getUtranBands() {
        _assertTag(2);
        return (int[]) this._value;
    }

    public void setUtranBands(int[] iArr) {
        _set(2, iArr);
    }

    public static android.hardware.radio.network.RadioAccessSpecifierBands eutranBands(int[] iArr) {
        return new android.hardware.radio.network.RadioAccessSpecifierBands(3, iArr);
    }

    public int[] getEutranBands() {
        _assertTag(3);
        return (int[]) this._value;
    }

    public void setEutranBands(int[] iArr) {
        _set(3, iArr);
    }

    public static android.hardware.radio.network.RadioAccessSpecifierBands ngranBands(int[] iArr) {
        return new android.hardware.radio.network.RadioAccessSpecifierBands(4, iArr);
    }

    public int[] getNgranBands() {
        _assertTag(4);
        return (int[]) this._value;
    }

    public void setNgranBands(int[] iArr) {
        _set(4, iArr);
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
                parcel.writeIntArray(getGeranBands());
                break;
            case 2:
                parcel.writeIntArray(getUtranBands());
                break;
            case 3:
                parcel.writeIntArray(getEutranBands());
                break;
            case 4:
                parcel.writeIntArray(getNgranBands());
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
                _set(readInt, parcel.createIntArray());
                return;
            case 2:
                _set(readInt, parcel.createIntArray());
                return;
            case 3:
                _set(readInt, parcel.createIntArray());
                return;
            case 4:
                _set(readInt, parcel.createIntArray());
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
                return "RadioAccessSpecifierBands.noinit(" + getNoinit() + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END;
            case 1:
                return "RadioAccessSpecifierBands.geranBands(" + android.hardware.radio.network.GeranBands$$.arrayToString(getGeranBands()) + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END;
            case 2:
                return "RadioAccessSpecifierBands.utranBands(" + android.hardware.radio.network.UtranBands$$.arrayToString(getUtranBands()) + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END;
            case 3:
                return "RadioAccessSpecifierBands.eutranBands(" + android.hardware.radio.network.EutranBands$$.arrayToString(getEutranBands()) + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END;
            case 4:
                return "RadioAccessSpecifierBands.ngranBands(" + android.hardware.radio.network.NgranBands$$.arrayToString(getNgranBands()) + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END;
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
                return "geranBands";
            case 2:
                return "utranBands";
            case 3:
                return "eutranBands";
            case 4:
                return "ngranBands";
            default:
                throw new java.lang.IllegalStateException("unknown field: " + i);
        }
    }

    private void _set(int i, java.lang.Object obj) {
        this._tag = i;
        this._value = obj;
    }
}
