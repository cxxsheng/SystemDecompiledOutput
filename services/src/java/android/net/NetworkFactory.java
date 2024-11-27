package android.net;

/* loaded from: classes.dex */
public class NetworkFactory {
    public static final int CMD_CANCEL_REQUEST = 2;
    public static final int CMD_REQUEST_NETWORK = 1;
    static final boolean DBG = true;
    static final boolean VDBG = false;
    private final java.lang.String LOG_TAG;
    final android.net.NetworkFactoryShim mImpl;
    private int mRefCount = 0;

    public NetworkFactory(android.os.Looper looper, android.content.Context context, java.lang.String str, @android.annotation.Nullable android.net.NetworkCapabilities networkCapabilities) {
        this.LOG_TAG = str;
        if (com.android.modules.utils.build.SdkLevel.isAtLeastS()) {
            this.mImpl = new android.net.NetworkFactoryImpl(this, looper, context, networkCapabilities);
        } else {
            this.mImpl = new android.net.NetworkFactoryLegacyImpl(this, looper, context, networkCapabilities);
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    public android.os.Message obtainMessage(int i, int i2, int i3, @android.annotation.Nullable java.lang.Object obj) {
        return this.mImpl.obtainMessage(i, i2, i3, obj);
    }

    public final android.os.Looper getLooper() {
        return this.mImpl.getLooper();
    }

    public void register() {
        this.mImpl.register(this.LOG_TAG);
    }

    public void registerIgnoringScore() {
        this.mImpl.registerIgnoringScore(this.LOG_TAG);
    }

    public void terminate() {
        this.mImpl.terminate();
    }

    protected final void reevaluateAllRequests() {
        this.mImpl.reevaluateAllRequests();
    }

    public boolean acceptRequest(@android.annotation.NonNull android.net.NetworkRequest networkRequest) {
        return true;
    }

    protected void releaseRequestAsUnfulfillableByAnyFactory(android.net.NetworkRequest networkRequest) {
        this.mImpl.releaseRequestAsUnfulfillableByAnyFactory(networkRequest);
    }

    protected void startNetwork() {
    }

    protected void stopNetwork() {
    }

    protected void needNetworkFor(@android.annotation.NonNull android.net.NetworkRequest networkRequest) {
        int i = this.mRefCount + 1;
        this.mRefCount = i;
        if (i == 1) {
            startNetwork();
        }
    }

    protected void releaseNetworkFor(@android.annotation.NonNull android.net.NetworkRequest networkRequest) {
        int i = this.mRefCount - 1;
        this.mRefCount = i;
        if (i == 0) {
            stopNetwork();
        }
    }

    @java.lang.Deprecated
    public void setScoreFilter(int i) {
        this.mImpl.setScoreFilter(i);
    }

    public void setScoreFilter(@android.annotation.NonNull android.net.NetworkScore networkScore) {
        this.mImpl.setScoreFilter(networkScore);
    }

    public void setCapabilityFilter(android.net.NetworkCapabilities networkCapabilities) {
        this.mImpl.setCapabilityFilter(networkCapabilities);
    }

    @com.android.internal.annotations.VisibleForTesting
    protected int getRequestCount() {
        return this.mImpl.getRequestCount();
    }

    public int getSerialNumber() {
        return this.mImpl.getSerialNumber();
    }

    public android.net.NetworkProvider getProvider() {
        return this.mImpl.getProvider();
    }

    protected void log(java.lang.String str) {
        android.util.Log.d(this.LOG_TAG, str);
    }

    public void dump(java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, java.lang.String[] strArr) {
        this.mImpl.dump(fileDescriptor, printWriter, strArr);
    }

    public java.lang.String toString() {
        return "{" + this.LOG_TAG + " " + this.mImpl.toString() + "}";
    }
}
