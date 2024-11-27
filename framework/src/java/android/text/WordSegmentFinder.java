package android.text;

/* loaded from: classes3.dex */
public class WordSegmentFinder extends android.text.SegmentFinder {
    private final java.lang.CharSequence mText;
    private final android.text.method.WordIterator mWordIterator;

    public WordSegmentFinder(java.lang.CharSequence charSequence, android.icu.util.ULocale uLocale) {
        this.mText = charSequence;
        this.mWordIterator = new android.text.method.WordIterator(uLocale);
        this.mWordIterator.setCharSequence(charSequence, 0, charSequence.length());
    }

    public WordSegmentFinder(java.lang.CharSequence charSequence, android.text.method.WordIterator wordIterator) {
        this.mText = charSequence;
        this.mWordIterator = wordIterator;
    }

    @Override // android.text.SegmentFinder
    public int previousStartBoundary(int i) {
        do {
            i = this.mWordIterator.prevBoundary(i);
            if (i == -1) {
                return -1;
            }
        } while (java.lang.Character.isWhitespace(this.mText.charAt(i)));
        return i;
    }

    @Override // android.text.SegmentFinder
    public int previousEndBoundary(int i) {
        do {
            i = this.mWordIterator.prevBoundary(i);
            if (i == -1 || i == 0) {
                return -1;
            }
        } while (java.lang.Character.isWhitespace(this.mText.charAt(i - 1)));
        return i;
    }

    @Override // android.text.SegmentFinder
    public int nextStartBoundary(int i) {
        do {
            i = this.mWordIterator.nextBoundary(i);
            if (i == -1 || i == this.mText.length()) {
                return -1;
            }
        } while (java.lang.Character.isWhitespace(this.mText.charAt(i)));
        return i;
    }

    @Override // android.text.SegmentFinder
    public int nextEndBoundary(int i) {
        do {
            i = this.mWordIterator.nextBoundary(i);
            if (i == -1) {
                return -1;
            }
        } while (java.lang.Character.isWhitespace(this.mText.charAt(i - 1)));
        return i;
    }
}
