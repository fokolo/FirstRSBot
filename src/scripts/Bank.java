package scripts;

import org.powerbot.script.rt4.ClientContext;

public class Bank extends Task<ClientContext>{
    public Bank(ClientContext ctx) {
        super(ctx);
    }

    @Override
    public boolean should_activate() {
        return ctx.inventory.select().count() == 28 && !ctx.players.local().inMotion();
    }

    @Override
    public void execute() {
        if (!ctx.bank.inViewport())
        {
            ctx.movement.step(ctx.bank.nearest());
        }
        else
        {
            ctx.bank.open();
            if (ctx.bank.open())
            {
                ctx.bank.depositInventory();
                ctx.bank.close();
            }
        }
    }
}
