package com.android.server.usb.descriptors;

/* loaded from: classes2.dex */
abstract class UsbACEndpoint extends com.android.server.usb.descriptors.UsbDescriptor {
    public static final byte MS_GENERAL = 1;
    public static final byte MS_GENERAL_2_0 = 2;
    private static final java.lang.String TAG = "UsbACEndpoint";
    protected final int mSubclass;
    protected final byte mSubtype;

    UsbACEndpoint(int i, byte b, int i2, byte b2) {
        super(i, b);
        this.mSubclass = i2;
        this.mSubtype = b2;
    }

    public int getSubclass() {
        return this.mSubclass;
    }

    public byte getSubtype() {
        return this.mSubtype;
    }

    @Override // com.android.server.usb.descriptors.UsbDescriptor
    public int parseRawDescriptors(com.android.server.usb.descriptors.ByteStream byteStream) {
        return this.mLength;
    }

    public static com.android.server.usb.descriptors.UsbDescriptor allocDescriptor(com.android.server.usb.descriptors.UsbDescriptorParser usbDescriptorParser, int i, byte b, byte b2) {
        int usbSubclass = usbDescriptorParser.getCurInterface().getUsbSubclass();
        switch (usbSubclass) {
            case 1:
                break;
            case 2:
                break;
            case 3:
                switch (b2) {
                    case 1:
                        break;
                    case 2:
                        break;
                    default:
                        android.util.Log.w(TAG, "Unknown Midi Endpoint id:0x" + java.lang.Integer.toHexString(b2));
                        break;
                }
            default:
                android.util.Log.w(TAG, "Unknown Audio Class Endpoint id:0x" + java.lang.Integer.toHexString(usbSubclass));
                break;
        }
        return null;
    }
}
