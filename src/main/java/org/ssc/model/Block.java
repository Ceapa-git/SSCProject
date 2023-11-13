package org.ssc.model;

import org.ssc.gui.BlockPanel;

import java.util.ArrayList;

public class Block {
    protected Block previous;
    protected Block next;
    protected ArrayList<Block> connections;
    protected String blockName;
    protected BlockPanel blockPanel;

    public Block() {
        this.previous = null;
        this.next = null;
        this.connections = new ArrayList<>();
        this.blockName = "Start";
    }

    public Block(Block previous, Block next) {
        this.previous = previous;
        this.next = next;
        this.connections = new ArrayList<>();
        this.blockName = "empty";
    }

    public BlockPanel getBlockPanel() {
        return blockPanel;
    }

    public void setBlockPanel(BlockPanel blockPanel) {
        this.blockPanel = blockPanel;
    }

    public String getBlockName() {
        return this.blockName;
    }

    public void setBlockName(String blockName) {
        this.blockName = blockName;
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

    public boolean removeConnection(Block connection) {
        if (!this.connections.contains(connection)) return false;
        this.connections.set(this.connections.indexOf(connection), null);
        return true;
    }

    public boolean removeConnection(int id) {
        if (id < 0 || id > this.connections.size() || this.connections.get(id) == null) return false;
        this.connections.set(id, null);
        return true;
    }

    public int addConnection(Block connection) {
        this.connections.add(connection);
        return this.connections.indexOf(connection);
    }

    public boolean setConnection(Block connection) {
        return setConnection(connection, 0);
    }

    public boolean setConnection(Block connection, int id) {
        if (id < 0 || id > this.connections.size()) return false;
        this.connections.set(id, connection);
        this.connections.get(this.connections.indexOf(connection)).setPrevious(this);
        return true;
    }

    public int getNumberOfConnections() {
        return this.connections.size();
    }

    public Block getConnection(int index) {
        if (index >= this.connections.size() || index < 0)
            return null;
        return this.connections.get(index);
    }

    public Block[] getConnections() {
        return this.connections.toArray(new Block[connections.size()]);
    }

    public String getName() {
        return null;
    }

    public void setName(String name) {
    }
}
