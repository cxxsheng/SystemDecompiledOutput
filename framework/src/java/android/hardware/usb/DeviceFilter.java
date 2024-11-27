package android.hardware.usb;

/* loaded from: classes2.dex */
public class DeviceFilter {
    private static final java.lang.String TAG = android.hardware.usb.DeviceFilter.class.getSimpleName();
    public final int mClass;
    public final java.lang.String mManufacturerName;
    public final int mProductId;
    public final java.lang.String mProductName;
    public final int mProtocol;
    public final java.lang.String mSerialNumber;
    public final int mSubclass;
    public final int mVendorId;

    public DeviceFilter(int i, int i2, int i3, int i4, int i5, java.lang.String str, java.lang.String str2, java.lang.String str3) {
        this.mVendorId = i;
        this.mProductId = i2;
        this.mClass = i3;
        this.mSubclass = i4;
        this.mProtocol = i5;
        this.mManufacturerName = str;
        this.mProductName = str2;
        this.mSerialNumber = str3;
    }

    public DeviceFilter(android.hardware.usb.UsbDevice usbDevice) {
        this.mVendorId = usbDevice.getVendorId();
        this.mProductId = usbDevice.getProductId();
        this.mClass = usbDevice.getDeviceClass();
        this.mSubclass = usbDevice.getDeviceSubclass();
        this.mProtocol = usbDevice.getDeviceProtocol();
        this.mManufacturerName = usbDevice.getManufacturerName();
        this.mProductName = usbDevice.getProductName();
        this.mSerialNumber = usbDevice.getSerialNumber();
    }

    public DeviceFilter(android.hardware.usb.DeviceFilter deviceFilter) {
        this.mVendorId = deviceFilter.mVendorId;
        this.mProductId = deviceFilter.mProductId;
        this.mClass = deviceFilter.mClass;
        this.mSubclass = deviceFilter.mSubclass;
        this.mProtocol = deviceFilter.mProtocol;
        this.mManufacturerName = deviceFilter.mManufacturerName;
        this.mProductName = deviceFilter.mProductName;
        this.mSerialNumber = deviceFilter.mSerialNumber;
    }

    public static android.hardware.usb.DeviceFilter read(org.xmlpull.v1.XmlPullParser xmlPullParser) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        int i;
        int attributeCount = xmlPullParser.getAttributeCount();
        int i2 = 0;
        int i3 = -1;
        int i4 = -1;
        int i5 = -1;
        int i6 = -1;
        int i7 = -1;
        java.lang.String str = null;
        java.lang.String str2 = null;
        java.lang.String str3 = null;
        int i8 = 0;
        while (i8 < attributeCount) {
            java.lang.String attributeName = xmlPullParser.getAttributeName(i8);
            java.lang.String attributeValue = xmlPullParser.getAttributeValue(i8);
            if ("manufacturer-name".equals(attributeName)) {
                str = attributeValue;
            } else if ("product-name".equals(attributeName)) {
                str2 = attributeValue;
            } else if ("serial-number".equals(attributeName)) {
                str3 = attributeValue;
            } else {
                if (attributeValue != null && attributeValue.length() > 2 && attributeValue.charAt(i2) == '0' && (attributeValue.charAt(1) == 'x' || attributeValue.charAt(1) == 'X')) {
                    attributeValue = attributeValue.substring(2);
                    i = 16;
                } else {
                    i = 10;
                }
                try {
                    int parseInt = java.lang.Integer.parseInt(attributeValue, i);
                    if ("vendor-id".equals(attributeName)) {
                        i3 = parseInt;
                    } else if ("product-id".equals(attributeName)) {
                        i4 = parseInt;
                    } else if ("class".equals(attributeName)) {
                        i5 = parseInt;
                    } else if ("subclass".equals(attributeName)) {
                        i6 = parseInt;
                    } else if ("protocol".equals(attributeName)) {
                        i7 = parseInt;
                    }
                } catch (java.lang.NumberFormatException e) {
                    android.util.Slog.e(TAG, "invalid number for field " + attributeName, e);
                }
            }
            i8++;
            i2 = 0;
        }
        return new android.hardware.usb.DeviceFilter(i3, i4, i5, i6, i7, str, str2, str3);
    }

    public void write(org.xmlpull.v1.XmlSerializer xmlSerializer) throws java.io.IOException {
        xmlSerializer.startTag(null, "usb-device");
        if (this.mVendorId != -1) {
            xmlSerializer.attribute(null, "vendor-id", java.lang.Integer.toString(this.mVendorId));
        }
        if (this.mProductId != -1) {
            xmlSerializer.attribute(null, "product-id", java.lang.Integer.toString(this.mProductId));
        }
        if (this.mClass != -1) {
            xmlSerializer.attribute(null, "class", java.lang.Integer.toString(this.mClass));
        }
        if (this.mSubclass != -1) {
            xmlSerializer.attribute(null, "subclass", java.lang.Integer.toString(this.mSubclass));
        }
        if (this.mProtocol != -1) {
            xmlSerializer.attribute(null, "protocol", java.lang.Integer.toString(this.mProtocol));
        }
        if (this.mManufacturerName != null) {
            xmlSerializer.attribute(null, "manufacturer-name", this.mManufacturerName);
        }
        if (this.mProductName != null) {
            xmlSerializer.attribute(null, "product-name", this.mProductName);
        }
        if (this.mSerialNumber != null) {
            xmlSerializer.attribute(null, "serial-number", this.mSerialNumber);
        }
        xmlSerializer.endTag(null, "usb-device");
    }

    private boolean matches(int i, int i2, int i3) {
        return (this.mClass == -1 || i == this.mClass) && (this.mSubclass == -1 || i2 == this.mSubclass) && (this.mProtocol == -1 || i3 == this.mProtocol);
    }

    public boolean matches(android.hardware.usb.UsbDevice usbDevice) {
        if (this.mVendorId != -1 && usbDevice.getVendorId() != this.mVendorId) {
            return false;
        }
        if (this.mProductId != -1 && usbDevice.getProductId() != this.mProductId) {
            return false;
        }
        if (this.mManufacturerName != null && usbDevice.getManufacturerName() == null) {
            return false;
        }
        if (this.mProductName != null && usbDevice.getProductName() == null) {
            return false;
        }
        if (this.mSerialNumber != null && usbDevice.getSerialNumber() == null) {
            return false;
        }
        if (this.mManufacturerName != null && usbDevice.getManufacturerName() != null && !this.mManufacturerName.equals(usbDevice.getManufacturerName())) {
            return false;
        }
        if (this.mProductName != null && usbDevice.getProductName() != null && !this.mProductName.equals(usbDevice.getProductName())) {
            return false;
        }
        if (this.mSerialNumber != null && usbDevice.getSerialNumber() != null && !this.mSerialNumber.equals(usbDevice.getSerialNumber())) {
            return false;
        }
        if (matches(usbDevice.getDeviceClass(), usbDevice.getDeviceSubclass(), usbDevice.getDeviceProtocol())) {
            return true;
        }
        int interfaceCount = usbDevice.getInterfaceCount();
        for (int i = 0; i < interfaceCount; i++) {
            android.hardware.usb.UsbInterface usbInterface = usbDevice.getInterface(i);
            if (matches(usbInterface.getInterfaceClass(), usbInterface.getInterfaceSubclass(), usbInterface.getInterfaceProtocol())) {
                return true;
            }
        }
        return false;
    }

    public boolean contains(android.hardware.usb.DeviceFilter deviceFilter) {
        if (this.mVendorId != -1 && deviceFilter.mVendorId != this.mVendorId) {
            return false;
        }
        if (this.mProductId != -1 && deviceFilter.mProductId != this.mProductId) {
            return false;
        }
        if (this.mManufacturerName != null && !java.util.Objects.equals(this.mManufacturerName, deviceFilter.mManufacturerName)) {
            return false;
        }
        if (this.mProductName != null && !java.util.Objects.equals(this.mProductName, deviceFilter.mProductName)) {
            return false;
        }
        if (this.mSerialNumber == null || java.util.Objects.equals(this.mSerialNumber, deviceFilter.mSerialNumber)) {
            return matches(deviceFilter.mClass, deviceFilter.mSubclass, deviceFilter.mProtocol);
        }
        return false;
    }

    public boolean equals(java.lang.Object obj) {
        if (this.mVendorId == -1 || this.mProductId == -1 || this.mClass == -1 || this.mSubclass == -1 || this.mProtocol == -1) {
            return false;
        }
        if (obj instanceof android.hardware.usb.DeviceFilter) {
            android.hardware.usb.DeviceFilter deviceFilter = (android.hardware.usb.DeviceFilter) obj;
            if (deviceFilter.mVendorId != this.mVendorId || deviceFilter.mProductId != this.mProductId || deviceFilter.mClass != this.mClass || deviceFilter.mSubclass != this.mSubclass || deviceFilter.mProtocol != this.mProtocol) {
                return false;
            }
            if ((deviceFilter.mManufacturerName == null || this.mManufacturerName != null) && ((deviceFilter.mManufacturerName != null || this.mManufacturerName == null) && ((deviceFilter.mProductName == null || this.mProductName != null) && ((deviceFilter.mProductName != null || this.mProductName == null) && ((deviceFilter.mSerialNumber == null || this.mSerialNumber != null) && (deviceFilter.mSerialNumber != null || this.mSerialNumber == null)))))) {
                return (deviceFilter.mManufacturerName == null || this.mManufacturerName == null || this.mManufacturerName.equals(deviceFilter.mManufacturerName)) && (deviceFilter.mProductName == null || this.mProductName == null || this.mProductName.equals(deviceFilter.mProductName)) && (deviceFilter.mSerialNumber == null || this.mSerialNumber == null || this.mSerialNumber.equals(deviceFilter.mSerialNumber));
            }
            return false;
        }
        if (!(obj instanceof android.hardware.usb.UsbDevice)) {
            return false;
        }
        android.hardware.usb.UsbDevice usbDevice = (android.hardware.usb.UsbDevice) obj;
        if (usbDevice.getVendorId() != this.mVendorId || usbDevice.getProductId() != this.mProductId || usbDevice.getDeviceClass() != this.mClass || usbDevice.getDeviceSubclass() != this.mSubclass || usbDevice.getDeviceProtocol() != this.mProtocol) {
            return false;
        }
        if ((this.mManufacturerName == null || usbDevice.getManufacturerName() != null) && ((this.mManufacturerName != null || usbDevice.getManufacturerName() == null) && ((this.mProductName == null || usbDevice.getProductName() != null) && ((this.mProductName != null || usbDevice.getProductName() == null) && ((this.mSerialNumber == null || usbDevice.getSerialNumber() != null) && (this.mSerialNumber != null || usbDevice.getSerialNumber() == null)))))) {
            return (usbDevice.getManufacturerName() == null || this.mManufacturerName.equals(usbDevice.getManufacturerName())) && (usbDevice.getProductName() == null || this.mProductName.equals(usbDevice.getProductName())) && (usbDevice.getSerialNumber() == null || this.mSerialNumber.equals(usbDevice.getSerialNumber()));
        }
        return false;
    }

    public int hashCode() {
        return ((this.mVendorId << 16) | this.mProductId) ^ (((this.mClass << 16) | (this.mSubclass << 8)) | this.mProtocol);
    }

    public java.lang.String toString() {
        return "DeviceFilter[mVendorId=" + this.mVendorId + ",mProductId=" + this.mProductId + ",mClass=" + this.mClass + ",mSubclass=" + this.mSubclass + ",mProtocol=" + this.mProtocol + ",mManufacturerName=" + this.mManufacturerName + ",mProductName=" + this.mProductName + ",mSerialNumber=" + this.mSerialNumber + android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_END;
    }

    public void dump(com.android.internal.util.dump.DualDumpOutputStream dualDumpOutputStream, java.lang.String str, long j) {
        long start = dualDumpOutputStream.start(str, j);
        dualDumpOutputStream.write("vendor_id", 1120986464257L, this.mVendorId);
        dualDumpOutputStream.write("product_id", 1120986464258L, this.mProductId);
        dualDumpOutputStream.write("class", 1120986464259L, this.mClass);
        dualDumpOutputStream.write("subclass", 1120986464260L, this.mSubclass);
        dualDumpOutputStream.write("protocol", 1120986464261L, this.mProtocol);
        dualDumpOutputStream.write("manufacturer_name", 1138166333446L, this.mManufacturerName);
        dualDumpOutputStream.write("product_name", 1138166333447L, this.mProductName);
        dualDumpOutputStream.write("serial_number", 1138166333448L, this.mSerialNumber);
        dualDumpOutputStream.end(start);
    }
}
