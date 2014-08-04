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
 	private final ComponentsFactory factory;
 	private int valor =0, centavo, dolar ;
 	private CashBox cb;
 	//public ArrayList<Coin> moedas = new ArrayList<Coin>();
 	public Coin[] moedas;
 	private Dispenser cupDispenser;
 	private final int valorDoCafe = 35;
	
	public MyCoffeeMachine(ComponentsFactory factory){
		this.factory = factory;
		factory.getDisplay().info("Insert coins and select a drink!");
		cupDispenser = factory.getCupDispenser();
		cb = factory.getCashBox();
		this.moedas = new Coin[50];
	}

	public int valorDoTroco(){
		int contMoedas = 0;
		for(Coin c : Coin.reverse()){
			for(Coin auxiliar : this.moedas){
				if(auxiliar == c){
					contMoedas += auxiliar.getValue();
				}
			}
		}
		return contMoedas - this.valorDoCafe;
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
	
	private void esvaziaLista(){
		for (int j = 0; j < this.moedas.length; j++) {
			this.moedas[j] = null;
		}
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
		esvaziaLista();
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
	
	private ArrayList <Coin> releaseCoin(int valor){
		ArrayList<Coin> listaAux = new ArrayList<Coin>();
		for (int i = 0; i < Coin.reverse().length; i++){
			while (Coin.reverse()[i].getValue() <= valor){
				cb.release(Coin.reverse()[i]);
				valor = valor - Coin.reverse()[i].getValue();
			}
		}
		return listaAux;
	}
	
	
	private ArrayList <Coin> reverseCoin(int valor){
		ArrayList<Coin> listaAux = new ArrayList<Coin>();
		for (int i = 0; i < Coin.reverse().length; i++){
			while (Coin.reverse()[i].getValue() <= valor){
				cb.count(Coin.reverse()[i]);
				listaAux.add(Coin.reverse()[i]);
				valor = valor - Coin.reverse()[i].getValue();
			}
		}
		return listaAux;
	}

	public void select(Drink drink) {
			
		if(valorDoTroco()< 0){
			this.factory.getDisplay().warn(Messages.NO_ENOUGHT_MONEY);
			retornaMoedas();	
			return;
			}
		
		if(!this.factory.getCupDispenser().contains(1)){
			this.factory.getDisplay().warn(Messages.OUT_OF_CUP);
			retornaMoedas();
			return;
		}
		
		if(!this.factory.getWaterDispenser().contains(1.0)){
			this.factory.getDisplay().warn(Messages.OUT_OF_WATER);
			retornaMoedas();
			return;
		}
		
		if(!this.factory.getCoffeePowderDispenser().contains(1.0)){
			this.factory.getDisplay().warn(Messages.OUT_OF_COFFEE_POWDER);
			retornaMoedas();
			return;
		}
		
		if (drink == Drink.WHITE) {
			this.factory.getCreamerDispenser().contains(1.0);
			}
		
		if (drink == Drink.WHITE_SUGAR) {
			this.factory.getCreamerDispenser().contains(1.0);
			this.factory.getSugarDispenser().contains(2.0);

			}
		
		if (drink.equals(Drink.BLACK_SUGAR)) {
			if(!this.factory.getSugarDispenser().contains(1.0)){
				this.factory.getDisplay().warn(Messages.OUT_OF_SUGAR);
				retornaMoedas();
				return;
			}
	
		}
		reverseCoin(valorDoTroco());///Se ligue!!!
		
		
		this.factory.getDisplay().info(Messages.MIXING);
		this.factory.getCoffeePowderDispenser().release(1.0);
		this.factory.getWaterDispenser().release(1.0);
		
		if (drink == Drink.BLACK_SUGAR) {
			this.factory.getSugarDispenser().release(1.0);
		}
		
		if (drink == Drink.WHITE) {
			this.factory.getCreamerDispenser().release(1.0);
		}
		
		if (drink == Drink.WHITE_SUGAR) {
			this.factory.getCreamerDispenser().release(1.0);
			this.factory.getSugarDispenser().release(1.0);
		}
		
		this.factory.getDisplay().info(Messages.RELEASING);
		this.factory.getCupDispenser().release(1);
		this.factory.getDrinkDispenser().release(1.0);
		this.factory.getDisplay().info(Messages.TAKE_DRINK);
		
		
		releaseCoin(valorDoTroco());
		
		esvaziaLista();
		
		this.factory.getDisplay().info(Messages.INSERT_COINS);

		
	}
	

}
