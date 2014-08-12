package br.ufpb.dce.aps.coffeemachine.impl;

import br.ufpb.dce.aps.coffeemachine.CoffeeMachine;
import br.ufpb.dce.aps.coffeemachine.Coin;
import br.ufpb.dce.aps.coffeemachine.ComponentsFactory;
import br.ufpb.dce.aps.coffeemachine.Drink;

public class MyCoffeeMachine implements CoffeeMachine {

	private ComponentsFactory factory;
	private ManagerCoins managerCoins; 
	private ManagerCoffeeMachine managerCoffeeMachine;
		
	public MyCoffeeMachine(ComponentsFactory factory) {
		this.factory = factory;		
		this.managerCoins =  new ManagerCoins();
		this.managerCoffeeMachine = new ManagerCoffeeMachine();
		this.managerCoffeeMachine.messageInsertCoins(factory);
	}
	
	public void insertCoin(Coin coin) {
		this.managerCoins.insertCoins(coin, this.factory);
	}

	public void cancel(){
		this.managerCoins.cancel(this.factory);	
	}
	
	public void select(Drink drink) {		
		this.managerCoffeeMachine.requestDrink(drink, this.managerCoins, this.factory);
	}
}