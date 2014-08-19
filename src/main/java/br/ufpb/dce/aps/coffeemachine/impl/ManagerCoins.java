package br.ufpb.dce.aps.coffeemachine.impl;

import java.util.ArrayList;

import br.ufpb.dce.aps.coffeemachine.CoffeeMachineException;
import br.ufpb.dce.aps.coffeemachine.Coin;
import br.ufpb.dce.aps.coffeemachine.ComponentsFactory;
import br.ufpb.dce.aps.coffeemachine.Messages;

public class ManagerCoins {
	
	private int totalCoins;
	private Coin[] reverseCoins = Coin.reverse();
	private ArrayList<Coin> boxCoins = new ArrayList<Coin>();
	private ArrayList<Coin> boxChange = new ArrayList<Coin>();


	//Insere moedas
	public void insertCoins(Coin coin, ComponentsFactory factory) throws CoffeeMachineException {
		if(coin == null){
			throw new CoffeeMachineException("");
		}
		this.totalCoins += coin.getValue();
		this.boxCoins.add(coin);
		factory.getDisplay().info("Total: US$ " + this.totalCoins / 100 + "." + this.totalCoins % 100);
	}

	//Cancela
	public void cancel(ComponentsFactory factory) throws CoffeeMachineException {	
		if (this.totalCoins == 0) {
			throw new CoffeeMachineException("");
		}
		this.ReleaseCoins(factory, true);

	}

	//Entrega o troco
	public void changeReleases(double valorDaBebida, ComponentsFactory factory) {
		this.reverseCoins = Coin.reverse();
		for (Coin c : this.reverseCoins) {
			for (Coin cc : this.boxChange) {
				if (cc == c) {
					factory.getCashBox().release(c);
				}
			}
		}
	}

	
	//Esvazia a caixa de moedas
	public void emptyBoxCoins() {
		this.boxCoins.clear();
	}
	
	//Libera moedas
	public boolean planCoins(double change, ComponentsFactory factory) {
		double vChange = this.totalCoins - change;
		this.reverseCoins = Coin.reverse();
		for (Coin c : this.reverseCoins) {
			if (c.getValue() <= vChange && factory.getCashBox().count(c) > 0) {
				while (c.getValue() <= vChange) {
					vChange = vChange - c.getValue();
					this.boxChange.add(c);
				}
			}
		}
		return (vChange == 0);
	}

	//Checa moedas inseridas
	public boolean checkCoin(double valueDrink, ComponentsFactory factory) {
		if (this.totalCoins < valueDrink || this.totalCoins == 0) {
			factory.getDisplay().warn(Messages.NO_ENOUGHT_MONEY);
			this.ReleaseCoins(factory, false);
			return false;
		}
		return true;
	}

	//Verifica se possui moedas suficientes para troco
	public boolean giveEnoughCoins(ComponentsFactory factory, double valueDrink) {
		if (this.totalCoins % valueDrink != 0 && this.totalCoins > valueDrink) {
			if (!(this.planCoins(valueDrink, factory))) {
				factory.getDisplay().warn(Messages.NO_ENOUGHT_CHANGE);
				this.ReleaseCoins(factory, false);
				return false;
			}
		}
		return true;
	}
	

	
	
	// Libera as moedas
	public void ReleaseCoins(ComponentsFactory factory, Boolean condition) {
		if (condition) {
			factory.getDisplay().warn(Messages.CANCEL);
		}
		for (Coin r : this.reverseCoins) {
			for (Coin aux : this.boxCoins) {
				if (aux == r) {
					factory.getCashBox().release(aux);
				}
			}
		}
		this.totalCoins = 0;
		this.emptyBoxCoins();
		factory.getDisplay().info(Messages.INSERT_COINS);
	}
	

	//Retorna o total de moedas
	public int getTotalCoins() {
		return totalCoins;
	}
}
