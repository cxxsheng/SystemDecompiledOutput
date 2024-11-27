package android.ddm;

/* loaded from: classes.dex */
public class DdmHandleProfiling extends android.ddm.DdmHandle {
    private static final boolean DEBUG = false;
    public static final int CHUNK_MPRS = org.apache.harmony.dalvik.ddmc.ChunkHandler.type("MPRS");
    public static final int CHUNK_MPRE = org.apache.harmony.dalvik.ddmc.ChunkHandler.type("MPRE");
    public static final int CHUNK_MPSS = org.apache.harmony.dalvik.ddmc.ChunkHandler.type("MPSS");
    public static final int CHUNK_MPSE = org.apache.harmony.dalvik.ddmc.ChunkHandler.type("MPSE");
    public static final int CHUNK_MPRQ = org.apache.harmony.dalvik.ddmc.ChunkHandler.type("MPRQ");
    public static final int CHUNK_SPSS = org.apache.harmony.dalvik.ddmc.ChunkHandler.type("SPSS");
    public static final int CHUNK_SPSE = org.apache.harmony.dalvik.ddmc.ChunkHandler.type("SPSE");
    private static android.ddm.DdmHandleProfiling mInstance = new android.ddm.DdmHandleProfiling();

    private DdmHandleProfiling() {
    }

    public static void register() {
        org.apache.harmony.dalvik.ddmc.DdmServer.registerHandler(CHUNK_MPRS, mInstance);
        org.apache.harmony.dalvik.ddmc.DdmServer.registerHandler(CHUNK_MPRE, mInstance);
        org.apache.harmony.dalvik.ddmc.DdmServer.registerHandler(CHUNK_MPSS, mInstance);
        org.apache.harmony.dalvik.ddmc.DdmServer.registerHandler(CHUNK_MPSE, mInstance);
        org.apache.harmony.dalvik.ddmc.DdmServer.registerHandler(CHUNK_MPRQ, mInstance);
        org.apache.harmony.dalvik.ddmc.DdmServer.registerHandler(CHUNK_SPSS, mInstance);
        org.apache.harmony.dalvik.ddmc.DdmServer.registerHandler(CHUNK_SPSE, mInstance);
    }

    public void onConnected() {
    }

    public void onDisconnected() {
    }

    public org.apache.harmony.dalvik.ddmc.Chunk handleChunk(org.apache.harmony.dalvik.ddmc.Chunk chunk) {
        int i = chunk.type;
        if (i == CHUNK_MPRS) {
            return handleMPRS(chunk);
        }
        if (i == CHUNK_MPRE) {
            return handleMPRE(chunk);
        }
        if (i == CHUNK_MPSS) {
            return handleMPSS(chunk);
        }
        if (i == CHUNK_MPSE) {
            return handleMPSEOrSPSE(chunk, "Method");
        }
        if (i == CHUNK_MPRQ) {
            return handleMPRQ(chunk);
        }
        if (i == CHUNK_SPSS) {
            return handleSPSS(chunk);
        }
        if (i == CHUNK_SPSE) {
            return handleMPSEOrSPSE(chunk, "Sample");
        }
        throw new java.lang.RuntimeException("Unknown packet " + name(i));
    }

    private org.apache.harmony.dalvik.ddmc.Chunk handleMPRS(org.apache.harmony.dalvik.ddmc.Chunk chunk) {
        java.nio.ByteBuffer wrapChunk = wrapChunk(chunk);
        try {
            android.os.Debug.startMethodTracing(getString(wrapChunk, wrapChunk.getInt()), wrapChunk.getInt(), wrapChunk.getInt());
            return null;
        } catch (java.lang.RuntimeException e) {
            return createFailChunk(1, e.getMessage());
        }
    }

    private org.apache.harmony.dalvik.ddmc.Chunk handleMPRE(org.apache.harmony.dalvik.ddmc.Chunk chunk) {
        byte b;
        try {
            android.os.Debug.stopMethodTracing();
            b = 0;
        } catch (java.lang.RuntimeException e) {
            android.util.Log.w("ddm-heap", "Method profiling end failed: " + e.getMessage());
            b = 1;
        }
        return new org.apache.harmony.dalvik.ddmc.Chunk(CHUNK_MPRE, new byte[]{b}, 0, 1);
    }

    private org.apache.harmony.dalvik.ddmc.Chunk handleMPSS(org.apache.harmony.dalvik.ddmc.Chunk chunk) {
        java.nio.ByteBuffer wrapChunk = wrapChunk(chunk);
        try {
            android.os.Debug.startMethodTracingDdms(wrapChunk.getInt(), wrapChunk.getInt(), false, 0);
            return null;
        } catch (java.lang.RuntimeException e) {
            return createFailChunk(1, e.getMessage());
        }
    }

    private org.apache.harmony.dalvik.ddmc.Chunk handleMPSEOrSPSE(org.apache.harmony.dalvik.ddmc.Chunk chunk, java.lang.String str) {
        try {
            android.os.Debug.stopMethodTracing();
            return null;
        } catch (java.lang.RuntimeException e) {
            android.util.Log.w("ddm-heap", str + " prof stream end failed: " + e.getMessage());
            return createFailChunk(1, e.getMessage());
        }
    }

    private org.apache.harmony.dalvik.ddmc.Chunk handleMPRQ(org.apache.harmony.dalvik.ddmc.Chunk chunk) {
        return new org.apache.harmony.dalvik.ddmc.Chunk(CHUNK_MPRQ, new byte[]{(byte) android.os.Debug.getMethodTracingMode()}, 0, 1);
    }

    private org.apache.harmony.dalvik.ddmc.Chunk handleSPSS(org.apache.harmony.dalvik.ddmc.Chunk chunk) {
        java.nio.ByteBuffer wrapChunk = wrapChunk(chunk);
        try {
            android.os.Debug.startMethodTracingDdms(wrapChunk.getInt(), wrapChunk.getInt(), true, wrapChunk.getInt());
            return null;
        } catch (java.lang.RuntimeException e) {
            return createFailChunk(1, e.getMessage());
        }
    }
}
