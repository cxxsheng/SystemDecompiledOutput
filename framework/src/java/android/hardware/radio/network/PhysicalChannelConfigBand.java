package android.hardware.radio.network;

/* loaded from: classes2.dex */
public final class PhysicalChannelConfigBand implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.hardware.radio.network.PhysicalChannelConfigBand> CREATOR = new android.os.Parcelable.Creator<android.hardware.radio.network.PhysicalChannelConfigBand>() { // from class: android.hardware.radio.network.PhysicalChannelConfigBand.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.radio.network.PhysicalChannelConfigBand createFromParcel(android.os.Parcel parcel) {
            return new android.hardware.radio.network.PhysicalChannelConfigBand(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.radio.network.PhysicalChannelConfigBand[] newArray(int i) {
            return new android.hardware.radio.network.PhysicalChannelConfigBand[i];
        }
    };
    public static final int eutranBand = 3;
    public static final int geranBand = 1;
    public static final int ngranBand = 4;
    public static final int noinit = 0;
    public static final int utranBand = 2;
    private int _tag;
    private java.lang.Object _value;

    public @interface Tag {
        public static final int eutranBand = 3;
        public static final int geranBand = 1;
        public static final int ngranBand = 4;
        public static final int noinit = 0;
        public static final int utranBand = 2;
    }

    public PhysicalChannelConfigBand() {
        this._tag = 0;
        this._value = false;
    }

    private PhysicalChannelConfigBand(android.os.Parcel parcel) {
        readFromParcel(parcel);
    }

    private PhysicalChannelConfigBand(int i, java.lang.Object obj) {
        this._tag = i;
        this._value = obj;
    }

    public int getTag() {
        return this._tag;
    }

    public static android.hardware.radio.network.PhysicalChannelConfigBand noinit(boolean z) {
        return new android.hardware.radio.network.PhysicalChannelConfigBand(0, java.lang.Boolean.valueOf(z));
    }

    public boolean getNoinit() {
        _assertTag(0);
        return ((java.lang.Boolean) this._value).booleanValue();
    }

    public void setNoinit(boolean z) {
        _set(0, java.lang.Boolean.valueOf(z));
    }

    public static android.hardware.radio.network.PhysicalChannelConfigBand geranBand(int i) {
        return new android.hardware.radio.network.PhysicalChannelConfigBand(1, java.lang.Integer.valueOf(i));
    }

    public int getGeranBand() {
        _assertTag(1);
        return ((java.lang.Integer) this._value).intValue();
    }

    public void setGeranBand(int i) {
        _set(1, java.lang.Integer.valueOf(i));
    }

    public static android.hardware.radio.network.PhysicalChannelConfigBand utranBand(int i) {
        return new android.hardware.radio.network.PhysicalChannelConfigBand(2, java.lang.Integer.valueOf(i));
    }

    public int getUtranBand() {
        _assertTag(2);
        return ((java.lang.Integer) this._value).intValue();
    }

    public void setUtranBand(int i) {
        _set(2, java.lang.Integer.valueOf(i));
    }

    public static android.hardware.radio.network.PhysicalChannelConfigBand eutranBand(int i) {
        return new android.hardware.radio.network.PhysicalChannelConfigBand(3, java.lang.Integer.valueOf(i));
    }

    public int getEutranBand() {
        _assertTag(3);
        return ((java.lang.Integer) this._value).intValue();
    }

    public void setEutranBand(int i) {
        _set(3, java.lang.Integer.valueOf(i));
    }

    public static android.hardware.radio.network.PhysicalChannelConfigBand ngranBand(int i) {
        return new android.hardware.radio.network.PhysicalChannelConfigBand(4, java.lang.Integer.valueOf(i));
    }

    public int getNgranBand() {
        _assertTag(4);
        return ((java.lang.Integer) this._value).intValue();
    }

    public void setNgranBand(int i) {
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
                parcel.writeBoolean(getNoinit());
                break;
            case 1:
                parcel.writeInt(getGeranBand());
                break;
            case 2:
                parcel.writeInt(getUtranBand());
                break;
            case 3:
                parcel.writeInt(getEutranBand());
                break;
            case 4:
                parcel.writeInt(getNgranBand());
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

    public java.lang.String toString() {
        switch (this._tag) {
            case 0:
                return "PhysicalChannelConfigBand.noinit(" + getNoinit() + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END;
            case 1:
                return "PhysicalChannelConfigBand.geranBand(" + android.hardware.radio.network.GeranBands$$.toString(getGeranBand()) + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END;
            case 2:
                return "PhysicalChannelConfigBand.utranBand(" + android.hardware.radio.network.UtranBands$$.toString(getUtranBand()) + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END;
            case 3:
                return "PhysicalChannelConfigBand.eutranBand(" + android.hardware.radio.network.EutranBands$$.toString(getEutranBand()) + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END;
            case 4:
                return "PhysicalChannelConfigBand.ngranBand(" + android.hardware.radio.network.NgranBands$$.toString(getNgranBand()) + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END;
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
                return "geranBand";
            case 2:
                return "utranBand";
            case 3:
                return "eutranBand";
            case 4:
                return "ngranBand";
            default:
                throw new java.lang.IllegalStateException("unknown field: " + i);
        }
    }

    private void _set(int i, java.lang.Object obj) {
        this._tag = i;
        this._value = obj;
    }
}
