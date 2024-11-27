package android.app.timedetector;

/* loaded from: classes.dex */
public final class TelephonyTimeSuggestion implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.app.timedetector.TelephonyTimeSuggestion> CREATOR = new android.os.Parcelable.Creator<android.app.timedetector.TelephonyTimeSuggestion>() { // from class: android.app.timedetector.TelephonyTimeSuggestion.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.timedetector.TelephonyTimeSuggestion createFromParcel(android.os.Parcel parcel) {
            return android.app.timedetector.TelephonyTimeSuggestion.createFromParcel(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.timedetector.TelephonyTimeSuggestion[] newArray(int i) {
            return new android.app.timedetector.TelephonyTimeSuggestion[i];
        }
    };
    private java.util.ArrayList<java.lang.String> mDebugInfo;
    private final int mSlotIndex;
    private final android.app.time.UnixEpochTime mUnixEpochTime;

    private TelephonyTimeSuggestion(android.app.timedetector.TelephonyTimeSuggestion.Builder builder) {
        this.mSlotIndex = builder.mSlotIndex;
        this.mUnixEpochTime = builder.mUnixEpochTime;
        this.mDebugInfo = builder.mDebugInfo != null ? new java.util.ArrayList<>(builder.mDebugInfo) : null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static android.app.timedetector.TelephonyTimeSuggestion createFromParcel(android.os.Parcel parcel) {
        int readInt = parcel.readInt();
        android.app.timedetector.TelephonyTimeSuggestion build = new android.app.timedetector.TelephonyTimeSuggestion.Builder(readInt).setUnixEpochTime((android.app.time.UnixEpochTime) parcel.readParcelable(null, android.app.time.UnixEpochTime.class)).build();
        java.util.ArrayList readArrayList = parcel.readArrayList(null, java.lang.String.class);
        if (readArrayList != null) {
            build.addDebugInfo(readArrayList);
        }
        return build;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public static android.app.timedetector.TelephonyTimeSuggestion parseCommandLineArg(android.os.ShellCommand shellCommand) throws java.lang.IllegalArgumentException {
        char c;
        java.lang.Integer num = null;
        java.lang.Long l = null;
        java.lang.Long l2 = null;
        while (true) {
            java.lang.String nextArg = shellCommand.getNextArg();
            if (nextArg != null) {
                switch (nextArg.hashCode()) {
                    case 16142561:
                        if (nextArg.equals("--reference_time")) {
                            c = 1;
                            break;
                        }
                        c = 65535;
                        break;
                    case 48316014:
                        if (nextArg.equals("--elapsed_realtime")) {
                            c = 2;
                            break;
                        }
                        c = 65535;
                        break;
                    case 410278458:
                        if (nextArg.equals("--unix_epoch_time")) {
                            c = 3;
                            break;
                        }
                        c = 65535;
                        break;
                    case 2069298417:
                        if (nextArg.equals("--slot_index")) {
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
                        num = java.lang.Integer.valueOf(java.lang.Integer.parseInt(shellCommand.getNextArgRequired()));
                        break;
                    case 1:
                    case 2:
                        l = java.lang.Long.valueOf(java.lang.Long.parseLong(shellCommand.getNextArgRequired()));
                        break;
                    case 3:
                        l2 = java.lang.Long.valueOf(java.lang.Long.parseLong(shellCommand.getNextArgRequired()));
                        break;
                    default:
                        throw new java.lang.IllegalArgumentException("Unknown option: " + nextArg);
                }
            } else {
                if (num == null) {
                    throw new java.lang.IllegalArgumentException("No slotIndex specified.");
                }
                if (l == null) {
                    throw new java.lang.IllegalArgumentException("No elapsedRealtimeMillis specified.");
                }
                if (l2 == null) {
                    throw new java.lang.IllegalArgumentException("No unixEpochTimeMillis specified.");
                }
                return new android.app.timedetector.TelephonyTimeSuggestion.Builder(num.intValue()).setUnixEpochTime(new android.app.time.UnixEpochTime(l.longValue(), l2.longValue())).addDebugInfo("Command line injection").build();
            }
        }
    }

    public static void printCommandLineOpts(java.io.PrintWriter printWriter) {
        printWriter.println("Telephony suggestion options:");
        printWriter.println("  --slot_index <number>");
        printWriter.println("  --elapsed_realtime <elapsed realtime millis>");
        printWriter.println("  --unix_epoch_time <Unix epoch time millis>");
        printWriter.println();
        printWriter.println("See " + android.app.timedetector.TelephonyTimeSuggestion.class.getName() + " for more information");
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mSlotIndex);
        parcel.writeParcelable(this.mUnixEpochTime, 0);
        parcel.writeList(this.mDebugInfo);
    }

    public int getSlotIndex() {
        return this.mSlotIndex;
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
        android.app.timedetector.TelephonyTimeSuggestion telephonyTimeSuggestion = (android.app.timedetector.TelephonyTimeSuggestion) obj;
        if (this.mSlotIndex == telephonyTimeSuggestion.mSlotIndex && java.util.Objects.equals(this.mUnixEpochTime, telephonyTimeSuggestion.mUnixEpochTime)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(this.mSlotIndex), this.mUnixEpochTime);
    }

    public java.lang.String toString() {
        return "TelephonyTimeSuggestion{mSlotIndex='" + this.mSlotIndex + android.text.format.DateFormat.QUOTE + ", mUnixEpochTime=" + this.mUnixEpochTime + ", mDebugInfo=" + this.mDebugInfo + '}';
    }

    public static final class Builder {
        private java.util.List<java.lang.String> mDebugInfo;
        private final int mSlotIndex;
        private android.app.time.UnixEpochTime mUnixEpochTime;

        public Builder(int i) {
            this.mSlotIndex = i;
        }

        public android.app.timedetector.TelephonyTimeSuggestion.Builder setUnixEpochTime(android.app.time.UnixEpochTime unixEpochTime) {
            this.mUnixEpochTime = unixEpochTime;
            return this;
        }

        public android.app.timedetector.TelephonyTimeSuggestion.Builder addDebugInfo(java.lang.String str) {
            if (this.mDebugInfo == null) {
                this.mDebugInfo = new java.util.ArrayList();
            }
            this.mDebugInfo.add(str);
            return this;
        }

        public android.app.timedetector.TelephonyTimeSuggestion build() {
            return new android.app.timedetector.TelephonyTimeSuggestion(this);
        }
    }
}
