package android.app.usage;

@android.annotation.SystemApi
/* loaded from: classes.dex */
public abstract class CacheQuotaService extends android.app.Service {
    public static final java.lang.String REQUEST_LIST_KEY = "requests";
    public static final java.lang.String SERVICE_INTERFACE = "android.app.usage.CacheQuotaService";
    private static final java.lang.String TAG = "CacheQuotaService";
    private android.os.Handler mHandler;
    private android.app.usage.CacheQuotaService.CacheQuotaServiceWrapper mWrapper;

    public abstract java.util.List<android.app.usage.CacheQuotaHint> onComputeCacheQuotaHints(java.util.List<android.app.usage.CacheQuotaHint> list);

    @Override // android.app.Service
    public void onCreate() {
        super.onCreate();
        this.mWrapper = new android.app.usage.CacheQuotaService.CacheQuotaServiceWrapper();
        this.mHandler = new android.app.usage.CacheQuotaService.ServiceHandler(getMainLooper());
    }

    @Override // android.app.Service
    public android.os.IBinder onBind(android.content.Intent intent) {
        return this.mWrapper;
    }

    private final class CacheQuotaServiceWrapper extends android.app.usage.ICacheQuotaService.Stub {
        private CacheQuotaServiceWrapper() {
        }

        @Override // android.app.usage.ICacheQuotaService
        public void computeCacheQuotaHints(android.os.RemoteCallback remoteCallback, java.util.List<android.app.usage.CacheQuotaHint> list) {
            android.app.usage.CacheQuotaService.this.mHandler.sendMessage(android.app.usage.CacheQuotaService.this.mHandler.obtainMessage(1, android.util.Pair.create(remoteCallback, list)));
        }
    }

    private final class ServiceHandler extends android.os.Handler {
        public static final int MSG_SEND_LIST = 1;

        public ServiceHandler(android.os.Looper looper) {
            super(looper, null, true);
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // android.os.Handler
        public void handleMessage(android.os.Message message) {
            int i = message.what;
            switch (i) {
                case 1:
                    android.util.Pair pair = (android.util.Pair) message.obj;
                    java.util.List<android.app.usage.CacheQuotaHint> onComputeCacheQuotaHints = android.app.usage.CacheQuotaService.this.onComputeCacheQuotaHints((java.util.List) pair.second);
                    android.os.Bundle bundle = new android.os.Bundle();
                    bundle.putParcelableList(android.app.usage.CacheQuotaService.REQUEST_LIST_KEY, onComputeCacheQuotaHints);
                    ((android.os.RemoteCallback) pair.first).sendResult(bundle);
                    break;
                default:
                    android.util.Log.w(android.app.usage.CacheQuotaService.TAG, "Handling unknown message: " + i);
                    break;
            }
        }
    }
}
