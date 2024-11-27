package android.ddm;

/* loaded from: classes.dex */
public class DdmHandleExit extends android.ddm.DdmHandle {
    public static final int CHUNK_EXIT = org.apache.harmony.dalvik.ddmc.ChunkHandler.type("EXIT");
    private static android.ddm.DdmHandleExit mInstance = new android.ddm.DdmHandleExit();

    private DdmHandleExit() {
    }

    public static void register() {
        org.apache.harmony.dalvik.ddmc.DdmServer.registerHandler(CHUNK_EXIT, mInstance);
    }

    public void onConnected() {
    }

    public void onDisconnected() {
    }

    public org.apache.harmony.dalvik.ddmc.Chunk handleChunk(org.apache.harmony.dalvik.ddmc.Chunk chunk) {
        java.lang.Runtime.getRuntime().halt(wrapChunk(chunk).getInt());
        return null;
    }
}
