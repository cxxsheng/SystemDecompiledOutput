package android.print;

/* loaded from: classes3.dex */
public abstract class PrintDocumentAdapter {
    public static final java.lang.String EXTRA_PRINT_PREVIEW = "EXTRA_PRINT_PREVIEW";

    public abstract void onLayout(android.print.PrintAttributes printAttributes, android.print.PrintAttributes printAttributes2, android.os.CancellationSignal cancellationSignal, android.print.PrintDocumentAdapter.LayoutResultCallback layoutResultCallback, android.os.Bundle bundle);

    public abstract void onWrite(android.print.PageRange[] pageRangeArr, android.os.ParcelFileDescriptor parcelFileDescriptor, android.os.CancellationSignal cancellationSignal, android.print.PrintDocumentAdapter.WriteResultCallback writeResultCallback);

    public void onStart() {
    }

    public void onFinish() {
    }

    public static abstract class WriteResultCallback {
        public void onWriteFinished(android.print.PageRange[] pageRangeArr) {
        }

        public void onWriteFailed(java.lang.CharSequence charSequence) {
        }

        public void onWriteCancelled() {
        }
    }

    public static abstract class LayoutResultCallback {
        public void onLayoutFinished(android.print.PrintDocumentInfo printDocumentInfo, boolean z) {
        }

        public void onLayoutFailed(java.lang.CharSequence charSequence) {
        }

        public void onLayoutCancelled() {
        }
    }
}
