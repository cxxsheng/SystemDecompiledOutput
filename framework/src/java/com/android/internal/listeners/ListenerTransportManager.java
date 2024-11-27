package com.android.internal.listeners;

/* loaded from: classes4.dex */
public abstract class ListenerTransportManager<TTransport extends com.android.internal.listeners.ListenerTransport<?>> {
    private final java.util.Map<java.lang.Object, java.lang.ref.WeakReference<TTransport>> mRegistrations;

    protected abstract void registerTransport(TTransport ttransport) throws android.os.RemoteException;

    protected abstract void unregisterTransport(TTransport ttransport) throws android.os.RemoteException;

    protected ListenerTransportManager(boolean z) {
        if (z) {
            this.mRegistrations = new java.util.WeakHashMap();
        } else {
            this.mRegistrations = new android.util.ArrayMap();
        }
    }

    public final void addListener(java.lang.Object obj, TTransport ttransport) {
        TTransport ttransport2;
        try {
            synchronized (this.mRegistrations) {
                java.lang.ref.WeakReference<TTransport> weakReference = this.mRegistrations.get(obj);
                if (weakReference != null) {
                    ttransport2 = weakReference.get();
                } else {
                    ttransport2 = null;
                }
                if (ttransport2 == null) {
                    registerTransport(ttransport);
                } else {
                    registerTransport(ttransport, ttransport2);
                    ttransport2.unregister();
                }
                this.mRegistrations.put(obj, new java.lang.ref.WeakReference<>(ttransport));
            }
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public final void removeListener(java.lang.Object obj) {
        TTransport ttransport;
        try {
            synchronized (this.mRegistrations) {
                java.lang.ref.WeakReference<TTransport> remove = this.mRegistrations.remove(obj);
                if (remove != null && (ttransport = remove.get()) != null) {
                    ttransport.unregister();
                    unregisterTransport(ttransport);
                }
            }
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    protected void registerTransport(TTransport ttransport, TTransport ttransport2) throws android.os.RemoteException {
        registerTransport(ttransport);
        try {
            unregisterTransport(ttransport2);
        } catch (android.os.RemoteException e) {
            try {
                unregisterTransport(ttransport);
            } catch (android.os.RemoteException e2) {
                e.addSuppressed(e2);
            }
            throw e;
        }
    }
}
