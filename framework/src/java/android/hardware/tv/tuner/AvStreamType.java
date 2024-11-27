package android.hardware.tv.tuner;

/* loaded from: classes2.dex */
public final class AvStreamType implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.hardware.tv.tuner.AvStreamType> CREATOR = new android.os.Parcelable.Creator<android.hardware.tv.tuner.AvStreamType>() { // from class: android.hardware.tv.tuner.AvStreamType.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.tv.tuner.AvStreamType createFromParcel(android.os.Parcel parcel) {
            return new android.hardware.tv.tuner.AvStreamType(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.tv.tuner.AvStreamType[] newArray(int i) {
            return new android.hardware.tv.tuner.AvStreamType[i];
        }
    };
    public static final int audio = 1;
    public static final int video = 0;
    private int _tag;
    private java.lang.Object _value;

    public @interface Tag {
        public static final int audio = 1;
        public static final int video = 0;
    }

    public AvStreamType() {
        this._tag = 0;
        this._value = 0;
    }

    private AvStreamType(android.os.Parcel parcel) {
        readFromParcel(parcel);
    }

    private AvStreamType(int i, java.lang.Object obj) {
        this._tag = i;
        this._value = obj;
    }

    public int getTag() {
        return this._tag;
    }

    public static android.hardware.tv.tuner.AvStreamType video(int i) {
        return new android.hardware.tv.tuner.AvStreamType(0, java.lang.Integer.valueOf(i));
    }

    public int getVideo() {
        _assertTag(0);
        return ((java.lang.Integer) this._value).intValue();
    }

    public void setVideo(int i) {
        _set(0, java.lang.Integer.valueOf(i));
    }

    public static android.hardware.tv.tuner.AvStreamType audio(int i) {
        return new android.hardware.tv.tuner.AvStreamType(1, java.lang.Integer.valueOf(i));
    }

    public int getAudio() {
        _assertTag(1);
        return ((java.lang.Integer) this._value).intValue();
    }

    public void setAudio(int i) {
        _set(1, java.lang.Integer.valueOf(i));
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
                parcel.writeInt(getVideo());
                break;
            case 1:
                parcel.writeInt(getAudio());
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
                return "video";
            case 1:
                return "audio";
            default:
                throw new java.lang.IllegalStateException("unknown field: " + i);
        }
    }

    private void _set(int i, java.lang.Object obj) {
        this._tag = i;
        this._value = obj;
    }
}
