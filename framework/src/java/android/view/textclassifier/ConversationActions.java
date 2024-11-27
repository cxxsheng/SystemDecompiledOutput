package android.view.textclassifier;

/* loaded from: classes4.dex */
public final class ConversationActions implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.view.textclassifier.ConversationActions> CREATOR = new android.os.Parcelable.Creator<android.view.textclassifier.ConversationActions>() { // from class: android.view.textclassifier.ConversationActions.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.view.textclassifier.ConversationActions createFromParcel(android.os.Parcel parcel) {
            return new android.view.textclassifier.ConversationActions(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.view.textclassifier.ConversationActions[] newArray(int i) {
            return new android.view.textclassifier.ConversationActions[i];
        }
    };
    private final java.util.List<android.view.textclassifier.ConversationAction> mConversationActions;
    private final java.lang.String mId;

    public ConversationActions(java.util.List<android.view.textclassifier.ConversationAction> list, java.lang.String str) {
        this.mConversationActions = java.util.Collections.unmodifiableList((java.util.List) java.util.Objects.requireNonNull(list));
        this.mId = str;
    }

    private ConversationActions(android.os.Parcel parcel) {
        this.mConversationActions = java.util.Collections.unmodifiableList(parcel.createTypedArrayList(android.view.textclassifier.ConversationAction.CREATOR));
        this.mId = parcel.readString();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeTypedList(this.mConversationActions);
        parcel.writeString(this.mId);
    }

    public java.util.List<android.view.textclassifier.ConversationAction> getConversationActions() {
        return this.mConversationActions;
    }

    public java.lang.String getId() {
        return this.mId;
    }

    public static final class Message implements android.os.Parcelable {
        private final android.app.Person mAuthor;
        private final android.os.Bundle mExtras;
        private final java.time.ZonedDateTime mReferenceTime;
        private final java.lang.CharSequence mText;
        public static final android.app.Person PERSON_USER_SELF = new android.app.Person.Builder().setKey("text-classifier-conversation-actions-user-self").build();
        public static final android.app.Person PERSON_USER_OTHERS = new android.app.Person.Builder().setKey("text-classifier-conversation-actions-user-others").build();
        public static final android.os.Parcelable.Creator<android.view.textclassifier.ConversationActions.Message> CREATOR = new android.os.Parcelable.Creator<android.view.textclassifier.ConversationActions.Message>() { // from class: android.view.textclassifier.ConversationActions.Message.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.view.textclassifier.ConversationActions.Message createFromParcel(android.os.Parcel parcel) {
                return new android.view.textclassifier.ConversationActions.Message(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.view.textclassifier.ConversationActions.Message[] newArray(int i) {
                return new android.view.textclassifier.ConversationActions.Message[i];
            }
        };

        private Message(android.app.Person person, java.time.ZonedDateTime zonedDateTime, java.lang.CharSequence charSequence, android.os.Bundle bundle) {
            this.mAuthor = person;
            this.mReferenceTime = zonedDateTime;
            this.mText = charSequence;
            this.mExtras = (android.os.Bundle) java.util.Objects.requireNonNull(bundle);
        }

        private Message(android.os.Parcel parcel) {
            this.mAuthor = (android.app.Person) parcel.readParcelable(null, android.app.Person.class);
            this.mReferenceTime = parcel.readInt() != 0 ? java.time.ZonedDateTime.parse(parcel.readString(), java.time.format.DateTimeFormatter.ISO_ZONED_DATE_TIME) : null;
            this.mText = parcel.readCharSequence();
            this.mExtras = parcel.readBundle();
        }

        @Override // android.os.Parcelable
        public void writeToParcel(android.os.Parcel parcel, int i) {
            parcel.writeParcelable(this.mAuthor, i);
            parcel.writeInt(this.mReferenceTime != null ? 1 : 0);
            if (this.mReferenceTime != null) {
                parcel.writeString(this.mReferenceTime.format(java.time.format.DateTimeFormatter.ISO_ZONED_DATE_TIME));
            }
            parcel.writeCharSequence(this.mText);
            parcel.writeBundle(this.mExtras);
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        public android.app.Person getAuthor() {
            return this.mAuthor;
        }

        public java.time.ZonedDateTime getReferenceTime() {
            return this.mReferenceTime;
        }

        public java.lang.CharSequence getText() {
            return this.mText;
        }

        public android.os.Bundle getExtras() {
            return this.mExtras;
        }

        public static final class Builder {
            private android.app.Person mAuthor;
            private android.os.Bundle mExtras;
            private java.time.ZonedDateTime mReferenceTime;
            private java.lang.CharSequence mText;

            public Builder(android.app.Person person) {
                this.mAuthor = (android.app.Person) java.util.Objects.requireNonNull(person);
            }

            public android.view.textclassifier.ConversationActions.Message.Builder setText(java.lang.CharSequence charSequence) {
                this.mText = charSequence;
                return this;
            }

            public android.view.textclassifier.ConversationActions.Message.Builder setReferenceTime(java.time.ZonedDateTime zonedDateTime) {
                this.mReferenceTime = zonedDateTime;
                return this;
            }

            public android.view.textclassifier.ConversationActions.Message.Builder setExtras(android.os.Bundle bundle) {
                this.mExtras = bundle;
                return this;
            }

            public android.view.textclassifier.ConversationActions.Message build() {
                return new android.view.textclassifier.ConversationActions.Message(this.mAuthor, this.mReferenceTime, this.mText == null ? null : new android.text.SpannedString(this.mText), this.mExtras == null ? android.os.Bundle.EMPTY : this.mExtras);
            }
        }
    }

    public static final class Request implements android.os.Parcelable {
        public static final android.os.Parcelable.Creator<android.view.textclassifier.ConversationActions.Request> CREATOR = new android.os.Parcelable.Creator<android.view.textclassifier.ConversationActions.Request>() { // from class: android.view.textclassifier.ConversationActions.Request.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.view.textclassifier.ConversationActions.Request createFromParcel(android.os.Parcel parcel) {
                return android.view.textclassifier.ConversationActions.Request.readFromParcel(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.view.textclassifier.ConversationActions.Request[] newArray(int i) {
                return new android.view.textclassifier.ConversationActions.Request[i];
            }
        };
        public static final java.lang.String HINT_FOR_IN_APP = "in_app";
        public static final java.lang.String HINT_FOR_NOTIFICATION = "notification";
        private final java.util.List<android.view.textclassifier.ConversationActions.Message> mConversation;
        private android.os.Bundle mExtras;
        private final java.util.List<java.lang.String> mHints;
        private final int mMaxSuggestions;
        private android.view.textclassifier.SystemTextClassifierMetadata mSystemTcMetadata;
        private final android.view.textclassifier.TextClassifier.EntityConfig mTypeConfig;

        @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
        public @interface Hint {
        }

        private Request(java.util.List<android.view.textclassifier.ConversationActions.Message> list, android.view.textclassifier.TextClassifier.EntityConfig entityConfig, int i, java.util.List<java.lang.String> list2, android.os.Bundle bundle) {
            this.mConversation = (java.util.List) java.util.Objects.requireNonNull(list);
            this.mTypeConfig = (android.view.textclassifier.TextClassifier.EntityConfig) java.util.Objects.requireNonNull(entityConfig);
            this.mMaxSuggestions = i;
            this.mHints = list2;
            this.mExtras = bundle;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static android.view.textclassifier.ConversationActions.Request readFromParcel(android.os.Parcel parcel) {
            java.util.ArrayList arrayList = new java.util.ArrayList();
            parcel.readParcelableList(arrayList, null, android.view.textclassifier.ConversationActions.Message.class);
            android.view.textclassifier.TextClassifier.EntityConfig entityConfig = (android.view.textclassifier.TextClassifier.EntityConfig) parcel.readParcelable(null, android.view.textclassifier.TextClassifier.EntityConfig.class);
            int readInt = parcel.readInt();
            java.util.ArrayList arrayList2 = new java.util.ArrayList();
            parcel.readStringList(arrayList2);
            android.os.Bundle readBundle = parcel.readBundle();
            android.view.textclassifier.SystemTextClassifierMetadata systemTextClassifierMetadata = (android.view.textclassifier.SystemTextClassifierMetadata) parcel.readParcelable(null, android.view.textclassifier.SystemTextClassifierMetadata.class);
            android.view.textclassifier.ConversationActions.Request request = new android.view.textclassifier.ConversationActions.Request(arrayList, entityConfig, readInt, arrayList2, readBundle);
            request.setSystemTextClassifierMetadata(systemTextClassifierMetadata);
            return request;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(android.os.Parcel parcel, int i) {
            parcel.writeParcelableList(this.mConversation, i);
            parcel.writeParcelable(this.mTypeConfig, i);
            parcel.writeInt(this.mMaxSuggestions);
            parcel.writeStringList(this.mHints);
            parcel.writeBundle(this.mExtras);
            parcel.writeParcelable(this.mSystemTcMetadata, i);
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        public android.view.textclassifier.TextClassifier.EntityConfig getTypeConfig() {
            return this.mTypeConfig;
        }

        public java.util.List<android.view.textclassifier.ConversationActions.Message> getConversation() {
            return this.mConversation;
        }

        public int getMaxSuggestions() {
            return this.mMaxSuggestions;
        }

        public java.util.List<java.lang.String> getHints() {
            return this.mHints;
        }

        public java.lang.String getCallingPackageName() {
            if (this.mSystemTcMetadata != null) {
                return this.mSystemTcMetadata.getCallingPackageName();
            }
            return null;
        }

        void setSystemTextClassifierMetadata(android.view.textclassifier.SystemTextClassifierMetadata systemTextClassifierMetadata) {
            this.mSystemTcMetadata = systemTextClassifierMetadata;
        }

        public android.view.textclassifier.SystemTextClassifierMetadata getSystemTextClassifierMetadata() {
            return this.mSystemTcMetadata;
        }

        public android.os.Bundle getExtras() {
            return this.mExtras;
        }

        public static final class Builder {
            private java.util.List<android.view.textclassifier.ConversationActions.Message> mConversation;
            private android.os.Bundle mExtras;
            private java.util.List<java.lang.String> mHints;
            private int mMaxSuggestions = -1;
            private android.view.textclassifier.TextClassifier.EntityConfig mTypeConfig;

            public Builder(java.util.List<android.view.textclassifier.ConversationActions.Message> list) {
                this.mConversation = (java.util.List) java.util.Objects.requireNonNull(list);
            }

            public android.view.textclassifier.ConversationActions.Request.Builder setHints(java.util.List<java.lang.String> list) {
                this.mHints = list;
                return this;
            }

            public android.view.textclassifier.ConversationActions.Request.Builder setTypeConfig(android.view.textclassifier.TextClassifier.EntityConfig entityConfig) {
                this.mTypeConfig = entityConfig;
                return this;
            }

            public android.view.textclassifier.ConversationActions.Request.Builder setMaxSuggestions(int i) {
                if (i < -1) {
                    throw new java.lang.IllegalArgumentException("maxSuggestions has to be greater than or equal to -1.");
                }
                this.mMaxSuggestions = i;
                return this;
            }

            public android.view.textclassifier.ConversationActions.Request.Builder setExtras(android.os.Bundle bundle) {
                this.mExtras = bundle;
                return this;
            }

            public android.view.textclassifier.ConversationActions.Request build() {
                android.view.textclassifier.TextClassifier.EntityConfig entityConfig;
                java.util.List unmodifiableList;
                java.util.List unmodifiableList2 = java.util.Collections.unmodifiableList(this.mConversation);
                if (this.mTypeConfig == null) {
                    entityConfig = new android.view.textclassifier.TextClassifier.EntityConfig.Builder().build();
                } else {
                    entityConfig = this.mTypeConfig;
                }
                int i = this.mMaxSuggestions;
                if (this.mHints == null) {
                    unmodifiableList = java.util.Collections.emptyList();
                } else {
                    unmodifiableList = java.util.Collections.unmodifiableList(this.mHints);
                }
                return new android.view.textclassifier.ConversationActions.Request(unmodifiableList2, entityConfig, i, unmodifiableList, this.mExtras == null ? android.os.Bundle.EMPTY : this.mExtras);
            }
        }
    }
}
