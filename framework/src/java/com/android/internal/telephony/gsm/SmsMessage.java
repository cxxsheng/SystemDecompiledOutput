package com.android.internal.telephony.gsm;

/* loaded from: classes5.dex */
public class SmsMessage extends com.android.internal.telephony.SmsMessageBase {
    private static final int INVALID_VALIDITY_PERIOD = -1;
    static final java.lang.String LOG_TAG = "SmsMessage";
    private static final int VALIDITY_PERIOD_FORMAT_ABSOLUTE = 3;
    private static final int VALIDITY_PERIOD_FORMAT_ENHANCED = 1;
    private static final int VALIDITY_PERIOD_FORMAT_NONE = 0;
    private static final int VALIDITY_PERIOD_FORMAT_RELATIVE = 2;
    private static final int VALIDITY_PERIOD_MAX = 635040;
    private static final int VALIDITY_PERIOD_MIN = 5;
    private static final boolean VDBG = false;
    private int mDataCodingScheme;
    private int mMti;
    private int mProtocolIdentifier;
    private int mStatus;
    private com.android.internal.telephony.SmsConstants.MessageClass messageClass;
    private boolean mReplyPathPresent = false;
    private boolean mIsStatusReportMessage = false;
    private int mVoiceMailCount = 0;

    public static class SubmitPdu extends com.android.internal.telephony.SmsMessageBase.SubmitPduBase {
    }

    public static com.android.internal.telephony.gsm.SmsMessage createFromPdu(byte[] bArr) {
        try {
            com.android.internal.telephony.gsm.SmsMessage smsMessage = new com.android.internal.telephony.gsm.SmsMessage();
            smsMessage.parsePdu(bArr);
            return smsMessage;
        } catch (java.lang.OutOfMemoryError e) {
            com.android.telephony.Rlog.e(LOG_TAG, "SMS PDU parsing failed with out of memory: ", e);
            return null;
        } catch (java.lang.RuntimeException e2) {
            com.android.telephony.Rlog.e(LOG_TAG, "SMS PDU parsing failed: ", e2);
            return null;
        }
    }

    public boolean isTypeZero() {
        return this.mProtocolIdentifier == 64;
    }

    public static com.android.internal.telephony.gsm.SmsMessage createFromEfRecord(int i, byte[] bArr) {
        try {
            com.android.internal.telephony.gsm.SmsMessage smsMessage = new com.android.internal.telephony.gsm.SmsMessage();
            smsMessage.mIndexOnIcc = i;
            if ((bArr[0] & 1) == 0) {
                com.android.telephony.Rlog.w(LOG_TAG, "SMS parsing failed: Trying to parse a free record");
                return null;
            }
            smsMessage.mStatusOnIcc = bArr[0] & 7;
            int length = bArr.length - 1;
            byte[] bArr2 = new byte[length];
            java.lang.System.arraycopy(bArr, 1, bArr2, 0, length);
            smsMessage.parsePdu(bArr2);
            return smsMessage;
        } catch (java.lang.RuntimeException e) {
            com.android.telephony.Rlog.e(LOG_TAG, "SMS PDU parsing failed: ", e);
            return null;
        }
    }

    public static int getTPLayerLengthForPDU(java.lang.String str) {
        return ((str.length() / 2) - java.lang.Integer.parseInt(str.substring(0, 2), 16)) - 1;
    }

    public static int getRelativeValidityPeriod(int i) {
        if (i >= 5) {
            if (i <= 720) {
                return (i / 5) - 1;
            }
            if (i <= 1440) {
                return ((i - com.android.internal.logging.nano.MetricsProto.MetricsEvent.ACTION_PERMISSION_DENIED_RECEIVE_WAP_PUSH) / 30) + 143;
            }
            if (i <= 43200) {
                return (i / com.android.internal.logging.nano.MetricsProto.MetricsEvent.ACTION_HUSH_GESTURE) + 166;
            }
            if (i <= VALIDITY_PERIOD_MAX) {
                return (i / 10080) + 192;
            }
        }
        return -1;
    }

    public static com.android.internal.telephony.gsm.SmsMessage.SubmitPdu getSubmitPdu(java.lang.String str, java.lang.String str2, java.lang.String str3, boolean z, byte[] bArr) {
        return getSubmitPdu(str, str2, str3, z, bArr, 0, 0, 0);
    }

    public static com.android.internal.telephony.gsm.SmsMessage.SubmitPdu getSubmitPdu(java.lang.String str, java.lang.String str2, java.lang.String str3, boolean z, byte[] bArr, int i, int i2, int i3) {
        return getSubmitPdu(str, str2, str3, z, bArr, i, i2, i3, -1, 0);
    }

    public static com.android.internal.telephony.gsm.SmsMessage.SubmitPdu getSubmitPdu(java.lang.String str, java.lang.String str2, java.lang.String str3, boolean z, byte[] bArr, int i, int i2, int i3, int i4) {
        return getSubmitPdu(str, str2, str3, z, bArr, i, i2, i3, i4, 0);
    }

    public static com.android.internal.telephony.gsm.SmsMessage.SubmitPdu getSubmitPdu(java.lang.String str, java.lang.String str2, java.lang.String str3, boolean z, byte[] bArr, int i, int i2, int i3, int i4, int i5) {
        byte[] bArr2;
        int i6;
        int i7;
        int i8;
        byte b;
        byte b2;
        byte[] encodeUCS2;
        byte[] byteArray;
        if (str3 == null || str2 == null) {
            return null;
        }
        if (i != 0) {
            bArr2 = bArr;
            i6 = i;
            i7 = i2;
            i8 = i3;
        } else {
            com.android.internal.telephony.GsmAlphabet.TextEncodingDetails calculateLength = calculateLength(str3, false);
            int i9 = calculateLength.codeUnitSize;
            int i10 = calculateLength.languageTable;
            int i11 = calculateLength.languageShiftTable;
            if (i9 == 1 && (i10 != 0 || i11 != 0)) {
                if (bArr != null) {
                    com.android.internal.telephony.SmsHeader fromByteArray = com.android.internal.telephony.SmsHeader.fromByteArray(bArr);
                    if (fromByteArray.languageTable == i10 && fromByteArray.languageShiftTable == i11) {
                        byteArray = bArr;
                    } else {
                        com.android.telephony.Rlog.w(LOG_TAG, "Updating language table in SMS header: " + fromByteArray.languageTable + " -> " + i10 + ", " + fromByteArray.languageShiftTable + " -> " + i11);
                        fromByteArray.languageTable = i10;
                        fromByteArray.languageShiftTable = i11;
                        byteArray = com.android.internal.telephony.SmsHeader.toByteArray(fromByteArray);
                    }
                    i8 = i11;
                    i6 = i9;
                    i7 = i10;
                    bArr2 = byteArray;
                } else {
                    com.android.internal.telephony.SmsHeader smsHeader = new com.android.internal.telephony.SmsHeader();
                    smsHeader.languageTable = i10;
                    smsHeader.languageShiftTable = i11;
                    i8 = i11;
                    i6 = i9;
                    i7 = i10;
                    bArr2 = com.android.internal.telephony.SmsHeader.toByteArray(smsHeader);
                }
            } else {
                bArr2 = bArr;
                i8 = i11;
                i6 = i9;
                i7 = i10;
            }
        }
        com.android.internal.telephony.gsm.SmsMessage.SubmitPdu submitPdu = new com.android.internal.telephony.gsm.SmsMessage.SubmitPdu();
        int relativeValidityPeriod = getRelativeValidityPeriod(i4);
        if (bArr2 == null) {
            b = 1;
        } else {
            b = (byte) 65;
        }
        if (relativeValidityPeriod == -1) {
            b2 = b;
        } else {
            b2 = (byte) (b | 16);
        }
        java.io.ByteArrayOutputStream submitPduHead = getSubmitPduHead(str, str2, b2, z, submitPdu, i5);
        if (submitPduHead == null) {
            return submitPdu;
        }
        try {
            if (i6 == 1) {
                encodeUCS2 = com.android.internal.telephony.GsmAlphabet.stringToGsm7BitPackedWithHeader(str3, bArr2, i7, i8);
            } else {
                try {
                    encodeUCS2 = encodeUCS2(str3, bArr2);
                } catch (java.io.UnsupportedEncodingException e) {
                    com.android.telephony.Rlog.e(LOG_TAG, "Implausible UnsupportedEncodingException ", e);
                    return null;
                }
            }
        } catch (com.android.internal.telephony.EncodeException e2) {
            if (e2.getError() == 1) {
                com.android.telephony.Rlog.e(LOG_TAG, "Exceed size limitation EncodeException", e2);
                return null;
            }
            try {
                encodeUCS2 = encodeUCS2(str3, bArr2);
                i6 = 3;
            } catch (com.android.internal.telephony.EncodeException e3) {
                com.android.telephony.Rlog.e(LOG_TAG, "Exceed size limitation EncodeException", e3);
                return null;
            } catch (java.io.UnsupportedEncodingException e4) {
                com.android.telephony.Rlog.e(LOG_TAG, "Implausible UnsupportedEncodingException ", e4);
                return null;
            }
        }
        if (i6 == 1) {
            if ((encodeUCS2[0] & 255) > 160) {
                com.android.telephony.Rlog.e(LOG_TAG, "Message too long (" + (encodeUCS2[0] & 255) + " septets)");
                return null;
            }
            submitPduHead.write(0);
        } else {
            if ((encodeUCS2[0] & 255) > 140) {
                com.android.telephony.Rlog.e(LOG_TAG, "Message too long (" + (encodeUCS2[0] & 255) + " bytes)");
                return null;
            }
            submitPduHead.write(8);
        }
        if (relativeValidityPeriod != -1) {
            submitPduHead.write(relativeValidityPeriod);
        }
        submitPduHead.write(encodeUCS2, 0, encodeUCS2.length);
        submitPdu.encodedMessage = submitPduHead.toByteArray();
        return submitPdu;
    }

    private static byte[] encodeUCS2(java.lang.String str, byte[] bArr) throws java.io.UnsupportedEncodingException, com.android.internal.telephony.EncodeException {
        byte[] bytes = str.getBytes("utf-16be");
        if (bArr != null) {
            byte[] bArr2 = new byte[bArr.length + bytes.length + 1];
            bArr2[0] = (byte) bArr.length;
            java.lang.System.arraycopy(bArr, 0, bArr2, 1, bArr.length);
            java.lang.System.arraycopy(bytes, 0, bArr2, bArr.length + 1, bytes.length);
            bytes = bArr2;
        }
        if (bytes.length > 255) {
            throw new com.android.internal.telephony.EncodeException("Payload cannot exceed 255 bytes", 1);
        }
        byte[] bArr3 = new byte[bytes.length + 1];
        bArr3[0] = (byte) (255 & bytes.length);
        java.lang.System.arraycopy(bytes, 0, bArr3, 1, bytes.length);
        return bArr3;
    }

    public static com.android.internal.telephony.gsm.SmsMessage.SubmitPdu getSubmitPdu(java.lang.String str, java.lang.String str2, java.lang.String str3, boolean z) {
        return getSubmitPdu(str, str2, str3, z, (byte[]) null);
    }

    public static com.android.internal.telephony.gsm.SmsMessage.SubmitPdu getSubmitPdu(java.lang.String str, java.lang.String str2, java.lang.String str3, boolean z, int i) {
        return getSubmitPdu(str, str2, str3, z, null, 0, 0, 0, i, 0);
    }

    public static com.android.internal.telephony.gsm.SmsMessage.SubmitPdu getSubmitPdu(java.lang.String str, java.lang.String str2, int i, byte[] bArr, boolean z, int i2) {
        com.android.internal.telephony.SmsHeader.PortAddrs portAddrs = new com.android.internal.telephony.SmsHeader.PortAddrs();
        portAddrs.destPort = i;
        portAddrs.origPort = 0;
        portAddrs.areEightBits = false;
        com.android.internal.telephony.SmsHeader smsHeader = new com.android.internal.telephony.SmsHeader();
        smsHeader.portAddrs = portAddrs;
        byte[] byteArray = com.android.internal.telephony.SmsHeader.toByteArray(smsHeader);
        if (bArr.length + byteArray.length + 1 > 140) {
            com.android.telephony.Rlog.e(LOG_TAG, "SMS data message may only contain " + ((140 - byteArray.length) - 1) + " bytes");
            return null;
        }
        com.android.internal.telephony.gsm.SmsMessage.SubmitPdu submitPdu = new com.android.internal.telephony.gsm.SmsMessage.SubmitPdu();
        java.io.ByteArrayOutputStream submitPduHead = getSubmitPduHead(str, str2, (byte) 65, z, submitPdu, i2);
        if (submitPduHead == null) {
            return submitPdu;
        }
        submitPduHead.write(4);
        submitPduHead.write(bArr.length + byteArray.length + 1);
        submitPduHead.write(byteArray.length);
        submitPduHead.write(byteArray, 0, byteArray.length);
        submitPduHead.write(bArr, 0, bArr.length);
        submitPdu.encodedMessage = submitPduHead.toByteArray();
        return submitPdu;
    }

    public static com.android.internal.telephony.gsm.SmsMessage.SubmitPdu getSubmitPdu(java.lang.String str, java.lang.String str2, int i, byte[] bArr, boolean z) {
        return getSubmitPdu(str, str2, i, bArr, z, 0);
    }

    private static java.io.ByteArrayOutputStream getSubmitPduHead(java.lang.String str, java.lang.String str2, byte b, boolean z, com.android.internal.telephony.gsm.SmsMessage.SubmitPdu submitPdu) {
        return getSubmitPduHead(str, str2, b, z, submitPdu, 0);
    }

    private static java.io.ByteArrayOutputStream getSubmitPduHead(java.lang.String str, java.lang.String str2, byte b, boolean z, com.android.internal.telephony.gsm.SmsMessage.SubmitPdu submitPdu, int i) {
        java.io.ByteArrayOutputStream byteArrayOutputStream = new java.io.ByteArrayOutputStream(180);
        if (str == null) {
            submitPdu.encodedScAddress = null;
        } else {
            submitPdu.encodedScAddress = android.telephony.PhoneNumberUtils.networkPortionToCalledPartyBCDWithLength(str);
        }
        if (z) {
            b = (byte) (b | com.android.net.module.util.NetworkStackConstants.TCPHDR_URG);
        }
        byteArrayOutputStream.write(b);
        byteArrayOutputStream.write(i);
        byte[] networkPortionToCalledPartyBCD = android.telephony.PhoneNumberUtils.networkPortionToCalledPartyBCD(str2);
        if (networkPortionToCalledPartyBCD == null) {
            return null;
        }
        byteArrayOutputStream.write(((networkPortionToCalledPartyBCD.length - 1) * 2) - ((networkPortionToCalledPartyBCD[networkPortionToCalledPartyBCD.length - 1] & 240) != 240 ? 0 : 1));
        byteArrayOutputStream.write(networkPortionToCalledPartyBCD, 0, networkPortionToCalledPartyBCD.length);
        byteArrayOutputStream.write(0);
        return byteArrayOutputStream;
    }

    /* JADX WARN: Type inference failed for: r7v4, types: [java.time.LocalDateTime] */
    public static com.android.internal.telephony.gsm.SmsMessage.SubmitPdu getDeliverPdu(java.lang.String str, java.lang.String str2, java.lang.String str3, long j) {
        byte[] bArr;
        int i;
        byte[] encodeUCS2;
        boolean z;
        if (str2 == null || str3 == null) {
            return null;
        }
        com.android.internal.telephony.GsmAlphabet.TextEncodingDetails calculateLength = calculateLength(str3, false);
        int i2 = calculateLength.codeUnitSize;
        int i3 = calculateLength.languageTable;
        int i4 = calculateLength.languageShiftTable;
        if (i2 == 1 && (i3 != 0 || i4 != 0)) {
            com.android.internal.telephony.SmsHeader smsHeader = new com.android.internal.telephony.SmsHeader();
            smsHeader.languageTable = i3;
            smsHeader.languageShiftTable = i4;
            bArr = com.android.internal.telephony.SmsHeader.toByteArray(smsHeader);
        } else {
            bArr = null;
        }
        com.android.internal.telephony.gsm.SmsMessage.SubmitPdu submitPdu = new com.android.internal.telephony.gsm.SmsMessage.SubmitPdu();
        java.io.ByteArrayOutputStream byteArrayOutputStream = new java.io.ByteArrayOutputStream(180);
        if (str == null) {
            submitPdu.encodedScAddress = null;
        } else {
            submitPdu.encodedScAddress = android.telephony.PhoneNumberUtils.networkPortionToCalledPartyBCDWithLength(str);
        }
        byteArrayOutputStream.write(0);
        byte[] networkPortionToCalledPartyBCD = android.telephony.PhoneNumberUtils.networkPortionToCalledPartyBCD(str2);
        if (networkPortionToCalledPartyBCD == null) {
            return null;
        }
        int length = (networkPortionToCalledPartyBCD.length - 1) * 2;
        if ((networkPortionToCalledPartyBCD[networkPortionToCalledPartyBCD.length - 1] & 240) != 240) {
            i = 0;
        } else {
            i = 1;
        }
        byteArrayOutputStream.write(length - i);
        byteArrayOutputStream.write(networkPortionToCalledPartyBCD, 0, networkPortionToCalledPartyBCD.length);
        byteArrayOutputStream.write(0);
        try {
            if (i2 == 1) {
                encodeUCS2 = com.android.internal.telephony.GsmAlphabet.stringToGsm7BitPackedWithHeader(str3, bArr, i3, i4);
            } else {
                try {
                    encodeUCS2 = encodeUCS2(str3, bArr);
                } catch (java.io.UnsupportedEncodingException e) {
                    com.android.telephony.Rlog.e(LOG_TAG, "Implausible UnsupportedEncodingException ", e);
                    return null;
                }
            }
        } catch (com.android.internal.telephony.EncodeException e2) {
            if (e2.getError() == 1) {
                com.android.telephony.Rlog.e(LOG_TAG, "Exceed size limitation EncodeException", e2);
                return null;
            }
            try {
                encodeUCS2 = encodeUCS2(str3, bArr);
                i2 = 3;
            } catch (com.android.internal.telephony.EncodeException e3) {
                com.android.telephony.Rlog.e(LOG_TAG, "Exceed size limitation EncodeException", e3);
                return null;
            } catch (java.io.UnsupportedEncodingException e4) {
                com.android.telephony.Rlog.e(LOG_TAG, "Implausible UnsupportedEncodingException ", e4);
                return null;
            }
        }
        if (i2 == 1) {
            if ((encodeUCS2[0] & 255) > 160) {
                com.android.telephony.Rlog.e(LOG_TAG, "Message too long (" + (encodeUCS2[0] & 255) + " septets)");
                return null;
            }
            byteArrayOutputStream.write(0);
        } else {
            if ((encodeUCS2[0] & 255) > 140) {
                com.android.telephony.Rlog.e(LOG_TAG, "Message too long (" + (encodeUCS2[0] & 255) + " bytes)");
                return null;
            }
            byteArrayOutputStream.write(8);
        }
        byte[] bArr2 = new byte[7];
        java.time.ZonedDateTime atZone = java.time.Instant.ofEpochMilli(j).atZone(java.time.ZoneId.systemDefault());
        ?? localDateTime = atZone.toLocalDateTime();
        int totalSeconds = (atZone.getOffset().getTotalSeconds() / 60) / 15;
        if (totalSeconds >= 0) {
            z = false;
        } else {
            z = true;
        }
        if (z) {
            totalSeconds = -totalSeconds;
        }
        int year = localDateTime.getYear();
        int monthValue = localDateTime.getMonthValue();
        int dayOfMonth = localDateTime.getDayOfMonth();
        int hour = localDateTime.getHour();
        int minute = localDateTime.getMinute();
        int second = localDateTime.getSecond();
        int i5 = year > 2000 ? year - 2000 : year - 1900;
        bArr2[0] = (byte) ((((i5 % 10) & 15) << 4) | ((i5 / 10) & 15));
        bArr2[1] = (byte) ((((monthValue % 10) & 15) << 4) | ((monthValue / 10) & 15));
        bArr2[2] = (byte) ((((dayOfMonth % 10) & 15) << 4) | ((dayOfMonth / 10) & 15));
        bArr2[3] = (byte) ((((hour % 10) & 15) << 4) | ((hour / 10) & 15));
        bArr2[4] = (byte) ((((minute % 10) & 15) << 4) | ((minute / 10) & 15));
        bArr2[5] = (byte) ((((second % 10) & 15) << 4) | ((second / 10) & 15));
        bArr2[6] = (byte) ((((totalSeconds % 10) & 15) << 4) | ((totalSeconds / 10) & 15));
        if (z) {
            bArr2[0] = (byte) (bArr2[0] | 8);
        }
        byteArrayOutputStream.write(bArr2, 0, 7);
        byteArrayOutputStream.write(encodeUCS2, 0, encodeUCS2.length);
        submitPdu.encodedMessage = byteArrayOutputStream.toByteArray();
        return submitPdu;
    }

    private static class PduParser {
        byte[] mPdu;
        byte[] mUserData;
        com.android.internal.telephony.SmsHeader mUserDataHeader;
        int mCur = 0;
        int mUserDataSeptetPadding = 0;

        PduParser(byte[] bArr) {
            this.mPdu = bArr;
        }

        java.lang.String getSCAddress() {
            int i = getByte();
            java.lang.String str = null;
            if (i != 0) {
                try {
                    str = android.telephony.PhoneNumberUtils.calledPartyBCDToString(this.mPdu, this.mCur, i, 2);
                } catch (java.lang.RuntimeException e) {
                    com.android.telephony.Rlog.d(com.android.internal.telephony.gsm.SmsMessage.LOG_TAG, "invalid SC address: ", e);
                }
            }
            this.mCur += i;
            return str;
        }

        int getByte() {
            byte[] bArr = this.mPdu;
            int i = this.mCur;
            this.mCur = i + 1;
            return bArr[i] & 255;
        }

        com.android.internal.telephony.gsm.GsmSmsAddress getAddress() {
            int i = (((this.mPdu[this.mCur] & 255) + 1) / 2) + 2;
            try {
                com.android.internal.telephony.gsm.GsmSmsAddress gsmSmsAddress = new com.android.internal.telephony.gsm.GsmSmsAddress(this.mPdu, this.mCur, i);
                this.mCur += i;
                return gsmSmsAddress;
            } catch (java.text.ParseException e) {
                throw new java.lang.RuntimeException(e.getMessage());
            }
        }

        long getSCTimestampMillis() {
            byte[] bArr = this.mPdu;
            int i = this.mCur;
            this.mCur = i + 1;
            int gsmBcdByteToInt = com.android.internal.telephony.uicc.IccUtils.gsmBcdByteToInt(bArr[i]);
            byte[] bArr2 = this.mPdu;
            int i2 = this.mCur;
            this.mCur = i2 + 1;
            int gsmBcdByteToInt2 = com.android.internal.telephony.uicc.IccUtils.gsmBcdByteToInt(bArr2[i2]);
            byte[] bArr3 = this.mPdu;
            int i3 = this.mCur;
            this.mCur = i3 + 1;
            int gsmBcdByteToInt3 = com.android.internal.telephony.uicc.IccUtils.gsmBcdByteToInt(bArr3[i3]);
            byte[] bArr4 = this.mPdu;
            int i4 = this.mCur;
            this.mCur = i4 + 1;
            int gsmBcdByteToInt4 = com.android.internal.telephony.uicc.IccUtils.gsmBcdByteToInt(bArr4[i4]);
            byte[] bArr5 = this.mPdu;
            int i5 = this.mCur;
            this.mCur = i5 + 1;
            int gsmBcdByteToInt5 = com.android.internal.telephony.uicc.IccUtils.gsmBcdByteToInt(bArr5[i5]);
            byte[] bArr6 = this.mPdu;
            int i6 = this.mCur;
            this.mCur = i6 + 1;
            int gsmBcdByteToInt6 = com.android.internal.telephony.uicc.IccUtils.gsmBcdByteToInt(bArr6[i6]);
            byte[] bArr7 = this.mPdu;
            int i7 = this.mCur;
            this.mCur = i7 + 1;
            byte b = bArr7[i7];
            int gsmBcdByteToInt7 = com.android.internal.telephony.uicc.IccUtils.gsmBcdByteToInt((byte) (b & (-9)));
            if ((b & 8) != 0) {
                gsmBcdByteToInt7 = -gsmBcdByteToInt7;
            }
            try {
                return (java.time.LocalDateTime.of(gsmBcdByteToInt >= 90 ? gsmBcdByteToInt + 1900 : gsmBcdByteToInt + 2000, gsmBcdByteToInt2, gsmBcdByteToInt3, gsmBcdByteToInt4, gsmBcdByteToInt5, gsmBcdByteToInt6).toEpochSecond(java.time.ZoneOffset.UTC) - ((gsmBcdByteToInt7 * 15) * 60)) * 1000;
            } catch (java.time.DateTimeException e) {
                com.android.telephony.Rlog.e(com.android.internal.telephony.gsm.SmsMessage.LOG_TAG, "Invalid timestamp", e);
                return 0L;
            }
        }

        int constructUserData(boolean z, boolean z2) {
            int i;
            int i2;
            int i3;
            int i4 = this.mCur;
            int i5 = i4 + 1;
            int i6 = this.mPdu[i4] & 255;
            if (!z) {
                i = 0;
                i2 = 0;
            } else {
                int i7 = i5 + 1;
                int i8 = this.mPdu[i5] & 255;
                byte[] bArr = new byte[i8];
                java.lang.System.arraycopy(this.mPdu, i7, bArr, 0, i8);
                this.mUserDataHeader = com.android.internal.telephony.SmsHeader.fromByteArray(bArr);
                int i9 = i7 + i8;
                int i10 = (i8 + 1) * 8;
                i2 = (i10 / 7) + (i10 % 7 > 0 ? 1 : 0);
                this.mUserDataSeptetPadding = (i2 * 7) - i10;
                i = i8;
                i5 = i9;
            }
            if (z2) {
                i3 = this.mPdu.length - i5;
            } else {
                i3 = i6 - (z ? i + 1 : 0);
                if (i3 < 0) {
                    i3 = 0;
                }
            }
            this.mUserData = new byte[i3];
            java.lang.System.arraycopy(this.mPdu, i5, this.mUserData, 0, this.mUserData.length);
            this.mCur = i5;
            if (z2) {
                int i11 = i6 - i2;
                if (i11 < 0) {
                    return 0;
                }
                return i11;
            }
            return this.mUserData.length;
        }

        byte[] getUserData() {
            return this.mUserData;
        }

        com.android.internal.telephony.SmsHeader getUserDataHeader() {
            return this.mUserDataHeader;
        }

        java.lang.String getUserDataGSM7Bit(int i, int i2, int i3) {
            java.lang.String gsm7BitPackedToString = com.android.internal.telephony.GsmAlphabet.gsm7BitPackedToString(this.mPdu, this.mCur, i, this.mUserDataSeptetPadding, i2, i3);
            this.mCur += (i * 7) / 8;
            return gsm7BitPackedToString;
        }

        java.lang.String getUserDataGSM8bit(int i) {
            java.lang.String gsm8BitUnpackedToString = com.android.internal.telephony.GsmAlphabet.gsm8BitUnpackedToString(this.mPdu, this.mCur, i);
            this.mCur += i;
            return gsm8BitUnpackedToString;
        }

        java.lang.String getUserDataUCS2(int i) {
            java.lang.String str;
            try {
                str = new java.lang.String(this.mPdu, this.mCur, i, com.google.android.mms.pdu.CharacterSets.MIMENAME_UTF_16);
            } catch (java.io.UnsupportedEncodingException e) {
                com.android.telephony.Rlog.e(com.android.internal.telephony.gsm.SmsMessage.LOG_TAG, "implausible UnsupportedEncodingException", e);
                str = "";
            }
            this.mCur += i;
            return str;
        }

        java.lang.String getUserDataKSC5601(int i) {
            java.lang.String str;
            try {
                str = new java.lang.String(this.mPdu, this.mCur, i, "KSC5601");
            } catch (java.io.UnsupportedEncodingException e) {
                com.android.telephony.Rlog.e(com.android.internal.telephony.gsm.SmsMessage.LOG_TAG, "implausible UnsupportedEncodingException", e);
                str = "";
            }
            this.mCur += i;
            return str;
        }

        boolean moreDataPresent() {
            return this.mPdu.length > this.mCur;
        }
    }

    public static com.android.internal.telephony.GsmAlphabet.TextEncodingDetails calculateLength(java.lang.CharSequence charSequence, boolean z) {
        java.lang.String str;
        if (!android.content.res.Resources.getSystem().getBoolean(com.android.internal.R.bool.config_sms_force_7bit_encoding)) {
            str = null;
        } else {
            str = com.android.internal.telephony.Sms7BitEncodingTranslator.translate(charSequence, false);
        }
        if (!android.text.TextUtils.isEmpty(str)) {
            charSequence = str;
        }
        com.android.internal.telephony.GsmAlphabet.TextEncodingDetails countGsmSeptets = com.android.internal.telephony.GsmAlphabet.countGsmSeptets(charSequence, z);
        if (countGsmSeptets == null) {
            return com.android.internal.telephony.SmsMessageBase.calcUnicodeEncodingDetails(charSequence);
        }
        return countGsmSeptets;
    }

    @Override // com.android.internal.telephony.SmsMessageBase
    public int getProtocolIdentifier() {
        return this.mProtocolIdentifier;
    }

    int getDataCodingScheme() {
        return this.mDataCodingScheme;
    }

    @Override // com.android.internal.telephony.SmsMessageBase
    public boolean isReplace() {
        return (this.mProtocolIdentifier & 192) == 64 && (this.mProtocolIdentifier & 63) > 0 && (this.mProtocolIdentifier & 63) < 8;
    }

    @Override // com.android.internal.telephony.SmsMessageBase
    public boolean isCphsMwiMessage() {
        return ((com.android.internal.telephony.gsm.GsmSmsAddress) this.mOriginatingAddress).isCphsVoiceMessageClear() || ((com.android.internal.telephony.gsm.GsmSmsAddress) this.mOriginatingAddress).isCphsVoiceMessageSet();
    }

    @Override // com.android.internal.telephony.SmsMessageBase
    public boolean isMWIClearMessage() {
        if (!this.mIsMwi || this.mMwiSense) {
            return this.mOriginatingAddress != null && ((com.android.internal.telephony.gsm.GsmSmsAddress) this.mOriginatingAddress).isCphsVoiceMessageClear();
        }
        return true;
    }

    @Override // com.android.internal.telephony.SmsMessageBase
    public boolean isMWISetMessage() {
        if (this.mIsMwi && this.mMwiSense) {
            return true;
        }
        return this.mOriginatingAddress != null && ((com.android.internal.telephony.gsm.GsmSmsAddress) this.mOriginatingAddress).isCphsVoiceMessageSet();
    }

    @Override // com.android.internal.telephony.SmsMessageBase
    public boolean isMwiDontStore() {
        if (this.mIsMwi && this.mMwiDontStore) {
            return true;
        }
        return isCphsMwiMessage() && " ".equals(getMessageBody());
    }

    @Override // com.android.internal.telephony.SmsMessageBase
    public int getStatus() {
        return this.mStatus;
    }

    @Override // com.android.internal.telephony.SmsMessageBase
    public boolean isStatusReportMessage() {
        return this.mIsStatusReportMessage;
    }

    @Override // com.android.internal.telephony.SmsMessageBase
    public boolean isReplyPathPresent() {
        return this.mReplyPathPresent;
    }

    private void parsePdu(byte[] bArr) {
        this.mPdu = bArr;
        com.android.internal.telephony.gsm.SmsMessage.PduParser pduParser = new com.android.internal.telephony.gsm.SmsMessage.PduParser(bArr);
        this.mScAddress = pduParser.getSCAddress();
        java.lang.String str = this.mScAddress;
        int i = pduParser.getByte();
        this.mMti = i & 3;
        switch (this.mMti) {
            case 0:
            case 3:
                parseSmsDeliver(pduParser, i);
                return;
            case 1:
                parseSmsSubmit(pduParser, i);
                return;
            case 2:
                parseSmsStatusReport(pduParser, i);
                return;
            default:
                throw new java.lang.RuntimeException("Unsupported message type");
        }
    }

    private void parseSmsStatusReport(com.android.internal.telephony.gsm.SmsMessage.PduParser pduParser, int i) {
        this.mIsStatusReportMessage = true;
        this.mMessageRef = pduParser.getByte();
        this.mRecipientAddress = pduParser.getAddress();
        this.mScTimeMillis = pduParser.getSCTimestampMillis();
        pduParser.getSCTimestampMillis();
        this.mStatus = pduParser.getByte();
        if (pduParser.moreDataPresent()) {
            int i2 = pduParser.getByte();
            int i3 = i2;
            while ((i3 & 128) != 0) {
                i3 = pduParser.getByte();
            }
            if ((i2 & 120) == 0) {
                if ((i2 & 1) != 0) {
                    this.mProtocolIdentifier = pduParser.getByte();
                }
                if ((i2 & 2) != 0) {
                    this.mDataCodingScheme = pduParser.getByte();
                }
                if ((i2 & 4) != 0) {
                    parseUserData(pduParser, (i & 64) == 64);
                }
            }
        }
    }

    private void parseSmsDeliver(com.android.internal.telephony.gsm.SmsMessage.PduParser pduParser, int i) {
        this.mReplyPathPresent = (i & 128) == 128;
        this.mOriginatingAddress = pduParser.getAddress();
        com.android.internal.telephony.SmsAddress smsAddress = this.mOriginatingAddress;
        this.mProtocolIdentifier = pduParser.getByte();
        this.mDataCodingScheme = pduParser.getByte();
        this.mScTimeMillis = pduParser.getSCTimestampMillis();
        parseUserData(pduParser, (i & 64) == 64);
    }

    private void parseSmsSubmit(com.android.internal.telephony.gsm.SmsMessage.PduParser pduParser, int i) {
        int i2;
        this.mReplyPathPresent = (i & 128) == 128;
        this.mMessageRef = pduParser.getByte();
        this.mRecipientAddress = pduParser.getAddress();
        com.android.internal.telephony.SmsAddress smsAddress = this.mRecipientAddress;
        this.mProtocolIdentifier = pduParser.getByte();
        this.mDataCodingScheme = pduParser.getByte();
        int i3 = (i >> 3) & 3;
        if (i3 == 0) {
            i2 = 0;
        } else if (i3 == 2) {
            i2 = 1;
        } else {
            i2 = 7;
        }
        while (true) {
            int i4 = i2 - 1;
            if (i2 <= 0) {
                break;
            }
            pduParser.getByte();
            i2 = i4;
        }
        parseUserData(pduParser, (i & 64) == 64);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:40:0x01f0, code lost:
    
        if ((r17.mDataCodingScheme & 240) != 224) goto L94;
     */
    /* JADX WARN: Code restructure failed: missing block: B:41:0x01fd, code lost:
    
        r17.mMwiDontStore = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:43:0x01fb, code lost:
    
        if ((r17.mDataCodingScheme & 3) != 0) goto L94;
     */
    /* JADX WARN: Code restructure failed: missing block: B:89:0x0064, code lost:
    
        if (r3.getBoolean(com.android.internal.R.bool.config_sms_decode_gsm_8bit_data) != false) goto L66;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private void parseUserData(com.android.internal.telephony.gsm.SmsMessage.PduParser pduParser, boolean z) {
        int i;
        boolean z2;
        int i2;
        android.content.res.Resources system = android.content.res.Resources.getSystem();
        int i3 = 128;
        int i4 = 2;
        if ((this.mDataCodingScheme & 128) == 0) {
            boolean z3 = (this.mDataCodingScheme & 32) != 0;
            z2 = (this.mDataCodingScheme & 16) != 0;
            if (z3) {
                com.android.telephony.Rlog.w(LOG_TAG, "4 - Unsupported SMS data coding scheme (compression) " + (this.mDataCodingScheme & 255));
                i4 = 0;
            } else {
                switch ((this.mDataCodingScheme >> 2) & 3) {
                    case 0:
                        i4 = 1;
                        break;
                    case 1:
                        break;
                    case 2:
                        i4 = 3;
                        break;
                    case 3:
                        com.android.telephony.Rlog.w(LOG_TAG, "1 - Unsupported SMS data coding scheme " + (this.mDataCodingScheme & 255));
                        i4 = system.getInteger(com.android.internal.R.integer.default_reserved_data_coding_scheme);
                        break;
                    default:
                        i4 = 0;
                        break;
                }
            }
        } else if ((this.mDataCodingScheme & 240) == 240) {
            if ((this.mDataCodingScheme & 4) == 0) {
                i4 = 1;
                z2 = true;
            } else {
                z2 = true;
            }
        } else if ((this.mDataCodingScheme & 240) == 192 || (this.mDataCodingScheme & 240) == 208 || (this.mDataCodingScheme & 240) == 224) {
            if ((this.mDataCodingScheme & 240) == 224) {
                i = 3;
            } else {
                i = 1;
            }
            boolean z4 = (this.mDataCodingScheme & 8) == 8;
            if ((this.mDataCodingScheme & 3) != 0) {
                this.mIsMwi = false;
                com.android.telephony.Rlog.w(LOG_TAG, "MWI in DCS for fax/email/other: " + (this.mDataCodingScheme & 255));
            } else {
                this.mIsMwi = true;
                this.mMwiSense = z4;
                this.mMwiDontStore = (this.mDataCodingScheme & 240) == 192;
                if (z4) {
                    this.mVoiceMailCount = -1;
                } else {
                    this.mVoiceMailCount = 0;
                }
                com.android.telephony.Rlog.w(LOG_TAG, "MWI in DCS for Vmail. DCS = " + (this.mDataCodingScheme & 255) + " Dont store = " + this.mMwiDontStore + " vmail count = " + this.mVoiceMailCount);
            }
            i4 = i;
            z2 = false;
        } else {
            if ((this.mDataCodingScheme & 192) != 128) {
                com.android.telephony.Rlog.w(LOG_TAG, "3 - Unsupported SMS data coding scheme " + (this.mDataCodingScheme & 255));
            } else if (this.mDataCodingScheme != 132) {
                com.android.telephony.Rlog.w(LOG_TAG, "5 - Unsupported SMS data coding scheme " + (this.mDataCodingScheme & 255));
            } else {
                i4 = 4;
                z2 = false;
            }
            i4 = 0;
            z2 = false;
        }
        int constructUserData = pduParser.constructUserData(z, i4 == 1);
        this.mUserData = pduParser.getUserData();
        this.mUserDataHeader = pduParser.getUserDataHeader();
        this.mReceivedEncodingType = i4;
        if (!z || this.mUserDataHeader.specialSmsMsgList.size() == 0) {
            i2 = 0;
        } else {
            java.util.Iterator<com.android.internal.telephony.SmsHeader.SpecialSmsMsg> it = this.mUserDataHeader.specialSmsMsgList.iterator();
            while (it.hasNext()) {
                com.android.internal.telephony.SmsHeader.SpecialSmsMsg next = it.next();
                int i5 = next.msgIndType & 255;
                if (i5 == 0 || i5 == i3) {
                    this.mIsMwi = true;
                    if (i5 == i3) {
                        this.mMwiDontStore = false;
                    } else if (!this.mMwiDontStore) {
                        if ((this.mDataCodingScheme & 240) == 208) {
                        }
                    }
                    this.mVoiceMailCount = next.msgCount & 255;
                    if (this.mVoiceMailCount <= 0) {
                        this.mMwiSense = false;
                    } else {
                        this.mMwiSense = true;
                    }
                    com.android.telephony.Rlog.w(LOG_TAG, "MWI in TP-UDH for Vmail. Msg Ind = " + i5 + " Dont store = " + this.mMwiDontStore + " Vmail count = " + this.mVoiceMailCount);
                } else {
                    com.android.telephony.Rlog.w(LOG_TAG, "TP_UDH fax/email/extended msg/multisubscriber profile. Msg Ind = " + i5);
                }
                i3 = 128;
            }
            i2 = 0;
        }
        switch (i4) {
            case 0:
                this.mMessageBody = null;
                break;
            case 1:
                this.mMessageBody = pduParser.getUserDataGSM7Bit(constructUserData, z ? this.mUserDataHeader.languageTable : i2, z ? this.mUserDataHeader.languageShiftTable : i2);
                break;
            case 2:
                if (system.getBoolean(com.android.internal.R.bool.config_sms_decode_gsm_8bit_data)) {
                    this.mMessageBody = pduParser.getUserDataGSM8bit(constructUserData);
                    break;
                } else {
                    this.mMessageBody = null;
                    break;
                }
            case 3:
                this.mMessageBody = pduParser.getUserDataUCS2(constructUserData);
                break;
            case 4:
                this.mMessageBody = pduParser.getUserDataKSC5601(constructUserData);
                break;
        }
        if (this.mMessageBody != null) {
            parseMessageBody();
        }
        if (!z2) {
            this.messageClass = com.android.internal.telephony.SmsConstants.MessageClass.UNKNOWN;
            return;
        }
        switch (this.mDataCodingScheme & 3) {
            case 0:
                this.messageClass = com.android.internal.telephony.SmsConstants.MessageClass.CLASS_0;
                break;
            case 1:
                this.messageClass = com.android.internal.telephony.SmsConstants.MessageClass.CLASS_1;
                break;
            case 2:
                this.messageClass = com.android.internal.telephony.SmsConstants.MessageClass.CLASS_2;
                break;
            case 3:
                this.messageClass = com.android.internal.telephony.SmsConstants.MessageClass.CLASS_3;
                break;
        }
    }

    @Override // com.android.internal.telephony.SmsMessageBase
    public com.android.internal.telephony.SmsConstants.MessageClass getMessageClass() {
        return this.messageClass;
    }

    boolean isUsimDataDownload() {
        return this.messageClass == com.android.internal.telephony.SmsConstants.MessageClass.CLASS_2 && (this.mProtocolIdentifier == 127 || this.mProtocolIdentifier == 124);
    }

    public int getNumOfVoicemails() {
        if (!this.mIsMwi && isCphsMwiMessage()) {
            if (this.mOriginatingAddress != null && ((com.android.internal.telephony.gsm.GsmSmsAddress) this.mOriginatingAddress).isCphsVoiceMessageSet()) {
                this.mVoiceMailCount = 255;
            } else {
                this.mVoiceMailCount = 0;
            }
            com.android.telephony.Rlog.v(LOG_TAG, "CPHS voice mail message");
        }
        return this.mVoiceMailCount;
    }
}
