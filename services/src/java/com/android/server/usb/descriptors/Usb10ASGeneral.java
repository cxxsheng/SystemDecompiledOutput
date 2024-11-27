package com.android.server.usb.descriptors;

/* loaded from: classes2.dex */
public final class Usb10ASGeneral extends com.android.server.usb.descriptors.UsbACInterface {
    private static final java.lang.String TAG = "Usb10ASGeneral";
    private byte mDelay;
    private int mFormatTag;
    private byte mTerminalLink;

    public Usb10ASGeneral(int i, byte b, byte b2, int i2) {
        super(i, b, b2, i2);
    }

    public byte getTerminalLink() {
        return this.mTerminalLink;
    }

    public byte getDelay() {
        return this.mDelay;
    }

    public int getFormatTag() {
        return this.mFormatTag;
    }

    @Override // com.android.server.usb.descriptors.UsbDescriptor
    public int parseRawDescriptors(com.android.server.usb.descriptors.ByteStream byteStream) {
        this.mTerminalLink = byteStream.getByte();
        this.mDelay = byteStream.getByte();
        this.mFormatTag = byteStream.unpackUsbShort();
        return this.mLength;
    }

    @Override // com.android.server.usb.descriptors.UsbACInterface, com.android.server.usb.descriptors.UsbDescriptor, com.android.server.usb.descriptors.report.Reporting
    public void report(com.android.server.usb.descriptors.report.ReportCanvas reportCanvas) {
        super.report(reportCanvas);
        reportCanvas.openList();
        reportCanvas.writeListItem("Delay: " + ((int) this.mDelay));
        reportCanvas.writeListItem("Terminal Link: " + ((int) this.mTerminalLink));
        reportCanvas.writeListItem("Format: " + com.android.server.usb.descriptors.report.UsbStrings.getAudioFormatName(this.mFormatTag) + " - " + com.android.server.usb.descriptors.report.ReportCanvas.getHexString(this.mFormatTag));
        reportCanvas.closeList();
    }
}
