package android.printservice;

/* loaded from: classes3.dex */
public final class PrintDocument {
    private static final java.lang.String LOG_TAG = "PrintDocument";
    private final android.print.PrintDocumentInfo mInfo;
    private final android.print.PrintJobId mPrintJobId;
    private final android.printservice.IPrintServiceClient mPrintServiceClient;

    PrintDocument(android.print.PrintJobId printJobId, android.printservice.IPrintServiceClient iPrintServiceClient, android.print.PrintDocumentInfo printDocumentInfo) {
        this.mPrintJobId = printJobId;
        this.mPrintServiceClient = iPrintServiceClient;
        this.mInfo = printDocumentInfo;
    }

    public android.print.PrintDocumentInfo getInfo() {
        android.printservice.PrintService.throwIfNotCalledOnMainThread();
        return this.mInfo;
    }

    /* JADX WARN: Not initialized variable reg: 3, insn: 0x0025: MOVE (r2 I:??[OBJECT, ARRAY]) = (r3 I:??[OBJECT, ARRAY]), block:B:28:0x0025 */
    /* JADX WARN: Removed duplicated region for block: B:31:0x0048 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public android.os.ParcelFileDescriptor getData() {
        android.os.ParcelFileDescriptor parcelFileDescriptor;
        java.lang.AutoCloseable autoCloseable;
        android.os.ParcelFileDescriptor parcelFileDescriptor2;
        android.printservice.PrintService.throwIfNotCalledOnMainThread();
        java.lang.AutoCloseable autoCloseable2 = null;
        try {
            try {
                try {
                    android.os.ParcelFileDescriptor[] createPipe = android.os.ParcelFileDescriptor.createPipe();
                    parcelFileDescriptor2 = createPipe[0];
                    parcelFileDescriptor = createPipe[1];
                } catch (android.os.RemoteException e) {
                    e = e;
                    parcelFileDescriptor = null;
                } catch (java.io.IOException e2) {
                    e = e2;
                    parcelFileDescriptor = null;
                } catch (java.lang.Throwable th) {
                    th = th;
                    if (autoCloseable2 != null) {
                    }
                    throw th;
                }
                try {
                    this.mPrintServiceClient.writePrintJobData(parcelFileDescriptor, this.mPrintJobId);
                    if (parcelFileDescriptor != null) {
                        try {
                            parcelFileDescriptor.close();
                        } catch (java.io.IOException e3) {
                        }
                    }
                    return parcelFileDescriptor2;
                } catch (android.os.RemoteException e4) {
                    e = e4;
                    android.util.Log.e(LOG_TAG, "Error calling getting print job data!", e);
                    if (parcelFileDescriptor != null) {
                        parcelFileDescriptor.close();
                    }
                    return null;
                } catch (java.io.IOException e5) {
                    e = e5;
                    android.util.Log.e(LOG_TAG, "Error calling getting print job data!", e);
                    if (parcelFileDescriptor != null) {
                        parcelFileDescriptor.close();
                    }
                    return null;
                }
            } catch (java.lang.Throwable th2) {
                th = th2;
                autoCloseable2 = autoCloseable;
                if (autoCloseable2 != null) {
                    try {
                        autoCloseable2.close();
                    } catch (java.io.IOException e6) {
                    }
                }
                throw th;
            }
        } catch (java.io.IOException e7) {
        }
    }
}
