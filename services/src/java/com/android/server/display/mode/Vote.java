package com.android.server.display.mode;

/* loaded from: classes.dex */
interface Vote {
    public static final int APP_REQUEST_REFRESH_RATE_RANGE_PRIORITY_CUTOFF = 5;
    public static final int INVALID_SIZE = -1;
    public static final int MAX_PRIORITY = 17;
    public static final int MIN_PRIORITY = 0;
    public static final int PRIORITY_APP_REQUEST_BASE_MODE_REFRESH_RATE = 6;
    public static final int PRIORITY_APP_REQUEST_RENDER_FRAME_RATE_RANGE = 5;
    public static final int PRIORITY_APP_REQUEST_SIZE = 7;
    public static final int PRIORITY_AUTH_OPTIMIZER_RENDER_FRAME_RATE = 11;
    public static final int PRIORITY_DEFAULT_RENDER_FRAME_RATE = 0;
    public static final int PRIORITY_FLICKER_REFRESH_RATE = 1;
    public static final int PRIORITY_FLICKER_REFRESH_RATE_SWITCH = 14;
    public static final int PRIORITY_HIGH_BRIGHTNESS_MODE = 2;
    public static final int PRIORITY_LAYOUT_LIMITED_FRAME_RATE = 12;
    public static final int PRIORITY_LIMIT_MODE = 10;
    public static final int PRIORITY_LOW_POWER_MODE = 13;
    public static final int PRIORITY_PROXIMITY = 16;
    public static final int PRIORITY_SKIN_TEMPERATURE = 15;
    public static final int PRIORITY_SYNCHRONIZED_REFRESH_RATE = 9;
    public static final int PRIORITY_UDFPS = 17;
    public static final int PRIORITY_USER_SETTING_DISPLAY_PREFERRED_SIZE = 4;
    public static final int PRIORITY_USER_SETTING_MIN_RENDER_FRAME_RATE = 3;
    public static final int PRIORITY_USER_SETTING_PEAK_RENDER_FRAME_RATE = 8;

    void updateSummary(com.android.server.display.mode.VoteSummary voteSummary);

    static com.android.server.display.mode.Vote forPhysicalRefreshRates(float f, float f2) {
        return new com.android.server.display.mode.CombinedVote(java.util.List.of(new com.android.server.display.mode.RefreshRateVote.PhysicalVote(f, f2), new com.android.server.display.mode.DisableRefreshRateSwitchingVote(f == f2)));
    }

    static com.android.server.display.mode.Vote forRenderFrameRates(float f, float f2) {
        return new com.android.server.display.mode.RefreshRateVote.RenderVote(f, f2);
    }

    static com.android.server.display.mode.Vote forSize(int i, int i2) {
        return new com.android.server.display.mode.SizeVote(i, i2, i, i2);
    }

    static com.android.server.display.mode.Vote forSizeAndPhysicalRefreshRatesRange(int i, int i2, int i3, int i4, float f, float f2) {
        return new com.android.server.display.mode.CombinedVote(java.util.List.of(new com.android.server.display.mode.SizeVote(i3, i4, i, i2), new com.android.server.display.mode.RefreshRateVote.PhysicalVote(f, f2), new com.android.server.display.mode.DisableRefreshRateSwitchingVote(f == f2)));
    }

    static com.android.server.display.mode.Vote forDisableRefreshRateSwitching() {
        return new com.android.server.display.mode.DisableRefreshRateSwitchingVote(true);
    }

    static com.android.server.display.mode.Vote forBaseModeRefreshRate(float f) {
        return new com.android.server.display.mode.BaseModeRefreshRateVote(f);
    }

    static com.android.server.display.mode.Vote forSupportedModes(java.util.List<com.android.server.display.mode.SupportedModesVote.SupportedMode> list) {
        return new com.android.server.display.mode.SupportedModesVote(list);
    }

    static com.android.server.display.mode.Vote forSupportedModesAndDisableRefreshRateSwitching(java.util.List<com.android.server.display.mode.SupportedModesVote.SupportedMode> list) {
        return new com.android.server.display.mode.CombinedVote(java.util.List.of(forDisableRefreshRateSwitching(), forSupportedModes(list)));
    }

    static java.lang.String priorityToString(int i) {
        switch (i) {
            case 0:
                return "PRIORITY_DEFAULT_REFRESH_RATE";
            case 1:
                return "PRIORITY_FLICKER_REFRESH_RATE";
            case 2:
                return "PRIORITY_HIGH_BRIGHTNESS_MODE";
            case 3:
                return "PRIORITY_USER_SETTING_MIN_RENDER_FRAME_RATE";
            case 4:
                return "PRIORITY_USER_SETTING_DISPLAY_PREFERRED_SIZE";
            case 5:
                return "PRIORITY_APP_REQUEST_RENDER_FRAME_RATE_RANGE";
            case 6:
                return "PRIORITY_APP_REQUEST_BASE_MODE_REFRESH_RATE";
            case 7:
                return "PRIORITY_APP_REQUEST_SIZE";
            case 8:
                return "PRIORITY_USER_SETTING_PEAK_RENDER_FRAME_RATE";
            case 9:
                return "PRIORITY_SYNCHRONIZED_REFRESH_RATE";
            case 10:
                return "PRIORITY_LIMIT_MODE";
            case 11:
                return "PRIORITY_AUTH_OPTIMIZER_RENDER_FRAME_RATE";
            case 12:
                return "PRIORITY_LAYOUT_LIMITED_FRAME_RATE";
            case 13:
                return "PRIORITY_LOW_POWER_MODE";
            case 14:
                return "PRIORITY_FLICKER_REFRESH_RATE_SWITCH";
            case 15:
                return "PRIORITY_SKIN_TEMPERATURE";
            case 16:
                return "PRIORITY_PROXIMITY";
            case 17:
                return "PRIORITY_UDFPS";
            default:
                return java.lang.Integer.toString(i);
        }
    }
}
