package com.android.internal.app;

/* loaded from: classes4.dex */
public class MediaRouteChooserDialogFragment extends android.app.DialogFragment {
    private final java.lang.String ARGUMENT_ROUTE_TYPES = "routeTypes";
    private android.view.View.OnClickListener mExtendedSettingsClickListener;

    public MediaRouteChooserDialogFragment() {
        int i;
        if (com.android.internal.app.MediaRouteChooserDialog.isLightTheme(getContext())) {
            i = 16974130;
        } else {
            i = 16974126;
        }
        setCancelable(true);
        setStyle(0, i);
    }

    public int getRouteTypes() {
        android.os.Bundle arguments = getArguments();
        if (arguments != null) {
            return arguments.getInt("routeTypes");
        }
        return 0;
    }

    public void setRouteTypes(int i) {
        if (i != getRouteTypes()) {
            android.os.Bundle arguments = getArguments();
            if (arguments == null) {
                arguments = new android.os.Bundle();
            }
            arguments.putInt("routeTypes", i);
            setArguments(arguments);
            com.android.internal.app.MediaRouteChooserDialog mediaRouteChooserDialog = (com.android.internal.app.MediaRouteChooserDialog) getDialog();
            if (mediaRouteChooserDialog != null) {
                mediaRouteChooserDialog.setRouteTypes(i);
            }
        }
    }

    public void setExtendedSettingsClickListener(android.view.View.OnClickListener onClickListener) {
        if (onClickListener != this.mExtendedSettingsClickListener) {
            this.mExtendedSettingsClickListener = onClickListener;
            com.android.internal.app.MediaRouteChooserDialog mediaRouteChooserDialog = (com.android.internal.app.MediaRouteChooserDialog) getDialog();
            if (mediaRouteChooserDialog != null) {
                mediaRouteChooserDialog.setExtendedSettingsClickListener(onClickListener);
            }
        }
    }

    public com.android.internal.app.MediaRouteChooserDialog onCreateChooserDialog(android.content.Context context, android.os.Bundle bundle) {
        return new com.android.internal.app.MediaRouteChooserDialog(context, getTheme());
    }

    @Override // android.app.DialogFragment
    public android.app.Dialog onCreateDialog(android.os.Bundle bundle) {
        com.android.internal.app.MediaRouteChooserDialog onCreateChooserDialog = onCreateChooserDialog(getActivity(), bundle);
        onCreateChooserDialog.setRouteTypes(getRouteTypes());
        onCreateChooserDialog.setExtendedSettingsClickListener(this.mExtendedSettingsClickListener);
        return onCreateChooserDialog;
    }
}
