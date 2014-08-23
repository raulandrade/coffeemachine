package br.ufpb.dce.aps.coffeemachine.impl;

import br.ufpb.dce.aps.coffeemachine.ComponentsFactory;
import br.ufpb.dce.aps.coffeemachine.Drink;
import br.ufpb.dce.aps.coffeemachine.Messages;

public class ManagerCoffeeMachine {

	private ManagerDrink mDrink = new ManagerDrink();

	public void requestDrink(Drink drink, ComponentsFactory factory, ManagerCoins managerCoins) {
		this.mDrink.makeDrink(factory, drink);
		if (!managerCoins.checkCoin(factory, this.mDrink.getValueCoffee())) {
			return;
		}
		if (!this.mDrink.ingredientsDrink(factory, drink)) {
			managerCoins.releaseCoins(factory, false);
			return;
		}
		if (!this.mDrink.checksSugar(factory)) {
			managerCoins.releaseCoins(factory, false);
			return;
		}
		if (!managerCoins.giveEnoughCoins(factory, this.mDrink.getValueCoffee())) {
			return;
		}
		this.mDrink.mixingDrink(factory, drink);
		this.mDrink.releaseDrink(factory);

		if (managerCoins.getTotalCoins() >= this.mDrink.getValueCoffee()) {
			managerCoins.changeReleases(factory, this.mDrink.getValueCoffee());
		}
		this.messageInsertCoins(factory);
		managerCoins.emptyBoxCoins();
	}

	public void messageInsertCoins(ComponentsFactory factory) {
		factory.getDisplay().info(Messages.INSERT_COINS);
	}
}
