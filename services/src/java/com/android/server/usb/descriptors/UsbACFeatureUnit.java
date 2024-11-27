package com.android.server.usb.descriptors;

/* loaded from: classes2.dex */
public final class UsbACFeatureUnit extends com.android.server.usb.descriptors.UsbACInterface {
    public static final int CONTROL_MASK_AGC = 64;
    public static final int CONTROL_MASK_BASS = 4;
    public static final int CONTROL_MASK_BOOST = 256;
    public static final int CONTROL_MASK_DELAY = 128;
    public static final int CONTROL_MASK_EQ = 32;
    public static final int CONTROL_MASK_LOUD = 512;
    public static final int CONTROL_MASK_MID = 8;
    public static final int CONTROL_MASK_MUTE = 1;
    public static final int CONTROL_MASK_TREB = 16;
    public static final int CONTROL_MASK_VOL = 2;
    private static final java.lang.String TAG = "UsbACFeatureUnit";
    private byte mControlSize;
    private int[] mControls;
    private int mNumChannels;
    private byte mSourceID;
    private byte mUnitID;
    private byte mUnitName;

    public UsbACFeatureUnit(int i, byte b, byte b2, int i2) {
        super(i, b, b2, i2);
    }

    public int getNumChannels() {
        return this.mNumChannels;
    }

    public byte getUnitID() {
        return this.mUnitID;
    }

    public byte getSourceID() {
        return this.mSourceID;
    }

    public byte getControlSize() {
        return this.mControlSize;
    }

    public int[] getControls() {
        return this.mControls;
    }

    public byte getUnitName() {
        return this.mUnitName;
    }
}
