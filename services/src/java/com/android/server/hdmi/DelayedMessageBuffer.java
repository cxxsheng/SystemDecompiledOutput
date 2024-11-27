package com.android.server.hdmi;

/* loaded from: classes2.dex */
final class DelayedMessageBuffer {
    private final java.util.ArrayList<com.android.server.hdmi.HdmiCecMessage> mBuffer = new java.util.ArrayList<>();
    private final com.android.server.hdmi.HdmiCecLocalDevice mDevice;

    DelayedMessageBuffer(com.android.server.hdmi.HdmiCecLocalDevice hdmiCecLocalDevice) {
        this.mDevice = hdmiCecLocalDevice;
    }

    void add(com.android.server.hdmi.HdmiCecMessage hdmiCecMessage) {
        boolean z;
        switch (hdmiCecMessage.getOpcode()) {
            case 114:
            case 192:
                this.mBuffer.add(hdmiCecMessage);
                z = true;
                break;
            case 130:
                removeActiveSource();
                this.mBuffer.add(hdmiCecMessage);
                z = true;
                break;
            default:
                z = false;
                break;
        }
        if (z) {
            com.android.server.hdmi.HdmiLogger.debug("Buffering message:" + hdmiCecMessage, new java.lang.Object[0]);
        }
    }

    protected void removeActiveSource() {
        java.util.Iterator<com.android.server.hdmi.HdmiCecMessage> it = this.mBuffer.iterator();
        while (it.hasNext()) {
            if (it.next().getOpcode() == 130) {
                it.remove();
            }
        }
    }

    boolean isBuffered(int i) {
        java.util.Iterator<com.android.server.hdmi.HdmiCecMessage> it = this.mBuffer.iterator();
        while (it.hasNext()) {
            if (it.next().getOpcode() == i) {
                return true;
            }
        }
        return false;
    }

    void processAllMessages() {
        java.util.ArrayList arrayList = new java.util.ArrayList(this.mBuffer);
        this.mBuffer.clear();
        java.util.Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            com.android.server.hdmi.HdmiCecMessage hdmiCecMessage = (com.android.server.hdmi.HdmiCecMessage) it.next();
            this.mDevice.onMessage(hdmiCecMessage);
            com.android.server.hdmi.HdmiLogger.debug("Processing message:" + hdmiCecMessage, new java.lang.Object[0]);
        }
    }

    void processMessagesForDevice(int i) {
        java.util.ArrayList arrayList = new java.util.ArrayList(this.mBuffer);
        this.mBuffer.clear();
        com.android.server.hdmi.HdmiLogger.debug("Checking message for address:" + i, new java.lang.Object[0]);
        java.util.Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            com.android.server.hdmi.HdmiCecMessage hdmiCecMessage = (com.android.server.hdmi.HdmiCecMessage) it.next();
            if (hdmiCecMessage.getSource() != i) {
                this.mBuffer.add(hdmiCecMessage);
            } else if (hdmiCecMessage.getOpcode() == 130 && !this.mDevice.isInputReady(android.hardware.hdmi.HdmiDeviceInfo.idForCecDevice(i))) {
                this.mBuffer.add(hdmiCecMessage);
            } else {
                this.mDevice.onMessage(hdmiCecMessage);
                com.android.server.hdmi.HdmiLogger.debug("Processing message:" + hdmiCecMessage, new java.lang.Object[0]);
            }
        }
    }

    void processActiveSource(int i) {
        java.util.ArrayList arrayList = new java.util.ArrayList(this.mBuffer);
        this.mBuffer.clear();
        java.util.Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            com.android.server.hdmi.HdmiCecMessage hdmiCecMessage = (com.android.server.hdmi.HdmiCecMessage) it.next();
            if (hdmiCecMessage.getOpcode() == 130 && hdmiCecMessage.getSource() == i) {
                this.mDevice.onMessage(hdmiCecMessage);
                com.android.server.hdmi.HdmiLogger.debug("Processing message:" + hdmiCecMessage, new java.lang.Object[0]);
            } else {
                this.mBuffer.add(hdmiCecMessage);
            }
        }
    }
}
