package android.telephony;

/* loaded from: classes3.dex */
public class PhoneNumberUtils {
    private static final java.lang.String BCD_CALLED_PARTY_EXTENDED = "*#abc";
    private static final java.lang.String BCD_EF_ADN_EXTENDED = "*#,N;";
    public static final int BCD_EXTENDED_TYPE_CALLED_PARTY = 2;
    public static final int BCD_EXTENDED_TYPE_EF_ADN = 1;
    private static final int CCC_LENGTH;
    private static final java.lang.String CLIR_OFF = "#31#";
    private static final java.lang.String CLIR_ON = "*31#";
    private static final boolean[] COUNTRY_CALLING_CALL;
    private static final boolean DBG = false;
    public static final int FORMAT_JAPAN = 2;
    public static final int FORMAT_NANP = 1;
    public static final int FORMAT_UNKNOWN = 0;
    private static final java.lang.String JAPAN_ISO_COUNTRY_CODE = "JP";
    private static final java.lang.String KOREA_ISO_COUNTRY_CODE = "KR";
    static final java.lang.String LOG_TAG = "PhoneNumberUtils";
    private static final java.lang.String NANP_IDP_STRING = "011";
    private static final int NANP_LENGTH = 10;
    private static final int NANP_STATE_DASH = 4;
    private static final int NANP_STATE_DIGIT = 1;
    private static final int NANP_STATE_ONE = 3;
    private static final int NANP_STATE_PLUS = 2;
    public static final char PAUSE = ',';
    private static final char PLUS_SIGN_CHAR = '+';
    private static final java.lang.String PLUS_SIGN_STRING = "+";
    private static final java.lang.String PREFIX_WPS = "*272";
    private static final java.lang.String PREFIX_WPS_CLIR_ACTIVATE = "*31#*272";
    private static final java.lang.String PREFIX_WPS_CLIR_DEACTIVATE = "#31#*272";
    public static final int TOA_International = 145;
    public static final int TOA_Unknown = 129;
    public static final char WAIT = ';';
    public static final char WILD = 'N';
    private static java.lang.String[] sConvertToEmergencyMap;
    private static final java.util.regex.Pattern GLOBAL_PHONE_NUMBER_PATTERN = java.util.regex.Pattern.compile("[\\+]?[0-9.-]+");
    private static int sMinMatch = 0;
    private static final java.lang.String[] NANP_COUNTRIES = {"US", "CA", "AS", "AI", "AG", "BS", "BB", "BM", "VG", "KY", "DM", "DO", "GD", "GU", "JM", "PR", "MS", "MP", "KN", "LC", "VC", "TT", "TC", "VI"};
    private static final android.util.SparseIntArray KEYPAD_MAP = new android.util.SparseIntArray();

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface BcdExtendType {
    }

    static {
        KEYPAD_MAP.put(97, 50);
        KEYPAD_MAP.put(98, 50);
        KEYPAD_MAP.put(99, 50);
        KEYPAD_MAP.put(65, 50);
        KEYPAD_MAP.put(66, 50);
        KEYPAD_MAP.put(67, 50);
        KEYPAD_MAP.put(100, 51);
        KEYPAD_MAP.put(101, 51);
        KEYPAD_MAP.put(102, 51);
        KEYPAD_MAP.put(68, 51);
        KEYPAD_MAP.put(69, 51);
        KEYPAD_MAP.put(70, 51);
        KEYPAD_MAP.put(103, 52);
        KEYPAD_MAP.put(104, 52);
        KEYPAD_MAP.put(105, 52);
        KEYPAD_MAP.put(71, 52);
        KEYPAD_MAP.put(72, 52);
        KEYPAD_MAP.put(73, 52);
        KEYPAD_MAP.put(106, 53);
        KEYPAD_MAP.put(107, 53);
        KEYPAD_MAP.put(108, 53);
        KEYPAD_MAP.put(74, 53);
        KEYPAD_MAP.put(75, 53);
        KEYPAD_MAP.put(76, 53);
        KEYPAD_MAP.put(109, 54);
        KEYPAD_MAP.put(110, 54);
        KEYPAD_MAP.put(111, 54);
        KEYPAD_MAP.put(77, 54);
        KEYPAD_MAP.put(78, 54);
        KEYPAD_MAP.put(79, 54);
        KEYPAD_MAP.put(112, 55);
        KEYPAD_MAP.put(113, 55);
        KEYPAD_MAP.put(114, 55);
        KEYPAD_MAP.put(115, 55);
        KEYPAD_MAP.put(80, 55);
        KEYPAD_MAP.put(81, 55);
        KEYPAD_MAP.put(82, 55);
        KEYPAD_MAP.put(83, 55);
        KEYPAD_MAP.put(116, 56);
        KEYPAD_MAP.put(117, 56);
        KEYPAD_MAP.put(118, 56);
        KEYPAD_MAP.put(84, 56);
        KEYPAD_MAP.put(85, 56);
        KEYPAD_MAP.put(86, 56);
        KEYPAD_MAP.put(119, 57);
        KEYPAD_MAP.put(120, 57);
        KEYPAD_MAP.put(121, 57);
        KEYPAD_MAP.put(122, 57);
        KEYPAD_MAP.put(87, 57);
        KEYPAD_MAP.put(88, 57);
        KEYPAD_MAP.put(89, 57);
        KEYPAD_MAP.put(90, 57);
        COUNTRY_CALLING_CALL = new boolean[]{true, true, false, false, false, false, false, true, false, false, false, false, false, false, false, false, false, false, false, false, true, false, false, false, false, false, false, true, true, false, true, true, true, true, true, false, true, false, false, true, true, false, false, true, true, true, true, true, true, true, false, true, true, true, true, true, true, true, true, false, true, true, true, true, true, true, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, true, true, true, false, true, false, false, true, true, true, true, true, true, true, false, false, true, false};
        CCC_LENGTH = COUNTRY_CALLING_CALL.length;
        sConvertToEmergencyMap = null;
    }

    public static boolean isISODigit(char c) {
        return c >= '0' && c <= '9';
    }

    public static final boolean is12Key(char c) {
        return (c >= '0' && c <= '9') || c == '*' || c == '#';
    }

    public static final boolean isDialable(char c) {
        return (c >= '0' && c <= '9') || c == '*' || c == '#' || c == '+' || c == 'N';
    }

    public static final boolean isReallyDialable(char c) {
        return (c >= '0' && c <= '9') || c == '*' || c == '#' || c == '+';
    }

    public static final boolean isNonSeparator(char c) {
        return (c >= '0' && c <= '9') || c == '*' || c == '#' || c == '+' || c == 'N' || c == ';' || c == ',';
    }

    public static final boolean isStartsPostDial(char c) {
        return c == ',' || c == ';';
    }

    private static boolean isPause(char c) {
        return c == 'p' || c == 'P';
    }

    private static boolean isToneWait(char c) {
        return c == 'w' || c == 'W';
    }

    private static int getMinMatch() {
        if (sMinMatch == 0) {
            sMinMatch = android.content.res.Resources.getSystem().getInteger(com.android.internal.R.integer.config_phonenumber_compare_min_match);
        }
        return sMinMatch;
    }

    public static int getMinMatchForTest() {
        return getMinMatch();
    }

    public static void setMinMatchForTest(int i) {
        sMinMatch = i;
    }

    private static boolean isSeparator(char c) {
        return !isDialable(c) && ('a' > c || c > 'z') && ('A' > c || c > 'Z');
    }

    /* JADX WARN: Code restructure failed: missing block: B:20:0x006b, code lost:
    
        if (r8 != null) goto L32;
     */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x006d, code lost:
    
        r8.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:22:0x007f, code lost:
    
        return r6;
     */
    /* JADX WARN: Code restructure failed: missing block: B:31:0x007c, code lost:
    
        if (r8 == null) goto L40;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:36:0x0084  */
    /* JADX WARN: Type inference failed for: r6v0 */
    /* JADX WARN: Type inference failed for: r6v1, types: [android.database.Cursor] */
    /* JADX WARN: Type inference failed for: r6v2 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static java.lang.String getNumberFromIntent(android.content.Intent intent, android.content.Context context) {
        java.lang.String scheme;
        android.database.Cursor cursor;
        android.net.Uri data = intent.getData();
        ?? r6 = 0;
        r6 = null;
        r6 = null;
        r6 = null;
        java.lang.String str = null;
        if (data == null || (scheme = data.getScheme()) == null) {
            return null;
        }
        if (scheme.equals(android.telecom.PhoneAccount.SCHEME_TEL) || scheme.equals("sip")) {
            return data.getSchemeSpecificPart();
        }
        if (context == null) {
            return null;
        }
        intent.resolveType(context);
        java.lang.String authority = data.getAuthority();
        java.lang.String str2 = android.provider.Contacts.AUTHORITY.equals(authority) ? "number" : android.provider.ContactsContract.AUTHORITY.equals(authority) ? "data1" : null;
        try {
            try {
                cursor = context.getContentResolver().query(data, new java.lang.String[]{str2}, null, null, null);
                if (cursor != null) {
                    try {
                        if (cursor.moveToFirst()) {
                            str = cursor.getString(cursor.getColumnIndex(str2));
                        }
                    } catch (java.lang.RuntimeException e) {
                        e = e;
                        com.android.telephony.Rlog.e(LOG_TAG, "Error getting phone number.", e);
                    }
                }
            } catch (java.lang.RuntimeException e2) {
                e = e2;
                cursor = null;
            } catch (java.lang.Throwable th) {
                th = th;
                if (r6 != 0) {
                }
                throw th;
            }
        } catch (java.lang.Throwable th2) {
            th = th2;
            r6 = context;
            if (r6 != 0) {
                r6.close();
            }
            throw th;
        }
    }

    public static java.lang.String extractNetworkPortion(java.lang.String str) {
        if (str == null) {
            return null;
        }
        int length = str.length();
        java.lang.StringBuilder sb = new java.lang.StringBuilder(length);
        for (int i = 0; i < length; i++) {
            char charAt = str.charAt(i);
            int digit = java.lang.Character.digit(charAt, 10);
            if (digit != -1) {
                sb.append(digit);
            } else if (charAt != '+') {
                if (isDialable(charAt)) {
                    sb.append(charAt);
                } else if (isStartsPostDial(charAt)) {
                    break;
                }
            } else {
                java.lang.String sb2 = sb.toString();
                if (sb2.length() == 0 || sb2.equals(CLIR_ON) || sb2.equals(CLIR_OFF)) {
                    sb.append(charAt);
                }
            }
        }
        return sb.toString();
    }

    public static java.lang.String extractNetworkPortionAlt(java.lang.String str) {
        if (str == null) {
            return null;
        }
        int length = str.length();
        java.lang.StringBuilder sb = new java.lang.StringBuilder(length);
        boolean z = false;
        for (int i = 0; i < length; i++) {
            char charAt = str.charAt(i);
            if (charAt == '+') {
                if (z) {
                    continue;
                } else {
                    z = true;
                }
            }
            if (isDialable(charAt)) {
                sb.append(charAt);
            } else if (isStartsPostDial(charAt)) {
                break;
            }
        }
        return sb.toString();
    }

    public static java.lang.String stripSeparators(java.lang.String str) {
        if (str == null) {
            return null;
        }
        int length = str.length();
        java.lang.StringBuilder sb = new java.lang.StringBuilder(length);
        for (int i = 0; i < length; i++) {
            char charAt = str.charAt(i);
            int digit = java.lang.Character.digit(charAt, 10);
            if (digit != -1) {
                sb.append(digit);
            } else if (isNonSeparator(charAt)) {
                sb.append(charAt);
            }
        }
        return sb.toString();
    }

    public static java.lang.String convertAndStrip(java.lang.String str) {
        return stripSeparators(convertKeypadLettersToDigits(str));
    }

    public static java.lang.String convertPreDial(java.lang.String str) {
        if (str == null) {
            return null;
        }
        int length = str.length();
        java.lang.StringBuilder sb = new java.lang.StringBuilder(length);
        for (int i = 0; i < length; i++) {
            char charAt = str.charAt(i);
            if (isPause(charAt)) {
                charAt = ',';
            } else if (isToneWait(charAt)) {
                charAt = ';';
            }
            sb.append(charAt);
        }
        return sb.toString();
    }

    private static int minPositive(int i, int i2) {
        if (i >= 0 && i2 >= 0) {
            return i < i2 ? i : i2;
        }
        if (i >= 0) {
            return i;
        }
        if (i2 >= 0) {
            return i2;
        }
        return -1;
    }

    private static void log(java.lang.String str) {
        com.android.telephony.Rlog.d(LOG_TAG, str);
    }

    private static int indexOfLastNetworkChar(java.lang.String str) {
        int length = str.length();
        int minPositive = minPositive(str.indexOf(44), str.indexOf(59));
        if (minPositive < 0) {
            return length - 1;
        }
        return minPositive - 1;
    }

    public static java.lang.String extractPostDialPortion(java.lang.String str) {
        if (str == null) {
            return null;
        }
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        int length = str.length();
        for (int indexOfLastNetworkChar = indexOfLastNetworkChar(str) + 1; indexOfLastNetworkChar < length; indexOfLastNetworkChar++) {
            char charAt = str.charAt(indexOfLastNetworkChar);
            if (isNonSeparator(charAt)) {
                sb.append(charAt);
            }
        }
        return sb.toString();
    }

    @java.lang.Deprecated
    public static boolean compare(java.lang.String str, java.lang.String str2) {
        return compare(str, str2, false);
    }

    @java.lang.Deprecated
    public static boolean compare(android.content.Context context, java.lang.String str, java.lang.String str2) {
        return compare(str, str2, context.getResources().getBoolean(com.android.internal.R.bool.config_use_strict_phone_number_comparation));
    }

    public static boolean compare(java.lang.String str, java.lang.String str2, boolean z) {
        return z ? compareStrictly(str, str2) : compareLoosely(str, str2);
    }

    public static boolean compareLoosely(java.lang.String str, java.lang.String str2) {
        boolean z;
        int minMatch = getMinMatch();
        if (str == null || str2 == null) {
            return str == str2;
        }
        if (str.length() == 0 || str2.length() == 0) {
            return false;
        }
        int indexOfLastNetworkChar = indexOfLastNetworkChar(str);
        int indexOfLastNetworkChar2 = indexOfLastNetworkChar(str2);
        int i = 0;
        int i2 = 0;
        int i3 = 0;
        while (indexOfLastNetworkChar >= 0 && indexOfLastNetworkChar2 >= 0) {
            char charAt = str.charAt(indexOfLastNetworkChar);
            if (isDialable(charAt)) {
                z = false;
            } else {
                indexOfLastNetworkChar--;
                i++;
                z = true;
            }
            char charAt2 = str2.charAt(indexOfLastNetworkChar2);
            if (!isDialable(charAt2)) {
                indexOfLastNetworkChar2--;
                i2++;
                z = true;
            }
            if (!z) {
                if (charAt2 != charAt && charAt != 'N' && charAt2 != 'N') {
                    break;
                }
                indexOfLastNetworkChar--;
                indexOfLastNetworkChar2--;
                i3++;
            }
        }
        if (i3 < minMatch) {
            int length = str.length() - i;
            return length == str2.length() - i2 && length == i3;
        }
        if (i3 >= minMatch && (indexOfLastNetworkChar < 0 || indexOfLastNetworkChar2 < 0)) {
            return true;
        }
        int i4 = indexOfLastNetworkChar + 1;
        if (matchIntlPrefix(str, i4) && matchIntlPrefix(str2, indexOfLastNetworkChar2 + 1)) {
            return true;
        }
        if (matchTrunkPrefix(str, i4) && matchIntlPrefixAndCC(str2, indexOfLastNetworkChar2 + 1)) {
            return true;
        }
        return matchTrunkPrefix(str2, indexOfLastNetworkChar2 + 1) && matchIntlPrefixAndCC(str, i4);
    }

    public static boolean compareStrictly(java.lang.String str, java.lang.String str2) {
        return compareStrictly(str, str2, true);
    }

    public static boolean compareStrictly(java.lang.String str, java.lang.String str2, boolean z) {
        int tryGetTrunkPrefixOmittedIndex;
        boolean z2;
        int tryGetTrunkPrefixOmittedIndex2;
        boolean z3;
        boolean z4;
        boolean z5;
        boolean z6;
        if (str == null || str2 == null) {
            return str == str2;
        }
        if (str.length() == 0 && str2.length() == 0) {
            return false;
        }
        android.telephony.PhoneNumberUtils.CountryCallingCodeAndNewIndex tryGetCountryCallingCodeAndNewIndex = tryGetCountryCallingCodeAndNewIndex(str, z);
        android.telephony.PhoneNumberUtils.CountryCallingCodeAndNewIndex tryGetCountryCallingCodeAndNewIndex2 = tryGetCountryCallingCodeAndNewIndex(str2, z);
        if (tryGetCountryCallingCodeAndNewIndex != null && tryGetCountryCallingCodeAndNewIndex2 != null) {
            if (tryGetCountryCallingCodeAndNewIndex.countryCallingCode != tryGetCountryCallingCodeAndNewIndex2.countryCallingCode) {
                return false;
            }
            tryGetTrunkPrefixOmittedIndex = tryGetCountryCallingCodeAndNewIndex.newIndex;
            tryGetTrunkPrefixOmittedIndex2 = tryGetCountryCallingCodeAndNewIndex2.newIndex;
            z4 = true;
            z2 = false;
            z3 = false;
            z5 = false;
        } else if (tryGetCountryCallingCodeAndNewIndex == null && tryGetCountryCallingCodeAndNewIndex2 == null) {
            tryGetTrunkPrefixOmittedIndex = 0;
            tryGetTrunkPrefixOmittedIndex2 = 0;
            z2 = false;
            z4 = false;
            z3 = false;
            z5 = false;
        } else {
            if (tryGetCountryCallingCodeAndNewIndex != null) {
                tryGetTrunkPrefixOmittedIndex = tryGetCountryCallingCodeAndNewIndex.newIndex;
                z2 = false;
            } else {
                tryGetTrunkPrefixOmittedIndex = tryGetTrunkPrefixOmittedIndex(str2, 0);
                if (tryGetTrunkPrefixOmittedIndex < 0) {
                    tryGetTrunkPrefixOmittedIndex = 0;
                    z2 = false;
                } else {
                    z2 = true;
                }
            }
            if (tryGetCountryCallingCodeAndNewIndex2 != null) {
                tryGetTrunkPrefixOmittedIndex2 = tryGetCountryCallingCodeAndNewIndex2.newIndex;
                z3 = true;
                z4 = false;
                z5 = false;
            } else {
                tryGetTrunkPrefixOmittedIndex2 = tryGetTrunkPrefixOmittedIndex(str2, 0);
                if (tryGetTrunkPrefixOmittedIndex2 < 0) {
                    z3 = true;
                    tryGetTrunkPrefixOmittedIndex2 = 0;
                    z4 = false;
                    z5 = false;
                } else {
                    z3 = true;
                    z5 = true;
                    z4 = false;
                }
            }
        }
        int length = str.length() - 1;
        int length2 = str2.length() - 1;
        while (length >= tryGetTrunkPrefixOmittedIndex && length2 >= tryGetTrunkPrefixOmittedIndex2) {
            char charAt = str.charAt(length);
            char charAt2 = str2.charAt(length2);
            if (!isSeparator(charAt)) {
                z6 = false;
            } else {
                length--;
                z6 = true;
            }
            if (isSeparator(charAt2)) {
                length2--;
                z6 = true;
            }
            if (!z6) {
                if (charAt != charAt2) {
                    return false;
                }
                length--;
                length2--;
            }
        }
        if (z3) {
            if ((z2 && tryGetTrunkPrefixOmittedIndex <= length) || !checkPrefixIsIgnorable(str, tryGetTrunkPrefixOmittedIndex, length)) {
                if (z) {
                    return compare(str, str2, false);
                }
                return false;
            }
            if ((z5 && tryGetTrunkPrefixOmittedIndex2 <= length2) || !checkPrefixIsIgnorable(str2, tryGetTrunkPrefixOmittedIndex, length2)) {
                if (z) {
                    return compare(str, str2, false);
                }
                return false;
            }
        } else {
            boolean z7 = !z4;
            while (length >= tryGetTrunkPrefixOmittedIndex) {
                char charAt3 = str.charAt(length);
                if (isDialable(charAt3)) {
                    if (!z7 || tryGetISODigit(charAt3) != 1) {
                        return false;
                    }
                    z7 = false;
                }
                length--;
            }
            while (length2 >= tryGetTrunkPrefixOmittedIndex2) {
                char charAt4 = str2.charAt(length2);
                if (isDialable(charAt4)) {
                    if (!z7 || tryGetISODigit(charAt4) != 1) {
                        return false;
                    }
                    z7 = false;
                }
                length2--;
            }
        }
        return true;
    }

    public static java.lang.String toCallerIDMinMatch(java.lang.String str) {
        return internalGetStrippedReversed(extractNetworkPortionAlt(str), getMinMatch());
    }

    public static java.lang.String getStrippedReversed(java.lang.String str) {
        java.lang.String extractNetworkPortionAlt = extractNetworkPortionAlt(str);
        if (extractNetworkPortionAlt == null) {
            return null;
        }
        return internalGetStrippedReversed(extractNetworkPortionAlt, extractNetworkPortionAlt.length());
    }

    private static java.lang.String internalGetStrippedReversed(java.lang.String str, int i) {
        if (str == null) {
            return null;
        }
        java.lang.StringBuilder sb = new java.lang.StringBuilder(i);
        int length = str.length();
        for (int i2 = length - 1; i2 >= 0 && length - i2 <= i; i2--) {
            sb.append(str.charAt(i2));
        }
        return sb.toString();
    }

    public static java.lang.String stringFromStringAndTOA(java.lang.String str, int i) {
        if (str == null) {
            return null;
        }
        if (i == 145 && str.length() > 0 && str.charAt(0) != '+') {
            return PLUS_SIGN_STRING + str;
        }
        return str;
    }

    public static int toaFromString(java.lang.String str) {
        if (str != null && str.length() > 0 && str.charAt(0) == '+') {
            return 145;
        }
        return 129;
    }

    @java.lang.Deprecated
    public static java.lang.String calledPartyBCDToString(byte[] bArr, int i, int i2) {
        return calledPartyBCDToString(bArr, i, i2, 1);
    }

    public static java.lang.String calledPartyBCDToString(byte[] bArr, int i, int i2, int i3) {
        boolean z;
        java.lang.StringBuilder sb = new java.lang.StringBuilder((i2 * 2) + 1);
        if (i2 < 2) {
            return "";
        }
        if ((bArr[i] & 240) != 144) {
            z = false;
        } else {
            z = true;
        }
        internalCalledPartyBCDFragmentToString(sb, bArr, i + 1, i2 - 1, i3);
        if (z && sb.length() == 0) {
            return "";
        }
        if (z) {
            java.lang.String sb2 = sb.toString();
            java.util.regex.Matcher matcher = java.util.regex.Pattern.compile("(^[#*])(.*)([#*])(.*)(#)$").matcher(sb2);
            if (matcher.matches()) {
                if ("".equals(matcher.group(2))) {
                    java.lang.StringBuilder sb3 = new java.lang.StringBuilder();
                    sb3.append(matcher.group(1));
                    sb3.append(matcher.group(3));
                    sb3.append(matcher.group(4));
                    sb3.append(matcher.group(5));
                    sb3.append(PLUS_SIGN_STRING);
                    sb = sb3;
                } else {
                    java.lang.StringBuilder sb4 = new java.lang.StringBuilder();
                    sb4.append(matcher.group(1));
                    sb4.append(matcher.group(2));
                    sb4.append(matcher.group(3));
                    sb4.append(PLUS_SIGN_STRING);
                    sb4.append(matcher.group(4));
                    sb4.append(matcher.group(5));
                    sb = sb4;
                }
            } else {
                java.util.regex.Matcher matcher2 = java.util.regex.Pattern.compile("(^[#*])(.*)([#*])(.*)").matcher(sb2);
                if (matcher2.matches()) {
                    java.lang.StringBuilder sb5 = new java.lang.StringBuilder();
                    sb5.append(matcher2.group(1));
                    sb5.append(matcher2.group(2));
                    sb5.append(matcher2.group(3));
                    sb5.append(PLUS_SIGN_STRING);
                    sb5.append(matcher2.group(4));
                    sb = sb5;
                } else {
                    sb = new java.lang.StringBuilder();
                    sb.append(PLUS_SIGN_CHAR);
                    sb.append(sb2);
                }
            }
        }
        return sb.toString();
    }

    private static void internalCalledPartyBCDFragmentToString(java.lang.StringBuilder sb, byte[] bArr, int i, int i2, int i3) {
        char bcdToChar;
        char bcdToChar2;
        int i4 = i;
        while (true) {
            int i5 = i2 + i;
            if (i4 >= i5 || (bcdToChar = bcdToChar((byte) (bArr[i4] & 15), i3)) == 0) {
                return;
            }
            sb.append(bcdToChar);
            byte b = (byte) ((bArr[i4] >> 4) & 15);
            if ((b == 15 && i4 + 1 == i5) || (bcdToChar2 = bcdToChar(b, i3)) == 0) {
                return;
            }
            sb.append(bcdToChar2);
            i4++;
        }
    }

    @java.lang.Deprecated
    public static java.lang.String calledPartyBCDFragmentToString(byte[] bArr, int i, int i2) {
        return calledPartyBCDFragmentToString(bArr, i, i2, 1);
    }

    public static java.lang.String calledPartyBCDFragmentToString(byte[] bArr, int i, int i2, int i3) {
        java.lang.StringBuilder sb = new java.lang.StringBuilder(i2 * 2);
        internalCalledPartyBCDFragmentToString(sb, bArr, i, i2, i3);
        return sb.toString();
    }

    private static char bcdToChar(byte b, int i) {
        java.lang.String str;
        int i2;
        if (b < 10) {
            return (char) (b + 48);
        }
        if (1 == i) {
            str = BCD_EF_ADN_EXTENDED;
        } else if (2 != i) {
            str = null;
        } else {
            str = BCD_CALLED_PARTY_EXTENDED;
        }
        if (str == null || (i2 = b - 10) >= str.length()) {
            return (char) 0;
        }
        return str.charAt(i2);
    }

    private static int charToBCD(char c, int i) {
        java.lang.String str;
        if ('0' <= c && c <= '9') {
            return c - '0';
        }
        if (1 == i) {
            str = BCD_EF_ADN_EXTENDED;
        } else if (2 != i) {
            str = null;
        } else {
            str = BCD_CALLED_PARTY_EXTENDED;
        }
        if (str == null || str.indexOf(c) == -1) {
            throw new java.lang.RuntimeException("invalid char for BCD " + c);
        }
        return str.indexOf(c) + 10;
    }

    public static boolean isWellFormedSmsAddress(java.lang.String str) {
        java.lang.String extractNetworkPortion = extractNetworkPortion(str);
        return (extractNetworkPortion.equals(PLUS_SIGN_STRING) || android.text.TextUtils.isEmpty(extractNetworkPortion) || !isDialable(extractNetworkPortion)) ? false : true;
    }

    public static boolean isGlobalPhoneNumber(java.lang.String str) {
        if (android.text.TextUtils.isEmpty(str)) {
            return false;
        }
        return GLOBAL_PHONE_NUMBER_PATTERN.matcher(str).matches();
    }

    private static boolean isDialable(java.lang.String str) {
        int length = str.length();
        for (int i = 0; i < length; i++) {
            if (!isDialable(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    private static boolean isNonSeparator(java.lang.String str) {
        int length = str.length();
        for (int i = 0; i < length; i++) {
            if (!isNonSeparator(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static byte[] networkPortionToCalledPartyBCD(java.lang.String str) {
        return numberToCalledPartyBCDHelper(extractNetworkPortion(str), false, 1);
    }

    public static byte[] networkPortionToCalledPartyBCDWithLength(java.lang.String str) {
        return numberToCalledPartyBCDHelper(extractNetworkPortion(str), true, 1);
    }

    @java.lang.Deprecated
    public static byte[] numberToCalledPartyBCD(java.lang.String str) {
        return numberToCalledPartyBCD(str, 1);
    }

    public static byte[] numberToCalledPartyBCD(java.lang.String str, int i) {
        return numberToCalledPartyBCDHelper(str, false, i);
    }

    private static byte[] numberToCalledPartyBCDHelper(java.lang.String str, boolean z, int i) {
        int length = str.length();
        char c = 0;
        boolean z2 = str.indexOf(43) != -1;
        int i2 = z2 ? length - 1 : length;
        if (i2 == 0) {
            return null;
        }
        int i3 = (i2 + 1) / 2;
        int i4 = z ? 2 : 1;
        int i5 = i3 + i4;
        byte[] bArr = new byte[i5];
        int i6 = 0;
        for (int i7 = 0; i7 < length; i7++) {
            char charAt = str.charAt(i7);
            if (charAt != '+') {
                int i8 = (i6 >> 1) + i4;
                bArr[i8] = (byte) (((byte) ((charToBCD(charAt, i) & 15) << ((i6 & 1) == 1 ? 4 : 0))) | bArr[i8]);
                i6++;
            }
        }
        if ((i6 & 1) == 1) {
            int i9 = i4 + (i6 >> 1);
            bArr[i9] = (byte) (bArr[i9] | 240);
        }
        if (z) {
            bArr[0] = (byte) (i5 - 1);
            c = 1;
        }
        bArr[c] = (byte) (z2 ? 145 : 129);
        return bArr;
    }

    @java.lang.Deprecated
    public static java.lang.String formatNumber(java.lang.String str) {
        android.text.SpannableStringBuilder spannableStringBuilder = new android.text.SpannableStringBuilder(str);
        formatNumber(spannableStringBuilder, getFormatTypeForLocale(java.util.Locale.getDefault()));
        return spannableStringBuilder.toString();
    }

    @java.lang.Deprecated
    public static java.lang.String formatNumber(java.lang.String str, int i) {
        android.text.SpannableStringBuilder spannableStringBuilder = new android.text.SpannableStringBuilder(str);
        formatNumber(spannableStringBuilder, i);
        return spannableStringBuilder.toString();
    }

    @java.lang.Deprecated
    public static int getFormatTypeForLocale(java.util.Locale locale) {
        return getFormatTypeFromCountryCode(locale.getCountry());
    }

    @java.lang.Deprecated
    public static void formatNumber(android.text.Editable editable, int i) {
        if (editable.length() > 2 && editable.charAt(0) == '+') {
            i = 1;
            if (editable.charAt(1) != '1') {
                if (editable.length() >= 3 && editable.charAt(1) == '8' && editable.charAt(2) == '1') {
                    i = 2;
                } else {
                    i = 0;
                }
            }
        }
        switch (i) {
            case 0:
                removeDashes(editable);
                break;
            case 1:
                formatNanpNumber(editable);
                break;
            case 2:
                formatJapaneseNumber(editable);
                break;
        }
    }

    @java.lang.Deprecated
    public static void formatNanpNumber(android.text.Editable editable) {
        int length = editable.length();
        if (length > "+1-nnn-nnn-nnnn".length() || length <= 5) {
            return;
        }
        java.lang.CharSequence subSequence = editable.subSequence(0, length);
        removeDashes(editable);
        int length2 = editable.length();
        int[] iArr = new int[3];
        int i = 0;
        int i2 = 0;
        char c = 1;
        for (int i3 = 0; i3 < length2; i3++) {
            switch (editable.charAt(i3)) {
                case '+':
                    if (i3 == 0) {
                        c = 2;
                    } else {
                        editable.replace(0, length2, subSequence);
                        break;
                    }
                case ',':
                case '.':
                case '/':
                default:
                    editable.replace(0, length2, subSequence);
                    break;
                case '-':
                    c = 4;
                case '1':
                    if (i == 0 || c == 2) {
                        c = 3;
                    }
                case '0':
                case '2':
                case '3':
                case '4':
                case '5':
                case '6':
                case '7':
                case '8':
                case '9':
                    if (c == 2) {
                        editable.replace(0, length2, subSequence);
                        break;
                    } else {
                        if (c == 3) {
                            iArr[i2] = i3;
                            i2++;
                        } else if (c != 4 && (i == 3 || i == 6)) {
                            iArr[i2] = i3;
                            i2++;
                        }
                        i++;
                        c = 1;
                    }
            }
            return;
        }
        if (i == 7) {
            i2--;
        }
        for (int i4 = 0; i4 < i2; i4++) {
            int i5 = iArr[i4] + i4;
            editable.replace(i5, i5, com.android.internal.content.NativeLibraryHelper.CLEAR_ABI_OVERRIDE);
        }
        for (int length3 = editable.length(); length3 > 0; length3--) {
            int i6 = length3 - 1;
            if (editable.charAt(i6) == '-') {
                editable.delete(i6, length3);
            } else {
                return;
            }
        }
    }

    @java.lang.Deprecated
    public static void formatJapaneseNumber(android.text.Editable editable) {
        android.telephony.JapanesePhoneNumberFormatter.format(editable);
    }

    private static void removeDashes(android.text.Editable editable) {
        int i = 0;
        while (i < editable.length()) {
            if (editable.charAt(i) == '-') {
                editable.delete(i, i + 1);
            } else {
                i++;
            }
        }
    }

    public static java.lang.String formatNumberToE164(java.lang.String str, java.lang.String str2) {
        if (str2 != null) {
            str2 = str2.toUpperCase(java.util.Locale.ROOT);
        }
        return formatNumberInternal(str, str2, com.android.i18n.phonenumbers.PhoneNumberUtil.PhoneNumberFormat.E164);
    }

    public static java.lang.String formatNumberToRFC3966(java.lang.String str, java.lang.String str2) {
        if (str2 != null) {
            str2 = str2.toUpperCase(java.util.Locale.ROOT);
        }
        return formatNumberInternal(str, str2, com.android.i18n.phonenumbers.PhoneNumberUtil.PhoneNumberFormat.RFC3966);
    }

    private static java.lang.String formatNumberInternal(java.lang.String str, java.lang.String str2, com.android.i18n.phonenumbers.PhoneNumberUtil.PhoneNumberFormat phoneNumberFormat) {
        com.android.i18n.phonenumbers.PhoneNumberUtil phoneNumberUtil = com.android.i18n.phonenumbers.PhoneNumberUtil.getInstance();
        try {
            com.android.i18n.phonenumbers.Phonenumber.PhoneNumber parse = phoneNumberUtil.parse(str, str2);
            if (phoneNumberUtil.isValidNumber(parse)) {
                return phoneNumberUtil.format(parse, phoneNumberFormat);
            }
            return null;
        } catch (com.android.i18n.phonenumbers.NumberParseException e) {
            return null;
        }
    }

    public static boolean isInternationalNumber(java.lang.String str, java.lang.String str2) {
        if (android.text.TextUtils.isEmpty(str) || str.startsWith("#") || str.startsWith("*")) {
            return false;
        }
        if (str2 != null) {
            str2 = str2.toUpperCase(java.util.Locale.ROOT);
        }
        com.android.i18n.phonenumbers.PhoneNumberUtil phoneNumberUtil = com.android.i18n.phonenumbers.PhoneNumberUtil.getInstance();
        try {
            return phoneNumberUtil.parseAndKeepRawInput(str, str2).getCountryCode() != phoneNumberUtil.getCountryCodeForRegion(str2);
        } catch (com.android.i18n.phonenumbers.NumberParseException e) {
            return false;
        }
    }

    public static java.lang.String formatNumber(java.lang.String str, java.lang.String str2) {
        java.lang.String formatInOriginalFormat;
        if (str.startsWith("#") || str.startsWith("*")) {
            return str;
        }
        if (str2 != null) {
            str2 = str2.toUpperCase(java.util.Locale.ROOT);
        }
        com.android.i18n.phonenumbers.PhoneNumberUtil phoneNumberUtil = com.android.i18n.phonenumbers.PhoneNumberUtil.getInstance();
        try {
            com.android.i18n.phonenumbers.Phonenumber.PhoneNumber parseAndKeepRawInput = phoneNumberUtil.parseAndKeepRawInput(str, str2);
            if (KOREA_ISO_COUNTRY_CODE.equalsIgnoreCase(str2) && parseAndKeepRawInput.getCountryCode() == phoneNumberUtil.getCountryCodeForRegion(KOREA_ISO_COUNTRY_CODE) && parseAndKeepRawInput.getCountryCodeSource() == com.android.i18n.phonenumbers.Phonenumber.PhoneNumber.CountryCodeSource.FROM_NUMBER_WITH_PLUS_SIGN) {
                formatInOriginalFormat = phoneNumberUtil.format(parseAndKeepRawInput, com.android.i18n.phonenumbers.PhoneNumberUtil.PhoneNumberFormat.NATIONAL);
            } else if (JAPAN_ISO_COUNTRY_CODE.equalsIgnoreCase(str2) && parseAndKeepRawInput.getCountryCode() == phoneNumberUtil.getCountryCodeForRegion(JAPAN_ISO_COUNTRY_CODE) && parseAndKeepRawInput.getCountryCodeSource() == com.android.i18n.phonenumbers.Phonenumber.PhoneNumber.CountryCodeSource.FROM_NUMBER_WITH_PLUS_SIGN) {
                formatInOriginalFormat = phoneNumberUtil.format(parseAndKeepRawInput, com.android.i18n.phonenumbers.PhoneNumberUtil.PhoneNumberFormat.NATIONAL);
            } else {
                formatInOriginalFormat = phoneNumberUtil.formatInOriginalFormat(parseAndKeepRawInput, str2);
            }
            return formatInOriginalFormat;
        } catch (com.android.i18n.phonenumbers.NumberParseException e) {
            return null;
        }
    }

    public static java.lang.String formatNumber(java.lang.String str, java.lang.String str2, java.lang.String str3) {
        if (str3 != null) {
            str3 = str3.toUpperCase(java.util.Locale.ROOT);
        }
        int length = str.length();
        for (int i = 0; i < length; i++) {
            if (!isDialable(str.charAt(i))) {
                return str;
            }
        }
        com.android.i18n.phonenumbers.PhoneNumberUtil phoneNumberUtil = com.android.i18n.phonenumbers.PhoneNumberUtil.getInstance();
        if (str2 != null && str2.length() >= 2 && str2.charAt(0) == '+') {
            try {
                java.lang.String regionCodeForNumber = phoneNumberUtil.getRegionCodeForNumber(phoneNumberUtil.parse(str2, "ZZ"));
                if (!android.text.TextUtils.isEmpty(regionCodeForNumber)) {
                    if (normalizeNumber(str).indexOf(str2.substring(1)) <= 0) {
                        str3 = regionCodeForNumber;
                    }
                }
            } catch (com.android.i18n.phonenumbers.NumberParseException e) {
            }
        }
        java.lang.String formatNumber = formatNumber(str, str3);
        return formatNumber != null ? formatNumber : str;
    }

    public static java.lang.String normalizeNumber(java.lang.String str) {
        if (android.text.TextUtils.isEmpty(str)) {
            return "";
        }
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        int length = str.length();
        for (int i = 0; i < length; i++) {
            char charAt = str.charAt(i);
            int digit = java.lang.Character.digit(charAt, 10);
            if (digit != -1) {
                sb.append(digit);
            } else if (sb.length() == 0 && charAt == '+') {
                sb.append(charAt);
            } else if ((charAt >= 'a' && charAt <= 'z') || (charAt >= 'A' && charAt <= 'Z')) {
                return normalizeNumber(convertKeypadLettersToDigits(str));
            }
        }
        return sb.toString();
    }

    public static java.lang.String replaceUnicodeDigits(java.lang.String str) {
        java.lang.StringBuilder sb = new java.lang.StringBuilder(str.length());
        for (char c : str.toCharArray()) {
            int digit = java.lang.Character.digit(c, 10);
            if (digit != -1) {
                sb.append(digit);
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    @java.lang.Deprecated
    public static boolean isEmergencyNumber(java.lang.String str) {
        return isEmergencyNumber(getDefaultVoiceSubId(), str);
    }

    @java.lang.Deprecated
    public static boolean isEmergencyNumber(int i, java.lang.String str) {
        return isEmergencyNumberInternal(i, str);
    }

    private static boolean isEmergencyNumberInternal(int i, java.lang.String str) {
        try {
            return android.telephony.TelephonyManager.getDefault().isEmergencyNumber(str);
        } catch (java.lang.RuntimeException e) {
            com.android.telephony.Rlog.e(LOG_TAG, "isEmergencyNumberInternal: RuntimeException: " + e);
            return false;
        }
    }

    @java.lang.Deprecated
    public static boolean isLocalEmergencyNumber(android.content.Context context, java.lang.String str) {
        return isEmergencyNumberInternal(getDefaultVoiceSubId(), str);
    }

    public static boolean isVoiceMailNumber(java.lang.String str) {
        return isVoiceMailNumber(android.telephony.SubscriptionManager.getDefaultSubscriptionId(), str);
    }

    public static boolean isVoiceMailNumber(int i, java.lang.String str) {
        return isVoiceMailNumber(null, i, str);
    }

    @android.annotation.SystemApi
    public static boolean isVoiceMailNumber(android.content.Context context, int i, java.lang.String str) {
        android.telephony.TelephonyManager from;
        boolean z;
        android.telephony.CarrierConfigManager carrierConfigManager;
        android.os.PersistableBundle configForSubId;
        try {
            if (context == null) {
                from = android.telephony.TelephonyManager.getDefault();
            } else {
                from = android.telephony.TelephonyManager.from(context);
            }
            java.lang.String voiceMailNumber = from.getVoiceMailNumber(i);
            java.lang.String line1Number = from.getLine1Number(i);
            java.lang.String extractNetworkPortionAlt = extractNetworkPortionAlt(str);
            if (android.text.TextUtils.isEmpty(extractNetworkPortionAlt)) {
                return false;
            }
            if (context != null && (carrierConfigManager = (android.telephony.CarrierConfigManager) context.getSystemService("carrier_config")) != null && (configForSubId = carrierConfigManager.getConfigForSubId(i)) != null) {
                z = configForSubId.getBoolean(android.telephony.CarrierConfigManager.KEY_MDN_IS_ADDITIONAL_VOICEMAIL_NUMBER_BOOL);
            } else {
                z = false;
            }
            if (z) {
                return compare(extractNetworkPortionAlt, voiceMailNumber) || compare(extractNetworkPortionAlt, line1Number);
            }
            return compare(extractNetworkPortionAlt, voiceMailNumber);
        } catch (java.lang.SecurityException e) {
            return false;
        }
    }

    public static java.lang.String convertKeypadLettersToDigits(java.lang.String str) {
        if (str == null) {
            return str;
        }
        int length = str.length();
        if (length == 0) {
            return str;
        }
        char[] charArray = str.toCharArray();
        for (int i = 0; i < length; i++) {
            char c = charArray[i];
            charArray[i] = (char) KEYPAD_MAP.get(c, c);
        }
        return new java.lang.String(charArray);
    }

    public static java.lang.String cdmaCheckAndProcessPlusCode(java.lang.String str) {
        if (!android.text.TextUtils.isEmpty(str) && isReallyDialable(str.charAt(0)) && isNonSeparator(str)) {
            java.lang.String networkCountryIso = android.telephony.TelephonyManager.getDefault().getNetworkCountryIso();
            java.lang.String simCountryIso = android.telephony.TelephonyManager.getDefault().getSimCountryIso();
            if (!android.text.TextUtils.isEmpty(networkCountryIso) && !android.text.TextUtils.isEmpty(simCountryIso)) {
                return cdmaCheckAndProcessPlusCodeByNumberFormat(str, getFormatTypeFromCountryCode(networkCountryIso), getFormatTypeFromCountryCode(simCountryIso));
            }
        }
        return str;
    }

    public static java.lang.String cdmaCheckAndProcessPlusCodeForSms(java.lang.String str) {
        if (!android.text.TextUtils.isEmpty(str) && isReallyDialable(str.charAt(0)) && isNonSeparator(str)) {
            java.lang.String simCountryIso = android.telephony.TelephonyManager.getDefault().getSimCountryIso();
            if (!android.text.TextUtils.isEmpty(simCountryIso)) {
                int formatTypeFromCountryCode = getFormatTypeFromCountryCode(simCountryIso);
                return cdmaCheckAndProcessPlusCodeByNumberFormat(str, formatTypeFromCountryCode, formatTypeFromCountryCode);
            }
        }
        return str;
    }

    public static java.lang.String cdmaCheckAndProcessPlusCodeByNumberFormat(java.lang.String str, int i, int i2) {
        java.lang.String extractNetworkPortionAlt;
        java.lang.String processPlusCode;
        boolean z = i == i2 && i == 1;
        if (str != null && str.lastIndexOf(PLUS_SIGN_STRING) != -1) {
            java.lang.String str2 = null;
            java.lang.String str3 = str;
            while (true) {
                if (z) {
                    extractNetworkPortionAlt = extractNetworkPortion(str3);
                } else {
                    extractNetworkPortionAlt = extractNetworkPortionAlt(str3);
                }
                processPlusCode = processPlusCode(extractNetworkPortionAlt, z);
                if (!android.text.TextUtils.isEmpty(processPlusCode)) {
                    if (str2 != null) {
                        processPlusCode = str2.concat(processPlusCode);
                    }
                    java.lang.String extractPostDialPortion = extractPostDialPortion(str3);
                    if (!android.text.TextUtils.isEmpty(extractPostDialPortion)) {
                        int findDialableIndexFromPostDialStr = findDialableIndexFromPostDialStr(extractPostDialPortion);
                        if (findDialableIndexFromPostDialStr >= 1) {
                            processPlusCode = appendPwCharBackToOrigDialStr(findDialableIndexFromPostDialStr, processPlusCode, extractPostDialPortion);
                            str3 = extractPostDialPortion.substring(findDialableIndexFromPostDialStr);
                        } else {
                            if (findDialableIndexFromPostDialStr < 0) {
                                extractPostDialPortion = "";
                            }
                            com.android.telephony.Rlog.e("wrong postDialStr=", extractPostDialPortion);
                        }
                    }
                    if (android.text.TextUtils.isEmpty(extractPostDialPortion) || android.text.TextUtils.isEmpty(str3)) {
                        break;
                    }
                    str2 = processPlusCode;
                } else {
                    com.android.telephony.Rlog.e("checkAndProcessPlusCode: null newDialStr", processPlusCode);
                    return str;
                }
            }
            return processPlusCode;
        }
        return str;
    }

    public static java.lang.CharSequence createTtsSpannable(java.lang.CharSequence charSequence) {
        if (charSequence == null) {
            return null;
        }
        android.text.Spannable newSpannable = android.text.Spannable.Factory.getInstance().newSpannable(charSequence);
        addTtsSpan(newSpannable, 0, newSpannable.length());
        return newSpannable;
    }

    public static void addTtsSpan(android.text.Spannable spannable, int i, int i2) {
        spannable.setSpan(createTtsSpan(spannable.subSequence(i, i2).toString()), i, i2, 33);
    }

    @java.lang.Deprecated
    public static java.lang.CharSequence ttsSpanAsPhoneNumber(java.lang.CharSequence charSequence) {
        return createTtsSpannable(charSequence);
    }

    @java.lang.Deprecated
    public static void ttsSpanAsPhoneNumber(android.text.Spannable spannable, int i, int i2) {
        addTtsSpan(spannable, i, i2);
    }

    public static android.text.style.TtsSpan createTtsSpan(java.lang.String str) {
        com.android.i18n.phonenumbers.Phonenumber.PhoneNumber phoneNumber = null;
        if (str == null) {
            return null;
        }
        try {
            phoneNumber = com.android.i18n.phonenumbers.PhoneNumberUtil.getInstance().parse(str, (java.lang.String) null);
        } catch (com.android.i18n.phonenumbers.NumberParseException e) {
        }
        android.text.style.TtsSpan.TelephoneBuilder telephoneBuilder = new android.text.style.TtsSpan.TelephoneBuilder();
        if (phoneNumber == null) {
            telephoneBuilder.setNumberParts(splitAtNonNumerics(str));
        } else {
            if (phoneNumber.hasCountryCode()) {
                telephoneBuilder.setCountryCode(java.lang.Integer.toString(phoneNumber.getCountryCode()));
            }
            telephoneBuilder.setNumberParts(java.lang.Long.toString(phoneNumber.getNationalNumber()));
        }
        return telephoneBuilder.build();
    }

    private static java.lang.String splitAtNonNumerics(java.lang.CharSequence charSequence) {
        java.lang.StringBuilder sb = new java.lang.StringBuilder(charSequence.length());
        int i = 0;
        while (true) {
            java.lang.Object obj = " ";
            if (i < charSequence.length()) {
                if (is12Key(charSequence.charAt(i))) {
                    obj = java.lang.Character.valueOf(charSequence.charAt(i));
                }
                sb.append(obj);
                i++;
            } else {
                return sb.toString().replaceAll(" +", " ").trim();
            }
        }
    }

    private static java.lang.String getCurrentIdp(boolean z) {
        if (z) {
            return NANP_IDP_STRING;
        }
        return android.sysprop.TelephonyProperties.operator_idp_string().orElse(PLUS_SIGN_STRING);
    }

    private static boolean isTwoToNine(char c) {
        if (c >= '2' && c <= '9') {
            return true;
        }
        return false;
    }

    private static int getFormatTypeFromCountryCode(java.lang.String str) {
        int length = NANP_COUNTRIES.length;
        for (int i = 0; i < length; i++) {
            if (NANP_COUNTRIES[i].compareToIgnoreCase(str) == 0) {
                return 1;
            }
        }
        if ("jp".compareToIgnoreCase(str) != 0) {
            return 0;
        }
        return 2;
    }

    public static boolean isNanp(java.lang.String str) {
        if (str != null) {
            if (str.length() != 10 || !isTwoToNine(str.charAt(0)) || !isTwoToNine(str.charAt(3))) {
                return false;
            }
            for (int i = 1; i < 10; i++) {
                if (!isISODigit(str.charAt(i))) {
                    return false;
                }
            }
            return true;
        }
        com.android.telephony.Rlog.e("isNanp: null dialStr passed in", str);
        return false;
    }

    private static boolean isOneNanp(java.lang.String str) {
        if (str != null) {
            java.lang.String substring = str.substring(1);
            if (str.charAt(0) != '1' || !isNanp(substring)) {
                return false;
            }
            return true;
        }
        com.android.telephony.Rlog.e("isOneNanp: null dialStr passed in", str);
        return false;
    }

    @android.annotation.SystemApi
    public static boolean isUriNumber(java.lang.String str) {
        return str != null && (str.contains("@") || str.contains("%40"));
    }

    @android.annotation.SystemApi
    public static java.lang.String getUsernameFromUriNumber(java.lang.String str) {
        int indexOf = str.indexOf(64);
        if (indexOf < 0) {
            indexOf = str.indexOf("%40");
        }
        if (indexOf < 0) {
            com.android.telephony.Rlog.w(LOG_TAG, "getUsernameFromUriNumber: no delimiter found in SIP addr '" + str + "'");
            indexOf = str.length();
        }
        return str.substring(0, indexOf);
    }

    public static android.net.Uri convertSipUriToTelUri(android.net.Uri uri) {
        if (!"sip".equals(uri.getScheme())) {
            return uri;
        }
        java.lang.String[] split = uri.getSchemeSpecificPart().split("[@;:]");
        if (split.length == 0) {
            return uri;
        }
        return android.net.Uri.fromParts(android.telecom.PhoneAccount.SCHEME_TEL, split[0], null);
    }

    private static java.lang.String processPlusCode(java.lang.String str, boolean z) {
        if (str != null && str.charAt(0) == '+' && str.length() > 1) {
            java.lang.String substring = str.substring(1);
            if (z && isOneNanp(substring)) {
                return substring;
            }
            return str.replaceFirst("[+]", getCurrentIdp(z));
        }
        return str;
    }

    private static int findDialableIndexFromPostDialStr(java.lang.String str) {
        for (int i = 0; i < str.length(); i++) {
            if (isReallyDialable(str.charAt(i))) {
                return i;
            }
        }
        return -1;
    }

    private static java.lang.String appendPwCharBackToOrigDialStr(int i, java.lang.String str, java.lang.String str2) {
        if (i == 1) {
            return str + str2.charAt(0);
        }
        return str.concat(str2.substring(0, i));
    }

    private static boolean matchIntlPrefix(java.lang.String str, int i) {
        char c = 0;
        for (int i2 = 0; i2 < i; i2++) {
            char charAt = str.charAt(i2);
            switch (c) {
                case 0:
                    if (charAt == '+') {
                        c = 1;
                        break;
                    } else if (charAt == '0') {
                        c = 2;
                        break;
                    } else {
                        if (isNonSeparator(charAt)) {
                            return false;
                        }
                        break;
                    }
                case 1:
                case 3:
                default:
                    if (isNonSeparator(charAt)) {
                        return false;
                    }
                    break;
                case 2:
                    if (charAt == '0') {
                        c = 3;
                        break;
                    } else if (charAt == '1') {
                        c = 4;
                        break;
                    } else {
                        if (isNonSeparator(charAt)) {
                            return false;
                        }
                        break;
                    }
                case 4:
                    if (charAt == '1') {
                        c = 5;
                        break;
                    } else {
                        if (isNonSeparator(charAt)) {
                            return false;
                        }
                        break;
                    }
            }
        }
        return c == 1 || c == 3 || c == 5;
    }

    private static boolean matchIntlPrefixAndCC(java.lang.String str, int i) {
        int i2 = 0;
        for (int i3 = 0; i3 < i; i3++) {
            char charAt = str.charAt(i3);
            switch (i2) {
                case 0:
                    if (charAt == '+') {
                        i2 = 1;
                        break;
                    } else if (charAt == '0') {
                        i2 = 2;
                        break;
                    } else {
                        if (isNonSeparator(charAt)) {
                            return false;
                        }
                        break;
                    }
                case 1:
                case 3:
                case 5:
                    if (isISODigit(charAt)) {
                        i2 = 6;
                        break;
                    } else {
                        if (isNonSeparator(charAt)) {
                            return false;
                        }
                        break;
                    }
                case 2:
                    if (charAt == '0') {
                        i2 = 3;
                        break;
                    } else if (charAt == '1') {
                        i2 = 4;
                        break;
                    } else {
                        if (isNonSeparator(charAt)) {
                            return false;
                        }
                        break;
                    }
                case 4:
                    if (charAt == '1') {
                        i2 = 5;
                        break;
                    } else {
                        if (isNonSeparator(charAt)) {
                            return false;
                        }
                        break;
                    }
                case 6:
                case 7:
                    if (isISODigit(charAt)) {
                        i2++;
                        break;
                    } else {
                        if (isNonSeparator(charAt)) {
                            return false;
                        }
                        break;
                    }
                default:
                    if (isNonSeparator(charAt)) {
                        return false;
                    }
                    break;
            }
        }
        return i2 == 6 || i2 == 7 || i2 == 8;
    }

    private static boolean matchTrunkPrefix(java.lang.String str, int i) {
        boolean z = false;
        for (int i2 = 0; i2 < i; i2++) {
            char charAt = str.charAt(i2);
            if (charAt == '0' && !z) {
                z = true;
            } else if (isNonSeparator(charAt)) {
                return false;
            }
        }
        return z;
    }

    private static boolean isCountryCallingCode(int i) {
        return i > 0 && i < CCC_LENGTH && COUNTRY_CALLING_CALL[i];
    }

    private static int tryGetISODigit(char c) {
        if ('0' <= c && c <= '9') {
            return c - '0';
        }
        return -1;
    }

    private static class CountryCallingCodeAndNewIndex {
        public final int countryCallingCode;
        public final int newIndex;

        public CountryCallingCodeAndNewIndex(int i, int i2) {
            this.countryCallingCode = i;
            this.newIndex = i2;
        }
    }

    private static android.telephony.PhoneNumberUtils.CountryCallingCodeAndNewIndex tryGetCountryCallingCodeAndNewIndex(java.lang.String str, boolean z) {
        int length = str.length();
        int i = 0;
        int i2 = 0;
        for (int i3 = 0; i3 < length; i3++) {
            char charAt = str.charAt(i3);
            switch (i) {
                case 0:
                    if (charAt == '+') {
                        i = 1;
                        break;
                    } else if (charAt == '0') {
                        i = 2;
                        break;
                    } else if (charAt == '1') {
                        if (!z) {
                            return null;
                        }
                        i = 8;
                        break;
                    } else {
                        if (isDialable(charAt)) {
                            return null;
                        }
                        break;
                    }
                case 1:
                case 3:
                case 5:
                case 6:
                case 7:
                    int tryGetISODigit = tryGetISODigit(charAt);
                    if (tryGetISODigit > 0) {
                        i2 = (i2 * 10) + tryGetISODigit;
                        if (i2 >= 100 || isCountryCallingCode(i2)) {
                            return new android.telephony.PhoneNumberUtils.CountryCallingCodeAndNewIndex(i2, i3 + 1);
                        }
                        if (i == 1 || i == 3 || i == 5) {
                            i = 6;
                            break;
                        } else {
                            i++;
                            break;
                        }
                    } else {
                        if (isDialable(charAt)) {
                            return null;
                        }
                        break;
                    }
                case 2:
                    if (charAt == '0') {
                        i = 3;
                        break;
                    } else if (charAt == '1') {
                        i = 4;
                        break;
                    } else {
                        if (isDialable(charAt)) {
                            return null;
                        }
                        break;
                    }
                case 4:
                    if (charAt == '1') {
                        i = 5;
                        break;
                    } else {
                        if (isDialable(charAt)) {
                            return null;
                        }
                        break;
                    }
                case 8:
                    if (charAt == '6') {
                        i = 9;
                        break;
                    } else {
                        if (isDialable(charAt)) {
                            return null;
                        }
                        break;
                    }
                case 9:
                    if (charAt == '6') {
                        return new android.telephony.PhoneNumberUtils.CountryCallingCodeAndNewIndex(66, i3 + 1);
                    }
                    return null;
                default:
                    return null;
            }
        }
        return null;
    }

    private static int tryGetTrunkPrefixOmittedIndex(java.lang.String str, int i) {
        int length = str.length();
        while (i < length) {
            char charAt = str.charAt(i);
            if (tryGetISODigit(charAt) >= 0) {
                return i + 1;
            }
            if (isDialable(charAt)) {
                return -1;
            }
            i++;
        }
        return -1;
    }

    private static boolean checkPrefixIsIgnorable(java.lang.String str, int i, int i2) {
        boolean z = false;
        while (i2 >= i) {
            if (tryGetISODigit(str.charAt(i2)) >= 0) {
                if (z) {
                    return false;
                }
                z = true;
            } else if (isDialable(str.charAt(i2))) {
                return false;
            }
            i2--;
        }
        return true;
    }

    private static int getDefaultVoiceSubId() {
        return android.telephony.SubscriptionManager.getDefaultVoiceSubscriptionId();
    }

    public static java.lang.String convertToEmergencyNumber(android.content.Context context, java.lang.String str) {
        java.lang.String[] strArr;
        java.lang.String[] strArr2;
        if (context == null || android.text.TextUtils.isEmpty(str)) {
            return str;
        }
        java.lang.String normalizeNumber = normalizeNumber(str);
        if (isEmergencyNumber(normalizeNumber)) {
            return str;
        }
        if (sConvertToEmergencyMap == null) {
            sConvertToEmergencyMap = context.getResources().getStringArray(com.android.internal.R.array.config_convert_to_emergency_number_map);
        }
        if (sConvertToEmergencyMap == null || sConvertToEmergencyMap.length == 0) {
            return str;
        }
        for (java.lang.String str2 : sConvertToEmergencyMap) {
            java.lang.String str3 = null;
            if (android.text.TextUtils.isEmpty(str2)) {
                strArr = null;
            } else {
                strArr = str2.split(":");
            }
            if (strArr != null && strArr.length == 2) {
                java.lang.String str4 = strArr[1];
                if (android.text.TextUtils.isEmpty(strArr[0])) {
                    strArr2 = null;
                    str3 = str4;
                } else {
                    strArr2 = strArr[0].split(",");
                    str3 = str4;
                }
            } else {
                strArr2 = null;
            }
            if (!android.text.TextUtils.isEmpty(str3) && strArr2 != null && strArr2.length != 0) {
                for (java.lang.String str5 : strArr2) {
                    if (!android.text.TextUtils.isEmpty(str5) && str5.equals(normalizeNumber)) {
                        return str3;
                    }
                }
            }
        }
        return str;
    }

    public static boolean areSamePhoneNumber(java.lang.String str, java.lang.String str2, java.lang.String str3) {
        com.android.i18n.phonenumbers.PhoneNumberUtil phoneNumberUtil = com.android.i18n.phonenumbers.PhoneNumberUtil.getInstance();
        if (str3 != null) {
            str3 = str3.toUpperCase(java.util.Locale.ROOT);
        }
        try {
            com.android.i18n.phonenumbers.Phonenumber.PhoneNumber parseAndKeepRawInput = phoneNumberUtil.parseAndKeepRawInput(str, str3);
            com.android.i18n.phonenumbers.Phonenumber.PhoneNumber parseAndKeepRawInput2 = phoneNumberUtil.parseAndKeepRawInput(str2, str3);
            com.android.i18n.phonenumbers.PhoneNumberUtil.MatchType isNumberMatch = phoneNumberUtil.isNumberMatch(parseAndKeepRawInput, parseAndKeepRawInput2);
            if (isNumberMatch == com.android.i18n.phonenumbers.PhoneNumberUtil.MatchType.EXACT_MATCH || isNumberMatch == com.android.i18n.phonenumbers.PhoneNumberUtil.MatchType.NSN_MATCH) {
                return true;
            }
            return isNumberMatch == com.android.i18n.phonenumbers.PhoneNumberUtil.MatchType.SHORT_NSN_MATCH && parseAndKeepRawInput.getNationalNumber() == parseAndKeepRawInput2.getNationalNumber() && parseAndKeepRawInput.getCountryCode() == parseAndKeepRawInput2.getCountryCode();
        } catch (com.android.i18n.phonenumbers.NumberParseException e) {
            return false;
        }
    }

    public static boolean isWpsCallNumber(java.lang.String str) {
        return str != null && (str.startsWith(PREFIX_WPS) || str.startsWith(PREFIX_WPS_CLIR_ACTIVATE) || str.startsWith(PREFIX_WPS_CLIR_DEACTIVATE));
    }
}
