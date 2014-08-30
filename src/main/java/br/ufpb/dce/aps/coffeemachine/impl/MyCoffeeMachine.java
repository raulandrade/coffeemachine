package br.ufpb.dce.aps.coffeemachine.impl;

import br.ufpb.dce.aps.coffeemachine.CoffeeMachine;
import br.ufpb.dce.aps.coffeemachine.Button;
import br.ufpb.dce.aps.coffeemachine.Coin;
import br.ufpb.dce.aps.coffeemachine.ComponentsFactory;

public class MyCoffeeMachine implements CoffeeMachine {

	private ComponentsFactory factory;
	private ManagerCoins managerCoins = new ManagerCoins(); 
	private ManagerCoffeeMachine managerCoffeeMachine = new ManagerCoffeeMachine();
		
	public void setFactory(ComponentsFactory factory) {
		this.factory = factory;		
		this.managerCoffeeMachine.startWithCoins(factory);
	}

	public void readBadge(int badgeCode) {
		this.managerCoffeeMachine.startWithCracha(this.factory, this.managerCoins, badgeCode);
	}

	public void insertCoin(Coin coin) {
		this.managerCoins.insertCoins(this.factory, coin, this.managerCoffeeMachine.getAccess());
	}

	public void cancel(){
		this.managerCoins.cancel(this.factory);	
	}
	
	public void select(Button drink) {		
		this.managerCoffeeMachine.requestDrink(drink, this.factory, this.managerCoins);
	}

	
}
