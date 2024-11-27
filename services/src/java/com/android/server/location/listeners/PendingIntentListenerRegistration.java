package com.android.server.location.listeners;

/* loaded from: classes2.dex */
public abstract class PendingIntentListenerRegistration<TKey, TListener> extends com.android.server.location.listeners.RemovableListenerRegistration<TKey, TListener> implements android.app.PendingIntent.CancelListener {
    protected abstract android.app.PendingIntent getPendingIntentFromKey(TKey tkey);

    protected PendingIntentListenerRegistration(TListener tlistener) {
        super(com.android.internal.util.ConcurrentUtils.DIRECT_EXECUTOR, tlistener);
    }

    @Override // com.android.server.location.listeners.RemovableListenerRegistration
    protected void onRegister() {
        super.onRegister();
        if (!getPendingIntentFromKey(getKey()).addCancelListener(com.android.internal.util.ConcurrentUtils.DIRECT_EXECUTOR, this)) {
            remove();
        }
    }

    @Override // com.android.server.location.listeners.RemovableListenerRegistration, com.android.server.location.listeners.ListenerRegistration
    protected void onUnregister() {
        getPendingIntentFromKey(getKey()).removeCancelListener(this);
        super.onUnregister();
    }

    @Override // com.android.server.location.listeners.ListenerRegistration
    protected void onOperationFailure(com.android.internal.listeners.ListenerExecutor.ListenerOperation<TListener> listenerOperation, java.lang.Exception exc) {
        if (exc instanceof android.app.PendingIntent.CanceledException) {
            android.util.Log.w(getTag(), "registration " + this + " removed", exc);
            remove();
            return;
        }
        super.onOperationFailure(listenerOperation, exc);
    }

    public void onCanceled(android.app.PendingIntent pendingIntent) {
        if (android.util.Log.isLoggable(getTag(), 3)) {
            android.util.Log.d(getTag(), "pending intent registration " + this + " canceled");
        }
        remove();
    }
}
