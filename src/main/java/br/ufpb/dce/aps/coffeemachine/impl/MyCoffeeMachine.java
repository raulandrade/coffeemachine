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
 	//public ArrayList<Coin> moedas = new ArrayList<Coin>();
 	public Coin[] moedas;
 	private Dispenser cupDispenser;
	
	public MyCoffeeMachine(ComponentsFactory factory){
		this.factory = factory;
		factory.getDisplay().info("Insert coins and select a drink!");
		cupDispenser = factory.getCupDispenser();
		cb = factory.getCashBox();
		this.moedas = new Coin[50];
	}

	public void insertCoin(Coin coin) throws CoffeeMachineException{
		
		if (coin == null) {
			throw new CoffeeMachineException("Sem moedas!");
		}
		this.moedas[++this.valor] = coin;
		this.dolar += coin.getValue() / 100;
		this.centavo += coin.getValue() % 100;
		this.factory.getDisplay().info(
				"Total: US$ " + this.dolar + "." + this.centavo);
	}
		
		
	private void retornaMoedas(){
		Coin[] c = Coin.reverse();
		for (int i = 0; i < c.length; i++) {
			for (int j = 0; j < this.moedas.length; j++) {
				if (c[i].equals(this.moedas[j])) {
					this.factory.getCashBox().release(this.moedas[j]);
					this.moedas[j] = null;
				}
			}
		}
		this.factory.getDisplay().info(Messages.INSERT_COINS);
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
		if (this.dolar == 0 && this.centavo == 0) {
			throw new CoffeeMachineException("Sessão cancelada!");
		}
		this.factory.getDisplay().warn(Messages.CANCEL);
		retornaMoedas();
			
	}

	public void select(Drink drink) {
		
		this.factory.getCupDispenser().contains(1);
		this.factory.getWaterDispenser().contains(1.0);
		if(!this.factory.getCoffeePowderDispenser().contains(1.0)){
			this.factory.getDisplay().warn(Messages.OUT_OF_COFFEE_POWDER);
			retornaMoedas();
			return;
		}
		if (drink.equals(Drink.BLACK_SUGAR)) {
			if(!this.factory.getSugarDispenser().contains(1.0)){
				this.factory.getDisplay().warn(Messages.OUT_OF_SUGAR);
				retornaMoedas();
				return;
			}
	
		}
		this.factory.getDisplay().info(Messages.MIXING);
		this.factory.getCoffeePowderDispenser().release(1.0);
		this.factory.getWaterDispenser().release(1.0);
		if (drink.equals(Drink.BLACK_SUGAR)) {
			this.factory.getSugarDispenser().release(1.0);
		}

		this.factory.getDisplay().info(Messages.RELEASING);
		this.factory.getCupDispenser().release(1);
		this.factory.getDrinkDispenser().release(1.0);
		this.factory.getDisplay().info(Messages.TAKE_DRINK);
		for(int i = 0; i < this.moedas.length; i++){
			moedas[i] = null;
		}
		this.factory.getDisplay().info(Messages.INSERT_COINS);
		
	}
	

}
