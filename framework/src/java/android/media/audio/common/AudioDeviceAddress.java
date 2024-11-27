package android.media.audio.common;

/* loaded from: classes2.dex */
public final class AudioDeviceAddress implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.media.audio.common.AudioDeviceAddress> CREATOR = new android.os.Parcelable.Creator<android.media.audio.common.AudioDeviceAddress>() { // from class: android.media.audio.common.AudioDeviceAddress.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.audio.common.AudioDeviceAddress createFromParcel(android.os.Parcel parcel) {
            return new android.media.audio.common.AudioDeviceAddress(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.audio.common.AudioDeviceAddress[] newArray(int i) {
            return new android.media.audio.common.AudioDeviceAddress[i];
        }
    };
    public static final int alsa = 4;
    public static final int id = 0;
    public static final int ipv4 = 2;
    public static final int ipv6 = 3;
    public static final int mac = 1;
    private int _tag;
    private java.lang.Object _value;

    public @interface Tag {
        public static final int alsa = 4;
        public static final int id = 0;
        public static final int ipv4 = 2;
        public static final int ipv6 = 3;
        public static final int mac = 1;
    }

    public AudioDeviceAddress() {
        this._tag = 0;
        this._value = null;
    }

    private AudioDeviceAddress(android.os.Parcel parcel) {
        readFromParcel(parcel);
    }

    private AudioDeviceAddress(int i, java.lang.Object obj) {
        this._tag = i;
        this._value = obj;
    }

    public int getTag() {
        return this._tag;
    }

    public static android.media.audio.common.AudioDeviceAddress id(java.lang.String str) {
        return new android.media.audio.common.AudioDeviceAddress(0, str);
    }

    public java.lang.String getId() {
        _assertTag(0);
        return (java.lang.String) this._value;
    }

    public void setId(java.lang.String str) {
        _set(0, str);
    }

    public static android.media.audio.common.AudioDeviceAddress mac(byte[] bArr) {
        return new android.media.audio.common.AudioDeviceAddress(1, bArr);
    }

    public byte[] getMac() {
        _assertTag(1);
        return (byte[]) this._value;
    }

    public void setMac(byte[] bArr) {
        _set(1, bArr);
    }

    public static android.media.audio.common.AudioDeviceAddress ipv4(byte[] bArr) {
        return new android.media.audio.common.AudioDeviceAddress(2, bArr);
    }

    public byte[] getIpv4() {
        _assertTag(2);
        return (byte[]) this._value;
    }

    public void setIpv4(byte[] bArr) {
        _set(2, bArr);
    }

    public static android.media.audio.common.AudioDeviceAddress ipv6(int[] iArr) {
        return new android.media.audio.common.AudioDeviceAddress(3, iArr);
    }

    public int[] getIpv6() {
        _assertTag(3);
        return (int[]) this._value;
    }

    public void setIpv6(int[] iArr) {
        _set(3, iArr);
    }

    public static android.media.audio.common.AudioDeviceAddress alsa(int[] iArr) {
        return new android.media.audio.common.AudioDeviceAddress(4, iArr);
    }

    public int[] getAlsa() {
        _assertTag(4);
        return (int[]) this._value;
    }

    public void setAlsa(int[] iArr) {
        _set(4, iArr);
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
                parcel.writeString(getId());
                break;
            case 1:
                parcel.writeByteArray(getMac());
                break;
            case 2:
                parcel.writeByteArray(getIpv4());
                break;
            case 3:
                parcel.writeIntArray(getIpv6());
                break;
            case 4:
                parcel.writeIntArray(getAlsa());
                break;
        }
    }

    public void readFromParcel(android.os.Parcel parcel) {
        int readInt = parcel.readInt();
        switch (readInt) {
            case 0:
                _set(readInt, parcel.readString());
                return;
            case 1:
                _set(readInt, parcel.createByteArray());
                return;
            case 2:
                _set(readInt, parcel.createByteArray());
                return;
            case 3:
                _set(readInt, parcel.createIntArray());
                return;
            case 4:
                _set(readInt, parcel.createIntArray());
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
                return "AudioDeviceAddress.id(" + java.util.Objects.toString(getId()) + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END;
            case 1:
                return "AudioDeviceAddress.mac(" + java.util.Arrays.toString(getMac()) + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END;
            case 2:
                return "AudioDeviceAddress.ipv4(" + java.util.Arrays.toString(getIpv4()) + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END;
            case 3:
                return "AudioDeviceAddress.ipv6(" + java.util.Arrays.toString(getIpv6()) + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END;
            case 4:
                return "AudioDeviceAddress.alsa(" + java.util.Arrays.toString(getAlsa()) + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END;
            default:
                throw new java.lang.IllegalStateException("unknown field: " + this._tag);
        }
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof android.media.audio.common.AudioDeviceAddress)) {
            return false;
        }
        android.media.audio.common.AudioDeviceAddress audioDeviceAddress = (android.media.audio.common.AudioDeviceAddress) obj;
        if (this._tag == audioDeviceAddress._tag && java.util.Objects.deepEquals(this._value, audioDeviceAddress._value)) {
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
                return "id";
            case 1:
                return "mac";
            case 2:
                return "ipv4";
            case 3:
                return "ipv6";
            case 4:
                return "alsa";
            default:
                throw new java.lang.IllegalStateException("unknown field: " + i);
        }
    }

    private void _set(int i, java.lang.Object obj) {
        this._tag = i;
        this._value = obj;
    }
}
