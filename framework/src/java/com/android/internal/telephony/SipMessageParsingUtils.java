package com.android.internal.telephony;

/* loaded from: classes5.dex */
public class SipMessageParsingUtils {
    private static final java.lang.String ACCEPT_CONTACT_HEADER_KEY = "accept-contact";
    private static final java.lang.String ACCEPT_CONTACT_HEADER_KEY_COMPACT = "a";
    private static final java.lang.String BRANCH_PARAM_KEY = "branch";
    private static final java.lang.String CALL_ID_SIP_HEADER_KEY = "call-id";
    private static final java.lang.String CALL_ID_SIP_HEADER_KEY_COMPACT = "i";
    private static final java.lang.String FROM_HEADER_KEY = "from";
    private static final java.lang.String FROM_HEADER_KEY_COMPACT = "f";
    private static final java.lang.String HEADER_KEY_VALUE_SEPARATOR = ":";
    private static final java.lang.String PARAM_KEY_VALUE_SEPARATOR = "=";
    private static final java.lang.String PARAM_SEPARATOR = ";";
    private static final java.lang.String[] SIP_REQUEST_METHODS = {"INVITE", "ACK", "OPTIONS", "BYE", "CANCEL", "REGISTER", "PRACK", "SUBSCRIBE", "NOTIFY", "PUBLISH", "INFO", "REFER", "MESSAGE", "UPDATE"};
    private static final java.lang.String SIP_VERSION_2 = "SIP/2.0";
    private static final java.lang.String SUBHEADER_VALUE_SEPARATOR = ",";
    private static final java.lang.String TAG = "SipMessageParsingUtils";
    private static final java.lang.String TAG_PARAM_KEY = "tag";
    private static final java.lang.String TO_HEADER_KEY = "to";
    private static final java.lang.String TO_HEADER_KEY_COMPACT = "t";
    private static final java.lang.String VIA_SIP_HEADER_KEY = "via";
    private static final java.lang.String VIA_SIP_HEADER_KEY_COMPACT = "v";

    public static boolean isSipRequest(java.lang.String str) {
        java.lang.String[] splitStartLineAndVerify = splitStartLineAndVerify(str);
        if (splitStartLineAndVerify == null) {
            return false;
        }
        return verifySipRequest(splitStartLineAndVerify);
    }

    public static boolean isSipResponse(java.lang.String str) {
        java.lang.String[] splitStartLineAndVerify = splitStartLineAndVerify(str);
        if (splitStartLineAndVerify == null) {
            return false;
        }
        return verifySipResponse(splitStartLineAndVerify);
    }

    public static java.lang.String getTransactionId(java.lang.String str) {
        java.util.Iterator<android.util.Pair<java.lang.String, java.lang.String>> it = parseHeaders(str, true, VIA_SIP_HEADER_KEY, "v").iterator();
        while (it.hasNext()) {
            for (java.lang.String str2 : it.next().second.split(",")) {
                java.lang.String parameterValue = getParameterValue(str2, BRANCH_PARAM_KEY);
                if (parameterValue != null) {
                    return parameterValue;
                }
            }
        }
        return null;
    }

    private static java.lang.String getParameterValue(java.lang.String str, java.lang.String str2) {
        java.lang.String[] split = str.split(";");
        if (split.length < 2) {
            return null;
        }
        for (java.lang.String str3 : split) {
            java.lang.String[] split2 = str3.split(PARAM_KEY_VALUE_SEPARATOR);
            if (split2.length >= 2) {
                if (split2.length > 2) {
                    android.util.Log.w(TAG, "getParameterValue: unexpected parameter" + java.util.Arrays.toString(split2));
                }
                split2[0] = split2[0].trim();
                split2[1] = split2[1].trim();
                if (str2.equalsIgnoreCase(split2[0])) {
                    return split2[1];
                }
            }
        }
        return null;
    }

    public static java.lang.String getCallId(java.lang.String str) {
        java.util.List<android.util.Pair<java.lang.String, java.lang.String>> parseHeaders = parseHeaders(str, true, CALL_ID_SIP_HEADER_KEY, CALL_ID_SIP_HEADER_KEY_COMPACT);
        if (parseHeaders.isEmpty()) {
            return null;
        }
        return parseHeaders.get(0).second;
    }

    public static java.lang.String getFromTag(java.lang.String str) {
        java.util.List<android.util.Pair<java.lang.String, java.lang.String>> parseHeaders = parseHeaders(str, true, FROM_HEADER_KEY, "f");
        if (parseHeaders.isEmpty()) {
            return null;
        }
        return getParameterValue(parseHeaders.get(0).second, "tag");
    }

    public static java.lang.String getToTag(java.lang.String str) {
        java.util.List<android.util.Pair<java.lang.String, java.lang.String>> parseHeaders = parseHeaders(str, true, TO_HEADER_KEY, "t");
        if (parseHeaders.isEmpty()) {
            return null;
        }
        return getParameterValue(parseHeaders.get(0).second, "tag");
    }

    public static java.lang.String[] splitStartLineAndVerify(java.lang.String str) {
        java.lang.String[] split = str.split(" ", 3);
        if (isStartLineMalformed(split)) {
            return null;
        }
        return split;
    }

    public static java.util.Set<java.lang.String> getAcceptContactFeatureTags(java.lang.String str) {
        java.util.List<android.util.Pair<java.lang.String, java.lang.String>> parseHeaders = parseHeaders(str, false, ACCEPT_CONTACT_HEADER_KEY, "a");
        if (str.isEmpty()) {
            return java.util.Collections.emptySet();
        }
        android.util.ArraySet arraySet = new android.util.ArraySet();
        java.util.Iterator<android.util.Pair<java.lang.String, java.lang.String>> it = parseHeaders.iterator();
        while (it.hasNext()) {
            java.lang.String[] split = it.next().second.split(";");
            if (split.length >= 2) {
                for (java.lang.String str2 : (java.util.Set) java.util.Arrays.asList(split).subList(1, split.length).stream().map(new java.util.function.Function() { // from class: com.android.internal.telephony.SipMessageParsingUtils$$ExternalSyntheticLambda3
                    @Override // java.util.function.Function
                    public final java.lang.Object apply(java.lang.Object obj) {
                        return ((java.lang.String) obj).trim();
                    }
                }).filter(new java.util.function.Predicate() { // from class: com.android.internal.telephony.SipMessageParsingUtils$$ExternalSyntheticLambda4
                    @Override // java.util.function.Predicate
                    public final boolean test(java.lang.Object obj) {
                        boolean startsWith;
                        startsWith = ((java.lang.String) obj).startsWith("+");
                        return startsWith;
                    }
                }).collect(java.util.stream.Collectors.toSet())) {
                    java.lang.String[] split2 = str2.split(PARAM_KEY_VALUE_SEPARATOR, 2);
                    if (split2.length < 2) {
                        arraySet.add(str2);
                    } else {
                        for (java.lang.String str3 : splitParamValue(split2[1])) {
                            arraySet.add(split2[0] + PARAM_KEY_VALUE_SEPARATOR + str3);
                        }
                    }
                }
            }
        }
        return arraySet;
    }

    private static java.lang.String[] splitParamValue(java.lang.String str) {
        if (!str.startsWith("\"") && !str.endsWith("\"")) {
            return new java.lang.String[]{str};
        }
        java.lang.String[] split = str.substring(1, str.length() - 1).split(",");
        for (int i = 0; i < split.length; i++) {
            split[i] = "\"" + split[i] + "\"";
        }
        return split;
    }

    private static boolean isStartLineMalformed(java.lang.String[] strArr) {
        return strArr == null || strArr.length == 0 || strArr.length != 3;
    }

    private static boolean verifySipRequest(final java.lang.String[] strArr) {
        if (!strArr[2].contains(SIP_VERSION_2)) {
            return false;
        }
        try {
            return java.util.Arrays.stream(SIP_REQUEST_METHODS).anyMatch(new java.util.function.Predicate() { // from class: com.android.internal.telephony.SipMessageParsingUtils$$ExternalSyntheticLambda0
                @Override // java.util.function.Predicate
                public final boolean test(java.lang.Object obj) {
                    boolean contains;
                    contains = strArr[0].contains((java.lang.String) obj);
                    return contains;
                }
            }) & (android.net.Uri.parse(strArr[1]).getScheme() != null);
        } catch (java.lang.NumberFormatException e) {
            return false;
        }
    }

    private static boolean verifySipResponse(java.lang.String[] strArr) {
        if (!strArr[0].contains(SIP_VERSION_2)) {
            return false;
        }
        try {
            int parseInt = java.lang.Integer.parseInt(strArr[1]);
            if (parseInt < 100 || parseInt >= 700) {
                return false;
            }
            return true;
        } catch (java.lang.NumberFormatException e) {
            return false;
        }
    }

    public static java.util.List<android.util.Pair<java.lang.String, java.lang.String>> parseHeaders(java.lang.String str, boolean z, java.lang.String... strArr) {
        java.lang.String removeLeadingWhitespace = removeLeadingWhitespace(str);
        java.util.ArrayList arrayList = new java.util.ArrayList();
        java.lang.String[] split = removeLeadingWhitespace.split("\\r?\\n");
        if (split.length == 0) {
            return java.util.Collections.emptyList();
        }
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        final java.lang.String str2 = null;
        for (java.lang.String str3 : split) {
            if (str3.startsWith("\t") || str3.startsWith(" ")) {
                sb.append(removeLeadingWhitespace(str3));
            } else {
                if (str2 != null) {
                    if (strArr == null || strArr.length == 0 || java.util.Arrays.stream(strArr).anyMatch(new java.util.function.Predicate() { // from class: com.android.internal.telephony.SipMessageParsingUtils$$ExternalSyntheticLambda1
                        @Override // java.util.function.Predicate
                        public final boolean test(java.lang.Object obj) {
                            boolean equalsIgnoreCase;
                            equalsIgnoreCase = ((java.lang.String) obj).equalsIgnoreCase(str2);
                            return equalsIgnoreCase;
                        }
                    })) {
                        arrayList.add(new android.util.Pair(str2, sb.toString()));
                        if (z) {
                            return arrayList;
                        }
                    }
                    sb = new java.lang.StringBuilder();
                    str2 = null;
                }
                java.lang.String[] split2 = str3.split(":", 2);
                if (split2.length < 2) {
                    android.util.Log.w(TAG, "parseHeaders - received malformed line: " + str3);
                } else {
                    str2 = split2[0].trim();
                    for (int i = 1; i < split2.length; i++) {
                        sb.append(removeLeadingWhitespace(split2[i]));
                    }
                }
            }
        }
        if (str2 != null && (strArr == null || strArr.length == 0 || java.util.Arrays.stream(strArr).anyMatch(new java.util.function.Predicate() { // from class: com.android.internal.telephony.SipMessageParsingUtils$$ExternalSyntheticLambda2
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                boolean equalsIgnoreCase;
                equalsIgnoreCase = ((java.lang.String) obj).equalsIgnoreCase(str2);
                return equalsIgnoreCase;
            }
        }))) {
            arrayList.add(new android.util.Pair(str2, sb.toString()));
        }
        return arrayList;
    }

    private static java.lang.String removeLeadingWhitespace(java.lang.String str) {
        return str.replaceFirst("^\\s*", "");
    }
}
