package android.graphics;

/* loaded from: classes.dex */
public class ColorFilter {
    private long mNativeInstance;

    /* JADX INFO: Access modifiers changed from: private */
    public static native long nativeGetFinalizer();

    private static class NoImagePreloadHolder {
        public static final libcore.util.NativeAllocationRegistry sRegistry = libcore.util.NativeAllocationRegistry.createMalloced(android.graphics.ColorFilter.class.getClassLoader(), android.graphics.ColorFilter.nativeGetFinalizer());

        private NoImagePreloadHolder() {
        }
    }

    @java.lang.Deprecated
    public ColorFilter() {
    }

    long createNativeInstance() {
        return 0L;
    }

    public final synchronized long getNativeInstance() {
        if (this.mNativeInstance == 0) {
            this.mNativeInstance = createNativeInstance();
            if (this.mNativeInstance != 0) {
                android.graphics.ColorFilter.NoImagePreloadHolder.sRegistry.registerNativeAllocation(this, this.mNativeInstance);
            }
        }
        return this.mNativeInstance;
    }
}
