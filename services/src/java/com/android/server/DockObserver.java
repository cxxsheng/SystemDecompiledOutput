package com.android.server;

/* loaded from: classes.dex */
final class DockObserver extends com.android.server.SystemService {
    private static final int MSG_DOCK_STATE_CHANGED = 0;
    private static final java.lang.String TAG = "DockObserver";
    private int mActualDockState;
    private final boolean mAllowTheaterModeWakeFromDock;
    private com.android.server.DockObserver.DeviceProvisionedObserver mDeviceProvisionedObserver;
    private final java.util.List<com.android.server.DockObserver.ExtconStateConfig> mExtconStateConfigs;
    private final com.android.server.ExtconUEventObserver mExtconUEventObserver;
    private final android.os.Handler mHandler;
    private final boolean mKeepDreamingWhenUnplugging;
    private final java.lang.Object mLock;
    private final android.os.PowerManager mPowerManager;
    private int mPreviousDockState;
    private int mReportedDockState;
    private boolean mSystemReady;
    private boolean mUpdatesStopped;
    private final android.os.PowerManager.WakeLock mWakeLock;

    static final class ExtconStateProvider {
        private final java.util.Map<java.lang.String, java.lang.String> mState;

        ExtconStateProvider(java.util.Map<java.lang.String, java.lang.String> map) {
            this.mState = map;
        }

        java.lang.String getValue(java.lang.String str) {
            return this.mState.get(str);
        }

        static com.android.server.DockObserver.ExtconStateProvider fromString(java.lang.String str) {
            java.util.HashMap hashMap = new java.util.HashMap();
            for (java.lang.String str2 : str.split("\n")) {
                java.lang.String[] split = str2.split("=");
                if (split.length == 2) {
                    hashMap.put(split[0], split[1]);
                } else {
                    android.util.Slog.e(com.android.server.DockObserver.TAG, "Invalid line: " + str2);
                }
            }
            return new com.android.server.DockObserver.ExtconStateProvider(hashMap);
        }

        static com.android.server.DockObserver.ExtconStateProvider fromFile(java.lang.String str) {
            char[] cArr = new char[1024];
            try {
                java.io.FileReader fileReader = new java.io.FileReader(str);
                try {
                    com.android.server.DockObserver.ExtconStateProvider fromString = fromString(new java.lang.String(cArr, 0, fileReader.read(cArr, 0, 1024)).trim());
                    fileReader.close();
                    return fromString;
                } catch (java.lang.Throwable th) {
                    try {
                        fileReader.close();
                    } catch (java.lang.Throwable th2) {
                        th.addSuppressed(th2);
                    }
                    throw th;
                }
            } catch (java.io.FileNotFoundException e) {
                android.util.Slog.w(com.android.server.DockObserver.TAG, "No state file found at: " + str);
                return new com.android.server.DockObserver.ExtconStateProvider(new java.util.HashMap());
            } catch (java.lang.Exception e2) {
                android.util.Slog.e(com.android.server.DockObserver.TAG, "", e2);
                return new com.android.server.DockObserver.ExtconStateProvider(new java.util.HashMap());
            }
        }
    }

    private static final class ExtconStateConfig {
        public final int extraStateValue;
        public final java.util.List<android.util.Pair<java.lang.String, java.lang.String>> keyValuePairs = new java.util.ArrayList();

        ExtconStateConfig(int i) {
            this.extraStateValue = i;
        }
    }

    private static java.util.List<com.android.server.DockObserver.ExtconStateConfig> loadExtconStateConfigs(android.content.Context context) {
        java.lang.String[] stringArray = context.getResources().getStringArray(android.R.array.config_displayWhiteBalanceStrongDisplayColorTemperatures);
        try {
            java.util.ArrayList arrayList = new java.util.ArrayList();
            for (java.lang.String str : stringArray) {
                java.lang.String[] split = str.split(",");
                com.android.server.DockObserver.ExtconStateConfig extconStateConfig = new com.android.server.DockObserver.ExtconStateConfig(java.lang.Integer.parseInt(split[0]));
                for (int i = 1; i < split.length; i++) {
                    java.lang.String[] split2 = split[i].split("=");
                    if (split2.length == 2) {
                        extconStateConfig.keyValuePairs.add(android.util.Pair.create(split2[0], split2[1]));
                    } else {
                        throw new java.lang.IllegalArgumentException("Invalid key-value: " + split[i]);
                    }
                }
                arrayList.add(extconStateConfig);
            }
            return arrayList;
        } catch (java.lang.ArrayIndexOutOfBoundsException | java.lang.IllegalArgumentException e) {
            android.util.Slog.e(TAG, "Could not parse extcon state config", e);
            return new java.util.ArrayList();
        }
    }

    public DockObserver(android.content.Context context) {
        super(context);
        this.mLock = new java.lang.Object();
        this.mActualDockState = 0;
        this.mReportedDockState = 0;
        this.mPreviousDockState = 0;
        this.mHandler = new android.os.Handler(true) { // from class: com.android.server.DockObserver.1
            @Override // android.os.Handler
            public void handleMessage(android.os.Message message) {
                switch (message.what) {
                    case 0:
                        com.android.server.DockObserver.this.handleDockStateChange();
                        com.android.server.DockObserver.this.mWakeLock.release();
                        break;
                }
            }
        };
        this.mExtconUEventObserver = new com.android.server.ExtconUEventObserver() { // from class: com.android.server.DockObserver.2
            @Override // com.android.server.ExtconUEventObserver
            public void onUEvent(com.android.server.ExtconUEventObserver.ExtconInfo extconInfo, android.os.UEventObserver.UEvent uEvent) {
                synchronized (com.android.server.DockObserver.this.mLock) {
                    try {
                        java.lang.String str = uEvent.get("STATE");
                        if (str != null) {
                            com.android.server.DockObserver.this.setDockStateFromProviderLocked(com.android.server.DockObserver.ExtconStateProvider.fromString(str));
                        } else {
                            android.util.Slog.e(com.android.server.DockObserver.TAG, "Extcon event missing STATE: " + uEvent);
                        }
                    } catch (java.lang.Throwable th) {
                        throw th;
                    }
                }
            }
        };
        this.mPowerManager = (android.os.PowerManager) context.getSystemService("power");
        this.mWakeLock = this.mPowerManager.newWakeLock(1, TAG);
        this.mAllowTheaterModeWakeFromDock = context.getResources().getBoolean(android.R.bool.config_allowTheaterModeWakeFromDock);
        this.mKeepDreamingWhenUnplugging = context.getResources().getBoolean(android.R.bool.config_isPreApprovalRequestAvailable);
        this.mDeviceProvisionedObserver = new com.android.server.DockObserver.DeviceProvisionedObserver(this.mHandler);
        this.mExtconStateConfigs = loadExtconStateConfigs(context);
        java.util.List<com.android.server.ExtconUEventObserver.ExtconInfo> extconInfoForTypes = com.android.server.ExtconUEventObserver.ExtconInfo.getExtconInfoForTypes(new java.lang.String[]{com.android.server.ExtconUEventObserver.ExtconInfo.EXTCON_DOCK});
        if (!extconInfoForTypes.isEmpty()) {
            com.android.server.ExtconUEventObserver.ExtconInfo extconInfo = extconInfoForTypes.get(0);
            android.util.Slog.i(TAG, "Found extcon info devPath: " + extconInfo.getDevicePath() + ", statePath: " + extconInfo.getStatePath());
            setDockStateFromProviderLocked(com.android.server.DockObserver.ExtconStateProvider.fromFile(extconInfo.getStatePath()));
            this.mPreviousDockState = this.mActualDockState;
            this.mExtconUEventObserver.startObserving(extconInfo);
            return;
        }
        android.util.Slog.i(TAG, "No extcon dock device found in this kernel.");
    }

    @Override // com.android.server.SystemService
    public void onStart() {
        publishBinderService(TAG, new com.android.server.DockObserver.BinderService());
        com.android.internal.util.FrameworkStatsLog.write(com.android.internal.util.FrameworkStatsLog.DOCK_STATE_CHANGED, this.mReportedDockState);
    }

    @Override // com.android.server.SystemService
    public void onBootPhase(int i) {
        if (i == 550) {
            synchronized (this.mLock) {
                this.mSystemReady = true;
                this.mDeviceProvisionedObserver.onSystemReady();
                updateIfDockedLocked();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateIfDockedLocked() {
        if (this.mReportedDockState != 0) {
            updateLocked();
        }
    }

    private void setActualDockStateLocked(int i) {
        this.mActualDockState = i;
        if (!this.mUpdatesStopped) {
            setDockStateLocked(i);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setDockStateLocked(int i) {
        if (i != this.mReportedDockState) {
            this.mReportedDockState = i;
            if (this.mSystemReady) {
                if (allowWakeFromDock()) {
                    this.mPowerManager.wakeUp(android.os.SystemClock.uptimeMillis(), "android.server:DOCK");
                }
                updateLocked();
            }
        }
    }

    private boolean allowWakeFromDock() {
        if (this.mKeepDreamingWhenUnplugging) {
            return false;
        }
        return this.mAllowTheaterModeWakeFromDock || android.provider.Settings.Global.getInt(getContext().getContentResolver(), "theater_mode_on", 0) == 0;
    }

    private void updateLocked() {
        this.mWakeLock.acquire();
        this.mHandler.sendEmptyMessage(0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleDockStateChange() {
        java.lang.String str;
        java.lang.String string;
        android.net.Uri parse;
        android.media.Ringtone ringtone;
        synchronized (this.mLock) {
            try {
                android.util.Slog.i(TAG, "Dock state changed from " + this.mPreviousDockState + " to " + this.mReportedDockState);
                int i = this.mPreviousDockState;
                this.mPreviousDockState = this.mReportedDockState;
                android.content.ContentResolver contentResolver = getContext().getContentResolver();
                if (!this.mDeviceProvisionedObserver.isDeviceProvisioned()) {
                    android.util.Slog.i(TAG, "Device not provisioned, skipping dock broadcast");
                    return;
                }
                android.content.Intent intent = new android.content.Intent("android.intent.action.DOCK_EVENT");
                intent.addFlags(536870912);
                intent.putExtra("android.intent.extra.DOCK_STATE", this.mReportedDockState);
                boolean z = android.provider.Settings.Global.getInt(contentResolver, "dock_sounds_enabled", 1) == 1;
                boolean z2 = android.provider.Settings.Global.getInt(contentResolver, "dock_sounds_enabled_when_accessbility", 1) == 1;
                boolean z3 = android.provider.Settings.Secure.getInt(contentResolver, "accessibility_enabled", 0) == 1;
                if (z || (z3 && z2)) {
                    if (this.mReportedDockState == 0) {
                        if (i == 1 || i == 3 || i == 4) {
                            str = "desk_undock_sound";
                        } else {
                            if (i == 2) {
                                str = "car_undock_sound";
                            }
                            str = null;
                        }
                        if (str != null && (string = android.provider.Settings.Global.getString(contentResolver, str)) != null) {
                            parse = android.net.Uri.parse("file://" + string);
                            if (parse != null && (ringtone = android.media.RingtoneManager.getRingtone(getContext(), parse)) != null) {
                                ringtone.setStreamType(1);
                                ringtone.preferBuiltinDevice(true);
                                ringtone.play();
                            }
                        }
                    } else {
                        if (this.mReportedDockState == 1 || this.mReportedDockState == 3 || this.mReportedDockState == 4) {
                            str = "desk_dock_sound";
                        } else {
                            if (this.mReportedDockState == 2) {
                                str = "car_dock_sound";
                            }
                            str = null;
                        }
                        if (str != null) {
                            parse = android.net.Uri.parse("file://" + string);
                            if (parse != null) {
                                ringtone.setStreamType(1);
                                ringtone.preferBuiltinDevice(true);
                                ringtone.play();
                            }
                        }
                    }
                }
                getContext().sendStickyBroadcastAsUser(intent, android.os.UserHandle.ALL);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private int getDockedStateExtraValue(com.android.server.DockObserver.ExtconStateProvider extconStateProvider) {
        for (com.android.server.DockObserver.ExtconStateConfig extconStateConfig : this.mExtconStateConfigs) {
            boolean z = true;
            for (android.util.Pair<java.lang.String, java.lang.String> pair : extconStateConfig.keyValuePairs) {
                z = z && ((java.lang.String) pair.second).equals(extconStateProvider.getValue((java.lang.String) pair.first));
                if (!z) {
                    break;
                }
            }
            if (z) {
                return extconStateConfig.extraStateValue;
            }
        }
        return 1;
    }

    @com.android.internal.annotations.VisibleForTesting
    void setDockStateFromProviderForTesting(com.android.server.DockObserver.ExtconStateProvider extconStateProvider) {
        synchronized (this.mLock) {
            setDockStateFromProviderLocked(extconStateProvider);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setDockStateFromProviderLocked(com.android.server.DockObserver.ExtconStateProvider extconStateProvider) {
        int i;
        if (!"1".equals(extconStateProvider.getValue(com.android.server.ExtconUEventObserver.ExtconInfo.EXTCON_DOCK))) {
            i = 0;
        } else {
            i = getDockedStateExtraValue(extconStateProvider);
        }
        setActualDockStateLocked(i);
    }

    private final class BinderService extends android.os.Binder {
        private BinderService() {
        }

        @Override // android.os.Binder
        protected void dump(java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, java.lang.String[] strArr) {
            if (com.android.internal.util.DumpUtils.checkDumpPermission(com.android.server.DockObserver.this.getContext(), com.android.server.DockObserver.TAG, printWriter)) {
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    synchronized (com.android.server.DockObserver.this.mLock) {
                        if (strArr != null) {
                            try {
                                if (strArr.length != 0 && !"-a".equals(strArr[0])) {
                                    if (strArr.length == 3 && "set".equals(strArr[0])) {
                                        java.lang.String str = strArr[1];
                                        java.lang.String str2 = strArr[2];
                                        try {
                                            if ("state".equals(str)) {
                                                com.android.server.DockObserver.this.mUpdatesStopped = true;
                                                com.android.server.DockObserver.this.setDockStateLocked(java.lang.Integer.parseInt(str2));
                                            } else {
                                                printWriter.println("Unknown set option: " + str);
                                            }
                                        } catch (java.lang.NumberFormatException e) {
                                            printWriter.println("Bad value: " + str2);
                                        }
                                    } else if (strArr.length == 1 && "reset".equals(strArr[0])) {
                                        com.android.server.DockObserver.this.mUpdatesStopped = false;
                                        com.android.server.DockObserver.this.setDockStateLocked(com.android.server.DockObserver.this.mActualDockState);
                                    } else {
                                        printWriter.println("Dump current dock state, or:");
                                        printWriter.println("  set state <value>");
                                        printWriter.println("  reset");
                                    }
                                }
                            } catch (java.lang.Throwable th) {
                                throw th;
                            }
                        }
                        printWriter.println("Current Dock Observer Service state:");
                        if (com.android.server.DockObserver.this.mUpdatesStopped) {
                            printWriter.println("  (UPDATES STOPPED -- use 'reset' to restart)");
                        }
                        printWriter.println("  reported state: " + com.android.server.DockObserver.this.mReportedDockState);
                        printWriter.println("  previous state: " + com.android.server.DockObserver.this.mPreviousDockState);
                        printWriter.println("  actual state: " + com.android.server.DockObserver.this.mActualDockState);
                    }
                } finally {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }
        }
    }

    private final class DeviceProvisionedObserver extends android.database.ContentObserver {
        private boolean mRegistered;

        public DeviceProvisionedObserver(android.os.Handler handler) {
            super(handler);
        }

        @Override // android.database.ContentObserver
        public void onChange(boolean z, android.net.Uri uri) {
            synchronized (com.android.server.DockObserver.this.mLock) {
                try {
                    updateRegistration();
                    if (isDeviceProvisioned()) {
                        com.android.server.DockObserver.this.updateIfDockedLocked();
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        void onSystemReady() {
            updateRegistration();
        }

        private void updateRegistration() {
            boolean z = !isDeviceProvisioned();
            if (z == this.mRegistered) {
                return;
            }
            android.content.ContentResolver contentResolver = com.android.server.DockObserver.this.getContext().getContentResolver();
            if (z) {
                contentResolver.registerContentObserver(android.provider.Settings.Global.getUriFor("device_provisioned"), false, this);
            } else {
                contentResolver.unregisterContentObserver(this);
            }
            this.mRegistered = z;
        }

        boolean isDeviceProvisioned() {
            return android.provider.Settings.Global.getInt(com.android.server.DockObserver.this.getContext().getContentResolver(), "device_provisioned", 0) != 0;
        }
    }
}
