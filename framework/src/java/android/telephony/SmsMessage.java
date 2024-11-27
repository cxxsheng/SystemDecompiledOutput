package android.telephony;

/* loaded from: classes3.dex */
public class SmsMessage {
    public static final int ENCODING_16BIT = 3;
    public static final int ENCODING_7BIT = 1;
    public static final int ENCODING_8BIT = 2;
    public static final int ENCODING_KSC5601 = 4;
    public static final int ENCODING_UNKNOWN = 0;
    public static final java.lang.String FORMAT_3GPP = "3gpp";
    public static final java.lang.String FORMAT_3GPP2 = "3gpp2";
    private static final java.lang.String LOG_TAG = "SmsMessage";
    public static final int MAX_USER_DATA_BYTES = 140;
    public static final int MAX_USER_DATA_BYTES_WITH_HEADER = 134;
    public static final int MAX_USER_DATA_SEPTETS = 160;
    public static final int MAX_USER_DATA_SEPTETS_WITH_HEADER = 153;
    private int mSubId = 0;
    public com.android.internal.telephony.SmsMessageBase mWrappedSmsMessage;
    private static android.telephony.SmsMessage.NoEmsSupportConfig[] mNoEmsSupportConfigList = null;
    private static boolean mIsNoEmsSupportConfigListLoaded = false;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface EncodingSize {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface Format {
    }

    public enum MessageClass {
        UNKNOWN,
        CLASS_0,
        CLASS_1,
        CLASS_2,
        CLASS_3
    }

    public void setSubId(int i) {
        this.mSubId = i;
    }

    public int getSubId() {
        return this.mSubId;
    }

    public static class SubmitPdu {
        public byte[] encodedMessage;
        public byte[] encodedScAddress;

        public java.lang.String toString() {
            return "SubmitPdu: encodedScAddress = " + java.util.Arrays.toString(this.encodedScAddress) + ", encodedMessage = " + java.util.Arrays.toString(this.encodedMessage);
        }

        protected SubmitPdu(com.android.internal.telephony.SmsMessageBase.SubmitPduBase submitPduBase) {
            this.encodedMessage = submitPduBase.encodedMessage;
            this.encodedScAddress = submitPduBase.encodedScAddress;
        }
    }

    public SmsMessage(com.android.internal.telephony.SmsMessageBase smsMessageBase) {
        this.mWrappedSmsMessage = smsMessageBase;
    }

    @java.lang.Deprecated
    public static android.telephony.SmsMessage createFromPdu(byte[] bArr) {
        return createFromPdu(bArr, 2 == android.telephony.TelephonyManager.getDefault().getCurrentPhoneType() ? "3gpp2" : "3gpp");
    }

    public static android.telephony.SmsMessage createFromPdu(byte[] bArr, java.lang.String str) {
        return createFromPdu(bArr, str, true);
    }

    private static android.telephony.SmsMessage createFromPdu(byte[] bArr, java.lang.String str, boolean z) {
        com.android.internal.telephony.SmsMessageBase createFromPdu;
        if (bArr == null) {
            com.android.telephony.Rlog.i(LOG_TAG, "createFromPdu(): pdu is null");
            return null;
        }
        java.lang.String str2 = "3gpp2".equals(str) ? "3gpp" : "3gpp2";
        if ("3gpp2".equals(str)) {
            createFromPdu = com.android.internal.telephony.cdma.SmsMessage.createFromPdu(bArr);
        } else if ("3gpp".equals(str)) {
            createFromPdu = com.android.internal.telephony.gsm.SmsMessage.createFromPdu(bArr);
        } else {
            com.android.telephony.Rlog.e(LOG_TAG, "createFromPdu(): unsupported message format " + str);
            return null;
        }
        if (createFromPdu != null) {
            return new android.telephony.SmsMessage(createFromPdu);
        }
        if (z) {
            return createFromPdu(bArr, str2, false);
        }
        com.android.telephony.Rlog.e(LOG_TAG, "createFromPdu(): wrappedMessage is null");
        return null;
    }

    public static android.telephony.SmsMessage createFromEfRecord(int i, byte[] bArr) {
        return createFromEfRecord(i, bArr, android.telephony.SmsManager.getDefaultSmsSubscriptionId());
    }

    public static android.telephony.SmsMessage createFromEfRecord(int i, byte[] bArr, int i2) {
        com.android.internal.telephony.SmsMessageBase createFromEfRecord;
        if (isCdmaVoice(i2)) {
            createFromEfRecord = com.android.internal.telephony.cdma.SmsMessage.createFromEfRecord(i, bArr);
        } else {
            createFromEfRecord = com.android.internal.telephony.gsm.SmsMessage.createFromEfRecord(i, bArr);
        }
        if (createFromEfRecord != null) {
            return new android.telephony.SmsMessage(createFromEfRecord);
        }
        return null;
    }

    @android.annotation.SystemApi
    public static android.telephony.SmsMessage createFromNativeSmsSubmitPdu(byte[] bArr, boolean z) {
        com.android.internal.telephony.SmsMessageBase createFromEfRecord;
        if (z) {
            createFromEfRecord = com.android.internal.telephony.cdma.SmsMessage.createFromEfRecord(0, bArr);
        } else {
            createFromEfRecord = com.android.internal.telephony.gsm.SmsMessage.createFromEfRecord(0, bArr);
        }
        if (createFromEfRecord != null) {
            return new android.telephony.SmsMessage(createFromEfRecord);
        }
        return null;
    }

    public static int getTPLayerLengthForPDU(java.lang.String str) {
        if (isCdmaVoice()) {
            return com.android.internal.telephony.cdma.SmsMessage.getTPLayerLengthForPDU(str);
        }
        return com.android.internal.telephony.gsm.SmsMessage.getTPLayerLengthForPDU(str);
    }

    public static int[] calculateLength(java.lang.CharSequence charSequence, boolean z) {
        return calculateLength(charSequence, z, android.telephony.SmsManager.getDefaultSmsSubscriptionId());
    }

    public static int[] calculateLength(java.lang.CharSequence charSequence, boolean z, int i) {
        com.android.internal.telephony.GsmAlphabet.TextEncodingDetails calculateLength;
        if (useCdmaFormatForMoSms(i)) {
            calculateLength = com.android.internal.telephony.cdma.SmsMessage.calculateLength(charSequence, z, true);
        } else {
            calculateLength = com.android.internal.telephony.gsm.SmsMessage.calculateLength(charSequence, z);
        }
        return new int[]{calculateLength.msgCount, calculateLength.codeUnitCount, calculateLength.codeUnitsRemaining, calculateLength.codeUnitSize, calculateLength.languageTable, calculateLength.languageShiftTable};
    }

    public static java.util.ArrayList<java.lang.String> fragmentText(java.lang.String str) {
        return fragmentText(str, android.telephony.SmsManager.getDefaultSmsSubscriptionId());
    }

    public static java.util.ArrayList<java.lang.String> fragmentText(java.lang.String str, int i) {
        com.android.internal.telephony.GsmAlphabet.TextEncodingDetails calculateLength;
        int i2;
        java.lang.String str2;
        int findNextUnicodePosition;
        int i3;
        boolean useCdmaFormatForMoSms = useCdmaFormatForMoSms(i);
        int i4 = 0;
        if (useCdmaFormatForMoSms) {
            calculateLength = com.android.internal.telephony.cdma.SmsMessage.calculateLength(str, false, true);
        } else {
            calculateLength = com.android.internal.telephony.gsm.SmsMessage.calculateLength(str, false);
        }
        if (calculateLength.codeUnitSize == 1) {
            if (calculateLength.languageTable != 0 && calculateLength.languageShiftTable != 0) {
                i3 = 7;
            } else if (calculateLength.languageTable != 0 || calculateLength.languageShiftTable != 0) {
                i3 = 4;
            } else {
                i3 = 0;
            }
            if (calculateLength.msgCount > 1) {
                i3 += 6;
            }
            if (i3 != 0) {
                i3++;
            }
            i2 = 160 - i3;
        } else if (calculateLength.msgCount > 1) {
            if (!hasEmsSupport() && calculateLength.msgCount < 10) {
                i2 = 132;
            } else {
                i2 = 134;
            }
        } else {
            i2 = 140;
        }
        if (!android.content.res.Resources.getSystem().getBoolean(com.android.internal.R.bool.config_sms_force_7bit_encoding)) {
            str2 = null;
        } else {
            str2 = com.android.internal.telephony.Sms7BitEncodingTranslator.translate(str, useCdmaFormatForMoSms && calculateLength.msgCount == 1);
        }
        if (!android.text.TextUtils.isEmpty(str2)) {
            str = str2;
        }
        int length = str.length();
        java.util.ArrayList<java.lang.String> arrayList = new java.util.ArrayList<>(calculateLength.msgCount);
        while (i4 < length) {
            if (calculateLength.codeUnitSize == 1) {
                if (useCdmaFormatForMoSms && calculateLength.msgCount == 1) {
                    findNextUnicodePosition = java.lang.Math.min(i2, length - i4) + i4;
                } else {
                    findNextUnicodePosition = com.android.internal.telephony.GsmAlphabet.findGsmSeptetLimitIndex(str, i4, i2, calculateLength.languageTable, calculateLength.languageShiftTable);
                }
            } else {
                findNextUnicodePosition = com.android.internal.telephony.SmsMessageBase.findNextUnicodePosition(i4, i2, str);
            }
            if (findNextUnicodePosition <= i4 || findNextUnicodePosition > length) {
                com.android.telephony.Rlog.e(LOG_TAG, "fragmentText failed (" + i4 + " >= " + findNextUnicodePosition + " or " + findNextUnicodePosition + " >= " + length + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
                break;
            }
            arrayList.add(str.substring(i4, findNextUnicodePosition));
            i4 = findNextUnicodePosition;
        }
        return arrayList;
    }

    public static int[] calculateLength(java.lang.String str, boolean z) {
        return calculateLength((java.lang.CharSequence) str, z);
    }

    public static int[] calculateLength(java.lang.String str, boolean z, int i) {
        return calculateLength((java.lang.CharSequence) str, z, i);
    }

    public static android.telephony.SmsMessage.SubmitPdu getSubmitPdu(java.lang.String str, java.lang.String str2, java.lang.String str3, boolean z) {
        return getSubmitPdu(str, str2, str3, z, android.telephony.SmsManager.getDefaultSmsSubscriptionId());
    }

    public static android.telephony.SmsMessage.SubmitPdu getSubmitPdu(java.lang.String str, java.lang.String str2, java.lang.String str3, boolean z, int i) {
        com.android.internal.telephony.SmsMessageBase.SubmitPduBase submitPdu;
        if (useCdmaFormatForMoSms(i)) {
            submitPdu = com.android.internal.telephony.cdma.SmsMessage.getSubmitPdu(str, str2, str3, z, (com.android.internal.telephony.SmsHeader) null);
        } else {
            submitPdu = com.android.internal.telephony.gsm.SmsMessage.getSubmitPdu(str, str2, str3, z);
        }
        if (submitPdu != null) {
            return new android.telephony.SmsMessage.SubmitPdu(submitPdu);
        }
        return null;
    }

    public static android.telephony.SmsMessage.SubmitPdu getSubmitPdu(java.lang.String str, java.lang.String str2, short s, byte[] bArr, boolean z) {
        com.android.internal.telephony.SmsMessageBase.SubmitPduBase submitPdu;
        if (useCdmaFormatForMoSms()) {
            submitPdu = com.android.internal.telephony.cdma.SmsMessage.getSubmitPdu(str, str2, s, bArr, z);
        } else {
            submitPdu = com.android.internal.telephony.gsm.SmsMessage.getSubmitPdu(str, str2, s, bArr, z);
        }
        if (submitPdu != null) {
            return new android.telephony.SmsMessage.SubmitPdu(submitPdu);
        }
        return null;
    }

    @android.annotation.SystemApi
    public static android.telephony.SmsMessage.SubmitPdu getSmsPdu(int i, int i2, java.lang.String str, java.lang.String str2, java.lang.String str3, long j) {
        com.android.internal.telephony.SmsMessageBase.SubmitPduBase deliverPdu;
        if (isCdmaVoice(i)) {
            if (i2 == 1 || i2 == 3) {
                deliverPdu = com.android.internal.telephony.cdma.SmsMessage.getDeliverPdu(str2, str3, j);
            } else {
                deliverPdu = com.android.internal.telephony.cdma.SmsMessage.getSubmitPdu(str, str2, str3, false, (com.android.internal.telephony.SmsHeader) null);
            }
        } else if (i2 == 1 || i2 == 3) {
            deliverPdu = com.android.internal.telephony.gsm.SmsMessage.getDeliverPdu(str, str2, str3, j);
        } else {
            deliverPdu = com.android.internal.telephony.gsm.SmsMessage.getSubmitPdu(str, str2, str3, false, (byte[]) null);
        }
        if (deliverPdu != null) {
            return new android.telephony.SmsMessage.SubmitPdu(deliverPdu);
        }
        return null;
    }

    @android.annotation.SystemApi
    public static byte[] getSubmitPduEncodedMessage(boolean z, java.lang.String str, java.lang.String str2, int i, int i2, int i3, int i4, int i5, int i6) {
        byte[] bArr;
        com.android.internal.telephony.SmsHeader.ConcatRef concatRef = new com.android.internal.telephony.SmsHeader.ConcatRef();
        concatRef.refNumber = i4;
        concatRef.seqNumber = i5;
        concatRef.msgCount = i6;
        concatRef.isEightBits = true;
        com.android.internal.telephony.SmsHeader smsHeader = new com.android.internal.telephony.SmsHeader();
        smsHeader.concatRef = concatRef;
        if (i == 1) {
            smsHeader.languageTable = i2;
            smsHeader.languageShiftTable = i3;
        }
        if (z) {
            bArr = com.android.internal.telephony.gsm.SmsMessage.getSubmitPdu(null, str, str2, false, com.android.internal.telephony.SmsHeader.toByteArray(smsHeader), i, i2, i3).encodedMessage;
        } else {
            com.android.internal.telephony.cdma.sms.UserData userData = new com.android.internal.telephony.cdma.sms.UserData();
            userData.payloadStr = str2;
            userData.userDataHeader = smsHeader;
            if (i == 1) {
                userData.msgEncoding = 9;
            } else {
                userData.msgEncoding = 4;
            }
            userData.msgEncodingSet = true;
            bArr = com.android.internal.telephony.cdma.SmsMessage.getSubmitPdu(str, userData, false).encodedMessage;
        }
        if (bArr == null) {
            return new byte[0];
        }
        return bArr;
    }

    public java.lang.String getServiceCenterAddress() {
        return this.mWrappedSmsMessage.getServiceCenterAddress();
    }

    public java.lang.String getOriginatingAddress() {
        return this.mWrappedSmsMessage.getOriginatingAddress();
    }

    public java.lang.String getDisplayOriginatingAddress() {
        return this.mWrappedSmsMessage.getDisplayOriginatingAddress();
    }

    public java.lang.String getMessageBody() {
        return this.mWrappedSmsMessage.getMessageBody();
    }

    public android.telephony.SmsMessage.MessageClass getMessageClass() {
        switch (this.mWrappedSmsMessage.getMessageClass()) {
            case CLASS_0:
                return android.telephony.SmsMessage.MessageClass.CLASS_0;
            case CLASS_1:
                return android.telephony.SmsMessage.MessageClass.CLASS_1;
            case CLASS_2:
                return android.telephony.SmsMessage.MessageClass.CLASS_2;
            case CLASS_3:
                return android.telephony.SmsMessage.MessageClass.CLASS_3;
            default:
                return android.telephony.SmsMessage.MessageClass.UNKNOWN;
        }
    }

    public java.lang.String getDisplayMessageBody() {
        return this.mWrappedSmsMessage.getDisplayMessageBody();
    }

    public java.lang.String getPseudoSubject() {
        return this.mWrappedSmsMessage.getPseudoSubject();
    }

    public long getTimestampMillis() {
        return this.mWrappedSmsMessage.getTimestampMillis();
    }

    public boolean isEmail() {
        return this.mWrappedSmsMessage.isEmail();
    }

    public java.lang.String getEmailBody() {
        return this.mWrappedSmsMessage.getEmailBody();
    }

    public java.lang.String getEmailFrom() {
        return this.mWrappedSmsMessage.getEmailFrom();
    }

    public int getProtocolIdentifier() {
        return this.mWrappedSmsMessage.getProtocolIdentifier();
    }

    public boolean isReplace() {
        return this.mWrappedSmsMessage.isReplace();
    }

    public boolean isCphsMwiMessage() {
        return this.mWrappedSmsMessage.isCphsMwiMessage();
    }

    public boolean isMWIClearMessage() {
        return this.mWrappedSmsMessage.isMWIClearMessage();
    }

    public boolean isMWISetMessage() {
        return this.mWrappedSmsMessage.isMWISetMessage();
    }

    public boolean isMwiDontStore() {
        return this.mWrappedSmsMessage.isMwiDontStore();
    }

    public byte[] getUserData() {
        return this.mWrappedSmsMessage.getUserData();
    }

    public byte[] getPdu() {
        return this.mWrappedSmsMessage.getPdu();
    }

    @java.lang.Deprecated
    public int getStatusOnSim() {
        return this.mWrappedSmsMessage.getStatusOnIcc();
    }

    public int getStatusOnIcc() {
        return this.mWrappedSmsMessage.getStatusOnIcc();
    }

    @java.lang.Deprecated
    public int getIndexOnSim() {
        return this.mWrappedSmsMessage.getIndexOnIcc();
    }

    public int getIndexOnIcc() {
        return this.mWrappedSmsMessage.getIndexOnIcc();
    }

    public int getStatus() {
        return this.mWrappedSmsMessage.getStatus();
    }

    public boolean isStatusReportMessage() {
        return this.mWrappedSmsMessage.isStatusReportMessage();
    }

    public boolean isReplyPathPresent() {
        return this.mWrappedSmsMessage.isReplyPathPresent();
    }

    public int getReceivedEncodingType() {
        return this.mWrappedSmsMessage.getReceivedEncodingType();
    }

    public boolean is3gpp() {
        return this.mWrappedSmsMessage instanceof com.android.internal.telephony.gsm.SmsMessage;
    }

    private static boolean useCdmaFormatForMoSms() {
        return useCdmaFormatForMoSms(android.telephony.SmsManager.getDefaultSmsSubscriptionId());
    }

    private static boolean useCdmaFormatForMoSms(int i) {
        android.telephony.SmsManager smsManagerForSubscriptionId = android.telephony.SmsManager.getSmsManagerForSubscriptionId(i);
        if (!smsManagerForSubscriptionId.isImsSmsSupported()) {
            return isCdmaVoice(i);
        }
        return "3gpp2".equals(smsManagerForSubscriptionId.getImsSmsFormat());
    }

    private static boolean isCdmaVoice() {
        return isCdmaVoice(android.telephony.SmsManager.getDefaultSmsSubscriptionId());
    }

    private static boolean isCdmaVoice(int i) {
        return 2 == android.telephony.TelephonyManager.getDefault().getCurrentPhoneType(i);
    }

    public static boolean hasEmsSupport() {
        if (!isNoEmsSupportConfigListExisted()) {
            return true;
        }
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            java.lang.String simOperatorNumeric = android.telephony.TelephonyManager.getDefault().getSimOperatorNumeric();
            java.lang.String groupIdLevel1 = android.telephony.TelephonyManager.getDefault().getGroupIdLevel1();
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            if (!android.text.TextUtils.isEmpty(simOperatorNumeric)) {
                for (android.telephony.SmsMessage.NoEmsSupportConfig noEmsSupportConfig : mNoEmsSupportConfigList) {
                    if (noEmsSupportConfig == null) {
                        com.android.telephony.Rlog.w(LOG_TAG, "hasEmsSupport currentConfig is null");
                    } else if (simOperatorNumeric.startsWith(noEmsSupportConfig.mOperatorNumber) && (android.text.TextUtils.isEmpty(noEmsSupportConfig.mGid1) || (!android.text.TextUtils.isEmpty(noEmsSupportConfig.mGid1) && noEmsSupportConfig.mGid1.equalsIgnoreCase(groupIdLevel1)))) {
                        return false;
                    }
                }
            }
            return true;
        } catch (java.lang.Throwable th) {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public static boolean shouldAppendPageNumberAsPrefix() {
        if (!isNoEmsSupportConfigListExisted()) {
            return false;
        }
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            java.lang.String simOperatorNumeric = android.telephony.TelephonyManager.getDefault().getSimOperatorNumeric();
            java.lang.String groupIdLevel1 = android.telephony.TelephonyManager.getDefault().getGroupIdLevel1();
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            for (android.telephony.SmsMessage.NoEmsSupportConfig noEmsSupportConfig : mNoEmsSupportConfigList) {
                if (simOperatorNumeric.startsWith(noEmsSupportConfig.mOperatorNumber) && (android.text.TextUtils.isEmpty(noEmsSupportConfig.mGid1) || (!android.text.TextUtils.isEmpty(noEmsSupportConfig.mGid1) && noEmsSupportConfig.mGid1.equalsIgnoreCase(groupIdLevel1)))) {
                    return noEmsSupportConfig.mIsPrefix;
                }
            }
            return false;
        } catch (java.lang.Throwable th) {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    private static class NoEmsSupportConfig {
        java.lang.String mGid1;
        boolean mIsPrefix;
        java.lang.String mOperatorNumber;

        public NoEmsSupportConfig(java.lang.String[] strArr) {
            this.mOperatorNumber = strArr[0];
            this.mIsPrefix = "prefix".equals(strArr[1]);
            this.mGid1 = strArr.length > 2 ? strArr[2] : null;
        }

        public java.lang.String toString() {
            return "NoEmsSupportConfig { mOperatorNumber = " + this.mOperatorNumber + ", mIsPrefix = " + this.mIsPrefix + ", mGid1 = " + this.mGid1 + " }";
        }
    }

    private static boolean isNoEmsSupportConfigListExisted() {
        android.content.res.Resources system;
        synchronized (android.telephony.SmsMessage.class) {
            if (!mIsNoEmsSupportConfigListLoaded && (system = android.content.res.Resources.getSystem()) != null) {
                java.lang.String[] stringArray = system.getStringArray(com.android.internal.R.array.no_ems_support_sim_operators);
                if (stringArray != null && stringArray.length > 0) {
                    mNoEmsSupportConfigList = new android.telephony.SmsMessage.NoEmsSupportConfig[stringArray.length];
                    for (int i = 0; i < stringArray.length; i++) {
                        mNoEmsSupportConfigList[i] = new android.telephony.SmsMessage.NoEmsSupportConfig(stringArray[i].split(android.inputmethodservice.navigationbar.NavigationBarInflaterView.GRAVITY_SEPARATOR));
                    }
                }
                mIsNoEmsSupportConfigListLoaded = true;
            }
        }
        return (mNoEmsSupportConfigList == null || mNoEmsSupportConfigList.length == 0) ? false : true;
    }

    public java.lang.String getRecipientAddress() {
        return this.mWrappedSmsMessage.getRecipientAddress();
    }
}
