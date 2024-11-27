package android.os;

/* loaded from: classes3.dex */
public final class BugreportManager {
    private static final java.lang.String TAG = "BugreportManager";
    private final android.os.IDumpstate mBinder;
    private final android.content.Context mContext;

    public BugreportManager(android.content.Context context, android.os.IDumpstate iDumpstate) {
        this.mContext = context;
        this.mBinder = iDumpstate;
    }

    public static abstract class BugreportCallback {
        public static final int BUGREPORT_ERROR_ANOTHER_REPORT_IN_PROGRESS = 5;
        public static final int BUGREPORT_ERROR_INVALID_INPUT = 1;
        public static final int BUGREPORT_ERROR_NO_BUGREPORT_TO_RETRIEVE = 6;
        public static final int BUGREPORT_ERROR_RUNTIME = 2;
        public static final int BUGREPORT_ERROR_USER_CONSENT_TIMED_OUT = 4;
        public static final int BUGREPORT_ERROR_USER_DENIED_CONSENT = 3;

        @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
        public @interface BugreportErrorCode {
        }

        public void onProgress(float f) {
        }

        public void onError(int i) {
        }

        public void onFinished() {
        }

        @android.annotation.SystemApi
        public void onFinished(java.lang.String str) {
        }

        public void onEarlyReportFinished() {
        }
    }

    @android.annotation.SystemApi
    public void preDumpUiData() {
        try {
            this.mBinder.preDumpUiData(this.mContext.getOpPackageName());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    /* JADX WARN: Can't wrap try/catch for region: R(12:2|3|(1:5)(1:33)|(8:9|(2:11|12)(1:31)|13|14|15|(1:20)|17|18)|32|(0)(0)|13|14|15|(0)|17|18) */
    /* JADX WARN: Code restructure failed: missing block: B:22:0x0069, code lost:
    
        r0 = e;
     */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x0089, code lost:
    
        throw r0.rethrowFromSystemServer();
     */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x0067, code lost:
    
        r0 = e;
     */
    /* JADX WARN: Code restructure failed: missing block: B:26:0x0072, code lost:
    
        android.util.Log.wtf(android.os.BugreportManager.TAG, "Not able to find /dev/null file: ", r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x0079, code lost:
    
        libcore.io.IoUtils.closeQuietly(r18);
     */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x007c, code lost:
    
        if (r16 == null) goto L47;
     */
    /* JADX WARN: Code restructure failed: missing block: B:29:?, code lost:
    
        return;
     */
    /* JADX WARN: Removed duplicated region for block: B:11:0x0026 A[Catch: all -> 0x006b, FileNotFoundException -> 0x006f, RemoteException -> 0x0082, TRY_LEAVE, TryCatch #4 {RemoteException -> 0x0082, FileNotFoundException -> 0x006f, all -> 0x006b, blocks: (B:3:0x0002, B:11:0x0026), top: B:2:0x0002 }] */
    /* JADX WARN: Removed duplicated region for block: B:20:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:31:0x0036  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x0090  */
    @android.annotation.SystemApi
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void startBugreport(android.os.ParcelFileDescriptor parcelFileDescriptor, android.os.ParcelFileDescriptor parcelFileDescriptor2, android.os.BugreportParams bugreportParams, java.util.concurrent.Executor executor, android.os.BugreportManager.BugreportCallback bugreportCallback) {
        android.os.ParcelFileDescriptor parcelFileDescriptor3;
        boolean z;
        boolean z2;
        try {
            try {
                com.android.internal.util.Preconditions.checkNotNull(parcelFileDescriptor);
                com.android.internal.util.Preconditions.checkNotNull(bugreportParams);
                com.android.internal.util.Preconditions.checkNotNull(executor);
                com.android.internal.util.Preconditions.checkNotNull(bugreportCallback);
                z = (bugreportParams.getFlags() & 2) != 0;
            } catch (android.os.RemoteException e) {
                e = e;
            } catch (java.io.FileNotFoundException e2) {
                e = e2;
                parcelFileDescriptor3 = parcelFileDescriptor2;
            } catch (java.lang.Throwable th) {
                th = th;
                libcore.io.IoUtils.closeQuietly(parcelFileDescriptor);
                if (parcelFileDescriptor2 != null) {
                }
                throw th;
            }
            if (parcelFileDescriptor2 == null && !z) {
                z2 = false;
                parcelFileDescriptor3 = parcelFileDescriptor2 != null ? android.os.ParcelFileDescriptor.open(new java.io.File("/dev/null"), 268435456) : parcelFileDescriptor2;
                this.mBinder.startBugreport(-1, this.mContext.getOpPackageName(), parcelFileDescriptor.getFileDescriptor(), parcelFileDescriptor3.getFileDescriptor(), bugreportParams.getMode(), bugreportParams.getFlags(), new android.os.BugreportManager.DumpstateListener(executor, bugreportCallback, z2, z), z2);
                libcore.io.IoUtils.closeQuietly(parcelFileDescriptor);
                if (parcelFileDescriptor3 == null) {
                    return;
                }
                libcore.io.IoUtils.closeQuietly(parcelFileDescriptor3);
            }
            z2 = true;
            if (parcelFileDescriptor2 != null) {
            }
            this.mBinder.startBugreport(-1, this.mContext.getOpPackageName(), parcelFileDescriptor.getFileDescriptor(), parcelFileDescriptor3.getFileDescriptor(), bugreportParams.getMode(), bugreportParams.getFlags(), new android.os.BugreportManager.DumpstateListener(executor, bugreportCallback, z2, z), z2);
            libcore.io.IoUtils.closeQuietly(parcelFileDescriptor);
            if (parcelFileDescriptor3 == null) {
            }
            libcore.io.IoUtils.closeQuietly(parcelFileDescriptor3);
        } catch (java.lang.Throwable th2) {
            th = th2;
            libcore.io.IoUtils.closeQuietly(parcelFileDescriptor);
            if (parcelFileDescriptor2 != null) {
                libcore.io.IoUtils.closeQuietly(parcelFileDescriptor2);
            }
            throw th;
        }
    }

    @android.annotation.SystemApi
    public void retrieveBugreport(java.lang.String str, android.os.ParcelFileDescriptor parcelFileDescriptor, java.util.concurrent.Executor executor, android.os.BugreportManager.BugreportCallback bugreportCallback) {
        try {
            try {
                com.android.internal.util.Preconditions.checkNotNull(str);
                com.android.internal.util.Preconditions.checkNotNull(parcelFileDescriptor);
                com.android.internal.util.Preconditions.checkNotNull(executor);
                com.android.internal.util.Preconditions.checkNotNull(bugreportCallback);
                this.mBinder.retrieveBugreport(android.os.Binder.getCallingUid(), this.mContext.getOpPackageName(), this.mContext.getUserId(), parcelFileDescriptor.getFileDescriptor(), str, false, new android.os.BugreportManager.DumpstateListener(executor, bugreportCallback, false, false));
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        } finally {
            libcore.io.IoUtils.closeQuietly(parcelFileDescriptor);
        }
    }

    public void startConnectivityBugreport(android.os.ParcelFileDescriptor parcelFileDescriptor, java.util.concurrent.Executor executor, android.os.BugreportManager.BugreportCallback bugreportCallback) {
        startBugreport(parcelFileDescriptor, null, new android.os.BugreportParams(4), executor, bugreportCallback);
    }

    public void cancelBugreport() {
        try {
            this.mBinder.cancelBugreport(-1, this.mContext.getOpPackageName());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public void requestBugreport(android.os.BugreportParams bugreportParams, java.lang.CharSequence charSequence, java.lang.CharSequence charSequence2) {
        java.lang.String charSequence3;
        java.lang.String str = null;
        if (charSequence == null) {
            charSequence3 = null;
        } else {
            try {
                charSequence3 = charSequence.toString();
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        if (charSequence2 != null) {
            str = charSequence2.toString();
        }
        android.app.ActivityManager.getService().requestBugReportWithDescription(charSequence3, str, bugreportParams.getMode());
    }

    /* JADX INFO: Access modifiers changed from: private */
    final class DumpstateListener extends android.os.IDumpstateListener.Stub {
        private final android.os.BugreportManager.BugreportCallback mCallback;
        private final java.util.concurrent.Executor mExecutor;
        private final boolean mIsConsentDeferred;
        private final boolean mIsScreenshotRequested;

        DumpstateListener(java.util.concurrent.Executor executor, android.os.BugreportManager.BugreportCallback bugreportCallback, boolean z, boolean z2) {
            this.mExecutor = executor;
            this.mCallback = bugreportCallback;
            this.mIsScreenshotRequested = z;
            this.mIsConsentDeferred = z2;
        }

        @Override // android.os.IDumpstateListener
        public void onProgress(final int i) throws android.os.RemoteException {
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                this.mExecutor.execute(new java.lang.Runnable() { // from class: android.os.BugreportManager$DumpstateListener$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.os.BugreportManager.DumpstateListener.this.lambda$onProgress$0(i);
                    }
                });
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onProgress$0(int i) {
            this.mCallback.onProgress(i);
        }

        @Override // android.os.IDumpstateListener
        public void onError(final int i) throws android.os.RemoteException {
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                this.mExecutor.execute(new java.lang.Runnable() { // from class: android.os.BugreportManager$DumpstateListener$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.os.BugreportManager.DumpstateListener.this.lambda$onError$1(i);
                    }
                });
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onError$1(int i) {
            this.mCallback.onError(i);
        }

        @Override // android.os.IDumpstateListener
        public void onFinished(final java.lang.String str) throws android.os.RemoteException {
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                if (this.mIsConsentDeferred) {
                    this.mExecutor.execute(new java.lang.Runnable() { // from class: android.os.BugreportManager$DumpstateListener$$ExternalSyntheticLambda2
                        @Override // java.lang.Runnable
                        public final void run() {
                            android.os.BugreportManager.DumpstateListener.this.lambda$onFinished$2(str);
                        }
                    });
                } else {
                    this.mExecutor.execute(new java.lang.Runnable() { // from class: android.os.BugreportManager$DumpstateListener$$ExternalSyntheticLambda3
                        @Override // java.lang.Runnable
                        public final void run() {
                            android.os.BugreportManager.DumpstateListener.this.lambda$onFinished$3();
                        }
                    });
                }
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onFinished$2(java.lang.String str) {
            this.mCallback.onFinished(str);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onFinished$3() {
            this.mCallback.onFinished();
        }

        @Override // android.os.IDumpstateListener
        public void onScreenshotTaken(final boolean z) throws android.os.RemoteException {
            if (!this.mIsScreenshotRequested) {
                return;
            }
            new android.os.Handler(android.os.Looper.getMainLooper()).post(new java.lang.Runnable() { // from class: android.os.BugreportManager$DumpstateListener$$ExternalSyntheticLambda5
                @Override // java.lang.Runnable
                public final void run() {
                    android.os.BugreportManager.DumpstateListener.this.lambda$onScreenshotTaken$4(z);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onScreenshotTaken$4(boolean z) {
            int i;
            if (z) {
                i = com.android.internal.R.string.bugreport_screenshot_success_toast;
            } else {
                i = com.android.internal.R.string.bugreport_screenshot_failure_toast;
            }
            android.widget.Toast.makeText(android.os.BugreportManager.this.mContext, i, 1).show();
        }

        @Override // android.os.IDumpstateListener
        public void onUiIntensiveBugreportDumpsFinished() throws android.os.RemoteException {
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                this.mExecutor.execute(new java.lang.Runnable() { // from class: android.os.BugreportManager$DumpstateListener$$ExternalSyntheticLambda4
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.os.BugreportManager.DumpstateListener.this.lambda$onUiIntensiveBugreportDumpsFinished$5();
                    }
                });
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onUiIntensiveBugreportDumpsFinished$5() {
            this.mCallback.onEarlyReportFinished();
        }
    }
}
