package com.gmail.mphag;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Utils {

    private final static GlyphLayout layout = new GlyphLayout();

    public static void drawStringCentered(BitmapFont font, SpriteBatch b, String m, int x, int y)  {

        layout.setText(font, m);

        font.draw(b,m,x - layout.width/2,y + layout.height/2);
    }

    public static void drawStringFromLeftCorner(BitmapFont font, SpriteBatch b, String m, int x, int y) {

        layout.setText(font, m);

        font.draw(b,m,x,y + layout.height - font.getDescent() * font.getData().scaleY);

    }

    public static void drawStringFromCenterYAndBottomLeftX(BitmapFont font, SpriteBatch b, String m, int x, int y) {

        layout.setText(font, m);

        font.draw(b,m,x, y + layout.height/2);

    }

}
