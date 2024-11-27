package android.media;

/* loaded from: classes2.dex */
public final class AudioPortExtSys implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.media.AudioPortExtSys> CREATOR = new android.os.Parcelable.Creator<android.media.AudioPortExtSys>() { // from class: android.media.AudioPortExtSys.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.AudioPortExtSys createFromParcel(android.os.Parcel parcel) {
            return new android.media.AudioPortExtSys(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.AudioPortExtSys[] newArray(int i) {
            return new android.media.AudioPortExtSys[i];
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

    public AudioPortExtSys() {
        this._tag = 0;
        this._value = false;
    }

    private AudioPortExtSys(android.os.Parcel parcel) {
        readFromParcel(parcel);
    }

    private AudioPortExtSys(int i, java.lang.Object obj) {
        this._tag = i;
        this._value = obj;
    }

    public int getTag() {
        return this._tag;
    }

    public static android.media.AudioPortExtSys unspecified(boolean z) {
        return new android.media.AudioPortExtSys(0, java.lang.Boolean.valueOf(z));
    }

    public boolean getUnspecified() {
        _assertTag(0);
        return ((java.lang.Boolean) this._value).booleanValue();
    }

    public void setUnspecified(boolean z) {
        _set(0, java.lang.Boolean.valueOf(z));
    }

    public static android.media.AudioPortExtSys device(android.media.AudioPortDeviceExtSys audioPortDeviceExtSys) {
        return new android.media.AudioPortExtSys(1, audioPortDeviceExtSys);
    }

    public android.media.AudioPortDeviceExtSys getDevice() {
        _assertTag(1);
        return (android.media.AudioPortDeviceExtSys) this._value;
    }

    public void setDevice(android.media.AudioPortDeviceExtSys audioPortDeviceExtSys) {
        _set(1, audioPortDeviceExtSys);
    }

    public static android.media.AudioPortExtSys mix(android.media.AudioPortMixExtSys audioPortMixExtSys) {
        return new android.media.AudioPortExtSys(2, audioPortMixExtSys);
    }

    public android.media.AudioPortMixExtSys getMix() {
        _assertTag(2);
        return (android.media.AudioPortMixExtSys) this._value;
    }

    public void setMix(android.media.AudioPortMixExtSys audioPortMixExtSys) {
        _set(2, audioPortMixExtSys);
    }

    public static android.media.AudioPortExtSys session(int i) {
        return new android.media.AudioPortExtSys(3, java.lang.Integer.valueOf(i));
    }

    public int getSession() {
        _assertTag(3);
        return ((java.lang.Integer) this._value).intValue();
    }

    public void setSession(int i) {
        _set(3, java.lang.Integer.valueOf(i));
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
                _set(readInt, (android.media.AudioPortDeviceExtSys) parcel.readTypedObject(android.media.AudioPortDeviceExtSys.CREATOR));
                return;
            case 2:
                _set(readInt, (android.media.AudioPortMixExtSys) parcel.readTypedObject(android.media.AudioPortMixExtSys.CREATOR));
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
