package android.print;

/* loaded from: classes3.dex */
public class PrintServicesLoader extends android.content.Loader<java.util.List<android.printservice.PrintServiceInfo>> {
    private final android.os.Handler mHandler;
    private android.print.PrintManager.PrintServicesChangeListener mListener;
    private final android.print.PrintManager mPrintManager;
    private final int mSelectionFlags;

    public PrintServicesLoader(android.print.PrintManager printManager, android.content.Context context, int i) {
        super((android.content.Context) com.android.internal.util.Preconditions.checkNotNull(context));
        this.mHandler = new android.print.PrintServicesLoader.MyHandler();
        this.mPrintManager = (android.print.PrintManager) com.android.internal.util.Preconditions.checkNotNull(printManager);
        this.mSelectionFlags = com.android.internal.util.Preconditions.checkFlagsArgument(i, 3);
    }

    @Override // android.content.Loader
    protected void onForceLoad() {
        queueNewResult();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void queueNewResult() {
        android.os.Message obtainMessage = this.mHandler.obtainMessage(0);
        obtainMessage.obj = this.mPrintManager.getPrintServices(this.mSelectionFlags);
        this.mHandler.sendMessage(obtainMessage);
    }

    @Override // android.content.Loader
    protected void onStartLoading() {
        this.mListener = new android.print.PrintManager.PrintServicesChangeListener() { // from class: android.print.PrintServicesLoader.1
            @Override // android.print.PrintManager.PrintServicesChangeListener
            public void onPrintServicesChanged() {
                android.print.PrintServicesLoader.this.queueNewResult();
            }
        };
        this.mPrintManager.addPrintServicesChangeListener(this.mListener, null);
        deliverResult(this.mPrintManager.getPrintServices(this.mSelectionFlags));
    }

    @Override // android.content.Loader
    protected void onStopLoading() {
        if (this.mListener != null) {
            this.mPrintManager.removePrintServicesChangeListener(this.mListener);
            this.mListener = null;
        }
        this.mHandler.removeMessages(0);
    }

    @Override // android.content.Loader
    protected void onReset() {
        onStopLoading();
    }

    private class MyHandler extends android.os.Handler {
        public MyHandler() {
            super(android.print.PrintServicesLoader.this.getContext().getMainLooper());
        }

        @Override // android.os.Handler
        public void handleMessage(android.os.Message message) {
            if (android.print.PrintServicesLoader.this.isStarted()) {
                android.print.PrintServicesLoader.this.deliverResult((java.util.List) message.obj);
            }
        }
    }
}
