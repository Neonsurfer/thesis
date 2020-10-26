package glvmdl.pac.entity;

import glvmdl.pac.Handler;
import glvmdl.pac.entity.creatures.Player;
import glvmdl.pac.entity.creatures.Angry;
import glvmdl.pac.entity.creatures.Helpful;
import glvmdl.pac.entity.creatures.Problem;
import glvmdl.pac.entity.creatures.Smith;
import glvmdl.pac.entity.creatures.FriendBear;
import java.awt.Graphics;
import java.util.ArrayList;

public class EntityManager {

    private Handler handler;
    private Player player;
    private Angry angry;
    private Helpful helpful;
    private Problem problem;
    private Smith smith;
    private FriendBear friendlyBear;
    private static ArrayList<Entity> entities= new ArrayList<Entity>();;
    
    public EntityManager(Handler handler, Player player, Angry angry, Helpful helpful, Problem problem, Smith smith, FriendBear friendBear){
        this.handler = handler;
        
        this.player = player;
        this.angry = angry;
        this.helpful = helpful;
        this.problem = problem;
        this.smith = smith;
        this.friendlyBear = friendBear;
        addEntity(player);
        addEntity(angry);
        addEntity(helpful);
        addEntity(problem);
        addEntity(smith);
        addEntity(friendBear);
    }

    public Angry getAngry() {
        return angry;
    }

    public void setAngry(Angry angry) {
        this.angry = angry;
    }

    public Helpful getHelpful() {
        return helpful;
    }

    public void setHelpful(Helpful helpful) {
        this.helpful = helpful;
    }

    public Problem getProblem() {
        return problem;
    }

    public void setProblem(Problem problem) {
        this.problem = problem;
    }

    public Smith getSmith() {
        return smith;
    }

    public void setSmith(Smith smith) {
        this.smith = smith;
    }
    
    public void tick(){
        for(int i=0;i<entities.size();i++){
            Entity e = entities.get(i);
            e.tick();
        }
    }
    
    public void render(Graphics g){
        for(Entity e : entities){
            e.render(g);
        }
    }
    
    public static void addEntity(Entity e){
        entities.add(e);
    }
    //GETTERS SETTERS

    public Handler getHandler() {
        return handler;
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public ArrayList<Entity> getEntities() {
        return entities;
    }

    public void setEntities(ArrayList<Entity> entities) {
        this.entities = entities;
    }
    
    public void spawnFriendlyBear(){
        this.friendlyBear = new FriendBear(this.handler,51,51);
        entities.add(friendlyBear);
    }
    
    public void deleteFriendlyBear(){
        for(int i=0;i<entities.size();i++){
            if(entities.get(i).getX() == this.handler.getWorld().getEntityManager().friendlyBear.x &&entities.get(i).getY() == this.handler.getWorld().getEntityManager().friendlyBear.y){
                entities.remove(i);
            }
        }
    }
    
    public void killAll(){
        entities.clear();
    }
    
    
}
