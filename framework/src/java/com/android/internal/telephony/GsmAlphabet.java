package com.android.internal.telephony;

/* loaded from: classes5.dex */
public class GsmAlphabet {
    public static final byte GSM_EXTENDED_ESCAPE = 27;
    private static final java.lang.String TAG = "GSM";
    public static final int UDH_SEPTET_COST_CONCATENATED_MESSAGE = 6;
    public static final int UDH_SEPTET_COST_LENGTH = 1;
    public static final int UDH_SEPTET_COST_ONE_SHIFT_TABLE = 4;
    public static final int UDH_SEPTET_COST_TWO_SHIFT_TABLES = 7;
    private static final android.util.SparseIntArray[] sCharsToGsmTables;
    private static final android.util.SparseIntArray[] sCharsToShiftTables;
    private static int[] sEnabledLockingShiftTables;
    private static int[] sEnabledSingleShiftTables;
    private static int sHighestEnabledSingleShiftCode;
    private static boolean sDisableCountryEncodingCheck = false;
    private static final java.lang.String[] sLanguageTables = {"@£$¥èéùìòÇ\nØø\rÅåΔ_ΦΓΛΩΠΨΣΘΞ\uffffÆæßÉ !\"#¤%&'()*+,-./0123456789:;<=>?¡ABCDEFGHIJKLMNOPQRSTUVWXYZÄÖÑÜ§¿abcdefghijklmnopqrstuvwxyzäöñüà", "@£$¥€éùıòÇ\nĞğ\rÅåΔ_ΦΓΛΩΠΨΣΘΞ\uffffŞşßÉ !\"#¤%&'()*+,-./0123456789:;<=>?İABCDEFGHIJKLMNOPQRSTUVWXYZÄÖÑÜ§çabcdefghijklmnopqrstuvwxyzäöñüà", "", "@£$¥êéúíóç\nÔô\rÁáΔ_ªÇÀ∞^\\€Ó|\uffffÂâÊÉ !\"#º%&'()*+,-./0123456789:;<=>?ÍABCDEFGHIJKLMNOPQRSTUVWXYZÃÕÚÜ§~abcdefghijklmnopqrstuvwxyzãõ`üà", "ঁংঃঅআইঈউঊঋ\nঌ \r এঐ  ওঔকখগঘঙচ\uffffছজঝঞ !টঠডঢণত)(থদ,ধ.ন0123456789:; পফ?বভমযর ল   শষসহ়ঽািীুূৃৄ  েৈ  োৌ্ৎabcdefghijklmnopqrstuvwxyzৗড়ঢ়ৰৱ", "ઁંઃઅઆઇઈઉઊઋ\nઌઍ\r એઐઑ ઓઔકખગઘઙચ\uffffછજઝઞ !ટઠડઢણત)(થદ,ધ.ન0123456789:; પફ?બભમયર લળ વશષસહ઼ઽાિીુૂૃૄૅ ેૈૉ ોૌ્ૐabcdefghijklmnopqrstuvwxyzૠૡૢૣ૱", "ँंःअआइईउऊऋ\nऌऍ\rऎएऐऑऒओऔकखगघङच\uffffछजझञ !टठडढणत)(थद,ध.न0123456789:;ऩपफ?बभमयरऱलळऴवशषसह़ऽािीुूृॄॅॆेैॉॊोौ्ॐabcdefghijklmnopqrstuvwxyzॲॻॼॾॿ", " ಂಃಅಆಇಈಉಊಋ\nಌ \rಎಏಐ ಒಓಔಕಖಗಘಙಚ\uffffಛಜಝಞ !ಟಠಡಢಣತ)(ಥದ,ಧ.ನ0123456789:; ಪಫ?ಬಭಮಯರಱಲಳ ವಶಷಸಹ಼ಽಾಿೀುೂೃೄ ೆೇೈ ೊೋೌ್ೕabcdefghijklmnopqrstuvwxyzೖೠೡೢೣ", " ംഃഅആഇഈഉഊഋ\nഌ \rഎഏഐ ഒഓഔകഖഗഘങച\uffffഛജഝഞ !ടഠഡഢണത)(ഥദ,ധ.ന0123456789:; പഫ?ബഭമയരറലളഴവശഷസഹ ഽാിീുൂൃൄ െേൈ ൊോൌ്ൗabcdefghijklmnopqrstuvwxyzൠൡൢൣ൹", "ଁଂଃଅଆଇଈଉଊଋ\nଌ \r ଏଐ  ଓଔକଖଗଘଙଚ\uffffଛଜଝଞ !ଟଠଡଢଣତ)(ଥଦ,ଧ.ନ0123456789:; ପଫ?ବଭମଯର ଲଳ ଵଶଷସହ଼ଽାିୀୁୂୃୄ  େୈ  ୋୌ୍ୖabcdefghijklmnopqrstuvwxyzୗୠୡୢୣ", "ਁਂਃਅਆਇਈਉਊ \n  \r ਏਐ  ਓਔਕਖਗਘਙਚ\uffffਛਜਝਞ !ਟਠਡਢਣਤ)(ਥਦ,ਧ.ਨ0123456789:; ਪਫ?ਬਭਮਯਰ ਲਲ਼ ਵਸ਼ ਸਹ਼ ਾਿੀੁੂ    ੇੈ  ੋੌ੍ੑabcdefghijklmnopqrstuvwxyzੰੱੲੳੴ", " ஂஃஅஆஇஈஉஊ \n  \rஎஏஐ ஒஓஔக   ஙச\uffff ஜ ஞ !ட   ணத)(  , .ந0123456789:;னப ?  மயரறலளழவஶஷஸஹ  ாிீுூ   ெேை ொோௌ்ௐabcdefghijklmnopqrstuvwxyzௗ௰௱௲௹", "ఁంఃఅఆఇఈఉఊఋ\nఌ \rఎఏఐ ఒఓఔకఖగఘఙచ\uffffఛజఝఞ !టఠడఢణత)(థద,ధ.న0123456789:; పఫ?బభమయరఱలళ వశషసహ ఽాిీుూృౄ ెేై ొోౌ్ౕabcdefghijklmnopqrstuvwxyzౖౠౡౢౣ", "اآبٻڀپڦتۂٿ\nٹٽ\rٺټثجځڄڃڅچڇحخد\uffffڌڈډڊ !ڏڍذرڑړ)(ڙز,ږ.ژ0123456789:;ښسش?صضطظعفقکڪګگڳڱلمنںڻڼوۄەہھءیېےٍُِٗٔabcdefghijklmnopqrstuvwxyzّٰٕٖٓ"};
    private static final java.lang.String[] sLanguageShiftTables = {"          \f         ^                   {}     \\            [~] |                                    €                          ", "          \f         ^                   {}     \\            [~] |      Ğ İ         Ş               ç € ğ ı         ş            ", "         ç\f         ^                   {}     \\            [~] |Á       Í     Ó     Ú           á   €   í     ó     ú          ", "     ê   ç\fÔô Áá  ΦΓ^ΩΠΨΣΘ     Ê        {}     \\            [~] |À       Í     Ó     Ú     ÃÕ    Â   €   í     ó     ú     ãõ  â", "@£$¥¿\"¤%&'\f*+ -/<=>¡^¡_#*০১ ২৩৪৫৬৭৮৯য়ৠৡৢ{}ৣ৲৳৴৵\\৶৷৸৹৺       [~] |ABCDEFGHIJKLMNOPQRSTUVWXYZ          €                          ", "@£$¥¿\"¤%&'\f*+ -/<=>¡^¡_#*।॥ ૦૧૨૩૪૫૬૭૮૯  {}     \\            [~] |ABCDEFGHIJKLMNOPQRSTUVWXYZ          €                          ", "@£$¥¿\"¤%&'\f*+ -/<=>¡^¡_#*।॥ ०१२३४५६७८९॒॑{}॓॔क़ख़ग़\\ज़ड़ढ़फ़य़ॠॡॢॣ॰ॱ [~] |ABCDEFGHIJKLMNOPQRSTUVWXYZ          €                          ", "@£$¥¿\"¤%&'\f*+ -/<=>¡^¡_#*।॥ ೦೧೨೩೪೫೬೭೮೯ೞೱ{}ೲ    \\            [~] |ABCDEFGHIJKLMNOPQRSTUVWXYZ          €                          ", "@£$¥¿\"¤%&'\f*+ -/<=>¡^¡_#*।॥ ൦൧൨൩൪൫൬൭൮൯൰൱{}൲൳൴൵ൺ\\ൻർൽൾൿ       [~] |ABCDEFGHIJKLMNOPQRSTUVWXYZ          €                          ", "@£$¥¿\"¤%&'\f*+ -/<=>¡^¡_#*।॥ ୦୧୨୩୪୫୬୭୮୯ଡ଼ଢ଼{}ୟ୰ୱ  \\            [~] |ABCDEFGHIJKLMNOPQRSTUVWXYZ          €                          ", "@£$¥¿\"¤%&'\f*+ -/<=>¡^¡_#*।॥ ੦੧੨੩੪੫੬੭੮੯ਖ਼ਗ਼{}ਜ਼ੜਫ਼ੵ \\            [~] |ABCDEFGHIJKLMNOPQRSTUVWXYZ          €                          ", "@£$¥¿\"¤%&'\f*+ -/<=>¡^¡_#*।॥ ௦௧௨௩௪௫௬௭௮௯௳௴{}௵௶௷௸௺\\            [~] |ABCDEFGHIJKLMNOPQRSTUVWXYZ          €                          ", "@£$¥¿\"¤%&'\f*+ -/<=>¡^¡_#*   ౦౧౨౩౪౫౬౭౮౯ౘౙ{}౸౹౺౻౼\\౽౾౿         [~] |ABCDEFGHIJKLMNOPQRSTUVWXYZ          €                          ", "@£$¥¿\"¤%&'\f*+ -/<=>¡^¡_#*\u0600\u0601 ۰۱۲۳۴۵۶۷۸۹،؍{}؎؏ؐؑؒ\\ؓؔ؛؟ـْ٘٫٬ٲٳۍ[~]۔|ABCDEFGHIJKLMNOPQRSTUVWXYZ          €                          "};

    private GsmAlphabet() {
    }

    public static class TextEncodingDetails {
        public int codeUnitCount;
        public int codeUnitSize;
        public int codeUnitsRemaining;
        public int languageShiftTable;
        public int languageTable;
        public int msgCount;

        public java.lang.String toString() {
            return "TextEncodingDetails { msgCount=" + this.msgCount + ", codeUnitCount=" + this.codeUnitCount + ", codeUnitsRemaining=" + this.codeUnitsRemaining + ", codeUnitSize=" + this.codeUnitSize + ", languageTable=" + this.languageTable + ", languageShiftTable=" + this.languageShiftTable + " }";
        }
    }

    public static int charToGsm(char c) {
        try {
            return charToGsm(c, false);
        } catch (com.android.internal.telephony.EncodeException e) {
            return sCharsToGsmTables[0].get(32, 32);
        }
    }

    public static int charToGsm(char c, boolean z) throws com.android.internal.telephony.EncodeException {
        int i = sCharsToGsmTables[0].get(c, -1);
        if (i == -1) {
            if (sCharsToShiftTables[0].get(c, -1) == -1) {
                if (z) {
                    throw new com.android.internal.telephony.EncodeException(c);
                }
                return sCharsToGsmTables[0].get(32, 32);
            }
            return 27;
        }
        return i;
    }

    public static int charToGsmExtended(char c) {
        int i = sCharsToShiftTables[0].get(c, -1);
        if (i == -1) {
            return sCharsToGsmTables[0].get(32, 32);
        }
        return i;
    }

    public static char gsmToChar(int i) {
        if (i >= 0 && i < 128) {
            return sLanguageTables[0].charAt(i);
        }
        return ' ';
    }

    public static char gsmExtendedToChar(int i) {
        if (i == 27 || i < 0 || i >= 128) {
            return ' ';
        }
        char charAt = sLanguageShiftTables[0].charAt(i);
        if (charAt == ' ') {
            return sLanguageTables[0].charAt(i);
        }
        return charAt;
    }

    public static byte[] stringToGsm7BitPackedWithHeader(java.lang.String str, byte[] bArr) throws com.android.internal.telephony.EncodeException {
        return stringToGsm7BitPackedWithHeader(str, bArr, 0, 0);
    }

    public static byte[] stringToGsm7BitPackedWithHeader(java.lang.String str, byte[] bArr, int i, int i2) throws com.android.internal.telephony.EncodeException {
        if (bArr == null || bArr.length == 0) {
            return stringToGsm7BitPacked(str, i, i2);
        }
        byte[] stringToGsm7BitPacked = stringToGsm7BitPacked(str, (((bArr.length + 1) * 8) + 6) / 7, true, i, i2);
        stringToGsm7BitPacked[1] = (byte) bArr.length;
        java.lang.System.arraycopy(bArr, 0, stringToGsm7BitPacked, 2, bArr.length);
        return stringToGsm7BitPacked;
    }

    public static byte[] stringToGsm7BitPacked(java.lang.String str) throws com.android.internal.telephony.EncodeException {
        return stringToGsm7BitPacked(str, 0, true, 0, 0);
    }

    public static byte[] stringToGsm7BitPacked(java.lang.String str, int i, int i2) throws com.android.internal.telephony.EncodeException {
        return stringToGsm7BitPacked(str, 0, true, i, i2);
    }

    public static byte[] stringToGsm7BitPacked(java.lang.String str, int i, boolean z, int i2, int i3) throws com.android.internal.telephony.EncodeException {
        int length = str.length();
        int countGsmSeptetsUsingTables = countGsmSeptetsUsingTables(str, !z, i2, i3);
        if (countGsmSeptetsUsingTables == -1) {
            throw new com.android.internal.telephony.EncodeException("countGsmSeptetsUsingTables(): unencodable char");
        }
        int i4 = countGsmSeptetsUsingTables + i;
        if (i4 > 255) {
            throw new com.android.internal.telephony.EncodeException("Payload cannot exceed 255 septets", 1);
        }
        byte[] bArr = new byte[(((i4 * 7) + 7) / 8) + 1];
        android.util.SparseIntArray sparseIntArray = sCharsToGsmTables[i2];
        android.util.SparseIntArray sparseIntArray2 = sCharsToShiftTables[i3];
        int i5 = i * 7;
        int i6 = 0;
        while (i6 < length && i < i4) {
            char charAt = str.charAt(i6);
            int i7 = sparseIntArray.get(charAt, -1);
            if (i7 == -1) {
                i7 = sparseIntArray2.get(charAt, -1);
                if (i7 == -1) {
                    if (z) {
                        throw new com.android.internal.telephony.EncodeException("stringToGsm7BitPacked(): unencodable char");
                    }
                    i7 = sparseIntArray.get(32, 32);
                } else {
                    packSmsChar(bArr, i5, 27);
                    i5 += 7;
                    i++;
                }
            }
            packSmsChar(bArr, i5, i7);
            i++;
            i6++;
            i5 += 7;
        }
        bArr[0] = (byte) i4;
        return bArr;
    }

    private static void packSmsChar(byte[] bArr, int i, int i2) {
        int i3 = i / 8;
        int i4 = i % 8;
        int i5 = i3 + 1;
        bArr[i5] = (byte) (bArr[i5] | (i2 << i4));
        if (i4 > 1) {
            bArr[i5 + 1] = (byte) (i2 >> (8 - i4));
        }
    }

    public static java.lang.String gsm7BitPackedToString(byte[] bArr, int i, int i2) {
        return gsm7BitPackedToString(bArr, i, i2, 0, 0, 0);
    }

    public static java.lang.String gsm7BitPackedToString(byte[] bArr, int i, int i2, int i3, int i4, int i5) {
        int i6 = i4;
        int i7 = i5;
        java.lang.StringBuilder sb = new java.lang.StringBuilder(i2);
        if (i6 < 0 || i6 > sLanguageTables.length) {
            android.util.Log.w(TAG, "unknown language table " + i6 + ", using default");
            i6 = 0;
        }
        if (i7 < 0 || i7 > sLanguageShiftTables.length) {
            android.util.Log.w(TAG, "unknown single shift table " + i7 + ", using default");
            i7 = 0;
        }
        try {
            java.lang.String str = sLanguageTables[i6];
            java.lang.String str2 = sLanguageShiftTables[i7];
            if (str.isEmpty()) {
                android.util.Log.w(TAG, "no language table for code " + i6 + ", using default");
                str = sLanguageTables[0];
            }
            if (str2.isEmpty()) {
                android.util.Log.w(TAG, "no single shift table for code " + i7 + ", using default");
                str2 = sLanguageShiftTables[0];
            }
            boolean z = false;
            for (int i8 = 0; i8 < i2; i8++) {
                int i9 = (i8 * 7) + i3;
                int i10 = i9 / 8;
                int i11 = i9 % 8;
                int i12 = i10 + i;
                int i13 = (bArr[i12] >> i11) & 127;
                if (i11 > 1) {
                    i13 = (i13 & (127 >> (i11 - 1))) | ((bArr[i12 + 1] << (8 - i11)) & 127);
                }
                if (z) {
                    if (i13 == 27) {
                        sb.append(' ');
                    } else {
                        char charAt = str2.charAt(i13);
                        if (charAt == ' ') {
                            sb.append(str.charAt(i13));
                        } else {
                            sb.append(charAt);
                        }
                    }
                    z = false;
                } else if (i13 == 27) {
                    z = true;
                } else {
                    sb.append(str.charAt(i13));
                }
            }
            return sb.toString();
        } catch (java.lang.RuntimeException e) {
            android.util.Log.e(TAG, "Error GSM 7 bit packed: ", e);
            return null;
        }
    }

    public static java.lang.String gsm8BitUnpackedToString(byte[] bArr, int i, int i2) {
        return gsm8BitUnpackedToString(bArr, i, i2, "");
    }

    public static java.lang.String gsm8BitUnpackedToString(byte[] bArr, int i, int i2, java.lang.String str) {
        java.nio.charset.Charset charset;
        java.nio.ByteBuffer byteBuffer;
        boolean z;
        int i3;
        int i4;
        if (!android.text.TextUtils.isEmpty(str) && !str.equalsIgnoreCase(com.google.android.mms.pdu.CharacterSets.MIMENAME_US_ASCII) && java.nio.charset.Charset.isSupported(str)) {
            charset = java.nio.charset.Charset.forName(str);
            byteBuffer = java.nio.ByteBuffer.allocate(2);
            z = true;
        } else {
            charset = null;
            byteBuffer = null;
            z = false;
        }
        java.lang.String str2 = sLanguageTables[0];
        java.lang.String str3 = sLanguageShiftTables[0];
        java.lang.StringBuilder sb = new java.lang.StringBuilder(i2);
        int i5 = i;
        boolean z2 = false;
        while (true) {
            int i6 = i + i2;
            if (i5 >= i6 || (i3 = bArr[i5] & 255) == 255) {
                break;
            }
            if (i3 == 27) {
                if (z2) {
                    sb.append(' ');
                    z2 = false;
                } else {
                    z2 = true;
                }
            } else {
                if (z2) {
                    char charAt = i3 < str3.length() ? str3.charAt(i3) : ' ';
                    if (charAt == ' ') {
                        if (i3 < str2.length()) {
                            sb.append(str2.charAt(i3));
                        } else {
                            sb.append(' ');
                        }
                    } else {
                        sb.append(charAt);
                    }
                } else if (!z || i3 < 128 || (i4 = i5 + 1) >= i6) {
                    if (i3 < str2.length()) {
                        sb.append(str2.charAt(i3));
                    } else {
                        sb.append(' ');
                    }
                } else {
                    byteBuffer.clear();
                    byteBuffer.put(bArr, i5, 2);
                    byteBuffer.flip();
                    sb.append(charset.decode(byteBuffer).toString());
                    i5 = i4;
                }
                z2 = false;
            }
            i5++;
        }
        return sb.toString();
    }

    public static byte[] stringToGsm8BitPacked(java.lang.String str) {
        int countGsmSeptetsUsingTables = countGsmSeptetsUsingTables(str, true, 0, 0);
        byte[] bArr = new byte[countGsmSeptetsUsingTables];
        stringToGsm8BitUnpackedField(str, bArr, 0, countGsmSeptetsUsingTables);
        return bArr;
    }

    public static void stringToGsm8BitUnpackedField(java.lang.String str, byte[] bArr, int i, int i2) {
        int i3 = 0;
        android.util.SparseIntArray sparseIntArray = sCharsToGsmTables[0];
        android.util.SparseIntArray sparseIntArray2 = sCharsToShiftTables[0];
        int length = str.length();
        int i4 = i;
        while (i3 < length && i4 - i < i2) {
            char charAt = str.charAt(i3);
            int i5 = sparseIntArray.get(charAt, -1);
            if (i5 == -1) {
                i5 = sparseIntArray2.get(charAt, -1);
                if (i5 == -1) {
                    i5 = sparseIntArray.get(32, 32);
                } else {
                    int i6 = i4 + 1;
                    if (i6 - i >= i2) {
                        break;
                    }
                    bArr[i4] = GSM_EXTENDED_ESCAPE;
                    i4 = i6;
                }
            }
            bArr[i4] = (byte) i5;
            i3++;
            i4++;
        }
        while (i4 - i < i2) {
            bArr[i4] = -1;
            i4++;
        }
    }

    public static int countGsmSeptets(char c) {
        try {
            return countGsmSeptets(c, false);
        } catch (com.android.internal.telephony.EncodeException e) {
            return 0;
        }
    }

    public static int countGsmSeptets(char c, boolean z) throws com.android.internal.telephony.EncodeException {
        if (sCharsToGsmTables[0].get(c, -1) != -1) {
            return 1;
        }
        if (sCharsToShiftTables[0].get(c, -1) != -1) {
            return 2;
        }
        if (z) {
            throw new com.android.internal.telephony.EncodeException(c);
        }
        return 1;
    }

    public static boolean isGsmSeptets(char c) {
        return (sCharsToGsmTables[0].get(c, -1) == -1 && sCharsToShiftTables[0].get(c, -1) == -1) ? false : true;
    }

    public static int countGsmSeptetsUsingTables(java.lang.CharSequence charSequence, boolean z, int i, int i2) {
        int length = charSequence.length();
        android.util.SparseIntArray sparseIntArray = sCharsToGsmTables[i];
        android.util.SparseIntArray sparseIntArray2 = sCharsToShiftTables[i2];
        int i3 = 0;
        for (int i4 = 0; i4 < length; i4++) {
            char charAt = charSequence.charAt(i4);
            if (charAt == 27) {
                android.util.Log.w(TAG, "countGsmSeptets() string contains Escape character, skipping.");
            } else if (sparseIntArray.get(charAt, -1) == -1) {
                if (sparseIntArray2.get(charAt, -1) != -1) {
                    i3 += 2;
                } else {
                    if (!z) {
                        return -1;
                    }
                    i3++;
                }
            } else {
                i3++;
            }
        }
        return i3;
    }

    public static com.android.internal.telephony.GsmAlphabet.TextEncodingDetails countGsmSeptets(java.lang.CharSequence charSequence, boolean z) {
        int i;
        int i2;
        int i3;
        if (!sDisableCountryEncodingCheck) {
            enableCountrySpecificEncodings();
        }
        int i4 = 160;
        int i5 = -1;
        if (sEnabledSingleShiftTables.length + sEnabledLockingShiftTables.length == 0) {
            com.android.internal.telephony.GsmAlphabet.TextEncodingDetails textEncodingDetails = new com.android.internal.telephony.GsmAlphabet.TextEncodingDetails();
            int countGsmSeptetsUsingTables = countGsmSeptetsUsingTables(charSequence, z, 0, 0);
            if (countGsmSeptetsUsingTables == -1) {
                return null;
            }
            textEncodingDetails.codeUnitSize = 1;
            textEncodingDetails.codeUnitCount = countGsmSeptetsUsingTables;
            if (countGsmSeptetsUsingTables > 160) {
                textEncodingDetails.msgCount = (countGsmSeptetsUsingTables + 152) / 153;
                textEncodingDetails.codeUnitsRemaining = (textEncodingDetails.msgCount * 153) - countGsmSeptetsUsingTables;
            } else {
                textEncodingDetails.msgCount = 1;
                textEncodingDetails.codeUnitsRemaining = 160 - countGsmSeptetsUsingTables;
            }
            return textEncodingDetails;
        }
        int i6 = sHighestEnabledSingleShiftCode;
        java.util.ArrayList<com.android.internal.telephony.GsmAlphabet.LanguagePairCount> arrayList = new java.util.ArrayList(sEnabledLockingShiftTables.length + 1);
        arrayList.add(new com.android.internal.telephony.GsmAlphabet.LanguagePairCount(0));
        for (int i7 : sEnabledLockingShiftTables) {
            if (i7 != 0 && !sLanguageTables[i7].isEmpty()) {
                arrayList.add(new com.android.internal.telephony.GsmAlphabet.LanguagePairCount(i7));
            }
        }
        int length = charSequence.length();
        for (int i8 = 0; i8 < length && !arrayList.isEmpty(); i8++) {
            char charAt = charSequence.charAt(i8);
            if (charAt == 27) {
                android.util.Log.w(TAG, "countGsmSeptets() string contains Escape character, ignoring!");
            } else {
                for (com.android.internal.telephony.GsmAlphabet.LanguagePairCount languagePairCount : arrayList) {
                    if (sCharsToGsmTables[languagePairCount.languageCode].get(charAt, -1) == -1) {
                        for (int i9 = 0; i9 <= i6; i9++) {
                            if (languagePairCount.septetCounts[i9] != -1) {
                                if (sCharsToShiftTables[i9].get(charAt, -1) == -1) {
                                    if (z) {
                                        int[] iArr = languagePairCount.septetCounts;
                                        iArr[i9] = iArr[i9] + 1;
                                        int[] iArr2 = languagePairCount.unencodableCounts;
                                        iArr2[i9] = iArr2[i9] + 1;
                                    } else {
                                        languagePairCount.septetCounts[i9] = -1;
                                    }
                                } else {
                                    int[] iArr3 = languagePairCount.septetCounts;
                                    iArr3[i9] = iArr3[i9] + 2;
                                }
                            }
                        }
                    } else {
                        for (int i10 = 0; i10 <= i6; i10++) {
                            if (languagePairCount.septetCounts[i10] != -1) {
                                int[] iArr4 = languagePairCount.septetCounts;
                                iArr4[i10] = iArr4[i10] + 1;
                            }
                        }
                    }
                }
            }
        }
        com.android.internal.telephony.GsmAlphabet.TextEncodingDetails textEncodingDetails2 = new com.android.internal.telephony.GsmAlphabet.TextEncodingDetails();
        textEncodingDetails2.msgCount = Integer.MAX_VALUE;
        textEncodingDetails2.codeUnitSize = 1;
        int i11 = Integer.MAX_VALUE;
        for (com.android.internal.telephony.GsmAlphabet.LanguagePairCount languagePairCount2 : arrayList) {
            int i12 = 0;
            while (i12 <= i6) {
                int i13 = languagePairCount2.septetCounts[i12];
                if (i13 != i5) {
                    if (languagePairCount2.languageCode != 0 && i12 != 0) {
                        i = 8;
                    } else if (languagePairCount2.languageCode != 0 || i12 != 0) {
                        i = 5;
                    } else {
                        i = 0;
                    }
                    if (i13 + i > i4) {
                        if (i == 0) {
                            i = 1;
                        }
                        int i14 = 160 - (i + 6);
                        i3 = ((i13 + i14) - 1) / i14;
                        i2 = (i14 * i3) - i13;
                    } else {
                        i2 = (160 - i) - i13;
                        i3 = 1;
                    }
                    int i15 = languagePairCount2.unencodableCounts[i12];
                    if ((!z || i15 <= i11) && ((z && i15 < i11) || i3 < textEncodingDetails2.msgCount || (i3 == textEncodingDetails2.msgCount && i2 > textEncodingDetails2.codeUnitsRemaining))) {
                        textEncodingDetails2.msgCount = i3;
                        textEncodingDetails2.codeUnitCount = i13;
                        textEncodingDetails2.codeUnitsRemaining = i2;
                        textEncodingDetails2.languageTable = languagePairCount2.languageCode;
                        textEncodingDetails2.languageShiftTable = i12;
                        i11 = i15;
                    }
                }
                i12++;
                i4 = 160;
                i5 = -1;
            }
            i4 = 160;
            i5 = -1;
        }
        if (textEncodingDetails2.msgCount == Integer.MAX_VALUE) {
            return null;
        }
        return textEncodingDetails2;
    }

    public static int findGsmSeptetLimitIndex(java.lang.String str, int i, int i2, int i3, int i4) {
        int length = str.length();
        android.util.SparseIntArray sparseIntArray = sCharsToGsmTables[i3];
        android.util.SparseIntArray sparseIntArray2 = sCharsToShiftTables[i4];
        int i5 = 0;
        while (i < length) {
            if (sparseIntArray.get(str.charAt(i), -1) == -1) {
                if (sparseIntArray2.get(str.charAt(i), -1) == -1) {
                    i5++;
                } else {
                    i5 += 2;
                }
            } else {
                i5++;
            }
            if (i5 <= i2) {
                i++;
            } else {
                return i;
            }
        }
        return length;
    }

    public static synchronized void setEnabledSingleShiftTables(int[] iArr) {
        synchronized (com.android.internal.telephony.GsmAlphabet.class) {
            sEnabledSingleShiftTables = iArr;
            sDisableCountryEncodingCheck = true;
            if (iArr.length > 0) {
                sHighestEnabledSingleShiftCode = iArr[iArr.length - 1];
            } else {
                sHighestEnabledSingleShiftCode = 0;
            }
        }
    }

    public static synchronized void setEnabledLockingShiftTables(int[] iArr) {
        synchronized (com.android.internal.telephony.GsmAlphabet.class) {
            sEnabledLockingShiftTables = iArr;
            sDisableCountryEncodingCheck = true;
        }
    }

    public static synchronized int[] getEnabledSingleShiftTables() {
        int[] iArr;
        synchronized (com.android.internal.telephony.GsmAlphabet.class) {
            iArr = sEnabledSingleShiftTables;
        }
        return iArr;
    }

    public static synchronized int[] getEnabledLockingShiftTables() {
        int[] iArr;
        synchronized (com.android.internal.telephony.GsmAlphabet.class) {
            iArr = sEnabledLockingShiftTables;
        }
        return iArr;
    }

    private static void enableCountrySpecificEncodings() {
        android.content.res.Resources system = android.content.res.Resources.getSystem();
        sEnabledSingleShiftTables = system.getIntArray(com.android.internal.R.array.config_sms_enabled_single_shift_tables);
        sEnabledLockingShiftTables = system.getIntArray(com.android.internal.R.array.config_sms_enabled_locking_shift_tables);
        if (sEnabledSingleShiftTables.length > 0) {
            sHighestEnabledSingleShiftCode = sEnabledSingleShiftTables[sEnabledSingleShiftTables.length - 1];
        } else {
            sHighestEnabledSingleShiftCode = 0;
        }
    }

    static {
        enableCountrySpecificEncodings();
        int length = sLanguageTables.length;
        int length2 = sLanguageShiftTables.length;
        if (length != length2) {
            android.util.Log.e(TAG, "Error: language tables array length " + length + " != shift tables array length " + length2);
        }
        sCharsToGsmTables = new android.util.SparseIntArray[length];
        for (int i = 0; i < length; i++) {
            java.lang.String str = sLanguageTables[i];
            int length3 = str.length();
            if (length3 != 0 && length3 != 128) {
                android.util.Log.e(TAG, "Error: language tables index " + i + " length " + length3 + " (expected 128 or 0)");
            }
            android.util.SparseIntArray sparseIntArray = new android.util.SparseIntArray(length3);
            sCharsToGsmTables[i] = sparseIntArray;
            for (int i2 = 0; i2 < length3; i2++) {
                sparseIntArray.put(str.charAt(i2), i2);
            }
        }
        sCharsToShiftTables = new android.util.SparseIntArray[length2];
        for (int i3 = 0; i3 < length2; i3++) {
            java.lang.String str2 = sLanguageShiftTables[i3];
            int length4 = str2.length();
            if (length4 != 0 && length4 != 128) {
                android.util.Log.e(TAG, "Error: language shift tables index " + i3 + " length " + length4 + " (expected 128 or 0)");
            }
            android.util.SparseIntArray sparseIntArray2 = new android.util.SparseIntArray(length4);
            sCharsToShiftTables[i3] = sparseIntArray2;
            for (int i4 = 0; i4 < length4; i4++) {
                char charAt = str2.charAt(i4);
                if (charAt != ' ') {
                    sparseIntArray2.put(charAt, i4);
                }
            }
        }
    }

    private static class LanguagePairCount {
        final int languageCode;
        final int[] septetCounts;
        final int[] unencodableCounts;

        LanguagePairCount(int i) {
            this.languageCode = i;
            int i2 = com.android.internal.telephony.GsmAlphabet.sHighestEnabledSingleShiftCode;
            int i3 = i2 + 1;
            this.septetCounts = new int[i3];
            this.unencodableCounts = new int[i3];
            int i4 = 0;
            for (int i5 = 1; i5 <= i2; i5++) {
                if (com.android.internal.telephony.GsmAlphabet.sEnabledSingleShiftTables[i4] == i5) {
                    i4++;
                } else {
                    this.septetCounts[i5] = -1;
                }
            }
            if (i == 1 && i2 >= 1) {
                this.septetCounts[1] = -1;
            } else if (i == 3 && i2 >= 2) {
                this.septetCounts[2] = -1;
            }
        }
    }
}
