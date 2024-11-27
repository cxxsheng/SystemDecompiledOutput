package com.android.server.wm;

/* loaded from: classes3.dex */
public class TrustedPresentationListenerController {
    private android.os.Handler mHandler;
    private android.os.HandlerThread mHandlerThread;
    private android.view.InputWindowHandle[] mLastWindowHandles;
    private android.window.WindowInfosListener mWindowInfosListener;
    private final java.lang.Object mHandlerThreadLock = new java.lang.Object();
    com.android.server.wm.TrustedPresentationListenerController.Listeners mRegisteredListeners = new com.android.server.wm.TrustedPresentationListenerController.Listeners();

    /* JADX INFO: Access modifiers changed from: private */
    class Listeners {
        android.util.ArrayMap<android.os.IBinder, com.android.server.wm.TrustedPresentationListenerController.Listeners.ListenerDeathRecipient> mUniqueListeners;
        android.util.ArrayMap<android.os.IBinder, java.util.ArrayList<com.android.server.wm.TrustedPresentationListenerController.TrustedPresentationInfo>> mWindowToListeners;

        private Listeners() {
            this.mUniqueListeners = new android.util.ArrayMap<>();
            this.mWindowToListeners = new android.util.ArrayMap<>();
        }

        /* JADX INFO: Access modifiers changed from: private */
        final class ListenerDeathRecipient implements android.os.IBinder.DeathRecipient {
            int mInstances = 0;
            android.os.IBinder mListenerBinder;

            ListenerDeathRecipient(android.os.IBinder iBinder) {
                this.mListenerBinder = iBinder;
                try {
                    this.mListenerBinder.linkToDeath(this, 0);
                } catch (android.os.RemoteException e) {
                }
            }

            void addInstance() {
                this.mInstances++;
            }

            boolean removeInstance() {
                this.mInstances--;
                if (this.mInstances > 0) {
                    return false;
                }
                this.mListenerBinder.unlinkToDeath(this, 0);
                return true;
            }

            @Override // android.os.IBinder.DeathRecipient
            public void binderDied() {
                com.android.server.wm.TrustedPresentationListenerController.this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.wm.TrustedPresentationListenerController$Listeners$ListenerDeathRecipient$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        com.android.server.wm.TrustedPresentationListenerController.Listeners.ListenerDeathRecipient.this.lambda$binderDied$0();
                    }
                });
            }

            /* JADX INFO: Access modifiers changed from: private */
            public /* synthetic */ void lambda$binderDied$0() {
                com.android.server.wm.TrustedPresentationListenerController.Listeners.this.mUniqueListeners.remove(this.mListenerBinder);
                com.android.server.wm.TrustedPresentationListenerController.Listeners.this.removeListeners(this.mListenerBinder, java.util.Optional.empty());
            }
        }

        void register(android.os.IBinder iBinder, android.window.ITrustedPresentationListener iTrustedPresentationListener, android.window.TrustedPresentationThresholds trustedPresentationThresholds, int i) {
            this.mWindowToListeners.computeIfAbsent(iBinder, new java.util.function.Function() { // from class: com.android.server.wm.TrustedPresentationListenerController$Listeners$$ExternalSyntheticLambda0
                @Override // java.util.function.Function
                public final java.lang.Object apply(java.lang.Object obj) {
                    java.util.ArrayList lambda$register$0;
                    lambda$register$0 = com.android.server.wm.TrustedPresentationListenerController.Listeners.lambda$register$0((android.os.IBinder) obj);
                    return lambda$register$0;
                }
            }).add(new com.android.server.wm.TrustedPresentationListenerController.TrustedPresentationInfo(trustedPresentationThresholds, i, iTrustedPresentationListener));
            this.mUniqueListeners.computeIfAbsent(iTrustedPresentationListener.asBinder(), new java.util.function.Function() { // from class: com.android.server.wm.TrustedPresentationListenerController$Listeners$$ExternalSyntheticLambda1
                @Override // java.util.function.Function
                public final java.lang.Object apply(java.lang.Object obj) {
                    com.android.server.wm.TrustedPresentationListenerController.Listeners.ListenerDeathRecipient lambda$register$1;
                    lambda$register$1 = com.android.server.wm.TrustedPresentationListenerController.Listeners.this.lambda$register$1((android.os.IBinder) obj);
                    return lambda$register$1;
                }
            }).addInstance();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ java.util.ArrayList lambda$register$0(android.os.IBinder iBinder) {
            return new java.util.ArrayList();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ com.android.server.wm.TrustedPresentationListenerController.Listeners.ListenerDeathRecipient lambda$register$1(android.os.IBinder iBinder) {
            return new com.android.server.wm.TrustedPresentationListenerController.Listeners.ListenerDeathRecipient(iBinder);
        }

        void unregister(android.window.ITrustedPresentationListener iTrustedPresentationListener, int i) {
            android.os.IBinder asBinder = iTrustedPresentationListener.asBinder();
            com.android.server.wm.TrustedPresentationListenerController.Listeners.ListenerDeathRecipient listenerDeathRecipient = this.mUniqueListeners.get(asBinder);
            if (listenerDeathRecipient == null) {
                com.android.internal.protolog.ProtoLogImpl_1545807451.e(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_TPL, 3445530300764535903L, 4, null, java.lang.String.valueOf(iTrustedPresentationListener), java.lang.Long.valueOf(i));
            } else {
                if (listenerDeathRecipient.removeInstance()) {
                    this.mUniqueListeners.remove(asBinder);
                }
                removeListeners(asBinder, java.util.Optional.of(java.lang.Integer.valueOf(i)));
            }
        }

        boolean isEmpty() {
            return this.mWindowToListeners.isEmpty();
        }

        java.util.ArrayList<com.android.server.wm.TrustedPresentationListenerController.TrustedPresentationInfo> get(android.os.IBinder iBinder) {
            return this.mWindowToListeners.get(iBinder);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void removeListeners(android.os.IBinder iBinder, java.util.Optional<java.lang.Integer> optional) {
            for (int size = this.mWindowToListeners.size() - 1; size >= 0; size--) {
                java.util.ArrayList<com.android.server.wm.TrustedPresentationListenerController.TrustedPresentationInfo> valueAt = this.mWindowToListeners.valueAt(size);
                for (int size2 = valueAt.size() - 1; size2 >= 0; size2--) {
                    com.android.server.wm.TrustedPresentationListenerController.TrustedPresentationInfo trustedPresentationInfo = valueAt.get(size2);
                    if (trustedPresentationInfo.mListener.asBinder() == iBinder && (optional.isEmpty() || trustedPresentationInfo.mId == optional.get().intValue())) {
                        valueAt.remove(size2);
                    }
                }
                if (valueAt.isEmpty()) {
                    this.mWindowToListeners.removeAt(size);
                }
            }
        }
    }

    private void startHandlerThreadIfNeeded() {
        synchronized (this.mHandlerThreadLock) {
            try {
                if (this.mHandler == null) {
                    this.mHandlerThread = new android.os.HandlerThread("WindowInfosListenerForTpl");
                    this.mHandlerThread.start();
                    this.mHandler = new android.os.Handler(this.mHandlerThread.getLooper());
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    void registerListener(final android.os.IBinder iBinder, final android.window.ITrustedPresentationListener iTrustedPresentationListener, final android.window.TrustedPresentationThresholds trustedPresentationThresholds, final int i) {
        startHandlerThreadIfNeeded();
        this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.wm.TrustedPresentationListenerController$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.wm.TrustedPresentationListenerController.this.lambda$registerListener$0(iTrustedPresentationListener, i, iBinder, trustedPresentationThresholds);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$registerListener$0(android.window.ITrustedPresentationListener iTrustedPresentationListener, int i, android.os.IBinder iBinder, android.window.TrustedPresentationThresholds trustedPresentationThresholds) {
        com.android.internal.protolog.ProtoLogImpl_1545807451.d(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_TPL, -6140852484700685564L, 4, null, java.lang.String.valueOf(iTrustedPresentationListener), java.lang.Long.valueOf(i), java.lang.String.valueOf(iBinder), java.lang.String.valueOf(trustedPresentationThresholds));
        this.mRegisteredListeners.register(iBinder, iTrustedPresentationListener, trustedPresentationThresholds, i);
        registerWindowInfosListener();
        computeTpl(this.mLastWindowHandles);
    }

    void unregisterListener(final android.window.ITrustedPresentationListener iTrustedPresentationListener, final int i) {
        startHandlerThreadIfNeeded();
        this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.wm.TrustedPresentationListenerController$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.wm.TrustedPresentationListenerController.this.lambda$unregisterListener$1(iTrustedPresentationListener, i);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$unregisterListener$1(android.window.ITrustedPresentationListener iTrustedPresentationListener, int i) {
        com.android.internal.protolog.ProtoLogImpl_1545807451.d(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_TPL, 3691097873058247482L, 4, null, java.lang.String.valueOf(iTrustedPresentationListener), java.lang.Long.valueOf(i));
        this.mRegisteredListeners.unregister(iTrustedPresentationListener, i);
        if (this.mRegisteredListeners.isEmpty()) {
            unregisterWindowInfosListener();
        }
    }

    void dump(java.io.PrintWriter printWriter) {
        printWriter.println("TrustedPresentationListenerController:");
        printWriter.println("  Active unique listeners (" + this.mRegisteredListeners.mUniqueListeners.size() + "):");
        for (int i = 0; i < this.mRegisteredListeners.mWindowToListeners.size(); i++) {
            printWriter.println("    window=" + this.mRegisteredListeners.mWindowToListeners.keyAt(i));
            java.util.ArrayList<com.android.server.wm.TrustedPresentationListenerController.TrustedPresentationInfo> valueAt = this.mRegisteredListeners.mWindowToListeners.valueAt(i);
            for (int i2 = 0; i2 < valueAt.size(); i2++) {
                com.android.server.wm.TrustedPresentationListenerController.TrustedPresentationInfo trustedPresentationInfo = valueAt.get(i2);
                printWriter.println("      listener=" + trustedPresentationInfo.mListener.asBinder() + " id=" + trustedPresentationInfo.mId + " thresholds=" + trustedPresentationInfo.mThresholds);
            }
        }
    }

    private void registerWindowInfosListener() {
        if (this.mWindowInfosListener != null) {
            return;
        }
        this.mWindowInfosListener = new com.android.server.wm.TrustedPresentationListenerController.AnonymousClass1();
        this.mLastWindowHandles = (android.view.InputWindowHandle[]) this.mWindowInfosListener.register().first;
    }

    /* renamed from: com.android.server.wm.TrustedPresentationListenerController$1, reason: invalid class name */
    class AnonymousClass1 extends android.window.WindowInfosListener {
        AnonymousClass1() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onWindowInfosChanged$0(android.view.InputWindowHandle[] inputWindowHandleArr) {
            com.android.server.wm.TrustedPresentationListenerController.this.computeTpl(inputWindowHandleArr);
        }

        public void onWindowInfosChanged(final android.view.InputWindowHandle[] inputWindowHandleArr, android.window.WindowInfosListener.DisplayInfo[] displayInfoArr) {
            com.android.server.wm.TrustedPresentationListenerController.this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.wm.TrustedPresentationListenerController$1$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.wm.TrustedPresentationListenerController.AnonymousClass1.this.lambda$onWindowInfosChanged$0(inputWindowHandleArr);
                }
            });
        }
    }

    private void unregisterWindowInfosListener() {
        if (this.mWindowInfosListener == null) {
            return;
        }
        this.mWindowInfosListener.unregister();
        this.mWindowInfosListener = null;
        this.mLastWindowHandles = null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void computeTpl(android.view.InputWindowHandle[] inputWindowHandleArr) {
        int i;
        int i2;
        android.graphics.Matrix matrix;
        android.view.InputWindowHandle inputWindowHandle;
        com.android.server.wm.TrustedPresentationListenerController trustedPresentationListenerController = this;
        trustedPresentationListenerController.mLastWindowHandles = inputWindowHandleArr;
        if (trustedPresentationListenerController.mLastWindowHandles == null || trustedPresentationListenerController.mLastWindowHandles.length == 0 || trustedPresentationListenerController.mRegisteredListeners.isEmpty()) {
            return;
        }
        android.graphics.Rect rect = new android.graphics.Rect();
        android.graphics.Matrix matrix2 = new android.graphics.Matrix();
        float[] fArr = new float[9];
        android.graphics.Region region = new android.graphics.Region();
        long currentTimeMillis = java.lang.System.currentTimeMillis();
        com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_TPL, 6408851516381868623L, 1, null, java.lang.Long.valueOf(trustedPresentationListenerController.mLastWindowHandles.length));
        android.util.ArrayMap<android.window.ITrustedPresentationListener, android.util.Pair<android.util.IntArray, android.util.IntArray>> arrayMap = new android.util.ArrayMap<>();
        android.view.InputWindowHandle[] inputWindowHandleArr2 = trustedPresentationListenerController.mLastWindowHandles;
        int length = inputWindowHandleArr2.length;
        int i3 = 0;
        while (i3 < length) {
            android.view.InputWindowHandle inputWindowHandle2 = inputWindowHandleArr2[i3];
            if (!inputWindowHandle2.canOccludePresentation) {
                com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_TPL, 7718187745767272532L, 0, null, java.lang.String.valueOf(inputWindowHandle2.name));
                i = i3;
                i2 = length;
                matrix = matrix2;
            } else {
                rect.set(inputWindowHandle2.frame);
                java.util.ArrayList<com.android.server.wm.TrustedPresentationListenerController.TrustedPresentationInfo> arrayList = trustedPresentationListenerController.mRegisteredListeners.get(inputWindowHandle2.getWindowToken());
                if (arrayList == null) {
                    i = i3;
                    i2 = length;
                    matrix = matrix2;
                    inputWindowHandle = inputWindowHandle2;
                } else {
                    android.graphics.Region region2 = new android.graphics.Region();
                    region2.op(rect, region, android.graphics.Region.Op.DIFFERENCE);
                    inputWindowHandle2.transform.invert(matrix2);
                    matrix2.getValues(fArr);
                    matrix = matrix2;
                    inputWindowHandle = inputWindowHandle2;
                    i = i3;
                    i2 = length;
                    checkIfInThreshold(arrayList, arrayMap, computeFractionRendered(region2, new android.graphics.RectF(rect), inputWindowHandle2.contentSize, (float) java.lang.Math.sqrt((fArr[0] * fArr[0]) + (fArr[1] * fArr[1])), (float) java.lang.Math.sqrt((fArr[4] * fArr[4]) + (fArr[3] * fArr[3]))), inputWindowHandle.alpha, currentTimeMillis);
                }
                region.op(rect, android.graphics.Region.Op.UNION);
                com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_TPL, -1135667737459933313L, 0, null, java.lang.String.valueOf(inputWindowHandle.name), java.lang.String.valueOf(rect.toShortString()), java.lang.String.valueOf(region));
            }
            i3 = i + 1;
            matrix2 = matrix;
            length = i2;
            trustedPresentationListenerController = this;
        }
        for (int i4 = 0; i4 < arrayMap.size(); i4++) {
            android.util.Pair<android.util.IntArray, android.util.IntArray> valueAt = arrayMap.valueAt(i4);
            try {
                arrayMap.keyAt(i4).onTrustedPresentationChanged(((android.util.IntArray) valueAt.first).toArray(), ((android.util.IntArray) valueAt.second).toArray());
            } catch (android.os.RemoteException e) {
            }
        }
    }

    private void addListenerUpdate(android.util.ArrayMap<android.window.ITrustedPresentationListener, android.util.Pair<android.util.IntArray, android.util.IntArray>> arrayMap, android.window.ITrustedPresentationListener iTrustedPresentationListener, int i, boolean z) {
        android.util.Pair<android.util.IntArray, android.util.IntArray> pair = arrayMap.get(iTrustedPresentationListener);
        if (pair == null) {
            pair = new android.util.Pair<>(new android.util.IntArray(), new android.util.IntArray());
            arrayMap.put(iTrustedPresentationListener, pair);
        }
        if (z) {
            ((android.util.IntArray) pair.first).add(i);
        } else {
            ((android.util.IntArray) pair.second).add(i);
        }
    }

    private void checkIfInThreshold(java.util.ArrayList<com.android.server.wm.TrustedPresentationListenerController.TrustedPresentationInfo> arrayList, android.util.ArrayMap<android.window.ITrustedPresentationListener, android.util.Pair<android.util.IntArray, android.util.IntArray>> arrayMap, float f, float f2, long j) {
        float f3 = f;
        float f4 = f2;
        double d = f3;
        double d2 = f4;
        com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_TPL, 854487339271667012L, 26, null, java.lang.Double.valueOf(d), java.lang.Double.valueOf(d2), java.lang.Long.valueOf(j));
        int i = 0;
        while (i < arrayList.size()) {
            com.android.server.wm.TrustedPresentationListenerController.TrustedPresentationInfo trustedPresentationInfo = arrayList.get(i);
            android.window.ITrustedPresentationListener iTrustedPresentationListener = trustedPresentationInfo.mListener;
            boolean z = trustedPresentationInfo.mLastComputedTrustedPresentationState;
            boolean z2 = f4 >= trustedPresentationInfo.mThresholds.getMinAlpha() && f3 >= trustedPresentationInfo.mThresholds.getMinFractionRendered();
            trustedPresentationInfo.mLastComputedTrustedPresentationState = z2;
            int i2 = i;
            com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_TPL, -2248576188205088843L, 2720, null, java.lang.String.valueOf(z), java.lang.String.valueOf(z2), java.lang.Double.valueOf(d2), java.lang.Double.valueOf(trustedPresentationInfo.mThresholds.getMinAlpha()), java.lang.Double.valueOf(d), java.lang.Double.valueOf(trustedPresentationInfo.mThresholds.getMinFractionRendered()));
            if (z && !z2) {
                if (trustedPresentationInfo.mLastReportedTrustedPresentationState) {
                    trustedPresentationInfo.mLastReportedTrustedPresentationState = false;
                    addListenerUpdate(arrayMap, iTrustedPresentationListener, trustedPresentationInfo.mId, false);
                    com.android.internal.protolog.ProtoLogImpl_1545807451.d(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_TPL, 6236170793308011579L, 4, null, java.lang.String.valueOf(iTrustedPresentationListener), java.lang.Long.valueOf(trustedPresentationInfo.mId));
                }
                trustedPresentationInfo.mEnteredTrustedPresentationStateTime = -1L;
            } else if (!z && z2) {
                trustedPresentationInfo.mEnteredTrustedPresentationStateTime = j;
                this.mHandler.postDelayed(new java.lang.Runnable() { // from class: com.android.server.wm.TrustedPresentationListenerController$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        com.android.server.wm.TrustedPresentationListenerController.this.lambda$checkIfInThreshold$2();
                    }
                }, (long) (trustedPresentationInfo.mThresholds.getStabilityRequirementMillis() * 1.5d));
            }
            if (!trustedPresentationInfo.mLastReportedTrustedPresentationState && z2 && j - trustedPresentationInfo.mEnteredTrustedPresentationStateTime > trustedPresentationInfo.mThresholds.getStabilityRequirementMillis()) {
                trustedPresentationInfo.mLastReportedTrustedPresentationState = true;
                addListenerUpdate(arrayMap, iTrustedPresentationListener, trustedPresentationInfo.mId, true);
                com.android.internal.protolog.ProtoLogImpl_1545807451.d(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_TPL, 5405816744363636527L, 4, null, java.lang.String.valueOf(iTrustedPresentationListener), java.lang.Long.valueOf(trustedPresentationInfo.mId));
            }
            i = i2 + 1;
            f3 = f;
            f4 = f2;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$checkIfInThreshold$2() {
        computeTpl(this.mLastWindowHandles);
    }

    private float computeFractionRendered(android.graphics.Region region, android.graphics.RectF rectF, android.util.Size size, float f, float f2) {
        com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_TPL, -5162728346383863020L, 640, null, java.lang.String.valueOf(region), java.lang.String.valueOf(rectF), java.lang.String.valueOf(size), java.lang.Double.valueOf(f), java.lang.Double.valueOf(f2));
        if (size.getWidth() == 0 || size.getHeight() == 0 || rectF.width() == com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE || rectF.height() == com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE) {
            return -1.0f;
        }
        float min = java.lang.Math.min(f * f2, 1.0f);
        com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_TPL, 898769258643799441L, 2, null, java.lang.Double.valueOf(min));
        float width = min * (rectF.width() / size.getWidth()) * (rectF.height() / size.getHeight());
        com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_TPL, -455501334697331596L, 2, null, java.lang.Double.valueOf(width));
        final float[] fArr = new float[1];
        com.android.server.wm.utils.RegionUtils.forEachRect(region, new java.util.function.Consumer() { // from class: com.android.server.wm.TrustedPresentationListenerController$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.wm.TrustedPresentationListenerController.lambda$computeFractionRendered$3(fArr, (android.graphics.Rect) obj);
            }
        });
        return width * (fArr[0] / (rectF.width() * rectF.height()));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$computeFractionRendered$3(float[] fArr, android.graphics.Rect rect) {
        fArr[0] = fArr[0] + (rect.width() * rect.height());
    }

    private static class TrustedPresentationInfo {
        long mEnteredTrustedPresentationStateTime;
        final int mId;
        boolean mLastComputedTrustedPresentationState;
        boolean mLastReportedTrustedPresentationState;
        final android.window.ITrustedPresentationListener mListener;
        final android.window.TrustedPresentationThresholds mThresholds;

        private TrustedPresentationInfo(android.window.TrustedPresentationThresholds trustedPresentationThresholds, int i, android.window.ITrustedPresentationListener iTrustedPresentationListener) {
            this.mLastComputedTrustedPresentationState = false;
            this.mLastReportedTrustedPresentationState = false;
            this.mEnteredTrustedPresentationStateTime = -1L;
            this.mThresholds = trustedPresentationThresholds;
            this.mId = i;
            this.mListener = iTrustedPresentationListener;
        }
    }
}
