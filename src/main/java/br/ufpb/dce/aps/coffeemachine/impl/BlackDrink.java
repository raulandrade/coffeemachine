package br.ufpb.dce.aps.coffeemachine.impl;

import br.ufpb.dce.aps.coffeemachine.ComponentsFactory;
import br.ufpb.dce.aps.coffeemachine.Drink;
import br.ufpb.dce.aps.coffeemachine.Messages;

public class BlackDrink extends ServiceDrink {

	public BlackDrink (Drink drink) {
		if (drink.BLACK_SUGAR == drink) {
			this.drink = drink.BLACK_SUGAR;
		}else {
			this.drink = drink.BLACK;
		}
	}

	public void release(ComponentsFactory factory) {
		factory.getWaterDispenser().release(100);
		
		if (drink == drink.BLACK_SUGAR) {
			factory.getSugarDispenser().release(5);
		}
		
		factory.getDisplay().info(Messages.RELEASING);
		factory.getCupDispenser().release(1);
	}
}
