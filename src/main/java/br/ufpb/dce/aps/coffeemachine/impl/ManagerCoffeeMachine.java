package br.ufpb.dce.aps.coffeemachine.impl;

import br.ufpb.dce.aps.coffeemachine.ComponentsFactory;
import br.ufpb.dce.aps.coffeemachine.Drink;
import br.ufpb.dce.aps.coffeemachine.Messages;

public class ManagerCoffeeMachine {

	private ManagerDrink mDrink = new ManagerDrink();
	private static String access = "";
	private int ac = 0;

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
		factory.getDisplay().info(Messages.INSERT_COINS);
		managerCoins.emptyBoxCoins();
	}

	public void startCracha(ComponentsFactory factory, ManagerCoins managerCoins, int c) {
		if(managerCoins.getTotalCoins() < 0){
			factory.getDisplay().info(Messages.BADGE_READ);
			this.ac = c;
			ManagerCoffeeMachine.setAccess("cracha");
		} else{
			factory.getDisplay().warn(Messages.CAN_NOT_READ_BADGE);
			return;
		}
	}
	
	public void startCoins(ComponentsFactory factory) {
		factory.getDisplay().info(Messages.INSERT_COINS);
		ManagerCoffeeMachine.setAccess("moedas");
	}
		
	public static void setAccess(String a) {
		access = a;
	}
	
	public String getAccess(){
		return access;
	}

}
