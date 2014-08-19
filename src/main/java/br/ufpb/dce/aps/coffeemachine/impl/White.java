package br.ufpb.dce.aps.coffeemachine.impl;

import br.ufpb.dce.aps.coffeemachine.ComponentsFactory;
import br.ufpb.dce.aps.coffeemachine.Drink;
import br.ufpb.dce.aps.coffeemachine.Messages;

public class White extends ServiceDrink {

	public White(Drink drink) {
		if (drink == drink.WHITE) {
			this.drink = drink.WHITE;
		} else {
			this.drink = drink.WHITE_SUGAR;
		}
	}

	public void release(ComponentsFactory factory) {
		factory.getWaterDispenser().release(80);
		factory.getCreamerDispenser().release(20);
		if (this.drink == drink.WHITE_SUGAR) {
			factory.getSugarDispenser().release(5);
		}
		factory.getDisplay().info(Messages.RELEASING);
		factory.getCupDispenser().release(1);
	}
	
	
}
