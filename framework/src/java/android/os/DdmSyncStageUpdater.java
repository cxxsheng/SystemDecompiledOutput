package android.os;

/* loaded from: classes3.dex */
public class DdmSyncStageUpdater {
    private static final int CHUNK_STAGE = org.apache.harmony.dalvik.ddmc.ChunkHandler.type("STAG");
    private static final java.lang.String TAG = "DdmSyncStageUpdater";

    public synchronized void next(android.os.DdmSyncState.Stage stage) {
        try {
            android.os.DdmSyncState.next(stage);
            java.nio.ByteBuffer allocate = java.nio.ByteBuffer.allocate(4);
            allocate.putInt(stage.toInt());
            org.apache.harmony.dalvik.ddmc.DdmServer.sendChunk(new org.apache.harmony.dalvik.ddmc.Chunk(CHUNK_STAGE, allocate));
        } catch (java.lang.Exception e) {
            android.util.Slog.w(TAG, "Unable to go to next stage" + stage, e);
        }
    }
}
