package android.printservice;

/* loaded from: classes3.dex */
public abstract class PrinterDiscoverySession {
    private static final java.lang.String LOG_TAG = "PrinterDiscoverySession";
    private static int sIdCounter = 0;
    private final int mId;
    private boolean mIsDestroyed;
    private boolean mIsDiscoveryStarted;
    private android.util.ArrayMap<android.print.PrinterId, android.print.PrinterInfo> mLastSentPrinters;
    private android.printservice.IPrintServiceClient mObserver;
    private final android.util.ArrayMap<android.print.PrinterId, android.print.PrinterInfo> mPrinters = new android.util.ArrayMap<>();
    private final java.util.List<android.print.PrinterId> mTrackedPrinters = new java.util.ArrayList();

    public abstract void onDestroy();

    public abstract void onStartPrinterDiscovery(java.util.List<android.print.PrinterId> list);

    public abstract void onStartPrinterStateTracking(android.print.PrinterId printerId);

    public abstract void onStopPrinterDiscovery();

    public abstract void onStopPrinterStateTracking(android.print.PrinterId printerId);

    public abstract void onValidatePrinters(java.util.List<android.print.PrinterId> list);

    public PrinterDiscoverySession() {
        int i = sIdCounter;
        sIdCounter = i + 1;
        this.mId = i;
    }

    void setObserver(android.printservice.IPrintServiceClient iPrintServiceClient) {
        this.mObserver = iPrintServiceClient;
        if (!this.mPrinters.isEmpty()) {
            try {
                this.mObserver.onPrintersAdded(new android.content.pm.ParceledListSlice(getPrinters()));
            } catch (android.os.RemoteException e) {
                android.util.Log.e(LOG_TAG, "Error sending added printers", e);
            }
        }
    }

    int getId() {
        return this.mId;
    }

    public final java.util.List<android.print.PrinterInfo> getPrinters() {
        android.printservice.PrintService.throwIfNotCalledOnMainThread();
        if (this.mIsDestroyed) {
            return java.util.Collections.emptyList();
        }
        return new java.util.ArrayList(this.mPrinters.values());
    }

    public final void addPrinters(java.util.List<android.print.PrinterInfo> list) {
        android.printservice.PrintService.throwIfNotCalledOnMainThread();
        if (this.mIsDestroyed) {
            android.util.Log.w(LOG_TAG, "Not adding printers - session destroyed.");
            return;
        }
        int i = 0;
        if (this.mIsDiscoveryStarted) {
            int size = list.size();
            java.util.ArrayList arrayList = null;
            while (i < size) {
                android.print.PrinterInfo printerInfo = list.get(i);
                android.print.PrinterInfo put = this.mPrinters.put(printerInfo.getId(), printerInfo);
                if (put == null || !put.equals(printerInfo)) {
                    if (arrayList == null) {
                        arrayList = new java.util.ArrayList();
                    }
                    arrayList.add(printerInfo);
                }
                i++;
            }
            if (arrayList != null) {
                try {
                    this.mObserver.onPrintersAdded(new android.content.pm.ParceledListSlice(arrayList));
                    return;
                } catch (android.os.RemoteException e) {
                    android.util.Log.e(LOG_TAG, "Error sending added printers", e);
                    return;
                }
            }
            return;
        }
        if (this.mLastSentPrinters == null) {
            this.mLastSentPrinters = new android.util.ArrayMap<>(this.mPrinters);
        }
        int size2 = list.size();
        while (i < size2) {
            android.print.PrinterInfo printerInfo2 = list.get(i);
            if (this.mPrinters.get(printerInfo2.getId()) == null) {
                this.mPrinters.put(printerInfo2.getId(), printerInfo2);
            }
            i++;
        }
    }

    public final void removePrinters(java.util.List<android.print.PrinterId> list) {
        android.printservice.PrintService.throwIfNotCalledOnMainThread();
        if (this.mIsDestroyed) {
            android.util.Log.w(LOG_TAG, "Not removing printers - session destroyed.");
            return;
        }
        int i = 0;
        if (this.mIsDiscoveryStarted) {
            java.util.ArrayList arrayList = new java.util.ArrayList();
            int size = list.size();
            while (i < size) {
                android.print.PrinterId printerId = list.get(i);
                if (this.mPrinters.remove(printerId) != null) {
                    arrayList.add(printerId);
                }
                i++;
            }
            if (!arrayList.isEmpty()) {
                try {
                    this.mObserver.onPrintersRemoved(new android.content.pm.ParceledListSlice(arrayList));
                    return;
                } catch (android.os.RemoteException e) {
                    android.util.Log.e(LOG_TAG, "Error sending removed printers", e);
                    return;
                }
            }
            return;
        }
        if (this.mLastSentPrinters == null) {
            this.mLastSentPrinters = new android.util.ArrayMap<>(this.mPrinters);
        }
        int size2 = list.size();
        while (i < size2) {
            this.mPrinters.remove(list.get(i));
            i++;
        }
    }

    private void sendOutOfDiscoveryPeriodPrinterChanges() {
        if (this.mLastSentPrinters == null || this.mLastSentPrinters.isEmpty()) {
            this.mLastSentPrinters = null;
            return;
        }
        java.util.ArrayList arrayList = null;
        for (android.print.PrinterInfo printerInfo : this.mPrinters.values()) {
            android.print.PrinterInfo printerInfo2 = this.mLastSentPrinters.get(printerInfo.getId());
            if (printerInfo2 == null || !printerInfo2.equals(printerInfo)) {
                if (arrayList == null) {
                    arrayList = new java.util.ArrayList();
                }
                arrayList.add(printerInfo);
            }
        }
        if (arrayList != null) {
            try {
                this.mObserver.onPrintersAdded(new android.content.pm.ParceledListSlice(arrayList));
            } catch (android.os.RemoteException e) {
                android.util.Log.e(LOG_TAG, "Error sending added printers", e);
            }
        }
        java.util.ArrayList arrayList2 = null;
        for (android.print.PrinterInfo printerInfo3 : this.mLastSentPrinters.values()) {
            if (!this.mPrinters.containsKey(printerInfo3.getId())) {
                if (arrayList2 == null) {
                    arrayList2 = new java.util.ArrayList();
                }
                arrayList2.add(printerInfo3.getId());
            }
        }
        if (arrayList2 != null) {
            try {
                this.mObserver.onPrintersRemoved(new android.content.pm.ParceledListSlice(arrayList2));
            } catch (android.os.RemoteException e2) {
                android.util.Log.e(LOG_TAG, "Error sending removed printers", e2);
            }
        }
        this.mLastSentPrinters = null;
    }

    public void onRequestCustomPrinterIcon(android.print.PrinterId printerId, android.os.CancellationSignal cancellationSignal, android.printservice.CustomPrinterIconCallback customPrinterIconCallback) {
    }

    public final java.util.List<android.print.PrinterId> getTrackedPrinters() {
        android.printservice.PrintService.throwIfNotCalledOnMainThread();
        if (this.mIsDestroyed) {
            return java.util.Collections.emptyList();
        }
        return new java.util.ArrayList(this.mTrackedPrinters);
    }

    public final boolean isDestroyed() {
        android.printservice.PrintService.throwIfNotCalledOnMainThread();
        return this.mIsDestroyed;
    }

    public final boolean isPrinterDiscoveryStarted() {
        android.printservice.PrintService.throwIfNotCalledOnMainThread();
        return this.mIsDiscoveryStarted;
    }

    void startPrinterDiscovery(java.util.List<android.print.PrinterId> list) {
        if (!this.mIsDestroyed) {
            this.mIsDiscoveryStarted = true;
            sendOutOfDiscoveryPeriodPrinterChanges();
            if (list == null) {
                list = java.util.Collections.emptyList();
            }
            onStartPrinterDiscovery(list);
        }
    }

    void stopPrinterDiscovery() {
        if (!this.mIsDestroyed) {
            this.mIsDiscoveryStarted = false;
            onStopPrinterDiscovery();
        }
    }

    void validatePrinters(java.util.List<android.print.PrinterId> list) {
        if (!this.mIsDestroyed && this.mObserver != null) {
            onValidatePrinters(list);
        }
    }

    void startPrinterStateTracking(android.print.PrinterId printerId) {
        if (!this.mIsDestroyed && this.mObserver != null && !this.mTrackedPrinters.contains(printerId)) {
            this.mTrackedPrinters.add(printerId);
            onStartPrinterStateTracking(printerId);
        }
    }

    void requestCustomPrinterIcon(android.print.PrinterId printerId) {
        if (!this.mIsDestroyed && this.mObserver != null) {
            onRequestCustomPrinterIcon(printerId, new android.os.CancellationSignal(), new android.printservice.CustomPrinterIconCallback(printerId, this.mObserver));
        }
    }

    void stopPrinterStateTracking(android.print.PrinterId printerId) {
        if (!this.mIsDestroyed && this.mObserver != null && this.mTrackedPrinters.remove(printerId)) {
            onStopPrinterStateTracking(printerId);
        }
    }

    void destroy() {
        if (!this.mIsDestroyed) {
            this.mIsDestroyed = true;
            this.mIsDiscoveryStarted = false;
            this.mPrinters.clear();
            this.mLastSentPrinters = null;
            this.mObserver = null;
            onDestroy();
        }
    }
}
