package com.android.server.am;

/* loaded from: classes.dex */
public class AssistDataRequester extends android.app.IAssistDataReceiver.Stub {
    public static final java.lang.String KEY_RECEIVER_EXTRA_COUNT = "count";
    public static final java.lang.String KEY_RECEIVER_EXTRA_INDEX = "index";
    private android.app.AppOpsManager mAppOpsManager;
    private com.android.server.am.AssistDataRequester.AssistDataRequesterCallbacks mCallbacks;
    private java.lang.Object mCallbacksLock;
    private boolean mCanceled;
    private android.content.Context mContext;
    private int mPendingDataCount;
    private int mPendingScreenshotCount;
    private int mRequestScreenshotAppOps;
    private int mRequestStructureAppOps;
    private android.view.IWindowManager mWindowManager;
    private final java.util.ArrayList<android.os.Bundle> mAssistData = new java.util.ArrayList<>();
    private final java.util.ArrayList<android.graphics.Bitmap> mAssistScreenshot = new java.util.ArrayList<>();

    @com.android.internal.annotations.VisibleForTesting
    public android.app.IActivityTaskManager mActivityTaskManager = android.app.ActivityTaskManager.getService();

    public interface AssistDataRequesterCallbacks {
        @com.android.internal.annotations.GuardedBy({"mCallbacksLock"})
        boolean canHandleReceivedAssistDataLocked();

        @com.android.internal.annotations.GuardedBy({"mCallbacksLock"})
        default void onAssistDataReceivedLocked(android.os.Bundle bundle, int i, int i2) {
        }

        @com.android.internal.annotations.GuardedBy({"mCallbacksLock"})
        default void onAssistScreenshotReceivedLocked(android.graphics.Bitmap bitmap) {
        }

        @com.android.internal.annotations.GuardedBy({"mCallbacksLock"})
        default void onAssistRequestCompleted() {
        }
    }

    public AssistDataRequester(android.content.Context context, android.view.IWindowManager iWindowManager, android.app.AppOpsManager appOpsManager, com.android.server.am.AssistDataRequester.AssistDataRequesterCallbacks assistDataRequesterCallbacks, java.lang.Object obj, int i, int i2) {
        this.mCallbacks = assistDataRequesterCallbacks;
        this.mCallbacksLock = obj;
        this.mWindowManager = iWindowManager;
        this.mContext = context;
        this.mAppOpsManager = appOpsManager;
        this.mRequestStructureAppOps = i;
        this.mRequestScreenshotAppOps = i2;
    }

    public void requestAssistData(@android.annotation.NonNull java.util.List<android.os.IBinder> list, boolean z, boolean z2, boolean z3, boolean z4, int i, @android.annotation.NonNull java.lang.String str, @android.annotation.Nullable java.lang.String str2) {
        requestAssistData(list, z, z2, true, z3, z4, false, i, str, str2);
    }

    public void requestAssistData(@android.annotation.NonNull java.util.List<android.os.IBinder> list, boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6, int i, @android.annotation.NonNull java.lang.String str, @android.annotation.Nullable java.lang.String str2) {
        requestData(list, false, z, z2, z3, z4, z5, z6, i, str, str2);
    }

    private void requestData(@android.annotation.NonNull java.util.List<android.os.IBinder> list, boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6, boolean z7, int i, @android.annotation.NonNull java.lang.String str, @android.annotation.Nullable java.lang.String str2) {
        boolean z8;
        boolean z9;
        int i2;
        boolean z10;
        boolean z11;
        boolean requestAssistContextExtras;
        java.util.Objects.requireNonNull(list);
        java.util.Objects.requireNonNull(str);
        if (list.isEmpty()) {
            tryDispatchRequestComplete();
            return;
        }
        boolean z12 = false;
        try {
            z8 = this.mActivityTaskManager.isAssistDataAllowed();
        } catch (android.os.RemoteException e) {
            z8 = false;
        }
        boolean z13 = z5 & z8;
        if (!z2 || !z8 || this.mRequestScreenshotAppOps == -1) {
            z9 = false;
        } else {
            z9 = true;
        }
        boolean z14 = z6 & z9;
        this.mCanceled = false;
        this.mPendingDataCount = 0;
        this.mPendingScreenshotCount = 0;
        this.mAssistData.clear();
        this.mAssistScreenshot.clear();
        if (z2) {
            if (this.mAppOpsManager.noteOpNoThrow(this.mRequestStructureAppOps, i, str, str2, (java.lang.String) null) == 0 && z13) {
                int size = list.size();
                int i3 = 0;
                while (true) {
                    if (i3 >= size) {
                        z12 = z14;
                        break;
                    }
                    android.os.IBinder iBinder = list.get(i3);
                    try {
                        com.android.internal.logging.MetricsLogger.count(this.mContext, "assist_with_context", 1);
                        android.os.Bundle bundle = new android.os.Bundle();
                        bundle.putInt(KEY_RECEIVER_EXTRA_INDEX, i3);
                        bundle.putInt(KEY_RECEIVER_EXTRA_COUNT, size);
                        if (z) {
                            requestAssistContextExtras = this.mActivityTaskManager.requestAutofillData(this, bundle, iBinder, 0);
                        } else {
                            if (z4) {
                                i2 = 1;
                            } else {
                                i2 = 3;
                            }
                            android.app.IActivityTaskManager iActivityTaskManager = this.mActivityTaskManager;
                            if (i3 != 0 || z7) {
                                z10 = false;
                            } else {
                                z10 = true;
                            }
                            if (i3 != 0) {
                                z11 = false;
                            } else {
                                z11 = true;
                            }
                            requestAssistContextExtras = iActivityTaskManager.requestAssistContextExtras(i2, this, bundle, iBinder, z10, z11);
                        }
                        if (requestAssistContextExtras) {
                            this.mPendingDataCount++;
                        } else if (i3 == 0) {
                            if (!this.mCallbacks.canHandleReceivedAssistDataLocked()) {
                                this.mAssistData.add(null);
                            } else {
                                dispatchAssistDataReceived(null);
                            }
                        }
                    } catch (android.os.RemoteException e2) {
                    }
                    i3++;
                }
            } else if (!this.mCallbacks.canHandleReceivedAssistDataLocked()) {
                this.mAssistData.add(null);
            } else {
                dispatchAssistDataReceived(null);
            }
        } else {
            z12 = z14;
        }
        if (z3) {
            if (this.mAppOpsManager.noteOpNoThrow(this.mRequestScreenshotAppOps, i, str, str2, (java.lang.String) null) == 0 && z12) {
                try {
                    com.android.internal.logging.MetricsLogger.count(this.mContext, "assist_with_screen", 1);
                    this.mPendingScreenshotCount++;
                    this.mWindowManager.requestAssistScreenshot(this);
                } catch (android.os.RemoteException e3) {
                }
            } else if (!this.mCallbacks.canHandleReceivedAssistDataLocked()) {
                this.mAssistScreenshot.add(null);
            } else {
                dispatchAssistScreenshotReceived(null);
            }
        }
        tryDispatchRequestComplete();
    }

    public void processPendingAssistData() {
        flushPendingAssistData();
        tryDispatchRequestComplete();
    }

    private void flushPendingAssistData() {
        int size = this.mAssistData.size();
        for (int i = 0; i < size; i++) {
            dispatchAssistDataReceived(this.mAssistData.get(i));
        }
        this.mAssistData.clear();
        int size2 = this.mAssistScreenshot.size();
        for (int i2 = 0; i2 < size2; i2++) {
            dispatchAssistScreenshotReceived(this.mAssistScreenshot.get(i2));
        }
        this.mAssistScreenshot.clear();
    }

    public int getPendingDataCount() {
        return this.mPendingDataCount;
    }

    public int getPendingScreenshotCount() {
        return this.mPendingScreenshotCount;
    }

    public void cancel() {
        this.mCanceled = true;
        this.mPendingDataCount = 0;
        this.mPendingScreenshotCount = 0;
        this.mAssistData.clear();
        this.mAssistScreenshot.clear();
    }

    public void onHandleAssistData(android.os.Bundle bundle) {
        synchronized (this.mCallbacksLock) {
            try {
                if (this.mCanceled) {
                    return;
                }
                this.mPendingDataCount--;
                if (this.mCallbacks.canHandleReceivedAssistDataLocked()) {
                    flushPendingAssistData();
                    dispatchAssistDataReceived(bundle);
                    tryDispatchRequestComplete();
                } else {
                    this.mAssistData.add(bundle);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void onHandleAssistScreenshot(android.graphics.Bitmap bitmap) {
        synchronized (this.mCallbacksLock) {
            try {
                if (this.mCanceled) {
                    return;
                }
                this.mPendingScreenshotCount--;
                if (this.mCallbacks.canHandleReceivedAssistDataLocked()) {
                    flushPendingAssistData();
                    dispatchAssistScreenshotReceived(bitmap);
                    tryDispatchRequestComplete();
                } else {
                    this.mAssistScreenshot.add(bitmap);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private void dispatchAssistDataReceived(android.os.Bundle bundle) {
        int i;
        int i2;
        android.os.Bundle bundle2 = bundle != null ? bundle.getBundle(com.android.server.wm.ActivityTaskManagerInternal.ASSIST_KEY_RECEIVER_EXTRAS) : null;
        if (bundle2 == null) {
            i = 0;
            i2 = 0;
        } else {
            i = bundle2.getInt(KEY_RECEIVER_EXTRA_INDEX);
            i2 = bundle2.getInt(KEY_RECEIVER_EXTRA_COUNT);
        }
        this.mCallbacks.onAssistDataReceivedLocked(bundle, i, i2);
    }

    private void dispatchAssistScreenshotReceived(android.graphics.Bitmap bitmap) {
        this.mCallbacks.onAssistScreenshotReceivedLocked(bitmap);
    }

    private void tryDispatchRequestComplete() {
        if (this.mPendingDataCount == 0 && this.mPendingScreenshotCount == 0 && this.mAssistData.isEmpty() && this.mAssistScreenshot.isEmpty()) {
            this.mCallbacks.onAssistRequestCompleted();
        }
    }

    public void dump(java.lang.String str, java.io.PrintWriter printWriter) {
        printWriter.print(str);
        printWriter.print("mPendingDataCount=");
        printWriter.println(this.mPendingDataCount);
        printWriter.print(str);
        printWriter.print("mAssistData=");
        printWriter.println(this.mAssistData);
        printWriter.print(str);
        printWriter.print("mPendingScreenshotCount=");
        printWriter.println(this.mPendingScreenshotCount);
        printWriter.print(str);
        printWriter.print("mAssistScreenshot=");
        printWriter.println(this.mAssistScreenshot);
    }
}
