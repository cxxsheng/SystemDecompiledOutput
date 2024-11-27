package android.os;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public abstract class HwBinder implements android.os.IHwBinder {
    private static final java.lang.String TAG = "HwBinder";
    private static final libcore.util.NativeAllocationRegistry sNativeRegistry = new libcore.util.NativeAllocationRegistry(android.os.HwBinder.class.getClassLoader(), native_init(), 128);
    private long mNativeContext;

    public static final native void configureRpcThreadpool(long j, boolean z);

    public static final native android.os.IHwBinder getService(java.lang.String str, java.lang.String str2, boolean z) throws android.os.RemoteException, java.util.NoSuchElementException;

    public static final native void joinRpcThreadpool();

    private static final native long native_init();

    private static native void native_report_sysprop_change();

    private final native void native_setup();

    public static final native void setTrebleTestingOverride(boolean z);

    public abstract void onTransact(int i, android.os.HwParcel hwParcel, android.os.HwParcel hwParcel2, int i2) throws android.os.RemoteException;

    public final native void registerService(java.lang.String str) throws android.os.RemoteException;

    @Override // android.os.IHwBinder
    public final native void transact(int i, android.os.HwParcel hwParcel, android.os.HwParcel hwParcel2, int i2) throws android.os.RemoteException;

    public HwBinder() {
        native_setup();
        sNativeRegistry.registerNativeAllocation(this, this.mNativeContext);
    }

    public static final android.os.IHwBinder getService(java.lang.String str, java.lang.String str2) throws android.os.RemoteException, java.util.NoSuchElementException {
        if (!android.os.HidlSupport.isHidlSupported() && (str.equals(android.internal.hidl.manager.V1_0.IServiceManager.kInterfaceName) || str.equals(android.internal.hidl.manager.V1_1.IServiceManager.kInterfaceName) || str.equals(android.internal.hidl.manager.V1_2.IServiceManager.kInterfaceName))) {
            android.util.Log.i(TAG, "Replacing Java hwservicemanager with a fake HwNoService because HIDL is not supported on this device.");
            return new android.os.HwNoService();
        }
        return getService(str, str2, false);
    }

    public static void enableInstrumentation() {
        native_report_sysprop_change();
    }

    public static void reportSyspropChanged() {
        native_report_sysprop_change();
    }
}
