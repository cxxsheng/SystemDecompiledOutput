package com.android.server.timedetector;

/* loaded from: classes2.dex */
public interface TimeDetectorStrategy extends com.android.server.timezonedetector.Dumpable {
    public static final int ORIGIN_EXTERNAL = 5;
    public static final int ORIGIN_GNSS = 4;
    public static final int ORIGIN_MANUAL = 2;
    public static final int ORIGIN_NETWORK = 3;
    public static final int ORIGIN_TELEPHONY = 1;

    @java.lang.annotation.Target({java.lang.annotation.ElementType.TYPE_USE, java.lang.annotation.ElementType.TYPE_PARAMETER})
    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface Origin {
    }

    void addChangeListener(@android.annotation.NonNull com.android.server.timezonedetector.StateChangeListener stateChangeListener);

    void addNetworkTimeUpdateListener(@android.annotation.NonNull com.android.server.timezonedetector.StateChangeListener stateChangeListener);

    void clearLatestNetworkSuggestion();

    boolean confirmTime(@android.annotation.NonNull android.app.time.UnixEpochTime unixEpochTime);

    android.app.time.TimeCapabilitiesAndConfig getCapabilitiesAndConfig(int i, boolean z);

    @android.annotation.Nullable
    com.android.server.timedetector.NetworkTimeSuggestion getLatestNetworkSuggestion();

    @android.annotation.NonNull
    android.app.time.TimeState getTimeState();

    void setTimeState(@android.annotation.NonNull android.app.time.TimeState timeState);

    void suggestExternalTime(@android.annotation.NonNull android.app.time.ExternalTimeSuggestion externalTimeSuggestion);

    void suggestGnssTime(@android.annotation.NonNull com.android.server.timedetector.GnssTimeSuggestion gnssTimeSuggestion);

    boolean suggestManualTime(int i, @android.annotation.NonNull android.app.timedetector.ManualTimeSuggestion manualTimeSuggestion, boolean z);

    void suggestNetworkTime(@android.annotation.NonNull com.android.server.timedetector.NetworkTimeSuggestion networkTimeSuggestion);

    void suggestTelephonyTime(@android.annotation.NonNull android.app.timedetector.TelephonyTimeSuggestion telephonyTimeSuggestion);

    boolean updateConfiguration(int i, @android.annotation.NonNull android.app.time.TimeConfiguration timeConfiguration, boolean z);

    @android.annotation.NonNull
    static java.lang.String originToString(int i) {
        switch (i) {
            case 1:
                return "telephony";
            case 2:
                return "manual";
            case 3:
                return "network";
            case 4:
                return "gnss";
            case 5:
                return "external";
            default:
                throw new java.lang.IllegalArgumentException("origin=" + i);
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x003c, code lost:
    
        if (r6.equals("manual") != false) goto L25;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    static int stringToOrigin(java.lang.String str) {
        char c = 0;
        com.android.internal.util.Preconditions.checkArgument(str != null);
        switch (str.hashCode()) {
            case -1820761141:
                if (str.equals("external")) {
                    c = 4;
                    break;
                }
                c = 65535;
                break;
            case -1081415738:
                break;
            case 3177863:
                if (str.equals("gnss")) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case 783201304:
                if (str.equals("telephony")) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case 1843485230:
                if (str.equals("network")) {
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
                return 2;
            case 1:
                return 3;
            case 2:
                return 1;
            case 3:
                return 4;
            case 4:
                return 5;
            default:
                throw new java.lang.IllegalArgumentException("originString=" + str);
        }
    }
}
