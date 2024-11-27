package android.webkit;

/* loaded from: classes4.dex */
public final class URLUtil {
    static final java.lang.String ASSET_BASE = "file:///android_asset/";
    static final java.lang.String CONTENT_BASE = "content:";
    private static final java.util.regex.Pattern CONTENT_DISPOSITION_PATTERN = java.util.regex.Pattern.compile("attachment;\\s*filename\\s*=\\s*(\"?)([^\"]*)\\1\\s*$", 2);
    private static final java.util.regex.Pattern DISPOSITION_PATTERN = java.util.regex.Pattern.compile("\\s*(\\S+?) # Group 1: parameter name\n\\s*=\\s* # Match equals sign\n(?: # non-capturing group of options\n   '( (?: [^'\\\\] | \\\\. )* )' # Group 2: single-quoted\n | \"( (?: [^\"\\\\] | \\\\. )*  )\" # Group 3: double-quoted\n | ( [^'\"][^;\\s]* ) # Group 4: un-quoted parameter\n)\\s*;? # Optional end semicolon", 4);
    static final java.lang.String FILE_BASE = "file:";
    private static final java.lang.String LOGTAG = "webkit";
    static final long PARSE_CONTENT_DISPOSITION_USING_RFC_6266 = 319400769;
    static final java.lang.String PROXY_BASE = "file:///cookieless_proxy/";
    static final java.lang.String RESOURCE_BASE = "file:///android_res/";
    private static final boolean TRACE = false;

    public static java.lang.String guessUrl(java.lang.String str) {
        java.lang.String str2;
        if (str.length() == 0 || str.startsWith("about:") || str.startsWith("data:") || str.startsWith(FILE_BASE) || str.startsWith("javascript:")) {
            return str;
        }
        if (!str.endsWith(android.media.MediaMetrics.SEPARATOR)) {
            str2 = str;
        } else {
            str2 = str.substring(0, str.length() - 1);
        }
        try {
            android.net.WebAddress webAddress = new android.net.WebAddress(str2);
            if (webAddress.getHost().indexOf(46) == -1) {
                webAddress.setHost("www." + webAddress.getHost() + ".com");
            }
            return webAddress.toString();
        } catch (android.net.ParseException e) {
            return str;
        }
    }

    public static java.lang.String composeSearchUrl(java.lang.String str, java.lang.String str2, java.lang.String str3) {
        int indexOf = str2.indexOf(str3);
        if (indexOf < 0) {
            return null;
        }
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append(str2.substring(0, indexOf));
        try {
            sb.append(java.net.URLEncoder.encode(str, "utf-8"));
            sb.append(str2.substring(indexOf + str3.length()));
            return sb.toString();
        } catch (java.io.UnsupportedEncodingException e) {
            return null;
        }
    }

    public static byte[] decode(byte[] bArr) throws java.lang.IllegalArgumentException {
        if (bArr.length == 0) {
            return new byte[0];
        }
        byte[] bArr2 = new byte[bArr.length];
        int i = 0;
        int i2 = 0;
        while (i < bArr.length) {
            byte b = bArr[i];
            if (b == 37) {
                if (bArr.length - i > 2) {
                    int parseHex = parseHex(bArr[i + 1]) * 16;
                    i += 2;
                    b = (byte) (parseHex + parseHex(bArr[i]));
                } else {
                    throw new java.lang.IllegalArgumentException("Invalid format");
                }
            }
            bArr2[i2] = b;
            i++;
            i2++;
        }
        byte[] bArr3 = new byte[i2];
        java.lang.System.arraycopy(bArr2, 0, bArr3, 0, i2);
        return bArr3;
    }

    static boolean verifyURLEncoding(java.lang.String str) {
        int length = str.length();
        if (length == 0) {
            return false;
        }
        int indexOf = str.indexOf(37);
        while (indexOf >= 0 && indexOf < length) {
            if (indexOf >= length - 2) {
                return false;
            }
            int i = indexOf + 1;
            try {
                parseHex((byte) str.charAt(i));
                int i2 = i + 1;
                parseHex((byte) str.charAt(i2));
                indexOf = str.indexOf(37, i2 + 1);
            } catch (java.lang.IllegalArgumentException e) {
                return false;
            }
        }
        return true;
    }

    private static int parseHex(byte b) {
        if (b >= 48 && b <= 57) {
            return b - 48;
        }
        if (b >= 65 && b <= 70) {
            return (b - 65) + 10;
        }
        if (b >= 97 && b <= 102) {
            return (b - 97) + 10;
        }
        throw new java.lang.IllegalArgumentException("Invalid hex char '" + ((int) b) + "'");
    }

    public static boolean isAssetUrl(java.lang.String str) {
        return str != null && str.startsWith(ASSET_BASE);
    }

    public static boolean isResourceUrl(java.lang.String str) {
        return str != null && str.startsWith(RESOURCE_BASE);
    }

    @java.lang.Deprecated
    public static boolean isCookielessProxyUrl(java.lang.String str) {
        return str != null && str.startsWith(PROXY_BASE);
    }

    public static boolean isFileUrl(java.lang.String str) {
        return (str == null || !str.startsWith(FILE_BASE) || str.startsWith(ASSET_BASE) || str.startsWith(PROXY_BASE)) ? false : true;
    }

    public static boolean isAboutUrl(java.lang.String str) {
        return str != null && str.startsWith("about:");
    }

    public static boolean isDataUrl(java.lang.String str) {
        return str != null && str.startsWith("data:");
    }

    public static boolean isJavaScriptUrl(java.lang.String str) {
        return str != null && str.startsWith("javascript:");
    }

    public static boolean isHttpUrl(java.lang.String str) {
        return str != null && str.length() > 6 && str.substring(0, 7).equalsIgnoreCase("http://");
    }

    public static boolean isHttpsUrl(java.lang.String str) {
        return str != null && str.length() > 7 && str.substring(0, 8).equalsIgnoreCase("https://");
    }

    public static boolean isNetworkUrl(java.lang.String str) {
        if (str == null || str.length() == 0) {
            return false;
        }
        return isHttpUrl(str) || isHttpsUrl(str);
    }

    public static boolean isContentUrl(java.lang.String str) {
        return str != null && str.startsWith(CONTENT_BASE);
    }

    public static boolean isValidUrl(java.lang.String str) {
        if (str == null || str.length() == 0) {
            return false;
        }
        return isAssetUrl(str) || isResourceUrl(str) || isFileUrl(str) || isAboutUrl(str) || isHttpUrl(str) || isHttpsUrl(str) || isJavaScriptUrl(str) || isContentUrl(str);
    }

    public static java.lang.String stripAnchor(java.lang.String str) {
        int indexOf = str.indexOf(35);
        if (indexOf != -1) {
            return str.substring(0, indexOf);
        }
        return str;
    }

    public static java.lang.String guessFileName(java.lang.String str, java.lang.String str2, java.lang.String str3) {
        if (android.os.Flags.androidOsBuildVanillaIceCream() && android.compat.Compatibility.isChangeEnabled(PARSE_CONTENT_DISPOSITION_USING_RFC_6266)) {
            return guessFileNameRfc6266(str, str2, str3);
        }
        return guessFileNameRfc2616(str, str2, str3);
    }

    private static java.lang.String guessFileNameRfc2616(java.lang.String str, java.lang.String str2, java.lang.String str3) {
        java.lang.String str4;
        java.lang.String decode;
        int lastIndexOf;
        int lastIndexOf2;
        java.lang.String str5 = null;
        if (str2 == null) {
            str4 = null;
        } else {
            str4 = parseContentDispositionRfc2616(str2);
            if (str4 != null && (lastIndexOf2 = str4.lastIndexOf(47) + 1) > 0) {
                str4 = str4.substring(lastIndexOf2);
            }
        }
        if (str4 == null && (decode = android.net.Uri.decode(str)) != null) {
            int indexOf = decode.indexOf(63);
            if (indexOf > 0) {
                decode = decode.substring(0, indexOf);
            }
            if (!decode.endsWith("/") && (lastIndexOf = decode.lastIndexOf(47) + 1) > 0) {
                str4 = decode.substring(lastIndexOf);
            }
        }
        if (str4 == null) {
            str4 = "downloadfile";
        }
        int indexOf2 = str4.indexOf(46);
        if (indexOf2 < 0) {
            if (str3 != null && (str5 = android.webkit.MimeTypeMap.getSingleton().getExtensionFromMimeType(str3)) != null) {
                str5 = android.media.MediaMetrics.SEPARATOR + str5;
            }
            if (str5 == null) {
                if (str3 != null && str3.toLowerCase(java.util.Locale.ROOT).startsWith("text/")) {
                    if (str3.equalsIgnoreCase("text/html")) {
                        str5 = ".html";
                    } else {
                        str5 = ".txt";
                    }
                } else {
                    str5 = ".bin";
                }
            }
        } else {
            if (str3 != null) {
                java.lang.String mimeTypeFromExtension = android.webkit.MimeTypeMap.getSingleton().getMimeTypeFromExtension(str4.substring(str4.lastIndexOf(46) + 1));
                if (mimeTypeFromExtension != null && !mimeTypeFromExtension.equalsIgnoreCase(str3) && (str5 = android.webkit.MimeTypeMap.getSingleton().getExtensionFromMimeType(str3)) != null) {
                    str5 = android.media.MediaMetrics.SEPARATOR + str5;
                }
            }
            if (str5 == null) {
                str5 = str4.substring(indexOf2);
            }
            str4 = str4.substring(0, indexOf2);
        }
        return str4 + str5;
    }

    private static java.lang.String guessFileNameRfc6266(java.lang.String str, java.lang.String str2, java.lang.String str3) {
        java.lang.String filenameSuggestion = getFilenameSuggestion(str, str2);
        java.lang.String suggestExtensionFromMimeType = suggestExtensionFromMimeType(str3);
        if (filenameSuggestion.indexOf(46) < 0) {
            return filenameSuggestion + suggestExtensionFromMimeType;
        }
        if (str3 != null && extensionDifferentFromMimeType(filenameSuggestion, str3)) {
            return filenameSuggestion + suggestExtensionFromMimeType;
        }
        return filenameSuggestion;
    }

    private static java.lang.String getFilenameSuggestion(java.lang.String str, java.lang.String str2) {
        java.lang.String lastPathSegment;
        java.lang.String filenameFromContentDispositionRfc6266;
        if (str2 != null && (filenameFromContentDispositionRfc6266 = getFilenameFromContentDispositionRfc6266(str2)) != null) {
            return replacePathSeparators(filenameFromContentDispositionRfc6266);
        }
        if (str != null && (lastPathSegment = android.net.Uri.parse(str).getLastPathSegment()) != null) {
            return replacePathSeparators(lastPathSegment);
        }
        return "downloadfile";
    }

    private static java.lang.String replacePathSeparators(java.lang.String str) {
        return str.replaceAll("/", android.telecom.Logging.Session.SESSION_SEPARATION_CHAR_CHILD);
    }

    private static boolean extensionDifferentFromMimeType(java.lang.String str, java.lang.String str2) {
        java.lang.String mimeTypeFromExtension = android.webkit.MimeTypeMap.getSingleton().getMimeTypeFromExtension(str.substring(str.lastIndexOf(46) + 1));
        return (mimeTypeFromExtension == null || mimeTypeFromExtension.equalsIgnoreCase(str2)) ? false : true;
    }

    private static java.lang.String suggestExtensionFromMimeType(java.lang.String str) {
        if (str == null) {
            return ".bin";
        }
        java.lang.String extensionFromMimeType = android.webkit.MimeTypeMap.getSingleton().getExtensionFromMimeType(str);
        if (extensionFromMimeType != null) {
            return android.media.MediaMetrics.SEPARATOR + extensionFromMimeType;
        }
        if (str.equalsIgnoreCase("text/html")) {
            return ".html";
        }
        if (!str.toLowerCase(java.util.Locale.ROOT).startsWith("text/")) {
            return ".bin";
        }
        return ".txt";
    }

    static java.lang.String parseContentDisposition(java.lang.String str) {
        if (android.os.Flags.androidOsBuildVanillaIceCream() && android.compat.Compatibility.isChangeEnabled(PARSE_CONTENT_DISPOSITION_USING_RFC_6266)) {
            return getFilenameFromContentDispositionRfc6266(str);
        }
        return parseContentDispositionRfc2616(str);
    }

    private static java.lang.String parseContentDispositionRfc2616(java.lang.String str) {
        try {
            java.util.regex.Matcher matcher = CONTENT_DISPOSITION_PATTERN.matcher(str);
            if (matcher.find()) {
                return matcher.group(2);
            }
            return null;
        } catch (java.lang.IllegalStateException e) {
            return null;
        }
    }

    private static java.lang.String getFilenameFromContentDispositionRfc6266(java.lang.String str) {
        java.lang.String group;
        java.lang.String[] split = str.trim().split(android.inputmethodservice.navigationbar.NavigationBarInflaterView.GRAVITY_SEPARATOR, 2);
        java.lang.String str2 = null;
        if (split.length < 2 || android.speech.RecognizerResultsIntent.URI_SCHEME_INLINE.equalsIgnoreCase(split[0].trim())) {
            return null;
        }
        java.util.regex.Matcher matcher = DISPOSITION_PATTERN.matcher(split[1]);
        java.lang.String str3 = null;
        while (matcher.find()) {
            java.lang.String group2 = matcher.group(1);
            if (matcher.group(2) != null) {
                group = removeSlashEscapes(matcher.group(2));
            } else if (matcher.group(3) != null) {
                group = removeSlashEscapes(matcher.group(3));
            } else {
                group = matcher.group(4);
            }
            if (group2 != null && group != null) {
                if ("filename*".equalsIgnoreCase(group2)) {
                    str2 = parseExtValueString(group);
                } else if ("filename".equalsIgnoreCase(group2)) {
                    str3 = group;
                }
            }
        }
        if (str2 != null) {
            return str2;
        }
        return str3;
    }

    private static java.lang.String removeSlashEscapes(java.lang.String str) {
        if (str == null) {
            return null;
        }
        return str.replaceAll("\\\\(.)", "$1");
    }

    private static java.lang.String parseExtValueString(java.lang.String str) {
        java.lang.String[] split = str.split("'", 3);
        if (split.length < 3) {
            return null;
        }
        java.lang.String str2 = split[0];
        java.lang.String str3 = split[2];
        try {
            java.nio.charset.Charset forName = java.nio.charset.Charset.forName(str2);
            return java.net.URLDecoder.decode(encodePlusCharacters(str3, forName), forName);
        } catch (java.lang.RuntimeException e) {
            return null;
        }
    }

    private static java.lang.String encodePlusCharacters(java.lang.String str, java.nio.charset.Charset charset) {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        for (byte b : charset.encode("+").array()) {
            sb.append(java.lang.String.format("%02x", java.lang.Byte.valueOf(b)));
        }
        return str.replaceAll("\\+", sb.toString());
    }
}
