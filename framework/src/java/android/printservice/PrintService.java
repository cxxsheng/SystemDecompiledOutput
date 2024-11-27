package android.printservice;

/* loaded from: classes3.dex */
public abstract class PrintService extends android.app.Service {
    private static final boolean DEBUG = false;
    public static final java.lang.String EXTRA_CAN_SELECT_PRINTER = "android.printservice.extra.CAN_SELECT_PRINTER";
    public static final java.lang.String EXTRA_PRINTER_INFO = "android.intent.extra.print.EXTRA_PRINTER_INFO";
    public static final java.lang.String EXTRA_PRINT_DOCUMENT_INFO = "android.printservice.extra.PRINT_DOCUMENT_INFO";
    public static final java.lang.String EXTRA_PRINT_JOB_INFO = "android.intent.extra.print.PRINT_JOB_INFO";
    public static final java.lang.String EXTRA_SELECT_PRINTER = "android.printservice.extra.SELECT_PRINTER";
    private static final java.lang.String LOG_TAG = "PrintService";
    public static final java.lang.String SERVICE_INTERFACE = "android.printservice.PrintService";
    public static final java.lang.String SERVICE_META_DATA = "android.printservice";
    private android.printservice.IPrintServiceClient mClient;
    private android.printservice.PrinterDiscoverySession mDiscoverySession;
    private android.os.Handler mHandler;
    private int mLastSessionId = -1;

    protected abstract android.printservice.PrinterDiscoverySession onCreatePrinterDiscoverySession();

    protected abstract void onPrintJobQueued(android.printservice.PrintJob printJob);

    protected abstract void onRequestCancelPrintJob(android.printservice.PrintJob printJob);

    @Override // android.app.Service, android.content.ContextWrapper
    protected final void attachBaseContext(android.content.Context context) {
        super.attachBaseContext(context);
        this.mHandler = new android.printservice.PrintService.ServiceHandler(context.getMainLooper());
    }

    protected void onConnected() {
    }

    protected void onDisconnected() {
    }

    public final java.util.List<android.printservice.PrintJob> getActivePrintJobs() {
        java.util.ArrayList arrayList;
        throwIfNotCalledOnMainThread();
        if (this.mClient == null) {
            return java.util.Collections.emptyList();
        }
        try {
            java.util.List<android.print.PrintJobInfo> printJobInfos = this.mClient.getPrintJobInfos();
            if (printJobInfos == null) {
                arrayList = null;
            } else {
                int size = printJobInfos.size();
                arrayList = new java.util.ArrayList(size);
                for (int i = 0; i < size; i++) {
                    arrayList.add(new android.printservice.PrintJob(this, printJobInfos.get(i), this.mClient));
                }
            }
            if (arrayList != null) {
                return arrayList;
            }
        } catch (android.os.RemoteException e) {
            android.util.Log.e(LOG_TAG, "Error calling getPrintJobs()", e);
        }
        return java.util.Collections.emptyList();
    }

    public final android.print.PrinterId generatePrinterId(java.lang.String str) {
        throwIfNotCalledOnMainThread();
        return new android.print.PrinterId(new android.content.ComponentName(getPackageName(), getClass().getName()), (java.lang.String) com.android.internal.util.Preconditions.checkNotNull(str, "localId cannot be null"));
    }

    static void throwIfNotCalledOnMainThread() {
        if (!android.os.Looper.getMainLooper().isCurrentThread()) {
            throw new java.lang.IllegalAccessError("must be called from the main thread");
        }
    }

    @Override // android.app.Service
    public final android.os.IBinder onBind(android.content.Intent intent) {
        return new android.printservice.IPrintService.Stub() { // from class: android.printservice.PrintService.1
            @Override // android.printservice.IPrintService
            public void createPrinterDiscoverySession() {
                android.printservice.PrintService.this.mHandler.sendEmptyMessage(1);
            }

            @Override // android.printservice.IPrintService
            public void destroyPrinterDiscoverySession() {
                android.printservice.PrintService.this.mHandler.sendEmptyMessage(2);
            }

            @Override // android.printservice.IPrintService
            public void startPrinterDiscovery(java.util.List<android.print.PrinterId> list) {
                android.printservice.PrintService.this.mHandler.obtainMessage(3, list).sendToTarget();
            }

            @Override // android.printservice.IPrintService
            public void stopPrinterDiscovery() {
                android.printservice.PrintService.this.mHandler.sendEmptyMessage(4);
            }

            @Override // android.printservice.IPrintService
            public void validatePrinters(java.util.List<android.print.PrinterId> list) {
                android.printservice.PrintService.this.mHandler.obtainMessage(5, list).sendToTarget();
            }

            @Override // android.printservice.IPrintService
            public void startPrinterStateTracking(android.print.PrinterId printerId) {
                android.printservice.PrintService.this.mHandler.obtainMessage(6, printerId).sendToTarget();
            }

            @Override // android.printservice.IPrintService
            public void requestCustomPrinterIcon(android.print.PrinterId printerId) {
                android.printservice.PrintService.this.mHandler.obtainMessage(7, printerId).sendToTarget();
            }

            @Override // android.printservice.IPrintService
            public void stopPrinterStateTracking(android.print.PrinterId printerId) {
                android.printservice.PrintService.this.mHandler.obtainMessage(8, printerId).sendToTarget();
            }

            @Override // android.printservice.IPrintService
            public void setClient(android.printservice.IPrintServiceClient iPrintServiceClient) {
                android.printservice.PrintService.this.mHandler.obtainMessage(11, iPrintServiceClient).sendToTarget();
            }

            @Override // android.printservice.IPrintService
            public void requestCancelPrintJob(android.print.PrintJobInfo printJobInfo) {
                android.printservice.PrintService.this.mHandler.obtainMessage(10, printJobInfo).sendToTarget();
            }

            @Override // android.printservice.IPrintService
            public void onPrintJobQueued(android.print.PrintJobInfo printJobInfo) {
                android.printservice.PrintService.this.mHandler.obtainMessage(9, printJobInfo).sendToTarget();
            }
        };
    }

    private final class ServiceHandler extends android.os.Handler {
        public static final int MSG_CREATE_PRINTER_DISCOVERY_SESSION = 1;
        public static final int MSG_DESTROY_PRINTER_DISCOVERY_SESSION = 2;
        public static final int MSG_ON_PRINTJOB_QUEUED = 9;
        public static final int MSG_ON_REQUEST_CANCEL_PRINTJOB = 10;
        public static final int MSG_REQUEST_CUSTOM_PRINTER_ICON = 7;
        public static final int MSG_SET_CLIENT = 11;
        public static final int MSG_START_PRINTER_DISCOVERY = 3;
        public static final int MSG_START_PRINTER_STATE_TRACKING = 6;
        public static final int MSG_STOP_PRINTER_DISCOVERY = 4;
        public static final int MSG_STOP_PRINTER_STATE_TRACKING = 8;
        public static final int MSG_VALIDATE_PRINTERS = 5;

        public ServiceHandler(android.os.Looper looper) {
            super(looper, null, true);
        }

        @Override // android.os.Handler
        public void handleMessage(android.os.Message message) {
            int i = message.what;
            switch (i) {
                case 1:
                    android.printservice.PrinterDiscoverySession onCreatePrinterDiscoverySession = android.printservice.PrintService.this.onCreatePrinterDiscoverySession();
                    if (onCreatePrinterDiscoverySession == null) {
                        throw new java.lang.NullPointerException("session cannot be null");
                    }
                    if (onCreatePrinterDiscoverySession.getId() == android.printservice.PrintService.this.mLastSessionId) {
                        throw new java.lang.IllegalStateException("cannot reuse session instances");
                    }
                    android.printservice.PrintService.this.mDiscoverySession = onCreatePrinterDiscoverySession;
                    android.printservice.PrintService.this.mLastSessionId = onCreatePrinterDiscoverySession.getId();
                    onCreatePrinterDiscoverySession.setObserver(android.printservice.PrintService.this.mClient);
                    return;
                case 2:
                    if (android.printservice.PrintService.this.mDiscoverySession != null) {
                        android.printservice.PrintService.this.mDiscoverySession.destroy();
                        android.printservice.PrintService.this.mDiscoverySession = null;
                        return;
                    }
                    return;
                case 3:
                    if (android.printservice.PrintService.this.mDiscoverySession != null) {
                        android.printservice.PrintService.this.mDiscoverySession.startPrinterDiscovery((java.util.ArrayList) message.obj);
                        return;
                    }
                    return;
                case 4:
                    if (android.printservice.PrintService.this.mDiscoverySession != null) {
                        android.printservice.PrintService.this.mDiscoverySession.stopPrinterDiscovery();
                        return;
                    }
                    return;
                case 5:
                    if (android.printservice.PrintService.this.mDiscoverySession != null) {
                        android.printservice.PrintService.this.mDiscoverySession.validatePrinters((java.util.List) message.obj);
                        return;
                    }
                    return;
                case 6:
                    if (android.printservice.PrintService.this.mDiscoverySession != null) {
                        android.printservice.PrintService.this.mDiscoverySession.startPrinterStateTracking((android.print.PrinterId) message.obj);
                        return;
                    }
                    return;
                case 7:
                    if (android.printservice.PrintService.this.mDiscoverySession != null) {
                        android.printservice.PrintService.this.mDiscoverySession.requestCustomPrinterIcon((android.print.PrinterId) message.obj);
                        return;
                    }
                    return;
                case 8:
                    if (android.printservice.PrintService.this.mDiscoverySession != null) {
                        android.printservice.PrintService.this.mDiscoverySession.stopPrinterStateTracking((android.print.PrinterId) message.obj);
                        return;
                    }
                    return;
                case 9:
                    android.printservice.PrintService.this.onPrintJobQueued(new android.printservice.PrintJob(android.printservice.PrintService.this, (android.print.PrintJobInfo) message.obj, android.printservice.PrintService.this.mClient));
                    return;
                case 10:
                    android.printservice.PrintService.this.onRequestCancelPrintJob(new android.printservice.PrintJob(android.printservice.PrintService.this, (android.print.PrintJobInfo) message.obj, android.printservice.PrintService.this.mClient));
                    return;
                case 11:
                    android.printservice.PrintService.this.mClient = (android.printservice.IPrintServiceClient) message.obj;
                    if (android.printservice.PrintService.this.mClient != null) {
                        android.printservice.PrintService.this.onConnected();
                        return;
                    } else {
                        android.printservice.PrintService.this.onDisconnected();
                        return;
                    }
                default:
                    throw new java.lang.IllegalArgumentException("Unknown message: " + i);
            }
        }
    }
}
