package android.hardware.tv.tuner;

/* loaded from: classes2.dex */
public final class FrontendCapabilities implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.hardware.tv.tuner.FrontendCapabilities> CREATOR = new android.os.Parcelable.Creator<android.hardware.tv.tuner.FrontendCapabilities>() { // from class: android.hardware.tv.tuner.FrontendCapabilities.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.tv.tuner.FrontendCapabilities createFromParcel(android.os.Parcel parcel) {
            return new android.hardware.tv.tuner.FrontendCapabilities(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.tv.tuner.FrontendCapabilities[] newArray(int i) {
            return new android.hardware.tv.tuner.FrontendCapabilities[i];
        }
    };
    public static final int analogCaps = 0;
    public static final int atsc3Caps = 2;
    public static final int atscCaps = 1;
    public static final int dtmbCaps = 3;
    public static final int dvbcCaps = 5;
    public static final int dvbsCaps = 4;
    public static final int dvbtCaps = 6;
    public static final int iptvCaps = 10;
    public static final int isdbs3Caps = 8;
    public static final int isdbsCaps = 7;
    public static final int isdbtCaps = 9;
    private int _tag;
    private java.lang.Object _value;

    public @interface Tag {
        public static final int analogCaps = 0;
        public static final int atsc3Caps = 2;
        public static final int atscCaps = 1;
        public static final int dtmbCaps = 3;
        public static final int dvbcCaps = 5;
        public static final int dvbsCaps = 4;
        public static final int dvbtCaps = 6;
        public static final int iptvCaps = 10;
        public static final int isdbs3Caps = 8;
        public static final int isdbsCaps = 7;
        public static final int isdbtCaps = 9;
    }

    public FrontendCapabilities() {
        this._tag = 0;
        this._value = null;
    }

    private FrontendCapabilities(android.os.Parcel parcel) {
        readFromParcel(parcel);
    }

    private FrontendCapabilities(int i, java.lang.Object obj) {
        this._tag = i;
        this._value = obj;
    }

    public int getTag() {
        return this._tag;
    }

    public static android.hardware.tv.tuner.FrontendCapabilities analogCaps(android.hardware.tv.tuner.FrontendAnalogCapabilities frontendAnalogCapabilities) {
        return new android.hardware.tv.tuner.FrontendCapabilities(0, frontendAnalogCapabilities);
    }

    public android.hardware.tv.tuner.FrontendAnalogCapabilities getAnalogCaps() {
        _assertTag(0);
        return (android.hardware.tv.tuner.FrontendAnalogCapabilities) this._value;
    }

    public void setAnalogCaps(android.hardware.tv.tuner.FrontendAnalogCapabilities frontendAnalogCapabilities) {
        _set(0, frontendAnalogCapabilities);
    }

    public static android.hardware.tv.tuner.FrontendCapabilities atscCaps(android.hardware.tv.tuner.FrontendAtscCapabilities frontendAtscCapabilities) {
        return new android.hardware.tv.tuner.FrontendCapabilities(1, frontendAtscCapabilities);
    }

    public android.hardware.tv.tuner.FrontendAtscCapabilities getAtscCaps() {
        _assertTag(1);
        return (android.hardware.tv.tuner.FrontendAtscCapabilities) this._value;
    }

    public void setAtscCaps(android.hardware.tv.tuner.FrontendAtscCapabilities frontendAtscCapabilities) {
        _set(1, frontendAtscCapabilities);
    }

    public static android.hardware.tv.tuner.FrontendCapabilities atsc3Caps(android.hardware.tv.tuner.FrontendAtsc3Capabilities frontendAtsc3Capabilities) {
        return new android.hardware.tv.tuner.FrontendCapabilities(2, frontendAtsc3Capabilities);
    }

    public android.hardware.tv.tuner.FrontendAtsc3Capabilities getAtsc3Caps() {
        _assertTag(2);
        return (android.hardware.tv.tuner.FrontendAtsc3Capabilities) this._value;
    }

    public void setAtsc3Caps(android.hardware.tv.tuner.FrontendAtsc3Capabilities frontendAtsc3Capabilities) {
        _set(2, frontendAtsc3Capabilities);
    }

    public static android.hardware.tv.tuner.FrontendCapabilities dtmbCaps(android.hardware.tv.tuner.FrontendDtmbCapabilities frontendDtmbCapabilities) {
        return new android.hardware.tv.tuner.FrontendCapabilities(3, frontendDtmbCapabilities);
    }

    public android.hardware.tv.tuner.FrontendDtmbCapabilities getDtmbCaps() {
        _assertTag(3);
        return (android.hardware.tv.tuner.FrontendDtmbCapabilities) this._value;
    }

    public void setDtmbCaps(android.hardware.tv.tuner.FrontendDtmbCapabilities frontendDtmbCapabilities) {
        _set(3, frontendDtmbCapabilities);
    }

    public static android.hardware.tv.tuner.FrontendCapabilities dvbsCaps(android.hardware.tv.tuner.FrontendDvbsCapabilities frontendDvbsCapabilities) {
        return new android.hardware.tv.tuner.FrontendCapabilities(4, frontendDvbsCapabilities);
    }

    public android.hardware.tv.tuner.FrontendDvbsCapabilities getDvbsCaps() {
        _assertTag(4);
        return (android.hardware.tv.tuner.FrontendDvbsCapabilities) this._value;
    }

    public void setDvbsCaps(android.hardware.tv.tuner.FrontendDvbsCapabilities frontendDvbsCapabilities) {
        _set(4, frontendDvbsCapabilities);
    }

    public static android.hardware.tv.tuner.FrontendCapabilities dvbcCaps(android.hardware.tv.tuner.FrontendDvbcCapabilities frontendDvbcCapabilities) {
        return new android.hardware.tv.tuner.FrontendCapabilities(5, frontendDvbcCapabilities);
    }

    public android.hardware.tv.tuner.FrontendDvbcCapabilities getDvbcCaps() {
        _assertTag(5);
        return (android.hardware.tv.tuner.FrontendDvbcCapabilities) this._value;
    }

    public void setDvbcCaps(android.hardware.tv.tuner.FrontendDvbcCapabilities frontendDvbcCapabilities) {
        _set(5, frontendDvbcCapabilities);
    }

    public static android.hardware.tv.tuner.FrontendCapabilities dvbtCaps(android.hardware.tv.tuner.FrontendDvbtCapabilities frontendDvbtCapabilities) {
        return new android.hardware.tv.tuner.FrontendCapabilities(6, frontendDvbtCapabilities);
    }

    public android.hardware.tv.tuner.FrontendDvbtCapabilities getDvbtCaps() {
        _assertTag(6);
        return (android.hardware.tv.tuner.FrontendDvbtCapabilities) this._value;
    }

    public void setDvbtCaps(android.hardware.tv.tuner.FrontendDvbtCapabilities frontendDvbtCapabilities) {
        _set(6, frontendDvbtCapabilities);
    }

    public static android.hardware.tv.tuner.FrontendCapabilities isdbsCaps(android.hardware.tv.tuner.FrontendIsdbsCapabilities frontendIsdbsCapabilities) {
        return new android.hardware.tv.tuner.FrontendCapabilities(7, frontendIsdbsCapabilities);
    }

    public android.hardware.tv.tuner.FrontendIsdbsCapabilities getIsdbsCaps() {
        _assertTag(7);
        return (android.hardware.tv.tuner.FrontendIsdbsCapabilities) this._value;
    }

    public void setIsdbsCaps(android.hardware.tv.tuner.FrontendIsdbsCapabilities frontendIsdbsCapabilities) {
        _set(7, frontendIsdbsCapabilities);
    }

    public static android.hardware.tv.tuner.FrontendCapabilities isdbs3Caps(android.hardware.tv.tuner.FrontendIsdbs3Capabilities frontendIsdbs3Capabilities) {
        return new android.hardware.tv.tuner.FrontendCapabilities(8, frontendIsdbs3Capabilities);
    }

    public android.hardware.tv.tuner.FrontendIsdbs3Capabilities getIsdbs3Caps() {
        _assertTag(8);
        return (android.hardware.tv.tuner.FrontendIsdbs3Capabilities) this._value;
    }

    public void setIsdbs3Caps(android.hardware.tv.tuner.FrontendIsdbs3Capabilities frontendIsdbs3Capabilities) {
        _set(8, frontendIsdbs3Capabilities);
    }

    public static android.hardware.tv.tuner.FrontendCapabilities isdbtCaps(android.hardware.tv.tuner.FrontendIsdbtCapabilities frontendIsdbtCapabilities) {
        return new android.hardware.tv.tuner.FrontendCapabilities(9, frontendIsdbtCapabilities);
    }

    public android.hardware.tv.tuner.FrontendIsdbtCapabilities getIsdbtCaps() {
        _assertTag(9);
        return (android.hardware.tv.tuner.FrontendIsdbtCapabilities) this._value;
    }

    public void setIsdbtCaps(android.hardware.tv.tuner.FrontendIsdbtCapabilities frontendIsdbtCapabilities) {
        _set(9, frontendIsdbtCapabilities);
    }

    public static android.hardware.tv.tuner.FrontendCapabilities iptvCaps(android.hardware.tv.tuner.FrontendIptvCapabilities frontendIptvCapabilities) {
        return new android.hardware.tv.tuner.FrontendCapabilities(10, frontendIptvCapabilities);
    }

    public android.hardware.tv.tuner.FrontendIptvCapabilities getIptvCaps() {
        _assertTag(10);
        return (android.hardware.tv.tuner.FrontendIptvCapabilities) this._value;
    }

    public void setIptvCaps(android.hardware.tv.tuner.FrontendIptvCapabilities frontendIptvCapabilities) {
        _set(10, frontendIptvCapabilities);
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
                parcel.writeTypedObject(getAnalogCaps(), i);
                break;
            case 1:
                parcel.writeTypedObject(getAtscCaps(), i);
                break;
            case 2:
                parcel.writeTypedObject(getAtsc3Caps(), i);
                break;
            case 3:
                parcel.writeTypedObject(getDtmbCaps(), i);
                break;
            case 4:
                parcel.writeTypedObject(getDvbsCaps(), i);
                break;
            case 5:
                parcel.writeTypedObject(getDvbcCaps(), i);
                break;
            case 6:
                parcel.writeTypedObject(getDvbtCaps(), i);
                break;
            case 7:
                parcel.writeTypedObject(getIsdbsCaps(), i);
                break;
            case 8:
                parcel.writeTypedObject(getIsdbs3Caps(), i);
                break;
            case 9:
                parcel.writeTypedObject(getIsdbtCaps(), i);
                break;
            case 10:
                parcel.writeTypedObject(getIptvCaps(), i);
                break;
        }
    }

    public void readFromParcel(android.os.Parcel parcel) {
        int readInt = parcel.readInt();
        switch (readInt) {
            case 0:
                _set(readInt, (android.hardware.tv.tuner.FrontendAnalogCapabilities) parcel.readTypedObject(android.hardware.tv.tuner.FrontendAnalogCapabilities.CREATOR));
                return;
            case 1:
                _set(readInt, (android.hardware.tv.tuner.FrontendAtscCapabilities) parcel.readTypedObject(android.hardware.tv.tuner.FrontendAtscCapabilities.CREATOR));
                return;
            case 2:
                _set(readInt, (android.hardware.tv.tuner.FrontendAtsc3Capabilities) parcel.readTypedObject(android.hardware.tv.tuner.FrontendAtsc3Capabilities.CREATOR));
                return;
            case 3:
                _set(readInt, (android.hardware.tv.tuner.FrontendDtmbCapabilities) parcel.readTypedObject(android.hardware.tv.tuner.FrontendDtmbCapabilities.CREATOR));
                return;
            case 4:
                _set(readInt, (android.hardware.tv.tuner.FrontendDvbsCapabilities) parcel.readTypedObject(android.hardware.tv.tuner.FrontendDvbsCapabilities.CREATOR));
                return;
            case 5:
                _set(readInt, (android.hardware.tv.tuner.FrontendDvbcCapabilities) parcel.readTypedObject(android.hardware.tv.tuner.FrontendDvbcCapabilities.CREATOR));
                return;
            case 6:
                _set(readInt, (android.hardware.tv.tuner.FrontendDvbtCapabilities) parcel.readTypedObject(android.hardware.tv.tuner.FrontendDvbtCapabilities.CREATOR));
                return;
            case 7:
                _set(readInt, (android.hardware.tv.tuner.FrontendIsdbsCapabilities) parcel.readTypedObject(android.hardware.tv.tuner.FrontendIsdbsCapabilities.CREATOR));
                return;
            case 8:
                _set(readInt, (android.hardware.tv.tuner.FrontendIsdbs3Capabilities) parcel.readTypedObject(android.hardware.tv.tuner.FrontendIsdbs3Capabilities.CREATOR));
                return;
            case 9:
                _set(readInt, (android.hardware.tv.tuner.FrontendIsdbtCapabilities) parcel.readTypedObject(android.hardware.tv.tuner.FrontendIsdbtCapabilities.CREATOR));
                return;
            case 10:
                _set(readInt, (android.hardware.tv.tuner.FrontendIptvCapabilities) parcel.readTypedObject(android.hardware.tv.tuner.FrontendIptvCapabilities.CREATOR));
                return;
            default:
                throw new java.lang.IllegalArgumentException("union: unknown tag: " + readInt);
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        switch (getTag()) {
            case 0:
                return 0 | describeContents(getAnalogCaps());
            case 1:
                return 0 | describeContents(getAtscCaps());
            case 2:
                return 0 | describeContents(getAtsc3Caps());
            case 3:
                return 0 | describeContents(getDtmbCaps());
            case 4:
                return 0 | describeContents(getDvbsCaps());
            case 5:
                return 0 | describeContents(getDvbcCaps());
            case 6:
                return 0 | describeContents(getDvbtCaps());
            case 7:
                return 0 | describeContents(getIsdbsCaps());
            case 8:
                return 0 | describeContents(getIsdbs3Caps());
            case 9:
                return 0 | describeContents(getIsdbtCaps());
            case 10:
                return 0 | describeContents(getIptvCaps());
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
                return "analogCaps";
            case 1:
                return "atscCaps";
            case 2:
                return "atsc3Caps";
            case 3:
                return "dtmbCaps";
            case 4:
                return "dvbsCaps";
            case 5:
                return "dvbcCaps";
            case 6:
                return "dvbtCaps";
            case 7:
                return "isdbsCaps";
            case 8:
                return "isdbs3Caps";
            case 9:
                return "isdbtCaps";
            case 10:
                return "iptvCaps";
            default:
                throw new java.lang.IllegalStateException("unknown field: " + i);
        }
    }

    private void _set(int i, java.lang.Object obj) {
        this._tag = i;
        this._value = obj;
    }
}
