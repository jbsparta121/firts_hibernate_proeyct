/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.modelo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "aprendiz")
public class Aprendiz implements Serializable {
    
    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)//Para generar el autoincrementable
    @Column(name = "id")
    private Integer id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "correo")
    private String correo;
    
    @Column(name = "titulacion")
    private String titulacion;

    /*@ManyToOne
    @JoinColumn(name = "titulacion") // Nombre de la columna en la tabla "aprendiz" que hace referencia a "titulacion"
    private Titulacion titulacion;*/

    // Constructor, getters y setters

    public Aprendiz() {
    }

    public Aprendiz(int id, String nombre, String correo, String titulacion) {
        this.id = id;
        this.nombre = nombre;
        this.correo = correo;
        this.titulacion = titulacion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }
    
    public String getTitulacion() {
        return titulacion;
    }

    public void setTitulacion(String titulacion) {
        this.titulacion = titulacion;
    }
}
    

