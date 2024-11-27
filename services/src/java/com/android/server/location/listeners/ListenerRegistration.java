package com.android.server.location.listeners;

/* loaded from: classes2.dex */
public class ListenerRegistration<TListener> implements com.android.internal.listeners.ListenerExecutor {
    private boolean mActive;
    private final java.util.concurrent.Executor mExecutor;

    @android.annotation.Nullable
    private volatile TListener mListener;

    protected ListenerRegistration(java.util.concurrent.Executor executor, TListener tlistener) {
        java.util.Objects.requireNonNull(executor);
        this.mExecutor = executor;
        this.mActive = false;
        java.util.Objects.requireNonNull(tlistener);
        this.mListener = tlistener;
    }

    protected java.lang.String getTag() {
        return "ListenerRegistration";
    }

    protected final java.util.concurrent.Executor getExecutor() {
        return this.mExecutor;
    }

    protected void onRegister(java.lang.Object obj) {
    }

    protected void onUnregister() {
    }

    protected void onActive() {
    }

    protected void onInactive() {
    }

    public final boolean isActive() {
        return this.mActive;
    }

    final boolean setActive(boolean z) {
        if (z != this.mActive) {
            this.mActive = z;
            return true;
        }
        return false;
    }

    public final boolean isRegistered() {
        return this.mListener != null;
    }

    final void unregisterInternal() {
        this.mListener = null;
        onListenerUnregister();
    }

    protected void onListenerUnregister() {
    }

    protected void onOperationFailure(com.android.internal.listeners.ListenerExecutor.ListenerOperation<TListener> listenerOperation, java.lang.Exception exc) {
        throw new java.lang.AssertionError(exc);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ java.lang.Object lambda$executeOperation$0() {
        return this.mListener;
    }

    protected final void executeOperation(@android.annotation.Nullable com.android.internal.listeners.ListenerExecutor.ListenerOperation<TListener> listenerOperation) {
        executeSafely(this.mExecutor, new java.util.function.Supplier() { // from class: com.android.server.location.listeners.ListenerRegistration$$ExternalSyntheticLambda0
            @Override // java.util.function.Supplier
            public final java.lang.Object get() {
                java.lang.Object lambda$executeOperation$0;
                lambda$executeOperation$0 = com.android.server.location.listeners.ListenerRegistration.this.lambda$executeOperation$0();
                return lambda$executeOperation$0;
            }
        }, listenerOperation, new com.android.internal.listeners.ListenerExecutor.FailureCallback() { // from class: com.android.server.location.listeners.ListenerRegistration$$ExternalSyntheticLambda1
            public final void onFailure(com.android.internal.listeners.ListenerExecutor.ListenerOperation listenerOperation2, java.lang.Exception exc) {
                com.android.server.location.listeners.ListenerRegistration.this.onOperationFailure(listenerOperation2, exc);
            }
        });
    }

    public java.lang.String toString() {
        return "[]";
    }

    public final boolean equals(java.lang.Object obj) {
        return this == obj;
    }

    public final int hashCode() {
        return super.hashCode();
    }
}
