package com.android.server.audio;

/* loaded from: classes.dex */
public class SystemServerAdapter {
    protected final android.content.Context mContext;

    protected SystemServerAdapter(@android.annotation.Nullable android.content.Context context) {
        this.mContext = context;
    }

    @android.annotation.NonNull
    static final com.android.server.audio.SystemServerAdapter getDefaultAdapter(android.content.Context context) {
        java.util.Objects.requireNonNull(context);
        return new com.android.server.audio.SystemServerAdapter(context);
    }

    public boolean isPrivileged() {
        return true;
    }

    public void sendMicrophoneMuteChangedIntent() {
        this.mContext.sendBroadcastAsUser(new android.content.Intent("android.media.action.MICROPHONE_MUTE_CHANGED").setFlags(1073741824), android.os.UserHandle.ALL);
    }

    public void sendDeviceBecomingNoisyIntent() {
        if (this.mContext == null) {
            return;
        }
        android.content.Intent intent = new android.content.Intent("android.media.AUDIO_BECOMING_NOISY");
        intent.addFlags(67108864);
        intent.addFlags(268435456);
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            this.mContext.sendBroadcastAsUser(intent, android.os.UserHandle.ALL);
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    public void broadcastStickyIntentToCurrentProfileGroup(android.content.Intent intent) {
        for (int i : ((android.app.ActivityManagerInternal) com.android.server.LocalServices.getService(android.app.ActivityManagerInternal.class)).getCurrentProfileIds()) {
            android.app.ActivityManager.broadcastStickyIntent(intent, i);
        }
    }

    void registerUserStartedReceiver(android.content.Context context) {
        android.content.IntentFilter intentFilter = new android.content.IntentFilter();
        intentFilter.addAction("android.intent.action.USER_STARTED");
        context.registerReceiverAsUser(new android.content.BroadcastReceiver() { // from class: com.android.server.audio.SystemServerAdapter.1
            @Override // android.content.BroadcastReceiver
            public void onReceive(android.content.Context context2, android.content.Intent intent) {
                int intExtra;
                android.content.pm.UserInfo profileParent;
                if (!"android.intent.action.USER_STARTED".equals(intent.getAction()) || (intExtra = intent.getIntExtra("android.intent.extra.user_handle", com.android.server.am.ProcessList.INVALID_ADJ)) == -10000 || (profileParent = ((android.os.UserManager) context2.getSystemService(android.os.UserManager.class)).getProfileParent(intExtra)) == null) {
                    return;
                }
                com.android.server.audio.SystemServerAdapter.this.broadcastProfileParentStickyIntent(context2, "android.media.action.HDMI_AUDIO_PLUG", intExtra, profileParent.id);
                com.android.server.audio.SystemServerAdapter.this.broadcastProfileParentStickyIntent(context2, "android.intent.action.HEADSET_PLUG", intExtra, profileParent.id);
            }
        }, android.os.UserHandle.ALL, intentFilter, null, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void broadcastProfileParentStickyIntent(android.content.Context context, java.lang.String str, int i, int i2) {
        android.content.Intent registerReceiverAsUser = context.registerReceiverAsUser(null, android.os.UserHandle.of(i2), new android.content.IntentFilter(str), null, null);
        if (registerReceiverAsUser != null) {
            android.app.ActivityManager.broadcastStickyIntent(registerReceiverAsUser, i);
        }
    }

    void broadcastMasterMuteStatus(boolean z) {
        android.content.Intent intent = new android.content.Intent("android.media.MASTER_MUTE_CHANGED_ACTION");
        intent.putExtra("android.media.EXTRA_MASTER_VOLUME_MUTED", z);
        intent.addFlags(872415232);
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            this.mContext.sendStickyBroadcastAsUser(intent, android.os.UserHandle.ALL);
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }
}
