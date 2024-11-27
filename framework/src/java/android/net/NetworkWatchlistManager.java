package android.net;

@android.annotation.SystemApi(client = android.annotation.SystemApi.Client.MODULE_LIBRARIES)
/* loaded from: classes2.dex */
public class NetworkWatchlistManager {
    private static final java.lang.String SHARED_MEMORY_TAG = "NETWORK_WATCHLIST_SHARED_MEMORY";
    private static final java.lang.String TAG = "NetworkWatchlistManager";
    private final android.content.Context mContext;
    private final com.android.internal.net.INetworkWatchlistManager mNetworkWatchlistManager;

    public NetworkWatchlistManager(android.content.Context context, com.android.internal.net.INetworkWatchlistManager iNetworkWatchlistManager) {
        this.mContext = context;
        this.mNetworkWatchlistManager = iNetworkWatchlistManager;
    }

    public NetworkWatchlistManager(android.content.Context context) {
        this.mContext = (android.content.Context) com.android.internal.util.Preconditions.checkNotNull(context, "missing context");
        this.mNetworkWatchlistManager = com.android.internal.net.INetworkWatchlistManager.Stub.asInterface(android.os.ServiceManager.getService(android.content.Context.NETWORK_WATCHLIST_SERVICE));
    }

    public void reportWatchlistIfNecessary() {
        try {
            this.mNetworkWatchlistManager.reportWatchlistIfNecessary();
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Cannot report records", e);
            e.rethrowFromSystemServer();
        }
    }

    public void reloadWatchlist() {
        try {
            this.mNetworkWatchlistManager.reloadWatchlist();
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Unable to reload watchlist");
            e.rethrowFromSystemServer();
        }
    }

    public byte[] getWatchlistConfigHash() {
        try {
            return this.mNetworkWatchlistManager.getWatchlistConfigHash();
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Unable to get watchlist config hash");
            throw e.rethrowFromSystemServer();
        }
    }
}
