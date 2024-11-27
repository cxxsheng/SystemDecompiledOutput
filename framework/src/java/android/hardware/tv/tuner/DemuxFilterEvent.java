package android.hardware.tv.tuner;

/* loaded from: classes2.dex */
public final class DemuxFilterEvent implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.hardware.tv.tuner.DemuxFilterEvent> CREATOR = new android.os.Parcelable.Creator<android.hardware.tv.tuner.DemuxFilterEvent>() { // from class: android.hardware.tv.tuner.DemuxFilterEvent.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.tv.tuner.DemuxFilterEvent createFromParcel(android.os.Parcel parcel) {
            return new android.hardware.tv.tuner.DemuxFilterEvent(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.tv.tuner.DemuxFilterEvent[] newArray(int i) {
            return new android.hardware.tv.tuner.DemuxFilterEvent[i];
        }
    };
    public static final int download = 5;
    public static final int ipPayload = 6;
    public static final int media = 1;
    public static final int mmtpRecord = 4;
    public static final int monitorEvent = 8;
    public static final int pes = 2;
    public static final int section = 0;
    public static final int startId = 9;
    public static final int temi = 7;
    public static final int tsRecord = 3;
    private int _tag;
    private java.lang.Object _value;

    public @interface Tag {
        public static final int download = 5;
        public static final int ipPayload = 6;
        public static final int media = 1;
        public static final int mmtpRecord = 4;
        public static final int monitorEvent = 8;
        public static final int pes = 2;
        public static final int section = 0;
        public static final int startId = 9;
        public static final int temi = 7;
        public static final int tsRecord = 3;
    }

    public DemuxFilterEvent() {
        this._tag = 0;
        this._value = null;
    }

    private DemuxFilterEvent(android.os.Parcel parcel) {
        readFromParcel(parcel);
    }

    private DemuxFilterEvent(int i, java.lang.Object obj) {
        this._tag = i;
        this._value = obj;
    }

    public int getTag() {
        return this._tag;
    }

    public static android.hardware.tv.tuner.DemuxFilterEvent section(android.hardware.tv.tuner.DemuxFilterSectionEvent demuxFilterSectionEvent) {
        return new android.hardware.tv.tuner.DemuxFilterEvent(0, demuxFilterSectionEvent);
    }

    public android.hardware.tv.tuner.DemuxFilterSectionEvent getSection() {
        _assertTag(0);
        return (android.hardware.tv.tuner.DemuxFilterSectionEvent) this._value;
    }

    public void setSection(android.hardware.tv.tuner.DemuxFilterSectionEvent demuxFilterSectionEvent) {
        _set(0, demuxFilterSectionEvent);
    }

    public static android.hardware.tv.tuner.DemuxFilterEvent media(android.hardware.tv.tuner.DemuxFilterMediaEvent demuxFilterMediaEvent) {
        return new android.hardware.tv.tuner.DemuxFilterEvent(1, demuxFilterMediaEvent);
    }

    public android.hardware.tv.tuner.DemuxFilterMediaEvent getMedia() {
        _assertTag(1);
        return (android.hardware.tv.tuner.DemuxFilterMediaEvent) this._value;
    }

    public void setMedia(android.hardware.tv.tuner.DemuxFilterMediaEvent demuxFilterMediaEvent) {
        _set(1, demuxFilterMediaEvent);
    }

    public static android.hardware.tv.tuner.DemuxFilterEvent pes(android.hardware.tv.tuner.DemuxFilterPesEvent demuxFilterPesEvent) {
        return new android.hardware.tv.tuner.DemuxFilterEvent(2, demuxFilterPesEvent);
    }

    public android.hardware.tv.tuner.DemuxFilterPesEvent getPes() {
        _assertTag(2);
        return (android.hardware.tv.tuner.DemuxFilterPesEvent) this._value;
    }

    public void setPes(android.hardware.tv.tuner.DemuxFilterPesEvent demuxFilterPesEvent) {
        _set(2, demuxFilterPesEvent);
    }

    public static android.hardware.tv.tuner.DemuxFilterEvent tsRecord(android.hardware.tv.tuner.DemuxFilterTsRecordEvent demuxFilterTsRecordEvent) {
        return new android.hardware.tv.tuner.DemuxFilterEvent(3, demuxFilterTsRecordEvent);
    }

    public android.hardware.tv.tuner.DemuxFilterTsRecordEvent getTsRecord() {
        _assertTag(3);
        return (android.hardware.tv.tuner.DemuxFilterTsRecordEvent) this._value;
    }

    public void setTsRecord(android.hardware.tv.tuner.DemuxFilterTsRecordEvent demuxFilterTsRecordEvent) {
        _set(3, demuxFilterTsRecordEvent);
    }

    public static android.hardware.tv.tuner.DemuxFilterEvent mmtpRecord(android.hardware.tv.tuner.DemuxFilterMmtpRecordEvent demuxFilterMmtpRecordEvent) {
        return new android.hardware.tv.tuner.DemuxFilterEvent(4, demuxFilterMmtpRecordEvent);
    }

    public android.hardware.tv.tuner.DemuxFilterMmtpRecordEvent getMmtpRecord() {
        _assertTag(4);
        return (android.hardware.tv.tuner.DemuxFilterMmtpRecordEvent) this._value;
    }

    public void setMmtpRecord(android.hardware.tv.tuner.DemuxFilterMmtpRecordEvent demuxFilterMmtpRecordEvent) {
        _set(4, demuxFilterMmtpRecordEvent);
    }

    public static android.hardware.tv.tuner.DemuxFilterEvent download(android.hardware.tv.tuner.DemuxFilterDownloadEvent demuxFilterDownloadEvent) {
        return new android.hardware.tv.tuner.DemuxFilterEvent(5, demuxFilterDownloadEvent);
    }

    public android.hardware.tv.tuner.DemuxFilterDownloadEvent getDownload() {
        _assertTag(5);
        return (android.hardware.tv.tuner.DemuxFilterDownloadEvent) this._value;
    }

    public void setDownload(android.hardware.tv.tuner.DemuxFilterDownloadEvent demuxFilterDownloadEvent) {
        _set(5, demuxFilterDownloadEvent);
    }

    public static android.hardware.tv.tuner.DemuxFilterEvent ipPayload(android.hardware.tv.tuner.DemuxFilterIpPayloadEvent demuxFilterIpPayloadEvent) {
        return new android.hardware.tv.tuner.DemuxFilterEvent(6, demuxFilterIpPayloadEvent);
    }

    public android.hardware.tv.tuner.DemuxFilterIpPayloadEvent getIpPayload() {
        _assertTag(6);
        return (android.hardware.tv.tuner.DemuxFilterIpPayloadEvent) this._value;
    }

    public void setIpPayload(android.hardware.tv.tuner.DemuxFilterIpPayloadEvent demuxFilterIpPayloadEvent) {
        _set(6, demuxFilterIpPayloadEvent);
    }

    public static android.hardware.tv.tuner.DemuxFilterEvent temi(android.hardware.tv.tuner.DemuxFilterTemiEvent demuxFilterTemiEvent) {
        return new android.hardware.tv.tuner.DemuxFilterEvent(7, demuxFilterTemiEvent);
    }

    public android.hardware.tv.tuner.DemuxFilterTemiEvent getTemi() {
        _assertTag(7);
        return (android.hardware.tv.tuner.DemuxFilterTemiEvent) this._value;
    }

    public void setTemi(android.hardware.tv.tuner.DemuxFilterTemiEvent demuxFilterTemiEvent) {
        _set(7, demuxFilterTemiEvent);
    }

    public static android.hardware.tv.tuner.DemuxFilterEvent monitorEvent(android.hardware.tv.tuner.DemuxFilterMonitorEvent demuxFilterMonitorEvent) {
        return new android.hardware.tv.tuner.DemuxFilterEvent(8, demuxFilterMonitorEvent);
    }

    public android.hardware.tv.tuner.DemuxFilterMonitorEvent getMonitorEvent() {
        _assertTag(8);
        return (android.hardware.tv.tuner.DemuxFilterMonitorEvent) this._value;
    }

    public void setMonitorEvent(android.hardware.tv.tuner.DemuxFilterMonitorEvent demuxFilterMonitorEvent) {
        _set(8, demuxFilterMonitorEvent);
    }

    public static android.hardware.tv.tuner.DemuxFilterEvent startId(int i) {
        return new android.hardware.tv.tuner.DemuxFilterEvent(9, java.lang.Integer.valueOf(i));
    }

    public int getStartId() {
        _assertTag(9);
        return ((java.lang.Integer) this._value).intValue();
    }

    public void setStartId(int i) {
        _set(9, java.lang.Integer.valueOf(i));
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
                parcel.writeTypedObject(getSection(), i);
                break;
            case 1:
                parcel.writeTypedObject(getMedia(), i);
                break;
            case 2:
                parcel.writeTypedObject(getPes(), i);
                break;
            case 3:
                parcel.writeTypedObject(getTsRecord(), i);
                break;
            case 4:
                parcel.writeTypedObject(getMmtpRecord(), i);
                break;
            case 5:
                parcel.writeTypedObject(getDownload(), i);
                break;
            case 6:
                parcel.writeTypedObject(getIpPayload(), i);
                break;
            case 7:
                parcel.writeTypedObject(getTemi(), i);
                break;
            case 8:
                parcel.writeTypedObject(getMonitorEvent(), i);
                break;
            case 9:
                parcel.writeInt(getStartId());
                break;
        }
    }

    public void readFromParcel(android.os.Parcel parcel) {
        int readInt = parcel.readInt();
        switch (readInt) {
            case 0:
                _set(readInt, (android.hardware.tv.tuner.DemuxFilterSectionEvent) parcel.readTypedObject(android.hardware.tv.tuner.DemuxFilterSectionEvent.CREATOR));
                return;
            case 1:
                _set(readInt, (android.hardware.tv.tuner.DemuxFilterMediaEvent) parcel.readTypedObject(android.hardware.tv.tuner.DemuxFilterMediaEvent.CREATOR));
                return;
            case 2:
                _set(readInt, (android.hardware.tv.tuner.DemuxFilterPesEvent) parcel.readTypedObject(android.hardware.tv.tuner.DemuxFilterPesEvent.CREATOR));
                return;
            case 3:
                _set(readInt, (android.hardware.tv.tuner.DemuxFilterTsRecordEvent) parcel.readTypedObject(android.hardware.tv.tuner.DemuxFilterTsRecordEvent.CREATOR));
                return;
            case 4:
                _set(readInt, (android.hardware.tv.tuner.DemuxFilterMmtpRecordEvent) parcel.readTypedObject(android.hardware.tv.tuner.DemuxFilterMmtpRecordEvent.CREATOR));
                return;
            case 5:
                _set(readInt, (android.hardware.tv.tuner.DemuxFilterDownloadEvent) parcel.readTypedObject(android.hardware.tv.tuner.DemuxFilterDownloadEvent.CREATOR));
                return;
            case 6:
                _set(readInt, (android.hardware.tv.tuner.DemuxFilterIpPayloadEvent) parcel.readTypedObject(android.hardware.tv.tuner.DemuxFilterIpPayloadEvent.CREATOR));
                return;
            case 7:
                _set(readInt, (android.hardware.tv.tuner.DemuxFilterTemiEvent) parcel.readTypedObject(android.hardware.tv.tuner.DemuxFilterTemiEvent.CREATOR));
                return;
            case 8:
                _set(readInt, (android.hardware.tv.tuner.DemuxFilterMonitorEvent) parcel.readTypedObject(android.hardware.tv.tuner.DemuxFilterMonitorEvent.CREATOR));
                return;
            case 9:
                _set(readInt, java.lang.Integer.valueOf(parcel.readInt()));
                return;
            default:
                throw new java.lang.IllegalArgumentException("union: unknown tag: " + readInt);
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        switch (getTag()) {
            case 0:
                return 0 | describeContents(getSection());
            case 1:
                return 0 | describeContents(getMedia());
            case 2:
                return 0 | describeContents(getPes());
            case 3:
                return 0 | describeContents(getTsRecord());
            case 4:
                return 0 | describeContents(getMmtpRecord());
            case 5:
                return 0 | describeContents(getDownload());
            case 6:
                return 0 | describeContents(getIpPayload());
            case 7:
                return 0 | describeContents(getTemi());
            case 8:
                return 0 | describeContents(getMonitorEvent());
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
                return "section";
            case 1:
                return "media";
            case 2:
                return "pes";
            case 3:
                return "tsRecord";
            case 4:
                return "mmtpRecord";
            case 5:
                return "download";
            case 6:
                return "ipPayload";
            case 7:
                return "temi";
            case 8:
                return "monitorEvent";
            case 9:
                return "startId";
            default:
                throw new java.lang.IllegalStateException("unknown field: " + i);
        }
    }

    private void _set(int i, java.lang.Object obj) {
        this._tag = i;
        this._value = obj;
    }
}
