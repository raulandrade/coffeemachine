package br.ufpb.dce.aps.coffeemachine.impl;

import br.ufpb.dce.aps.coffeemachine.ComponentsFactory;
import br.ufpb.dce.aps.coffeemachine.Drink;
import br.ufpb.dce.aps.coffeemachine.Messages;

public class ManagerCoffeeMachine {

	private ManagerDrink mDrink = new ManagerDrink();
	private static String access = "";
	private int ac = 0;
	
	public void requestDrink(Drink drink, ComponentsFactory factory,ManagerCoins managerCoins) {
		if (!(access.equals("cracha"))){
			startRequestWithCoins(factory, managerCoins, drink);
		}else{
			startRequestWithCracha(factory, drink);
		}
	}
		
	public void startRequestWithCoins(ComponentsFactory factory, ManagerCoins managerCoins, Drink drink){
		this.mDrink.makeDrink(factory, drink);
		if (!managerCoins.checkCoin(factory, this.mDrink.getValueDrink())) {
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
		if (!managerCoins.giveEnoughCoins(factory, this.mDrink.getValueDrink())) {
			return;
		}
		
		this.mDrink.mixingDrink(factory, drink);
		this.mDrink.releaseDrink(factory);

		if (managerCoins.getTotalCoins() >= this.mDrink.getValueDrink()) {
			managerCoins.changeReleases(factory, this.mDrink.getValueDrink());
		}

		factory.getDisplay().info(Messages.INSERT_COINS);
		ManagerCoffeeMachine.setAccess("");
		
		managerCoins.emptyBoxCoins();	
	}
	
	public void startWithCoins(int cracha, ComponentsFactory factory, ManagerCoins managerCoins) {
		if(managerCoins.getTotalCoins()>0){
			factory.getDisplay().warn(Messages.CAN_NOT_READ_BADGE);
			return;
		}
		else{
			factory.getDisplay().info(Messages.BADGE_READ);
			this.ac = cracha;
			ManagerCoffeeMachine.setAccess("cracha");
		}
	}
	
	public void startRequestWithCracha(ComponentsFactory factory, Drink drink){
		this.mDrink.makeDrink(factory, drink);
		if (!this.mDrink.ingredientsDrink(factory, drink)) {
			return;
		}
		if (!this.mDrink.checksSugar(factory)) {
			return;
		}
		factory.getPayrollSystem().debit(mDrink.getValueDrink(), this.ac);
		
		this.mDrink.mixingDrink(factory, drink);
		this.mDrink.releaseDrink(factory);

		factory.getDisplay().info(Messages.INSERT_COINS);
		ManagerCoffeeMachine.setAccess("");
	}

	public void startWithCracha(ComponentsFactory factory) {
			factory.getDisplay().info(Messages.INSERT_COINS);
			ManagerCoffeeMachine.setAccess("coins");
	}
	
	public String getAccess(){
		return access;
	}
	
	public static void setAccess(String accessMode) {
		access = accessMode;
	}
}
