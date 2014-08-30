package br.ufpb.dce.aps.coffeemachine.impl;

import br.ufpb.dce.aps.coffeemachine.Button;
import br.ufpb.dce.aps.coffeemachine.ComponentsFactory;
import br.ufpb.dce.aps.coffeemachine.Messages;

public class ManagerDrink {

	private ServiceDrink sDrink;
	private int valueDrink = 35, valueBouillon = 25;

	public void makeDrink(ComponentsFactory factory, Button drink) {
		if (drink == Button.BUTTON_3 || drink == Button.BUTTON_4) {
			this.sDrink = new WhiteDrink(drink);
		}
		else if (drink == Button.BUTTON_1 || drink == Button.BUTTON_2) {
			this.sDrink = new BlackDrink(drink);
		} 
		else {
			this.sDrink = new BouillonDrink(drink);
			this.valueDrink = valueBouillon;
		}
	}

	public boolean ingredientsDrink(ComponentsFactory factory, Button drink) {
		if (this.sDrink.getButton() == Button.BUTTON_1 || this.sDrink.getButton() == Button.BUTTON_2) {
			return (this.checkIngredients(factory, drink, 1, 100, 15, 0, 0));
		}else if (this.sDrink.getButton() == Button.BUTTON_3 || this.sDrink.getButton() == Button.BUTTON_4) {
			return (this.checkIngredients(factory, drink, 1, 80, 15, 20, 0));
		}else {
			return (this.checkIngredients(factory, drink, 1, 100, 0, 0, 10));
		}
	}
	
	public boolean checkIngredients(ComponentsFactory factory, Button drink, int c, int w, int p, int cr, int s) {
		if (c > 0) {
			if (!factory.getCupDispenser().contains(c)) {
				factory.getDisplay().warn(Messages.OUT_OF_CUP);
				return false;
			}
		}
		if (!factory.getWaterDispenser().contains(w)) {
			factory.getDisplay().warn(Messages.OUT_OF_WATER);
			return false;
		}
		if (p > 0) {
			if (!factory.getCoffeePowderDispenser().contains(p)) {
				factory.getDisplay().warn(Messages.OUT_OF_COFFEE_POWDER);
				return false;
			}
		}
		if (this.sDrink.getButton() == Button.BUTTON_3 || this.sDrink.getButton() == Button.BUTTON_4) {
			if (!factory.getCreamerDispenser().contains(cr)) {
				factory.getDisplay().warn(Messages.OUT_OF_CREAMER);
				return false;
			}
		}
		if (s > 0) {
			if (!factory.getBouillonDispenser().contains(s)) {
				factory.getDisplay().warn(Messages.OUT_OF_BOUILLON_POWDER);
				return false;
			}
		}
		return true;
	}

	public boolean checksSugar(ComponentsFactory factory) {
		if (this.sDrink.getButton() == Button.BUTTON_2	|| this.sDrink.getButton() == Button.BUTTON_4) {
			if (!factory.getSugarDispenser().contains(5)) {
				factory.getDisplay().warn(Messages.OUT_OF_SUGAR);
				return false;
			}
		}
		return true;
	}

	public void mixingDrink(ComponentsFactory factory, Button drink) {
		factory.getDisplay().info(Messages.MIXING);
		if (this.sDrink.getButton() == Button.BUTTON_5) {
			factory.getBouillonDispenser().release(10);
		} 
		else {
			factory.getCoffeePowderDispenser().release(15);
		}
	}

	public void releaseDrink(ComponentsFactory factory) {
		this.sDrink.release(factory);
		factory.getDrinkDispenser().release(100);
		factory.getDisplay().info(Messages.TAKE_DRINK);
	}

	public int getValueDrink() {
		return this.valueDrink;
	}
}
