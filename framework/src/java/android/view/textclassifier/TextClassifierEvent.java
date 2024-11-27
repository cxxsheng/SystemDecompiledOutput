package android.view.textclassifier;

/* loaded from: classes4.dex */
public abstract class TextClassifierEvent implements android.os.Parcelable {
    public static final int CATEGORY_CONVERSATION_ACTIONS = 3;
    public static final int CATEGORY_LANGUAGE_DETECTION = 4;
    public static final int CATEGORY_LINKIFY = 2;
    public static final int CATEGORY_SELECTION = 1;
    public static final android.os.Parcelable.Creator<android.view.textclassifier.TextClassifierEvent> CREATOR = new android.os.Parcelable.Creator<android.view.textclassifier.TextClassifierEvent>() { // from class: android.view.textclassifier.TextClassifierEvent.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.view.textclassifier.TextClassifierEvent createFromParcel(android.os.Parcel parcel) {
            int readInt = parcel.readInt();
            byte b = 0;
            byte b2 = 0;
            byte b3 = 0;
            if (readInt == 1) {
                return new android.view.textclassifier.TextClassifierEvent.TextSelectionEvent(parcel);
            }
            if (readInt == 2) {
                return new android.view.textclassifier.TextClassifierEvent.TextLinkifyEvent(parcel);
            }
            if (readInt == 4) {
                return new android.view.textclassifier.TextClassifierEvent.LanguageDetectionEvent(parcel);
            }
            if (readInt == 3) {
                return new android.view.textclassifier.TextClassifierEvent.ConversationActionsEvent(parcel);
            }
            throw new java.lang.IllegalStateException("Unexpected input event type token in parcel.");
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.view.textclassifier.TextClassifierEvent[] newArray(int i) {
            return new android.view.textclassifier.TextClassifierEvent[i];
        }
    };
    private static final int PARCEL_TOKEN_CONVERSATION_ACTION_EVENT = 3;
    private static final int PARCEL_TOKEN_LANGUAGE_DETECTION_EVENT = 4;
    private static final int PARCEL_TOKEN_TEXT_LINKIFY_EVENT = 2;
    private static final int PARCEL_TOKEN_TEXT_SELECTION_EVENT = 1;
    public static final int TYPE_ACTIONS_GENERATED = 20;
    public static final int TYPE_ACTIONS_SHOWN = 6;
    public static final int TYPE_AUTO_SELECTION = 5;
    public static final int TYPE_COPY_ACTION = 9;
    public static final int TYPE_CUT_ACTION = 11;
    public static final int TYPE_LINKS_GENERATED = 21;
    public static final int TYPE_LINK_CLICKED = 7;
    public static final int TYPE_MANUAL_REPLY = 19;
    public static final int TYPE_OTHER_ACTION = 16;
    public static final int TYPE_OVERTYPE = 8;
    public static final int TYPE_PASTE_ACTION = 10;
    public static final int TYPE_READ_CLIPBOARD = 22;
    public static final int TYPE_SELECTION_DESTROYED = 15;
    public static final int TYPE_SELECTION_DRAG = 14;
    public static final int TYPE_SELECTION_MODIFIED = 2;
    public static final int TYPE_SELECTION_RESET = 18;
    public static final int TYPE_SELECTION_STARTED = 1;
    public static final int TYPE_SELECT_ALL = 17;
    public static final int TYPE_SHARE_ACTION = 12;
    public static final int TYPE_SMART_ACTION = 13;
    public static final int TYPE_SMART_SELECTION_MULTI = 4;
    public static final int TYPE_SMART_SELECTION_SINGLE = 3;
    private final int[] mActionIndices;
    private final java.lang.String[] mEntityTypes;
    private final int mEventCategory;
    private android.view.textclassifier.TextClassificationContext mEventContext;
    private final int mEventIndex;
    private final int mEventType;
    private final android.os.Bundle mExtras;
    public android.view.textclassifier.TextClassificationSessionId mHiddenTempSessionId;
    private final android.icu.util.ULocale mLocale;
    private final java.lang.String mModelName;
    private final java.lang.String mResultId;
    private final float[] mScores;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface Category {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface Type {
    }

    private TextClassifierEvent(android.view.textclassifier.TextClassifierEvent.Builder builder) {
        this.mEventCategory = builder.mEventCategory;
        this.mEventType = builder.mEventType;
        this.mEntityTypes = builder.mEntityTypes;
        this.mEventContext = builder.mEventContext;
        this.mResultId = builder.mResultId;
        this.mEventIndex = builder.mEventIndex;
        this.mScores = builder.mScores;
        this.mModelName = builder.mModelName;
        this.mActionIndices = builder.mActionIndices;
        this.mLocale = builder.mLocale;
        this.mExtras = builder.mExtras == null ? android.os.Bundle.EMPTY : builder.mExtras;
    }

    private TextClassifierEvent(android.os.Parcel parcel) {
        this.mEventCategory = parcel.readInt();
        this.mEventType = parcel.readInt();
        this.mEntityTypes = parcel.readStringArray();
        this.mEventContext = (android.view.textclassifier.TextClassificationContext) parcel.readParcelable(null, android.view.textclassifier.TextClassificationContext.class);
        this.mResultId = parcel.readString();
        this.mEventIndex = parcel.readInt();
        this.mScores = new float[parcel.readInt()];
        parcel.readFloatArray(this.mScores);
        this.mModelName = parcel.readString();
        this.mActionIndices = parcel.createIntArray();
        java.lang.String readString = parcel.readString();
        this.mLocale = readString != null ? android.icu.util.ULocale.forLanguageTag(readString) : null;
        this.mExtras = parcel.readBundle();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(getParcelToken());
        parcel.writeInt(this.mEventCategory);
        parcel.writeInt(this.mEventType);
        parcel.writeStringArray(this.mEntityTypes);
        parcel.writeParcelable(this.mEventContext, i);
        parcel.writeString(this.mResultId);
        parcel.writeInt(this.mEventIndex);
        parcel.writeInt(this.mScores.length);
        parcel.writeFloatArray(this.mScores);
        parcel.writeString(this.mModelName);
        parcel.writeIntArray(this.mActionIndices);
        parcel.writeString(this.mLocale == null ? null : this.mLocale.toLanguageTag());
        parcel.writeBundle(this.mExtras);
    }

    private int getParcelToken() {
        if (this instanceof android.view.textclassifier.TextClassifierEvent.TextSelectionEvent) {
            return 1;
        }
        if (this instanceof android.view.textclassifier.TextClassifierEvent.TextLinkifyEvent) {
            return 2;
        }
        if (this instanceof android.view.textclassifier.TextClassifierEvent.LanguageDetectionEvent) {
            return 4;
        }
        if (this instanceof android.view.textclassifier.TextClassifierEvent.ConversationActionsEvent) {
            return 3;
        }
        throw new java.lang.IllegalArgumentException("Unexpected type: " + getClass().getSimpleName());
    }

    public int getEventCategory() {
        return this.mEventCategory;
    }

    public int getEventType() {
        return this.mEventType;
    }

    public java.lang.String[] getEntityTypes() {
        return this.mEntityTypes;
    }

    public android.view.textclassifier.TextClassificationContext getEventContext() {
        return this.mEventContext;
    }

    void setEventContext(android.view.textclassifier.TextClassificationContext textClassificationContext) {
        this.mEventContext = textClassificationContext;
    }

    public java.lang.String getResultId() {
        return this.mResultId;
    }

    public int getEventIndex() {
        return this.mEventIndex;
    }

    public float[] getScores() {
        return this.mScores;
    }

    public java.lang.String getModelName() {
        return this.mModelName;
    }

    public int[] getActionIndices() {
        return this.mActionIndices;
    }

    public android.icu.util.ULocale getLocale() {
        return this.mLocale;
    }

    public android.os.Bundle getExtras() {
        return this.mExtras;
    }

    public java.lang.String toString() {
        java.lang.StringBuilder sb = new java.lang.StringBuilder(128);
        sb.append(getClass().getSimpleName());
        sb.append("{");
        sb.append("mEventCategory=").append(this.mEventCategory);
        sb.append(", mEventType=").append(this.mEventType);
        sb.append(", mEntityTypes=").append(java.util.Arrays.toString(this.mEntityTypes));
        sb.append(", mEventContext=").append(this.mEventContext);
        sb.append(", mResultId=").append(this.mResultId);
        sb.append(", mEventIndex=").append(this.mEventIndex);
        sb.append(", mExtras=").append(this.mExtras);
        sb.append(", mScores=").append(java.util.Arrays.toString(this.mScores));
        sb.append(", mModelName=").append(this.mModelName);
        sb.append(", mActionIndices=").append(java.util.Arrays.toString(this.mActionIndices));
        toString(sb);
        sb.append("}");
        return sb.toString();
    }

    void toString(java.lang.StringBuilder sb) {
    }

    public final android.view.textclassifier.SelectionEvent toSelectionEvent() {
        int i;
        int i2 = 2;
        switch (getEventCategory()) {
            case 1:
                i = 1;
                break;
            case 2:
                i = 2;
                break;
            default:
                return null;
        }
        android.view.textclassifier.SelectionEvent selectionEvent = new android.view.textclassifier.SelectionEvent(0, 0, 0, getEntityTypes().length > 0 ? getEntityTypes()[0] : "", 0, "");
        selectionEvent.setInvocationMethod(i);
        if (getEventContext() != null) {
            selectionEvent.setTextClassificationSessionContext(getEventContext());
        }
        selectionEvent.setSessionId(this.mHiddenTempSessionId);
        java.lang.String resultId = getResultId();
        selectionEvent.setResultId(resultId != null ? resultId : "");
        selectionEvent.setEventIndex(getEventIndex());
        switch (getEventType()) {
            case 1:
                i2 = 1;
                break;
            case 2:
                break;
            case 3:
                i2 = 3;
                break;
            case 4:
                i2 = 4;
                break;
            case 5:
                i2 = 5;
                break;
            case 6:
            case 7:
            default:
                i2 = 0;
                break;
            case 8:
                i2 = 100;
                break;
            case 9:
                i2 = 101;
                break;
            case 10:
                i2 = 102;
                break;
            case 11:
                i2 = 103;
                break;
            case 12:
                i2 = 104;
                break;
            case 13:
                i2 = 105;
                break;
            case 14:
                i2 = 106;
                break;
            case 15:
                i2 = 107;
                break;
            case 16:
                i2 = 108;
                break;
            case 17:
                i2 = 200;
                break;
            case 18:
                i2 = 201;
                break;
        }
        selectionEvent.setEventType(i2);
        if (this instanceof android.view.textclassifier.TextClassifierEvent.TextSelectionEvent) {
            android.view.textclassifier.TextClassifierEvent.TextSelectionEvent textSelectionEvent = (android.view.textclassifier.TextClassifierEvent.TextSelectionEvent) this;
            selectionEvent.setStart(textSelectionEvent.getRelativeWordStartIndex());
            selectionEvent.setEnd(textSelectionEvent.getRelativeWordEndIndex());
            selectionEvent.setSmartStart(textSelectionEvent.getRelativeSuggestedWordStartIndex());
            selectionEvent.setSmartEnd(textSelectionEvent.getRelativeSuggestedWordEndIndex());
        }
        return selectionEvent;
    }

    public static abstract class Builder<T extends android.view.textclassifier.TextClassifierEvent.Builder<T>> {
        private int[] mActionIndices;
        private java.lang.String[] mEntityTypes;
        private final int mEventCategory;
        private android.view.textclassifier.TextClassificationContext mEventContext;
        private int mEventIndex;
        private final int mEventType;
        private android.os.Bundle mExtras;
        private android.icu.util.ULocale mLocale;
        private java.lang.String mModelName;
        private java.lang.String mResultId;
        private float[] mScores;

        abstract T self();

        private Builder(int i, int i2) {
            this.mEntityTypes = new java.lang.String[0];
            this.mScores = new float[0];
            this.mActionIndices = new int[0];
            this.mEventCategory = i;
            this.mEventType = i2;
        }

        public T setEntityTypes(java.lang.String... strArr) {
            java.util.Objects.requireNonNull(strArr);
            this.mEntityTypes = new java.lang.String[strArr.length];
            java.lang.System.arraycopy(strArr, 0, this.mEntityTypes, 0, strArr.length);
            return self();
        }

        public T setEventContext(android.view.textclassifier.TextClassificationContext textClassificationContext) {
            this.mEventContext = textClassificationContext;
            return self();
        }

        public T setResultId(java.lang.String str) {
            this.mResultId = str;
            return self();
        }

        public T setEventIndex(int i) {
            this.mEventIndex = i;
            return self();
        }

        public T setScores(float... fArr) {
            java.util.Objects.requireNonNull(fArr);
            this.mScores = new float[fArr.length];
            java.lang.System.arraycopy(fArr, 0, this.mScores, 0, fArr.length);
            return self();
        }

        public T setModelName(java.lang.String str) {
            this.mModelName = str;
            return self();
        }

        public T setActionIndices(int... iArr) {
            this.mActionIndices = new int[iArr.length];
            java.lang.System.arraycopy(iArr, 0, this.mActionIndices, 0, iArr.length);
            return self();
        }

        public T setLocale(android.icu.util.ULocale uLocale) {
            this.mLocale = uLocale;
            return self();
        }

        public T setExtras(android.os.Bundle bundle) {
            this.mExtras = (android.os.Bundle) java.util.Objects.requireNonNull(bundle);
            return self();
        }
    }

    public static final class TextSelectionEvent extends android.view.textclassifier.TextClassifierEvent implements android.os.Parcelable {
        public static final android.os.Parcelable.Creator<android.view.textclassifier.TextClassifierEvent.TextSelectionEvent> CREATOR = new android.os.Parcelable.Creator<android.view.textclassifier.TextClassifierEvent.TextSelectionEvent>() { // from class: android.view.textclassifier.TextClassifierEvent.TextSelectionEvent.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.view.textclassifier.TextClassifierEvent.TextSelectionEvent createFromParcel(android.os.Parcel parcel) {
                parcel.readInt();
                return new android.view.textclassifier.TextClassifierEvent.TextSelectionEvent(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.view.textclassifier.TextClassifierEvent.TextSelectionEvent[] newArray(int i) {
                return new android.view.textclassifier.TextClassifierEvent.TextSelectionEvent[i];
            }
        };
        final int mRelativeSuggestedWordEndIndex;
        final int mRelativeSuggestedWordStartIndex;
        final int mRelativeWordEndIndex;
        final int mRelativeWordStartIndex;

        private TextSelectionEvent(android.view.textclassifier.TextClassifierEvent.TextSelectionEvent.Builder builder) {
            super(builder);
            this.mRelativeWordStartIndex = builder.mRelativeWordStartIndex;
            this.mRelativeWordEndIndex = builder.mRelativeWordEndIndex;
            this.mRelativeSuggestedWordStartIndex = builder.mRelativeSuggestedWordStartIndex;
            this.mRelativeSuggestedWordEndIndex = builder.mRelativeSuggestedWordEndIndex;
        }

        private TextSelectionEvent(android.os.Parcel parcel) {
            super(parcel);
            this.mRelativeWordStartIndex = parcel.readInt();
            this.mRelativeWordEndIndex = parcel.readInt();
            this.mRelativeSuggestedWordStartIndex = parcel.readInt();
            this.mRelativeSuggestedWordEndIndex = parcel.readInt();
        }

        @Override // android.view.textclassifier.TextClassifierEvent, android.os.Parcelable
        public void writeToParcel(android.os.Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeInt(this.mRelativeWordStartIndex);
            parcel.writeInt(this.mRelativeWordEndIndex);
            parcel.writeInt(this.mRelativeSuggestedWordStartIndex);
            parcel.writeInt(this.mRelativeSuggestedWordEndIndex);
        }

        public int getRelativeWordStartIndex() {
            return this.mRelativeWordStartIndex;
        }

        public int getRelativeWordEndIndex() {
            return this.mRelativeWordEndIndex;
        }

        public int getRelativeSuggestedWordStartIndex() {
            return this.mRelativeSuggestedWordStartIndex;
        }

        public int getRelativeSuggestedWordEndIndex() {
            return this.mRelativeSuggestedWordEndIndex;
        }

        @Override // android.view.textclassifier.TextClassifierEvent
        void toString(java.lang.StringBuilder sb) {
            sb.append(", getRelativeWordStartIndex=").append(this.mRelativeWordStartIndex);
            sb.append(", getRelativeWordEndIndex=").append(this.mRelativeWordEndIndex);
            sb.append(", getRelativeSuggestedWordStartIndex=").append(this.mRelativeSuggestedWordStartIndex);
            sb.append(", getRelativeSuggestedWordEndIndex=").append(this.mRelativeSuggestedWordEndIndex);
        }

        public static final class Builder extends android.view.textclassifier.TextClassifierEvent.Builder<android.view.textclassifier.TextClassifierEvent.TextSelectionEvent.Builder> {
            int mRelativeSuggestedWordEndIndex;
            int mRelativeSuggestedWordStartIndex;
            int mRelativeWordEndIndex;
            int mRelativeWordStartIndex;

            public Builder(int i) {
                super(1, i);
            }

            public android.view.textclassifier.TextClassifierEvent.TextSelectionEvent.Builder setRelativeWordStartIndex(int i) {
                this.mRelativeWordStartIndex = i;
                return this;
            }

            public android.view.textclassifier.TextClassifierEvent.TextSelectionEvent.Builder setRelativeWordEndIndex(int i) {
                this.mRelativeWordEndIndex = i;
                return this;
            }

            public android.view.textclassifier.TextClassifierEvent.TextSelectionEvent.Builder setRelativeSuggestedWordStartIndex(int i) {
                this.mRelativeSuggestedWordStartIndex = i;
                return this;
            }

            public android.view.textclassifier.TextClassifierEvent.TextSelectionEvent.Builder setRelativeSuggestedWordEndIndex(int i) {
                this.mRelativeSuggestedWordEndIndex = i;
                return this;
            }

            /* JADX INFO: Access modifiers changed from: package-private */
            @Override // android.view.textclassifier.TextClassifierEvent.Builder
            public android.view.textclassifier.TextClassifierEvent.TextSelectionEvent.Builder self() {
                return this;
            }

            public android.view.textclassifier.TextClassifierEvent.TextSelectionEvent build() {
                return new android.view.textclassifier.TextClassifierEvent.TextSelectionEvent(this);
            }
        }
    }

    public static final class TextLinkifyEvent extends android.view.textclassifier.TextClassifierEvent implements android.os.Parcelable {
        public static final android.os.Parcelable.Creator<android.view.textclassifier.TextClassifierEvent.TextLinkifyEvent> CREATOR = new android.os.Parcelable.Creator<android.view.textclassifier.TextClassifierEvent.TextLinkifyEvent>() { // from class: android.view.textclassifier.TextClassifierEvent.TextLinkifyEvent.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.view.textclassifier.TextClassifierEvent.TextLinkifyEvent createFromParcel(android.os.Parcel parcel) {
                parcel.readInt();
                return new android.view.textclassifier.TextClassifierEvent.TextLinkifyEvent(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.view.textclassifier.TextClassifierEvent.TextLinkifyEvent[] newArray(int i) {
                return new android.view.textclassifier.TextClassifierEvent.TextLinkifyEvent[i];
            }
        };

        private TextLinkifyEvent(android.os.Parcel parcel) {
            super(parcel);
        }

        private TextLinkifyEvent(android.view.textclassifier.TextClassifierEvent.TextLinkifyEvent.Builder builder) {
            super(builder);
        }

        public static final class Builder extends android.view.textclassifier.TextClassifierEvent.Builder<android.view.textclassifier.TextClassifierEvent.TextLinkifyEvent.Builder> {
            public Builder(int i) {
                super(2, i);
            }

            /* JADX INFO: Access modifiers changed from: package-private */
            @Override // android.view.textclassifier.TextClassifierEvent.Builder
            public android.view.textclassifier.TextClassifierEvent.TextLinkifyEvent.Builder self() {
                return this;
            }

            public android.view.textclassifier.TextClassifierEvent.TextLinkifyEvent build() {
                return new android.view.textclassifier.TextClassifierEvent.TextLinkifyEvent(this);
            }
        }
    }

    public static final class LanguageDetectionEvent extends android.view.textclassifier.TextClassifierEvent implements android.os.Parcelable {
        public static final android.os.Parcelable.Creator<android.view.textclassifier.TextClassifierEvent.LanguageDetectionEvent> CREATOR = new android.os.Parcelable.Creator<android.view.textclassifier.TextClassifierEvent.LanguageDetectionEvent>() { // from class: android.view.textclassifier.TextClassifierEvent.LanguageDetectionEvent.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.view.textclassifier.TextClassifierEvent.LanguageDetectionEvent createFromParcel(android.os.Parcel parcel) {
                parcel.readInt();
                return new android.view.textclassifier.TextClassifierEvent.LanguageDetectionEvent(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.view.textclassifier.TextClassifierEvent.LanguageDetectionEvent[] newArray(int i) {
                return new android.view.textclassifier.TextClassifierEvent.LanguageDetectionEvent[i];
            }
        };

        private LanguageDetectionEvent(android.os.Parcel parcel) {
            super(parcel);
        }

        private LanguageDetectionEvent(android.view.textclassifier.TextClassifierEvent.LanguageDetectionEvent.Builder builder) {
            super(builder);
        }

        public static final class Builder extends android.view.textclassifier.TextClassifierEvent.Builder<android.view.textclassifier.TextClassifierEvent.LanguageDetectionEvent.Builder> {
            public Builder(int i) {
                super(4, i);
            }

            /* JADX INFO: Access modifiers changed from: package-private */
            @Override // android.view.textclassifier.TextClassifierEvent.Builder
            public android.view.textclassifier.TextClassifierEvent.LanguageDetectionEvent.Builder self() {
                return this;
            }

            public android.view.textclassifier.TextClassifierEvent.LanguageDetectionEvent build() {
                return new android.view.textclassifier.TextClassifierEvent.LanguageDetectionEvent(this);
            }
        }
    }

    public static final class ConversationActionsEvent extends android.view.textclassifier.TextClassifierEvent implements android.os.Parcelable {
        public static final android.os.Parcelable.Creator<android.view.textclassifier.TextClassifierEvent.ConversationActionsEvent> CREATOR = new android.os.Parcelable.Creator<android.view.textclassifier.TextClassifierEvent.ConversationActionsEvent>() { // from class: android.view.textclassifier.TextClassifierEvent.ConversationActionsEvent.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.view.textclassifier.TextClassifierEvent.ConversationActionsEvent createFromParcel(android.os.Parcel parcel) {
                parcel.readInt();
                return new android.view.textclassifier.TextClassifierEvent.ConversationActionsEvent(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.view.textclassifier.TextClassifierEvent.ConversationActionsEvent[] newArray(int i) {
                return new android.view.textclassifier.TextClassifierEvent.ConversationActionsEvent[i];
            }
        };

        private ConversationActionsEvent(android.os.Parcel parcel) {
            super(parcel);
        }

        private ConversationActionsEvent(android.view.textclassifier.TextClassifierEvent.ConversationActionsEvent.Builder builder) {
            super(builder);
        }

        public static final class Builder extends android.view.textclassifier.TextClassifierEvent.Builder<android.view.textclassifier.TextClassifierEvent.ConversationActionsEvent.Builder> {
            public Builder(int i) {
                super(3, i);
            }

            /* JADX INFO: Access modifiers changed from: package-private */
            @Override // android.view.textclassifier.TextClassifierEvent.Builder
            public android.view.textclassifier.TextClassifierEvent.ConversationActionsEvent.Builder self() {
                return this;
            }

            public android.view.textclassifier.TextClassifierEvent.ConversationActionsEvent build() {
                return new android.view.textclassifier.TextClassifierEvent.ConversationActionsEvent(this);
            }
        }
    }
}
