package android.app.timedetector;

/* loaded from: classes.dex */
public final class TimeSuggestionHelper {
    private java.util.ArrayList<java.lang.String> mDebugInfo;
    private final java.lang.Class<?> mHelpedClass;
    private final android.app.time.UnixEpochTime mUnixEpochTime;

    public TimeSuggestionHelper(java.lang.Class<?> cls, android.app.time.UnixEpochTime unixEpochTime) {
        this.mHelpedClass = (java.lang.Class) java.util.Objects.requireNonNull(cls);
        this.mUnixEpochTime = (android.app.time.UnixEpochTime) java.util.Objects.requireNonNull(unixEpochTime);
    }

    public android.app.time.UnixEpochTime getUnixEpochTime() {
        return this.mUnixEpochTime;
    }

    public java.util.List<java.lang.String> getDebugInfo() {
        return this.mDebugInfo == null ? java.util.Collections.emptyList() : java.util.Collections.unmodifiableList(this.mDebugInfo);
    }

    public void addDebugInfo(java.lang.String str) {
        if (this.mDebugInfo == null) {
            this.mDebugInfo = new java.util.ArrayList<>();
        }
        this.mDebugInfo.add(str);
    }

    public void addDebugInfo(java.lang.String... strArr) {
        addDebugInfo(java.util.Arrays.asList(strArr));
    }

    public void addDebugInfo(java.util.List<java.lang.String> list) {
        if (this.mDebugInfo == null) {
            this.mDebugInfo = new java.util.ArrayList<>(list.size());
        }
        this.mDebugInfo.addAll(list);
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return handleEquals((android.app.timedetector.TimeSuggestionHelper) obj);
    }

    public boolean handleEquals(android.app.timedetector.TimeSuggestionHelper timeSuggestionHelper) {
        return java.util.Objects.equals(this.mHelpedClass, timeSuggestionHelper.mHelpedClass) && java.util.Objects.equals(this.mUnixEpochTime, timeSuggestionHelper.mUnixEpochTime);
    }

    public int hashCode() {
        return java.util.Objects.hash(this.mUnixEpochTime);
    }

    public java.lang.String handleToString() {
        return this.mHelpedClass.getSimpleName() + "{mUnixEpochTime=" + this.mUnixEpochTime + ", mDebugInfo=" + this.mDebugInfo + '}';
    }

    public static android.app.timedetector.TimeSuggestionHelper handleCreateFromParcel(java.lang.Class<?> cls, android.os.Parcel parcel) {
        android.app.timedetector.TimeSuggestionHelper timeSuggestionHelper = new android.app.timedetector.TimeSuggestionHelper(cls, (android.app.time.UnixEpochTime) parcel.readParcelable(null, android.app.time.UnixEpochTime.class));
        timeSuggestionHelper.mDebugInfo = parcel.readArrayList(null, java.lang.String.class);
        return timeSuggestionHelper;
    }

    public void handleWriteToParcel(android.os.Parcel parcel, int i) {
        parcel.writeParcelable(this.mUnixEpochTime, 0);
        parcel.writeList(this.mDebugInfo);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public static android.app.timedetector.TimeSuggestionHelper handleParseCommandLineArg(java.lang.Class<?> cls, android.os.ShellCommand shellCommand) throws java.lang.IllegalArgumentException {
        char c;
        java.lang.Long l = null;
        java.lang.Long l2 = null;
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
                    default:
                        throw new java.lang.IllegalArgumentException("Unknown option: " + nextArg);
                }
            } else {
                if (l == null) {
                    throw new java.lang.IllegalArgumentException("No referenceTimeMillis specified.");
                }
                if (l2 == null) {
                    throw new java.lang.IllegalArgumentException("No unixEpochTimeMillis specified.");
                }
                android.app.timedetector.TimeSuggestionHelper timeSuggestionHelper = new android.app.timedetector.TimeSuggestionHelper(cls, new android.app.time.UnixEpochTime(l.longValue(), l2.longValue()));
                timeSuggestionHelper.addDebugInfo("Command line injection");
                return timeSuggestionHelper;
            }
        }
    }

    public static void handlePrintCommandLineOpts(java.io.PrintWriter printWriter, java.lang.String str, java.lang.Class<?> cls) {
        printWriter.printf("%s suggestion options:\n", str);
        printWriter.println("  --elapsed_realtime <elapsed realtime millis> - the elapsed realtime millis when unix epoch time was read");
        printWriter.println("  --unix_epoch_time <Unix epoch time millis>");
        printWriter.println();
        printWriter.println("See " + cls.getName() + " for more information");
    }
}
