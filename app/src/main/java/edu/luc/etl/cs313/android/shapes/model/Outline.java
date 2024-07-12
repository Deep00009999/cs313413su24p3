package edu.luc.etl.cs313.android.shapes.model;


public class Outline implements Shape {

    protected final Shape shape;

    public Outline(final Shape shape)
    {
        this.shape = shape;
    }

    public Shape getShape() {
        return shape;
    }

    @Override
    public <Result> Result accept(final Visitor<Result> v) {
        return v.onOutline(this);
    }
}