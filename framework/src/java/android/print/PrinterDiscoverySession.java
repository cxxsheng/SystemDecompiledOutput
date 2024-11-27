package android.print;

/* loaded from: classes3.dex */
public final class PrinterDiscoverySession {
    private static final java.lang.String LOG_TAG = "PrinterDiscoverySession";
    private static final int MSG_PRINTERS_ADDED = 1;
    private static final int MSG_PRINTERS_REMOVED = 2;
    private final android.os.Handler mHandler;
    private boolean mIsPrinterDiscoveryStarted;
    private android.print.PrinterDiscoverySession.OnPrintersChangeListener mListener;
    private final android.print.IPrintManager mPrintManager;
    private final int mUserId;
    private final java.util.LinkedHashMap<android.print.PrinterId, android.print.PrinterInfo> mPrinters = new java.util.LinkedHashMap<>();
    private android.print.IPrinterDiscoveryObserver mObserver = new android.print.PrinterDiscoverySession.PrinterDiscoveryObserver(this);

    public interface OnPrintersChangeListener {
        void onPrintersChanged();
    }

    PrinterDiscoverySession(android.print.IPrintManager iPrintManager, android.content.Context context, int i) {
        this.mPrintManager = iPrintManager;
        this.mUserId = i;
        this.mHandler = new android.print.PrinterDiscoverySession.SessionHandler(context.getMainLooper());
        try {
            this.mPrintManager.createPrinterDiscoverySession(this.mObserver, this.mUserId);
        } catch (android.os.RemoteException e) {
            android.util.Log.e(LOG_TAG, "Error creating printer discovery session", e);
        }
    }

    public final void startPrinterDiscovery(java.util.List<android.print.PrinterId> list) {
        if (isDestroyed()) {
            android.util.Log.w(LOG_TAG, "Ignoring start printers discovery - session destroyed");
            return;
        }
        if (!this.mIsPrinterDiscoveryStarted) {
            this.mIsPrinterDiscoveryStarted = true;
            try {
                this.mPrintManager.startPrinterDiscovery(this.mObserver, list, this.mUserId);
            } catch (android.os.RemoteException e) {
                android.util.Log.e(LOG_TAG, "Error starting printer discovery", e);
            }
        }
    }

    public final void stopPrinterDiscovery() {
        if (isDestroyed()) {
            android.util.Log.w(LOG_TAG, "Ignoring stop printers discovery - session destroyed");
            return;
        }
        if (this.mIsPrinterDiscoveryStarted) {
            this.mIsPrinterDiscoveryStarted = false;
            try {
                this.mPrintManager.stopPrinterDiscovery(this.mObserver, this.mUserId);
            } catch (android.os.RemoteException e) {
                android.util.Log.e(LOG_TAG, "Error stopping printer discovery", e);
            }
        }
    }

    public final void startPrinterStateTracking(android.print.PrinterId printerId) {
        if (isDestroyed()) {
            android.util.Log.w(LOG_TAG, "Ignoring start printer state tracking - session destroyed");
            return;
        }
        try {
            this.mPrintManager.startPrinterStateTracking(printerId, this.mUserId);
        } catch (android.os.RemoteException e) {
            android.util.Log.e(LOG_TAG, "Error starting printer state tracking", e);
        }
    }

    public final void stopPrinterStateTracking(android.print.PrinterId printerId) {
        if (isDestroyed()) {
            android.util.Log.w(LOG_TAG, "Ignoring stop printer state tracking - session destroyed");
            return;
        }
        try {
            this.mPrintManager.stopPrinterStateTracking(printerId, this.mUserId);
        } catch (android.os.RemoteException e) {
            android.util.Log.e(LOG_TAG, "Error stopping printer state tracking", e);
        }
    }

    public final void validatePrinters(java.util.List<android.print.PrinterId> list) {
        if (isDestroyed()) {
            android.util.Log.w(LOG_TAG, "Ignoring validate printers - session destroyed");
            return;
        }
        try {
            this.mPrintManager.validatePrinters(list, this.mUserId);
        } catch (android.os.RemoteException e) {
            android.util.Log.e(LOG_TAG, "Error validating printers", e);
        }
    }

    public final void destroy() {
        if (isDestroyed()) {
            android.util.Log.w(LOG_TAG, "Ignoring destroy - session destroyed");
        }
        destroyNoCheck();
    }

    public final java.util.List<android.print.PrinterInfo> getPrinters() {
        if (isDestroyed()) {
            android.util.Log.w(LOG_TAG, "Ignoring get printers - session destroyed");
            return java.util.Collections.emptyList();
        }
        return new java.util.ArrayList(this.mPrinters.values());
    }

    public final boolean isDestroyed() {
        throwIfNotCalledOnMainThread();
        return isDestroyedNoCheck();
    }

    public final boolean isPrinterDiscoveryStarted() {
        throwIfNotCalledOnMainThread();
        return this.mIsPrinterDiscoveryStarted;
    }

    public final void setOnPrintersChangeListener(android.print.PrinterDiscoverySession.OnPrintersChangeListener onPrintersChangeListener) {
        throwIfNotCalledOnMainThread();
        this.mListener = onPrintersChangeListener;
    }

    protected final void finalize() throws java.lang.Throwable {
        if (!isDestroyedNoCheck()) {
            android.util.Log.e(LOG_TAG, "Destroying leaked printer discovery session");
            destroyNoCheck();
        }
        super.finalize();
    }

    private boolean isDestroyedNoCheck() {
        return this.mObserver == null;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v0, types: [android.print.IPrinterDiscoveryObserver] */
    /* JADX WARN: Type inference failed for: r0v1, types: [android.print.IPrinterDiscoveryObserver] */
    /* JADX WARN: Type inference failed for: r0v3, types: [java.util.LinkedHashMap, java.util.LinkedHashMap<android.print.PrinterId, android.print.PrinterInfo>] */
    /* JADX WARN: Type inference failed for: r0v4 */
    private void destroyNoCheck() {
        stopPrinterDiscovery();
        ?? r0 = 0;
        r0 = 0;
        try {
            try {
                this.mPrintManager.destroyPrinterDiscoverySession(this.mObserver, this.mUserId);
            } catch (android.os.RemoteException e) {
                android.util.Log.e(LOG_TAG, "Error destroying printer discovery session", e);
            }
        } finally {
            this.mObserver = r0;
            this.mPrinters.clear();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handlePrintersAdded(java.util.List<android.print.PrinterInfo> list) {
        if (isDestroyed()) {
            return;
        }
        int i = 0;
        if (this.mPrinters.isEmpty()) {
            int size = list.size();
            while (i < size) {
                android.print.PrinterInfo printerInfo = list.get(i);
                this.mPrinters.put(printerInfo.getId(), printerInfo);
                i++;
            }
            notifyOnPrintersChanged();
            return;
        }
        android.util.ArrayMap arrayMap = new android.util.ArrayMap();
        int size2 = list.size();
        while (i < size2) {
            android.print.PrinterInfo printerInfo2 = list.get(i);
            arrayMap.put(printerInfo2.getId(), printerInfo2);
            i++;
        }
        for (android.print.PrinterId printerId : this.mPrinters.keySet()) {
            android.print.PrinterInfo printerInfo3 = (android.print.PrinterInfo) arrayMap.remove(printerId);
            if (printerInfo3 != null) {
                this.mPrinters.put(printerId, printerInfo3);
            }
        }
        this.mPrinters.putAll(arrayMap);
        notifyOnPrintersChanged();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handlePrintersRemoved(java.util.List<android.print.PrinterId> list) {
        if (isDestroyed()) {
            return;
        }
        int size = list.size();
        boolean z = false;
        for (int i = 0; i < size; i++) {
            if (this.mPrinters.remove(list.get(i)) != null) {
                z = true;
            }
        }
        if (z) {
            notifyOnPrintersChanged();
        }
    }

    private void notifyOnPrintersChanged() {
        if (this.mListener != null) {
            this.mListener.onPrintersChanged();
        }
    }

    private static void throwIfNotCalledOnMainThread() {
        if (!android.os.Looper.getMainLooper().isCurrentThread()) {
            throw new java.lang.IllegalAccessError("must be called from the main thread");
        }
    }

    private final class SessionHandler extends android.os.Handler {
        public SessionHandler(android.os.Looper looper) {
            super(looper, null, false);
        }

        @Override // android.os.Handler
        public void handleMessage(android.os.Message message) {
            switch (message.what) {
                case 1:
                    android.print.PrinterDiscoverySession.this.handlePrintersAdded((java.util.List) message.obj);
                    break;
                case 2:
                    android.print.PrinterDiscoverySession.this.handlePrintersRemoved((java.util.List) message.obj);
                    break;
            }
        }
    }

    public static final class PrinterDiscoveryObserver extends android.print.IPrinterDiscoveryObserver.Stub {
        private final java.lang.ref.WeakReference<android.print.PrinterDiscoverySession> mWeakSession;

        public PrinterDiscoveryObserver(android.print.PrinterDiscoverySession printerDiscoverySession) {
            this.mWeakSession = new java.lang.ref.WeakReference<>(printerDiscoverySession);
        }

        @Override // android.print.IPrinterDiscoveryObserver
        public void onPrintersAdded(android.content.pm.ParceledListSlice parceledListSlice) {
            android.print.PrinterDiscoverySession printerDiscoverySession = this.mWeakSession.get();
            if (printerDiscoverySession != null) {
                printerDiscoverySession.mHandler.obtainMessage(1, parceledListSlice.getList()).sendToTarget();
            }
        }

        @Override // android.print.IPrinterDiscoveryObserver
        public void onPrintersRemoved(android.content.pm.ParceledListSlice parceledListSlice) {
            android.print.PrinterDiscoverySession printerDiscoverySession = this.mWeakSession.get();
            if (printerDiscoverySession != null) {
                printerDiscoverySession.mHandler.obtainMessage(2, parceledListSlice.getList()).sendToTarget();
            }
        }
    }
}
