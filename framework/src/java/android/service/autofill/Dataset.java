package android.service.autofill;

/* loaded from: classes3.dex */
public final class Dataset implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.service.autofill.Dataset> CREATOR = new android.os.Parcelable.Creator<android.service.autofill.Dataset>() { // from class: android.service.autofill.Dataset.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.service.autofill.Dataset createFromParcel(android.os.Parcel parcel) {
            android.service.autofill.Dataset.Builder builder;
            android.widget.RemoteViews remoteViews = (android.widget.RemoteViews) parcel.readParcelable(null, android.widget.RemoteViews.class);
            android.widget.RemoteViews remoteViews2 = (android.widget.RemoteViews) parcel.readParcelable(null, android.widget.RemoteViews.class);
            android.service.autofill.InlinePresentation inlinePresentation = (android.service.autofill.InlinePresentation) parcel.readParcelable(null, android.service.autofill.InlinePresentation.class);
            android.service.autofill.InlinePresentation inlinePresentation2 = (android.service.autofill.InlinePresentation) parcel.readParcelable(null, android.service.autofill.InlinePresentation.class);
            java.util.ArrayList createTypedArrayList = parcel.createTypedArrayList(android.view.autofill.AutofillId.CREATOR);
            java.util.ArrayList createTypedArrayList2 = parcel.createTypedArrayList(android.view.autofill.AutofillValue.CREATOR);
            java.util.ArrayList createTypedArrayList3 = parcel.createTypedArrayList(android.widget.RemoteViews.CREATOR);
            java.util.ArrayList createTypedArrayList4 = parcel.createTypedArrayList(android.widget.RemoteViews.CREATOR);
            java.util.ArrayList createTypedArrayList5 = parcel.createTypedArrayList(android.service.autofill.InlinePresentation.CREATOR);
            java.util.ArrayList createTypedArrayList6 = parcel.createTypedArrayList(android.service.autofill.InlinePresentation.CREATOR);
            java.util.ArrayList createTypedArrayList7 = parcel.createTypedArrayList(android.service.autofill.Dataset.DatasetFieldFilter.CREATOR);
            java.util.ArrayList<java.lang.String> createStringArrayList = parcel.createStringArrayList();
            android.content.ClipData clipData = (android.content.ClipData) parcel.readParcelable(null, android.content.ClipData.class);
            android.content.IntentSender intentSender = (android.content.IntentSender) parcel.readParcelable(null, android.content.IntentSender.class);
            java.lang.String readString = parcel.readString();
            int readInt = parcel.readInt();
            android.content.Intent intent = (android.content.Intent) parcel.readTypedObject(android.content.Intent.CREATOR);
            if (remoteViews != null || inlinePresentation != null || remoteViews2 != null) {
                android.service.autofill.Presentations.Builder builder2 = new android.service.autofill.Presentations.Builder();
                if (remoteViews != null) {
                    builder2.setMenuPresentation(remoteViews);
                }
                if (inlinePresentation != null) {
                    builder2.setInlinePresentation(inlinePresentation);
                }
                if (inlinePresentation2 != null) {
                    builder2.setInlineTooltipPresentation(inlinePresentation2);
                }
                if (remoteViews2 != null) {
                    builder2.setDialogPresentation(remoteViews2);
                }
                builder = new android.service.autofill.Dataset.Builder(builder2.build());
            } else {
                builder = new android.service.autofill.Dataset.Builder();
            }
            int i = 0;
            if (clipData != null) {
                builder.setContent((android.view.autofill.AutofillId) createTypedArrayList.get(0), clipData);
            }
            int size = createTypedArrayList5.size();
            while (i < createTypedArrayList.size()) {
                builder.createFromParcel((android.view.autofill.AutofillId) createTypedArrayList.get(i), createStringArrayList.get(i), (android.view.autofill.AutofillValue) createTypedArrayList2.get(i), (android.widget.RemoteViews) createTypedArrayList3.get(i), i < size ? (android.service.autofill.InlinePresentation) createTypedArrayList5.get(i) : null, i < size ? (android.service.autofill.InlinePresentation) createTypedArrayList6.get(i) : null, (android.service.autofill.Dataset.DatasetFieldFilter) createTypedArrayList7.get(i), (android.widget.RemoteViews) createTypedArrayList4.get(i));
                i++;
            }
            builder.setAuthentication(intentSender);
            builder.setCredentialFillInIntent(intent);
            builder.setId(readString);
            android.service.autofill.Dataset build = builder.build();
            build.mEligibleReason = readInt;
            return build;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.service.autofill.Dataset[] newArray(int i) {
            return new android.service.autofill.Dataset[i];
        }
    };
    public static final int PICK_REASON_NO_PCC = 1;
    public static final int PICK_REASON_PCC_DETECTION_ONLY = 4;
    public static final int PICK_REASON_PCC_DETECTION_PREFERRED_WITH_PROVIDER = 5;
    public static final int PICK_REASON_PROVIDER_DETECTION_ONLY = 2;
    public static final int PICK_REASON_PROVIDER_DETECTION_PREFERRED_WITH_PCC = 3;
    public static final int PICK_REASON_UNKNOWN = 0;
    private final android.content.IntentSender mAuthentication;
    private final java.util.ArrayList<java.lang.String> mAutofillDatatypes;
    private android.content.Intent mCredentialFillInIntent;
    private final android.widget.RemoteViews mDialogPresentation;
    private int mEligibleReason;
    private final android.content.ClipData mFieldContent;
    private final java.util.ArrayList<android.widget.RemoteViews> mFieldDialogPresentations;
    private final java.util.ArrayList<android.service.autofill.Dataset.DatasetFieldFilter> mFieldFilters;
    private final java.util.ArrayList<android.view.autofill.AutofillId> mFieldIds;
    private final java.util.ArrayList<android.service.autofill.InlinePresentation> mFieldInlinePresentations;
    private final java.util.ArrayList<android.service.autofill.InlinePresentation> mFieldInlineTooltipPresentations;
    private final java.util.ArrayList<android.widget.RemoteViews> mFieldPresentations;
    private final java.util.ArrayList<android.view.autofill.AutofillValue> mFieldValues;
    java.lang.String mId;
    private final android.service.autofill.InlinePresentation mInlinePresentation;
    private final android.service.autofill.InlinePresentation mInlineTooltipPresentation;
    private final android.widget.RemoteViews mPresentation;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface DatasetEligibleReason {
    }

    public Dataset(java.util.ArrayList<android.view.autofill.AutofillId> arrayList, java.util.ArrayList<android.view.autofill.AutofillValue> arrayList2, java.util.ArrayList<android.widget.RemoteViews> arrayList3, java.util.ArrayList<android.widget.RemoteViews> arrayList4, java.util.ArrayList<android.service.autofill.InlinePresentation> arrayList5, java.util.ArrayList<android.service.autofill.InlinePresentation> arrayList6, java.util.ArrayList<android.service.autofill.Dataset.DatasetFieldFilter> arrayList7, java.util.ArrayList<java.lang.String> arrayList8, android.content.ClipData clipData, android.widget.RemoteViews remoteViews, android.widget.RemoteViews remoteViews2, android.service.autofill.InlinePresentation inlinePresentation, android.service.autofill.InlinePresentation inlinePresentation2, java.lang.String str, android.content.IntentSender intentSender) {
        this.mFieldIds = arrayList;
        this.mFieldValues = arrayList2;
        this.mFieldPresentations = arrayList3;
        this.mFieldDialogPresentations = arrayList4;
        this.mFieldInlinePresentations = arrayList5;
        this.mFieldInlineTooltipPresentations = arrayList6;
        this.mAutofillDatatypes = arrayList8;
        this.mFieldFilters = arrayList7;
        this.mFieldContent = clipData;
        this.mPresentation = remoteViews;
        this.mDialogPresentation = remoteViews2;
        this.mInlinePresentation = inlinePresentation;
        this.mInlineTooltipPresentation = inlinePresentation2;
        this.mAuthentication = intentSender;
        this.mCredentialFillInIntent = null;
        this.mId = str;
    }

    public Dataset(android.service.autofill.Dataset dataset, java.util.ArrayList<android.view.autofill.AutofillId> arrayList) {
        this.mFieldIds = arrayList;
        this.mFieldValues = dataset.mFieldValues;
        this.mFieldPresentations = dataset.mFieldPresentations;
        this.mFieldDialogPresentations = dataset.mFieldDialogPresentations;
        this.mFieldInlinePresentations = dataset.mFieldInlinePresentations;
        this.mFieldInlineTooltipPresentations = dataset.mFieldInlineTooltipPresentations;
        this.mFieldFilters = dataset.mFieldFilters;
        this.mFieldContent = dataset.mFieldContent;
        this.mPresentation = dataset.mPresentation;
        this.mDialogPresentation = dataset.mDialogPresentation;
        this.mInlinePresentation = dataset.mInlinePresentation;
        this.mInlineTooltipPresentation = dataset.mInlineTooltipPresentation;
        this.mAuthentication = dataset.mAuthentication;
        this.mCredentialFillInIntent = dataset.mCredentialFillInIntent;
        this.mId = dataset.mId;
        this.mAutofillDatatypes = dataset.mAutofillDatatypes;
    }

    private Dataset(android.service.autofill.Dataset.Builder builder) {
        this.mFieldIds = builder.mFieldIds;
        this.mFieldValues = builder.mFieldValues;
        this.mFieldPresentations = builder.mFieldPresentations;
        this.mFieldDialogPresentations = builder.mFieldDialogPresentations;
        this.mFieldInlinePresentations = builder.mFieldInlinePresentations;
        this.mFieldInlineTooltipPresentations = builder.mFieldInlineTooltipPresentations;
        this.mFieldFilters = builder.mFieldFilters;
        this.mFieldContent = builder.mFieldContent;
        this.mPresentation = builder.mPresentation;
        this.mDialogPresentation = builder.mDialogPresentation;
        this.mInlinePresentation = builder.mInlinePresentation;
        this.mInlineTooltipPresentation = builder.mInlineTooltipPresentation;
        this.mAuthentication = builder.mAuthentication;
        this.mCredentialFillInIntent = builder.mCredentialFillInIntent;
        this.mId = builder.mId;
        this.mAutofillDatatypes = builder.mAutofillDatatypes;
    }

    public java.util.ArrayList<java.lang.String> getAutofillDatatypes() {
        return this.mAutofillDatatypes;
    }

    public java.util.ArrayList<android.view.autofill.AutofillId> getFieldIds() {
        return this.mFieldIds;
    }

    public java.util.ArrayList<android.view.autofill.AutofillValue> getFieldValues() {
        return this.mFieldValues;
    }

    public android.widget.RemoteViews getFieldPresentation(int i) {
        android.widget.RemoteViews remoteViews = this.mFieldPresentations.get(i);
        return remoteViews != null ? remoteViews : this.mPresentation;
    }

    public android.widget.RemoteViews getFieldDialogPresentation(int i) {
        android.widget.RemoteViews remoteViews = this.mFieldDialogPresentations.get(i);
        return remoteViews != null ? remoteViews : this.mDialogPresentation;
    }

    public android.service.autofill.InlinePresentation getFieldInlinePresentation(int i) {
        android.service.autofill.InlinePresentation inlinePresentation = this.mFieldInlinePresentations.get(i);
        return inlinePresentation != null ? inlinePresentation : this.mInlinePresentation;
    }

    public android.service.autofill.InlinePresentation getFieldInlineTooltipPresentation(int i) {
        android.service.autofill.InlinePresentation inlinePresentation = this.mFieldInlineTooltipPresentations.get(i);
        return inlinePresentation != null ? inlinePresentation : this.mInlineTooltipPresentation;
    }

    public android.service.autofill.Dataset.DatasetFieldFilter getFilter(int i) {
        return this.mFieldFilters.get(i);
    }

    public android.content.ClipData getFieldContent() {
        return this.mFieldContent;
    }

    public android.content.IntentSender getAuthentication() {
        return this.mAuthentication;
    }

    public android.content.Intent getCredentialFillInIntent() {
        return this.mCredentialFillInIntent;
    }

    public void setCredentialFillInIntent(android.content.Intent intent) {
        this.mCredentialFillInIntent = intent;
    }

    public boolean isEmpty() {
        return this.mFieldIds == null || this.mFieldIds.isEmpty();
    }

    public java.lang.String toString() {
        if (!android.view.autofill.Helper.sDebug) {
            return super.toString();
        }
        java.lang.StringBuilder sb = new java.lang.StringBuilder("Dataset[");
        if (this.mId == null) {
            sb.append("noId");
        } else {
            sb.append("id=").append(this.mId.length()).append("_chars");
        }
        if (this.mFieldIds != null) {
            sb.append(", fieldIds=").append(this.mFieldIds);
        }
        if (this.mFieldValues != null) {
            sb.append(", fieldValues=").append(this.mFieldValues);
        }
        if (this.mFieldContent != null) {
            sb.append(", fieldContent=").append(this.mFieldContent);
        }
        if (this.mFieldPresentations != null) {
            sb.append(", fieldPresentations=").append(this.mFieldPresentations.size());
        }
        if (this.mFieldDialogPresentations != null) {
            sb.append(", fieldDialogPresentations=").append(this.mFieldDialogPresentations.size());
        }
        if (this.mFieldInlinePresentations != null) {
            sb.append(", fieldInlinePresentations=").append(this.mFieldInlinePresentations.size());
        }
        if (this.mFieldInlineTooltipPresentations != null) {
            sb.append(", fieldInlineTooltipInlinePresentations=").append(this.mFieldInlineTooltipPresentations.size());
        }
        if (this.mFieldFilters != null) {
            sb.append(", fieldFilters=").append(this.mFieldFilters.size());
        }
        if (this.mPresentation != null) {
            sb.append(", hasPresentation");
        }
        if (this.mDialogPresentation != null) {
            sb.append(", hasDialogPresentation");
        }
        if (this.mInlinePresentation != null) {
            sb.append(", hasInlinePresentation");
        }
        if (this.mInlineTooltipPresentation != null) {
            sb.append(", hasInlineTooltipPresentation");
        }
        if (this.mAuthentication != null) {
            sb.append(", hasAuthentication");
        }
        if (this.mCredentialFillInIntent != null) {
            sb.append(", hasAuthenticationExtras");
        }
        if (this.mAutofillDatatypes != null) {
            sb.append(", autofillDatatypes=").append(this.mAutofillDatatypes);
        }
        return sb.append(']').toString();
    }

    public java.lang.String getId() {
        return this.mId;
    }

    public void setEligibleReasonReason(int i) {
        this.mEligibleReason = i;
    }

    public int getEligibleReason() {
        return this.mEligibleReason;
    }

    public static final class Builder {
        private android.content.IntentSender mAuthentication;
        private android.content.Intent mCredentialFillInIntent;
        private boolean mDestroyed;
        private android.widget.RemoteViews mDialogPresentation;
        private android.content.ClipData mFieldContent;
        private java.lang.String mId;
        private android.service.autofill.InlinePresentation mInlinePresentation;
        private android.service.autofill.InlinePresentation mInlineTooltipPresentation;
        private android.widget.RemoteViews mPresentation;
        private java.util.ArrayList<android.view.autofill.AutofillId> mFieldIds = new java.util.ArrayList<>();
        private java.util.ArrayList<android.view.autofill.AutofillValue> mFieldValues = new java.util.ArrayList<>();
        private java.util.ArrayList<android.widget.RemoteViews> mFieldPresentations = new java.util.ArrayList<>();
        private java.util.ArrayList<android.widget.RemoteViews> mFieldDialogPresentations = new java.util.ArrayList<>();
        private java.util.ArrayList<android.service.autofill.InlinePresentation> mFieldInlinePresentations = new java.util.ArrayList<>();
        private java.util.ArrayList<android.service.autofill.InlinePresentation> mFieldInlineTooltipPresentations = new java.util.ArrayList<>();
        private java.util.ArrayList<android.service.autofill.Dataset.DatasetFieldFilter> mFieldFilters = new java.util.ArrayList<>();
        private java.util.ArrayList<java.lang.String> mAutofillDatatypes = new java.util.ArrayList<>();
        private android.util.ArrayMap<android.service.autofill.Field, java.lang.Integer> mFieldToIndexdMap = new android.util.ArrayMap<>();

        @java.lang.Deprecated
        public Builder(android.widget.RemoteViews remoteViews) {
            java.util.Objects.requireNonNull(remoteViews, "presentation must be non-null");
            this.mPresentation = remoteViews;
        }

        @android.annotation.SystemApi
        @java.lang.Deprecated
        public Builder(android.service.autofill.InlinePresentation inlinePresentation) {
            java.util.Objects.requireNonNull(inlinePresentation, "inlinePresentation must be non-null");
            this.mInlinePresentation = inlinePresentation;
        }

        public Builder(android.service.autofill.Presentations presentations) {
            java.util.Objects.requireNonNull(presentations, "presentations must be non-null");
            this.mPresentation = presentations.getMenuPresentation();
            this.mInlinePresentation = presentations.getInlinePresentation();
            this.mInlineTooltipPresentation = presentations.getInlineTooltipPresentation();
            this.mDialogPresentation = presentations.getDialogPresentation();
        }

        public Builder() {
        }

        @java.lang.Deprecated
        public android.service.autofill.Dataset.Builder setInlinePresentation(android.service.autofill.InlinePresentation inlinePresentation) {
            throwIfDestroyed();
            java.util.Objects.requireNonNull(inlinePresentation, "inlinePresentation must be non-null");
            this.mInlinePresentation = inlinePresentation;
            return this;
        }

        @java.lang.Deprecated
        public android.service.autofill.Dataset.Builder setInlinePresentation(android.service.autofill.InlinePresentation inlinePresentation, android.service.autofill.InlinePresentation inlinePresentation2) {
            throwIfDestroyed();
            java.util.Objects.requireNonNull(inlinePresentation, "inlinePresentation must be non-null");
            java.util.Objects.requireNonNull(inlinePresentation2, "inlineTooltipPresentation must be non-null");
            this.mInlinePresentation = inlinePresentation;
            this.mInlineTooltipPresentation = inlinePresentation2;
            return this;
        }

        public android.service.autofill.Dataset.Builder setAuthentication(android.content.IntentSender intentSender) {
            throwIfDestroyed();
            this.mAuthentication = intentSender;
            return this;
        }

        public android.service.autofill.Dataset.Builder setCredentialFillInIntent(android.content.Intent intent) {
            throwIfDestroyed();
            this.mCredentialFillInIntent = intent;
            return this;
        }

        public android.service.autofill.Dataset.Builder setId(java.lang.String str) {
            throwIfDestroyed();
            this.mId = str;
            return this;
        }

        @android.annotation.SystemApi
        public android.service.autofill.Dataset.Builder setContent(android.view.autofill.AutofillId autofillId, android.content.ClipData clipData) {
            throwIfDestroyed();
            if (clipData != null) {
                for (int i = 0; i < clipData.getItemCount(); i++) {
                    com.android.internal.util.Preconditions.checkArgument(clipData.getItemAt(i).getIntent() == null, "Content items cannot contain an Intent: content=" + clipData);
                }
            }
            setLifeTheUniverseAndEverything(autofillId, (android.view.autofill.AutofillValue) null, (android.widget.RemoteViews) null, (android.service.autofill.InlinePresentation) null, (android.service.autofill.InlinePresentation) null, (android.service.autofill.Dataset.DatasetFieldFilter) null, (android.widget.RemoteViews) null);
            this.mFieldContent = clipData;
            return this;
        }

        @java.lang.Deprecated
        public android.service.autofill.Dataset.Builder setValue(android.view.autofill.AutofillId autofillId, android.view.autofill.AutofillValue autofillValue) {
            throwIfDestroyed();
            setLifeTheUniverseAndEverything(autofillId, autofillValue, (android.widget.RemoteViews) null, (android.service.autofill.InlinePresentation) null, (android.service.autofill.InlinePresentation) null, (android.service.autofill.Dataset.DatasetFieldFilter) null, (android.widget.RemoteViews) null);
            return this;
        }

        @java.lang.Deprecated
        public android.service.autofill.Dataset.Builder setValue(android.view.autofill.AutofillId autofillId, android.view.autofill.AutofillValue autofillValue, android.widget.RemoteViews remoteViews) {
            throwIfDestroyed();
            java.util.Objects.requireNonNull(remoteViews, "presentation cannot be null");
            setLifeTheUniverseAndEverything(autofillId, autofillValue, remoteViews, (android.service.autofill.InlinePresentation) null, (android.service.autofill.InlinePresentation) null, (android.service.autofill.Dataset.DatasetFieldFilter) null, (android.widget.RemoteViews) null);
            return this;
        }

        @java.lang.Deprecated
        public android.service.autofill.Dataset.Builder setValue(android.view.autofill.AutofillId autofillId, android.view.autofill.AutofillValue autofillValue, java.util.regex.Pattern pattern) {
            throwIfDestroyed();
            com.android.internal.util.Preconditions.checkState(this.mPresentation != null, "Dataset presentation not set on constructor");
            setLifeTheUniverseAndEverything(autofillId, autofillValue, (android.widget.RemoteViews) null, (android.service.autofill.InlinePresentation) null, (android.service.autofill.InlinePresentation) null, new android.service.autofill.Dataset.DatasetFieldFilter(pattern), (android.widget.RemoteViews) null);
            return this;
        }

        @java.lang.Deprecated
        public android.service.autofill.Dataset.Builder setValue(android.view.autofill.AutofillId autofillId, android.view.autofill.AutofillValue autofillValue, java.util.regex.Pattern pattern, android.widget.RemoteViews remoteViews) {
            throwIfDestroyed();
            java.util.Objects.requireNonNull(remoteViews, "presentation cannot be null");
            setLifeTheUniverseAndEverything(autofillId, autofillValue, remoteViews, (android.service.autofill.InlinePresentation) null, (android.service.autofill.InlinePresentation) null, new android.service.autofill.Dataset.DatasetFieldFilter(pattern), (android.widget.RemoteViews) null);
            return this;
        }

        @java.lang.Deprecated
        public android.service.autofill.Dataset.Builder setValue(android.view.autofill.AutofillId autofillId, android.view.autofill.AutofillValue autofillValue, android.widget.RemoteViews remoteViews, android.service.autofill.InlinePresentation inlinePresentation) {
            throwIfDestroyed();
            java.util.Objects.requireNonNull(remoteViews, "presentation cannot be null");
            java.util.Objects.requireNonNull(inlinePresentation, "inlinePresentation cannot be null");
            setLifeTheUniverseAndEverything(autofillId, autofillValue, remoteViews, inlinePresentation, (android.service.autofill.InlinePresentation) null, (android.service.autofill.Dataset.DatasetFieldFilter) null, (android.widget.RemoteViews) null);
            return this;
        }

        @java.lang.Deprecated
        public android.service.autofill.Dataset.Builder setValue(android.view.autofill.AutofillId autofillId, android.view.autofill.AutofillValue autofillValue, android.widget.RemoteViews remoteViews, android.service.autofill.InlinePresentation inlinePresentation, android.service.autofill.InlinePresentation inlinePresentation2) {
            throwIfDestroyed();
            java.util.Objects.requireNonNull(remoteViews, "presentation cannot be null");
            java.util.Objects.requireNonNull(inlinePresentation, "inlinePresentation cannot be null");
            java.util.Objects.requireNonNull(inlinePresentation2, "inlineTooltipPresentation cannot be null");
            setLifeTheUniverseAndEverything(autofillId, autofillValue, remoteViews, inlinePresentation, inlinePresentation2, (android.service.autofill.Dataset.DatasetFieldFilter) null, (android.widget.RemoteViews) null);
            return this;
        }

        @java.lang.Deprecated
        public android.service.autofill.Dataset.Builder setValue(android.view.autofill.AutofillId autofillId, android.view.autofill.AutofillValue autofillValue, java.util.regex.Pattern pattern, android.widget.RemoteViews remoteViews, android.service.autofill.InlinePresentation inlinePresentation) {
            throwIfDestroyed();
            java.util.Objects.requireNonNull(remoteViews, "presentation cannot be null");
            java.util.Objects.requireNonNull(inlinePresentation, "inlinePresentation cannot be null");
            setLifeTheUniverseAndEverything(autofillId, autofillValue, remoteViews, inlinePresentation, (android.service.autofill.InlinePresentation) null, new android.service.autofill.Dataset.DatasetFieldFilter(pattern), (android.widget.RemoteViews) null);
            return this;
        }

        @java.lang.Deprecated
        public android.service.autofill.Dataset.Builder setValue(android.view.autofill.AutofillId autofillId, android.view.autofill.AutofillValue autofillValue, java.util.regex.Pattern pattern, android.widget.RemoteViews remoteViews, android.service.autofill.InlinePresentation inlinePresentation, android.service.autofill.InlinePresentation inlinePresentation2) {
            throwIfDestroyed();
            java.util.Objects.requireNonNull(remoteViews, "presentation cannot be null");
            java.util.Objects.requireNonNull(inlinePresentation, "inlinePresentation cannot be null");
            java.util.Objects.requireNonNull(inlinePresentation2, "inlineTooltipPresentation cannot be null");
            setLifeTheUniverseAndEverything(autofillId, autofillValue, remoteViews, inlinePresentation, inlinePresentation2, new android.service.autofill.Dataset.DatasetFieldFilter(pattern), (android.widget.RemoteViews) null);
            return this;
        }

        public android.service.autofill.Dataset.Builder setField(android.view.autofill.AutofillId autofillId, android.service.autofill.Field field) {
            int lifeTheUniverseAndEverything;
            throwIfDestroyed();
            if (this.mFieldToIndexdMap.containsKey(field)) {
                int intValue = this.mFieldToIndexdMap.get(field).intValue();
                if (this.mFieldIds.get(intValue) == null) {
                    this.mFieldIds.set(intValue, autofillId);
                    return this;
                }
            }
            if (field == null) {
                lifeTheUniverseAndEverything = setLifeTheUniverseAndEverything(autofillId, (android.view.autofill.AutofillValue) null, (android.widget.RemoteViews) null, (android.service.autofill.InlinePresentation) null, (android.service.autofill.InlinePresentation) null, (android.service.autofill.Dataset.DatasetFieldFilter) null, (android.widget.RemoteViews) null);
            } else {
                android.service.autofill.Dataset.DatasetFieldFilter datasetFieldFilter = field.getDatasetFieldFilter();
                android.service.autofill.Presentations presentations = field.getPresentations();
                if (presentations == null) {
                    lifeTheUniverseAndEverything = setLifeTheUniverseAndEverything(autofillId, field.getValue(), (android.widget.RemoteViews) null, (android.service.autofill.InlinePresentation) null, (android.service.autofill.InlinePresentation) null, datasetFieldFilter, (android.widget.RemoteViews) null);
                } else {
                    lifeTheUniverseAndEverything = setLifeTheUniverseAndEverything(autofillId, field.getValue(), presentations.getMenuPresentation(), presentations.getInlinePresentation(), presentations.getInlineTooltipPresentation(), datasetFieldFilter, presentations.getDialogPresentation());
                }
            }
            this.mFieldToIndexdMap.put(field, java.lang.Integer.valueOf(lifeTheUniverseAndEverything));
            return this;
        }

        public android.service.autofill.Dataset.Builder setField(java.lang.String str, android.service.autofill.Field field) {
            int lifeTheUniverseAndEverything;
            throwIfDestroyed();
            if (this.mFieldToIndexdMap.containsKey(field)) {
                int intValue = this.mFieldToIndexdMap.get(field).intValue();
                if (this.mAutofillDatatypes.get(intValue) == null) {
                    this.mAutofillDatatypes.set(intValue, str);
                    return this;
                }
            }
            android.service.autofill.Dataset.DatasetFieldFilter datasetFieldFilter = field.getDatasetFieldFilter();
            android.service.autofill.Presentations presentations = field.getPresentations();
            if (presentations == null) {
                lifeTheUniverseAndEverything = setLifeTheUniverseAndEverything(str, field.getValue(), (android.widget.RemoteViews) null, (android.service.autofill.InlinePresentation) null, (android.service.autofill.InlinePresentation) null, datasetFieldFilter, (android.widget.RemoteViews) null);
            } else {
                lifeTheUniverseAndEverything = setLifeTheUniverseAndEverything(str, field.getValue(), presentations.getMenuPresentation(), presentations.getInlinePresentation(), presentations.getInlineTooltipPresentation(), datasetFieldFilter, presentations.getDialogPresentation());
            }
            this.mFieldToIndexdMap.put(field, java.lang.Integer.valueOf(lifeTheUniverseAndEverything));
            return this;
        }

        public android.service.autofill.Dataset.Builder setFieldForAllHints(android.service.autofill.Field field) {
            return setField(android.view.autofill.AutofillManager.ANY_HINT, field);
        }

        @android.annotation.SystemApi
        @java.lang.Deprecated
        public android.service.autofill.Dataset.Builder setFieldInlinePresentation(android.view.autofill.AutofillId autofillId, android.view.autofill.AutofillValue autofillValue, java.util.regex.Pattern pattern, android.service.autofill.InlinePresentation inlinePresentation) {
            throwIfDestroyed();
            java.util.Objects.requireNonNull(inlinePresentation, "inlinePresentation cannot be null");
            setLifeTheUniverseAndEverything(autofillId, autofillValue, (android.widget.RemoteViews) null, inlinePresentation, (android.service.autofill.InlinePresentation) null, new android.service.autofill.Dataset.DatasetFieldFilter(pattern), (android.widget.RemoteViews) null);
            return this;
        }

        private int setLifeTheUniverseAndEverything(java.lang.String str, android.view.autofill.AutofillValue autofillValue, android.widget.RemoteViews remoteViews, android.service.autofill.InlinePresentation inlinePresentation, android.service.autofill.InlinePresentation inlinePresentation2, android.service.autofill.Dataset.DatasetFieldFilter datasetFieldFilter, android.widget.RemoteViews remoteViews2) {
            java.util.Objects.requireNonNull(str, "datatype cannot be null");
            int indexOf = this.mAutofillDatatypes.indexOf(str);
            if (indexOf >= 0) {
                this.mAutofillDatatypes.add(str);
                this.mFieldValues.set(indexOf, autofillValue);
                this.mFieldPresentations.set(indexOf, remoteViews);
                this.mFieldDialogPresentations.set(indexOf, remoteViews2);
                this.mFieldInlinePresentations.set(indexOf, inlinePresentation);
                this.mFieldInlineTooltipPresentations.set(indexOf, inlinePresentation2);
                this.mFieldFilters.set(indexOf, datasetFieldFilter);
                return indexOf;
            }
            this.mFieldIds.add(null);
            this.mAutofillDatatypes.add(str);
            this.mFieldValues.add(autofillValue);
            this.mFieldPresentations.add(remoteViews);
            this.mFieldDialogPresentations.add(remoteViews2);
            this.mFieldInlinePresentations.add(inlinePresentation);
            this.mFieldInlineTooltipPresentations.add(inlinePresentation2);
            this.mFieldFilters.add(datasetFieldFilter);
            return this.mFieldIds.size() - 1;
        }

        private int setLifeTheUniverseAndEverything(android.view.autofill.AutofillId autofillId, android.view.autofill.AutofillValue autofillValue, android.widget.RemoteViews remoteViews, android.service.autofill.InlinePresentation inlinePresentation, android.service.autofill.InlinePresentation inlinePresentation2, android.service.autofill.Dataset.DatasetFieldFilter datasetFieldFilter, android.widget.RemoteViews remoteViews2) {
            java.util.Objects.requireNonNull(autofillId, "id cannot be null");
            int indexOf = this.mFieldIds.indexOf(autofillId);
            if (indexOf >= 0) {
                this.mFieldValues.set(indexOf, autofillValue);
                this.mFieldPresentations.set(indexOf, remoteViews);
                this.mFieldDialogPresentations.set(indexOf, remoteViews2);
                this.mFieldInlinePresentations.set(indexOf, inlinePresentation);
                this.mFieldInlineTooltipPresentations.set(indexOf, inlinePresentation2);
                this.mFieldFilters.set(indexOf, datasetFieldFilter);
                return indexOf;
            }
            this.mFieldIds.add(autofillId);
            this.mAutofillDatatypes.add(null);
            this.mFieldValues.add(autofillValue);
            this.mFieldPresentations.add(remoteViews);
            this.mFieldDialogPresentations.add(remoteViews2);
            this.mFieldInlinePresentations.add(inlinePresentation);
            this.mFieldInlineTooltipPresentations.add(inlinePresentation2);
            this.mFieldFilters.add(datasetFieldFilter);
            return this.mFieldIds.size() - 1;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void createFromParcel(android.view.autofill.AutofillId autofillId, java.lang.String str, android.view.autofill.AutofillValue autofillValue, android.widget.RemoteViews remoteViews, android.service.autofill.InlinePresentation inlinePresentation, android.service.autofill.InlinePresentation inlinePresentation2, android.service.autofill.Dataset.DatasetFieldFilter datasetFieldFilter, android.widget.RemoteViews remoteViews2) {
            int indexOf;
            if (autofillId != null && (indexOf = this.mFieldIds.indexOf(autofillId)) >= 0) {
                this.mFieldValues.set(indexOf, autofillValue);
                this.mFieldPresentations.set(indexOf, remoteViews);
                this.mFieldDialogPresentations.set(indexOf, remoteViews2);
                this.mFieldInlinePresentations.set(indexOf, inlinePresentation);
                this.mFieldInlineTooltipPresentations.set(indexOf, inlinePresentation2);
                this.mFieldFilters.set(indexOf, datasetFieldFilter);
                return;
            }
            this.mFieldIds.add(autofillId);
            this.mAutofillDatatypes.add(str);
            this.mFieldValues.add(autofillValue);
            this.mFieldPresentations.add(remoteViews);
            this.mFieldDialogPresentations.add(remoteViews2);
            this.mFieldInlinePresentations.add(inlinePresentation);
            this.mFieldInlineTooltipPresentations.add(inlinePresentation2);
            this.mFieldFilters.add(datasetFieldFilter);
        }

        public android.service.autofill.Dataset build() {
            throwIfDestroyed();
            this.mDestroyed = true;
            if (this.mFieldIds == null && this.mAutofillDatatypes == null) {
                throw new java.lang.IllegalStateException("at least one of field or datatype must be set");
            }
            if (this.mFieldIds != null && this.mAutofillDatatypes != null && this.mFieldIds.size() == 0 && this.mAutofillDatatypes.size() == 0) {
                throw new java.lang.IllegalStateException("at least one of field or datatype must be set");
            }
            if (this.mFieldContent != null) {
                if (this.mFieldIds.size() > 1) {
                    throw new java.lang.IllegalStateException("when filling content, only one field can be filled");
                }
                if (this.mFieldValues.get(0) != null) {
                    throw new java.lang.IllegalStateException("cannot fill both content and values");
                }
            }
            return new android.service.autofill.Dataset(this);
        }

        private void throwIfDestroyed() {
            if (this.mDestroyed) {
                throw new java.lang.IllegalStateException("Already called #build()");
            }
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeParcelable(this.mPresentation, i);
        parcel.writeParcelable(this.mDialogPresentation, i);
        parcel.writeParcelable(this.mInlinePresentation, i);
        parcel.writeParcelable(this.mInlineTooltipPresentation, i);
        parcel.writeTypedList(this.mFieldIds, i);
        parcel.writeTypedList(this.mFieldValues, i);
        parcel.writeTypedList(this.mFieldPresentations, i);
        parcel.writeTypedList(this.mFieldDialogPresentations, i);
        parcel.writeTypedList(this.mFieldInlinePresentations, i);
        parcel.writeTypedList(this.mFieldInlineTooltipPresentations, i);
        parcel.writeTypedList(this.mFieldFilters, i);
        parcel.writeStringList(this.mAutofillDatatypes);
        parcel.writeParcelable(this.mFieldContent, i);
        parcel.writeParcelable(this.mAuthentication, i);
        parcel.writeString(this.mId);
        parcel.writeInt(this.mEligibleReason);
        parcel.writeTypedObject(this.mCredentialFillInIntent, i);
    }

    public static final class DatasetFieldFilter implements android.os.Parcelable {
        public static final android.os.Parcelable.Creator<android.service.autofill.Dataset.DatasetFieldFilter> CREATOR = new android.os.Parcelable.Creator<android.service.autofill.Dataset.DatasetFieldFilter>() { // from class: android.service.autofill.Dataset.DatasetFieldFilter.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.service.autofill.Dataset.DatasetFieldFilter createFromParcel(android.os.Parcel parcel) {
                return new android.service.autofill.Dataset.DatasetFieldFilter((java.util.regex.Pattern) parcel.readSerializable(java.util.regex.Pattern.class.getClassLoader(), java.util.regex.Pattern.class));
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.service.autofill.Dataset.DatasetFieldFilter[] newArray(int i) {
                return new android.service.autofill.Dataset.DatasetFieldFilter[i];
            }
        };
        public final java.util.regex.Pattern pattern;

        DatasetFieldFilter(java.util.regex.Pattern pattern) {
            this.pattern = pattern;
        }

        public java.util.regex.Pattern getPattern() {
            return this.pattern;
        }

        public java.lang.String toString() {
            return !android.view.autofill.Helper.sDebug ? super.toString() : this.pattern == null ? "null" : this.pattern.pattern().length() + "_chars";
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(android.os.Parcel parcel, int i) {
            parcel.writeSerializable(this.pattern);
        }
    }
}
