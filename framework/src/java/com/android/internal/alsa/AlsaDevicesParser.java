package com.android.internal.alsa;

/* loaded from: classes4.dex */
public class AlsaDevicesParser {
    protected static final boolean DEBUG = false;
    public static final int SCANSTATUS_EMPTY = 2;
    public static final int SCANSTATUS_FAIL = 1;
    public static final int SCANSTATUS_NOTSCANNED = -1;
    public static final int SCANSTATUS_SUCCESS = 0;
    private static final java.lang.String TAG = "AlsaDevicesParser";
    private static final java.lang.String kDevicesFilePath = "/proc/asound/devices";
    private static final int kEndIndex_CardNum = 8;
    private static final int kEndIndex_DeviceNum = 11;
    private static final int kIndex_CardDeviceField = 5;
    private static final int kStartIndex_CardNum = 6;
    private static final int kStartIndex_DeviceNum = 9;
    private static final int kStartIndex_Type = 14;
    private static com.android.internal.alsa.LineTokenizer mTokenizer = new com.android.internal.alsa.LineTokenizer(" :[]-");
    private boolean mHasCaptureDevices = false;
    private boolean mHasPlaybackDevices = false;
    private boolean mHasMIDIDevices = false;
    private int mScanStatus = -1;
    private final java.util.ArrayList<com.android.internal.alsa.AlsaDevicesParser.AlsaDeviceRecord> mDeviceRecords = new java.util.ArrayList<>();

    public class AlsaDeviceRecord {
        public static final int kDeviceDir_Capture = 0;
        public static final int kDeviceDir_Playback = 1;
        public static final int kDeviceDir_Unknown = -1;
        public static final int kDeviceType_Audio = 0;
        public static final int kDeviceType_Control = 1;
        public static final int kDeviceType_MIDI = 2;
        public static final int kDeviceType_Unknown = -1;
        int mCardNum = -1;
        int mDeviceNum = -1;
        int mDeviceType = -1;
        int mDeviceDir = -1;

        public AlsaDeviceRecord() {
        }

        public boolean parse(java.lang.String str) {
            int i;
            int i2 = 0;
            int i3 = 0;
            while (true) {
                int nextToken = com.android.internal.alsa.AlsaDevicesParser.mTokenizer.nextToken(str, i2);
                if (nextToken == -1) {
                    return true;
                }
                int nextDelimiter = com.android.internal.alsa.AlsaDevicesParser.mTokenizer.nextDelimiter(str, nextToken);
                if (nextDelimiter != -1) {
                    i = nextDelimiter;
                } else {
                    i = str.length();
                }
                java.lang.String substring = str.substring(nextToken, i);
                switch (i3) {
                    case 1:
                        this.mCardNum = java.lang.Integer.parseInt(substring);
                        if (str.charAt(i) == '-') {
                            break;
                        } else {
                            i3++;
                            break;
                        }
                    case 2:
                        this.mDeviceNum = java.lang.Integer.parseInt(substring);
                        break;
                    case 3:
                        if (!substring.equals("digital")) {
                            if (substring.equals(android.provider.Downloads.Impl.COLUMN_CONTROL)) {
                                this.mDeviceType = 1;
                                break;
                            } else {
                                substring.equals("raw");
                                break;
                            }
                        } else {
                            break;
                        }
                    case 4:
                        if (substring.equals("audio")) {
                            this.mDeviceType = 0;
                            break;
                        } else if (!substring.equals("midi")) {
                            break;
                        } else {
                            this.mDeviceType = 2;
                            com.android.internal.alsa.AlsaDevicesParser.this.mHasMIDIDevices = true;
                            break;
                        }
                    case 5:
                        try {
                            if (substring.equals("capture")) {
                                this.mDeviceDir = 0;
                                com.android.internal.alsa.AlsaDevicesParser.this.mHasCaptureDevices = true;
                                break;
                            } else if (!substring.equals("playback")) {
                                break;
                            } else {
                                this.mDeviceDir = 1;
                                com.android.internal.alsa.AlsaDevicesParser.this.mHasPlaybackDevices = true;
                                break;
                            }
                        } catch (java.lang.NumberFormatException e) {
                            android.util.Slog.e(com.android.internal.alsa.AlsaDevicesParser.TAG, "Failed to parse token " + i3 + " of " + com.android.internal.alsa.AlsaDevicesParser.kDevicesFilePath + " token: " + substring);
                            return false;
                        }
                }
                i3++;
                i2 = i;
            }
        }

        public java.lang.String textFormat() {
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            sb.append(android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_START + this.mCardNum + ":" + this.mDeviceNum + android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_END);
            switch (this.mDeviceType) {
                case 0:
                    sb.append(" Audio");
                    break;
                case 1:
                    sb.append(" Control");
                    break;
                case 2:
                    sb.append(" MIDI");
                    break;
                default:
                    sb.append(" N/A");
                    break;
            }
            switch (this.mDeviceDir) {
                case 0:
                    sb.append(" Capture");
                    break;
                case 1:
                    sb.append(" Playback");
                    break;
                default:
                    sb.append(" N/A");
                    break;
            }
            return sb.toString();
        }
    }

    public int getDefaultDeviceNum(int i) {
        return 0;
    }

    public boolean hasPlaybackDevices(int i) {
        java.util.Iterator<com.android.internal.alsa.AlsaDevicesParser.AlsaDeviceRecord> it = this.mDeviceRecords.iterator();
        while (it.hasNext()) {
            com.android.internal.alsa.AlsaDevicesParser.AlsaDeviceRecord next = it.next();
            if (next.mCardNum == i && next.mDeviceType == 0 && next.mDeviceDir == 1) {
                return true;
            }
        }
        return false;
    }

    public boolean hasCaptureDevices(int i) {
        java.util.Iterator<com.android.internal.alsa.AlsaDevicesParser.AlsaDeviceRecord> it = this.mDeviceRecords.iterator();
        while (it.hasNext()) {
            com.android.internal.alsa.AlsaDevicesParser.AlsaDeviceRecord next = it.next();
            if (next.mCardNum == i && next.mDeviceType == 0 && next.mDeviceDir == 0) {
                return true;
            }
        }
        return false;
    }

    public boolean hasMIDIDevices(int i) {
        java.util.Iterator<com.android.internal.alsa.AlsaDevicesParser.AlsaDeviceRecord> it = this.mDeviceRecords.iterator();
        while (it.hasNext()) {
            com.android.internal.alsa.AlsaDevicesParser.AlsaDeviceRecord next = it.next();
            if (next.mCardNum == i && next.mDeviceType == 2) {
                return true;
            }
        }
        return false;
    }

    private boolean isLineDeviceRecord(java.lang.String str) {
        return str.charAt(5) == '[';
    }

    public int scan() {
        this.mDeviceRecords.clear();
        try {
            java.io.FileReader fileReader = new java.io.FileReader(new java.io.File(kDevicesFilePath));
            java.io.BufferedReader bufferedReader = new java.io.BufferedReader(fileReader);
            while (true) {
                java.lang.String readLine = bufferedReader.readLine();
                if (readLine == null) {
                    break;
                }
                if (isLineDeviceRecord(readLine)) {
                    com.android.internal.alsa.AlsaDevicesParser.AlsaDeviceRecord alsaDeviceRecord = new com.android.internal.alsa.AlsaDevicesParser.AlsaDeviceRecord();
                    alsaDeviceRecord.parse(readLine);
                    android.util.Slog.i(TAG, alsaDeviceRecord.textFormat());
                    this.mDeviceRecords.add(alsaDeviceRecord);
                }
            }
            fileReader.close();
            if (this.mDeviceRecords.size() > 0) {
                this.mScanStatus = 0;
            } else {
                this.mScanStatus = 2;
            }
        } catch (java.io.FileNotFoundException e) {
            e.printStackTrace();
            this.mScanStatus = 1;
        } catch (java.io.IOException e2) {
            e2.printStackTrace();
            this.mScanStatus = 1;
        }
        return this.mScanStatus;
    }

    public int getScanStatus() {
        return this.mScanStatus;
    }

    private void Log(java.lang.String str) {
    }
}
