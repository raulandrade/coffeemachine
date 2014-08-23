package br.ufpb.dce.aps.coffeemachine.impl;
import br.ufpb.dce.aps.coffeemachine.ComponentsFactory;
import br.ufpb.dce.aps.coffeemachine.Drink;
import br.ufpb.dce.aps.coffeemachine.Messages;

public class WhiteDrink extends ServiceDrink {

	public WhiteDrink(Drink drink) {
		if (drink == drink.WHITE_SUGAR) {
			this.drink = drink.WHITE_SUGAR;
		}else {
			this.drink = drink.WHITE;
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
