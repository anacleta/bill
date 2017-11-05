package bill.api;

import java.time.LocalDate;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

class FacturaTest {
    private static final short PUNTO_DE_VENTA = 1;
    private static final int NUMERO = 203;
    private static final String NOMBRE = "nombre";
    private static final LocalDate HOY = LocalDate.now();
    private static final int CANTIDAD = 10;
    private static final String DESCRIPCION = "descripcion";

    @Test
    void billShouldAcceptItem() {
        Factura factura = Factura
            .constructor()
                .conPuntoDeVenta(PUNTO_DE_VENTA)
                .conNumero(NUMERO)
                .conNombre(NOMBRE)
                .conFechaDeHoy()
                .conItem(Factura.Item
                    .constructor()
                        .conCantidad(CANTIDAD)
                        .conDescripcion(DESCRIPCION)
                    .construir())
            .construir();

        assertThat(factura.getPuntoDeVenta(), is(equalTo(PUNTO_DE_VENTA)));
        assertThat(factura.getNumero(), is(equalTo(NUMERO)));
        assertThat(factura.getNombre(), is(equalTo(NOMBRE)));
        assertThat(factura.getFecha(), is(equalTo(HOY)));

        assertThat(factura.getItems().size(), is(equalTo(1)));
        assertThat(factura.getItems().get(0).getCantidad(), is(equalTo(CANTIDAD)));
        assertThat(factura.getItems().get(0).getDescripcion(), is(equalTo(DESCRIPCION)));
    }

}