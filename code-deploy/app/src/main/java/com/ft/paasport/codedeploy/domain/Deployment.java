package com.ft.paasport.codedeploy.domain;

/**
 * @author anuragkapur
 */
public class Deployment {

    private TarFile sourceTar;

    public TarFile getSourceTar() {
        return sourceTar;
    }

    public void setSourceTar(TarFile sourceTar) {
        this.sourceTar = sourceTar;
    }
}
