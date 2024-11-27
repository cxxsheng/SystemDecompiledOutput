package com.android.server.usb.descriptors;

/* loaded from: classes2.dex */
public abstract class UsbACInterface extends com.android.server.usb.descriptors.UsbDescriptor {
    public static final byte ACI_CLOCK_MULTIPLIER = 12;
    public static final byte ACI_CLOCK_SELECTOR = 11;
    public static final byte ACI_CLOCK_SOURCE = 10;
    public static final byte ACI_EXTENSION_UNIT = 8;
    public static final byte ACI_FEATURE_UNIT = 6;
    public static final byte ACI_HEADER = 1;
    public static final byte ACI_INPUT_TERMINAL = 2;
    public static final byte ACI_MIXER_UNIT = 4;
    public static final byte ACI_OUTPUT_TERMINAL = 3;
    public static final byte ACI_PROCESSING_UNIT = 7;
    public static final byte ACI_SAMPLE_RATE_CONVERTER = 13;
    public static final byte ACI_SELECTOR_UNIT = 5;
    public static final byte ACI_UNDEFINED = 0;
    public static final byte ASI_FORMAT_SPECIFIC = 3;
    public static final byte ASI_FORMAT_TYPE = 2;
    public static final byte ASI_GENERAL = 1;
    public static final byte ASI_UNDEFINED = 0;
    public static final int FORMAT_III_IEC1937AC3 = 8193;
    public static final int FORMAT_III_IEC1937_MPEG1_Layer1 = 8194;
    public static final int FORMAT_III_IEC1937_MPEG1_Layer2 = 8195;
    public static final int FORMAT_III_IEC1937_MPEG2_EXT = 8196;
    public static final int FORMAT_III_IEC1937_MPEG2_Layer1LS = 8197;
    public static final int FORMAT_III_UNDEFINED = 8192;
    public static final int FORMAT_II_AC3 = 4098;
    public static final int FORMAT_II_MPEG = 4097;
    public static final int FORMAT_II_UNDEFINED = 4096;
    public static final int FORMAT_I_ALAW = 4;
    public static final int FORMAT_I_IEEE_FLOAT = 3;
    public static final int FORMAT_I_MULAW = 5;
    public static final int FORMAT_I_PCM = 1;
    public static final int FORMAT_I_PCM8 = 2;
    public static final int FORMAT_I_UNDEFINED = 0;
    public static final byte MSI_ELEMENT = 4;
    public static final byte MSI_HEADER = 1;
    public static final byte MSI_IN_JACK = 2;
    public static final byte MSI_OUT_JACK = 3;
    public static final byte MSI_UNDEFINED = 0;
    private static final java.lang.String TAG = "UsbACInterface";
    protected final int mSubclass;
    protected final byte mSubtype;

    public UsbACInterface(int i, byte b, byte b2, int i2) {
        super(i, b);
        this.mSubtype = b2;
        this.mSubclass = i2;
    }

    public byte getSubtype() {
        return this.mSubtype;
    }

    public int getSubclass() {
        return this.mSubclass;
    }

    private static com.android.server.usb.descriptors.UsbDescriptor allocAudioControlDescriptor(com.android.server.usb.descriptors.UsbDescriptorParser usbDescriptorParser, com.android.server.usb.descriptors.ByteStream byteStream, int i, byte b, byte b2, int i2) {
        switch (b2) {
            case 1:
                int unpackUsbShort = byteStream.unpackUsbShort();
                usbDescriptorParser.setACInterfaceSpec(unpackUsbShort);
                if (unpackUsbShort == 512) {
                    return new com.android.server.usb.descriptors.Usb20ACHeader(i, b, b2, i2, unpackUsbShort);
                }
                return new com.android.server.usb.descriptors.Usb10ACHeader(i, b, b2, i2, unpackUsbShort);
            case 2:
                if (usbDescriptorParser.getACInterfaceSpec() == 512) {
                    return new com.android.server.usb.descriptors.Usb20ACInputTerminal(i, b, b2, i2);
                }
                return new com.android.server.usb.descriptors.Usb10ACInputTerminal(i, b, b2, i2);
            case 3:
                if (usbDescriptorParser.getACInterfaceSpec() == 512) {
                    return new com.android.server.usb.descriptors.Usb20ACOutputTerminal(i, b, b2, i2);
                }
                return new com.android.server.usb.descriptors.Usb10ACOutputTerminal(i, b, b2, i2);
            case 4:
                if (usbDescriptorParser.getACInterfaceSpec() == 512) {
                    return new com.android.server.usb.descriptors.Usb20ACMixerUnit(i, b, b2, i2);
                }
                return new com.android.server.usb.descriptors.Usb10ACMixerUnit(i, b, b2, i2);
            case 5:
                return new com.android.server.usb.descriptors.UsbACSelectorUnit(i, b, b2, i2);
            case 6:
                return new com.android.server.usb.descriptors.UsbACFeatureUnit(i, b, b2, i2);
            default:
                android.util.Log.w(TAG, "Unknown Audio Class Interface subtype:0x" + java.lang.Integer.toHexString(b2));
                return new com.android.server.usb.descriptors.UsbACInterfaceUnparsed(i, b, b2, i2);
        }
    }

    private static com.android.server.usb.descriptors.UsbDescriptor allocAudioStreamingDescriptor(com.android.server.usb.descriptors.UsbDescriptorParser usbDescriptorParser, com.android.server.usb.descriptors.ByteStream byteStream, int i, byte b, byte b2, int i2) {
        int aCInterfaceSpec = usbDescriptorParser.getACInterfaceSpec();
        switch (b2) {
            case 1:
                if (aCInterfaceSpec == 512) {
                    return new com.android.server.usb.descriptors.Usb20ASGeneral(i, b, b2, i2);
                }
                return new com.android.server.usb.descriptors.Usb10ASGeneral(i, b, b2, i2);
            case 2:
                return com.android.server.usb.descriptors.UsbASFormat.allocDescriptor(usbDescriptorParser, byteStream, i, b, b2, i2);
            default:
                android.util.Log.w(TAG, "Unknown Audio Streaming Interface subtype:0x" + java.lang.Integer.toHexString(b2));
                return null;
        }
    }

    private static com.android.server.usb.descriptors.UsbDescriptor allocMidiStreamingDescriptor(int i, byte b, byte b2, int i2) {
        switch (b2) {
            case 1:
                return new com.android.server.usb.descriptors.UsbMSMidiHeader(i, b, b2, i2);
            case 2:
                return new com.android.server.usb.descriptors.UsbMSMidiInputJack(i, b, b2, i2);
            case 3:
                return new com.android.server.usb.descriptors.UsbMSMidiOutputJack(i, b, b2, i2);
            default:
                android.util.Log.w(TAG, "Unknown MIDI Streaming Interface subtype:0x" + java.lang.Integer.toHexString(b2));
                return null;
        }
    }

    public static com.android.server.usb.descriptors.UsbDescriptor allocDescriptor(com.android.server.usb.descriptors.UsbDescriptorParser usbDescriptorParser, com.android.server.usb.descriptors.ByteStream byteStream, int i, byte b) {
        byte b2 = byteStream.getByte();
        int usbSubclass = usbDescriptorParser.getCurInterface().getUsbSubclass();
        switch (usbSubclass) {
            case 1:
                return allocAudioControlDescriptor(usbDescriptorParser, byteStream, i, b, b2, usbSubclass);
            case 2:
                return allocAudioStreamingDescriptor(usbDescriptorParser, byteStream, i, b, b2, usbSubclass);
            case 3:
                return allocMidiStreamingDescriptor(i, b, b2, usbSubclass);
            default:
                android.util.Log.w(TAG, "Unknown Audio Class Interface Subclass: 0x" + java.lang.Integer.toHexString(usbSubclass));
                return null;
        }
    }

    @Override // com.android.server.usb.descriptors.UsbDescriptor, com.android.server.usb.descriptors.report.Reporting
    public void report(com.android.server.usb.descriptors.report.ReportCanvas reportCanvas) {
        super.report(reportCanvas);
        int subclass = getSubclass();
        java.lang.String aCInterfaceSubclassName = com.android.server.usb.descriptors.report.UsbStrings.getACInterfaceSubclassName(subclass);
        byte subtype = getSubtype();
        java.lang.String aCControlInterfaceName = com.android.server.usb.descriptors.report.UsbStrings.getACControlInterfaceName(subtype);
        reportCanvas.openList();
        reportCanvas.writeListItem("Subclass: " + com.android.server.usb.descriptors.report.ReportCanvas.getHexString(subclass) + " " + aCInterfaceSubclassName);
        reportCanvas.writeListItem("Subtype: " + com.android.server.usb.descriptors.report.ReportCanvas.getHexString(subtype) + " " + aCControlInterfaceName);
        reportCanvas.closeList();
    }
}
