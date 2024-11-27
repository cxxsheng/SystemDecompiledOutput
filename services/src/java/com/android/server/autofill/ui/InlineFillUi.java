package com.android.server.autofill.ui;

/* loaded from: classes.dex */
public final class InlineFillUi {
    private static final java.lang.String TAG = "InlineFillUi";

    @android.annotation.NonNull
    final android.view.autofill.AutofillId mAutofillId;

    @android.annotation.Nullable
    private final java.util.ArrayList<android.service.autofill.Dataset> mDatasets;
    private boolean mFilterMatchingDisabled;

    @android.annotation.Nullable
    private java.lang.String mFilterText;

    @android.annotation.NonNull
    private final java.util.ArrayList<android.view.inputmethod.InlineSuggestion> mInlineSuggestions;
    private int mMaxInputLengthForAutofill;

    public interface InlineSuggestionUiCallback {
        void authenticate(int i, int i2);

        void autofill(@android.annotation.NonNull android.service.autofill.Dataset dataset, int i);

        void onError();

        void onInflate();

        void startIntentSender(@android.annotation.NonNull android.content.IntentSender intentSender);
    }

    public interface InlineUiEventCallback {
        void notifyInlineUiHidden(@android.annotation.NonNull android.view.autofill.AutofillId autofillId);

        void notifyInlineUiShown(@android.annotation.NonNull android.view.autofill.AutofillId autofillId);
    }

    @android.annotation.NonNull
    public static com.android.server.autofill.ui.InlineFillUi emptyUi(@android.annotation.NonNull android.view.autofill.AutofillId autofillId) {
        return new com.android.server.autofill.ui.InlineFillUi(autofillId);
    }

    public static class InlineFillUiInfo {
        public java.lang.String mFilterText;
        public android.view.autofill.AutofillId mFocusedId;
        public android.view.inputmethod.InlineSuggestionsRequest mInlineRequest;
        public com.android.server.autofill.RemoteInlineSuggestionRenderService mRemoteRenderService;
        public int mSessionId;
        public int mUserId;

        public InlineFillUiInfo(@android.annotation.NonNull android.view.inputmethod.InlineSuggestionsRequest inlineSuggestionsRequest, @android.annotation.NonNull android.view.autofill.AutofillId autofillId, @android.annotation.NonNull java.lang.String str, @android.annotation.NonNull com.android.server.autofill.RemoteInlineSuggestionRenderService remoteInlineSuggestionRenderService, int i, int i2) {
            this.mUserId = i;
            this.mSessionId = i2;
            this.mInlineRequest = inlineSuggestionsRequest;
            this.mFocusedId = autofillId;
            this.mFilterText = str;
            this.mRemoteRenderService = remoteInlineSuggestionRenderService;
        }
    }

    @android.annotation.NonNull
    public static com.android.server.autofill.ui.InlineFillUi forAutofill(@android.annotation.NonNull com.android.server.autofill.ui.InlineFillUi.InlineFillUiInfo inlineFillUiInfo, @android.annotation.NonNull android.service.autofill.FillResponse fillResponse, @android.annotation.NonNull com.android.server.autofill.ui.InlineFillUi.InlineSuggestionUiCallback inlineSuggestionUiCallback, int i) {
        if (fillResponse.getAuthentication() != null && fillResponse.getInlinePresentation() != null) {
            return new com.android.server.autofill.ui.InlineFillUi(inlineFillUiInfo, com.android.server.autofill.ui.InlineSuggestionFactory.createInlineAuthentication(inlineFillUiInfo, fillResponse, inlineSuggestionUiCallback), i);
        }
        if (fillResponse.getDatasets() != null) {
            return new com.android.server.autofill.ui.InlineFillUi(inlineFillUiInfo, com.android.server.autofill.ui.InlineSuggestionFactory.createInlineSuggestions(inlineFillUiInfo, "android:autofill", fillResponse.getDatasets(), inlineSuggestionUiCallback, android.service.autofill.Flags.autofillCredmanIntegration() && (fillResponse.getFlags() & 8) != 0), i);
        }
        return new com.android.server.autofill.ui.InlineFillUi(inlineFillUiInfo, (android.util.SparseArray<android.util.Pair<android.service.autofill.Dataset, android.view.inputmethod.InlineSuggestion>>) new android.util.SparseArray(), i);
    }

    @android.annotation.NonNull
    public static com.android.server.autofill.ui.InlineFillUi forAugmentedAutofill(@android.annotation.NonNull com.android.server.autofill.ui.InlineFillUi.InlineFillUiInfo inlineFillUiInfo, @android.annotation.NonNull java.util.List<android.service.autofill.Dataset> list, @android.annotation.NonNull com.android.server.autofill.ui.InlineFillUi.InlineSuggestionUiCallback inlineSuggestionUiCallback) {
        return new com.android.server.autofill.ui.InlineFillUi(inlineFillUiInfo, com.android.server.autofill.ui.InlineSuggestionFactory.createInlineSuggestions(inlineFillUiInfo, "android:platform", list, inlineSuggestionUiCallback, false));
    }

    private InlineFillUi(@android.annotation.Nullable com.android.server.autofill.ui.InlineFillUi.InlineFillUiInfo inlineFillUiInfo, @android.annotation.NonNull android.util.SparseArray<android.util.Pair<android.service.autofill.Dataset, android.view.inputmethod.InlineSuggestion>> sparseArray) {
        this.mMaxInputLengthForAutofill = Integer.MAX_VALUE;
        this.mAutofillId = inlineFillUiInfo.mFocusedId;
        int size = sparseArray.size();
        this.mDatasets = new java.util.ArrayList<>(size);
        this.mInlineSuggestions = new java.util.ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            android.util.Pair<android.service.autofill.Dataset, android.view.inputmethod.InlineSuggestion> valueAt = sparseArray.valueAt(i);
            this.mDatasets.add((android.service.autofill.Dataset) valueAt.first);
            this.mInlineSuggestions.add((android.view.inputmethod.InlineSuggestion) valueAt.second);
        }
        this.mFilterText = inlineFillUiInfo.mFilterText;
    }

    private InlineFillUi(@android.annotation.Nullable com.android.server.autofill.ui.InlineFillUi.InlineFillUiInfo inlineFillUiInfo, @android.annotation.NonNull android.util.SparseArray<android.util.Pair<android.service.autofill.Dataset, android.view.inputmethod.InlineSuggestion>> sparseArray, int i) {
        this.mMaxInputLengthForAutofill = Integer.MAX_VALUE;
        this.mAutofillId = inlineFillUiInfo.mFocusedId;
        int size = sparseArray.size();
        this.mDatasets = new java.util.ArrayList<>(size);
        this.mInlineSuggestions = new java.util.ArrayList<>(size);
        for (int i2 = 0; i2 < size; i2++) {
            android.util.Pair<android.service.autofill.Dataset, android.view.inputmethod.InlineSuggestion> valueAt = sparseArray.valueAt(i2);
            this.mDatasets.add((android.service.autofill.Dataset) valueAt.first);
            this.mInlineSuggestions.add((android.view.inputmethod.InlineSuggestion) valueAt.second);
        }
        this.mFilterText = inlineFillUiInfo.mFilterText;
        this.mMaxInputLengthForAutofill = i;
    }

    private InlineFillUi(@android.annotation.NonNull com.android.server.autofill.ui.InlineFillUi.InlineFillUiInfo inlineFillUiInfo, @android.annotation.NonNull android.view.inputmethod.InlineSuggestion inlineSuggestion, int i) {
        this.mMaxInputLengthForAutofill = Integer.MAX_VALUE;
        this.mAutofillId = inlineFillUiInfo.mFocusedId;
        this.mDatasets = null;
        this.mInlineSuggestions = new java.util.ArrayList<>();
        this.mInlineSuggestions.add(inlineSuggestion);
        this.mFilterText = inlineFillUiInfo.mFilterText;
        this.mMaxInputLengthForAutofill = i;
    }

    private InlineFillUi(@android.annotation.NonNull android.view.autofill.AutofillId autofillId) {
        this.mMaxInputLengthForAutofill = Integer.MAX_VALUE;
        this.mAutofillId = autofillId;
        this.mDatasets = new java.util.ArrayList<>(0);
        this.mInlineSuggestions = new java.util.ArrayList<>(0);
        this.mFilterText = null;
    }

    @android.annotation.NonNull
    public android.view.autofill.AutofillId getAutofillId() {
        return this.mAutofillId;
    }

    public void setFilterText(@android.annotation.Nullable java.lang.String str) {
        this.mFilterText = str;
    }

    @android.annotation.NonNull
    public android.view.inputmethod.InlineSuggestionsResponse getInlineSuggestionsResponse() {
        int size = this.mInlineSuggestions.size();
        if (size == 0) {
            return new android.view.inputmethod.InlineSuggestionsResponse(java.util.Collections.emptyList());
        }
        java.util.ArrayList arrayList = new java.util.ArrayList();
        int i = 0;
        if (this.mDatasets == null || this.mDatasets.size() != size) {
            while (i < size) {
                arrayList.add(copy(i, this.mInlineSuggestions.get(i)));
                i++;
            }
            return new android.view.inputmethod.InlineSuggestionsResponse(arrayList);
        }
        if (!android.text.TextUtils.isEmpty(this.mFilterText) && this.mFilterText.length() > this.mMaxInputLengthForAutofill) {
            if (com.android.server.autofill.Helper.sVerbose) {
                android.util.Slog.v(TAG, "Not showing inline suggestion when user entered more than " + this.mMaxInputLengthForAutofill + " characters");
            }
            return new android.view.inputmethod.InlineSuggestionsResponse(arrayList);
        }
        while (i < size) {
            android.service.autofill.Dataset dataset = this.mDatasets.get(i);
            int indexOf = dataset.getFieldIds().indexOf(this.mAutofillId);
            if (indexOf < 0) {
                android.util.Slog.w(TAG, "AutofillId=" + this.mAutofillId + " not found in dataset");
            } else {
                android.service.autofill.InlinePresentation fieldInlinePresentation = dataset.getFieldInlinePresentation(indexOf);
                if (fieldInlinePresentation == null) {
                    android.util.Slog.w(TAG, "InlinePresentation not found in dataset");
                } else if (fieldInlinePresentation.isPinned() || includeDataset(dataset, indexOf)) {
                    arrayList.add(copy(i, this.mInlineSuggestions.get(i)));
                }
            }
            i++;
        }
        return new android.view.inputmethod.InlineSuggestionsResponse(arrayList);
    }

    @android.annotation.NonNull
    private android.view.inputmethod.InlineSuggestion copy(int i, @android.annotation.NonNull android.view.inputmethod.InlineSuggestion inlineSuggestion) {
        com.android.server.autofill.ui.InlineContentProviderImpl contentProvider = inlineSuggestion.getContentProvider();
        if (contentProvider instanceof com.android.server.autofill.ui.InlineContentProviderImpl) {
            android.view.inputmethod.InlineSuggestion inlineSuggestion2 = new android.view.inputmethod.InlineSuggestion(inlineSuggestion.getInfo(), contentProvider.copy());
            this.mInlineSuggestions.set(i, inlineSuggestion2);
            return inlineSuggestion2;
        }
        return inlineSuggestion;
    }

    private boolean includeDataset(android.service.autofill.Dataset dataset, int i) {
        if (android.text.TextUtils.isEmpty(this.mFilterText)) {
            return true;
        }
        java.lang.String lowerCase = this.mFilterText.toString().toLowerCase();
        android.service.autofill.Dataset.DatasetFieldFilter filter = dataset.getFilter(i);
        if (filter != null) {
            java.util.regex.Pattern pattern = filter.pattern;
            if (pattern == null) {
                if (com.android.server.autofill.Helper.sVerbose) {
                    android.util.Slog.v(TAG, "Explicitly disabling filter for dataset id" + dataset.getId());
                }
                return false;
            }
            if (this.mFilterMatchingDisabled) {
                return false;
            }
            return pattern.matcher(lowerCase).matches();
        }
        android.view.autofill.AutofillValue autofillValue = (android.view.autofill.AutofillValue) dataset.getFieldValues().get(i);
        if (autofillValue == null || !autofillValue.isText()) {
            return dataset.getAuthentication() == null;
        }
        if (this.mFilterMatchingDisabled) {
            return false;
        }
        return autofillValue.getTextValue().toString().toLowerCase().toLowerCase().startsWith(lowerCase);
    }

    public void disableFilterMatching() {
        this.mFilterMatchingDisabled = true;
    }
}
