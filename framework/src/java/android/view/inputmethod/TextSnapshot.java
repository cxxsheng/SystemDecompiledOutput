package android.view.inputmethod;

/* loaded from: classes4.dex */
public final class TextSnapshot {
    private final int mCompositionEnd;
    private final int mCompositionStart;
    private final int mCursorCapsMode;
    private final android.view.inputmethod.SurroundingText mSurroundingText;

    public TextSnapshot(android.view.inputmethod.SurroundingText surroundingText, int i, int i2, int i3) {
        java.util.Objects.requireNonNull(surroundingText);
        this.mSurroundingText = surroundingText;
        if (i < -1) {
            throw new java.lang.IllegalArgumentException("compositionStart must be -1 or higher but was " + i);
        }
        if (i2 < -1) {
            throw new java.lang.IllegalArgumentException("compositionEnd must be -1 or higher but was " + i2);
        }
        if (i == -1 && i2 != -1) {
            throw new java.lang.IllegalArgumentException("compositionEnd must be -1 if compositionStart is -1 but was " + i2);
        }
        if (i != -1 && i2 == -1) {
            throw new java.lang.IllegalArgumentException("compositionStart must be -1 if compositionEnd is -1 but was " + i);
        }
        if (i > i2) {
            throw new java.lang.IllegalArgumentException("compositionStart=" + i + " must be equal to or greater than compositionEnd=" + i2);
        }
        this.mCompositionStart = i;
        this.mCompositionEnd = i2;
        this.mCursorCapsMode = i3;
    }

    public android.view.inputmethod.SurroundingText getSurroundingText() {
        return this.mSurroundingText;
    }

    public int getSelectionStart() {
        if (this.mSurroundingText.getOffset() < 0) {
            return -1;
        }
        return this.mSurroundingText.getSelectionStart() + this.mSurroundingText.getOffset();
    }

    public int getSelectionEnd() {
        if (this.mSurroundingText.getOffset() < 0) {
            return -1;
        }
        return this.mSurroundingText.getSelectionEnd() + this.mSurroundingText.getOffset();
    }

    public int getCompositionStart() {
        return this.mCompositionStart;
    }

    public int getCompositionEnd() {
        return this.mCompositionEnd;
    }

    public int getCursorCapsMode() {
        return this.mCursorCapsMode;
    }
}
