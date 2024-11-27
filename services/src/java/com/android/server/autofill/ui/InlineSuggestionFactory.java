package com.android.server.autofill.ui;

/* loaded from: classes.dex */
final class InlineSuggestionFactory {
    private static final java.lang.String TAG = "InlineSuggestionFactory";

    public static android.view.inputmethod.InlineSuggestion createInlineAuthentication(@android.annotation.NonNull com.android.server.autofill.ui.InlineFillUi.InlineFillUiInfo inlineFillUiInfo, @android.annotation.NonNull android.service.autofill.FillResponse fillResponse, @android.annotation.NonNull final com.android.server.autofill.ui.InlineFillUi.InlineSuggestionUiCallback inlineSuggestionUiCallback) {
        android.service.autofill.InlinePresentation inlinePresentation = fillResponse.getInlinePresentation();
        final int requestId = fillResponse.getRequestId();
        return createInlineSuggestion(inlineFillUiInfo, "android:autofill", "android:autofill:action", new java.lang.Runnable() { // from class: com.android.server.autofill.ui.InlineSuggestionFactory$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.autofill.ui.InlineFillUi.InlineSuggestionUiCallback.this.authenticate(requestId, com.android.server.location.gnss.hal.GnssNative.GNSS_AIDING_TYPE_ALL);
            }
        }, mergedInlinePresentation(inlineFillUiInfo.mInlineRequest, 0, inlinePresentation, android.service.autofill.Flags.autofillCredmanIntegration() && (fillResponse.getFlags() & 8) != 0), createInlineSuggestionTooltip(inlineFillUiInfo.mInlineRequest, inlineFillUiInfo, "android:autofill", fillResponse.getInlineTooltipPresentation()), inlineSuggestionUiCallback);
    }

    @android.annotation.Nullable
    public static android.util.SparseArray<android.util.Pair<android.service.autofill.Dataset, android.view.inputmethod.InlineSuggestion>> createInlineSuggestions(@android.annotation.NonNull com.android.server.autofill.ui.InlineFillUi.InlineFillUiInfo inlineFillUiInfo, @android.annotation.NonNull java.lang.String str, @android.annotation.NonNull java.util.List<android.service.autofill.Dataset> list, @android.annotation.NonNull final com.android.server.autofill.ui.InlineFillUi.InlineSuggestionUiCallback inlineSuggestionUiCallback, boolean z) {
        boolean z2;
        android.view.inputmethod.InlineSuggestion inlineSuggestion;
        if (com.android.server.autofill.Helper.sDebug) {
            android.util.Slog.d(TAG, "createInlineSuggestions(source=" + str + ") called");
        }
        android.view.inputmethod.InlineSuggestionsRequest inlineSuggestionsRequest = inlineFillUiInfo.mInlineRequest;
        android.util.SparseArray<android.util.Pair<android.service.autofill.Dataset, android.view.inputmethod.InlineSuggestion>> sparseArray = new android.util.SparseArray<>(list.size());
        boolean z3 = false;
        for (final int i = 0; i < list.size(); i++) {
            final android.service.autofill.Dataset dataset = list.get(i);
            int indexOf = dataset.getFieldIds().indexOf(inlineFillUiInfo.mFocusedId);
            if (indexOf < 0) {
                android.util.Slog.w(TAG, "AutofillId=" + inlineFillUiInfo.mFocusedId + " not found in dataset");
            } else {
                android.service.autofill.InlinePresentation fieldInlinePresentation = dataset.getFieldInlinePresentation(indexOf);
                if (fieldInlinePresentation == null) {
                    android.util.Slog.w(TAG, "InlinePresentation not found in dataset");
                } else {
                    java.lang.String str2 = dataset.getAuthentication() == null ? "android:autofill:suggestion" : "android:autofill:action";
                    if (z3) {
                        z2 = z3;
                        inlineSuggestion = null;
                    } else {
                        android.view.inputmethod.InlineSuggestion createInlineSuggestionTooltip = createInlineSuggestionTooltip(inlineSuggestionsRequest, inlineFillUiInfo, str, dataset.getFieldInlineTooltipPresentation(indexOf));
                        if (createInlineSuggestionTooltip == null) {
                            z2 = z3;
                            inlineSuggestion = createInlineSuggestionTooltip;
                        } else {
                            z2 = true;
                            inlineSuggestion = createInlineSuggestionTooltip;
                        }
                    }
                    sparseArray.append(i, android.util.Pair.create(dataset, createInlineSuggestion(inlineFillUiInfo, str, str2, new java.lang.Runnable() { // from class: com.android.server.autofill.ui.InlineSuggestionFactory$$ExternalSyntheticLambda2
                        @Override // java.lang.Runnable
                        public final void run() {
                            com.android.server.autofill.ui.InlineFillUi.InlineSuggestionUiCallback.this.autofill(dataset, i);
                        }
                    }, mergedInlinePresentation(inlineSuggestionsRequest, i, fieldInlinePresentation, z), inlineSuggestion, inlineSuggestionUiCallback)));
                    z3 = z2;
                }
            }
        }
        return sparseArray;
    }

    private static android.view.inputmethod.InlineSuggestion createInlineSuggestion(@android.annotation.NonNull com.android.server.autofill.ui.InlineFillUi.InlineFillUiInfo inlineFillUiInfo, @android.annotation.NonNull java.lang.String str, @android.annotation.NonNull java.lang.String str2, @android.annotation.NonNull java.lang.Runnable runnable, @android.annotation.NonNull android.service.autofill.InlinePresentation inlinePresentation, @android.annotation.Nullable android.view.inputmethod.InlineSuggestion inlineSuggestion, @android.annotation.NonNull com.android.server.autofill.ui.InlineFillUi.InlineSuggestionUiCallback inlineSuggestionUiCallback) {
        return new android.view.inputmethod.InlineSuggestion(new android.view.inputmethod.InlineSuggestionInfo(inlinePresentation.getInlinePresentationSpec(), str, inlinePresentation.getAutofillHints(), str2, inlinePresentation.isPinned(), inlineSuggestion), createInlineContentProvider(inlineFillUiInfo, inlinePresentation, runnable, inlineSuggestionUiCallback));
    }

    private static android.service.autofill.InlinePresentation mergedInlinePresentation(@android.annotation.NonNull android.view.inputmethod.InlineSuggestionsRequest inlineSuggestionsRequest, int i, @android.annotation.NonNull android.service.autofill.InlinePresentation inlinePresentation, boolean z) {
        java.util.List<android.widget.inline.InlinePresentationSpec> inlinePresentationSpecs = inlineSuggestionsRequest.getInlinePresentationSpecs();
        if (inlinePresentationSpecs.isEmpty()) {
            return inlinePresentation;
        }
        android.widget.inline.InlinePresentationSpec inlinePresentationSpec = inlinePresentationSpecs.get(java.lang.Math.min(inlinePresentationSpecs.size() - 1, i));
        if (z) {
            inlinePresentationSpec = inlinePresentation.getInlinePresentationSpec();
        }
        return new android.service.autofill.InlinePresentation(inlinePresentation.getSlice(), new android.widget.inline.InlinePresentationSpec.Builder(inlinePresentation.getInlinePresentationSpec().getMinSize(), inlinePresentation.getInlinePresentationSpec().getMaxSize()).setStyle(inlinePresentationSpec.getStyle()).build(), inlinePresentation.isPinned());
    }

    private static android.view.inputmethod.InlineSuggestion createInlineSuggestionTooltip(@android.annotation.NonNull android.view.inputmethod.InlineSuggestionsRequest inlineSuggestionsRequest, @android.annotation.NonNull com.android.server.autofill.ui.InlineFillUi.InlineFillUiInfo inlineFillUiInfo, java.lang.String str, @android.annotation.NonNull android.service.autofill.InlinePresentation inlinePresentation) {
        android.widget.inline.InlinePresentationSpec build;
        if (inlinePresentation == null) {
            return null;
        }
        android.widget.inline.InlinePresentationSpec inlineTooltipPresentationSpec = inlineSuggestionsRequest.getInlineTooltipPresentationSpec();
        if (inlineTooltipPresentationSpec == null) {
            build = inlinePresentation.getInlinePresentationSpec();
        } else {
            build = new android.widget.inline.InlinePresentationSpec.Builder(inlinePresentation.getInlinePresentationSpec().getMinSize(), inlinePresentation.getInlinePresentationSpec().getMaxSize()).setStyle(inlineTooltipPresentationSpec.getStyle()).build();
        }
        return new android.view.inputmethod.InlineSuggestion(new android.view.inputmethod.InlineSuggestionInfo(build, str, null, "android:autofill:suggestion", false, null), createInlineContentProvider(inlineFillUiInfo, new android.service.autofill.InlinePresentation(inlinePresentation.getSlice(), build, false), new java.lang.Runnable() { // from class: com.android.server.autofill.ui.InlineSuggestionFactory$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.autofill.ui.InlineSuggestionFactory.lambda$createInlineSuggestionTooltip$2();
            }
        }, new com.android.server.autofill.ui.InlineFillUi.InlineSuggestionUiCallback() { // from class: com.android.server.autofill.ui.InlineSuggestionFactory.1
            @Override // com.android.server.autofill.ui.InlineFillUi.InlineSuggestionUiCallback
            public void autofill(android.service.autofill.Dataset dataset, int i) {
            }

            @Override // com.android.server.autofill.ui.InlineFillUi.InlineSuggestionUiCallback
            public void authenticate(int i, int i2) {
            }

            @Override // com.android.server.autofill.ui.InlineFillUi.InlineSuggestionUiCallback
            public void startIntentSender(android.content.IntentSender intentSender) {
            }

            @Override // com.android.server.autofill.ui.InlineFillUi.InlineSuggestionUiCallback
            public void onError() {
                android.util.Slog.w(com.android.server.autofill.ui.InlineSuggestionFactory.TAG, "An error happened on the tooltip");
            }

            @Override // com.android.server.autofill.ui.InlineFillUi.InlineSuggestionUiCallback
            public void onInflate() {
            }
        }));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$createInlineSuggestionTooltip$2() {
    }

    private static com.android.internal.view.inline.IInlineContentProvider createInlineContentProvider(@android.annotation.NonNull com.android.server.autofill.ui.InlineFillUi.InlineFillUiInfo inlineFillUiInfo, @android.annotation.NonNull android.service.autofill.InlinePresentation inlinePresentation, @android.annotation.Nullable java.lang.Runnable runnable, @android.annotation.NonNull com.android.server.autofill.ui.InlineFillUi.InlineSuggestionUiCallback inlineSuggestionUiCallback) {
        return new com.android.server.autofill.ui.InlineContentProviderImpl(new com.android.server.autofill.ui.RemoteInlineSuggestionViewConnector(inlineFillUiInfo, inlinePresentation, runnable, inlineSuggestionUiCallback), null);
    }

    private InlineSuggestionFactory() {
    }
}
