package android.ddm;

/* loaded from: classes.dex */
public class DdmHandleHello extends android.ddm.DdmHandle {
    private static final int CLIENT_PROTOCOL_VERSION = 1;
    public static final int CHUNK_HELO = org.apache.harmony.dalvik.ddmc.ChunkHandler.type("HELO");
    public static final int CHUNK_WAIT = org.apache.harmony.dalvik.ddmc.ChunkHandler.type("WAIT");
    public static final int CHUNK_FEAT = org.apache.harmony.dalvik.ddmc.ChunkHandler.type("FEAT");
    private static android.ddm.DdmHandleHello mInstance = new android.ddm.DdmHandleHello();
    private static final java.lang.String[] FRAMEWORK_FEATURES = {"opengl-tracing", "view-hierarchy", "support_boot_stages"};

    private DdmHandleHello() {
    }

    public static void register() {
        org.apache.harmony.dalvik.ddmc.DdmServer.registerHandler(CHUNK_HELO, mInstance);
        org.apache.harmony.dalvik.ddmc.DdmServer.registerHandler(CHUNK_FEAT, mInstance);
    }

    public void onConnected() {
    }

    public void onDisconnected() {
    }

    public org.apache.harmony.dalvik.ddmc.Chunk handleChunk(org.apache.harmony.dalvik.ddmc.Chunk chunk) {
        int i = chunk.type;
        if (i == CHUNK_HELO) {
            return handleHELO(chunk);
        }
        if (i == CHUNK_FEAT) {
            return handleFEAT(chunk);
        }
        throw new java.lang.RuntimeException("Unknown packet " + name(i));
    }

    private org.apache.harmony.dalvik.ddmc.Chunk handleHELO(org.apache.harmony.dalvik.ddmc.Chunk chunk) {
        wrapChunk(chunk).getInt();
        java.lang.String str = java.lang.System.getProperty("java.vm.name", "?") + " v" + java.lang.System.getProperty("java.vm.version", "?");
        android.ddm.DdmHandleAppName.Names names = android.ddm.DdmHandleAppName.getNames();
        java.lang.String appName = names.getAppName();
        java.lang.String pkgName = names.getPkgName();
        dalvik.system.VMRuntime runtime = dalvik.system.VMRuntime.getRuntime();
        java.lang.String str2 = runtime.is64Bit() ? "64-bit" : "32-bit";
        java.lang.String vmInstructionSet = runtime.vmInstructionSet();
        if (vmInstructionSet != null && vmInstructionSet.length() > 0) {
            str2 = str2 + " (" + vmInstructionSet + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END;
        }
        java.lang.String str3 = "CheckJNI=" + (runtime.isCheckJniEnabled() ? "true" : "false");
        boolean isNativeDebuggable = runtime.isNativeDebuggable();
        java.nio.ByteBuffer allocate = java.nio.ByteBuffer.allocate((str.length() * 2) + 32 + (appName.length() * 2) + (str2.length() * 2) + (str3.length() * 2) + 1 + (pkgName.length() * 2) + 4);
        allocate.order(org.apache.harmony.dalvik.ddmc.ChunkHandler.CHUNK_ORDER);
        allocate.putInt(1);
        allocate.putInt(android.os.Process.myPid());
        allocate.putInt(str.length());
        allocate.putInt(appName.length());
        putString(allocate, str);
        putString(allocate, appName);
        allocate.putInt(android.os.UserHandle.myUserId());
        allocate.putInt(str2.length());
        putString(allocate, str2);
        allocate.putInt(str3.length());
        putString(allocate, str3);
        allocate.put(isNativeDebuggable ? (byte) 1 : (byte) 0);
        allocate.putInt(pkgName.length());
        putString(allocate, pkgName);
        allocate.putInt(android.os.DdmSyncState.getStage().toInt());
        org.apache.harmony.dalvik.ddmc.Chunk chunk2 = new org.apache.harmony.dalvik.ddmc.Chunk(CHUNK_HELO, allocate);
        if (android.os.Debug.waitingForDebugger()) {
            sendWAIT(0);
        }
        return chunk2;
    }

    private org.apache.harmony.dalvik.ddmc.Chunk handleFEAT(org.apache.harmony.dalvik.ddmc.Chunk chunk) {
        java.lang.String[] vmFeatureList = android.os.Debug.getVmFeatureList();
        int length = ((vmFeatureList.length + FRAMEWORK_FEATURES.length) * 4) + 4;
        for (int length2 = vmFeatureList.length - 1; length2 >= 0; length2--) {
            length += vmFeatureList[length2].length() * 2;
        }
        for (int length3 = FRAMEWORK_FEATURES.length - 1; length3 >= 0; length3--) {
            length += FRAMEWORK_FEATURES[length3].length() * 2;
        }
        java.nio.ByteBuffer allocate = java.nio.ByteBuffer.allocate(length);
        allocate.order(org.apache.harmony.dalvik.ddmc.ChunkHandler.CHUNK_ORDER);
        allocate.putInt(vmFeatureList.length + FRAMEWORK_FEATURES.length);
        for (int length4 = vmFeatureList.length - 1; length4 >= 0; length4--) {
            allocate.putInt(vmFeatureList[length4].length());
            putString(allocate, vmFeatureList[length4]);
        }
        for (int length5 = FRAMEWORK_FEATURES.length - 1; length5 >= 0; length5--) {
            allocate.putInt(FRAMEWORK_FEATURES[length5].length());
            putString(allocate, FRAMEWORK_FEATURES[length5]);
        }
        return new org.apache.harmony.dalvik.ddmc.Chunk(CHUNK_FEAT, allocate);
    }

    public static void sendWAIT(int i) {
        org.apache.harmony.dalvik.ddmc.DdmServer.sendChunk(new org.apache.harmony.dalvik.ddmc.Chunk(CHUNK_WAIT, new byte[]{(byte) i}, 0, 1));
    }
}
