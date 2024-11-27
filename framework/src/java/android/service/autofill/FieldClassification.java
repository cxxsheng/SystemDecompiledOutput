package android.service.autofill;

/* loaded from: classes3.dex */
public final class FieldClassification {
    private final java.util.ArrayList<android.service.autofill.FieldClassification.Match> mMatches;

    public FieldClassification(java.util.ArrayList<android.service.autofill.FieldClassification.Match> arrayList) {
        this.mMatches = (java.util.ArrayList) java.util.Objects.requireNonNull(arrayList);
        java.util.Collections.sort(this.mMatches, new java.util.Comparator<android.service.autofill.FieldClassification.Match>() { // from class: android.service.autofill.FieldClassification.1
            @Override // java.util.Comparator
            public int compare(android.service.autofill.FieldClassification.Match match, android.service.autofill.FieldClassification.Match match2) {
                if (match.mScore > match2.mScore) {
                    return -1;
                }
                return match.mScore < match2.mScore ? 1 : 0;
            }
        });
    }

    public java.util.List<android.service.autofill.FieldClassification.Match> getMatches() {
        return this.mMatches;
    }

    public java.lang.String toString() {
        return !android.view.autofill.Helper.sDebug ? super.toString() : "FieldClassification: " + this.mMatches;
    }

    private void writeToParcel(android.os.Parcel parcel) {
        parcel.writeInt(this.mMatches.size());
        for (int i = 0; i < this.mMatches.size(); i++) {
            this.mMatches.get(i).writeToParcel(parcel);
        }
    }

    private static android.service.autofill.FieldClassification readFromParcel(android.os.Parcel parcel) {
        int readInt = parcel.readInt();
        java.util.ArrayList arrayList = new java.util.ArrayList();
        for (int i = 0; i < readInt; i++) {
            arrayList.add(i, android.service.autofill.FieldClassification.Match.readFromParcel(parcel));
        }
        return new android.service.autofill.FieldClassification(arrayList);
    }

    static android.service.autofill.FieldClassification[] readArrayFromParcel(android.os.Parcel parcel) {
        int readInt = parcel.readInt();
        android.service.autofill.FieldClassification[] fieldClassificationArr = new android.service.autofill.FieldClassification[readInt];
        for (int i = 0; i < readInt; i++) {
            fieldClassificationArr[i] = readFromParcel(parcel);
        }
        return fieldClassificationArr;
    }

    static void writeArrayToParcel(android.os.Parcel parcel, android.service.autofill.FieldClassification[] fieldClassificationArr) {
        parcel.writeInt(fieldClassificationArr.length);
        for (android.service.autofill.FieldClassification fieldClassification : fieldClassificationArr) {
            fieldClassification.writeToParcel(parcel);
        }
    }

    public static final class Match {
        private final java.lang.String mCategoryId;
        private final float mScore;

        public Match(java.lang.String str, float f) {
            this.mCategoryId = (java.lang.String) java.util.Objects.requireNonNull(str);
            this.mScore = f;
        }

        public java.lang.String getCategoryId() {
            return this.mCategoryId;
        }

        public float getScore() {
            return this.mScore;
        }

        public java.lang.String toString() {
            if (!android.view.autofill.Helper.sDebug) {
                return super.toString();
            }
            java.lang.StringBuilder sb = new java.lang.StringBuilder("Match: categoryId=");
            android.view.autofill.Helper.appendRedacted(sb, this.mCategoryId);
            return sb.append(", score=").append(this.mScore).toString();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void writeToParcel(android.os.Parcel parcel) {
            parcel.writeString(this.mCategoryId);
            parcel.writeFloat(this.mScore);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static android.service.autofill.FieldClassification.Match readFromParcel(android.os.Parcel parcel) {
            return new android.service.autofill.FieldClassification.Match(parcel.readString(), parcel.readFloat());
        }
    }
}
