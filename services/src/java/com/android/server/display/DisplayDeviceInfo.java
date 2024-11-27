package com.android.server.display;

/* loaded from: classes.dex */
final class DisplayDeviceInfo {
    public static final int DIFF_COLOR_MODE = 4;
    public static final int DIFF_EVERYTHING = -1;
    public static final int DIFF_HDR_SDR_RATIO = 8;
    public static final int DIFF_OTHER = 2;
    public static final int DIFF_STATE = 1;
    public static final int FLAG_ALLOWED_TO_BE_DEFAULT_DISPLAY = 1;
    public static final int FLAG_ALWAYS_UNLOCKED = 32768;
    public static final int FLAG_CAN_SHOW_WITH_INSECURE_KEYGUARD = 512;
    public static final int FLAG_DESTROY_CONTENT_ON_REMOVAL = 1024;
    public static final int FLAG_DEVICE_DISPLAY_GROUP = 262144;
    public static final int FLAG_MASK_DISPLAY_CUTOUT = 2048;
    public static final int FLAG_NEVER_BLANK = 32;
    public static final int FLAG_OWN_CONTENT_ONLY = 128;
    public static final int FLAG_OWN_DISPLAY_GROUP = 16384;
    public static final int FLAG_OWN_FOCUS = 131072;
    public static final int FLAG_PRESENTATION = 64;
    public static final int FLAG_PRIVATE = 16;
    public static final int FLAG_ROTATES_WITH_CONTENT = 2;
    public static final int FLAG_ROUND = 256;
    public static final int FLAG_SECURE = 4;
    public static final int FLAG_SHOULD_SHOW_SYSTEM_DECORATIONS = 4096;
    public static final int FLAG_STEAL_TOP_FOCUS_DISABLED = 524288;
    public static final int FLAG_SUPPORTS_PROTECTED_BUFFERS = 8;
    public static final int FLAG_TOUCH_FEEDBACK_DISABLED = 65536;
    public static final int FLAG_TRUSTED = 8192;
    public static final int TOUCH_EXTERNAL = 2;
    public static final int TOUCH_INTERNAL = 1;
    public static final int TOUCH_NONE = 0;
    public static final int TOUCH_VIRTUAL = 3;
    public android.view.DisplayAddress address;
    public boolean allmSupported;
    public long appVsyncOffsetNanos;
    public float brightnessDefault;
    public float brightnessMaximum;
    public float brightnessMinimum;
    public int colorMode;
    public int defaultModeId;
    public int densityDpi;
    public android.hardware.display.DeviceProductInfo deviceProductInfo;
    public android.view.DisplayCutout displayCutout;
    public android.view.DisplayShape displayShape;
    public int flags;
    public boolean gameContentTypeSupported;
    public android.view.Display.HdrCapabilities hdrCapabilities;
    public int height;
    public int modeId;
    public java.lang.String name;
    public java.lang.String ownerPackageName;
    public int ownerUid;
    public long presentationDeadlineNanos;
    public float renderFrameRate;
    public android.view.RoundedCorners roundedCorners;
    public int touch;
    public int type;
    public java.lang.String uniqueId;
    public int width;
    public float xDpi;
    public float yDpi;
    public int userPreferredModeId = -1;
    public android.view.Display.Mode[] supportedModes = android.view.Display.Mode.EMPTY_ARRAY;
    public int[] supportedColorModes = {0};
    public int rotation = 0;
    public int state = 2;
    public int committedState = 0;
    public android.view.DisplayEventReceiver.FrameRateOverride[] frameRateOverrides = new android.view.DisplayEventReceiver.FrameRateOverride[0];
    public float hdrSdrRatio = Float.NaN;
    public int installOrientation = 0;

    DisplayDeviceInfo() {
    }

    public void setAssumedDensityForExternalDisplay(int i, int i2) {
        this.densityDpi = (java.lang.Math.min(i, i2) * 320) / 1080;
        this.xDpi = this.densityDpi;
        this.yDpi = this.densityDpi;
    }

    public boolean equals(java.lang.Object obj) {
        return (obj instanceof com.android.server.display.DisplayDeviceInfo) && equals((com.android.server.display.DisplayDeviceInfo) obj);
    }

    public boolean equals(com.android.server.display.DisplayDeviceInfo displayDeviceInfo) {
        return displayDeviceInfo != null && diff(displayDeviceInfo) == 0;
    }

    public int diff(com.android.server.display.DisplayDeviceInfo displayDeviceInfo) {
        int i = (this.state == displayDeviceInfo.state && this.committedState == displayDeviceInfo.committedState) ? 0 : 1;
        if (this.colorMode != displayDeviceInfo.colorMode) {
            i |= 4;
        }
        if (!com.android.internal.display.BrightnessSynchronizer.floatEquals(this.hdrSdrRatio, displayDeviceInfo.hdrSdrRatio)) {
            i |= 8;
        }
        if (!java.util.Objects.equals(this.name, displayDeviceInfo.name) || !java.util.Objects.equals(this.uniqueId, displayDeviceInfo.uniqueId) || this.width != displayDeviceInfo.width || this.height != displayDeviceInfo.height || this.modeId != displayDeviceInfo.modeId || this.renderFrameRate != displayDeviceInfo.renderFrameRate || this.defaultModeId != displayDeviceInfo.defaultModeId || this.userPreferredModeId != displayDeviceInfo.userPreferredModeId || !java.util.Arrays.equals(this.supportedModes, displayDeviceInfo.supportedModes) || !java.util.Arrays.equals(this.supportedColorModes, displayDeviceInfo.supportedColorModes) || !java.util.Objects.equals(this.hdrCapabilities, displayDeviceInfo.hdrCapabilities) || this.allmSupported != displayDeviceInfo.allmSupported || this.gameContentTypeSupported != displayDeviceInfo.gameContentTypeSupported || this.densityDpi != displayDeviceInfo.densityDpi || this.xDpi != displayDeviceInfo.xDpi || this.yDpi != displayDeviceInfo.yDpi || this.appVsyncOffsetNanos != displayDeviceInfo.appVsyncOffsetNanos || this.presentationDeadlineNanos != displayDeviceInfo.presentationDeadlineNanos || this.flags != displayDeviceInfo.flags || !java.util.Objects.equals(this.displayCutout, displayDeviceInfo.displayCutout) || this.touch != displayDeviceInfo.touch || this.rotation != displayDeviceInfo.rotation || this.type != displayDeviceInfo.type || !java.util.Objects.equals(this.address, displayDeviceInfo.address) || !java.util.Objects.equals(this.deviceProductInfo, displayDeviceInfo.deviceProductInfo) || this.ownerUid != displayDeviceInfo.ownerUid || !java.util.Objects.equals(this.ownerPackageName, displayDeviceInfo.ownerPackageName) || !java.util.Arrays.equals(this.frameRateOverrides, displayDeviceInfo.frameRateOverrides) || !com.android.internal.display.BrightnessSynchronizer.floatEquals(this.brightnessMinimum, displayDeviceInfo.brightnessMinimum) || !com.android.internal.display.BrightnessSynchronizer.floatEquals(this.brightnessMaximum, displayDeviceInfo.brightnessMaximum) || !com.android.internal.display.BrightnessSynchronizer.floatEquals(this.brightnessDefault, displayDeviceInfo.brightnessDefault) || !java.util.Objects.equals(this.roundedCorners, displayDeviceInfo.roundedCorners) || this.installOrientation != displayDeviceInfo.installOrientation || !java.util.Objects.equals(this.displayShape, displayDeviceInfo.displayShape)) {
            return i | 2;
        }
        return i;
    }

    public int hashCode() {
        return 0;
    }

    public void copyFrom(com.android.server.display.DisplayDeviceInfo displayDeviceInfo) {
        this.name = displayDeviceInfo.name;
        this.uniqueId = displayDeviceInfo.uniqueId;
        this.width = displayDeviceInfo.width;
        this.height = displayDeviceInfo.height;
        this.modeId = displayDeviceInfo.modeId;
        this.renderFrameRate = displayDeviceInfo.renderFrameRate;
        this.defaultModeId = displayDeviceInfo.defaultModeId;
        this.userPreferredModeId = displayDeviceInfo.userPreferredModeId;
        this.supportedModes = displayDeviceInfo.supportedModes;
        this.colorMode = displayDeviceInfo.colorMode;
        this.supportedColorModes = displayDeviceInfo.supportedColorModes;
        this.hdrCapabilities = displayDeviceInfo.hdrCapabilities;
        this.allmSupported = displayDeviceInfo.allmSupported;
        this.gameContentTypeSupported = displayDeviceInfo.gameContentTypeSupported;
        this.densityDpi = displayDeviceInfo.densityDpi;
        this.xDpi = displayDeviceInfo.xDpi;
        this.yDpi = displayDeviceInfo.yDpi;
        this.appVsyncOffsetNanos = displayDeviceInfo.appVsyncOffsetNanos;
        this.presentationDeadlineNanos = displayDeviceInfo.presentationDeadlineNanos;
        this.flags = displayDeviceInfo.flags;
        this.displayCutout = displayDeviceInfo.displayCutout;
        this.touch = displayDeviceInfo.touch;
        this.rotation = displayDeviceInfo.rotation;
        this.type = displayDeviceInfo.type;
        this.address = displayDeviceInfo.address;
        this.deviceProductInfo = displayDeviceInfo.deviceProductInfo;
        this.state = displayDeviceInfo.state;
        this.committedState = displayDeviceInfo.committedState;
        this.ownerUid = displayDeviceInfo.ownerUid;
        this.ownerPackageName = displayDeviceInfo.ownerPackageName;
        this.frameRateOverrides = displayDeviceInfo.frameRateOverrides;
        this.brightnessMinimum = displayDeviceInfo.brightnessMinimum;
        this.brightnessMaximum = displayDeviceInfo.brightnessMaximum;
        this.brightnessDefault = displayDeviceInfo.brightnessDefault;
        this.hdrSdrRatio = displayDeviceInfo.hdrSdrRatio;
        this.roundedCorners = displayDeviceInfo.roundedCorners;
        this.installOrientation = displayDeviceInfo.installOrientation;
        this.displayShape = displayDeviceInfo.displayShape;
    }

    public java.lang.String toString() {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append("DisplayDeviceInfo{\"");
        sb.append(this.name);
        sb.append("\": uniqueId=\"");
        sb.append(this.uniqueId);
        sb.append("\", ");
        sb.append(this.width);
        sb.append(" x ");
        sb.append(this.height);
        sb.append(", modeId ");
        sb.append(this.modeId);
        sb.append(", renderFrameRate ");
        sb.append(this.renderFrameRate);
        sb.append(", defaultModeId ");
        sb.append(this.defaultModeId);
        sb.append(", userPreferredModeId ");
        sb.append(this.userPreferredModeId);
        sb.append(", supportedModes ");
        sb.append(java.util.Arrays.toString(this.supportedModes));
        sb.append(", colorMode ");
        sb.append(this.colorMode);
        sb.append(", supportedColorModes ");
        sb.append(java.util.Arrays.toString(this.supportedColorModes));
        sb.append(", hdrCapabilities ");
        sb.append(this.hdrCapabilities);
        sb.append(", allmSupported ");
        sb.append(this.allmSupported);
        sb.append(", gameContentTypeSupported ");
        sb.append(this.gameContentTypeSupported);
        sb.append(", density ");
        sb.append(this.densityDpi);
        sb.append(", ");
        sb.append(this.xDpi);
        sb.append(" x ");
        sb.append(this.yDpi);
        sb.append(" dpi");
        sb.append(", appVsyncOff ");
        sb.append(this.appVsyncOffsetNanos);
        sb.append(", presDeadline ");
        sb.append(this.presentationDeadlineNanos);
        if (this.displayCutout != null) {
            sb.append(", cutout ");
            sb.append(this.displayCutout);
        }
        sb.append(", touch ");
        sb.append(touchToString(this.touch));
        sb.append(", rotation ");
        sb.append(this.rotation);
        sb.append(", type ");
        sb.append(android.view.Display.typeToString(this.type));
        if (this.address != null) {
            sb.append(", address ");
            sb.append(this.address);
        }
        sb.append(", deviceProductInfo ");
        sb.append(this.deviceProductInfo);
        sb.append(", state ");
        sb.append(android.view.Display.stateToString(this.state));
        sb.append(", committedState ");
        sb.append(android.view.Display.stateToString(this.committedState));
        if (this.ownerUid != 0 || this.ownerPackageName != null) {
            sb.append(", owner ");
            sb.append(this.ownerPackageName);
            sb.append(" (uid ");
            sb.append(this.ownerUid);
            sb.append(")");
        }
        sb.append(", frameRateOverride ");
        for (android.view.DisplayEventReceiver.FrameRateOverride frameRateOverride : this.frameRateOverrides) {
            sb.append(frameRateOverride);
            sb.append(" ");
        }
        sb.append(", brightnessMinimum ");
        sb.append(this.brightnessMinimum);
        sb.append(", brightnessMaximum ");
        sb.append(this.brightnessMaximum);
        sb.append(", brightnessDefault ");
        sb.append(this.brightnessDefault);
        sb.append(", hdrSdrRatio ");
        sb.append(this.hdrSdrRatio);
        if (this.roundedCorners != null) {
            sb.append(", roundedCorners ");
            sb.append(this.roundedCorners);
        }
        sb.append(flagsToString(this.flags));
        sb.append(", installOrientation ");
        sb.append(this.installOrientation);
        if (this.displayShape != null) {
            sb.append(", displayShape ");
            sb.append(this.displayShape);
        }
        sb.append("}");
        return sb.toString();
    }

    private static java.lang.String touchToString(int i) {
        switch (i) {
            case 0:
                return "NONE";
            case 1:
                return "INTERNAL";
            case 2:
                return "EXTERNAL";
            case 3:
                return "VIRTUAL";
            default:
                return java.lang.Integer.toString(i);
        }
    }

    private static java.lang.String flagsToString(int i) {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        if ((i & 1) != 0) {
            sb.append(", FLAG_ALLOWED_TO_BE_DEFAULT_DISPLAY");
        }
        if ((i & 2) != 0) {
            sb.append(", FLAG_ROTATES_WITH_CONTENT");
        }
        if ((i & 4) != 0) {
            sb.append(", FLAG_SECURE");
        }
        if ((i & 8) != 0) {
            sb.append(", FLAG_SUPPORTS_PROTECTED_BUFFERS");
        }
        if ((i & 16) != 0) {
            sb.append(", FLAG_PRIVATE");
        }
        if ((i & 32) != 0) {
            sb.append(", FLAG_NEVER_BLANK");
        }
        if ((i & 64) != 0) {
            sb.append(", FLAG_PRESENTATION");
        }
        if ((i & 128) != 0) {
            sb.append(", FLAG_OWN_CONTENT_ONLY");
        }
        if ((i & 256) != 0) {
            sb.append(", FLAG_ROUND");
        }
        if ((i & 512) != 0) {
            sb.append(", FLAG_CAN_SHOW_WITH_INSECURE_KEYGUARD");
        }
        if ((i & 1024) != 0) {
            sb.append(", FLAG_DESTROY_CONTENT_ON_REMOVAL");
        }
        if ((i & 2048) != 0) {
            sb.append(", FLAG_MASK_DISPLAY_CUTOUT");
        }
        if ((i & 4096) != 0) {
            sb.append(", FLAG_SHOULD_SHOW_SYSTEM_DECORATIONS");
        }
        if ((i & 8192) != 0) {
            sb.append(", FLAG_TRUSTED");
        }
        if ((i & 16384) != 0) {
            sb.append(", FLAG_OWN_DISPLAY_GROUP");
        }
        if ((32768 & i) != 0) {
            sb.append(", FLAG_ALWAYS_UNLOCKED");
        }
        if ((65536 & i) != 0) {
            sb.append(", FLAG_TOUCH_FEEDBACK_DISABLED");
        }
        if ((131072 & i) != 0) {
            sb.append(", FLAG_OWN_FOCUS");
        }
        if ((i & 524288) != 0) {
            sb.append(", FLAG_STEAL_TOP_FOCUS_DISABLED");
        }
        return sb.toString();
    }
}
