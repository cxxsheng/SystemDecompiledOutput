package com.android.server.usb.descriptors.report;

/* loaded from: classes2.dex */
public final class HTMLReportCanvas extends com.android.server.usb.descriptors.report.ReportCanvas {
    private static final java.lang.String TAG = "HTMLReportCanvas";
    private final java.lang.StringBuilder mStringBuilder;

    public HTMLReportCanvas(com.android.server.usb.descriptors.UsbDescriptorParser usbDescriptorParser, java.lang.StringBuilder sb) {
        super(usbDescriptorParser);
        this.mStringBuilder = sb;
    }

    @Override // com.android.server.usb.descriptors.report.ReportCanvas
    public void write(java.lang.String str) {
        this.mStringBuilder.append(str);
    }

    @Override // com.android.server.usb.descriptors.report.ReportCanvas
    public void openHeader(int i) {
        java.lang.StringBuilder sb = this.mStringBuilder;
        sb.append("<h");
        sb.append(i);
        sb.append('>');
    }

    @Override // com.android.server.usb.descriptors.report.ReportCanvas
    public void closeHeader(int i) {
        java.lang.StringBuilder sb = this.mStringBuilder;
        sb.append("</h");
        sb.append(i);
        sb.append('>');
    }

    @Override // com.android.server.usb.descriptors.report.ReportCanvas
    public void openParagraph(boolean z) {
        if (z) {
            this.mStringBuilder.append("<p style=\"color:red\">");
        } else {
            this.mStringBuilder.append("<p>");
        }
    }

    @Override // com.android.server.usb.descriptors.report.ReportCanvas
    public void closeParagraph() {
        this.mStringBuilder.append("</p>");
    }

    @Override // com.android.server.usb.descriptors.report.ReportCanvas
    public void writeParagraph(java.lang.String str, boolean z) {
        openParagraph(z);
        this.mStringBuilder.append(str);
        closeParagraph();
    }

    @Override // com.android.server.usb.descriptors.report.ReportCanvas
    public void openList() {
        this.mStringBuilder.append("<ul>");
    }

    @Override // com.android.server.usb.descriptors.report.ReportCanvas
    public void closeList() {
        this.mStringBuilder.append("</ul>");
    }

    @Override // com.android.server.usb.descriptors.report.ReportCanvas
    public void openListItem() {
        this.mStringBuilder.append("<li>");
    }

    @Override // com.android.server.usb.descriptors.report.ReportCanvas
    public void closeListItem() {
        this.mStringBuilder.append("</li>");
    }
}
