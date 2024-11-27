package android.view;

/* loaded from: classes4.dex */
public class EventLogTags {
    public static final int IMF_IME_ANIM_CANCEL = 32008;
    public static final int IMF_IME_ANIM_FINISH = 32007;
    public static final int IMF_IME_ANIM_START = 32006;
    public static final int IMF_IME_REMOTE_ANIM_CANCEL = 32011;
    public static final int IMF_IME_REMOTE_ANIM_END = 32010;
    public static final int IMF_IME_REMOTE_ANIM_START = 32009;
    public static final int VIEWROOT_DRAW_EVENT = 60004;
    public static final int VIEW_ENQUEUE_INPUT_EVENT = 62002;

    private EventLogTags() {
    }

    public static void writeImfImeAnimStart(java.lang.String str, int i, float f, java.lang.String str2, java.lang.String str3, java.lang.String str4) {
        android.util.EventLog.writeEvent(IMF_IME_ANIM_START, str, java.lang.Integer.valueOf(i), java.lang.Float.valueOf(f), str2, str3, str4);
    }

    public static void writeImfImeAnimFinish(java.lang.String str, int i, float f, int i2, java.lang.String str2) {
        android.util.EventLog.writeEvent(IMF_IME_ANIM_FINISH, str, java.lang.Integer.valueOf(i), java.lang.Float.valueOf(f), java.lang.Integer.valueOf(i2), str2);
    }

    public static void writeImfImeAnimCancel(java.lang.String str, int i, java.lang.String str2) {
        android.util.EventLog.writeEvent(IMF_IME_ANIM_CANCEL, str, java.lang.Integer.valueOf(i), str2);
    }

    public static void writeImfImeRemoteAnimStart(java.lang.String str, int i, int i2, float f, float f2, float f3, java.lang.String str2, java.lang.String str3, java.lang.String str4, java.lang.String str5) {
        android.util.EventLog.writeEvent(IMF_IME_REMOTE_ANIM_START, str, java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2), java.lang.Float.valueOf(f), java.lang.Float.valueOf(f2), java.lang.Float.valueOf(f3), str2, str3, str4, str5);
    }

    public static void writeImfImeRemoteAnimEnd(java.lang.String str, int i, int i2, float f, java.lang.String str2, java.lang.String str3, java.lang.String str4, java.lang.String str5) {
        android.util.EventLog.writeEvent(IMF_IME_REMOTE_ANIM_END, str, java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2), java.lang.Float.valueOf(f), str2, str3, str4, str5);
    }

    public static void writeImfImeRemoteAnimCancel(java.lang.String str, int i, java.lang.String str2) {
        android.util.EventLog.writeEvent(IMF_IME_REMOTE_ANIM_CANCEL, str, java.lang.Integer.valueOf(i), str2);
    }

    public static void writeViewEnqueueInputEvent(java.lang.String str, java.lang.String str2) {
        android.util.EventLog.writeEvent(VIEW_ENQUEUE_INPUT_EVENT, str, str2);
    }

    public static void writeViewrootDrawEvent(java.lang.String str, java.lang.String str2) {
        android.util.EventLog.writeEvent(VIEWROOT_DRAW_EVENT, str, str2);
    }
}
