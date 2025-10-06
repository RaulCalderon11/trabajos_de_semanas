/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases;

/**
 *
 * @author USUARIO
 */
public class Compra {
    // Atributo para guardar el monto de la compra
    private double montoCompra;

    // Constructor: se ejecuta cuando creamos un objeto Compra
    public Compra(double monto) {
        this.montoCompra = monto;
    }

    // Método que calcula y devuelve el descuento
    public double calcularDescuento() {
        if (montoCompra >= 200) {
            return montoCompra * 0.15; // 15%
        } else if (montoCompra >= 100) {
            return montoCompra * 0.12; // 12%
        } else {
            return montoCompra * 0.10; // 10%
        }
    }

    // Método que calcula y devuelve el total a pagar
    public double calcularTotalPagar() {
        // Reutilizamos el método de arriba para no repetir código
        return montoCompra - calcularDescuento();
    }
    
    // Método para poder acceder al monto original desde fuera (opcional pero útil)
    public double getMontoCompra() {
        return this.montoCompra;
    }
}
