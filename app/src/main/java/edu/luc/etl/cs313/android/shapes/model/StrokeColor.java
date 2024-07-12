package edu.luc.etl.cs313.android.shapes.model;

public class StrokeColor implements Shape {
    final int color;
    final Shape shape;


    public StrokeColor(final int color, final Shape shape) {
        this.color = color;
        this.shape = shape;
    }

    public int getColor()
    {
        return color;
    }

    public Shape getShape()
    {
        return shape;
    }

    @Override
    public <Result> Result accept(Visitor<Result> v)
    {
        return v.onStrokeColor(this);
    }
}