package com.android.internal.midi;

/* loaded from: classes4.dex */
public class MidiFramer extends android.media.midi.MidiReceiver {
    public java.lang.String TAG = "MidiFramer";
    private byte[] mBuffer = new byte[3];
    private int mCount;
    private boolean mInSysEx;
    private int mNeeded;
    private android.media.midi.MidiReceiver mReceiver;
    private byte mRunningStatus;

    public MidiFramer(android.media.midi.MidiReceiver midiReceiver) {
        this.mReceiver = midiReceiver;
    }

    public static java.lang.String formatMidiData(byte[] bArr, int i, int i2) {
        java.lang.String str = "MIDI+" + i + " : ";
        for (int i3 = 0; i3 < i2; i3++) {
            str = str + java.lang.String.format("0x%02X, ", java.lang.Byte.valueOf(bArr[i + i3]));
        }
        return str;
    }

    @Override // android.media.midi.MidiReceiver
    public void onSend(byte[] bArr, int i, int i2, long j) throws java.io.IOException {
        int i3;
        int i4 = this.mInSysEx ? i : -1;
        int i5 = i;
        for (int i6 = 0; i6 < i2; i6++) {
            byte b = bArr[i5];
            int i7 = b & 255;
            if (i7 >= 128) {
                if (i7 < 240) {
                    this.mRunningStatus = b;
                    this.mCount = 1;
                    this.mNeeded = com.android.internal.midi.MidiConstants.getBytesPerMessage(b) - 1;
                } else if (i7 < 248) {
                    if (i7 == 240) {
                        this.mInSysEx = true;
                        i4 = i5;
                    } else if (i7 != 247) {
                        this.mBuffer[0] = b;
                        this.mRunningStatus = (byte) 0;
                        this.mCount = 1;
                        this.mNeeded = com.android.internal.midi.MidiConstants.getBytesPerMessage(b) - 1;
                    } else if (this.mInSysEx) {
                        this.mReceiver.send(bArr, i4, (i5 - i4) + 1, j);
                        this.mInSysEx = false;
                        i4 = -1;
                    }
                } else {
                    if (!this.mInSysEx) {
                        i3 = i4;
                    } else {
                        this.mReceiver.send(bArr, i4, i5 - i4, j);
                        i3 = i5 + 1;
                    }
                    this.mReceiver.send(bArr, i5, 1, j);
                    i4 = i3;
                }
            } else if (this.mInSysEx) {
                continue;
            } else {
                if (this.mNeeded <= 0) {
                    break;
                }
                byte[] bArr2 = this.mBuffer;
                int i8 = this.mCount;
                this.mCount = i8 + 1;
                bArr2[i8] = b;
                int i9 = this.mNeeded - 1;
                this.mNeeded = i9;
                if (i9 == 0) {
                    if (this.mRunningStatus != 0) {
                        this.mBuffer[0] = this.mRunningStatus;
                    }
                    this.mReceiver.send(this.mBuffer, 0, this.mCount, j);
                    this.mNeeded = com.android.internal.midi.MidiConstants.getBytesPerMessage(this.mBuffer[0]) - 1;
                    this.mCount = 1;
                }
            }
            i5++;
        }
        if (i4 >= 0 && i4 < i5) {
            this.mReceiver.send(bArr, i4, i5 - i4, j);
        }
    }
}
