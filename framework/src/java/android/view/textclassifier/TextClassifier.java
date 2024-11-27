package android.view.textclassifier;

/* loaded from: classes4.dex */
public interface TextClassifier {
    public static final int DEFAULT_SYSTEM = 2;
    public static final java.lang.String EXTRA_FROM_TEXT_CLASSIFIER = "android.view.textclassifier.extra.FROM_TEXT_CLASSIFIER";
    public static final java.lang.String HINT_TEXT_IS_EDITABLE = "android.text_is_editable";
    public static final java.lang.String HINT_TEXT_IS_NOT_EDITABLE = "android.text_is_not_editable";
    public static final int LOCAL = 0;
    public static final java.lang.String LOG_TAG = "androidtc";
    public static final android.view.textclassifier.TextClassifier NO_OP = new android.view.textclassifier.TextClassifier() { // from class: android.view.textclassifier.TextClassifier.1
        public java.lang.String toString() {
            return "TextClassifier.NO_OP";
        }
    };
    public static final int SYSTEM = 1;
    public static final java.lang.String TYPE_ADDRESS = "address";
    public static final java.lang.String TYPE_DATE = "date";
    public static final java.lang.String TYPE_DATE_TIME = "datetime";
    public static final java.lang.String TYPE_DICTIONARY = "dictionary";
    public static final java.lang.String TYPE_EMAIL = "email";
    public static final java.lang.String TYPE_FLIGHT_NUMBER = "flight";
    public static final java.lang.String TYPE_OTHER = "other";
    public static final java.lang.String TYPE_OTP_CODE = "otp_code";
    public static final java.lang.String TYPE_PHONE = "phone";
    public static final java.lang.String TYPE_UNKNOWN = "";
    public static final java.lang.String TYPE_URL = "url";
    public static final java.lang.String WIDGET_TYPE_CLIPBOARD = "clipboard";
    public static final java.lang.String WIDGET_TYPE_CUSTOM_EDITTEXT = "customedit";
    public static final java.lang.String WIDGET_TYPE_CUSTOM_TEXTVIEW = "customview";
    public static final java.lang.String WIDGET_TYPE_CUSTOM_UNSELECTABLE_TEXTVIEW = "nosel-customview";
    public static final java.lang.String WIDGET_TYPE_EDITTEXT = "edittext";
    public static final java.lang.String WIDGET_TYPE_EDIT_WEBVIEW = "edit-webview";
    public static final java.lang.String WIDGET_TYPE_NOTIFICATION = "notification";
    public static final java.lang.String WIDGET_TYPE_TEXTVIEW = "textview";
    public static final java.lang.String WIDGET_TYPE_UNKNOWN = "unknown";
    public static final java.lang.String WIDGET_TYPE_UNSELECTABLE_TEXTVIEW = "nosel-textview";
    public static final java.lang.String WIDGET_TYPE_WEBVIEW = "webview";

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface EntityType {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface Hints {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface TextClassifierType {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface WidgetType {
    }

    static java.lang.String typeToString(int i) {
        switch (i) {
            case 0:
                return "Local";
            case 1:
                return "System";
            case 2:
                return "Default system";
            default:
                return "Unknown";
        }
    }

    default android.view.textclassifier.TextSelection suggestSelection(android.view.textclassifier.TextSelection.Request request) {
        java.util.Objects.requireNonNull(request);
        android.view.textclassifier.TextClassifier.Utils.checkMainThread();
        return new android.view.textclassifier.TextSelection.Builder(request.getStartIndex(), request.getEndIndex()).build();
    }

    default android.view.textclassifier.TextSelection suggestSelection(java.lang.CharSequence charSequence, int i, int i2, android.os.LocaleList localeList) {
        return suggestSelection(new android.view.textclassifier.TextSelection.Request.Builder(charSequence, i, i2).setDefaultLocales(localeList).build());
    }

    default android.view.textclassifier.TextClassification classifyText(android.view.textclassifier.TextClassification.Request request) {
        java.util.Objects.requireNonNull(request);
        android.view.textclassifier.TextClassifier.Utils.checkMainThread();
        return android.view.textclassifier.TextClassification.EMPTY;
    }

    default android.view.textclassifier.TextClassification classifyText(java.lang.CharSequence charSequence, int i, int i2, android.os.LocaleList localeList) {
        return classifyText(new android.view.textclassifier.TextClassification.Request.Builder(charSequence, i, i2).setDefaultLocales(localeList).build());
    }

    default android.view.textclassifier.TextLinks generateLinks(android.view.textclassifier.TextLinks.Request request) {
        java.util.Objects.requireNonNull(request);
        android.view.textclassifier.TextClassifier.Utils.checkMainThread();
        return new android.view.textclassifier.TextLinks.Builder(request.getText().toString()).build();
    }

    default int getMaxGenerateLinksTextLength() {
        return Integer.MAX_VALUE;
    }

    default android.view.textclassifier.TextLanguage detectLanguage(android.view.textclassifier.TextLanguage.Request request) {
        java.util.Objects.requireNonNull(request);
        android.view.textclassifier.TextClassifier.Utils.checkMainThread();
        return android.view.textclassifier.TextLanguage.EMPTY;
    }

    default android.view.textclassifier.ConversationActions suggestConversationActions(android.view.textclassifier.ConversationActions.Request request) {
        java.util.Objects.requireNonNull(request);
        android.view.textclassifier.TextClassifier.Utils.checkMainThread();
        return new android.view.textclassifier.ConversationActions((java.util.List<android.view.textclassifier.ConversationAction>) java.util.Collections.emptyList(), (java.lang.String) null);
    }

    default void onSelectionEvent(android.view.textclassifier.SelectionEvent selectionEvent) {
    }

    default void onTextClassifierEvent(android.view.textclassifier.TextClassifierEvent textClassifierEvent) {
    }

    default void destroy() {
    }

    default boolean isDestroyed() {
        return false;
    }

    default void dump(com.android.internal.util.IndentingPrintWriter indentingPrintWriter) {
    }

    public static final class EntityConfig implements android.os.Parcelable {
        public static final android.os.Parcelable.Creator<android.view.textclassifier.TextClassifier.EntityConfig> CREATOR = new android.os.Parcelable.Creator<android.view.textclassifier.TextClassifier.EntityConfig>() { // from class: android.view.textclassifier.TextClassifier.EntityConfig.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.view.textclassifier.TextClassifier.EntityConfig createFromParcel(android.os.Parcel parcel) {
                return new android.view.textclassifier.TextClassifier.EntityConfig(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.view.textclassifier.TextClassifier.EntityConfig[] newArray(int i) {
                return new android.view.textclassifier.TextClassifier.EntityConfig[i];
            }
        };
        private final java.util.List<java.lang.String> mExcludedTypes;
        private final java.util.List<java.lang.String> mHints;
        private final boolean mIncludeTypesFromTextClassifier;
        private final java.util.List<java.lang.String> mIncludedTypes;

        private EntityConfig(java.util.List<java.lang.String> list, java.util.List<java.lang.String> list2, java.util.List<java.lang.String> list3, boolean z) {
            this.mIncludedTypes = (java.util.List) java.util.Objects.requireNonNull(list);
            this.mExcludedTypes = (java.util.List) java.util.Objects.requireNonNull(list2);
            this.mHints = (java.util.List) java.util.Objects.requireNonNull(list3);
            this.mIncludeTypesFromTextClassifier = z;
        }

        private EntityConfig(android.os.Parcel parcel) {
            this.mIncludedTypes = new java.util.ArrayList();
            parcel.readStringList(this.mIncludedTypes);
            this.mExcludedTypes = new java.util.ArrayList();
            parcel.readStringList(this.mExcludedTypes);
            java.util.ArrayList arrayList = new java.util.ArrayList();
            parcel.readStringList(arrayList);
            this.mHints = java.util.Collections.unmodifiableList(arrayList);
            this.mIncludeTypesFromTextClassifier = parcel.readByte() != 0;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(android.os.Parcel parcel, int i) {
            parcel.writeStringList(this.mIncludedTypes);
            parcel.writeStringList(this.mExcludedTypes);
            parcel.writeStringList(this.mHints);
            parcel.writeByte(this.mIncludeTypesFromTextClassifier ? (byte) 1 : (byte) 0);
        }

        @java.lang.Deprecated
        public static android.view.textclassifier.TextClassifier.EntityConfig createWithHints(java.util.Collection<java.lang.String> collection) {
            return new android.view.textclassifier.TextClassifier.EntityConfig.Builder().includeTypesFromTextClassifier(true).setHints(collection).build();
        }

        @java.lang.Deprecated
        public static android.view.textclassifier.TextClassifier.EntityConfig create(java.util.Collection<java.lang.String> collection, java.util.Collection<java.lang.String> collection2, java.util.Collection<java.lang.String> collection3) {
            return new android.view.textclassifier.TextClassifier.EntityConfig.Builder().setIncludedTypes(collection2).setExcludedTypes(collection3).setHints(collection).includeTypesFromTextClassifier(true).build();
        }

        @java.lang.Deprecated
        public static android.view.textclassifier.TextClassifier.EntityConfig createWithExplicitEntityList(java.util.Collection<java.lang.String> collection) {
            return new android.view.textclassifier.TextClassifier.EntityConfig.Builder().setIncludedTypes(collection).includeTypesFromTextClassifier(false).build();
        }

        public java.util.Collection<java.lang.String> resolveEntityListModifications(java.util.Collection<java.lang.String> collection) {
            java.util.HashSet hashSet = new java.util.HashSet();
            if (this.mIncludeTypesFromTextClassifier) {
                hashSet.addAll(collection);
            }
            hashSet.addAll(this.mIncludedTypes);
            hashSet.removeAll(this.mExcludedTypes);
            return hashSet;
        }

        public java.util.Collection<java.lang.String> getHints() {
            return this.mHints;
        }

        public boolean shouldIncludeTypesFromTextClassifier() {
            return this.mIncludeTypesFromTextClassifier;
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        public static final class Builder {
            private java.util.Collection<java.lang.String> mExcludedTypes;
            private java.util.Collection<java.lang.String> mHints;
            private boolean mIncludeTypesFromTextClassifier = true;
            private java.util.Collection<java.lang.String> mIncludedTypes;

            public android.view.textclassifier.TextClassifier.EntityConfig.Builder setIncludedTypes(java.util.Collection<java.lang.String> collection) {
                this.mIncludedTypes = collection;
                return this;
            }

            public android.view.textclassifier.TextClassifier.EntityConfig.Builder setExcludedTypes(java.util.Collection<java.lang.String> collection) {
                this.mExcludedTypes = collection;
                return this;
            }

            public android.view.textclassifier.TextClassifier.EntityConfig.Builder includeTypesFromTextClassifier(boolean z) {
                this.mIncludeTypesFromTextClassifier = z;
                return this;
            }

            public android.view.textclassifier.TextClassifier.EntityConfig.Builder setHints(java.util.Collection<java.lang.String> collection) {
                this.mHints = collection;
                return this;
            }

            /* JADX WARN: Multi-variable type inference failed */
            /* JADX WARN: Type inference failed for: r0v10, types: [java.util.List] */
            /* JADX WARN: Type inference failed for: r0v9, types: [java.util.List] */
            public android.view.textclassifier.TextClassifier.EntityConfig build() {
                java.util.ArrayList arrayList;
                java.util.ArrayList arrayList2;
                java.util.List unmodifiableList;
                if (this.mIncludedTypes == null) {
                    arrayList = java.util.Collections.emptyList();
                } else {
                    arrayList = new java.util.ArrayList(this.mIncludedTypes);
                }
                if (this.mExcludedTypes == null) {
                    arrayList2 = java.util.Collections.emptyList();
                } else {
                    arrayList2 = new java.util.ArrayList(this.mExcludedTypes);
                }
                if (this.mHints == null) {
                    unmodifiableList = java.util.Collections.emptyList();
                } else {
                    unmodifiableList = java.util.Collections.unmodifiableList(new java.util.ArrayList(this.mHints));
                }
                return new android.view.textclassifier.TextClassifier.EntityConfig(arrayList, arrayList2, unmodifiableList, this.mIncludeTypesFromTextClassifier);
            }
        }
    }

    public static final class Utils {
        private static final java.text.BreakIterator WORD_ITERATOR = java.text.BreakIterator.getWordInstance();

        static void checkArgument(java.lang.CharSequence charSequence, int i, int i2) {
            com.android.internal.util.Preconditions.checkArgument(charSequence != null);
            com.android.internal.util.Preconditions.checkArgument(i >= 0);
            com.android.internal.util.Preconditions.checkArgument(i2 <= charSequence.length());
            com.android.internal.util.Preconditions.checkArgument(i2 > i);
        }

        static boolean checkTextLength(java.lang.CharSequence charSequence, int i) {
            int length = charSequence.length();
            return length >= 0 && length <= i;
        }

        public static java.lang.String getSubString(java.lang.String str, int i, int i2, int i3) {
            java.lang.String substring;
            com.android.internal.util.Preconditions.checkArgument(i >= 0);
            com.android.internal.util.Preconditions.checkArgument(i2 <= str.length());
            com.android.internal.util.Preconditions.checkArgument(i <= i2);
            if (str.length() < i3) {
                return str;
            }
            int i4 = i2 - i;
            if (i4 >= i3) {
                return str.substring(i, i2);
            }
            int max = java.lang.Math.max(0, java.lang.Math.min(i - ((i3 - i4) / 2), str.length() - i3));
            int min = java.lang.Math.min(str.length(), i3 + max);
            synchronized (WORD_ITERATOR) {
                WORD_ITERATOR.setText(str);
                if (!WORD_ITERATOR.isBoundary(max)) {
                    max = java.lang.Math.max(0, WORD_ITERATOR.preceding(max));
                }
                if (!WORD_ITERATOR.isBoundary(min)) {
                    min = java.lang.Math.max(min, WORD_ITERATOR.following(min));
                }
                WORD_ITERATOR.setText("");
                substring = str.substring(max, min);
            }
            return substring;
        }

        public static android.view.textclassifier.TextLinks generateLegacyLinks(android.view.textclassifier.TextLinks.Request request) {
            java.lang.String charSequence = request.getText().toString();
            android.view.textclassifier.TextLinks.Builder builder = new android.view.textclassifier.TextLinks.Builder(charSequence);
            java.util.Collection<java.lang.String> resolveEntityListModifications = request.getEntityConfig().resolveEntityListModifications(java.util.Collections.emptyList());
            if (resolveEntityListModifications.contains("url")) {
                addLinks(builder, charSequence, "url");
            }
            if (resolveEntityListModifications.contains("phone")) {
                addLinks(builder, charSequence, "phone");
            }
            if (resolveEntityListModifications.contains("email")) {
                addLinks(builder, charSequence, "email");
            }
            return builder.build();
        }

        private static void addLinks(android.view.textclassifier.TextLinks.Builder builder, java.lang.String str, java.lang.String str2) {
            android.text.SpannableString spannableString = new android.text.SpannableString(str);
            if (android.text.util.Linkify.addLinks(spannableString, linkMask(str2))) {
                for (android.text.style.URLSpan uRLSpan : (android.text.style.URLSpan[]) spannableString.getSpans(0, spannableString.length(), android.text.style.URLSpan.class)) {
                    builder.addLink(spannableString.getSpanStart(uRLSpan), spannableString.getSpanEnd(uRLSpan), entityScores(str2), uRLSpan);
                }
            }
        }

        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        private static int linkMask(java.lang.String str) {
            char c;
            switch (str.hashCode()) {
                case 116079:
                    if (str.equals("url")) {
                        c = 0;
                        break;
                    }
                    c = 65535;
                    break;
                case 96619420:
                    if (str.equals("email")) {
                        c = 2;
                        break;
                    }
                    c = 65535;
                    break;
                case 106642798:
                    if (str.equals("phone")) {
                        c = 1;
                        break;
                    }
                    c = 65535;
                    break;
                default:
                    c = 65535;
                    break;
            }
            switch (c) {
                case 0:
                    return 1;
                case 1:
                    return 4;
                case 2:
                    return 2;
                default:
                    return 0;
            }
        }

        private static java.util.Map<java.lang.String, java.lang.Float> entityScores(java.lang.String str) {
            android.util.ArrayMap arrayMap = new android.util.ArrayMap();
            arrayMap.put(str, java.lang.Float.valueOf(1.0f));
            return arrayMap;
        }

        static void checkMainThread() {
            if (android.os.Looper.myLooper() == android.os.Looper.getMainLooper()) {
                android.view.textclassifier.Log.w(android.view.textclassifier.TextClassifier.LOG_TAG, "TextClassifier called on main thread");
            }
        }
    }
}
