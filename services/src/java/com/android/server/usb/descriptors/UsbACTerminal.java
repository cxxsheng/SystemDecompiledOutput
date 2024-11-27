package com.android.server.usb.descriptors;

/* loaded from: classes2.dex */
public abstract class UsbACTerminal extends com.android.server.usb.descriptors.UsbACInterface {
    private static final java.lang.String TAG = "UsbACTerminal";
    protected byte mAssocTerminal;
    protected byte mTerminalID;
    protected int mTerminalType;

    public UsbACTerminal(int i, byte b, byte b2, int i2) {
        super(i, b, b2, i2);
    }

    public byte getTerminalID() {
        return this.mTerminalID;
    }

    public int getTerminalType() {
        return this.mTerminalType;
    }

    public byte getAssocTerminal() {
        return this.mAssocTerminal;
    }

    @Override // com.android.server.usb.descriptors.UsbDescriptor
    public int parseRawDescriptors(com.android.server.usb.descriptors.ByteStream byteStream) {
        this.mTerminalID = byteStream.getByte();
        this.mTerminalType = byteStream.unpackUsbShort();
        this.mAssocTerminal = byteStream.getByte();
        return this.mLength;
    }

    @Override // com.android.server.usb.descriptors.UsbACInterface, com.android.server.usb.descriptors.UsbDescriptor, com.android.server.usb.descriptors.report.Reporting
    public void report(com.android.server.usb.descriptors.report.ReportCanvas reportCanvas) {
        super.report(reportCanvas);
        reportCanvas.openList();
        int terminalType = getTerminalType();
        reportCanvas.writeListItem("Type: " + com.android.server.usb.descriptors.report.ReportCanvas.getHexString(terminalType) + ": " + com.android.server.usb.descriptors.report.UsbStrings.getTerminalName(terminalType));
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append("ID: ");
        sb.append(com.android.server.usb.descriptors.report.ReportCanvas.getHexString(getTerminalID()));
        reportCanvas.writeListItem(sb.toString());
        reportCanvas.writeListItem("Associated terminal: " + com.android.server.usb.descriptors.report.ReportCanvas.getHexString(getAssocTerminal()));
        reportCanvas.closeList();
    }
}
