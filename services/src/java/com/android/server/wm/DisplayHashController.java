package com.android.server.wm;

/* loaded from: classes3.dex */
public class DisplayHashController {
    private static final boolean DEBUG = false;
    private static final java.lang.String TAG = "WindowManager";
    private final android.content.Context mContext;

    @com.android.internal.annotations.GuardedBy({"mDisplayHashAlgorithmsLock"})
    private java.util.Map<java.lang.String, android.service.displayhash.DisplayHashParams> mDisplayHashAlgorithms;
    private long mLastRequestTimeMs;
    private int mLastRequestUid;

    @com.android.internal.annotations.GuardedBy({"mServiceConnectionLock"})
    private com.android.server.wm.DisplayHashController.DisplayHashingServiceConnection mServiceConnection;
    private final java.lang.Object mServiceConnectionLock = new java.lang.Object();
    private final java.lang.Object mDisplayHashAlgorithmsLock = new java.lang.Object();
    private final float[] mTmpFloat9 = new float[9];
    private final android.graphics.Matrix mTmpMatrix = new android.graphics.Matrix();
    private final android.graphics.RectF mTmpRectF = new android.graphics.RectF();
    private final java.lang.Object mIntervalBetweenRequestsLock = new java.lang.Object();

    @com.android.internal.annotations.GuardedBy({"mDurationBetweenRequestsLock"})
    private int mIntervalBetweenRequestMillis = -1;
    private boolean mDisplayHashThrottlingEnabled = true;
    private final com.android.server.wm.DisplayHashController.Handler mHandler = new com.android.server.wm.DisplayHashController.Handler(android.os.Looper.getMainLooper());
    private final byte[] mSalt = java.util.UUID.randomUUID().toString().getBytes();

    /* JADX INFO: Access modifiers changed from: private */
    interface Command {
        void run(android.service.displayhash.IDisplayHashingService iDisplayHashingService) throws android.os.RemoteException;
    }

    DisplayHashController(android.content.Context context) {
        this.mContext = context;
    }

    java.lang.String[] getSupportedHashAlgorithms() {
        return (java.lang.String[]) getDisplayHashAlgorithms().keySet().toArray(new java.lang.String[0]);
    }

    @android.annotation.Nullable
    android.view.displayhash.VerifiedDisplayHash verifyDisplayHash(final android.view.displayhash.DisplayHash displayHash) {
        return (android.view.displayhash.VerifiedDisplayHash) new com.android.server.wm.DisplayHashController.SyncCommand().run(new java.util.function.BiConsumer() { // from class: com.android.server.wm.DisplayHashController$$ExternalSyntheticLambda2
            @Override // java.util.function.BiConsumer
            public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                com.android.server.wm.DisplayHashController.this.lambda$verifyDisplayHash$0(displayHash, (android.service.displayhash.IDisplayHashingService) obj, (android.os.RemoteCallback) obj2);
            }
        }).getParcelable("android.service.displayhash.extra.VERIFIED_DISPLAY_HASH");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$verifyDisplayHash$0(android.view.displayhash.DisplayHash displayHash, android.service.displayhash.IDisplayHashingService iDisplayHashingService, android.os.RemoteCallback remoteCallback) {
        try {
            iDisplayHashingService.verifyDisplayHash(this.mSalt, displayHash, remoteCallback);
        } catch (android.os.RemoteException e) {
            android.util.Slog.e(TAG, "Failed to invoke verifyDisplayHash command");
        }
    }

    void setDisplayHashThrottlingEnabled(boolean z) {
        this.mDisplayHashThrottlingEnabled = z;
    }

    private void generateDisplayHash(final android.hardware.HardwareBuffer hardwareBuffer, final android.graphics.Rect rect, final java.lang.String str, final android.os.RemoteCallback remoteCallback) {
        connectAndRun(new com.android.server.wm.DisplayHashController.Command() { // from class: com.android.server.wm.DisplayHashController$$ExternalSyntheticLambda0
            @Override // com.android.server.wm.DisplayHashController.Command
            public final void run(android.service.displayhash.IDisplayHashingService iDisplayHashingService) {
                com.android.server.wm.DisplayHashController.this.lambda$generateDisplayHash$1(hardwareBuffer, rect, str, remoteCallback, iDisplayHashingService);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$generateDisplayHash$1(android.hardware.HardwareBuffer hardwareBuffer, android.graphics.Rect rect, java.lang.String str, android.os.RemoteCallback remoteCallback, android.service.displayhash.IDisplayHashingService iDisplayHashingService) throws android.os.RemoteException {
        iDisplayHashingService.generateDisplayHash(this.mSalt, hardwareBuffer, rect, str, remoteCallback);
    }

    private boolean allowedToGenerateHash(int i) {
        if (!this.mDisplayHashThrottlingEnabled) {
            return true;
        }
        long currentTimeMillis = java.lang.System.currentTimeMillis();
        if (this.mLastRequestUid != i) {
            this.mLastRequestUid = i;
            this.mLastRequestTimeMs = currentTimeMillis;
            return true;
        }
        if (currentTimeMillis - this.mLastRequestTimeMs < getIntervalBetweenRequestMillis()) {
            return false;
        }
        this.mLastRequestTimeMs = currentTimeMillis;
        return true;
    }

    void generateDisplayHash(android.window.ScreenCapture.LayerCaptureArgs.Builder builder, android.graphics.Rect rect, java.lang.String str, int i, android.os.RemoteCallback remoteCallback) {
        if (!allowedToGenerateHash(i)) {
            sendDisplayHashError(remoteCallback, -6);
            return;
        }
        android.service.displayhash.DisplayHashParams displayHashParams = getDisplayHashAlgorithms().get(str);
        if (displayHashParams == null) {
            android.util.Slog.w(TAG, "Failed to generateDisplayHash. Invalid hashAlgorithm");
            sendDisplayHashError(remoteCallback, -5);
            return;
        }
        android.util.Size bufferSize = displayHashParams.getBufferSize();
        if (bufferSize != null && (bufferSize.getWidth() > 0 || bufferSize.getHeight() > 0)) {
            builder.setFrameScale(bufferSize.getWidth() / rect.width(), bufferSize.getHeight() / rect.height());
        }
        builder.setGrayscale(displayHashParams.isGrayscaleBuffer());
        android.window.ScreenCapture.ScreenshotHardwareBuffer captureLayers = android.window.ScreenCapture.captureLayers(builder.build());
        if (captureLayers == null || captureLayers.getHardwareBuffer() == null) {
            android.util.Slog.w(TAG, "Failed to generate DisplayHash. Couldn't capture content");
            sendDisplayHashError(remoteCallback, -1);
        } else {
            generateDisplayHash(captureLayers.getHardwareBuffer(), rect, str, remoteCallback);
        }
    }

    private java.util.Map<java.lang.String, android.service.displayhash.DisplayHashParams> getDisplayHashAlgorithms() {
        synchronized (this.mDisplayHashAlgorithmsLock) {
            try {
                if (this.mDisplayHashAlgorithms != null) {
                    return this.mDisplayHashAlgorithms;
                }
                android.os.Bundle run = new com.android.server.wm.DisplayHashController.SyncCommand().run(new java.util.function.BiConsumer() { // from class: com.android.server.wm.DisplayHashController$$ExternalSyntheticLambda3
                    @Override // java.util.function.BiConsumer
                    public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                        com.android.server.wm.DisplayHashController.lambda$getDisplayHashAlgorithms$2((android.service.displayhash.IDisplayHashingService) obj, (android.os.RemoteCallback) obj2);
                    }
                });
                this.mDisplayHashAlgorithms = new java.util.HashMap(run.size());
                for (java.lang.String str : run.keySet()) {
                    this.mDisplayHashAlgorithms.put(str, (android.service.displayhash.DisplayHashParams) run.getParcelable(str));
                }
                return this.mDisplayHashAlgorithms;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$getDisplayHashAlgorithms$2(android.service.displayhash.IDisplayHashingService iDisplayHashingService, android.os.RemoteCallback remoteCallback) {
        try {
            iDisplayHashingService.getDisplayHashAlgorithms(remoteCallback);
        } catch (android.os.RemoteException e) {
            android.util.Slog.e(TAG, "Failed to invoke getDisplayHashAlgorithms command", e);
        }
    }

    void sendDisplayHashError(android.os.RemoteCallback remoteCallback, int i) {
        android.os.Bundle bundle = new android.os.Bundle();
        bundle.putInt("DISPLAY_HASH_ERROR_CODE", i);
        remoteCallback.sendResult(bundle);
    }

    void calculateDisplayHashBoundsLocked(com.android.server.wm.WindowState windowState, android.graphics.Rect rect, android.graphics.Rect rect2) {
        rect2.set(rect);
        com.android.server.wm.DisplayContent displayContent = windowState.getDisplayContent();
        if (displayContent == null) {
            return;
        }
        android.graphics.Rect rect3 = new android.graphics.Rect();
        windowState.getBounds(rect3);
        rect3.offsetTo(0, 0);
        rect2.intersectUnchecked(rect3);
        if (rect2.isEmpty()) {
            return;
        }
        windowState.getTransformationMatrix(this.mTmpFloat9, this.mTmpMatrix);
        this.mTmpRectF.set(rect2);
        this.mTmpMatrix.mapRect(this.mTmpRectF, this.mTmpRectF);
        rect2.set((int) this.mTmpRectF.left, (int) this.mTmpRectF.top, (int) this.mTmpRectF.right, (int) this.mTmpRectF.bottom);
        android.view.MagnificationSpec magnificationSpec = displayContent.getMagnificationSpec();
        if (magnificationSpec != null) {
            rect2.scale(magnificationSpec.scale);
            rect2.offset((int) magnificationSpec.offsetX, (int) magnificationSpec.offsetY);
        }
        if (rect2.isEmpty()) {
            return;
        }
        rect2.intersectUnchecked(displayContent.getBounds());
    }

    private int getIntervalBetweenRequestMillis() {
        synchronized (this.mIntervalBetweenRequestsLock) {
            try {
                if (this.mIntervalBetweenRequestMillis != -1) {
                    return this.mIntervalBetweenRequestMillis;
                }
                this.mIntervalBetweenRequestMillis = new com.android.server.wm.DisplayHashController.SyncCommand().run(new java.util.function.BiConsumer() { // from class: com.android.server.wm.DisplayHashController$$ExternalSyntheticLambda1
                    @Override // java.util.function.BiConsumer
                    public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                        com.android.server.wm.DisplayHashController.lambda$getIntervalBetweenRequestMillis$3((android.service.displayhash.IDisplayHashingService) obj, (android.os.RemoteCallback) obj2);
                    }
                }).getInt("android.service.displayhash.extra.INTERVAL_BETWEEN_REQUESTS", 0);
                return this.mIntervalBetweenRequestMillis;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$getIntervalBetweenRequestMillis$3(android.service.displayhash.IDisplayHashingService iDisplayHashingService, android.os.RemoteCallback remoteCallback) {
        try {
            iDisplayHashingService.getIntervalBetweenRequestsMillis(remoteCallback);
        } catch (android.os.RemoteException e) {
            android.util.Slog.e(TAG, "Failed to invoke getDisplayHashAlgorithms command", e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void connectAndRun(@android.annotation.NonNull com.android.server.wm.DisplayHashController.Command command) {
        android.content.ComponentName serviceComponentName;
        synchronized (this.mServiceConnectionLock) {
            try {
                this.mHandler.resetTimeoutMessage();
                if (this.mServiceConnection == null && (serviceComponentName = getServiceComponentName()) != null) {
                    android.content.Intent intent = new android.content.Intent();
                    intent.setComponent(serviceComponentName);
                    long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                    try {
                        this.mServiceConnection = new com.android.server.wm.DisplayHashController.DisplayHashingServiceConnection();
                        this.mContext.bindService(intent, this.mServiceConnection, 1);
                        android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                    } catch (java.lang.Throwable th) {
                        android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                        throw th;
                    }
                }
                if (this.mServiceConnection != null) {
                    this.mServiceConnection.runCommandLocked(command);
                }
            } catch (java.lang.Throwable th2) {
                throw th2;
            }
        }
    }

    @android.annotation.Nullable
    private android.content.pm.ServiceInfo getServiceInfo() {
        java.lang.String servicesSystemSharedLibraryPackageName = this.mContext.getPackageManager().getServicesSystemSharedLibraryPackageName();
        if (servicesSystemSharedLibraryPackageName == null) {
            android.util.Slog.w(TAG, "no external services package!");
            return null;
        }
        android.content.Intent intent = new android.content.Intent("android.service.displayhash.DisplayHashingService");
        intent.setPackage(servicesSystemSharedLibraryPackageName);
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            android.content.pm.ResolveInfo resolveService = this.mContext.getPackageManager().resolveService(intent, 132);
            if (resolveService == null || resolveService.serviceInfo == null) {
                android.util.Slog.w(TAG, "No valid components found.");
                return null;
            }
            return resolveService.serviceInfo;
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    @android.annotation.Nullable
    private android.content.ComponentName getServiceComponentName() {
        android.content.pm.ServiceInfo serviceInfo = getServiceInfo();
        if (serviceInfo == null) {
            return null;
        }
        android.content.ComponentName componentName = new android.content.ComponentName(serviceInfo.packageName, serviceInfo.name);
        if (!"android.permission.BIND_DISPLAY_HASHING_SERVICE".equals(serviceInfo.permission)) {
            android.util.Slog.w(TAG, componentName.flattenToShortString() + " requires permission android.permission.BIND_DISPLAY_HASHING_SERVICE");
            return null;
        }
        return componentName;
    }

    /* JADX INFO: Access modifiers changed from: private */
    class SyncCommand {
        private static final int WAIT_TIME_S = 5;
        private final java.util.concurrent.CountDownLatch mCountDownLatch;
        private android.os.Bundle mResult;

        private SyncCommand() {
            this.mCountDownLatch = new java.util.concurrent.CountDownLatch(1);
        }

        public android.os.Bundle run(final java.util.function.BiConsumer<android.service.displayhash.IDisplayHashingService, android.os.RemoteCallback> biConsumer) {
            com.android.server.wm.DisplayHashController.this.connectAndRun(new com.android.server.wm.DisplayHashController.Command() { // from class: com.android.server.wm.DisplayHashController$SyncCommand$$ExternalSyntheticLambda1
                @Override // com.android.server.wm.DisplayHashController.Command
                public final void run(android.service.displayhash.IDisplayHashingService iDisplayHashingService) {
                    com.android.server.wm.DisplayHashController.SyncCommand.this.lambda$run$1(biConsumer, iDisplayHashingService);
                }
            });
            try {
                this.mCountDownLatch.await(5L, java.util.concurrent.TimeUnit.SECONDS);
            } catch (java.lang.Exception e) {
                android.util.Slog.e(com.android.server.wm.DisplayHashController.TAG, "Failed to wait for command", e);
            }
            return this.mResult;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$run$1(java.util.function.BiConsumer biConsumer, android.service.displayhash.IDisplayHashingService iDisplayHashingService) throws android.os.RemoteException {
            biConsumer.accept(iDisplayHashingService, new android.os.RemoteCallback(new android.os.RemoteCallback.OnResultListener() { // from class: com.android.server.wm.DisplayHashController$SyncCommand$$ExternalSyntheticLambda0
                public final void onResult(android.os.Bundle bundle) {
                    com.android.server.wm.DisplayHashController.SyncCommand.this.lambda$run$0(bundle);
                }
            }));
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$run$0(android.os.Bundle bundle) {
            this.mResult = bundle;
            this.mCountDownLatch.countDown();
        }
    }

    private class DisplayHashingServiceConnection implements android.content.ServiceConnection {

        @com.android.internal.annotations.GuardedBy({"mServiceConnectionLock"})
        private java.util.ArrayList<com.android.server.wm.DisplayHashController.Command> mQueuedCommands;

        @com.android.internal.annotations.GuardedBy({"mServiceConnectionLock"})
        private android.service.displayhash.IDisplayHashingService mRemoteService;

        private DisplayHashingServiceConnection() {
        }

        @Override // android.content.ServiceConnection
        public void onServiceConnected(android.content.ComponentName componentName, android.os.IBinder iBinder) {
            synchronized (com.android.server.wm.DisplayHashController.this.mServiceConnectionLock) {
                this.mRemoteService = android.service.displayhash.IDisplayHashingService.Stub.asInterface(iBinder);
                if (this.mQueuedCommands != null) {
                    int size = this.mQueuedCommands.size();
                    for (int i = 0; i < size; i++) {
                        try {
                            this.mQueuedCommands.get(i).run(this.mRemoteService);
                        } catch (android.os.RemoteException e) {
                            android.util.Slog.w(com.android.server.wm.DisplayHashController.TAG, "exception calling " + componentName + ": " + e);
                        }
                    }
                    this.mQueuedCommands = null;
                }
            }
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(android.content.ComponentName componentName) {
            synchronized (com.android.server.wm.DisplayHashController.this.mServiceConnectionLock) {
                this.mRemoteService = null;
            }
        }

        @Override // android.content.ServiceConnection
        public void onBindingDied(android.content.ComponentName componentName) {
            synchronized (com.android.server.wm.DisplayHashController.this.mServiceConnectionLock) {
                this.mRemoteService = null;
            }
        }

        @Override // android.content.ServiceConnection
        public void onNullBinding(android.content.ComponentName componentName) {
            synchronized (com.android.server.wm.DisplayHashController.this.mServiceConnectionLock) {
                this.mRemoteService = null;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void runCommandLocked(com.android.server.wm.DisplayHashController.Command command) {
            if (this.mRemoteService == null) {
                if (this.mQueuedCommands == null) {
                    this.mQueuedCommands = new java.util.ArrayList<>(1);
                }
                this.mQueuedCommands.add(command);
                return;
            }
            try {
                command.run(this.mRemoteService);
            } catch (android.os.RemoteException e) {
                android.util.Slog.w(com.android.server.wm.DisplayHashController.TAG, "exception calling service: " + e);
            }
        }
    }

    private class Handler extends android.os.Handler {
        static final int MSG_SERVICE_SHUTDOWN_TIMEOUT = 1;
        static final long SERVICE_SHUTDOWN_TIMEOUT_MILLIS = 10000;

        Handler(android.os.Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(android.os.Message message) {
            if (message.what == 1) {
                synchronized (com.android.server.wm.DisplayHashController.this.mServiceConnectionLock) {
                    try {
                        if (com.android.server.wm.DisplayHashController.this.mServiceConnection != null) {
                            com.android.server.wm.DisplayHashController.this.mContext.unbindService(com.android.server.wm.DisplayHashController.this.mServiceConnection);
                            com.android.server.wm.DisplayHashController.this.mServiceConnection = null;
                        }
                    } finally {
                    }
                }
            }
        }

        void resetTimeoutMessage() {
            removeMessages(1);
            sendEmptyMessageDelayed(1, 10000L);
        }
    }
}
