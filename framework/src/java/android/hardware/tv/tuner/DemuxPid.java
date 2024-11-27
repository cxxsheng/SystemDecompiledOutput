package android.hardware.tv.tuner;

/* loaded from: classes2.dex */
public final class DemuxPid implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.hardware.tv.tuner.DemuxPid> CREATOR = new android.os.Parcelable.Creator<android.hardware.tv.tuner.DemuxPid>() { // from class: android.hardware.tv.tuner.DemuxPid.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.tv.tuner.DemuxPid createFromParcel(android.os.Parcel parcel) {
            return new android.hardware.tv.tuner.DemuxPid(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.tv.tuner.DemuxPid[] newArray(int i) {
            return new android.hardware.tv.tuner.DemuxPid[i];
        }
    };
    public static final int mmtpPid = 1;
    public static final int tPid = 0;
    private int _tag;
    private java.lang.Object _value;

    public @interface Tag {
        public static final int mmtpPid = 1;
        public static final int tPid = 0;
    }

    public DemuxPid() {
        this._tag = 0;
        this._value = 0;
    }

    private DemuxPid(android.os.Parcel parcel) {
        readFromParcel(parcel);
    }

    private DemuxPid(int i, java.lang.Object obj) {
        this._tag = i;
        this._value = obj;
    }

    public int getTag() {
        return this._tag;
    }

    public static android.hardware.tv.tuner.DemuxPid tPid(int i) {
        return new android.hardware.tv.tuner.DemuxPid(0, java.lang.Integer.valueOf(i));
    }

    public int getTPid() {
        _assertTag(0);
        return ((java.lang.Integer) this._value).intValue();
    }

    public void setTPid(int i) {
        _set(0, java.lang.Integer.valueOf(i));
    }

    public static android.hardware.tv.tuner.DemuxPid mmtpPid(int i) {
        return new android.hardware.tv.tuner.DemuxPid(1, java.lang.Integer.valueOf(i));
    }

    public int getMmtpPid() {
        _assertTag(1);
        return ((java.lang.Integer) this._value).intValue();
    }

    public void setMmtpPid(int i) {
        _set(1, java.lang.Integer.valueOf(i));
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
                parcel.writeInt(getTPid());
                break;
            case 1:
                parcel.writeInt(getMmtpPid());
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
                return "tPid";
            case 1:
                return "mmtpPid";
            default:
                throw new java.lang.IllegalStateException("unknown field: " + i);
        }
    }

    private void _set(int i, java.lang.Object obj) {
        this._tag = i;
        this._value = obj;
    }
}
