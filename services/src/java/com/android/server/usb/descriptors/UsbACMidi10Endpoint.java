package com.android.server.usb.descriptors;

/* loaded from: classes2.dex */
public final class UsbACMidi10Endpoint extends com.android.server.usb.descriptors.UsbACEndpoint {
    private static final java.lang.String TAG = "UsbACMidi10Endpoint";
    private byte[] mJackIds;
    private byte mNumJacks;

    @Override // com.android.server.usb.descriptors.UsbACEndpoint
    public /* bridge */ /* synthetic */ int getSubclass() {
        return super.getSubclass();
    }

    @Override // com.android.server.usb.descriptors.UsbACEndpoint
    public /* bridge */ /* synthetic */ byte getSubtype() {
        return super.getSubtype();
    }

    public UsbACMidi10Endpoint(int i, byte b, int i2, byte b2) {
        super(i, b, i2, b2);
        this.mJackIds = new byte[0];
    }

    public byte getNumJacks() {
        return this.mNumJacks;
    }

    public byte[] getJackIds() {
        return this.mJackIds;
    }

    @Override // com.android.server.usb.descriptors.UsbACEndpoint, com.android.server.usb.descriptors.UsbDescriptor
    public int parseRawDescriptors(com.android.server.usb.descriptors.ByteStream byteStream) {
        super.parseRawDescriptors(byteStream);
        this.mNumJacks = byteStream.getByte();
        if (this.mNumJacks > 0) {
            this.mJackIds = new byte[this.mNumJacks];
            for (int i = 0; i < this.mNumJacks; i++) {
                this.mJackIds[i] = byteStream.getByte();
            }
        }
        return this.mLength;
    }

    @Override // com.android.server.usb.descriptors.UsbDescriptor, com.android.server.usb.descriptors.report.Reporting
    public void report(com.android.server.usb.descriptors.report.ReportCanvas reportCanvas) {
        super.report(reportCanvas);
        reportCanvas.writeHeader(3, "ACMidi10Endpoint: " + com.android.server.usb.descriptors.report.ReportCanvas.getHexString(getType()) + " Length: " + getLength());
        reportCanvas.openList();
        reportCanvas.writeListItem("" + ((int) getNumJacks()) + " Jacks.");
        for (int i = 0; i < getNumJacks(); i++) {
            reportCanvas.writeListItem("Jack " + i + ": " + ((int) this.mJackIds[i]));
        }
        reportCanvas.closeList();
    }
}
