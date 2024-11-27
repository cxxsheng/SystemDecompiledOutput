package android.appwidget;

/* loaded from: classes.dex */
public class PendingHostUpdate implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.appwidget.PendingHostUpdate> CREATOR = new android.os.Parcelable.Creator<android.appwidget.PendingHostUpdate>() { // from class: android.appwidget.PendingHostUpdate.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.appwidget.PendingHostUpdate createFromParcel(android.os.Parcel parcel) {
            return new android.appwidget.PendingHostUpdate(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.appwidget.PendingHostUpdate[] newArray(int i) {
            return new android.appwidget.PendingHostUpdate[i];
        }
    };
    static final int TYPE_APP_WIDGET_REMOVED = 3;
    static final int TYPE_PROVIDER_CHANGED = 1;
    static final int TYPE_VIEWS_UPDATE = 0;
    static final int TYPE_VIEW_DATA_CHANGED = 2;
    final int appWidgetId;
    final int type;
    int viewId;
    android.widget.RemoteViews views;
    android.appwidget.AppWidgetProviderInfo widgetInfo;

    public static android.appwidget.PendingHostUpdate updateAppWidget(int i, android.widget.RemoteViews remoteViews) {
        android.appwidget.PendingHostUpdate pendingHostUpdate = new android.appwidget.PendingHostUpdate(i, 0);
        pendingHostUpdate.views = remoteViews;
        return pendingHostUpdate;
    }

    public static android.appwidget.PendingHostUpdate providerChanged(int i, android.appwidget.AppWidgetProviderInfo appWidgetProviderInfo) {
        android.appwidget.PendingHostUpdate pendingHostUpdate = new android.appwidget.PendingHostUpdate(i, 1);
        pendingHostUpdate.widgetInfo = appWidgetProviderInfo;
        return pendingHostUpdate;
    }

    public static android.appwidget.PendingHostUpdate viewDataChanged(int i, int i2) {
        android.appwidget.PendingHostUpdate pendingHostUpdate = new android.appwidget.PendingHostUpdate(i, 2);
        pendingHostUpdate.viewId = i2;
        return pendingHostUpdate;
    }

    public static android.appwidget.PendingHostUpdate appWidgetRemoved(int i) {
        return new android.appwidget.PendingHostUpdate(i, 3);
    }

    private PendingHostUpdate(int i, int i2) {
        this.appWidgetId = i;
        this.type = i2;
    }

    private PendingHostUpdate(android.os.Parcel parcel) {
        this.appWidgetId = parcel.readInt();
        this.type = parcel.readInt();
        switch (this.type) {
            case 0:
                if (parcel.readInt() != 0) {
                    this.views = new android.widget.RemoteViews(parcel);
                    break;
                }
                break;
            case 1:
                if (parcel.readInt() != 0) {
                    this.widgetInfo = new android.appwidget.AppWidgetProviderInfo(parcel);
                    break;
                }
                break;
            case 2:
                this.viewId = parcel.readInt();
                break;
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.appWidgetId);
        parcel.writeInt(this.type);
        switch (this.type) {
            case 0:
                writeNullParcelable(this.views, parcel, i);
                break;
            case 1:
                writeNullParcelable(this.widgetInfo, parcel, i);
                break;
            case 2:
                parcel.writeInt(this.viewId);
                break;
        }
    }

    private void writeNullParcelable(android.os.Parcelable parcelable, android.os.Parcel parcel, int i) {
        if (parcelable != null) {
            parcel.writeInt(1);
            parcelable.writeToParcel(parcel, i);
        } else {
            parcel.writeInt(0);
        }
    }
}
