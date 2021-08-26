import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

/*

SILVER
on-points = 50_000
off-points= 49_999
in points = 1_000, 10_0000, 30_0000
out points = 60_000, 70_000, 100_000

--------------------------------------
GOLD

on-points = 200_000
off-points= 199_999
in points = 100_000, 150_0000, 190_0000
out points = 200_000, 250_000, 350_000

--------------------------------------
PLATINUM

on-points = 199_999
off-points= 200_000
in points = 200_000, 250_0000, 300_0000
out points = 50_000, 100_000, 150_000


*/


public class ContaCorrenteTest {
    private ContaCorrente conta;

    @BeforeEach
    public void start (){
        this.conta = new ContaCorrente("1234-3","Joao Ribeiro");
    }

    @Test
    public void validaInformacoesConta(){

        assertEquals("1234-3",conta.getNumeroConta());
        assertEquals("Joao Ribeiro",conta.getNomeCorrentista());

    }


    @Test
    public void depositoValorInvalido() {
        double valorDeposito = -5;

        boolean resultadoDeposito = conta.deposito(valorDeposito);

        assertFalse(resultadoDeposito);
        assertEquals(0, conta.getSaldo());
        assertEquals(CategoriaEnum.SILVER, conta.getCategoria());

    }

    @Test
    public void depositoSilver() {
        double valorDeposito = 49000;

        boolean resultadoDeposito = conta.deposito(valorDeposito);

        assertEquals(49000, conta.getSaldo());
        assertEquals(CategoriaEnum.SILVER, conta.getCategoria());
    }

    @Test
    public void depositoGold() {
        double valorDeposito = 50_000;

        boolean resultadoDeposito = conta.deposito(valorDeposito);

        assertEquals(50_000, conta.getSaldo());
        assertEquals(CategoriaEnum.GOLD, conta.getCategoria());
    }


    @Test
    public void depositoMudaCategoriaPlatinumLimite() {
        depositoInicial(50_000);
        double valorDeposito = 140_000;

        boolean resultadoDeposito = conta.deposito(valorDeposito);

        assertEquals(191_400, conta.getSaldo());
        assertEquals(CategoriaEnum.GOLD, conta.getCategoria());
    }

    @Test
    public void depositoMudaCategoriaPlatinum() {
        depositoInicial(50_000);
        double valorDeposito = 200_000;

        boolean resultadoDeposito = conta.deposito(valorDeposito);

        assertEquals(252_000, conta.getSaldo());
        assertEquals(CategoriaEnum.PLATINUM, conta.getCategoria());
    }

    @Test
    public void depositoValorizaPlatinum() {
        depositoInicial(50_000);
        depositoInicial(200_000);
        double valorDeposito = 1_000;


        boolean resultadoDeposito = conta.deposito(valorDeposito);

        assertEquals(253_025, conta.getSaldo());
        assertEquals(CategoriaEnum.PLATINUM, conta.getCategoria());
    }

    @Test
    public void retiradaValorMaiorQueSaldo() {
        depositoInicial(500);
        double valorSaque = 1000;

        boolean resultadoSaque = conta.retirada(valorSaque);

        assertFalse(resultadoSaque);
        assertEquals(500, conta.getSaldo());
        assertEquals(CategoriaEnum.SILVER, conta.getCategoria());
    }

    @Test
    public void retiradaGold() {
        depositoInicial(50_000);
        double valorSaque = 30_000;

        boolean resultadoSaque = conta.retirada(valorSaque);

        assertTrue(resultadoSaque);
        assertEquals(20_000, conta.getSaldo());
        assertEquals(CategoriaEnum.SILVER, conta.getCategoria());
    }

    @Test
    public void retiradaContinuaGold() {
        depositoInicial(50_000);
        double valorSaque = 10_000;

        boolean resultadoSaque = conta.retirada(valorSaque);

        assertTrue(resultadoSaque);
        assertEquals(40_000, conta.getSaldo());
        assertEquals(CategoriaEnum.GOLD, conta.getCategoria());
    }

    @Test
    public void retiradaGoldLimite() {
        depositoInicial(50_000);
        double valorSaque = 25_000;

        boolean resultadoSaque = conta.retirada(valorSaque);

        assertTrue(resultadoSaque);
        assertEquals(25_000, conta.getSaldo());
        assertEquals(CategoriaEnum.GOLD, conta.getCategoria());
    }

    @Test
    public void retiradaPlatinum() {
        depositoInicial(50_000);
        depositoInicial(200_000);

        double valorSaque = 202_000;

        boolean resultadoSaque = conta.retirada(valorSaque);

        assertTrue(resultadoSaque);
        assertEquals(50_000, conta.getSaldo());
        assertEquals(CategoriaEnum.GOLD, conta.getCategoria());
    }

    @Test
    public void retiradaContinuaPlatinum() {
        depositoInicial(50_000);
        depositoInicial(200_000);

        double valorSaque = 102_000;

        boolean resultadoSaque = conta.retirada(valorSaque);

        assertTrue(resultadoSaque);
        assertEquals(150_000, conta.getSaldo());
        assertEquals(CategoriaEnum.PLATINUM, conta.getCategoria());
    }

    @ParameterizedTest
    @CsvSource({"49000, 49000, SILVER", "10, 10, SILVER", "55000, 55000, GOLD"})
    public void depositoParametrizado(double deposito, double rEsp, CategoriaEnum categoria){
        conta.deposito(deposito);

        assertEquals(rEsp, conta.getSaldo());
        assertEquals(categoria, conta.getCategoria());
    }

    public void depositoInicial(double valor){

        conta.deposito(valor);
    }


}
