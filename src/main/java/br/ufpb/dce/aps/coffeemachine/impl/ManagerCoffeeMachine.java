package br.ufpb.dce.aps.coffeemachine.impl;

import br.ufpb.dce.aps.coffeemachine.ComponentsFactory;
import br.ufpb.dce.aps.coffeemachine.Drink;
import br.ufpb.dce.aps.coffeemachine.Messages;

public class ManagerCoffeeMachine {

	private ManagerDrink managerDrink = new ManagerDrink();

	public void requestDrink (Drink drink, ManagerCoins managerCoins, ComponentsFactory factory) {

		if (!managerCoins.checkCoin(this.managerDrink.getValueCoffee(),factory)) {
			return;
		}
		
		this.managerDrink.makeDrink(drink, factory);

		if (!this.managerDrink.ingredientsDrink(factory)) {
			managerCoins.ReleaseCoins(factory, false);
			return;
		} if (!this.managerDrink.checksSugar(factory)) {
			managerCoins.ReleaseCoins(factory, false);
			return;
		} if (!managerCoins.giveEnoughCoins(factory,this.managerDrink.getValueCoffee())) {
			return;
		}
		

		this.managerDrink.mixingDrink(factory);
		this.managerDrink.releaseDrink(factory);
		
		//Entrega o troco
		if ((managerCoins.getTotalCoins() % this.managerDrink.getValueCoffee() != 0) && (managerCoins.getTotalCoins() > this.managerDrink.getValueCoffee())) {
			managerCoins.changeReleases(this.managerDrink.getValueCoffee(), factory);
		}

		this.messageInsertCoins(factory);
		managerCoins.emptyBoxCoins();
	}

	public void messageInsertCoins(ComponentsFactory factory){
		factory.getDisplay().info(Messages.INSERT_COINS);
	}

}