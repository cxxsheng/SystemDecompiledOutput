package co.nstant.in.cbor.model;

/* loaded from: classes.dex */
public class Map extends co.nstant.in.cbor.model.ChunkableDataItem {
    private final java.util.List<co.nstant.in.cbor.model.DataItem> keys;
    private final java.util.HashMap<co.nstant.in.cbor.model.DataItem, co.nstant.in.cbor.model.DataItem> map;

    @Override // co.nstant.in.cbor.model.ChunkableDataItem
    public /* bridge */ /* synthetic */ boolean isChunked() {
        return super.isChunked();
    }

    @Override // co.nstant.in.cbor.model.ChunkableDataItem
    public /* bridge */ /* synthetic */ co.nstant.in.cbor.model.ChunkableDataItem setChunked(boolean z) {
        return super.setChunked(z);
    }

    public Map() {
        super(co.nstant.in.cbor.model.MajorType.MAP);
        this.keys = new java.util.LinkedList();
        this.map = new java.util.HashMap<>();
    }

    public Map(int i) {
        super(co.nstant.in.cbor.model.MajorType.MAP);
        this.keys = new java.util.LinkedList();
        this.map = new java.util.HashMap<>(i);
    }

    public co.nstant.in.cbor.model.Map put(co.nstant.in.cbor.model.DataItem dataItem, co.nstant.in.cbor.model.DataItem dataItem2) {
        if (this.map.put(dataItem, dataItem2) == null) {
            this.keys.add(dataItem);
        }
        return this;
    }

    public co.nstant.in.cbor.model.DataItem get(co.nstant.in.cbor.model.DataItem dataItem) {
        return this.map.get(dataItem);
    }

    public co.nstant.in.cbor.model.DataItem remove(co.nstant.in.cbor.model.DataItem dataItem) {
        this.keys.remove(dataItem);
        return this.map.remove(dataItem);
    }

    public java.util.Collection<co.nstant.in.cbor.model.DataItem> getKeys() {
        return this.keys;
    }

    public java.util.Collection<co.nstant.in.cbor.model.DataItem> getValues() {
        return this.map.values();
    }

    @Override // co.nstant.in.cbor.model.ChunkableDataItem, co.nstant.in.cbor.model.DataItem
    public boolean equals(java.lang.Object obj) {
        if (obj instanceof co.nstant.in.cbor.model.Map) {
            return super.equals(obj) && this.map.equals(((co.nstant.in.cbor.model.Map) obj).map);
        }
        return false;
    }

    @Override // co.nstant.in.cbor.model.ChunkableDataItem, co.nstant.in.cbor.model.DataItem
    public int hashCode() {
        return super.hashCode() ^ this.map.hashCode();
    }

    public java.lang.String toString() {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        if (isChunked()) {
            sb.append("{_ ");
        } else {
            sb.append("{ ");
        }
        for (co.nstant.in.cbor.model.DataItem dataItem : this.keys) {
            sb.append(dataItem);
            sb.append(": ");
            sb.append(this.map.get(dataItem));
            sb.append(", ");
        }
        if (sb.toString().endsWith(", ")) {
            sb.setLength(sb.length() - 2);
        }
        sb.append(" }");
        return sb.toString();
    }
}
