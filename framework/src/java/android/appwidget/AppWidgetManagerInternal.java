package android.appwidget;

/* loaded from: classes.dex */
public abstract class AppWidgetManagerInternal {
    public abstract void applyResourceOverlaysToWidgets(java.util.Set<java.lang.String> set, int i, boolean z);

    public abstract android.util.ArraySet<java.lang.String> getHostedWidgetPackages(int i);

    public abstract void unlockUser(int i);
}
