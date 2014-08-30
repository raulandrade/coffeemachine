package br.ufpb.dce.aps.coffeemachine.impl;

import br.ufpb.dce.aps.coffeemachine.ComponentsFactory;
//import br.ufpb.dce.aps.coffeemachine.Drink;
import br.ufpb.dce.aps.coffeemachine.Button;

import br.ufpb.dce.aps.coffeemachine.Messages;

public class ManagerCoffeeMachine {

	private ManagerDrink mDrink = new ManagerDrink();
	private static String access = "";
	private int ac = 0;
	
	public void requestDrink(Button drink, ComponentsFactory factory, ManagerCoins managerCoins) {
		if (!(access.equals("cracha"))){
			startRequestWithCoins(drink, factory, managerCoins);
		}else{
			startRequestWithCracha(factory, drink);
		}
	}

	public void startRequestWithCoins(Button drink, ComponentsFactory factory, ManagerCoins managerCoins){
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

		this.restartFactory(factory);
		managerCoins.emptyBoxCoins();	
		}
	
	public void startWithCoins(ComponentsFactory factory) {
		factory.getDisplay().info(Messages.INSERT_COINS);
		ManagerCoffeeMachine.setAccess("coins");
	}
	
	public void startRequestWithCracha(ComponentsFactory factory, Button drink){
		this.mDrink.makeDrink(factory, drink);
		if (!this.mDrink.ingredientsDrink(factory, drink)) {
			return;
		}
		if (!this.mDrink.checksSugar(factory)) {
			return;
		}
		if(!factory.getPayrollSystem().debit(mDrink.getValueDrink(), this.ac)){
			factory.getDisplay().warn(Messages.UNKNOWN_BADGE_CODE);
			this.restartFactory(factory);
			return;
		}
		
		this.mDrink.mixingDrink(factory, drink);
		this.mDrink.releaseDrink(factory);
		this.restartFactory(factory);
	}
	
	public void startWithCracha(ComponentsFactory factory, ManagerCoins managerCoins, int cracha) {
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
		
	public String getAccess(){
		return access;
	}
	
	public static void setAccess(String novoModo) {
		access = novoModo;
	}
	
	public void restartFactory(ComponentsFactory factory){
		factory.getDisplay().info(Messages.INSERT_COINS);
		ManagerCoffeeMachine.setAccess("");
	}

}
