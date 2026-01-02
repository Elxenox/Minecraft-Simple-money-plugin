package fr.xenox.simpleEconomy.commands;

import fr.xenox.simpleEconomy.SimpleEconomy;
import fr.xenox.simpleEconomy.managers.EconomyManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class EcoCommand implements CommandExecutor {

    // Manager
    private final EconomyManager economyManager;

    public  EcoCommand() {
        economyManager = new EconomyManager();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        // Droite -> Plugin.yml
        if (!sender.hasPermission("simpleEconomy.admin")) {
            sender.sendMessage("§cVous n'avez pas la permission !");
            return true;
        }

        if (args.length < 3) {
            sender.sendMessage("§e§l--- Commandes Admin Economy ----");
            sender.sendMessage("§7/eco give <joueur> <montant>");
            sender.sendMessage("§7/eco take <joueur> <montant>");
            sender.sendMessage("§7/eco set <joueur> <montant>");
            // TODO --> Transaction history pour rapidement voir les trades étranges
            return true;
        }

        String action = args[0].toLowerCase();
        Player target =  Bukkit.getPlayer(args[1]);

        if (target == null) {
            sender.sendMessage("§cJoueur introuvable");
            return true;
        }

        double amount;
        try {
            amount = Double.parseDouble(args[2]);
        } catch (NumberFormatException e) {
            sender.sendMessage("§cErreur dans le type de données");
            return true;
        }

        switch (action) {
            case "give":
                economyManager.addMoney(target, amount);
                sender.sendMessage("§aVous avez donné §e" + amount + "$ à " + target.getName());
                if (target.isOnline()) {
                    target.sendMessage("§a Vous avez reçu " + amount + "$ de la part des admins, chanceux.");
                }
                break;

            case "take":
                if (economyManager.removeMoney(target, amount)) {
                    sender.sendMessage("§aVous avez retiré §e" + amount + " à " + target.getName());
                    if (target.isOnline()) {
                        target.sendMessage("§cLes admins vous ont retiré " + amount + "$");
                    }
                } else {
                    sender.sendMessage("§cLe joueur n'a pas assez d'argent (Solde " + amount + "$)");
                }
                break;

            case "set":
                economyManager.setBalance(target, amount);
                sender.sendMessage("§aSolde de §6" + target.getName() + " est de " + economyManager.getBalance(target) + "$");
                if (target.isOnline()) {
                    target.sendMessage("§eVotre solde a été modifié par les admins, nouveau solde : " + economyManager.getBalance(target) + "$");
                }
                break;

            default:
                sender.sendMessage("§cAction invalide ! Utilisez give, take ou send.");
                break;
        }

        /// Afficher le nouveau solde du joueur
        sender.sendMessage("§7Nouveau solde: §e" + economyManager.getBalance(target) + "$");

        return true;
    }

}
