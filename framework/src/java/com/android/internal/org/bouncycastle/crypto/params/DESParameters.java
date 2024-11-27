package com.android.internal.org.bouncycastle.crypto.params;

/* loaded from: classes4.dex */
public class DESParameters extends com.android.internal.org.bouncycastle.crypto.params.KeyParameter {
    public static final int DES_KEY_LENGTH = 8;
    private static byte[] DES_weak_keys = {1, 1, 1, 1, 1, 1, 1, 1, 31, 31, 31, 31, 14, 14, 14, 14, com.android.internal.midi.MidiConstants.STATUS_PITCH_BEND, com.android.internal.midi.MidiConstants.STATUS_PITCH_BEND, com.android.internal.midi.MidiConstants.STATUS_PITCH_BEND, com.android.internal.midi.MidiConstants.STATUS_PITCH_BEND, com.android.internal.midi.MidiConstants.STATUS_MIDI_TIME_CODE, com.android.internal.midi.MidiConstants.STATUS_MIDI_TIME_CODE, com.android.internal.midi.MidiConstants.STATUS_MIDI_TIME_CODE, com.android.internal.midi.MidiConstants.STATUS_MIDI_TIME_CODE, -2, -2, -2, -2, -2, -2, -2, -2, 1, -2, 1, -2, 1, -2, 1, -2, 31, com.android.internal.midi.MidiConstants.STATUS_PITCH_BEND, 31, com.android.internal.midi.MidiConstants.STATUS_PITCH_BEND, 14, com.android.internal.midi.MidiConstants.STATUS_MIDI_TIME_CODE, 14, com.android.internal.midi.MidiConstants.STATUS_MIDI_TIME_CODE, 1, com.android.internal.midi.MidiConstants.STATUS_PITCH_BEND, 1, com.android.internal.midi.MidiConstants.STATUS_PITCH_BEND, 1, com.android.internal.midi.MidiConstants.STATUS_MIDI_TIME_CODE, 1, com.android.internal.midi.MidiConstants.STATUS_MIDI_TIME_CODE, 31, -2, 31, -2, 14, -2, 14, -2, 1, 31, 1, 31, 1, 14, 1, 14, com.android.internal.midi.MidiConstants.STATUS_PITCH_BEND, -2, com.android.internal.midi.MidiConstants.STATUS_PITCH_BEND, -2, com.android.internal.midi.MidiConstants.STATUS_MIDI_TIME_CODE, -2, com.android.internal.midi.MidiConstants.STATUS_MIDI_TIME_CODE, -2, -2, 1, -2, 1, -2, 1, -2, 1, com.android.internal.midi.MidiConstants.STATUS_PITCH_BEND, 31, com.android.internal.midi.MidiConstants.STATUS_PITCH_BEND, 31, com.android.internal.midi.MidiConstants.STATUS_MIDI_TIME_CODE, 14, com.android.internal.midi.MidiConstants.STATUS_MIDI_TIME_CODE, 14, com.android.internal.midi.MidiConstants.STATUS_PITCH_BEND, 1, com.android.internal.midi.MidiConstants.STATUS_PITCH_BEND, 1, com.android.internal.midi.MidiConstants.STATUS_MIDI_TIME_CODE, 1, com.android.internal.midi.MidiConstants.STATUS_MIDI_TIME_CODE, 1, -2, 31, -2, 31, -2, 14, -2, 14, 31, 1, 31, 1, 14, 1, 14, 1, -2, com.android.internal.midi.MidiConstants.STATUS_PITCH_BEND, -2, com.android.internal.midi.MidiConstants.STATUS_PITCH_BEND, -2, com.android.internal.midi.MidiConstants.STATUS_MIDI_TIME_CODE, -2, com.android.internal.midi.MidiConstants.STATUS_MIDI_TIME_CODE};
    private static final int N_DES_WEAK_KEYS = 16;

    public DESParameters(byte[] bArr) {
        super(bArr);
        if (isWeakKey(bArr, 0)) {
            throw new java.lang.IllegalArgumentException("attempt to create weak DES key");
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:12:0x001d, code lost:
    
        r2 = r2 + 1;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static boolean isWeakKey(byte[] bArr, int i) {
        if (bArr.length - i < 8) {
            throw new java.lang.IllegalArgumentException("key material too short.");
        }
        int i2 = 0;
        while (i2 < 16) {
            for (int i3 = 0; i3 < 8; i3++) {
                if (bArr[i3 + i] != DES_weak_keys[(i2 * 8) + i3]) {
                    break;
                }
            }
            return true;
        }
        return false;
    }

    public static void setOddParity(byte[] bArr) {
        for (int i = 0; i < bArr.length; i++) {
            byte b = bArr[i];
            bArr[i] = (byte) (((((b >> 7) ^ ((((((b >> 1) ^ (b >> 2)) ^ (b >> 3)) ^ (b >> 4)) ^ (b >> 5)) ^ (b >> 6))) ^ 1) & 1) | (b & com.android.internal.midi.MidiConstants.STATUS_ACTIVE_SENSING));
        }
    }
}
