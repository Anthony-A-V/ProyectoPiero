//--------------------------------------------------------------------------
// Clase para representar una cuenta con n�mero, clave y saldo

import java.util.ArrayList;

public class Cuenta {
    // Propiedades
    private String nombreCliente;
    private String numeroCuenta;
    private String clave;
    private double saldo;
    private String calle;
    private String ciudad;
    private String celular;
    private ArrayList<Movimiento> movimientos;

    // Constructor
    public Cuenta(String nombreCliente, String numeroCuenta, String clave, double saldo, String calle, String ciudad,
            String celular) {
        this.nombreCliente = nombreCliente;
        this.numeroCuenta = numeroCuenta;
        this.clave = clave;
        this.saldo = saldo;
        this.calle = calle;
        this.ciudad = ciudad;
        this.celular = celular;
        this.movimientos = new ArrayList<Movimiento>();
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public String getNumeroCuenta() {
        return numeroCuenta;
    }

    public void setNumeroCuenta(String numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    // Metodo para agregar un movimiento al cliente
    public void agregarMovimiento(Movimiento movimiento) {
        // Agregar el nuevo registro al final de la lista
        movimientos.add(movimiento);

        // Si la lista tiene mas de 5 movimientos, elimina el primer elemento
        if (movimientos.size() > 5) {
            movimientos.remove(0);
        }
    }

    // Metodo para obtener todos los movimientos del cliente
    public ArrayList<Movimiento> getMovimientos() {
        return movimientos;
    }
}