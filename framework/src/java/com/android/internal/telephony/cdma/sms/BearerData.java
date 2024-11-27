package com.android.internal.telephony.cdma.sms;

/* loaded from: classes5.dex */
public final class BearerData {
    public static final int ALERT_DEFAULT = 0;
    public static final int ALERT_HIGH_PRIO = 3;
    public static final int ALERT_LOW_PRIO = 1;
    public static final int ALERT_MEDIUM_PRIO = 2;
    public static final int DISPLAY_MODE_DEFAULT = 1;
    public static final int DISPLAY_MODE_IMMEDIATE = 0;
    public static final int DISPLAY_MODE_USER = 2;
    public static final int ERROR_NONE = 0;
    public static final int ERROR_PERMANENT = 3;
    public static final int ERROR_TEMPORARY = 2;
    public static final int ERROR_UNDEFINED = 255;
    public static final int LANGUAGE_CHINESE = 6;
    public static final int LANGUAGE_ENGLISH = 1;
    public static final int LANGUAGE_FRENCH = 2;
    public static final int LANGUAGE_HEBREW = 7;
    public static final int LANGUAGE_JAPANESE = 4;
    public static final int LANGUAGE_KOREAN = 5;
    public static final int LANGUAGE_SPANISH = 3;
    public static final int LANGUAGE_UNKNOWN = 0;
    private static final java.lang.String LOG_TAG = "BearerData";
    public static final int MESSAGE_TYPE_CANCELLATION = 3;
    public static final int MESSAGE_TYPE_DELIVER = 1;
    public static final int MESSAGE_TYPE_DELIVERY_ACK = 4;
    public static final int MESSAGE_TYPE_DELIVER_REPORT = 7;
    public static final int MESSAGE_TYPE_READ_ACK = 6;
    public static final int MESSAGE_TYPE_SUBMIT = 2;
    public static final int MESSAGE_TYPE_SUBMIT_REPORT = 8;
    public static final int MESSAGE_TYPE_USER_ACK = 5;
    public static final int PRIORITY_EMERGENCY = 3;
    public static final int PRIORITY_INTERACTIVE = 1;
    public static final int PRIORITY_NORMAL = 0;
    public static final int PRIORITY_URGENT = 2;
    public static final int PRIVACY_CONFIDENTIAL = 2;
    public static final int PRIVACY_NOT_RESTRICTED = 0;
    public static final int PRIVACY_RESTRICTED = 1;
    public static final int PRIVACY_SECRET = 3;
    public static final int RELATIVE_TIME_DAYS_LIMIT = 196;
    public static final int RELATIVE_TIME_HOURS_LIMIT = 167;
    public static final int RELATIVE_TIME_INDEFINITE = 245;
    public static final int RELATIVE_TIME_MINS_LIMIT = 143;
    public static final int RELATIVE_TIME_MOBILE_INACTIVE = 247;
    public static final int RELATIVE_TIME_NOW = 246;
    public static final int RELATIVE_TIME_RESERVED = 248;
    public static final int RELATIVE_TIME_WEEKS_LIMIT = 244;
    public static final int STATUS_ACCEPTED = 0;
    public static final int STATUS_BLOCKED_DESTINATION = 7;
    public static final int STATUS_CANCELLED = 3;
    public static final int STATUS_CANCEL_FAILED = 6;
    public static final int STATUS_DELIVERED = 2;
    public static final int STATUS_DEPOSITED_TO_INTERNET = 1;
    public static final int STATUS_DUPLICATE_MESSAGE = 9;
    public static final int STATUS_INVALID_DESTINATION = 10;
    public static final int STATUS_MESSAGE_EXPIRED = 13;
    public static final int STATUS_NETWORK_CONGESTION = 4;
    public static final int STATUS_NETWORK_ERROR = 5;
    public static final int STATUS_TEXT_TOO_LONG = 8;
    public static final int STATUS_UNDEFINED = 255;
    public static final int STATUS_UNKNOWN_ERROR = 31;
    private static final byte SUBPARAM_ALERT_ON_MESSAGE_DELIVERY = 12;
    private static final byte SUBPARAM_CALLBACK_NUMBER = 14;
    private static final byte SUBPARAM_DEFERRED_DELIVERY_TIME_ABSOLUTE = 6;
    private static final byte SUBPARAM_DEFERRED_DELIVERY_TIME_RELATIVE = 7;
    private static final byte SUBPARAM_ID_LAST_DEFINED = 23;
    private static final byte SUBPARAM_LANGUAGE_INDICATOR = 13;
    private static final byte SUBPARAM_MESSAGE_CENTER_TIME_STAMP = 3;
    private static final byte SUBPARAM_MESSAGE_DEPOSIT_INDEX = 17;
    private static final byte SUBPARAM_MESSAGE_DISPLAY_MODE = 15;
    private static final byte SUBPARAM_MESSAGE_IDENTIFIER = 0;
    private static final byte SUBPARAM_MESSAGE_STATUS = 20;
    private static final byte SUBPARAM_NUMBER_OF_MESSAGES = 11;
    private static final byte SUBPARAM_PRIORITY_INDICATOR = 8;
    private static final byte SUBPARAM_PRIVACY_INDICATOR = 9;
    private static final byte SUBPARAM_REPLY_OPTION = 10;
    private static final byte SUBPARAM_SERVICE_CATEGORY_PROGRAM_DATA = 18;
    private static final byte SUBPARAM_SERVICE_CATEGORY_PROGRAM_RESULTS = 19;
    private static final byte SUBPARAM_USER_DATA = 1;
    private static final byte SUBPARAM_USER_RESPONSE_CODE = 2;
    private static final byte SUBPARAM_VALIDITY_PERIOD_ABSOLUTE = 4;
    private static final byte SUBPARAM_VALIDITY_PERIOD_RELATIVE = 5;
    public com.android.internal.telephony.cdma.sms.CdmaSmsAddress callbackNumber;
    public android.telephony.SmsCbCmasInfo cmasWarningInfo;
    public com.android.internal.telephony.cdma.sms.BearerData.TimeStamp deferredDeliveryTimeAbsolute;
    public int deferredDeliveryTimeRelative;
    public boolean deferredDeliveryTimeRelativeSet;
    public boolean deliveryAckReq;
    public int depositIndex;
    public boolean hasUserDataHeader;
    public int messageId;
    public int messageType;
    public com.android.internal.telephony.cdma.sms.BearerData.TimeStamp msgCenterTimeStamp;
    public int numberOfMessages;
    public boolean readAckReq;
    public boolean reportReq;
    public java.util.ArrayList<android.telephony.cdma.CdmaSmsCbProgramData> serviceCategoryProgramData;
    public java.util.ArrayList<android.telephony.cdma.CdmaSmsCbProgramResults> serviceCategoryProgramResults;
    public boolean userAckReq;
    public com.android.internal.telephony.cdma.sms.UserData userData;
    public int userResponseCode;
    public com.android.internal.telephony.cdma.sms.BearerData.TimeStamp validityPeriodAbsolute;
    public int validityPeriodRelative;
    public boolean validityPeriodRelativeSet;
    public boolean priorityIndicatorSet = false;
    public int priority = 0;
    public boolean privacyIndicatorSet = false;
    public int privacy = 0;
    public boolean alertIndicatorSet = false;
    public int alert = 0;
    public boolean displayModeSet = false;
    public int displayMode = 1;
    public boolean languageIndicatorSet = false;
    public int language = 0;
    public boolean messageStatusSet = false;
    public int errorClass = 255;
    public int messageStatus = 255;
    public boolean userResponseCodeSet = false;

    public static class TimeStamp {
        public int hour;
        private java.time.ZoneId mZoneId = java.time.ZoneId.systemDefault();
        public int minute;
        public int monthDay;
        public int monthOrdinal;
        public int second;
        public int year;

        public static com.android.internal.telephony.cdma.sms.BearerData.TimeStamp fromByteArray(byte[] bArr) {
            com.android.internal.telephony.cdma.sms.BearerData.TimeStamp timeStamp = new com.android.internal.telephony.cdma.sms.BearerData.TimeStamp();
            int cdmaBcdByteToInt = com.android.internal.telephony.uicc.IccUtils.cdmaBcdByteToInt(bArr[0]);
            if (cdmaBcdByteToInt > 99 || cdmaBcdByteToInt < 0) {
                return null;
            }
            timeStamp.year = cdmaBcdByteToInt >= 96 ? cdmaBcdByteToInt + 1900 : cdmaBcdByteToInt + 2000;
            int cdmaBcdByteToInt2 = com.android.internal.telephony.uicc.IccUtils.cdmaBcdByteToInt(bArr[1]);
            if (cdmaBcdByteToInt2 < 1 || cdmaBcdByteToInt2 > 12) {
                return null;
            }
            timeStamp.monthOrdinal = cdmaBcdByteToInt2;
            int cdmaBcdByteToInt3 = com.android.internal.telephony.uicc.IccUtils.cdmaBcdByteToInt(bArr[2]);
            if (cdmaBcdByteToInt3 < 1 || cdmaBcdByteToInt3 > 31) {
                return null;
            }
            timeStamp.monthDay = cdmaBcdByteToInt3;
            int cdmaBcdByteToInt4 = com.android.internal.telephony.uicc.IccUtils.cdmaBcdByteToInt(bArr[3]);
            if (cdmaBcdByteToInt4 < 0 || cdmaBcdByteToInt4 > 23) {
                return null;
            }
            timeStamp.hour = cdmaBcdByteToInt4;
            int cdmaBcdByteToInt5 = com.android.internal.telephony.uicc.IccUtils.cdmaBcdByteToInt(bArr[4]);
            if (cdmaBcdByteToInt5 < 0 || cdmaBcdByteToInt5 > 59) {
                return null;
            }
            timeStamp.minute = cdmaBcdByteToInt5;
            int cdmaBcdByteToInt6 = com.android.internal.telephony.uicc.IccUtils.cdmaBcdByteToInt(bArr[5]);
            if (cdmaBcdByteToInt6 < 0 || cdmaBcdByteToInt6 > 59) {
                return null;
            }
            timeStamp.second = cdmaBcdByteToInt6;
            return timeStamp;
        }

        /* JADX WARN: Type inference failed for: r2v3, types: [java.time.LocalDateTime] */
        public static com.android.internal.telephony.cdma.sms.BearerData.TimeStamp fromMillis(long j) {
            com.android.internal.telephony.cdma.sms.BearerData.TimeStamp timeStamp = new com.android.internal.telephony.cdma.sms.BearerData.TimeStamp();
            ?? localDateTime = java.time.Instant.ofEpochMilli(j).atZone(timeStamp.mZoneId).toLocalDateTime();
            int year = localDateTime.getYear();
            if (year < 1996 || year > 2095) {
                return null;
            }
            timeStamp.year = year;
            timeStamp.monthOrdinal = localDateTime.getMonthValue();
            timeStamp.monthDay = localDateTime.getDayOfMonth();
            timeStamp.hour = localDateTime.getHour();
            timeStamp.minute = localDateTime.getMinute();
            timeStamp.second = localDateTime.getSecond();
            return timeStamp;
        }

        public byte[] toByteArray() {
            int i = this.year % 100;
            java.io.ByteArrayOutputStream byteArrayOutputStream = new java.io.ByteArrayOutputStream(6);
            byteArrayOutputStream.write(((i % 10) & 15) | (((i / 10) & 15) << 4));
            byteArrayOutputStream.write((((this.monthOrdinal / 10) << 4) & 240) | ((this.monthOrdinal % 10) & 15));
            byteArrayOutputStream.write((((this.monthDay / 10) << 4) & 240) | ((this.monthDay % 10) & 15));
            byteArrayOutputStream.write((((this.hour / 10) << 4) & 240) | ((this.hour % 10) & 15));
            byteArrayOutputStream.write((((this.minute / 10) << 4) & 240) | ((this.minute % 10) & 15));
            byteArrayOutputStream.write((((this.second / 10) << 4) & 240) | ((this.second % 10) & 15));
            return byteArrayOutputStream.toByteArray();
        }

        public long toMillis() {
            try {
                java.time.LocalDateTime of = java.time.LocalDateTime.of(this.year, this.monthOrdinal, this.monthDay, this.hour, this.minute, this.second);
                return of.toInstant(this.mZoneId.getRules().getOffset(of)).toEpochMilli();
            } catch (java.time.DateTimeException e) {
                com.android.telephony.Rlog.e(com.android.internal.telephony.cdma.sms.BearerData.LOG_TAG, "Invalid timestamp", e);
                return 0L;
            }
        }

        public java.lang.String toString() {
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            sb.append("TimeStamp ");
            sb.append("{ year=" + this.year);
            sb.append(", month=" + this.monthOrdinal);
            sb.append(", day=" + this.monthDay);
            sb.append(", hour=" + this.hour);
            sb.append(", minute=" + this.minute);
            sb.append(", second=" + this.second);
            sb.append(" }");
            return sb.toString();
        }
    }

    private static class CodingException extends java.lang.Exception {
        public CodingException(java.lang.String str) {
            super(str);
        }
    }

    public java.lang.String getLanguage() {
        return getLanguageCodeForValue(this.language);
    }

    private static java.lang.String getLanguageCodeForValue(int i) {
        switch (i) {
            case 1:
                return "en";
            case 2:
                return "fr";
            case 3:
                return "es";
            case 4:
                return "ja";
            case 5:
                return "ko";
            case 6:
                return "zh";
            case 7:
                return "he";
            default:
                return null;
        }
    }

    public java.lang.String toString() {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append("BearerData ");
        sb.append("{ messageType=" + this.messageType);
        sb.append(", messageId=" + this.messageId);
        sb.append(", priority=" + (this.priorityIndicatorSet ? java.lang.Integer.valueOf(this.priority) : "unset"));
        sb.append(", privacy=" + (this.privacyIndicatorSet ? java.lang.Integer.valueOf(this.privacy) : "unset"));
        sb.append(", alert=" + (this.alertIndicatorSet ? java.lang.Integer.valueOf(this.alert) : "unset"));
        sb.append(", displayMode=" + (this.displayModeSet ? java.lang.Integer.valueOf(this.displayMode) : "unset"));
        sb.append(", language=" + (this.languageIndicatorSet ? java.lang.Integer.valueOf(this.language) : "unset"));
        sb.append(", errorClass=" + (this.messageStatusSet ? java.lang.Integer.valueOf(this.errorClass) : "unset"));
        sb.append(", msgStatus=" + (this.messageStatusSet ? java.lang.Integer.valueOf(this.messageStatus) : "unset"));
        sb.append(", msgCenterTimeStamp=" + (this.msgCenterTimeStamp != null ? this.msgCenterTimeStamp : "unset"));
        sb.append(", validityPeriodAbsolute=" + (this.validityPeriodAbsolute != null ? this.validityPeriodAbsolute : "unset"));
        sb.append(", validityPeriodRelative=" + (this.validityPeriodRelativeSet ? java.lang.Integer.valueOf(this.validityPeriodRelative) : "unset"));
        sb.append(", deferredDeliveryTimeAbsolute=" + (this.deferredDeliveryTimeAbsolute != null ? this.deferredDeliveryTimeAbsolute : "unset"));
        sb.append(", deferredDeliveryTimeRelative=" + (this.deferredDeliveryTimeRelativeSet ? java.lang.Integer.valueOf(this.deferredDeliveryTimeRelative) : "unset"));
        sb.append(", userAckReq=" + this.userAckReq);
        sb.append(", deliveryAckReq=" + this.deliveryAckReq);
        sb.append(", readAckReq=" + this.readAckReq);
        sb.append(", reportReq=" + this.reportReq);
        sb.append(", numberOfMessages=" + this.numberOfMessages);
        sb.append(", callbackNumber=" + com.android.telephony.Rlog.pii(LOG_TAG, this.callbackNumber));
        sb.append(", depositIndex=" + this.depositIndex);
        sb.append(", hasUserDataHeader=" + this.hasUserDataHeader);
        sb.append(", userData=" + this.userData);
        sb.append(" }");
        return sb.toString();
    }

    private static void encodeMessageId(com.android.internal.telephony.cdma.sms.BearerData bearerData, com.android.internal.util.BitwiseOutputStream bitwiseOutputStream) throws com.android.internal.util.BitwiseOutputStream.AccessException {
        bitwiseOutputStream.write(8, 3);
        bitwiseOutputStream.write(4, bearerData.messageType);
        bitwiseOutputStream.write(8, bearerData.messageId >> 8);
        bitwiseOutputStream.write(8, bearerData.messageId);
        bitwiseOutputStream.write(1, bearerData.hasUserDataHeader ? 1 : 0);
        bitwiseOutputStream.skip(3);
    }

    private static int countAsciiSeptets(java.lang.CharSequence charSequence, boolean z) {
        int length = charSequence.length();
        if (z) {
            return length;
        }
        for (int i = 0; i < length; i++) {
            if (com.android.internal.telephony.cdma.sms.UserData.charToAscii.get(charSequence.charAt(i), -1) == -1) {
                return -1;
            }
        }
        return length;
    }

    public static com.android.internal.telephony.GsmAlphabet.TextEncodingDetails calcTextEncodingDetails(java.lang.CharSequence charSequence, boolean z, boolean z2) {
        java.lang.CharSequence charSequence2;
        if (!android.content.res.Resources.getSystem().getBoolean(com.android.internal.R.bool.config_sms_force_7bit_encoding)) {
            charSequence2 = null;
        } else {
            charSequence2 = com.android.internal.telephony.Sms7BitEncodingTranslator.translate(charSequence, true);
        }
        if (android.text.TextUtils.isEmpty(charSequence2)) {
            charSequence2 = charSequence;
        }
        int countAsciiSeptets = countAsciiSeptets(charSequence2, z);
        if (countAsciiSeptets != -1 && countAsciiSeptets <= 160) {
            com.android.internal.telephony.GsmAlphabet.TextEncodingDetails textEncodingDetails = new com.android.internal.telephony.GsmAlphabet.TextEncodingDetails();
            textEncodingDetails.msgCount = 1;
            textEncodingDetails.codeUnitCount = countAsciiSeptets;
            textEncodingDetails.codeUnitsRemaining = 160 - countAsciiSeptets;
            textEncodingDetails.codeUnitSize = 1;
            return textEncodingDetails;
        }
        com.android.internal.telephony.GsmAlphabet.TextEncodingDetails calculateLength = com.android.internal.telephony.gsm.SmsMessage.calculateLength(charSequence, z);
        if (calculateLength.msgCount == 1 && calculateLength.codeUnitSize == 1 && z2) {
            return com.android.internal.telephony.SmsMessageBase.calcUnicodeEncodingDetails(charSequence);
        }
        return calculateLength;
    }

    private static byte[] encode7bitAscii(java.lang.String str, boolean z) throws com.android.internal.telephony.cdma.sms.BearerData.CodingException {
        try {
            com.android.internal.util.BitwiseOutputStream bitwiseOutputStream = new com.android.internal.util.BitwiseOutputStream(str.length());
            int length = str.length();
            for (int i = 0; i < length; i++) {
                int i2 = com.android.internal.telephony.cdma.sms.UserData.charToAscii.get(str.charAt(i), -1);
                if (i2 == -1) {
                    if (z) {
                        bitwiseOutputStream.write(7, 32);
                    } else {
                        throw new com.android.internal.telephony.cdma.sms.BearerData.CodingException("cannot ASCII encode (" + str.charAt(i) + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
                    }
                } else {
                    bitwiseOutputStream.write(7, i2);
                }
            }
            return bitwiseOutputStream.toByteArray();
        } catch (com.android.internal.util.BitwiseOutputStream.AccessException e) {
            throw new com.android.internal.telephony.cdma.sms.BearerData.CodingException("7bit ASCII encode failed: " + e);
        }
    }

    private static byte[] encodeUtf16(java.lang.String str) throws com.android.internal.telephony.cdma.sms.BearerData.CodingException {
        try {
            return str.getBytes("utf-16be");
        } catch (java.io.UnsupportedEncodingException e) {
            throw new com.android.internal.telephony.cdma.sms.BearerData.CodingException("UTF-16 encode failed: " + e);
        }
    }

    private static class Gsm7bitCodingResult {
        byte[] data;
        int septets;

        private Gsm7bitCodingResult() {
        }
    }

    private static com.android.internal.telephony.cdma.sms.BearerData.Gsm7bitCodingResult encode7bitGsm(java.lang.String str, int i, boolean z) throws com.android.internal.telephony.cdma.sms.BearerData.CodingException {
        try {
            byte[] stringToGsm7BitPacked = com.android.internal.telephony.GsmAlphabet.stringToGsm7BitPacked(str, i, !z, 0, 0);
            com.android.internal.telephony.cdma.sms.BearerData.Gsm7bitCodingResult gsm7bitCodingResult = new com.android.internal.telephony.cdma.sms.BearerData.Gsm7bitCodingResult();
            gsm7bitCodingResult.data = new byte[stringToGsm7BitPacked.length - 1];
            java.lang.System.arraycopy(stringToGsm7BitPacked, 1, gsm7bitCodingResult.data, 0, stringToGsm7BitPacked.length - 1);
            gsm7bitCodingResult.septets = stringToGsm7BitPacked[0] & 255;
            return gsm7bitCodingResult;
        } catch (com.android.internal.telephony.EncodeException e) {
            throw new com.android.internal.telephony.cdma.sms.BearerData.CodingException("7bit GSM encode failed: " + e);
        }
    }

    private static void encode7bitEms(com.android.internal.telephony.cdma.sms.UserData userData, byte[] bArr, boolean z) throws com.android.internal.telephony.cdma.sms.BearerData.CodingException {
        com.android.internal.telephony.cdma.sms.BearerData.Gsm7bitCodingResult encode7bitGsm = encode7bitGsm(userData.payloadStr, (((bArr.length + 1) * 8) + 6) / 7, z);
        userData.msgEncoding = 9;
        userData.msgEncodingSet = true;
        userData.numFields = encode7bitGsm.septets;
        userData.payload = encode7bitGsm.data;
        userData.payload[0] = (byte) bArr.length;
        java.lang.System.arraycopy(bArr, 0, userData.payload, 1, bArr.length);
    }

    private static void encode16bitEms(com.android.internal.telephony.cdma.sms.UserData userData, byte[] bArr) throws com.android.internal.telephony.cdma.sms.BearerData.CodingException {
        byte[] encodeUtf16 = encodeUtf16(userData.payloadStr);
        int length = bArr.length + 1;
        int length2 = encodeUtf16.length / 2;
        userData.msgEncoding = 4;
        userData.msgEncodingSet = true;
        userData.numFields = ((length + 1) / 2) + length2;
        userData.payload = new byte[userData.numFields * 2];
        userData.payload[0] = (byte) bArr.length;
        java.lang.System.arraycopy(bArr, 0, userData.payload, 1, bArr.length);
        java.lang.System.arraycopy(encodeUtf16, 0, userData.payload, length, encodeUtf16.length);
    }

    private static void encode7bitAsciiEms(com.android.internal.telephony.cdma.sms.UserData userData, byte[] bArr, boolean z) throws com.android.internal.telephony.cdma.sms.BearerData.CodingException {
        try {
            com.android.telephony.Rlog.d(LOG_TAG, "encode7bitAsciiEms");
            int length = bArr.length + 1;
            int i = length * 8;
            int i2 = (i + 6) / 7;
            int i3 = (i2 * 7) - i;
            java.lang.String str = userData.payloadStr;
            int length2 = str.length();
            com.android.internal.util.BitwiseOutputStream bitwiseOutputStream = new com.android.internal.util.BitwiseOutputStream((i3 > 0 ? 1 : 0) + length2);
            bitwiseOutputStream.write(i3, 0);
            for (int i4 = 0; i4 < length2; i4++) {
                int i5 = com.android.internal.telephony.cdma.sms.UserData.charToAscii.get(str.charAt(i4), -1);
                if (i5 == -1) {
                    if (z) {
                        bitwiseOutputStream.write(7, 32);
                    } else {
                        throw new com.android.internal.telephony.cdma.sms.BearerData.CodingException("cannot ASCII encode (" + str.charAt(i4) + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
                    }
                } else {
                    bitwiseOutputStream.write(7, i5);
                }
            }
            byte[] byteArray = bitwiseOutputStream.toByteArray();
            userData.msgEncoding = 2;
            userData.msgEncodingSet = true;
            userData.numFields = i2 + userData.payloadStr.length();
            userData.payload = new byte[byteArray.length + length];
            userData.payload[0] = (byte) bArr.length;
            java.lang.System.arraycopy(bArr, 0, userData.payload, 1, bArr.length);
            java.lang.System.arraycopy(byteArray, 0, userData.payload, length, byteArray.length);
        } catch (com.android.internal.util.BitwiseOutputStream.AccessException e) {
            throw new com.android.internal.telephony.cdma.sms.BearerData.CodingException("7bit ASCII encode failed: " + e);
        }
    }

    private static void encodeEmsUserDataPayload(com.android.internal.telephony.cdma.sms.UserData userData) throws com.android.internal.telephony.cdma.sms.BearerData.CodingException {
        byte[] byteArray = com.android.internal.telephony.SmsHeader.toByteArray(userData.userDataHeader);
        if (userData.msgEncodingSet) {
            if (userData.msgEncoding == 9) {
                encode7bitEms(userData, byteArray, true);
                return;
            } else if (userData.msgEncoding == 4) {
                encode16bitEms(userData, byteArray);
                return;
            } else {
                if (userData.msgEncoding == 2) {
                    encode7bitAsciiEms(userData, byteArray, true);
                    return;
                }
                throw new com.android.internal.telephony.cdma.sms.BearerData.CodingException("unsupported EMS user data encoding (" + userData.msgEncoding + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
            }
        }
        try {
            encode7bitEms(userData, byteArray, false);
        } catch (com.android.internal.telephony.cdma.sms.BearerData.CodingException e) {
            encode16bitEms(userData, byteArray);
        }
    }

    private static byte[] encodeShiftJis(java.lang.String str) throws com.android.internal.telephony.cdma.sms.BearerData.CodingException {
        try {
            return str.getBytes("Shift_JIS");
        } catch (java.io.UnsupportedEncodingException e) {
            throw new com.android.internal.telephony.cdma.sms.BearerData.CodingException("Shift-JIS encode failed: " + e);
        }
    }

    private static void encodeUserDataPayload(com.android.internal.telephony.cdma.sms.UserData userData) throws com.android.internal.telephony.cdma.sms.BearerData.CodingException {
        if (userData.payloadStr == null && userData.msgEncoding != 0) {
            com.android.telephony.Rlog.e(LOG_TAG, "user data with null payloadStr");
            userData.payloadStr = "";
        }
        if (userData.userDataHeader != null) {
            encodeEmsUserDataPayload(userData);
            return;
        }
        if (userData.msgEncodingSet) {
            if (userData.msgEncoding == 0) {
                if (userData.payload == null) {
                    com.android.telephony.Rlog.e(LOG_TAG, "user data with octet encoding but null payload");
                    userData.payload = new byte[0];
                    userData.numFields = 0;
                    return;
                }
                userData.numFields = userData.payload.length;
                return;
            }
            if (userData.payloadStr == null) {
                com.android.telephony.Rlog.e(LOG_TAG, "non-octet user data with null payloadStr");
                userData.payloadStr = "";
            }
            if (userData.msgEncoding == 9) {
                com.android.internal.telephony.cdma.sms.BearerData.Gsm7bitCodingResult encode7bitGsm = encode7bitGsm(userData.payloadStr, 0, true);
                userData.payload = encode7bitGsm.data;
                userData.numFields = encode7bitGsm.septets;
                return;
            } else if (userData.msgEncoding == 2) {
                userData.payload = encode7bitAscii(userData.payloadStr, true);
                userData.numFields = userData.payloadStr.length();
                return;
            } else if (userData.msgEncoding == 4) {
                userData.payload = encodeUtf16(userData.payloadStr);
                userData.numFields = userData.payloadStr.length();
                return;
            } else {
                if (userData.msgEncoding == 5) {
                    userData.payload = encodeShiftJis(userData.payloadStr);
                    userData.numFields = userData.payload.length;
                    return;
                }
                throw new com.android.internal.telephony.cdma.sms.BearerData.CodingException("unsupported user data encoding (" + userData.msgEncoding + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
            }
        }
        try {
            userData.payload = encode7bitAscii(userData.payloadStr, false);
            userData.msgEncoding = 2;
        } catch (com.android.internal.telephony.cdma.sms.BearerData.CodingException e) {
            userData.payload = encodeUtf16(userData.payloadStr);
            userData.msgEncoding = 4;
        }
        userData.numFields = userData.payloadStr.length();
        userData.msgEncodingSet = true;
    }

    private static void encodeUserData(com.android.internal.telephony.cdma.sms.BearerData bearerData, com.android.internal.util.BitwiseOutputStream bitwiseOutputStream) throws com.android.internal.util.BitwiseOutputStream.AccessException, com.android.internal.telephony.cdma.sms.BearerData.CodingException {
        encodeUserDataPayload(bearerData.userData);
        bearerData.hasUserDataHeader = bearerData.userData.userDataHeader != null;
        if (bearerData.userData.payload.length > 140) {
            throw new com.android.internal.telephony.cdma.sms.BearerData.CodingException("encoded user data too large (" + bearerData.userData.payload.length + " > 140 bytes)");
        }
        if (bearerData.userData.msgEncoding == 2) {
            bearerData.userData.paddingBits = (bearerData.userData.payload.length * 8) - (bearerData.userData.numFields * 7);
        } else {
            bearerData.userData.paddingBits = 0;
        }
        int length = (bearerData.userData.payload.length * 8) - bearerData.userData.paddingBits;
        int i = length + 13;
        if (bearerData.userData.msgEncoding == 1 || bearerData.userData.msgEncoding == 10) {
            i += 8;
        }
        int i2 = (i / 8) + (i % 8 > 0 ? 1 : 0);
        int i3 = (i2 * 8) - i;
        bitwiseOutputStream.write(8, i2);
        bitwiseOutputStream.write(5, bearerData.userData.msgEncoding);
        if (bearerData.userData.msgEncoding == 1 || bearerData.userData.msgEncoding == 10) {
            bitwiseOutputStream.write(8, bearerData.userData.msgType);
        }
        bitwiseOutputStream.write(8, bearerData.userData.numFields);
        bitwiseOutputStream.writeByteArray(length, bearerData.userData.payload);
        if (i3 > 0) {
            bitwiseOutputStream.write(i3, 0);
        }
    }

    private static void encodeReplyOption(com.android.internal.telephony.cdma.sms.BearerData bearerData, com.android.internal.util.BitwiseOutputStream bitwiseOutputStream) throws com.android.internal.util.BitwiseOutputStream.AccessException {
        bitwiseOutputStream.write(8, 1);
        bitwiseOutputStream.write(1, bearerData.userAckReq ? 1 : 0);
        bitwiseOutputStream.write(1, bearerData.deliveryAckReq ? 1 : 0);
        bitwiseOutputStream.write(1, bearerData.readAckReq ? 1 : 0);
        bitwiseOutputStream.write(1, bearerData.reportReq ? 1 : 0);
        bitwiseOutputStream.write(4, 0);
    }

    private static byte[] encodeDtmfSmsAddress(java.lang.String str) {
        int i;
        int length = str.length();
        int i2 = length * 4;
        byte[] bArr = new byte[(i2 / 8) + (i2 % 8 > 0 ? 1 : 0)];
        for (int i3 = 0; i3 < length; i3++) {
            char charAt = str.charAt(i3);
            if (charAt >= '1' && charAt <= '9') {
                i = charAt - '0';
            } else if (charAt == '0') {
                i = 10;
            } else if (charAt == '*') {
                i = 11;
            } else {
                if (charAt != '#') {
                    return null;
                }
                i = 12;
            }
            int i4 = i3 / 2;
            bArr[i4] = (byte) ((i << (4 - ((i3 % 2) * 4))) | bArr[i4]);
        }
        return bArr;
    }

    private static void encodeCdmaSmsAddress(com.android.internal.telephony.cdma.sms.CdmaSmsAddress cdmaSmsAddress) throws com.android.internal.telephony.cdma.sms.BearerData.CodingException {
        if (cdmaSmsAddress.digitMode == 1) {
            try {
                cdmaSmsAddress.origBytes = cdmaSmsAddress.address.getBytes("US-ASCII");
            } catch (java.io.UnsupportedEncodingException e) {
                throw new com.android.internal.telephony.cdma.sms.BearerData.CodingException("invalid SMS address, cannot convert to ASCII");
            }
        } else {
            cdmaSmsAddress.origBytes = encodeDtmfSmsAddress(cdmaSmsAddress.address);
        }
    }

    private static void encodeCallbackNumber(com.android.internal.telephony.cdma.sms.BearerData bearerData, com.android.internal.util.BitwiseOutputStream bitwiseOutputStream) throws com.android.internal.util.BitwiseOutputStream.AccessException, com.android.internal.telephony.cdma.sms.BearerData.CodingException {
        int i;
        int i2;
        com.android.internal.telephony.cdma.sms.CdmaSmsAddress cdmaSmsAddress = bearerData.callbackNumber;
        encodeCdmaSmsAddress(cdmaSmsAddress);
        if (cdmaSmsAddress.digitMode == 1) {
            i = cdmaSmsAddress.numberOfDigits * 8;
            i2 = 16;
        } else {
            i = cdmaSmsAddress.numberOfDigits * 4;
            i2 = 9;
        }
        int i3 = i2 + i;
        int i4 = (i3 / 8) + (i3 % 8 > 0 ? 1 : 0);
        int i5 = (i4 * 8) - i3;
        bitwiseOutputStream.write(8, i4);
        bitwiseOutputStream.write(1, cdmaSmsAddress.digitMode);
        if (cdmaSmsAddress.digitMode == 1) {
            bitwiseOutputStream.write(3, cdmaSmsAddress.ton);
            bitwiseOutputStream.write(4, cdmaSmsAddress.numberPlan);
        }
        bitwiseOutputStream.write(8, cdmaSmsAddress.numberOfDigits);
        bitwiseOutputStream.writeByteArray(i, cdmaSmsAddress.origBytes);
        if (i5 > 0) {
            bitwiseOutputStream.write(i5, 0);
        }
    }

    private static void encodeMsgStatus(com.android.internal.telephony.cdma.sms.BearerData bearerData, com.android.internal.util.BitwiseOutputStream bitwiseOutputStream) throws com.android.internal.util.BitwiseOutputStream.AccessException {
        bitwiseOutputStream.write(8, 1);
        bitwiseOutputStream.write(2, bearerData.errorClass);
        bitwiseOutputStream.write(6, bearerData.messageStatus);
    }

    private static void encodeMsgCount(com.android.internal.telephony.cdma.sms.BearerData bearerData, com.android.internal.util.BitwiseOutputStream bitwiseOutputStream) throws com.android.internal.util.BitwiseOutputStream.AccessException {
        bitwiseOutputStream.write(8, 1);
        bitwiseOutputStream.write(8, bearerData.numberOfMessages);
    }

    private static void encodeValidityPeriodRel(com.android.internal.telephony.cdma.sms.BearerData bearerData, com.android.internal.util.BitwiseOutputStream bitwiseOutputStream) throws com.android.internal.util.BitwiseOutputStream.AccessException {
        bitwiseOutputStream.write(8, 1);
        bitwiseOutputStream.write(8, bearerData.validityPeriodRelative);
    }

    private static void encodePrivacyIndicator(com.android.internal.telephony.cdma.sms.BearerData bearerData, com.android.internal.util.BitwiseOutputStream bitwiseOutputStream) throws com.android.internal.util.BitwiseOutputStream.AccessException {
        bitwiseOutputStream.write(8, 1);
        bitwiseOutputStream.write(2, bearerData.privacy);
        bitwiseOutputStream.skip(6);
    }

    private static void encodeLanguageIndicator(com.android.internal.telephony.cdma.sms.BearerData bearerData, com.android.internal.util.BitwiseOutputStream bitwiseOutputStream) throws com.android.internal.util.BitwiseOutputStream.AccessException {
        bitwiseOutputStream.write(8, 1);
        bitwiseOutputStream.write(8, bearerData.language);
    }

    private static void encodeDisplayMode(com.android.internal.telephony.cdma.sms.BearerData bearerData, com.android.internal.util.BitwiseOutputStream bitwiseOutputStream) throws com.android.internal.util.BitwiseOutputStream.AccessException {
        bitwiseOutputStream.write(8, 1);
        bitwiseOutputStream.write(2, bearerData.displayMode);
        bitwiseOutputStream.skip(6);
    }

    private static void encodePriorityIndicator(com.android.internal.telephony.cdma.sms.BearerData bearerData, com.android.internal.util.BitwiseOutputStream bitwiseOutputStream) throws com.android.internal.util.BitwiseOutputStream.AccessException {
        bitwiseOutputStream.write(8, 1);
        bitwiseOutputStream.write(2, bearerData.priority);
        bitwiseOutputStream.skip(6);
    }

    private static void encodeMsgDeliveryAlert(com.android.internal.telephony.cdma.sms.BearerData bearerData, com.android.internal.util.BitwiseOutputStream bitwiseOutputStream) throws com.android.internal.util.BitwiseOutputStream.AccessException {
        bitwiseOutputStream.write(8, 1);
        bitwiseOutputStream.write(2, bearerData.alert);
        bitwiseOutputStream.skip(6);
    }

    private static void encodeScpResults(com.android.internal.telephony.cdma.sms.BearerData bearerData, com.android.internal.util.BitwiseOutputStream bitwiseOutputStream) throws com.android.internal.util.BitwiseOutputStream.AccessException {
        java.util.ArrayList<android.telephony.cdma.CdmaSmsCbProgramResults> arrayList = bearerData.serviceCategoryProgramResults;
        bitwiseOutputStream.write(8, arrayList.size() * 4);
        java.util.Iterator<android.telephony.cdma.CdmaSmsCbProgramResults> it = arrayList.iterator();
        while (it.hasNext()) {
            android.telephony.cdma.CdmaSmsCbProgramResults next = it.next();
            int category = next.getCategory();
            bitwiseOutputStream.write(8, category >> 8);
            bitwiseOutputStream.write(8, category);
            bitwiseOutputStream.write(8, next.getLanguage());
            bitwiseOutputStream.write(4, next.getCategoryResult());
            bitwiseOutputStream.skip(4);
        }
    }

    private static void encodeMsgCenterTimeStamp(com.android.internal.telephony.cdma.sms.BearerData bearerData, com.android.internal.util.BitwiseOutputStream bitwiseOutputStream) throws com.android.internal.util.BitwiseOutputStream.AccessException {
        bitwiseOutputStream.write(8, 6);
        bitwiseOutputStream.writeByteArray(48, bearerData.msgCenterTimeStamp.toByteArray());
    }

    public static byte[] encode(com.android.internal.telephony.cdma.sms.BearerData bearerData) {
        bearerData.hasUserDataHeader = (bearerData.userData == null || bearerData.userData.userDataHeader == null) ? false : true;
        try {
            com.android.internal.util.BitwiseOutputStream bitwiseOutputStream = new com.android.internal.util.BitwiseOutputStream(200);
            bitwiseOutputStream.write(8, 0);
            encodeMessageId(bearerData, bitwiseOutputStream);
            if (bearerData.userData != null) {
                bitwiseOutputStream.write(8, 1);
                encodeUserData(bearerData, bitwiseOutputStream);
            }
            if (bearerData.callbackNumber != null) {
                bitwiseOutputStream.write(8, 14);
                encodeCallbackNumber(bearerData, bitwiseOutputStream);
            }
            if (bearerData.userAckReq || bearerData.deliveryAckReq || bearerData.readAckReq || bearerData.reportReq) {
                bitwiseOutputStream.write(8, 10);
                encodeReplyOption(bearerData, bitwiseOutputStream);
            }
            if (bearerData.numberOfMessages != 0) {
                bitwiseOutputStream.write(8, 11);
                encodeMsgCount(bearerData, bitwiseOutputStream);
            }
            if (bearerData.validityPeriodRelativeSet) {
                bitwiseOutputStream.write(8, 5);
                encodeValidityPeriodRel(bearerData, bitwiseOutputStream);
            }
            if (bearerData.privacyIndicatorSet) {
                bitwiseOutputStream.write(8, 9);
                encodePrivacyIndicator(bearerData, bitwiseOutputStream);
            }
            if (bearerData.languageIndicatorSet) {
                bitwiseOutputStream.write(8, 13);
                encodeLanguageIndicator(bearerData, bitwiseOutputStream);
            }
            if (bearerData.displayModeSet) {
                bitwiseOutputStream.write(8, 15);
                encodeDisplayMode(bearerData, bitwiseOutputStream);
            }
            if (bearerData.priorityIndicatorSet) {
                bitwiseOutputStream.write(8, 8);
                encodePriorityIndicator(bearerData, bitwiseOutputStream);
            }
            if (bearerData.alertIndicatorSet) {
                bitwiseOutputStream.write(8, 12);
                encodeMsgDeliveryAlert(bearerData, bitwiseOutputStream);
            }
            if (bearerData.messageStatusSet) {
                bitwiseOutputStream.write(8, 20);
                encodeMsgStatus(bearerData, bitwiseOutputStream);
            }
            if (bearerData.serviceCategoryProgramResults != null) {
                bitwiseOutputStream.write(8, 19);
                encodeScpResults(bearerData, bitwiseOutputStream);
            }
            if (bearerData.msgCenterTimeStamp != null) {
                bitwiseOutputStream.write(8, 3);
                encodeMsgCenterTimeStamp(bearerData, bitwiseOutputStream);
            }
            return bitwiseOutputStream.toByteArray();
        } catch (com.android.internal.telephony.cdma.sms.BearerData.CodingException e) {
            com.android.telephony.Rlog.e(LOG_TAG, "BearerData encode failed: " + e);
            return null;
        } catch (com.android.internal.util.BitwiseOutputStream.AccessException e2) {
            com.android.telephony.Rlog.e(LOG_TAG, "BearerData encode failed: " + e2);
            return null;
        }
    }

    private static boolean decodeMessageId(com.android.internal.telephony.cdma.sms.BearerData bearerData, com.android.internal.util.BitwiseInputStream bitwiseInputStream) throws com.android.internal.util.BitwiseInputStream.AccessException {
        int read = bitwiseInputStream.read(8) * 8;
        if (read >= 24) {
            read -= 24;
            bearerData.messageType = bitwiseInputStream.read(4);
            bearerData.messageId = bitwiseInputStream.read(8) << 8;
            bearerData.messageId = bitwiseInputStream.read(8) | bearerData.messageId;
            bearerData.hasUserDataHeader = bitwiseInputStream.read(1) == 1;
            bitwiseInputStream.skip(3);
            r3 = true;
        }
        if (!r3 || read > 0) {
            com.android.telephony.Rlog.d(LOG_TAG, "MESSAGE_IDENTIFIER decode " + (r3 ? "succeeded" : "failed") + " (extra bits = " + read + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
        }
        bitwiseInputStream.skip(read);
        return r3;
    }

    private static boolean decodeReserved(com.android.internal.telephony.cdma.sms.BearerData bearerData, com.android.internal.util.BitwiseInputStream bitwiseInputStream, int i) throws com.android.internal.util.BitwiseInputStream.AccessException, com.android.internal.telephony.cdma.sms.BearerData.CodingException {
        boolean z;
        int read = bitwiseInputStream.read(8);
        int i2 = read * 8;
        if (i2 > bitwiseInputStream.available()) {
            z = false;
        } else {
            bitwiseInputStream.skip(i2);
            z = true;
        }
        com.android.telephony.Rlog.d(LOG_TAG, "RESERVED bearer data subparameter " + i + " decode " + (z ? "succeeded" : "failed") + " (param bits = " + i2 + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
        if (!z) {
            throw new com.android.internal.telephony.cdma.sms.BearerData.CodingException("RESERVED bearer data subparameter " + i + " had invalid SUBPARAM_LEN " + read);
        }
        return z;
    }

    private static boolean decodeUserData(com.android.internal.telephony.cdma.sms.BearerData bearerData, com.android.internal.util.BitwiseInputStream bitwiseInputStream) throws com.android.internal.util.BitwiseInputStream.AccessException {
        int read = bitwiseInputStream.read(8) * 8;
        bearerData.userData = new com.android.internal.telephony.cdma.sms.UserData();
        int i = 5;
        bearerData.userData.msgEncoding = bitwiseInputStream.read(5);
        bearerData.userData.msgEncodingSet = true;
        bearerData.userData.msgType = 0;
        if (bearerData.userData.msgEncoding == 1 || bearerData.userData.msgEncoding == 10) {
            bearerData.userData.msgType = bitwiseInputStream.read(8);
            i = 13;
        }
        bearerData.userData.numFields = bitwiseInputStream.read(8);
        bearerData.userData.payload = bitwiseInputStream.readByteArray(read - (i + 8));
        return true;
    }

    private static java.lang.String decodeUtf8(byte[] bArr, int i, int i2) throws com.android.internal.telephony.cdma.sms.BearerData.CodingException {
        return decodeCharset(bArr, i, i2, 1, android.media.tv.SignalingDataInfo.CONTENT_ENCODING_UTF_8);
    }

    private static java.lang.String decodeUtf16(byte[] bArr, int i, int i2) throws com.android.internal.telephony.cdma.sms.BearerData.CodingException {
        return decodeCharset(bArr, i, i2 - (((i % 2) + i) / 2), 2, "utf-16be");
    }

    private static java.lang.String decodeCharset(byte[] bArr, int i, int i2, int i3, java.lang.String str) throws com.android.internal.telephony.cdma.sms.BearerData.CodingException {
        if (i2 < 0 || (i2 * i3) + i > bArr.length) {
            int length = ((bArr.length - i) - (i % i3)) / i3;
            if (length < 0) {
                throw new com.android.internal.telephony.cdma.sms.BearerData.CodingException(str + " decode failed: offset out of range");
            }
            com.android.telephony.Rlog.e(LOG_TAG, str + " decode error: offset = " + i + " numFields = " + i2 + " data.length = " + bArr.length + " maxNumFields = " + length);
            i2 = length;
        }
        try {
            return new java.lang.String(bArr, i, i2 * i3, str);
        } catch (java.io.UnsupportedEncodingException e) {
            throw new com.android.internal.telephony.cdma.sms.BearerData.CodingException(str + " decode failed: " + e);
        }
    }

    private static java.lang.String decode7bitAscii(byte[] bArr, int i, int i2) throws com.android.internal.telephony.cdma.sms.BearerData.CodingException {
        try {
            int i3 = ((i * 8) + 6) / 7;
            int i4 = i2 - i3;
            java.lang.StringBuffer stringBuffer = new java.lang.StringBuffer(i4);
            com.android.internal.util.BitwiseInputStream bitwiseInputStream = new com.android.internal.util.BitwiseInputStream(bArr);
            int i5 = i3 * 7;
            int i6 = (i4 * 7) + i5;
            if (bitwiseInputStream.available() < i6) {
                throw new com.android.internal.telephony.cdma.sms.BearerData.CodingException("insufficient data (wanted " + i6 + " bits, but only have " + bitwiseInputStream.available() + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
            }
            bitwiseInputStream.skip(i5);
            for (int i7 = 0; i7 < i4; i7++) {
                int read = bitwiseInputStream.read(7);
                if (read >= 32 && read <= com.android.internal.telephony.cdma.sms.UserData.ASCII_MAP_MAX_INDEX) {
                    stringBuffer.append(com.android.internal.telephony.cdma.sms.UserData.ASCII_MAP[read - 32]);
                } else if (read == 10) {
                    stringBuffer.append('\n');
                } else if (read == 13) {
                    stringBuffer.append('\r');
                } else {
                    stringBuffer.append(' ');
                }
            }
            return stringBuffer.toString();
        } catch (com.android.internal.util.BitwiseInputStream.AccessException e) {
            throw new com.android.internal.telephony.cdma.sms.BearerData.CodingException("7bit ASCII decode failed: " + e);
        }
    }

    private static java.lang.String decode7bitGsm(byte[] bArr, int i, int i2) throws com.android.internal.telephony.cdma.sms.BearerData.CodingException {
        int i3 = i * 8;
        int i4 = (i3 + 6) / 7;
        java.lang.String gsm7BitPackedToString = com.android.internal.telephony.GsmAlphabet.gsm7BitPackedToString(bArr, i, i2 - i4, (i4 * 7) - i3, 0, 0);
        if (gsm7BitPackedToString == null) {
            throw new com.android.internal.telephony.cdma.sms.BearerData.CodingException("7bit GSM decoding failed");
        }
        return gsm7BitPackedToString;
    }

    private static java.lang.String decodeLatin(byte[] bArr, int i, int i2) throws com.android.internal.telephony.cdma.sms.BearerData.CodingException {
        return decodeCharset(bArr, i, i2, 1, "ISO-8859-1");
    }

    private static java.lang.String decodeShiftJis(byte[] bArr, int i, int i2) throws com.android.internal.telephony.cdma.sms.BearerData.CodingException {
        return decodeCharset(bArr, i, i2, 1, "Shift_JIS");
    }

    private static java.lang.String decodeGsmDcs(byte[] bArr, int i, int i2, int i3) throws com.android.internal.telephony.cdma.sms.BearerData.CodingException {
        if ((i3 & 192) != 0) {
            throw new com.android.internal.telephony.cdma.sms.BearerData.CodingException("unsupported coding group (" + i3 + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
        }
        switch ((i3 >> 2) & 3) {
            case 0:
                return decode7bitGsm(bArr, i, i2);
            case 1:
                return decodeUtf8(bArr, i, i2);
            case 2:
                return decodeUtf16(bArr, i, i2);
            default:
                throw new com.android.internal.telephony.cdma.sms.BearerData.CodingException("unsupported user msgType encoding (" + i3 + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
        }
    }

    private static void decodeUserDataPayload(com.android.internal.telephony.cdma.sms.UserData userData, boolean z) throws com.android.internal.telephony.cdma.sms.BearerData.CodingException {
        int i;
        if (z) {
            int i2 = userData.payload[0] & 255;
            i = i2 + 1 + 0;
            byte[] bArr = new byte[i2];
            java.lang.System.arraycopy(userData.payload, 1, bArr, 0, i2);
            userData.userDataHeader = com.android.internal.telephony.SmsHeader.fromByteArray(bArr);
        } else {
            i = 0;
        }
        switch (userData.msgEncoding) {
            case 0:
                boolean z2 = android.content.res.Resources.getSystem().getBoolean(com.android.internal.R.bool.config_sms_utf8_support);
                byte[] bArr2 = new byte[userData.numFields];
                java.lang.System.arraycopy(userData.payload, 0, bArr2, 0, userData.numFields < userData.payload.length ? userData.numFields : userData.payload.length);
                userData.payload = bArr2;
                if (!z2) {
                    userData.payloadStr = decodeLatin(userData.payload, i, userData.numFields);
                    return;
                } else {
                    userData.payloadStr = decodeUtf8(userData.payload, i, userData.numFields);
                    return;
                }
            case 1:
            case 6:
            case 7:
            default:
                throw new com.android.internal.telephony.cdma.sms.BearerData.CodingException("unsupported user data encoding (" + userData.msgEncoding + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
            case 2:
            case 3:
                userData.payloadStr = decode7bitAscii(userData.payload, i, userData.numFields);
                return;
            case 4:
                userData.payloadStr = decodeUtf16(userData.payload, i, userData.numFields);
                return;
            case 5:
                userData.payloadStr = decodeShiftJis(userData.payload, i, userData.numFields);
                return;
            case 8:
                userData.payloadStr = decodeLatin(userData.payload, i, userData.numFields);
                return;
            case 9:
                userData.payloadStr = decode7bitGsm(userData.payload, i, userData.numFields);
                return;
            case 10:
                userData.payloadStr = decodeGsmDcs(userData.payload, i, userData.numFields, userData.msgType);
                return;
        }
    }

    private static void decodeIs91VoicemailStatus(com.android.internal.telephony.cdma.sms.BearerData bearerData) throws com.android.internal.util.BitwiseInputStream.AccessException, com.android.internal.telephony.cdma.sms.BearerData.CodingException {
        com.android.internal.util.BitwiseInputStream bitwiseInputStream = new com.android.internal.util.BitwiseInputStream(bearerData.userData.payload);
        int available = bitwiseInputStream.available() / 6;
        int i = bearerData.userData.numFields;
        if (available > 14 || available < 3 || available < i) {
            throw new com.android.internal.telephony.cdma.sms.BearerData.CodingException("IS-91 voicemail status decoding failed");
        }
        try {
            java.lang.StringBuffer stringBuffer = new java.lang.StringBuffer(available);
            while (bitwiseInputStream.available() >= 6) {
                stringBuffer.append(com.android.internal.telephony.cdma.sms.UserData.ASCII_MAP[bitwiseInputStream.read(6)]);
            }
            java.lang.String stringBuffer2 = stringBuffer.toString();
            bearerData.numberOfMessages = java.lang.Integer.parseInt(stringBuffer2.substring(0, 2));
            char charAt = stringBuffer2.charAt(2);
            if (charAt == ' ') {
                bearerData.priority = 0;
            } else if (charAt == '!') {
                bearerData.priority = 2;
            } else {
                throw new com.android.internal.telephony.cdma.sms.BearerData.CodingException("IS-91 voicemail status decoding failed: illegal priority setting (" + charAt + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
            }
            bearerData.priorityIndicatorSet = true;
            bearerData.userData.payloadStr = stringBuffer2.substring(3, i - 3);
        } catch (java.lang.IndexOutOfBoundsException e) {
            throw new com.android.internal.telephony.cdma.sms.BearerData.CodingException("IS-91 voicemail status decoding failed: " + e);
        } catch (java.lang.NumberFormatException e2) {
            throw new com.android.internal.telephony.cdma.sms.BearerData.CodingException("IS-91 voicemail status decoding failed: " + e2);
        }
    }

    private static void decodeIs91ShortMessage(com.android.internal.telephony.cdma.sms.BearerData bearerData) throws com.android.internal.util.BitwiseInputStream.AccessException, com.android.internal.telephony.cdma.sms.BearerData.CodingException {
        com.android.internal.util.BitwiseInputStream bitwiseInputStream = new com.android.internal.util.BitwiseInputStream(bearerData.userData.payload);
        int available = bitwiseInputStream.available() / 6;
        int i = bearerData.userData.numFields;
        if (i > 14 || available < i) {
            throw new com.android.internal.telephony.cdma.sms.BearerData.CodingException("IS-91 short message decoding failed");
        }
        java.lang.StringBuffer stringBuffer = new java.lang.StringBuffer(available);
        for (int i2 = 0; i2 < i; i2++) {
            stringBuffer.append(com.android.internal.telephony.cdma.sms.UserData.ASCII_MAP[bitwiseInputStream.read(6)]);
        }
        bearerData.userData.payloadStr = stringBuffer.toString();
    }

    private static void decodeIs91Cli(com.android.internal.telephony.cdma.sms.BearerData bearerData) throws com.android.internal.telephony.cdma.sms.BearerData.CodingException {
        int available = new com.android.internal.util.BitwiseInputStream(bearerData.userData.payload).available() / 4;
        int i = bearerData.userData.numFields;
        if (available > 14 || available < 3 || available < i) {
            throw new com.android.internal.telephony.cdma.sms.BearerData.CodingException("IS-91 voicemail status decoding failed");
        }
        com.android.internal.telephony.cdma.sms.CdmaSmsAddress cdmaSmsAddress = new com.android.internal.telephony.cdma.sms.CdmaSmsAddress();
        cdmaSmsAddress.digitMode = 0;
        cdmaSmsAddress.origBytes = bearerData.userData.payload;
        cdmaSmsAddress.numberOfDigits = (byte) i;
        decodeSmsAddress(cdmaSmsAddress);
        bearerData.callbackNumber = cdmaSmsAddress;
    }

    private static void decodeIs91(com.android.internal.telephony.cdma.sms.BearerData bearerData) throws com.android.internal.util.BitwiseInputStream.AccessException, com.android.internal.telephony.cdma.sms.BearerData.CodingException {
        switch (bearerData.userData.msgType) {
            case 130:
                decodeIs91VoicemailStatus(bearerData);
                return;
            case 131:
            case 133:
                decodeIs91ShortMessage(bearerData);
                return;
            case 132:
                decodeIs91Cli(bearerData);
                return;
            default:
                throw new com.android.internal.telephony.cdma.sms.BearerData.CodingException("unsupported IS-91 message type (" + bearerData.userData.msgType + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
        }
    }

    private static boolean decodeReplyOption(com.android.internal.telephony.cdma.sms.BearerData bearerData, com.android.internal.util.BitwiseInputStream bitwiseInputStream) throws com.android.internal.util.BitwiseInputStream.AccessException {
        boolean z;
        boolean z2;
        boolean z3;
        int read = bitwiseInputStream.read(8) * 8;
        boolean z4 = false;
        if (read >= 8) {
            read -= 8;
            if (bitwiseInputStream.read(1) != 1) {
                z = false;
            } else {
                z = true;
            }
            bearerData.userAckReq = z;
            if (bitwiseInputStream.read(1) != 1) {
                z2 = false;
            } else {
                z2 = true;
            }
            bearerData.deliveryAckReq = z2;
            if (bitwiseInputStream.read(1) != 1) {
                z3 = false;
            } else {
                z3 = true;
            }
            bearerData.readAckReq = z3;
            if (bitwiseInputStream.read(1) == 1) {
                z4 = true;
            }
            bearerData.reportReq = z4;
            bitwiseInputStream.skip(4);
            z4 = true;
        }
        if (!z4 || read > 0) {
            com.android.telephony.Rlog.d(LOG_TAG, "REPLY_OPTION decode " + (z4 ? "succeeded" : "failed") + " (extra bits = " + read + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
        }
        bitwiseInputStream.skip(read);
        return z4;
    }

    private static boolean decodeMsgCount(com.android.internal.telephony.cdma.sms.BearerData bearerData, com.android.internal.util.BitwiseInputStream bitwiseInputStream) throws com.android.internal.util.BitwiseInputStream.AccessException {
        boolean z;
        int read = bitwiseInputStream.read(8) * 8;
        if (read < 8) {
            z = false;
        } else {
            read -= 8;
            bearerData.numberOfMessages = com.android.internal.telephony.uicc.IccUtils.cdmaBcdByteToInt((byte) bitwiseInputStream.read(8));
            z = true;
        }
        if (!z || read > 0) {
            com.android.telephony.Rlog.d(LOG_TAG, "NUMBER_OF_MESSAGES decode " + (z ? "succeeded" : "failed") + " (extra bits = " + read + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
        }
        bitwiseInputStream.skip(read);
        return z;
    }

    private static boolean decodeDepositIndex(com.android.internal.telephony.cdma.sms.BearerData bearerData, com.android.internal.util.BitwiseInputStream bitwiseInputStream) throws com.android.internal.util.BitwiseInputStream.AccessException {
        boolean z;
        int read = bitwiseInputStream.read(8) * 8;
        if (read < 16) {
            z = false;
        } else {
            read -= 16;
            bearerData.depositIndex = bitwiseInputStream.read(8) | (bitwiseInputStream.read(8) << 8);
            z = true;
        }
        if (!z || read > 0) {
            com.android.telephony.Rlog.d(LOG_TAG, "MESSAGE_DEPOSIT_INDEX decode " + (z ? "succeeded" : "failed") + " (extra bits = " + read + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
        }
        bitwiseInputStream.skip(read);
        return z;
    }

    private static java.lang.String decodeDtmfSmsAddress(byte[] bArr, int i) throws com.android.internal.telephony.cdma.sms.BearerData.CodingException {
        java.lang.StringBuffer stringBuffer = new java.lang.StringBuffer(i);
        for (int i2 = 0; i2 < i; i2++) {
            int i3 = (bArr[i2 / 2] >>> (4 - ((i2 % 2) * 4))) & 15;
            if (i3 >= 1 && i3 <= 9) {
                stringBuffer.append(java.lang.Integer.toString(i3, 10));
            } else if (i3 == 10) {
                stringBuffer.append('0');
            } else if (i3 == 11) {
                stringBuffer.append('*');
            } else {
                if (i3 != 12) {
                    throw new com.android.internal.telephony.cdma.sms.BearerData.CodingException("invalid SMS address DTMF code (" + i3 + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
                }
                stringBuffer.append('#');
            }
        }
        return stringBuffer.toString();
    }

    private static void decodeSmsAddress(com.android.internal.telephony.cdma.sms.CdmaSmsAddress cdmaSmsAddress) throws com.android.internal.telephony.cdma.sms.BearerData.CodingException {
        if (cdmaSmsAddress.digitMode == 1) {
            try {
                cdmaSmsAddress.address = new java.lang.String(cdmaSmsAddress.origBytes, 0, cdmaSmsAddress.origBytes.length, "US-ASCII");
            } catch (java.io.UnsupportedEncodingException e) {
                throw new com.android.internal.telephony.cdma.sms.BearerData.CodingException("invalid SMS address ASCII code");
            }
        } else {
            cdmaSmsAddress.address = decodeDtmfSmsAddress(cdmaSmsAddress.origBytes, cdmaSmsAddress.numberOfDigits);
        }
    }

    private static boolean decodeCallbackNumber(com.android.internal.telephony.cdma.sms.BearerData bearerData, com.android.internal.util.BitwiseInputStream bitwiseInputStream) throws com.android.internal.util.BitwiseInputStream.AccessException, com.android.internal.telephony.cdma.sms.BearerData.CodingException {
        byte b;
        int read = bitwiseInputStream.read(8) * 8;
        if (read < 8) {
            bitwiseInputStream.skip(read);
            return false;
        }
        com.android.internal.telephony.cdma.sms.CdmaSmsAddress cdmaSmsAddress = new com.android.internal.telephony.cdma.sms.CdmaSmsAddress();
        cdmaSmsAddress.digitMode = bitwiseInputStream.read(1);
        int i = 4;
        if (cdmaSmsAddress.digitMode != 1) {
            b = 1;
        } else {
            cdmaSmsAddress.ton = bitwiseInputStream.read(3);
            cdmaSmsAddress.numberPlan = bitwiseInputStream.read(4);
            b = (byte) 8;
            i = 8;
        }
        cdmaSmsAddress.numberOfDigits = bitwiseInputStream.read(8);
        int i2 = read - ((byte) (b + 8));
        int i3 = cdmaSmsAddress.numberOfDigits * i;
        int i4 = i2 - i3;
        if (i2 < i3) {
            throw new com.android.internal.telephony.cdma.sms.BearerData.CodingException("CALLBACK_NUMBER subparam encoding size error (remainingBits + " + i2 + ", dataBits + " + i3 + ", paddingBits + " + i4 + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
        }
        cdmaSmsAddress.origBytes = bitwiseInputStream.readByteArray(i3);
        bitwiseInputStream.skip(i4);
        decodeSmsAddress(cdmaSmsAddress);
        bearerData.callbackNumber = cdmaSmsAddress;
        return true;
    }

    private static boolean decodeMsgStatus(com.android.internal.telephony.cdma.sms.BearerData bearerData, com.android.internal.util.BitwiseInputStream bitwiseInputStream) throws com.android.internal.util.BitwiseInputStream.AccessException {
        boolean z;
        int read = bitwiseInputStream.read(8) * 8;
        if (read < 8) {
            z = false;
        } else {
            read -= 8;
            bearerData.errorClass = bitwiseInputStream.read(2);
            bearerData.messageStatus = bitwiseInputStream.read(6);
            z = true;
        }
        if (!z || read > 0) {
            com.android.telephony.Rlog.d(LOG_TAG, "MESSAGE_STATUS decode " + (z ? "succeeded" : "failed") + " (extra bits = " + read + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
        }
        bitwiseInputStream.skip(read);
        bearerData.messageStatusSet = z;
        return z;
    }

    private static boolean decodeMsgCenterTimeStamp(com.android.internal.telephony.cdma.sms.BearerData bearerData, com.android.internal.util.BitwiseInputStream bitwiseInputStream) throws com.android.internal.util.BitwiseInputStream.AccessException {
        boolean z;
        int read = bitwiseInputStream.read(8) * 8;
        if (read < 48) {
            z = false;
        } else {
            read -= 48;
            bearerData.msgCenterTimeStamp = com.android.internal.telephony.cdma.sms.BearerData.TimeStamp.fromByteArray(bitwiseInputStream.readByteArray(48));
            z = true;
        }
        if (!z || read > 0) {
            com.android.telephony.Rlog.d(LOG_TAG, "MESSAGE_CENTER_TIME_STAMP decode " + (z ? "succeeded" : "failed") + " (extra bits = " + read + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
        }
        bitwiseInputStream.skip(read);
        return z;
    }

    private static boolean decodeValidityAbs(com.android.internal.telephony.cdma.sms.BearerData bearerData, com.android.internal.util.BitwiseInputStream bitwiseInputStream) throws com.android.internal.util.BitwiseInputStream.AccessException {
        boolean z;
        int read = bitwiseInputStream.read(8) * 8;
        if (read < 48) {
            z = false;
        } else {
            read -= 48;
            bearerData.validityPeriodAbsolute = com.android.internal.telephony.cdma.sms.BearerData.TimeStamp.fromByteArray(bitwiseInputStream.readByteArray(48));
            z = true;
        }
        if (!z || read > 0) {
            com.android.telephony.Rlog.d(LOG_TAG, "VALIDITY_PERIOD_ABSOLUTE decode " + (z ? "succeeded" : "failed") + " (extra bits = " + read + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
        }
        bitwiseInputStream.skip(read);
        return z;
    }

    private static boolean decodeDeferredDeliveryAbs(com.android.internal.telephony.cdma.sms.BearerData bearerData, com.android.internal.util.BitwiseInputStream bitwiseInputStream) throws com.android.internal.util.BitwiseInputStream.AccessException {
        boolean z;
        int read = bitwiseInputStream.read(8) * 8;
        if (read < 48) {
            z = false;
        } else {
            read -= 48;
            bearerData.deferredDeliveryTimeAbsolute = com.android.internal.telephony.cdma.sms.BearerData.TimeStamp.fromByteArray(bitwiseInputStream.readByteArray(48));
            z = true;
        }
        if (!z || read > 0) {
            com.android.telephony.Rlog.d(LOG_TAG, "DEFERRED_DELIVERY_TIME_ABSOLUTE decode " + (z ? "succeeded" : "failed") + " (extra bits = " + read + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
        }
        bitwiseInputStream.skip(read);
        return z;
    }

    private static boolean decodeValidityRel(com.android.internal.telephony.cdma.sms.BearerData bearerData, com.android.internal.util.BitwiseInputStream bitwiseInputStream) throws com.android.internal.util.BitwiseInputStream.AccessException {
        boolean z;
        int read = bitwiseInputStream.read(8) * 8;
        if (read < 8) {
            z = false;
        } else {
            read -= 8;
            bearerData.deferredDeliveryTimeRelative = bitwiseInputStream.read(8);
            z = true;
        }
        if (!z || read > 0) {
            com.android.telephony.Rlog.d(LOG_TAG, "VALIDITY_PERIOD_RELATIVE decode " + (z ? "succeeded" : "failed") + " (extra bits = " + read + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
        }
        bitwiseInputStream.skip(read);
        bearerData.deferredDeliveryTimeRelativeSet = z;
        return z;
    }

    private static boolean decodeDeferredDeliveryRel(com.android.internal.telephony.cdma.sms.BearerData bearerData, com.android.internal.util.BitwiseInputStream bitwiseInputStream) throws com.android.internal.util.BitwiseInputStream.AccessException {
        boolean z;
        int read = bitwiseInputStream.read(8) * 8;
        if (read < 8) {
            z = false;
        } else {
            read -= 8;
            bearerData.validityPeriodRelative = bitwiseInputStream.read(8);
            z = true;
        }
        if (!z || read > 0) {
            com.android.telephony.Rlog.d(LOG_TAG, "DEFERRED_DELIVERY_TIME_RELATIVE decode " + (z ? "succeeded" : "failed") + " (extra bits = " + read + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
        }
        bitwiseInputStream.skip(read);
        bearerData.validityPeriodRelativeSet = z;
        return z;
    }

    private static boolean decodePrivacyIndicator(com.android.internal.telephony.cdma.sms.BearerData bearerData, com.android.internal.util.BitwiseInputStream bitwiseInputStream) throws com.android.internal.util.BitwiseInputStream.AccessException {
        boolean z;
        int read = bitwiseInputStream.read(8) * 8;
        if (read < 8) {
            z = false;
        } else {
            read -= 8;
            bearerData.privacy = bitwiseInputStream.read(2);
            bitwiseInputStream.skip(6);
            z = true;
        }
        if (!z || read > 0) {
            com.android.telephony.Rlog.d(LOG_TAG, "PRIVACY_INDICATOR decode " + (z ? "succeeded" : "failed") + " (extra bits = " + read + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
        }
        bitwiseInputStream.skip(read);
        bearerData.privacyIndicatorSet = z;
        return z;
    }

    private static boolean decodeLanguageIndicator(com.android.internal.telephony.cdma.sms.BearerData bearerData, com.android.internal.util.BitwiseInputStream bitwiseInputStream) throws com.android.internal.util.BitwiseInputStream.AccessException {
        boolean z;
        int read = bitwiseInputStream.read(8) * 8;
        if (read < 8) {
            z = false;
        } else {
            read -= 8;
            bearerData.language = bitwiseInputStream.read(8);
            z = true;
        }
        if (!z || read > 0) {
            com.android.telephony.Rlog.d(LOG_TAG, "LANGUAGE_INDICATOR decode " + (z ? "succeeded" : "failed") + " (extra bits = " + read + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
        }
        bitwiseInputStream.skip(read);
        bearerData.languageIndicatorSet = z;
        return z;
    }

    private static boolean decodeDisplayMode(com.android.internal.telephony.cdma.sms.BearerData bearerData, com.android.internal.util.BitwiseInputStream bitwiseInputStream) throws com.android.internal.util.BitwiseInputStream.AccessException {
        boolean z;
        int read = bitwiseInputStream.read(8) * 8;
        if (read < 8) {
            z = false;
        } else {
            read -= 8;
            bearerData.displayMode = bitwiseInputStream.read(2);
            bitwiseInputStream.skip(6);
            z = true;
        }
        if (!z || read > 0) {
            com.android.telephony.Rlog.d(LOG_TAG, "DISPLAY_MODE decode " + (z ? "succeeded" : "failed") + " (extra bits = " + read + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
        }
        bitwiseInputStream.skip(read);
        bearerData.displayModeSet = z;
        return z;
    }

    private static boolean decodePriorityIndicator(com.android.internal.telephony.cdma.sms.BearerData bearerData, com.android.internal.util.BitwiseInputStream bitwiseInputStream) throws com.android.internal.util.BitwiseInputStream.AccessException {
        boolean z;
        int read = bitwiseInputStream.read(8) * 8;
        if (read < 8) {
            z = false;
        } else {
            read -= 8;
            bearerData.priority = bitwiseInputStream.read(2);
            bitwiseInputStream.skip(6);
            z = true;
        }
        if (!z || read > 0) {
            com.android.telephony.Rlog.d(LOG_TAG, "PRIORITY_INDICATOR decode " + (z ? "succeeded" : "failed") + " (extra bits = " + read + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
        }
        bitwiseInputStream.skip(read);
        bearerData.priorityIndicatorSet = z;
        return z;
    }

    private static boolean decodeMsgDeliveryAlert(com.android.internal.telephony.cdma.sms.BearerData bearerData, com.android.internal.util.BitwiseInputStream bitwiseInputStream) throws com.android.internal.util.BitwiseInputStream.AccessException {
        boolean z;
        int read = bitwiseInputStream.read(8) * 8;
        if (read < 8) {
            z = false;
        } else {
            read -= 8;
            bearerData.alert = bitwiseInputStream.read(2);
            bitwiseInputStream.skip(6);
            z = true;
        }
        if (!z || read > 0) {
            com.android.telephony.Rlog.d(LOG_TAG, "ALERT_ON_MESSAGE_DELIVERY decode " + (z ? "succeeded" : "failed") + " (extra bits = " + read + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
        }
        bitwiseInputStream.skip(read);
        bearerData.alertIndicatorSet = z;
        return z;
    }

    private static boolean decodeUserResponseCode(com.android.internal.telephony.cdma.sms.BearerData bearerData, com.android.internal.util.BitwiseInputStream bitwiseInputStream) throws com.android.internal.util.BitwiseInputStream.AccessException {
        boolean z;
        int read = bitwiseInputStream.read(8) * 8;
        if (read < 8) {
            z = false;
        } else {
            read -= 8;
            bearerData.userResponseCode = bitwiseInputStream.read(8);
            z = true;
        }
        if (!z || read > 0) {
            com.android.telephony.Rlog.d(LOG_TAG, "USER_RESPONSE_CODE decode " + (z ? "succeeded" : "failed") + " (extra bits = " + read + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
        }
        bitwiseInputStream.skip(read);
        bearerData.userResponseCodeSet = z;
        return z;
    }

    private static boolean decodeServiceCategoryProgramData(com.android.internal.telephony.cdma.sms.BearerData bearerData, com.android.internal.util.BitwiseInputStream bitwiseInputStream) throws com.android.internal.util.BitwiseInputStream.AccessException, com.android.internal.telephony.cdma.sms.BearerData.CodingException {
        if (bitwiseInputStream.available() < 13) {
            throw new com.android.internal.telephony.cdma.sms.BearerData.CodingException("SERVICE_CATEGORY_PROGRAM_DATA decode failed: only " + bitwiseInputStream.available() + " bits available");
        }
        int read = bitwiseInputStream.read(8) * 8;
        int read2 = bitwiseInputStream.read(5);
        int i = read - 5;
        if (bitwiseInputStream.available() < i) {
            throw new com.android.internal.telephony.cdma.sms.BearerData.CodingException("SERVICE_CATEGORY_PROGRAM_DATA decode failed: only " + bitwiseInputStream.available() + " bits available (" + i + " bits expected)");
        }
        java.util.ArrayList<android.telephony.cdma.CdmaSmsCbProgramData> arrayList = new java.util.ArrayList<>();
        boolean z = false;
        while (i >= 48) {
            int read3 = bitwiseInputStream.read(4);
            int read4 = bitwiseInputStream.read(8) | (bitwiseInputStream.read(8) << 8);
            int read5 = bitwiseInputStream.read(8);
            int read6 = bitwiseInputStream.read(8);
            int read7 = bitwiseInputStream.read(4);
            int read8 = bitwiseInputStream.read(8);
            int i2 = i - 48;
            int bitsForNumFields = getBitsForNumFields(read2, read8);
            if (i2 < bitsForNumFields) {
                throw new com.android.internal.telephony.cdma.sms.BearerData.CodingException("category name is " + bitsForNumFields + " bits in length, but there are only " + i2 + " bits available");
            }
            com.android.internal.telephony.cdma.sms.UserData userData = new com.android.internal.telephony.cdma.sms.UserData();
            userData.msgEncoding = read2;
            userData.msgEncodingSet = true;
            userData.numFields = read8;
            userData.payload = bitwiseInputStream.readByteArray(bitsForNumFields);
            i = i2 - bitsForNumFields;
            decodeUserDataPayload(userData, false);
            arrayList.add(new android.telephony.cdma.CdmaSmsCbProgramData(read3, read4, read5, read6, read7, userData.payloadStr));
            z = true;
        }
        if (!z || i > 0) {
            com.android.telephony.Rlog.d(LOG_TAG, "SERVICE_CATEGORY_PROGRAM_DATA decode " + (z ? "succeeded" : "failed") + " (extra bits = " + i + ')');
        }
        bitwiseInputStream.skip(i);
        bearerData.serviceCategoryProgramData = arrayList;
        return z;
    }

    private static int serviceCategoryToCmasMessageClass(int i) {
        switch (i) {
            case 4096:
                return 0;
            case 4097:
                return 1;
            case 4098:
                return 2;
            case 4099:
                return 3;
            case 4100:
                return 4;
            default:
                return -1;
        }
    }

    private static int getBitsForNumFields(int i, int i2) throws com.android.internal.telephony.cdma.sms.BearerData.CodingException {
        switch (i) {
            case 0:
            case 5:
            case 6:
            case 7:
            case 8:
                return i2 * 8;
            case 1:
            default:
                throw new com.android.internal.telephony.cdma.sms.BearerData.CodingException("unsupported message encoding (" + i + ')');
            case 2:
            case 3:
            case 9:
                return i2 * 7;
            case 4:
                return i2 * 16;
        }
    }

    private static void decodeCmasUserData(com.android.internal.telephony.cdma.sms.BearerData bearerData, int i) throws com.android.internal.util.BitwiseInputStream.AccessException, com.android.internal.telephony.cdma.sms.BearerData.CodingException {
        int i2;
        com.android.internal.util.BitwiseInputStream bitwiseInputStream = new com.android.internal.util.BitwiseInputStream(bearerData.userData.payload);
        if (bitwiseInputStream.available() < 8) {
            throw new com.android.internal.telephony.cdma.sms.BearerData.CodingException("emergency CB with no CMAE_protocol_version");
        }
        int read = bitwiseInputStream.read(8);
        if (read != 0) {
            throw new com.android.internal.telephony.cdma.sms.BearerData.CodingException("unsupported CMAE_protocol_version " + read);
        }
        int serviceCategoryToCmasMessageClass = serviceCategoryToCmasMessageClass(i);
        int i3 = -1;
        int i4 = -1;
        int i5 = -1;
        int i6 = -1;
        int i7 = -1;
        while (bitwiseInputStream.available() >= 16) {
            int read2 = bitwiseInputStream.read(8);
            int read3 = bitwiseInputStream.read(8);
            switch (read2) {
                case 0:
                    com.android.internal.telephony.cdma.sms.UserData userData = new com.android.internal.telephony.cdma.sms.UserData();
                    userData.msgEncoding = bitwiseInputStream.read(5);
                    userData.msgEncodingSet = true;
                    userData.msgType = 0;
                    switch (userData.msgEncoding) {
                        case 0:
                        case 8:
                            i2 = read3 - 1;
                            break;
                        case 1:
                        case 5:
                        case 6:
                        case 7:
                        default:
                            i2 = 0;
                            break;
                        case 2:
                        case 3:
                        case 9:
                            i2 = ((read3 * 8) - 5) / 7;
                            break;
                        case 4:
                            i2 = (read3 - 1) / 2;
                            break;
                    }
                    userData.numFields = i2;
                    userData.payload = bitwiseInputStream.readByteArray((read3 * 8) - 5);
                    decodeUserDataPayload(userData, false);
                    bearerData.userData = userData;
                    break;
                case 1:
                    i3 = bitwiseInputStream.read(8);
                    i4 = bitwiseInputStream.read(8);
                    i5 = bitwiseInputStream.read(4);
                    i6 = bitwiseInputStream.read(4);
                    i7 = bitwiseInputStream.read(4);
                    bitwiseInputStream.skip((read3 * 8) - 28);
                    break;
                default:
                    com.android.telephony.Rlog.w(LOG_TAG, "skipping unsupported CMAS record type " + read2);
                    bitwiseInputStream.skip(read3 * 8);
                    break;
            }
        }
        bearerData.cmasWarningInfo = new android.telephony.SmsCbCmasInfo(serviceCategoryToCmasMessageClass, i3, i4, i5, i6, i7);
    }

    public static com.android.internal.telephony.cdma.sms.BearerData decode(byte[] bArr) {
        return decode(bArr, 0);
    }

    private static boolean isCmasAlertCategory(int i) {
        return i >= 4096 && i <= 4351;
    }

    public static com.android.internal.telephony.cdma.sms.BearerData decode(byte[] bArr, int i) {
        boolean decodeMessageId;
        try {
            com.android.internal.util.BitwiseInputStream bitwiseInputStream = new com.android.internal.util.BitwiseInputStream(bArr);
            com.android.internal.telephony.cdma.sms.BearerData bearerData = new com.android.internal.telephony.cdma.sms.BearerData();
            int i2 = 0;
            while (bitwiseInputStream.available() > 0) {
                int read = bitwiseInputStream.read(8);
                int i3 = 1 << read;
                if ((i2 & i3) != 0 && read >= 0 && read <= 23) {
                    throw new com.android.internal.telephony.cdma.sms.BearerData.CodingException("illegal duplicate subparameter (" + read + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
                }
                switch (read) {
                    case 0:
                        decodeMessageId = decodeMessageId(bearerData, bitwiseInputStream);
                        break;
                    case 1:
                        decodeMessageId = decodeUserData(bearerData, bitwiseInputStream);
                        break;
                    case 2:
                        decodeMessageId = decodeUserResponseCode(bearerData, bitwiseInputStream);
                        break;
                    case 3:
                        decodeMessageId = decodeMsgCenterTimeStamp(bearerData, bitwiseInputStream);
                        break;
                    case 4:
                        decodeMessageId = decodeValidityAbs(bearerData, bitwiseInputStream);
                        break;
                    case 5:
                        decodeMessageId = decodeValidityRel(bearerData, bitwiseInputStream);
                        break;
                    case 6:
                        decodeMessageId = decodeDeferredDeliveryAbs(bearerData, bitwiseInputStream);
                        break;
                    case 7:
                        decodeMessageId = decodeDeferredDeliveryRel(bearerData, bitwiseInputStream);
                        break;
                    case 8:
                        decodeMessageId = decodePriorityIndicator(bearerData, bitwiseInputStream);
                        break;
                    case 9:
                        decodeMessageId = decodePrivacyIndicator(bearerData, bitwiseInputStream);
                        break;
                    case 10:
                        decodeMessageId = decodeReplyOption(bearerData, bitwiseInputStream);
                        break;
                    case 11:
                        decodeMessageId = decodeMsgCount(bearerData, bitwiseInputStream);
                        break;
                    case 12:
                        decodeMessageId = decodeMsgDeliveryAlert(bearerData, bitwiseInputStream);
                        break;
                    case 13:
                        decodeMessageId = decodeLanguageIndicator(bearerData, bitwiseInputStream);
                        break;
                    case 14:
                        decodeMessageId = decodeCallbackNumber(bearerData, bitwiseInputStream);
                        break;
                    case 15:
                        decodeMessageId = decodeDisplayMode(bearerData, bitwiseInputStream);
                        break;
                    case 16:
                    case 19:
                    default:
                        decodeMessageId = decodeReserved(bearerData, bitwiseInputStream, read);
                        break;
                    case 17:
                        decodeMessageId = decodeDepositIndex(bearerData, bitwiseInputStream);
                        break;
                    case 18:
                        decodeMessageId = decodeServiceCategoryProgramData(bearerData, bitwiseInputStream);
                        break;
                    case 20:
                        decodeMessageId = decodeMsgStatus(bearerData, bitwiseInputStream);
                        break;
                }
                if (decodeMessageId && read >= 0 && read <= 23) {
                    i2 |= i3;
                }
            }
            if ((i2 & 1) == 0) {
                throw new com.android.internal.telephony.cdma.sms.BearerData.CodingException("missing MESSAGE_IDENTIFIER subparam");
            }
            if (bearerData.userData != null) {
                if (isCmasAlertCategory(i)) {
                    decodeCmasUserData(bearerData, i);
                } else if (bearerData.userData.msgEncoding == 1) {
                    if (((i2 ^ 1) ^ 2) != 0) {
                        com.android.telephony.Rlog.e(LOG_TAG, "IS-91 must occur without extra subparams (" + i2 + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
                    }
                    decodeIs91(bearerData);
                } else {
                    decodeUserDataPayload(bearerData.userData, bearerData.hasUserDataHeader);
                }
            }
            return bearerData;
        } catch (com.android.internal.telephony.cdma.sms.BearerData.CodingException e) {
            com.android.telephony.Rlog.e(LOG_TAG, "BearerData decode failed: " + e);
            return null;
        } catch (com.android.internal.util.BitwiseInputStream.AccessException e2) {
            com.android.telephony.Rlog.e(LOG_TAG, "BearerData decode failed: " + e2);
            return null;
        }
    }
}
