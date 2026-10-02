package net.aymanx.examples.recycleview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.Collections;
import java.util.List;

public final class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.Holder> {
    public interface SelectionListener { void onSelected(int id); }
    private final boolean horizontal;
    private final SelectionListener listener;
    private List<PhotoCatalog.Photo> photos = Collections.emptyList();
    private int selectedId;

    public PhotoAdapter(boolean horizontal, SelectionListener listener) {
        this.horizontal = horizontal;
        this.listener = listener;
        setHasStableIds(true);
    }

    public void setData(List<PhotoCatalog.Photo> next, int selection) {
        photos = next;
        selectedId = selection;
        notifyDataSetChanged();
    }

    @Override public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View card = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_photo, parent, false);
        if (horizontal) {
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) card.getLayoutParams();
            params.width = Math.round(144 * parent.getResources().getDisplayMetrics().density);
            card.setLayoutParams(params);
        }
        return new Holder(card);
    }

    @Override public void onBindViewHolder(Holder holder, int position) {
        PhotoCatalog.Photo photo = photos.get(position);
        holder.tile.setPhoto(photo);
        holder.label.setText(photo.label);
        holder.itemView.setSelected(photo.id == selectedId);
        holder.itemView.setContentDescription(holder.itemView.getResources().getString(
                photo.id == selectedId ? R.string.selected_photo_accessibility : R.string.photo_accessibility, photo.label));
    }

    @Override public long getItemId(int position) { return photos.get(position).id; }
    @Override public int getItemCount() { return photos.size(); }

    final class Holder extends RecyclerView.ViewHolder {
        final ShapeTileView tile;
        final TextView label;

        Holder(View card) {
            super(card);
            tile = card.findViewById(R.id.photo_tile);
            label = card.findViewById(R.id.photo_label);
            card.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && position < photos.size()) {
                        listener.onSelected(photos.get(position).id);
                    }
                }
            });
        }
    }
}
