package com.android.internal.telephony;

/* loaded from: classes5.dex */
public abstract class SmsMessageBase {
    public static final java.util.regex.Pattern NAME_ADDR_EMAIL_PATTERN = java.util.regex.Pattern.compile("\\s*(\"[^\"]*\"|[^<>\"]+)\\s*<([^<>]+)>\\s*");
    protected java.lang.String mEmailBody;
    protected java.lang.String mEmailFrom;
    protected boolean mIsEmail;
    protected boolean mIsMwi;
    protected java.lang.String mMessageBody;
    public int mMessageRef;
    protected boolean mMwiDontStore;
    protected boolean mMwiSense;
    protected com.android.internal.telephony.SmsAddress mOriginatingAddress;
    protected byte[] mPdu;
    protected java.lang.String mPseudoSubject;
    protected com.android.internal.telephony.SmsAddress mRecipientAddress;
    protected java.lang.String mScAddress;
    protected long mScTimeMillis;
    protected byte[] mUserData;
    protected com.android.internal.telephony.SmsHeader mUserDataHeader;
    protected int mReceivedEncodingType = 0;
    protected int mStatusOnIcc = -1;
    protected int mIndexOnIcc = -1;

    public abstract com.android.internal.telephony.SmsConstants.MessageClass getMessageClass();

    public abstract int getProtocolIdentifier();

    public abstract int getStatus();

    public abstract boolean isCphsMwiMessage();

    public abstract boolean isMWIClearMessage();

    public abstract boolean isMWISetMessage();

    public abstract boolean isMwiDontStore();

    public abstract boolean isReplace();

    public abstract boolean isReplyPathPresent();

    public abstract boolean isStatusReportMessage();

    public static abstract class SubmitPduBase {
        public byte[] encodedMessage;
        public byte[] encodedScAddress;

        public java.lang.String toString() {
            return "SubmitPdu: encodedScAddress = " + java.util.Arrays.toString(this.encodedScAddress) + ", encodedMessage = " + java.util.Arrays.toString(this.encodedMessage);
        }
    }

    public java.lang.String getServiceCenterAddress() {
        return this.mScAddress;
    }

    public java.lang.String getOriginatingAddress() {
        if (this.mOriginatingAddress == null) {
            return null;
        }
        return this.mOriginatingAddress.getAddressString();
    }

    public java.lang.String getDisplayOriginatingAddress() {
        if (this.mIsEmail) {
            return this.mEmailFrom;
        }
        return getOriginatingAddress();
    }

    public java.lang.String getMessageBody() {
        return this.mMessageBody;
    }

    public java.lang.String getDisplayMessageBody() {
        if (this.mIsEmail) {
            return this.mEmailBody;
        }
        return getMessageBody();
    }

    public java.lang.String getPseudoSubject() {
        return this.mPseudoSubject == null ? "" : this.mPseudoSubject;
    }

    public long getTimestampMillis() {
        return this.mScTimeMillis;
    }

    public boolean isEmail() {
        return this.mIsEmail;
    }

    public java.lang.String getEmailBody() {
        return this.mEmailBody;
    }

    public java.lang.String getEmailFrom() {
        return this.mEmailFrom;
    }

    public byte[] getUserData() {
        return this.mUserData;
    }

    public com.android.internal.telephony.SmsHeader getUserDataHeader() {
        return this.mUserDataHeader;
    }

    public byte[] getPdu() {
        return this.mPdu;
    }

    public int getStatusOnIcc() {
        return this.mStatusOnIcc;
    }

    public int getIndexOnIcc() {
        return this.mIndexOnIcc;
    }

    protected void parseMessageBody() {
        if (this.mOriginatingAddress != null && this.mOriginatingAddress.couldBeEmailGateway()) {
            extractEmailAddressFromMessageBody();
        }
    }

    private static java.lang.String extractAddrSpec(java.lang.String str) {
        java.util.regex.Matcher matcher = NAME_ADDR_EMAIL_PATTERN.matcher(str);
        if (matcher.matches()) {
            return matcher.group(2);
        }
        return str;
    }

    public static boolean isEmailAddress(java.lang.String str) {
        if (android.text.TextUtils.isEmpty(str)) {
            return false;
        }
        return android.util.Patterns.EMAIL_ADDRESS.matcher(extractAddrSpec(str)).matches();
    }

    protected void extractEmailAddressFromMessageBody() {
        java.lang.String[] split = this.mMessageBody.split("( /)|( )", 2);
        if (split.length < 2) {
            return;
        }
        this.mEmailFrom = split[0];
        this.mEmailBody = split[1];
        this.mIsEmail = isEmailAddress(this.mEmailFrom);
    }

    /* JADX WARN: Code restructure failed: missing block: B:16:?, code lost:
    
        return r0;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static int findNextUnicodePosition(int i, int i2, java.lang.CharSequence charSequence) {
        int min = java.lang.Math.min((i2 / 2) + i, charSequence.length());
        if (min < charSequence.length()) {
            java.text.BreakIterator characterInstance = java.text.BreakIterator.getCharacterInstance();
            characterInstance.setText(charSequence.toString());
            if (!characterInstance.isBoundary(min)) {
                int preceding = characterInstance.preceding(min);
                while (true) {
                    int i3 = preceding + 4;
                    if (i3 > min || !isRegionalIndicatorSymbol(java.lang.Character.codePointAt(charSequence, preceding)) || !isRegionalIndicatorSymbol(java.lang.Character.codePointAt(charSequence, preceding + 2))) {
                        break;
                    }
                    preceding = i3;
                }
                if (java.lang.Character.isHighSurrogate(charSequence.charAt(min - 1))) {
                    return min - 1;
                }
                return min;
            }
            return min;
        }
        return min;
    }

    private static boolean isRegionalIndicatorSymbol(int i) {
        return 127462 <= i && i <= 127487;
    }

    public static com.android.internal.telephony.GsmAlphabet.TextEncodingDetails calcUnicodeEncodingDetails(java.lang.CharSequence charSequence) {
        int i;
        com.android.internal.telephony.GsmAlphabet.TextEncodingDetails textEncodingDetails = new com.android.internal.telephony.GsmAlphabet.TextEncodingDetails();
        int length = charSequence.length() * 2;
        textEncodingDetails.codeUnitSize = 3;
        textEncodingDetails.codeUnitCount = charSequence.length();
        if (length > 140) {
            if (!android.telephony.SmsMessage.hasEmsSupport() && length <= 1188) {
                i = 132;
            } else {
                i = 134;
            }
            int i2 = 0;
            int i3 = 0;
            while (i2 < charSequence.length()) {
                int findNextUnicodePosition = findNextUnicodePosition(i2, i, charSequence);
                if (findNextUnicodePosition == charSequence.length()) {
                    textEncodingDetails.codeUnitsRemaining = (i2 + (i / 2)) - charSequence.length();
                }
                i3++;
                i2 = findNextUnicodePosition;
            }
            textEncodingDetails.msgCount = i3;
        } else {
            textEncodingDetails.msgCount = 1;
            textEncodingDetails.codeUnitsRemaining = (140 - length) / 2;
        }
        return textEncodingDetails;
    }

    public java.lang.String getRecipientAddress() {
        if (this.mRecipientAddress == null) {
            return null;
        }
        return this.mRecipientAddress.getAddressString();
    }

    public int getReceivedEncodingType() {
        return this.mReceivedEncodingType;
    }
}
