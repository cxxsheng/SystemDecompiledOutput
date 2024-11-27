package android.hardware.usb;

/* loaded from: classes.dex */
public final class PortRole implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.hardware.usb.PortRole> CREATOR = new android.os.Parcelable.Creator<android.hardware.usb.PortRole>() { // from class: android.hardware.usb.PortRole.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.usb.PortRole createFromParcel(android.os.Parcel parcel) {
            return new android.hardware.usb.PortRole(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.usb.PortRole[] newArray(int i) {
            return new android.hardware.usb.PortRole[i];
        }
    };
    public static final int dataRole = 1;
    public static final int mode = 2;
    public static final int powerRole = 0;
    private int _tag;
    private java.lang.Object _value;

    public @interface Tag {
        public static final int dataRole = 1;
        public static final int mode = 2;
        public static final int powerRole = 0;
    }

    public PortRole() {
        this._tag = 0;
        this._value = (byte) 0;
    }

    private PortRole(android.os.Parcel parcel) {
        readFromParcel(parcel);
    }

    private PortRole(int i, java.lang.Object obj) {
        this._tag = i;
        this._value = obj;
    }

    public int getTag() {
        return this._tag;
    }

    public static android.hardware.usb.PortRole powerRole(byte b) {
        return new android.hardware.usb.PortRole(0, java.lang.Byte.valueOf(b));
    }

    public byte getPowerRole() {
        _assertTag(0);
        return ((java.lang.Byte) this._value).byteValue();
    }

    public void setPowerRole(byte b) {
        _set(0, java.lang.Byte.valueOf(b));
    }

    public static android.hardware.usb.PortRole dataRole(byte b) {
        return new android.hardware.usb.PortRole(1, java.lang.Byte.valueOf(b));
    }

    public byte getDataRole() {
        _assertTag(1);
        return ((java.lang.Byte) this._value).byteValue();
    }

    public void setDataRole(byte b) {
        _set(1, java.lang.Byte.valueOf(b));
    }

    public static android.hardware.usb.PortRole mode(byte b) {
        return new android.hardware.usb.PortRole(2, java.lang.Byte.valueOf(b));
    }

    public byte getMode() {
        _assertTag(2);
        return ((java.lang.Byte) this._value).byteValue();
    }

    public void setMode(byte b) {
        _set(2, java.lang.Byte.valueOf(b));
    }

    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this._tag);
        switch (this._tag) {
            case 0:
                parcel.writeByte(getPowerRole());
                break;
            case 1:
                parcel.writeByte(getDataRole());
                break;
            case 2:
                parcel.writeByte(getMode());
                break;
        }
    }

    public void readFromParcel(android.os.Parcel parcel) {
        int readInt = parcel.readInt();
        switch (readInt) {
            case 0:
                _set(readInt, java.lang.Byte.valueOf(parcel.readByte()));
                return;
            case 1:
                _set(readInt, java.lang.Byte.valueOf(parcel.readByte()));
                return;
            case 2:
                _set(readInt, java.lang.Byte.valueOf(parcel.readByte()));
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
                return "powerRole";
            case 1:
                return "dataRole";
            case 2:
                return com.android.server.app.GameManagerService.GamePackageConfiguration.GameModeConfiguration.MODE_KEY;
            default:
                throw new java.lang.IllegalStateException("unknown field: " + i);
        }
    }

    private void _set(int i, java.lang.Object obj) {
        this._tag = i;
        this._value = obj;
    }
}
