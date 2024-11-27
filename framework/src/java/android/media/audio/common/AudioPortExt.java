package android.media.audio.common;

/* loaded from: classes2.dex */
public final class AudioPortExt implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.media.audio.common.AudioPortExt> CREATOR = new android.os.Parcelable.Creator<android.media.audio.common.AudioPortExt>() { // from class: android.media.audio.common.AudioPortExt.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.audio.common.AudioPortExt createFromParcel(android.os.Parcel parcel) {
            return new android.media.audio.common.AudioPortExt(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.audio.common.AudioPortExt[] newArray(int i) {
            return new android.media.audio.common.AudioPortExt[i];
        }
    };
    public static final int device = 1;
    public static final int mix = 2;
    public static final int session = 3;
    public static final int unspecified = 0;
    private int _tag;
    private java.lang.Object _value;

    public @interface Tag {
        public static final int device = 1;
        public static final int mix = 2;
        public static final int session = 3;
        public static final int unspecified = 0;
    }

    public AudioPortExt() {
        this._tag = 0;
        this._value = false;
    }

    private AudioPortExt(android.os.Parcel parcel) {
        readFromParcel(parcel);
    }

    private AudioPortExt(int i, java.lang.Object obj) {
        this._tag = i;
        this._value = obj;
    }

    public int getTag() {
        return this._tag;
    }

    public static android.media.audio.common.AudioPortExt unspecified(boolean z) {
        return new android.media.audio.common.AudioPortExt(0, java.lang.Boolean.valueOf(z));
    }

    public boolean getUnspecified() {
        _assertTag(0);
        return ((java.lang.Boolean) this._value).booleanValue();
    }

    public void setUnspecified(boolean z) {
        _set(0, java.lang.Boolean.valueOf(z));
    }

    public static android.media.audio.common.AudioPortExt device(android.media.audio.common.AudioPortDeviceExt audioPortDeviceExt) {
        return new android.media.audio.common.AudioPortExt(1, audioPortDeviceExt);
    }

    public android.media.audio.common.AudioPortDeviceExt getDevice() {
        _assertTag(1);
        return (android.media.audio.common.AudioPortDeviceExt) this._value;
    }

    public void setDevice(android.media.audio.common.AudioPortDeviceExt audioPortDeviceExt) {
        _set(1, audioPortDeviceExt);
    }

    public static android.media.audio.common.AudioPortExt mix(android.media.audio.common.AudioPortMixExt audioPortMixExt) {
        return new android.media.audio.common.AudioPortExt(2, audioPortMixExt);
    }

    public android.media.audio.common.AudioPortMixExt getMix() {
        _assertTag(2);
        return (android.media.audio.common.AudioPortMixExt) this._value;
    }

    public void setMix(android.media.audio.common.AudioPortMixExt audioPortMixExt) {
        _set(2, audioPortMixExt);
    }

    public static android.media.audio.common.AudioPortExt session(int i) {
        return new android.media.audio.common.AudioPortExt(3, java.lang.Integer.valueOf(i));
    }

    public int getSession() {
        _assertTag(3);
        return ((java.lang.Integer) this._value).intValue();
    }

    public void setSession(int i) {
        _set(3, java.lang.Integer.valueOf(i));
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
                parcel.writeTypedObject(getDevice(), i);
                break;
            case 2:
                parcel.writeTypedObject(getMix(), i);
                break;
            case 3:
                parcel.writeInt(getSession());
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
                _set(readInt, (android.media.audio.common.AudioPortDeviceExt) parcel.readTypedObject(android.media.audio.common.AudioPortDeviceExt.CREATOR));
                return;
            case 2:
                _set(readInt, (android.media.audio.common.AudioPortMixExt) parcel.readTypedObject(android.media.audio.common.AudioPortMixExt.CREATOR));
                return;
            case 3:
                _set(readInt, java.lang.Integer.valueOf(parcel.readInt()));
                return;
            default:
                throw new java.lang.IllegalArgumentException("union: unknown tag: " + readInt);
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        switch (getTag()) {
            case 1:
                return 0 | describeContents(getDevice());
            case 2:
                return 0 | describeContents(getMix());
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

    public java.lang.String toString() {
        switch (this._tag) {
            case 0:
                return "AudioPortExt.unspecified(" + getUnspecified() + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END;
            case 1:
                return "AudioPortExt.device(" + java.util.Objects.toString(getDevice()) + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END;
            case 2:
                return "AudioPortExt.mix(" + java.util.Objects.toString(getMix()) + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END;
            case 3:
                return "AudioPortExt.session(" + getSession() + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END;
            default:
                throw new java.lang.IllegalStateException("unknown field: " + this._tag);
        }
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof android.media.audio.common.AudioPortExt)) {
            return false;
        }
        android.media.audio.common.AudioPortExt audioPortExt = (android.media.audio.common.AudioPortExt) obj;
        if (this._tag == audioPortExt._tag && java.util.Objects.deepEquals(this._value, audioPortExt._value)) {
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
                return android.hardware.usb.UsbManager.EXTRA_DEVICE;
            case 2:
                return "mix";
            case 3:
                return "session";
            default:
                throw new java.lang.IllegalStateException("unknown field: " + i);
        }
    }

    private void _set(int i, java.lang.Object obj) {
        this._tag = i;
        this._value = obj;
    }
}
