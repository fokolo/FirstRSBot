package scripts;

import org.powerbot.script.PollingScript;
import org.powerbot.script.Script;
import org.powerbot.script.rt4.ClientContext;

import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;

@Script.Manifest(
        name = "First Bot!", properties = "author=fokolo; client=4;",
        description = "First bot by fokolo"
)

@SuppressWarnings("unused")
public class FirstBot extends PollingScript<ClientContext> {
    private List<Task> taskList = new ArrayList<Task>();

    @Override
    public void start() {
        log.info("Hello, RSBot!");

        taskList.add(new PickupItem(ctx, new int[]{592, 593, 17680, 17681}));
        taskList.add(new Bank(ctx));

        log.info("TaskList:" + taskList.toString());
    }

    @Override
    public void poll() {
        for (Task task : taskList) {
            Boolean in_motion = ctx.players.local().inMotion();
            int inventory_count = ctx.inventory.select().count();
            log.info(format("InMotion %b, Inventory count %d.", in_motion, inventory_count));

            if (task.should_activate()) {
                task.execute();
            }
        }
    }
}

