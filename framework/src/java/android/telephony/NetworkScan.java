package android.telephony;

/* loaded from: classes3.dex */
public class NetworkScan {
    public static final int ERROR_INTERRUPTED = 10002;
    public static final int ERROR_INVALID_SCAN = 2;
    public static final int ERROR_INVALID_SCANID = 10001;
    public static final int ERROR_MODEM_ERROR = 1;
    public static final int ERROR_MODEM_UNAVAILABLE = 3;
    public static final int ERROR_RADIO_INTERFACE_ERROR = 10000;
    public static final int ERROR_UNSUPPORTED = 4;
    public static final int SUCCESS = 0;
    private static final java.lang.String TAG = "NetworkScan";
    private final int mScanId;
    private final int mSubId;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface ScanErrorCode {
    }

    public void stopScan() {
        com.android.internal.telephony.ITelephony iTelephony = getITelephony();
        if (iTelephony == null) {
            com.android.telephony.Rlog.e(TAG, "Failed to get the ITelephony instance.");
        }
        try {
            iTelephony.stopNetworkScan(this.mSubId, this.mScanId);
        } catch (android.os.RemoteException e) {
            com.android.telephony.Rlog.e(TAG, "stopNetworkScan  RemoteException", e);
        } catch (java.lang.IllegalArgumentException e2) {
            com.android.telephony.Rlog.d(TAG, "stopNetworkScan - no active scan for ScanID=" + this.mScanId);
        } catch (java.lang.RuntimeException e3) {
            com.android.telephony.Rlog.e(TAG, "stopNetworkScan  RuntimeException", e3);
        }
    }

    @java.lang.Deprecated
    public void stop() throws android.os.RemoteException {
        try {
            stopScan();
        } catch (java.lang.RuntimeException e) {
            throw new android.os.RemoteException("Failed to stop the network scan with id " + this.mScanId);
        }
    }

    public NetworkScan(int i, int i2) {
        this.mScanId = i;
        this.mSubId = i2;
    }

    private com.android.internal.telephony.ITelephony getITelephony() {
        return com.android.internal.telephony.ITelephony.Stub.asInterface(android.telephony.TelephonyFrameworkInitializer.getTelephonyServiceManager().getTelephonyServiceRegisterer().get());
    }
}
