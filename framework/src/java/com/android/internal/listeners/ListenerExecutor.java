package com.android.internal.listeners;

/* loaded from: classes4.dex */
public interface ListenerExecutor {

    public interface FailureCallback<TListenerOperation extends com.android.internal.listeners.ListenerExecutor.ListenerOperation<?>> {
        void onFailure(TListenerOperation tlisteneroperation, java.lang.Exception exc);
    }

    public interface ListenerOperation<TListener> {
        void operate(TListener tlistener) throws java.lang.Exception;

        default void onPreExecute() {
        }

        default void onFailure(java.lang.Exception exc) {
        }

        default void onPostExecute(boolean z) {
        }

        default void onComplete(boolean z) {
        }
    }

    default <TListener> void executeSafely(java.util.concurrent.Executor executor, java.util.function.Supplier<TListener> supplier, com.android.internal.listeners.ListenerExecutor.ListenerOperation<TListener> listenerOperation) {
        executeSafely(executor, supplier, listenerOperation, null);
    }

    default <TListener, TListenerOperation extends com.android.internal.listeners.ListenerExecutor.ListenerOperation<TListener>> void executeSafely(java.util.concurrent.Executor executor, final java.util.function.Supplier<TListener> supplier, final TListenerOperation tlisteneroperation, final com.android.internal.listeners.ListenerExecutor.FailureCallback<TListenerOperation> failureCallback) {
        final TListener tlistener;
        boolean z;
        if (tlisteneroperation == null || (tlistener = supplier.get()) == null) {
            return;
        }
        try {
            tlisteneroperation.onPreExecute();
            z = true;
        } catch (java.lang.Throwable th) {
            th = th;
            z = false;
        }
        try {
            executor.execute(new java.lang.Runnable() { // from class: com.android.internal.listeners.ListenerExecutor$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.internal.listeners.ListenerExecutor.lambda$executeSafely$0(tlistener, supplier, tlisteneroperation, failureCallback);
                }
            });
        } catch (java.lang.Throwable th2) {
            th = th2;
            if (z) {
                tlisteneroperation.onPostExecute(false);
            }
            tlisteneroperation.onComplete(false);
            throw th;
        }
    }

    static /* synthetic */ void lambda$executeSafely$0(java.lang.Object obj, java.util.function.Supplier supplier, com.android.internal.listeners.ListenerExecutor.ListenerOperation listenerOperation, com.android.internal.listeners.ListenerExecutor.FailureCallback failureCallback) {
        boolean z = false;
        try {
            try {
                if (obj == supplier.get()) {
                    listenerOperation.operate(obj);
                    z = true;
                }
            } catch (java.lang.Exception e) {
                if (e instanceof java.lang.RuntimeException) {
                    throw ((java.lang.RuntimeException) e);
                }
                listenerOperation.onFailure(e);
                if (failureCallback != null) {
                    failureCallback.onFailure(listenerOperation, e);
                }
            }
        } finally {
            listenerOperation.onPostExecute(false);
            listenerOperation.onComplete(false);
        }
    }
}
