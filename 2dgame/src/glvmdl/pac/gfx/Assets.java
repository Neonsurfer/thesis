package glvmdl.pac.gfx;

import java.awt.image.BufferedImage;

public class Assets {
    
    private static final int width = 50, height = 50;

    public static BufferedImage player, dirt, tree, apple, rock, angry, helpful, problem, smith, basket, friend;
    
    public static void init(){
        SpriteSheet sheet = new SpriteSheet(ImageLoader.loadImage("/textures/spritesheet.png"));
        
        player = sheet.crop(width, 0, width, height);
        dirt = sheet.crop(2*width, 0, width, height);
        tree = sheet.crop(width, height, width, height);
        apple = sheet.crop(0, 0, width, height);
        rock = sheet.crop(0, height, width, height);
        angry = sheet.crop(2*width, height, width, height);
        helpful = sheet.crop(0, 2*height, width, height);
        problem = sheet.crop(width, 2*height, width, height);
        smith = sheet.crop(2*width, 2*height, width, height);
        basket = sheet.crop(0, 3*height, width, height);
        friend = sheet.crop(width, 3*height,width, height);
    }
    
    public static void dispose(){
        player = null;
        dirt = null;
        tree = null;
        apple = null;
        rock = null;
        angry = null;
        helpful = null;
        problem = null;
        smith = null;
        basket = null;
        friend = null;
    }
}