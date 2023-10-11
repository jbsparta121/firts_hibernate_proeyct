package com.mycompany;

import com.mycompany.controlador.CntAutenticar;
import com.mycompany.vista.FrmAutenticar;

public class App {

    public static void main(String[] args) {
        FrmAutenticar frmAutenticar = new FrmAutenticar();
        CntAutenticar cntAutenticar = new CntAutenticar(frmAutenticar);
        frmAutenticar.setVisible(true);
    }
}
