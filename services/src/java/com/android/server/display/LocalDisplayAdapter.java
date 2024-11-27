package com.android.server.display;

/* loaded from: classes.dex */
final class LocalDisplayAdapter extends com.android.server.display.DisplayAdapter {
    private static final boolean DEBUG = false;
    private static final java.lang.String PROPERTY_EMULATOR_CIRCULAR = "ro.boot.emulator.circular";
    private static final java.lang.String TAG = "LocalDisplayAdapter";
    private static final java.lang.String UNIQUE_ID_PREFIX = "local:";
    private final android.util.LongSparseArray<com.android.server.display.LocalDisplayAdapter.LocalDisplayDevice> mDevices;
    private final com.android.server.display.notifications.DisplayNotificationManager mDisplayNotificationManager;
    private final com.android.server.display.LocalDisplayAdapter.Injector mInjector;
    private final boolean mIsBootDisplayModeSupported;
    private android.content.Context mOverlayContext;
    private final com.android.server.display.LocalDisplayAdapter.SurfaceControlProxy mSurfaceControlProxy;

    public interface DisplayEventListener {
        void onFrameRateOverridesChanged(long j, long j2, android.view.DisplayEventReceiver.FrameRateOverride[] frameRateOverrideArr);

        void onHdcpLevelsChanged(long j, int i, int i2);

        void onHotplug(long j, long j2, boolean z);

        void onHotplugConnectionError(long j, int i);

        void onModeChanged(long j, long j2, int i, long j3);
    }

    LocalDisplayAdapter(com.android.server.display.DisplayManagerService.SyncRoot syncRoot, android.content.Context context, android.os.Handler handler, com.android.server.display.DisplayAdapter.Listener listener, com.android.server.display.feature.DisplayManagerFlags displayManagerFlags, com.android.server.display.notifications.DisplayNotificationManager displayNotificationManager) {
        this(syncRoot, context, handler, listener, displayManagerFlags, displayNotificationManager, new com.android.server.display.LocalDisplayAdapter.Injector());
    }

    @com.android.internal.annotations.VisibleForTesting
    LocalDisplayAdapter(com.android.server.display.DisplayManagerService.SyncRoot syncRoot, android.content.Context context, android.os.Handler handler, com.android.server.display.DisplayAdapter.Listener listener, com.android.server.display.feature.DisplayManagerFlags displayManagerFlags, com.android.server.display.notifications.DisplayNotificationManager displayNotificationManager, com.android.server.display.LocalDisplayAdapter.Injector injector) {
        super(syncRoot, context, handler, listener, TAG, displayManagerFlags);
        this.mDevices = new android.util.LongSparseArray<>();
        this.mDisplayNotificationManager = displayNotificationManager;
        this.mInjector = injector;
        this.mSurfaceControlProxy = this.mInjector.getSurfaceControlProxy();
        this.mIsBootDisplayModeSupported = this.mSurfaceControlProxy.getBootDisplayModeSupport();
    }

    @Override // com.android.server.display.DisplayAdapter
    public void registerLocked() {
        super.registerLocked();
        this.mInjector.setDisplayEventListenerLocked(getHandler().getLooper(), new com.android.server.display.LocalDisplayAdapter.LocalDisplayEventListener());
        for (long j : this.mSurfaceControlProxy.getPhysicalDisplayIds()) {
            tryConnectDisplayLocked(j);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void tryConnectDisplayLocked(long j) {
        android.os.IBinder physicalDisplayToken = this.mSurfaceControlProxy.getPhysicalDisplayToken(j);
        if (physicalDisplayToken != null) {
            android.view.SurfaceControl.StaticDisplayInfo staticDisplayInfo = this.mSurfaceControlProxy.getStaticDisplayInfo(j);
            if (staticDisplayInfo == null) {
                android.util.Slog.w(TAG, "No valid static info found for display device " + j);
                return;
            }
            android.view.SurfaceControl.DynamicDisplayInfo dynamicDisplayInfo = this.mSurfaceControlProxy.getDynamicDisplayInfo(j);
            if (dynamicDisplayInfo == null) {
                android.util.Slog.w(TAG, "No valid dynamic info found for display device " + j);
                return;
            }
            if (dynamicDisplayInfo.supportedDisplayModes == null) {
                android.util.Slog.w(TAG, "No valid modes found for display device " + j);
                return;
            }
            if (dynamicDisplayInfo.activeDisplayModeId < 0) {
                android.util.Slog.w(TAG, "No valid active mode found for display device " + j);
                return;
            }
            if (dynamicDisplayInfo.activeColorMode < 0) {
                android.util.Slog.w(TAG, "No valid active color mode for display device " + j);
                dynamicDisplayInfo.activeColorMode = -1;
            }
            android.view.SurfaceControl.DesiredDisplayModeSpecs desiredDisplayModeSpecs = this.mSurfaceControlProxy.getDesiredDisplayModeSpecs(physicalDisplayToken);
            com.android.server.display.LocalDisplayAdapter.LocalDisplayDevice localDisplayDevice = this.mDevices.get(j);
            if (localDisplayDevice == null) {
                com.android.server.display.LocalDisplayAdapter.LocalDisplayDevice localDisplayDevice2 = new com.android.server.display.LocalDisplayAdapter.LocalDisplayDevice(physicalDisplayToken, j, staticDisplayInfo, dynamicDisplayInfo, desiredDisplayModeSpecs, this.mDevices.size() == 0);
                this.mDevices.put(j, localDisplayDevice2);
                sendDisplayDeviceEventLocked(localDisplayDevice2, 1);
            } else if (localDisplayDevice.updateDisplayPropertiesLocked(staticDisplayInfo, dynamicDisplayInfo, desiredDisplayModeSpecs)) {
                sendDisplayDeviceEventLocked(localDisplayDevice, 2);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void tryDisconnectDisplayLocked(long j) {
        com.android.server.display.LocalDisplayAdapter.LocalDisplayDevice localDisplayDevice = this.mDevices.get(j);
        if (localDisplayDevice != null) {
            this.mDevices.remove(j);
            sendDisplayDeviceEventLocked(localDisplayDevice, 3);
        }
    }

    static int getPowerModeForState(int i) {
        switch (i) {
            case 1:
                return 0;
            case 2:
            case 5:
            default:
                return 2;
            case 3:
                return 1;
            case 4:
                return 3;
            case 6:
                return 4;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    final class LocalDisplayDevice extends com.android.server.display.DisplayDevice {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        private int mActiveColorMode;
        private int mActiveModeId;
        private float mActiveRenderFrameRate;
        private android.view.SurfaceControl.DisplayMode mActiveSfDisplayMode;
        private int mActiveSfDisplayModeAtStartId;
        private boolean mAllmRequested;
        private boolean mAllmSupported;
        private final com.android.server.display.LocalDisplayAdapter.BacklightAdapter mBacklightAdapter;
        private float mBrightnessState;
        private int mCommittedState;
        private int mConnectedHdcpLevel;
        private float mCurrentHdrSdrRatio;
        private int mDefaultModeGroup;
        private int mDefaultModeId;
        private final com.android.server.display.mode.DisplayModeDirector.DesiredDisplayModeSpecs mDisplayModeSpecs;
        private boolean mDisplayModeSpecsInvalid;
        private android.view.DisplayEventReceiver.FrameRateOverride[] mFrameRateOverrides;
        private boolean mGameContentTypeRequested;
        private boolean mGameContentTypeSupported;
        private boolean mHavePendingChanges;
        private android.view.Display.HdrCapabilities mHdrCapabilities;
        private com.android.server.display.DisplayDeviceInfo mInfo;
        private final boolean mIsFirstDisplay;
        private final long mPhysicalDisplayId;
        private float mSdrBrightnessState;
        private android.view.SurfaceControl.DisplayMode[] mSfDisplayModes;
        private boolean mSidekickActive;
        private final android.hardware.sidekick.SidekickInternal mSidekickInternal;
        private int mState;
        private android.view.SurfaceControl.StaticDisplayInfo mStaticDisplayInfo;
        private final java.util.ArrayList<java.lang.Integer> mSupportedColorModes;
        private final android.util.SparseArray<com.android.server.display.LocalDisplayAdapter.DisplayModeRecord> mSupportedModes;
        private int mSystemPreferredModeId;
        private android.view.Display.Mode mUserPreferredMode;
        private int mUserPreferredModeId;

        LocalDisplayDevice(android.os.IBinder iBinder, long j, android.view.SurfaceControl.StaticDisplayInfo staticDisplayInfo, android.view.SurfaceControl.DynamicDisplayInfo dynamicDisplayInfo, android.view.SurfaceControl.DesiredDisplayModeSpecs desiredDisplayModeSpecs, boolean z) {
            super(com.android.server.display.LocalDisplayAdapter.this, iBinder, com.android.server.display.LocalDisplayAdapter.UNIQUE_ID_PREFIX + j, com.android.server.display.LocalDisplayAdapter.this.getContext());
            this.mSupportedModes = new android.util.SparseArray<>();
            this.mSupportedColorModes = new java.util.ArrayList<>();
            this.mDisplayModeSpecs = new com.android.server.display.mode.DisplayModeDirector.DesiredDisplayModeSpecs();
            this.mState = 0;
            this.mCommittedState = 0;
            this.mBrightnessState = Float.NaN;
            this.mSdrBrightnessState = Float.NaN;
            this.mCurrentHdrSdrRatio = Float.NaN;
            this.mDefaultModeId = -1;
            this.mSystemPreferredModeId = -1;
            this.mUserPreferredModeId = -1;
            this.mActiveSfDisplayModeAtStartId = -1;
            this.mActiveModeId = -1;
            this.mFrameRateOverrides = new android.view.DisplayEventReceiver.FrameRateOverride[0];
            this.mPhysicalDisplayId = j;
            this.mIsFirstDisplay = z;
            updateDisplayPropertiesLocked(staticDisplayInfo, dynamicDisplayInfo, desiredDisplayModeSpecs);
            this.mSidekickInternal = (android.hardware.sidekick.SidekickInternal) com.android.server.LocalServices.getService(android.hardware.sidekick.SidekickInternal.class);
            this.mBacklightAdapter = new com.android.server.display.LocalDisplayAdapter.BacklightAdapter(iBinder, z, com.android.server.display.LocalDisplayAdapter.this.mSurfaceControlProxy);
            this.mActiveSfDisplayModeAtStartId = dynamicDisplayInfo.activeDisplayModeId;
        }

        @Override // com.android.server.display.DisplayDevice
        public boolean hasStableUniqueId() {
            return true;
        }

        @Override // com.android.server.display.DisplayDevice
        public android.view.Display.Mode getActiveDisplayModeAtStartLocked() {
            return findMode(findMatchingModeIdLocked(this.mActiveSfDisplayModeAtStartId));
        }

        public boolean updateDisplayPropertiesLocked(android.view.SurfaceControl.StaticDisplayInfo staticDisplayInfo, android.view.SurfaceControl.DynamicDisplayInfo dynamicDisplayInfo, android.view.SurfaceControl.DesiredDisplayModeSpecs desiredDisplayModeSpecs) {
            boolean updateStaticInfo = updateStaticInfo(staticDisplayInfo) | updateDisplayModesLocked(dynamicDisplayInfo.supportedDisplayModes, dynamicDisplayInfo.preferredBootDisplayMode, dynamicDisplayInfo.activeDisplayModeId, dynamicDisplayInfo.renderFrameRate, desiredDisplayModeSpecs) | updateColorModesLocked(dynamicDisplayInfo.supportedColorModes, dynamicDisplayInfo.activeColorMode) | updateHdrCapabilitiesLocked(dynamicDisplayInfo.hdrCapabilities) | updateAllmSupport(dynamicDisplayInfo.autoLowLatencyModeSupported) | updateGameContentTypeSupport(dynamicDisplayInfo.gameContentTypeSupported);
            if (updateStaticInfo) {
                this.mHavePendingChanges = true;
            }
            return updateStaticInfo;
        }

        /* JADX WARN: Removed duplicated region for block: B:101:0x01cd  */
        /* JADX WARN: Removed duplicated region for block: B:108:0x01d5  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public boolean updateDisplayModesLocked(android.view.SurfaceControl.DisplayMode[] displayModeArr, int i, int i2, float f, android.view.SurfaceControl.DesiredDisplayModeSpecs desiredDisplayModeSpecs) {
            com.android.server.display.LocalDisplayAdapter.DisplayModeRecord displayModeRecord;
            com.android.server.display.LocalDisplayAdapter.DisplayModeRecord displayModeRecord2;
            boolean z;
            boolean z2;
            boolean z3;
            int findMatchingModeIdLocked;
            boolean z4;
            this.mSfDisplayModes = (android.view.SurfaceControl.DisplayMode[]) java.util.Arrays.copyOf(displayModeArr, displayModeArr.length);
            this.mActiveSfDisplayMode = getModeById(displayModeArr, i2);
            android.view.SurfaceControl.DisplayMode modeById = getModeById(displayModeArr, i);
            java.util.ArrayList arrayList = new java.util.ArrayList();
            int i3 = 0;
            boolean z5 = false;
            while (i3 < displayModeArr.length) {
                android.view.SurfaceControl.DisplayMode displayMode = displayModeArr[i3];
                java.util.ArrayList arrayList2 = new java.util.ArrayList();
                int i4 = 0;
                while (i4 < displayModeArr.length) {
                    android.view.SurfaceControl.DisplayMode displayMode2 = displayModeArr[i4];
                    if (i4 != i3 && displayMode2.width == displayMode.width && displayMode2.height == displayMode.height && displayMode2.peakRefreshRate != displayMode.peakRefreshRate && displayMode2.group == displayMode.group) {
                        arrayList2.add(java.lang.Float.valueOf(displayModeArr[i4].peakRefreshRate));
                    }
                    i4++;
                }
                java.util.Collections.sort(arrayList2);
                java.util.Iterator it = arrayList.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        z4 = false;
                        break;
                    }
                    com.android.server.display.LocalDisplayAdapter.DisplayModeRecord displayModeRecord3 = (com.android.server.display.LocalDisplayAdapter.DisplayModeRecord) it.next();
                    if (displayModeRecord3.hasMatchingMode(displayMode) && refreshRatesEquals(arrayList2, displayModeRecord3.mMode.getAlternativeRefreshRates())) {
                        z4 = true;
                        break;
                    }
                }
                if (!z4) {
                    com.android.server.display.LocalDisplayAdapter.DisplayModeRecord findDisplayModeRecord = findDisplayModeRecord(displayMode, arrayList2);
                    if (findDisplayModeRecord == null) {
                        int size = arrayList2.size();
                        float[] fArr = new float[size];
                        for (int i5 = 0; i5 < size; i5++) {
                            fArr[i5] = arrayList2.get(i5).floatValue();
                        }
                        findDisplayModeRecord = new com.android.server.display.LocalDisplayAdapter.DisplayModeRecord(displayMode, fArr);
                        z5 = true;
                    }
                    arrayList.add(findDisplayModeRecord);
                }
                i3++;
            }
            java.util.Iterator it2 = arrayList.iterator();
            while (true) {
                displayModeRecord = null;
                if (!it2.hasNext()) {
                    displayModeRecord2 = null;
                    break;
                }
                displayModeRecord2 = (com.android.server.display.LocalDisplayAdapter.DisplayModeRecord) it2.next();
                if (displayModeRecord2.hasMatchingMode(this.mActiveSfDisplayMode)) {
                    break;
                }
            }
            if (i != -1 && modeById != null) {
                java.util.Iterator it3 = arrayList.iterator();
                while (true) {
                    if (!it3.hasNext()) {
                        break;
                    }
                    com.android.server.display.LocalDisplayAdapter.DisplayModeRecord displayModeRecord4 = (com.android.server.display.LocalDisplayAdapter.DisplayModeRecord) it3.next();
                    if (displayModeRecord4.hasMatchingMode(modeById)) {
                        displayModeRecord = displayModeRecord4;
                        break;
                    }
                }
                if (displayModeRecord != null) {
                    int modeId = displayModeRecord.mMode.getModeId();
                    if (com.android.server.display.LocalDisplayAdapter.this.mIsBootDisplayModeSupported && this.mSystemPreferredModeId != modeId) {
                        this.mSystemPreferredModeId = modeId;
                        z = true;
                        if (this.mActiveModeId == -1 && this.mActiveModeId != displayModeRecord2.mMode.getModeId()) {
                            android.util.Slog.d(com.android.server.display.LocalDisplayAdapter.TAG, "The active mode was changed from SurfaceFlinger or the display device to " + displayModeRecord2.mMode);
                            this.mActiveModeId = displayModeRecord2.mMode.getModeId();
                            com.android.server.display.LocalDisplayAdapter.this.sendTraversalRequestLocked();
                            z2 = true;
                        } else {
                            z2 = false;
                        }
                        if (this.mActiveRenderFrameRate <= com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE && this.mActiveRenderFrameRate != f) {
                            android.util.Slog.d(com.android.server.display.LocalDisplayAdapter.TAG, "The render frame rate was changed from SurfaceFlinger or the display device to " + f);
                            this.mActiveRenderFrameRate = f;
                            com.android.server.display.LocalDisplayAdapter.this.sendTraversalRequestLocked();
                            z3 = true;
                        } else {
                            z3 = false;
                        }
                        if (this.mDisplayModeSpecs.baseModeId != -1 && ((findMatchingModeIdLocked = findMatchingModeIdLocked(desiredDisplayModeSpecs.defaultMode)) == -1 || this.mDisplayModeSpecs.baseModeId != findMatchingModeIdLocked || !this.mDisplayModeSpecs.primary.equals(desiredDisplayModeSpecs.primaryRanges) || !this.mDisplayModeSpecs.appRequest.equals(desiredDisplayModeSpecs.appRequestRanges))) {
                            this.mDisplayModeSpecsInvalid = true;
                            com.android.server.display.LocalDisplayAdapter.this.sendTraversalRequestLocked();
                        }
                        if (arrayList.size() == this.mSupportedModes.size() || z5) {
                            return z2 || z || z3;
                        }
                        this.mSupportedModes.clear();
                        java.util.Iterator it4 = arrayList.iterator();
                        while (it4.hasNext()) {
                            com.android.server.display.LocalDisplayAdapter.DisplayModeRecord displayModeRecord5 = (com.android.server.display.LocalDisplayAdapter.DisplayModeRecord) it4.next();
                            this.mSupportedModes.put(displayModeRecord5.mMode.getModeId(), displayModeRecord5);
                        }
                        if (this.mDefaultModeId == -1) {
                            this.mDefaultModeId = displayModeRecord2.mMode.getModeId();
                            this.mDefaultModeGroup = this.mActiveSfDisplayMode.group;
                            this.mActiveRenderFrameRate = f;
                        } else if (!z5 || !z2) {
                            if (findSfDisplayModeIdLocked(this.mDefaultModeId, this.mDefaultModeGroup) < 0) {
                                android.util.Slog.w(com.android.server.display.LocalDisplayAdapter.TAG, "Default display mode no longer available, using currently active mode as default.");
                                this.mDefaultModeId = displayModeRecord2.mMode.getModeId();
                                this.mDefaultModeGroup = this.mActiveSfDisplayMode.group;
                                this.mActiveRenderFrameRate = f;
                            }
                        } else {
                            android.util.Slog.d(com.android.server.display.LocalDisplayAdapter.TAG, "New display modes are added and the active mode has changed, use active mode as default mode.");
                            this.mDefaultModeId = displayModeRecord2.mMode.getModeId();
                            this.mDefaultModeGroup = this.mActiveSfDisplayMode.group;
                            this.mActiveRenderFrameRate = f;
                        }
                        if (this.mSupportedModes.indexOfKey(this.mDisplayModeSpecs.baseModeId) < 0) {
                            if (this.mDisplayModeSpecs.baseModeId != -1) {
                                android.util.Slog.w(com.android.server.display.LocalDisplayAdapter.TAG, "DisplayModeSpecs base mode no longer available, using currently active mode.");
                            }
                            this.mDisplayModeSpecs.baseModeId = displayModeRecord2.mMode.getModeId();
                            this.mDisplayModeSpecsInvalid = true;
                        }
                        if (this.mUserPreferredMode != null) {
                            this.mUserPreferredModeId = findUserPreferredModeIdLocked(this.mUserPreferredMode);
                        }
                        if (this.mSupportedModes.indexOfKey(this.mActiveModeId) < 0) {
                            if (this.mActiveModeId != -1) {
                                android.util.Slog.w(com.android.server.display.LocalDisplayAdapter.TAG, "Active display mode no longer available, reverting to default mode.");
                            }
                            this.mActiveModeId = getPreferredModeId();
                        }
                        com.android.server.display.LocalDisplayAdapter.this.sendTraversalRequestLocked();
                        return true;
                    }
                }
            }
            z = false;
            if (this.mActiveModeId == -1) {
            }
            z2 = false;
            if (this.mActiveRenderFrameRate <= com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE) {
            }
            z3 = false;
            if (this.mDisplayModeSpecs.baseModeId != -1) {
                this.mDisplayModeSpecsInvalid = true;
                com.android.server.display.LocalDisplayAdapter.this.sendTraversalRequestLocked();
            }
            if (arrayList.size() == this.mSupportedModes.size() || z5) {
            }
        }

        @Override // com.android.server.display.DisplayDevice
        public com.android.server.display.DisplayDeviceConfig getDisplayDeviceConfig() {
            if (this.mDisplayDeviceConfig == null) {
                loadDisplayDeviceConfig();
            }
            return this.mDisplayDeviceConfig;
        }

        private int getPreferredModeId() {
            if (this.mUserPreferredModeId != -1) {
                return this.mUserPreferredModeId;
            }
            return this.mDefaultModeId;
        }

        private int getLogicalDensity() {
            com.android.server.display.DensityMapping densityMapping = getDisplayDeviceConfig().getDensityMapping();
            if (densityMapping == null) {
                return (int) ((this.mStaticDisplayInfo.density * 160.0f) + 0.5d);
            }
            return densityMapping.getDensityForResolution(this.mInfo.width, this.mInfo.height);
        }

        private void loadDisplayDeviceConfig() {
            this.mDisplayDeviceConfig = com.android.server.display.LocalDisplayAdapter.this.mInjector.createDisplayDeviceConfig(com.android.server.display.LocalDisplayAdapter.this.getOverlayContext(), this.mPhysicalDisplayId, this.mIsFirstDisplay, com.android.server.display.LocalDisplayAdapter.this.getFeatureFlags());
            this.mBacklightAdapter.setForceSurfaceControl(this.mDisplayDeviceConfig.hasQuirk(com.android.server.display.DisplayDeviceConfig.QUIRK_CAN_SET_BRIGHTNESS_VIA_HWC));
        }

        private boolean updateStaticInfo(android.view.SurfaceControl.StaticDisplayInfo staticDisplayInfo) {
            if (java.util.Objects.equals(this.mStaticDisplayInfo, staticDisplayInfo)) {
                return false;
            }
            this.mStaticDisplayInfo = staticDisplayInfo;
            return true;
        }

        private boolean updateColorModesLocked(int[] iArr, int i) {
            if (iArr == null) {
                return false;
            }
            java.util.ArrayList arrayList = new java.util.ArrayList();
            boolean z = false;
            for (int i2 : iArr) {
                if (!this.mSupportedColorModes.contains(java.lang.Integer.valueOf(i2))) {
                    z = true;
                }
                arrayList.add(java.lang.Integer.valueOf(i2));
            }
            if (!(arrayList.size() != this.mSupportedColorModes.size() || z)) {
                return false;
            }
            this.mSupportedColorModes.clear();
            this.mSupportedColorModes.addAll(arrayList);
            java.util.Collections.sort(this.mSupportedColorModes);
            if (!this.mSupportedColorModes.contains(java.lang.Integer.valueOf(this.mActiveColorMode))) {
                if (this.mActiveColorMode != 0) {
                    android.util.Slog.w(com.android.server.display.LocalDisplayAdapter.TAG, "Active color mode no longer available, reverting to default mode.");
                    this.mActiveColorMode = 0;
                } else if (!this.mSupportedColorModes.isEmpty()) {
                    android.util.Slog.e(com.android.server.display.LocalDisplayAdapter.TAG, "Default and active color mode is no longer available! Reverting to first available mode.");
                    this.mActiveColorMode = this.mSupportedColorModes.get(0).intValue();
                } else {
                    android.util.Slog.e(com.android.server.display.LocalDisplayAdapter.TAG, "No color modes available!");
                }
            }
            return true;
        }

        private boolean updateHdrCapabilitiesLocked(android.view.Display.HdrCapabilities hdrCapabilities) {
            if (java.util.Objects.equals(this.mHdrCapabilities, hdrCapabilities)) {
                return false;
            }
            this.mHdrCapabilities = hdrCapabilities;
            return true;
        }

        private boolean updateAllmSupport(boolean z) {
            if (this.mAllmSupported == z) {
                return false;
            }
            this.mAllmSupported = z;
            return true;
        }

        private boolean updateGameContentTypeSupport(boolean z) {
            if (this.mGameContentTypeSupported == z) {
                return false;
            }
            this.mGameContentTypeSupported = z;
            return true;
        }

        private android.view.SurfaceControl.DisplayMode getModeById(android.view.SurfaceControl.DisplayMode[] displayModeArr, int i) {
            for (android.view.SurfaceControl.DisplayMode displayMode : displayModeArr) {
                if (displayMode.id == i) {
                    return displayMode;
                }
            }
            android.util.Slog.e(com.android.server.display.LocalDisplayAdapter.TAG, "Can't find display mode with id " + i);
            return null;
        }

        private com.android.server.display.LocalDisplayAdapter.DisplayModeRecord findDisplayModeRecord(android.view.SurfaceControl.DisplayMode displayMode, java.util.List<java.lang.Float> list) {
            for (int i = 0; i < this.mSupportedModes.size(); i++) {
                com.android.server.display.LocalDisplayAdapter.DisplayModeRecord valueAt = this.mSupportedModes.valueAt(i);
                if (valueAt.hasMatchingMode(displayMode) && refreshRatesEquals(list, valueAt.mMode.getAlternativeRefreshRates()) && com.android.server.display.LocalDisplayAdapter.this.hdrTypesEqual(displayMode.supportedHdrTypes, valueAt.mMode.getSupportedHdrTypes())) {
                    return valueAt;
                }
            }
            return null;
        }

        private boolean refreshRatesEquals(java.util.List<java.lang.Float> list, float[] fArr) {
            if (list.size() != fArr.length) {
                return false;
            }
            for (int i = 0; i < list.size(); i++) {
                if (java.lang.Float.floatToIntBits(list.get(i).floatValue()) != java.lang.Float.floatToIntBits(fArr[i])) {
                    return false;
                }
            }
            return true;
        }

        @Override // com.android.server.display.DisplayDevice
        public void applyPendingDisplayDeviceInfoChangesLocked() {
            if (this.mHavePendingChanges) {
                this.mInfo = null;
                this.mHavePendingChanges = false;
            }
        }

        @Override // com.android.server.display.DisplayDevice
        public com.android.server.display.DisplayDeviceInfo getDisplayDeviceInfoLocked() {
            if (this.mInfo == null) {
                this.mInfo = new com.android.server.display.DisplayDeviceInfo();
                this.mInfo.width = this.mActiveSfDisplayMode.width;
                this.mInfo.height = this.mActiveSfDisplayMode.height;
                this.mInfo.modeId = this.mActiveModeId;
                this.mInfo.renderFrameRate = this.mActiveRenderFrameRate;
                this.mInfo.defaultModeId = getPreferredModeId();
                this.mInfo.userPreferredModeId = this.mUserPreferredModeId;
                this.mInfo.supportedModes = getDisplayModes(this.mSupportedModes);
                this.mInfo.colorMode = this.mActiveColorMode;
                this.mInfo.allmSupported = this.mAllmSupported;
                this.mInfo.gameContentTypeSupported = this.mGameContentTypeSupported;
                this.mInfo.supportedColorModes = new int[this.mSupportedColorModes.size()];
                for (int i = 0; i < this.mSupportedColorModes.size(); i++) {
                    this.mInfo.supportedColorModes[i] = this.mSupportedColorModes.get(i).intValue();
                }
                this.mInfo.hdrCapabilities = this.mHdrCapabilities;
                this.mInfo.appVsyncOffsetNanos = this.mActiveSfDisplayMode.appVsyncOffsetNanos;
                this.mInfo.presentationDeadlineNanos = this.mActiveSfDisplayMode.presentationDeadlineNanos;
                this.mInfo.state = this.mState;
                this.mInfo.committedState = this.mCommittedState;
                this.mInfo.uniqueId = getUniqueId();
                android.view.DisplayAddress.Physical fromPhysicalDisplayId = android.view.DisplayAddress.fromPhysicalDisplayId(this.mPhysicalDisplayId);
                this.mInfo.address = fromPhysicalDisplayId;
                this.mInfo.densityDpi = getLogicalDensity();
                this.mInfo.xDpi = this.mActiveSfDisplayMode.xDpi;
                this.mInfo.yDpi = this.mActiveSfDisplayMode.yDpi;
                this.mInfo.deviceProductInfo = this.mStaticDisplayInfo.deviceProductInfo;
                if (this.mConnectedHdcpLevel != 0) {
                    this.mStaticDisplayInfo.secure = this.mConnectedHdcpLevel >= 2;
                }
                if (this.mStaticDisplayInfo.secure) {
                    this.mInfo.flags = 12;
                }
                android.content.res.Resources resources = com.android.server.display.LocalDisplayAdapter.this.getOverlayContext().getResources();
                this.mInfo.flags |= 1;
                if (this.mIsFirstDisplay) {
                    if (resources.getBoolean(android.R.bool.config_longPressOnPowerForAssistantSettingAvailable) || (android.os.Build.IS_EMULATOR && android.os.SystemProperties.getBoolean(com.android.server.display.LocalDisplayAdapter.PROPERTY_EMULATOR_CIRCULAR, false))) {
                        this.mInfo.flags |= 256;
                    }
                } else {
                    if (!resources.getBoolean(android.R.bool.config_letterboxIsPolicyForIgnoringRequestedOrientationEnabled)) {
                        this.mInfo.flags |= 128;
                    }
                    if (isDisplayPrivate(fromPhysicalDisplayId)) {
                        this.mInfo.flags |= 16;
                    }
                }
                if (android.view.DisplayCutout.getMaskBuiltInDisplayCutout(resources, this.mInfo.uniqueId)) {
                    this.mInfo.flags |= 2048;
                }
                android.view.Display.Mode maximumResolutionDisplayMode = android.util.DisplayUtils.getMaximumResolutionDisplayMode(this.mInfo.supportedModes);
                int physicalWidth = maximumResolutionDisplayMode == null ? this.mInfo.width : maximumResolutionDisplayMode.getPhysicalWidth();
                int physicalHeight = maximumResolutionDisplayMode == null ? this.mInfo.height : maximumResolutionDisplayMode.getPhysicalHeight();
                if (this.mStaticDisplayInfo.isInternal) {
                    int i2 = physicalWidth;
                    int i3 = physicalHeight;
                    this.mInfo.displayCutout = android.view.DisplayCutout.fromResourcesRectApproximation(resources, this.mInfo.uniqueId, i2, i3, this.mInfo.width, this.mInfo.height);
                    this.mInfo.roundedCorners = android.view.RoundedCorners.fromResources(resources, this.mInfo.uniqueId, i2, i3, this.mInfo.width, this.mInfo.height);
                }
                this.mInfo.installOrientation = this.mStaticDisplayInfo.installOrientation;
                this.mInfo.displayShape = android.view.DisplayShape.fromResources(resources, this.mInfo.uniqueId, physicalWidth, physicalHeight, this.mInfo.width, this.mInfo.height);
                this.mInfo.name = getDisplayDeviceConfig().getName();
                if (this.mStaticDisplayInfo.isInternal) {
                    this.mInfo.type = 1;
                    this.mInfo.touch = 1;
                    this.mInfo.flags |= 2;
                    if (this.mInfo.name == null) {
                        this.mInfo.name = resources.getString(android.R.string.deprecated_abi_message);
                    }
                } else {
                    this.mInfo.type = 2;
                    this.mInfo.touch = 2;
                    this.mInfo.flags |= 64;
                    if (this.mInfo.name == null) {
                        this.mInfo.name = com.android.server.display.LocalDisplayAdapter.this.getContext().getResources().getString(android.R.string.deprecated_target_sdk_app_store);
                    }
                }
                this.mInfo.frameRateOverrides = this.mFrameRateOverrides;
                this.mInfo.flags |= 8192;
                this.mInfo.brightnessMinimum = com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE;
                this.mInfo.brightnessMaximum = 1.0f;
                this.mInfo.brightnessDefault = getDisplayDeviceConfig().getBrightnessDefault();
                this.mInfo.hdrSdrRatio = this.mCurrentHdrSdrRatio;
            }
            return this.mInfo;
        }

        @Override // com.android.server.display.DisplayDevice
        public java.lang.Runnable requestDisplayStateLocked(final int i, final float f, final float f2, @android.annotation.Nullable final com.android.server.display.DisplayOffloadSessionImpl displayOffloadSessionImpl) {
            final boolean z = true;
            boolean z2 = this.mState != i;
            if (this.mBrightnessState == f && this.mSdrBrightnessState == f2) {
                z = false;
            }
            if (z2 || z) {
                final long j = this.mPhysicalDisplayId;
                final android.os.IBinder displayTokenLocked = getDisplayTokenLocked();
                final int i2 = this.mState;
                if (z2) {
                    this.mState = i;
                    updateDeviceInfoLocked();
                }
                return new java.lang.Runnable() { // from class: com.android.server.display.LocalDisplayAdapter.LocalDisplayDevice.1
                    @Override // java.lang.Runnable
                    public void run() {
                        int i3 = i2;
                        if (android.view.Display.isSuspendedState(i2) || i2 == 0) {
                            if (!android.view.Display.isSuspendedState(i)) {
                                setDisplayState(i);
                                i3 = i;
                            } else if (i == 4 || i2 == 4) {
                                i3 = 3;
                                setDisplayState(3);
                            } else if (i == 6 || i2 == 6) {
                                i3 = 2;
                                setDisplayState(2);
                            } else if (i2 != 0) {
                                return;
                            }
                        }
                        if (z) {
                            setDisplayBrightness(f, f2);
                            com.android.server.display.LocalDisplayAdapter.LocalDisplayDevice.this.mBrightnessState = f;
                            com.android.server.display.LocalDisplayAdapter.LocalDisplayDevice.this.mSdrBrightnessState = f2;
                        }
                        if (i != i3) {
                            setDisplayState(i);
                        }
                    }

                    private void setDisplayState(int i3) {
                        boolean isDisplayOffloadEnabled = com.android.server.display.LocalDisplayAdapter.this.getFeatureFlags().isDisplayOffloadEnabled();
                        if (isDisplayOffloadEnabled) {
                            if (displayOffloadSessionImpl != null && !android.hardware.display.DisplayManagerInternal.DisplayOffloadSession.isSupportedOffloadState(i3)) {
                                displayOffloadSessionImpl.stopOffload();
                            }
                        } else if (com.android.server.display.LocalDisplayAdapter.LocalDisplayDevice.this.mSidekickActive) {
                            android.os.Trace.traceBegin(131072L, "SidekickInternal#endDisplayControl");
                            try {
                                com.android.server.display.LocalDisplayAdapter.LocalDisplayDevice.this.mSidekickInternal.endDisplayControl();
                                android.os.Trace.traceEnd(131072L);
                                com.android.server.display.LocalDisplayAdapter.LocalDisplayDevice.this.mSidekickActive = false;
                            } finally {
                            }
                        }
                        int powerModeForState = com.android.server.display.LocalDisplayAdapter.getPowerModeForState(i3);
                        android.os.Trace.traceBegin(131072L, "setDisplayState(id=" + j + ", state=" + android.view.Display.stateToString(i3) + ")");
                        try {
                            com.android.server.display.LocalDisplayAdapter.this.mSurfaceControlProxy.setDisplayPowerMode(displayTokenLocked, powerModeForState);
                            android.os.Trace.traceCounter(131072L, "DisplayPowerMode", powerModeForState);
                            android.os.Trace.traceEnd(131072L);
                            setCommittedState(i3);
                            if (isDisplayOffloadEnabled) {
                                if (displayOffloadSessionImpl != null && android.hardware.display.DisplayManagerInternal.DisplayOffloadSession.isSupportedOffloadState(i3)) {
                                    displayOffloadSessionImpl.startOffload();
                                    return;
                                }
                                return;
                            }
                            if (android.view.Display.isSuspendedState(i3) && i3 != 1 && com.android.server.display.LocalDisplayAdapter.LocalDisplayDevice.this.mSidekickInternal != null && !com.android.server.display.LocalDisplayAdapter.LocalDisplayDevice.this.mSidekickActive) {
                                android.os.Trace.traceBegin(131072L, "SidekickInternal#startDisplayControl");
                                try {
                                    com.android.server.display.LocalDisplayAdapter.LocalDisplayDevice.this.mSidekickActive = com.android.server.display.LocalDisplayAdapter.LocalDisplayDevice.this.mSidekickInternal.startDisplayControl(i3);
                                } finally {
                                }
                            }
                        } finally {
                        }
                    }

                    private void setCommittedState(int i3) {
                        synchronized (com.android.server.display.LocalDisplayAdapter.this.getSyncRoot()) {
                            com.android.server.display.LocalDisplayAdapter.LocalDisplayDevice.this.mCommittedState = i3;
                            com.android.server.display.LocalDisplayAdapter.LocalDisplayDevice.this.updateDeviceInfoLocked();
                        }
                    }

                    private void setDisplayBrightness(float f3, float f4) {
                        if (java.lang.Float.isNaN(f3) || java.lang.Float.isNaN(f4)) {
                            return;
                        }
                        android.os.Trace.traceBegin(131072L, "setDisplayBrightness(id=" + j + ", brightnessState=" + f3 + ", sdrBrightnessState=" + f4 + ")");
                        try {
                            float brightnessToBacklight = brightnessToBacklight(f3);
                            float brightnessToBacklight2 = brightnessToBacklight(f4);
                            float backlightToNits = backlightToNits(brightnessToBacklight);
                            float backlightToNits2 = backlightToNits(brightnessToBacklight2);
                            com.android.server.display.LocalDisplayAdapter.LocalDisplayDevice.this.mBacklightAdapter.setBacklight(brightnessToBacklight2, backlightToNits2, brightnessToBacklight, backlightToNits);
                            android.os.Trace.traceCounter(131072L, "ScreenBrightness", com.android.internal.display.BrightnessSynchronizer.brightnessFloatToInt(f3));
                            android.os.Trace.traceCounter(131072L, "SdrScreenBrightness", com.android.internal.display.BrightnessSynchronizer.brightnessFloatToInt(f4));
                            if (com.android.server.display.LocalDisplayAdapter.LocalDisplayDevice.this.getDisplayDeviceConfig().hasSdrToHdrRatioSpline()) {
                                handleHdrSdrNitsChanged(backlightToNits, backlightToNits2);
                            }
                        } finally {
                            android.os.Trace.traceEnd(131072L);
                        }
                    }

                    private float brightnessToBacklight(float f3) {
                        if (f3 == -1.0f) {
                            return -1.0f;
                        }
                        return com.android.server.display.LocalDisplayAdapter.LocalDisplayDevice.this.getDisplayDeviceConfig().getBacklightFromBrightness(f3);
                    }

                    private float backlightToNits(float f3) {
                        return com.android.server.display.LocalDisplayAdapter.LocalDisplayDevice.this.getDisplayDeviceConfig().getNitsFromBacklight(f3);
                    }

                    void handleHdrSdrNitsChanged(float f3, float f4) {
                        float f5;
                        if (f3 != -1.0f && f4 != -1.0f) {
                            f5 = java.lang.Math.max(1.0f, f3 / f4);
                        } else {
                            f5 = Float.NaN;
                        }
                        if (!com.android.internal.display.BrightnessSynchronizer.floatEquals(com.android.server.display.LocalDisplayAdapter.LocalDisplayDevice.this.mCurrentHdrSdrRatio, f5)) {
                            synchronized (com.android.server.display.LocalDisplayAdapter.this.getSyncRoot()) {
                                com.android.server.display.LocalDisplayAdapter.LocalDisplayDevice.this.mCurrentHdrSdrRatio = f5;
                                com.android.server.display.LocalDisplayAdapter.LocalDisplayDevice.this.updateDeviceInfoLocked();
                            }
                        }
                    }
                };
            }
            return null;
        }

        @Override // com.android.server.display.DisplayDevice
        public void setUserPreferredDisplayModeLocked(android.view.Display.Mode mode) {
            android.view.Display.Mode findMode;
            int preferredModeId = getPreferredModeId();
            this.mUserPreferredMode = mode;
            if (mode == null && this.mSystemPreferredModeId != -1) {
                this.mDefaultModeId = this.mSystemPreferredModeId;
            }
            if (mode != null && ((mode.isRefreshRateSet() || mode.isResolutionSet()) && (findMode = findMode(mode.getPhysicalWidth(), mode.getPhysicalHeight(), mode.getRefreshRate())) != null)) {
                this.mUserPreferredMode = findMode;
            }
            this.mUserPreferredModeId = findUserPreferredModeIdLocked(this.mUserPreferredMode);
            if (preferredModeId == getPreferredModeId()) {
                return;
            }
            updateDeviceInfoLocked();
            if (com.android.server.display.LocalDisplayAdapter.this.mIsBootDisplayModeSupported) {
                if (this.mUserPreferredModeId == -1) {
                    com.android.server.display.LocalDisplayAdapter.this.mSurfaceControlProxy.clearBootDisplayMode(getDisplayTokenLocked());
                } else {
                    com.android.server.display.LocalDisplayAdapter.this.mSurfaceControlProxy.setBootDisplayMode(getDisplayTokenLocked(), findSfDisplayModeIdLocked(this.mUserPreferredMode.getModeId(), this.mDefaultModeGroup));
                }
            }
        }

        @Override // com.android.server.display.DisplayDevice
        public android.view.Display.Mode getUserPreferredDisplayModeLocked() {
            return this.mUserPreferredMode;
        }

        @Override // com.android.server.display.DisplayDevice
        public android.view.Display.Mode getSystemPreferredDisplayModeLocked() {
            return findMode(this.mSystemPreferredModeId);
        }

        @Override // com.android.server.display.DisplayDevice
        public void setRequestedColorModeLocked(int i) {
            requestColorModeLocked(i);
        }

        @Override // com.android.server.display.DisplayDevice
        public void setDesiredDisplayModeSpecsLocked(com.android.server.display.mode.DisplayModeDirector.DesiredDisplayModeSpecs desiredDisplayModeSpecs) {
            if (desiredDisplayModeSpecs.baseModeId == 0) {
                return;
            }
            int findSfDisplayModeIdLocked = findSfDisplayModeIdLocked(desiredDisplayModeSpecs.baseModeId, this.mDefaultModeGroup);
            if (findSfDisplayModeIdLocked < 0) {
                android.util.Slog.w(com.android.server.display.LocalDisplayAdapter.TAG, "Ignoring request for invalid base mode id " + desiredDisplayModeSpecs.baseModeId);
                updateDeviceInfoLocked();
                return;
            }
            if (this.mDisplayModeSpecsInvalid || !desiredDisplayModeSpecs.equals(this.mDisplayModeSpecs)) {
                this.mDisplayModeSpecsInvalid = false;
                this.mDisplayModeSpecs.copyFrom(desiredDisplayModeSpecs);
                com.android.server.display.LocalDisplayAdapter.this.getHandler().sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new com.android.internal.util.function.TriConsumer() { // from class: com.android.server.display.LocalDisplayAdapter$LocalDisplayDevice$$ExternalSyntheticLambda0
                    public final void accept(java.lang.Object obj, java.lang.Object obj2, java.lang.Object obj3) {
                        ((com.android.server.display.LocalDisplayAdapter.LocalDisplayDevice) obj).setDesiredDisplayModeSpecsAsync((android.os.IBinder) obj2, (android.view.SurfaceControl.DesiredDisplayModeSpecs) obj3);
                    }
                }, this, getDisplayTokenLocked(), new android.view.SurfaceControl.DesiredDisplayModeSpecs(findSfDisplayModeIdLocked, this.mDisplayModeSpecs.allowGroupSwitching, this.mDisplayModeSpecs.primary, this.mDisplayModeSpecs.appRequest)));
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setDesiredDisplayModeSpecsAsync(android.os.IBinder iBinder, android.view.SurfaceControl.DesiredDisplayModeSpecs desiredDisplayModeSpecs) {
            com.android.server.display.LocalDisplayAdapter.this.mSurfaceControlProxy.setDesiredDisplayModeSpecs(iBinder, desiredDisplayModeSpecs);
        }

        @Override // com.android.server.display.DisplayDevice
        public void onOverlayChangedLocked() {
            updateDeviceInfoLocked();
        }

        public void onActiveDisplayModeChangedLocked(int i, float f) {
            if (updateActiveModeLocked(i, f)) {
                updateDeviceInfoLocked();
            }
        }

        public void onFrameRateOverridesChanged(android.view.DisplayEventReceiver.FrameRateOverride[] frameRateOverrideArr) {
            if (updateFrameRateOverridesLocked(frameRateOverrideArr)) {
                updateDeviceInfoLocked();
            }
        }

        public void onHdcpLevelsChangedLocked(int i, int i2) {
            if (updateHdcpLevelsLocked(i, i2)) {
                updateDeviceInfoLocked();
            }
        }

        public boolean updateActiveModeLocked(int i, float f) {
            if (this.mActiveSfDisplayMode.id == i && this.mActiveRenderFrameRate == f) {
                return false;
            }
            this.mActiveSfDisplayMode = getModeById(this.mSfDisplayModes, i);
            this.mActiveModeId = findMatchingModeIdLocked(i);
            if (this.mActiveModeId == -1) {
                android.util.Slog.w(com.android.server.display.LocalDisplayAdapter.TAG, "In unknown mode after setting allowed modes, activeModeId=" + i);
            }
            this.mActiveRenderFrameRate = f;
            return true;
        }

        public boolean updateFrameRateOverridesLocked(android.view.DisplayEventReceiver.FrameRateOverride[] frameRateOverrideArr) {
            if (java.util.Arrays.equals(frameRateOverrideArr, this.mFrameRateOverrides)) {
                return false;
            }
            this.mFrameRateOverrides = frameRateOverrideArr;
            return true;
        }

        public boolean updateHdcpLevelsLocked(int i, int i2) {
            if (i > i2) {
                android.util.Slog.w(com.android.server.display.LocalDisplayAdapter.TAG, "HDCP connected level: " + i + " is larger than max level: " + i2 + ", ignoring request.");
                return false;
            }
            if (this.mConnectedHdcpLevel == i) {
                return false;
            }
            this.mConnectedHdcpLevel = i;
            return true;
        }

        public void requestColorModeLocked(int i) {
            if (this.mActiveColorMode == i) {
                return;
            }
            if (!this.mSupportedColorModes.contains(java.lang.Integer.valueOf(i))) {
                android.util.Slog.w(com.android.server.display.LocalDisplayAdapter.TAG, "Unable to find color mode " + i + ", ignoring request.");
                return;
            }
            this.mActiveColorMode = i;
            com.android.server.display.LocalDisplayAdapter.this.getHandler().sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new com.android.internal.util.function.TriConsumer() { // from class: com.android.server.display.LocalDisplayAdapter$LocalDisplayDevice$$ExternalSyntheticLambda1
                public final void accept(java.lang.Object obj, java.lang.Object obj2, java.lang.Object obj3) {
                    ((com.android.server.display.LocalDisplayAdapter.LocalDisplayDevice) obj).requestColorModeAsync((android.os.IBinder) obj2, ((java.lang.Integer) obj3).intValue());
                }
            }, this, getDisplayTokenLocked(), java.lang.Integer.valueOf(i)));
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void requestColorModeAsync(android.os.IBinder iBinder, int i) {
            com.android.server.display.LocalDisplayAdapter.this.mSurfaceControlProxy.setActiveColorMode(iBinder, i);
            synchronized (com.android.server.display.LocalDisplayAdapter.this.getSyncRoot()) {
                updateDeviceInfoLocked();
            }
        }

        @Override // com.android.server.display.DisplayDevice
        public void setAutoLowLatencyModeLocked(boolean z) {
            if (this.mAllmRequested == z) {
                return;
            }
            this.mAllmRequested = z;
            if (!this.mAllmSupported) {
                android.util.Slog.d(com.android.server.display.LocalDisplayAdapter.TAG, "Unable to set ALLM because the connected display does not support ALLM.");
            } else {
                com.android.server.display.LocalDisplayAdapter.this.mSurfaceControlProxy.setAutoLowLatencyMode(getDisplayTokenLocked(), z);
            }
        }

        @Override // com.android.server.display.DisplayDevice
        public void setGameContentTypeLocked(boolean z) {
            if (this.mGameContentTypeRequested == z) {
                return;
            }
            this.mGameContentTypeRequested = z;
            com.android.server.display.LocalDisplayAdapter.this.mSurfaceControlProxy.setGameContentType(getDisplayTokenLocked(), z);
        }

        @Override // com.android.server.display.DisplayDevice
        public void dumpLocked(java.io.PrintWriter printWriter) {
            super.dumpLocked(printWriter);
            printWriter.println("mPhysicalDisplayId=" + this.mPhysicalDisplayId);
            printWriter.println("mDisplayModeSpecs={" + this.mDisplayModeSpecs + "}");
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            sb.append("mDisplayModeSpecsInvalid=");
            sb.append(this.mDisplayModeSpecsInvalid);
            printWriter.println(sb.toString());
            printWriter.println("mActiveModeId=" + this.mActiveModeId);
            printWriter.println("mActiveColorMode=" + this.mActiveColorMode);
            printWriter.println("mDefaultModeId=" + this.mDefaultModeId);
            printWriter.println("mUserPreferredModeId=" + this.mUserPreferredModeId);
            printWriter.println("mState=" + android.view.Display.stateToString(this.mState));
            printWriter.println("mCommittedState=" + android.view.Display.stateToString(this.mCommittedState));
            printWriter.println("mBrightnessState=" + this.mBrightnessState);
            printWriter.println("mBacklightAdapter=" + this.mBacklightAdapter);
            printWriter.println("mAllmSupported=" + this.mAllmSupported);
            printWriter.println("mAllmRequested=" + this.mAllmRequested);
            printWriter.println("mGameContentTypeSupported=" + this.mGameContentTypeSupported);
            printWriter.println("mGameContentTypeRequested=" + this.mGameContentTypeRequested);
            printWriter.println("mStaticDisplayInfo=" + this.mStaticDisplayInfo);
            printWriter.println("mSfDisplayModes=");
            for (android.view.SurfaceControl.DisplayMode displayMode : this.mSfDisplayModes) {
                printWriter.println("  " + displayMode);
            }
            printWriter.println("mActiveSfDisplayMode=" + this.mActiveSfDisplayMode);
            printWriter.println("mActiveRenderFrameRate=" + this.mActiveRenderFrameRate);
            printWriter.println("mSupportedModes=");
            for (int i = 0; i < this.mSupportedModes.size(); i++) {
                printWriter.println("  " + this.mSupportedModes.valueAt(i));
            }
            printWriter.println("mSupportedColorModes=" + this.mSupportedColorModes);
            printWriter.println("mDisplayDeviceConfig=" + this.mDisplayDeviceConfig);
        }

        private int findSfDisplayModeIdLocked(int i, int i2) {
            com.android.server.display.LocalDisplayAdapter.DisplayModeRecord displayModeRecord = this.mSupportedModes.get(i);
            if (displayModeRecord == null) {
                return -1;
            }
            int i3 = -1;
            for (android.view.SurfaceControl.DisplayMode displayMode : this.mSfDisplayModes) {
                if (displayModeRecord.hasMatchingMode(displayMode)) {
                    if (i3 == -1) {
                        i3 = displayMode.id;
                    }
                    if (displayMode.group == i2) {
                        return displayMode.id;
                    }
                }
            }
            return i3;
        }

        private android.view.Display.Mode findMode(int i) {
            for (int i2 = 0; i2 < this.mSupportedModes.size(); i2++) {
                android.view.Display.Mode mode = this.mSupportedModes.valueAt(i2).mMode;
                if (mode.getModeId() == i) {
                    return mode;
                }
            }
            return null;
        }

        private android.view.Display.Mode findMode(int i, int i2, float f) {
            for (int i3 = 0; i3 < this.mSupportedModes.size(); i3++) {
                android.view.Display.Mode mode = this.mSupportedModes.valueAt(i3).mMode;
                if (mode.matchesIfValid(i, i2, f)) {
                    return mode;
                }
            }
            return null;
        }

        private int findUserPreferredModeIdLocked(android.view.Display.Mode mode) {
            if (mode != null) {
                for (int i = 0; i < this.mSupportedModes.size(); i++) {
                    android.view.Display.Mode mode2 = this.mSupportedModes.valueAt(i).mMode;
                    if (mode.matches(mode2.getPhysicalWidth(), mode2.getPhysicalHeight(), mode2.getRefreshRate())) {
                        return mode2.getModeId();
                    }
                }
                return -1;
            }
            return -1;
        }

        private int findMatchingModeIdLocked(int i) {
            android.view.SurfaceControl.DisplayMode modeById = getModeById(this.mSfDisplayModes, i);
            if (modeById == null) {
                android.util.Slog.e(com.android.server.display.LocalDisplayAdapter.TAG, "Invalid display mode ID " + i);
                return -1;
            }
            for (int i2 = 0; i2 < this.mSupportedModes.size(); i2++) {
                com.android.server.display.LocalDisplayAdapter.DisplayModeRecord valueAt = this.mSupportedModes.valueAt(i2);
                if (valueAt.hasMatchingMode(modeById)) {
                    return valueAt.mMode.getModeId();
                }
            }
            return -1;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void updateDeviceInfoLocked() {
            this.mInfo = null;
            com.android.server.display.LocalDisplayAdapter.this.sendDisplayDeviceEventLocked(this, 2);
        }

        private android.view.Display.Mode[] getDisplayModes(android.util.SparseArray<com.android.server.display.LocalDisplayAdapter.DisplayModeRecord> sparseArray) {
            int size = sparseArray.size();
            android.view.Display.Mode[] modeArr = new android.view.Display.Mode[size];
            for (int i = 0; i < size; i++) {
                modeArr[i] = sparseArray.valueAt(i).mMode;
            }
            return modeArr;
        }

        private boolean isDisplayPrivate(android.view.DisplayAddress.Physical physical) {
            int[] intArray;
            if (physical != null && (intArray = com.android.server.display.LocalDisplayAdapter.this.getOverlayContext().getResources().getIntArray(android.R.array.config_highRefreshRateBlacklist)) != null) {
                int port = physical.getPort();
                for (int i : intArray) {
                    if (i == port) {
                        return true;
                    }
                }
            }
            return false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean hdrTypesEqual(int[] iArr, int[] iArr2) {
        int[] copyOf = java.util.Arrays.copyOf(iArr, iArr.length);
        java.util.Arrays.sort(copyOf);
        return java.util.Arrays.equals(copyOf, iArr2);
    }

    android.content.Context getOverlayContext() {
        if (this.mOverlayContext == null) {
            this.mOverlayContext = android.app.ActivityThread.currentActivityThread().getSystemUiContext();
        }
        return this.mOverlayContext;
    }

    private static final class DisplayModeRecord {
        public final android.view.Display.Mode mMode;

        DisplayModeRecord(android.view.SurfaceControl.DisplayMode displayMode, float[] fArr) {
            this.mMode = com.android.server.display.DisplayAdapter.createMode(displayMode.width, displayMode.height, displayMode.peakRefreshRate, displayMode.vsyncRate, fArr, displayMode.supportedHdrTypes);
        }

        public boolean hasMatchingMode(android.view.SurfaceControl.DisplayMode displayMode) {
            return this.mMode.getPhysicalWidth() == displayMode.width && this.mMode.getPhysicalHeight() == displayMode.height && java.lang.Float.floatToIntBits(this.mMode.getRefreshRate()) == java.lang.Float.floatToIntBits(displayMode.peakRefreshRate) && java.lang.Float.floatToIntBits(this.mMode.getVsyncRate()) == java.lang.Float.floatToIntBits(displayMode.vsyncRate);
        }

        public java.lang.String toString() {
            return "DisplayModeRecord{mMode=" + this.mMode + "}";
        }
    }

    public static class Injector {
        private com.android.server.display.LocalDisplayAdapter.ProxyDisplayEventReceiver mReceiver;

        public void setDisplayEventListenerLocked(android.os.Looper looper, com.android.server.display.LocalDisplayAdapter.DisplayEventListener displayEventListener) {
            this.mReceiver = new com.android.server.display.LocalDisplayAdapter.ProxyDisplayEventReceiver(looper, displayEventListener);
        }

        public com.android.server.display.LocalDisplayAdapter.SurfaceControlProxy getSurfaceControlProxy() {
            return new com.android.server.display.LocalDisplayAdapter.SurfaceControlProxy();
        }

        public com.android.server.display.DisplayDeviceConfig createDisplayDeviceConfig(android.content.Context context, long j, boolean z, com.android.server.display.feature.DisplayManagerFlags displayManagerFlags) {
            return com.android.server.display.DisplayDeviceConfig.create(context, j, z, displayManagerFlags);
        }
    }

    public static final class ProxyDisplayEventReceiver extends android.view.DisplayEventReceiver {
        private final com.android.server.display.LocalDisplayAdapter.DisplayEventListener mListener;

        ProxyDisplayEventReceiver(android.os.Looper looper, com.android.server.display.LocalDisplayAdapter.DisplayEventListener displayEventListener) {
            super(looper, 0, 3);
            this.mListener = displayEventListener;
        }

        public void onHotplug(long j, long j2, boolean z) {
            this.mListener.onHotplug(j, j2, z);
        }

        public void onHotplugConnectionError(long j, int i) {
            this.mListener.onHotplugConnectionError(j, i);
        }

        public void onModeChanged(long j, long j2, int i, long j3) {
            this.mListener.onModeChanged(j, j2, i, j3);
        }

        public void onFrameRateOverridesChanged(long j, long j2, android.view.DisplayEventReceiver.FrameRateOverride[] frameRateOverrideArr) {
            this.mListener.onFrameRateOverridesChanged(j, j2, frameRateOverrideArr);
        }

        public void onHdcpLevelsChanged(long j, int i, int i2) {
            this.mListener.onHdcpLevelsChanged(j, i, i2);
        }
    }

    private final class LocalDisplayEventListener implements com.android.server.display.LocalDisplayAdapter.DisplayEventListener {
        private LocalDisplayEventListener() {
        }

        @Override // com.android.server.display.LocalDisplayAdapter.DisplayEventListener
        public void onHotplug(long j, long j2, boolean z) {
            synchronized (com.android.server.display.LocalDisplayAdapter.this.getSyncRoot()) {
                try {
                    if (z) {
                        com.android.server.display.LocalDisplayAdapter.this.tryConnectDisplayLocked(j2);
                    } else {
                        com.android.server.display.LocalDisplayAdapter.this.tryDisconnectDisplayLocked(j2);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        @Override // com.android.server.display.LocalDisplayAdapter.DisplayEventListener
        public void onHotplugConnectionError(long j, int i) {
            com.android.server.display.LocalDisplayAdapter.this.mDisplayNotificationManager.onHotplugConnectionError();
        }

        @Override // com.android.server.display.LocalDisplayAdapter.DisplayEventListener
        public void onModeChanged(long j, long j2, int i, long j3) {
            synchronized (com.android.server.display.LocalDisplayAdapter.this.getSyncRoot()) {
                try {
                    com.android.server.display.LocalDisplayAdapter.LocalDisplayDevice localDisplayDevice = (com.android.server.display.LocalDisplayAdapter.LocalDisplayDevice) com.android.server.display.LocalDisplayAdapter.this.mDevices.get(j2);
                    if (localDisplayDevice == null) {
                        return;
                    }
                    localDisplayDevice.onActiveDisplayModeChangedLocked(i, 1.0E9f / j3);
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        @Override // com.android.server.display.LocalDisplayAdapter.DisplayEventListener
        public void onFrameRateOverridesChanged(long j, long j2, android.view.DisplayEventReceiver.FrameRateOverride[] frameRateOverrideArr) {
            synchronized (com.android.server.display.LocalDisplayAdapter.this.getSyncRoot()) {
                try {
                    com.android.server.display.LocalDisplayAdapter.LocalDisplayDevice localDisplayDevice = (com.android.server.display.LocalDisplayAdapter.LocalDisplayDevice) com.android.server.display.LocalDisplayAdapter.this.mDevices.get(j2);
                    if (localDisplayDevice == null) {
                        return;
                    }
                    localDisplayDevice.onFrameRateOverridesChanged(frameRateOverrideArr);
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        @Override // com.android.server.display.LocalDisplayAdapter.DisplayEventListener
        public void onHdcpLevelsChanged(long j, int i, int i2) {
            synchronized (com.android.server.display.LocalDisplayAdapter.this.getSyncRoot()) {
                try {
                    com.android.server.display.LocalDisplayAdapter.LocalDisplayDevice localDisplayDevice = (com.android.server.display.LocalDisplayAdapter.LocalDisplayDevice) com.android.server.display.LocalDisplayAdapter.this.mDevices.get(j);
                    if (localDisplayDevice == null) {
                        return;
                    }
                    localDisplayDevice.onHdcpLevelsChangedLocked(i, i2);
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    public static class SurfaceControlProxy {
        public android.view.SurfaceControl.DynamicDisplayInfo getDynamicDisplayInfo(long j) {
            return android.view.SurfaceControl.getDynamicDisplayInfo(j);
        }

        public long[] getPhysicalDisplayIds() {
            return com.android.server.display.DisplayControl.getPhysicalDisplayIds();
        }

        public android.os.IBinder getPhysicalDisplayToken(long j) {
            return com.android.server.display.DisplayControl.getPhysicalDisplayToken(j);
        }

        public android.view.SurfaceControl.StaticDisplayInfo getStaticDisplayInfo(long j) {
            return android.view.SurfaceControl.getStaticDisplayInfo(j);
        }

        public android.view.SurfaceControl.DesiredDisplayModeSpecs getDesiredDisplayModeSpecs(android.os.IBinder iBinder) {
            return android.view.SurfaceControl.getDesiredDisplayModeSpecs(iBinder);
        }

        public boolean setDesiredDisplayModeSpecs(android.os.IBinder iBinder, android.view.SurfaceControl.DesiredDisplayModeSpecs desiredDisplayModeSpecs) {
            return android.view.SurfaceControl.setDesiredDisplayModeSpecs(iBinder, desiredDisplayModeSpecs);
        }

        public void setDisplayPowerMode(android.os.IBinder iBinder, int i) {
            android.view.SurfaceControl.setDisplayPowerMode(iBinder, i);
        }

        public boolean setActiveColorMode(android.os.IBinder iBinder, int i) {
            return android.view.SurfaceControl.setActiveColorMode(iBinder, i);
        }

        public boolean getBootDisplayModeSupport() {
            android.os.Trace.traceBegin(32L, "getBootDisplayModeSupport");
            try {
                return android.view.SurfaceControl.getBootDisplayModeSupport();
            } finally {
                android.os.Trace.traceEnd(32L);
            }
        }

        public void setBootDisplayMode(android.os.IBinder iBinder, int i) {
            android.view.SurfaceControl.setBootDisplayMode(iBinder, i);
        }

        public void clearBootDisplayMode(android.os.IBinder iBinder) {
            android.view.SurfaceControl.clearBootDisplayMode(iBinder);
        }

        public void setAutoLowLatencyMode(android.os.IBinder iBinder, boolean z) {
            android.view.SurfaceControl.setAutoLowLatencyMode(iBinder, z);
        }

        public void setGameContentType(android.os.IBinder iBinder, boolean z) {
            android.view.SurfaceControl.setGameContentType(iBinder, z);
        }

        public boolean getDisplayBrightnessSupport(android.os.IBinder iBinder) {
            return android.view.SurfaceControl.getDisplayBrightnessSupport(iBinder);
        }

        public boolean setDisplayBrightness(android.os.IBinder iBinder, float f) {
            return android.view.SurfaceControl.setDisplayBrightness(iBinder, f);
        }

        public boolean setDisplayBrightness(android.os.IBinder iBinder, float f, float f2, float f3, float f4) {
            return android.view.SurfaceControl.setDisplayBrightness(iBinder, f, f2, f3, f4);
        }
    }

    static class BacklightAdapter {
        private final com.android.server.lights.LogicalLight mBacklight;
        private final android.os.IBinder mDisplayToken;
        private boolean mForceSurfaceControl = false;
        private final com.android.server.display.LocalDisplayAdapter.SurfaceControlProxy mSurfaceControlProxy;
        private final boolean mUseSurfaceControlBrightness;

        BacklightAdapter(android.os.IBinder iBinder, boolean z, com.android.server.display.LocalDisplayAdapter.SurfaceControlProxy surfaceControlProxy) {
            this.mDisplayToken = iBinder;
            this.mSurfaceControlProxy = surfaceControlProxy;
            this.mUseSurfaceControlBrightness = this.mSurfaceControlProxy.getDisplayBrightnessSupport(this.mDisplayToken);
            if (!this.mUseSurfaceControlBrightness && z) {
                this.mBacklight = ((com.android.server.lights.LightsManager) com.android.server.LocalServices.getService(com.android.server.lights.LightsManager.class)).getLight(0);
            } else {
                this.mBacklight = null;
            }
        }

        void setBacklight(float f, float f2, float f3, float f4) {
            if (this.mUseSurfaceControlBrightness || this.mForceSurfaceControl) {
                if (com.android.internal.display.BrightnessSynchronizer.floatEquals(f, Float.NaN)) {
                    this.mSurfaceControlProxy.setDisplayBrightness(this.mDisplayToken, f3);
                    return;
                } else {
                    this.mSurfaceControlProxy.setDisplayBrightness(this.mDisplayToken, f, f2, f3, f4);
                    return;
                }
            }
            if (this.mBacklight != null) {
                this.mBacklight.setBrightness(f3);
            }
        }

        void setForceSurfaceControl(boolean z) {
            this.mForceSurfaceControl = z;
        }

        public java.lang.String toString() {
            return "BacklightAdapter [useSurfaceControl=" + this.mUseSurfaceControlBrightness + " (force_anyway? " + this.mForceSurfaceControl + "), backlight=" + this.mBacklight + "]";
        }
    }
}
