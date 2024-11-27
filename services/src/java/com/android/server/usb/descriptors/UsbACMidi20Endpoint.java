package com.android.server.usb.descriptors;

/* loaded from: classes2.dex */
public final class UsbACMidi20Endpoint extends com.android.server.usb.descriptors.UsbACEndpoint {
    private static final java.lang.String TAG = "UsbACMidi20Endpoint";
    private byte[] mBlockIds;
    private byte mNumGroupTerminals;

    @Override // com.android.server.usb.descriptors.UsbACEndpoint
    public /* bridge */ /* synthetic */ int getSubclass() {
        return super.getSubclass();
    }

    @Override // com.android.server.usb.descriptors.UsbACEndpoint
    public /* bridge */ /* synthetic */ byte getSubtype() {
        return super.getSubtype();
    }

    public UsbACMidi20Endpoint(int i, byte b, int i2, byte b2) {
        super(i, b, i2, b2);
        this.mBlockIds = new byte[0];
    }

    public byte getNumGroupTerminals() {
        return this.mNumGroupTerminals;
    }

    public byte[] getBlockIds() {
        return this.mBlockIds;
    }

    @Override // com.android.server.usb.descriptors.UsbACEndpoint, com.android.server.usb.descriptors.UsbDescriptor
    public int parseRawDescriptors(com.android.server.usb.descriptors.ByteStream byteStream) {
        super.parseRawDescriptors(byteStream);
        this.mNumGroupTerminals = byteStream.getByte();
        if (this.mNumGroupTerminals > 0) {
            this.mBlockIds = new byte[this.mNumGroupTerminals];
            for (int i = 0; i < this.mNumGroupTerminals; i++) {
                this.mBlockIds[i] = byteStream.getByte();
            }
        }
        return this.mLength;
    }

    @Override // com.android.server.usb.descriptors.UsbDescriptor, com.android.server.usb.descriptors.report.Reporting
    public void report(com.android.server.usb.descriptors.report.ReportCanvas reportCanvas) {
        super.report(reportCanvas);
        reportCanvas.writeHeader(3, "AC Midi20 Endpoint: " + com.android.server.usb.descriptors.report.ReportCanvas.getHexString(getType()) + " Length: " + getLength());
        reportCanvas.openList();
        reportCanvas.writeListItem("" + ((int) getNumGroupTerminals()) + " Group Terminals.");
        for (int i = 0; i < getNumGroupTerminals(); i++) {
            reportCanvas.writeListItem("Group Terminal " + i + ": " + ((int) this.mBlockIds[i]));
        }
        reportCanvas.closeList();
    }
}
