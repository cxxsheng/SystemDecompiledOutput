package android.hardware.usb;

/* loaded from: classes2.dex */
public class AccessoryFilter {
    public final java.lang.String mManufacturer;
    public final java.lang.String mModel;
    public final java.lang.String mVersion;

    public AccessoryFilter(java.lang.String str, java.lang.String str2, java.lang.String str3) {
        this.mManufacturer = str;
        this.mModel = str2;
        this.mVersion = str3;
    }

    public AccessoryFilter(android.hardware.usb.UsbAccessory usbAccessory) {
        this.mManufacturer = usbAccessory.getManufacturer();
        this.mModel = usbAccessory.getModel();
        this.mVersion = usbAccessory.getVersion();
    }

    public AccessoryFilter(android.hardware.usb.AccessoryFilter accessoryFilter) {
        this.mManufacturer = accessoryFilter.mManufacturer;
        this.mModel = accessoryFilter.mModel;
        this.mVersion = accessoryFilter.mVersion;
    }

    public static android.hardware.usb.AccessoryFilter read(org.xmlpull.v1.XmlPullParser xmlPullParser) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        int attributeCount = xmlPullParser.getAttributeCount();
        java.lang.String str = null;
        java.lang.String str2 = null;
        java.lang.String str3 = null;
        for (int i = 0; i < attributeCount; i++) {
            java.lang.String attributeName = xmlPullParser.getAttributeName(i);
            java.lang.String attributeValue = xmlPullParser.getAttributeValue(i);
            if (android.media.midi.MidiDeviceInfo.PROPERTY_MANUFACTURER.equals(attributeName)) {
                str = attributeValue;
            } else if ("model".equals(attributeName)) {
                str3 = attributeValue;
            } else if ("version".equals(attributeName)) {
                str2 = attributeValue;
            }
        }
        return new android.hardware.usb.AccessoryFilter(str, str3, str2);
    }

    public void write(org.xmlpull.v1.XmlSerializer xmlSerializer) throws java.io.IOException {
        xmlSerializer.startTag(null, "usb-accessory");
        if (this.mManufacturer != null) {
            xmlSerializer.attribute(null, android.media.midi.MidiDeviceInfo.PROPERTY_MANUFACTURER, this.mManufacturer);
        }
        if (this.mModel != null) {
            xmlSerializer.attribute(null, "model", this.mModel);
        }
        if (this.mVersion != null) {
            xmlSerializer.attribute(null, "version", this.mVersion);
        }
        xmlSerializer.endTag(null, "usb-accessory");
    }

    public boolean matches(android.hardware.usb.UsbAccessory usbAccessory) {
        if (this.mManufacturer != null && !usbAccessory.getManufacturer().equals(this.mManufacturer)) {
            return false;
        }
        if (this.mModel == null || usbAccessory.getModel().equals(this.mModel)) {
            return this.mVersion == null || this.mVersion.equals(usbAccessory.getVersion());
        }
        return false;
    }

    public boolean contains(android.hardware.usb.AccessoryFilter accessoryFilter) {
        if (this.mManufacturer != null && !java.util.Objects.equals(accessoryFilter.mManufacturer, this.mManufacturer)) {
            return false;
        }
        if (this.mModel == null || java.util.Objects.equals(accessoryFilter.mModel, this.mModel)) {
            return this.mVersion == null || java.util.Objects.equals(accessoryFilter.mVersion, this.mVersion);
        }
        return false;
    }

    public boolean equals(java.lang.Object obj) {
        if (this.mManufacturer == null || this.mModel == null || this.mVersion == null) {
            return false;
        }
        if (obj instanceof android.hardware.usb.AccessoryFilter) {
            android.hardware.usb.AccessoryFilter accessoryFilter = (android.hardware.usb.AccessoryFilter) obj;
            return this.mManufacturer.equals(accessoryFilter.mManufacturer) && this.mModel.equals(accessoryFilter.mModel) && this.mVersion.equals(accessoryFilter.mVersion);
        }
        if (!(obj instanceof android.hardware.usb.UsbAccessory)) {
            return false;
        }
        android.hardware.usb.UsbAccessory usbAccessory = (android.hardware.usb.UsbAccessory) obj;
        return this.mManufacturer.equals(usbAccessory.getManufacturer()) && this.mModel.equals(usbAccessory.getModel()) && this.mVersion.equals(usbAccessory.getVersion());
    }

    public int hashCode() {
        return ((this.mManufacturer == null ? 0 : this.mManufacturer.hashCode()) ^ (this.mModel == null ? 0 : this.mModel.hashCode())) ^ (this.mVersion != null ? this.mVersion.hashCode() : 0);
    }

    public java.lang.String toString() {
        return "AccessoryFilter[mManufacturer=\"" + this.mManufacturer + "\", mModel=\"" + this.mModel + "\", mVersion=\"" + this.mVersion + "\"]";
    }

    public void dump(com.android.internal.util.dump.DualDumpOutputStream dualDumpOutputStream, java.lang.String str, long j) {
        long start = dualDumpOutputStream.start(str, j);
        dualDumpOutputStream.write(android.media.midi.MidiDeviceInfo.PROPERTY_MANUFACTURER, 1138166333441L, this.mManufacturer);
        dualDumpOutputStream.write("model", 1138166333442L, this.mModel);
        dualDumpOutputStream.write("version", 1138166333443L, this.mVersion);
        dualDumpOutputStream.end(start);
    }
}
