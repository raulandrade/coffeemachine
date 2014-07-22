package br.ufpb.dce.aps.coffeemachine.impl;

import static org.mockito.Matchers.anyDouble;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.Collections;

import org.mockito.InOrder;

import br.ufpb.dce.aps.coffeemachine.CashBox;
import br.ufpb.dce.aps.coffeemachine.CoffeeMachine;
import br.ufpb.dce.aps.coffeemachine.CoffeeMachineException;
import br.ufpb.dce.aps.coffeemachine.Coin;
import br.ufpb.dce.aps.coffeemachine.ComponentsFactory;
import br.ufpb.dce.aps.coffeemachine.Dispenser;
import br.ufpb.dce.aps.coffeemachine.Drink;
import br.ufpb.dce.aps.coffeemachine.Messages;

public class MyCoffeeMachine implements CoffeeMachine{
 	public final ComponentsFactory factory;
 	public int valor =0, centavo, dolar ;
 	public CashBox cb;
 	public ArrayList<Coin> moedas = new ArrayList<Coin>();
 	private Dispenser cupDispenser;
	
	public MyCoffeeMachine(ComponentsFactory factory){
		this.factory = factory;
		factory.getDisplay().info("Insert coins and select a drink!");
		cupDispenser = factory.getCupDispenser();
		cb = factory.getCashBox();
	}

	public void insertCoin(Coin coin) throws CoffeeMachineException{
		if(coin == null){
			throw new CoffeeMachineException("");
		}
		this.moedas.add(coin);
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
		factory.getDisplay().warn(Messages.CANCEL);
		
		Collections.reverse(this.moedas);
		for (int i = 0; i < this.moedas.size(); i++){
			cb.release(this.moedas.get(i));
		}
		factory.getDisplay().info(Messages.INSERT_COINS);		
	}

	public void select(Drink drink) {
		factory.getCupDispenser().contains(1);
		factory.getWaterDispenser().contains(1.0);
		factory.getCoffeePowderDispenser().contains(1.0);
		
		if (drink == Drink.BLACK_SUGAR){
			factory.getSugarDispenser().contains(1.0);
		}
			
		factory.getDisplay().info(Messages.MIXING);
		factory.getCoffeePowderDispenser().release(1.0);
		factory.getWaterDispenser().release(1.0);
		
		if (drink == Drink.BLACK_SUGAR){
			factory.getSugarDispenser().release(1.0);
		}
	
		factory.getDisplay().info(Messages.RELEASING);
		factory.getCupDispenser().release(1);
		factory.getDrinkDispenser().release(1.0);
		factory.getDisplay().info(Messages.TAKE_DRINK);
		
		factory.getDisplay().info(Messages.INSERT_COINS);		
	}

}
