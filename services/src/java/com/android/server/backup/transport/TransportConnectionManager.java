package com.android.server.backup.transport;

/* loaded from: classes.dex */
public class TransportConnectionManager {
    private static final java.lang.String TAG = "TransportConnectionManager";
    private final android.content.Context mContext;
    private final java.util.function.Function<android.content.ComponentName, android.content.Intent> mIntentFunction;
    private java.util.Map<com.android.server.backup.transport.TransportConnection, java.lang.String> mTransportClientsCallerMap;
    private int mTransportClientsCreated;
    private final java.lang.Object mTransportClientsLock;
    private final com.android.server.backup.transport.TransportStats mTransportStats;
    private final int mUserId;

    /* JADX INFO: Access modifiers changed from: private */
    public static android.content.Intent getRealTransportIntent(android.content.ComponentName componentName) {
        return new android.content.Intent(com.android.server.backup.TransportManager.SERVICE_ACTION_TRANSPORT_HOST).setComponent(componentName);
    }

    public TransportConnectionManager(int i, android.content.Context context, com.android.server.backup.transport.TransportStats transportStats) {
        this(i, context, transportStats, new java.util.function.Function() { // from class: com.android.server.backup.transport.TransportConnectionManager$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final java.lang.Object apply(java.lang.Object obj) {
                android.content.Intent realTransportIntent;
                realTransportIntent = com.android.server.backup.transport.TransportConnectionManager.getRealTransportIntent((android.content.ComponentName) obj);
                return realTransportIntent;
            }
        });
    }

    private TransportConnectionManager(int i, android.content.Context context, com.android.server.backup.transport.TransportStats transportStats, java.util.function.Function<android.content.ComponentName, android.content.Intent> function) {
        this.mTransportClientsLock = new java.lang.Object();
        this.mTransportClientsCreated = 0;
        this.mTransportClientsCallerMap = new java.util.WeakHashMap();
        this.mUserId = i;
        this.mContext = context;
        this.mTransportStats = transportStats;
        this.mIntentFunction = function;
    }

    public com.android.server.backup.transport.TransportConnection getTransportClient(android.content.ComponentName componentName, java.lang.String str) {
        return getTransportClient(componentName, (android.os.Bundle) null, str);
    }

    public com.android.server.backup.transport.TransportConnection getTransportClient(android.content.ComponentName componentName, @android.annotation.Nullable android.os.Bundle bundle, java.lang.String str) {
        android.content.Intent apply = this.mIntentFunction.apply(componentName);
        if (bundle != null) {
            apply.putExtras(bundle);
        }
        return getTransportClient(componentName, str, apply);
    }

    private com.android.server.backup.transport.TransportConnection getTransportClient(android.content.ComponentName componentName, java.lang.String str, android.content.Intent intent) {
        com.android.server.backup.transport.TransportConnection transportConnection;
        synchronized (this.mTransportClientsLock) {
            transportConnection = new com.android.server.backup.transport.TransportConnection(this.mUserId, this.mContext, this.mTransportStats, intent, componentName, java.lang.Integer.toString(this.mTransportClientsCreated), str);
            this.mTransportClientsCallerMap.put(transportConnection, str);
            this.mTransportClientsCreated++;
            com.android.server.backup.transport.TransportUtils.log(3, TAG, com.android.server.backup.transport.TransportUtils.formatMessage(null, str, "Retrieving " + transportConnection));
        }
        return transportConnection;
    }

    public void disposeOfTransportClient(com.android.server.backup.transport.TransportConnection transportConnection, java.lang.String str) {
        transportConnection.unbind(str);
        transportConnection.markAsDisposed();
        synchronized (this.mTransportClientsLock) {
            com.android.server.backup.transport.TransportUtils.log(3, TAG, com.android.server.backup.transport.TransportUtils.formatMessage(null, str, "Disposing of " + transportConnection));
            this.mTransportClientsCallerMap.remove(transportConnection);
        }
    }

    public void dump(java.io.PrintWriter printWriter) {
        printWriter.println("Transport clients created: " + this.mTransportClientsCreated);
        synchronized (this.mTransportClientsLock) {
            try {
                printWriter.println("Current transport clients: " + this.mTransportClientsCallerMap.size());
                for (com.android.server.backup.transport.TransportConnection transportConnection : this.mTransportClientsCallerMap.keySet()) {
                    printWriter.println("    " + transportConnection + " [" + this.mTransportClientsCallerMap.get(transportConnection) + "]");
                    java.util.Iterator<java.lang.String> it = transportConnection.getLogBuffer().iterator();
                    while (it.hasNext()) {
                        printWriter.println("        " + it.next());
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }
}
