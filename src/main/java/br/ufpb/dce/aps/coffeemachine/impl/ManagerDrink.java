package br.ufpb.dce.aps.coffeemachine.impl;

import br.ufpb.dce.aps.coffeemachine.ComponentsFactory;
import br.ufpb.dce.aps.coffeemachine.Drink;
import br.ufpb.dce.aps.coffeemachine.Messages;

public class ManagerDrink {

	private CoffeeService coffeeService;
	private static int valueCoffee = 35;

	public void makeDrink(Drink drink, ComponentsFactory factory) {
		if (drink == drink.BLACK || drink == drink.BLACK_SUGAR) {
			this.coffeeService = new BlackCoffee(drink, factory);
		} else {
			this.coffeeService = new WhiteDrink(drink, factory);
		}
	}

	public boolean ingredientsDrink(ComponentsFactory factory) {
		if (!factory.getCupDispenser().contains(1)) {
			factory.getDisplay().warn(Messages.OUT_OF_CUP);
			return false;
		} else if (!factory.getWaterDispenser().contains(1)) {
			factory.getDisplay().warn(Messages.OUT_OF_WATER);
			return false;
		} else if (!factory.getCoffeePowderDispenser().contains(1)) {
			factory.getDisplay().warn(Messages.OUT_OF_COFFEE_POWDER);
			return false;
		} else if (this.coffeeService.getDrink() == Drink.WHITE	|| this.coffeeService.getDrink() == Drink.WHITE_SUGAR) {
			if (!factory.getCreamerDispenser().contains(1)) {
				return false;
			}
		}
		return true;
	}

	public boolean checksSugar(ComponentsFactory factory) {

		if (this.coffeeService.getDrink() == Drink.BLACK_SUGAR || this.coffeeService.getDrink() == Drink.WHITE_SUGAR) {
			if (!factory.getSugarDispenser().contains(1)) {
				factory.getDisplay().warn(Messages.OUT_OF_SUGAR);
				return false;
			}
		}
		return true;
	}
	

	public void mixingDrink(ComponentsFactory factory) {
		factory.getDisplay().info(Messages.MIXING);
		factory.getCoffeePowderDispenser().release(1);
		factory.getWaterDispenser().release(3);
	}

	public void releaseDrink(ComponentsFactory factory) {
		this.coffeeService.release();
		factory.getDisplay().info(Messages.RELEASING);
		factory.getCupDispenser().release(1);
		factory.getDrinkDispenser().release(1);
		factory.getDisplay().info(Messages.TAKE_DRINK);
	}

	public double getValueCoffee() {
		return this.valueCoffee;
	}
}