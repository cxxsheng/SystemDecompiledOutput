package android.window;

/* loaded from: classes4.dex */
public interface WindowProvider {
    public static final java.lang.String KEY_IS_WINDOW_PROVIDER_SERVICE = "android.windowContext.isWindowProviderService";

    android.os.Bundle getWindowContextOptions();

    android.os.IBinder getWindowContextToken();

    int getWindowType();
}
