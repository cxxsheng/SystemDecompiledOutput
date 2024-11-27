package com.android.server.usb;

/* loaded from: classes2.dex */
public final class UsbAlsaManager {
    private static final int ALSA_DEVICE_TYPE_CAPTURE = 2;
    private static final int ALSA_DEVICE_TYPE_MIDI = 3;
    private static final int ALSA_DEVICE_TYPE_PLAYBACK = 1;
    private static final int ALSA_DEVICE_TYPE_UNKNOWN = 0;
    private static final java.lang.String ALSA_DIRECTORY = "/dev/snd/";
    private static final boolean DEBUG = false;
    private static final int USB_DENYLIST_INPUT = 2;
    private static final int USB_DENYLIST_OUTPUT = 1;
    private android.media.IAudioService mAudioService;
    private final android.content.Context mContext;
    private final boolean mHasMidiFeature;
    private static final java.lang.String TAG = com.android.server.usb.UsbAlsaManager.class.getSimpleName();
    private static final boolean IS_MULTI_MODE = android.os.SystemProperties.getBoolean("ro.audio.multi_usb_mode", false);
    private static final int USB_VENDORID_SONY = 1356;
    private static final int USB_PRODUCTID_PS4CONTROLLER_ZCT1 = 1476;
    private static final int USB_PRODUCTID_PS4CONTROLLER_ZCT2 = 2508;
    private static final int USB_PRODUCTID_PS5CONTROLLER = 3302;
    static final java.util.List<com.android.server.usb.UsbAlsaManager.DenyListEntry> sDeviceDenylist = java.util.Arrays.asList(new com.android.server.usb.UsbAlsaManager.DenyListEntry(USB_VENDORID_SONY, USB_PRODUCTID_PS4CONTROLLER_ZCT1, 1), new com.android.server.usb.UsbAlsaManager.DenyListEntry(USB_VENDORID_SONY, USB_PRODUCTID_PS4CONTROLLER_ZCT2, 1), new com.android.server.usb.UsbAlsaManager.DenyListEntry(USB_VENDORID_SONY, USB_PRODUCTID_PS5CONTROLLER, 1));
    private final com.android.internal.alsa.AlsaCardsParser mCardsParser = new com.android.internal.alsa.AlsaCardsParser();
    private final java.util.ArrayList<com.android.server.usb.UsbAlsaDevice> mAlsaDevices = new java.util.ArrayList<>();
    private java.util.HashMap<java.lang.Integer, java.util.Stack<com.android.server.usb.UsbAlsaDevice>> mAttachedDevices = new java.util.HashMap<>();
    private final java.util.HashMap<java.lang.String, com.android.server.usb.UsbAlsaMidiDevice> mMidiDevices = new java.util.HashMap<>();
    private com.android.server.usb.UsbAlsaMidiDevice mPeripheralMidiDevice = null;
    private final java.util.HashSet<java.lang.Integer> mAlsaCards = new java.util.HashSet<>();
    private final android.os.FileObserver mAlsaObserver = new android.os.FileObserver(new java.io.File(ALSA_DIRECTORY), 768) { // from class: com.android.server.usb.UsbAlsaManager.1
        @Override // android.os.FileObserver
        public void onEvent(int i, java.lang.String str) {
            switch (i) {
                case 256:
                    com.android.server.usb.UsbAlsaManager.this.alsaFileAdded(str);
                    break;
                case 512:
                    com.android.server.usb.UsbAlsaManager.this.alsaFileRemoved(str);
                    break;
            }
        }
    };

    private static class DenyListEntry {
        final int mFlags;
        final int mProductId;
        final int mVendorId;

        DenyListEntry(int i, int i2, int i3) {
            this.mVendorId = i;
            this.mProductId = i2;
            this.mFlags = i3;
        }
    }

    private static boolean isDeviceDenylisted(int i, int i2, int i3) {
        for (com.android.server.usb.UsbAlsaManager.DenyListEntry denyListEntry : sDeviceDenylist) {
            if (denyListEntry.mVendorId == i && denyListEntry.mProductId == i2) {
                return (denyListEntry.mFlags & i3) != 0;
            }
        }
        return false;
    }

    UsbAlsaManager(android.content.Context context) {
        this.mContext = context;
        this.mHasMidiFeature = context.getPackageManager().hasSystemFeature("android.software.midi");
    }

    public void systemReady() {
        this.mAudioService = android.media.IAudioService.Stub.asInterface(android.os.ServiceManager.getService("audio"));
        this.mAlsaObserver.startWatching();
    }

    private synchronized void selectAlsaDevice(com.android.server.usb.UsbAlsaDevice usbAlsaDevice) {
        if (android.provider.Settings.Secure.getInt(this.mContext.getContentResolver(), "usb_audio_automatic_routing_disabled", 0) != 0) {
            return;
        }
        usbAlsaDevice.start();
    }

    private synchronized void deselectAlsaDevice(com.android.server.usb.UsbAlsaDevice usbAlsaDevice) {
        usbAlsaDevice.stop();
    }

    private int getAlsaDeviceListIndexFor(java.lang.String str) {
        for (int i = 0; i < this.mAlsaDevices.size(); i++) {
            if (this.mAlsaDevices.get(i).getDeviceAddress().equals(str)) {
                return i;
            }
        }
        return -1;
    }

    private void addDeviceToAttachedDevicesMap(int i, com.android.server.usb.UsbAlsaDevice usbAlsaDevice) {
        if (i == 0) {
            android.util.Slog.i(TAG, "Ignore caching device as the type is NONE, device=" + usbAlsaDevice);
            return;
        }
        java.util.Stack<com.android.server.usb.UsbAlsaDevice> stack = this.mAttachedDevices.get(java.lang.Integer.valueOf(i));
        if (stack == null) {
            this.mAttachedDevices.put(java.lang.Integer.valueOf(i), new java.util.Stack<>());
            stack = this.mAttachedDevices.get(java.lang.Integer.valueOf(i));
        }
        stack.push(usbAlsaDevice);
    }

    private void addAlsaDevice(com.android.server.usb.UsbAlsaDevice usbAlsaDevice) {
        this.mAlsaDevices.add(0, usbAlsaDevice);
        addDeviceToAttachedDevicesMap(usbAlsaDevice.getInputDeviceType(), usbAlsaDevice);
        addDeviceToAttachedDevicesMap(usbAlsaDevice.getOutputDeviceType(), usbAlsaDevice);
    }

    private void removeDeviceFromAttachedDevicesMap(int i, com.android.server.usb.UsbAlsaDevice usbAlsaDevice) {
        java.util.Stack<com.android.server.usb.UsbAlsaDevice> stack = this.mAttachedDevices.get(java.lang.Integer.valueOf(i));
        if (stack == null) {
            return;
        }
        stack.remove(usbAlsaDevice);
        if (stack.isEmpty()) {
            this.mAttachedDevices.remove(java.lang.Integer.valueOf(i));
        }
    }

    private com.android.server.usb.UsbAlsaDevice removeAlsaDevice(java.lang.String str) {
        int alsaDeviceListIndexFor = getAlsaDeviceListIndexFor(str);
        if (alsaDeviceListIndexFor > -1) {
            com.android.server.usb.UsbAlsaDevice remove = this.mAlsaDevices.remove(alsaDeviceListIndexFor);
            removeDeviceFromAttachedDevicesMap(remove.getOutputDeviceType(), remove);
            removeDeviceFromAttachedDevicesMap(remove.getInputDeviceType(), remove);
            return remove;
        }
        return null;
    }

    private com.android.server.usb.UsbAlsaDevice selectDefaultDevice(int i) {
        java.util.Stack<com.android.server.usb.UsbAlsaDevice> stack = this.mAttachedDevices.get(java.lang.Integer.valueOf(i));
        if (stack == null || stack.isEmpty()) {
            return null;
        }
        com.android.server.usb.UsbAlsaDevice peek = stack.peek();
        android.util.Slog.d(TAG, "select default device:" + peek);
        if (android.media.AudioManager.isInputDevice(i)) {
            peek.startInput();
        } else {
            peek.startOutput();
        }
        return peek;
    }

    private void deselectCurrentDevice(int i) {
        java.util.Stack<com.android.server.usb.UsbAlsaDevice> stack;
        if (i == 0 || (stack = this.mAttachedDevices.get(java.lang.Integer.valueOf(i))) == null || stack.isEmpty()) {
            return;
        }
        com.android.server.usb.UsbAlsaDevice peek = stack.peek();
        android.util.Slog.d(TAG, "deselect current device:" + peek);
        if (android.media.AudioManager.isInputDevice(i)) {
            peek.stopInput();
        } else {
            peek.stopOutput();
        }
    }

    void usbDeviceAdded(java.lang.String str, android.hardware.usb.UsbDevice usbDevice, com.android.server.usb.descriptors.UsbDescriptorParser usbDescriptorParser) {
        this.mCardsParser.scan();
        com.android.internal.alsa.AlsaCardsParser.AlsaCardRecord findCardNumFor = this.mCardsParser.findCardNumFor(str);
        if (findCardNumFor == null) {
            if (usbDescriptorParser.hasAudioInterface()) {
                android.util.Slog.e(TAG, "usbDeviceAdded(): cannot find sound card for " + str);
                return;
            }
            return;
        }
        waitForAlsaDevice(findCardNumFor.getCardNum(), true);
        boolean z = usbDescriptorParser.hasInput() && !isDeviceDenylisted(usbDevice.getVendorId(), usbDevice.getProductId(), 2);
        boolean z2 = usbDescriptorParser.hasOutput() && !isDeviceDenylisted(usbDevice.getVendorId(), usbDevice.getProductId(), 1);
        if (z || z2) {
            boolean isInputHeadset = usbDescriptorParser.isInputHeadset();
            boolean isOutputHeadset = usbDescriptorParser.isOutputHeadset();
            boolean isDock = usbDescriptorParser.isDock();
            if (this.mAudioService == null) {
                android.util.Slog.e(TAG, "no AudioService");
                return;
            }
            com.android.server.usb.UsbAlsaDevice usbAlsaDevice = new com.android.server.usb.UsbAlsaDevice(this.mAudioService, findCardNumFor.getCardNum(), 0, str, z2, z, isInputHeadset, isOutputHeadset, isDock);
            usbAlsaDevice.setDeviceNameAndDescription(findCardNumFor.getCardName(), findCardNumFor.getCardDescription());
            if (IS_MULTI_MODE) {
                deselectCurrentDevice(usbAlsaDevice.getInputDeviceType());
                deselectCurrentDevice(usbAlsaDevice.getOutputDeviceType());
            } else if (!this.mAlsaDevices.isEmpty()) {
                deselectAlsaDevice(this.mAlsaDevices.get(0));
            }
            addAlsaDevice(usbAlsaDevice);
            selectAlsaDevice(usbAlsaDevice);
        }
        addMidiDevice(str, usbDevice, usbDescriptorParser, findCardNumFor);
        logDevices("deviceAdded()");
    }

    private void addMidiDevice(java.lang.String str, android.hardware.usb.UsbDevice usbDevice, com.android.server.usb.descriptors.UsbDescriptorParser usbDescriptorParser, com.android.internal.alsa.AlsaCardsParser.AlsaCardRecord alsaCardRecord) {
        java.lang.String str2;
        boolean hasMIDIInterface = usbDescriptorParser.hasMIDIInterface();
        boolean containsUniversalMidiDeviceEndpoint = usbDescriptorParser.containsUniversalMidiDeviceEndpoint();
        if (this.mHasMidiFeature && hasMIDIInterface && !containsUniversalMidiDeviceEndpoint) {
            android.os.Bundle bundle = new android.os.Bundle();
            java.lang.String manufacturerName = usbDevice.getManufacturerName();
            java.lang.String productName = usbDevice.getProductName();
            java.lang.String version = usbDevice.getVersion();
            if (manufacturerName == null || manufacturerName.isEmpty()) {
                str2 = productName;
            } else if (productName == null || productName.isEmpty()) {
                str2 = manufacturerName;
            } else {
                str2 = manufacturerName + " " + productName;
            }
            bundle.putString("name", str2);
            bundle.putString("manufacturer", manufacturerName);
            bundle.putString("product", productName);
            bundle.putString("version", version);
            bundle.putString("serial_number", usbDevice.getSerialNumber());
            bundle.putInt("alsa_card", alsaCardRecord.getCardNum());
            bundle.putInt("alsa_device", 0);
            bundle.putParcelable("usb_device", usbDevice);
            com.android.server.usb.UsbAlsaMidiDevice create = com.android.server.usb.UsbAlsaMidiDevice.create(this.mContext, bundle, alsaCardRecord.getCardNum(), 0, usbDescriptorParser.calculateNumLegacyMidiInputs(), usbDescriptorParser.calculateNumLegacyMidiOutputs());
            if (create != null) {
                this.mMidiDevices.put(str, create);
            }
        }
    }

    synchronized void usbDeviceRemoved(java.lang.String str) {
        try {
            com.android.server.usb.UsbAlsaDevice removeAlsaDevice = removeAlsaDevice(str);
            android.util.Slog.i(TAG, "USB Audio Device Removed: " + removeAlsaDevice);
            if (removeAlsaDevice != null) {
                waitForAlsaDevice(removeAlsaDevice.getCardNum(), false);
                deselectAlsaDevice(removeAlsaDevice);
                if (IS_MULTI_MODE) {
                    selectDefaultDevice(removeAlsaDevice.getOutputDeviceType());
                    selectDefaultDevice(removeAlsaDevice.getInputDeviceType());
                } else if (!this.mAlsaDevices.isEmpty() && this.mAlsaDevices.get(0) != null) {
                    selectAlsaDevice(this.mAlsaDevices.get(0));
                }
            }
            com.android.server.usb.UsbAlsaMidiDevice remove = this.mMidiDevices.remove(str);
            if (remove != null) {
                android.util.Slog.i(TAG, "USB MIDI Device Removed: " + str);
                libcore.io.IoUtils.closeQuietly(remove);
            }
            logDevices("usbDeviceRemoved()");
        } catch (java.lang.Throwable th) {
            throw th;
        }
    }

    void setPeripheralMidiState(boolean z, int i, int i2) {
        if (!this.mHasMidiFeature) {
            return;
        }
        if (!z || this.mPeripheralMidiDevice != null) {
            if (!z && this.mPeripheralMidiDevice != null) {
                libcore.io.IoUtils.closeQuietly(this.mPeripheralMidiDevice);
                this.mPeripheralMidiDevice = null;
                return;
            }
            return;
        }
        android.os.Bundle bundle = new android.os.Bundle();
        android.content.res.Resources resources = this.mContext.getResources();
        bundle.putString("name", resources.getString(android.R.string.time_picker_mode));
        bundle.putString("manufacturer", resources.getString(android.R.string.time_picker_minute_label));
        bundle.putString("product", resources.getString(android.R.string.time_picker_prompt_label));
        bundle.putInt("alsa_card", i);
        bundle.putInt("alsa_device", i2);
        this.mPeripheralMidiDevice = com.android.server.usb.UsbAlsaMidiDevice.create(this.mContext, bundle, i, i2, 1, 1);
    }

    private boolean waitForAlsaDevice(int i, boolean z) {
        boolean contains;
        synchronized (this.mAlsaCards) {
            long elapsedRealtime = android.os.SystemClock.elapsedRealtime() + 2500;
            while ((this.mAlsaCards.contains(java.lang.Integer.valueOf(i)) ^ z) && elapsedRealtime > android.os.SystemClock.elapsedRealtime()) {
                long elapsedRealtime2 = elapsedRealtime - android.os.SystemClock.elapsedRealtime();
                if (elapsedRealtime2 > 0) {
                    try {
                        this.mAlsaCards.wait(elapsedRealtime2);
                    } catch (java.lang.InterruptedException e) {
                        android.util.Slog.d(TAG, "usb: InterruptedException while waiting for ALSA file.");
                    }
                }
            }
            contains = this.mAlsaCards.contains(java.lang.Integer.valueOf(i));
            if ((z ^ contains) && elapsedRealtime > android.os.SystemClock.elapsedRealtime()) {
                android.util.Slog.e(TAG, "waitForAlsaDevice(" + i + ") timeout");
            } else {
                android.util.Slog.i(TAG, "waitForAlsaDevice for device card=" + i + ", isAdded=" + z + ", found=" + contains);
            }
        }
        return contains;
    }

    private int getCardNumberFromAlsaFilePath(java.lang.String str) {
        char c;
        if (str.startsWith("pcmC")) {
            if (str.endsWith("p")) {
                c = 1;
            } else {
                if (str.endsWith("c")) {
                    c = 2;
                }
                c = 0;
            }
        } else {
            if (str.startsWith("midiC")) {
                c = 3;
            }
            c = 0;
        }
        if (c == 0) {
            android.util.Slog.i(TAG, "Unknown type file(" + str + ") added.");
            return -1;
        }
        try {
            return java.lang.Integer.parseInt(str.substring(str.indexOf(67) + 1, str.indexOf(68)));
        } catch (java.lang.Exception e) {
            android.util.Slog.e(TAG, "Could not parse ALSA file name " + str, e);
            return -1;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void alsaFileAdded(java.lang.String str) {
        android.util.Slog.i(TAG, "alsaFileAdded(" + str + ")");
        int cardNumberFromAlsaFilePath = getCardNumberFromAlsaFilePath(str);
        if (cardNumberFromAlsaFilePath == -1) {
            return;
        }
        synchronized (this.mAlsaCards) {
            try {
                if (!this.mAlsaCards.contains(java.lang.Integer.valueOf(cardNumberFromAlsaFilePath))) {
                    android.util.Slog.d(TAG, "Adding ALSA device card=" + cardNumberFromAlsaFilePath);
                    this.mAlsaCards.add(java.lang.Integer.valueOf(cardNumberFromAlsaFilePath));
                    this.mAlsaCards.notifyAll();
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void alsaFileRemoved(java.lang.String str) {
        int cardNumberFromAlsaFilePath = getCardNumberFromAlsaFilePath(str);
        if (cardNumberFromAlsaFilePath == -1) {
            return;
        }
        synchronized (this.mAlsaCards) {
            this.mAlsaCards.remove(java.lang.Integer.valueOf(cardNumberFromAlsaFilePath));
        }
    }

    public void dump(com.android.internal.util.dump.DualDumpOutputStream dualDumpOutputStream, java.lang.String str, long j) {
        long start = dualDumpOutputStream.start(str, j);
        dualDumpOutputStream.write("cards_parser", 1120986464257L, this.mCardsParser.getScanStatus());
        java.util.Iterator<com.android.server.usb.UsbAlsaDevice> it = this.mAlsaDevices.iterator();
        while (it.hasNext()) {
            it.next().dump(dualDumpOutputStream, "alsa_devices", 2246267895810L);
        }
        for (java.lang.String str2 : this.mMidiDevices.keySet()) {
            this.mMidiDevices.get(str2).dump(str2, dualDumpOutputStream, "alsa_midi_devices", 2246267895812L);
        }
        dualDumpOutputStream.end(start);
    }

    public void logDevicesList(java.lang.String str) {
    }

    public void logDevices(java.lang.String str) {
    }
}
