package android.hardware.location;

@android.annotation.SystemApi
/* loaded from: classes2.dex */
public class ContextHubInfo implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.hardware.location.ContextHubInfo> CREATOR = new android.os.Parcelable.Creator<android.hardware.location.ContextHubInfo>() { // from class: android.hardware.location.ContextHubInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.location.ContextHubInfo createFromParcel(android.os.Parcel parcel) {
            return new android.hardware.location.ContextHubInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.location.ContextHubInfo[] newArray(int i) {
            return new android.hardware.location.ContextHubInfo[i];
        }
    };
    private byte mChreApiMajorVersion;
    private byte mChreApiMinorVersion;
    private short mChrePatchVersion;
    private long mChrePlatformId;
    private int mId;
    private int mMaxPacketLengthBytes;
    private android.hardware.location.MemoryRegion[] mMemoryRegions;
    private java.lang.String mName;
    private float mPeakMips;
    private float mPeakPowerDrawMw;
    private int mPlatformVersion;
    private float mSleepPowerDrawMw;
    private float mStoppedPowerDrawMw;
    private int[] mSupportedSensors;
    private boolean mSupportsReliableMessages;
    private java.lang.String mToolchain;
    private int mToolchainVersion;
    private java.lang.String mVendor;

    public ContextHubInfo() {
    }

    public ContextHubInfo(android.hardware.contexthub.V1_0.ContextHub contextHub) {
        this.mId = contextHub.hubId;
        this.mName = contextHub.name;
        this.mVendor = contextHub.vendor;
        this.mToolchain = contextHub.toolchain;
        this.mPlatformVersion = contextHub.platformVersion;
        this.mToolchainVersion = contextHub.toolchainVersion;
        this.mPeakMips = contextHub.peakMips;
        this.mStoppedPowerDrawMw = contextHub.stoppedPowerDrawMw;
        this.mSleepPowerDrawMw = contextHub.sleepPowerDrawMw;
        this.mPeakPowerDrawMw = contextHub.peakPowerDrawMw;
        this.mMaxPacketLengthBytes = contextHub.maxSupportedMsgLen;
        this.mSupportsReliableMessages = false;
        this.mChrePlatformId = contextHub.chrePlatformId;
        this.mChreApiMajorVersion = contextHub.chreApiMajorVersion;
        this.mChreApiMinorVersion = contextHub.chreApiMinorVersion;
        this.mChrePatchVersion = contextHub.chrePatchVersion;
        this.mSupportedSensors = new int[0];
        this.mMemoryRegions = new android.hardware.location.MemoryRegion[0];
    }

    public ContextHubInfo(android.hardware.contexthub.ContextHubInfo contextHubInfo) {
        this.mId = contextHubInfo.id;
        this.mName = contextHubInfo.name;
        this.mVendor = contextHubInfo.vendor;
        this.mToolchain = contextHubInfo.toolchain;
        this.mPlatformVersion = 0;
        this.mToolchainVersion = 0;
        this.mPeakMips = contextHubInfo.peakMips;
        this.mStoppedPowerDrawMw = 0.0f;
        this.mSleepPowerDrawMw = 0.0f;
        this.mPeakPowerDrawMw = 0.0f;
        this.mMaxPacketLengthBytes = contextHubInfo.maxSupportedMessageLengthBytes;
        this.mSupportsReliableMessages = android.chre.flags.Flags.reliableMessageImplementation() && contextHubInfo.supportsReliableMessages;
        this.mChrePlatformId = contextHubInfo.chrePlatformId;
        this.mChreApiMajorVersion = contextHubInfo.chreApiMajorVersion;
        this.mChreApiMinorVersion = contextHubInfo.chreApiMinorVersion;
        this.mChrePatchVersion = (short) contextHubInfo.chrePatchVersion;
        this.mSupportedSensors = new int[0];
        this.mMemoryRegions = new android.hardware.location.MemoryRegion[0];
    }

    public int getMaxPacketLengthBytes() {
        return this.mMaxPacketLengthBytes;
    }

    public boolean supportsReliableMessages() {
        return this.mSupportsReliableMessages;
    }

    public int getId() {
        return this.mId;
    }

    public java.lang.String getName() {
        return this.mName;
    }

    public java.lang.String getVendor() {
        return this.mVendor;
    }

    public java.lang.String getToolchain() {
        return this.mToolchain;
    }

    public int getPlatformVersion() {
        return this.mPlatformVersion;
    }

    public int getStaticSwVersion() {
        return (java.lang.Byte.toUnsignedInt(this.mChreApiMajorVersion) << 24) | (java.lang.Byte.toUnsignedInt(this.mChreApiMinorVersion) << 16) | java.lang.Short.toUnsignedInt(this.mChrePatchVersion);
    }

    public int getToolchainVersion() {
        return this.mToolchainVersion;
    }

    public float getPeakMips() {
        return this.mPeakMips;
    }

    public float getStoppedPowerDrawMw() {
        return this.mStoppedPowerDrawMw;
    }

    public float getSleepPowerDrawMw() {
        return this.mSleepPowerDrawMw;
    }

    public float getPeakPowerDrawMw() {
        return this.mPeakPowerDrawMw;
    }

    public int[] getSupportedSensors() {
        return java.util.Arrays.copyOf(this.mSupportedSensors, this.mSupportedSensors.length);
    }

    public android.hardware.location.MemoryRegion[] getMemoryRegions() {
        return (android.hardware.location.MemoryRegion[]) java.util.Arrays.copyOf(this.mMemoryRegions, this.mMemoryRegions.length);
    }

    public long getChrePlatformId() {
        return this.mChrePlatformId;
    }

    public byte getChreApiMajorVersion() {
        return this.mChreApiMajorVersion;
    }

    public byte getChreApiMinorVersion() {
        return this.mChreApiMinorVersion;
    }

    public short getChrePatchVersion() {
        return this.mChrePatchVersion;
    }

    public java.lang.String toString() {
        return (((((((((((("ID/handle : " + this.mId) + ", Name : " + this.mName) + "\n\tVendor : " + this.mVendor) + ", Toolchain : " + this.mToolchain) + ", Toolchain version: 0x" + java.lang.Integer.toHexString(this.mToolchainVersion)) + "\n\tPlatformVersion : 0x" + java.lang.Integer.toHexString(this.mPlatformVersion)) + ", SwVersion : " + java.lang.Byte.toUnsignedInt(this.mChreApiMajorVersion) + android.media.MediaMetrics.SEPARATOR + java.lang.Byte.toUnsignedInt(this.mChreApiMinorVersion) + android.media.MediaMetrics.SEPARATOR + java.lang.Short.toUnsignedInt(this.mChrePatchVersion)) + ", CHRE platform ID: 0x" + java.lang.Long.toHexString(this.mChrePlatformId)) + "\n\tPeakMips : " + this.mPeakMips) + ", StoppedPowerDraw : " + this.mStoppedPowerDrawMw + " mW") + ", PeakPowerDraw : " + this.mPeakPowerDrawMw + " mW") + ", MaxPacketLength : " + this.mMaxPacketLengthBytes + " Bytes") + ", SupportsReliableMessage : " + this.mSupportsReliableMessages;
    }

    public void dump(android.util.proto.ProtoOutputStream protoOutputStream) {
        protoOutputStream.write(1120986464257L, this.mId);
        protoOutputStream.write(1138166333442L, this.mName);
        protoOutputStream.write(1138166333443L, this.mVendor);
        protoOutputStream.write(1138166333444L, this.mToolchain);
        protoOutputStream.write(1120986464261L, this.mPlatformVersion);
        protoOutputStream.write(1120986464262L, getStaticSwVersion());
        protoOutputStream.write(1120986464263L, this.mToolchainVersion);
        protoOutputStream.write(1112396529672L, this.mChrePlatformId);
        protoOutputStream.write(1108101562377L, this.mPeakMips);
        protoOutputStream.write(1108101562378L, this.mStoppedPowerDrawMw);
        protoOutputStream.write(1108101562379L, this.mSleepPowerDrawMw);
        protoOutputStream.write(1108101562380L, this.mPeakPowerDrawMw);
        protoOutputStream.write(1120986464269L, this.mMaxPacketLengthBytes);
        protoOutputStream.write(1120986464270L, this.mSupportsReliableMessages);
    }

    public boolean equals(java.lang.Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof android.hardware.location.ContextHubInfo)) {
            return false;
        }
        android.hardware.location.ContextHubInfo contextHubInfo = (android.hardware.location.ContextHubInfo) obj;
        return contextHubInfo.getId() == this.mId && contextHubInfo.getName().equals(this.mName) && contextHubInfo.getVendor().equals(this.mVendor) && contextHubInfo.getToolchain().equals(this.mToolchain) && contextHubInfo.getToolchainVersion() == this.mToolchainVersion && contextHubInfo.getStaticSwVersion() == getStaticSwVersion() && contextHubInfo.getChrePlatformId() == this.mChrePlatformId && contextHubInfo.getPeakMips() == this.mPeakMips && contextHubInfo.getStoppedPowerDrawMw() == this.mStoppedPowerDrawMw && contextHubInfo.getSleepPowerDrawMw() == this.mSleepPowerDrawMw && contextHubInfo.getPeakPowerDrawMw() == this.mPeakPowerDrawMw && contextHubInfo.getMaxPacketLengthBytes() == this.mMaxPacketLengthBytes && (!android.chre.flags.Flags.reliableMessage() || contextHubInfo.supportsReliableMessages() == this.mSupportsReliableMessages) && java.util.Arrays.equals(contextHubInfo.getSupportedSensors(), this.mSupportedSensors) && java.util.Arrays.equals(contextHubInfo.getMemoryRegions(), this.mMemoryRegions);
    }

    private ContextHubInfo(android.os.Parcel parcel) {
        this.mId = parcel.readInt();
        this.mName = parcel.readString();
        this.mVendor = parcel.readString();
        this.mToolchain = parcel.readString();
        this.mPlatformVersion = parcel.readInt();
        this.mToolchainVersion = parcel.readInt();
        this.mPeakMips = parcel.readFloat();
        this.mStoppedPowerDrawMw = parcel.readFloat();
        this.mSleepPowerDrawMw = parcel.readFloat();
        this.mPeakPowerDrawMw = parcel.readFloat();
        this.mMaxPacketLengthBytes = parcel.readInt();
        this.mChrePlatformId = parcel.readLong();
        this.mChreApiMajorVersion = parcel.readByte();
        this.mChreApiMinorVersion = parcel.readByte();
        this.mChrePatchVersion = (short) parcel.readInt();
        this.mSupportedSensors = new int[parcel.readInt()];
        parcel.readIntArray(this.mSupportedSensors);
        this.mMemoryRegions = (android.hardware.location.MemoryRegion[]) parcel.createTypedArray(android.hardware.location.MemoryRegion.CREATOR);
        this.mSupportsReliableMessages = parcel.readBoolean();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mId);
        parcel.writeString(this.mName);
        parcel.writeString(this.mVendor);
        parcel.writeString(this.mToolchain);
        parcel.writeInt(this.mPlatformVersion);
        parcel.writeInt(this.mToolchainVersion);
        parcel.writeFloat(this.mPeakMips);
        parcel.writeFloat(this.mStoppedPowerDrawMw);
        parcel.writeFloat(this.mSleepPowerDrawMw);
        parcel.writeFloat(this.mPeakPowerDrawMw);
        parcel.writeInt(this.mMaxPacketLengthBytes);
        parcel.writeLong(this.mChrePlatformId);
        parcel.writeByte(this.mChreApiMajorVersion);
        parcel.writeByte(this.mChreApiMinorVersion);
        parcel.writeInt(this.mChrePatchVersion);
        parcel.writeInt(this.mSupportedSensors.length);
        parcel.writeIntArray(this.mSupportedSensors);
        parcel.writeTypedArray(this.mMemoryRegions, i);
        parcel.writeBoolean(this.mSupportsReliableMessages);
    }
}
