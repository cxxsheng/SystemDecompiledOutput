package android.content.res;

/* loaded from: classes.dex */
public final class Configuration implements android.os.Parcelable, java.lang.Comparable<android.content.res.Configuration> {
    public static final int ASSETS_SEQ_UNDEFINED = 0;
    public static final int COLOR_MODE_HDR_MASK = 12;
    public static final int COLOR_MODE_HDR_NO = 4;
    public static final int COLOR_MODE_HDR_SHIFT = 2;
    public static final int COLOR_MODE_HDR_UNDEFINED = 0;
    public static final int COLOR_MODE_HDR_YES = 8;
    public static final int COLOR_MODE_UNDEFINED = 0;
    public static final int COLOR_MODE_WIDE_COLOR_GAMUT_MASK = 3;
    public static final int COLOR_MODE_WIDE_COLOR_GAMUT_NO = 1;
    public static final int COLOR_MODE_WIDE_COLOR_GAMUT_UNDEFINED = 0;
    public static final int COLOR_MODE_WIDE_COLOR_GAMUT_YES = 2;
    public static final int DENSITY_DPI_ANY = 65534;
    public static final int DENSITY_DPI_NONE = 65535;
    public static final int DENSITY_DPI_UNDEFINED = 0;
    public static final int FONT_WEIGHT_ADJUSTMENT_UNDEFINED = Integer.MAX_VALUE;
    public static final int GRAMMATICAL_GENDER_FEMININE = 2;
    public static final int GRAMMATICAL_GENDER_MASCULINE = 3;
    public static final int GRAMMATICAL_GENDER_NEUTRAL = 1;
    public static final int GRAMMATICAL_GENDER_NOT_SPECIFIED = 0;
    public static final int GRAMMATICAL_GENDER_UNDEFINED = -1;
    public static final int HARDKEYBOARDHIDDEN_NO = 1;
    public static final int HARDKEYBOARDHIDDEN_UNDEFINED = 0;
    public static final int HARDKEYBOARDHIDDEN_YES = 2;
    public static final int KEYBOARDHIDDEN_NO = 1;
    public static final int KEYBOARDHIDDEN_SOFT = 3;
    public static final int KEYBOARDHIDDEN_UNDEFINED = 0;
    public static final int KEYBOARDHIDDEN_YES = 2;
    public static final int KEYBOARD_12KEY = 3;
    public static final int KEYBOARD_NOKEYS = 1;
    public static final int KEYBOARD_QWERTY = 2;
    public static final int KEYBOARD_UNDEFINED = 0;
    public static final int MNC_ZERO = 65535;
    public static final int NATIVE_CONFIG_COLOR_MODE = 65536;
    public static final int NATIVE_CONFIG_DENSITY = 256;
    public static final int NATIVE_CONFIG_GRAMMATICAL_GENDER = 131072;
    public static final int NATIVE_CONFIG_KEYBOARD = 16;
    public static final int NATIVE_CONFIG_KEYBOARD_HIDDEN = 32;
    public static final int NATIVE_CONFIG_LAYOUTDIR = 16384;
    public static final int NATIVE_CONFIG_LOCALE = 4;
    public static final int NATIVE_CONFIG_MCC = 1;
    public static final int NATIVE_CONFIG_MNC = 2;
    public static final int NATIVE_CONFIG_NAVIGATION = 64;
    public static final int NATIVE_CONFIG_ORIENTATION = 128;
    public static final int NATIVE_CONFIG_SCREEN_LAYOUT = 2048;
    public static final int NATIVE_CONFIG_SCREEN_SIZE = 512;
    public static final int NATIVE_CONFIG_SMALLEST_SCREEN_SIZE = 8192;
    public static final int NATIVE_CONFIG_TOUCHSCREEN = 8;
    public static final int NATIVE_CONFIG_UI_MODE = 4096;
    public static final int NATIVE_CONFIG_VERSION = 1024;
    public static final int NAVIGATIONHIDDEN_NO = 1;
    public static final int NAVIGATIONHIDDEN_UNDEFINED = 0;
    public static final int NAVIGATIONHIDDEN_YES = 2;
    public static final int NAVIGATION_DPAD = 2;
    public static final int NAVIGATION_NONAV = 1;
    public static final int NAVIGATION_TRACKBALL = 3;
    public static final int NAVIGATION_UNDEFINED = 0;
    public static final int NAVIGATION_WHEEL = 4;
    public static final int ORIENTATION_LANDSCAPE = 2;
    public static final int ORIENTATION_PORTRAIT = 1;

    @java.lang.Deprecated
    public static final int ORIENTATION_SQUARE = 3;
    public static final int ORIENTATION_UNDEFINED = 0;
    public static final int SCREENLAYOUT_COMPAT_NEEDED = 268435456;
    public static final int SCREENLAYOUT_LAYOUTDIR_LTR = 64;
    public static final int SCREENLAYOUT_LAYOUTDIR_MASK = 192;
    public static final int SCREENLAYOUT_LAYOUTDIR_RTL = 128;
    public static final int SCREENLAYOUT_LAYOUTDIR_SHIFT = 6;
    public static final int SCREENLAYOUT_LAYOUTDIR_UNDEFINED = 0;
    public static final int SCREENLAYOUT_LONG_MASK = 48;
    public static final int SCREENLAYOUT_LONG_NO = 16;
    public static final int SCREENLAYOUT_LONG_UNDEFINED = 0;
    public static final int SCREENLAYOUT_LONG_YES = 32;
    public static final int SCREENLAYOUT_ROUND_MASK = 768;
    public static final int SCREENLAYOUT_ROUND_NO = 256;
    public static final int SCREENLAYOUT_ROUND_SHIFT = 8;
    public static final int SCREENLAYOUT_ROUND_UNDEFINED = 0;
    public static final int SCREENLAYOUT_ROUND_YES = 512;
    public static final int SCREENLAYOUT_SIZE_LARGE = 3;
    public static final int SCREENLAYOUT_SIZE_MASK = 15;
    public static final int SCREENLAYOUT_SIZE_NORMAL = 2;
    public static final int SCREENLAYOUT_SIZE_SMALL = 1;
    public static final int SCREENLAYOUT_SIZE_UNDEFINED = 0;
    public static final int SCREENLAYOUT_SIZE_XLARGE = 4;
    public static final int SCREENLAYOUT_UNDEFINED = 0;
    public static final int SCREEN_HEIGHT_DP_UNDEFINED = 0;
    public static final int SCREEN_WIDTH_DP_UNDEFINED = 0;
    public static final int SMALLEST_SCREEN_WIDTH_DP_UNDEFINED = 0;
    private static final java.lang.String TAG = "Configuration";
    public static final int TOUCHSCREEN_FINGER = 3;
    public static final int TOUCHSCREEN_NOTOUCH = 1;

    @java.lang.Deprecated
    public static final int TOUCHSCREEN_STYLUS = 2;
    public static final int TOUCHSCREEN_UNDEFINED = 0;
    public static final int UI_MODE_NIGHT_MASK = 48;
    public static final int UI_MODE_NIGHT_NO = 16;
    public static final int UI_MODE_NIGHT_UNDEFINED = 0;
    public static final int UI_MODE_NIGHT_YES = 32;
    public static final int UI_MODE_TYPE_APPLIANCE = 5;
    public static final int UI_MODE_TYPE_CAR = 3;
    public static final int UI_MODE_TYPE_DESK = 2;
    public static final int UI_MODE_TYPE_MASK = 15;
    public static final int UI_MODE_TYPE_NORMAL = 1;
    public static final int UI_MODE_TYPE_TELEVISION = 4;
    public static final int UI_MODE_TYPE_UNDEFINED = 0;
    public static final int UI_MODE_TYPE_VR_HEADSET = 7;
    public static final int UI_MODE_TYPE_WATCH = 6;
    private static final java.lang.String XML_ATTR_APP_BOUNDS = "app_bounds";
    private static final java.lang.String XML_ATTR_COLOR_MODE = "clrMod";
    private static final java.lang.String XML_ATTR_DENSITY = "density";
    private static final java.lang.String XML_ATTR_FONT_SCALE = "fs";
    private static final java.lang.String XML_ATTR_FONT_WEIGHT_ADJUSTMENT = "fontWeightAdjustment";
    private static final java.lang.String XML_ATTR_GRAMMATICAL_GENDER = "grammaticalGender";
    private static final java.lang.String XML_ATTR_HARD_KEYBOARD_HIDDEN = "hardKeyHid";
    private static final java.lang.String XML_ATTR_KEYBOARD = "key";
    private static final java.lang.String XML_ATTR_KEYBOARD_HIDDEN = "keyHid";
    private static final java.lang.String XML_ATTR_LOCALES = "locales";
    private static final java.lang.String XML_ATTR_MCC = "mcc";
    private static final java.lang.String XML_ATTR_MNC = "mnc";
    private static final java.lang.String XML_ATTR_NAVIGATION = "nav";
    private static final java.lang.String XML_ATTR_NAVIGATION_HIDDEN = "navHid";
    private static final java.lang.String XML_ATTR_ORIENTATION = "ori";
    private static final java.lang.String XML_ATTR_ROTATION = "rot";
    private static final java.lang.String XML_ATTR_SCREEN_HEIGHT = "height";
    private static final java.lang.String XML_ATTR_SCREEN_LAYOUT = "scrLay";
    private static final java.lang.String XML_ATTR_SCREEN_WIDTH = "width";
    private static final java.lang.String XML_ATTR_SMALLEST_WIDTH = "sw";
    private static final java.lang.String XML_ATTR_TOUCHSCREEN = "touch";
    private static final java.lang.String XML_ATTR_UI_MODE = "ui";
    public int assetsSeq;
    public int colorMode;
    public int compatScreenHeightDp;
    public int compatScreenWidthDp;
    public int compatSmallestScreenWidthDp;
    public int densityDpi;
    public float fontScale;
    public int fontWeightAdjustment;
    public int hardKeyboardHidden;
    public int keyboard;
    public int keyboardHidden;

    @java.lang.Deprecated
    public java.util.Locale locale;
    private int mGrammaticalGender;
    private android.os.LocaleList mLocaleList;
    public int mcc;
    public int mnc;
    public int navigation;
    public int navigationHidden;
    public int orientation;
    public int screenHeightDp;
    public int screenLayout;
    public int screenWidthDp;
    public int seq;
    public int smallestScreenWidthDp;
    public int touchscreen;
    public int uiMode;
    public boolean userSetLocale;
    public final android.app.WindowConfiguration windowConfiguration;
    public static final android.content.res.Configuration EMPTY = new android.content.res.Configuration();
    public static final android.os.Parcelable.Creator<android.content.res.Configuration> CREATOR = new android.os.Parcelable.Creator<android.content.res.Configuration>() { // from class: android.content.res.Configuration.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.content.res.Configuration createFromParcel(android.os.Parcel parcel) {
            return new android.content.res.Configuration(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.content.res.Configuration[] newArray(int i) {
            return new android.content.res.Configuration[i];
        }
    };

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface GrammaticalGender {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface NativeConfig {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface Orientation {
    }

    public static int resetScreenLayout(int i) {
        return (i & (-268435520)) | 36;
    }

    public static int reduceScreenLayout(int i, int i2, int i3) {
        int i4;
        boolean z;
        boolean z2 = false;
        int i5 = 1;
        if (i2 < 470) {
            z = false;
        } else {
            if (i2 >= 960 && i3 >= 720) {
                i4 = 4;
            } else if (i2 >= 640 && i3 >= 480) {
                i4 = 3;
            } else {
                i4 = 2;
            }
            if (i3 > 321 || i2 > 570) {
                z = true;
            } else {
                z = false;
            }
            if ((i2 * 3) / 5 >= i3 - 1) {
                z2 = true;
                i5 = i4;
            } else {
                i5 = i4;
            }
        }
        if (!z2) {
            i = (i & (-49)) | 16;
        }
        if (z) {
            i |= 268435456;
        }
        if (i5 < (i & 15)) {
            return (i & (-16)) | i5;
        }
        return i;
    }

    public static java.lang.String configurationDiffToString(int i) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        if ((i & 1) != 0) {
            arrayList.add("CONFIG_MCC");
        }
        if ((i & 2) != 0) {
            arrayList.add("CONFIG_MNC");
        }
        if ((i & 4) != 0) {
            arrayList.add("CONFIG_LOCALE");
        }
        if ((i & 8) != 0) {
            arrayList.add("CONFIG_TOUCHSCREEN");
        }
        if ((i & 16) != 0) {
            arrayList.add("CONFIG_KEYBOARD");
        }
        if ((i & 32) != 0) {
            arrayList.add("CONFIG_KEYBOARD_HIDDEN");
        }
        if ((i & 64) != 0) {
            arrayList.add("CONFIG_NAVIGATION");
        }
        if ((i & 128) != 0) {
            arrayList.add("CONFIG_ORIENTATION");
        }
        if ((i & 256) != 0) {
            arrayList.add("CONFIG_SCREEN_LAYOUT");
        }
        if ((i & 16384) != 0) {
            arrayList.add("CONFIG_COLOR_MODE");
        }
        if ((i & 512) != 0) {
            arrayList.add("CONFIG_UI_MODE");
        }
        if ((i & 1024) != 0) {
            arrayList.add("CONFIG_SCREEN_SIZE");
        }
        if ((i & 2048) != 0) {
            arrayList.add("CONFIG_SMALLEST_SCREEN_SIZE");
        }
        if ((i & 4096) != 0) {
            arrayList.add("CONFIG_DENSITY");
        }
        if ((i & 8192) != 0) {
            arrayList.add("CONFIG_LAYOUT_DIRECTION");
        }
        if ((1073741824 & i) != 0) {
            arrayList.add("CONFIG_FONT_SCALE");
        }
        if ((Integer.MIN_VALUE & i) != 0) {
            arrayList.add("CONFIG_ASSETS_PATHS");
        }
        if ((536870912 & i) != 0) {
            arrayList.add("CONFIG_WINDOW_CONFIGURATION");
        }
        if ((268435456 & i) != 0) {
            arrayList.add("CONFIG_AUTO_BOLD_TEXT");
        }
        if ((i & 32768) != 0) {
            arrayList.add("CONFIG_GRAMMATICAL_GENDER");
        }
        return "{" + android.text.TextUtils.join(", ", arrayList) + "}";
    }

    public boolean isLayoutSizeAtLeast(int i) {
        int i2 = this.screenLayout & 15;
        return i2 != 0 && i2 >= i;
    }

    public Configuration() {
        this.windowConfiguration = new android.app.WindowConfiguration();
        unset();
    }

    public Configuration(android.content.res.Configuration configuration) {
        this.windowConfiguration = new android.app.WindowConfiguration();
        setTo(configuration);
    }

    private void fixUpLocaleList() {
        if ((this.locale == null && !this.mLocaleList.isEmpty()) || (this.locale != null && !this.locale.equals(this.mLocaleList.get(0)))) {
            this.mLocaleList = this.locale == null ? android.os.LocaleList.getEmptyLocaleList() : new android.os.LocaleList(this.locale);
        }
    }

    public void setTo(android.content.res.Configuration configuration) {
        this.fontScale = configuration.fontScale;
        this.mcc = configuration.mcc;
        this.mnc = configuration.mnc;
        if (configuration.locale == null) {
            this.locale = null;
        } else if (!configuration.locale.equals(this.locale)) {
            this.locale = (java.util.Locale) configuration.locale.clone();
        }
        configuration.fixUpLocaleList();
        this.mLocaleList = configuration.mLocaleList;
        this.mGrammaticalGender = configuration.mGrammaticalGender;
        this.userSetLocale = configuration.userSetLocale;
        this.touchscreen = configuration.touchscreen;
        this.keyboard = configuration.keyboard;
        this.keyboardHidden = configuration.keyboardHidden;
        this.hardKeyboardHidden = configuration.hardKeyboardHidden;
        this.navigation = configuration.navigation;
        this.navigationHidden = configuration.navigationHidden;
        this.orientation = configuration.orientation;
        this.screenLayout = configuration.screenLayout;
        this.colorMode = configuration.colorMode;
        this.uiMode = configuration.uiMode;
        this.screenWidthDp = configuration.screenWidthDp;
        this.screenHeightDp = configuration.screenHeightDp;
        this.smallestScreenWidthDp = configuration.smallestScreenWidthDp;
        this.densityDpi = configuration.densityDpi;
        this.compatScreenWidthDp = configuration.compatScreenWidthDp;
        this.compatScreenHeightDp = configuration.compatScreenHeightDp;
        this.compatSmallestScreenWidthDp = configuration.compatSmallestScreenWidthDp;
        this.assetsSeq = configuration.assetsSeq;
        this.seq = configuration.seq;
        this.windowConfiguration.setTo(configuration.windowConfiguration);
        this.fontWeightAdjustment = configuration.fontWeightAdjustment;
    }

    public java.lang.String toString() {
        java.lang.StringBuilder sb = new java.lang.StringBuilder(128);
        sb.append("{");
        sb.append(this.fontScale);
        sb.append(" ");
        if (this.mcc != 0) {
            sb.append(this.mcc);
            sb.append("mcc");
        } else {
            sb.append("?mcc");
        }
        if (this.mnc != 65535) {
            sb.append(this.mnc);
            sb.append("mnc");
        } else {
            sb.append("?mnc");
        }
        fixUpLocaleList();
        if (!this.mLocaleList.isEmpty()) {
            sb.append(" ");
            sb.append(this.mLocaleList);
        } else {
            sb.append(" ?localeList");
        }
        if (this.mGrammaticalGender > 0) {
            switch (this.mGrammaticalGender) {
                case 1:
                    sb.append(" neuter");
                    break;
                case 2:
                    sb.append(" feminine");
                    break;
                case 3:
                    sb.append(" masculine");
                    break;
                default:
                    sb.append(" ?grgend");
                    break;
            }
        }
        int i = this.screenLayout & 192;
        switch (i) {
            case 0:
                sb.append(" ?layoutDir");
                break;
            case 64:
                sb.append(" ldltr");
                break;
            case 128:
                sb.append(" ldrtl");
                break;
            default:
                sb.append(" layoutDir=");
                sb.append(i >> 6);
                break;
        }
        if (this.smallestScreenWidthDp != 0) {
            sb.append(" sw");
            sb.append(this.smallestScreenWidthDp);
            sb.append("dp");
        } else {
            sb.append(" ?swdp");
        }
        if (this.screenWidthDp != 0) {
            sb.append(" w");
            sb.append(this.screenWidthDp);
            sb.append("dp");
        } else {
            sb.append(" ?wdp");
        }
        if (this.screenHeightDp != 0) {
            sb.append(" h");
            sb.append(this.screenHeightDp);
            sb.append("dp");
        } else {
            sb.append(" ?hdp");
        }
        if (this.densityDpi != 0) {
            sb.append(" ");
            sb.append(this.densityDpi);
            sb.append("dpi");
        } else {
            sb.append(" ?density");
        }
        switch (this.screenLayout & 15) {
            case 0:
                sb.append(" ?lsize");
                break;
            case 1:
                sb.append(" smll");
                break;
            case 2:
                sb.append(" nrml");
                break;
            case 3:
                sb.append(" lrg");
                break;
            case 4:
                sb.append(" xlrg");
                break;
            default:
                sb.append(" layoutSize=");
                sb.append(this.screenLayout & 15);
                break;
        }
        switch (this.screenLayout & 48) {
            case 0:
                sb.append(" ?long");
                break;
            case 16:
                break;
            case 32:
                sb.append(" long");
                break;
            default:
                sb.append(" layoutLong=");
                sb.append(this.screenLayout & 48);
                break;
        }
        switch (this.colorMode & 12) {
            case 0:
                sb.append(" ?ldr");
                break;
            case 4:
                break;
            case 8:
                sb.append(" hdr");
                break;
            default:
                sb.append(" dynamicRange=");
                sb.append(this.colorMode & 12);
                break;
        }
        switch (this.colorMode & 3) {
            case 0:
                sb.append(" ?wideColorGamut");
                break;
            case 1:
                break;
            case 2:
                sb.append(" widecg");
                break;
            default:
                sb.append(" wideColorGamut=");
                sb.append(this.colorMode & 3);
                break;
        }
        switch (this.orientation) {
            case 0:
                sb.append(" ?orien");
                break;
            case 1:
                sb.append(" port");
                break;
            case 2:
                sb.append(" land");
                break;
            default:
                sb.append(" orien=");
                sb.append(this.orientation);
                break;
        }
        switch (this.uiMode & 15) {
            case 0:
                sb.append(" ?uimode");
                break;
            case 1:
                break;
            case 2:
                sb.append(" desk");
                break;
            case 3:
                sb.append(" car");
                break;
            case 4:
                sb.append(" television");
                break;
            case 5:
                sb.append(" appliance");
                break;
            case 6:
                sb.append(" watch");
                break;
            case 7:
                sb.append(" vrheadset");
                break;
            default:
                sb.append(" uimode=");
                sb.append(this.uiMode & 15);
                break;
        }
        switch (this.uiMode & 48) {
            case 0:
                sb.append(" ?night");
                break;
            case 16:
                break;
            case 32:
                sb.append(" night");
                break;
            default:
                sb.append(" night=");
                sb.append(this.uiMode & 48);
                break;
        }
        switch (this.touchscreen) {
            case 0:
                sb.append(" ?touch");
                break;
            case 1:
                sb.append(" -touch");
                break;
            case 2:
                sb.append(" stylus");
                break;
            case 3:
                sb.append(" finger");
                break;
            default:
                sb.append(" touch=");
                sb.append(this.touchscreen);
                break;
        }
        switch (this.keyboard) {
            case 0:
                sb.append(" ?keyb");
                break;
            case 1:
                sb.append(" -keyb");
                break;
            case 2:
                sb.append(" qwerty");
                break;
            case 3:
                sb.append(" 12key");
                break;
            default:
                sb.append(" keys=");
                sb.append(this.keyboard);
                break;
        }
        switch (this.keyboardHidden) {
            case 0:
                sb.append("/?");
                break;
            case 1:
                sb.append("/v");
                break;
            case 2:
                sb.append("/h");
                break;
            case 3:
                sb.append("/s");
                break;
            default:
                sb.append("/");
                sb.append(this.keyboardHidden);
                break;
        }
        switch (this.hardKeyboardHidden) {
            case 0:
                sb.append("/?");
                break;
            case 1:
                sb.append("/v");
                break;
            case 2:
                sb.append("/h");
                break;
            default:
                sb.append("/");
                sb.append(this.hardKeyboardHidden);
                break;
        }
        switch (this.navigation) {
            case 0:
                sb.append(" ?nav");
                break;
            case 1:
                sb.append(" -nav");
                break;
            case 2:
                sb.append(" dpad");
                break;
            case 3:
                sb.append(" tball");
                break;
            case 4:
                sb.append(" wheel");
                break;
            default:
                sb.append(" nav=");
                sb.append(this.navigation);
                break;
        }
        switch (this.navigationHidden) {
            case 0:
                sb.append("/?");
                break;
            case 1:
                sb.append("/v");
                break;
            case 2:
                sb.append("/h");
                break;
            default:
                sb.append("/");
                sb.append(this.navigationHidden);
                break;
        }
        sb.append(" winConfig=");
        sb.append(this.windowConfiguration);
        if (this.assetsSeq != 0) {
            sb.append(" as.").append(this.assetsSeq);
        }
        if (this.seq != 0) {
            sb.append(" s.").append(this.seq);
        }
        if (this.fontWeightAdjustment != Integer.MAX_VALUE) {
            sb.append(" fontWeightAdjustment=");
            sb.append(this.fontWeightAdjustment);
        } else {
            sb.append(" ?fontWeightAdjustment");
        }
        sb.append('}');
        return sb.toString();
    }

    public void dumpDebug(android.util.proto.ProtoOutputStream protoOutputStream, long j, boolean z, boolean z2) {
        long start = protoOutputStream.start(j);
        if (!z2) {
            protoOutputStream.write(1108101562369L, this.fontScale);
            protoOutputStream.write(1155346202626L, this.mcc);
            protoOutputStream.write(1155346202627L, this.mnc);
            if (this.mLocaleList != null) {
                protoOutputStream.write(1138166333460L, this.mLocaleList.toLanguageTags());
            }
            protoOutputStream.write(1155346202629L, this.screenLayout);
            protoOutputStream.write(1155346202630L, this.colorMode);
            protoOutputStream.write(1155346202631L, this.touchscreen);
            protoOutputStream.write(1155346202632L, this.keyboard);
            protoOutputStream.write(1155346202633L, this.keyboardHidden);
            protoOutputStream.write(1155346202634L, this.hardKeyboardHidden);
            protoOutputStream.write(1155346202635L, this.navigation);
            protoOutputStream.write(1155346202636L, this.navigationHidden);
            protoOutputStream.write(1155346202638L, this.uiMode);
            protoOutputStream.write(1155346202641L, this.smallestScreenWidthDp);
            protoOutputStream.write(1155346202642L, this.densityDpi);
            if (!z && this.windowConfiguration != null) {
                this.windowConfiguration.dumpDebug(protoOutputStream, 1146756268051L);
            }
            protoOutputStream.write(1155346202645L, this.fontWeightAdjustment);
        }
        protoOutputStream.write(1155346202637L, this.orientation);
        protoOutputStream.write(1155346202639L, this.screenWidthDp);
        protoOutputStream.write(1155346202640L, this.screenHeightDp);
        protoOutputStream.write(1155346202646L, this.mGrammaticalGender);
        protoOutputStream.end(start);
    }

    public void dumpDebug(android.util.proto.ProtoOutputStream protoOutputStream, long j) {
        dumpDebug(protoOutputStream, j, false, false);
    }

    public void dumpDebug(android.util.proto.ProtoOutputStream protoOutputStream, long j, boolean z) {
        dumpDebug(protoOutputStream, j, false, z);
    }

    /* JADX WARN: Removed duplicated region for block: B:103:0x0385  */
    /* JADX WARN: Removed duplicated region for block: B:88:0x02c3 A[Catch: IllformedLocaleException -> 0x02ec, all -> 0x032e, TryCatch #5 {IllformedLocaleException -> 0x02ec, blocks: (B:86:0x02a3, B:88:0x02c3, B:93:0x02e8), top: B:85:0x02a3, outer: #8 }] */
    /* JADX WARN: Removed duplicated region for block: B:93:0x02e8 A[Catch: IllformedLocaleException -> 0x02ec, all -> 0x032e, TRY_LEAVE, TryCatch #5 {IllformedLocaleException -> 0x02ec, blocks: (B:86:0x02a3, B:88:0x02c3, B:93:0x02e8), top: B:85:0x02a3, outer: #8 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void readFromProto(android.util.proto.ProtoInputStream protoInputStream, long j) throws java.io.IOException {
        long j2;
        long j3;
        java.lang.String str;
        java.lang.String str2;
        java.lang.String str3;
        java.lang.String str4;
        java.lang.String str5;
        java.lang.Throwable th;
        int indexOf;
        java.lang.String str6;
        java.lang.String str7;
        java.lang.String str8;
        java.lang.String str9;
        java.lang.String str10;
        java.lang.String str11;
        android.content.res.Configuration configuration = this;
        long start = protoInputStream.start(j);
        java.util.ArrayList arrayList = new java.util.ArrayList();
        while (true) {
            try {
                if (protoInputStream.nextField() == -1) {
                    long j4 = start;
                    if (arrayList.size() > 0) {
                        configuration.setLocales(new android.os.LocaleList((java.util.Locale[]) arrayList.toArray(new java.util.Locale[arrayList.size()])));
                    }
                    protoInputStream.end(j4);
                    return;
                }
                try {
                    int fieldNumber = protoInputStream.getFieldNumber();
                    java.lang.String str12 = TAG;
                    switch (fieldNumber) {
                        case 1:
                            j3 = start;
                            configuration.fontScale = protoInputStream.readFloat(1108101562369L);
                            start = j3;
                        case 2:
                            j3 = start;
                            configuration.mcc = protoInputStream.readInt(1155346202626L);
                            start = j3;
                        case 3:
                            try {
                                j3 = start;
                                configuration = this;
                                configuration.mnc = protoInputStream.readInt(1155346202627L);
                                start = j3;
                            } catch (java.lang.Throwable th2) {
                                th = th2;
                                configuration = this;
                                j2 = j3;
                                if (arrayList.size() > 0) {
                                }
                                protoInputStream.end(j2);
                                throw th;
                            }
                            break;
                        case 4:
                            try {
                                long start2 = protoInputStream.start(2246267895812L);
                                java.lang.String str13 = "";
                                java.lang.String str14 = str13;
                                java.lang.String str15 = str14;
                                java.lang.String str16 = str15;
                                for (int i = -1; protoInputStream.nextField() != i; i = -1) {
                                    try {
                                        try {
                                            switch (protoInputStream.getFieldNumber()) {
                                                case 1:
                                                    str11 = str12;
                                                    str13 = protoInputStream.readString(1138166333441L);
                                                    str12 = str11;
                                                case 2:
                                                    str11 = str12;
                                                    str14 = protoInputStream.readString(1138166333442L);
                                                    str12 = str11;
                                                case 3:
                                                    str11 = str12;
                                                    str15 = protoInputStream.readString(1138166333443L);
                                                    str12 = str11;
                                                case 4:
                                                    str11 = str12;
                                                    try {
                                                        str16 = protoInputStream.readString(1138166333444L);
                                                        str12 = str11;
                                                    } catch (android.util.proto.WireTypeMismatchException e) {
                                                        e = e;
                                                        str = str13;
                                                        str4 = str14;
                                                        str2 = str16;
                                                        j3 = start;
                                                        str5 = str15;
                                                        str3 = str11;
                                                        try {
                                                            throw e;
                                                        } catch (java.lang.Throwable th3) {
                                                            th = th3;
                                                            th = th;
                                                            protoInputStream.end(start2);
                                                            try {
                                                                java.util.Locale build = new java.util.Locale.Builder().setLanguage(str).setRegion(str4).setVariant(str5).setScript(str2).build();
                                                                indexOf = arrayList.indexOf(build);
                                                                if (indexOf == -1) {
                                                                    android.util.Slog.wtf(str3, "Repeated locale (" + arrayList.get(indexOf) + ") found when trying to add: " + build.toString());
                                                                } else {
                                                                    arrayList.add(build);
                                                                }
                                                                throw th;
                                                            } catch (java.util.IllformedLocaleException e2) {
                                                                android.util.Slog.e(str3, "readFromProto error building locale with: language-" + str + ";country-" + str4 + ";variant-" + str5 + ";script-" + str2);
                                                                throw th;
                                                            }
                                                        }
                                                    } catch (java.lang.Throwable th4) {
                                                        th = th4;
                                                        str = str13;
                                                        str4 = str14;
                                                        str2 = str16;
                                                        j3 = start;
                                                        str5 = str15;
                                                        str3 = str11;
                                                        th = th;
                                                        protoInputStream.end(start2);
                                                        java.util.Locale build2 = new java.util.Locale.Builder().setLanguage(str).setRegion(str4).setVariant(str5).setScript(str2).build();
                                                        indexOf = arrayList.indexOf(build2);
                                                        if (indexOf == -1) {
                                                        }
                                                        throw th;
                                                    }
                                                default:
                                                    str11 = str12;
                                                    str12 = str11;
                                            }
                                        } catch (android.util.proto.WireTypeMismatchException e3) {
                                            e = e3;
                                            str11 = str12;
                                        } catch (java.lang.Throwable th5) {
                                            th = th5;
                                            str11 = str12;
                                        }
                                    } catch (android.util.proto.WireTypeMismatchException e4) {
                                        e = e4;
                                        str = str13;
                                        str2 = str16;
                                        long j5 = start;
                                        str3 = str12;
                                        str4 = str14;
                                        str5 = str15;
                                        j3 = j5;
                                    } catch (java.lang.Throwable th6) {
                                        th = th6;
                                        str = str13;
                                        str2 = str16;
                                        long j6 = start;
                                        str3 = str12;
                                        str4 = str14;
                                        str5 = str15;
                                        j3 = j6;
                                    }
                                }
                                java.lang.String str17 = str12;
                                protoInputStream.end(start2);
                                try {
                                    str6 = str13;
                                    try {
                                        str7 = str14;
                                        try {
                                            str8 = str15;
                                        } catch (java.util.IllformedLocaleException e5) {
                                            j3 = start;
                                            str8 = str15;
                                        }
                                    } catch (java.util.IllformedLocaleException e6) {
                                        str7 = str14;
                                        str8 = str15;
                                        str9 = str16;
                                        j3 = start;
                                        str10 = str17;
                                        android.util.Slog.e(str10, "readFromProto error building locale with: language-" + str6 + ";country-" + str7 + ";variant-" + str8 + ";script-" + str9);
                                        start = j3;
                                    }
                                } catch (java.util.IllformedLocaleException e7) {
                                    str6 = str13;
                                }
                                try {
                                    str9 = str16;
                                    try {
                                        java.util.Locale build3 = new java.util.Locale.Builder().setLanguage(str6).setRegion(str7).setVariant(str8).setScript(str9).build();
                                        j3 = start;
                                        try {
                                            try {
                                                int indexOf2 = arrayList.indexOf(build3);
                                                if (indexOf2 != -1) {
                                                    java.lang.String str18 = "Repeated locale (" + arrayList.get(indexOf2) + ") found when trying to add: " + build3.toString();
                                                    str10 = str17;
                                                    try {
                                                        android.util.Slog.wtf(str10, str18);
                                                    } catch (java.util.IllformedLocaleException e8) {
                                                        android.util.Slog.e(str10, "readFromProto error building locale with: language-" + str6 + ";country-" + str7 + ";variant-" + str8 + ";script-" + str9);
                                                        start = j3;
                                                    }
                                                } else {
                                                    arrayList.add(build3);
                                                }
                                            } catch (java.lang.Throwable th7) {
                                                th = th7;
                                                j2 = j3;
                                                if (arrayList.size() > 0) {
                                                    configuration.setLocales(new android.os.LocaleList((java.util.Locale[]) arrayList.toArray(new java.util.Locale[arrayList.size()])));
                                                }
                                                protoInputStream.end(j2);
                                                throw th;
                                            }
                                        } catch (java.util.IllformedLocaleException e9) {
                                            str10 = str17;
                                            android.util.Slog.e(str10, "readFromProto error building locale with: language-" + str6 + ";country-" + str7 + ";variant-" + str8 + ";script-" + str9);
                                            start = j3;
                                        }
                                    } catch (java.util.IllformedLocaleException e10) {
                                        j3 = start;
                                        str10 = str17;
                                        android.util.Slog.e(str10, "readFromProto error building locale with: language-" + str6 + ";country-" + str7 + ";variant-" + str8 + ";script-" + str9);
                                        start = j3;
                                    }
                                } catch (java.util.IllformedLocaleException e11) {
                                    j3 = start;
                                    str9 = str16;
                                    str10 = str17;
                                    android.util.Slog.e(str10, "readFromProto error building locale with: language-" + str6 + ";country-" + str7 + ";variant-" + str8 + ";script-" + str9);
                                    start = j3;
                                }
                                start = j3;
                            } catch (java.lang.Throwable th8) {
                                th = th8;
                                j3 = start;
                                configuration = this;
                                j2 = j3;
                                if (arrayList.size() > 0) {
                                }
                                protoInputStream.end(j2);
                                throw th;
                            }
                        case 5:
                            configuration.screenLayout = protoInputStream.readInt(1155346202629L);
                            j3 = start;
                            start = j3;
                        case 6:
                            configuration.colorMode = protoInputStream.readInt(1155346202630L);
                            j3 = start;
                            start = j3;
                        case 7:
                            configuration.touchscreen = protoInputStream.readInt(1155346202631L);
                            j3 = start;
                            start = j3;
                        case 8:
                            configuration.keyboard = protoInputStream.readInt(1155346202632L);
                            j3 = start;
                            start = j3;
                        case 9:
                            configuration.keyboardHidden = protoInputStream.readInt(1155346202633L);
                            j3 = start;
                            start = j3;
                        case 10:
                            configuration.hardKeyboardHidden = protoInputStream.readInt(1155346202634L);
                            j3 = start;
                            start = j3;
                        case 11:
                            configuration.navigation = protoInputStream.readInt(1155346202635L);
                            j3 = start;
                            start = j3;
                        case 12:
                            configuration.navigationHidden = protoInputStream.readInt(1155346202636L);
                            j3 = start;
                            start = j3;
                        case 13:
                            configuration.orientation = protoInputStream.readInt(1155346202637L);
                            j3 = start;
                            start = j3;
                        case 14:
                            configuration.uiMode = protoInputStream.readInt(1155346202638L);
                            j3 = start;
                            start = j3;
                        case 15:
                            configuration.screenWidthDp = protoInputStream.readInt(1155346202639L);
                            j3 = start;
                            start = j3;
                        case 16:
                            configuration.screenHeightDp = protoInputStream.readInt(1155346202640L);
                            j3 = start;
                            start = j3;
                        case 17:
                            configuration.smallestScreenWidthDp = protoInputStream.readInt(1155346202641L);
                            j3 = start;
                            start = j3;
                        case 18:
                            configuration.densityDpi = protoInputStream.readInt(1155346202642L);
                            j3 = start;
                            start = j3;
                        case 19:
                            configuration.windowConfiguration.readFromProto(protoInputStream, 1146756268051L);
                            j3 = start;
                            start = j3;
                        case 20:
                            try {
                                configuration.setLocales(android.os.LocaleList.forLanguageTags(protoInputStream.readString(1138166333460L)));
                                j3 = start;
                            } catch (java.lang.Exception e12) {
                                android.util.Slog.e(TAG, "error parsing locale list in configuration.", e12);
                                j3 = start;
                            }
                            start = j3;
                        case 21:
                            configuration.fontWeightAdjustment = protoInputStream.readInt(1155346202645L);
                            j3 = start;
                            start = j3;
                        case 22:
                            configuration.mGrammaticalGender = protoInputStream.readInt(1155346202646L);
                            j3 = start;
                            start = j3;
                        default:
                            j3 = start;
                            start = j3;
                    }
                } catch (java.lang.Throwable th9) {
                    th = th9;
                    j3 = start;
                }
            } catch (java.lang.Throwable th10) {
                th = th10;
                j2 = start;
                if (arrayList.size() > 0) {
                }
                protoInputStream.end(j2);
                throw th;
            }
        }
    }

    public void writeResConfigToProto(android.util.proto.ProtoOutputStream protoOutputStream, long j, android.util.DisplayMetrics displayMetrics) {
        int i;
        int i2;
        if (displayMetrics.widthPixels >= displayMetrics.heightPixels) {
            i = displayMetrics.widthPixels;
            i2 = displayMetrics.heightPixels;
        } else {
            i = displayMetrics.heightPixels;
            i2 = displayMetrics.widthPixels;
        }
        long start = protoOutputStream.start(j);
        dumpDebug(protoOutputStream, 1146756268033L);
        protoOutputStream.write(1155346202626L, android.os.Build.VERSION.RESOURCES_SDK_INT);
        protoOutputStream.write(1155346202627L, i);
        protoOutputStream.write(1155346202628L, i2);
        protoOutputStream.end(start);
    }

    public static java.lang.String uiModeToString(int i) {
        switch (i) {
            case 0:
                return "UI_MODE_TYPE_UNDEFINED";
            case 1:
                return "UI_MODE_TYPE_NORMAL";
            case 2:
                return "UI_MODE_TYPE_DESK";
            case 3:
                return "UI_MODE_TYPE_CAR";
            case 4:
                return "UI_MODE_TYPE_TELEVISION";
            case 5:
                return "UI_MODE_TYPE_APPLIANCE";
            case 6:
                return "UI_MODE_TYPE_WATCH";
            case 7:
                return "UI_MODE_TYPE_VR_HEADSET";
            default:
                return java.lang.Integer.toString(i);
        }
    }

    public void setToDefaults() {
        this.fontScale = 1.0f;
        this.mnc = 0;
        this.mcc = 0;
        this.mLocaleList = android.os.LocaleList.getEmptyLocaleList();
        this.locale = null;
        this.userSetLocale = false;
        this.touchscreen = 0;
        this.keyboard = 0;
        this.keyboardHidden = 0;
        this.hardKeyboardHidden = 0;
        this.navigation = 0;
        this.navigationHidden = 0;
        this.orientation = 0;
        this.screenLayout = 0;
        this.colorMode = 0;
        this.uiMode = 0;
        this.compatScreenWidthDp = 0;
        this.screenWidthDp = 0;
        this.compatScreenHeightDp = 0;
        this.screenHeightDp = 0;
        this.compatSmallestScreenWidthDp = 0;
        this.smallestScreenWidthDp = 0;
        this.densityDpi = 0;
        this.assetsSeq = 0;
        this.seq = 0;
        this.windowConfiguration.setToDefaults();
        this.fontWeightAdjustment = Integer.MAX_VALUE;
        this.mGrammaticalGender = -1;
    }

    public void unset() {
        setToDefaults();
        this.fontScale = 0.0f;
    }

    @java.lang.Deprecated
    public void makeDefault() {
        setToDefaults();
    }

    public int updateFrom(android.content.res.Configuration configuration) {
        int i;
        if (configuration.fontScale > 0.0f && this.fontScale != configuration.fontScale) {
            this.fontScale = configuration.fontScale;
            i = 1073741824;
        } else {
            i = 0;
        }
        if (configuration.mcc != 0 && this.mcc != configuration.mcc) {
            i |= 1;
            this.mcc = configuration.mcc;
        }
        if (configuration.mnc != 0 && this.mnc != configuration.mnc) {
            i |= 2;
            this.mnc = configuration.mnc;
        }
        fixUpLocaleList();
        configuration.fixUpLocaleList();
        if (!configuration.mLocaleList.isEmpty() && !this.mLocaleList.equals(configuration.mLocaleList)) {
            i |= 4;
            this.mLocaleList = configuration.mLocaleList;
            if (!configuration.locale.equals(this.locale)) {
                this.locale = (java.util.Locale) configuration.locale.clone();
                i |= 8192;
                setLayoutDirection(this.locale);
            }
        }
        int i2 = configuration.screenLayout & 192;
        if (i2 != 0 && i2 != (this.screenLayout & 192)) {
            this.screenLayout = i2 | (this.screenLayout & (-193));
            i |= 8192;
        }
        if (configuration.userSetLocale && (!this.userSetLocale || (i & 4) != 0)) {
            i |= 4;
            this.userSetLocale = true;
        }
        if (configuration.touchscreen != 0 && this.touchscreen != configuration.touchscreen) {
            i |= 8;
            this.touchscreen = configuration.touchscreen;
        }
        if (configuration.keyboard != 0 && this.keyboard != configuration.keyboard) {
            i |= 16;
            this.keyboard = configuration.keyboard;
        }
        if (configuration.keyboardHidden != 0 && this.keyboardHidden != configuration.keyboardHidden) {
            i |= 32;
            this.keyboardHidden = configuration.keyboardHidden;
        }
        if (configuration.hardKeyboardHidden != 0 && this.hardKeyboardHidden != configuration.hardKeyboardHidden) {
            i |= 32;
            this.hardKeyboardHidden = configuration.hardKeyboardHidden;
        }
        if (configuration.navigation != 0 && this.navigation != configuration.navigation) {
            i |= 64;
            this.navigation = configuration.navigation;
        }
        if (configuration.navigationHidden != 0 && this.navigationHidden != configuration.navigationHidden) {
            i |= 32;
            this.navigationHidden = configuration.navigationHidden;
        }
        if (configuration.orientation != 0 && this.orientation != configuration.orientation) {
            i |= 128;
            this.orientation = configuration.orientation;
        }
        if ((configuration.screenLayout & 15) != 0 && (configuration.screenLayout & 15) != (this.screenLayout & 15)) {
            i |= 256;
            this.screenLayout = (this.screenLayout & (-16)) | (configuration.screenLayout & 15);
        }
        if ((configuration.screenLayout & 48) != 0 && (configuration.screenLayout & 48) != (this.screenLayout & 48)) {
            i |= 256;
            this.screenLayout = (this.screenLayout & (-49)) | (configuration.screenLayout & 48);
        }
        if ((configuration.screenLayout & 768) != 0 && (configuration.screenLayout & 768) != (this.screenLayout & 768)) {
            i |= 256;
            this.screenLayout = (this.screenLayout & (-769)) | (configuration.screenLayout & 768);
        }
        if ((configuration.screenLayout & 268435456) != (this.screenLayout & 268435456) && configuration.screenLayout != 0) {
            i |= 256;
            this.screenLayout = (this.screenLayout & (-268435457)) | (configuration.screenLayout & 268435456);
        }
        if ((configuration.colorMode & 3) != 0 && (configuration.colorMode & 3) != (this.colorMode & 3)) {
            i |= 16384;
            this.colorMode = (this.colorMode & (-4)) | (configuration.colorMode & 3);
        }
        if ((configuration.colorMode & 12) != 0 && (configuration.colorMode & 12) != (this.colorMode & 12)) {
            i |= 16384;
            this.colorMode = (this.colorMode & (-13)) | (configuration.colorMode & 12);
        }
        if (configuration.uiMode != 0 && this.uiMode != configuration.uiMode) {
            i |= 512;
            if ((configuration.uiMode & 15) != 0) {
                this.uiMode = (this.uiMode & (-16)) | (configuration.uiMode & 15);
            }
            if ((configuration.uiMode & 48) != 0) {
                this.uiMode = (this.uiMode & (-49)) | (configuration.uiMode & 48);
            }
        }
        if (configuration.screenWidthDp != 0 && this.screenWidthDp != configuration.screenWidthDp) {
            i |= 1024;
            this.screenWidthDp = configuration.screenWidthDp;
        }
        if (configuration.screenHeightDp != 0 && this.screenHeightDp != configuration.screenHeightDp) {
            i |= 1024;
            this.screenHeightDp = configuration.screenHeightDp;
        }
        if (configuration.smallestScreenWidthDp != 0 && this.smallestScreenWidthDp != configuration.smallestScreenWidthDp) {
            i |= 2048;
            this.smallestScreenWidthDp = configuration.smallestScreenWidthDp;
        }
        if (configuration.densityDpi != 0 && this.densityDpi != configuration.densityDpi) {
            i |= 4096;
            this.densityDpi = configuration.densityDpi;
        }
        if (configuration.compatScreenWidthDp != 0) {
            this.compatScreenWidthDp = configuration.compatScreenWidthDp;
        }
        if (configuration.compatScreenHeightDp != 0) {
            this.compatScreenHeightDp = configuration.compatScreenHeightDp;
        }
        if (configuration.compatSmallestScreenWidthDp != 0) {
            this.compatSmallestScreenWidthDp = configuration.compatSmallestScreenWidthDp;
        }
        if (configuration.assetsSeq != 0 && configuration.assetsSeq != this.assetsSeq) {
            i |= Integer.MIN_VALUE;
            this.assetsSeq = configuration.assetsSeq;
        }
        if (configuration.seq != 0) {
            this.seq = configuration.seq;
        }
        if (this.windowConfiguration.updateFrom(configuration.windowConfiguration) != 0) {
            i |= 536870912;
        }
        if (configuration.fontWeightAdjustment != Integer.MAX_VALUE && configuration.fontWeightAdjustment != this.fontWeightAdjustment) {
            i |= 268435456;
            this.fontWeightAdjustment = configuration.fontWeightAdjustment;
        }
        if (configuration.mGrammaticalGender != -1 && configuration.mGrammaticalGender != this.mGrammaticalGender) {
            int i3 = i | 32768;
            this.mGrammaticalGender = configuration.mGrammaticalGender;
            return i3;
        }
        return i;
    }

    public void setTo(android.content.res.Configuration configuration, int i, int i2) {
        if ((1073741824 & i) != 0) {
            this.fontScale = configuration.fontScale;
        }
        if ((i & 1) != 0) {
            this.mcc = configuration.mcc;
        }
        if ((i & 2) != 0) {
            this.mnc = configuration.mnc;
        }
        int i3 = i & 4;
        if (i3 != 0) {
            this.mLocaleList = configuration.mLocaleList;
            if (!this.mLocaleList.isEmpty() && !configuration.locale.equals(this.locale)) {
                this.locale = (java.util.Locale) configuration.locale.clone();
            }
        }
        if ((i & 8192) != 0) {
            this.screenLayout = (configuration.screenLayout & 192) | (this.screenLayout & (-193));
        }
        if (i3 != 0) {
            this.userSetLocale = configuration.userSetLocale;
        }
        if ((i & 8) != 0) {
            this.touchscreen = configuration.touchscreen;
        }
        if ((i & 16) != 0) {
            this.keyboard = configuration.keyboard;
        }
        if ((i & 32) != 0) {
            this.keyboardHidden = configuration.keyboardHidden;
            this.hardKeyboardHidden = configuration.hardKeyboardHidden;
            this.navigationHidden = configuration.navigationHidden;
        }
        if ((i & 64) != 0) {
            this.navigation = configuration.navigation;
        }
        if ((i & 128) != 0) {
            this.orientation = configuration.orientation;
        }
        if ((i & 256) != 0) {
            this.screenLayout |= configuration.screenLayout & (-193);
        }
        if ((i & 16384) != 0) {
            this.colorMode = configuration.colorMode;
        }
        if ((i & 512) != 0) {
            this.uiMode = configuration.uiMode;
        }
        if ((i & 1024) != 0) {
            this.screenWidthDp = configuration.screenWidthDp;
            this.screenHeightDp = configuration.screenHeightDp;
        }
        if ((i & 2048) != 0) {
            this.smallestScreenWidthDp = configuration.smallestScreenWidthDp;
        }
        if ((i & 4096) != 0) {
            this.densityDpi = configuration.densityDpi;
        }
        if ((Integer.MIN_VALUE & i) != 0) {
            this.assetsSeq = configuration.assetsSeq;
        }
        if ((536870912 & i) != 0) {
            this.windowConfiguration.setTo(configuration.windowConfiguration, i2);
        }
        if ((268435456 & i) != 0) {
            this.fontWeightAdjustment = configuration.fontWeightAdjustment;
        }
        if ((i & 32768) != 0) {
            this.mGrammaticalGender = configuration.mGrammaticalGender;
        }
    }

    public int diff(android.content.res.Configuration configuration) {
        return diff(configuration, false, false);
    }

    public int diffPublicOnly(android.content.res.Configuration configuration) {
        return diff(configuration, false, true);
    }

    public int diff(android.content.res.Configuration configuration, boolean z, boolean z2) {
        int i;
        if ((z || configuration.fontScale > 0.0f) && this.fontScale != configuration.fontScale) {
            i = 1073741824;
        } else {
            i = 0;
        }
        if ((z || configuration.mcc != 0) && this.mcc != configuration.mcc) {
            i |= 1;
        }
        if ((z || configuration.mnc != 0) && this.mnc != configuration.mnc) {
            i |= 2;
        }
        fixUpLocaleList();
        configuration.fixUpLocaleList();
        if ((z || !configuration.mLocaleList.isEmpty()) && !this.mLocaleList.equals(configuration.mLocaleList)) {
            i = i | 4 | 8192;
        }
        int i2 = configuration.screenLayout & 192;
        if ((z || i2 != 0) && i2 != (this.screenLayout & 192)) {
            i |= 8192;
        }
        if ((z || configuration.touchscreen != 0) && this.touchscreen != configuration.touchscreen) {
            i |= 8;
        }
        if ((z || configuration.keyboard != 0) && this.keyboard != configuration.keyboard) {
            i |= 16;
        }
        if ((z || configuration.keyboardHidden != 0) && this.keyboardHidden != configuration.keyboardHidden) {
            i |= 32;
        }
        if ((z || configuration.hardKeyboardHidden != 0) && this.hardKeyboardHidden != configuration.hardKeyboardHidden) {
            i |= 32;
        }
        if ((z || configuration.navigation != 0) && this.navigation != configuration.navigation) {
            i |= 64;
        }
        if ((z || configuration.navigationHidden != 0) && this.navigationHidden != configuration.navigationHidden) {
            i |= 32;
        }
        if ((z || configuration.orientation != 0) && this.orientation != configuration.orientation) {
            i |= 128;
        }
        if ((z || getScreenLayoutNoDirection(configuration.screenLayout) != 0) && getScreenLayoutNoDirection(this.screenLayout) != getScreenLayoutNoDirection(configuration.screenLayout)) {
            i |= 256;
        }
        if ((z || (configuration.colorMode & 12) != 0) && (this.colorMode & 12) != (configuration.colorMode & 12)) {
            i |= 16384;
        }
        if ((z || (configuration.colorMode & 3) != 0) && (this.colorMode & 3) != (configuration.colorMode & 3)) {
            i |= 16384;
        }
        if ((z || configuration.uiMode != 0) && this.uiMode != configuration.uiMode) {
            i |= 512;
        }
        if ((z || configuration.screenWidthDp != 0) && this.screenWidthDp != configuration.screenWidthDp) {
            i |= 1024;
        }
        if ((z || configuration.screenHeightDp != 0) && this.screenHeightDp != configuration.screenHeightDp) {
            i |= 1024;
        }
        if ((z || configuration.smallestScreenWidthDp != 0) && this.smallestScreenWidthDp != configuration.smallestScreenWidthDp) {
            i |= 2048;
        }
        if ((z || configuration.densityDpi != 0) && this.densityDpi != configuration.densityDpi) {
            i |= 4096;
        }
        if ((z || configuration.assetsSeq != 0) && this.assetsSeq != configuration.assetsSeq) {
            i |= Integer.MIN_VALUE;
        }
        if (!z2 && this.windowConfiguration.diff(configuration.windowConfiguration, z) != 0) {
            i |= 536870912;
        }
        if ((z || configuration.fontWeightAdjustment != Integer.MAX_VALUE) && this.fontWeightAdjustment != configuration.fontWeightAdjustment) {
            i |= 268435456;
        }
        if ((z || configuration.mGrammaticalGender != -1) && this.mGrammaticalGender != configuration.mGrammaticalGender) {
            return i | 32768;
        }
        return i;
    }

    public static boolean needNewResources(int i, int i2) {
        return (i & ((i2 | Integer.MIN_VALUE) | 1073741824)) != 0;
    }

    public boolean isOtherSeqNewer(android.content.res.Configuration configuration) {
        if (configuration == null) {
            return false;
        }
        if (configuration.seq == 0 || this.seq == 0) {
            return true;
        }
        int i = configuration.seq - this.seq;
        return java.lang.Math.abs(i) > 268435456 ? i < 0 : i > 0;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeFloat(this.fontScale);
        parcel.writeInt(this.mcc);
        parcel.writeInt(this.mnc);
        fixUpLocaleList();
        parcel.writeTypedObject(this.mLocaleList, i);
        if (this.userSetLocale) {
            parcel.writeInt(1);
        } else {
            parcel.writeInt(0);
        }
        parcel.writeInt(this.touchscreen);
        parcel.writeInt(this.keyboard);
        parcel.writeInt(this.keyboardHidden);
        parcel.writeInt(this.hardKeyboardHidden);
        parcel.writeInt(this.navigation);
        parcel.writeInt(this.navigationHidden);
        parcel.writeInt(this.orientation);
        parcel.writeInt(this.screenLayout);
        parcel.writeInt(this.colorMode);
        parcel.writeInt(this.uiMode);
        parcel.writeInt(this.screenWidthDp);
        parcel.writeInt(this.screenHeightDp);
        parcel.writeInt(this.smallestScreenWidthDp);
        parcel.writeInt(this.densityDpi);
        parcel.writeInt(this.compatScreenWidthDp);
        parcel.writeInt(this.compatScreenHeightDp);
        parcel.writeInt(this.compatSmallestScreenWidthDp);
        this.windowConfiguration.writeToParcel(parcel, i);
        parcel.writeInt(this.assetsSeq);
        parcel.writeInt(this.seq);
        parcel.writeInt(this.fontWeightAdjustment);
        parcel.writeInt(this.mGrammaticalGender);
    }

    public void readFromParcel(android.os.Parcel parcel) {
        this.fontScale = parcel.readFloat();
        this.mcc = parcel.readInt();
        this.mnc = parcel.readInt();
        this.mLocaleList = (android.os.LocaleList) parcel.readTypedObject(android.os.LocaleList.CREATOR);
        this.locale = this.mLocaleList.get(0);
        this.userSetLocale = parcel.readInt() == 1;
        this.touchscreen = parcel.readInt();
        this.keyboard = parcel.readInt();
        this.keyboardHidden = parcel.readInt();
        this.hardKeyboardHidden = parcel.readInt();
        this.navigation = parcel.readInt();
        this.navigationHidden = parcel.readInt();
        this.orientation = parcel.readInt();
        this.screenLayout = parcel.readInt();
        this.colorMode = parcel.readInt();
        this.uiMode = parcel.readInt();
        this.screenWidthDp = parcel.readInt();
        this.screenHeightDp = parcel.readInt();
        this.smallestScreenWidthDp = parcel.readInt();
        this.densityDpi = parcel.readInt();
        this.compatScreenWidthDp = parcel.readInt();
        this.compatScreenHeightDp = parcel.readInt();
        this.compatSmallestScreenWidthDp = parcel.readInt();
        this.windowConfiguration.readFromParcel(parcel);
        this.assetsSeq = parcel.readInt();
        this.seq = parcel.readInt();
        this.fontWeightAdjustment = parcel.readInt();
        this.mGrammaticalGender = parcel.readInt();
    }

    private Configuration(android.os.Parcel parcel) {
        this.windowConfiguration = new android.app.WindowConfiguration();
        readFromParcel(parcel);
    }

    public boolean isNightModeActive() {
        return (this.uiMode & 48) == 32;
    }

    @Override // java.lang.Comparable
    public int compareTo(android.content.res.Configuration configuration) {
        float f = this.fontScale;
        float f2 = configuration.fontScale;
        if (f < f2) {
            return -1;
        }
        if (f > f2) {
            return 1;
        }
        int i = this.mcc - configuration.mcc;
        if (i != 0) {
            return i;
        }
        int i2 = this.mnc - configuration.mnc;
        if (i2 != 0) {
            return i2;
        }
        fixUpLocaleList();
        configuration.fixUpLocaleList();
        if (this.mLocaleList.isEmpty()) {
            if (!configuration.mLocaleList.isEmpty()) {
                return 1;
            }
        } else {
            if (configuration.mLocaleList.isEmpty()) {
                return -1;
            }
            int min = java.lang.Math.min(this.mLocaleList.size(), configuration.mLocaleList.size());
            for (int i3 = 0; i3 < min; i3++) {
                java.util.Locale locale = this.mLocaleList.get(i3);
                java.util.Locale locale2 = configuration.mLocaleList.get(i3);
                int compareTo = locale.getLanguage().compareTo(locale2.getLanguage());
                if (compareTo != 0) {
                    return compareTo;
                }
                int compareTo2 = locale.getCountry().compareTo(locale2.getCountry());
                if (compareTo2 != 0) {
                    return compareTo2;
                }
                int compareTo3 = locale.getVariant().compareTo(locale2.getVariant());
                if (compareTo3 != 0) {
                    return compareTo3;
                }
                int compareTo4 = locale.toLanguageTag().compareTo(locale2.toLanguageTag());
                if (compareTo4 != 0) {
                    return compareTo4;
                }
            }
            int size = this.mLocaleList.size() - configuration.mLocaleList.size();
            if (size != 0) {
                return size;
            }
        }
        int i4 = this.mGrammaticalGender - configuration.mGrammaticalGender;
        if (i4 != 0) {
            return i4;
        }
        int i5 = this.touchscreen - configuration.touchscreen;
        if (i5 != 0) {
            return i5;
        }
        int i6 = this.keyboard - configuration.keyboard;
        if (i6 != 0) {
            return i6;
        }
        int i7 = this.keyboardHidden - configuration.keyboardHidden;
        if (i7 != 0) {
            return i7;
        }
        int i8 = this.hardKeyboardHidden - configuration.hardKeyboardHidden;
        if (i8 != 0) {
            return i8;
        }
        int i9 = this.navigation - configuration.navigation;
        if (i9 != 0) {
            return i9;
        }
        int i10 = this.navigationHidden - configuration.navigationHidden;
        if (i10 != 0) {
            return i10;
        }
        int i11 = this.orientation - configuration.orientation;
        if (i11 != 0) {
            return i11;
        }
        int i12 = this.colorMode - configuration.colorMode;
        if (i12 != 0) {
            return i12;
        }
        int i13 = this.screenLayout - configuration.screenLayout;
        if (i13 != 0) {
            return i13;
        }
        int i14 = this.uiMode - configuration.uiMode;
        if (i14 != 0) {
            return i14;
        }
        int i15 = this.screenWidthDp - configuration.screenWidthDp;
        if (i15 != 0) {
            return i15;
        }
        int i16 = this.screenHeightDp - configuration.screenHeightDp;
        if (i16 != 0) {
            return i16;
        }
        int i17 = this.smallestScreenWidthDp - configuration.smallestScreenWidthDp;
        if (i17 != 0) {
            return i17;
        }
        int i18 = this.densityDpi - configuration.densityDpi;
        if (i18 != 0) {
            return i18;
        }
        int i19 = this.assetsSeq - configuration.assetsSeq;
        if (i19 != 0) {
            return i19;
        }
        int compareTo5 = this.windowConfiguration.compareTo(configuration.windowConfiguration);
        return compareTo5 != 0 ? compareTo5 : this.fontWeightAdjustment - configuration.fontWeightAdjustment;
    }

    public boolean equals(android.content.res.Configuration configuration) {
        if (configuration == null) {
            return false;
        }
        if (configuration != this && compareTo(configuration) != 0) {
            return false;
        }
        return true;
    }

    public boolean equals(java.lang.Object obj) {
        try {
            return equals((android.content.res.Configuration) obj);
        } catch (java.lang.ClassCastException e) {
            return false;
        }
    }

    public int hashCode() {
        return ((((((((((((((((((((((((((((((((((((((((527 + java.lang.Float.floatToIntBits(this.fontScale)) * 31) + this.mcc) * 31) + this.mnc) * 31) + this.mLocaleList.hashCode()) * 31) + this.touchscreen) * 31) + this.keyboard) * 31) + this.keyboardHidden) * 31) + this.hardKeyboardHidden) * 31) + this.navigation) * 31) + this.navigationHidden) * 31) + this.orientation) * 31) + this.screenLayout) * 31) + this.colorMode) * 31) + this.uiMode) * 31) + this.screenWidthDp) * 31) + this.screenHeightDp) * 31) + this.smallestScreenWidthDp) * 31) + this.densityDpi) * 31) + this.assetsSeq) * 31) + this.fontWeightAdjustment) * 31) + this.mGrammaticalGender;
    }

    public int getGrammaticalGender() {
        if (this.mGrammaticalGender == -1) {
            return 0;
        }
        return this.mGrammaticalGender;
    }

    public int getGrammaticalGenderRaw() {
        return this.mGrammaticalGender;
    }

    public void setGrammaticalGender(int i) {
        this.mGrammaticalGender = i;
    }

    public android.os.LocaleList getLocales() {
        fixUpLocaleList();
        return this.mLocaleList;
    }

    public void setLocales(android.os.LocaleList localeList) {
        if (localeList == null) {
            localeList = android.os.LocaleList.getEmptyLocaleList();
        }
        this.mLocaleList = localeList;
        this.locale = this.mLocaleList.get(0);
        setLayoutDirection(this.locale);
    }

    public void setLocale(java.util.Locale locale) {
        setLocales(locale == null ? android.os.LocaleList.getEmptyLocaleList() : new android.os.LocaleList(locale));
    }

    public void clearLocales() {
        this.mLocaleList = android.os.LocaleList.getEmptyLocaleList();
        this.locale = null;
    }

    public int getLayoutDirection() {
        return (this.screenLayout & 192) == 128 ? 1 : 0;
    }

    public void setLayoutDirection(java.util.Locale locale) {
        this.screenLayout = ((android.text.TextUtils.getLayoutDirectionFromLocale(locale) + 1) << 6) | (this.screenLayout & (-193));
    }

    private static int getScreenLayoutNoDirection(int i) {
        return i & (-193);
    }

    public boolean isScreenRound() {
        return (this.screenLayout & 768) == 512;
    }

    public boolean isScreenWideColorGamut() {
        return (this.colorMode & 3) == 2;
    }

    public boolean isScreenHdr() {
        return (this.colorMode & 12) == 8;
    }

    public static java.lang.String localesToResourceQualifier(android.os.LocaleList localeList) {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        for (int i = 0; i < localeList.size(); i++) {
            java.util.Locale locale = localeList.get(i);
            int length = locale.getLanguage().length();
            if (length != 0) {
                int length2 = locale.getScript().length();
                int length3 = locale.getCountry().length();
                int length4 = locale.getVariant().length();
                if (sb.length() != 0) {
                    sb.append(",");
                }
                if (length == 2 && length2 == 0 && ((length3 == 0 || length3 == 2) && length4 == 0)) {
                    sb.append(locale.getLanguage());
                    if (length3 == 2) {
                        sb.append("-r").append(locale.getCountry());
                    }
                } else {
                    sb.append("b+");
                    sb.append(locale.getLanguage());
                    if (length2 != 0) {
                        sb.append("+");
                        sb.append(locale.getScript());
                    }
                    if (length3 != 0) {
                        sb.append("+");
                        sb.append(locale.getCountry());
                    }
                    if (length4 != 0) {
                        sb.append("+");
                        sb.append(locale.getVariant());
                    }
                }
            }
        }
        return sb.toString();
    }

    public static java.lang.String resourceQualifierString(android.content.res.Configuration configuration) {
        return resourceQualifierString(configuration, null);
    }

    public static java.lang.String resourceQualifierString(android.content.res.Configuration configuration, android.util.DisplayMetrics displayMetrics) {
        int i;
        int i2;
        java.util.ArrayList arrayList = new java.util.ArrayList();
        if (configuration.mcc != 0) {
            arrayList.add("mcc" + configuration.mcc);
            if (configuration.mnc != 0) {
                arrayList.add("mnc" + configuration.mnc);
            }
        }
        if (!configuration.mLocaleList.isEmpty()) {
            java.lang.String localesToResourceQualifier = localesToResourceQualifier(configuration.mLocaleList);
            if (!localesToResourceQualifier.isEmpty()) {
                arrayList.add(localesToResourceQualifier);
            }
        }
        switch (configuration.mGrammaticalGender) {
            case 1:
                arrayList.add("neuter");
                break;
            case 2:
                arrayList.add("feminine");
                break;
            case 3:
                arrayList.add("masculine");
                break;
        }
        switch (configuration.screenLayout & 192) {
            case 64:
                arrayList.add("ldltr");
                break;
            case 128:
                arrayList.add("ldrtl");
                break;
        }
        if (configuration.smallestScreenWidthDp != 0) {
            arrayList.add(XML_ATTR_SMALLEST_WIDTH + configuration.smallestScreenWidthDp + "dp");
        }
        if (configuration.screenWidthDp != 0) {
            arrayList.add("w" + configuration.screenWidthDp + "dp");
        }
        if (configuration.screenHeightDp != 0) {
            arrayList.add("h" + configuration.screenHeightDp + "dp");
        }
        switch (configuration.screenLayout & 15) {
            case 1:
                arrayList.add("small");
                break;
            case 2:
                arrayList.add(android.graphics.FontListParser.STYLE_NORMAL);
                break;
            case 3:
                arrayList.add(android.app.slice.Slice.HINT_LARGE);
                break;
            case 4:
                arrayList.add("xlarge");
                break;
        }
        switch (configuration.screenLayout & 48) {
            case 16:
                arrayList.add("notlong");
                break;
            case 32:
                arrayList.add(android.app.slice.SliceItem.FORMAT_LONG);
                break;
        }
        switch (configuration.screenLayout & 768) {
            case 256:
                arrayList.add("notround");
                break;
            case 512:
                arrayList.add("round");
                break;
        }
        switch (configuration.colorMode & 3) {
            case 1:
                arrayList.add("nowidecg");
                break;
            case 2:
                arrayList.add("widecg");
                break;
        }
        switch (configuration.colorMode & 12) {
            case 4:
                arrayList.add("lowdr");
                break;
            case 8:
                arrayList.add("highdr");
                break;
        }
        switch (configuration.orientation) {
            case 1:
                arrayList.add("port");
                break;
            case 2:
                arrayList.add("land");
                break;
        }
        java.lang.String uiModeTypeString = getUiModeTypeString(configuration.uiMode & 15);
        if (uiModeTypeString != null) {
            arrayList.add(uiModeTypeString);
        }
        switch (configuration.uiMode & 48) {
            case 16:
                arrayList.add("notnight");
                break;
            case 32:
                arrayList.add(android.hardware.Camera.Parameters.SCENE_MODE_NIGHT);
                break;
        }
        switch (configuration.densityDpi) {
            case 0:
                break;
            case 120:
                arrayList.add("ldpi");
                break;
            case 160:
                arrayList.add("mdpi");
                break;
            case 213:
                arrayList.add("tvdpi");
                break;
            case 240:
                arrayList.add("hdpi");
                break;
            case 320:
                arrayList.add("xhdpi");
                break;
            case 480:
                arrayList.add("xxhdpi");
                break;
            case 640:
                arrayList.add("xxxhdpi");
                break;
            case DENSITY_DPI_ANY /* 65534 */:
                arrayList.add("anydpi");
                break;
            case 65535:
                arrayList.add("nodpi");
                break;
            default:
                arrayList.add(configuration.densityDpi + "dpi");
                break;
        }
        switch (configuration.touchscreen) {
            case 1:
                arrayList.add("notouch");
                break;
            case 3:
                arrayList.add("finger");
                break;
        }
        switch (configuration.keyboardHidden) {
            case 1:
                arrayList.add("keysexposed");
                break;
            case 2:
                arrayList.add("keyshidden");
                break;
            case 3:
                arrayList.add("keyssoft");
                break;
        }
        switch (configuration.keyboard) {
            case 1:
                arrayList.add("nokeys");
                break;
            case 2:
                arrayList.add("qwerty");
                break;
            case 3:
                arrayList.add("12key");
                break;
        }
        switch (configuration.navigationHidden) {
            case 1:
                arrayList.add("navexposed");
                break;
            case 2:
                arrayList.add("navhidden");
                break;
        }
        switch (configuration.navigation) {
            case 1:
                arrayList.add("nonav");
                break;
            case 2:
                arrayList.add("dpad");
                break;
            case 3:
                arrayList.add("trackball");
                break;
            case 4:
                arrayList.add("wheel");
                break;
        }
        if (displayMetrics != null) {
            if (displayMetrics.widthPixels >= displayMetrics.heightPixels) {
                i = displayMetrics.widthPixels;
                i2 = displayMetrics.heightPixels;
            } else {
                i = displayMetrics.heightPixels;
                i2 = displayMetrics.widthPixels;
            }
            arrayList.add(i + "x" + i2);
        }
        arrayList.add("v" + android.os.Build.VERSION.RESOURCES_SDK_INT);
        return android.text.TextUtils.join(com.android.internal.content.NativeLibraryHelper.CLEAR_ABI_OVERRIDE, arrayList);
    }

    public static java.lang.String getUiModeTypeString(int i) {
        switch (i) {
            case 2:
                return "desk";
            case 3:
                return "car";
            case 4:
                return "television";
            case 5:
                return "appliance";
            case 6:
                return "watch";
            case 7:
                return "vrheadset";
            default:
                return null;
        }
    }

    public static android.content.res.Configuration generateDelta(android.content.res.Configuration configuration, android.content.res.Configuration configuration2) {
        android.content.res.Configuration configuration3 = new android.content.res.Configuration();
        if (configuration.fontScale != configuration2.fontScale) {
            configuration3.fontScale = configuration2.fontScale;
        }
        if (configuration.mcc != configuration2.mcc) {
            configuration3.mcc = configuration2.mcc;
        }
        if (configuration.mnc != configuration2.mnc) {
            configuration3.mnc = configuration2.mnc;
        }
        configuration.fixUpLocaleList();
        configuration2.fixUpLocaleList();
        if (!configuration.mLocaleList.equals(configuration2.mLocaleList)) {
            configuration3.mLocaleList = configuration2.mLocaleList;
            configuration3.locale = configuration2.locale;
        }
        if (configuration.mGrammaticalGender != configuration2.mGrammaticalGender) {
            configuration3.mGrammaticalGender = configuration2.mGrammaticalGender;
        }
        if (configuration.touchscreen != configuration2.touchscreen) {
            configuration3.touchscreen = configuration2.touchscreen;
        }
        if (configuration.keyboard != configuration2.keyboard) {
            configuration3.keyboard = configuration2.keyboard;
        }
        if (configuration.keyboardHidden != configuration2.keyboardHidden) {
            configuration3.keyboardHidden = configuration2.keyboardHidden;
        }
        if (configuration.navigation != configuration2.navigation) {
            configuration3.navigation = configuration2.navigation;
        }
        if (configuration.navigationHidden != configuration2.navigationHidden) {
            configuration3.navigationHidden = configuration2.navigationHidden;
        }
        if (configuration.orientation != configuration2.orientation) {
            configuration3.orientation = configuration2.orientation;
        }
        if ((configuration.screenLayout & 15) != (configuration2.screenLayout & 15)) {
            configuration3.screenLayout |= configuration2.screenLayout & 15;
        }
        if ((configuration.screenLayout & 192) != (configuration2.screenLayout & 192)) {
            configuration3.screenLayout |= configuration2.screenLayout & 192;
        }
        if ((configuration.screenLayout & 48) != (configuration2.screenLayout & 48)) {
            configuration3.screenLayout |= configuration2.screenLayout & 48;
        }
        if ((configuration.screenLayout & 768) != (configuration2.screenLayout & 768)) {
            configuration3.screenLayout |= configuration2.screenLayout & 768;
        }
        if ((configuration.colorMode & 3) != (configuration2.colorMode & 3)) {
            configuration3.colorMode |= configuration2.colorMode & 3;
        }
        if ((configuration.colorMode & 12) != (configuration2.colorMode & 12)) {
            configuration3.colorMode |= configuration2.colorMode & 12;
        }
        if ((configuration.uiMode & 15) != (configuration2.uiMode & 15)) {
            configuration3.uiMode |= configuration2.uiMode & 15;
        }
        if ((configuration.uiMode & 48) != (configuration2.uiMode & 48)) {
            configuration3.uiMode |= configuration2.uiMode & 48;
        }
        if (configuration.screenWidthDp != configuration2.screenWidthDp) {
            configuration3.screenWidthDp = configuration2.screenWidthDp;
        }
        if (configuration.screenHeightDp != configuration2.screenHeightDp) {
            configuration3.screenHeightDp = configuration2.screenHeightDp;
        }
        if (configuration.smallestScreenWidthDp != configuration2.smallestScreenWidthDp) {
            configuration3.smallestScreenWidthDp = configuration2.smallestScreenWidthDp;
        }
        if (configuration.densityDpi != configuration2.densityDpi) {
            configuration3.densityDpi = configuration2.densityDpi;
        }
        if (configuration.assetsSeq != configuration2.assetsSeq) {
            configuration3.assetsSeq = configuration2.assetsSeq;
        }
        if (!configuration.windowConfiguration.equals(configuration2.windowConfiguration)) {
            configuration3.windowConfiguration.setTo(configuration2.windowConfiguration);
        }
        if (configuration.fontWeightAdjustment != configuration2.fontWeightAdjustment) {
            configuration3.fontWeightAdjustment = configuration2.fontWeightAdjustment;
        }
        return configuration3;
    }

    public static void readXmlAttrs(org.xmlpull.v1.XmlPullParser xmlPullParser, android.content.res.Configuration configuration) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        configuration.fontScale = java.lang.Float.intBitsToFloat(com.android.internal.util.XmlUtils.readIntAttribute(xmlPullParser, XML_ATTR_FONT_SCALE, 0));
        configuration.mcc = com.android.internal.util.XmlUtils.readIntAttribute(xmlPullParser, "mcc", 0);
        configuration.mnc = com.android.internal.util.XmlUtils.readIntAttribute(xmlPullParser, "mnc", 0);
        configuration.mLocaleList = android.os.LocaleList.forLanguageTags(com.android.internal.util.XmlUtils.readStringAttribute(xmlPullParser, XML_ATTR_LOCALES));
        configuration.locale = configuration.mLocaleList.get(0);
        configuration.touchscreen = com.android.internal.util.XmlUtils.readIntAttribute(xmlPullParser, XML_ATTR_TOUCHSCREEN, 0);
        configuration.keyboard = com.android.internal.util.XmlUtils.readIntAttribute(xmlPullParser, "key", 0);
        configuration.keyboardHidden = com.android.internal.util.XmlUtils.readIntAttribute(xmlPullParser, XML_ATTR_KEYBOARD_HIDDEN, 0);
        configuration.hardKeyboardHidden = com.android.internal.util.XmlUtils.readIntAttribute(xmlPullParser, XML_ATTR_HARD_KEYBOARD_HIDDEN, 0);
        configuration.navigation = com.android.internal.util.XmlUtils.readIntAttribute(xmlPullParser, XML_ATTR_NAVIGATION, 0);
        configuration.navigationHidden = com.android.internal.util.XmlUtils.readIntAttribute(xmlPullParser, XML_ATTR_NAVIGATION_HIDDEN, 0);
        configuration.orientation = com.android.internal.util.XmlUtils.readIntAttribute(xmlPullParser, XML_ATTR_ORIENTATION, 0);
        configuration.screenLayout = com.android.internal.util.XmlUtils.readIntAttribute(xmlPullParser, XML_ATTR_SCREEN_LAYOUT, 0);
        configuration.colorMode = com.android.internal.util.XmlUtils.readIntAttribute(xmlPullParser, XML_ATTR_COLOR_MODE, 0);
        configuration.uiMode = com.android.internal.util.XmlUtils.readIntAttribute(xmlPullParser, XML_ATTR_UI_MODE, 0);
        configuration.screenWidthDp = com.android.internal.util.XmlUtils.readIntAttribute(xmlPullParser, "width", 0);
        configuration.screenHeightDp = com.android.internal.util.XmlUtils.readIntAttribute(xmlPullParser, "height", 0);
        configuration.smallestScreenWidthDp = com.android.internal.util.XmlUtils.readIntAttribute(xmlPullParser, XML_ATTR_SMALLEST_WIDTH, 0);
        configuration.densityDpi = com.android.internal.util.XmlUtils.readIntAttribute(xmlPullParser, XML_ATTR_DENSITY, 0);
        configuration.fontWeightAdjustment = com.android.internal.util.XmlUtils.readIntAttribute(xmlPullParser, XML_ATTR_FONT_WEIGHT_ADJUSTMENT, Integer.MAX_VALUE);
        configuration.mGrammaticalGender = com.android.internal.util.XmlUtils.readIntAttribute(xmlPullParser, XML_ATTR_GRAMMATICAL_GENDER, -1);
    }
}
