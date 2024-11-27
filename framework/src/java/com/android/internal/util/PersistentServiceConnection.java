package com.android.internal.util;

/* loaded from: classes5.dex */
public class PersistentServiceConnection<T> extends com.android.internal.util.ObservableServiceConnection<T> {
    private final int mBaseReconnectDelayMs;
    private java.lang.Object mCancelToken;
    private final java.lang.Runnable mConnectRunnable;
    private final com.android.internal.util.ObservableServiceConnection.Callback<T> mConnectionCallback;
    private final android.os.Handler mHandler;
    private final com.android.internal.util.PersistentServiceConnection.Injector mInjector;
    private final java.lang.Object mLock;
    private final int mMaxReconnectAttempts;
    private final int mMinConnectionDurationMs;
    private int mReconnectAttempts;

    public PersistentServiceConnection(android.content.Context context, java.util.concurrent.Executor executor, android.os.Handler handler, com.android.internal.util.ObservableServiceConnection.ServiceTransformer<T> serviceTransformer, android.content.Intent intent, int i, int i2, int i3, int i4) {
        this(context, executor, handler, serviceTransformer, intent, i, i2, i3, i4, new com.android.internal.util.PersistentServiceConnection.Injector());
    }

    public PersistentServiceConnection(android.content.Context context, java.util.concurrent.Executor executor, android.os.Handler handler, com.android.internal.util.ObservableServiceConnection.ServiceTransformer<T> serviceTransformer, android.content.Intent intent, int i, int i2, int i3, int i4, com.android.internal.util.PersistentServiceConnection.Injector injector) {
        super(context, executor, serviceTransformer, intent, i);
        this.mConnectionCallback = new com.android.internal.util.ObservableServiceConnection.Callback<T>() { // from class: com.android.internal.util.PersistentServiceConnection.1
            private long mConnectedTime;

            @Override // com.android.internal.util.ObservableServiceConnection.Callback
            public void onConnected(com.android.internal.util.ObservableServiceConnection<T> observableServiceConnection, T t) {
                this.mConnectedTime = com.android.internal.util.PersistentServiceConnection.this.mInjector.uptimeMillis();
            }

            @Override // com.android.internal.util.ObservableServiceConnection.Callback
            public void onDisconnected(com.android.internal.util.ObservableServiceConnection<T> observableServiceConnection, int i5) {
                if (i5 == 4) {
                    return;
                }
                synchronized (com.android.internal.util.PersistentServiceConnection.this.mLock) {
                    if (com.android.internal.util.PersistentServiceConnection.this.mInjector.uptimeMillis() - this.mConnectedTime > com.android.internal.util.PersistentServiceConnection.this.mMinConnectionDurationMs) {
                        com.android.internal.util.PersistentServiceConnection.this.mReconnectAttempts = 0;
                        com.android.internal.util.PersistentServiceConnection.this.bindInternalLocked();
                    } else {
                        com.android.internal.util.PersistentServiceConnection.this.scheduleConnectionAttemptLocked();
                    }
                }
            }
        };
        this.mLock = new java.lang.Object();
        this.mConnectRunnable = new java.lang.Runnable() { // from class: com.android.internal.util.PersistentServiceConnection.2
            @Override // java.lang.Runnable
            public void run() {
                synchronized (com.android.internal.util.PersistentServiceConnection.this.mLock) {
                    com.android.internal.util.PersistentServiceConnection.this.mCancelToken = null;
                    com.android.internal.util.PersistentServiceConnection.this.bindInternalLocked();
                }
            }
        };
        this.mHandler = handler;
        this.mMinConnectionDurationMs = i2;
        this.mMaxReconnectAttempts = i3;
        this.mBaseReconnectDelayMs = i4;
        this.mInjector = injector;
    }

    @Override // com.android.internal.util.ObservableServiceConnection
    public boolean bind() {
        boolean bindInternalLocked;
        synchronized (this.mLock) {
            addCallback(this.mConnectionCallback);
            this.mReconnectAttempts = 0;
            bindInternalLocked = bindInternalLocked();
        }
        return bindInternalLocked;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean bindInternalLocked() {
        return super.bind();
    }

    @Override // com.android.internal.util.ObservableServiceConnection
    public void unbind() {
        synchronized (this.mLock) {
            removeCallback(this.mConnectionCallback);
            cancelPendingConnectionAttemptLocked();
            super.unbind();
        }
    }

    private void cancelPendingConnectionAttemptLocked() {
        if (this.mCancelToken != null) {
            this.mHandler.removeCallbacksAndMessages(this.mCancelToken);
            this.mCancelToken = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void scheduleConnectionAttemptLocked() {
        cancelPendingConnectionAttemptLocked();
        if (this.mReconnectAttempts >= this.mMaxReconnectAttempts) {
            return;
        }
        long scalb = (long) java.lang.Math.scalb(this.mBaseReconnectDelayMs, this.mReconnectAttempts);
        this.mCancelToken = new java.lang.Object();
        this.mHandler.postDelayed(this.mConnectRunnable, this.mCancelToken, scalb);
        this.mReconnectAttempts++;
    }

    public static class Injector {
        public long uptimeMillis() {
            return android.os.SystemClock.uptimeMillis();
        }
    }
}
