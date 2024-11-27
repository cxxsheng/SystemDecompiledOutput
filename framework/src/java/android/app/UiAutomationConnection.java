package android.app;

/* loaded from: classes.dex */
public final class UiAutomationConnection extends android.app.IUiAutomationConnection.Stub {
    private static final int INITIAL_FROZEN_ROTATION_UNSPECIFIED = -1;
    private static final java.lang.String TAG = "UiAutomationConnection";
    private android.accessibilityservice.IAccessibilityServiceClient mClient;
    private boolean mIsShutdown;
    private int mOwningUid;
    private final android.view.IWindowManager mWindowManager = android.view.IWindowManager.Stub.asInterface(android.os.ServiceManager.getService(android.content.Context.WINDOW_SERVICE));
    private final android.view.accessibility.IAccessibilityManager mAccessibilityManager = android.view.accessibility.IAccessibilityManager.Stub.asInterface(android.os.ServiceManager.getService(android.content.Context.ACCESSIBILITY_SERVICE));
    private final android.permission.IPermissionManager mPermissionManager = android.permission.IPermissionManager.Stub.asInterface(android.os.ServiceManager.getService("permissionmgr"));
    private final android.app.IActivityManager mActivityManager = android.app.IActivityManager.Stub.asInterface(android.os.ServiceManager.getService("activity"));
    private final java.lang.Object mLock = new java.lang.Object();
    private final android.os.Binder mToken = new android.os.Binder();
    private int mInitialFrozenRotation = -1;

    public UiAutomationConnection() {
        android.util.Log.d(TAG, "Created on user " + android.os.Process.myUserHandle());
    }

    @Override // android.app.IUiAutomationConnection
    public void connect(android.accessibilityservice.IAccessibilityServiceClient iAccessibilityServiceClient, int i) {
        if (iAccessibilityServiceClient == null) {
            throw new java.lang.IllegalArgumentException("Client cannot be null!");
        }
        synchronized (this.mLock) {
            throwIfShutdownLocked();
            if (isConnectedLocked()) {
                throw new java.lang.IllegalStateException("Already connected.");
            }
            this.mOwningUid = android.os.Binder.getCallingUid();
            registerUiTestAutomationServiceLocked(iAccessibilityServiceClient, android.os.Binder.getCallingUserHandle().getIdentifier(), i);
            storeRotationStateLocked();
        }
    }

    @Override // android.app.IUiAutomationConnection
    public void disconnect() {
        synchronized (this.mLock) {
            throwIfCalledByNotTrustedUidLocked();
            throwIfShutdownLocked();
            if (!isConnectedLocked()) {
                throw new java.lang.IllegalStateException("Already disconnected.");
            }
            this.mOwningUid = -1;
            unregisterUiTestAutomationServiceLocked();
            restoreRotationStateLocked();
        }
    }

    @Override // android.app.IUiAutomationConnection
    public boolean injectInputEvent(android.view.InputEvent inputEvent, boolean z, boolean z2) {
        boolean z3;
        synchronized (this.mLock) {
            throwIfCalledByNotTrustedUidLocked();
            throwIfShutdownLocked();
            throwIfNotConnectedLocked();
        }
        boolean z4 = true;
        if (inputEvent instanceof android.view.KeyEvent) {
            android.view.KeyEvent keyEvent = (android.view.KeyEvent) inputEvent;
            z3 = keyEvent.getAction() == 0;
            if (keyEvent.getAction() != 1) {
                z4 = false;
            }
        } else {
            android.view.MotionEvent motionEvent = (android.view.MotionEvent) inputEvent;
            z3 = motionEvent.getAction() == 0 || motionEvent.isFromSource(8194);
            if (motionEvent.getAction() != 1) {
                z4 = false;
            }
        }
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            if (z3) {
                this.mWindowManager.syncInputTransactions(z2);
            }
            boolean injectInputEvent = android.hardware.input.InputManagerGlobal.getInstance().injectInputEvent(inputEvent, z ? 2 : 0);
            if (z4) {
                this.mWindowManager.syncInputTransactions(z2);
            }
            return injectInputEvent;
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
            return false;
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    @Override // android.app.IUiAutomationConnection
    public void injectInputEventToInputFilter(android.view.InputEvent inputEvent) throws android.os.RemoteException {
        synchronized (this.mLock) {
            throwIfCalledByNotTrustedUidLocked();
            throwIfShutdownLocked();
            throwIfNotConnectedLocked();
        }
        this.mAccessibilityManager.injectInputEventToInputFilter(inputEvent);
    }

    @Override // android.app.IUiAutomationConnection
    public void syncInputTransactions(boolean z) {
        synchronized (this.mLock) {
            throwIfCalledByNotTrustedUidLocked();
            throwIfShutdownLocked();
            throwIfNotConnectedLocked();
        }
        try {
            this.mWindowManager.syncInputTransactions(z);
        } catch (android.os.RemoteException e) {
        }
    }

    @Override // android.app.IUiAutomationConnection
    public boolean setRotation(int i) {
        synchronized (this.mLock) {
            throwIfCalledByNotTrustedUidLocked();
            throwIfShutdownLocked();
            throwIfNotConnectedLocked();
        }
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            if (i == -2) {
                this.mWindowManager.thawRotation("UiAutomationConnection#setRotation");
            } else {
                this.mWindowManager.freezeRotation(i, "UiAutomationConnection#setRotation");
            }
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            return true;
        } catch (android.os.RemoteException e) {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            return false;
        } catch (java.lang.Throwable th) {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    @Override // android.app.IUiAutomationConnection
    public boolean takeScreenshot(android.graphics.Rect rect, android.window.ScreenCapture.ScreenCaptureListener screenCaptureListener) {
        synchronized (this.mLock) {
            throwIfCalledByNotTrustedUidLocked();
            throwIfShutdownLocked();
            throwIfNotConnectedLocked();
        }
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            try {
                this.mWindowManager.captureDisplay(0, new android.window.ScreenCapture.CaptureArgs.Builder().setSourceCrop(rect).build(), screenCaptureListener);
            } catch (android.os.RemoteException e) {
                e.rethrowAsRuntimeException();
            }
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            return true;
        } catch (java.lang.Throwable th) {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    @Override // android.app.IUiAutomationConnection
    public boolean takeSurfaceControlScreenshot(android.view.SurfaceControl surfaceControl, android.window.ScreenCapture.ScreenCaptureListener screenCaptureListener) {
        synchronized (this.mLock) {
            throwIfCalledByNotTrustedUidLocked();
            throwIfShutdownLocked();
            throwIfNotConnectedLocked();
        }
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            if (android.window.ScreenCapture.captureLayers(new android.window.ScreenCapture.LayerCaptureArgs.Builder(surfaceControl).setChildrenOnly(false).build(), screenCaptureListener) != 0) {
                return false;
            }
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            return true;
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    @Override // android.app.IUiAutomationConnection
    public boolean clearWindowContentFrameStats(int i) throws android.os.RemoteException {
        synchronized (this.mLock) {
            throwIfCalledByNotTrustedUidLocked();
            throwIfShutdownLocked();
            throwIfNotConnectedLocked();
        }
        int callingUserId = android.os.UserHandle.getCallingUserId();
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            android.os.IBinder windowToken = this.mAccessibilityManager.getWindowToken(i, callingUserId);
            if (windowToken != null) {
                return this.mWindowManager.clearWindowContentFrameStats(windowToken);
            }
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            return false;
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    @Override // android.app.IUiAutomationConnection
    public android.view.WindowContentFrameStats getWindowContentFrameStats(int i) throws android.os.RemoteException {
        synchronized (this.mLock) {
            throwIfCalledByNotTrustedUidLocked();
            throwIfShutdownLocked();
            throwIfNotConnectedLocked();
        }
        int callingUserId = android.os.UserHandle.getCallingUserId();
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            android.os.IBinder windowToken = this.mAccessibilityManager.getWindowToken(i, callingUserId);
            if (windowToken != null) {
                return this.mWindowManager.getWindowContentFrameStats(windowToken);
            }
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            return null;
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    @Override // android.app.IUiAutomationConnection
    public void clearWindowAnimationFrameStats() {
        synchronized (this.mLock) {
            throwIfCalledByNotTrustedUidLocked();
            throwIfShutdownLocked();
            throwIfNotConnectedLocked();
        }
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            android.view.SurfaceControl.clearAnimationFrameStats();
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    @Override // android.app.IUiAutomationConnection
    public android.view.WindowAnimationFrameStats getWindowAnimationFrameStats() {
        synchronized (this.mLock) {
            throwIfCalledByNotTrustedUidLocked();
            throwIfShutdownLocked();
            throwIfNotConnectedLocked();
        }
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            android.view.WindowAnimationFrameStats windowAnimationFrameStats = new android.view.WindowAnimationFrameStats();
            android.view.SurfaceControl.getAnimationFrameStats(windowAnimationFrameStats);
            return windowAnimationFrameStats;
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    @Override // android.app.IUiAutomationConnection
    public void grantRuntimePermission(java.lang.String str, java.lang.String str2, int i) throws android.os.RemoteException {
        synchronized (this.mLock) {
            throwIfCalledByNotTrustedUidLocked();
            throwIfShutdownLocked();
            throwIfNotConnectedLocked();
        }
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            this.mPermissionManager.grantRuntimePermission(str, str2, android.companion.virtual.VirtualDeviceManager.PERSISTENT_DEVICE_ID_DEFAULT, i);
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    @Override // android.app.IUiAutomationConnection
    public void revokeRuntimePermission(java.lang.String str, java.lang.String str2, int i) throws android.os.RemoteException {
        synchronized (this.mLock) {
            throwIfCalledByNotTrustedUidLocked();
            throwIfShutdownLocked();
            throwIfNotConnectedLocked();
        }
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            this.mPermissionManager.revokeRuntimePermission(str, str2, android.companion.virtual.VirtualDeviceManager.PERSISTENT_DEVICE_ID_DEFAULT, i, null);
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    @Override // android.app.IUiAutomationConnection
    public void adoptShellPermissionIdentity(int i, java.lang.String[] strArr) throws android.os.RemoteException {
        synchronized (this.mLock) {
            throwIfCalledByNotTrustedUidLocked();
            throwIfShutdownLocked();
            throwIfNotConnectedLocked();
        }
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            this.mActivityManager.startDelegateShellPermissionIdentity(i, strArr);
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    @Override // android.app.IUiAutomationConnection
    public void dropShellPermissionIdentity() throws android.os.RemoteException {
        synchronized (this.mLock) {
            throwIfCalledByNotTrustedUidLocked();
            throwIfShutdownLocked();
            throwIfNotConnectedLocked();
        }
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            this.mActivityManager.stopDelegateShellPermissionIdentity();
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    @Override // android.app.IUiAutomationConnection
    public java.util.List<java.lang.String> getAdoptedShellPermissions() throws android.os.RemoteException {
        synchronized (this.mLock) {
            throwIfCalledByNotTrustedUidLocked();
            throwIfShutdownLocked();
            throwIfNotConnectedLocked();
        }
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            return this.mActivityManager.getDelegatedShellPermissions();
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public class Repeater implements java.lang.Runnable {
        private final java.io.InputStream readFrom;
        private final java.io.OutputStream writeTo;

        public Repeater(java.io.InputStream inputStream, java.io.OutputStream outputStream) {
            this.readFrom = inputStream;
            this.writeTo = outputStream;
        }

        @Override // java.lang.Runnable
        public void run() {
            try {
                byte[] bArr = new byte[8192];
                while (true) {
                    int read = this.readFrom.read(bArr);
                    if (read < 0) {
                        break;
                    }
                    this.writeTo.write(bArr, 0, read);
                    this.writeTo.flush();
                }
            } catch (java.io.IOException e) {
            } catch (java.lang.Throwable th) {
                libcore.io.IoUtils.closeQuietly(this.readFrom);
                libcore.io.IoUtils.closeQuietly(this.writeTo);
                throw th;
            }
            libcore.io.IoUtils.closeQuietly(this.readFrom);
            libcore.io.IoUtils.closeQuietly(this.writeTo);
        }
    }

    @Override // android.app.IUiAutomationConnection
    public void executeShellCommand(java.lang.String str, android.os.ParcelFileDescriptor parcelFileDescriptor, android.os.ParcelFileDescriptor parcelFileDescriptor2) throws android.os.RemoteException {
        executeShellCommandWithStderr(str, parcelFileDescriptor, parcelFileDescriptor2, null);
    }

    @Override // android.app.IUiAutomationConnection
    public void executeShellCommandWithStderr(java.lang.String str, final android.os.ParcelFileDescriptor parcelFileDescriptor, final android.os.ParcelFileDescriptor parcelFileDescriptor2, final android.os.ParcelFileDescriptor parcelFileDescriptor3) throws android.os.RemoteException {
        final java.lang.Thread thread;
        final java.lang.Thread thread2;
        final java.lang.Thread thread3;
        synchronized (this.mLock) {
            throwIfCalledByNotTrustedUidLocked();
            throwIfShutdownLocked();
            throwIfNotConnectedLocked();
        }
        try {
            final java.lang.Process exec = java.lang.Runtime.getRuntime().exec(str);
            if (parcelFileDescriptor != null) {
                java.lang.Thread thread4 = new java.lang.Thread(new android.app.UiAutomationConnection.Repeater(exec.getInputStream(), new java.io.FileOutputStream(parcelFileDescriptor.getFileDescriptor())));
                thread4.start();
                thread = thread4;
            } else {
                thread = null;
            }
            if (parcelFileDescriptor2 != null) {
                java.lang.Thread thread5 = new java.lang.Thread(new android.app.UiAutomationConnection.Repeater(new java.io.FileInputStream(parcelFileDescriptor2.getFileDescriptor()), exec.getOutputStream()));
                thread5.start();
                thread2 = thread5;
            } else {
                thread2 = null;
            }
            if (parcelFileDescriptor3 != null) {
                java.lang.Thread thread6 = new java.lang.Thread(new android.app.UiAutomationConnection.Repeater(exec.getErrorStream(), new java.io.FileOutputStream(parcelFileDescriptor3.getFileDescriptor())));
                thread6.start();
                thread3 = thread6;
            } else {
                thread3 = null;
            }
            new java.lang.Thread(new java.lang.Runnable() { // from class: android.app.UiAutomationConnection.1
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        if (thread2 != null) {
                            thread2.join();
                        }
                        if (thread != null) {
                            thread.join();
                        }
                        if (thread3 != null) {
                            thread3.join();
                        }
                    } catch (java.lang.InterruptedException e) {
                        android.util.Log.e(android.app.UiAutomationConnection.TAG, "At least one of the threads was interrupted");
                    }
                    libcore.io.IoUtils.closeQuietly(parcelFileDescriptor);
                    libcore.io.IoUtils.closeQuietly(parcelFileDescriptor2);
                    libcore.io.IoUtils.closeQuietly(parcelFileDescriptor3);
                    exec.destroy();
                }
            }).start();
        } catch (java.io.IOException e) {
            throw new java.lang.RuntimeException("Error running shell command '" + str + "'", e);
        }
    }

    @Override // android.app.IUiAutomationConnection
    public void shutdown() {
        synchronized (this.mLock) {
            if (isConnectedLocked()) {
                throwIfCalledByNotTrustedUidLocked();
            }
            throwIfShutdownLocked();
            this.mIsShutdown = true;
            if (isConnectedLocked()) {
                disconnect();
            }
        }
    }

    private void registerUiTestAutomationServiceLocked(android.accessibilityservice.IAccessibilityServiceClient iAccessibilityServiceClient, int i, int i2) {
        android.view.accessibility.IAccessibilityManager asInterface = android.view.accessibility.IAccessibilityManager.Stub.asInterface(android.os.ServiceManager.getService(android.content.Context.ACCESSIBILITY_SERVICE));
        android.accessibilityservice.AccessibilityServiceInfo accessibilityServiceInfo = new android.accessibilityservice.AccessibilityServiceInfo();
        accessibilityServiceInfo.eventTypes = -1;
        accessibilityServiceInfo.feedbackType = 16;
        accessibilityServiceInfo.flags |= 65554;
        accessibilityServiceInfo.setCapabilities(11);
        if ((i2 & 4) == 0) {
            accessibilityServiceInfo.setAccessibilityTool(true);
        }
        try {
            asInterface.registerUiTestAutomationService(this.mToken, iAccessibilityServiceClient, accessibilityServiceInfo, i, i2);
            this.mClient = iAccessibilityServiceClient;
        } catch (android.os.RemoteException e) {
            throw new java.lang.IllegalStateException("Error while registering UiTestAutomationService for user " + i + android.media.MediaMetrics.SEPARATOR, e);
        }
    }

    private void unregisterUiTestAutomationServiceLocked() {
        try {
            android.view.accessibility.IAccessibilityManager.Stub.asInterface(android.os.ServiceManager.getService(android.content.Context.ACCESSIBILITY_SERVICE)).unregisterUiTestAutomationService(this.mClient);
            this.mClient = null;
        } catch (android.os.RemoteException e) {
            throw new java.lang.IllegalStateException("Error while unregistering UiTestAutomationService", e);
        }
    }

    private void storeRotationStateLocked() {
        try {
            if (this.mWindowManager.isRotationFrozen()) {
                this.mInitialFrozenRotation = this.mWindowManager.getDefaultDisplayRotation();
            }
        } catch (android.os.RemoteException e) {
        }
    }

    private void restoreRotationStateLocked() {
        try {
            if (this.mInitialFrozenRotation != -1) {
                this.mWindowManager.freezeRotation(this.mInitialFrozenRotation, "UiAutomationConnection#restoreRotationStateLocked");
            } else {
                this.mWindowManager.thawRotation("UiAutomationConnection#restoreRotationStateLocked");
            }
        } catch (android.os.RemoteException e) {
        }
    }

    private boolean isConnectedLocked() {
        return this.mClient != null;
    }

    private void throwIfShutdownLocked() {
        if (this.mIsShutdown) {
            throw new java.lang.IllegalStateException("Connection shutdown!");
        }
    }

    private void throwIfNotConnectedLocked() {
        if (!isConnectedLocked()) {
            throw new java.lang.IllegalStateException("Not connected!");
        }
    }

    private void throwIfCalledByNotTrustedUidLocked() {
        int callingUid = android.os.Binder.getCallingUid();
        if (callingUid != this.mOwningUid && this.mOwningUid != 1000 && callingUid != 0) {
            throw new java.lang.SecurityException("Calling from not trusted UID!");
        }
    }
}
