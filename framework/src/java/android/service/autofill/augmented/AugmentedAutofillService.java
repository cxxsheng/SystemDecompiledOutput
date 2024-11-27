package android.service.autofill.augmented;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public abstract class AugmentedAutofillService extends android.app.Service {
    public static final java.lang.String SERVICE_INTERFACE = "android.service.autofill.augmented.AugmentedAutofillService";
    private static final java.lang.String TAG = android.service.autofill.augmented.AugmentedAutofillService.class.getSimpleName();
    static boolean sDebug = !android.os.Build.IS_USER;
    static boolean sVerbose = false;
    private android.util.SparseArray<android.service.autofill.augmented.AugmentedAutofillService.AutofillProxy> mAutofillProxies;
    private android.service.autofill.augmented.AugmentedAutofillService.AutofillProxy mAutofillProxyForLastRequest;
    private android.os.Handler mHandler;
    private android.content.ComponentName mServiceComponentName;

    /* JADX INFO: Access modifiers changed from: private */
    final class AugmentedAutofillServiceImpl extends android.service.autofill.augmented.IAugmentedAutofillService.Stub {
        private AugmentedAutofillServiceImpl() {
        }

        @Override // android.service.autofill.augmented.IAugmentedAutofillService
        public void onConnected(boolean z, boolean z2) {
            android.service.autofill.augmented.AugmentedAutofillService.this.mHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new com.android.internal.util.function.TriConsumer() { // from class: android.service.autofill.augmented.AugmentedAutofillService$AugmentedAutofillServiceImpl$$ExternalSyntheticLambda0
                @Override // com.android.internal.util.function.TriConsumer
                public final void accept(java.lang.Object obj, java.lang.Object obj2, java.lang.Object obj3) {
                    ((android.service.autofill.augmented.AugmentedAutofillService) obj).handleOnConnected(((java.lang.Boolean) obj2).booleanValue(), ((java.lang.Boolean) obj3).booleanValue());
                }
            }, android.service.autofill.augmented.AugmentedAutofillService.this, java.lang.Boolean.valueOf(z), java.lang.Boolean.valueOf(z2)));
        }

        @Override // android.service.autofill.augmented.IAugmentedAutofillService
        public void onDisconnected() {
            android.service.autofill.augmented.AugmentedAutofillService.this.mHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new java.util.function.Consumer() { // from class: android.service.autofill.augmented.AugmentedAutofillService$AugmentedAutofillServiceImpl$$ExternalSyntheticLambda2
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    ((android.service.autofill.augmented.AugmentedAutofillService) obj).handleOnDisconnected();
                }
            }, android.service.autofill.augmented.AugmentedAutofillService.this));
        }

        @Override // android.service.autofill.augmented.IAugmentedAutofillService
        public void onFillRequest(int i, android.os.IBinder iBinder, int i2, android.content.ComponentName componentName, android.view.autofill.AutofillId autofillId, android.view.autofill.AutofillValue autofillValue, long j, android.view.inputmethod.InlineSuggestionsRequest inlineSuggestionsRequest, android.service.autofill.augmented.IFillCallback iFillCallback) {
            android.service.autofill.augmented.AugmentedAutofillService.this.mHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new com.android.internal.util.function.DecConsumer() { // from class: android.service.autofill.augmented.AugmentedAutofillService$AugmentedAutofillServiceImpl$$ExternalSyntheticLambda1
                @Override // com.android.internal.util.function.DecConsumer
                public final void accept(java.lang.Object obj, java.lang.Object obj2, java.lang.Object obj3, java.lang.Object obj4, java.lang.Object obj5, java.lang.Object obj6, java.lang.Object obj7, java.lang.Object obj8, java.lang.Object obj9, java.lang.Object obj10) {
                    ((android.service.autofill.augmented.AugmentedAutofillService) obj).handleOnFillRequest(((java.lang.Integer) obj2).intValue(), (android.os.IBinder) obj3, ((java.lang.Integer) obj4).intValue(), (android.content.ComponentName) obj5, (android.view.autofill.AutofillId) obj6, (android.view.autofill.AutofillValue) obj7, ((java.lang.Long) obj8).longValue(), (android.view.inputmethod.InlineSuggestionsRequest) obj9, (android.service.autofill.augmented.IFillCallback) obj10);
                }
            }, android.service.autofill.augmented.AugmentedAutofillService.this, java.lang.Integer.valueOf(i), iBinder, java.lang.Integer.valueOf(i2), componentName, autofillId, autofillValue, java.lang.Long.valueOf(j), inlineSuggestionsRequest, iFillCallback));
        }

        @Override // android.service.autofill.augmented.IAugmentedAutofillService
        public void onDestroyAllFillWindowsRequest() {
            android.service.autofill.augmented.AugmentedAutofillService.this.mHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new java.util.function.Consumer() { // from class: android.service.autofill.augmented.AugmentedAutofillService$AugmentedAutofillServiceImpl$$ExternalSyntheticLambda3
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    ((android.service.autofill.augmented.AugmentedAutofillService) obj).handleOnDestroyAllFillWindowsRequest();
                }
            }, android.service.autofill.augmented.AugmentedAutofillService.this));
        }
    }

    @Override // android.app.Service
    public void onCreate() {
        super.onCreate();
        this.mHandler = new android.os.Handler(android.os.Looper.getMainLooper(), null, true);
        android.os.BaseBundle.setShouldDefuse(true);
    }

    @Override // android.app.Service
    public final android.os.IBinder onBind(android.content.Intent intent) {
        this.mServiceComponentName = intent.getComponent();
        if (SERVICE_INTERFACE.equals(intent.getAction())) {
            return new android.service.autofill.augmented.AugmentedAutofillService.AugmentedAutofillServiceImpl();
        }
        android.util.Log.w(TAG, "Tried to bind to wrong intent (should be android.service.autofill.augmented.AugmentedAutofillService: " + intent);
        return null;
    }

    @Override // android.app.Service
    public boolean onUnbind(android.content.Intent intent) {
        this.mHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new java.util.function.Consumer() { // from class: android.service.autofill.augmented.AugmentedAutofillService$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                ((android.service.autofill.augmented.AugmentedAutofillService) obj).handleOnUnbind();
            }
        }, this));
        return false;
    }

    public void onConnected() {
    }

    public final boolean requestAutofill(android.content.ComponentName componentName, android.view.autofill.AutofillId autofillId) {
        android.service.autofill.augmented.AugmentedAutofillService.AutofillProxy autofillProxy = this.mAutofillProxyForLastRequest;
        if (autofillProxy == null || !autofillProxy.mComponentName.equals(componentName) || !autofillProxy.mFocusedId.equals(autofillId)) {
            return false;
        }
        try {
            return autofillProxy.requestAutofill();
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
            return false;
        }
    }

    public void onFillRequest(android.service.autofill.augmented.FillRequest fillRequest, android.os.CancellationSignal cancellationSignal, android.service.autofill.augmented.FillController fillController, android.service.autofill.augmented.FillCallback fillCallback) {
    }

    public void onDisconnected() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleOnConnected(boolean z, boolean z2) {
        if (sDebug || z) {
            android.util.Log.d(TAG, "handleOnConnected(): debug=" + z + ", verbose=" + z2);
        }
        sDebug = z;
        sVerbose = z2;
        onConnected();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleOnDisconnected() {
        onDisconnected();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleOnFillRequest(int i, android.os.IBinder iBinder, int i2, android.content.ComponentName componentName, android.view.autofill.AutofillId autofillId, android.view.autofill.AutofillValue autofillValue, long j, android.view.inputmethod.InlineSuggestionsRequest inlineSuggestionsRequest, android.service.autofill.augmented.IFillCallback iFillCallback) {
        android.os.ICancellationSignal iCancellationSignal;
        android.os.CancellationSignal cancellationSignal;
        if (this.mAutofillProxies == null) {
            this.mAutofillProxies = new android.util.SparseArray<>();
        }
        android.os.ICancellationSignal createTransport = android.os.CancellationSignal.createTransport();
        android.os.CancellationSignal fromTransport = android.os.CancellationSignal.fromTransport(createTransport);
        android.service.autofill.augmented.AugmentedAutofillService.AutofillProxy autofillProxy = this.mAutofillProxies.get(i);
        if (autofillProxy == null) {
            iCancellationSignal = createTransport;
            android.service.autofill.augmented.AugmentedAutofillService.AutofillProxy autofillProxy2 = new android.service.autofill.augmented.AugmentedAutofillService.AutofillProxy(i, iBinder, i2, this.mServiceComponentName, componentName, autofillId, autofillValue, j, iFillCallback, fromTransport);
            this.mAutofillProxies.put(i, autofillProxy2);
            cancellationSignal = fromTransport;
            autofillProxy = autofillProxy2;
        } else {
            iCancellationSignal = createTransport;
            if (sDebug) {
                android.util.Log.d(TAG, "Reusing proxy for session " + i);
            }
            cancellationSignal = fromTransport;
            autofillProxy.update(autofillId, autofillValue, iFillCallback, cancellationSignal);
        }
        try {
            iFillCallback.onCancellable(iCancellationSignal);
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
        }
        this.mAutofillProxyForLastRequest = autofillProxy;
        onFillRequest(new android.service.autofill.augmented.FillRequest(autofillProxy, inlineSuggestionsRequest), cancellationSignal, new android.service.autofill.augmented.FillController(autofillProxy), new android.service.autofill.augmented.FillCallback(autofillProxy));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleOnDestroyAllFillWindowsRequest() {
        if (this.mAutofillProxies != null) {
            int size = this.mAutofillProxies.size();
            for (int i = 0; i < size; i++) {
                int keyAt = this.mAutofillProxies.keyAt(i);
                android.service.autofill.augmented.AugmentedAutofillService.AutofillProxy valueAt = this.mAutofillProxies.valueAt(i);
                if (valueAt == null) {
                    android.util.Log.w(TAG, "No proxy for session " + keyAt);
                    return;
                }
                if (valueAt.mCallback != null) {
                    try {
                        if (!valueAt.mCallback.isCompleted()) {
                            valueAt.mCallback.cancel();
                        }
                    } catch (java.lang.Exception e) {
                        android.util.Log.e(TAG, "failed to check current pending request status", e);
                    }
                }
                valueAt.destroy();
            }
            this.mAutofillProxies.clear();
            this.mAutofillProxyForLastRequest = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleOnUnbind() {
        if (this.mAutofillProxies == null) {
            if (sDebug) {
                android.util.Log.d(TAG, "onUnbind(): no proxy to destroy");
                return;
            }
            return;
        }
        int size = this.mAutofillProxies.size();
        if (sDebug) {
            android.util.Log.d(TAG, "onUnbind(): destroying " + size + " proxies");
        }
        for (int i = 0; i < size; i++) {
            android.service.autofill.augmented.AugmentedAutofillService.AutofillProxy valueAt = this.mAutofillProxies.valueAt(i);
            try {
                valueAt.destroy();
            } catch (java.lang.Exception e) {
                android.util.Log.w(TAG, "error destroying " + valueAt);
            }
        }
        this.mAutofillProxies = null;
        this.mAutofillProxyForLastRequest = null;
    }

    @Override // android.app.Service
    protected final void dump(java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, java.lang.String[] strArr) {
        printWriter.print("Service component: ");
        printWriter.println(android.content.ComponentName.flattenToShortString(this.mServiceComponentName));
        if (this.mAutofillProxies != null) {
            int size = this.mAutofillProxies.size();
            printWriter.print("Number proxies: ");
            printWriter.println(size);
            for (int i = 0; i < size; i++) {
                int keyAt = this.mAutofillProxies.keyAt(i);
                android.service.autofill.augmented.AugmentedAutofillService.AutofillProxy valueAt = this.mAutofillProxies.valueAt(i);
                printWriter.print(i);
                printWriter.print(") SessionId=");
                printWriter.print(keyAt);
                printWriter.println(":");
                valueAt.dump("  ", printWriter);
            }
        }
        dump(printWriter, strArr);
    }

    protected void dump(java.io.PrintWriter printWriter, java.lang.String[] strArr) {
        printWriter.print(getClass().getName());
        printWriter.println(": nothing to dump");
    }

    public final android.service.autofill.FillEventHistory getFillEventHistory() {
        android.view.autofill.AutofillManager autofillManager = (android.view.autofill.AutofillManager) getSystemService(android.view.autofill.AutofillManager.class);
        if (autofillManager == null) {
            return null;
        }
        return autofillManager.getFillEventHistory();
    }

    static final class AutofillProxy {
        static final int REPORT_EVENT_INLINE_RESPONSE = 4;
        static final int REPORT_EVENT_NO_RESPONSE = 1;
        static final int REPORT_EVENT_UI_DESTROYED = 3;
        static final int REPORT_EVENT_UI_SHOWN = 2;
        private android.service.autofill.augmented.IFillCallback mCallback;
        private android.os.CancellationSignal mCancellationSignal;
        private final android.view.autofill.IAugmentedAutofillManagerClient mClient;
        public final android.content.ComponentName mComponentName;
        private android.service.autofill.augmented.FillWindow mFillWindow;
        private long mFirstOnSuccessTime;
        private final long mFirstRequestTime;
        private android.view.autofill.AutofillId mFocusedId;
        private android.view.autofill.AutofillValue mFocusedValue;
        private android.app.assist.AssistStructure.ViewNode mFocusedViewNode;
        private android.view.autofill.AutofillId mLastShownId;
        private final java.lang.Object mLock;
        private java.lang.String mServicePackageName;
        private final int mSessionId;
        private android.service.autofill.augmented.PresentationParams.SystemPopupPresentationParams mSmartSuggestion;
        public final int mTaskId;
        private long mUiFirstDestroyedTime;
        private long mUiFirstShownTime;

        @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
        @interface ReportEvent {
        }

        private AutofillProxy(int i, android.os.IBinder iBinder, int i2, android.content.ComponentName componentName, android.content.ComponentName componentName2, android.view.autofill.AutofillId autofillId, android.view.autofill.AutofillValue autofillValue, long j, android.service.autofill.augmented.IFillCallback iFillCallback, android.os.CancellationSignal cancellationSignal) {
            this.mLock = new java.lang.Object();
            this.mSessionId = i;
            this.mClient = android.view.autofill.IAugmentedAutofillManagerClient.Stub.asInterface(iBinder);
            this.mCallback = iFillCallback;
            this.mTaskId = i2;
            this.mComponentName = componentName2;
            this.mServicePackageName = componentName.getPackageName();
            this.mFocusedId = autofillId;
            this.mFocusedValue = autofillValue;
            this.mFirstRequestTime = j;
            this.mCancellationSignal = cancellationSignal;
        }

        public android.service.autofill.augmented.PresentationParams.SystemPopupPresentationParams getSmartSuggestionParams() {
            synchronized (this.mLock) {
                if (this.mSmartSuggestion != null && this.mFocusedId.equals(this.mLastShownId)) {
                    return this.mSmartSuggestion;
                }
                try {
                    android.graphics.Rect viewCoordinates = this.mClient.getViewCoordinates(this.mFocusedId);
                    if (viewCoordinates == null) {
                        if (android.service.autofill.augmented.AugmentedAutofillService.sDebug) {
                            android.util.Log.d(android.service.autofill.augmented.AugmentedAutofillService.TAG, "getViewCoordinates(" + this.mFocusedId + ") returned null");
                        }
                        return null;
                    }
                    this.mSmartSuggestion = new android.service.autofill.augmented.PresentationParams.SystemPopupPresentationParams(this, viewCoordinates);
                    this.mLastShownId = this.mFocusedId;
                    return this.mSmartSuggestion;
                } catch (android.os.RemoteException e) {
                    android.util.Log.w(android.service.autofill.augmented.AugmentedAutofillService.TAG, "Could not get coordinates for " + this.mFocusedId);
                    return null;
                }
            }
        }

        public void autofill(java.util.List<android.util.Pair<android.view.autofill.AutofillId, android.view.autofill.AutofillValue>> list) throws android.os.RemoteException {
            int size = list.size();
            java.util.ArrayList arrayList = new java.util.ArrayList(size);
            java.util.ArrayList arrayList2 = new java.util.ArrayList(size);
            boolean z = false;
            for (int i = 0; i < size; i++) {
                android.util.Pair<android.view.autofill.AutofillId, android.view.autofill.AutofillValue> pair = list.get(i);
                arrayList.add(pair.first);
                arrayList2.add(pair.second);
            }
            if (size == 1 && ((android.view.autofill.AutofillId) arrayList.get(0)).equals(this.mFocusedId)) {
                z = true;
            }
            this.mClient.autofill(this.mSessionId, arrayList, arrayList2, z);
        }

        public void setFillWindow(android.service.autofill.augmented.FillWindow fillWindow) {
            synchronized (this.mLock) {
                this.mFillWindow = fillWindow;
            }
        }

        public android.service.autofill.augmented.FillWindow getFillWindow() {
            android.service.autofill.augmented.FillWindow fillWindow;
            synchronized (this.mLock) {
                fillWindow = this.mFillWindow;
            }
            return fillWindow;
        }

        public void requestShowFillUi(int i, int i2, android.graphics.Rect rect, android.view.autofill.IAutofillWindowPresenter iAutofillWindowPresenter) throws android.os.RemoteException {
            if (this.mCancellationSignal.isCanceled()) {
                if (android.service.autofill.augmented.AugmentedAutofillService.sVerbose) {
                    android.util.Log.v(android.service.autofill.augmented.AugmentedAutofillService.TAG, "requestShowFillUi() not showing because request is cancelled");
                    return;
                }
                return;
            }
            this.mClient.requestShowFillUi(this.mSessionId, this.mFocusedId, i, i2, rect, iAutofillWindowPresenter);
        }

        public void requestHideFillUi() throws android.os.RemoteException {
            this.mClient.requestHideFillUi(this.mSessionId, this.mFocusedId);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public boolean requestAutofill() throws android.os.RemoteException {
            return this.mClient.requestAutofill(this.mSessionId, this.mFocusedId);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void update(android.view.autofill.AutofillId autofillId, android.view.autofill.AutofillValue autofillValue, android.service.autofill.augmented.IFillCallback iFillCallback, android.os.CancellationSignal cancellationSignal) {
            synchronized (this.mLock) {
                this.mFocusedId = autofillId;
                this.mFocusedValue = autofillValue;
                this.mFocusedViewNode = null;
                if (this.mCallback != null) {
                    try {
                        if (!this.mCallback.isCompleted()) {
                            this.mCallback.cancel();
                        }
                    } catch (android.os.RemoteException e) {
                        android.util.Log.e(android.service.autofill.augmented.AugmentedAutofillService.TAG, "failed to check current pending request status", e);
                    }
                    android.util.Log.d(android.service.autofill.augmented.AugmentedAutofillService.TAG, "mCallback is updated.");
                }
                this.mCallback = iFillCallback;
                this.mCancellationSignal = cancellationSignal;
            }
        }

        public android.view.autofill.AutofillId getFocusedId() {
            android.view.autofill.AutofillId autofillId;
            synchronized (this.mLock) {
                autofillId = this.mFocusedId;
            }
            return autofillId;
        }

        public android.view.autofill.AutofillValue getFocusedValue() {
            android.view.autofill.AutofillValue autofillValue;
            synchronized (this.mLock) {
                autofillValue = this.mFocusedValue;
            }
            return autofillValue;
        }

        void reportResult(java.util.List<android.service.autofill.Dataset> list, android.os.Bundle bundle, boolean z) {
            try {
                this.mCallback.onSuccess(list, bundle, z);
            } catch (android.os.RemoteException e) {
                android.util.Log.e(android.service.autofill.augmented.AugmentedAutofillService.TAG, "Error calling back with the inline suggestions data: " + e);
            }
        }

        public android.app.assist.AssistStructure.ViewNode getFocusedViewNode() {
            android.app.assist.AssistStructure.ViewNode viewNode;
            synchronized (this.mLock) {
                if (this.mFocusedViewNode == null) {
                    try {
                        android.app.assist.AssistStructure.ViewNodeParcelable viewNodeParcelable = this.mClient.getViewNodeParcelable(this.mFocusedId);
                        if (viewNodeParcelable != null) {
                            this.mFocusedViewNode = viewNodeParcelable.getViewNode();
                        }
                    } catch (android.os.RemoteException e) {
                        android.util.Log.e(android.service.autofill.augmented.AugmentedAutofillService.TAG, "Error getting the ViewNode of the focused view: " + e);
                        return null;
                    }
                }
                viewNode = this.mFocusedViewNode;
            }
            return viewNode;
        }

        void logEvent(int i) {
            long j;
            int i2;
            if (android.service.autofill.augmented.AugmentedAutofillService.sVerbose) {
                android.util.Log.v(android.service.autofill.augmented.AugmentedAutofillService.TAG, "returnAndLogResult(): " + i);
            }
            long j2 = -1;
            int i3 = 0;
            switch (i) {
                case 1:
                    i3 = 10;
                    if (this.mFirstOnSuccessTime != 0) {
                        j = -1;
                        i2 = 10;
                        break;
                    } else {
                        this.mFirstOnSuccessTime = android.os.SystemClock.elapsedRealtime();
                        j2 = this.mFirstOnSuccessTime - this.mFirstRequestTime;
                        if (android.service.autofill.augmented.AugmentedAutofillService.sDebug) {
                            android.util.Log.d(android.service.autofill.augmented.AugmentedAutofillService.TAG, "Service responded nothing in " + android.util.TimeUtils.formatDuration(j2));
                        }
                        j = j2;
                        i2 = i3;
                        break;
                    }
                case 2:
                    i3 = 1;
                    if (this.mUiFirstShownTime != 0) {
                        j = -1;
                        i2 = 1;
                        break;
                    } else {
                        this.mUiFirstShownTime = android.os.SystemClock.elapsedRealtime();
                        j2 = this.mUiFirstShownTime - this.mFirstRequestTime;
                        if (android.service.autofill.augmented.AugmentedAutofillService.sDebug) {
                            android.util.Log.d(android.service.autofill.augmented.AugmentedAutofillService.TAG, "UI shown in " + android.util.TimeUtils.formatDuration(j2));
                        }
                        j = j2;
                        i2 = i3;
                        break;
                    }
                case 3:
                    i3 = 2;
                    if (this.mUiFirstDestroyedTime != 0) {
                        j = -1;
                        i2 = 2;
                        break;
                    } else {
                        this.mUiFirstDestroyedTime = android.os.SystemClock.elapsedRealtime();
                        j2 = this.mUiFirstDestroyedTime - this.mFirstRequestTime;
                        if (android.service.autofill.augmented.AugmentedAutofillService.sDebug) {
                            android.util.Log.d(android.service.autofill.augmented.AugmentedAutofillService.TAG, "UI destroyed in " + android.util.TimeUtils.formatDuration(j2));
                        }
                        j = j2;
                        i2 = i3;
                        break;
                    }
                case 4:
                    if (this.mFirstOnSuccessTime == 0) {
                        this.mFirstOnSuccessTime = android.os.SystemClock.elapsedRealtime();
                        j2 = this.mFirstOnSuccessTime - this.mFirstRequestTime;
                        if (android.service.autofill.augmented.AugmentedAutofillService.sDebug) {
                            android.util.Log.d(android.service.autofill.augmented.AugmentedAutofillService.TAG, "Inline response in " + android.util.TimeUtils.formatDuration(j2));
                        }
                    }
                    j = j2;
                    i2 = i3;
                    break;
                default:
                    android.util.Log.w(android.service.autofill.augmented.AugmentedAutofillService.TAG, "invalid event reported: " + i);
                    j = j2;
                    i2 = i3;
                    break;
            }
            android.service.autofill.augmented.Helper.logResponse(i2, this.mServicePackageName, this.mComponentName, this.mSessionId, j);
        }

        public void dump(java.lang.String str, java.io.PrintWriter printWriter) {
            printWriter.print(str);
            printWriter.print("sessionId: ");
            printWriter.println(this.mSessionId);
            printWriter.print(str);
            printWriter.print("taskId: ");
            printWriter.println(this.mTaskId);
            printWriter.print(str);
            printWriter.print("component: ");
            printWriter.println(this.mComponentName.flattenToShortString());
            printWriter.print(str);
            printWriter.print("focusedId: ");
            printWriter.println(this.mFocusedId);
            if (this.mFocusedValue != null) {
                printWriter.print(str);
                printWriter.print("focusedValue: ");
                printWriter.println(this.mFocusedValue);
            }
            if (this.mLastShownId != null) {
                printWriter.print(str);
                printWriter.print("lastShownId: ");
                printWriter.println(this.mLastShownId);
            }
            printWriter.print(str);
            printWriter.print("client: ");
            printWriter.println(this.mClient);
            java.lang.String str2 = str + "  ";
            if (this.mFillWindow != null) {
                printWriter.print(str);
                printWriter.println("window:");
                this.mFillWindow.dump(str2, printWriter);
            }
            if (this.mSmartSuggestion != null) {
                printWriter.print(str);
                printWriter.println("smartSuggestion:");
                this.mSmartSuggestion.dump(str2, printWriter);
            }
            if (this.mFirstOnSuccessTime > 0) {
                long j = this.mFirstOnSuccessTime - this.mFirstRequestTime;
                printWriter.print(str);
                printWriter.print("response time: ");
                android.util.TimeUtils.formatDuration(j, printWriter);
                printWriter.println();
            }
            if (this.mUiFirstShownTime > 0) {
                long j2 = this.mUiFirstShownTime - this.mFirstRequestTime;
                printWriter.print(str);
                printWriter.print("UI rendering time: ");
                android.util.TimeUtils.formatDuration(j2, printWriter);
                printWriter.println();
            }
            if (this.mUiFirstDestroyedTime > 0) {
                long j3 = this.mUiFirstDestroyedTime - this.mFirstRequestTime;
                printWriter.print(str);
                printWriter.print("UI life time: ");
                android.util.TimeUtils.formatDuration(j3, printWriter);
                printWriter.println();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void destroy() {
            synchronized (this.mLock) {
                if (this.mFillWindow != null) {
                    if (android.service.autofill.augmented.AugmentedAutofillService.sDebug) {
                        android.util.Log.d(android.service.autofill.augmented.AugmentedAutofillService.TAG, "destroying window");
                    }
                    this.mFillWindow.destroy();
                    this.mFillWindow = null;
                }
            }
        }
    }
}
