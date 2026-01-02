package fr.xenox.simpleEconomy.managers;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class EconomyManager {

    /*
     * Stockage dans la mémoire dans un premier temps.
     * @TODO - Passer en mode db - sqlite
     */
    private final HashMap<UUID, Double> balances;

    // Argent de départ
    private final double startingBalance = 100.0;

    public EconomyManager() {
        this.balances = new HashMap<>();
    }

    /*
     * Récupération de la solde d'un joueur ou renvoie par défaut le starting balance.
     */
    public double getBalance(Player player) {
        return balances.getOrDefault(player.getUniqueId(), startingBalance);
    }

    /*
     * Défini le solde complet d'un joueur
     * Int positif uniquement - Sinon null
     */
    public void setBalance(Player player, double amount) {
        // Montant positif ofc
        if (amount < 0) {
            amount = 0;
            // Log ?
        }

        balances.put(player.getUniqueId(), amount);
    }

    /*
     * Ajoute de l'argent à un joueur
     * Possitif int
     */
    public void addMoney(Player player, double amount) {

        if (amount < 0) {
            amount = 0;
        }

        double currentBalance = getBalance(player);
        setBalance(player, currentBalance + amount);
    }

    /*
     * Retire de l'argent à un joueur
     */
    public boolean removeMoney(Player player, double amount) {

        if (amount < 0) {
            return false; // Pas d'éléments négatifs
        }

        // Récupère balance du joueur
        double currentBalance = getBalance(player);

        if (currentBalance >= amount ) {
            setBalance(player, currentBalance - amount);
            return true;
        }

        return false; // Si pas assez d'argent
    }

    /*
     * Transfert l'argent d'un joueur à un autre
     */
    public boolean transferMoney(Player from, Player to, double amount) {
        if (amount < 0) {
            return false;
        }

        // Si l'user peut envoyer l'argent (si assez, sinon on bloque ofc)
        if (removeMoney(from, amount)) {
            addMoney(to, amount);
            return true;
        }

        return false;
    }



}
