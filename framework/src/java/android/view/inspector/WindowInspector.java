package android.view.inspector;

/* loaded from: classes4.dex */
public final class WindowInspector {
    private WindowInspector() {
    }

    public static java.util.List<android.view.View> getGlobalWindowViews() {
        return android.view.WindowManagerGlobal.getInstance().getWindowViews();
    }
}
