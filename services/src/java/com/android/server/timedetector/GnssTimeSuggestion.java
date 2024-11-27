package com.android.server.timedetector;

/* loaded from: classes2.dex */
public final class GnssTimeSuggestion {

    @android.annotation.NonNull
    private final android.app.timedetector.TimeSuggestionHelper mTimeSuggestionHelper;

    public GnssTimeSuggestion(@android.annotation.NonNull android.app.time.UnixEpochTime unixEpochTime) {
        this.mTimeSuggestionHelper = new android.app.timedetector.TimeSuggestionHelper(com.android.server.timedetector.GnssTimeSuggestion.class, unixEpochTime);
    }

    private GnssTimeSuggestion(@android.annotation.NonNull android.app.timedetector.TimeSuggestionHelper timeSuggestionHelper) {
        java.util.Objects.requireNonNull(timeSuggestionHelper);
        this.mTimeSuggestionHelper = timeSuggestionHelper;
    }

    @android.annotation.NonNull
    public android.app.time.UnixEpochTime getUnixEpochTime() {
        return this.mTimeSuggestionHelper.getUnixEpochTime();
    }

    @android.annotation.NonNull
    public java.util.List<java.lang.String> getDebugInfo() {
        return this.mTimeSuggestionHelper.getDebugInfo();
    }

    public void addDebugInfo(java.lang.String... strArr) {
        this.mTimeSuggestionHelper.addDebugInfo(strArr);
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || com.android.server.timedetector.GnssTimeSuggestion.class != obj.getClass()) {
            return false;
        }
        return this.mTimeSuggestionHelper.handleEquals(((com.android.server.timedetector.GnssTimeSuggestion) obj).mTimeSuggestionHelper);
    }

    public int hashCode() {
        return this.mTimeSuggestionHelper.hashCode();
    }

    public java.lang.String toString() {
        return this.mTimeSuggestionHelper.handleToString();
    }

    public static com.android.server.timedetector.GnssTimeSuggestion parseCommandLineArg(@android.annotation.NonNull android.os.ShellCommand shellCommand) throws java.lang.IllegalArgumentException {
        return new com.android.server.timedetector.GnssTimeSuggestion(android.app.timedetector.TimeSuggestionHelper.handleParseCommandLineArg(com.android.server.timedetector.GnssTimeSuggestion.class, shellCommand));
    }

    public static void printCommandLineOpts(java.io.PrintWriter printWriter) {
        android.app.timedetector.TimeSuggestionHelper.handlePrintCommandLineOpts(printWriter, "GNSS", com.android.server.timedetector.GnssTimeSuggestion.class);
    }
}
