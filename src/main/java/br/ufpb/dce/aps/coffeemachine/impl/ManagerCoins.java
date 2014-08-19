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
	private ArrayList<Coin> auxBox = new ArrayList<Coin>();

	//Insere moedas (inserirMoeda) OK
	public void insertCoins(Coin coin, ComponentsFactory factory) throws CoffeeMachineException {
		if(coin == null){
			throw new CoffeeMachineException("");
		}
		this.totalCoins += coin.getValue();
		this.boxCoins.add(coin);
		factory.getDisplay().info("Total: US$ " + this.totalCoins / 100 + "." + this.totalCoins % 100);
	}
	
	//Cancela (CAncelar) 
	public void cancel(ComponentsFactory factory) throws CoffeeMachineException {	
		if (this.totalCoins == 0) {
			throw new CoffeeMachineException("");
		}
		this.ReleaseCoins(factory, true);
		}

	
	public void changeReleases(ComponentsFactory factory, double valorDaBebida) {
		this.reverseCoins = Coin.reverse();
		for (Coin c : this.reverseCoins) {
			for (Coin cC : this.auxBox) {
				if (cC == c) {
					factory.getCashBox().release(c);
				}
			}
		}
	}
	
	public void emptyBoxCoins() {
		this.boxCoins.clear();
	}
	
	
	public boolean planCoins(ComponentsFactory factory,	double valueDrink) {
		double change = this.totalCoins - valueDrink;
		this.reverseCoins = Coin.reverse();
		for (Coin c : this.reverseCoins) {
			if(c.getValue() <= change && factory.getCashBox().count(c) >0){
				while (c.getValue() <= change) {
					change = change - c.getValue();
					this.auxBox.add(c);
				}
			}
		}
		return (change == 0);
	}

	
	public boolean checkCoin(ComponentsFactory factory,	double ValueDrink) {
		if (this.totalCoins < ValueDrink || this.totalCoins == 0) {
			factory.getDisplay().warn(Messages.NO_ENOUGHT_MONEY);
			this.ReleaseCoins(factory, false);
			return false;
		}
		return true;
	}
	



	public boolean giveEnoughCoins(ComponentsFactory factory,double valueDrink) {
		if (this.totalCoins % valueDrink != 0 && this.totalCoins > valueDrink) {
			if(!this.planCoins(factory, valueDrink)){
				factory.getDisplay().warn(Messages.NO_ENOUGHT_CHANGE);
				ReleaseCoins(factory, false);
				return false;
			}
		}
		return true;

	}
	
	
	
	
	
	public void ReleaseCoins(ComponentsFactory factory, Boolean confirmation) {
		if (confirmation) {
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
	public int getTotalCoins() {
		return totalCoins;
	}
}
