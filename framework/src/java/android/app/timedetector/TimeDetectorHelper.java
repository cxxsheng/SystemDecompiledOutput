package android.app.timedetector;

/* loaded from: classes.dex */
public class TimeDetectorHelper {
    private static final int MANUAL_SUGGESTION_YEAR_MAX_WITHOUT_Y2038_ISSUE = 2100;
    private static final int MANUAL_SUGGESTION_YEAR_MAX_WITH_Y2038_ISSUE = 2037;
    private static final int MANUAL_SUGGESTION_YEAR_MIN = 2015;
    private static final java.time.Instant SUGGESTION_UPPER_BOUND_WITH_Y2038_ISSUE = java.time.Instant.ofEpochMilli(2147483647000L);
    private static final java.time.Instant SUGGESTION_UPPER_BOUND_WIITHOUT_Y2038_ISSUE = java.time.Instant.ofEpochMilli(Long.MAX_VALUE);
    private static final java.time.Instant MANUAL_SUGGESTION_LOWER_BOUND = java.time.Instant.ofEpochMilli(1415491200000L);
    private static final java.time.Instant AUTO_SUGGESTION_LOWER_BOUND_DEFAULT = java.time.Instant.ofEpochMilli(java.lang.Long.max(android.os.Environment.getRootDirectory().lastModified(), android.os.Build.TIME));
    public static final android.app.timedetector.TimeDetectorHelper INSTANCE = new android.app.timedetector.TimeDetectorHelper();

    protected TimeDetectorHelper() {
    }

    public int getManualDateSelectionYearMin() {
        return 2015;
    }

    public int getManualDateSelectionYearMax() {
        if (getDeviceHasY2038Issue()) {
            return 2037;
        }
        return 2100;
    }

    public java.time.Instant getManualSuggestionLowerBound() {
        return MANUAL_SUGGESTION_LOWER_BOUND;
    }

    public java.time.Instant getAutoSuggestionLowerBoundDefault() {
        return AUTO_SUGGESTION_LOWER_BOUND_DEFAULT;
    }

    public java.time.Instant getSuggestionUpperBound() {
        if (getDeviceHasY2038Issue()) {
            return SUGGESTION_UPPER_BOUND_WITH_Y2038_ISSUE;
        }
        return SUGGESTION_UPPER_BOUND_WIITHOUT_Y2038_ISSUE;
    }

    private boolean getDeviceHasY2038Issue() {
        return android.os.Build.SUPPORTED_32_BIT_ABIS.length > 0;
    }
}
