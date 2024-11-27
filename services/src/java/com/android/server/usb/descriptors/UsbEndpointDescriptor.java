package com.android.server.usb.descriptors;

/* loaded from: classes2.dex */
public class UsbEndpointDescriptor extends com.android.server.usb.descriptors.UsbDescriptor {
    public static final int DIRECTION_INPUT = 128;
    public static final int DIRECTION_OUTPUT = 0;
    public static final byte MASK_ATTRIBS_SYNCTYPE = 12;
    public static final int MASK_ATTRIBS_TRANSTYPE = 3;
    public static final int MASK_ATTRIBS_USEAGE = 48;
    public static final int MASK_ENDPOINT_ADDRESS = 15;
    public static final int MASK_ENDPOINT_DIRECTION = -128;
    public static final byte SYNCTYPE_ADAPTSYNC = 8;
    public static final byte SYNCTYPE_ASYNC = 4;
    public static final byte SYNCTYPE_NONE = 0;
    public static final byte SYNCTYPE_RESERVED = 12;
    private static final java.lang.String TAG = "UsbEndpointDescriptor";
    public static final int TRANSTYPE_BULK = 2;
    public static final int TRANSTYPE_CONTROL = 0;
    public static final int TRANSTYPE_INTERRUPT = 3;
    public static final int TRANSTYPE_ISO = 1;
    public static final int USEAGE_DATA = 0;
    public static final int USEAGE_EXPLICIT = 32;
    public static final int USEAGE_FEEDBACK = 16;
    public static final int USEAGE_RESERVED = 48;
    private int mAttributes;
    private com.android.server.usb.descriptors.UsbDescriptor mClassSpecificEndpointDescriptor;
    private int mEndpointAddress;
    private int mInterval;
    private int mPacketSize;
    private byte mRefresh;
    private byte mSyncAddress;

    public UsbEndpointDescriptor(int i, byte b) {
        super(i, b);
        this.mHierarchyLevel = 4;
    }

    public int getEndpointAddress() {
        return this.mEndpointAddress & 15;
    }

    public int getAttributes() {
        return this.mAttributes;
    }

    public int getPacketSize() {
        return this.mPacketSize;
    }

    public int getInterval() {
        return this.mInterval;
    }

    public byte getRefresh() {
        return this.mRefresh;
    }

    public byte getSyncAddress() {
        return this.mSyncAddress;
    }

    public int getDirection() {
        return this.mEndpointAddress & MASK_ENDPOINT_DIRECTION;
    }

    void setClassSpecificEndpointDescriptor(com.android.server.usb.descriptors.UsbDescriptor usbDescriptor) {
        this.mClassSpecificEndpointDescriptor = usbDescriptor;
    }

    public com.android.server.usb.descriptors.UsbDescriptor getClassSpecificEndpointDescriptor() {
        return this.mClassSpecificEndpointDescriptor;
    }

    public android.hardware.usb.UsbEndpoint toAndroid(com.android.server.usb.descriptors.UsbDescriptorParser usbDescriptorParser) {
        return new android.hardware.usb.UsbEndpoint(this.mEndpointAddress, this.mAttributes, this.mPacketSize, this.mInterval);
    }

    @Override // com.android.server.usb.descriptors.UsbDescriptor
    public int parseRawDescriptors(com.android.server.usb.descriptors.ByteStream byteStream) {
        this.mEndpointAddress = byteStream.getUnsignedByte();
        this.mAttributes = byteStream.getUnsignedByte();
        this.mPacketSize = byteStream.unpackUsbShort();
        this.mInterval = byteStream.getUnsignedByte();
        if (this.mLength == 9) {
            this.mRefresh = byteStream.getByte();
            this.mSyncAddress = byteStream.getByte();
        }
        return this.mLength;
    }

    @Override // com.android.server.usb.descriptors.UsbDescriptor, com.android.server.usb.descriptors.report.Reporting
    public void report(com.android.server.usb.descriptors.report.ReportCanvas reportCanvas) {
        super.report(reportCanvas);
        reportCanvas.openList();
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append("Address: ");
        sb.append(com.android.server.usb.descriptors.report.ReportCanvas.getHexString(getEndpointAddress()));
        sb.append(getDirection() == 0 ? " [out]" : " [in]");
        reportCanvas.writeListItem(sb.toString());
        int attributes = getAttributes();
        reportCanvas.openListItem();
        reportCanvas.write("Attributes: " + com.android.server.usb.descriptors.report.ReportCanvas.getHexString(attributes) + " ");
        int i = attributes & 3;
        switch (i) {
            case 0:
                reportCanvas.write("Control");
                break;
            case 1:
                reportCanvas.write("Iso");
                break;
            case 2:
                reportCanvas.write("Bulk");
                break;
            case 3:
                reportCanvas.write("Interrupt");
                break;
        }
        reportCanvas.closeListItem();
        if (i == 1) {
            reportCanvas.openListItem();
            reportCanvas.write("Aync: ");
            switch (attributes & 12) {
                case 0:
                    reportCanvas.write("NONE");
                    break;
                case 4:
                    reportCanvas.write("ASYNC");
                    break;
                case 8:
                    reportCanvas.write("ADAPTIVE ASYNC");
                    break;
            }
            reportCanvas.closeListItem();
            reportCanvas.openListItem();
            reportCanvas.write("Useage: ");
            switch (attributes & 48) {
                case 0:
                    reportCanvas.write("DATA");
                    break;
                case 16:
                    reportCanvas.write("FEEDBACK");
                    break;
                case 32:
                    reportCanvas.write("EXPLICIT FEEDBACK");
                    break;
                case 48:
                    reportCanvas.write("RESERVED");
                    break;
            }
            reportCanvas.closeListItem();
        }
        reportCanvas.writeListItem("Package Size: " + getPacketSize());
        reportCanvas.writeListItem("Interval: " + getInterval());
        reportCanvas.closeList();
    }
}
