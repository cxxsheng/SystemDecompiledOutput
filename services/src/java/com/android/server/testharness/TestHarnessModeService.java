package com.android.server.testharness;

/* loaded from: classes2.dex */
public class TestHarnessModeService extends com.android.server.SystemService {
    private static final java.lang.String TAG = com.android.server.testharness.TestHarnessModeService.class.getSimpleName();
    public static final java.lang.String TEST_HARNESS_MODE_PROPERTY = "persist.sys.test_harness";
    private boolean mEnableKeepMemtagMode;
    private com.android.server.pdb.PersistentDataBlockManagerInternal mPersistentDataBlockManagerInternal;
    private final android.os.IBinder mService;

    public TestHarnessModeService(android.content.Context context) {
        super(context);
        this.mEnableKeepMemtagMode = false;
        this.mService = new android.os.Binder() { // from class: com.android.server.testharness.TestHarnessModeService.1
            public void onShellCommand(java.io.FileDescriptor fileDescriptor, java.io.FileDescriptor fileDescriptor2, java.io.FileDescriptor fileDescriptor3, java.lang.String[] strArr, android.os.ShellCallback shellCallback, android.os.ResultReceiver resultReceiver) {
                new com.android.server.testharness.TestHarnessModeService.TestHarnessModeShellCommand().exec(this, fileDescriptor, fileDescriptor2, fileDescriptor3, strArr, shellCallback, resultReceiver);
            }
        };
    }

    @Override // com.android.server.SystemService
    public void onStart() {
        publishBinderService("testharness", this.mService);
    }

    @Override // com.android.server.SystemService
    public void onBootPhase(int i) {
        switch (i) {
            case 500:
                setUpTestHarnessMode();
                break;
            case 1000:
                completeTestHarnessModeSetup();
                showNotificationIfEnabled();
                break;
        }
        super.onBootPhase(i);
    }

    private void setUpTestHarnessMode() {
        android.util.Slog.d(TAG, "Setting up test harness mode");
        if (getTestHarnessModeData() == null) {
            return;
        }
        setDeviceProvisioned();
        disableLockScreen();
        android.os.SystemProperties.set(TEST_HARNESS_MODE_PROPERTY, "1");
    }

    private void disableLockScreen() {
        new com.android.internal.widget.LockPatternUtils(getContext()).setLockScreenDisabled(true, getMainUserId());
    }

    private void completeTestHarnessModeSetup() {
        android.util.Slog.d(TAG, "Completing Test Harness Mode setup.");
        byte[] testHarnessModeData = getTestHarnessModeData();
        if (testHarnessModeData == null) {
            return;
        }
        try {
            try {
                setUpAdbFiles(com.android.server.testharness.TestHarnessModeService.PersistentData.fromBytes(testHarnessModeData));
                configureSettings();
                configureUser();
            } catch (com.android.server.testharness.TestHarnessModeService.SetUpTestHarnessModeException e) {
                android.util.Slog.e(TAG, "Failed to set up Test Harness Mode. Bad data.", e);
            }
        } finally {
            getPersistentDataBlock().clearTestHarnessModeData();
        }
    }

    private byte[] getTestHarnessModeData() {
        com.android.server.pdb.PersistentDataBlockManagerInternal persistentDataBlock = getPersistentDataBlock();
        if (persistentDataBlock == null) {
            android.util.Slog.e(TAG, "Failed to start Test Harness Mode; no implementation of PersistentDataBlockManagerInternal was bound!");
            return null;
        }
        byte[] testHarnessModeData = persistentDataBlock.getTestHarnessModeData();
        if (testHarnessModeData == null || testHarnessModeData.length == 0) {
            return null;
        }
        return testHarnessModeData;
    }

    private void configureSettings() {
        android.content.ContentResolver contentResolver = getContext().getContentResolver();
        if (android.provider.Settings.Global.getInt(contentResolver, "adb_enabled", 0) == 1) {
            android.os.SystemProperties.set("ctl.restart", "adbd");
            android.util.Slog.d(TAG, "Restarted adbd");
        }
        android.provider.Settings.Global.putLong(contentResolver, "adb_allowed_connection_time", 0L);
        android.provider.Settings.Global.putInt(contentResolver, "development_settings_enabled", 1);
        android.provider.Settings.Global.putInt(contentResolver, "verifier_verify_adb_installs", 0);
        android.provider.Settings.Global.putInt(contentResolver, "stay_on_while_plugged_in", 15);
        android.provider.Settings.Global.putInt(contentResolver, "ota_disable_automatic_update", 1);
    }

    private void setUpAdbFiles(com.android.server.testharness.TestHarnessModeService.PersistentData persistentData) {
        android.debug.AdbManagerInternal adbManagerInternal = (android.debug.AdbManagerInternal) com.android.server.LocalServices.getService(android.debug.AdbManagerInternal.class);
        if (adbManagerInternal.getAdbKeysFile() != null) {
            writeBytesToFile(persistentData.mAdbKeys, adbManagerInternal.getAdbKeysFile().toPath());
        }
        if (adbManagerInternal.getAdbTempKeysFile() != null) {
            writeBytesToFile(persistentData.mAdbTempKeys, adbManagerInternal.getAdbTempKeysFile().toPath());
        }
        adbManagerInternal.notifyKeyFilesUpdated();
    }

    private void configureUser() {
        int mainUserId = getMainUserId();
        android.content.ContentResolver.setMasterSyncAutomaticallyAsUser(false, mainUserId);
        ((android.location.LocationManager) getContext().getSystemService(android.location.LocationManager.class)).setLocationEnabledForUser(true, android.os.UserHandle.of(mainUserId));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getMainUserId() {
        int mainUserId = ((com.android.server.pm.UserManagerInternal) com.android.server.LocalServices.getService(com.android.server.pm.UserManagerInternal.class)).getMainUserId();
        if (mainUserId >= 0) {
            return mainUserId;
        }
        android.util.Slog.w(TAG, "No MainUser exists; using user 0 instead");
        return 0;
    }

    private void writeBytesToFile(byte[] bArr, java.nio.file.Path path) {
        try {
            java.io.OutputStream newOutputStream = java.nio.file.Files.newOutputStream(path, new java.nio.file.OpenOption[0]);
            newOutputStream.write(bArr);
            newOutputStream.close();
            java.util.Set<java.nio.file.attribute.PosixFilePermission> posixFilePermissions = java.nio.file.Files.getPosixFilePermissions(path, new java.nio.file.LinkOption[0]);
            posixFilePermissions.add(java.nio.file.attribute.PosixFilePermission.GROUP_READ);
            java.nio.file.Files.setPosixFilePermissions(path, posixFilePermissions);
        } catch (java.io.IOException e) {
            android.util.Slog.e(TAG, "Failed to set up adb keys", e);
        }
    }

    private void setDeviceProvisioned() {
        android.content.ContentResolver contentResolver = getContext().getContentResolver();
        android.provider.Settings.Global.putInt(contentResolver, "device_provisioned", 1);
        android.provider.Settings.Secure.putIntForUser(contentResolver, "user_setup_complete", 1, -2);
    }

    private void showNotificationIfEnabled() {
        if (!android.os.SystemProperties.getBoolean(TEST_HARNESS_MODE_PROPERTY, false)) {
            return;
        }
        java.lang.String string = getContext().getString(android.R.string.status_bar_volume);
        ((android.app.NotificationManager) getContext().getSystemService(android.app.NotificationManager.class)).notifyAsUser(null, 54, new android.app.Notification.Builder(getContext(), com.android.internal.notification.SystemNotificationChannels.DEVELOPER).setSmallIcon(android.R.drawable.stat_notify_sync_anim0).setWhen(0L).setOngoing(true).setTicker(string).setDefaults(0).setColor(getContext().getColor(android.R.color.system_notification_accent_color)).setContentTitle(string).setContentText(getContext().getString(android.R.string.status_bar_tty)).setVisibility(1).build(), android.os.UserHandle.ALL);
    }

    /* JADX INFO: Access modifiers changed from: private */
    @android.annotation.Nullable
    public com.android.server.pdb.PersistentDataBlockManagerInternal getPersistentDataBlock() {
        if (this.mPersistentDataBlockManagerInternal == null) {
            android.util.Slog.d(TAG, "Getting PersistentDataBlockManagerInternal from LocalServices");
            this.mPersistentDataBlockManagerInternal = (com.android.server.pdb.PersistentDataBlockManagerInternal) com.android.server.LocalServices.getService(com.android.server.pdb.PersistentDataBlockManagerInternal.class);
        }
        return this.mPersistentDataBlockManagerInternal;
    }

    private class TestHarnessModeShellCommand extends android.os.ShellCommand {
        private TestHarnessModeShellCommand() {
        }

        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        public int onCommand(java.lang.String str) {
            boolean z;
            boolean z2;
            if (str == null) {
                return handleDefaultCommands(str);
            }
            switch (str.hashCode()) {
                case -1298848381:
                    if (str.equals("enable")) {
                        z = false;
                        break;
                    }
                    z = -1;
                    break;
                case 1097519758:
                    if (str.equals("restore")) {
                        z = true;
                        break;
                    }
                    z = -1;
                    break;
                default:
                    z = -1;
                    break;
            }
            switch (z) {
                case false:
                case true:
                    break;
                default:
                    return handleDefaultCommands(str);
            }
            while (true) {
                java.lang.String nextOption = getNextOption();
                if (nextOption == null) {
                    checkPermissions();
                    long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                    try {
                        if (!isDeviceSecure()) {
                            return handleEnable();
                        }
                        getErrPrintWriter().println("Test Harness Mode cannot be enabled if there is a lock screen");
                        android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                        return 2;
                    } finally {
                        android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                    }
                }
                switch (nextOption.hashCode()) {
                    case -510577843:
                        if (nextOption.equals("--keep-memtag")) {
                            z2 = false;
                            break;
                        }
                    default:
                        z2 = -1;
                        break;
                }
                switch (z2) {
                    case false:
                        com.android.server.testharness.TestHarnessModeService.this.mEnableKeepMemtagMode = true;
                    default:
                        getErrPrintWriter().println("Invalid option: " + nextOption);
                        return 1;
                }
            }
        }

        private void checkPermissions() {
            com.android.server.testharness.TestHarnessModeService.this.getContext().enforceCallingPermission("android.permission.ENABLE_TEST_HARNESS_MODE", "You must hold android.permission.ENABLE_TEST_HARNESS_MODE to enable Test Harness Mode");
        }

        private boolean isDeviceSecure() {
            return ((android.app.KeyguardManager) com.android.server.testharness.TestHarnessModeService.this.getContext().getSystemService(android.app.KeyguardManager.class)).isDeviceSecure(com.android.server.testharness.TestHarnessModeService.this.getMainUserId());
        }

        private int handleEnable() {
            android.debug.AdbManagerInternal adbManagerInternal = (android.debug.AdbManagerInternal) com.android.server.LocalServices.getService(android.debug.AdbManagerInternal.class);
            try {
                com.android.server.testharness.TestHarnessModeService.PersistentData persistentData = new com.android.server.testharness.TestHarnessModeService.PersistentData(getBytesFromFile(adbManagerInternal.getAdbKeysFile()), getBytesFromFile(adbManagerInternal.getAdbTempKeysFile()));
                com.android.server.pdb.PersistentDataBlockManagerInternal persistentDataBlock = com.android.server.testharness.TestHarnessModeService.this.getPersistentDataBlock();
                if (persistentDataBlock == null) {
                    android.util.Slog.e("ShellCommand", "Failed to enable Test Harness Mode. No implementation of PersistentDataBlockManagerInternal was bound.");
                    getErrPrintWriter().println("Failed to enable Test Harness Mode");
                    return 1;
                }
                persistentDataBlock.setTestHarnessModeData(persistentData.toBytes());
                android.content.Intent intent = new android.content.Intent("android.intent.action.FACTORY_RESET");
                intent.setPackage(com.android.server.pm.PackageManagerService.PLATFORM_PACKAGE_NAME);
                intent.addFlags(268435456);
                intent.putExtra("android.intent.extra.REASON", "ShellCommand");
                intent.putExtra("android.intent.extra.WIPE_EXTERNAL_STORAGE", true);
                intent.putExtra("keep_memtag_mode", com.android.server.testharness.TestHarnessModeService.this.mEnableKeepMemtagMode);
                com.android.server.testharness.TestHarnessModeService.this.getContext().sendBroadcastAsUser(intent, android.os.UserHandle.SYSTEM);
                return 0;
            } catch (java.io.IOException e) {
                android.util.Slog.e("ShellCommand", "Failed to store ADB keys.", e);
                getErrPrintWriter().println("Failed to enable Test Harness Mode");
                return 1;
            }
        }

        private byte[] getBytesFromFile(java.io.File file) throws java.io.IOException {
            if (file == null || !file.exists()) {
                return new byte[0];
            }
            java.nio.file.Path path = file.toPath();
            java.io.InputStream newInputStream = java.nio.file.Files.newInputStream(path, new java.nio.file.OpenOption[0]);
            try {
                int size = (int) java.nio.file.Files.size(path);
                byte[] bArr = new byte[size];
                if (newInputStream.read(bArr) != size) {
                    throw new java.io.IOException("Failed to read the whole file");
                }
                newInputStream.close();
                return bArr;
            } catch (java.lang.Throwable th) {
                if (newInputStream != null) {
                    try {
                        newInputStream.close();
                    } catch (java.lang.Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        }

        public void onHelp() {
            java.io.PrintWriter outPrintWriter = getOutPrintWriter();
            outPrintWriter.println("About:");
            outPrintWriter.println("  Test Harness Mode is a mode that the device can be placed in to prepare");
            outPrintWriter.println("  the device for running UI tests. The device is placed into this mode by");
            outPrintWriter.println("  first wiping all data from the device, preserving ADB keys.");
            outPrintWriter.println();
            outPrintWriter.println("  By default, the following settings are configured:");
            outPrintWriter.println("    * Package Verifier is disabled");
            outPrintWriter.println("    * Stay Awake While Charging is enabled");
            outPrintWriter.println("    * OTA Updates are disabled");
            outPrintWriter.println("    * Auto-Sync for accounts is disabled");
            outPrintWriter.println();
            outPrintWriter.println("  Other apps may configure themselves differently in Test Harness Mode by");
            outPrintWriter.println("  checking ActivityManager.isRunningInUserTestHarness()");
            outPrintWriter.println();
            outPrintWriter.println("Test Harness Mode commands:");
            outPrintWriter.println("  help");
            outPrintWriter.println("    Print this help text.");
            outPrintWriter.println();
            outPrintWriter.println("  enable|restore");
            outPrintWriter.println("    Erase all data from this device and enable Test Harness Mode,");
            outPrintWriter.println("    preserving the stored ADB keys currently on the device and toggling");
            outPrintWriter.println("    settings in a way that are conducive to Instrumentation testing.");
        }
    }

    public static class PersistentData {
        static final byte VERSION_1 = 1;
        static final byte VERSION_2 = 2;
        final byte[] mAdbKeys;
        final byte[] mAdbTempKeys;
        final int mVersion;

        PersistentData(byte[] bArr, byte[] bArr2) {
            this(2, bArr, bArr2);
        }

        PersistentData(int i, byte[] bArr, byte[] bArr2) {
            this.mVersion = i;
            this.mAdbKeys = bArr;
            this.mAdbTempKeys = bArr2;
        }

        static com.android.server.testharness.TestHarnessModeService.PersistentData fromBytes(byte[] bArr) throws com.android.server.testharness.TestHarnessModeService.SetUpTestHarnessModeException {
            try {
                java.io.DataInputStream dataInputStream = new java.io.DataInputStream(new java.io.ByteArrayInputStream(bArr));
                int readInt = dataInputStream.readInt();
                if (readInt == 1) {
                    dataInputStream.readBoolean();
                }
                byte[] bArr2 = new byte[dataInputStream.readInt()];
                dataInputStream.readFully(bArr2);
                byte[] bArr3 = new byte[dataInputStream.readInt()];
                dataInputStream.readFully(bArr3);
                return new com.android.server.testharness.TestHarnessModeService.PersistentData(readInt, bArr2, bArr3);
            } catch (java.io.IOException e) {
                throw new com.android.server.testharness.TestHarnessModeService.SetUpTestHarnessModeException(e);
            }
        }

        byte[] toBytes() {
            try {
                java.io.ByteArrayOutputStream byteArrayOutputStream = new java.io.ByteArrayOutputStream();
                java.io.DataOutputStream dataOutputStream = new java.io.DataOutputStream(byteArrayOutputStream);
                dataOutputStream.writeInt(2);
                dataOutputStream.writeInt(this.mAdbKeys.length);
                dataOutputStream.write(this.mAdbKeys);
                dataOutputStream.writeInt(this.mAdbTempKeys.length);
                dataOutputStream.write(this.mAdbTempKeys);
                dataOutputStream.close();
                return byteArrayOutputStream.toByteArray();
            } catch (java.io.IOException e) {
                throw new java.lang.RuntimeException(e);
            }
        }
    }

    private static class SetUpTestHarnessModeException extends java.lang.Exception {
        SetUpTestHarnessModeException(java.lang.Exception exc) {
            super(exc);
        }
    }
}
