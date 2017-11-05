package bill.api;

import java.util.LinkedList;
import java.util.List;
import java.time.LocalDate;

public class Factura {
    public short getPuntoDeVenta() {
        return puntoDeVenta;
    }

    public int getNumero() {
        return numero;
    }

    public String getNombre() {
        return nombre;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public List<Item> getItems() {
        return items;
    }

    public static class Item {

        public Item(int cantidad, String descripcion) {
            this.cantidad = cantidad;
            this.descripcion = descripcion;
        }

        public int getCantidad() {
            return cantidad;
        }

        public String getDescripcion() {
            return descripcion;
        }

        public static Constructor constructor() {
            return new Constructor();
        }

        public static class Constructor {
            private int cantidad;
            private String descripcion;

            public Item construir() {
                return new Item(cantidad, descripcion);
            }

            public Constructor conCantidad(int cantidad) {
                this.cantidad = cantidad;
                return this;
            }

            public Constructor conDescripcion(String descripcion) {
                this.descripcion = descripcion;
                return this;
            }
        }

        private final int cantidad;
        private final String descripcion;
    }

    public static Constructor constructor() {
        return new Constructor();
    }

    public static class Constructor {
        private String nombre;
        private LocalDate fecha;
        private short puntoDeVenta;
        private int numero;
        private List<Item> items;

        public Constructor() {
            items = new LinkedList<>();
        }

        public Factura construir() {
            return new Factura(nombre, fecha, puntoDeVenta, numero, items);
        }

        public Constructor conNombre(String nombre) {
            this.nombre = nombre;
            return this;
        }

        public Constructor conFechaDeHoy() {
            this.fecha = LocalDate.now();
            return this;
        }

        public Constructor conPuntoDeVenta(short puntoDeVenta) {
            this.puntoDeVenta = puntoDeVenta;
            return this;
        }

        public Constructor conNumero(int numero) {
            this.numero = numero;
            return this;
        }

        public Constructor conItem(Item item) {
            items.add(item);
            return this;
        }
    }

    private Factura(String nombre, LocalDate fecha, short puntoDeVenta, int numero, List<Item> items) {
        this.nombre = nombre;
        this.fecha = fecha;
        this.puntoDeVenta = puntoDeVenta;
        this.numero = numero;
        this.cae = null;
        this.items = items;
    }

    private final String nombre;
    private final LocalDate fecha;
    private final short puntoDeVenta;
    private final int numero;
    private final CAE cae;
    private final List<Item> items;
}