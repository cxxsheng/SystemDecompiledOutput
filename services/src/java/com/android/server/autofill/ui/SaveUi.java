package com.android.server.autofill.ui;

/* loaded from: classes.dex */
final class SaveUi {
    private static final int SCROLL_BAR_DEFAULT_DELAY_BEFORE_FADE_MS = 500;
    private static final java.lang.String TAG = "SaveUi";
    private static final int THEME_ID_DARK = 16974829;
    private static final int THEME_ID_LIGHT = 16974840;
    private final boolean mCompatMode;
    private final android.content.ComponentName mComponentName;
    private boolean mDestroyed;

    @android.annotation.NonNull
    private final android.app.Dialog mDialog;

    @android.annotation.NonNull
    private final com.android.server.autofill.ui.SaveUi.OneActionThenDestroyListener mListener;

    @android.annotation.NonNull
    private final com.android.server.autofill.ui.OverlayControl mOverlayControl;
    private final com.android.server.autofill.ui.PendingUi mPendingUi;
    private final java.lang.String mServicePackageName;
    private final java.lang.CharSequence mSubTitle;
    private final int mThemeId;
    private final java.lang.CharSequence mTitle;
    private final int mType;
    private final android.os.Handler mHandler = com.android.server.UiThread.getHandler();
    private final com.android.internal.logging.MetricsLogger mMetricsLogger = new com.android.internal.logging.MetricsLogger();

    public interface OnSaveListener {
        void onCancel(android.content.IntentSender intentSender);

        void onDestroy();

        void onSave();

        void startIntentSender(android.content.IntentSender intentSender, android.content.Intent intent);
    }

    private class OneActionThenDestroyListener implements com.android.server.autofill.ui.SaveUi.OnSaveListener {
        private boolean mDone;
        private final com.android.server.autofill.ui.SaveUi.OnSaveListener mRealListener;

        OneActionThenDestroyListener(com.android.server.autofill.ui.SaveUi.OnSaveListener onSaveListener) {
            this.mRealListener = onSaveListener;
        }

        @Override // com.android.server.autofill.ui.SaveUi.OnSaveListener
        public void onSave() {
            if (com.android.server.autofill.Helper.sDebug) {
                android.util.Slog.d(com.android.server.autofill.ui.SaveUi.TAG, "OneTimeListener.onSave(): " + this.mDone);
            }
            if (this.mDone) {
                return;
            }
            this.mRealListener.onSave();
        }

        @Override // com.android.server.autofill.ui.SaveUi.OnSaveListener
        public void onCancel(android.content.IntentSender intentSender) {
            if (com.android.server.autofill.Helper.sDebug) {
                android.util.Slog.d(com.android.server.autofill.ui.SaveUi.TAG, "OneTimeListener.onCancel(): " + this.mDone);
            }
            if (this.mDone) {
                return;
            }
            this.mRealListener.onCancel(intentSender);
        }

        @Override // com.android.server.autofill.ui.SaveUi.OnSaveListener
        public void onDestroy() {
            if (com.android.server.autofill.Helper.sDebug) {
                android.util.Slog.d(com.android.server.autofill.ui.SaveUi.TAG, "OneTimeListener.onDestroy(): " + this.mDone);
            }
            if (this.mDone) {
                return;
            }
            this.mDone = true;
            this.mRealListener.onDestroy();
        }

        @Override // com.android.server.autofill.ui.SaveUi.OnSaveListener
        public void startIntentSender(android.content.IntentSender intentSender, android.content.Intent intent) {
            if (com.android.server.autofill.Helper.sDebug) {
                android.util.Slog.d(com.android.server.autofill.ui.SaveUi.TAG, "OneTimeListener.startIntentSender(): " + this.mDone);
            }
            if (this.mDone) {
                return;
            }
            this.mRealListener.startIntentSender(intentSender, intent);
        }
    }

    SaveUi(@android.annotation.NonNull android.content.Context context, @android.annotation.NonNull com.android.server.autofill.ui.PendingUi pendingUi, @android.annotation.NonNull java.lang.CharSequence charSequence, @android.annotation.NonNull android.graphics.drawable.Drawable drawable, @android.annotation.Nullable java.lang.String str, @android.annotation.NonNull android.content.ComponentName componentName, @android.annotation.NonNull final android.service.autofill.SaveInfo saveInfo, @android.annotation.NonNull android.service.autofill.ValueFinder valueFinder, @android.annotation.NonNull com.android.server.autofill.ui.OverlayControl overlayControl, @android.annotation.NonNull com.android.server.autofill.ui.SaveUi.OnSaveListener onSaveListener, boolean z, boolean z2, boolean z3, boolean z4) {
        if (com.android.server.autofill.Helper.sVerbose) {
            com.android.server.utils.Slogf.v(TAG, "nightMode: %b displayId: %d", java.lang.Boolean.valueOf(z), java.lang.Integer.valueOf(context.getDisplayId()));
        }
        this.mThemeId = z ? 16974829 : 16974840;
        this.mPendingUi = pendingUi;
        this.mListener = new com.android.server.autofill.ui.SaveUi.OneActionThenDestroyListener(onSaveListener);
        this.mOverlayControl = overlayControl;
        this.mServicePackageName = str;
        this.mComponentName = componentName;
        this.mCompatMode = z3;
        android.view.ContextThemeWrapper contextThemeWrapper = new android.view.ContextThemeWrapper(context, this.mThemeId) { // from class: com.android.server.autofill.ui.SaveUi.1
            @Override // android.content.ContextWrapper, android.content.Context
            public void startActivity(android.content.Intent intent) {
                if (resolveActivity(intent) == null) {
                    if (com.android.server.autofill.Helper.sDebug) {
                        android.util.Slog.d(com.android.server.autofill.ui.SaveUi.TAG, "Can not startActivity for save UI with intent=" + intent);
                        return;
                    }
                    return;
                }
                intent.putExtra("android.view.autofill.extra.RESTORE_CROSS_ACTIVITY", true);
                android.app.PendingIntent activityAsUser = android.app.PendingIntent.getActivityAsUser(this, 0, intent, android.hardware.audio.common.V2_0.AudioFormat.AMR_WB, android.app.ActivityOptions.makeBasic().setPendingIntentCreatorBackgroundActivityStartMode(1).toBundle(), android.os.UserHandle.CURRENT);
                if (com.android.server.autofill.Helper.sDebug) {
                    android.util.Slog.d(com.android.server.autofill.ui.SaveUi.TAG, "startActivity add save UI restored with intent=" + intent);
                }
                com.android.server.autofill.ui.SaveUi.this.startIntentSenderWithRestore(activityAsUser, intent);
            }

            private android.content.ComponentName resolveActivity(android.content.Intent intent) {
                android.content.pm.PackageManager packageManager = getPackageManager();
                android.content.ComponentName resolveActivity = intent.resolveActivity(packageManager);
                if (resolveActivity != null) {
                    return resolveActivity;
                }
                intent.addFlags(2048);
                android.content.pm.ActivityInfo resolveActivityInfo = intent.resolveActivityInfo(packageManager, 8388608);
                if (resolveActivityInfo != null) {
                    return new android.content.ComponentName(resolveActivityInfo.applicationInfo.packageName, resolveActivityInfo.name);
                }
                return null;
            }
        };
        android.view.View inflate = android.view.LayoutInflater.from(contextThemeWrapper).inflate(android.R.layout.autofill_save, (android.view.ViewGroup) null);
        android.widget.TextView textView = (android.widget.TextView) inflate.findViewById(android.R.id.autofill_save_title);
        android.util.ArraySet arraySet = new android.util.ArraySet(3);
        this.mType = saveInfo.getType();
        if ((this.mType & 1) != 0) {
            arraySet.add(contextThemeWrapper.getString(android.R.string.autofill_save_title_with_type));
        }
        if ((this.mType & 2) != 0) {
            arraySet.add(contextThemeWrapper.getString(android.R.string.autofill_save_no));
        }
        if (java.lang.Integer.bitCount(this.mType & 100) > 1 || (this.mType & 128) != 0) {
            arraySet.add(contextThemeWrapper.getString(android.R.string.autofill_save_title_with_3types));
        } else if ((this.mType & 64) != 0) {
            arraySet.add(contextThemeWrapper.getString(android.R.string.autofill_save_type_address));
        } else if ((this.mType & 4) == 0) {
            if ((this.mType & 32) != 0) {
                arraySet.add(contextThemeWrapper.getString(android.R.string.autofill_save_title));
            }
        } else {
            arraySet.add(contextThemeWrapper.getString(android.R.string.autofill_save_notnow));
        }
        if ((this.mType & 8) != 0) {
            arraySet.add(contextThemeWrapper.getString(android.R.string.autofill_save_type_credit_card));
        }
        if ((this.mType & 16) != 0) {
            arraySet.add(contextThemeWrapper.getString(android.R.string.autofill_save_title_with_2types));
        }
        switch (arraySet.size()) {
            case 1:
                this.mTitle = android.text.Html.fromHtml(contextThemeWrapper.getString(z2 ? android.R.string.autofill_save_type_payment_card : android.R.string.autofill_save_never, arraySet.valueAt(0), charSequence), 0);
                break;
            case 2:
                this.mTitle = android.text.Html.fromHtml(contextThemeWrapper.getString(z2 ? android.R.string.autofill_save_type_generic_card : android.R.string.autofill_picker_some_suggestions, arraySet.valueAt(0), arraySet.valueAt(1), charSequence), 0);
                break;
            case 3:
                this.mTitle = android.text.Html.fromHtml(contextThemeWrapper.getString(z2 ? android.R.string.autofill_save_type_password : android.R.string.autofill_save_accessibility_title, arraySet.valueAt(0), arraySet.valueAt(1), arraySet.valueAt(2), charSequence), 0);
                break;
            default:
                this.mTitle = android.text.Html.fromHtml(contextThemeWrapper.getString(z2 ? android.R.string.autofill_save_type_email_address : android.R.string.autofill_picker_no_suggestions, charSequence), 0);
                break;
        }
        textView.setText(this.mTitle);
        if (z4) {
            setServiceIcon(contextThemeWrapper, inflate, drawable);
        }
        if (!applyCustomDescription(contextThemeWrapper, inflate, valueFinder, saveInfo)) {
            this.mSubTitle = saveInfo.getDescription();
            if (this.mSubTitle != null) {
                writeLog(1131);
                android.view.ViewGroup viewGroup = (android.view.ViewGroup) inflate.findViewById(android.R.id.autofill_save_custom_subtitle);
                android.widget.TextView textView2 = new android.widget.TextView(contextThemeWrapper);
                textView2.setText(this.mSubTitle);
                applyMovementMethodIfNeed(textView2);
                viewGroup.addView(textView2, new android.view.ViewGroup.LayoutParams(-1, -2));
                viewGroup.setVisibility(0);
                viewGroup.setScrollBarDefaultDelayBeforeFade(500);
            }
            if (com.android.server.autofill.Helper.sDebug) {
                android.util.Slog.d(TAG, "on constructor: title=" + ((java.lang.Object) this.mTitle) + ", subTitle=" + ((java.lang.Object) this.mSubTitle));
            }
        } else {
            this.mSubTitle = null;
            if (com.android.server.autofill.Helper.sDebug) {
                android.util.Slog.d(TAG, "on constructor: applied custom description");
            }
        }
        android.widget.TextView textView3 = (android.widget.TextView) inflate.findViewById(android.R.id.autofill_save_no);
        switch (saveInfo.getNegativeActionStyle()) {
            case 1:
                textView3.setText(android.R.string.autofill_picker_accessibility_title);
                break;
            case 2:
                textView3.setText(android.R.string.autofill_continue_yes);
                break;
            default:
                textView3.setText(android.R.string.autofill_error_cannot_autofill);
                break;
        }
        textView3.setOnClickListener(new android.view.View.OnClickListener() { // from class: com.android.server.autofill.ui.SaveUi$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(android.view.View view) {
                com.android.server.autofill.ui.SaveUi.this.lambda$new$0(saveInfo, view);
            }
        });
        android.widget.TextView textView4 = (android.widget.TextView) inflate.findViewById(android.R.id.autofill_save_yes);
        if (saveInfo.getPositiveActionStyle() == 1) {
            textView4.setText(android.R.string.app_suspended_title);
        } else if (z2) {
            textView4.setText(android.R.string.autofill_save_type_username);
        }
        textView4.setOnClickListener(new android.view.View.OnClickListener() { // from class: com.android.server.autofill.ui.SaveUi$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(android.view.View view) {
                com.android.server.autofill.ui.SaveUi.this.lambda$new$1(view);
            }
        });
        this.mDialog = new android.app.Dialog(contextThemeWrapper, this.mThemeId);
        this.mDialog.setContentView(inflate);
        this.mDialog.setOnDismissListener(new android.content.DialogInterface.OnDismissListener() { // from class: com.android.server.autofill.ui.SaveUi$$ExternalSyntheticLambda2
            @Override // android.content.DialogInterface.OnDismissListener
            public final void onDismiss(android.content.DialogInterface dialogInterface) {
                com.android.server.autofill.ui.SaveUi.this.lambda$new$2(dialogInterface);
            }
        });
        android.view.Window window = this.mDialog.getWindow();
        window.setType(2038);
        window.addFlags(131074);
        window.setDimAmount(0.6f);
        window.addPrivateFlags(16);
        window.setSoftInputMode(32);
        window.setGravity(81);
        window.setCloseOnTouchOutside(true);
        android.view.WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.accessibilityTitle = contextThemeWrapper.getString(android.R.string.auto_data_switch_title);
        attributes.windowAnimations = android.R.style.AutofillHalfSheetTonalButton;
        attributes.setTrustedOverlay();
        final android.widget.ScrollView scrollView = (android.widget.ScrollView) inflate.findViewById(android.R.id.autofill_sheet_scroll_view);
        final android.view.View findViewById = inflate.findViewById(android.R.id.autofill_sheet_divider);
        scrollView.getViewTreeObserver().addOnGlobalLayoutListener(new android.view.ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.android.server.autofill.ui.SaveUi$$ExternalSyntheticLambda3
            @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
            public final void onGlobalLayout() {
                com.android.server.autofill.ui.SaveUi.this.lambda$new$3(scrollView, findViewById);
            }
        });
        scrollView.getViewTreeObserver().addOnScrollChangedListener(new android.view.ViewTreeObserver.OnScrollChangedListener() { // from class: com.android.server.autofill.ui.SaveUi$$ExternalSyntheticLambda4
            @Override // android.view.ViewTreeObserver.OnScrollChangedListener
            public final void onScrollChanged() {
                com.android.server.autofill.ui.SaveUi.this.lambda$new$4(scrollView, findViewById);
            }
        });
        show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(android.service.autofill.SaveInfo saveInfo, android.view.View view) {
        this.mListener.onCancel(saveInfo.getNegativeActionListener());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$1(android.view.View view) {
        this.mListener.onSave();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$2(android.content.DialogInterface dialogInterface) {
        this.mListener.onCancel(null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: adjustDividerVisibility, reason: merged with bridge method [inline-methods] and merged with bridge method [inline-methods] */
    public void lambda$new$4(android.widget.ScrollView scrollView, android.view.View view) {
        view.setVisibility(scrollView.canScrollVertically(1) ? 0 : 4);
    }

    private boolean applyCustomDescription(@android.annotation.NonNull android.content.Context context, @android.annotation.NonNull android.view.View view, @android.annotation.NonNull android.service.autofill.ValueFinder valueFinder, @android.annotation.NonNull android.service.autofill.SaveInfo saveInfo) {
        android.service.autofill.CustomDescription customDescription = saveInfo.getCustomDescription();
        if (customDescription != null) {
            writeLog(1129);
            android.widget.RemoteViews sanitizeRemoteView = com.android.server.autofill.Helper.sanitizeRemoteView(customDescription.getPresentation());
            if (sanitizeRemoteView == null) {
                android.util.Slog.w(TAG, "No remote view on custom description");
                return false;
            }
            java.util.ArrayList transformations = customDescription.getTransformations();
            if (com.android.server.autofill.Helper.sVerbose) {
                android.util.Slog.v(TAG, "applyCustomDescription(): transformations = " + transformations);
            }
            if (transformations == null || android.service.autofill.InternalTransformation.batchApply(valueFinder, sanitizeRemoteView, transformations)) {
                try {
                    android.view.View applyWithTheme = sanitizeRemoteView.applyWithTheme(context, null, new android.widget.RemoteViews.InteractionHandler() { // from class: com.android.server.autofill.ui.SaveUi$$ExternalSyntheticLambda5
                        public final boolean onInteraction(android.view.View view2, android.app.PendingIntent pendingIntent, android.widget.RemoteViews.RemoteResponse remoteResponse) {
                            boolean lambda$applyCustomDescription$5;
                            lambda$applyCustomDescription$5 = com.android.server.autofill.ui.SaveUi.this.lambda$applyCustomDescription$5(view2, pendingIntent, remoteResponse);
                            return lambda$applyCustomDescription$5;
                        }
                    }, this.mThemeId);
                    java.util.ArrayList updates = customDescription.getUpdates();
                    if (com.android.server.autofill.Helper.sVerbose) {
                        android.util.Slog.v(TAG, "applyCustomDescription(): view = " + applyWithTheme + " updates=" + updates);
                    }
                    if (updates != null) {
                        int size = updates.size();
                        if (com.android.server.autofill.Helper.sDebug) {
                            android.util.Slog.d(TAG, "custom description has " + size + " batch updates");
                        }
                        for (int i = 0; i < size; i++) {
                            android.util.Pair pair = (android.util.Pair) updates.get(i);
                            android.service.autofill.InternalValidator internalValidator = (android.service.autofill.InternalValidator) pair.first;
                            if (internalValidator == null || !internalValidator.isValid(valueFinder)) {
                                if (com.android.server.autofill.Helper.sDebug) {
                                    android.util.Slog.d(TAG, "Skipping batch update #" + i);
                                }
                            } else {
                                android.service.autofill.BatchUpdates batchUpdates = (android.service.autofill.BatchUpdates) pair.second;
                                android.widget.RemoteViews sanitizeRemoteView2 = com.android.server.autofill.Helper.sanitizeRemoteView(batchUpdates.getUpdates());
                                if (sanitizeRemoteView2 != null) {
                                    if (com.android.server.autofill.Helper.sDebug) {
                                        android.util.Slog.d(TAG, "Applying template updates for batch update #" + i);
                                    }
                                    sanitizeRemoteView2.reapply(context, applyWithTheme);
                                }
                                java.util.ArrayList transformations2 = batchUpdates.getTransformations();
                                if (transformations2 == null) {
                                    continue;
                                } else {
                                    if (com.android.server.autofill.Helper.sDebug) {
                                        android.util.Slog.d(TAG, "Applying child transformation for batch update #" + i + ": " + transformations2);
                                    }
                                    if (!android.service.autofill.InternalTransformation.batchApply(valueFinder, sanitizeRemoteView, transformations2)) {
                                        android.util.Slog.w(TAG, "Could not apply child transformation for batch update #" + i + ": " + transformations2);
                                        return false;
                                    }
                                    sanitizeRemoteView.reapply(context, applyWithTheme);
                                }
                            }
                        }
                    }
                    android.util.SparseArray actions = customDescription.getActions();
                    if (actions != null) {
                        int size2 = actions.size();
                        if (com.android.server.autofill.Helper.sDebug) {
                            android.util.Slog.d(TAG, "custom description has " + size2 + " actions");
                        }
                        if (!(applyWithTheme instanceof android.view.ViewGroup)) {
                            android.util.Slog.w(TAG, "cannot apply actions because custom description root is not a ViewGroup: " + applyWithTheme);
                        } else {
                            final android.view.ViewGroup viewGroup = (android.view.ViewGroup) applyWithTheme;
                            for (int i2 = 0; i2 < size2; i2++) {
                                int keyAt = actions.keyAt(i2);
                                final android.service.autofill.InternalOnClickAction internalOnClickAction = (android.service.autofill.InternalOnClickAction) actions.valueAt(i2);
                                android.view.View findViewById = viewGroup.findViewById(keyAt);
                                if (findViewById == null) {
                                    android.util.Slog.w(TAG, "Ignoring action " + internalOnClickAction + " for view " + keyAt + " because it's not on " + viewGroup);
                                } else {
                                    findViewById.setOnClickListener(new android.view.View.OnClickListener() { // from class: com.android.server.autofill.ui.SaveUi$$ExternalSyntheticLambda6
                                        @Override // android.view.View.OnClickListener
                                        public final void onClick(android.view.View view2) {
                                            com.android.server.autofill.ui.SaveUi.lambda$applyCustomDescription$6(internalOnClickAction, viewGroup, view2);
                                        }
                                    });
                                }
                            }
                        }
                    }
                    applyTextViewStyle(applyWithTheme);
                    android.view.ViewGroup viewGroup2 = (android.view.ViewGroup) view.findViewById(android.R.id.autofill_save_custom_subtitle);
                    viewGroup2.addView(applyWithTheme);
                    viewGroup2.setVisibility(0);
                    viewGroup2.setScrollBarDefaultDelayBeforeFade(500);
                    return true;
                } catch (java.lang.Exception e) {
                    android.util.Slog.e(TAG, "Error applying custom description. ", e);
                    return false;
                }
            }
            android.util.Slog.w(TAG, "could not apply main transformations on custom description");
            return false;
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$applyCustomDescription$5(android.view.View view, android.app.PendingIntent pendingIntent, android.widget.RemoteViews.RemoteResponse remoteResponse) {
        android.content.Intent intent = (android.content.Intent) remoteResponse.getLaunchOptions(view).first;
        if (!isValidLink(pendingIntent, intent)) {
            android.metrics.LogMaker newLogMaker = newLogMaker(1132, this.mType);
            newLogMaker.setType(0);
            this.mMetricsLogger.write(newLogMaker);
            return false;
        }
        startIntentSenderWithRestore(pendingIntent, intent);
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$applyCustomDescription$6(android.service.autofill.InternalOnClickAction internalOnClickAction, android.view.ViewGroup viewGroup, android.view.View view) {
        if (com.android.server.autofill.Helper.sVerbose) {
            android.util.Slog.v(TAG, "Applying " + internalOnClickAction + " after " + view + " was clicked");
        }
        internalOnClickAction.onClick(viewGroup);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startIntentSenderWithRestore(@android.annotation.NonNull android.app.PendingIntent pendingIntent, @android.annotation.NonNull android.content.Intent intent) {
        if (com.android.server.autofill.Helper.sVerbose) {
            android.util.Slog.v(TAG, "Intercepting custom description intent");
        }
        android.os.IBinder token = this.mPendingUi.getToken();
        intent.putExtra("android.view.autofill.extra.RESTORE_SESSION_TOKEN", token);
        this.mListener.startIntentSender(pendingIntent.getIntentSender(), intent);
        this.mPendingUi.setState(2);
        if (com.android.server.autofill.Helper.sDebug) {
            android.util.Slog.d(TAG, "hiding UI until restored with token " + token);
        }
        hide();
        android.metrics.LogMaker newLogMaker = newLogMaker(1132, this.mType);
        newLogMaker.setType(1);
        this.mMetricsLogger.write(newLogMaker);
    }

    private void applyTextViewStyle(@android.annotation.NonNull android.view.View view) {
        final java.util.ArrayList arrayList = new java.util.ArrayList();
        view.findViewByPredicate(new java.util.function.Predicate() { // from class: com.android.server.autofill.ui.SaveUi$$ExternalSyntheticLambda7
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                boolean lambda$applyTextViewStyle$7;
                lambda$applyTextViewStyle$7 = com.android.server.autofill.ui.SaveUi.lambda$applyTextViewStyle$7(arrayList, (android.view.View) obj);
                return lambda$applyTextViewStyle$7;
            }
        });
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            applyMovementMethodIfNeed((android.widget.TextView) arrayList.get(i));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$applyTextViewStyle$7(java.util.List list, android.view.View view) {
        if (view instanceof android.widget.TextView) {
            list.add((android.widget.TextView) view);
            return false;
        }
        return false;
    }

    private void applyMovementMethodIfNeed(@android.annotation.NonNull android.widget.TextView textView) {
        java.lang.CharSequence text = textView.getText();
        if (android.text.TextUtils.isEmpty(text)) {
            return;
        }
        android.text.SpannableStringBuilder spannableStringBuilder = new android.text.SpannableStringBuilder(text);
        if (com.android.internal.util.ArrayUtils.isEmpty((android.text.style.ClickableSpan[]) spannableStringBuilder.getSpans(0, spannableStringBuilder.length(), android.text.style.ClickableSpan.class))) {
            return;
        }
        textView.setMovementMethod(android.text.method.LinkMovementMethod.getInstance());
    }

    private void setServiceIcon(android.content.Context context, android.view.View view, android.graphics.drawable.Drawable drawable) {
        android.widget.ImageView imageView = (android.widget.ImageView) view.findViewById(android.R.id.autofill_save_icon);
        context.getResources();
        imageView.setImageDrawable(drawable);
    }

    private static boolean isValidLink(android.app.PendingIntent pendingIntent, android.content.Intent intent) {
        if (pendingIntent == null) {
            android.util.Slog.w(TAG, "isValidLink(): custom description without pending intent");
            return false;
        }
        if (!pendingIntent.isActivity()) {
            android.util.Slog.w(TAG, "isValidLink(): pending intent not for activity");
            return false;
        }
        if (intent == null) {
            android.util.Slog.w(TAG, "isValidLink(): no intent");
            return false;
        }
        return true;
    }

    private android.metrics.LogMaker newLogMaker(int i, int i2) {
        return newLogMaker(i).addTaggedData(1130, java.lang.Integer.valueOf(i2));
    }

    private android.metrics.LogMaker newLogMaker(int i) {
        return com.android.server.autofill.Helper.newLogMaker(i, this.mComponentName, this.mServicePackageName, this.mPendingUi.sessionId, this.mCompatMode);
    }

    private void writeLog(int i) {
        this.mMetricsLogger.write(newLogMaker(i, this.mType));
    }

    void onPendingUi(int i, @android.annotation.NonNull android.os.IBinder iBinder) {
        if (!this.mPendingUi.matches(iBinder)) {
            android.util.Slog.w(TAG, "restore(" + i + "): got token " + iBinder + " instead of " + this.mPendingUi.getToken());
            return;
        }
        android.metrics.LogMaker newLogMaker = newLogMaker(1134);
        try {
            switch (i) {
                case 1:
                    newLogMaker.setType(5);
                    if (com.android.server.autofill.Helper.sDebug) {
                        android.util.Slog.d(TAG, "Cancelling pending save dialog for " + iBinder);
                    }
                    hide();
                    break;
                case 2:
                    if (com.android.server.autofill.Helper.sDebug) {
                        android.util.Slog.d(TAG, "Restoring save dialog for " + iBinder);
                    }
                    newLogMaker.setType(1);
                    show();
                    break;
                default:
                    newLogMaker.setType(11);
                    android.util.Slog.w(TAG, "restore(): invalid operation " + i);
                    break;
            }
            this.mMetricsLogger.write(newLogMaker);
            this.mPendingUi.setState(4);
        } catch (java.lang.Throwable th) {
            this.mMetricsLogger.write(newLogMaker);
            throw th;
        }
    }

    private void show() {
        android.util.Slog.i(TAG, "Showing save dialog: " + ((java.lang.Object) this.mTitle));
        this.mDialog.show();
        this.mOverlayControl.hideOverlays();
    }

    com.android.server.autofill.ui.PendingUi hide() {
        if (com.android.server.autofill.Helper.sVerbose) {
            android.util.Slog.v(TAG, "Hiding save dialog.");
        }
        try {
            this.mDialog.hide();
            this.mOverlayControl.showOverlays();
            return this.mPendingUi;
        } catch (java.lang.Throwable th) {
            this.mOverlayControl.showOverlays();
            throw th;
        }
    }

    boolean isShowing() {
        return this.mDialog.isShowing();
    }

    void destroy() {
        try {
            if (com.android.server.autofill.Helper.sDebug) {
                android.util.Slog.d(TAG, "destroy()");
            }
            throwIfDestroyed();
            this.mListener.onDestroy();
            this.mHandler.removeCallbacksAndMessages(this.mListener);
            this.mDialog.dismiss();
            this.mDestroyed = true;
            this.mOverlayControl.showOverlays();
        } catch (java.lang.Throwable th) {
            this.mOverlayControl.showOverlays();
            throw th;
        }
    }

    private void throwIfDestroyed() {
        if (this.mDestroyed) {
            throw new java.lang.IllegalStateException("cannot interact with a destroyed instance");
        }
    }

    public java.lang.String toString() {
        return this.mTitle == null ? "NO TITLE" : this.mTitle.toString();
    }

    void dump(java.io.PrintWriter printWriter, java.lang.String str) {
        printWriter.print(str);
        printWriter.print("title: ");
        printWriter.println(this.mTitle);
        printWriter.print(str);
        printWriter.print("subtitle: ");
        printWriter.println(this.mSubTitle);
        printWriter.print(str);
        printWriter.print("pendingUi: ");
        printWriter.println(this.mPendingUi);
        printWriter.print(str);
        printWriter.print("service: ");
        printWriter.println(this.mServicePackageName);
        printWriter.print(str);
        printWriter.print("app: ");
        printWriter.println(this.mComponentName.toShortString());
        printWriter.print(str);
        printWriter.print("compat mode: ");
        printWriter.println(this.mCompatMode);
        printWriter.print(str);
        printWriter.print("theme id: ");
        printWriter.print(this.mThemeId);
        switch (this.mThemeId) {
            case 16974829:
                printWriter.println(" (dark)");
                break;
            case 16974840:
                printWriter.println(" (light)");
                break;
            default:
                printWriter.println("(UNKNOWN_MODE)");
                break;
        }
        android.view.View decorView = this.mDialog.getWindow().getDecorView();
        int[] locationOnScreen = decorView.getLocationOnScreen();
        printWriter.print(str);
        printWriter.print("coordinates: ");
        printWriter.print('(');
        printWriter.print(locationOnScreen[0]);
        printWriter.print(',');
        printWriter.print(locationOnScreen[1]);
        printWriter.print(')');
        printWriter.print('(');
        printWriter.print(locationOnScreen[0] + decorView.getWidth());
        printWriter.print(',');
        printWriter.print(locationOnScreen[1] + decorView.getHeight());
        printWriter.println(')');
        printWriter.print(str);
        printWriter.print("destroyed: ");
        printWriter.println(this.mDestroyed);
    }
}
