package com.android.server.media;

/* loaded from: classes2.dex */
class MediaSessionDeviceConfig {
    private static final long DEFAULT_MEDIA_BUTTON_RECEIVER_FGS_ALLOWLIST_DURATION_MS = 10000;
    private static final long DEFAULT_MEDIA_SESSION_CALLBACK_FGS_ALLOWLIST_DURATION_MS = 10000;
    private static final long DEFAULT_MEDIA_SESSION_CALLBACK_FGS_WHILE_IN_USE_TEMP_ALLOW_DURATION_MS = 10000;
    private static final java.lang.String KEY_MEDIA_BUTTON_RECEIVER_FGS_ALLOWLIST_DURATION_MS = "media_button_receiver_fgs_allowlist_duration_ms";
    private static final java.lang.String KEY_MEDIA_SESSION_CALLBACK_FGS_ALLOWLIST_DURATION_MS = "media_session_calback_fgs_allowlist_duration_ms";
    private static final java.lang.String KEY_MEDIA_SESSION_CALLBACK_FGS_WHILE_IN_USE_TEMP_ALLOW_DURATION_MS = "media_session_callback_fgs_while_in_use_temp_allow_duration_ms";
    private static volatile long sMediaButtonReceiverFgsAllowlistDurationMs = com.android.server.job.controllers.JobStatus.DEFAULT_TRIGGER_UPDATE_DELAY;
    private static volatile long sMediaSessionCallbackFgsAllowlistDurationMs = com.android.server.job.controllers.JobStatus.DEFAULT_TRIGGER_UPDATE_DELAY;
    private static volatile long sMediaSessionCallbackFgsWhileInUseTempAllowDurationMs = com.android.server.job.controllers.JobStatus.DEFAULT_TRIGGER_UPDATE_DELAY;

    MediaSessionDeviceConfig() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void refresh(final android.provider.DeviceConfig.Properties properties) {
        properties.getKeyset();
        properties.getKeyset().forEach(new java.util.function.Consumer() { // from class: com.android.server.media.MediaSessionDeviceConfig$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.media.MediaSessionDeviceConfig.lambda$refresh$0(properties, (java.lang.String) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public static /* synthetic */ void lambda$refresh$0(android.provider.DeviceConfig.Properties properties, java.lang.String str) {
        char c;
        switch (str.hashCode()) {
            case -1976080914:
                if (str.equals(KEY_MEDIA_BUTTON_RECEIVER_FGS_ALLOWLIST_DURATION_MS)) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case -1060130895:
                if (str.equals(KEY_MEDIA_SESSION_CALLBACK_FGS_WHILE_IN_USE_TEMP_ALLOW_DURATION_MS)) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case 1803361950:
                if (str.equals(KEY_MEDIA_SESSION_CALLBACK_FGS_ALLOWLIST_DURATION_MS)) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        switch (c) {
            case 0:
                sMediaButtonReceiverFgsAllowlistDurationMs = properties.getLong(str, com.android.server.job.controllers.JobStatus.DEFAULT_TRIGGER_UPDATE_DELAY);
                break;
            case 1:
                sMediaSessionCallbackFgsAllowlistDurationMs = properties.getLong(str, com.android.server.job.controllers.JobStatus.DEFAULT_TRIGGER_UPDATE_DELAY);
                break;
            case 2:
                sMediaSessionCallbackFgsWhileInUseTempAllowDurationMs = properties.getLong(str, com.android.server.job.controllers.JobStatus.DEFAULT_TRIGGER_UPDATE_DELAY);
                break;
        }
    }

    public static void initialize(android.content.Context context) {
        android.provider.DeviceConfig.addOnPropertiesChangedListener("media", context.getMainExecutor(), new android.provider.DeviceConfig.OnPropertiesChangedListener() { // from class: com.android.server.media.MediaSessionDeviceConfig$$ExternalSyntheticLambda0
            public final void onPropertiesChanged(android.provider.DeviceConfig.Properties properties) {
                com.android.server.media.MediaSessionDeviceConfig.refresh(properties);
            }
        });
        refresh(android.provider.DeviceConfig.getProperties("media", new java.lang.String[0]));
    }

    public static long getMediaButtonReceiverFgsAllowlistDurationMs() {
        return sMediaButtonReceiverFgsAllowlistDurationMs;
    }

    public static long getMediaSessionCallbackFgsAllowlistDurationMs() {
        return sMediaSessionCallbackFgsAllowlistDurationMs;
    }

    public static long getMediaSessionCallbackFgsWhileInUseTempAllowDurationMs() {
        return sMediaSessionCallbackFgsWhileInUseTempAllowDurationMs;
    }

    public static void dump(java.io.PrintWriter printWriter, java.lang.String str) {
        printWriter.println("Media session config:");
        java.lang.String str2 = str + "  %s: [cur: %s, def: %s]";
        java.lang.Long valueOf = java.lang.Long.valueOf(sMediaButtonReceiverFgsAllowlistDurationMs);
        java.lang.Long valueOf2 = java.lang.Long.valueOf(com.android.server.job.controllers.JobStatus.DEFAULT_TRIGGER_UPDATE_DELAY);
        printWriter.println(android.text.TextUtils.formatSimple(str2, new java.lang.Object[]{KEY_MEDIA_BUTTON_RECEIVER_FGS_ALLOWLIST_DURATION_MS, valueOf, valueOf2}));
        printWriter.println(android.text.TextUtils.formatSimple(str2, new java.lang.Object[]{KEY_MEDIA_SESSION_CALLBACK_FGS_ALLOWLIST_DURATION_MS, java.lang.Long.valueOf(sMediaSessionCallbackFgsAllowlistDurationMs), valueOf2}));
        printWriter.println(android.text.TextUtils.formatSimple(str2, new java.lang.Object[]{KEY_MEDIA_SESSION_CALLBACK_FGS_WHILE_IN_USE_TEMP_ALLOW_DURATION_MS, java.lang.Long.valueOf(sMediaSessionCallbackFgsWhileInUseTempAllowDurationMs), valueOf2}));
    }
}
