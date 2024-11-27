package android.content.om;

/* loaded from: classes.dex */
public class OverlayManager {
    public static final long SELF_TARGETING_OVERLAY = 205919743;
    private static final long THROW_SECURITY_EXCEPTIONS = 147340954;
    private final android.content.Context mContext;
    private final com.android.internal.content.om.OverlayManagerImpl mOverlayManagerImpl;
    private final android.content.om.IOverlayManager mService;

    public OverlayManager(android.content.Context context, android.content.om.IOverlayManager iOverlayManager) {
        this.mContext = context;
        this.mService = iOverlayManager;
        this.mOverlayManagerImpl = new com.android.internal.content.om.OverlayManagerImpl(context);
    }

    public OverlayManager(android.content.Context context) {
        this(context, android.content.om.IOverlayManager.Stub.asInterface(android.os.ServiceManager.getService("overlay")));
    }

    @android.annotation.SystemApi
    public void setEnabledExclusiveInCategory(java.lang.String str, android.os.UserHandle userHandle) throws java.lang.SecurityException, java.lang.IllegalStateException {
        try {
            if (!this.mService.setEnabledExclusiveInCategory(str, userHandle.getIdentifier())) {
                throw new java.lang.IllegalStateException("setEnabledExclusiveInCategory failed");
            }
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        } catch (java.lang.SecurityException e2) {
            rethrowSecurityException(e2);
        }
    }

    @android.annotation.SystemApi
    public void setEnabled(java.lang.String str, boolean z, android.os.UserHandle userHandle) throws java.lang.SecurityException, java.lang.IllegalStateException {
        try {
            if (!this.mService.setEnabled(str, z, userHandle.getIdentifier())) {
                throw new java.lang.IllegalStateException("setEnabled failed");
            }
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        } catch (java.lang.SecurityException e2) {
            rethrowSecurityException(e2);
        }
    }

    @android.annotation.SystemApi
    public android.content.om.OverlayInfo getOverlayInfo(java.lang.String str, android.os.UserHandle userHandle) {
        try {
            return this.mService.getOverlayInfo(str, userHandle.getIdentifier());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public android.content.om.OverlayInfo getOverlayInfo(android.content.om.OverlayIdentifier overlayIdentifier, android.os.UserHandle userHandle) {
        try {
            return this.mService.getOverlayInfoByIdentifier(overlayIdentifier, userHandle.getIdentifier());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public java.util.List<android.content.om.OverlayInfo> getOverlayInfosForTarget(java.lang.String str, android.os.UserHandle userHandle) {
        try {
            return this.mService.getOverlayInfosForTarget(str, userHandle.getIdentifier());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void invalidateCachesForOverlay(java.lang.String str, android.os.UserHandle userHandle) {
        try {
            this.mService.invalidateCachesForOverlay(str, userHandle.getIdentifier());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    private void commitToSystemServer(android.content.om.OverlayManagerTransaction overlayManagerTransaction) {
        try {
            this.mService.commit(overlayManagerTransaction);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void commit(android.content.om.OverlayManagerTransaction overlayManagerTransaction) {
        if (overlayManagerTransaction.isSelfTargeting() || this.mService == null || this.mService.asBinder() == null) {
            try {
                commitSelfTarget(overlayManagerTransaction);
                return;
            } catch (android.content.pm.PackageManager.NameNotFoundException | java.io.IOException e) {
                throw new java.lang.RuntimeException(e);
            }
        }
        commitToSystemServer(overlayManagerTransaction);
    }

    private void rethrowSecurityException(java.lang.SecurityException securityException) {
        if (!android.compat.Compatibility.isChangeEnabled(THROW_SECURITY_EXCEPTIONS)) {
            throw new java.lang.IllegalStateException(securityException);
        }
        throw securityException;
    }

    void commitSelfTarget(android.content.om.OverlayManagerTransaction overlayManagerTransaction) throws android.content.pm.PackageManager.NameNotFoundException, java.io.IOException {
        synchronized (this.mOverlayManagerImpl) {
            this.mOverlayManagerImpl.commit(overlayManagerTransaction);
        }
    }

    public java.util.List<android.content.om.OverlayInfo> getOverlayInfosForTarget(java.lang.String str) {
        java.util.List<android.content.om.OverlayInfo> overlayInfosForTarget;
        synchronized (this.mOverlayManagerImpl) {
            overlayInfosForTarget = this.mOverlayManagerImpl.getOverlayInfosForTarget(str);
        }
        return overlayInfosForTarget;
    }
}
