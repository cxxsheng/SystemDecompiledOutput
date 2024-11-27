package android.text.util;

/* loaded from: classes3.dex */
public class Linkify {

    @java.lang.Deprecated
    public static final int ALL = 15;
    public static final int EMAIL_ADDRESSES = 2;
    private static final java.lang.String LOG_TAG = "Linkify";

    @java.lang.Deprecated
    public static final int MAP_ADDRESSES = 8;
    public static final int PHONE_NUMBERS = 4;
    private static final int PHONE_NUMBER_MINIMUM_DIGITS = 5;
    public static final int WEB_URLS = 1;
    public static final android.text.util.Linkify.MatchFilter sUrlMatchFilter = new android.text.util.Linkify.MatchFilter() { // from class: android.text.util.Linkify.1
        @Override // android.text.util.Linkify.MatchFilter
        public final boolean acceptMatch(java.lang.CharSequence charSequence, int i, int i2) {
            if (i == 0 || charSequence.charAt(i - 1) != '@') {
                return true;
            }
            return false;
        }
    };
    public static final android.text.util.Linkify.MatchFilter sPhoneNumberMatchFilter = new android.text.util.Linkify.MatchFilter() { // from class: android.text.util.Linkify.2
        @Override // android.text.util.Linkify.MatchFilter
        public final boolean acceptMatch(java.lang.CharSequence charSequence, int i, int i2) {
            int i3 = 0;
            while (i < i2) {
                if (!java.lang.Character.isDigit(charSequence.charAt(i)) || (i3 = i3 + 1) < 5) {
                    i++;
                } else {
                    return true;
                }
            }
            return false;
        }
    };
    public static final android.text.util.Linkify.TransformFilter sPhoneNumberTransformFilter = new android.text.util.Linkify.TransformFilter() { // from class: android.text.util.Linkify.3
        @Override // android.text.util.Linkify.TransformFilter
        public final java.lang.String transformUrl(java.util.regex.Matcher matcher, java.lang.String str) {
            return android.util.Patterns.digitsAndPlusOnly(matcher);
        }
    };
    private static final java.util.function.Function<java.lang.String, android.text.style.URLSpan> DEFAULT_SPAN_FACTORY = new java.util.function.Function() { // from class: android.text.util.Linkify$$ExternalSyntheticLambda0
        @Override // java.util.function.Function
        public final java.lang.Object apply(java.lang.Object obj) {
            return android.text.util.Linkify.lambda$static$0((java.lang.String) obj);
        }
    };

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface LinkifyMask {
    }

    public interface MatchFilter {
        boolean acceptMatch(java.lang.CharSequence charSequence, int i, int i2);
    }

    public interface TransformFilter {
        java.lang.String transformUrl(java.util.regex.Matcher matcher, java.lang.String str);
    }

    public static final boolean addLinks(android.text.Spannable spannable, int i) {
        return addLinks(spannable, i, null, null);
    }

    public static final boolean addLinks(android.text.Spannable spannable, int i, java.util.function.Function<java.lang.String, android.text.style.URLSpan> function) {
        return addLinks(spannable, i, null, function);
    }

    private static boolean addLinks(android.text.Spannable spannable, int i, android.content.Context context, java.util.function.Function<java.lang.String, android.text.style.URLSpan> function) {
        if (spannable != null && containsUnsupportedCharacters(spannable.toString())) {
            android.util.EventLog.writeEvent(1397638484, "116321860", -1, "");
            return false;
        }
        if (i == 0) {
            return false;
        }
        android.text.style.URLSpan[] uRLSpanArr = (android.text.style.URLSpan[]) spannable.getSpans(0, spannable.length(), android.text.style.URLSpan.class);
        for (int length = uRLSpanArr.length - 1; length >= 0; length--) {
            spannable.removeSpan(uRLSpanArr[length]);
        }
        java.util.ArrayList arrayList = new java.util.ArrayList();
        if ((i & 1) != 0) {
            gatherLinks(arrayList, spannable, android.util.Patterns.AUTOLINK_WEB_URL, new java.lang.String[]{"http://", "https://", "rtsp://", "ftp://"}, sUrlMatchFilter, null);
        }
        if ((i & 2) != 0) {
            gatherLinks(arrayList, spannable, android.util.Patterns.AUTOLINK_EMAIL_ADDRESS, new java.lang.String[]{"mailto:"}, null, null);
        }
        if ((i & 4) != 0) {
            gatherTelLinks(arrayList, spannable, context);
        }
        if ((i & 8) != 0) {
            gatherMapLinks(arrayList, spannable);
        }
        pruneOverlaps(arrayList);
        if (arrayList.size() == 0) {
            return false;
        }
        java.util.Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            android.text.util.LinkSpec linkSpec = (android.text.util.LinkSpec) it.next();
            applyLink(linkSpec.url, linkSpec.start, linkSpec.end, spannable, function);
        }
        return true;
    }

    public static boolean containsUnsupportedCharacters(java.lang.String str) {
        if (str.contains("\u202c")) {
            android.util.Log.e(LOG_TAG, "Unsupported character for applying links: u202C");
            return true;
        }
        if (str.contains("\u202d")) {
            android.util.Log.e(LOG_TAG, "Unsupported character for applying links: u202D");
            return true;
        }
        if (str.contains("\u202e")) {
            android.util.Log.e(LOG_TAG, "Unsupported character for applying links: u202E");
            return true;
        }
        return false;
    }

    public static final boolean addLinks(android.widget.TextView textView, int i) {
        if (i == 0) {
            return false;
        }
        android.content.Context context = textView.getContext();
        java.lang.CharSequence text = textView.getText();
        if (text instanceof android.text.Spannable) {
            if (!addLinks((android.text.Spannable) text, i, context, null)) {
                return false;
            }
            addLinkMovementMethod(textView);
            return true;
        }
        android.text.SpannableString valueOf = android.text.SpannableString.valueOf(text);
        if (!addLinks(valueOf, i, context, null)) {
            return false;
        }
        addLinkMovementMethod(textView);
        textView.lambda$setTextAsync$0(valueOf);
        return true;
    }

    private static final void addLinkMovementMethod(android.widget.TextView textView) {
        android.text.method.MovementMethod movementMethod = textView.getMovementMethod();
        if ((movementMethod == null || !(movementMethod instanceof android.text.method.LinkMovementMethod)) && textView.getLinksClickable()) {
            textView.setMovementMethod(android.text.method.LinkMovementMethod.getInstance());
        }
    }

    public static final void addLinks(android.widget.TextView textView, java.util.regex.Pattern pattern, java.lang.String str) {
        addLinks(textView, pattern, str, (java.lang.String[]) null, (android.text.util.Linkify.MatchFilter) null, (android.text.util.Linkify.TransformFilter) null);
    }

    public static final void addLinks(android.widget.TextView textView, java.util.regex.Pattern pattern, java.lang.String str, android.text.util.Linkify.MatchFilter matchFilter, android.text.util.Linkify.TransformFilter transformFilter) {
        addLinks(textView, pattern, str, (java.lang.String[]) null, matchFilter, transformFilter);
    }

    public static final void addLinks(android.widget.TextView textView, java.util.regex.Pattern pattern, java.lang.String str, java.lang.String[] strArr, android.text.util.Linkify.MatchFilter matchFilter, android.text.util.Linkify.TransformFilter transformFilter) {
        android.text.SpannableString valueOf = android.text.SpannableString.valueOf(textView.getText());
        if (addLinks(valueOf, pattern, str, strArr, matchFilter, transformFilter)) {
            textView.lambda$setTextAsync$0(valueOf);
            addLinkMovementMethod(textView);
        }
    }

    public static final boolean addLinks(android.text.Spannable spannable, java.util.regex.Pattern pattern, java.lang.String str) {
        return addLinks(spannable, pattern, str, (java.lang.String[]) null, (android.text.util.Linkify.MatchFilter) null, (android.text.util.Linkify.TransformFilter) null);
    }

    public static final boolean addLinks(android.text.Spannable spannable, java.util.regex.Pattern pattern, java.lang.String str, android.text.util.Linkify.MatchFilter matchFilter, android.text.util.Linkify.TransformFilter transformFilter) {
        return addLinks(spannable, pattern, str, (java.lang.String[]) null, matchFilter, transformFilter);
    }

    public static final boolean addLinks(android.text.Spannable spannable, java.util.regex.Pattern pattern, java.lang.String str, java.lang.String[] strArr, android.text.util.Linkify.MatchFilter matchFilter, android.text.util.Linkify.TransformFilter transformFilter) {
        return addLinks(spannable, pattern, str, strArr, matchFilter, transformFilter, null);
    }

    public static final boolean addLinks(android.text.Spannable spannable, java.util.regex.Pattern pattern, java.lang.String str, java.lang.String[] strArr, android.text.util.Linkify.MatchFilter matchFilter, android.text.util.Linkify.TransformFilter transformFilter, java.util.function.Function<java.lang.String, android.text.style.URLSpan> function) {
        boolean z;
        if (spannable != null && containsUnsupportedCharacters(spannable.toString())) {
            android.util.EventLog.writeEvent(1397638484, "116321860", -1, "");
            return false;
        }
        if (str == null) {
            str = "";
        }
        if (strArr == null || strArr.length < 1) {
            strArr = libcore.util.EmptyArray.STRING;
        }
        java.lang.String[] strArr2 = new java.lang.String[strArr.length + 1];
        strArr2[0] = str.toLowerCase(java.util.Locale.ROOT);
        int i = 0;
        while (i < strArr.length) {
            java.lang.String str2 = strArr[i];
            i++;
            strArr2[i] = str2 == null ? "" : str2.toLowerCase(java.util.Locale.ROOT);
        }
        java.util.regex.Matcher matcher = pattern.matcher(spannable);
        boolean z2 = false;
        while (matcher.find()) {
            int start = matcher.start();
            int end = matcher.end();
            if (matchFilter == null) {
                z = true;
            } else {
                z = matchFilter.acceptMatch(spannable, start, end);
            }
            if (z) {
                applyLink(makeUrl(matcher.group(0), strArr2, matcher, transformFilter), start, end, spannable, function);
                z2 = true;
            }
        }
        return z2;
    }

    private static void applyLink(java.lang.String str, int i, int i2, android.text.Spannable spannable, java.util.function.Function<java.lang.String, android.text.style.URLSpan> function) {
        if (function == null) {
            function = DEFAULT_SPAN_FACTORY;
        }
        spannable.setSpan(function.apply(str), i, i2, 33);
    }

    private static final java.lang.String makeUrl(java.lang.String str, java.lang.String[] strArr, java.util.regex.Matcher matcher, android.text.util.Linkify.TransformFilter transformFilter) {
        boolean z;
        if (transformFilter != null) {
            str = transformFilter.transformUrl(matcher, str);
        }
        int i = 0;
        while (true) {
            if (i >= strArr.length) {
                z = false;
                break;
            }
            if (!str.regionMatches(true, 0, strArr[i], 0, strArr[i].length())) {
                i++;
            } else {
                z = true;
                if (!str.regionMatches(false, 0, strArr[i], 0, strArr[i].length())) {
                    str = strArr[i] + str.substring(strArr[i].length());
                }
            }
        }
        if (!z && strArr.length > 0) {
            return strArr[0] + str;
        }
        return str;
    }

    private static final void gatherLinks(java.util.ArrayList<android.text.util.LinkSpec> arrayList, android.text.Spannable spannable, java.util.regex.Pattern pattern, java.lang.String[] strArr, android.text.util.Linkify.MatchFilter matchFilter, android.text.util.Linkify.TransformFilter transformFilter) {
        java.util.regex.Matcher matcher = pattern.matcher(spannable);
        while (matcher.find()) {
            int start = matcher.start();
            int end = matcher.end();
            if (matchFilter == null || matchFilter.acceptMatch(spannable, start, end)) {
                android.text.util.LinkSpec linkSpec = new android.text.util.LinkSpec();
                linkSpec.url = makeUrl(matcher.group(0), strArr, matcher, transformFilter);
                linkSpec.start = start;
                linkSpec.end = end;
                arrayList.add(linkSpec);
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x0049 A[LOOP:0: B:10:0x0043->B:12:0x0049, LOOP_END] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private static void gatherTelLinks(java.util.ArrayList<android.text.util.LinkSpec> arrayList, android.text.Spannable spannable, android.content.Context context) {
        java.lang.String str;
        com.android.i18n.phonenumbers.PhoneNumberUtil phoneNumberUtil = com.android.i18n.phonenumbers.PhoneNumberUtil.getInstance();
        if (context == null) {
            context = android.app.ActivityThread.currentApplication();
        }
        java.lang.String country = java.util.Locale.getDefault().getCountry();
        if (context != null) {
            java.lang.String simCountryIso = ((android.telephony.TelephonyManager) context.getSystemService(android.telephony.TelephonyManager.class)).getSimCountryIso();
            if (!android.text.TextUtils.isEmpty(simCountryIso)) {
                str = simCountryIso.toUpperCase(java.util.Locale.US);
                for (com.android.i18n.phonenumbers.PhoneNumberMatch phoneNumberMatch : phoneNumberUtil.findNumbers(spannable.toString(), str, com.android.i18n.phonenumbers.PhoneNumberUtil.Leniency.POSSIBLE, Long.MAX_VALUE)) {
                    android.text.util.LinkSpec linkSpec = new android.text.util.LinkSpec();
                    linkSpec.url = android.webkit.WebView.SCHEME_TEL + android.telephony.PhoneNumberUtils.normalizeNumber(phoneNumberMatch.rawString());
                    linkSpec.start = phoneNumberMatch.start();
                    linkSpec.end = phoneNumberMatch.end();
                    arrayList.add(linkSpec);
                }
            }
        }
        str = country;
        while (r7.hasNext()) {
        }
    }

    private static final void gatherMapLinks(java.util.ArrayList<android.text.util.LinkSpec> arrayList, android.text.Spannable spannable) {
        int indexOf;
        java.lang.String obj = spannable.toString();
        int i = 0;
        while (true) {
            try {
                java.lang.String findAddress = android.webkit.WebView.findAddress(obj);
                if (findAddress != null && (indexOf = obj.indexOf(findAddress)) >= 0) {
                    android.text.util.LinkSpec linkSpec = new android.text.util.LinkSpec();
                    int length = findAddress.length() + indexOf;
                    linkSpec.start = indexOf + i;
                    i += length;
                    linkSpec.end = i;
                    obj = obj.substring(length);
                    try {
                        linkSpec.url = android.webkit.WebView.SCHEME_GEO + java.net.URLEncoder.encode(findAddress, android.media.tv.SignalingDataInfo.CONTENT_ENCODING_UTF_8);
                        arrayList.add(linkSpec);
                    } catch (java.io.UnsupportedEncodingException e) {
                    }
                }
                return;
            } catch (java.lang.UnsupportedOperationException e2) {
                return;
            }
        }
    }

    private static final void pruneOverlaps(java.util.ArrayList<android.text.util.LinkSpec> arrayList) {
        int i;
        java.util.Collections.sort(arrayList, new java.util.Comparator<android.text.util.LinkSpec>() { // from class: android.text.util.Linkify.4
            @Override // java.util.Comparator
            public final int compare(android.text.util.LinkSpec linkSpec, android.text.util.LinkSpec linkSpec2) {
                if (linkSpec.start < linkSpec2.start) {
                    return -1;
                }
                if (linkSpec.start <= linkSpec2.start && linkSpec.end >= linkSpec2.end) {
                    return linkSpec.end > linkSpec2.end ? -1 : 0;
                }
                return 1;
            }
        });
        int size = arrayList.size();
        int i2 = 0;
        while (i2 < size - 1) {
            android.text.util.LinkSpec linkSpec = arrayList.get(i2);
            int i3 = i2 + 1;
            android.text.util.LinkSpec linkSpec2 = arrayList.get(i3);
            if (linkSpec.start <= linkSpec2.start && linkSpec.end > linkSpec2.start) {
                if (linkSpec2.end <= linkSpec.end) {
                    i = i3;
                } else if (linkSpec.end - linkSpec.start > linkSpec2.end - linkSpec2.start) {
                    i = i3;
                } else if (linkSpec.end - linkSpec.start >= linkSpec2.end - linkSpec2.start) {
                    i = -1;
                } else {
                    i = i2;
                }
                if (i != -1) {
                    arrayList.remove(i);
                    size--;
                }
            }
            i2 = i3;
        }
    }

    static /* synthetic */ android.text.style.URLSpan lambda$static$0(java.lang.String str) {
        return new android.text.style.URLSpan(str);
    }
}
