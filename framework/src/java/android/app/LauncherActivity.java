package android.app;

@java.lang.Deprecated
/* loaded from: classes.dex */
public abstract class LauncherActivity extends android.app.ListActivity {
    android.app.LauncherActivity.IconResizer mIconResizer;
    android.content.Intent mIntent;
    android.content.pm.PackageManager mPackageManager;

    public static class ListItem {
        public java.lang.String className;
        public android.os.Bundle extras;
        public android.graphics.drawable.Drawable icon;
        public java.lang.CharSequence label;
        public java.lang.String packageName;
        public android.content.pm.ResolveInfo resolveInfo;

        ListItem(android.content.pm.PackageManager packageManager, android.content.pm.ResolveInfo resolveInfo, android.app.LauncherActivity.IconResizer iconResizer) {
            this.resolveInfo = resolveInfo;
            this.label = resolveInfo.loadLabel(packageManager);
            android.content.pm.ComponentInfo componentInfo = resolveInfo.activityInfo;
            componentInfo = componentInfo == null ? resolveInfo.serviceInfo : componentInfo;
            if (this.label == null && componentInfo != null) {
                this.label = resolveInfo.activityInfo.name;
            }
            if (iconResizer != null) {
                this.icon = iconResizer.createIconThumbnail(resolveInfo.loadIcon(packageManager));
            }
            this.packageName = componentInfo.applicationInfo.packageName;
            this.className = componentInfo.name;
        }

        public ListItem() {
        }
    }

    private class ActivityAdapter extends android.widget.BaseAdapter implements android.widget.Filterable {
        private final java.lang.Object lock = new java.lang.Object();
        protected java.util.List<android.app.LauncherActivity.ListItem> mActivitiesList;
        private android.widget.Filter mFilter;
        protected final android.app.LauncherActivity.IconResizer mIconResizer;
        protected final android.view.LayoutInflater mInflater;
        private java.util.ArrayList<android.app.LauncherActivity.ListItem> mOriginalValues;
        private final boolean mShowIcons;

        public ActivityAdapter(android.app.LauncherActivity.IconResizer iconResizer) {
            this.mIconResizer = iconResizer;
            this.mInflater = (android.view.LayoutInflater) android.app.LauncherActivity.this.getSystemService(android.content.Context.LAYOUT_INFLATER_SERVICE);
            this.mShowIcons = android.app.LauncherActivity.this.onEvaluateShowIcons();
            this.mActivitiesList = android.app.LauncherActivity.this.makeListItems();
        }

        public android.content.Intent intentForPosition(int i) {
            if (this.mActivitiesList == null) {
                return null;
            }
            android.content.Intent intent = new android.content.Intent(android.app.LauncherActivity.this.mIntent);
            android.app.LauncherActivity.ListItem listItem = this.mActivitiesList.get(i);
            intent.setClassName(listItem.packageName, listItem.className);
            if (listItem.extras != null) {
                intent.putExtras(listItem.extras);
            }
            return intent;
        }

        public android.app.LauncherActivity.ListItem itemForPosition(int i) {
            if (this.mActivitiesList == null) {
                return null;
            }
            return this.mActivitiesList.get(i);
        }

        @Override // android.widget.Adapter
        public int getCount() {
            if (this.mActivitiesList != null) {
                return this.mActivitiesList.size();
            }
            return 0;
        }

        @Override // android.widget.Adapter
        public java.lang.Object getItem(int i) {
            return java.lang.Integer.valueOf(i);
        }

        @Override // android.widget.Adapter
        public long getItemId(int i) {
            return i;
        }

        @Override // android.widget.Adapter
        public android.view.View getView(int i, android.view.View view, android.view.ViewGroup viewGroup) {
            if (view == null) {
                view = this.mInflater.inflate(com.android.internal.R.layout.activity_list_item_2, viewGroup, false);
            }
            bindView(view, this.mActivitiesList.get(i));
            return view;
        }

        private void bindView(android.view.View view, android.app.LauncherActivity.ListItem listItem) {
            android.widget.TextView textView = (android.widget.TextView) view;
            textView.lambda$setTextAsync$0(listItem.label);
            if (this.mShowIcons) {
                if (listItem.icon == null) {
                    listItem.icon = this.mIconResizer.createIconThumbnail(listItem.resolveInfo.loadIcon(android.app.LauncherActivity.this.getPackageManager()));
                }
                textView.setCompoundDrawablesRelativeWithIntrinsicBounds(listItem.icon, (android.graphics.drawable.Drawable) null, (android.graphics.drawable.Drawable) null, (android.graphics.drawable.Drawable) null);
            }
        }

        @Override // android.widget.Filterable
        public android.widget.Filter getFilter() {
            if (this.mFilter == null) {
                this.mFilter = new android.app.LauncherActivity.ActivityAdapter.ArrayFilter();
            }
            return this.mFilter;
        }

        private class ArrayFilter extends android.widget.Filter {
            private ArrayFilter() {
            }

            @Override // android.widget.Filter
            protected android.widget.Filter.FilterResults performFiltering(java.lang.CharSequence charSequence) {
                android.widget.Filter.FilterResults filterResults = new android.widget.Filter.FilterResults();
                if (android.app.LauncherActivity.ActivityAdapter.this.mOriginalValues == null) {
                    synchronized (android.app.LauncherActivity.ActivityAdapter.this.lock) {
                        android.app.LauncherActivity.ActivityAdapter.this.mOriginalValues = new java.util.ArrayList(android.app.LauncherActivity.ActivityAdapter.this.mActivitiesList);
                    }
                }
                if (charSequence == null || charSequence.length() == 0) {
                    synchronized (android.app.LauncherActivity.ActivityAdapter.this.lock) {
                        java.util.ArrayList arrayList = new java.util.ArrayList(android.app.LauncherActivity.ActivityAdapter.this.mOriginalValues);
                        filterResults.values = arrayList;
                        filterResults.count = arrayList.size();
                    }
                } else {
                    java.lang.String lowerCase = charSequence.toString().toLowerCase();
                    java.util.ArrayList arrayList2 = android.app.LauncherActivity.ActivityAdapter.this.mOriginalValues;
                    int size = arrayList2.size();
                    java.util.ArrayList arrayList3 = new java.util.ArrayList(size);
                    for (int i = 0; i < size; i++) {
                        android.app.LauncherActivity.ListItem listItem = (android.app.LauncherActivity.ListItem) arrayList2.get(i);
                        java.lang.String[] split = listItem.label.toString().toLowerCase().split(" ");
                        int length = split.length;
                        int i2 = 0;
                        while (true) {
                            if (i2 >= length) {
                                break;
                            }
                            if (!split[i2].startsWith(lowerCase)) {
                                i2++;
                            } else {
                                arrayList3.add(listItem);
                                break;
                            }
                        }
                    }
                    filterResults.values = arrayList3;
                    filterResults.count = arrayList3.size();
                }
                return filterResults;
            }

            @Override // android.widget.Filter
            protected void publishResults(java.lang.CharSequence charSequence, android.widget.Filter.FilterResults filterResults) {
                android.app.LauncherActivity.ActivityAdapter.this.mActivitiesList = (java.util.List) filterResults.values;
                if (filterResults.count > 0) {
                    android.app.LauncherActivity.ActivityAdapter.this.notifyDataSetChanged();
                } else {
                    android.app.LauncherActivity.ActivityAdapter.this.notifyDataSetInvalidated();
                }
            }
        }
    }

    public class IconResizer {
        private int mIconHeight;
        private int mIconWidth;
        private final android.graphics.Rect mOldBounds = new android.graphics.Rect();
        private android.graphics.Canvas mCanvas = new android.graphics.Canvas();

        public IconResizer() {
            this.mIconWidth = -1;
            this.mIconHeight = -1;
            this.mCanvas.setDrawFilter(new android.graphics.PaintFlagsDrawFilter(4, 2));
            int dimension = (int) android.app.LauncherActivity.this.getResources().getDimension(17104896);
            this.mIconHeight = dimension;
            this.mIconWidth = dimension;
        }

        public android.graphics.drawable.Drawable createIconThumbnail(android.graphics.drawable.Drawable drawable) {
            int i = this.mIconWidth;
            int i2 = this.mIconHeight;
            int intrinsicWidth = drawable.getIntrinsicWidth();
            int intrinsicHeight = drawable.getIntrinsicHeight();
            if (drawable instanceof android.graphics.drawable.PaintDrawable) {
                android.graphics.drawable.PaintDrawable paintDrawable = (android.graphics.drawable.PaintDrawable) drawable;
                paintDrawable.setIntrinsicWidth(i);
                paintDrawable.setIntrinsicHeight(i2);
            }
            if (i <= 0 || i2 <= 0) {
                return drawable;
            }
            if (i < intrinsicWidth || i2 < intrinsicHeight) {
                float f = intrinsicWidth / intrinsicHeight;
                if (intrinsicWidth > intrinsicHeight) {
                    i2 = (int) (i / f);
                } else if (intrinsicHeight > intrinsicWidth) {
                    i = (int) (i2 * f);
                }
                android.graphics.Bitmap createBitmap = android.graphics.Bitmap.createBitmap(this.mIconWidth, this.mIconHeight, drawable.getOpacity() != -1 ? android.graphics.Bitmap.Config.ARGB_8888 : android.graphics.Bitmap.Config.RGB_565);
                android.graphics.Canvas canvas = this.mCanvas;
                canvas.setBitmap(createBitmap);
                this.mOldBounds.set(drawable.getBounds());
                int i3 = (this.mIconWidth - i) / 2;
                int i4 = (this.mIconHeight - i2) / 2;
                drawable.setBounds(i3, i4, i + i3, i2 + i4);
                drawable.draw(canvas);
                drawable.setBounds(this.mOldBounds);
                android.graphics.drawable.BitmapDrawable bitmapDrawable = new android.graphics.drawable.BitmapDrawable(android.app.LauncherActivity.this.getResources(), createBitmap);
                canvas.setBitmap(null);
                return bitmapDrawable;
            }
            if (intrinsicWidth < i && intrinsicHeight < i2) {
                android.graphics.Bitmap createBitmap2 = android.graphics.Bitmap.createBitmap(this.mIconWidth, this.mIconHeight, android.graphics.Bitmap.Config.ARGB_8888);
                android.graphics.Canvas canvas2 = this.mCanvas;
                canvas2.setBitmap(createBitmap2);
                this.mOldBounds.set(drawable.getBounds());
                int i5 = (i - intrinsicWidth) / 2;
                int i6 = (i2 - intrinsicHeight) / 2;
                drawable.setBounds(i5, i6, intrinsicWidth + i5, intrinsicHeight + i6);
                drawable.draw(canvas2);
                drawable.setBounds(this.mOldBounds);
                android.graphics.drawable.BitmapDrawable bitmapDrawable2 = new android.graphics.drawable.BitmapDrawable(android.app.LauncherActivity.this.getResources(), createBitmap2);
                canvas2.setBitmap(null);
                return bitmapDrawable2;
            }
            return drawable;
        }
    }

    @Override // android.app.Activity
    protected void onCreate(android.os.Bundle bundle) {
        super.onCreate(bundle);
        this.mPackageManager = getPackageManager();
        if (!this.mPackageManager.hasSystemFeature(android.content.pm.PackageManager.FEATURE_WATCH)) {
            requestWindowFeature(5);
            setProgressBarIndeterminateVisibility(true);
        }
        onSetContentView();
        this.mIconResizer = new android.app.LauncherActivity.IconResizer();
        this.mIntent = new android.content.Intent(getTargetIntent());
        this.mIntent.setComponent(null);
        this.mAdapter = new android.app.LauncherActivity.ActivityAdapter(this.mIconResizer);
        setListAdapter(this.mAdapter);
        getListView().setTextFilterEnabled(true);
        updateAlertTitle();
        updateButtonText();
        if (!this.mPackageManager.hasSystemFeature(android.content.pm.PackageManager.FEATURE_WATCH)) {
            setProgressBarIndeterminateVisibility(false);
        }
    }

    private void updateAlertTitle() {
        android.widget.TextView textView = (android.widget.TextView) findViewById(com.android.internal.R.id.alertTitle);
        if (textView != null) {
            textView.lambda$setTextAsync$0(getTitle());
        }
    }

    private void updateButtonText() {
        android.widget.Button button = (android.widget.Button) findViewById(16908313);
        if (button != null) {
            button.setOnClickListener(new android.view.View.OnClickListener() { // from class: android.app.LauncherActivity.1
                @Override // android.view.View.OnClickListener
                public void onClick(android.view.View view) {
                    android.app.LauncherActivity.this.finish();
                }
            });
        }
    }

    @Override // android.app.Activity
    public void setTitle(java.lang.CharSequence charSequence) {
        super.setTitle(charSequence);
        updateAlertTitle();
    }

    @Override // android.app.Activity
    public void setTitle(int i) {
        super.setTitle(i);
        updateAlertTitle();
    }

    protected void onSetContentView() {
        setContentView(com.android.internal.R.layout.activity_list);
    }

    @Override // android.app.ListActivity
    protected void onListItemClick(android.widget.ListView listView, android.view.View view, int i, long j) {
        startActivity(intentForPosition(i));
    }

    protected android.content.Intent intentForPosition(int i) {
        return ((android.app.LauncherActivity.ActivityAdapter) this.mAdapter).intentForPosition(i);
    }

    protected android.app.LauncherActivity.ListItem itemForPosition(int i) {
        return ((android.app.LauncherActivity.ActivityAdapter) this.mAdapter).itemForPosition(i);
    }

    protected android.content.Intent getTargetIntent() {
        return new android.content.Intent();
    }

    protected java.util.List<android.content.pm.ResolveInfo> onQueryPackageManager(android.content.Intent intent) {
        return this.mPackageManager.queryIntentActivities(intent, 0);
    }

    protected void onSortResultList(java.util.List<android.content.pm.ResolveInfo> list) {
        java.util.Collections.sort(list, new android.content.pm.ResolveInfo.DisplayNameComparator(this.mPackageManager));
    }

    public java.util.List<android.app.LauncherActivity.ListItem> makeListItems() {
        java.util.List<android.content.pm.ResolveInfo> onQueryPackageManager = onQueryPackageManager(this.mIntent);
        onSortResultList(onQueryPackageManager);
        java.util.ArrayList arrayList = new java.util.ArrayList(onQueryPackageManager.size());
        int size = onQueryPackageManager.size();
        for (int i = 0; i < size; i++) {
            arrayList.add(new android.app.LauncherActivity.ListItem(this.mPackageManager, onQueryPackageManager.get(i), null));
        }
        return arrayList;
    }

    protected boolean onEvaluateShowIcons() {
        return true;
    }
}
