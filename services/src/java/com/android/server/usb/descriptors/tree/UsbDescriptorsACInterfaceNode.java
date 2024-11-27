package com.android.server.usb.descriptors.tree;

/* loaded from: classes2.dex */
public final class UsbDescriptorsACInterfaceNode extends com.android.server.usb.descriptors.tree.UsbDescriptorsTreeNode {
    private static final java.lang.String TAG = "UsbDescriptorsACInterfaceNode";
    private final com.android.server.usb.descriptors.UsbACInterface mACInterface;

    public UsbDescriptorsACInterfaceNode(com.android.server.usb.descriptors.UsbACInterface usbACInterface) {
        this.mACInterface = usbACInterface;
    }

    @Override // com.android.server.usb.descriptors.tree.UsbDescriptorsTreeNode, com.android.server.usb.descriptors.report.Reporting
    public void report(com.android.server.usb.descriptors.report.ReportCanvas reportCanvas) {
        reportCanvas.writeListItem("AC Interface type: 0x" + java.lang.Integer.toHexString(this.mACInterface.getSubtype()));
        reportCanvas.openList();
        this.mACInterface.report(reportCanvas);
        reportCanvas.closeList();
    }
}
