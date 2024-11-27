package com.android.server.usb.descriptors.tree;

/* loaded from: classes2.dex */
public final class UsbDescriptorsDeviceNode extends com.android.server.usb.descriptors.tree.UsbDescriptorsTreeNode {
    private static final java.lang.String TAG = "UsbDescriptorsDeviceNode";
    private final java.util.ArrayList<com.android.server.usb.descriptors.tree.UsbDescriptorsConfigNode> mConfigNodes = new java.util.ArrayList<>();
    private final com.android.server.usb.descriptors.UsbDeviceDescriptor mDeviceDescriptor;

    public UsbDescriptorsDeviceNode(com.android.server.usb.descriptors.UsbDeviceDescriptor usbDeviceDescriptor) {
        this.mDeviceDescriptor = usbDeviceDescriptor;
    }

    public void addConfigDescriptorNode(com.android.server.usb.descriptors.tree.UsbDescriptorsConfigNode usbDescriptorsConfigNode) {
        this.mConfigNodes.add(usbDescriptorsConfigNode);
    }

    @Override // com.android.server.usb.descriptors.tree.UsbDescriptorsTreeNode, com.android.server.usb.descriptors.report.Reporting
    public void report(com.android.server.usb.descriptors.report.ReportCanvas reportCanvas) {
        this.mDeviceDescriptor.report(reportCanvas);
        java.util.Iterator<com.android.server.usb.descriptors.tree.UsbDescriptorsConfigNode> it = this.mConfigNodes.iterator();
        while (it.hasNext()) {
            it.next().report(reportCanvas);
        }
    }
}
