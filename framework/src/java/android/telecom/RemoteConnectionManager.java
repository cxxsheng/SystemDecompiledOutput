package android.telecom;

/* loaded from: classes3.dex */
public class RemoteConnectionManager {
    private final android.telecom.ConnectionService mOurConnectionServiceImpl;
    private final java.util.Map<android.content.ComponentName, android.telecom.RemoteConnectionService> mRemoteConnectionServices = new java.util.HashMap();

    public RemoteConnectionManager(android.telecom.ConnectionService connectionService) {
        this.mOurConnectionServiceImpl = connectionService;
    }

    void addConnectionService(final android.content.ComponentName componentName, final com.android.internal.telecom.IConnectionService iConnectionService) {
        this.mRemoteConnectionServices.computeIfAbsent(componentName, new java.util.function.Function() { // from class: android.telecom.RemoteConnectionManager$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final java.lang.Object apply(java.lang.Object obj) {
                android.telecom.RemoteConnectionService lambda$addConnectionService$0;
                lambda$addConnectionService$0 = android.telecom.RemoteConnectionManager.this.lambda$addConnectionService$0(iConnectionService, componentName, (android.content.ComponentName) obj);
                return lambda$addConnectionService$0;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ android.telecom.RemoteConnectionService lambda$addConnectionService$0(com.android.internal.telecom.IConnectionService iConnectionService, android.content.ComponentName componentName, android.content.ComponentName componentName2) {
        try {
            return new android.telecom.RemoteConnectionService(iConnectionService, this.mOurConnectionServiceImpl);
        } catch (android.os.RemoteException e) {
            android.telecom.Log.w(this, "error when addConnectionService of %s: %s", componentName, e.toString());
            return null;
        }
    }

    public android.telecom.RemoteConnection createRemoteConnection(android.telecom.PhoneAccountHandle phoneAccountHandle, android.telecom.ConnectionRequest connectionRequest, boolean z) {
        if (connectionRequest.getAccountHandle() == null) {
            throw new java.lang.IllegalArgumentException("accountHandle must be specified.");
        }
        android.content.ComponentName componentName = connectionRequest.getAccountHandle().getComponentName();
        android.telecom.RemoteConnectionService remoteConnectionService = this.mRemoteConnectionServices.get(componentName);
        if (remoteConnectionService == null) {
            throw new java.lang.UnsupportedOperationException("accountHandle not supported: " + componentName);
        }
        return remoteConnectionService.createRemoteConnection(phoneAccountHandle, connectionRequest, z);
    }

    public android.telecom.RemoteConference createRemoteConference(android.telecom.PhoneAccountHandle phoneAccountHandle, android.telecom.ConnectionRequest connectionRequest, boolean z) {
        if (connectionRequest.getAccountHandle() == null) {
            throw new java.lang.IllegalArgumentException("accountHandle must be specified.");
        }
        android.content.ComponentName componentName = connectionRequest.getAccountHandle().getComponentName();
        android.telecom.RemoteConnectionService remoteConnectionService = this.mRemoteConnectionServices.get(componentName);
        if (remoteConnectionService == null) {
            throw new java.lang.UnsupportedOperationException("accountHandle not supported: " + componentName);
        }
        return remoteConnectionService.createRemoteConference(phoneAccountHandle, connectionRequest, z);
    }

    public void conferenceRemoteConnections(android.telecom.RemoteConnection remoteConnection, android.telecom.RemoteConnection remoteConnection2) {
        if (remoteConnection.getConnectionService() == remoteConnection2.getConnectionService()) {
            try {
                remoteConnection.getConnectionService().conference(remoteConnection.getId(), remoteConnection2.getId(), null);
            } catch (android.os.RemoteException e) {
            }
        } else {
            android.telecom.Log.w(this, "Request to conference incompatible remote connections (%s,%s) (%s,%s)", remoteConnection.getConnectionService(), remoteConnection.getId(), remoteConnection2.getConnectionService(), remoteConnection2.getId());
        }
    }
}
