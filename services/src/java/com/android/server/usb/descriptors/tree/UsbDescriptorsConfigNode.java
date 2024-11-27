package com.android.server.usb.descriptors.tree;

/* loaded from: classes2.dex */
public final class UsbDescriptorsConfigNode extends com.android.server.usb.descriptors.tree.UsbDescriptorsTreeNode {
    private static final java.lang.String TAG = "UsbDescriptorsConfigNode";
    private final com.android.server.usb.descriptors.UsbConfigDescriptor mConfigDescriptor;
    private final java.util.ArrayList<com.android.server.usb.descriptors.tree.UsbDescriptorsInterfaceNode> mInterfaceNodes = new java.util.ArrayList<>();

    public UsbDescriptorsConfigNode(com.android.server.usb.descriptors.UsbConfigDescriptor usbConfigDescriptor) {
        this.mConfigDescriptor = usbConfigDescriptor;
    }

    public void addInterfaceNode(com.android.server.usb.descriptors.tree.UsbDescriptorsInterfaceNode usbDescriptorsInterfaceNode) {
        this.mInterfaceNodes.add(usbDescriptorsInterfaceNode);
    }

    @Override // com.android.server.usb.descriptors.tree.UsbDescriptorsTreeNode, com.android.server.usb.descriptors.report.Reporting
    public void report(com.android.server.usb.descriptors.report.ReportCanvas reportCanvas) {
        this.mConfigDescriptor.report(reportCanvas);
        reportCanvas.openList();
        java.util.Iterator<com.android.server.usb.descriptors.tree.UsbDescriptorsInterfaceNode> it = this.mInterfaceNodes.iterator();
        while (it.hasNext()) {
            it.next().report(reportCanvas);
        }
        reportCanvas.closeList();
    }
}
