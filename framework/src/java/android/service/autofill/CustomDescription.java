package android.service.autofill;

/* loaded from: classes3.dex */
public final class CustomDescription implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.service.autofill.CustomDescription> CREATOR = new android.os.Parcelable.Creator<android.service.autofill.CustomDescription>() { // from class: android.service.autofill.CustomDescription.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.service.autofill.CustomDescription createFromParcel(android.os.Parcel parcel) {
            android.widget.RemoteViews remoteViews = (android.widget.RemoteViews) parcel.readParcelable(null, android.widget.RemoteViews.class);
            if (remoteViews == null) {
                return null;
            }
            android.service.autofill.CustomDescription.Builder builder = new android.service.autofill.CustomDescription.Builder(remoteViews);
            int[] createIntArray = parcel.createIntArray();
            if (createIntArray != null) {
                android.service.autofill.InternalTransformation[] internalTransformationArr = (android.service.autofill.InternalTransformation[]) parcel.readParcelableArray(null, android.service.autofill.InternalTransformation.class);
                int length = createIntArray.length;
                for (int i = 0; i < length; i++) {
                    builder.addChild(createIntArray[i], internalTransformationArr[i]);
                }
            }
            android.service.autofill.InternalValidator[] internalValidatorArr = (android.service.autofill.InternalValidator[]) parcel.readParcelableArray(null, android.service.autofill.InternalValidator.class);
            if (internalValidatorArr != null) {
                android.service.autofill.BatchUpdates[] batchUpdatesArr = (android.service.autofill.BatchUpdates[]) parcel.readParcelableArray(null, android.service.autofill.BatchUpdates.class);
                int length2 = internalValidatorArr.length;
                for (int i2 = 0; i2 < length2; i2++) {
                    builder.batchUpdate(internalValidatorArr[i2], batchUpdatesArr[i2]);
                }
            }
            int[] createIntArray2 = parcel.createIntArray();
            if (createIntArray2 != null) {
                android.service.autofill.InternalOnClickAction[] internalOnClickActionArr = (android.service.autofill.InternalOnClickAction[]) parcel.readParcelableArray(null, android.service.autofill.InternalOnClickAction.class);
                int length3 = createIntArray2.length;
                for (int i3 = 0; i3 < length3; i3++) {
                    builder.addOnClickAction(createIntArray2[i3], internalOnClickActionArr[i3]);
                }
            }
            return builder.build();
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.service.autofill.CustomDescription[] newArray(int i) {
            return new android.service.autofill.CustomDescription[i];
        }
    };
    private final android.util.SparseArray<android.service.autofill.InternalOnClickAction> mActions;
    private final android.widget.RemoteViews mPresentation;
    private final java.util.ArrayList<android.util.Pair<java.lang.Integer, android.service.autofill.InternalTransformation>> mTransformations;
    private final java.util.ArrayList<android.util.Pair<android.service.autofill.InternalValidator, android.service.autofill.BatchUpdates>> mUpdates;

    private CustomDescription(android.service.autofill.CustomDescription.Builder builder) {
        this.mPresentation = builder.mPresentation;
        this.mTransformations = builder.mTransformations;
        this.mUpdates = builder.mUpdates;
        this.mActions = builder.mActions;
    }

    public android.widget.RemoteViews getPresentation() {
        return this.mPresentation;
    }

    public java.util.ArrayList<android.util.Pair<java.lang.Integer, android.service.autofill.InternalTransformation>> getTransformations() {
        return this.mTransformations;
    }

    public java.util.ArrayList<android.util.Pair<android.service.autofill.InternalValidator, android.service.autofill.BatchUpdates>> getUpdates() {
        return this.mUpdates;
    }

    public android.util.SparseArray<android.service.autofill.InternalOnClickAction> getActions() {
        return this.mActions;
    }

    public static class Builder {
        private android.util.SparseArray<android.service.autofill.InternalOnClickAction> mActions;
        private boolean mDestroyed;
        private final android.widget.RemoteViews mPresentation;
        private java.util.ArrayList<android.util.Pair<java.lang.Integer, android.service.autofill.InternalTransformation>> mTransformations;
        private java.util.ArrayList<android.util.Pair<android.service.autofill.InternalValidator, android.service.autofill.BatchUpdates>> mUpdates;

        public Builder(android.widget.RemoteViews remoteViews) {
            this.mPresentation = (android.widget.RemoteViews) java.util.Objects.requireNonNull(remoteViews);
        }

        public android.service.autofill.CustomDescription.Builder addChild(int i, android.service.autofill.Transformation transformation) {
            throwIfDestroyed();
            com.android.internal.util.Preconditions.checkArgument(transformation instanceof android.service.autofill.InternalTransformation, "not provided by Android System: %s", transformation);
            if (this.mTransformations == null) {
                this.mTransformations = new java.util.ArrayList<>();
            }
            this.mTransformations.add(new android.util.Pair<>(java.lang.Integer.valueOf(i), (android.service.autofill.InternalTransformation) transformation));
            return this;
        }

        public android.service.autofill.CustomDescription.Builder batchUpdate(android.service.autofill.Validator validator, android.service.autofill.BatchUpdates batchUpdates) {
            throwIfDestroyed();
            com.android.internal.util.Preconditions.checkArgument(validator instanceof android.service.autofill.InternalValidator, "not provided by Android System: %s", validator);
            java.util.Objects.requireNonNull(batchUpdates);
            if (this.mUpdates == null) {
                this.mUpdates = new java.util.ArrayList<>();
            }
            this.mUpdates.add(new android.util.Pair<>((android.service.autofill.InternalValidator) validator, batchUpdates));
            return this;
        }

        public android.service.autofill.CustomDescription.Builder addOnClickAction(int i, android.service.autofill.OnClickAction onClickAction) {
            throwIfDestroyed();
            com.android.internal.util.Preconditions.checkArgument(onClickAction instanceof android.service.autofill.InternalOnClickAction, "not provided by Android System: %s", onClickAction);
            if (this.mActions == null) {
                this.mActions = new android.util.SparseArray<>();
            }
            this.mActions.put(i, (android.service.autofill.InternalOnClickAction) onClickAction);
            return this;
        }

        public android.service.autofill.CustomDescription build() {
            throwIfDestroyed();
            this.mDestroyed = true;
            return new android.service.autofill.CustomDescription(this);
        }

        private void throwIfDestroyed() {
            if (this.mDestroyed) {
                throw new java.lang.IllegalStateException("Already called #build()");
            }
        }
    }

    public java.lang.String toString() {
        if (android.view.autofill.Helper.sDebug) {
            return "CustomDescription: [presentation=" + this.mPresentation + ", transformations=" + (this.mTransformations == null ? "N/A" : java.lang.Integer.valueOf(this.mTransformations.size())) + ", updates=" + (this.mUpdates == null ? "N/A" : java.lang.Integer.valueOf(this.mUpdates.size())) + ", actions=" + (this.mActions != null ? java.lang.Integer.valueOf(this.mActions.size()) : "N/A") + android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_END;
        }
        return super.toString();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeParcelable(this.mPresentation, i);
        if (this.mPresentation == null) {
            return;
        }
        if (this.mTransformations == null) {
            parcel.writeIntArray(null);
        } else {
            int size = this.mTransformations.size();
            int[] iArr = new int[size];
            android.service.autofill.InternalTransformation[] internalTransformationArr = new android.service.autofill.InternalTransformation[size];
            for (int i2 = 0; i2 < size; i2++) {
                android.util.Pair<java.lang.Integer, android.service.autofill.InternalTransformation> pair = this.mTransformations.get(i2);
                iArr[i2] = pair.first.intValue();
                internalTransformationArr[i2] = pair.second;
            }
            parcel.writeIntArray(iArr);
            parcel.writeParcelableArray(internalTransformationArr, i);
        }
        if (this.mUpdates == null) {
            parcel.writeParcelableArray(null, i);
        } else {
            int size2 = this.mUpdates.size();
            android.service.autofill.InternalValidator[] internalValidatorArr = new android.service.autofill.InternalValidator[size2];
            android.service.autofill.BatchUpdates[] batchUpdatesArr = new android.service.autofill.BatchUpdates[size2];
            for (int i3 = 0; i3 < size2; i3++) {
                android.util.Pair<android.service.autofill.InternalValidator, android.service.autofill.BatchUpdates> pair2 = this.mUpdates.get(i3);
                internalValidatorArr[i3] = pair2.first;
                batchUpdatesArr[i3] = pair2.second;
            }
            parcel.writeParcelableArray(internalValidatorArr, i);
            parcel.writeParcelableArray(batchUpdatesArr, i);
        }
        if (this.mActions == null) {
            parcel.writeIntArray(null);
            return;
        }
        int size3 = this.mActions.size();
        int[] iArr2 = new int[size3];
        android.service.autofill.InternalOnClickAction[] internalOnClickActionArr = new android.service.autofill.InternalOnClickAction[size3];
        for (int i4 = 0; i4 < size3; i4++) {
            iArr2[i4] = this.mActions.keyAt(i4);
            internalOnClickActionArr[i4] = this.mActions.valueAt(i4);
        }
        parcel.writeIntArray(iArr2);
        parcel.writeParcelableArray(internalOnClickActionArr, i);
    }
}
