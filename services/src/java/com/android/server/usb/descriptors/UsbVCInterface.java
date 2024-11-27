package com.android.server.usb.descriptors;

/* loaded from: classes2.dex */
public abstract class UsbVCInterface extends com.android.server.usb.descriptors.UsbDescriptor {
    private static final java.lang.String TAG = "UsbVCInterface";
    public static final byte VCI_EXTENSION_UNIT = 6;
    public static final byte VCI_INPUT_TERMINAL = 2;
    public static final byte VCI_OUTPUT_TERMINAL = 3;
    public static final byte VCI_PROCESSING_UNIT = 5;
    public static final byte VCI_SELECTOR_UNIT = 4;
    public static final byte VCI_UNDEFINED = 0;
    public static final byte VCI_VEADER = 1;
    protected final byte mSubtype;

    public UsbVCInterface(int i, byte b, byte b2) {
        super(i, b);
        this.mSubtype = b2;
    }

    public static com.android.server.usb.descriptors.UsbDescriptor allocDescriptor(com.android.server.usb.descriptors.UsbDescriptorParser usbDescriptorParser, com.android.server.usb.descriptors.ByteStream byteStream, int i, byte b) {
        byte b2 = byteStream.getByte();
        usbDescriptorParser.getCurInterface();
        switch (b2) {
            case 0:
            case 6:
                return null;
            case 1:
                int unpackUsbShort = byteStream.unpackUsbShort();
                usbDescriptorParser.setVCInterfaceSpec(unpackUsbShort);
                return new com.android.server.usb.descriptors.UsbVCHeader(i, b, b2, unpackUsbShort);
            case 2:
                return new com.android.server.usb.descriptors.UsbVCInputTerminal(i, b, b2);
            case 3:
                return new com.android.server.usb.descriptors.UsbVCOutputTerminal(i, b, b2);
            case 4:
                return new com.android.server.usb.descriptors.UsbVCSelectorUnit(i, b, b2);
            case 5:
                return new com.android.server.usb.descriptors.UsbVCProcessingUnit(i, b, b2);
            default:
                android.util.Log.w(TAG, "Unknown Video Class Interface subtype: 0x" + java.lang.Integer.toHexString(b2));
                return null;
        }
    }
}
