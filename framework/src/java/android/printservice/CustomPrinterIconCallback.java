package android.printservice;

/* loaded from: classes3.dex */
public final class CustomPrinterIconCallback {
    private static final java.lang.String LOG_TAG = "CustomPrinterIconCB";
    private final android.printservice.IPrintServiceClient mObserver;
    private final android.print.PrinterId mPrinterId;

    CustomPrinterIconCallback(android.print.PrinterId printerId, android.printservice.IPrintServiceClient iPrintServiceClient) {
        this.mPrinterId = printerId;
        this.mObserver = iPrintServiceClient;
    }

    public boolean onCustomPrinterIconLoaded(android.graphics.drawable.Icon icon) {
        try {
            this.mObserver.onCustomPrinterIconLoaded(this.mPrinterId, icon);
            return true;
        } catch (android.os.RemoteException e) {
            android.util.Log.e(LOG_TAG, "Could not update icon", e);
            return false;
        }
    }
}
