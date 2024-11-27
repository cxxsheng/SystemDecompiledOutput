package android.hardware.location;

@android.annotation.SystemApi
/* loaded from: classes2.dex */
public final class NanoAppBinary implements android.os.Parcelable {
    private static final int EXPECTED_HEADER_VERSION = 1;
    private static final int EXPECTED_MAGIC_VALUE = 1330528590;
    private static final int HEADER_SIZE_BYTES = 40;
    private static final int NANOAPP_ENCRYPTED_FLAG_BIT = 2;
    private static final int NANOAPP_SIGNED_FLAG_BIT = 1;
    private static final java.lang.String TAG = "NanoAppBinary";
    private int mFlags;
    private boolean mHasValidHeader;
    private int mHeaderVersion;
    private long mHwHubType;
    private int mMagic;
    private byte[] mNanoAppBinary;
    private long mNanoAppId;
    private int mNanoAppVersion;
    private byte mTargetChreApiMajorVersion;
    private byte mTargetChreApiMinorVersion;
    private static final java.nio.ByteOrder HEADER_ORDER = java.nio.ByteOrder.LITTLE_ENDIAN;
    public static final android.os.Parcelable.Creator<android.hardware.location.NanoAppBinary> CREATOR = new android.os.Parcelable.Creator<android.hardware.location.NanoAppBinary>() { // from class: android.hardware.location.NanoAppBinary.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.location.NanoAppBinary createFromParcel(android.os.Parcel parcel) {
            return new android.hardware.location.NanoAppBinary(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.location.NanoAppBinary[] newArray(int i) {
            return new android.hardware.location.NanoAppBinary[i];
        }
    };

    public NanoAppBinary(byte[] bArr) {
        this.mHasValidHeader = false;
        this.mNanoAppBinary = bArr;
        parseBinaryHeader();
    }

    private void parseBinaryHeader() {
        java.nio.ByteBuffer order = java.nio.ByteBuffer.wrap(this.mNanoAppBinary).order(HEADER_ORDER);
        this.mHasValidHeader = false;
        try {
            this.mHeaderVersion = order.getInt();
            if (this.mHeaderVersion != 1) {
                android.util.Log.e(TAG, "Unexpected header version " + this.mHeaderVersion + " while parsing header (expected 1" + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
                return;
            }
            this.mMagic = order.getInt();
            this.mNanoAppId = order.getLong();
            this.mNanoAppVersion = order.getInt();
            this.mFlags = order.getInt();
            this.mHwHubType = order.getLong();
            this.mTargetChreApiMajorVersion = order.get();
            this.mTargetChreApiMinorVersion = order.get();
            if (this.mMagic != EXPECTED_MAGIC_VALUE) {
                android.util.Log.e(TAG, "Unexpected magic value " + java.lang.String.format("0x%08X", java.lang.Integer.valueOf(this.mMagic)) + "while parsing header (expected " + java.lang.String.format("0x%08X", java.lang.Integer.valueOf(EXPECTED_MAGIC_VALUE)) + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
            } else {
                this.mHasValidHeader = true;
            }
        } catch (java.nio.BufferUnderflowException e) {
            android.util.Log.e(TAG, "Not enough contents in nanoapp header");
        }
    }

    public byte[] getBinary() {
        return this.mNanoAppBinary;
    }

    public byte[] getBinaryNoHeader() {
        if (this.mNanoAppBinary.length < 40) {
            throw new java.lang.IndexOutOfBoundsException("NanoAppBinary binary byte size (" + this.mNanoAppBinary.length + ") is less than header size (40" + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
        }
        return java.util.Arrays.copyOfRange(this.mNanoAppBinary, 40, this.mNanoAppBinary.length);
    }

    public boolean hasValidHeader() {
        return this.mHasValidHeader;
    }

    public int getHeaderVersion() {
        return this.mHeaderVersion;
    }

    public long getNanoAppId() {
        return this.mNanoAppId;
    }

    public int getNanoAppVersion() {
        return this.mNanoAppVersion;
    }

    public long getHwHubType() {
        return this.mHwHubType;
    }

    public byte getTargetChreApiMajorVersion() {
        return this.mTargetChreApiMajorVersion;
    }

    public byte getTargetChreApiMinorVersion() {
        return this.mTargetChreApiMinorVersion;
    }

    public int getFlags() {
        return this.mFlags;
    }

    public boolean isSigned() {
        return (this.mFlags & 1) != 0;
    }

    public boolean isEncrypted() {
        return (this.mFlags & 2) != 0;
    }

    private NanoAppBinary(android.os.Parcel parcel) {
        this.mHasValidHeader = false;
        this.mNanoAppBinary = new byte[parcel.readInt()];
        parcel.readByteArray(this.mNanoAppBinary);
        parseBinaryHeader();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mNanoAppBinary.length);
        parcel.writeByteArray(this.mNanoAppBinary);
    }
}
