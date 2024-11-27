package com.android.server.usb.descriptors;

/* loaded from: classes2.dex */
public final class Usb10ACHeader extends com.android.server.usb.descriptors.UsbACHeaderInterface {
    private static final java.lang.String TAG = "Usb10ACHeader";
    private byte mControls;
    private byte[] mInterfaceNums;
    private byte mNumInterfaces;

    public Usb10ACHeader(int i, byte b, byte b2, int i2, int i3) {
        super(i, b, b2, i2, i3);
        this.mNumInterfaces = (byte) 0;
        this.mInterfaceNums = null;
    }

    public byte getNumInterfaces() {
        return this.mNumInterfaces;
    }

    public byte[] getInterfaceNums() {
        return this.mInterfaceNums;
    }

    public byte getControls() {
        return this.mControls;
    }

    @Override // com.android.server.usb.descriptors.UsbDescriptor
    public int parseRawDescriptors(com.android.server.usb.descriptors.ByteStream byteStream) {
        this.mTotalLength = byteStream.unpackUsbShort();
        if (this.mADCRelease >= 512) {
            this.mControls = byteStream.getByte();
        } else {
            this.mNumInterfaces = byteStream.getByte();
            this.mInterfaceNums = new byte[this.mNumInterfaces];
            for (int i = 0; i < this.mNumInterfaces; i++) {
                this.mInterfaceNums[i] = byteStream.getByte();
            }
        }
        return this.mLength;
    }

    @Override // com.android.server.usb.descriptors.UsbACHeaderInterface, com.android.server.usb.descriptors.UsbACInterface, com.android.server.usb.descriptors.UsbDescriptor, com.android.server.usb.descriptors.report.Reporting
    public void report(com.android.server.usb.descriptors.report.ReportCanvas reportCanvas) {
        super.report(reportCanvas);
        reportCanvas.openList();
        byte numInterfaces = getNumInterfaces();
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append("" + ((int) numInterfaces) + " Interfaces");
        if (numInterfaces > 0) {
            sb.append(" [");
            byte[] interfaceNums = getInterfaceNums();
            if (interfaceNums != null) {
                for (int i = 0; i < numInterfaces; i++) {
                    sb.append("" + ((int) interfaceNums[i]));
                    if (i < numInterfaces - 1) {
                        sb.append(" ");
                    }
                }
            }
            sb.append("]");
        }
        reportCanvas.writeListItem(sb.toString());
        reportCanvas.writeListItem("Controls: " + com.android.server.usb.descriptors.report.ReportCanvas.getHexString(getControls()));
        reportCanvas.closeList();
    }
}
