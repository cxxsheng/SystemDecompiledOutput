package com.android.server.inputmethod;

/* loaded from: classes2.dex */
final class LocaleUtils {

    public interface LocaleExtractor<T> {
        @android.annotation.Nullable
        java.util.Locale get(@android.annotation.Nullable T t);
    }

    LocaleUtils() {
    }

    private static byte calculateMatchingSubScore(@android.annotation.NonNull android.icu.util.ULocale uLocale, @android.annotation.NonNull android.icu.util.ULocale uLocale2) {
        if (uLocale.equals(uLocale2)) {
            return (byte) 3;
        }
        java.lang.String script = uLocale.getScript();
        if (script.isEmpty() || !script.equals(uLocale2.getScript())) {
            return (byte) 1;
        }
        java.lang.String country = uLocale.getCountry();
        return (country.isEmpty() || !country.equals(uLocale2.getCountry())) ? (byte) 2 : (byte) 3;
    }

    private static final class ScoreEntry implements java.lang.Comparable<com.android.server.inputmethod.LocaleUtils.ScoreEntry> {
        public int mIndex = -1;

        @android.annotation.NonNull
        public final byte[] mScore;

        ScoreEntry(@android.annotation.NonNull byte[] bArr, int i) {
            this.mScore = new byte[bArr.length];
            set(bArr, i);
        }

        private void set(@android.annotation.NonNull byte[] bArr, int i) {
            for (int i2 = 0; i2 < this.mScore.length; i2++) {
                this.mScore[i2] = bArr[i2];
            }
            this.mIndex = i;
        }

        public void updateIfBetter(@android.annotation.NonNull byte[] bArr, int i) {
            if (compare(this.mScore, bArr) == -1) {
                set(bArr, i);
            }
        }

        private static int compare(@android.annotation.NonNull byte[] bArr, @android.annotation.NonNull byte[] bArr2) {
            for (int i = 0; i < bArr.length; i++) {
                if (bArr[i] > bArr2[i]) {
                    return 1;
                }
                if (bArr[i] < bArr2[i]) {
                    return -1;
                }
            }
            return 0;
        }

        @Override // java.lang.Comparable
        public int compareTo(com.android.server.inputmethod.LocaleUtils.ScoreEntry scoreEntry) {
            return compare(this.mScore, scoreEntry.mScore) * (-1);
        }
    }

    public static <T> void filterByLanguage(@android.annotation.NonNull java.util.List<T> list, @android.annotation.NonNull com.android.server.inputmethod.LocaleUtils.LocaleExtractor<T> localeExtractor, @android.annotation.NonNull android.os.LocaleList localeList, @android.annotation.NonNull java.util.ArrayList<T> arrayList) {
        if (localeList.isEmpty()) {
            return;
        }
        int size = localeList.size();
        android.util.ArrayMap arrayMap = new android.util.ArrayMap();
        byte[] bArr = new byte[size];
        android.icu.util.ULocale[] uLocaleArr = new android.icu.util.ULocale[size];
        int size2 = list.size();
        for (int i = 0; i < size2; i++) {
            java.util.Locale locale = localeExtractor.get(list.get(i));
            if (locale != null) {
                boolean z = true;
                for (int i2 = 0; i2 < size; i2++) {
                    java.util.Locale locale2 = localeList.get(i2);
                    if (!android.text.TextUtils.equals(locale.getLanguage(), locale2.getLanguage())) {
                        bArr[i2] = 0;
                    } else {
                        if (uLocaleArr[i2] == null) {
                            uLocaleArr[i2] = android.icu.util.ULocale.addLikelySubtags(android.icu.util.ULocale.forLocale(locale2));
                        }
                        bArr[i2] = calculateMatchingSubScore(uLocaleArr[i2], android.icu.util.ULocale.addLikelySubtags(android.icu.util.ULocale.forLocale(locale)));
                        if (z && bArr[i2] != 0) {
                            z = false;
                        }
                    }
                }
                if (!z) {
                    java.lang.String language = locale.getLanguage();
                    com.android.server.inputmethod.LocaleUtils.ScoreEntry scoreEntry = (com.android.server.inputmethod.LocaleUtils.ScoreEntry) arrayMap.get(language);
                    if (scoreEntry == null) {
                        arrayMap.put(language, new com.android.server.inputmethod.LocaleUtils.ScoreEntry(bArr, i));
                    } else {
                        scoreEntry.updateIfBetter(bArr, i);
                    }
                }
            }
        }
        int size3 = arrayMap.size();
        com.android.server.inputmethod.LocaleUtils.ScoreEntry[] scoreEntryArr = new com.android.server.inputmethod.LocaleUtils.ScoreEntry[size3];
        for (int i3 = 0; i3 < size3; i3++) {
            scoreEntryArr[i3] = (com.android.server.inputmethod.LocaleUtils.ScoreEntry) arrayMap.valueAt(i3);
        }
        java.util.Arrays.sort(scoreEntryArr);
        for (int i4 = 0; i4 < size3; i4++) {
            arrayList.add(list.get(scoreEntryArr[i4].mIndex));
        }
    }

    static java.lang.String getLanguageFromLocaleString(java.lang.String str) {
        int indexOf = str.indexOf(95);
        if (indexOf < 0) {
            return str;
        }
        return str.substring(0, indexOf);
    }

    static java.util.Locale getSystemLocaleFromContext(android.content.Context context) {
        try {
            return context.getResources().getConfiguration().locale;
        } catch (android.content.res.Resources.NotFoundException e) {
            return null;
        }
    }
}
