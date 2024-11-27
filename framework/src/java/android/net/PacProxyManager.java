package android.net;

@android.annotation.SystemApi(client = android.annotation.SystemApi.Client.MODULE_LIBRARIES)
/* loaded from: classes2.dex */
public class PacProxyManager {
    private final android.content.Context mContext;
    private final java.util.HashMap<android.net.PacProxyManager.PacProxyInstalledListener, android.net.PacProxyManager.PacProxyInstalledListenerProxy> mListenerMap = new java.util.HashMap<>();
    private final android.net.IPacProxyManager mService;

    public interface PacProxyInstalledListener {
        void onPacProxyInstalled(android.net.Network network, android.net.ProxyInfo proxyInfo);
    }

    public PacProxyManager(android.content.Context context, android.net.IPacProxyManager iPacProxyManager) {
        java.util.Objects.requireNonNull(iPacProxyManager, "missing IPacProxyManager");
        this.mContext = context;
        this.mService = iPacProxyManager;
    }

    public void addPacProxyInstalledListener(java.util.concurrent.Executor executor, android.net.PacProxyManager.PacProxyInstalledListener pacProxyInstalledListener) {
        try {
            synchronized (this.mListenerMap) {
                android.net.PacProxyManager.PacProxyInstalledListenerProxy pacProxyInstalledListenerProxy = new android.net.PacProxyManager.PacProxyInstalledListenerProxy(executor, pacProxyInstalledListener);
                if (this.mListenerMap.putIfAbsent(pacProxyInstalledListener, pacProxyInstalledListenerProxy) != null) {
                    throw new java.lang.IllegalStateException("Listener is already added.");
                }
                this.mService.addListener(pacProxyInstalledListenerProxy);
            }
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void removePacProxyInstalledListener(android.net.PacProxyManager.PacProxyInstalledListener pacProxyInstalledListener) {
        try {
            synchronized (this.mListenerMap) {
                android.net.PacProxyManager.PacProxyInstalledListenerProxy remove = this.mListenerMap.remove(pacProxyInstalledListener);
                if (remove == null) {
                    return;
                }
                this.mService.removeListener(remove);
            }
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setCurrentProxyScriptUrl(android.net.ProxyInfo proxyInfo) {
        try {
            this.mService.setCurrentProxyScriptUrl(proxyInfo);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public class PacProxyInstalledListenerProxy extends android.net.IPacProxyInstalledListener.Stub {
        private final java.util.concurrent.Executor mExecutor;
        private final android.net.PacProxyManager.PacProxyInstalledListener mListener;

        PacProxyInstalledListenerProxy(java.util.concurrent.Executor executor, android.net.PacProxyManager.PacProxyInstalledListener pacProxyInstalledListener) {
            this.mExecutor = executor;
            this.mListener = pacProxyInstalledListener;
        }

        @Override // android.net.IPacProxyInstalledListener
        public void onPacProxyInstalled(final android.net.Network network, final android.net.ProxyInfo proxyInfo) {
            android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: android.net.PacProxyManager$PacProxyInstalledListenerProxy$$ExternalSyntheticLambda1
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    android.net.PacProxyManager.PacProxyInstalledListenerProxy.this.lambda$onPacProxyInstalled$1(network, proxyInfo);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onPacProxyInstalled$1(final android.net.Network network, final android.net.ProxyInfo proxyInfo) throws java.lang.Exception {
            this.mExecutor.execute(new java.lang.Runnable() { // from class: android.net.PacProxyManager$PacProxyInstalledListenerProxy$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    android.net.PacProxyManager.PacProxyInstalledListenerProxy.this.lambda$onPacProxyInstalled$0(network, proxyInfo);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onPacProxyInstalled$0(android.net.Network network, android.net.ProxyInfo proxyInfo) {
            this.mListener.onPacProxyInstalled(network, proxyInfo);
        }
    }
}
