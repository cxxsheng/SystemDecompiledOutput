package android.filterpacks.base;

/* loaded from: classes.dex */
public class CallbackFilter extends android.filterfw.core.Filter {

    @android.filterfw.core.GenerateFinalPort(hasDefault = true, name = "callUiThread")
    private boolean mCallbacksOnUiThread;

    @android.filterfw.core.GenerateFieldPort(hasDefault = true, name = "listener")
    private android.filterfw.core.FilterContext.OnFrameReceivedListener mListener;
    private android.os.Handler mUiThreadHandler;

    @android.filterfw.core.GenerateFieldPort(hasDefault = true, name = "userData")
    private java.lang.Object mUserData;

    private class CallbackRunnable implements java.lang.Runnable {
        private android.filterfw.core.Filter mFilter;
        private android.filterfw.core.Frame mFrame;
        private android.filterfw.core.FilterContext.OnFrameReceivedListener mListener;
        private java.lang.Object mUserData;

        public CallbackRunnable(android.filterfw.core.FilterContext.OnFrameReceivedListener onFrameReceivedListener, android.filterfw.core.Filter filter, android.filterfw.core.Frame frame, java.lang.Object obj) {
            this.mListener = onFrameReceivedListener;
            this.mFilter = filter;
            this.mFrame = frame;
            this.mUserData = obj;
        }

        @Override // java.lang.Runnable
        public void run() {
            this.mListener.onFrameReceived(this.mFilter, this.mFrame, this.mUserData);
            this.mFrame.release();
        }
    }

    public CallbackFilter(java.lang.String str) {
        super(str);
        this.mCallbacksOnUiThread = true;
    }

    @Override // android.filterfw.core.Filter
    public void setupPorts() {
        addInputPort("frame");
    }

    @Override // android.filterfw.core.Filter
    public void prepare(android.filterfw.core.FilterContext filterContext) {
        if (this.mCallbacksOnUiThread) {
            this.mUiThreadHandler = new android.os.Handler(android.os.Looper.getMainLooper());
        }
    }

    @Override // android.filterfw.core.Filter
    public void process(android.filterfw.core.FilterContext filterContext) {
        android.filterfw.core.Frame pullInput = pullInput("frame");
        if (this.mListener != null) {
            if (this.mCallbacksOnUiThread) {
                pullInput.retain();
                if (!this.mUiThreadHandler.post(new android.filterpacks.base.CallbackFilter.CallbackRunnable(this.mListener, this, pullInput, this.mUserData))) {
                    throw new java.lang.RuntimeException("Unable to send callback to UI thread!");
                }
                return;
            }
            this.mListener.onFrameReceived(this, pullInput, this.mUserData);
            return;
        }
        throw new java.lang.RuntimeException("CallbackFilter received frame, but no listener set!");
    }
}
