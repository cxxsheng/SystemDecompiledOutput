package com.android.server.timezonedetector;

/* loaded from: classes2.dex */
public final class LocationAlgorithmEvent {

    @android.annotation.NonNull
    private final android.app.time.LocationTimeZoneAlgorithmStatus mAlgorithmStatus;

    @android.annotation.Nullable
    private java.util.ArrayList<java.lang.String> mDebugInfo;

    @android.annotation.Nullable
    private final com.android.server.timezonedetector.GeolocationTimeZoneSuggestion mSuggestion;

    public LocationAlgorithmEvent(@android.annotation.NonNull android.app.time.LocationTimeZoneAlgorithmStatus locationTimeZoneAlgorithmStatus, @android.annotation.Nullable com.android.server.timezonedetector.GeolocationTimeZoneSuggestion geolocationTimeZoneSuggestion) {
        java.util.Objects.requireNonNull(locationTimeZoneAlgorithmStatus);
        this.mAlgorithmStatus = locationTimeZoneAlgorithmStatus;
        this.mSuggestion = geolocationTimeZoneSuggestion;
    }

    @android.annotation.NonNull
    public android.app.time.LocationTimeZoneAlgorithmStatus getAlgorithmStatus() {
        return this.mAlgorithmStatus;
    }

    @android.annotation.Nullable
    public com.android.server.timezonedetector.GeolocationTimeZoneSuggestion getSuggestion() {
        return this.mSuggestion;
    }

    @android.annotation.NonNull
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
        if (obj == null || com.android.server.timezonedetector.LocationAlgorithmEvent.class != obj.getClass()) {
            return false;
        }
        com.android.server.timezonedetector.LocationAlgorithmEvent locationAlgorithmEvent = (com.android.server.timezonedetector.LocationAlgorithmEvent) obj;
        if (this.mAlgorithmStatus.equals(locationAlgorithmEvent.mAlgorithmStatus) && java.util.Objects.equals(this.mSuggestion, locationAlgorithmEvent.mSuggestion)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return java.util.Objects.hash(this.mAlgorithmStatus, this.mSuggestion);
    }

    public java.lang.String toString() {
        return "LocationAlgorithmEvent{mAlgorithmStatus=" + this.mAlgorithmStatus + ", mSuggestion=" + this.mSuggestion + ", mDebugInfo=" + this.mDebugInfo + '}';
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    static com.android.server.timezonedetector.LocationAlgorithmEvent parseCommandLineArg(@android.annotation.NonNull android.os.ShellCommand shellCommand) {
        char c;
        com.android.server.timezonedetector.GeolocationTimeZoneSuggestion geolocationTimeZoneSuggestion = null;
        android.app.time.LocationTimeZoneAlgorithmStatus locationTimeZoneAlgorithmStatus = null;
        java.lang.String str = null;
        while (true) {
            java.lang.String nextArg = shellCommand.getNextArg();
            if (nextArg != null) {
                switch (nextArg.hashCode()) {
                    case -841922652:
                        if (nextArg.equals("--suggestion")) {
                            c = 1;
                            break;
                        }
                        c = 65535;
                        break;
                    case 1507532178:
                        if (nextArg.equals("--status")) {
                            c = 0;
                            break;
                        }
                        c = 65535;
                        break;
                    default:
                        c = 65535;
                        break;
                }
                switch (c) {
                    case 0:
                        locationTimeZoneAlgorithmStatus = android.app.time.LocationTimeZoneAlgorithmStatus.parseCommandlineArg(shellCommand.getNextArgRequired());
                        break;
                    case 1:
                        str = shellCommand.getNextArgRequired();
                        break;
                    default:
                        throw new java.lang.IllegalArgumentException("Unknown option: " + nextArg);
                }
            } else {
                if (locationTimeZoneAlgorithmStatus == null) {
                    throw new java.lang.IllegalArgumentException("Missing --status");
                }
                if (str != null) {
                    java.util.List<java.lang.String> parseZoneIds = parseZoneIds(str);
                    long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
                    if (parseZoneIds == null) {
                        geolocationTimeZoneSuggestion = com.android.server.timezonedetector.GeolocationTimeZoneSuggestion.createUncertainSuggestion(elapsedRealtime);
                    } else {
                        geolocationTimeZoneSuggestion = com.android.server.timezonedetector.GeolocationTimeZoneSuggestion.createCertainSuggestion(elapsedRealtime, parseZoneIds);
                    }
                }
                com.android.server.timezonedetector.LocationAlgorithmEvent locationAlgorithmEvent = new com.android.server.timezonedetector.LocationAlgorithmEvent(locationTimeZoneAlgorithmStatus, geolocationTimeZoneSuggestion);
                locationAlgorithmEvent.addDebugInfo("Command line injection");
                return locationAlgorithmEvent;
            }
        }
    }

    private static java.util.List<java.lang.String> parseZoneIds(java.lang.String str) {
        if ("UNCERTAIN".equals(str)) {
            return null;
        }
        if ("EMPTY".equals(str)) {
            return java.util.Collections.emptyList();
        }
        java.util.ArrayList arrayList = new java.util.ArrayList();
        java.util.StringTokenizer stringTokenizer = new java.util.StringTokenizer(str, ",");
        while (stringTokenizer.hasMoreTokens()) {
            arrayList.add(stringTokenizer.nextToken());
        }
        return arrayList;
    }

    static void printCommandLineOpts(@android.annotation.NonNull java.io.PrintWriter printWriter) {
        printWriter.println("Location algorithm event options:");
        printWriter.println("  --status {LocationTimeZoneAlgorithmStatus toString() format}");
        printWriter.println("  [--suggestion {UNCERTAIN|EMPTY|<Olson ID>+}]");
        printWriter.println();
        printWriter.println("See " + com.android.server.timezonedetector.LocationAlgorithmEvent.class.getName() + " for more information");
    }
}
