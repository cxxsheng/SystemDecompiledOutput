package android.content.res;

/* loaded from: classes.dex */
public final class ResourceId {
    public static boolean isValid(int i) {
        return (i == -1 || ((-16777216) & i) == 0 || (i & android.text.Spanned.SPAN_PRIORITY) == 0) ? false : true;
    }
}
