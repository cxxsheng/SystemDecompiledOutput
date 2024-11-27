package android.service.resolver;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public abstract class ResolverRankerService extends android.app.Service {
    public static final java.lang.String BIND_PERMISSION = "android.permission.BIND_RESOLVER_RANKER_SERVICE";
    private static final boolean DEBUG = false;
    private static final java.lang.String HANDLER_THREAD_NAME = "RESOLVER_RANKER_SERVICE";
    public static final java.lang.String HOLD_PERMISSION = "android.permission.PROVIDE_RESOLVER_RANKER_SERVICE";
    public static final java.lang.String SERVICE_INTERFACE = "android.service.resolver.ResolverRankerService";
    private static final java.lang.String TAG = "ResolverRankerService";
    private volatile android.os.Handler mHandler;
    private android.os.HandlerThread mHandlerThread;
    private android.service.resolver.ResolverRankerService.ResolverRankerServiceWrapper mWrapper = null;

    public void onPredictSharingProbabilities(java.util.List<android.service.resolver.ResolverTarget> list) {
    }

    public void onTrainRankingModel(java.util.List<android.service.resolver.ResolverTarget> list, int i) {
    }

    @Override // android.app.Service
    public android.os.IBinder onBind(android.content.Intent intent) {
        if (!SERVICE_INTERFACE.equals(intent.getAction())) {
            return null;
        }
        if (this.mHandlerThread == null) {
            this.mHandlerThread = new android.os.HandlerThread(HANDLER_THREAD_NAME);
            this.mHandlerThread.start();
            this.mHandler = new android.os.Handler(this.mHandlerThread.getLooper());
        }
        if (this.mWrapper == null) {
            this.mWrapper = new android.service.resolver.ResolverRankerService.ResolverRankerServiceWrapper();
        }
        return this.mWrapper;
    }

    @Override // android.app.Service
    public void onDestroy() {
        this.mHandler = null;
        if (this.mHandlerThread != null) {
            this.mHandlerThread.quitSafely();
        }
        super.onDestroy();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void sendResult(java.util.List<android.service.resolver.ResolverTarget> list, android.service.resolver.IResolverRankerResult iResolverRankerResult) {
        try {
            iResolverRankerResult.sendResult(list);
        } catch (java.lang.Exception e) {
            android.util.Log.e(TAG, "failed to send results: " + e);
        }
    }

    private class ResolverRankerServiceWrapper extends android.service.resolver.IResolverRankerService.Stub {
        private ResolverRankerServiceWrapper() {
        }

        @Override // android.service.resolver.IResolverRankerService
        public void predict(final java.util.List<android.service.resolver.ResolverTarget> list, final android.service.resolver.IResolverRankerResult iResolverRankerResult) throws android.os.RemoteException {
            java.lang.Runnable runnable = new java.lang.Runnable() { // from class: android.service.resolver.ResolverRankerService.ResolverRankerServiceWrapper.1
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        android.service.resolver.ResolverRankerService.this.onPredictSharingProbabilities(list);
                        android.service.resolver.ResolverRankerService.sendResult(list, iResolverRankerResult);
                    } catch (java.lang.Exception e) {
                        android.util.Log.e(android.service.resolver.ResolverRankerService.TAG, "onPredictSharingProbabilities failed; send null results: " + e);
                        android.service.resolver.ResolverRankerService.sendResult(null, iResolverRankerResult);
                    }
                }
            };
            android.os.Handler handler = android.service.resolver.ResolverRankerService.this.mHandler;
            if (handler != null) {
                handler.post(runnable);
            }
        }

        @Override // android.service.resolver.IResolverRankerService
        public void train(final java.util.List<android.service.resolver.ResolverTarget> list, final int i) throws android.os.RemoteException {
            java.lang.Runnable runnable = new java.lang.Runnable() { // from class: android.service.resolver.ResolverRankerService.ResolverRankerServiceWrapper.2
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        android.service.resolver.ResolverRankerService.this.onTrainRankingModel(list, i);
                    } catch (java.lang.Exception e) {
                        android.util.Log.e(android.service.resolver.ResolverRankerService.TAG, "onTrainRankingModel failed; skip train: " + e);
                    }
                }
            };
            android.os.Handler handler = android.service.resolver.ResolverRankerService.this.mHandler;
            if (handler != null) {
                handler.post(runnable);
            }
        }
    }
}
