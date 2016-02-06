package scripts;


import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.GroundItem;

public class PickupItem extends Task<ClientContext>{
    private int[] item_ids = null;

    public PickupItem(ClientContext ctx) {
        super(ctx);
    }
    public PickupItem(ClientContext ctx, int[] item_ids_filter) {
        super(ctx);
        item_ids = item_ids_filter;
    }

    @Override
    public boolean should_activate() {
        Boolean in_motion = ctx.players.local().inMotion();
        int inventory_count = ctx.inventory.select().count();
        // log.info(format("Player animation %d, Inventory count %d.", player_animation, inventory_count));
        return !in_motion && inventory_count < 28;
    }

    @Override
    public void execute() {
        ctx.groundItems.select();
        if (item_ids != null)
        {
            ctx.groundItems.id(item_ids);
        }
        GroundItem ground_item = ctx.groundItems.nearest().poll();
        if (ground_item.inViewport())
        {
            ground_item.interact("Take");
        }
        else {
            ctx.movement.step(ground_item);
        }

    }
}
