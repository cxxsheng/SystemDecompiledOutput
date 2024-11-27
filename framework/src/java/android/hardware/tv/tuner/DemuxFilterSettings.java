package android.hardware.tv.tuner;

/* loaded from: classes2.dex */
public final class DemuxFilterSettings implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.hardware.tv.tuner.DemuxFilterSettings> CREATOR = new android.os.Parcelable.Creator<android.hardware.tv.tuner.DemuxFilterSettings>() { // from class: android.hardware.tv.tuner.DemuxFilterSettings.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.tv.tuner.DemuxFilterSettings createFromParcel(android.os.Parcel parcel) {
            return new android.hardware.tv.tuner.DemuxFilterSettings(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.tv.tuner.DemuxFilterSettings[] newArray(int i) {
            return new android.hardware.tv.tuner.DemuxFilterSettings[i];
        }
    };
    public static final int alp = 4;
    public static final int ip = 2;
    public static final int mmtp = 1;
    public static final int tlv = 3;
    public static final int ts = 0;
    private int _tag;
    private java.lang.Object _value;

    public @interface Tag {
        public static final int alp = 4;
        public static final int ip = 2;
        public static final int mmtp = 1;
        public static final int tlv = 3;
        public static final int ts = 0;
    }

    public DemuxFilterSettings() {
        this._tag = 0;
        this._value = null;
    }

    private DemuxFilterSettings(android.os.Parcel parcel) {
        readFromParcel(parcel);
    }

    private DemuxFilterSettings(int i, java.lang.Object obj) {
        this._tag = i;
        this._value = obj;
    }

    public int getTag() {
        return this._tag;
    }

    public static android.hardware.tv.tuner.DemuxFilterSettings ts(android.hardware.tv.tuner.DemuxTsFilterSettings demuxTsFilterSettings) {
        return new android.hardware.tv.tuner.DemuxFilterSettings(0, demuxTsFilterSettings);
    }

    public android.hardware.tv.tuner.DemuxTsFilterSettings getTs() {
        _assertTag(0);
        return (android.hardware.tv.tuner.DemuxTsFilterSettings) this._value;
    }

    public void setTs(android.hardware.tv.tuner.DemuxTsFilterSettings demuxTsFilterSettings) {
        _set(0, demuxTsFilterSettings);
    }

    public static android.hardware.tv.tuner.DemuxFilterSettings mmtp(android.hardware.tv.tuner.DemuxMmtpFilterSettings demuxMmtpFilterSettings) {
        return new android.hardware.tv.tuner.DemuxFilterSettings(1, demuxMmtpFilterSettings);
    }

    public android.hardware.tv.tuner.DemuxMmtpFilterSettings getMmtp() {
        _assertTag(1);
        return (android.hardware.tv.tuner.DemuxMmtpFilterSettings) this._value;
    }

    public void setMmtp(android.hardware.tv.tuner.DemuxMmtpFilterSettings demuxMmtpFilterSettings) {
        _set(1, demuxMmtpFilterSettings);
    }

    public static android.hardware.tv.tuner.DemuxFilterSettings ip(android.hardware.tv.tuner.DemuxIpFilterSettings demuxIpFilterSettings) {
        return new android.hardware.tv.tuner.DemuxFilterSettings(2, demuxIpFilterSettings);
    }

    public android.hardware.tv.tuner.DemuxIpFilterSettings getIp() {
        _assertTag(2);
        return (android.hardware.tv.tuner.DemuxIpFilterSettings) this._value;
    }

    public void setIp(android.hardware.tv.tuner.DemuxIpFilterSettings demuxIpFilterSettings) {
        _set(2, demuxIpFilterSettings);
    }

    public static android.hardware.tv.tuner.DemuxFilterSettings tlv(android.hardware.tv.tuner.DemuxTlvFilterSettings demuxTlvFilterSettings) {
        return new android.hardware.tv.tuner.DemuxFilterSettings(3, demuxTlvFilterSettings);
    }

    public android.hardware.tv.tuner.DemuxTlvFilterSettings getTlv() {
        _assertTag(3);
        return (android.hardware.tv.tuner.DemuxTlvFilterSettings) this._value;
    }

    public void setTlv(android.hardware.tv.tuner.DemuxTlvFilterSettings demuxTlvFilterSettings) {
        _set(3, demuxTlvFilterSettings);
    }

    public static android.hardware.tv.tuner.DemuxFilterSettings alp(android.hardware.tv.tuner.DemuxAlpFilterSettings demuxAlpFilterSettings) {
        return new android.hardware.tv.tuner.DemuxFilterSettings(4, demuxAlpFilterSettings);
    }

    public android.hardware.tv.tuner.DemuxAlpFilterSettings getAlp() {
        _assertTag(4);
        return (android.hardware.tv.tuner.DemuxAlpFilterSettings) this._value;
    }

    public void setAlp(android.hardware.tv.tuner.DemuxAlpFilterSettings demuxAlpFilterSettings) {
        _set(4, demuxAlpFilterSettings);
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
                parcel.writeTypedObject(getTs(), i);
                break;
            case 1:
                parcel.writeTypedObject(getMmtp(), i);
                break;
            case 2:
                parcel.writeTypedObject(getIp(), i);
                break;
            case 3:
                parcel.writeTypedObject(getTlv(), i);
                break;
            case 4:
                parcel.writeTypedObject(getAlp(), i);
                break;
        }
    }

    public void readFromParcel(android.os.Parcel parcel) {
        int readInt = parcel.readInt();
        switch (readInt) {
            case 0:
                _set(readInt, (android.hardware.tv.tuner.DemuxTsFilterSettings) parcel.readTypedObject(android.hardware.tv.tuner.DemuxTsFilterSettings.CREATOR));
                return;
            case 1:
                _set(readInt, (android.hardware.tv.tuner.DemuxMmtpFilterSettings) parcel.readTypedObject(android.hardware.tv.tuner.DemuxMmtpFilterSettings.CREATOR));
                return;
            case 2:
                _set(readInt, (android.hardware.tv.tuner.DemuxIpFilterSettings) parcel.readTypedObject(android.hardware.tv.tuner.DemuxIpFilterSettings.CREATOR));
                return;
            case 3:
                _set(readInt, (android.hardware.tv.tuner.DemuxTlvFilterSettings) parcel.readTypedObject(android.hardware.tv.tuner.DemuxTlvFilterSettings.CREATOR));
                return;
            case 4:
                _set(readInt, (android.hardware.tv.tuner.DemuxAlpFilterSettings) parcel.readTypedObject(android.hardware.tv.tuner.DemuxAlpFilterSettings.CREATOR));
                return;
            default:
                throw new java.lang.IllegalArgumentException("union: unknown tag: " + readInt);
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        switch (getTag()) {
            case 0:
                return 0 | describeContents(getTs());
            case 1:
                return 0 | describeContents(getMmtp());
            case 2:
                return 0 | describeContents(getIp());
            case 3:
                return 0 | describeContents(getTlv());
            case 4:
                return 0 | describeContents(getAlp());
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
                return "ts";
            case 1:
                return "mmtp";
            case 2:
                return "ip";
            case 3:
                return "tlv";
            case 4:
                return "alp";
            default:
                throw new java.lang.IllegalStateException("unknown field: " + i);
        }
    }

    private void _set(int i, java.lang.Object obj) {
        this._tag = i;
        this._value = obj;
    }
}
