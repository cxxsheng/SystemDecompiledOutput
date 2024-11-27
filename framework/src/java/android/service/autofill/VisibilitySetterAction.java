package android.service.autofill;

/* loaded from: classes3.dex */
public final class VisibilitySetterAction extends android.service.autofill.InternalOnClickAction implements android.service.autofill.OnClickAction, android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.service.autofill.VisibilitySetterAction> CREATOR = new android.os.Parcelable.Creator<android.service.autofill.VisibilitySetterAction>() { // from class: android.service.autofill.VisibilitySetterAction.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.service.autofill.VisibilitySetterAction createFromParcel(android.os.Parcel parcel) {
            android.util.SparseIntArray readSparseIntArray = parcel.readSparseIntArray();
            android.service.autofill.VisibilitySetterAction.Builder builder = null;
            for (int i = 0; i < readSparseIntArray.size(); i++) {
                int keyAt = readSparseIntArray.keyAt(i);
                int valueAt = readSparseIntArray.valueAt(i);
                if (builder == null) {
                    builder = new android.service.autofill.VisibilitySetterAction.Builder(keyAt, valueAt);
                } else {
                    builder.setVisibility(keyAt, valueAt);
                }
            }
            if (builder == null) {
                return null;
            }
            return builder.build();
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.service.autofill.VisibilitySetterAction[] newArray(int i) {
            return new android.service.autofill.VisibilitySetterAction[i];
        }
    };
    private static final java.lang.String TAG = "VisibilitySetterAction";
    private final android.util.SparseIntArray mVisibilities;

    private VisibilitySetterAction(android.service.autofill.VisibilitySetterAction.Builder builder) {
        this.mVisibilities = builder.mVisibilities;
    }

    @Override // android.service.autofill.InternalOnClickAction
    public void onClick(android.view.ViewGroup viewGroup) {
        for (int i = 0; i < this.mVisibilities.size(); i++) {
            int keyAt = this.mVisibilities.keyAt(i);
            android.view.View findViewById = viewGroup.findViewById(keyAt);
            if (findViewById == null) {
                android.util.Slog.w(TAG, "Skipping view id " + keyAt + " because it's not found on " + viewGroup);
            } else {
                int valueAt = this.mVisibilities.valueAt(i);
                if (android.view.autofill.Helper.sVerbose) {
                    android.util.Slog.v(TAG, "Changing visibility of view " + findViewById + " from " + findViewById.getVisibility() + " to  " + valueAt);
                }
                findViewById.setVisibility(valueAt);
            }
        }
    }

    public static final class Builder {
        private boolean mDestroyed;
        private final android.util.SparseIntArray mVisibilities = new android.util.SparseIntArray();

        public Builder(int i, int i2) {
            setVisibility(i, i2);
        }

        public android.service.autofill.VisibilitySetterAction.Builder setVisibility(int i, int i2) {
            throwIfDestroyed();
            switch (i2) {
                case 0:
                case 4:
                case 8:
                    this.mVisibilities.put(i, i2);
                    return this;
                default:
                    throw new java.lang.IllegalArgumentException("Invalid visibility: " + i2);
            }
        }

        public android.service.autofill.VisibilitySetterAction build() {
            throwIfDestroyed();
            this.mDestroyed = true;
            return new android.service.autofill.VisibilitySetterAction(this);
        }

        private void throwIfDestroyed() {
            com.android.internal.util.Preconditions.checkState(!this.mDestroyed, "Already called build()");
        }
    }

    public java.lang.String toString() {
        return !android.view.autofill.Helper.sDebug ? super.toString() : "VisibilitySetterAction: [" + this.mVisibilities + android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_END;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeSparseIntArray(this.mVisibilities);
    }
}
