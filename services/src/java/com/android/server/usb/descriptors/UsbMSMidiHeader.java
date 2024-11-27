package com.android.server.usb.descriptors;

/* loaded from: classes2.dex */
public final class UsbMSMidiHeader extends com.android.server.usb.descriptors.UsbACInterface {
    private static final java.lang.String TAG = "UsbMSMidiHeader";
    private int mMidiStreamingClass;

    public UsbMSMidiHeader(int i, byte b, byte b2, int i2) {
        super(i, b, b2, i2);
    }

    public int getMidiStreamingClass() {
        return this.mMidiStreamingClass;
    }

    @Override // com.android.server.usb.descriptors.UsbDescriptor
    public int parseRawDescriptors(com.android.server.usb.descriptors.ByteStream byteStream) {
        this.mMidiStreamingClass = byteStream.unpackUsbShort();
        byteStream.advance(this.mLength - byteStream.getReadCount());
        return this.mLength;
    }

    @Override // com.android.server.usb.descriptors.UsbACInterface, com.android.server.usb.descriptors.UsbDescriptor, com.android.server.usb.descriptors.report.Reporting
    public void report(com.android.server.usb.descriptors.report.ReportCanvas reportCanvas) {
        super.report(reportCanvas);
        reportCanvas.writeHeader(3, "MS Midi Header: " + com.android.server.usb.descriptors.report.ReportCanvas.getHexString(getType()) + " SubType: " + com.android.server.usb.descriptors.report.ReportCanvas.getHexString(getSubclass()) + " Length: " + getLength() + " MidiStreamingClass :" + getMidiStreamingClass());
    }
}
