package android.hardware.audio.common.V2_0;

/* loaded from: classes.dex */
public final class AudioHandleConsts {
    public static final int AUDIO_IO_HANDLE_NONE = 0;
    public static final int AUDIO_MODULE_HANDLE_NONE = 0;
    public static final int AUDIO_PATCH_HANDLE_NONE = 0;
    public static final int AUDIO_PORT_HANDLE_NONE = 0;

    public static final java.lang.String toString(int i) {
        if (i == 0) {
            return "AUDIO_IO_HANDLE_NONE";
        }
        if (i == 0) {
            return "AUDIO_MODULE_HANDLE_NONE";
        }
        if (i == 0) {
            return "AUDIO_PORT_HANDLE_NONE";
        }
        if (i == 0) {
            return "AUDIO_PATCH_HANDLE_NONE";
        }
        return "0x" + java.lang.Integer.toHexString(i);
    }

    public static final java.lang.String dumpBitfield(int i) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        arrayList.add("AUDIO_IO_HANDLE_NONE");
        arrayList.add("AUDIO_MODULE_HANDLE_NONE");
        arrayList.add("AUDIO_PORT_HANDLE_NONE");
        arrayList.add("AUDIO_PATCH_HANDLE_NONE");
        if (i != 0) {
            arrayList.add("0x" + java.lang.Integer.toHexString(i & (-1)));
        }
        return java.lang.String.join(" | ", arrayList);
    }
}
