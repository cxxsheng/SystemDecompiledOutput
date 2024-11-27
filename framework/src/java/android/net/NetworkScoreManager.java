package android.net;

@android.annotation.SystemApi
@java.lang.Deprecated
/* loaded from: classes2.dex */
public class NetworkScoreManager {

    @java.lang.Deprecated
    public static final java.lang.String ACTION_CHANGE_ACTIVE = "android.net.scoring.CHANGE_ACTIVE";
    public static final java.lang.String ACTION_CUSTOM_ENABLE = "android.net.scoring.CUSTOM_ENABLE";
    public static final java.lang.String ACTION_RECOMMEND_NETWORKS = "android.net.action.RECOMMEND_NETWORKS";
    public static final java.lang.String ACTION_SCORER_CHANGED = "android.net.scoring.SCORER_CHANGED";

    @java.lang.Deprecated
    public static final java.lang.String ACTION_SCORE_NETWORKS = "android.net.scoring.SCORE_NETWORKS";

    @java.lang.Deprecated
    public static final java.lang.String EXTRA_NETWORKS_TO_SCORE = "networksToScore";
    public static final java.lang.String EXTRA_NEW_SCORER = "newScorer";

    @java.lang.Deprecated
    public static final java.lang.String EXTRA_PACKAGE_NAME = "packageName";
    public static final java.lang.String NETWORK_AVAILABLE_NOTIFICATION_CHANNEL_ID_META_DATA = "android.net.wifi.notification_channel_id_network_available";
    public static final int RECOMMENDATIONS_ENABLED_FORCED_OFF = -1;
    public static final int RECOMMENDATIONS_ENABLED_OFF = 0;
    public static final int RECOMMENDATIONS_ENABLED_ON = 1;
    public static final java.lang.String RECOMMENDATION_SERVICE_LABEL_META_DATA = "android.net.scoring.recommendation_service_label";
    public static final int SCORE_FILTER_CURRENT_NETWORK = 1;
    public static final int SCORE_FILTER_NONE = 0;
    public static final int SCORE_FILTER_SCAN_RESULTS = 2;
    private static final java.lang.String TAG = "NetworkScoreManager";
    public static final java.lang.String USE_OPEN_WIFI_PACKAGE_META_DATA = "android.net.wifi.use_open_wifi_package";
    private final android.content.Context mContext;
    private final android.net.INetworkScoreService mService = android.net.INetworkScoreService.Stub.asInterface(android.os.ServiceManager.getServiceOrThrow(android.content.Context.NETWORK_SCORE_SERVICE));

    @android.annotation.SystemApi
    public static abstract class NetworkScoreCallback {
        public abstract void onScoresInvalidated();

        public abstract void onScoresUpdated(java.util.Collection<android.net.ScoredNetwork> collection);
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface RecommendationsEnabledSetting {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface ScoreUpdateFilter {
    }

    public NetworkScoreManager(android.content.Context context) throws android.os.ServiceManager.ServiceNotFoundException {
        this.mContext = context;
    }

    public java.lang.String getActiveScorerPackage() {
        try {
            return this.mService.getActiveScorerPackage();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public android.net.NetworkScorerAppData getActiveScorer() {
        try {
            return this.mService.getActiveScorer();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public java.util.List<android.net.NetworkScorerAppData> getAllValidScorers() {
        try {
            return this.mService.getAllValidScorers();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean updateScores(android.net.ScoredNetwork[] scoredNetworkArr) throws java.lang.SecurityException {
        try {
            return this.mService.updateScores(scoredNetworkArr);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean clearScores() throws java.lang.SecurityException {
        try {
            return this.mService.clearScores();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public boolean setActiveScorer(java.lang.String str) throws java.lang.SecurityException {
        try {
            return this.mService.setActiveScorer(str);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void disableScoring() throws java.lang.SecurityException {
        try {
            this.mService.disableScoring();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean requestScores(android.net.NetworkKey[] networkKeyArr) throws java.lang.SecurityException {
        try {
            return this.mService.requestScores(networkKeyArr);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public boolean requestScores(java.util.Collection<android.net.NetworkKey> collection) throws java.lang.SecurityException {
        return requestScores((android.net.NetworkKey[]) collection.toArray(new android.net.NetworkKey[0]));
    }

    @java.lang.Deprecated
    public void registerNetworkScoreCache(int i, android.net.INetworkScoreCache iNetworkScoreCache) {
        registerNetworkScoreCache(i, iNetworkScoreCache, 0);
    }

    public void registerNetworkScoreCache(int i, android.net.INetworkScoreCache iNetworkScoreCache, int i2) {
        try {
            this.mService.registerNetworkScoreCache(i, iNetworkScoreCache, i2);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void unregisterNetworkScoreCache(int i, android.net.INetworkScoreCache iNetworkScoreCache) {
        try {
            this.mService.unregisterNetworkScoreCache(i, iNetworkScoreCache);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    class NetworkScoreCallbackProxy extends android.net.INetworkScoreCache.Stub {
        private final android.net.NetworkScoreManager.NetworkScoreCallback mCallback;
        private final java.util.concurrent.Executor mExecutor;

        NetworkScoreCallbackProxy(java.util.concurrent.Executor executor, android.net.NetworkScoreManager.NetworkScoreCallback networkScoreCallback) {
            this.mExecutor = executor;
            this.mCallback = networkScoreCallback;
        }

        @Override // android.net.INetworkScoreCache
        public void updateScores(final java.util.List<android.net.ScoredNetwork> list) {
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                this.mExecutor.execute(new java.lang.Runnable() { // from class: android.net.NetworkScoreManager$NetworkScoreCallbackProxy$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.net.NetworkScoreManager.NetworkScoreCallbackProxy.this.lambda$updateScores$0(list);
                    }
                });
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$updateScores$0(java.util.List list) {
            this.mCallback.onScoresUpdated(list);
        }

        @Override // android.net.INetworkScoreCache
        public void clearScores() {
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                this.mExecutor.execute(new java.lang.Runnable() { // from class: android.net.NetworkScoreManager$NetworkScoreCallbackProxy$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.net.NetworkScoreManager.NetworkScoreCallbackProxy.this.lambda$clearScores$1();
                    }
                });
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$clearScores$1() {
            this.mCallback.onScoresInvalidated();
        }
    }

    @android.annotation.SystemApi
    public void registerNetworkScoreCallback(int i, int i2, java.util.concurrent.Executor executor, android.net.NetworkScoreManager.NetworkScoreCallback networkScoreCallback) throws java.lang.SecurityException {
        if (networkScoreCallback == null || executor == null) {
            throw new java.lang.IllegalArgumentException("callback / executor cannot be null");
        }
        android.util.Log.v(TAG, "registerNetworkScoreCallback: callback=" + networkScoreCallback + ", executor=" + executor);
        registerNetworkScoreCache(i, new android.net.NetworkScoreManager.NetworkScoreCallbackProxy(executor, networkScoreCallback), i2);
    }

    public boolean isCallerActiveScorer(int i) {
        try {
            return this.mService.isCallerActiveScorer(i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }
}
