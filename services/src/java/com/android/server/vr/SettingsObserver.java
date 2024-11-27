package com.android.server.vr;

/* loaded from: classes2.dex */
public class SettingsObserver {
    private final android.database.ContentObserver mContentObserver;
    private final java.lang.String mSecureSettingName;
    private final android.content.BroadcastReceiver mSettingRestoreReceiver;
    private final java.util.Set<com.android.server.vr.SettingsObserver.SettingChangeListener> mSettingsListeners = new android.util.ArraySet();

    public interface SettingChangeListener {
        void onSettingChanged();

        void onSettingRestored(java.lang.String str, java.lang.String str2, int i);
    }

    private SettingsObserver(@android.annotation.NonNull android.content.Context context, @android.annotation.NonNull android.os.Handler handler, @android.annotation.NonNull final android.net.Uri uri, @android.annotation.NonNull final java.lang.String str) {
        this.mSecureSettingName = str;
        this.mSettingRestoreReceiver = new android.content.BroadcastReceiver() { // from class: com.android.server.vr.SettingsObserver.1
            @Override // android.content.BroadcastReceiver
            public void onReceive(android.content.Context context2, android.content.Intent intent) {
                if ("android.os.action.SETTING_RESTORED".equals(intent.getAction()) && java.util.Objects.equals(intent.getStringExtra("setting_name"), str)) {
                    com.android.server.vr.SettingsObserver.this.sendSettingRestored(intent.getStringExtra("previous_value"), intent.getStringExtra("new_value"), getSendingUserId());
                }
            }
        };
        this.mContentObserver = new android.database.ContentObserver(handler) { // from class: com.android.server.vr.SettingsObserver.2
            @Override // android.database.ContentObserver
            public void onChange(boolean z, android.net.Uri uri2) {
                if (uri2 == null || uri.equals(uri2)) {
                    com.android.server.vr.SettingsObserver.this.sendSettingChanged();
                }
            }
        };
        context.getContentResolver().registerContentObserver(uri, false, this.mContentObserver, -1);
    }

    public static com.android.server.vr.SettingsObserver build(@android.annotation.NonNull android.content.Context context, @android.annotation.NonNull android.os.Handler handler, @android.annotation.NonNull java.lang.String str) {
        return new com.android.server.vr.SettingsObserver(context, handler, android.provider.Settings.Secure.getUriFor(str), str);
    }

    public void addListener(@android.annotation.NonNull com.android.server.vr.SettingsObserver.SettingChangeListener settingChangeListener) {
        this.mSettingsListeners.add(settingChangeListener);
    }

    public void removeListener(@android.annotation.NonNull com.android.server.vr.SettingsObserver.SettingChangeListener settingChangeListener) {
        this.mSettingsListeners.remove(settingChangeListener);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendSettingChanged() {
        java.util.Iterator<com.android.server.vr.SettingsObserver.SettingChangeListener> it = this.mSettingsListeners.iterator();
        while (it.hasNext()) {
            it.next().onSettingChanged();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendSettingRestored(java.lang.String str, java.lang.String str2, int i) {
        java.util.Iterator<com.android.server.vr.SettingsObserver.SettingChangeListener> it = this.mSettingsListeners.iterator();
        while (it.hasNext()) {
            it.next().onSettingRestored(str, str2, i);
        }
    }
}
