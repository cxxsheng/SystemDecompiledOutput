package com.android.server.location.injector;

/* loaded from: classes2.dex */
public abstract class PackageResetHelper {
    private final java.util.concurrent.CopyOnWriteArrayList<com.android.server.location.injector.PackageResetHelper.Responder> mResponders = new java.util.concurrent.CopyOnWriteArrayList<>();

    public interface Responder {
        boolean isResetableForPackage(java.lang.String str);

        void onPackageReset(java.lang.String str);
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    protected abstract void onRegister();

    @com.android.internal.annotations.GuardedBy({"this"})
    protected abstract void onUnregister();

    public synchronized void register(com.android.server.location.injector.PackageResetHelper.Responder responder) {
        boolean isEmpty = this.mResponders.isEmpty();
        this.mResponders.add(responder);
        if (isEmpty) {
            onRegister();
        }
    }

    public synchronized void unregister(com.android.server.location.injector.PackageResetHelper.Responder responder) {
        this.mResponders.remove(responder);
        if (this.mResponders.isEmpty()) {
            onUnregister();
        }
    }

    protected final void notifyPackageReset(java.lang.String str) {
        if (com.android.server.location.LocationManagerService.D) {
            android.util.Log.d(com.android.server.location.LocationManagerService.TAG, "package " + str + " reset");
        }
        java.util.Iterator<com.android.server.location.injector.PackageResetHelper.Responder> it = this.mResponders.iterator();
        while (it.hasNext()) {
            it.next().onPackageReset(str);
        }
    }

    protected final boolean queryResetableForPackage(java.lang.String str) {
        java.util.Iterator<com.android.server.location.injector.PackageResetHelper.Responder> it = this.mResponders.iterator();
        while (it.hasNext()) {
            if (it.next().isResetableForPackage(str)) {
                return true;
            }
        }
        return false;
    }
}
