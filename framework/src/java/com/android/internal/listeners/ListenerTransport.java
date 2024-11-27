package com.android.internal.listeners;

/* loaded from: classes4.dex */
public interface ListenerTransport<TListener> {
    TListener getListener();

    void unregister();

    default void execute(java.util.concurrent.Executor executor, final java.util.function.Consumer<TListener> consumer) {
        java.util.Objects.requireNonNull(consumer);
        if (getListener() == null) {
            return;
        }
        executor.execute(new java.lang.Runnable() { // from class: com.android.internal.listeners.ListenerTransport$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                com.android.internal.listeners.ListenerTransport.this.lambda$execute$0(consumer);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* synthetic */ default void lambda$execute$0(java.util.function.Consumer consumer) {
        TListener listener = getListener();
        if (listener == null) {
            return;
        }
        consumer.accept(listener);
    }
}
