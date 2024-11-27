package android.media.audio.common;

/* loaded from: classes2.dex */
public final class AudioIoFlags implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.media.audio.common.AudioIoFlags> CREATOR = new android.os.Parcelable.Creator<android.media.audio.common.AudioIoFlags>() { // from class: android.media.audio.common.AudioIoFlags.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.audio.common.AudioIoFlags createFromParcel(android.os.Parcel parcel) {
            return new android.media.audio.common.AudioIoFlags(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.audio.common.AudioIoFlags[] newArray(int i) {
            return new android.media.audio.common.AudioIoFlags[i];
        }
    };
    public static final int input = 0;
    public static final int output = 1;
    private int _tag;
    private java.lang.Object _value;

    public @interface Tag {
        public static final int input = 0;
        public static final int output = 1;
    }

    public AudioIoFlags() {
        this._tag = 0;
        this._value = 0;
    }

    private AudioIoFlags(android.os.Parcel parcel) {
        readFromParcel(parcel);
    }

    private AudioIoFlags(int i, java.lang.Object obj) {
        this._tag = i;
        this._value = obj;
    }

    public int getTag() {
        return this._tag;
    }

    public static android.media.audio.common.AudioIoFlags input(int i) {
        return new android.media.audio.common.AudioIoFlags(0, java.lang.Integer.valueOf(i));
    }

    public int getInput() {
        _assertTag(0);
        return ((java.lang.Integer) this._value).intValue();
    }

    public void setInput(int i) {
        _set(0, java.lang.Integer.valueOf(i));
    }

    public static android.media.audio.common.AudioIoFlags output(int i) {
        return new android.media.audio.common.AudioIoFlags(1, java.lang.Integer.valueOf(i));
    }

    public int getOutput() {
        _assertTag(1);
        return ((java.lang.Integer) this._value).intValue();
    }

    public void setOutput(int i) {
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
                parcel.writeInt(getInput());
                break;
            case 1:
                parcel.writeInt(getOutput());
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

    public java.lang.String toString() {
        switch (this._tag) {
            case 0:
                return "AudioIoFlags.input(" + getInput() + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END;
            case 1:
                return "AudioIoFlags.output(" + getOutput() + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END;
            default:
                throw new java.lang.IllegalStateException("unknown field: " + this._tag);
        }
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof android.media.audio.common.AudioIoFlags)) {
            return false;
        }
        android.media.audio.common.AudioIoFlags audioIoFlags = (android.media.audio.common.AudioIoFlags) obj;
        if (this._tag == audioIoFlags._tag && java.util.Objects.deepEquals(this._value, audioIoFlags._value)) {
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
                return "input";
            case 1:
                return "output";
            default:
                throw new java.lang.IllegalStateException("unknown field: " + i);
        }
    }

    private void _set(int i, java.lang.Object obj) {
        this._tag = i;
        this._value = obj;
    }
}
