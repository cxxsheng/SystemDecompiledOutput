package android.hardware.tv.tuner;

/* loaded from: classes2.dex */
public final class DemuxMmtpFilterSettingsFilterSettings implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.hardware.tv.tuner.DemuxMmtpFilterSettingsFilterSettings> CREATOR = new android.os.Parcelable.Creator<android.hardware.tv.tuner.DemuxMmtpFilterSettingsFilterSettings>() { // from class: android.hardware.tv.tuner.DemuxMmtpFilterSettingsFilterSettings.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.tv.tuner.DemuxMmtpFilterSettingsFilterSettings createFromParcel(android.os.Parcel parcel) {
            return new android.hardware.tv.tuner.DemuxMmtpFilterSettingsFilterSettings(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.tv.tuner.DemuxMmtpFilterSettingsFilterSettings[] newArray(int i) {
            return new android.hardware.tv.tuner.DemuxMmtpFilterSettingsFilterSettings[i];
        }
    };
    public static final int av = 2;
    public static final int download = 5;
    public static final int noinit = 0;
    public static final int pesData = 3;
    public static final int record = 4;
    public static final int section = 1;
    private int _tag;
    private java.lang.Object _value;

    public @interface Tag {
        public static final int av = 2;
        public static final int download = 5;
        public static final int noinit = 0;
        public static final int pesData = 3;
        public static final int record = 4;
        public static final int section = 1;
    }

    public DemuxMmtpFilterSettingsFilterSettings() {
        this._tag = 0;
        this._value = false;
    }

    private DemuxMmtpFilterSettingsFilterSettings(android.os.Parcel parcel) {
        readFromParcel(parcel);
    }

    private DemuxMmtpFilterSettingsFilterSettings(int i, java.lang.Object obj) {
        this._tag = i;
        this._value = obj;
    }

    public int getTag() {
        return this._tag;
    }

    public static android.hardware.tv.tuner.DemuxMmtpFilterSettingsFilterSettings noinit(boolean z) {
        return new android.hardware.tv.tuner.DemuxMmtpFilterSettingsFilterSettings(0, java.lang.Boolean.valueOf(z));
    }

    public boolean getNoinit() {
        _assertTag(0);
        return ((java.lang.Boolean) this._value).booleanValue();
    }

    public void setNoinit(boolean z) {
        _set(0, java.lang.Boolean.valueOf(z));
    }

    public static android.hardware.tv.tuner.DemuxMmtpFilterSettingsFilterSettings section(android.hardware.tv.tuner.DemuxFilterSectionSettings demuxFilterSectionSettings) {
        return new android.hardware.tv.tuner.DemuxMmtpFilterSettingsFilterSettings(1, demuxFilterSectionSettings);
    }

    public android.hardware.tv.tuner.DemuxFilterSectionSettings getSection() {
        _assertTag(1);
        return (android.hardware.tv.tuner.DemuxFilterSectionSettings) this._value;
    }

    public void setSection(android.hardware.tv.tuner.DemuxFilterSectionSettings demuxFilterSectionSettings) {
        _set(1, demuxFilterSectionSettings);
    }

    public static android.hardware.tv.tuner.DemuxMmtpFilterSettingsFilterSettings av(android.hardware.tv.tuner.DemuxFilterAvSettings demuxFilterAvSettings) {
        return new android.hardware.tv.tuner.DemuxMmtpFilterSettingsFilterSettings(2, demuxFilterAvSettings);
    }

    public android.hardware.tv.tuner.DemuxFilterAvSettings getAv() {
        _assertTag(2);
        return (android.hardware.tv.tuner.DemuxFilterAvSettings) this._value;
    }

    public void setAv(android.hardware.tv.tuner.DemuxFilterAvSettings demuxFilterAvSettings) {
        _set(2, demuxFilterAvSettings);
    }

    public static android.hardware.tv.tuner.DemuxMmtpFilterSettingsFilterSettings pesData(android.hardware.tv.tuner.DemuxFilterPesDataSettings demuxFilterPesDataSettings) {
        return new android.hardware.tv.tuner.DemuxMmtpFilterSettingsFilterSettings(3, demuxFilterPesDataSettings);
    }

    public android.hardware.tv.tuner.DemuxFilterPesDataSettings getPesData() {
        _assertTag(3);
        return (android.hardware.tv.tuner.DemuxFilterPesDataSettings) this._value;
    }

    public void setPesData(android.hardware.tv.tuner.DemuxFilterPesDataSettings demuxFilterPesDataSettings) {
        _set(3, demuxFilterPesDataSettings);
    }

    public static android.hardware.tv.tuner.DemuxMmtpFilterSettingsFilterSettings record(android.hardware.tv.tuner.DemuxFilterRecordSettings demuxFilterRecordSettings) {
        return new android.hardware.tv.tuner.DemuxMmtpFilterSettingsFilterSettings(4, demuxFilterRecordSettings);
    }

    public android.hardware.tv.tuner.DemuxFilterRecordSettings getRecord() {
        _assertTag(4);
        return (android.hardware.tv.tuner.DemuxFilterRecordSettings) this._value;
    }

    public void setRecord(android.hardware.tv.tuner.DemuxFilterRecordSettings demuxFilterRecordSettings) {
        _set(4, demuxFilterRecordSettings);
    }

    public static android.hardware.tv.tuner.DemuxMmtpFilterSettingsFilterSettings download(android.hardware.tv.tuner.DemuxFilterDownloadSettings demuxFilterDownloadSettings) {
        return new android.hardware.tv.tuner.DemuxMmtpFilterSettingsFilterSettings(5, demuxFilterDownloadSettings);
    }

    public android.hardware.tv.tuner.DemuxFilterDownloadSettings getDownload() {
        _assertTag(5);
        return (android.hardware.tv.tuner.DemuxFilterDownloadSettings) this._value;
    }

    public void setDownload(android.hardware.tv.tuner.DemuxFilterDownloadSettings demuxFilterDownloadSettings) {
        _set(5, demuxFilterDownloadSettings);
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
                parcel.writeTypedObject(getAv(), i);
                break;
            case 3:
                parcel.writeTypedObject(getPesData(), i);
                break;
            case 4:
                parcel.writeTypedObject(getRecord(), i);
                break;
            case 5:
                parcel.writeTypedObject(getDownload(), i);
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
                _set(readInt, (android.hardware.tv.tuner.DemuxFilterAvSettings) parcel.readTypedObject(android.hardware.tv.tuner.DemuxFilterAvSettings.CREATOR));
                return;
            case 3:
                _set(readInt, (android.hardware.tv.tuner.DemuxFilterPesDataSettings) parcel.readTypedObject(android.hardware.tv.tuner.DemuxFilterPesDataSettings.CREATOR));
                return;
            case 4:
                _set(readInt, (android.hardware.tv.tuner.DemuxFilterRecordSettings) parcel.readTypedObject(android.hardware.tv.tuner.DemuxFilterRecordSettings.CREATOR));
                return;
            case 5:
                _set(readInt, (android.hardware.tv.tuner.DemuxFilterDownloadSettings) parcel.readTypedObject(android.hardware.tv.tuner.DemuxFilterDownloadSettings.CREATOR));
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
            case 2:
                return 0 | describeContents(getAv());
            case 3:
                return 0 | describeContents(getPesData());
            case 4:
                return 0 | describeContents(getRecord());
            case 5:
                return 0 | describeContents(getDownload());
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
                return "av";
            case 3:
                return "pesData";
            case 4:
                return "record";
            case 5:
                return "download";
            default:
                throw new java.lang.IllegalStateException("unknown field: " + i);
        }
    }

    private void _set(int i, java.lang.Object obj) {
        this._tag = i;
        this._value = obj;
    }
}
