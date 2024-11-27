package com.android.server.hdmi;

/* loaded from: classes2.dex */
final class CecMessageBuffer {
    private java.util.List<com.android.server.hdmi.HdmiCecMessage> mBuffer = new java.util.ArrayList();
    private com.android.server.hdmi.HdmiControlService mHdmiControlService;

    CecMessageBuffer(com.android.server.hdmi.HdmiControlService hdmiControlService) {
        this.mHdmiControlService = hdmiControlService;
    }

    public boolean bufferMessage(com.android.server.hdmi.HdmiCecMessage hdmiCecMessage) {
        switch (hdmiCecMessage.getOpcode()) {
            case 4:
            case 13:
                bufferImageOrTextViewOn(hdmiCecMessage);
                break;
            case 112:
                bufferSystemAudioModeRequest(hdmiCecMessage);
                break;
            case 128:
                bufferRoutingChange(hdmiCecMessage);
                break;
            case 130:
                bufferActiveSource(hdmiCecMessage);
                break;
            case 134:
                bufferSetStreamPath(hdmiCecMessage);
                break;
        }
        return true;
    }

    public void processMessages() {
        for (final com.android.server.hdmi.HdmiCecMessage hdmiCecMessage : this.mBuffer) {
            this.mHdmiControlService.runOnServiceThread(new java.lang.Runnable() { // from class: com.android.server.hdmi.CecMessageBuffer.1
                @Override // java.lang.Runnable
                public void run() {
                    com.android.server.hdmi.CecMessageBuffer.this.mHdmiControlService.handleCecCommand(hdmiCecMessage);
                }
            });
        }
        this.mBuffer.clear();
    }

    private void bufferActiveSource(com.android.server.hdmi.HdmiCecMessage hdmiCecMessage) {
        if (!replaceMessageIfBuffered(hdmiCecMessage, 130)) {
            this.mBuffer.add(hdmiCecMessage);
        }
    }

    private void bufferImageOrTextViewOn(com.android.server.hdmi.HdmiCecMessage hdmiCecMessage) {
        if (!replaceMessageIfBuffered(hdmiCecMessage, 4) && !replaceMessageIfBuffered(hdmiCecMessage, 13)) {
            this.mBuffer.add(hdmiCecMessage);
        }
    }

    private void bufferSystemAudioModeRequest(com.android.server.hdmi.HdmiCecMessage hdmiCecMessage) {
        if (!replaceMessageIfBuffered(hdmiCecMessage, 112)) {
            this.mBuffer.add(hdmiCecMessage);
        }
    }

    private void bufferRoutingChange(com.android.server.hdmi.HdmiCecMessage hdmiCecMessage) {
        if (!replaceMessageIfBuffered(hdmiCecMessage, 128)) {
            this.mBuffer.add(hdmiCecMessage);
        }
    }

    private void bufferSetStreamPath(com.android.server.hdmi.HdmiCecMessage hdmiCecMessage) {
        if (!replaceMessageIfBuffered(hdmiCecMessage, 134)) {
            this.mBuffer.add(hdmiCecMessage);
        }
    }

    public java.util.List<com.android.server.hdmi.HdmiCecMessage> getBuffer() {
        return new java.util.ArrayList(this.mBuffer);
    }

    private boolean replaceMessageIfBuffered(com.android.server.hdmi.HdmiCecMessage hdmiCecMessage, int i) {
        for (int i2 = 0; i2 < this.mBuffer.size(); i2++) {
            if (this.mBuffer.get(i2).getOpcode() == i) {
                this.mBuffer.set(i2, hdmiCecMessage);
                return true;
            }
        }
        return false;
    }
}
