package android.os;

/* loaded from: classes3.dex */
public class SecurityStateManager {
    public static final java.lang.String KEY_KERNEL_VERSION = "kernel_version";
    public static final java.lang.String KEY_SYSTEM_SPL = "system_spl";
    public static final java.lang.String KEY_VENDOR_SPL = "vendor_spl";
    private final android.os.ISecurityStateManager mService;

    public SecurityStateManager(android.os.ISecurityStateManager iSecurityStateManager) {
        this.mService = (android.os.ISecurityStateManager) java.util.Objects.requireNonNull(iSecurityStateManager, "missing ISecurityStateManager");
    }

    public android.os.Bundle getGlobalSecurityState() {
        try {
            return this.mService.getGlobalSecurityState();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }
}
