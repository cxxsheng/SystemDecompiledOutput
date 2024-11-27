package com.android.server.hdmi;

/* loaded from: classes2.dex */
final class RequestSadAction extends com.android.server.hdmi.HdmiCecFeatureAction {
    private static final int MAX_SAD_PER_REQUEST = 4;
    private static final int RETRY_COUNTER_MAX = 1;
    private static final int STATE_WAITING_FOR_REPORT_SAD = 1;
    private static final java.lang.String TAG = "RequestSadAction";
    private final com.android.server.hdmi.RequestSadAction.RequestSadCallback mCallback;
    private final java.util.List<java.lang.Integer> mCecCodecsToQuery;
    private int mQueriedSadCount;
    private final java.util.List<byte[]> mSupportedSads;
    private final int mTargetAddress;
    private int mTimeoutRetry;

    interface RequestSadCallback {
        void onRequestSadDone(java.util.List<byte[]> list);
    }

    RequestSadAction(com.android.server.hdmi.HdmiCecLocalDevice hdmiCecLocalDevice, int i, com.android.server.hdmi.RequestSadAction.RequestSadCallback requestSadCallback) {
        super(hdmiCecLocalDevice);
        this.mCecCodecsToQuery = new java.util.ArrayList();
        this.mSupportedSads = new java.util.ArrayList();
        this.mQueriedSadCount = 0;
        this.mTimeoutRetry = 0;
        this.mTargetAddress = i;
        java.util.Objects.requireNonNull(requestSadCallback);
        this.mCallback = requestSadCallback;
        com.android.server.hdmi.HdmiCecConfig hdmiCecConfig = localDevice().mService.getHdmiCecConfig();
        if (hdmiCecConfig.getIntValue("query_sad_lpcm") == 1) {
            this.mCecCodecsToQuery.add(1);
        }
        if (hdmiCecConfig.getIntValue("query_sad_dd") == 1) {
            this.mCecCodecsToQuery.add(2);
        }
        if (hdmiCecConfig.getIntValue("query_sad_mpeg1") == 1) {
            this.mCecCodecsToQuery.add(3);
        }
        if (hdmiCecConfig.getIntValue("query_sad_mp3") == 1) {
            this.mCecCodecsToQuery.add(4);
        }
        if (hdmiCecConfig.getIntValue("query_sad_mpeg2") == 1) {
            this.mCecCodecsToQuery.add(5);
        }
        if (hdmiCecConfig.getIntValue("query_sad_aac") == 1) {
            this.mCecCodecsToQuery.add(6);
        }
        if (hdmiCecConfig.getIntValue("query_sad_dts") == 1) {
            this.mCecCodecsToQuery.add(7);
        }
        if (hdmiCecConfig.getIntValue("query_sad_atrac") == 1) {
            this.mCecCodecsToQuery.add(8);
        }
        if (hdmiCecConfig.getIntValue("query_sad_onebitaudio") == 1) {
            this.mCecCodecsToQuery.add(9);
        }
        if (hdmiCecConfig.getIntValue("query_sad_ddp") == 1) {
            this.mCecCodecsToQuery.add(10);
        }
        if (hdmiCecConfig.getIntValue("query_sad_dtshd") == 1) {
            this.mCecCodecsToQuery.add(11);
        }
        if (hdmiCecConfig.getIntValue("query_sad_truehd") == 1) {
            this.mCecCodecsToQuery.add(12);
        }
        if (hdmiCecConfig.getIntValue("query_sad_dst") == 1) {
            this.mCecCodecsToQuery.add(13);
        }
        if (hdmiCecConfig.getIntValue("query_sad_wmapro") == 1) {
            this.mCecCodecsToQuery.add(14);
        }
        if (hdmiCecConfig.getIntValue("query_sad_max") == 1) {
            this.mCecCodecsToQuery.add(15);
        }
    }

    @Override // com.android.server.hdmi.HdmiCecFeatureAction
    boolean start() {
        querySad();
        return true;
    }

    private void querySad() {
        if (this.mQueriedSadCount >= this.mCecCodecsToQuery.size()) {
            wrapUpAndFinish();
            return;
        }
        sendCommand(com.android.server.hdmi.HdmiCecMessageBuilder.buildRequestShortAudioDescriptor(getSourceAddress(), this.mTargetAddress, this.mCecCodecsToQuery.subList(this.mQueriedSadCount, java.lang.Math.min(this.mCecCodecsToQuery.size(), this.mQueriedSadCount + 4)).stream().mapToInt(new java.util.function.ToIntFunction() { // from class: com.android.server.hdmi.RequestSadAction$$ExternalSyntheticLambda0
            @Override // java.util.function.ToIntFunction
            public final int applyAsInt(java.lang.Object obj) {
                int intValue;
                intValue = ((java.lang.Integer) obj).intValue();
                return intValue;
            }
        }).toArray()));
        this.mState = 1;
        addTimer(this.mState, 2000);
    }

    @Override // com.android.server.hdmi.HdmiCecFeatureAction
    boolean processCommand(com.android.server.hdmi.HdmiCecMessage hdmiCecMessage) {
        if (this.mState != 1 || this.mTargetAddress != hdmiCecMessage.getSource()) {
            return false;
        }
        if (hdmiCecMessage.getOpcode() == 163) {
            if (hdmiCecMessage.getParams() == null || hdmiCecMessage.getParams().length == 0 || hdmiCecMessage.getParams().length % 3 != 0) {
                return true;
            }
            for (int i = 0; i < hdmiCecMessage.getParams().length - 2; i += 3) {
                if (isValidCodec(hdmiCecMessage.getParams()[i])) {
                    updateResult(new byte[]{hdmiCecMessage.getParams()[i], hdmiCecMessage.getParams()[i + 1], hdmiCecMessage.getParams()[i + 2]});
                } else {
                    android.util.Slog.w(TAG, "Dropped invalid codec " + ((int) hdmiCecMessage.getParams()[i]) + ".");
                }
            }
            this.mQueriedSadCount += 4;
            this.mTimeoutRetry = 0;
            querySad();
            return true;
        }
        if (hdmiCecMessage.getOpcode() == 0 && (hdmiCecMessage.getParams()[0] & android.hardware.tv.hdmi.cec.CecDeviceType.INACTIVE) == 164) {
            if ((hdmiCecMessage.getParams()[1] & android.hardware.tv.hdmi.cec.CecDeviceType.INACTIVE) == 0) {
                wrapUpAndFinish();
                return true;
            }
            if ((hdmiCecMessage.getParams()[1] & android.hardware.tv.hdmi.cec.CecDeviceType.INACTIVE) == 3) {
                this.mQueriedSadCount += 4;
                this.mTimeoutRetry = 0;
                querySad();
                return true;
            }
        }
        return false;
    }

    private boolean isValidCodec(byte b) {
        int i;
        return (b & 128) == 0 && (i = (b & 120) >> 3) > 0 && i <= 15;
    }

    private void updateResult(byte[] bArr) {
        this.mSupportedSads.add(bArr);
    }

    @Override // com.android.server.hdmi.HdmiCecFeatureAction
    void handleTimerEvent(int i) {
        if (this.mState == i && i == 1) {
            int i2 = this.mTimeoutRetry + 1;
            this.mTimeoutRetry = i2;
            if (i2 <= 1) {
                querySad();
            } else {
                wrapUpAndFinish();
            }
        }
    }

    private void wrapUpAndFinish() {
        this.mCallback.onRequestSadDone(this.mSupportedSads);
        finish();
    }
}
