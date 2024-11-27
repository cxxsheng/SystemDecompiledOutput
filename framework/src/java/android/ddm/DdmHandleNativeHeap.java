package android.ddm;

/* loaded from: classes.dex */
public class DdmHandleNativeHeap extends android.ddm.DdmHandle {
    public static final int CHUNK_NHGT = org.apache.harmony.dalvik.ddmc.ChunkHandler.type("NHGT");
    private static android.ddm.DdmHandleNativeHeap mInstance = new android.ddm.DdmHandleNativeHeap();

    private native byte[] getLeakInfo();

    private DdmHandleNativeHeap() {
    }

    public static void register() {
        org.apache.harmony.dalvik.ddmc.DdmServer.registerHandler(CHUNK_NHGT, mInstance);
    }

    public void onConnected() {
    }

    public void onDisconnected() {
    }

    public org.apache.harmony.dalvik.ddmc.Chunk handleChunk(org.apache.harmony.dalvik.ddmc.Chunk chunk) {
        android.util.Log.i("ddm-nativeheap", "Handling " + name(chunk.type) + " chunk");
        int i = chunk.type;
        if (i == CHUNK_NHGT) {
            return handleNHGT(chunk);
        }
        throw new java.lang.RuntimeException("Unknown packet " + name(i));
    }

    private org.apache.harmony.dalvik.ddmc.Chunk handleNHGT(org.apache.harmony.dalvik.ddmc.Chunk chunk) {
        byte[] leakInfo = getLeakInfo();
        if (leakInfo != null) {
            android.util.Log.i("ddm-nativeheap", "Sending " + leakInfo.length + " bytes");
            return new org.apache.harmony.dalvik.ddmc.Chunk(org.apache.harmony.dalvik.ddmc.ChunkHandler.type("NHGT"), leakInfo, 0, leakInfo.length);
        }
        return createFailChunk(1, "Something went wrong");
    }
}
