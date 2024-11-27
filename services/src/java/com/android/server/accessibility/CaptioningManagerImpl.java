package com.android.server.accessibility;

/* loaded from: classes.dex */
public class CaptioningManagerImpl implements android.view.accessibility.CaptioningManager.SystemAudioCaptioningAccessing {
    private static final boolean SYSTEM_AUDIO_CAPTIONING_UI_DEFAULT_ENABLED = false;
    private final android.content.Context mContext;

    public CaptioningManagerImpl(android.content.Context context) {
        this.mContext = context;
    }

    public void setSystemAudioCaptioningEnabled(boolean z, int i) {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            android.provider.Settings.Secure.putIntForUser(this.mContext.getContentResolver(), "odi_captions_enabled", z ? 1 : 0, i);
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public boolean isSystemAudioCaptioningUiEnabled(int i) {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            return android.provider.Settings.Secure.getIntForUser(this.mContext.getContentResolver(), "odi_captions_volume_ui_enabled", 0, i) == 1;
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void setSystemAudioCaptioningUiEnabled(boolean z, int i) {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            android.provider.Settings.Secure.putIntForUser(this.mContext.getContentResolver(), "odi_captions_volume_ui_enabled", z ? 1 : 0, i);
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }
}
