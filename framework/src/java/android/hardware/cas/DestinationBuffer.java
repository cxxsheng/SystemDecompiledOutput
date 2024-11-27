package android.hardware.cas;

/* loaded from: classes.dex */
public final class DestinationBuffer implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.hardware.cas.DestinationBuffer> CREATOR = new android.os.Parcelable.Creator<android.hardware.cas.DestinationBuffer>() { // from class: android.hardware.cas.DestinationBuffer.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.cas.DestinationBuffer createFromParcel(android.os.Parcel parcel) {
            return new android.hardware.cas.DestinationBuffer(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.cas.DestinationBuffer[] newArray(int i) {
            return new android.hardware.cas.DestinationBuffer[i];
        }
    };
    public static final int nonsecureMemory = 0;
    public static final int secureMemory = 1;
    private int _tag;
    private java.lang.Object _value;

    public @interface Tag {
        public static final int nonsecureMemory = 0;
        public static final int secureMemory = 1;
    }

    public DestinationBuffer() {
        this._tag = 0;
        this._value = null;
    }

    private DestinationBuffer(android.os.Parcel parcel) {
        readFromParcel(parcel);
    }

    private DestinationBuffer(int i, java.lang.Object obj) {
        this._tag = i;
        this._value = obj;
    }

    public int getTag() {
        return this._tag;
    }

    public static android.hardware.cas.DestinationBuffer nonsecureMemory(android.hardware.cas.SharedBuffer sharedBuffer) {
        return new android.hardware.cas.DestinationBuffer(0, sharedBuffer);
    }

    public android.hardware.cas.SharedBuffer getNonsecureMemory() {
        _assertTag(0);
        return (android.hardware.cas.SharedBuffer) this._value;
    }

    public void setNonsecureMemory(android.hardware.cas.SharedBuffer sharedBuffer) {
        _set(0, sharedBuffer);
    }

    public static android.hardware.cas.DestinationBuffer secureMemory(android.hardware.common.NativeHandle nativeHandle) {
        return new android.hardware.cas.DestinationBuffer(1, nativeHandle);
    }

    public android.hardware.common.NativeHandle getSecureMemory() {
        _assertTag(1);
        return (android.hardware.common.NativeHandle) this._value;
    }

    public void setSecureMemory(android.hardware.common.NativeHandle nativeHandle) {
        _set(1, nativeHandle);
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
                parcel.writeTypedObject(getNonsecureMemory(), i);
                break;
            case 1:
                parcel.writeTypedObject(getSecureMemory(), i);
                break;
        }
    }

    public void readFromParcel(android.os.Parcel parcel) {
        int readInt = parcel.readInt();
        switch (readInt) {
            case 0:
                _set(readInt, (android.hardware.cas.SharedBuffer) parcel.readTypedObject(android.hardware.cas.SharedBuffer.CREATOR));
                return;
            case 1:
                _set(readInt, (android.hardware.common.NativeHandle) parcel.readTypedObject(android.hardware.common.NativeHandle.CREATOR));
                return;
            default:
                throw new java.lang.IllegalArgumentException("union: unknown tag: " + readInt);
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        switch (getTag()) {
            case 0:
                return 0 | describeContents(getNonsecureMemory());
            case 1:
                return 0 | describeContents(getSecureMemory());
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
                return "nonsecureMemory";
            case 1:
                return "secureMemory";
            default:
                throw new java.lang.IllegalStateException("unknown field: " + i);
        }
    }

    private void _set(int i, java.lang.Object obj) {
        this._tag = i;
        this._value = obj;
    }
}
