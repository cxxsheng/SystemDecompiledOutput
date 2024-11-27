package com.android.internal.app;

/* loaded from: classes4.dex */
public class ChooserTargetActionsDialogFragment extends android.app.DialogFragment implements android.content.DialogInterface.OnClickListener {
    public static final java.lang.String INTENT_FILTER_KEY = "intent_filter";
    public static final java.lang.String IS_SHORTCUT_PINNED_KEY = "is_shortcut_pinned";
    public static final java.lang.String SHORTCUT_ID_KEY = "shortcut_id";
    public static final java.lang.String SHORTCUT_TITLE_KEY = "shortcut_title";
    public static final java.lang.String TARGET_INFOS_KEY = "target_infos";
    public static final java.lang.String USER_HANDLE_KEY = "user_handle";
    protected android.content.IntentFilter mIntentFilter;
    protected boolean mIsShortcutPinned;
    protected java.lang.String mShortcutId;
    protected java.lang.String mShortcutTitle;
    protected java.util.ArrayList<com.android.internal.app.chooser.DisplayResolveInfo> mTargetInfos = new java.util.ArrayList<>();
    protected android.os.UserHandle mUserHandle;

    @Override // android.app.DialogFragment, android.app.Fragment
    public void onCreate(android.os.Bundle bundle) {
        super.onCreate(bundle);
        if (bundle != null) {
            setStateFromBundle(bundle);
        } else {
            setStateFromBundle(getArguments());
        }
    }

    void setStateFromBundle(android.os.Bundle bundle) {
        this.mTargetInfos = (java.util.ArrayList) bundle.get(TARGET_INFOS_KEY);
        this.mUserHandle = (android.os.UserHandle) bundle.get("user_handle");
        this.mShortcutId = bundle.getString(SHORTCUT_ID_KEY);
        this.mShortcutTitle = bundle.getString(SHORTCUT_TITLE_KEY);
        this.mIsShortcutPinned = bundle.getBoolean(IS_SHORTCUT_PINNED_KEY);
        this.mIntentFilter = (android.content.IntentFilter) bundle.get("intent_filter");
    }

    @Override // android.app.DialogFragment, android.app.Fragment
    public void onSaveInstanceState(android.os.Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putParcelable("user_handle", this.mUserHandle);
        bundle.putParcelableArrayList(TARGET_INFOS_KEY, this.mTargetInfos);
        bundle.putString(SHORTCUT_ID_KEY, this.mShortcutId);
        bundle.putBoolean(IS_SHORTCUT_PINNED_KEY, this.mIsShortcutPinned);
        bundle.putString(SHORTCUT_TITLE_KEY, this.mShortcutTitle);
        bundle.putParcelable("intent_filter", this.mIntentFilter);
    }

    @Override // android.app.Fragment
    public android.view.View onCreateView(android.view.LayoutInflater layoutInflater, android.view.ViewGroup viewGroup, android.os.Bundle bundle) {
        if (bundle != null) {
            setStateFromBundle(bundle);
        } else {
            setStateFromBundle(getArguments());
        }
        java.util.Optional.of(getDialog()).map(new java.util.function.Function() { // from class: com.android.internal.app.ChooserTargetActionsDialogFragment$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final java.lang.Object apply(java.lang.Object obj) {
                return ((android.app.Dialog) obj).getWindow();
            }
        }).ifPresent(new java.util.function.Consumer() { // from class: com.android.internal.app.ChooserTargetActionsDialogFragment$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                ((android.view.Window) obj).setBackgroundDrawable(new android.graphics.drawable.ColorDrawable(0));
            }
        });
        java.util.List list = (java.util.List) this.mTargetInfos.stream().map(new java.util.function.Function() { // from class: com.android.internal.app.ChooserTargetActionsDialogFragment$$ExternalSyntheticLambda2
            @Override // java.util.function.Function
            public final java.lang.Object apply(java.lang.Object obj) {
                android.util.Pair lambda$onCreateView$1;
                lambda$onCreateView$1 = com.android.internal.app.ChooserTargetActionsDialogFragment.this.lambda$onCreateView$1((com.android.internal.app.chooser.DisplayResolveInfo) obj);
                return lambda$onCreateView$1;
            }
        }).collect(java.util.stream.Collectors.toList());
        android.view.View inflate = layoutInflater.inflate(com.android.internal.R.layout.chooser_dialog, viewGroup, false);
        android.widget.TextView textView = (android.widget.TextView) inflate.findViewById(16908310);
        android.widget.ImageView imageView = (android.widget.ImageView) inflate.findViewById(16908294);
        com.android.internal.widget.RecyclerView recyclerView = (com.android.internal.widget.RecyclerView) inflate.findViewById(com.android.internal.R.id.listContainer);
        com.android.internal.app.ResolverListAdapter.ResolveInfoPresentationGetter providingAppPresentationGetter = getProvidingAppPresentationGetter();
        textView.lambda$setTextAsync$0(isShortcutTarget() ? this.mShortcutTitle : providingAppPresentationGetter.getLabel());
        imageView.lambda$setImageURIAsync$2(providingAppPresentationGetter.getIcon(this.mUserHandle));
        recyclerView.setAdapter(new com.android.internal.app.ChooserTargetActionsDialogFragment.VHAdapter(list));
        return inflate;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ android.util.Pair lambda$onCreateView$1(com.android.internal.app.chooser.DisplayResolveInfo displayResolveInfo) {
        return new android.util.Pair(getItemIcon(displayResolveInfo), getItemLabel(displayResolveInfo));
    }

    class VHAdapter extends com.android.internal.widget.RecyclerView.Adapter<com.android.internal.app.ChooserTargetActionsDialogFragment.VH> {
        java.util.List<android.util.Pair<android.graphics.drawable.Drawable, java.lang.CharSequence>> mItems;

        VHAdapter(java.util.List<android.util.Pair<android.graphics.drawable.Drawable, java.lang.CharSequence>> list) {
            this.mItems = list;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // com.android.internal.widget.RecyclerView.Adapter
        public com.android.internal.app.ChooserTargetActionsDialogFragment.VH onCreateViewHolder(android.view.ViewGroup viewGroup, int i) {
            return com.android.internal.app.ChooserTargetActionsDialogFragment.this.new VH(android.view.LayoutInflater.from(viewGroup.getContext()).inflate(com.android.internal.R.layout.chooser_dialog_item, viewGroup, false));
        }

        @Override // com.android.internal.widget.RecyclerView.Adapter
        public void onBindViewHolder(com.android.internal.app.ChooserTargetActionsDialogFragment.VH vh, int i) {
            vh.bind(this.mItems.get(i), i);
        }

        @Override // com.android.internal.widget.RecyclerView.Adapter
        public int getItemCount() {
            return this.mItems.size();
        }
    }

    class VH extends com.android.internal.widget.RecyclerView.ViewHolder {
        android.widget.ImageView mIcon;
        android.widget.TextView mLabel;

        VH(android.view.View view) {
            super(view);
            this.mLabel = (android.widget.TextView) view.findViewById(com.android.internal.R.id.text);
            this.mIcon = (android.widget.ImageView) view.findViewById(16908294);
        }

        public void bind(android.util.Pair<android.graphics.drawable.Drawable, java.lang.CharSequence> pair, final int i) {
            this.mLabel.lambda$setTextAsync$0(pair.second);
            if (pair.first == null) {
                this.mIcon.setVisibility(8);
            } else {
                this.mIcon.setVisibility(0);
                this.mIcon.lambda$setImageURIAsync$2(pair.first);
            }
            this.itemView.setOnClickListener(new android.view.View.OnClickListener() { // from class: com.android.internal.app.ChooserTargetActionsDialogFragment$VH$$ExternalSyntheticLambda0
                @Override // android.view.View.OnClickListener
                public final void onClick(android.view.View view) {
                    com.android.internal.app.ChooserTargetActionsDialogFragment.VH.this.lambda$bind$0(i, view);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$bind$0(int i, android.view.View view) {
            com.android.internal.app.ChooserTargetActionsDialogFragment.this.onClick(com.android.internal.app.ChooserTargetActionsDialogFragment.this.getDialog(), i);
        }
    }

    public void onClick(android.content.DialogInterface dialogInterface, int i) {
        if (isShortcutTarget()) {
            toggleShortcutPinned(this.mTargetInfos.get(i).getResolvedComponentName());
        } else {
            pinComponent(this.mTargetInfos.get(i).getResolvedComponentName());
        }
        ((com.android.internal.app.ChooserActivity) getActivity()).handlePackagesChanged();
        dismiss();
    }

    private void toggleShortcutPinned(android.content.ComponentName componentName) {
        if (this.mIntentFilter == null) {
            return;
        }
        java.util.List<java.lang.String> pinnedShortcutsFromPackageAsUser = getPinnedShortcutsFromPackageAsUser(getContext(), this.mUserHandle, this.mIntentFilter, componentName.getPackageName());
        if (this.mIsShortcutPinned) {
            pinnedShortcutsFromPackageAsUser.remove(this.mShortcutId);
        } else {
            pinnedShortcutsFromPackageAsUser.add(this.mShortcutId);
        }
        ((android.content.pm.LauncherApps) getContext().getSystemService(android.content.pm.LauncherApps.class)).pinShortcuts(componentName.getPackageName(), pinnedShortcutsFromPackageAsUser, this.mUserHandle);
    }

    private static java.util.List<java.lang.String> getPinnedShortcutsFromPackageAsUser(android.content.Context context, android.os.UserHandle userHandle, android.content.IntentFilter intentFilter, final java.lang.String str) {
        return (java.util.List) ((android.content.pm.ShortcutManager) context.createContextAsUser(userHandle, 0).getSystemService(android.content.pm.ShortcutManager.class)).getShareTargets(intentFilter).stream().map(new java.util.function.Function() { // from class: com.android.internal.app.ChooserTargetActionsDialogFragment$$ExternalSyntheticLambda3
            @Override // java.util.function.Function
            public final java.lang.Object apply(java.lang.Object obj) {
                return ((android.content.pm.ShortcutManager.ShareShortcutInfo) obj).getShortcutInfo();
            }
        }).filter(new java.util.function.Predicate() { // from class: com.android.internal.app.ChooserTargetActionsDialogFragment$$ExternalSyntheticLambda4
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                return com.android.internal.app.ChooserTargetActionsDialogFragment.lambda$getPinnedShortcutsFromPackageAsUser$2(str, (android.content.pm.ShortcutInfo) obj);
            }
        }).map(new java.util.function.Function() { // from class: com.android.internal.app.ChooserTargetActionsDialogFragment$$ExternalSyntheticLambda5
            @Override // java.util.function.Function
            public final java.lang.Object apply(java.lang.Object obj) {
                return ((android.content.pm.ShortcutInfo) obj).getId();
            }
        }).collect(java.util.stream.Collectors.toList());
    }

    static /* synthetic */ boolean lambda$getPinnedShortcutsFromPackageAsUser$2(java.lang.String str, android.content.pm.ShortcutInfo shortcutInfo) {
        return shortcutInfo.isPinned() && shortcutInfo.getPackage().equals(str);
    }

    private void pinComponent(android.content.ComponentName componentName) {
        android.content.SharedPreferences pinnedSharedPrefs = com.android.internal.app.ChooserActivity.getPinnedSharedPrefs(getContext());
        java.lang.String flattenToString = componentName.flattenToString();
        if (pinnedSharedPrefs.getBoolean(componentName.flattenToString(), false)) {
            pinnedSharedPrefs.edit().remove(flattenToString).apply();
        } else {
            pinnedSharedPrefs.edit().putBoolean(flattenToString, true).apply();
        }
    }

    private android.graphics.drawable.Drawable getPinIcon(boolean z) {
        if (z) {
            return getContext().getDrawable(com.android.internal.R.drawable.ic_close);
        }
        return getContext().getDrawable(com.android.internal.R.drawable.ic_chooser_pin_dialog);
    }

    private java.lang.CharSequence getPinLabel(boolean z, java.lang.CharSequence charSequence) {
        if (z) {
            return getResources().getString(com.android.internal.R.string.unpin_specific_target, charSequence);
        }
        return getResources().getString(com.android.internal.R.string.pin_specific_target, charSequence);
    }

    protected java.lang.CharSequence getItemLabel(com.android.internal.app.chooser.DisplayResolveInfo displayResolveInfo) {
        return getPinLabel(isPinned(displayResolveInfo), isShortcutTarget() ? this.mShortcutTitle : displayResolveInfo.getResolveInfo().loadLabel(getContext().getPackageManager()));
    }

    protected android.graphics.drawable.Drawable getItemIcon(com.android.internal.app.chooser.DisplayResolveInfo displayResolveInfo) {
        return getPinIcon(isPinned(displayResolveInfo));
    }

    private com.android.internal.app.ResolverListAdapter.ResolveInfoPresentationGetter getProvidingAppPresentationGetter() {
        return new com.android.internal.app.ResolverListAdapter.ResolveInfoPresentationGetter(getContext(), ((android.app.ActivityManager) getContext().getSystemService("activity")).getLauncherLargeIconDensity(), this.mTargetInfos.get(0).getResolveInfo());
    }

    private boolean isPinned(com.android.internal.app.chooser.DisplayResolveInfo displayResolveInfo) {
        return isShortcutTarget() ? this.mIsShortcutPinned : displayResolveInfo.isPinned();
    }

    private boolean isShortcutTarget() {
        return this.mShortcutId != null;
    }
}
