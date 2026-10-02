package net.aymanx.examples.recycleview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

public final class ShapeTileView extends View {
    private final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Path path = new Path();
    private final RectF bounds = new RectF();
    private PhotoCatalog.Photo photo;

    public ShapeTileView(Context context, AttributeSet attributes) { super(context, attributes); }

    public void setPhoto(PhotoCatalog.Photo value) {
        photo = value;
        invalidate();
    }

    @Override protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (photo == null) return;
        float centerX = getWidth() / 2f;
        float centerY = getHeight() / 2f;
        float radius = Math.min(getWidth(), getHeight()) * 0.35f;
        paint.setColor(photo.color);
        if (photo.shape == PhotoCatalog.Shape.CIRCLE) {
            canvas.drawCircle(centerX, centerY, radius, paint);
        } else if (photo.shape == PhotoCatalog.Shape.RECTANGLE) {
            bounds.set(centerX - radius, centerY - radius * 0.7f, centerX + radius, centerY + radius * 0.7f);
            canvas.drawRoundRect(bounds, radius * 0.16f, radius * 0.16f, paint);
        } else {
            path.reset();
            path.moveTo(centerX, centerY - radius);
            path.lineTo(centerX + radius, photo.shape == PhotoCatalog.Shape.DIAMOND ? centerY : centerY + radius);
            if (photo.shape == PhotoCatalog.Shape.DIAMOND) path.lineTo(centerX, centerY + radius);
            path.lineTo(centerX - radius, photo.shape == PhotoCatalog.Shape.DIAMOND ? centerY : centerY + radius);
            path.close();
            canvas.drawPath(path, paint);
        }
    }
}
