package android.hardware.radio.network;

/* loaded from: classes2.dex */
public final class CellInfoRatSpecificInfo implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.hardware.radio.network.CellInfoRatSpecificInfo> CREATOR = new android.os.Parcelable.Creator<android.hardware.radio.network.CellInfoRatSpecificInfo>() { // from class: android.hardware.radio.network.CellInfoRatSpecificInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.radio.network.CellInfoRatSpecificInfo createFromParcel(android.os.Parcel parcel) {
            return new android.hardware.radio.network.CellInfoRatSpecificInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.radio.network.CellInfoRatSpecificInfo[] newArray(int i) {
            return new android.hardware.radio.network.CellInfoRatSpecificInfo[i];
        }
    };
    public static final int cdma = 5;
    public static final int gsm = 0;
    public static final int lte = 3;
    public static final int nr = 4;
    public static final int tdscdma = 2;
    public static final int wcdma = 1;
    private int _tag;
    private java.lang.Object _value;

    public @interface Tag {
        public static final int cdma = 5;
        public static final int gsm = 0;
        public static final int lte = 3;
        public static final int nr = 4;
        public static final int tdscdma = 2;
        public static final int wcdma = 1;
    }

    public CellInfoRatSpecificInfo() {
        this._tag = 0;
        this._value = null;
    }

    private CellInfoRatSpecificInfo(android.os.Parcel parcel) {
        readFromParcel(parcel);
    }

    private CellInfoRatSpecificInfo(int i, java.lang.Object obj) {
        this._tag = i;
        this._value = obj;
    }

    public int getTag() {
        return this._tag;
    }

    public static android.hardware.radio.network.CellInfoRatSpecificInfo gsm(android.hardware.radio.network.CellInfoGsm cellInfoGsm) {
        return new android.hardware.radio.network.CellInfoRatSpecificInfo(0, cellInfoGsm);
    }

    public android.hardware.radio.network.CellInfoGsm getGsm() {
        _assertTag(0);
        return (android.hardware.radio.network.CellInfoGsm) this._value;
    }

    public void setGsm(android.hardware.radio.network.CellInfoGsm cellInfoGsm) {
        _set(0, cellInfoGsm);
    }

    public static android.hardware.radio.network.CellInfoRatSpecificInfo wcdma(android.hardware.radio.network.CellInfoWcdma cellInfoWcdma) {
        return new android.hardware.radio.network.CellInfoRatSpecificInfo(1, cellInfoWcdma);
    }

    public android.hardware.radio.network.CellInfoWcdma getWcdma() {
        _assertTag(1);
        return (android.hardware.radio.network.CellInfoWcdma) this._value;
    }

    public void setWcdma(android.hardware.radio.network.CellInfoWcdma cellInfoWcdma) {
        _set(1, cellInfoWcdma);
    }

    public static android.hardware.radio.network.CellInfoRatSpecificInfo tdscdma(android.hardware.radio.network.CellInfoTdscdma cellInfoTdscdma) {
        return new android.hardware.radio.network.CellInfoRatSpecificInfo(2, cellInfoTdscdma);
    }

    public android.hardware.radio.network.CellInfoTdscdma getTdscdma() {
        _assertTag(2);
        return (android.hardware.radio.network.CellInfoTdscdma) this._value;
    }

    public void setTdscdma(android.hardware.radio.network.CellInfoTdscdma cellInfoTdscdma) {
        _set(2, cellInfoTdscdma);
    }

    public static android.hardware.radio.network.CellInfoRatSpecificInfo lte(android.hardware.radio.network.CellInfoLte cellInfoLte) {
        return new android.hardware.radio.network.CellInfoRatSpecificInfo(3, cellInfoLte);
    }

    public android.hardware.radio.network.CellInfoLte getLte() {
        _assertTag(3);
        return (android.hardware.radio.network.CellInfoLte) this._value;
    }

    public void setLte(android.hardware.radio.network.CellInfoLte cellInfoLte) {
        _set(3, cellInfoLte);
    }

    public static android.hardware.radio.network.CellInfoRatSpecificInfo nr(android.hardware.radio.network.CellInfoNr cellInfoNr) {
        return new android.hardware.radio.network.CellInfoRatSpecificInfo(4, cellInfoNr);
    }

    public android.hardware.radio.network.CellInfoNr getNr() {
        _assertTag(4);
        return (android.hardware.radio.network.CellInfoNr) this._value;
    }

    public void setNr(android.hardware.radio.network.CellInfoNr cellInfoNr) {
        _set(4, cellInfoNr);
    }

    public static android.hardware.radio.network.CellInfoRatSpecificInfo cdma(android.hardware.radio.network.CellInfoCdma cellInfoCdma) {
        return new android.hardware.radio.network.CellInfoRatSpecificInfo(5, cellInfoCdma);
    }

    public android.hardware.radio.network.CellInfoCdma getCdma() {
        _assertTag(5);
        return (android.hardware.radio.network.CellInfoCdma) this._value;
    }

    public void setCdma(android.hardware.radio.network.CellInfoCdma cellInfoCdma) {
        _set(5, cellInfoCdma);
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
                parcel.writeTypedObject(getGsm(), i);
                break;
            case 1:
                parcel.writeTypedObject(getWcdma(), i);
                break;
            case 2:
                parcel.writeTypedObject(getTdscdma(), i);
                break;
            case 3:
                parcel.writeTypedObject(getLte(), i);
                break;
            case 4:
                parcel.writeTypedObject(getNr(), i);
                break;
            case 5:
                parcel.writeTypedObject(getCdma(), i);
                break;
        }
    }

    public void readFromParcel(android.os.Parcel parcel) {
        int readInt = parcel.readInt();
        switch (readInt) {
            case 0:
                _set(readInt, (android.hardware.radio.network.CellInfoGsm) parcel.readTypedObject(android.hardware.radio.network.CellInfoGsm.CREATOR));
                return;
            case 1:
                _set(readInt, (android.hardware.radio.network.CellInfoWcdma) parcel.readTypedObject(android.hardware.radio.network.CellInfoWcdma.CREATOR));
                return;
            case 2:
                _set(readInt, (android.hardware.radio.network.CellInfoTdscdma) parcel.readTypedObject(android.hardware.radio.network.CellInfoTdscdma.CREATOR));
                return;
            case 3:
                _set(readInt, (android.hardware.radio.network.CellInfoLte) parcel.readTypedObject(android.hardware.radio.network.CellInfoLte.CREATOR));
                return;
            case 4:
                _set(readInt, (android.hardware.radio.network.CellInfoNr) parcel.readTypedObject(android.hardware.radio.network.CellInfoNr.CREATOR));
                return;
            case 5:
                _set(readInt, (android.hardware.radio.network.CellInfoCdma) parcel.readTypedObject(android.hardware.radio.network.CellInfoCdma.CREATOR));
                return;
            default:
                throw new java.lang.IllegalArgumentException("union: unknown tag: " + readInt);
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        switch (getTag()) {
            case 0:
                return 0 | describeContents(getGsm());
            case 1:
                return 0 | describeContents(getWcdma());
            case 2:
                return 0 | describeContents(getTdscdma());
            case 3:
                return 0 | describeContents(getLte());
            case 4:
                return 0 | describeContents(getNr());
            case 5:
                return 0 | describeContents(getCdma());
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
                return "CellInfoRatSpecificInfo.gsm(" + java.util.Objects.toString(getGsm()) + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END;
            case 1:
                return "CellInfoRatSpecificInfo.wcdma(" + java.util.Objects.toString(getWcdma()) + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END;
            case 2:
                return "CellInfoRatSpecificInfo.tdscdma(" + java.util.Objects.toString(getTdscdma()) + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END;
            case 3:
                return "CellInfoRatSpecificInfo.lte(" + java.util.Objects.toString(getLte()) + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END;
            case 4:
                return "CellInfoRatSpecificInfo.nr(" + java.util.Objects.toString(getNr()) + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END;
            case 5:
                return "CellInfoRatSpecificInfo.cdma(" + java.util.Objects.toString(getCdma()) + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END;
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
                return "gsm";
            case 1:
                return "wcdma";
            case 2:
                return "tdscdma";
            case 3:
                return "lte";
            case 4:
                return "nr";
            case 5:
                return "cdma";
            default:
                throw new java.lang.IllegalStateException("unknown field: " + i);
        }
    }

    private void _set(int i, java.lang.Object obj) {
        this._tag = i;
        this._value = obj;
    }
}
