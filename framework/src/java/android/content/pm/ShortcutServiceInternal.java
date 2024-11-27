package android.content.pm;

/* loaded from: classes.dex */
public abstract class ShortcutServiceInternal {

    public interface ShortcutChangeListener {
        void onShortcutChanged(java.lang.String str, int i);
    }

    public abstract void addListener(android.content.pm.ShortcutServiceInternal.ShortcutChangeListener shortcutChangeListener);

    public abstract void addShortcutChangeCallback(android.content.pm.LauncherApps.ShortcutChangeCallback shortcutChangeCallback);

    public abstract boolean areShortcutsSupportedOnHomeScreen(int i);

    public abstract void cacheShortcuts(int i, java.lang.String str, java.lang.String str2, java.util.List<java.lang.String> list, int i2, int i3);

    public abstract android.content.Intent[] createShortcutIntents(int i, java.lang.String str, java.lang.String str2, java.lang.String str3, int i2, int i3, int i4);

    public abstract void createShortcutIntentsAsync(int i, java.lang.String str, java.lang.String str2, java.lang.String str3, int i2, int i3, int i4, com.android.internal.infra.AndroidFuture<android.content.Intent[]> androidFuture);

    public abstract java.util.List<android.content.pm.ShortcutManager.ShareShortcutInfo> getShareTargets(java.lang.String str, android.content.IntentFilter intentFilter, int i);

    public abstract android.os.ParcelFileDescriptor getShortcutIconFd(int i, java.lang.String str, java.lang.String str2, java.lang.String str3, int i2);

    public abstract void getShortcutIconFdAsync(int i, java.lang.String str, java.lang.String str2, java.lang.String str3, int i2, com.android.internal.infra.AndroidFuture<android.os.ParcelFileDescriptor> androidFuture);

    public abstract int getShortcutIconResId(int i, java.lang.String str, java.lang.String str2, java.lang.String str3, int i2);

    public abstract java.lang.String getShortcutIconUri(int i, java.lang.String str, java.lang.String str2, java.lang.String str3, int i2);

    public abstract void getShortcutIconUriAsync(int i, java.lang.String str, java.lang.String str2, java.lang.String str3, int i2, com.android.internal.infra.AndroidFuture<java.lang.String> androidFuture);

    public abstract java.lang.String getShortcutStartingThemeResName(int i, java.lang.String str, java.lang.String str2, java.lang.String str3, int i2);

    public abstract java.util.List<android.content.pm.ShortcutInfo> getShortcuts(int i, java.lang.String str, long j, java.lang.String str2, java.util.List<java.lang.String> list, java.util.List<android.content.LocusId> list2, android.content.ComponentName componentName, int i2, int i3, int i4, int i5);

    public abstract void getShortcutsAsync(int i, java.lang.String str, long j, java.lang.String str2, java.util.List<java.lang.String> list, java.util.List<android.content.LocusId> list2, android.content.ComponentName componentName, int i2, int i3, int i4, int i5, com.android.internal.infra.AndroidFuture<java.util.List<android.content.pm.ShortcutInfo>> androidFuture);

    public abstract boolean hasShortcutHostPermission(int i, java.lang.String str, int i2, int i3);

    public abstract boolean isForegroundDefaultLauncher(java.lang.String str, int i);

    public abstract boolean isPinnedByCaller(int i, java.lang.String str, java.lang.String str2, java.lang.String str3, int i2);

    public abstract boolean isRequestPinItemSupported(int i, int i2);

    public abstract boolean isSharingShortcut(int i, java.lang.String str, java.lang.String str2, java.lang.String str3, int i2, android.content.IntentFilter intentFilter);

    public abstract void pinShortcuts(int i, java.lang.String str, java.lang.String str2, java.util.List<java.lang.String> list, int i2);

    public abstract boolean requestPinAppWidget(java.lang.String str, android.appwidget.AppWidgetProviderInfo appWidgetProviderInfo, android.os.Bundle bundle, android.content.IntentSender intentSender, int i);

    public abstract void setShortcutHostPackage(java.lang.String str, java.lang.String str2, int i);

    public abstract void uncacheShortcuts(int i, java.lang.String str, java.lang.String str2, java.util.List<java.lang.String> list, int i2, int i3);
}
