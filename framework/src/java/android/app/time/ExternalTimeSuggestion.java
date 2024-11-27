package android.app.time;

@android.annotation.SystemApi
/* loaded from: classes.dex */
public final class ExternalTimeSuggestion implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.app.time.ExternalTimeSuggestion> CREATOR = new android.os.Parcelable.Creator<android.app.time.ExternalTimeSuggestion>() { // from class: android.app.time.ExternalTimeSuggestion.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.time.ExternalTimeSuggestion createFromParcel(android.os.Parcel parcel) {
            return new android.app.time.ExternalTimeSuggestion(android.app.timedetector.TimeSuggestionHelper.handleCreateFromParcel(android.app.time.ExternalTimeSuggestion.class, parcel));
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.time.ExternalTimeSuggestion[] newArray(int i) {
            return new android.app.time.ExternalTimeSuggestion[i];
        }
    };
    private final android.app.timedetector.TimeSuggestionHelper mTimeSuggestionHelper;

    public ExternalTimeSuggestion(long j, long j2) {
        this.mTimeSuggestionHelper = new android.app.timedetector.TimeSuggestionHelper(android.app.time.ExternalTimeSuggestion.class, new android.app.time.UnixEpochTime(j, j2));
    }

    private ExternalTimeSuggestion(android.app.timedetector.TimeSuggestionHelper timeSuggestionHelper) {
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
        return this.mTimeSuggestionHelper.handleEquals(((android.app.time.ExternalTimeSuggestion) obj).mTimeSuggestionHelper);
    }

    public int hashCode() {
        return this.mTimeSuggestionHelper.hashCode();
    }

    public java.lang.String toString() {
        return this.mTimeSuggestionHelper.handleToString();
    }

    public static android.app.time.ExternalTimeSuggestion parseCommandLineArg(android.os.ShellCommand shellCommand) throws java.lang.IllegalArgumentException {
        return new android.app.time.ExternalTimeSuggestion(android.app.timedetector.TimeSuggestionHelper.handleParseCommandLineArg(android.app.time.ExternalTimeSuggestion.class, shellCommand));
    }

    public static void printCommandLineOpts(java.io.PrintWriter printWriter) {
        android.app.timedetector.TimeSuggestionHelper.handlePrintCommandLineOpts(printWriter, "External", android.app.time.ExternalTimeSuggestion.class);
    }
}
