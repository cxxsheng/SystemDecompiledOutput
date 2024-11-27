package com.android.server.usb.descriptors;

/* loaded from: classes2.dex */
public final class Usb20ACHeader extends com.android.server.usb.descriptors.UsbACHeaderInterface {
    private static final java.lang.String TAG = "Usb20ACHeader";
    private byte mCategory;
    private byte mControls;

    public Usb20ACHeader(int i, byte b, byte b2, int i2, int i3) {
        super(i, b, b2, i2, i3);
    }

    public byte getCategory() {
        return this.mCategory;
    }

    public byte getControls() {
        return this.mControls;
    }

    @Override // com.android.server.usb.descriptors.UsbDescriptor
    public int parseRawDescriptors(com.android.server.usb.descriptors.ByteStream byteStream) {
        this.mCategory = byteStream.getByte();
        this.mTotalLength = byteStream.unpackUsbShort();
        this.mControls = byteStream.getByte();
        return this.mLength;
    }

    @Override // com.android.server.usb.descriptors.UsbACHeaderInterface, com.android.server.usb.descriptors.UsbACInterface, com.android.server.usb.descriptors.UsbDescriptor, com.android.server.usb.descriptors.report.Reporting
    public void report(com.android.server.usb.descriptors.report.ReportCanvas reportCanvas) {
        super.report(reportCanvas);
        reportCanvas.openList();
        reportCanvas.writeListItem("Category: " + com.android.server.usb.descriptors.report.ReportCanvas.getHexString(getCategory()));
        reportCanvas.writeListItem("Controls: " + com.android.server.usb.descriptors.report.ReportCanvas.getHexString(getControls()));
        reportCanvas.closeList();
    }
}
