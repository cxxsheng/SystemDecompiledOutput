package android.os;

/* loaded from: classes3.dex */
public class HwRemoteBinder implements android.os.IHwBinder {
    private static final java.lang.String TAG = "HwRemoteBinder";
    private static final libcore.util.NativeAllocationRegistry sNativeRegistry = new libcore.util.NativeAllocationRegistry(android.os.HwRemoteBinder.class.getClassLoader(), native_init(), 128);
    private long mNativeContext;

    private static final native long native_init();

    private final native void native_setup_empty();

    public final native boolean equals(java.lang.Object obj);

    public final native int hashCode();

    @Override // android.os.IHwBinder, android.hardware.cas.V1_0.ICas, android.internal.hidl.base.V1_0.IBase
    public native boolean linkToDeath(android.os.IHwBinder.DeathRecipient deathRecipient, long j);

    @Override // android.os.IHwBinder
    public final native void transact(int i, android.os.HwParcel hwParcel, android.os.HwParcel hwParcel2, int i2) throws android.os.RemoteException;

    @Override // android.os.IHwBinder, android.hardware.cas.V1_0.ICas, android.internal.hidl.base.V1_0.IBase
    public native boolean unlinkToDeath(android.os.IHwBinder.DeathRecipient deathRecipient);

    public HwRemoteBinder() {
        native_setup_empty();
        sNativeRegistry.registerNativeAllocation(this, this.mNativeContext);
    }

    @Override // android.os.IHwBinder
    public android.os.IHwInterface queryLocalInterface(java.lang.String str) {
        return null;
    }

    private static final void sendDeathNotice(android.os.IHwBinder.DeathRecipient deathRecipient, long j) {
        deathRecipient.serviceDied(j);
    }
}
