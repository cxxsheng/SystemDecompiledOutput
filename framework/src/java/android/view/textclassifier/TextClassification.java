package android.view.textclassifier;

/* loaded from: classes4.dex */
public final class TextClassification implements android.os.Parcelable {
    private static final java.lang.String LOG_TAG = "TextClassification";
    private static final int MAX_LEGACY_ICON_SIZE = 192;
    private final java.util.List<android.app.RemoteAction> mActions;
    private final android.view.textclassifier.EntityConfidence mEntityConfidence;
    private final android.os.Bundle mExtras;
    private final java.lang.String mId;
    private final android.graphics.drawable.Drawable mLegacyIcon;
    private final android.content.Intent mLegacyIntent;
    private final java.lang.String mLegacyLabel;
    private final android.view.View.OnClickListener mLegacyOnClickListener;
    private final java.lang.String mText;
    public static final android.view.textclassifier.TextClassification EMPTY = new android.view.textclassifier.TextClassification.Builder().build();
    public static final android.os.Parcelable.Creator<android.view.textclassifier.TextClassification> CREATOR = new android.os.Parcelable.Creator<android.view.textclassifier.TextClassification>() { // from class: android.view.textclassifier.TextClassification.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.view.textclassifier.TextClassification createFromParcel(android.os.Parcel parcel) {
            return new android.view.textclassifier.TextClassification(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.view.textclassifier.TextClassification[] newArray(int i) {
            return new android.view.textclassifier.TextClassification[i];
        }
    };

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    private @interface IntentType {
        public static final int ACTIVITY = 0;
        public static final int SERVICE = 1;
        public static final int UNSUPPORTED = -1;
    }

    private TextClassification(java.lang.String str, android.graphics.drawable.Drawable drawable, java.lang.String str2, android.content.Intent intent, android.view.View.OnClickListener onClickListener, java.util.List<android.app.RemoteAction> list, android.view.textclassifier.EntityConfidence entityConfidence, java.lang.String str3, android.os.Bundle bundle) {
        this.mText = str;
        this.mLegacyIcon = drawable;
        this.mLegacyLabel = str2;
        this.mLegacyIntent = intent;
        this.mLegacyOnClickListener = onClickListener;
        this.mActions = java.util.Collections.unmodifiableList(list);
        this.mEntityConfidence = (android.view.textclassifier.EntityConfidence) java.util.Objects.requireNonNull(entityConfidence);
        this.mId = str3;
        this.mExtras = bundle;
    }

    public java.lang.String getText() {
        return this.mText;
    }

    public int getEntityCount() {
        return this.mEntityConfidence.getEntities().size();
    }

    public java.lang.String getEntity(int i) {
        return this.mEntityConfidence.getEntities().get(i);
    }

    public float getConfidenceScore(java.lang.String str) {
        return this.mEntityConfidence.getConfidenceScore(str);
    }

    public java.util.List<android.app.RemoteAction> getActions() {
        return this.mActions;
    }

    @java.lang.Deprecated
    public android.graphics.drawable.Drawable getIcon() {
        return this.mLegacyIcon;
    }

    @java.lang.Deprecated
    public java.lang.CharSequence getLabel() {
        return this.mLegacyLabel;
    }

    @java.lang.Deprecated
    public android.content.Intent getIntent() {
        return this.mLegacyIntent;
    }

    public android.view.View.OnClickListener getOnClickListener() {
        return this.mLegacyOnClickListener;
    }

    public java.lang.String getId() {
        return this.mId;
    }

    public android.os.Bundle getExtras() {
        return this.mExtras;
    }

    public android.view.textclassifier.TextClassification.Builder toBuilder() {
        return new android.view.textclassifier.TextClassification.Builder().setId(this.mId).setText(this.mText).addActions(this.mActions).setEntityConfidence(this.mEntityConfidence).setIcon(this.mLegacyIcon).setLabel(this.mLegacyLabel).setIntent(this.mLegacyIntent).setOnClickListener(this.mLegacyOnClickListener).setExtras(this.mExtras);
    }

    public java.lang.String toString() {
        return java.lang.String.format(java.util.Locale.US, "TextClassification {text=%s, entities=%s, actions=%s, id=%s, extras=%s}", this.mText, this.mEntityConfidence, this.mActions, this.mId, this.mExtras);
    }

    public static android.view.View.OnClickListener createIntentOnClickListener(final android.app.PendingIntent pendingIntent) {
        java.util.Objects.requireNonNull(pendingIntent);
        return new android.view.View.OnClickListener() { // from class: android.view.textclassifier.TextClassification$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(android.view.View view) {
                android.view.textclassifier.TextClassification.lambda$createIntentOnClickListener$0(android.app.PendingIntent.this, view);
            }
        };
    }

    static /* synthetic */ void lambda$createIntentOnClickListener$0(android.app.PendingIntent pendingIntent, android.view.View view) {
        try {
            pendingIntent.send(android.app.ActivityOptions.makeBasic().setPendingIntentBackgroundActivityStartMode(1).toBundle());
        } catch (android.app.PendingIntent.CanceledException e) {
            android.view.textclassifier.Log.e(LOG_TAG, "Error sending PendingIntent", e);
        }
    }

    public static android.app.PendingIntent createPendingIntent(android.content.Context context, android.content.Intent intent, int i) {
        return android.app.PendingIntent.getActivity(context, i, intent, android.media.audio.Enums.AUDIO_FORMAT_DTS_HD);
    }

    public static final class Builder {
        private android.os.Bundle mExtras;
        private java.lang.String mId;
        private android.graphics.drawable.Drawable mLegacyIcon;
        private android.content.Intent mLegacyIntent;
        private java.lang.String mLegacyLabel;
        private android.view.View.OnClickListener mLegacyOnClickListener;
        private java.lang.String mText;
        private final java.util.List<android.app.RemoteAction> mActions = new java.util.ArrayList();
        private final java.util.Map<java.lang.String, java.lang.Float> mTypeScoreMap = new android.util.ArrayMap();

        public android.view.textclassifier.TextClassification.Builder setText(java.lang.String str) {
            this.mText = str;
            return this;
        }

        public android.view.textclassifier.TextClassification.Builder setEntityType(java.lang.String str, float f) {
            this.mTypeScoreMap.put(str, java.lang.Float.valueOf(f));
            return this;
        }

        android.view.textclassifier.TextClassification.Builder setEntityConfidence(android.view.textclassifier.EntityConfidence entityConfidence) {
            this.mTypeScoreMap.clear();
            this.mTypeScoreMap.putAll(entityConfidence.toMap());
            return this;
        }

        public android.view.textclassifier.TextClassification.Builder clearEntityTypes() {
            this.mTypeScoreMap.clear();
            return this;
        }

        public android.view.textclassifier.TextClassification.Builder addAction(android.app.RemoteAction remoteAction) {
            com.android.internal.util.Preconditions.checkArgument(remoteAction != null);
            this.mActions.add(remoteAction);
            return this;
        }

        public android.view.textclassifier.TextClassification.Builder addActions(java.util.Collection<android.app.RemoteAction> collection) {
            java.util.Objects.requireNonNull(collection);
            this.mActions.addAll(collection);
            return this;
        }

        public android.view.textclassifier.TextClassification.Builder clearActions() {
            this.mActions.clear();
            return this;
        }

        @java.lang.Deprecated
        public android.view.textclassifier.TextClassification.Builder setIcon(android.graphics.drawable.Drawable drawable) {
            this.mLegacyIcon = drawable;
            return this;
        }

        @java.lang.Deprecated
        public android.view.textclassifier.TextClassification.Builder setLabel(java.lang.String str) {
            this.mLegacyLabel = str;
            return this;
        }

        @java.lang.Deprecated
        public android.view.textclassifier.TextClassification.Builder setIntent(android.content.Intent intent) {
            this.mLegacyIntent = intent;
            return this;
        }

        @java.lang.Deprecated
        public android.view.textclassifier.TextClassification.Builder setOnClickListener(android.view.View.OnClickListener onClickListener) {
            this.mLegacyOnClickListener = onClickListener;
            return this;
        }

        public android.view.textclassifier.TextClassification.Builder setId(java.lang.String str) {
            this.mId = str;
            return this;
        }

        public android.view.textclassifier.TextClassification.Builder setExtras(android.os.Bundle bundle) {
            this.mExtras = bundle;
            return this;
        }

        public android.view.textclassifier.TextClassification build() {
            return new android.view.textclassifier.TextClassification(this.mText, this.mLegacyIcon, this.mLegacyLabel, this.mLegacyIntent, this.mLegacyOnClickListener, this.mActions, new android.view.textclassifier.EntityConfidence(this.mTypeScoreMap), this.mId, this.mExtras == null ? android.os.Bundle.EMPTY : this.mExtras);
        }
    }

    public static final class Request implements android.os.Parcelable {
        public static final android.os.Parcelable.Creator<android.view.textclassifier.TextClassification.Request> CREATOR = new android.os.Parcelable.Creator<android.view.textclassifier.TextClassification.Request>() { // from class: android.view.textclassifier.TextClassification.Request.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.view.textclassifier.TextClassification.Request createFromParcel(android.os.Parcel parcel) {
                return android.view.textclassifier.TextClassification.Request.readFromParcel(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.view.textclassifier.TextClassification.Request[] newArray(int i) {
                return new android.view.textclassifier.TextClassification.Request[i];
            }
        };
        private final android.os.LocaleList mDefaultLocales;
        private final int mEndIndex;
        private final android.os.Bundle mExtras;
        private final java.time.ZonedDateTime mReferenceTime;
        private final int mStartIndex;
        private android.view.textclassifier.SystemTextClassifierMetadata mSystemTcMetadata;
        private final java.lang.CharSequence mText;

        private Request(java.lang.CharSequence charSequence, int i, int i2, android.os.LocaleList localeList, java.time.ZonedDateTime zonedDateTime, android.os.Bundle bundle) {
            this.mText = charSequence;
            this.mStartIndex = i;
            this.mEndIndex = i2;
            this.mDefaultLocales = localeList;
            this.mReferenceTime = zonedDateTime;
            this.mExtras = bundle;
        }

        public java.lang.CharSequence getText() {
            return this.mText;
        }

        public int getStartIndex() {
            return this.mStartIndex;
        }

        public int getEndIndex() {
            return this.mEndIndex;
        }

        public android.os.LocaleList getDefaultLocales() {
            return this.mDefaultLocales;
        }

        public java.time.ZonedDateTime getReferenceTime() {
            return this.mReferenceTime;
        }

        public java.lang.String getCallingPackageName() {
            if (this.mSystemTcMetadata != null) {
                return this.mSystemTcMetadata.getCallingPackageName();
            }
            return null;
        }

        public void setSystemTextClassifierMetadata(android.view.textclassifier.SystemTextClassifierMetadata systemTextClassifierMetadata) {
            this.mSystemTcMetadata = systemTextClassifierMetadata;
        }

        public android.view.textclassifier.SystemTextClassifierMetadata getSystemTextClassifierMetadata() {
            return this.mSystemTcMetadata;
        }

        public android.os.Bundle getExtras() {
            return this.mExtras;
        }

        public static final class Builder {
            private android.os.LocaleList mDefaultLocales;
            private final int mEndIndex;
            private android.os.Bundle mExtras;
            private java.time.ZonedDateTime mReferenceTime;
            private final int mStartIndex;
            private final java.lang.CharSequence mText;

            public Builder(java.lang.CharSequence charSequence, int i, int i2) {
                android.view.textclassifier.TextClassifier.Utils.checkArgument(charSequence, i, i2);
                this.mText = charSequence;
                this.mStartIndex = i;
                this.mEndIndex = i2;
            }

            public android.view.textclassifier.TextClassification.Request.Builder setDefaultLocales(android.os.LocaleList localeList) {
                this.mDefaultLocales = localeList;
                return this;
            }

            public android.view.textclassifier.TextClassification.Request.Builder setReferenceTime(java.time.ZonedDateTime zonedDateTime) {
                this.mReferenceTime = zonedDateTime;
                return this;
            }

            public android.view.textclassifier.TextClassification.Request.Builder setExtras(android.os.Bundle bundle) {
                this.mExtras = bundle;
                return this;
            }

            public android.view.textclassifier.TextClassification.Request build() {
                return new android.view.textclassifier.TextClassification.Request(new android.text.SpannedString(this.mText), this.mStartIndex, this.mEndIndex, this.mDefaultLocales, this.mReferenceTime, this.mExtras == null ? android.os.Bundle.EMPTY : this.mExtras);
            }
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(android.os.Parcel parcel, int i) {
            parcel.writeCharSequence(this.mText);
            parcel.writeInt(this.mStartIndex);
            parcel.writeInt(this.mEndIndex);
            parcel.writeParcelable(this.mDefaultLocales, i);
            parcel.writeString(this.mReferenceTime == null ? null : this.mReferenceTime.toString());
            parcel.writeBundle(this.mExtras);
            parcel.writeParcelable(this.mSystemTcMetadata, i);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static android.view.textclassifier.TextClassification.Request readFromParcel(android.os.Parcel parcel) {
            java.lang.CharSequence readCharSequence = parcel.readCharSequence();
            int readInt = parcel.readInt();
            int readInt2 = parcel.readInt();
            android.os.LocaleList localeList = (android.os.LocaleList) parcel.readParcelable(null, android.os.LocaleList.class);
            java.lang.String readString = parcel.readString();
            java.time.ZonedDateTime parse = readString == null ? null : java.time.ZonedDateTime.parse(readString);
            android.os.Bundle readBundle = parcel.readBundle();
            android.view.textclassifier.SystemTextClassifierMetadata systemTextClassifierMetadata = (android.view.textclassifier.SystemTextClassifierMetadata) parcel.readParcelable(null, android.view.textclassifier.SystemTextClassifierMetadata.class);
            android.view.textclassifier.TextClassification.Request request = new android.view.textclassifier.TextClassification.Request(readCharSequence, readInt, readInt2, localeList, parse, readBundle);
            request.setSystemTextClassifierMetadata(systemTextClassifierMetadata);
            return request;
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeString(this.mText);
        parcel.writeTypedList(this.mActions);
        this.mEntityConfidence.writeToParcel(parcel, i);
        parcel.writeString(this.mId);
        parcel.writeBundle(this.mExtras);
    }

    private TextClassification(android.os.Parcel parcel) {
        this.mText = parcel.readString();
        this.mActions = parcel.createTypedArrayList(android.app.RemoteAction.CREATOR);
        if (!this.mActions.isEmpty()) {
            android.app.RemoteAction remoteAction = this.mActions.get(0);
            this.mLegacyIcon = maybeLoadDrawable(remoteAction.getIcon());
            this.mLegacyLabel = remoteAction.getTitle().toString();
            this.mLegacyOnClickListener = createIntentOnClickListener(this.mActions.get(0).getActionIntent());
        } else {
            this.mLegacyIcon = null;
            this.mLegacyLabel = null;
            this.mLegacyOnClickListener = null;
        }
        this.mLegacyIntent = null;
        this.mEntityConfidence = android.view.textclassifier.EntityConfidence.CREATOR.createFromParcel(parcel);
        this.mId = parcel.readString();
        this.mExtras = parcel.readBundle();
    }

    private static android.graphics.drawable.Drawable maybeLoadDrawable(android.graphics.drawable.Icon icon) {
        if (icon == null) {
            return null;
        }
        switch (icon.getType()) {
        }
        return null;
    }
}
