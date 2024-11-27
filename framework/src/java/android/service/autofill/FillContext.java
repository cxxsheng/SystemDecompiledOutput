package android.service.autofill;

/* loaded from: classes3.dex */
public final class FillContext implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.service.autofill.FillContext> CREATOR = new android.os.Parcelable.Creator<android.service.autofill.FillContext>() { // from class: android.service.autofill.FillContext.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.service.autofill.FillContext[] newArray(int i) {
            return new android.service.autofill.FillContext[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.service.autofill.FillContext createFromParcel(android.os.Parcel parcel) {
            return new android.service.autofill.FillContext(parcel.readInt(), (android.app.assist.AssistStructure) parcel.readTypedObject(android.app.assist.AssistStructure.CREATOR), (android.view.autofill.AutofillId) parcel.readTypedObject(android.view.autofill.AutofillId.CREATOR));
        }
    };
    private final android.view.autofill.AutofillId mFocusedId;
    private final int mRequestId;
    private final android.app.assist.AssistStructure mStructure;
    private transient android.util.ArrayMap<android.view.autofill.AutofillId, android.app.assist.AssistStructure.ViewNode> mViewNodeLookupTable;

    public java.lang.String toString() {
        return !android.view.autofill.Helper.sDebug ? super.toString() : "FillContext [reqId=" + this.mRequestId + ", focusedId=" + this.mFocusedId + android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_END;
    }

    public android.app.assist.AssistStructure.ViewNode[] findViewNodesByAutofillIds(android.view.autofill.AutofillId[] autofillIdArr) {
        java.util.ArrayDeque arrayDeque = new java.util.ArrayDeque();
        android.app.assist.AssistStructure.ViewNode[] viewNodeArr = new android.app.assist.AssistStructure.ViewNode[autofillIdArr.length];
        android.util.SparseIntArray sparseIntArray = new android.util.SparseIntArray(autofillIdArr.length);
        for (int i = 0; i < autofillIdArr.length; i++) {
            if (this.mViewNodeLookupTable != null) {
                int indexOfKey = this.mViewNodeLookupTable.indexOfKey(autofillIdArr[i]);
                if (indexOfKey >= 0) {
                    viewNodeArr[i] = this.mViewNodeLookupTable.valueAt(indexOfKey);
                } else {
                    sparseIntArray.put(i, 0);
                }
            } else {
                sparseIntArray.put(i, 0);
            }
        }
        int windowNodeCount = this.mStructure.getWindowNodeCount();
        for (int i2 = 0; i2 < windowNodeCount; i2++) {
            arrayDeque.add(this.mStructure.getWindowNodeAt(i2).getRootViewNode());
        }
        while (sparseIntArray.size() > 0 && !arrayDeque.isEmpty()) {
            android.app.assist.AssistStructure.ViewNode viewNode = (android.app.assist.AssistStructure.ViewNode) arrayDeque.removeFirst();
            int i3 = 0;
            while (true) {
                if (i3 >= sparseIntArray.size()) {
                    break;
                }
                int keyAt = sparseIntArray.keyAt(i3);
                android.view.autofill.AutofillId autofillId = autofillIdArr[keyAt];
                if (autofillId == null || !autofillId.equals(viewNode.getAutofillId())) {
                    i3++;
                } else {
                    viewNodeArr[keyAt] = viewNode;
                    if (this.mViewNodeLookupTable == null) {
                        this.mViewNodeLookupTable = new android.util.ArrayMap<>(autofillIdArr.length);
                    }
                    this.mViewNodeLookupTable.put(autofillId, viewNode);
                    sparseIntArray.removeAt(i3);
                }
            }
            for (int i4 = 0; i4 < viewNode.getChildCount(); i4++) {
                arrayDeque.addLast(viewNode.getChildAt(i4));
            }
        }
        for (int i5 = 0; i5 < sparseIntArray.size(); i5++) {
            if (this.mViewNodeLookupTable == null) {
                this.mViewNodeLookupTable = new android.util.ArrayMap<>(sparseIntArray.size());
            }
            this.mViewNodeLookupTable.put(autofillIdArr[sparseIntArray.keyAt(i5)], null);
        }
        return viewNodeArr;
    }

    public FillContext(int i, android.app.assist.AssistStructure assistStructure, android.view.autofill.AutofillId autofillId) {
        this.mRequestId = i;
        this.mStructure = assistStructure;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mStructure);
        this.mFocusedId = autofillId;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mFocusedId);
    }

    public int getRequestId() {
        return this.mRequestId;
    }

    public android.app.assist.AssistStructure getStructure() {
        return this.mStructure;
    }

    public android.view.autofill.AutofillId getFocusedId() {
        return this.mFocusedId;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mRequestId);
        parcel.writeTypedObject(this.mStructure, i);
        parcel.writeTypedObject(this.mFocusedId, i);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @java.lang.Deprecated
    private void __metadata() {
    }
}
