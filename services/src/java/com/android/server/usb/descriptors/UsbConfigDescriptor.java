package com.android.server.usb.descriptors;

/* loaded from: classes2.dex */
public final class UsbConfigDescriptor extends com.android.server.usb.descriptors.UsbDescriptor {
    private static final java.lang.String TAG = "UsbConfigDescriptor";
    private int mAttribs;
    private boolean mBlockAudio;
    private byte mConfigIndex;
    private int mConfigValue;
    private java.util.ArrayList<com.android.server.usb.descriptors.UsbInterfaceDescriptor> mInterfaceDescriptors;
    private int mMaxPower;
    private byte mNumInterfaces;
    private int mTotalLength;

    UsbConfigDescriptor(int i, byte b) {
        super(i, b);
        this.mInterfaceDescriptors = new java.util.ArrayList<>();
        this.mHierarchyLevel = 2;
    }

    public int getTotalLength() {
        return this.mTotalLength;
    }

    public byte getNumInterfaces() {
        return this.mNumInterfaces;
    }

    public int getConfigValue() {
        return this.mConfigValue;
    }

    public byte getConfigIndex() {
        return this.mConfigIndex;
    }

    public int getAttribs() {
        return this.mAttribs;
    }

    public int getMaxPower() {
        return this.mMaxPower;
    }

    void addInterfaceDescriptor(com.android.server.usb.descriptors.UsbInterfaceDescriptor usbInterfaceDescriptor) {
        this.mInterfaceDescriptors.add(usbInterfaceDescriptor);
    }

    java.util.ArrayList<com.android.server.usb.descriptors.UsbInterfaceDescriptor> getInterfaceDescriptors() {
        return this.mInterfaceDescriptors;
    }

    private boolean isAudioInterface(com.android.server.usb.descriptors.UsbInterfaceDescriptor usbInterfaceDescriptor) {
        return usbInterfaceDescriptor.getUsbClass() == 1 && usbInterfaceDescriptor.getUsbSubclass() == 2;
    }

    android.hardware.usb.UsbConfiguration toAndroid(com.android.server.usb.descriptors.UsbDescriptorParser usbDescriptorParser) {
        android.hardware.usb.UsbConfiguration usbConfiguration = new android.hardware.usb.UsbConfiguration(this.mConfigValue, usbDescriptorParser.getDescriptorString(this.mConfigIndex), this.mAttribs, this.mMaxPower);
        java.util.ArrayList arrayList = new java.util.ArrayList();
        java.util.Iterator<com.android.server.usb.descriptors.UsbInterfaceDescriptor> it = this.mInterfaceDescriptors.iterator();
        while (it.hasNext()) {
            com.android.server.usb.descriptors.UsbInterfaceDescriptor next = it.next();
            if (!this.mBlockAudio || !isAudioInterface(next)) {
                arrayList.add(next.toAndroid(usbDescriptorParser));
            }
        }
        usbConfiguration.setInterfaces((android.hardware.usb.UsbInterface[]) arrayList.toArray(new android.hardware.usb.UsbInterface[0]));
        return usbConfiguration;
    }

    @Override // com.android.server.usb.descriptors.UsbDescriptor
    public int parseRawDescriptors(com.android.server.usb.descriptors.ByteStream byteStream) {
        this.mTotalLength = byteStream.unpackUsbShort();
        this.mNumInterfaces = byteStream.getByte();
        this.mConfigValue = byteStream.getUnsignedByte();
        this.mConfigIndex = byteStream.getByte();
        this.mAttribs = byteStream.getUnsignedByte();
        this.mMaxPower = byteStream.getUnsignedByte();
        return this.mLength;
    }

    @Override // com.android.server.usb.descriptors.UsbDescriptor, com.android.server.usb.descriptors.report.Reporting
    public void report(com.android.server.usb.descriptors.report.ReportCanvas reportCanvas) {
        super.report(reportCanvas);
        reportCanvas.openList();
        reportCanvas.writeListItem("Config # " + getConfigValue());
        reportCanvas.writeListItem(((int) getNumInterfaces()) + " Interfaces.");
        reportCanvas.writeListItem("Attributes: " + com.android.server.usb.descriptors.report.ReportCanvas.getHexString(getAttribs()));
        reportCanvas.closeList();
    }
}
