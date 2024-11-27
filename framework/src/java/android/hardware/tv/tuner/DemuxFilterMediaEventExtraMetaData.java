package android.hardware.tv.tuner;

/* loaded from: classes2.dex */
public final class DemuxFilterMediaEventExtraMetaData implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.hardware.tv.tuner.DemuxFilterMediaEventExtraMetaData> CREATOR = new android.os.Parcelable.Creator<android.hardware.tv.tuner.DemuxFilterMediaEventExtraMetaData>() { // from class: android.hardware.tv.tuner.DemuxFilterMediaEventExtraMetaData.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.tv.tuner.DemuxFilterMediaEventExtraMetaData createFromParcel(android.os.Parcel parcel) {
            return new android.hardware.tv.tuner.DemuxFilterMediaEventExtraMetaData(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.tv.tuner.DemuxFilterMediaEventExtraMetaData[] newArray(int i) {
            return new android.hardware.tv.tuner.DemuxFilterMediaEventExtraMetaData[i];
        }
    };
    public static final int audio = 1;
    public static final int audioPresentations = 2;
    public static final int noinit = 0;
    private int _tag;
    private java.lang.Object _value;

    public @interface Tag {
        public static final int audio = 1;
        public static final int audioPresentations = 2;
        public static final int noinit = 0;
    }

    public DemuxFilterMediaEventExtraMetaData() {
        this._tag = 0;
        this._value = false;
    }

    private DemuxFilterMediaEventExtraMetaData(android.os.Parcel parcel) {
        readFromParcel(parcel);
    }

    private DemuxFilterMediaEventExtraMetaData(int i, java.lang.Object obj) {
        this._tag = i;
        this._value = obj;
    }

    public int getTag() {
        return this._tag;
    }

    public static android.hardware.tv.tuner.DemuxFilterMediaEventExtraMetaData noinit(boolean z) {
        return new android.hardware.tv.tuner.DemuxFilterMediaEventExtraMetaData(0, java.lang.Boolean.valueOf(z));
    }

    public boolean getNoinit() {
        _assertTag(0);
        return ((java.lang.Boolean) this._value).booleanValue();
    }

    public void setNoinit(boolean z) {
        _set(0, java.lang.Boolean.valueOf(z));
    }

    public static android.hardware.tv.tuner.DemuxFilterMediaEventExtraMetaData audio(android.hardware.tv.tuner.AudioExtraMetaData audioExtraMetaData) {
        return new android.hardware.tv.tuner.DemuxFilterMediaEventExtraMetaData(1, audioExtraMetaData);
    }

    public android.hardware.tv.tuner.AudioExtraMetaData getAudio() {
        _assertTag(1);
        return (android.hardware.tv.tuner.AudioExtraMetaData) this._value;
    }

    public void setAudio(android.hardware.tv.tuner.AudioExtraMetaData audioExtraMetaData) {
        _set(1, audioExtraMetaData);
    }

    public static android.hardware.tv.tuner.DemuxFilterMediaEventExtraMetaData audioPresentations(android.hardware.tv.tuner.AudioPresentation[] audioPresentationArr) {
        return new android.hardware.tv.tuner.DemuxFilterMediaEventExtraMetaData(2, audioPresentationArr);
    }

    public android.hardware.tv.tuner.AudioPresentation[] getAudioPresentations() {
        _assertTag(2);
        return (android.hardware.tv.tuner.AudioPresentation[]) this._value;
    }

    public void setAudioPresentations(android.hardware.tv.tuner.AudioPresentation[] audioPresentationArr) {
        _set(2, audioPresentationArr);
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
                parcel.writeTypedObject(getAudio(), i);
                break;
            case 2:
                parcel.writeTypedArray(getAudioPresentations(), i);
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
                _set(readInt, (android.hardware.tv.tuner.AudioExtraMetaData) parcel.readTypedObject(android.hardware.tv.tuner.AudioExtraMetaData.CREATOR));
                return;
            case 2:
                _set(readInt, (android.hardware.tv.tuner.AudioPresentation[]) parcel.createTypedArray(android.hardware.tv.tuner.AudioPresentation.CREATOR));
                return;
            default:
                throw new java.lang.IllegalArgumentException("union: unknown tag: " + readInt);
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        switch (getTag()) {
            case 1:
                return 0 | describeContents(getAudio());
            case 2:
                return 0 | describeContents(getAudioPresentations());
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
                return "noinit";
            case 1:
                return "audio";
            case 2:
                return "audioPresentations";
            default:
                throw new java.lang.IllegalStateException("unknown field: " + i);
        }
    }

    private void _set(int i, java.lang.Object obj) {
        this._tag = i;
        this._value = obj;
    }
}
