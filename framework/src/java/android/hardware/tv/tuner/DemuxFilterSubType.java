package android.hardware.tv.tuner;

/* loaded from: classes2.dex */
public final class DemuxFilterSubType implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.hardware.tv.tuner.DemuxFilterSubType> CREATOR = new android.os.Parcelable.Creator<android.hardware.tv.tuner.DemuxFilterSubType>() { // from class: android.hardware.tv.tuner.DemuxFilterSubType.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.tv.tuner.DemuxFilterSubType createFromParcel(android.os.Parcel parcel) {
            return new android.hardware.tv.tuner.DemuxFilterSubType(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.tv.tuner.DemuxFilterSubType[] newArray(int i) {
            return new android.hardware.tv.tuner.DemuxFilterSubType[i];
        }
    };
    public static final int alpFilterType = 4;
    public static final int ipFilterType = 2;
    public static final int mmtpFilterType = 1;
    public static final int tlvFilterType = 3;
    public static final int tsFilterType = 0;
    private int _tag;
    private java.lang.Object _value;

    public @interface Tag {
        public static final int alpFilterType = 4;
        public static final int ipFilterType = 2;
        public static final int mmtpFilterType = 1;
        public static final int tlvFilterType = 3;
        public static final int tsFilterType = 0;
    }

    public DemuxFilterSubType() {
        this._tag = 0;
        this._value = 0;
    }

    private DemuxFilterSubType(android.os.Parcel parcel) {
        readFromParcel(parcel);
    }

    private DemuxFilterSubType(int i, java.lang.Object obj) {
        this._tag = i;
        this._value = obj;
    }

    public int getTag() {
        return this._tag;
    }

    public static android.hardware.tv.tuner.DemuxFilterSubType tsFilterType(int i) {
        return new android.hardware.tv.tuner.DemuxFilterSubType(0, java.lang.Integer.valueOf(i));
    }

    public int getTsFilterType() {
        _assertTag(0);
        return ((java.lang.Integer) this._value).intValue();
    }

    public void setTsFilterType(int i) {
        _set(0, java.lang.Integer.valueOf(i));
    }

    public static android.hardware.tv.tuner.DemuxFilterSubType mmtpFilterType(int i) {
        return new android.hardware.tv.tuner.DemuxFilterSubType(1, java.lang.Integer.valueOf(i));
    }

    public int getMmtpFilterType() {
        _assertTag(1);
        return ((java.lang.Integer) this._value).intValue();
    }

    public void setMmtpFilterType(int i) {
        _set(1, java.lang.Integer.valueOf(i));
    }

    public static android.hardware.tv.tuner.DemuxFilterSubType ipFilterType(int i) {
        return new android.hardware.tv.tuner.DemuxFilterSubType(2, java.lang.Integer.valueOf(i));
    }

    public int getIpFilterType() {
        _assertTag(2);
        return ((java.lang.Integer) this._value).intValue();
    }

    public void setIpFilterType(int i) {
        _set(2, java.lang.Integer.valueOf(i));
    }

    public static android.hardware.tv.tuner.DemuxFilterSubType tlvFilterType(int i) {
        return new android.hardware.tv.tuner.DemuxFilterSubType(3, java.lang.Integer.valueOf(i));
    }

    public int getTlvFilterType() {
        _assertTag(3);
        return ((java.lang.Integer) this._value).intValue();
    }

    public void setTlvFilterType(int i) {
        _set(3, java.lang.Integer.valueOf(i));
    }

    public static android.hardware.tv.tuner.DemuxFilterSubType alpFilterType(int i) {
        return new android.hardware.tv.tuner.DemuxFilterSubType(4, java.lang.Integer.valueOf(i));
    }

    public int getAlpFilterType() {
        _assertTag(4);
        return ((java.lang.Integer) this._value).intValue();
    }

    public void setAlpFilterType(int i) {
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
                parcel.writeInt(getTsFilterType());
                break;
            case 1:
                parcel.writeInt(getMmtpFilterType());
                break;
            case 2:
                parcel.writeInt(getIpFilterType());
                break;
            case 3:
                parcel.writeInt(getTlvFilterType());
                break;
            case 4:
                parcel.writeInt(getAlpFilterType());
                break;
        }
    }

    public void readFromParcel(android.os.Parcel parcel) {
        int readInt = parcel.readInt();
        switch (readInt) {
            case 0:
                _set(readInt, java.lang.Integer.valueOf(parcel.readInt()));
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

    private void _assertTag(int i) {
        if (getTag() != i) {
            throw new java.lang.IllegalStateException("bad access: " + _tagString(i) + ", " + _tagString(getTag()) + " is available.");
        }
    }

    private java.lang.String _tagString(int i) {
        switch (i) {
            case 0:
                return "tsFilterType";
            case 1:
                return "mmtpFilterType";
            case 2:
                return "ipFilterType";
            case 3:
                return "tlvFilterType";
            case 4:
                return "alpFilterType";
            default:
                throw new java.lang.IllegalStateException("unknown field: " + i);
        }
    }

    private void _set(int i, java.lang.Object obj) {
        this._tag = i;
        this._value = obj;
    }
}
