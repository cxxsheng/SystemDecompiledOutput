package android.printservice.recommendation;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public abstract class RecommendationService extends android.app.Service {
    private static final java.lang.String LOG_TAG = "PrintServiceRecS";
    public static final java.lang.String SERVICE_INTERFACE = "android.printservice.recommendation.RecommendationService";
    private android.printservice.recommendation.IRecommendationServiceCallbacks mCallbacks;
    private android.os.Handler mHandler;

    public abstract void onConnected();

    public abstract void onDisconnected();

    @Override // android.app.Service, android.content.ContextWrapper
    protected void attachBaseContext(android.content.Context context) {
        super.attachBaseContext(context);
        this.mHandler = new android.printservice.recommendation.RecommendationService.MyHandler();
    }

    public final void updateRecommendations(java.util.List<android.printservice.recommendation.RecommendationInfo> list) {
        this.mHandler.obtainMessage(3, list).sendToTarget();
    }

    @Override // android.app.Service
    public final android.os.IBinder onBind(android.content.Intent intent) {
        return new android.printservice.recommendation.IRecommendationService.Stub() { // from class: android.printservice.recommendation.RecommendationService.1
            @Override // android.printservice.recommendation.IRecommendationService
            public void registerCallbacks(android.printservice.recommendation.IRecommendationServiceCallbacks iRecommendationServiceCallbacks) {
                if (iRecommendationServiceCallbacks != null) {
                    android.printservice.recommendation.RecommendationService.this.mHandler.obtainMessage(1, iRecommendationServiceCallbacks).sendToTarget();
                } else {
                    android.printservice.recommendation.RecommendationService.this.mHandler.obtainMessage(2).sendToTarget();
                }
            }
        };
    }

    private class MyHandler extends android.os.Handler {
        static final int MSG_CONNECT = 1;
        static final int MSG_DISCONNECT = 2;
        static final int MSG_UPDATE = 3;

        MyHandler() {
            super(android.os.Looper.getMainLooper());
        }

        @Override // android.os.Handler
        public void handleMessage(android.os.Message message) {
            switch (message.what) {
                case 1:
                    android.printservice.recommendation.RecommendationService.this.mCallbacks = (android.printservice.recommendation.IRecommendationServiceCallbacks) message.obj;
                    android.printservice.recommendation.RecommendationService.this.onConnected();
                    break;
                case 2:
                    android.printservice.recommendation.RecommendationService.this.onDisconnected();
                    android.printservice.recommendation.RecommendationService.this.mCallbacks = null;
                    break;
                case 3:
                    if (android.printservice.recommendation.RecommendationService.this.mCallbacks != null) {
                        try {
                            android.printservice.recommendation.RecommendationService.this.mCallbacks.onRecommendationsUpdated((java.util.List) message.obj);
                            break;
                        } catch (android.os.RemoteException | java.lang.NullPointerException e) {
                            android.util.Log.e(android.printservice.recommendation.RecommendationService.LOG_TAG, "Could not update recommended services", e);
                            return;
                        }
                    }
                    break;
            }
        }
    }
}
