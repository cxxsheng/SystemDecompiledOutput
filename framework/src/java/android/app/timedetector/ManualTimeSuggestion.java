package android.app.timedetector;

/* loaded from: classes.dex */
public final class ManualTimeSuggestion implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.app.timedetector.ManualTimeSuggestion> CREATOR = new android.os.Parcelable.Creator<android.app.timedetector.ManualTimeSuggestion>() { // from class: android.app.timedetector.ManualTimeSuggestion.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.timedetector.ManualTimeSuggestion createFromParcel(android.os.Parcel parcel) {
            return new android.app.timedetector.ManualTimeSuggestion(android.app.timedetector.TimeSuggestionHelper.handleCreateFromParcel(android.app.timedetector.ManualTimeSuggestion.class, parcel));
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.timedetector.ManualTimeSuggestion[] newArray(int i) {
            return new android.app.timedetector.ManualTimeSuggestion[i];
        }
    };
    private final android.app.timedetector.TimeSuggestionHelper mTimeSuggestionHelper;

    public ManualTimeSuggestion(android.app.time.UnixEpochTime unixEpochTime) {
        this.mTimeSuggestionHelper = new android.app.timedetector.TimeSuggestionHelper(android.app.timedetector.ManualTimeSuggestion.class, unixEpochTime);
    }

    private ManualTimeSuggestion(android.app.timedetector.TimeSuggestionHelper timeSuggestionHelper) {
        this.mTimeSuggestionHelper = (android.app.timedetector.TimeSuggestionHelper) java.util.Objects.requireNonNull(timeSuggestionHelper);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        this.mTimeSuggestionHelper.handleWriteToParcel(parcel, i);
    }

    public android.app.time.UnixEpochTime getUnixEpochTime() {
        return this.mTimeSuggestionHelper.getUnixEpochTime();
    }

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
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return this.mTimeSuggestionHelper.handleEquals(((android.app.timedetector.ManualTimeSuggestion) obj).mTimeSuggestionHelper);
    }

    public int hashCode() {
        return this.mTimeSuggestionHelper.hashCode();
    }

    public java.lang.String toString() {
        return this.mTimeSuggestionHelper.handleToString();
    }

    public static android.app.timedetector.ManualTimeSuggestion parseCommandLineArg(android.os.ShellCommand shellCommand) throws java.lang.IllegalArgumentException {
        return new android.app.timedetector.ManualTimeSuggestion(android.app.timedetector.TimeSuggestionHelper.handleParseCommandLineArg(android.app.timedetector.ManualTimeSuggestion.class, shellCommand));
    }

    public static void printCommandLineOpts(java.io.PrintWriter printWriter) {
        android.app.timedetector.TimeSuggestionHelper.handlePrintCommandLineOpts(printWriter, "Manual", android.app.timedetector.ManualTimeSuggestion.class);
    }
}
