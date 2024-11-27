package android.print;

/* loaded from: classes3.dex */
public class PrintServiceRecommendationsLoader extends android.content.Loader<java.util.List<android.printservice.recommendation.RecommendationInfo>> {
    private final android.os.Handler mHandler;
    private android.print.PrintManager.PrintServiceRecommendationsChangeListener mListener;
    private final android.print.PrintManager mPrintManager;

    public PrintServiceRecommendationsLoader(android.print.PrintManager printManager, android.content.Context context) {
        super((android.content.Context) com.android.internal.util.Preconditions.checkNotNull(context));
        this.mHandler = new android.print.PrintServiceRecommendationsLoader.MyHandler();
        this.mPrintManager = (android.print.PrintManager) com.android.internal.util.Preconditions.checkNotNull(printManager);
    }

    @Override // android.content.Loader
    protected void onForceLoad() {
        queueNewResult();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void queueNewResult() {
        android.os.Message obtainMessage = this.mHandler.obtainMessage(0);
        obtainMessage.obj = this.mPrintManager.getPrintServiceRecommendations();
        this.mHandler.sendMessage(obtainMessage);
    }

    @Override // android.content.Loader
    protected void onStartLoading() {
        this.mListener = new android.print.PrintManager.PrintServiceRecommendationsChangeListener() { // from class: android.print.PrintServiceRecommendationsLoader.1
            @Override // android.print.PrintManager.PrintServiceRecommendationsChangeListener
            public void onPrintServiceRecommendationsChanged() {
                android.print.PrintServiceRecommendationsLoader.this.queueNewResult();
            }
        };
        this.mPrintManager.addPrintServiceRecommendationsChangeListener(this.mListener, null);
        deliverResult(this.mPrintManager.getPrintServiceRecommendations());
    }

    @Override // android.content.Loader
    protected void onStopLoading() {
        if (this.mListener != null) {
            this.mPrintManager.removePrintServiceRecommendationsChangeListener(this.mListener);
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
            super(android.print.PrintServiceRecommendationsLoader.this.getContext().getMainLooper());
        }

        @Override // android.os.Handler
        public void handleMessage(android.os.Message message) {
            if (android.print.PrintServiceRecommendationsLoader.this.isStarted()) {
                android.print.PrintServiceRecommendationsLoader.this.deliverResult((java.util.List) message.obj);
            }
        }
    }
}
