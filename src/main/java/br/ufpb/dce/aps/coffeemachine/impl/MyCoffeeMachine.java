package br.ufpb.dce.aps.coffeemachine.impl;

import br.ufpb.dce.aps.coffeemachine.CashBox;
import br.ufpb.dce.aps.coffeemachine.CoffeeMachine;
import br.ufpb.dce.aps.coffeemachine.CoffeeMachineException;
import br.ufpb.dce.aps.coffeemachine.Coin;
import br.ufpb.dce.aps.coffeemachine.ComponentsFactory;
import br.ufpb.dce.aps.coffeemachine.Messages;

public class MyCoffeeMachine implements CoffeeMachine{
 	public final ComponentsFactory factory;
 	public int valor =0, centavo, dolar ;
 	public CashBox cb;
	
	public MyCoffeeMachine(ComponentsFactory factory){
		this.factory = factory;
		factory.getDisplay().info("Insert coins and select a drink!");
		cb = factory.getCashBox();
	}

	public void insertCoin(Coin coin) throws CoffeeMachineException{
		if(coin == null){
			throw new CoffeeMachineException("");
		}
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

	public void cancel() throws CoffeeMachineException{
		
		if (centavo == 0  & dolar == 0){
			throw new CoffeeMachineException("Sessão Cancelada");
		}
		
		factory.getDisplay().warn(Messages.CANCEL_MESSAGE);
		cb.release(Coin.halfDollar);
		factory.getDisplay().info(Messages.INSERT_COINS_MESSAGE);		
	}

}
