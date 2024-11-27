package android.service.autofill;

/* loaded from: classes3.dex */
public interface SavedDatasetsInfoCallback {
    public static final int ERROR_NEEDS_USER_ACTION = 2;
    public static final int ERROR_OTHER = 0;
    public static final int ERROR_UNSUPPORTED = 1;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface Error {
    }

    void onError(int i);

    void onSuccess(java.util.Set<android.service.autofill.SavedDatasetsInfo> set);
}
