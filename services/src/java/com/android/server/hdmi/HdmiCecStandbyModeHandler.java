package com.android.server.hdmi;

/* loaded from: classes2.dex */
public final class HdmiCecStandbyModeHandler {
    private final com.android.server.hdmi.HdmiCecStandbyModeHandler.CecMessageHandler mDefaultHandler;
    private final com.android.server.hdmi.HdmiCecLocalDevice mDevice;
    private final com.android.server.hdmi.HdmiControlService mService;
    private final android.util.SparseArray<com.android.server.hdmi.HdmiCecStandbyModeHandler.CecMessageHandler> mCecMessageHandlers = new android.util.SparseArray<>();
    private final com.android.server.hdmi.HdmiCecStandbyModeHandler.CecMessageHandler mAborterUnrecognizedOpcode = new com.android.server.hdmi.HdmiCecStandbyModeHandler.Aborter(0);
    private final com.android.server.hdmi.HdmiCecStandbyModeHandler.CecMessageHandler mAborterIncorrectMode = new com.android.server.hdmi.HdmiCecStandbyModeHandler.Aborter(1);
    private final com.android.server.hdmi.HdmiCecStandbyModeHandler.CecMessageHandler mAborterRefused = new com.android.server.hdmi.HdmiCecStandbyModeHandler.Aborter(4);
    private final com.android.server.hdmi.HdmiCecStandbyModeHandler.CecMessageHandler mAutoOnHandler = new com.android.server.hdmi.HdmiCecStandbyModeHandler.AutoOnHandler();
    private final com.android.server.hdmi.HdmiCecStandbyModeHandler.CecMessageHandler mBypasser = new com.android.server.hdmi.HdmiCecStandbyModeHandler.Bypasser();
    private final com.android.server.hdmi.HdmiCecStandbyModeHandler.CecMessageHandler mBystander = new com.android.server.hdmi.HdmiCecStandbyModeHandler.Bystander();
    private final com.android.server.hdmi.HdmiCecStandbyModeHandler.UserControlProcessedHandler mUserControlProcessedHandler = new com.android.server.hdmi.HdmiCecStandbyModeHandler.UserControlProcessedHandler();

    private interface CecMessageHandler {
        boolean handle(com.android.server.hdmi.HdmiCecMessage hdmiCecMessage);
    }

    private static final class Bystander implements com.android.server.hdmi.HdmiCecStandbyModeHandler.CecMessageHandler {
        private Bystander() {
        }

        @Override // com.android.server.hdmi.HdmiCecStandbyModeHandler.CecMessageHandler
        public boolean handle(com.android.server.hdmi.HdmiCecMessage hdmiCecMessage) {
            return true;
        }
    }

    private static final class Bypasser implements com.android.server.hdmi.HdmiCecStandbyModeHandler.CecMessageHandler {
        private Bypasser() {
        }

        @Override // com.android.server.hdmi.HdmiCecStandbyModeHandler.CecMessageHandler
        public boolean handle(com.android.server.hdmi.HdmiCecMessage hdmiCecMessage) {
            return false;
        }
    }

    private final class Aborter implements com.android.server.hdmi.HdmiCecStandbyModeHandler.CecMessageHandler {
        private final int mReason;

        public Aborter(int i) {
            this.mReason = i;
        }

        @Override // com.android.server.hdmi.HdmiCecStandbyModeHandler.CecMessageHandler
        public boolean handle(com.android.server.hdmi.HdmiCecMessage hdmiCecMessage) {
            com.android.server.hdmi.HdmiCecStandbyModeHandler.this.mService.maySendFeatureAbortCommand(hdmiCecMessage, this.mReason);
            return true;
        }
    }

    private final class AutoOnHandler implements com.android.server.hdmi.HdmiCecStandbyModeHandler.CecMessageHandler {
        private AutoOnHandler() {
        }

        @Override // com.android.server.hdmi.HdmiCecStandbyModeHandler.CecMessageHandler
        public boolean handle(com.android.server.hdmi.HdmiCecMessage hdmiCecMessage) {
            if (!((com.android.server.hdmi.HdmiCecLocalDeviceTv) com.android.server.hdmi.HdmiCecStandbyModeHandler.this.mDevice).getAutoWakeup()) {
                com.android.server.hdmi.HdmiCecStandbyModeHandler.this.mAborterRefused.handle(hdmiCecMessage);
                return true;
            }
            return false;
        }
    }

    private final class UserControlProcessedHandler implements com.android.server.hdmi.HdmiCecStandbyModeHandler.CecMessageHandler {
        private UserControlProcessedHandler() {
        }

        @Override // com.android.server.hdmi.HdmiCecStandbyModeHandler.CecMessageHandler
        public boolean handle(com.android.server.hdmi.HdmiCecMessage hdmiCecMessage) {
            if (com.android.server.hdmi.HdmiCecLocalDevice.isPowerOnOrToggleCommand(hdmiCecMessage)) {
                return false;
            }
            if (com.android.server.hdmi.HdmiCecLocalDevice.isPowerOffOrToggleCommand(hdmiCecMessage)) {
                return true;
            }
            return com.android.server.hdmi.HdmiCecStandbyModeHandler.this.mAborterIncorrectMode.handle(hdmiCecMessage);
        }
    }

    private void addCommonHandlers() {
        addHandler(68, this.mUserControlProcessedHandler);
    }

    private void addTvHandlers() {
        addHandler(130, this.mBystander);
        addHandler(133, this.mBystander);
        addHandler(128, this.mBystander);
        addHandler(129, this.mBystander);
        addHandler(134, this.mBystander);
        addHandler(54, this.mBystander);
        addHandler(50, this.mBystander);
        addHandler(69, this.mBystander);
        addHandler(0, this.mBystander);
        addHandler(157, this.mBystander);
        addHandler(126, this.mBystander);
        addHandler(122, this.mBystander);
        addHandler(131, this.mBypasser);
        addHandler(145, this.mBypasser);
        addHandler(132, this.mBypasser);
        addHandler(140, this.mBypasser);
        addHandler(70, this.mBypasser);
        addHandler(71, this.mBypasser);
        addHandler(135, this.mBypasser);
        addHandler(144, this.mBypasser);
        addHandler(165, this.mBypasser);
        addHandler(143, this.mBypasser);
        addHandler(255, this.mBypasser);
        addHandler(159, this.mBypasser);
        addHandler(160, this.mAborterIncorrectMode);
        addHandler(114, this.mAborterIncorrectMode);
        addHandler(4, this.mAutoOnHandler);
        addHandler(13, this.mAutoOnHandler);
        addHandler(10, this.mBystander);
        addHandler(15, this.mAborterIncorrectMode);
        addHandler(192, this.mAborterIncorrectMode);
        addHandler(197, this.mAborterIncorrectMode);
    }

    public HdmiCecStandbyModeHandler(com.android.server.hdmi.HdmiControlService hdmiControlService, com.android.server.hdmi.HdmiCecLocalDevice hdmiCecLocalDevice) {
        this.mService = hdmiControlService;
        this.mDevice = hdmiCecLocalDevice;
        addCommonHandlers();
        if (this.mDevice.getType() == 0) {
            addTvHandlers();
            this.mDefaultHandler = this.mAborterUnrecognizedOpcode;
        } else {
            this.mDefaultHandler = this.mBypasser;
        }
    }

    private void addHandler(int i, com.android.server.hdmi.HdmiCecStandbyModeHandler.CecMessageHandler cecMessageHandler) {
        this.mCecMessageHandlers.put(i, cecMessageHandler);
    }

    boolean handleCommand(com.android.server.hdmi.HdmiCecMessage hdmiCecMessage) {
        com.android.server.hdmi.HdmiCecStandbyModeHandler.CecMessageHandler cecMessageHandler = this.mCecMessageHandlers.get(hdmiCecMessage.getOpcode());
        if (cecMessageHandler != null) {
            return cecMessageHandler.handle(hdmiCecMessage);
        }
        return this.mDefaultHandler.handle(hdmiCecMessage);
    }
}
