package com.android.server.storage;

/* loaded from: classes2.dex */
public class AppFuseBridge implements java.lang.Runnable {
    private static final java.lang.String APPFUSE_MOUNT_NAME_TEMPLATE = "/mnt/appfuse/%d_%d";
    public static final java.lang.String TAG = "AppFuseBridge";

    @com.android.internal.annotations.GuardedBy({"this"})
    private final android.util.SparseArray<com.android.server.storage.AppFuseBridge.MountScope> mScopes = new android.util.SparseArray<>();

    @com.android.internal.annotations.GuardedBy({"this"})
    private long mNativeLoop = native_new();

    private native int native_add_bridge(long j, int i, int i2);

    private native void native_delete(long j);

    private native void native_lock();

    private native long native_new();

    private native void native_start_loop(long j);

    private native void native_unlock();

    public android.os.ParcelFileDescriptor addBridge(com.android.server.storage.AppFuseBridge.MountScope mountScope) throws com.android.internal.os.FuseUnavailableMountException, com.android.server.AppFuseMountException {
        android.os.ParcelFileDescriptor adoptFd;
        native_lock();
        try {
            synchronized (this) {
                com.android.internal.util.Preconditions.checkArgument(this.mScopes.indexOfKey(mountScope.mountId) < 0);
                if (this.mNativeLoop == 0) {
                    throw new com.android.internal.os.FuseUnavailableMountException(mountScope.mountId);
                }
                int native_add_bridge = native_add_bridge(this.mNativeLoop, mountScope.mountId, mountScope.open().detachFd());
                if (native_add_bridge == -1) {
                    throw new com.android.internal.os.FuseUnavailableMountException(mountScope.mountId);
                }
                adoptFd = android.os.ParcelFileDescriptor.adoptFd(native_add_bridge);
                this.mScopes.put(mountScope.mountId, mountScope);
            }
            native_unlock();
            libcore.io.IoUtils.closeQuietly((java.lang.AutoCloseable) null);
            return adoptFd;
        } catch (java.lang.Throwable th) {
            native_unlock();
            libcore.io.IoUtils.closeQuietly(mountScope);
            throw th;
        }
    }

    @Override // java.lang.Runnable
    public void run() {
        native_start_loop(this.mNativeLoop);
        synchronized (this) {
            native_delete(this.mNativeLoop);
            this.mNativeLoop = 0L;
        }
    }

    public android.os.ParcelFileDescriptor openFile(int i, int i2, int i3) throws com.android.internal.os.FuseUnavailableMountException, java.lang.InterruptedException {
        com.android.server.storage.AppFuseBridge.MountScope mountScope;
        synchronized (this) {
            mountScope = this.mScopes.get(i);
            if (mountScope == null) {
                throw new com.android.internal.os.FuseUnavailableMountException(i);
            }
        }
        if (!mountScope.waitForMount()) {
            throw new com.android.internal.os.FuseUnavailableMountException(i);
        }
        try {
            return mountScope.openFile(i, i2, android.os.FileUtils.translateModePfdToPosix(i3));
        } catch (com.android.server.AppFuseMountException e) {
            throw new com.android.internal.os.FuseUnavailableMountException(i);
        }
    }

    private synchronized void onMount(int i) {
        com.android.server.storage.AppFuseBridge.MountScope mountScope = this.mScopes.get(i);
        if (mountScope != null) {
            mountScope.setMountResultLocked(true);
        }
    }

    private synchronized void onClosed(int i) {
        com.android.server.storage.AppFuseBridge.MountScope mountScope = this.mScopes.get(i);
        if (mountScope != null) {
            mountScope.setMountResultLocked(false);
            libcore.io.IoUtils.closeQuietly(mountScope);
            this.mScopes.remove(i);
        }
    }

    public static abstract class MountScope implements java.lang.AutoCloseable {
        public final int mountId;
        public final int uid;
        private final java.util.concurrent.CountDownLatch mMounted = new java.util.concurrent.CountDownLatch(1);
        private boolean mMountResult = false;

        public abstract android.os.ParcelFileDescriptor open() throws com.android.server.AppFuseMountException;

        public abstract android.os.ParcelFileDescriptor openFile(int i, int i2, int i3) throws com.android.server.AppFuseMountException;

        public MountScope(int i, int i2) {
            this.uid = i;
            this.mountId = i2;
        }

        @com.android.internal.annotations.GuardedBy({"AppFuseBridge.this"})
        void setMountResultLocked(boolean z) {
            if (this.mMounted.getCount() == 0) {
                return;
            }
            this.mMountResult = z;
            this.mMounted.countDown();
        }

        boolean waitForMount() throws java.lang.InterruptedException {
            this.mMounted.await();
            return this.mMountResult;
        }
    }
}
