package android.hardware.tv.tuner;

/* loaded from: classes2.dex */
public final class DemuxFilterSectionSettingsCondition implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.hardware.tv.tuner.DemuxFilterSectionSettingsCondition> CREATOR = new android.os.Parcelable.Creator<android.hardware.tv.tuner.DemuxFilterSectionSettingsCondition>() { // from class: android.hardware.tv.tuner.DemuxFilterSectionSettingsCondition.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.tv.tuner.DemuxFilterSectionSettingsCondition createFromParcel(android.os.Parcel parcel) {
            return new android.hardware.tv.tuner.DemuxFilterSectionSettingsCondition(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.tv.tuner.DemuxFilterSectionSettingsCondition[] newArray(int i) {
            return new android.hardware.tv.tuner.DemuxFilterSectionSettingsCondition[i];
        }
    };
    public static final int sectionBits = 0;
    public static final int tableInfo = 1;
    private int _tag;
    private java.lang.Object _value;

    public @interface Tag {
        public static final int sectionBits = 0;
        public static final int tableInfo = 1;
    }

    public DemuxFilterSectionSettingsCondition() {
        this._tag = 0;
        this._value = null;
    }

    private DemuxFilterSectionSettingsCondition(android.os.Parcel parcel) {
        readFromParcel(parcel);
    }

    private DemuxFilterSectionSettingsCondition(int i, java.lang.Object obj) {
        this._tag = i;
        this._value = obj;
    }

    public int getTag() {
        return this._tag;
    }

    public static android.hardware.tv.tuner.DemuxFilterSectionSettingsCondition sectionBits(android.hardware.tv.tuner.DemuxFilterSectionBits demuxFilterSectionBits) {
        return new android.hardware.tv.tuner.DemuxFilterSectionSettingsCondition(0, demuxFilterSectionBits);
    }

    public android.hardware.tv.tuner.DemuxFilterSectionBits getSectionBits() {
        _assertTag(0);
        return (android.hardware.tv.tuner.DemuxFilterSectionBits) this._value;
    }

    public void setSectionBits(android.hardware.tv.tuner.DemuxFilterSectionBits demuxFilterSectionBits) {
        _set(0, demuxFilterSectionBits);
    }

    public static android.hardware.tv.tuner.DemuxFilterSectionSettingsCondition tableInfo(android.hardware.tv.tuner.DemuxFilterSectionSettingsConditionTableInfo demuxFilterSectionSettingsConditionTableInfo) {
        return new android.hardware.tv.tuner.DemuxFilterSectionSettingsCondition(1, demuxFilterSectionSettingsConditionTableInfo);
    }

    public android.hardware.tv.tuner.DemuxFilterSectionSettingsConditionTableInfo getTableInfo() {
        _assertTag(1);
        return (android.hardware.tv.tuner.DemuxFilterSectionSettingsConditionTableInfo) this._value;
    }

    public void setTableInfo(android.hardware.tv.tuner.DemuxFilterSectionSettingsConditionTableInfo demuxFilterSectionSettingsConditionTableInfo) {
        _set(1, demuxFilterSectionSettingsConditionTableInfo);
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
                parcel.writeTypedObject(getSectionBits(), i);
                break;
            case 1:
                parcel.writeTypedObject(getTableInfo(), i);
                break;
        }
    }

    public void readFromParcel(android.os.Parcel parcel) {
        int readInt = parcel.readInt();
        switch (readInt) {
            case 0:
                _set(readInt, (android.hardware.tv.tuner.DemuxFilterSectionBits) parcel.readTypedObject(android.hardware.tv.tuner.DemuxFilterSectionBits.CREATOR));
                return;
            case 1:
                _set(readInt, (android.hardware.tv.tuner.DemuxFilterSectionSettingsConditionTableInfo) parcel.readTypedObject(android.hardware.tv.tuner.DemuxFilterSectionSettingsConditionTableInfo.CREATOR));
                return;
            default:
                throw new java.lang.IllegalArgumentException("union: unknown tag: " + readInt);
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        switch (getTag()) {
            case 0:
                return 0 | describeContents(getSectionBits());
            case 1:
                return 0 | describeContents(getTableInfo());
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
                return "sectionBits";
            case 1:
                return "tableInfo";
            default:
                throw new java.lang.IllegalStateException("unknown field: " + i);
        }
    }

    private void _set(int i, java.lang.Object obj) {
        this._tag = i;
        this._value = obj;
    }
}
