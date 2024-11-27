package com.android.server.location.listeners;

/* loaded from: classes2.dex */
public abstract class RemovableListenerRegistration<TKey, TListener> extends com.android.server.location.listeners.ListenerRegistration<TListener> {

    @android.annotation.Nullable
    private volatile TKey mKey;
    private final java.util.concurrent.atomic.AtomicBoolean mRemoved;

    protected abstract com.android.server.location.listeners.ListenerMultiplexer<TKey, ? super TListener, ?, ?> getOwner();

    protected RemovableListenerRegistration(java.util.concurrent.Executor executor, TListener tlistener) {
        super(executor, tlistener);
        this.mRemoved = new java.util.concurrent.atomic.AtomicBoolean(false);
    }

    protected final TKey getKey() {
        TKey tkey = this.mKey;
        java.util.Objects.requireNonNull(tkey);
        return tkey;
    }

    public final void remove() {
        remove(true);
    }

    public final void remove(boolean z) {
        final TKey tkey = this.mKey;
        if (tkey != null && !this.mRemoved.getAndSet(true)) {
            onRemove(z);
            if (z) {
                getOwner().removeRegistration(tkey, this);
            } else {
                executeOperation(new com.android.internal.listeners.ListenerExecutor.ListenerOperation() { // from class: com.android.server.location.listeners.RemovableListenerRegistration$$ExternalSyntheticLambda0
                    public final void operate(java.lang.Object obj) {
                        com.android.server.location.listeners.RemovableListenerRegistration.this.lambda$remove$0(tkey, obj);
                    }
                });
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public /* synthetic */ void lambda$remove$0(java.lang.Object obj, java.lang.Object obj2) throws java.lang.Exception {
        getOwner().removeRegistration(obj, this);
    }

    protected void onRemove(boolean z) {
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.android.server.location.listeners.ListenerRegistration
    protected final void onRegister(java.lang.Object obj) {
        super.onRegister(obj);
        java.util.Objects.requireNonNull(obj);
        this.mKey = obj;
        onRegister();
    }

    protected void onRegister() {
    }

    @Override // com.android.server.location.listeners.ListenerRegistration
    protected void onUnregister() {
        this.mKey = null;
        super.onUnregister();
    }
}
