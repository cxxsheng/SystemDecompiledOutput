package com.android.server.usb.descriptors;

/* loaded from: classes2.dex */
public abstract class UsbACHeaderInterface extends com.android.server.usb.descriptors.UsbACInterface {
    private static final java.lang.String TAG = "UsbACHeaderInterface";
    protected int mADCRelease;
    protected int mTotalLength;

    public UsbACHeaderInterface(int i, byte b, byte b2, int i2, int i3) {
        super(i, b, b2, i2);
        this.mADCRelease = i3;
    }

    public int getADCRelease() {
        return this.mADCRelease;
    }

    public int getTotalLength() {
        return this.mTotalLength;
    }

    @Override // com.android.server.usb.descriptors.UsbACInterface, com.android.server.usb.descriptors.UsbDescriptor, com.android.server.usb.descriptors.report.Reporting
    public void report(com.android.server.usb.descriptors.report.ReportCanvas reportCanvas) {
        super.report(reportCanvas);
        reportCanvas.openList();
        reportCanvas.writeListItem("Release: " + com.android.server.usb.descriptors.report.ReportCanvas.getBCDString(getADCRelease()));
        reportCanvas.writeListItem("Total Length: " + getTotalLength());
        reportCanvas.closeList();
    }
}
