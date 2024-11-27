package android.hardware.radio.network;

/* loaded from: classes2.dex */
public final class AccessTechnologySpecificInfo implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.hardware.radio.network.AccessTechnologySpecificInfo> CREATOR = new android.os.Parcelable.Creator<android.hardware.radio.network.AccessTechnologySpecificInfo>() { // from class: android.hardware.radio.network.AccessTechnologySpecificInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.radio.network.AccessTechnologySpecificInfo createFromParcel(android.os.Parcel parcel) {
            return new android.hardware.radio.network.AccessTechnologySpecificInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.radio.network.AccessTechnologySpecificInfo[] newArray(int i) {
            return new android.hardware.radio.network.AccessTechnologySpecificInfo[i];
        }
    };
    public static final int cdmaInfo = 1;
    public static final int eutranInfo = 2;
    public static final int geranDtmSupported = 4;
    public static final int ngranNrVopsInfo = 3;
    public static final int noinit = 0;
    private int _tag;
    private java.lang.Object _value;

    public @interface Tag {
        public static final int cdmaInfo = 1;
        public static final int eutranInfo = 2;
        public static final int geranDtmSupported = 4;
        public static final int ngranNrVopsInfo = 3;
        public static final int noinit = 0;
    }

    public AccessTechnologySpecificInfo() {
        this._tag = 0;
        this._value = false;
    }

    private AccessTechnologySpecificInfo(android.os.Parcel parcel) {
        readFromParcel(parcel);
    }

    private AccessTechnologySpecificInfo(int i, java.lang.Object obj) {
        this._tag = i;
        this._value = obj;
    }

    public int getTag() {
        return this._tag;
    }

    public static android.hardware.radio.network.AccessTechnologySpecificInfo noinit(boolean z) {
        return new android.hardware.radio.network.AccessTechnologySpecificInfo(0, java.lang.Boolean.valueOf(z));
    }

    public boolean getNoinit() {
        _assertTag(0);
        return ((java.lang.Boolean) this._value).booleanValue();
    }

    public void setNoinit(boolean z) {
        _set(0, java.lang.Boolean.valueOf(z));
    }

    public static android.hardware.radio.network.AccessTechnologySpecificInfo cdmaInfo(android.hardware.radio.network.Cdma2000RegistrationInfo cdma2000RegistrationInfo) {
        return new android.hardware.radio.network.AccessTechnologySpecificInfo(1, cdma2000RegistrationInfo);
    }

    public android.hardware.radio.network.Cdma2000RegistrationInfo getCdmaInfo() {
        _assertTag(1);
        return (android.hardware.radio.network.Cdma2000RegistrationInfo) this._value;
    }

    public void setCdmaInfo(android.hardware.radio.network.Cdma2000RegistrationInfo cdma2000RegistrationInfo) {
        _set(1, cdma2000RegistrationInfo);
    }

    public static android.hardware.radio.network.AccessTechnologySpecificInfo eutranInfo(android.hardware.radio.network.EutranRegistrationInfo eutranRegistrationInfo) {
        return new android.hardware.radio.network.AccessTechnologySpecificInfo(2, eutranRegistrationInfo);
    }

    public android.hardware.radio.network.EutranRegistrationInfo getEutranInfo() {
        _assertTag(2);
        return (android.hardware.radio.network.EutranRegistrationInfo) this._value;
    }

    public void setEutranInfo(android.hardware.radio.network.EutranRegistrationInfo eutranRegistrationInfo) {
        _set(2, eutranRegistrationInfo);
    }

    public static android.hardware.radio.network.AccessTechnologySpecificInfo ngranNrVopsInfo(android.hardware.radio.network.NrVopsInfo nrVopsInfo) {
        return new android.hardware.radio.network.AccessTechnologySpecificInfo(3, nrVopsInfo);
    }

    public android.hardware.radio.network.NrVopsInfo getNgranNrVopsInfo() {
        _assertTag(3);
        return (android.hardware.radio.network.NrVopsInfo) this._value;
    }

    public void setNgranNrVopsInfo(android.hardware.radio.network.NrVopsInfo nrVopsInfo) {
        _set(3, nrVopsInfo);
    }

    public static android.hardware.radio.network.AccessTechnologySpecificInfo geranDtmSupported(boolean z) {
        return new android.hardware.radio.network.AccessTechnologySpecificInfo(4, java.lang.Boolean.valueOf(z));
    }

    public boolean getGeranDtmSupported() {
        _assertTag(4);
        return ((java.lang.Boolean) this._value).booleanValue();
    }

    public void setGeranDtmSupported(boolean z) {
        _set(4, java.lang.Boolean.valueOf(z));
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
                parcel.writeTypedObject(getCdmaInfo(), i);
                break;
            case 2:
                parcel.writeTypedObject(getEutranInfo(), i);
                break;
            case 3:
                parcel.writeTypedObject(getNgranNrVopsInfo(), i);
                break;
            case 4:
                parcel.writeBoolean(getGeranDtmSupported());
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
                _set(readInt, (android.hardware.radio.network.Cdma2000RegistrationInfo) parcel.readTypedObject(android.hardware.radio.network.Cdma2000RegistrationInfo.CREATOR));
                return;
            case 2:
                _set(readInt, (android.hardware.radio.network.EutranRegistrationInfo) parcel.readTypedObject(android.hardware.radio.network.EutranRegistrationInfo.CREATOR));
                return;
            case 3:
                _set(readInt, (android.hardware.radio.network.NrVopsInfo) parcel.readTypedObject(android.hardware.radio.network.NrVopsInfo.CREATOR));
                return;
            case 4:
                _set(readInt, java.lang.Boolean.valueOf(parcel.readBoolean()));
                return;
            default:
                throw new java.lang.IllegalArgumentException("union: unknown tag: " + readInt);
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        switch (getTag()) {
            case 1:
                return 0 | describeContents(getCdmaInfo());
            case 2:
                return 0 | describeContents(getEutranInfo());
            case 3:
                return 0 | describeContents(getNgranNrVopsInfo());
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
                return "AccessTechnologySpecificInfo.noinit(" + getNoinit() + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END;
            case 1:
                return "AccessTechnologySpecificInfo.cdmaInfo(" + java.util.Objects.toString(getCdmaInfo()) + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END;
            case 2:
                return "AccessTechnologySpecificInfo.eutranInfo(" + java.util.Objects.toString(getEutranInfo()) + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END;
            case 3:
                return "AccessTechnologySpecificInfo.ngranNrVopsInfo(" + java.util.Objects.toString(getNgranNrVopsInfo()) + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END;
            case 4:
                return "AccessTechnologySpecificInfo.geranDtmSupported(" + getGeranDtmSupported() + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END;
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
                return "cdmaInfo";
            case 2:
                return "eutranInfo";
            case 3:
                return "ngranNrVopsInfo";
            case 4:
                return "geranDtmSupported";
            default:
                throw new java.lang.IllegalStateException("unknown field: " + i);
        }
    }

    private void _set(int i, java.lang.Object obj) {
        this._tag = i;
        this._value = obj;
    }
}
