package com.android.server.adb;

/* loaded from: classes.dex */
public class AdbService extends android.debug.IAdbManager.Stub {
    static final java.lang.String ADBD = "adbd";
    static final java.lang.String CTL_START = "ctl.start";
    static final java.lang.String CTL_STOP = "ctl.stop";
    private static final boolean DEBUG = false;
    private static final java.lang.String TAG = "AdbService";
    private static final java.lang.String USB_PERSISTENT_CONFIG_PROPERTY = "persist.sys.usb.config";
    private static final java.lang.String WIFI_PERSISTENT_CONFIG_PROPERTY = "persist.adb.tls_server.enable";
    private final android.os.RemoteCallbackList<android.debug.IAdbCallback> mCallbacks;
    java.util.concurrent.atomic.AtomicInteger mConnectionPort;
    private com.android.server.adb.AdbDebuggingManager.AdbConnectionPortPoller mConnectionPortPoller;
    private final android.content.ContentResolver mContentResolver;
    private final android.content.Context mContext;
    private com.android.server.adb.AdbDebuggingManager mDebuggingManager;
    private boolean mIsAdbUsbEnabled;
    private boolean mIsAdbWifiEnabled;
    private android.database.ContentObserver mObserver;
    private final com.android.server.adb.AdbService.AdbConnectionPortListener mPortListener;
    private final android.util.ArrayMap<android.os.IBinder, android.debug.IAdbTransport> mTransports;

    public static class Lifecycle extends com.android.server.SystemService {
        private com.android.server.adb.AdbService mAdbService;

        public Lifecycle(android.content.Context context) {
            super(context);
        }

        @Override // com.android.server.SystemService
        public void onStart() {
            this.mAdbService = new com.android.server.adb.AdbService(getContext());
            publishBinderService(com.android.server.integrity.AppIntegrityManagerServiceImpl.ADB_INSTALLER, this.mAdbService);
        }

        @Override // com.android.server.SystemService
        public void onBootPhase(int i) {
            if (i == 550) {
                this.mAdbService.systemReady();
            } else if (i == 1000) {
                com.android.server.FgThread.getHandler().sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new java.util.function.Consumer() { // from class: com.android.server.adb.AdbService$Lifecycle$$ExternalSyntheticLambda0
                    @Override // java.util.function.Consumer
                    public final void accept(java.lang.Object obj) {
                        ((com.android.server.adb.AdbService) obj).bootCompleted();
                    }
                }, this.mAdbService));
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    class AdbManagerInternalImpl extends android.debug.AdbManagerInternal {
        private AdbManagerInternalImpl() {
        }

        public void registerTransport(android.debug.IAdbTransport iAdbTransport) {
            com.android.server.adb.AdbService.this.mTransports.put(iAdbTransport.asBinder(), iAdbTransport);
        }

        public void unregisterTransport(android.debug.IAdbTransport iAdbTransport) {
            com.android.server.adb.AdbService.this.mTransports.remove(iAdbTransport.asBinder());
        }

        public boolean isAdbEnabled(byte b) {
            if (b == 0) {
                return com.android.server.adb.AdbService.this.mIsAdbUsbEnabled;
            }
            if (b == 1) {
                return com.android.server.adb.AdbService.this.mIsAdbWifiEnabled;
            }
            throw new java.lang.IllegalArgumentException("isAdbEnabled called with unimplemented transport type=" + ((int) b));
        }

        public java.io.File getAdbKeysFile() {
            if (com.android.server.adb.AdbService.this.mDebuggingManager == null) {
                return null;
            }
            return com.android.server.adb.AdbService.this.mDebuggingManager.getUserKeyFile();
        }

        public java.io.File getAdbTempKeysFile() {
            if (com.android.server.adb.AdbService.this.mDebuggingManager == null) {
                return null;
            }
            return com.android.server.adb.AdbService.this.mDebuggingManager.getAdbTempKeysFile();
        }

        public void notifyKeyFilesUpdated() {
            if (com.android.server.adb.AdbService.this.mDebuggingManager == null) {
                return;
            }
            com.android.server.adb.AdbService.this.mDebuggingManager.notifyKeyFilesUpdated();
        }

        public void startAdbdForTransport(byte b) {
            com.android.server.FgThread.getHandler().sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new com.android.server.adb.AdbService$AdbManagerInternalImpl$$ExternalSyntheticLambda0(), com.android.server.adb.AdbService.this, true, java.lang.Byte.valueOf(b)));
        }

        public void stopAdbdForTransport(byte b) {
            com.android.server.FgThread.getHandler().sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new com.android.server.adb.AdbService$AdbManagerInternalImpl$$ExternalSyntheticLambda0(), com.android.server.adb.AdbService.this, false, java.lang.Byte.valueOf(b)));
        }
    }

    private void registerContentObservers() {
        try {
            this.mObserver = new com.android.server.adb.AdbService.AdbSettingsObserver();
            this.mContentResolver.registerContentObserver(android.provider.Settings.Global.getUriFor("adb_enabled"), false, this.mObserver);
            this.mContentResolver.registerContentObserver(android.provider.Settings.Global.getUriFor("adb_wifi_enabled"), false, this.mObserver);
        } catch (java.lang.Exception e) {
            android.util.Slog.e(TAG, "Error in registerContentObservers", e);
        }
    }

    private static boolean containsFunction(java.lang.String str, java.lang.String str2) {
        int indexOf = str.indexOf(str2);
        if (indexOf < 0) {
            return false;
        }
        if (indexOf > 0 && str.charAt(indexOf - 1) != ',') {
            return false;
        }
        int length = indexOf + str2.length();
        if (length < str.length() && str.charAt(length) != ',') {
            return false;
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    class AdbSettingsObserver extends android.database.ContentObserver {
        private final android.net.Uri mAdbUsbUri;
        private final android.net.Uri mAdbWifiUri;

        AdbSettingsObserver() {
            super(null);
            this.mAdbUsbUri = android.provider.Settings.Global.getUriFor("adb_enabled");
            this.mAdbWifiUri = android.provider.Settings.Global.getUriFor("adb_wifi_enabled");
        }

        @Override // android.database.ContentObserver
        public void onChange(boolean z, @android.annotation.NonNull android.net.Uri uri, int i) {
            if (this.mAdbUsbUri.equals(uri)) {
                com.android.server.FgThread.getHandler().sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new com.android.internal.util.function.TriConsumer() { // from class: com.android.server.adb.AdbService$AdbSettingsObserver$$ExternalSyntheticLambda0
                    public final void accept(java.lang.Object obj, java.lang.Object obj2, java.lang.Object obj3) {
                        ((com.android.server.adb.AdbService) obj).setAdbEnabled(((java.lang.Boolean) obj2).booleanValue(), ((java.lang.Byte) obj3).byteValue());
                    }
                }, com.android.server.adb.AdbService.this, java.lang.Boolean.valueOf(android.provider.Settings.Global.getInt(com.android.server.adb.AdbService.this.mContentResolver, "adb_enabled", 0) > 0), (byte) 0));
            } else if (this.mAdbWifiUri.equals(uri)) {
                com.android.server.FgThread.getHandler().sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new com.android.internal.util.function.TriConsumer() { // from class: com.android.server.adb.AdbService$AdbSettingsObserver$$ExternalSyntheticLambda0
                    public final void accept(java.lang.Object obj, java.lang.Object obj2, java.lang.Object obj3) {
                        ((com.android.server.adb.AdbService) obj).setAdbEnabled(((java.lang.Boolean) obj2).booleanValue(), ((java.lang.Byte) obj3).byteValue());
                    }
                }, com.android.server.adb.AdbService.this, java.lang.Boolean.valueOf(android.provider.Settings.Global.getInt(com.android.server.adb.AdbService.this.mContentResolver, "adb_wifi_enabled", 0) > 0), (byte) 1));
            }
        }
    }

    private AdbService(android.content.Context context) {
        this.mConnectionPort = new java.util.concurrent.atomic.AtomicInteger(-1);
        this.mPortListener = new com.android.server.adb.AdbService.AdbConnectionPortListener();
        this.mCallbacks = new android.os.RemoteCallbackList<>();
        this.mTransports = new android.util.ArrayMap<>();
        this.mContext = context;
        this.mContentResolver = context.getContentResolver();
        this.mDebuggingManager = new com.android.server.adb.AdbDebuggingManager(context);
        registerContentObservers();
        com.android.server.LocalServices.addService(android.debug.AdbManagerInternal.class, new com.android.server.adb.AdbService.AdbManagerInternalImpl());
    }

    public void systemReady() {
        this.mIsAdbUsbEnabled = containsFunction(android.os.SystemProperties.get(USB_PERSISTENT_CONFIG_PROPERTY, ""), com.android.server.integrity.AppIntegrityManagerServiceImpl.ADB_INSTALLER);
        int i = 1;
        boolean z = this.mIsAdbUsbEnabled || android.os.SystemProperties.getBoolean(com.android.server.testharness.TestHarnessModeService.TEST_HARNESS_MODE_PROPERTY, false);
        this.mIsAdbWifiEnabled = "1".equals(android.os.SystemProperties.get(WIFI_PERSISTENT_CONFIG_PROPERTY, "0"));
        try {
            android.provider.Settings.Global.putInt(this.mContentResolver, "adb_enabled", z ? 1 : 0);
            android.content.ContentResolver contentResolver = this.mContentResolver;
            if (!this.mIsAdbWifiEnabled) {
                i = 0;
            }
            android.provider.Settings.Global.putInt(contentResolver, "adb_wifi_enabled", i);
        } catch (java.lang.SecurityException e) {
            android.util.Slog.d(TAG, "ADB_ENABLED is restricted.");
        }
    }

    public void bootCompleted() {
        if (this.mDebuggingManager != null) {
            this.mDebuggingManager.setAdbEnabled(this.mIsAdbUsbEnabled, (byte) 0);
            this.mDebuggingManager.setAdbEnabled(this.mIsAdbWifiEnabled, (byte) 1);
        }
    }

    public void allowDebugging(boolean z, @android.annotation.NonNull java.lang.String str) {
        this.mContext.enforceCallingOrSelfPermission("android.permission.MANAGE_DEBUGGING", null);
        com.android.internal.util.Preconditions.checkStringNotEmpty(str);
        if (this.mDebuggingManager != null) {
            this.mDebuggingManager.allowDebugging(z, str);
        }
    }

    public void denyDebugging() {
        this.mContext.enforceCallingOrSelfPermission("android.permission.MANAGE_DEBUGGING", null);
        if (this.mDebuggingManager != null) {
            this.mDebuggingManager.denyDebugging();
        }
    }

    public void clearDebuggingKeys() {
        this.mContext.enforceCallingOrSelfPermission("android.permission.MANAGE_DEBUGGING", null);
        if (this.mDebuggingManager != null) {
            this.mDebuggingManager.clearDebuggingKeys();
            return;
        }
        throw new java.lang.RuntimeException("Cannot clear ADB debugging keys, AdbDebuggingManager not enabled");
    }

    public boolean isAdbWifiSupported() {
        this.mContext.enforceCallingPermission("android.permission.MANAGE_DEBUGGING", TAG);
        return this.mContext.getPackageManager().hasSystemFeature("android.hardware.wifi") || this.mContext.getPackageManager().hasSystemFeature("android.hardware.ethernet");
    }

    public boolean isAdbWifiQrSupported() {
        this.mContext.enforceCallingPermission("android.permission.MANAGE_DEBUGGING", TAG);
        return isAdbWifiSupported() && this.mContext.getPackageManager().hasSystemFeature("android.hardware.camera.any");
    }

    public void allowWirelessDebugging(boolean z, @android.annotation.NonNull java.lang.String str) {
        this.mContext.enforceCallingOrSelfPermission("android.permission.MANAGE_DEBUGGING", null);
        com.android.internal.util.Preconditions.checkStringNotEmpty(str);
        if (this.mDebuggingManager != null) {
            this.mDebuggingManager.allowWirelessDebugging(z, str);
        }
    }

    public void denyWirelessDebugging() {
        this.mContext.enforceCallingOrSelfPermission("android.permission.MANAGE_DEBUGGING", null);
        if (this.mDebuggingManager != null) {
            this.mDebuggingManager.denyWirelessDebugging();
        }
    }

    public android.debug.FingerprintAndPairDevice[] getPairedDevices() {
        this.mContext.enforceCallingOrSelfPermission("android.permission.MANAGE_DEBUGGING", null);
        if (this.mDebuggingManager == null) {
            return null;
        }
        java.util.Map<java.lang.String, android.debug.PairDevice> pairedDevices = this.mDebuggingManager.getPairedDevices();
        android.debug.FingerprintAndPairDevice[] fingerprintAndPairDeviceArr = new android.debug.FingerprintAndPairDevice[pairedDevices.size()];
        int i = 0;
        for (java.util.Map.Entry<java.lang.String, android.debug.PairDevice> entry : pairedDevices.entrySet()) {
            fingerprintAndPairDeviceArr[i] = new android.debug.FingerprintAndPairDevice();
            fingerprintAndPairDeviceArr[i].keyFingerprint = entry.getKey();
            fingerprintAndPairDeviceArr[i].device = entry.getValue();
            i++;
        }
        return fingerprintAndPairDeviceArr;
    }

    public void unpairDevice(@android.annotation.NonNull java.lang.String str) {
        this.mContext.enforceCallingOrSelfPermission("android.permission.MANAGE_DEBUGGING", null);
        com.android.internal.util.Preconditions.checkStringNotEmpty(str);
        if (this.mDebuggingManager != null) {
            this.mDebuggingManager.unpairDevice(str);
        }
    }

    public void enablePairingByPairingCode() {
        this.mContext.enforceCallingOrSelfPermission("android.permission.MANAGE_DEBUGGING", null);
        if (this.mDebuggingManager != null) {
            this.mDebuggingManager.enablePairingByPairingCode();
        }
    }

    public void enablePairingByQrCode(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull java.lang.String str2) {
        this.mContext.enforceCallingOrSelfPermission("android.permission.MANAGE_DEBUGGING", null);
        com.android.internal.util.Preconditions.checkStringNotEmpty(str);
        com.android.internal.util.Preconditions.checkStringNotEmpty(str2);
        if (this.mDebuggingManager != null) {
            this.mDebuggingManager.enablePairingByQrCode(str, str2);
        }
    }

    public void disablePairing() {
        this.mContext.enforceCallingOrSelfPermission("android.permission.MANAGE_DEBUGGING", null);
        if (this.mDebuggingManager != null) {
            this.mDebuggingManager.disablePairing();
        }
    }

    public int getAdbWirelessPort() {
        this.mContext.enforceCallingOrSelfPermission("android.permission.MANAGE_DEBUGGING", null);
        if (this.mDebuggingManager != null) {
            return this.mDebuggingManager.getAdbWirelessPort();
        }
        return this.mConnectionPort.get();
    }

    public void registerCallback(android.debug.IAdbCallback iAdbCallback) throws android.os.RemoteException {
        this.mCallbacks.register(iAdbCallback);
    }

    public void unregisterCallback(android.debug.IAdbCallback iAdbCallback) throws android.os.RemoteException {
        this.mCallbacks.unregister(iAdbCallback);
    }

    class AdbConnectionPortListener implements com.android.server.adb.AdbDebuggingManager.AdbConnectionPortListener {
        AdbConnectionPortListener() {
        }

        @Override // com.android.server.adb.AdbDebuggingManager.AdbConnectionPortListener
        public void onPortReceived(int i) {
            if (i > 0 && i <= 65535) {
                com.android.server.adb.AdbService.this.mConnectionPort.set(i);
            } else {
                com.android.server.adb.AdbService.this.mConnectionPort.set(-1);
                try {
                    android.provider.Settings.Global.putInt(com.android.server.adb.AdbService.this.mContentResolver, "adb_wifi_enabled", 0);
                } catch (java.lang.SecurityException e) {
                    android.util.Slog.d(com.android.server.adb.AdbService.TAG, "ADB_ENABLED is restricted.");
                }
            }
            com.android.server.adb.AdbService.this.broadcastPortInfo(com.android.server.adb.AdbService.this.mConnectionPort.get());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void broadcastPortInfo(int i) {
        int i2;
        android.content.Intent intent = new android.content.Intent("com.android.server.adb.WIRELESS_DEBUG_STATUS");
        if (i >= 0) {
            i2 = 4;
        } else {
            i2 = 5;
        }
        intent.putExtra("status", i2);
        intent.putExtra("adb_port", i);
        com.android.server.adb.AdbDebuggingManager.sendBroadcastWithDebugPermission(this.mContext, intent, android.os.UserHandle.ALL);
        android.util.Slog.i(TAG, "sent port broadcast port=" + i);
    }

    private void startAdbd() {
        android.os.SystemProperties.set(CTL_START, ADBD);
    }

    private void stopAdbd() {
        if (!this.mIsAdbUsbEnabled && !this.mIsAdbWifiEnabled) {
            android.os.SystemProperties.set(CTL_STOP, ADBD);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setAdbdEnabledForTransport(boolean z, byte b) {
        if (b == 0) {
            this.mIsAdbUsbEnabled = z;
        } else if (b == 1) {
            this.mIsAdbWifiEnabled = z;
        }
        if (z) {
            startAdbd();
        } else {
            stopAdbd();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setAdbEnabled(final boolean z, final byte b) {
        if (b == 0 && z != this.mIsAdbUsbEnabled) {
            this.mIsAdbUsbEnabled = z;
        } else if (b == 1 && z != this.mIsAdbWifiEnabled) {
            this.mIsAdbWifiEnabled = z;
            if (this.mIsAdbWifiEnabled) {
                if (!((java.lang.Boolean) android.sysprop.AdbProperties.secure().orElse(false)).booleanValue() && this.mDebuggingManager == null) {
                    android.os.SystemProperties.set(WIFI_PERSISTENT_CONFIG_PROPERTY, "1");
                    this.mConnectionPortPoller = new com.android.server.adb.AdbDebuggingManager.AdbConnectionPortPoller(this.mPortListener);
                    this.mConnectionPortPoller.start();
                }
            } else {
                android.os.SystemProperties.set(WIFI_PERSISTENT_CONFIG_PROPERTY, "0");
                if (this.mConnectionPortPoller != null) {
                    this.mConnectionPortPoller.cancelAndWait();
                    this.mConnectionPortPoller = null;
                }
            }
        } else {
            return;
        }
        if (z) {
            startAdbd();
        } else {
            stopAdbd();
        }
        for (android.debug.IAdbTransport iAdbTransport : this.mTransports.values()) {
            try {
                iAdbTransport.onAdbEnabled(z, b);
            } catch (android.os.RemoteException e) {
                android.util.Slog.w(TAG, "Unable to send onAdbEnabled to transport " + iAdbTransport.toString());
            }
        }
        if (this.mDebuggingManager != null) {
            this.mDebuggingManager.setAdbEnabled(z, b);
        }
        this.mCallbacks.broadcast(new java.util.function.Consumer() { // from class: com.android.server.adb.AdbService$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.adb.AdbService.lambda$setAdbEnabled$0(z, b, (android.debug.IAdbCallback) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$setAdbEnabled$0(boolean z, byte b, android.debug.IAdbCallback iAdbCallback) {
        try {
            iAdbCallback.onDebuggingChanged(z, b);
        } catch (android.os.RemoteException e) {
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public int handleShellCommand(android.os.ParcelFileDescriptor parcelFileDescriptor, android.os.ParcelFileDescriptor parcelFileDescriptor2, android.os.ParcelFileDescriptor parcelFileDescriptor3, java.lang.String[] strArr) {
        return new com.android.server.adb.AdbShellCommand(this).exec(this, parcelFileDescriptor.getFileDescriptor(), parcelFileDescriptor2.getFileDescriptor(), parcelFileDescriptor3.getFileDescriptor(), strArr);
    }

    public void dump(java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, java.lang.String[] strArr) {
        boolean z;
        com.android.internal.util.dump.DualDumpOutputStream dualDumpOutputStream;
        if (com.android.internal.util.DumpUtils.checkDumpPermission(this.mContext, TAG, printWriter)) {
            com.android.internal.util.IndentingPrintWriter indentingPrintWriter = new com.android.internal.util.IndentingPrintWriter(printWriter, "  ");
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                android.util.ArraySet arraySet = new android.util.ArraySet();
                java.util.Collections.addAll(arraySet, strArr);
                if (!arraySet.contains("--proto")) {
                    z = false;
                } else {
                    z = true;
                }
                if (arraySet.size() == 0 || arraySet.contains("-a") || z) {
                    if (z) {
                        dualDumpOutputStream = new com.android.internal.util.dump.DualDumpOutputStream(new android.util.proto.ProtoOutputStream(fileDescriptor));
                    } else {
                        indentingPrintWriter.println("ADB MANAGER STATE (dumpsys adb):");
                        dualDumpOutputStream = new com.android.internal.util.dump.DualDumpOutputStream(new com.android.internal.util.IndentingPrintWriter(indentingPrintWriter, "  "));
                    }
                    if (this.mDebuggingManager != null) {
                        this.mDebuggingManager.dump(dualDumpOutputStream, "debugging_manager", 1146756268033L);
                    }
                    dualDumpOutputStream.flush();
                } else {
                    indentingPrintWriter.println("Dump current ADB state");
                    indentingPrintWriter.println("  No commands available");
                }
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            } catch (java.lang.Throwable th) {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                throw th;
            }
        }
    }
}
