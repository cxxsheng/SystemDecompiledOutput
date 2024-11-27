package com.android.server.usb.descriptors;

/* loaded from: classes2.dex */
abstract class UsbVCEndpoint extends com.android.server.usb.descriptors.UsbDescriptor {
    private static final java.lang.String TAG = "UsbVCEndpoint";
    public static final byte VCEP_ENDPOINT = 2;
    public static final byte VCEP_GENERAL = 1;
    public static final byte VCEP_INTERRUPT = 3;
    public static final byte VCEP_UNDEFINED = 0;

    UsbVCEndpoint(int i, byte b) {
        super(i, b);
    }

    public static com.android.server.usb.descriptors.UsbDescriptor allocDescriptor(com.android.server.usb.descriptors.UsbDescriptorParser usbDescriptorParser, int i, byte b, byte b2) {
        usbDescriptorParser.getCurInterface();
        switch (b2) {
            case 0:
                break;
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
            default:
                android.util.Log.w(TAG, "Unknown Video Class Endpoint id:0x" + java.lang.Integer.toHexString(b2));
                break;
        }
        return null;
    }
}
