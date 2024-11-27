package android.widget;

/* loaded from: classes4.dex */
public abstract class Filter {
    private static final int FILTER_TOKEN = -791613427;
    private static final int FINISH_TOKEN = -559038737;
    private static final java.lang.String LOG_TAG = "Filter";
    private static final java.lang.String THREAD_NAME = "Filter";
    private android.widget.Filter.Delayer mDelayer;
    private final java.lang.Object mLock = new java.lang.Object();
    private android.os.Handler mResultHandler = new android.widget.Filter.ResultsHandler();
    private android.os.Handler mThreadHandler;

    public interface Delayer {
        long getPostingDelay(java.lang.CharSequence charSequence);
    }

    public interface FilterListener {
        void onFilterComplete(int i);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public static class FilterResults {
        public int count;
        public java.lang.Object values;
    }

    protected abstract android.widget.Filter.FilterResults performFiltering(java.lang.CharSequence charSequence);

    protected abstract void publishResults(java.lang.CharSequence charSequence, android.widget.Filter.FilterResults filterResults);

    public void setDelayer(android.widget.Filter.Delayer delayer) {
        synchronized (this.mLock) {
            this.mDelayer = delayer;
        }
    }

    public final void filter(java.lang.CharSequence charSequence) {
        filter(charSequence, null);
    }

    public final void filter(java.lang.CharSequence charSequence, android.widget.Filter.FilterListener filterListener) {
        synchronized (this.mLock) {
            if (this.mThreadHandler == null) {
                android.os.HandlerThread handlerThread = new android.os.HandlerThread("Filter", 10);
                handlerThread.start();
                this.mThreadHandler = new android.widget.Filter.RequestHandler(handlerThread.getLooper());
            }
            long postingDelay = this.mDelayer == null ? 0L : this.mDelayer.getPostingDelay(charSequence);
            android.os.Message obtainMessage = this.mThreadHandler.obtainMessage(FILTER_TOKEN);
            android.widget.Filter.RequestArguments requestArguments = new android.widget.Filter.RequestArguments();
            requestArguments.constraint = charSequence != null ? charSequence.toString() : null;
            requestArguments.listener = filterListener;
            obtainMessage.obj = requestArguments;
            this.mThreadHandler.removeMessages(FILTER_TOKEN);
            this.mThreadHandler.removeMessages(FINISH_TOKEN);
            this.mThreadHandler.sendMessageDelayed(obtainMessage, postingDelay);
        }
    }

    public java.lang.CharSequence convertResultToString(java.lang.Object obj) {
        return obj == null ? "" : obj.toString();
    }

    private class RequestHandler extends android.os.Handler {
        public RequestHandler(android.os.Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(android.os.Message message) {
            int i = message.what;
            switch (i) {
                case android.widget.Filter.FILTER_TOKEN /* -791613427 */:
                    android.widget.Filter.RequestArguments requestArguments = (android.widget.Filter.RequestArguments) message.obj;
                    try {
                        try {
                            requestArguments.results = android.widget.Filter.this.performFiltering(requestArguments.constraint);
                        } catch (java.lang.Exception e) {
                            requestArguments.results = new android.widget.Filter.FilterResults();
                            android.util.Log.w("Filter", "An exception occured during performFiltering()!", e);
                        }
                        synchronized (android.widget.Filter.this.mLock) {
                            if (android.widget.Filter.this.mThreadHandler != null) {
                                android.widget.Filter.this.mThreadHandler.sendMessageDelayed(android.widget.Filter.this.mThreadHandler.obtainMessage(android.widget.Filter.FINISH_TOKEN), 3000L);
                            }
                        }
                        return;
                    } finally {
                        android.os.Message obtainMessage = android.widget.Filter.this.mResultHandler.obtainMessage(i);
                        obtainMessage.obj = requestArguments;
                        obtainMessage.sendToTarget();
                    }
                case android.widget.Filter.FINISH_TOKEN /* -559038737 */:
                    synchronized (android.widget.Filter.this.mLock) {
                        if (android.widget.Filter.this.mThreadHandler != null) {
                            android.widget.Filter.this.mThreadHandler.getLooper().quit();
                            android.widget.Filter.this.mThreadHandler = null;
                        }
                    }
                    return;
                default:
                    return;
            }
        }
    }

    private class ResultsHandler extends android.os.Handler {
        private ResultsHandler() {
        }

        @Override // android.os.Handler
        public void handleMessage(android.os.Message message) {
            android.widget.Filter.RequestArguments requestArguments = (android.widget.Filter.RequestArguments) message.obj;
            android.widget.Filter.this.publishResults(requestArguments.constraint, requestArguments.results);
            if (requestArguments.listener != null) {
                requestArguments.listener.onFilterComplete(requestArguments.results != null ? requestArguments.results.count : -1);
            }
        }
    }

    private static class RequestArguments {
        java.lang.CharSequence constraint;
        android.widget.Filter.FilterListener listener;
        android.widget.Filter.FilterResults results;

        private RequestArguments() {
        }
    }
}
