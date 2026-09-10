package net.smponline.event;

import org.bukkit.ExplosionResult;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Entity;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import java.util.List;

public class PreExplodeEvent extends Event implements Cancellable {

    private static final HandlerList HANDLER_LIST = new HandlerList();

    private final Entity explodedEntity;
    private final BlockState explodedBlockState;
    private final Block explodedBlock;
    private final List<Block> affectedBlocks;
    private final List<Entity> affectedEntities;
    private final ExplosionResult result;

    private float yield;
    private boolean causeFire;
    private boolean cancelled;

    public PreExplodeEvent(final Entity entity, final Block block, final BlockState blockState, final List<Block> blocks, final List<Entity> entities, final float yield, final boolean causeFire, final ExplosionResult result) {
        this.explodedEntity = entity;
        this.explodedBlock = block;
        this.explodedBlockState = blockState;
        this.affectedBlocks = blocks;
        this.affectedEntities = entities;
        this.yield = yield;
        this.causeFire = causeFire;
        this.result = result;
    }

    public Entity getExplodedEntity() {
        return explodedEntity;
    }

    public BlockState getExplodedBlockState() {
        return explodedBlockState;
    }

    public Block getExplodedBlock() {
        return explodedBlock;
    }

    public List<Block> getAffectedBlocks() {
        return affectedBlocks;
    }

    public List<Entity> getAffectedEntities() {
        return affectedEntities;
    }

    public ExplosionResult getResult() {
        return result;
    }

    public float getYield() {
        return yield;
    }

    public void setYield(final float yield) {
        this.yield = yield;
    }

    @Override
    public boolean isCancelled() {
        return this.cancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.cancelled = cancel;
    }

    public HandlerList getHandlers() {
        return HANDLER_LIST;
    }

    public static HandlerList getHandlerList() {
        return HANDLER_LIST;
    }

    public boolean isCauseFire() {
        return causeFire;
    }

    public void setCauseFire(final boolean causeFire) {
        this.causeFire = causeFire;
    }
}
