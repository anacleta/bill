package bill.api;

import java.time.LocalDate;

public class CAE {
    private final String codigo;
    private final LocalDate vencimiento;

    public CAE(String codigo, LocalDate vencimiento) {
        this.codigo = codigo;
        this.vencimiento = vencimiento;
    }

    public String getCodigo() {
        return codigo;
    }

    public LocalDate getVencimiento() {
        return vencimiento;
    }
}