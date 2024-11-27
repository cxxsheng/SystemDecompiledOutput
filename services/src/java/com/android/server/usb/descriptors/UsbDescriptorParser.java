package com.android.server.usb.descriptors;

/* loaded from: classes2.dex */
public final class UsbDescriptorParser {
    public static final boolean DEBUG = false;
    private static final int DESCRIPTORS_ALLOC_SIZE = 128;
    private static final float IN_HEADSET_TRIGGER = 0.75f;
    private static final int MS_MIDI_1_0 = 256;
    private static final int MS_MIDI_2_0 = 512;
    private static final float OUT_HEADSET_TRIGGER = 0.75f;
    private static final java.lang.String TAG = "UsbDescriptorParser";
    private int mACInterfacesSpec;
    private com.android.server.usb.descriptors.UsbConfigDescriptor mCurConfigDescriptor;
    private com.android.server.usb.descriptors.UsbEndpointDescriptor mCurEndpointDescriptor;
    private com.android.server.usb.descriptors.UsbInterfaceDescriptor mCurInterfaceDescriptor;
    private final java.util.ArrayList<com.android.server.usb.descriptors.UsbDescriptor> mDescriptors;
    private final java.lang.String mDeviceAddr;
    private com.android.server.usb.descriptors.UsbDeviceDescriptor mDeviceDescriptor;
    private int mVCInterfacesSpec;

    private native java.lang.String getDescriptorString_native(java.lang.String str, int i);

    private native byte[] getRawDescriptors_native(java.lang.String str);

    public UsbDescriptorParser(java.lang.String str, java.util.ArrayList<com.android.server.usb.descriptors.UsbDescriptor> arrayList) {
        this.mACInterfacesSpec = 256;
        this.mVCInterfacesSpec = 256;
        this.mDeviceAddr = str;
        this.mDescriptors = arrayList;
        this.mDeviceDescriptor = (com.android.server.usb.descriptors.UsbDeviceDescriptor) arrayList.get(0);
    }

    public UsbDescriptorParser(java.lang.String str, byte[] bArr) {
        this.mACInterfacesSpec = 256;
        this.mVCInterfacesSpec = 256;
        this.mDeviceAddr = str;
        this.mDescriptors = new java.util.ArrayList<>(128);
        parseDescriptors(bArr);
    }

    public java.lang.String getDeviceAddr() {
        return this.mDeviceAddr;
    }

    public int getUsbSpec() {
        if (this.mDeviceDescriptor != null) {
            return this.mDeviceDescriptor.getSpec();
        }
        throw new java.lang.IllegalArgumentException();
    }

    public void setACInterfaceSpec(int i) {
        this.mACInterfacesSpec = i;
    }

    public int getACInterfaceSpec() {
        return this.mACInterfacesSpec;
    }

    public void setVCInterfaceSpec(int i) {
        this.mVCInterfacesSpec = i;
    }

    public int getVCInterfaceSpec() {
        return this.mVCInterfacesSpec;
    }

    private class UsbDescriptorsStreamFormatException extends java.lang.Exception {
        java.lang.String mMessage;

        UsbDescriptorsStreamFormatException(java.lang.String str) {
            this.mMessage = str;
        }

        @Override // java.lang.Throwable
        public java.lang.String toString() {
            return "Descriptor Stream Format Exception: " + this.mMessage;
        }
    }

    private com.android.server.usb.descriptors.UsbDescriptor allocDescriptor(com.android.server.usb.descriptors.ByteStream byteStream) throws com.android.server.usb.descriptors.UsbDescriptorParser.UsbDescriptorsStreamFormatException {
        byteStream.resetReadCount();
        int unsignedByte = byteStream.getUnsignedByte();
        byte b = byteStream.getByte();
        com.android.server.usb.descriptors.UsbDescriptor.logDescriptorName(b, unsignedByte);
        com.android.server.usb.descriptors.UsbDescriptor usbDescriptor = null;
        r3 = null;
        com.android.server.usb.descriptors.UsbDescriptor usbDescriptor2 = null;
        usbDescriptor = null;
        usbDescriptor = null;
        usbDescriptor = null;
        usbDescriptor = null;
        switch (b) {
            case 1:
                com.android.server.usb.descriptors.UsbDeviceDescriptor usbDeviceDescriptor = new com.android.server.usb.descriptors.UsbDeviceDescriptor(unsignedByte, b);
                this.mDeviceDescriptor = usbDeviceDescriptor;
                usbDescriptor = usbDeviceDescriptor;
                break;
            case 2:
                com.android.server.usb.descriptors.UsbConfigDescriptor usbConfigDescriptor = new com.android.server.usb.descriptors.UsbConfigDescriptor(unsignedByte, b);
                this.mCurConfigDescriptor = usbConfigDescriptor;
                if (this.mDeviceDescriptor == null) {
                    android.util.Log.e(TAG, "Config Descriptor found with no associated Device Descriptor!");
                    throw new com.android.server.usb.descriptors.UsbDescriptorParser.UsbDescriptorsStreamFormatException("Config Descriptor found with no associated Device Descriptor!");
                }
                this.mDeviceDescriptor.addConfigDescriptor(this.mCurConfigDescriptor);
                usbDescriptor = usbConfigDescriptor;
                break;
            case 4:
                com.android.server.usb.descriptors.UsbInterfaceDescriptor usbInterfaceDescriptor = new com.android.server.usb.descriptors.UsbInterfaceDescriptor(unsignedByte, b);
                this.mCurInterfaceDescriptor = usbInterfaceDescriptor;
                if (this.mCurConfigDescriptor == null) {
                    android.util.Log.e(TAG, "Interface Descriptor found with no associated Config Descriptor!");
                    throw new com.android.server.usb.descriptors.UsbDescriptorParser.UsbDescriptorsStreamFormatException("Interface Descriptor found with no associated Config Descriptor!");
                }
                this.mCurConfigDescriptor.addInterfaceDescriptor(this.mCurInterfaceDescriptor);
                usbDescriptor = usbInterfaceDescriptor;
                break;
            case 5:
                com.android.server.usb.descriptors.UsbEndpointDescriptor usbEndpointDescriptor = new com.android.server.usb.descriptors.UsbEndpointDescriptor(unsignedByte, b);
                this.mCurEndpointDescriptor = usbEndpointDescriptor;
                if (this.mCurInterfaceDescriptor == null) {
                    android.util.Log.e(TAG, "Endpoint Descriptor found with no associated Interface Descriptor!");
                    throw new com.android.server.usb.descriptors.UsbDescriptorParser.UsbDescriptorsStreamFormatException("Endpoint Descriptor found with no associated Interface Descriptor!");
                }
                this.mCurInterfaceDescriptor.addEndpointDescriptor(usbEndpointDescriptor);
                usbDescriptor = usbEndpointDescriptor;
                break;
            case 11:
                usbDescriptor = new com.android.server.usb.descriptors.UsbInterfaceAssoc(unsignedByte, b);
                break;
            case 33:
                usbDescriptor = new com.android.server.usb.descriptors.UsbHIDDescriptor(unsignedByte, b);
                break;
            case 36:
                if (this.mCurInterfaceDescriptor != null) {
                    switch (this.mCurInterfaceDescriptor.getUsbClass()) {
                        case 1:
                            com.android.server.usb.descriptors.UsbDescriptor allocDescriptor = com.android.server.usb.descriptors.UsbACInterface.allocDescriptor(this, byteStream, unsignedByte, b);
                            boolean z = allocDescriptor instanceof com.android.server.usb.descriptors.UsbMSMidiHeader;
                            usbDescriptor = allocDescriptor;
                            if (z) {
                                this.mCurInterfaceDescriptor.setMidiHeaderInterfaceDescriptor(allocDescriptor);
                                usbDescriptor = allocDescriptor;
                                break;
                            }
                            break;
                        case 14:
                            usbDescriptor = com.android.server.usb.descriptors.UsbVCInterface.allocDescriptor(this, byteStream, unsignedByte, b);
                            break;
                        case 16:
                            break;
                        default:
                            android.util.Log.w(TAG, "  Unparsed Class-specific");
                            break;
                    }
                }
                break;
            case 37:
                if (this.mCurInterfaceDescriptor != null) {
                    int usbClass = this.mCurInterfaceDescriptor.getUsbClass();
                    switch (usbClass) {
                        case 1:
                            usbDescriptor2 = com.android.server.usb.descriptors.UsbACEndpoint.allocDescriptor(this, unsignedByte, b, java.lang.Byte.valueOf(byteStream.getByte()).byteValue());
                            break;
                        case 14:
                            usbDescriptor2 = com.android.server.usb.descriptors.UsbVCEndpoint.allocDescriptor(this, unsignedByte, b, java.lang.Byte.valueOf(byteStream.getByte()).byteValue());
                            break;
                        case 16:
                            break;
                        default:
                            android.util.Log.w(TAG, "  Unparsed Class-specific Endpoint:0x" + java.lang.Integer.toHexString(usbClass));
                            break;
                    }
                    usbDescriptor = usbDescriptor2;
                    usbDescriptor = usbDescriptor2;
                    if (this.mCurEndpointDescriptor != null && usbDescriptor2 != null) {
                        this.mCurEndpointDescriptor.setClassSpecificEndpointDescriptor(usbDescriptor2);
                        usbDescriptor = usbDescriptor2;
                        break;
                    }
                }
                break;
        }
        if (usbDescriptor == null) {
            return new com.android.server.usb.descriptors.UsbUnknown(unsignedByte, b);
        }
        return usbDescriptor;
    }

    public com.android.server.usb.descriptors.UsbDeviceDescriptor getDeviceDescriptor() {
        return this.mDeviceDescriptor;
    }

    public com.android.server.usb.descriptors.UsbInterfaceDescriptor getCurInterface() {
        return this.mCurInterfaceDescriptor;
    }

    public void parseDescriptors(byte[] bArr) {
        com.android.server.usb.descriptors.UsbDescriptor usbDescriptor;
        com.android.server.usb.descriptors.ByteStream byteStream = new com.android.server.usb.descriptors.ByteStream(bArr);
        while (byteStream.available() > 0) {
            try {
                usbDescriptor = allocDescriptor(byteStream);
            } catch (java.lang.Exception e) {
                android.util.Log.e(TAG, "Exception allocating USB descriptor.", e);
                usbDescriptor = null;
            }
            if (usbDescriptor != null) {
                try {
                    try {
                        usbDescriptor.parseRawDescriptors(byteStream);
                        usbDescriptor.postParse(byteStream);
                    } catch (java.lang.Exception e2) {
                        usbDescriptor.postParse(byteStream);
                        android.util.Log.w(TAG, "Exception parsing USB descriptors. type:0x" + ((int) usbDescriptor.getType()) + " status:" + usbDescriptor.getStatus());
                        java.lang.StackTraceElement[] stackTrace = e2.getStackTrace();
                        if (stackTrace.length > 0) {
                            android.util.Log.i(TAG, "  class:" + stackTrace[0].getClassName() + " @ " + stackTrace[0].getLineNumber());
                        }
                        if (stackTrace.length > 1) {
                            android.util.Log.i(TAG, "  class:" + stackTrace[1].getClassName() + " @ " + stackTrace[1].getLineNumber());
                        }
                        usbDescriptor.setStatus(4);
                    }
                } finally {
                    this.mDescriptors.add(usbDescriptor);
                }
            }
        }
    }

    public byte[] getRawDescriptors() {
        return getRawDescriptors_native(this.mDeviceAddr);
    }

    public java.lang.String getDescriptorString(int i) {
        return getDescriptorString_native(this.mDeviceAddr, i);
    }

    public int getParsingSpec() {
        if (this.mDeviceDescriptor != null) {
            return this.mDeviceDescriptor.getSpec();
        }
        return 0;
    }

    public java.util.ArrayList<com.android.server.usb.descriptors.UsbDescriptor> getDescriptors() {
        return this.mDescriptors;
    }

    public android.hardware.usb.UsbDevice.Builder toAndroidUsbDeviceBuilder() {
        if (this.mDeviceDescriptor == null) {
            android.util.Log.e(TAG, "toAndroidUsbDevice() ERROR - No Device Descriptor");
            return null;
        }
        android.hardware.usb.UsbDevice.Builder android2 = this.mDeviceDescriptor.toAndroid(this);
        if (android2 == null) {
            android.util.Log.e(TAG, "toAndroidUsbDevice() ERROR Creating Device");
        }
        return android2;
    }

    public java.util.ArrayList<com.android.server.usb.descriptors.UsbDescriptor> getDescriptors(byte b) {
        java.util.ArrayList<com.android.server.usb.descriptors.UsbDescriptor> arrayList = new java.util.ArrayList<>();
        java.util.Iterator<com.android.server.usb.descriptors.UsbDescriptor> it = this.mDescriptors.iterator();
        while (it.hasNext()) {
            com.android.server.usb.descriptors.UsbDescriptor next = it.next();
            if (next.getType() == b) {
                arrayList.add(next);
            }
        }
        return arrayList;
    }

    public java.util.ArrayList<com.android.server.usb.descriptors.UsbDescriptor> getInterfaceDescriptorsForClass(int i) {
        java.util.ArrayList<com.android.server.usb.descriptors.UsbDescriptor> arrayList = new java.util.ArrayList<>();
        java.util.Iterator<com.android.server.usb.descriptors.UsbDescriptor> it = this.mDescriptors.iterator();
        while (it.hasNext()) {
            com.android.server.usb.descriptors.UsbDescriptor next = it.next();
            if (next.getType() == 4) {
                if (next instanceof com.android.server.usb.descriptors.UsbInterfaceDescriptor) {
                    if (((com.android.server.usb.descriptors.UsbInterfaceDescriptor) next).getUsbClass() == i) {
                        arrayList.add(next);
                    }
                } else {
                    android.util.Log.w(TAG, "Unrecognized Interface l: " + next.getLength() + " t:0x" + java.lang.Integer.toHexString(next.getType()));
                }
            }
        }
        return arrayList;
    }

    public java.util.ArrayList<com.android.server.usb.descriptors.UsbDescriptor> getACInterfaceDescriptors(byte b, int i) {
        java.util.ArrayList<com.android.server.usb.descriptors.UsbDescriptor> arrayList = new java.util.ArrayList<>();
        java.util.Iterator<com.android.server.usb.descriptors.UsbDescriptor> it = this.mDescriptors.iterator();
        while (it.hasNext()) {
            com.android.server.usb.descriptors.UsbDescriptor next = it.next();
            if (next.getType() == 36) {
                if (next instanceof com.android.server.usb.descriptors.UsbACInterface) {
                    com.android.server.usb.descriptors.UsbACInterface usbACInterface = (com.android.server.usb.descriptors.UsbACInterface) next;
                    if (usbACInterface.getSubtype() == b && usbACInterface.getSubclass() == i) {
                        arrayList.add(next);
                    }
                } else {
                    android.util.Log.w(TAG, "Unrecognized Audio Interface len: " + next.getLength() + " type:0x" + java.lang.Integer.toHexString(next.getType()));
                }
            }
        }
        return arrayList;
    }

    public boolean hasInput() {
        java.util.Iterator<com.android.server.usb.descriptors.UsbDescriptor> it = getACInterfaceDescriptors((byte) 2, 1).iterator();
        while (it.hasNext()) {
            com.android.server.usb.descriptors.UsbDescriptor next = it.next();
            if (next instanceof com.android.server.usb.descriptors.UsbACTerminal) {
                int terminalType = ((com.android.server.usb.descriptors.UsbACTerminal) next).getTerminalType() & (-256);
                if (terminalType != 256 && terminalType != 768) {
                    return true;
                }
            } else {
                android.util.Log.w(TAG, "Undefined Audio Input terminal l: " + next.getLength() + " t:0x" + java.lang.Integer.toHexString(next.getType()));
            }
        }
        return false;
    }

    public boolean hasOutput() {
        java.util.Iterator<com.android.server.usb.descriptors.UsbDescriptor> it = getACInterfaceDescriptors((byte) 3, 1).iterator();
        while (it.hasNext()) {
            com.android.server.usb.descriptors.UsbDescriptor next = it.next();
            if (next instanceof com.android.server.usb.descriptors.UsbACTerminal) {
                int terminalType = ((com.android.server.usb.descriptors.UsbACTerminal) next).getTerminalType() & (-256);
                if (terminalType != 256 && terminalType != 512) {
                    return true;
                }
            } else {
                android.util.Log.w(TAG, "Undefined Audio Input terminal l: " + next.getLength() + " t:0x" + java.lang.Integer.toHexString(next.getType()));
            }
        }
        return false;
    }

    public boolean hasMic() {
        java.util.Iterator<com.android.server.usb.descriptors.UsbDescriptor> it = getACInterfaceDescriptors((byte) 2, 1).iterator();
        while (it.hasNext()) {
            com.android.server.usb.descriptors.UsbDescriptor next = it.next();
            if (next instanceof com.android.server.usb.descriptors.UsbACTerminal) {
                com.android.server.usb.descriptors.UsbACTerminal usbACTerminal = (com.android.server.usb.descriptors.UsbACTerminal) next;
                if (usbACTerminal.getTerminalType() == 513 || usbACTerminal.getTerminalType() == 1026 || usbACTerminal.getTerminalType() == 1024 || usbACTerminal.getTerminalType() == 1539) {
                    return true;
                }
            } else {
                android.util.Log.w(TAG, "Undefined Audio Input terminal l: " + next.getLength() + " t:0x" + java.lang.Integer.toHexString(next.getType()));
            }
        }
        return false;
    }

    public boolean hasSpeaker() {
        java.util.Iterator<com.android.server.usb.descriptors.UsbDescriptor> it = getACInterfaceDescriptors((byte) 3, 1).iterator();
        while (it.hasNext()) {
            com.android.server.usb.descriptors.UsbDescriptor next = it.next();
            if (next instanceof com.android.server.usb.descriptors.UsbACTerminal) {
                com.android.server.usb.descriptors.UsbACTerminal usbACTerminal = (com.android.server.usb.descriptors.UsbACTerminal) next;
                if (usbACTerminal.getTerminalType() == 769 || usbACTerminal.getTerminalType() == 770 || usbACTerminal.getTerminalType() == 1026) {
                    return true;
                }
            } else {
                android.util.Log.w(TAG, "Undefined Audio Output terminal l: " + next.getLength() + " t:0x" + java.lang.Integer.toHexString(next.getType()));
            }
        }
        return false;
    }

    public boolean hasAudioInterface() {
        return true ^ getInterfaceDescriptorsForClass(1).isEmpty();
    }

    public boolean hasAudioTerminal(int i, int i2) {
        java.util.Iterator<com.android.server.usb.descriptors.UsbDescriptor> it = this.mDescriptors.iterator();
        while (it.hasNext()) {
            com.android.server.usb.descriptors.UsbDescriptor next = it.next();
            if (next instanceof com.android.server.usb.descriptors.UsbACTerminal) {
                com.android.server.usb.descriptors.UsbACTerminal usbACTerminal = (com.android.server.usb.descriptors.UsbACTerminal) next;
                if (usbACTerminal.getSubclass() == 1 && usbACTerminal.getSubtype() == i && usbACTerminal.getTerminalType() == i2) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean hasAudioTerminalExcludeType(int i, int i2) {
        java.util.Iterator<com.android.server.usb.descriptors.UsbDescriptor> it = this.mDescriptors.iterator();
        while (it.hasNext()) {
            com.android.server.usb.descriptors.UsbDescriptor next = it.next();
            if (next instanceof com.android.server.usb.descriptors.UsbACTerminal) {
                com.android.server.usb.descriptors.UsbACTerminal usbACTerminal = (com.android.server.usb.descriptors.UsbACTerminal) next;
                if (usbACTerminal.getSubclass() == 1 && usbACTerminal.getSubtype() == i && usbACTerminal.getTerminalType() != i2) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean hasAudioPlayback() {
        return hasAudioTerminalExcludeType(3, 257) && hasAudioTerminal(2, 257);
    }

    public boolean hasAudioCapture() {
        return hasAudioTerminalExcludeType(2, 257) && hasAudioTerminal(3, 257);
    }

    public boolean hasVideoCapture() {
        java.util.Iterator<com.android.server.usb.descriptors.UsbDescriptor> it = this.mDescriptors.iterator();
        while (it.hasNext()) {
            if (it.next() instanceof com.android.server.usb.descriptors.UsbVCInputTerminal) {
                return true;
            }
        }
        return false;
    }

    public boolean hasVideoPlayback() {
        java.util.Iterator<com.android.server.usb.descriptors.UsbDescriptor> it = this.mDescriptors.iterator();
        while (it.hasNext()) {
            if (it.next() instanceof com.android.server.usb.descriptors.UsbVCOutputTerminal) {
                return true;
            }
        }
        return false;
    }

    public boolean hasHIDInterface() {
        return !getInterfaceDescriptorsForClass(3).isEmpty();
    }

    public boolean hasStorageInterface() {
        return !getInterfaceDescriptorsForClass(8).isEmpty();
    }

    public boolean hasMIDIInterface() {
        java.util.Iterator<com.android.server.usb.descriptors.UsbDescriptor> it = getInterfaceDescriptorsForClass(1).iterator();
        while (it.hasNext()) {
            com.android.server.usb.descriptors.UsbDescriptor next = it.next();
            if (next instanceof com.android.server.usb.descriptors.UsbInterfaceDescriptor) {
                if (((com.android.server.usb.descriptors.UsbInterfaceDescriptor) next).getUsbSubclass() == 3) {
                    return true;
                }
            } else {
                android.util.Log.w(TAG, "Undefined Audio Class Interface l: " + next.getLength() + " t:0x" + java.lang.Integer.toHexString(next.getType()));
            }
        }
        return false;
    }

    public boolean containsUniversalMidiDeviceEndpoint() {
        return doesInterfaceContainEndpoint(findUniversalMidiInterfaceDescriptors());
    }

    public boolean containsLegacyMidiDeviceEndpoint() {
        return doesInterfaceContainEndpoint(findLegacyMidiInterfaceDescriptors());
    }

    public boolean doesInterfaceContainEndpoint(java.util.ArrayList<com.android.server.usb.descriptors.UsbInterfaceDescriptor> arrayList) {
        int i = 0;
        int i2 = 0;
        for (int i3 = 0; i3 < arrayList.size(); i3++) {
            com.android.server.usb.descriptors.UsbInterfaceDescriptor usbInterfaceDescriptor = arrayList.get(i3);
            for (int i4 = 0; i4 < usbInterfaceDescriptor.getNumEndpoints(); i4++) {
                if (usbInterfaceDescriptor.getEndpointDescriptor(i4).getDirection() == 0) {
                    i++;
                } else {
                    i2++;
                }
            }
        }
        return i > 0 || i2 > 0;
    }

    public java.util.ArrayList<com.android.server.usb.descriptors.UsbInterfaceDescriptor> findUniversalMidiInterfaceDescriptors() {
        return findMidiInterfaceDescriptors(512);
    }

    public java.util.ArrayList<com.android.server.usb.descriptors.UsbInterfaceDescriptor> findLegacyMidiInterfaceDescriptors() {
        return findMidiInterfaceDescriptors(256);
    }

    private java.util.ArrayList<com.android.server.usb.descriptors.UsbInterfaceDescriptor> findMidiInterfaceDescriptors(int i) {
        com.android.server.usb.descriptors.UsbDescriptor midiHeaderInterfaceDescriptor;
        java.util.ArrayList<com.android.server.usb.descriptors.UsbDescriptor> interfaceDescriptorsForClass = getInterfaceDescriptorsForClass(1);
        java.util.ArrayList<com.android.server.usb.descriptors.UsbInterfaceDescriptor> arrayList = new java.util.ArrayList<>();
        java.util.Iterator<com.android.server.usb.descriptors.UsbDescriptor> it = interfaceDescriptorsForClass.iterator();
        while (it.hasNext()) {
            com.android.server.usb.descriptors.UsbDescriptor next = it.next();
            if (next instanceof com.android.server.usb.descriptors.UsbInterfaceDescriptor) {
                com.android.server.usb.descriptors.UsbInterfaceDescriptor usbInterfaceDescriptor = (com.android.server.usb.descriptors.UsbInterfaceDescriptor) next;
                if (usbInterfaceDescriptor.getUsbSubclass() == 3 && (midiHeaderInterfaceDescriptor = usbInterfaceDescriptor.getMidiHeaderInterfaceDescriptor()) != null && (midiHeaderInterfaceDescriptor instanceof com.android.server.usb.descriptors.UsbMSMidiHeader) && ((com.android.server.usb.descriptors.UsbMSMidiHeader) midiHeaderInterfaceDescriptor).getMidiStreamingClass() == i) {
                    arrayList.add(usbInterfaceDescriptor);
                }
            } else {
                android.util.Log.w(TAG, "Undefined Audio Class Interface l: " + next.getLength() + " t:0x" + java.lang.Integer.toHexString(next.getType()));
            }
        }
        return arrayList;
    }

    public int calculateMidiInterfaceDescriptorsCount() {
        com.android.server.usb.descriptors.UsbDescriptor midiHeaderInterfaceDescriptor;
        java.util.Iterator<com.android.server.usb.descriptors.UsbDescriptor> it = getInterfaceDescriptorsForClass(1).iterator();
        int i = 0;
        while (it.hasNext()) {
            com.android.server.usb.descriptors.UsbDescriptor next = it.next();
            if (next instanceof com.android.server.usb.descriptors.UsbInterfaceDescriptor) {
                com.android.server.usb.descriptors.UsbInterfaceDescriptor usbInterfaceDescriptor = (com.android.server.usb.descriptors.UsbInterfaceDescriptor) next;
                if (usbInterfaceDescriptor.getUsbSubclass() == 3 && (midiHeaderInterfaceDescriptor = usbInterfaceDescriptor.getMidiHeaderInterfaceDescriptor()) != null && (midiHeaderInterfaceDescriptor instanceof com.android.server.usb.descriptors.UsbMSMidiHeader)) {
                    i++;
                }
            } else {
                android.util.Log.w(TAG, "Undefined Audio Class Interface l: " + next.getLength() + " t:0x" + java.lang.Integer.toHexString(next.getType()));
            }
        }
        return i;
    }

    private int calculateNumLegacyMidiPorts(boolean z) {
        com.android.server.usb.descriptors.UsbConfigDescriptor usbConfigDescriptor;
        com.android.server.usb.descriptors.UsbDescriptor classSpecificEndpointDescriptor;
        com.android.server.usb.descriptors.UsbDescriptor midiHeaderInterfaceDescriptor;
        java.util.Iterator<com.android.server.usb.descriptors.UsbDescriptor> it = this.mDescriptors.iterator();
        while (true) {
            if (!it.hasNext()) {
                usbConfigDescriptor = null;
                break;
            }
            com.android.server.usb.descriptors.UsbDescriptor next = it.next();
            if (next.getType() == 2) {
                if (next instanceof com.android.server.usb.descriptors.UsbConfigDescriptor) {
                    usbConfigDescriptor = (com.android.server.usb.descriptors.UsbConfigDescriptor) next;
                    break;
                }
                android.util.Log.w(TAG, "Unrecognized Config l: " + next.getLength() + " t:0x" + java.lang.Integer.toHexString(next.getType()));
            }
        }
        if (usbConfigDescriptor == null) {
            android.util.Log.w(TAG, "Config not found");
            return 0;
        }
        java.util.ArrayList arrayList = new java.util.ArrayList();
        java.util.Iterator<com.android.server.usb.descriptors.UsbInterfaceDescriptor> it2 = usbConfigDescriptor.getInterfaceDescriptors().iterator();
        while (it2.hasNext()) {
            com.android.server.usb.descriptors.UsbInterfaceDescriptor next2 = it2.next();
            if (next2.getUsbClass() == 1 && next2.getUsbSubclass() == 3 && (midiHeaderInterfaceDescriptor = next2.getMidiHeaderInterfaceDescriptor()) != null && (midiHeaderInterfaceDescriptor instanceof com.android.server.usb.descriptors.UsbMSMidiHeader) && ((com.android.server.usb.descriptors.UsbMSMidiHeader) midiHeaderInterfaceDescriptor).getMidiStreamingClass() == 256) {
                arrayList.add(next2);
            }
        }
        java.util.Iterator it3 = arrayList.iterator();
        int i = 0;
        while (it3.hasNext()) {
            com.android.server.usb.descriptors.UsbInterfaceDescriptor usbInterfaceDescriptor = (com.android.server.usb.descriptors.UsbInterfaceDescriptor) it3.next();
            for (int i2 = 0; i2 < usbInterfaceDescriptor.getNumEndpoints(); i2++) {
                com.android.server.usb.descriptors.UsbEndpointDescriptor endpointDescriptor = usbInterfaceDescriptor.getEndpointDescriptor(i2);
                if ((endpointDescriptor.getDirection() == 0) == z && (classSpecificEndpointDescriptor = endpointDescriptor.getClassSpecificEndpointDescriptor()) != null && (classSpecificEndpointDescriptor instanceof com.android.server.usb.descriptors.UsbACMidi10Endpoint)) {
                    i += ((com.android.server.usb.descriptors.UsbACMidi10Endpoint) classSpecificEndpointDescriptor).getNumJacks();
                }
            }
        }
        return i;
    }

    public int calculateNumLegacyMidiInputs() {
        return calculateNumLegacyMidiPorts(false);
    }

    public int calculateNumLegacyMidiOutputs() {
        return calculateNumLegacyMidiPorts(true);
    }

    public float getInputHeadsetProbability() {
        boolean hasMIDIInterface = hasMIDIInterface();
        float f = com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE;
        if (hasMIDIInterface) {
            return com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE;
        }
        boolean hasMic = hasMic();
        boolean hasSpeaker = hasSpeaker();
        if (hasMic && hasSpeaker) {
            f = 0.75f;
        }
        if (hasMic && hasHIDInterface()) {
            return f + 0.25f;
        }
        return f;
    }

    public boolean isInputHeadset() {
        return getInputHeadsetProbability() >= 0.75f;
    }

    private int getMaximumChannelCount() {
        java.util.Iterator<com.android.server.usb.descriptors.UsbDescriptor> it = this.mDescriptors.iterator();
        int i = 0;
        while (it.hasNext()) {
            com.android.server.usb.descriptors.report.Reporting reporting = (com.android.server.usb.descriptors.UsbDescriptor) it.next();
            if (reporting instanceof com.android.server.usb.descriptors.UsbAudioChannelCluster) {
                i = java.lang.Math.max(i, (int) ((com.android.server.usb.descriptors.UsbAudioChannelCluster) reporting).getChannelCount());
            }
        }
        return i;
    }

    public float getOutputHeadsetLikelihood() {
        boolean hasMIDIInterface = hasMIDIInterface();
        float f = com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE;
        if (hasMIDIInterface) {
            return com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE;
        }
        java.util.Iterator<com.android.server.usb.descriptors.UsbDescriptor> it = getACInterfaceDescriptors((byte) 3, 1).iterator();
        boolean z = false;
        boolean z2 = false;
        boolean z3 = false;
        while (it.hasNext()) {
            com.android.server.usb.descriptors.UsbDescriptor next = it.next();
            if (next instanceof com.android.server.usb.descriptors.UsbACTerminal) {
                com.android.server.usb.descriptors.UsbACTerminal usbACTerminal = (com.android.server.usb.descriptors.UsbACTerminal) next;
                if (usbACTerminal.getTerminalType() == 769) {
                    if (usbACTerminal.getAssocTerminal() == 0) {
                        z2 = true;
                    } else {
                        z2 = true;
                        z3 = true;
                    }
                } else if (usbACTerminal.getTerminalType() == 770 || usbACTerminal.getTerminalType() == 1026) {
                    z = true;
                }
            } else {
                android.util.Log.w(TAG, "Undefined Audio Output terminal l: " + next.getLength() + " t:0x" + java.lang.Integer.toHexString(next.getType()));
            }
        }
        if (z) {
            f = 0.75f;
        } else if (z2) {
            if (!z3) {
                f = 0.5f;
            } else {
                f = 0.75f;
            }
            if (getMaximumChannelCount() > 2) {
                f -= 0.25f;
            }
        }
        if ((z || z2) && hasHIDInterface()) {
            return f + 0.25f;
        }
        return f;
    }

    public boolean isOutputHeadset() {
        return getOutputHeadsetLikelihood() >= 0.75f;
    }

    public boolean isDock() {
        if (hasMIDIInterface() || hasHIDInterface()) {
            return false;
        }
        java.util.ArrayList<com.android.server.usb.descriptors.UsbDescriptor> aCInterfaceDescriptors = getACInterfaceDescriptors((byte) 3, 1);
        if (aCInterfaceDescriptors.size() != 1) {
            return false;
        }
        if (aCInterfaceDescriptors.get(0) instanceof com.android.server.usb.descriptors.UsbACTerminal) {
            if (((com.android.server.usb.descriptors.UsbACTerminal) aCInterfaceDescriptors.get(0)).getTerminalType() == 1538) {
                return true;
            }
        } else {
            android.util.Log.w(TAG, "Undefined Audio Output terminal l: " + aCInterfaceDescriptors.get(0).getLength() + " t:0x" + java.lang.Integer.toHexString(aCInterfaceDescriptors.get(0).getType()));
        }
        return false;
    }
}
