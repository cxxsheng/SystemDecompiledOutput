package android.webkit;

/* loaded from: classes4.dex */
class FindAddress {
    private static final java.lang.String HOUSE_COMPONENT = "(?:one|[0-9]+([a-z](?=[^a-z]|$)|st|nd|rd|th)?)";
    private static final java.lang.String HOUSE_END = "(?=[,\"'\t  \u1680\u2000\u2001\u2002\u2003\u2004\u2005\u2006 \u2008\u2009\u200a \u205f\u3000\n\u000b\f\r\u0085\u2028\u2029]|$)";
    private static final java.lang.String HOUSE_POST_DELIM = ",\"'\t  \u1680\u2000\u2001\u2002\u2003\u2004\u2005\u2006 \u2008\u2009\u200a \u205f\u3000\n\u000b\f\r\u0085\u2028\u2029";
    private static final java.lang.String HOUSE_PRE_DELIM = ":,\"'\t  \u1680\u2000\u2001\u2002\u2003\u2004\u2005\u2006 \u2008\u2009\u200a \u205f\u3000\n\u000b\f\r\u0085\u2028\u2029";
    private static final int MAX_ADDRESS_LINES = 5;
    private static final int MAX_ADDRESS_WORDS = 14;
    private static final int MAX_LOCATION_NAME_DISTANCE = 5;
    private static final int MIN_ADDRESS_WORDS = 4;
    private static final java.lang.String NL = "\n\u000b\f\r\u0085\u2028\u2029";
    private static final java.lang.String SP = "\t  \u1680\u2000\u2001\u2002\u2003\u2004\u2005\u2006 \u2008\u2009\u200a \u205f\u3000";
    private static final java.lang.String WORD_DELIM = ",*•\t  \u1680\u2000\u2001\u2002\u2003\u2004\u2005\u2006 \u2008\u2009\u200a \u205f\u3000\n\u000b\f\r\u0085\u2028\u2029";
    private static final java.lang.String WORD_END = "(?=[,*•\t  \u1680\u2000\u2001\u2002\u2003\u2004\u2005\u2006 \u2008\u2009\u200a \u205f\u3000\n\u000b\f\r\u0085\u2028\u2029]|$)";
    private static final java.lang.String WS = "\t  \u1680\u2000\u2001\u2002\u2003\u2004\u2005\u2006 \u2008\u2009\u200a \u205f\u3000\n\u000b\f\r\u0085\u2028\u2029";
    private static final int kMaxAddressNameWordLength = 25;
    private static final android.webkit.FindAddress.ZipRange[] sStateZipCodeRanges = {new android.webkit.FindAddress.ZipRange(99, 99, -1, -1), new android.webkit.FindAddress.ZipRange(35, 36, -1, -1), new android.webkit.FindAddress.ZipRange(71, 72, -1, -1), new android.webkit.FindAddress.ZipRange(96, 96, -1, -1), new android.webkit.FindAddress.ZipRange(85, 86, -1, -1), new android.webkit.FindAddress.ZipRange(90, 96, -1, -1), new android.webkit.FindAddress.ZipRange(80, 81, -1, -1), new android.webkit.FindAddress.ZipRange(6, 6, -1, -1), new android.webkit.FindAddress.ZipRange(20, 20, -1, -1), new android.webkit.FindAddress.ZipRange(19, 19, -1, -1), new android.webkit.FindAddress.ZipRange(32, 34, -1, -1), new android.webkit.FindAddress.ZipRange(96, 96, -1, -1), new android.webkit.FindAddress.ZipRange(30, 31, -1, -1), new android.webkit.FindAddress.ZipRange(96, 96, -1, -1), new android.webkit.FindAddress.ZipRange(96, 96, -1, -1), new android.webkit.FindAddress.ZipRange(50, 52, -1, -1), new android.webkit.FindAddress.ZipRange(83, 83, -1, -1), new android.webkit.FindAddress.ZipRange(60, 62, -1, -1), new android.webkit.FindAddress.ZipRange(46, 47, -1, -1), new android.webkit.FindAddress.ZipRange(66, 67, 73, -1), new android.webkit.FindAddress.ZipRange(40, 42, -1, -1), new android.webkit.FindAddress.ZipRange(70, 71, -1, -1), new android.webkit.FindAddress.ZipRange(1, 2, -1, -1), new android.webkit.FindAddress.ZipRange(20, 21, -1, -1), new android.webkit.FindAddress.ZipRange(3, 4, -1, -1), new android.webkit.FindAddress.ZipRange(96, 96, -1, -1), new android.webkit.FindAddress.ZipRange(48, 49, -1, -1), new android.webkit.FindAddress.ZipRange(55, 56, -1, -1), new android.webkit.FindAddress.ZipRange(63, 65, -1, -1), new android.webkit.FindAddress.ZipRange(96, 96, -1, -1), new android.webkit.FindAddress.ZipRange(38, 39, -1, -1), new android.webkit.FindAddress.ZipRange(55, 56, -1, -1), new android.webkit.FindAddress.ZipRange(27, 28, -1, -1), new android.webkit.FindAddress.ZipRange(58, 58, -1, -1), new android.webkit.FindAddress.ZipRange(68, 69, -1, -1), new android.webkit.FindAddress.ZipRange(3, 4, -1, -1), new android.webkit.FindAddress.ZipRange(7, 8, -1, -1), new android.webkit.FindAddress.ZipRange(87, 88, 86, -1), new android.webkit.FindAddress.ZipRange(88, 89, 96, -1), new android.webkit.FindAddress.ZipRange(10, 14, 0, 6), new android.webkit.FindAddress.ZipRange(43, 45, -1, -1), new android.webkit.FindAddress.ZipRange(73, 74, -1, -1), new android.webkit.FindAddress.ZipRange(97, 97, -1, -1), new android.webkit.FindAddress.ZipRange(15, 19, -1, -1), new android.webkit.FindAddress.ZipRange(6, 6, 0, 9), new android.webkit.FindAddress.ZipRange(96, 96, -1, -1), new android.webkit.FindAddress.ZipRange(2, 2, -1, -1), new android.webkit.FindAddress.ZipRange(29, 29, -1, -1), new android.webkit.FindAddress.ZipRange(57, 57, -1, -1), new android.webkit.FindAddress.ZipRange(37, 38, -1, -1), new android.webkit.FindAddress.ZipRange(75, 79, 87, 88), new android.webkit.FindAddress.ZipRange(84, 84, -1, -1), new android.webkit.FindAddress.ZipRange(22, 24, 20, -1), new android.webkit.FindAddress.ZipRange(6, 9, -1, -1), new android.webkit.FindAddress.ZipRange(5, 5, -1, -1), new android.webkit.FindAddress.ZipRange(98, 99, -1, -1), new android.webkit.FindAddress.ZipRange(53, 54, -1, -1), new android.webkit.FindAddress.ZipRange(24, 26, -1, -1), new android.webkit.FindAddress.ZipRange(82, 83, -1, -1)};
    private static final java.util.regex.Pattern sWordRe = java.util.regex.Pattern.compile("[^,*•\t  \u1680\u2000\u2001\u2002\u2003\u2004\u2005\u2006 \u2008\u2009\u200a \u205f\u3000\n\u000b\f\r\u0085\u2028\u2029]+(?=[,*•\t  \u1680\u2000\u2001\u2002\u2003\u2004\u2005\u2006 \u2008\u2009\u200a \u205f\u3000\n\u000b\f\r\u0085\u2028\u2029]|$)", 2);
    private static final java.util.regex.Pattern sHouseNumberRe = java.util.regex.Pattern.compile("(?:one|[0-9]+([a-z](?=[^a-z]|$)|st|nd|rd|th)?)(?:-(?:one|[0-9]+([a-z](?=[^a-z]|$)|st|nd|rd|th)?))*(?=[,\"'\t  \u1680\u2000\u2001\u2002\u2003\u2004\u2005\u2006 \u2008\u2009\u200a \u205f\u3000\n\u000b\f\r\u0085\u2028\u2029]|$)", 2);
    private static final java.util.regex.Pattern sStateRe = java.util.regex.Pattern.compile("(?:(ak|alaska)|(al|alabama)|(ar|arkansas)|(as|american[\t  \u1680\u2000\u2001\u2002\u2003\u2004\u2005\u2006 \u2008\u2009\u200a \u205f\u3000]+samoa)|(az|arizona)|(ca|california)|(co|colorado)|(ct|connecticut)|(dc|district[\t  \u1680\u2000\u2001\u2002\u2003\u2004\u2005\u2006 \u2008\u2009\u200a \u205f\u3000]+of[\t  \u1680\u2000\u2001\u2002\u2003\u2004\u2005\u2006 \u2008\u2009\u200a \u205f\u3000]+columbia)|(de|delaware)|(fl|florida)|(fm|federated[\t  \u1680\u2000\u2001\u2002\u2003\u2004\u2005\u2006 \u2008\u2009\u200a \u205f\u3000]+states[\t  \u1680\u2000\u2001\u2002\u2003\u2004\u2005\u2006 \u2008\u2009\u200a \u205f\u3000]+of[\t  \u1680\u2000\u2001\u2002\u2003\u2004\u2005\u2006 \u2008\u2009\u200a \u205f\u3000]+micronesia)|(ga|georgia)|(gu|guam)|(hi|hawaii)|(ia|iowa)|(id|idaho)|(il|illinois)|(in|indiana)|(ks|kansas)|(ky|kentucky)|(la|louisiana)|(ma|massachusetts)|(md|maryland)|(me|maine)|(mh|marshall[\t  \u1680\u2000\u2001\u2002\u2003\u2004\u2005\u2006 \u2008\u2009\u200a \u205f\u3000]+islands)|(mi|michigan)|(mn|minnesota)|(mo|missouri)|(mp|northern[\t  \u1680\u2000\u2001\u2002\u2003\u2004\u2005\u2006 \u2008\u2009\u200a \u205f\u3000]+mariana[\t  \u1680\u2000\u2001\u2002\u2003\u2004\u2005\u2006 \u2008\u2009\u200a \u205f\u3000]+islands)|(ms|mississippi)|(mt|montana)|(nc|north[\t  \u1680\u2000\u2001\u2002\u2003\u2004\u2005\u2006 \u2008\u2009\u200a \u205f\u3000]+carolina)|(nd|north[\t  \u1680\u2000\u2001\u2002\u2003\u2004\u2005\u2006 \u2008\u2009\u200a \u205f\u3000]+dakota)|(ne|nebraska)|(nh|new[\t  \u1680\u2000\u2001\u2002\u2003\u2004\u2005\u2006 \u2008\u2009\u200a \u205f\u3000]+hampshire)|(nj|new[\t  \u1680\u2000\u2001\u2002\u2003\u2004\u2005\u2006 \u2008\u2009\u200a \u205f\u3000]+jersey)|(nm|new[\t  \u1680\u2000\u2001\u2002\u2003\u2004\u2005\u2006 \u2008\u2009\u200a \u205f\u3000]+mexico)|(nv|nevada)|(ny|new[\t  \u1680\u2000\u2001\u2002\u2003\u2004\u2005\u2006 \u2008\u2009\u200a \u205f\u3000]+york)|(oh|ohio)|(ok|oklahoma)|(or|oregon)|(pa|pennsylvania)|(pr|puerto[\t  \u1680\u2000\u2001\u2002\u2003\u2004\u2005\u2006 \u2008\u2009\u200a \u205f\u3000]+rico)|(pw|palau)|(ri|rhode[\t  \u1680\u2000\u2001\u2002\u2003\u2004\u2005\u2006 \u2008\u2009\u200a \u205f\u3000]+island)|(sc|south[\t  \u1680\u2000\u2001\u2002\u2003\u2004\u2005\u2006 \u2008\u2009\u200a \u205f\u3000]+carolina)|(sd|south[\t  \u1680\u2000\u2001\u2002\u2003\u2004\u2005\u2006 \u2008\u2009\u200a \u205f\u3000]+dakota)|(tn|tennessee)|(tx|texas)|(ut|utah)|(va|virginia)|(vi|virgin[\t  \u1680\u2000\u2001\u2002\u2003\u2004\u2005\u2006 \u2008\u2009\u200a \u205f\u3000]+islands)|(vt|vermont)|(wa|washington)|(wi|wisconsin)|(wv|west[\t  \u1680\u2000\u2001\u2002\u2003\u2004\u2005\u2006 \u2008\u2009\u200a \u205f\u3000]+virginia)|(wy|wyoming))(?=[,*•\t  \u1680\u2000\u2001\u2002\u2003\u2004\u2005\u2006 \u2008\u2009\u200a \u205f\u3000\n\u000b\f\r\u0085\u2028\u2029]|$)", 2);
    private static final java.util.regex.Pattern sLocationNameRe = java.util.regex.Pattern.compile("(?:alley|annex|arcade|ave[.]?|avenue|alameda|bayou|beach|bend|bluffs?|bottom|boulevard|branch|bridge|brooks?|burgs?|bypass|broadway|camino|camp|canyon|cape|causeway|centers?|circles?|cliffs?|club|common|corners?|course|courts?|coves?|creek|crescent|crest|crossing|crossroad|curve|circulo|dale|dam|divide|drives?|estates?|expressway|extensions?|falls?|ferry|fields?|flats?|fords?|forest|forges?|forks?|fort|freeway|gardens?|gateway|glens?|greens?|groves?|harbors?|haven|heights|highway|hills?|hollow|inlet|islands?|isle|junctions?|keys?|knolls?|lakes?|land|landing|lane|lights?|loaf|locks?|lodge|loop|mall|manors?|meadows?|mews|mills?|mission|motorway|mount|mountains?|neck|orchard|oval|overpass|parks?|parkways?|pass|passage|path|pike|pines?|plains?|plaza|points?|ports?|prairie|privada|radial|ramp|ranch|rapids?|rd[.]?|rest|ridges?|river|roads?|route|row|rue|run|shoals?|shores?|skyway|springs?|spurs?|squares?|station|stravenue|stream|st[.]?|streets?|summit|speedway|terrace|throughway|trace|track|trafficway|trail|tunnel|turnpike|underpass|unions?|valleys?|viaduct|views?|villages?|ville|vista|walks?|wall|ways?|wells?|xing|xrd)(?=[,*•\t  \u1680\u2000\u2001\u2002\u2003\u2004\u2005\u2006 \u2008\u2009\u200a \u205f\u3000\n\u000b\f\r\u0085\u2028\u2029]|$)", 2);
    private static final java.util.regex.Pattern sSuffixedNumberRe = java.util.regex.Pattern.compile("([0-9]+)(st|nd|rd|th)", 2);
    private static final java.util.regex.Pattern sZipCodeRe = java.util.regex.Pattern.compile("(?:[0-9]{5}(?:-[0-9]{4})?)(?=[,*•\t  \u1680\u2000\u2001\u2002\u2003\u2004\u2005\u2006 \u2008\u2009\u200a \u205f\u3000\n\u000b\f\r\u0085\u2028\u2029]|$)", 2);

    FindAddress() {
    }

    static class ZipRange {
        int mException1;
        int mException2;
        int mHigh;
        int mLow;

        ZipRange(int i, int i2, int i3, int i4) {
            this.mLow = i;
            this.mHigh = i2;
            this.mException1 = i3;
            this.mException2 = i3;
        }

        boolean matches(java.lang.String str) {
            int parseInt = java.lang.Integer.parseInt(str.substring(0, 2));
            return (this.mLow <= parseInt && parseInt <= this.mHigh) || parseInt == this.mException1 || parseInt == this.mException2;
        }
    }

    private static boolean checkHouseNumber(java.lang.String str) {
        int i = 0;
        for (int i2 = 0; i2 < str.length(); i2++) {
            if (java.lang.Character.isDigit(str.charAt(i2))) {
                i++;
            }
        }
        if (i > 5) {
            return false;
        }
        java.util.regex.Matcher matcher = sSuffixedNumberRe.matcher(str);
        if (!matcher.find()) {
            return true;
        }
        int parseInt = java.lang.Integer.parseInt(matcher.group(1));
        if (parseInt == 0) {
            return false;
        }
        java.lang.String lowerCase = matcher.group(2).toLowerCase(java.util.Locale.getDefault());
        switch (parseInt % 10) {
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
        }
        return false;
    }

    private static java.util.regex.MatchResult matchHouseNumber(java.lang.String str, int i) {
        if (i > 0 && HOUSE_PRE_DELIM.indexOf(str.charAt(i - 1)) == -1) {
            return null;
        }
        java.util.regex.Matcher region = sHouseNumberRe.matcher(str).region(i, str.length());
        if (region.lookingAt()) {
            java.util.regex.MatchResult matchResult = region.toMatchResult();
            if (checkHouseNumber(matchResult.group(0))) {
                return matchResult;
            }
        }
        return null;
    }

    private static java.util.regex.MatchResult matchState(java.lang.String str, int i) {
        if (i > 0 && WORD_DELIM.indexOf(str.charAt(i - 1)) == -1) {
            return null;
        }
        java.util.regex.Matcher region = sStateRe.matcher(str).region(i, str.length());
        if (region.lookingAt()) {
            return region.toMatchResult();
        }
        return null;
    }

    private static boolean isValidZipCode(java.lang.String str, java.util.regex.MatchResult matchResult) {
        if (matchResult == null) {
            return false;
        }
        int groupCount = matchResult.groupCount();
        while (true) {
            if (groupCount <= 0) {
                break;
            }
            int i = groupCount - 1;
            if (matchResult.group(groupCount) != null) {
                groupCount = i;
                break;
            }
            groupCount = i;
        }
        return sZipCodeRe.matcher(str).matches() && sStateZipCodeRanges[groupCount].matches(str);
    }

    private static boolean isValidLocationName(java.lang.String str) {
        return sLocationNameRe.matcher(str).matches();
    }

    /* JADX WARN: Code restructure failed: missing block: B:42:0x00e1, code lost:
    
        if (r10 <= 0) goto L60;
     */
    /* JADX WARN: Code restructure failed: missing block: B:43:0x00e3, code lost:
    
        return r10;
     */
    /* JADX WARN: Code restructure failed: missing block: B:44:0x00e4, code lost:
    
        if (r9 <= 0) goto L62;
     */
    /* JADX WARN: Code restructure failed: missing block: B:45:0x00e7, code lost:
    
        r9 = r14;
     */
    /* JADX WARN: Code restructure failed: missing block: B:47:0x00e9, code lost:
    
        return -r9;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private static int attemptMatch(java.lang.String str, java.util.regex.MatchResult matchResult) {
        java.util.regex.MatchResult matchState;
        int end = matchResult.end();
        java.util.regex.Matcher matcher = sWordRe.matcher(str);
        java.lang.String str2 = "";
        int i = -1;
        int i2 = -1;
        int i3 = 1;
        int i4 = 1;
        boolean z = true;
        boolean z2 = false;
        while (true) {
            if (end < str.length()) {
                if (!matcher.find(end)) {
                    return -str.length();
                }
                if (matcher.end() - matcher.start() > 25) {
                    return -matcher.end();
                }
                while (end < matcher.start()) {
                    int i5 = end + 1;
                    if (NL.indexOf(str.charAt(end)) != -1) {
                        i3++;
                    }
                    end = i5;
                }
                if (i3 > 5 || (i4 = i4 + 1) > 14) {
                    break;
                }
                if (matchHouseNumber(str, end) != null) {
                    if (z && i3 > 1) {
                        return -end;
                    }
                    if (i == -1) {
                        i = end;
                    }
                } else if (isValidLocationName(matcher.group(0))) {
                    z2 = true;
                    z = false;
                } else {
                    if (i4 == 5 && !z2) {
                        end = matcher.end();
                        break;
                    }
                    if (z2 && i4 > 4 && (matchState = matchState(str, end)) != null) {
                        if (str2.equals("et") && matchState.group(0).equals(android.app.blob.XmlTags.ATTR_ALGO)) {
                            end = matchState.end();
                            break;
                        }
                        java.util.regex.Matcher matcher2 = sWordRe.matcher(str);
                        if (matcher2.find(matchState.end())) {
                            if (isValidZipCode(matcher2.group(0), matchState)) {
                                return matcher2.end();
                            }
                        } else {
                            i2 = matchState.end();
                            z = false;
                        }
                    }
                    z = false;
                }
                str2 = matcher.group(0);
                end = matcher.end();
            } else {
                break;
            }
        }
    }

    static java.lang.String findAddress(java.lang.String str) {
        java.util.regex.Matcher matcher = sHouseNumberRe.matcher(str);
        int i = 0;
        while (matcher.find(i)) {
            if (checkHouseNumber(matcher.group(0))) {
                int start = matcher.start();
                int attemptMatch = attemptMatch(str, matcher);
                if (attemptMatch > 0) {
                    return str.substring(start, attemptMatch);
                }
                i = -attemptMatch;
            } else {
                i = matcher.end();
            }
        }
        return null;
    }
}
