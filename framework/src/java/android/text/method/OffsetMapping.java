package android.text.method;

/* loaded from: classes3.dex */
public interface OffsetMapping {
    public static final int MAP_STRATEGY_CHARACTER = 0;
    public static final int MAP_STRATEGY_CURSOR = 1;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface MapStrategy {
    }

    int originalToTransformed(int i, int i2);

    void originalToTransformed(android.text.method.OffsetMapping.TextUpdate textUpdate);

    int transformedToOriginal(int i, int i2);

    public static class TextUpdate {
        public int after;
        public int before;
        public int where;

        public TextUpdate(int i, int i2, int i3) {
            this.where = i;
            this.before = i2;
            this.after = i3;
        }
    }
}
