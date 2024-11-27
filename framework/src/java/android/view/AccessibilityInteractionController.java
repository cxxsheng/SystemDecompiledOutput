package android.view;

/* loaded from: classes4.dex */
public final class AccessibilityInteractionController {
    private static final boolean CONSIDER_REQUEST_PREPARERS = false;
    private static final boolean ENFORCE_NODE_TREE_CONSISTENT = false;
    private static final int FLAGS_AFFECTING_REPORTED_DATA = 896;
    private static final boolean IGNORE_REQUEST_PREPARERS = true;
    private static final java.lang.String LOG_TAG = "AccessibilityInteractionController";
    private static final long REQUEST_PREPARER_TIMEOUT_MS = 500;
    private final android.view.accessibility.AccessibilityManager mA11yManager;
    private int mActiveRequestPreparerId;
    private android.view.AccessibilityInteractionController.AddNodeInfosForViewId mAddNodeInfosForViewId;
    private final android.view.AccessibilityInteractionController.PrivateHandler mHandler;
    private java.util.List<android.view.AccessibilityInteractionController.MessageHolder> mMessagesWaitingForRequestPreparer;
    private final long mMyLooperThreadId;
    private final int mMyProcessId;
    private int mNumActiveRequestPreparers;
    private java.util.ArrayList<android.os.Message> mPendingFindNodeByIdMessages;
    private final android.view.AccessibilityInteractionController.AccessibilityNodePrefetcher mPrefetcher;
    private final android.view.ViewRootImpl mViewRootImpl;
    private final java.util.ArrayList<android.view.accessibility.AccessibilityNodeInfo> mTempAccessibilityNodeInfoList = new java.util.ArrayList<>();
    private final java.lang.Object mLock = new java.lang.Object();
    private final java.util.ArrayList<android.view.View> mTempArrayList = new java.util.ArrayList<>();
    private final android.graphics.Rect mTempRect = new android.graphics.Rect();
    private final android.graphics.RectF mTempRectF = new android.graphics.RectF();

    interface DequeNode {
        void addChildren(android.view.accessibility.AccessibilityNodeInfo accessibilityNodeInfo, android.view.AccessibilityInteractionController.PrefetchDeque prefetchDeque);

        android.view.accessibility.AccessibilityNodeInfo getA11yNodeInfo();
    }

    public AccessibilityInteractionController(android.view.ViewRootImpl viewRootImpl) {
        android.os.Looper looper = viewRootImpl.mHandler.getLooper();
        this.mMyLooperThreadId = looper.getThread().getId();
        this.mMyProcessId = android.os.Process.myPid();
        this.mHandler = new android.view.AccessibilityInteractionController.PrivateHandler(looper);
        this.mViewRootImpl = viewRootImpl;
        this.mPrefetcher = new android.view.AccessibilityInteractionController.AccessibilityNodePrefetcher();
        this.mA11yManager = (android.view.accessibility.AccessibilityManager) this.mViewRootImpl.mContext.getSystemService(android.view.accessibility.AccessibilityManager.class);
        this.mPendingFindNodeByIdMessages = new java.util.ArrayList<>();
    }

    private void scheduleMessage(android.os.Message message, int i, long j, boolean z) {
        if (z || !holdOffMessageIfNeeded(message, i, j)) {
            if (i == this.mMyProcessId && j == this.mMyLooperThreadId && this.mHandler.hasAccessibilityCallback(message)) {
                android.view.accessibility.AccessibilityInteractionClient.getInstanceForThread(j).setSameThreadMessage(message);
            } else if (!this.mHandler.hasAccessibilityCallback(message) && java.lang.Thread.currentThread().getId() == this.mMyLooperThreadId) {
                this.mHandler.handleMessage(message);
            } else {
                this.mHandler.sendMessage(message);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isShown(android.view.View view) {
        return view != null && view.getWindowVisibility() == 0 && view.isShown();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isVisibleToAccessibilityService(android.view.View view) {
        return view != null && (this.mA11yManager.isRequestFromAccessibilityTool() || !view.isAccessibilityDataSensitive());
    }

    public void findAccessibilityNodeInfoByAccessibilityIdClientThread(long j, android.graphics.Region region, int i, android.view.accessibility.IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback, int i2, int i3, long j2, android.view.MagnificationSpec magnificationSpec, float[] fArr, android.os.Bundle bundle) {
        android.os.Message obtainMessage = this.mHandler.obtainMessage();
        obtainMessage.what = 2;
        obtainMessage.arg1 = i2;
        com.android.internal.os.SomeArgs obtain = com.android.internal.os.SomeArgs.obtain();
        obtain.argi1 = android.view.accessibility.AccessibilityNodeInfo.getAccessibilityViewId(j);
        obtain.argi2 = android.view.accessibility.AccessibilityNodeInfo.getVirtualDescendantId(j);
        obtain.argi3 = i;
        obtain.arg1 = iAccessibilityInteractionConnectionCallback;
        obtain.arg2 = magnificationSpec;
        obtain.arg3 = region;
        obtain.arg4 = bundle;
        obtain.arg5 = fArr;
        obtainMessage.obj = obtain;
        synchronized (this.mLock) {
            this.mPendingFindNodeByIdMessages.add(obtainMessage);
            scheduleMessage(obtainMessage, i3, j2, false);
        }
    }

    private boolean holdOffMessageIfNeeded(android.os.Message message, int i, long j) {
        synchronized (this.mLock) {
            if (this.mNumActiveRequestPreparers != 0) {
                queueMessageToHandleOncePrepared(message, i, j);
                return true;
            }
            if (message.what != 2) {
                return false;
            }
            com.android.internal.os.SomeArgs someArgs = (com.android.internal.os.SomeArgs) message.obj;
            android.os.Bundle bundle = (android.os.Bundle) someArgs.arg4;
            if (bundle == null) {
                return false;
            }
            java.util.List<android.view.accessibility.AccessibilityRequestPreparer> requestPreparersForAccessibilityId = this.mA11yManager.getRequestPreparersForAccessibilityId(someArgs.argi1);
            if (requestPreparersForAccessibilityId == null) {
                return false;
            }
            java.lang.String string = bundle.getString(android.view.accessibility.AccessibilityNodeInfo.EXTRA_DATA_REQUESTED_KEY);
            if (string == null) {
                return false;
            }
            this.mNumActiveRequestPreparers = requestPreparersForAccessibilityId.size();
            for (int i2 = 0; i2 < requestPreparersForAccessibilityId.size(); i2++) {
                android.os.Message obtainMessage = this.mHandler.obtainMessage(7);
                com.android.internal.os.SomeArgs obtain = com.android.internal.os.SomeArgs.obtain();
                obtain.argi1 = someArgs.argi2 == Integer.MAX_VALUE ? -1 : someArgs.argi2;
                obtain.arg1 = requestPreparersForAccessibilityId.get(i2);
                obtain.arg2 = string;
                obtain.arg3 = bundle;
                android.os.Message obtainMessage2 = this.mHandler.obtainMessage(8);
                int i3 = this.mActiveRequestPreparerId + 1;
                this.mActiveRequestPreparerId = i3;
                obtainMessage2.arg1 = i3;
                obtain.arg4 = obtainMessage2;
                obtainMessage.obj = obtain;
                scheduleMessage(obtainMessage, i, j, true);
                this.mHandler.obtainMessage(9);
                this.mHandler.sendEmptyMessageDelayed(9, REQUEST_PREPARER_TIMEOUT_MS);
            }
            queueMessageToHandleOncePrepared(message, i, j);
            return true;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void prepareForExtraDataRequestUiThread(android.os.Message message) {
        com.android.internal.os.SomeArgs someArgs = (com.android.internal.os.SomeArgs) message.obj;
        ((android.view.accessibility.AccessibilityRequestPreparer) someArgs.arg1).onPrepareExtraData(someArgs.argi1, (java.lang.String) someArgs.arg2, (android.os.Bundle) someArgs.arg3, (android.os.Message) someArgs.arg4);
    }

    private void queueMessageToHandleOncePrepared(android.os.Message message, int i, long j) {
        if (this.mMessagesWaitingForRequestPreparer == null) {
            this.mMessagesWaitingForRequestPreparer = new java.util.ArrayList(1);
        }
        this.mMessagesWaitingForRequestPreparer.add(new android.view.AccessibilityInteractionController.MessageHolder(message, i, j));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void requestPreparerDoneUiThread(android.os.Message message) {
        synchronized (this.mLock) {
            if (message.arg1 != this.mActiveRequestPreparerId) {
                android.util.Slog.e(LOG_TAG, "Surprising AccessibilityRequestPreparer callback (likely late)");
                return;
            }
            this.mNumActiveRequestPreparers--;
            if (this.mNumActiveRequestPreparers <= 0) {
                this.mHandler.removeMessages(9);
                scheduleAllMessagesWaitingForRequestPreparerLocked();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void requestPreparerTimeoutUiThread() {
        synchronized (this.mLock) {
            android.util.Slog.e(LOG_TAG, "AccessibilityRequestPreparer timed out");
            scheduleAllMessagesWaitingForRequestPreparerLocked();
        }
    }

    private void scheduleAllMessagesWaitingForRequestPreparerLocked() {
        int size = this.mMessagesWaitingForRequestPreparer.size();
        int i = 0;
        while (i < size) {
            android.view.AccessibilityInteractionController.MessageHolder messageHolder = this.mMessagesWaitingForRequestPreparer.get(i);
            scheduleMessage(messageHolder.mMessage, messageHolder.mInterrogatingPid, messageHolder.mInterrogatingTid, i == 0);
            i++;
        }
        this.mMessagesWaitingForRequestPreparer.clear();
        this.mNumActiveRequestPreparers = 0;
        this.mActiveRequestPreparerId = -1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:31:0x009d  */
    /* JADX WARN: Removed duplicated region for block: B:40:0x00ba  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void findAccessibilityNodeInfoByAccessibilityIdUiThread(android.os.Message message) {
        int i;
        android.view.accessibility.AccessibilityNodeInfo accessibilityNodeInfo;
        java.lang.Throwable th;
        android.view.accessibility.AccessibilityNodeInfo accessibilityNodeInfo2;
        android.view.accessibility.AccessibilityNodeInfo accessibilityNodeInfo3;
        int i2;
        synchronized (this.mLock) {
            this.mPendingFindNodeByIdMessages.remove(message);
        }
        int i3 = message.arg1;
        com.android.internal.os.SomeArgs someArgs = (com.android.internal.os.SomeArgs) message.obj;
        int i4 = someArgs.argi1;
        int i5 = someArgs.argi2;
        int i6 = someArgs.argi3;
        android.view.accessibility.IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback = (android.view.accessibility.IAccessibilityInteractionConnectionCallback) someArgs.arg1;
        android.view.MagnificationSpec magnificationSpec = (android.view.MagnificationSpec) someArgs.arg2;
        android.graphics.Region region = (android.graphics.Region) someArgs.arg3;
        android.os.Bundle bundle = (android.os.Bundle) someArgs.arg4;
        float[] fArr = (float[]) someArgs.arg5;
        someArgs.recycle();
        boolean z = (i3 & 32) == 0;
        java.util.ArrayList<android.view.accessibility.AccessibilityNodeInfo> arrayList = this.mTempAccessibilityNodeInfoList;
        arrayList.clear();
        try {
            if (this.mViewRootImpl.mView != null) {
                try {
                    if (this.mViewRootImpl.mAttachInfo == null) {
                        i2 = i6;
                    } else {
                        setAccessibilityFetchFlags(i3);
                        android.view.View findViewByAccessibilityId = findViewByAccessibilityId(i4);
                        if (findViewByAccessibilityId != null) {
                            try {
                                if (isShown(findViewByAccessibilityId)) {
                                    android.view.accessibility.AccessibilityNodeInfo populateAccessibilityNodeInfoForView = populateAccessibilityNodeInfoForView(findViewByAccessibilityId, bundle, i5);
                                    try {
                                        this.mPrefetcher.mInterruptPrefetch = z;
                                        this.mPrefetcher.mFetchFlags = i3 & 63;
                                        if (!z) {
                                            arrayList.add(populateAccessibilityNodeInfoForView);
                                            this.mPrefetcher.prefetchAccessibilityNodeInfos(findViewByAccessibilityId, populateAccessibilityNodeInfoForView == null ? null : new android.view.accessibility.AccessibilityNodeInfo(populateAccessibilityNodeInfoForView), arrayList);
                                            resetAccessibilityFetchFlags();
                                        }
                                        accessibilityNodeInfo3 = populateAccessibilityNodeInfoForView;
                                        if (z) {
                                            android.view.accessibility.AccessibilityNodeInfo accessibilityNodeInfo4 = accessibilityNodeInfo3;
                                            updateInfosForViewportAndReturnFindNodeResult(arrayList, iAccessibilityInteractionConnectionCallback, i6, magnificationSpec, fArr, region);
                                            android.view.AccessibilityInteractionController.SatisfiedFindAccessibilityNodeByAccessibilityIdRequest satisfiedRequestInPrefetch = getSatisfiedRequestInPrefetch(accessibilityNodeInfo4 == null ? null : accessibilityNodeInfo4, arrayList, i3);
                                            if (satisfiedRequestInPrefetch != null) {
                                                returnFindNodeResult(satisfiedRequestInPrefetch);
                                                return;
                                            }
                                            return;
                                        }
                                        android.view.accessibility.AccessibilityNodeInfo accessibilityNodeInfo5 = accessibilityNodeInfo3;
                                        updateInfoForViewportAndReturnFindNodeResult(accessibilityNodeInfo5 == null ? null : new android.view.accessibility.AccessibilityNodeInfo(accessibilityNodeInfo5), iAccessibilityInteractionConnectionCallback, i6, magnificationSpec, fArr, region);
                                        this.mPrefetcher.prefetchAccessibilityNodeInfos(findViewByAccessibilityId, accessibilityNodeInfo5 == null ? null : new android.view.accessibility.AccessibilityNodeInfo(accessibilityNodeInfo5), arrayList);
                                        resetAccessibilityFetchFlags();
                                        updateInfosForViewPort(arrayList, magnificationSpec, fArr, region);
                                        android.view.AccessibilityInteractionController.SatisfiedFindAccessibilityNodeByAccessibilityIdRequest satisfiedRequestInPrefetch2 = getSatisfiedRequestInPrefetch(accessibilityNodeInfo5 == null ? null : accessibilityNodeInfo5, arrayList, i3);
                                        returnPrefetchResult(i6, arrayList, iAccessibilityInteractionConnectionCallback);
                                        if (satisfiedRequestInPrefetch2 != null) {
                                            returnFindNodeResult(satisfiedRequestInPrefetch2);
                                            return;
                                        }
                                        return;
                                    } catch (java.lang.Throwable th2) {
                                        th = th2;
                                        accessibilityNodeInfo2 = populateAccessibilityNodeInfoForView;
                                        i = i6;
                                        accessibilityNodeInfo = null;
                                        if (z) {
                                            updateInfoForViewportAndReturnFindNodeResult(accessibilityNodeInfo2 == null ? accessibilityNodeInfo : new android.view.accessibility.AccessibilityNodeInfo(accessibilityNodeInfo2), iAccessibilityInteractionConnectionCallback, i, magnificationSpec, fArr, region);
                                            throw th;
                                        }
                                        updateInfosForViewportAndReturnFindNodeResult(arrayList, iAccessibilityInteractionConnectionCallback, i, magnificationSpec, fArr, region);
                                        android.view.AccessibilityInteractionController.SatisfiedFindAccessibilityNodeByAccessibilityIdRequest satisfiedRequestInPrefetch3 = getSatisfiedRequestInPrefetch(accessibilityNodeInfo2 == null ? accessibilityNodeInfo : accessibilityNodeInfo2, arrayList, i3);
                                        if (satisfiedRequestInPrefetch3 != null) {
                                            returnFindNodeResult(satisfiedRequestInPrefetch3);
                                            return;
                                        }
                                        return;
                                    }
                                }
                            } catch (java.lang.Throwable th3) {
                                th = th3;
                                accessibilityNodeInfo2 = null;
                                i = i6;
                                accessibilityNodeInfo = null;
                            }
                        }
                        accessibilityNodeInfo3 = null;
                        if (z) {
                        }
                    }
                } catch (java.lang.Throwable th4) {
                    i = i6;
                    th = th4;
                    accessibilityNodeInfo2 = null;
                    accessibilityNodeInfo = null;
                }
            } else {
                i2 = i6;
            }
            if (z) {
                updateInfoForViewportAndReturnFindNodeResult(null, iAccessibilityInteractionConnectionCallback, i2, magnificationSpec, fArr, region);
                return;
            }
            updateInfosForViewportAndReturnFindNodeResult(arrayList, iAccessibilityInteractionConnectionCallback, i2, magnificationSpec, fArr, region);
            android.view.AccessibilityInteractionController.SatisfiedFindAccessibilityNodeByAccessibilityIdRequest satisfiedRequestInPrefetch4 = getSatisfiedRequestInPrefetch(null, arrayList, i3);
            if (satisfiedRequestInPrefetch4 != null) {
                returnFindNodeResult(satisfiedRequestInPrefetch4);
            }
        } catch (java.lang.Throwable th5) {
            i = i6;
            accessibilityNodeInfo = null;
            th = th5;
            accessibilityNodeInfo2 = null;
        }
    }

    private android.view.accessibility.AccessibilityNodeInfo populateAccessibilityNodeInfoForView(android.view.View view, android.os.Bundle bundle, int i) {
        android.view.accessibility.AccessibilityNodeProvider accessibilityNodeProvider = view.getAccessibilityNodeProvider();
        java.lang.String string = bundle == null ? null : bundle.getString(android.view.accessibility.AccessibilityNodeInfo.EXTRA_DATA_REQUESTED_KEY);
        if (accessibilityNodeProvider == null) {
            android.view.accessibility.AccessibilityNodeInfo createAccessibilityNodeInfo = view.createAccessibilityNodeInfo();
            if (createAccessibilityNodeInfo == null || string == null) {
                return createAccessibilityNodeInfo;
            }
            view.addExtraDataToAccessibilityNodeInfo(createAccessibilityNodeInfo, string, bundle);
            return createAccessibilityNodeInfo;
        }
        android.view.accessibility.AccessibilityNodeInfo createAccessibilityNodeInfo2 = accessibilityNodeProvider.createAccessibilityNodeInfo(i);
        if (createAccessibilityNodeInfo2 != null && string != null) {
            accessibilityNodeProvider.addExtraDataToAccessibilityNodeInfo(i, createAccessibilityNodeInfo2, string, bundle);
        }
        return createAccessibilityNodeInfo2;
    }

    public void findAccessibilityNodeInfosByViewIdClientThread(long j, java.lang.String str, android.graphics.Region region, int i, android.view.accessibility.IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback, int i2, int i3, long j2, android.view.MagnificationSpec magnificationSpec, float[] fArr) {
        android.os.Message obtainMessage = this.mHandler.obtainMessage();
        obtainMessage.what = 3;
        obtainMessage.arg1 = i2;
        obtainMessage.arg2 = android.view.accessibility.AccessibilityNodeInfo.getAccessibilityViewId(j);
        com.android.internal.os.SomeArgs obtain = com.android.internal.os.SomeArgs.obtain();
        obtain.argi1 = i;
        obtain.arg1 = iAccessibilityInteractionConnectionCallback;
        obtain.arg2 = magnificationSpec;
        obtain.arg3 = str;
        obtain.arg4 = region;
        obtain.arg5 = fArr;
        obtainMessage.obj = obtain;
        scheduleMessage(obtainMessage, i3, j2, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void findAccessibilityNodeInfosByViewIdUiThread(android.os.Message message) {
        int i = message.arg1;
        int i2 = message.arg2;
        com.android.internal.os.SomeArgs someArgs = (com.android.internal.os.SomeArgs) message.obj;
        int i3 = someArgs.argi1;
        android.view.accessibility.IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback = (android.view.accessibility.IAccessibilityInteractionConnectionCallback) someArgs.arg1;
        android.view.MagnificationSpec magnificationSpec = (android.view.MagnificationSpec) someArgs.arg2;
        java.lang.String str = (java.lang.String) someArgs.arg3;
        android.graphics.Region region = (android.graphics.Region) someArgs.arg4;
        float[] fArr = (float[]) someArgs.arg5;
        someArgs.recycle();
        java.util.ArrayList<android.view.accessibility.AccessibilityNodeInfo> arrayList = this.mTempAccessibilityNodeInfoList;
        arrayList.clear();
        try {
            if (this.mViewRootImpl.mView != null && this.mViewRootImpl.mAttachInfo != null && str != null) {
                setAccessibilityFetchFlags(i);
                android.view.View findViewByAccessibilityId = findViewByAccessibilityId(i2);
                if (findViewByAccessibilityId != null) {
                    int identifier = findViewByAccessibilityId.getContext().getResources().getIdentifier(str, null, null);
                    if (identifier <= 0) {
                        return;
                    }
                    if (this.mAddNodeInfosForViewId == null) {
                        this.mAddNodeInfosForViewId = new android.view.AccessibilityInteractionController.AddNodeInfosForViewId();
                    }
                    this.mAddNodeInfosForViewId.init(identifier, arrayList);
                    findViewByAccessibilityId.findViewByPredicate(this.mAddNodeInfosForViewId);
                    this.mAddNodeInfosForViewId.reset();
                }
            }
        } finally {
            resetAccessibilityFetchFlags();
            updateInfosForViewportAndReturnFindNodeResult(arrayList, iAccessibilityInteractionConnectionCallback, i3, magnificationSpec, fArr, region);
        }
    }

    public void findAccessibilityNodeInfosByTextClientThread(long j, java.lang.String str, android.graphics.Region region, int i, android.view.accessibility.IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback, int i2, int i3, long j2, android.view.MagnificationSpec magnificationSpec, float[] fArr) {
        android.os.Message obtainMessage = this.mHandler.obtainMessage();
        obtainMessage.what = 4;
        obtainMessage.arg1 = i2;
        com.android.internal.os.SomeArgs obtain = com.android.internal.os.SomeArgs.obtain();
        obtain.arg1 = str;
        obtain.arg2 = iAccessibilityInteractionConnectionCallback;
        obtain.arg3 = magnificationSpec;
        obtain.argi1 = android.view.accessibility.AccessibilityNodeInfo.getAccessibilityViewId(j);
        obtain.argi2 = android.view.accessibility.AccessibilityNodeInfo.getVirtualDescendantId(j);
        obtain.argi3 = i;
        obtain.arg4 = region;
        obtain.arg5 = fArr;
        obtainMessage.obj = obtain;
        scheduleMessage(obtainMessage, i3, j2, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void findAccessibilityNodeInfosByTextUiThread(android.os.Message message) {
        int i = message.arg1;
        com.android.internal.os.SomeArgs someArgs = (com.android.internal.os.SomeArgs) message.obj;
        java.lang.String str = (java.lang.String) someArgs.arg1;
        android.view.accessibility.IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback = (android.view.accessibility.IAccessibilityInteractionConnectionCallback) someArgs.arg2;
        android.view.MagnificationSpec magnificationSpec = (android.view.MagnificationSpec) someArgs.arg3;
        int i2 = someArgs.argi1;
        int i3 = someArgs.argi2;
        int i4 = someArgs.argi3;
        android.graphics.Region region = (android.graphics.Region) someArgs.arg4;
        float[] fArr = (float[]) someArgs.arg5;
        someArgs.recycle();
        java.util.List<android.view.accessibility.AccessibilityNodeInfo> list = null;
        try {
            if (this.mViewRootImpl.mView != null && this.mViewRootImpl.mAttachInfo != null) {
                setAccessibilityFetchFlags(i);
                android.view.View findViewByAccessibilityId = findViewByAccessibilityId(i2);
                if (findViewByAccessibilityId != null && isShown(findViewByAccessibilityId)) {
                    android.view.accessibility.AccessibilityNodeProvider accessibilityNodeProvider = findViewByAccessibilityId.getAccessibilityNodeProvider();
                    if (accessibilityNodeProvider != null) {
                        list = accessibilityNodeProvider.findAccessibilityNodeInfosByText(str, i3);
                    } else if (i3 == -1) {
                        java.util.ArrayList<android.view.View> arrayList = this.mTempArrayList;
                        arrayList.clear();
                        findViewByAccessibilityId.findViewsWithText(arrayList, str, 7);
                        if (!arrayList.isEmpty()) {
                            java.util.ArrayList<android.view.accessibility.AccessibilityNodeInfo> arrayList2 = this.mTempAccessibilityNodeInfoList;
                            try {
                                arrayList2.clear();
                                int size = arrayList.size();
                                for (int i5 = 0; i5 < size; i5++) {
                                    android.view.View view = arrayList.get(i5);
                                    if (isShown(view) && isVisibleToAccessibilityService(view)) {
                                        android.view.accessibility.AccessibilityNodeProvider accessibilityNodeProvider2 = view.getAccessibilityNodeProvider();
                                        if (accessibilityNodeProvider2 != null) {
                                            java.util.List<android.view.accessibility.AccessibilityNodeInfo> findAccessibilityNodeInfosByText = accessibilityNodeProvider2.findAccessibilityNodeInfosByText(str, -1);
                                            if (findAccessibilityNodeInfosByText != null) {
                                                arrayList2.addAll(findAccessibilityNodeInfosByText);
                                            }
                                        } else {
                                            arrayList2.add(view.createAccessibilityNodeInfo());
                                        }
                                    }
                                }
                                list = arrayList2;
                            } catch (java.lang.Throwable th) {
                                list = arrayList2;
                                th = th;
                                resetAccessibilityFetchFlags();
                                updateInfosForViewportAndReturnFindNodeResult(list, iAccessibilityInteractionConnectionCallback, i4, magnificationSpec, fArr, region);
                                throw th;
                            }
                        }
                    }
                }
                resetAccessibilityFetchFlags();
                updateInfosForViewportAndReturnFindNodeResult(list, iAccessibilityInteractionConnectionCallback, i4, magnificationSpec, fArr, region);
                return;
            }
            resetAccessibilityFetchFlags();
            updateInfosForViewportAndReturnFindNodeResult(null, iAccessibilityInteractionConnectionCallback, i4, magnificationSpec, fArr, region);
        } catch (java.lang.Throwable th2) {
            th = th2;
        }
    }

    public void takeScreenshotOfWindowClientThread(int i, android.window.ScreenCapture.ScreenCaptureListener screenCaptureListener, android.view.accessibility.IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback) {
        this.mHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new com.android.internal.util.function.QuadConsumer() { // from class: android.view.AccessibilityInteractionController$$ExternalSyntheticLambda1
            @Override // com.android.internal.util.function.QuadConsumer
            public final void accept(java.lang.Object obj, java.lang.Object obj2, java.lang.Object obj3, java.lang.Object obj4) {
                ((android.view.AccessibilityInteractionController) obj).takeScreenshotOfWindowUiThread(((java.lang.Integer) obj2).intValue(), (android.window.ScreenCapture.ScreenCaptureListener) obj3, (android.view.accessibility.IAccessibilityInteractionConnectionCallback) obj4);
            }
        }, this, java.lang.Integer.valueOf(i), screenCaptureListener, iAccessibilityInteractionConnectionCallback));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void takeScreenshotOfWindowUiThread(int i, android.window.ScreenCapture.ScreenCaptureListener screenCaptureListener, android.view.accessibility.IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback) {
        try {
            if ((this.mViewRootImpl.getWindowFlags() & 8192) != 0) {
                iAccessibilityInteractionConnectionCallback.sendTakeScreenshotOfWindowError(6, i);
            } else if (android.window.ScreenCapture.captureLayers(new android.window.ScreenCapture.LayerCaptureArgs.Builder(this.mViewRootImpl.getSurfaceControl()).setChildrenOnly(false).setUid(android.os.Process.myUid()).build(), screenCaptureListener) != 0) {
                iAccessibilityInteractionConnectionCallback.sendTakeScreenshotOfWindowError(1, i);
            }
        } catch (android.os.RemoteException e) {
        }
    }

    public void findFocusClientThread(long j, int i, android.graphics.Region region, int i2, android.view.accessibility.IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback, int i3, int i4, long j2, android.view.MagnificationSpec magnificationSpec, float[] fArr) {
        android.os.Message obtainMessage = this.mHandler.obtainMessage();
        obtainMessage.what = 5;
        obtainMessage.arg1 = i3;
        obtainMessage.arg2 = i;
        com.android.internal.os.SomeArgs obtain = com.android.internal.os.SomeArgs.obtain();
        obtain.argi1 = i2;
        obtain.argi2 = android.view.accessibility.AccessibilityNodeInfo.getAccessibilityViewId(j);
        obtain.argi3 = android.view.accessibility.AccessibilityNodeInfo.getVirtualDescendantId(j);
        obtain.arg1 = iAccessibilityInteractionConnectionCallback;
        obtain.arg2 = magnificationSpec;
        obtain.arg3 = region;
        obtain.arg4 = fArr;
        obtainMessage.obj = obtain;
        scheduleMessage(obtainMessage, i4, j2, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void findFocusUiThread(android.os.Message message) {
        android.view.accessibility.AccessibilityNodeInfo accessibilityNodeInfo;
        int i = message.arg1;
        int i2 = message.arg2;
        com.android.internal.os.SomeArgs someArgs = (com.android.internal.os.SomeArgs) message.obj;
        int i3 = someArgs.argi1;
        int i4 = someArgs.argi2;
        int i5 = someArgs.argi3;
        android.view.accessibility.IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback = (android.view.accessibility.IAccessibilityInteractionConnectionCallback) someArgs.arg1;
        android.view.MagnificationSpec magnificationSpec = (android.view.MagnificationSpec) someArgs.arg2;
        android.graphics.Region region = (android.graphics.Region) someArgs.arg3;
        float[] fArr = (float[]) someArgs.arg4;
        someArgs.recycle();
        android.view.accessibility.AccessibilityNodeInfo accessibilityNodeInfo2 = null;
        try {
            if (this.mViewRootImpl.mView != null && this.mViewRootImpl.mAttachInfo != null) {
                setAccessibilityFetchFlags(i);
                android.view.View findViewByAccessibilityId = findViewByAccessibilityId(i4);
                if (findViewByAccessibilityId != null && isShown(findViewByAccessibilityId)) {
                    switch (i2) {
                        case 1:
                            android.view.View findFocus = findViewByAccessibilityId.findFocus();
                            if (isShown(findFocus) && isVisibleToAccessibilityService(findFocus)) {
                                android.view.accessibility.AccessibilityNodeProvider accessibilityNodeProvider = findFocus.getAccessibilityNodeProvider();
                                if (accessibilityNodeProvider != null) {
                                    accessibilityNodeInfo2 = accessibilityNodeProvider.findFocus(i2);
                                }
                                if (accessibilityNodeInfo2 == null) {
                                    accessibilityNodeInfo2 = findFocus.createAccessibilityNodeInfo();
                                }
                                accessibilityNodeInfo = accessibilityNodeInfo2;
                                resetAccessibilityFetchFlags();
                                updateInfoForViewportAndReturnFindNodeResult(accessibilityNodeInfo, iAccessibilityInteractionConnectionCallback, i3, magnificationSpec, fArr, region);
                            }
                            break;
                        case 2:
                            android.view.View view = this.mViewRootImpl.mAccessibilityFocusedHost;
                            if (view != null && android.view.ViewRootImpl.isViewDescendantOf(view, findViewByAccessibilityId) && isShown(view) && isVisibleToAccessibilityService(view)) {
                                android.view.accessibility.AccessibilityNodeProvider accessibilityNodeProvider2 = view.getAccessibilityNodeProvider();
                                if (accessibilityNodeProvider2 != null) {
                                    android.view.accessibility.AccessibilityNodeInfo accessibilityNodeInfo3 = this.mViewRootImpl.mAccessibilityFocusedVirtualView;
                                    if (accessibilityNodeInfo3 != null) {
                                        accessibilityNodeInfo2 = accessibilityNodeProvider2.createAccessibilityNodeInfo(android.view.accessibility.AccessibilityNodeInfo.getVirtualDescendantId(accessibilityNodeInfo3.getSourceNodeId()));
                                    }
                                } else if (i5 == -1) {
                                    accessibilityNodeInfo2 = view.createAccessibilityNodeInfo();
                                }
                                accessibilityNodeInfo = accessibilityNodeInfo2;
                                resetAccessibilityFetchFlags();
                                updateInfoForViewportAndReturnFindNodeResult(accessibilityNodeInfo, iAccessibilityInteractionConnectionCallback, i3, magnificationSpec, fArr, region);
                            }
                            break;
                        default:
                            throw new java.lang.IllegalArgumentException("Unknown focus type: " + i2);
                    }
                }
                accessibilityNodeInfo = null;
                resetAccessibilityFetchFlags();
                updateInfoForViewportAndReturnFindNodeResult(accessibilityNodeInfo, iAccessibilityInteractionConnectionCallback, i3, magnificationSpec, fArr, region);
            }
        } finally {
            resetAccessibilityFetchFlags();
            updateInfoForViewportAndReturnFindNodeResult(null, iAccessibilityInteractionConnectionCallback, i3, magnificationSpec, fArr, region);
        }
    }

    public void focusSearchClientThread(long j, int i, android.graphics.Region region, int i2, android.view.accessibility.IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback, int i3, int i4, long j2, android.view.MagnificationSpec magnificationSpec, float[] fArr) {
        android.os.Message obtainMessage = this.mHandler.obtainMessage();
        obtainMessage.what = 6;
        obtainMessage.arg1 = i3;
        obtainMessage.arg2 = android.view.accessibility.AccessibilityNodeInfo.getAccessibilityViewId(j);
        com.android.internal.os.SomeArgs obtain = com.android.internal.os.SomeArgs.obtain();
        obtain.argi2 = i;
        obtain.argi3 = i2;
        obtain.arg1 = iAccessibilityInteractionConnectionCallback;
        obtain.arg2 = magnificationSpec;
        obtain.arg3 = region;
        obtain.arg4 = fArr;
        obtainMessage.obj = obtain;
        scheduleMessage(obtainMessage, i4, j2, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void focusSearchUiThread(android.os.Message message) {
        android.view.View focusSearch;
        int i = message.arg1;
        int i2 = message.arg2;
        com.android.internal.os.SomeArgs someArgs = (com.android.internal.os.SomeArgs) message.obj;
        int i3 = someArgs.argi2;
        int i4 = someArgs.argi3;
        android.view.accessibility.IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback = (android.view.accessibility.IAccessibilityInteractionConnectionCallback) someArgs.arg1;
        android.view.MagnificationSpec magnificationSpec = (android.view.MagnificationSpec) someArgs.arg2;
        android.graphics.Region region = (android.graphics.Region) someArgs.arg3;
        float[] fArr = (float[]) someArgs.arg4;
        someArgs.recycle();
        android.view.accessibility.AccessibilityNodeInfo accessibilityNodeInfo = null;
        try {
            if (this.mViewRootImpl.mView != null && this.mViewRootImpl.mAttachInfo != null) {
                setAccessibilityFetchFlags(i);
                android.view.View findViewByAccessibilityId = findViewByAccessibilityId(i2);
                if (findViewByAccessibilityId != null && isShown(findViewByAccessibilityId) && (focusSearch = findViewByAccessibilityId.focusSearch(i3)) != null) {
                    accessibilityNodeInfo = focusSearch.createAccessibilityNodeInfo();
                }
            }
        } finally {
            resetAccessibilityFetchFlags();
            updateInfoForViewportAndReturnFindNodeResult(null, iAccessibilityInteractionConnectionCallback, i4, magnificationSpec, fArr, region);
        }
    }

    public void performAccessibilityActionClientThread(long j, int i, android.os.Bundle bundle, int i2, android.view.accessibility.IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback, int i3, int i4, long j2) {
        android.os.Message obtainMessage = this.mHandler.obtainMessage();
        obtainMessage.what = 1;
        obtainMessage.arg1 = i3;
        obtainMessage.arg2 = android.view.accessibility.AccessibilityNodeInfo.getAccessibilityViewId(j);
        com.android.internal.os.SomeArgs obtain = com.android.internal.os.SomeArgs.obtain();
        obtain.argi1 = android.view.accessibility.AccessibilityNodeInfo.getVirtualDescendantId(j);
        obtain.argi2 = i;
        obtain.argi3 = i2;
        obtain.arg1 = iAccessibilityInteractionConnectionCallback;
        obtain.arg2 = bundle;
        obtainMessage.obj = obtain;
        scheduleMessage(obtainMessage, i4, j2, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void performAccessibilityActionUiThread(android.os.Message message) {
        boolean z;
        int i = message.arg1;
        int i2 = message.arg2;
        com.android.internal.os.SomeArgs someArgs = (com.android.internal.os.SomeArgs) message.obj;
        int i3 = someArgs.argi1;
        int i4 = someArgs.argi2;
        int i5 = someArgs.argi3;
        android.view.accessibility.IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback = (android.view.accessibility.IAccessibilityInteractionConnectionCallback) someArgs.arg1;
        android.os.Bundle bundle = (android.os.Bundle) someArgs.arg2;
        someArgs.recycle();
        boolean z2 = false;
        try {
            if (this.mViewRootImpl.mView != null && this.mViewRootImpl.mAttachInfo != null && !this.mViewRootImpl.mStopped && !this.mViewRootImpl.mPausedForTransition) {
                setAccessibilityFetchFlags(i);
                android.view.View findViewByAccessibilityId = findViewByAccessibilityId(i2);
                if (findViewByAccessibilityId != null && isShown(findViewByAccessibilityId) && isVisibleToAccessibilityService(findViewByAccessibilityId)) {
                    this.mA11yManager.notifyPerformingAction(i4);
                    if (i4 == 16908696) {
                        z = handleClickableSpanActionUiThread(findViewByAccessibilityId, i3, bundle);
                    } else {
                        android.view.accessibility.AccessibilityNodeProvider accessibilityNodeProvider = findViewByAccessibilityId.getAccessibilityNodeProvider();
                        if (accessibilityNodeProvider != null) {
                            z = accessibilityNodeProvider.performAction(i3, i4, bundle);
                        } else if (i3 != -1) {
                            z = false;
                        } else {
                            z = findViewByAccessibilityId.performAccessibilityAction(i4, bundle);
                        }
                    }
                    try {
                        this.mA11yManager.notifyPerformingAction(0);
                        z2 = z;
                    } catch (java.lang.Throwable th) {
                        boolean z3 = z;
                        th = th;
                        z2 = z3;
                        try {
                            resetAccessibilityFetchFlags();
                            iAccessibilityInteractionConnectionCallback.setPerformAccessibilityActionResult(z2, i5);
                        } catch (android.os.RemoteException e) {
                        }
                        throw th;
                    }
                }
                try {
                    resetAccessibilityFetchFlags();
                    iAccessibilityInteractionConnectionCallback.setPerformAccessibilityActionResult(z2, i5);
                    return;
                } catch (android.os.RemoteException e2) {
                    return;
                }
            }
            try {
                resetAccessibilityFetchFlags();
                iAccessibilityInteractionConnectionCallback.setPerformAccessibilityActionResult(false, i5);
            } catch (android.os.RemoteException e3) {
            }
        } catch (java.lang.Throwable th2) {
            th = th2;
        }
    }

    public void clearAccessibilityFocusClientThread() {
        android.os.Message obtainMessage = this.mHandler.obtainMessage();
        obtainMessage.what = 101;
        scheduleMessage(obtainMessage, 0, 0L, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void clearAccessibilityFocusUiThread() {
        if (this.mViewRootImpl.mView == null || this.mViewRootImpl.mAttachInfo == null) {
            return;
        }
        try {
            setAccessibilityFetchFlags(640);
            android.view.View rootView = getRootView();
            if (rootView != null && isShown(rootView)) {
                android.view.View view = this.mViewRootImpl.mAccessibilityFocusedHost;
                if (view != null && android.view.ViewRootImpl.isViewDescendantOf(view, rootView)) {
                    android.view.accessibility.AccessibilityNodeProvider accessibilityNodeProvider = view.getAccessibilityNodeProvider();
                    android.view.accessibility.AccessibilityNodeInfo accessibilityNodeInfo = this.mViewRootImpl.mAccessibilityFocusedVirtualView;
                    if (accessibilityNodeProvider != null && accessibilityNodeInfo != null) {
                        accessibilityNodeProvider.performAction(android.view.accessibility.AccessibilityNodeInfo.getVirtualDescendantId(accessibilityNodeInfo.getSourceNodeId()), android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction.ACTION_CLEAR_ACCESSIBILITY_FOCUS.getId(), null);
                    } else {
                        view.performAccessibilityAction(android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction.ACTION_CLEAR_ACCESSIBILITY_FOCUS.getId(), null);
                    }
                }
            }
        } finally {
            resetAccessibilityFetchFlags();
        }
    }

    public void notifyOutsideTouchClientThread() {
        android.os.Message obtainMessage = this.mHandler.obtainMessage();
        obtainMessage.what = 102;
        scheduleMessage(obtainMessage, 0, 0L, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyOutsideTouchUiThread() {
        android.view.View rootView;
        if (this.mViewRootImpl.mView != null && this.mViewRootImpl.mAttachInfo != null && !this.mViewRootImpl.mStopped && !this.mViewRootImpl.mPausedForTransition && (rootView = getRootView()) != null && isShown(rootView)) {
            long uptimeMillis = android.os.SystemClock.uptimeMillis();
            android.view.MotionEvent obtain = android.view.MotionEvent.obtain(uptimeMillis, uptimeMillis, 4, 0.0f, 0.0f, 0);
            obtain.setSource(4098);
            this.mViewRootImpl.dispatchInputEvent(obtain);
        }
    }

    private android.view.View findViewByAccessibilityId(int i) {
        if (i == 2147483646) {
            return getRootView();
        }
        return android.view.accessibility.AccessibilityNodeIdManager.getInstance().findView(i);
    }

    private android.view.View getRootView() {
        if (!isVisibleToAccessibilityService(this.mViewRootImpl.mView)) {
            return null;
        }
        return this.mViewRootImpl.mView;
    }

    private void setAccessibilityFetchFlags(int i) {
        this.mViewRootImpl.mAttachInfo.mAccessibilityFetchFlags = i;
        this.mA11yManager.setRequestFromAccessibilityTool((i & 512) != 0);
    }

    private void resetAccessibilityFetchFlags() {
        this.mViewRootImpl.mAttachInfo.mAccessibilityFetchFlags = 0;
        this.mA11yManager.setRequestFromAccessibilityTool(false);
    }

    private void adjustIsVisibleToUserIfNeeded(android.view.accessibility.AccessibilityNodeInfo accessibilityNodeInfo, android.graphics.Region region, android.view.MagnificationSpec magnificationSpec) {
        if (region == null || accessibilityNodeInfo == null) {
            return;
        }
        android.graphics.Rect rect = this.mTempRect;
        accessibilityNodeInfo.getBoundsInScreen(rect);
        if (magnificationSpec != null && !magnificationSpec.isNop()) {
            rect.offset((int) (-magnificationSpec.offsetX), (int) (-magnificationSpec.offsetY));
            rect.scale(1.0f / magnificationSpec.scale);
        }
        if (region.quickReject(rect) && !shouldBypassAdjustIsVisible()) {
            accessibilityNodeInfo.setVisibleToUser(false);
        }
    }

    private boolean shouldBypassAdjustIsVisible() {
        if (this.mViewRootImpl.mOrigWindowType == 2011) {
            return true;
        }
        return false;
    }

    private void applyHostWindowMatrixIfNeeded(android.view.accessibility.AccessibilityNodeInfo accessibilityNodeInfo) {
        if (accessibilityNodeInfo == null || shouldBypassApplyWindowMatrix()) {
            return;
        }
        android.graphics.Rect rect = this.mTempRect;
        android.graphics.RectF rectF = this.mTempRectF;
        android.graphics.Matrix matrix = this.mViewRootImpl.mAttachInfo.mWindowMatrixInEmbeddedHierarchy;
        accessibilityNodeInfo.getBoundsInScreen(rect);
        rectF.set(rect);
        matrix.mapRect(rectF);
        rect.set((int) rectF.left, (int) rectF.top, (int) rectF.right, (int) rectF.bottom);
        accessibilityNodeInfo.setBoundsInScreen(rect);
    }

    private boolean shouldBypassApplyWindowMatrix() {
        android.graphics.Matrix matrix = this.mViewRootImpl.mAttachInfo.mWindowMatrixInEmbeddedHierarchy;
        return matrix == null || matrix.isIdentity();
    }

    private void associateLeashedParentIfNeeded(android.view.accessibility.AccessibilityNodeInfo accessibilityNodeInfo) {
        if (accessibilityNodeInfo == null || shouldBypassAssociateLeashedParent() || this.mViewRootImpl.mView.getAccessibilityViewId() != android.view.accessibility.AccessibilityNodeInfo.getAccessibilityViewId(accessibilityNodeInfo.getSourceNodeId())) {
            return;
        }
        accessibilityNodeInfo.setLeashedParent(this.mViewRootImpl.mAttachInfo.mLeashedParentToken, this.mViewRootImpl.mAttachInfo.mLeashedParentAccessibilityViewId);
    }

    private boolean shouldBypassAssociateLeashedParent() {
        return this.mViewRootImpl.mAttachInfo.mLeashedParentToken == null && this.mViewRootImpl.mAttachInfo.mLeashedParentAccessibilityViewId == -1;
    }

    private boolean shouldApplyAppScaleAndMagnificationSpec(float f, android.view.MagnificationSpec magnificationSpec) {
        return (f == 1.0f && (magnificationSpec == null || magnificationSpec.isNop())) ? false : true;
    }

    private void updateInfosForViewPort(java.util.List<android.view.accessibility.AccessibilityNodeInfo> list, android.view.MagnificationSpec magnificationSpec, float[] fArr, android.graphics.Region region) {
        for (int i = 0; i < list.size(); i++) {
            updateInfoForViewPort(list.get(i), magnificationSpec, fArr, region);
        }
    }

    private void updateInfoForViewPort(android.view.accessibility.AccessibilityNodeInfo accessibilityNodeInfo, android.view.MagnificationSpec magnificationSpec, float[] fArr, android.graphics.Region region) {
        associateLeashedParentIfNeeded(accessibilityNodeInfo);
        applyHostWindowMatrixIfNeeded(accessibilityNodeInfo);
        transformBoundsWithScreenMatrix(accessibilityNodeInfo, fArr);
        adjustIsVisibleToUserIfNeeded(accessibilityNodeInfo, region, magnificationSpec);
    }

    private void transformBoundsWithScreenMatrix(android.view.accessibility.AccessibilityNodeInfo accessibilityNodeInfo, float[] fArr) {
        android.graphics.RectF[] rectFArr;
        if (accessibilityNodeInfo == null || fArr == null) {
            return;
        }
        android.graphics.Rect rect = this.mTempRect;
        android.graphics.RectF rectF = this.mTempRectF;
        accessibilityNodeInfo.getBoundsInScreen(rect);
        rectF.set(rect);
        android.graphics.Matrix matrix = new android.graphics.Matrix();
        matrix.setValues(fArr);
        float f = this.mViewRootImpl.mAttachInfo.mApplicationScale;
        if (f != 1.0f) {
            matrix.preScale(f, f);
        }
        if (this.mViewRootImpl.mAttachInfo.mWindowMatrixInEmbeddedHierarchy == null) {
            matrix.preTranslate(-this.mViewRootImpl.mAttachInfo.mWindowLeft, -this.mViewRootImpl.mAttachInfo.mWindowTop);
        }
        if (matrix.isIdentity()) {
            return;
        }
        matrix.mapRect(rectF);
        roundRectFToRect(rectF, rect);
        accessibilityNodeInfo.setBoundsInScreen(rect);
        if (accessibilityNodeInfo.hasExtras() && (rectFArr = (android.graphics.RectF[]) accessibilityNodeInfo.getExtras().getParcelableArray(android.view.accessibility.AccessibilityNodeInfo.EXTRA_DATA_TEXT_CHARACTER_LOCATION_KEY, android.graphics.RectF.class)) != null) {
            for (android.graphics.RectF rectF2 : rectFArr) {
                if (rectF2 != null) {
                    matrix.mapRect(rectF2);
                }
            }
        }
        applyTransformMatrixToBoundsInParentIfNeeded(accessibilityNodeInfo, matrix);
    }

    private void applyTransformMatrixToBoundsInParentIfNeeded(android.view.accessibility.AccessibilityNodeInfo accessibilityNodeInfo, android.graphics.Matrix matrix) {
        float[] fArr = new float[9];
        matrix.getValues(fArr);
        android.graphics.Matrix matrix2 = new android.graphics.Matrix();
        matrix2.setScale(fArr[0], fArr[0]);
        if (matrix2.isIdentity()) {
            return;
        }
        android.graphics.Rect rect = this.mTempRect;
        android.graphics.RectF rectF = this.mTempRectF;
        accessibilityNodeInfo.getBoundsInParent(rect);
        rectF.set(rect);
        matrix2.mapRect(rectF);
        roundRectFToRect(rectF, rect);
        accessibilityNodeInfo.setBoundsInParent(rect);
    }

    private void updateInfosForViewportAndReturnFindNodeResult(java.util.List<android.view.accessibility.AccessibilityNodeInfo> list, android.view.accessibility.IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback, int i, android.view.MagnificationSpec magnificationSpec, float[] fArr, android.graphics.Region region) {
        if (list != null) {
            updateInfosForViewPort(list, magnificationSpec, fArr, region);
        }
        returnFindNodesResult(list, iAccessibilityInteractionConnectionCallback, i);
    }

    private void returnFindNodeResult(android.view.accessibility.AccessibilityNodeInfo accessibilityNodeInfo, android.view.accessibility.IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback, int i) {
        try {
            iAccessibilityInteractionConnectionCallback.setFindAccessibilityNodeInfoResult(accessibilityNodeInfo, i);
        } catch (android.os.RemoteException e) {
        }
    }

    private void returnFindNodeResult(android.view.AccessibilityInteractionController.SatisfiedFindAccessibilityNodeByAccessibilityIdRequest satisfiedFindAccessibilityNodeByAccessibilityIdRequest) {
        try {
            satisfiedFindAccessibilityNodeByAccessibilityIdRequest.mSatisfiedRequestCallback.setFindAccessibilityNodeInfoResult(satisfiedFindAccessibilityNodeByAccessibilityIdRequest.mSatisfiedRequestNode, satisfiedFindAccessibilityNodeByAccessibilityIdRequest.mSatisfiedRequestInteractionId);
        } catch (android.os.RemoteException e) {
        }
    }

    private void returnFindNodesResult(java.util.List<android.view.accessibility.AccessibilityNodeInfo> list, android.view.accessibility.IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback, int i) {
        try {
            iAccessibilityInteractionConnectionCallback.setFindAccessibilityNodeInfosResult(list, i);
            if (list != null) {
                list.clear();
            }
        } catch (android.os.RemoteException e) {
        }
    }

    private android.view.AccessibilityInteractionController.SatisfiedFindAccessibilityNodeByAccessibilityIdRequest getSatisfiedRequestInPrefetch(android.view.accessibility.AccessibilityNodeInfo accessibilityNodeInfo, java.util.List<android.view.accessibility.AccessibilityNodeInfo> list, int i) {
        android.view.AccessibilityInteractionController.SatisfiedFindAccessibilityNodeByAccessibilityIdRequest satisfiedFindAccessibilityNodeByAccessibilityIdRequest;
        synchronized (this.mLock) {
            int i2 = 0;
            while (true) {
                if (i2 >= this.mPendingFindNodeByIdMessages.size()) {
                    satisfiedFindAccessibilityNodeByAccessibilityIdRequest = null;
                    break;
                }
                android.os.Message message = this.mPendingFindNodeByIdMessages.get(i2);
                if ((message.arg1 & 896) == (i & 896)) {
                    com.android.internal.os.SomeArgs someArgs = (com.android.internal.os.SomeArgs) message.obj;
                    android.view.accessibility.AccessibilityNodeInfo nodeWithIdFromList = nodeWithIdFromList(accessibilityNodeInfo, list, android.view.accessibility.AccessibilityNodeInfo.makeNodeId(someArgs.argi1, someArgs.argi2));
                    if (nodeWithIdFromList != null) {
                        this.mHandler.removeMessages(2, message.obj);
                        satisfiedFindAccessibilityNodeByAccessibilityIdRequest = new android.view.AccessibilityInteractionController.SatisfiedFindAccessibilityNodeByAccessibilityIdRequest(nodeWithIdFromList, (android.view.accessibility.IAccessibilityInteractionConnectionCallback) someArgs.arg1, someArgs.argi3);
                        someArgs.recycle();
                        break;
                    }
                }
                i2++;
            }
            this.mPendingFindNodeByIdMessages.clear();
            if (satisfiedFindAccessibilityNodeByAccessibilityIdRequest != null && satisfiedFindAccessibilityNodeByAccessibilityIdRequest.mSatisfiedRequestNode != accessibilityNodeInfo) {
                list.remove(satisfiedFindAccessibilityNodeByAccessibilityIdRequest.mSatisfiedRequestNode);
            }
        }
        return satisfiedFindAccessibilityNodeByAccessibilityIdRequest;
    }

    private android.view.accessibility.AccessibilityNodeInfo nodeWithIdFromList(android.view.accessibility.AccessibilityNodeInfo accessibilityNodeInfo, java.util.List<android.view.accessibility.AccessibilityNodeInfo> list, long j) {
        if (accessibilityNodeInfo != null && accessibilityNodeInfo.getSourceNodeId() == j) {
            return accessibilityNodeInfo;
        }
        for (int i = 0; i < list.size(); i++) {
            android.view.accessibility.AccessibilityNodeInfo accessibilityNodeInfo2 = list.get(i);
            if (accessibilityNodeInfo2.getSourceNodeId() == j) {
                return accessibilityNodeInfo2;
            }
        }
        return null;
    }

    private void returnPrefetchResult(int i, java.util.List<android.view.accessibility.AccessibilityNodeInfo> list, android.view.accessibility.IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback) {
        if (list.size() > 0) {
            try {
                iAccessibilityInteractionConnectionCallback.setPrefetchAccessibilityNodeInfoResult(list, i);
            } catch (android.os.RemoteException e) {
            }
        }
    }

    private void updateInfoForViewportAndReturnFindNodeResult(android.view.accessibility.AccessibilityNodeInfo accessibilityNodeInfo, android.view.accessibility.IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback, int i, android.view.MagnificationSpec magnificationSpec, float[] fArr, android.graphics.Region region) {
        updateInfoForViewPort(accessibilityNodeInfo, magnificationSpec, fArr, region);
        returnFindNodeResult(accessibilityNodeInfo, iAccessibilityInteractionConnectionCallback, i);
    }

    private boolean handleClickableSpanActionUiThread(android.view.View view, int i, android.os.Bundle bundle) {
        android.view.accessibility.AccessibilityNodeInfo accessibilityNodeInfo;
        android.text.style.ClickableSpan findClickableSpan;
        android.os.Parcelable parcelable = bundle.getParcelable(android.view.accessibility.AccessibilityNodeInfo.ACTION_ARGUMENT_ACCESSIBLE_CLICKABLE_SPAN);
        if (!(parcelable instanceof android.text.style.AccessibilityClickableSpan)) {
            return false;
        }
        android.view.accessibility.AccessibilityNodeProvider accessibilityNodeProvider = view.getAccessibilityNodeProvider();
        if (accessibilityNodeProvider != null) {
            accessibilityNodeInfo = accessibilityNodeProvider.createAccessibilityNodeInfo(i);
        } else if (i != -1) {
            accessibilityNodeInfo = null;
        } else {
            accessibilityNodeInfo = view.createAccessibilityNodeInfo();
        }
        if (accessibilityNodeInfo == null || (findClickableSpan = ((android.text.style.AccessibilityClickableSpan) parcelable).findClickableSpan(accessibilityNodeInfo.getOriginalText())) == null) {
            return false;
        }
        findClickableSpan.onClick(view);
        return true;
    }

    private static void roundRectFToRect(android.graphics.RectF rectF, android.graphics.Rect rect) {
        rect.set((int) (rectF.left + 0.5d), (int) (rectF.top + 0.5d), (int) (rectF.right + 0.5d), (int) (rectF.bottom + 0.5d));
    }

    private class AccessibilityNodePrefetcher {
        private int mFetchFlags;
        private boolean mInterruptPrefetch;
        private final java.util.ArrayList<android.view.View> mTempViewList;

        private AccessibilityNodePrefetcher() {
            this.mTempViewList = new java.util.ArrayList<>();
        }

        public void prefetchAccessibilityNodeInfos(android.view.View view, android.view.accessibility.AccessibilityNodeInfo accessibilityNodeInfo, java.util.List<android.view.accessibility.AccessibilityNodeInfo> list) {
            if (accessibilityNodeInfo == null) {
                return;
            }
            android.view.accessibility.AccessibilityNodeProvider accessibilityNodeProvider = view.getAccessibilityNodeProvider();
            boolean isFlagSet = isFlagSet(1);
            if (accessibilityNodeProvider == null) {
                if (isFlagSet) {
                    prefetchPredecessorsOfRealNode(view, list);
                }
                if (isFlagSet(2)) {
                    prefetchSiblingsOfRealNode(view, list, isFlagSet);
                }
                if (isFlagSet(4)) {
                    prefetchDescendantsOfRealNode(view, list);
                }
            } else {
                if (isFlagSet) {
                    prefetchPredecessorsOfVirtualNode(accessibilityNodeInfo, view, accessibilityNodeProvider, list);
                }
                if (isFlagSet(2)) {
                    prefetchSiblingsOfVirtualNode(accessibilityNodeInfo, view, accessibilityNodeProvider, list, isFlagSet);
                }
                if (isFlagSet(4)) {
                    prefetchDescendantsOfVirtualNode(accessibilityNodeInfo, accessibilityNodeProvider, list);
                }
            }
            if ((!isFlagSet(8) && !isFlagSet(16)) || shouldStopPrefetching(list)) {
                return;
            }
            android.view.AccessibilityInteractionController.PrefetchDeque prefetchDeque = android.view.AccessibilityInteractionController.this.new PrefetchDeque(this.mFetchFlags & 28, list);
            addChildrenOfRoot(view, accessibilityNodeInfo, accessibilityNodeProvider, prefetchDeque);
            prefetchDeque.performTraversalAndPrefetch();
        }

        private void addChildrenOfRoot(android.view.View view, android.view.accessibility.AccessibilityNodeInfo accessibilityNodeInfo, android.view.accessibility.AccessibilityNodeProvider accessibilityNodeProvider, android.view.AccessibilityInteractionController.PrefetchDeque prefetchDeque) {
            android.view.AccessibilityInteractionController.DequeNode virtualNode;
            if (accessibilityNodeProvider == null) {
                virtualNode = android.view.AccessibilityInteractionController.this.new ViewNode(view);
            } else {
                virtualNode = android.view.AccessibilityInteractionController.this.new VirtualNode(-1L, accessibilityNodeProvider);
            }
            virtualNode.addChildren(accessibilityNodeInfo, prefetchDeque);
        }

        private boolean isFlagSet(int i) {
            return (i & this.mFetchFlags) != 0;
        }

        public boolean shouldStopPrefetching(java.util.List list) {
            return (android.view.AccessibilityInteractionController.this.mHandler.hasUserInteractiveMessagesWaiting() && this.mInterruptPrefetch) || list.size() >= 50;
        }

        private void enforceNodeTreeConsistent(android.view.accessibility.AccessibilityNodeInfo accessibilityNodeInfo, java.util.List<android.view.accessibility.AccessibilityNodeInfo> list) {
            android.util.LongSparseArray longSparseArray = new android.util.LongSparseArray();
            int size = list.size();
            for (int i = 0; i < size; i++) {
                android.view.accessibility.AccessibilityNodeInfo accessibilityNodeInfo2 = list.get(i);
                longSparseArray.put(accessibilityNodeInfo2.getSourceNodeId(), accessibilityNodeInfo2);
            }
            android.view.accessibility.AccessibilityNodeInfo accessibilityNodeInfo3 = accessibilityNodeInfo;
            while (accessibilityNodeInfo != null) {
                accessibilityNodeInfo3 = accessibilityNodeInfo;
                accessibilityNodeInfo = (android.view.accessibility.AccessibilityNodeInfo) longSparseArray.get(accessibilityNodeInfo.getParentNodeId());
            }
            java.util.HashSet hashSet = new java.util.HashSet();
            java.util.LinkedList linkedList = new java.util.LinkedList();
            linkedList.add(accessibilityNodeInfo3);
            android.view.accessibility.AccessibilityNodeInfo accessibilityNodeInfo4 = null;
            android.view.accessibility.AccessibilityNodeInfo accessibilityNodeInfo5 = null;
            while (!linkedList.isEmpty()) {
                android.view.accessibility.AccessibilityNodeInfo accessibilityNodeInfo6 = (android.view.accessibility.AccessibilityNodeInfo) linkedList.poll();
                if (!hashSet.add(accessibilityNodeInfo6)) {
                    throw new java.lang.IllegalStateException("Duplicate node: " + accessibilityNodeInfo6 + " in window:" + android.view.AccessibilityInteractionController.this.mViewRootImpl.mAttachInfo.mAccessibilityWindowId);
                }
                if (accessibilityNodeInfo6.isAccessibilityFocused()) {
                    if (accessibilityNodeInfo4 != null) {
                        throw new java.lang.IllegalStateException("Duplicate accessibility focus:" + accessibilityNodeInfo6 + " in window:" + android.view.AccessibilityInteractionController.this.mViewRootImpl.mAttachInfo.mAccessibilityWindowId);
                    }
                    accessibilityNodeInfo4 = accessibilityNodeInfo6;
                }
                if (accessibilityNodeInfo6.isFocused()) {
                    if (accessibilityNodeInfo5 != null) {
                        throw new java.lang.IllegalStateException("Duplicate input focus: " + accessibilityNodeInfo6 + " in window:" + android.view.AccessibilityInteractionController.this.mViewRootImpl.mAttachInfo.mAccessibilityWindowId);
                    }
                    accessibilityNodeInfo5 = accessibilityNodeInfo6;
                }
                int childCount = accessibilityNodeInfo6.getChildCount();
                for (int i2 = 0; i2 < childCount; i2++) {
                    android.view.accessibility.AccessibilityNodeInfo accessibilityNodeInfo7 = (android.view.accessibility.AccessibilityNodeInfo) longSparseArray.get(accessibilityNodeInfo6.getChildId(i2));
                    if (accessibilityNodeInfo7 != null) {
                        linkedList.add(accessibilityNodeInfo7);
                    }
                }
            }
            for (int size2 = longSparseArray.size() - 1; size2 >= 0; size2--) {
                android.view.accessibility.AccessibilityNodeInfo accessibilityNodeInfo8 = (android.view.accessibility.AccessibilityNodeInfo) longSparseArray.valueAt(size2);
                if (!hashSet.contains(accessibilityNodeInfo8)) {
                    throw new java.lang.IllegalStateException("Disconnected node: " + accessibilityNodeInfo8);
                }
            }
        }

        private void prefetchPredecessorsOfRealNode(android.view.View view, java.util.List<android.view.accessibility.AccessibilityNodeInfo> list) {
            if (shouldStopPrefetching(list)) {
                return;
            }
            for (android.view.ViewParent parentForAccessibility = view.getParentForAccessibility(); (parentForAccessibility instanceof android.view.View) && !shouldStopPrefetching(list); parentForAccessibility = parentForAccessibility.getParentForAccessibility()) {
                android.view.accessibility.AccessibilityNodeInfo createAccessibilityNodeInfo = ((android.view.View) parentForAccessibility).createAccessibilityNodeInfo();
                if (createAccessibilityNodeInfo != null) {
                    list.add(createAccessibilityNodeInfo);
                }
            }
        }

        private void prefetchSiblingsOfRealNode(android.view.View view, java.util.List<android.view.accessibility.AccessibilityNodeInfo> list, boolean z) {
            android.view.accessibility.AccessibilityNodeInfo createAccessibilityNodeInfo;
            if (shouldStopPrefetching(list)) {
                return;
            }
            android.view.ViewParent parentForAccessibility = view.getParentForAccessibility();
            if (parentForAccessibility instanceof android.view.ViewGroup) {
                android.view.ViewGroup viewGroup = (android.view.ViewGroup) parentForAccessibility;
                java.util.ArrayList<android.view.View> arrayList = this.mTempViewList;
                arrayList.clear();
                if (!z) {
                    try {
                        android.view.accessibility.AccessibilityNodeInfo createAccessibilityNodeInfo2 = ((android.view.ViewGroup) parentForAccessibility).createAccessibilityNodeInfo();
                        if (createAccessibilityNodeInfo2 != null) {
                            list.add(createAccessibilityNodeInfo2);
                        }
                    } finally {
                        arrayList.clear();
                    }
                }
                viewGroup.addChildrenForAccessibility(arrayList);
                int size = arrayList.size();
                for (int i = 0; i < size; i++) {
                    if (shouldStopPrefetching(list)) {
                        return;
                    }
                    android.view.View view2 = arrayList.get(i);
                    if (view2.getAccessibilityViewId() != view.getAccessibilityViewId() && android.view.AccessibilityInteractionController.this.isShown(view2)) {
                        android.view.accessibility.AccessibilityNodeProvider accessibilityNodeProvider = view2.getAccessibilityNodeProvider();
                        if (accessibilityNodeProvider == null) {
                            createAccessibilityNodeInfo = view2.createAccessibilityNodeInfo();
                        } else {
                            createAccessibilityNodeInfo = accessibilityNodeProvider.createAccessibilityNodeInfo(-1);
                        }
                        if (createAccessibilityNodeInfo != null) {
                            list.add(createAccessibilityNodeInfo);
                        }
                    }
                }
            }
        }

        private void prefetchDescendantsOfRealNode(android.view.View view, java.util.List<android.view.accessibility.AccessibilityNodeInfo> list) {
            if (shouldStopPrefetching(list) || !(view instanceof android.view.ViewGroup)) {
                return;
            }
            java.util.LinkedHashMap linkedHashMap = new java.util.LinkedHashMap();
            java.util.ArrayList<android.view.View> arrayList = this.mTempViewList;
            arrayList.clear();
            try {
                view.addChildrenForAccessibility(arrayList);
                int size = arrayList.size();
                for (int i = 0; i < size; i++) {
                    if (shouldStopPrefetching(list)) {
                        return;
                    }
                    android.view.View view2 = arrayList.get(i);
                    if (android.view.AccessibilityInteractionController.this.isShown(view2)) {
                        android.view.accessibility.AccessibilityNodeProvider accessibilityNodeProvider = view2.getAccessibilityNodeProvider();
                        if (accessibilityNodeProvider == null) {
                            android.view.accessibility.AccessibilityNodeInfo createAccessibilityNodeInfo = view2.createAccessibilityNodeInfo();
                            if (createAccessibilityNodeInfo != null) {
                                list.add(createAccessibilityNodeInfo);
                                linkedHashMap.put(view2, null);
                            }
                        } else {
                            android.view.accessibility.AccessibilityNodeInfo createAccessibilityNodeInfo2 = accessibilityNodeProvider.createAccessibilityNodeInfo(-1);
                            if (createAccessibilityNodeInfo2 != null) {
                                list.add(createAccessibilityNodeInfo2);
                                linkedHashMap.put(view2, createAccessibilityNodeInfo2);
                            }
                        }
                    }
                }
                arrayList.clear();
                if (!shouldStopPrefetching(list)) {
                    for (java.util.Map.Entry entry : linkedHashMap.entrySet()) {
                        android.view.View view3 = (android.view.View) entry.getKey();
                        android.view.accessibility.AccessibilityNodeInfo accessibilityNodeInfo = (android.view.accessibility.AccessibilityNodeInfo) entry.getValue();
                        if (accessibilityNodeInfo == null) {
                            prefetchDescendantsOfRealNode(view3, list);
                        } else {
                            prefetchDescendantsOfVirtualNode(accessibilityNodeInfo, view3.getAccessibilityNodeProvider(), list);
                        }
                    }
                }
            } finally {
                arrayList.clear();
            }
        }

        private void prefetchPredecessorsOfVirtualNode(android.view.accessibility.AccessibilityNodeInfo accessibilityNodeInfo, android.view.View view, android.view.accessibility.AccessibilityNodeProvider accessibilityNodeProvider, java.util.List<android.view.accessibility.AccessibilityNodeInfo> list) {
            int size = list.size();
            long parentNodeId = accessibilityNodeInfo.getParentNodeId();
            int accessibilityViewId = android.view.accessibility.AccessibilityNodeInfo.getAccessibilityViewId(parentNodeId);
            while (accessibilityViewId != Integer.MAX_VALUE && !shouldStopPrefetching(list)) {
                int virtualDescendantId = android.view.accessibility.AccessibilityNodeInfo.getVirtualDescendantId(parentNodeId);
                if (virtualDescendantId != -1 || accessibilityViewId == view.getAccessibilityViewId()) {
                    android.view.accessibility.AccessibilityNodeInfo createAccessibilityNodeInfo = accessibilityNodeProvider.createAccessibilityNodeInfo(virtualDescendantId);
                    if (createAccessibilityNodeInfo == null) {
                        for (int size2 = list.size() - 1; size2 >= size; size2--) {
                            list.remove(size2);
                        }
                        return;
                    }
                    list.add(createAccessibilityNodeInfo);
                    parentNodeId = createAccessibilityNodeInfo.getParentNodeId();
                    accessibilityViewId = android.view.accessibility.AccessibilityNodeInfo.getAccessibilityViewId(parentNodeId);
                } else {
                    prefetchPredecessorsOfRealNode(view, list);
                    return;
                }
            }
        }

        private void prefetchSiblingsOfVirtualNode(android.view.accessibility.AccessibilityNodeInfo accessibilityNodeInfo, android.view.View view, android.view.accessibility.AccessibilityNodeProvider accessibilityNodeProvider, java.util.List<android.view.accessibility.AccessibilityNodeInfo> list, boolean z) {
            android.view.accessibility.AccessibilityNodeInfo createAccessibilityNodeInfo;
            long parentNodeId = accessibilityNodeInfo.getParentNodeId();
            int accessibilityViewId = android.view.accessibility.AccessibilityNodeInfo.getAccessibilityViewId(parentNodeId);
            int virtualDescendantId = android.view.accessibility.AccessibilityNodeInfo.getVirtualDescendantId(parentNodeId);
            if (virtualDescendantId != -1 || accessibilityViewId == view.getAccessibilityViewId()) {
                android.view.accessibility.AccessibilityNodeInfo createAccessibilityNodeInfo2 = accessibilityNodeProvider.createAccessibilityNodeInfo(virtualDescendantId);
                if (createAccessibilityNodeInfo2 != null) {
                    if (!z) {
                        list.add(createAccessibilityNodeInfo2);
                    }
                    int childCount = createAccessibilityNodeInfo2.getChildCount();
                    for (int i = 0; i < childCount && !shouldStopPrefetching(list); i++) {
                        long childId = createAccessibilityNodeInfo2.getChildId(i);
                        if (childId != accessibilityNodeInfo.getSourceNodeId() && (createAccessibilityNodeInfo = accessibilityNodeProvider.createAccessibilityNodeInfo(android.view.accessibility.AccessibilityNodeInfo.getVirtualDescendantId(childId))) != null) {
                            list.add(createAccessibilityNodeInfo);
                        }
                    }
                    return;
                }
                return;
            }
            prefetchSiblingsOfRealNode(view, list, z);
        }

        private void prefetchDescendantsOfVirtualNode(android.view.accessibility.AccessibilityNodeInfo accessibilityNodeInfo, android.view.accessibility.AccessibilityNodeProvider accessibilityNodeProvider, java.util.List<android.view.accessibility.AccessibilityNodeInfo> list) {
            int size = list.size();
            int childCount = accessibilityNodeInfo.getChildCount();
            for (int i = 0; i < childCount; i++) {
                if (shouldStopPrefetching(list)) {
                    return;
                }
                android.view.accessibility.AccessibilityNodeInfo createAccessibilityNodeInfo = accessibilityNodeProvider.createAccessibilityNodeInfo(android.view.accessibility.AccessibilityNodeInfo.getVirtualDescendantId(accessibilityNodeInfo.getChildId(i)));
                if (createAccessibilityNodeInfo != null) {
                    list.add(createAccessibilityNodeInfo);
                }
            }
            if (!shouldStopPrefetching(list)) {
                int size2 = list.size() - size;
                for (int i2 = 0; i2 < size2; i2++) {
                    prefetchDescendantsOfVirtualNode(list.get(size + i2), accessibilityNodeProvider, list);
                }
            }
        }
    }

    private class PrivateHandler extends android.os.Handler {
        private static final int FIRST_NO_ACCESSIBILITY_CALLBACK_MSG = 100;
        private static final int MSG_APP_PREPARATION_FINISHED = 8;
        private static final int MSG_APP_PREPARATION_TIMEOUT = 9;
        private static final int MSG_CLEAR_ACCESSIBILITY_FOCUS = 101;
        private static final int MSG_FIND_ACCESSIBILITY_NODE_INFOS_BY_VIEW_ID = 3;
        private static final int MSG_FIND_ACCESSIBILITY_NODE_INFO_BY_ACCESSIBILITY_ID = 2;
        private static final int MSG_FIND_ACCESSIBILITY_NODE_INFO_BY_TEXT = 4;
        private static final int MSG_FIND_FOCUS = 5;
        private static final int MSG_FOCUS_SEARCH = 6;
        private static final int MSG_NOTIFY_OUTSIDE_TOUCH = 102;
        private static final int MSG_PERFORM_ACCESSIBILITY_ACTION = 1;
        private static final int MSG_PREPARE_FOR_EXTRA_DATA_REQUEST = 7;

        public PrivateHandler(android.os.Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public java.lang.String getMessageName(android.os.Message message) {
            int i = message.what;
            switch (i) {
                case 1:
                    return "MSG_PERFORM_ACCESSIBILITY_ACTION";
                case 2:
                    return "MSG_FIND_ACCESSIBILITY_NODE_INFO_BY_ACCESSIBILITY_ID";
                case 3:
                    return "MSG_FIND_ACCESSIBILITY_NODE_INFOS_BY_VIEW_ID";
                case 4:
                    return "MSG_FIND_ACCESSIBILITY_NODE_INFO_BY_TEXT";
                case 5:
                    return "MSG_FIND_FOCUS";
                case 6:
                    return "MSG_FOCUS_SEARCH";
                case 7:
                    return "MSG_PREPARE_FOR_EXTRA_DATA_REQUEST";
                case 8:
                    return "MSG_APP_PREPARATION_FINISHED";
                case 9:
                    return "MSG_APP_PREPARATION_TIMEOUT";
                case 101:
                    return "MSG_CLEAR_ACCESSIBILITY_FOCUS";
                case 102:
                    return "MSG_NOTIFY_OUTSIDE_TOUCH";
                default:
                    throw new java.lang.IllegalArgumentException("Unknown message type: " + i);
            }
        }

        @Override // android.os.Handler
        public void handleMessage(android.os.Message message) {
            int i = message.what;
            switch (i) {
                case 1:
                    android.view.AccessibilityInteractionController.this.performAccessibilityActionUiThread(message);
                    return;
                case 2:
                    android.view.AccessibilityInteractionController.this.findAccessibilityNodeInfoByAccessibilityIdUiThread(message);
                    return;
                case 3:
                    android.view.AccessibilityInteractionController.this.findAccessibilityNodeInfosByViewIdUiThread(message);
                    return;
                case 4:
                    android.view.AccessibilityInteractionController.this.findAccessibilityNodeInfosByTextUiThread(message);
                    return;
                case 5:
                    android.view.AccessibilityInteractionController.this.findFocusUiThread(message);
                    return;
                case 6:
                    android.view.AccessibilityInteractionController.this.focusSearchUiThread(message);
                    return;
                case 7:
                    android.view.AccessibilityInteractionController.this.prepareForExtraDataRequestUiThread(message);
                    return;
                case 8:
                    android.view.AccessibilityInteractionController.this.requestPreparerDoneUiThread(message);
                    return;
                case 9:
                    android.view.AccessibilityInteractionController.this.requestPreparerTimeoutUiThread();
                    return;
                case 101:
                    android.view.AccessibilityInteractionController.this.clearAccessibilityFocusUiThread();
                    return;
                case 102:
                    android.view.AccessibilityInteractionController.this.notifyOutsideTouchUiThread();
                    return;
                default:
                    throw new java.lang.IllegalArgumentException("Unknown message type: " + i);
            }
        }

        boolean hasAccessibilityCallback(android.os.Message message) {
            return message.what < 100;
        }

        boolean hasUserInteractiveMessagesWaiting() {
            return hasMessagesOrCallbacks();
        }
    }

    private final class AddNodeInfosForViewId implements java.util.function.Predicate<android.view.View> {
        private java.util.List<android.view.accessibility.AccessibilityNodeInfo> mInfos;
        private int mViewId;

        private AddNodeInfosForViewId() {
            this.mViewId = -1;
        }

        public void init(int i, java.util.List<android.view.accessibility.AccessibilityNodeInfo> list) {
            this.mViewId = i;
            this.mInfos = list;
        }

        public void reset() {
            this.mViewId = -1;
            this.mInfos = null;
        }

        @Override // java.util.function.Predicate
        public boolean test(android.view.View view) {
            if (view.getId() == this.mViewId && android.view.AccessibilityInteractionController.this.isShown(view) && android.view.AccessibilityInteractionController.this.isVisibleToAccessibilityService(view)) {
                this.mInfos.add(view.createAccessibilityNodeInfo());
                return false;
            }
            return false;
        }
    }

    private static final class MessageHolder {
        final int mInterrogatingPid;
        final long mInterrogatingTid;
        final android.os.Message mMessage;

        MessageHolder(android.os.Message message, int i, long j) {
            this.mMessage = message;
            this.mInterrogatingPid = i;
            this.mInterrogatingTid = j;
        }
    }

    private static class SatisfiedFindAccessibilityNodeByAccessibilityIdRequest {
        final android.view.accessibility.IAccessibilityInteractionConnectionCallback mSatisfiedRequestCallback;
        final int mSatisfiedRequestInteractionId;
        final android.view.accessibility.AccessibilityNodeInfo mSatisfiedRequestNode;

        SatisfiedFindAccessibilityNodeByAccessibilityIdRequest(android.view.accessibility.AccessibilityNodeInfo accessibilityNodeInfo, android.view.accessibility.IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback, int i) {
            this.mSatisfiedRequestNode = accessibilityNodeInfo;
            this.mSatisfiedRequestCallback = iAccessibilityInteractionConnectionCallback;
            this.mSatisfiedRequestInteractionId = i;
        }
    }

    private class PrefetchDeque<E extends android.view.AccessibilityInteractionController.DequeNode> extends java.util.ArrayDeque<E> {
        java.util.List<android.view.accessibility.AccessibilityNodeInfo> mPrefetchOutput;
        int mStrategy;

        PrefetchDeque(int i, java.util.List<android.view.accessibility.AccessibilityNodeInfo> list) {
            this.mStrategy = i;
            this.mPrefetchOutput = list;
        }

        void performTraversalAndPrefetch() {
            while (!isEmpty()) {
                try {
                    E next = getNext();
                    android.view.accessibility.AccessibilityNodeInfo a11yNodeInfo = next.getA11yNodeInfo();
                    if (a11yNodeInfo != null) {
                        this.mPrefetchOutput.add(a11yNodeInfo);
                    }
                    if (android.view.AccessibilityInteractionController.this.mPrefetcher.shouldStopPrefetching(this.mPrefetchOutput)) {
                        return;
                    } else {
                        next.addChildren(a11yNodeInfo, this);
                    }
                } finally {
                    clear();
                }
            }
        }

        E getNext() {
            if (isStack()) {
                return (E) pop();
            }
            return (E) removeLast();
        }

        boolean isStack() {
            return (this.mStrategy & 8) != 0;
        }
    }

    private class ViewNode implements android.view.AccessibilityInteractionController.DequeNode {
        private final java.util.ArrayList<android.view.View> mTempViewList = new java.util.ArrayList<>();
        android.view.View mView;

        ViewNode(android.view.View view) {
            this.mView = view;
        }

        @Override // android.view.AccessibilityInteractionController.DequeNode
        public android.view.accessibility.AccessibilityNodeInfo getA11yNodeInfo() {
            if (this.mView == null) {
                return null;
            }
            return this.mView.createAccessibilityNodeInfo();
        }

        @Override // android.view.AccessibilityInteractionController.DequeNode
        public void addChildren(android.view.accessibility.AccessibilityNodeInfo accessibilityNodeInfo, android.view.AccessibilityInteractionController.PrefetchDeque prefetchDeque) {
            if (this.mView == null || !(this.mView instanceof android.view.ViewGroup)) {
                return;
            }
            java.util.ArrayList<android.view.View> arrayList = this.mTempViewList;
            arrayList.clear();
            try {
                this.mView.addChildrenForAccessibility(arrayList);
                int size = arrayList.size();
                if (prefetchDeque.isStack()) {
                    for (int i = size - 1; i >= 0; i--) {
                        addChild(prefetchDeque, arrayList.get(i));
                    }
                } else {
                    for (int i2 = 0; i2 < size; i2++) {
                        addChild(prefetchDeque, arrayList.get(i2));
                    }
                }
            } finally {
                arrayList.clear();
            }
        }

        private void addChild(java.util.ArrayDeque arrayDeque, android.view.View view) {
            if (android.view.AccessibilityInteractionController.this.isShown(view)) {
                android.view.accessibility.AccessibilityNodeProvider accessibilityNodeProvider = view.getAccessibilityNodeProvider();
                if (accessibilityNodeProvider == null) {
                    arrayDeque.push(android.view.AccessibilityInteractionController.this.new ViewNode(view));
                } else {
                    arrayDeque.push(android.view.AccessibilityInteractionController.this.new VirtualNode(-1L, accessibilityNodeProvider));
                }
            }
        }
    }

    private class VirtualNode implements android.view.AccessibilityInteractionController.DequeNode {
        long mInfoId;
        android.view.accessibility.AccessibilityNodeProvider mProvider;

        VirtualNode(long j, android.view.accessibility.AccessibilityNodeProvider accessibilityNodeProvider) {
            this.mInfoId = j;
            this.mProvider = accessibilityNodeProvider;
        }

        @Override // android.view.AccessibilityInteractionController.DequeNode
        public android.view.accessibility.AccessibilityNodeInfo getA11yNodeInfo() {
            if (this.mProvider == null) {
                return null;
            }
            return this.mProvider.createAccessibilityNodeInfo(android.view.accessibility.AccessibilityNodeInfo.getVirtualDescendantId(this.mInfoId));
        }

        @Override // android.view.AccessibilityInteractionController.DequeNode
        public void addChildren(android.view.accessibility.AccessibilityNodeInfo accessibilityNodeInfo, android.view.AccessibilityInteractionController.PrefetchDeque prefetchDeque) {
            if (accessibilityNodeInfo == null) {
                return;
            }
            int childCount = accessibilityNodeInfo.getChildCount();
            if (prefetchDeque.isStack()) {
                for (int i = childCount - 1; i >= 0; i--) {
                    prefetchDeque.push(android.view.AccessibilityInteractionController.this.new VirtualNode(accessibilityNodeInfo.getChildId(i), this.mProvider));
                }
                return;
            }
            for (int i2 = 0; i2 < childCount; i2++) {
                prefetchDeque.push(android.view.AccessibilityInteractionController.this.new VirtualNode(accessibilityNodeInfo.getChildId(i2), this.mProvider));
            }
        }
    }

    public void attachAccessibilityOverlayToWindowClientThread(android.view.SurfaceControl surfaceControl, int i, android.view.accessibility.IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback) {
        this.mHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new com.android.internal.util.function.QuadConsumer() { // from class: android.view.AccessibilityInteractionController$$ExternalSyntheticLambda0
            @Override // com.android.internal.util.function.QuadConsumer
            public final void accept(java.lang.Object obj, java.lang.Object obj2, java.lang.Object obj3, java.lang.Object obj4) {
                ((android.view.AccessibilityInteractionController) obj).attachAccessibilityOverlayToWindowUiThread((android.view.SurfaceControl) obj2, ((java.lang.Integer) obj3).intValue(), (android.view.accessibility.IAccessibilityInteractionConnectionCallback) obj4);
            }
        }, this, surfaceControl, java.lang.Integer.valueOf(i), iAccessibilityInteractionConnectionCallback));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void attachAccessibilityOverlayToWindowUiThread(android.view.SurfaceControl surfaceControl, int i, android.view.accessibility.IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback) {
        android.view.SurfaceControl surfaceControl2 = this.mViewRootImpl.getSurfaceControl();
        if (!surfaceControl2.isValid()) {
            try {
                iAccessibilityInteractionConnectionCallback.sendAttachOverlayResult(1, i);
                return;
            } catch (android.os.RemoteException e) {
            }
        }
        android.view.SurfaceControl.Transaction transaction = new android.view.SurfaceControl.Transaction();
        transaction.reparent(surfaceControl, surfaceControl2).apply();
        transaction.close();
        try {
            iAccessibilityInteractionConnectionCallback.sendAttachOverlayResult(0, i);
        } catch (android.os.RemoteException e2) {
        }
    }
}
