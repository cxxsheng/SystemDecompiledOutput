package com.android.internal.app;

/* loaded from: classes4.dex */
public class MediaRouteControllerDialogFragment extends android.app.DialogFragment {
    public MediaRouteControllerDialogFragment() {
        setCancelable(true);
    }

    public com.android.internal.app.MediaRouteControllerDialog onCreateControllerDialog(android.content.Context context, android.os.Bundle bundle) {
        return new com.android.internal.app.MediaRouteControllerDialog(context, getTheme());
    }

    @Override // android.app.DialogFragment
    public android.app.Dialog onCreateDialog(android.os.Bundle bundle) {
        return onCreateControllerDialog(getContext(), bundle);
    }
}
