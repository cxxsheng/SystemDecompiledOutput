package com.android.internal.telephony.cdma.sms;

/* loaded from: classes5.dex */
public class UserData {
    public static final int ASCII_CR_INDEX = 13;
    public static final int ASCII_MAP_BASE_INDEX = 32;
    public static final int ASCII_MAP_MAX_INDEX;
    public static final int ASCII_NL_INDEX = 10;
    public static final int ENCODING_7BIT_ASCII = 2;
    public static final int ENCODING_GSM_7BIT_ALPHABET = 9;
    public static final int ENCODING_GSM_DCS = 10;
    public static final int ENCODING_GSM_DCS_16BIT = 2;
    public static final int ENCODING_GSM_DCS_7BIT = 0;
    public static final int ENCODING_GSM_DCS_8BIT = 1;
    public static final int ENCODING_IA5 = 3;
    public static final int ENCODING_IS91_EXTENDED_PROTOCOL = 1;
    public static final int ENCODING_KOREAN = 6;
    public static final int ENCODING_LATIN = 8;
    public static final int ENCODING_LATIN_HEBREW = 7;
    public static final int ENCODING_OCTET = 0;
    public static final int ENCODING_SHIFT_JIS = 5;
    public static final int ENCODING_UNICODE_16 = 4;
    public static final int IS91_MSG_TYPE_CLI = 132;
    public static final int IS91_MSG_TYPE_SHORT_MESSAGE = 133;
    public static final int IS91_MSG_TYPE_SHORT_MESSAGE_FULL = 131;
    public static final int IS91_MSG_TYPE_VOICEMAIL_STATUS = 130;
    public static final int PRINTABLE_ASCII_MIN_INDEX = 32;
    static final byte UNENCODABLE_7_BIT_CHAR = 32;
    public int msgEncoding;
    public boolean msgEncodingSet = false;
    public int msgType;
    public int numFields;
    public int paddingBits;
    public byte[] payload;
    public java.lang.String payloadStr;
    public com.android.internal.telephony.SmsHeader userDataHeader;
    public static final char[] ASCII_MAP = {' ', '!', '\"', '#', '$', '%', '&', android.text.format.DateFormat.QUOTE, '(', ')', '*', '+', ',', '-', '.', '/', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', com.android.internal.accessibility.common.ShortcutConstants.SERVICES_SEPARATOR, ';', '<', '=', '>', '?', '@', android.text.format.DateFormat.CAPITAL_AM_PM, 'B', 'C', 'D', android.text.format.DateFormat.DAY, 'F', 'G', 'H', 'I', 'J', 'K', android.text.format.DateFormat.STANDALONE_MONTH, android.text.format.DateFormat.MONTH, android.telephony.PhoneNumberUtils.WILD, 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '[', '\\', ']', '^', '_', '`', android.text.format.DateFormat.AM_PM, 'b', 'c', android.text.format.DateFormat.DATE, 'e', 'f', 'g', android.text.format.DateFormat.HOUR, 'i', 'j', android.text.format.DateFormat.HOUR_OF_DAY, 'l', android.text.format.DateFormat.MINUTE, 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', com.android.internal.transition.EpicenterTranslateClipReveal.StateProperty.TARGET_X, 'y', android.text.format.DateFormat.TIME_ZONE, '{', '|', '}', '~'};
    public static final android.util.SparseIntArray charToAscii = new android.util.SparseIntArray();

    static {
        for (int i = 0; i < ASCII_MAP.length; i++) {
            charToAscii.put(ASCII_MAP[i], i + 32);
        }
        charToAscii.put(10, 10);
        charToAscii.put(13, 13);
        ASCII_MAP_MAX_INDEX = (ASCII_MAP.length + 32) - 1;
    }

    public static byte[] stringToAscii(java.lang.String str) {
        int length = str.length();
        byte[] bArr = new byte[length];
        for (int i = 0; i < length; i++) {
            int i2 = charToAscii.get(str.charAt(i), -1);
            if (i2 == -1) {
                return null;
            }
            bArr[i] = (byte) i2;
        }
        return bArr;
    }

    public java.lang.String toString() {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append("UserData ");
        sb.append("{ msgEncoding=" + (this.msgEncodingSet ? java.lang.Integer.valueOf(this.msgEncoding) : "unset"));
        sb.append(", msgType=" + this.msgType);
        sb.append(", paddingBits=" + this.paddingBits);
        sb.append(", numFields=" + this.numFields);
        sb.append(", userDataHeader=" + this.userDataHeader);
        sb.append(", payload='" + com.android.internal.util.HexDump.toHexString(this.payload) + "'");
        sb.append(", payloadStr='" + this.payloadStr + "'");
        sb.append(" }");
        return sb.toString();
    }
}
