package com.android.server.voiceinteraction;

/* loaded from: classes2.dex */
public final class HotwordMetricsLogger {
    private static final int AUDIO_EGRESS_DSP_DETECTOR = 1;
    private static final int AUDIO_EGRESS_NORMAL_DETECTOR = 0;
    private static final int AUDIO_EGRESS_SOFTWARE_DETECTOR = 2;
    private static final int METRICS_INIT_DETECTOR_DSP = 1;
    private static final int METRICS_INIT_DETECTOR_SOFTWARE = 2;
    private static final int METRICS_INIT_NORMAL_DETECTOR = 0;

    private HotwordMetricsLogger() {
    }

    public static void writeDetectorCreateEvent(int i, boolean z, int i2) {
        com.android.internal.util.FrameworkStatsLog.write(com.android.internal.util.FrameworkStatsLog.HOTWORD_DETECTOR_CREATE_REQUESTED, getCreateMetricsDetectorType(i), z, i2);
    }

    public static void writeServiceInitResultEvent(int i, int i2, int i3) {
        com.android.internal.util.FrameworkStatsLog.write(com.android.internal.util.FrameworkStatsLog.HOTWORD_DETECTION_SERVICE_INIT_RESULT_REPORTED, getInitMetricsDetectorType(i), i2, i3);
    }

    public static void writeServiceRestartEvent(int i, int i2, int i3) {
        com.android.internal.util.FrameworkStatsLog.write(com.android.internal.util.FrameworkStatsLog.HOTWORD_DETECTION_SERVICE_RESTARTED, getRestartMetricsDetectorType(i), i2, i3);
    }

    public static void writeKeyphraseTriggerEvent(int i, int i2, int i3) {
        com.android.internal.util.FrameworkStatsLog.write(com.android.internal.util.FrameworkStatsLog.HOTWORD_DETECTOR_KEYPHRASE_TRIGGERED, getKeyphraseMetricsDetectorType(i), i2, i3);
    }

    public static void writeDetectorEvent(int i, int i2, int i3) {
        com.android.internal.util.FrameworkStatsLog.write(com.android.internal.util.FrameworkStatsLog.HOTWORD_DETECTOR_EVENTS, getDetectorMetricsDetectorType(i), i2, i3);
    }

    public static void writeAudioEgressEvent(int i, int i2, int i3, int i4, int i5, int i6) {
        com.android.internal.util.FrameworkStatsLog.write(com.android.internal.util.FrameworkStatsLog.HOTWORD_AUDIO_EGRESS_EVENT_REPORTED, getAudioEgressDetectorType(i), i2, i3, i4, i5, i6);
    }

    public static void writeHotwordDataEgressSize(int i, long j, int i2, int i3) {
        com.android.internal.util.FrameworkStatsLog.write(com.android.internal.util.FrameworkStatsLog.HOTWORD_EGRESS_SIZE_ATOM_REPORTED, i, j, getHotwordEventEgressSizeDetectorType(i2), i3);
    }

    public static void startHotwordTriggerToUiLatencySession(android.content.Context context, java.lang.String str) {
        com.android.internal.util.LatencyTracker.getInstance(context).onActionStart(19, str);
    }

    public static void stopHotwordTriggerToUiLatencySession(android.content.Context context) {
        com.android.internal.util.LatencyTracker.getInstance(context).onActionEnd(19);
    }

    public static void cancelHotwordTriggerToUiLatencySession(android.content.Context context) {
        com.android.internal.util.LatencyTracker.getInstance(context).onActionCancel(19);
    }

    private static int getCreateMetricsDetectorType(int i) {
        switch (i) {
            case 1:
                return 1;
            case 2:
                return 2;
            default:
                return 0;
        }
    }

    private static int getRestartMetricsDetectorType(int i) {
        switch (i) {
            case 1:
                return 1;
            case 2:
                return 2;
            default:
                return 0;
        }
    }

    private static int getInitMetricsDetectorType(int i) {
        switch (i) {
            case 1:
                return 1;
            case 2:
                return 2;
            default:
                return 0;
        }
    }

    private static int getKeyphraseMetricsDetectorType(int i) {
        switch (i) {
            case 1:
                return 1;
            case 2:
                return 2;
            default:
                return 0;
        }
    }

    private static int getDetectorMetricsDetectorType(int i) {
        switch (i) {
            case 1:
                return 1;
            case 2:
                return 2;
            default:
                return 0;
        }
    }

    private static int getAudioEgressDetectorType(int i) {
        switch (i) {
            case 1:
                return 1;
            case 2:
                return 2;
            default:
                return 0;
        }
    }

    private static int getHotwordEventEgressSizeDetectorType(int i) {
        switch (i) {
            case 1:
                return 1;
            case 2:
                return 2;
            default:
                return 0;
        }
    }
}
