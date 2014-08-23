package br.ufpb.dce.aps.coffeemachine.impl;

import java.util.ArrayList;

import br.ufpb.dce.aps.coffeemachine.Coin;
import br.ufpb.dce.aps.coffeemachine.ComponentsFactory;
import br.ufpb.dce.aps.coffeemachine.Drink;
import br.ufpb.dce.aps.coffeemachine.Messages;

public class ManagerDrink {

	private ServiceDrink sDrink;
	private int valueDrink = 35, valueBouillon = 25;

	public void makeDrink(ComponentsFactory factory, Drink drink) {
		if (drink == drink.WHITE || drink == drink.WHITE_SUGAR) {
			this.sDrink = new WhiteDrink(drink);
		}
		else if (drink == drink.BLACK || drink == drink.BLACK_SUGAR) {
			this.sDrink = new BlackDrink(drink);
		} 
		else {
			this.sDrink = new BouillonDrink(drink);
			this.valueDrink = valueBouillon;
		}
	}

	public boolean ingredientsDrink(ComponentsFactory factory, Drink drink) {
		if (this.sDrink.getDrink() == drink.BLACK || this.sDrink.getDrink() == Drink.BLACK_SUGAR) {
			return (this.checkIngredients(factory, drink, 1, 100, 15, 0, 0));
		}else if (this.sDrink.getDrink() == drink.WHITE || this.sDrink.getDrink() == drink.WHITE_SUGAR) {
			return (this.checkIngredients(factory, drink, 1, 80, 15, 20, 0));
		}else {
			return (this.checkIngredients(factory, drink, 1, 100, 0, 0, 10));
		}
	}
	
	public boolean checkIngredients(ComponentsFactory factory, Drink drink, int c, int w, int p, int cr, int s) {
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
		if (this.sDrink.getDrink() == Drink.WHITE
				|| this.sDrink.getDrink() == Drink.WHITE_SUGAR) {
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
		if (this.sDrink.getDrink() == Drink.BLACK_SUGAR	|| this.sDrink.getDrink() == Drink.WHITE_SUGAR) {
			if (!factory.getSugarDispenser().contains(5)) {
				factory.getDisplay().warn(Messages.OUT_OF_SUGAR);
				return false;
			}
		}
		return true;
	}

	public void mixingDrink(ComponentsFactory factory, Drink drink) {
		factory.getDisplay().info(Messages.MIXING);
		if (this.sDrink.getDrink() == drink.BOUILLON) {
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

	public double getValueCoffee() {
		return this.valueDrink;
	}
}
