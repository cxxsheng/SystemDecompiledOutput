package com.android.server.hdmi;

/* loaded from: classes2.dex */
final class AbsoluteVolumeAudioStatusAction extends com.android.server.hdmi.HdmiCecFeatureAction {
    private static final int STATE_MONITOR_AUDIO_STATUS = 2;
    private static final int STATE_WAIT_FOR_INITIAL_AUDIO_STATUS = 1;
    private static final java.lang.String TAG = "AbsoluteVolumeAudioStatusAction";
    private int mInitialAudioStatusRetriesLeft;
    private com.android.server.hdmi.AudioStatus mLastAudioStatus;
    private final int mTargetAddress;

    AbsoluteVolumeAudioStatusAction(com.android.server.hdmi.HdmiCecLocalDevice hdmiCecLocalDevice, int i) {
        super(hdmiCecLocalDevice);
        this.mInitialAudioStatusRetriesLeft = 2;
        this.mTargetAddress = i;
    }

    @Override // com.android.server.hdmi.HdmiCecFeatureAction
    boolean start() {
        this.mState = 1;
        sendGiveAudioStatus();
        return true;
    }

    void updateVolume(int i) {
        this.mLastAudioStatus = new com.android.server.hdmi.AudioStatus(i, this.mLastAudioStatus.getMute());
    }

    private void sendGiveAudioStatus() {
        addTimer(this.mState, 2000);
        sendCommand(com.android.server.hdmi.HdmiCecMessageBuilder.buildGiveAudioStatus(getSourceAddress(), this.mTargetAddress));
    }

    @Override // com.android.server.hdmi.HdmiCecFeatureAction
    boolean processCommand(com.android.server.hdmi.HdmiCecMessage hdmiCecMessage) {
        switch (hdmiCecMessage.getOpcode()) {
            case 122:
                return handleReportAudioStatus(hdmiCecMessage);
            default:
                return false;
        }
    }

    void requestAndUpdateAudioStatus() {
        if (this.mState == 2) {
            sendGiveAudioStatus();
        }
    }

    private boolean handleReportAudioStatus(com.android.server.hdmi.HdmiCecMessage hdmiCecMessage) {
        if (this.mTargetAddress != hdmiCecMessage.getSource() || hdmiCecMessage.getParams().length == 0) {
            return false;
        }
        boolean isAudioStatusMute = com.android.server.hdmi.HdmiUtils.isAudioStatusMute(hdmiCecMessage);
        int audioStatusVolume = com.android.server.hdmi.HdmiUtils.getAudioStatusVolume(hdmiCecMessage);
        if (audioStatusVolume == -1) {
            return true;
        }
        com.android.server.hdmi.AudioStatus audioStatus = new com.android.server.hdmi.AudioStatus(audioStatusVolume, isAudioStatusMute);
        if (this.mState == 1) {
            localDevice().getService().enableAbsoluteVolumeBehavior(audioStatus);
            this.mState = 2;
        } else if (this.mState == 2) {
            boolean z = audioStatus.getVolume() != this.mLastAudioStatus.getVolume();
            if (z) {
                localDevice().getService().notifyAvbVolumeChange(audioStatus.getVolume());
            }
            if (audioStatus.getMute() != this.mLastAudioStatus.getMute() || z || localDevice().getService().isTvDevice()) {
                localDevice().getService().notifyAvbMuteChange(audioStatus.getMute());
            }
        }
        this.mLastAudioStatus = audioStatus;
        return true;
    }

    @Override // com.android.server.hdmi.HdmiCecFeatureAction
    void handleTimerEvent(int i) {
        if (this.mState == i && this.mInitialAudioStatusRetriesLeft > 0) {
            this.mInitialAudioStatusRetriesLeft--;
            sendGiveAudioStatus();
        }
    }
}
