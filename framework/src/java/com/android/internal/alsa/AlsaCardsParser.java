package com.android.internal.alsa;

/* loaded from: classes4.dex */
public class AlsaCardsParser {
    protected static final boolean DEBUG = false;
    public static final int SCANSTATUS_EMPTY = 2;
    public static final int SCANSTATUS_FAIL = 1;
    public static final int SCANSTATUS_NOTSCANNED = -1;
    public static final int SCANSTATUS_SUCCESS = 0;
    private static final java.lang.String TAG = "AlsaCardsParser";
    private static final java.lang.String kAlsaFolderPath = "/proc/asound";
    private static final java.lang.String kCardsFilePath = "/proc/asound/cards";
    private static final java.lang.String kDeviceAddressPrefix = "/dev/bus/usb/";
    private static com.android.internal.alsa.LineTokenizer mTokenizer = new com.android.internal.alsa.LineTokenizer(" :[]");
    private java.util.ArrayList<com.android.internal.alsa.AlsaCardsParser.AlsaCardRecord> mCardRecords = new java.util.ArrayList<>();
    private int mScanStatus = -1;

    public class AlsaCardRecord {
        private static final java.lang.String TAG = "AlsaCardRecord";
        private static final java.lang.String kUsbCardKeyStr = "at usb-";
        int mCardNum = -1;
        java.lang.String mField1 = "";
        java.lang.String mCardName = "";
        java.lang.String mCardDescription = "";
        private java.lang.String mUsbDeviceAddress = null;

        public AlsaCardRecord() {
        }

        public int getCardNum() {
            return this.mCardNum;
        }

        public java.lang.String getCardName() {
            return this.mCardName;
        }

        public java.lang.String getCardDescription() {
            return this.mCardDescription;
        }

        public void setDeviceAddress(java.lang.String str) {
            this.mUsbDeviceAddress = str;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public boolean parse(java.lang.String str, int i) {
            int nextToken;
            if (i == 0) {
                int nextToken2 = com.android.internal.alsa.AlsaCardsParser.mTokenizer.nextToken(str, 0);
                int nextDelimiter = com.android.internal.alsa.AlsaCardsParser.mTokenizer.nextDelimiter(str, nextToken2);
                try {
                    this.mCardNum = java.lang.Integer.parseInt(str.substring(nextToken2, nextDelimiter));
                    int nextToken3 = com.android.internal.alsa.AlsaCardsParser.mTokenizer.nextToken(str, nextDelimiter);
                    int nextDelimiter2 = com.android.internal.alsa.AlsaCardsParser.mTokenizer.nextDelimiter(str, nextToken3);
                    this.mField1 = str.substring(nextToken3, nextDelimiter2);
                    this.mCardName = str.substring(com.android.internal.alsa.AlsaCardsParser.mTokenizer.nextToken(str, nextDelimiter2));
                } catch (java.lang.NumberFormatException e) {
                    android.util.Slog.e(TAG, "Failed to parse line " + i + " of " + com.android.internal.alsa.AlsaCardsParser.kCardsFilePath + ": " + str.substring(nextToken2, nextDelimiter));
                    return false;
                }
            } else if (i == 1 && (nextToken = com.android.internal.alsa.AlsaCardsParser.mTokenizer.nextToken(str, 0)) != -1) {
                int indexOf = str.indexOf(kUsbCardKeyStr);
                if (indexOf != -1) {
                    this.mCardDescription = str.substring(nextToken, indexOf - 1);
                }
            }
            return true;
        }

        boolean isUsb() {
            return this.mUsbDeviceAddress != null;
        }

        public java.lang.String textFormat() {
            return this.mCardName + " : " + this.mCardDescription + " [addr:" + this.mUsbDeviceAddress + android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_END;
        }

        public void log(int i) {
            android.util.Slog.d(TAG, "" + i + " [" + this.mCardNum + " " + this.mCardName + " : " + this.mCardDescription + " usb:" + isUsb());
        }
    }

    public int scan() {
        this.mCardRecords = new java.util.ArrayList<>();
        try {
            java.io.FileReader fileReader = new java.io.FileReader(new java.io.File(kCardsFilePath));
            java.io.BufferedReader bufferedReader = new java.io.BufferedReader(fileReader);
            while (true) {
                java.lang.String readLine = bufferedReader.readLine();
                if (readLine == null) {
                    break;
                }
                com.android.internal.alsa.AlsaCardsParser.AlsaCardRecord alsaCardRecord = new com.android.internal.alsa.AlsaCardsParser.AlsaCardRecord();
                alsaCardRecord.parse(readLine, 0);
                java.lang.String readLine2 = bufferedReader.readLine();
                if (readLine2 == null) {
                    break;
                }
                alsaCardRecord.parse(readLine2, 1);
                java.io.File file = new java.io.File(("/proc/asound/card" + alsaCardRecord.mCardNum) + "/usbbus");
                if (file.exists()) {
                    java.io.FileReader fileReader2 = new java.io.FileReader(file);
                    java.lang.String readLine3 = new java.io.BufferedReader(fileReader2).readLine();
                    if (readLine3 != null) {
                        alsaCardRecord.setDeviceAddress(kDeviceAddressPrefix + readLine3);
                    }
                    fileReader2.close();
                }
                this.mCardRecords.add(alsaCardRecord);
            }
            fileReader.close();
            if (this.mCardRecords.size() > 0) {
                this.mScanStatus = 0;
            } else {
                this.mScanStatus = 2;
            }
        } catch (java.io.FileNotFoundException e) {
            this.mScanStatus = 1;
        } catch (java.io.IOException e2) {
            this.mScanStatus = 1;
        }
        return this.mScanStatus;
    }

    public int getScanStatus() {
        return this.mScanStatus;
    }

    public com.android.internal.alsa.AlsaCardsParser.AlsaCardRecord findCardNumFor(java.lang.String str) {
        java.util.Iterator<com.android.internal.alsa.AlsaCardsParser.AlsaCardRecord> it = this.mCardRecords.iterator();
        while (it.hasNext()) {
            com.android.internal.alsa.AlsaCardsParser.AlsaCardRecord next = it.next();
            if (next.isUsb() && next.mUsbDeviceAddress.equals(str)) {
                return next;
            }
        }
        return null;
    }

    private void Log(java.lang.String str) {
    }
}
