package android.service.autofill;

/* loaded from: classes3.dex */
public final class FillEventHistory implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.service.autofill.FillEventHistory> CREATOR = new android.os.Parcelable.Creator<android.service.autofill.FillEventHistory>() { // from class: android.service.autofill.FillEventHistory.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.service.autofill.FillEventHistory createFromParcel(android.os.Parcel parcel) {
            java.util.ArrayList arrayList;
            android.service.autofill.FieldClassification[] fieldClassificationArr;
            int i = 0;
            android.service.autofill.FillEventHistory fillEventHistory = new android.service.autofill.FillEventHistory(0, parcel.readBundle());
            int readInt = parcel.readInt();
            int i2 = 0;
            while (i2 < readInt) {
                int readInt2 = parcel.readInt();
                java.lang.String readString = parcel.readString();
                android.os.Bundle readBundle = parcel.readBundle();
                java.util.ArrayList<java.lang.String> createStringArrayList = parcel.createStringArrayList();
                android.util.ArraySet<? extends java.lang.Object> readArraySet = parcel.readArraySet(null);
                java.util.ArrayList createTypedArrayList = parcel.createTypedArrayList(android.view.autofill.AutofillId.CREATOR);
                java.util.ArrayList<java.lang.String> createStringArrayList2 = parcel.createStringArrayList();
                java.util.ArrayList createTypedArrayList2 = parcel.createTypedArrayList(android.view.autofill.AutofillId.CREATOR);
                if (createTypedArrayList2 != null) {
                    int size = createTypedArrayList2.size();
                    java.util.ArrayList arrayList2 = new java.util.ArrayList(size);
                    while (i < size) {
                        arrayList2.add(parcel.createStringArrayList());
                        i++;
                    }
                    arrayList = arrayList2;
                } else {
                    arrayList = null;
                }
                android.view.autofill.AutofillId[] autofillIdArr = (android.view.autofill.AutofillId[]) parcel.readParcelableArray(null, android.view.autofill.AutofillId.class);
                if (autofillIdArr != null) {
                    fieldClassificationArr = android.service.autofill.FieldClassification.readArrayFromParcel(parcel);
                } else {
                    fieldClassificationArr = null;
                }
                fillEventHistory.addEvent(new android.service.autofill.FillEventHistory.Event(readInt2, readString, readBundle, createStringArrayList, readArraySet, createTypedArrayList, createStringArrayList2, createTypedArrayList2, arrayList, autofillIdArr, fieldClassificationArr, parcel.readInt(), parcel.readInt()));
                i2++;
                i = 0;
            }
            return fillEventHistory;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.service.autofill.FillEventHistory[] newArray(int i) {
            return new android.service.autofill.FillEventHistory[i];
        }
    };
    private static final java.lang.String TAG = "FillEventHistory";
    private final android.os.Bundle mClientState;
    java.util.List<android.service.autofill.FillEventHistory.Event> mEvents;
    private final int mSessionId;

    public int getSessionId() {
        return this.mSessionId;
    }

    @java.lang.Deprecated
    public android.os.Bundle getClientState() {
        return this.mClientState;
    }

    public java.util.List<android.service.autofill.FillEventHistory.Event> getEvents() {
        return this.mEvents;
    }

    public void addEvent(android.service.autofill.FillEventHistory.Event event) {
        if (this.mEvents == null) {
            this.mEvents = new java.util.ArrayList(1);
        }
        this.mEvents.add(event);
    }

    public FillEventHistory(int i, android.os.Bundle bundle) {
        this.mClientState = bundle;
        this.mSessionId = i;
    }

    public java.lang.String toString() {
        return this.mEvents == null ? "no events" : this.mEvents.toString();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeBundle(this.mClientState);
        if (this.mEvents == null) {
            parcel.writeInt(0);
            return;
        }
        parcel.writeInt(this.mEvents.size());
        int size = this.mEvents.size();
        for (int i2 = 0; i2 < size; i2++) {
            android.service.autofill.FillEventHistory.Event event = this.mEvents.get(i2);
            parcel.writeInt(event.mEventType);
            parcel.writeString(event.mDatasetId);
            parcel.writeBundle(event.mClientState);
            parcel.writeStringList(event.mSelectedDatasetIds);
            parcel.writeArraySet(event.mIgnoredDatasetIds);
            parcel.writeTypedList(event.mChangedFieldIds);
            parcel.writeStringList(event.mChangedDatasetIds);
            parcel.writeTypedList(event.mManuallyFilledFieldIds);
            if (event.mManuallyFilledFieldIds != null) {
                int size2 = event.mManuallyFilledFieldIds.size();
                for (int i3 = 0; i3 < size2; i3++) {
                    parcel.writeStringList((java.util.List) event.mManuallyFilledDatasetIds.get(i3));
                }
            }
            android.view.autofill.AutofillId[] autofillIdArr = event.mDetectedFieldIds;
            parcel.writeParcelableArray(autofillIdArr, i);
            if (autofillIdArr != null) {
                android.service.autofill.FieldClassification.writeArrayToParcel(parcel, event.mDetectedFieldClassifications);
            }
            parcel.writeInt(event.mSaveDialogNotShowReason);
            parcel.writeInt(event.mUiType);
        }
    }

    public static final class Event {
        public static final int NO_SAVE_UI_REASON_DATASET_MATCH = 6;
        public static final int NO_SAVE_UI_REASON_FIELD_VALIDATION_FAILED = 5;
        public static final int NO_SAVE_UI_REASON_HAS_EMPTY_REQUIRED = 3;
        public static final int NO_SAVE_UI_REASON_NONE = 0;
        public static final int NO_SAVE_UI_REASON_NO_SAVE_INFO = 1;
        public static final int NO_SAVE_UI_REASON_NO_VALUE_CHANGED = 4;
        public static final int NO_SAVE_UI_REASON_WITH_DELAY_SAVE_FLAG = 2;
        public static final int TYPE_AUTHENTICATION_SELECTED = 2;
        public static final int TYPE_CONTEXT_COMMITTED = 4;
        public static final int TYPE_DATASETS_SHOWN = 5;
        public static final int TYPE_DATASET_AUTHENTICATION_SELECTED = 1;
        public static final int TYPE_DATASET_SELECTED = 0;
        public static final int TYPE_SAVE_SHOWN = 3;
        public static final int TYPE_VIEW_REQUESTED_AUTOFILL = 6;
        public static final int UI_TYPE_CREDMAN_BOTTOM_SHEET = 4;
        public static final int UI_TYPE_DIALOG = 3;
        public static final int UI_TYPE_INLINE = 2;
        public static final int UI_TYPE_MENU = 1;
        public static final int UI_TYPE_UNKNOWN = 0;
        private final java.util.ArrayList<java.lang.String> mChangedDatasetIds;
        private final java.util.ArrayList<android.view.autofill.AutofillId> mChangedFieldIds;
        private final android.os.Bundle mClientState;
        private final java.lang.String mDatasetId;
        private final android.service.autofill.FieldClassification[] mDetectedFieldClassifications;
        private final android.view.autofill.AutofillId[] mDetectedFieldIds;
        private final int mEventType;
        private final android.util.ArraySet<java.lang.String> mIgnoredDatasetIds;
        private final java.util.ArrayList<java.util.ArrayList<java.lang.String>> mManuallyFilledDatasetIds;
        private final java.util.ArrayList<android.view.autofill.AutofillId> mManuallyFilledFieldIds;
        private final int mSaveDialogNotShowReason;
        private final java.util.List<java.lang.String> mSelectedDatasetIds;
        private final int mUiType;

        @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
        @interface EventIds {
        }

        @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
        public @interface NoSaveReason {
        }

        @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
        public @interface UiType {
        }

        public int getType() {
            return this.mEventType;
        }

        public java.lang.String getDatasetId() {
            return this.mDatasetId;
        }

        public android.os.Bundle getClientState() {
            return this.mClientState;
        }

        public java.util.Set<java.lang.String> getSelectedDatasetIds() {
            return this.mSelectedDatasetIds == null ? java.util.Collections.emptySet() : new android.util.ArraySet(this.mSelectedDatasetIds);
        }

        public java.util.Set<java.lang.String> getIgnoredDatasetIds() {
            return this.mIgnoredDatasetIds == null ? java.util.Collections.emptySet() : this.mIgnoredDatasetIds;
        }

        public java.util.Map<android.view.autofill.AutofillId, java.lang.String> getChangedFields() {
            if (this.mChangedFieldIds == null || this.mChangedDatasetIds == null) {
                return java.util.Collections.emptyMap();
            }
            int size = this.mChangedFieldIds.size();
            android.util.ArrayMap arrayMap = new android.util.ArrayMap(size);
            for (int i = 0; i < size; i++) {
                arrayMap.put(this.mChangedFieldIds.get(i), this.mChangedDatasetIds.get(i));
            }
            return arrayMap;
        }

        public java.util.Map<android.view.autofill.AutofillId, android.service.autofill.FieldClassification> getFieldsClassification() {
            if (this.mDetectedFieldIds == null) {
                return java.util.Collections.emptyMap();
            }
            int length = this.mDetectedFieldIds.length;
            android.util.ArrayMap arrayMap = new android.util.ArrayMap(length);
            for (int i = 0; i < length; i++) {
                android.view.autofill.AutofillId autofillId = this.mDetectedFieldIds[i];
                android.service.autofill.FieldClassification fieldClassification = this.mDetectedFieldClassifications[i];
                if (android.view.autofill.Helper.sVerbose) {
                    android.util.Log.v(android.service.autofill.FillEventHistory.TAG, "getFieldsClassification[" + i + "]: id=" + autofillId + ", fc=" + fieldClassification);
                }
                arrayMap.put(autofillId, fieldClassification);
            }
            return arrayMap;
        }

        public java.util.Map<android.view.autofill.AutofillId, java.util.Set<java.lang.String>> getManuallyEnteredField() {
            if (this.mManuallyFilledFieldIds == null || this.mManuallyFilledDatasetIds == null) {
                return java.util.Collections.emptyMap();
            }
            int size = this.mManuallyFilledFieldIds.size();
            android.util.ArrayMap arrayMap = new android.util.ArrayMap(size);
            for (int i = 0; i < size; i++) {
                arrayMap.put(this.mManuallyFilledFieldIds.get(i), new android.util.ArraySet(this.mManuallyFilledDatasetIds.get(i)));
            }
            return arrayMap;
        }

        public int getNoSaveUiReason() {
            return this.mSaveDialogNotShowReason;
        }

        public int getUiType() {
            return this.mUiType;
        }

        public Event(int i, java.lang.String str, android.os.Bundle bundle, java.util.List<java.lang.String> list, android.util.ArraySet<java.lang.String> arraySet, java.util.ArrayList<android.view.autofill.AutofillId> arrayList, java.util.ArrayList<java.lang.String> arrayList2, java.util.ArrayList<android.view.autofill.AutofillId> arrayList3, java.util.ArrayList<java.util.ArrayList<java.lang.String>> arrayList4, android.view.autofill.AutofillId[] autofillIdArr, android.service.autofill.FieldClassification[] fieldClassificationArr) {
            this(i, str, bundle, list, arraySet, arrayList, arrayList2, arrayList3, arrayList4, autofillIdArr, fieldClassificationArr, 0);
        }

        public Event(int i, java.lang.String str, android.os.Bundle bundle, java.util.List<java.lang.String> list, android.util.ArraySet<java.lang.String> arraySet, java.util.ArrayList<android.view.autofill.AutofillId> arrayList, java.util.ArrayList<java.lang.String> arrayList2, java.util.ArrayList<android.view.autofill.AutofillId> arrayList3, java.util.ArrayList<java.util.ArrayList<java.lang.String>> arrayList4, android.view.autofill.AutofillId[] autofillIdArr, android.service.autofill.FieldClassification[] fieldClassificationArr, int i2) {
            this(i, str, bundle, list, arraySet, arrayList, arrayList2, arrayList3, arrayList4, autofillIdArr, fieldClassificationArr, i2, 0);
        }

        public Event(int i, java.lang.String str, android.os.Bundle bundle, java.util.List<java.lang.String> list, android.util.ArraySet<java.lang.String> arraySet, java.util.ArrayList<android.view.autofill.AutofillId> arrayList, java.util.ArrayList<java.lang.String> arrayList2, java.util.ArrayList<android.view.autofill.AutofillId> arrayList3, java.util.ArrayList<java.util.ArrayList<java.lang.String>> arrayList4, android.view.autofill.AutofillId[] autofillIdArr, android.service.autofill.FieldClassification[] fieldClassificationArr, int i2, int i3) {
            this.mEventType = com.android.internal.util.Preconditions.checkArgumentInRange(i, 0, 6, "eventType");
            this.mDatasetId = str;
            this.mClientState = bundle;
            this.mSelectedDatasetIds = list;
            this.mIgnoredDatasetIds = arraySet;
            if (arrayList != null) {
                com.android.internal.util.Preconditions.checkArgument((com.android.internal.util.ArrayUtils.isEmpty(arrayList) || arrayList2 == null || arrayList.size() != arrayList2.size()) ? false : true, "changed ids must have same length and not be empty");
            }
            this.mChangedFieldIds = arrayList;
            this.mChangedDatasetIds = arrayList2;
            if (arrayList3 != null) {
                com.android.internal.util.Preconditions.checkArgument((com.android.internal.util.ArrayUtils.isEmpty(arrayList3) || arrayList4 == null || arrayList3.size() != arrayList4.size()) ? false : true, "manually filled ids must have same length and not be empty");
            }
            this.mManuallyFilledFieldIds = arrayList3;
            this.mManuallyFilledDatasetIds = arrayList4;
            this.mDetectedFieldIds = autofillIdArr;
            this.mDetectedFieldClassifications = fieldClassificationArr;
            this.mSaveDialogNotShowReason = com.android.internal.util.Preconditions.checkArgumentInRange(i2, 0, 6, "saveDialogNotShowReason");
            this.mUiType = i3;
        }

        public java.lang.String toString() {
            return "FillEvent [datasetId=" + this.mDatasetId + ", type=" + eventToString(this.mEventType) + ", uiType=" + uiTypeToString(this.mUiType) + ", selectedDatasets=" + this.mSelectedDatasetIds + ", ignoredDatasetIds=" + this.mIgnoredDatasetIds + ", changedFieldIds=" + this.mChangedFieldIds + ", changedDatasetsIds=" + this.mChangedDatasetIds + ", manuallyFilledFieldIds=" + this.mManuallyFilledFieldIds + ", manuallyFilledDatasetIds=" + this.mManuallyFilledDatasetIds + ", detectedFieldIds=" + java.util.Arrays.toString(this.mDetectedFieldIds) + ", detectedFieldClassifications =" + java.util.Arrays.toString(this.mDetectedFieldClassifications) + ", saveDialogNotShowReason=" + this.mSaveDialogNotShowReason + android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_END;
        }

        private static java.lang.String eventToString(int i) {
            switch (i) {
                case 0:
                    return "TYPE_DATASET_SELECTED";
                case 1:
                    return "TYPE_DATASET_AUTHENTICATION_SELECTED";
                case 2:
                    return "TYPE_AUTHENTICATION_SELECTED";
                case 3:
                    return "TYPE_SAVE_SHOWN";
                case 4:
                    return "TYPE_CONTEXT_COMMITTED";
                case 5:
                    return "TYPE_DATASETS_SHOWN";
                case 6:
                    return "TYPE_VIEW_REQUESTED_AUTOFILL";
                default:
                    return "TYPE_UNKNOWN";
            }
        }

        private static java.lang.String uiTypeToString(int i) {
            switch (i) {
                case 1:
                    return "UI_TYPE_MENU";
                case 2:
                    return "UI_TYPE_INLINE";
                case 3:
                    return "UI_TYPE_FILL_DIALOG";
                case 4:
                    return "UI_TYPE_CREDMAN_BOTTOM_SHEET";
                default:
                    return "UI_TYPE_UNKNOWN";
            }
        }
    }
}
