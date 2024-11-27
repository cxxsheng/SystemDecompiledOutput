package com.android.server.usb.descriptors;

/* loaded from: classes2.dex */
public final class UsbHIDDescriptor extends com.android.server.usb.descriptors.UsbDescriptor {
    private static final java.lang.String TAG = "UsbHIDDescriptor";
    private byte mCountryCode;
    private int mDescriptorLen;
    private byte mDescriptorType;
    private byte mNumDescriptors;
    private int mRelease;

    public UsbHIDDescriptor(int i, byte b) {
        super(i, b);
        this.mHierarchyLevel = 3;
    }

    public int getRelease() {
        return this.mRelease;
    }

    public byte getCountryCode() {
        return this.mCountryCode;
    }

    public byte getNumDescriptors() {
        return this.mNumDescriptors;
    }

    public byte getDescriptorType() {
        return this.mDescriptorType;
    }

    public int getDescriptorLen() {
        return this.mDescriptorLen;
    }

    @Override // com.android.server.usb.descriptors.UsbDescriptor
    public int parseRawDescriptors(com.android.server.usb.descriptors.ByteStream byteStream) {
        this.mRelease = byteStream.unpackUsbShort();
        this.mCountryCode = byteStream.getByte();
        this.mNumDescriptors = byteStream.getByte();
        this.mDescriptorType = byteStream.getByte();
        this.mDescriptorLen = byteStream.unpackUsbShort();
        return this.mLength;
    }

    @Override // com.android.server.usb.descriptors.UsbDescriptor, com.android.server.usb.descriptors.report.Reporting
    public void report(com.android.server.usb.descriptors.report.ReportCanvas reportCanvas) {
        super.report(reportCanvas);
        reportCanvas.openList();
        reportCanvas.writeListItem("Spec: " + com.android.server.usb.descriptors.report.ReportCanvas.getBCDString(getRelease()));
        reportCanvas.writeListItem("Type: " + com.android.server.usb.descriptors.report.ReportCanvas.getBCDString(getDescriptorType()));
        reportCanvas.writeListItem("" + ((int) getNumDescriptors()) + " Descriptors Len: " + getDescriptorLen());
        reportCanvas.closeList();
    }
}
