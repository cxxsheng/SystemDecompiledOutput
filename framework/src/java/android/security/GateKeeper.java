package android.security;

/* loaded from: classes3.dex */
public abstract class GateKeeper {
    public static final long INVALID_SECURE_USER_ID = 0;

    private GateKeeper() {
    }

    public static android.service.gatekeeper.IGateKeeperService getService() {
        android.service.gatekeeper.IGateKeeperService asInterface = android.service.gatekeeper.IGateKeeperService.Stub.asInterface(android.os.ServiceManager.getService("android.service.gatekeeper.IGateKeeperService"));
        if (asInterface == null) {
            throw new java.lang.IllegalStateException("Gatekeeper service not available");
        }
        return asInterface;
    }

    public static long getSecureUserId() throws java.lang.IllegalStateException {
        return getSecureUserId(android.os.UserHandle.myUserId());
    }

    public static long getSecureUserId(int i) throws java.lang.IllegalStateException {
        try {
            return getService().getSecureUserId(i);
        } catch (android.os.RemoteException e) {
            throw new java.lang.IllegalStateException("Failed to obtain secure user ID from gatekeeper", e);
        }
    }
}
