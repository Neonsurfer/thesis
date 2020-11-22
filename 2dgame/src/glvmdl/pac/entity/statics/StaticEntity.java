package glvmdl.pac.entity.statics;

import glvmdl.pac.Handler;
import glvmdl.pac.entity.Entity;

public abstract class StaticEntity extends Entity{

    public StaticEntity(Handler handler, float x, float y, int width, int height){
        super(handler, x, y, width, height);
    }
}