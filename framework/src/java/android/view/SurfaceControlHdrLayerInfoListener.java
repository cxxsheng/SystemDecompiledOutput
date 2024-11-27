package android.view;

/* loaded from: classes4.dex */
public abstract class SurfaceControlHdrLayerInfoListener {
    private static final libcore.util.NativeAllocationRegistry sRegistry = libcore.util.NativeAllocationRegistry.createMalloced(android.view.SurfaceControlHdrLayerInfoListener.class.getClassLoader(), nGetDestructor());
    private android.util.ArrayMap<android.os.IBinder, java.lang.Runnable> mRegisteredListeners = new android.util.ArrayMap<>();

    private static native long nGetDestructor();

    private native long nRegister(android.os.IBinder iBinder);

    public abstract void onHdrInfoChanged(android.os.IBinder iBinder, int i, int i2, int i3, int i4, float f);

    public void register(android.os.IBinder iBinder) {
        java.util.Objects.requireNonNull(iBinder);
        synchronized (this) {
            if (this.mRegisteredListeners.containsKey(iBinder)) {
                return;
            }
            this.mRegisteredListeners.put(iBinder, sRegistry.registerNativeAllocation(this, nRegister(iBinder)));
        }
    }

    public void unregister(android.os.IBinder iBinder) {
        java.lang.Runnable remove;
        java.util.Objects.requireNonNull(iBinder);
        synchronized (this) {
            remove = this.mRegisteredListeners.remove(iBinder);
        }
        if (remove != null) {
            remove.run();
        }
    }

    public void unregisterAll() {
        android.util.ArrayMap<android.os.IBinder, java.lang.Runnable> arrayMap;
        synchronized (this) {
            arrayMap = this.mRegisteredListeners;
            this.mRegisteredListeners = new android.util.ArrayMap<>();
        }
        java.util.Iterator<java.lang.Runnable> it = arrayMap.values().iterator();
        while (it.hasNext()) {
            it.next().run();
        }
    }
}
