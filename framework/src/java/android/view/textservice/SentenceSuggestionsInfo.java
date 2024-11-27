package android.view.textservice;

/* loaded from: classes4.dex */
public final class SentenceSuggestionsInfo implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.view.textservice.SentenceSuggestionsInfo> CREATOR = new android.os.Parcelable.Creator<android.view.textservice.SentenceSuggestionsInfo>() { // from class: android.view.textservice.SentenceSuggestionsInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.view.textservice.SentenceSuggestionsInfo createFromParcel(android.os.Parcel parcel) {
            return new android.view.textservice.SentenceSuggestionsInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.view.textservice.SentenceSuggestionsInfo[] newArray(int i) {
            return new android.view.textservice.SentenceSuggestionsInfo[i];
        }
    };
    private final int[] mLengths;
    private final int[] mOffsets;
    private final android.view.textservice.SuggestionsInfo[] mSuggestionsInfos;

    public SentenceSuggestionsInfo(android.view.textservice.SuggestionsInfo[] suggestionsInfoArr, int[] iArr, int[] iArr2) {
        if (suggestionsInfoArr == null || iArr == null || iArr2 == null) {
            throw new java.lang.NullPointerException();
        }
        if (suggestionsInfoArr.length != iArr.length || iArr.length != iArr2.length) {
            throw new java.lang.IllegalArgumentException();
        }
        int length = suggestionsInfoArr.length;
        this.mSuggestionsInfos = (android.view.textservice.SuggestionsInfo[]) java.util.Arrays.copyOf(suggestionsInfoArr, length);
        this.mOffsets = java.util.Arrays.copyOf(iArr, length);
        this.mLengths = java.util.Arrays.copyOf(iArr2, length);
    }

    public SentenceSuggestionsInfo(android.os.Parcel parcel) {
        this.mSuggestionsInfos = new android.view.textservice.SuggestionsInfo[parcel.readInt()];
        parcel.readTypedArray(this.mSuggestionsInfos, android.view.textservice.SuggestionsInfo.CREATOR);
        this.mOffsets = new int[this.mSuggestionsInfos.length];
        parcel.readIntArray(this.mOffsets);
        this.mLengths = new int[this.mSuggestionsInfos.length];
        parcel.readIntArray(this.mLengths);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mSuggestionsInfos.length);
        parcel.writeTypedArray(this.mSuggestionsInfos, 0);
        parcel.writeIntArray(this.mOffsets);
        parcel.writeIntArray(this.mLengths);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public int getSuggestionsCount() {
        return this.mSuggestionsInfos.length;
    }

    public android.view.textservice.SuggestionsInfo getSuggestionsInfoAt(int i) {
        if (i >= 0 && i < this.mSuggestionsInfos.length) {
            return this.mSuggestionsInfos[i];
        }
        return null;
    }

    public int getOffsetAt(int i) {
        if (i >= 0 && i < this.mOffsets.length) {
            return this.mOffsets[i];
        }
        return -1;
    }

    public int getLengthAt(int i) {
        if (i >= 0 && i < this.mLengths.length) {
            return this.mLengths[i];
        }
        return -1;
    }
}
