package android.speech.tts;

/* loaded from: classes3.dex */
public class EventLogTags {
    public static final int TTS_SPEAK_FAILURE = 76002;
    public static final int TTS_SPEAK_SUCCESS = 76001;
    public static final int TTS_V2_SPEAK_FAILURE = 76004;
    public static final int TTS_V2_SPEAK_SUCCESS = 76003;

    private EventLogTags() {
    }

    public static void writeTtsSpeakSuccess(java.lang.String str, int i, int i2, int i3, java.lang.String str2, int i4, int i5, long j, long j2, long j3) {
        android.util.EventLog.writeEvent(TTS_SPEAK_SUCCESS, str, java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2), java.lang.Integer.valueOf(i3), str2, java.lang.Integer.valueOf(i4), java.lang.Integer.valueOf(i5), java.lang.Long.valueOf(j), java.lang.Long.valueOf(j2), java.lang.Long.valueOf(j3));
    }

    public static void writeTtsSpeakFailure(java.lang.String str, int i, int i2, int i3, java.lang.String str2, int i4, int i5) {
        android.util.EventLog.writeEvent(TTS_SPEAK_FAILURE, str, java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2), java.lang.Integer.valueOf(i3), str2, java.lang.Integer.valueOf(i4), java.lang.Integer.valueOf(i5));
    }

    public static void writeTtsV2SpeakSuccess(java.lang.String str, int i, int i2, int i3, java.lang.String str2, long j, long j2, long j3) {
        android.util.EventLog.writeEvent(TTS_V2_SPEAK_SUCCESS, str, java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2), java.lang.Integer.valueOf(i3), str2, java.lang.Long.valueOf(j), java.lang.Long.valueOf(j2), java.lang.Long.valueOf(j3));
    }

    public static void writeTtsV2SpeakFailure(java.lang.String str, int i, int i2, int i3, java.lang.String str2, int i4) {
        android.util.EventLog.writeEvent(TTS_V2_SPEAK_FAILURE, str, java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2), java.lang.Integer.valueOf(i3), str2, java.lang.Integer.valueOf(i4));
    }
}
