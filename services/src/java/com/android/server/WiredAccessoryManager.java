package com.android.server;

/* loaded from: classes.dex */
final class WiredAccessoryManager implements com.android.server.input.InputManagerService.WiredAccessoryCallbacks {
    private static final int BIT_HDMI_AUDIO = 16;
    private static final int BIT_HEADSET = 1;
    private static final int BIT_HEADSET_NO_MIC = 2;
    private static final int BIT_LINEOUT = 32;
    private static final int BIT_USB_HEADSET_ANLG = 4;
    private static final int BIT_USB_HEADSET_DGTL = 8;
    private static final java.lang.String INTF_DP = "DP";
    private static final java.lang.String INTF_HDMI = "HDMI";
    private static final boolean LOG = false;
    private static final int MSG_NEW_DEVICE_STATE = 1;
    private static final int MSG_SYSTEM_READY = 2;
    private static final java.lang.String NAME_DP_AUDIO = "soc:qcom,msm-ext-disp";
    private static final java.lang.String NAME_H2W = "h2w";
    private static final java.lang.String NAME_HDMI = "hdmi";
    private static final java.lang.String NAME_HDMI_AUDIO = "hdmi_audio";
    private static final java.lang.String NAME_USB_AUDIO = "usb_audio";
    private static final int SUPPORTED_HEADSETS = 63;
    private final android.media.AudioManager mAudioManager;
    private int mDpCount;
    private final com.android.server.WiredAccessoryManager.WiredAccessoryExtconObserver mExtconObserver;
    private int mHeadsetState;
    private final com.android.server.input.InputManagerService mInputManager;
    private final com.android.server.WiredAccessoryManager.WiredAccessoryObserver mObserver;
    private int mSwitchValues;
    private final boolean mUseDevInputEventForAudioJack;
    private final android.os.PowerManager.WakeLock mWakeLock;
    private static final java.lang.String TAG = com.android.server.WiredAccessoryManager.class.getSimpleName();
    private static final java.lang.String[] DP_AUDIO_CONNS = {"soc:qcom,msm-ext-disp/3/1", "soc:qcom,msm-ext-disp/2/1", "soc:qcom,msm-ext-disp/1/1", "soc:qcom,msm-ext-disp/0/1", "soc:qcom,msm-ext-disp/3/0", "soc:qcom,msm-ext-disp/2/0", "soc:qcom,msm-ext-disp/1/0", "soc:qcom,msm-ext-disp/0/0"};
    private final java.lang.Object mLock = new java.lang.Object();
    private java.lang.String mDetectedIntf = INTF_DP;
    private final android.os.Handler mHandler = new android.os.Handler(android.os.Looper.myLooper(), null, true) { // from class: com.android.server.WiredAccessoryManager.1
        @Override // android.os.Handler
        public void handleMessage(android.os.Message message) {
            switch (message.what) {
                case 1:
                    com.android.server.WiredAccessoryManager.this.setDevicesState(message.arg1, message.arg2, (java.lang.String) message.obj);
                    com.android.server.WiredAccessoryManager.this.mWakeLock.release();
                    break;
                case 2:
                    com.android.server.WiredAccessoryManager.this.onSystemReady();
                    com.android.server.WiredAccessoryManager.this.mWakeLock.release();
                    break;
            }
        }
    };

    public WiredAccessoryManager(android.content.Context context, com.android.server.input.InputManagerService inputManagerService) {
        this.mWakeLock = ((android.os.PowerManager) context.getSystemService("power")).newWakeLock(1, "WiredAccessoryManager");
        this.mWakeLock.setReferenceCounted(false);
        this.mAudioManager = (android.media.AudioManager) context.getSystemService("audio");
        this.mInputManager = inputManagerService;
        this.mUseDevInputEventForAudioJack = context.getResources().getBoolean(android.R.bool.config_useAttentionLight);
        this.mExtconObserver = new com.android.server.WiredAccessoryManager.WiredAccessoryExtconObserver();
        this.mObserver = new com.android.server.WiredAccessoryManager.WiredAccessoryObserver();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onSystemReady() {
        int i;
        if (this.mUseDevInputEventForAudioJack) {
            if (this.mInputManager.getSwitchState(-1, -256, 2) != 1) {
                i = 0;
            } else {
                i = 4;
            }
            if (this.mInputManager.getSwitchState(-1, -256, 4) == 1) {
                i |= 16;
            }
            if (this.mInputManager.getSwitchState(-1, -256, 6) == 1) {
                i |= 64;
            }
            notifyWiredAccessoryChanged(0L, i, 84);
        }
        if (com.android.server.ExtconUEventObserver.extconExists()) {
            this.mExtconObserver.uEventCount();
        }
        this.mObserver.init();
    }

    @Override // com.android.server.input.InputManagerService.WiredAccessoryCallbacks
    public void notifyWiredAccessoryChanged(long j, int i, int i2) {
        synchronized (this.mLock) {
            this.mSwitchValues = (this.mSwitchValues & (~i2)) | i;
            int i3 = 1;
            switch (this.mSwitchValues & 84) {
                case 0:
                    i3 = 0;
                    break;
                case 4:
                    i3 = 2;
                    break;
                case 16:
                    break;
                case 20:
                    break;
                case 64:
                    i3 = 32;
                    break;
                default:
                    i3 = 0;
                    break;
            }
            updateLocked(NAME_H2W, "", i3 | (this.mHeadsetState & (-36)));
        }
    }

    @Override // com.android.server.input.InputManagerService.WiredAccessoryCallbacks
    public void systemReady() {
        synchronized (this.mLock) {
            this.mWakeLock.acquire();
            this.mHandler.sendMessage(this.mHandler.obtainMessage(2, 0, 0, null));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateLocked(java.lang.String str, java.lang.String str2, int i) {
        boolean z;
        android.os.Message obtainMessage;
        int i2 = i & 63;
        int i3 = i & 16;
        int i4 = i2 & 4;
        int i5 = i2 & 8;
        int i6 = i2 & 35;
        boolean z2 = false;
        boolean z3 = (this.mHeadsetState & 16) > 0;
        boolean z4 = this.mDpCount != 0;
        if (this.mHeadsetState == i2 && !str.startsWith(NAME_DP_AUDIO)) {
            android.util.Log.e(TAG, "No state change.");
            return;
        }
        if (i6 != 35) {
            z = true;
        } else {
            android.util.Log.e(TAG, "Invalid combination, unsetting h2w flag");
            z = false;
        }
        if (i4 == 4 && i5 == 8) {
            android.util.Log.e(TAG, "Invalid combination, unsetting usb flag");
        } else {
            z2 = true;
        }
        if (!z && !z2) {
            android.util.Log.e(TAG, "invalid transition, returning ...");
            return;
        }
        if (str.startsWith(NAME_DP_AUDIO)) {
            if (i3 > 0 && this.mDpCount < DP_AUDIO_CONNS.length && z3 == z4) {
                this.mDpCount++;
            } else if (i3 == 0 && this.mDpCount > 0) {
                this.mDpCount--;
            } else {
                android.util.Log.e(TAG, "No state change for DP.");
                return;
            }
        }
        this.mWakeLock.acquire();
        android.util.Log.i(TAG, "MSG_NEW_DEVICE_STATE");
        if (str.startsWith(NAME_DP_AUDIO)) {
            int i7 = this.mHeadsetState;
            if (z3 && i3 != 0) {
                i7 = this.mHeadsetState & (-17);
            }
            obtainMessage = this.mHandler.obtainMessage(1, i2, i7, "soc:qcom,msm-ext-disp/" + str2);
            if (i2 == 0 && this.mDpCount != 0) {
                i2 |= 16;
            }
        } else {
            obtainMessage = this.mHandler.obtainMessage(1, i2, this.mHeadsetState, str + com.android.server.slice.SliceClientPermissions.SliceAuthority.DELIMITER + str2);
        }
        this.mHandler.sendMessage(obtainMessage);
        this.mHeadsetState = i2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setDevicesState(int i, int i2, java.lang.String str) {
        synchronized (this.mLock) {
            int i3 = 1;
            int i4 = 63;
            while (i4 != 0) {
                if ((i3 & i4) != 0) {
                    try {
                        setDeviceStateLocked(i3, i, i2, str);
                        i4 &= ~i3;
                    } catch (java.lang.Throwable th) {
                        throw th;
                    }
                }
                i3 <<= 1;
            }
        }
    }

    private void setDeviceStateLocked(int i, int i2, int i3, java.lang.String str) {
        int i4;
        int i5;
        int i6 = i2 & i;
        if (i6 != (i3 & i)) {
            if (i6 != 0) {
                i4 = 1;
            } else {
                i4 = 0;
            }
            int i7 = 4;
            if (i == 1) {
                i5 = android.hardware.audio.common.V2_0.AudioDevice.IN_WIRED_HEADSET;
            } else if (i == 2) {
                i5 = 0;
                i7 = 8;
            } else if (i == 32) {
                i7 = 131072;
                i5 = 0;
            } else if (i == 4) {
                i7 = 2048;
                i5 = 0;
            } else if (i == 8) {
                i7 = 4096;
                i5 = 0;
            } else if (i == 16) {
                i7 = 1024;
                i5 = 0;
            } else {
                android.util.Slog.e(TAG, "setDeviceState() invalid headset type: " + i);
                return;
            }
            java.lang.String[] split = str.split(com.android.server.slice.SliceClientPermissions.SliceAuthority.DELIMITER);
            if (i7 != 0) {
                this.mAudioManager.setWiredDeviceConnectionState(i7, i4, split.length > 1 ? split[1] : "", split[0]);
            }
            if (i5 != 0) {
                this.mAudioManager.setWiredDeviceConnectionState(i5, i4, split.length > 1 ? split[1] : "", split[0]);
            }
        }
    }

    private java.lang.String switchCodeToString(int i, int i2) {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        if ((i2 & 4) != 0 && (i & 4) != 0) {
            sb.append("SW_HEADPHONE_INSERT ");
        }
        if ((i2 & 16) != 0 && (i & 16) != 0) {
            sb.append("SW_MICROPHONE_INSERT");
        }
        return sb.toString();
    }

    class WiredAccessoryObserver extends android.os.UEventObserver {
        private java.util.List<java.lang.String> mDevPath = new java.util.ArrayList();
        private final java.util.List<com.android.server.WiredAccessoryManager.WiredAccessoryObserver.UEventInfo> mUEventInfo = makeObservedUEventList();

        public WiredAccessoryObserver() {
        }

        void init() {
            int i;
            synchronized (com.android.server.WiredAccessoryManager.this.mLock) {
                try {
                    char[] cArr = new char[1024];
                    for (int i2 = 0; i2 < this.mUEventInfo.size(); i2++) {
                        com.android.server.WiredAccessoryManager.WiredAccessoryObserver.UEventInfo uEventInfo = this.mUEventInfo.get(i2);
                        try {
                            try {
                                java.lang.String switchStatePath = uEventInfo.getSwitchStatePath();
                                java.io.FileReader fileReader = new java.io.FileReader(switchStatePath);
                                int read = fileReader.read(cArr, 0, 1024);
                                fileReader.close();
                                int parseInt = java.lang.Integer.parseInt(new java.lang.String(cArr, 0, read).replaceAll("\\D+", "").trim());
                                if (parseInt > 0) {
                                    int lastIndexOf = switchStatePath.lastIndexOf(".");
                                    if (switchStatePath.substring(lastIndexOf + 1, lastIndexOf + 2).equals("1")) {
                                        com.android.server.WiredAccessoryManager.this.mDetectedIntf = "HDMI";
                                    }
                                    updateStateLocked(uEventInfo.getDevPath(), uEventInfo.getDevName(), parseInt);
                                }
                            } catch (java.lang.Exception e) {
                                android.util.Slog.e(com.android.server.WiredAccessoryManager.TAG, "Error while attempting to determine initial switch state for " + uEventInfo.getDevName(), e);
                            }
                        } catch (java.io.FileNotFoundException e2) {
                            android.util.Slog.w(com.android.server.WiredAccessoryManager.TAG, uEventInfo.getSwitchStatePath() + " not found while attempting to determine initial switch state");
                        }
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
            for (i = 0; i < this.mUEventInfo.size(); i++) {
                com.android.server.WiredAccessoryManager.WiredAccessoryObserver.UEventInfo uEventInfo2 = this.mUEventInfo.get(i);
                java.lang.String devPath = uEventInfo2.getDevPath();
                if (!this.mDevPath.contains(devPath)) {
                    startObserving("DEVPATH=" + uEventInfo2.getDevPath());
                    this.mDevPath.add(devPath);
                }
            }
        }

        private java.util.List<com.android.server.WiredAccessoryManager.WiredAccessoryObserver.UEventInfo> makeObservedUEventList() {
            java.util.ArrayList arrayList = new java.util.ArrayList();
            if (!com.android.server.WiredAccessoryManager.this.mUseDevInputEventForAudioJack) {
                com.android.server.WiredAccessoryManager.WiredAccessoryObserver.UEventInfo uEventInfo = new com.android.server.WiredAccessoryManager.WiredAccessoryObserver.UEventInfo(com.android.server.WiredAccessoryManager.NAME_H2W, 1, 2, 32);
                if (uEventInfo.checkSwitchExists()) {
                    arrayList.add(uEventInfo);
                } else {
                    android.util.Slog.w(com.android.server.WiredAccessoryManager.TAG, "This kernel does not have wired headset support");
                }
            }
            com.android.server.WiredAccessoryManager.WiredAccessoryObserver.UEventInfo uEventInfo2 = new com.android.server.WiredAccessoryManager.WiredAccessoryObserver.UEventInfo(com.android.server.WiredAccessoryManager.NAME_USB_AUDIO, 4, 8, 0);
            if (uEventInfo2.checkSwitchExists()) {
                arrayList.add(uEventInfo2);
            } else {
                android.util.Slog.w(com.android.server.WiredAccessoryManager.TAG, "This kernel does not have usb audio support");
            }
            com.android.server.WiredAccessoryManager.WiredAccessoryObserver.UEventInfo uEventInfo3 = new com.android.server.WiredAccessoryManager.WiredAccessoryObserver.UEventInfo(com.android.server.WiredAccessoryManager.NAME_HDMI_AUDIO, 16, 0, 0);
            if (uEventInfo3.checkSwitchExists()) {
                arrayList.add(uEventInfo3);
            } else {
                com.android.server.WiredAccessoryManager.WiredAccessoryObserver.UEventInfo uEventInfo4 = new com.android.server.WiredAccessoryManager.WiredAccessoryObserver.UEventInfo(com.android.server.WiredAccessoryManager.NAME_HDMI, 16, 0, 0);
                if (uEventInfo4.checkSwitchExists()) {
                    arrayList.add(uEventInfo4);
                } else {
                    android.util.Slog.w(com.android.server.WiredAccessoryManager.TAG, "This kernel does not have HDMI audio support");
                }
            }
            for (java.lang.String str : com.android.server.WiredAccessoryManager.DP_AUDIO_CONNS) {
                com.android.server.WiredAccessoryManager.WiredAccessoryObserver.UEventInfo uEventInfo5 = new com.android.server.WiredAccessoryManager.WiredAccessoryObserver.UEventInfo(str, 16, 0, 0);
                if (uEventInfo5.checkSwitchExists()) {
                    arrayList.add(uEventInfo5);
                } else {
                    android.util.Slog.w(com.android.server.WiredAccessoryManager.TAG, "Conn " + str + " does not have DP audio support");
                }
            }
            return arrayList;
        }

        /* JADX WARN: Can't wrap try/catch for region: R(6:37|38|(2:39|40)|(2:41|42)|44|45) */
        /* JADX WARN: Code restructure failed: missing block: B:59:0x00d0, code lost:
        
            android.util.Slog.e(com.android.server.WiredAccessoryManager.TAG, "could not convert to number");
         */
        /* JADX WARN: Removed duplicated region for block: B:48:0x00ef A[EXC_TOP_SPLITTER, SYNTHETIC] */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public void onUEvent(android.os.UEventObserver.UEvent uEvent) {
            int i;
            int i2;
            java.io.FileReader fileReader;
            java.lang.String str = uEvent.get("DEVPATH");
            java.lang.String str2 = uEvent.get("NAME");
            if (str2 == null) {
                str2 = uEvent.get("SWITCH_NAME");
            }
            try {
                if (str2.startsWith(com.android.server.WiredAccessoryManager.NAME_DP_AUDIO)) {
                    java.lang.String str3 = uEvent.get("STATE");
                    int length = str3.length();
                    int i3 = 0;
                    i = 0;
                    while (i3 < length) {
                        try {
                            int indexOf = str3.indexOf(61, i3);
                            if (indexOf > i3) {
                                java.lang.String substring = str3.substring(i3, indexOf);
                                if ((substring.equals(com.android.server.WiredAccessoryManager.INTF_DP) || substring.equals("HDMI")) && (i = java.lang.Integer.parseInt(str3.substring(indexOf + 1, indexOf + 2))) == 1) {
                                    com.android.server.WiredAccessoryManager.this.mDetectedIntf = substring;
                                    break;
                                }
                            }
                            i3 = indexOf + 3;
                        } catch (java.lang.NumberFormatException e) {
                            android.util.Slog.i(com.android.server.WiredAccessoryManager.TAG, "couldn't get state from event, checking node");
                            int i4 = 0;
                            while (true) {
                                if (i4 >= this.mUEventInfo.size()) {
                                    break;
                                }
                                com.android.server.WiredAccessoryManager.WiredAccessoryObserver.UEventInfo uEventInfo = this.mUEventInfo.get(i4);
                                if (!str2.equals(uEventInfo.getDevName())) {
                                    i4++;
                                } else {
                                    char[] cArr = new char[1024];
                                    try {
                                        try {
                                            fileReader = new java.io.FileReader(uEventInfo.getSwitchStatePath());
                                            i2 = fileReader.read(cArr, 0, 1024);
                                        } catch (java.lang.Exception e2) {
                                            e = e2;
                                            i2 = 0;
                                        }
                                    } catch (java.io.FileNotFoundException e3) {
                                        android.util.Slog.e(com.android.server.WiredAccessoryManager.TAG, "file not found");
                                    }
                                    try {
                                        fileReader.close();
                                    } catch (java.lang.Exception e4) {
                                        e = e4;
                                        android.util.Slog.e(com.android.server.WiredAccessoryManager.TAG, "onUEvent exception", e);
                                        i = java.lang.Integer.parseInt(new java.lang.String(cArr, 0, i2).trim());
                                        synchronized (com.android.server.WiredAccessoryManager.this.mLock) {
                                        }
                                    }
                                    i = java.lang.Integer.parseInt(new java.lang.String(cArr, 0, i2).trim());
                                    break;
                                }
                            }
                            synchronized (com.android.server.WiredAccessoryManager.this.mLock) {
                            }
                        }
                    }
                } else {
                    i = java.lang.Integer.parseInt(uEvent.get("SWITCH_STATE"));
                }
            } catch (java.lang.NumberFormatException e5) {
                i = 0;
            }
            synchronized (com.android.server.WiredAccessoryManager.this.mLock) {
                updateStateLocked(str, str2, i);
            }
        }

        private void updateStateLocked(java.lang.String str, java.lang.String str2, int i) {
            for (int i2 = 0; i2 < this.mUEventInfo.size(); i2++) {
                com.android.server.WiredAccessoryManager.WiredAccessoryObserver.UEventInfo uEventInfo = this.mUEventInfo.get(i2);
                if (str.equals(uEventInfo.getDevPath())) {
                    if (i == 1 && com.android.server.WiredAccessoryManager.this.mDpCount > 0) {
                        uEventInfo.setStreamIndex(com.android.server.WiredAccessoryManager.this.mDpCount);
                    }
                    if (i == 1) {
                        uEventInfo.setCableIndex(1 ^ (com.android.server.WiredAccessoryManager.this.mDetectedIntf.equals(com.android.server.WiredAccessoryManager.INTF_DP) ? 1 : 0));
                    }
                    com.android.server.WiredAccessoryManager.this.updateLocked(str2, uEventInfo.getDevAddress(), uEventInfo.computeNewHeadsetState(com.android.server.WiredAccessoryManager.this.mHeadsetState, i));
                    return;
                }
            }
        }

        private final class UEventInfo {
            static final /* synthetic */ boolean $assertionsDisabled = false;
            private final java.lang.String mDevName;
            private final int mState1Bits;
            private final int mState2Bits;
            private final int mStateNbits;
            private java.lang.String mDevAddress = "controller=0;stream=0";
            private int mDevIndex = -1;
            private int mCableIndex = -1;

            public UEventInfo(java.lang.String str, int i, int i2, int i3) {
                int indexOf;
                this.mDevName = str;
                this.mState1Bits = i;
                this.mState2Bits = i2;
                this.mStateNbits = i3;
                if (this.mDevName.startsWith(com.android.server.WiredAccessoryManager.NAME_DP_AUDIO) && (indexOf = this.mDevName.indexOf(com.android.server.slice.SliceClientPermissions.SliceAuthority.DELIMITER)) != -1) {
                    int i4 = indexOf + 1;
                    int indexOf2 = this.mDevName.indexOf(com.android.server.slice.SliceClientPermissions.SliceAuthority.DELIMITER, i4);
                    int parseInt = java.lang.Integer.parseInt(this.mDevName.substring(i4, indexOf2));
                    int parseInt2 = java.lang.Integer.parseInt(this.mDevName.substring(indexOf2 + 1));
                    checkDevIndex(parseInt);
                    checkCableIndex(parseInt2);
                }
            }

            private void checkDevIndex(int i) {
                char[] cArr = new char[1024];
                int i2 = 0;
                while (true) {
                    java.lang.String format = java.lang.String.format(java.util.Locale.US, "/sys/devices/platform/soc/%s/extcon/extcon%d/name", com.android.server.WiredAccessoryManager.NAME_DP_AUDIO, java.lang.Integer.valueOf(i));
                    java.io.File file = new java.io.File(format);
                    if (!file.exists()) {
                        android.util.Slog.e(com.android.server.WiredAccessoryManager.TAG, "file " + format + " not found");
                        return;
                    }
                    try {
                        java.io.FileReader fileReader = new java.io.FileReader(file);
                        int read = fileReader.read(cArr, 0, 1024);
                        fileReader.close();
                        if (new java.lang.String(cArr, 0, read).trim().startsWith(com.android.server.WiredAccessoryManager.NAME_DP_AUDIO) && i2 == i) {
                            this.mDevIndex = i;
                            return;
                        }
                        i2++;
                    } catch (java.lang.Exception e) {
                        android.util.Slog.e(com.android.server.WiredAccessoryManager.TAG, "checkDevIndex exception", e);
                        return;
                    }
                }
            }

            /* JADX WARN: Code restructure failed: missing block: B:15:0x008f, code lost:
            
                return;
             */
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            private void checkCableIndex(int i) {
                if (this.mDevIndex == -1) {
                    return;
                }
                char[] cArr = new char[1024];
                int i2 = 0;
                while (true) {
                    java.lang.String format = java.lang.String.format(java.util.Locale.US, "/sys/devices/platform/soc/%s/extcon/extcon%d/cable.%d/name", com.android.server.WiredAccessoryManager.NAME_DP_AUDIO, java.lang.Integer.valueOf(this.mDevIndex), java.lang.Integer.valueOf(i2));
                    java.io.File file = new java.io.File(format);
                    if (!file.exists()) {
                        android.util.Slog.e(com.android.server.WiredAccessoryManager.TAG, "file " + format + " not found");
                        return;
                    }
                    try {
                        java.io.FileReader fileReader = new java.io.FileReader(file);
                        int read = fileReader.read(cArr, 0, 1024);
                        fileReader.close();
                        java.lang.String trim = new java.lang.String(cArr, 0, read).trim();
                        if (trim.equals("HDMI") && i2 == i) {
                            this.mCableIndex = i2;
                            break;
                        } else {
                            if (trim.equals(com.android.server.WiredAccessoryManager.INTF_DP) && i2 == i) {
                                this.mCableIndex = i2;
                                break;
                            }
                            i2++;
                        }
                    } catch (java.lang.Exception e) {
                        android.util.Slog.e(com.android.server.WiredAccessoryManager.TAG, "checkCableIndex exception", e);
                        return;
                    }
                }
            }

            public void setStreamIndex(int i) {
                this.mDevAddress = this.mDevAddress.substring(0, this.mDevAddress.indexOf("=", this.mDevAddress.indexOf("=") + 1) + 1) + java.lang.String.valueOf(i);
            }

            public void setCableIndex(int i) {
                int indexOf = this.mDevAddress.indexOf("=");
                this.mDevAddress = this.mDevAddress.substring(0, indexOf + 1) + i + this.mDevAddress.substring(indexOf + 2);
            }

            public java.lang.String getDevName() {
                return this.mDevName;
            }

            public java.lang.String getDevAddress() {
                return this.mDevAddress;
            }

            public java.lang.String getDevPath() {
                if (this.mDevName.startsWith(com.android.server.WiredAccessoryManager.NAME_DP_AUDIO)) {
                    return java.lang.String.format(java.util.Locale.US, "/devices/platform/soc/%s/extcon/extcon%d", com.android.server.WiredAccessoryManager.NAME_DP_AUDIO, java.lang.Integer.valueOf(this.mDevIndex));
                }
                return java.lang.String.format(java.util.Locale.US, "/devices/virtual/switch/%s", this.mDevName);
            }

            public java.lang.String getSwitchStatePath() {
                if (this.mDevName.startsWith(com.android.server.WiredAccessoryManager.NAME_DP_AUDIO)) {
                    return java.lang.String.format(java.util.Locale.US, "/sys/devices/platform/soc/%s/extcon/extcon%d/cable.%d/state", com.android.server.WiredAccessoryManager.NAME_DP_AUDIO, java.lang.Integer.valueOf(this.mDevIndex), java.lang.Integer.valueOf(this.mCableIndex));
                }
                return java.lang.String.format(java.util.Locale.US, "/sys/class/switch/%s/state", this.mDevName);
            }

            public boolean checkSwitchExists() {
                return new java.io.File(getSwitchStatePath()).exists();
            }

            public int computeNewHeadsetState(int i, int i2) {
                int i3;
                int i4 = ~(this.mState1Bits | this.mState2Bits | this.mStateNbits);
                if (i2 == 1) {
                    i3 = this.mState1Bits;
                } else if (i2 == 2) {
                    i3 = this.mState2Bits;
                } else {
                    i3 = i2 == this.mStateNbits ? this.mStateNbits : 0;
                }
                return (i & i4) | i3;
            }
        }
    }

    private class WiredAccessoryExtconObserver extends com.android.server.ExtconStateObserver<android.util.Pair<java.lang.Integer, java.lang.Integer>> {
        private final java.util.List<com.android.server.ExtconUEventObserver.ExtconInfo> mExtconInfos = com.android.server.ExtconUEventObserver.ExtconInfo.getExtconInfoForTypes(new java.lang.String[]{com.android.server.ExtconUEventObserver.ExtconInfo.EXTCON_HEADPHONE, com.android.server.ExtconUEventObserver.ExtconInfo.EXTCON_MICROPHONE, "HDMI", com.android.server.ExtconUEventObserver.ExtconInfo.EXTCON_LINE_OUT});

        WiredAccessoryExtconObserver() {
        }

        /* JADX WARN: Removed duplicated region for block: B:11:0x0069 A[SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:8:0x0062  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        private void init() {
            android.util.Pair<java.lang.Integer, java.lang.Integer> pair;
            for (com.android.server.ExtconUEventObserver.ExtconInfo extconInfo : this.mExtconInfos) {
                try {
                    pair = parseStateFromFile(extconInfo);
                } catch (java.io.FileNotFoundException e) {
                    android.util.Slog.w(com.android.server.WiredAccessoryManager.TAG, extconInfo.getStatePath() + " not found while attempting to determine initial state", e);
                    pair = null;
                    if (pair != null) {
                    }
                    startObserving(extconInfo);
                } catch (java.io.IOException e2) {
                    android.util.Slog.e(com.android.server.WiredAccessoryManager.TAG, "Error reading " + extconInfo.getStatePath() + " while attempting to determine initial state", e2);
                    pair = null;
                    if (pair != null) {
                    }
                    startObserving(extconInfo);
                }
                if (pair != null) {
                    updateState(extconInfo, extconInfo.getName(), pair);
                }
                startObserving(extconInfo);
            }
        }

        public int uEventCount() {
            return this.mExtconInfos.size();
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // com.android.server.ExtconStateObserver
        public android.util.Pair<java.lang.Integer, java.lang.Integer> parseState(com.android.server.ExtconUEventObserver.ExtconInfo extconInfo, java.lang.String str) {
            int[] iArr = {0, 0};
            if (extconInfo.hasCableType(com.android.server.ExtconUEventObserver.ExtconInfo.EXTCON_HEADPHONE)) {
                com.android.server.WiredAccessoryManager.updateBit(iArr, 2, str, com.android.server.ExtconUEventObserver.ExtconInfo.EXTCON_HEADPHONE);
            }
            if (extconInfo.hasCableType(com.android.server.ExtconUEventObserver.ExtconInfo.EXTCON_MICROPHONE)) {
                com.android.server.WiredAccessoryManager.updateBit(iArr, 1, str, com.android.server.ExtconUEventObserver.ExtconInfo.EXTCON_MICROPHONE);
            }
            if (extconInfo.hasCableType("HDMI")) {
                com.android.server.WiredAccessoryManager.updateBit(iArr, 16, str, "HDMI");
            }
            if (extconInfo.hasCableType(com.android.server.ExtconUEventObserver.ExtconInfo.EXTCON_LINE_OUT)) {
                com.android.server.WiredAccessoryManager.updateBit(iArr, 32, str, com.android.server.ExtconUEventObserver.ExtconInfo.EXTCON_LINE_OUT);
            }
            return android.util.Pair.create(java.lang.Integer.valueOf(iArr[0]), java.lang.Integer.valueOf(iArr[1]));
        }

        @Override // com.android.server.ExtconStateObserver
        public void updateState(com.android.server.ExtconUEventObserver.ExtconInfo extconInfo, java.lang.String str, android.util.Pair<java.lang.Integer, java.lang.Integer> pair) {
            synchronized (com.android.server.WiredAccessoryManager.this.mLock) {
                int intValue = ((java.lang.Integer) pair.first).intValue();
                int intValue2 = ((java.lang.Integer) pair.second).intValue();
                com.android.server.WiredAccessoryManager.this.updateLocked(str, "", (intValue2 & intValue) | (com.android.server.WiredAccessoryManager.this.mHeadsetState & (~((~intValue2) & intValue))));
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void updateBit(int[] iArr, int i, java.lang.String str, java.lang.String str2) {
        iArr[0] = iArr[0] | i;
        if (str.contains(str2 + "=1")) {
            iArr[0] = iArr[0] | i;
            iArr[1] = i | iArr[1];
            return;
        }
        if (str.contains(str2 + "=0")) {
            iArr[0] = iArr[0] | i;
            iArr[1] = (~i) & iArr[1];
        }
    }
}
