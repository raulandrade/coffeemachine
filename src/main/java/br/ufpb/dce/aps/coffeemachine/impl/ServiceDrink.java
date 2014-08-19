package br.ufpb.dce.aps.coffeemachine.impl;

import br.ufpb.dce.aps.coffeemachine.ComponentsFactory;
import br.ufpb.dce.aps.coffeemachine.Drink;
import br.ufpb.dce.aps.coffeemachine.Messages;

public abstract class ServiceDrink {

	protected Drink drink; 
	
	public abstract void release(ComponentsFactory factory);	
	
	public Drink getDrink(){
		return this.drink;
	}
}

