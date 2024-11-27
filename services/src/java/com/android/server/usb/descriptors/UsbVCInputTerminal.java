package com.android.server.usb.descriptors;

/* loaded from: classes2.dex */
public final class UsbVCInputTerminal extends com.android.server.usb.descriptors.UsbVCInterface {
    private static final java.lang.String TAG = "UsbVCInputTerminal";

    public UsbVCInputTerminal(int i, byte b, byte b2) {
        super(i, b, b2);
    }

    @Override // com.android.server.usb.descriptors.UsbDescriptor
    public int parseRawDescriptors(com.android.server.usb.descriptors.ByteStream byteStream) {
        return super.parseRawDescriptors(byteStream);
    }

    @Override // com.android.server.usb.descriptors.UsbDescriptor, com.android.server.usb.descriptors.report.Reporting
    public void report(com.android.server.usb.descriptors.report.ReportCanvas reportCanvas) {
        super.report(reportCanvas);
    }
}
