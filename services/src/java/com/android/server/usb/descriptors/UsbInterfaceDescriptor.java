package com.android.server.usb.descriptors;

/* loaded from: classes2.dex */
public class UsbInterfaceDescriptor extends com.android.server.usb.descriptors.UsbDescriptor {
    private static final java.lang.String TAG = "UsbInterfaceDescriptor";
    protected byte mAlternateSetting;
    protected byte mDescrIndex;
    private java.util.ArrayList<com.android.server.usb.descriptors.UsbEndpointDescriptor> mEndpointDescriptors;
    protected int mInterfaceNumber;
    private com.android.server.usb.descriptors.UsbDescriptor mMidiHeaderInterfaceDescriptor;
    protected byte mNumEndpoints;
    protected int mProtocol;
    protected int mUsbClass;
    protected int mUsbSubclass;

    UsbInterfaceDescriptor(int i, byte b) {
        super(i, b);
        this.mEndpointDescriptors = new java.util.ArrayList<>();
        this.mHierarchyLevel = 3;
    }

    @Override // com.android.server.usb.descriptors.UsbDescriptor
    public int parseRawDescriptors(com.android.server.usb.descriptors.ByteStream byteStream) {
        this.mInterfaceNumber = byteStream.getUnsignedByte();
        this.mAlternateSetting = byteStream.getByte();
        this.mNumEndpoints = byteStream.getByte();
        this.mUsbClass = byteStream.getUnsignedByte();
        this.mUsbSubclass = byteStream.getUnsignedByte();
        this.mProtocol = byteStream.getUnsignedByte();
        this.mDescrIndex = byteStream.getByte();
        return this.mLength;
    }

    public int getInterfaceNumber() {
        return this.mInterfaceNumber;
    }

    public byte getAlternateSetting() {
        return this.mAlternateSetting;
    }

    public byte getNumEndpoints() {
        return this.mNumEndpoints;
    }

    public com.android.server.usb.descriptors.UsbEndpointDescriptor getEndpointDescriptor(int i) {
        if (i < 0 || i >= this.mEndpointDescriptors.size()) {
            return null;
        }
        return this.mEndpointDescriptors.get(i);
    }

    public int getUsbClass() {
        return this.mUsbClass;
    }

    public int getUsbSubclass() {
        return this.mUsbSubclass;
    }

    public int getProtocol() {
        return this.mProtocol;
    }

    public byte getDescrIndex() {
        return this.mDescrIndex;
    }

    void addEndpointDescriptor(com.android.server.usb.descriptors.UsbEndpointDescriptor usbEndpointDescriptor) {
        this.mEndpointDescriptors.add(usbEndpointDescriptor);
    }

    public void setMidiHeaderInterfaceDescriptor(com.android.server.usb.descriptors.UsbDescriptor usbDescriptor) {
        this.mMidiHeaderInterfaceDescriptor = usbDescriptor;
    }

    public com.android.server.usb.descriptors.UsbDescriptor getMidiHeaderInterfaceDescriptor() {
        return this.mMidiHeaderInterfaceDescriptor;
    }

    public android.hardware.usb.UsbInterface toAndroid(com.android.server.usb.descriptors.UsbDescriptorParser usbDescriptorParser) {
        android.hardware.usb.UsbInterface usbInterface = new android.hardware.usb.UsbInterface(this.mInterfaceNumber, this.mAlternateSetting, usbDescriptorParser.getDescriptorString(this.mDescrIndex), this.mUsbClass, this.mUsbSubclass, this.mProtocol);
        android.hardware.usb.UsbEndpoint[] usbEndpointArr = new android.hardware.usb.UsbEndpoint[this.mEndpointDescriptors.size()];
        for (int i = 0; i < this.mEndpointDescriptors.size(); i++) {
            usbEndpointArr[i] = this.mEndpointDescriptors.get(i).toAndroid(usbDescriptorParser);
        }
        usbInterface.setEndpoints(usbEndpointArr);
        return usbInterface;
    }

    @Override // com.android.server.usb.descriptors.UsbDescriptor, com.android.server.usb.descriptors.report.Reporting
    public void report(com.android.server.usb.descriptors.report.ReportCanvas reportCanvas) {
        java.lang.String str;
        super.report(reportCanvas);
        int usbClass = getUsbClass();
        int usbSubclass = getUsbSubclass();
        int protocol = getProtocol();
        java.lang.String className = com.android.server.usb.descriptors.report.UsbStrings.getClassName(usbClass);
        if (usbClass != 1) {
            str = "";
        } else {
            str = com.android.server.usb.descriptors.report.UsbStrings.getAudioSubclassName(usbSubclass);
        }
        reportCanvas.openList();
        reportCanvas.writeListItem("Interface #" + getInterfaceNumber());
        reportCanvas.writeListItem("Class: " + com.android.server.usb.descriptors.report.ReportCanvas.getHexString(usbClass) + ": " + className);
        reportCanvas.writeListItem("Subclass: " + com.android.server.usb.descriptors.report.ReportCanvas.getHexString(usbSubclass) + ": " + str);
        reportCanvas.writeListItem("Protocol: " + protocol + ": " + com.android.server.usb.descriptors.report.ReportCanvas.getHexString(protocol));
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append("Endpoints: ");
        sb.append((int) getNumEndpoints());
        reportCanvas.writeListItem(sb.toString());
        reportCanvas.closeList();
    }
}
