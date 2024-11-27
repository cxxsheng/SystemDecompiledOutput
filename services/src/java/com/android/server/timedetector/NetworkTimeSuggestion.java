package com.android.server.timedetector;

/* loaded from: classes2.dex */
public final class NetworkTimeSuggestion {

    @android.annotation.Nullable
    private java.util.ArrayList<java.lang.String> mDebugInfo;
    private final int mUncertaintyMillis;

    @android.annotation.NonNull
    private final android.app.time.UnixEpochTime mUnixEpochTime;

    public NetworkTimeSuggestion(@android.annotation.NonNull android.app.time.UnixEpochTime unixEpochTime, int i) {
        java.util.Objects.requireNonNull(unixEpochTime);
        this.mUnixEpochTime = unixEpochTime;
        if (i < 0) {
            throw new java.lang.IllegalArgumentException("uncertaintyMillis < 0");
        }
        this.mUncertaintyMillis = i;
    }

    @android.annotation.NonNull
    public android.app.time.UnixEpochTime getUnixEpochTime() {
        return this.mUnixEpochTime;
    }

    public int getUncertaintyMillis() {
        return this.mUncertaintyMillis;
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
        if (!(obj instanceof com.android.server.timedetector.NetworkTimeSuggestion)) {
            return false;
        }
        com.android.server.timedetector.NetworkTimeSuggestion networkTimeSuggestion = (com.android.server.timedetector.NetworkTimeSuggestion) obj;
        return this.mUnixEpochTime.equals(networkTimeSuggestion.mUnixEpochTime) && this.mUncertaintyMillis == networkTimeSuggestion.mUncertaintyMillis;
    }

    public int hashCode() {
        return java.util.Objects.hash(this.mUnixEpochTime, java.lang.Integer.valueOf(this.mUncertaintyMillis));
    }

    public java.lang.String toString() {
        return "NetworkTimeSuggestion{mUnixEpochTime=" + this.mUnixEpochTime + ", mUncertaintyMillis=" + this.mUncertaintyMillis + ", mDebugInfo=" + this.mDebugInfo + '}';
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public static com.android.server.timedetector.NetworkTimeSuggestion parseCommandLineArg(@android.annotation.NonNull android.os.ShellCommand shellCommand) throws java.lang.IllegalArgumentException {
        char c;
        java.lang.Long l = null;
        java.lang.Long l2 = null;
        java.lang.Integer num = null;
        while (true) {
            java.lang.String nextArg = shellCommand.getNextArg();
            if (nextArg != null) {
                switch (nextArg.hashCode()) {
                    case 16142561:
                        if (nextArg.equals("--reference_time")) {
                            c = 0;
                            break;
                        }
                        c = 65535;
                        break;
                    case 48316014:
                        if (nextArg.equals("--elapsed_realtime")) {
                            c = 1;
                            break;
                        }
                        c = 65535;
                        break;
                    case 410278458:
                        if (nextArg.equals("--unix_epoch_time")) {
                            c = 2;
                            break;
                        }
                        c = 65535;
                        break;
                    case 1387445527:
                        if (nextArg.equals("--uncertainty_millis")) {
                            c = 3;
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
                    case 1:
                        l = java.lang.Long.valueOf(java.lang.Long.parseLong(shellCommand.getNextArgRequired()));
                        break;
                    case 2:
                        l2 = java.lang.Long.valueOf(java.lang.Long.parseLong(shellCommand.getNextArgRequired()));
                        break;
                    case 3:
                        num = java.lang.Integer.valueOf(java.lang.Integer.parseInt(shellCommand.getNextArgRequired()));
                        break;
                    default:
                        throw new java.lang.IllegalArgumentException("Unknown option: " + nextArg);
                }
            } else {
                if (l == null) {
                    throw new java.lang.IllegalArgumentException("No elapsedRealtimeMillis specified.");
                }
                if (l2 == null) {
                    throw new java.lang.IllegalArgumentException("No unixEpochTimeMillis specified.");
                }
                if (num == null) {
                    throw new java.lang.IllegalArgumentException("No uncertaintyMillis specified.");
                }
                com.android.server.timedetector.NetworkTimeSuggestion networkTimeSuggestion = new com.android.server.timedetector.NetworkTimeSuggestion(new android.app.time.UnixEpochTime(l.longValue(), l2.longValue()), num.intValue());
                networkTimeSuggestion.addDebugInfo("Command line injection");
                return networkTimeSuggestion;
            }
        }
    }

    public static void printCommandLineOpts(java.io.PrintWriter printWriter) {
        printWriter.printf("%s suggestion options:\n", "Network");
        printWriter.println("  --elapsed_realtime <elapsed realtime millis> - the elapsed realtime millis when unix epoch time was read");
        printWriter.println("  --unix_epoch_time <Unix epoch time millis>");
        printWriter.println("  --uncertainty_millis <Uncertainty millis> - a positive error bound (+/-) estimate for unix epoch time");
        printWriter.println();
        printWriter.println("See " + com.android.server.timedetector.NetworkTimeSuggestion.class.getName() + " for more information");
    }
}
