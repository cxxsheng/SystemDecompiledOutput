package android.hardware.tv.tuner;

/* loaded from: classes2.dex */
public final class FrontendSettings implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.hardware.tv.tuner.FrontendSettings> CREATOR = new android.os.Parcelable.Creator<android.hardware.tv.tuner.FrontendSettings>() { // from class: android.hardware.tv.tuner.FrontendSettings.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.tv.tuner.FrontendSettings createFromParcel(android.os.Parcel parcel) {
            return new android.hardware.tv.tuner.FrontendSettings(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.tv.tuner.FrontendSettings[] newArray(int i) {
            return new android.hardware.tv.tuner.FrontendSettings[i];
        }
    };
    public static final int analog = 0;
    public static final int atsc = 1;
    public static final int atsc3 = 2;
    public static final int dtmb = 9;
    public static final int dvbc = 4;
    public static final int dvbs = 3;
    public static final int dvbt = 5;
    public static final int iptv = 10;
    public static final int isdbs = 6;
    public static final int isdbs3 = 7;
    public static final int isdbt = 8;
    private int _tag;
    private java.lang.Object _value;

    public @interface Tag {
        public static final int analog = 0;
        public static final int atsc = 1;
        public static final int atsc3 = 2;
        public static final int dtmb = 9;
        public static final int dvbc = 4;
        public static final int dvbs = 3;
        public static final int dvbt = 5;
        public static final int iptv = 10;
        public static final int isdbs = 6;
        public static final int isdbs3 = 7;
        public static final int isdbt = 8;
    }

    public FrontendSettings() {
        this._tag = 0;
        this._value = null;
    }

    private FrontendSettings(android.os.Parcel parcel) {
        readFromParcel(parcel);
    }

    private FrontendSettings(int i, java.lang.Object obj) {
        this._tag = i;
        this._value = obj;
    }

    public int getTag() {
        return this._tag;
    }

    public static android.hardware.tv.tuner.FrontendSettings analog(android.hardware.tv.tuner.FrontendAnalogSettings frontendAnalogSettings) {
        return new android.hardware.tv.tuner.FrontendSettings(0, frontendAnalogSettings);
    }

    public android.hardware.tv.tuner.FrontendAnalogSettings getAnalog() {
        _assertTag(0);
        return (android.hardware.tv.tuner.FrontendAnalogSettings) this._value;
    }

    public void setAnalog(android.hardware.tv.tuner.FrontendAnalogSettings frontendAnalogSettings) {
        _set(0, frontendAnalogSettings);
    }

    public static android.hardware.tv.tuner.FrontendSettings atsc(android.hardware.tv.tuner.FrontendAtscSettings frontendAtscSettings) {
        return new android.hardware.tv.tuner.FrontendSettings(1, frontendAtscSettings);
    }

    public android.hardware.tv.tuner.FrontendAtscSettings getAtsc() {
        _assertTag(1);
        return (android.hardware.tv.tuner.FrontendAtscSettings) this._value;
    }

    public void setAtsc(android.hardware.tv.tuner.FrontendAtscSettings frontendAtscSettings) {
        _set(1, frontendAtscSettings);
    }

    public static android.hardware.tv.tuner.FrontendSettings atsc3(android.hardware.tv.tuner.FrontendAtsc3Settings frontendAtsc3Settings) {
        return new android.hardware.tv.tuner.FrontendSettings(2, frontendAtsc3Settings);
    }

    public android.hardware.tv.tuner.FrontendAtsc3Settings getAtsc3() {
        _assertTag(2);
        return (android.hardware.tv.tuner.FrontendAtsc3Settings) this._value;
    }

    public void setAtsc3(android.hardware.tv.tuner.FrontendAtsc3Settings frontendAtsc3Settings) {
        _set(2, frontendAtsc3Settings);
    }

    public static android.hardware.tv.tuner.FrontendSettings dvbs(android.hardware.tv.tuner.FrontendDvbsSettings frontendDvbsSettings) {
        return new android.hardware.tv.tuner.FrontendSettings(3, frontendDvbsSettings);
    }

    public android.hardware.tv.tuner.FrontendDvbsSettings getDvbs() {
        _assertTag(3);
        return (android.hardware.tv.tuner.FrontendDvbsSettings) this._value;
    }

    public void setDvbs(android.hardware.tv.tuner.FrontendDvbsSettings frontendDvbsSettings) {
        _set(3, frontendDvbsSettings);
    }

    public static android.hardware.tv.tuner.FrontendSettings dvbc(android.hardware.tv.tuner.FrontendDvbcSettings frontendDvbcSettings) {
        return new android.hardware.tv.tuner.FrontendSettings(4, frontendDvbcSettings);
    }

    public android.hardware.tv.tuner.FrontendDvbcSettings getDvbc() {
        _assertTag(4);
        return (android.hardware.tv.tuner.FrontendDvbcSettings) this._value;
    }

    public void setDvbc(android.hardware.tv.tuner.FrontendDvbcSettings frontendDvbcSettings) {
        _set(4, frontendDvbcSettings);
    }

    public static android.hardware.tv.tuner.FrontendSettings dvbt(android.hardware.tv.tuner.FrontendDvbtSettings frontendDvbtSettings) {
        return new android.hardware.tv.tuner.FrontendSettings(5, frontendDvbtSettings);
    }

    public android.hardware.tv.tuner.FrontendDvbtSettings getDvbt() {
        _assertTag(5);
        return (android.hardware.tv.tuner.FrontendDvbtSettings) this._value;
    }

    public void setDvbt(android.hardware.tv.tuner.FrontendDvbtSettings frontendDvbtSettings) {
        _set(5, frontendDvbtSettings);
    }

    public static android.hardware.tv.tuner.FrontendSettings isdbs(android.hardware.tv.tuner.FrontendIsdbsSettings frontendIsdbsSettings) {
        return new android.hardware.tv.tuner.FrontendSettings(6, frontendIsdbsSettings);
    }

    public android.hardware.tv.tuner.FrontendIsdbsSettings getIsdbs() {
        _assertTag(6);
        return (android.hardware.tv.tuner.FrontendIsdbsSettings) this._value;
    }

    public void setIsdbs(android.hardware.tv.tuner.FrontendIsdbsSettings frontendIsdbsSettings) {
        _set(6, frontendIsdbsSettings);
    }

    public static android.hardware.tv.tuner.FrontendSettings isdbs3(android.hardware.tv.tuner.FrontendIsdbs3Settings frontendIsdbs3Settings) {
        return new android.hardware.tv.tuner.FrontendSettings(7, frontendIsdbs3Settings);
    }

    public android.hardware.tv.tuner.FrontendIsdbs3Settings getIsdbs3() {
        _assertTag(7);
        return (android.hardware.tv.tuner.FrontendIsdbs3Settings) this._value;
    }

    public void setIsdbs3(android.hardware.tv.tuner.FrontendIsdbs3Settings frontendIsdbs3Settings) {
        _set(7, frontendIsdbs3Settings);
    }

    public static android.hardware.tv.tuner.FrontendSettings isdbt(android.hardware.tv.tuner.FrontendIsdbtSettings frontendIsdbtSettings) {
        return new android.hardware.tv.tuner.FrontendSettings(8, frontendIsdbtSettings);
    }

    public android.hardware.tv.tuner.FrontendIsdbtSettings getIsdbt() {
        _assertTag(8);
        return (android.hardware.tv.tuner.FrontendIsdbtSettings) this._value;
    }

    public void setIsdbt(android.hardware.tv.tuner.FrontendIsdbtSettings frontendIsdbtSettings) {
        _set(8, frontendIsdbtSettings);
    }

    public static android.hardware.tv.tuner.FrontendSettings dtmb(android.hardware.tv.tuner.FrontendDtmbSettings frontendDtmbSettings) {
        return new android.hardware.tv.tuner.FrontendSettings(9, frontendDtmbSettings);
    }

    public android.hardware.tv.tuner.FrontendDtmbSettings getDtmb() {
        _assertTag(9);
        return (android.hardware.tv.tuner.FrontendDtmbSettings) this._value;
    }

    public void setDtmb(android.hardware.tv.tuner.FrontendDtmbSettings frontendDtmbSettings) {
        _set(9, frontendDtmbSettings);
    }

    public static android.hardware.tv.tuner.FrontendSettings iptv(android.hardware.tv.tuner.FrontendIptvSettings frontendIptvSettings) {
        return new android.hardware.tv.tuner.FrontendSettings(10, frontendIptvSettings);
    }

    public android.hardware.tv.tuner.FrontendIptvSettings getIptv() {
        _assertTag(10);
        return (android.hardware.tv.tuner.FrontendIptvSettings) this._value;
    }

    public void setIptv(android.hardware.tv.tuner.FrontendIptvSettings frontendIptvSettings) {
        _set(10, frontendIptvSettings);
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
                parcel.writeTypedObject(getAnalog(), i);
                break;
            case 1:
                parcel.writeTypedObject(getAtsc(), i);
                break;
            case 2:
                parcel.writeTypedObject(getAtsc3(), i);
                break;
            case 3:
                parcel.writeTypedObject(getDvbs(), i);
                break;
            case 4:
                parcel.writeTypedObject(getDvbc(), i);
                break;
            case 5:
                parcel.writeTypedObject(getDvbt(), i);
                break;
            case 6:
                parcel.writeTypedObject(getIsdbs(), i);
                break;
            case 7:
                parcel.writeTypedObject(getIsdbs3(), i);
                break;
            case 8:
                parcel.writeTypedObject(getIsdbt(), i);
                break;
            case 9:
                parcel.writeTypedObject(getDtmb(), i);
                break;
            case 10:
                parcel.writeTypedObject(getIptv(), i);
                break;
        }
    }

    public void readFromParcel(android.os.Parcel parcel) {
        int readInt = parcel.readInt();
        switch (readInt) {
            case 0:
                _set(readInt, (android.hardware.tv.tuner.FrontendAnalogSettings) parcel.readTypedObject(android.hardware.tv.tuner.FrontendAnalogSettings.CREATOR));
                return;
            case 1:
                _set(readInt, (android.hardware.tv.tuner.FrontendAtscSettings) parcel.readTypedObject(android.hardware.tv.tuner.FrontendAtscSettings.CREATOR));
                return;
            case 2:
                _set(readInt, (android.hardware.tv.tuner.FrontendAtsc3Settings) parcel.readTypedObject(android.hardware.tv.tuner.FrontendAtsc3Settings.CREATOR));
                return;
            case 3:
                _set(readInt, (android.hardware.tv.tuner.FrontendDvbsSettings) parcel.readTypedObject(android.hardware.tv.tuner.FrontendDvbsSettings.CREATOR));
                return;
            case 4:
                _set(readInt, (android.hardware.tv.tuner.FrontendDvbcSettings) parcel.readTypedObject(android.hardware.tv.tuner.FrontendDvbcSettings.CREATOR));
                return;
            case 5:
                _set(readInt, (android.hardware.tv.tuner.FrontendDvbtSettings) parcel.readTypedObject(android.hardware.tv.tuner.FrontendDvbtSettings.CREATOR));
                return;
            case 6:
                _set(readInt, (android.hardware.tv.tuner.FrontendIsdbsSettings) parcel.readTypedObject(android.hardware.tv.tuner.FrontendIsdbsSettings.CREATOR));
                return;
            case 7:
                _set(readInt, (android.hardware.tv.tuner.FrontendIsdbs3Settings) parcel.readTypedObject(android.hardware.tv.tuner.FrontendIsdbs3Settings.CREATOR));
                return;
            case 8:
                _set(readInt, (android.hardware.tv.tuner.FrontendIsdbtSettings) parcel.readTypedObject(android.hardware.tv.tuner.FrontendIsdbtSettings.CREATOR));
                return;
            case 9:
                _set(readInt, (android.hardware.tv.tuner.FrontendDtmbSettings) parcel.readTypedObject(android.hardware.tv.tuner.FrontendDtmbSettings.CREATOR));
                return;
            case 10:
                _set(readInt, (android.hardware.tv.tuner.FrontendIptvSettings) parcel.readTypedObject(android.hardware.tv.tuner.FrontendIptvSettings.CREATOR));
                return;
            default:
                throw new java.lang.IllegalArgumentException("union: unknown tag: " + readInt);
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        switch (getTag()) {
            case 0:
                return 0 | describeContents(getAnalog());
            case 1:
                return 0 | describeContents(getAtsc());
            case 2:
                return 0 | describeContents(getAtsc3());
            case 3:
                return 0 | describeContents(getDvbs());
            case 4:
                return 0 | describeContents(getDvbc());
            case 5:
                return 0 | describeContents(getDvbt());
            case 6:
                return 0 | describeContents(getIsdbs());
            case 7:
                return 0 | describeContents(getIsdbs3());
            case 8:
                return 0 | describeContents(getIsdbt());
            case 9:
                return 0 | describeContents(getDtmb());
            case 10:
                return 0 | describeContents(getIptv());
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

    private void _assertTag(int i) {
        if (getTag() != i) {
            throw new java.lang.IllegalStateException("bad access: " + _tagString(i) + ", " + _tagString(getTag()) + " is available.");
        }
    }

    private java.lang.String _tagString(int i) {
        switch (i) {
            case 0:
                return android.media.audio.common.AudioDeviceDescription.CONNECTION_ANALOG;
            case 1:
                return "atsc";
            case 2:
                return "atsc3";
            case 3:
                return "dvbs";
            case 4:
                return "dvbc";
            case 5:
                return "dvbt";
            case 6:
                return "isdbs";
            case 7:
                return "isdbs3";
            case 8:
                return "isdbt";
            case 9:
                return "dtmb";
            case 10:
                return "iptv";
            default:
                throw new java.lang.IllegalStateException("unknown field: " + i);
        }
    }

    private void _set(int i, java.lang.Object obj) {
        this._tag = i;
        this._value = obj;
    }
}
