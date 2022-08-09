package com.example.myapplication.canvasdemo;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;

import com.example.myapplication.R;
import com.skydoves.colorpickerview.ColorPickerDialog;
import com.skydoves.colorpickerview.listeners.ColorEnvelopeListener;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class PaintHomeActivity extends AppCompatActivity {

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

        AppCompatButton btnClearDraw = findViewById(R.id.clear_draw);
        btnClearDraw.setOnClickListener(v -> {
            paintViewer.undo();
            Toast.makeText(this, "cleared", Toast.LENGTH_SHORT).show();
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.paint_element_, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.option_pencil) {
            paintViewer.currentShape = PaintViewer.SMOOTH_LINE;
            Toast.makeText(this, "brush", Toast.LENGTH_SHORT).show();
        } else if (itemId == R.id.option_rectangle) {
            paintViewer.currentShape = PaintViewer.RECTANGLE_SHAPE;
            Toast.makeText(this, "rectangle", Toast.LENGTH_SHORT).show();
        } else if (itemId == R.id.option_square) {
            paintViewer.currentShape = PaintViewer.SQUARE_SHAPE;
            Toast.makeText(this, "Square", Toast.LENGTH_SHORT).show();
        } else if (itemId == R.id.option_oval) {
            paintViewer.currentShape = PaintViewer.OVAL_SHAPE;
            Toast.makeText(this, "oval", Toast.LENGTH_SHORT).show();
        } else if (itemId == R.id.option_circle) {
            paintViewer.currentShape = PaintViewer.CIRCLE_SHAPE;
            Toast.makeText(this, "Circle", Toast.LENGTH_SHORT).show();
        } else if (itemId == R.id.option_triangle) {
            paintViewer.currentShape = PaintViewer.TRIANGLE_SHAPE;
            Toast.makeText(this, "Triangle", Toast.LENGTH_SHORT).show();
        } else if (itemId == R.id.option_save_image) {
            downloadCanvasImage();
            Toast.makeText(this, "save image", Toast.LENGTH_SHORT).show();
        } else if (itemId == R.id.option_color_palette) {
            new ColorPickerDialog.Builder(this)
                    .setTitle("ColorPicker Dialog")
                    .setPreferenceName("MyColorPickerDialog")
                    .setPositiveButton("OK", (ColorEnvelopeListener) (envelope, fromUser) -> {
                        paintViewer.setColor(envelope.getColor());
                    })
                    .setNegativeButton("CANCEL", (dialogInterface, i) -> dialogInterface.dismiss())
                    .attachAlphaSlideBar(true)
                    .attachBrightnessSlideBar(true)
                    .setBottomSpace(12)
                    .show();
        }
        return super.onOptionsItemSelected(item);
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