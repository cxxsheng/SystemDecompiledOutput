package com.android.server.usb.descriptors;

/* loaded from: classes2.dex */
public final class Usb20ACOutputTerminal extends com.android.server.usb.descriptors.UsbACTerminal {
    private static final java.lang.String TAG = "Usb20ACOutputTerminal";
    private byte mClkSoureID;
    private int mControls;
    private byte mSourceID;
    private byte mTerminalID;

    public Usb20ACOutputTerminal(int i, byte b, byte b2, int i2) {
        super(i, b, b2, i2);
    }

    public byte getSourceID() {
        return this.mSourceID;
    }

    public byte getClkSourceID() {
        return this.mClkSoureID;
    }

    public int getControls() {
        return this.mControls;
    }

    @Override // com.android.server.usb.descriptors.UsbACTerminal
    public byte getTerminalID() {
        return this.mTerminalID;
    }

    @Override // com.android.server.usb.descriptors.UsbACTerminal, com.android.server.usb.descriptors.UsbDescriptor
    public int parseRawDescriptors(com.android.server.usb.descriptors.ByteStream byteStream) {
        super.parseRawDescriptors(byteStream);
        this.mSourceID = byteStream.getByte();
        this.mClkSoureID = byteStream.getByte();
        this.mControls = byteStream.unpackUsbShort();
        this.mTerminalID = byteStream.getByte();
        return this.mLength;
    }

    @Override // com.android.server.usb.descriptors.UsbACTerminal, com.android.server.usb.descriptors.UsbACInterface, com.android.server.usb.descriptors.UsbDescriptor, com.android.server.usb.descriptors.report.Reporting
    public void report(com.android.server.usb.descriptors.report.ReportCanvas reportCanvas) {
        super.report(reportCanvas);
        reportCanvas.openList();
        reportCanvas.writeListItem("Source ID:" + ((int) getSourceID()));
        reportCanvas.writeListItem("Clock Source ID: " + ((int) getClkSourceID()));
        reportCanvas.writeListItem("Controls: " + com.android.server.usb.descriptors.report.ReportCanvas.getHexString(getControls()));
        reportCanvas.writeListItem("Terminal Name ID: " + ((int) getTerminalID()));
        reportCanvas.closeList();
    }
}
