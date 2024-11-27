package android.ddm;

/* loaded from: classes.dex */
public class DdmHandleAppName extends android.ddm.DdmHandle {
    public static final int CHUNK_APNM = org.apache.harmony.dalvik.ddmc.ChunkHandler.type("APNM");
    private static android.ddm.DdmHandleAppName mInstance = new android.ddm.DdmHandleAppName();
    private static volatile android.ddm.DdmHandleAppName.Names sNames;

    static {
        java.lang.String str = "";
        sNames = new android.ddm.DdmHandleAppName.Names(str, str);
    }

    private DdmHandleAppName() {
    }

    public static void register() {
    }

    public void onConnected() {
    }

    public void onDisconnected() {
    }

    public org.apache.harmony.dalvik.ddmc.Chunk handleChunk(org.apache.harmony.dalvik.ddmc.Chunk chunk) {
        return null;
    }

    public static void setAppName(java.lang.String str, int i) {
        setAppName(str, str, i);
    }

    public static void setAppName(java.lang.String str, java.lang.String str2, int i) {
        if (str == null || str.isEmpty() || str2 == null || str2.isEmpty()) {
            return;
        }
        sNames = new android.ddm.DdmHandleAppName.Names(str, str2);
        sendAPNM(str, str2, i);
    }

    public static android.ddm.DdmHandleAppName.Names getNames() {
        return sNames;
    }

    private static void sendAPNM(java.lang.String str, java.lang.String str2, int i) {
        java.nio.ByteBuffer allocate = java.nio.ByteBuffer.allocate((str.length() * 2) + 4 + 4 + 4 + (str2.length() * 2));
        allocate.order(org.apache.harmony.dalvik.ddmc.ChunkHandler.CHUNK_ORDER);
        allocate.putInt(str.length());
        putString(allocate, str);
        allocate.putInt(i);
        allocate.putInt(str2.length());
        putString(allocate, str2);
        org.apache.harmony.dalvik.ddmc.DdmServer.sendChunk(new org.apache.harmony.dalvik.ddmc.Chunk(CHUNK_APNM, allocate));
    }

    static final class Names {
        private final java.lang.String mAppName;
        private final java.lang.String mPkgName;

        private Names(java.lang.String str, java.lang.String str2) {
            this.mAppName = str;
            this.mPkgName = str2;
        }

        public java.lang.String getAppName() {
            return this.mAppName;
        }

        public java.lang.String getPkgName() {
            return this.mPkgName;
        }
    }
}
