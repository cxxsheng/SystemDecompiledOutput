package com.android.server.usb.hal.gadget;

/* loaded from: classes2.dex */
public final class UsbGadgetHalInstance {
    public static com.android.server.usb.hal.gadget.UsbGadgetHal getInstance(com.android.server.usb.UsbDeviceManager usbDeviceManager, com.android.internal.util.IndentingPrintWriter indentingPrintWriter) {
        com.android.server.usb.UsbPortManager.logAndPrint(3, indentingPrintWriter, "Querying USB Gadget HAL version");
        if (com.android.server.usb.hal.gadget.UsbGadgetAidl.isServicePresent(null)) {
            com.android.server.usb.UsbPortManager.logAndPrint(4, indentingPrintWriter, "USB Gadget HAL AIDL present");
            return new com.android.server.usb.hal.gadget.UsbGadgetAidl(usbDeviceManager, indentingPrintWriter);
        }
        if (com.android.server.usb.hal.gadget.UsbGadgetHidl.isServicePresent(null)) {
            com.android.server.usb.UsbPortManager.logAndPrint(4, indentingPrintWriter, "USB Gadget HAL HIDL present");
            return new com.android.server.usb.hal.gadget.UsbGadgetHidl(usbDeviceManager, indentingPrintWriter);
        }
        com.android.server.usb.UsbPortManager.logAndPrint(6, indentingPrintWriter, "USB Gadget HAL AIDL/HIDL not present");
        return null;
    }
}
