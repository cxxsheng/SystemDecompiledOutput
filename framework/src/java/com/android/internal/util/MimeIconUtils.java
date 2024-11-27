package com.android.internal.util;

/* loaded from: classes5.dex */
public class MimeIconUtils {
    private static final android.util.ArrayMap<java.lang.String, android.content.ContentResolver.MimeTypeInfo> sCache = new android.util.ArrayMap<>();

    private static android.content.ContentResolver.MimeTypeInfo buildTypeInfo(java.lang.String str, int i, int i2, int i3) {
        java.lang.String string;
        android.content.res.Resources system = android.content.res.Resources.getSystem();
        java.lang.String guessExtensionFromMimeType = libcore.content.type.MimeMap.getDefault().guessExtensionFromMimeType(str);
        if (!android.text.TextUtils.isEmpty(guessExtensionFromMimeType) && i3 != -1) {
            string = system.getString(i3, guessExtensionFromMimeType.toUpperCase(java.util.Locale.US));
        } else {
            string = system.getString(i2);
        }
        return new android.content.ContentResolver.MimeTypeInfo(android.graphics.drawable.Icon.createWithResource(system, i), string, string);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private static android.content.ContentResolver.MimeTypeInfo buildTypeInfo(java.lang.String str) {
        char c;
        switch (str.hashCode()) {
            case -2135180893:
                if (str.equals("application/vnd.stardivision.calc")) {
                    c = 'S';
                    break;
                }
                c = 65535;
                break;
            case -2135135086:
                if (str.equals("application/vnd.stardivision.draw")) {
                    c = 'F';
                    break;
                }
                c = 65535;
                break;
            case -2035614749:
                if (str.equals("application/vnd.google-apps.spreadsheet")) {
                    c = 'W';
                    break;
                }
                c = 65535;
                break;
            case -1988437312:
                if (str.equals("application/x-x509-ca-cert")) {
                    c = '\b';
                    break;
                }
                c = 65535;
                break;
            case -1917350260:
                if (str.equals("text/x-literate-haskell")) {
                    c = 28;
                    break;
                }
                c = 65535;
                break;
            case -1883861089:
                if (str.equals("application/rss+xml")) {
                    c = 14;
                    break;
                }
                c = 65535;
                break;
            case -1808693885:
                if (str.equals("text/x-pascal")) {
                    c = 29;
                    break;
                }
                c = 65535;
                break;
            case -1777056778:
                if (str.equals("application/vnd.oasis.opendocument.image")) {
                    c = android.text.format.DateFormat.DAY;
                    break;
                }
                c = 65535;
                break;
            case -1747277413:
                if (str.equals("application/vnd.sun.xml.writer.template")) {
                    c = '`';
                    break;
                }
                c = 65535;
                break;
            case -1719571662:
                if (str.equals("application/vnd.oasis.opendocument.text")) {
                    c = 'X';
                    break;
                }
                c = 65535;
                break;
            case -1628346451:
                if (str.equals("application/vnd.sun.xml.writer")) {
                    c = '^';
                    break;
                }
                c = 65535;
                break;
            case -1590813831:
                if (str.equals("application/vnd.sun.xml.calc.template")) {
                    c = 'U';
                    break;
                }
                c = 65535;
                break;
            case -1506009513:
                if (str.equals("application/vnd.openxmlformats-officedocument.spreadsheetml.template")) {
                    c = android.text.format.DateFormat.HOUR_OF_DAY;
                    break;
                }
                c = 65535;
                break;
            case -1386165903:
                if (str.equals("application/x-kpresenter")) {
                    c = android.telephony.PhoneNumberUtils.WILD;
                    break;
                }
                c = 65535;
                break;
            case -1348236371:
                if (str.equals("application/x-deb")) {
                    c = '9';
                    break;
                }
                c = 65535;
                break;
            case -1348228591:
                if (str.equals("application/x-lha")) {
                    c = '0';
                    break;
                }
                c = 65535;
                break;
            case -1348228026:
                if (str.equals("application/x-lzh")) {
                    c = '1';
                    break;
                }
                c = 65535;
                break;
            case -1348228010:
                if (str.equals("application/x-lzx")) {
                    c = '2';
                    break;
                }
                c = 65535;
                break;
            case -1348221103:
                if (str.equals("application/x-tar")) {
                    c = '4';
                    break;
                }
                c = 65535;
                break;
            case -1326989846:
                if (str.equals("application/x-shockwave-flash")) {
                    c = 'e';
                    break;
                }
                c = 65535;
                break;
            case -1316922187:
                if (str.equals("application/vnd.oasis.opendocument.text-template")) {
                    c = 'Z';
                    break;
                }
                c = 65535;
                break;
            case -1296467268:
                if (str.equals("application/atom+xml")) {
                    c = '\"';
                    break;
                }
                c = 65535;
                break;
            case -1294595255:
                if (str.equals("inode/directory")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case -1248334925:
                if (str.equals("application/pdf")) {
                    c = 'J';
                    break;
                }
                c = 65535;
                break;
            case -1248333084:
                if (str.equals("application/rar")) {
                    c = '*';
                    break;
                }
                c = 65535;
                break;
            case -1248326952:
                if (str.equals("application/xml")) {
                    c = '&';
                    break;
                }
                c = 65535;
                break;
            case -1248325150:
                if (str.equals("application/zip")) {
                    c = '+';
                    break;
                }
                c = 65535;
                break;
            case -1190438973:
                if (str.equals("application/x-pkcs7-signature")) {
                    c = '\f';
                    break;
                }
                c = 65535;
                break;
            case -1143717099:
                if (str.equals("application/x-pkcs7-certreqresp")) {
                    c = 6;
                    break;
                }
                c = 65535;
                break;
            case -1082243251:
                if (str.equals("text/html")) {
                    c = 18;
                    break;
                }
                c = 65535;
                break;
            case -1073633483:
                if (str.equals("application/vnd.openxmlformats-officedocument.presentationml.presentation")) {
                    c = android.text.format.DateFormat.MINUTE;
                    break;
                }
                c = 65535;
                break;
            case -1071817359:
                if (str.equals("application/vnd.ms-powerpoint")) {
                    c = 'l';
                    break;
                }
                c = 65535;
                break;
            case -1050893613:
                if (str.equals("application/vnd.openxmlformats-officedocument.wordprocessingml.document")) {
                    c = 'g';
                    break;
                }
                c = 65535;
                break;
            case -1033484950:
                if (str.equals("application/vnd.sun.xml.draw.template")) {
                    c = 'H';
                    break;
                }
                c = 65535;
                break;
            case -1004747231:
                if (str.equals("text/css")) {
                    c = 17;
                    break;
                }
                c = 65535;
                break;
            case -1004727243:
                if (str.equals("text/xml")) {
                    c = 19;
                    break;
                }
                c = 65535;
                break;
            case -958424608:
                if (str.equals("text/calendar")) {
                    c = '=';
                    break;
                }
                c = 65535;
                break;
            case -951557661:
                if (str.equals("application/vnd.google-apps.presentation")) {
                    c = 'P';
                    break;
                }
                c = 65535;
                break;
            case -779959281:
                if (str.equals("application/vnd.sun.xml.calc")) {
                    c = 'T';
                    break;
                }
                c = 65535;
                break;
            case -779913474:
                if (str.equals("application/vnd.sun.xml.draw")) {
                    c = 'G';
                    break;
                }
                c = 65535;
                break;
            case -723118015:
                if (str.equals("application/x-javascript")) {
                    c = '(';
                    break;
                }
                c = 65535;
                break;
            case -676675015:
                if (str.equals("application/vnd.oasis.opendocument.text-web")) {
                    c = '[';
                    break;
                }
                c = 65535;
                break;
            case -479218428:
                if (str.equals("application/vnd.sun.xml.writer.global")) {
                    c = '_';
                    break;
                }
                c = 65535;
                break;
            case -427343476:
                if (str.equals("application/x-webarchive-xml")) {
                    c = '6';
                    break;
                }
                c = 65535;
                break;
            case -396757341:
                if (str.equals("application/vnd.sun.xml.impress.template")) {
                    c = android.text.format.DateFormat.MONTH;
                    break;
                }
                c = 65535;
                break;
            case -366307023:
                if (str.equals("application/vnd.ms-excel")) {
                    c = 'i';
                    break;
                }
                c = 65535;
                break;
            case -261480694:
                if (str.equals("text/x-chdr")) {
                    c = 22;
                    break;
                }
                c = 65535;
                break;
            case -261469704:
                if (str.equals("text/x-csrc")) {
                    c = 23;
                    break;
                }
                c = 65535;
                break;
            case -261439913:
                if (str.equals("text/x-dsrc")) {
                    c = 24;
                    break;
                }
                c = 65535;
                break;
            case -261278343:
                if (str.equals("text/x-java")) {
                    c = 27;
                    break;
                }
                c = 65535;
                break;
            case -228136375:
                if (str.equals("application/x-pkcs7-mime")) {
                    c = 11;
                    break;
                }
                c = 65535;
                break;
            case -221944004:
                if (str.equals("application/x-font-ttf")) {
                    c = 'B';
                    break;
                }
                c = 65535;
                break;
            case -109382304:
                if (str.equals("application/vnd.oasis.opendocument.spreadsheet-template")) {
                    c = 'R';
                    break;
                }
                c = 65535;
                break;
            case -43923783:
                if (str.equals("application/gzip")) {
                    c = '7';
                    break;
                }
                c = 65535;
                break;
            case -43840953:
                if (str.equals("application/json")) {
                    c = '$';
                    break;
                }
                c = 65535;
                break;
            case 26919318:
                if (str.equals("application/x-iso9660-image")) {
                    c = '/';
                    break;
                }
                c = 65535;
                break;
            case 81142075:
                if (str.equals("application/vnd.android.package-archive")) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case 163679941:
                if (str.equals("application/pgp-signature")) {
                    c = 4;
                    break;
                }
                c = 65535;
                break;
            case 180207563:
                if (str.equals("application/x-stuffit")) {
                    c = '3';
                    break;
                }
                c = 65535;
                break;
            case 245790645:
                if (str.equals("application/vnd.google-apps.drawing")) {
                    c = 'I';
                    break;
                }
                c = 65535;
                break;
            case 262346941:
                if (str.equals("text/x-vcalendar")) {
                    c = '>';
                    break;
                }
                c = 65535;
                break;
            case 302189274:
                if (str.equals(android.provider.DocumentsContract.Document.MIME_TYPE_DIR)) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case 302663708:
                if (str.equals("application/ecmascript")) {
                    c = '#';
                    break;
                }
                c = 65535;
                break;
            case 363965503:
                if (str.equals("application/x-rar-compressed")) {
                    c = com.android.internal.accessibility.common.ShortcutConstants.SERVICES_SEPARATOR;
                    break;
                }
                c = 65535;
                break;
            case 394650567:
                if (str.equals("application/pgp-keys")) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case 428819984:
                if (str.equals("application/vnd.oasis.opendocument.graphics")) {
                    c = 'C';
                    break;
                }
                c = 65535;
                break;
            case 501428239:
                if (str.equals(android.provider.ContactsContract.Contacts.CONTENT_VCARD_TYPE)) {
                    c = ';';
                    break;
                }
                c = 65535;
                break;
            case 571050671:
                if (str.equals("application/vnd.stardivision.writer-global")) {
                    c = ']';
                    break;
                }
                c = 65535;
                break;
            case 603849904:
                if (str.equals(com.google.android.mms.ContentType.APP_XHTML)) {
                    c = 16;
                    break;
                }
                c = 65535;
                break;
            case 641141505:
                if (str.equals("application/x-texinfo")) {
                    c = '!';
                    break;
                }
                c = 65535;
                break;
            case 669516689:
                if (str.equals("application/vnd.stardivision.impress")) {
                    c = 'K';
                    break;
                }
                c = 65535;
                break;
            case 694663701:
                if (str.equals("application/vnd.openxmlformats-officedocument.presentationml.template")) {
                    c = 'n';
                    break;
                }
                c = 65535;
                break;
            case 717553764:
                if (str.equals("application/vnd.google-apps.document")) {
                    c = 'c';
                    break;
                }
                c = 65535;
                break;
            case 822609188:
                if (str.equals("text/vcard")) {
                    c = '<';
                    break;
                }
                c = 65535;
                break;
            case 822849473:
                if (str.equals("text/x-csh")) {
                    c = 25;
                    break;
                }
                c = 65535;
                break;
            case 822865318:
                if (str.equals("text/x-tcl")) {
                    c = 30;
                    break;
                }
                c = 65535;
                break;
            case 822865392:
                if (str.equals("text/x-tex")) {
                    c = 31;
                    break;
                }
                c = 65535;
                break;
            case 859118878:
                if (str.equals("application/x-abiword")) {
                    c = android.text.format.DateFormat.AM_PM;
                    break;
                }
                c = 65535;
                break;
            case 904647503:
                if (str.equals("application/msword")) {
                    c = 'f';
                    break;
                }
                c = 65535;
                break;
            case 1043583697:
                if (str.equals("application/x-pkcs7-certificates")) {
                    c = '\n';
                    break;
                }
                c = 65535;
                break;
            case 1060806194:
                if (str.equals("application/vnd.openxmlformats-officedocument.wordprocessingml.template")) {
                    c = android.text.format.DateFormat.HOUR;
                    break;
                }
                c = 65535;
                break;
            case 1154415139:
                if (str.equals("application/x-font")) {
                    c = '?';
                    break;
                }
                c = 65535;
                break;
            case 1154449330:
                if (str.equals("application/x-gtar")) {
                    c = '.';
                    break;
                }
                c = 65535;
                break;
            case 1239557416:
                if (str.equals("application/x-pkcs7-crl")) {
                    c = 7;
                    break;
                }
                c = 65535;
                break;
            case 1255211837:
                if (str.equals("text/x-haskell")) {
                    c = 26;
                    break;
                }
                c = 65535;
                break;
            case 1283455191:
                if (str.equals("application/x-apple-diskimage")) {
                    c = ',';
                    break;
                }
                c = 65535;
                break;
            case 1305955842:
                if (str.equals("application/x-debian-package")) {
                    c = '-';
                    break;
                }
                c = 65535;
                break;
            case 1377360791:
                if (str.equals("application/vnd.oasis.opendocument.graphics-template")) {
                    c = 'D';
                    break;
                }
                c = 65535;
                break;
            case 1383977381:
                if (str.equals("application/vnd.sun.xml.impress")) {
                    c = android.text.format.DateFormat.STANDALONE_MONTH;
                    break;
                }
                c = 65535;
                break;
            case 1431987873:
                if (str.equals("application/x-kword")) {
                    c = 'b';
                    break;
                }
                c = 65535;
                break;
            case 1432260414:
                if (str.equals("application/x-latex")) {
                    c = ' ';
                    break;
                }
                c = 65535;
                break;
            case 1436962847:
                if (str.equals("application/vnd.oasis.opendocument.presentation")) {
                    c = 'O';
                    break;
                }
                c = 65535;
                break;
            case 1440428940:
                if (str.equals("application/javascript")) {
                    c = '%';
                    break;
                }
                c = 65535;
                break;
            case 1454024983:
                if (str.equals("application/x-7z-compressed")) {
                    c = '8';
                    break;
                }
                c = 65535;
                break;
            case 1461751133:
                if (str.equals("application/vnd.oasis.opendocument.text-master")) {
                    c = 'Y';
                    break;
                }
                c = 65535;
                break;
            case 1502452311:
                if (str.equals("application/font-woff")) {
                    c = '@';
                    break;
                }
                c = 65535;
                break;
            case 1536912403:
                if (str.equals("application/x-object")) {
                    c = 15;
                    break;
                }
                c = 65535;
                break;
            case 1573656544:
                if (str.equals("application/x-pkcs12")) {
                    c = 5;
                    break;
                }
                c = 65535;
                break;
            case 1577426419:
                if (str.equals("application/vnd.openxmlformats-officedocument.presentationml.slideshow")) {
                    c = 'o';
                    break;
                }
                c = 65535;
                break;
            case 1637222218:
                if (str.equals("application/x-kspread")) {
                    c = 'V';
                    break;
                }
                c = 65535;
                break;
            case 1643664935:
                if (str.equals("application/vnd.oasis.opendocument.spreadsheet")) {
                    c = 'Q';
                    break;
                }
                c = 65535;
                break;
            case 1673742401:
                if (str.equals("application/vnd.stardivision.writer")) {
                    c = '\\';
                    break;
                }
                c = 65535;
                break;
            case 1709755138:
                if (str.equals("application/x-font-woff")) {
                    c = android.text.format.DateFormat.CAPITAL_AM_PM;
                    break;
                }
                c = 65535;
                break;
            case 1851895234:
                if (str.equals("application/x-webarchive")) {
                    c = '5';
                    break;
                }
                c = 65535;
                break;
            case 1868769095:
                if (str.equals("application/x-quicktimeplayer")) {
                    c = android.text.format.DateFormat.DATE;
                    break;
                }
                c = 65535;
                break;
            case 1948418893:
                if (str.equals("application/mac-binhex40")) {
                    c = ')';
                    break;
                }
                c = 65535;
                break;
            case 1969663169:
                if (str.equals("application/rdf+xml")) {
                    c = '\r';
                    break;
                }
                c = 65535;
                break;
            case 1993842850:
                if (str.equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")) {
                    c = 'j';
                    break;
                }
                c = 65535;
                break;
            case 2041423923:
                if (str.equals("application/x-x509-user-cert")) {
                    c = '\t';
                    break;
                }
                c = 65535;
                break;
            case 2062084266:
                if (str.equals("text/x-c++hdr")) {
                    c = 20;
                    break;
                }
                c = 65535;
                break;
            case 2062095256:
                if (str.equals("text/x-c++src")) {
                    c = 21;
                    break;
                }
                c = 65535;
                break;
            case 2132236175:
                if (str.equals("text/javascript")) {
                    c = android.text.format.DateFormat.QUOTE;
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        switch (c) {
            case 0:
            case 1:
                return buildTypeInfo(str, com.android.internal.R.drawable.ic_doc_folder, com.android.internal.R.string.mime_type_folder, -1);
            case 2:
                return buildTypeInfo(str, com.android.internal.R.drawable.ic_doc_apk, com.android.internal.R.string.mime_type_apk, -1);
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case '\b':
            case '\t':
            case '\n':
            case 11:
            case '\f':
                return buildTypeInfo(str, com.android.internal.R.drawable.ic_doc_certificate, com.android.internal.R.string.mime_type_generic, com.android.internal.R.string.mime_type_generic_ext);
            case '\r':
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
            case 24:
            case 25:
            case 26:
            case 27:
            case 28:
            case 29:
            case 30:
            case 31:
            case ' ':
            case '!':
            case '\"':
            case '#':
            case '$':
            case '%':
            case '&':
            case '\'':
            case '(':
                return buildTypeInfo(str, com.android.internal.R.drawable.ic_doc_codes, com.android.internal.R.string.mime_type_document, com.android.internal.R.string.mime_type_document_ext);
            case ')':
            case '*':
            case '+':
            case ',':
            case '-':
            case '.':
            case '/':
            case '0':
            case '1':
            case '2':
            case '3':
            case '4':
            case '5':
            case '6':
            case '7':
            case '8':
            case '9':
            case ':':
                return buildTypeInfo(str, com.android.internal.R.drawable.ic_doc_compressed, com.android.internal.R.string.mime_type_compressed, com.android.internal.R.string.mime_type_compressed_ext);
            case ';':
            case '<':
                return buildTypeInfo(str, com.android.internal.R.drawable.ic_doc_contact, com.android.internal.R.string.mime_type_generic, com.android.internal.R.string.mime_type_generic_ext);
            case '=':
            case '>':
                return buildTypeInfo(str, com.android.internal.R.drawable.ic_doc_event, com.android.internal.R.string.mime_type_generic, com.android.internal.R.string.mime_type_generic_ext);
            case '?':
            case '@':
            case 'A':
            case 'B':
                return buildTypeInfo(str, com.android.internal.R.drawable.ic_doc_font, com.android.internal.R.string.mime_type_generic, com.android.internal.R.string.mime_type_generic_ext);
            case 'C':
            case 'D':
            case 'E':
            case 'F':
            case 'G':
            case 'H':
            case 'I':
                return buildTypeInfo(str, com.android.internal.R.drawable.ic_doc_image, com.android.internal.R.string.mime_type_image, com.android.internal.R.string.mime_type_image_ext);
            case 'J':
                return buildTypeInfo(str, com.android.internal.R.drawable.ic_doc_pdf, com.android.internal.R.string.mime_type_document, com.android.internal.R.string.mime_type_document_ext);
            case 'K':
            case 'L':
            case 'M':
            case 'N':
            case 'O':
            case 'P':
                return buildTypeInfo(str, com.android.internal.R.drawable.ic_doc_presentation, com.android.internal.R.string.mime_type_presentation, com.android.internal.R.string.mime_type_presentation_ext);
            case 'Q':
            case 'R':
            case 'S':
            case 'T':
            case 'U':
            case 'V':
            case 'W':
                return buildTypeInfo(str, com.android.internal.R.drawable.ic_doc_spreadsheet, com.android.internal.R.string.mime_type_spreadsheet, com.android.internal.R.string.mime_type_spreadsheet_ext);
            case 'X':
            case 'Y':
            case 'Z':
            case '[':
            case '\\':
            case ']':
            case '^':
            case '_':
            case '`':
            case 'a':
            case 'b':
            case 'c':
                return buildTypeInfo(str, com.android.internal.R.drawable.ic_doc_document, com.android.internal.R.string.mime_type_document, com.android.internal.R.string.mime_type_document_ext);
            case 'd':
            case 'e':
                return buildTypeInfo(str, com.android.internal.R.drawable.ic_doc_video, com.android.internal.R.string.mime_type_video, com.android.internal.R.string.mime_type_video_ext);
            case 'f':
            case 'g':
            case 'h':
                return buildTypeInfo(str, com.android.internal.R.drawable.ic_doc_word, com.android.internal.R.string.mime_type_document, com.android.internal.R.string.mime_type_document_ext);
            case 'i':
            case 'j':
            case 'k':
                return buildTypeInfo(str, com.android.internal.R.drawable.ic_doc_excel, com.android.internal.R.string.mime_type_spreadsheet, com.android.internal.R.string.mime_type_spreadsheet_ext);
            case 'l':
            case 'm':
            case 'n':
            case 'o':
                return buildTypeInfo(str, com.android.internal.R.drawable.ic_doc_powerpoint, com.android.internal.R.string.mime_type_presentation, com.android.internal.R.string.mime_type_presentation_ext);
            default:
                return buildGenericTypeInfo(str);
        }
    }

    private static android.content.ContentResolver.MimeTypeInfo buildGenericTypeInfo(java.lang.String str) {
        if (str.startsWith("audio/")) {
            return buildTypeInfo(str, com.android.internal.R.drawable.ic_doc_audio, com.android.internal.R.string.mime_type_audio, com.android.internal.R.string.mime_type_audio_ext);
        }
        if (str.startsWith("video/")) {
            return buildTypeInfo(str, com.android.internal.R.drawable.ic_doc_video, com.android.internal.R.string.mime_type_video, com.android.internal.R.string.mime_type_video_ext);
        }
        if (str.startsWith(com.android.internal.widget.MessagingMessage.IMAGE_MIME_TYPE_PREFIX)) {
            return buildTypeInfo(str, com.android.internal.R.drawable.ic_doc_image, com.android.internal.R.string.mime_type_image, com.android.internal.R.string.mime_type_image_ext);
        }
        if (str.startsWith("text/")) {
            return buildTypeInfo(str, com.android.internal.R.drawable.ic_doc_text, com.android.internal.R.string.mime_type_document, com.android.internal.R.string.mime_type_document_ext);
        }
        libcore.content.type.MimeMap mimeMap = libcore.content.type.MimeMap.getDefault();
        java.lang.String guessMimeTypeFromExtension = mimeMap.guessMimeTypeFromExtension(mimeMap.guessExtensionFromMimeType(str));
        if (guessMimeTypeFromExtension != null && !java.util.Objects.equals(str, guessMimeTypeFromExtension)) {
            return buildTypeInfo(guessMimeTypeFromExtension);
        }
        return buildTypeInfo(str, com.android.internal.R.drawable.ic_doc_generic, com.android.internal.R.string.mime_type_generic, com.android.internal.R.string.mime_type_generic_ext);
    }

    public static android.content.ContentResolver.MimeTypeInfo getTypeInfo(java.lang.String str) {
        android.content.ContentResolver.MimeTypeInfo mimeTypeInfo;
        java.lang.String lowerCase = str.toLowerCase(java.util.Locale.US);
        synchronized (sCache) {
            mimeTypeInfo = sCache.get(lowerCase);
            if (mimeTypeInfo == null) {
                mimeTypeInfo = buildTypeInfo(lowerCase);
                sCache.put(lowerCase, mimeTypeInfo);
            }
        }
        return mimeTypeInfo;
    }
}
