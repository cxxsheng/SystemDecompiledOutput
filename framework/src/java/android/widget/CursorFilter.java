package android.widget;

/* loaded from: classes4.dex */
class CursorFilter extends android.widget.Filter {
    android.widget.CursorFilter.CursorFilterClient mClient;

    interface CursorFilterClient {
        void changeCursor(android.database.Cursor cursor);

        java.lang.CharSequence convertToString(android.database.Cursor cursor);

        android.database.Cursor getCursor();

        android.database.Cursor runQueryOnBackgroundThread(java.lang.CharSequence charSequence);
    }

    CursorFilter(android.widget.CursorFilter.CursorFilterClient cursorFilterClient) {
        this.mClient = cursorFilterClient;
    }

    @Override // android.widget.Filter
    public java.lang.CharSequence convertResultToString(java.lang.Object obj) {
        return this.mClient.convertToString((android.database.Cursor) obj);
    }

    @Override // android.widget.Filter
    protected android.widget.Filter.FilterResults performFiltering(java.lang.CharSequence charSequence) {
        android.database.Cursor runQueryOnBackgroundThread = this.mClient.runQueryOnBackgroundThread(charSequence);
        android.widget.Filter.FilterResults filterResults = new android.widget.Filter.FilterResults();
        if (runQueryOnBackgroundThread != null) {
            filterResults.count = runQueryOnBackgroundThread.getCount();
            filterResults.values = runQueryOnBackgroundThread;
        } else {
            filterResults.count = 0;
            filterResults.values = null;
        }
        return filterResults;
    }

    @Override // android.widget.Filter
    protected void publishResults(java.lang.CharSequence charSequence, android.widget.Filter.FilterResults filterResults) {
        android.database.Cursor cursor = this.mClient.getCursor();
        if (filterResults.values != null && filterResults.values != cursor) {
            this.mClient.changeCursor((android.database.Cursor) filterResults.values);
        }
    }
}
