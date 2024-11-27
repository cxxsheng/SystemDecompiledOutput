package android.service.autofill;

/* loaded from: classes3.dex */
public final class BatchUpdates implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.service.autofill.BatchUpdates> CREATOR = new android.os.Parcelable.Creator<android.service.autofill.BatchUpdates>() { // from class: android.service.autofill.BatchUpdates.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.service.autofill.BatchUpdates createFromParcel(android.os.Parcel parcel) {
            android.service.autofill.BatchUpdates.Builder builder = new android.service.autofill.BatchUpdates.Builder();
            int[] createIntArray = parcel.createIntArray();
            if (createIntArray != null) {
                android.service.autofill.InternalTransformation[] internalTransformationArr = (android.service.autofill.InternalTransformation[]) parcel.readParcelableArray(null, android.service.autofill.InternalTransformation.class);
                int length = createIntArray.length;
                for (int i = 0; i < length; i++) {
                    builder.transformChild(createIntArray[i], internalTransformationArr[i]);
                }
            }
            android.widget.RemoteViews remoteViews = (android.widget.RemoteViews) parcel.readParcelable(null, android.widget.RemoteViews.class);
            if (remoteViews != null) {
                builder.updateTemplate(remoteViews);
            }
            return builder.build();
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.service.autofill.BatchUpdates[] newArray(int i) {
            return new android.service.autofill.BatchUpdates[i];
        }
    };
    private final java.util.ArrayList<android.util.Pair<java.lang.Integer, android.service.autofill.InternalTransformation>> mTransformations;
    private final android.widget.RemoteViews mUpdates;

    private BatchUpdates(android.service.autofill.BatchUpdates.Builder builder) {
        this.mTransformations = builder.mTransformations;
        this.mUpdates = builder.mUpdates;
    }

    public java.util.ArrayList<android.util.Pair<java.lang.Integer, android.service.autofill.InternalTransformation>> getTransformations() {
        return this.mTransformations;
    }

    public android.widget.RemoteViews getUpdates() {
        return this.mUpdates;
    }

    public static class Builder {
        private boolean mDestroyed;
        private java.util.ArrayList<android.util.Pair<java.lang.Integer, android.service.autofill.InternalTransformation>> mTransformations;
        private android.widget.RemoteViews mUpdates;

        public android.service.autofill.BatchUpdates.Builder updateTemplate(android.widget.RemoteViews remoteViews) {
            throwIfDestroyed();
            this.mUpdates = (android.widget.RemoteViews) java.util.Objects.requireNonNull(remoteViews);
            return this;
        }

        public android.service.autofill.BatchUpdates.Builder transformChild(int i, android.service.autofill.Transformation transformation) {
            throwIfDestroyed();
            com.android.internal.util.Preconditions.checkArgument(transformation instanceof android.service.autofill.InternalTransformation, "not provided by Android System: %s", transformation);
            if (this.mTransformations == null) {
                this.mTransformations = new java.util.ArrayList<>();
            }
            this.mTransformations.add(new android.util.Pair<>(java.lang.Integer.valueOf(i), (android.service.autofill.InternalTransformation) transformation));
            return this;
        }

        public android.service.autofill.BatchUpdates build() {
            throwIfDestroyed();
            com.android.internal.util.Preconditions.checkState((this.mUpdates == null && this.mTransformations == null) ? false : true, "must call either updateTemplate() or transformChild() at least once");
            this.mDestroyed = true;
            return new android.service.autofill.BatchUpdates(this);
        }

        private void throwIfDestroyed() {
            if (this.mDestroyed) {
                throw new java.lang.IllegalStateException("Already called #build()");
            }
        }
    }

    public java.lang.String toString() {
        if (android.view.autofill.Helper.sDebug) {
            return "BatchUpdates: [, transformations=" + (this.mTransformations == null ? "N/A" : java.lang.Integer.valueOf(this.mTransformations.size())) + ", updates=" + this.mUpdates + android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_END;
        }
        return super.toString();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
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
        parcel.writeParcelable(this.mUpdates, i);
    }
}
