package net.aymanx.examples.recycleview;

import java.util.Collections;
import java.util.List;

/** Selection is shared by the row and grid and survives Activity recreation. */
public final class GalleryState {
    private int selectedId;
    private boolean hidden;

    public GalleryState(int restoredId, boolean restoredHidden) {
        selectedId = PhotoCatalog.find(restoredId) == null ? PhotoCatalog.all().get(0).id : restoredId;
        hidden = restoredHidden;
    }

    public int selectedId() { return selectedId; }
    public boolean isHidden() { return hidden; }
    public List<PhotoCatalog.Photo> visiblePhotos() {
        return hidden ? Collections.<PhotoCatalog.Photo>emptyList() : PhotoCatalog.all();
    }
    public void toggleVisibility() { hidden = !hidden; }
    public void select(int id) {
        if (hidden || PhotoCatalog.find(id) == null) throw new IllegalArgumentException("Photo is not visible");
        selectedId = id;
    }
}
