package fr.xenox.simpleEconomy.commands;

import fr.xenox.simpleEconomy.SimpleEconomy;
import fr.xenox.simpleEconomy.managers.EconomyManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PayCommand implements CommandExecutor {


    // Manager
    private final EconomyManager economyManager;

    public PayCommand() {
        economyManager = new EconomyManager();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (!(sender instanceof Player p)) {
            sender.sendMessage("§cCommande réservée aux joueurs!");
            return true;
        }

        // Vérifier les arguments : /pay <joueur> <montant>
        if (args.length < 2) {
            p.sendMessage("§cUtilisation: /pay <joueur> <montant>");
            return true;
        }

        // Target
        Player  target = Bukkit.getPlayer(args[0]);

        if (target == null) {
            p.sendMessage("§cUtilisateur non trouvé!");
            return true;
        }

        if (!target.isOnline()) {
            p.sendMessage("§cUtilisateur doit être en ligne pour recevoir l'argent");
            return true;
        }

        if (target.equals(p)) {
            p.sendMessage("§cVous ne pouvez pas vous envoyer de l'argent à vous même..");
            return true;
        }

        // Montant ==
        double amount;
        try {
            amount = Double.parseDouble(args[1]);
        } catch (NumberFormatException e) {
            // String, etc..
            p.sendMessage("§cMontant invalide!");
            return true;
        }

        // Vérifier si le montant est positif
        if (amount <= 0) {
            p.sendMessage("§cLe montant ne peut pas être nul ou négatif.");
            return true;
        }

        // Sender a assez d'argent ?
        if (!(economyManager.getBalance(p) >= amount)) {
            p.sendMessage("§cVous n'avez pas assez d'argent!");
            p.sendMessage("§cVotre solde actuel est : " + economyManager.getBalance(p) + "$");
            return true;
        }

        // Effecturer le transfert si tout est bon
        if (economyManager.transferMoney(p, target, amount)) {
            p.sendMessage("§aVous avez envoyé §e" + amount + "$ à " + target.getName());
            target.sendMessage("§aVous avez reçu §e" + amount + "$ de la part de " + target.getName());

            // Transaction effectuée
            p.sendMessage("§7Nouveau solde : §e" + economyManager.getBalance(p) + "$");
        } else {
            p.sendMessage("§cErreur lors du transfert");
        }

        return true;
    }

}
