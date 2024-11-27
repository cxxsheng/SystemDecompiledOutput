package android.text;

/* loaded from: classes3.dex */
public class SpanSet<E> {
    private final java.lang.Class<? extends E> classType;
    int numberOfSpans = 0;
    int[] spanEnds;
    int[] spanFlags;
    int[] spanStarts;
    E[] spans;

    SpanSet(java.lang.Class<? extends E> cls) {
        this.classType = cls;
    }

    public void init(android.text.Spanned spanned, int i, int i2) {
        java.lang.Object[] spans = spanned.getSpans(i, i2, this.classType);
        int length = spans.length;
        if (length > 0 && (this.spans == null || this.spans.length < length)) {
            this.spans = (E[]) ((java.lang.Object[]) java.lang.reflect.Array.newInstance(this.classType, length));
            this.spanStarts = new int[length];
            this.spanEnds = new int[length];
            this.spanFlags = new int[length];
        }
        int i3 = this.numberOfSpans;
        this.numberOfSpans = 0;
        for (java.lang.Object obj : spans) {
            int spanStart = spanned.getSpanStart(obj);
            int spanEnd = spanned.getSpanEnd(obj);
            if (spanStart != spanEnd) {
                int spanFlags = spanned.getSpanFlags(obj);
                ((E[]) this.spans)[this.numberOfSpans] = obj;
                this.spanStarts[this.numberOfSpans] = spanStart;
                this.spanEnds[this.numberOfSpans] = spanEnd;
                this.spanFlags[this.numberOfSpans] = spanFlags;
                this.numberOfSpans++;
            }
        }
        if (this.numberOfSpans < i3) {
            java.util.Arrays.fill(this.spans, this.numberOfSpans, i3, (java.lang.Object) null);
        }
    }

    public boolean hasSpansIntersecting(int i, int i2) {
        for (int i3 = 0; i3 < this.numberOfSpans; i3++) {
            if (this.spanStarts[i3] < i2 && this.spanEnds[i3] > i) {
                return true;
            }
        }
        return false;
    }

    int getNextTransition(int i, int i2) {
        for (int i3 = 0; i3 < this.numberOfSpans; i3++) {
            int i4 = this.spanStarts[i3];
            int i5 = this.spanEnds[i3];
            if (i4 > i && i4 < i2) {
                i2 = i4;
            }
            if (i5 > i && i5 < i2) {
                i2 = i5;
            }
        }
        return i2;
    }

    public void recycle() {
        if (this.spans != null) {
            java.util.Arrays.fill(this.spans, 0, this.numberOfSpans, (java.lang.Object) null);
        }
    }
}
