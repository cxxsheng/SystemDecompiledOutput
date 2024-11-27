package com.android.server.usb.descriptors;

/* loaded from: classes2.dex */
public class UsbACAudioStreamEndpoint extends com.android.server.usb.descriptors.UsbACEndpoint {
    private static final java.lang.String TAG = "UsbACAudioStreamEndpoint";

    @Override // com.android.server.usb.descriptors.UsbACEndpoint
    public /* bridge */ /* synthetic */ int getSubclass() {
        return super.getSubclass();
    }

    @Override // com.android.server.usb.descriptors.UsbACEndpoint
    public /* bridge */ /* synthetic */ byte getSubtype() {
        return super.getSubtype();
    }

    public UsbACAudioStreamEndpoint(int i, byte b, int i2, byte b2) {
        super(i, b, i2, b2);
    }

    @Override // com.android.server.usb.descriptors.UsbACEndpoint, com.android.server.usb.descriptors.UsbDescriptor
    public int parseRawDescriptors(com.android.server.usb.descriptors.ByteStream byteStream) {
        super.parseRawDescriptors(byteStream);
        byteStream.advance(this.mLength - byteStream.getReadCount());
        return this.mLength;
    }
}
