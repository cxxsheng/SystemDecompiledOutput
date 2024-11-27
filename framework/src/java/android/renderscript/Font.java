package android.renderscript;

@java.lang.Deprecated
/* loaded from: classes3.dex */
public class Font extends android.renderscript.BaseObj {
    private static java.util.Map<java.lang.String, android.renderscript.Font.FontFamily> sFontFamilyMap;
    private static final java.lang.String[] sSansNames = {android.graphics.Typeface.DEFAULT_FAMILY, "arial", "helvetica", "tahoma", "verdana"};
    private static final java.lang.String[] sSerifNames = {"serif", "times", "times new roman", "palatino", "georgia", "baskerville", "goudy", "fantasy", "cursive", "ITC Stone Serif"};
    private static final java.lang.String[] sMonoNames = {"monospace", "courier", "courier new", "monaco"};

    public enum Style {
        NORMAL,
        BOLD,
        ITALIC,
        BOLD_ITALIC
    }

    static {
        initFontFamilyMap();
    }

    private static class FontFamily {
        java.lang.String mBoldFileName;
        java.lang.String mBoldItalicFileName;
        java.lang.String mItalicFileName;
        java.lang.String[] mNames;
        java.lang.String mNormalFileName;

        private FontFamily() {
        }
    }

    private static void addFamilyToMap(android.renderscript.Font.FontFamily fontFamily) {
        for (int i = 0; i < fontFamily.mNames.length; i++) {
            sFontFamilyMap.put(fontFamily.mNames[i], fontFamily);
        }
    }

    private static void initFontFamilyMap() {
        sFontFamilyMap = new java.util.HashMap();
        android.renderscript.Font.FontFamily fontFamily = new android.renderscript.Font.FontFamily();
        fontFamily.mNames = sSansNames;
        fontFamily.mNormalFileName = "Roboto-Regular.ttf";
        fontFamily.mBoldFileName = "Roboto-Bold.ttf";
        fontFamily.mItalicFileName = "Roboto-Italic.ttf";
        fontFamily.mBoldItalicFileName = "Roboto-BoldItalic.ttf";
        addFamilyToMap(fontFamily);
        android.renderscript.Font.FontFamily fontFamily2 = new android.renderscript.Font.FontFamily();
        fontFamily2.mNames = sSerifNames;
        fontFamily2.mNormalFileName = "NotoSerif-Regular.ttf";
        fontFamily2.mBoldFileName = "NotoSerif-Bold.ttf";
        fontFamily2.mItalicFileName = "NotoSerif-Italic.ttf";
        fontFamily2.mBoldItalicFileName = "NotoSerif-BoldItalic.ttf";
        addFamilyToMap(fontFamily2);
        android.renderscript.Font.FontFamily fontFamily3 = new android.renderscript.Font.FontFamily();
        fontFamily3.mNames = sMonoNames;
        fontFamily3.mNormalFileName = "DroidSansMono.ttf";
        fontFamily3.mBoldFileName = "DroidSansMono.ttf";
        fontFamily3.mItalicFileName = "DroidSansMono.ttf";
        fontFamily3.mBoldItalicFileName = "DroidSansMono.ttf";
        addFamilyToMap(fontFamily3);
    }

    static java.lang.String getFontFileName(java.lang.String str, android.renderscript.Font.Style style) {
        android.renderscript.Font.FontFamily fontFamily = sFontFamilyMap.get(str);
        if (fontFamily != null) {
            switch (style) {
                case NORMAL:
                    return fontFamily.mNormalFileName;
                case BOLD:
                    return fontFamily.mBoldFileName;
                case ITALIC:
                    return fontFamily.mItalicFileName;
                case BOLD_ITALIC:
                    return fontFamily.mBoldItalicFileName;
                default:
                    return "DroidSans.ttf";
            }
        }
        return "DroidSans.ttf";
    }

    Font(long j, android.renderscript.RenderScript renderScript) {
        super(j, renderScript);
        this.guard.open("destroy");
    }

    public static android.renderscript.Font createFromFile(android.renderscript.RenderScript renderScript, android.content.res.Resources resources, java.lang.String str, float f) {
        renderScript.validate();
        long nFontCreateFromFile = renderScript.nFontCreateFromFile(str, f, resources.getDisplayMetrics().densityDpi);
        if (nFontCreateFromFile == 0) {
            throw new android.renderscript.RSRuntimeException("Unable to create font from file " + str);
        }
        return new android.renderscript.Font(nFontCreateFromFile, renderScript);
    }

    public static android.renderscript.Font createFromFile(android.renderscript.RenderScript renderScript, android.content.res.Resources resources, java.io.File file, float f) {
        return createFromFile(renderScript, resources, file.getAbsolutePath(), f);
    }

    public static android.renderscript.Font createFromAsset(android.renderscript.RenderScript renderScript, android.content.res.Resources resources, java.lang.String str, float f) {
        renderScript.validate();
        long nFontCreateFromAsset = renderScript.nFontCreateFromAsset(resources.getAssets(), str, f, resources.getDisplayMetrics().densityDpi);
        if (nFontCreateFromAsset == 0) {
            throw new android.renderscript.RSRuntimeException("Unable to create font from asset " + str);
        }
        return new android.renderscript.Font(nFontCreateFromAsset, renderScript);
    }

    public static android.renderscript.Font createFromResource(android.renderscript.RenderScript renderScript, android.content.res.Resources resources, int i, float f) {
        java.lang.String str = "R." + java.lang.Integer.toString(i);
        renderScript.validate();
        try {
            java.io.InputStream openRawResource = resources.openRawResource(i);
            int i2 = resources.getDisplayMetrics().densityDpi;
            if (openRawResource instanceof android.content.res.AssetManager.AssetInputStream) {
                long nFontCreateFromAssetStream = renderScript.nFontCreateFromAssetStream(str, f, i2, ((android.content.res.AssetManager.AssetInputStream) openRawResource).getNativeAsset());
                if (nFontCreateFromAssetStream == 0) {
                    throw new android.renderscript.RSRuntimeException("Unable to create font from resource " + i);
                }
                return new android.renderscript.Font(nFontCreateFromAssetStream, renderScript);
            }
            throw new android.renderscript.RSRuntimeException("Unsupported asset stream created");
        } catch (java.lang.Exception e) {
            throw new android.renderscript.RSRuntimeException("Unable to open resource " + i);
        }
    }

    public static android.renderscript.Font create(android.renderscript.RenderScript renderScript, android.content.res.Resources resources, java.lang.String str, android.renderscript.Font.Style style, float f) {
        return createFromFile(renderScript, resources, android.os.Environment.getRootDirectory().getAbsolutePath() + "/fonts/" + getFontFileName(str, style), f);
    }
}
