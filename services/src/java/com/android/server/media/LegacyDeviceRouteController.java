package com.android.server.media;

/* loaded from: classes2.dex */
final class LegacyDeviceRouteController implements com.android.server.media.DeviceRouteController {
    private static final java.lang.String DEVICE_ROUTE_ID = "DEVICE_ROUTE";
    private static final java.lang.String TAG = "LDeviceRouteController";

    @android.annotation.NonNull
    private final android.media.AudioManager mAudioManager;

    @android.annotation.NonNull
    private final com.android.server.media.LegacyDeviceRouteController.AudioRoutesObserver mAudioRoutesObserver;

    @android.annotation.NonNull
    private final android.media.IAudioService mAudioService;
    private final int mBuiltInSpeakerSuitabilityStatus;

    @android.annotation.NonNull
    private final android.content.Context mContext;
    private android.media.MediaRoute2Info mDeviceRoute;
    private int mDeviceVolume;

    @android.annotation.NonNull
    private final com.android.server.media.DeviceRouteController.OnDeviceRouteChangedListener mOnDeviceRouteChangedListener;

    LegacyDeviceRouteController(@android.annotation.NonNull android.content.Context context, @android.annotation.NonNull android.media.AudioManager audioManager, @android.annotation.NonNull android.media.IAudioService iAudioService, @android.annotation.NonNull com.android.server.media.DeviceRouteController.OnDeviceRouteChangedListener onDeviceRouteChangedListener) {
        android.media.AudioRoutesInfo audioRoutesInfo = null;
        this.mAudioRoutesObserver = new com.android.server.media.LegacyDeviceRouteController.AudioRoutesObserver();
        java.util.Objects.requireNonNull(context);
        java.util.Objects.requireNonNull(audioManager);
        java.util.Objects.requireNonNull(iAudioService);
        java.util.Objects.requireNonNull(onDeviceRouteChangedListener);
        this.mContext = context;
        this.mOnDeviceRouteChangedListener = onDeviceRouteChangedListener;
        this.mAudioManager = audioManager;
        this.mAudioService = iAudioService;
        this.mBuiltInSpeakerSuitabilityStatus = com.android.server.media.DeviceRouteController.getBuiltInSpeakerSuitabilityStatus(this.mContext);
        try {
            audioRoutesInfo = this.mAudioService.startWatchingRoutes(this.mAudioRoutesObserver);
        } catch (android.os.RemoteException e) {
            android.util.Slog.w(TAG, "Cannot connect to audio service to start listen to routes", e);
        }
        this.mDeviceRoute = createRouteFromAudioInfo(audioRoutesInfo);
    }

    @Override // com.android.server.media.DeviceRouteController
    public void start(android.os.UserHandle userHandle) {
    }

    @Override // com.android.server.media.DeviceRouteController
    public void stop() {
    }

    @Override // com.android.server.media.DeviceRouteController
    @android.annotation.NonNull
    public synchronized android.media.MediaRoute2Info getSelectedRoute() {
        return this.mDeviceRoute;
    }

    @Override // com.android.server.media.DeviceRouteController
    public synchronized java.util.List<android.media.MediaRoute2Info> getAvailableRoutes() {
        return java.util.Collections.emptyList();
    }

    @Override // com.android.server.media.DeviceRouteController
    public synchronized void transferTo(@android.annotation.Nullable java.lang.String str) {
    }

    @Override // com.android.server.media.DeviceRouteController
    public synchronized boolean updateVolume(int i) {
        if (this.mDeviceVolume == i) {
            return false;
        }
        this.mDeviceVolume = i;
        this.mDeviceRoute = new android.media.MediaRoute2Info.Builder(this.mDeviceRoute).setVolume(i).build();
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:18:0x0045 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public android.media.MediaRoute2Info createRouteFromAudioInfo(@android.annotation.Nullable android.media.AudioRoutesInfo audioRoutesInfo) {
        int i;
        int i2;
        android.media.MediaRoute2Info build;
        if (audioRoutesInfo != null) {
            int i3 = audioRoutesInfo.mainType & 2;
            i = android.R.string.date_time_set;
            i2 = 4;
            if (i3 == 0) {
                if ((audioRoutesInfo.mainType & 1) != 0) {
                    i2 = 3;
                } else if ((audioRoutesInfo.mainType & 4) != 0) {
                    i2 = 13;
                    i = android.R.string.date_time;
                } else if ((audioRoutesInfo.mainType & 8) != 0) {
                    i2 = 9;
                    i = android.R.string.date_time_done;
                } else if ((audioRoutesInfo.mainType & 16) != 0) {
                    i2 = 11;
                    i = android.R.string.day;
                }
            }
            synchronized (this) {
                try {
                    android.media.MediaRoute2Info.Builder connectionState = new android.media.MediaRoute2Info.Builder(DEVICE_ROUTE_ID, this.mContext.getResources().getText(i).toString()).setVolumeHandling(this.mAudioManager.isVolumeFixed() ? 0 : 1).setVolume(this.mDeviceVolume).setVolumeMax(this.mAudioManager.getStreamMaxVolume(3)).setType(i2).addFeature("android.media.route.feature.LIVE_AUDIO").addFeature("android.media.route.feature.LIVE_VIDEO").addFeature("android.media.route.feature.LOCAL_PLAYBACK").setConnectionState(2);
                    if (i2 == 2) {
                        connectionState.setSuitabilityStatus(this.mBuiltInSpeakerSuitabilityStatus);
                    }
                    build = connectionState.build();
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
            return build;
        }
        i = android.R.string.date_picker_prev_month_button;
        i2 = 2;
        synchronized (this) {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyDeviceRouteUpdate() {
        this.mOnDeviceRouteChangedListener.onDeviceRouteChanged();
    }

    private class AudioRoutesObserver extends android.media.IAudioRoutesObserver.Stub {
        private AudioRoutesObserver() {
        }

        public void dispatchAudioRoutesChanged(android.media.AudioRoutesInfo audioRoutesInfo) {
            android.media.MediaRoute2Info createRouteFromAudioInfo = com.android.server.media.LegacyDeviceRouteController.this.createRouteFromAudioInfo(audioRoutesInfo);
            synchronized (com.android.server.media.LegacyDeviceRouteController.this) {
                com.android.server.media.LegacyDeviceRouteController.this.mDeviceRoute = createRouteFromAudioInfo;
            }
            com.android.server.media.LegacyDeviceRouteController.this.notifyDeviceRouteUpdate();
        }
    }
}
