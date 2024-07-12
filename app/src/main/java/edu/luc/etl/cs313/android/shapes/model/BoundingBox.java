package edu.luc.etl.cs313.android.shapes.model;

public class BoundingBox implements Visitor<Location> {


    @Override
    public Location onCircle(final Circle c) {
        final int radius = c.getRadius();
        return new Location(-radius, -radius, new Rectangle(2 * radius, 2 * radius));
    }

    @Override
    public Location onFill(final Fill f) {
        return f.getShape().accept(this);
    }

    @Override
    public Location onGroup(final Group g) {

        var shapesList = g.getShapes();

        int currXMin = Integer.MAX_VALUE;
        int currYMin = Integer.MAX_VALUE;
        int currXMax = 0;
        int currYMax = 0;

        for (Shape eachItem : shapesList) {
            final Location loc = eachItem.accept(this);
            int x = loc.getX();
            int y = loc.getY();

            int widthPlusX = x;
            int heightPlusY = y;

            if (loc.getShape() instanceof Rectangle) {
                heightPlusY += ((Rectangle) loc.getShape()).getHeight();
            }

            if (loc.getShape() instanceof Rectangle) {
                widthPlusX += ((Rectangle) loc.getShape()).getWidth();
            }

            if (currYMax <= heightPlusY) {
                currYMax = heightPlusY;
            }

            if (currXMax <= widthPlusX) {
                currXMax = widthPlusX;
            }

            if (currXMin >= x) {
                currXMin = x;
            }
            if (currYMin >= y) {
                currYMin = y;
            }

        }
        return new Location(currXMin, currYMin, new Rectangle((currXMax - currXMin),
                (currYMax - currYMin)));
    }

    @Override
    public Location onLocation(final Location l) {
        final int x = l.getX() + l.getShape().accept(this).getX();
        final int y = l.getY() + l.getShape().accept(this).getY();
        return new Location(x,y,l.getShape().accept(this).getShape());
    }

    @Override
    public Location onRectangle(final Rectangle r) {
        return new Location(0,0, new Rectangle(r.getWidth(), r.getHeight()));
    }

    @Override
    public Location onStrokeColor(final StrokeColor c) {
        return c.getShape().accept(this);
    }

    @Override
    public Location onOutline(final Outline o) {
        return o.getShape().accept(this);
    }

    @Override
    public Location onPolygon(final Polygon s) {

        int x, y, x1, y1, width, height;
        int lowX, lowY, highX, highY;

        x1 = s.getPoints().get(0).getX();
        y1 = s.getPoints().get(0).getY();

        lowX = x1; highX=x1; lowY = y1; highY=y1;

        for (Point value : s.getPoints()) {
            x = value.getX();
            y = value.getY();

            if (x > highX)  highX = x;
            if (y > highY) highY = y;

        }
        width = highX - lowX;
        height = highY - lowY;
        return new Location(x1, y1, new Rectangle(width, height));
    }
}