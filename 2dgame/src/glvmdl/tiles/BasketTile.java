package glvmdl.tiles;

import glvmdl.pac.gfx.Assets;

public class BasketTile extends Tile{
    
    public BasketTile(int id) {
        super(Assets.basket, id);
    }
    
    @Override
    public boolean isSolid(){
        return false;
    }
}