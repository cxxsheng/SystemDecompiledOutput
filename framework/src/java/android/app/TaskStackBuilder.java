package android.app;

/* loaded from: classes.dex */
public class TaskStackBuilder {
    private static final java.lang.String TAG = "TaskStackBuilder";
    private final java.util.ArrayList<android.content.Intent> mIntents = new java.util.ArrayList<>();
    private final android.content.Context mSourceContext;

    private TaskStackBuilder(android.content.Context context) {
        this.mSourceContext = context;
    }

    public static android.app.TaskStackBuilder create(android.content.Context context) {
        return new android.app.TaskStackBuilder(context);
    }

    public android.app.TaskStackBuilder addNextIntent(android.content.Intent intent) {
        this.mIntents.add(intent);
        return this;
    }

    public android.app.TaskStackBuilder addNextIntentWithParentStack(android.content.Intent intent) {
        android.content.ComponentName component = intent.getComponent();
        if (component == null) {
            component = intent.resolveActivity(this.mSourceContext.getPackageManager());
        }
        if (component != null) {
            addParentStack(component);
        }
        addNextIntent(intent);
        return this;
    }

    public android.app.TaskStackBuilder addParentStack(android.app.Activity activity) {
        android.content.Intent parentActivityIntent = activity.getParentActivityIntent();
        if (parentActivityIntent != null) {
            android.content.ComponentName component = parentActivityIntent.getComponent();
            if (component == null) {
                component = parentActivityIntent.resolveActivity(this.mSourceContext.getPackageManager());
            }
            addParentStack(component);
            addNextIntent(parentActivityIntent);
        }
        return this;
    }

    public android.app.TaskStackBuilder addParentStack(java.lang.Class<?> cls) {
        return addParentStack(new android.content.ComponentName(this.mSourceContext, cls));
    }

    public android.app.TaskStackBuilder addParentStack(android.content.ComponentName componentName) {
        android.content.Intent component;
        int size = this.mIntents.size();
        android.content.pm.PackageManager packageManager = this.mSourceContext.getPackageManager();
        try {
            android.content.pm.ActivityInfo activityInfo = packageManager.getActivityInfo(componentName, 0);
            java.lang.String str = activityInfo.parentActivityName;
            while (str != null) {
                android.content.ComponentName componentName2 = new android.content.ComponentName(activityInfo.packageName, str);
                activityInfo = packageManager.getActivityInfo(componentName2, 0);
                str = activityInfo.parentActivityName;
                if (str == null && size == 0) {
                    component = android.content.Intent.makeMainActivity(componentName2);
                } else {
                    component = new android.content.Intent().setComponent(componentName2);
                }
                this.mIntents.add(size, component);
            }
            return this;
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            android.util.Log.e(TAG, "Bad ComponentName while traversing activity parent metadata");
            throw new java.lang.IllegalArgumentException(e);
        }
    }

    public int getIntentCount() {
        return this.mIntents.size();
    }

    public android.content.Intent editIntentAt(int i) {
        return this.mIntents.get(i);
    }

    public void startActivities() {
        startActivities(null);
    }

    public int startActivities(android.os.Bundle bundle, android.os.UserHandle userHandle) {
        if (this.mIntents.isEmpty()) {
            throw new java.lang.IllegalStateException("No intents added to TaskStackBuilder; cannot startActivities");
        }
        return this.mSourceContext.startActivitiesAsUser(getIntents(), bundle, userHandle);
    }

    public void startActivities(android.os.Bundle bundle) {
        startActivities(bundle, this.mSourceContext.getUser());
    }

    public android.app.PendingIntent getPendingIntent(int i, int i2) {
        return getPendingIntent(i, i2, null);
    }

    public android.app.PendingIntent getPendingIntent(int i, int i2, android.os.Bundle bundle) {
        if (this.mIntents.isEmpty()) {
            throw new java.lang.IllegalStateException("No intents added to TaskStackBuilder; cannot getPendingIntent");
        }
        return android.app.PendingIntent.getActivities(this.mSourceContext, i, getIntents(), i2, bundle);
    }

    public android.app.PendingIntent getPendingIntent(int i, int i2, android.os.Bundle bundle, android.os.UserHandle userHandle) {
        if (this.mIntents.isEmpty()) {
            throw new java.lang.IllegalStateException("No intents added to TaskStackBuilder; cannot getPendingIntent");
        }
        return android.app.PendingIntent.getActivitiesAsUser(this.mSourceContext, i, getIntents(), i2, bundle, userHandle);
    }

    public android.content.Intent[] getIntents() {
        int size = this.mIntents.size();
        android.content.Intent[] intentArr = new android.content.Intent[size];
        if (size == 0) {
            return intentArr;
        }
        intentArr[0] = new android.content.Intent(this.mIntents.get(0)).addFlags(268484608);
        for (int i = 1; i < size; i++) {
            intentArr[i] = new android.content.Intent(this.mIntents.get(i));
        }
        return intentArr;
    }
}
