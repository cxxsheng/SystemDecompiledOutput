package com.android.internal.telephony.gsm;

/* loaded from: classes5.dex */
public class SmsCbHeader {
    public static final int FORMAT_ETWS_PRIMARY = 3;
    public static final int FORMAT_GSM = 1;
    public static final int FORMAT_UMTS = 2;
    private static final java.lang.String[] LANGUAGE_CODES_GROUP_0 = {java.util.Locale.GERMAN.getLanguage(), java.util.Locale.ENGLISH.getLanguage(), java.util.Locale.ITALIAN.getLanguage(), java.util.Locale.FRENCH.getLanguage(), new java.util.Locale("es").getLanguage(), new java.util.Locale("nl").getLanguage(), new java.util.Locale("sv").getLanguage(), new java.util.Locale("da").getLanguage(), new java.util.Locale("pt").getLanguage(), new java.util.Locale("fi").getLanguage(), new java.util.Locale(android.app.backup.FullBackup.NO_BACKUP_TREE_TOKEN).getLanguage(), new java.util.Locale("el").getLanguage(), new java.util.Locale("tr").getLanguage(), new java.util.Locale("hu").getLanguage(), new java.util.Locale("pl").getLanguage(), null};
    private static final java.lang.String[] LANGUAGE_CODES_GROUP_2 = {new java.util.Locale("cs").getLanguage(), new java.util.Locale("he").getLanguage(), new java.util.Locale("ar").getLanguage(), new java.util.Locale("ru").getLanguage(), new java.util.Locale("is").getLanguage(), null, null, null, null, null, null, null, null, null, null, null};
    private static final int MESSAGE_TYPE_CBS_MESSAGE = 1;
    public static final int PDU_HEADER_LENGTH = 6;
    private static final int PDU_LENGTH_ETWS = 56;
    private static final int PDU_LENGTH_GSM = 88;
    private final android.telephony.SmsCbCmasInfo mCmasInfo;
    private final int mDataCodingScheme;
    private com.android.internal.telephony.gsm.SmsCbHeader.DataCodingScheme mDataCodingSchemeStructedData;
    private final android.telephony.SmsCbEtwsInfo mEtwsInfo;
    private final int mFormat;
    private final int mGeographicalScope;
    private final int mMessageIdentifier;
    private final int mNrOfPages;
    private final int mPageIndex;
    private final int mSerialNumber;

    public SmsCbHeader(byte[] bArr) throws java.lang.IllegalArgumentException {
        byte[] bArr2;
        if (bArr == null || bArr.length < 6) {
            throw new java.lang.IllegalArgumentException("Illegal PDU");
        }
        int i = 1;
        if (bArr.length <= 88) {
            this.mGeographicalScope = (bArr[0] & com.android.internal.midi.MidiConstants.STATUS_PROGRAM_CHANGE) >>> 6;
            this.mSerialNumber = ((bArr[0] & 255) << 8) | (bArr[1] & 255);
            this.mMessageIdentifier = ((bArr[2] & 255) << 8) | (bArr[3] & 255);
            if (isEtwsMessage() && bArr.length <= 56) {
                this.mFormat = 3;
                this.mDataCodingScheme = -1;
                this.mPageIndex = -1;
                this.mNrOfPages = -1;
                boolean z = (bArr[4] & 1) != 0;
                boolean z2 = (bArr[5] & 128) != 0;
                int i2 = (bArr[4] & com.android.internal.midi.MidiConstants.STATUS_ACTIVE_SENSING) >>> 1;
                if (bArr.length > 6) {
                    bArr2 = java.util.Arrays.copyOfRange(bArr, 6, bArr.length);
                } else {
                    bArr2 = null;
                }
                this.mEtwsInfo = new android.telephony.SmsCbEtwsInfo(i2, z, z2, true, bArr2);
                this.mCmasInfo = null;
                return;
            }
            this.mFormat = 1;
            this.mDataCodingScheme = bArr[4] & 255;
            int i3 = (bArr[5] & 240) >>> 4;
            int i4 = bArr[5] & 15;
            if (i3 == 0 || i4 == 0 || i3 > i4) {
                i4 = 1;
            } else {
                i = i3;
            }
            this.mPageIndex = i;
            this.mNrOfPages = i4;
        } else {
            this.mFormat = 2;
            byte b = bArr[0];
            if (b == 1) {
                this.mMessageIdentifier = ((bArr[1] & 255) << 8) | (bArr[2] & 255);
                this.mGeographicalScope = (bArr[3] & com.android.internal.midi.MidiConstants.STATUS_PROGRAM_CHANGE) >>> 6;
                this.mSerialNumber = ((bArr[3] & 255) << 8) | (bArr[4] & 255);
                this.mDataCodingScheme = bArr[5] & 255;
                this.mPageIndex = 1;
                this.mNrOfPages = 1;
            } else {
                throw new java.lang.IllegalArgumentException("Unsupported message type " + ((int) b));
            }
        }
        if (this.mDataCodingScheme != -1) {
            this.mDataCodingSchemeStructedData = new com.android.internal.telephony.gsm.SmsCbHeader.DataCodingScheme(this.mDataCodingScheme);
        }
        if (isEtwsMessage()) {
            this.mEtwsInfo = new android.telephony.SmsCbEtwsInfo(getEtwsWarningType(), isEtwsEmergencyUserAlert(), isEtwsPopupAlert(), false, null);
            this.mCmasInfo = null;
        } else {
            if (isCmasMessage()) {
                int cmasMessageClass = getCmasMessageClass();
                int cmasSeverity = getCmasSeverity();
                int cmasUrgency = getCmasUrgency();
                int cmasCertainty = getCmasCertainty();
                this.mEtwsInfo = null;
                this.mCmasInfo = new android.telephony.SmsCbCmasInfo(cmasMessageClass, -1, -1, cmasSeverity, cmasUrgency, cmasCertainty);
                return;
            }
            this.mEtwsInfo = null;
            this.mCmasInfo = null;
        }
    }

    public int getGeographicalScope() {
        return this.mGeographicalScope;
    }

    public int getSerialNumber() {
        return this.mSerialNumber;
    }

    public int getServiceCategory() {
        return this.mMessageIdentifier;
    }

    public int getDataCodingScheme() {
        return this.mDataCodingScheme;
    }

    public com.android.internal.telephony.gsm.SmsCbHeader.DataCodingScheme getDataCodingSchemeStructedData() {
        return this.mDataCodingSchemeStructedData;
    }

    public int getPageIndex() {
        return this.mPageIndex;
    }

    public int getNumberOfPages() {
        return this.mNrOfPages;
    }

    public android.telephony.SmsCbEtwsInfo getEtwsInfo() {
        return this.mEtwsInfo;
    }

    public android.telephony.SmsCbCmasInfo getCmasInfo() {
        return this.mCmasInfo;
    }

    public boolean isEmergencyMessage() {
        return this.mMessageIdentifier >= 4352 && this.mMessageIdentifier <= 6399;
    }

    private boolean isEtwsMessage() {
        return (this.mMessageIdentifier & com.android.internal.telephony.gsm.SmsCbConstants.MESSAGE_ID_ETWS_TYPE_MASK) == 4352;
    }

    public boolean isEtwsPrimaryNotification() {
        return this.mFormat == 3;
    }

    public boolean isUmtsFormat() {
        return this.mFormat == 2;
    }

    private boolean isCmasMessage() {
        return this.mMessageIdentifier >= 4370 && this.mMessageIdentifier <= 4400;
    }

    private boolean isEtwsPopupAlert() {
        return (this.mSerialNumber & 4096) != 0;
    }

    private boolean isEtwsEmergencyUserAlert() {
        return (this.mSerialNumber & 8192) != 0;
    }

    private int getEtwsWarningType() {
        return this.mMessageIdentifier - 4352;
    }

    private int getCmasMessageClass() {
        switch (this.mMessageIdentifier) {
            case 4370:
            case com.android.internal.telephony.gsm.SmsCbConstants.MESSAGE_ID_CMAS_ALERT_PRESIDENTIAL_LEVEL_LANGUAGE /* 4383 */:
                return 0;
            case com.android.internal.telephony.gsm.SmsCbConstants.MESSAGE_ID_CMAS_ALERT_EXTREME_IMMEDIATE_OBSERVED /* 4371 */:
            case com.android.internal.telephony.gsm.SmsCbConstants.MESSAGE_ID_CMAS_ALERT_EXTREME_IMMEDIATE_LIKELY /* 4372 */:
            case com.android.internal.telephony.gsm.SmsCbConstants.MESSAGE_ID_CMAS_ALERT_EXTREME_IMMEDIATE_OBSERVED_LANGUAGE /* 4384 */:
            case com.android.internal.telephony.gsm.SmsCbConstants.MESSAGE_ID_CMAS_ALERT_EXTREME_IMMEDIATE_LIKELY_LANGUAGE /* 4385 */:
                return 1;
            case com.android.internal.telephony.gsm.SmsCbConstants.MESSAGE_ID_CMAS_ALERT_EXTREME_EXPECTED_OBSERVED /* 4373 */:
            case com.android.internal.telephony.gsm.SmsCbConstants.MESSAGE_ID_CMAS_ALERT_EXTREME_EXPECTED_LIKELY /* 4374 */:
            case com.android.internal.telephony.gsm.SmsCbConstants.MESSAGE_ID_CMAS_ALERT_SEVERE_IMMEDIATE_OBSERVED /* 4375 */:
            case com.android.internal.telephony.gsm.SmsCbConstants.MESSAGE_ID_CMAS_ALERT_SEVERE_IMMEDIATE_LIKELY /* 4376 */:
            case com.android.internal.telephony.gsm.SmsCbConstants.MESSAGE_ID_CMAS_ALERT_SEVERE_EXPECTED_OBSERVED /* 4377 */:
            case com.android.internal.telephony.gsm.SmsCbConstants.MESSAGE_ID_CMAS_ALERT_SEVERE_EXPECTED_LIKELY /* 4378 */:
            case com.android.internal.telephony.gsm.SmsCbConstants.MESSAGE_ID_CMAS_ALERT_EXTREME_EXPECTED_OBSERVED_LANGUAGE /* 4386 */:
            case com.android.internal.telephony.gsm.SmsCbConstants.MESSAGE_ID_CMAS_ALERT_EXTREME_EXPECTED_LIKELY_LANGUAGE /* 4387 */:
            case com.android.internal.telephony.gsm.SmsCbConstants.MESSAGE_ID_CMAS_ALERT_SEVERE_IMMEDIATE_OBSERVED_LANGUAGE /* 4388 */:
            case com.android.internal.telephony.gsm.SmsCbConstants.MESSAGE_ID_CMAS_ALERT_SEVERE_IMMEDIATE_LIKELY_LANGUAGE /* 4389 */:
            case com.android.internal.telephony.gsm.SmsCbConstants.MESSAGE_ID_CMAS_ALERT_SEVERE_EXPECTED_OBSERVED_LANGUAGE /* 4390 */:
            case com.android.internal.telephony.gsm.SmsCbConstants.MESSAGE_ID_CMAS_ALERT_SEVERE_EXPECTED_LIKELY_LANGUAGE /* 4391 */:
                return 2;
            case com.android.internal.telephony.gsm.SmsCbConstants.MESSAGE_ID_CMAS_ALERT_CHILD_ABDUCTION_EMERGENCY /* 4379 */:
            case com.android.internal.telephony.gsm.SmsCbConstants.MESSAGE_ID_CMAS_ALERT_CHILD_ABDUCTION_EMERGENCY_LANGUAGE /* 4392 */:
                return 3;
            case com.android.internal.telephony.gsm.SmsCbConstants.MESSAGE_ID_CMAS_ALERT_REQUIRED_MONTHLY_TEST /* 4380 */:
            case com.android.internal.telephony.gsm.SmsCbConstants.MESSAGE_ID_CMAS_ALERT_REQUIRED_MONTHLY_TEST_LANGUAGE /* 4393 */:
                return 4;
            case com.android.internal.telephony.gsm.SmsCbConstants.MESSAGE_ID_CMAS_ALERT_EXERCISE /* 4381 */:
            case com.android.internal.telephony.gsm.SmsCbConstants.MESSAGE_ID_CMAS_ALERT_EXERCISE_LANGUAGE /* 4394 */:
                return 5;
            case com.android.internal.telephony.gsm.SmsCbConstants.MESSAGE_ID_CMAS_ALERT_OPERATOR_DEFINED_USE /* 4382 */:
            case com.android.internal.telephony.gsm.SmsCbConstants.MESSAGE_ID_CMAS_ALERT_OPERATOR_DEFINED_USE_LANGUAGE /* 4395 */:
                return 6;
            default:
                return -1;
        }
    }

    private int getCmasSeverity() {
        switch (this.mMessageIdentifier) {
            case com.android.internal.telephony.gsm.SmsCbConstants.MESSAGE_ID_CMAS_ALERT_EXTREME_IMMEDIATE_OBSERVED /* 4371 */:
            case com.android.internal.telephony.gsm.SmsCbConstants.MESSAGE_ID_CMAS_ALERT_EXTREME_IMMEDIATE_LIKELY /* 4372 */:
            case com.android.internal.telephony.gsm.SmsCbConstants.MESSAGE_ID_CMAS_ALERT_EXTREME_EXPECTED_OBSERVED /* 4373 */:
            case com.android.internal.telephony.gsm.SmsCbConstants.MESSAGE_ID_CMAS_ALERT_EXTREME_EXPECTED_LIKELY /* 4374 */:
            case com.android.internal.telephony.gsm.SmsCbConstants.MESSAGE_ID_CMAS_ALERT_EXTREME_IMMEDIATE_OBSERVED_LANGUAGE /* 4384 */:
            case com.android.internal.telephony.gsm.SmsCbConstants.MESSAGE_ID_CMAS_ALERT_EXTREME_IMMEDIATE_LIKELY_LANGUAGE /* 4385 */:
            case com.android.internal.telephony.gsm.SmsCbConstants.MESSAGE_ID_CMAS_ALERT_EXTREME_EXPECTED_OBSERVED_LANGUAGE /* 4386 */:
            case com.android.internal.telephony.gsm.SmsCbConstants.MESSAGE_ID_CMAS_ALERT_EXTREME_EXPECTED_LIKELY_LANGUAGE /* 4387 */:
                return 0;
            case com.android.internal.telephony.gsm.SmsCbConstants.MESSAGE_ID_CMAS_ALERT_SEVERE_IMMEDIATE_OBSERVED /* 4375 */:
            case com.android.internal.telephony.gsm.SmsCbConstants.MESSAGE_ID_CMAS_ALERT_SEVERE_IMMEDIATE_LIKELY /* 4376 */:
            case com.android.internal.telephony.gsm.SmsCbConstants.MESSAGE_ID_CMAS_ALERT_SEVERE_EXPECTED_OBSERVED /* 4377 */:
            case com.android.internal.telephony.gsm.SmsCbConstants.MESSAGE_ID_CMAS_ALERT_SEVERE_EXPECTED_LIKELY /* 4378 */:
            case com.android.internal.telephony.gsm.SmsCbConstants.MESSAGE_ID_CMAS_ALERT_SEVERE_IMMEDIATE_OBSERVED_LANGUAGE /* 4388 */:
            case com.android.internal.telephony.gsm.SmsCbConstants.MESSAGE_ID_CMAS_ALERT_SEVERE_IMMEDIATE_LIKELY_LANGUAGE /* 4389 */:
            case com.android.internal.telephony.gsm.SmsCbConstants.MESSAGE_ID_CMAS_ALERT_SEVERE_EXPECTED_OBSERVED_LANGUAGE /* 4390 */:
            case com.android.internal.telephony.gsm.SmsCbConstants.MESSAGE_ID_CMAS_ALERT_SEVERE_EXPECTED_LIKELY_LANGUAGE /* 4391 */:
                return 1;
            case com.android.internal.telephony.gsm.SmsCbConstants.MESSAGE_ID_CMAS_ALERT_CHILD_ABDUCTION_EMERGENCY /* 4379 */:
            case com.android.internal.telephony.gsm.SmsCbConstants.MESSAGE_ID_CMAS_ALERT_REQUIRED_MONTHLY_TEST /* 4380 */:
            case com.android.internal.telephony.gsm.SmsCbConstants.MESSAGE_ID_CMAS_ALERT_EXERCISE /* 4381 */:
            case com.android.internal.telephony.gsm.SmsCbConstants.MESSAGE_ID_CMAS_ALERT_OPERATOR_DEFINED_USE /* 4382 */:
            case com.android.internal.telephony.gsm.SmsCbConstants.MESSAGE_ID_CMAS_ALERT_PRESIDENTIAL_LEVEL_LANGUAGE /* 4383 */:
            default:
                return -1;
        }
    }

    private int getCmasUrgency() {
        switch (this.mMessageIdentifier) {
            case com.android.internal.telephony.gsm.SmsCbConstants.MESSAGE_ID_CMAS_ALERT_EXTREME_IMMEDIATE_OBSERVED /* 4371 */:
            case com.android.internal.telephony.gsm.SmsCbConstants.MESSAGE_ID_CMAS_ALERT_EXTREME_IMMEDIATE_LIKELY /* 4372 */:
            case com.android.internal.telephony.gsm.SmsCbConstants.MESSAGE_ID_CMAS_ALERT_SEVERE_IMMEDIATE_OBSERVED /* 4375 */:
            case com.android.internal.telephony.gsm.SmsCbConstants.MESSAGE_ID_CMAS_ALERT_SEVERE_IMMEDIATE_LIKELY /* 4376 */:
            case com.android.internal.telephony.gsm.SmsCbConstants.MESSAGE_ID_CMAS_ALERT_EXTREME_IMMEDIATE_OBSERVED_LANGUAGE /* 4384 */:
            case com.android.internal.telephony.gsm.SmsCbConstants.MESSAGE_ID_CMAS_ALERT_EXTREME_IMMEDIATE_LIKELY_LANGUAGE /* 4385 */:
            case com.android.internal.telephony.gsm.SmsCbConstants.MESSAGE_ID_CMAS_ALERT_SEVERE_IMMEDIATE_OBSERVED_LANGUAGE /* 4388 */:
            case com.android.internal.telephony.gsm.SmsCbConstants.MESSAGE_ID_CMAS_ALERT_SEVERE_IMMEDIATE_LIKELY_LANGUAGE /* 4389 */:
                return 0;
            case com.android.internal.telephony.gsm.SmsCbConstants.MESSAGE_ID_CMAS_ALERT_EXTREME_EXPECTED_OBSERVED /* 4373 */:
            case com.android.internal.telephony.gsm.SmsCbConstants.MESSAGE_ID_CMAS_ALERT_EXTREME_EXPECTED_LIKELY /* 4374 */:
            case com.android.internal.telephony.gsm.SmsCbConstants.MESSAGE_ID_CMAS_ALERT_SEVERE_EXPECTED_OBSERVED /* 4377 */:
            case com.android.internal.telephony.gsm.SmsCbConstants.MESSAGE_ID_CMAS_ALERT_SEVERE_EXPECTED_LIKELY /* 4378 */:
            case com.android.internal.telephony.gsm.SmsCbConstants.MESSAGE_ID_CMAS_ALERT_EXTREME_EXPECTED_OBSERVED_LANGUAGE /* 4386 */:
            case com.android.internal.telephony.gsm.SmsCbConstants.MESSAGE_ID_CMAS_ALERT_EXTREME_EXPECTED_LIKELY_LANGUAGE /* 4387 */:
            case com.android.internal.telephony.gsm.SmsCbConstants.MESSAGE_ID_CMAS_ALERT_SEVERE_EXPECTED_OBSERVED_LANGUAGE /* 4390 */:
            case com.android.internal.telephony.gsm.SmsCbConstants.MESSAGE_ID_CMAS_ALERT_SEVERE_EXPECTED_LIKELY_LANGUAGE /* 4391 */:
                return 1;
            case com.android.internal.telephony.gsm.SmsCbConstants.MESSAGE_ID_CMAS_ALERT_CHILD_ABDUCTION_EMERGENCY /* 4379 */:
            case com.android.internal.telephony.gsm.SmsCbConstants.MESSAGE_ID_CMAS_ALERT_REQUIRED_MONTHLY_TEST /* 4380 */:
            case com.android.internal.telephony.gsm.SmsCbConstants.MESSAGE_ID_CMAS_ALERT_EXERCISE /* 4381 */:
            case com.android.internal.telephony.gsm.SmsCbConstants.MESSAGE_ID_CMAS_ALERT_OPERATOR_DEFINED_USE /* 4382 */:
            case com.android.internal.telephony.gsm.SmsCbConstants.MESSAGE_ID_CMAS_ALERT_PRESIDENTIAL_LEVEL_LANGUAGE /* 4383 */:
            default:
                return -1;
        }
    }

    private int getCmasCertainty() {
        switch (this.mMessageIdentifier) {
            case com.android.internal.telephony.gsm.SmsCbConstants.MESSAGE_ID_CMAS_ALERT_EXTREME_IMMEDIATE_OBSERVED /* 4371 */:
            case com.android.internal.telephony.gsm.SmsCbConstants.MESSAGE_ID_CMAS_ALERT_EXTREME_EXPECTED_OBSERVED /* 4373 */:
            case com.android.internal.telephony.gsm.SmsCbConstants.MESSAGE_ID_CMAS_ALERT_SEVERE_IMMEDIATE_OBSERVED /* 4375 */:
            case com.android.internal.telephony.gsm.SmsCbConstants.MESSAGE_ID_CMAS_ALERT_SEVERE_EXPECTED_OBSERVED /* 4377 */:
            case com.android.internal.telephony.gsm.SmsCbConstants.MESSAGE_ID_CMAS_ALERT_EXTREME_IMMEDIATE_OBSERVED_LANGUAGE /* 4384 */:
            case com.android.internal.telephony.gsm.SmsCbConstants.MESSAGE_ID_CMAS_ALERT_EXTREME_EXPECTED_OBSERVED_LANGUAGE /* 4386 */:
            case com.android.internal.telephony.gsm.SmsCbConstants.MESSAGE_ID_CMAS_ALERT_SEVERE_IMMEDIATE_OBSERVED_LANGUAGE /* 4388 */:
            case com.android.internal.telephony.gsm.SmsCbConstants.MESSAGE_ID_CMAS_ALERT_SEVERE_EXPECTED_OBSERVED_LANGUAGE /* 4390 */:
                return 0;
            case com.android.internal.telephony.gsm.SmsCbConstants.MESSAGE_ID_CMAS_ALERT_EXTREME_IMMEDIATE_LIKELY /* 4372 */:
            case com.android.internal.telephony.gsm.SmsCbConstants.MESSAGE_ID_CMAS_ALERT_EXTREME_EXPECTED_LIKELY /* 4374 */:
            case com.android.internal.telephony.gsm.SmsCbConstants.MESSAGE_ID_CMAS_ALERT_SEVERE_IMMEDIATE_LIKELY /* 4376 */:
            case com.android.internal.telephony.gsm.SmsCbConstants.MESSAGE_ID_CMAS_ALERT_SEVERE_EXPECTED_LIKELY /* 4378 */:
            case com.android.internal.telephony.gsm.SmsCbConstants.MESSAGE_ID_CMAS_ALERT_EXTREME_IMMEDIATE_LIKELY_LANGUAGE /* 4385 */:
            case com.android.internal.telephony.gsm.SmsCbConstants.MESSAGE_ID_CMAS_ALERT_EXTREME_EXPECTED_LIKELY_LANGUAGE /* 4387 */:
            case com.android.internal.telephony.gsm.SmsCbConstants.MESSAGE_ID_CMAS_ALERT_SEVERE_IMMEDIATE_LIKELY_LANGUAGE /* 4389 */:
            case com.android.internal.telephony.gsm.SmsCbConstants.MESSAGE_ID_CMAS_ALERT_SEVERE_EXPECTED_LIKELY_LANGUAGE /* 4391 */:
                return 1;
            case com.android.internal.telephony.gsm.SmsCbConstants.MESSAGE_ID_CMAS_ALERT_CHILD_ABDUCTION_EMERGENCY /* 4379 */:
            case com.android.internal.telephony.gsm.SmsCbConstants.MESSAGE_ID_CMAS_ALERT_REQUIRED_MONTHLY_TEST /* 4380 */:
            case com.android.internal.telephony.gsm.SmsCbConstants.MESSAGE_ID_CMAS_ALERT_EXERCISE /* 4381 */:
            case com.android.internal.telephony.gsm.SmsCbConstants.MESSAGE_ID_CMAS_ALERT_OPERATOR_DEFINED_USE /* 4382 */:
            case com.android.internal.telephony.gsm.SmsCbConstants.MESSAGE_ID_CMAS_ALERT_PRESIDENTIAL_LEVEL_LANGUAGE /* 4383 */:
            default:
                return -1;
        }
    }

    public java.lang.String toString() {
        return "SmsCbHeader{GS=" + this.mGeographicalScope + ", serialNumber=0x" + java.lang.Integer.toHexString(this.mSerialNumber) + ", messageIdentifier=0x" + java.lang.Integer.toHexString(this.mMessageIdentifier) + ", format=" + this.mFormat + ", DCS=0x" + java.lang.Integer.toHexString(this.mDataCodingScheme) + ", page " + this.mPageIndex + " of " + this.mNrOfPages + '}';
    }

    public static final class DataCodingScheme {
        public final int encoding;
        public final boolean hasLanguageIndicator;
        public final java.lang.String language;

        public DataCodingScheme(int i) {
            int i2 = 3;
            boolean z = true;
            java.lang.String str = null;
            switch ((i & 240) >> 4) {
                case 0:
                    str = com.android.internal.telephony.gsm.SmsCbHeader.LANGUAGE_CODES_GROUP_0[i & 15];
                    i2 = 1;
                    z = false;
                    break;
                case 1:
                    if ((i & 15) != 1) {
                        i2 = 1;
                        break;
                    }
                    break;
                case 2:
                    str = com.android.internal.telephony.gsm.SmsCbHeader.LANGUAGE_CODES_GROUP_2[i & 15];
                    i2 = 1;
                    z = false;
                    break;
                case 3:
                    i2 = 1;
                    z = false;
                    break;
                case 4:
                case 5:
                    switch ((i & 12) >> 2) {
                        case 1:
                            i2 = 2;
                            z = false;
                            break;
                        case 2:
                            z = false;
                            break;
                        default:
                            i2 = 1;
                            z = false;
                            break;
                    }
                case 6:
                case 7:
                case 9:
                case 14:
                    throw new java.lang.IllegalArgumentException("Unsupported GSM dataCodingScheme " + i);
                case 8:
                case 10:
                case 11:
                case 12:
                case 13:
                default:
                    i2 = 1;
                    z = false;
                    break;
                case 15:
                    if (((i & 4) >> 2) == 1) {
                        i2 = 2;
                        z = false;
                        break;
                    } else {
                        i2 = 1;
                        z = false;
                        break;
                    }
            }
            this.encoding = i2;
            this.language = str;
            this.hasLanguageIndicator = z;
        }
    }
}
