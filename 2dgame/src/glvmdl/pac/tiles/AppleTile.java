package glvmdl.pac.tiles;

import glvmdl.pac.gfx.Assets;

public class AppleTile extends Tile{
     
    public AppleTile(int id) {
        super(Assets.apple, id);
    }
    
    @Override
    public boolean isSolid(){
        return false;
    }
}