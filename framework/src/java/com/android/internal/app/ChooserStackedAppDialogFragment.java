package com.android.internal.app;

/* loaded from: classes4.dex */
public class ChooserStackedAppDialogFragment extends com.android.internal.app.ChooserTargetActionsDialogFragment implements android.content.DialogInterface.OnClickListener {
    static final java.lang.String MULTI_DRI_KEY = "multi_dri_key";
    static final java.lang.String WHICH_KEY = "which_key";
    private com.android.internal.app.chooser.MultiDisplayResolveInfo mMultiDisplayResolveInfo;
    private int mParentWhich;

    @Override // com.android.internal.app.ChooserTargetActionsDialogFragment
    void setStateFromBundle(android.os.Bundle bundle) {
        this.mMultiDisplayResolveInfo = (com.android.internal.app.chooser.MultiDisplayResolveInfo) bundle.get(MULTI_DRI_KEY);
        this.mTargetInfos = this.mMultiDisplayResolveInfo.getTargets();
        this.mUserHandle = (android.os.UserHandle) bundle.get("user_handle");
        this.mParentWhich = bundle.getInt(WHICH_KEY);
    }

    @Override // com.android.internal.app.ChooserTargetActionsDialogFragment, android.app.DialogFragment, android.app.Fragment
    public void onSaveInstanceState(android.os.Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putInt(WHICH_KEY, this.mParentWhich);
        bundle.putParcelable(MULTI_DRI_KEY, this.mMultiDisplayResolveInfo);
    }

    @Override // com.android.internal.app.ChooserTargetActionsDialogFragment
    protected java.lang.CharSequence getItemLabel(com.android.internal.app.chooser.DisplayResolveInfo displayResolveInfo) {
        return displayResolveInfo.getResolveInfo().loadLabel(getContext().getPackageManager());
    }

    @Override // com.android.internal.app.ChooserTargetActionsDialogFragment
    protected android.graphics.drawable.Drawable getItemIcon(com.android.internal.app.chooser.DisplayResolveInfo displayResolveInfo) {
        return null;
    }

    @Override // com.android.internal.app.ChooserTargetActionsDialogFragment, android.content.DialogInterface.OnClickListener
    public void onClick(android.content.DialogInterface dialogInterface, int i) {
        this.mMultiDisplayResolveInfo.setSelected(i);
        ((com.android.internal.app.ChooserActivity) getActivity()).startSelected(this.mParentWhich, false, true);
        dismiss();
    }
}
