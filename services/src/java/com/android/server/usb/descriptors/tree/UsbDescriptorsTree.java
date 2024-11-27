package com.android.server.usb.descriptors.tree;

/* loaded from: classes2.dex */
public final class UsbDescriptorsTree {
    private static final java.lang.String TAG = "UsbDescriptorsTree";
    private com.android.server.usb.descriptors.tree.UsbDescriptorsConfigNode mConfigNode;
    private com.android.server.usb.descriptors.tree.UsbDescriptorsDeviceNode mDeviceNode;
    private com.android.server.usb.descriptors.tree.UsbDescriptorsInterfaceNode mInterfaceNode;

    private void addDeviceDescriptor(com.android.server.usb.descriptors.UsbDeviceDescriptor usbDeviceDescriptor) {
        this.mDeviceNode = new com.android.server.usb.descriptors.tree.UsbDescriptorsDeviceNode(usbDeviceDescriptor);
    }

    private void addConfigDescriptor(com.android.server.usb.descriptors.UsbConfigDescriptor usbConfigDescriptor) {
        this.mConfigNode = new com.android.server.usb.descriptors.tree.UsbDescriptorsConfigNode(usbConfigDescriptor);
        this.mDeviceNode.addConfigDescriptorNode(this.mConfigNode);
    }

    private void addInterfaceDescriptor(com.android.server.usb.descriptors.UsbInterfaceDescriptor usbInterfaceDescriptor) {
        this.mInterfaceNode = new com.android.server.usb.descriptors.tree.UsbDescriptorsInterfaceNode(usbInterfaceDescriptor);
        this.mConfigNode.addInterfaceNode(this.mInterfaceNode);
    }

    private void addEndpointDescriptor(com.android.server.usb.descriptors.UsbEndpointDescriptor usbEndpointDescriptor) {
        this.mInterfaceNode.addEndpointNode(new com.android.server.usb.descriptors.tree.UsbDescriptorsEndpointNode(usbEndpointDescriptor));
    }

    private void addACInterface(com.android.server.usb.descriptors.UsbACInterface usbACInterface) {
        this.mInterfaceNode.addACInterfaceNode(new com.android.server.usb.descriptors.tree.UsbDescriptorsACInterfaceNode(usbACInterface));
    }

    public void parse(com.android.server.usb.descriptors.UsbDescriptorParser usbDescriptorParser) {
        java.util.ArrayList<com.android.server.usb.descriptors.UsbDescriptor> descriptors = usbDescriptorParser.getDescriptors();
        for (int i = 0; i < descriptors.size(); i++) {
            com.android.server.usb.descriptors.UsbDescriptor usbDescriptor = descriptors.get(i);
            switch (usbDescriptor.getType()) {
                case 1:
                    addDeviceDescriptor((com.android.server.usb.descriptors.UsbDeviceDescriptor) usbDescriptor);
                    break;
                case 2:
                    addConfigDescriptor((com.android.server.usb.descriptors.UsbConfigDescriptor) usbDescriptor);
                    break;
                case 4:
                    addInterfaceDescriptor((com.android.server.usb.descriptors.UsbInterfaceDescriptor) usbDescriptor);
                    break;
                case 5:
                    addEndpointDescriptor((com.android.server.usb.descriptors.UsbEndpointDescriptor) usbDescriptor);
                    break;
            }
        }
    }

    public void report(com.android.server.usb.descriptors.report.ReportCanvas reportCanvas) {
        this.mDeviceNode.report(reportCanvas);
    }
}
