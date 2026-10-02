package net.aymanx.examples.recycleview;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public final class MainActivity extends Activity {
    private static final String SELECTED = "selected-photo";
    private static final String HIDDEN = "hidden-photos";
    private GalleryState state;
    private RecyclerView horizontalPhotos;
    private RecyclerView gridPhotos;
    private PhotoAdapter horizontalAdapter;
    private PhotoAdapter gridAdapter;
    private TextView selection;
    private TextView emptyState;
    private Button toggle;

    @Override public void onCreate(Bundle savedState) {
        super.onCreate(savedState);
        setContentView(R.layout.activity_main);
        state = new GalleryState(savedState == null ? -1 : savedState.getInt(SELECTED, -1),
                savedState != null && savedState.getBoolean(HIDDEN, false));
        horizontalPhotos = findViewById(R.id.horizontal_photos);
        gridPhotos = findViewById(R.id.photo_grid);
        selection = findViewById(R.id.selection);
        emptyState = findViewById(R.id.empty_state);
        toggle = findViewById(R.id.toggle_photos);

        PhotoAdapter.SelectionListener listener = new PhotoAdapter.SelectionListener() {
            @Override public void onSelected(int id) { state.select(id); render(); }
        };
        horizontalAdapter = new PhotoAdapter(true, listener);
        gridAdapter = new PhotoAdapter(false, listener);
        horizontalPhotos.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        gridPhotos.setLayoutManager(new GridLayoutManager(this, getResources().getInteger(R.integer.gallery_columns)));
        horizontalPhotos.setAdapter(horizontalAdapter);
        gridPhotos.setAdapter(gridAdapter);
        toggle.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) { state.toggleVisibility(); render(); }
        });
        render();
    }

    private void render() {
        horizontalAdapter.setData(state.visiblePhotos(), state.selectedId());
        gridAdapter.setData(state.visiblePhotos(), state.selectedId());
        horizontalPhotos.setVisibility(state.isHidden() ? View.GONE : View.VISIBLE);
        gridPhotos.setVisibility(state.isHidden() ? View.GONE : View.VISIBLE);
        emptyState.setVisibility(state.isHidden() ? View.VISIBLE : View.GONE);
        toggle.setText(state.isHidden() ? R.string.show_photos : R.string.hide_photos);
        selection.setText(state.isHidden() ? getString(R.string.hidden_status) :
                getString(R.string.selected_photo, PhotoCatalog.find(state.selectedId()).label));
    }

    @Override protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(SELECTED, state.selectedId());
        outState.putBoolean(HIDDEN, state.isHidden());
        super.onSaveInstanceState(outState);
    }

    @Override protected void onDestroy() {
        horizontalPhotos.setAdapter(null);
        gridPhotos.setAdapter(null);
        super.onDestroy();
    }
}
