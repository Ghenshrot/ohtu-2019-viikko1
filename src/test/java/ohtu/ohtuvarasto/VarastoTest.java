package ohtu.ohtuvarasto;

import org.junit.*;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class VarastoTest {

    Varasto varasto;
    double vertailuTarkkuus = 0.0001;

    @Before
    public void setUp() {
        varasto = new Varasto(10);
    }

    @Test
    public void konstruktoriLuoTyhjanVaraston() {
        assertEquals(0, varasto.getSaldo(), vertailuTarkkuus);
    }

    @Test
    public void uudellaVarastollaOikeaTilavuus() {
        assertEquals(10, varasto.getTilavuus(), vertailuTarkkuus);
    }

    @Test
    public void virheellisenVarastonTilavuus() {
        Varasto v = new Varasto(-42);
        assertEquals(0.0, v.getTilavuus(), vertailuTarkkuus);
    }

    @Test
    public void virheellisenVarastonTilavuusAlkusaldolla() {
        Varasto v = new Varasto(-42, 5);
        assertEquals(0.0, v.getTilavuus(), vertailuTarkkuus);
    }

    @Test
    public void liianIsoAlkusaldoUudelleVarastolle() {
        double tilavuus = 10;
        Varasto v = new Varasto(tilavuus, tilavuus * 2);
        assertEquals(tilavuus, v.getTilavuus(), vertailuTarkkuus);
    }

    @Test
    public void negatiivinenAlkusaldoUudellaVarastolla() {
        Varasto v = new Varasto(7, -7);
        assertEquals(0, v.getSaldo(), vertailuTarkkuus);
    }

    @Test
    public void toimivaAlkusaldoUudellaVarastolla() {
        double tilavuus = 8;
        Varasto v = new Varasto(tilavuus * 3, tilavuus);
        assertEquals(tilavuus, v.getSaldo(), vertailuTarkkuus);
    }
    

    @Test
    public void lisaysLisaaSaldoa() {
        varasto.lisaaVarastoon(8);

        // saldon pitäisi olla sama kun lisätty määrä
        assertEquals(8, varasto.getSaldo(), vertailuTarkkuus);
    }

    @Test
    public void lisaysLisaaPienentaaVapaataTilaa() {
        varasto.lisaaVarastoon(8);

        // vapaata tilaa pitäisi vielä olla tilavuus-lisättävä määrä eli 2
        assertEquals(2, varasto.paljonkoMahtuu(), vertailuTarkkuus);
    }

    @Test
    public void lisaysVirheellisellaMaarallaEiTeeMitaan() {
        double alkuperainenMaara = varasto.getSaldo();
        varasto.lisaaVarastoon(-7);
        assertEquals(alkuperainenMaara, varasto.getSaldo(), vertailuTarkkuus);
    }

    @Test
    public void liianSuuriLisaysHukkaa() {
        double lisa = varasto.getTilavuus() - varasto.getSaldo() + 234;
        varasto.lisaaVarastoon(lisa);
        assertEquals(varasto.getTilavuus(), varasto.getSaldo(), vertailuTarkkuus);
    }

    @Test
    public void ottaminenPalauttaaOikeanMaaran() {
        varasto.lisaaVarastoon(8);

        double saatuMaara = varasto.otaVarastosta(2);

        assertEquals(2, saatuMaara, vertailuTarkkuus);
    }

    @Test
    public void ottaminenLisääTilaa() {
        varasto.lisaaVarastoon(8);

        varasto.otaVarastosta(2);

        // varastossa pitäisi olla tilaa 10 - 8 + 2 eli 4
        assertEquals(4, varasto.paljonkoMahtuu(), vertailuTarkkuus);
    }

    @Test
    public void ottaminenNegatiivisellaMaaralla() {
        double saldo = varasto.getSaldo();
        double otettu = varasto.otaVarastosta(-4);
        assertEquals(saldo, varasto.getSaldo(), vertailuTarkkuus);
        assertEquals(0, otettu, vertailuTarkkuus);
    }

    @Test
    public void liianPaljonOttaminenAntaaKaiken() {
        varasto.lisaaVarastoon(varasto.getTilavuus() / 2);
        double saldoAlussa = varasto.getSaldo();
        double otettuMaara = varasto.otaVarastosta(saldoAlussa * 2.0);
        assertEquals(otettuMaara, saldoAlussa, vertailuTarkkuus);
        assertEquals(0.0, varasto.getSaldo(), vertailuTarkkuus);
    }

    @Test
    public void toStringPalauttaaSaldon() {
        assertTrue(varasto.toString().contains("saldo"));
    }
}
