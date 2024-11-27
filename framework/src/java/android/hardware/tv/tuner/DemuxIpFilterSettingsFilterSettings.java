package android.hardware.tv.tuner;

/* loaded from: classes2.dex */
public final class DemuxIpFilterSettingsFilterSettings implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.hardware.tv.tuner.DemuxIpFilterSettingsFilterSettings> CREATOR = new android.os.Parcelable.Creator<android.hardware.tv.tuner.DemuxIpFilterSettingsFilterSettings>() { // from class: android.hardware.tv.tuner.DemuxIpFilterSettingsFilterSettings.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.tv.tuner.DemuxIpFilterSettingsFilterSettings createFromParcel(android.os.Parcel parcel) {
            return new android.hardware.tv.tuner.DemuxIpFilterSettingsFilterSettings(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.tv.tuner.DemuxIpFilterSettingsFilterSettings[] newArray(int i) {
            return new android.hardware.tv.tuner.DemuxIpFilterSettingsFilterSettings[i];
        }
    };
    public static final int bPassthrough = 2;
    public static final int noinit = 0;
    public static final int section = 1;
    private int _tag;
    private java.lang.Object _value;

    public @interface Tag {
        public static final int bPassthrough = 2;
        public static final int noinit = 0;
        public static final int section = 1;
    }

    public DemuxIpFilterSettingsFilterSettings() {
        this._tag = 0;
        this._value = false;
    }

    private DemuxIpFilterSettingsFilterSettings(android.os.Parcel parcel) {
        readFromParcel(parcel);
    }

    private DemuxIpFilterSettingsFilterSettings(int i, java.lang.Object obj) {
        this._tag = i;
        this._value = obj;
    }

    public int getTag() {
        return this._tag;
    }

    public static android.hardware.tv.tuner.DemuxIpFilterSettingsFilterSettings noinit(boolean z) {
        return new android.hardware.tv.tuner.DemuxIpFilterSettingsFilterSettings(0, java.lang.Boolean.valueOf(z));
    }

    public boolean getNoinit() {
        _assertTag(0);
        return ((java.lang.Boolean) this._value).booleanValue();
    }

    public void setNoinit(boolean z) {
        _set(0, java.lang.Boolean.valueOf(z));
    }

    public static android.hardware.tv.tuner.DemuxIpFilterSettingsFilterSettings section(android.hardware.tv.tuner.DemuxFilterSectionSettings demuxFilterSectionSettings) {
        return new android.hardware.tv.tuner.DemuxIpFilterSettingsFilterSettings(1, demuxFilterSectionSettings);
    }

    public android.hardware.tv.tuner.DemuxFilterSectionSettings getSection() {
        _assertTag(1);
        return (android.hardware.tv.tuner.DemuxFilterSectionSettings) this._value;
    }

    public void setSection(android.hardware.tv.tuner.DemuxFilterSectionSettings demuxFilterSectionSettings) {
        _set(1, demuxFilterSectionSettings);
    }

    public static android.hardware.tv.tuner.DemuxIpFilterSettingsFilterSettings bPassthrough(boolean z) {
        return new android.hardware.tv.tuner.DemuxIpFilterSettingsFilterSettings(2, java.lang.Boolean.valueOf(z));
    }

    public boolean getBPassthrough() {
        _assertTag(2);
        return ((java.lang.Boolean) this._value).booleanValue();
    }

    public void setBPassthrough(boolean z) {
        _set(2, java.lang.Boolean.valueOf(z));
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
                parcel.writeBoolean(getNoinit());
                break;
            case 1:
                parcel.writeTypedObject(getSection(), i);
                break;
            case 2:
                parcel.writeBoolean(getBPassthrough());
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
                _set(readInt, (android.hardware.tv.tuner.DemuxFilterSectionSettings) parcel.readTypedObject(android.hardware.tv.tuner.DemuxFilterSectionSettings.CREATOR));
                return;
            case 2:
                _set(readInt, java.lang.Boolean.valueOf(parcel.readBoolean()));
                return;
            default:
                throw new java.lang.IllegalArgumentException("union: unknown tag: " + readInt);
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        switch (getTag()) {
            case 1:
                return 0 | describeContents(getSection());
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
                return "noinit";
            case 1:
                return "section";
            case 2:
                return "bPassthrough";
            default:
                throw new java.lang.IllegalStateException("unknown field: " + i);
        }
    }

    private void _set(int i, java.lang.Object obj) {
        this._tag = i;
        this._value = obj;
    }
}
