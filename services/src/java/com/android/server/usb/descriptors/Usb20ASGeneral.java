package com.android.server.usb.descriptors;

/* loaded from: classes2.dex */
public final class Usb20ASGeneral extends com.android.server.usb.descriptors.UsbACInterface implements com.android.server.usb.descriptors.UsbAudioChannelCluster {
    private static final java.lang.String TAG = "Usb20ASGeneral";
    private int mChannelConfig;
    private byte mChannelNames;
    private byte mControls;
    private byte mFormatType;
    private int mFormats;
    private byte mNumChannels;
    private byte mTerminalLink;

    public Usb20ASGeneral(int i, byte b, byte b2, int i2) {
        super(i, b, b2, i2);
    }

    public byte getTerminalLink() {
        return this.mTerminalLink;
    }

    public byte getControls() {
        return this.mControls;
    }

    public byte getFormatType() {
        return this.mFormatType;
    }

    public int getFormats() {
        return this.mFormats;
    }

    @Override // com.android.server.usb.descriptors.UsbAudioChannelCluster
    public byte getChannelCount() {
        return this.mNumChannels;
    }

    @Override // com.android.server.usb.descriptors.UsbAudioChannelCluster
    public int getChannelConfig() {
        return this.mChannelConfig;
    }

    @Override // com.android.server.usb.descriptors.UsbAudioChannelCluster
    public byte getChannelNames() {
        return this.mChannelNames;
    }

    @Override // com.android.server.usb.descriptors.UsbDescriptor
    public int parseRawDescriptors(com.android.server.usb.descriptors.ByteStream byteStream) {
        this.mTerminalLink = byteStream.getByte();
        this.mControls = byteStream.getByte();
        this.mFormatType = byteStream.getByte();
        this.mFormats = byteStream.unpackUsbInt();
        this.mNumChannels = byteStream.getByte();
        this.mChannelConfig = byteStream.unpackUsbInt();
        this.mChannelNames = byteStream.getByte();
        return this.mLength;
    }

    @Override // com.android.server.usb.descriptors.UsbACInterface, com.android.server.usb.descriptors.UsbDescriptor, com.android.server.usb.descriptors.report.Reporting
    public void report(com.android.server.usb.descriptors.report.ReportCanvas reportCanvas) {
        super.report(reportCanvas);
        reportCanvas.openList();
        reportCanvas.writeListItem("Terminal Link: " + ((int) getTerminalLink()));
        reportCanvas.writeListItem("Controls: " + com.android.server.usb.descriptors.report.ReportCanvas.getHexString(getControls()));
        reportCanvas.writeListItem("Format Type: " + com.android.server.usb.descriptors.report.ReportCanvas.getHexString(getFormatType()));
        reportCanvas.writeListItem("Formats: " + com.android.server.usb.descriptors.report.ReportCanvas.getHexString(getFormats()));
        reportCanvas.writeListItem("Channel Count: " + ((int) getChannelCount()));
        reportCanvas.writeListItem("Channel Config: " + com.android.server.usb.descriptors.report.ReportCanvas.getHexString(getChannelConfig()));
        reportCanvas.writeListItem("Channel Names String ID: " + ((int) getChannelNames()));
        reportCanvas.closeList();
    }
}
