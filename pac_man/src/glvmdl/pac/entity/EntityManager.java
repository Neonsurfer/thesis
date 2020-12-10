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
    private static ArrayList<Entity> entities= new ArrayList<Entity>();
    
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
    
    public static void addEntity(Entity e){
        entities.add(e);
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
    
    public void killAll(){
        entities.clear();
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
    
    public FriendBear getFriendBear(){
        return this.friendlyBear;
    }
    
    public void setFriendBear(FriendBear friendBear){
        this.friendlyBear = friendBear;
    }

    public ArrayList<Entity> getEntities() {
        return entities;
    }

    public void setEntities(ArrayList<Entity> entities) {
        this.entities = entities;
    }
 
}
