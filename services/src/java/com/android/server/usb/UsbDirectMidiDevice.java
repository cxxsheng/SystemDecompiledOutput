package com.android.server.usb;

/* loaded from: classes2.dex */
public final class UsbDirectMidiDevice implements java.io.Closeable {
    private static final int BULK_TRANSFER_NUMBER_OF_RETRIES = 20;
    private static final int BULK_TRANSFER_TIMEOUT_MILLISECONDS = 50;
    private static final boolean DEBUG = true;
    private static final byte MESSAGE_TYPE_MIDI_1_CHANNEL_VOICE = 2;
    private static final byte MESSAGE_TYPE_MIDI_2_CHANNEL_VOICE = 4;
    private static final java.lang.String TAG = "UsbDirectMidiDevice";
    private static final int THREAD_JOIN_TIMEOUT_MILLISECONDS = 200;
    private android.content.Context mContext;
    private java.util.ArrayList<java.util.ArrayList<java.lang.Integer>> mInputUsbEndpointCableCounts;
    private java.util.ArrayList<java.util.ArrayList<android.hardware.usb.UsbEndpoint>> mInputUsbEndpoints;
    private boolean mIsOpen;
    private final boolean mIsUniversalMidiDevice;
    private java.util.ArrayList<java.util.ArrayList<com.android.internal.midi.MidiEventMultiScheduler>> mMidiEventMultiSchedulers;
    private final com.android.server.usb.UsbDirectMidiDevice.InputReceiverProxy[] mMidiInputPortReceivers;
    private java.lang.String mName;
    private final int mNumInputs;
    private final int mNumOutputs;
    private java.util.ArrayList<java.util.ArrayList<java.lang.Integer>> mOutputUsbEndpointCableCounts;
    private java.util.ArrayList<java.util.ArrayList<android.hardware.usb.UsbEndpoint>> mOutputUsbEndpoints;
    private com.android.server.usb.descriptors.UsbDescriptorParser mParser;
    private com.android.server.usb.PowerBoostSetter mPowerBoostSetter;
    private android.media.midi.MidiDeviceServer mServer;
    private boolean mServerAvailable;
    private final boolean mShouldCallSetInterface;
    private java.util.ArrayList<java.lang.Thread> mThreads;
    private final java.lang.String mUniqueUsbDeviceIdentifier;
    private android.hardware.usb.UsbDevice mUsbDevice;
    private java.util.ArrayList<android.hardware.usb.UsbDeviceConnection> mUsbDeviceConnections;
    private java.util.ArrayList<com.android.server.usb.descriptors.UsbInterfaceDescriptor> mUsbInterfaces;
    private com.android.server.usb.descriptors.UsbMidiBlockParser mMidiBlockParser = new com.android.server.usb.descriptors.UsbMidiBlockParser();
    private int mDefaultMidiProtocol = 1;
    private final java.lang.Object mLock = new java.lang.Object();
    private final android.media.midi.MidiDeviceServer.Callback mCallback = new android.media.midi.MidiDeviceServer.Callback() { // from class: com.android.server.usb.UsbDirectMidiDevice.1
        public void onDeviceStatusChanged(android.media.midi.MidiDeviceServer midiDeviceServer, android.media.midi.MidiDeviceStatus midiDeviceStatus) {
            android.media.midi.MidiDeviceInfo deviceInfo = midiDeviceStatus.getDeviceInfo();
            int inputPortCount = deviceInfo.getInputPortCount();
            int outputPortCount = deviceInfo.getOutputPortCount();
            int i = 0;
            for (int i2 = 0; i2 < inputPortCount; i2++) {
                if (midiDeviceStatus.isInputPortOpen(i2)) {
                    i++;
                }
            }
            for (int i3 = 0; i3 < outputPortCount; i3++) {
                if (midiDeviceStatus.getOutputPortOpenCount(i3) > 0) {
                    i += midiDeviceStatus.getOutputPortOpenCount(i3);
                }
            }
            synchronized (com.android.server.usb.UsbDirectMidiDevice.this.mLock) {
                try {
                    android.util.Log.d(com.android.server.usb.UsbDirectMidiDevice.TAG, "numOpenPorts: " + i + " isOpen: " + com.android.server.usb.UsbDirectMidiDevice.this.mIsOpen + " mServerAvailable: " + com.android.server.usb.UsbDirectMidiDevice.this.mServerAvailable);
                    if (i > 0 && !com.android.server.usb.UsbDirectMidiDevice.this.mIsOpen && com.android.server.usb.UsbDirectMidiDevice.this.mServerAvailable) {
                        com.android.server.usb.UsbDirectMidiDevice.this.openLocked();
                    } else if (i == 0 && com.android.server.usb.UsbDirectMidiDevice.this.mIsOpen) {
                        com.android.server.usb.UsbDirectMidiDevice.this.closeLocked();
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        public void onClose() {
        }
    };

    private static final class InputReceiverProxy extends android.media.midi.MidiReceiver {
        private android.media.midi.MidiReceiver mReceiver;

        private InputReceiverProxy() {
        }

        @Override // android.media.midi.MidiReceiver
        public void onSend(byte[] bArr, int i, int i2, long j) throws java.io.IOException {
            android.media.midi.MidiReceiver midiReceiver = this.mReceiver;
            if (midiReceiver != null) {
                midiReceiver.send(bArr, i, i2, j);
            }
        }

        public void setReceiver(android.media.midi.MidiReceiver midiReceiver) {
            this.mReceiver = midiReceiver;
        }

        @Override // android.media.midi.MidiReceiver
        public void onFlush() throws java.io.IOException {
            android.media.midi.MidiReceiver midiReceiver = this.mReceiver;
            if (midiReceiver != null) {
                midiReceiver.flush();
            }
        }
    }

    public static com.android.server.usb.UsbDirectMidiDevice create(android.content.Context context, android.hardware.usb.UsbDevice usbDevice, com.android.server.usb.descriptors.UsbDescriptorParser usbDescriptorParser, boolean z, java.lang.String str) {
        com.android.server.usb.UsbDirectMidiDevice usbDirectMidiDevice = new com.android.server.usb.UsbDirectMidiDevice(usbDevice, usbDescriptorParser, z, str);
        if (!usbDirectMidiDevice.register(context)) {
            libcore.io.IoUtils.closeQuietly(usbDirectMidiDevice);
            android.util.Log.e(TAG, "createDeviceServer failed");
            return null;
        }
        return usbDirectMidiDevice;
    }

    private UsbDirectMidiDevice(android.hardware.usb.UsbDevice usbDevice, com.android.server.usb.descriptors.UsbDescriptorParser usbDescriptorParser, boolean z, java.lang.String str) {
        java.util.ArrayList<com.android.server.usb.descriptors.UsbInterfaceDescriptor> findLegacyMidiInterfaceDescriptors;
        this.mPowerBoostSetter = null;
        this.mUsbDevice = usbDevice;
        this.mParser = usbDescriptorParser;
        this.mUniqueUsbDeviceIdentifier = str;
        this.mIsUniversalMidiDevice = z;
        this.mShouldCallSetInterface = usbDescriptorParser.calculateMidiInterfaceDescriptorsCount() > 1;
        if (z) {
            findLegacyMidiInterfaceDescriptors = usbDescriptorParser.findUniversalMidiInterfaceDescriptors();
        } else {
            findLegacyMidiInterfaceDescriptors = usbDescriptorParser.findLegacyMidiInterfaceDescriptors();
        }
        this.mUsbInterfaces = new java.util.ArrayList<>();
        if (this.mUsbDevice.getConfigurationCount() > 0) {
            android.hardware.usb.UsbConfiguration configuration = this.mUsbDevice.getConfiguration(0);
            for (int i = 0; i < configuration.getInterfaceCount(); i++) {
                android.hardware.usb.UsbInterface usbInterface = configuration.getInterface(i);
                java.util.Iterator<com.android.server.usb.descriptors.UsbInterfaceDescriptor> it = findLegacyMidiInterfaceDescriptors.iterator();
                while (true) {
                    if (it.hasNext()) {
                        com.android.server.usb.descriptors.UsbInterfaceDescriptor next = it.next();
                        if (areEquivalent(usbInterface, next.toAndroid(this.mParser))) {
                            this.mUsbInterfaces.add(next);
                            break;
                        }
                    }
                }
            }
            if (this.mUsbDevice.getConfigurationCount() > 1) {
                android.util.Log.w(TAG, "Skipping some USB configurations. Count: " + this.mUsbDevice.getConfigurationCount());
            }
        }
        int i2 = 0;
        int i3 = 0;
        for (int i4 = 0; i4 < this.mUsbInterfaces.size(); i4++) {
            com.android.server.usb.descriptors.UsbInterfaceDescriptor usbInterfaceDescriptor = this.mUsbInterfaces.get(i4);
            for (int i5 = 0; i5 < usbInterfaceDescriptor.getNumEndpoints(); i5++) {
                com.android.server.usb.descriptors.UsbEndpointDescriptor endpointDescriptor = usbInterfaceDescriptor.getEndpointDescriptor(i5);
                if (endpointDescriptor.getDirection() == 0) {
                    i3 += getNumJacks(endpointDescriptor);
                } else {
                    i2 += getNumJacks(endpointDescriptor);
                }
            }
        }
        this.mNumInputs = i2;
        this.mNumOutputs = i3;
        android.util.Log.d(TAG, "Created UsbDirectMidiDevice with " + i2 + " inputs and " + i3 + " outputs. isUniversalMidiDevice: " + z);
        this.mMidiInputPortReceivers = new com.android.server.usb.UsbDirectMidiDevice.InputReceiverProxy[i3];
        for (int i6 = 0; i6 < i3; i6++) {
            this.mMidiInputPortReceivers[i6] = new com.android.server.usb.UsbDirectMidiDevice.InputReceiverProxy();
        }
        this.mPowerBoostSetter = new com.android.server.usb.PowerBoostSetter();
    }

    private int calculateDefaultMidiProtocol() {
        android.hardware.usb.UsbManager usbManager = (android.hardware.usb.UsbManager) this.mContext.getSystemService(android.hardware.usb.UsbManager.class);
        for (int i = 0; i < this.mUsbInterfaces.size(); i++) {
            com.android.server.usb.descriptors.UsbInterfaceDescriptor usbInterfaceDescriptor = this.mUsbInterfaces.get(i);
            boolean z = false;
            boolean z2 = false;
            for (int i2 = 0; i2 < usbInterfaceDescriptor.getNumEndpoints() && (!z || !z2); i2++) {
                if (usbInterfaceDescriptor.getEndpointDescriptor(i2).getDirection() == 0) {
                    z2 = true;
                } else {
                    z = true;
                }
            }
            if (z && z2) {
                android.hardware.usb.UsbDeviceConnection openDevice = usbManager.openDevice(this.mUsbDevice);
                if (updateUsbInterface(usbInterfaceDescriptor.toAndroid(this.mParser), openDevice)) {
                    int calculateMidiType = this.mMidiBlockParser.calculateMidiType(openDevice, usbInterfaceDescriptor.getInterfaceNumber(), usbInterfaceDescriptor.getAlternateSetting());
                    openDevice.close();
                    return calculateMidiType;
                }
            }
        }
        android.util.Log.w(TAG, "Cannot find interface with both input and output endpoints");
        return 1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean openLocked() {
        android.util.Log.d(TAG, "openLocked()");
        android.hardware.usb.UsbManager usbManager = (android.hardware.usb.UsbManager) this.mContext.getSystemService(android.hardware.usb.UsbManager.class);
        this.mUsbDeviceConnections = new java.util.ArrayList<>();
        this.mInputUsbEndpoints = new java.util.ArrayList<>();
        this.mOutputUsbEndpoints = new java.util.ArrayList<>();
        this.mInputUsbEndpointCableCounts = new java.util.ArrayList<>();
        this.mOutputUsbEndpointCableCounts = new java.util.ArrayList<>();
        this.mMidiEventMultiSchedulers = new java.util.ArrayList<>();
        this.mThreads = new java.util.ArrayList<>();
        for (int i = 0; i < this.mUsbInterfaces.size(); i++) {
            java.util.ArrayList<android.hardware.usb.UsbEndpoint> arrayList = new java.util.ArrayList<>();
            java.util.ArrayList<android.hardware.usb.UsbEndpoint> arrayList2 = new java.util.ArrayList<>();
            java.util.ArrayList<java.lang.Integer> arrayList3 = new java.util.ArrayList<>();
            java.util.ArrayList<java.lang.Integer> arrayList4 = new java.util.ArrayList<>();
            java.util.ArrayList<com.android.internal.midi.MidiEventMultiScheduler> arrayList5 = new java.util.ArrayList<>();
            com.android.server.usb.descriptors.UsbInterfaceDescriptor usbInterfaceDescriptor = this.mUsbInterfaces.get(i);
            for (int i2 = 0; i2 < usbInterfaceDescriptor.getNumEndpoints(); i2++) {
                com.android.server.usb.descriptors.UsbEndpointDescriptor endpointDescriptor = usbInterfaceDescriptor.getEndpointDescriptor(i2);
                if (endpointDescriptor.getDirection() == 0) {
                    arrayList2.add(endpointDescriptor.toAndroid(this.mParser));
                    arrayList4.add(java.lang.Integer.valueOf(getNumJacks(endpointDescriptor)));
                    arrayList5.add(new com.android.internal.midi.MidiEventMultiScheduler(getNumJacks(endpointDescriptor)));
                } else {
                    arrayList.add(endpointDescriptor.toAndroid(this.mParser));
                    arrayList3.add(java.lang.Integer.valueOf(getNumJacks(endpointDescriptor)));
                }
            }
            if (!arrayList2.isEmpty() || !arrayList.isEmpty()) {
                android.hardware.usb.UsbDeviceConnection openDevice = usbManager.openDevice(this.mUsbDevice);
                if (updateUsbInterface(usbInterfaceDescriptor.toAndroid(this.mParser), openDevice)) {
                    this.mUsbDeviceConnections.add(openDevice);
                    this.mInputUsbEndpoints.add(arrayList);
                    this.mOutputUsbEndpoints.add(arrayList2);
                    this.mInputUsbEndpointCableCounts.add(arrayList3);
                    this.mOutputUsbEndpointCableCounts.add(arrayList4);
                    this.mMidiEventMultiSchedulers.add(arrayList5);
                }
            }
        }
        int i3 = 0;
        for (int i4 = 0; i4 < this.mMidiEventMultiSchedulers.size(); i4++) {
            for (int i5 = 0; i5 < this.mMidiEventMultiSchedulers.get(i4).size(); i5++) {
                int intValue = this.mOutputUsbEndpointCableCounts.get(i4).get(i5).intValue();
                com.android.internal.midi.MidiEventMultiScheduler midiEventMultiScheduler = this.mMidiEventMultiSchedulers.get(i4).get(i5);
                for (int i6 = 0; i6 < intValue; i6++) {
                    this.mMidiInputPortReceivers[i3].setReceiver(midiEventMultiScheduler.getEventScheduler(i6).getReceiver());
                    i3++;
                }
            }
        }
        final android.media.midi.MidiReceiver[] outputPortReceivers = this.mServer.getOutputPortReceivers();
        int i7 = 0;
        int i8 = 0;
        while (i8 < this.mInputUsbEndpoints.size()) {
            int i9 = i7;
            for (int i10 = 0; i10 < this.mInputUsbEndpoints.get(i8).size(); i10++) {
                final android.hardware.usb.UsbDeviceConnection usbDeviceConnection = this.mUsbDeviceConnections.get(i8);
                final android.hardware.usb.UsbEndpoint usbEndpoint = this.mInputUsbEndpoints.get(i8).get(i10);
                final int intValue2 = this.mInputUsbEndpointCableCounts.get(i8).get(i10).intValue();
                final int i11 = i9;
                java.lang.Thread thread = new java.lang.Thread("UsbDirectMidiDevice input thread " + i9) { // from class: com.android.server.usb.UsbDirectMidiDevice.2
                    @Override // java.lang.Thread, java.lang.Runnable
                    public void run() {
                        byte[] pullDecodedMidiPackets;
                        int i12;
                        int i13;
                        int i14;
                        android.hardware.usb.UsbRequest usbRequest = new android.hardware.usb.UsbRequest();
                        com.android.server.usb.UsbMidiPacketConverter usbMidiPacketConverter = new com.android.server.usb.UsbMidiPacketConverter();
                        usbMidiPacketConverter.createDecoders(intValue2);
                        try {
                            try {
                                try {
                                    usbRequest.initialize(usbDeviceConnection, usbEndpoint);
                                    byte[] bArr = new byte[usbEndpoint.getMaxPacketSize()];
                                    int i15 = 1;
                                    while (true) {
                                        if (i15 == 0) {
                                            break;
                                        }
                                        java.lang.Thread.currentThread();
                                        if (java.lang.Thread.interrupted()) {
                                            android.util.Log.w(com.android.server.usb.UsbDirectMidiDevice.TAG, "input thread interrupted");
                                            break;
                                        }
                                        java.nio.ByteBuffer wrap = java.nio.ByteBuffer.wrap(bArr);
                                        if (!usbRequest.queue(wrap)) {
                                            android.util.Log.w(com.android.server.usb.UsbDirectMidiDevice.TAG, "Cannot queue request");
                                            break;
                                        }
                                        android.hardware.usb.UsbRequest requestWait = usbDeviceConnection.requestWait();
                                        if (requestWait == null) {
                                            android.util.Log.w(com.android.server.usb.UsbDirectMidiDevice.TAG, "Response is null");
                                            break;
                                        }
                                        if (usbRequest != requestWait) {
                                            android.util.Log.w(com.android.server.usb.UsbDirectMidiDevice.TAG, "Skipping response");
                                        } else {
                                            long nanoTime = java.lang.System.nanoTime();
                                            int position = wrap.position();
                                            if (position > 0) {
                                                int i16 = 0;
                                                com.android.server.usb.UsbDirectMidiDevice.logByteArray("Input before conversion ", bArr, 0, position);
                                                if (!com.android.server.usb.UsbDirectMidiDevice.this.mIsUniversalMidiDevice) {
                                                    usbMidiPacketConverter.decodeMidiPackets(bArr, position);
                                                }
                                                int i17 = 0;
                                                while (i17 < intValue2) {
                                                    if (com.android.server.usb.UsbDirectMidiDevice.this.mIsUniversalMidiDevice) {
                                                        pullDecodedMidiPackets = com.android.server.usb.UsbDirectMidiDevice.this.swapEndiannessPerWord(bArr, position);
                                                    } else {
                                                        pullDecodedMidiPackets = usbMidiPacketConverter.pullDecodedMidiPackets(i17);
                                                    }
                                                    com.android.server.usb.UsbDirectMidiDevice.logByteArray("Input " + i17 + " after conversion ", pullDecodedMidiPackets, i16, pullDecodedMidiPackets.length);
                                                    if (pullDecodedMidiPackets.length == 0) {
                                                        i13 = i16;
                                                        i14 = i17;
                                                    } else {
                                                        if (outputPortReceivers != null) {
                                                            if (outputPortReceivers[i11 + i17] == null) {
                                                                i12 = i16;
                                                            } else {
                                                                byte[] bArr2 = pullDecodedMidiPackets;
                                                                i13 = i16;
                                                                i14 = i17;
                                                                outputPortReceivers[i11 + i17].send(pullDecodedMidiPackets, 0, pullDecodedMidiPackets.length, nanoTime);
                                                                if (com.android.server.usb.UsbDirectMidiDevice.this.mPowerBoostSetter != null && bArr2.length > 1 && (!com.android.server.usb.UsbDirectMidiDevice.this.mIsUniversalMidiDevice || com.android.server.usb.UsbDirectMidiDevice.this.isChannelVoiceMessage(bArr2))) {
                                                                    com.android.server.usb.UsbDirectMidiDevice.this.mPowerBoostSetter.boostPower();
                                                                }
                                                            }
                                                        } else {
                                                            i12 = i16;
                                                        }
                                                        android.util.Log.w(com.android.server.usb.UsbDirectMidiDevice.TAG, "outputReceivers is null");
                                                        i15 = i12;
                                                        break;
                                                    }
                                                    i17 = i14 + 1;
                                                    i16 = i13;
                                                }
                                            }
                                        }
                                    }
                                } catch (java.lang.NullPointerException e) {
                                    android.util.Log.e(com.android.server.usb.UsbDirectMidiDevice.TAG, "input thread: ", e);
                                }
                            } catch (java.io.IOException e2) {
                                android.util.Log.d(com.android.server.usb.UsbDirectMidiDevice.TAG, "reader thread exiting");
                            }
                            usbRequest.close();
                            android.util.Log.d(com.android.server.usb.UsbDirectMidiDevice.TAG, "input thread exit");
                        } catch (java.lang.Throwable th) {
                            usbRequest.close();
                            throw th;
                        }
                    }
                };
                thread.start();
                this.mThreads.add(thread);
                i9 += intValue2;
            }
            i8++;
            i7 = i9;
        }
        int i12 = 0;
        for (int i13 = 0; i13 < this.mOutputUsbEndpoints.size(); i13++) {
            for (int i14 = 0; i14 < this.mOutputUsbEndpoints.get(i13).size(); i14++) {
                final android.hardware.usb.UsbDeviceConnection usbDeviceConnection2 = this.mUsbDeviceConnections.get(i13);
                final android.hardware.usb.UsbEndpoint usbEndpoint2 = this.mOutputUsbEndpoints.get(i13).get(i14);
                final int intValue3 = this.mOutputUsbEndpointCableCounts.get(i13).get(i14).intValue();
                final com.android.internal.midi.MidiEventMultiScheduler midiEventMultiScheduler2 = this.mMidiEventMultiSchedulers.get(i13).get(i14);
                java.lang.Thread thread2 = new java.lang.Thread("UsbDirectMidiDevice output write thread " + i12) { // from class: com.android.server.usb.UsbDirectMidiDevice.3
                    @Override // java.lang.Thread, java.lang.Runnable
                    public void run() {
                        byte[] pullEncodedMidiPackets;
                        try {
                            java.io.ByteArrayOutputStream byteArrayOutputStream = new java.io.ByteArrayOutputStream();
                            com.android.server.usb.UsbMidiPacketConverter usbMidiPacketConverter = new com.android.server.usb.UsbMidiPacketConverter();
                            usbMidiPacketConverter.createEncoders(intValue3);
                            int i15 = 0;
                            boolean z = false;
                            while (true) {
                                if (z) {
                                    break;
                                }
                                if (!midiEventMultiScheduler2.waitNextEvent()) {
                                    android.util.Log.d(com.android.server.usb.UsbDirectMidiDevice.TAG, "output thread closed");
                                    break;
                                }
                                long nanoTime = java.lang.System.nanoTime();
                                for (int i16 = i15; i16 < intValue3; i16++) {
                                    com.android.internal.midi.MidiEventScheduler eventScheduler = midiEventMultiScheduler2.getEventScheduler(i16);
                                    for (com.android.internal.midi.MidiEventScheduler.MidiEvent nextEvent = eventScheduler.getNextEvent(nanoTime); nextEvent != null; nextEvent = (com.android.internal.midi.MidiEventScheduler.MidiEvent) eventScheduler.getNextEvent(nanoTime)) {
                                        com.android.server.usb.UsbDirectMidiDevice.logByteArray("Output before conversion ", nextEvent.data, i15, nextEvent.count);
                                        if (com.android.server.usb.UsbDirectMidiDevice.this.mIsUniversalMidiDevice) {
                                            byte[] swapEndiannessPerWord = com.android.server.usb.UsbDirectMidiDevice.this.swapEndiannessPerWord(nextEvent.data, nextEvent.count);
                                            byteArrayOutputStream.write(swapEndiannessPerWord, i15, swapEndiannessPerWord.length);
                                        } else {
                                            usbMidiPacketConverter.encodeMidiPackets(nextEvent.data, nextEvent.count, i16);
                                        }
                                        eventScheduler.addEventToPool(nextEvent);
                                    }
                                }
                                java.lang.Thread.currentThread();
                                if (java.lang.Thread.interrupted()) {
                                    android.util.Log.d(com.android.server.usb.UsbDirectMidiDevice.TAG, "output thread interrupted");
                                    break;
                                }
                                if (com.android.server.usb.UsbDirectMidiDevice.this.mIsUniversalMidiDevice) {
                                    pullEncodedMidiPackets = byteArrayOutputStream.toByteArray();
                                    byteArrayOutputStream.reset();
                                } else {
                                    pullEncodedMidiPackets = usbMidiPacketConverter.pullEncodedMidiPackets();
                                }
                                com.android.server.usb.UsbDirectMidiDevice.logByteArray("Output after conversion ", pullEncodedMidiPackets, i15, pullEncodedMidiPackets.length);
                                int i17 = i15;
                                while (i17 < pullEncodedMidiPackets.length && !z) {
                                    int min = java.lang.Math.min(usbEndpoint2.getMaxPacketSize(), pullEncodedMidiPackets.length - i17);
                                    int i18 = -1;
                                    int i19 = i15;
                                    while (true) {
                                        if (i18 >= 0 || i19 > 20) {
                                            break;
                                        }
                                        i18 = usbDeviceConnection2.bulkTransfer(usbEndpoint2, pullEncodedMidiPackets, i17, min, 50);
                                        i19++;
                                        java.lang.Thread.currentThread();
                                        if (java.lang.Thread.interrupted()) {
                                            android.util.Log.w(com.android.server.usb.UsbDirectMidiDevice.TAG, "output thread interrupted after send");
                                            z = true;
                                            break;
                                        } else if (i18 < 0) {
                                            android.util.Log.d(com.android.server.usb.UsbDirectMidiDevice.TAG, "retrying packet. retryCount = " + i19 + " result = " + i18);
                                            if (i19 > 20) {
                                                android.util.Log.w(com.android.server.usb.UsbDirectMidiDevice.TAG, "Skipping packet because timeout");
                                            }
                                        }
                                    }
                                    i17 += usbEndpoint2.getMaxPacketSize();
                                    i15 = 0;
                                }
                                i15 = 0;
                            }
                        } catch (java.lang.InterruptedException e) {
                            android.util.Log.w(com.android.server.usb.UsbDirectMidiDevice.TAG, "output thread: ", e);
                        } catch (java.lang.NullPointerException e2) {
                            android.util.Log.e(com.android.server.usb.UsbDirectMidiDevice.TAG, "output thread: ", e2);
                        }
                        android.util.Log.d(com.android.server.usb.UsbDirectMidiDevice.TAG, "output thread exit");
                    }
                };
                thread2.start();
                this.mThreads.add(thread2);
                i12 += intValue3;
            }
        }
        this.mIsOpen = true;
        return true;
    }

    private boolean register(android.content.Context context) {
        java.lang.String str;
        java.lang.String str2;
        this.mContext = context;
        android.media.midi.MidiManager midiManager = (android.media.midi.MidiManager) context.getSystemService(android.media.midi.MidiManager.class);
        if (midiManager == null) {
            android.util.Log.e(TAG, "No MidiManager in UsbDirectMidiDevice.register()");
            return false;
        }
        if (this.mIsUniversalMidiDevice) {
            this.mDefaultMidiProtocol = calculateDefaultMidiProtocol();
        } else {
            this.mDefaultMidiProtocol = -1;
        }
        android.os.Bundle bundle = new android.os.Bundle();
        java.lang.String manufacturerName = this.mUsbDevice.getManufacturerName();
        java.lang.String productName = this.mUsbDevice.getProductName();
        java.lang.String version = this.mUsbDevice.getVersion();
        if (manufacturerName == null || manufacturerName.isEmpty()) {
            str = productName;
        } else if (productName == null || productName.isEmpty()) {
            str = manufacturerName;
        } else {
            str = manufacturerName + " " + productName;
        }
        java.lang.String str3 = str + "#" + this.mUniqueUsbDeviceIdentifier;
        if (this.mIsUniversalMidiDevice) {
            str2 = str3 + " MIDI 2.0";
        } else {
            str2 = str3 + " MIDI 1.0";
        }
        this.mName = str2;
        bundle.putString("name", str2);
        bundle.putString("manufacturer", manufacturerName);
        bundle.putString("product", productName);
        bundle.putString("version", version);
        bundle.putString("serial_number", this.mUsbDevice.getSerialNumber());
        bundle.putParcelable("usb_device", this.mUsbDevice);
        this.mServerAvailable = true;
        this.mServer = midiManager.createDeviceServer(this.mMidiInputPortReceivers, this.mNumInputs, null, null, bundle, 1, this.mDefaultMidiProtocol, this.mCallback);
        if (this.mServer == null) {
            return false;
        }
        return true;
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() throws java.io.IOException {
        synchronized (this.mLock) {
            try {
                if (this.mIsOpen) {
                    closeLocked();
                }
                this.mServerAvailable = false;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        if (this.mServer != null) {
            libcore.io.IoUtils.closeQuietly(this.mServer);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void closeLocked() {
        android.util.Log.d(TAG, "closeLocked()");
        java.util.Iterator<java.lang.Thread> it = this.mThreads.iterator();
        while (it.hasNext()) {
            java.lang.Thread next = it.next();
            if (next != null) {
                next.interrupt();
            }
        }
        java.util.Iterator<java.lang.Thread> it2 = this.mThreads.iterator();
        while (it2.hasNext()) {
            java.lang.Thread next2 = it2.next();
            if (next2 != null) {
                try {
                    next2.join(200L);
                } catch (java.lang.InterruptedException e) {
                    android.util.Log.w(TAG, "thread join interrupted");
                }
            }
        }
        this.mThreads = null;
        for (int i = 0; i < this.mMidiInputPortReceivers.length; i++) {
            this.mMidiInputPortReceivers[i].setReceiver(null);
        }
        for (int i2 = 0; i2 < this.mMidiEventMultiSchedulers.size(); i2++) {
            for (int i3 = 0; i3 < this.mMidiEventMultiSchedulers.get(i2).size(); i3++) {
                this.mMidiEventMultiSchedulers.get(i2).get(i3).close();
            }
        }
        this.mMidiEventMultiSchedulers = null;
        java.util.Iterator<android.hardware.usb.UsbDeviceConnection> it3 = this.mUsbDeviceConnections.iterator();
        while (it3.hasNext()) {
            it3.next().close();
        }
        this.mUsbDeviceConnections = null;
        this.mInputUsbEndpoints = null;
        this.mOutputUsbEndpoints = null;
        this.mInputUsbEndpointCableCounts = null;
        this.mOutputUsbEndpointCableCounts = null;
        this.mIsOpen = false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public byte[] swapEndiannessPerWord(byte[] bArr, int i) {
        int i2 = i & 3;
        if (i2 != 0) {
            android.util.Log.e(TAG, "size not multiple of 4: " + i);
        }
        byte[] bArr2 = new byte[i - i2];
        int i3 = 0;
        while (true) {
            int i4 = i3 + 3;
            if (i4 < i) {
                bArr2[i3] = bArr[i4];
                int i5 = i3 + 1;
                int i6 = i3 + 2;
                bArr2[i5] = bArr[i6];
                bArr2[i6] = bArr[i5];
                bArr2[i4] = bArr[i3];
                i3 += 4;
            } else {
                return bArr2;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void logByteArray(java.lang.String str, byte[] bArr, int i, int i2) {
        java.lang.StringBuilder sb = new java.lang.StringBuilder(str);
        for (int i3 = i; i3 < i + i2; i3++) {
            sb.append(java.lang.String.format("0x%02X", java.lang.Byte.valueOf(bArr[i3])));
            if (i3 != bArr.length - 1) {
                sb.append(", ");
            }
        }
        android.util.Log.d(TAG, sb.toString());
    }

    private boolean updateUsbInterface(android.hardware.usb.UsbInterface usbInterface, android.hardware.usb.UsbDeviceConnection usbDeviceConnection) {
        if (usbInterface == null) {
            android.util.Log.e(TAG, "Usb Interface is null");
            return false;
        }
        if (usbDeviceConnection == null) {
            android.util.Log.e(TAG, "UsbDeviceConnection is null");
            return false;
        }
        if (!usbDeviceConnection.claimInterface(usbInterface, true)) {
            android.util.Log.e(TAG, "Can't claim interface");
            return false;
        }
        if (this.mShouldCallSetInterface) {
            if (!usbDeviceConnection.setInterface(usbInterface)) {
                android.util.Log.w(TAG, "Can't set interface");
            }
        } else {
            android.util.Log.w(TAG, "no alternate interface");
        }
        return true;
    }

    private boolean areEquivalent(android.hardware.usb.UsbInterface usbInterface, android.hardware.usb.UsbInterface usbInterface2) {
        if (usbInterface.getId() != usbInterface2.getId() || usbInterface.getAlternateSetting() != usbInterface2.getAlternateSetting() || usbInterface.getInterfaceClass() != usbInterface2.getInterfaceClass() || usbInterface.getInterfaceSubclass() != usbInterface2.getInterfaceSubclass() || usbInterface.getInterfaceProtocol() != usbInterface2.getInterfaceProtocol() || usbInterface.getEndpointCount() != usbInterface2.getEndpointCount()) {
            return false;
        }
        if (usbInterface.getName() == null) {
            if (usbInterface2.getName() != null) {
                return false;
            }
        } else if (!usbInterface.getName().equals(usbInterface2.getName())) {
            return false;
        }
        for (int i = 0; i < usbInterface.getEndpointCount(); i++) {
            android.hardware.usb.UsbEndpoint endpoint = usbInterface.getEndpoint(i);
            android.hardware.usb.UsbEndpoint endpoint2 = usbInterface2.getEndpoint(i);
            if (endpoint.getAddress() != endpoint2.getAddress() || endpoint.getAttributes() != endpoint2.getAttributes() || endpoint.getMaxPacketSize() != endpoint2.getMaxPacketSize() || endpoint.getInterval() != endpoint2.getInterval()) {
                return false;
            }
        }
        return true;
    }

    public void dump(@android.annotation.NonNull com.android.internal.util.dump.DualDumpOutputStream dualDumpOutputStream, @android.annotation.NonNull java.lang.String str, long j) {
        long start = dualDumpOutputStream.start(str, j);
        dualDumpOutputStream.write("num_inputs", 1120986464257L, this.mNumInputs);
        dualDumpOutputStream.write("num_outputs", 1120986464258L, this.mNumOutputs);
        dualDumpOutputStream.write("is_universal", 1133871366147L, this.mIsUniversalMidiDevice);
        dualDumpOutputStream.write("name", 1138166333444L, this.mName);
        if (this.mIsUniversalMidiDevice) {
            this.mMidiBlockParser.dump(dualDumpOutputStream, "block_parser", 1146756268037L);
        }
        dualDumpOutputStream.end(start);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isChannelVoiceMessage(byte[] bArr) {
        byte b = (byte) ((bArr[0] >> 4) & 15);
        return b == 2 || b == 4;
    }

    private int getNumJacks(com.android.server.usb.descriptors.UsbEndpointDescriptor usbEndpointDescriptor) {
        com.android.server.usb.descriptors.UsbDescriptor classSpecificEndpointDescriptor = usbEndpointDescriptor.getClassSpecificEndpointDescriptor();
        if (classSpecificEndpointDescriptor != null && (classSpecificEndpointDescriptor instanceof com.android.server.usb.descriptors.UsbACMidi10Endpoint)) {
            return ((com.android.server.usb.descriptors.UsbACMidi10Endpoint) classSpecificEndpointDescriptor).getNumJacks();
        }
        return 1;
    }
}
