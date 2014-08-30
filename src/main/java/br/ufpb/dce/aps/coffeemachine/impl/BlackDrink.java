package br.ufpb.dce.aps.coffeemachine.impl;

import br.ufpb.dce.aps.coffeemachine.Button;
import br.ufpb.dce.aps.coffeemachine.ComponentsFactory;
import br.ufpb.dce.aps.coffeemachine.Messages;

public class BlackDrink extends ServiceDrink {

	public BlackDrink (Button button) {
		if (this.button == Button.BUTTON_2) {
			this.button = Button.BUTTON_2;
		}else {
			this.button = Button.BUTTON_1;
		}
	}
	
	public void release(ComponentsFactory factory) {
		factory.getWaterDispenser().release(100);
		
		if (button == Button.BUTTON_2) {
			factory.getSugarDispenser().release(5);
		}
		
		factory.getDisplay().info(Messages.RELEASING);
		factory.getCupDispenser().release(1);
	}
}

