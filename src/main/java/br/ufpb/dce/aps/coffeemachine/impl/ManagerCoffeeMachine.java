package br.ufpb.dce.aps.coffeemachine.impl;

import br.ufpb.dce.aps.coffeemachine.ComponentsFactory;
import br.ufpb.dce.aps.coffeemachine.Button;
import br.ufpb.dce.aps.coffeemachine.Messages;

public class ManagerCoffeeMachine {

	private ManagerDrink ManagerDrink = new ManagerDrink();
	private static String access = "";
	private int accessCode = 0;
	
	public void requestDrink(Button drink, ComponentsFactory factory, ManagerCoins managerCoins) {
		if (!(access.equals("cracha"))){
			startRequestWithCoins(drink, factory, managerCoins);
		}else{
			startRequestWithCracha(factory, drink);
		}
	}
	
	public void startRequestWithCoins(Button drink, ComponentsFactory factory, ManagerCoins managerCoins){
		this.ManagerDrink.makeDrink(factory, drink);
		if (!managerCoins.checkCoin(factory, this.ManagerDrink.getValueDrink())) {
			return;
		}
		if (!this.ManagerDrink.ingredientsDrink(factory, drink)) {
			managerCoins.releaseCoins(factory, false);
			return;
		}
		if (!this.ManagerDrink.checksSugar(factory)) {
			managerCoins.releaseCoins(factory, false);
			return;
		}
		if (!managerCoins.giveEnoughCoins(factory, this.ManagerDrink.getValueDrink())) {
			return;
		}
	
		this.ManagerDrink.mixingDrink(factory, drink);
		this.ManagerDrink.releaseDrink(factory);

		if (managerCoins.getTotalCoins() >= this.ManagerDrink.getValueDrink()) {
			managerCoins.changeReleases(factory, this.ManagerDrink.getValueDrink());
		}

		this.restartFactory(factory);
		managerCoins.emptyBoxCoins();	
		}
	
	public void startWithCoins(ComponentsFactory factory) {
		factory.getDisplay().info(Messages.INSERT_COINS);
		ManagerCoffeeMachine.setAccess("coins");
	}
	
	public void startRequestWithCracha(ComponentsFactory factory, Button drink){
		this.ManagerDrink.makeDrink(factory, drink);
		if (!this.ManagerDrink.ingredientsDrink(factory, drink)) {
			return;
		}
		if (!this.ManagerDrink.checksSugar(factory)) {
			return;
		}
		if(!factory.getPayrollSystem().debit(ManagerDrink.getValueDrink(), this.accessCode)){
			factory.getDisplay().warn(Messages.UNKNOWN_BADGE_CODE);
			this.restartFactory(factory);
			return;
		}
		
		this.ManagerDrink.mixingDrink(factory, drink);
		this.ManagerDrink.releaseDrink(factory);
		this.restartFactory(factory);
	}
	
	public void startWithCracha(ComponentsFactory factory, ManagerCoins managerCoins, int cracha) {
		if(managerCoins.getTotalCoins()>0){
			factory.getDisplay().warn(Messages.CAN_NOT_READ_BADGE);
			return;
		}
		else{
			factory.getDisplay().info(Messages.BADGE_READ);
			this.accessCode = cracha;
			ManagerCoffeeMachine.setAccess("cracha");
		}
	}
		
	public String getAccess(){
		return access;
	}
	
	public static void setAccess(String newAccessMode) {
		access = newAccessMode;
	}
	
	public void restartFactory(ComponentsFactory factory){
		factory.getDisplay().info(Messages.INSERT_COINS);
		ManagerCoffeeMachine.setAccess("");
	}

}
