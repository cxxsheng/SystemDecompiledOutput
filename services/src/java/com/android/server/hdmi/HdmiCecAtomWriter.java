package com.android.server.hdmi;

@com.android.internal.annotations.VisibleForTesting
/* loaded from: classes2.dex */
public class HdmiCecAtomWriter {
    private static final int ERROR_CODE_UNKNOWN = -1;

    @com.android.internal.annotations.VisibleForTesting
    protected static final int FEATURE_ABORT_OPCODE_UNKNOWN = 256;

    public void messageReported(com.android.server.hdmi.HdmiCecMessage hdmiCecMessage, int i, int i2, int i3) {
        messageReportedBase(createMessageReportedGenericArgs(hdmiCecMessage, i, i3, i2), createMessageReportedSpecialArgs(hdmiCecMessage));
    }

    public void messageReported(com.android.server.hdmi.HdmiCecMessage hdmiCecMessage, int i, int i2) {
        messageReported(hdmiCecMessage, i, i2, -1);
    }

    private com.android.server.hdmi.HdmiCecAtomWriter.MessageReportedGenericArgs createMessageReportedGenericArgs(com.android.server.hdmi.HdmiCecMessage hdmiCecMessage, int i, int i2, int i3) {
        int i4;
        if (i2 == -1) {
            i4 = 0;
        } else {
            i4 = i2 + 10;
        }
        return new com.android.server.hdmi.HdmiCecAtomWriter.MessageReportedGenericArgs(i3, i, hdmiCecMessage.getSource(), hdmiCecMessage.getDestination(), hdmiCecMessage.getOpcode(), i4);
    }

    private com.android.server.hdmi.HdmiCecAtomWriter.MessageReportedSpecialArgs createMessageReportedSpecialArgs(com.android.server.hdmi.HdmiCecMessage hdmiCecMessage) {
        switch (hdmiCecMessage.getOpcode()) {
            case 0:
                return createFeatureAbortSpecialArgs(hdmiCecMessage);
            case 68:
                return createUserControlPressedSpecialArgs(hdmiCecMessage);
            default:
                return new com.android.server.hdmi.HdmiCecAtomWriter.MessageReportedSpecialArgs();
        }
    }

    private com.android.server.hdmi.HdmiCecAtomWriter.MessageReportedSpecialArgs createUserControlPressedSpecialArgs(com.android.server.hdmi.HdmiCecMessage hdmiCecMessage) {
        com.android.server.hdmi.HdmiCecAtomWriter.MessageReportedSpecialArgs messageReportedSpecialArgs = new com.android.server.hdmi.HdmiCecAtomWriter.MessageReportedSpecialArgs();
        if (hdmiCecMessage.getParams().length > 0) {
            byte b = hdmiCecMessage.getParams()[0];
            if (b >= 30 && b <= 41) {
                messageReportedSpecialArgs.mUserControlPressedCommand = 2;
            } else {
                messageReportedSpecialArgs.mUserControlPressedCommand = b + 256;
            }
        }
        return messageReportedSpecialArgs;
    }

    private com.android.server.hdmi.HdmiCecAtomWriter.MessageReportedSpecialArgs createFeatureAbortSpecialArgs(com.android.server.hdmi.HdmiCecMessage hdmiCecMessage) {
        com.android.server.hdmi.HdmiCecAtomWriter.MessageReportedSpecialArgs messageReportedSpecialArgs = new com.android.server.hdmi.HdmiCecAtomWriter.MessageReportedSpecialArgs();
        if (hdmiCecMessage.getParams().length > 0) {
            messageReportedSpecialArgs.mFeatureAbortOpcode = hdmiCecMessage.getParams()[0] & android.hardware.tv.hdmi.cec.CecDeviceType.INACTIVE;
            if (hdmiCecMessage.getParams().length > 1) {
                messageReportedSpecialArgs.mFeatureAbortReason = hdmiCecMessage.getParams()[1] + 10;
            }
        }
        return messageReportedSpecialArgs;
    }

    private void messageReportedBase(com.android.server.hdmi.HdmiCecAtomWriter.MessageReportedGenericArgs messageReportedGenericArgs, com.android.server.hdmi.HdmiCecAtomWriter.MessageReportedSpecialArgs messageReportedSpecialArgs) {
        writeHdmiCecMessageReportedAtom(messageReportedGenericArgs.mUid, messageReportedGenericArgs.mDirection, messageReportedGenericArgs.mInitiatorLogicalAddress, messageReportedGenericArgs.mDestinationLogicalAddress, messageReportedGenericArgs.mOpcode, messageReportedGenericArgs.mSendMessageResult, messageReportedSpecialArgs.mUserControlPressedCommand, messageReportedSpecialArgs.mFeatureAbortOpcode, messageReportedSpecialArgs.mFeatureAbortReason);
    }

    @com.android.internal.annotations.VisibleForTesting
    protected void writeHdmiCecMessageReportedAtom(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9) {
        com.android.internal.util.FrameworkStatsLog.write(310, i, i2, i3, i4, i5, i6, i7, i8, i9);
    }

    public void activeSourceChanged(int i, int i2, @com.android.server.hdmi.Constants.PathRelationship int i3) {
        com.android.internal.util.FrameworkStatsLog.write(309, i, i2, i3);
    }

    public void earcStatusChanged(boolean z, boolean z2, int i, int i2, int i3) {
        com.android.internal.util.FrameworkStatsLog.write(com.android.internal.util.FrameworkStatsLog.HDMI_EARC_STATUS_REPORTED, z, z2, earcStateToEnum(i), earcStateToEnum(i2), i3);
    }

    public void dsmStatusChanged(boolean z, boolean z2, int i) {
        com.android.internal.util.FrameworkStatsLog.write(com.android.internal.util.FrameworkStatsLog.HDMI_SOUNDBAR_MODE_STATUS_REPORTED, z, z2, i);
    }

    private int earcStateToEnum(int i) {
        switch (i) {
            case 0:
                return 1;
            case 1:
                return 2;
            case 2:
                return 3;
            case 3:
                return 4;
            default:
                return 0;
        }
    }

    private class MessageReportedGenericArgs {
        final int mDestinationLogicalAddress;
        final int mDirection;
        final int mInitiatorLogicalAddress;
        final int mOpcode;
        final int mSendMessageResult;
        final int mUid;

        MessageReportedGenericArgs(int i, int i2, int i3, int i4, int i5, int i6) {
            this.mUid = i;
            this.mDirection = i2;
            this.mInitiatorLogicalAddress = i3;
            this.mDestinationLogicalAddress = i4;
            this.mOpcode = i5;
            this.mSendMessageResult = i6;
        }
    }

    private class MessageReportedSpecialArgs {
        int mFeatureAbortOpcode;
        int mFeatureAbortReason;
        int mUserControlPressedCommand;

        private MessageReportedSpecialArgs() {
            this.mUserControlPressedCommand = 0;
            this.mFeatureAbortOpcode = 256;
            this.mFeatureAbortReason = 0;
        }
    }
}
