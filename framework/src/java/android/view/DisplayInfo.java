package android.view;

/* loaded from: classes4.dex */
public final class DisplayInfo implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.view.DisplayInfo> CREATOR = new android.os.Parcelable.Creator<android.view.DisplayInfo>() { // from class: android.view.DisplayInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.view.DisplayInfo createFromParcel(android.os.Parcel parcel) {
            return new android.view.DisplayInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.view.DisplayInfo[] newArray(int i) {
            return new android.view.DisplayInfo[i];
        }
    };
    public android.view.DisplayAddress address;
    public int appHeight;
    public long appVsyncOffsetNanos;
    public int appWidth;
    public float brightnessDefault;
    public float brightnessMaximum;
    public float brightnessMinimum;
    public int colorMode;
    public int committedState;
    public int defaultModeId;
    public android.hardware.display.DeviceProductInfo deviceProductInfo;
    public android.view.DisplayCutout displayCutout;
    public int displayGroupId;
    public int displayId;
    public android.view.DisplayShape displayShape;
    public int flags;
    public android.view.Display.HdrCapabilities hdrCapabilities;
    public float hdrSdrRatio;
    public int installOrientation;
    public int largestNominalAppHeight;
    public int largestNominalAppWidth;
    public int layerStack;
    public android.view.SurfaceControl.RefreshRateRange layoutLimitedRefreshRate;
    public int logicalDensityDpi;
    public int logicalHeight;
    public int logicalWidth;
    public boolean minimalPostProcessingSupported;
    public int modeId;
    public java.lang.String name;
    public java.lang.String ownerPackageName;
    public int ownerUid;
    public float physicalXDpi;
    public float physicalYDpi;
    public long presentationDeadlineNanos;
    public float refreshRateOverride;
    public int removeMode;
    public float renderFrameRate;
    public int rotation;
    public android.view.RoundedCorners roundedCorners;
    public int smallestNominalAppHeight;
    public int smallestNominalAppWidth;
    public int state;
    public int[] supportedColorModes;
    public android.view.Display.Mode[] supportedModes;
    public java.lang.String thermalBrightnessThrottlingDataId;
    public android.util.SparseArray<android.view.SurfaceControl.RefreshRateRange> thermalRefreshRateThrottling;
    public int type;
    public java.lang.String uniqueId;
    public int[] userDisabledHdrTypes;
    public int userPreferredModeId;

    public DisplayInfo() {
        this.userPreferredModeId = -1;
        this.supportedModes = android.view.Display.Mode.EMPTY_ARRAY;
        this.supportedColorModes = new int[]{0};
        this.userDisabledHdrTypes = new int[0];
        this.removeMode = 0;
        this.hdrSdrRatio = Float.NaN;
        this.thermalRefreshRateThrottling = new android.util.SparseArray<>();
    }

    public DisplayInfo(android.view.DisplayInfo displayInfo) {
        this.userPreferredModeId = -1;
        this.supportedModes = android.view.Display.Mode.EMPTY_ARRAY;
        this.supportedColorModes = new int[]{0};
        this.userDisabledHdrTypes = new int[0];
        this.removeMode = 0;
        this.hdrSdrRatio = Float.NaN;
        this.thermalRefreshRateThrottling = new android.util.SparseArray<>();
        copyFrom(displayInfo);
    }

    private DisplayInfo(android.os.Parcel parcel) {
        this.userPreferredModeId = -1;
        this.supportedModes = android.view.Display.Mode.EMPTY_ARRAY;
        this.supportedColorModes = new int[]{0};
        this.userDisabledHdrTypes = new int[0];
        this.removeMode = 0;
        this.hdrSdrRatio = Float.NaN;
        this.thermalRefreshRateThrottling = new android.util.SparseArray<>();
        readFromParcel(parcel);
    }

    public boolean equals(java.lang.Object obj) {
        return (obj instanceof android.view.DisplayInfo) && equals((android.view.DisplayInfo) obj);
    }

    public boolean equals(android.view.DisplayInfo displayInfo) {
        return displayInfo != null && this.layerStack == displayInfo.layerStack && this.flags == displayInfo.flags && this.type == displayInfo.type && this.displayId == displayInfo.displayId && this.displayGroupId == displayInfo.displayGroupId && java.util.Objects.equals(this.address, displayInfo.address) && java.util.Objects.equals(this.deviceProductInfo, displayInfo.deviceProductInfo) && java.util.Objects.equals(this.uniqueId, displayInfo.uniqueId) && this.appWidth == displayInfo.appWidth && this.appHeight == displayInfo.appHeight && this.smallestNominalAppWidth == displayInfo.smallestNominalAppWidth && this.smallestNominalAppHeight == displayInfo.smallestNominalAppHeight && this.largestNominalAppWidth == displayInfo.largestNominalAppWidth && this.largestNominalAppHeight == displayInfo.largestNominalAppHeight && this.logicalWidth == displayInfo.logicalWidth && this.logicalHeight == displayInfo.logicalHeight && java.util.Objects.equals(this.displayCutout, displayInfo.displayCutout) && this.rotation == displayInfo.rotation && this.modeId == displayInfo.modeId && this.renderFrameRate == displayInfo.renderFrameRate && this.defaultModeId == displayInfo.defaultModeId && this.userPreferredModeId == displayInfo.userPreferredModeId && java.util.Arrays.equals(this.supportedModes, displayInfo.supportedModes) && this.colorMode == displayInfo.colorMode && java.util.Arrays.equals(this.supportedColorModes, displayInfo.supportedColorModes) && java.util.Objects.equals(this.hdrCapabilities, displayInfo.hdrCapabilities) && java.util.Arrays.equals(this.userDisabledHdrTypes, displayInfo.userDisabledHdrTypes) && this.minimalPostProcessingSupported == displayInfo.minimalPostProcessingSupported && this.logicalDensityDpi == displayInfo.logicalDensityDpi && this.physicalXDpi == displayInfo.physicalXDpi && this.physicalYDpi == displayInfo.physicalYDpi && this.appVsyncOffsetNanos == displayInfo.appVsyncOffsetNanos && this.presentationDeadlineNanos == displayInfo.presentationDeadlineNanos && this.state == displayInfo.state && this.committedState == displayInfo.committedState && this.ownerUid == displayInfo.ownerUid && java.util.Objects.equals(this.ownerPackageName, displayInfo.ownerPackageName) && this.removeMode == displayInfo.removeMode && getRefreshRate() == displayInfo.getRefreshRate() && this.brightnessMinimum == displayInfo.brightnessMinimum && this.brightnessMaximum == displayInfo.brightnessMaximum && this.brightnessDefault == displayInfo.brightnessDefault && java.util.Objects.equals(this.roundedCorners, displayInfo.roundedCorners) && this.installOrientation == displayInfo.installOrientation && java.util.Objects.equals(this.displayShape, displayInfo.displayShape) && java.util.Objects.equals(this.layoutLimitedRefreshRate, displayInfo.layoutLimitedRefreshRate) && com.android.internal.display.BrightnessSynchronizer.floatEquals(this.hdrSdrRatio, displayInfo.hdrSdrRatio) && this.thermalRefreshRateThrottling.contentEquals(displayInfo.thermalRefreshRateThrottling) && java.util.Objects.equals(this.thermalBrightnessThrottlingDataId, displayInfo.thermalBrightnessThrottlingDataId);
    }

    public int hashCode() {
        return 0;
    }

    public void copyFrom(android.view.DisplayInfo displayInfo) {
        this.layerStack = displayInfo.layerStack;
        this.flags = displayInfo.flags;
        this.type = displayInfo.type;
        this.displayId = displayInfo.displayId;
        this.displayGroupId = displayInfo.displayGroupId;
        this.address = displayInfo.address;
        this.deviceProductInfo = displayInfo.deviceProductInfo;
        this.name = displayInfo.name;
        this.uniqueId = displayInfo.uniqueId;
        this.appWidth = displayInfo.appWidth;
        this.appHeight = displayInfo.appHeight;
        this.smallestNominalAppWidth = displayInfo.smallestNominalAppWidth;
        this.smallestNominalAppHeight = displayInfo.smallestNominalAppHeight;
        this.largestNominalAppWidth = displayInfo.largestNominalAppWidth;
        this.largestNominalAppHeight = displayInfo.largestNominalAppHeight;
        this.logicalWidth = displayInfo.logicalWidth;
        this.logicalHeight = displayInfo.logicalHeight;
        this.displayCutout = displayInfo.displayCutout;
        this.rotation = displayInfo.rotation;
        this.modeId = displayInfo.modeId;
        this.renderFrameRate = displayInfo.renderFrameRate;
        this.defaultModeId = displayInfo.defaultModeId;
        this.userPreferredModeId = displayInfo.userPreferredModeId;
        this.supportedModes = (android.view.Display.Mode[]) java.util.Arrays.copyOf(displayInfo.supportedModes, displayInfo.supportedModes.length);
        this.colorMode = displayInfo.colorMode;
        this.supportedColorModes = java.util.Arrays.copyOf(displayInfo.supportedColorModes, displayInfo.supportedColorModes.length);
        this.hdrCapabilities = displayInfo.hdrCapabilities;
        this.userDisabledHdrTypes = displayInfo.userDisabledHdrTypes;
        this.minimalPostProcessingSupported = displayInfo.minimalPostProcessingSupported;
        this.logicalDensityDpi = displayInfo.logicalDensityDpi;
        this.physicalXDpi = displayInfo.physicalXDpi;
        this.physicalYDpi = displayInfo.physicalYDpi;
        this.appVsyncOffsetNanos = displayInfo.appVsyncOffsetNanos;
        this.presentationDeadlineNanos = displayInfo.presentationDeadlineNanos;
        this.state = displayInfo.state;
        this.committedState = displayInfo.committedState;
        this.ownerUid = displayInfo.ownerUid;
        this.ownerPackageName = displayInfo.ownerPackageName;
        this.removeMode = displayInfo.removeMode;
        this.refreshRateOverride = displayInfo.refreshRateOverride;
        this.brightnessMinimum = displayInfo.brightnessMinimum;
        this.brightnessMaximum = displayInfo.brightnessMaximum;
        this.brightnessDefault = displayInfo.brightnessDefault;
        this.roundedCorners = displayInfo.roundedCorners;
        this.installOrientation = displayInfo.installOrientation;
        this.displayShape = displayInfo.displayShape;
        this.layoutLimitedRefreshRate = displayInfo.layoutLimitedRefreshRate;
        this.hdrSdrRatio = displayInfo.hdrSdrRatio;
        this.thermalRefreshRateThrottling = displayInfo.thermalRefreshRateThrottling;
        this.thermalBrightnessThrottlingDataId = displayInfo.thermalBrightnessThrottlingDataId;
    }

    public void readFromParcel(android.os.Parcel parcel) {
        this.layerStack = parcel.readInt();
        this.flags = parcel.readInt();
        this.type = parcel.readInt();
        this.displayId = parcel.readInt();
        this.displayGroupId = parcel.readInt();
        this.address = (android.view.DisplayAddress) parcel.readParcelable(null, android.view.DisplayAddress.class);
        this.deviceProductInfo = (android.hardware.display.DeviceProductInfo) parcel.readParcelable(null, android.hardware.display.DeviceProductInfo.class);
        this.name = parcel.readString8();
        this.appWidth = parcel.readInt();
        this.appHeight = parcel.readInt();
        this.smallestNominalAppWidth = parcel.readInt();
        this.smallestNominalAppHeight = parcel.readInt();
        this.largestNominalAppWidth = parcel.readInt();
        this.largestNominalAppHeight = parcel.readInt();
        this.logicalWidth = parcel.readInt();
        this.logicalHeight = parcel.readInt();
        this.displayCutout = android.view.DisplayCutout.ParcelableWrapper.readCutoutFromParcel(parcel);
        this.rotation = parcel.readInt();
        this.modeId = parcel.readInt();
        this.renderFrameRate = parcel.readFloat();
        this.defaultModeId = parcel.readInt();
        this.userPreferredModeId = parcel.readInt();
        int readInt = parcel.readInt();
        this.supportedModes = new android.view.Display.Mode[readInt];
        for (int i = 0; i < readInt; i++) {
            this.supportedModes[i] = android.view.Display.Mode.CREATOR.createFromParcel(parcel);
        }
        this.colorMode = parcel.readInt();
        int readInt2 = parcel.readInt();
        this.supportedColorModes = new int[readInt2];
        for (int i2 = 0; i2 < readInt2; i2++) {
            this.supportedColorModes[i2] = parcel.readInt();
        }
        this.hdrCapabilities = (android.view.Display.HdrCapabilities) parcel.readParcelable(null, android.view.Display.HdrCapabilities.class);
        this.minimalPostProcessingSupported = parcel.readBoolean();
        this.logicalDensityDpi = parcel.readInt();
        this.physicalXDpi = parcel.readFloat();
        this.physicalYDpi = parcel.readFloat();
        this.appVsyncOffsetNanos = parcel.readLong();
        this.presentationDeadlineNanos = parcel.readLong();
        this.state = parcel.readInt();
        this.committedState = parcel.readInt();
        this.ownerUid = parcel.readInt();
        this.ownerPackageName = parcel.readString8();
        this.uniqueId = parcel.readString8();
        this.removeMode = parcel.readInt();
        this.refreshRateOverride = parcel.readFloat();
        this.brightnessMinimum = parcel.readFloat();
        this.brightnessMaximum = parcel.readFloat();
        this.brightnessDefault = parcel.readFloat();
        this.roundedCorners = (android.view.RoundedCorners) parcel.readTypedObject(android.view.RoundedCorners.CREATOR);
        int readInt3 = parcel.readInt();
        this.userDisabledHdrTypes = new int[readInt3];
        for (int i3 = 0; i3 < readInt3; i3++) {
            this.userDisabledHdrTypes[i3] = parcel.readInt();
        }
        this.installOrientation = parcel.readInt();
        this.displayShape = (android.view.DisplayShape) parcel.readTypedObject(android.view.DisplayShape.CREATOR);
        this.layoutLimitedRefreshRate = (android.view.SurfaceControl.RefreshRateRange) parcel.readTypedObject(android.view.SurfaceControl.RefreshRateRange.CREATOR);
        this.hdrSdrRatio = parcel.readFloat();
        this.thermalRefreshRateThrottling = parcel.readSparseArray(null, android.view.SurfaceControl.RefreshRateRange.class);
        this.thermalBrightnessThrottlingDataId = parcel.readString8();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.layerStack);
        parcel.writeInt(this.flags);
        parcel.writeInt(this.type);
        parcel.writeInt(this.displayId);
        parcel.writeInt(this.displayGroupId);
        parcel.writeParcelable(this.address, i);
        parcel.writeParcelable(this.deviceProductInfo, i);
        parcel.writeString8(this.name);
        parcel.writeInt(this.appWidth);
        parcel.writeInt(this.appHeight);
        parcel.writeInt(this.smallestNominalAppWidth);
        parcel.writeInt(this.smallestNominalAppHeight);
        parcel.writeInt(this.largestNominalAppWidth);
        parcel.writeInt(this.largestNominalAppHeight);
        parcel.writeInt(this.logicalWidth);
        parcel.writeInt(this.logicalHeight);
        android.view.DisplayCutout.ParcelableWrapper.writeCutoutToParcel(this.displayCutout, parcel, i);
        parcel.writeInt(this.rotation);
        parcel.writeInt(this.modeId);
        parcel.writeFloat(this.renderFrameRate);
        parcel.writeInt(this.defaultModeId);
        parcel.writeInt(this.userPreferredModeId);
        parcel.writeInt(this.supportedModes.length);
        for (int i2 = 0; i2 < this.supportedModes.length; i2++) {
            this.supportedModes[i2].writeToParcel(parcel, i);
        }
        parcel.writeInt(this.colorMode);
        parcel.writeInt(this.supportedColorModes.length);
        for (int i3 = 0; i3 < this.supportedColorModes.length; i3++) {
            parcel.writeInt(this.supportedColorModes[i3]);
        }
        parcel.writeParcelable(this.hdrCapabilities, i);
        parcel.writeBoolean(this.minimalPostProcessingSupported);
        parcel.writeInt(this.logicalDensityDpi);
        parcel.writeFloat(this.physicalXDpi);
        parcel.writeFloat(this.physicalYDpi);
        parcel.writeLong(this.appVsyncOffsetNanos);
        parcel.writeLong(this.presentationDeadlineNanos);
        parcel.writeInt(this.state);
        parcel.writeInt(this.committedState);
        parcel.writeInt(this.ownerUid);
        parcel.writeString8(this.ownerPackageName);
        parcel.writeString8(this.uniqueId);
        parcel.writeInt(this.removeMode);
        parcel.writeFloat(this.refreshRateOverride);
        parcel.writeFloat(this.brightnessMinimum);
        parcel.writeFloat(this.brightnessMaximum);
        parcel.writeFloat(this.brightnessDefault);
        parcel.writeTypedObject(this.roundedCorners, i);
        parcel.writeInt(this.userDisabledHdrTypes.length);
        for (int i4 = 0; i4 < this.userDisabledHdrTypes.length; i4++) {
            parcel.writeInt(this.userDisabledHdrTypes[i4]);
        }
        parcel.writeInt(this.installOrientation);
        parcel.writeTypedObject(this.displayShape, i);
        parcel.writeTypedObject(this.layoutLimitedRefreshRate, i);
        parcel.writeFloat(this.hdrSdrRatio);
        parcel.writeSparseArray(this.thermalRefreshRateThrottling);
        parcel.writeString8(this.thermalBrightnessThrottlingDataId);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public float getRefreshRate() {
        if (this.refreshRateOverride > 0.0f) {
            return this.refreshRateOverride;
        }
        if (this.supportedModes.length == 0) {
            return 0.0f;
        }
        return getMode().getRefreshRate();
    }

    public android.view.Display.Mode getMode() {
        return findMode(this.modeId);
    }

    public android.view.Display.Mode getDefaultMode() {
        return findMode(this.defaultModeId);
    }

    private android.view.Display.Mode findMode(int i) {
        for (int i2 = 0; i2 < this.supportedModes.length; i2++) {
            if (this.supportedModes[i2].getModeId() == i) {
                return this.supportedModes[i2];
            }
        }
        throw new java.lang.IllegalStateException("Unable to locate mode id=" + i + ",supportedModes=" + java.util.Arrays.toString(this.supportedModes));
    }

    public android.view.Display.Mode findDefaultModeByRefreshRate(float f) {
        android.view.Display.Mode[] modeArr = this.supportedModes;
        android.view.Display.Mode defaultMode = getDefaultMode();
        for (int i = 0; i < modeArr.length; i++) {
            if (modeArr[i].matches(defaultMode.getPhysicalWidth(), defaultMode.getPhysicalHeight(), f)) {
                return modeArr[i];
            }
        }
        return null;
    }

    public float[] getDefaultRefreshRates() {
        android.view.Display.Mode[] modeArr = this.supportedModes;
        android.util.ArraySet arraySet = new android.util.ArraySet();
        android.view.Display.Mode defaultMode = getDefaultMode();
        int i = 0;
        for (android.view.Display.Mode mode : modeArr) {
            if (mode.getPhysicalWidth() == defaultMode.getPhysicalWidth() && mode.getPhysicalHeight() == defaultMode.getPhysicalHeight()) {
                arraySet.add(java.lang.Float.valueOf(mode.getRefreshRate()));
            }
        }
        float[] fArr = new float[arraySet.size()];
        java.util.Iterator it = arraySet.iterator();
        while (it.hasNext()) {
            fArr[i] = ((java.lang.Float) it.next()).floatValue();
            i++;
        }
        return fArr;
    }

    public void getAppMetrics(android.util.DisplayMetrics displayMetrics) {
        getAppMetrics(displayMetrics, android.content.res.CompatibilityInfo.DEFAULT_COMPATIBILITY_INFO, null);
    }

    public void getAppMetrics(android.util.DisplayMetrics displayMetrics, android.view.DisplayAdjustments displayAdjustments) {
        getMetricsWithSize(displayMetrics, displayAdjustments.getCompatibilityInfo(), displayAdjustments.getConfiguration(), this.appWidth, this.appHeight);
    }

    public void getAppMetrics(android.util.DisplayMetrics displayMetrics, android.content.res.CompatibilityInfo compatibilityInfo, android.content.res.Configuration configuration) {
        getMetricsWithSize(displayMetrics, compatibilityInfo, configuration, this.appWidth, this.appHeight);
    }

    public void getLogicalMetrics(android.util.DisplayMetrics displayMetrics, android.content.res.CompatibilityInfo compatibilityInfo, android.content.res.Configuration configuration) {
        getMetricsWithSize(displayMetrics, compatibilityInfo, configuration, this.logicalWidth, this.logicalHeight);
    }

    public void getMaxBoundsMetrics(android.util.DisplayMetrics displayMetrics, android.content.res.CompatibilityInfo compatibilityInfo, android.content.res.Configuration configuration) {
        android.graphics.Rect maxBounds = configuration.windowConfiguration.getMaxBounds();
        getMetricsWithSize(displayMetrics, compatibilityInfo, null, maxBounds.width(), maxBounds.height());
    }

    public int getNaturalWidth() {
        return (this.rotation == 0 || this.rotation == 2) ? this.logicalWidth : this.logicalHeight;
    }

    public int getNaturalHeight() {
        return (this.rotation == 0 || this.rotation == 2) ? this.logicalHeight : this.logicalWidth;
    }

    public boolean isHdr() {
        int[] supportedHdrTypes = this.hdrCapabilities != null ? this.hdrCapabilities.getSupportedHdrTypes() : null;
        return supportedHdrTypes != null && supportedHdrTypes.length > 0;
    }

    public boolean isWideColorGamut() {
        for (int i : this.supportedColorModes) {
            if (i == 6 || i > 7) {
                return true;
            }
        }
        return false;
    }

    public boolean hasAccess(int i) {
        return android.view.Display.hasAccess(i, this.flags, this.ownerUid, this.displayId);
    }

    private void getMetricsWithSize(android.util.DisplayMetrics displayMetrics, android.content.res.CompatibilityInfo compatibilityInfo, android.content.res.Configuration configuration, int i, int i2) {
        int i3 = this.logicalDensityDpi;
        displayMetrics.noncompatDensityDpi = i3;
        displayMetrics.densityDpi = i3;
        float f = this.logicalDensityDpi * 0.00625f;
        displayMetrics.noncompatDensity = f;
        displayMetrics.density = f;
        float f2 = displayMetrics.density;
        displayMetrics.noncompatScaledDensity = f2;
        displayMetrics.scaledDensity = f2;
        float f3 = this.physicalXDpi;
        displayMetrics.noncompatXdpi = f3;
        displayMetrics.xdpi = f3;
        float f4 = this.physicalYDpi;
        displayMetrics.noncompatYdpi = f4;
        displayMetrics.ydpi = f4;
        android.graphics.Rect appBounds = configuration != null ? configuration.windowConfiguration.getAppBounds() : null;
        if (appBounds != null) {
            i = appBounds.width();
        }
        if (appBounds != null) {
            i2 = appBounds.height();
        }
        displayMetrics.widthPixels = i;
        displayMetrics.noncompatWidthPixels = i;
        displayMetrics.heightPixels = i2;
        displayMetrics.noncompatHeightPixels = i2;
        compatibilityInfo.applyDisplayMetricsIfNeeded(displayMetrics, configuration != null && appBounds == null);
    }

    public java.lang.String toString() {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append("DisplayInfo{\"");
        sb.append(this.name);
        sb.append("\", displayId ");
        sb.append(this.displayId);
        sb.append(", displayGroupId ");
        sb.append(this.displayGroupId);
        sb.append(flagsToString(this.flags));
        sb.append(", real ");
        sb.append(this.logicalWidth);
        sb.append(" x ");
        sb.append(this.logicalHeight);
        sb.append(", largest app ");
        sb.append(this.largestNominalAppWidth);
        sb.append(" x ");
        sb.append(this.largestNominalAppHeight);
        sb.append(", smallest app ");
        sb.append(this.smallestNominalAppWidth);
        sb.append(" x ");
        sb.append(this.smallestNominalAppHeight);
        sb.append(", appVsyncOff ");
        sb.append(this.appVsyncOffsetNanos);
        sb.append(", presDeadline ");
        sb.append(this.presentationDeadlineNanos);
        sb.append(", mode ");
        sb.append(this.modeId);
        sb.append(", renderFrameRate ");
        sb.append(this.renderFrameRate);
        sb.append(", defaultMode ");
        sb.append(this.defaultModeId);
        sb.append(", userPreferredModeId ");
        sb.append(this.userPreferredModeId);
        sb.append(", modes ");
        sb.append(java.util.Arrays.toString(this.supportedModes));
        sb.append(", hdrCapabilities ");
        sb.append(this.hdrCapabilities);
        sb.append(", userDisabledHdrTypes ");
        sb.append(java.util.Arrays.toString(this.userDisabledHdrTypes));
        sb.append(", minimalPostProcessingSupported ");
        sb.append(this.minimalPostProcessingSupported);
        sb.append(", rotation ");
        sb.append(this.rotation);
        sb.append(", state ");
        sb.append(android.view.Display.stateToString(this.state));
        sb.append(", committedState ");
        sb.append(android.view.Display.stateToString(this.committedState));
        if (android.os.Process.myUid() != 1000) {
            sb.append("}");
            return sb.toString();
        }
        sb.append(", type ");
        sb.append(android.view.Display.typeToString(this.type));
        sb.append(", uniqueId \"");
        sb.append(this.uniqueId);
        sb.append("\", app ");
        sb.append(this.appWidth);
        sb.append(" x ");
        sb.append(this.appHeight);
        sb.append(", density ");
        sb.append(this.logicalDensityDpi);
        sb.append(" (");
        sb.append(this.physicalXDpi);
        sb.append(" x ");
        sb.append(this.physicalYDpi);
        sb.append(") dpi, layerStack ");
        sb.append(this.layerStack);
        sb.append(", colorMode ");
        sb.append(this.colorMode);
        sb.append(", supportedColorModes ");
        sb.append(java.util.Arrays.toString(this.supportedColorModes));
        if (this.address != null) {
            sb.append(", address ").append(this.address);
        }
        sb.append(", deviceProductInfo ");
        sb.append(this.deviceProductInfo);
        if (this.ownerUid != 0 || this.ownerPackageName != null) {
            sb.append(", owner ").append(this.ownerPackageName);
            sb.append(" (uid ").append(this.ownerUid).append(android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
        }
        sb.append(", removeMode ");
        sb.append(this.removeMode);
        sb.append(", refreshRateOverride ");
        sb.append(this.refreshRateOverride);
        sb.append(", brightnessMinimum ");
        sb.append(this.brightnessMinimum);
        sb.append(", brightnessMaximum ");
        sb.append(this.brightnessMaximum);
        sb.append(", brightnessDefault ");
        sb.append(this.brightnessDefault);
        sb.append(", installOrientation ");
        sb.append(android.view.Surface.rotationToString(this.installOrientation));
        sb.append(", layoutLimitedRefreshRate ");
        sb.append(this.layoutLimitedRefreshRate);
        sb.append(", hdrSdrRatio ");
        if (java.lang.Float.isNaN(this.hdrSdrRatio)) {
            sb.append("not_available");
        } else {
            sb.append(this.hdrSdrRatio);
        }
        sb.append(", thermalRefreshRateThrottling ");
        sb.append(this.thermalRefreshRateThrottling);
        sb.append(", thermalBrightnessThrottlingDataId ");
        sb.append(this.thermalBrightnessThrottlingDataId);
        sb.append("}");
        return sb.toString();
    }

    public void dumpDebug(android.util.proto.ProtoOutputStream protoOutputStream, long j) {
        long start = protoOutputStream.start(j);
        protoOutputStream.write(1120986464257L, this.logicalWidth);
        protoOutputStream.write(1120986464258L, this.logicalHeight);
        protoOutputStream.write(1120986464259L, this.appWidth);
        protoOutputStream.write(1120986464260L, this.appHeight);
        protoOutputStream.write(1138166333445L, this.name);
        protoOutputStream.write(1120986464262L, this.flags);
        if (this.displayCutout != null) {
            this.displayCutout.dumpDebug(protoOutputStream, 1146756268039L);
        }
        protoOutputStream.end(start);
    }

    private static java.lang.String flagsToString(int i) {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        if ((i & 2) != 0) {
            sb.append(", FLAG_SECURE");
        }
        if ((i & 1) != 0) {
            sb.append(", FLAG_SUPPORTS_PROTECTED_BUFFERS");
        }
        if ((i & 4) != 0) {
            sb.append(", FLAG_PRIVATE");
        }
        if ((i & 8) != 0) {
            sb.append(", FLAG_PRESENTATION");
        }
        if ((1073741824 & i) != 0) {
            sb.append(", FLAG_SCALING_DISABLED");
        }
        if ((i & 16) != 0) {
            sb.append(", FLAG_ROUND");
        }
        if ((i & 32) != 0) {
            sb.append(", FLAG_CAN_SHOW_WITH_INSECURE_KEYGUARD");
        }
        if ((i & 64) != 0) {
            sb.append(", FLAG_SHOULD_SHOW_SYSTEM_DECORATIONS");
        }
        if ((i & 128) != 0) {
            sb.append(", FLAG_TRUSTED");
        }
        if ((i & 256) != 0) {
            sb.append(", FLAG_OWN_DISPLAY_GROUP");
        }
        if ((i & 512) != 0) {
            sb.append(", FLAG_ALWAYS_UNLOCKED");
        }
        if ((i & 1024) != 0) {
            sb.append(", FLAG_TOUCH_FEEDBACK_DISABLED");
        }
        if ((i & 8192) != 0) {
            sb.append(", FLAG_REAR_DISPLAY");
        }
        return sb.toString();
    }
}
