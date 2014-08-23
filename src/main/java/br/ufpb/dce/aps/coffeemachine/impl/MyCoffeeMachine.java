package br.ufpb.dce.aps.coffeemachine.impl;

import br.ufpb.dce.aps.coffeemachine.CoffeeMachine;
import br.ufpb.dce.aps.coffeemachine.Coin;
import br.ufpb.dce.aps.coffeemachine.ComponentsFactory;
import br.ufpb.dce.aps.coffeemachine.Drink;
import br.ufpb.dce.aps.coffeemachine.Messages;

public class MyCoffeeMachine implements CoffeeMachine {

	private ComponentsFactory factory;
	private ManagerCoins mCoins =  new ManagerCoins(); 
	private ManagerCoffeeMachine mCM = new ManagerCoffeeMachine();
	
	public void setFactory(ComponentsFactory factory) {
		this.factory = factory;		
		this.factory.getDisplay().info(Messages.INSERT_COINS);		
	}
	public void readBadge(int badgeCode) {
		mCoins.readBadge(badgeCode, this.factory);
		
	}
	public void insertCoin(Coin coin) {
		this.mCoins.insertCoins(this.factory, coin);
	}
	public void cancel(){
		this.mCoins.cancel(this.factory);	
	}
	public void select(Drink drink) {		
		this.mCM.requestDrink(drink, this.factory, this.mCoins);
	}
}
