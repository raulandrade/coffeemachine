package br.ufpb.dce.aps.coffeemachine.impl;

import static org.mockito.Mockito.verify;
import br.ufpb.dce.aps.coffeemachine.CoffeeMachine;
import br.ufpb.dce.aps.coffeemachine.Coin;
import br.ufpb.dce.aps.coffeemachine.ComponentsFactory;

public class MyCoffeeMachine implements CoffeeMachine{
 	public final ComponentsFactory factory;
 	public int valor =0, centavo, dolar ;
	
	public MyCoffeeMachine(ComponentsFactory factory){
		this.factory = factory;
		factory.getDisplay().info("Insert coins and select a drink!");
	}

	public void insertCoin(Coin coin) {
		this.valor += coin.getValue();
		this.factory.getDisplay().info("Total: US$ "+ getDolares(coin)+"."+ getCentavos(coin));
	}
	
	public int getCentavos(Coin coin){
		this.centavo += coin.getValue();
		return this.centavo%100;
	}
	
	public int getDolares(Coin coin){
		this.dolar += coin.getValue();
		return this.dolar/100;
		
	}

}
