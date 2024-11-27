package com.android.server.usb.descriptors;

/* loaded from: classes2.dex */
public final class UsbVCHeader extends com.android.server.usb.descriptors.UsbVCHeaderInterface {
    private static final java.lang.String TAG = "UsbVCHeader";

    public UsbVCHeader(int i, byte b, byte b2, int i2) {
        super(i, b, b2, i2);
    }

    @Override // com.android.server.usb.descriptors.UsbDescriptor
    public int parseRawDescriptors(com.android.server.usb.descriptors.ByteStream byteStream) {
        return super.parseRawDescriptors(byteStream);
    }

    @Override // com.android.server.usb.descriptors.UsbVCHeaderInterface, com.android.server.usb.descriptors.UsbDescriptor, com.android.server.usb.descriptors.report.Reporting
    public void report(com.android.server.usb.descriptors.report.ReportCanvas reportCanvas) {
        super.report(reportCanvas);
    }
}
