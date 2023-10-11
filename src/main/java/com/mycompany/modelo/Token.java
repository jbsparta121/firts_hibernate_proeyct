/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.modelo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

@Entity
@Table(name = "token")
public class Token implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "numero")
    private int numero;

    @Column(name = "valor")
    private int valor;
    
    @ManyToOne
    @JoinColumn(name = "instructor") // Nombre de la columna en la tabla "aprendiz" que hace referencia a "titulacion"
    private Instructor instructor;
    
    @ManyToOne
    @JoinColumn(name = "aprendiz") // Nombre de la columna en la tabla "aprendiz" que hace referencia a "titulacion"
    private Aprendiz aprendiz;
    
    @Column(name = "fecha")
    private Date fecha;

    public Token() {
    }
  
    public Token(int numero, int valor, Instructor instructor, Aprendiz aprendiz, Date fecha) {
        this.numero = numero;
        this.valor = valor;
        this.instructor = instructor;
        this.aprendiz = aprendiz;
        this.fecha = fecha;
    }
   

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }

    public Instructor getInstructor() {
        return instructor;
    }

    public void setInstructor(Instructor instructor) {
        this.instructor = instructor;
    }

    public Aprendiz getAprendiz() {
        return aprendiz;
    }

    public void setAprendiz(Aprendiz aprendiz) {
        this.aprendiz = aprendiz;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
}
