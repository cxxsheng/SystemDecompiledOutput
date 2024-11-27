package com.android.server.usb.descriptors.report;

/* loaded from: classes2.dex */
public final class UsbStrings {
    private static final java.lang.String TAG = "UsbStrings";
    private static java.util.HashMap<java.lang.Byte, java.lang.String> sACControlInterfaceNames;
    private static java.util.HashMap<java.lang.Byte, java.lang.String> sACStreamingInterfaceNames;
    private static java.util.HashMap<java.lang.Integer, java.lang.String> sAudioEncodingNames;
    private static java.util.HashMap<java.lang.Integer, java.lang.String> sAudioSubclassNames;
    private static java.util.HashMap<java.lang.Integer, java.lang.String> sClassNames;
    private static java.util.HashMap<java.lang.Byte, java.lang.String> sDescriptorNames;
    private static java.util.HashMap<java.lang.Integer, java.lang.String> sFormatNames;
    private static java.util.HashMap<java.lang.Integer, java.lang.String> sTerminalNames;

    static {
        allocUsbStrings();
    }

    private static void initDescriptorNames() {
        sDescriptorNames = new java.util.HashMap<>();
        sDescriptorNames.put((byte) 1, "Device");
        sDescriptorNames.put((byte) 2, "Config");
        sDescriptorNames.put((byte) 3, "String");
        sDescriptorNames.put((byte) 4, "Interface");
        sDescriptorNames.put((byte) 5, "Endpoint");
        sDescriptorNames.put((byte) 15, "BOS (whatever that means)");
        sDescriptorNames.put((byte) 11, "Interface Association");
        sDescriptorNames.put(java.lang.Byte.valueOf(com.android.server.usb.descriptors.UsbDescriptor.DESCRIPTORTYPE_CAPABILITY), "Capability");
        sDescriptorNames.put(java.lang.Byte.valueOf(com.android.server.usb.descriptors.UsbDescriptor.DESCRIPTORTYPE_HID), "HID");
        sDescriptorNames.put(java.lang.Byte.valueOf(com.android.server.usb.descriptors.UsbDescriptor.DESCRIPTORTYPE_REPORT), "Report");
        sDescriptorNames.put(java.lang.Byte.valueOf(com.android.server.usb.descriptors.UsbDescriptor.DESCRIPTORTYPE_PHYSICAL), "Physical");
        sDescriptorNames.put(java.lang.Byte.valueOf(com.android.server.usb.descriptors.UsbDescriptor.DESCRIPTORTYPE_CLASSSPECIFIC_INTERFACE), "Class-specific Interface");
        sDescriptorNames.put(java.lang.Byte.valueOf(com.android.server.usb.descriptors.UsbDescriptor.DESCRIPTORTYPE_CLASSSPECIFIC_ENDPOINT), "Class-specific Endpoint");
        sDescriptorNames.put(java.lang.Byte.valueOf(com.android.server.usb.descriptors.UsbDescriptor.DESCRIPTORTYPE_HUB), "Hub");
        sDescriptorNames.put(java.lang.Byte.valueOf(com.android.server.usb.descriptors.UsbDescriptor.DESCRIPTORTYPE_SUPERSPEED_HUB), "Superspeed Hub");
        sDescriptorNames.put(java.lang.Byte.valueOf(com.android.server.usb.descriptors.UsbDescriptor.DESCRIPTORTYPE_ENDPOINT_COMPANION), "Endpoint Companion");
    }

    private static void initACControlInterfaceNames() {
        sACControlInterfaceNames = new java.util.HashMap<>();
        sACControlInterfaceNames.put((byte) 0, "Undefined");
        sACControlInterfaceNames.put((byte) 1, "Header");
        sACControlInterfaceNames.put((byte) 2, "Input Terminal");
        sACControlInterfaceNames.put((byte) 3, "Output Terminal");
        sACControlInterfaceNames.put((byte) 4, "Mixer Unit");
        sACControlInterfaceNames.put((byte) 5, "Selector Unit");
        sACControlInterfaceNames.put((byte) 6, "Feature Unit");
        sACControlInterfaceNames.put((byte) 7, "Processing Unit");
        sACControlInterfaceNames.put((byte) 8, "Extension Unit");
        sACControlInterfaceNames.put((byte) 10, "Clock Source");
        sACControlInterfaceNames.put((byte) 11, "Clock Selector");
        sACControlInterfaceNames.put((byte) 12, "Clock Multiplier");
        sACControlInterfaceNames.put((byte) 13, "Sample Rate Converter");
    }

    private static void initACStreamingInterfaceNames() {
        sACStreamingInterfaceNames = new java.util.HashMap<>();
        sACStreamingInterfaceNames.put((byte) 0, "Undefined");
        sACStreamingInterfaceNames.put((byte) 1, "General");
        sACStreamingInterfaceNames.put((byte) 2, "Format Type");
        sACStreamingInterfaceNames.put((byte) 3, "Format Specific");
    }

    private static void initClassNames() {
        sClassNames = new java.util.HashMap<>();
        sClassNames.put(0, "Device");
        sClassNames.put(1, "Audio");
        sClassNames.put(2, "Communications");
        sClassNames.put(3, "HID");
        sClassNames.put(5, "Physical");
        sClassNames.put(6, "Image");
        sClassNames.put(7, "Printer");
        sClassNames.put(8, "Storage");
        sClassNames.put(9, "Hub");
        sClassNames.put(10, "CDC Control");
        sClassNames.put(11, "Smart Card");
        sClassNames.put(13, "Security");
        sClassNames.put(14, "Video");
        sClassNames.put(15, "Healthcare");
        sClassNames.put(16, "Audio/Video");
        sClassNames.put(17, "Billboard");
        sClassNames.put(18, "Type C Bridge");
        sClassNames.put(java.lang.Integer.valueOf(com.android.server.usb.descriptors.UsbDescriptor.CLASSID_DIAGNOSTIC), "Diagnostic");
        sClassNames.put(java.lang.Integer.valueOf(com.android.server.usb.descriptors.UsbDescriptor.CLASSID_WIRELESS), "Wireless");
        sClassNames.put(239, "Misc");
        sClassNames.put(254, "Application Specific");
        sClassNames.put(255, "Vendor Specific");
    }

    private static void initAudioSubclassNames() {
        sAudioSubclassNames = new java.util.HashMap<>();
        sAudioSubclassNames.put(0, "Undefinded");
        sAudioSubclassNames.put(1, "Audio Control");
        sAudioSubclassNames.put(2, "Audio Streaming");
        sAudioSubclassNames.put(3, "MIDI Streaming");
    }

    private static void initAudioEncodingNames() {
        sAudioEncodingNames = new java.util.HashMap<>();
        sAudioEncodingNames.put(0, "Format I Undefined");
        sAudioEncodingNames.put(1, "Format I PCM");
        sAudioEncodingNames.put(2, "Format I PCM8");
        sAudioEncodingNames.put(3, "Format I FLOAT");
        sAudioEncodingNames.put(4, "Format I ALAW");
        sAudioEncodingNames.put(5, "Format I MuLAW");
        sAudioEncodingNames.put(4096, "FORMAT_II Undefined");
        sAudioEncodingNames.put(java.lang.Integer.valueOf(com.android.server.usb.descriptors.UsbACInterface.FORMAT_II_MPEG), "FORMAT_II MPEG");
        sAudioEncodingNames.put(java.lang.Integer.valueOf(com.android.server.usb.descriptors.UsbACInterface.FORMAT_II_AC3), "FORMAT_II AC3");
        sAudioEncodingNames.put(8192, "FORMAT_III Undefined");
        sAudioEncodingNames.put(java.lang.Integer.valueOf(com.android.server.usb.descriptors.UsbACInterface.FORMAT_III_IEC1937AC3), "FORMAT_III IEC1937 AC3");
        sAudioEncodingNames.put(java.lang.Integer.valueOf(com.android.server.usb.descriptors.UsbACInterface.FORMAT_III_IEC1937_MPEG1_Layer1), "FORMAT_III MPEG1 Layer 1");
        sAudioEncodingNames.put(java.lang.Integer.valueOf(com.android.server.usb.descriptors.UsbACInterface.FORMAT_III_IEC1937_MPEG1_Layer2), "FORMAT_III MPEG1 Layer 2");
        sAudioEncodingNames.put(java.lang.Integer.valueOf(com.android.server.usb.descriptors.UsbACInterface.FORMAT_III_IEC1937_MPEG2_EXT), "FORMAT_III MPEG2 EXT");
        sAudioEncodingNames.put(java.lang.Integer.valueOf(com.android.server.usb.descriptors.UsbACInterface.FORMAT_III_IEC1937_MPEG2_Layer1LS), "FORMAT_III MPEG2 Layer1LS");
    }

    private static void initTerminalNames() {
        sTerminalNames = new java.util.HashMap<>();
        sTerminalNames.put(257, "USB Streaming");
        sTerminalNames.put(512, "Undefined");
        sTerminalNames.put(513, "Microphone");
        sTerminalNames.put(514, "Desktop Microphone");
        sTerminalNames.put(515, "Personal (headset) Microphone");
        sTerminalNames.put(516, "Omni Microphone");
        sTerminalNames.put(517, "Microphone Array");
        sTerminalNames.put(java.lang.Integer.valueOf(com.android.server.usb.descriptors.UsbTerminalTypes.TERMINAL_IN_PROC_MIC_ARRAY), "Proecessing Microphone Array");
        sTerminalNames.put(768, "Undefined");
        sTerminalNames.put(java.lang.Integer.valueOf(com.android.server.usb.descriptors.UsbTerminalTypes.TERMINAL_OUT_SPEAKER), "Speaker");
        sTerminalNames.put(java.lang.Integer.valueOf(com.android.server.usb.descriptors.UsbTerminalTypes.TERMINAL_OUT_HEADPHONES), "Headphones");
        sTerminalNames.put(java.lang.Integer.valueOf(com.android.server.usb.descriptors.UsbTerminalTypes.TERMINAL_OUT_HEADMOUNTED), "Head Mounted Speaker");
        sTerminalNames.put(772, "Desktop Speaker");
        sTerminalNames.put(773, "Room Speaker");
        sTerminalNames.put(774, "Communications Speaker");
        sTerminalNames.put(775, "Low Frequency Speaker");
        sTerminalNames.put(1024, "Undefined");
        sTerminalNames.put(java.lang.Integer.valueOf(com.android.server.usb.descriptors.UsbTerminalTypes.TERMINAL_BIDIR_HANDSET), "Handset");
        sTerminalNames.put(java.lang.Integer.valueOf(com.android.server.usb.descriptors.UsbTerminalTypes.TERMINAL_BIDIR_HEADSET), "Headset");
        sTerminalNames.put(java.lang.Integer.valueOf(com.android.server.usb.descriptors.UsbTerminalTypes.TERMINAL_BIDIR_SKRPHONE), "Speaker Phone");
        sTerminalNames.put(java.lang.Integer.valueOf(com.android.server.usb.descriptors.UsbTerminalTypes.TERMINAL_BIDIR_SKRPHONE_SUPRESS), "Speaker Phone (echo supressing)");
        sTerminalNames.put(java.lang.Integer.valueOf(com.android.server.usb.descriptors.UsbTerminalTypes.TERMINAL_BIDIR_SKRPHONE_CANCEL), "Speaker Phone (echo canceling)");
        sTerminalNames.put(1280, "Undefined");
        sTerminalNames.put(java.lang.Integer.valueOf(com.android.server.usb.descriptors.UsbTerminalTypes.TERMINAL_TELE_PHONELINE), "Phone Line");
        sTerminalNames.put(java.lang.Integer.valueOf(com.android.server.usb.descriptors.UsbTerminalTypes.TERMINAL_TELE_PHONE), "Telephone");
        sTerminalNames.put(java.lang.Integer.valueOf(com.android.server.usb.descriptors.UsbTerminalTypes.TERMINAL_TELE_DOWNLINEPHONE), "Down Line Phone");
        sTerminalNames.put(1536, "Undefined");
        sTerminalNames.put(java.lang.Integer.valueOf(com.android.server.usb.descriptors.UsbTerminalTypes.TERMINAL_EXTERN_ANALOG), "Analog Connector");
        sTerminalNames.put(java.lang.Integer.valueOf(com.android.server.usb.descriptors.UsbTerminalTypes.TERMINAL_EXTERN_DIGITAL), "Digital Connector");
        sTerminalNames.put(1539, "Line Connector");
        sTerminalNames.put(java.lang.Integer.valueOf(com.android.server.usb.descriptors.UsbTerminalTypes.TERMINAL_EXTERN_LEGACY), "Legacy Audio Connector");
        sTerminalNames.put(java.lang.Integer.valueOf(com.android.server.usb.descriptors.UsbTerminalTypes.TERMINAL_EXTERN_SPIDF), "S/PIDF Interface");
        sTerminalNames.put(java.lang.Integer.valueOf(com.android.server.usb.descriptors.UsbTerminalTypes.TERMINAL_EXTERN_1394DA), "1394 Audio");
        sTerminalNames.put(java.lang.Integer.valueOf(com.android.server.usb.descriptors.UsbTerminalTypes.TERMINAL_EXTERN_1394DV), "1394 Audio/Video");
        sTerminalNames.put(java.lang.Integer.valueOf(com.android.server.usb.descriptors.UsbTerminalTypes.TERMINAL_EMBED_UNDEFINED), "Undefined");
        sTerminalNames.put(java.lang.Integer.valueOf(com.android.server.usb.descriptors.UsbTerminalTypes.TERMINAL_EMBED_CALNOISE), "Calibration Nose");
        sTerminalNames.put(java.lang.Integer.valueOf(com.android.server.usb.descriptors.UsbTerminalTypes.TERMINAL_EMBED_EQNOISE), "EQ Noise");
        sTerminalNames.put(java.lang.Integer.valueOf(com.android.server.usb.descriptors.UsbTerminalTypes.TERMINAL_EMBED_CDPLAYER), "CD Player");
        sTerminalNames.put(java.lang.Integer.valueOf(com.android.server.usb.descriptors.UsbTerminalTypes.TERMINAL_EMBED_DAT), "DAT");
        sTerminalNames.put(java.lang.Integer.valueOf(com.android.server.usb.descriptors.UsbTerminalTypes.TERMINAL_EMBED_DCC), "DCC");
        sTerminalNames.put(java.lang.Integer.valueOf(com.android.server.usb.descriptors.UsbTerminalTypes.TERMINAL_EMBED_MINIDISK), "Mini Disk");
        sTerminalNames.put(java.lang.Integer.valueOf(com.android.server.usb.descriptors.UsbTerminalTypes.TERMINAL_EMBED_ANALOGTAPE), "Analog Tap");
        sTerminalNames.put(1800, "Phonograph");
        sTerminalNames.put(java.lang.Integer.valueOf(com.android.server.usb.descriptors.UsbTerminalTypes.TERMINAL_EMBED_VCRAUDIO), "VCR Audio");
        sTerminalNames.put(java.lang.Integer.valueOf(com.android.server.usb.descriptors.UsbTerminalTypes.TERMINAL_EMBED_VIDDISKAUDIO), "Video Disk Audio");
        sTerminalNames.put(java.lang.Integer.valueOf(com.android.server.usb.descriptors.UsbTerminalTypes.TERMINAL_EMBED_DVDAUDIO), "DVD Audio");
        sTerminalNames.put(java.lang.Integer.valueOf(com.android.server.usb.descriptors.UsbTerminalTypes.TERMINAL_EMBED_TVAUDIO), "TV Audio");
        sTerminalNames.put(java.lang.Integer.valueOf(com.android.server.usb.descriptors.UsbTerminalTypes.TERMINAL_EMBED_SATELLITEAUDIO), "Satellite Audio");
        sTerminalNames.put(java.lang.Integer.valueOf(com.android.server.usb.descriptors.UsbTerminalTypes.TERMINAL_EMBED_CABLEAUDIO), "Cable Tuner Audio");
        sTerminalNames.put(java.lang.Integer.valueOf(com.android.server.usb.descriptors.UsbTerminalTypes.TERMINAL_EMBED_DSSAUDIO), "DSS Audio");
        sTerminalNames.put(java.lang.Integer.valueOf(com.android.server.usb.descriptors.UsbTerminalTypes.TERMINAL_EMBED_RADIOTRANSMITTER), "Radio Transmitter");
        sTerminalNames.put(java.lang.Integer.valueOf(com.android.server.usb.descriptors.UsbTerminalTypes.TERMINAL_EMBED_MULTITRACK), "Multitrack Recorder");
        sTerminalNames.put(java.lang.Integer.valueOf(com.android.server.usb.descriptors.UsbTerminalTypes.TERMINAL_EMBED_SYNTHESIZER), "Synthesizer");
    }

    public static java.lang.String getTerminalName(int i) {
        java.lang.String str = sTerminalNames.get(java.lang.Integer.valueOf(i));
        if (str != null) {
            return str;
        }
        return "Unknown Terminal Type 0x" + java.lang.Integer.toHexString(i);
    }

    private static void initFormatNames() {
        sFormatNames = new java.util.HashMap<>();
        sFormatNames.put(1, "FORMAT_TYPE_I");
        sFormatNames.put(2, "FORMAT_TYPE_II");
        sFormatNames.put(3, "FORMAT_TYPE_III");
        sFormatNames.put(4, "FORMAT_TYPE_IV");
        sFormatNames.put(-127, "EXT_FORMAT_TYPE_I");
        sFormatNames.put(-126, "EXT_FORMAT_TYPE_II");
        sFormatNames.put(-125, "EXT_FORMAT_TYPE_III");
    }

    public static java.lang.String getFormatName(int i) {
        java.lang.String str = sFormatNames.get(java.lang.Integer.valueOf(i));
        if (str != null) {
            return str;
        }
        return "Unknown Format Type 0x" + java.lang.Integer.toHexString(i);
    }

    private static void allocUsbStrings() {
        initDescriptorNames();
        initACControlInterfaceNames();
        initACStreamingInterfaceNames();
        initClassNames();
        initAudioSubclassNames();
        initAudioEncodingNames();
        initTerminalNames();
        initFormatNames();
    }

    public static java.lang.String getDescriptorName(byte b) {
        java.lang.String str = sDescriptorNames.get(java.lang.Byte.valueOf(b));
        int i = b & android.hardware.tv.hdmi.cec.CecDeviceType.INACTIVE;
        if (str != null) {
            return str;
        }
        return "Unknown Descriptor [0x" + java.lang.Integer.toHexString(i) + ":" + i + "]";
    }

    public static java.lang.String getACControlInterfaceName(byte b) {
        java.lang.String str = sACControlInterfaceNames.get(java.lang.Byte.valueOf(b));
        int i = b & android.hardware.tv.hdmi.cec.CecDeviceType.INACTIVE;
        if (str != null) {
            return str;
        }
        return "Unknown subtype [0x" + java.lang.Integer.toHexString(i) + ":" + i + "]";
    }

    public static java.lang.String getACStreamingInterfaceName(byte b) {
        java.lang.String str = sACStreamingInterfaceNames.get(java.lang.Byte.valueOf(b));
        int i = b & android.hardware.tv.hdmi.cec.CecDeviceType.INACTIVE;
        if (str != null) {
            return str;
        }
        return "Unknown Subtype [0x" + java.lang.Integer.toHexString(i) + ":" + i + "]";
    }

    public static java.lang.String getClassName(int i) {
        java.lang.String str = sClassNames.get(java.lang.Integer.valueOf(i));
        int i2 = i & 255;
        if (str != null) {
            return str;
        }
        return "Unknown Class ID [0x" + java.lang.Integer.toHexString(i2) + ":" + i2 + "]";
    }

    public static java.lang.String getAudioSubclassName(int i) {
        java.lang.String str = sAudioSubclassNames.get(java.lang.Integer.valueOf(i));
        int i2 = i & 255;
        if (str != null) {
            return str;
        }
        return "Unknown Audio Subclass [0x" + java.lang.Integer.toHexString(i2) + ":" + i2 + "]";
    }

    public static java.lang.String getAudioFormatName(int i) {
        java.lang.String str = sAudioEncodingNames.get(java.lang.Integer.valueOf(i));
        if (str != null) {
            return str;
        }
        return "Unknown Format (encoding) ID [0x" + java.lang.Integer.toHexString(i) + ":" + i + "]";
    }

    public static java.lang.String getACInterfaceSubclassName(int i) {
        return i == 1 ? "AC Control" : "AC Streaming";
    }
}
