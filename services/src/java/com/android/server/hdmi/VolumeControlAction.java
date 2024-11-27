package com.android.server.hdmi;

/* loaded from: classes2.dex */
final class VolumeControlAction extends com.android.server.hdmi.HdmiCecFeatureAction {
    private static final int MAX_VOLUME = 100;
    private static final int STATE_WAIT_FOR_NEXT_VOLUME_PRESS = 1;
    private static final java.lang.String TAG = "VolumeControlAction";
    private static final int UNKNOWN_AVR_VOLUME = -1;
    private final int mAvrAddress;
    private boolean mIsVolumeUp;
    private boolean mLastAvrMute;
    private int mLastAvrVolume;
    private long mLastKeyUpdateTime;
    private boolean mSentKeyPressed;

    public static int scaleToCecVolume(int i, int i2) {
        return (i * 100) / i2;
    }

    public static int scaleToCustomVolume(int i, int i2) {
        return (i * i2) / 100;
    }

    VolumeControlAction(com.android.server.hdmi.HdmiCecLocalDevice hdmiCecLocalDevice, int i, boolean z) {
        super(hdmiCecLocalDevice);
        this.mAvrAddress = i;
        this.mIsVolumeUp = z;
        this.mLastAvrVolume = -1;
        this.mLastAvrMute = false;
        this.mSentKeyPressed = false;
        updateLastKeyUpdateTime();
    }

    private void updateLastKeyUpdateTime() {
        this.mLastKeyUpdateTime = java.lang.System.currentTimeMillis();
    }

    @Override // com.android.server.hdmi.HdmiCecFeatureAction
    boolean start() {
        this.mState = 1;
        sendVolumeKeyPressed();
        resetTimer();
        return true;
    }

    private void sendVolumeKeyPressed() {
        sendCommand(com.android.server.hdmi.HdmiCecMessageBuilder.buildUserControlPressed(getSourceAddress(), this.mAvrAddress, this.mIsVolumeUp ? 65 : 66));
        this.mSentKeyPressed = true;
    }

    private void resetTimer() {
        this.mActionTimer.clearTimerMessage();
        addTimer(1, 300);
    }

    void handleVolumeChange(boolean z) {
        if (this.mIsVolumeUp != z) {
            com.android.server.hdmi.HdmiLogger.debug("Volume Key Status Changed[old:%b new:%b]", java.lang.Boolean.valueOf(this.mIsVolumeUp), java.lang.Boolean.valueOf(z));
            sendVolumeKeyReleased();
            this.mIsVolumeUp = z;
            sendVolumeKeyPressed();
            resetTimer();
        }
        updateLastKeyUpdateTime();
    }

    private void sendVolumeKeyReleased() {
        sendCommand(com.android.server.hdmi.HdmiCecMessageBuilder.buildUserControlReleased(getSourceAddress(), this.mAvrAddress));
        this.mSentKeyPressed = false;
    }

    @Override // com.android.server.hdmi.HdmiCecFeatureAction
    boolean processCommand(com.android.server.hdmi.HdmiCecMessage hdmiCecMessage) {
        if (this.mState != 1 || hdmiCecMessage.getSource() != this.mAvrAddress) {
            return false;
        }
        switch (hdmiCecMessage.getOpcode()) {
            case 0:
                return handleFeatureAbort(hdmiCecMessage);
            case 122:
                return handleReportAudioStatus(hdmiCecMessage);
            default:
                return false;
        }
    }

    private boolean handleReportAudioStatus(com.android.server.hdmi.HdmiCecMessage hdmiCecMessage) {
        boolean isAudioStatusMute = com.android.server.hdmi.HdmiUtils.isAudioStatusMute(hdmiCecMessage);
        int audioStatusVolume = com.android.server.hdmi.HdmiUtils.getAudioStatusVolume(hdmiCecMessage);
        this.mLastAvrVolume = audioStatusVolume;
        this.mLastAvrMute = isAudioStatusMute;
        if (shouldUpdateAudioVolume(isAudioStatusMute)) {
            com.android.server.hdmi.HdmiLogger.debug("Force volume change[mute:%b, volume=%d]", java.lang.Boolean.valueOf(isAudioStatusMute), java.lang.Integer.valueOf(audioStatusVolume));
            tv().setAudioStatus(isAudioStatusMute, audioStatusVolume);
            this.mLastAvrVolume = -1;
            this.mLastAvrMute = false;
            return true;
        }
        return true;
    }

    private boolean shouldUpdateAudioVolume(boolean z) {
        if (z) {
            return true;
        }
        com.android.server.hdmi.AudioManagerWrapper audioManager = tv().getService().getAudioManager();
        int streamVolume = audioManager.getStreamVolume(3);
        return this.mIsVolumeUp ? streamVolume == audioManager.getStreamMaxVolume(3) : streamVolume == 0;
    }

    private boolean handleFeatureAbort(com.android.server.hdmi.HdmiCecMessage hdmiCecMessage) {
        if ((hdmiCecMessage.getParams()[0] & android.hardware.tv.hdmi.cec.CecDeviceType.INACTIVE) != 68) {
            return false;
        }
        finish();
        return true;
    }

    @Override // com.android.server.hdmi.HdmiCecFeatureAction
    protected void clear() {
        super.clear();
        if (this.mSentKeyPressed) {
            sendVolumeKeyReleased();
        }
        if (this.mLastAvrVolume != -1) {
            tv().setAudioStatus(this.mLastAvrMute, this.mLastAvrVolume);
            this.mLastAvrVolume = -1;
            this.mLastAvrMute = false;
        }
    }

    @Override // com.android.server.hdmi.HdmiCecFeatureAction
    void handleTimerEvent(int i) {
        if (i != 1) {
            return;
        }
        if (java.lang.System.currentTimeMillis() - this.mLastKeyUpdateTime >= 300) {
            finish();
        } else {
            sendVolumeKeyPressed();
            resetTimer();
        }
    }
}
