package android.graphics.fonts;

@android.annotation.SystemApi
/* loaded from: classes.dex */
public class FontManager {
    public static final int RESULT_ERROR_DOWNGRADING = -5;
    public static final int RESULT_ERROR_FAILED_TO_OPEN_FONT_FILE = -10001;
    public static final int RESULT_ERROR_FAILED_TO_OPEN_SIGNATURE_FILE = -10002;
    public static final int RESULT_ERROR_FAILED_TO_OPEN_XML_FILE = -10006;
    public static final int RESULT_ERROR_FAILED_TO_WRITE_FONT_FILE = -1;
    public static final int RESULT_ERROR_FAILED_UPDATE_CONFIG = -6;
    public static final int RESULT_ERROR_FONT_NOT_FOUND = -9;
    public static final int RESULT_ERROR_FONT_UPDATER_DISABLED = -7;
    public static final int RESULT_ERROR_INVALID_DEBUG_CERTIFICATE = -10008;
    public static final int RESULT_ERROR_INVALID_FONT_FILE = -3;
    public static final int RESULT_ERROR_INVALID_FONT_NAME = -4;
    public static final int RESULT_ERROR_INVALID_SHELL_ARGUMENT = -10003;
    public static final int RESULT_ERROR_INVALID_SIGNATURE_FILE = -10004;
    public static final int RESULT_ERROR_INVALID_XML = -10007;
    public static final int RESULT_ERROR_SIGNATURE_TOO_LARGE = -10005;
    public static final int RESULT_ERROR_VERIFICATION_FAILURE = -2;
    public static final int RESULT_ERROR_VERSION_MISMATCH = -8;
    public static final int RESULT_SUCCESS = 0;
    private static final java.lang.String TAG = "FontManager";
    private final com.android.internal.graphics.fonts.IFontManager mIFontManager;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface ResultCode {
    }

    private FontManager(com.android.internal.graphics.fonts.IFontManager iFontManager) {
        this.mIFontManager = iFontManager;
    }

    public android.text.FontConfig getFontConfig() {
        try {
            return this.mIFontManager.getFontConfig();
        } catch (android.os.RemoteException e) {
            throw e.rethrowAsRuntimeException();
        }
    }

    public int updateFontFamily(android.graphics.fonts.FontFamilyUpdateRequest fontFamilyUpdateRequest, int i) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        java.util.List<android.graphics.fonts.FontFileUpdateRequest> fontFileUpdateRequests = fontFamilyUpdateRequest.getFontFileUpdateRequests();
        for (int i2 = 0; i2 < fontFileUpdateRequests.size(); i2++) {
            android.graphics.fonts.FontFileUpdateRequest fontFileUpdateRequest = fontFileUpdateRequests.get(i2);
            arrayList.add(new android.graphics.fonts.FontUpdateRequest(fontFileUpdateRequest.getParcelFileDescriptor(), fontFileUpdateRequest.getSignature()));
        }
        java.util.List<android.graphics.fonts.FontFamilyUpdateRequest.FontFamily> fontFamilies = fontFamilyUpdateRequest.getFontFamilies();
        for (int i3 = 0; i3 < fontFamilies.size(); i3++) {
            android.graphics.fonts.FontFamilyUpdateRequest.FontFamily fontFamily = fontFamilies.get(i3);
            arrayList.add(new android.graphics.fonts.FontUpdateRequest(fontFamily.getName(), fontFamily.getFonts()));
        }
        try {
            return this.mIFontManager.updateFontFamily(arrayList, i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public static android.graphics.fonts.FontManager create(com.android.internal.graphics.fonts.IFontManager iFontManager) {
        java.util.Objects.requireNonNull(iFontManager);
        return new android.graphics.fonts.FontManager(iFontManager);
    }
}
