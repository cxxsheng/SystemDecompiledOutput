package com.android.server.usb.descriptors;

/* loaded from: classes2.dex */
public final class UsbBinaryParser {
    private static final boolean LOGGING = false;
    private static final java.lang.String TAG = "UsbBinaryParser";

    private void dumpDescriptor(com.android.server.usb.descriptors.ByteStream byteStream, int i, byte b, java.lang.StringBuilder sb) {
        sb.append("<p>");
        sb.append("<b> l: " + i + " t:0x" + java.lang.Integer.toHexString(b) + " " + com.android.server.usb.descriptors.report.UsbStrings.getDescriptorName(b) + "</b><br>");
        for (int i2 = 2; i2 < i; i2++) {
            sb.append("0x" + java.lang.Integer.toHexString(byteStream.getByte() & android.hardware.tv.hdmi.cec.CecDeviceType.INACTIVE) + " ");
        }
        sb.append("</p>");
    }

    public void parseDescriptors(android.hardware.usb.UsbDeviceConnection usbDeviceConnection, byte[] bArr, java.lang.StringBuilder sb) {
        sb.append("<tt>");
        com.android.server.usb.descriptors.ByteStream byteStream = new com.android.server.usb.descriptors.ByteStream(bArr);
        while (byteStream.available() > 0) {
            dumpDescriptor(byteStream, byteStream.getByte() & android.hardware.tv.hdmi.cec.CecDeviceType.INACTIVE, byteStream.getByte(), sb);
        }
        sb.append("</tt>");
    }
}
