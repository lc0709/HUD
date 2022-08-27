package com.reve.thirst.thirstTasks;

import com.reve.thirst.Main;
import com.reve.thirst.thirst.Thirst;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import java.util.UUID;

public class ThirstTask extends BukkitRunnable {
    Main plugin; UUID id;
    public ThirstTask(Main plugin, UUID id){
        this.plugin = plugin;
        this.id = id;
    }
    @Override
    public void run() {

        Player player = plugin.getServer().getPlayer(id);
        float amp = 1f;
        JumpTask jump = new JumpTask(plugin, id);
        RunTask run = new RunTask(plugin, id);
        if (player != null) {
            ThirstDamageTask task = new ThirstDamageTask(plugin, id);
            //player.sendMessage("Run: " + run.isRunning(id));
            //player.sendMessage("Jump: " + jump.isJumping(id));
            if (Thirst.getThirst(id) > 0) {
                //task.cancel();
                if (run.isRunning(id)) {
                    //player.sendMessage("Running!!");
                    amp += 0.5f;
                }
                if (jump.isJumping(id)) {
                    //player.sendMessage("Jumping!!");
                    amp += 0.5f;
                }
                Thirst.setThirst(id, Thirst.getThirst(id) - 0.05f * amp);
            } else {
                Thirst.setThirst(id, 0);
                if (!task.isCooldown(id)) {
                    task.setIsCooldown(id, true);
                    task.runTaskLater(plugin, 40L);
                }

            }
        }
    }

}