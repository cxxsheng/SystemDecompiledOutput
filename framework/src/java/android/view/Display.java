package android.view;

/* loaded from: classes4.dex */
public final class Display {
    private static final int CACHED_APP_SIZE_DURATION_MILLIS = 20;
    public static final int COLOR_MODE_ADOBE_RGB = 8;
    public static final int COLOR_MODE_BT601_525 = 3;
    public static final int COLOR_MODE_BT601_525_UNADJUSTED = 4;
    public static final int COLOR_MODE_BT601_625 = 1;
    public static final int COLOR_MODE_BT601_625_UNADJUSTED = 2;
    public static final int COLOR_MODE_BT709 = 5;
    public static final int COLOR_MODE_DCI_P3 = 6;
    public static final int COLOR_MODE_DEFAULT = 0;
    public static final int COLOR_MODE_DISPLAY_P3 = 9;
    public static final int COLOR_MODE_INVALID = -1;
    public static final int COLOR_MODE_SRGB = 7;
    private static final boolean DEBUG = false;
    public static final int DEFAULT_DISPLAY = 0;
    public static final int DEFAULT_DISPLAY_GROUP = 0;
    public static final int DISPLAY_MODE_ID_FOR_FRAME_RATE_OVERRIDE = 255;
    public static final int FLAG_ALWAYS_UNLOCKED = 512;
    public static final int FLAG_CAN_SHOW_WITH_INSECURE_KEYGUARD = 32;
    public static final int FLAG_OWN_DISPLAY_GROUP = 256;
    public static final int FLAG_OWN_FOCUS = 2048;
    public static final int FLAG_PRESENTATION = 8;
    public static final int FLAG_PRIVATE = 4;
    public static final int FLAG_REAR = 8192;
    public static final int FLAG_ROTATES_WITH_CONTENT = 16384;
    public static final int FLAG_ROUND = 16;
    public static final int FLAG_SCALING_DISABLED = 1073741824;
    public static final int FLAG_SECURE = 2;
    public static final int FLAG_SHOULD_SHOW_SYSTEM_DECORATIONS = 64;
    public static final int FLAG_STEAL_TOP_FOCUS_DISABLED = 4096;
    public static final int FLAG_SUPPORTS_PROTECTED_BUFFERS = 1;
    public static final int FLAG_TOUCH_FEEDBACK_DISABLED = 1024;
    public static final int FLAG_TRUSTED = 128;
    public static final int INVALID_DISPLAY = -1;
    public static final int INVALID_DISPLAY_GROUP = -1;
    public static final int INVALID_DISPLAY_HEIGHT = -1;
    public static final float INVALID_DISPLAY_REFRESH_RATE = 0.0f;
    public static final int INVALID_DISPLAY_WIDTH = -1;
    public static final int REMOVE_MODE_DESTROY_CONTENT = 1;
    public static final int REMOVE_MODE_MOVE_CONTENT_TO_PRIMARY = 0;
    public static final int STATE_DOZE = 3;
    public static final int STATE_DOZE_SUSPEND = 4;
    public static final int STATE_OFF = 1;
    public static final int STATE_ON = 2;
    public static final int STATE_ON_SUSPEND = 6;
    public static final int STATE_UNKNOWN = 0;
    public static final int STATE_VR = 5;
    private static final java.lang.String TAG = "Display";
    public static final int TYPE_EXTERNAL = 2;
    public static final int TYPE_INTERNAL = 1;
    public static final int TYPE_OVERLAY = 4;
    public static final int TYPE_UNKNOWN = 0;
    public static final int TYPE_VIRTUAL = 5;
    public static final int TYPE_WIFI = 3;
    private int mCachedAppHeightCompat;
    private int mCachedAppWidthCompat;
    private android.view.DisplayAdjustments mDisplayAdjustments;
    private final int mDisplayId;
    private android.view.DisplayInfo mDisplayInfo;
    private final int mFlags;
    private final android.hardware.display.DisplayManagerGlobal mGlobal;
    private java.util.ArrayList<android.view.Display.HdrSdrRatioListenerWrapper> mHdrSdrRatioListeners;
    private boolean mIsValid;
    private long mLastCachedAppSizeUpdate;
    private final java.lang.Object mLock;
    private final java.lang.String mOwnerPackageName;
    private final int mOwnerUid;
    private final android.content.res.Resources mResources;
    private final android.util.DisplayMetrics mTempMetrics;
    private final int mType;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface ColorMode {
    }

    public Display(android.hardware.display.DisplayManagerGlobal displayManagerGlobal, int i, android.view.DisplayInfo displayInfo, android.view.DisplayAdjustments displayAdjustments) {
        this(displayManagerGlobal, i, displayInfo, displayAdjustments, null);
    }

    public Display(android.hardware.display.DisplayManagerGlobal displayManagerGlobal, int i, android.view.DisplayInfo displayInfo, android.content.res.Resources resources) {
        this(displayManagerGlobal, i, displayInfo, null, resources);
    }

    private Display(android.hardware.display.DisplayManagerGlobal displayManagerGlobal, int i, android.view.DisplayInfo displayInfo, android.view.DisplayAdjustments displayAdjustments, android.content.res.Resources resources) {
        android.view.DisplayAdjustments displayAdjustments2;
        this.mLock = new java.lang.Object();
        this.mTempMetrics = new android.util.DisplayMetrics();
        this.mHdrSdrRatioListeners = new java.util.ArrayList<>();
        this.mGlobal = displayManagerGlobal;
        this.mDisplayId = i;
        this.mDisplayInfo = displayInfo;
        this.mResources = resources;
        if (this.mResources != null) {
            displayAdjustments2 = new android.view.DisplayAdjustments(this.mResources.getConfiguration());
        } else {
            displayAdjustments2 = displayAdjustments != null ? new android.view.DisplayAdjustments(displayAdjustments) : new android.view.DisplayAdjustments();
        }
        this.mDisplayAdjustments = displayAdjustments2;
        this.mIsValid = true;
        this.mFlags = displayInfo.flags;
        this.mType = displayInfo.type;
        this.mOwnerUid = displayInfo.ownerUid;
        this.mOwnerPackageName = displayInfo.ownerPackageName;
    }

    public int getDisplayId() {
        return this.mDisplayId;
    }

    public java.lang.String getUniqueId() {
        return this.mDisplayInfo.uniqueId;
    }

    public boolean isValid() {
        boolean z;
        synchronized (this.mLock) {
            updateDisplayInfoLocked();
            z = this.mIsValid;
        }
        return z;
    }

    public boolean getDisplayInfo(android.view.DisplayInfo displayInfo) {
        boolean z;
        synchronized (this.mLock) {
            updateDisplayInfoLocked();
            displayInfo.copyFrom(this.mDisplayInfo);
            z = this.mIsValid;
        }
        return z;
    }

    public int getLayerStack() {
        int i;
        synchronized (this.mLock) {
            updateDisplayInfoLocked();
            i = this.mDisplayInfo.layerStack;
        }
        return i;
    }

    public int getFlags() {
        return this.mFlags;
    }

    public int getType() {
        return this.mType;
    }

    public android.view.DisplayAddress getAddress() {
        android.view.DisplayAddress displayAddress;
        synchronized (this.mLock) {
            updateDisplayInfoLocked();
            displayAddress = this.mDisplayInfo.address;
        }
        return displayAddress;
    }

    public int getOwnerUid() {
        return this.mOwnerUid;
    }

    public java.lang.String getOwnerPackageName() {
        return this.mOwnerPackageName;
    }

    public android.view.DisplayAdjustments getDisplayAdjustments() {
        if (this.mResources != null) {
            android.view.DisplayAdjustments displayAdjustments = this.mResources.getDisplayAdjustments();
            if (!this.mDisplayAdjustments.equals(displayAdjustments)) {
                this.mDisplayAdjustments = new android.view.DisplayAdjustments(displayAdjustments);
            }
        }
        return this.mDisplayAdjustments;
    }

    public java.lang.String getName() {
        java.lang.String str;
        synchronized (this.mLock) {
            updateDisplayInfoLocked();
            str = this.mDisplayInfo.name;
        }
        return str;
    }

    public float getBrightnessDefault() {
        float f;
        synchronized (this.mLock) {
            updateDisplayInfoLocked();
            f = this.mDisplayInfo.brightnessDefault;
        }
        return f;
    }

    public android.hardware.display.BrightnessInfo getBrightnessInfo() {
        return this.mGlobal.getBrightnessInfo(this.mDisplayId);
    }

    @java.lang.Deprecated
    public void getSize(android.graphics.Point point) {
        synchronized (this.mLock) {
            updateDisplayInfoLocked();
            this.mDisplayInfo.getAppMetrics(this.mTempMetrics, getDisplayAdjustments());
            point.x = this.mTempMetrics.widthPixels;
            point.y = this.mTempMetrics.heightPixels;
        }
    }

    @java.lang.Deprecated
    public void getRectSize(android.graphics.Rect rect) {
        synchronized (this.mLock) {
            updateDisplayInfoLocked();
            this.mDisplayInfo.getAppMetrics(this.mTempMetrics, getDisplayAdjustments());
            rect.set(0, 0, this.mTempMetrics.widthPixels, this.mTempMetrics.heightPixels);
        }
    }

    public void getCurrentSizeRange(android.graphics.Point point, android.graphics.Point point2) {
        synchronized (this.mLock) {
            updateDisplayInfoLocked();
            point.x = this.mDisplayInfo.smallestNominalAppWidth;
            point.y = this.mDisplayInfo.smallestNominalAppHeight;
            point2.x = this.mDisplayInfo.largestNominalAppWidth;
            point2.y = this.mDisplayInfo.largestNominalAppHeight;
        }
    }

    public int getMaximumSizeDimension() {
        int max;
        synchronized (this.mLock) {
            updateDisplayInfoLocked();
            max = java.lang.Math.max(this.mDisplayInfo.logicalWidth, this.mDisplayInfo.logicalHeight);
        }
        return max;
    }

    @java.lang.Deprecated
    public int getWidth() {
        int i;
        synchronized (this.mLock) {
            updateCachedAppSizeIfNeededLocked();
            i = this.mCachedAppWidthCompat;
        }
        return i;
    }

    @java.lang.Deprecated
    public int getHeight() {
        int i;
        synchronized (this.mLock) {
            updateCachedAppSizeIfNeededLocked();
            i = this.mCachedAppHeightCompat;
        }
        return i;
    }

    public int getRotation() {
        int localRotation;
        synchronized (this.mLock) {
            updateDisplayInfoLocked();
            localRotation = getLocalRotation();
        }
        return localRotation;
    }

    public int getInstallOrientation() {
        int i;
        synchronized (this.mLock) {
            updateDisplayInfoLocked();
            i = this.mDisplayInfo.installOrientation;
        }
        return i;
    }

    @java.lang.Deprecated
    public int getOrientation() {
        return getRotation();
    }

    public android.view.DisplayCutout getCutout() {
        synchronized (this.mLock) {
            updateDisplayInfoLocked();
            if (this.mResources == null) {
                return this.mDisplayInfo.displayCutout;
            }
            android.view.DisplayCutout displayCutout = this.mDisplayInfo.displayCutout;
            if (displayCutout == null) {
                return null;
            }
            int localRotation = getLocalRotation();
            if (localRotation == this.mDisplayInfo.rotation) {
                return displayCutout;
            }
            return displayCutout.getRotated(this.mDisplayInfo.logicalWidth, this.mDisplayInfo.logicalHeight, this.mDisplayInfo.rotation, localRotation);
        }
    }

    public android.view.RoundedCorner getRoundedCorner(int i) {
        android.view.RoundedCorner roundedCorner;
        synchronized (this.mLock) {
            updateDisplayInfoLocked();
            android.view.RoundedCorners roundedCorners = this.mDisplayInfo.roundedCorners;
            int localRotation = getLocalRotation();
            if (roundedCorners != null && localRotation != this.mDisplayInfo.rotation) {
                roundedCorners.rotate(localRotation, this.mDisplayInfo.logicalWidth, this.mDisplayInfo.logicalHeight);
            }
            roundedCorner = roundedCorners == null ? null : roundedCorners.getRoundedCorner(i);
        }
        return roundedCorner;
    }

    public android.view.DisplayShape getShape() {
        synchronized (this.mLock) {
            updateDisplayInfoLocked();
            android.view.DisplayShape displayShape = this.mDisplayInfo.displayShape;
            int localRotation = getLocalRotation();
            if (displayShape == null || localRotation == this.mDisplayInfo.rotation) {
                return displayShape;
            }
            return displayShape.setRotation(localRotation);
        }
    }

    @java.lang.Deprecated
    public int getPixelFormat() {
        return 1;
    }

    public float getRefreshRate() {
        float refreshRate;
        synchronized (this.mLock) {
            updateDisplayInfoLocked();
            refreshRate = this.mDisplayInfo.getRefreshRate();
        }
        return refreshRate;
    }

    @java.lang.Deprecated
    public float[] getSupportedRefreshRates() {
        float[] defaultRefreshRates;
        synchronized (this.mLock) {
            updateDisplayInfoLocked();
            defaultRefreshRates = this.mDisplayInfo.getDefaultRefreshRates();
        }
        return defaultRefreshRates;
    }

    public android.view.Display.Mode getMode() {
        android.view.Display.Mode mode;
        synchronized (this.mLock) {
            updateDisplayInfoLocked();
            mode = this.mDisplayInfo.getMode();
        }
        return mode;
    }

    public android.view.Display.Mode getDefaultMode() {
        android.view.Display.Mode defaultMode;
        synchronized (this.mLock) {
            updateDisplayInfoLocked();
            defaultMode = this.mDisplayInfo.getDefaultMode();
        }
        return defaultMode;
    }

    public android.view.Display.Mode[] getSupportedModes() {
        android.view.Display.Mode[] modeArr;
        synchronized (this.mLock) {
            updateDisplayInfoLocked();
            android.view.Display.Mode[] modeArr2 = this.mDisplayInfo.supportedModes;
            modeArr = (android.view.Display.Mode[]) java.util.Arrays.copyOf(modeArr2, modeArr2.length);
        }
        return modeArr;
    }

    public boolean isMinimalPostProcessingSupported() {
        boolean z;
        synchronized (this.mLock) {
            updateDisplayInfoLocked();
            z = this.mDisplayInfo.minimalPostProcessingSupported;
        }
        return z;
    }

    public void requestColorMode(int i) {
        this.mGlobal.requestColorMode(this.mDisplayId, i);
    }

    public int getColorMode() {
        int i;
        synchronized (this.mLock) {
            updateDisplayInfoLocked();
            i = this.mDisplayInfo.colorMode;
        }
        return i;
    }

    public int getRemoveMode() {
        return this.mDisplayInfo.removeMode;
    }

    public android.view.Display.Mode getSystemPreferredDisplayMode() {
        return this.mGlobal.getSystemPreferredDisplayMode(getDisplayId());
    }

    public android.view.Display.HdrCapabilities getHdrCapabilities() {
        int[] iArr;
        synchronized (this.mLock) {
            updateDisplayInfoLocked();
            if (this.mDisplayInfo.hdrCapabilities == null) {
                return null;
            }
            if (this.mDisplayInfo.userDisabledHdrTypes.length == 0) {
                int[] supportedHdrTypes = getMode().getSupportedHdrTypes();
                iArr = java.util.Arrays.copyOf(supportedHdrTypes, supportedHdrTypes.length);
            } else {
                android.util.ArraySet arraySet = new android.util.ArraySet();
                int i = 0;
                for (int i2 : getMode().getSupportedHdrTypes()) {
                    if (!contains(this.mDisplayInfo.userDisabledHdrTypes, i2)) {
                        arraySet.add(java.lang.Integer.valueOf(i2));
                    }
                }
                int[] iArr2 = new int[arraySet.size()];
                java.util.Iterator it = arraySet.iterator();
                while (it.hasNext()) {
                    iArr2[i] = ((java.lang.Integer) it.next()).intValue();
                    i++;
                }
                iArr = iArr2;
            }
            return new android.view.Display.HdrCapabilities(iArr, this.mDisplayInfo.hdrCapabilities.mMaxLuminance, this.mDisplayInfo.hdrCapabilities.mMaxAverageLuminance, this.mDisplayInfo.hdrCapabilities.mMinLuminance);
        }
    }

    private boolean contains(int[] iArr, int i) {
        for (int i2 : iArr) {
            if (java.lang.Integer.valueOf(i2).intValue() == i) {
                return true;
            }
        }
        return false;
    }

    public int[] getReportedHdrTypes() {
        int[] supportedHdrTypes;
        synchronized (this.mLock) {
            updateDisplayInfoLocked();
            supportedHdrTypes = this.mDisplayInfo.getMode().getSupportedHdrTypes();
        }
        return supportedHdrTypes;
    }

    public boolean isHdr() {
        synchronized (this.mLock) {
            updateDisplayInfoLocked();
            android.view.Display.HdrCapabilities hdrCapabilities = getHdrCapabilities();
            if (hdrCapabilities == null) {
                return false;
            }
            return hdrCapabilities.getSupportedHdrTypes().length != 0;
        }
    }

    public boolean isHdrSdrRatioAvailable() {
        boolean z;
        synchronized (this.mLock) {
            updateDisplayInfoLocked();
            z = !java.lang.Float.isNaN(this.mDisplayInfo.hdrSdrRatio);
        }
        return z;
    }

    public float getHdrSdrRatio() {
        float f;
        synchronized (this.mLock) {
            updateDisplayInfoLocked();
            f = java.lang.Float.isNaN(this.mDisplayInfo.hdrSdrRatio) ? 1.0f : this.mDisplayInfo.hdrSdrRatio;
        }
        return f;
    }

    private int findHdrSdrRatioListenerLocked(java.util.function.Consumer<android.view.Display> consumer) {
        for (int i = 0; i < this.mHdrSdrRatioListeners.size(); i++) {
            if (this.mHdrSdrRatioListeners.get(i).mListener == consumer) {
                return i;
            }
        }
        return -1;
    }

    public void registerHdrSdrRatioChangedListener(java.util.concurrent.Executor executor, java.util.function.Consumer<android.view.Display> consumer) {
        android.view.Display.HdrSdrRatioListenerWrapper hdrSdrRatioListenerWrapper;
        if (!isHdrSdrRatioAvailable()) {
            throw new java.lang.IllegalStateException("HDR/SDR ratio changed not available");
        }
        synchronized (this.mLock) {
            if (findHdrSdrRatioListenerLocked(consumer) != -1) {
                hdrSdrRatioListenerWrapper = null;
            } else {
                android.view.Display.HdrSdrRatioListenerWrapper hdrSdrRatioListenerWrapper2 = new android.view.Display.HdrSdrRatioListenerWrapper(consumer);
                this.mHdrSdrRatioListeners.add(hdrSdrRatioListenerWrapper2);
                hdrSdrRatioListenerWrapper = hdrSdrRatioListenerWrapper2;
            }
        }
        if (hdrSdrRatioListenerWrapper != null) {
            this.mGlobal.registerDisplayListener(hdrSdrRatioListenerWrapper, executor, 18L, android.app.ActivityThread.currentPackageName());
        }
    }

    public void unregisterHdrSdrRatioChangedListener(java.util.function.Consumer<android.view.Display> consumer) {
        android.view.Display.HdrSdrRatioListenerWrapper hdrSdrRatioListenerWrapper;
        synchronized (this.mLock) {
            int findHdrSdrRatioListenerLocked = findHdrSdrRatioListenerLocked(consumer);
            if (findHdrSdrRatioListenerLocked == -1) {
                hdrSdrRatioListenerWrapper = null;
            } else {
                hdrSdrRatioListenerWrapper = this.mHdrSdrRatioListeners.remove(findHdrSdrRatioListenerLocked);
            }
        }
        if (hdrSdrRatioListenerWrapper != null) {
            this.mGlobal.unregisterDisplayListener(hdrSdrRatioListenerWrapper);
        }
    }

    public void setUserPreferredDisplayMode(android.view.Display.Mode mode) {
        this.mGlobal.setUserPreferredDisplayMode(this.mDisplayId, new android.view.Display.Mode(mode.getPhysicalWidth(), mode.getPhysicalHeight(), mode.getRefreshRate()));
    }

    public void clearUserPreferredDisplayMode() {
        this.mGlobal.setUserPreferredDisplayMode(this.mDisplayId, null);
    }

    public android.view.Display.Mode getUserPreferredDisplayMode() {
        return this.mGlobal.getUserPreferredDisplayMode(this.mDisplayId);
    }

    public boolean isWideColorGamut() {
        boolean isWideColorGamut;
        synchronized (this.mLock) {
            updateDisplayInfoLocked();
            isWideColorGamut = this.mDisplayInfo.isWideColorGamut();
        }
        return isWideColorGamut;
    }

    public android.graphics.ColorSpace getPreferredWideGamutColorSpace() {
        synchronized (this.mLock) {
            updateDisplayInfoLocked();
            if (!this.mDisplayInfo.isWideColorGamut()) {
                return null;
            }
            return this.mGlobal.getPreferredWideGamutColorSpace();
        }
    }

    public android.hardware.OverlayProperties getOverlaySupport() {
        synchronized (this.mLock) {
            updateDisplayInfoLocked();
            if (this.mDisplayInfo.type != 1 && this.mDisplayInfo.type != 2) {
                return android.hardware.OverlayProperties.getDefault();
            }
            return this.mGlobal.getOverlaySupport();
        }
    }

    public int[] getSupportedColorModes() {
        int[] copyOf;
        synchronized (this.mLock) {
            updateDisplayInfoLocked();
            int[] iArr = this.mDisplayInfo.supportedColorModes;
            copyOf = java.util.Arrays.copyOf(iArr, iArr.length);
        }
        return copyOf;
    }

    public android.graphics.ColorSpace[] getSupportedWideColorGamut() {
        synchronized (this.mLock) {
            android.graphics.ColorSpace[] colorSpaceArr = new android.graphics.ColorSpace[0];
            updateDisplayInfoLocked();
            if (!isWideColorGamut()) {
                return colorSpaceArr;
            }
            int[] supportedColorModes = getSupportedColorModes();
            java.util.ArrayList arrayList = new java.util.ArrayList();
            for (int i : supportedColorModes) {
                switch (i) {
                    case 6:
                        arrayList.add(android.graphics.ColorSpace.get(android.graphics.ColorSpace.Named.DCI_P3));
                        break;
                    case 9:
                        arrayList.add(android.graphics.ColorSpace.get(android.graphics.ColorSpace.Named.DISPLAY_P3));
                        break;
                }
            }
            return (android.graphics.ColorSpace[]) arrayList.toArray(colorSpaceArr);
        }
    }

    public long getAppVsyncOffsetNanos() {
        long j;
        synchronized (this.mLock) {
            updateDisplayInfoLocked();
            j = this.mDisplayInfo.appVsyncOffsetNanos;
        }
        return j;
    }

    public long getPresentationDeadlineNanos() {
        long j;
        synchronized (this.mLock) {
            updateDisplayInfoLocked();
            j = this.mDisplayInfo.presentationDeadlineNanos;
        }
        return j;
    }

    public android.hardware.display.DeviceProductInfo getDeviceProductInfo() {
        android.hardware.display.DeviceProductInfo deviceProductInfo;
        synchronized (this.mLock) {
            updateDisplayInfoLocked();
            deviceProductInfo = this.mDisplayInfo.deviceProductInfo;
        }
        return deviceProductInfo;
    }

    @java.lang.Deprecated
    public void getMetrics(android.util.DisplayMetrics displayMetrics) {
        synchronized (this.mLock) {
            updateDisplayInfoLocked();
            this.mDisplayInfo.getAppMetrics(displayMetrics, getDisplayAdjustments());
        }
    }

    @java.lang.Deprecated
    public void getRealSize(android.graphics.Point point) {
        synchronized (this.mLock) {
            updateDisplayInfoLocked();
            if (shouldReportMaxBounds()) {
                android.graphics.Rect maxBounds = this.mResources.getConfiguration().windowConfiguration.getMaxBounds();
                point.x = maxBounds.width();
                point.y = maxBounds.height();
            } else {
                point.x = this.mDisplayInfo.logicalWidth;
                point.y = this.mDisplayInfo.logicalHeight;
                int localRotation = getLocalRotation();
                if (localRotation != this.mDisplayInfo.rotation) {
                    adjustSize(point, this.mDisplayInfo.rotation, localRotation);
                }
            }
        }
    }

    @java.lang.Deprecated
    public void getRealMetrics(android.util.DisplayMetrics displayMetrics) {
        synchronized (this.mLock) {
            updateDisplayInfoLocked();
            if (shouldReportMaxBounds()) {
                this.mDisplayInfo.getMaxBoundsMetrics(displayMetrics, android.content.res.CompatibilityInfo.DEFAULT_COMPATIBILITY_INFO, this.mResources.getConfiguration());
                return;
            }
            this.mDisplayInfo.getLogicalMetrics(displayMetrics, android.content.res.CompatibilityInfo.DEFAULT_COMPATIBILITY_INFO, null);
            int localRotation = getLocalRotation();
            if (localRotation != this.mDisplayInfo.rotation) {
                adjustMetrics(displayMetrics, this.mDisplayInfo.rotation, localRotation);
            }
        }
    }

    private boolean shouldReportMaxBounds() {
        android.content.res.Configuration configuration;
        return (this.mResources == null || (configuration = this.mResources.getConfiguration()) == null || configuration.windowConfiguration.getMaxBounds().isEmpty()) ? false : true;
    }

    public int getState() {
        int i;
        synchronized (this.mLock) {
            updateDisplayInfoLocked();
            i = this.mIsValid ? this.mDisplayInfo.state : 0;
        }
        return i;
    }

    public int getCommittedState() {
        int i;
        synchronized (this.mLock) {
            updateDisplayInfoLocked();
            i = this.mIsValid ? this.mDisplayInfo.committedState : 0;
        }
        return i;
    }

    public boolean hasAccess(int i) {
        return hasAccess(i, this.mFlags, this.mOwnerUid, this.mDisplayId);
    }

    public static boolean hasAccess(int i, int i2, int i3, int i4) {
        return (i2 & 4) == 0 || i == i3 || i == 1000 || i == 0 || android.hardware.display.DisplayManagerGlobal.getInstance().isUidPresentOnDisplay(i, i4);
    }

    public boolean isPublicPresentation() {
        return (this.mFlags & 12) == 8;
    }

    public boolean isTrusted() {
        return (this.mFlags & 128) == 128;
    }

    public boolean canStealTopFocus() {
        return (this.mFlags & 4096) == 0;
    }

    private void updateDisplayInfoLocked() {
        android.view.DisplayInfo displayInfo = this.mGlobal.getDisplayInfo(this.mDisplayId);
        if (displayInfo == null) {
            if (this.mIsValid) {
                this.mIsValid = false;
            }
        } else {
            this.mDisplayInfo = displayInfo;
            if (!this.mIsValid) {
                this.mIsValid = true;
            }
        }
    }

    private void updateCachedAppSizeIfNeededLocked() {
        long uptimeMillis = android.os.SystemClock.uptimeMillis();
        if (uptimeMillis > this.mLastCachedAppSizeUpdate + 20) {
            updateDisplayInfoLocked();
            this.mDisplayInfo.getAppMetrics(this.mTempMetrics, getDisplayAdjustments());
            this.mCachedAppWidthCompat = this.mTempMetrics.widthPixels;
            this.mCachedAppHeightCompat = this.mTempMetrics.heightPixels;
            this.mLastCachedAppSizeUpdate = uptimeMillis;
        }
    }

    private static boolean noFlip(int i, int i2) {
        return ((i - i2) + 4) % 2 == 0;
    }

    private void adjustSize(android.graphics.Point point, int i, int i2) {
        if (noFlip(i, i2)) {
            return;
        }
        int i3 = point.x;
        point.x = point.y;
        point.y = i3;
    }

    private void adjustMetrics(android.util.DisplayMetrics displayMetrics, int i, int i2) {
        if (noFlip(i, i2)) {
            return;
        }
        int i3 = displayMetrics.widthPixels;
        displayMetrics.widthPixels = displayMetrics.heightPixels;
        displayMetrics.heightPixels = i3;
        int i4 = displayMetrics.noncompatWidthPixels;
        displayMetrics.noncompatWidthPixels = displayMetrics.noncompatHeightPixels;
        displayMetrics.noncompatHeightPixels = i4;
    }

    private int getLocalRotation() {
        if (this.mResources == null) {
            return this.mDisplayInfo.rotation;
        }
        int displayRotation = this.mResources.getConfiguration().windowConfiguration.getDisplayRotation();
        return displayRotation != -1 ? displayRotation : this.mDisplayInfo.rotation;
    }

    public java.lang.String toString() {
        java.lang.String str;
        synchronized (this.mLock) {
            updateDisplayInfoLocked();
            this.mDisplayInfo.getAppMetrics(this.mTempMetrics, getDisplayAdjustments());
            str = "Display id " + this.mDisplayId + ": " + this.mDisplayInfo + ", " + this.mTempMetrics + ", isValid=" + this.mIsValid;
        }
        return str;
    }

    public static java.lang.String typeToString(int i) {
        switch (i) {
            case 0:
                return "UNKNOWN";
            case 1:
                return "INTERNAL";
            case 2:
                return "EXTERNAL";
            case 3:
                return "WIFI";
            case 4:
                return "OVERLAY";
            case 5:
                return "VIRTUAL";
            default:
                return java.lang.Integer.toString(i);
        }
    }

    public static java.lang.String stateToString(int i) {
        switch (i) {
            case 0:
                return "UNKNOWN";
            case 1:
                return "OFF";
            case 2:
                return "ON";
            case 3:
                return "DOZE";
            case 4:
                return "DOZE_SUSPEND";
            case 5:
                return "VR";
            case 6:
                return "ON_SUSPEND";
            default:
                return java.lang.Integer.toString(i);
        }
    }

    public static boolean isSuspendedState(int i) {
        return i == 1 || i == 4 || i == 6;
    }

    public static boolean isDozeState(int i) {
        return i == 3 || i == 4;
    }

    public static boolean isActiveState(int i) {
        return i == 2 || i == 5;
    }

    public static boolean isOffState(int i) {
        return i == 1;
    }

    public static boolean isOnState(int i) {
        return i == 2 || i == 5 || i == 6;
    }

    public static boolean isWidthValid(int i) {
        return i > 0;
    }

    public static boolean isHeightValid(int i) {
        return i > 0;
    }

    public static boolean isRefreshRateValid(float f) {
        return f > 0.0f;
    }

    public android.hardware.graphics.common.DisplayDecorationSupport getDisplayDecorationSupport() {
        return this.mGlobal.getDisplayDecorationSupport(this.mDisplayId);
    }

    public static final class Mode implements android.os.Parcelable {
        public static final int INVALID_MODE_ID = -1;
        private final float[] mAlternativeRefreshRates;
        private final int mHeight;
        private final int mModeId;
        private final float mPeakRefreshRate;
        private final int[] mSupportedHdrTypes;
        private final float mVsyncRate;
        private final int mWidth;
        public static final android.view.Display.Mode[] EMPTY_ARRAY = new android.view.Display.Mode[0];
        public static final android.os.Parcelable.Creator<android.view.Display.Mode> CREATOR = new android.os.Parcelable.Creator<android.view.Display.Mode>() { // from class: android.view.Display.Mode.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.view.Display.Mode createFromParcel(android.os.Parcel parcel) {
                return new android.view.Display.Mode(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.view.Display.Mode[] newArray(int i) {
                return new android.view.Display.Mode[i];
            }
        };

        public Mode(int i, int i2, float f) {
            this(-1, i, i2, f, f, new float[0], new int[0]);
        }

        public Mode(int i, int i2, float f, float f2) {
            this(-1, i, i2, f, f2, new float[0], new int[0]);
        }

        public Mode(int i, int i2, int i3, float f) {
            this(i, i2, i3, f, f, new float[0], new int[0]);
        }

        public Mode(int i, int i2, int i3, float f, float[] fArr, int[] iArr) {
            this(i, i2, i3, f, f, fArr, iArr);
        }

        public Mode(int i, int i2, int i3, float f, float f2, float[] fArr, int[] iArr) {
            this.mModeId = i;
            this.mWidth = i2;
            this.mHeight = i3;
            this.mPeakRefreshRate = f;
            this.mVsyncRate = f2;
            this.mAlternativeRefreshRates = java.util.Arrays.copyOf(fArr, fArr.length);
            java.util.Arrays.sort(this.mAlternativeRefreshRates);
            this.mSupportedHdrTypes = java.util.Arrays.copyOf(iArr, iArr.length);
            java.util.Arrays.sort(this.mSupportedHdrTypes);
        }

        public int getModeId() {
            return this.mModeId;
        }

        public int getPhysicalWidth() {
            return this.mWidth;
        }

        public int getPhysicalHeight() {
            return this.mHeight;
        }

        public float getRefreshRate() {
            return this.mPeakRefreshRate;
        }

        public float getVsyncRate() {
            return this.mVsyncRate;
        }

        public float[] getAlternativeRefreshRates() {
            return java.util.Arrays.copyOf(this.mAlternativeRefreshRates, this.mAlternativeRefreshRates.length);
        }

        public int[] getSupportedHdrTypes() {
            return java.util.Arrays.copyOf(this.mSupportedHdrTypes, this.mSupportedHdrTypes.length);
        }

        public boolean matches(int i, int i2, float f) {
            return this.mWidth == i && this.mHeight == i2 && java.lang.Float.floatToIntBits(this.mPeakRefreshRate) == java.lang.Float.floatToIntBits(f);
        }

        public boolean matchesIfValid(int i, int i2, float f) {
            if ((!android.view.Display.isWidthValid(i) && !android.view.Display.isHeightValid(i2) && !android.view.Display.isRefreshRateValid(f)) || android.view.Display.isWidthValid(i) != android.view.Display.isHeightValid(i2)) {
                return false;
            }
            if (android.view.Display.isWidthValid(i) && this.mWidth != i) {
                return false;
            }
            if (!android.view.Display.isHeightValid(i2) || this.mHeight == i2) {
                return !android.view.Display.isRefreshRateValid(f) || java.lang.Float.floatToIntBits(this.mPeakRefreshRate) == java.lang.Float.floatToIntBits(f);
            }
            return false;
        }

        public boolean equalsExceptRefreshRate(android.view.Display.Mode mode) {
            return this.mWidth == mode.mWidth && this.mHeight == mode.mHeight;
        }

        public boolean isRefreshRateSet() {
            return this.mPeakRefreshRate != 0.0f;
        }

        public boolean isResolutionSet() {
            return (this.mWidth == -1 || this.mHeight == -1) ? false : true;
        }

        public boolean equals(java.lang.Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof android.view.Display.Mode)) {
                return false;
            }
            android.view.Display.Mode mode = (android.view.Display.Mode) obj;
            return this.mModeId == mode.mModeId && matches(mode.mWidth, mode.mHeight, mode.mPeakRefreshRate) && java.util.Arrays.equals(this.mAlternativeRefreshRates, mode.mAlternativeRefreshRates) && java.util.Arrays.equals(this.mSupportedHdrTypes, mode.mSupportedHdrTypes);
        }

        public int hashCode() {
            return ((((((((((((this.mModeId + 17) * 17) + this.mWidth) * 17) + this.mHeight) * 17) + java.lang.Float.floatToIntBits(this.mPeakRefreshRate)) * 17) + java.lang.Float.floatToIntBits(this.mVsyncRate)) * 17) + java.util.Arrays.hashCode(this.mAlternativeRefreshRates)) * 17) + java.util.Arrays.hashCode(this.mSupportedHdrTypes);
        }

        public java.lang.String toString() {
            return "{id=" + this.mModeId + ", width=" + this.mWidth + ", height=" + this.mHeight + ", fps=" + this.mPeakRefreshRate + ", vsync=" + this.mVsyncRate + ", alternativeRefreshRates=" + java.util.Arrays.toString(this.mAlternativeRefreshRates) + ", supportedHdrTypes=" + java.util.Arrays.toString(this.mSupportedHdrTypes) + "}";
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        private Mode(android.os.Parcel parcel) {
            this(parcel.readInt(), parcel.readInt(), parcel.readInt(), parcel.readFloat(), parcel.readFloat(), parcel.createFloatArray(), parcel.createIntArray());
        }

        @Override // android.os.Parcelable
        public void writeToParcel(android.os.Parcel parcel, int i) {
            parcel.writeInt(this.mModeId);
            parcel.writeInt(this.mWidth);
            parcel.writeInt(this.mHeight);
            parcel.writeFloat(this.mPeakRefreshRate);
            parcel.writeFloat(this.mVsyncRate);
            parcel.writeFloatArray(this.mAlternativeRefreshRates);
            parcel.writeIntArray(this.mSupportedHdrTypes);
        }

        public static final class Builder {
            private int mWidth = -1;
            private int mHeight = -1;
            private float mRefreshRate = 0.0f;

            public android.view.Display.Mode.Builder setResolution(int i, int i2) {
                if (i > 0 && i2 > 0) {
                    this.mWidth = i;
                    this.mHeight = i2;
                }
                return this;
            }

            public android.view.Display.Mode.Builder setRefreshRate(float f) {
                if (f > 0.0f) {
                    this.mRefreshRate = f;
                }
                return this;
            }

            public android.view.Display.Mode build() {
                return new android.view.Display.Mode(this.mWidth, this.mHeight, this.mRefreshRate);
            }
        }
    }

    public static final class HdrCapabilities implements android.os.Parcelable {
        public static final int HDR_TYPE_DOLBY_VISION = 1;
        public static final int HDR_TYPE_HDR10 = 2;
        public static final int HDR_TYPE_HDR10_PLUS = 4;
        public static final int HDR_TYPE_HLG = 3;
        public static final int HDR_TYPE_INVALID = -1;
        public static final float INVALID_LUMINANCE = -1.0f;
        private float mMaxAverageLuminance;
        private float mMaxLuminance;
        private float mMinLuminance;
        private int[] mSupportedHdrTypes;
        public static final int[] HDR_TYPES = {1, 2, 3, 4};
        public static final android.os.Parcelable.Creator<android.view.Display.HdrCapabilities> CREATOR = new android.os.Parcelable.Creator<android.view.Display.HdrCapabilities>() { // from class: android.view.Display.HdrCapabilities.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.view.Display.HdrCapabilities createFromParcel(android.os.Parcel parcel) {
                return new android.view.Display.HdrCapabilities(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.view.Display.HdrCapabilities[] newArray(int i) {
                return new android.view.Display.HdrCapabilities[i];
            }
        };

        @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
        public @interface HdrType {
        }

        public HdrCapabilities() {
            this.mSupportedHdrTypes = new int[0];
            this.mMaxLuminance = -1.0f;
            this.mMaxAverageLuminance = -1.0f;
            this.mMinLuminance = -1.0f;
        }

        public HdrCapabilities(int[] iArr, float f, float f2, float f3) {
            this.mSupportedHdrTypes = new int[0];
            this.mMaxLuminance = -1.0f;
            this.mMaxAverageLuminance = -1.0f;
            this.mMinLuminance = -1.0f;
            this.mSupportedHdrTypes = iArr;
            java.util.Arrays.sort(this.mSupportedHdrTypes);
            this.mMaxLuminance = f;
            this.mMaxAverageLuminance = f2;
            this.mMinLuminance = f3;
        }

        @java.lang.Deprecated
        public int[] getSupportedHdrTypes() {
            return java.util.Arrays.copyOf(this.mSupportedHdrTypes, this.mSupportedHdrTypes.length);
        }

        public float getDesiredMaxLuminance() {
            return this.mMaxLuminance;
        }

        public float getDesiredMaxAverageLuminance() {
            return this.mMaxAverageLuminance;
        }

        public float getDesiredMinLuminance() {
            return this.mMinLuminance;
        }

        public boolean equals(java.lang.Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof android.view.Display.HdrCapabilities)) {
                return false;
            }
            android.view.Display.HdrCapabilities hdrCapabilities = (android.view.Display.HdrCapabilities) obj;
            return java.util.Arrays.equals(this.mSupportedHdrTypes, hdrCapabilities.mSupportedHdrTypes) && this.mMaxLuminance == hdrCapabilities.mMaxLuminance && this.mMaxAverageLuminance == hdrCapabilities.mMaxAverageLuminance && this.mMinLuminance == hdrCapabilities.mMinLuminance;
        }

        public int hashCode() {
            return ((((((391 + java.util.Arrays.hashCode(this.mSupportedHdrTypes)) * 17) + java.lang.Float.floatToIntBits(this.mMaxLuminance)) * 17) + java.lang.Float.floatToIntBits(this.mMaxAverageLuminance)) * 17) + java.lang.Float.floatToIntBits(this.mMinLuminance);
        }

        private HdrCapabilities(android.os.Parcel parcel) {
            this.mSupportedHdrTypes = new int[0];
            this.mMaxLuminance = -1.0f;
            this.mMaxAverageLuminance = -1.0f;
            this.mMinLuminance = -1.0f;
            readFromParcel(parcel);
        }

        public void readFromParcel(android.os.Parcel parcel) {
            int readInt = parcel.readInt();
            this.mSupportedHdrTypes = new int[readInt];
            for (int i = 0; i < readInt; i++) {
                this.mSupportedHdrTypes[i] = parcel.readInt();
            }
            this.mMaxLuminance = parcel.readFloat();
            this.mMaxAverageLuminance = parcel.readFloat();
            this.mMinLuminance = parcel.readFloat();
        }

        @Override // android.os.Parcelable
        public void writeToParcel(android.os.Parcel parcel, int i) {
            parcel.writeInt(this.mSupportedHdrTypes.length);
            for (int i2 = 0; i2 < this.mSupportedHdrTypes.length; i2++) {
                parcel.writeInt(this.mSupportedHdrTypes[i2]);
            }
            parcel.writeFloat(this.mMaxLuminance);
            parcel.writeFloat(this.mMaxAverageLuminance);
            parcel.writeFloat(this.mMinLuminance);
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        public java.lang.String toString() {
            return "HdrCapabilities{mSupportedHdrTypes=" + java.util.Arrays.toString(this.mSupportedHdrTypes) + ", mMaxLuminance=" + this.mMaxLuminance + ", mMaxAverageLuminance=" + this.mMaxAverageLuminance + ", mMinLuminance=" + this.mMinLuminance + '}';
        }

        public static java.lang.String hdrTypeToString(int i) {
            switch (i) {
                case 1:
                    return "HDR_TYPE_DOLBY_VISION";
                case 2:
                    return "HDR_TYPE_HDR10";
                case 3:
                    return "HDR_TYPE_HLG";
                case 4:
                    return "HDR_TYPE_HDR10_PLUS";
                default:
                    return "HDR_TYPE_INVALID";
            }
        }
    }

    private class HdrSdrRatioListenerWrapper implements android.hardware.display.DisplayManager.DisplayListener {
        float mLastReportedRatio;
        java.util.function.Consumer<android.view.Display> mListener;

        private HdrSdrRatioListenerWrapper(java.util.function.Consumer<android.view.Display> consumer) {
            this.mLastReportedRatio = 1.0f;
            this.mListener = consumer;
        }

        @Override // android.hardware.display.DisplayManager.DisplayListener
        public void onDisplayAdded(int i) {
        }

        @Override // android.hardware.display.DisplayManager.DisplayListener
        public void onDisplayRemoved(int i) {
        }

        @Override // android.hardware.display.DisplayManager.DisplayListener
        public void onDisplayChanged(int i) {
            if (i == android.view.Display.this.getDisplayId()) {
                float hdrSdrRatio = android.view.Display.this.getHdrSdrRatio();
                if (hdrSdrRatio != this.mLastReportedRatio) {
                    this.mLastReportedRatio = hdrSdrRatio;
                    this.mListener.accept(android.view.Display.this);
                }
            }
        }
    }
}
