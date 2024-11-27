package android.appwidget;

/* loaded from: classes.dex */
public class AppWidgetProvider extends android.content.BroadcastReceiver {
    @Override // android.content.BroadcastReceiver
    public void onReceive(android.content.Context context, android.content.Intent intent) {
        android.os.Bundle extras;
        int[] intArray;
        java.lang.String action = intent.getAction();
        if (android.appwidget.AppWidgetManager.ACTION_APPWIDGET_ENABLE_AND_UPDATE.equals(action)) {
            onReceive(context, new android.content.Intent(intent).setAction(android.appwidget.AppWidgetManager.ACTION_APPWIDGET_ENABLED));
            onReceive(context, new android.content.Intent(intent).setAction(android.appwidget.AppWidgetManager.ACTION_APPWIDGET_UPDATE));
            return;
        }
        if (android.appwidget.AppWidgetManager.ACTION_APPWIDGET_UPDATE.equals(action)) {
            android.os.Bundle extras2 = intent.getExtras();
            if (extras2 != null && (intArray = extras2.getIntArray(android.appwidget.AppWidgetManager.EXTRA_APPWIDGET_IDS)) != null && intArray.length > 0) {
                onUpdate(context, android.appwidget.AppWidgetManager.getInstance(context), intArray);
                return;
            }
            return;
        }
        if (android.appwidget.AppWidgetManager.ACTION_APPWIDGET_DELETED.equals(action)) {
            android.os.Bundle extras3 = intent.getExtras();
            if (extras3 != null && extras3.containsKey(android.appwidget.AppWidgetManager.EXTRA_APPWIDGET_ID)) {
                onDeleted(context, new int[]{extras3.getInt(android.appwidget.AppWidgetManager.EXTRA_APPWIDGET_ID)});
                return;
            }
            return;
        }
        if (android.appwidget.AppWidgetManager.ACTION_APPWIDGET_OPTIONS_CHANGED.equals(action)) {
            android.os.Bundle extras4 = intent.getExtras();
            if (extras4 != null && extras4.containsKey(android.appwidget.AppWidgetManager.EXTRA_APPWIDGET_ID) && extras4.containsKey(android.appwidget.AppWidgetManager.EXTRA_APPWIDGET_OPTIONS)) {
                onAppWidgetOptionsChanged(context, android.appwidget.AppWidgetManager.getInstance(context), extras4.getInt(android.appwidget.AppWidgetManager.EXTRA_APPWIDGET_ID), extras4.getBundle(android.appwidget.AppWidgetManager.EXTRA_APPWIDGET_OPTIONS));
                return;
            }
            return;
        }
        if (android.appwidget.AppWidgetManager.ACTION_APPWIDGET_ENABLED.equals(action)) {
            onEnabled(context);
            return;
        }
        if (android.appwidget.AppWidgetManager.ACTION_APPWIDGET_DISABLED.equals(action)) {
            onDisabled(context);
            return;
        }
        if (android.appwidget.AppWidgetManager.ACTION_APPWIDGET_RESTORED.equals(action) && (extras = intent.getExtras()) != null) {
            int[] intArray2 = extras.getIntArray(android.appwidget.AppWidgetManager.EXTRA_APPWIDGET_OLD_IDS);
            int[] intArray3 = extras.getIntArray(android.appwidget.AppWidgetManager.EXTRA_APPWIDGET_IDS);
            if (intArray2 != null && intArray2.length > 0) {
                onRestored(context, intArray2, intArray3);
                onUpdate(context, android.appwidget.AppWidgetManager.getInstance(context), intArray3);
            }
        }
    }

    public void onUpdate(android.content.Context context, android.appwidget.AppWidgetManager appWidgetManager, int[] iArr) {
    }

    public void onAppWidgetOptionsChanged(android.content.Context context, android.appwidget.AppWidgetManager appWidgetManager, int i, android.os.Bundle bundle) {
    }

    public void onDeleted(android.content.Context context, int[] iArr) {
    }

    public void onEnabled(android.content.Context context) {
    }

    public void onDisabled(android.content.Context context) {
    }

    public void onRestored(android.content.Context context, int[] iArr, int[] iArr2) {
    }
}
