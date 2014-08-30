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

	public void insertCoins(ComponentsFactory factory, Coin coin, String modo)throws CoffeeMachineException {
		if(coin == null){
			throw new CoffeeMachineException("");
		}
		if(modo.equals("cracha")){
			factory.getDisplay().warn(Messages.CAN_NOT_INSERT_COINS);
			this.releaseCoinsCracha(coin, factory);
			return;
		}
		else{
			this.totalCoins += coin.getValue();
			this.boxCoins.add(coin);
			factory.getDisplay().info("Total: US$ " + this.totalCoins / 100 + "." + this.totalCoins % 100);

		}
	}

	public void cancel(ComponentsFactory factory) throws CoffeeMachineException {
		if (this.totalCoins == 0) {
			throw new CoffeeMachineException("");
		}
		this.releaseCoins(factory, true);

	}

	public void changeReleases(ComponentsFactory factory, double valueDrink) {
		this.reverseCoins = Coin.reverse();
		for (Coin c : this.reverseCoins) {
			for (Coin cT : this.auxBox) {
				if (cT == c) {
					factory.getCashBox().release(c);
				}
			}
		}
	}
	
	public void emptyBoxCoins() {
		this.boxCoins.clear();
		this.totalCoins = 0;
	}
	
	public boolean planCoins(ComponentsFactory factory, double valueDrink) {
		double change = this.totalCoins - valueDrink;
		this.reverseCoins = Coin.reverse();
		for (Coin c : this.reverseCoins) {
			if (c.getValue() <= change) {
				int cnt = factory.getCashBox().count(c);
				while (c.getValue() <= change && cnt > 0) {
					change = change - c.getValue();
					this.auxBox.add(c);
				}
			}
		}
		return (change == 0);
	}
	
	public boolean checkCoin(ComponentsFactory factory,	double valueDrink) {
		if (this.totalCoins < valueDrink || this.totalCoins == 0) {
			factory.getDisplay().warn(Messages.NO_ENOUGHT_MONEY);
			this.releaseCoins(factory, false);
			return false;
		}
		return true;
	}
	
	public boolean giveEnoughCoins(ComponentsFactory factory, double valueDrink) {
		if (!this.planCoins(factory, valueDrink)) {
			factory.getDisplay().warn(Messages.NO_ENOUGHT_CHANGE);
			this.releaseCoins(factory, false);
			return false;
		}
		return true;
	}
	public void releaseCoins(ComponentsFactory factory, Boolean c) {
		if (c) {
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
	
	public void releaseCoinsCracha(Coin coin, ComponentsFactory factory){
		factory.getCashBox().release(coin);
	}
}
