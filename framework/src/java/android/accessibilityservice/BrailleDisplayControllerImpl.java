package android.accessibilityservice;

/* loaded from: classes.dex */
public final class BrailleDisplayControllerImpl implements android.accessibilityservice.BrailleDisplayController {
    private static final boolean IS_HIDRAW_SUPPORTED = android.os.SystemProperties.getBoolean("ro.accessibility.support_hidraw", true);
    private final android.accessibilityservice.AccessibilityService mAccessibilityService;
    private android.accessibilityservice.IBrailleDisplayConnection mBrailleDisplayConnection;
    private android.accessibilityservice.BrailleDisplayController.BrailleDisplayCallback mCallback;
    private java.util.concurrent.Executor mCallbackExecutor;
    private final boolean mIsHidrawSupported;
    private final java.lang.Object mLock;

    BrailleDisplayControllerImpl(android.accessibilityservice.AccessibilityService accessibilityService, java.lang.Object obj) {
        this(accessibilityService, obj, IS_HIDRAW_SUPPORTED);
    }

    public BrailleDisplayControllerImpl(android.accessibilityservice.AccessibilityService accessibilityService, java.lang.Object obj, boolean z) {
        this.mAccessibilityService = accessibilityService;
        this.mLock = obj;
        this.mIsHidrawSupported = z;
    }

    @Override // android.accessibilityservice.BrailleDisplayController
    public void connect(android.bluetooth.BluetoothDevice bluetoothDevice, android.accessibilityservice.BrailleDisplayController.BrailleDisplayCallback brailleDisplayCallback) {
        connect(bluetoothDevice, this.mAccessibilityService.getMainExecutor(), brailleDisplayCallback);
    }

    @Override // android.accessibilityservice.BrailleDisplayController
    public void connect(final android.bluetooth.BluetoothDevice bluetoothDevice, java.util.concurrent.Executor executor, android.accessibilityservice.BrailleDisplayController.BrailleDisplayCallback brailleDisplayCallback) {
        java.util.Objects.requireNonNull(bluetoothDevice);
        java.util.Objects.requireNonNull(executor);
        java.util.Objects.requireNonNull(brailleDisplayCallback);
        connect(new com.android.internal.util.FunctionalUtils.RemoteExceptionIgnoringConsumer() { // from class: android.accessibilityservice.BrailleDisplayControllerImpl$$ExternalSyntheticLambda1
            @Override // com.android.internal.util.FunctionalUtils.RemoteExceptionIgnoringConsumer
            public final void acceptOrThrow(java.lang.Object obj) {
                android.accessibilityservice.BrailleDisplayControllerImpl.this.lambda$connect$0(bluetoothDevice, (android.accessibilityservice.IAccessibilityServiceConnection) obj);
            }
        }, executor, brailleDisplayCallback);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$connect$0(android.bluetooth.BluetoothDevice bluetoothDevice, android.accessibilityservice.IAccessibilityServiceConnection iAccessibilityServiceConnection) throws android.os.RemoteException {
        iAccessibilityServiceConnection.connectBluetoothBrailleDisplay(bluetoothDevice.getAddress(), new android.accessibilityservice.BrailleDisplayControllerImpl.IBrailleDisplayControllerWrapper());
    }

    @Override // android.accessibilityservice.BrailleDisplayController
    public void connect(android.hardware.usb.UsbDevice usbDevice, android.accessibilityservice.BrailleDisplayController.BrailleDisplayCallback brailleDisplayCallback) {
        connect(usbDevice, this.mAccessibilityService.getMainExecutor(), brailleDisplayCallback);
    }

    @Override // android.accessibilityservice.BrailleDisplayController
    public void connect(final android.hardware.usb.UsbDevice usbDevice, java.util.concurrent.Executor executor, android.accessibilityservice.BrailleDisplayController.BrailleDisplayCallback brailleDisplayCallback) {
        java.util.Objects.requireNonNull(usbDevice);
        java.util.Objects.requireNonNull(executor);
        java.util.Objects.requireNonNull(brailleDisplayCallback);
        connect(new com.android.internal.util.FunctionalUtils.RemoteExceptionIgnoringConsumer() { // from class: android.accessibilityservice.BrailleDisplayControllerImpl$$ExternalSyntheticLambda2
            @Override // com.android.internal.util.FunctionalUtils.RemoteExceptionIgnoringConsumer
            public final void acceptOrThrow(java.lang.Object obj) {
                android.accessibilityservice.BrailleDisplayControllerImpl.this.lambda$connect$1(usbDevice, (android.accessibilityservice.IAccessibilityServiceConnection) obj);
            }
        }, executor, brailleDisplayCallback);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$connect$1(android.hardware.usb.UsbDevice usbDevice, android.accessibilityservice.IAccessibilityServiceConnection iAccessibilityServiceConnection) throws android.os.RemoteException {
        iAccessibilityServiceConnection.connectUsbBrailleDisplay(usbDevice, new android.accessibilityservice.BrailleDisplayControllerImpl.IBrailleDisplayControllerWrapper());
    }

    private void connect(com.android.internal.util.FunctionalUtils.RemoteExceptionIgnoringConsumer<android.accessibilityservice.IAccessibilityServiceConnection> remoteExceptionIgnoringConsumer, java.util.concurrent.Executor executor, final android.accessibilityservice.BrailleDisplayController.BrailleDisplayCallback brailleDisplayCallback) {
        android.accessibilityservice.BrailleDisplayController.checkApiFlagIsEnabled();
        if (!this.mIsHidrawSupported) {
            executor.execute(new java.lang.Runnable() { // from class: android.accessibilityservice.BrailleDisplayControllerImpl$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    android.accessibilityservice.BrailleDisplayController.BrailleDisplayCallback.this.onConnectionFailed(1);
                }
            });
            return;
        }
        if (isConnected()) {
            throw new java.lang.IllegalStateException("This service already has a connected Braille display");
        }
        android.accessibilityservice.IAccessibilityServiceConnection connection = android.view.accessibility.AccessibilityInteractionClient.getConnection(this.mAccessibilityService.getConnectionId());
        if (connection == null) {
            throw new java.lang.IllegalStateException("Accessibility service is not connected");
        }
        synchronized (this.mLock) {
            this.mCallbackExecutor = executor;
            this.mCallback = brailleDisplayCallback;
        }
        try {
            remoteExceptionIgnoringConsumer.acceptOrThrow(connection);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.accessibilityservice.BrailleDisplayController
    public boolean isConnected() {
        android.accessibilityservice.BrailleDisplayController.checkApiFlagIsEnabled();
        return this.mBrailleDisplayConnection != null;
    }

    @Override // android.accessibilityservice.BrailleDisplayController
    public void write(byte[] bArr) throws java.io.IOException {
        android.accessibilityservice.BrailleDisplayController.checkApiFlagIsEnabled();
        java.util.Objects.requireNonNull(bArr);
        if (bArr.length > android.os.IBinder.getSuggestedMaxIpcSizeBytes()) {
            throw new java.lang.IllegalArgumentException("Invalid write buffer size " + bArr.length);
        }
        synchronized (this.mLock) {
            if (this.mBrailleDisplayConnection == null) {
                throw new java.io.IOException("Braille display is not connected");
            }
            try {
                this.mBrailleDisplayConnection.write(bArr);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    @Override // android.accessibilityservice.BrailleDisplayController
    public void disconnect() {
        android.accessibilityservice.BrailleDisplayController.checkApiFlagIsEnabled();
        synchronized (this.mLock) {
            try {
                try {
                    if (this.mBrailleDisplayConnection != null) {
                        this.mBrailleDisplayConnection.disconnect();
                    }
                } catch (android.os.RemoteException e) {
                    throw e.rethrowFromSystemServer();
                }
            } finally {
                clearConnectionLocked();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    final class IBrailleDisplayControllerWrapper extends android.accessibilityservice.IBrailleDisplayController.Stub {
        private IBrailleDisplayControllerWrapper() {
        }

        @Override // android.accessibilityservice.IBrailleDisplayController
        public void onConnected(android.accessibilityservice.IBrailleDisplayConnection iBrailleDisplayConnection, final byte[] bArr) {
            android.accessibilityservice.BrailleDisplayController.checkApiFlagIsEnabled();
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                synchronized (android.accessibilityservice.BrailleDisplayControllerImpl.this.mLock) {
                    android.accessibilityservice.BrailleDisplayControllerImpl.this.mBrailleDisplayConnection = iBrailleDisplayConnection;
                    android.accessibilityservice.BrailleDisplayControllerImpl.this.mCallbackExecutor.execute(new java.lang.Runnable() { // from class: android.accessibilityservice.BrailleDisplayControllerImpl$IBrailleDisplayControllerWrapper$$ExternalSyntheticLambda3
                        @Override // java.lang.Runnable
                        public final void run() {
                            android.accessibilityservice.BrailleDisplayControllerImpl.IBrailleDisplayControllerWrapper.this.lambda$onConnected$0(bArr);
                        }
                    });
                }
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onConnected$0(byte[] bArr) {
            android.accessibilityservice.BrailleDisplayControllerImpl.this.mCallback.onConnected(bArr);
        }

        @Override // android.accessibilityservice.IBrailleDisplayController
        public void onConnectionFailed(final int i) {
            android.accessibilityservice.BrailleDisplayController.checkApiFlagIsEnabled();
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                synchronized (android.accessibilityservice.BrailleDisplayControllerImpl.this.mLock) {
                    android.accessibilityservice.BrailleDisplayControllerImpl.this.mCallbackExecutor.execute(new java.lang.Runnable() { // from class: android.accessibilityservice.BrailleDisplayControllerImpl$IBrailleDisplayControllerWrapper$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            android.accessibilityservice.BrailleDisplayControllerImpl.IBrailleDisplayControllerWrapper.this.lambda$onConnectionFailed$1(i);
                        }
                    });
                }
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onConnectionFailed$1(int i) {
            android.accessibilityservice.BrailleDisplayControllerImpl.this.mCallback.onConnectionFailed(i);
        }

        @Override // android.accessibilityservice.IBrailleDisplayController
        public void onInput(final byte[] bArr) {
            android.accessibilityservice.BrailleDisplayController.checkApiFlagIsEnabled();
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                synchronized (android.accessibilityservice.BrailleDisplayControllerImpl.this.mLock) {
                    if (android.accessibilityservice.BrailleDisplayControllerImpl.this.mBrailleDisplayConnection != null) {
                        android.accessibilityservice.BrailleDisplayControllerImpl.this.mCallbackExecutor.execute(new java.lang.Runnable() { // from class: android.accessibilityservice.BrailleDisplayControllerImpl$IBrailleDisplayControllerWrapper$$ExternalSyntheticLambda2
                            @Override // java.lang.Runnable
                            public final void run() {
                                android.accessibilityservice.BrailleDisplayControllerImpl.IBrailleDisplayControllerWrapper.this.lambda$onInput$2(bArr);
                            }
                        });
                    }
                }
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onInput$2(byte[] bArr) {
            android.accessibilityservice.BrailleDisplayControllerImpl.this.mCallback.onInput(bArr);
        }

        @Override // android.accessibilityservice.IBrailleDisplayController
        public void onDisconnected() {
            android.accessibilityservice.BrailleDisplayController.checkApiFlagIsEnabled();
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                synchronized (android.accessibilityservice.BrailleDisplayControllerImpl.this.mLock) {
                    java.util.concurrent.Executor executor = android.accessibilityservice.BrailleDisplayControllerImpl.this.mCallbackExecutor;
                    final android.accessibilityservice.BrailleDisplayController.BrailleDisplayCallback brailleDisplayCallback = android.accessibilityservice.BrailleDisplayControllerImpl.this.mCallback;
                    java.util.Objects.requireNonNull(brailleDisplayCallback);
                    executor.execute(new java.lang.Runnable() { // from class: android.accessibilityservice.BrailleDisplayControllerImpl$IBrailleDisplayControllerWrapper$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() {
                            android.accessibilityservice.BrailleDisplayController.BrailleDisplayCallback.this.onDisconnected();
                        }
                    });
                    android.accessibilityservice.BrailleDisplayControllerImpl.this.clearConnectionLocked();
                }
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void clearConnectionLocked() {
        this.mBrailleDisplayConnection = null;
    }
}
