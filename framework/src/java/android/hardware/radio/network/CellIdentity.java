package android.hardware.radio.network;

/* loaded from: classes2.dex */
public final class CellIdentity implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.hardware.radio.network.CellIdentity> CREATOR = new android.os.Parcelable.Creator<android.hardware.radio.network.CellIdentity>() { // from class: android.hardware.radio.network.CellIdentity.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.radio.network.CellIdentity createFromParcel(android.os.Parcel parcel) {
            return new android.hardware.radio.network.CellIdentity(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.radio.network.CellIdentity[] newArray(int i) {
            return new android.hardware.radio.network.CellIdentity[i];
        }
    };
    public static final int cdma = 4;
    public static final int gsm = 1;
    public static final int lte = 5;
    public static final int noinit = 0;
    public static final int nr = 6;
    public static final int tdscdma = 3;
    public static final int wcdma = 2;
    private int _tag;
    private java.lang.Object _value;

    public @interface Tag {
        public static final int cdma = 4;
        public static final int gsm = 1;
        public static final int lte = 5;
        public static final int noinit = 0;
        public static final int nr = 6;
        public static final int tdscdma = 3;
        public static final int wcdma = 2;
    }

    public CellIdentity() {
        this._tag = 0;
        this._value = false;
    }

    private CellIdentity(android.os.Parcel parcel) {
        readFromParcel(parcel);
    }

    private CellIdentity(int i, java.lang.Object obj) {
        this._tag = i;
        this._value = obj;
    }

    public int getTag() {
        return this._tag;
    }

    public static android.hardware.radio.network.CellIdentity noinit(boolean z) {
        return new android.hardware.radio.network.CellIdentity(0, java.lang.Boolean.valueOf(z));
    }

    public boolean getNoinit() {
        _assertTag(0);
        return ((java.lang.Boolean) this._value).booleanValue();
    }

    public void setNoinit(boolean z) {
        _set(0, java.lang.Boolean.valueOf(z));
    }

    public static android.hardware.radio.network.CellIdentity gsm(android.hardware.radio.network.CellIdentityGsm cellIdentityGsm) {
        return new android.hardware.radio.network.CellIdentity(1, cellIdentityGsm);
    }

    public android.hardware.radio.network.CellIdentityGsm getGsm() {
        _assertTag(1);
        return (android.hardware.radio.network.CellIdentityGsm) this._value;
    }

    public void setGsm(android.hardware.radio.network.CellIdentityGsm cellIdentityGsm) {
        _set(1, cellIdentityGsm);
    }

    public static android.hardware.radio.network.CellIdentity wcdma(android.hardware.radio.network.CellIdentityWcdma cellIdentityWcdma) {
        return new android.hardware.radio.network.CellIdentity(2, cellIdentityWcdma);
    }

    public android.hardware.radio.network.CellIdentityWcdma getWcdma() {
        _assertTag(2);
        return (android.hardware.radio.network.CellIdentityWcdma) this._value;
    }

    public void setWcdma(android.hardware.radio.network.CellIdentityWcdma cellIdentityWcdma) {
        _set(2, cellIdentityWcdma);
    }

    public static android.hardware.radio.network.CellIdentity tdscdma(android.hardware.radio.network.CellIdentityTdscdma cellIdentityTdscdma) {
        return new android.hardware.radio.network.CellIdentity(3, cellIdentityTdscdma);
    }

    public android.hardware.radio.network.CellIdentityTdscdma getTdscdma() {
        _assertTag(3);
        return (android.hardware.radio.network.CellIdentityTdscdma) this._value;
    }

    public void setTdscdma(android.hardware.radio.network.CellIdentityTdscdma cellIdentityTdscdma) {
        _set(3, cellIdentityTdscdma);
    }

    public static android.hardware.radio.network.CellIdentity cdma(android.hardware.radio.network.CellIdentityCdma cellIdentityCdma) {
        return new android.hardware.radio.network.CellIdentity(4, cellIdentityCdma);
    }

    public android.hardware.radio.network.CellIdentityCdma getCdma() {
        _assertTag(4);
        return (android.hardware.radio.network.CellIdentityCdma) this._value;
    }

    public void setCdma(android.hardware.radio.network.CellIdentityCdma cellIdentityCdma) {
        _set(4, cellIdentityCdma);
    }

    public static android.hardware.radio.network.CellIdentity lte(android.hardware.radio.network.CellIdentityLte cellIdentityLte) {
        return new android.hardware.radio.network.CellIdentity(5, cellIdentityLte);
    }

    public android.hardware.radio.network.CellIdentityLte getLte() {
        _assertTag(5);
        return (android.hardware.radio.network.CellIdentityLte) this._value;
    }

    public void setLte(android.hardware.radio.network.CellIdentityLte cellIdentityLte) {
        _set(5, cellIdentityLte);
    }

    public static android.hardware.radio.network.CellIdentity nr(android.hardware.radio.network.CellIdentityNr cellIdentityNr) {
        return new android.hardware.radio.network.CellIdentity(6, cellIdentityNr);
    }

    public android.hardware.radio.network.CellIdentityNr getNr() {
        _assertTag(6);
        return (android.hardware.radio.network.CellIdentityNr) this._value;
    }

    public void setNr(android.hardware.radio.network.CellIdentityNr cellIdentityNr) {
        _set(6, cellIdentityNr);
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
                parcel.writeTypedObject(getGsm(), i);
                break;
            case 2:
                parcel.writeTypedObject(getWcdma(), i);
                break;
            case 3:
                parcel.writeTypedObject(getTdscdma(), i);
                break;
            case 4:
                parcel.writeTypedObject(getCdma(), i);
                break;
            case 5:
                parcel.writeTypedObject(getLte(), i);
                break;
            case 6:
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
                _set(readInt, (android.hardware.radio.network.CellIdentityGsm) parcel.readTypedObject(android.hardware.radio.network.CellIdentityGsm.CREATOR));
                return;
            case 2:
                _set(readInt, (android.hardware.radio.network.CellIdentityWcdma) parcel.readTypedObject(android.hardware.radio.network.CellIdentityWcdma.CREATOR));
                return;
            case 3:
                _set(readInt, (android.hardware.radio.network.CellIdentityTdscdma) parcel.readTypedObject(android.hardware.radio.network.CellIdentityTdscdma.CREATOR));
                return;
            case 4:
                _set(readInt, (android.hardware.radio.network.CellIdentityCdma) parcel.readTypedObject(android.hardware.radio.network.CellIdentityCdma.CREATOR));
                return;
            case 5:
                _set(readInt, (android.hardware.radio.network.CellIdentityLte) parcel.readTypedObject(android.hardware.radio.network.CellIdentityLte.CREATOR));
                return;
            case 6:
                _set(readInt, (android.hardware.radio.network.CellIdentityNr) parcel.readTypedObject(android.hardware.radio.network.CellIdentityNr.CREATOR));
                return;
            default:
                throw new java.lang.IllegalArgumentException("union: unknown tag: " + readInt);
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        switch (getTag()) {
            case 1:
                return 0 | describeContents(getGsm());
            case 2:
                return 0 | describeContents(getWcdma());
            case 3:
                return 0 | describeContents(getTdscdma());
            case 4:
                return 0 | describeContents(getCdma());
            case 5:
                return 0 | describeContents(getLte());
            case 6:
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
                return "CellIdentity.noinit(" + getNoinit() + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END;
            case 1:
                return "CellIdentity.gsm(" + java.util.Objects.toString(getGsm()) + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END;
            case 2:
                return "CellIdentity.wcdma(" + java.util.Objects.toString(getWcdma()) + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END;
            case 3:
                return "CellIdentity.tdscdma(" + java.util.Objects.toString(getTdscdma()) + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END;
            case 4:
                return "CellIdentity.cdma(" + java.util.Objects.toString(getCdma()) + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END;
            case 5:
                return "CellIdentity.lte(" + java.util.Objects.toString(getLte()) + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END;
            case 6:
                return "CellIdentity.nr(" + java.util.Objects.toString(getNr()) + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END;
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
                return "gsm";
            case 2:
                return "wcdma";
            case 3:
                return "tdscdma";
            case 4:
                return "cdma";
            case 5:
                return "lte";
            case 6:
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
