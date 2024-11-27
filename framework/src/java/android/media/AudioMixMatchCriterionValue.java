package android.media;

/* loaded from: classes2.dex */
public final class AudioMixMatchCriterionValue implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.media.AudioMixMatchCriterionValue> CREATOR = new android.os.Parcelable.Creator<android.media.AudioMixMatchCriterionValue>() { // from class: android.media.AudioMixMatchCriterionValue.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.AudioMixMatchCriterionValue createFromParcel(android.os.Parcel parcel) {
            return new android.media.AudioMixMatchCriterionValue(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.AudioMixMatchCriterionValue[] newArray(int i) {
            return new android.media.AudioMixMatchCriterionValue[i];
        }
    };
    public static final int audioSessionId = 4;
    public static final int source = 1;
    public static final int uid = 2;
    public static final int usage = 0;
    public static final int userId = 3;
    private int _tag;
    private java.lang.Object _value;

    public @interface Tag {
        public static final int audioSessionId = 4;
        public static final int source = 1;
        public static final int uid = 2;
        public static final int usage = 0;
        public static final int userId = 3;
    }

    public AudioMixMatchCriterionValue() {
        this._tag = 0;
        this._value = 0;
    }

    private AudioMixMatchCriterionValue(android.os.Parcel parcel) {
        readFromParcel(parcel);
    }

    private AudioMixMatchCriterionValue(int i, java.lang.Object obj) {
        this._tag = i;
        this._value = obj;
    }

    public int getTag() {
        return this._tag;
    }

    public static android.media.AudioMixMatchCriterionValue usage(int i) {
        return new android.media.AudioMixMatchCriterionValue(0, java.lang.Integer.valueOf(i));
    }

    public int getUsage() {
        _assertTag(0);
        return ((java.lang.Integer) this._value).intValue();
    }

    public void setUsage(int i) {
        _set(0, java.lang.Integer.valueOf(i));
    }

    public static android.media.AudioMixMatchCriterionValue source(int i) {
        return new android.media.AudioMixMatchCriterionValue(1, java.lang.Integer.valueOf(i));
    }

    public int getSource() {
        _assertTag(1);
        return ((java.lang.Integer) this._value).intValue();
    }

    public void setSource(int i) {
        _set(1, java.lang.Integer.valueOf(i));
    }

    public static android.media.AudioMixMatchCriterionValue uid(int i) {
        return new android.media.AudioMixMatchCriterionValue(2, java.lang.Integer.valueOf(i));
    }

    public int getUid() {
        _assertTag(2);
        return ((java.lang.Integer) this._value).intValue();
    }

    public void setUid(int i) {
        _set(2, java.lang.Integer.valueOf(i));
    }

    public static android.media.AudioMixMatchCriterionValue userId(int i) {
        return new android.media.AudioMixMatchCriterionValue(3, java.lang.Integer.valueOf(i));
    }

    public int getUserId() {
        _assertTag(3);
        return ((java.lang.Integer) this._value).intValue();
    }

    public void setUserId(int i) {
        _set(3, java.lang.Integer.valueOf(i));
    }

    public static android.media.AudioMixMatchCriterionValue audioSessionId(int i) {
        return new android.media.AudioMixMatchCriterionValue(4, java.lang.Integer.valueOf(i));
    }

    public int getAudioSessionId() {
        _assertTag(4);
        return ((java.lang.Integer) this._value).intValue();
    }

    public void setAudioSessionId(int i) {
        _set(4, java.lang.Integer.valueOf(i));
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this._tag);
        switch (this._tag) {
            case 0:
                parcel.writeInt(getUsage());
                break;
            case 1:
                parcel.writeInt(getSource());
                break;
            case 2:
                parcel.writeInt(getUid());
                break;
            case 3:
                parcel.writeInt(getUserId());
                break;
            case 4:
                parcel.writeInt(getAudioSessionId());
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
            case 4:
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
                return "usage";
            case 1:
                return android.app.slice.Slice.SUBTYPE_SOURCE;
            case 2:
                return "uid";
            case 3:
                return "userId";
            case 4:
                return "audioSessionId";
            default:
                throw new java.lang.IllegalStateException("unknown field: " + i);
        }
    }

    private void _set(int i, java.lang.Object obj) {
        this._tag = i;
        this._value = obj;
    }
}
