package com.android.internal.util;

/* loaded from: classes5.dex */
public class ObservableServiceConnection<T> implements android.content.ServiceConnection {
    public static final int DISCONNECT_REASON_BINDING_DIED = 3;
    public static final int DISCONNECT_REASON_DISCONNECTED = 2;
    public static final int DISCONNECT_REASON_NONE = 0;
    public static final int DISCONNECT_REASON_NULL_BINDING = 1;
    public static final int DISCONNECT_REASON_UNBIND = 4;
    private final android.content.Context mContext;
    private final java.util.concurrent.Executor mExecutor;
    private final int mFlags;
    private T mService;
    private final android.content.Intent mServiceIntent;
    private final com.android.internal.util.ObservableServiceConnection.ServiceTransformer<T> mTransformer;
    private final java.lang.Object mLock = new java.lang.Object();
    private boolean mBoundCalled = false;
    private int mLastDisconnectReason = 0;
    private final com.android.internal.util.CallbackRegistry<com.android.internal.util.ObservableServiceConnection.Callback<T>, com.android.internal.util.ObservableServiceConnection<T>, T> mCallbackRegistry = new com.android.internal.util.CallbackRegistry<>(new com.android.internal.util.ObservableServiceConnection.AnonymousClass1());

    public interface Callback<T> {
        void onConnected(com.android.internal.util.ObservableServiceConnection<T> observableServiceConnection, T t);

        void onDisconnected(com.android.internal.util.ObservableServiceConnection<T> observableServiceConnection, int i);
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface DisconnectReason {
    }

    public interface ServiceTransformer<T> {
        T convert(android.os.IBinder iBinder);
    }

    /* renamed from: com.android.internal.util.ObservableServiceConnection$1, reason: invalid class name */
    class AnonymousClass1 extends com.android.internal.util.CallbackRegistry.NotifierCallback<com.android.internal.util.ObservableServiceConnection.Callback<T>, com.android.internal.util.ObservableServiceConnection<T>, T> {
        AnonymousClass1() {
        }

        @Override // com.android.internal.util.CallbackRegistry.NotifierCallback
        public /* bridge */ /* synthetic */ void onNotifyCallback(java.lang.Object obj, java.lang.Object obj2, int i, java.lang.Object obj3) {
            onNotifyCallback((com.android.internal.util.ObservableServiceConnection.Callback<int>) obj, (com.android.internal.util.ObservableServiceConnection<int>) obj2, i, (int) obj3);
        }

        public void onNotifyCallback(final com.android.internal.util.ObservableServiceConnection.Callback<T> callback, final com.android.internal.util.ObservableServiceConnection<T> observableServiceConnection, final int i, final T t) {
            com.android.internal.util.ObservableServiceConnection.this.mExecutor.execute(new java.lang.Runnable() { // from class: com.android.internal.util.ObservableServiceConnection$1$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.internal.util.ObservableServiceConnection.AnonymousClass1.this.lambda$onNotifyCallback$0(t, callback, observableServiceConnection, i);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onNotifyCallback$0(java.lang.Object obj, com.android.internal.util.ObservableServiceConnection.Callback callback, com.android.internal.util.ObservableServiceConnection observableServiceConnection, int i) {
            synchronized (com.android.internal.util.ObservableServiceConnection.this.mLock) {
                if (obj != null) {
                    callback.onConnected(observableServiceConnection, obj);
                } else if (com.android.internal.util.ObservableServiceConnection.this.mLastDisconnectReason != 0) {
                    callback.onDisconnected(observableServiceConnection, i);
                }
            }
        }
    }

    public ObservableServiceConnection(android.content.Context context, java.util.concurrent.Executor executor, com.android.internal.util.ObservableServiceConnection.ServiceTransformer<T> serviceTransformer, android.content.Intent intent, int i) {
        this.mContext = context;
        this.mExecutor = executor;
        this.mTransformer = serviceTransformer;
        this.mServiceIntent = intent;
        this.mFlags = i;
    }

    public void execute(java.lang.Runnable runnable) {
        this.mExecutor.execute(runnable);
    }

    public boolean bind() {
        synchronized (this.mLock) {
            if (this.mBoundCalled) {
                return false;
            }
            boolean bindService = this.mContext.bindService(this.mServiceIntent, this.mFlags, this.mExecutor, this);
            this.mBoundCalled = true;
            return bindService;
        }
    }

    public void unbind() {
        onDisconnected(4);
    }

    public void addCallback(final com.android.internal.util.ObservableServiceConnection.Callback<T> callback) {
        this.mCallbackRegistry.add(callback);
        this.mExecutor.execute(new java.lang.Runnable() { // from class: com.android.internal.util.ObservableServiceConnection$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                com.android.internal.util.ObservableServiceConnection.this.lambda$addCallback$0(callback);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$addCallback$0(com.android.internal.util.ObservableServiceConnection.Callback callback) {
        synchronized (this.mLock) {
            if (this.mService != null) {
                callback.onConnected(this, this.mService);
            } else if (this.mLastDisconnectReason != 0) {
                callback.onDisconnected(this, this.mLastDisconnectReason);
            }
        }
    }

    public void removeCallback(com.android.internal.util.ObservableServiceConnection.Callback<T> callback) {
        synchronized (this.mLock) {
            this.mCallbackRegistry.remove(callback);
        }
    }

    private void onDisconnected(int i) {
        synchronized (this.mLock) {
            if (this.mBoundCalled) {
                this.mBoundCalled = false;
                this.mLastDisconnectReason = i;
                this.mContext.unbindService(this);
                this.mService = null;
                this.mCallbackRegistry.notifyCallbacks(this, i, null);
            }
        }
    }

    @Override // android.content.ServiceConnection
    public final void onServiceConnected(android.content.ComponentName componentName, android.os.IBinder iBinder) {
        synchronized (this.mLock) {
            this.mService = this.mTransformer.convert(iBinder);
            this.mLastDisconnectReason = 0;
            this.mCallbackRegistry.notifyCallbacks(this, this.mLastDisconnectReason, this.mService);
        }
    }

    @Override // android.content.ServiceConnection
    public final void onServiceDisconnected(android.content.ComponentName componentName) {
        onDisconnected(2);
    }

    @Override // android.content.ServiceConnection
    public final void onBindingDied(android.content.ComponentName componentName) {
        onDisconnected(3);
    }

    @Override // android.content.ServiceConnection
    public final void onNullBinding(android.content.ComponentName componentName) {
        onDisconnected(1);
    }
}
