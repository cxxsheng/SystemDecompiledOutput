package android.print;

/* loaded from: classes3.dex */
public final class PrintManager {
    public static final java.lang.String ACTION_PRINT_DIALOG = "android.print.PRINT_DIALOG";
    public static final int ALL_SERVICES = 3;
    public static final int APP_ID_ANY = -2;
    private static final boolean DEBUG = false;
    public static final int DISABLED_SERVICES = 2;

    @android.annotation.SystemApi
    public static final int ENABLED_SERVICES = 1;
    public static final java.lang.String EXTRA_PRINT_DIALOG_INTENT = "android.print.intent.extra.EXTRA_PRINT_DIALOG_INTENT";
    public static final java.lang.String EXTRA_PRINT_DOCUMENT_ADAPTER = "android.print.intent.extra.EXTRA_PRINT_DOCUMENT_ADAPTER";
    public static final java.lang.String EXTRA_PRINT_JOB = "android.print.intent.extra.EXTRA_PRINT_JOB";
    private static final java.lang.String LOG_TAG = "PrintManager";
    private static final int MSG_NOTIFY_PRINT_JOB_STATE_CHANGED = 1;
    public static final java.lang.String PRINT_SPOOLER_PACKAGE_NAME = "com.android.printspooler";
    private final int mAppId;
    private final android.content.Context mContext;
    private final android.os.Handler mHandler;
    private java.util.Map<android.print.PrintManager.PrintJobStateChangeListener, android.print.PrintManager.PrintJobStateChangeListenerWrapper> mPrintJobStateChangeListeners;
    private java.util.Map<android.print.PrintManager.PrintServiceRecommendationsChangeListener, android.print.PrintManager.PrintServiceRecommendationsChangeListenerWrapper> mPrintServiceRecommendationsChangeListeners;
    private java.util.Map<android.print.PrintManager.PrintServicesChangeListener, android.print.PrintManager.PrintServicesChangeListenerWrapper> mPrintServicesChangeListeners;
    private final android.print.IPrintManager mService;
    private final int mUserId;

    public interface PrintJobStateChangeListener {
        void onPrintJobStateChanged(android.print.PrintJobId printJobId);
    }

    @android.annotation.SystemApi
    public interface PrintServiceRecommendationsChangeListener {
        void onPrintServiceRecommendationsChanged();
    }

    @android.annotation.SystemApi
    public interface PrintServicesChangeListener {
        void onPrintServicesChanged();
    }

    public PrintManager(android.content.Context context, android.print.IPrintManager iPrintManager, int i, int i2) {
        this.mContext = context;
        this.mService = iPrintManager;
        this.mUserId = i;
        this.mAppId = i2;
        this.mHandler = new android.os.Handler(context.getMainLooper(), null, false) { // from class: android.print.PrintManager.1
            @Override // android.os.Handler
            public void handleMessage(android.os.Message message) {
                switch (message.what) {
                    case 1:
                        com.android.internal.os.SomeArgs someArgs = (com.android.internal.os.SomeArgs) message.obj;
                        android.print.PrintManager.PrintJobStateChangeListener listener = ((android.print.PrintManager.PrintJobStateChangeListenerWrapper) someArgs.arg1).getListener();
                        if (listener != null) {
                            listener.onPrintJobStateChanged((android.print.PrintJobId) someArgs.arg2);
                        }
                        someArgs.recycle();
                        break;
                }
            }
        };
    }

    public android.print.PrintManager getGlobalPrintManagerForUser(int i) {
        if (this.mService == null) {
            android.util.Log.w(LOG_TAG, "Feature android.software.print not available");
            return null;
        }
        return new android.print.PrintManager(this.mContext, this.mService, i, -2);
    }

    android.print.PrintJobInfo getPrintJobInfo(android.print.PrintJobId printJobId) {
        try {
            return this.mService.getPrintJobInfo(printJobId, this.mAppId, this.mUserId);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void addPrintJobStateChangeListener(android.print.PrintManager.PrintJobStateChangeListener printJobStateChangeListener) {
        if (this.mService == null) {
            android.util.Log.w(LOG_TAG, "Feature android.software.print not available");
            return;
        }
        if (this.mPrintJobStateChangeListeners == null) {
            this.mPrintJobStateChangeListeners = new android.util.ArrayMap();
        }
        android.print.PrintManager.PrintJobStateChangeListenerWrapper printJobStateChangeListenerWrapper = new android.print.PrintManager.PrintJobStateChangeListenerWrapper(printJobStateChangeListener, this.mHandler);
        try {
            this.mService.addPrintJobStateChangeListener(printJobStateChangeListenerWrapper, this.mAppId, this.mUserId);
            this.mPrintJobStateChangeListeners.put(printJobStateChangeListener, printJobStateChangeListenerWrapper);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void removePrintJobStateChangeListener(android.print.PrintManager.PrintJobStateChangeListener printJobStateChangeListener) {
        android.print.PrintManager.PrintJobStateChangeListenerWrapper remove;
        if (this.mService == null) {
            android.util.Log.w(LOG_TAG, "Feature android.software.print not available");
            return;
        }
        if (this.mPrintJobStateChangeListeners == null || (remove = this.mPrintJobStateChangeListeners.remove(printJobStateChangeListener)) == null) {
            return;
        }
        if (this.mPrintJobStateChangeListeners.isEmpty()) {
            this.mPrintJobStateChangeListeners = null;
        }
        remove.destroy();
        try {
            this.mService.removePrintJobStateChangeListener(remove, this.mUserId);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public android.print.PrintJob getPrintJob(android.print.PrintJobId printJobId) {
        if (this.mService == null) {
            android.util.Log.w(LOG_TAG, "Feature android.software.print not available");
            return null;
        }
        try {
            android.print.PrintJobInfo printJobInfo = this.mService.getPrintJobInfo(printJobId, this.mAppId, this.mUserId);
            if (printJobInfo != null) {
                return new android.print.PrintJob(printJobInfo, this);
            }
            return null;
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public android.graphics.drawable.Icon getCustomPrinterIcon(android.print.PrinterId printerId) {
        if (this.mService == null) {
            android.util.Log.w(LOG_TAG, "Feature android.software.print not available");
            return null;
        }
        try {
            return this.mService.getCustomPrinterIcon(printerId, this.mUserId);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public java.util.List<android.print.PrintJob> getPrintJobs() {
        if (this.mService == null) {
            android.util.Log.w(LOG_TAG, "Feature android.software.print not available");
            return java.util.Collections.emptyList();
        }
        try {
            java.util.List<android.print.PrintJobInfo> printJobInfos = this.mService.getPrintJobInfos(this.mAppId, this.mUserId);
            if (printJobInfos == null) {
                return java.util.Collections.emptyList();
            }
            int size = printJobInfos.size();
            java.util.ArrayList arrayList = new java.util.ArrayList(size);
            for (int i = 0; i < size; i++) {
                arrayList.add(new android.print.PrintJob(printJobInfos.get(i), this));
            }
            return arrayList;
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    void cancelPrintJob(android.print.PrintJobId printJobId) {
        if (this.mService == null) {
            android.util.Log.w(LOG_TAG, "Feature android.software.print not available");
            return;
        }
        try {
            this.mService.cancelPrintJob(printJobId, this.mAppId, this.mUserId);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    void restartPrintJob(android.print.PrintJobId printJobId) {
        if (this.mService == null) {
            android.util.Log.w(LOG_TAG, "Feature android.software.print not available");
            return;
        }
        try {
            this.mService.restartPrintJob(printJobId, this.mAppId, this.mUserId);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public android.print.PrintJob print(java.lang.String str, android.print.PrintDocumentAdapter printDocumentAdapter, android.print.PrintAttributes printAttributes) {
        if (this.mService == null) {
            android.util.Log.w(LOG_TAG, "Feature android.software.print not available");
            return null;
        }
        if (!(this.mContext instanceof android.app.Activity)) {
            throw new java.lang.IllegalStateException("Can print only from an activity");
        }
        if (android.text.TextUtils.isEmpty(str)) {
            throw new java.lang.IllegalArgumentException("printJobName cannot be empty");
        }
        if (printDocumentAdapter == null) {
            throw new java.lang.IllegalArgumentException("documentAdapter cannot be null");
        }
        try {
            android.os.Bundle print = this.mService.print(str, new android.print.PrintManager.PrintDocumentAdapterDelegate((android.app.Activity) this.mContext, printDocumentAdapter), printAttributes, this.mContext.getPackageName(), this.mAppId, this.mUserId);
            if (print != null) {
                android.print.PrintJobInfo printJobInfo = (android.print.PrintJobInfo) print.getParcelable(EXTRA_PRINT_JOB, android.print.PrintJobInfo.class);
                android.content.IntentSender intentSender = (android.content.IntentSender) print.getParcelable(EXTRA_PRINT_DIALOG_INTENT, android.content.IntentSender.class);
                if (printJobInfo == null || intentSender == null) {
                    return null;
                }
                try {
                    this.mContext.startIntentSender(intentSender, null, 0, 0, 0, android.app.ActivityOptions.makeBasic().setPendingIntentBackgroundActivityStartMode(1).toBundle());
                    return new android.print.PrintJob(printJobInfo, this);
                } catch (android.content.IntentSender.SendIntentException e) {
                    android.util.Log.e(LOG_TAG, "Couldn't start print job config activity.", e);
                }
            }
            return null;
        } catch (android.os.RemoteException e2) {
            throw e2.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public void addPrintServicesChangeListener(android.print.PrintManager.PrintServicesChangeListener printServicesChangeListener, android.os.Handler handler) {
        com.android.internal.util.Preconditions.checkNotNull(printServicesChangeListener);
        if (handler == null) {
            handler = this.mHandler;
        }
        if (this.mService == null) {
            android.util.Log.w(LOG_TAG, "Feature android.software.print not available");
            return;
        }
        if (this.mPrintServicesChangeListeners == null) {
            this.mPrintServicesChangeListeners = new android.util.ArrayMap();
        }
        android.print.PrintManager.PrintServicesChangeListenerWrapper printServicesChangeListenerWrapper = new android.print.PrintManager.PrintServicesChangeListenerWrapper(printServicesChangeListener, handler);
        try {
            this.mService.addPrintServicesChangeListener(printServicesChangeListenerWrapper, this.mUserId);
            this.mPrintServicesChangeListeners.put(printServicesChangeListener, printServicesChangeListenerWrapper);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public void removePrintServicesChangeListener(android.print.PrintManager.PrintServicesChangeListener printServicesChangeListener) {
        android.print.PrintManager.PrintServicesChangeListenerWrapper remove;
        com.android.internal.util.Preconditions.checkNotNull(printServicesChangeListener);
        if (this.mService == null) {
            android.util.Log.w(LOG_TAG, "Feature android.software.print not available");
            return;
        }
        if (this.mPrintServicesChangeListeners == null || (remove = this.mPrintServicesChangeListeners.remove(printServicesChangeListener)) == null) {
            return;
        }
        if (this.mPrintServicesChangeListeners.isEmpty()) {
            this.mPrintServicesChangeListeners = null;
        }
        remove.destroy();
        try {
            this.mService.removePrintServicesChangeListener(remove, this.mUserId);
        } catch (android.os.RemoteException e) {
            android.util.Log.e(LOG_TAG, "Error removing print services change listener", e);
        }
    }

    @android.annotation.SystemApi
    public java.util.List<android.printservice.PrintServiceInfo> getPrintServices(int i) {
        com.android.internal.util.Preconditions.checkFlagsArgument(i, 3);
        try {
            java.util.List<android.printservice.PrintServiceInfo> printServices = this.mService.getPrintServices(i, this.mUserId);
            if (printServices != null) {
                return printServices;
            }
            return java.util.Collections.emptyList();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public void addPrintServiceRecommendationsChangeListener(android.print.PrintManager.PrintServiceRecommendationsChangeListener printServiceRecommendationsChangeListener, android.os.Handler handler) {
        com.android.internal.util.Preconditions.checkNotNull(printServiceRecommendationsChangeListener);
        if (handler == null) {
            handler = this.mHandler;
        }
        if (this.mService == null) {
            android.util.Log.w(LOG_TAG, "Feature android.software.print not available");
            return;
        }
        if (this.mPrintServiceRecommendationsChangeListeners == null) {
            this.mPrintServiceRecommendationsChangeListeners = new android.util.ArrayMap();
        }
        android.print.PrintManager.PrintServiceRecommendationsChangeListenerWrapper printServiceRecommendationsChangeListenerWrapper = new android.print.PrintManager.PrintServiceRecommendationsChangeListenerWrapper(printServiceRecommendationsChangeListener, handler);
        try {
            this.mService.addPrintServiceRecommendationsChangeListener(printServiceRecommendationsChangeListenerWrapper, this.mUserId);
            this.mPrintServiceRecommendationsChangeListeners.put(printServiceRecommendationsChangeListener, printServiceRecommendationsChangeListenerWrapper);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public void removePrintServiceRecommendationsChangeListener(android.print.PrintManager.PrintServiceRecommendationsChangeListener printServiceRecommendationsChangeListener) {
        android.print.PrintManager.PrintServiceRecommendationsChangeListenerWrapper remove;
        com.android.internal.util.Preconditions.checkNotNull(printServiceRecommendationsChangeListener);
        if (this.mService == null) {
            android.util.Log.w(LOG_TAG, "Feature android.software.print not available");
            return;
        }
        if (this.mPrintServiceRecommendationsChangeListeners == null || (remove = this.mPrintServiceRecommendationsChangeListeners.remove(printServiceRecommendationsChangeListener)) == null) {
            return;
        }
        if (this.mPrintServiceRecommendationsChangeListeners.isEmpty()) {
            this.mPrintServiceRecommendationsChangeListeners = null;
        }
        remove.destroy();
        try {
            this.mService.removePrintServiceRecommendationsChangeListener(remove, this.mUserId);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public java.util.List<android.printservice.recommendation.RecommendationInfo> getPrintServiceRecommendations() {
        try {
            java.util.List<android.printservice.recommendation.RecommendationInfo> printServiceRecommendations = this.mService.getPrintServiceRecommendations(this.mUserId);
            if (printServiceRecommendations != null) {
                return printServiceRecommendations;
            }
            return java.util.Collections.emptyList();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public android.print.PrinterDiscoverySession createPrinterDiscoverySession() {
        if (this.mService == null) {
            android.util.Log.w(LOG_TAG, "Feature android.software.print not available");
            return null;
        }
        return new android.print.PrinterDiscoverySession(this.mService, this.mContext, this.mUserId);
    }

    public void setPrintServiceEnabled(android.content.ComponentName componentName, boolean z) {
        if (this.mService == null) {
            android.util.Log.w(LOG_TAG, "Feature android.software.print not available");
            return;
        }
        try {
            this.mService.setPrintServiceEnabled(componentName, z, this.mUserId);
        } catch (android.os.RemoteException e) {
            android.util.Log.e(LOG_TAG, "Error enabling or disabling " + componentName, e);
        }
    }

    public boolean isPrintServiceEnabled(android.content.ComponentName componentName) {
        if (this.mService == null) {
            android.util.Log.w(LOG_TAG, "Feature android.software.print not available");
            return false;
        }
        try {
            return this.mService.isPrintServiceEnabled(componentName, this.mUserId);
        } catch (android.os.RemoteException e) {
            android.util.Log.e(LOG_TAG, "Error sampling enabled/disabled " + componentName, e);
            return false;
        }
    }

    public static final class PrintDocumentAdapterDelegate extends android.print.IPrintDocumentAdapter.Stub implements android.app.Application.ActivityLifecycleCallbacks {
        private android.app.Activity mActivity;
        private android.print.PrintDocumentAdapter mDocumentAdapter;
        private android.os.Handler mHandler;
        private final java.lang.Object mLock = new java.lang.Object();
        private android.print.IPrintDocumentAdapterObserver mObserver;
        private android.print.PrintManager.PrintDocumentAdapterDelegate.DestroyableCallback mPendingCallback;

        private interface DestroyableCallback {
            void destroy();
        }

        public PrintDocumentAdapterDelegate(android.app.Activity activity, android.print.PrintDocumentAdapter printDocumentAdapter) {
            if (activity.isFinishing()) {
                throw new java.lang.IllegalStateException("Cannot start printing for finishing activity");
            }
            this.mActivity = activity;
            this.mDocumentAdapter = printDocumentAdapter;
            this.mHandler = new android.print.PrintManager.PrintDocumentAdapterDelegate.MyHandler(this.mActivity.getMainLooper());
            this.mActivity.getApplication().registerActivityLifecycleCallbacks(this);
        }

        @Override // android.print.IPrintDocumentAdapter
        public void setObserver(android.print.IPrintDocumentAdapterObserver iPrintDocumentAdapterObserver) {
            boolean isDestroyedLocked;
            synchronized (this.mLock) {
                this.mObserver = iPrintDocumentAdapterObserver;
                isDestroyedLocked = isDestroyedLocked();
            }
            if (isDestroyedLocked && iPrintDocumentAdapterObserver != null) {
                try {
                    iPrintDocumentAdapterObserver.onDestroy();
                } catch (android.os.RemoteException e) {
                    android.util.Log.e(android.print.PrintManager.LOG_TAG, "Error announcing destroyed state", e);
                }
            }
        }

        @Override // android.print.IPrintDocumentAdapter
        public void start() {
            synchronized (this.mLock) {
                if (!isDestroyedLocked()) {
                    this.mHandler.obtainMessage(1, this.mDocumentAdapter).sendToTarget();
                }
            }
        }

        @Override // android.print.IPrintDocumentAdapter
        public void layout(android.print.PrintAttributes printAttributes, android.print.PrintAttributes printAttributes2, android.print.ILayoutResultCallback iLayoutResultCallback, android.os.Bundle bundle, int i) {
            android.os.ICancellationSignal createTransport = android.os.CancellationSignal.createTransport();
            try {
                iLayoutResultCallback.onLayoutStarted(createTransport, i);
                synchronized (this.mLock) {
                    if (isDestroyedLocked()) {
                        return;
                    }
                    android.os.CancellationSignal fromTransport = android.os.CancellationSignal.fromTransport(createTransport);
                    com.android.internal.os.SomeArgs obtain = com.android.internal.os.SomeArgs.obtain();
                    obtain.arg1 = this.mDocumentAdapter;
                    obtain.arg2 = printAttributes;
                    obtain.arg3 = printAttributes2;
                    obtain.arg4 = fromTransport;
                    obtain.arg5 = new android.print.PrintManager.PrintDocumentAdapterDelegate.MyLayoutResultCallback(iLayoutResultCallback, i);
                    obtain.arg6 = bundle;
                    this.mHandler.obtainMessage(2, obtain).sendToTarget();
                }
            } catch (android.os.RemoteException e) {
                android.util.Log.e(android.print.PrintManager.LOG_TAG, "Error notifying for layout start", e);
            }
        }

        @Override // android.print.IPrintDocumentAdapter
        public void write(android.print.PageRange[] pageRangeArr, android.os.ParcelFileDescriptor parcelFileDescriptor, android.print.IWriteResultCallback iWriteResultCallback, int i) {
            android.os.ICancellationSignal createTransport = android.os.CancellationSignal.createTransport();
            try {
                iWriteResultCallback.onWriteStarted(createTransport, i);
                synchronized (this.mLock) {
                    if (isDestroyedLocked()) {
                        return;
                    }
                    android.os.CancellationSignal fromTransport = android.os.CancellationSignal.fromTransport(createTransport);
                    com.android.internal.os.SomeArgs obtain = com.android.internal.os.SomeArgs.obtain();
                    obtain.arg1 = this.mDocumentAdapter;
                    obtain.arg2 = pageRangeArr;
                    obtain.arg3 = parcelFileDescriptor;
                    obtain.arg4 = fromTransport;
                    obtain.arg5 = new android.print.PrintManager.PrintDocumentAdapterDelegate.MyWriteResultCallback(iWriteResultCallback, parcelFileDescriptor, i);
                    this.mHandler.obtainMessage(3, obtain).sendToTarget();
                }
            } catch (android.os.RemoteException e) {
                android.util.Log.e(android.print.PrintManager.LOG_TAG, "Error notifying for write start", e);
            }
        }

        @Override // android.print.IPrintDocumentAdapter
        public void finish() {
            synchronized (this.mLock) {
                if (!isDestroyedLocked()) {
                    this.mHandler.obtainMessage(4, this.mDocumentAdapter).sendToTarget();
                }
            }
        }

        @Override // android.print.IPrintDocumentAdapter
        public void kill(java.lang.String str) {
            synchronized (this.mLock) {
                if (!isDestroyedLocked()) {
                    this.mHandler.obtainMessage(5, str).sendToTarget();
                }
            }
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityPaused(android.app.Activity activity) {
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityCreated(android.app.Activity activity, android.os.Bundle bundle) {
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityStarted(android.app.Activity activity) {
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityResumed(android.app.Activity activity) {
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityStopped(android.app.Activity activity) {
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivitySaveInstanceState(android.app.Activity activity, android.os.Bundle bundle) {
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityDestroyed(android.app.Activity activity) {
            android.print.IPrintDocumentAdapterObserver iPrintDocumentAdapterObserver;
            synchronized (this.mLock) {
                if (activity != this.mActivity) {
                    iPrintDocumentAdapterObserver = null;
                } else {
                    iPrintDocumentAdapterObserver = this.mObserver;
                    destroyLocked();
                }
            }
            if (iPrintDocumentAdapterObserver != null) {
                try {
                    iPrintDocumentAdapterObserver.onDestroy();
                } catch (android.os.RemoteException e) {
                    android.util.Log.e(android.print.PrintManager.LOG_TAG, "Error announcing destroyed state", e);
                }
            }
        }

        private boolean isDestroyedLocked() {
            return this.mActivity == null;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void destroyLocked() {
            this.mActivity.getApplication().unregisterActivityLifecycleCallbacks(this);
            this.mActivity = null;
            this.mDocumentAdapter = null;
            this.mHandler.removeMessages(1);
            this.mHandler.removeMessages(2);
            this.mHandler.removeMessages(3);
            this.mHandler.removeMessages(4);
            this.mHandler = null;
            this.mObserver = null;
            if (this.mPendingCallback != null) {
                this.mPendingCallback.destroy();
                this.mPendingCallback = null;
            }
        }

        private final class MyHandler extends android.os.Handler {
            public static final int MSG_ON_FINISH = 4;
            public static final int MSG_ON_KILL = 5;
            public static final int MSG_ON_LAYOUT = 2;
            public static final int MSG_ON_START = 1;
            public static final int MSG_ON_WRITE = 3;

            public MyHandler(android.os.Looper looper) {
                super(looper, null, true);
            }

            @Override // android.os.Handler
            public void handleMessage(android.os.Message message) {
                switch (message.what) {
                    case 1:
                        ((android.print.PrintDocumentAdapter) message.obj).onStart();
                        return;
                    case 2:
                        com.android.internal.os.SomeArgs someArgs = (com.android.internal.os.SomeArgs) message.obj;
                        android.print.PrintDocumentAdapter printDocumentAdapter = (android.print.PrintDocumentAdapter) someArgs.arg1;
                        android.print.PrintAttributes printAttributes = (android.print.PrintAttributes) someArgs.arg2;
                        android.print.PrintAttributes printAttributes2 = (android.print.PrintAttributes) someArgs.arg3;
                        android.os.CancellationSignal cancellationSignal = (android.os.CancellationSignal) someArgs.arg4;
                        android.print.PrintDocumentAdapter.LayoutResultCallback layoutResultCallback = (android.print.PrintDocumentAdapter.LayoutResultCallback) someArgs.arg5;
                        android.os.Bundle bundle = (android.os.Bundle) someArgs.arg6;
                        someArgs.recycle();
                        printDocumentAdapter.onLayout(printAttributes, printAttributes2, cancellationSignal, layoutResultCallback, bundle);
                        return;
                    case 3:
                        com.android.internal.os.SomeArgs someArgs2 = (com.android.internal.os.SomeArgs) message.obj;
                        android.print.PrintDocumentAdapter printDocumentAdapter2 = (android.print.PrintDocumentAdapter) someArgs2.arg1;
                        android.print.PageRange[] pageRangeArr = (android.print.PageRange[]) someArgs2.arg2;
                        android.os.ParcelFileDescriptor parcelFileDescriptor = (android.os.ParcelFileDescriptor) someArgs2.arg3;
                        android.os.CancellationSignal cancellationSignal2 = (android.os.CancellationSignal) someArgs2.arg4;
                        android.print.PrintDocumentAdapter.WriteResultCallback writeResultCallback = (android.print.PrintDocumentAdapter.WriteResultCallback) someArgs2.arg5;
                        someArgs2.recycle();
                        printDocumentAdapter2.onWrite(pageRangeArr, parcelFileDescriptor, cancellationSignal2, writeResultCallback);
                        return;
                    case 4:
                        ((android.print.PrintDocumentAdapter) message.obj).onFinish();
                        synchronized (android.print.PrintManager.PrintDocumentAdapterDelegate.this.mLock) {
                            android.print.PrintManager.PrintDocumentAdapterDelegate.this.destroyLocked();
                        }
                        return;
                    case 5:
                        throw new java.lang.RuntimeException((java.lang.String) message.obj);
                    default:
                        throw new java.lang.IllegalArgumentException("Unknown message: " + message.what);
                }
            }
        }

        private final class MyLayoutResultCallback extends android.print.PrintDocumentAdapter.LayoutResultCallback implements android.print.PrintManager.PrintDocumentAdapterDelegate.DestroyableCallback {
            private android.print.ILayoutResultCallback mCallback;
            private final int mSequence;

            public MyLayoutResultCallback(android.print.ILayoutResultCallback iLayoutResultCallback, int i) {
                this.mCallback = iLayoutResultCallback;
                this.mSequence = i;
            }

            @Override // android.print.PrintDocumentAdapter.LayoutResultCallback
            public void onLayoutFinished(android.print.PrintDocumentInfo printDocumentInfo, boolean z) {
                android.print.ILayoutResultCallback iLayoutResultCallback;
                synchronized (android.print.PrintManager.PrintDocumentAdapterDelegate.this.mLock) {
                    iLayoutResultCallback = this.mCallback;
                }
                if (iLayoutResultCallback == null) {
                    android.util.Log.e(android.print.PrintManager.LOG_TAG, "PrintDocumentAdapter is destroyed. Did you finish the printing activity before print completion or did you invoke a callback after finish?");
                    return;
                }
                try {
                    if (printDocumentInfo == null) {
                        throw new java.lang.NullPointerException("document info cannot be null");
                    }
                    try {
                        iLayoutResultCallback.onLayoutFinished(printDocumentInfo, z, this.mSequence);
                    } catch (android.os.RemoteException e) {
                        android.util.Log.e(android.print.PrintManager.LOG_TAG, "Error calling onLayoutFinished", e);
                    }
                } finally {
                    destroy();
                }
            }

            @Override // android.print.PrintDocumentAdapter.LayoutResultCallback
            public void onLayoutFailed(java.lang.CharSequence charSequence) {
                android.print.ILayoutResultCallback iLayoutResultCallback;
                synchronized (android.print.PrintManager.PrintDocumentAdapterDelegate.this.mLock) {
                    iLayoutResultCallback = this.mCallback;
                }
                if (iLayoutResultCallback == null) {
                    android.util.Log.e(android.print.PrintManager.LOG_TAG, "PrintDocumentAdapter is destroyed. Did you finish the printing activity before print completion or did you invoke a callback after finish?");
                    return;
                }
                try {
                    try {
                        iLayoutResultCallback.onLayoutFailed(charSequence, this.mSequence);
                    } catch (android.os.RemoteException e) {
                        android.util.Log.e(android.print.PrintManager.LOG_TAG, "Error calling onLayoutFailed", e);
                    }
                } finally {
                    destroy();
                }
            }

            @Override // android.print.PrintDocumentAdapter.LayoutResultCallback
            public void onLayoutCancelled() {
                android.print.ILayoutResultCallback iLayoutResultCallback;
                synchronized (android.print.PrintManager.PrintDocumentAdapterDelegate.this.mLock) {
                    iLayoutResultCallback = this.mCallback;
                }
                if (iLayoutResultCallback == null) {
                    android.util.Log.e(android.print.PrintManager.LOG_TAG, "PrintDocumentAdapter is destroyed. Did you finish the printing activity before print completion or did you invoke a callback after finish?");
                    return;
                }
                try {
                    try {
                        iLayoutResultCallback.onLayoutCanceled(this.mSequence);
                    } catch (android.os.RemoteException e) {
                        android.util.Log.e(android.print.PrintManager.LOG_TAG, "Error calling onLayoutFailed", e);
                    }
                } finally {
                    destroy();
                }
            }

            @Override // android.print.PrintManager.PrintDocumentAdapterDelegate.DestroyableCallback
            public void destroy() {
                synchronized (android.print.PrintManager.PrintDocumentAdapterDelegate.this.mLock) {
                    this.mCallback = null;
                    android.print.PrintManager.PrintDocumentAdapterDelegate.this.mPendingCallback = null;
                }
            }
        }

        private final class MyWriteResultCallback extends android.print.PrintDocumentAdapter.WriteResultCallback implements android.print.PrintManager.PrintDocumentAdapterDelegate.DestroyableCallback {
            private android.print.IWriteResultCallback mCallback;
            private android.os.ParcelFileDescriptor mFd;
            private final int mSequence;

            public MyWriteResultCallback(android.print.IWriteResultCallback iWriteResultCallback, android.os.ParcelFileDescriptor parcelFileDescriptor, int i) {
                this.mFd = parcelFileDescriptor;
                this.mSequence = i;
                this.mCallback = iWriteResultCallback;
            }

            @Override // android.print.PrintDocumentAdapter.WriteResultCallback
            public void onWriteFinished(android.print.PageRange[] pageRangeArr) {
                android.print.IWriteResultCallback iWriteResultCallback;
                synchronized (android.print.PrintManager.PrintDocumentAdapterDelegate.this.mLock) {
                    iWriteResultCallback = this.mCallback;
                }
                if (iWriteResultCallback == null) {
                    android.util.Log.e(android.print.PrintManager.LOG_TAG, "PrintDocumentAdapter is destroyed. Did you finish the printing activity before print completion or did you invoke a callback after finish?");
                    return;
                }
                try {
                    if (pageRangeArr == null) {
                        throw new java.lang.IllegalArgumentException("pages cannot be null");
                    }
                    if (pageRangeArr.length == 0) {
                        throw new java.lang.IllegalArgumentException("pages cannot be empty");
                    }
                    try {
                        iWriteResultCallback.onWriteFinished(pageRangeArr, this.mSequence);
                    } catch (android.os.RemoteException e) {
                        android.util.Log.e(android.print.PrintManager.LOG_TAG, "Error calling onWriteFinished", e);
                    }
                    destroy();
                } catch (java.lang.Throwable th) {
                    destroy();
                    throw th;
                }
            }

            @Override // android.print.PrintDocumentAdapter.WriteResultCallback
            public void onWriteFailed(java.lang.CharSequence charSequence) {
                android.print.IWriteResultCallback iWriteResultCallback;
                synchronized (android.print.PrintManager.PrintDocumentAdapterDelegate.this.mLock) {
                    iWriteResultCallback = this.mCallback;
                }
                if (iWriteResultCallback == null) {
                    android.util.Log.e(android.print.PrintManager.LOG_TAG, "PrintDocumentAdapter is destroyed. Did you finish the printing activity before print completion or did you invoke a callback after finish?");
                    return;
                }
                try {
                    try {
                        iWriteResultCallback.onWriteFailed(charSequence, this.mSequence);
                    } catch (android.os.RemoteException e) {
                        android.util.Log.e(android.print.PrintManager.LOG_TAG, "Error calling onWriteFailed", e);
                    }
                } finally {
                    destroy();
                }
            }

            @Override // android.print.PrintDocumentAdapter.WriteResultCallback
            public void onWriteCancelled() {
                android.print.IWriteResultCallback iWriteResultCallback;
                synchronized (android.print.PrintManager.PrintDocumentAdapterDelegate.this.mLock) {
                    iWriteResultCallback = this.mCallback;
                }
                if (iWriteResultCallback == null) {
                    android.util.Log.e(android.print.PrintManager.LOG_TAG, "PrintDocumentAdapter is destroyed. Did you finish the printing activity before print completion or did you invoke a callback after finish?");
                    return;
                }
                try {
                    try {
                        iWriteResultCallback.onWriteCanceled(this.mSequence);
                    } catch (android.os.RemoteException e) {
                        android.util.Log.e(android.print.PrintManager.LOG_TAG, "Error calling onWriteCanceled", e);
                    }
                } finally {
                    destroy();
                }
            }

            @Override // android.print.PrintManager.PrintDocumentAdapterDelegate.DestroyableCallback
            public void destroy() {
                synchronized (android.print.PrintManager.PrintDocumentAdapterDelegate.this.mLock) {
                    libcore.io.IoUtils.closeQuietly(this.mFd);
                    this.mCallback = null;
                    this.mFd = null;
                    android.print.PrintManager.PrintDocumentAdapterDelegate.this.mPendingCallback = null;
                }
            }
        }
    }

    public static final class PrintJobStateChangeListenerWrapper extends android.print.IPrintJobStateChangeListener.Stub {
        private final java.lang.ref.WeakReference<android.os.Handler> mWeakHandler;
        private final java.lang.ref.WeakReference<android.print.PrintManager.PrintJobStateChangeListener> mWeakListener;

        public PrintJobStateChangeListenerWrapper(android.print.PrintManager.PrintJobStateChangeListener printJobStateChangeListener, android.os.Handler handler) {
            this.mWeakListener = new java.lang.ref.WeakReference<>(printJobStateChangeListener);
            this.mWeakHandler = new java.lang.ref.WeakReference<>(handler);
        }

        @Override // android.print.IPrintJobStateChangeListener
        public void onPrintJobStateChanged(android.print.PrintJobId printJobId) {
            android.os.Handler handler = this.mWeakHandler.get();
            android.print.PrintManager.PrintJobStateChangeListener printJobStateChangeListener = this.mWeakListener.get();
            if (handler != null && printJobStateChangeListener != null) {
                com.android.internal.os.SomeArgs obtain = com.android.internal.os.SomeArgs.obtain();
                obtain.arg1 = this;
                obtain.arg2 = printJobId;
                handler.obtainMessage(1, obtain).sendToTarget();
            }
        }

        public void destroy() {
            this.mWeakListener.clear();
        }

        public android.print.PrintManager.PrintJobStateChangeListener getListener() {
            return this.mWeakListener.get();
        }
    }

    public static final class PrintServicesChangeListenerWrapper extends android.print.IPrintServicesChangeListener.Stub {
        private final java.lang.ref.WeakReference<android.os.Handler> mWeakHandler;
        private final java.lang.ref.WeakReference<android.print.PrintManager.PrintServicesChangeListener> mWeakListener;

        public PrintServicesChangeListenerWrapper(android.print.PrintManager.PrintServicesChangeListener printServicesChangeListener, android.os.Handler handler) {
            this.mWeakListener = new java.lang.ref.WeakReference<>(printServicesChangeListener);
            this.mWeakHandler = new java.lang.ref.WeakReference<>(handler);
        }

        @Override // android.print.IPrintServicesChangeListener
        public void onPrintServicesChanged() {
            android.os.Handler handler = this.mWeakHandler.get();
            final android.print.PrintManager.PrintServicesChangeListener printServicesChangeListener = this.mWeakListener.get();
            if (handler != null && printServicesChangeListener != null) {
                java.util.Objects.requireNonNull(printServicesChangeListener);
                handler.post(new java.lang.Runnable() { // from class: android.print.PrintManager$PrintServicesChangeListenerWrapper$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.print.PrintManager.PrintServicesChangeListener.this.onPrintServicesChanged();
                    }
                });
            }
        }

        public void destroy() {
            this.mWeakListener.clear();
        }
    }

    public static final class PrintServiceRecommendationsChangeListenerWrapper extends android.printservice.recommendation.IRecommendationsChangeListener.Stub {
        private final java.lang.ref.WeakReference<android.os.Handler> mWeakHandler;
        private final java.lang.ref.WeakReference<android.print.PrintManager.PrintServiceRecommendationsChangeListener> mWeakListener;

        public PrintServiceRecommendationsChangeListenerWrapper(android.print.PrintManager.PrintServiceRecommendationsChangeListener printServiceRecommendationsChangeListener, android.os.Handler handler) {
            this.mWeakListener = new java.lang.ref.WeakReference<>(printServiceRecommendationsChangeListener);
            this.mWeakHandler = new java.lang.ref.WeakReference<>(handler);
        }

        @Override // android.printservice.recommendation.IRecommendationsChangeListener
        public void onRecommendationsChanged() {
            android.os.Handler handler = this.mWeakHandler.get();
            final android.print.PrintManager.PrintServiceRecommendationsChangeListener printServiceRecommendationsChangeListener = this.mWeakListener.get();
            if (handler != null && printServiceRecommendationsChangeListener != null) {
                java.util.Objects.requireNonNull(printServiceRecommendationsChangeListener);
                handler.post(new java.lang.Runnable() { // from class: android.print.PrintManager$PrintServiceRecommendationsChangeListenerWrapper$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.print.PrintManager.PrintServiceRecommendationsChangeListener.this.onPrintServiceRecommendationsChanged();
                    }
                });
            }
        }

        public void destroy() {
            this.mWeakListener.clear();
        }
    }
}
