package com.android.internal.telephony;

/* loaded from: classes5.dex */
public class SmsNumberUtils {
    private static int[] ALL_COUNTRY_CODES = null;
    private static final int CDMA_HOME_NETWORK = 1;
    private static final int CDMA_ROAMING_NETWORK = 2;
    private static final boolean DBG;
    private static final int GSM_UMTS_NETWORK = 0;
    private static java.util.HashMap<java.lang.String, java.util.ArrayList<java.lang.String>> IDDS_MAPS = null;
    private static int MAX_COUNTRY_CODES_LENGTH = 0;
    private static final int MIN_COUNTRY_AREA_LOCAL_LENGTH = 10;
    private static final int NANP_CC = 1;
    private static final java.lang.String NANP_IDD = "011";
    private static final int NANP_LONG_LENGTH = 11;
    private static final int NANP_MEDIUM_LENGTH = 10;
    private static final java.lang.String NANP_NDD = "1";
    private static final int NANP_SHORT_LENGTH = 7;
    private static final int NP_CC_AREA_LOCAL = 104;
    private static final int NP_HOMEIDD_CC_AREA_LOCAL = 101;
    private static final int NP_INTERNATIONAL_BEGIN = 100;
    private static final int NP_LOCALIDD_CC_AREA_LOCAL = 103;
    private static final int NP_NANP_AREA_LOCAL = 2;
    private static final int NP_NANP_BEGIN = 1;
    private static final int NP_NANP_LOCAL = 1;
    private static final int NP_NANP_LOCALIDD_CC_AREA_LOCAL = 5;
    private static final int NP_NANP_NBPCD_CC_AREA_LOCAL = 4;
    private static final int NP_NANP_NBPCD_HOMEIDD_CC_AREA_LOCAL = 6;
    private static final int NP_NANP_NDD_AREA_LOCAL = 3;
    private static final int NP_NBPCD_CC_AREA_LOCAL = 102;
    private static final int NP_NBPCD_HOMEIDD_CC_AREA_LOCAL = 100;
    private static final int NP_NONE = 0;
    private static final java.lang.String PLUS_SIGN = "+";
    private static final java.lang.String TAG = "SmsNumberUtils";

    static {
        DBG = android.os.SystemProperties.getInt("ro.debuggable", 0) == 1;
        ALL_COUNTRY_CODES = null;
        IDDS_MAPS = new java.util.HashMap<>();
    }

    private static class NumberEntry {
        public java.lang.String IDD;
        public int countryCode;
        public java.lang.String number;

        public NumberEntry(java.lang.String str) {
            this.number = str;
        }
    }

    private static java.lang.String formatNumber(android.content.Context context, java.lang.String str, java.lang.String str2, int i) {
        java.lang.String substring;
        if (str == null) {
            throw new java.lang.IllegalArgumentException("number is null");
        }
        if (str2 == null || str2.trim().length() == 0) {
            throw new java.lang.IllegalArgumentException("activeMcc is null or empty!");
        }
        java.lang.String extractNetworkPortion = android.telephony.PhoneNumberUtils.extractNetworkPortion(str);
        if (extractNetworkPortion == null || extractNetworkPortion.length() == 0) {
            throw new java.lang.IllegalArgumentException("Number is invalid!");
        }
        com.android.internal.telephony.SmsNumberUtils.NumberEntry numberEntry = new com.android.internal.telephony.SmsNumberUtils.NumberEntry(extractNetworkPortion);
        java.util.ArrayList<java.lang.String> allIDDs = getAllIDDs(context, str2);
        int checkNANP = checkNANP(numberEntry, allIDDs);
        if (DBG) {
            android.util.Log.d(TAG, "NANP type: " + getNumberPlanType(checkNANP));
        }
        if (checkNANP == 1 || checkNANP == 2 || checkNANP == 3) {
            return extractNetworkPortion;
        }
        if (checkNANP == 4) {
            if (i == 1 || i == 2) {
                return extractNetworkPortion.substring(1);
            }
            return extractNetworkPortion;
        }
        if (checkNANP == 5) {
            if (i == 1) {
                return extractNetworkPortion;
            }
            if (i == 0) {
                return PLUS_SIGN + extractNetworkPortion.substring(numberEntry.IDD != null ? numberEntry.IDD.length() : 0);
            }
            if (i == 2) {
                return extractNetworkPortion.substring(numberEntry.IDD != null ? numberEntry.IDD.length() : 0);
            }
        }
        int checkInternationalNumberPlan = checkInternationalNumberPlan(context, numberEntry, allIDDs, NANP_IDD);
        if (DBG) {
            android.util.Log.d(TAG, "International type: " + getNumberPlanType(checkInternationalNumberPlan));
        }
        switch (checkInternationalNumberPlan) {
            case 100:
                if (i == 0) {
                    substring = extractNetworkPortion.substring(1);
                    break;
                }
                substring = null;
                break;
            case 101:
                substring = extractNetworkPortion;
                break;
            case 102:
                substring = NANP_IDD + extractNetworkPortion.substring(1);
                break;
            case 103:
                if (i == 0 || i == 2) {
                    substring = NANP_IDD + extractNetworkPortion.substring(numberEntry.IDD != null ? numberEntry.IDD.length() : 0);
                    break;
                }
                substring = null;
                break;
            case 104:
                int i2 = numberEntry.countryCode;
                if (!inExceptionListForNpCcAreaLocal(numberEntry) && extractNetworkPortion.length() >= 11 && i2 != 1) {
                    substring = NANP_IDD + extractNetworkPortion;
                    break;
                }
                substring = null;
                break;
            default:
                if (extractNetworkPortion.startsWith(PLUS_SIGN) && (i == 1 || i == 2)) {
                    if (!extractNetworkPortion.startsWith("+011")) {
                        substring = NANP_IDD + extractNetworkPortion.substring(1);
                        break;
                    } else {
                        substring = extractNetworkPortion.substring(1);
                        break;
                    }
                }
                substring = null;
                break;
        }
        if (substring != null) {
            return substring;
        }
        return extractNetworkPortion;
    }

    /* JADX WARN: Code restructure failed: missing block: B:22:0x0053, code lost:
    
        if (r3 != null) goto L25;
     */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x0063, code lost:
    
        com.android.internal.telephony.SmsNumberUtils.IDDS_MAPS.put(r11, r1);
     */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x006a, code lost:
    
        if (com.android.internal.telephony.SmsNumberUtils.DBG == false) goto L29;
     */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x006c, code lost:
    
        android.util.Log.d(com.android.internal.telephony.SmsNumberUtils.TAG, "MCC = " + r11 + ", all IDDs = " + r1);
     */
    /* JADX WARN: Code restructure failed: missing block: B:26:0x008c, code lost:
    
        return r1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x0060, code lost:
    
        r3.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:30:0x005e, code lost:
    
        if (r3 == null) goto L26;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private static java.util.ArrayList<java.lang.String> getAllIDDs(android.content.Context context, java.lang.String str) {
        java.lang.String str2;
        java.lang.String[] strArr;
        java.util.ArrayList<java.lang.String> arrayList = IDDS_MAPS.get(str);
        if (arrayList != null) {
            return arrayList;
        }
        java.util.ArrayList<java.lang.String> arrayList2 = new java.util.ArrayList<>();
        java.lang.String[] strArr2 = {com.android.internal.telephony.HbpcdLookup.MccIdd.IDD, "MCC"};
        android.database.Cursor cursor = null;
        if (str != null) {
            strArr = new java.lang.String[]{str};
            str2 = "MCC=?";
        } else {
            str2 = null;
            strArr = null;
        }
        try {
            try {
                cursor = context.getContentResolver().query(com.android.internal.telephony.HbpcdLookup.MccIdd.CONTENT_URI, strArr2, str2, strArr, null);
                if (cursor.getCount() > 0) {
                    while (cursor.moveToNext()) {
                        java.lang.String string = cursor.getString(0);
                        if (!arrayList2.contains(string)) {
                            arrayList2.add(string);
                        }
                    }
                }
            } catch (android.database.SQLException e) {
                android.util.Log.e(TAG, "Can't access HbpcdLookup database", e);
            }
        } catch (java.lang.Throwable th) {
            if (cursor != null) {
                cursor.close();
            }
            throw th;
        }
    }

    private static int checkNANP(com.android.internal.telephony.SmsNumberUtils.NumberEntry numberEntry, java.util.ArrayList<java.lang.String> arrayList) {
        java.lang.String substring;
        boolean z;
        java.lang.String str = numberEntry.number;
        if (str.length() == 7) {
            char charAt = str.charAt(0);
            if (charAt >= '2' && charAt <= '9') {
                int i = 1;
                while (true) {
                    if (i >= 7) {
                        z = true;
                        break;
                    }
                    if (android.telephony.PhoneNumberUtils.isISODigit(str.charAt(i))) {
                        i++;
                    } else {
                        z = false;
                        break;
                    }
                }
            } else {
                z = false;
            }
            if (z) {
                return 1;
            }
        } else if (str.length() == 10) {
            if (isNANP(str)) {
                return 2;
            }
        } else if (str.length() == 11) {
            if (isNANP(str)) {
                return 3;
            }
        } else if (str.startsWith(PLUS_SIGN)) {
            java.lang.String substring2 = str.substring(1);
            if (substring2.length() == 11) {
                if (isNANP(substring2)) {
                    return 4;
                }
            } else if (substring2.startsWith(NANP_IDD) && substring2.length() == 14 && isNANP(substring2.substring(3))) {
                return 6;
            }
        } else {
            java.util.Iterator<java.lang.String> it = arrayList.iterator();
            while (it.hasNext()) {
                java.lang.String next = it.next();
                if (str.startsWith(next) && (substring = str.substring(next.length())) != null && substring.startsWith(java.lang.String.valueOf(1)) && isNANP(substring)) {
                    numberEntry.IDD = next;
                    return 5;
                }
            }
        }
        return 0;
    }

    private static boolean isNANP(java.lang.String str) {
        if (str.length() != 10 && (str.length() != 11 || !str.startsWith(NANP_NDD))) {
            return false;
        }
        if (str.length() == 11) {
            str = str.substring(1);
        }
        if (!isTwoToNine(str.charAt(0)) || !isTwoToNine(str.charAt(3))) {
            return false;
        }
        for (int i = 1; i < 10; i++) {
            if (!android.telephony.PhoneNumberUtils.isISODigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    private static boolean isTwoToNine(char c) {
        if (c >= '2' && c <= '9') {
            return true;
        }
        return false;
    }

    private static int checkInternationalNumberPlan(android.content.Context context, com.android.internal.telephony.SmsNumberUtils.NumberEntry numberEntry, java.util.ArrayList<java.lang.String> arrayList, java.lang.String str) {
        int countryCode;
        int countryCode2;
        java.lang.String str2 = numberEntry.number;
        if (str2.startsWith(PLUS_SIGN)) {
            java.lang.String substring = str2.substring(1);
            if (substring.startsWith(str)) {
                int countryCode3 = getCountryCode(context, substring.substring(str.length()));
                if (countryCode3 > 0) {
                    numberEntry.countryCode = countryCode3;
                    return 100;
                }
                return 0;
            }
            int countryCode4 = getCountryCode(context, substring);
            if (countryCode4 > 0) {
                numberEntry.countryCode = countryCode4;
                return 102;
            }
            return 0;
        }
        if (str2.startsWith(str)) {
            int countryCode5 = getCountryCode(context, str2.substring(str.length()));
            if (countryCode5 > 0) {
                numberEntry.countryCode = countryCode5;
                return 101;
            }
            return 0;
        }
        java.util.Iterator<java.lang.String> it = arrayList.iterator();
        while (it.hasNext()) {
            java.lang.String next = it.next();
            if (str2.startsWith(next) && (countryCode2 = getCountryCode(context, str2.substring(next.length()))) > 0) {
                numberEntry.countryCode = countryCode2;
                numberEntry.IDD = next;
                return 103;
            }
        }
        if (!str2.startsWith(android.media.AudioSystem.LEGACY_REMOTE_SUBMIX_ADDRESS) && (countryCode = getCountryCode(context, str2)) > 0) {
            numberEntry.countryCode = countryCode;
            return 104;
        }
        return 0;
    }

    private static int getCountryCode(android.content.Context context, java.lang.String str) {
        int[] allCountryCodes;
        if (str.length() < 10 || (allCountryCodes = getAllCountryCodes(context)) == null) {
            return -1;
        }
        int[] iArr = new int[MAX_COUNTRY_CODES_LENGTH];
        int i = 0;
        while (i < MAX_COUNTRY_CODES_LENGTH) {
            int i2 = i + 1;
            iArr[i] = java.lang.Integer.parseInt(str.substring(0, i2));
            i = i2;
        }
        for (int i3 : allCountryCodes) {
            for (int i4 = 0; i4 < MAX_COUNTRY_CODES_LENGTH; i4++) {
                if (i3 == iArr[i4]) {
                    if (DBG) {
                        android.util.Log.d(TAG, "Country code = " + i3);
                    }
                    return i3;
                }
            }
        }
        return -1;
    }

    /* JADX WARN: Code restructure failed: missing block: B:21:0x0050, code lost:
    
        if (r0 != null) goto L23;
     */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x0064, code lost:
    
        return com.android.internal.telephony.SmsNumberUtils.ALL_COUNTRY_CODES;
     */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x005f, code lost:
    
        r0.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x005d, code lost:
    
        if (r0 == null) goto L24;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private static int[] getAllCountryCodes(android.content.Context context) {
        if (ALL_COUNTRY_CODES != null) {
            return ALL_COUNTRY_CODES;
        }
        android.database.Cursor cursor = null;
        try {
            try {
                cursor = context.getContentResolver().query(com.android.internal.telephony.HbpcdLookup.MccLookup.CONTENT_URI, new java.lang.String[]{com.android.internal.telephony.HbpcdLookup.MccLookup.COUNTRY_CODE}, null, null, null);
                if (cursor.getCount() > 0) {
                    ALL_COUNTRY_CODES = new int[cursor.getCount()];
                    int i = 0;
                    while (cursor.moveToNext()) {
                        int i2 = cursor.getInt(0);
                        int i3 = i + 1;
                        ALL_COUNTRY_CODES[i] = i2;
                        int length = java.lang.String.valueOf(i2).trim().length();
                        if (length > MAX_COUNTRY_CODES_LENGTH) {
                            MAX_COUNTRY_CODES_LENGTH = length;
                        }
                        i = i3;
                    }
                }
            } catch (android.database.SQLException e) {
                android.util.Log.e(TAG, "Can't access HbpcdLookup database", e);
            }
        } catch (java.lang.Throwable th) {
            if (cursor != null) {
                cursor.close();
            }
            throw th;
        }
    }

    private static boolean inExceptionListForNpCcAreaLocal(com.android.internal.telephony.SmsNumberUtils.NumberEntry numberEntry) {
        int i = numberEntry.countryCode;
        return numberEntry.number.length() == 12 && (i == 7 || i == 20 || i == 65 || i == 90);
    }

    private static java.lang.String getNumberPlanType(int i) {
        java.lang.String str = "Number Plan type (" + i + "): ";
        if (i == 1) {
            return "NP_NANP_LOCAL";
        }
        if (i == 2) {
            return "NP_NANP_AREA_LOCAL";
        }
        if (i == 3) {
            return "NP_NANP_NDD_AREA_LOCAL";
        }
        if (i == 4) {
            return "NP_NANP_NBPCD_CC_AREA_LOCAL";
        }
        if (i == 5) {
            return "NP_NANP_LOCALIDD_CC_AREA_LOCAL";
        }
        if (i == 6) {
            return "NP_NANP_NBPCD_HOMEIDD_CC_AREA_LOCAL";
        }
        if (i == 100) {
            return "NP_NBPCD_HOMEIDD_CC_AREA_LOCAL";
        }
        if (i == 101) {
            return "NP_HOMEIDD_CC_AREA_LOCAL";
        }
        if (i == 102) {
            return "NP_NBPCD_CC_AREA_LOCAL";
        }
        if (i == 103) {
            return "NP_LOCALIDD_CC_AREA_LOCAL";
        }
        if (i == 104) {
            return "NP_CC_AREA_LOCAL";
        }
        return "Unknown type";
    }

    public static java.lang.String filterDestAddr(android.content.Context context, int i, java.lang.String str) {
        java.lang.String str2;
        int networkType;
        java.lang.String substring;
        if (DBG) {
            android.util.Log.d(TAG, "enter filterDestAddr. destAddr=\"" + pii(TAG, str) + "\"");
        }
        if (str == null || !android.telephony.PhoneNumberUtils.isGlobalPhoneNumber(str)) {
            android.util.Log.w(TAG, "destAddr" + pii(TAG, str) + " is not a global phone number! Nothing changed.");
            return str;
        }
        android.telephony.TelephonyManager createForSubscriptionId = ((android.telephony.TelephonyManager) context.getSystemService("phone")).createForSubscriptionId(i);
        java.lang.String networkOperator = createForSubscriptionId.getNetworkOperator();
        if (needToConvert(context, i) && (networkType = getNetworkType(createForSubscriptionId)) != -1 && !android.text.TextUtils.isEmpty(networkOperator) && (substring = networkOperator.substring(0, 3)) != null && substring.trim().length() > 0) {
            str2 = formatNumber(context, str, substring, networkType);
        } else {
            str2 = null;
        }
        if (DBG) {
            android.util.Log.d(TAG, "destAddr is " + (str2 != null ? "formatted." : "not formatted."));
            android.util.Log.d(TAG, "leave filterDestAddr, new destAddr=\"" + (str2 != null ? pii(TAG, str2) : pii(TAG, str)) + "\"");
        }
        return str2 != null ? str2 : str;
    }

    private static int getNetworkType(android.telephony.TelephonyManager telephonyManager) {
        int phoneType = telephonyManager.getPhoneType();
        if (phoneType == 1) {
            return 0;
        }
        if (phoneType == 2) {
            if (!isInternationalRoaming(telephonyManager)) {
                return 1;
            }
            return 2;
        }
        if (DBG) {
            android.util.Log.w(TAG, "warning! unknown mPhoneType value=" + phoneType);
        }
        return -1;
    }

    private static boolean isInternationalRoaming(android.telephony.TelephonyManager telephonyManager) {
        java.lang.String networkCountryIso = telephonyManager.getNetworkCountryIso();
        java.lang.String simCountryIso = telephonyManager.getSimCountryIso();
        boolean z = (android.text.TextUtils.isEmpty(networkCountryIso) || android.text.TextUtils.isEmpty(simCountryIso) || simCountryIso.equals(networkCountryIso)) ? false : true;
        if (z) {
            if (android.app.blob.XmlTags.ATTR_USER_ID.equals(simCountryIso)) {
                return !"vi".equals(networkCountryIso);
            }
            if ("vi".equals(simCountryIso)) {
                return !android.app.blob.XmlTags.ATTR_USER_ID.equals(networkCountryIso);
            }
            return z;
        }
        return z;
    }

    private static boolean needToConvert(android.content.Context context, int i) {
        android.os.PersistableBundle configForSubId;
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            android.telephony.CarrierConfigManager carrierConfigManager = (android.telephony.CarrierConfigManager) context.getSystemService("carrier_config");
            if (carrierConfigManager != null && (configForSubId = carrierConfigManager.getConfigForSubId(i)) != null) {
                return configForSubId.getBoolean(android.telephony.CarrierConfigManager.KEY_SMS_REQUIRES_DESTINATION_NUMBER_CONVERSION_BOOL);
            }
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            return false;
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    private static java.lang.String pii(java.lang.String str, java.lang.Object obj) {
        java.lang.String valueOf = java.lang.String.valueOf(obj);
        if (obj == null || android.text.TextUtils.isEmpty(valueOf) || android.util.Log.isLoggable(str, 2)) {
            return valueOf;
        }
        return android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_START + secureHash(valueOf.getBytes()) + android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_END;
    }

    private static java.lang.String secureHash(byte[] bArr) {
        if (com.android.internal.telephony.util.TelephonyUtils.IS_USER) {
            return "****";
        }
        try {
            return android.util.Base64.encodeToString(java.security.MessageDigest.getInstance("SHA-1").digest(bArr), 11);
        } catch (java.security.NoSuchAlgorithmException e) {
            return "####";
        }
    }
}
