package android.hardware.tv.tuner;

/* loaded from: classes2.dex */
public final class DemuxFilterMonitorEvent implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.hardware.tv.tuner.DemuxFilterMonitorEvent> CREATOR = new android.os.Parcelable.Creator<android.hardware.tv.tuner.DemuxFilterMonitorEvent>() { // from class: android.hardware.tv.tuner.DemuxFilterMonitorEvent.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.tv.tuner.DemuxFilterMonitorEvent createFromParcel(android.os.Parcel parcel) {
            return new android.hardware.tv.tuner.DemuxFilterMonitorEvent(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.tv.tuner.DemuxFilterMonitorEvent[] newArray(int i) {
            return new android.hardware.tv.tuner.DemuxFilterMonitorEvent[i];
        }
    };
    public static final int cid = 1;
    public static final int scramblingStatus = 0;
    private int _tag;
    private java.lang.Object _value;

    public @interface Tag {
        public static final int cid = 1;
        public static final int scramblingStatus = 0;
    }

    public DemuxFilterMonitorEvent() {
        this._tag = 0;
        this._value = 1;
    }

    private DemuxFilterMonitorEvent(android.os.Parcel parcel) {
        readFromParcel(parcel);
    }

    private DemuxFilterMonitorEvent(int i, java.lang.Object obj) {
        this._tag = i;
        this._value = obj;
    }

    public int getTag() {
        return this._tag;
    }

    public static android.hardware.tv.tuner.DemuxFilterMonitorEvent scramblingStatus(int i) {
        return new android.hardware.tv.tuner.DemuxFilterMonitorEvent(0, java.lang.Integer.valueOf(i));
    }

    public int getScramblingStatus() {
        _assertTag(0);
        return ((java.lang.Integer) this._value).intValue();
    }

    public void setScramblingStatus(int i) {
        _set(0, java.lang.Integer.valueOf(i));
    }

    public static android.hardware.tv.tuner.DemuxFilterMonitorEvent cid(int i) {
        return new android.hardware.tv.tuner.DemuxFilterMonitorEvent(1, java.lang.Integer.valueOf(i));
    }

    public int getCid() {
        _assertTag(1);
        return ((java.lang.Integer) this._value).intValue();
    }

    public void setCid(int i) {
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
                parcel.writeInt(getScramblingStatus());
                break;
            case 1:
                parcel.writeInt(getCid());
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
                return "scramblingStatus";
            case 1:
                return "cid";
            default:
                throw new java.lang.IllegalStateException("unknown field: " + i);
        }
    }

    private void _set(int i, java.lang.Object obj) {
        this._tag = i;
        this._value = obj;
    }
}
