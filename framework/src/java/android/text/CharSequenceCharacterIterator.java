package android.text;

/* loaded from: classes3.dex */
public class CharSequenceCharacterIterator implements java.text.CharacterIterator {
    private final int mBeginIndex;
    private final java.lang.CharSequence mCharSeq;
    private final int mEndIndex;
    private int mIndex;

    public CharSequenceCharacterIterator(java.lang.CharSequence charSequence, int i, int i2) {
        this.mCharSeq = charSequence;
        this.mIndex = i;
        this.mBeginIndex = i;
        this.mEndIndex = i2;
    }

    @Override // java.text.CharacterIterator
    public char first() {
        this.mIndex = this.mBeginIndex;
        return current();
    }

    @Override // java.text.CharacterIterator
    public char last() {
        if (this.mBeginIndex == this.mEndIndex) {
            this.mIndex = this.mEndIndex;
            return (char) 65535;
        }
        this.mIndex = this.mEndIndex - 1;
        return this.mCharSeq.charAt(this.mIndex);
    }

    @Override // java.text.CharacterIterator
    public char current() {
        if (this.mIndex == this.mEndIndex) {
            return (char) 65535;
        }
        return this.mCharSeq.charAt(this.mIndex);
    }

    @Override // java.text.CharacterIterator
    public char next() {
        this.mIndex++;
        if (this.mIndex >= this.mEndIndex) {
            this.mIndex = this.mEndIndex;
            return (char) 65535;
        }
        return this.mCharSeq.charAt(this.mIndex);
    }

    @Override // java.text.CharacterIterator
    public char previous() {
        if (this.mIndex <= this.mBeginIndex) {
            return (char) 65535;
        }
        this.mIndex--;
        return this.mCharSeq.charAt(this.mIndex);
    }

    @Override // java.text.CharacterIterator
    public char setIndex(int i) {
        if (this.mBeginIndex <= i && i <= this.mEndIndex) {
            this.mIndex = i;
            return current();
        }
        throw new java.lang.IllegalArgumentException("invalid position");
    }

    @Override // java.text.CharacterIterator
    public int getBeginIndex() {
        return this.mBeginIndex;
    }

    @Override // java.text.CharacterIterator
    public int getEndIndex() {
        return this.mEndIndex;
    }

    @Override // java.text.CharacterIterator
    public int getIndex() {
        return this.mIndex;
    }

    @Override // java.text.CharacterIterator
    public java.lang.Object clone() {
        try {
            return super.clone();
        } catch (java.lang.CloneNotSupportedException e) {
            throw new java.lang.InternalError();
        }
    }
}
