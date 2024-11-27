package android.content.res;

/* loaded from: classes.dex */
public interface FontScaleConverter {
    float convertDpToSp(float f);

    float convertSpToDp(float f);

    static boolean isNonLinearFontScalingActive(float f) {
        return android.content.res.FontScaleConverterFactory.isNonLinearFontScalingActive(f);
    }

    static android.content.res.FontScaleConverter forScale(float f) {
        return android.content.res.FontScaleConverterFactory.forScale(f);
    }
}
