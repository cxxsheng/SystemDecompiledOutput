package com.android.server.usb.descriptors.report;

/* loaded from: classes2.dex */
public abstract class ReportCanvas {
    private static final java.lang.String TAG = "ReportCanvas";
    private final com.android.server.usb.descriptors.UsbDescriptorParser mParser;

    public abstract void closeHeader(int i);

    public abstract void closeList();

    public abstract void closeListItem();

    public abstract void closeParagraph();

    public abstract void openHeader(int i);

    public abstract void openList();

    public abstract void openListItem();

    public abstract void openParagraph(boolean z);

    public abstract void write(java.lang.String str);

    public abstract void writeParagraph(java.lang.String str, boolean z);

    public ReportCanvas(com.android.server.usb.descriptors.UsbDescriptorParser usbDescriptorParser) {
        this.mParser = usbDescriptorParser;
    }

    public com.android.server.usb.descriptors.UsbDescriptorParser getParser() {
        return this.mParser;
    }

    public void writeHeader(int i, java.lang.String str) {
        openHeader(i);
        write(str);
        closeHeader(i);
    }

    public void writeListItem(java.lang.String str) {
        openListItem();
        write(str);
        closeListItem();
    }

    public static java.lang.String getHexString(byte b) {
        return "0x" + java.lang.Integer.toHexString(b & android.hardware.tv.hdmi.cec.CecDeviceType.INACTIVE).toUpperCase();
    }

    public static java.lang.String getBCDString(int i) {
        return "" + ((i >> 8) & 15) + "." + ((i >> 4) & 15) + (i & 15);
    }

    public static java.lang.String getHexString(int i) {
        return "0x" + java.lang.Integer.toHexString(i & com.android.server.location.gnss.hal.GnssNative.GNSS_AIDING_TYPE_ALL).toUpperCase();
    }

    public void dumpHexArray(byte[] bArr, java.lang.StringBuilder sb) {
        if (bArr != null) {
            openParagraph(false);
            for (byte b : bArr) {
                sb.append(getHexString(b) + " ");
            }
            closeParagraph();
        }
    }
}
