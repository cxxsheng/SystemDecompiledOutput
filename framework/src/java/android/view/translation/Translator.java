package android.view.translation;

/* loaded from: classes4.dex */
public class Translator {
    public static final java.lang.String EXTRA_SERVICE_BINDER = "binder";
    public static final java.lang.String EXTRA_SESSION_ID = "sessionId";
    private static final java.lang.String TAG = "Translator";
    private final android.content.Context mContext;
    private boolean mDestroyed;
    private android.view.translation.ITranslationDirectManager mDirectServiceBinder;
    private final android.os.Handler mHandler;
    private int mId;
    private final java.lang.Object mLock;
    private final android.view.translation.TranslationManager mManager;
    private final android.view.translation.Translator.ServiceBinderReceiver mServiceBinderReceiver;
    private android.view.translation.ITranslationManager mSystemServerBinder;
    private final android.view.translation.TranslationContext mTranslationContext;

    static class ServiceBinderReceiver extends com.android.internal.os.IResultReceiver.Stub {
        private java.util.function.Consumer<android.view.translation.Translator> mCallback;
        private final java.util.concurrent.CountDownLatch mLatch = new java.util.concurrent.CountDownLatch(1);
        private int mSessionId;
        private final android.view.translation.Translator mTranslator;

        ServiceBinderReceiver(android.view.translation.Translator translator, java.util.function.Consumer<android.view.translation.Translator> consumer) {
            this.mTranslator = translator;
            this.mCallback = consumer;
        }

        ServiceBinderReceiver(android.view.translation.Translator translator) {
            this.mTranslator = translator;
        }

        int getSessionStateResult() throws android.view.translation.Translator.ServiceBinderReceiver.TimeoutException {
            try {
                if (!this.mLatch.await(60000L, java.util.concurrent.TimeUnit.MILLISECONDS)) {
                    throw new android.view.translation.Translator.ServiceBinderReceiver.TimeoutException("Session not created in 60000ms");
                }
                return this.mSessionId;
            } catch (java.lang.InterruptedException e) {
                java.lang.Thread.currentThread().interrupt();
                throw new android.view.translation.Translator.ServiceBinderReceiver.TimeoutException("Session not created because interrupted");
            }
        }

        @Override // com.android.internal.os.IResultReceiver
        public void send(int i, android.os.Bundle bundle) {
            android.os.IBinder iBinder = null;
            if (i == 2) {
                this.mLatch.countDown();
                if (this.mCallback != null) {
                    this.mCallback.accept(null);
                    return;
                }
                return;
            }
            if (bundle != null) {
                this.mSessionId = bundle.getInt("sessionId");
                iBinder = bundle.getBinder("binder");
                if (iBinder == null) {
                    android.util.Log.wtf(android.view.translation.Translator.TAG, "No binder extra result");
                    return;
                }
            }
            this.mTranslator.setServiceBinder(iBinder);
            this.mLatch.countDown();
            if (this.mCallback != null) {
                this.mCallback.accept(this.mTranslator);
            }
        }

        static final class TimeoutException extends java.lang.Exception {
            private TimeoutException(java.lang.String str) {
                super(str);
            }
        }
    }

    public Translator(android.content.Context context, android.view.translation.TranslationContext translationContext, int i, android.view.translation.TranslationManager translationManager, android.os.Handler handler, android.view.translation.ITranslationManager iTranslationManager, java.util.function.Consumer<android.view.translation.Translator> consumer) {
        this.mLock = new java.lang.Object();
        this.mContext = context;
        this.mTranslationContext = translationContext;
        this.mId = i;
        this.mManager = translationManager;
        this.mHandler = handler;
        this.mSystemServerBinder = iTranslationManager;
        this.mServiceBinderReceiver = new android.view.translation.Translator.ServiceBinderReceiver(this, consumer);
        try {
            this.mSystemServerBinder.onSessionCreated(this.mTranslationContext, this.mId, this.mServiceBinderReceiver, this.mContext.getUserId());
        } catch (android.os.RemoteException e) {
            android.util.Log.w(TAG, "RemoteException calling startSession(): " + e);
        }
    }

    public Translator(android.content.Context context, android.view.translation.TranslationContext translationContext, int i, android.view.translation.TranslationManager translationManager, android.os.Handler handler, android.view.translation.ITranslationManager iTranslationManager) {
        this.mLock = new java.lang.Object();
        this.mContext = context;
        this.mTranslationContext = translationContext;
        this.mId = i;
        this.mManager = translationManager;
        this.mHandler = handler;
        this.mSystemServerBinder = iTranslationManager;
        this.mServiceBinderReceiver = new android.view.translation.Translator.ServiceBinderReceiver(this);
    }

    void start() {
        try {
            this.mSystemServerBinder.onSessionCreated(this.mTranslationContext, this.mId, this.mServiceBinderReceiver, this.mContext.getUserId());
        } catch (android.os.RemoteException e) {
            android.util.Log.w(TAG, "RemoteException calling startSession(): " + e);
        }
    }

    boolean isSessionCreated() throws android.view.translation.Translator.ServiceBinderReceiver.TimeoutException {
        return this.mServiceBinderReceiver.getSessionStateResult() > 0;
    }

    private int getNextRequestId() {
        return this.mManager.getAvailableRequestId().getAndIncrement();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setServiceBinder(android.os.IBinder iBinder) {
        synchronized (this.mLock) {
            if (this.mDirectServiceBinder != null) {
                return;
            }
            if (iBinder != null) {
                this.mDirectServiceBinder = android.view.translation.ITranslationDirectManager.Stub.asInterface(iBinder);
            }
        }
    }

    public android.view.translation.TranslationContext getTranslationContext() {
        return this.mTranslationContext;
    }

    public int getTranslatorId() {
        return this.mId;
    }

    public void dump(java.lang.String str, java.io.PrintWriter printWriter) {
        printWriter.print(str);
        printWriter.print("translationContext: ");
        printWriter.println(this.mTranslationContext);
    }

    @java.lang.Deprecated
    public void translate(android.view.translation.TranslationRequest translationRequest, java.util.concurrent.Executor executor, java.util.function.Consumer<android.view.translation.TranslationResponse> consumer) {
        java.util.Objects.requireNonNull(translationRequest, "Translation request cannot be null");
        java.util.Objects.requireNonNull(executor, "Executor cannot be null");
        java.util.Objects.requireNonNull(consumer, "Callback cannot be null");
        if (isDestroyed()) {
            throw new java.lang.IllegalStateException("This translator has been destroyed");
        }
        try {
            this.mDirectServiceBinder.onTranslationRequest(translationRequest, this.mId, android.os.CancellationSignal.createTransport(), new android.view.translation.Translator.TranslationResponseCallbackImpl(consumer, executor));
        } catch (android.os.RemoteException e) {
            android.util.Log.w(TAG, "RemoteException calling requestTranslate(): " + e);
        }
    }

    public void translate(android.view.translation.TranslationRequest translationRequest, android.os.CancellationSignal cancellationSignal, java.util.concurrent.Executor executor, java.util.function.Consumer<android.view.translation.TranslationResponse> consumer) {
        android.os.ICancellationSignal iCancellationSignal;
        java.util.Objects.requireNonNull(translationRequest, "Translation request cannot be null");
        java.util.Objects.requireNonNull(executor, "Executor cannot be null");
        java.util.Objects.requireNonNull(consumer, "Callback cannot be null");
        if (isDestroyed()) {
            throw new java.lang.IllegalStateException("This translator has been destroyed");
        }
        if (cancellationSignal == null) {
            iCancellationSignal = null;
        } else {
            iCancellationSignal = android.os.CancellationSignal.createTransport();
            cancellationSignal.setRemote(iCancellationSignal);
        }
        try {
            this.mDirectServiceBinder.onTranslationRequest(translationRequest, this.mId, iCancellationSignal, new android.view.translation.Translator.TranslationResponseCallbackImpl(consumer, executor));
        } catch (android.os.RemoteException e) {
            android.util.Log.w(TAG, "RemoteException calling requestTranslate(): " + e);
        }
    }

    public void destroy() {
        synchronized (this.mLock) {
            if (this.mDestroyed) {
                return;
            }
            this.mDestroyed = true;
            try {
                this.mDirectServiceBinder.onFinishTranslationSession(this.mId);
            } catch (android.os.RemoteException e) {
                android.util.Log.w(TAG, "RemoteException calling onSessionFinished");
            }
            this.mDirectServiceBinder = null;
            this.mManager.removeTranslator(this.mId);
        }
    }

    public boolean isDestroyed() {
        boolean z;
        synchronized (this.mLock) {
            z = this.mDestroyed;
        }
        return z;
    }

    public void requestUiTranslate(android.view.translation.TranslationRequest translationRequest, java.util.concurrent.Executor executor, java.util.function.Consumer<android.view.translation.TranslationResponse> consumer) {
        if (this.mDirectServiceBinder == null) {
            android.util.Log.wtf(TAG, "Translator created without proper initialization.");
            return;
        }
        try {
            this.mDirectServiceBinder.onTranslationRequest(translationRequest, this.mId, android.os.CancellationSignal.createTransport(), new android.view.translation.Translator.TranslationResponseCallbackImpl(consumer, executor));
        } catch (android.os.RemoteException e) {
            android.util.Log.w(TAG, "RemoteException calling flushRequest");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class TranslationResponseCallbackImpl extends android.service.translation.ITranslationCallback.Stub {
        private final java.util.function.Consumer<android.view.translation.TranslationResponse> mCallback;
        private final java.util.concurrent.Executor mExecutor;

        TranslationResponseCallbackImpl(java.util.function.Consumer<android.view.translation.TranslationResponse> consumer, java.util.concurrent.Executor executor) {
            this.mCallback = consumer;
            this.mExecutor = executor;
        }

        @Override // android.service.translation.ITranslationCallback
        public void onTranslationResponse(final android.view.translation.TranslationResponse translationResponse) throws android.os.RemoteException {
            if (android.util.Log.isLoggable(android.view.translation.UiTranslationManager.LOG_TAG, 3)) {
                android.util.Log.i(android.view.translation.Translator.TAG, "onTranslationResponse called.");
            }
            java.lang.Runnable runnable = new java.lang.Runnable() { // from class: android.view.translation.Translator$TranslationResponseCallbackImpl$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    android.view.translation.Translator.TranslationResponseCallbackImpl.this.lambda$onTranslationResponse$0(translationResponse);
                }
            };
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                this.mExecutor.execute(runnable);
            } finally {
                restoreCallingIdentity(clearCallingIdentity);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onTranslationResponse$0(android.view.translation.TranslationResponse translationResponse) {
            this.mCallback.accept(translationResponse);
        }
    }
}
