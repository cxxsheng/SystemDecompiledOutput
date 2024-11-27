package android.net;

@android.annotation.SystemApi
/* loaded from: classes2.dex */
public abstract class NetworkRecommendationProvider {
    private static final java.lang.String TAG = "NetworkRecProvider";
    private static final boolean VERBOSE;
    private final android.os.IBinder mService;

    public abstract void onRequestScores(android.net.NetworkKey[] networkKeyArr);

    static {
        VERBOSE = android.os.Build.IS_DEBUGGABLE && android.util.Log.isLoggable(TAG, 2);
    }

    public NetworkRecommendationProvider(android.content.Context context, java.util.concurrent.Executor executor) {
        com.android.internal.util.Preconditions.checkNotNull(context);
        com.android.internal.util.Preconditions.checkNotNull(executor);
        this.mService = new android.net.NetworkRecommendationProvider.ServiceWrapper(context, executor);
    }

    public final android.os.IBinder getBinder() {
        return this.mService;
    }

    private final class ServiceWrapper extends android.net.INetworkRecommendationProvider.Stub {
        private final android.content.Context mContext;
        private final java.util.concurrent.Executor mExecutor;
        private final android.os.Handler mHandler = null;

        ServiceWrapper(android.content.Context context, java.util.concurrent.Executor executor) {
            this.mContext = context;
            this.mExecutor = executor;
        }

        @Override // android.net.INetworkRecommendationProvider
        public void requestScores(final android.net.NetworkKey[] networkKeyArr) throws android.os.RemoteException {
            enforceCallingPermission();
            if (networkKeyArr != null && networkKeyArr.length > 0) {
                execute(new java.lang.Runnable() { // from class: android.net.NetworkRecommendationProvider.ServiceWrapper.1
                    @Override // java.lang.Runnable
                    public void run() {
                        android.net.NetworkRecommendationProvider.this.onRequestScores(networkKeyArr);
                    }
                });
            }
        }

        private void execute(java.lang.Runnable runnable) {
            if (this.mExecutor != null) {
                this.mExecutor.execute(runnable);
            } else {
                this.mHandler.post(runnable);
            }
        }

        private void enforceCallingPermission() {
            if (this.mContext != null) {
                this.mContext.enforceCallingOrSelfPermission(android.Manifest.permission.REQUEST_NETWORK_SCORES, "Permission denied.");
            }
        }
    }
}
