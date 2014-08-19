package br.ufpb.dce.aps.coffeemachine.impl;

import br.ufpb.dce.aps.coffeemachine.ComponentsFactory;
import br.ufpb.dce.aps.coffeemachine.Drink;
import br.ufpb.dce.aps.coffeemachine.Messages;

public class ManagerCoffeeMachine {

	private ManagerDrink mDrink = new ManagerDrink();

	public void startDrink(ComponentsFactory factory, ManagerCoins mCoins, Drink drink) {
		
		this.mDrink.makeDrink(factory, drink);
		if (!mCoins.checkCoin(factory, this.mDrink.getValueCoffee())) {
			return;
		}if (!this.mDrink.ingredientsDrink(factory,drink)) {
			mCoins.ReleaseCoins(factory, false);
			return;
		}if (!this.mDrink.checksSugar(factory)) {
			mCoins.ReleaseCoins(factory, false);
			return;
		}if (!mCoins.giveEnoughCoins(factory,
				this.mDrink.getValueCoffee())) {
			return;
		}
		
		this.mDrink.mixingDrink(factory, drink);
		this.mDrink.releaseDrink(factory);

		if (mCoins.getTotalCoins()% this.mDrink.getValueCoffee() != 0	&& mCoins.getTotalCoins() > this.mDrink.getValueCoffee()) {
			mCoins.changeReleases(factory, this.mDrink.getValueCoffee());
		}
	
	
		this.menssageInsert(factory);
		mCoins.emptyBoxCoins();
	}
	
	public void menssageInsert(ComponentsFactory factory){
		factory.getDisplay().info(Messages.INSERT_COINS);
	}

}
