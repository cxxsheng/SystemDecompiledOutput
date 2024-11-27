package com.android.server.audio;

/* loaded from: classes.dex */
class UuidUtils {
    private static final long LSB_PREFIX_BT = 4779445104546938880L;
    private static final long LSB_PREFIX_MASK = -281474976710656L;
    private static final long LSB_SUFFIX_MASK = 281474976710655L;
    public static final java.util.UUID STANDALONE_UUID = new java.util.UUID(0, 0);
    private static final java.lang.String TAG = "AudioService.UuidUtils";

    UuidUtils() {
    }

    public static java.util.UUID uuidFromAudioDeviceAttributes(android.media.AudioDeviceAttributes audioDeviceAttributes) {
        if (!android.media.AudioSystem.isBluetoothA2dpOutDevice(audioDeviceAttributes.getInternalType()) && !android.media.AudioSystem.isBluetoothLeOutDevice(audioDeviceAttributes.getInternalType())) {
            return null;
        }
        java.lang.String replace = audioDeviceAttributes.getAddress().replace(":", "");
        if (replace.length() != 12) {
            return null;
        }
        try {
            return new java.util.UUID(0L, java.lang.Long.decode("0x" + replace).longValue() | LSB_PREFIX_BT);
        } catch (java.lang.NumberFormatException e) {
            return null;
        }
    }
}
