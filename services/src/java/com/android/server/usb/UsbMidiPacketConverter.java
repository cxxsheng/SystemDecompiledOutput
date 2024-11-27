package com.android.server.usb;

/* loaded from: classes2.dex */
public class UsbMidiPacketConverter {
    private static final byte CODE_INDEX_NUMBER_SINGLE_BYTE = 15;
    private static final byte CODE_INDEX_NUMBER_SYSEX_END_SINGLE_BYTE = 5;
    private static final byte CODE_INDEX_NUMBER_SYSEX_STARTS_OR_CONTINUES = 4;
    private static final byte FIRST_SYSTEM_MESSAGE_VALUE = -16;
    private static final byte SYSEX_END_EXCLUSIVE = -9;
    private static final byte SYSEX_START_EXCLUSIVE = -16;
    private static final java.lang.String TAG = "UsbMidiPacketConverter";
    private java.io.ByteArrayOutputStream mEncoderOutputStream = new java.io.ByteArrayOutputStream();
    private com.android.server.usb.UsbMidiPacketConverter.UsbMidiDecoder mUsbMidiDecoder;
    private com.android.server.usb.UsbMidiPacketConverter.UsbMidiEncoder[] mUsbMidiEncoders;
    private static final int[] PAYLOAD_SIZE = {-1, -1, 2, 3, 3, 1, 2, 3, 3, 3, 3, 3, 2, 2, 3, 1};
    private static final int[] CODE_INDEX_NUMBER_FROM_SYSTEM_TYPE = {-1, 2, 3, 2, -1, -1, 5, -1, 5, -1, 5, 5, 5, -1, 5, 5};

    public void createEncoders(int i) {
        this.mUsbMidiEncoders = new com.android.server.usb.UsbMidiPacketConverter.UsbMidiEncoder[i];
        for (int i2 = 0; i2 < i; i2++) {
            this.mUsbMidiEncoders[i2] = new com.android.server.usb.UsbMidiPacketConverter.UsbMidiEncoder(i2);
        }
    }

    public void encodeMidiPackets(byte[] bArr, int i, int i2) {
        if (i2 >= this.mUsbMidiEncoders.length) {
            android.util.Log.w(TAG, "encoderId " + i2 + " invalid");
            i2 = 0;
        }
        byte[] encode = this.mUsbMidiEncoders[i2].encode(bArr, i);
        this.mEncoderOutputStream.write(encode, 0, encode.length);
    }

    public byte[] pullEncodedMidiPackets() {
        byte[] byteArray = this.mEncoderOutputStream.toByteArray();
        this.mEncoderOutputStream.reset();
        return byteArray;
    }

    public void createDecoders(int i) {
        this.mUsbMidiDecoder = new com.android.server.usb.UsbMidiPacketConverter.UsbMidiDecoder(i);
    }

    public void decodeMidiPackets(byte[] bArr, int i) {
        this.mUsbMidiDecoder.decode(bArr, i);
    }

    public byte[] pullDecodedMidiPackets(int i) {
        return this.mUsbMidiDecoder.pullBytes(i);
    }

    private class UsbMidiDecoder {
        java.io.ByteArrayOutputStream[] mDecodedByteArrays;
        int mNumJacks;

        UsbMidiDecoder(int i) {
            this.mNumJacks = i;
            this.mDecodedByteArrays = new java.io.ByteArrayOutputStream[i];
            for (int i2 = 0; i2 < i; i2++) {
                this.mDecodedByteArrays[i2] = new java.io.ByteArrayOutputStream();
            }
        }

        public void decode(byte[] bArr, int i) {
            new java.io.ByteArrayOutputStream();
            if (i % 4 != 0) {
                android.util.Log.w(com.android.server.usb.UsbMidiPacketConverter.TAG, "size " + i + " not multiple of 4");
            }
            for (int i2 = 0; i2 + 3 < i; i2 += 4) {
                int i3 = (bArr[i2] >> 4) & 15;
                int i4 = com.android.server.usb.UsbMidiPacketConverter.PAYLOAD_SIZE[bArr[i2] & 15];
                if (i4 >= 0) {
                    if (i3 >= this.mNumJacks) {
                        android.util.Log.w(com.android.server.usb.UsbMidiPacketConverter.TAG, "cableNumber " + i3 + " invalid");
                        i3 = 0;
                    }
                    this.mDecodedByteArrays[i3].write(bArr, i2 + 1, i4);
                }
            }
        }

        public byte[] pullBytes(int i) {
            if (i >= this.mNumJacks) {
                android.util.Log.w(com.android.server.usb.UsbMidiPacketConverter.TAG, "cableNumber " + i + " invalid");
                i = 0;
            }
            byte[] byteArray = this.mDecodedByteArrays[i].toByteArray();
            this.mDecodedByteArrays[i].reset();
            return byteArray;
        }
    }

    private class UsbMidiEncoder {
        private byte mShiftedCableNumber;
        private byte[] mStoredSystemExclusiveBytes = new byte[3];
        private int mNumStoredSystemExclusiveBytes = 0;
        private boolean mHasSystemExclusiveStarted = false;
        private byte[] mEmptyBytes = new byte[3];

        UsbMidiEncoder(int i) {
            this.mShiftedCableNumber = (byte) (i << 4);
        }

        public byte[] encode(byte[] bArr, int i) {
            java.io.ByteArrayOutputStream byteArrayOutputStream = new java.io.ByteArrayOutputStream();
            int i2 = 0;
            while (i2 < i) {
                if (bArr[i2] >= 0) {
                    if (this.mHasSystemExclusiveStarted) {
                        this.mStoredSystemExclusiveBytes[this.mNumStoredSystemExclusiveBytes] = bArr[i2];
                        this.mNumStoredSystemExclusiveBytes++;
                        if (this.mNumStoredSystemExclusiveBytes == 3) {
                            byteArrayOutputStream.write(this.mShiftedCableNumber | 4);
                            byteArrayOutputStream.write(this.mStoredSystemExclusiveBytes, 0, 3);
                            this.mNumStoredSystemExclusiveBytes = 0;
                        }
                    } else {
                        writeSingleByte(byteArrayOutputStream, bArr[i2]);
                    }
                    i2++;
                } else {
                    if (bArr[i2] != -9 && this.mHasSystemExclusiveStarted) {
                        for (int i3 = 0; i3 < this.mNumStoredSystemExclusiveBytes; i3++) {
                            writeSingleByte(byteArrayOutputStream, this.mStoredSystemExclusiveBytes[i3]);
                        }
                        this.mNumStoredSystemExclusiveBytes = 0;
                        this.mHasSystemExclusiveStarted = false;
                    }
                    if (bArr[i2] < -16) {
                        byte b = (byte) ((bArr[i2] >> 4) & 15);
                        int i4 = com.android.server.usb.UsbMidiPacketConverter.PAYLOAD_SIZE[b];
                        int i5 = i2 + i4;
                        if (i5 <= i) {
                            byteArrayOutputStream.write(b | this.mShiftedCableNumber);
                            byteArrayOutputStream.write(bArr, i2, i4);
                            byteArrayOutputStream.write(this.mEmptyBytes, 0, 3 - i4);
                            i2 = i5;
                        } else {
                            while (i2 < i) {
                                writeSingleByte(byteArrayOutputStream, bArr[i2]);
                                i2++;
                            }
                        }
                    } else if (bArr[i2] == -16) {
                        this.mHasSystemExclusiveStarted = true;
                        this.mStoredSystemExclusiveBytes[0] = bArr[i2];
                        this.mNumStoredSystemExclusiveBytes = 1;
                        i2++;
                    } else if (bArr[i2] == -9) {
                        byteArrayOutputStream.write((this.mNumStoredSystemExclusiveBytes + 5) | this.mShiftedCableNumber);
                        this.mStoredSystemExclusiveBytes[this.mNumStoredSystemExclusiveBytes] = bArr[i2];
                        this.mNumStoredSystemExclusiveBytes++;
                        byteArrayOutputStream.write(this.mStoredSystemExclusiveBytes, 0, this.mNumStoredSystemExclusiveBytes);
                        byteArrayOutputStream.write(this.mEmptyBytes, 0, 3 - this.mNumStoredSystemExclusiveBytes);
                        this.mHasSystemExclusiveStarted = false;
                        this.mNumStoredSystemExclusiveBytes = 0;
                        i2++;
                    } else {
                        int i6 = com.android.server.usb.UsbMidiPacketConverter.CODE_INDEX_NUMBER_FROM_SYSTEM_TYPE[bArr[i2] & 15];
                        if (i6 < 0) {
                            writeSingleByte(byteArrayOutputStream, bArr[i2]);
                            i2++;
                        } else {
                            int i7 = com.android.server.usb.UsbMidiPacketConverter.PAYLOAD_SIZE[i6];
                            int i8 = i2 + i7;
                            if (i8 <= i) {
                                byteArrayOutputStream.write(i6 | this.mShiftedCableNumber);
                                byteArrayOutputStream.write(bArr, i2, i7);
                                byteArrayOutputStream.write(this.mEmptyBytes, 0, 3 - i7);
                                i2 = i8;
                            } else {
                                while (i2 < i) {
                                    writeSingleByte(byteArrayOutputStream, bArr[i2]);
                                    i2++;
                                }
                            }
                        }
                    }
                }
            }
            return byteArrayOutputStream.toByteArray();
        }

        private void writeSingleByte(java.io.ByteArrayOutputStream byteArrayOutputStream, byte b) {
            byteArrayOutputStream.write(this.mShiftedCableNumber | 15);
            byteArrayOutputStream.write(b);
            byteArrayOutputStream.write(0);
            byteArrayOutputStream.write(0);
        }
    }
}
