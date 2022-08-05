package com.example.myapplication.canvasdemo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.view.MotionEvent;
import android.view.View;

public class PaintViewer extends View {

    private float xAxis;
    private float yAxis;
    private float xAxisStart;
    private float yAxisStart;
    private float xAxisEnd;
    private float yAxisEnd;
    private Paint mPaint;
    private Path mPath;
    private Canvas mCanvas;
    private Bitmap mBitmap;
    public static final int SMOOTH_LINE = 1;
    public static final int RECTANGLE_SHAPE = 2;
    public static final int SQUARE_SHAPE = 3;
    public static final int CIRCLE_SHAPE = 4;
    public static final int TRIANGLE_SHAPE = 5;
    public boolean isDrawing = false;
    public int currentShape;
    public int triangleTouchCount = 0;
    public float xTriangle = 0;
    public float yTriangle = 0;

    public PaintViewer(Context context) {
        super(context);
        mPath = new Path();
        mPaint = new Paint(Paint.DITHER_FLAG);
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setColor(Color.GRAY);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(8);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitmap);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(mBitmap, 0, 0, mPaint);
        if (isDrawing) {
            switch (currentShape) {
                case SMOOTH_LINE:
                    onSignatureDraw(canvas);
                    break;
                case RECTANGLE_SHAPE:
                    onRectangleDraw(canvas);
                    break;
                case SQUARE_SHAPE:
                    onSquareDraw(canvas);
                    break;
                case CIRCLE_SHAPE:
                    onCircleDraw(canvas);
                    break;
                case TRIANGLE_SHAPE:
                    onTriangleDraw(canvas);
                    break;
            }
        }
    }

    public Bitmap getBitmapImage() {
        this.setDrawingCacheEnabled(true);
        this.buildDrawingCache();
        Bitmap bmp = Bitmap.createBitmap(this.getDrawingCache());
        this.setDrawingCacheEnabled(false);
        return bmp;
    }

    public void undo() {
        mCanvas.drawColor(0, PorterDuff.Mode.CLEAR);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        xAxis = event.getX();
        yAxis = event.getY();

        switch (currentShape) {
            case SMOOTH_LINE:
                onSignatureTouchEvent(event);
                break;
            case RECTANGLE_SHAPE:
                onRectangleTouchEvent(event);
                break;
            case SQUARE_SHAPE:
                onSquareTouchEvent(event);
                break;
            case CIRCLE_SHAPE:
                onCircleTouchEvent(event);
                break;
            case TRIANGLE_SHAPE:
                onTriangleTouchEvent(event);
                break;
        }
        return true;
    }

    private void onSignatureDraw(Canvas canvas) {
        canvas.drawLine(xAxis, yAxis, xAxis, yAxis, mPaint);
    }

    private void onSignatureTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            mPath.moveTo(x, y);
        } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
            mPath.lineTo(x, y);
            mCanvas.drawPath(mPath, mPaint);
            invalidate();
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            isDrawing = false;
            mPath.reset();
            invalidate();
        }
    }

    private void onRectangleDraw(Canvas canvas) {
        onRectangle(canvas, mPaint);
    }

    private void onRectangle(Canvas canvas, Paint paint) {
        float right = Math.max(xAxisStart, xAxis);
        float left = Math.min(xAxisStart, xAxis);
        float bottom = Math.max(yAxisStart, yAxis);
        float top = Math.min(yAxisStart, yAxis);
        canvas.drawRect(left, top, right, bottom, paint);
    }

    private void onRectangleTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                isDrawing = true;
                xAxisStart = xAxis;
                yAxisStart = yAxis;
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                isDrawing = false;
                onRectangle(mCanvas, mPaint);
                invalidate();
                break;
        }
    }

    private void onSquareDraw(Canvas canvas) {
        onRectangleDraw(canvas);
    }

    private void onSquareTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                isDrawing = true;
                xAxisStart = xAxis;
                yAxisStart = yAxis;
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                isDrawing = false;
                onManageSquare(xAxis, yAxis);
                onRectangle(mCanvas, mPaint);
                invalidate();
                break;
        }
    }

    private void onManageSquare(float x, float y) {
        float dx = Math.abs(xAxisStart - x);
        float dy = Math.abs(yAxisStart - y);
        float max = Math.max(dx, dy);
        xAxis = xAxisStart - x < 0 ? xAxisStart + max : xAxisStart - max;
        yAxis = yAxisStart - y < 0 ? yAxisStart + max : yAxisStart - max;
    }

    private void onCircleDraw(Canvas canvas) {
        canvas.drawOval(xAxisStart, yAxisStart, xAxisEnd, yAxisEnd, mPaint);
    }

    private void onCircleTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                isDrawing = true;
                xAxisStart = xAxis;
                yAxisStart = yAxis;
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                xAxisEnd = event.getX();
                yAxisEnd = event.getY();
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                mCanvas.drawOval(xAxisStart, yAxisStart, xAxisEnd, yAxisEnd, mPaint);
                invalidate();
                break;
        }
    }

    private void onTriangleDraw(Canvas canvas) {
        if (triangleTouchCount < 3) {
            canvas.drawLine(xAxisStart, yAxisStart, xAxis, yAxis, mPaint);
        } else if (triangleTouchCount == 3) {
            canvas.drawLine(xAxis, yAxis, xAxisStart, yAxisStart, mPaint);
            canvas.drawLine(xAxis, yAxis, xTriangle, yTriangle, mPaint);
        }
    }

    private void onTriangleTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                triangleTouchCount++;
                if (triangleTouchCount == 1) {
                    isDrawing = true;
                    xAxisStart = xAxis;
                    yAxisStart = yAxis;
                } else if (triangleTouchCount == 3) {
                    isDrawing = true;
                }
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                triangleTouchCount++;
                isDrawing = false;
                if (triangleTouchCount < 3) {
                    xTriangle = xAxis;
                    yTriangle = yAxis;
                    mCanvas.drawLine(xAxisStart, yAxisStart, xAxis, yAxis, mPaint);
                } else if (triangleTouchCount >= 3) {
                    mCanvas.drawLine(xAxis, yAxis, xAxisStart, yAxisStart, mPaint);
                    mCanvas.drawLine(xAxis, yAxis, xTriangle, yTriangle, mPaint);
                    triangleTouchCount = 0;
                }
                invalidate();
                break;
        }
    }

}
