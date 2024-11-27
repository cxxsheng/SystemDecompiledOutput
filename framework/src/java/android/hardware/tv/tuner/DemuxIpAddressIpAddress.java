package android.hardware.tv.tuner;

/* loaded from: classes2.dex */
public final class DemuxIpAddressIpAddress implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.hardware.tv.tuner.DemuxIpAddressIpAddress> CREATOR = new android.os.Parcelable.Creator<android.hardware.tv.tuner.DemuxIpAddressIpAddress>() { // from class: android.hardware.tv.tuner.DemuxIpAddressIpAddress.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.tv.tuner.DemuxIpAddressIpAddress createFromParcel(android.os.Parcel parcel) {
            return new android.hardware.tv.tuner.DemuxIpAddressIpAddress(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.tv.tuner.DemuxIpAddressIpAddress[] newArray(int i) {
            return new android.hardware.tv.tuner.DemuxIpAddressIpAddress[i];
        }
    };
    public static final int v4 = 0;
    public static final int v6 = 1;
    private int _tag;
    private java.lang.Object _value;

    public @interface Tag {
        public static final int v4 = 0;
        public static final int v6 = 1;
    }

    public DemuxIpAddressIpAddress() {
        this._tag = 0;
        this._value = new byte[0];
    }

    private DemuxIpAddressIpAddress(android.os.Parcel parcel) {
        readFromParcel(parcel);
    }

    private DemuxIpAddressIpAddress(int i, java.lang.Object obj) {
        this._tag = i;
        this._value = obj;
    }

    public int getTag() {
        return this._tag;
    }

    public static android.hardware.tv.tuner.DemuxIpAddressIpAddress v4(byte[] bArr) {
        return new android.hardware.tv.tuner.DemuxIpAddressIpAddress(0, bArr);
    }

    public byte[] getV4() {
        _assertTag(0);
        return (byte[]) this._value;
    }

    public void setV4(byte[] bArr) {
        _set(0, bArr);
    }

    public static android.hardware.tv.tuner.DemuxIpAddressIpAddress v6(byte[] bArr) {
        return new android.hardware.tv.tuner.DemuxIpAddressIpAddress(1, bArr);
    }

    public byte[] getV6() {
        _assertTag(1);
        return (byte[]) this._value;
    }

    public void setV6(byte[] bArr) {
        _set(1, bArr);
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
                parcel.writeByteArray(getV4());
                break;
            case 1:
                parcel.writeByteArray(getV6());
                break;
        }
    }

    public void readFromParcel(android.os.Parcel parcel) {
        int readInt = parcel.readInt();
        switch (readInt) {
            case 0:
                _set(readInt, parcel.createByteArray());
                return;
            case 1:
                _set(readInt, parcel.createByteArray());
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
                return "v4";
            case 1:
                return "v6";
            default:
                throw new java.lang.IllegalStateException("unknown field: " + i);
        }
    }

    private void _set(int i, java.lang.Object obj) {
        this._tag = i;
        this._value = obj;
    }
}
