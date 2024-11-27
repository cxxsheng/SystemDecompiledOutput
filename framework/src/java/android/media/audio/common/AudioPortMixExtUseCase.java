package android.media.audio.common;

/* loaded from: classes2.dex */
public final class AudioPortMixExtUseCase implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.media.audio.common.AudioPortMixExtUseCase> CREATOR = new android.os.Parcelable.Creator<android.media.audio.common.AudioPortMixExtUseCase>() { // from class: android.media.audio.common.AudioPortMixExtUseCase.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.audio.common.AudioPortMixExtUseCase createFromParcel(android.os.Parcel parcel) {
            return new android.media.audio.common.AudioPortMixExtUseCase(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.audio.common.AudioPortMixExtUseCase[] newArray(int i) {
            return new android.media.audio.common.AudioPortMixExtUseCase[i];
        }
    };
    public static final int source = 2;
    public static final int stream = 1;
    public static final int unspecified = 0;
    private int _tag;
    private java.lang.Object _value;

    public @interface Tag {
        public static final int source = 2;
        public static final int stream = 1;
        public static final int unspecified = 0;
    }

    public AudioPortMixExtUseCase() {
        this._tag = 0;
        this._value = false;
    }

    private AudioPortMixExtUseCase(android.os.Parcel parcel) {
        readFromParcel(parcel);
    }

    private AudioPortMixExtUseCase(int i, java.lang.Object obj) {
        this._tag = i;
        this._value = obj;
    }

    public int getTag() {
        return this._tag;
    }

    public static android.media.audio.common.AudioPortMixExtUseCase unspecified(boolean z) {
        return new android.media.audio.common.AudioPortMixExtUseCase(0, java.lang.Boolean.valueOf(z));
    }

    public boolean getUnspecified() {
        _assertTag(0);
        return ((java.lang.Boolean) this._value).booleanValue();
    }

    public void setUnspecified(boolean z) {
        _set(0, java.lang.Boolean.valueOf(z));
    }

    public static android.media.audio.common.AudioPortMixExtUseCase stream(int i) {
        return new android.media.audio.common.AudioPortMixExtUseCase(1, java.lang.Integer.valueOf(i));
    }

    public int getStream() {
        _assertTag(1);
        return ((java.lang.Integer) this._value).intValue();
    }

    public void setStream(int i) {
        _set(1, java.lang.Integer.valueOf(i));
    }

    public static android.media.audio.common.AudioPortMixExtUseCase source(int i) {
        return new android.media.audio.common.AudioPortMixExtUseCase(2, java.lang.Integer.valueOf(i));
    }

    public int getSource() {
        _assertTag(2);
        return ((java.lang.Integer) this._value).intValue();
    }

    public void setSource(int i) {
        _set(2, java.lang.Integer.valueOf(i));
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
                parcel.writeBoolean(getUnspecified());
                break;
            case 1:
                parcel.writeInt(getStream());
                break;
            case 2:
                parcel.writeInt(getSource());
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
                _set(readInt, java.lang.Integer.valueOf(parcel.readInt()));
                return;
            case 2:
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

    public java.lang.String toString() {
        switch (this._tag) {
            case 0:
                return "AudioPortMixExtUseCase.unspecified(" + getUnspecified() + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END;
            case 1:
                return "AudioPortMixExtUseCase.stream(" + getStream() + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END;
            case 2:
                return "AudioPortMixExtUseCase.source(" + getSource() + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END;
            default:
                throw new java.lang.IllegalStateException("unknown field: " + this._tag);
        }
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof android.media.audio.common.AudioPortMixExtUseCase)) {
            return false;
        }
        android.media.audio.common.AudioPortMixExtUseCase audioPortMixExtUseCase = (android.media.audio.common.AudioPortMixExtUseCase) obj;
        if (this._tag == audioPortMixExtUseCase._tag && java.util.Objects.deepEquals(this._value, audioPortMixExtUseCase._value)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return java.util.Arrays.deepHashCode(java.util.Arrays.asList(java.lang.Integer.valueOf(this._tag), this._value).toArray());
    }

    private void _assertTag(int i) {
        if (getTag() != i) {
            throw new java.lang.IllegalStateException("bad access: " + _tagString(i) + ", " + _tagString(getTag()) + " is available.");
        }
    }

    private java.lang.String _tagString(int i) {
        switch (i) {
            case 0:
                return "unspecified";
            case 1:
                return "stream";
            case 2:
                return android.app.slice.Slice.SUBTYPE_SOURCE;
            default:
                throw new java.lang.IllegalStateException("unknown field: " + i);
        }
    }

    private void _set(int i, java.lang.Object obj) {
        this._tag = i;
        this._value = obj;
    }
}
