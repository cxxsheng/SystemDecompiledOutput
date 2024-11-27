package android.app.time;

@android.annotation.SystemApi
/* loaded from: classes.dex */
public final class TimeState implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.app.time.TimeState> CREATOR = new android.os.Parcelable.Creator<android.app.time.TimeState>() { // from class: android.app.time.TimeState.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.time.TimeState createFromParcel(android.os.Parcel parcel) {
            return android.app.time.TimeState.createFromParcel(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.time.TimeState[] newArray(int i) {
            return new android.app.time.TimeState[i];
        }
    };
    private final android.app.time.UnixEpochTime mUnixEpochTime;
    private final boolean mUserShouldConfirmTime;

    public TimeState(android.app.time.UnixEpochTime unixEpochTime, boolean z) {
        this.mUnixEpochTime = (android.app.time.UnixEpochTime) java.util.Objects.requireNonNull(unixEpochTime);
        this.mUserShouldConfirmTime = z;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static android.app.time.TimeState createFromParcel(android.os.Parcel parcel) {
        return new android.app.time.TimeState((android.app.time.UnixEpochTime) parcel.readParcelable(null, android.app.time.UnixEpochTime.class), parcel.readBoolean());
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeParcelable(this.mUnixEpochTime, 0);
        parcel.writeBoolean(this.mUserShouldConfirmTime);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public static android.app.time.TimeState parseCommandLineArgs(android.os.ShellCommand shellCommand) {
        char c;
        java.lang.Long l = null;
        java.lang.Long l2 = null;
        java.lang.Boolean bool = null;
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
                    case 663918372:
                        if (nextArg.equals("--user_should_confirm_time")) {
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
                        l = java.lang.Long.valueOf(java.lang.Long.parseLong(shellCommand.getNextArgRequired()));
                        break;
                    case 1:
                        l2 = java.lang.Long.valueOf(java.lang.Long.parseLong(shellCommand.getNextArgRequired()));
                        break;
                    case 2:
                        bool = java.lang.Boolean.valueOf(java.lang.Boolean.parseBoolean(shellCommand.getNextArgRequired()));
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
                if (bool == null) {
                    throw new java.lang.IllegalArgumentException("No userShouldConfirmTime specified.");
                }
                return new android.app.time.TimeState(new android.app.time.UnixEpochTime(l.longValue(), l2.longValue()), bool.booleanValue());
            }
        }
    }

    public static void printCommandLineOpts(java.io.PrintWriter printWriter) {
        printWriter.println("TimeState options:");
        printWriter.println("  --elapsed_realtime <elapsed realtime millis>");
        printWriter.println("  --unix_epoch_time <Unix epoch time millis>");
        printWriter.println("  --user_should_confirm_time {true|false}");
        printWriter.println();
        printWriter.println("See " + android.app.time.TimeState.class.getName() + " for more information");
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public android.app.time.UnixEpochTime getUnixEpochTime() {
        return this.mUnixEpochTime;
    }

    public boolean getUserShouldConfirmTime() {
        return this.mUserShouldConfirmTime;
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        android.app.time.TimeState timeState = (android.app.time.TimeState) obj;
        if (java.util.Objects.equals(this.mUnixEpochTime, timeState.mUnixEpochTime) && this.mUserShouldConfirmTime == timeState.mUserShouldConfirmTime) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return java.util.Objects.hash(this.mUnixEpochTime, java.lang.Boolean.valueOf(this.mUserShouldConfirmTime));
    }

    public java.lang.String toString() {
        return "TimeState{mUnixEpochTime=" + this.mUnixEpochTime + ", mUserShouldConfirmTime=" + this.mUserShouldConfirmTime + '}';
    }
}
