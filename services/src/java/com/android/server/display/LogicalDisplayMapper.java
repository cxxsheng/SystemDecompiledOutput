package com.android.server.display;

/* loaded from: classes.dex */
class LogicalDisplayMapper implements com.android.server.display.DisplayDeviceRepository.Listener {
    public static final int DISPLAY_GROUP_EVENT_ADDED = 1;
    public static final int DISPLAY_GROUP_EVENT_CHANGED = 2;
    public static final int DISPLAY_GROUP_EVENT_REMOVED = 3;
    public static final int LOGICAL_DISPLAY_EVENT_ADDED = 1;
    public static final int LOGICAL_DISPLAY_EVENT_CHANGED = 2;
    public static final int LOGICAL_DISPLAY_EVENT_CONNECTED = 8;
    public static final int LOGICAL_DISPLAY_EVENT_DEVICE_STATE_TRANSITION = 6;
    public static final int LOGICAL_DISPLAY_EVENT_DISCONNECTED = 9;
    public static final int LOGICAL_DISPLAY_EVENT_FRAME_RATE_OVERRIDES_CHANGED = 5;
    public static final int LOGICAL_DISPLAY_EVENT_HDR_SDR_RATIO_CHANGED = 7;
    public static final int LOGICAL_DISPLAY_EVENT_REMOVED = 3;
    public static final int LOGICAL_DISPLAY_EVENT_SWAPPED = 4;
    private static final int MSG_TRANSITION_TO_PENDING_DEVICE_STATE = 1;
    private static final int TIMEOUT_STATE_TRANSITION_MILLIS = 500;
    private static final int UPDATE_STATE_NEW = 0;
    private static final int UPDATE_STATE_TRANSITION = 1;
    private static final int UPDATE_STATE_UPDATED = 2;
    private boolean mBootCompleted;
    private com.android.server.display.layout.Layout mCurrentLayout;
    private final android.util.SparseIntArray mDeviceDisplayGroupIds;
    private int mDeviceState;
    private int mDeviceStateToBeAppliedAfterBoot;
    private final com.android.server.display.DeviceStateToLayoutMap mDeviceStateToLayoutMap;
    private final android.util.SparseBooleanArray mDeviceStatesOnWhichToSleep;
    private final android.util.SparseBooleanArray mDeviceStatesOnWhichToWakeUp;
    private final com.android.server.display.DisplayDeviceRepository mDisplayDeviceRepo;
    private final android.util.ArrayMap<java.lang.String, java.lang.Integer> mDisplayGroupIdsByName;
    private final android.util.SparseArray<com.android.server.display.DisplayGroup> mDisplayGroups;
    private final android.util.SparseIntArray mDisplayGroupsToUpdate;
    private final android.util.SparseBooleanArray mDisplaysEnabledCache;
    private final com.android.server.display.feature.DisplayManagerFlags mFlags;
    private final com.android.server.utils.FoldSettingProvider mFoldSettingProvider;
    private final com.android.server.display.LogicalDisplayMapper.LogicalDisplayMapperHandler mHandler;
    private final com.android.server.display.layout.DisplayIdProducer mIdProducer;
    private boolean mInteractive;
    private final com.android.server.display.LogicalDisplayMapper.Listener mListener;
    private final android.util.SparseArray<com.android.server.display.LogicalDisplay> mLogicalDisplays;
    private final android.util.SparseIntArray mLogicalDisplaysToUpdate;
    private int mNextNonDefaultGroupId;
    private int mPendingDeviceState;
    private final android.os.PowerManager mPowerManager;
    private final boolean mSingleDisplayDemoMode;
    private final boolean mSupportsConcurrentInternalDisplays;
    private final com.android.server.display.DisplayManagerService.SyncRoot mSyncRoot;
    private final android.view.DisplayInfo mTempDisplayInfo;
    private final android.view.DisplayInfo mTempNonOverrideDisplayInfo;
    private final android.util.SparseIntArray mUpdatedDisplayGroups;
    private final android.util.SparseIntArray mUpdatedLogicalDisplays;
    private final android.util.ArrayMap<java.lang.String, java.lang.Integer> mVirtualDeviceDisplayMapping;
    private static final java.lang.String TAG = "LogicalDisplayMapper";
    private static final boolean DEBUG = com.android.server.display.utils.DebugUtils.isDebuggable(TAG);
    private static int sNextNonDefaultDisplayId = 1;

    public interface Listener {
        void onDisplayGroupEventLocked(int i, int i2);

        void onLogicalDisplayEventLocked(com.android.server.display.LogicalDisplay logicalDisplay, int i);

        void onTraversalRequested();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ int lambda$new$0(boolean z) {
        if (z) {
            return 0;
        }
        int i = sNextNonDefaultDisplayId;
        sNextNonDefaultDisplayId = i + 1;
        return i;
    }

    LogicalDisplayMapper(@android.annotation.NonNull android.content.Context context, com.android.server.utils.FoldSettingProvider foldSettingProvider, @android.annotation.NonNull com.android.server.display.DisplayDeviceRepository displayDeviceRepository, @android.annotation.NonNull com.android.server.display.LogicalDisplayMapper.Listener listener, @android.annotation.NonNull com.android.server.display.DisplayManagerService.SyncRoot syncRoot, @android.annotation.NonNull android.os.Handler handler, com.android.server.display.feature.DisplayManagerFlags displayManagerFlags) {
        this(context, foldSettingProvider, displayDeviceRepository, listener, syncRoot, handler, new com.android.server.display.DeviceStateToLayoutMap(new com.android.server.display.layout.DisplayIdProducer() { // from class: com.android.server.display.LogicalDisplayMapper$$ExternalSyntheticLambda1
            @Override // com.android.server.display.layout.DisplayIdProducer
            public final int getId(boolean z) {
                int lambda$new$1;
                lambda$new$1 = com.android.server.display.LogicalDisplayMapper.lambda$new$1(z);
                return lambda$new$1;
            }
        }, displayManagerFlags), displayManagerFlags);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ int lambda$new$1(boolean z) {
        if (z) {
            return 0;
        }
        int i = sNextNonDefaultDisplayId;
        sNextNonDefaultDisplayId = i + 1;
        return i;
    }

    LogicalDisplayMapper(@android.annotation.NonNull android.content.Context context, com.android.server.utils.FoldSettingProvider foldSettingProvider, @android.annotation.NonNull com.android.server.display.DisplayDeviceRepository displayDeviceRepository, @android.annotation.NonNull com.android.server.display.LogicalDisplayMapper.Listener listener, @android.annotation.NonNull com.android.server.display.DisplayManagerService.SyncRoot syncRoot, @android.annotation.NonNull android.os.Handler handler, @android.annotation.NonNull com.android.server.display.DeviceStateToLayoutMap deviceStateToLayoutMap, com.android.server.display.feature.DisplayManagerFlags displayManagerFlags) {
        this.mTempDisplayInfo = new android.view.DisplayInfo();
        this.mTempNonOverrideDisplayInfo = new android.view.DisplayInfo();
        this.mLogicalDisplays = new android.util.SparseArray<>();
        this.mDisplaysEnabledCache = new android.util.SparseBooleanArray();
        this.mDisplayGroups = new android.util.SparseArray<>();
        this.mDeviceDisplayGroupIds = new android.util.SparseIntArray();
        this.mDisplayGroupIdsByName = new android.util.ArrayMap<>();
        this.mUpdatedLogicalDisplays = new android.util.SparseIntArray();
        this.mUpdatedDisplayGroups = new android.util.SparseIntArray();
        this.mLogicalDisplaysToUpdate = new android.util.SparseIntArray();
        this.mDisplayGroupsToUpdate = new android.util.SparseIntArray();
        this.mVirtualDeviceDisplayMapping = new android.util.ArrayMap<>();
        this.mNextNonDefaultGroupId = 1;
        this.mIdProducer = new com.android.server.display.layout.DisplayIdProducer() { // from class: com.android.server.display.LogicalDisplayMapper$$ExternalSyntheticLambda0
            @Override // com.android.server.display.layout.DisplayIdProducer
            public final int getId(boolean z) {
                int lambda$new$0;
                lambda$new$0 = com.android.server.display.LogicalDisplayMapper.lambda$new$0(z);
                return lambda$new$0;
            }
        };
        this.mCurrentLayout = null;
        this.mDeviceState = -1;
        this.mPendingDeviceState = -1;
        this.mDeviceStateToBeAppliedAfterBoot = -1;
        this.mBootCompleted = false;
        this.mSyncRoot = syncRoot;
        this.mPowerManager = (android.os.PowerManager) context.getSystemService(android.os.PowerManager.class);
        this.mInteractive = this.mPowerManager.isInteractive();
        this.mHandler = new com.android.server.display.LogicalDisplayMapper.LogicalDisplayMapperHandler(handler.getLooper());
        this.mDisplayDeviceRepo = displayDeviceRepository;
        this.mListener = listener;
        this.mFoldSettingProvider = foldSettingProvider;
        this.mSingleDisplayDemoMode = android.os.SystemProperties.getBoolean("persist.demo.singledisplay", false);
        this.mSupportsConcurrentInternalDisplays = context.getResources().getBoolean(android.R.bool.config_supportTelephonyTimeZoneFallback);
        this.mDeviceStatesOnWhichToWakeUp = toSparseBooleanArray(context.getResources().getIntArray(android.R.array.config_deviceStatesAvailableForAppRequests));
        this.mDeviceStatesOnWhichToSleep = toSparseBooleanArray(context.getResources().getIntArray(android.R.array.config_deviceSpecificSystemServices));
        this.mDisplayDeviceRepo.addListener(this);
        this.mDeviceStateToLayoutMap = deviceStateToLayoutMap;
        this.mFlags = displayManagerFlags;
    }

    @Override // com.android.server.display.DisplayDeviceRepository.Listener
    public void onDisplayDeviceEventLocked(com.android.server.display.DisplayDevice displayDevice, int i) {
        switch (i) {
            case 1:
                if (DEBUG) {
                    android.util.Slog.d(TAG, "Display device added: " + displayDevice.getDisplayDeviceInfoLocked());
                }
                handleDisplayDeviceAddedLocked(displayDevice);
                break;
            case 3:
                if (DEBUG) {
                    android.util.Slog.d(TAG, "Display device removed: " + displayDevice.getDisplayDeviceInfoLocked());
                }
                handleDisplayDeviceRemovedLocked(displayDevice);
                updateLogicalDisplaysLocked();
                break;
        }
    }

    @Override // com.android.server.display.DisplayDeviceRepository.Listener
    public void onDisplayDeviceChangedLocked(com.android.server.display.DisplayDevice displayDevice, int i) {
        if (DEBUG) {
            android.util.Slog.d(TAG, "Display device changed: " + displayDevice.getDisplayDeviceInfoLocked());
        }
        finishStateTransitionLocked(false);
        updateLogicalDisplaysLocked(i);
    }

    @Override // com.android.server.display.DisplayDeviceRepository.Listener
    public void onTraversalRequested() {
        this.mListener.onTraversalRequested();
    }

    public com.android.server.display.LogicalDisplay getDisplayLocked(int i) {
        return getDisplayLocked(i, true);
    }

    public com.android.server.display.LogicalDisplay getDisplayLocked(int i, boolean z) {
        com.android.server.display.LogicalDisplay logicalDisplay = this.mLogicalDisplays.get(i);
        if (logicalDisplay == null || logicalDisplay.isEnabledLocked() || z) {
            return logicalDisplay;
        }
        return null;
    }

    public com.android.server.display.LogicalDisplay getDisplayLocked(com.android.server.display.DisplayDevice displayDevice) {
        return getDisplayLocked(displayDevice, true);
    }

    public com.android.server.display.LogicalDisplay getDisplayLocked(com.android.server.display.DisplayDevice displayDevice, boolean z) {
        if (displayDevice == null) {
            return null;
        }
        int size = this.mLogicalDisplays.size();
        for (int i = 0; i < size; i++) {
            com.android.server.display.LogicalDisplay valueAt = this.mLogicalDisplays.valueAt(i);
            if (valueAt.getPrimaryDisplayDeviceLocked() == displayDevice) {
                if (!valueAt.isEnabledLocked() && !z) {
                    return null;
                }
                return valueAt;
            }
        }
        return null;
    }

    public int[] getDisplayIdsLocked(int i, boolean z) {
        int size = this.mLogicalDisplays.size();
        int[] iArr = new int[size];
        int i2 = 0;
        for (int i3 = 0; i3 < size; i3++) {
            com.android.server.display.LogicalDisplay valueAt = this.mLogicalDisplays.valueAt(i3);
            if ((valueAt.isEnabledLocked() || z) && valueAt.getDisplayInfoLocked().hasAccess(i)) {
                iArr[i2] = this.mLogicalDisplays.keyAt(i3);
                i2++;
            }
        }
        if (i2 != size) {
            return java.util.Arrays.copyOfRange(iArr, 0, i2);
        }
        return iArr;
    }

    public void forEachLocked(java.util.function.Consumer<com.android.server.display.LogicalDisplay> consumer) {
        forEachLocked(consumer, true);
    }

    public void forEachLocked(java.util.function.Consumer<com.android.server.display.LogicalDisplay> consumer, boolean z) {
        int size = this.mLogicalDisplays.size();
        for (int i = 0; i < size; i++) {
            com.android.server.display.LogicalDisplay valueAt = this.mLogicalDisplays.valueAt(i);
            if (valueAt.isEnabledLocked() || z) {
                consumer.accept(valueAt);
            }
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    public int getDisplayGroupIdFromDisplayIdLocked(int i) {
        com.android.server.display.LogicalDisplay displayLocked = getDisplayLocked(i);
        if (displayLocked == null) {
            return -1;
        }
        int size = this.mDisplayGroups.size();
        for (int i2 = 0; i2 < size; i2++) {
            if (this.mDisplayGroups.valueAt(i2).containsLocked(displayLocked)) {
                return this.mDisplayGroups.keyAt(i2);
            }
        }
        return -1;
    }

    public com.android.server.display.DisplayGroup getDisplayGroupLocked(int i) {
        return this.mDisplayGroups.get(i);
    }

    @android.annotation.Nullable
    public android.view.DisplayInfo getDisplayInfoForStateLocked(int i, int i2) {
        com.android.server.display.layout.Layout.Display byId;
        com.android.server.display.layout.Layout layout = this.mDeviceStateToLayoutMap.get(i);
        if (layout == null || (byId = layout.getById(i2)) == null) {
            return null;
        }
        com.android.server.display.DisplayDevice byAddressLocked = this.mDisplayDeviceRepo.getByAddressLocked(byId.getAddress());
        if (byAddressLocked == null) {
            android.util.Slog.w(TAG, "The display device (" + byId.getAddress() + "), is not available for the display state " + this.mDeviceState);
            return null;
        }
        com.android.server.display.LogicalDisplay displayLocked = getDisplayLocked(byAddressLocked, true);
        if (displayLocked == null) {
            android.util.Slog.w(TAG, "The logical display associated with address (" + byId.getAddress() + "), is not available for the display state " + this.mDeviceState);
            return null;
        }
        android.view.DisplayInfo displayInfo = new android.view.DisplayInfo(displayLocked.getDisplayInfoLocked());
        displayInfo.displayId = i2;
        return displayInfo;
    }

    public void dumpLocked(java.io.PrintWriter printWriter) {
        printWriter.println("LogicalDisplayMapper:");
        java.io.PrintWriter indentingPrintWriter = new android.util.IndentingPrintWriter(printWriter, "  ");
        indentingPrintWriter.increaseIndent();
        indentingPrintWriter.println("mSingleDisplayDemoMode=" + this.mSingleDisplayDemoMode);
        indentingPrintWriter.println("mCurrentLayout=" + this.mCurrentLayout);
        indentingPrintWriter.println("mDeviceStatesOnWhichToWakeUp=" + this.mDeviceStatesOnWhichToWakeUp);
        indentingPrintWriter.println("mDeviceStatesOnWhichToSleep=" + this.mDeviceStatesOnWhichToSleep);
        indentingPrintWriter.println("mInteractive=" + this.mInteractive);
        indentingPrintWriter.println("mBootCompleted=" + this.mBootCompleted);
        indentingPrintWriter.println();
        indentingPrintWriter.println("mDeviceState=" + this.mDeviceState);
        indentingPrintWriter.println("mPendingDeviceState=" + this.mPendingDeviceState);
        indentingPrintWriter.println("mDeviceStateToBeAppliedAfterBoot=" + this.mDeviceStateToBeAppliedAfterBoot);
        int size = this.mLogicalDisplays.size();
        indentingPrintWriter.println();
        indentingPrintWriter.println("Logical Displays: size=" + size);
        for (int i = 0; i < size; i++) {
            int keyAt = this.mLogicalDisplays.keyAt(i);
            com.android.server.display.LogicalDisplay valueAt = this.mLogicalDisplays.valueAt(i);
            indentingPrintWriter.println("Display " + keyAt + ":");
            indentingPrintWriter.increaseIndent();
            valueAt.dumpLocked(indentingPrintWriter);
            indentingPrintWriter.decreaseIndent();
            indentingPrintWriter.println();
        }
        this.mDeviceStateToLayoutMap.dumpLocked(indentingPrintWriter);
    }

    void associateDisplayDeviceWithVirtualDevice(com.android.server.display.DisplayDevice displayDevice, int i) {
        this.mVirtualDeviceDisplayMapping.put(displayDevice.getUniqueId(), java.lang.Integer.valueOf(i));
    }

    void setDeviceStateLocked(int i, boolean z) {
        if (!this.mBootCompleted) {
            if (DEBUG) {
                android.util.Slog.d(TAG, "Postponing transition to state: " + this.mPendingDeviceState + " until boot is completed");
            }
            this.mDeviceStateToBeAppliedAfterBoot = i;
            return;
        }
        android.util.Slog.i(TAG, "Requesting Transition to state: " + i + ", from state=" + this.mDeviceState + ", interactive=" + this.mInteractive + ", mBootCompleted=" + this.mBootCompleted);
        resetLayoutLocked(this.mDeviceState, i, true);
        this.mPendingDeviceState = i;
        this.mDeviceStateToBeAppliedAfterBoot = -1;
        boolean shouldDeviceBeWoken = shouldDeviceBeWoken(this.mPendingDeviceState, this.mDeviceState, this.mInteractive, this.mBootCompleted);
        boolean shouldDeviceBePutToSleep = shouldDeviceBePutToSleep(this.mPendingDeviceState, this.mDeviceState, z, this.mInteractive, this.mBootCompleted);
        if (areAllTransitioningDisplaysOffLocked() && !shouldDeviceBeWoken && !shouldDeviceBePutToSleep) {
            transitionToPendingStateLocked();
            return;
        }
        if (DEBUG) {
            android.util.Slog.d(TAG, "Postponing transition to state: " + this.mPendingDeviceState);
        }
        updateLogicalDisplaysLocked();
        if (shouldDeviceBeWoken || shouldDeviceBePutToSleep) {
            if (shouldDeviceBeWoken) {
                this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.display.LogicalDisplayMapper$$ExternalSyntheticLambda2
                    @Override // java.lang.Runnable
                    public final void run() {
                        com.android.server.display.LogicalDisplayMapper.this.lambda$setDeviceStateLocked$2();
                    }
                });
            } else if (shouldDeviceBePutToSleep) {
                final int i2 = this.mFoldSettingProvider.shouldSleepOnFold() ? 0 : 2;
                this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.display.LogicalDisplayMapper$$ExternalSyntheticLambda3
                    @Override // java.lang.Runnable
                    public final void run() {
                        com.android.server.display.LogicalDisplayMapper.this.lambda$setDeviceStateLocked$3(i2);
                    }
                });
            }
        }
        this.mHandler.sendEmptyMessageDelayed(1, 500L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setDeviceStateLocked$2() {
        this.mPowerManager.wakeUp(android.os.SystemClock.uptimeMillis(), 12, "server.display:unfold");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setDeviceStateLocked$3(int i) {
        this.mPowerManager.goToSleep(android.os.SystemClock.uptimeMillis(), 13, i);
    }

    void onBootCompleted() {
        synchronized (this.mSyncRoot) {
            try {
                this.mBootCompleted = true;
                if (this.mDeviceStateToBeAppliedAfterBoot != -1) {
                    setDeviceStateLocked(this.mDeviceStateToBeAppliedAfterBoot, false);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    void onEarlyInteractivityChange(boolean z) {
        synchronized (this.mSyncRoot) {
            try {
                if (this.mInteractive != z) {
                    this.mInteractive = z;
                    finishStateTransitionLocked(false);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    boolean shouldDeviceBeWoken(int i, int i2, boolean z, boolean z2) {
        return this.mDeviceStatesOnWhichToWakeUp.get(i) && !this.mDeviceStatesOnWhichToWakeUp.get(i2) && !z && z2;
    }

    @com.android.internal.annotations.VisibleForTesting
    boolean shouldDeviceBePutToSleep(int i, int i2, boolean z, boolean z2, boolean z3) {
        return (i2 == -1 || !this.mDeviceStatesOnWhichToSleep.get(i) || this.mDeviceStatesOnWhichToSleep.get(i2) || z || !z2 || !z3 || this.mFoldSettingProvider.shouldStayAwakeOnFold()) ? false : true;
    }

    private boolean areAllTransitioningDisplaysOffLocked() {
        com.android.server.display.DisplayDevice primaryDisplayDeviceLocked;
        int size = this.mLogicalDisplays.size();
        for (int i = 0; i < size; i++) {
            com.android.server.display.LogicalDisplay valueAt = this.mLogicalDisplays.valueAt(i);
            if (valueAt.isInTransitionLocked() && (primaryDisplayDeviceLocked = valueAt.getPrimaryDisplayDeviceLocked()) != null && primaryDisplayDeviceLocked.getDisplayDeviceInfoLocked().state != 1) {
                return false;
            }
        }
        return true;
    }

    private void transitionToPendingStateLocked() {
        resetLayoutLocked(this.mDeviceState, this.mPendingDeviceState, false);
        this.mDeviceState = this.mPendingDeviceState;
        this.mPendingDeviceState = -1;
        applyLayoutLocked();
        updateLogicalDisplaysLocked();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void finishStateTransitionLocked(boolean z) {
        if (this.mPendingDeviceState == -1) {
            return;
        }
        boolean z2 = false;
        boolean z3 = this.mDeviceStatesOnWhichToWakeUp.get(this.mPendingDeviceState) && !this.mDeviceStatesOnWhichToWakeUp.get(this.mDeviceState) && !this.mInteractive && this.mBootCompleted;
        boolean z4 = this.mDeviceStatesOnWhichToSleep.get(this.mPendingDeviceState) && !this.mDeviceStatesOnWhichToSleep.get(this.mDeviceState) && this.mInteractive && this.mBootCompleted;
        boolean areAllTransitioningDisplaysOffLocked = areAllTransitioningDisplaysOffLocked();
        if (areAllTransitioningDisplaysOffLocked && !z3 && !z4) {
            z2 = true;
        }
        if (z2 || z) {
            transitionToPendingStateLocked();
            this.mHandler.removeMessages(1);
            return;
        }
        if (DEBUG) {
            android.util.Slog.d(TAG, "Not yet ready to transition to state=" + this.mPendingDeviceState + " with displays-off=" + areAllTransitioningDisplaysOffLocked + ", force=" + z + ", mInteractive=" + this.mInteractive + ", isReady=" + z2);
        }
    }

    private void handleDisplayDeviceAddedLocked(com.android.server.display.DisplayDevice displayDevice) {
        if ((displayDevice.getDisplayDeviceInfoLocked().flags & 1) != 0) {
            initializeDefaultDisplayDeviceLocked(displayDevice);
        }
        createNewLogicalDisplayLocked(displayDevice, this.mIdProducer.getId(false));
        applyLayoutLocked();
        updateLogicalDisplaysLocked();
    }

    private void handleDisplayDeviceRemovedLocked(com.android.server.display.DisplayDevice displayDevice) {
        com.android.server.display.layout.Layout layout = this.mDeviceStateToLayoutMap.get(-1);
        com.android.server.display.layout.Layout.Display byId = layout.getById(0);
        if (byId == null) {
            return;
        }
        com.android.server.display.DisplayDeviceInfo displayDeviceInfoLocked = displayDevice.getDisplayDeviceInfoLocked();
        this.mVirtualDeviceDisplayMapping.remove(displayDevice.getUniqueId());
        if (byId.getAddress().equals(displayDeviceInfoLocked.address)) {
            layout.removeDisplayLocked(0);
            for (int i = 0; i < this.mLogicalDisplays.size(); i++) {
                com.android.server.display.DisplayDevice primaryDisplayDeviceLocked = this.mLogicalDisplays.valueAt(i).getPrimaryDisplayDeviceLocked();
                if (primaryDisplayDeviceLocked != null) {
                    com.android.server.display.DisplayDeviceInfo displayDeviceInfoLocked2 = primaryDisplayDeviceLocked.getDisplayDeviceInfoLocked();
                    if ((displayDeviceInfoLocked2.flags & 1) != 0 && !displayDeviceInfoLocked2.address.equals(displayDeviceInfoLocked.address)) {
                        layout.createDefaultDisplayLocked(displayDeviceInfoLocked2.address, this.mIdProducer);
                        applyLayoutLocked();
                        return;
                    }
                }
            }
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    void updateLogicalDisplays() {
        synchronized (this.mSyncRoot) {
            updateLogicalDisplaysLocked();
        }
    }

    void updateLogicalDisplaysLocked() {
        updateLogicalDisplaysLocked(-1);
    }

    private void updateLogicalDisplaysLocked(int i) {
        updateLogicalDisplaysLocked(i, false);
    }

    private void updateLogicalDisplaysLocked(int i, boolean z) {
        int i2;
        int size = this.mLogicalDisplays.size() - 1;
        int i3 = 0;
        boolean z2 = false;
        while (size >= 0) {
            int keyAt = this.mLogicalDisplays.keyAt(size);
            com.android.server.display.LogicalDisplay valueAt = this.mLogicalDisplays.valueAt(size);
            assignDisplayGroupLocked(valueAt);
            boolean isDirtyLocked = valueAt.isDirtyLocked();
            this.mTempDisplayInfo.copyFrom(valueAt.getDisplayInfoLocked());
            valueAt.getNonOverrideDisplayInfoLocked(this.mTempNonOverrideDisplayInfo);
            valueAt.updateLocked(this.mDisplayDeviceRepo);
            android.view.DisplayInfo displayInfoLocked = valueAt.getDisplayInfoLocked();
            int i4 = this.mUpdatedLogicalDisplays.get(keyAt, i3);
            int i5 = i4 != 0 ? 1 : i3;
            boolean z3 = this.mDisplaysEnabledCache.get(keyAt);
            boolean isEnabledLocked = valueAt.isEnabledLocked();
            if (!valueAt.isValidLocked()) {
                com.android.server.display.DisplayGroup displayGroupLocked = getDisplayGroupLocked(getDisplayGroupIdFromDisplayIdLocked(keyAt));
                if (displayGroupLocked != null) {
                    displayGroupLocked.removeDisplayLocked(valueAt);
                }
                if (i5 != 0) {
                    if (this.mFlags.isConnectedDisplayManagementEnabled()) {
                        if (this.mDisplaysEnabledCache.get(keyAt)) {
                            this.mLogicalDisplaysToUpdate.put(keyAt, 3);
                            z2 = true;
                        } else {
                            this.mUpdatedLogicalDisplays.delete(keyAt);
                            this.mLogicalDisplaysToUpdate.put(keyAt, 9);
                        }
                    } else {
                        this.mUpdatedLogicalDisplays.delete(keyAt);
                        this.mLogicalDisplaysToUpdate.put(keyAt, 3);
                    }
                } else {
                    this.mLogicalDisplays.removeAt(size);
                }
            } else {
                if (i5 == 0) {
                    if (this.mFlags.isConnectedDisplayManagementEnabled()) {
                        this.mLogicalDisplaysToUpdate.put(keyAt, 8);
                        z2 = true;
                        i2 = 2;
                    } else {
                        this.mLogicalDisplaysToUpdate.put(keyAt, 1);
                        i2 = 2;
                    }
                } else if (!android.text.TextUtils.equals(this.mTempDisplayInfo.uniqueId, displayInfoLocked.uniqueId)) {
                    this.mLogicalDisplaysToUpdate.put(keyAt, 4);
                    i2 = 2;
                } else if (!this.mFlags.isConnectedDisplayManagementEnabled() || z3 == isEnabledLocked) {
                    if (isDirtyLocked || !this.mTempDisplayInfo.equals(displayInfoLocked)) {
                        if (i == 8) {
                            this.mLogicalDisplaysToUpdate.put(keyAt, 7);
                            i2 = 2;
                        } else {
                            i2 = 2;
                            this.mLogicalDisplaysToUpdate.put(keyAt, 2);
                        }
                    } else if (i4 == 1) {
                        this.mLogicalDisplaysToUpdate.put(keyAt, 6);
                        i2 = 2;
                    } else if (!valueAt.getPendingFrameRateOverrideUids().isEmpty()) {
                        this.mLogicalDisplaysToUpdate.put(keyAt, 5);
                        i2 = 2;
                    } else {
                        valueAt.getNonOverrideDisplayInfoLocked(this.mTempDisplayInfo);
                        if (this.mTempNonOverrideDisplayInfo.equals(this.mTempDisplayInfo)) {
                            i2 = 2;
                        } else {
                            i2 = 2;
                            this.mLogicalDisplaysToUpdate.put(keyAt, 2);
                        }
                    }
                } else {
                    this.mLogicalDisplaysToUpdate.put(keyAt, isEnabledLocked ? 1 : 3);
                    i2 = 2;
                }
                this.mUpdatedLogicalDisplays.put(keyAt, i2);
            }
            size--;
            i3 = 0;
        }
        for (int size2 = this.mDisplayGroups.size() - 1; size2 >= 0; size2--) {
            int keyAt2 = this.mDisplayGroups.keyAt(size2);
            com.android.server.display.DisplayGroup valueAt2 = this.mDisplayGroups.valueAt(size2);
            boolean z4 = this.mUpdatedDisplayGroups.indexOfKey(keyAt2) > -1;
            int changeCountLocked = valueAt2.getChangeCountLocked();
            if (valueAt2.isEmptyLocked()) {
                this.mUpdatedDisplayGroups.delete(keyAt2);
                if (z4) {
                    this.mDisplayGroupsToUpdate.put(keyAt2, 3);
                }
            } else {
                if (!z4) {
                    this.mDisplayGroupsToUpdate.put(keyAt2, 1);
                } else if (this.mUpdatedDisplayGroups.get(keyAt2) != changeCountLocked) {
                    this.mDisplayGroupsToUpdate.put(keyAt2, 2);
                }
                this.mUpdatedDisplayGroups.put(keyAt2, changeCountLocked);
            }
        }
        sendUpdatesForDisplaysLocked(6);
        sendUpdatesForGroupsLocked(1);
        sendUpdatesForDisplaysLocked(3);
        if (this.mFlags.isConnectedDisplayManagementEnabled()) {
            sendUpdatesForDisplaysLocked(9);
        }
        sendUpdatesForDisplaysLocked(2);
        sendUpdatesForDisplaysLocked(5);
        sendUpdatesForDisplaysLocked(4);
        if (this.mFlags.isConnectedDisplayManagementEnabled()) {
            sendUpdatesForDisplaysLocked(8);
        }
        sendUpdatesForDisplaysLocked(1);
        sendUpdatesForDisplaysLocked(7);
        sendUpdatesForGroupsLocked(2);
        sendUpdatesForGroupsLocked(3);
        this.mLogicalDisplaysToUpdate.clear();
        this.mDisplayGroupsToUpdate.clear();
        if (z2) {
            if (z) {
                android.util.Slog.wtf(TAG, "Trying to loop a third time");
            } else {
                updateLogicalDisplaysLocked(i, true);
            }
        }
    }

    private void sendUpdatesForDisplaysLocked(int i) {
        for (int size = this.mLogicalDisplaysToUpdate.size() - 1; size >= 0; size--) {
            if (this.mLogicalDisplaysToUpdate.valueAt(size) == i) {
                int keyAt = this.mLogicalDisplaysToUpdate.keyAt(size);
                com.android.server.display.LogicalDisplay displayLocked = getDisplayLocked(keyAt);
                if (DEBUG) {
                    com.android.server.display.DisplayDevice primaryDisplayDeviceLocked = displayLocked.getPrimaryDisplayDeviceLocked();
                    android.util.Slog.d(TAG, "Sending " + displayEventToString(i) + " for display=" + keyAt + " with device=" + (primaryDisplayDeviceLocked == null ? "null" : primaryDisplayDeviceLocked.getUniqueId()));
                }
                if (this.mFlags.isConnectedDisplayManagementEnabled()) {
                    if (i == 1) {
                        this.mDisplaysEnabledCache.put(keyAt, true);
                    } else if (i == 3) {
                        this.mDisplaysEnabledCache.delete(keyAt);
                    }
                }
                this.mListener.onLogicalDisplayEventLocked(displayLocked, i);
                if (this.mFlags.isConnectedDisplayManagementEnabled()) {
                    if (i == 9) {
                        this.mLogicalDisplays.delete(keyAt);
                    }
                } else if (i == 3) {
                    this.mLogicalDisplays.delete(keyAt);
                }
            }
        }
    }

    private void sendUpdatesForGroupsLocked(int i) {
        for (int size = this.mDisplayGroupsToUpdate.size() - 1; size >= 0; size--) {
            if (this.mDisplayGroupsToUpdate.valueAt(size) == i) {
                int keyAt = this.mDisplayGroupsToUpdate.keyAt(size);
                this.mListener.onDisplayGroupEventLocked(keyAt, i);
                if (i == 3) {
                    this.mDisplayGroups.delete(keyAt);
                    int indexOfValue = this.mDeviceDisplayGroupIds.indexOfValue(keyAt);
                    if (indexOfValue >= 0) {
                        this.mDeviceDisplayGroupIds.removeAt(indexOfValue);
                    }
                }
            }
        }
    }

    private void assignDisplayGroupLocked(com.android.server.display.LogicalDisplay logicalDisplay) {
        java.lang.Integer num;
        if (!logicalDisplay.isValidLocked()) {
            return;
        }
        com.android.server.display.DisplayDevice primaryDisplayDeviceLocked = logicalDisplay.getPrimaryDisplayDeviceLocked();
        int displayIdLocked = logicalDisplay.getDisplayIdLocked();
        java.lang.Integer num2 = this.mVirtualDeviceDisplayMapping.get(primaryDisplayDeviceLocked.getUniqueId());
        int displayGroupIdFromDisplayIdLocked = getDisplayGroupIdFromDisplayIdLocked(displayIdLocked);
        if (num2 != null && this.mDeviceDisplayGroupIds.indexOfKey(num2.intValue()) > 0) {
            num = java.lang.Integer.valueOf(this.mDeviceDisplayGroupIds.get(num2.intValue()));
        } else {
            num = null;
        }
        com.android.server.display.DisplayGroup displayGroupLocked = getDisplayGroupLocked(displayGroupIdFromDisplayIdLocked);
        boolean z = ((primaryDisplayDeviceLocked.getDisplayDeviceInfoLocked().flags & 16384) == 0 && android.text.TextUtils.isEmpty(logicalDisplay.getDisplayGroupNameLocked())) ? false : true;
        boolean z2 = displayGroupIdFromDisplayIdLocked != 0;
        boolean z3 = (z || num2 == null) ? false : true;
        boolean z4 = num != null && displayGroupIdFromDisplayIdLocked == num.intValue();
        if (displayGroupIdFromDisplayIdLocked == -1 || z2 != z || z4 != z3) {
            displayGroupIdFromDisplayIdLocked = assignDisplayGroupIdLocked(z, logicalDisplay.getDisplayGroupNameLocked(), z3, num2);
        }
        com.android.server.display.DisplayGroup displayGroupLocked2 = getDisplayGroupLocked(displayGroupIdFromDisplayIdLocked);
        if (displayGroupLocked2 == null) {
            displayGroupLocked2 = new com.android.server.display.DisplayGroup(displayGroupIdFromDisplayIdLocked);
            this.mDisplayGroups.append(displayGroupIdFromDisplayIdLocked, displayGroupLocked2);
        }
        if (displayGroupLocked != displayGroupLocked2) {
            if (displayGroupLocked != null) {
                displayGroupLocked.removeDisplayLocked(logicalDisplay);
            }
            displayGroupLocked2.addDisplayLocked(logicalDisplay);
            logicalDisplay.updateDisplayGroupIdLocked(displayGroupIdFromDisplayIdLocked);
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            sb.append("Setting new display group ");
            sb.append(displayGroupIdFromDisplayIdLocked);
            sb.append(" for display ");
            sb.append(displayIdLocked);
            sb.append(", from previous group: ");
            sb.append(displayGroupLocked != null ? java.lang.Integer.valueOf(displayGroupLocked.getGroupId()) : "null");
            android.util.Slog.i(TAG, sb.toString());
        }
    }

    private void resetLayoutLocked(int i, int i2, boolean z) {
        boolean z2;
        boolean z3;
        boolean z4;
        boolean z5;
        boolean z6;
        boolean z7;
        boolean z8;
        com.android.server.display.layout.Layout layout = this.mDeviceStateToLayoutMap.get(i);
        com.android.server.display.layout.Layout layout2 = this.mDeviceStateToLayoutMap.get(i2);
        int size = this.mLogicalDisplays.size();
        for (int i3 = 0; i3 < size; i3++) {
            com.android.server.display.LogicalDisplay valueAt = this.mLogicalDisplays.valueAt(i3);
            int displayIdLocked = valueAt.getDisplayIdLocked();
            com.android.server.display.DisplayDevice primaryDisplayDeviceLocked = valueAt.getPrimaryDisplayDeviceLocked();
            if (primaryDisplayDeviceLocked != null) {
                android.view.DisplayAddress displayAddress = primaryDisplayDeviceLocked.getDisplayDeviceInfoLocked().address;
                com.android.server.display.layout.Layout.Display byAddress = displayAddress != null ? layout.getByAddress(displayAddress) : null;
                com.android.server.display.layout.Layout.Display byAddress2 = displayAddress != null ? layout2.getByAddress(displayAddress) : null;
                if (byAddress != null) {
                    z2 = false;
                } else {
                    z2 = true;
                }
                if (byAddress2 != null) {
                    z3 = false;
                } else {
                    z3 = true;
                }
                if (z2 == z3) {
                    z4 = false;
                } else {
                    z4 = true;
                }
                if (byAddress != null && !byAddress.isEnabled()) {
                    z5 = false;
                } else {
                    z5 = true;
                }
                if (byAddress2 != null && !byAddress2.isEnabled()) {
                    z6 = false;
                } else {
                    z6 = true;
                }
                if (byAddress == null || byAddress2 == null || byAddress.getLogicalDisplayId() == byAddress2.getLogicalDisplayId()) {
                    z7 = false;
                } else {
                    z7 = true;
                }
                if (!valueAt.isInTransitionLocked() && z5 == z6 && !z7 && !z4) {
                    z8 = false;
                } else {
                    z8 = true;
                }
                if (z8) {
                    if (z != valueAt.isInTransitionLocked()) {
                        android.util.Slog.i(TAG, "Set isInTransition on display " + displayIdLocked + ": " + z);
                    }
                    valueAt.setIsInTransitionLocked(z);
                    this.mUpdatedLogicalDisplays.put(displayIdLocked, 1);
                }
            }
        }
    }

    private void applyLayoutLocked() {
        java.lang.String thermalBrightnessThrottlingMapId;
        com.android.server.display.layout.Layout layout = this.mCurrentLayout;
        this.mCurrentLayout = this.mDeviceStateToLayoutMap.get(this.mDeviceState);
        android.util.Slog.i(TAG, "Applying layout: " + this.mCurrentLayout + ", Previous layout: " + layout);
        int size = this.mCurrentLayout.size();
        for (int i = 0; i < size; i++) {
            com.android.server.display.layout.Layout.Display at = this.mCurrentLayout.getAt(i);
            android.view.DisplayAddress address = at.getAddress();
            com.android.server.display.DisplayDevice byAddressLocked = this.mDisplayDeviceRepo.getByAddressLocked(address);
            if (byAddressLocked == null) {
                android.util.Slog.w(TAG, "applyLayoutLocked: The display device (" + address + "), is not available for the display state " + this.mDeviceState);
            } else {
                int logicalDisplayId = at.getLogicalDisplayId();
                com.android.server.display.LogicalDisplay displayLocked = getDisplayLocked(logicalDisplayId);
                if (displayLocked == null) {
                    displayLocked = createNewLogicalDisplayLocked(null, logicalDisplayId);
                }
                com.android.server.display.LogicalDisplay displayLocked2 = getDisplayLocked(byAddressLocked);
                if (displayLocked != displayLocked2) {
                    displayLocked.swapDisplaysLocked(displayLocked2);
                }
                com.android.server.display.DisplayDeviceConfig displayDeviceConfig = byAddressLocked.getDisplayDeviceConfig();
                displayLocked.setDevicePositionLocked(at.getPosition());
                displayLocked.setLeadDisplayLocked(at.getLeadDisplayId());
                displayLocked.updateLayoutLimitedRefreshRateLocked(displayDeviceConfig.getRefreshRange(at.getRefreshRateZoneId()));
                displayLocked.updateThermalRefreshRateThrottling(displayDeviceConfig.getThermalRefreshRateThrottlingData(at.getRefreshRateThermalThrottlingMapId()));
                setEnabledLocked(displayLocked, at.isEnabled());
                if (at.getThermalBrightnessThrottlingMapId() == null) {
                    thermalBrightnessThrottlingMapId = "default";
                } else {
                    thermalBrightnessThrottlingMapId = at.getThermalBrightnessThrottlingMapId();
                }
                displayLocked.setThermalBrightnessThrottlingDataIdLocked(thermalBrightnessThrottlingMapId);
                displayLocked.setPowerThrottlingDataIdLocked(at.getPowerThrottlingMapId() != null ? at.getPowerThrottlingMapId() : "default");
                displayLocked.setDisplayGroupNameLocked(at.getDisplayGroupName());
            }
        }
    }

    private com.android.server.display.LogicalDisplay createNewLogicalDisplayLocked(com.android.server.display.DisplayDevice displayDevice, int i) {
        com.android.server.display.LogicalDisplay logicalDisplay = new com.android.server.display.LogicalDisplay(i, assignLayerStackLocked(i), displayDevice);
        logicalDisplay.updateLocked(this.mDisplayDeviceRepo);
        if (logicalDisplay.getDisplayInfoLocked().type == 1 && this.mDeviceStateToLayoutMap.size() > 1) {
            logicalDisplay.setEnabledLocked(false);
        }
        this.mLogicalDisplays.put(i, logicalDisplay);
        return logicalDisplay;
    }

    /* JADX WARN: Code restructure failed: missing block: B:4:0x0010, code lost:
    
        if (r1.type != 1) goto L8;
     */
    @com.android.internal.annotations.VisibleForTesting
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    void setEnabledLocked(com.android.server.display.LogicalDisplay logicalDisplay, boolean z) {
        boolean z2;
        int displayIdLocked = logicalDisplay.getDisplayIdLocked();
        android.view.DisplayInfo displayInfoLocked = logicalDisplay.getDisplayInfoLocked();
        if (this.mSingleDisplayDemoMode) {
            z2 = true;
        }
        z2 = false;
        if (z && z2) {
            android.util.Slog.i(TAG, "Not creating a logical display for a secondary display because single display demo mode is enabled: " + logicalDisplay.getDisplayInfoLocked());
            z = false;
        }
        if (logicalDisplay.isEnabledLocked() != z) {
            android.util.Slog.i(TAG, "SetEnabled on display " + displayIdLocked + ": " + z);
            logicalDisplay.setEnabledLocked(z);
        }
    }

    private int assignDisplayGroupIdLocked(boolean z, java.lang.String str, boolean z2, java.lang.Integer num) {
        if (z2 && num != null) {
            int i = this.mDeviceDisplayGroupIds.get(num.intValue());
            if (i == 0) {
                int i2 = this.mNextNonDefaultGroupId;
                this.mNextNonDefaultGroupId = i2 + 1;
                this.mDeviceDisplayGroupIds.put(num.intValue(), i2);
                return i2;
            }
            return i;
        }
        if (!z) {
            return 0;
        }
        java.lang.Integer num2 = this.mDisplayGroupIdsByName.get(str);
        if (num2 == null) {
            int i3 = this.mNextNonDefaultGroupId;
            this.mNextNonDefaultGroupId = i3 + 1;
            num2 = java.lang.Integer.valueOf(i3);
            this.mDisplayGroupIdsByName.put(str, num2);
        }
        return num2.intValue();
    }

    private void initializeDefaultDisplayDeviceLocked(com.android.server.display.DisplayDevice displayDevice) {
        com.android.server.display.layout.Layout layout = this.mDeviceStateToLayoutMap.get(-1);
        if (layout.getById(0) != null) {
            return;
        }
        layout.createDefaultDisplayLocked(displayDevice.getDisplayDeviceInfoLocked().address, this.mIdProducer);
    }

    private int assignLayerStackLocked(int i) {
        return i;
    }

    private android.util.SparseBooleanArray toSparseBooleanArray(int[] iArr) {
        android.util.SparseBooleanArray sparseBooleanArray = new android.util.SparseBooleanArray(2);
        for (int i = 0; iArr != null && i < iArr.length; i++) {
            sparseBooleanArray.put(iArr[i], true);
        }
        return sparseBooleanArray;
    }

    private java.lang.String displayEventToString(int i) {
        switch (i) {
            case 1:
                return "added";
            case 2:
                return "changed";
            case 3:
                return "removed";
            case 4:
                return "swapped";
            case 5:
                return "framerate_override";
            case 6:
                return "transition";
            case 7:
                return "hdr_sdr_ratio_changed";
            case 8:
                return "connected";
            case 9:
                return "disconnected";
            default:
                return null;
        }
    }

    void setDisplayEnabledLocked(@android.annotation.NonNull com.android.server.display.LogicalDisplay logicalDisplay, boolean z) {
        boolean isEnabledLocked = logicalDisplay.isEnabledLocked();
        if (isEnabledLocked == z) {
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            sb.append("Display is already ");
            sb.append(isEnabledLocked ? com.android.server.timezonedetector.ServiceConfigAccessor.PROVIDER_MODE_ENABLED : com.android.server.timezonedetector.ServiceConfigAccessor.PROVIDER_MODE_DISABLED);
            sb.append(": ");
            sb.append(logicalDisplay.getDisplayIdLocked());
            android.util.Slog.w(TAG, sb.toString());
            return;
        }
        setEnabledLocked(logicalDisplay, z);
        updateLogicalDisplaysLocked();
    }

    private class LogicalDisplayMapperHandler extends android.os.Handler {
        LogicalDisplayMapperHandler(android.os.Looper looper) {
            super(looper, null, true);
        }

        @Override // android.os.Handler
        public void handleMessage(android.os.Message message) {
            switch (message.what) {
                case 1:
                    synchronized (com.android.server.display.LogicalDisplayMapper.this.mSyncRoot) {
                        com.android.server.display.LogicalDisplayMapper.this.finishStateTransitionLocked(true);
                    }
                    return;
                default:
                    return;
            }
        }
    }
}
