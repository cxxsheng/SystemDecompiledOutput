package com.android.server.display;

/* loaded from: classes.dex */
final class LogicalDisplay {
    private static final int BLANK_LAYER_STACK = -1;
    private static final android.view.DisplayInfo EMPTY_DISPLAY_INFO = new android.view.DisplayInfo();
    private static final java.lang.String TAG = "LogicalDisplay";
    private java.lang.String mDisplayGroupName;
    private final int mDisplayId;
    private com.android.server.display.DisplayOffloadSessionImpl mDisplayOffloadSession;
    private int mDisplayOffsetX;
    private int mDisplayOffsetY;
    private boolean mDisplayScalingDisabled;
    private android.view.DisplayEventReceiver.FrameRateOverride[] mFrameRateOverrides;
    private boolean mHasContent;
    private final int mLayerStack;

    @android.annotation.Nullable
    private android.view.SurfaceControl.RefreshRateRange mLayoutLimitedRefreshRate;
    private android.view.DisplayInfo mOverrideDisplayInfo;
    private com.android.server.display.DisplayDevice mPrimaryDisplayDevice;
    private com.android.server.display.DisplayDeviceInfo mPrimaryDisplayDeviceInfo;
    private int mRequestedColorMode;
    private boolean mRequestedMinimalPostProcessing;
    private final android.view.DisplayInfo mBaseDisplayInfo = new android.view.DisplayInfo();
    private int mLeadDisplayId = -1;
    private int mDisplayGroupId = -1;
    private final com.android.server.display.DisplayInfoProxy mInfo = new com.android.server.display.DisplayInfoProxy(null);
    private int[] mUserDisabledHdrTypes = new int[0];
    private com.android.server.display.mode.DisplayModeDirector.DesiredDisplayModeSpecs mDesiredDisplayModeSpecs = new com.android.server.display.mode.DisplayModeDirector.DesiredDisplayModeSpecs();
    private final android.graphics.Point mDisplayPosition = new android.graphics.Point();
    private final android.graphics.Rect mTempLayerStackRect = new android.graphics.Rect();
    private final android.graphics.Rect mTempDisplayRect = new android.graphics.Rect();
    private int mDevicePosition = -1;
    private boolean mDirty = false;

    @android.annotation.NonNull
    private android.util.SparseArray<android.view.SurfaceControl.RefreshRateRange> mThermalRefreshRateThrottling = new android.util.SparseArray<>();
    private android.util.ArraySet<java.lang.Integer> mPendingFrameRateOverrideUids = new android.util.ArraySet<>();
    private final android.util.SparseArray<java.lang.Float> mTempFrameRateOverride = new android.util.SparseArray<>();
    private boolean mIsEnabled = true;
    private boolean mIsInTransition = false;
    private java.lang.String mThermalBrightnessThrottlingDataId = "default";
    private java.lang.String mPowerThrottlingDataId = "default";

    LogicalDisplay(int i, int i2, com.android.server.display.DisplayDevice displayDevice) {
        this.mDisplayId = i;
        this.mLayerStack = i2;
        this.mPrimaryDisplayDevice = displayDevice;
        this.mBaseDisplayInfo.thermalBrightnessThrottlingDataId = this.mThermalBrightnessThrottlingDataId;
    }

    public void setDevicePositionLocked(int i) {
        if (this.mDevicePosition != i) {
            this.mDevicePosition = i;
            this.mDirty = true;
        }
    }

    public int getDevicePositionLocked() {
        return this.mDevicePosition;
    }

    public int getDisplayIdLocked() {
        return this.mDisplayId;
    }

    public com.android.server.display.DisplayDevice getPrimaryDisplayDeviceLocked() {
        return this.mPrimaryDisplayDevice;
    }

    public android.view.DisplayInfo getDisplayInfoLocked() {
        if (this.mInfo.get() == null) {
            android.view.DisplayInfo displayInfo = new android.view.DisplayInfo();
            com.android.server.wm.utils.DisplayInfoOverrides.copyDisplayInfoFields(displayInfo, this.mBaseDisplayInfo, this.mOverrideDisplayInfo, com.android.server.wm.utils.DisplayInfoOverrides.WM_OVERRIDE_FIELDS);
            this.mInfo.set(displayInfo);
        }
        return this.mInfo.get();
    }

    public android.view.DisplayEventReceiver.FrameRateOverride[] getFrameRateOverrides() {
        return this.mFrameRateOverrides;
    }

    public android.util.ArraySet<java.lang.Integer> getPendingFrameRateOverrideUids() {
        return this.mPendingFrameRateOverrideUids;
    }

    public void clearPendingFrameRateOverrideUids() {
        this.mPendingFrameRateOverrideUids = new android.util.ArraySet<>();
    }

    void getNonOverrideDisplayInfoLocked(android.view.DisplayInfo displayInfo) {
        displayInfo.copyFrom(this.mBaseDisplayInfo);
    }

    public boolean setDisplayInfoOverrideFromWindowManagerLocked(android.view.DisplayInfo displayInfo) {
        if (displayInfo != null) {
            if (this.mOverrideDisplayInfo == null) {
                this.mOverrideDisplayInfo = new android.view.DisplayInfo(displayInfo);
                this.mInfo.set(null);
                return true;
            }
            if (!this.mOverrideDisplayInfo.equals(displayInfo)) {
                this.mOverrideDisplayInfo.copyFrom(displayInfo);
                this.mInfo.set(null);
                return true;
            }
            return false;
        }
        if (this.mOverrideDisplayInfo != null) {
            this.mOverrideDisplayInfo = null;
            this.mInfo.set(null);
            return true;
        }
        return false;
    }

    public boolean isValidLocked() {
        return this.mPrimaryDisplayDevice != null;
    }

    boolean isDirtyLocked() {
        return this.mDirty;
    }

    public void updateDisplayGroupIdLocked(int i) {
        if (i != this.mDisplayGroupId) {
            this.mDisplayGroupId = i;
            this.mDirty = true;
        }
    }

    public void updateLayoutLimitedRefreshRateLocked(@android.annotation.Nullable android.view.SurfaceControl.RefreshRateRange refreshRateRange) {
        if (!java.util.Objects.equals(refreshRateRange, this.mLayoutLimitedRefreshRate)) {
            this.mLayoutLimitedRefreshRate = refreshRateRange;
            this.mDirty = true;
        }
    }

    public void updateThermalRefreshRateThrottling(@android.annotation.Nullable android.util.SparseArray<android.view.SurfaceControl.RefreshRateRange> sparseArray) {
        if (sparseArray == null) {
            sparseArray = new android.util.SparseArray<>();
        }
        if (!this.mThermalRefreshRateThrottling.contentEquals(sparseArray)) {
            this.mThermalRefreshRateThrottling = sparseArray;
            this.mDirty = true;
        }
    }

    public void updateLocked(com.android.server.display.DisplayDeviceRepository displayDeviceRepository) {
        if (this.mPrimaryDisplayDevice == null) {
            return;
        }
        if (!displayDeviceRepository.containsLocked(this.mPrimaryDisplayDevice)) {
            setPrimaryDisplayDeviceLocked(null);
            return;
        }
        com.android.server.display.DisplayDeviceInfo displayDeviceInfoLocked = this.mPrimaryDisplayDevice.getDisplayDeviceInfoLocked();
        if (!java.util.Objects.equals(this.mPrimaryDisplayDeviceInfo, displayDeviceInfoLocked) || this.mDirty) {
            this.mBaseDisplayInfo.layerStack = this.mLayerStack;
            this.mBaseDisplayInfo.flags = 0;
            this.mBaseDisplayInfo.removeMode = 0;
            if ((displayDeviceInfoLocked.flags & 8) != 0) {
                this.mBaseDisplayInfo.flags |= 1;
            }
            if ((displayDeviceInfoLocked.flags & 4) != 0) {
                this.mBaseDisplayInfo.flags |= 2;
            }
            if ((displayDeviceInfoLocked.flags & 16) != 0) {
                this.mBaseDisplayInfo.flags |= 4;
                this.mBaseDisplayInfo.removeMode = 1;
            }
            if ((displayDeviceInfoLocked.flags & 1024) != 0) {
                this.mBaseDisplayInfo.removeMode = 1;
            }
            if ((displayDeviceInfoLocked.flags & 64) != 0) {
                this.mBaseDisplayInfo.flags |= 8;
            }
            if ((displayDeviceInfoLocked.flags & 256) != 0) {
                this.mBaseDisplayInfo.flags |= 16;
            }
            if ((displayDeviceInfoLocked.flags & 512) != 0) {
                this.mBaseDisplayInfo.flags |= 32;
            }
            if ((displayDeviceInfoLocked.flags & 4096) != 0) {
                this.mBaseDisplayInfo.flags |= 64;
            }
            if ((displayDeviceInfoLocked.flags & 8192) != 0) {
                this.mBaseDisplayInfo.flags |= 128;
            }
            if ((displayDeviceInfoLocked.flags & 16384) != 0) {
                this.mBaseDisplayInfo.flags |= 256;
            }
            if ((displayDeviceInfoLocked.flags & 32768) != 0) {
                this.mBaseDisplayInfo.flags |= 512;
            }
            if ((displayDeviceInfoLocked.flags & 2) != 0) {
                this.mBaseDisplayInfo.flags |= 16384;
            }
            if ((displayDeviceInfoLocked.flags & 65536) != 0) {
                this.mBaseDisplayInfo.flags |= 1024;
            }
            if ((displayDeviceInfoLocked.flags & 131072) != 0) {
                this.mBaseDisplayInfo.flags |= 2048;
            }
            if ((displayDeviceInfoLocked.flags & 524288) != 0) {
                this.mBaseDisplayInfo.flags |= 4096;
            }
            android.graphics.Rect maskingInsets = getMaskingInsets(displayDeviceInfoLocked);
            int i = (displayDeviceInfoLocked.width - maskingInsets.left) - maskingInsets.right;
            int i2 = (displayDeviceInfoLocked.height - maskingInsets.top) - maskingInsets.bottom;
            this.mBaseDisplayInfo.type = displayDeviceInfoLocked.type;
            this.mBaseDisplayInfo.address = displayDeviceInfoLocked.address;
            this.mBaseDisplayInfo.deviceProductInfo = displayDeviceInfoLocked.deviceProductInfo;
            this.mBaseDisplayInfo.name = displayDeviceInfoLocked.name;
            this.mBaseDisplayInfo.uniqueId = displayDeviceInfoLocked.uniqueId;
            this.mBaseDisplayInfo.appWidth = i;
            this.mBaseDisplayInfo.appHeight = i2;
            this.mBaseDisplayInfo.logicalWidth = i;
            this.mBaseDisplayInfo.logicalHeight = i2;
            this.mBaseDisplayInfo.rotation = 0;
            this.mBaseDisplayInfo.modeId = displayDeviceInfoLocked.modeId;
            this.mBaseDisplayInfo.renderFrameRate = displayDeviceInfoLocked.renderFrameRate;
            this.mBaseDisplayInfo.defaultModeId = displayDeviceInfoLocked.defaultModeId;
            this.mBaseDisplayInfo.userPreferredModeId = displayDeviceInfoLocked.userPreferredModeId;
            this.mBaseDisplayInfo.supportedModes = (android.view.Display.Mode[]) java.util.Arrays.copyOf(displayDeviceInfoLocked.supportedModes, displayDeviceInfoLocked.supportedModes.length);
            this.mBaseDisplayInfo.colorMode = displayDeviceInfoLocked.colorMode;
            this.mBaseDisplayInfo.supportedColorModes = java.util.Arrays.copyOf(displayDeviceInfoLocked.supportedColorModes, displayDeviceInfoLocked.supportedColorModes.length);
            this.mBaseDisplayInfo.hdrCapabilities = displayDeviceInfoLocked.hdrCapabilities;
            this.mBaseDisplayInfo.userDisabledHdrTypes = this.mUserDisabledHdrTypes;
            this.mBaseDisplayInfo.minimalPostProcessingSupported = displayDeviceInfoLocked.allmSupported || displayDeviceInfoLocked.gameContentTypeSupported;
            this.mBaseDisplayInfo.logicalDensityDpi = displayDeviceInfoLocked.densityDpi;
            this.mBaseDisplayInfo.physicalXDpi = displayDeviceInfoLocked.xDpi;
            this.mBaseDisplayInfo.physicalYDpi = displayDeviceInfoLocked.yDpi;
            this.mBaseDisplayInfo.appVsyncOffsetNanos = displayDeviceInfoLocked.appVsyncOffsetNanos;
            this.mBaseDisplayInfo.presentationDeadlineNanos = displayDeviceInfoLocked.presentationDeadlineNanos;
            this.mBaseDisplayInfo.state = displayDeviceInfoLocked.state;
            this.mBaseDisplayInfo.committedState = displayDeviceInfoLocked.committedState;
            this.mBaseDisplayInfo.smallestNominalAppWidth = i;
            this.mBaseDisplayInfo.smallestNominalAppHeight = i2;
            this.mBaseDisplayInfo.largestNominalAppWidth = i;
            this.mBaseDisplayInfo.largestNominalAppHeight = i2;
            this.mBaseDisplayInfo.ownerUid = displayDeviceInfoLocked.ownerUid;
            this.mBaseDisplayInfo.ownerPackageName = displayDeviceInfoLocked.ownerPackageName;
            this.mBaseDisplayInfo.displayCutout = (displayDeviceInfoLocked.flags & 2048) != 0 ? null : displayDeviceInfoLocked.displayCutout;
            this.mBaseDisplayInfo.displayId = this.mDisplayId;
            this.mBaseDisplayInfo.displayGroupId = this.mDisplayGroupId;
            updateFrameRateOverrides(displayDeviceInfoLocked);
            this.mBaseDisplayInfo.brightnessMinimum = displayDeviceInfoLocked.brightnessMinimum;
            this.mBaseDisplayInfo.brightnessMaximum = displayDeviceInfoLocked.brightnessMaximum;
            this.mBaseDisplayInfo.brightnessDefault = displayDeviceInfoLocked.brightnessDefault;
            this.mBaseDisplayInfo.hdrSdrRatio = displayDeviceInfoLocked.hdrSdrRatio;
            this.mBaseDisplayInfo.roundedCorners = displayDeviceInfoLocked.roundedCorners;
            this.mBaseDisplayInfo.installOrientation = displayDeviceInfoLocked.installOrientation;
            this.mBaseDisplayInfo.displayShape = displayDeviceInfoLocked.displayShape;
            if (this.mDevicePosition == 1) {
                this.mBaseDisplayInfo.flags |= 8192;
                this.mBaseDisplayInfo.flags |= 8;
                this.mBaseDisplayInfo.removeMode = 1;
            }
            this.mBaseDisplayInfo.layoutLimitedRefreshRate = this.mLayoutLimitedRefreshRate;
            this.mBaseDisplayInfo.thermalRefreshRateThrottling = this.mThermalRefreshRateThrottling;
            this.mBaseDisplayInfo.thermalBrightnessThrottlingDataId = this.mThermalBrightnessThrottlingDataId;
            this.mPrimaryDisplayDeviceInfo = displayDeviceInfoLocked;
            this.mInfo.set(null);
            this.mDirty = false;
        }
    }

    private void updateFrameRateOverrides(com.android.server.display.DisplayDeviceInfo displayDeviceInfo) {
        this.mTempFrameRateOverride.clear();
        if (this.mFrameRateOverrides != null) {
            for (android.view.DisplayEventReceiver.FrameRateOverride frameRateOverride : this.mFrameRateOverrides) {
                this.mTempFrameRateOverride.put(frameRateOverride.uid, java.lang.Float.valueOf(frameRateOverride.frameRateHz));
            }
        }
        this.mFrameRateOverrides = displayDeviceInfo.frameRateOverrides;
        if (this.mFrameRateOverrides != null) {
            for (android.view.DisplayEventReceiver.FrameRateOverride frameRateOverride2 : this.mFrameRateOverrides) {
                float floatValue = this.mTempFrameRateOverride.get(frameRateOverride2.uid, java.lang.Float.valueOf(com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE)).floatValue();
                if (floatValue == com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE || frameRateOverride2.frameRateHz != floatValue) {
                    this.mTempFrameRateOverride.put(frameRateOverride2.uid, java.lang.Float.valueOf(frameRateOverride2.frameRateHz));
                } else {
                    this.mTempFrameRateOverride.delete(frameRateOverride2.uid);
                }
            }
        }
        for (int i = 0; i < this.mTempFrameRateOverride.size(); i++) {
            this.mPendingFrameRateOverrideUids.add(java.lang.Integer.valueOf(this.mTempFrameRateOverride.keyAt(i)));
        }
    }

    public android.graphics.Rect getInsets() {
        return getMaskingInsets(this.mPrimaryDisplayDeviceInfo);
    }

    private static android.graphics.Rect getMaskingInsets(com.android.server.display.DisplayDeviceInfo displayDeviceInfo) {
        if (((displayDeviceInfo.flags & 2048) != 0) && displayDeviceInfo.displayCutout != null) {
            return displayDeviceInfo.displayCutout.getSafeInsets();
        }
        return new android.graphics.Rect();
    }

    android.graphics.Point getDisplayPosition() {
        return new android.graphics.Point(this.mDisplayPosition);
    }

    public void configureDisplayLocked(android.view.SurfaceControl.Transaction transaction, com.android.server.display.DisplayDevice displayDevice, boolean z) {
        int i;
        int i2;
        int i3;
        int i4;
        displayDevice.setLayerStackLocked(transaction, z ? -1 : this.mLayerStack, this.mDisplayId);
        if (isEnabledLocked() && displayDevice.getDisplayDeviceInfoLocked().touch != 0) {
            i = 1;
        } else {
            i = 0;
        }
        displayDevice.setDisplayFlagsLocked(transaction, i);
        if (displayDevice == this.mPrimaryDisplayDevice) {
            displayDevice.setDesiredDisplayModeSpecsLocked(this.mDesiredDisplayModeSpecs);
            displayDevice.setRequestedColorModeLocked(this.mRequestedColorMode);
        } else {
            displayDevice.setDesiredDisplayModeSpecsLocked(new com.android.server.display.mode.DisplayModeDirector.DesiredDisplayModeSpecs());
            displayDevice.setRequestedColorModeLocked(0);
        }
        displayDevice.setAutoLowLatencyModeLocked(this.mRequestedMinimalPostProcessing);
        displayDevice.setGameContentTypeLocked(this.mRequestedMinimalPostProcessing);
        android.view.DisplayInfo displayInfoLocked = getDisplayInfoLocked();
        com.android.server.display.DisplayDeviceInfo displayDeviceInfoLocked = displayDevice.getDisplayDeviceInfoLocked();
        this.mTempLayerStackRect.set(0, 0, displayInfoLocked.logicalWidth, displayInfoLocked.logicalHeight);
        if ((displayDeviceInfoLocked.flags & 2) == 0) {
            i2 = 0;
        } else {
            i2 = displayInfoLocked.rotation;
        }
        int i5 = (i2 + displayDeviceInfoLocked.rotation) % 4;
        boolean z2 = i5 == 1 || i5 == 3;
        int i6 = z2 ? displayDeviceInfoLocked.height : displayDeviceInfoLocked.width;
        int i7 = z2 ? displayDeviceInfoLocked.width : displayDeviceInfoLocked.height;
        android.graphics.Rect maskingInsets = getMaskingInsets(displayDeviceInfoLocked);
        com.android.server.wm.utils.InsetUtils.rotateInsets(maskingInsets, i5);
        int i8 = i6 - (maskingInsets.left + maskingInsets.right);
        int i9 = i7 - (maskingInsets.top + maskingInsets.bottom);
        if ((displayInfoLocked.flags & 1073741824) != 0 || this.mDisplayScalingDisabled) {
            int i10 = displayInfoLocked.logicalWidth;
            i3 = displayInfoLocked.logicalHeight;
            i4 = i10;
        } else if (displayInfoLocked.logicalHeight * i8 < displayInfoLocked.logicalWidth * i9) {
            i3 = (displayInfoLocked.logicalHeight * i8) / displayInfoLocked.logicalWidth;
            i4 = i8;
        } else {
            i4 = (displayInfoLocked.logicalWidth * i9) / displayInfoLocked.logicalHeight;
            i3 = i9;
        }
        int i11 = (i9 - i3) / 2;
        int i12 = (i8 - i4) / 2;
        this.mTempDisplayRect.set(i12, i11, i4 + i12, i3 + i11);
        this.mTempDisplayRect.offset(maskingInsets.left, maskingInsets.top);
        if (i5 == 0) {
            this.mTempDisplayRect.offset(this.mDisplayOffsetX, this.mDisplayOffsetY);
        } else if (i5 == 1) {
            this.mTempDisplayRect.offset(this.mDisplayOffsetY, -this.mDisplayOffsetX);
        } else if (i5 == 2) {
            this.mTempDisplayRect.offset(-this.mDisplayOffsetX, -this.mDisplayOffsetY);
        } else {
            this.mTempDisplayRect.offset(-this.mDisplayOffsetY, this.mDisplayOffsetX);
        }
        this.mDisplayPosition.set(this.mTempDisplayRect.left, this.mTempDisplayRect.top);
        displayDevice.setProjectionLocked(transaction, i5, this.mTempLayerStackRect, this.mTempDisplayRect);
    }

    public boolean hasContentLocked() {
        return this.mHasContent;
    }

    public void setHasContentLocked(boolean z) {
        this.mHasContent = z;
    }

    public void setDesiredDisplayModeSpecsLocked(com.android.server.display.mode.DisplayModeDirector.DesiredDisplayModeSpecs desiredDisplayModeSpecs) {
        this.mDesiredDisplayModeSpecs = desiredDisplayModeSpecs;
    }

    public com.android.server.display.mode.DisplayModeDirector.DesiredDisplayModeSpecs getDesiredDisplayModeSpecsLocked() {
        return this.mDesiredDisplayModeSpecs;
    }

    public void setRequestedColorModeLocked(int i) {
        this.mRequestedColorMode = i;
    }

    public boolean getRequestedMinimalPostProcessingLocked() {
        return this.mRequestedMinimalPostProcessing;
    }

    public void setRequestedMinimalPostProcessingLocked(boolean z) {
        this.mRequestedMinimalPostProcessing = z;
    }

    public int getRequestedColorModeLocked() {
        return this.mRequestedColorMode;
    }

    public int getDisplayOffsetXLocked() {
        return this.mDisplayOffsetX;
    }

    public int getDisplayOffsetYLocked() {
        return this.mDisplayOffsetY;
    }

    public void setDisplayOffsetsLocked(int i, int i2) {
        this.mDisplayOffsetX = i;
        this.mDisplayOffsetY = i2;
    }

    public boolean isDisplayScalingDisabled() {
        return this.mDisplayScalingDisabled;
    }

    public void setDisplayScalingDisabledLocked(boolean z) {
        this.mDisplayScalingDisabled = z;
    }

    public void setUserDisabledHdrTypes(@android.annotation.NonNull int[] iArr) {
        if (this.mUserDisabledHdrTypes != iArr) {
            this.mUserDisabledHdrTypes = iArr;
            this.mBaseDisplayInfo.userDisabledHdrTypes = iArr;
            this.mInfo.set(null);
        }
    }

    public void swapDisplaysLocked(@android.annotation.NonNull com.android.server.display.LogicalDisplay logicalDisplay) {
        setPrimaryDisplayDeviceLocked(logicalDisplay.setPrimaryDisplayDeviceLocked(this.mPrimaryDisplayDevice));
    }

    public com.android.server.display.DisplayDevice setPrimaryDisplayDeviceLocked(@android.annotation.Nullable com.android.server.display.DisplayDevice displayDevice) {
        com.android.server.display.DisplayDevice displayDevice2 = this.mPrimaryDisplayDevice;
        this.mPrimaryDisplayDevice = displayDevice;
        this.mPrimaryDisplayDeviceInfo = null;
        this.mBaseDisplayInfo.copyFrom(EMPTY_DISPLAY_INFO);
        this.mInfo.set(null);
        return displayDevice2;
    }

    public boolean isEnabledLocked() {
        return this.mIsEnabled;
    }

    public void setEnabledLocked(boolean z) {
        if (z != this.mIsEnabled) {
            this.mDirty = true;
            this.mIsEnabled = z;
        }
    }

    public boolean isInTransitionLocked() {
        return this.mIsInTransition;
    }

    public void setIsInTransitionLocked(boolean z) {
        this.mIsInTransition = z;
    }

    public void setThermalBrightnessThrottlingDataIdLocked(java.lang.String str) {
        if (!java.util.Objects.equals(str, this.mThermalBrightnessThrottlingDataId)) {
            this.mThermalBrightnessThrottlingDataId = str;
            this.mDirty = true;
        }
    }

    public void setPowerThrottlingDataIdLocked(java.lang.String str) {
        if (!java.util.Objects.equals(str, this.mPowerThrottlingDataId)) {
            this.mPowerThrottlingDataId = str;
            this.mDirty = true;
        }
    }

    public java.lang.String getPowerThrottlingDataIdLocked() {
        return this.mPowerThrottlingDataId;
    }

    public void setLeadDisplayLocked(int i) {
        if (this.mDisplayId != this.mLeadDisplayId && this.mDisplayId != i) {
            this.mLeadDisplayId = i;
        }
    }

    public int getLeadDisplayIdLocked() {
        return this.mLeadDisplayId;
    }

    public void setDisplayGroupNameLocked(java.lang.String str) {
        this.mDisplayGroupName = str;
    }

    public java.lang.String getDisplayGroupNameLocked() {
        return this.mDisplayGroupName;
    }

    public void setDisplayOffloadSessionLocked(com.android.server.display.DisplayOffloadSessionImpl displayOffloadSessionImpl) {
        this.mDisplayOffloadSession = displayOffloadSessionImpl;
    }

    public com.android.server.display.DisplayOffloadSessionImpl getDisplayOffloadSessionLocked() {
        return this.mDisplayOffloadSession;
    }

    public void dumpLocked(java.io.PrintWriter printWriter) {
        printWriter.println("mDisplayId=" + this.mDisplayId);
        printWriter.println("mIsEnabled=" + this.mIsEnabled);
        printWriter.println("mIsInTransition=" + this.mIsInTransition);
        printWriter.println("mLayerStack=" + this.mLayerStack);
        printWriter.println("mPosition=" + this.mDevicePosition);
        printWriter.println("mHasContent=" + this.mHasContent);
        printWriter.println("mDesiredDisplayModeSpecs={" + this.mDesiredDisplayModeSpecs + "}");
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append("mRequestedColorMode=");
        sb.append(this.mRequestedColorMode);
        printWriter.println(sb.toString());
        printWriter.println("mDisplayOffset=(" + this.mDisplayOffsetX + ", " + this.mDisplayOffsetY + ")");
        java.lang.StringBuilder sb2 = new java.lang.StringBuilder();
        sb2.append("mDisplayScalingDisabled=");
        sb2.append(this.mDisplayScalingDisabled);
        printWriter.println(sb2.toString());
        java.lang.StringBuilder sb3 = new java.lang.StringBuilder();
        sb3.append("mPrimaryDisplayDevice=");
        sb3.append(this.mPrimaryDisplayDevice != null ? this.mPrimaryDisplayDevice.getNameLocked() : "null");
        printWriter.println(sb3.toString());
        printWriter.println("mBaseDisplayInfo=" + this.mBaseDisplayInfo);
        printWriter.println("mOverrideDisplayInfo=" + this.mOverrideDisplayInfo);
        printWriter.println("mRequestedMinimalPostProcessing=" + this.mRequestedMinimalPostProcessing);
        printWriter.println("mFrameRateOverrides=" + java.util.Arrays.toString(this.mFrameRateOverrides));
        printWriter.println("mPendingFrameRateOverrideUids=" + this.mPendingFrameRateOverrideUids);
        printWriter.println("mDisplayGroupName=" + this.mDisplayGroupName);
        printWriter.println("mThermalBrightnessThrottlingDataId=" + this.mThermalBrightnessThrottlingDataId);
        printWriter.println("mLeadDisplayId=" + this.mLeadDisplayId);
        printWriter.println("mLayoutLimitedRefreshRate=" + this.mLayoutLimitedRefreshRate);
        printWriter.println("mThermalRefreshRateThrottling=" + this.mThermalRefreshRateThrottling);
        printWriter.println("mPowerThrottlingDataId=" + this.mPowerThrottlingDataId);
    }

    public java.lang.String toString() {
        java.io.StringWriter stringWriter = new java.io.StringWriter();
        dumpLocked(new java.io.PrintWriter(stringWriter));
        return stringWriter.toString();
    }
}
