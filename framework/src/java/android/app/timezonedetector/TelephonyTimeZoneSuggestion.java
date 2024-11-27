package android.app.timezonedetector;

/* loaded from: classes.dex */
public final class TelephonyTimeZoneSuggestion implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.app.timezonedetector.TelephonyTimeZoneSuggestion> CREATOR = new android.os.Parcelable.Creator<android.app.timezonedetector.TelephonyTimeZoneSuggestion>() { // from class: android.app.timezonedetector.TelephonyTimeZoneSuggestion.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.timezonedetector.TelephonyTimeZoneSuggestion createFromParcel(android.os.Parcel parcel) {
            return android.app.timezonedetector.TelephonyTimeZoneSuggestion.createFromParcel(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.timezonedetector.TelephonyTimeZoneSuggestion[] newArray(int i) {
            return new android.app.timezonedetector.TelephonyTimeZoneSuggestion[i];
        }
    };
    public static final int MATCH_TYPE_EMULATOR_ZONE_ID = 4;
    public static final int MATCH_TYPE_NA = 0;
    public static final int MATCH_TYPE_NETWORK_COUNTRY_AND_OFFSET = 3;
    public static final int MATCH_TYPE_NETWORK_COUNTRY_ONLY = 2;
    public static final int MATCH_TYPE_TEST_NETWORK_OFFSET_ONLY = 5;
    public static final int QUALITY_MULTIPLE_ZONES_WITH_DIFFERENT_OFFSETS = 3;
    public static final int QUALITY_MULTIPLE_ZONES_WITH_SAME_OFFSET = 2;
    public static final int QUALITY_NA = 0;
    public static final int QUALITY_SINGLE_ZONE = 1;
    private java.util.List<java.lang.String> mDebugInfo;
    private final int mMatchType;
    private final int mQuality;
    private final int mSlotIndex;
    private final java.lang.String mZoneId;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface MatchType {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface Quality {
    }

    public static android.app.timezonedetector.TelephonyTimeZoneSuggestion createEmptySuggestion(int i, java.lang.String str) {
        return new android.app.timezonedetector.TelephonyTimeZoneSuggestion.Builder(i).addDebugInfo(str).build();
    }

    private TelephonyTimeZoneSuggestion(android.app.timezonedetector.TelephonyTimeZoneSuggestion.Builder builder) {
        this.mSlotIndex = builder.mSlotIndex;
        this.mZoneId = builder.mZoneId;
        this.mMatchType = builder.mMatchType;
        this.mQuality = builder.mQuality;
        this.mDebugInfo = builder.mDebugInfo != null ? new java.util.ArrayList(builder.mDebugInfo) : null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static android.app.timezonedetector.TelephonyTimeZoneSuggestion createFromParcel(android.os.Parcel parcel) {
        android.app.timezonedetector.TelephonyTimeZoneSuggestion build = new android.app.timezonedetector.TelephonyTimeZoneSuggestion.Builder(parcel.readInt()).setZoneId(parcel.readString()).setMatchType(parcel.readInt()).setQuality(parcel.readInt()).build();
        java.util.ArrayList readArrayList = parcel.readArrayList(android.app.timezonedetector.TelephonyTimeZoneSuggestion.class.getClassLoader(), java.lang.String.class);
        if (readArrayList != null) {
            build.addDebugInfo(readArrayList);
        }
        return build;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mSlotIndex);
        parcel.writeString(this.mZoneId);
        parcel.writeInt(this.mMatchType);
        parcel.writeInt(this.mQuality);
        parcel.writeList(this.mDebugInfo);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public int getSlotIndex() {
        return this.mSlotIndex;
    }

    public java.lang.String getZoneId() {
        return this.mZoneId;
    }

    public int getMatchType() {
        return this.mMatchType;
    }

    public int getQuality() {
        return this.mQuality;
    }

    public java.util.List<java.lang.String> getDebugInfo() {
        return this.mDebugInfo == null ? java.util.Collections.emptyList() : java.util.Collections.unmodifiableList(this.mDebugInfo);
    }

    public void addDebugInfo(java.lang.String str) {
        if (this.mDebugInfo == null) {
            this.mDebugInfo = new java.util.ArrayList();
        }
        this.mDebugInfo.add(str);
    }

    public void addDebugInfo(java.util.List<java.lang.String> list) {
        if (this.mDebugInfo == null) {
            this.mDebugInfo = new java.util.ArrayList(list.size());
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
        android.app.timezonedetector.TelephonyTimeZoneSuggestion telephonyTimeZoneSuggestion = (android.app.timezonedetector.TelephonyTimeZoneSuggestion) obj;
        if (this.mSlotIndex == telephonyTimeZoneSuggestion.mSlotIndex && this.mMatchType == telephonyTimeZoneSuggestion.mMatchType && this.mQuality == telephonyTimeZoneSuggestion.mQuality && java.util.Objects.equals(this.mZoneId, telephonyTimeZoneSuggestion.mZoneId)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(this.mSlotIndex), this.mZoneId, java.lang.Integer.valueOf(this.mMatchType), java.lang.Integer.valueOf(this.mQuality));
    }

    public java.lang.String toString() {
        return "TelephonyTimeZoneSuggestion{mSlotIndex=" + this.mSlotIndex + ", mZoneId='" + this.mZoneId + android.text.format.DateFormat.QUOTE + ", mMatchType=" + this.mMatchType + ", mQuality=" + this.mQuality + ", mDebugInfo=" + this.mDebugInfo + '}';
    }

    public static final class Builder {
        private java.util.List<java.lang.String> mDebugInfo;
        private int mMatchType;
        private int mQuality;
        private final int mSlotIndex;
        private java.lang.String mZoneId;

        public Builder(int i) {
            this.mSlotIndex = i;
        }

        public android.app.timezonedetector.TelephonyTimeZoneSuggestion.Builder setZoneId(java.lang.String str) {
            this.mZoneId = str;
            return this;
        }

        public android.app.timezonedetector.TelephonyTimeZoneSuggestion.Builder setMatchType(int i) {
            this.mMatchType = i;
            return this;
        }

        public android.app.timezonedetector.TelephonyTimeZoneSuggestion.Builder setQuality(int i) {
            this.mQuality = i;
            return this;
        }

        public android.app.timezonedetector.TelephonyTimeZoneSuggestion.Builder addDebugInfo(java.lang.String str) {
            if (this.mDebugInfo == null) {
                this.mDebugInfo = new java.util.ArrayList();
            }
            this.mDebugInfo.add(str);
            return this;
        }

        void validate() {
            int i = this.mQuality;
            int i2 = this.mMatchType;
            if (this.mZoneId == null) {
                if (i != 0 || i2 != 0) {
                    throw new java.lang.RuntimeException("Invalid quality or match type for null zone ID. quality=" + i + ", matchType=" + i2);
                }
            } else {
                boolean z = i == 1 || i == 2 || i == 3;
                boolean z2 = i2 == 2 || i2 == 3 || i2 == 4 || i2 == 5;
                if (!z || !z2) {
                    throw new java.lang.RuntimeException("Invalid quality or match type with zone ID. quality=" + i + ", matchType=" + i2);
                }
            }
        }

        public android.app.timezonedetector.TelephonyTimeZoneSuggestion build() {
            validate();
            return new android.app.timezonedetector.TelephonyTimeZoneSuggestion(this);
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public static android.app.timezonedetector.TelephonyTimeZoneSuggestion parseCommandLineArg(android.os.ShellCommand shellCommand) throws java.lang.IllegalArgumentException {
        char c;
        java.lang.Integer num = null;
        java.lang.String str = null;
        java.lang.Integer num2 = null;
        java.lang.Integer num3 = null;
        while (true) {
            java.lang.String nextArg = shellCommand.getNextArg();
            if (nextArg != null) {
                switch (nextArg.hashCode()) {
                    case -174375148:
                        if (nextArg.equals("--match_type")) {
                            c = 3;
                            break;
                        }
                        c = 65535;
                        break;
                    case 1274807534:
                        if (nextArg.equals("--zone_id")) {
                            c = 1;
                            break;
                        }
                        c = 65535;
                        break;
                    case 2037196639:
                        if (nextArg.equals("--quality")) {
                            c = 2;
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
                        str = shellCommand.getNextArgRequired();
                        break;
                    case 2:
                        num2 = java.lang.Integer.valueOf(parseQualityCommandLineArg(shellCommand.getNextArgRequired()));
                        break;
                    case 3:
                        num3 = java.lang.Integer.valueOf(parseMatchTypeCommandLineArg(shellCommand.getNextArgRequired()));
                        break;
                    default:
                        throw new java.lang.IllegalArgumentException("Unknown option: " + nextArg);
                }
            } else {
                if (num == null) {
                    throw new java.lang.IllegalArgumentException("No slotIndex specified.");
                }
                android.app.timezonedetector.TelephonyTimeZoneSuggestion.Builder builder = new android.app.timezonedetector.TelephonyTimeZoneSuggestion.Builder(num.intValue());
                if (!android.text.TextUtils.isEmpty(str) && !android.telecom.Logging.Session.SESSION_SEPARATION_CHAR_CHILD.equals(str)) {
                    builder.setZoneId(str);
                }
                if (num2 != null) {
                    builder.setQuality(num2.intValue());
                }
                if (num3 != null) {
                    builder.setMatchType(num3.intValue());
                }
                builder.addDebugInfo("Command line injection");
                return builder.build();
            }
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private static int parseQualityCommandLineArg(java.lang.String str) {
        char c;
        switch (str.hashCode()) {
            case -902265784:
                if (str.equals("single")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case -650306251:
                if (str.equals("multiple_same")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case 1611832522:
                if (str.equals("multiple_different")) {
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
                return 1;
            case 1:
                return 2;
            case 2:
                return 3;
            default:
                throw new java.lang.IllegalArgumentException("Unrecognized quality: " + str);
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private static int parseMatchTypeCommandLineArg(java.lang.String str) {
        char c;
        switch (str.hashCode()) {
            case -1592694013:
                if (str.equals("country_with_offset")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case 556438401:
                if (str.equals(android.content.Context.TEST_NETWORK_SERVICE)) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case 957831062:
                if (str.equals(android.speech.tts.TextToSpeech.Engine.KEY_PARAM_COUNTRY)) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case 1336193813:
                if (str.equals("emulator")) {
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
                return 4;
            case 1:
                return 3;
            case 2:
                return 2;
            case 3:
                return 5;
            default:
                throw new java.lang.IllegalArgumentException("Unrecognized match_type: " + str);
        }
    }

    public static void printCommandLineOpts(java.io.PrintWriter printWriter) {
        printWriter.println("Telephony suggestion options:");
        printWriter.println("  --slot_index <number>");
        printWriter.println("  To withdraw a previous suggestion:");
        printWriter.println("    [--zone_id \"_\"]");
        printWriter.println("  To make a new suggestion:");
        printWriter.println("    --zone_id <Olson ID>");
        printWriter.println("    --quality <single|multiple_same|multiple_different>");
        printWriter.println("    --match_type <emulator|country_with_offset|country|test_network>");
        printWriter.println();
        printWriter.println("See " + android.app.timezonedetector.TelephonyTimeZoneSuggestion.class.getName() + " for more information");
    }
}
