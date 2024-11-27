package android.hardware.tv.tuner;

/* loaded from: classes2.dex */
public final class DvrSettings implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.hardware.tv.tuner.DvrSettings> CREATOR = new android.os.Parcelable.Creator<android.hardware.tv.tuner.DvrSettings>() { // from class: android.hardware.tv.tuner.DvrSettings.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.tv.tuner.DvrSettings createFromParcel(android.os.Parcel parcel) {
            return new android.hardware.tv.tuner.DvrSettings(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.tv.tuner.DvrSettings[] newArray(int i) {
            return new android.hardware.tv.tuner.DvrSettings[i];
        }
    };
    public static final int playback = 1;
    public static final int record = 0;
    private int _tag;
    private java.lang.Object _value;

    public @interface Tag {
        public static final int playback = 1;
        public static final int record = 0;
    }

    public DvrSettings() {
        this._tag = 0;
        this._value = null;
    }

    private DvrSettings(android.os.Parcel parcel) {
        readFromParcel(parcel);
    }

    private DvrSettings(int i, java.lang.Object obj) {
        this._tag = i;
        this._value = obj;
    }

    public int getTag() {
        return this._tag;
    }

    public static android.hardware.tv.tuner.DvrSettings record(android.hardware.tv.tuner.RecordSettings recordSettings) {
        return new android.hardware.tv.tuner.DvrSettings(0, recordSettings);
    }

    public android.hardware.tv.tuner.RecordSettings getRecord() {
        _assertTag(0);
        return (android.hardware.tv.tuner.RecordSettings) this._value;
    }

    public void setRecord(android.hardware.tv.tuner.RecordSettings recordSettings) {
        _set(0, recordSettings);
    }

    public static android.hardware.tv.tuner.DvrSettings playback(android.hardware.tv.tuner.PlaybackSettings playbackSettings) {
        return new android.hardware.tv.tuner.DvrSettings(1, playbackSettings);
    }

    public android.hardware.tv.tuner.PlaybackSettings getPlayback() {
        _assertTag(1);
        return (android.hardware.tv.tuner.PlaybackSettings) this._value;
    }

    public void setPlayback(android.hardware.tv.tuner.PlaybackSettings playbackSettings) {
        _set(1, playbackSettings);
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
                parcel.writeTypedObject(getRecord(), i);
                break;
            case 1:
                parcel.writeTypedObject(getPlayback(), i);
                break;
        }
    }

    public void readFromParcel(android.os.Parcel parcel) {
        int readInt = parcel.readInt();
        switch (readInt) {
            case 0:
                _set(readInt, (android.hardware.tv.tuner.RecordSettings) parcel.readTypedObject(android.hardware.tv.tuner.RecordSettings.CREATOR));
                return;
            case 1:
                _set(readInt, (android.hardware.tv.tuner.PlaybackSettings) parcel.readTypedObject(android.hardware.tv.tuner.PlaybackSettings.CREATOR));
                return;
            default:
                throw new java.lang.IllegalArgumentException("union: unknown tag: " + readInt);
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        switch (getTag()) {
            case 0:
                return 0 | describeContents(getRecord());
            case 1:
                return 0 | describeContents(getPlayback());
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
                return "record";
            case 1:
                return "playback";
            default:
                throw new java.lang.IllegalStateException("unknown field: " + i);
        }
    }

    private void _set(int i, java.lang.Object obj) {
        this._tag = i;
        this._value = obj;
    }
}
