package android.content;

/* loaded from: classes.dex */
public interface Attributable {
    void setAttributionSource(android.content.AttributionSource attributionSource);

    static <T extends android.content.Attributable> T setAttributionSource(T t, android.content.AttributionSource attributionSource) {
        if (t != null) {
            t.setAttributionSource(attributionSource);
        }
        return t;
    }

    static <T extends android.content.Attributable> java.util.List<T> setAttributionSource(java.util.List<T> list, android.content.AttributionSource attributionSource) {
        if (list != null) {
            int size = list.size();
            for (int i = 0; i < size; i++) {
                setAttributionSource(list.get(i), attributionSource);
            }
        }
        return list;
    }
}
