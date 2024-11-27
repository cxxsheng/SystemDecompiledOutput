package android.ddm;

/* loaded from: classes.dex */
public class DdmRegister {
    private DdmRegister() {
    }

    public static void registerHandlers() {
        android.ddm.DdmHandleHello.register();
        android.ddm.DdmHandleHeap.register();
        android.ddm.DdmHandleNativeHeap.register();
        android.ddm.DdmHandleProfiling.register();
        android.ddm.DdmHandleExit.register();
        android.ddm.DdmHandleViewDebug.register();
        org.apache.harmony.dalvik.ddmc.DdmServer.registrationComplete();
    }
}
