package android.text;

/* loaded from: classes3.dex */
public class GraphemeClusterSegmentFinder extends android.text.SegmentFinder {
    private static android.text.AutoGrowArray.FloatArray sTempAdvances = null;
    private final boolean[] mIsGraphemeBreak;

    public GraphemeClusterSegmentFinder(java.lang.CharSequence charSequence, android.text.TextPaint textPaint) {
        if (sTempAdvances == null) {
            sTempAdvances = new android.text.AutoGrowArray.FloatArray(charSequence.length());
        } else if (sTempAdvances.size() < charSequence.length()) {
            sTempAdvances.resize(charSequence.length());
        }
        this.mIsGraphemeBreak = new boolean[charSequence.length()];
        float[] rawArray = sTempAdvances.getRawArray();
        char[] obtain = android.graphics.TemporaryBuffer.obtain(charSequence.length());
        android.text.TextUtils.getChars(charSequence, 0, charSequence.length(), obtain, 0);
        textPaint.getTextWidths(obtain, 0, charSequence.length(), rawArray);
        android.graphics.text.GraphemeBreak.isGraphemeBreak(rawArray, obtain, 0, charSequence.length(), this.mIsGraphemeBreak);
        android.graphics.TemporaryBuffer.recycle(obtain);
    }

    private int previousBoundary(int i) {
        if (i <= 0) {
            return -1;
        }
        do {
            i--;
            if (i <= 0) {
                break;
            }
        } while (!this.mIsGraphemeBreak[i]);
        return i;
    }

    private int nextBoundary(int i) {
        if (i >= this.mIsGraphemeBreak.length) {
            return -1;
        }
        do {
            i++;
            if (i >= this.mIsGraphemeBreak.length) {
                break;
            }
        } while (!this.mIsGraphemeBreak[i]);
        return i;
    }

    @Override // android.text.SegmentFinder
    public int previousStartBoundary(int i) {
        return previousBoundary(i);
    }

    @Override // android.text.SegmentFinder
    public int previousEndBoundary(int i) {
        int previousBoundary;
        if (i == 0 || (previousBoundary = previousBoundary(i)) == -1 || previousBoundary(previousBoundary) == -1) {
            return -1;
        }
        return previousBoundary;
    }

    @Override // android.text.SegmentFinder
    public int nextStartBoundary(int i) {
        int nextBoundary;
        if (i == this.mIsGraphemeBreak.length || (nextBoundary = nextBoundary(i)) == -1 || nextBoundary(nextBoundary) == -1) {
            return -1;
        }
        return nextBoundary;
    }

    @Override // android.text.SegmentFinder
    public int nextEndBoundary(int i) {
        return nextBoundary(i);
    }
}
