package android.hardware.tv.tuner;

/* loaded from: classes2.dex */
public final class FrontendScanMessage implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.hardware.tv.tuner.FrontendScanMessage> CREATOR = new android.os.Parcelable.Creator<android.hardware.tv.tuner.FrontendScanMessage>() { // from class: android.hardware.tv.tuner.FrontendScanMessage.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.tv.tuner.FrontendScanMessage createFromParcel(android.os.Parcel parcel) {
            return new android.hardware.tv.tuner.FrontendScanMessage(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.tv.tuner.FrontendScanMessage[] newArray(int i) {
            return new android.hardware.tv.tuner.FrontendScanMessage[i];
        }
    };
    public static final int analogType = 6;
    public static final int annex = 13;
    public static final int atsc3PlpInfos = 11;
    public static final int dvbtCellIds = 15;
    public static final int frequencies = 3;
    public static final int groupIds = 8;
    public static final int hierarchy = 5;
    public static final int inputStreamIds = 9;
    public static final int isEnd = 1;
    public static final int isHighPriority = 14;
    public static final int isLocked = 0;
    public static final int modulation = 12;
    public static final int plpIds = 7;
    public static final int progressPercent = 2;
    public static final int std = 10;
    public static final int symbolRates = 4;
    private int _tag;
    private java.lang.Object _value;

    public @interface Tag {
        public static final int analogType = 6;
        public static final int annex = 13;
        public static final int atsc3PlpInfos = 11;
        public static final int dvbtCellIds = 15;
        public static final int frequencies = 3;
        public static final int groupIds = 8;
        public static final int hierarchy = 5;
        public static final int inputStreamIds = 9;
        public static final int isEnd = 1;
        public static final int isHighPriority = 14;
        public static final int isLocked = 0;
        public static final int modulation = 12;
        public static final int plpIds = 7;
        public static final int progressPercent = 2;
        public static final int std = 10;
        public static final int symbolRates = 4;
    }

    public FrontendScanMessage() {
        this._tag = 0;
        this._value = false;
    }

    private FrontendScanMessage(android.os.Parcel parcel) {
        readFromParcel(parcel);
    }

    private FrontendScanMessage(int i, java.lang.Object obj) {
        this._tag = i;
        this._value = obj;
    }

    public int getTag() {
        return this._tag;
    }

    public static android.hardware.tv.tuner.FrontendScanMessage isLocked(boolean z) {
        return new android.hardware.tv.tuner.FrontendScanMessage(0, java.lang.Boolean.valueOf(z));
    }

    public boolean getIsLocked() {
        _assertTag(0);
        return ((java.lang.Boolean) this._value).booleanValue();
    }

    public void setIsLocked(boolean z) {
        _set(0, java.lang.Boolean.valueOf(z));
    }

    public static android.hardware.tv.tuner.FrontendScanMessage isEnd(boolean z) {
        return new android.hardware.tv.tuner.FrontendScanMessage(1, java.lang.Boolean.valueOf(z));
    }

    public boolean getIsEnd() {
        _assertTag(1);
        return ((java.lang.Boolean) this._value).booleanValue();
    }

    public void setIsEnd(boolean z) {
        _set(1, java.lang.Boolean.valueOf(z));
    }

    public static android.hardware.tv.tuner.FrontendScanMessage progressPercent(int i) {
        return new android.hardware.tv.tuner.FrontendScanMessage(2, java.lang.Integer.valueOf(i));
    }

    public int getProgressPercent() {
        _assertTag(2);
        return ((java.lang.Integer) this._value).intValue();
    }

    public void setProgressPercent(int i) {
        _set(2, java.lang.Integer.valueOf(i));
    }

    public static android.hardware.tv.tuner.FrontendScanMessage frequencies(long[] jArr) {
        return new android.hardware.tv.tuner.FrontendScanMessage(3, jArr);
    }

    public long[] getFrequencies() {
        _assertTag(3);
        return (long[]) this._value;
    }

    public void setFrequencies(long[] jArr) {
        _set(3, jArr);
    }

    public static android.hardware.tv.tuner.FrontendScanMessage symbolRates(int[] iArr) {
        return new android.hardware.tv.tuner.FrontendScanMessage(4, iArr);
    }

    public int[] getSymbolRates() {
        _assertTag(4);
        return (int[]) this._value;
    }

    public void setSymbolRates(int[] iArr) {
        _set(4, iArr);
    }

    public static android.hardware.tv.tuner.FrontendScanMessage hierarchy(int i) {
        return new android.hardware.tv.tuner.FrontendScanMessage(5, java.lang.Integer.valueOf(i));
    }

    public int getHierarchy() {
        _assertTag(5);
        return ((java.lang.Integer) this._value).intValue();
    }

    public void setHierarchy(int i) {
        _set(5, java.lang.Integer.valueOf(i));
    }

    public static android.hardware.tv.tuner.FrontendScanMessage analogType(int i) {
        return new android.hardware.tv.tuner.FrontendScanMessage(6, java.lang.Integer.valueOf(i));
    }

    public int getAnalogType() {
        _assertTag(6);
        return ((java.lang.Integer) this._value).intValue();
    }

    public void setAnalogType(int i) {
        _set(6, java.lang.Integer.valueOf(i));
    }

    public static android.hardware.tv.tuner.FrontendScanMessage plpIds(int[] iArr) {
        return new android.hardware.tv.tuner.FrontendScanMessage(7, iArr);
    }

    public int[] getPlpIds() {
        _assertTag(7);
        return (int[]) this._value;
    }

    public void setPlpIds(int[] iArr) {
        _set(7, iArr);
    }

    public static android.hardware.tv.tuner.FrontendScanMessage groupIds(int[] iArr) {
        return new android.hardware.tv.tuner.FrontendScanMessage(8, iArr);
    }

    public int[] getGroupIds() {
        _assertTag(8);
        return (int[]) this._value;
    }

    public void setGroupIds(int[] iArr) {
        _set(8, iArr);
    }

    public static android.hardware.tv.tuner.FrontendScanMessage inputStreamIds(int[] iArr) {
        return new android.hardware.tv.tuner.FrontendScanMessage(9, iArr);
    }

    public int[] getInputStreamIds() {
        _assertTag(9);
        return (int[]) this._value;
    }

    public void setInputStreamIds(int[] iArr) {
        _set(9, iArr);
    }

    public static android.hardware.tv.tuner.FrontendScanMessage std(android.hardware.tv.tuner.FrontendScanMessageStandard frontendScanMessageStandard) {
        return new android.hardware.tv.tuner.FrontendScanMessage(10, frontendScanMessageStandard);
    }

    public android.hardware.tv.tuner.FrontendScanMessageStandard getStd() {
        _assertTag(10);
        return (android.hardware.tv.tuner.FrontendScanMessageStandard) this._value;
    }

    public void setStd(android.hardware.tv.tuner.FrontendScanMessageStandard frontendScanMessageStandard) {
        _set(10, frontendScanMessageStandard);
    }

    public static android.hardware.tv.tuner.FrontendScanMessage atsc3PlpInfos(android.hardware.tv.tuner.FrontendScanAtsc3PlpInfo[] frontendScanAtsc3PlpInfoArr) {
        return new android.hardware.tv.tuner.FrontendScanMessage(11, frontendScanAtsc3PlpInfoArr);
    }

    public android.hardware.tv.tuner.FrontendScanAtsc3PlpInfo[] getAtsc3PlpInfos() {
        _assertTag(11);
        return (android.hardware.tv.tuner.FrontendScanAtsc3PlpInfo[]) this._value;
    }

    public void setAtsc3PlpInfos(android.hardware.tv.tuner.FrontendScanAtsc3PlpInfo[] frontendScanAtsc3PlpInfoArr) {
        _set(11, frontendScanAtsc3PlpInfoArr);
    }

    public static android.hardware.tv.tuner.FrontendScanMessage modulation(android.hardware.tv.tuner.FrontendModulation frontendModulation) {
        return new android.hardware.tv.tuner.FrontendScanMessage(12, frontendModulation);
    }

    public android.hardware.tv.tuner.FrontendModulation getModulation() {
        _assertTag(12);
        return (android.hardware.tv.tuner.FrontendModulation) this._value;
    }

    public void setModulation(android.hardware.tv.tuner.FrontendModulation frontendModulation) {
        _set(12, frontendModulation);
    }

    public static android.hardware.tv.tuner.FrontendScanMessage annex(byte b) {
        return new android.hardware.tv.tuner.FrontendScanMessage(13, java.lang.Byte.valueOf(b));
    }

    public byte getAnnex() {
        _assertTag(13);
        return ((java.lang.Byte) this._value).byteValue();
    }

    public void setAnnex(byte b) {
        _set(13, java.lang.Byte.valueOf(b));
    }

    public static android.hardware.tv.tuner.FrontendScanMessage isHighPriority(boolean z) {
        return new android.hardware.tv.tuner.FrontendScanMessage(14, java.lang.Boolean.valueOf(z));
    }

    public boolean getIsHighPriority() {
        _assertTag(14);
        return ((java.lang.Boolean) this._value).booleanValue();
    }

    public void setIsHighPriority(boolean z) {
        _set(14, java.lang.Boolean.valueOf(z));
    }

    public static android.hardware.tv.tuner.FrontendScanMessage dvbtCellIds(int[] iArr) {
        return new android.hardware.tv.tuner.FrontendScanMessage(15, iArr);
    }

    public int[] getDvbtCellIds() {
        _assertTag(15);
        return (int[]) this._value;
    }

    public void setDvbtCellIds(int[] iArr) {
        _set(15, iArr);
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
                parcel.writeBoolean(getIsLocked());
                break;
            case 1:
                parcel.writeBoolean(getIsEnd());
                break;
            case 2:
                parcel.writeInt(getProgressPercent());
                break;
            case 3:
                parcel.writeLongArray(getFrequencies());
                break;
            case 4:
                parcel.writeIntArray(getSymbolRates());
                break;
            case 5:
                parcel.writeInt(getHierarchy());
                break;
            case 6:
                parcel.writeInt(getAnalogType());
                break;
            case 7:
                parcel.writeIntArray(getPlpIds());
                break;
            case 8:
                parcel.writeIntArray(getGroupIds());
                break;
            case 9:
                parcel.writeIntArray(getInputStreamIds());
                break;
            case 10:
                parcel.writeTypedObject(getStd(), i);
                break;
            case 11:
                parcel.writeTypedArray(getAtsc3PlpInfos(), i);
                break;
            case 12:
                parcel.writeTypedObject(getModulation(), i);
                break;
            case 13:
                parcel.writeByte(getAnnex());
                break;
            case 14:
                parcel.writeBoolean(getIsHighPriority());
                break;
            case 15:
                parcel.writeIntArray(getDvbtCellIds());
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
                _set(readInt, java.lang.Boolean.valueOf(parcel.readBoolean()));
                return;
            case 2:
                _set(readInt, java.lang.Integer.valueOf(parcel.readInt()));
                return;
            case 3:
                _set(readInt, parcel.createLongArray());
                return;
            case 4:
                _set(readInt, parcel.createIntArray());
                return;
            case 5:
                _set(readInt, java.lang.Integer.valueOf(parcel.readInt()));
                return;
            case 6:
                _set(readInt, java.lang.Integer.valueOf(parcel.readInt()));
                return;
            case 7:
                _set(readInt, parcel.createIntArray());
                return;
            case 8:
                _set(readInt, parcel.createIntArray());
                return;
            case 9:
                _set(readInt, parcel.createIntArray());
                return;
            case 10:
                _set(readInt, (android.hardware.tv.tuner.FrontendScanMessageStandard) parcel.readTypedObject(android.hardware.tv.tuner.FrontendScanMessageStandard.CREATOR));
                return;
            case 11:
                _set(readInt, (android.hardware.tv.tuner.FrontendScanAtsc3PlpInfo[]) parcel.createTypedArray(android.hardware.tv.tuner.FrontendScanAtsc3PlpInfo.CREATOR));
                return;
            case 12:
                _set(readInt, (android.hardware.tv.tuner.FrontendModulation) parcel.readTypedObject(android.hardware.tv.tuner.FrontendModulation.CREATOR));
                return;
            case 13:
                _set(readInt, java.lang.Byte.valueOf(parcel.readByte()));
                return;
            case 14:
                _set(readInt, java.lang.Boolean.valueOf(parcel.readBoolean()));
                return;
            case 15:
                _set(readInt, parcel.createIntArray());
                return;
            default:
                throw new java.lang.IllegalArgumentException("union: unknown tag: " + readInt);
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        switch (getTag()) {
            case 10:
                return 0 | describeContents(getStd());
            case 11:
                return 0 | describeContents(getAtsc3PlpInfos());
            case 12:
                return 0 | describeContents(getModulation());
            default:
                return 0;
        }
    }

    private int describeContents(java.lang.Object obj) {
        if (obj == null) {
            return 0;
        }
        if (obj instanceof java.lang.Object[]) {
            int i = 0;
            for (java.lang.Object obj2 : (java.lang.Object[]) obj) {
                i |= describeContents(obj2);
            }
            return i;
        }
        if (!(obj instanceof android.os.Parcelable)) {
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
                return "isLocked";
            case 1:
                return "isEnd";
            case 2:
                return "progressPercent";
            case 3:
                return "frequencies";
            case 4:
                return "symbolRates";
            case 5:
                return "hierarchy";
            case 6:
                return "analogType";
            case 7:
                return "plpIds";
            case 8:
                return "groupIds";
            case 9:
                return "inputStreamIds";
            case 10:
                return "std";
            case 11:
                return "atsc3PlpInfos";
            case 12:
                return "modulation";
            case 13:
                return "annex";
            case 14:
                return "isHighPriority";
            case 15:
                return "dvbtCellIds";
            default:
                throw new java.lang.IllegalStateException("unknown field: " + i);
        }
    }

    private void _set(int i, java.lang.Object obj) {
        this._tag = i;
        this._value = obj;
    }
}
