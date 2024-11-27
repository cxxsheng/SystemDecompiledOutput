package android.app.time;

@android.annotation.SystemApi
/* loaded from: classes.dex */
public final class UnixEpochTime implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.app.time.UnixEpochTime> CREATOR = new android.os.Parcelable.Creator<android.app.time.UnixEpochTime>() { // from class: android.app.time.UnixEpochTime.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.time.UnixEpochTime createFromParcel(android.os.Parcel parcel) {
            return new android.app.time.UnixEpochTime(parcel.readLong(), parcel.readLong());
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.time.UnixEpochTime[] newArray(int i) {
            return new android.app.time.UnixEpochTime[i];
        }
    };
    private final long mElapsedRealtimeMillis;
    private final long mUnixEpochTimeMillis;

    public UnixEpochTime(long j, long j2) {
        this.mElapsedRealtimeMillis = j;
        this.mUnixEpochTimeMillis = j2;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public static android.app.time.UnixEpochTime parseCommandLineArgs(android.os.ShellCommand shellCommand) {
        char c;
        java.lang.Long l = null;
        java.lang.Long l2 = null;
        while (true) {
            java.lang.String nextArg = shellCommand.getNextArg();
            if (nextArg != null) {
                switch (nextArg.hashCode()) {
                    case 48316014:
                        if (nextArg.equals("--elapsed_realtime")) {
                            c = 0;
                            break;
                        }
                        c = 65535;
                        break;
                    case 410278458:
                        if (nextArg.equals("--unix_epoch_time")) {
                            c = 1;
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
                        l = java.lang.Long.valueOf(java.lang.Long.parseLong(shellCommand.getNextArgRequired()));
                        break;
                    case 1:
                        l2 = java.lang.Long.valueOf(java.lang.Long.parseLong(shellCommand.getNextArgRequired()));
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
                return new android.app.time.UnixEpochTime(l.longValue(), l2.longValue());
            }
        }
    }

    public static void printCommandLineOpts(java.io.PrintWriter printWriter) {
        printWriter.println("UnixEpochTime options:\n");
        printWriter.println("  --elapsed_realtime <elapsed realtime millis>");
        printWriter.println("  --unix_epoch_time <Unix epoch time millis>");
        printWriter.println();
        printWriter.println("See " + android.app.time.UnixEpochTime.class.getName() + " for more information");
    }

    public long getElapsedRealtimeMillis() {
        return this.mElapsedRealtimeMillis;
    }

    public long getUnixEpochTimeMillis() {
        return this.mUnixEpochTimeMillis;
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        android.app.time.UnixEpochTime unixEpochTime = (android.app.time.UnixEpochTime) obj;
        if (this.mElapsedRealtimeMillis == unixEpochTime.mElapsedRealtimeMillis && this.mUnixEpochTimeMillis == unixEpochTime.mUnixEpochTimeMillis) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return java.util.Objects.hash(java.lang.Long.valueOf(this.mElapsedRealtimeMillis), java.lang.Long.valueOf(this.mUnixEpochTimeMillis));
    }

    public java.lang.String toString() {
        return "UnixEpochTime{mElapsedRealtimeMillis=" + this.mElapsedRealtimeMillis + ", mUnixEpochTimeMillis=" + this.mUnixEpochTimeMillis + '}';
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeLong(this.mElapsedRealtimeMillis);
        parcel.writeLong(this.mUnixEpochTimeMillis);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public android.app.time.UnixEpochTime at(long j) {
        return new android.app.time.UnixEpochTime(j, (j - this.mElapsedRealtimeMillis) + this.mUnixEpochTimeMillis);
    }

    public static long elapsedRealtimeDifference(android.app.time.UnixEpochTime unixEpochTime, android.app.time.UnixEpochTime unixEpochTime2) {
        return unixEpochTime.mElapsedRealtimeMillis - unixEpochTime2.mElapsedRealtimeMillis;
    }
}
