package com.android.server.flags;

/* loaded from: classes.dex */
class GlobalSettingsProxy implements com.android.server.flags.SettingsProxy {
    private final android.content.ContentResolver mContentResolver;

    GlobalSettingsProxy(android.content.ContentResolver contentResolver) {
        this.mContentResolver = contentResolver;
    }

    @Override // com.android.server.flags.SettingsProxy
    public android.content.ContentResolver getContentResolver() {
        return this.mContentResolver;
    }

    @Override // com.android.server.flags.SettingsProxy
    public android.net.Uri getUriFor(java.lang.String str) {
        return android.provider.Settings.Global.getUriFor(str);
    }

    @Override // com.android.server.flags.SettingsProxy
    public java.lang.String getStringForUser(java.lang.String str, int i) {
        return android.provider.Settings.Global.getStringForUser(this.mContentResolver, str, i);
    }

    @Override // com.android.server.flags.SettingsProxy
    public boolean putString(java.lang.String str, java.lang.String str2, boolean z) {
        throw new java.lang.UnsupportedOperationException("This method only exists publicly for Settings.System and Settings.Secure");
    }

    @Override // com.android.server.flags.SettingsProxy
    public boolean putStringForUser(java.lang.String str, java.lang.String str2, int i) {
        return android.provider.Settings.Global.putStringForUser(this.mContentResolver, str, str2, i);
    }

    @Override // com.android.server.flags.SettingsProxy
    public boolean putStringForUser(java.lang.String str, java.lang.String str2, java.lang.String str3, boolean z, int i, boolean z2) {
        return android.provider.Settings.Global.putStringForUser(this.mContentResolver, str, str2, str3, z, i, z2);
    }

    @Override // com.android.server.flags.SettingsProxy
    public boolean putString(java.lang.String str, java.lang.String str2, java.lang.String str3, boolean z) {
        return android.provider.Settings.Global.putString(this.mContentResolver, str, str2, str3, z);
    }
}
