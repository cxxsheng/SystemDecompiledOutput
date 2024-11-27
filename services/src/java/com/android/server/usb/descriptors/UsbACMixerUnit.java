package com.android.server.usb.descriptors;

/* loaded from: classes2.dex */
public class UsbACMixerUnit extends com.android.server.usb.descriptors.UsbACInterface {
    private static final java.lang.String TAG = "UsbACMixerUnit";
    protected byte[] mInputIDs;
    protected byte mNumInputs;
    protected byte mNumOutputs;
    protected byte mUnitID;

    public UsbACMixerUnit(int i, byte b, byte b2, int i2) {
        super(i, b, b2, i2);
    }

    public byte getUnitID() {
        return this.mUnitID;
    }

    public byte getNumInputs() {
        return this.mNumInputs;
    }

    public byte[] getInputIDs() {
        return this.mInputIDs;
    }

    public byte getNumOutputs() {
        return this.mNumOutputs;
    }

    protected static int calcControlArraySize(int i, int i2) {
        return ((i * i2) + 7) / 8;
    }

    @Override // com.android.server.usb.descriptors.UsbDescriptor
    public int parseRawDescriptors(com.android.server.usb.descriptors.ByteStream byteStream) {
        this.mUnitID = byteStream.getByte();
        this.mNumInputs = byteStream.getByte();
        this.mInputIDs = new byte[this.mNumInputs];
        for (int i = 0; i < this.mNumInputs; i++) {
            this.mInputIDs[i] = byteStream.getByte();
        }
        this.mNumOutputs = byteStream.getByte();
        return this.mLength;
    }
}
