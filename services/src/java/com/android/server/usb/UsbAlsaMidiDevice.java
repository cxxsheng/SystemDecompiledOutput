package com.android.server.usb;

/* loaded from: classes2.dex */
public final class UsbAlsaMidiDevice implements java.io.Closeable {
    private static final int BUFFER_SIZE = 512;
    private static final java.lang.String TAG = "UsbAlsaMidiDevice";
    private final int mAlsaCard;
    private final int mAlsaDevice;
    private com.android.internal.midi.MidiEventScheduler[] mEventSchedulers;
    private java.io.FileDescriptor[] mFileDescriptors;
    private java.io.FileInputStream[] mInputStreams;
    private boolean mIsOpen;
    private final com.android.server.usb.UsbAlsaMidiDevice.InputReceiverProxy[] mMidiInputPortReceivers;
    private final int mNumInputs;
    private final int mNumOutputs;
    private java.io.FileOutputStream[] mOutputStreams;
    private android.system.StructPollfd[] mPollFDs;
    private com.android.server.usb.PowerBoostSetter mPowerBoostSetter;
    private android.media.midi.MidiDeviceServer mServer;
    private boolean mServerAvailable;
    private final java.lang.Object mLock = new java.lang.Object();
    private int mPipeFD = -1;
    private final android.media.midi.MidiDeviceServer.Callback mCallback = new android.media.midi.MidiDeviceServer.Callback() { // from class: com.android.server.usb.UsbAlsaMidiDevice.1
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
            synchronized (com.android.server.usb.UsbAlsaMidiDevice.this.mLock) {
                try {
                    android.util.Log.d(com.android.server.usb.UsbAlsaMidiDevice.TAG, "numOpenPorts: " + i + " isOpen: " + com.android.server.usb.UsbAlsaMidiDevice.this.mIsOpen + " mServerAvailable: " + com.android.server.usb.UsbAlsaMidiDevice.this.mServerAvailable);
                    if (i > 0 && !com.android.server.usb.UsbAlsaMidiDevice.this.mIsOpen && com.android.server.usb.UsbAlsaMidiDevice.this.mServerAvailable) {
                        com.android.server.usb.UsbAlsaMidiDevice.this.openLocked();
                    } else if (i == 0 && com.android.server.usb.UsbAlsaMidiDevice.this.mIsOpen) {
                        com.android.server.usb.UsbAlsaMidiDevice.this.closeLocked();
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        public void onClose() {
        }
    };

    private native void nativeClose(java.io.FileDescriptor[] fileDescriptorArr);

    private native java.io.FileDescriptor[] nativeOpen(int i, int i2, int i3, int i4);

    private final class InputReceiverProxy extends android.media.midi.MidiReceiver {
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

    public static com.android.server.usb.UsbAlsaMidiDevice create(android.content.Context context, android.os.Bundle bundle, int i, int i2, int i3, int i4) {
        com.android.server.usb.UsbAlsaMidiDevice usbAlsaMidiDevice = new com.android.server.usb.UsbAlsaMidiDevice(i, i2, i3, i4);
        if (!usbAlsaMidiDevice.register(context, bundle)) {
            libcore.io.IoUtils.closeQuietly(usbAlsaMidiDevice);
            android.util.Log.e(TAG, "createDeviceServer failed");
            return null;
        }
        return usbAlsaMidiDevice;
    }

    private UsbAlsaMidiDevice(int i, int i2, int i3, int i4) {
        this.mPowerBoostSetter = null;
        this.mAlsaCard = i;
        this.mAlsaDevice = i2;
        this.mNumInputs = i3;
        this.mNumOutputs = i4;
        this.mMidiInputPortReceivers = new com.android.server.usb.UsbAlsaMidiDevice.InputReceiverProxy[i4];
        for (int i5 = 0; i5 < i4; i5++) {
            this.mMidiInputPortReceivers[i5] = new com.android.server.usb.UsbAlsaMidiDevice.InputReceiverProxy();
        }
        this.mPowerBoostSetter = new com.android.server.usb.PowerBoostSetter();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean openLocked() {
        int i = this.mNumInputs;
        if (i > 0) {
            i++;
        }
        int i2 = this.mNumOutputs;
        java.io.FileDescriptor[] nativeOpen = nativeOpen(this.mAlsaCard, this.mAlsaDevice, i, i2);
        if (nativeOpen == null) {
            android.util.Log.e(TAG, "nativeOpen failed");
            return false;
        }
        this.mFileDescriptors = nativeOpen;
        this.mPollFDs = new android.system.StructPollfd[i];
        this.mInputStreams = new java.io.FileInputStream[i];
        for (int i3 = 0; i3 < i; i3++) {
            java.io.FileDescriptor fileDescriptor = nativeOpen[i3];
            android.system.StructPollfd structPollfd = new android.system.StructPollfd();
            structPollfd.fd = fileDescriptor;
            structPollfd.events = (short) android.system.OsConstants.POLLIN;
            this.mPollFDs[i3] = structPollfd;
            this.mInputStreams[i3] = new java.io.FileInputStream(fileDescriptor);
        }
        this.mOutputStreams = new java.io.FileOutputStream[i2];
        this.mEventSchedulers = new com.android.internal.midi.MidiEventScheduler[i2];
        for (int i4 = 0; i4 < i2; i4++) {
            this.mOutputStreams[i4] = new java.io.FileOutputStream(nativeOpen[i + i4]);
            com.android.internal.midi.MidiEventScheduler midiEventScheduler = new com.android.internal.midi.MidiEventScheduler();
            this.mEventSchedulers[i4] = midiEventScheduler;
            this.mMidiInputPortReceivers[i4].setReceiver(midiEventScheduler.getReceiver());
        }
        final android.media.midi.MidiReceiver[] outputPortReceivers = this.mServer.getOutputPortReceivers();
        if (i > 0) {
            new java.lang.Thread("UsbAlsaMidiDevice input thread") { // from class: com.android.server.usb.UsbAlsaMidiDevice.2
                @Override // java.lang.Thread, java.lang.Runnable
                public void run() {
                    byte[] bArr = new byte[512];
                    while (true) {
                        try {
                            long nanoTime = java.lang.System.nanoTime();
                            synchronized (com.android.server.usb.UsbAlsaMidiDevice.this.mLock) {
                                try {
                                    if (!com.android.server.usb.UsbAlsaMidiDevice.this.mIsOpen) {
                                        break;
                                    }
                                    for (int i5 = 0; i5 < com.android.server.usb.UsbAlsaMidiDevice.this.mPollFDs.length; i5++) {
                                        android.system.StructPollfd structPollfd2 = com.android.server.usb.UsbAlsaMidiDevice.this.mPollFDs[i5];
                                        if ((structPollfd2.revents & (android.system.OsConstants.POLLERR | android.system.OsConstants.POLLHUP)) != 0) {
                                            break;
                                        }
                                        if ((structPollfd2.revents & android.system.OsConstants.POLLIN) != 0) {
                                            structPollfd2.revents = (short) 0;
                                            if (i5 == com.android.server.usb.UsbAlsaMidiDevice.this.mInputStreams.length - 1) {
                                                break;
                                            }
                                            int read = com.android.server.usb.UsbAlsaMidiDevice.this.mInputStreams[i5].read(bArr);
                                            outputPortReceivers[i5].send(bArr, 0, read, nanoTime);
                                            if (com.android.server.usb.UsbAlsaMidiDevice.this.mPowerBoostSetter != null && read > 1) {
                                                com.android.server.usb.UsbAlsaMidiDevice.this.mPowerBoostSetter.boostPower();
                                            }
                                        }
                                    }
                                } finally {
                                }
                            }
                            android.system.Os.poll(com.android.server.usb.UsbAlsaMidiDevice.this.mPollFDs, -1);
                        } catch (android.system.ErrnoException e) {
                            android.util.Log.d(com.android.server.usb.UsbAlsaMidiDevice.TAG, "reader thread exiting");
                        } catch (java.io.IOException e2) {
                            android.util.Log.d(com.android.server.usb.UsbAlsaMidiDevice.TAG, "reader thread exiting");
                        }
                    }
                    android.util.Log.d(com.android.server.usb.UsbAlsaMidiDevice.TAG, "input thread exit");
                }
            }.start();
        }
        for (int i5 = 0; i5 < i2; i5++) {
            final com.android.internal.midi.MidiEventScheduler midiEventScheduler2 = this.mEventSchedulers[i5];
            final java.io.FileOutputStream fileOutputStream = this.mOutputStreams[i5];
            final int i6 = i5;
            new java.lang.Thread("UsbAlsaMidiDevice output thread " + i5) { // from class: com.android.server.usb.UsbAlsaMidiDevice.3
                @Override // java.lang.Thread, java.lang.Runnable
                public void run() {
                    com.android.internal.midi.MidiEventScheduler.MidiEvent waitNextEvent;
                    while (true) {
                        try {
                            waitNextEvent = midiEventScheduler2.waitNextEvent();
                        } catch (java.lang.InterruptedException e) {
                        }
                        if (waitNextEvent == null) {
                            android.util.Log.d(com.android.server.usb.UsbAlsaMidiDevice.TAG, "output thread exit");
                            return;
                        }
                        try {
                            fileOutputStream.write(waitNextEvent.data, 0, waitNextEvent.count);
                        } catch (java.io.IOException e2) {
                            android.util.Log.e(com.android.server.usb.UsbAlsaMidiDevice.TAG, "write failed for port " + i6);
                        }
                        midiEventScheduler2.addEventToPool(waitNextEvent);
                    }
                }
            }.start();
        }
        this.mIsOpen = true;
        return true;
    }

    private boolean register(android.content.Context context, android.os.Bundle bundle) {
        android.media.midi.MidiManager midiManager = (android.media.midi.MidiManager) context.getSystemService(android.media.midi.MidiManager.class);
        if (midiManager == null) {
            android.util.Log.e(TAG, "No MidiManager in UsbAlsaMidiDevice.register()");
            return false;
        }
        this.mServerAvailable = true;
        this.mServer = midiManager.createDeviceServer(this.mMidiInputPortReceivers, this.mNumInputs, null, null, bundle, 1, -1, this.mCallback);
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
        for (int i = 0; i < this.mEventSchedulers.length; i++) {
            this.mMidiInputPortReceivers[i].setReceiver(null);
            this.mEventSchedulers[i].close();
        }
        this.mEventSchedulers = null;
        for (int i2 = 0; i2 < this.mInputStreams.length; i2++) {
            libcore.io.IoUtils.closeQuietly(this.mInputStreams[i2]);
        }
        this.mInputStreams = null;
        for (int i3 = 0; i3 < this.mOutputStreams.length; i3++) {
            libcore.io.IoUtils.closeQuietly(this.mOutputStreams[i3]);
        }
        this.mOutputStreams = null;
        nativeClose(this.mFileDescriptors);
        this.mFileDescriptors = null;
        this.mIsOpen = false;
    }

    public void dump(java.lang.String str, @android.annotation.NonNull com.android.internal.util.dump.DualDumpOutputStream dualDumpOutputStream, @android.annotation.NonNull java.lang.String str2, long j) {
        long start = dualDumpOutputStream.start(str2, j);
        dualDumpOutputStream.write("device_address", 1138166333443L, str);
        dualDumpOutputStream.write("card", 1120986464257L, this.mAlsaCard);
        dualDumpOutputStream.write("device", 1120986464258L, this.mAlsaDevice);
        dualDumpOutputStream.end(start);
    }
}
