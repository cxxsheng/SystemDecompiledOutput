package android.os;

/* loaded from: classes3.dex */
public interface WearModeManagerInternal {
    public static final java.lang.String OFFBODY_STATE_ID = "off_body";
    public static final java.lang.String QUICK_DOZE_REQUEST_IDENTIFIER = "quick_doze_request";

    @java.lang.annotation.Target({java.lang.annotation.ElementType.TYPE_USE})
    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface Identifier {
    }

    <T> void addActiveStateChangeListener(java.lang.String str, java.util.concurrent.Executor executor, java.util.function.Consumer<T> consumer);
}
