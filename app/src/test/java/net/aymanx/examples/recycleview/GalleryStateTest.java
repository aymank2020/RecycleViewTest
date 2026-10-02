package net.aymanx.examples.recycleview;

import org.junit.Test;
import java.util.HashSet;
import java.util.Set;
import static org.junit.Assert.*;

public class GalleryStateTest {
    @Test public void catalogHasDistinctLocalImageryAndStableIds() {
        Set<Integer> ids = new HashSet<>();
        Set<PhotoCatalog.Shape> shapes = new HashSet<>();
        for (PhotoCatalog.Photo photo : PhotoCatalog.all()) {
            assertTrue(ids.add(photo.id));
            assertFalse(photo.label.isEmpty());
            assertEquals(0xFF, photo.color >>> 24);
            shapes.add(photo.shape);
        }
        assertEquals(8, ids.size());
        assertEquals(4, shapes.size());
    }

    @Test public void selectionRestoresForBothViews() {
        GalleryState state = new GalleryState(-1, false);
        state.select(108);
        GalleryState restored = new GalleryState(state.selectedId(), state.isHidden());
        assertEquals(108, restored.selectedId());
        assertEquals(8, restored.visiblePhotos().size());
    }

    @Test public void hidingAndRestoringKeepsSelectionWithoutDuplicatingData() {
        GalleryState state = new GalleryState(105, false);
        state.toggleVisibility();
        assertTrue(state.visiblePhotos().isEmpty());
        GalleryState restored = new GalleryState(state.selectedId(), state.isHidden());
        assertTrue(restored.isHidden());
        restored.toggleVisibility();
        assertEquals(105, restored.selectedId());
        assertEquals(8, restored.visiblePhotos().size());
        restored.toggleVisibility();
        restored.toggleVisibility();
        assertEquals(8, restored.visiblePhotos().size());
    }

    @Test public void obsoleteRestoredIdFallsBackToAnExistingItem() {
        GalleryState state = new GalleryState(999, false);
        assertEquals(PhotoCatalog.all().get(0).id, state.selectedId());
    }

    @Test(expected = IllegalArgumentException.class) public void invalidSelectionIsRejected() {
        new GalleryState(101, false).select(999);
    }

    @Test(expected = IllegalArgumentException.class) public void hiddenItemsCannotBeSelected() {
        new GalleryState(101, true).select(102);
    }

    @Test(expected = UnsupportedOperationException.class) public void catalogCannotBeMutatedByAnAdapter() {
        PhotoCatalog.all().clear();
    }
}
