package src.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.InputMismatchException;

public abstract class ContaBancaria {

    //#region Atributos
  protected String agencia;
    
    protected String conta;

    protected Integer digito;

    protected double saldo;

    protected Date dataAbertura;

    protected ArrayList<Movimentacao> movimentacoes;

    protected Double VALOR_MINIMO_DEPOSITO = 10.0;

    //#endregion

    
    //#region Construtores
    public ContaBancaria(String agencia, String conta, Integer digito, double saldoInicial) {
        this.agencia = agencia;
        this.conta = conta;
        this.digito = digito;
        this.saldo = saldoInicial;
        this.dataAbertura = new Date();
        
        // Se não instanciar, vai dar uma excepcion de nullpointerExcepcion
        this.movimentacoes = new ArrayList<Movimentacao>();

        Movimentacao movimentacao = new Movimentacao("Abertura de conta", saldoInicial);
        
        // Aqui estou salvando a movimentação dentro do meu array de movimentacoes.
        // Aqui estou iniciando o meu extrato bancário
        this.movimentacoes.add(movimentacao);
    }
    //#endregion
    //#region Getters e Setters


    public Date getDataAbertura() {
        return dataAbertura;
    }

    public String getAgencia() {
        return agencia;
    }

    public void setAgencia(String agencia) {
        this.agencia = agencia;
    }

    public String getConta() {
        return conta;
    }

    public void setConta(String conta) {
        this.conta = conta;
    }

    public Integer getDigito() {
        return digito;
    }

    public void setDigito(Integer digito) {
        this.digito = digito;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }
    //#endregion
    
    //#region Metodos
    public void depositar(double valor) {

        // Verifica se o valor minimo de deposito é menor que o valor minimo
        // Se for, não pode acontecer deposito
        if(valor < VALOR_MINIMO_DEPOSITO) {
            throw new InputMismatchException("O valor minimo para deposito é R$" + VALOR_MINIMO_DEPOSITO);
        }
         
        // Efetua o deposito somando o valor ao saldo.
        this.saldo += valor;

    }

    public Double sacar(Double valor) {
        
        // Verifica se o valor é maior que o saldo da conta.
        // Se for, manda mensagem de saldo insuficiente.
        if(valor > this.saldo) {
            throw new InputMismatchException("O saldo é insuficinete");
        }
        
        // Aqui removemos o valor do saque atual.
        this.saldo -= valor;

        // Aqui faço uma movimentação no extrato.
        Movimentacao movimentacao = new Movimentacao("Retirada de valor", valor);
        this.movimentacoes.add(movimentacao);

        // Rotorna o valor sacado ao usuario.
        return valor;
    }

    public void transferir(Double valor, ContaBancaria ContaDestino) {

        this.sacar(valor);

        ContaDestino.depositar(valor);
    }
    //#endregion

    // Método abstrato obriga as classes que estão herdando de implementarem este método.
    public abstract void imprimirExtrato();
    

}
