package net.aymanx.examples.recycleview;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/** Locally drawn sample imagery; no remote photos or third-party assets. */
public final class PhotoCatalog {
    public enum Shape { CIRCLE, TRIANGLE, RECTANGLE, DIAMOND }

    public static final class Photo {
        public final int id;
        public final String label;
        public final int color;
        public final Shape shape;

        private Photo(int id, String label, int color, Shape shape) {
            this.id = id;
            this.label = label;
            this.color = color;
            this.shape = shape;
        }
    }

    private static final List<Photo> PHOTOS = Collections.unmodifiableList(Arrays.asList(
            new Photo(101, "دائرة المرجان", 0xFFCE6C51, Shape.CIRCLE),
            new Photo(102, "مثلث الغابة", 0xFF297F6E, Shape.TRIANGLE),
            new Photo(103, "مستطيل البحيرة", 0xFF347A9D, Shape.RECTANGLE),
            new Photo(104, "معيّن الشمس", 0xFFE3AF3F, Shape.DIAMOND),
            new Photo(105, "دائرة البنفسج", 0xFF8D73B2, Shape.CIRCLE),
            new Photo(106, "مثلث الفيروز", 0xFF2A9EAA, Shape.TRIANGLE),
            new Photo(107, "مستطيل الظل", 0xFF516B83, Shape.RECTANGLE),
            new Photo(108, "معيّن الورد", 0xFFB95A76, Shape.DIAMOND)
    ));

    private PhotoCatalog() { }
    public static List<Photo> all() { return PHOTOS; }
    public static Photo find(int id) {
        for (Photo photo : PHOTOS) if (photo.id == id) return photo;
        return null;
    }
}
