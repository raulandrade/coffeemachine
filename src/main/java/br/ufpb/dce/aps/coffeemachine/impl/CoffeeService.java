package br.ufpb.dce.aps.coffeemachine.impl;

import br.ufpb.dce.aps.coffeemachine.ComponentsFactory;
import br.ufpb.dce.aps.coffeemachine.Drink;

public abstract class CoffeeService {

	protected ComponentsFactory factory; Drink drink;

	public Drink getDrink(){
		return this.drink;
	}
	public abstract void release();	
	
	
}

