package com.example.myapplication.canvasdemo;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.myapplication.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class PaintHomeActivity extends AppCompatActivity implements View.OnClickListener {

    private PaintViewer paintViewer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paint_home);

        Toolbar toolbar = findViewById(R.id.canvas_toolbar);
        setSupportActionBar(toolbar);
        paintViewer = new PaintViewer(this);
        LinearLayout layout = findViewById(R.id.lll);
        layout.addView(paintViewer);

        ImageView ivBrush = findViewById(R.id.iv_pencil);
        ImageView ivEraser = findViewById(R.id.iv_eraser);
        ImageView ivSave = findViewById(R.id.iv_save_img);
        ImageView ivRectangle = findViewById(R.id.rectangle_shape);
        ImageView ivSquare = findViewById(R.id.square_shape);
        ImageView ivCircle = findViewById(R.id.oval_shape);
        ImageView ivTriangle = findViewById(R.id.triangle_shape);

        ivBrush.setOnClickListener(this);
        ivEraser.setOnClickListener(this);
        ivSave.setOnClickListener(this);
        ivRectangle.setOnClickListener(this);
        ivSquare.setOnClickListener(this);
        ivCircle.setOnClickListener(this);
        ivTriangle.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.iv_pencil) {
            paintViewer.currentShape = PaintViewer.SMOOTH_LINE;
            Toast.makeText(this, "brush", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.iv_eraser) {
            paintViewer.undo();
            Toast.makeText(this, "eraser", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.iv_save_img) {
            downloadCanvasImage();
        } else if (id == R.id.rectangle_shape) {
            paintViewer.currentShape = PaintViewer.RECTANGLE_SHAPE;
            Toast.makeText(this, "rectangle", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.square_shape) {
            paintViewer.currentShape = PaintViewer.SQUARE_SHAPE;
            Toast.makeText(this, "Square", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.oval_shape) {
            paintViewer.currentShape = PaintViewer.CIRCLE_SHAPE;
            Toast.makeText(this, "Circle", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.triangle_shape) {
            paintViewer.currentShape = PaintViewer.TRIANGLE_SHAPE;
            Toast.makeText(this, "Triangle", Toast.LENGTH_SHORT).show();
        }
    }

    private void downloadCanvasImage() {
        File folder = new File(Environment.getExternalStorageDirectory().toString());
        boolean success = false;
        if (!folder.exists()) {
            success = folder.mkdirs();
        }
        File file = new File(Environment.getExternalStorageDirectory().toString() + "/canvasimage.png");
        if (!file.exists()) {
            try {
                success = file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println(success + "file");
        System.out.println(file);
        FileOutputStream ostream = null;
        try {
            ostream = new FileOutputStream(file);
            Bitmap well = paintViewer.getBitmapImage();
            Bitmap save = Bitmap.createBitmap(320, 480, Bitmap.Config.ARGB_8888);
            Paint paint = new Paint();
            paint.setColor(Color.WHITE);
            Canvas now = new Canvas(save);
            now.drawRect(new Rect(0, 0, 320, 480), paint);
            now.drawBitmap(well, new Rect(0, 0, well.getWidth(), well.getHeight())
                    , new Rect(0, 0, 320, 480), null);
            if (save == null) {
                System.out.println("NULL bitmap save\n");
            }
            save.compress(Bitmap.CompressFormat.PNG, 100, ostream);
            Toast.makeText(this, "image save", Toast.LENGTH_SHORT).show();
        } catch (NullPointerException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Null error", Toast.LENGTH_SHORT).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "File error", Toast.LENGTH_SHORT).show();
        }
    }
}