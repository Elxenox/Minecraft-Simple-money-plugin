package fr.xenox.simpleEconomy.commands;

import fr.xenox.simpleEconomy.SimpleEconomy;
import fr.xenox.simpleEconomy.managers.EconomyManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BalanceCommand implements CommandExecutor {

    // Manager
    private final EconomyManager economyManager;

    public BalanceCommand() {
        this.economyManager = SimpleEconomy.getInstance().getEconomyManager();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        /*
         * - /balance
         * Pour récupérer la solde d'un joueur
         */
        if (args.length == 0) {
            if (!(sender instanceof Player p)) {
                sender.sendMessage("§cVous devez être un joueur !");
                return true;
            }

            double balance = economyManager.getBalance(p);
            p.sendMessage("§e§lVotre solde : §a" + balance + "$");
            return true;
        }

        /*
         * - /balance <joueur>
         * Permet à un joueur de voir le solde d'un autre (@TODO - Rôles based)
         */
        Player target = Bukkit.getPlayer(args[0]);

        if (target == null) {
            sender.sendMessage("§cJoueur introuvable..");
            return true;
        }

        double balance = economyManager.getBalance(target);
        sender.sendMessage("§cSolde du joueur " + target.getName() + " -> " + balance + "$");

        return true;
    }
}
