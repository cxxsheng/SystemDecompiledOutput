package android.view.translation;

/* loaded from: classes4.dex */
public class UiTranslationController implements android.util.Dumpable {
    public static final boolean DEBUG = android.util.Log.isLoggable(android.view.translation.UiTranslationManager.LOG_TAG, 3);
    public static final java.lang.String DUMPABLE_NAME = "UiTranslationController";
    private static final java.lang.String TAG = "UiTranslationController";
    private final android.app.Activity mActivity;
    private final android.content.Context mContext;
    private int mCurrentState;
    private android.util.ArraySet<android.view.autofill.AutofillId> mLastRequestAutofillIds;
    private final android.os.Handler mWorkerHandler;
    private final android.os.HandlerThread mWorkerThread;
    private final java.lang.Object mLock = new java.lang.Object();
    private final android.util.ArrayMap<android.view.autofill.AutofillId, java.lang.ref.WeakReference<android.view.View>> mViews = new android.util.ArrayMap<>();
    private final android.util.ArrayMap<android.util.Pair<android.view.translation.TranslationSpec, android.view.translation.TranslationSpec>, android.view.translation.Translator> mTranslators = new android.util.ArrayMap<>();
    private final android.util.ArraySet<android.view.autofill.AutofillId> mViewsToPadContent = new android.util.ArraySet<>();

    public UiTranslationController(android.app.Activity activity, android.content.Context context) {
        this.mActivity = activity;
        this.mContext = context;
        this.mWorkerThread = new android.os.HandlerThread("UiTranslationController_" + this.mActivity.getComponentName(), -2);
        this.mWorkerThread.start();
        this.mWorkerHandler = this.mWorkerThread.getThreadHandler();
        activity.addDumpable(this);
    }

    public void updateUiTranslationState(int i, android.view.translation.TranslationSpec translationSpec, android.view.translation.TranslationSpec translationSpec2, java.util.List<android.view.autofill.AutofillId> list, android.view.translation.UiTranslationSpec uiTranslationSpec) {
        if (this.mActivity.isDestroyed()) {
            android.util.Log.i("UiTranslationController", "Cannot update " + stateToString(i) + " for destroyed " + this.mActivity);
            return;
        }
        android.util.Log.i("UiTranslationController", "updateUiTranslationState state: " + stateToString(i) + (android.util.Log.isLoggable(android.view.translation.UiTranslationManager.LOG_TAG, 3) ? ", views: " + list + ", spec: " + uiTranslationSpec : ""));
        synchronized (this.mLock) {
            this.mCurrentState = i;
            if (list != null) {
                setLastRequestAutofillIdsLocked(list);
            }
        }
        switch (i) {
            case 0:
                if (uiTranslationSpec != null && uiTranslationSpec.shouldPadContentForCompat()) {
                    synchronized (this.mLock) {
                        this.mViewsToPadContent.addAll(list);
                    }
                }
                android.util.Pair pair = new android.util.Pair(translationSpec, translationSpec2);
                if (!this.mTranslators.containsKey(pair)) {
                    this.mWorkerHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new com.android.internal.util.function.QuadConsumer() { // from class: android.view.translation.UiTranslationController$$ExternalSyntheticLambda3
                        @Override // com.android.internal.util.function.QuadConsumer
                        public final void accept(java.lang.Object obj, java.lang.Object obj2, java.lang.Object obj3, java.lang.Object obj4) {
                            ((android.view.translation.UiTranslationController) obj).createTranslatorAndStart((android.view.translation.TranslationSpec) obj2, (android.view.translation.TranslationSpec) obj3, (java.util.List) obj4);
                        }
                    }, this, translationSpec, translationSpec2, list));
                    return;
                } else {
                    onUiTranslationStarted(this.mTranslators.get(pair), list);
                    return;
                }
            case 1:
                runForEachView(new java.util.function.BiConsumer() { // from class: android.view.translation.UiTranslationController$$ExternalSyntheticLambda4
                    @Override // java.util.function.BiConsumer
                    public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                        ((android.view.translation.ViewTranslationCallback) obj2).onHideTranslation((android.view.View) obj);
                    }
                });
                return;
            case 2:
                runForEachView(new java.util.function.BiConsumer() { // from class: android.view.translation.UiTranslationController$$ExternalSyntheticLambda5
                    @Override // java.util.function.BiConsumer
                    public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                        ((android.view.translation.ViewTranslationCallback) obj2).onShowTranslation((android.view.View) obj);
                    }
                });
                return;
            case 3:
                destroyTranslators();
                runForEachView(new java.util.function.BiConsumer() { // from class: android.view.translation.UiTranslationController$$ExternalSyntheticLambda6
                    @Override // java.util.function.BiConsumer
                    public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                        ((android.view.View) obj).clearTranslationState();
                    }
                });
                notifyTranslationFinished(false);
                synchronized (this.mLock) {
                    this.mViews.clear();
                }
                return;
            default:
                android.util.Log.w("UiTranslationController", "onAutoTranslationStateChange(): unknown state: " + i);
                return;
        }
    }

    public void onActivityDestroyed() {
        synchronized (this.mLock) {
            android.util.Log.i("UiTranslationController", "onActivityDestroyed(): mCurrentState is " + stateToString(this.mCurrentState));
            if (this.mCurrentState != 3) {
                notifyTranslationFinished(true);
            }
            this.mViews.clear();
            destroyTranslators();
            this.mWorkerThread.quitSafely();
        }
    }

    private void notifyTranslationFinished(boolean z) {
        android.view.translation.UiTranslationManager uiTranslationManager = (android.view.translation.UiTranslationManager) this.mContext.getSystemService(android.view.translation.UiTranslationManager.class);
        if (uiTranslationManager != null) {
            uiTranslationManager.onTranslationFinished(z, new android.app.assist.ActivityId(this.mActivity.getTaskId(), this.mActivity.getShareableActivityToken()), this.mActivity.getComponentName());
        }
    }

    private void setLastRequestAutofillIdsLocked(java.util.List<android.view.autofill.AutofillId> list) {
        if (this.mLastRequestAutofillIds == null) {
            this.mLastRequestAutofillIds = new android.util.ArraySet<>();
        }
        if (this.mLastRequestAutofillIds.size() > 0) {
            this.mLastRequestAutofillIds.clear();
        }
        this.mLastRequestAutofillIds.addAll(list);
    }

    @Override // android.util.Dumpable
    public java.lang.String getDumpableName() {
        return "UiTranslationController";
    }

    @Override // android.util.Dumpable
    public void dump(java.io.PrintWriter printWriter, java.lang.String[] strArr) {
        printWriter.print("");
        printWriter.println("UiTranslationController:");
        java.lang.String str = "  ";
        printWriter.print(str);
        printWriter.print("activity: ");
        printWriter.print(this.mActivity);
        printWriter.print(str);
        printWriter.print("resumed: ");
        printWriter.println(this.mActivity.isResumed());
        printWriter.print(str);
        printWriter.print("current state: ");
        printWriter.println(this.mCurrentState);
        int size = this.mTranslators.size();
        printWriter.print("");
        printWriter.print("number translator: ");
        printWriter.println(size);
        for (int i = 0; i < size; i++) {
            printWriter.print("");
            printWriter.print("#");
            printWriter.println(i);
            this.mTranslators.valueAt(i).dump("", printWriter);
            printWriter.println();
        }
        synchronized (this.mLock) {
            int size2 = this.mViews.size();
            printWriter.print("");
            printWriter.print("number views: ");
            printWriter.println(size2);
            for (int i2 = 0; i2 < size2; i2++) {
                printWriter.print("");
                printWriter.print("#");
                printWriter.println(i2);
                android.view.autofill.AutofillId keyAt = this.mViews.keyAt(i2);
                android.view.View view = this.mViews.valueAt(i2).get();
                printWriter.print(str);
                printWriter.print("autofillId: ");
                printWriter.println(keyAt);
                printWriter.print(str);
                printWriter.print("view:");
                printWriter.println(view);
            }
            printWriter.print("");
            printWriter.print("padded views: ");
            printWriter.println(this.mViewsToPadContent);
        }
        if (android.util.Log.isLoggable(android.view.translation.UiTranslationManager.LOG_TAG, 3)) {
            dumpViewByTraversal("", printWriter);
        }
    }

    private void dumpViewByTraversal(java.lang.String str, java.io.PrintWriter printWriter) {
        java.util.ArrayList<android.view.ViewRootImpl> rootViews = android.view.WindowManagerGlobal.getInstance().getRootViews(this.mActivity.getActivityToken());
        printWriter.print(str);
        printWriter.println("Dump views:");
        for (int i = 0; i < rootViews.size(); i++) {
            android.view.View view = rootViews.get(i).getView();
            if (view instanceof android.view.ViewGroup) {
                dumpChildren((android.view.ViewGroup) view, str, printWriter);
            } else {
                dumpViewInfo(view, str, printWriter);
            }
        }
    }

    private void dumpChildren(android.view.ViewGroup viewGroup, java.lang.String str, java.io.PrintWriter printWriter) {
        int childCount = viewGroup.getChildCount();
        for (int i = 0; i < childCount; i++) {
            android.view.View childAt = viewGroup.getChildAt(i);
            if (childAt instanceof android.view.ViewGroup) {
                printWriter.print(str);
                printWriter.println("Children: ");
                printWriter.print(str);
                printWriter.print(str);
                printWriter.println(childAt);
                dumpChildren((android.view.ViewGroup) childAt, str, printWriter);
            } else {
                printWriter.print(str);
                printWriter.println("End Children: ");
                printWriter.print(str);
                printWriter.print(str);
                printWriter.print(childAt);
                dumpViewInfo(childAt, str, printWriter);
            }
        }
    }

    private void dumpViewInfo(android.view.View view, java.lang.String str, java.io.PrintWriter printWriter) {
        boolean z;
        boolean z2;
        android.view.autofill.AutofillId autofillId = view.getAutofillId();
        printWriter.print(str);
        printWriter.print("autofillId: ");
        printWriter.print(autofillId);
        synchronized (this.mLock) {
            z = true;
            if (!this.mLastRequestAutofillIds.contains(autofillId)) {
                z2 = false;
            } else {
                z2 = true;
            }
            java.lang.ref.WeakReference<android.view.View> weakReference = this.mViews.get(autofillId);
            if (weakReference == null || weakReference.get() == null) {
                z = false;
            }
        }
        printWriter.print(str);
        printWriter.print("isContainsView: ");
        printWriter.print(z);
        printWriter.print(str);
        printWriter.print("isRequestedView: ");
        printWriter.println(z2);
    }

    public void onTranslationCompleted(android.view.translation.TranslationResponse translationResponse) {
        java.lang.Object valueOf;
        if (translationResponse == null || translationResponse.getTranslationStatus() != 0) {
            java.lang.StringBuilder append = new java.lang.StringBuilder().append("Fail result from TranslationService, status=");
            if (translationResponse == null) {
                valueOf = "null";
            } else {
                valueOf = java.lang.Integer.valueOf(translationResponse.getTranslationStatus());
            }
            android.util.Log.w("UiTranslationController", append.append(valueOf).toString());
            return;
        }
        android.util.SparseArray<android.view.translation.ViewTranslationResponse> viewTranslationResponses = translationResponse.getViewTranslationResponses();
        android.util.SparseArray<android.view.translation.ViewTranslationResponse> sparseArray = new android.util.SparseArray<>();
        android.util.SparseArray<android.util.LongSparseArray<android.view.translation.ViewTranslationResponse>> sparseArray2 = new android.util.SparseArray<>();
        android.util.IntArray intArray = new android.util.IntArray(1);
        for (int i = 0; i < viewTranslationResponses.size(); i++) {
            android.view.translation.ViewTranslationResponse valueAt = viewTranslationResponses.valueAt(i);
            android.view.autofill.AutofillId autofillId = valueAt.getAutofillId();
            if (intArray.indexOf(autofillId.getViewId()) < 0) {
                intArray.add(autofillId.getViewId());
            }
            if (autofillId.isNonVirtual()) {
                sparseArray.put(viewTranslationResponses.keyAt(i), valueAt);
            } else {
                boolean z = sparseArray2.indexOfKey(autofillId.getViewId()) >= 0;
                android.util.LongSparseArray<android.view.translation.ViewTranslationResponse> longSparseArray = z ? sparseArray2.get(autofillId.getViewId()) : new android.util.LongSparseArray<>();
                longSparseArray.put(autofillId.getVirtualChildLongId(), valueAt);
                if (!z) {
                    sparseArray2.put(autofillId.getViewId(), longSparseArray);
                }
            }
        }
        findViewsTraversalByAutofillIds(intArray);
        if (sparseArray.size() > 0) {
            onTranslationCompleted(sparseArray);
        }
        if (sparseArray2.size() > 0) {
            onVirtualViewTranslationCompleted(sparseArray2);
        }
    }

    private void onVirtualViewTranslationCompleted(android.util.SparseArray<android.util.LongSparseArray<android.view.translation.ViewTranslationResponse>> sparseArray) {
        final boolean isLoggable = android.util.Log.isLoggable(android.view.translation.UiTranslationManager.LOG_TAG, 3);
        if (this.mActivity.isDestroyed()) {
            android.util.Log.v("UiTranslationController", "onTranslationCompleted:" + this.mActivity + "is destroyed.");
            return;
        }
        synchronized (this.mLock) {
            if (this.mCurrentState == 3) {
                android.util.Log.w("UiTranslationController", "onTranslationCompleted: the translation state is finished now. Skip to show the translated text.");
                return;
            }
            for (int i = 0; i < sparseArray.size(); i++) {
                android.view.autofill.AutofillId autofillId = new android.view.autofill.AutofillId(sparseArray.keyAt(i));
                java.lang.ref.WeakReference<android.view.View> weakReference = this.mViews.get(autofillId);
                if (weakReference != null) {
                    final android.view.View view = weakReference.get();
                    if (view == null) {
                        android.util.Log.w("UiTranslationController", "onTranslationCompleted: the view for autofill id " + autofillId + " may be gone.");
                    } else {
                        android.util.LongSparseArray<android.view.translation.ViewTranslationResponse> valueAt = sparseArray.valueAt(i);
                        if (isLoggable) {
                            android.util.Log.v("UiTranslationController", "onVirtualViewTranslationCompleted: received response for AutofillId " + autofillId);
                        }
                        view.onVirtualViewTranslationResponses(valueAt);
                        if (this.mCurrentState == 1) {
                            return;
                        } else {
                            this.mActivity.runOnUiThread(new java.lang.Runnable() { // from class: android.view.translation.UiTranslationController$$ExternalSyntheticLambda9
                                @Override // java.lang.Runnable
                                public final void run() {
                                    android.view.translation.UiTranslationController.lambda$onVirtualViewTranslationCompleted$3(android.view.View.this, isLoggable);
                                }
                            });
                        }
                    }
                }
            }
        }
    }

    static /* synthetic */ void lambda$onVirtualViewTranslationCompleted$3(android.view.View view, boolean z) {
        if (view.getViewTranslationCallback() == null) {
            if (z) {
                android.util.Log.d("UiTranslationController", view + " doesn't support showing translation because of null ViewTranslationCallback.");
            }
        } else if (view.getViewTranslationCallback() != null) {
            view.getViewTranslationCallback().onShowTranslation(view);
        }
    }

    private void onTranslationCompleted(android.util.SparseArray<android.view.translation.ViewTranslationResponse> sparseArray) {
        final boolean isLoggable = android.util.Log.isLoggable(android.view.translation.UiTranslationManager.LOG_TAG, 3);
        if (this.mActivity.isDestroyed()) {
            android.util.Log.v("UiTranslationController", "onTranslationCompleted:" + this.mActivity + "is destroyed.");
            return;
        }
        int size = sparseArray.size();
        if (isLoggable) {
            android.util.Log.v("UiTranslationController", "onTranslationCompleted: receive " + size + " responses.");
        }
        synchronized (this.mLock) {
            if (this.mCurrentState == 3) {
                android.util.Log.w("UiTranslationController", "onTranslationCompleted: the translation state is finished now. Skip to show the translated text.");
                return;
            }
            for (int i = 0; i < size; i++) {
                final android.view.translation.ViewTranslationResponse valueAt = sparseArray.valueAt(i);
                if (isLoggable) {
                    android.util.Log.v("UiTranslationController", "onTranslationCompleted: " + sanitizedViewTranslationResponse(valueAt));
                }
                final android.view.autofill.AutofillId autofillId = valueAt.getAutofillId();
                if (autofillId == null) {
                    android.util.Log.w("UiTranslationController", "No AutofillId is set in ViewTranslationResponse");
                } else {
                    java.lang.ref.WeakReference<android.view.View> weakReference = this.mViews.get(autofillId);
                    if (weakReference != null) {
                        final android.view.View view = weakReference.get();
                        if (view == null) {
                            android.util.Log.w("UiTranslationController", "onTranslationCompleted: the view for autofill id " + autofillId + " may be gone.");
                        } else {
                            final int i2 = this.mCurrentState;
                            this.mActivity.runOnUiThread(new java.lang.Runnable() { // from class: android.view.translation.UiTranslationController$$ExternalSyntheticLambda2
                                @Override // java.lang.Runnable
                                public final void run() {
                                    android.view.translation.UiTranslationController.this.lambda$onTranslationCompleted$4(view, valueAt, isLoggable, autofillId, i2);
                                }
                            });
                        }
                    }
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onTranslationCompleted$4(android.view.View view, android.view.translation.ViewTranslationResponse viewTranslationResponse, boolean z, android.view.autofill.AutofillId autofillId, int i) {
        android.view.translation.ViewTranslationCallback viewTranslationCallback = view.getViewTranslationCallback();
        if (view.getViewTranslationResponse() != null && view.getViewTranslationResponse().equals(viewTranslationResponse) && (viewTranslationCallback instanceof android.widget.TextViewTranslationCallback)) {
            android.widget.TextViewTranslationCallback textViewTranslationCallback = (android.widget.TextViewTranslationCallback) viewTranslationCallback;
            if (textViewTranslationCallback.isShowingTranslation() || textViewTranslationCallback.isAnimationRunning()) {
                if (z) {
                    android.util.Log.d("UiTranslationController", "Duplicate ViewTranslationResponse for " + autofillId + ". Ignoring.");
                    return;
                }
                return;
            }
        }
        if (viewTranslationCallback == null) {
            if (view instanceof android.widget.TextView) {
                viewTranslationCallback = new android.widget.TextViewTranslationCallback();
                view.setViewTranslationCallback(viewTranslationCallback);
            } else {
                if (z) {
                    android.util.Log.d("UiTranslationController", view + " doesn't support showing translation because of null ViewTranslationCallback.");
                    return;
                }
                return;
            }
        }
        viewTranslationCallback.setAnimationDurationMillis(250);
        if (this.mViewsToPadContent.contains(autofillId)) {
            viewTranslationCallback.enableContentPadding();
        }
        view.onViewTranslationResponse(viewTranslationResponse);
        if (i == 1) {
            return;
        }
        viewTranslationCallback.onShowTranslation(view);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void createTranslatorAndStart(android.view.translation.TranslationSpec translationSpec, android.view.translation.TranslationSpec translationSpec2, java.util.List<android.view.autofill.AutofillId> list) {
        android.view.translation.Translator createTranslatorIfNeeded = createTranslatorIfNeeded(translationSpec, translationSpec2);
        if (createTranslatorIfNeeded == null) {
            android.util.Log.w("UiTranslationController", "Can not create Translator for sourceSpec:" + translationSpec + " targetSpec:" + translationSpec2);
        } else {
            onUiTranslationStarted(createTranslatorIfNeeded, list);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendTranslationRequest(android.view.translation.Translator translator, java.util.List<android.view.translation.ViewTranslationRequest> list) {
        if (list.size() == 0) {
            android.util.Log.w("UiTranslationController", "No ViewTranslationRequest was collected.");
            return;
        }
        android.view.translation.TranslationRequest build = new android.view.translation.TranslationRequest.Builder().setViewTranslationRequests(list).build();
        if (android.util.Log.isLoggable(android.view.translation.UiTranslationManager.LOG_TAG, 3)) {
            java.lang.StringBuilder sb = new java.lang.StringBuilder("sendTranslationRequest:{requests=[");
            java.util.Iterator<android.view.translation.ViewTranslationRequest> it = list.iterator();
            while (it.hasNext()) {
                sb.append("{request=").append(sanitizedViewTranslationRequest(it.next())).append("}, ");
            }
            android.util.Log.d("UiTranslationController", "sendTranslationRequest: " + sb.toString());
        }
        translator.requestUiTranslate(build, new java.util.concurrent.Executor() { // from class: android.view.translation.UiTranslationController$$ExternalSyntheticLambda7
            @Override // java.util.concurrent.Executor
            public final void execute(java.lang.Runnable runnable) {
                runnable.run();
            }
        }, new java.util.function.Consumer() { // from class: android.view.translation.UiTranslationController$$ExternalSyntheticLambda8
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                android.view.translation.UiTranslationController.this.onTranslationCompleted((android.view.translation.TranslationResponse) obj);
            }
        });
    }

    /* JADX WARN: Multi-variable type inference failed */
    private void onUiTranslationStarted(final android.view.translation.Translator translator, java.util.List<android.view.autofill.AutofillId> list) {
        long[] jArr;
        int i;
        synchronized (this.mLock) {
            android.util.SparseIntArray requestVirtualViewChildCount = getRequestVirtualViewChildCount(list);
            final android.util.ArrayMap arrayMap = new android.util.ArrayMap();
            android.util.ArrayMap arrayMap2 = null;
            for (int i2 = 0; i2 < list.size(); i2++) {
                android.view.autofill.AutofillId autofillId = list.get(i2);
                if (autofillId.isNonVirtual()) {
                    arrayMap.put(autofillId, null);
                } else {
                    if (arrayMap2 == null) {
                        arrayMap2 = new android.util.ArrayMap();
                    }
                    android.view.autofill.AutofillId autofillId2 = new android.view.autofill.AutofillId(autofillId.getViewId());
                    if (arrayMap.containsKey(autofillId2)) {
                        jArr = (long[]) arrayMap.get(autofillId2);
                        i = ((java.lang.Integer) arrayMap2.get(autofillId2)).intValue();
                    } else {
                        jArr = new long[requestVirtualViewChildCount.get(autofillId.getViewId())];
                        arrayMap.put(autofillId2, jArr);
                        i = 0;
                    }
                    arrayMap2.put(autofillId2, java.lang.Integer.valueOf(i + 1));
                    jArr[i] = autofillId.getVirtualChildLongId();
                }
            }
            final java.util.ArrayList arrayList = new java.util.ArrayList();
            final int[] supportedFormatsLocked = getSupportedFormatsLocked();
            final java.util.ArrayList<android.view.ViewRootImpl> rootViews = android.view.WindowManagerGlobal.getInstance().getRootViews(this.mActivity.getActivityToken());
            final android.view.translation.TranslationCapability translationCapability = getTranslationCapability(translator.getTranslationContext());
            this.mActivity.runOnUiThread(new java.lang.Runnable() { // from class: android.view.translation.UiTranslationController$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    android.view.translation.UiTranslationController.this.lambda$onUiTranslationStarted$6(rootViews, arrayMap, supportedFormatsLocked, translationCapability, arrayList, translator);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onUiTranslationStarted$6(java.util.ArrayList arrayList, java.util.Map map, int[] iArr, android.view.translation.TranslationCapability translationCapability, java.util.ArrayList arrayList2, android.view.translation.Translator translator) {
        for (int i = 0; i < arrayList.size(); i++) {
            ((android.view.ViewRootImpl) arrayList.get(i)).getView().dispatchCreateViewTranslationRequest(map, iArr, translationCapability, arrayList2);
        }
        this.mWorkerHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new com.android.internal.util.function.TriConsumer() { // from class: android.view.translation.UiTranslationController$$ExternalSyntheticLambda10
            @Override // com.android.internal.util.function.TriConsumer
            public final void accept(java.lang.Object obj, java.lang.Object obj2, java.lang.Object obj3) {
                ((android.view.translation.UiTranslationController) obj).sendTranslationRequest((android.view.translation.Translator) obj2, (java.util.ArrayList) obj3);
            }
        }, this, translator, arrayList2));
    }

    private android.util.SparseIntArray getRequestVirtualViewChildCount(java.util.List<android.view.autofill.AutofillId> list) {
        android.util.SparseIntArray sparseIntArray = new android.util.SparseIntArray();
        for (int i = 0; i < list.size(); i++) {
            android.view.autofill.AutofillId autofillId = list.get(i);
            if (!autofillId.isNonVirtual()) {
                int viewId = autofillId.getViewId();
                if (sparseIntArray.indexOfKey(viewId) < 0) {
                    sparseIntArray.put(viewId, 1);
                } else {
                    sparseIntArray.put(viewId, sparseIntArray.get(viewId) + 1);
                }
            }
        }
        return sparseIntArray;
    }

    private int[] getSupportedFormatsLocked() {
        return new int[]{1};
    }

    private android.view.translation.TranslationCapability getTranslationCapability(android.view.translation.TranslationContext translationContext) {
        return new android.view.translation.TranslationCapability(3, translationContext.getSourceSpec(), translationContext.getTargetSpec(), true, 0);
    }

    private void findViewsTraversalByAutofillIds(android.util.IntArray intArray) {
        java.util.ArrayList<android.view.ViewRootImpl> rootViews = android.view.WindowManagerGlobal.getInstance().getRootViews(this.mActivity.getActivityToken());
        for (int i = 0; i < rootViews.size(); i++) {
            android.view.View view = rootViews.get(i).getView();
            if (view instanceof android.view.ViewGroup) {
                findViewsTraversalByAutofillIds((android.view.ViewGroup) view, intArray);
            }
            addViewIfNeeded(intArray, view);
        }
    }

    private void findViewsTraversalByAutofillIds(android.view.ViewGroup viewGroup, android.util.IntArray intArray) {
        int childCount = viewGroup.getChildCount();
        for (int i = 0; i < childCount; i++) {
            android.view.View childAt = viewGroup.getChildAt(i);
            if (childAt instanceof android.view.ViewGroup) {
                findViewsTraversalByAutofillIds((android.view.ViewGroup) childAt, intArray);
            }
            addViewIfNeeded(intArray, childAt);
        }
    }

    private void addViewIfNeeded(android.util.IntArray intArray, android.view.View view) {
        android.view.autofill.AutofillId autofillId = view.getAutofillId();
        if (autofillId != null && intArray.indexOf(autofillId.getViewId()) >= 0 && !this.mViews.containsKey(autofillId)) {
            this.mViews.put(autofillId, new java.lang.ref.WeakReference<>(view));
        }
    }

    private void runForEachView(final java.util.function.BiConsumer<android.view.View, android.view.translation.ViewTranslationCallback> biConsumer) {
        synchronized (this.mLock) {
            final boolean isLoggable = android.util.Log.isLoggable(android.view.translation.UiTranslationManager.LOG_TAG, 3);
            final android.util.ArrayMap arrayMap = new android.util.ArrayMap(this.mViews);
            if (arrayMap.size() == 0) {
                android.util.Log.w("UiTranslationController", "No views can be excuted for runForEachView.");
            }
            this.mActivity.runOnUiThread(new java.lang.Runnable() { // from class: android.view.translation.UiTranslationController$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    android.view.translation.UiTranslationController.lambda$runForEachView$7(android.util.ArrayMap.this, isLoggable, biConsumer);
                }
            });
        }
    }

    static /* synthetic */ void lambda$runForEachView$7(android.util.ArrayMap arrayMap, boolean z, java.util.function.BiConsumer biConsumer) {
        int size = arrayMap.size();
        for (int i = 0; i < size; i++) {
            android.view.View view = (android.view.View) ((java.lang.ref.WeakReference) arrayMap.valueAt(i)).get();
            if (z) {
                android.util.Log.d("UiTranslationController", "runForEachView for autofillId = " + (view != null ? view.getAutofillId() : " null"));
            }
            if (view == null || view.getViewTranslationCallback() == null) {
                if (z) {
                    android.util.Log.d("UiTranslationController", "View was gone or ViewTranslationCallback for autofillId = " + arrayMap.keyAt(i));
                }
            } else {
                biConsumer.accept(view, view.getViewTranslationCallback());
            }
        }
    }

    private android.view.translation.Translator createTranslatorIfNeeded(android.view.translation.TranslationSpec translationSpec, android.view.translation.TranslationSpec translationSpec2) {
        android.view.translation.TranslationManager translationManager = (android.view.translation.TranslationManager) this.mContext.getSystemService(android.view.translation.TranslationManager.class);
        if (translationManager == null) {
            android.util.Log.e("UiTranslationController", "Can not find TranslationManager when trying to create translator.");
            return null;
        }
        android.view.translation.Translator createTranslator = translationManager.createTranslator(new android.view.translation.TranslationContext.Builder(translationSpec, translationSpec2).setActivityId(new android.app.assist.ActivityId(this.mActivity.getTaskId(), this.mActivity.getShareableActivityToken())).build());
        if (createTranslator != null) {
            this.mTranslators.put(new android.util.Pair<>(translationSpec, translationSpec2), createTranslator);
        }
        return createTranslator;
    }

    private void destroyTranslators() {
        synchronized (this.mLock) {
            int size = this.mTranslators.size();
            for (int i = 0; i < size; i++) {
                this.mTranslators.valueAt(i).destroy();
            }
            this.mTranslators.clear();
        }
    }

    public static java.lang.String stateToString(int i) {
        switch (i) {
            case 0:
                return "UI_TRANSLATION_STARTED";
            case 1:
                return "UI_TRANSLATION_PAUSED";
            case 2:
                return "UI_TRANSLATION_RESUMED";
            case 3:
                return "UI_TRANSLATION_FINISHED";
            default:
                return "Unknown state (" + i + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END;
        }
    }

    private static java.lang.String sanitizedViewTranslationRequest(android.view.translation.ViewTranslationRequest viewTranslationRequest) {
        java.lang.String str;
        java.lang.StringBuilder sb = new java.lang.StringBuilder("ViewTranslationRequest:{values=[");
        java.util.Iterator<java.lang.String> it = viewTranslationRequest.getKeys().iterator();
        while (it.hasNext()) {
            android.view.translation.TranslationRequestValue value = viewTranslationRequest.getValue(it.next());
            java.lang.StringBuilder append = sb.append("{text=");
            if (value.getText() == null) {
                str = "null";
            } else {
                str = "string[" + value.getText().length() + "]}, ";
            }
            append.append(str);
        }
        return sb.toString();
    }

    private static java.lang.String sanitizedViewTranslationResponse(android.view.translation.ViewTranslationResponse viewTranslationResponse) {
        java.lang.String str;
        java.lang.String str2;
        java.lang.StringBuilder sb = new java.lang.StringBuilder("ViewTranslationResponse:{values=[");
        java.util.Iterator<java.lang.String> it = viewTranslationResponse.getKeys().iterator();
        while (it.hasNext()) {
            android.view.translation.TranslationResponseValue value = viewTranslationResponse.getValue(it.next());
            sb.append("{status=").append(value.getStatusCode()).append(", ");
            java.lang.StringBuilder append = sb.append("text=");
            java.lang.String str3 = "null";
            if (value.getText() == null) {
                str = "null";
            } else {
                str = "string[" + value.getText().length() + "], ";
            }
            append.append(str);
            android.os.Bundle bundle = (android.os.Bundle) value.getExtras().get(android.view.translation.TranslationResponseValue.EXTRA_DEFINITIONS);
            if (bundle != null) {
                sb.append("definitions={");
                for (java.lang.String str4 : bundle.keySet()) {
                    sb.append(str4).append(":[");
                    for (java.lang.CharSequence charSequence : bundle.getCharSequenceArray(str4)) {
                        if (charSequence == null) {
                            str2 = "null, ";
                        } else {
                            str2 = "string[" + charSequence.length() + "], ";
                        }
                        sb.append(str2);
                    }
                    sb.append("], ");
                }
                sb.append("}");
            }
            java.lang.StringBuilder append2 = sb.append("transliteration=");
            if (value.getTransliteration() != null) {
                str3 = "string[" + value.getTransliteration().length() + "]}, ";
            }
            append2.append(str3);
        }
        return sb.toString();
    }
}
