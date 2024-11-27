package com.android.internal.app;

/* loaded from: classes4.dex */
class NavItemSelectedListener implements android.widget.AdapterView.OnItemSelectedListener {
    private final android.app.ActionBar.OnNavigationListener mListener;

    public NavItemSelectedListener(android.app.ActionBar.OnNavigationListener onNavigationListener) {
        this.mListener = onNavigationListener;
    }

    @Override // android.widget.AdapterView.OnItemSelectedListener
    public void onItemSelected(android.widget.AdapterView<?> adapterView, android.view.View view, int i, long j) {
        if (this.mListener != null) {
            this.mListener.onNavigationItemSelected(i, j);
        }
    }

    @Override // android.widget.AdapterView.OnItemSelectedListener
    public void onNothingSelected(android.widget.AdapterView<?> adapterView) {
    }
}
