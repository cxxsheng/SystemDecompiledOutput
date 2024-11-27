package android.preference;

@java.lang.Deprecated
/* loaded from: classes3.dex */
public final class PreferenceScreen extends android.preference.PreferenceGroup implements android.widget.AdapterView.OnItemClickListener, android.content.DialogInterface.OnDismissListener {
    private android.app.Dialog mDialog;
    private android.graphics.drawable.Drawable mDividerDrawable;
    private boolean mDividerSpecified;
    private int mLayoutResId;
    private android.widget.ListView mListView;
    private android.widget.ListAdapter mRootAdapter;

    public PreferenceScreen(android.content.Context context, android.util.AttributeSet attributeSet) {
        super(context, attributeSet, 16842891);
        this.mLayoutResId = com.android.internal.R.layout.preference_list_fragment;
        android.content.res.TypedArray obtainStyledAttributes = context.obtainStyledAttributes(null, com.android.internal.R.styleable.PreferenceScreen, 16842891, 0);
        this.mLayoutResId = obtainStyledAttributes.getResourceId(1, this.mLayoutResId);
        if (obtainStyledAttributes.hasValueOrEmpty(0)) {
            this.mDividerDrawable = obtainStyledAttributes.getDrawable(0);
            this.mDividerSpecified = true;
        }
        obtainStyledAttributes.recycle();
    }

    public android.widget.ListAdapter getRootAdapter() {
        if (this.mRootAdapter == null) {
            this.mRootAdapter = onCreateRootAdapter();
        }
        return this.mRootAdapter;
    }

    protected android.widget.ListAdapter onCreateRootAdapter() {
        return new android.preference.PreferenceGroupAdapter(this);
    }

    public void bind(android.widget.ListView listView) {
        listView.setOnItemClickListener(this);
        listView.setAdapter(getRootAdapter());
        onAttachedToActivity();
    }

    @Override // android.preference.Preference
    protected void onClick() {
        if (getIntent() != null || getFragment() != null || getPreferenceCount() == 0) {
            return;
        }
        showDialog(null);
    }

    private void showDialog(android.os.Bundle bundle) {
        android.content.Context context = getContext();
        if (this.mListView != null) {
            this.mListView.setAdapter((android.widget.ListAdapter) null);
        }
        android.view.View inflate = ((android.view.LayoutInflater) context.getSystemService(android.content.Context.LAYOUT_INFLATER_SERVICE)).inflate(this.mLayoutResId, (android.view.ViewGroup) null);
        android.view.View findViewById = inflate.findViewById(16908310);
        this.mListView = (android.widget.ListView) inflate.findViewById(16908298);
        if (this.mDividerSpecified) {
            this.mListView.setDivider(this.mDividerDrawable);
        }
        bind(this.mListView);
        java.lang.CharSequence title = getTitle();
        android.app.Dialog dialog = new android.app.Dialog(context, context.getThemeResId());
        this.mDialog = dialog;
        if (android.text.TextUtils.isEmpty(title)) {
            if (findViewById != null) {
                findViewById.setVisibility(8);
            }
            dialog.getWindow().requestFeature(1);
        } else if (findViewById instanceof android.widget.TextView) {
            ((android.widget.TextView) findViewById).lambda$setTextAsync$0(title);
            findViewById.setVisibility(0);
        } else {
            dialog.setTitle(title);
        }
        dialog.setContentView(inflate);
        dialog.setOnDismissListener(this);
        if (bundle != null) {
            dialog.onRestoreInstanceState(bundle);
        }
        getPreferenceManager().addPreferencesScreen(dialog);
        dialog.show();
    }

    @Override // android.content.DialogInterface.OnDismissListener
    public void onDismiss(android.content.DialogInterface dialogInterface) {
        this.mDialog = null;
        getPreferenceManager().removePreferencesScreen(dialogInterface);
    }

    public android.app.Dialog getDialog() {
        return this.mDialog;
    }

    @Override // android.widget.AdapterView.OnItemClickListener
    public void onItemClick(android.widget.AdapterView adapterView, android.view.View view, int i, long j) {
        if (adapterView instanceof android.widget.ListView) {
            i -= ((android.widget.ListView) adapterView).getHeaderViewsCount();
        }
        java.lang.Object item = getRootAdapter().getItem(i);
        if (item instanceof android.preference.Preference) {
            ((android.preference.Preference) item).performClick(this);
        }
    }

    @Override // android.preference.PreferenceGroup
    protected boolean isOnSameScreenAsChildren() {
        return false;
    }

    @Override // android.preference.Preference
    protected android.os.Parcelable onSaveInstanceState() {
        android.os.Parcelable onSaveInstanceState = super.onSaveInstanceState();
        android.app.Dialog dialog = this.mDialog;
        if (dialog == null || !dialog.isShowing()) {
            return onSaveInstanceState;
        }
        android.preference.PreferenceScreen.SavedState savedState = new android.preference.PreferenceScreen.SavedState(onSaveInstanceState);
        savedState.isDialogShowing = true;
        savedState.dialogBundle = dialog.onSaveInstanceState();
        return savedState;
    }

    @Override // android.preference.Preference
    protected void onRestoreInstanceState(android.os.Parcelable parcelable) {
        if (parcelable == null || !parcelable.getClass().equals(android.preference.PreferenceScreen.SavedState.class)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        android.preference.PreferenceScreen.SavedState savedState = (android.preference.PreferenceScreen.SavedState) parcelable;
        super.onRestoreInstanceState(savedState.getSuperState());
        if (savedState.isDialogShowing) {
            showDialog(savedState.dialogBundle);
        }
    }

    private static class SavedState extends android.preference.Preference.BaseSavedState {
        public static final android.os.Parcelable.Creator<android.preference.PreferenceScreen.SavedState> CREATOR = new android.os.Parcelable.Creator<android.preference.PreferenceScreen.SavedState>() { // from class: android.preference.PreferenceScreen.SavedState.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.preference.PreferenceScreen.SavedState createFromParcel(android.os.Parcel parcel) {
                return new android.preference.PreferenceScreen.SavedState(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.preference.PreferenceScreen.SavedState[] newArray(int i) {
                return new android.preference.PreferenceScreen.SavedState[i];
            }
        };
        android.os.Bundle dialogBundle;
        boolean isDialogShowing;

        public SavedState(android.os.Parcel parcel) {
            super(parcel);
            this.isDialogShowing = parcel.readInt() == 1;
            this.dialogBundle = parcel.readBundle();
        }

        @Override // android.view.AbsSavedState, android.os.Parcelable
        public void writeToParcel(android.os.Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeInt(this.isDialogShowing ? 1 : 0);
            parcel.writeBundle(this.dialogBundle);
        }

        public SavedState(android.os.Parcelable parcelable) {
            super(parcelable);
        }
    }
}
