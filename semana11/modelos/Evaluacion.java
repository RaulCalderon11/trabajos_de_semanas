/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mitesis.modelos;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 *
 * @author palac
 */
public class Evaluacion {
    private int idEvaluacion;
    private int idTesis;
    private String codigoDocente; // FK hacia usuarios.codigo
    
    private String comentarios;
    private BigDecimal puntajeTotal;
    private String condicion; 
    private Timestamp fechaEvaluacion;

    // Ítems de Rúbrica (1-38)
    private BigDecimal item1;
    private BigDecimal item2;
    private BigDecimal item3;
    private BigDecimal item4;
    private BigDecimal item5;
    private BigDecimal item6;
    private BigDecimal item7;
    private BigDecimal item8;
    private BigDecimal item9;
    private BigDecimal item10;
    private BigDecimal item11;
    private BigDecimal item12;
    private BigDecimal item13;
    private BigDecimal item14;
    private BigDecimal item15;
    private BigDecimal item16;
    private BigDecimal item17;
    private BigDecimal item18;
    private BigDecimal item19;
    private BigDecimal item20;
    private BigDecimal item21;
    private BigDecimal item22;
    private BigDecimal item23;
    private BigDecimal item24;
    private BigDecimal item25;
    private BigDecimal item26;
    private BigDecimal item27;
    private BigDecimal item28;
    private BigDecimal item29;
    private BigDecimal item30;
    private BigDecimal item31;
    private BigDecimal item32;
    private BigDecimal item33;
    private BigDecimal item34;
    private BigDecimal item35;
    private BigDecimal item36;
    private BigDecimal item37;
    private BigDecimal item38;

    public Evaluacion() {}

    // Getters y Setters
    public int getIdEvaluacion() { return idEvaluacion; }
    public void setIdEvaluacion(int idEvaluacion) { this.idEvaluacion = idEvaluacion; }

    public int getIdTesis() { return idTesis; }
    public void setIdTesis(int idTesis) { this.idTesis = idTesis; }

    public String getCodigoDocente() { return codigoDocente; }
    public void setCodigoDocente(String codigoDocente) { this.codigoDocente = codigoDocente; }

    public String getComentarios() { return comentarios; }
    public void setComentarios(String comentarios) { this.comentarios = comentarios; }

    public BigDecimal getPuntajeTotal() { return puntajeTotal; }
    public void setPuntajeTotal(BigDecimal puntajeTotal) { this.puntajeTotal = puntajeTotal; }

    public String getCondicion() { return condicion; }
    public void setCondicion(String condicion) { this.condicion = condicion; }

    public Timestamp getFechaEvaluacion() { return fechaEvaluacion; }
    public void setFechaEvaluacion(Timestamp fechaEvaluacion) { this.fechaEvaluacion = fechaEvaluacion; }

    // --- LÓGICA DINÁMICA ---
    public void setItem(int numero, BigDecimal valor) {
        switch (numero) {
            case 1: this.item1 = valor; break;
            case 2: this.item2 = valor; break;
            case 3: this.item3 = valor; break;
            case 4: this.item4 = valor; break;
            case 5: this.item5 = valor; break;
            case 6: this.item6 = valor; break;
            case 7: this.item7 = valor; break;
            case 8: this.item8 = valor; break;
            case 9: this.item9 = valor; break;
            case 10: this.item10 = valor; break;
            case 11: this.item11 = valor; break;
            case 12: this.item12 = valor; break;
            case 13: this.item13 = valor; break;
            case 14: this.item14 = valor; break;
            case 15: this.item15 = valor; break;
            case 16: this.item16 = valor; break;
            case 17: this.item17 = valor; break;
            case 18: this.item18 = valor; break;
            case 19: this.item19 = valor; break;
            case 20: this.item20 = valor; break;
            case 21: this.item21 = valor; break;
            case 22: this.item22 = valor; break;
            case 23: this.item23 = valor; break;
            case 24: this.item24 = valor; break;
            case 25: this.item25 = valor; break;
            case 26: this.item26 = valor; break;
            case 27: this.item27 = valor; break;
            case 28: this.item28 = valor; break;
            case 29: this.item29 = valor; break;
            case 30: this.item30 = valor; break;
            case 31: this.item31 = valor; break;
            case 32: this.item32 = valor; break;
            case 33: this.item33 = valor; break;
            case 34: this.item34 = valor; break;
            case 35: this.item35 = valor; break;
            case 36: this.item36 = valor; break;
            case 37: this.item37 = valor; break;
            case 38: this.item38 = valor; break;
            default: break;
        }
    }

    public BigDecimal getItem(int numero) {
        switch (numero) {
            case 1: return this.item1;
            case 2: return this.item2;
            case 3: return this.item3;
            case 4: return this.item4;
            case 5: return this.item5;
            case 6: return this.item6;
            case 7: return this.item7;
            case 8: return this.item8;
            case 9: return this.item9;
            case 10: return this.item10;
            case 11: return this.item11;
            case 12: return this.item12;
            case 13: return this.item13;
            case 14: return this.item14;
            case 15: return this.item15;
            case 16: return this.item16;
            case 17: return this.item17;
            case 18: return this.item18;
            case 19: return this.item19;
            case 20: return this.item20;
            case 21: return this.item21;
            case 22: return this.item22;
            case 23: return this.item23;
            case 24: return this.item24;
            case 25: return this.item25;
            case 26: return this.item26;
            case 27: return this.item27;
            case 28: return this.item28;
            case 29: return this.item29;
            case 30: return this.item30;
            case 31: return this.item31;
            case 32: return this.item32;
            case 33: return this.item33;
            case 34: return this.item34;
            case 35: return this.item35;
            case 36: return this.item36;
            case 37: return this.item37;
            case 38: return this.item38;
            default: return BigDecimal.ZERO;
        }
    }

}
