package com.android.server.usb.descriptors;

/* loaded from: classes2.dex */
public class UsbASFormat extends com.android.server.usb.descriptors.UsbACInterface {
    public static final byte EXT_FORMAT_TYPE_I = -127;
    public static final byte EXT_FORMAT_TYPE_II = -126;
    public static final byte EXT_FORMAT_TYPE_III = -125;
    public static final byte FORMAT_TYPE_I = 1;
    public static final byte FORMAT_TYPE_II = 2;
    public static final byte FORMAT_TYPE_III = 3;
    public static final byte FORMAT_TYPE_IV = 4;
    private static final java.lang.String TAG = "UsbASFormat";
    private final byte mFormatType;

    public UsbASFormat(int i, byte b, byte b2, byte b3, int i2) {
        super(i, b, b2, i2);
        this.mFormatType = b3;
    }

    public byte getFormatType() {
        return this.mFormatType;
    }

    public int[] getSampleRates() {
        return null;
    }

    public int[] getBitDepths() {
        return null;
    }

    public int[] getChannelCounts() {
        return null;
    }

    public static com.android.server.usb.descriptors.UsbDescriptor allocDescriptor(com.android.server.usb.descriptors.UsbDescriptorParser usbDescriptorParser, com.android.server.usb.descriptors.ByteStream byteStream, int i, byte b, byte b2, int i2) {
        byte b3 = byteStream.getByte();
        int aCInterfaceSpec = usbDescriptorParser.getACInterfaceSpec();
        switch (b3) {
            case 1:
                if (aCInterfaceSpec == 512) {
                    return new com.android.server.usb.descriptors.Usb20ASFormatI(i, b, b2, b3, i2);
                }
                return new com.android.server.usb.descriptors.Usb10ASFormatI(i, b, b2, b3, i2);
            case 2:
                if (aCInterfaceSpec == 512) {
                    return new com.android.server.usb.descriptors.Usb20ASFormatII(i, b, b2, b3, i2);
                }
                return new com.android.server.usb.descriptors.Usb10ASFormatII(i, b, b2, b3, i2);
            case 3:
                return new com.android.server.usb.descriptors.Usb20ASFormatIII(i, b, b2, b3, i2);
            default:
                return new com.android.server.usb.descriptors.UsbASFormat(i, b, b2, b3, i2);
        }
    }

    @Override // com.android.server.usb.descriptors.UsbACInterface, com.android.server.usb.descriptors.UsbDescriptor, com.android.server.usb.descriptors.report.Reporting
    public void report(com.android.server.usb.descriptors.report.ReportCanvas reportCanvas) {
        super.report(reportCanvas);
        reportCanvas.writeParagraph(com.android.server.usb.descriptors.report.UsbStrings.getFormatName(getFormatType()), false);
    }
}
