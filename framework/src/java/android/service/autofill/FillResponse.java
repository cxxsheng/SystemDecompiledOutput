package android.service.autofill;

/* loaded from: classes3.dex */
public final class FillResponse implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.service.autofill.FillResponse> CREATOR = new android.os.Parcelable.Creator<android.service.autofill.FillResponse>() { // from class: android.service.autofill.FillResponse.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.service.autofill.FillResponse createFromParcel(android.os.Parcel parcel) {
            android.service.autofill.FillResponse.Builder builder = new android.service.autofill.FillResponse.Builder();
            android.content.pm.ParceledListSlice parceledListSlice = (android.content.pm.ParceledListSlice) parcel.readParcelable(null, android.content.pm.ParceledListSlice.class);
            java.util.List list = parceledListSlice != null ? parceledListSlice.getList() : null;
            int size = list != null ? list.size() : 0;
            for (int i = 0; i < size; i++) {
                builder.addDataset((android.service.autofill.Dataset) list.get(i));
            }
            builder.setSaveInfo((android.service.autofill.SaveInfo) parcel.readParcelable(null, android.service.autofill.SaveInfo.class));
            builder.setClientState((android.os.Bundle) parcel.readParcelable(null, android.os.Bundle.class));
            android.view.autofill.AutofillId[] autofillIdArr = (android.view.autofill.AutofillId[]) parcel.readParcelableArray(null, android.view.autofill.AutofillId.class);
            android.content.IntentSender intentSender = (android.content.IntentSender) parcel.readParcelable(null, android.content.IntentSender.class);
            android.widget.RemoteViews remoteViews = (android.widget.RemoteViews) parcel.readParcelable(null, android.widget.RemoteViews.class);
            android.service.autofill.InlinePresentation inlinePresentation = (android.service.autofill.InlinePresentation) parcel.readParcelable(null, android.service.autofill.InlinePresentation.class);
            android.service.autofill.InlinePresentation inlinePresentation2 = (android.service.autofill.InlinePresentation) parcel.readParcelable(null, android.service.autofill.InlinePresentation.class);
            android.widget.RemoteViews remoteViews2 = (android.widget.RemoteViews) parcel.readParcelable(null, android.widget.RemoteViews.class);
            if (autofillIdArr != null) {
                builder.setAuthentication(autofillIdArr, intentSender, remoteViews, inlinePresentation, inlinePresentation2, remoteViews2);
            }
            android.widget.RemoteViews remoteViews3 = (android.widget.RemoteViews) parcel.readParcelable(null, android.widget.RemoteViews.class);
            if (remoteViews3 != null) {
                builder.setDialogHeader(remoteViews3);
            }
            android.app.PendingIntent pendingIntent = (android.app.PendingIntent) parcel.readParcelable(null, android.app.PendingIntent.class);
            if (pendingIntent != null) {
                builder.setDialogPendingIntent(pendingIntent);
            }
            android.view.autofill.AutofillId[] autofillIdArr2 = (android.view.autofill.AutofillId[]) parcel.readParcelableArray(null, android.view.autofill.AutofillId.class);
            if (autofillIdArr2 != null) {
                builder.setFillDialogTriggerIds(autofillIdArr2);
            }
            android.widget.RemoteViews remoteViews4 = (android.widget.RemoteViews) parcel.readParcelable(null, android.widget.RemoteViews.class);
            if (remoteViews4 != null) {
                builder.setHeader(remoteViews4);
            }
            android.widget.RemoteViews remoteViews5 = (android.widget.RemoteViews) parcel.readParcelable(null, android.widget.RemoteViews.class);
            if (remoteViews5 != null) {
                builder.setFooter(remoteViews5);
            }
            android.service.autofill.UserData userData = (android.service.autofill.UserData) parcel.readParcelable(null, android.service.autofill.UserData.class);
            if (userData != null) {
                builder.setUserData(userData);
            }
            builder.setIgnoredIds((android.view.autofill.AutofillId[]) parcel.readParcelableArray(null, android.view.autofill.AutofillId.class));
            long readLong = parcel.readLong();
            if (readLong > 0) {
                builder.disableAutofill(readLong);
            }
            android.view.autofill.AutofillId[] autofillIdArr3 = (android.view.autofill.AutofillId[]) parcel.readParcelableArray(null, android.view.autofill.AutofillId.class);
            if (autofillIdArr3 != null) {
                builder.setFieldClassificationIds(autofillIdArr3);
            }
            android.service.assist.classification.FieldClassification[] fieldClassificationArr = (android.service.assist.classification.FieldClassification[]) parcel.readParcelableArray(null, android.service.assist.classification.FieldClassification.class);
            if (fieldClassificationArr != null) {
                builder.setDetectedFieldClassifications(java.util.Set.of((java.lang.Object[]) fieldClassificationArr));
            }
            builder.setIconResourceId(parcel.readInt());
            builder.setServiceDisplayNameResourceId(parcel.readInt());
            builder.setShowFillDialogIcon(parcel.readBoolean());
            builder.setShowSaveDialogIcon(parcel.readBoolean());
            builder.setFlags(parcel.readInt());
            builder.setPresentationCancelIds(parcel.createIntArray());
            android.service.autofill.FillResponse build = builder.build();
            build.setRequestId(parcel.readInt());
            return build;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.service.autofill.FillResponse[] newArray(int i) {
            return new android.service.autofill.FillResponse[i];
        }
    };
    public static final int FLAG_CREDENTIAL_MANAGER_RESPONSE = 8;
    public static final int FLAG_DELAY_FILL = 4;
    public static final int FLAG_DISABLE_ACTIVITY_ONLY = 2;
    public static final int FLAG_TRACK_CONTEXT_COMMITED = 1;
    private final android.content.IntentSender mAuthentication;
    private final android.view.autofill.AutofillId[] mAuthenticationIds;
    private final int[] mCancelIds;
    private final android.os.Bundle mClientState;
    private final android.content.pm.ParceledListSlice<android.service.autofill.Dataset> mDatasets;
    private final android.service.assist.classification.FieldClassification[] mDetectedFieldTypes;
    private final android.widget.RemoteViews mDialogHeader;
    private final android.app.PendingIntent mDialogPendingIntent;
    private final android.widget.RemoteViews mDialogPresentation;
    private final long mDisableDuration;
    private final android.view.autofill.AutofillId[] mFieldClassificationIds;
    private final android.view.autofill.AutofillId[] mFillDialogTriggerIds;
    private final int mFlags;
    private final android.widget.RemoteViews mFooter;
    private final android.widget.RemoteViews mHeader;
    private final int mIconResourceId;
    private final android.view.autofill.AutofillId[] mIgnoredIds;
    private final android.service.autofill.InlinePresentation mInlinePresentation;
    private final android.service.autofill.InlinePresentation mInlineTooltipPresentation;
    private final android.widget.RemoteViews mPresentation;
    private int mRequestId;
    private final android.service.autofill.SaveInfo mSaveInfo;
    private final int mServiceDisplayNameResourceId;
    private final boolean mShowFillDialogIcon;
    private final boolean mShowSaveDialogIcon;
    private final boolean mSupportsInlineSuggestions;
    private final android.service.autofill.UserData mUserData;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    @interface FillResponseFlags {
    }

    public static android.service.autofill.FillResponse shallowCopy(android.service.autofill.FillResponse fillResponse, java.util.List<android.service.autofill.Dataset> list, android.service.autofill.SaveInfo saveInfo) {
        return new android.service.autofill.FillResponse(list != null ? new android.content.pm.ParceledListSlice(list) : null, saveInfo, fillResponse.mClientState, fillResponse.mPresentation, fillResponse.mInlinePresentation, fillResponse.mInlineTooltipPresentation, fillResponse.mDialogPresentation, fillResponse.mDialogHeader, fillResponse.mHeader, fillResponse.mFooter, fillResponse.mAuthentication, fillResponse.mAuthenticationIds, fillResponse.mIgnoredIds, fillResponse.mFillDialogTriggerIds, fillResponse.mDisableDuration, fillResponse.mFieldClassificationIds, fillResponse.mFlags, fillResponse.mRequestId, fillResponse.mUserData, fillResponse.mCancelIds, fillResponse.mSupportsInlineSuggestions, fillResponse.mIconResourceId, fillResponse.mServiceDisplayNameResourceId, fillResponse.mShowFillDialogIcon, fillResponse.mShowSaveDialogIcon, fillResponse.mDetectedFieldTypes, fillResponse.mDialogPendingIntent);
    }

    private FillResponse(android.content.pm.ParceledListSlice<android.service.autofill.Dataset> parceledListSlice, android.service.autofill.SaveInfo saveInfo, android.os.Bundle bundle, android.widget.RemoteViews remoteViews, android.service.autofill.InlinePresentation inlinePresentation, android.service.autofill.InlinePresentation inlinePresentation2, android.widget.RemoteViews remoteViews2, android.widget.RemoteViews remoteViews3, android.widget.RemoteViews remoteViews4, android.widget.RemoteViews remoteViews5, android.content.IntentSender intentSender, android.view.autofill.AutofillId[] autofillIdArr, android.view.autofill.AutofillId[] autofillIdArr2, android.view.autofill.AutofillId[] autofillIdArr3, long j, android.view.autofill.AutofillId[] autofillIdArr4, int i, int i2, android.service.autofill.UserData userData, int[] iArr, boolean z, int i3, int i4, boolean z2, boolean z3, android.service.assist.classification.FieldClassification[] fieldClassificationArr, android.app.PendingIntent pendingIntent) {
        this.mDatasets = parceledListSlice;
        this.mSaveInfo = saveInfo;
        this.mClientState = bundle;
        this.mPresentation = remoteViews;
        this.mInlinePresentation = inlinePresentation;
        this.mInlineTooltipPresentation = inlinePresentation2;
        this.mDialogPresentation = remoteViews2;
        this.mDialogHeader = remoteViews3;
        this.mHeader = remoteViews4;
        this.mFooter = remoteViews5;
        this.mAuthentication = intentSender;
        this.mAuthenticationIds = autofillIdArr;
        this.mIgnoredIds = autofillIdArr2;
        this.mFillDialogTriggerIds = autofillIdArr3;
        this.mDisableDuration = j;
        this.mFieldClassificationIds = autofillIdArr4;
        this.mFlags = i;
        this.mRequestId = i2;
        this.mUserData = userData;
        this.mCancelIds = iArr;
        this.mSupportsInlineSuggestions = z;
        this.mIconResourceId = i3;
        this.mServiceDisplayNameResourceId = i4;
        this.mShowFillDialogIcon = z2;
        this.mShowSaveDialogIcon = z3;
        this.mDetectedFieldTypes = fieldClassificationArr;
        this.mDialogPendingIntent = pendingIntent;
    }

    private FillResponse(android.service.autofill.FillResponse.Builder builder) {
        this.mDatasets = builder.mDatasets != null ? new android.content.pm.ParceledListSlice<>(builder.mDatasets) : null;
        this.mSaveInfo = builder.mSaveInfo;
        this.mClientState = builder.mClientState;
        this.mPresentation = builder.mPresentation;
        this.mInlinePresentation = builder.mInlinePresentation;
        this.mInlineTooltipPresentation = builder.mInlineTooltipPresentation;
        this.mDialogPresentation = builder.mDialogPresentation;
        this.mDialogHeader = builder.mDialogHeader;
        this.mHeader = builder.mHeader;
        this.mFooter = builder.mFooter;
        this.mAuthentication = builder.mAuthentication;
        this.mAuthenticationIds = builder.mAuthenticationIds;
        this.mFillDialogTriggerIds = builder.mFillDialogTriggerIds;
        this.mIgnoredIds = builder.mIgnoredIds;
        this.mDisableDuration = builder.mDisableDuration;
        this.mFieldClassificationIds = builder.mFieldClassificationIds;
        this.mFlags = builder.mFlags;
        this.mRequestId = Integer.MIN_VALUE;
        this.mUserData = builder.mUserData;
        this.mCancelIds = builder.mCancelIds;
        this.mSupportsInlineSuggestions = builder.mSupportsInlineSuggestions;
        this.mIconResourceId = builder.mIconResourceId;
        this.mServiceDisplayNameResourceId = builder.mServiceDisplayNameResourceId;
        this.mShowFillDialogIcon = builder.mShowFillDialogIcon;
        this.mShowSaveDialogIcon = builder.mShowSaveDialogIcon;
        this.mDetectedFieldTypes = builder.mDetectedFieldTypes;
        this.mDialogPendingIntent = builder.mDialogPendingIntent;
    }

    public java.util.Set<android.service.assist.classification.FieldClassification> getDetectedFieldClassifications() {
        return java.util.Set.of((java.lang.Object[]) this.mDetectedFieldTypes);
    }

    public android.os.Bundle getClientState() {
        return this.mClientState;
    }

    public java.util.List<android.service.autofill.Dataset> getDatasets() {
        if (this.mDatasets != null) {
            return this.mDatasets.getList();
        }
        return null;
    }

    public android.service.autofill.SaveInfo getSaveInfo() {
        return this.mSaveInfo;
    }

    public android.widget.RemoteViews getPresentation() {
        return this.mPresentation;
    }

    public android.service.autofill.InlinePresentation getInlinePresentation() {
        return this.mInlinePresentation;
    }

    public android.service.autofill.InlinePresentation getInlineTooltipPresentation() {
        return this.mInlineTooltipPresentation;
    }

    public android.widget.RemoteViews getDialogPresentation() {
        return this.mDialogPresentation;
    }

    public android.widget.RemoteViews getDialogHeader() {
        return this.mDialogHeader;
    }

    public android.widget.RemoteViews getHeader() {
        return this.mHeader;
    }

    public android.widget.RemoteViews getFooter() {
        return this.mFooter;
    }

    public android.content.IntentSender getAuthentication() {
        return this.mAuthentication;
    }

    public android.view.autofill.AutofillId[] getAuthenticationIds() {
        return this.mAuthenticationIds;
    }

    public android.view.autofill.AutofillId[] getFillDialogTriggerIds() {
        return this.mFillDialogTriggerIds;
    }

    public android.view.autofill.AutofillId[] getIgnoredIds() {
        return this.mIgnoredIds;
    }

    public long getDisableDuration() {
        return this.mDisableDuration;
    }

    public android.view.autofill.AutofillId[] getFieldClassificationIds() {
        return this.mFieldClassificationIds;
    }

    public android.service.autofill.UserData getUserData() {
        return this.mUserData;
    }

    public int getIconResourceId() {
        return this.mIconResourceId;
    }

    public int getServiceDisplayNameResourceId() {
        return this.mServiceDisplayNameResourceId;
    }

    public boolean getShowFillDialogIcon() {
        return this.mShowFillDialogIcon;
    }

    public boolean getShowSaveDialogIcon() {
        return this.mShowSaveDialogIcon;
    }

    public int getFlags() {
        return this.mFlags;
    }

    public void setRequestId(int i) {
        this.mRequestId = i;
    }

    public int getRequestId() {
        return this.mRequestId;
    }

    public int[] getCancelIds() {
        return this.mCancelIds;
    }

    public boolean supportsInlineSuggestions() {
        return this.mSupportsInlineSuggestions;
    }

    public static final class Builder {
        private android.content.IntentSender mAuthentication;
        private android.view.autofill.AutofillId[] mAuthenticationIds;
        private int[] mCancelIds;
        private android.os.Bundle mClientState;
        private java.util.ArrayList<android.service.autofill.Dataset> mDatasets;
        private boolean mDestroyed;
        private android.service.assist.classification.FieldClassification[] mDetectedFieldTypes;
        private android.widget.RemoteViews mDialogHeader;
        private android.app.PendingIntent mDialogPendingIntent;
        private android.widget.RemoteViews mDialogPresentation;
        private long mDisableDuration;
        private android.view.autofill.AutofillId[] mFieldClassificationIds;
        private android.view.autofill.AutofillId[] mFillDialogTriggerIds;
        private int mFlags;
        private android.widget.RemoteViews mFooter;
        private android.widget.RemoteViews mHeader;
        private int mIconResourceId;
        private android.view.autofill.AutofillId[] mIgnoredIds;
        private android.service.autofill.InlinePresentation mInlinePresentation;
        private android.service.autofill.InlinePresentation mInlineTooltipPresentation;
        private android.widget.RemoteViews mPresentation;
        private android.service.autofill.SaveInfo mSaveInfo;
        private int mServiceDisplayNameResourceId;
        private boolean mShowFillDialogIcon = true;
        private boolean mShowSaveDialogIcon = true;
        private boolean mSupportsInlineSuggestions;
        private android.service.autofill.UserData mUserData;

        public android.service.autofill.FillResponse.Builder setDetectedFieldClassifications(java.util.Set<android.service.assist.classification.FieldClassification> set) {
            throwIfDestroyed();
            throwIfDisableAutofillCalled();
            this.mDetectedFieldTypes = (android.service.assist.classification.FieldClassification[]) set.toArray(new android.service.assist.classification.FieldClassification[0]);
            return this;
        }

        @java.lang.Deprecated
        public android.service.autofill.FillResponse.Builder setAuthentication(android.view.autofill.AutofillId[] autofillIdArr, android.content.IntentSender intentSender, android.widget.RemoteViews remoteViews) {
            throwIfDestroyed();
            throwIfDisableAutofillCalled();
            if (this.mHeader != null || this.mFooter != null) {
                throw new java.lang.IllegalStateException("Already called #setHeader() or #setFooter()");
            }
            if ((remoteViews == null) ^ (intentSender == null)) {
                throw new java.lang.IllegalArgumentException("authentication and presentation must be both non-null or null");
            }
            this.mAuthentication = intentSender;
            this.mPresentation = remoteViews;
            this.mAuthenticationIds = android.service.autofill.AutofillServiceHelper.assertValid(autofillIdArr);
            return this;
        }

        @java.lang.Deprecated
        public android.service.autofill.FillResponse.Builder setAuthentication(android.view.autofill.AutofillId[] autofillIdArr, android.content.IntentSender intentSender, android.widget.RemoteViews remoteViews, android.service.autofill.InlinePresentation inlinePresentation) {
            return setAuthentication(autofillIdArr, intentSender, remoteViews, inlinePresentation, null);
        }

        @java.lang.Deprecated
        public android.service.autofill.FillResponse.Builder setAuthentication(android.view.autofill.AutofillId[] autofillIdArr, android.content.IntentSender intentSender, android.widget.RemoteViews remoteViews, android.service.autofill.InlinePresentation inlinePresentation, android.service.autofill.InlinePresentation inlinePresentation2) {
            throwIfDestroyed();
            throwIfDisableAutofillCalled();
            return setAuthentication(autofillIdArr, intentSender, remoteViews, inlinePresentation, inlinePresentation2, null);
        }

        public android.service.autofill.FillResponse.Builder setAuthentication(android.view.autofill.AutofillId[] autofillIdArr, android.content.IntentSender intentSender, android.service.autofill.Presentations presentations) {
            throwIfDestroyed();
            throwIfDisableAutofillCalled();
            if (presentations == null) {
                return setAuthentication(autofillIdArr, intentSender, null, null, null, null);
            }
            return setAuthentication(autofillIdArr, intentSender, presentations.getMenuPresentation(), presentations.getInlinePresentation(), presentations.getInlineTooltipPresentation(), presentations.getDialogPresentation());
        }

        /* JADX INFO: Access modifiers changed from: private */
        public android.service.autofill.FillResponse.Builder setAuthentication(android.view.autofill.AutofillId[] autofillIdArr, android.content.IntentSender intentSender, android.widget.RemoteViews remoteViews, android.service.autofill.InlinePresentation inlinePresentation, android.service.autofill.InlinePresentation inlinePresentation2, android.widget.RemoteViews remoteViews2) {
            throwIfDestroyed();
            throwIfDisableAutofillCalled();
            if (this.mHeader != null || this.mFooter != null) {
                throw new java.lang.IllegalStateException("Already called #setHeader() or #setFooter()");
            }
            if ((remoteViews == null && inlinePresentation == null) ^ (intentSender == null)) {
                throw new java.lang.IllegalArgumentException("authentication and presentation (dropdown or inline), must be both non-null or null");
            }
            this.mAuthentication = intentSender;
            this.mPresentation = remoteViews;
            this.mInlinePresentation = inlinePresentation;
            this.mInlineTooltipPresentation = inlinePresentation2;
            this.mDialogPresentation = remoteViews2;
            this.mAuthenticationIds = android.service.autofill.AutofillServiceHelper.assertValid(autofillIdArr);
            return this;
        }

        public android.service.autofill.FillResponse.Builder setIgnoredIds(android.view.autofill.AutofillId... autofillIdArr) {
            throwIfDestroyed();
            this.mIgnoredIds = autofillIdArr;
            return this;
        }

        public android.service.autofill.FillResponse.Builder addDataset(android.service.autofill.Dataset dataset) {
            throwIfDestroyed();
            throwIfDisableAutofillCalled();
            if (dataset == null) {
                return this;
            }
            if (this.mDatasets == null) {
                this.mDatasets = new java.util.ArrayList<>();
            }
            this.mDatasets.add(dataset);
            return this;
        }

        public android.service.autofill.FillResponse.Builder setDatasets(java.util.ArrayList<android.service.autofill.Dataset> arrayList) {
            this.mDatasets = arrayList;
            return this;
        }

        public android.service.autofill.FillResponse.Builder setSaveInfo(android.service.autofill.SaveInfo saveInfo) {
            throwIfDestroyed();
            throwIfDisableAutofillCalled();
            this.mSaveInfo = saveInfo;
            return this;
        }

        public android.service.autofill.FillResponse.Builder setClientState(android.os.Bundle bundle) {
            throwIfDestroyed();
            throwIfDisableAutofillCalled();
            this.mClientState = bundle;
            return this;
        }

        public android.service.autofill.FillResponse.Builder setFieldClassificationIds(android.view.autofill.AutofillId... autofillIdArr) {
            throwIfDestroyed();
            throwIfDisableAutofillCalled();
            com.android.internal.util.Preconditions.checkArrayElementsNotNull(autofillIdArr, android.provider.Downloads.EXTRA_IDS);
            com.android.internal.util.Preconditions.checkArgumentInRange(autofillIdArr.length, 1, android.service.autofill.UserData.getMaxFieldClassificationIdsSize(), "ids length");
            this.mFieldClassificationIds = autofillIdArr;
            this.mFlags |= 1;
            return this;
        }

        public android.service.autofill.FillResponse.Builder setFlags(int i) {
            throwIfDestroyed();
            this.mFlags = com.android.internal.util.Preconditions.checkFlagsArgument(i, 15);
            return this;
        }

        public android.service.autofill.FillResponse.Builder disableAutofill(long j) {
            throwIfDestroyed();
            if (j <= 0) {
                throw new java.lang.IllegalArgumentException("duration must be greater than 0");
            }
            if (this.mAuthentication != null || this.mDatasets != null || this.mSaveInfo != null || this.mFieldClassificationIds != null || this.mClientState != null) {
                throw new java.lang.IllegalStateException("disableAutofill() must be the only method called");
            }
            this.mDisableDuration = j;
            return this;
        }

        public android.service.autofill.FillResponse.Builder setIconResourceId(int i) {
            throwIfDestroyed();
            this.mIconResourceId = i;
            return this;
        }

        public android.service.autofill.FillResponse.Builder setServiceDisplayNameResourceId(int i) {
            throwIfDestroyed();
            this.mServiceDisplayNameResourceId = i;
            return this;
        }

        public android.service.autofill.FillResponse.Builder setShowFillDialogIcon(boolean z) {
            throwIfDestroyed();
            this.mShowFillDialogIcon = z;
            return this;
        }

        public android.service.autofill.FillResponse.Builder setShowSaveDialogIcon(boolean z) {
            throwIfDestroyed();
            this.mShowSaveDialogIcon = z;
            return this;
        }

        public android.service.autofill.FillResponse.Builder setHeader(android.widget.RemoteViews remoteViews) {
            throwIfDestroyed();
            throwIfAuthenticationCalled();
            this.mHeader = (android.widget.RemoteViews) java.util.Objects.requireNonNull(remoteViews);
            return this;
        }

        public android.service.autofill.FillResponse.Builder setFooter(android.widget.RemoteViews remoteViews) {
            throwIfDestroyed();
            throwIfAuthenticationCalled();
            this.mFooter = (android.widget.RemoteViews) java.util.Objects.requireNonNull(remoteViews);
            return this;
        }

        public android.service.autofill.FillResponse.Builder setUserData(android.service.autofill.UserData userData) {
            throwIfDestroyed();
            throwIfAuthenticationCalled();
            this.mUserData = (android.service.autofill.UserData) java.util.Objects.requireNonNull(userData);
            return this;
        }

        public android.service.autofill.FillResponse.Builder setPresentationCancelIds(int[] iArr) {
            throwIfDestroyed();
            this.mCancelIds = iArr;
            return this;
        }

        public android.service.autofill.FillResponse.Builder setDialogHeader(android.widget.RemoteViews remoteViews) {
            throwIfDestroyed();
            java.util.Objects.requireNonNull(remoteViews);
            this.mDialogHeader = remoteViews;
            return this;
        }

        public android.service.autofill.FillResponse.Builder setFillDialogTriggerIds(android.view.autofill.AutofillId... autofillIdArr) {
            throwIfDestroyed();
            com.android.internal.util.Preconditions.checkArrayElementsNotNull(autofillIdArr, android.provider.Downloads.EXTRA_IDS);
            this.mFillDialogTriggerIds = autofillIdArr;
            return this;
        }

        public android.service.autofill.FillResponse.Builder setDialogPendingIntent(android.app.PendingIntent pendingIntent) {
            throwIfDestroyed();
            com.android.internal.util.Preconditions.checkNotNull(pendingIntent, "can't pass a null object to setDialogPendingIntent");
            this.mDialogPendingIntent = pendingIntent;
            return this;
        }

        public android.service.autofill.FillResponse build() {
            throwIfDestroyed();
            if (this.mAuthentication == null && this.mDatasets == null && this.mSaveInfo == null && this.mDisableDuration == 0 && this.mFieldClassificationIds == null && this.mClientState == null) {
                throw new java.lang.IllegalStateException("need to provide: at least one DataSet, or a SaveInfo, or an authentication with a presentation, or a FieldsDetection, or a client state, or disable autofill");
            }
            if (this.mDatasets == null && (this.mHeader != null || this.mFooter != null)) {
                throw new java.lang.IllegalStateException("must add at least 1 dataset when using header or footer");
            }
            if (this.mDatasets != null) {
                java.util.Iterator<android.service.autofill.Dataset> it = this.mDatasets.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    }
                    if (it.next().getFieldInlinePresentation(0) != null) {
                        this.mSupportsInlineSuggestions = true;
                        break;
                    }
                }
            } else if (this.mInlinePresentation != null) {
                this.mSupportsInlineSuggestions = true;
            }
            this.mDestroyed = true;
            return new android.service.autofill.FillResponse(this);
        }

        private void throwIfDestroyed() {
            if (this.mDestroyed) {
                throw new java.lang.IllegalStateException("Already called #build()");
            }
        }

        private void throwIfDisableAutofillCalled() {
            if (this.mDisableDuration > 0) {
                throw new java.lang.IllegalStateException("Already called #disableAutofill()");
            }
        }

        private void throwIfAuthenticationCalled() {
            if (this.mAuthentication != null) {
                throw new java.lang.IllegalStateException("Already called #setAuthentication()");
            }
        }
    }

    public java.lang.String toString() {
        if (!android.view.autofill.Helper.sDebug) {
            return super.toString();
        }
        java.lang.StringBuilder sb = new java.lang.StringBuilder("FillResponse : [mRequestId=" + this.mRequestId);
        if (this.mDatasets != null) {
            sb.append(", datasets=").append(this.mDatasets.getList());
        }
        if (this.mSaveInfo != null) {
            sb.append(", saveInfo=").append(this.mSaveInfo);
        }
        if (this.mClientState != null) {
            sb.append(", hasClientState");
        }
        if (this.mPresentation != null) {
            sb.append(", hasPresentation");
        }
        if (this.mInlinePresentation != null) {
            sb.append(", hasInlinePresentation");
        }
        if (this.mInlineTooltipPresentation != null) {
            sb.append(", hasInlineTooltipPresentation");
        }
        if (this.mDialogPresentation != null) {
            sb.append(", hasDialogPresentation");
        }
        if (this.mDialogHeader != null) {
            sb.append(", hasDialogHeader");
        }
        if (this.mHeader != null) {
            sb.append(", hasHeader");
        }
        if (this.mFooter != null) {
            sb.append(", hasFooter");
        }
        if (this.mAuthentication != null) {
            sb.append(", hasAuthentication");
        }
        if (this.mDialogPendingIntent != null) {
            sb.append(", hasDialogPendingIntent");
        }
        if (this.mAuthenticationIds != null) {
            sb.append(", authenticationIds=").append(java.util.Arrays.toString(this.mAuthenticationIds));
        }
        if (this.mFillDialogTriggerIds != null) {
            sb.append(", fillDialogTriggerIds=").append(java.util.Arrays.toString(this.mFillDialogTriggerIds));
        }
        sb.append(", disableDuration=").append(this.mDisableDuration);
        if (this.mFlags != 0) {
            sb.append(", flags=").append(this.mFlags);
        }
        if (this.mFieldClassificationIds != null) {
            sb.append(java.util.Arrays.toString(this.mFieldClassificationIds));
        }
        if (this.mUserData != null) {
            sb.append(", userData=").append(this.mUserData);
        }
        if (this.mCancelIds != null) {
            sb.append(", mCancelIds=").append(this.mCancelIds.length);
        }
        sb.append(", mSupportInlinePresentations=").append(this.mSupportsInlineSuggestions);
        return sb.append(android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_END).toString();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeParcelable(this.mDatasets, i);
        parcel.writeParcelable(this.mSaveInfo, i);
        parcel.writeParcelable(this.mClientState, i);
        parcel.writeParcelableArray(this.mAuthenticationIds, i);
        parcel.writeParcelable(this.mAuthentication, i);
        parcel.writeParcelable(this.mPresentation, i);
        parcel.writeParcelable(this.mInlinePresentation, i);
        parcel.writeParcelable(this.mInlineTooltipPresentation, i);
        parcel.writeParcelable(this.mDialogPresentation, i);
        parcel.writeParcelable(this.mDialogHeader, i);
        parcel.writeParcelable(this.mDialogPendingIntent, i);
        parcel.writeParcelableArray(this.mFillDialogTriggerIds, i);
        parcel.writeParcelable(this.mHeader, i);
        parcel.writeParcelable(this.mFooter, i);
        parcel.writeParcelable(this.mUserData, i);
        parcel.writeParcelableArray(this.mIgnoredIds, i);
        parcel.writeLong(this.mDisableDuration);
        parcel.writeParcelableArray(this.mFieldClassificationIds, i);
        parcel.writeParcelableArray(this.mDetectedFieldTypes, i);
        parcel.writeInt(this.mIconResourceId);
        parcel.writeInt(this.mServiceDisplayNameResourceId);
        parcel.writeBoolean(this.mShowFillDialogIcon);
        parcel.writeBoolean(this.mShowSaveDialogIcon);
        parcel.writeInt(this.mFlags);
        parcel.writeIntArray(this.mCancelIds);
        parcel.writeInt(this.mRequestId);
    }
}
