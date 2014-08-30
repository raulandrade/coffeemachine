package br.ufpb.dce.aps.coffeemachine.impl;

import br.ufpb.dce.aps.coffeemachine.ComponentsFactory;
import br.ufpb.dce.aps.coffeemachine.Button;
//import br.ufpb.dce.aps.coffeemachine.Drink;
import br.ufpb.dce.aps.coffeemachine.Messages;

public class BouillonDrink extends ServiceDrink {
	
	public BouillonDrink (Button drink) {
		this.button = Button.BUTTON_5;
	}
	public void release(ComponentsFactory factory) {
		factory.getWaterDispenser().release(100);
		factory.getDisplay().info(Messages.RELEASING);
		factory.getCupDispenser().release(1);
	}
}
