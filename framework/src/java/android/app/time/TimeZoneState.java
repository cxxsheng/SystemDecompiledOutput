package android.app.time;

@android.annotation.SystemApi
/* loaded from: classes.dex */
public final class TimeZoneState implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.app.time.TimeZoneState> CREATOR = new android.os.Parcelable.Creator<android.app.time.TimeZoneState>() { // from class: android.app.time.TimeZoneState.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.time.TimeZoneState createFromParcel(android.os.Parcel parcel) {
            return android.app.time.TimeZoneState.createFromParcel(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.time.TimeZoneState[] newArray(int i) {
            return new android.app.time.TimeZoneState[i];
        }
    };
    private final java.lang.String mId;
    private final boolean mUserShouldConfirmId;

    public TimeZoneState(java.lang.String str, boolean z) {
        this.mId = (java.lang.String) java.util.Objects.requireNonNull(str);
        this.mUserShouldConfirmId = z;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static android.app.time.TimeZoneState createFromParcel(android.os.Parcel parcel) {
        return new android.app.time.TimeZoneState(parcel.readString8(), parcel.readBoolean());
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeString8(this.mId);
        parcel.writeBoolean(this.mUserShouldConfirmId);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public static android.app.time.TimeZoneState parseCommandLineArgs(android.os.ShellCommand shellCommand) {
        char c;
        java.lang.String str = null;
        java.lang.Boolean bool = null;
        while (true) {
            java.lang.String nextArg = shellCommand.getNextArg();
            if (nextArg != null) {
                switch (nextArg.hashCode()) {
                    case -1988134094:
                        if (nextArg.equals("--user_should_confirm_id")) {
                            c = 1;
                            break;
                        }
                        c = 65535;
                        break;
                    case 1274807534:
                        if (nextArg.equals("--zone_id")) {
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
                        str = shellCommand.getNextArgRequired();
                        break;
                    case 1:
                        bool = java.lang.Boolean.valueOf(java.lang.Boolean.parseBoolean(shellCommand.getNextArgRequired()));
                        break;
                    default:
                        throw new java.lang.IllegalArgumentException("Unknown option: " + nextArg);
                }
            } else {
                if (str == null) {
                    throw new java.lang.IllegalArgumentException("No zoneId specified.");
                }
                if (bool == null) {
                    throw new java.lang.IllegalArgumentException("No userShouldConfirmId specified.");
                }
                return new android.app.time.TimeZoneState(str, bool.booleanValue());
            }
        }
    }

    public static void printCommandLineOpts(java.io.PrintWriter printWriter) {
        printWriter.println("TimeZoneState options:");
        printWriter.println("  --zone_id {<Olson ID>}");
        printWriter.println("  --user_should_confirm_id {true|false}");
        printWriter.println();
        printWriter.println("See " + android.app.time.TimeZoneState.class.getName() + " for more information");
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public java.lang.String getId() {
        return this.mId;
    }

    public boolean getUserShouldConfirmId() {
        return this.mUserShouldConfirmId;
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        android.app.time.TimeZoneState timeZoneState = (android.app.time.TimeZoneState) obj;
        if (java.util.Objects.equals(this.mId, timeZoneState.mId) && this.mUserShouldConfirmId == timeZoneState.mUserShouldConfirmId) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return java.util.Objects.hash(this.mId, java.lang.Boolean.valueOf(this.mUserShouldConfirmId));
    }

    public java.lang.String toString() {
        return "TimeZoneState{mZoneId=" + this.mId + ", mUserShouldConfirmId=" + this.mUserShouldConfirmId + '}';
    }
}
