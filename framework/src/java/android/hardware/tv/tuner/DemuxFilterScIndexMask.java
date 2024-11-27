package android.hardware.tv.tuner;

/* loaded from: classes2.dex */
public final class DemuxFilterScIndexMask implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.hardware.tv.tuner.DemuxFilterScIndexMask> CREATOR = new android.os.Parcelable.Creator<android.hardware.tv.tuner.DemuxFilterScIndexMask>() { // from class: android.hardware.tv.tuner.DemuxFilterScIndexMask.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.tv.tuner.DemuxFilterScIndexMask createFromParcel(android.os.Parcel parcel) {
            return new android.hardware.tv.tuner.DemuxFilterScIndexMask(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.tv.tuner.DemuxFilterScIndexMask[] newArray(int i) {
            return new android.hardware.tv.tuner.DemuxFilterScIndexMask[i];
        }
    };
    public static final int scAvc = 1;
    public static final int scHevc = 2;
    public static final int scIndex = 0;
    public static final int scVvc = 3;
    private int _tag;
    private java.lang.Object _value;

    public @interface Tag {
        public static final int scAvc = 1;
        public static final int scHevc = 2;
        public static final int scIndex = 0;
        public static final int scVvc = 3;
    }

    public DemuxFilterScIndexMask() {
        this._tag = 0;
        this._value = 0;
    }

    private DemuxFilterScIndexMask(android.os.Parcel parcel) {
        readFromParcel(parcel);
    }

    private DemuxFilterScIndexMask(int i, java.lang.Object obj) {
        this._tag = i;
        this._value = obj;
    }

    public int getTag() {
        return this._tag;
    }

    public static android.hardware.tv.tuner.DemuxFilterScIndexMask scIndex(int i) {
        return new android.hardware.tv.tuner.DemuxFilterScIndexMask(0, java.lang.Integer.valueOf(i));
    }

    public int getScIndex() {
        _assertTag(0);
        return ((java.lang.Integer) this._value).intValue();
    }

    public void setScIndex(int i) {
        _set(0, java.lang.Integer.valueOf(i));
    }

    public static android.hardware.tv.tuner.DemuxFilterScIndexMask scAvc(int i) {
        return new android.hardware.tv.tuner.DemuxFilterScIndexMask(1, java.lang.Integer.valueOf(i));
    }

    public int getScAvc() {
        _assertTag(1);
        return ((java.lang.Integer) this._value).intValue();
    }

    public void setScAvc(int i) {
        _set(1, java.lang.Integer.valueOf(i));
    }

    public static android.hardware.tv.tuner.DemuxFilterScIndexMask scHevc(int i) {
        return new android.hardware.tv.tuner.DemuxFilterScIndexMask(2, java.lang.Integer.valueOf(i));
    }

    public int getScHevc() {
        _assertTag(2);
        return ((java.lang.Integer) this._value).intValue();
    }

    public void setScHevc(int i) {
        _set(2, java.lang.Integer.valueOf(i));
    }

    public static android.hardware.tv.tuner.DemuxFilterScIndexMask scVvc(int i) {
        return new android.hardware.tv.tuner.DemuxFilterScIndexMask(3, java.lang.Integer.valueOf(i));
    }

    public int getScVvc() {
        _assertTag(3);
        return ((java.lang.Integer) this._value).intValue();
    }

    public void setScVvc(int i) {
        _set(3, java.lang.Integer.valueOf(i));
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
                parcel.writeInt(getScIndex());
                break;
            case 1:
                parcel.writeInt(getScAvc());
                break;
            case 2:
                parcel.writeInt(getScHevc());
                break;
            case 3:
                parcel.writeInt(getScVvc());
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
                return "scIndex";
            case 1:
                return "scAvc";
            case 2:
                return "scHevc";
            case 3:
                return "scVvc";
            default:
                throw new java.lang.IllegalStateException("unknown field: " + i);
        }
    }

    private void _set(int i, java.lang.Object obj) {
        this._tag = i;
        this._value = obj;
    }
}
