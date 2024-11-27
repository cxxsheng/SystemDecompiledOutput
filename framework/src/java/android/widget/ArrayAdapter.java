package android.widget;

/* loaded from: classes4.dex */
public class ArrayAdapter<T> extends android.widget.BaseAdapter implements android.widget.Filterable, android.widget.ThemedSpinnerAdapter {
    private final android.content.Context mContext;
    private android.view.LayoutInflater mDropDownInflater;
    private int mDropDownResource;
    private int mFieldId;
    private android.widget.ArrayAdapter<T>.ArrayFilter mFilter;
    private final android.view.LayoutInflater mInflater;
    private final java.lang.Object mLock;
    private boolean mNotifyOnChange;
    private java.util.List<T> mObjects;
    private boolean mObjectsFromResources;
    private java.util.ArrayList<T> mOriginalValues;
    private final int mResource;

    public ArrayAdapter(android.content.Context context, int i) {
        this(context, i, 0, new java.util.ArrayList());
    }

    public ArrayAdapter(android.content.Context context, int i, int i2) {
        this(context, i, i2, new java.util.ArrayList());
    }

    public ArrayAdapter(android.content.Context context, int i, T[] tArr) {
        this(context, i, 0, java.util.Arrays.asList(tArr));
    }

    public ArrayAdapter(android.content.Context context, int i, int i2, T[] tArr) {
        this(context, i, i2, java.util.Arrays.asList(tArr));
    }

    public ArrayAdapter(android.content.Context context, int i, java.util.List<T> list) {
        this(context, i, 0, list);
    }

    public ArrayAdapter(android.content.Context context, int i, int i2, java.util.List<T> list) {
        this(context, i, i2, list, false);
    }

    private ArrayAdapter(android.content.Context context, int i, int i2, java.util.List<T> list, boolean z) {
        this.mLock = new java.lang.Object();
        this.mFieldId = 0;
        this.mNotifyOnChange = true;
        this.mContext = context;
        this.mInflater = android.view.LayoutInflater.from(context);
        this.mDropDownResource = i;
        this.mResource = i;
        this.mObjects = list;
        this.mObjectsFromResources = z;
        this.mFieldId = i2;
    }

    public void add(T t) {
        synchronized (this.mLock) {
            if (this.mOriginalValues != null) {
                this.mOriginalValues.add(t);
            } else {
                this.mObjects.add(t);
            }
            this.mObjectsFromResources = false;
        }
        if (this.mNotifyOnChange) {
            notifyDataSetChanged();
        }
    }

    public void addAll(java.util.Collection<? extends T> collection) {
        synchronized (this.mLock) {
            if (this.mOriginalValues != null) {
                this.mOriginalValues.addAll(collection);
            } else {
                this.mObjects.addAll(collection);
            }
            this.mObjectsFromResources = false;
        }
        if (this.mNotifyOnChange) {
            notifyDataSetChanged();
        }
    }

    public void addAll(T... tArr) {
        synchronized (this.mLock) {
            if (this.mOriginalValues != null) {
                java.util.Collections.addAll(this.mOriginalValues, tArr);
            } else {
                java.util.Collections.addAll(this.mObjects, tArr);
            }
            this.mObjectsFromResources = false;
        }
        if (this.mNotifyOnChange) {
            notifyDataSetChanged();
        }
    }

    public void insert(T t, int i) {
        synchronized (this.mLock) {
            if (this.mOriginalValues != null) {
                this.mOriginalValues.add(i, t);
            } else {
                this.mObjects.add(i, t);
            }
            this.mObjectsFromResources = false;
        }
        if (this.mNotifyOnChange) {
            notifyDataSetChanged();
        }
    }

    public void remove(T t) {
        synchronized (this.mLock) {
            if (this.mOriginalValues != null) {
                this.mOriginalValues.remove(t);
            } else {
                this.mObjects.remove(t);
            }
            this.mObjectsFromResources = false;
        }
        if (this.mNotifyOnChange) {
            notifyDataSetChanged();
        }
    }

    public void clear() {
        synchronized (this.mLock) {
            if (this.mOriginalValues != null) {
                this.mOriginalValues.clear();
            } else {
                this.mObjects.clear();
            }
            this.mObjectsFromResources = false;
        }
        if (this.mNotifyOnChange) {
            notifyDataSetChanged();
        }
    }

    public void sort(java.util.Comparator<? super T> comparator) {
        synchronized (this.mLock) {
            if (this.mOriginalValues != null) {
                java.util.Collections.sort(this.mOriginalValues, comparator);
            } else {
                java.util.Collections.sort(this.mObjects, comparator);
            }
        }
        if (this.mNotifyOnChange) {
            notifyDataSetChanged();
        }
    }

    @Override // android.widget.BaseAdapter
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
        this.mNotifyOnChange = true;
    }

    public void setNotifyOnChange(boolean z) {
        this.mNotifyOnChange = z;
    }

    public android.content.Context getContext() {
        return this.mContext;
    }

    @Override // android.widget.Adapter
    public int getCount() {
        return this.mObjects.size();
    }

    @Override // android.widget.Adapter
    public T getItem(int i) {
        return this.mObjects.get(i);
    }

    public int getPosition(T t) {
        return this.mObjects.indexOf(t);
    }

    @Override // android.widget.Adapter
    public long getItemId(int i) {
        return i;
    }

    @Override // android.widget.Adapter
    public android.view.View getView(int i, android.view.View view, android.view.ViewGroup viewGroup) {
        return createViewFromResource(this.mInflater, i, view, viewGroup, this.mResource);
    }

    private android.view.View createViewFromResource(android.view.LayoutInflater layoutInflater, int i, android.view.View view, android.view.ViewGroup viewGroup, int i2) {
        android.widget.TextView textView;
        if (view == null) {
            view = layoutInflater.inflate(i2, viewGroup, false);
        }
        try {
            if (this.mFieldId == 0) {
                textView = (android.widget.TextView) view;
            } else {
                textView = (android.widget.TextView) view.findViewById(this.mFieldId);
                if (textView == null) {
                    throw new java.lang.RuntimeException("Failed to find view with ID " + this.mContext.getResources().getResourceName(this.mFieldId) + " in item layout");
                }
            }
            T item = getItem(i);
            if (item instanceof java.lang.CharSequence) {
                textView.lambda$setTextAsync$0((java.lang.CharSequence) item);
            } else {
                textView.lambda$setTextAsync$0(item.toString());
            }
            return view;
        } catch (java.lang.ClassCastException e) {
            android.util.Log.e("ArrayAdapter", "You must supply a resource ID for a TextView");
            throw new java.lang.IllegalStateException("ArrayAdapter requires the resource ID to be a TextView", e);
        }
    }

    public void setDropDownViewResource(int i) {
        this.mDropDownResource = i;
    }

    @Override // android.widget.ThemedSpinnerAdapter
    public void setDropDownViewTheme(android.content.res.Resources.Theme theme) {
        if (theme == null) {
            this.mDropDownInflater = null;
        } else if (theme == this.mInflater.getContext().getTheme()) {
            this.mDropDownInflater = this.mInflater;
        } else {
            this.mDropDownInflater = android.view.LayoutInflater.from(new android.view.ContextThemeWrapper(this.mContext, theme));
        }
    }

    @Override // android.widget.ThemedSpinnerAdapter
    public android.content.res.Resources.Theme getDropDownViewTheme() {
        if (this.mDropDownInflater == null) {
            return null;
        }
        return this.mDropDownInflater.getContext().getTheme();
    }

    @Override // android.widget.BaseAdapter, android.widget.SpinnerAdapter
    public android.view.View getDropDownView(int i, android.view.View view, android.view.ViewGroup viewGroup) {
        return createViewFromResource(this.mDropDownInflater == null ? this.mInflater : this.mDropDownInflater, i, view, viewGroup, this.mDropDownResource);
    }

    public static android.widget.ArrayAdapter<java.lang.CharSequence> createFromResource(android.content.Context context, int i, int i2) {
        return new android.widget.ArrayAdapter<>(context, i2, 0, java.util.Arrays.asList(context.getResources().getTextArray(i)), true);
    }

    @Override // android.widget.Filterable
    public android.widget.Filter getFilter() {
        if (this.mFilter == null) {
            this.mFilter = new android.widget.ArrayAdapter.ArrayFilter();
        }
        return this.mFilter;
    }

    @Override // android.widget.BaseAdapter, android.widget.Adapter
    public java.lang.CharSequence[] getAutofillOptions() {
        java.lang.CharSequence[] autofillOptions = super.getAutofillOptions();
        if (autofillOptions != null) {
            return autofillOptions;
        }
        if (!this.mObjectsFromResources || this.mObjects == null || this.mObjects.isEmpty()) {
            return null;
        }
        java.lang.CharSequence[] charSequenceArr = new java.lang.CharSequence[this.mObjects.size()];
        this.mObjects.toArray(charSequenceArr);
        return charSequenceArr;
    }

    private class ArrayFilter extends android.widget.Filter {
        private ArrayFilter() {
        }

        @Override // android.widget.Filter
        protected android.widget.Filter.FilterResults performFiltering(java.lang.CharSequence charSequence) {
            java.util.ArrayList arrayList;
            java.util.ArrayList arrayList2;
            android.widget.Filter.FilterResults filterResults = new android.widget.Filter.FilterResults();
            if (android.widget.ArrayAdapter.this.mOriginalValues == null) {
                synchronized (android.widget.ArrayAdapter.this.mLock) {
                    android.widget.ArrayAdapter.this.mOriginalValues = new java.util.ArrayList(android.widget.ArrayAdapter.this.mObjects);
                }
            }
            if (charSequence == null || charSequence.length() == 0) {
                synchronized (android.widget.ArrayAdapter.this.mLock) {
                    arrayList = new java.util.ArrayList(android.widget.ArrayAdapter.this.mOriginalValues);
                }
                filterResults.values = arrayList;
                filterResults.count = arrayList.size();
            } else {
                java.lang.String lowerCase = charSequence.toString().toLowerCase();
                synchronized (android.widget.ArrayAdapter.this.mLock) {
                    arrayList2 = new java.util.ArrayList(android.widget.ArrayAdapter.this.mOriginalValues);
                }
                int size = arrayList2.size();
                java.util.ArrayList arrayList3 = new java.util.ArrayList();
                for (int i = 0; i < size; i++) {
                    java.lang.Object obj = arrayList2.get(i);
                    java.lang.String lowerCase2 = obj.toString().toLowerCase();
                    if (lowerCase2.startsWith(lowerCase)) {
                        arrayList3.add(obj);
                    } else {
                        java.lang.String[] split = lowerCase2.split(" ");
                        int length = split.length;
                        int i2 = 0;
                        while (true) {
                            if (i2 >= length) {
                                break;
                            }
                            if (!split[i2].startsWith(lowerCase)) {
                                i2++;
                            } else {
                                arrayList3.add(obj);
                                break;
                            }
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
            android.widget.ArrayAdapter.this.mObjects = (java.util.List) filterResults.values;
            if (filterResults.count > 0) {
                android.widget.ArrayAdapter.this.notifyDataSetChanged();
            } else {
                android.widget.ArrayAdapter.this.notifyDataSetInvalidated();
            }
        }
    }
}
