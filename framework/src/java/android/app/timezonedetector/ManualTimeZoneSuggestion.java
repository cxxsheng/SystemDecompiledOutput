package android.app.timezonedetector;

/* loaded from: classes.dex */
public final class ManualTimeZoneSuggestion implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.app.timezonedetector.ManualTimeZoneSuggestion> CREATOR = new android.os.Parcelable.Creator<android.app.timezonedetector.ManualTimeZoneSuggestion>() { // from class: android.app.timezonedetector.ManualTimeZoneSuggestion.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.timezonedetector.ManualTimeZoneSuggestion createFromParcel(android.os.Parcel parcel) {
            return android.app.timezonedetector.ManualTimeZoneSuggestion.createFromParcel(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.timezonedetector.ManualTimeZoneSuggestion[] newArray(int i) {
            return new android.app.timezonedetector.ManualTimeZoneSuggestion[i];
        }
    };
    private java.util.ArrayList<java.lang.String> mDebugInfo;
    private final java.lang.String mZoneId;

    public ManualTimeZoneSuggestion(java.lang.String str) {
        this.mZoneId = (java.lang.String) java.util.Objects.requireNonNull(str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static android.app.timezonedetector.ManualTimeZoneSuggestion createFromParcel(android.os.Parcel parcel) {
        android.app.timezonedetector.ManualTimeZoneSuggestion manualTimeZoneSuggestion = new android.app.timezonedetector.ManualTimeZoneSuggestion(parcel.readString());
        manualTimeZoneSuggestion.mDebugInfo = parcel.readArrayList(null, java.lang.String.class);
        return manualTimeZoneSuggestion;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeString(this.mZoneId);
        parcel.writeList(this.mDebugInfo);
    }

    public java.lang.String getZoneId() {
        return this.mZoneId;
    }

    public java.util.List<java.lang.String> getDebugInfo() {
        return this.mDebugInfo == null ? java.util.Collections.emptyList() : java.util.Collections.unmodifiableList(this.mDebugInfo);
    }

    public void addDebugInfo(java.lang.String... strArr) {
        if (this.mDebugInfo == null) {
            this.mDebugInfo = new java.util.ArrayList<>();
        }
        this.mDebugInfo.addAll(java.util.Arrays.asList(strArr));
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return java.util.Objects.equals(this.mZoneId, ((android.app.timezonedetector.ManualTimeZoneSuggestion) obj).mZoneId);
    }

    public int hashCode() {
        return java.util.Objects.hash(this.mZoneId);
    }

    public java.lang.String toString() {
        return "ManualTimeZoneSuggestion{mZoneId=" + this.mZoneId + ", mDebugInfo=" + this.mDebugInfo + '}';
    }

    public static android.app.timezonedetector.ManualTimeZoneSuggestion parseCommandLineArg(android.os.ShellCommand shellCommand) {
        char c;
        java.lang.String str = null;
        while (true) {
            java.lang.String nextArg = shellCommand.getNextArg();
            if (nextArg != null) {
                switch (nextArg.hashCode()) {
                    case 1274807534:
                        if (nextArg.equals("--zone_id")) {
                            c = 0;
                            break;
                        }
                    default:
                        c = 65535;
                        break;
                }
                switch (c) {
                    case 0:
                        str = shellCommand.getNextArgRequired();
                    default:
                        throw new java.lang.IllegalArgumentException("Unknown option: " + nextArg);
                }
            } else {
                android.app.timezonedetector.ManualTimeZoneSuggestion manualTimeZoneSuggestion = new android.app.timezonedetector.ManualTimeZoneSuggestion(str);
                manualTimeZoneSuggestion.addDebugInfo("Command line injection");
                return manualTimeZoneSuggestion;
            }
        }
    }

    public static void printCommandLineOpts(java.io.PrintWriter printWriter) {
        printWriter.println("Manual suggestion options:");
        printWriter.println("  --zone_id <Olson ID>");
        printWriter.println();
        printWriter.println("See " + android.app.timezonedetector.ManualTimeZoneSuggestion.class.getName() + " for more information");
    }
}
