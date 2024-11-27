package android.hardware.tv.tuner;

/* loaded from: classes2.dex */
public final class DemuxAlpFilterSettingsFilterSettings implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.hardware.tv.tuner.DemuxAlpFilterSettingsFilterSettings> CREATOR = new android.os.Parcelable.Creator<android.hardware.tv.tuner.DemuxAlpFilterSettingsFilterSettings>() { // from class: android.hardware.tv.tuner.DemuxAlpFilterSettingsFilterSettings.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.tv.tuner.DemuxAlpFilterSettingsFilterSettings createFromParcel(android.os.Parcel parcel) {
            return new android.hardware.tv.tuner.DemuxAlpFilterSettingsFilterSettings(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.tv.tuner.DemuxAlpFilterSettingsFilterSettings[] newArray(int i) {
            return new android.hardware.tv.tuner.DemuxAlpFilterSettingsFilterSettings[i];
        }
    };
    public static final int noinit = 0;
    public static final int section = 1;
    private int _tag;
    private java.lang.Object _value;

    public @interface Tag {
        public static final int noinit = 0;
        public static final int section = 1;
    }

    public DemuxAlpFilterSettingsFilterSettings() {
        this._tag = 0;
        this._value = false;
    }

    private DemuxAlpFilterSettingsFilterSettings(android.os.Parcel parcel) {
        readFromParcel(parcel);
    }

    private DemuxAlpFilterSettingsFilterSettings(int i, java.lang.Object obj) {
        this._tag = i;
        this._value = obj;
    }

    public int getTag() {
        return this._tag;
    }

    public static android.hardware.tv.tuner.DemuxAlpFilterSettingsFilterSettings noinit(boolean z) {
        return new android.hardware.tv.tuner.DemuxAlpFilterSettingsFilterSettings(0, java.lang.Boolean.valueOf(z));
    }

    public boolean getNoinit() {
        _assertTag(0);
        return ((java.lang.Boolean) this._value).booleanValue();
    }

    public void setNoinit(boolean z) {
        _set(0, java.lang.Boolean.valueOf(z));
    }

    public static android.hardware.tv.tuner.DemuxAlpFilterSettingsFilterSettings section(android.hardware.tv.tuner.DemuxFilterSectionSettings demuxFilterSectionSettings) {
        return new android.hardware.tv.tuner.DemuxAlpFilterSettingsFilterSettings(1, demuxFilterSectionSettings);
    }

    public android.hardware.tv.tuner.DemuxFilterSectionSettings getSection() {
        _assertTag(1);
        return (android.hardware.tv.tuner.DemuxFilterSectionSettings) this._value;
    }

    public void setSection(android.hardware.tv.tuner.DemuxFilterSectionSettings demuxFilterSectionSettings) {
        _set(1, demuxFilterSectionSettings);
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
            default:
                throw new java.lang.IllegalStateException("unknown field: " + i);
        }
    }

    private void _set(int i, java.lang.Object obj) {
        this._tag = i;
        this._value = obj;
    }
}
