package org.ssc.model;

import java.util.ArrayList;

public class Block {
    protected Block previous;
    protected Block next;
    protected ArrayList<Block> connections;
    protected String blockName;

    public Block() {
        this.previous = null;
        this.next = null;
        this.connections = new ArrayList<>();
        this.blockName = "empty";
    }

    public Block(Block previous, Block next) {
        this.previous = previous;
        this.next = next;
        this.connections = new ArrayList<>();
        this.blockName = "empty";
    }
    public String getBlockName(){
        return this.blockName;
    }

    public Block getPrevious() {
        return previous;
    }

    public void setPrevious(Block previous) {
        this.previous = previous;
    }

    public Block getNext() {
        return next;
    }

    public void setNext(Block next) {
        this.next = next;
    }
    public boolean removeConnection(Block connection){
        if(!this.connections.contains(connection)) return false;
        this.connections.remove(connection);
        return true;
    }
    public boolean removeConnection(int id){
        if(id<0 || id>this.connections.size() || this.connections.get(id) == null) return false;
        this.connections.remove(id);
        return true;
    }
    public int addConnection(Block connection){
        this.connections.add(connection);
        return this.connections.indexOf(connection);
    }
    public boolean setConnection(Block connection){
        return setConnection(connection,0);
    }
    public boolean setConnection(Block connection,int id){
        if(id<0 || id>this.connections.size()) return false;
        this.connections.set(id,connection);
        this.connections.get(this.connections.indexOf(connection)).setPrevious(this);
        return true;
    }
    public int getNumberOfConnections(){
        return this.connections.size();
    }
    public Block getConnection(int index){
        if(index>=this.connections.size() || index<0)
            return null;
        return this.connections.get(index);
    }
}
