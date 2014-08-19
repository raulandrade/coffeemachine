package br.ufpb.dce.aps.coffeemachine.impl;

import br.ufpb.dce.aps.coffeemachine.CoffeeMachine;
import br.ufpb.dce.aps.coffeemachine.Coin;
import br.ufpb.dce.aps.coffeemachine.ComponentsFactory;
import br.ufpb.dce.aps.coffeemachine.Drink;
import br.ufpb.dce.aps.coffeemachine.Messages;

public class MyCoffeeMachine implements CoffeeMachine {

	private ComponentsFactory factory;
	private ManagerCoins gerenteFinanceiro =  new ManagerCoins(); 
	private ManagerCoffeeMachine gerenteDeMaquina = new ManagerCoffeeMachine();
		
	public MyCoffeeMachine(ComponentsFactory factory) {
		this.factory = factory;		
		this.gerenteDeMaquina.menssageInsert(factory);
	}
	
	public void insertCoin(Coin coin) {
		this.gerenteFinanceiro.insertCoins(coin, this.factory);
	}

	public void cancel(){
		this.gerenteFinanceiro.cancel(this.factory);	
	}
	
	public void select(Drink drink) {		
		this.gerenteDeMaquina.startDrink(this.factory, this.gerenteFinanceiro, drink);
	}
}
