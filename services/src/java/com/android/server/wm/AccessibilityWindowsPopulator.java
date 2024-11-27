package com.android.server.wm;

/* loaded from: classes3.dex */
public final class AccessibilityWindowsPopulator extends android.window.WindowInfosListener {
    private static final int SURFACE_FLINGER_CALLBACK_WINDOWS_STABLE_TIMES_MS = 35;
    private static final int WINDOWS_CHANGED_NOTIFICATION_MAX_DURATION_TIMES_MS = 450;
    private final com.android.server.wm.AccessibilityController mAccessibilityController;
    private final android.os.Handler mHandler;
    private final com.android.server.wm.WindowManagerService mService;
    private static final java.lang.String TAG = com.android.server.wm.AccessibilityWindowsPopulator.class.getSimpleName();
    private static final float[] sTempFloats = new float[9];

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final android.util.SparseArray<java.util.List<android.view.InputWindowHandle>> mInputWindowHandlesOnDisplays = new android.util.SparseArray<>();

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final android.util.SparseArray<android.graphics.Matrix> mMagnificationSpecInverseMatrix = new android.util.SparseArray<>();

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final android.util.SparseArray<android.window.WindowInfosListener.DisplayInfo> mDisplayInfos = new android.util.SparseArray<>();
    private final android.util.SparseArray<android.view.MagnificationSpec> mCurrentMagnificationSpec = new android.util.SparseArray<>();

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final android.util.SparseArray<android.view.MagnificationSpec> mPreviousMagnificationSpec = new android.util.SparseArray<>();

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final java.util.List<android.view.InputWindowHandle> mVisibleWindows = new java.util.ArrayList();

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean mWindowsNotificationEnabled = false;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final java.util.Map<android.os.IBinder, android.graphics.Matrix> mWindowsTransformMatrixMap = new java.util.HashMap();
    private final java.lang.Object mLock = new java.lang.Object();
    private final android.graphics.Matrix mTempMatrix1 = new android.graphics.Matrix();
    private final android.graphics.Matrix mTempMatrix2 = new android.graphics.Matrix();
    private final float[] mTempFloat1 = new float[9];
    private final float[] mTempFloat2 = new float[9];
    private final float[] mTempFloat3 = new float[9];

    AccessibilityWindowsPopulator(com.android.server.wm.WindowManagerService windowManagerService, com.android.server.wm.AccessibilityController accessibilityController) {
        this.mService = windowManagerService;
        this.mAccessibilityController = accessibilityController;
        this.mHandler = new com.android.server.wm.AccessibilityWindowsPopulator.MyHandler(this.mService.mH.getLooper());
    }

    public void populateVisibleWindowsOnScreenLocked(int i, java.util.List<com.android.server.wm.AccessibilityWindowsPopulator.AccessibilityWindow> list) {
        android.graphics.Matrix matrix = new android.graphics.Matrix();
        android.graphics.Matrix matrix2 = new android.graphics.Matrix();
        synchronized (this.mLock) {
            try {
                java.util.List<android.view.InputWindowHandle> list2 = this.mInputWindowHandlesOnDisplays.get(i);
                if (list2 == null) {
                    list.clear();
                    return;
                }
                matrix.set(this.mMagnificationSpecInverseMatrix.get(i));
                android.window.WindowInfosListener.DisplayInfo displayInfo = this.mDisplayInfos.get(i);
                if (displayInfo != null) {
                    matrix2.set(displayInfo.mTransform);
                } else {
                    android.util.Slog.w(TAG, "The displayInfo of this displayId (" + i + ") called back from the surface fligner is null");
                }
                com.android.server.wm.ShellRoot shellRoot = this.mService.mRoot.getDisplayContent(i).mShellRoots.get(1);
                android.os.IBinder accessibilityWindowToken = shellRoot != null ? shellRoot.getAccessibilityWindowToken() : null;
                java.util.Iterator<android.view.InputWindowHandle> it = list2.iterator();
                while (it.hasNext()) {
                    list.add(com.android.server.wm.AccessibilityWindowsPopulator.AccessibilityWindow.initializeData(this.mService, it.next(), matrix, accessibilityWindowToken, matrix2));
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void onWindowInfosChanged(final android.view.InputWindowHandle[] inputWindowHandleArr, final android.window.WindowInfosListener.DisplayInfo[] displayInfoArr) {
        this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.wm.AccessibilityWindowsPopulator$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.wm.AccessibilityWindowsPopulator.this.lambda$onWindowInfosChanged$0(inputWindowHandleArr, displayInfoArr);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: onWindowInfosChangedInternal, reason: merged with bridge method [inline-methods] */
    public void lambda$onWindowInfosChanged$0(android.view.InputWindowHandle[] inputWindowHandleArr, android.window.WindowInfosListener.DisplayInfo[] displayInfoArr) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        for (android.view.InputWindowHandle inputWindowHandle : inputWindowHandleArr) {
            boolean z = (inputWindowHandle.inputConfig & 2) == 0;
            boolean z2 = (inputWindowHandle.inputConfig & 65536) == 0;
            boolean z3 = !inputWindowHandle.touchableRegion.isEmpty();
            boolean isEmpty = true ^ inputWindowHandle.frame.isEmpty();
            if (z && z2 && z3 && isEmpty) {
                arrayList.add(inputWindowHandle);
            }
        }
        java.util.HashMap<android.os.IBinder, android.graphics.Matrix> windowsTransformMatrix = getWindowsTransformMatrix(arrayList);
        synchronized (this.mLock) {
            try {
                this.mWindowsTransformMatrixMap.clear();
                this.mWindowsTransformMatrixMap.putAll(windowsTransformMatrix);
                this.mVisibleWindows.clear();
                this.mVisibleWindows.addAll(arrayList);
                this.mDisplayInfos.clear();
                for (android.window.WindowInfosListener.DisplayInfo displayInfo : displayInfoArr) {
                    this.mDisplayInfos.put(displayInfo.mDisplayId, displayInfo);
                }
                if (this.mWindowsNotificationEnabled) {
                    if (!this.mHandler.hasMessages(3)) {
                        this.mHandler.sendEmptyMessageDelayed(3, 450L);
                    }
                    populateVisibleWindowHandlesAndNotifyWindowsChangeIfNeeded();
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private java.util.HashMap<android.os.IBinder, android.graphics.Matrix> getWindowsTransformMatrix(java.util.List<android.view.InputWindowHandle> list) {
        java.util.HashMap<android.os.IBinder, android.graphics.Matrix> hashMap;
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mService.mGlobalLock;
        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                hashMap = new java.util.HashMap<>();
                java.util.Iterator<android.view.InputWindowHandle> it = list.iterator();
                while (it.hasNext()) {
                    android.os.IBinder windowToken = it.next().getWindowToken();
                    com.android.server.wm.WindowState windowState = windowToken != null ? this.mService.mWindowMap.get(windowToken) : null;
                    if (windowState != null && windowState.shouldMagnify()) {
                        android.graphics.Matrix matrix = new android.graphics.Matrix();
                        windowState.getTransformationMatrix(sTempFloats, matrix);
                        hashMap.put(windowToken, matrix);
                    }
                }
            } catch (java.lang.Throwable th) {
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
        return hashMap;
    }

    public void setWindowsNotification(boolean z) {
        synchronized (this.mLock) {
            try {
                if (this.mWindowsNotificationEnabled == z) {
                    return;
                }
                this.mWindowsNotificationEnabled = z;
                if (this.mWindowsNotificationEnabled) {
                    android.util.Pair register = register();
                    lambda$onWindowInfosChanged$0((android.view.InputWindowHandle[]) register.first, (android.window.WindowInfosListener.DisplayInfo[]) register.second);
                } else {
                    unregister();
                    releaseResources();
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void setMagnificationSpec(int i, android.view.MagnificationSpec magnificationSpec) {
        synchronized (this.mLock) {
            try {
                android.view.MagnificationSpec magnificationSpec2 = this.mCurrentMagnificationSpec.get(i);
                if (magnificationSpec2 == null) {
                    android.view.MagnificationSpec magnificationSpec3 = new android.view.MagnificationSpec();
                    magnificationSpec3.setTo(magnificationSpec);
                    this.mCurrentMagnificationSpec.put(i, magnificationSpec3);
                } else {
                    android.view.MagnificationSpec magnificationSpec4 = this.mPreviousMagnificationSpec.get(i);
                    if (magnificationSpec4 == null) {
                        magnificationSpec4 = new android.view.MagnificationSpec();
                        this.mPreviousMagnificationSpec.put(i, magnificationSpec4);
                    }
                    magnificationSpec4.setTo(magnificationSpec2);
                    magnificationSpec2.setTo(magnificationSpec);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void populateVisibleWindowHandlesAndNotifyWindowsChangeIfNeeded() {
        android.util.SparseArray<java.util.List<android.view.InputWindowHandle>> sparseArray = new android.util.SparseArray<>();
        for (android.view.InputWindowHandle inputWindowHandle : this.mVisibleWindows) {
            java.util.List<android.view.InputWindowHandle> list = sparseArray.get(inputWindowHandle.displayId);
            if (list == null) {
                list = new java.util.ArrayList<>();
                sparseArray.put(inputWindowHandle.displayId, list);
            }
            list.add(inputWindowHandle);
        }
        findMagnificationSpecInverseMatrixIfNeeded(sparseArray);
        java.util.ArrayList arrayList = new java.util.ArrayList();
        getDisplaysForWindowsChanged(arrayList, sparseArray, this.mInputWindowHandlesOnDisplays);
        this.mInputWindowHandlesOnDisplays.clear();
        for (int i = 0; i < sparseArray.size(); i++) {
            int keyAt = sparseArray.keyAt(i);
            this.mInputWindowHandlesOnDisplays.put(keyAt, sparseArray.get(keyAt));
        }
        if (!arrayList.isEmpty()) {
            if (!this.mHandler.hasMessages(1)) {
                this.mHandler.obtainMessage(1, arrayList).sendToTarget();
            }
        } else {
            this.mHandler.removeMessages(2);
            this.mHandler.sendEmptyMessageDelayed(2, 35L);
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private static void getDisplaysForWindowsChanged(java.util.List<java.lang.Integer> list, android.util.SparseArray<java.util.List<android.view.InputWindowHandle>> sparseArray, android.util.SparseArray<java.util.List<android.view.InputWindowHandle>> sparseArray2) {
        for (int i = 0; i < sparseArray.size(); i++) {
            int keyAt = sparseArray.keyAt(i);
            if (hasWindowsChanged(sparseArray.get(keyAt), sparseArray2.get(keyAt))) {
                list.add(java.lang.Integer.valueOf(keyAt));
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private static boolean hasWindowsChanged(java.util.List<android.view.InputWindowHandle> list, java.util.List<android.view.InputWindowHandle> list2) {
        boolean z;
        boolean z2;
        if (list2 == null || list2.size() != list.size()) {
            return true;
        }
        int size = list.size();
        for (int i = 0; i < size; i++) {
            android.os.IBinder windowToken = list.get(i).getWindowToken();
            android.os.IBinder windowToken2 = list2.get(i).getWindowToken();
            if (windowToken != null) {
                z = true;
            } else {
                z = false;
            }
            if (windowToken2 != null) {
                z2 = true;
            } else {
                z2 = false;
            }
            if (z != z2) {
                return true;
            }
            if (z && z2 && !windowToken.equals(windowToken2)) {
                return true;
            }
        }
        return false;
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void findMagnificationSpecInverseMatrixIfNeeded(android.util.SparseArray<java.util.List<android.view.InputWindowHandle>> sparseArray) {
        for (int i = 0; i < sparseArray.size(); i++) {
            int keyAt = sparseArray.keyAt(i);
            java.util.List<android.view.InputWindowHandle> list = sparseArray.get(keyAt);
            android.view.MagnificationSpec magnificationSpec = this.mCurrentMagnificationSpec.get(keyAt);
            if (magnificationSpec != null) {
                android.view.MagnificationSpec magnificationSpec2 = new android.view.MagnificationSpec();
                magnificationSpec2.setTo(magnificationSpec);
                android.view.MagnificationSpec magnificationSpec3 = this.mPreviousMagnificationSpec.get(keyAt);
                if (magnificationSpec3 == null) {
                    android.graphics.Matrix matrix = new android.graphics.Matrix();
                    generateInverseMatrix(magnificationSpec2, matrix);
                    this.mMagnificationSpecInverseMatrix.put(keyAt, matrix);
                } else {
                    android.view.MagnificationSpec magnificationSpec4 = new android.view.MagnificationSpec();
                    magnificationSpec4.setTo(magnificationSpec3);
                    generateInverseMatrixBasedOnProperMagnificationSpecForDisplay(list, magnificationSpec2, magnificationSpec4);
                }
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void generateInverseMatrixBasedOnProperMagnificationSpecForDisplay(java.util.List<android.view.InputWindowHandle> list, android.view.MagnificationSpec magnificationSpec, android.view.MagnificationSpec magnificationSpec2) {
        for (int size = list.size() - 1; size >= 0; size--) {
            android.graphics.Matrix matrix = this.mTempMatrix2;
            android.view.InputWindowHandle inputWindowHandle = list.get(size);
            if (getWindowTransformMatrix(inputWindowHandle.getWindowToken(), matrix)) {
                generateMagnificationSpecInverseMatrix(inputWindowHandle, magnificationSpec, magnificationSpec2, matrix);
                return;
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean getWindowTransformMatrix(android.os.IBinder iBinder, android.graphics.Matrix matrix) {
        android.graphics.Matrix matrix2 = iBinder != null ? this.mWindowsTransformMatrixMap.get(iBinder) : null;
        if (matrix2 == null) {
            return false;
        }
        matrix.set(matrix2);
        return true;
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void generateMagnificationSpecInverseMatrix(android.view.InputWindowHandle inputWindowHandle, @android.annotation.NonNull android.view.MagnificationSpec magnificationSpec, @android.annotation.NonNull android.view.MagnificationSpec magnificationSpec2, android.graphics.Matrix matrix) {
        float[] fArr = this.mTempFloat1;
        computeIdentityMatrix(inputWindowHandle, magnificationSpec, matrix, fArr);
        float[] fArr2 = this.mTempFloat2;
        computeIdentityMatrix(inputWindowHandle, magnificationSpec2, matrix, fArr2);
        android.graphics.Matrix matrix2 = new android.graphics.Matrix();
        if (selectProperMagnificationSpecByComparingIdentityDegree(fArr, fArr2)) {
            generateInverseMatrix(magnificationSpec, matrix2);
            this.mPreviousMagnificationSpec.remove(inputWindowHandle.displayId);
            if (magnificationSpec.isNop()) {
                this.mCurrentMagnificationSpec.remove(inputWindowHandle.displayId);
                this.mMagnificationSpecInverseMatrix.remove(inputWindowHandle.displayId);
                return;
            }
        } else {
            generateInverseMatrix(magnificationSpec2, matrix2);
        }
        this.mMagnificationSpecInverseMatrix.put(inputWindowHandle.displayId, matrix2);
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void computeIdentityMatrix(android.view.InputWindowHandle inputWindowHandle, @android.annotation.NonNull android.view.MagnificationSpec magnificationSpec, android.graphics.Matrix matrix, float[] fArr) {
        android.graphics.Matrix matrix2 = this.mTempMatrix1;
        transformMagnificationSpecToMatrix(magnificationSpec, matrix2);
        android.graphics.Matrix matrix3 = new android.graphics.Matrix(inputWindowHandle.transform);
        matrix3.preConcat(matrix2);
        matrix3.preConcat(matrix);
        matrix3.getValues(fArr);
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean selectProperMagnificationSpecByComparingIdentityDegree(float[] fArr, float[] fArr2) {
        float[] fArr3 = this.mTempFloat3;
        android.graphics.Matrix.IDENTITY_MATRIX.getValues(fArr3);
        float abs = java.lang.Math.abs(fArr3[0] - fArr[0]);
        float abs2 = java.lang.Math.abs(fArr3[0] - fArr2[0]);
        float abs3 = java.lang.Math.abs(fArr3[2] - fArr[2]);
        return java.lang.Float.compare(abs2, abs) > 0 || (java.lang.Float.compare(abs2, abs) == 0 && java.lang.Float.compare(java.lang.Math.abs(fArr3[2] - fArr2[2]) + java.lang.Math.abs(fArr3[5] - fArr2[5]), abs3 + java.lang.Math.abs(fArr3[5] - fArr[5])) > 0);
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private static void generateInverseMatrix(android.view.MagnificationSpec magnificationSpec, android.graphics.Matrix matrix) {
        matrix.reset();
        android.graphics.Matrix matrix2 = new android.graphics.Matrix();
        transformMagnificationSpecToMatrix(magnificationSpec, matrix2);
        if (!matrix2.invert(matrix)) {
            android.util.Slog.e(TAG, "Can't inverse the magnification spec matrix with the magnification spec = " + magnificationSpec);
            matrix.reset();
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private static void transformMagnificationSpecToMatrix(android.view.MagnificationSpec magnificationSpec, android.graphics.Matrix matrix) {
        matrix.reset();
        matrix.postScale(magnificationSpec.scale, magnificationSpec.scale);
        matrix.postTranslate(magnificationSpec.offsetX, magnificationSpec.offsetY);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyWindowsChanged(@android.annotation.NonNull java.util.List<java.lang.Integer> list) {
        this.mHandler.removeMessages(3);
        for (int i = 0; i < list.size(); i++) {
            this.mAccessibilityController.performComputeChangedWindowsNot(list.get(i).intValue(), false);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void forceUpdateWindows() {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        synchronized (this.mLock) {
            for (int i = 0; i < this.mInputWindowHandlesOnDisplays.size(); i++) {
                try {
                    arrayList.add(java.lang.Integer.valueOf(this.mInputWindowHandlesOnDisplays.keyAt(i)));
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
        notifyWindowsChanged(arrayList);
    }

    void dump(final java.io.PrintWriter printWriter, final java.lang.String str) {
        printWriter.print(str);
        printWriter.println("AccessibilityWindowsPopulator");
        java.lang.String str2 = str + "  ";
        printWriter.print(str2);
        printWriter.print("mWindowsNotificationEnabled: ");
        printWriter.println(this.mWindowsNotificationEnabled);
        if (this.mVisibleWindows.isEmpty()) {
            printWriter.print(str2);
            printWriter.println("No visible windows");
        } else {
            printWriter.print(str2);
            printWriter.print(this.mVisibleWindows.size());
            printWriter.print(" visible windows: ");
            printWriter.println(this.mVisibleWindows);
        }
        com.android.internal.util.DumpUtils.KeyDumper keyDumper = new com.android.internal.util.DumpUtils.KeyDumper() { // from class: com.android.server.wm.AccessibilityWindowsPopulator$$ExternalSyntheticLambda1
            public final void dump(int i, int i2) {
                com.android.server.wm.AccessibilityWindowsPopulator.lambda$dump$1(i, i2);
            }
        };
        com.android.internal.util.DumpUtils.KeyDumper keyDumper2 = new com.android.internal.util.DumpUtils.KeyDumper() { // from class: com.android.server.wm.AccessibilityWindowsPopulator$$ExternalSyntheticLambda2
            public final void dump(int i, int i2) {
                com.android.server.wm.AccessibilityWindowsPopulator.lambda$dump$2(printWriter, str, i, i2);
            }
        };
        com.android.internal.util.DumpUtils.ValueDumper valueDumper = new com.android.internal.util.DumpUtils.ValueDumper() { // from class: com.android.server.wm.AccessibilityWindowsPopulator$$ExternalSyntheticLambda3
            public final void dump(java.lang.Object obj) {
                printWriter.print((android.view.MagnificationSpec) obj);
            }
        };
        com.android.internal.util.DumpUtils.dumpSparseArray(printWriter, str2, this.mDisplayInfos, "display info", keyDumper, new com.android.internal.util.DumpUtils.ValueDumper() { // from class: com.android.server.wm.AccessibilityWindowsPopulator$$ExternalSyntheticLambda4
            public final void dump(java.lang.Object obj) {
                printWriter.print((android.window.WindowInfosListener.DisplayInfo) obj);
            }
        });
        com.android.internal.util.DumpUtils.dumpSparseArray(printWriter, str2, this.mInputWindowHandlesOnDisplays, "window handles on display", keyDumper2, new com.android.internal.util.DumpUtils.ValueDumper() { // from class: com.android.server.wm.AccessibilityWindowsPopulator$$ExternalSyntheticLambda5
            public final void dump(java.lang.Object obj) {
                printWriter.print((java.util.List) obj);
            }
        });
        com.android.internal.util.DumpUtils.dumpSparseArray(printWriter, str2, this.mMagnificationSpecInverseMatrix, "magnification spec matrix", keyDumper, new com.android.internal.util.DumpUtils.ValueDumper() { // from class: com.android.server.wm.AccessibilityWindowsPopulator$$ExternalSyntheticLambda6
            public final void dump(java.lang.Object obj) {
                ((android.graphics.Matrix) obj).dump(printWriter);
            }
        });
        com.android.internal.util.DumpUtils.dumpSparseArray(printWriter, str2, this.mCurrentMagnificationSpec, "current magnification spec", keyDumper, valueDumper);
        com.android.internal.util.DumpUtils.dumpSparseArray(printWriter, str2, this.mPreviousMagnificationSpec, "previous magnification spec", keyDumper, valueDumper);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$dump$1(int i, int i2) {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$dump$2(java.io.PrintWriter printWriter, java.lang.String str, int i, int i2) {
        printWriter.printf("%sDisplay #%d: ", str, java.lang.Integer.valueOf(i2));
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void releaseResources() {
        this.mInputWindowHandlesOnDisplays.clear();
        this.mMagnificationSpecInverseMatrix.clear();
        this.mVisibleWindows.clear();
        this.mDisplayInfos.clear();
        this.mCurrentMagnificationSpec.clear();
        this.mPreviousMagnificationSpec.clear();
        this.mWindowsTransformMatrixMap.clear();
        this.mWindowsNotificationEnabled = false;
        this.mHandler.removeCallbacksAndMessages(null);
    }

    private class MyHandler extends android.os.Handler {
        public static final int MESSAGE_NOTIFY_WINDOWS_CHANGED = 1;
        public static final int MESSAGE_NOTIFY_WINDOWS_CHANGED_BY_TIMEOUT = 3;
        public static final int MESSAGE_NOTIFY_WINDOWS_CHANGED_BY_UI_STABLE = 2;

        MyHandler(android.os.Looper looper) {
            super(looper, null, false);
        }

        @Override // android.os.Handler
        public void handleMessage(android.os.Message message) {
            switch (message.what) {
                case 1:
                    com.android.server.wm.AccessibilityWindowsPopulator.this.notifyWindowsChanged((java.util.List) message.obj);
                    break;
                case 2:
                    com.android.server.wm.AccessibilityWindowsPopulator.this.forceUpdateWindows();
                    break;
                case 3:
                    android.util.Slog.w(com.android.server.wm.AccessibilityWindowsPopulator.TAG, "Windows change within in 2 frames continuously over 500 ms and notify windows changed immediately");
                    com.android.server.wm.AccessibilityWindowsPopulator.this.mHandler.removeMessages(2);
                    com.android.server.wm.AccessibilityWindowsPopulator.this.forceUpdateWindows();
                    break;
            }
        }
    }

    public static class AccessibilityWindow {
        private int mDisplayId;
        private boolean mIgnoreDuetoRecentsAnimation;
        private int mInputConfig;
        private boolean mIsFocused;
        private boolean mIsPIPMenu;
        private int mPrivateFlags;
        private boolean mShouldMagnify;
        private int mType;
        private android.os.IBinder mWindow;
        private android.view.WindowInfo mWindowInfo;
        private final android.graphics.Region mTouchableRegionInScreen = new android.graphics.Region();
        private final android.graphics.Region mTouchableRegionInWindow = new android.graphics.Region();
        private android.graphics.Rect mSystemBarInsetFrame = null;

        public static com.android.server.wm.AccessibilityWindowsPopulator.AccessibilityWindow initializeData(com.android.server.wm.WindowManagerService windowManagerService, android.view.InputWindowHandle inputWindowHandle, android.graphics.Matrix matrix, android.os.IBinder iBinder, android.graphics.Matrix matrix2) {
            android.os.IBinder windowToken = inputWindowHandle.getWindowToken();
            com.android.server.wm.WindowState windowState = windowToken != null ? windowManagerService.mWindowMap.get(windowToken) : null;
            com.android.server.wm.AccessibilityWindowsPopulator.AccessibilityWindow accessibilityWindow = new com.android.server.wm.AccessibilityWindowsPopulator.AccessibilityWindow();
            accessibilityWindow.mWindow = windowToken;
            accessibilityWindow.mDisplayId = inputWindowHandle.displayId;
            accessibilityWindow.mInputConfig = inputWindowHandle.inputConfig;
            accessibilityWindow.mType = inputWindowHandle.layoutParamsType;
            accessibilityWindow.mIsPIPMenu = windowToken != null && windowToken.equals(iBinder);
            accessibilityWindow.mPrivateFlags = windowState != null ? windowState.mAttrs.privateFlags : 0;
            accessibilityWindow.mIsFocused = windowState != null && windowState.isFocused();
            accessibilityWindow.mShouldMagnify = windowState == null || windowState.shouldMagnify();
            com.android.server.wm.RecentsAnimationController recentsAnimationController = windowManagerService.getRecentsAnimationController();
            accessibilityWindow.mIgnoreDuetoRecentsAnimation = (windowState == null || recentsAnimationController == null || !recentsAnimationController.shouldIgnoreForAccessibility(windowState)) ? false : true;
            getTouchableRegionInWindow(accessibilityWindow.mShouldMagnify, inputWindowHandle.touchableRegion, accessibilityWindow.mTouchableRegionInWindow, new android.graphics.Rect(inputWindowHandle.frame), matrix, matrix2);
            getUnMagnifiedTouchableRegion(accessibilityWindow.mShouldMagnify, inputWindowHandle.touchableRegion, accessibilityWindow.mTouchableRegionInScreen, matrix, matrix2);
            accessibilityWindow.mWindowInfo = windowState != null ? windowState.getWindowInfo() : getWindowInfoForWindowlessWindows(accessibilityWindow);
            android.graphics.Matrix matrix3 = new android.graphics.Matrix();
            inputWindowHandle.transform.invert(matrix3);
            matrix3.postConcat(matrix2);
            matrix3.getValues(accessibilityWindow.mWindowInfo.mTransformMatrix);
            android.graphics.Matrix matrix4 = new android.graphics.Matrix();
            if (accessibilityWindow.shouldMagnify() && matrix != null && !matrix.isIdentity()) {
                if (matrix.invert(matrix4)) {
                    matrix4.getValues(com.android.server.wm.AccessibilityWindowsPopulator.sTempFloats);
                    android.view.MagnificationSpec magnificationSpec = accessibilityWindow.mWindowInfo.mMagnificationSpec;
                    magnificationSpec.scale = com.android.server.wm.AccessibilityWindowsPopulator.sTempFloats[0];
                    magnificationSpec.offsetX = com.android.server.wm.AccessibilityWindowsPopulator.sTempFloats[2];
                    magnificationSpec.offsetY = com.android.server.wm.AccessibilityWindowsPopulator.sTempFloats[5];
                } else {
                    android.util.Slog.w(com.android.server.wm.AccessibilityWindowsPopulator.TAG, "can't find spec");
                }
            }
            com.android.server.accessibility.Flags.computeWindowChangesOnA11y();
            return accessibilityWindow;
        }

        public void getTouchableRegionInScreen(android.graphics.Region region) {
            region.set(this.mTouchableRegionInScreen);
        }

        public void getTouchableRegionInWindow(android.graphics.Region region) {
            region.set(this.mTouchableRegionInWindow);
        }

        public int getType() {
            return this.mType;
        }

        public int getPrivateFlag() {
            return this.mPrivateFlags;
        }

        public android.view.WindowInfo getWindowInfo() {
            return this.mWindowInfo;
        }

        public boolean shouldMagnify() {
            return this.mShouldMagnify;
        }

        public boolean isFocused() {
            return this.mIsFocused;
        }

        public boolean ignoreRecentsAnimationForAccessibility() {
            return this.mIgnoreDuetoRecentsAnimation;
        }

        public boolean isTrustedOverlay() {
            return (this.mInputConfig & 256) != 0;
        }

        public boolean isTouchable() {
            return (this.mInputConfig & 8) == 0;
        }

        public boolean isUntouchableNavigationBar() {
            if (this.mType != 2019) {
                return false;
            }
            return this.mTouchableRegionInScreen.isEmpty();
        }

        public boolean isPIPMenu() {
            return this.mIsPIPMenu;
        }

        @android.annotation.Nullable
        public android.graphics.Rect getSystemBarInsetsFrame() {
            return this.mSystemBarInsetFrame;
        }

        private static void getTouchableRegionInWindow(boolean z, android.graphics.Region region, android.graphics.Region region2, android.graphics.Rect rect, android.graphics.Matrix matrix, android.graphics.Matrix matrix2) {
            android.graphics.Region region3 = new android.graphics.Region();
            region3.set(region);
            region3.op(rect, android.graphics.Region.Op.INTERSECT);
            getUnMagnifiedTouchableRegion(z, region3, region2, matrix, matrix2);
        }

        private static void getUnMagnifiedTouchableRegion(boolean z, android.graphics.Region region, final android.graphics.Region region2, final android.graphics.Matrix matrix, final android.graphics.Matrix matrix2) {
            if ((!z || matrix.isIdentity()) && matrix2.isIdentity()) {
                region2.set(region);
            } else {
                com.android.server.wm.utils.RegionUtils.forEachRect(region, new java.util.function.Consumer() { // from class: com.android.server.wm.AccessibilityWindowsPopulator$AccessibilityWindow$$ExternalSyntheticLambda0
                    @Override // java.util.function.Consumer
                    public final void accept(java.lang.Object obj) {
                        com.android.server.wm.AccessibilityWindowsPopulator.AccessibilityWindow.lambda$getUnMagnifiedTouchableRegion$0(matrix2, matrix, region2, (android.graphics.Rect) obj);
                    }
                });
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ void lambda$getUnMagnifiedTouchableRegion$0(android.graphics.Matrix matrix, android.graphics.Matrix matrix2, android.graphics.Region region, android.graphics.Rect rect) {
            android.graphics.RectF rectF = new android.graphics.RectF(rect);
            matrix.mapRect(rectF);
            matrix2.mapRect(rectF);
            region.union(new android.graphics.Rect((int) rectF.left, (int) rectF.top, (int) rectF.right, (int) rectF.bottom));
        }

        private static android.view.WindowInfo getWindowInfoForWindowlessWindows(com.android.server.wm.AccessibilityWindowsPopulator.AccessibilityWindow accessibilityWindow) {
            android.view.WindowInfo obtain = android.view.WindowInfo.obtain();
            obtain.displayId = accessibilityWindow.mDisplayId;
            obtain.type = accessibilityWindow.mType;
            obtain.token = accessibilityWindow.mWindow;
            obtain.hasFlagWatchOutsideTouch = (accessibilityWindow.mInputConfig & 512) != 0;
            obtain.inPictureInPicture = accessibilityWindow.mIsPIPMenu;
            return obtain;
        }

        public java.lang.String toString() {
            return "A11yWindow=[" + (this.mWindow != null ? this.mWindow.toString() : "(no window token)") + ", displayId=" + this.mDisplayId + ", inputConfig=0x" + java.lang.Integer.toHexString(this.mInputConfig) + ", type=" + this.mType + ", privateFlag=0x" + java.lang.Integer.toHexString(this.mPrivateFlags) + ", focused=" + this.mIsFocused + ", shouldMagnify=" + this.mShouldMagnify + ", ignoreDuetoRecentsAnimation=" + this.mIgnoreDuetoRecentsAnimation + ", isTrustedOverlay=" + isTrustedOverlay() + ", regionInScreen=" + this.mTouchableRegionInScreen + ", touchableRegion=" + this.mTouchableRegionInWindow + ", isPIPMenu=" + this.mIsPIPMenu + ", windowInfo=" + this.mWindowInfo + "]";
        }
    }
}
