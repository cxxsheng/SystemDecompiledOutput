package com.android.server.usb.descriptors;

/* loaded from: classes2.dex */
public final class ByteStream {
    private static final java.lang.String TAG = "ByteStream";

    @android.annotation.NonNull
    private final byte[] mBytes;
    private int mIndex;
    private int mReadCount;

    public ByteStream(@android.annotation.NonNull byte[] bArr) {
        if (bArr == null) {
            throw new java.lang.IllegalArgumentException();
        }
        this.mBytes = bArr;
    }

    public void resetReadCount() {
        this.mReadCount = 0;
    }

    public int getReadCount() {
        return this.mReadCount;
    }

    public byte peekByte() {
        if (available() > 0) {
            return this.mBytes[this.mIndex + 1];
        }
        throw new java.lang.IndexOutOfBoundsException();
    }

    public byte getByte() {
        if (available() > 0) {
            this.mReadCount++;
            byte[] bArr = this.mBytes;
            int i = this.mIndex;
            this.mIndex = i + 1;
            return bArr[i];
        }
        throw new java.lang.IndexOutOfBoundsException();
    }

    public int getUnsignedByte() {
        if (available() > 0) {
            this.mReadCount++;
            byte[] bArr = this.mBytes;
            int i = this.mIndex;
            this.mIndex = i + 1;
            return bArr[i] & android.hardware.tv.hdmi.cec.CecDeviceType.INACTIVE;
        }
        throw new java.lang.IndexOutOfBoundsException();
    }

    public int unpackUsbShort() {
        if (available() >= 2) {
            return getUnsignedByte() | (getUnsignedByte() << 8);
        }
        throw new java.lang.IndexOutOfBoundsException();
    }

    public int unpackUsbTriple() {
        if (available() >= 3) {
            return getUnsignedByte() | (getUnsignedByte() << 8) | (getUnsignedByte() << 16);
        }
        throw new java.lang.IndexOutOfBoundsException();
    }

    public int unpackUsbInt() {
        if (available() >= 4) {
            int unsignedByte = getUnsignedByte();
            int unsignedByte2 = getUnsignedByte();
            return unsignedByte | (unsignedByte2 << 8) | (getUnsignedByte() << 16) | (getUnsignedByte() << 24);
        }
        throw new java.lang.IndexOutOfBoundsException();
    }

    public void advance(int i) {
        if (i < 0) {
            throw new java.lang.IllegalArgumentException();
        }
        if (this.mIndex + i <= this.mBytes.length) {
            this.mReadCount += i;
            this.mIndex += i;
        } else {
            this.mIndex = this.mBytes.length;
            throw new java.lang.IndexOutOfBoundsException();
        }
    }

    public void reverse(int i) {
        if (i < 0) {
            throw new java.lang.IllegalArgumentException();
        }
        if (this.mIndex >= i) {
            this.mReadCount -= i;
            this.mIndex -= i;
        } else {
            this.mIndex = 0;
            throw new java.lang.IndexOutOfBoundsException();
        }
    }

    public int available() {
        return this.mBytes.length - this.mIndex;
    }
}
