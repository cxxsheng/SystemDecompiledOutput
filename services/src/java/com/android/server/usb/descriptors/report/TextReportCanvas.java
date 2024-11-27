package com.android.server.usb.descriptors.report;

/* loaded from: classes2.dex */
public final class TextReportCanvas extends com.android.server.usb.descriptors.report.ReportCanvas {
    private static final int LIST_INDENT_AMNT = 2;
    private static final java.lang.String TAG = "TextReportCanvas";
    private int mListIndent;
    private final java.lang.StringBuilder mStringBuilder;

    public TextReportCanvas(com.android.server.usb.descriptors.UsbDescriptorParser usbDescriptorParser, java.lang.StringBuilder sb) {
        super(usbDescriptorParser);
        this.mStringBuilder = sb;
    }

    private void writeListIndent() {
        for (int i = 0; i < this.mListIndent; i++) {
            this.mStringBuilder.append(" ");
        }
    }

    @Override // com.android.server.usb.descriptors.report.ReportCanvas
    public void write(java.lang.String str) {
        this.mStringBuilder.append(str);
    }

    @Override // com.android.server.usb.descriptors.report.ReportCanvas
    public void openHeader(int i) {
        writeListIndent();
        this.mStringBuilder.append("[");
    }

    @Override // com.android.server.usb.descriptors.report.ReportCanvas
    public void closeHeader(int i) {
        this.mStringBuilder.append("]\n");
    }

    @Override // com.android.server.usb.descriptors.report.ReportCanvas
    public void openParagraph(boolean z) {
        writeListIndent();
    }

    @Override // com.android.server.usb.descriptors.report.ReportCanvas
    public void closeParagraph() {
        this.mStringBuilder.append("\n");
    }

    @Override // com.android.server.usb.descriptors.report.ReportCanvas
    public void writeParagraph(java.lang.String str, boolean z) {
        openParagraph(z);
        if (z) {
            this.mStringBuilder.append(com.android.server.am.SettingsToPropertiesMapper.NAMESPACE_REBOOT_STAGING_DELIMITER + str + com.android.server.am.SettingsToPropertiesMapper.NAMESPACE_REBOOT_STAGING_DELIMITER);
        } else {
            this.mStringBuilder.append(str);
        }
        closeParagraph();
    }

    @Override // com.android.server.usb.descriptors.report.ReportCanvas
    public void openList() {
        this.mListIndent += 2;
    }

    @Override // com.android.server.usb.descriptors.report.ReportCanvas
    public void closeList() {
        this.mListIndent -= 2;
    }

    @Override // com.android.server.usb.descriptors.report.ReportCanvas
    public void openListItem() {
        writeListIndent();
        this.mStringBuilder.append("- ");
    }

    @Override // com.android.server.usb.descriptors.report.ReportCanvas
    public void closeListItem() {
        this.mStringBuilder.append("\n");
    }
}
