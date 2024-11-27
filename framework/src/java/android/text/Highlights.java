package android.text;

/* loaded from: classes3.dex */
public class Highlights {
    private final java.util.List<android.util.Pair<android.graphics.Paint, int[]>> mHighlights;

    private Highlights(java.util.List<android.util.Pair<android.graphics.Paint, int[]>> list) {
        this.mHighlights = list;
    }

    public int getSize() {
        return this.mHighlights.size();
    }

    public android.graphics.Paint getPaint(int i) {
        return this.mHighlights.get(i).first;
    }

    public int[] getRanges(int i) {
        return this.mHighlights.get(i).second;
    }

    public static final class Builder {
        private final java.util.List<android.util.Pair<android.graphics.Paint, int[]>> mHighlights = new java.util.ArrayList();

        public android.text.Highlights.Builder addRange(android.graphics.Paint paint, int i, int i2) {
            if (i > i2) {
                throw new java.lang.IllegalArgumentException("start must not be larger than end: " + i + ", " + i2);
            }
            java.util.Objects.requireNonNull(paint);
            this.mHighlights.add(new android.util.Pair<>(paint, new int[]{i, i2}));
            return this;
        }

        public android.text.Highlights.Builder addRanges(android.graphics.Paint paint, int... iArr) {
            if (iArr.length % 2 == 1) {
                throw new java.lang.IllegalArgumentException("Flatten ranges must have even numbered elements");
            }
            for (int i = 0; i < iArr.length / 2; i++) {
                int i2 = i * 2;
                if (iArr[i2] > iArr[i2 + 1]) {
                    throw new java.lang.IllegalArgumentException("Reverse range found in the flatten range: " + java.util.Arrays.toString(iArr));
                }
            }
            java.util.Objects.requireNonNull(paint);
            this.mHighlights.add(new android.util.Pair<>(paint, iArr));
            return this;
        }

        public android.text.Highlights build() {
            return new android.text.Highlights(this.mHighlights);
        }
    }
}
