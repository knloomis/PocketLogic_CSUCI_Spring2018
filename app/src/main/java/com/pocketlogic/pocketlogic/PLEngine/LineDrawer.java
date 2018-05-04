package com.pocketlogic.pocketlogic.PLEngine;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.widget.ImageView;
import com.pocketlogic.pocketlogic.R;

/*======================================   USER MANUAL    ==========================================
Usage: draw one or more lines on screen with different colors.

    Declaration
    LineDrawer lineDrawer = new LineDrawer((ImageView) tileView)

    Available Functions

    void newPaint()                                            <- Delete all lines, reset everything.
    void drawLine(int srcX, int srcY, int destX, int destY, int color)   <- Add new line to drawpad.
    void renderLines()                                                   <- Apply drawpad to screen.

*/

public class LineDrawer {

    //===============================       PRIVATE MEMBERS      ===================================

    final int SCREEN_HEIGHT = 2712;
    final int SCREEN_WIDTH = 1485;

    private ImageView imageView;                              //The actual draw pad (a image view)
    private Paint paint;                                              //Brush settings
    private Bitmap.Config conf;                                       //Bitmap settings
    private Bitmap bmp;                                               //Bitmap we will draw
    private Canvas canvas;                                            //Canvas of bitmap

    //=====================================    CONSTRUCTOR    ======================================
    public LineDrawer(ImageView imageView){
        this.imageView = imageView;
        imageView.setAdjustViewBounds(true);
        newPaint();
    }

    //===================================      METHODS      ========================================
    public void newPaint(){
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.BLACK);
        conf = Bitmap.Config.ARGB_8888;
        bmp = Bitmap.createBitmap(SCREEN_WIDTH, SCREEN_HEIGHT, conf);
        canvas = new Canvas(bmp);
        paint.setStrokeWidth(10);
    }

    public void drawLine(int srcX, int srcY, int destX, int destY, int color) {
        paint.setColor(color);

        //Android Stretching BUG offset
        srcX = (int)((double)srcX * ((double)SCREEN_WIDTH / 1440.0));
        destX = (int)((double)destX * ((double)SCREEN_WIDTH / 1440.0));

        canvas.drawLine(srcX,srcY,destX,destY,paint);
    }

    public void renderLines(){
        imageView.setImageBitmap(bmp);
    }

}
