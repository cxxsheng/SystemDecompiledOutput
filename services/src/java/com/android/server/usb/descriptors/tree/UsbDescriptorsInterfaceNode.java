package com.android.server.usb.descriptors.tree;

/* loaded from: classes2.dex */
public final class UsbDescriptorsInterfaceNode extends com.android.server.usb.descriptors.tree.UsbDescriptorsTreeNode {
    private static final java.lang.String TAG = "UsbDescriptorsInterfaceNode";
    private final com.android.server.usb.descriptors.UsbInterfaceDescriptor mInterfaceDescriptor;
    private final java.util.ArrayList<com.android.server.usb.descriptors.tree.UsbDescriptorsEndpointNode> mEndpointNodes = new java.util.ArrayList<>();
    private final java.util.ArrayList<com.android.server.usb.descriptors.tree.UsbDescriptorsACInterfaceNode> mACInterfaceNodes = new java.util.ArrayList<>();

    public UsbDescriptorsInterfaceNode(com.android.server.usb.descriptors.UsbInterfaceDescriptor usbInterfaceDescriptor) {
        this.mInterfaceDescriptor = usbInterfaceDescriptor;
    }

    public void addEndpointNode(com.android.server.usb.descriptors.tree.UsbDescriptorsEndpointNode usbDescriptorsEndpointNode) {
        this.mEndpointNodes.add(usbDescriptorsEndpointNode);
    }

    public void addACInterfaceNode(com.android.server.usb.descriptors.tree.UsbDescriptorsACInterfaceNode usbDescriptorsACInterfaceNode) {
        this.mACInterfaceNodes.add(usbDescriptorsACInterfaceNode);
    }

    @Override // com.android.server.usb.descriptors.tree.UsbDescriptorsTreeNode, com.android.server.usb.descriptors.report.Reporting
    public void report(com.android.server.usb.descriptors.report.ReportCanvas reportCanvas) {
        this.mInterfaceDescriptor.report(reportCanvas);
        if (this.mACInterfaceNodes.size() > 0) {
            reportCanvas.writeParagraph("Audio Class Interfaces", false);
            reportCanvas.openList();
            java.util.Iterator<com.android.server.usb.descriptors.tree.UsbDescriptorsACInterfaceNode> it = this.mACInterfaceNodes.iterator();
            while (it.hasNext()) {
                it.next().report(reportCanvas);
            }
            reportCanvas.closeList();
        }
        if (this.mEndpointNodes.size() > 0) {
            reportCanvas.writeParagraph("Endpoints", false);
            reportCanvas.openList();
            java.util.Iterator<com.android.server.usb.descriptors.tree.UsbDescriptorsEndpointNode> it2 = this.mEndpointNodes.iterator();
            while (it2.hasNext()) {
                it2.next().report(reportCanvas);
            }
            reportCanvas.closeList();
        }
    }
}
