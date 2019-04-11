/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package extra;

import java.util.Date;

/**
 *
 * @author Claudinei
 */
public class Amigo {
    
    private String nome, email, CPF;
    private Date dataNascimento;
    private Character sexo;

    public Amigo() {
        this(null, null, null, null, null);
    }

    public Amigo(String nome, String email, String CPF, Date dataNascimento, Character sexo) {
        this.nome = nome;
        this.email = email;
        this.CPF = CPF;
        this.dataNascimento = dataNascimento;
        this.sexo = sexo;
    }

    public String getCPF() {
        return CPF;
    }

    public void setCPF(String CPF) {
        this.CPF = CPF;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public Character getSexo() {
        return sexo;
    }

    public void setSexo(Character sexo) {
        this.sexo = sexo;
    }
}
