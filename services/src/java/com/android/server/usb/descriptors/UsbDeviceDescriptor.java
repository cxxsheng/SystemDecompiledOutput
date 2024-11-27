package com.android.server.usb.descriptors;

/* loaded from: classes2.dex */
public final class UsbDeviceDescriptor extends com.android.server.usb.descriptors.UsbDescriptor {
    private static final java.lang.String TAG = "UsbDeviceDescriptor";
    public static final int USBSPEC_1_0 = 256;
    public static final int USBSPEC_1_1 = 272;
    public static final int USBSPEC_2_0 = 512;
    private java.util.ArrayList<com.android.server.usb.descriptors.UsbConfigDescriptor> mConfigDescriptors;
    private int mDevClass;
    private int mDevSubClass;
    private int mDeviceRelease;
    private byte mMfgIndex;
    private byte mNumConfigs;
    private byte mPacketSize;
    private int mProductID;
    private byte mProductIndex;
    private int mProtocol;
    private byte mSerialIndex;
    private int mSpec;
    private int mVendorID;

    UsbDeviceDescriptor(int i, byte b) {
        super(i, b);
        this.mConfigDescriptors = new java.util.ArrayList<>();
        this.mHierarchyLevel = 1;
    }

    public int getSpec() {
        return this.mSpec;
    }

    public int getDevClass() {
        return this.mDevClass;
    }

    public int getDevSubClass() {
        return this.mDevSubClass;
    }

    public int getProtocol() {
        return this.mProtocol;
    }

    public byte getPacketSize() {
        return this.mPacketSize;
    }

    public int getVendorID() {
        return this.mVendorID;
    }

    public int getProductID() {
        return this.mProductID;
    }

    public int getDeviceRelease() {
        return this.mDeviceRelease;
    }

    public java.lang.String getDeviceReleaseString() {
        int i = this.mDeviceRelease & 15;
        int i2 = (this.mDeviceRelease & com.android.internal.util.FrameworkStatsLog.BOOT_TIME_EVENT_ELAPSED_TIME_REPORTED) >> 4;
        return java.lang.String.format("%d.%d%d", java.lang.Integer.valueOf((((this.mDeviceRelease & 61440) >> 12) * 10) + ((this.mDeviceRelease & 3840) >> 8)), java.lang.Integer.valueOf(i2), java.lang.Integer.valueOf(i));
    }

    public byte getMfgIndex() {
        return this.mMfgIndex;
    }

    public java.lang.String getMfgString(com.android.server.usb.descriptors.UsbDescriptorParser usbDescriptorParser) {
        return usbDescriptorParser.getDescriptorString(this.mMfgIndex);
    }

    public byte getProductIndex() {
        return this.mProductIndex;
    }

    public java.lang.String getProductString(com.android.server.usb.descriptors.UsbDescriptorParser usbDescriptorParser) {
        return usbDescriptorParser.getDescriptorString(this.mProductIndex);
    }

    public byte getSerialIndex() {
        return this.mSerialIndex;
    }

    public java.lang.String getSerialString(com.android.server.usb.descriptors.UsbDescriptorParser usbDescriptorParser) {
        return usbDescriptorParser.getDescriptorString(this.mSerialIndex);
    }

    public byte getNumConfigs() {
        return this.mNumConfigs;
    }

    void addConfigDescriptor(com.android.server.usb.descriptors.UsbConfigDescriptor usbConfigDescriptor) {
        this.mConfigDescriptors.add(usbConfigDescriptor);
    }

    public android.hardware.usb.UsbDevice.Builder toAndroid(com.android.server.usb.descriptors.UsbDescriptorParser usbDescriptorParser) {
        java.lang.String mfgString = getMfgString(usbDescriptorParser);
        java.lang.String productString = getProductString(usbDescriptorParser);
        java.lang.String deviceReleaseString = getDeviceReleaseString();
        java.lang.String serialString = getSerialString(usbDescriptorParser);
        int size = this.mConfigDescriptors.size();
        android.hardware.usb.UsbConfiguration[] usbConfigurationArr = new android.hardware.usb.UsbConfiguration[size];
        android.util.Log.d(TAG, "  " + size + " configs");
        for (int i = 0; i < this.mConfigDescriptors.size(); i++) {
            usbConfigurationArr[i] = this.mConfigDescriptors.get(i).toAndroid(usbDescriptorParser);
        }
        return new android.hardware.usb.UsbDevice.Builder(usbDescriptorParser.getDeviceAddr(), this.mVendorID, this.mProductID, this.mDevClass, this.mDevSubClass, this.mProtocol, mfgString, productString, deviceReleaseString, usbConfigurationArr, serialString, usbDescriptorParser.hasAudioPlayback(), usbDescriptorParser.hasAudioCapture(), usbDescriptorParser.hasMIDIInterface(), usbDescriptorParser.hasVideoPlayback(), usbDescriptorParser.hasVideoCapture());
    }

    @Override // com.android.server.usb.descriptors.UsbDescriptor
    public int parseRawDescriptors(com.android.server.usb.descriptors.ByteStream byteStream) {
        this.mSpec = byteStream.unpackUsbShort();
        this.mDevClass = byteStream.getUnsignedByte();
        this.mDevSubClass = byteStream.getUnsignedByte();
        this.mProtocol = byteStream.getUnsignedByte();
        this.mPacketSize = byteStream.getByte();
        this.mVendorID = byteStream.unpackUsbShort();
        this.mProductID = byteStream.unpackUsbShort();
        this.mDeviceRelease = byteStream.unpackUsbShort();
        this.mMfgIndex = byteStream.getByte();
        this.mProductIndex = byteStream.getByte();
        this.mSerialIndex = byteStream.getByte();
        this.mNumConfigs = byteStream.getByte();
        return this.mLength;
    }

    @Override // com.android.server.usb.descriptors.UsbDescriptor, com.android.server.usb.descriptors.report.Reporting
    public void report(com.android.server.usb.descriptors.report.ReportCanvas reportCanvas) {
        super.report(reportCanvas);
        reportCanvas.openList();
        reportCanvas.writeListItem("Spec: " + com.android.server.usb.descriptors.report.ReportCanvas.getBCDString(getSpec()));
        int devClass = getDevClass();
        java.lang.String className = com.android.server.usb.descriptors.report.UsbStrings.getClassName(devClass);
        int devSubClass = getDevSubClass();
        reportCanvas.writeListItem("Class " + devClass + ": " + className + " Subclass" + devSubClass + ": " + com.android.server.usb.descriptors.report.UsbStrings.getClassName(devSubClass));
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append("Vendor ID: ");
        sb.append(com.android.server.usb.descriptors.report.ReportCanvas.getHexString(getVendorID()));
        sb.append(" Product ID: ");
        sb.append(com.android.server.usb.descriptors.report.ReportCanvas.getHexString(getProductID()));
        sb.append(" Product Release: ");
        sb.append(com.android.server.usb.descriptors.report.ReportCanvas.getBCDString(getDeviceRelease()));
        reportCanvas.writeListItem(sb.toString());
        com.android.server.usb.descriptors.UsbDescriptorParser parser = reportCanvas.getParser();
        byte mfgIndex = getMfgIndex();
        java.lang.String descriptorString = parser.getDescriptorString(mfgIndex);
        byte productIndex = getProductIndex();
        reportCanvas.writeListItem("Manufacturer " + ((int) mfgIndex) + ": " + descriptorString + " Product " + ((int) productIndex) + ": " + parser.getDescriptorString(productIndex));
        reportCanvas.closeList();
    }
}
