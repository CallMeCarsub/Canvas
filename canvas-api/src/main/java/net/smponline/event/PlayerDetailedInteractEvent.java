package net.smponline.event;

import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import java.util.UUID;
import java.util.logging.Level;

/**
 * <p>This event is fired as an "Outcome" after a PlayerInteractEvent. This event can be fired multiple times per
 * individual interaction, though most cases it will only fire once. Each event firing corresponds to an attempt
 * to initiate an interaction in a discrete way.</p>
 * <p>To fine-tune interactions controlling certain Items interacting with Blocks, these events can be used.
 * PlayerDetailedInteractEvent will be fired REGARDLESS of if the given item can affect a given block, or even
 * if a given interaction truly affects a block.</p>
 * <p>Cancelling all non-nothing interactions will</p>
 */
public class PlayerDetailedInteractEvent extends PlayerInteractEvent {
    public enum InteractionType {
        /**
         * Spectator opens a menu via a block, like a Chest
         */
        SPECTATOR_OPEN_MENU(true, false),
        /**
         * An attempt to use an Item on a Block
         */
        ATTEMPT_USE_ITEM_ON_BLOCK(true, true),
        /**
         * Using a block without the Item in hand having relevance
         */
        ATTEMPT_USE_HAND_ON_BLOCK(true, false),
        /**
         * Item interaction does nothing
         */
        NOTHING(false, false);
        private final boolean mayAffectBlock;
        private final boolean mayAffectItem;
        InteractionType(boolean mayAffectBlock, boolean mayAffectItem){
            this.mayAffectBlock = mayAffectBlock;
            this.mayAffectItem = mayAffectItem;
        }

        public boolean mayAffectBlock(){
            return this.mayAffectBlock;
        }

        public boolean mayAffectItem() {
            return this.mayAffectItem;
        }
    }

    private final InteractionType interactionType;
    private final UUID interactionId;
    private boolean cancelled = false;
    public PlayerDetailedInteractEvent(final @NotNull Player player, final @NotNull Action action, final @Nullable ItemStack item, final @Nullable Block clickedBlock, final @NotNull BlockFace clickedFace, final @Nullable EquipmentSlot hand, final @Nullable Vector clickedPosition, final InteractionType interactionType, final UUID interactionId) {
        super(player, action, item, clickedBlock, clickedFace, hand, clickedPosition);
        this.interactionType = interactionType;
        this.interactionId = interactionId;
    }

    public InteractionType getInteractionType() {
        return this.interactionType;
    }

    /**
     * Gets the Interaction ID representing the singular Interaction that initiated
     * every following DetailedInteract event
     * @return the UUID of the Interaction
     */
    public UUID getInteractionId() {
        return this.interactionId;
    }

    /**
     * Cancels the event.
     * Does nothing if you try to cancel a NOTHING as you cannot prevent NOTHING from happening.
     *
     * @param cancel {@code true} if you wish to cancel this event
     */
    @Override
    public void setCancelled(final boolean cancel) {
        if(cancel && this.interactionType == InteractionType.NOTHING){
            Bukkit.getLogger().log(Level.WARNING, "Plugin made illegal attempt to cancel PlayerDetailedInteractEvent", new UnsupportedOperationException("Tried to cancel when itneractionType = NOTHING"));
            return;
        }
        this.cancelled = true;
    }

    /**
     * Check if the event is cancelled
     * @return is the event is cancelled
     */
    @Override
    public boolean isCancelled() {
        return this.cancelled;
    }

    /**
     * Does nothing as DetailedInteract is for interfacing more directly with NMS actions
     * @param useInteractedBlock the action to take with the interacted block
     */
    @Override
    public void setUseInteractedBlock(@NotNull final Result useInteractedBlock) {

    }

    /**
     * Does nothing as DetailedInteract is for interfacing more directly with NMS actions
     * @param useItemInHand the action to take with the item in hand
     */
    @Override
    public void setUseItemInHand(@NotNull final Result useItemInHand) {

    }
}
