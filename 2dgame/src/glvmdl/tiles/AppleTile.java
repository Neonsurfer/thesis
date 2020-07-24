package glvmdl.tiles;

import glvmdl.pac.gfx.Assets;
/**
 *
 * @author mvass
 */
public class AppleTile extends Tile{
     
    public AppleTile(int id) {
        super(Assets.apple, id);
    }
    
    @Override
    public boolean isSolid(){
        return false;
    }
    
}
