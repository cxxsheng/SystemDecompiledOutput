package android.speech;

/* loaded from: classes3.dex */
public abstract class RecognitionService extends android.app.Service {
    private static final boolean DBG = false;
    private static final int DEFAULT_MAX_CONCURRENT_SESSIONS_COUNT = 1;
    private static final int MSG_CANCEL = 3;
    private static final int MSG_CHECK_RECOGNITION_SUPPORT = 5;
    private static final int MSG_RESET = 4;
    private static final int MSG_START_LISTENING = 1;
    private static final int MSG_STOP_LISTENING = 2;
    private static final int MSG_TRIGGER_MODEL_DOWNLOAD = 6;
    public static final java.lang.String SERVICE_INTERFACE = "android.speech.RecognitionService";
    public static final java.lang.String SERVICE_META_DATA = "android.speech";
    private static final java.lang.String TAG = "RecognitionService";
    private final java.util.Map<android.os.IBinder, android.speech.RecognitionService.SessionState> mSessions = new java.util.HashMap();
    private final android.speech.RecognitionService.RecognitionServiceBinder mBinder = new android.speech.RecognitionService.RecognitionServiceBinder(this);
    private final android.os.Handler mHandler = new android.os.Handler() { // from class: android.speech.RecognitionService.1
        @Override // android.os.Handler
        public void handleMessage(android.os.Message message) {
            switch (message.what) {
                case 1:
                    android.speech.RecognitionService.StartListeningArgs startListeningArgs = (android.speech.RecognitionService.StartListeningArgs) message.obj;
                    android.speech.RecognitionService.this.dispatchStartListening(startListeningArgs.mIntent, startListeningArgs.mListener, startListeningArgs.mAttributionSource);
                    break;
                case 2:
                    android.speech.RecognitionService.this.dispatchStopListening((android.speech.IRecognitionListener) message.obj);
                    break;
                case 3:
                    android.speech.RecognitionService.this.dispatchCancel((android.speech.IRecognitionListener) message.obj);
                    break;
                case 4:
                    android.speech.RecognitionService.this.dispatchClearCallback((android.speech.IRecognitionListener) message.obj);
                    break;
                case 5:
                    android.speech.RecognitionService.CheckRecognitionSupportArgs checkRecognitionSupportArgs = (android.speech.RecognitionService.CheckRecognitionSupportArgs) message.obj;
                    android.speech.RecognitionService.this.dispatchCheckRecognitionSupport(checkRecognitionSupportArgs.mIntent, checkRecognitionSupportArgs.callback, checkRecognitionSupportArgs.mAttributionSource);
                    break;
                case 6:
                    android.speech.RecognitionService.ModelDownloadArgs modelDownloadArgs = (android.speech.RecognitionService.ModelDownloadArgs) message.obj;
                    android.speech.RecognitionService.this.dispatchTriggerModelDownload(modelDownloadArgs.mIntent, modelDownloadArgs.mAttributionSource, modelDownloadArgs.mListener);
                    break;
            }
        }
    };

    protected abstract void onCancel(android.speech.RecognitionService.Callback callback);

    protected abstract void onStartListening(android.content.Intent intent, android.speech.RecognitionService.Callback callback);

    protected abstract void onStopListening(android.speech.RecognitionService.Callback callback);

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:17:0x003d A[Catch: RemoteException -> 0x0086, TryCatch #0 {RemoteException -> 0x0086, blocks: (B:4:0x0011, B:6:0x001d, B:9:0x0028, B:11:0x0030, B:17:0x003d, B:19:0x0057, B:23:0x005d, B:25:0x0064, B:26:0x0076, B:28:0x007c), top: B:2:0x000f }] */
    /* JADX WARN: Removed duplicated region for block: B:25:0x0064 A[Catch: RemoteException -> 0x0086, TryCatch #0 {RemoteException -> 0x0086, blocks: (B:4:0x0011, B:6:0x001d, B:9:0x0028, B:11:0x0030, B:17:0x003d, B:19:0x0057, B:23:0x005d, B:25:0x0064, B:26:0x0076, B:28:0x007c), top: B:2:0x000f }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void dispatchStartListening(android.content.Intent intent, android.speech.IRecognitionListener iRecognitionListener, android.content.AttributionSource attributionSource) {
        boolean z;
        android.speech.RecognitionService.SessionState sessionState = this.mSessions.get(iRecognitionListener.asBinder());
        try {
            if (sessionState == null) {
                if (this.mSessions.size() >= getMaxConcurrentSessionsCount()) {
                    iRecognitionListener.onError(8);
                    android.util.Log.i(TAG, "#startListening received when the service's capacity is full - ignoring this call.");
                    return;
                }
                if (!intent.hasExtra(android.speech.RecognizerIntent.EXTRA_AUDIO_SOURCE) && !checkPermissionForPreflightNotHardDenied(attributionSource)) {
                    z = false;
                    android.speech.RecognitionService.Callback callback = null;
                    byte b = 0;
                    if (z) {
                        android.speech.RecognitionService.Callback callback2 = new android.speech.RecognitionService.Callback(iRecognitionListener, attributionSource);
                        android.speech.RecognitionService.SessionState sessionState2 = new android.speech.RecognitionService.SessionState(callback2);
                        this.mSessions.put(iRecognitionListener.asBinder(), sessionState2);
                        onStartListening(intent, callback2);
                        callback = callback2;
                        sessionState = sessionState2;
                    }
                    if (z || !checkPermissionAndStartDataDelivery(sessionState)) {
                        iRecognitionListener.onError(9);
                        if (z) {
                            onCancel(callback);
                            this.mSessions.remove(iRecognitionListener.asBinder());
                            finishDataDelivery(sessionState);
                            sessionState.reset();
                        }
                        android.util.Log.i(TAG, "#startListening received from a caller without permission android.permission.RECORD_AUDIO.");
                    }
                    return;
                }
                z = true;
                android.speech.RecognitionService.Callback callback3 = null;
                byte b2 = 0;
                if (z) {
                }
                if (z) {
                }
                iRecognitionListener.onError(9);
                if (z) {
                }
                android.util.Log.i(TAG, "#startListening received from a caller without permission android.permission.RECORD_AUDIO.");
                return;
            }
            iRecognitionListener.onError(5);
            android.util.Log.i(TAG, "#startListening received for a listener which is already in session - ignoring this call.");
        } catch (android.os.RemoteException e) {
            android.util.Log.d(TAG, "#onError call from #startListening failed.");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dispatchStopListening(android.speech.IRecognitionListener iRecognitionListener) {
        android.speech.RecognitionService.SessionState sessionState = this.mSessions.get(iRecognitionListener.asBinder());
        if (sessionState == null) {
            try {
                iRecognitionListener.onError(5);
            } catch (android.os.RemoteException e) {
                android.util.Log.d(TAG, "#onError call from #stopListening failed.");
            }
            android.util.Log.w(TAG, "#stopListening received for a listener which has not started a session - ignoring this call.");
            return;
        }
        onStopListening(sessionState.mCallback);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dispatchCancel(android.speech.IRecognitionListener iRecognitionListener) {
        android.speech.RecognitionService.SessionState sessionState = this.mSessions.get(iRecognitionListener.asBinder());
        if (sessionState == null) {
            android.util.Log.w(TAG, "#cancel received for a listener which has not started a session - ignoring this call.");
        } else {
            onCancel(sessionState.mCallback);
            dispatchClearCallback(iRecognitionListener);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dispatchClearCallback(android.speech.IRecognitionListener iRecognitionListener) {
        android.speech.RecognitionService.SessionState remove = this.mSessions.remove(iRecognitionListener.asBinder());
        if (remove != null) {
            finishDataDelivery(remove);
            remove.reset();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dispatchCheckRecognitionSupport(android.content.Intent intent, android.speech.IRecognitionSupportCallback iRecognitionSupportCallback, android.content.AttributionSource attributionSource) {
        onCheckRecognitionSupport(intent, attributionSource, new android.speech.RecognitionService.SupportCallback(iRecognitionSupportCallback));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dispatchTriggerModelDownload(android.content.Intent intent, android.content.AttributionSource attributionSource, final android.speech.IModelDownloadListener iModelDownloadListener) {
        if (iModelDownloadListener == null) {
            onTriggerModelDownload(intent, attributionSource);
        } else {
            onTriggerModelDownload(intent, attributionSource, new android.speech.ModelDownloadListener() { // from class: android.speech.RecognitionService.2
                private final java.lang.Object mLock = new java.lang.Object();
                private boolean mIsTerminated = false;

                @Override // android.speech.ModelDownloadListener
                public void onProgress(int i) {
                    synchronized (this.mLock) {
                        if (this.mIsTerminated) {
                            return;
                        }
                        try {
                            iModelDownloadListener.onProgress(i);
                        } catch (android.os.RemoteException e) {
                            throw e.rethrowFromSystemServer();
                        }
                    }
                }

                @Override // android.speech.ModelDownloadListener
                public void onSuccess() {
                    synchronized (this.mLock) {
                        if (this.mIsTerminated) {
                            return;
                        }
                        this.mIsTerminated = true;
                        try {
                            iModelDownloadListener.onSuccess();
                        } catch (android.os.RemoteException e) {
                            throw e.rethrowFromSystemServer();
                        }
                    }
                }

                @Override // android.speech.ModelDownloadListener
                public void onScheduled() {
                    synchronized (this.mLock) {
                        if (this.mIsTerminated) {
                            return;
                        }
                        this.mIsTerminated = true;
                        try {
                            iModelDownloadListener.onScheduled();
                        } catch (android.os.RemoteException e) {
                            throw e.rethrowFromSystemServer();
                        }
                    }
                }

                @Override // android.speech.ModelDownloadListener
                public void onError(int i) {
                    synchronized (this.mLock) {
                        if (this.mIsTerminated) {
                            return;
                        }
                        this.mIsTerminated = true;
                        try {
                            iModelDownloadListener.onError(i);
                        } catch (android.os.RemoteException e) {
                            throw e.rethrowFromSystemServer();
                        }
                    }
                }
            });
        }
    }

    private static class StartListeningArgs {
        public final android.content.AttributionSource mAttributionSource;
        public final android.content.Intent mIntent;
        public final android.speech.IRecognitionListener mListener;

        public StartListeningArgs(android.content.Intent intent, android.speech.IRecognitionListener iRecognitionListener, android.content.AttributionSource attributionSource) {
            this.mIntent = intent;
            this.mListener = iRecognitionListener;
            this.mAttributionSource = attributionSource;
        }
    }

    private static class CheckRecognitionSupportArgs {
        public final android.speech.IRecognitionSupportCallback callback;
        public final android.content.AttributionSource mAttributionSource;
        public final android.content.Intent mIntent;

        private CheckRecognitionSupportArgs(android.content.Intent intent, android.speech.IRecognitionSupportCallback iRecognitionSupportCallback, android.content.AttributionSource attributionSource) {
            this.mIntent = intent;
            this.callback = iRecognitionSupportCallback;
            this.mAttributionSource = attributionSource;
        }
    }

    private static class ModelDownloadArgs {
        final android.content.AttributionSource mAttributionSource;
        final android.content.Intent mIntent;
        final android.speech.IModelDownloadListener mListener;

        private ModelDownloadArgs(android.content.Intent intent, android.content.AttributionSource attributionSource, android.speech.IModelDownloadListener iModelDownloadListener) {
            this.mIntent = intent;
            this.mAttributionSource = attributionSource;
            this.mListener = iModelDownloadListener;
        }
    }

    public void onCheckRecognitionSupport(android.content.Intent intent, android.speech.RecognitionService.SupportCallback supportCallback) {
        supportCallback.onError(14);
    }

    public void onCheckRecognitionSupport(android.content.Intent intent, android.content.AttributionSource attributionSource, android.speech.RecognitionService.SupportCallback supportCallback) {
        onCheckRecognitionSupport(intent, supportCallback);
    }

    public void onTriggerModelDownload(android.content.Intent intent) {
    }

    public void onTriggerModelDownload(android.content.Intent intent, android.content.AttributionSource attributionSource) {
        onTriggerModelDownload(intent);
    }

    public void onTriggerModelDownload(android.content.Intent intent, android.content.AttributionSource attributionSource, android.speech.ModelDownloadListener modelDownloadListener) {
        modelDownloadListener.onError(15);
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public android.content.Context createContext(android.content.ContextParams contextParams) {
        if (contextParams.getNextAttributionSource() != null) {
            if (this.mHandler.getLooper().equals(android.os.Looper.myLooper())) {
                handleAttributionContextCreation(contextParams.getNextAttributionSource());
            } else {
                this.mHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new java.util.function.Consumer() { // from class: android.speech.RecognitionService$$ExternalSyntheticLambda0
                    @Override // java.util.function.Consumer
                    public final void accept(java.lang.Object obj) {
                        android.speech.RecognitionService.this.handleAttributionContextCreation((android.content.AttributionSource) obj);
                    }
                }, contextParams.getNextAttributionSource()));
            }
        }
        return super.createContext(contextParams);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleAttributionContextCreation(android.content.AttributionSource attributionSource) {
        java.util.Iterator<android.speech.RecognitionService.SessionState> it = this.mSessions.values().iterator();
        while (it.hasNext()) {
            android.speech.RecognitionService.Callback callback = it.next().mCallback;
            if (callback != null && callback.mCallingAttributionSource.equals(attributionSource)) {
                callback.mAttributionContextCreated = true;
            }
        }
    }

    @Override // android.app.Service
    public final android.os.IBinder onBind(android.content.Intent intent) {
        onBindInternal();
        return this.mBinder;
    }

    public void onBindInternal() {
    }

    @Override // android.app.Service
    public void onDestroy() {
        for (android.speech.RecognitionService.SessionState sessionState : this.mSessions.values()) {
            finishDataDelivery(sessionState);
            sessionState.reset();
        }
        this.mSessions.clear();
        this.mBinder.clearReference();
        super.onDestroy();
    }

    public int getMaxConcurrentSessionsCount() {
        return 1;
    }

    public class Callback {
        private android.content.Context mAttributionContext;
        private boolean mAttributionContextCreated;
        private final android.content.AttributionSource mCallingAttributionSource;
        private final android.speech.IRecognitionListener mListener;

        private Callback(android.speech.IRecognitionListener iRecognitionListener, android.content.AttributionSource attributionSource) {
            this.mListener = iRecognitionListener;
            this.mCallingAttributionSource = attributionSource;
        }

        public void beginningOfSpeech() throws android.os.RemoteException {
            this.mListener.onBeginningOfSpeech();
        }

        public void bufferReceived(byte[] bArr) throws android.os.RemoteException {
            this.mListener.onBufferReceived(bArr);
        }

        public void endOfSpeech() throws android.os.RemoteException {
            this.mListener.onEndOfSpeech();
        }

        public void error(int i) throws android.os.RemoteException {
            android.os.Message.obtain(android.speech.RecognitionService.this.mHandler, 4, this.mListener).sendToTarget();
            this.mListener.onError(i);
        }

        public void partialResults(android.os.Bundle bundle) throws android.os.RemoteException {
            this.mListener.onPartialResults(bundle);
        }

        public void readyForSpeech(android.os.Bundle bundle) throws android.os.RemoteException {
            this.mListener.onReadyForSpeech(bundle);
        }

        public void results(android.os.Bundle bundle) throws android.os.RemoteException {
            android.os.Message.obtain(android.speech.RecognitionService.this.mHandler, 4, this.mListener).sendToTarget();
            this.mListener.onResults(bundle);
        }

        public void rmsChanged(float f) throws android.os.RemoteException {
            this.mListener.onRmsChanged(f);
        }

        public void segmentResults(android.os.Bundle bundle) throws android.os.RemoteException {
            this.mListener.onSegmentResults(bundle);
        }

        public void endOfSegmentedSession() throws android.os.RemoteException {
            android.os.Message.obtain(android.speech.RecognitionService.this.mHandler, 4, this.mListener).sendToTarget();
            this.mListener.onEndOfSegmentedSession();
        }

        public void languageDetection(android.os.Bundle bundle) {
            try {
                this.mListener.onLanguageDetection(bundle);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        public int getCallingUid() {
            return this.mCallingAttributionSource.getUid();
        }

        public android.content.AttributionSource getCallingAttributionSource() {
            return this.mCallingAttributionSource;
        }

        android.content.Context getAttributionContextForCaller() {
            if (this.mAttributionContext == null) {
                this.mAttributionContext = android.speech.RecognitionService.this.createContext(new android.content.ContextParams.Builder().setNextAttributionSource(this.mCallingAttributionSource).build());
            }
            return this.mAttributionContext;
        }
    }

    public static class SupportCallback {
        private final android.speech.IRecognitionSupportCallback mCallback;

        private SupportCallback(android.speech.IRecognitionSupportCallback iRecognitionSupportCallback) {
            this.mCallback = iRecognitionSupportCallback;
        }

        public void onSupportResult(android.speech.RecognitionSupport recognitionSupport) {
            try {
                this.mCallback.onSupportResult(recognitionSupport);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        public void onError(int i) {
            try {
                this.mCallback.onError(i);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    private static final class RecognitionServiceBinder extends android.speech.IRecognitionService.Stub {
        private final java.lang.ref.WeakReference<android.speech.RecognitionService> mServiceRef;

        public RecognitionServiceBinder(android.speech.RecognitionService recognitionService) {
            this.mServiceRef = new java.lang.ref.WeakReference<>(recognitionService);
        }

        @Override // android.speech.IRecognitionService
        public void startListening(android.content.Intent intent, android.speech.IRecognitionListener iRecognitionListener, android.content.AttributionSource attributionSource) {
            java.util.Objects.requireNonNull(attributionSource);
            attributionSource.enforceCallingUid();
            android.speech.RecognitionService recognitionService = this.mServiceRef.get();
            if (recognitionService != null) {
                recognitionService.mHandler.sendMessage(android.os.Message.obtain(recognitionService.mHandler, 1, new android.speech.RecognitionService.StartListeningArgs(intent, iRecognitionListener, attributionSource)));
            }
        }

        @Override // android.speech.IRecognitionService
        public void stopListening(android.speech.IRecognitionListener iRecognitionListener) {
            android.speech.RecognitionService recognitionService = this.mServiceRef.get();
            if (recognitionService != null) {
                recognitionService.mHandler.sendMessage(android.os.Message.obtain(recognitionService.mHandler, 2, iRecognitionListener));
            }
        }

        @Override // android.speech.IRecognitionService
        public void cancel(android.speech.IRecognitionListener iRecognitionListener, boolean z) {
            android.speech.RecognitionService recognitionService = this.mServiceRef.get();
            if (recognitionService != null) {
                recognitionService.mHandler.sendMessage(android.os.Message.obtain(recognitionService.mHandler, 3, iRecognitionListener));
            }
        }

        @Override // android.speech.IRecognitionService
        public void checkRecognitionSupport(android.content.Intent intent, android.content.AttributionSource attributionSource, android.speech.IRecognitionSupportCallback iRecognitionSupportCallback) {
            android.speech.RecognitionService recognitionService = this.mServiceRef.get();
            if (recognitionService != null) {
                recognitionService.mHandler.sendMessage(android.os.Message.obtain(recognitionService.mHandler, 5, new android.speech.RecognitionService.CheckRecognitionSupportArgs(intent, iRecognitionSupportCallback, attributionSource)));
            }
        }

        @Override // android.speech.IRecognitionService
        public void triggerModelDownload(android.content.Intent intent, android.content.AttributionSource attributionSource, android.speech.IModelDownloadListener iModelDownloadListener) {
            android.speech.RecognitionService recognitionService = this.mServiceRef.get();
            if (recognitionService != null) {
                recognitionService.mHandler.sendMessage(android.os.Message.obtain(recognitionService.mHandler, 6, new android.speech.RecognitionService.ModelDownloadArgs(intent, attributionSource, iModelDownloadListener)));
            }
        }

        public void clearReference() {
            this.mServiceRef.clear();
        }
    }

    private boolean checkPermissionAndStartDataDelivery(android.speech.RecognitionService.SessionState sessionState) {
        if (sessionState.mCallback.mAttributionContextCreated) {
            return true;
        }
        if (android.content.PermissionChecker.checkPermissionAndStartDataDelivery(this, android.Manifest.permission.RECORD_AUDIO, sessionState.mCallback.getAttributionContextForCaller().getAttributionSource(), null) == 0) {
            sessionState.mStartedDataDelivery = true;
        }
        return sessionState.mStartedDataDelivery;
    }

    private boolean checkPermissionForPreflightNotHardDenied(android.content.AttributionSource attributionSource) {
        int checkPermissionForPreflight = android.content.PermissionChecker.checkPermissionForPreflight(this, android.Manifest.permission.RECORD_AUDIO, attributionSource);
        return checkPermissionForPreflight == 0 || checkPermissionForPreflight == 1;
    }

    void finishDataDelivery(android.speech.RecognitionService.SessionState sessionState) {
        if (sessionState.mStartedDataDelivery) {
            sessionState.mStartedDataDelivery = false;
            android.content.PermissionChecker.finishDataDelivery(this, android.app.AppOpsManager.permissionToOp(android.Manifest.permission.RECORD_AUDIO), sessionState.mCallback.getAttributionContextForCaller().getAttributionSource());
        }
    }

    private static class SessionState {
        private android.speech.RecognitionService.Callback mCallback;
        private boolean mStartedDataDelivery;

        SessionState(android.speech.RecognitionService.Callback callback, boolean z) {
            this.mCallback = callback;
            this.mStartedDataDelivery = z;
        }

        SessionState(android.speech.RecognitionService.Callback callback) {
            this(callback, false);
        }

        void reset() {
            this.mCallback = null;
            this.mStartedDataDelivery = false;
        }
    }
}
