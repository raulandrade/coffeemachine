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
		for (Coin moeda : this.reverseCoins) {
			for (Coin moedaDeTroco : this.auxBox) {
				if (moedaDeTroco == moeda) {
					factory.getCashBox().release(moeda);
				}
			}
		}
	}

	public void emptyBoxCoins() {
		this.boxCoins.clear();
	}
	
	
	public boolean planCoins(ComponentsFactory factory,
			double valorDaBebida) {
		double troco = this.totalCoins - valorDaBebida;
		this.reverseCoins = Coin.reverse();
		for (Coin moeda : this.reverseCoins) {
			if (moeda.getValue() <= troco && factory.getCashBox().count(moeda) > 0) {
				while (moeda.getValue() <= troco) {
					troco = troco - moeda.getValue();
					this.auxBox.add(moeda);
				}
			}
		}
		return (troco == 0);
	}

	
	public boolean checkCoin(ComponentsFactory factory,
			double valorDaBebida) {
		if (this.totalCoins < valorDaBebida || this.totalCoins == 0) {
			factory.getDisplay().warn(Messages.NO_ENOUGHT_MONEY);
			this.ReleaseCoins(factory, false);
			return false;
		}
		return true;
	}


	public boolean giveEnoughCoins(ComponentsFactory factory,
			double valorDaBebida) {
		if (this.totalCoins % valorDaBebida != 0 && this.totalCoins > valorDaBebida) {
			if (!this.planCoins(factory, valorDaBebida)) {
				factory.getDisplay().warn(Messages.NO_ENOUGHT_CHANGE);
				this.ReleaseCoins(factory, false);
				return false;
			}
		}
		return true;
	}

	
	public void ReleaseCoins(ComponentsFactory factory, Boolean confirmacao) {
		if (confirmacao) {
			factory.getDisplay().warn(Messages.CANCEL);
		}
		for (Coin re : this.reverseCoins) {
			for (Coin aux : this.boxCoins) {
				if (aux == re) {
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
