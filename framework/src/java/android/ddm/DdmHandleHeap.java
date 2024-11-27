package android.ddm;

/* loaded from: classes.dex */
public class DdmHandleHeap extends android.ddm.DdmHandle {
    public static final int CHUNK_HPGC = org.apache.harmony.dalvik.ddmc.ChunkHandler.type("HPGC");
    private static android.ddm.DdmHandleHeap mInstance = new android.ddm.DdmHandleHeap();

    private DdmHandleHeap() {
    }

    public static void register() {
        org.apache.harmony.dalvik.ddmc.DdmServer.registerHandler(CHUNK_HPGC, mInstance);
    }

    public void onConnected() {
    }

    public void onDisconnected() {
    }

    public org.apache.harmony.dalvik.ddmc.Chunk handleChunk(org.apache.harmony.dalvik.ddmc.Chunk chunk) {
        int i = chunk.type;
        if (i == CHUNK_HPGC) {
            return handleHPGC(chunk);
        }
        throw new java.lang.RuntimeException("Unknown packet " + name(i));
    }

    private org.apache.harmony.dalvik.ddmc.Chunk handleHPGC(org.apache.harmony.dalvik.ddmc.Chunk chunk) {
        java.lang.Runtime.getRuntime().gc();
        return null;
    }
}
