package android.app.pinner;

/* loaded from: classes.dex */
public class PinnerServiceClient {
    private static java.lang.String TAG = "PinnerServiceClient";

    public java.util.List<android.app.pinner.PinnedFileStat> getPinnerStats() {
        android.os.IBinder service = android.os.ServiceManager.getService("pinner");
        if (service == null) {
            android.util.Slog.w(TAG, "Failed to retrieve PinnerService. A common failure reason is due to a lack of selinux permissions.");
            return new java.util.ArrayList();
        }
        android.app.pinner.IPinnerService asInterface = android.app.pinner.IPinnerService.Stub.asInterface(service);
        if (asInterface == null) {
            android.util.Slog.w(TAG, "Failed to cast PinnerService.");
            return new java.util.ArrayList();
        }
        try {
            return asInterface.getPinnerStats();
        } catch (android.os.RemoteException e) {
            throw new java.lang.RuntimeException("Failed to retrieve stats from PinnerService");
        }
    }
}
