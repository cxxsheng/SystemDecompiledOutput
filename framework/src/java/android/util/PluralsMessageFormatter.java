package android.util;

/* loaded from: classes3.dex */
public class PluralsMessageFormatter {
    public static java.lang.String format(android.content.res.Resources resources, java.util.Map<java.lang.String, java.lang.Object> map, int i) {
        return new android.icu.text.MessageFormat(resources.getString(i)).format(map);
    }
}
