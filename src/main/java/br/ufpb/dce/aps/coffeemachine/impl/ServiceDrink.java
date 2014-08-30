package br.ufpb.dce.aps.coffeemachine.impl;

import br.ufpb.dce.aps.coffeemachine.Button;
import br.ufpb.dce.aps.coffeemachine.ComponentsFactory;

public abstract class ServiceDrink {
	protected Button button; 
	
	public abstract void release(ComponentsFactory factory);	
	public Button getButton(){
		return this.button;
	}
}

