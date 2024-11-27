package android.os;

/* loaded from: classes3.dex */
public final class StatsBootstrapAtomValue implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.os.StatsBootstrapAtomValue> CREATOR = new android.os.Parcelable.Creator<android.os.StatsBootstrapAtomValue>() { // from class: android.os.StatsBootstrapAtomValue.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.os.StatsBootstrapAtomValue createFromParcel(android.os.Parcel parcel) {
            return new android.os.StatsBootstrapAtomValue(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.os.StatsBootstrapAtomValue[] newArray(int i) {
            return new android.os.StatsBootstrapAtomValue[i];
        }
    };
    public static final int boolValue = 0;
    public static final int bytesValue = 5;
    public static final int floatValue = 3;
    public static final int intValue = 1;
    public static final int longValue = 2;
    public static final int stringValue = 4;
    private int _tag;
    private java.lang.Object _value;

    public @interface Tag {
        public static final int boolValue = 0;
        public static final int bytesValue = 5;
        public static final int floatValue = 3;
        public static final int intValue = 1;
        public static final int longValue = 2;
        public static final int stringValue = 4;
    }

    public StatsBootstrapAtomValue() {
        this._tag = 0;
        this._value = false;
    }

    private StatsBootstrapAtomValue(android.os.Parcel parcel) {
        readFromParcel(parcel);
    }

    private StatsBootstrapAtomValue(int i, java.lang.Object obj) {
        this._tag = i;
        this._value = obj;
    }

    public int getTag() {
        return this._tag;
    }

    public static android.os.StatsBootstrapAtomValue boolValue(boolean z) {
        return new android.os.StatsBootstrapAtomValue(0, java.lang.Boolean.valueOf(z));
    }

    public boolean getBoolValue() {
        _assertTag(0);
        return ((java.lang.Boolean) this._value).booleanValue();
    }

    public void setBoolValue(boolean z) {
        _set(0, java.lang.Boolean.valueOf(z));
    }

    public static android.os.StatsBootstrapAtomValue intValue(int i) {
        return new android.os.StatsBootstrapAtomValue(1, java.lang.Integer.valueOf(i));
    }

    public int getIntValue() {
        _assertTag(1);
        return ((java.lang.Integer) this._value).intValue();
    }

    public void setIntValue(int i) {
        _set(1, java.lang.Integer.valueOf(i));
    }

    public static android.os.StatsBootstrapAtomValue longValue(long j) {
        return new android.os.StatsBootstrapAtomValue(2, java.lang.Long.valueOf(j));
    }

    public long getLongValue() {
        _assertTag(2);
        return ((java.lang.Long) this._value).longValue();
    }

    public void setLongValue(long j) {
        _set(2, java.lang.Long.valueOf(j));
    }

    public static android.os.StatsBootstrapAtomValue floatValue(float f) {
        return new android.os.StatsBootstrapAtomValue(3, java.lang.Float.valueOf(f));
    }

    public float getFloatValue() {
        _assertTag(3);
        return ((java.lang.Float) this._value).floatValue();
    }

    public void setFloatValue(float f) {
        _set(3, java.lang.Float.valueOf(f));
    }

    public static android.os.StatsBootstrapAtomValue stringValue(java.lang.String str) {
        return new android.os.StatsBootstrapAtomValue(4, str);
    }

    public java.lang.String getStringValue() {
        _assertTag(4);
        return (java.lang.String) this._value;
    }

    public void setStringValue(java.lang.String str) {
        _set(4, str);
    }

    public static android.os.StatsBootstrapAtomValue bytesValue(byte[] bArr) {
        return new android.os.StatsBootstrapAtomValue(5, bArr);
    }

    public byte[] getBytesValue() {
        _assertTag(5);
        return (byte[]) this._value;
    }

    public void setBytesValue(byte[] bArr) {
        _set(5, bArr);
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this._tag);
        switch (this._tag) {
            case 0:
                parcel.writeBoolean(getBoolValue());
                break;
            case 1:
                parcel.writeInt(getIntValue());
                break;
            case 2:
                parcel.writeLong(getLongValue());
                break;
            case 3:
                parcel.writeFloat(getFloatValue());
                break;
            case 4:
                parcel.writeString(getStringValue());
                break;
            case 5:
                parcel.writeByteArray(getBytesValue());
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
                _set(readInt, java.lang.Integer.valueOf(parcel.readInt()));
                return;
            case 2:
                _set(readInt, java.lang.Long.valueOf(parcel.readLong()));
                return;
            case 3:
                _set(readInt, java.lang.Float.valueOf(parcel.readFloat()));
                return;
            case 4:
                _set(readInt, parcel.readString());
                return;
            case 5:
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
                return "boolValue";
            case 1:
                return "intValue";
            case 2:
                return "longValue";
            case 3:
                return "floatValue";
            case 4:
                return "stringValue";
            case 5:
                return "bytesValue";
            default:
                throw new java.lang.IllegalStateException("unknown field: " + i);
        }
    }

    private void _set(int i, java.lang.Object obj) {
        this._tag = i;
        this._value = obj;
    }
}
